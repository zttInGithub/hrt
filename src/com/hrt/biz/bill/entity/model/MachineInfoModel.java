package com.hrt.biz.bill.entity.model;

import java.util.Date;

/**
 * 机具库存管理
 */
public class MachineInfoModel implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer bmaID;			//唯一编号
	private String machineModel;	//机具型号
	private String machineType;		//机具类型
	private String machinePrice;	//机具单价
	private Integer machineStock;	//机具库存
	private Integer maintainUID;	//操作人员
	private Date maintainDate;		//操作日期
	private String maintainType;	//操作类型
	
	public Integer getBmaID() {
		return bmaID;
	}
	public void setBmaID(Integer bmaID) {
		this.bmaID = bmaID;
	}
	public String getMachineModel() {
		return machineModel;
	}
	public void setMachineModel(String machineModel) {
		this.machineModel = machineModel;
	}
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public String getMachinePrice() {
		return machinePrice;
	}
	public void setMachinePrice(String machinePrice) {
		this.machinePrice = machinePrice;
	}
	public Integer getMachineStock() {
		return machineStock;
	}
	public void setMachineStock(Integer machineStock) {
		this.machineStock = machineStock;
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
