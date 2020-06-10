package com.hrt.biz.bill.entity.pagebean;

import java.util.Date;

public class MerchantInfoCommBean {
	
	private static final long serialVersionUID = 1L;
	
	private Integer page;	// 当前页数
	private Integer rows;	// 总记录数
	private String sort;	// 排序字段
	private String order;	// 排序规则 ASC DESC
	
	private Integer MAID; //int  NOT NULL CONSTRAINT PK_BILL_MERCHANTINFO_COMM PRIMARY KEY,商户ID
	private String MID; //char (15) NOT NULL,  -- MID商户编号
	private String MERCHNAME; //VARCHAR2(200),  -- 商户名称
	private String BNO; // varchar2(50),--营业执照
	private String LegalPerson; //varchar2(30),--法人
	private String LegalNum;  //varchar2(30), --身份证号
	private String BusinessScope; //varchar2(50),--实际经营范围
	private String BAddr; //varchar2(200),    --经营地址
	private String RAddr; //varchar2(200),    --注册地址
	private String ShoppingName; //varchar2(200),-- 店铺名称
	private Date  ReportDate;  // Date,  --报审日期
	private Date ApprovalDate;  // Date,  --批准日期
	private Date ReceiptDate;    // Date,  --收单日期
	private Date InstallDate;    // Date,  --安装日期
	private String Deposit;      // varchar2(20), --押金
	private String ServiceCharge;// varchar2(20), --服务费
	  private String CardType;    // varchar2(40), --卡种
	  private String InstallCard;  // varchar2(50),  --安装卡种
	  private String BankName;     // varchar2(100),--开户行
	  private String AccNo;        // varchar2(30), --帐号
	  private String BankAccName;  // varchar2(100),--开户帐号名称
	  private Double  BankFeeRate;  // NUMBER(18,2),--内卡费率
	  private Double WildFeeRate;   //NUMBER(18,2), --外卡费率
	  private Double StorageFeeRate; // NUMBER(18,2),--储联卡费率
	  private Double FeeRate;       // NUMBER(18,2),--储值卡费率
	  private String PayBankID;   //  varchar2(100), --支付系统行号
	  private String Exchange;    //  varchar2(20),--交换号
	  private String ContactPerson; //varchar2(50), --联系人
	  private String ContactPhone;  //varchar2(20),--手机
	  private String ContactTel;   // varchar2(20),--固话
	  private String Email;         //varchar2(50), --邮箱
	  private String SecondContactPerson;// varchar2(50), --联系人
	  private String SecondContactPhone; // varchar2(20),--手机
	  private String SecondContactTel;    //varchar2(20),--固话
	  private String MCCID;     //   CHAR(4),  --行业编码
	  private Integer SignUserID;     //     int,-- 销售
	  private Integer MaintainUserID;    //  int,-- 维护销售
	  private Integer Status;      // --状态
	  private String MINFO1; //VARCHAR2(100),
	  private String MINFO2; //VARCHAR2(100),
	  private String Remarks; //varchar2 (200) NULL 
	  private String TID;
	  private String installedAddress;
	  
	  
	  public String getInstalledAddress() {
		return installedAddress;
	}
	public void setInstalledAddress(String installedAddress) {
		this.installedAddress = installedAddress;
	}
	
	  
	  
	public String getInstallCard() {
		return InstallCard;
	}
	public void setInstallCard(String installCard) {
		InstallCard = installCard;
	}
	public Integer getStatus() {
		return Status;
	}
	public void setStatus(Integer status) {
		Status = status;
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
	public String getMERCHNAME() {
		return MERCHNAME;
	}
	public void setMERCHNAME(String mERCHNAME) {
		MERCHNAME = mERCHNAME;
	}
	public String getBNO() {
		return BNO;
	}
	public void setBNO(String bNO) {
		BNO = bNO;
	}
	public String getLegalPerson() {
		return LegalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		LegalPerson = legalPerson;
	}
	public String getLegalNum() {
		return LegalNum;
	}
	public void setLegalNum(String legalNum) {
		LegalNum = legalNum;
	}
	public String getBusinessScope() {
		return BusinessScope;
	}
	public void setBusinessScope(String businessScope) {
		BusinessScope = businessScope;
	}
	public String getBAddr() {
		return BAddr;
	}
	public void setBAddr(String bAddr) {
		BAddr = bAddr;
	}
	public String getRAddr() {
		return RAddr;
	}
	public void setRAddr(String rAddr) {
		RAddr = rAddr;
	}
	public String getShoppingName() {
		return ShoppingName;
	}
	public void setShoppingName(String shoppingName) {
		ShoppingName = shoppingName;
	}
	public Date getReportDate() {
		return ReportDate;
	}
	public void setReportDate(Date reportDate) {
		ReportDate = reportDate;
	}
	public Date getApprovalDate() {
		return ApprovalDate;
	}
	public void setApprovalDate(Date approvalDate) {
		ApprovalDate = approvalDate;
	}
	public Date getReceiptDate() {
		return ReceiptDate;
	}
	public void setReceiptDate(Date receiptDate) {
		ReceiptDate = receiptDate;
	}
	public Date getInstallDate() {
		return InstallDate;
	}
	public void setInstallDate(Date installDate) {
		InstallDate = installDate;
	}
	public String getDeposit() {
		return Deposit;
	}
	public void setDeposit(String deposit) {
		Deposit = deposit;
	}
	public String getServiceCharge() {
		return ServiceCharge;
	}
	public void setServiceCharge(String serviceCharge) {
		ServiceCharge = serviceCharge;
	}
	public String getCardType() {
		return CardType;
	}
	public void setCardType(String cardType) {
		CardType = cardType;
	}
	public String getBankName() {
		return BankName;
	}
	public void setBankName(String bankName) {
		BankName = bankName;
	}
	public String getAccNo() {
		return AccNo;
	}
	public void setAccNo(String accNo) {
		AccNo = accNo;
	}
	public String getBankAccName() {
		return BankAccName;
	}
	public void setBankAccName(String bankAccName) {
		BankAccName = bankAccName;
	}
	public Double getBankFeeRate() {
		return BankFeeRate;
	}
	public void setBankFeeRate(Double bankFeeRate) {
		BankFeeRate = bankFeeRate;
	}
	public Double getWildFeeRate() {
		return WildFeeRate;
	}
	public void setWildFeeRate(Double wildFeeRate) {
		WildFeeRate = wildFeeRate;
	}
	public Double getStorageFeeRate() {
		return StorageFeeRate;
	}
	public void setStorageFeeRate(Double storageFeeRate) {
		StorageFeeRate = storageFeeRate;
	}
	public Double getFeeRate() {
		return FeeRate;
	}
	public void setFeeRate(Double feeRate) {
		FeeRate = feeRate;
	}
	public String getPayBankID() {
		return PayBankID;
	}
	public void setPayBankID(String payBankID) {
		PayBankID = payBankID;
	}
	public String getExchange() {
		return Exchange;
	}
	public void setExchange(String exchange) {
		Exchange = exchange;
	}
	public String getContactPerson() {
		return ContactPerson;
	}
	public void setContactPerson(String contactPerson) {
		ContactPerson = contactPerson;
	}
	public String getContactPhone() {
		return ContactPhone;
	}
	public void setContactPhone(String contactPhone) {
		ContactPhone = contactPhone;
	}
	public String getContactTel() {
		return ContactTel;
	}
	public void setContactTel(String contactTel) {
		ContactTel = contactTel;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getSecondContactPerson() {
		return SecondContactPerson;
	}
	public void setSecondContactPerson(String secondContactPerson) {
		SecondContactPerson = secondContactPerson;
	}
	public String getSecondContactPhone() {
		return SecondContactPhone;
	}
	public void setSecondContactPhone(String secondContactPhone) {
		SecondContactPhone = secondContactPhone;
	}
	public String getSecondContactTel() {
		return SecondContactTel;
	}
	public void setSecondContactTel(String secondContactTel) {
		SecondContactTel = secondContactTel;
	}
	public String getMCCID() {
		return MCCID;
	}
	public void setMCCID(String mCCID) {
		MCCID = mCCID;
	}
	public Integer getSignUserID() {
		return SignUserID;
	}
	public void setSignUserID(Integer signUserID) {
		SignUserID = signUserID;
	}
	
	public Integer getMaintainUserID() {
		return MaintainUserID;
	}
	public void setMaintainUserID(Integer maintainUserID) {
		MaintainUserID = maintainUserID;
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
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
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
	public String getTID() {
		return TID;
	}
	public void setTID(String tID) {
		TID = tID;
	}
	
	  
}
