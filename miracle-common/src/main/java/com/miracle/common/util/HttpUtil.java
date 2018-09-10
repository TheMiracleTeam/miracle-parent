package com.miracle.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Http工具类
 * Created at 2018-09-10 22:48:42
 * @author Allen
 */
public class HttpUtil {

    /**
     * 获取HttpRequest对象
     * @return HttpServletRequest Request对象
     */
    public static HttpServletRequest gerRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
    }

    /**
     * 获取HttpResponse对象
     * @return HttpServletResponse Response对象
     */
    public static HttpServletResponse getResponse() {
        return ((ServletWebRequest) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }
}
