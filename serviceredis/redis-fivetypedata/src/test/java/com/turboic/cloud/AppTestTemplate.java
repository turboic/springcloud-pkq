package com.turboic.cloud;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTestTemplate
{
    private final static Logger log = LoggerFactory.getLogger(AppTestTemplate.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testReturnValue()
    {
        String key = "k1";
        String value = "v1";
        //设置过期时间5*60
        int seconds = 5*60;
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
        log.info("key={},value={},seconds={}",key,value,seconds);


        String key2 = "k2";
        String value2 = "v2";

        this.redisTemplate.opsForValue().set(key2, value2);
        Boolean b = this.redisTemplate.expire(key2,seconds,TimeUnit.SECONDS);


        log.info("key2={},value2={},seconds={},boolean={}",key,value,seconds,b);


    }
}
