package com.hrt.biz.credit.entity.model;


public class CreditInfoModel {
	 
	private String  AGENTNAME; //代理商名称	
	private String 	PROTOCOLNO; //协议编号
	private String 	CRETYPE; //贷款方式
	private String 	CREAMT; //贷款金额
	private String 	SURPLUSAMT; //未还金额
	private String 	CRERATE;//贷款利率
	private String 	CREDATE;//贷款日期
	private String 	CRETIMELIMIT    ;//贷款期限
	private String 	REPAYTIME    ;//到期时间
	private String 	APPROVEFLAG    ;//审批标志
	private String 	PROPOSER    ;//申请人
	private String 	PROPEMAIL    ;//申请人邮箱
	private String 	PROPHONE    ;//申请人电话
	private String 	REQRIME    ;//申请时间
	private String 	APPROVEDTIME    ;//审批时间
	private String 	APPROVER    ;//审批人
	private String 	LOANFLAG    ;//是否放款
	private String 	TOTALACCRUAL    ;//利息合计
	private String 	POSINVOICENO    ;//pos机单号
	private String 	PAYNO    ;//汇款单号
	private String 	LOANDATE    ;//放款时间
	private String 	OVERDUEFLAG    ;//是否逾期
	private String 	NEXTREPAYDAY    ;//下一还款日
	private String 	REPAYAMT    ;//已还金额
	private String 	REFUSALREASON    ;//拒绝原因
	private String 	REMARKS ;  //备注
	
	public String getAGENTNAME() {
		return AGENTNAME;
	}
	public void setAGENTNAME(String aGENTNAME) {
		AGENTNAME = aGENTNAME;
	}
	public String getPROTOCOLNO() {
		return PROTOCOLNO;
	}
	public void setPROTOCOLNO(String pROTOCOLNO) {
		PROTOCOLNO = pROTOCOLNO;
	}
	public String getCRETYPE() {
		return CRETYPE;
	}
	public void setCRETYPE(String cRETYPE) {
		CRETYPE = cRETYPE;
	}
	public String getCREAMT() {
		return CREAMT;
	}
	public void setCREAMT(String cREAMT) {
		CREAMT = cREAMT;
	}
	public String getSURPLUSAMT() {
		return SURPLUSAMT;
	}
	public void setSURPLUSAMT(String sURPLUSAMT) {
		SURPLUSAMT = sURPLUSAMT;
	}
	public String getCRERATE() {
		return CRERATE;
	}
	public void setCRERATE(String cRERATE) {
		CRERATE = cRERATE;
	}
	public String getCREDATE() {
		return CREDATE;
	}
	public void setCREDATE(String cREDATE) {
		CREDATE = cREDATE;
	}
	public String getCRETIMELIMIT() {
		return CRETIMELIMIT;
	}
	public void setCRETIMELIMIT(String cRETIMELIMIT) {
		CRETIMELIMIT = cRETIMELIMIT;
	}
	public String getREPAYTIME() {
		return REPAYTIME;
	}
	public void setREPAYTIME(String rEPAYTIME) {
		REPAYTIME = rEPAYTIME;
	}
	public String getAPPROVEFLAG() {
		return APPROVEFLAG;
	}
	public void setAPPROVEFLAG(String aPPROVEFLAG) {
		APPROVEFLAG = aPPROVEFLAG;
	}
	public String getPROPOSER() {
		return PROPOSER;
	}
	public void setPROPOSER(String pROPOSER) {
		PROPOSER = pROPOSER;
	}
	public String getPROPEMAIL() {
		return PROPEMAIL;
	}
	public void setPROPEMAIL(String pROPEMAIL) {
		PROPEMAIL = pROPEMAIL;
	}
	public String getPROPHONE() {
		return PROPHONE;
	}
	public void setPROPHONE(String pROPHONE) {
		PROPHONE = pROPHONE;
	}
	public String getREQRIME() {
		return REQRIME;
	}
	public void setREQRIME(String rEQRIME) {
		REQRIME = rEQRIME;
	}
	public String getAPPROVEDTIME() {
		return APPROVEDTIME;
	}
	public void setAPPROVEDTIME(String aPPROVEDTIME) {
		APPROVEDTIME = aPPROVEDTIME;
	}
	public String getAPPROVER() {
		return APPROVER;
	}
	public void setAPPROVER(String aPPROVER) {
		APPROVER = aPPROVER;
	}
	public String getLOANFLAG() {
		return LOANFLAG;
	}
	public void setLOANFLAG(String lOANFLAG) {
		LOANFLAG = lOANFLAG;
	}
	public String getTOTALACCRUAL() {
		return TOTALACCRUAL;
	}
	public void setTOTALACCRUAL(String tOTALACCRUAL) {
		TOTALACCRUAL = tOTALACCRUAL;
	}
	public String getPOSINVOICENO() {
		return POSINVOICENO;
	}
	public void setPOSINVOICENO(String pOSINVOICENO) {
		POSINVOICENO = pOSINVOICENO;
	}
	public String getPAYNO() {
		return PAYNO;
	}
	public void setPAYNO(String pAYNO) {
		PAYNO = pAYNO;
	}
	public String getLOANDATE() {
		return LOANDATE;
	}
	public void setLOANDATE(String lOANDATE) {
		LOANDATE = lOANDATE;
	}
	public String getOVERDUEFLAG() {
		return OVERDUEFLAG;
	}
	public void setOVERDUEFLAG(String oVERDUEFLAG) {
		OVERDUEFLAG = oVERDUEFLAG;
	}
	public String getNEXTREPAYDAY() {
		return NEXTREPAYDAY;
	}
	public void setNEXTREPAYDAY(String nEXTREPAYDAY) {
		NEXTREPAYDAY = nEXTREPAYDAY;
	}
	public String getREPAYAMT() {
		return REPAYAMT;
	}
	public void setREPAYAMT(String rEPAYAMT) {
		REPAYAMT = rEPAYAMT;
	}
	public String getREFUSALREASON() {
		return REFUSALREASON;
	}
	public void setREFUSALREASON(String rEFUSALREASON) {
		REFUSALREASON = rEFUSALREASON;
	}
	public String getREMARKS() {
		return REMARKS;
	}
	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}

}
