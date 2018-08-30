package com.miracle.mybatis.service;

import com.miracle.common.util.StringUtil;
import com.miracle.mybatis.dao.DaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MyBatis数据持久抽象类
 * @param <T> 泛型，对应数据库表实体类
 */
@Service
public abstract class AbstractService<T> extends DaoSupport {

    final static Class<? extends Object> SELF = AbstractService.class;

    // 默认的查询SqlId
    final static String DEFAULT_SELECT_SQL_ID = "findAll";

    // 默认的统计SqlId
    final static String DEFAULT_COUNT_SQL_ID = "countAll";

    // 默认的批量删除SqlId
    final static String DEFAULT_DELETE_SQL_ID = "deleteAll";

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);

    @Autowired
    protected Mapper<T> mapper;

    private String NAMESPACE;

    @SuppressWarnings("unchecked")
    public AbstractService() {
        System.out.println("in this");
        String namespace = "";
        try {
            Object genericClz = getClass().getGenericSuperclass();
            // 判断泛型是否被参数化
            if (genericClz instanceof ParameterizedType) {
                Class<T> entityClass = (Class<T>) ((ParameterizedType) genericClz).getActualTypeArguments()[0];
                namespace = entityClass.getPackage().getName();
                System.out.println("命名空间为：");
                System.out.println(namespace);
                String[] packagePath = StringUtil.split(namespace, '.');
                System.out.println(Arrays.toString(packagePath));
                if (packagePath.length > 0) {
                    packagePath[packagePath.length - 1] = "mapper." + entityClass.getSimpleName() + "Mapper.";
                }
                namespace = StringUtil.join(packagePath, '.');
            }
        } catch (RuntimeException e) {
            System.out.println("GG");
            LOGGER.error("初始化失败，没有发现泛型！", SELF);
        }
        NAMESPACE = namespace;
        System.out.println(NAMESPACE);
        this.setNameSpace(namespace);
        System.out.println("over");
    }

    /**
     * 插入数据
     * @param entity 实体类对象
     * @return int 成功执行数
     */
    public int insert(T entity) {
        return mapper.insert(entity);
    }

    /**
     * 插入数据（属性为空时使用数据库表字段默认值）
     * @param entity 实体类对象
     * @return int 成功执行数
     */
    public int insertSelective(T entity) {
        return mapper.insertSelective(entity);
    }

    /**
     * 批量插入数据
     * @param entities 实体类对象集合
     * @return int 成功执行数
     */
    public int insertMore(List<T> entities) {
        int count = 0;
        for (T entity : entities) {
            if (this.insert(entity) > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * 批量插入数据（属性为空时使用数据库表字段默认值）
     * @param entities 实体类对象集合
     * @return int 成功执行数
     */
    public int insertMoreSelective(List<T> entities) {
        int count = 0;
        for (T entity : entities) {
            if (this.insert(entity) > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * 更新数据
     * @param entity 实体类对象
     * @return int 成功执行数
     */
    public int update(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    /**
     * 批量更新数据
     * @param entities 实体类对象集
     * @return int 成功执行数
     */
    public int updateMore(List<T> entities) {
        int count = 0;
        for (T entity : entities) {
            if (this.update(entity) > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * 更新数据（只更新不为空属性对应的字段）
     * @param entity 实体类对象
     * @return int 成功执行数
     */
    public int updateSelective(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 批量更新数据（只更新不为空属性对应的字段）
     * @param entities 实体类对象集
     * @return int 成功执行数
     */
    public int updateMoreSelective(List<T> entities) {
        int count = 0;
        for (T entity : entities) {
            if (this.updateSelective(entity) > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * 删除数据
     * @param entity 实体类对象
     * @return int 成功执行数
     */
    public int delete(T entity) {
        return mapper.delete(entity);
    }

    /**
     * 批量删除数据
     * @param entities 实体类对象集
     * @return int 成功执行数
     */
    public int deleteMore(List<T> entities) {
        int count = 0;
        for (T entity : entities) {
            if (this.delete(entity) > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * 根据ID删除数据
     * @param id ID
     * @return int 成功执行数
     */
    public int deleteById(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据ID集删除数据
     * @param ids ID集，例如：1,2,3,4
     * @return int 成功执行数
     */
    public int deleteMoreByIds(String ids) {
        Map<String, Object> params = new HashMap<>();
        params.put("where_sql", String.format("id in (%s)", ids));
        int count = super.delete(DEFAULT_DELETE_SQL_ID, params);
        if (count == -1) {  // 若删除出现异常（例如：SqlId不存在）
            count = 0;
            for (Integer id : StringUtil.splitToInteger(ids, ",")) {
                if (this.deleteById(id) > 0) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 根据ID集删除数据
     * @param ids ID集合
     * @return int 成功执行数
     */
    public int deleteMoreByIds(List<Integer> ids) {
        return this.deleteMoreByIds(StringUtil.join(ids, ","));
    }

    /**
     * 根据ID集删除数据
     * @param ids ID数组
     * @return int 成功执行数
     */
    public int deleteMoreByIds(int[] ids) {
        return this.deleteMoreByIds(StringUtil.join(ids, ','));
    }

    /**
     * 查询所有数据
     * @return List<T> 泛型列表
     */
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    /**
     * 根据条件查询所有数据
     * @return List<T> 泛型列表
     */
    @SuppressWarnings("unchecked")
    public List<T> selectAll(Map<Object, Object> params) {
        if (null == params) {
            return this.selectAll();
        }
        return (List<T>) super.selectForList(DEFAULT_SELECT_SQL_ID, params);
    }

    /**
     * 根据ID获取对象
     * @param id ID，主键
     * @return T 泛型对象
     */
    public T selectById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 根据条件获取对象
     * @param params 参数对象
     * @return T 泛型对象
     */
    public T selectOne(Map<Object, Object> params) {
        if (null == params) {
            return null;
        }
        List<T> list = this.selectAll(params);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }

    /**
     * 统计所有数据量
     * @return int 数据量
     */
    public int countAll() {
        return mapper.selectCount(null);
    }

    /**
     * 根据条件统计数据量
     * @param params 参数对象
     * @return int 数据量
     */
    public int countAll(Map<Object, Object> params) {
        if (null == params) {
            return this.countAll();
        }
        return super.selectForObject(DEFAULT_COUNT_SQL_ID, params);
    }
}
