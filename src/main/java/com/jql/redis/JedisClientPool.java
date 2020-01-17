package com.jql.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisClientPool {

    private static JedisPool pool = new JedisPool("47.100.77.66",32769);

    public static Jedis getJedis(){
        Jedis resource = pool.getResource();
        resource.auth("root@redis");
        return resource;
    }


}
