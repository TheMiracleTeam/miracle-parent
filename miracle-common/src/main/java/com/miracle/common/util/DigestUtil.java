package com.miracle.common.util;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * 加密工具类
 * Created at 2018-09-10 22:59:55
 * @author Allen
 */
public class DigestUtil extends DigestUtils {

    /**
     * 微信公众号接入校验
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
     * @param token token值
     * @param timestamp 时间戳
     * @param nonce 随机字符串
     * @return boolean 校验通过返回true，否则返回false
     */
    public static boolean weChatPubCheckSignature(String signature, String token, int timestamp, int nonce) {
        String[] array = new String[] {token, String.valueOf(timestamp), String.valueOf(nonce)};
        Arrays.sort(array);
        System.out.println("排序后");
        System.out.println(JSONArray.toJSON(array));
        return signature.equals(DigestUtils.sha1Hex(array[0] + array[1] + array[2]));
    }
}
