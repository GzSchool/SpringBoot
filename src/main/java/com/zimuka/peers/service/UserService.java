package com.zimuka.peers.service;

import com.zimuka.peers.dto.AuthorizeDTO;

public interface UserService {

    AuthorizeDTO get3rdsession(String code);
}
