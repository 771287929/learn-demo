package com.zhouwei.jedis;

public class JedisClusterYamlBean extends JedisYamlBean {
    private Integer maxAttempts;

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }
}
