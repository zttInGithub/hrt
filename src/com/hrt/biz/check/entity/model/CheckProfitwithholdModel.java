package com.hrt.biz.check.entity.model;

import java.math.BigDecimal;
import java.util.Date;

public class CheckProfitwithholdModel {

	private Integer id; // 自增主键
	private String applicantUnno; // 申请机构
	private String executingUnno; // 被执行机构
	private BigDecimal withholdingAmount; // 代扣金额
	private String deductionSource;//扣款来源
	private String effectiveDate; // 生效日期
	private String cby; // 创建用户
	private String agreePerson; // 同意用户
	private Date agreeDate;//同意日期
	private String approveStatus; // 记录状态，1待确认；2已驳回；3已确认
	private String maintainType; // 操作类型
	private Date maintainDate; // 操作日期
	private String minfo1; // 备用1
	private String minfo2; // 备用2
	private String remark; //备注
	public String getDeductionSource() {
		return deductionSource;
	}
	public void setDeductionSource(String deductionSource) {
		this.deductionSource = deductionSource;
	}
	public Date getAgreeDate() {
		return agreeDate;
	}
	public void setAgreeDate(Date agreeDate) {
		this.agreeDate = agreeDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getApplicantUnno() {
		return applicantUnno;
	}
	public void setApplicantUnno(String applicantUnno) {
		this.applicantUnno = applicantUnno;
	}
	public String getExecutingUnno() {
		return executingUnno;
	}
	public void setExecutingUnno(String executingUnno) {
		this.executingUnno = executingUnno;
	}
	public BigDecimal getWithholdingAmount() {
		return withholdingAmount;
	}
	public void setWithholdingAmount(BigDecimal withholdingAmount) {
		this.withholdingAmount = withholdingAmount;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getCby() {
		return cby;
	}
	public void setCby(String cby) {
		this.cby = cby;
	}
	public String getAgreePerson() {
		return agreePerson;
	}
	public void setAgreePerson(String agreePerson) {
		this.agreePerson = agreePerson;
	}
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public String getMaintainType() {
		return maintainType;
	}
	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}
	public Date getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
