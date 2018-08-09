package com.eqxuan.peers.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 消息模板基本属性封装
 */
@Getter
@Setter
public class WechatTemplate {
	
	/** 模板消息ID(必填).*/
	private String template_id;
	
	/** 接收者（用户）的 openid(必填).*/
    private String touser;
    
    /** 支付场景下，为本次支付的 prepay_id(必填).*/
    private String form_id;
    
    /** 点击模板卡片后的跳转页面.*/
    private String page;
    
    /** 模板内容，不填则下发空模板(必填).*/
    private Map<String, TemplateData> data = new HashMap<String, TemplateData>();

}
