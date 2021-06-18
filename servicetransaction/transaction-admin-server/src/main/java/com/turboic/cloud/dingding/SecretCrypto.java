package com.turboic.cloud.dingding;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

/**
 * 钉钉加签的算法
 * @author liebe
 */
public class SecretCrypto {
    private static final Logger logger = LoggerFactory.getLogger(SecretCrypto.class);
    public static String crypto(String source,Long timestamp) throws Exception{
        if(timestamp == null){
            timestamp = System.currentTimeMillis();
        }
        String secret = source;
        String sign = null;

        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        System.out.println(sign);

        System.out.println(sign);
        logger.info(sign);
        return sign;
    }
}
