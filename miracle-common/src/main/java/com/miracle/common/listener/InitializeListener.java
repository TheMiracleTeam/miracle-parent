package com.miracle.common.listener;

import com.miracle.common.annotation.Initialize;
import com.miracle.common.data.AnnotationData;
import com.miracle.common.util.AnnotationUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

/**
 * 初始化监听
 * Created at 2018-08-30 22:40:35
 * @author Allen
 */
@Configuration
public class InitializeListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<AnnotationData> annotationDataList = AnnotationUtil.getAnnotationDataByAnnotationClass(Initialize.class);
        for (AnnotationData annotationData : annotationDataList) {
            try {
                annotationData.getMethod().invoke(annotationData.getObject());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
