package com.miracle.platform.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.miracle.repository.service.IHelloWorldService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ConfigService {

    @Reference
    private IHelloWorldService helloWorldService;

//    @PostConstruct
//    public void init() {
//        System.out.println("in ConfigService init");
//        System.out.println(JSONArray.toJSONString(helloWorldService.listAll()));
//    }
}
