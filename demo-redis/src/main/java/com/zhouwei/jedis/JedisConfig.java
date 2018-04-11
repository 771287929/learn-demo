package com.zhouwei.jedis;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class JedisConfig extends PathMatchingResourcePatternResolver implements ApplicationContextAware {
    private Logger logger=LoggerFactory.getLogger(JedisConfig.class);
    private ConfigurableApplicationContext applicationContext;

    @Bean(destroyMethod = "close",name = {"jedisCluster"})
    public JedisCluster jedisCluster(){
        JedisCluster jedisCluster=null;
        Resource[] resources=null;
        try {
            resources = this.getResources("classpath*:/META-INF/jedis-cluster*.yaml");
            if(ArrayUtils.isNotEmpty(resources)){
                InputStream input=resources[0].getInputStream();
                jedisCluster=JedisUtil.buildJedisCluster(input);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return jedisCluster;
        }

        return jedisCluster;
    }

    @Bean(destroyMethod = "close",name = {"jedisSentinel"})
    public Jedis jedisSentinel(){
        Jedis jedis=null;
        Resource[] resources=null;
        try {
            resources = this.getResources("classpath*:/META-INF/jedis-sentinel*.yaml");
            if(ArrayUtils.isNotEmpty(resources)){
                InputStream input=resources[0].getInputStream();
                jedis=JedisUtil.buildJedisSentinelProxy(input);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return jedis;
        }

        return jedis;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext= (ConfigurableApplicationContext) applicationContext;
    }
}
