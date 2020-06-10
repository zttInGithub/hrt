package com.hrt.biz.check.entity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 日返表
 */
public class CheckCashbackDayModel  implements Serializable {
//    CCDID	N	INTEGER	N
//    UNNO	N	CHAR(6)	N			直属机构号
//    UNNO_NAME	N	VARCHAR2(100)	N			直属名称
//    UPPER2_UNIT	N	CHAR(6)	Y			二代机构号
//    UPPER2_NAME	N	VARCHAR2(100)	Y			二代名称
//    UPPER1_UNIT	N	CHAR(6)	Y			一代机构号
//    UPPER1_NAME	N	VARCHAR2(100)	Y			一代名称
//    UPPER_UNIT	N	CHAR(6)	Y			运营中心机构号
//    UPPER_NAME	N	VARCHAR2(100)	Y			运营中心名称
//    MID	N	VARCHAR2(15)	N
//    TID	N	VARCHAR2(8)	N
//    SN	N	VARCHAR2(40)	Y
//    SN_TYPE	N	INTEGER	Y			类别,1小蓝牙，2大蓝牙
//    KEYCONFIRMDATE	N	VARCHAR2(10)	Y			出售日期
//    KEYMONTH	N	VARCHAR2(10)	Y			出售月
//    USEDCONFIRMDATE	N	DATE	Y			入网日期
//    USEMONTH	N	VARCHAR2(10)	Y			入网月
//    TXNMONTH	N	VARCHAR2(10)	Y			交易月
//    REBATE1_AMT	N	NUMBER(18,2)	Y			返利金额1
//    REBATE1_MONTH	N	VARCHAR2(10)	Y			返利日期1
//    REBATE2_AMT	N	NUMBER(18,2)	Y
//    REBATE2_MONTH	N	VARCHAR2(10)	Y
//    REBATE3_AMT	N	NUMBER(18,2)	Y
//    REBATE3_MONTH	N	VARCHAR2(10)	Y
//    TXNAMOUNT	N	NUMBER(18,2)	Y			交易金额
//    TXNCOUNT	N	NUMBER	Y			交易笔数
//    REBATETYPE	N	INTEGER	Y			活动类型
//    MINFO1	N	VARCHAR2(100)	Y			扩展1
//    MINFO2	N	VARCHAR2(100)	Y			扩展2
//    CDATE	N	DATE	Y			创建时间
//    LMDATE	N	DATE	Y			修改时间

    private Integer ccdid;
    private String unno;
    private String  unnoName;
    private String upper2Unit;
    private String upper2Name;
    private String upper1Unit;
    private String upper1Name;
    private String upperUnit;
    private String upperName;
    private String mid;
    private String tid;
    private String sn;
    private Integer snType;
    private String keyConfirmDate;
    private String keyMonth;
    private Date usedConfirmDate;
    private String useMonth;
    private String txnMonth;
    private double rebate1Amt;
    private String rebate1Month;
    private double rebate2Amt;
    private String rebate2Month;
    private double rebate3Amt;
    private String rebate3Month;
    private double txnAmount;
    private double txnCount;
    private Integer rebateType;
    private String mInfo1;
    private String mInfo2;
    private Date cDate;
    private Date lmDate;

    public CheckCashbackDayModel() {
    }

    public CheckCashbackDayModel(Integer ccdid, String unno, String unnoName, String upper2Unit, String upper2Name, String upper1Unit, String upper1Name, String upperUnit, String upperName, String mid, String tid, String sn, Integer snType, String keyConfirmDate, String keyMonth, Date usedConfirmDate, String useMonth, String txnMonth, double rebate1Amt, String rebate1Month, double rebate2Amt, String rebate2Month, double rebate3Amt, String rebate3Month, double txnAmount, double txnCount, Integer rebateType, String mInfo1, String mInfo2, Date cDate, Date lmDate) {
        this.ccdid = ccdid;
        this.unno = unno;
        this.unnoName = unnoName;
        this.upper2Unit = upper2Unit;
        this.upper2Name = upper2Name;
        this.upper1Unit = upper1Unit;
        this.upper1Name = upper1Name;
        this.upperUnit = upperUnit;
        this.upperName = upperName;
        this.mid = mid;
        this.tid = tid;
        this.sn = sn;
        this.snType = snType;
        this.keyConfirmDate = keyConfirmDate;
        this.keyMonth = keyMonth;
        this.usedConfirmDate = usedConfirmDate;
        this.useMonth = useMonth;
        this.txnMonth = txnMonth;
        this.rebate1Amt = rebate1Amt;
        this.rebate1Month = rebate1Month;
        this.rebate2Amt = rebate2Amt;
        this.rebate2Month = rebate2Month;
        this.rebate3Amt = rebate3Amt;
        this.rebate3Month = rebate3Month;
        this.txnAmount = txnAmount;
        this.txnCount = txnCount;
        this.rebateType = rebateType;
        this.mInfo1 = mInfo1;
        this.mInfo2 = mInfo2;
        this.cDate = cDate;
        this.lmDate = lmDate;
    }

    public Integer getCcdid() {
        return ccdid;
    }

    public void setCcdid(Integer ccdid) {
        this.ccdid = ccdid;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public String getUnnoName() {
        return unnoName;
    }

    public void setUnnoName(String unnoName) {
        this.unnoName = unnoName;
    }

    public String getUpper2Unit() {
        return upper2Unit;
    }

    public void setUpper2Unit(String upper2Unit) {
        this.upper2Unit = upper2Unit;
    }

    public String getUpper2Name() {
        return upper2Name;
    }

    public void setUpper2Name(String upper2Name) {
        this.upper2Name = upper2Name;
    }

    public String getUpper1Unit() {
        return upper1Unit;
    }

    public void setUpper1Unit(String upper1Unit) {
        this.upper1Unit = upper1Unit;
    }

    public String getUpper1Name() {
        return upper1Name;
    }

    public void setUpper1Name(String upper1Name) {
        this.upper1Name = upper1Name;
    }

    public String getUpperUnit() {
        return upperUnit;
    }

    public void setUpperUnit(String upperUnit) {
        this.upperUnit = upperUnit;
    }

    public String getUpperName() {
        return upperName;
    }

    public void setUpperName(String upperName) {
        this.upperName = upperName;
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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getSnType() {
        return snType;
    }

    public void setSnType(Integer snType) {
        this.snType = snType;
    }

    public String getKeyConfirmDate() {
        return keyConfirmDate;
    }

    public void setKeyConfirmDate(String keyConfirmDate) {
        this.keyConfirmDate = keyConfirmDate;
    }

    public String getKeyMonth() {
        return keyMonth;
    }

    public void setKeyMonth(String keyMonth) {
        this.keyMonth = keyMonth;
    }

    public Date getUsedConfirmDate() {
        return usedConfirmDate;
    }

    public void setUsedConfirmDate(Date usedConfirmDate) {
        this.usedConfirmDate = usedConfirmDate;
    }

    public String getUseMonth() {
        return useMonth;
    }

    public void setUseMonth(String useMonth) {
        this.useMonth = useMonth;
    }

    public String getTxnMonth() {
        return txnMonth;
    }

    public void setTxnMonth(String txnMonth) {
        this.txnMonth = txnMonth;
    }

    public double getRebate1Amt() {
        return rebate1Amt;
    }

    public void setRebate1Amt(double rebate1Amt) {
        this.rebate1Amt = rebate1Amt;
    }

    public String getRebate1Month() {
        return rebate1Month;
    }

    public void setRebate1Month(String rebate1Month) {
        this.rebate1Month = rebate1Month;
    }

    public double getRebate2Amt() {
        return rebate2Amt;
    }

    public void setRebate2Amt(double rebate2Amt) {
        this.rebate2Amt = rebate2Amt;
    }

    public String getRebate2Month() {
        return rebate2Month;
    }

    public void setRebate2Month(String rebate2Month) {
        this.rebate2Month = rebate2Month;
    }

    public double getRebate3Amt() {
        return rebate3Amt;
    }

    public void setRebate3Amt(double rebate3Amt) {
        this.rebate3Amt = rebate3Amt;
    }

    public String getRebate3Month() {
        return rebate3Month;
    }

    public void setRebate3Month(String rebate3Month) {
        this.rebate3Month = rebate3Month;
    }

    public double getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(double txnAmount) {
        this.txnAmount = txnAmount;
    }

    public double getTxnCount() {
        return txnCount;
    }

    public void setTxnCount(double txnCount) {
        this.txnCount = txnCount;
    }

    public Integer getRebateType() {
        return rebateType;
    }

    public void setRebateType(Integer rebateType) {
        this.rebateType = rebateType;
    }

    public String getmInfo1() {
        return mInfo1;
    }

    public void setmInfo1(String mInfo1) {
        this.mInfo1 = mInfo1;
    }

    public String getmInfo2() {
        return mInfo2;
    }

    public void setmInfo2(String mInfo2) {
        this.mInfo2 = mInfo2;
    }

    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    public Date getLmDate() {
        return lmDate;
    }

    public void setLmDate(Date lmDate) {
        this.lmDate = lmDate;
    }
}
