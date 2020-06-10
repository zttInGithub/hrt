package com.hrt.biz.check.entity.model;

import java.util.Date;

public class CheckMisTakeModel {
	
	private Integer dpid; // 
	private String inportDate; // 综合导入/通知时间
	private String mid; // 商户编号
	private String cardNo; // 交易卡号
	private String transDate; // 交易日期
	private Date txnDay; // 交易查询日期
	private String finalDate; // 暂定回复日期  后台导入“最后期限”减2天
	private String bankRemarks; // 银联备注
	private String rrn; // 参考号
	private String rname; // 商户注册店名
	private String raddr; // 营业地址
	private String contactPhone; // 联系电话
	private String contactPerson; // 联系人
	private String agRemarks; // 代理回复备注
	private Integer maintainUid; // 回复人id
	private Date maintainDate; // 实际回复日期
	private String orderUpload; // 上传单据
	private String status; // 处理状态  1:已回复；0：未回复
	private Integer orderType; // 单据类型  3查询回复  2差错调单回复
	private Integer page;			//当前页数
	private Integer rows;			//总记录数
	private String unno; // 
	private String tid; // 
	private String reason; //
	private Double samt; //交易金额
	private String minfo;
	private String minfo2;
	
	
	
	public String getMinfo() {
		return minfo;
	}
	public void setMinfo(String minfo) {
		this.minfo = minfo;
	}
	public String getMinfo2() {
		return minfo2;
	}
	public void setMinfo2(String minfo2) {
		this.minfo2 = minfo2;
	}
	public Double getSamt() {
		return samt;
	}
	public void setSamt(Double samt) {
		this.samt = samt;
	}
	public Integer getDpid() {
		return dpid;
	}
	public void setDpid(Integer dpid) {
		this.dpid = dpid;
	}
	public String getInportDate() {
		return inportDate;
	}
	public void setInportDate(String inportDate) {
		this.inportDate = inportDate;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public Date getTxnDay() {
		return txnDay;
	}
	public void setTxnDay(Date txnDay) {
		this.txnDay = txnDay;
	}
	public String getFinalDate() {
		return finalDate;
	}
	public void setFinalDate(String finalDate) {
		this.finalDate = finalDate;
	}
	public String getBankRemarks() {
		return bankRemarks;
	}
	public void setBankRemarks(String bankRemarks) {
		this.bankRemarks = bankRemarks;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getRaddr() {
		return raddr;
	}
	public void setRaddr(String raddr) {
		this.raddr = raddr;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getAgRemarks() {
		return agRemarks;
	}
	public void setAgRemarks(String agRemarks) {
		this.agRemarks = agRemarks;
	}
	public Integer getMaintainUid() {
		return maintainUid;
	}
	public void setMaintainUid(Integer maintainUid) {
		this.maintainUid = maintainUid;
	}
	public Date getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}
	public String getOrderUpload() {
		return orderUpload;
	}
	public void setOrderUpload(String orderUpload) {
		this.orderUpload = orderUpload;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
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
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
