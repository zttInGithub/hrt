package com.hrt.biz.bill.entity.model;

import java.util.Date;
import java.util.Set;

/**
 * 机具订单资料
 */
public class MachineOrderDataModel implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer bmoID;				//唯一编号
	private String unNO;				//机构编码
	private String orderID;				//订单编号
	private String txnDay;				//交易日期
	private String orderAmount;			//订单金额
	private Date amountConfirmDate;		//缴款时间
	private String consigneeName;		//收件人
	private String consigneeAddress;	//收货人地址
	private String consigneePhone;		//收货人手机
	private String consigneeTel;		//收货人固定电话
	private String expressBill;			//快递单号
	private String minfo;				//摘要信息
	private String postCode;			//邮编
	private Date sendConfirmDate;		//发货时间
	private Date recvConfirmDate;		//收货时间
	private Integer status;				//发货状态
	private String processContext;		//受理描述
	private Integer maintainUID ;		//操作人员   
	private Date maintainDate;			//操作日期
	private String maintainType ;		//变更类型    
	private Integer approveUID ;		//授权人员    
	private Date approveDate;			//授权日期    
	private String approveStatus ;		//授权状态 
	private String shipmeThod;			//收货方式
	
	public String getShipmeThod() {
		return shipmeThod;
	}
	public void setShipmeThod(String shipmeThod) {
		this.shipmeThod = shipmeThod;
	}
	public String getTxnDay() {
		return txnDay;
	}
	public void setTxnDay(String txnDay) {
		this.txnDay = txnDay;
	}
	public Integer getBmoID() {
		return bmoID;
	}
	public void setBmoID(Integer bmoID) {
		this.bmoID = bmoID;
	}
	public String getUnNO() {
		return unNO;
	}
	public void setUnNO(String unNO) {
		this.unNO = unNO;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Date getAmountConfirmDate() {
		return amountConfirmDate;
	}
	public void setAmountConfirmDate(Date amountConfirmDate) {
		this.amountConfirmDate = amountConfirmDate;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public String getConsigneePhone() {
		return consigneePhone;
	}
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	public String getConsigneeTel() {
		return consigneeTel;
	}
	public void setConsigneeTel(String consigneeTel) {
		this.consigneeTel = consigneeTel;
	}
	public String getExpressBill() {
		return expressBill;
	}
	public void setExpressBill(String expressBill) {
		this.expressBill = expressBill;
	}
	public String getMinfo() {
		return minfo;
	}
	public void setMinfo(String minfo) {
		this.minfo = minfo;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public Date getSendConfirmDate() {
		return sendConfirmDate;
	}
	public void setSendConfirmDate(Date sendConfirmDate) {
		this.sendConfirmDate = sendConfirmDate;
	}
	public Date getRecvConfirmDate() {
		return recvConfirmDate;
	}
	public void setRecvConfirmDate(Date recvConfirmDate) {
		this.recvConfirmDate = recvConfirmDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getProcessContext() {
		return processContext;
	}
	public void setProcessContext(String processContext) {
		this.processContext = processContext;
	}
	public Integer getMaintainUID() {
		return maintainUID;
	}
	public void setMaintainUID(Integer maintainUID) {
		this.maintainUID = maintainUID;
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
	public Integer getApproveUID() {
		return approveUID;
	}
	public void setApproveUID(Integer approveUID) {
		this.approveUID = approveUID;
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
	
}
