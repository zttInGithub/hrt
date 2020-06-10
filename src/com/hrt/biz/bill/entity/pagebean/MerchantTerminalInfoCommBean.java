package com.hrt.biz.bill.entity.pagebean;

import java.util.Date;

public class MerchantTerminalInfoCommBean {

	private Integer BMTID;// int  NOT NULL CONSTRAINT PK_bill_TerminalInfo_COMM PRIMARY KEY,
	private Integer MAID;//  int,
	private String MID;// char (15) NOT NULL,  -- MID
	private String TID;// char(8),             --TID
	private String LARGEMID;// char(15),       -- 大额MID
	private String LARGETID;// char(8),        -- 大额TID
	private String WILDMID;//  char(5),        -- 外卡MID
	private String WILDTID;//  char(8),        -- 外卡TID
	private String RESERVEMID;// char(15),     --储联卡MID
	private String RESERVETID;// char(8),      --储联卡TID
	private String STOREDMID;//  char(15),     --储值卡MID
	private String STOREDTID;//  char(8),      --储值卡TID
	private String MODELTYPE;//  varchar2(30),      --机型
	private String SN;//         varchar2(50),      --sn号
	private String ATTENDEDMODEL;//  varchar2(50),      --连接方式
	private String BELONG;//        varchar2(30),       --设备归属
	private String BINGNUM;//       varchar2(20),       --绑定电话
	private String INSTALLEDADDRESS;// varchar2(200),   --装机地址
	private String INSTALLEDNAME;//   varchar2(30),    --装机名称
	private String INSTALLEDSIM;//   varchar2(20),   --SIM卡   
	private String CONTACTPERSON;//   varchar2(30),   --联系人
	private String CONTACTTEL;
	private String CONTACTPHONE;//   varchar2(20),   --联系人电话  
	private Integer IFTRIGEMINY;//    int,            -- 是否三联  1-是，0 -否
	private Double LIMIT;//         NUMBER(18,2),     --限额
	private String SETUPLINE; // --装机路线
	private String   CARDID ;      //  --卡种
	private  String SERVICE_VALUE;  //--服务费
	private  String DEPOSIT;     // --押金
	private String  LINKMAN2; //--联系人2
	private String PHONE2 ;       //varchar(50),     --联系电话2
	private String SHOPNAME ;     // varchar(100),    --店铺名称
	private Date  REPORTDATE ;  //  Date,  --报审日期
	private Date APPROVALDATE;// Date,  --批准日期
	private Date  SETUP_DATE;     //Date,  --收单安装日期
	private Date  CLSETUPDATE;  //    Date,  --储联卡安装日期
	private Date  CZSETUPDATE;   // Date,  --储值卡安装日期	
	private Integer STATUS;//  int,                    --状态  
	private String MINFO1;// VARCHAR2(100),
	private String MINFO2;// VARCHAR2(100),
	private String REMARKS;// varchar2 (200) NULL  
	
	private String BUSINESSSCOPE; //varchar2(50),--实际经营范围
	private String BANKNAME;     // varchar2(100),--开户行
	private String ACCNO;        // varchar2(30), --帐号
	private String EMAIL;         //varchar2(50), --邮箱 
	public Integer getBMTID() {
		return BMTID;
	}
	public void setBMTID(Integer bMTID) {
		BMTID = bMTID;
	}
	public Integer getMAID() {
		return MAID;
	}
	public void setMAID(Integer mAID) {
		MAID = mAID;
	}
	public String getMID() {
		return MID;
	}
	public void setMID(String mID) {
		MID = mID;
	}
	public String getTID() {
		return TID;
	}
	public void setTID(String tID) {
		TID = tID;
	}
	public String getLARGEMID() {
		return LARGEMID;
	}
	public void setLARGEMID(String lARGEMID) {
		LARGEMID = lARGEMID;
	}
	public String getLARGETID() {
		return LARGETID;
	}
	public void setLARGETID(String lARGETID) {
		LARGETID = lARGETID;
	}
	public String getWILDMID() {
		return WILDMID;
	}
	public void setWILDMID(String wILDMID) {
		WILDMID = wILDMID;
	}
	public String getWILDTID() {
		return WILDTID;
	}
	public void setWILDTID(String wILDTID) {
		WILDTID = wILDTID;
	}
	public String getRESERVEMID() {
		return RESERVEMID;
	}
	public void setRESERVEMID(String rESERVEMID) {
		RESERVEMID = rESERVEMID;
	}
	public String getRESERVETID() {
		return RESERVETID;
	}
	public void setRESERVETID(String rESERVETID) {
		RESERVETID = rESERVETID;
	}
	public String getSTOREDMID() {
		return STOREDMID;
	}
	public void setSTOREDMID(String sTOREDMID) {
		STOREDMID = sTOREDMID;
	}
	public String getSTOREDTID() {
		return STOREDTID;
	}
	public void setSTOREDTID(String sTOREDTID) {
		STOREDTID = sTOREDTID;
	}
	public String getMODELTYPE() {
		return MODELTYPE;
	}
	public void setMODELTYPE(String mODELTYPE) {
		MODELTYPE = mODELTYPE;
	}
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getATTENDEDMODEL() {
		return ATTENDEDMODEL;
	}
	public void setATTENDEDMODEL(String aTTENDEDMODEL) {
		ATTENDEDMODEL = aTTENDEDMODEL;
	}
	public String getBELONG() {
		return BELONG;
	}
	public void setBELONG(String bELONG) {
		BELONG = bELONG;
	}
	public String getBINGNUM() {
		return BINGNUM;
	}
	public void setBINGNUM(String bINGNUM) {
		BINGNUM = bINGNUM;
	}
	public String getINSTALLEDADDRESS() {
		return INSTALLEDADDRESS;
	}
	public void setINSTALLEDADDRESS(String iNSTALLEDADDRESS) {
		INSTALLEDADDRESS = iNSTALLEDADDRESS;
	}
	public String getINSTALLEDNAME() {
		return INSTALLEDNAME;
	}
	public void setINSTALLEDNAME(String iNSTALLEDNAME) {
		INSTALLEDNAME = iNSTALLEDNAME;
	}
	public String getINSTALLEDSIM() {
		return INSTALLEDSIM;
	}
	public void setINSTALLEDSIM(String iNSTALLEDSIM) {
		INSTALLEDSIM = iNSTALLEDSIM;
	}
	public String getCONTACTPERSON() {
		return CONTACTPERSON;
	}
	public void setCONTACTPERSON(String cONTACTPERSON) {
		CONTACTPERSON = cONTACTPERSON;
	}
	public String getCONTACTTEL() {
		return CONTACTTEL;
	}
	public void setCONTACTTEL(String cONTACTTEL) {
		CONTACTTEL = cONTACTTEL;
	}
	public String getCONTACTPHONE() {
		return CONTACTPHONE;
	}
	public void setCONTACTPHONE(String cONTACTPHONE) {
		CONTACTPHONE = cONTACTPHONE;
	}
	public Integer getIFTRIGEMINY() {
		return IFTRIGEMINY;
	}
	public void setIFTRIGEMINY(Integer iFTRIGEMINY) {
		IFTRIGEMINY = iFTRIGEMINY;
	}
	public Double getLIMIT() {
		return LIMIT;
	}
	public void setLIMIT(Double lIMIT) {
		LIMIT = lIMIT;
	}
	public String getSETUPLINE() {
		return SETUPLINE;
	}
	public void setSETUPLINE(String sETUPLINE) {
		SETUPLINE = sETUPLINE;
	}
	public String getCARDID() {
		return CARDID;
	}
	public void setCARDID(String cARDID) {
		CARDID = cARDID;
	}
	public String getSERVICE_VALUE() {
		return SERVICE_VALUE;
	}
	public void setSERVICE_VALUE(String sERVICEVALUE) {
		SERVICE_VALUE = sERVICEVALUE;
	}
	public String getDEPOSIT() {
		return DEPOSIT;
	}
	public void setDEPOSIT(String dEPOSIT) {
		DEPOSIT = dEPOSIT;
	}
	public String getLINKMAN2() {
		return LINKMAN2;
	}
	public void setLINKMAN2(String lINKMAN2) {
		LINKMAN2 = lINKMAN2;
	}
	public String getPHONE2() {
		return PHONE2;
	}
	public void setPHONE2(String pHONE2) {
		PHONE2 = pHONE2;
	}
	public String getSHOPNAME() {
		return SHOPNAME;
	}
	public void setSHOPNAME(String sHOPNAME) {
		SHOPNAME = sHOPNAME;
	}
	public Date getREPORTDATE() {
		return REPORTDATE;
	}
	public void setREPORTDATE(Date rEPORTDATE) {
		REPORTDATE = rEPORTDATE;
	}
	public Date getAPPROVALDATE() {
		return APPROVALDATE;
	}
	public void setAPPROVALDATE(Date aPPROVALDATE) {
		APPROVALDATE = aPPROVALDATE;
	}
	public Date getSETUP_DATE() {
		return SETUP_DATE;
	}
	public void setSETUP_DATE(Date sETUPDATE) {
		SETUP_DATE = sETUPDATE;
	}
	public Date getCLSETUPDATE() {
		return CLSETUPDATE;
	}
	public void setCLSETUPDATE(Date cLSETUPDATE) {
		CLSETUPDATE = cLSETUPDATE;
	}
	public Date getCZSETUPDATE() {
		return CZSETUPDATE;
	}
	public void setCZSETUPDATE(Date cZSETUPDATE) {
		CZSETUPDATE = cZSETUPDATE;
	}
	public Integer getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(Integer sTATUS) {
		STATUS = sTATUS;
	}
	public String getMINFO1() {
		return MINFO1;
	}
	public void setMINFO1(String mINFO1) {
		MINFO1 = mINFO1;
	}
	public String getMINFO2() {
		return MINFO2;
	}
	public void setMINFO2(String mINFO2) {
		MINFO2 = mINFO2;
	}
	public String getREMARKS() {
		return REMARKS;
	}
	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}
	public String getBUSINESSSCOPE() {
		return BUSINESSSCOPE;
	}
	public void setBUSINESSSCOPE(String bUSINESSSCOPE) {
		BUSINESSSCOPE = bUSINESSSCOPE;
	}
	public String getBANKNAME() {
		return BANKNAME;
	}
	public void setBANKNAME(String bANKNAME) {
		BANKNAME = bANKNAME;
	}
	public String getACCNO() {
		return ACCNO;
	}
	public void setACCNO(String aCCNO) {
		ACCNO = aCCNO;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	 
	
	
}
