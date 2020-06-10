package com.hrt.biz.bill.entity.model;

import java.util.Date;

/**
 * 聚合支付商户费率调整记录
 * @author tenglong
 *
 */
public class MerAdjustRateModel implements java.io.Serializable  {

	private static final long serialVersionUID = 1L;
	
	private Integer mjrid;
	private String mid;
	private Integer settleCycle;//结算周期  0、1
	private String settMethod;//结算方式 000000--钱包提现  100000--秒到   300000 --定时结算 400000--按金额结算
	private Double secondRate;//转账手续费
	private Double scanRate;//费率
	private String preSetTime;//定时时间 9,18
	private Double quotaAmt; //定额结算额度
	private String status;  //E失败;Y成功
	private Date cdate;
	private String cby;
	private Date lmdate;
	private String lmby;
	
	public Double getScanRate() {
		return scanRate;
	}
	public void setScanRate(Double scanRate) {
		this.scanRate = scanRate;
	}
	public Integer getMjrid() {
		return mjrid;
	}
	public void setMjrid(Integer mjrid) {
		this.mjrid = mjrid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public Integer getSettleCycle() {
		return settleCycle;
	}
	public void setSettleCycle(Integer settleCycle) {
		this.settleCycle = settleCycle;
	}
	public String getSettMethod() {
		return settMethod;
	}
	public void setSettMethod(String settMethod) {
		this.settMethod = settMethod;
	}
	public Double getSecondRate() {
		return secondRate;
	}
	public void setSecondRate(Double secondRate) {
		this.secondRate = secondRate;
	}
	public String getPreSetTime() {
		return preSetTime;
	}
	public void setPreSetTime(String preSetTime) {
		this.preSetTime = preSetTime;
	}
	public Double getQuotaAmt() {
		return quotaAmt;
	}
	public void setQuotaAmt(Double quotaAmt) {
		this.quotaAmt = quotaAmt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Date getLmdate() {
		return lmdate;
	}
	public void setLmdate(Date lmdate) {
		this.lmdate = lmdate;
	}
	public String getLmby() {
		return lmby;
	}
	public void setLmby(String lmby) {
		this.lmby = lmby;
	}
}
