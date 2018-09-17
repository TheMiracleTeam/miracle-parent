package com.miracle.common.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilTest {

    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat();
        Date date = new Date();
        format.applyPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = format.format(date);
        System.out.println(dateTime);
        format.applyPattern("yyyy-MM-dd");
        dateTime = format.format(date);
        System.out.println(dateTime);
    }
}
