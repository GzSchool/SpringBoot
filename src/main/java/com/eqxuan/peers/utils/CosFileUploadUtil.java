package com.eqxuan.peers.utils;

import com.eqxuan.peers.config.CosConfig;
import com.eqxuan.peers.exception.PeerProjectException;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.Date;

/**
 * @author: zheng guangjing.
 * @date: 2018/8/21 12:19
 * @description: 腾讯云上传文件到对象服务器（COS）
 */
@Service
public class CosFileUploadUtil {

    @Autowired
    private CosConfig cosConfig;

    public String picCOS (File cosFile, String openId, String cardId, String index) throws Exception {

        COSCredentials cred = new BasicCOSCredentials(cosConfig.getSecretId(), cosConfig.getSecretKey());

        // 设置bucket的区域，COS区域简称，参考腾讯云 官网
        // https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing"));
        // 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 生成文件路径
        String key = "image/" + openId  + "/" + cardId + "-" + index + ".png";
        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        // 指定要上传到 COS 上的路径
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosConfig.getBucketName(), key, cosFile);
        cosClient.putObject(putObjectRequest);
        cosClient.shutdown();
        Date expiration = new Date(System.currentTimeMillis() + 5 * 60 * 10000);
        URL url = cosClient.generatePresignedUrl(cosConfig.getBucketName(), key, expiration);
        if (null == url) {
            throw new PeerProjectException("上传失败");
        }
        String returnUrl = cosConfig.getDomainName() + "/" + key;
        return returnUrl;
    }
}
