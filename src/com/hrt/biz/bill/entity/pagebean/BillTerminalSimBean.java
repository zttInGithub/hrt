package com.hrt.biz.bill.entity.pagebean;

import com.hrt.biz.bill.entity.model.BillTerminalSimModel;

import java.util.Date;

/**
 * BillTerminalSimBean
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2020/05/12
 * @since 1.8
 **/
public class BillTerminalSimBean extends BillTerminalSimModel {
    private Integer page;	// 当前页数
    private Integer rows;	// 总记录数
    private String sort;	// 排序字段
    private String order;	// 排序规则 ASC DESC
    private Date start;
    private Date end;

    /** 匹配查询使用 **/
    private String inSims;

    public String getInSims() {
        return inSims;
    }

    public void setInSims(String inSims) {
        this.inSims = inSims;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

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