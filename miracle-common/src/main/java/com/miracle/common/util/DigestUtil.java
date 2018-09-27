package com.miracle.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * 加密工具类
 * Created at 2018-09-10 22:59:55
 * @author Allen
 */
public class DigestUtil extends DigestUtils {

    private static final String TRANSFORMATION = "DES/CBC/PKCS5Padding";    //转换

    private final static String CHARSET = Charset.defaultCharset().name();  //当前环境编码

    private static byte[] iv = {(byte)0x6D, (byte)0x69, (byte)0x72,
            (byte)0x61, (byte)0x63, (byte)0x6C, (byte)0x65, (byte)0x73};    //自定义算法参数 miracles

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

    /**
     * 自定义加密
     * @param str 原文
     * @param key 密码
     * @return String 密文
     */
    public static String encrypt(String str, String key){
        StringBuffer sb = new StringBuffer(str);
        sb.reverse(); //调头
        sb.insert(str.length()/2, key);
        return encryptByDes(sb.toString(), key, CHARSET);
    }

    /**
     * 自定义解密
     * @param str 密文
     * @param key 密码
     * @return String 原文
     */
    public static String decrypt(String str, String key){
        String text = decryptByDes(str, key, CHARSET);
        int start = (text.length() - key.length())/2;
        int end = start + key.length();
        return new StringBuffer(text).replace(start, end, "").reverse().toString();
    }

    /**
     * des加密
     * @param str 原文
     * @param key 密码
     * @param charset 编码
     * @return String Base64编码转换后的密文
     */
    public static String encryptByDes(String str, String key, String charset){
        try {
            SecureRandom random = new SecureRandom();   //随机源
            // 从原始密匙数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(formatKey(key).getBytes());

            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

            // 一个SecretKey对象
            SecretKey secretKey = keyFactory.generateSecret(dks);

            Cipher ecipher = Cipher.getInstance(TRANSFORMATION);

            IvParameterSpec ivo = new IvParameterSpec(iv);

            // 用密匙初始化Cipher对象
            ecipher.init(Cipher.ENCRYPT_MODE, secretKey, ivo, random);
            return Base64.encodeBase64String(ecipher.doFinal(str.getBytes(charset)));
        } catch (Exception e) {
            throw new SecurityException(e.getMessage());
        }
    }

    /**
     * des解密
     * @param str 密文
     * @param key 密码
     * @param charset 编码
     * @return String 原文
     */
    public static String decryptByDes(String str, String key, String charset){
        try {
            SecureRandom random = new SecureRandom();   //随机源
            // 从原始密匙数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(formatKey(key).getBytes());

            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

            // 一个SecretKey对象
            SecretKey secretKey = keyFactory.generateSecret(dks);

            Cipher dcipher = Cipher.getInstance(TRANSFORMATION);

            IvParameterSpec ivo = new IvParameterSpec(iv);

            // 用密匙初始化Cipher对象
            dcipher.init(Cipher.DECRYPT_MODE, secretKey, ivo, random);
            return new String(dcipher.doFinal(Base64.decodeBase64(str)), charset);
        } catch (Exception e) {
            throw new SecurityException(e.getMessage());
        }
    }

    /**
     * 格式化key
     * @param key 原字符串
     * @return String 算法计算后的8位字符串
     */
    public static String formatKey(String key) {
        int len = key.length();
        for (int i=len;i<8;i++) {//小于8位，按下标填充"mnopqrst"字符
            key = new StringBuffer(key).append((char) (i+109)).toString();
        }
        if (len > 8) {//大于8位，保留首尾字节，截取中间
            int start = (len-6)/2;
            int end = start + 6;
            key = key.substring(0,1) + key.substring(start,end) + key.substring(key.length()-1,key.length());
        }
        return key;
    }
}
