package com.eqxuan.peers.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 获取微信accessToken接口信息，参数封装
 */
@Getter
@Setter
@ToString
public class AccessToken implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 获取到的凭证.*/
    private String accessToken;
    
    /** 凭证有效时间，单位：秒.*/
    private int expiresin;

}
