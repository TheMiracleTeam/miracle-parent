package com.miracle.common.data;

import com.miracle.common.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * EasyUI DataGrid封装类
 * Created at 2018-10-02 22:12:10
 * @author Allen
 */
public class DataGridData implements Serializable {

    private static final long serialVersionUID = -1120564781108854108L;

    // 页面数
    private Integer pageNum;

    // 页面数据量
    private Integer pageSize;

    // 总数据量
    private Integer total;

    // 当前页数据
    private List<? extends BaseModel> rows;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<? extends BaseModel> getRows() {
        return rows;
    }

    public void setRows(List<? extends BaseModel> rows) {
        this.rows = rows;
    }
}
