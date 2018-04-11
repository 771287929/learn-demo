package com.zhouwei.jedis;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.yaml.snakeyaml.Yaml;
import redis.clients.jedis.*;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

public class JedisUtil {

    /**
     * 获取sentinel模式yaml配置redis连接池信息
     *
     * @param inputStream
     * @return
     */
    private static JedisSentinelYamlBean getJedisSentinelYamlBean(InputStream inputStream) {
        JedisSentinelYamlBean jedisSentinelYamlBean = (new Yaml()).loadAs(inputStream, JedisSentinelYamlBean.class);
        if (jedisSentinelYamlBean == null) {
            throw new RuntimeException("Jedis does not properly configure yaml files.");
        }
        return jedisSentinelYamlBean;
    }

    /**
     * 获取cluster模式yaml配置redis连接池信息
     *
     * @param inputStream
     * @return
     */
    private static JedisClusterYamlBean getJedisClusterYamlBean(InputStream inputStream) {
        JedisClusterYamlBean jedisClusterYamlBean = (new Yaml()).loadAs(inputStream, JedisClusterYamlBean.class);
        if (jedisClusterYamlBean == null) {
            throw new RuntimeException("Jedis does not properly configure yaml files.");
        }
        return jedisClusterYamlBean;
    }

    /**
     * 获取yaml配置文件 redis连接池配置信息
     *
     * @param jedisPoolBean
     * @return
     */
    private static JedisPoolConfig getJedisPoolConfig(JedisPoolBean jedisPoolBean) {
        String yaml = (new Yaml()).dumpAsMap(jedisPoolBean);
        JedisPoolConfig jedisPoolConfig = (new Yaml()).loadAs(yaml, JedisPoolConfig.class);
        if (jedisPoolConfig == null) {
            throw new RuntimeException("Jedis yaml configuration file jedisPoolBean attribute error.");
        }
        return jedisPoolConfig;
    }

    /**
     * 获取sentinel连接池
     *
     * @param jedisSentinelYamlBean
     * @return
     */
    private static JedisSentinelPool getJedisSentinelPool(JedisSentinelYamlBean jedisSentinelYamlBean){
        Set<String> sentinels=new HashSet<String>();
        List<Properties> jedisClusterNode = jedisSentinelYamlBean.getJedisClusterNode();
        if(CollectionUtils.isEmpty(jedisClusterNode)){
            throw new RuntimeException("Jedis yaml configuration file jedisClusterNode attribute error.");
        }else{
            jedisClusterNode.forEach(p->sentinels.add(p.getProperty("host")+":"+p.get("port")));
        }
        JedisPoolConfig jedisPoolConfig=getJedisPoolConfig(jedisSentinelYamlBean.getJedisPoolBean());

        Integer timeOut=jedisSentinelYamlBean.timeout==null?2000:jedisSentinelYamlBean.timeout;
        JedisSentinelPool jedisSentinelPool=new JedisSentinelPool(jedisSentinelYamlBean.getMasterName(),sentinels,jedisPoolConfig,timeOut,jedisSentinelYamlBean.getPassword());
        return jedisSentinelPool;
    }

    /**
     * 获取JedisCluster
     * @param jedisClusterYamlBean
     * @return
     */
    private static JedisCluster jedisCluster(JedisClusterYamlBean jedisClusterYamlBean) {
        Set<HostAndPort> clusterNodes=new HashSet<HostAndPort>();
        List<Properties> jedisClusterNode=jedisClusterYamlBean.getJedisClusterNode();
        if(CollectionUtils.isEmpty(jedisClusterNode)){
            throw new RuntimeException("Jedis yaml configuration file jedisClusterNode attribute error.");
        }else{
            jedisClusterNode.forEach(p->clusterNodes.add(new HostAndPort(p.getProperty("host"),Integer.valueOf(p.get("port").toString()))));
        }
        int timeout=jedisClusterYamlBean.timeout==null?2000:jedisClusterYamlBean.timeout;
        int maxAttempts=jedisClusterYamlBean.getMaxAttempts()==null?5:jedisClusterYamlBean.getMaxAttempts();
        JedisPoolConfig jedisPoolConfig=getJedisPoolConfig(jedisClusterYamlBean.getJedisPoolBean());
        JedisCluster jedisCluster=new JedisCluster(clusterNodes,timeout,maxAttempts,jedisPoolConfig);
        return jedisCluster;
    }

    /**
     * 获取jedisCluster
     * @param inputStream
     * @return
     */
    public static JedisCluster buildJedisCluster(InputStream inputStream){
        JedisClusterYamlBean jedisClusterYamlBean=getJedisClusterYamlBean(inputStream);
        JedisCluster jedisCluster=jedisCluster(jedisClusterYamlBean);
        return jedisCluster;
    }


    /**
     * 构建代理
     * @param inputStream
     * @return
     */
    public static Jedis buildJedisSentinelProxy(InputStream inputStream) {
        JedisSentinelYamlBean jedisSentinelYamlBean=getJedisSentinelYamlBean(inputStream);
        JedisSentinelPool jedisSentinelPool= getJedisSentinelPool(jedisSentinelYamlBean);
        return buildProxyJedis(jedisSentinelPool);
    }

    /**
     * cglib代理jedis
     * @param jedisSentinelPool
     * @return
     */
    private static Jedis buildProxyJedis(JedisSentinelPool jedisSentinelPool){
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(Jedis.class);//设置代理目标
        enhancer.setCallback(new MethodInterceptor(){
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                Jedis jedis = jedisSentinelPool.getResource();
                Object obj;
                try{
                    obj=method.invoke(jedis,args);
                }finally {
                    jedis.close();
                }
                return obj;
            }
        });
        enhancer.setClassLoader(Jedis.class.getClassLoader());
        return (Jedis) enhancer.create();
    }

}
