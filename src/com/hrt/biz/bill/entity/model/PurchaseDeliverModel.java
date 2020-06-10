package com.hrt.biz.bill.entity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 采购单
 */
public class PurchaseDeliverModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer pdlid;
	private Integer poid;			//采购总表poid
	private Integer pdid;			//采购明细表pdid
	private String deliverOrderID;	//采购单订单号
	private String deliverId;		//快递单号
	private String deliverName;		//快递公司
	private Integer deliveNum;		//发货数量
	private Date deliveDate;		//发货时间
	private String deliver;			//发货人
	private Integer allocatedNum;	//已分配数量

	private Integer deliverStatus;	//1待提交;2待发货;3已发货;4已分配
	private String deliverPurName;	//采购机构名称
	private String deliverUnno;		//采购代理机构号
	private String deliverContacts;	//收货联系人
	private String deliverContactPhone;//收货联系电话
	private String deliverContactMail;//收货联系邮箱
	private String deliverReceiveaddr;//收货地址
	private String postCode;		//邮编

	private String deliverRemark;	//备注1
	private Date deliverCdate;		//创建时间
	private String deliverCby;		//创建人
	private Date deliverLmdate;		//修改时间
	private String deliverLmby;		//修改人
 	private Integer deliverMinfo1;	//扩展字段3
 	private String deliverMinfo2;	//扩展字段4
 	
	public Integer getAllocatedNum() {
		return allocatedNum;
	}
	public void setAllocatedNum(Integer allocatedNum) {
		this.allocatedNum = allocatedNum;
	}
	public Integer getPdlid() {
		return pdlid;
	}
	public void setPdlid(Integer pdlid) {
		this.pdlid = pdlid;
	}
	public Integer getPoid() {
		return poid;
	}
	public void setPoid(Integer poid) {
		this.poid = poid;
	}
	public Integer getPdid() {
		return pdid;
	}
	public void setPdid(Integer pdid) {
		this.pdid = pdid;
	}
	public String getDeliverOrderID() {
		return deliverOrderID;
	}
	public void setDeliverOrderID(String deliverOrderID) {
		this.deliverOrderID = deliverOrderID;
	}
	public String getDeliverId() {
		return deliverId;
	}
	public void setDeliverId(String deliverId) {
		this.deliverId = deliverId;
	}
	public String getDeliverName() {
		return deliverName;
	}
	public void setDeliverName(String deliverName) {
		this.deliverName = deliverName;
	}
	public Integer getDeliveNum() {
		return deliveNum;
	}
	public void setDeliveNum(Integer deliveNum) {
		this.deliveNum = deliveNum;
	}
	public Date getDeliveDate() {
		return deliveDate;
	}
	public void setDeliveDate(Date deliveDate) {
		this.deliveDate = deliveDate;
	}
	public String getDeliver() {
		return deliver;
	}
	public void setDeliver(String deliver) {
		this.deliver = deliver;
	}
	public Integer getDeliverStatus() {
		return deliverStatus;
	}
	public void setDeliverStatus(Integer deliverStatus) {
		this.deliverStatus = deliverStatus;
	}
	public String getDeliverPurName() {
		return deliverPurName;
	}
	public void setDeliverPurName(String deliverPurName) {
		this.deliverPurName = deliverPurName;
	}
	public String getDeliverUnno() {
		return deliverUnno;
	}
	public void setDeliverUnno(String deliverUnno) {
		this.deliverUnno = deliverUnno;
	}
	public String getDeliverContacts() {
		return deliverContacts;
	}
	public void setDeliverContacts(String deliverContacts) {
		this.deliverContacts = deliverContacts;
	}
	public String getDeliverContactPhone() {
		return deliverContactPhone;
	}
	public void setDeliverContactPhone(String deliverContactPhone) {
		this.deliverContactPhone = deliverContactPhone;
	}
	public String getDeliverContactMail() {
		return deliverContactMail;
	}
	public void setDeliverContactMail(String deliverContactMail) {
		this.deliverContactMail = deliverContactMail;
	}
	public String getDeliverReceiveaddr() {
		return deliverReceiveaddr;
	}
	public void setDeliverReceiveaddr(String deliverReceiveaddr) {
		this.deliverReceiveaddr = deliverReceiveaddr;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getDeliverRemark() {
		return deliverRemark;
	}
	public void setDeliverRemark(String deliverRemark) {
		this.deliverRemark = deliverRemark;
	}
	public Date getDeliverCdate() {
		return deliverCdate;
	}
	public void setDeliverCdate(Date deliverCdate) {
		this.deliverCdate = deliverCdate;
	}
	public String getDeliverCby() {
		return deliverCby;
	}
	public void setDeliverCby(String deliverCby) {
		this.deliverCby = deliverCby;
	}
	public Date getDeliverLmdate() {
		return deliverLmdate;
	}
	public void setDeliverLmdate(Date deliverLmdate) {
		this.deliverLmdate = deliverLmdate;
	}
	public String getDeliverLmby() {
		return deliverLmby;
	}
	public void setDeliverLmby(String deliverLmby) {
		this.deliverLmby = deliverLmby;
	}
	public Integer getDeliverMinfo1() {
		return deliverMinfo1;
	}
	public void setDeliverMinfo1(Integer deliverMinfo1) {
		this.deliverMinfo1 = deliverMinfo1;
	}
	public String getDeliverMinfo2() {
		return deliverMinfo2;
	}
	public void setDeliverMinfo2(String deliverMinfo2) {
		this.deliverMinfo2 = deliverMinfo2;
	}
}