package com.hrt.biz.bill.entity.model;

import java.util.Date;

@SuppressWarnings("serial")
public class PgUnnoPurseModel implements java.io.Serializable{
	
	private Integer pupid;
	
	private String unno;
	
	private Double curamt;//可提现金额
	
	private Double balance;//钱包余额
	
	private Integer status;//状态 1可用，0停用（冻结）
	
	private String remarks;
	
	private String minfo1;

	private Date cdate;
	
	private Double balancetotal;//总收入
	
	private Double frozenamt;//冻结金额

	public Integer getPupid() {
		return pupid;
	}

	public void setPupid(Integer pupid) {
		this.pupid = pupid;
	}

	public String getUnno() {
		return unno;
	}

	public void setUnno(String unno) {
		this.unno = unno;
	}

	public Double getCuramt() {
		return curamt;
	}

	public void setCuramt(Double curamt) {
		this.curamt = curamt;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getFrozenamt() {
		return frozenamt;
	}

	public void setFrozenamt(Double frozenamt) {
		this.frozenamt = frozenamt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMinfo1() {
		return minfo1;
	}

	public void setMinfo1(String minfo1) {
		this.minfo1 = minfo1;
	}

	public Double getBalancetotal() {
		return balancetotal;
	}

	public void setBalancetotal(Double balancetotal) {
		this.balancetotal = balancetotal;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
}
