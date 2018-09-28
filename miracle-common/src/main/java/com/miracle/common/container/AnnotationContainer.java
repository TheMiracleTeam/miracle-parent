package com.miracle.common.container;

import com.miracle.common.data.AnnotationData;
import com.miracle.common.util.AnnotationUtil;
import com.miracle.common.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AnnotationContainer implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationContainer.class);
    // 自定义注解类集合
    private static List<Class> classes = new ArrayList<>();
    // 自定义注解实体类存放容器
    private static Map<String, List<AnnotationData>> containers = new HashMap<>();
    // 自定义注解包名
    private static final String ANNOTATION_PACKAGE_NAME = "com.miracle.common.annotation";

    static {
        try {
            classes.addAll(ClassUtil.getClassesInPackage(ANNOTATION_PACKAGE_NAME, true));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * 重写Bean初始之前切面方法
     * @param bean Bean
     * @param beanName BeanName
     * @return Object
     * @throws BeansException Bean初始化异常
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        String key;
        List<AnnotationData> list;
        for (Method method : methods) {
            for (Class clazz : classes) {
                Annotation annotation = AnnotationUtil.findAnnotation(method, clazz);
                if (null != annotation) {
                    key = clazz.getName();
                    list = new ArrayList<>();
                    if (containers.containsKey(key)) {
                        list = containers.get(key);
                    }
                    list.add(new AnnotationData(annotation, bean, method));
                    containers.put(key, list);
                }
            }
        }
        return bean;
    }

    /**
     * 根据注解类获取自定义注解实体集
     * @param annotationClass 注解类
     * @return List<AnnotationData> 自定义注解集
     */
    public static <T extends Annotation> List<AnnotationData> getAnnotationByClazz(Class<T> annotationClass) {
        String key = annotationClass.getName();
        return containers.containsKey(key) ? containers.get(key) : new ArrayList<>();
    }

    public static List<Class> getClasses() {
        return classes;
    }

    public static Map<String, List<AnnotationData>> getContainers() {
        return containers;
    }

}
