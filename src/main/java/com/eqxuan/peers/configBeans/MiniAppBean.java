package com.eqxuan.peers.configBeans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 小程序基础信息配置类
 */
@Configuration
public class MiniAppBean {

    @Value("${AppId}")
    private String AppId;

    @Value("${AppSecret}")
    private String AppSecret;

    public String getAppId() {
        return AppId;
    }

    public String getAppSecret() {
        return AppSecret;
    }
}
