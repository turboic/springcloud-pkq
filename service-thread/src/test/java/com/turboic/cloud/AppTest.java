package com.turboic.cloud;
import com.turboic.cloud.lock.ZookeeperDistributedLock;
import org.apache.zookeeper.KeeperException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    private Logger logger = LoggerFactory.getLogger(AppTest.class);
    /**
     * Connection request from old client /127.0.0.1:59984; will be dropped if server is in r-o mode
     * Rigorous Test :-)
     */

    String lockName = "test";
    String zkUrl = "127.0.0.1:2181";
    @Test
    public void test() {
        ZookeeperDistributedLock lock = ZookeeperDistributedLock.create(lockName,zkUrl);
        int clientCount = 5;
        CountDownLatch countDownLatch = new CountDownLatch(clientCount);
        ExecutorService executorService = Executors.newFixedThreadPool(clientCount);
        long start = System.currentTimeMillis();
        int count = 0;
        for (int i = 0; i < clientCount; i++) {
            int finalI = i;
            logger.info("开始执行第:{}个线程",i);
            executorService.execute(() -> {
                long timeout = 6000;
                try {
                    if(lock.tryLock(timeout)){
                        logger.info("开始执行第:{}个线程加锁",finalI);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                        String value = sdf.format(new Date()) + "_" + finalI;
                        System.err.println("生成信息:" + value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.unlock();
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info("完成执行第:{}个线程解锁",finalI);
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
        System.err.println("执行线程数:{},总耗时:{},count数为:{}" + clientCount + (end - start) + ":" + count);
    }


}
