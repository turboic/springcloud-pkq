package com.turboic.cloud.aes;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @author liebe
 */
public class AesUtil {
    private static final Logger logger = LoggerFactory.getLogger(AesUtil.class);

    public static void main(String[] args) throws Exception {
        /***
         * java.security.InvalidParameterException: Wrong keysize: must be equal to 128, 192 or 256
         */
        String algorithm = "AES";
        String content = "20200712";
        String salt = "Idea@2020$Sz-@shanghai%2089145!167";
        int length = 192;
        String encryptString = encrypt(algorithm,content,salt,length);
        logger.error("加密后:{}",encryptString);

        String decryptString = decrypt(algorithm,encryptString,salt,length);
        logger.error("解密后:{}",decryptString);
    }

    public static String encrypt(String algorithm,String content, String salt,int length) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(length, new SecureRandom(salt.getBytes()));
        SecretKeySpec key = new SecretKeySpec(keyGenerator.generateKey().getEncoded(), algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        String hexString = byteToHexString(cipher.doFinal(content.getBytes("UTF-8")));
        logger.info("二进制源:{}",cipher.doFinal(content.getBytes("UTF-8")));
        logger.info("二进制转换成十六进制小写:{}",hexString);
        byte [] bytes = hexStringToByte(hexString);
        logger.info("十六制转换成二进制小写:{}",bytes);
        return Base64.encodeBase64String(cipher.doFinal(content.getBytes("UTF-8")));
    }

    public static String decrypt(String algorithm,String content, String salt,int length) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(length, new SecureRandom(salt.getBytes()));
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(keyGenerator.generateKey().getEncoded(), algorithm));
        byte[] result = cipher.doFinal(Base64.decodeBase64(content));
        return new String(result,"UTF-8");
    }

    public static String byteToHexString(byte [] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            stringBuffer.append(hex.toLowerCase());
        }
        return stringBuffer.toString();
    }

    public static byte[] hexStringToByte(String hexString) {
        if (hexString.length() < 1){
            return null;
        }
        byte[] result = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length() / 2; i++) {
            int high = Integer.parseInt(hexString.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexString.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
