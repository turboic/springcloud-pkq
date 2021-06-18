package com.turboic.cloud.util;

import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author liebe
 */
public class Base64Utils {
    public static String convertImageToBase64(String imagePath) {
        byte[] data = null;
        try {
            InputStream in = new FileInputStream(imagePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Str = encoder.encode(data);
        return "data:image/png;base64,"+base64Str;
    }

    public static void main(String[] args) {
        String path = "C:\\Users\\liebe\\Desktop\\2.jpg";
        String s = convertImageToBase64(path);
        System.err.println(s);
    }
}
