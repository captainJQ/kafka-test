package com.jql.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RedisTest {

    Jedis jedis = null;

    @Before
    public void before(){
        jedis = JedisClientPool.getJedis();
    }

    @Test
    public void test(){

        String name = jedis.get("name");
        System.out.println(name);

    }


    @Test
    public void test1(){

        Map<String,String> map = new HashMap<>();

/*        map.put("name","liyan");
        map.put("age",22+"");*/
        map.put("height",165+"cm");
        map.put("weight",50+"kg");

        String result = jedis.hmset("people",map);
        System.out.println(result);

    }

    @Test
    public void test2(){
        Map<String, String> people = jedis.hgetAll("people");
        System.out.println(people);

    }

    @Test
    public void test3(){
        jedis.zadd("hhh",22,"liyan");
        jedis.zadd("hhh",20,"zhumeiling");
        jedis.zadd("hhh",21,"noone");
        Set<String> hhh = jedis.zrange("hhh", 0, -1);
        System.out.println(hhh);
    }

    @Test
    public void test4() throws InterruptedException {
        jedis.set("expire_test","5");
        jedis.expire("expire_test",5);
        for (int i = 0; i <=5; i++) {
            Thread.sleep(1000);
            System.out.println(i+"s\texpire_test is expire:["+jedis.exists("expire_test")+"]");
        }
    }

    /**
     * list lpush 创建list
     */
    @Test
    public void test5(){

//        jedis.rpush("list_test","liyan");
        String list_test = jedis.lindex("list_test", 0);

        System.out.println(jedis.lrange("list_test",0,-1));

    }

    @Test
    public void test6(){
        jedis.sadd("set_test","liyan","liyang","noone","zhumeiling","liyang");
        System.out.println(jedis.smembers("set_test"));

        System.out.println(jedis.spop("set_test"));

        System.out.println(jedis.smembers("set_test"));
    }




    @After
    public void after(){
        jedis.close();
    }

}
