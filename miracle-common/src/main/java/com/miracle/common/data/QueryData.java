package com.miracle.common.data;

import com.miracle.common.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询参数封装类
 * Created at 2018-08-21 21:58:20
 * @author Allen
 */
public class QueryData extends HashMap<Object, Object> implements Map<Object, Object> {

    private static final long serialVersionUID = 196245055493226517L;
    // Field SQL
    private static final String KEY_FIELD_SQL_ID = "field_sql";
    // Where SQL
    private static final String KEY_WHERE = "where";
    private static final String KEY_WHERE_SQL_ID = "where_sql";
    // Group SQL
    private static final String KEY_GROUP = "group by";
    private static final String KEY_GROUP_SQL_ID = "group_sql";
    // Order SQL
    private static final String KEY_ORDER = "order by";
    private static final String KEY_ORDER_SQL_ID = "order_sql";
    // Limit SQL
    private static final String KEY_LIMIT = "limit";
    private static final String KEY_LIMIT_SQL_ID = "limit_sql";

    public QueryData() {}

    public QueryData(Map<Object, Object> data) {
        super(data);
    }

    /**
     * 设置查询字段
     * @param sql 字段内容，例如："id, FROM_UNIXTIME(created_at) as time"
     */
    public void setField(Object sql) {
        super.put(KEY_FIELD_SQL_ID, sql);
    }

    /**
     * 获取查询字段内容
     * @return Object 字段内容
     */
    public Object getField() {
        return super.get(KEY_FIELD_SQL_ID);
    }

    /**
     * 设置WhereSql
     * @param sql SQL内容（无需添加where字眼），例如："id in (1,2,3,4) and created_at > 1533825545"
     */
    public void setWhere(Object sql) {
        if (sql instanceof String) {
            if (StringUtil.containsIgnoreCase(sql.toString(), KEY_WHERE)) {
                int index = sql.toString().toLowerCase().indexOf(KEY_WHERE);
                sql = sql.toString().substring(index + KEY_WHERE.length());
            }
        }
        super.put(KEY_WHERE_SQL_ID, sql);
    }

    /**
     * 获取WhereSql内容
     * @return Object 字段内容
     */
    public Object getWhere() {
        return super.get(KEY_WHERE_SQL_ID);
    }

    /**
     * 设置GroupSql
     * @param sql SQL内容（需添加group by字眼），例如："group by world_name"
     */
    public void setGroup(Object sql) {
        if (sql instanceof String) {
            if (!StringUtil.containsIgnoreCase(sql.toString(), KEY_GROUP)) {
                sql = KEY_GROUP + " " + sql;
            }
        }
        super.put(KEY_GROUP_SQL_ID, sql);
    }

    /**
     * 获取GroupSql内容
     * @return Object 字段内容
     */
    public Object getGroup() {
        return super.get(KEY_GROUP_SQL_ID);
    }

    /**
     * 设置OrderSql
     * @param sql SQL内容（需添加order by字眼），例如："order by id desc"
     */
    public void setOrder(Object sql) {
        if (sql instanceof String) {
            if (!StringUtil.containsIgnoreCase(sql.toString(), KEY_ORDER)) {
                sql = KEY_ORDER + " " + sql;
            }
        }
        super.put(KEY_ORDER_SQL_ID, sql);
    }

    /**
     * 获取OrderSql内容
     * @return 字段内容
     */
    public Object getOrder() {
        return super.get(KEY_ORDER_SQL_ID);
    }

    /**
     * 设置LimitSql
     * @param sql SQL内容（需添加limit字眼），例如："limit 0, 10"
     */
    public void setLimit(Object sql) {
        if (sql instanceof String) {
            if (!StringUtil.containsIgnoreCase(sql.toString(), KEY_LIMIT)) {
                sql = KEY_LIMIT + " " + sql;
            }
        }
        super.put(KEY_LIMIT_SQL_ID, sql);
    }

    /**
     * 获取LimitSql内容
     * @return Object 字段内容
     */
    public Object getLimit() {
        return super.get(KEY_LIMIT_SQL_ID);
    }

    /**
     * 设置分页
     * @param pageNum 当前页数
     * @param pageSize 页数据量
     */
    public void setPage(int pageNum, int pageSize) {
        this.setLimit(String.format("limit %s, %s", (pageNum - 1) * pageSize, pageSize));
    }
}
