package com.hrt.biz.bill.entity.pagebean;

import java.util.Date;

public class MIDSeqInfoBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	//当前页数
	private Integer page;
	
	//总记录数
	private Integer rows;
	
	//排序字段
	private String sort;
	
	//排序规则 ASC DESC
	private String order;
	
	private Integer bmsid;			//主键
	private String areaCode;		//区域编码
	private String mccid;			//MCC编码
	private Integer seqNo;			//流水号
	private String minfo1;			//扩展字段1
	private String minfo2;			//扩展字段2
	private Date createDate;	//创建日期
	private String createUser;		//创建人
	
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
	public Integer getBmsid() {
		return bmsid;
	}
	public void setBmsid(Integer bmsid) {
		this.bmsid = bmsid;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getMccid() {
		return mccid;
	}
	public void setMccid(String mccid) {
		this.mccid = mccid;
	}
	public Integer getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
}
