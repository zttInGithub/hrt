package com.hrt.biz.bill.entity.model;


import java.util.Date;

/**
 * BillSaleUnnoModel
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2020/02/17
 * @since 1.8
 **/
public class BillSaleUnnoModel {
    private int bsuId;
    private String unno;
    private Integer unLvl;
    private String saleName;
    private Integer sakeLvl;
    private Integer type;
    private Integer status;
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
    private String remark1;
    private String remark2;

    public int getBsuId() {
        return bsuId;
    }

    public void setBsuId(int bsuId) {
        this.bsuId = bsuId;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public Integer getUnLvl() {
        return unLvl;
    }

    public void setUnLvl(Integer unLvl) {
        this.unLvl = unLvl;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public Integer getSakeLvl() {
        return sakeLvl;
    }

    public void setSakeLvl(Integer sakeLvl) {
        this.sakeLvl = sakeLvl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

        BillSaleUnnoModel that = (BillSaleUnnoModel) o;

        if (bsuId != that.bsuId) return false;
        if (unno != null ? !unno.equals(that.unno) : that.unno != null) return false;
        if (unLvl != null ? !unLvl.equals(that.unLvl) : that.unLvl != null) return false;
        if (saleName != null ? !saleName.equals(that.saleName) : that.saleName != null) return false;
        if (sakeLvl != null ? !sakeLvl.equals(that.sakeLvl) : that.sakeLvl != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
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
        int result = bsuId;
        result = 31 * result + (unno != null ? unno.hashCode() : 0);
        result = 31 * result + (unLvl != null ? unLvl.hashCode() : 0);
        result = 31 * result + (saleName != null ? saleName.hashCode() : 0);
        result = 31 * result + (sakeLvl != null ? sakeLvl.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
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
