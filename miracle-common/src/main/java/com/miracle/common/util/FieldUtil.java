package com.miracle.common.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 对象属性工具类
 * Created at 2018-09-02 00:06:34
 * @author Allen
 */
public class FieldUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldUtil.class);

    /**
     * 根据JSON对象初始化对象属性
     * @param object 对象
     * @param json JSON对象
     */
    public static void fieldInit(Object object, JSONObject json) {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (json.containsKey(field.getName())) {
                field.setAccessible(true);
                try {
                    field.set(object, json.getObject(field.getName(), field.getType()));
                } catch (IllegalAccessException e) {
                    LOGGER.error("公共配置[{}]字段[{}]数据初始化失败！JSON数据为：{}", clazz.getName(),
                            field.getName(), json);
                }
            }
        }
    }
}
