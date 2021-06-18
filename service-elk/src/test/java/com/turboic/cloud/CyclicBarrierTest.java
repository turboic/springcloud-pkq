package com.turboic.cloud;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {

    @Test
    public void test(){
        CyclicBarrier barrier = new CyclicBarrier(6, () -> {
            System.out.println("今天晚上有6个菜！！！！！");
        });
        System.out.println("开始上菜，全场欢呼............");
        ExecutorService service = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 6; i++) {
            service.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 菜，端上桌啦。");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + "  菜上了，开吃。。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
