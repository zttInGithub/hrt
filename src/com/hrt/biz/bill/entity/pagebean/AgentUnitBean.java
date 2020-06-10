package com.hrt.biz.bill.entity.pagebean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AgentUnitBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String agentMid;
	//当前页数
	private Integer page;
	
	//总记录数
	private Integer rows;
	
	//排序字段
	private String sort;
	
	//排序规则 ASC DESC
	private String order;
	
	//代理商主键
	private Integer buid;					
	
	//机构编号
	private String unno;	
	//归属机构编号-用于暂存
	private String parentUnno;				
	
	//代理商名称
	private String agentName;
	
	//代理商级别
	private String unLvl;				
	
	//经营地址
	private String baddr;					
	
	//法人
	private String legalPerson;				
	
	//法人证件类型
	private String legalType;				
	
	//法人证件号码
	private String legalNum;				
	
	//开户类型
	private String accType;					
	
	//开户银行
	private String bankBranch;				
	
	//开户银行账号
	private String bankAccNo;				
	
	//开户账号名称
	private String bankAccName;				
	
	//开户银行是否为交通银行
	private String bankType;				
	
	//开户银行所在地类别
	private String areaType;				
	
	//开户地
	private String bankArea;				
	
	//风险保障金
	private Double riskAmount;				
	
	//缴款时间
	private Date amountConfirmDate;	
	
	//代理商开通
	private Date openDate;				
	
	//签约合同编号
	private String contractNo;				
	
	//签约人员
	private Integer signUserId;			
	
	//营业执照编号
	private String bno;						
	
	//组织机构代码
	private String rno;						
	
	//税务登记证号
	private String registryNo;				
	
	//企业银行开户许可证号
	private String bankOpenAcc;				
	
	//维护人员
	private Integer maintainUserId;			
	
	//负责人
	private String contact;					
	
	//负责人固定电话
	private String contactTel;				
	
	//负责人手机
	private String contactPhone;			
	
	//第二联系人
	private String secondContact;			
	
	//第二联系人固定电话
	private String secondContactTel;		
	
	//第二联系人手机
	private String secondContactPhone;		
	
	//风险调单联系人
	private String riskContact;				
	
	//风险调单联系手机
	private String riskContactPhone;		
	
	//风险调单联系邮箱
	private String riskContactMail;			
	
	//业务联系人
	private String businessContacts;		
	
	//业务联系手机
	private String businessContactsPhone;	
	
	//业务联系邮箱
	private String businessContactsMail;	
	
	//操作人员
	private Integer maintainUid;			
	
	//操作日期
	private Date maintainDate;			
	
	//操作类型
	private String maintainType;			
	
	//缴款状态
	private String amountConfirmName;
	
	//代理商开通状态
	private String openName;
	
	//简码
	private String unitCode;
	
	//维护人员名称
	private String maintainUserName;	
	
	//签约人员名称
	private String signUserIdName;
	//维护人员
	private String maintainUserIdName;		
	
	//备注
	private String remarks;
	
	private String legalTypeName;
	
	private String accTypeName;
	
	private String bankTypeName;
	
	private String areaTypeName;
	
	//代理商机构性质
	private Integer agentAttr;
	
	private String regAddress;     //注册地址
	private String payBankID;     //支付系统行号
	private String profitContactPerson;     //分润联系人
	private String profitContactPhone;     //联系电话
	private String profitContactEmail;     //邮箱
	private File dealUpLoadFile;     //协议签章页照片
	private File busLicUpLoadFile;     //营业执照（企业必传）
	private File proofOfOpenAccountUpLoadFile;     //对公开户证明（企业必传）
	private File legalAUpLoadFile;     //法人身份证正面
	private File legalBUpLoadFile;     //法人身份证反面
	private File accountAuthUpLoadFile;     //入账授权书
	private File accountLegalAUpLoadFile;     //入账人身份证正面
	private File accountLegalBUpLoadFile;     //入账人身份证反面
	private File accountLegalHandUpLoadFile;     //入账人手持身份证
	private File officeAddressUpLoadFile;     //办公地点照片
	private File profitUpLoadFile;     //分润照片

	private String dealUpLoad;     //协议签章页照片-文件名称
	private String busLicUpLoad;     //营业执照（企业必传）-文件名称
	private String proofOfOpenAccountUpLoad;     //对公开户证明（企业必传）-文件名称
	private String legalAUpLoad;     //法人身份证正面-文件名称
	private String legalBUpLoad;     //法人身份证反面-文件名称
	private String accountAuthUpLoad;     //入账授权书-文件名称
	private String accountLegalAUpLoad;     //入账人身份证正面-文件名称
	private String accountLegalBUpLoad;     //入账人身份证反面-文件名称
	private String accountLegalHandUpLoad;     //入账人手持身份证-文件名称
	private String officeAddressUpLoad;     //办公地点照片-文件名称
	private String profitUpLoad;     //分润照片-文件名称
	private Integer agentLvl;     //是否为运营中心   只存在签约运营中心为1 
	private String approveStatus;  // K退回W待审Y通过
	private String agentShortNm;  
	private String provinceCode; //省份编码  
	private String returnReason; //退回原因  
	
	private String curAmt;//提现金额
	private String cashDay;//提现日期：空的话默认查询当天
	private String cashDay1;//提现日期：空的话默认查询当天
	private String status;//冻结状态 0冻结 1解冻
	private Integer statusName;//机构状态 0-停用  1-启用
	

	private Integer cycle;// 分润结算周期
	private BigDecimal cashRate;// 分润税点
	private Integer purseType;// 钱包状态 0或空-未开通，1-已开通
	private Integer cashStatus;//返现钱包状态 0-未开通，1-已开通
	
	private Integer cashsWitch;//活动钱包提现开关
	
	public Integer getCashsWitch() {
		return cashsWitch;
	}

	public void setCashsWitch(Integer cashsWitch) {
		this.cashsWitch = cashsWitch;
	}

	//20180914
	private String FID;			//文件ID
	private String cbUpLoad;	//成本名称
	private String cbUpLoadFile;
	private String zipUpLoad;	//分润照片名称
	private String zipUpLoadFile;
	
	private Date adate;
	private Date zdate;
	
	private Integer txnDetail;	//产品类型
	private Double rate;		//费率
	private Integer hucid;		//分润模板ID
	

	/**
	 * 微信1000以上0.38费率 WX_UPRATE
	 */
	private BigDecimal wxUpRate;

	/**
	 * 微信1000以上0.38转账费 WX_UPCASH
	 */
	private BigDecimal wxUpCash;

	/**
	 * 微信1000以上0.45费率 WX_UPRATE1
	 */
	private BigDecimal wxUpRate1;

	/**
	 * 微信1000以上0.45转账费 WX_UPCASH1
	 */
	private BigDecimal wxUpCash1;

	/**
	 * 支付宝费率 ZFB_RATE
	 */
	private BigDecimal zfbRate;

	/**
	 * 支付宝转账费 ZFB_CASH
	 */
	private BigDecimal zfbCash;

	/**
	 * 二维码费率
	 */
	private BigDecimal ewmRate;

	/**
	 * 二维码转账费
	 */
	private BigDecimal ewmCash;

	/**
	 * 云闪付费率
	 */
	private BigDecimal ysfRate;
	private BigDecimal hbRate;
	private BigDecimal hbCash;

	public BigDecimal getHbRate() {
		return hbRate;
	}

	public void setHbRate(BigDecimal hbRate) {
		this.hbRate = hbRate;
	}

	public BigDecimal getHbCash() {
		return hbCash;
	}

	public void setHbCash(BigDecimal hbCash) {
		this.hbCash = hbCash;
	}

	public BigDecimal getWxUpRate() {
		return wxUpRate;
	}

	public void setWxUpRate(BigDecimal wxUpRate) {
		this.wxUpRate = wxUpRate;
	}

	public BigDecimal getWxUpCash() {
		return wxUpCash;
	}

	public void setWxUpCash(BigDecimal wxUpCash) {
		this.wxUpCash = wxUpCash;
	}

	public BigDecimal getWxUpRate1() {
		return wxUpRate1;
	}

	public void setWxUpRate1(BigDecimal wxUpRate1) {
		this.wxUpRate1 = wxUpRate1;
	}

	public BigDecimal getWxUpCash1() {
		return wxUpCash1;
	}

	public void setWxUpCash1(BigDecimal wxUpCash1) {
		this.wxUpCash1 = wxUpCash1;
	}

	public BigDecimal getZfbRate() {
		return zfbRate;
	}

	public void setZfbRate(BigDecimal zfbRate) {
		this.zfbRate = zfbRate;
	}

	public BigDecimal getZfbCash() {
		return zfbCash;
	}

	public void setZfbCash(BigDecimal zfbCash) {
		this.zfbCash = zfbCash;
	}

	public BigDecimal getEwmRate() {
		return ewmRate;
	}

	public void setEwmRate(BigDecimal ewmRate) {
		this.ewmRate = ewmRate;
	}

	public BigDecimal getEwmCash() {
		return ewmCash;
	}

	public void setEwmCash(BigDecimal ewmCash) {
		this.ewmCash = ewmCash;
	}

	public BigDecimal getYsfRate() {
		return ysfRate;
	}

	public void setYsfRate(BigDecimal ysfRate) {
		this.ysfRate = ysfRate;
	}

	public Integer getCashStatus() {
		return cashStatus;
	}

	public void setCashStatus(Integer cashStatus) {
		this.cashStatus = cashStatus;
	}

	public Integer getTxnDetail() {
		return txnDetail;
	}

	public void setTxnDetail(Integer txnDetail) {
		this.txnDetail = txnDetail;
	}

	public Integer getHucid() {
		return hucid;
	}

	public void setHucid(Integer hucid) {
		this.hucid = hucid;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Date getAdate() {
		return adate;
	}

	public void setAdate(Date adate) {
		this.adate = adate;
	}

	public Date getZdate() {
		return zdate;
	}

	public void setZdate(Date zdate) {
		this.zdate = zdate;
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

	// 代理商成本信息
	private String costData;
	
	public String getCostData() {
		return costData;
	}

	public void setCostData(String costData) {
		this.costData = costData;
	}

	public String getFID() {
		return FID;
	}

	public void setFID(String fID) {
		FID = fID;
	}

	public String getCbUpLoadFile() {
		return cbUpLoadFile;
	}

	public void setCbUpLoadFile(String cbUpLoadFile) {
		this.cbUpLoadFile = cbUpLoadFile;
	}

	public String getZipUpLoadFile() {
		return zipUpLoadFile;
	}

	public void setZipUpLoadFile(String zipUpLoadFile) {
		this.zipUpLoadFile = zipUpLoadFile;
	}

	public String getCbUpLoad() {
		return cbUpLoad;
	}

	public void setCbUpLoad(String cbUpLoad) {
		this.cbUpLoad = cbUpLoad;
	}

	public String getZipUpLoad() {
		return zipUpLoad;
	}

	public void setZipUpLoad(String zipUpLoad) {
		this.zipUpLoad = zipUpLoad;
	}



	public Integer getStatusName() {
		return statusName;
	}

	public void setStatusName(Integer statusName) {
		this.statusName = statusName;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getUnLvl() {
		return unLvl;
	}

	public void setUnLvl(String unLvl) {
		this.unLvl = unLvl;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getBuid() {
		return buid;
	}

	public void setBuid(Integer buid) {
		this.buid = buid;
	}

	public String getUnno() {
		return unno;
	}

	public void setUnno(String unno) {
		this.unno = unno;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getBaddr() {
		return baddr;
	}

	public void setBaddr(String baddr) {
		this.baddr = baddr;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getLegalType() {
		return legalType;
	}

	public void setLegalType(String legalType) {
		this.legalType = legalType;
	}

	public String getLegalNum() {
		return legalNum;
	}

	public void setLegalNum(String legalNum) {
		this.legalNum = legalNum;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public String getBankAccName() {
		return bankAccName;
	}

	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getBankArea() {
		return bankArea;
	}

	public void setBankArea(String bankArea) {
		this.bankArea = bankArea;
	}

	public Double getRiskAmount() {
		return riskAmount;
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
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Integer getSignUserId() {
		return signUserId;
	}

	public void setSignUserId(Integer signUserId) {
		this.signUserId = signUserId;
	}

	public String getBno() {
		return bno;
	}

	public void setBno(String bno) {
		this.bno = bno;
	}

	public String getRno() {
		return rno;
	}

	public void setRno(String rno) {
		this.rno = rno;
	}

	public String getRegistryNo() {
		return registryNo;
	}

	public void setRegistryNo(String registryNo) {
		this.registryNo = registryNo;
	}

	public String getBankOpenAcc() {
		return bankOpenAcc;
	}

	public void setBankOpenAcc(String bankOpenAcc) {
		this.bankOpenAcc = bankOpenAcc;
	}

	public Integer getMaintainUserId() {
		return maintainUserId;
	}

	public void setMaintainUserId(Integer maintainUserId) {
		this.maintainUserId = maintainUserId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getSecondContact() {
		return secondContact;
	}

	public void setSecondContact(String secondContact) {
		this.secondContact = secondContact;
	}

	public String getSecondContactTel() {
		return secondContactTel;
	}

	public void setSecondContactTel(String secondContactTel) {
		this.secondContactTel = secondContactTel;
	}

	public String getSecondContactPhone() {
		return secondContactPhone;
	}

	public void setSecondContactPhone(String secondContactPhone) {
		this.secondContactPhone = secondContactPhone;
	}

	public String getRiskContact() {
		return riskContact;
	}

	public void setRiskContact(String riskContact) {
		this.riskContact = riskContact;
	}

	public String getRiskContactPhone() {
		return riskContactPhone;
	}

	public void setRiskContactPhone(String riskContactPhone) {
		this.riskContactPhone = riskContactPhone;
	}

	public String getRiskContactMail() {
		return riskContactMail;
	}

	public void setRiskContactMail(String riskContactMail) {
		this.riskContactMail = riskContactMail;
	}

	public String getBusinessContacts() {
		return businessContacts;
	}

	public void setBusinessContacts(String businessContacts) {
		this.businessContacts = businessContacts;
	}

	public String getBusinessContactsPhone() {
		return businessContactsPhone;
	}

	public void setBusinessContactsPhone(String businessContactsPhone) {
		this.businessContactsPhone = businessContactsPhone;
	}

	public String getBusinessContactsMail() {
		return businessContactsMail;
	}

	public void setBusinessContactsMail(String businessContactsMail) {
		this.businessContactsMail = businessContactsMail;
	}

	public Integer getMaintainUid() {
		return maintainUid;
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
		return maintainType;
	}

	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}

	public String getAmountConfirmName() {
		return amountConfirmName;
	}

	public void setAmountConfirmName(String amountConfirmName) {
		this.amountConfirmName = amountConfirmName;
	}

	public String getOpenName() {
		return openName;
	}

	public void setOpenName(String openName) {
		this.openName = openName;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getMaintainUserName() {
		return maintainUserName;
	}

	public void setMaintainUserName(String maintainUserName) {
		this.maintainUserName = maintainUserName;
	}

	public String getMaintainUserIdName() {
		return maintainUserIdName;
	}

	public void setMaintainUserIdName(String maintainUserIdName) {
		this.maintainUserIdName = maintainUserIdName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getLegalTypeName() {
		return legalTypeName;
	}

	public void setLegalTypeName(String legalTypeName) {
		this.legalTypeName = legalTypeName;
	}

	public String getAccTypeName() {
		return accTypeName;
	}

	public void setAccTypeName(String accTypeName) {
		this.accTypeName = accTypeName;
	}

	public String getBankTypeName() {
		return bankTypeName;
	}

	public void setBankTypeName(String bankTypeName) {
		this.bankTypeName = bankTypeName;
	}

	public String getAreaTypeName() {
		return areaTypeName;
	}

	public void setAreaTypeName(String areaTypeName) {
		this.areaTypeName = areaTypeName;
	}

	public Integer getAgentAttr() {
		return agentAttr;
	}

	public void setAgentAttr(Integer agentAttr) {
		this.agentAttr = agentAttr;
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

	public File getDealUpLoadFile() {
		return dealUpLoadFile;
	}

	public void setDealUpLoadFile(File dealUpLoadFile) {
		this.dealUpLoadFile = dealUpLoadFile;
	}

	public File getBusLicUpLoadFile() {
		return busLicUpLoadFile;
	}

	public void setBusLicUpLoadFile(File busLicUpLoadFile) {
		this.busLicUpLoadFile = busLicUpLoadFile;
	}

	public File getProofOfOpenAccountUpLoadFile() {
		return proofOfOpenAccountUpLoadFile;
	}

	public void setProofOfOpenAccountUpLoadFile(File proofOfOpenAccountUpLoadFile) {
		this.proofOfOpenAccountUpLoadFile = proofOfOpenAccountUpLoadFile;
	}

	public File getLegalAUpLoadFile() {
		return legalAUpLoadFile;
	}

	public void setLegalAUpLoadFile(File legalAUpLoadFile) {
		this.legalAUpLoadFile = legalAUpLoadFile;
	}

	public File getLegalBUpLoadFile() {
		return legalBUpLoadFile;
	}

	public void setLegalBUpLoadFile(File legalBUpLoadFile) {
		this.legalBUpLoadFile = legalBUpLoadFile;
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

	public File getAccountAuthUpLoadFile() {
		return accountAuthUpLoadFile;
	}

	public void setAccountAuthUpLoadFile(File accountAuthUpLoadFile) {
		this.accountAuthUpLoadFile = accountAuthUpLoadFile;
	}

	public File getAccountLegalAUpLoadFile() {
		return accountLegalAUpLoadFile;
	}

	public void setAccountLegalAUpLoadFile(File accountLegalAUpLoadFile) {
		this.accountLegalAUpLoadFile = accountLegalAUpLoadFile;
	}

	public File getAccountLegalBUpLoadFile() {
		return accountLegalBUpLoadFile;
	}

	public void setAccountLegalBUpLoadFile(File accountLegalBUpLoadFile) {
		this.accountLegalBUpLoadFile = accountLegalBUpLoadFile;
	}

	public File getAccountLegalHandUpLoadFile() {
		return accountLegalHandUpLoadFile;
	}

	public void setAccountLegalHandUpLoadFile(File accountLegalHandUpLoadFile) {
		this.accountLegalHandUpLoadFile = accountLegalHandUpLoadFile;
	}

	public File getOfficeAddressUpLoadFile() {
		return officeAddressUpLoadFile;
	}

	public void setOfficeAddressUpLoadFile(File officeAddressUpLoadFile) {
		this.officeAddressUpLoadFile = officeAddressUpLoadFile;
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

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public File getProfitUpLoadFile() {
		return profitUpLoadFile;
	}

	public void setProfitUpLoadFile(File profitUpLoadFile) {
		this.profitUpLoadFile = profitUpLoadFile;
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

	public String getSignUserIdName() {
		return signUserIdName;
	}

	public void setSignUserIdName(String signUserIdName) {
		this.signUserIdName = signUserIdName;
	}

	public String getParentUnno() {
		return parentUnno;
	}

	public void setParentUnno(String parentUnno) {
		this.parentUnno = parentUnno;
	}

	public String getCurAmt() {
		return curAmt;
	}

	public void setCurAmt(String curAmt) {
		this.curAmt = curAmt;
	}

	public String getCashDay() {
		return cashDay;
	}

	public void setCashDay(String cashDay) {
		this.cashDay = cashDay;
	}

	public String getCashDay1() {
		return cashDay1;
	}

	public void setCashDay1(String cashDay1) {
		this.cashDay1 = cashDay1;
	}

	public String getAgentMid() {
		return agentMid;
	}

	public void setAgentMid(String agentMid) {
		this.agentMid = agentMid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
