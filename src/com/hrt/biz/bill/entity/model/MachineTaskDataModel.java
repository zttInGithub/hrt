package com.hrt.biz.bill.entity.model;

import java.util.Date;

public class MachineTaskDataModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer bmtdID;				//主键
	private String taskID;				//工单编号
	private String unno;				//机构编号
	private String mid;					//商户编号
	private String type;				//工单类别
	private String changeType;			//换机类型
	private String tid;					//终端编号
	private Integer bmtID;				//内部编号
	private Integer bmaID;				//机具型号
	private String machinesn;			//机具SN号
	private String simtel;				//机具SIM卡号
	private Date taskConfirmDate;		//工单完成时间
	private String proCessconText;		//审批描述
	private Integer maintainUid;		//操作人员
	private Date maintainDate;			//操作日期
	private String maintainType;		//变更类型
	private Integer approveUid;			//授权人员
	private Date approveDate;			//授权日期
	private String approveStatus;		//授权状态
	private Date taskStartDate;			//待流转工单
	private Date taskStep1Date;			//工单流转1
	private Date taskStep2Date;			//工单流转2
	private String remarks;             //备注
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getBmtdID() {
		return bmtdID;
	}
	public void setBmtdID(Integer bmtdID) {
		this.bmtdID = bmtdID;
	}
	public String getTaskID() {
		return taskID;
	}
	public void setTaskID(String taskID) {
		this.taskID = taskID;
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
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public Integer getBmtID() {
		return bmtID;
	}
	public void setBmtID(Integer bmtID) {
		this.bmtID = bmtID;
	}
	public Integer getBmaID() {
		return bmaID;
	}
	public void setBmaID(Integer bmaID) {
		this.bmaID = bmaID;
	}
	public String getMachinesn() {
		return machinesn;
	}
	public void setMachinesn(String machinesn) {
		this.machinesn = machinesn;
	}
	public String getSimtel() {
		return simtel;
	}
	public void setSimtel(String simtel) {
		this.simtel = simtel;
	}
	public Date getTaskConfirmDate() {
		return taskConfirmDate;
	}
	public void setTaskConfirmDate(Date taskConfirmDate) {
		this.taskConfirmDate = taskConfirmDate;
	}
	public String getProCessconText() {
		return proCessconText;
	}
	public void setProCessconText(String proCessconText) {
		this.proCessconText = proCessconText;
	}
	public Integer getMaintainUid() {
		return maintainUid;
	}
	public void setMaintainUid(Integer maintainUid) {
		this.maintainUid = maintainUid;
	}
	public Date getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}
	public String getMaintainType() {
		return maintainType;
	}
	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}
	public Integer getApproveUid() {
		return approveUid;
	}
	public void setApproveUid(Integer approveUid) {
		this.approveUid = approveUid;
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
	public Date getTaskStartDate() {
		return taskStartDate;
	}
	public void setTaskStartDate(Date taskStartDate) {
		this.taskStartDate = taskStartDate;
	}
	public Date getTaskStep1Date() {
		return taskStep1Date;
	}
	public void setTaskStep1Date(Date taskStep1Date) {
		this.taskStep1Date = taskStep1Date;
	}
	public Date getTaskStep2Date() {
		return taskStep2Date;
	}
	public void setTaskStep2Date(Date taskStep2Date) {
		this.taskStep2Date = taskStep2Date;
	}
	
}
