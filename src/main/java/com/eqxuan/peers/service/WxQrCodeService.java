package com.eqxuan.peers.service;

import org.springframework.web.multipart.MultipartFile;

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
     * @return
     */
    String makeWxQrCode(String userPhotoUrl, String scene, String page, String openId);

    /**
     * 图片上传
     * @param openId
     * @param cardId
     * @param multipartFile
     * @param index
     * @return
     */
    String fileUpload(String openId, String cardId, MultipartFile[] multipartFile, String index);
}
