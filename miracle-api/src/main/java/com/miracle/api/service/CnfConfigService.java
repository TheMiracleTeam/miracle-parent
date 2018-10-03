package com.miracle.api.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.miracle.common.data.DataGridData;
import com.miracle.common.data.QueryData;
import com.miracle.common.util.StringUtil;
import com.miracle.mybatis.service.AbstractService;
import com.miracle.repository.model.CnfConfig;
import com.miracle.repository.service.ICnfConfigService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 公共配置业务处理接口服务暴露
 * Created at 2018-09-01 11:34:20
 * @author Allen
 */
@Service
public class CnfConfigService extends AbstractService<CnfConfig> implements ICnfConfigService {

    @Override
    public List<CnfConfig> listAll(Map<Object, Object> paramMap) {
        System.out.println("参数2为“");
        System.out.println(JSONObject.toJSONString(paramMap));
        QueryData queryData = new QueryData();
        if (paramMap != null) {
            List<String> condition = new ArrayList<>();
            if (paramMap.containsKey("type")) {
                condition.add(String.format("type in (%s)", paramMap.get("type")));
            }
            if (paramMap.containsKey("enable")) {
                condition.add("enable = " + paramMap.get("enable"));
            }
            queryData.setWhere(StringUtil.join(condition, " and "));
            if (paramMap.containsKey("order")) {
                queryData.setOrder("order by " + paramMap.get("order"));
            }
        }
        System.out.println(JSONObject.toJSONString(queryData));
        System.out.println(queryData.get("where_sql"));
        System.out.println(super.getSQLById("findAll", queryData));
        return super.selectAll(queryData);
    }

    @Override
    public DataGridData listDataGrid(Map<Object, Object> requestMap) {
        System.out.println("do api listDataGrid");
        return super.listDataGrid(requestMap);
    }
}
