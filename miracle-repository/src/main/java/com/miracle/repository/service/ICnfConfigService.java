package com.miracle.repository.service;

import com.miracle.repository.model.CnfConfig;

import java.util.List;
import java.util.Map;

/**
 * 公共配置业务处理接口
 * Crated at 2018-09-01 11:31:31
 * @author Allen
 */
public interface ICnfConfigService {

    /**
     * 获取所有数据
     * @param paramMap 参数Map
     * @return List<CnfConfig> 配置列表
     */
    List<CnfConfig> listAll(Map<Object, Object> paramMap);
}
