package com.zimuka.peers.service;

import com.zimuka.peers.dto.AuthorizeDTO;

/**
 * 小程序授权
 */
public interface WxCusInfoService {

    AuthorizeDTO get3rdsession(String code);
}
