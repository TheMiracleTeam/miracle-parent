package com.miracle.common.data;

import com.miracle.common.util.HttpUtil;
import com.miracle.common.util.IPUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 请求传输数据封装类
 * Created at 2018-09-10 22:21:47
 * @author Allen
 */
public class RequestData extends HashMap<Object, Object> implements Map<Object, Object> {

    private static final long serialVersionUID = 1L;

    private HttpServletRequest request;

    public RequestData(Map<Object, Object> map) {
        super(map);
    }

    public RequestData() {
        this.request = HttpUtil.gerRequest();
        Map<String, String[]> properties = request.getParameterMap();
        Iterator iterator = properties.entrySet().iterator();
        Map.Entry entry;
        String name, value = null;
        Object obj;
        while (iterator.hasNext()) {
            entry = (Entry) iterator.next();
            name = (String) entry.getKey();
            obj = entry.getValue();
            if (null == obj) {
                value = "";
            } else if (obj instanceof String[]) {
                for (String str : (String[]) obj) {
                    value = str + ",";
                }
                if (value != null) {
                    value = value.substring(0, value.length() - 1);
                }
            } else {
                value = obj.toString();
            }
            this.put(name, value);
        }
        // 设置请求IP
        this.put("ip", IPUtil.getIpAddress(request));
    }
}
