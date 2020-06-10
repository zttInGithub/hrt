package com.hrt.biz.bill.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.hrt.util.ParamPropUtils;
import org.springframework.beans.BeanUtils;
import org.xml.sax.SAXException;

import com.hrt.biz.bill.dao.IRiskPendingMerchantDao;
import com.hrt.biz.bill.dao.IRiskPushMsgDao;
import com.hrt.biz.bill.dao.IRiskSpecialMerchantDao;
import com.hrt.biz.bill.entity.model.RiskPendingMerchantModel;
import com.hrt.biz.bill.entity.model.RiskPushMsgModel;
import com.hrt.biz.bill.entity.model.RiskSpecialMerchantModel;
import com.hrt.biz.bill.entity.pagebean.RiskSpecialMerchantBean;
import com.hrt.biz.bill.service.IRiskSpecialMerchantService;
import com.hrt.biz.util.PaymentRiskUtil;
import com.hrt.frame.entity.pagebean.DataGridBean;

@SuppressWarnings("all")
public class RiskSpecialMerchantServiceImpl implements
		IRiskSpecialMerchantService {
	private IRiskPendingMerchantDao riskPendingMerchantDao;
	private IRiskSpecialMerchantDao specialMerchantDao;
	private  IRiskPushMsgDao riskPushMsgDao;
	
	public IRiskPendingMerchantDao getRiskPendingMerchantDao() {
		return riskPendingMerchantDao;
	}
	public void setRiskPendingMerchantDao(
			IRiskPendingMerchantDao riskPendingMerchantDao) {
		this.riskPendingMerchantDao = riskPendingMerchantDao;
	}
	public IRiskPushMsgDao getRiskPushMsgDao() {
		return riskPushMsgDao;
	}
	public void setRiskPushMsgDao(IRiskPushMsgDao riskPushMsgDao) {
		this.riskPushMsgDao = riskPushMsgDao;
	}
	public IRiskSpecialMerchantDao getSpecialMerchantDao() {
		return specialMerchantDao;
	}
	public void setSpecialMerchantDao(IRiskSpecialMerchantDao specialMerchantDao) {
		this.specialMerchantDao = specialMerchantDao;
	}
	
	/************************************************************************/
	public DataGridBean querySpecialPersonal(
			RiskSpecialMerchantBean specialMerchantBean) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("CusType", specialMerchantBean.getCUSTYPE());
		String sql = "select id,custype,cusnature,regname,cusname,cusnameen,doctype,doccode,cuscode,indutype,bankno,openbank,"
				+ " (select value from RISK_DICTIONARY where code=regaddrprov) as regaddrprov,regaddrdetail,"
				+ " (select value from RISK_DICTIONARY where code=addrprov) as addrprov,"
				+ "addrdetail,url,serverip,icp,contname,contphone,cusemail,"
				+ "(select value from RISK_DICTIONARY where code=Occurarea) as occurarea,"
				+ "networktype,status,starttime,endtime,rishstatus,opentype,chagetype,accounttype,expandtype,outservicename,"
				+ "outservicecardtype,outservicecardcode,outservicelegcardtype,outservicelegcardcode,orgid,repdate,reptype,repperson,"
				+ "LegDocType,LegDocCode,ShareHolder from risk_special_merchant where 1 = 1 ";
		// REGNAME CUSCODE CONTPHONE REPDATE
		if (specialMerchantBean.getCUSTYPE().equals("01")) {
			sql += " and CusType =:CusType ";
		} else {
			param.put("CusType", "01");
			sql += " and CusType !=:CusType ";
		}
		if (null != specialMerchantBean.getRegName()
				&& !"".equals(specialMerchantBean.getRegName())) {
			param.put("regname", specialMerchantBean.getRegName().trim());
			sql += " and regname = :regname ";
		}
		if (null != specialMerchantBean.getCusCode()
				&& !"".equals(specialMerchantBean.getCusCode())) {
			param.put("cuscode", specialMerchantBean.getCusCode().trim());
			sql += " and cuscode = :cuscode ";
		}
		if (null != specialMerchantBean.getContPhone()
				&& !"".equals(specialMerchantBean.getContPhone())) {
			param.put("contphone", specialMerchantBean.getContPhone().trim());
			sql += " and contphone = :contphone ";
		}
		if (null != specialMerchantBean.getREPDATE()
				&& !"".equals(specialMerchantBean.getREPDATE())
				&& null != specialMerchantBean.getREPDATE1()
				&& !"".equals(specialMerchantBean.getREPDATE1())) {
			param.put("repdate0",
					specialMerchantBean.getREPDATE().replaceAll("-", ""));
			param.put("repdate1",
					specialMerchantBean.getREPDATE1().replaceAll("-", ""));
			sql += " and   replace(substr(repdate,1,10),'-','') between :repdate0 and :repdate1  ";
		}

		sql += " order by repdate ";
		String count = "select count(*) from (" + sql + ") t ";
		BigDecimal counts;
		List<Map<String, String>> list;
		list = specialMerchantDao.queryObjectsBySqlList(sql, param,
				specialMerchantBean.getPage(), specialMerchantBean.getRows());

		counts = specialMerchantDao.querysqlCounts(count, param);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());// 查询数据库总条数
		dgd.setRows(list);
		return dgd;
	}

	private String queryDictionary(String Occurarea) {
		String result = "";
		String sql = " select value from risk_dictionary where code in ("
				+ Occurarea + ")";
		List<Map> list = specialMerchantDao.queryObjectsBySqlList(sql);
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				result += list.get(i).get("VALUE");
			} else {
				result += list.get(i).get("VALUE") + ",";
			}
		}
		return result;
	}

	public Map reportSpecialPersonal(RiskSpecialMerchantBean specialMerchantBean)
			throws Exception {
		Map map1 = new LinkedHashMap();
		map1.put("Version", "V1.0.1");
		String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
				.format(new java.util.Date());
		String substring = date.substring(0, 8);
		// 获取10位随机数
		String randomString = String.valueOf(Math.random()).substring(2, 12);
		map1.put("Identification", date.substring(0, 8) + randomString);// 报文唯一标识（8位日期+10顺序号））
		map1.put("OrigSender", specialMerchantBean.getOrigSender());// 会员单位机构号（字母、数字、下划线）"Z2015611000018"
		map1.put("OrigSenderSID", "HrtApp2014");// 会员单位发送系统号（字母、数字、下划线）
		map1.put("RecSystemId", "SECB01");// 协会系统编号
		map1.put("TrnxCode", specialMerchantBean.getTrnxCode());// 个人商户信息上报请求EPR001,企业商户信息上报请求EER001
		map1.put("TrnxTime", date);
		map1.put("UserToken", specialMerchantBean.getUserToken());
		map1.put("SecretKey", "");// 密钥
		Map map2 = new LinkedHashMap();
		map2.put("CusType", specialMerchantBean.getCusType());// 2 Y
																// 商户类型，参见商户类型，默认01:自然人
		map2.put("CusNature", specialMerchantBean.getCusNature());// 2 Y
																	// 商户属性，参见数据字典定义（商户属性）
		map2.put("RegName", specialMerchantBean.getRegName());// 64 Y 商户名称
		map2.put("CusName", specialMerchantBean.getCusName());// 64 N 商户简称
		map2.put("CusNameEn", specialMerchantBean.getCusNameEn());// 64 N 商户英文名称
		map2.put("DocType", specialMerchantBean.getDocType());// 2 Y
																// 证件类型,参照数据字典（人员证件类型）
		map2.put("DocCode", specialMerchantBean.getDocCode());// 64 Y 证件号码
		map2.put("CusCode", specialMerchantBean.getCusCode());// 32 Y
																// 商户代码，最长不能超过32个字符
		map2.put("InduType", specialMerchantBean.getInduType());// 256 Y
																// 商户行业类别，可多选,多个已英文逗号分割
		map2.put("BankNo", specialMerchantBean.getBankNo());// 32 Y 收款账\卡号
		map2.put("OpenBank", specialMerchantBean.getOpenBank());// 64 Y
																// 收款账\卡号开户行
		map2.put("RegAddrProv", specialMerchantBean.getRegAddrProv());// 6 Y
																		// 商户注册地址(省)，参照数据字段（地区）
		map2.put("RegAddrDetail", specialMerchantBean.getRegAddrDetail());// 128
																			// Y
																			// 商户注册地址
		map2.put("AddrProv", specialMerchantBean.getAddrProv());// 6 Y 商户经营地址(省)
																// ，参照数据字段（地区）
		map2.put("AddrDetail", specialMerchantBean.getAddrDetail());// 128 Y
																	// 商户经营地址
		map2.put("Url", specialMerchantBean.getUrl());// 512 N 网址
		map2.put("ServerIp", specialMerchantBean.getServerIp());// 15 N
																// 服务器IP（应为xxx.xxx.xxx.xxx，xxx可为3位数字，也可为一位数字）
		map2.put("Icp", specialMerchantBean.getIcp());// 20 N ICP备案编号
		map2.put("ContName", specialMerchantBean.getContName());// 64 Y 商户联系人
		map2.put("ContPhone", specialMerchantBean.getContPhone());// 32 Y 商户联系电话
		map2.put("CusEmail", specialMerchantBean.getCusEmail());// 64 N 商户E-mail
		map2.put("Occurarea", getCity(specialMerchantBean.getOccurarea()));// Y
																			// 商户经营地区范围，省级，可多选
		map2.put("NetworkType", specialMerchantBean.getNetworkType());// 32 Y
																		// 清算网络，参见数据字典定义,
																		// 可多选,多个已英文逗号分割
		map2.put("Status", specialMerchantBean.getStatus());// 2 Y 商户状态，参见数据字典定义
		map2.put("StartTime", specialMerchantBean.getStartTime());// 10 Y
																	// 服务起始时间，时间格式，YYYY-MM-DD,例如2016-06-07
		map2.put("EndTime", specialMerchantBean.getEndTime());// 10 Y
																// 服务终止时间，时间格式，YYYY-MM-DD,例如2016-06-07
		map2.put("RiskStatus", specialMerchantBean.getRishStatus());// 2 Y
																	// 合规风险状况，参见数据字典定义
		map2.put("OpenType", specialMerchantBean.getOpenType());// 32 Y
																// 开通业务种类，参见数据字典定义,多个已英文逗号分割
		map2.put("ChageType", specialMerchantBean.getChageType());// 2 Y
																	// 计费类型，参见数据字典定义
		map2.put("AccountType", specialMerchantBean.getExpandType());// 2 Y
																		// 支持账户类型，参见数据字典定义
		map2.put("ExpandType", specialMerchantBean.getExpandType());// 2 Y
																	// 拓展方式，参见数据字典定义
		map2.put("OutServiceName", specialMerchantBean.getOutServiceName());// 128
																			// Y
																			// 外包服务机构名称
		map2.put("OutServiceCardType",
				specialMerchantBean.getOutServiceCardType());// 2 Y 外包服务机构法人证件类型
		map2.put("OutServiceCardCode",
				specialMerchantBean.getOutServiceCardCode());// 64 Y
																// 外包服务机构法人证件号码
		map2.put("OutServiceLegCardType",
				specialMerchantBean.getOutServiceLegCardType());// 2 Y
																// 外包服务机构法定代表人证件类型，，参见数据字典定义
		map2.put("OutServiceLegCardCode",
				specialMerchantBean.getOutServiceLegCardCode());// 64 Y
																// 外包服务机构法定代表人证件号码
		map2.put("OrgId", specialMerchantBean.getOrgId());// 32 Y
															// 上报机构（数字、下划线、字母）
		String RepDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date());
		map2.put("RepDate", RepDate);// 20 date Y 上报日期 格式：yyyy-MM-DD HH:mm:ss
		map2.put("RepType", "03");// 2 Y 上传方式（值：03），参见数据字典定义
		map2.put("RepPerson", specialMerchantBean.getRepPerson());// 32 Y 上传人

		String xmlhead = PaymentRiskUtil.xmlByMap(map1);
		xmlhead = "<Head>" + xmlhead + "</Head>";
		String getxml = PaymentRiskUtil.xmlByMap(map2);
		String xmlbody = "<Body><PcacList><Count>1</Count><BaseInfo>" + getxml
				+ "</BaseInfo></PcacList></Body>";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF8\"?><Document><Request>"
				+ xmlhead
				+ xmlbody
				+ "</Request><Signature></Signature></Document>";

		 String xsdPath =ParamPropUtils.props.getProperty("RiskXSD")+"pcac.ries.024.xsd";
		// 校验方法
		boolean checkXDS = false;
		checkXDS = PaymentRiskUtil.checkXDS(xml, xsdPath);
		if(!checkXDS){
			Map resultMsg = new HashMap();
			resultMsg.put("error", "录入数据校验未通过！");
			return resultMsg;
		}
		// 商户名称、商户简称、商户英文名称、证件号码、商户代码、收款账\卡号、商户注册地址、商户经营地址、
		// 网址、服务器IP、ICP备案编号、商户联系人、商户联系电话、外包服务机构名称、外包服务机构法人证件号码
		// 、外包服务机构法定代表人证件号码
		// 加密
		
		Map getMM = new HashMap();
		//商户名称
		if (!specialMerchantBean.getRegName().isEmpty()) {
			getMM.put("RegName", specialMerchantBean.getRegName());
		}
		//、商户简称
		if (!specialMerchantBean.getCusName().isEmpty()) {
			getMM.put("CusName", specialMerchantBean.getCusName());
		}
		//、商户英文名称
		if (!specialMerchantBean.getCusNameEn().isEmpty()) {
			getMM.put("CusNameEn", specialMerchantBean.getCusNameEn());
		}
		//、证件号码
		if (!specialMerchantBean.getDocCode().isEmpty()) {
			getMM.put("DocCode", specialMerchantBean.getDocCode());
		}
		//商户代码、
		if (!specialMerchantBean.getCusCode().isEmpty()) {
			getMM.put("CusCode", specialMerchantBean.getCusCode());
		}
		//收款账\卡号、
		if (!specialMerchantBean.getBankNo().isEmpty()) {
			getMM.put("BankNo", specialMerchantBean.getBankNo());
		}
		//商户注册地址、
		if (!specialMerchantBean.getRegAddrDetail().isEmpty()) {
			getMM.put("RegAddrDetail", specialMerchantBean.getRegAddrDetail());
		}
		//商户经营地址
		if (!specialMerchantBean.getAddrDetail().isEmpty()) {
			getMM.put("AddrDetail", specialMerchantBean.getAddrDetail());
		}
		//、网址
		if (!specialMerchantBean.getUrl().isEmpty()) {
			getMM.put("Url", specialMerchantBean.getUrl());
		}
		//、服务器IP、
		if (!specialMerchantBean.getServerIp().isEmpty()) {
			getMM.put("ServerIp", specialMerchantBean.getServerIp());
		}
		//ICP备案编号
		if (!specialMerchantBean.getIcp().isEmpty()) {
			getMM.put("Icp", specialMerchantBean.getIcp());
		}
		//、商户联系人
		if (!specialMerchantBean.getContName().isEmpty()) {
			getMM.put("ContName", specialMerchantBean.getContName());
		}
		//、商户联系电话、
		if (!specialMerchantBean.getContPhone().isEmpty()) {
			getMM.put("ContPhone", specialMerchantBean.getContPhone());
		}
		//外包服务机构名称、
		if (!specialMerchantBean.getOutServiceName().isEmpty()) {
			getMM.put("OutServiceName", specialMerchantBean.getOutServiceName());
		}
		//外包服务机构法人证件号码、
		if (!specialMerchantBean.getOutServiceCardCode().isEmpty()) {
			getMM.put("OutServiceCardCode",
					specialMerchantBean.getOutServiceCardCode());
		}
		//外包服务机构法定代表人证件号码
		if (!specialMerchantBean.getOutServiceLegCardCode().isEmpty()) {
			getMM.put("OutServiceLegCardCode",
					specialMerchantBean.getOutServiceLegCardCode());
		}
		Map resultMap = null;
		if (getMM.size() > 0) {
			String RiskPKC = ParamPropUtils.props.getProperty("RiskPKCertificate");
			resultMap = PaymentRiskUtil.getAEStoString(getMM,RiskPKC);//E:\\HrtApp\\src\\data\\会员单位.pfx
			map1.put("SecretKey", resultMap.get("encryptedKey"));
		}

		// 拼装加密后xml
		xmlhead = PaymentRiskUtil.xmlByMap(map1);

		xmlhead = "<Head>" + xmlhead + "</Head>";
		//商户名称
		if (!specialMerchantBean.getRegName().isEmpty()) {
			map2.put("RegName", resultMap.get("RegName"));
		}
		//商户简称、
		if (!specialMerchantBean.getCusName().isEmpty()) {
			map2.put("CusName", resultMap.get("CusName"));
		}
		//商户英文名称
		if (!specialMerchantBean.getCusNameEn().isEmpty()) {
			map2.put("CusNameEn", resultMap.get("CusNameEn"));
		}
		//、证件号码
		if (!specialMerchantBean.getDocCode().isEmpty()) {
			map2.put("DocCode", resultMap.get("DocCode"));
		}
		//、商户代码
		if (!specialMerchantBean.getCusCode().isEmpty()) {
			map2.put("CusCode", resultMap.get("CusCode"));
		}
		//、收款账\卡号
		if (!specialMerchantBean.getBankNo().isEmpty()) {
			map2.put("BankNo", resultMap.get("BankNo"));
		}
		//、商户注册地址
		if (!specialMerchantBean.getRegAddrDetail().isEmpty()) {
			map2.put("RegAddrDetail", resultMap.get("RegAddrDetail"));
		}
	//、商户经营地址
		if (!specialMerchantBean.getAddrDetail().isEmpty()) {
			map2.put("AddrDetail", resultMap.get("AddrDetail"));
		}
		//、网址
		if (!specialMerchantBean.getUrl().isEmpty()) {
			map2.put("Url", resultMap.get("Url"));
		}
		//、服务器IP
		if (!specialMerchantBean.getServerIp().isEmpty()) {
			map2.put("ServerIp", resultMap.get("ServerIp"));
		}
		//、、ICP备案编号、
		if (!specialMerchantBean.getIcp().isEmpty()) {
			map2.put("Icp", resultMap.get("Icp"));
		}
		//商户联系人、
		if (!specialMerchantBean.getContName().isEmpty()) {
			map2.put("ContName", resultMap.get("ContName"));
		}
		//商户联系电话、
		if (!specialMerchantBean.getContPhone().isEmpty()) {
			map2.put("ContPhone", resultMap.get("ContPhone"));
		}
		//外包服务机构名称、
		if (!specialMerchantBean.getOutServiceName().isEmpty()) {
			map2.put("OutServiceName", resultMap.get("OutServiceName"));
		}
		//外包服务机构法人证件号码、
		if (!specialMerchantBean.getOutServiceCardCode().isEmpty()) {
			map2.put("OutServiceCardCode", resultMap.get("OutServiceCardCode"));
		}
		//外包服务机构法定代表人证件号码
		if (!specialMerchantBean.getOutServiceLegCardCode().isEmpty()) {
			map2.put("OutServiceLegCardCode",
					resultMap.get("OutServiceLegCardCode"));
		}
		getxml = PaymentRiskUtil.xmlByMap(map2);
		// xmlbody = "<Body>" + getxml + "</Body>";
		xmlbody = "<Body><PcacList><Count>1</Count><BaseInfo>" + getxml
				+ "</BaseInfo></PcacList></Body>";
		// 加密后xml
		xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
				+ xmlhead + xmlbody
				+ "</Request><Signature></Signature></Document>";

		// 加签
		try {
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
					+ xmlhead + xmlbody + "</Request></Document>";
			String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
			String signPWD = ParamPropUtils.props.getProperty("signPWD");
			String addQian = PaymentRiskUtil.AddQian(xml, pfx, signPWD);
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
					+ xmlhead
					+ xmlbody
					+ "</Request><Signature>"
					+ addQian
					+ "</Signature></Document>";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 发送请求

		String url = ParamPropUtils.props.getProperty("RiskElseUrl");
		String Result = PaymentRiskUtil.doPost(url, xml);
		Map readMap = new HashMap();
		Map requestXml = PaymentRiskUtil.requestXml(Result, readMap);
		return requestXml;
	}

	private String getCity(String city) {
		if (null == city) {
			city = "";
		} else {
			city = city.replace(" ", "");
		}
		return city;
	}

	public void saveMerchantPersonal(RiskSpecialMerchantBean specialMerchantBean) {
		RiskSpecialMerchantModel model = new RiskSpecialMerchantModel();
		BeanUtils.copyProperties(specialMerchantBean, model);
		model.setRepType("03");
		specialMerchantDao.saveObject(model);
	}

	public Map reportSpecialMerchant(RiskSpecialMerchantBean specialMerchantBean)
			throws Exception {
		Map map1 = new LinkedHashMap();
		map1.put("Version", "V1.0.1");
		String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
				.format(new java.util.Date());
		String substring = date.substring(0, 8);
		// 获取10位随机数
		String randomString = String.valueOf(Math.random()).substring(2, 12);
		map1.put("Identification", date.substring(0, 8) + randomString);// 报文唯一标识（8位日期+10顺序号））
		map1.put("OrigSender", specialMerchantBean.getOrigSender());// 会员单位机构号（字母、数字、下划线）"Z2015611000018"
		map1.put("OrigSenderSID", "HrtApp2014");// 会员单位发送系统号（字母、数字、下划线）
		map1.put("RecSystemId", "SECB01");// 协会系统编号
		map1.put("TrnxCode", specialMerchantBean.getTrnxCode());// 个人商户信息上报请求EPR001,企业商户信息上报请求EER001
		map1.put("TrnxTime", date);
		map1.put("UserToken", specialMerchantBean.getUserToken());
		map1.put("SecretKey", "0");// 密钥
		Map map2 = new LinkedHashMap();
		map2.put("CusType", specialMerchantBean.getCusType());// 2 Y
																// 商户类型，参见商户类型，默认01:自然人
		map2.put("CusNature", specialMerchantBean.getCusNature());// 2 Y
																	// 商户属性，参见数据字典定义（商户属性）
		map2.put("RegName", specialMerchantBean.getRegName());// 64 Y 商户名称
		map2.put("CusName", specialMerchantBean.getCusName());// 64 N 商户简称
		map2.put("CusNameEn", specialMerchantBean.getCusNameEn());// 64 N 商户英文名称
		map2.put("DocType", specialMerchantBean.getDocType());// 2 Y
																// 证件类型,参照数据字典（人员证件类型）
		map2.put("DocCode", specialMerchantBean.getDocCode());// 64 Y 证件号码

		map2.put("LegDocName", specialMerchantBean.getDocName());// 法定代表人姓名 y
		map2.put("LegDocType ", specialMerchantBean.getDocType());// 法定代表人证件类型 y
		map2.put("LegDocCode ", specialMerchantBean.getDocCode());// 法定代表人（负责人）证件号码
																	// y

		map2.put("CusCode", specialMerchantBean.getCusCode());// 32 Y
																// 商户代码，最长不能超过32个字符
		map2.put("InduType", specialMerchantBean.getInduType());// 256 Y
																// 商户行业类别，可多选,多个已英文逗号分割
		map2.put("BankNo", specialMerchantBean.getBankNo());// 32 Y 收款账\卡号
		map2.put("OpenBank", specialMerchantBean.getOpenBank());// 64 Y
																// 收款账\卡号开户行
		map2.put("RegAddrProv", specialMerchantBean.getRegAddrProv());// 6 Y
																		// 商户注册地址(省)，参照数据字段（地区）
		map2.put("RegAddrDetail", specialMerchantBean.getRegAddrDetail());// 128
																			// Y
																			// 商户注册地址
		map2.put("AddrProv", specialMerchantBean.getAddrProv());// 6 Y 商户经营地址(省)
																// ，参照数据字段（地区）
		map2.put("AddrDetail", specialMerchantBean.getAddrDetail());// 128 Y
																	// 商户经营地址
		map2.put("Url", specialMerchantBean.getUrl());// 512 N 网址
		map2.put("ServerIp", specialMerchantBean.getServerIp());// 15 N
																// 服务器IP（应为xxx.xxx.xxx.xxx，xxx可为3位数字，也可为一位数字）
		map2.put("Icp", specialMerchantBean.getIcp());// 20 N ICP备案编号
		map2.put("ContName", specialMerchantBean.getContName());// 64 Y 商户联系人
		map2.put("ContPhone", specialMerchantBean.getContPhone());// 32 Y 商户联系电话
		map2.put("CusEmail", specialMerchantBean.getCusEmail());// 64 N 商户E-mail
		map2.put("Occurarea", getCity(specialMerchantBean.getOccurarea()));// Y
																			// 商户经营地区范围，省级，可多选
		map2.put("NetworkType", specialMerchantBean.getNetworkType());// 32 Y
																		// 清算网络，参见数据字典定义,
																		// 可多选,多个已英文逗号分割
		map2.put("Status", specialMerchantBean.getStatus());// 2 Y 商户状态，参见数据字典定义
		map2.put("StartTime", specialMerchantBean.getStartTime());// 10 Y
																	// 服务起始时间，时间格式，YYYY-MM-DD,例如2016-06-07
		map2.put("EndTime", specialMerchantBean.getEndTime());// 10 Y
															// 服务终止时间，时间格式，YYYY-MM-DD,例如2016-06-07
		map2.put("RiskStatus", specialMerchantBean.getRishStatus());// 2 Y
																	// 合规风险状况，参见数据字典定义

		map2.put("ShareHolder", specialMerchantBean.getShareHolder());// 股东信息 N

		map2.put("OpenType", specialMerchantBean.getOpenType());// 32 Y
																// 开通业务种类，参见数据字典定义,多个已英文逗号分割
		map2.put("ChageType", specialMerchantBean.getChageType());// 2 Y
																	// 计费类型，参见数据字典定义
		map2.put("AccountType", specialMerchantBean.getExpandType());// 2 Y
																		// 支持账户类型，参见数据字典定义
		map2.put("ExpandType", specialMerchantBean.getExpandType());// 2 Y
																	// 拓展方式，参见数据字典定义
		map2.put("OutServiceName", specialMerchantBean.getOutServiceName());// 128
																			// Y
																			// 外包服务机构名称
		map2.put("OutServiceCardType",
				specialMerchantBean.getOutServiceCardType());// 2 Y 外包服务机构法人证件类型
		map2.put("OutServiceCardCode",
				specialMerchantBean.getOutServiceCardCode());// 64 Y
																// 外包服务机构法人证件号码
		map2.put("OutServiceLegCardType",
				specialMerchantBean.getOutServiceLegCardType());// 2 Y
																// 外包服务机构法定代表人证件类型，，参见数据字典定义
		map2.put("OutServiceLegCardCode",
				specialMerchantBean.getOutServiceLegCardCode());// 64 Y
																// 外包服务机构法定代表人证件号码
		map2.put("OrgId", specialMerchantBean.getOrgId());// 32 Y
															// 上报机构（数字、下划线、字母）
		String RepDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date());
		map2.put("RepDate", RepDate);// 20 date Y 上报日期 格式：yyyy-MM-DD HH:mm:ss
		map2.put("RepType", "03");// 2 Y 上传方式（值：03），参见数据字典定义
		map2.put("RepPerson", specialMerchantBean.getRepPerson());// 32 Y 上传人

		String xmlhead = PaymentRiskUtil.xmlByMap(map1);
		xmlhead = "<Head>" + xmlhead + "</Head>";
		String getxml = PaymentRiskUtil.xmlByMap(map2);
		String xmlbody = "<Body><PcacList><Count>1</Count><BaseInfo>" + getxml
				+ "</BaseInfo></PcacList></Body>";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF8\"?><Document><Request>"
				+ xmlhead
				+ xmlbody
				+ "</Request><Signature></Signature></Document>";

		String xsdPath =ParamPropUtils.props.getProperty("RiskXSD")+"pcac.ries.025.xsd";
		// String xsdPath = "C:/pcac.ries.025.xsd";
		// 校验方法
		boolean checkXDS = false;
		try {
			checkXDS = PaymentRiskUtil.checkXDS(xml, xsdPath);
		} catch (SAXException e1) {
			Map resultMsg = new HashMap();
			resultMsg.put("error", "录入数据校验未通过！");

			e1.printStackTrace();
			return resultMsg;
		}
		// 商户名称、商户简称、商户英文名称、证件号码、商户代码、收款账\卡号、商户注册地址、商户经营地址、
		// 网址、服务器IP、ICP备案编号、商户联系人、商户联系电话、外包服务机构名称、外包服务机构法人证件号码
		// 、外包服务机构法定代表人证件号码
		// 加密
		/*
		 * 商户名称、商户简称、商户英文名称、法人证件号码、法定代表人姓名、法定代表人证件号码、商户代码
		 * 、收款账\卡号、商户注册地址、商户经营地址、网址、服务器IP、ICP备案编号、商户联系人、
		 * 商户联系电话、股东信息、外包服务机构名称、外包服务机构法人证件号码、 外包服务机构法定代表人证件号码
		 */Map getMM = new HashMap();
		if (!specialMerchantBean.getRegName().isEmpty()) {
			getMM.put("RegName", specialMerchantBean.getRegName());
		}
		if (!specialMerchantBean.getCusName().isEmpty()) {
			getMM.put("CusName", specialMerchantBean.getCusName());
		}
		if (!specialMerchantBean.getCusNameEn().isEmpty()) {
			getMM.put("CusNameEn", specialMerchantBean.getCusNameEn());
		}
		if (!specialMerchantBean.getDocCode().isEmpty()) {
			getMM.put("DocCode", specialMerchantBean.getDocCode());
		}
		if (!specialMerchantBean.getDocName().isEmpty()) {
			getMM.put("DocName", specialMerchantBean.getDocName());
		}
		if (!specialMerchantBean.getLegDocCode().isEmpty()) {
			getMM.put("LegDocCode", specialMerchantBean.getLegDocCode());
		}
		if (!specialMerchantBean.getCusCode().isEmpty()) {
			getMM.put("CusCode", specialMerchantBean.getCusCode());
		}
		if (!specialMerchantBean.getBankNo().isEmpty()) {
			getMM.put("BankNo", specialMerchantBean.getBankNo());
		}
		if (!specialMerchantBean.getAddrDetail().isEmpty()) {// ?????????AddrProv
			getMM.put("AddrDetail", specialMerchantBean.getAddrDetail());
		}
		if (!specialMerchantBean.getAddrProv().isEmpty()) {
			getMM.put("AddrProv", specialMerchantBean.getAddrProv());
		}
		if (!specialMerchantBean.getUrl().isEmpty()) {
			getMM.put("Url", specialMerchantBean.getUrl());
		}
		if (!specialMerchantBean.getServerIp().isEmpty()) {
			getMM.put("ServerIp", specialMerchantBean.getServerIp());
		}
		if (!specialMerchantBean.getIcp().isEmpty()) {
			getMM.put("Icp", specialMerchantBean.getIcp());
		}
		// * 商户联系电话、股东信息、外包服务机构名称、外包服务机构法人证件号码、
		// * 外包服务机构法定代表人证件号码
		if (!specialMerchantBean.getContName().isEmpty()) {
			getMM.put("ContName", specialMerchantBean.getContName());
		}
		if (!specialMerchantBean.getContPhone().isEmpty()) {
			getMM.put("ContPhone", specialMerchantBean.getContPhone());
		}
		if (!specialMerchantBean.getShareHolder().isEmpty()) {
			getMM.put("ShareHolder", specialMerchantBean.getShareHolder());
		}
		if (!specialMerchantBean.getOutServiceName().isEmpty()) {
			getMM.put("OutServiceName", specialMerchantBean.getOutServiceName());
		}
		if (!specialMerchantBean.getOutServiceCardCode().isEmpty()) {
			getMM.put("OutServiceCardCode",
					specialMerchantBean.getOutServiceCardCode());
		}
		if (!specialMerchantBean.getOutServiceLegCardCode().isEmpty()) {
			getMM.put("OutServiceLegCardCode",
					specialMerchantBean.getOutServiceLegCardCode());
		}
		Map resultMap = null;
		if (getMM.size() > 0) {
			String RiskPKC = ParamPropUtils.props.getProperty("RiskPKCertificate");
			resultMap = PaymentRiskUtil.getAEStoString(getMM, RiskPKC);// E:\\HrtApp\\src\\data\\会员单位.pfx
			map1.put("SecretKey", resultMap.get("encryptedKey"));
		}

		// 拼装加密后xml
		xmlhead = PaymentRiskUtil.xmlByMap(map1);

		xmlhead = "<Head>" + xmlhead + "</Head>";
		if (!specialMerchantBean.getRegName().isEmpty()) {
			map2.put("RegName", resultMap.get("RegName"));
		}
		if (!specialMerchantBean.getCusName().isEmpty()) {
			map2.put("CusName", resultMap.get("CusName"));
		}
		if (!specialMerchantBean.getCusNameEn().isEmpty()) {
			map2.put("CusNameEn", resultMap.get("CusNameEn"));
		}
		if (!specialMerchantBean.getDocCode().isEmpty()) {
			map2.put("DocCode", resultMap.get("DocCode"));
		}
		if (!specialMerchantBean.getDocName().isEmpty()) {
			map2.put("DocName", resultMap.get("DocName"));
		}
		if (!specialMerchantBean.getLegDocCode().isEmpty()) {
			map2.put("LegDocCode", resultMap.get("LegDocCode"));
		}
		if (!specialMerchantBean.getCusCode().isEmpty()) {
			map2.put("CusCode", resultMap.get("CusCode"));
		}
		if (!specialMerchantBean.getBankNo().isEmpty()) {
			map2.put("BankNo", resultMap.get("BankNo"));
		}
		if (!specialMerchantBean.getAddrDetail().isEmpty()) {
			map2.put("AddrDetail", resultMap.get("AddrDetail"));
		}
		if (!specialMerchantBean.getAddrProv().isEmpty()) {
			map2.put("AddrProv", resultMap.get("AddrProv"));
		}
		if (!specialMerchantBean.getUrl().isEmpty()) {
			map2.put("Url", resultMap.get("Url"));
		}
		if (!specialMerchantBean.getServerIp().isEmpty()) {
			map2.put("ServerIp", resultMap.get("ServerIp"));
		}
		if (!specialMerchantBean.getIcp().isEmpty()) {
			map2.put("Icp", resultMap.get("Icp"));
		}
		if (!specialMerchantBean.getContName().isEmpty()) {
			map2.put("ContName", resultMap.get("ContName"));
		}
		if (!specialMerchantBean.getShareHolder().isEmpty()) {
			map2.put("ShareHolder", resultMap.get("ShareHolder"));
		}
		if (!specialMerchantBean.getContPhone().isEmpty()) {
			map2.put("ContPhone", resultMap.get("ContPhone"));
		}
		if (!specialMerchantBean.getOutServiceName().isEmpty()) {
			map2.put("OutServiceName", resultMap.get("OutServiceName"));
		}
		if (!specialMerchantBean.getOutServiceCardCode().isEmpty()) {
			map2.put("OutServiceCardCode", resultMap.get("OutServiceCardCode"));
		}
		if (!specialMerchantBean.getOutServiceLegCardCode().isEmpty()) {
			map2.put("OutServiceLegCardCode",
					resultMap.get("OutServiceLegCardCode"));
		}
		getxml = PaymentRiskUtil.xmlByMap(map2);
		// xmlbody = "<Body>" + getxml + "</Body>";
		xmlbody = "<Body><PcacList><Count>1</Count><RiskInfo>" + getxml
				+ "</RiskInfo></PcacList></Body>";
		// 加密后xml
		xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
				+ xmlhead + xmlbody
				+ "</Request><Signature></Signature></Document>";

		// 加签
		try {
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
					+ xmlhead + xmlbody + "</Request></Document>";
			String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
			String signPWD = ParamPropUtils.props.getProperty("signPWD");
			String addQian = PaymentRiskUtil.AddQian(xml, pfx, signPWD);
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
					+ xmlhead
					+ xmlbody
					+ "</Request><Signature>"
					+ addQian
					+ "</Signature></Document>";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 发送请求

		String url = ParamPropUtils.props.getProperty("RiskElseUrl");
		String Result = PaymentRiskUtil.doPost(url, xml);
		Map readMap = new HashMap();
		Map requestXml = PaymentRiskUtil.requestXml(Result, readMap);
		return requestXml;
	}

	/**
	 * 查找响应码字典
	 * */
	public String queryMerchantResult(String ResultCode) {
		Map param = new HashMap();
		param.put("code", ResultCode);
		String sql = "select value from risk_result where code = :code";
		List<Map<String, Object>> list = specialMerchantDao.executeSql2(sql,
				param);
		String value = (String) list.get(0).get("VALUE");
		return value;
	}
	 

	public DataGridBean queryPushMsg(RiskSpecialMerchantBean specialMerchantBean){
		Map<String, Object> param = new HashMap<String, Object>();
		String sql = " select id,identification,pushdate,regname,cusname,doctype,doccode,docname,legdoctype,legdoccode,risklevel,risktype,validdate,validstatus,flag" +
				" from risk_push_msg where 1=1 ";
		
		if (null != specialMerchantBean.getCusName()&& !"".equals(specialMerchantBean.getCusName())) {
			param.put("cusname", specialMerchantBean.getCusName().trim());
			sql += " and cusname = :cusname ";
		}
		if (null != specialMerchantBean.getRegName()&& !"".equals(specialMerchantBean.getRegName())) {
			param.put("regname", specialMerchantBean.getRegName().trim());
			sql += " and regname = :regname ";
		}
		if (null != specialMerchantBean.getFlag()&& !"".equals(specialMerchantBean.getFlag())) {
			param.put("flag", specialMerchantBean.getFlag().trim());
			sql += " and flag = :flag ";
		}
		if (null != specialMerchantBean.getREPDATE()&& !"".equals(specialMerchantBean.getREPDATE())
				&& null != specialMerchantBean.getREPDATE1()&& !"".equals(specialMerchantBean.getREPDATE1())) {
			param.put("repdate0",
					specialMerchantBean.getREPDATE().replaceAll("-", ""));
			param.put("repdate1",
					specialMerchantBean.getREPDATE1().replaceAll("-", ""));
			sql += " and  replace(pushdate,'-','') between :repdate0 and :repdate1  ";
		}

		sql += " order by pushdate ";
		String count = "select count(*) from (" + sql + ") t ";
		BigDecimal counts;
		List<Map<String, String>> list;
		list = specialMerchantDao.queryObjectsBySqlList(sql, param,
				specialMerchantBean.getPage(), specialMerchantBean.getRows());

		counts = specialMerchantDao.querysqlCounts(count, param);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());// 查询数据库总条数
		dgd.setRows(list);
		return dgd;
	}
	
	/**
	 * 添加推送信息
	 * @throws Exception 
	 * */
	public void addPushMsgList(Map requestXml) throws Exception{
		
		String Identification = String.valueOf(requestXml.get("Identification"));
		String Pushdate = String.valueOf(requestXml.get("UpDate"));
		String Flag = String.valueOf(requestXml.get("Flag"));
		//解密
		String SecretKey = (String) requestXml.get("SecretKey");
		Map decrypt = null;
		List<Map<String,String>> RiskInfo = (List) requestXml.get("resultlist");
		for (int i = 0; i < RiskInfo.size(); i++) {
			Map JieMiMap = new HashMap();
			if(RiskInfo.get(i).get("RegName")!=null){
				JieMiMap.put("RegName", String.valueOf(RiskInfo.get(i).get("RegName")));
			}
			if(RiskInfo.get(i).get("CusName")!=null){
				JieMiMap.put("CusName", String.valueOf(RiskInfo.get(i).get("CusName")));
			}
			if(RiskInfo.get(i).get("DocCode")!=null){
				JieMiMap.put("DocCode", String.valueOf(RiskInfo.get(i).get("DocCode")));
			}
			if(RiskInfo.get(i).get("LegDocName")!=null){
				JieMiMap.put("LegDocName", String.valueOf(RiskInfo.get(i).get("LegDocName")));
			}
			if(RiskInfo.get(i).get("DocName")!=null){
				JieMiMap.put("DocName", String.valueOf(RiskInfo.get(i).get("DocName")));
			}
			if(RiskInfo.get(i).get("LegDocCode")!=null){
				JieMiMap.put("LegDocCode", String.valueOf(RiskInfo.get(i).get("LegDocCode")));
			}
			String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
			String DecryptPWD = ParamPropUtils.props.getProperty("DecryptPWD");
			decrypt = PaymentRiskUtil.decrypt(SecretKey, JieMiMap,pfx,DecryptPWD);
			if(decrypt!=null){
				RiskInfo.get(i).putAll(decrypt);
			}
			
			RiskPushMsgModel model = new RiskPushMsgModel();
			Map<String, String> map = RiskInfo.get(i);
			model.setIdentification(Identification);
			model.setPushdate(Pushdate);      
			model.setRegname(map.get("RegName"));
			model.setCusname(map.get("CusName"));
			model.setDoctype(map.get("DocType"));       
			model.setDoccode(map.get("DocCode")); 
			if(Flag.equals("1")){
				model.setDocname(map.get("DocName"));//黑名单
			}else{
				model.setDocname(map.get("LegDocName"));//风险信息
			}
			model.setLegdoctype(map.get("LegDocType"));
			model.setLegdoccode(map.get("LegDocCode"));
			model.setRisklevel(map.get("Level"));
			model.setRisktype(map.get("RiskType")); 
			model.setValiddate(map.get("ValidDate"));
			model.setValidstatus(map.get("ValidStatus"));
			model.setFlag("1"); //黑名单    
			   
			riskPushMsgDao.saveObject(model);
		}
	}
	
	public String repPushMsgList(String RecSystemId,boolean flag) throws Exception{
		Map map1 = new LinkedHashMap();
		map1.put("Version", "V1.0.1");
		String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
		.format(new java.util.Date());
		String substring = date.substring(0, 8);
		//获取10位随机数
		String randomString = String.valueOf(Math.random()).substring(2, 12);
		map1.put("Identification", date.substring(0, 8)+randomString);//报文唯一标识（8位日期+10顺序号））		
		map1.put("OrigSender", "Z2015611000018");// 会员单位机构号（字母、数字、下划线）
		map1.put("OrigSenderSID", "HrtApp2014");// 会员单位发送系统号（字母、数字、下划线）
		map1.put("RecSystemId", RecSystemId);
		map1.put("TrnxCode", "-");// 
		map1.put("TrnxTime", date);
		map1.put("SecretKey", "");//密钥
		Map map2 = new LinkedHashMap();
		if(flag==true){
			map2.put("ResultStatus", "01");//
			map2.put("ResultCode", "S00000");
		}else{
			map2.put("ResultStatus", "02");//
			map2.put("ResultCode", "S99999");
		}
		
		String xmlhead = PaymentRiskUtil.xmlByMap(map1);
		xmlhead = "<Head>" + xmlhead + "</Head>";
		String getxml = PaymentRiskUtil.xmlByMap(map2);
		String xmlbody = "<Body><RespInfo>"+getxml+"</RespInfo></Body>";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Respone>"
				+ xmlhead
				+ xmlbody
				+ "</Respone><Signature></Signature></Document>";
		String xsdPath =ParamPropUtils.props.getProperty("RiskXSD")+"pcac.ries.002.xsd";
		// 校验方法
		boolean checkXDS = false;
		try {
			checkXDS = PaymentRiskUtil.checkXDS(xml,xsdPath);
		} catch (SAXException e1) {
			System.out.println("校验失败"+e1);
			
		}
		
		// 加签
		try {
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Respone>"
					+ xmlhead + xmlbody + "</Respone></Document>";
			String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
			String signPWD = ParamPropUtils.props.getProperty("signPWD");
			String addQian = PaymentRiskUtil.AddQian(xml,pfx,signPWD);
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Respone>"
					+ xmlhead
					+ xmlbody
					+ "</Respone><Signature>"
					+ addQian
					+ "</Signature></Document>";
		} catch (Exception e) {
			System.out.println("加签失败"+e);
			e.printStackTrace();
		}
		return xml;
	}
	
	public void addPendingMerList(Map requestXml) throws Exception{
		String Identification = String.valueOf(requestXml.get("Identification"));
		String Pushdate = String.valueOf(requestXml.get("UpDate"));
		//解密
		String SecretKey = (String) requestXml.get("SecretKey");
		Map decrypt = null;
		List<Map<String,String>> RiskInfo = (List) requestXml.get("resultlist");
		for (int i = 0; i < RiskInfo.size(); i++) {
			Map JieMiMap = new HashMap();
			if(RiskInfo.get(i).get("CusCode")!=null){
				JieMiMap.put("CusCode", String.valueOf(RiskInfo.get(i).get("CusCode")));
			}
			if(RiskInfo.get(i).get("RegName")!=null){
				JieMiMap.put("RegName", String.valueOf(RiskInfo.get(i).get("RegName")));
			}
			if(RiskInfo.get(i).get("LegDocName")!=null){
				JieMiMap.put("LegDocName", String.valueOf(RiskInfo.get(i).get("LegDocName")));
			}
			
			String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
			String DecryptPWD = ParamPropUtils.props.getProperty("DecryptPWD");
			decrypt = PaymentRiskUtil.decrypt(SecretKey, JieMiMap,pfx,DecryptPWD);
			if(decrypt!=null){
				RiskInfo.get(i).putAll(decrypt);
			}
			
			RiskPendingMerchantModel model = new RiskPendingMerchantModel();
			Map<String, String> map = RiskInfo.get(i);
			model.setCode(map.get("CusCode"));
			model.setName(map.get("RegName"));
			model.setLegalname(map.get("LegDocName"));
			model.setFlag("0"); //未处理
			model.setIndate(Pushdate);  
			riskPendingMerchantDao.saveObject(model);
		}
		
	}
	
	
	public DataGridBean queryPushMsgList(RiskSpecialMerchantBean specialMerchantBean){
		Map<String, Object> param = new HashMap<String, Object>();
		String sql = "select id,code,name,legalname,flag,indate from RISK_PENDING_MERCHANT where 1=1 ";
		
		if (null != specialMerchantBean.getCode()&& !"".equals(specialMerchantBean.getCode())) {
			param.put("code", specialMerchantBean.getCode().trim());
			sql += " and code = :code ";
		}
		if (null != specialMerchantBean.getName()&& !"".equals(specialMerchantBean.getName())) {
			param.put("name", specialMerchantBean.getName().trim());
			sql += " and name = :name ";
		}
		if (null != specialMerchantBean.getLegalname()&& !"".equals(specialMerchantBean.getLegalname())) {
			param.put("legalname", specialMerchantBean.getLegalname().trim());
			sql += " and legalname = :legalname ";
		}
		if (null != specialMerchantBean.getREPDATE()&& !"".equals(specialMerchantBean.getREPDATE())
				&& null != specialMerchantBean.getREPDATE1()&& !"".equals(specialMerchantBean.getREPDATE1())) {
			param.put("repdate0",
					specialMerchantBean.getREPDATE().replaceAll("-", ""));
			param.put("repdate1",
					specialMerchantBean.getREPDATE1().replaceAll("-", ""));
			sql += " and  replace(indate,'-','') between :repdate0 and :repdate1  ";
		}

		sql += " order by indate ";
		String count = "select count(*) from (" + sql + ") t ";
		BigDecimal counts;
		List<Map<String, String>> list;
		list = specialMerchantDao.queryObjectsBySqlList(sql, param,
				specialMerchantBean.getPage(), specialMerchantBean.getRows());

		counts = specialMerchantDao.querysqlCounts(count, param);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());// 查询数据库总条数
		dgd.setRows(list);
		return dgd;
	}
}
