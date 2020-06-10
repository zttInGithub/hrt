package com.hrt.biz.bill.entity.model;

import java.util.Date;

public class MIDSeqInfoModel implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer bmsid;			//主键
	private String areaCode;		//区域编码
	private String mccid;			//MCC编码
	private Integer seqNo;			//流水号
	private String minfo1;			//扩展字段1
	private String minfo2;			//扩展字段2
	private Date createDate;	//创建日期
	private String createUser;		//创建人
	
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
