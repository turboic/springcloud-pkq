package com.turboic.cloud.service.impl;

/**
 * @author liebe
 * redis操作接口
 */
public interface RedisService {
    String redisString(String key,String value);
}
