package com.miracle.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.miracle.common.util.ZookeeperUtil;
import com.miracle.repository.model.HelloWorld;
import com.miracle.repository.service.IHelloWorldService;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("config")
public class ConfigController {

    private static String HOST;

    @Reference
    private IHelloWorldService helloWorldService;

    @RequestMapping(value = "getAllData", method = RequestMethod.GET)
    @ResponseBody
    public List<HelloWorld> getAllData() {
        System.out.println("地址为：");
        System.out.println(HOST);
        ZkClient zkClient = ZookeeperUtil.getZkClient();

        return helloWorldService.listAll();
    }

    @RequestMapping(value = "listAllData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listAllData(@RequestParam String name) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("wordData", helloWorldService.listAll());
        ret.put("title", "你好世界！：" + name);
        return ret;
    }

    @Value("${zookeeper.host}")
    public void setHost(String host) {
        HOST = host;
    }
}
