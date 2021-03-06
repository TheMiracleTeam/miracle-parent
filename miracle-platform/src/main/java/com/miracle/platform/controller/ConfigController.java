package com.miracle.platform.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.miracle.common.data.DataGridData;
import com.miracle.common.data.RequestData;
import com.miracle.common.util.ZookeeperUtil;
import com.miracle.platform.config.PageSetupCnf;
import com.miracle.platform.service.ConfigService;
import com.miracle.repository.model.HelloWorld;
import com.miracle.repository.service.IHelloWorldService;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Reference
    private IHelloWorldService helloWorldService;

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "getPageConfig", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPageConfig() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("data", PageSetupCnf.getJson());
        ret.put("set", PageSetupCnf.getNavigationList());
        Map<Object, Object> map = JSONObject.parseObject("{\"ip\":\"192.168.1.103\",\"page\":\"1\",\"sort\":\"confKey\",\"rows\":\"10\",\"order\":\"asc\"}", HashMap.class);
        RequestData requestData = new RequestData(map);
        DataGridData data = configService.dataGridData(requestData);
        System.out.println(JSONObject.toJSONString(data));
        return ret;
    }

    @RequestMapping(value = "getAllData", method = RequestMethod.GET)
    @ResponseBody
    public List<HelloWorld> getAllData() {
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

}
