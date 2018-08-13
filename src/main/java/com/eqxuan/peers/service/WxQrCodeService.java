package com.eqxuan.peers.service;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/12 16:55
 * @Description: 小程序码个性化生成
 */
public interface WxQrCodeService {
    
    String makeWxQrCode(String userPhotoUrl, String scene, String page, String openId);
}
