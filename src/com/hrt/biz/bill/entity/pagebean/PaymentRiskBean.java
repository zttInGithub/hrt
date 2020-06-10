package com.hrt.biz.bill.entity.pagebean;


public class PaymentRiskBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	//当前页数
		private Integer page;
		
		//总记录数
		private Integer rows;
		
		//排序字段
		private String sort;
		
		//排序规则 ASC DESC
		private String order;
		
	
	//报文头
	private String Version;			//版本
	private String Identification;		//报文唯一标识（8位日期+10顺序号
	private String OrigSender;			//会员单位机构号（
	private String OrigSenderSID;		//会员单位发送系统号C
	private String RecSystemId;		//协会系统编号,见数据字典	
	private String TrnxCode;		//交易码，
	private String TrnxTime;		//交易时间格式：yyyyMMDDHHmmss（数字
	private String UserToken;		//用户登录信息凭证（用户登录和登出时无需该字段）
	private String SecretKey;		//密钥
	
	//个人信息上报 请求报文体
	private String CusProperty;			//客户属性（值：01），参见数据字典定义
	private String RiskType;		//风险类型
	private String MobileNo;			//手机号
	private String Mac;		//MAC地址（XX:XX:XX:XX:XX:XX或XX-XX-XX-XX-XX-XX，其中X取值范围为0-9,A-F。）
	private String Imei;		//Imei必须为小于或等于32位数字组成
	private String BankNo;		//银行帐/卡号（只能输入数字且不能超过32位字符）
	private String OpenBank;		//开户行
	private String CusName;		//客户姓名
	private String DocCode;		//身份证件号码
	private String Ip;			//IP地址（IP格式不正确，应为xxx.xxx.xxx.xxx，xxx可为3位数字，也可为一位数字）
	private String Address;		//收货地址
	private String Telephone;			//固定电话（必须输入三位或四位区号，七位或八位电话号码，区号为三位时电话号码必须为八位），区号与电话号码以-隔开
	private String RecBankNo;		//收款银行帐/卡号（只能输入数字且不能超过32位字符）
	private String RecOpenBank;		//收款账/卡号开户行
	private String Email;		//邮箱（为**@**.*,必须含“@”，*取值范围0-9，A-F，a-f，且不能超过64个字符）
	private String ValidDate;		//有效期 格式：yyyy-MM-DD
	private String Level;		//信息级别，参见数据字典
	private String Occurtimeb;		//风险事件发生时间，时间格式，YYYY-MM-DD,例如2016-06-07
	private String Occurtimee;			//风险事件结束时间，时间格式，YYYY-MM-DD,例如2016-06-07
	private String Occurchan;		//风险事件发生渠道,参照数据字段（事件发生渠道）
	private String Occurarea;			//风险事件发生地域，省级/地市，可多选
	private String Note;		//风险事件描述
	private String OrgId;		//上报机构（数字、下划线、字母
	private String RepDate;		//上报日期 格式：yyyy-MM-DD HH:mm:ss
	private String RepType;		//上传方式（值：03），参见数据字典定义
	private String RepPerson;		//上传人
	private String Scope;
	//商户信息上报 请求报文体
	//CusNature RegName CusCode OrgCode BusLicCode SocialUnityCreditCode TaxRegCer LegRepName LegDocCode Url  ServerIp Icp
	//   zzzz z统一社会信用代码zvz/负责人姓名zz z z/
	
	
	/*LegDocType 01:身份证	02:军官证	03:护照	04:户口簿	05:士兵证	06:港澳来往内地通行证 07:台湾同胞来往内地通行证 08:临时身份证	09:外国人居留证10:警官证	99:其他*/
	private String LegDocType;  //
	private String DocType;    //01:营业执照编码  02:统一社会信息代码 03:组织机构代码证 04:经营许可证 05：税务登记证 99:其他
	private String CusType;     //01:自然人   02:企业商户   03:个体工商户
	private String CusNature;	//商户属性 01实体商户 02 网络商户
	private String RegName;		//商户营业注册名称
	private String CusCode;			//商户编码
	private String OrgCode;		//组织机构代码
	private String BusLicCode;		//营业执照编码
	private String SocialUnityCreditCode;//统一社会信用代码
	private String TaxRegCer;		//税务登记证
	private String LegRepName;		//法定代表人姓名
	private String LegDocCode;		//法人身份证件号码
	private String Url;			//网址
	private String ServerIp;		//服务器IP
	private String Icp;				// ICP编号
	
	//批量信息导入查询
	private String KeyWord;		//关键字
	private String Infos;		//查询条件信息
	//风险信息汇总查询
	private String Info;		//关键字
	private String StartTime;	//v上报开始时间
	private String EndTime;		//上报结束时间
	
	private String UpdateType; //变更类型
	
	private String Id;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public String getIdentification() {
		return Identification;
	}
	public void setIdentification(String identification) {
		Identification = identification;
	}
	public String getOrigSender() {
		return OrigSender;
	}
	public void setOrigSender(String origSender) {
		OrigSender = origSender;
	}
	public String getOrigSenderSID() {
		return OrigSenderSID;
	}
	public void setOrigSenderSID(String origSenderSID) {
		OrigSenderSID = origSenderSID;
	}
	public String getRecSystemId() {
		return RecSystemId;
	}
	public void setRecSystemId(String recSystemId) {
		RecSystemId = recSystemId;
	}
	public String getTrnxCode() {
		return TrnxCode;
	}
	public void setTrnxCode(String trnxCode) {
		TrnxCode = trnxCode;
	}
	public String getTrnxTime() {
		return TrnxTime;
	}
	public void setTrnxTime(String trnxTime) {
		TrnxTime = trnxTime;
	}
	public String getUserToken() {
		return UserToken;
	}
	public void setUserToken(String userToken) {
		UserToken = userToken;
	}
	public String getSecretKey() {
		return SecretKey;
	}
	public void setSecretKey(String secretKey) {
		SecretKey = secretKey;
	}
	public String getCusProperty() {
		return CusProperty;
	}
	public void setCusProperty(String cusProperty) {
		CusProperty = cusProperty;
	}
	public String getRiskType() {
		return RiskType;
	}
	public void setRiskType(String riskType) {
		RiskType = riskType;
	}
	public String getMobileNo() {
		return MobileNo;
	}
	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}
	public String getMac() {
		return Mac;
	}
	public void setMac(String mac) {
		Mac = mac;
	}
	public String getImei() {
		return Imei;
	}
	public void setImei(String imei) {
		Imei = imei;
	}
	public String getBankNo() {
		return BankNo;
	}
	public void setBankNo(String bankNo) {
		BankNo = bankNo;
	}
	public String getOpenBank() {
		return OpenBank;
	}
	public void setOpenBank(String openBank) {
		OpenBank = openBank;
	}
	public String getCusName() {
		return CusName;
	}
	public void setCusName(String cusName) {
		CusName = cusName;
	}
	public String getDocCode() {
		return DocCode;
	}
	public void setDocCode(String docCode) {
		DocCode = docCode;
	}
	public String getIp() {
		return Ip;
	}
	public void setIp(String ip) {
		Ip = ip;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getTelephone() {
		return Telephone;
	}
	public void setTelephone(String telephone) {
		Telephone = telephone;
	}
	public String getRecBankNo() {
		return RecBankNo;
	}
	public void setRecBankNo(String recBankNo) {
		RecBankNo = recBankNo;
	}
	public String getRecOpenBank() {
		return RecOpenBank;
	}
	public void setRecOpenBank(String recOpenBank) {
		RecOpenBank = recOpenBank;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getValidDate() {
		return ValidDate;
	}
	public void setValidDate(String validDate) {
		ValidDate = validDate;
	}
	public String getLevel() {
		return Level;
	}
	public void setLevel(String level) {
		Level = level;
	}
	public String getOccurtimeb() {
		return Occurtimeb;
	}
	public void setOccurtimeb(String occurtimeb) {
		Occurtimeb = occurtimeb;
	}
	public String getOccurtimee() {
		return Occurtimee;
	}
	public void setOccurtimee(String occurtimee) {
		Occurtimee = occurtimee;
	}
	public String getOccurchan() {
		return Occurchan;
	}
	public void setOccurchan(String occurchan) {
		Occurchan = occurchan;
	}
	public String getOccurarea() {
		return Occurarea;
	}
	public void setOccurarea(String occurarea) {
		Occurarea = occurarea;
	}
	public String getNote() {
		return Note;
	}
	public void setNote(String note) {
		Note = note;
	}
	public String getOrgId() {
		return OrgId;
	}
	public void setOrgId(String orgId) {
		OrgId = orgId;
	}
	public String getRepDate() {
		return RepDate;
	}
	public void setRepDate(String repDate) {
		RepDate = repDate;
	}
	public String getRepType() {
		return RepType;
	}
	public void setRepType(String repType) {
		RepType = repType;
	}
	public String getRepPerson() {
		return RepPerson;
	}
	public void setRepPerson(String repPerson) {
		RepPerson = repPerson;
	}
	public String getCusNature() {
		return CusNature;
	}
	public void setCusNature(String cusNature) {
		CusNature = cusNature;
	}
	public String getRegName() {
		return RegName;
	}
	public void setRegName(String regName) {
		RegName = regName;
	}
	public String getCusCode() {
		return CusCode;
	}
	public void setCusCode(String cusCode) {
		CusCode = cusCode;
	}
	public String getOrgCode() {
		return OrgCode;
	}
	public void setOrgCode(String orgCode) {
		OrgCode = orgCode;
	}
	public String getBusLicCode() {
		return BusLicCode;
	}
	public void setBusLicCode(String busLicCode) {
		BusLicCode = busLicCode;
	}
	public String getSocialUnityCreditCode() {
		return SocialUnityCreditCode;
	}
	public void setSocialUnityCreditCode(String socialUnityCreditCode) {
		SocialUnityCreditCode = socialUnityCreditCode;
	}
	public String getTaxRegCer() {
		return TaxRegCer;
	}
	public void setTaxRegCer(String taxRegCer) {
		TaxRegCer = taxRegCer;
	}
	public String getLegRepName() {
		return LegRepName;
	}
	public void setLegRepName(String legRepName) {
		LegRepName = legRepName;
	}
	public String getLegDocCode() {
		return LegDocCode;
	}
	public void setLegDocCode(String legDocCode) {
		LegDocCode = legDocCode;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public String getServerIp() {
		return ServerIp;
	}
	public void setServerIp(String serverIp) {
		ServerIp = serverIp;
	}
	public String getIcp() {
		return Icp;
	}
	public void setIcp(String icp) {
		Icp = icp;
	}
	public String getKeyWord() {
		return KeyWord;
	}
	public void setKeyWord(String keyWord) {
		KeyWord = keyWord;
	}
	public String getInfos() {
		return Infos;
	}
	public void setInfos(String infos) {
		Infos = infos;
	}
	public String getInfo() {
		return Info;
	}
	public void setInfo(String info) {
		Info = info;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getUpdateType() {
		return UpdateType;
	}
	public void setUpdateType(String updateType) {
		UpdateType = updateType;
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
	public String getScope() {
		return Scope;
	}
	public void setScope(String scope) {
		Scope = scope;
	}
	public String getCusType() {
		return CusType;
	}
	public void setCusType(String cusType) {
		CusType = cusType;
	}
	public String getDocType() {
		return DocType;
	}
	public void setDocType(String docType) {
		DocType = docType;
	}
	public String getLegDocType() {
		return LegDocType;
	}
	public void setLegDocType(String legDocType) {
		LegDocType = legDocType;
	}
	
}