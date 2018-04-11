package com.zhouwei.jedis;

import java.util.List;
import java.util.Properties;

public class JedisSentinelYamlBean  extends JedisYamlBean {
    private String masterName;
    private String password;

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
