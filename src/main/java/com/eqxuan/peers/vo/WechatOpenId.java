package com.eqxuan.peers.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 获取微信授权接口信息，参数封装
 */
@Getter
@Setter
public class WechatOpenId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 用户openId.*/
    private String openId;
    
    /** sessionKey.*/
    private String sessionKey;

}
