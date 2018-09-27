package com.miracle.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP工具类
 * Created at 2018-09-10 22:46:40
 * @author Allen
 */
public class IPUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(IPUtil.class);

    // 默认IP地址
    private static final String DEFAULT_IP_ADDRESS = "0.0.0.0";

    private IPUtil() {}

    /**
     * 获取客户端IP地址
     * @return String IP地址
     */
    public static String getIpAddress() {
        return getIpAddress(HttpUtil.gerRequest());
    }

    /**
     * 解析客户端IP地址
     * @param request HttpServletRequest对象
     * @return String IP地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                // 根据网卡获取本机配置的IP
                InetAddress inetAddress;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    LOGGER.error("未知主机异常，返回默认IP地址。");
                    return DEFAULT_IP_ADDRESS;
                }
                ipAddress = inetAddress.getHostAddress();
            }
        }
        // 通过代理访问时可能会有两个IP地址，获取第二个，即代理IP地址
        if (ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[1].trim();
        }
        return ipAddress;
    }

    /**
     * 获取服务器IP
     * @return String 服务器IP
     */
    public static String getServerIp() {
        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            LOGGER.error("未知主机异常，重新选择通过ServletRequest获取服务器IP。");
            ip = HttpUtil.gerRequest().getServerName();
        }
        return ip;
    }

}
