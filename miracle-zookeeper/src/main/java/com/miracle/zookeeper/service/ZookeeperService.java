package com.miracle.zookeeper.service;

import com.alibaba.fastjson.JSON;
import com.miracle.common.annotation.ZookeeperWatch;
import com.miracle.common.data.AnnotationData;
import com.miracle.common.util.AnnotationUtil;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Zookeeper初始化操作类
 * Created at 2018-08-23 21:38:22
 * @author Allen
 */
@Service
public class ZookeeperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperService.class);

    /**
     * Watch事件超时时间
     */
    @Value("${zookeeper.watch.timeout:5000}")
    private int watchTimeout;

    @Autowired
    private ZkClient zkClient;

    /**
     * 构造方法私有化
     */
    private ZookeeperService() {}

    /**
     * 初始化添加ZookeeperWatch注解的配置类
     */
    @PostConstruct
    public void init() {
        for (final AnnotationData annotationData : AnnotationUtil.getAnnotationDataByAnnotationClass(ZookeeperWatch.class)) {
            final ZookeeperWatch annotation = AnnotationUtil.getAnnotation(annotationData.getMethod(), ZookeeperWatch.class);
            String conf = zkClient.readData(annotation.path());
            try {
                annotationData.getMethod().invoke(annotationData.getObject(), conf);
                zkClient.subscribeDataChanges(annotation.path(), new IZkDataListener() {
                    public void handleDataChange(String dataPath, Object data) {
                        try {
                            annotationData.getMethod().invoke(annotationData.getObject(), data);
                        } catch (Exception e) {
                            LOGGER.error("[{}]执行属性更新失败！配置目录为[{}]，配置值为[{}]",
                                    annotationData.getObject().getClass().getName(), annotation.path(), data);
                        }
                    }
                    public void handleDataDeleted(String dataPath) {
                        System.out.println("Node " + dataPath + " deleted.");
                    }
                });
            } catch (Exception e) {
                LOGGER.error("[{}]执行属性初始化失败！配置目录为[{}]，配置值为[{}]",
                        annotationData.getObject().getClass().getName(), annotation.path(), conf);
            }
        }
    }

    /**
     * 获取Zookeeper配置数据
     * @param path 配置目录
     * @param <T> 泛型
     * @return T 泛型配置
     */
    public <T> T getData(String path) {
        return zkClient.readData(path);
    }

    /**
     * 设置数据至Zookeeper服务器
     * @param path 配置目录
     * @param data 数据
     */
    public void setData(String path, Object data) {
        System.out.println("do setData ...");
        System.out.println(JSON.toJSONString(data));
        if (!zkClient.exists(path)) {
            zkClient.createPersistent(path, true);
        }
        if (!(data instanceof String)) {
            data = JSON.toJSONString(data);
        }
        zkClient.writeData(path, data);
    }

    /**
     * 更新Zookeeper数据
     * @param path 配置目录
     * @param data 数据
     */
    public void updateData(String path, Object data) {
        if (!zkClient.exists(path) || getData(path) == null) {
            setData(path, data);
        } else {
            String tmpData = getData(path);
            if (!tmpData.equals(path)) {
                setData(path, data);
            }
        }
    }

    /**
     * 等待节点删除
     * @param path 节点目录
     * @return boolean 正常删除返回false，超时或删除失败返回false
     */
    public boolean waitNodeDel(String path) {
        return waitNodeDel(path, watchTimeout);
    }

    /**
     * 等待节点删除
     * @param path 节点目录
     * @param timeout 超时时间
     * @return boolean 正常删除返回false，超时或删除失败返回false
     */
    public boolean waitNodeDel(String path, int timeout) {
        long startTimestamp = System.currentTimeMillis();
        AtomicBoolean flag = new AtomicBoolean(true);
        /*
         Zookeeper监听器
         理论上使用subscribeDataChanges更加合适，但由于其需要订阅两种监听事件，因此选择subscribeChildChanges。
         */
        IZkChildListener listener = (parentPath, currentChilds) -> {
            if (!zkClient.exists(path)) {
                flag.set(false);
            }
        };
        zkClient.subscribeChildChanges(path, listener);
        while (flag.get()) {
            if (System.currentTimeMillis() - startTimestamp > timeout) {
                LOGGER.error("节点[{}]删除超时！", path);
                zkClient.unsubscribeChildChanges(path, listener);
                return false;
            }
        }
        return true;
    }
}
