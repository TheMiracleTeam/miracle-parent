package com.miracle.common.test;

import com.alibaba.fastjson.JSONObject;
import com.miracle.common.data.RequestData;

import java.util.HashMap;
import java.util.Map;

public class RequestDataTest {

    public static void main(String[] args) {
        Map<Object, Object> map = new HashMap<>();
        map.put("a", "123");
        map.put("b", "234");
        map.put("c", "555");
        map.put("d", "666");
        RequestData requestData = new RequestData(map);
        System.out.println(JSONObject.toJSON(requestData));
    }
}
