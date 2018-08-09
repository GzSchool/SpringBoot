package com.eqxuan.peers.service;

import com.eqxuan.peers.dto.AuthorizeDTO;

public interface UserService {

    AuthorizeDTO get3rdsession(String code);
}
