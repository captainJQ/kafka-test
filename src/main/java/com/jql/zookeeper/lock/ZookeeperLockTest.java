package com.jql.zookeeper.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

public class ZookeeperLockTest {
    private static int count = 0;

    @Test
    public void lockTest() throws Exception {
        for (int i = 0; i < 100; i++) {

            new Thread(()->{
                //创建zookeeper的客户端
                RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
                CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
                client.start();

                //创建分布式锁, 锁空间的根节点路径为/curator/lock
                InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock");

                try {
                    mutex.acquire();
                    //获得了锁, 进行业务流程
                    for (int j = 0; j < 100; j++) {
                        System.out.println(Thread.currentThread().getName()+"\t"+count++);
                    }
                    //完成业务流程, 释放锁
                    mutex.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //关闭客户端
                client.close();
            }).start();

        }

        Thread.sleep(100*1001);

    }

    @Test
    public void lockMany(){
        //创建zookeeper的客户端
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        client.start();

        //创建分布式锁, 锁空间的根节点路径为/curator/lock
        InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock");

        try {
            mutex.acquire();
            mutex.acquire();
            mutex.acquire();
            mutex.acquire();
            //获得了锁, 进行业务流程
            System.out.println("Enter mutex");
//            Thread.sleep(10*1000);
            //完成业务流程, 释放锁
            mutex.release();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //关闭客户端
//        client.close();
    }


}
