package com.hrt.biz.bill.entity.model;

import java.util.Date;

/**
 * 交行数据
 */
public class CheckUnitdealdetailCommFCModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer pkid;
	private String mid;
	private String merchname;
	private String devno;
	private String cardkind;
	private String cardno;
	private Double txnamt;
	private Double mda;
	private Date txndate;
	private Date signdate;
	private String minfo1;
	private String minfo2;
	private String remarks;
	
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
	public String getMerchname() {
		return merchname;
	}
	public void setMerchname(String merchname) {
		this.merchname = merchname;
	}
	public String getDevno() {
		return devno;
	}
	public void setDevno(String devno) {
		this.devno = devno;
	}
	public String getCardkind() {
		return cardkind;
	}
	public void setCardkind(String cardkind) {
		this.cardkind = cardkind;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public Double getTxnamt() {
		return txnamt;
	}
	public void setTxnamt(Double txnamt) {
		this.txnamt = txnamt;
	}
	public Double getMda() {
		return mda;
	}
	public void setMda(Double mda) {
		this.mda = mda;
	}
	public Date getTxndate() {
		return txndate;
	}
	public void setTxndate(Date txndate) {
		this.txndate = txndate;
	}
	public Date getSigndate() {
		return signdate;
	}
	public void setSigndate(Date signdate) {
		this.signdate = signdate;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}