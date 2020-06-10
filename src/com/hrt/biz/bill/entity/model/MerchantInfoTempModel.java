package com.hrt.biz.bill.entity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户信息
 */
public class MerchantInfoTempModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String unno;				//机构编号
	private String rname;				//商户注册名称
	private String areaCode;			//商户所在地区
	private Integer busid;				//业务人员ID
	private String saleName;			//业务人员名称
	private String legalPerson;			//法人
	private String legalType;			//法人证件类型
	private String legalNum;			//法人证件号码
	private String accNum;				//入账人证件号
	private String bno;					//营业执照编号
	private String areaAddr;			//归属区域地址
	private String baddr;				//营业地址
	private String raddr;				//注册地址
	private String mccid;				//行业编码
	private String payBankId;			//支付系统行号
	private String bankFeeRate;			//银联卡费率
	private String feeAmt;				//封顶手续费
	private Integer merchantType;		//是否大小额商户
	private String accType;				//开户类型
	private String bankBranch;			//开户银行
	private String bankAccNo;			//开户银行账号
	private String bankAccName;			//开户账号名称
	private String contactPerson;		//联系人
	private String contactPhone;		//联系手机
	private Date maintainDate;			//操作日期
	private String cby;					//授权人员
	private Integer isM35;				//区分是传统商户  0：传统商户  1：微商户
	private String hybPhone;			//会员宝注册手机号
	private String remarks;				//备注
	private Integer fileId;				//文件FID主键
	
	private String creditBankRate;             //贷记卡费率
	private String creditFeeamt;             //贷记卡大额封顶手续费
	private String outsideBankRate;           //境外银联卡费率
	
	public String getCreditBankRate() {
		return creditBankRate;
	}
	public void setCreditBankRate(String creditBankRate) {
		this.creditBankRate = creditBankRate;
	}
	public String getCreditFeeamt() {
		return creditFeeamt;
	}
	public void setCreditFeeamt(String creditFeeamt) {
		this.creditFeeamt = creditFeeamt;
	}
	public String getOutsideBankRate() {
		return outsideBankRate;
	}
	public void setOutsideBankRate(String outsideBankRate) {
		this.outsideBankRate = outsideBankRate;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public Integer getBusid() {
		return busid;
	}
	public void setBusid(Integer busid) {
		this.busid = busid;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
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
	public String getAccNum() {
		return accNum;
	}
	public void setAccNum(String accNum) {
		this.accNum = accNum;
	}
	public String getBno() {
		return bno;
	}
	public void setBno(String bno) {
		this.bno = bno;
	}
	public String getAreaAddr() {
		return areaAddr;
	}
	public void setAreaAddr(String areaAddr) {
		this.areaAddr = areaAddr;
	}
	public String getBaddr() {
		return baddr;
	}
	public void setBaddr(String baddr) {
		this.baddr = baddr;
	}
	public String getRaddr() {
		return raddr;
	}
	public void setRaddr(String raddr) {
		this.raddr = raddr;
	}
	public String getMccid() {
		return mccid;
	}
	public void setMccid(String mccid) {
		this.mccid = mccid;
	}
	public String getPayBankId() {
		return payBankId;
	}
	public void setPayBankId(String payBankId) {
		this.payBankId = payBankId;
	}
	public String getBankFeeRate() {
		return bankFeeRate;
	}
	public void setBankFeeRate(String bankFeeRate) {
		this.bankFeeRate = bankFeeRate;
	}
	public String getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}
	public Integer getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(Integer merchantType) {
		this.merchantType = merchantType;
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
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public Date getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}
	public String getCby() {
		return cby;
	}
	public void setCby(String cby) {
		this.cby = cby;
	}
	public Integer getIsM35() {
		return isM35;
	}
	public void setIsM35(Integer isM35) {
		this.isM35 = isM35;
	}
	public String getHybPhone() {
		return hybPhone;
	}
	public void setHybPhone(String hybPhone) {
		this.hybPhone = hybPhone;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	@Override
	public boolean equals(Object obj) {
		return true;
	}
	@Override
	public int hashCode() {
		return 1;
	}
	
	
	
}