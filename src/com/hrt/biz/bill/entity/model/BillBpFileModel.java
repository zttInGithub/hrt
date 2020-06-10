package com.hrt.biz.bill.entity.model;

import java.util.Date;

/**
 * 文件信息
 */
public class BillBpFileModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer fid;
	private String fName;
	private Integer fType;
	private Integer status;
	private Date cdate;
	private String cby;
	private String aby;
	private Date adate;
	private String remarks;
	private String imgUpload;
	private Integer costType;
	public Integer getCostType() {
		return costType;
	}
	public void setCostType(Integer costType) {
		this.costType = costType;
	}
	public String getImgUpload() {
		return imgUpload;
	}
	public void setImgUpload(String imgUpload) {
		this.imgUpload = imgUpload;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public Integer getfType() {
		return fType;
	}
	public void setfType(Integer fType) {
		this.fType = fType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	public String getCby() {
		return cby;
	}
	public void setCby(String cby) {
		this.cby = cby;
	}
	public String getAby() {
		return aby;
	}
	public void setAby(String aby) {
		this.aby = aby;
	}
	public Date getAdate() {
		return adate;
	}
	public void setAdate(Date adate) {
		this.adate = adate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}