package com.hrt.frame.entity.model;

/**
 * SysEnumModel
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2020/01/16
 * @since 1.8
 **/
public class EnumModel {
    private int enumId;
    private Integer enumType;
    private String enumTable;
    private String enumColumn;
    private Integer enumStatus;
    private String enumCode;
    private String enumName;
    private String enumDesc;
    private int enumValue;
    private String enumInfo;

    public int getEnumId() {
        return enumId;
    }

    public void setEnumId(int enumId) {
        this.enumId = enumId;
    }

    public Integer getEnumType() {
        return enumType;
    }

    public void setEnumType(Integer enumType) {
        this.enumType = enumType;
    }

    public String getEnumTable() {
        return enumTable;
    }

    public void setEnumTable(String enumTable) {
        this.enumTable = enumTable;
    }

    public String getEnumColumn() {
        return enumColumn;
    }

    public void setEnumColumn(String enumColumn) {
        this.enumColumn = enumColumn;
    }

    public Integer getEnumStatus() {
        return enumStatus;
    }

    public void setEnumStatus(Integer enumStatus) {
        this.enumStatus = enumStatus;
    }

    public String getEnumCode() {
        return enumCode;
    }

    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public String getEnumDesc() {
        return enumDesc;
    }

    public void setEnumDesc(String enumDesc) {
        this.enumDesc = enumDesc;
    }

    public int getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(int enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumInfo() {
        return enumInfo;
    }

    public void setEnumInfo(String enumInfo) {
        this.enumInfo = enumInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnumModel that = (EnumModel) o;

        if (enumId != that.enumId) return false;
        if (enumValue != that.enumValue) return false;
        if (enumType != null ? !enumType.equals(that.enumType) : that.enumType != null) return false;
        if (enumTable != null ? !enumTable.equals(that.enumTable) : that.enumTable != null) return false;
        if (enumColumn != null ? !enumColumn.equals(that.enumColumn) : that.enumColumn != null) return false;
        if (enumStatus != null ? !enumStatus.equals(that.enumStatus) : that.enumStatus != null) return false;
        if (enumCode != null ? !enumCode.equals(that.enumCode) : that.enumCode != null) return false;
        if (enumName != null ? !enumName.equals(that.enumName) : that.enumName != null) return false;
        if (enumDesc != null ? !enumDesc.equals(that.enumDesc) : that.enumDesc != null) return false;
        if (enumInfo != null ? !enumInfo.equals(that.enumInfo) : that.enumInfo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = enumId;
        result = 31 * result + (enumType != null ? enumType.hashCode() : 0);
        result = 31 * result + (enumTable != null ? enumTable.hashCode() : 0);
        result = 31 * result + (enumColumn != null ? enumColumn.hashCode() : 0);
        result = 31 * result + (enumStatus != null ? enumStatus.hashCode() : 0);
        result = 31 * result + (enumCode != null ? enumCode.hashCode() : 0);
        result = 31 * result + (enumName != null ? enumName.hashCode() : 0);
        result = 31 * result + (enumDesc != null ? enumDesc.hashCode() : 0);
        result = 31 * result + enumValue;
        result = 31 * result + (enumInfo != null ? enumInfo.hashCode() : 0);
        return result;
    }
}
