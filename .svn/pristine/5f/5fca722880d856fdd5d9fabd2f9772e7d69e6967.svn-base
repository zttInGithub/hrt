package com.hrt.phone.entity.model;

import java.util.Date;

/**
 * 车辆违章订单信息
 * @author sun
 *
 */
public class GateOrderModel {
	
	private Integer goId;           //主键
	private String mid;             //商户主键
	private String hrtOrderId;      //和融通号
	private String orderId;         //三方订单号
	private String payOrderId;      //付款单号
	private String priceId;         //违章办理报价接口获得的id
	private String uniqueCode;      //唯一code
	private String isQuick;         //是否快速办理 0：否  1：是
	private String isUsPrice;       //是否本人下单 0：否  1：是
	private String batchId;         //订单批次id
	private String totalFee;        //订单总金额
	private String amount;          //补款金额
	private Integer privateFlag;    //是否是私家车 0：否  1：是
	private Date placeOrderTime;    //app下单时间
	private Date appPayOrderTime;    //app支付通知时间
	private String state;           //订单状态：-1 支付失败, 0：等待支付，1：已支付/待确认，9已下单，2：确认到账失败，3(补款)、4(补资料)、5、6：订单处理中，7：订单处理失败，8：成功
									//app 全部null ;  0：等待支付;  7：订单处理失败(7,-1)，8：订单成功 ；3订单处理中(1,9,3,4)
	private String msg;             //同步消息描述
	private String passMemo;        //异步受理信息
	private Date rebackorderTime;   //异步通知时间
	private Date mainTainDate;    //操作时间
	private String mainTainUid;   //操作人
	private String mainTainType; //操作状态
	private String waitUploadImgIds;//需补资料imgId
	private String oldHrtOrderId;      //和融通号
	
	
	public String getOldHrtOrderId() {
		return oldHrtOrderId;
	}
	public void setOldHrtOrderId(String oldHrtOrderId) {
		this.oldHrtOrderId = oldHrtOrderId;
	}
	public String getWaitUploadImgIds() {
		return waitUploadImgIds;
	}
	public void setWaitUploadImgIds(String waitUploadImgIds) {
		this.waitUploadImgIds = waitUploadImgIds;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public Date getAppPayOrderTime() {
		return appPayOrderTime;
	}
	public void setAppPayOrderTime(Date appPayOrderTime) {
		this.appPayOrderTime = appPayOrderTime;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMid() {
		return mid;
	}
	public Date getMainTainDate() {
		return mainTainDate;
	}
	public void setMainTainDate(Date mainTainDate) {
		this.mainTainDate = mainTainDate;
	}
	public String getMainTainUid() {
		return mainTainUid;
	}
	public void setMainTainUid(String mainTainUid) {
		this.mainTainUid = mainTainUid;
	}
	public String getMainTainType() {
		return mainTainType;
	}
	public void setMainTainType(String mainTainType) {
		this.mainTainType = mainTainType;
	}
	public Integer getGoId() {
		return goId;
	}
	public void setGoId(Integer goId) {
		this.goId = goId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPriceId() {
		return priceId;
	}
	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}
	public String getUniqueCode() {
		return uniqueCode;
	}
	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}
	public String getIsQuick() {
		return isQuick;
	}
	public void setIsQuick(String isQuick) {
		this.isQuick = isQuick;
	}
	public String getIsUsPrice() {
		return isUsPrice;
	}
	public void setIsUsPrice(String isUsPrice) {
		this.isUsPrice = isUsPrice;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public Integer getPrivateFlag() {
		return privateFlag;
	}
	public void setPrivateFlag(Integer privateFlag) {
		this.privateFlag = privateFlag;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPassMemo() {
		return passMemo;
	}
	public void setPassMemo(String passMemo) {
		this.passMemo = passMemo;
	}
	public String getHrtOrderId() {
		return hrtOrderId;
	}
	public void setHrtOrderId(String hrtOrderId) {
		this.hrtOrderId = hrtOrderId;
	}
	public String getPayOrderId() {
		return payOrderId;
	}
	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}
	public Date getPlaceOrderTime() {
		return placeOrderTime;
	}
	public void setPlaceOrderTime(Date placeOrderTime) {
		this.placeOrderTime = placeOrderTime;
	}
	public Date getRebackorderTime() {
		return rebackorderTime;
	}
	public void setRebackorderTime(Date rebackorderTime) {
		this.rebackorderTime = rebackorderTime;
	}
	
}
