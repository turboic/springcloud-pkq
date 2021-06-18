package com.turboic.cloud;

import com.turboic.cloud.lock.ZookeeperDistributedLock;
import org.apache.zookeeper.KeeperException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestMain {
    String readFile = "D:\\log.1";

    String writeFile = "D:\\log.1";

    String zkUrl = "127.0.0.1:2181";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        TestMain testMain = new TestMain();
        testMain.testWriteFile();
        long end = System.currentTimeMillis();
        System.err.println(end-start);

    }

    public void testWriteFile() {
        ExecutorService executorService = Executors.newFixedThreadPool(500);
        ExecutorService executorService2 = Executors.newFixedThreadPool(500);
        executorService.execute(() -> {
            try {
                for (int i = 0; i < 100000; i++) {
                    ZookeeperDistributedLock lock = ZookeeperDistributedLock.create("file", zkUrl);
                    lock.lock();
                    addFile(readFile, writeFile);
                    lock.unlock();
                }
            } catch (KeeperException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        executorService2.execute(() -> {
            try {
                for (int i = 0; i < 100000; i++) {
                    ZookeeperDistributedLock lock = ZookeeperDistributedLock.create("file", zkUrl);
                    lock.lock();
                    addFile(readFile, writeFile);
                    lock.unlock();
                }
            } catch (KeeperException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
    }




    private void addFile(String readFile,String writeFile) throws IOException {
        FileReader fr = new FileReader(readFile);
        BufferedReader br = new BufferedReader(fr);
        String s = br.readLine();
        br.close();
        FileWriter fw = new FileWriter(writeFile);
        int res = Integer.parseInt(s) + 1;
        fw.write(res+"");
        fw.flush();
        fw.close();
        System.err.println("文件操作"+res);
    }
}
