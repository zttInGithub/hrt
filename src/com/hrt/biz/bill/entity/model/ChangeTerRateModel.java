package com.hrt.biz.bill.entity.model;

import java.io.Serializable;
import java.util.Date;

public class ChangeTerRateModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer bctrid;				//主键
	private String snStart;				//sn
	private String snEnd;				//sn
	private Double rate;				//费率
	private Double scanRate;			//扫码费率
	private Double secondRate;			//手续费
	private Integer totalNum;			//总量
	private Date maintainDate;			//操作日期
	private String maintainUser;		//操作人
	private String approveStatus;		//状态
	private Date approvedate;			//审核日期
	private String approveUser;			//审核人
	
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getScanRate() {
		return scanRate;
	}
	public void setScanRate(Double scanRate) {
		this.scanRate = scanRate;
	}
	public Double getSecondRate() {
		return secondRate;
	}
	public void setSecondRate(Double secondRate) {
		this.secondRate = secondRate;
	}
	public Integer getBctrid() {
		return bctrid;
	}
	public void setBctrid(Integer bctrid) {
		this.bctrid = bctrid;
	}
	public String getSnStart() {
		return snStart;
	}
	public void setSnStart(String snStart) {
		this.snStart = snStart;
	}
	public String getSnEnd() {
		return snEnd;
	}
	public void setSnEnd(String snEnd) {
		this.snEnd = snEnd;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Date getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}
	public String getMaintainUser() {
		return maintainUser;
	}
	public void setMaintainUser(String maintainUser) {
		this.maintainUser = maintainUser;
	}
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public Date getApprovedate() {
		return approvedate;
	}
	public void setApprovedate(Date approvedate) {
		this.approvedate = approvedate;
	}
	public String getApproveUser() {
		return approveUser;
	}
	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}
}