package com.hrt.biz.bill.entity.pagebean;

import java.util.Date;

public class MerchantThreeUploadBean implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	//当前页数
	private Integer page;
	
	//总记录数
	private Integer rows;
	
	//排序字段
	private String sort;
	
	//排序规则 ASC DESC
	private String order;
	
	private Integer bmthid;
	private String mid;				//商户号
	private String tid;				//终端号
	private String sn;				//sn号
	private String license_name;	//营业执照名称
	private String r_name;			//经营名称
	private String r_addr;			//经营地址
	private Integer isConnect;		//是否支持非接
	private Integer isImmunity;		//是否支持双免
	private Integer isUnionPay;		//是否支持银联二维码
	private String merUpload1;		//非接交易小票
	private String merUpload2;		//银联二维码交易小票
	private String merUpload3;		//pos机反面照片
	private String merUpload4;		//门店照片
	private String merUpload5;		//店内经营照片
	private String merUpload6;		//云闪付照片
	private String merUpload7;		//银联标识照片
	private String merUpload8;		//非接改造操作视频
	private Date maintainDate;
	private String maintainType;
	private Date approveDate;
	private String approveStatus;
	private String approveNote;
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
	public Integer getBmthid() {
		return bmthid;
	}
	public void setBmthid(Integer bmthid) {
		this.bmthid = bmthid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getLicense_name() {
		return license_name;
	}
	public void setLicense_name(String license_name) {
		this.license_name = license_name;
	}
	public String getR_name() {
		return r_name;
	}
	public void setR_name(String r_name) {
		this.r_name = r_name;
	}
	public String getR_addr() {
		return r_addr;
	}
	public void setR_addr(String r_addr) {
		this.r_addr = r_addr;
	}
	public Integer getIsConnect() {
		return isConnect;
	}
	public void setIsConnect(Integer isConnect) {
		this.isConnect = isConnect;
	}
	public Integer getIsImmunity() {
		return isImmunity;
	}
	public void setIsImmunity(Integer isImmunity) {
		this.isImmunity = isImmunity;
	}
	public Integer getIsUnionPay() {
		return isUnionPay;
	}
	public void setIsUnionPay(Integer isUnionPay) {
		this.isUnionPay = isUnionPay;
	}
	public String getMerUpload1() {
		return merUpload1;
	}
	public void setMerUpload1(String merUpload1) {
		this.merUpload1 = merUpload1;
	}
	public String getMerUpload2() {
		return merUpload2;
	}
	public void setMerUpload2(String merUpload2) {
		this.merUpload2 = merUpload2;
	}
	public String getMerUpload3() {
		return merUpload3;
	}
	public void setMerUpload3(String merUpload3) {
		this.merUpload3 = merUpload3;
	}
	public String getMerUpload4() {
		return merUpload4;
	}
	public void setMerUpload4(String merUpload4) {
		this.merUpload4 = merUpload4;
	}
	public String getMerUpload5() {
		return merUpload5;
	}
	public void setMerUpload5(String merUpload5) {
		this.merUpload5 = merUpload5;
	}
	public String getMerUpload6() {
		return merUpload6;
	}
	public void setMerUpload6(String merUpload6) {
		this.merUpload6 = merUpload6;
	}
	public String getMerUpload7() {
		return merUpload7;
	}
	public void setMerUpload7(String merUpload7) {
		this.merUpload7 = merUpload7;
	}
	public String getMerUpload8() {
		return merUpload8;
	}
	public void setMerUpload8(String merUpload8) {
		this.merUpload8 = merUpload8;
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
	public String getApproveNote() {
		return approveNote;
	}
	public void setApproveNote(String approveNote) {
		this.approveNote = approveNote;
	}
}
