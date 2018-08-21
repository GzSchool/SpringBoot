package com.eqxuan.peers.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zheng guangjing.
 * @date: 2018/8/21 12:06
 * @description: description
 */
@Configuration
@Getter
public class CosConfig {

    @Value("${COS.AppId}")
    private String appId;

    @Value("${COS.SecretId}")
    private String secretId;

    @Value("${COS.SecretKey}")
    private String secretKey;

    @Value("${COS.BucketName}")
    private String bucketName;

    @Value("${COS.DomainName}")
    private String domainName;

}
