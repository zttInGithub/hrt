package com.hrt.biz.bill.entity.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * BillRebateRuleModel
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2020/01/15
 * @since 1.8
 **/
public class RebateRuleModel {
    private Integer brrId;
    private Integer pointType;
    private Integer txnWay;
    private Integer timeType;
    private Integer cycle;
    private Integer startTime;
    private Integer endTime;
    private Integer txnType;
    private BigDecimal txnAmount;
    private Integer backType1;
    private BigDecimal backAmount1;
    private Integer backType2;
    private BigDecimal backAmount2;
    private Integer startMonth;
    private Integer endMonth;
    private BigDecimal paymentAmount;
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
    private String remark1;
    private String remark2;
    private String brrName;
    private  Integer auditStatus;
    private String rejectReason;
    private Date   auditDate;
    private String auditUser;
    
    public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getBrrName() {
        return brrName;
    }

    public void setBrrName(String brrName) {
        this.brrName = brrName;
    }

    public Integer getBrrId() {
        return brrId;
    }

    public void setBrrId(Integer brrId) {
        this.brrId = brrId;
    }

    public Integer getPointType() {
        return pointType;
    }

    public void setPointType(Integer pointType) {
        this.pointType = pointType;
    }

    public Integer getTxnWay() {
        return txnWay;
    }

    public void setTxnWay(Integer txnWay) {
        this.txnWay = txnWay;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getTxnType() {
        return txnType;
    }

    public void setTxnType(Integer txnType) {
        this.txnType = txnType;
    }

    public BigDecimal getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(BigDecimal txnAmount) {
        this.txnAmount = txnAmount;
    }

    public Integer getBackType1() {
        return backType1;
    }

    public void setBackType1(Integer backType1) {
        this.backType1 = backType1;
    }

    public BigDecimal getBackAmount1() {
        return backAmount1;
    }

    public void setBackAmount1(BigDecimal backAmount1) {
        this.backAmount1 = backAmount1;
    }

    public Integer getBackType2() {
        return backType2;
    }

    public void setBackType2(Integer backType2) {
        this.backType2 = backType2;
    }

    public BigDecimal getBackAmount2() {
        return backAmount2;
    }

    public void setBackAmount2(BigDecimal backAmount2) {
        this.backAmount2 = backAmount2;
    }

    public Integer getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Integer startMonth) {
        this.startMonth = startMonth;
    }

    public Integer getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Integer endMonth) {
        this.endMonth = endMonth;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RebateRuleModel that = (RebateRuleModel) o;

        if (brrId != null ? !brrId.equals(that.brrId) : that.brrId != null) return false;
        if (pointType != null ? !pointType.equals(that.pointType) : that.pointType != null) return false;
        if (txnWay != null ? !txnWay.equals(that.txnWay) : that.txnWay != null) return false;
        if (timeType != null ? !timeType.equals(that.timeType) : that.timeType != null) return false;
        if (cycle != null ? !cycle.equals(that.cycle) : that.cycle != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (txnType != null ? !txnType.equals(that.txnType) : that.txnType != null) return false;
        if (txnAmount != null ? !txnAmount.equals(that.txnAmount) : that.txnAmount != null) return false;
        if (backType1 != null ? !backType1.equals(that.backType1) : that.backType1 != null) return false;
        if (backAmount1 != null ? !backAmount1.equals(that.backAmount1) : that.backAmount1 != null) return false;
        if (backType2 != null ? !backType2.equals(that.backType2) : that.backType2 != null) return false;
        if (backAmount2 != null ? !backAmount2.equals(that.backAmount2) : that.backAmount2 != null) return false;
        if (startMonth != null ? !startMonth.equals(that.startMonth) : that.startMonth != null) return false;
        if (endMonth != null ? !endMonth.equals(that.endMonth) : that.endMonth != null) return false;
        if (paymentAmount != null ? !paymentAmount.equals(that.paymentAmount) : that.paymentAmount != null)
            return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (updateUser != null ? !updateUser.equals(that.updateUser) : that.updateUser != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (remark1 != null ? !remark1.equals(that.remark1) : that.remark1 != null) return false;
        if (remark2 != null ? !remark2.equals(that.remark2) : that.remark2 != null) return false;
        if (auditStatus != null ? !auditStatus.equals(that.auditStatus) : that.auditStatus != null) return false;
        if (rejectReason != null ? !rejectReason.equals(that.rejectReason) : that.rejectReason != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = brrId != null ? brrId.hashCode() : 0;
        result = 31 * result + (pointType != null ? pointType.hashCode() : 0);
        result = 31 * result + (txnWay != null ? txnWay.hashCode() : 0);
        result = 31 * result + (timeType != null ? timeType.hashCode() : 0);
        result = 31 * result + (cycle != null ? cycle.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (txnType != null ? txnType.hashCode() : 0);
        result = 31 * result + (txnAmount != null ? txnAmount.hashCode() : 0);
        result = 31 * result + (backType1 != null ? backType1.hashCode() : 0);
        result = 31 * result + (backAmount1 != null ? backAmount1.hashCode() : 0);
        result = 31 * result + (backType2 != null ? backType2.hashCode() : 0);
        result = 31 * result + (backAmount2 != null ? backAmount2.hashCode() : 0);
        result = 31 * result + (startMonth != null ? startMonth.hashCode() : 0);
        result = 31 * result + (endMonth != null ? endMonth.hashCode() : 0);
        result = 31 * result + (paymentAmount != null ? paymentAmount.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (remark1 != null ? remark1.hashCode() : 0);
        result = 31 * result + (remark2 != null ? remark2.hashCode() : 0);
        result = 31 * result + (auditStatus != null ? auditStatus.hashCode() : 0);
        result = 31 * result + (rejectReason != null ? rejectReason.hashCode() : 0);
        return result;
    }
}
