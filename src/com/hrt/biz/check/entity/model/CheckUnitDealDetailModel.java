package com.hrt.biz.check.entity.model;

import java.util.Date;

/**
 * 2014-2-26
 * @author xmh
 * 实体类
 */

public class CheckUnitDealDetailModel {
	
	private Integer bdid;         //
	private String mid;          //商户编号
	private String tid;          //终端编号
	private String cardPan;      //卡号
	private String cbrand;       //卡别
	private Double txnAmount;    //交易金额
	private String txnDay;       //交易日期
	private String txnDate;      //交易时间
	private String txnType;      //交易类型
	private String authCode;     //授权码
	private String stan;         //流水号
	private String rrn;          //系统参考号
	private Double mda;          //商户手续费
	private Double mnamt;        //商户结算金额
	private Double profit;       //收益
	private String settleDate;   //结算日期
	private Integer maintainUid; //操作人员
	private Date maintainDate;   //操作日期
	private String maintainType; //操作类型
	private String actionsetttledate; 
	private String  schedulesettledate;  
	private String ifcard;
	public String getIfcard() {
		return ifcard;
	}
	public void setIfcard(String ifcard) {
		this.ifcard = ifcard;
	}
	public String getSchedulesettledate() {
		return schedulesettledate;
	}
	public void setSchedulesettledate(String schedulesettledate) {
		this.schedulesettledate = schedulesettledate;
	}
	public String getActionsetttledate() {
		return actionsetttledate;
	}
	public void setActionsetttledate(String actionsetttledate) {
		this.actionsetttledate = actionsetttledate;
	}
	public Integer getBdid() {
		return bdid;
	}
	public void setBdid(Integer bdid) {
		this.bdid = bdid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getCardPan() {
		return cardPan;
	}
	public void setCardPan(String cardPan) {
		this.cardPan = cardPan;
	}
	public String getCbrand() {
		return cbrand;
	}
	public void setCbrand(String cbrand) {
		this.cbrand = cbrand;
	}
	public Double getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}
	public String getTxnDay() {
		return txnDay;
	}
	public void setTxnDay(String txnDay) {
		this.txnDay = txnDay;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getStan() {
		return stan;
	}
	public void setStan(String stan) {
		this.stan = stan;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public Double getMda() {
		return mda;
	}
	public void setMda(Double mda) {
		this.mda = mda;
	}
	public Double getMnamt() {
		return mnamt;
	}
	public void setMnamt(Double mnamt) {
		this.mnamt = mnamt;
	}
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public Integer getMaintainUid() {
		return maintainUid;
	}
	public void setMaintainUid(Integer maintainUid) {
		this.maintainUid = maintainUid;
	}
	public Date getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}
	public String getMaintainType() {
		return maintainType;
	}
	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}
	
	
}
