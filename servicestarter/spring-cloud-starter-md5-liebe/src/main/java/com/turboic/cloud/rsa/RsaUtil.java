package com.turboic.cloud.rsa;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author liebe
 */
public class RsaUtil {
    /***
     * 公钥：
     * MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzvrewTbE/poDl2lQ9Wpmrwp4/Q9U1FzjIg7a4NQT4m3aHAHkgJsy6k4klOMoHqHw/gHq5y4Bmwg9zv/oo8+1mIpoqxz7C4l1EZfJWwXXsMsdKGg7seo+LKSzNIrohvVmhmwEUG1FqJVqYoaelz7mx3Y3swlo6hhM0YnHkE2FYPmut9BGkGmpramf1pEEhwR/JOm3KYeQ8Hy59sXfgrAyDLGXRu4mllv8vVs1TXZZ4tkX511eo3j5I2T3180+GwY9ird9IiUgkBf6guuK0T6hZrefgKaucYmxItvFpN0E/CFr2C+L6s/SHEqqZPEc+nzpdvzlQ7eLJwbNxiCnqiPvkwIDAQAB
     * @param args
     */

    /***
     * 私钥
     * MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDO+t7BNsT+mgOXaVD1amavCnj9D1TUXOMiDtrg1BPibdocAeSAmzLqTiSU4ygeofD+AernLgGbCD3O/+ijz7WYimirHPsLiXURl8lbBdewyx0oaDux6j4spLM0iuiG9WaGbARQbUWolWpihp6XPubHdjezCWjqGEzRiceQTYVg+a630EaQaamtqZ/WkQSHBH8k6bcph5DwfLn2xd+CsDIMsZdG7iaWW/y9WzVNdlni2RfnXV6jePkjZPfXzT4bBj2Kt30iJSCQF/qC64rRPqFmt5+Apq5xibEi28Wk3QT8IWvYL4vqz9IcSqpk8Rz6fOl2/OVDt4snBs3GIKeqI++TAgMBAAECggEAc09CEERSBUGKZK0QpFjNshDUjK8g5FqHU28RM5YTKd9nVpkbUyaje/Ni3+uZa09u8iPhWT/BNzylKpBZbAGIut3JKCMyHSBMLyvvypz7qAvQR7WiJCFK2BY9nDUTDDTlFFhTIvdtilkLdq5m5f3uFgtfRogkgd5d1ZK7skHt37LZlboRSjeuzAE3y7a5sgBIbow8/WUJsEhh596dM4b+j9K0HAxu4Dk/XYPK4R19o7o1CZRl4aGMxqde6T4OhXBhw+rYYTsZ/xwsso+KKcWXNQb8tdwJptyFdw81vCmnDwpBUtFuVfCIJuewqmo0csMmQXETyZTNqCoeRc0Mjil+EQKBgQDwA0sjjAwRj2umKDzZnMl7F/h0t/KbT56GZVvuegBv3SHiS5T5gyuP9eDHlk9xqdL0YY7QMwPjIZbVWf/PEc0GeO7to7oPcKZepCB6Bssysd85a/aDMzcYCOnSWfGukF3vr9TuPI4qAPLI3I0Cq9lOpkwPOOvp+10uwMpD9lU6CwKBgQDcxExvGQdoXxXLFucmDQPhdOJtIDK9mLhS0utf+i2fdWxFWFihNAfS3pu85BKW2g2ZyeEZS+jzFoJn2IErnbyPzIotJhcEdVsE1ehrA4NOjAXCL6Ksz625r+d4MAi+HEVkohyjs3eN2Glrg1YqKR2rWCjGUUy/CkZlrHTTAoMdmQKBgHngQkR/thUorfJG1nduTUVra6fhTJmfOZFnYOJdI2Gqea1O0gxNlk5Y/dZ7tkWyhcPA0mNYtG2evdgDBoogsOrLeBBGJpH2kxTV5QPx9Tb96atf7mK7HAvhYtKFB9urxXeT4t1C9LHRn3dtv1EO8J8mhNLqBangEXZPQaZ3LNn3AoGAQGkhZ9SknaYIZL2k6Swo8rG4kmAvjbOuK7QQKmuCviBzW3QgP8A/fB2o0J62cb7i8vi8rTXBnJL3rzay0TfvWQEzW2LemlVnHe0PJ5HZ0KP31iUx0DuzkxaocR+KnXUAJhFKS9TDz+XRNCYYkKp4VpKdKudzfdWQnn+Hgj0cZhkCgYEApA7ACp3kOKjLW4UPqYiJe457LSZmGQ0o+M8NHT0hNmm8HcY2Rq8z8diVjmw9WMcvMzB5aokxsliJFfKKUQ0tExvZnt4X8GAHJ6m1VO99rlBNEg8Vfq5yfBAiU92vlOb6nH6HSdUWiv0ORsa/D999YPw+qXCWB1Lin0N6uNTVxsU=
     * @param args
     */

    public static void main(String[] args) {
        String algorithm = "RSA";
        String pubPath = "D:\\RSA";
        String priPath = "D:\\RSA";
        try {
            generateKeyToFile(algorithm,pubPath,priPath);
        } catch (Exception e) {
            System.err.println("生成密钥对异常:{}"+e);
            System.err.println("throw new NoSuchAlgorithmException(liebe + \" KeyPairGenerator not available\")");
        }
        /**
         * 公钥加密
         */
        int maxEncryptSize = 1024*1024;
        int maxDecryptSize = 1024*1024;
        String source = "清爽柠檬味汽水20200712";
        String publicData = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzvrewTbE/poDl2lQ9Wpmrwp4/Q9U1FzjIg7a4NQT4m3aHAHkgJsy6k4klOMoHqHw/gHq5y4Bmwg9zv/oo8+1mIpoqxz7C4l1EZfJWwXXsMsdKGg7seo+LKSzNIrohvVmhmwEUG1FqJVqYoaelz7mx3Y3swlo6hhM0YnHkE2FYPmut9BGkGmpramf1pEEhwR/JOm3KYeQ8Hy59sXfgrAyDLGXRu4mllv8vVs1TXZZ4tkX511eo3j5I2T3180+GwY9ird9IiUgkBf6guuK0T6hZrefgKaucYmxItvFpN0E/CFr2C+L6s/SHEqqZPEc+nzpdvzlQ7eLJwbNxiCnqiPvkwIDAQAB";
        Key publicKey = null;
        try {
            publicKey = loadPublicKeyFromString(algorithm,publicData);
        } catch (Exception e) {
            System.err.println("publicKey获取失败:"+e);
        }
        String encryptString = null;
        try {
            encryptString = encrypt(algorithm,source,publicKey, maxEncryptSize);
        } catch (Exception e) {
            System.err.println("encrypt:"+e);
        }
        System.err.println("加密后:"+encryptString);

        String privateData = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDO+t7BNsT+mgOXaVD1amavCnj9D1TUXOMiDtrg1BPibdocAeSAmzLqTiSU4ygeofD+AernLgGbCD3O/+ijz7WYimirHPsLiXURl8lbBdewyx0oaDux6j4spLM0iuiG9WaGbARQbUWolWpihp6XPubHdjezCWjqGEzRiceQTYVg+a630EaQaamtqZ/WkQSHBH8k6bcph5DwfLn2xd+CsDIMsZdG7iaWW/y9WzVNdlni2RfnXV6jePkjZPfXzT4bBj2Kt30iJSCQF/qC64rRPqFmt5+Apq5xibEi28Wk3QT8IWvYL4vqz9IcSqpk8Rz6fOl2/OVDt4snBs3GIKeqI++TAgMBAAECggEAc09CEERSBUGKZK0QpFjNshDUjK8g5FqHU28RM5YTKd9nVpkbUyaje/Ni3+uZa09u8iPhWT/BNzylKpBZbAGIut3JKCMyHSBMLyvvypz7qAvQR7WiJCFK2BY9nDUTDDTlFFhTIvdtilkLdq5m5f3uFgtfRogkgd5d1ZK7skHt37LZlboRSjeuzAE3y7a5sgBIbow8/WUJsEhh596dM4b+j9K0HAxu4Dk/XYPK4R19o7o1CZRl4aGMxqde6T4OhXBhw+rYYTsZ/xwsso+KKcWXNQb8tdwJptyFdw81vCmnDwpBUtFuVfCIJuewqmo0csMmQXETyZTNqCoeRc0Mjil+EQKBgQDwA0sjjAwRj2umKDzZnMl7F/h0t/KbT56GZVvuegBv3SHiS5T5gyuP9eDHlk9xqdL0YY7QMwPjIZbVWf/PEc0GeO7to7oPcKZepCB6Bssysd85a/aDMzcYCOnSWfGukF3vr9TuPI4qAPLI3I0Cq9lOpkwPOOvp+10uwMpD9lU6CwKBgQDcxExvGQdoXxXLFucmDQPhdOJtIDK9mLhS0utf+i2fdWxFWFihNAfS3pu85BKW2g2ZyeEZS+jzFoJn2IErnbyPzIotJhcEdVsE1ehrA4NOjAXCL6Ksz625r+d4MAi+HEVkohyjs3eN2Glrg1YqKR2rWCjGUUy/CkZlrHTTAoMdmQKBgHngQkR/thUorfJG1nduTUVra6fhTJmfOZFnYOJdI2Gqea1O0gxNlk5Y/dZ7tkWyhcPA0mNYtG2evdgDBoogsOrLeBBGJpH2kxTV5QPx9Tb96atf7mK7HAvhYtKFB9urxXeT4t1C9LHRn3dtv1EO8J8mhNLqBangEXZPQaZ3LNn3AoGAQGkhZ9SknaYIZL2k6Swo8rG4kmAvjbOuK7QQKmuCviBzW3QgP8A/fB2o0J62cb7i8vi8rTXBnJL3rzay0TfvWQEzW2LemlVnHe0PJ5HZ0KP31iUx0DuzkxaocR+KnXUAJhFKS9TDz+XRNCYYkKp4VpKdKudzfdWQnn+Hgj0cZhkCgYEApA7ACp3kOKjLW4UPqYiJe457LSZmGQ0o+M8NHT0hNmm8HcY2Rq8z8diVjmw9WMcvMzB5aokxsliJFfKKUQ0tExvZnt4X8GAHJ6m1VO99rlBNEg8Vfq5yfBAiU92vlOb6nH6HSdUWiv0ORsa/D999YPw+qXCWB1Lin0N6uNTVxsU=";
        Key privateKey = null;
        try {
            privateKey = loadPrivateKeyFromString(algorithm,privateData);
        } catch (Exception e) {
            System.err.println("privateKey获取失败:"+e);
        }
        String decryptString = null;
        try {
            decryptString = decrypt(algorithm,encryptString,privateKey,maxDecryptSize);
        } catch (Exception e) {
            System.err.println("decrypt:"+e);
        }
        System.err.println("解密后:"+decryptString);
        /***
         * 私钥解密
         */
    }

    /**
     * 生成密钥对并保存在本地文件中
     *
     * @param algorithm : 算法
     * @param pubPath   : 公钥保存路径
     * @param priPath   : 私钥保存路径
     * @throws Exception
     */
    private static void generateKeyToFile(String algorithm, String pubPath, String priPath) throws Exception {
        // 获取密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(4096);
        // 获取密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取公钥
        PublicKey publicKey = keyPair.getPublic();
        // 获取私钥
        PrivateKey privateKey = keyPair.getPrivate();
        // 获取byte数组
        byte[] publicKeyEncoded = publicKey.getEncoded();
        byte[] privateKeyEncoded = privateKey.getEncoded();
        // 进行Base64编码
        String publicKeyString = Base64.encodeBase64String(publicKeyEncoded);
        System.err.println("生成公钥:{}"+publicKeyString);
        String privateKeyString = Base64.encodeBase64String(privateKeyEncoded);
        System.err.println("生成私钥钥:{}"+privateKeyString);
        // 保存文件
        FileUtils.writeStringToFile(new File(pubPath), publicKeyString, Charset.forName("UTF-8"));
        FileUtils.writeStringToFile(new File(priPath), privateKeyString, Charset.forName("UTF-8"));

    }

    /**
     * 从文件中加载公钥
     *
     * @param algorithm : 算法
     * @param filePath  : 文件路径
     * @return : 公钥
     * @throws Exception
     */
    private static PublicKey loadPublicKeyFromFile(String algorithm, String filePath) throws Exception {
        // 将文件内容转为字符串
        String keyString = FileUtils.readFileToString(new File(filePath), Charset.forName("UTF-8"));

        return loadPublicKeyFromString(algorithm, keyString);

    }

    /**
     * 从字符串中加载公钥
     *
     * @param algorithm : 算法
     * @param keyString : 公钥字符串
     * @return : 公钥
     * @throws Exception
     */
    private static PublicKey loadPublicKeyFromString(String algorithm, String keyString) throws Exception {
        // 进行Base64解码
        byte[] decode = Base64.decodeBase64(keyString);
        // 获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 构建密钥规范
        X509EncodedKeySpec keyspec = new X509EncodedKeySpec(decode);
        // 获取公钥
        return keyFactory.generatePublic(keyspec);

    }

    /**
     * 从文件中加载私钥
     *
     * @param algorithm : 算法
     * @param filePath  : 文件路径
     * @return : 私钥
     * @throws Exception
     */
    private static PrivateKey loadPrivateKeyFromFile(String algorithm, String filePath) throws Exception {
        // 将文件内容转为字符串
        String keyString = FileUtils.readFileToString(new File(filePath), Charset.forName("UTF-8"));
        return loadPrivateKeyFromString(algorithm, keyString);

    }

    /**
     * 从字符串中加载私钥
     *
     * @param algorithm : 算法
     * @param keyString : 私钥字符串
     * @return : 私钥
     * @throws Exception
     */
    private static PrivateKey loadPrivateKeyFromString(String algorithm, String keyString) throws Exception {
        // 进行Base64解码
        byte[] decode = Base64.decodeBase64(keyString);
        // 获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 构建密钥规范
        PKCS8EncodedKeySpec keyspec = new PKCS8EncodedKeySpec(decode);
        // 生成私钥
        return keyFactory.generatePrivate(keyspec);

    }

    /**
     * 使用密钥加密数据
     *
     * @param algorithm      : 算法
     * @param input          : 原文
     * @param key            : 密钥
     * @param maxEncryptSize : 最大加密长度(需要根据实际情况进行调整)
     * @return : 密文
     * @throws Exception
     */
    private static String encrypt(String algorithm, String input, Key key, int maxEncryptSize) throws Exception {
        // 获取Cipher对象
        Cipher cipher = Cipher.getInstance(algorithm);
        // 初始化模式(加密)和密钥
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 将原文转为byte数组
        byte[] data = input.getBytes();
        // 总数据长度
        int total = data.length;
        // 输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        decodeByte(maxEncryptSize, cipher, data, total, baos);
        // 对密文进行Base64编码
        return Base64.encodeBase64String(baos.toByteArray());

    }

    /**
     * 解密数据
     *
     * @param algorithm      : 算法
     * @param encrypted      : 密文
     * @param key            : 密钥
     * @param maxDecryptSize : 最大解密长度(需要根据实际情况进行调整)
     * @return : 原文
     * @throws Exception
     */
    private static String decrypt(String algorithm, String encrypted, Key key, int maxDecryptSize) throws Exception {
        // 获取Cipher对象
        Cipher cipher = Cipher.getInstance(algorithm);
        // 初始化模式(解密)和密钥
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 由于密文进行了Base64编码, 在这里需要进行解码
        byte[] data = Base64.decodeBase64(encrypted);
        // 总数据长度
        int total = data.length;
        // 输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        decodeByte(maxDecryptSize, cipher, data, total, baos);
        // 输出原文
        return baos.toString();

    }

    /**
     * 分段处理数据
     *
     * @param maxSize : 最大处理能力
     * @param cipher  : Cipher对象
     * @param data    : 要处理的byte数组
     * @param total   : 总数据长度
     * @param baos    : 输出流
     * @throws Exception
     */
    private static void decodeByte(int maxSize, Cipher cipher, byte[] data, int total, ByteArrayOutputStream baos) throws Exception {
        // 偏移量
        int offset = 0;
        // 缓冲区
        byte[] buffer;
        // 如果数据没有处理完, 就一直继续
        while (total - offset > 0) {
            // 如果剩余的数据 >= 最大处理能力, 就按照最大处理能力来加密数据
            if (total - offset >= maxSize) {
                // 加密数据
                buffer = cipher.doFinal(data, offset, maxSize);
                // 偏移量向右侧偏移最大数据能力个
                offset += maxSize;
            } else {
                // 如果剩余的数据 < 最大处理能力, 就按照剩余的个数来加密数据
                buffer = cipher.doFinal(data, offset, total - offset);
                // 偏移量设置为总数据长度, 这样可以跳出循环
                offset = total;
            }
            // 向输出流写入数据
            baos.write(buffer);
        }
    }
}