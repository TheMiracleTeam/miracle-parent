package com.miracle.common.test;

import com.miracle.common.util.StringUtil;

/**
 * 字符串工具测试类
 * Created at 2018-10-02 22:50:38
 * @author Allen
 */
public class StringUtilTest {

    public static void main(String[] args) {
        testCamelCaseToUnderscore();
    }

    static void testCamelCaseToUnderscore() {
        String hello = "hello33World";
        System.out.println(hello);
        System.out.println(StringUtil.camelCaseToUnderscore(hello));
        System.out.println(hello);
    }
}
