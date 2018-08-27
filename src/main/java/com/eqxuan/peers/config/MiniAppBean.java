package com.eqxuan.peers.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 小程序基础信息配置类
 */
@Configuration
@Getter
public class MiniAppBean {

    @Value("${WxMini.AppId}")
    private String appId;

    @Value("${WxMini.AppSecret}")
    private String appSecret;

    /** 名片创建消息模板ID.*/
    @Value("${WxMini.MakeTemplateID}")
    private String makeTemplateID;

    /** 名片保存消息模板ID.*/
    @Value("${WxMini.SaveTemplateID}")
    private String saveTemplateID;

}
