package com.hrt.biz.bill.entity.model;

import java.io.Serializable;
import java.util.Date;

public class MerchantUpdateTerModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer bmutID;				//主键
	private String unno;				//机构号
	private String mid;					//商户号
	private String type;				//类型1-换机申请(审核) 2-撤机申请 3-关闭商户
	private String tid;					//终端号
	private String sn;					//sn号
	private String snImg;				//sn照片
	private String maintainType;		//操作类型
	private Date maintainDate;			//操作日期
	private String approveType;			//审核类型
	private Date approveDate;			//审核日期

	public Integer getBmutID() {
		return bmutID;
	}

	public void setBmutID(Integer bmutID) {
		this.bmutID = bmutID;
	}

	public String getUnno() {
		return unno;
	}

	public void setUnno(String unno) {
		this.unno = unno;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getSnImg() {
		return snImg;
	}

	public void setSnImg(String snImg) {
		this.snImg = snImg;
	}

	public String getMaintainType() {
		return maintainType;
	}

	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}

	public Date getMaintainDate() {
		return maintainDate;
	}

	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}

	public String getApproveType() {
		return approveType;
	}

	public void setApproveType(String approveType) {
		this.approveType = approveType;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
}