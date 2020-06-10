package com.hrt.biz.check.entity.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 定制活动返现
 */
public class PgCashbackSpecialModel implements Serializable {
	private Integer pcsid;
	private String mid;
	private String returnday;
	private BigDecimal summoney;
	private Integer rebatetype;
	private BigDecimal returnmoney;
	private Integer returntime;
	private Integer status;
	private Date cdate;
	private String remarks1;
	private String remarks2;
	public Integer getPcsid() {
		return pcsid;
	}
	public void setPcsid(Integer pcsid) {
		this.pcsid = pcsid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getReturnday() {
		return returnday;
	}
	public void setReturnday(String returnday) {
		this.returnday = returnday;
	}
	public BigDecimal getSummoney() {
		return summoney;
	}
	public void setSummoney(BigDecimal summoney) {
		this.summoney = summoney;
	}
	public Integer getRebatetype() {
		return rebatetype;
	}
	public void setRebatetype(Integer rebatetype) {
		this.rebatetype = rebatetype;
	}
	public BigDecimal getReturnmoney() {
		return returnmoney;
	}
	public void setReturnmoney(BigDecimal returnmoney) {
		this.returnmoney = returnmoney;
	}
	public Integer getReturntime() {
		return returntime;
	}
	public void setReturntime(Integer returntime) {
		this.returntime = returntime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	public String getRemarks1() {
		return remarks1;
	}
	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}
	public String getRemarks2() {
		return remarks2;
	}
	public void setRemarks2(String remarks2) {
		this.remarks2 = remarks2;
	}
}
