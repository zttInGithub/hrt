package com.hrt.biz.bill.entity.model;


import java.util.Date;

/**
 * RebateRuleInfoModel
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2020/01/15
 * @since 1.8
 **/
public class RebateRuleInfoModel {
    private Integer id;
    private Integer brId;
    private Integer brrId;
    private Date startDate;
    private Date endDate;
    private Integer status;
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
    private String remark1;
    private String remark2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrId() {
        return brId;
    }

    public void setBrId(Integer brId) {
        this.brId = brId;
    }

    public Integer getBrrId() {
        return brrId;
    }

    public void setBrrId(Integer brrId) {
        this.brrId = brrId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

        RebateRuleInfoModel that = (RebateRuleInfoModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (brId != null ? !brId.equals(that.brId) : that.brId != null) return false;
        if (brrId != null ? !brrId.equals(that.brrId) : that.brrId != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (updateUser != null ? !updateUser.equals(that.updateUser) : that.updateUser != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (remark1 != null ? !remark1.equals(that.remark1) : that.remark1 != null) return false;
        if (remark2 != null ? !remark2.equals(that.remark2) : that.remark2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (brId != null ? brId.hashCode() : 0);
        result = 31 * result + (brrId != null ? brrId.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (remark1 != null ? remark1.hashCode() : 0);
        result = 31 * result + (remark2 != null ? remark2.hashCode() : 0);
        return result;
    }
}
