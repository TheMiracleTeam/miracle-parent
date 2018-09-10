package com.miracle.common.util;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Zookeeper工具类
 * Created at 2018-08-25 16:45:28
 * @author Allen
 */
@Component
public class ZookeeperUtil {

    // Zookeeper操作客户端
    private static ZkClient zkClient;
    // Zookeeper服务器地址
    private static String HOST;
    // Zookeeper超时时间
    private static int TIMEOUT;

    /**
     * 获取Zookeeper操作客户端实例
     * @param host Zookeeper地址
     * @param timeout Zookeeper超时时间
     * @return ZkClient Zookeeper操作客户端
     */
    public static ZkClient getZkClient(String host, int timeout) {
        if (null == zkClient) { // 提高效率
            synchronized (ZookeeperUtil.class) {
                if (null == zkClient) { // 线程安全
                    zkClient = new ZkClient(host, timeout);
                    zkClient.setZkSerializer(new MyZkSerializer());
                }
            }
        }
        return zkClient;
    }

    /**
     * 获取Zookeeper操作客户端实例
     * @return ZkClient= Zookeeper操作客户端
     */
    public static ZkClient getZkClient() {
        return getZkClient(HOST, TIMEOUT);
    }

    @Value("${zookeeper.host}")
    public void setHost(String host) {
        HOST = host;
    }

    @Value("${zookeeper.timeout}")
    public void setTimeout(int timeout) {
        TIMEOUT = timeout;
    }

    /**
     * 自定义Zookeeper序列化器
     */
    static class MyZkSerializer implements ZkSerializer {

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
