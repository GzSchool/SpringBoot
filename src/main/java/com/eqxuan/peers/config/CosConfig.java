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

    /** 腾讯云服务器AppId.*/
    @Value("${COS.AppId}")
    private String appId;

    /** 腾讯云SecretId.*/
    @Value("${COS.SecretId}")
    private String secretId;

    /** 腾讯云SecretKey.*/
    @Value("${COS.SecretKey}")
    private String secretKey;

    /** 存储桶名称.*/
    @Value("${COS.BucketName}")
    private String bucketName;

    /** 访问静态资源路径前缀.*/
    @Value("${COS.DomainName}")
    private String domainName;

}
