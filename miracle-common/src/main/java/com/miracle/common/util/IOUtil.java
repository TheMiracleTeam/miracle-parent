package com.miracle.common.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO流工具类
 * Created at 2018-09-13 21:06:51
 * @author Allen
 */
public class IOUtil {

    private IOUtil() {}

    /**
     * 同时关闭多个流
     * @param ios IO流，可变参数
     * @param <T> 泛型，Closeable子类
     */
    public static <T extends Closeable> void closeAll(T...ios) {
        for (Closeable closeable : ios) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
