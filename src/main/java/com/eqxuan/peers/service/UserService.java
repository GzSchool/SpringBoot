package com.eqxuan.peers.service;

import com.eqxuan.peers.dto.AuthorizeDTO;

public interface UserService {

    /**
     * 接口描述：用户授权接口
     * @param code
     * @return
     */
    AuthorizeDTO get3rdsession(String code);
}
