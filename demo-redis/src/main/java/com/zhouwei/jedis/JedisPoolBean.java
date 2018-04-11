package com.zhouwei.jedis;

public class JedisPoolBean {
    /**
     * 资源池中最大连接数
     */
    private int maxTotal=8;
    /**
     * 资源池允许最大空闲的连接数
     */
    private int maxIdle=8;
    /**
     *  资源池确保最少空闲的连接数
     */
    private int minIdle=0;
    /**
     * 当资源池连接用尽后，调用者的最大等待时间(单位为毫秒),-1：表示永不超时
     */
    private long maxWaitMillis=-1L;
    /**
     * 当资源池用尽后，调用者是否要等待.只有当为true时，maxWaitMillis才会生效
     */
    private boolean blockWhenExhausted=true;
    /**
     * 向资源池创建连接时是否做连接有效性检测(ping)，无效连接会被移除
     */
    private boolean testOnCreate = false;
    /**
     *  向资源池借用连接时是否做连接有效性检测(ping)，无效连接会被移除
     */
    private boolean testOnBorrow=false;
    /**
     * 向资源池归还连接时是否做连接有效性检测(ping)，无效连接会被移除
     */
    private boolean testOnReturn=false;
    /**
     * 是否开启空闲资源监测
     */
    private boolean  testWhileIdle=false;
    /**
     * 做空闲资源检测时，每次的采样数
     */
    private int numTestsPerEvictionRun=3;

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public boolean isBlockWhenExhausted() {
        return blockWhenExhausted;
    }

    public void setBlockWhenExhausted(boolean blockWhenExhausted) {
        this.blockWhenExhausted = blockWhenExhausted;
    }

    public boolean isTestOnCreate() {
        return testOnCreate;
    }

    public void setTestOnCreate(boolean testOnCreate) {
        this.testOnCreate = testOnCreate;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }
}
