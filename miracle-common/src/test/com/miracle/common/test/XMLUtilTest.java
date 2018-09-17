package com.miracle.common.test;

import com.miracle.common.data.RequestData;
import com.miracle.common.util.XMLUtil;

import java.util.HashMap;

public class XMLUtilTest {

    public static void main(String[] args) {
        RequestData requestData = new RequestData(new HashMap<>());
        XMLUtil.toXMLContent(requestData);
    }
}
