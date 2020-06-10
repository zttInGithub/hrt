package com.hrt.biz.bill.entity.model;

import java.io.Serializable;
import java.util.Date;

public class MerchantTaskDataModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer bmtkid;			//审核唯一ID
	private String taskId;			//工单编号	
	private String unno;			//机构编号
	private String mid;				//商户编号
	private String type;			//工单类别
	private String taskContext;		//工单描述
	private String processContext;	//受理描述
	private Integer mainTainUId;	//操作人员
	private Date mainTainDate;		//操作日期
	private String mainTainType;	//变更类型
	private Integer approveUId;		//授权人员
	private Date approveDate;		//授权日期
	private String approveStatus;	//授权状态
	
	  
	public Integer getBmtkid() {
		return bmtkid;
	}
	public void setBmtkid(Integer bmtkid) {
		this.bmtkid = bmtkid;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
	public String getTaskContext() {
		return taskContext;
	}
	public void setTaskContext(String taskContext) {
		this.taskContext = taskContext;
	}
	public String getProcessContext() {
		return processContext;
	}
	public void setProcessContext(String processContext) {
		this.processContext = processContext;
	}
	public Integer getMainTainUId() {
		return mainTainUId;
	}
	public void setMainTainUId(Integer mainTainUId) {
		this.mainTainUId = mainTainUId;
	}
	public Date getMainTainDate() {
		return mainTainDate;
	}
	public void setMainTainDate(Date mainTainDate) {
		this.mainTainDate = mainTainDate;
	}
	public String getMainTainType() {
		return mainTainType;
	}
	public void setMainTainType(String mainTainType) {
		this.mainTainType = mainTainType;
	}
	public Integer getApproveUId() {
		return approveUId;
	}
	public void setApproveUId(Integer approveUId) {
		this.approveUId = approveUId;
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
	
	public MerchantTaskDataModel() {
		super();
	}
	
	
}
