package com.miracle.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class工具类
 * Created at 2018-08-25 10:51:32
 * @author Allen
 */
public class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    private ClassUtil() {}

    /**
     * 获取当前类加载器
     * @return ClassLoad 类加载器
     */
    public static ClassLoader getCurrentClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 根据包名获取Class
     * @param packageName 包名
     * @param isGetChildren 是否获取子目录
     * @return List<Class<?>> 泛型Class集合
     */
    public static List<Class<?>> getClassesInPackage(String packageName, boolean isGetChildren) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            Enumeration<URL> urls = getCurrentClassLoader().getResources(packageName.replace(".", File.separator));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    // 如果为文件
                    if ("file".equals(protocol)) {
                        String packagePath = URLDecoder.decode(url.getFile(), "UTF-8");
                        classes.addAll(getClassesInPackageByFile(packageName, packagePath, isGetChildren));
                    } else if ("jar".equals(protocol)) {
                        // 如果为Jar包
                        classes.addAll(getClassesInPackageByJar(packageName, url));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * 根据包名获取文件夹下的Class
     * @param packageName 包名
     * @param packagePath 包路径
     * @param isGetChildren 是否获取子目录
     * @return List<Class<?>> 泛型Class集合
     */
    public static List<Class<?>> getClassesInPackageByFile(String packageName, String packagePath, boolean isGetChildren) {
        List<Class<?>> classes = new ArrayList<>();
        File dir = new File(packagePath);
        // 如果该文件不存在或者为文件夹
        if (!dir.exists() || !dir.isDirectory()) {
            return classes;
        }
        File[] files = dir.listFiles(new FileFilter() {
            /**
             * 自定义过滤规则，获取以.class结尾的文件以及是否获取子目录
             * @param file 文件对象
             * @return boolean 是否获取
             */
            @Override
            public boolean accept(File file) {
                return (isGetChildren && file.isDirectory()) || file.getName().endsWith(".class");
            }
        });
        if (null != files) {
            for (File file : files) {
                // 如果该文件为文件夹
                if (file.isDirectory()) {
                    classes.addAll(getClassesInPackageByFile(packageName + "." + file.getName(),
                            file.getAbsolutePath(), isGetChildren));
                } else {
                    // 如果为Class文件，去掉后缀.class
                    String className = file.getName().substring(0, file.getName().length() - ".class".length());
                    try {
                        classes.add(Class.forName(packageName + "." + className));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classes;
    }

    /**
     * 根据包名获取Jar包下的Class
     * @param packageName 包名
     * @param url URL资源对象
     * @return 泛型Class集合
     */
    public static List<Class<?>> getClassesInPackageByJar(String packageName, URL url) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entryName.charAt(0) == '/') {
                    entryName = entryName.substring(1);
                }
                if (entryName.startsWith(packageName.replace(".", "/"))) {
                    int index = entryName.lastIndexOf("/");
                    if (index != -1) {
                        packageName = entryName.substring(0, index).replace("/", ".");
                    }
                    if (entryName.endsWith(".class")) {
                        String className = entryName.substring(packageName.length() + 1, entryName.length() - 6);
                        try {
                            classes.add(Class.forName(packageName + "." + className));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }
}
