package com.turboic.cloud.service.impl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author liebe
 * redis操作接口
 */
@Service
public class RedisServiceImpl implements RedisService{

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String redisString(String key,String value){
        redisTemplate.opsForValue().set(key,value);
        return key+":"+value+"保存成功";
    }


}
