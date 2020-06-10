package com.hrt.biz.check.entity.pagebean;

import java.io.Serializable;
import java.util.Date;

/**
 * 扣款
 */
public class CheckDeductionBean implements Serializable {
	//当前页数
	private int page;
	//总记录数
	private int rows;
	//排序字段
	private String sort;
	//排序规则 ASC DESC
	private String order;

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

	private Integer cdId;
    private String yiDai;
    private String yiDaiName;
    private String erDai;
    private String erDaiName;
    private String unno;
    private String unnoName;
    private String yunYing;
    private String mid;
    private String sn;
    private Integer snType;
    private Date usedDate;
    private Date keyConfirmDate;
    private Date outDate;
    private Double deduction;
    private String rebateType;
    private Date maintainDate;
    private Date maintainDate1;

	public Date getMaintainDate1() {
		return maintainDate1;
	}

	public void setMaintainDate1(Date maintainDate1) {
		this.maintainDate1 = maintainDate1;
	}

	public Integer getCdId() {
		return cdId;
	}

	public void setCdId(Integer cdId) {
		this.cdId = cdId;
	}

	public String getYiDai() {
		return yiDai;
	}

	public void setYiDai(String yiDai) {
		this.yiDai = yiDai;
	}

	public String getYiDaiName() {
		return yiDaiName;
	}

	public void setYiDaiName(String yiDaiName) {
		this.yiDaiName = yiDaiName;
	}

	public String getErDai() {
		return erDai;
	}

	public void setErDai(String erDai) {
		this.erDai = erDai;
	}

	public String getErDaiName() {
		return erDaiName;
	}

	public void setErDaiName(String erDaiName) {
		this.erDaiName = erDaiName;
	}

	public String getUnno() {
		return unno;
	}

	public void setUnno(String unno) {
		this.unno = unno;
	}

	public String getUnnoName() {
		return unnoName;
	}

	public void setUnnoName(String unnoName) {
		this.unnoName = unnoName;
	}

	public String getYunYing() {
		return yunYing;
	}

	public void setYunYing(String yunYing) {
		this.yunYing = yunYing;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getSnType() {
		return snType;
	}

	public void setSnType(Integer snType) {
		this.snType = snType;
	}

	public Date getUsedDate() {
		return usedDate;
	}

	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}

	public Date getKeyConfirmDate() {
		return keyConfirmDate;
	}

	public void setKeyConfirmDate(Date keyConfirmDate) {
		this.keyConfirmDate = keyConfirmDate;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public Double getDeduction() {
		return deduction;
	}

	public void setDeduction(Double deduction) {
		this.deduction = deduction;
	}

	public String getRebateType() {
		return rebateType;
	}

	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}

	public Date getMaintainDate() {
		return maintainDate;
	}

	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}
}
