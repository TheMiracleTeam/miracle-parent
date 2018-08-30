package com.miracle.api.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.miracle.common.util.ZookeeperUtil;
import com.miracle.mybatis.service.AbstractService;
import com.miracle.repository.model.HelloWorld;
import com.miracle.repository.service.IHelloWorldService;

import java.util.List;

/**
 * HelloWorld业务处理接口服务暴露类
 * Created at 2018-08-27 23:55:16
 * @author Allen
 */
@Service
public class HelloWorldService extends AbstractService<HelloWorld> implements IHelloWorldService {

    @Override
    public List<HelloWorld> listAll() {
        System.out.println("do listAll");
        List<HelloWorld> list = super.selectAll();
        System.out.println("数据为：");
        System.out.println(JSONArray.toJSONString(list));
//        ZookeeperUtil.getInstance();
        return super.selectAll();
    }
}
