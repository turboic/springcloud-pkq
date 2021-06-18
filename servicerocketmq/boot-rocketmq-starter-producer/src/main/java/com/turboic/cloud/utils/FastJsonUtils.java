package com.turboic.cloud.utils;
import com.alibaba.fastjson.JSON;


/**
 * @author liebe
 */
public class FastJsonUtils   {
    public static <T> T jsonToObject(String text, Class<T> clazz){
        return JSON.parseObject(text,clazz);
    }


    public static String objectToJson(Object object){
        return JSON.toJSONString(object);
    }




}
