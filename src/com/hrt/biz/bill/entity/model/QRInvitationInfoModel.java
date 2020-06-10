package com.hrt.biz.bill.entity.model;

import java.util.Date;

/**
 * 手刷商户注册二维码邀请码信息
 * @author tenglong
 *
 */
public class QRInvitationInfoModel implements java.io.Serializable  {

	private static final long serialVersionUID = 1L;
	
	private Integer icid;
	private String unno;
	private String mid;
	private String invitationCode;
	private String icPassword;
	private Double scanRate;
	private Integer status;          //0未使用; 1已使  ; 2禁用;
	private Date usedConfirmDate;
	private Date maintainDate;
	private Integer maintainUserId;
	private String minfo1;
	private String minfo2;
	public Integer getIcid() {
		return icid;
	}
	public void setIcid(Integer icid) {
		this.icid = icid;
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
	public String getInvitationCode() {
		return invitationCode;
	}
	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}
	public String getIcPassword() {
		return icPassword;
	}
	public void setIcPassword(String icPassword) {
		this.icPassword = icPassword;
	}
	public Double getScanRate() {
		return scanRate;
	}
	public void setScanRate(Double scanRate) {
		this.scanRate = scanRate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getUsedConfirmDate() {
		return usedConfirmDate;
	}
	public void setUsedConfirmDate(Date usedConfirmDate) {
		this.usedConfirmDate = usedConfirmDate;
	}
	public Date getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}
	public Integer getMaintainUserId() {
		return maintainUserId;
	}
	public void setMaintainUserId(Integer maintainUserId) {
		this.maintainUserId = maintainUserId;
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
	
}
