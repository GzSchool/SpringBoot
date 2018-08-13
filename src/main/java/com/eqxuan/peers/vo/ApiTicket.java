package com.eqxuan.peers.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 获取微信apiTicket接口信息，参数封装
 */
@Getter
@Setter
@ToString
public class ApiTicket implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String ticket;
	
    private long expiresin;

}
