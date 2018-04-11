package com.zhouwei;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

public class JedisTest {
    @Resource
    private Jedis jedis;

    protected Set<String> sentinels = new HashSet<String>();

    public void setUp() throws Exception {
        sentinels.add("192.168.32.138:26379");
        sentinels.add("192.168.32.138:26380");
        sentinels.add("192.168.32.138:26381");
    }

    @Test
    public void exceuteRedis(){
        jedis.set("person","人");
    }
}
