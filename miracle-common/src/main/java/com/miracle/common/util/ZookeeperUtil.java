package com.miracle.common.util;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * Zookeeper操作工具类
 * Created at 2018-08-25 16:45:28
 * @author Allen
 */
@Component
public class ZookeeperUtil {

    private static ZkClient zkClient;

    private static String HOST;

    private static int TIMEOUT;

    private static String PATH;

    public static ZkClient getZkClient() {
        System.out.println("初始化Zookeeper");
        System.out.println(HOST);
        System.out.println(TIMEOUT);
        System.out.println(PATH);
        if (null == zkClient) { // 提高效率
            synchronized (ZookeeperUtil.class) {
                if (null == zkClient) { // 线程安全
                    zkClient = new ZkClient(HOST, TIMEOUT);
                    zkClient.setZkSerializer(new MyZkSerializer());

                    zkClient.subscribeChildChanges(PATH, new IZkChildListener() {
                        @Override
                        public void handleChildChange(String parentPath, List<String> currentChild) throws Exception {
                            System.out.println(parentPath + " `s child changed, currentChild：" + currentChild);
                        }
                    });

                    zkClient.subscribeDataChanges(PATH, new IZkDataListener() {
                        @Override
                        public void handleDataChange(String dataPath, Object data) throws Exception {
                            System.out.println("Node " + dataPath + " changed, new data " + data);
                        }

                        @Override
                        public void handleDataDeleted(String dataPath) throws Exception {
                            System.out.println("Node " + dataPath + " deleted.");
                        }
                    });

                    zkClient.subscribeDataChanges(PATH + "2", new IZkDataListener() {
                        @Override
                        public void handleDataChange(String dataPath, Object data) throws Exception {
                            System.out.println("Node " + dataPath + " changed, new data " + data);
                        }

                        @Override
                        public void handleDataDeleted(String dataPath) throws Exception {
                            System.out.println("Node " + dataPath + " deleted.");
                        }
                    });

                    zkClient.subscribeDataChanges(PATH + "/cnf", new IZkDataListener() {
                        @Override
                        public void handleDataChange(String dataPath, Object data) throws Exception {
                            System.out.println("Node " + dataPath + " changed, new data " + data);
                        }

                        @Override
                        public void handleDataDeleted(String dataPath) throws Exception {
                            System.out.println("Node " + dataPath + " deleted.");
                        }
                    });
                }
            }

        }
        return zkClient;
    }

    /**
     * 自定义Zookeeper序列化器
     */
    static class MyZkSerializer implements ZkSerializer {
        @Override
        public byte[] serialize(Object obj) throws ZkMarshallingError {
            System.out.println("do 序列化");
            return String.valueOf(obj).getBytes(StandardCharsets.UTF_8);
        }

        @Override
        public Object deserialize(byte[] bytes) throws ZkMarshallingError {
            System.out.println("de 序列化");
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }

    @Value("${zookeeper.host}")
    public void setHost(String host) {
        HOST = host;
    }

    @Value("${zookeeper.timeout}")
    public void setTimeout(int timeout) {
        TIMEOUT = timeout;
    }

    @Value("${zookeeper.path}")
    public void setPath(String path) {
        PATH = path;
    }
}
