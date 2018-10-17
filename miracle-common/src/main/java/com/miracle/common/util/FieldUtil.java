package com.miracle.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 对象属性工具类
 * Created at 2018-09-02 00:06:34
 * @author Allen
 */
public class FieldUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldUtil.class);

    private FieldUtil() {}

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
                    if (isPrimaryDataType(field.getType()) || String.class.isAssignableFrom(field.getType())) {
                        field.set(object, json.getObject(json.getString(field.getName()), field.getType()));
                    } else if (List.class.isAssignableFrom(field.getType())) {
                        field.set(object, JSONArray.parseObject(json.getString(field.getName()),
                                field.getAnnotatedType().getType()));
                    } else {
                        field.set(object, JSONObject.parseObject(json.getString(field.getName()),
                                field.getAnnotatedType().getType()));
                    }
                } catch (IllegalAccessException e) {
                    LOGGER.error("公共配置[{}]字段[{}]数据初始化失败！JSON数据为：{}", clazz.getName(),
                            field.getName(), json);
                }
            }
        }
    }

    /**
     * 判断类型是否为基本数据类型
     * @param clazz 类型
     * @return boolean 是返回true，否则返回false
     */
    public static boolean isPrimaryDataType(Class clazz) {
        return byte.class.isAssignableFrom(clazz) || short.class.isAssignableFrom(clazz) ||
                int.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz) ||
                float.class.isAssignableFrom(clazz) || double.class.isAssignableFrom(clazz) ||
                char.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz);
    }
}
