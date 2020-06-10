package com.hrt.biz.check.entity.model;

public class BlockTradeDetailModel {

	
	private Integer pkid;		//实时交易表共同主键
	private Integer ifUpload;		//是否已上传小票   0：未上传  1：已上传
	private Integer ifSettleFlag;	//是否已结算              0：未结算  1：已结算
	private String txnday;
	private Integer risktype;   //小票审核 类型 0：    1:接收另一个系统传过来的mid查询实时交易表插入的
	private String riskday;		//审核通过日期
	private String minfo1;			
	private String minfo2;
	
	
	
	
	
	public Integer getPkid() {
		return pkid;
	}
	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}
	public Integer getIfUpload() {
		return ifUpload;
	}
	public void setIfUpload(Integer ifUpload) {
		this.ifUpload = ifUpload;
	}
	public Integer getIfSettleFlag() {
		return ifSettleFlag;
	}
	public void setIfSettleFlag(Integer ifSettleFlag) {
		this.ifSettleFlag = ifSettleFlag;
	}
	public String getMinfo1() {
		return minfo1;
	}
	public void setMinfo1(String minfo1) {
		this.minfo1 = minfo1;
	}
	public String getMinfo2() {
		return minfo2;
	}
	public void setMinfo2(String minfo2) {
		this.minfo2 = minfo2;
	}
	public String getTxnday() {
		return txnday;
	}
	public void setTxnday(String txnday) {
		this.txnday = txnday;
	}
	public Integer getRisktype() {
		return risktype;
	}
	public void setRisktype(Integer risktype) {
		this.risktype = risktype;
	}
	public String getRiskday() {
		return riskday;
	}
	public void setRiskday(String riskday) {
		this.riskday = riskday;
	}
	
}
