package com.miracle.mybatis.dao;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * DAO数据持久接口实现类
 * Created at 2018-08-07 23:36:35
 * @author Allen
 */
public class DaoSupport implements DAO {

    // MyBatisMapper映射接口位置
    private String nameSpace = "com.miracle.repository.mapper.";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 获取SqlSessionFactory对象
     * @return SqlSessionFactory MyBatis会话工厂
     */
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionTemplate.getSqlSessionFactory();
    }

    /**
     * 保存
     * @param sqlId SqlId
     * @param object 对象
     * @return int 成功执行数
     */
    @Override
    public int insert(String sqlId, Object object) {
        return sqlSessionTemplate.insert(nameSpace + sqlId, object);
    }

    /**
     * 批量保存
     * @param sqlId SqlId
     * @param objects 对象列表
     * @return int 成功执行数
     */
    public int batchInsert(String sqlId, List<?> objects) {
        return sqlSessionTemplate.insert(nameSpace + sqlId, objects);
    }

    /**
     * 更新
     * @param sqlId SqlID
     * @param object 对象
     * @return int 成功执行数
     */
    @Override
    public int update(String sqlId, Object object) {
        return sqlSessionTemplate.update(sqlId, object);
    }

    /**
     * 批量更新
     * @param sqlId SqlID
     * @param objects 对象列表
     * @return int 成功执行数
     */
    public int batchUpdate(String sqlId, List<?> objects) {
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        int count = 0;
        // 批量执行器
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
            if (objects != null) {
                for (Object object : objects) {
                    count += sqlSession.update(nameSpace + sqlId, object);
                }
                sqlSession.flushStatements();
                sqlSession.commit();
                sqlSession.clearCache();
            }
        }
        return count;
    }

    /**
     * 删除
     * @param sqlId SqlID
     * @param object 对象
     * @return int 成功执行数
     */
    @Override
    public int delete(String sqlId, Object object) {
        return sqlSessionTemplate.delete(nameSpace + sqlId, object);
    }

    /**
     * 批量删除（对象集合）
     * @param sqlId SqlId
     * @param objects 对象集合
     * @return int 成功执行数
     */
    public int batchDelete(String sqlId, List<?> objects) {
        return sqlSessionTemplate.delete(nameSpace + sqlId, objects);
    }

    /**
     * 批量删除（ID数组）
     * @param sqlId SqlId
     * @param ids ID数组
     * @return int 成功执行数
     */
    public int batchDelete(String sqlId, int[] ids) {
        return sqlSessionTemplate.delete(nameSpace + sqlId, ids);
    }

    /**
     * 查询返回泛型对象
     * @param sqlId SqlID
     * @param object 对象
     * @param <T> 泛型
     * @return T 泛型对象
     */
    @Override
    public <T> T selectForObject(String sqlId, Object object) {
        return sqlSessionTemplate.selectOne(nameSpace + sqlId, object);
    }
    /**
     * 查询返回泛型List
     * @param sqlId SqlID
     * @param object 对象
     * @return List 泛型List
     */
    @Override
    public List<?> selectForList(String sqlId, Object object) {
        return sqlSessionTemplate.selectList(nameSpace + sqlId, object);
    }
    /**
     * 查询返回Map对象
     * @param sqlId SqlID
     * @param object 对象
     * @param key Key字段
     * @return Map 泛型Map
     */
    @Override
    public Map<?, ?> selectForMap(String sqlId, String object, String key) {
        return sqlSessionTemplate.selectMap(nameSpace + sqlId, object, key);
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }
}
