package com.hrt.biz.bill.entity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 来款
 * @author whgb
 *
 */
public class PurchaseRecordModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer prid; //主键
	private Integer poid; //订单表主键
	private String orderID; //订单ID
	private Double arraiveAmt;//来款金额
	private String arraiveCard; //来款卡号
	private String arraiveDate; //来款日期
	private Date recordCdate;//登记时间           
	private String recordCby; //登记人       
	private Date recordLmdate;//匹配时间
	private String recordLmby;//匹配人
	private Integer arraiveStatus;//---状态1-财务新增2-销售匹配3-财务确认4-退回5-删除
	private String arraiveWay;//来款方式
	private String arraiverName;//来款人姓名
	private String purchaserName;//代理机构名称
	private String unno;//代理机构号
	private Double recordAmt;//已匹配金额
	private String arraiveRemarks;//备注
	
	public String getArraiveRemarks() {
		return arraiveRemarks;
	}
	public void setArraiveRemarks(String arraiveRemarks) {
		this.arraiveRemarks = arraiveRemarks;
	}
	public Double getRecordAmt() {
		return recordAmt;
	}
	public void setRecordAmt(Double recordAmt) {
		this.recordAmt = recordAmt;
	}
	public Integer getPrid() {
		return prid;
	}
	public Integer getPoid() {
		return poid;
	}
	public String getOrderID() {
		return orderID;
	}
	public Double getArraiveAmt() {
		return arraiveAmt;
	}
	public String getArraiveCard() {
		return arraiveCard;
	}
	public String getArraiveDate() {
		return arraiveDate;
	}
	public Date getRecordCdate() {
		return recordCdate;
	}
	public String getRecordCby() {
		return recordCby;
	}
	public Date getRecordLmdate() {
		return recordLmdate;
	}
	public String getRecordLmby() {
		return recordLmby;
	}
	public Integer getArraiveStatus() {
		return arraiveStatus;
	}
	public String getArraiveWay() {
		return arraiveWay;
	}
	public void setPrid(Integer prid) {
		this.prid = prid;
	}
	public void setPoid(Integer poid) {
		this.poid = poid;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public void setArraiveAmt(Double arraiveAmt) {
		this.arraiveAmt = arraiveAmt;
	}
	public void setArraiveCard(String arraiveCard) {
		this.arraiveCard = arraiveCard;
	}
	public void setArraiveDate(String arraiveDate) {
		this.arraiveDate = arraiveDate;
	}
	public void setRecordCdate(Date recordCdate) {
		this.recordCdate = recordCdate;
	}
	public void setRecordCby(String recordCby) {
		this.recordCby = recordCby;
	}
	public void setRecordLmdate(Date recordLmdate) {
		this.recordLmdate = recordLmdate;
	}
	public void setRecordLmby(String recordLmby) {
		this.recordLmby = recordLmby;
	}
	public void setArraiveStatus(Integer arraiveStatus) {
		this.arraiveStatus = arraiveStatus;
	}
	public void setArraiveWay(String arraiveWay) {
		this.arraiveWay = arraiveWay;
	}
	public String getArraiverName() {
		return arraiverName;
	}
	public String getPurchaserName() {
		return purchaserName;
	}
	public String getUnno() {
		return unno;
	}
	public void setArraiverName(String arraiverName) {
		this.arraiverName = arraiverName;
	}
	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
		

}
