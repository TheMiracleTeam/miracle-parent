package com.miracle.mybatis.dao;

import java.util.List;
import java.util.Map;

/**
 * MyBatis数据持久DAO接口
 * Created at 2018-08-07 23:32:55
 * @author Allen
 */
public interface DAO {

    /**
     * 保存对象
     * @param sqlId SqlId
     * @param object 对象
     * @return int 成功执行数
     */
    int insert(String sqlId, Object object);

    /**
     * 更新对象
     * @param SqlID SqlID
     * @param object 对象
     * @return int 成功执行数
     */
    int update(String SqlID, Object object);

    /**
     * 删除对象
     * @param SqlID SqlID
     * @param object 对象
     * @return int 成功执行数
     */
    int delete(String SqlID, Object object);

    /**
     * 查询返回泛型对象
     * @param SqlID SqlID
     * @param object 对象
     * @param <T> 泛型
     * @return T 泛型对象
     */
    <T> T selectForObject(String SqlID, Object object);

    /**
     * 查询返回泛型List
     * @param SqlID SqlID
     * @param object 对象
     * @return List 泛型List
     */
    List<?> selectForList(String SqlID, Object object);

    /**
     * 查询返回Map对象
     * @param sql SqlID
     * @param object 对象
     * @param key Key字段
     * @return Map 泛型Map
     */
    Map<?, ?> selectForMap(String sql, String object, String key);
}
