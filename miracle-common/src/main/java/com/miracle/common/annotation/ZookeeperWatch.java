package com.miracle.common.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * Zookeeper初始化监听注解
 * Created at 2018-08-22 23:27:35
 * @author Allen
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ZookeeperWatch {

    String path();

    boolean isDir() default false;
}
