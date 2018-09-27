package com.miracle.platform.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.miracle.platform.common.ConfigKey;
import com.miracle.platform.common.Constant;
import com.miracle.repository.model.CnfConfig;
import com.miracle.repository.service.ICnfConfigService;
import com.miracle.zookeeper.service.ZookeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigService {

    @Autowired
    private ZookeeperService zkService;

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
        String path;
        for (CnfConfig cnfConfig : list) {
            path = ConfigKey.CNF_ROOT + cnfConfig.getConfKey();
            zkService.updateData(path, cnfConfig.getConfValue());
        }
    }
}
