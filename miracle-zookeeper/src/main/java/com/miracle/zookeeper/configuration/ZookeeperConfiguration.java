package com.miracle.zookeeper.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Zookeeper配置类
 */
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperConfiguration {

    // Zookeeper服务器地址
    private String host;

    // Zookeeper连接超时时间
    private int timeout = 3000;

    // ZookeeperWatch事件超时时间
    private int watchTimeout = 2000;

    // Zookeeper临时节点锁存放目录
    private String disLockPath = "/DIS_LOCK";

    // Zookeeper临时节点前缀
    private String disNodePrefix = "/dis_";

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getWatchTimeout() {
        return watchTimeout;
    }

    public void setWatchTimeout(int watchTimeout) {
        this.watchTimeout = watchTimeout;
    }

    public String getDisLockPath() {
        return disLockPath;
    }

    public void setDisLockPath(String disLockPath) {
        this.disLockPath = disLockPath;
    }

    public String getDisNodePrefix() {
        return disNodePrefix;
    }

    public void setDisNodePrefix(String disNodePrefix) {
        this.disNodePrefix = disNodePrefix;
    }
}
