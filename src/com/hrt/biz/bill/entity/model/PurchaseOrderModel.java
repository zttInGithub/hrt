package com.hrt.biz.bill.entity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 采购单
 */
public class PurchaseOrderModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer poid;
	private String orderID;			//采购单订单号
	private String orderDay;		//采购单订单日期
	private Integer orderMethod;	//大类1厂商单;2代理单(出库和入库单没关系，查询筛选sn出库)
	private String vendorName;		//供应商名称
	private String purchaserName;	//采购机构名称
	private String unno;			//采购代理机构号
	private String contacts;		//联系人
	private String contactPhone;	//联系电话
	private String contactMail;		//联系邮箱
	private Integer orderNum;		//总数量
	private Double orderAmt;		//总金额
	private Double orderpayAmt;		//已付款金额
	private Integer writeoffStatus;	//核销状态
	private Double writeoffAmt;		//已核销金额
	private Integer writeoffNum;	//已核销数量
	private Integer shippedStatus;	//已发货状态1，未完全发货2，已全发货
	private String purchaser;		//采购人(销售人员)
	private String purchaserGroup;		//销售组
	private String remarks;			//备注
	private Integer minfo1;			//扩展字段1
	private String minfo2;			//扩展字段2
	private String status;			//1待提交;2订单待审;3厂商单待付款/代理单已审核;4结款中;5已结款;
	private Date cdate;				//创建时间
	private String cby;				//创建人
	private Date approveDate;		//审批时间--有必要订单审核&发票审核都存吗？
	private String approver;		//审批审批人
	private String approveStatus;	//审批类型A新增;M修改;D删除;K退回 
	private String approveNote;		//退回原因
	private Date lmdate;			//修改时间
	private String lmby;			//修改人
	private Date busDate;			//业务日期
	private Integer cancelNum;		//取消数量
	private Date cancelDate;		//取消时间
	private Integer editStatus;		//可修改状态
	
	public Integer getEditStatus() {
		return editStatus;
	}
	public void setEditStatus(Integer editStatus) {
		this.editStatus = editStatus;
	}
	public Integer getCancelNum() {
		return cancelNum;
	}
	public void setCancelNum(Integer cancelNum) {
		this.cancelNum = cancelNum;
	}
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	public Date getBusDate() {
		return busDate;
	}
	public void setBusDate(Date busDate) {
		this.busDate = busDate;
	}
	public String getPurchaserGroup() {
		return purchaserGroup;
	}
	public void setPurchaserGroup(String purchaserGroup) {
		this.purchaserGroup = purchaserGroup;
	}
	public Integer getWriteoffStatus() {
		return writeoffStatus;
	}
	public void setWriteoffStatus(Integer writeoffStatus) {
		this.writeoffStatus = writeoffStatus;
	}
	public Integer getWriteoffNum() {
		return writeoffNum;
	}
	public void setWriteoffNum(Integer writeoffNum) {
		this.writeoffNum = writeoffNum;
	}
	public String getApproveNote() {
		return approveNote;
	}
	public void setApproveNote(String approveNote) {
		this.approveNote = approveNote;
	}
	public Integer getOrderMethod() {
		return orderMethod;
	}
	public void setOrderMethod(Integer orderMethod) {
		this.orderMethod = orderMethod;
	}
	public Integer getPoid() {
		return poid;
	}
	public void setPoid(Integer poid) {
		this.poid = poid;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getOrderDay() {
		return orderDay;
	}
	public void setOrderDay(String orderDay) {
		this.orderDay = orderDay;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getPurchaserName() {
		return purchaserName;
	}
	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactMail() {
		return contactMail;
	}
	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Double getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(Double orderAmt) {
		this.orderAmt = orderAmt;
	}
	public Double getOrderpayAmt() {
		return orderpayAmt;
	}
	public void setOrderpayAmt(Double orderpayAmt) {
		this.orderpayAmt = orderpayAmt;
	}
	public Double getWriteoffAmt() {
		return writeoffAmt;
	}
	public void setWriteoffAmt(Double writeoffAmt) {
		this.writeoffAmt = writeoffAmt;
	}
	public Integer getShippedStatus() {
		return shippedStatus;
	}
	public void setShippedStatus(Integer shippedStatus) {
		this.shippedStatus = shippedStatus;
	}
	public String getPurchaser() {
		return purchaser;
	}
	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getMinfo1() {
		return minfo1;
	}
	public void setMinfo1(Integer minfo1) {
		this.minfo1 = minfo1;
	}
	public String getMinfo2() {
		return minfo2;
	}
	public void setMinfo2(String minfo2) {
		this.minfo2 = minfo2;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
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
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public Date getLmdate() {
		return lmdate;
	}
	public void setLmdate(Date lmdate) {
		this.lmdate = lmdate;
	}
	public String getLmby() {
		return lmby;
	}
	public void setLmby(String lmby) {
		this.lmby = lmby;
	}
}