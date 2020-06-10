package com.hrt.biz.bill.entity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 采购单
 */
public class PurchaseDetailModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer pdid;
	private Integer poid;			//采购总表poid
	private String detailOrderID;	//采购单订单号
	private String brandName;		//品牌
	private String orderType;		//小类  1 采购订单;2 赠品订单;3 回购订单
	private String machineModel;	//机型小类名称
	private Integer snType;			//机型大类1小蓝牙，2大蓝牙
	private String rebateType;		//出库返利类型
	private Double rate;			//刷卡费率
	private Double scanRate;		//扫码费率
	private Double secondRate;		//提现手续费
	private Double machinePrice;	//机具单价
	private Integer machineNum;		//数量
	private Double detailAmt;			//金额
	private Integer detailStatus;		//6未入出库;7入出库中；8已入出库
	private Integer importNum;		//已入出数量
	private Date importDate;		//入出库时间
	private String importer;		//入出库人
	private String detailRemark;			//备注1
	private Date detailCdate;			//创建时间
	private String detailCby;			//创建人
	private Date detailLmdate;			//修改时间
	private String detailApproveStatus;	//操作类型A新增;D删除
	private String detailLmby;			//修改人
	private Integer detailMinfo1;			//扩展字段1
	private String detailMinfo2;			//扩展字段2
	private Date detailApproveDate;		//审批时间
	private String detailApprover;		//审批审批人
	private String detailApproveNote;		//退回原因

	/**
	 * 扫码1000以上
	 */
	private Double scanRateUp;

	/**
	 * 花呗费率
	 */
	private Double huaBeiRate;

	/**
	 * 押金
	 */
	private String depositAmt;

	public String getDepositAmt() {
		return depositAmt;
	}

	public void setDepositAmt(String depositAmt) {
		this.depositAmt = depositAmt;
	}

	public Double getScanRateUp() {
		return scanRateUp;
	}

	public void setScanRateUp(Double scanRateUp) {
		this.scanRateUp = scanRateUp;
	}

	public Double getHuaBeiRate() {
		return huaBeiRate;
	}

	public void setHuaBeiRate(Double huaBeiRate) {
		this.huaBeiRate = huaBeiRate;
	}

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
	public String getDetailApproveStatus() {
		return detailApproveStatus;
	}
	public void setDetailApproveStatus(String detailApproveStatus) {
		this.detailApproveStatus = detailApproveStatus;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Integer getSnType() {
		return snType;
	}
	public void setSnType(Integer snType) {
		this.snType = snType;
	}
	public Integer getPdid() {
		return pdid;
	}
	public void setPdid(Integer pdid) {
		this.pdid = pdid;
	}
	public Integer getPoid() {
		return poid;
	}
	public void setPoid(Integer poid) {
		this.poid = poid;
	}
	public String getDetailOrderID() {
		return detailOrderID;
	}
	public void setDetailOrderID(String detailOrderID) {
		this.detailOrderID = detailOrderID;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getMachineModel() {
		return machineModel;
	}
	public void setMachineModel(String machineModel) {
		this.machineModel = machineModel;
	}
	public String getRebateType() {
		return rebateType;
	}
	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}
	public Double getMachinePrice() {
		return machinePrice;
	}
	public void setMachinePrice(Double machinePrice) {
		this.machinePrice = machinePrice;
	}
	public Integer getMachineNum() {
		return machineNum;
	}
	public void setMachineNum(Integer machineNum) {
		this.machineNum = machineNum;
	}
	public Double getDetailAmt() {
		return detailAmt;
	}
	public void setDetailAmt(Double detailAmt) {
		this.detailAmt = detailAmt;
	}
	public Integer getDetailStatus() {
		return detailStatus;
	}
	public void setDetailStatus(Integer detailStatus) {
		this.detailStatus = detailStatus;
	}
	public Integer getImportNum() {
		return importNum;
	}
	public void setImportNum(Integer importNum) {
		this.importNum = importNum;
	}
	public Date getImportDate() {
		return importDate;
	}
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}
	public String getImporter() {
		return importer;
	}
	public void setImporter(String importer) {
		this.importer = importer;
	}
	public String getDetailRemark() {
		return detailRemark;
	}
	public void setDetailRemark(String detailRemark) {
		this.detailRemark = detailRemark;
	}
	public Date getDetailCdate() {
		return detailCdate;
	}
	public void setDetailCdate(Date detailCdate) {
		this.detailCdate = detailCdate;
	}
	public String getDetailCby() {
		return detailCby;
	}
	public void setDetailCby(String detailCby) {
		this.detailCby = detailCby;
	}
	public Date getDetailLmdate() {
		return detailLmdate;
	}
	public void setDetailLmdate(Date detailLmdate) {
		this.detailLmdate = detailLmdate;
	}
	public String getDetailLmby() {
		return detailLmby;
	}
	public void setDetailLmby(String detailLmby) {
		this.detailLmby = detailLmby;
	}
	public Integer getDetailMinfo1() {
		return detailMinfo1;
	}
	public void setDetailMinfo1(Integer detailMinfo1) {
		this.detailMinfo1 = detailMinfo1;
	}
	public String getDetailMinfo2() {
		return detailMinfo2;
	}
	public void setDetailMinfo2(String detailMinfo2) {
		this.detailMinfo2 = detailMinfo2;
	}
	public Date getDetailApproveDate() {
		return detailApproveDate;
	}
	public void setDetailApproveDate(Date detailApproveDate) {
		this.detailApproveDate = detailApproveDate;
	}
	public String getDetailApprover() {
		return detailApprover;
	}
	public void setDetailApprover(String detailApprover) {
		this.detailApprover = detailApprover;
	}
	public String getDetailApproveNote() {
		return detailApproveNote;
	}
	public void setDetailApproveNote(String detailApproveNote) {
		this.detailApproveNote = detailApproveNote;
	}
	
}