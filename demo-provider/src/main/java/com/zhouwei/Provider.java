/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhouwei;

import com.zhouwei.jedis.JedisConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashMap;
import java.util.UUID;

public class Provider {

    public static void main(String[] args) throws Exception {
        //Prevent to get IPV6 address,this way only work in debug mode
        //But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-demo-provider.xml"});
        context.start();

//        ApplicationContext context1=new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-demo-provider.xml"});
//        ApplicationContext context = new AnnotationConfigApplicationContext(JedisConfig.class);
        Jedis jedis = (Jedis) context.getBean("jedisSentinel");
        JedisCluster jedisCluster = (JedisCluster) context.getBean("jedisCluster");
        while(true){
            try{
                String key=System.currentTimeMillis()+"";
                String value=createUUID();
                jedis.set("N"+key,"S"+value);
                jedis.hset(key,"F"+key,"H"+value);
                System.out.println("jedis:"+jedis.get("N"+key));
                System.out.println("jedis:"+jedis.hget(key,"F"+key));

                jedisCluster.set("{user001}"+key,"S"+value);
                jedisCluster.hset("{map001}"+key,"F"+key,"H"+value);
                jedisCluster.mset("{s001}:a"+key,"a"+value,"{s001}:b"+key,"b"+value);
                System.out.println("jedisCluster:"+jedisCluster.get("{user001}"+key));
                System.out.println("jedisCluster:"+jedisCluster.hget("{map001}"+key,"F"+key));
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

//        System.in.read(); // press any key to exit
    }

    private static String createUUID(){
       String uuid= UUID.randomUUID().toString();
       StringBuilder sb=new StringBuilder();
       sb.append(uuid.substring(0,8));
        sb.append(uuid.substring(9,13));
        sb.append(uuid.substring(14,18));
        sb.append(uuid.substring(19,23));
        sb.append(uuid.substring(24));
        return sb.toString();
    }
}
