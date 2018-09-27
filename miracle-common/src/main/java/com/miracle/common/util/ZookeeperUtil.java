package com.miracle.common.util;

import com.miracle.common.serializer.ZkSerializer;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    private ZookeeperUtil() {}

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
                    zkClient.setZkSerializer(new ZkSerializer());
                }
            }
        }
        return zkClient;
    }

    /**
     * 获取Zookeeper操作客户端实例
     * @return ZkClient Zookeeper操作客户端
     * @deprecated 注意，若引入了miracle-zookeeper包，请通过Bean注入方式使用miracle-zookeeper包中的ZkUtil
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

}
