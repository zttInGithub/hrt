package com.hrt.biz.bill.entity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 机具订单明细资料
 */
public class MachineOrderDetailModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer bmdID;			//唯一编号
	private Integer bmoID;			//订单编号
	private Integer bmaID;			//设备编号
	private String unNO;			//机构编码
	private String actionPrice;	//实际执行单价
	private String orderNum;		//订购数量
	private String orderAmount;		//订单金额
	private Integer status;			//状态
	private Integer maintainUID ;	//操作人员   
	private Date maintainDate;		//操作日期
	private String maintainType ;	//变更类型
	
	public String getActionPrice() {
		return actionPrice;
	}
	public void setActionPrice(String actionPrice) {
		this.actionPrice = actionPrice;
	}
	public Integer getBmdID() {
		return bmdID;
	}
	public void setBmdID(Integer bmdID) {
		this.bmdID = bmdID;
	}
	public Integer getBmoID() {
		return bmoID;
	}
	public void setBmoID(Integer bmoID) {
		this.bmoID = bmoID;
	}
	public Integer getBmaID() {
		return bmaID;
	}
	public void setBmaID(Integer bmaID) {
		this.bmaID = bmaID;
	}
	public String getUnNO() {
		return unNO;
	}
	public void setUnNO(String unNO) {
		this.unNO = unNO;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
}
