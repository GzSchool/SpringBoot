package com.eqxuan.peers.vo;

import java.io.Serializable;

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
