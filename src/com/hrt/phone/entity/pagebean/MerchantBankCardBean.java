package com.hrt.phone.entity.pagebean;

import java.util.Date;

public class MerchantBankCardBean {

	private Integer mbcid;		//主键
	private String mid;			//商户编号
	private String bankAccNo;	//银行账号
	private String bankAccName;	//银行账户名称
	private Date createDate;	//创建日期
	private String payBankId;	//支付系统行号
	private String bankBranch;	//银行名称
	private Date matainDate;
	private Integer status;		//0：可用  1：停用
	private Integer merCardType; //0:默认，不可删除 1：可删除
	
	private Integer page;			//当前页数
	private Integer rows;			//总记录数
	private String sort;			//排序字段
	private String order;			//排序规则 ASC DESC
	
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
	public Integer getMbcid() {
		return mbcid;
	}
	public void setMbcid(Integer mbcid) {
		this.mbcid = mbcid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	public String getBankAccName() {
		return bankAccName;
	}
	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getMatainDate() {
		return matainDate;
	}
	public void setMatainDate(Date matainDate) {
		this.matainDate = matainDate;
	}
	public void setMerCardType(Integer merCardType) {
		this.merCardType = merCardType;
	}
	public Integer getMerCardType() {
		return merCardType;
	}
	public String getPayBankId() {
		return payBankId;
	}
	public void setPayBankId(String payBankId) {
		this.payBankId = payBankId;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	
}
