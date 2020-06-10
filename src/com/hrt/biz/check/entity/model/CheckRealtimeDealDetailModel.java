package com.hrt.biz.check.entity.model;


public class CheckRealtimeDealDetailModel {

	private Integer pkid;       //唯一编号
	private String mid;           //商户编号
	private String tid;          //终端号
	private String cardpan;       //卡号
	private String txnamount;      //交易金额
	private String txnday;         // 交易日期
	private String txndate;        //交易时间
	private String txntype;          // 交易种类
	private String authcode;      //授权号
	private String stan;          //流水号
	private String rrn;            //系统参考号
	private String respcode;           //交易状态
	private String proccode;		//处理码
	private Integer cardtype;		//卡种  1：借记卡 2：贷记卡 3：预付费卡
	private String sources;			//商户来源
 



	public Integer getPkid() {
		return pkid;
	}
	public void setPkid(Integer pkid) {
		this.pkid = pkid;
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
	public String getCardpan() {
		return cardpan;
	}
	public void setCardpan(String cardpan) {
		this.cardpan = cardpan;
	}
	public String getTxnamount() {
		return txnamount;
	}
	public void setTxnamount(String txnamount) {
		this.txnamount = txnamount;
	}
	public String getTxnday() {
		return txnday;
	}
	public void setTxnday(String txnday) {
		this.txnday = txnday;
	}
	public String getTxndate() {
		return txndate;
	}
	public void setTxndate(String txndate) {
		this.txndate = txndate;
	}
	public String getTxntype() {
		return txntype;
	}
	public void setTxntype(String txntype) {
		this.txntype = txntype;
	}
	public String getAuthcode() {
		return authcode;
	}
	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}
	public String getStan() {
		return stan;
	}
	public void setStan(String stan) {
		this.stan = stan;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public String getRespcode() {
		return respcode;
	}
	public void setRespcode(String respcode) {
		this.respcode = respcode;
	}
	public String getProccode() {
		return proccode;
	}
	public void setProccode(String proccode) {
		this.proccode = proccode;
	}
	public Integer getCardtype() {
		return cardtype;
	}
	public void setCardtype(Integer cardtype) {
		this.cardtype = cardtype;
	}
	public String getSources() {
		return sources;
	}
	public void setSources(String sources) {
		this.sources = sources;
	}

	
}
