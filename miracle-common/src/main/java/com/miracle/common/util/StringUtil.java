package com.miracle.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created at 2018-08-16 22:23:46
 * @author Allen
 */
public class StringUtil extends StringUtils {

    // 返回类型 - 整型
    public static final int DATA_TYPE_INTEGER = 1;
    // 返回类型 - 单精度浮点型
    public static final int DATA_TYPE_FLOAT = 2;
    // 返回类型 - 双精度浮点型
    public static final int DATA_TYPE_DOUBLE = 3;
    // 返回类型 - 布尔类型
    public static final int DATA_TYPE_BOOLEAN = 4;
    // 默认返回类型
    private static final int DATA_TYPE_DEFAULT = DATA_TYPE_INTEGER;

    private StringUtil() {}

    /**
     * 将字符串切割成列表
     * @param value 字符串
     * @param regex 切割符
     * @param dataType 数据类型，默认DATA_TYPE_DEFAULT（可选：DATA_TYPE_INTEGER, DATA_TYPE_FLOAT, DATA_TYPE_DOUBLE, DATA_TYPE_BOOLEAN）
     * @param <T> 泛型
     * @return List<T> 泛型列表
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable> List<T> splitToList(String value, String regex, int dataType) {
        List<T> result = new ArrayList<>();
        for (String val : value.split(regex)) {
            switch (dataType) {
                case DATA_TYPE_INTEGER:
                    result.add((T) Integer.valueOf(Integer.parseInt(val)));
                    break;
                case DATA_TYPE_FLOAT:
                    result.add((T) Float.valueOf(Float.parseFloat(val)));
                    break;
                case DATA_TYPE_DOUBLE:
                    result.add((T) Double.valueOf(Double.parseDouble(val)));
                    break;
                case DATA_TYPE_BOOLEAN:
                    result.add((T) Boolean.valueOf(Boolean.parseBoolean(val)));
                    break;
                default:
                    result.add((T) val);
                    break;
            }
        }
        return result;
    }

    /**
     * 将字符串切割成列表
     * @param value 字符串
     * @param regex 切割符
     * @param <T> 泛型
     * @return List<T> 泛型列表
     */
    public static <T extends Comparable> List<T> splitToList(String value, String regex) {
        return splitToList(value, regex, DATA_TYPE_DEFAULT);
    }

    /**
     * 将字符串切割成整型数组
     * @param value 字符串
     * @param regex 切割符
     * @return Integer[] 整型数组
     */
    @SuppressWarnings("SuspiciousToArrayCall")
    public static Integer[] splitToInteger(String value, String regex) {
        return splitToList(value, regex, DATA_TYPE_INTEGER).toArray(new Integer[0]);
    }

    /**
     * 将字符串切割成单精度数组
     * @param value 字符串
     * @param regex 切割符
     * @return Float[] 单精度数组
     */
    public static Float[] splitToFloat(String value, String regex) {
        return (Float[]) splitToList(value, regex, DATA_TYPE_FLOAT).toArray(new Float[0]);
    }

    /**
     * 将字符串切割成双精度数组
     * @param value 字符串
     * @param regex 切割符
     * @return Double[] 双精度数组
     */
    public static Double[] splitToDouble(String value, String regex) {
        return (Double[]) splitToList(value, regex, DATA_TYPE_DOUBLE).toArray(new Double[0]);
    }

    /**
     * 将字符串切割成布尔数组
     * @param value 字符串
     * @param regex 切割符
     * @return Boolean[] 布尔数组
     */
    public static Boolean[] splitToBoolean(String value, String regex) {
        return (Boolean[]) splitToList(value, regex, DATA_TYPE_BOOLEAN).toArray(new Boolean[0]);
    }

    /**
     * 将驼峰格式字符串转换成下划线格式字符串
     * @param camelCase 驼峰格式字符串
     * @return String 下划线格式字符串
     */
    public static String camelCaseToUnderscore(String camelCase) {
        if (camelCase == null || "".equals(camelCase)) {
            return "";
        }
        StringBuilder result = new StringBuilder(camelCase);
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(result);
        String text;
        int index;
        while (matcher.find()) {
            text = matcher.group();
            index = result.indexOf(text);
            result.replace(index, index + text.length(), "_" + text.toLowerCase());
        }
        if (camelCase.charAt(0) != '_' && result.charAt(0) == '_') {
            result.deleteCharAt(0);
        }
        if (camelCase.charAt(camelCase.length() - 1) != '_' && result.charAt(result.length() - 1) == '_') {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }
}
