package com.miracle.common.data;

import com.alibaba.fastjson.util.TypeUtils;
import com.miracle.common.util.HttpUtil;
import com.miracle.common.util.IPUtil;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
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

    // Servlet请求对象
    private HttpServletRequest request;

    /**
     * 带参构造方法，用于非Servlet请求
     * @param map Map<Object, Object>对象
     */
    public RequestData(Map<Object, Object> map) {
        super(map);
    }

    /**
     * 无参构造方法，用于Servlet请求
     */
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

    /**
     * 获取请求参数Map
     * @return Map<Object, Object> 请求参数Map
     */
    public Map<Object, Object> getMap() {
        Map<Object, Object> retMap = new HashMap<>();
        for (Entry entry : super.entrySet()) {
            retMap.put(entry.getKey(), entry.getValue());
        }
        return retMap;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public Boolean getBoolean(String key) {
        Object value = this.get(key);
        return value == null ? null : TypeUtils.castToBoolean(value);
    }

    public byte[] getBytes(String key) {
        Object value = this.get(key);
        return value == null ? null : TypeUtils.castToBytes(value);
    }

    public boolean getBooleanValue(String key) {
        Object value = this.get(key);
        Boolean booleanVal = TypeUtils.castToBoolean(value);
        return booleanVal == null ? false : booleanVal;
    }

    public Byte getByte(String key) {
        Object value = this.get(key);
        return TypeUtils.castToByte(value);
    }

    public byte getByteValue(String key) {
        Object value = this.get(key);
        Byte byteVal = TypeUtils.castToByte(value);
        return byteVal == null ? 0 : byteVal;
    }

    public Short getShort(String key) {
        Object value = this.get(key);
        return TypeUtils.castToShort(value);
    }

    public short getShortValue(String key) {
        Object value = this.get(key);
        Short shortVal = TypeUtils.castToShort(value);
        return shortVal == null ? 0 : shortVal;
    }

    public Integer getInteger(String key) {
        Object value = this.get(key);
        return TypeUtils.castToInt(value);
    }

    public int getIntValue(String key) {
        Object value = this.get(key);
        Integer intVal = TypeUtils.castToInt(value);
        return intVal == null ? 0 : intVal;
    }

    public Long getLong(String key) {
        Object value = this.get(key);
        return TypeUtils.castToLong(value);
    }

    public long getLongValue(String key) {
        Object value = this.get(key);
        Long longVal = TypeUtils.castToLong(value);
        return longVal == null ? 0L : longVal;
    }

    public Float getFloat(String key) {
        Object value = this.get(key);
        return TypeUtils.castToFloat(value);
    }

    public float getFloatValue(String key) {
        Object value = this.get(key);
        Float floatValue = TypeUtils.castToFloat(value);
        return floatValue == null ? 0.0F : floatValue;
    }

    public Double getDouble(String key) {
        Object value = this.get(key);
        return TypeUtils.castToDouble(value);
    }

    public double getDoubleValue(String key) {
        Object value = this.get(key);
        Double doubleValue = TypeUtils.castToDouble(value);
        return doubleValue == null ? 0.0D : doubleValue;
    }

    public BigDecimal getBigDecimal(String key) {
        Object value = this.get(key);
        return TypeUtils.castToBigDecimal(value);
    }

    public BigInteger getBigInteger(String key) {
        Object value = this.get(key);
        return TypeUtils.castToBigInteger(value);
    }

    public String getString(String key) {
        Object value = this.get(key);
        return value == null ? null : value.toString();
    }

    public Date getDate(String key) {
        Object value = this.get(key);
        return TypeUtils.castToDate(value);
    }

    public java.sql.Date getSqlDate(String key) {
        Object value = this.get(key);
        return TypeUtils.castToSqlDate(value);
    }

    public Timestamp getTimestamp(String key) {
        Object value = this.get(key);
        return TypeUtils.castToTimestamp(value);
    }
}
