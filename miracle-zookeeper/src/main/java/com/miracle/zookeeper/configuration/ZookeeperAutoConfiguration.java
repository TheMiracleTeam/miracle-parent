package com.miracle.zookeeper.configuration;

import com.miracle.common.serializer.ZkSerializer;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Zookeeper自动配置类
 * Created at 2018-09-26 14:55:00
 * @author Allen
 */
@Configuration
@EnableConfigurationProperties(ZookeeperConfiguration.class)
public class ZookeeperAutoConfiguration {

    @Autowired
    private ZookeeperConfiguration configuration;

    /**
     * ZkClient Bean注册
     * @return ZkClient
     */
    @Bean
    public ZkClient zkClient() {
        if (configuration.getHost() == null || "".equals(configuration.getHost())) {
            System.out.println("未配置Zookeeper服务器。(zookeeper.host)");
            return null;
        }
        ZkClient zkClient = new ZkClient(configuration.getHost(), configuration.getWatchTimeout());
        zkClient.setZkSerializer(new ZkSerializer());
        return zkClient;
    }
}
