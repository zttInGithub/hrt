package com.hrt.biz.check.entity.model;

import java.util.Date;

/**
 * 
 * @author xmh
 * 实体类
 */

public class CheckUnitDealDataModel {
	
	private Integer cuid;
	private String txnDay;                //交易日期
	private String unno;                  //机构编号
	private String mid;                   //商户编号
	private String tid;                   //终端编号
	private Double txnAmount;             //交易金额
	private Integer TxnCount;            //交易笔数
	private Double RefundAmt;           //退款金额
	private Integer RefundCount;           //退款笔数
	private Double mnamt;                 //商户结算金额
	private Double profit;                //收益
	private Integer maintainUID;          //操作人员
	private Date maintainDate;            //操作日期
	private String maintainType;          //操作类型
	private Double MDA;                   //交易手续费
	private Double REFUNDMDA;             //退款手续费
	

	public Double getMDA() {
		return MDA;
	}
	public void setMDA(Double mDA) {
		MDA = mDA;
	}
	public Double getREFUNDMDA() {
		return REFUNDMDA;
	}
	public void setREFUNDMDA(Double rEFUNDMDA) {
		REFUNDMDA = rEFUNDMDA;
	}
	public Integer getTxnCount() {
		return TxnCount;
	}
	public void setTxnCount(Integer txnCount) {
		TxnCount = txnCount;
	}
	public Double getRefundAmt() {
		return RefundAmt;
	}
	public void setRefundAmt(Double refundAmt) {
		RefundAmt = refundAmt;
	}
	public Integer getRefundCount() {
		return RefundCount;
	}
	public void setRefundCount(Integer refundCount) {
		RefundCount = refundCount;
	}
	public Integer getCuid() {
		return cuid;
	}
	public void setCuid(Integer cuid) {
		this.cuid = cuid;
	}
	public String getTxnDay() {
		return txnDay;
	}
	public void setTxnDay(String txnDay) {
		this.txnDay = txnDay;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
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
	public Double getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
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

	public Integer getMaintainUID() {
		return maintainUID;
	}
	public void setMaintainUID(Integer maintainUID) {
		this.maintainUID = maintainUID;
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
