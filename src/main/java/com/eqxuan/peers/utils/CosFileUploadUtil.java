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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

    /**
     * 上传文件到对象服务器
     * @param cosFile
     * @param openId
     * @param cardId
     * @param index
     * @return
     * @throws Exception
     */
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

    /**
     * 文件删除
     * @param key 文件名称
     */
    public void delFileCOS(String key) {
        COSCredentials cred = new BasicCOSCredentials(cosConfig.getSecretId(), cosConfig.getSecretKey());

        // 设置bucket的区域，COS区域简称，参考腾讯云 官网
        // https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing"));
        // 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);

        cosClient.deleteObject(cosConfig.getBucketName(), key);
    }

    /**
     * MultipartFile 转换为 File
     * @param ins
     * @param file
     */
    public void inputStreamToFile(InputStream ins, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != ins) {
                    ins.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
