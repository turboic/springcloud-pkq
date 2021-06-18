package com.turboic.cloud.util;

import com.alibaba.fastjson.JSON;

public class FastJsonUtils {
    public static String objectToJson(Object object){
        return JSON.toJSONString(object);
    }

    public static Object jsonToObject(String json,Class cls){
        return JSON.parseObject(json,cls);
    }
}
