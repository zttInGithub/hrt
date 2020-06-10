package com.hrt.biz.check.entity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 扣款
 */
public class CheckDeductionModel implements Serializable {

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

	public CheckDeductionModel() {
	}

	public CheckDeductionModel(Integer cdId, String yiDai, String yiDaiName, String erDai, String erDaiName, String unno, String unnoName, String yunYing, String mid, String sn, Integer snType, Date usedDate, Date keyConfirmDate, Date outDate, Double deduction, String rebateType, Date maintainDate) {
		this.cdId = cdId;
		this.yiDai = yiDai;
		this.yiDaiName = yiDaiName;
		this.erDai = erDai;
		this.erDaiName = erDaiName;
		this.unno = unno;
		this.unnoName = unnoName;
		this.yunYing = yunYing;
		this.mid = mid;
		this.sn = sn;
		this.snType = snType;
		this.usedDate = usedDate;
		this.keyConfirmDate = keyConfirmDate;
		this.outDate = outDate;
		this.deduction = deduction;
		this.rebateType = rebateType;
		this.maintainDate = maintainDate;
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
