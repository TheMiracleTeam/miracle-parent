package com.miracle.platform.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.miracle.common.util.ZookeeperUtil;
import com.miracle.platform.common.ConfigKey;
import com.miracle.platform.common.Constant;
import com.miracle.repository.model.CnfConfig;
import com.miracle.repository.service.ICnfConfigService;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigService {

    // Zookeeper服务器地址
    @Value("${zookeeper.host}")
    private String zkHost;

    // Zookeeper超时时间
    @Value("${zookeeper.timeout}")
    private int zkTimeout;

    @Reference
    private ICnfConfigService cnfConfigService;

    /**
     * 同步配置至Zookeeper服务器
     */
    @PostConstruct
    public void doSync() {
        Map<Object, Object> params = new HashMap<>();
        params.put("type", Constant.CNF_TYPE_SYSTEM + "," + Constant.CNF_TYPE_PLATFORM);
        params.put("enable", Constant.STATUS_ENABLE);
        List<CnfConfig> list = cnfConfigService.listAll(params);
        for (CnfConfig cnfConfig : list) {
            ZkClient zkClient = ZookeeperUtil.getZkClient(zkHost, zkTimeout);
            zkClient.createPersistent(ConfigKey.CNF_ROOT + cnfConfig.getConfKey(), cnfConfig.getConfValue());
        }
    }
}
