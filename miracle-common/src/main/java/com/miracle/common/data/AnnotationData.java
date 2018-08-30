package com.miracle.common.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 自定义注解实体类
 * Created at 2018-08-22 23:28:18
 * @author Allen
 */
public class AnnotationData {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationData.class);

    // 自定义注解类
    private Annotation annotation;
    // 注解对象
    private Object object;
    // 注解方法
    private Method method;

    /**
     * 调用该方法
     */
    public void invoke() {
        try {
            this.getMethod().invoke(this.getObject());
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
        }
    }

    public AnnotationData(Annotation annotation, Object object, Method method) {
        this.annotation = annotation;
        this.object = object;
        this.method = method;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
