package com.miracle.common.util;

import com.miracle.common.container.AnnotationContainer;
import com.miracle.common.data.AnnotationData;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 注解工具类
 * Created at 2018-08-25 14:37:24
 * @author Allen
 */
public class AnnotationUtil extends AnnotationUtils {

    private AnnotationUtil() {}

    /**
     * 根据注解类获取自定义注解实体集
     * @param annotationClass 注解类
     * @return List<AnnotationData> 自定义注解集
     */
    public static <T extends Annotation> List<AnnotationData> getAnnotationDataByAnnotationClass(Class<T> annotationClass) {
        return AnnotationContainer.getAnnotationByClazz(annotationClass);
    }
}
