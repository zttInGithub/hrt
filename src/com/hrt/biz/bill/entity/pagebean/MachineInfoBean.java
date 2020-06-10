package com.hrt.biz.bill.entity.pagebean;

import java.io.Serializable;
import java.util.Date;

public class MachineInfoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer page;	// 当前页数
	private Integer rows;	// 总记录数
	private String sort;	// 排序字段
	private String order;	// 排序规则 ASC DESC

	private Integer bmaID;			// 唯一编号
	private String machineModel;	// 机具型号
	private String machineType;		// 机具类型
	private String machinePrice;	// 机具单价
	private Integer machineStock;	// 机具库存
	private Integer maintainUID;	// 操作人员
	private Date maintainDate;		// 操作日期
	private String maintainType;	// 操作类型
	
	private String machineTypeName;	// 机具类型名称（转换）

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

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getMachineTypeName() {
		return machineTypeName;
	}

	public void setMachineTypeName(String machineTypeName) {
		this.machineTypeName = machineTypeName;
	}

}
