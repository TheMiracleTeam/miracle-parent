package com.miracle.zookeeper.service;

import com.miracle.common.annotation.ZookeeperWatch;
import com.miracle.common.data.AnnotationData;
import com.miracle.common.util.AnnotationUtil;
import com.miracle.common.util.ZookeeperUtil;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Zookeeper初始化操作类
 * Created at 2018-08-23 21:38:22
 * @author Allen
 */
@Service
public class ZookeeperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperService.class);

    @PostConstruct
    public void init() {
        ZkClient zkClient = ZookeeperUtil.getZkClient();
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
}
