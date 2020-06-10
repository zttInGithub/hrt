package com.hrt.biz.bill.entity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动产品对应表
 */
public class ProducttypeInRebatetypeModel implements Serializable {
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
}