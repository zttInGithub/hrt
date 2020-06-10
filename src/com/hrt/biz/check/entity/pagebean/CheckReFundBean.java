package com.hrt.biz.check.entity.pagebean;

import java.io.File;
import java.util.Date;

public class CheckReFundBean {
	
	private Integer refId; // 自增主键
	private Integer pkId; // 交易表主键 2.车辆代缴
	private String mid; // 商户编号
	private String rrn; // 交易参考号
	private String oriOrderId; // 原交易订单号
	private String cardPan; // 交易卡号
	private Double samt; // 原始交易金额
	private Date txnDay; // 交易时间
	private Date txnDay1; // 交易时间
	private String txnDayStr; // 交易时间
	private Double ramt; // 差错金额
	private Integer settlement; // 结算方式
	private String remarks; // 备注
	private File refundImg; // 电子签名
	private String refundImgName; // 电子签名
	private String isM35; 
	private Date approveDate; // 提交日期
	private Date approveDate1; // 提交日期1
	private Integer approveUid; // 提交人
	private Date maintainDate; //操作日期
	private String status; // 状态 W-待提交 C-审核中 Y-审核成功 F-审核失败 K-退回
	private Integer maintainUid; //操作人
	private Integer page;			//当前页数
	private Integer rows;			//总记录数
	//排序字段
	private String sort;
	
	//排序规则 ASC DESC
	private String order;
	
	public Date getApproveDate1() {
		return approveDate1;
	}
	public void setApproveDate1(Date approveDate1) {
		this.approveDate1 = approveDate1;
	}
	public String getRefundImgName() {
		return refundImgName;
	}
	public void setRefundImgName(String refundImgName) {
		this.refundImgName = refundImgName;
	}
	public File getRefundImg() {
		return refundImg;
	}
	public void setRefundImg(File refundImg) {
		this.refundImg = refundImg;
	}
	public String getIsM35() {
		return isM35;
	}
	public void setIsM35(String isM35) {
		this.isM35 = isM35;
	}
	public String getOriOrderId() {
		return oriOrderId;
	}
	public void setOriOrderId(String oriOrderId) {
		this.oriOrderId = oriOrderId;
	}
	public Date getTxnDay1() {
		return txnDay1;
	}
	public void setTxnDay1(Date txnDay1) {
		this.txnDay1 = txnDay1;
	}
	public Double getSamt() {
		return samt;
	}
	public void setSamt(Double samt) {
		this.samt = samt;
	}
	public Double getRamt() {
		return ramt;
	}
	public void setRamt(Double ramt) {
		this.ramt = ramt;
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
	public String getTxnDayStr() {
		return txnDayStr;
	}
	public void setTxnDayStr(String txnDayStr) {
		this.txnDayStr = txnDayStr;
	}
	public Integer getRefId() {
		return refId;
	}
	public void setRefId(Integer refId) {
		this.refId = refId;
	}
	public Integer getPkId() {
		return pkId;
	}
	public void setPkId(Integer pkId) {
		this.pkId = pkId;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public String getCardPan() {
		return cardPan;
	}
	public void setCardPan(String cardPan) {
		this.cardPan = cardPan;
	}
	public Date getTxnDay() {
		return txnDay;
	}
	public void setTxnDay(Date txnDay) {
		this.txnDay = txnDay;
	}
	public Integer getSettlement() {
		return settlement;
	}
	public void setSettlement(Integer settlement) {
		this.settlement = settlement;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	public Integer getApproveUid() {
		return approveUid;
	}
	public void setApproveUid(Integer approveUid) {
		this.approveUid = approveUid;
	}
	public Date getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getMaintainUid() {
		return maintainUid;
	}
	public void setMaintainUid(Integer maintainUid) {
		this.maintainUid = maintainUid;
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
	
}
