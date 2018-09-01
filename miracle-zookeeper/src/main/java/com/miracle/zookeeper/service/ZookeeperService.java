package com.miracle.zookeeper.service;

import com.miracle.common.annotation.ZookeeperWatch;
import com.miracle.common.data.AnnotationData;
import com.miracle.common.util.AnnotationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;

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
        for (AnnotationData annotationData : AnnotationUtil.getAnnotationDataByAnnotationClass(ZookeeperWatch.class)) {
            System.out.println("in for");
            ZookeeperWatch annotation = AnnotationUtil.getAnnotation(annotationData.getMethod(), ZookeeperWatch.class);
            System.out.println(annotation.path());
            try {
                annotationData.getMethod().invoke(annotationData.getObject(), "测试");
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}
