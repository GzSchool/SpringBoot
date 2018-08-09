package com.eqxuan.peers.utils;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 微信加密信息解密工具
 */
@Service
public class WxDecipherUtil {

    public JSONObject getGroupId(String encryptedData, String sessionKey, String iv) {
                    //加密数据
                    byte[] dataByte = Base64.decode(encryptedData);

                    //加密密钥
                    byte[] keyByte = Base64.decode(sessionKey);

                    //偏移量
                    byte[] ivByte = Base64.decode(iv);

                    try {
                        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
                        int base = 16;
                        if (keyByte.length % base != 0) {
                            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                            byte[] temp = new byte[groups * base];
                            Arrays.fill(temp, (byte) 0);
                            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                            keyByte = temp;
            }

            //初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
