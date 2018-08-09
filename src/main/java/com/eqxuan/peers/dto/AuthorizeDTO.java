package com.eqxuan.peers.dto;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 微信授权参数封装
 */
public class AuthorizeDTO {
	
	private String openId;
	
	private String openSession;

	//private Integer id;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOpenSession() {
		return openSession;
	}

	public void setOpenSession(String openSession) {
		this.openSession = openSession;
	}

//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
}
