package com.eqxuan.peers.vo;

import java.io.Serializable;

public class WechatOpenId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 用户openId.*/
    private String openId;
    
    /** sessionKey.*/
    private String sessionKey;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

}
