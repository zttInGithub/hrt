package com.hrt.biz.bill.entity.model;

import java.io.File;
import java.util.Date;

public class MerchantAuthenticityModel {

	private Integer bmatid;			//主键
	private String username;		//app用户名
	private String mid;				//商户编号
	private String legalNum;		//身份证号
	private String bankAccName;		//持卡人名称
	private String bankAccNo;		//卡号
	private String accnoExpdate;	//卡有效期
	private String status;			//认证状态
	private String respcd;			//认证返回码
	private String respinfo;		//认证返回信息
	private Date  cdate;			//认证时间
	private String sysseqnb;		//流水号
	private String cardName;		//通道
	private String unitName ;		//归属
	private String authType ;		//认证类型 mer 商户；txn 交易
	private String  approveNote;		//交易认证退回原因
	private String  authUpload;		//交易认证图片名
	private File  authUploadFile;		//交易认证图片
	private String appcd;			//认证APP返回码
	private String cardholderName;	//持卡人姓名
	private String sendType;		//类型 1插卡；2刷卡；3手填;4非接
	
	public String getCardholderName() {
		return cardholderName;
	}
	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	private String unno;			
	
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getAppcd() {
		return appcd;
	}
	public void setAppcd(String appcd) {
		this.appcd = appcd;
	}
	public File getAuthUploadFile() {
		return authUploadFile;
	}
	public void setAuthUploadFile(File authUploadFile) {
		this.authUploadFile = authUploadFile;
	}
	public String getApproveNote() {
		return approveNote;
	}
	public void setApproveNote(String approveNote) {
		this.approveNote = approveNote;
	}
	public String getAuthUpload() {
		return authUpload;
	}
	public void setAuthUpload(String authUpload) {
		this.authUpload = authUpload;
	}
	public Integer getBmatid() {
		return bmatid;
	}
	public void setBmatid(Integer bmatid) {
		this.bmatid = bmatid;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getLegalNum() {
		return legalNum;
	}
	public void setLegalNum(String legalNum) {
		this.legalNum = legalNum;
	}
	public String getBankAccName() {
		return bankAccName;
	}
	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	public String getAccnoExpdate() {
		return accnoExpdate;
	}
	public void setAccnoExpdate(String accnoExpdate) {
		this.accnoExpdate = accnoExpdate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRespcd() {
		return respcd;
	}
	public void setRespcd(String respcd) {
		this.respcd = respcd;
	}
	public String getRespinfo() {
		return respinfo;
	}
	public void setRespinfo(String respinfo) {
		this.respinfo = respinfo;
	}
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	public String getSysseqnb() {
		return sysseqnb;
	}
	public void setSysseqnb(String sysseqnb) {
		this.sysseqnb = sysseqnb;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	
}
