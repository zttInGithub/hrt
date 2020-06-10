package com.hrt.biz.bill.entity.pagebean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 活动/产品对应
 */
public class ProducttypeInRebatetypeBean implements Serializable {
	//当前页数
	private Integer page;
		
	//总记录数
	private Integer rows;
		
	//排序字段
	private String sort;
		
	//排序规则 ASC DESC
	private String order;
	
	private Integer id;
	private String producttype;
	private String rebatetype;
	private String activeterms;
	private String remark;
	private String minfo1;
	private String minfo2;
	private String cby;
	private Date maintaindate;
	private Integer status;
	
	private String txnDay;
	private String txnDay1;
	
	private String yunying;
	private String yunyingname;
	
	public String getYunying() {
		return yunying;
	}
	public void setYunying(String yunying) {
		this.yunying = yunying;
	}
	public String getYunyingname() {
		return yunyingname;
	}
	public void setYunyingname(String yunyingname) {
		this.yunyingname = yunyingname;
	}
	public String getTxnDay() {
		return txnDay;
	}
	public void setTxnDay(String txnDay) {
		this.txnDay = txnDay;
	}
	public String getTxnDay1() {
		return txnDay1;
	}
	public void setTxnDay1(String txnDay1) {
		this.txnDay1 = txnDay1;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCby() {
		return cby;
	}
	public void setCby(String cby) {
		this.cby = cby;
	}
	public Date getMaintaindate() {
		return maintaindate;
	}
	public void setMaintaindate(Date maintaindate) {
		this.maintaindate = maintaindate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProducttype() {
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	public String getRebatetype() {
		return rebatetype;
	}
	public void setRebatetype(String rebatetype) {
		this.rebatetype = rebatetype;
	}
	public String getActiveterms() {
		return activeterms;
	}
	public void setActiveterms(String activeterms) {
		this.activeterms = activeterms;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMinfo1() {
		return minfo1;
	}
	public void setMinfo1(String minfo1) {
		this.minfo1 = minfo1;
	}
	public String getMinfo2() {
		return minfo2;
	}
	public void setMinfo2(String minfo2) {
		this.minfo2 = minfo2;
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
