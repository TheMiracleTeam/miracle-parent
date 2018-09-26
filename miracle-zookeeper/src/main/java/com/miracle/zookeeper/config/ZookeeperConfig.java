package com.miracle.zookeeper.config;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

/**
 * Zookeeper配置类
 * Created at 2018-09-26 14:55:00
 * @author Allen
 */
@Configuration
public class ZookeeperConfig {

    @Value("${zookeeper.host}")
    private String host;

    @Value("${zookeeper.timeout:3000}")
    private int timeout;

    @Bean
    public ZkClient zkClient() {
        ZkClient zkClient = new ZkClient(host, timeout);
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
