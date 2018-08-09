package com.eqxuan.peers.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 微信授权参数封装
 */
@Getter
@Setter
public class AuthorizeDTO {
	
	private String openId;
	
	private String openSession;

}
