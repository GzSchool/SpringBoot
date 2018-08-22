package com.eqxuan.peers.service.impl;

import com.eqxuan.peers.config.MiniAppBean;
import com.eqxuan.peers.exception.PeerProjectException;
import com.eqxuan.peers.service.WxQrCodeService;
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

    @Override
    public String fileUpload(String openId, String cardId, MultipartFile[] multipartFiles, String index) {
        try {
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
                stringBuffer.append(cosFileUploadUtil.picCOS(file, openId, cardId, index) + ",");
                File del = new File(file.toURI());
                del.delete();
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            throw new PeerProjectException("上传图片异常");
        }
    }
}
