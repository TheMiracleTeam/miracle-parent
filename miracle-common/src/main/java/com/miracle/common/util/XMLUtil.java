package com.miracle.common.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * XML工具类
 * Created at 2018-09-17 22:21:04
 * @author Allen
 */
public class XMLUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLUtil.class);

    private XMLUtil() {}

    /**
     * 解析XML文本
     * @param xmlContent XML文本字符串
     * @return Object XML对象
     */
    public static Object parseObject(String xmlContent) {
        try {
            Unmarshaller unmarshaller = JAXBContext.newInstance(xmlContent).createUnmarshaller();
            StringReader reader = new StringReader(xmlContent);
            return unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            LOGGER.error("解析XML文本出错，XML内容为：[{}]", xmlContent);
        }
        return null;
    }

    /**
     * 将对象转换为XML文本
     * @param object XML对象
     * @return String XML文本字符串
     */
    public static String toXMLContent(Object object) {
        try {
            Marshaller marshaller = JAXBContext.newInstance(object.getClass()).createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);
            return writer.toString();
        } catch (JAXBException e) {
            LOGGER.error("转换XML文本失败，对象值为：[{}]", JSONObject.toJSONString(object));
        }
        return null;
    }
}
