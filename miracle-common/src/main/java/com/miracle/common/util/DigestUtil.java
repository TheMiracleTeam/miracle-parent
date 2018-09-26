package com.miracle.common.util;

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
        return signature.equals(DigestUtils.sha1Hex(array[0] + array[1] + array[2]));
    }

    /**
     * md5加密
     * @param msg 原字符串
     * @param key 中间位置插入key
     * @return String 加密结果
     */
    public static String md5(String msg, String key) {
        if(msg == null || msg == "") {
            return "";
        }
        String sb = new StringBuffer(msg).insert(msg.length()/2, key).toString();
        String md5=DigestUtils.md5Hex(sb);
        return md5;
    }

    /**
     * 验证md5
     * @param msg 待验证字符串
     * @param key 中间key
     * @param md5 md5字符串
     * @return boolean 成功或否
     */
    public static boolean verifyMD5(String msg, String key, String md5) {
        //根据传入的密钥进行验证
        String md5Text = md5(msg, key);
        if(md5Text.equalsIgnoreCase(md5))
        {
            return true;
        }
        return false;
    }
}
