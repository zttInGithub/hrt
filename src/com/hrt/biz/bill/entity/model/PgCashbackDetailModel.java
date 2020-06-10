package com.hrt.biz.bill.entity.model;

import java.math.BigDecimal;
import java.util.Date;

public class PgCashbackDetailModel {
	private Integer pcdid;
	private String txnday;
	private String uppUnno;
	private String unno;
	private String unName;
	private String mid;
	private String rebatetype;
	private String sn;
	private BigDecimal samt;
	private BigDecimal cashbackType;
	private BigDecimal cashbackAmt;
	private Date cdate;
	private String cby;
	private String minfo1;
	private String minfo2;
	private BigDecimal pctid;
	public Integer getPcdid() {
		return pcdid;
	}
	public void setPcdid(Integer pcdid) {
		this.pcdid = pcdid;
	}
	public String getTxnday() {
		return txnday;
	}
	public void setTxnday(String txnday) {
		this.txnday = txnday;
	}
	public String getUppUnno() {
		return uppUnno;
	}
	public void setUppUnno(String uppUnno) {
		this.uppUnno = uppUnno;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getUnName() {
		return unName;
	}
	public void setUnName(String unName) {
		this.unName = unName;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getRebatetype() {
		return rebatetype;
	}
	public void setRebatetype(String rebatetype) {
		this.rebatetype = rebatetype;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public BigDecimal getSamt() {
		return samt;
	}
	public void setSamt(BigDecimal samt) {
		this.samt = samt;
	}
	public BigDecimal getCashbackType() {
		return cashbackType;
	}
	public void setCashbackType(BigDecimal cashbackType) {
		this.cashbackType = cashbackType;
	}
	public BigDecimal getCashbackAmt() {
		return cashbackAmt;
	}
	public void setCashbackAmt(BigDecimal cashbackAmt) {
		this.cashbackAmt = cashbackAmt;
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
	public BigDecimal getPctid() {
		return pctid;
	}
	public void setPctid(BigDecimal pctid) {
		this.pctid = pctid;
	}
}
