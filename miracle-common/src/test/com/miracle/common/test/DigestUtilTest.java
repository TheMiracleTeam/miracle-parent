package com.miracle.common.test;

import com.alibaba.fastjson.JSONObject;
import com.miracle.common.util.DigestUtil;

public class DigestUtilTest {

    public static void main(String[] args) {
        JSONObject json = JSONObject.parseObject("{'signature':'b91b6a25f697bae74cdbcaff0054725aaf664291','ip':'47.106.104.198','nonce':'1382569534','echostr':'8847704550593259255','timestamp':'1536680614'}");
        System.out.println(DigestUtil.weChatPubCheckSignature(json.getString("signature"), "Allen", json.getInteger("timestamp"), json.getInteger("nonce")));
    }
}
