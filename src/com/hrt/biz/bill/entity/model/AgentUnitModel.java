package com.hrt.biz.bill.entity.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理商信息
 */
public class AgentUnitModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer buid;					//代理商主键
	private String parentUnno;				//归属机构编号-用于暂存
	private String unno;					//机构编号
	private String agentName;				//代理商名称
	private String baddr;					//经营地址
	private String legalPerson;				//法人
	private String legalType;				//法人证件类型
	private String legalNum;				//法人证件号码
	private String accType;					//开户类型
	private String bankBranch;				//开户银行
	private String bankAccNo;				//开户银行账号
	private String bankAccName;				//开户账号名称
	private String bankType;				//开户银行是否为交通银行
	private String areaType;				//开户银行所在地类别
	private String bankArea;				//开户地
	private Double riskAmount;				//风险保障金
	private Date amountConfirmDate;			//缴款时间
	private Date openDate;					//代理商开通
	private String contractNo;				//签约合同编号
	private Integer signUserId;				//签约人员
	private String bno;						//营业执照编号
	private String rno;						//组织机构代码
	private String registryNo;				//税务登记证号
	private String bankOpenAcc;				//企业银行开户许可证号
	private Integer maintainUserId;			//维护人员
	private String contact;					//负责人
	private String contactTel;				//负责人固定电话
	private String contactPhone;			//负责人手机
	private String secondContact;			//第二联系人
	private String secondContactTel;		//第二联系人固定电话
	private String secondContactPhone;		//第二联系人手机
	private String riskContact;				//风险调单联系人
	private String riskContactPhone;		//风险调单联系手机
	private String riskContactMail;			//风险调单联系邮箱
	private String businessContacts;		//业务联系人
	private String businessContactsPhone;	//业务联系手机
	private String businessContactsMail;	//业务联系邮箱
	private Integer maintainUid;			//操作人员
	private Date maintainDate;				//操作日期
	private String maintainType;			//操作类型
	private String remarks;					//备注
	// new add
	private String regAddress;     //注册地址
	private String payBankID;     //支付系统行号
	private String profitContactPerson;     //分润联系人
	private String profitContactPhone;     //联系电话
	private String profitContactEmail;     //邮箱
	private String dealUpLoad;     //协议签章页照片
	private String busLicUpLoad;     //营业执照（企业必传）
	private String proofOfOpenAccountUpLoad;     //对公开户证明（企业必传）
	private String legalAUpLoad;     //法人身份证正面
	private String legalBUpLoad;     //法人身份证反面
	private String accountAuthUpLoad;     //入账授权书
	private String accountLegalAUpLoad;     //入账人身份证正面
	private String accountLegalBUpLoad;     //入账人身份证反面
	private String accountLegalHandUpLoad;     //入账人手持身份证
	private String officeAddressUpLoad;     //办公地点照片
	private String profitUpLoad;     //分润照片
	private Integer agentLvl;     //是否为运营中心   只存在签约运营中心为1 
	private String approveStatus;  // K退回W待审Y通过
	private String agentShortNm;  
	private String returnReason; //退回原因  
	private Integer cycle;//分润结算周期
	private BigDecimal cashRate;//分润税点
	private Integer purseType;//钱包状态
	private Integer cashStatus;//返现钱包状态
	/*
	 * private Integer cashsWitch;//活动钱包提现开关
	 * 
	 * public Integer getCashsWitch() { return cashsWitch; }
	 * 
	 * public void setCashsWitch(Integer cashsWitch) { this.cashsWitch = cashsWitch;
	 * }
	 */

	public Integer getCashStatus() {
		return cashStatus;
	}

	public void setCashStatus(Integer cashStatus) {
		this.cashStatus = cashStatus;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public BigDecimal getCashRate() {
		return cashRate;
	}

	public void setCashRate(BigDecimal cashRate) {
		this.cashRate = cashRate;
	}

	public Integer getPurseType() {
		return purseType;
	}

	public void setPurseType(Integer purseType) {
		this.purseType = purseType;
	}

	public Integer getBuid() {
		return this.buid;
	}

	public void setBuid(Integer buid) {
		this.buid = buid;
	}

	public String getUnno() {
		return this.unno;
	}

	public void setUnno(String unno) {
		this.unno = unno;
	}

	public String getAgentName() {
		return this.agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getBaddr() {
		return this.baddr;
	}

	public void setBaddr(String baddr) {
		this.baddr = baddr;
	}

	public String getLegalPerson() {
		return this.legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getLegalType() {
		return this.legalType;
	}

	public void setLegalType(String legalType) {
		this.legalType = legalType;
	}

	public String getLegalNum() {
		return this.legalNum;
	}

	public void setLegalNum(String legalNum) {
		this.legalNum = legalNum;
	}

	public String getAccType() {
		return this.accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getBankBranch() {
		return this.bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankAccNo() {
		return this.bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public String getBankAccName() {
		return this.bankAccName;
	}

	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}

	public String getBankType() {
		return this.bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getAreaType() {
		return this.areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getBankArea() {
		return this.bankArea;
	}

	public void setBankArea(String bankArea) {
		this.bankArea = bankArea;
	}

	public Double getRiskAmount() {
		return this.riskAmount;
	}

	public void setRiskAmount(Double riskAmount) {
		this.riskAmount = riskAmount;
	}

	public Date getAmountConfirmDate() {
		return amountConfirmDate;
	}

	public void setAmountConfirmDate(Date amountConfirmDate) {
		this.amountConfirmDate = amountConfirmDate;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Integer getSignUserId() {
		return this.signUserId;
	}

	public void setSignUserId(Integer signUserId) {
		this.signUserId = signUserId;
	}

	public String getBno() {
		return this.bno;
	}

	public void setBno(String bno) {
		this.bno = bno;
	}

	public String getRno() {
		return this.rno;
	}

	public void setRno(String rno) {
		this.rno = rno;
	}

	public String getRegistryNo() {
		return this.registryNo;
	}

	public void setRegistryNo(String registryNo) {
		this.registryNo = registryNo;
	}

	public String getBankOpenAcc() {
		return this.bankOpenAcc;
	}

	public void setBankOpenAcc(String bankOpenAcc) {
		this.bankOpenAcc = bankOpenAcc;
	}

	public Integer getMaintainUserId() {
		return this.maintainUserId;
	}

	public void setMaintainUserId(Integer maintainUserId) {
		this.maintainUserId = maintainUserId;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactTel() {
		return this.contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getSecondContact() {
		return this.secondContact;
	}

	public void setSecondContact(String secondContact) {
		this.secondContact = secondContact;
	}

	public String getSecondContactTel() {
		return this.secondContactTel;
	}

	public void setSecondContactTel(String secondContactTel) {
		this.secondContactTel = secondContactTel;
	}

	public String getSecondContactPhone() {
		return this.secondContactPhone;
	}

	public void setSecondContactPhone(String secondContactPhone) {
		this.secondContactPhone = secondContactPhone;
	}

	public String getRiskContact() {
		return this.riskContact;
	}

	public void setRiskContact(String riskContact) {
		this.riskContact = riskContact;
	}

	public String getRiskContactPhone() {
		return this.riskContactPhone;
	}

	public void setRiskContactPhone(String riskContactPhone) {
		this.riskContactPhone = riskContactPhone;
	}

	public String getRiskContactMail() {
		return this.riskContactMail;
	}

	public void setRiskContactMail(String riskContactMail) {
		this.riskContactMail = riskContactMail;
	}

	public String getBusinessContacts() {
		return this.businessContacts;
	}

	public void setBusinessContacts(String businessContacts) {
		this.businessContacts = businessContacts;
	}

	public String getBusinessContactsPhone() {
		return this.businessContactsPhone;
	}

	public void setBusinessContactsPhone(String businessContactsPhone) {
		this.businessContactsPhone = businessContactsPhone;
	}

	public String getBusinessContactsMail() {
		return this.businessContactsMail;
	}

	public void setBusinessContactsMail(String businessContactsMail) {
		this.businessContactsMail = businessContactsMail;
	}

	public Integer getMaintainUid() {
		return this.maintainUid;
	}

	public void setMaintainUid(Integer maintainUid) {
		this.maintainUid = maintainUid;
	}

	public Date getMaintainDate() {
		return maintainDate;
	}

	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}

	public String getMaintainType() {
		return this.maintainType;
	}

	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public String getPayBankID() {
		return payBankID;
	}

	public void setPayBankID(String payBankID) {
		this.payBankID = payBankID;
	}

	public String getProfitContactPerson() {
		return profitContactPerson;
	}

	public void setProfitContactPerson(String profitContactPerson) {
		this.profitContactPerson = profitContactPerson;
	}

	public String getProfitContactPhone() {
		return profitContactPhone;
	}

	public void setProfitContactPhone(String profitContactPhone) {
		this.profitContactPhone = profitContactPhone;
	}

	public String getProfitContactEmail() {
		return profitContactEmail;
	}

	public void setProfitContactEmail(String profitContactEmail) {
		this.profitContactEmail = profitContactEmail;
	}

	public String getDealUpLoad() {
		return dealUpLoad;
	}

	public void setDealUpLoad(String dealUpLoad) {
		this.dealUpLoad = dealUpLoad;
	}

	public String getBusLicUpLoad() {
		return busLicUpLoad;
	}

	public void setBusLicUpLoad(String busLicUpLoad) {
		this.busLicUpLoad = busLicUpLoad;
	}

	public String getProofOfOpenAccountUpLoad() {
		return proofOfOpenAccountUpLoad;
	}

	public void setProofOfOpenAccountUpLoad(String proofOfOpenAccountUpLoad) {
		this.proofOfOpenAccountUpLoad = proofOfOpenAccountUpLoad;
	}

	public String getLegalAUpLoad() {
		return legalAUpLoad;
	}

	public void setLegalAUpLoad(String legalAUpLoad) {
		this.legalAUpLoad = legalAUpLoad;
	}

	public String getLegalBUpLoad() {
		return legalBUpLoad;
	}

	public void setLegalBUpLoad(String legalBUpLoad) {
		this.legalBUpLoad = legalBUpLoad;
	}

	public String getAccountAuthUpLoad() {
		return accountAuthUpLoad;
	}

	public void setAccountAuthUpLoad(String accountAuthUpLoad) {
		this.accountAuthUpLoad = accountAuthUpLoad;
	}

	public String getAccountLegalAUpLoad() {
		return accountLegalAUpLoad;
	}

	public void setAccountLegalAUpLoad(String accountLegalAUpLoad) {
		this.accountLegalAUpLoad = accountLegalAUpLoad;
	}

	public String getAccountLegalBUpLoad() {
		return accountLegalBUpLoad;
	}

	public void setAccountLegalBUpLoad(String accountLegalBUpLoad) {
		this.accountLegalBUpLoad = accountLegalBUpLoad;
	}

	public String getAccountLegalHandUpLoad() {
		return accountLegalHandUpLoad;
	}

	public void setAccountLegalHandUpLoad(String accountLegalHandUpLoad) {
		this.accountLegalHandUpLoad = accountLegalHandUpLoad;
	}

	public String getOfficeAddressUpLoad() {
		return officeAddressUpLoad;
	}

	public void setOfficeAddressUpLoad(String officeAddressUpLoad) {
		this.officeAddressUpLoad = officeAddressUpLoad;
	}

	public Integer getAgentLvl() {
		return agentLvl;
	}

	public void setAgentLvl(Integer agentLvl) {
		this.agentLvl = agentLvl;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getAgentShortNm() {
		return agentShortNm;
	}

	public void setAgentShortNm(String agentShortNm) {
		this.agentShortNm = agentShortNm;
	}

	public String getProfitUpLoad() {
		return profitUpLoad;
	}

	public void setProfitUpLoad(String profitUpLoad) {
		this.profitUpLoad = profitUpLoad;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getParentUnno() {
		return parentUnno;
	}

	public void setParentUnno(String parentUnno) {
		this.parentUnno = parentUnno;
	}
	
}