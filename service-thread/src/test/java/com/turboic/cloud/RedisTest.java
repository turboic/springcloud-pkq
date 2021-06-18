package com.turboic.cloud;

import com.turboic.cloud.redis.RedisTool;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Unit test for simple App.
 */
public class RedisTest
{
    /**
     * Connection request from old client /127.0.0.1:59984; will be dropped if server is in r-o mode
     * Rigorous Test :-)
     */
    private RedisTool redisTool = new RedisTool();
    @Test
    public void test()
    {

        int expireTime = 5000;
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.select(2);
        int clientCount =3;
        CountDownLatch countDownLatch = new CountDownLatch(clientCount);

        ExecutorService executorService = Executors.newFixedThreadPool(clientCount);
        long start = System.currentTimeMillis();
        int count = 0;
        for (int i = 0;i<clientCount;i++){

            executorService.execute(() -> {
                String requestId = UUID.randomUUID().toString();
                String lockKey = "1024122298";
                try {
                    boolean result = RedisTool.tryGetDistributedLock(jedis,lockKey,requestId,expireTime);
                    System.err.println("加锁成功"+result);
                    if(result){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                        String value = sdf.format(new Date()) + "_" + System.currentTimeMillis();
                        System.err.println("redis生成信息:"+value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    boolean release = RedisTool.releaseDistributedLock(jedis,lockKey,requestId);
                    System.err.println("解锁成功"+release);
                }
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.err.println("执行线程数:{},总耗时:{},count数为:{}"+clientCount+(end-start)+":"+count);
    }
}
