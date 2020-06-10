package com.hrt.biz.bill.entity.pagebean;

import java.util.Date;

public class CheckUnitdealCommBean {
	private static final long serialVersionUID = 1L;
	//当前页数
	private Integer page;
	
	//总记录数
	private Integer rows;
	
	//排序字段
	private String sort;
	
	//排序规则 ASC DESC
	private String order;
	private Integer pkid;
	private String cbrand;
	private String mid;
	private String merchname;
	private String devno;
	private String cardno;
	private Double txnamt;
	private Double mda;
	private Date txndate;
	private Date signdate;
	private String minfo1;
	private String minfo2;
	private String remarks;
	private String createDate;
	private String createDateone;
	
	
	public String getCreateDateone() {
		return createDateone;
	}
	public void setCreateDateone(String createDateone) {
		this.createDateone = createDateone;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
	public Integer getPkid() {
		return pkid;
	}
	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}
	public String getCbrand() {
		return cbrand;
	}
	public void setCbrand(String cbrand) {
		this.cbrand = cbrand;
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
