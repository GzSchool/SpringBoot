package com.zimuka.peers.configBeans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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
