package com.eqxuan.peers.vo;

import java.io.Serializable;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 获取微信授权接口信息，参数封装
 */
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
