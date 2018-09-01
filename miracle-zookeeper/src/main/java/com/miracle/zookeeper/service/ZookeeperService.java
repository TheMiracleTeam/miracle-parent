package com.miracle.zookeeper.service;

import com.alibaba.fastjson.JSONObject;
import com.miracle.common.annotation.ZookeeperWatch;
import com.miracle.common.data.AnnotationData;
import com.miracle.common.util.AnnotationUtil;
import com.miracle.common.util.ZookeeperUtil;
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
        System.out.println("do this init ZookeeperService");
        ZkClient zkClient = ZookeeperUtil.getZkClient();
        for (AnnotationData annotationData : AnnotationUtil.getAnnotationDataByAnnotationClass(ZookeeperWatch.class)) {
            System.out.println("in for");
            ZookeeperWatch annotation = AnnotationUtil.getAnnotation(annotationData.getMethod(), ZookeeperWatch.class);
            System.out.println(annotation.path());
            System.out.println("值为：");
            System.out.println(zkClient.readData(annotation.path()));
            try {
                annotationData.getMethod().invoke(annotationData.getObject(), zkClient.readData(annotation.path()));
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}
