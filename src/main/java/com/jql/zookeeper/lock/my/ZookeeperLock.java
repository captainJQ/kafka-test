package com.jql.zookeeper.lock.my;

import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class ZookeeperLock {
    private static final byte[] EMPTY_BYTE = new byte[0];
    private static ZooKeeper zkClient;
    private static Lock lock = new ReentrantLock();
    private static ThreadLocal<LockData> lockData = new ThreadLocal<>();

    public static class LockData{
        AtomicInteger account = new AtomicInteger(1);
        String lockPath = null;
    }

    static{
        if(zkClient==null){
            lock.lock();
            try {
                if(zkClient==null) {
                    zkClient = new ZooKeeper("127.0.0.1", 30000, null);
                }
            } catch (IOException e) {
                   e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }
        try{
            if(zkClient.exists("/lock",null)==null){
                zkClient.create("/lock",EMPTY_BYTE, Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static boolean lock(String lockName) throws KeeperException, InterruptedException {

        if(lockData.get()!=null&&lockData.get().account.intValue()>0){
            lockData.get().account.incrementAndGet();
            return true;
        }
        String path = createNode(lockName);
        if(path!=null){
            if(lockData.get()==null) {
                lockData.set(new LockData());
            }else{
                lockData.get().account.incrementAndGet();
            }
            lockData.get().lockPath = path;
            return true;
        }
        return  false;
    }

    public static boolean unlock() throws KeeperException, InterruptedException {
        if(lockData.get()!=null&&lockData.get().account!=null&&lockData.get().account.intValue()>1){
            lockData.get().account.decrementAndGet();
            return true;
        }else if(lockData.get()!=null&&lockData.get().account!=null&&lockData.get().account.intValue()==1){
            zkClient.delete(lockData.get().lockPath,-1);
            lockData.get().account.decrementAndGet();
            return true;
        }
        return false;
    }

    private static String createNode(String lockName) throws KeeperException, InterruptedException {
        String rootPath = "/lock/"+lockName;
        if(zkClient.exists(rootPath,false)==null){
            zkClient.create(rootPath,EMPTY_BYTE,Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        }
        String path = zkClient.create(rootPath+"/"+UUID.randomUUID().toString(), EMPTY_BYTE, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        List<String> children = zkClient.getChildren(rootPath, null);
        String node = path.substring(path.lastIndexOf("/")+1);
        String last = getLast(node, children);
        final Object flag = new Object();
        if(last==null){
            return path;
        }
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if(event.getType() == Event.EventType.NodeDeleted){
                    synchronized (flag){
                        flag.notify();
                    }
                }
            }
        };
        zkClient.exists(rootPath+"/"+last,watcher);
        synchronized (flag) {
            flag.wait();
        }
        return path;
    }

    private static String getLast(String current,List<String> brothers){

        List<String> sortBrothers = brothers.stream().sorted(Comparator.comparingInt(value -> Integer.valueOf(value.substring(36)))).collect(Collectors.toList());
        int index = sortBrothers.indexOf(current);
        System.out.println(index+"\t"+sortBrothers.get(index));
        if(index<1){
            return null;
        }
        return sortBrothers.get(index-1);
    }

}
