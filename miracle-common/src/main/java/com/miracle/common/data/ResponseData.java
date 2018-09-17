package com.miracle.common.data;

import com.alibaba.fastjson.JSONObject;
import com.miracle.common.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应数据封装类
 * Created at 2018-09-17 22:43:03
 * @author Allen
 */
public class ResponseData {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseData.class);

    // Servlet响应对象
    private HttpServletResponse response;

    /**
     * 无参构造方法，用于可通过RequestContextHolder获取Response对象的情况下
     */
    public ResponseData() {
        this(HttpUtil.getResponse());
    }

    /**
     * 带参构造方法
     * @param response 响应对象
     */
    public ResponseData(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * 响应
     * @param result 响应数据
     */
    public void response(Object result) {
        try {
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("响应失败，响应数据为：[{}]", JSONObject.toJSONString(result));
        }
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
