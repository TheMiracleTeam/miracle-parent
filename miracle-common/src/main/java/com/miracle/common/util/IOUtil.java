package com.miracle.common.util;

import java.io.*;

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

    /**
     * 获取指定目录下所有文件的总行数
     * @param path 根目录
     * @param scanChildren 是否扫描子文件夹
     * @param ignoreNullLine 忽略空行
     * @return int 所有文件总行数
     */
    public static int getFilesCount(String path, boolean scanChildren, boolean ignoreNullLine) {
        int count = 0;
        File root = new File(path);
        File[] files = root.listFiles();
        if (files == null) {
            return count;
        }
        BufferedReader br = null;
        String line;
        for (File file : files) {
            if (file.isFile()) {
                try {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    while ((line = br.readLine()) != null) {
                        if (ignoreNullLine) {
                            if (!line.trim().equals("")) {
                                count++;
                            }
                        } else {
                            count++;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (scanChildren) {
                count += getFilesCount(file.getPath(), true, ignoreNullLine);
            }
        }
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return count;
    }
}
