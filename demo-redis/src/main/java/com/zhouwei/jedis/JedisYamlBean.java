package com.zhouwei.jedis;

import java.util.List;
import java.util.Properties;

public class JedisYamlBean {
    protected JedisPoolBean jedisPoolBean;
    protected List<Properties> jedisClusterNode;
    protected Integer timeout;

    public JedisPoolBean getJedisPoolBean() {
        return jedisPoolBean;
    }

    public void setJedisPoolBean(JedisPoolBean jedisPoolBean) {
        this.jedisPoolBean = jedisPoolBean;
    }

    public List<Properties> getJedisClusterNode() {
        return jedisClusterNode;
    }

    public void setJedisClusterNode(List<Properties> jedisClusterNode) {
        this.jedisClusterNode = jedisClusterNode;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
