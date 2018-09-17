package com.miracle.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 时间工具类
 * Created at 2018-09-17 21:09:20
 * @author Allen
 */
public class DateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    private static SimpleDateFormat format = new SimpleDateFormat();

    // 时间日期格式
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    // 日期格式
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    // 时间格式
    public static final String TIME_PATTERN = "HH:mm:ss";

    /**
     * 获取当前时间戳，13位
     * @return long 13位时间戳
     */
    public static long currentLongTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间戳，10位
     * @return int 10位时间戳
     */
    public static int currentTimestamp() {
        return (int) (currentLongTimestamp() / 1000);
    }

    /**
     * 解析时间戳，获取日期对象
     * @param timestamp 时间戳，10位
     * @return Date 日期对象
     */
    public static Date parseDate(int timestamp) {
        return new Date(timestamp * 1000L);
    }

    /**
     * 获取当前日期对象
     * @return Date 日期对象
     */
    public static Date currentDate() {
        return parseDate(currentTimestamp());
    }

    /**
     * 格式化日期对象
     * @param date 日期对象
     * @param pattern 格式化模式
     * @return String 日期字符串
     */
    public static String format(Date date, String pattern) {
        format.applyPattern(pattern);
        return format.format(date);
    }

    /**
     * 格式化日期对象
     * @param date 日期对象
     * @return String 时间日期字符串，格式如：2018-09-17 21:57:06
     */
    public static String formatToDateTime(Date date) {
        return format(date, DATETIME_PATTERN);
    }

    /**
     * 获取当前时间日期
     * @return String 时间日期字符串，格式如：2018-09-17 21:57:59
     */
    public static String getCurrentDateTime() {
        return formatToDateTime(currentDate());
    }

    /**
     * 格式化日期对象
     * @param date 日期对象
     * @return String 日期字符串，格式如：2018-09-17
     */
    public static String formatToDate(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 获取当前日期
     * @return String 日期字符串，格式如：2018-09-17
     */
    public static String getCurrentDate() {
        return formatToDate(currentDate());
    }

    /**
     * 格式化日期对象
     * @param date 日期对象
     * @return String 时间字符串，格式如：22:02:04
     */
    public static String formatToTime(Date date) {
        return format(date, TIME_PATTERN);
    }

    /**
     * 获取当前时间
     * @return String 时间字符串，格式如：22:02:04
     */
    public static String getCurrentTime() {
        return formatToTime(currentDate());
    }

    /**
     * 解析时间日期
     * @param dateTime 时间日期字符串
     * @param pattern 格式
     * @return Date 日期对象
     */
    public static Date parse(String dateTime, String pattern) {
        format.applyPattern(pattern);
        try {
            return format.parse(dateTime);
        } catch (ParseException e) {
            LOGGER.error("解析时间日期字符串出错，时间日期字符串为：[{}]，格式为：[{}]", dateTime, pattern);
        }
        return null;
    }

    /**
     * 解析时间日期
     * @param dateTime 时间日期字符串
     * @param pattern 格式
     * @return long 时间戳，13位
     */
    public static long parseToLongTimestamp(String dateTime, String pattern) {
        return Objects.requireNonNull(parse(dateTime, pattern)).getTime();
    }

    /**
     * 解析时间日期
     * @param dateTime 时间日期字符串
     * @param pattern 格式
     * @return int 时间戳，10位
     */
    public static int parseToTimestamp(String dateTime, String pattern) {
        return (int) parseToLongTimestamp(dateTime, pattern);
    }
}
