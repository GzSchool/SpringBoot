package com.eqxuan.peers.vo;

import java.io.Serializable;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 获取微信accessToken接口信息，参数封装
 */
public class AccessToken implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 获取到的凭证.*/
    private String accessToken;
    
    /** 凭证有效时间，单位：秒.*/
    private int expiresin;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresin() {
		return expiresin;
	}

	public void setExpiresin(int expiresin) {
		this.expiresin = expiresin;
	}
    
}
