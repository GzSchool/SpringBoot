package com.eqxuan.peers.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/12 16:55
 * @Description: 小程序码个性化生成
 */
public interface WxQrCodeService {

    /**
     * 个性化小程序码生成
     * @param userPhotoUrl 用户头像地址
     * @param scene 参数
     * @param page 跳转位置
     * @param openId 当前用户ID
     * @param cardId 当前名片ID
     * @param index 图片别名
     * @return
     */
    String makeWxQrCode(String userPhotoUrl, String scene, String page, String openId, String cardId, String index);

    /**
     * 图片上传
     * @param openId
     * @param cardId
     * @param multipartFile
     * @param index
     * @return
     */
    List<String> fileUpload(String openId, String cardId, MultipartFile[] multipartFile, String index);

    /**
     * 删除文件
     * @param delFileUrl
     * @param openId
     * @param cardId
     */
    void delFile(String delFileUrl, String openId, String cardId);
}
