package com.turboic.cloud.util;

import java.util.UUID;

/**
 * @author liebe
 */
public class CookieUtil {
    public static String instance(){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-","");
        uuid = uuid.toUpperCase();
        return uuid;
    }

    public static void main(String [] args){
        System.err.println("9D716C30C62BC6A5739101233334F36C");
        System.err.println(instance());
    }
}
