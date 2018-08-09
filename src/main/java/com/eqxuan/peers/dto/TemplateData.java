package com.eqxuan.peers.dto;

/**
 * 消息模板内容，字体大小，颜色可配置
 * @author zimuka
 *
 * 2018年3月5日
 */
public class TemplateData {
	
	/** 模板内容字体的颜色，不填默认黑色.*/
    private String color;
    
    /** 模板需要放大的关键词，不填则默认无放大.*/
    private String value;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public TemplateData() {
		
	}
	
	public TemplateData(String value, String color) {
		this.value = value;
		this.color = color;
	}
}
