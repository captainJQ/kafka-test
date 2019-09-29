package com.jql.zookeeper.lock.my;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;

public class MyLockTest {
    public static int count = 0;
    @Test
    public void test1() throws Exception {
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    Thread.sleep(10);
                    ZookeeperLock.lock("test");
                        inc();
                    ZookeeperLock.unlock();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(1000*30);
        System.out.println(count);
    }

    @Test
    public void test2(){
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                for (int j = 0; j < 100; j++) {
                    System.out.println(count++);
                }
            }).start();
        }
    }

    ZooKeeper zkClient = null;
    {
        try {
            zkClient = new ZooKeeper("127.0.0.1:2181",3000,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void inc() throws Exception {
        byte[] data = zkClient.getData("/test/count", null, null);
        String s = new String(data);
        Integer c = Integer.valueOf(s);
        c++;
        zkClient.setData("/test/count",c.toString().getBytes(),-1);
    }
}
