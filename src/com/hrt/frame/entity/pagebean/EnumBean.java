package com.hrt.frame.entity.pagebean;

import com.hrt.frame.entity.model.EnumModel;

/**
 * EnumBean
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2020/01/16
 * @since 1.8
 **/
public class EnumBean extends EnumModel {
    private static final long serialVersionUID = -4769109037503491431L;

    //当前页数
    private Integer page;

    //总记录数
    private Integer rows;

    //排序字段
    private String sort;

    //排序规则 ASC DESC
    private String order;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
