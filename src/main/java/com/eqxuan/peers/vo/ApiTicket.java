package com.eqxuan.peers.vo;

import java.io.Serializable;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 获取微信apiTicket接口信息，参数封装
 */
public class ApiTicket implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String ticket;
	
    private long expiresin;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public long getExpiresin() {
		return expiresin;
	}

	public void setExpiresin(long expiresin) {
		this.expiresin = expiresin;
	}
    
}
