package com.eqxuan.peers.service.impl;

import com.eqxuan.peers.config.MiniAppBean;
import com.eqxuan.peers.dao.UserCard;
import com.eqxuan.peers.exception.PeerProjectException;
import com.eqxuan.peers.mapper.UserCardMapper;
import com.eqxuan.peers.service.WxQrCodeService;
import com.eqxuan.peers.service.cache.CacheManager;
import com.eqxuan.peers.utils.CosFileUploadUtil;
import com.eqxuan.peers.utils.WxQrCodeUtil;
import com.eqxuan.peers.utils.WxTemplateUtil;
import com.eqxuan.peers.vo.AccessToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/12 16:58
 * @Description: 小程序码个性化生成
 */
@Service
public class WxQrCodeServiceImpl implements WxQrCodeService {

    @Autowired
    private MiniAppBean miniAppBean;

    @Resource
    private CosFileUploadUtil cosFileUploadUtil;

    @Resource
    private UserCardMapper userCardMapper;

    @Resource
    private CacheManager cacheManager;

    /**
     * 接口描述：个性化小程序码生成
     * @param userPhotoUrl 用户头像地址
     * @param scene 参数
     * @param page 跳转位置
     * @param openId 当前用户ID
     * @param cardId 当前名片ID
     * @param index 图片别名
     * @return
     */
    @Override
    public String makeWxQrCode(String userPhotoUrl, String scene, String page, String openId, String cardId, String index) {
        try {
            if (StringUtils.isEmpty(userPhotoUrl) || StringUtils.isEmpty(openId) || StringUtils.isEmpty(scene) || StringUtils.isEmpty(openId)) {
                throw new PeerProjectException("参数缺失");
            }
            AccessToken accessToken = WxTemplateUtil.getAccessToken(miniAppBean.getAppId(), miniAppBean.getAppSecret());

            // 开始制作个性化小程序码（获取用户头像）
            InputStream photoInputStream = WxQrCodeUtil.getInputStream(userPhotoUrl);
            // 图像缩放
            BufferedImage zoomPhoto = WxQrCodeUtil.zoomPhoto(photoInputStream, 192, 192);
            // 图像截为圆形（用来代替小程序码的中央LOGO）
            BufferedImage centerImg = WxQrCodeUtil.circle(zoomPhoto);
            // 获取小程序码
            InputStream miniQrCodeInputStream = WxQrCodeUtil.miniQrCode(accessToken.getAccessToken(), scene, page);
            // 替换后的小程序码
            String fileName = cardId + "-" + index + ".png";
            File file = WxQrCodeUtil.makeInRound(miniQrCodeInputStream, centerImg, fileName);
            String returnUrl = cosFileUploadUtil.picCOS(file, openId, cardId, index);
            // 删除临时文件
            file.delete();
            return returnUrl;
        } catch (Exception e) {
            throw new PeerProjectException("上传图片异常");
        }
    }

    /**
     * 接口描述：图片上传至服务器
     * @param openId 当前用户标识
     * @param cardId 名片ID
     * @param multipartFiles
     * @param index 文件别名
     * @return
     */
    @Override
    public synchronized List<String> fileUpload(String openId, String cardId, MultipartFile[] multipartFiles, String index) {
        try {

            if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(cardId) || StringUtils.isEmpty(index)) {
                throw new PeerProjectException("参数缺失");
            }
            if (null == multipartFiles || "".equals(multipartFiles)) {
                throw new PeerProjectException("获取图片失败 ");
            }

            UserCard checkUserCard = userCardMapper.findById(Integer.parseInt(cardId));
            if (null == checkUserCard) {
                throw new PeerProjectException("名片不存在");
            }
            if (!checkUserCard.getOpenId().equals(openId)) {
                throw new PeerProjectException("该名片不属于你，不能修改");
            }

            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < multipartFiles.length; i++) {
                File file = null;
                if (multipartFiles[i].equals("") || multipartFiles[i].getSize() <= 0) {
                    multipartFiles[i] = null;
                } else {
                    InputStream inputStream = multipartFiles[i].getInputStream();
                    file = new File(multipartFiles[i].getOriginalFilename());
                    cosFileUploadUtil.inputStreamToFile(inputStream, file);
                }
                String imgUrl = cosFileUploadUtil.picCOS(file, openId, cardId, index);

                if ("touxiang".equals(index)) {
                    stringBuffer = new StringBuffer(imgUrl);
                    checkUserCard.setUserImg(imgUrl);
                    int rows = userCardMapper.update(checkUserCard);
                    if (1 != rows) {
                        throw new PeerProjectException("上传头像失败");
                    }
                } else {

                    if (StringUtils.isNotBlank(checkUserCard.getPhoto()) && !checkUserCard.getPhoto().endsWith(";")) {
                        checkUserCard.setPhoto(checkUserCard.getPhoto() + ";");
                    }

                    stringBuffer = new StringBuffer(checkUserCard.getPhoto());
                    checkUserCard.setPhoto(stringBuffer.append(imgUrl).append(";").toString());
                    int rows = userCardMapper.update(checkUserCard);
                    if (1 != rows) {
                        throw new PeerProjectException("上传相册失败");
                    }
                }
                // 更新缓存
                cacheManager.cacheUserCard(checkUserCard);

                File del = new File(file.toURI());
                del.delete();
            }
            String[] result = stringBuffer.toString().split(";");
            List<String> urls = Arrays.asList(result);
            return urls;
        } catch (Exception e) {
            throw new PeerProjectException("上传图片异常");
        }
    }

    /**
     * 接口描述：删除服务器文件
     * @param delFileUrl 删除文件的地址
     * @param openId 用户标识
     * @param cardId 名片ID
     */
    @Override
    public void delFile(String delFileUrl, String openId, String cardId) {

        if (StringUtils.isEmpty(delFileUrl) || StringUtils.isEmpty(openId) || StringUtils.isEmpty(cardId)) {
            throw new PeerProjectException("参数缺失");
        }

        UserCard checkUserCard = userCardMapper.findById(Integer.parseInt(cardId));
        if (null == checkUserCard) {
            throw new PeerProjectException("该名片不存在");
        }
        if (!checkUserCard.getOpenId().equals(openId)) {
            throw new PeerProjectException("改名片不属于你，不能修改");
        }

        String[] photoUrls = checkUserCard.getPhoto().split(";");

        List<String> stringPhotos = Arrays.asList(photoUrls);
        List<String> photoList = new ArrayList<String>(stringPhotos);
        Iterator<String> iterator = photoList.iterator();
        while (iterator.hasNext()) {
            String photo = iterator.next();
            if (photo.equals(delFileUrl)) {
                iterator.remove();
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (String residueUrl : photoList) {
            stringBuffer.append(residueUrl).append(";");
        }

        checkUserCard.setPhoto(stringBuffer.toString());
        int rows = userCardMapper.update(checkUserCard);
        if (1 != rows) {
            throw new PeerProjectException("删除文件异常");
        }

        // 更新缓存
        cacheManager.cacheUserCard(checkUserCard);

        String[] index = delFileUrl.split("com/");
        cosFileUploadUtil.delFileCOS(index[1]);
    }
}