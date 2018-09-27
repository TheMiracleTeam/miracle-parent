package com.miracle.zookeeper.configuration;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

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
        zkClient.setZkSerializer(new MyZkSerializer());
        return zkClient;
    }

    /**
     * 自定义Zookeeper序列化器
     */
    class MyZkSerializer implements ZkSerializer {

        /**
         * 序列化
         * @param obj 数据
         * @return byte[] 字节数组
         * @throws ZkMarshallingError Zookeeper序列化异常
         */
        @Override
        public byte[] serialize(Object obj) throws ZkMarshallingError {
            return String.valueOf(obj).getBytes(StandardCharsets.UTF_8);
        }

        /**
         * 反序列化
         * @param bytes 字节数组
         * @return Object 原数据
         * @throws ZkMarshallingError Zookeeper序列化异常
         */
        @Override
        public Object deserialize(byte[] bytes) throws ZkMarshallingError {
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }
}
