package com.eqxuan.peers.service;

import com.eqxuan.peers.dto.AuthorizeDTO;

public interface UserService {

    /**
     * 接口描述：用户授权接口
     * @param code
     * @return
     */
    AuthorizeDTO get3rdsession(String code);

    /**
     * 接口描述：解析用户绑定手机号
     * @param openId
     * @param iv
     * @param encryptedData
     * @return
     */
    String getUserPhone(String openId, String iv, String encryptedData);
}
