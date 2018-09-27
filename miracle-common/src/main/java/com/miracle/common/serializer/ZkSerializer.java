package com.miracle.common.serializer;

import org.I0Itec.zkclient.exception.ZkMarshallingError;

import java.nio.charset.StandardCharsets;

/**
 * Zookeeper序列化器
 * Created at 2018-09-27 10:20:00
 * @author Allen
 */
public class ZkSerializer implements org.I0Itec.zkclient.serialize.ZkSerializer {

    /**
     * 序列化
     * @param obj 数据
     * @return byte[] 字节数组
     * @throws ZkMarshallingError Zookeeper序列化异常
     */
    @Override
    public byte[] serialize(Object obj) throws ZkMarshallingError {
        return String.valueOf(obj).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 反序列化
     * @param bytes 字节数组
     * @return Object 原数据
     * @throws ZkMarshallingError Zookeeper序列化异常
     */
    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
