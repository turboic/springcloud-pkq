package com.turboic.cloud;

import static org.junit.Assert.assertTrue;

import com.turboic.cloud.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AppTest
{
    @Autowired
    private RedisUtil redisUtil;

    //300秒
    private final static long defaultExpireTime = 3000;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
        List<String> list = new ArrayList<>();
        list.add("穿搭");
        list.add("旅行");
        list.add("搞笑");
        list.add("萌宠");
        list.add("游戏");
        list.add("音乐");
        list.add("护肤");
        list.add("影视综艺");
        list.add("科技数码");
        list.add("摄影");
        list.add("运动健身");
        list.add("发型");
        list.add("美食");
        redisUtil.redisPersistListAndExpire("list",list,defaultExpireTime);


        Map<String,Object> hash = new HashMap<>();
        hash.put("android","小红书App");
        hash.put("ios","微信IOS版本");
        redisUtil.redisPersistHashSetAndExpire("hash",hash,defaultExpireTime);


        Object [] set = new Object[]{ "北京","上海","广州","深圳"};

        redisUtil.redisPersistSetAndExpire("set",defaultExpireTime,set);

        redisUtil.redisPersistStringAndExpire("string","测试",defaultExpireTime);


        Set<ZSetOperations.TypedTuple<Integer>> orderSet = new HashSet<>();
        ZSetOperations.TypedTuple<Integer> o1 = new DefaultTypedTuple(100,5.0);
        ZSetOperations.TypedTuple<Integer> o2 = new DefaultTypedTuple(22,2.0);
        ZSetOperations.TypedTuple<Integer> o3 = new DefaultTypedTuple(250,3.0);
        orderSet.add(o1);
        orderSet.add(o2);
        orderSet.add(o3);
        redisUtil.redisPersistOrderSetAndExpire("zset",orderSet,defaultExpireTime);



    }
}
