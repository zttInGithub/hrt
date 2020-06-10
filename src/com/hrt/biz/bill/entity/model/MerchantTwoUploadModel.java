package com.hrt.biz.bill.entity.model;

import java.util.Date;

/**
 * 资料二次上传
 */
public class MerchantTwoUploadModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer bmtuid;
	private String mid;
	private String legalName;
	private String legalName2;
	private String bupLoad;
	private String bigdealUpLoad;
	private String laborContractImg;
	private Date maintaindate;
	private String maintaintype;
	private Date approveDate;
	private String approveStatus;
	private String approveNote;
	
	public Integer getBmtuid() {
		return bmtuid;
	}
	public void setBmtuid(Integer bmtuid) {
		this.bmtuid = bmtuid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public String getLegalName2() {
		return legalName2;
	}
	public void setLegalName2(String legalName2) {
		this.legalName2 = legalName2;
	}
	public String getBupLoad() {
		return bupLoad;
	}
	public void setBupLoad(String bupLoad) {
		this.bupLoad = bupLoad;
	}
	public String getBigdealUpLoad() {
		return bigdealUpLoad;
	}
	public void setBigdealUpLoad(String bigdealUpLoad) {
		this.bigdealUpLoad = bigdealUpLoad;
	}
	public String getLaborContractImg() {
		return laborContractImg;
	}
	public void setLaborContractImg(String laborContractImg) {
		this.laborContractImg = laborContractImg;
	}
	public Date getMaintaindate() {
		return maintaindate;
	}
	public void setMaintaindate(Date maintaindate) {
		this.maintaindate = maintaindate;
	}
	public String getMaintaintype() {
		return maintaintype;
	}
	public void setMaintaintype(String maintaintype) {
		this.maintaintype = maintaintype;
	}
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public String getApproveNote() {
		return approveNote;
	}
	public void setApproveNote(String approveNote) {
		this.approveNote = approveNote;
	}
}