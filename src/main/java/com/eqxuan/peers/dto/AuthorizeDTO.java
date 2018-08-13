package com.eqxuan.peers.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 微信授权参数封装
 */
@Getter
@Setter
@ToString
public class AuthorizeDTO {
	
	private String openId;
	
	private String openSession;

}
