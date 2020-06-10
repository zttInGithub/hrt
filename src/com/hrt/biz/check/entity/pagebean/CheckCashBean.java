package com.hrt.biz.check.entity.pagebean;

import java.util.Date;

/**
 * 
 * @author XXX
 *
 */
public class CheckCashBean {

	private Integer pcid;
	private String mid;
	private String cashDay;
	private Double cashAmt;
	private Double cashFee;
	private Integer cashStatus;
	private Date cashDate;
	private String cashDateStart;
	private String cashDateEnd;
	
	//当前页数
	private int page;
	//总记录数
	private int rows;
	
	//排序字段
	private String sort;
	
	//排序规则 ASC DESC
	private String order;
	
	

	public Integer getPcid() {
		return pcid;
	}
	public void setPcid(Integer pcid) {
		this.pcid = pcid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getCashDay() {
		return cashDay;
	}
	public void setCashDay(String cashDay) {
		this.cashDay = cashDay;
	}
	public Double getCashAmt() {
		return cashAmt;
	}
	public void setCashAmt(Double cashAmt) {
		this.cashAmt = cashAmt;
	}
	public Double getCashFee() {
		return cashFee;
	}
	public void setCashFee(Double cashFee) {
		this.cashFee = cashFee;
	}
	public Integer getCashStatus() {
		return cashStatus;
	}
	public void setCashStatus(Integer cashStatus) {
		this.cashStatus = cashStatus;
	}
	public Date getCashDate() {
		return cashDate;
	}
	public void setCashDate(Date cashDate) {
		this.cashDate = cashDate;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
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
	public String getCashDateStart() {
		return cashDateStart;
	}
	public void setCashDateStart(String cashDateStart) {
		this.cashDateStart = cashDateStart;
	}
	public String getCashDateEnd() {
		return cashDateEnd;
	}
	public void setCashDateEnd(String cashDateEnd) {
		this.cashDateEnd = cashDateEnd;
	}
	
}
