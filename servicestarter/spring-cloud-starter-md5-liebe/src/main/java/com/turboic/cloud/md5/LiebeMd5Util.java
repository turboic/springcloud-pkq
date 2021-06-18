package com.turboic.cloud.md5;

import com.turboic.cloud.LiebeMd5ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.security.MessageDigest;

/**
 * @author liebe
 */
@Component
public class LiebeMd5Util {
    private static final Logger logger = LoggerFactory.getLogger(LiebeMd5Util.class);
    @Autowired
    private LiebeMd5ConfigProperties liebeMd5ConfigProperties;
    public String computer(String content) {
        try {
            content += liebeMd5ConfigProperties.getHost()+"/"+liebeMd5ConfigProperties.getUsername()+"@"+liebeMd5ConfigProperties.getPassword();
            logger.info("starter后台拼接的content:{}",content);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            char[] charArray = content.toCharArray();
            byte[] byteArray = new byte[charArray.length];
            for (int i = 0; i < charArray.length; i++) {
                byteArray[i] = (byte) charArray[i];
            }
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            logger.error("计算md5出现异常:{}",e);
            return null;
        }
    }
}
