package com.eqxuan.peers.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 消息模板内容，字体大小，颜色可配置
 */
@Getter
@Setter
@ToString
public class TemplateData {
	
	/** 模板内容字体的颜色，不填默认黑色.*/
    private String color;
    
    /** 模板需要放大的关键词，不填则默认无放大.*/
    private String value;

	public TemplateData(String value, String color) {
		this.value = value;
		this.color = color;
	}
}
