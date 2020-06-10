package com.hrt.biz.bill.entity.pagebean;

import java.util.Date;

public class CheckIncomeBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	
	//当前页数
		private Integer page;
		
		//总记录数
		private Integer rows;
		
		//排序字段
		private String sort;
		
		//排序规则 ASC DESC
	private Integer  ciid;
	  private String  mid;
	  private String  settleday;
	  private String  txnday;  
	  private Double totsamt;   
	  private Double totmfee;
	  private Double totramt;  
	  private Double totaamt;  
	  private Double totmnamt;    
	  private String baccno;
	  private Integer status;
	  private String  minfo1;    
	  private String  minfo2;     
	  private String remarks;
	  private String  dateone;
	  private String  datetwo;
	  private String  txnday2;  
	  
	  
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
	public String getDateone() {
		return dateone;
	}
	public void setDateone(String dateone) {
		this.dateone = dateone;
	}
	public String getDatetwo() {
		return datetwo;
	}
	public void setDatetwo(String datetwo) {
		this.datetwo = datetwo;
	}
	public Integer getCiid() {
		return ciid;
	}
	public void setCiid(Integer ciid) {
		this.ciid = ciid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getSettleday() {
		return settleday;
	}
	public void setSettleday(String settleday) {
		this.settleday = settleday;
	}
	public String getTxnday() {
		return txnday;
	}
	public void setTxnday(String txnday) {
		this.txnday = txnday;
	}
	public Double getTotsamt() {
		return totsamt;
	}
	public void setTotsamt(Double totsamt) {
		this.totsamt = totsamt;
	}
	public Double getTotmfee() {
		return totmfee;
	}
	public void setTotmfee(Double totmfee) {
		this.totmfee = totmfee;
	}
	public Double getTotramt() {
		return totramt;
	}
	public void setTotramt(Double totramt) {
		this.totramt = totramt;
	}
	public Double getTotaamt() {
		return totaamt;
	}
	public void setTotaamt(Double totaamt) {
		this.totaamt = totaamt;
	}
	public Double getTotmnamt() {
		return totmnamt;
	}
	public void setTotmnamt(Double totmnamt) {
		this.totmnamt = totmnamt;
	}
	public String getBaccno() {
		return baccno;
	}
	public void setBaccno(String baccno) {
		this.baccno = baccno;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getTxnday2() {
		return txnday2;
	}
	public void setTxnday2(String txnday2) {
		this.txnday2 = txnday2;
	}
	
	  
}
