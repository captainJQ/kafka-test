package com.jql.zookeeper.lock.test;

import java.io.IOException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ZookeeperTest {
    /**
     * 会话超时时间，设置与系统默认时间一致
     */
    private final int SESSION_TIMEOUT = 30000;
    //创建ZooKeeper实例
    ZooKeeper zk;
    /*Watcher watcher = new Watcher() {
        public void process(WatchedEvent event) {
            System.out.println(event.toString());
        }
    };*/

    Watcher watcher = (event) -> System.out.println(event.toString());

    //初始化ZooKeeper实例
    @Before
    public void before() throws IOException {
        zk = new ZooKeeper("127.0.0.1:2181", SESSION_TIMEOUT, watcher);
    }

    @After
    public void after() throws InterruptedException {
//        zk.close();
    }

    @Test
    public void createPersistent() throws KeeperException, InterruptedException {
        //在创建节点的时候，需要提供节点的名称、数据、权限以及节点类型
        System.out.println("1.创建ZooKeeper节点(znode:zoo2,数据:myData2,权限:OPEN_ACL_UNSAFE,节点类型:Persistent)");
        zk.create("/test/count", "0".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void createPersistentSequential() throws KeeperException, InterruptedException {
        //在创建节点的时候，需要提供节点的名称、数据、权限以及节点类型
        System.out.println("1.创建ZooKeeper节点(znode:zoo2,数据:myData2,权限:OPEN_ACL_UNSAFE,节点类型:Persistent)");
        zk.create("/test", "myData2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
    }

    @Test
    public void createEphemeral() throws KeeperException, InterruptedException {
        //在创建节点的时候，需要提供节点的名称、数据、权限以及节点类型
        System.out.println("1.创建ZooKeeper节点(znode:zoo2,数据:myData2,权限:OPEN_ACL_UNSAFE,节点类型:Persistent)");
        zk.create("/test/ephemeral", "ephemeral".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    @Test
    public void createEphemeralSequential() throws KeeperException, InterruptedException {
        //在创建节点的时候，需要提供节点的名称、数据、权限以及节点类型
        System.out.println("1.创建ZooKeeper节点(znode:zoo2,数据:myData2,权限:OPEN_ACL_UNSAFE,节点类型:Persistent)");
        zk.create("/test/ephemeral", "ephemeral".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    @Test
    public void getData() throws KeeperException, InterruptedException {
        System.out.println("2.查看是否创建成功:");
        System.out.println(new String(zk.getData("/test", false, null)));
    }

    @Test
    public void setData() throws KeeperException, InterruptedException {
        System.out.println("3.修改节点数据:");
        zk.setData("/test", "xiugai1234".getBytes(), -1);
    }

    @Test
    public void getSetData() throws KeeperException, InterruptedException {
        System.out.println("4.查看是否修改成功:");
        System.out.println(new String(zk.getData("/test", false, null)));
    }

    @Test
    public void delete() throws InterruptedException, KeeperException {
        System.out.println("5.删除节点");
        zk.delete("/test", -1);
    }

    @Test
    public void getStat() throws KeeperException, InterruptedException {
        //使用 exists函数时，如果节点不存在将返回一个nul值
        System.out.println("6.查看节点是否被删除:");
        System.out.println("节点状态:[" + zk.exists("/test/liyan", false) + "]");
    }

    @Test
    public void test11111(){
        System.out.println(37015799/1024.0/1024.0);
    }
}
