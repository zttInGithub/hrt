package com.hrt.biz.bill.entity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 采购单
 */
public class PurchaseAccountModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer paid;
	private Integer poid;			//采购总表poid
	private String accountOrderID;		//采购单订单号
	private Integer accountType;		//付款类型
	private Double accountAmt;	//金额
	private String payType;		//付款方式
	private Integer accountStatus;	//1待提交;2待审核;9已审核 
	private String accountRemark;			//备注2
	private Date accountCdate;		//创建时间
	private String accountCby;	//创建人
	private Date accountApproveDate;		//审批时间
	private String accountApprover;		//审批人
	private String accountApproveNote;	//退回原因
	private String accountApproveStatus;		//操作类型A新增;M修改;D删除;K退回
	
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public String getAccountApproveNote() {
		return accountApproveNote;
	}
	public void setAccountApproveNote(String accountApproveNote) {
		this.accountApproveNote = accountApproveNote;
	}
	public Integer getPaid() {
		return paid;
	}
	public void setPaid(Integer paid) {
		this.paid = paid;
	}
	public Integer getPoid() {
		return poid;
	}
	public void setPoid(Integer poid) {
		this.poid = poid;
	}
	public String getAccountOrderID() {
		return accountOrderID;
	}
	public void setAccountOrderID(String accountOrderID) {
		this.accountOrderID = accountOrderID;
	}
	public Double getAccountAmt() {
		return accountAmt;
	}
	public void setAccountAmt(Double accountAmt) {
		this.accountAmt = accountAmt;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Integer getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getAccountRemark() {
		return accountRemark;
	}
	public void setAccountRemark(String accountRemark) {
		this.accountRemark = accountRemark;
	}
	public Date getAccountCdate() {
		return accountCdate;
	}
	public void setAccountCdate(Date accountCdate) {
		this.accountCdate = accountCdate;
	}
	public String getAccountCby() {
		return accountCby;
	}
	public void setAccountCby(String accountCby) {
		this.accountCby = accountCby;
	}
	public Date getAccountApproveDate() {
		return accountApproveDate;
	}
	public void setAccountApproveDate(Date accountApproveDate) {
		this.accountApproveDate = accountApproveDate;
	}
	public String getAccountApprover() {
		return accountApprover;
	}
	public void setAccountApprover(String accountApprover) {
		this.accountApprover = accountApprover;
	}
	public String getAccountApproveStatus() {
		return accountApproveStatus;
	}
	public void setAccountApproveStatus(String accountApproveStatus) {
		this.accountApproveStatus = accountApproveStatus;
	}
}