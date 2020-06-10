package com.hrt.biz.bill.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hrt.util.ParamPropUtils;
import org.springframework.beans.BeanUtils;
import org.xml.sax.SAXException;

import com.hrt.biz.bill.dao.IDictionaryDao;
import com.hrt.biz.bill.dao.IPaymentRiskDao;
import com.hrt.biz.bill.entity.model.DictionaryModel;
import com.hrt.biz.bill.entity.model.RiskSharingModel;
import com.hrt.biz.bill.entity.pagebean.PaymentRiskBean;
import com.hrt.biz.bill.service.IPaymentRiskService;
import com.hrt.biz.util.PaymentRiskUtil;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.TreeNodeBean;

public class PaymentRiskServiceImpl implements IPaymentRiskService {
	
	private IPaymentRiskDao paymentRiskDao;

	public IPaymentRiskDao getPaymentRiskDao() {
		return paymentRiskDao;
	}
	public void setPaymentRiskDao(IPaymentRiskDao paymentRiskDao) {
		this.paymentRiskDao = paymentRiskDao;
	}

	private IDictionaryDao dictionaryDao;
	
	public IDictionaryDao getDictionaryDao() {
		return dictionaryDao;
	}
	public void setDictionaryDao(IDictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}
/***********************************************************************************************/
	public Map login(PaymentRiskBean paymentRiskBean,String realPath) throws Exception{
		Map map = new LinkedHashMap();
		map.put("Version", "V1.0.1");
		String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
		.format(new java.util.Date());
		String substring = date.substring(0, 8);
		//获取10位随机数
		String randomString = String.valueOf(Math.random()).substring(2, 12);
		map.put("Identification", date.substring(0, 8)+randomString);//报文唯一标识（8位日期+10顺序号）
		map.put("OrigSender", paymentRiskBean.getOrigSender());// 会员单位机构号（字母、数字、下划线）
		map.put("OrigSenderSID", "HrtApp2014");// 会员单位发送系统号（字母、数字、下划线）
		map.put("RecSystemId", "R0001");// 协会系统编号
		map.put("TrnxCode", "LR0001");// LR0001登录 LR0002登出
		map.put("TrnxTime", date);//交易时间格式：yyyyMMDDHHmmss（数字）
		// map.put("UserToKen", "");
		map.put("SecretKey", "1");//密钥
		String getHead = PaymentRiskUtil.xmlByMap(map);
		String jiaoyan = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request><Head>"
				+ getHead
				+ "</Head></Request><Signature></Signature></Document>";
		String xsdPath =ParamPropUtils.props.getProperty("RiskXSD")+"pcac.ries.022.xsd";// "C:/pcac.ries.022.xsd";
		//校验
		boolean checkXDS = PaymentRiskUtil.checkXDS(jiaoyan,xsdPath);
		String addQian = null;
		String Result = null;
		if(checkXDS){
			String qian = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request><Head>"
					+ getHead + "</Head></Request></Document>";
			try {
				String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
				String signPWD = ParamPropUtils.props.getProperty("signPWD");
				addQian = PaymentRiskUtil.AddQian(qian,pfx,signPWD);
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request><Head>"
					+ getHead
					+ "</Head></Request><Signature>"
					+ addQian
					+ "</Signature></Document>";
			//Result = PaymentRiskUtil.doPost("http://114.247.60.83:8080/ries_interface/loginServlet", xml);

			String url = ParamPropUtils.props.getProperty("RiskLoginOrOUTUrl");
			Result = PaymentRiskUtil.doPost(url,xml);
			
			/*	String uri = "http://114.247.60.83:8080/ries_interface/loginServlet";
			Result = PaymentRiskUtil.sendGet(uri,"xml="+xml);*/
		}else{
			//校验失败 
		}
		Map readMap = new HashMap();
		Map requestXml = PaymentRiskUtil.requestXml(Result, readMap);
		return requestXml;
	}
	
	/**
	 * 登出*/
	public Map logout(PaymentRiskBean paymentRiskBean) throws Exception{
		Map map = new LinkedHashMap();
		map.put("Version", "V1.0.1");
		String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
		.format(new java.util.Date());
		String substring = date.substring(0, 8);
		//获取10位随机数
		String randomString = String.valueOf(Math.random()).substring(2, 12);
		map.put("Identification", date.substring(0, 8)+randomString);//报文唯一标识（8位日期+10顺序号）
		map.put("OrigSender", "Z2015611000018");// 会员单位机构号（字母、数字、下划线）
		map.put("OrigSenderSID", "HrtApp2014");// 会员单位发送系统号（字母、数字、下划线）
		map.put("RecSystemId", "R0001");// 协会系统编号
		map.put("TrnxCode", "LR0002");// LR0001登录 LR0002登出LR0002
		map.put("TrnxTime", date);//交易时间格式：yyyyMMDDHHmmss（数字）
		// map.put("UserToKen", "");
		map.put("SecretKey", "1");//密钥
		String getHead = PaymentRiskUtil.xmlByMap(map);
		String jiaoyan = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request><Head>"
				+ getHead
				+ "</Head></Request><Signature></Signature></Document>";
		String xsdPath =ParamPropUtils.props.getProperty("RiskXSD")+"pcac.ries.022.xsd";
		//校验
		boolean checkXDS = PaymentRiskUtil.checkXDS(jiaoyan,xsdPath);
		String addQian = null;
		String Result = null;
		if(checkXDS){
			String qian = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request><Head>"
					+ getHead + "</Head></Request></Document>";
			try {
				String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
				String signPWD = ParamPropUtils.props.getProperty("signPWD");
				addQian = PaymentRiskUtil.AddQian(qian,pfx,signPWD);
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request><Head>"
					+ getHead
					+ "</Head></Request><Signature>"
					+ addQian
					+ "</Signature></Document>";
			String url = ParamPropUtils.props.getProperty("RiskLoginOrOUTUrl");
			Result = PaymentRiskUtil.doPost(url,xml);
			//Result = PaymentRiskUtil.doPost("http://114.247.60.83:8080/ries_interface/loginServlet",xml);
		}else{
			//校验失败 
		}
		Map readMap = new HashMap();
		Map requestXml = PaymentRiskUtil.requestXml(Result, readMap);
		return requestXml;
	}
	
	public DataGridBean queryBeenPersonal(PaymentRiskBean paymentRiskBean) throws Exception {
			
		Map param=new HashMap();
		param.put("trnxcode", "PR0001");
		String sql = "select id,cusproperty,risktype,mobileno,mac,imei,bankno,openbank,cusname,doccode,ip,address,telephone,recbankno,recopenbank,email,validdate,infolevel,occurtimeb,occurtimee,occurchan,occurarea,note,orgid,repdate,reptype,repperson from RISK_SHARING where trnxcode=:trnxcode ";
		if (null != paymentRiskBean.getRiskType()&& !"".equals(paymentRiskBean.getRiskType())) {
			param.put("RiskType", paymentRiskBean.getRiskType().trim());
			sql += " and RiskType = :RiskType ";
		}
		if (null != paymentRiskBean.getCusName()&& !"".equals(paymentRiskBean.getCusName())) {
			param.put("CusName", paymentRiskBean.getCusName().trim());
			sql += " and CusName = :CusName ";
		}
		if (null != paymentRiskBean.getMobileNo()&& !"".equals(paymentRiskBean.getMobileNo())) {
			param.put("MobileNo", paymentRiskBean.getMobileNo().trim());
			sql += " and MobileNo = :MobileNo ";
		}
		if (null != paymentRiskBean.getDocCode()&& !"".equals(paymentRiskBean.getDocCode())) {
			param.put("DocCode", paymentRiskBean.getDocCode().trim());
			sql += " and DocCode = :DocCode ";
		}
		if (null != paymentRiskBean.getOccurarea()&& !"".equals(paymentRiskBean.getOccurarea())) {
			param.put("Occurarea", "%"+paymentRiskBean.getOccurarea().replace(" ", "")+"%");
			sql += " and Occurarea like :Occurarea ";
		}
		if (null != paymentRiskBean.getBankNo()&& !"".equals(paymentRiskBean.getBankNo())) {
			param.put("BankNo", paymentRiskBean.getBankNo().trim());
			sql += " and BankNo = :BankNo ";
		}
		if (null != paymentRiskBean.getOpenBank()&& !"".equals(paymentRiskBean.getOpenBank())) {
			param.put("OpenBank", paymentRiskBean.getOpenBank().trim());
			sql += " and OpenBank = :OpenBank ";
		}
		if (null != paymentRiskBean.getAddress()&& !"".equals(paymentRiskBean.getAddress())) {
			param.put("Address", paymentRiskBean.getAddress().trim());
			sql += " and Address = :Address ";
		}
		if (null != paymentRiskBean.getTelephone()&& !"".equals(paymentRiskBean.getTelephone())) {
			param.put("Telephone", paymentRiskBean.getTelephone().trim());
			sql += " and Telephone = :Telephone ";
		}
		if (null != paymentRiskBean.getIp()&& !"".equals(paymentRiskBean.getIp())) {
			param.put("Ip", paymentRiskBean.getIp().trim());
			sql += " and Ip = :Ip ";
		}
		if (null != paymentRiskBean.getMac()&& !"".equals(paymentRiskBean.getMac())) {
			param.put("Mac", paymentRiskBean.getMac().trim());
			sql += " and Mac = :Mac ";
		}
		if (null != paymentRiskBean.getRecBankNo()&& !"".equals(paymentRiskBean.getRecBankNo())) {
			param.put("RecBankNo", paymentRiskBean.getRecBankNo().trim());
			sql += " and RecBankNo = :RecBankNo ";
		}
		if (null != paymentRiskBean.getRecOpenBank()&& !"".equals(paymentRiskBean.getRecOpenBank())) {
			param.put("RecOpenBank", paymentRiskBean.getRecOpenBank().trim());
			sql += " and RecOpenBank = :RecOpenBank ";
		}
		if (null != paymentRiskBean.getOccurtimeb()&& !"".equals(paymentRiskBean.getOccurtimeb())&&null != paymentRiskBean.getOccurtimee()&& !"".equals(paymentRiskBean.getOccurtimee())) {
			param.put("Occurtimeb", paymentRiskBean.getOccurtimeb().replaceAll("-", ""));
			param.put("Occurtimee", paymentRiskBean.getOccurtimee().replaceAll("-", ""));
			String replaceAll = paymentRiskBean.getOccurtimee().replaceAll("-", "");
			String replaceAll2 = paymentRiskBean.getOccurtimeb().replaceAll("-", "");
			sql += " and Occurtimeb between :Occurtimeb and :Occurtimee and Occurtimee between :Occurtimeb and :Occurtimee";
		}
		
		sql += " order by repdate ";
		String count = "select count(*) from (" + sql + ") t ";
		BigDecimal counts;
		List<Map<String, String>> list;
		list=paymentRiskDao.queryObjectsBySqlList(sql,param,paymentRiskBean.getPage(), paymentRiskBean.getRows());
		for (int j = 0; j < list.size(); j++) {
			if(null!=list.get(j).get("OCCURAREA")){
				String Occurarea = String.valueOf(list.get(j).get("OCCURAREA"));
				list.get(j).put("OCCURAREA", queryDictionary(Occurarea));
			}
		}
		counts = paymentRiskDao.querysqlCounts(count, param);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());// 查询数据库总条数
		dgd.setRows(list);
		return dgd;
	}
	

	//个人风险信息上报
	public Map reportPersonal(PaymentRiskBean paymentRiskBean) throws Exception{
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
		map1.put("RecSystemId", "R0001");// 协会系统编号
		map1.put("TrnxCode", paymentRiskBean.getTrnxCode());// 个人风险信息上报PR0001 商户风险信息上报ER0001
		map1.put("TrnxTime", date);
		map1.put("UserToken", paymentRiskBean.getUserToken());
		map1.put("SecretKey", "");//密钥
		Map map2 = new LinkedHashMap();
		map2.put("CusProperty", "01");// Y客户属性;01个人 02商户
		map2.put("RiskType", paymentRiskBean.getRiskType());// Y风险类型01：虚假申请（受害人信息）02：虚假申请（嫌疑人信息）
		map2.put("MobileNo", paymentRiskBean.getMobileNo());
		map2.put("Mac", paymentRiskBean.getMac());//PaymentRiskUtil.getMacAddress(paymentRiskBean.getIp()).toUpperCase()
		map2.put("Imei", paymentRiskBean.getImei());//Imei必须为小于或等于32位数字组成
		map2.put("BankNo", paymentRiskBean.getBankNo());// 银行卡账号
		map2.put("OpenBank", paymentRiskBean.getOpenBank()); //开户行
		map2.put("CusName", paymentRiskBean.getCusName());// 客户姓名
		map2.put("DocCode", paymentRiskBean.getDocCode());// 身份证
		map2.put("Ip",  paymentRiskBean.getIp());
		map2.put("Address", paymentRiskBean.getAddress());//收货地址
		map2.put("Telephone", paymentRiskBean.getTelephone());//固话 必须输入三位或四位区号，七位或八位电话号码，区号为三位时电话号码必须为八位），区号与电话号码以-隔开
		map2.put("RecBankNo", paymentRiskBean.getRecBankNo());//收款银行帐/卡号（只能输入数字且不能超过32位字符）
		map2.put("RecOpenBank", paymentRiskBean.getRecOpenBank());//收款账/卡号开户行
		map2.put("Email", paymentRiskBean.getEmail());
		map2.put("ValidDate", paymentRiskBean.getValidDate());// Y有效期  格式：yyyy-MM-DD
		map2.put("Level", paymentRiskBean.getLevel());// 信息级别Y
		map2.put("Occurtimeb", paymentRiskBean.getOccurtimeb());// 风险事件发生时间，时间格式，
		map2.put("Occurtimee",  paymentRiskBean.getOccurtimee());// 风险事件结束时间，时间格式，
		map2.put("Occurchan", paymentRiskBean.getOccurchan());// 发生渠道
		String occurarea = paymentRiskBean.getOccurarea();
		if(null==occurarea){
			occurarea="";
		}else{
			occurarea=occurarea.replace(" ", "");
		}
		map2.put("Occurarea", occurarea);// 风险事件发生地域，省级/地市，可多选
		map2.put("Note", paymentRiskBean.getNote());// 风险事件描述
		map2.put("OrgId", paymentRiskBean.getOrgId());// 上报机构（数字、下划线、字母）Y
		map2.put("RepDate",paymentRiskBean.getRepDate());// 上报日期 格式：yyyy-MM-DD HH:mm:ssY
		map2.put("RepType", "03");// 上传方式（值：03Y
		map2.put("RepPerson", paymentRiskBean.getRepPerson());// 上传人Y
		
		String xmlhead = PaymentRiskUtil.xmlByMap(map1);
		xmlhead = "<Head>" + xmlhead + "</Head>";
		String getxml = PaymentRiskUtil.xmlByMap(map2);
		String xmlbody = "<Body><PcacList><Count>1</Count><RiskInfo>" + getxml
				+ "</RiskInfo></PcacList></Body>";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
				+ xmlhead
				+ xmlbody
				+ "</Request><Signature></Signature></Document>";
		
		String xsdPath =ParamPropUtils.props.getProperty("RiskXSD")+"pcac.ries.001.xsd";
		// 校验方法
		boolean checkXDS = false;
		try {
			checkXDS = PaymentRiskUtil.checkXDS(xml,xsdPath);
		} catch (SAXException e1) {
			Map resultMsg=new HashMap();
			resultMsg.put("error", "录入数据校验未通过！");
			
			e1.printStackTrace();
			return resultMsg;
		}
		// 加密
		Map getMM = new HashMap();
		if(!paymentRiskBean.getMobileNo().isEmpty()){
			getMM.put("MobileNo", paymentRiskBean.getMobileNo());
		}
		if(!paymentRiskBean.getBankNo().isEmpty()){
			getMM.put("BankNo", paymentRiskBean.getBankNo());
		}
		if(!paymentRiskBean.getCusName().isEmpty()){
			getMM.put("CusName", paymentRiskBean.getCusName());
		}
		if(!paymentRiskBean.getDocCode().isEmpty()){
			getMM.put("DocCode", paymentRiskBean.getDocCode());
		}
		if(!paymentRiskBean.getTelephone().isEmpty()){
			getMM.put("Telephone", paymentRiskBean.getTelephone());
		}
		if(!paymentRiskBean.getRecBankNo().isEmpty()){
			getMM.put("RecBankNo", paymentRiskBean.getRecBankNo());
		}
		Map resultMap = null;
		if(getMM.size()>0){
			String RiskPKC = ParamPropUtils.props.getProperty("RiskPKCertificate");
			resultMap = PaymentRiskUtil.getAEStoString(getMM,RiskPKC);//E:\\HrtApp\\src\\data\\会员单位.pfx
			map1.put("SecretKey", resultMap.get("encryptedKey"));
		}

		// 拼装加密后xml
		xmlhead = PaymentRiskUtil.xmlByMap(map1);
		
		xmlhead = "<Head>" + xmlhead + "</Head>";
		if(!paymentRiskBean.getMobileNo().isEmpty()){
			map2.put("MobileNo", resultMap.get("MobileNo"));
		}
		if(!paymentRiskBean.getBankNo().isEmpty()){
			map2.put("BankNo", resultMap.get("BankNo"));
		}
		if(!paymentRiskBean.getCusName().isEmpty()){
			map2.put("CusName", resultMap.get("CusName"));
		}
		if(!paymentRiskBean.getDocCode().isEmpty()){
			map2.put("DocCode", resultMap.get("DocCode"));
		}
		if(!paymentRiskBean.getTelephone().isEmpty()){
			map2.put("Telephone", resultMap.get("Telephone"));
		}
		if(!paymentRiskBean.getRecBankNo().isEmpty()){
			map2.put("RecBankNo", resultMap.get("RecBankNo"));
		}
		getxml = PaymentRiskUtil.xmlByMap(map2);
		//xmlbody = "<Body>" + getxml + "</Body>";
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
					String addQian = PaymentRiskUtil.AddQian(xml,pfx,signPWD);
					xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
							+ xmlhead
							+ xmlbody
							+ "</Request><Signature>"
							+ addQian
							+ "</Signature></Document>";
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//发送请求
				
				String url = ParamPropUtils.props.getProperty("RiskElseUrl");
				String	Result = PaymentRiskUtil.doPost(url,xml);
			Map readMap = new HashMap();
			Map requestXml = PaymentRiskUtil.requestXml(Result, readMap);
			return requestXml;
			
	}
	
	public void savePersonal (PaymentRiskBean paymentRiskBean){
		RiskSharingModel modle=new RiskSharingModel();
		BeanUtils.copyProperties(paymentRiskBean,modle );
		modle.setCusProperty("01");
		modle.setRepType("03");
		paymentRiskDao.saveOrUpdateObject(modle);
	}
	
	public DataGridBean queryBeenMerchant(PaymentRiskBean paymentRiskBean) throws Exception {
		
		Map param=new HashMap();
		param.put("trnxcode", "ER0001");
		String sql = "select CusProperty,RiskType,CusNature,CusName,RegName,CusCode,OrgCode,BusLicCode,SocialUnityCreditCode,TaxRegCer,LegRepName,LegDocCode,BankNo,OpenBank,Url,ServerIp,MobileNo,Address,Icp,infoLevel,Occurtimeb,Occurtimee,Occurchan,Occurarea,Note,ValidDate,OrgId,RepDate,RepType,RepPerson from RISK_SHARING where trnxcode=:trnxcode ";
		if (null != paymentRiskBean.getRiskType()&& !"".equals(paymentRiskBean.getRiskType())) {
			param.put("RiskType", paymentRiskBean.getRiskType().trim());
			sql += " and RiskType = :RiskType ";
		}
		if (null != paymentRiskBean.getCusNature()&& !"".equals(paymentRiskBean.getCusNature())) {
			param.put("CusNature", paymentRiskBean.getCusNature().trim());
			sql += " and CusNature = :CusNature ";
		}
		if (null != paymentRiskBean.getCusName()&& !"".equals(paymentRiskBean.getCusName())) {
			param.put("CusName", paymentRiskBean.getCusName().trim());
			sql += " and CusName = :CusName ";
		}
		if (null != paymentRiskBean.getRegName()&& !"".equals(paymentRiskBean.getRegName())) {
			param.put("RegName", paymentRiskBean.getRegName().trim());
			sql += " and RegName = :RegName ";
		}
		if (null != paymentRiskBean.getCusCode()&& !"".equals(paymentRiskBean.getCusCode())) {
			param.put("CusCode", paymentRiskBean.getCusCode().trim());
			sql += " and CusCode = :CusCode ";
		}
		if (null != paymentRiskBean.getOrgCode()&& !"".equals(paymentRiskBean.getOrgCode())) {
			param.put("OrgCode", paymentRiskBean.getOrgCode().trim());
			sql += " and OrgCode = :OrgCode ";
		}
		if (null != paymentRiskBean.getBusLicCode()&& !"".equals(paymentRiskBean.getBusLicCode())) {
			param.put("BusLicCode", paymentRiskBean.getBusLicCode().trim());
			sql += " and BusLicCode = :BusLicCode ";
		}
		if (null != paymentRiskBean.getSocialUnityCreditCode()&& !"".equals(paymentRiskBean.getSocialUnityCreditCode())) {
			param.put("SocialUnityCreditCode", paymentRiskBean.getSocialUnityCreditCode().trim());
			sql += " and SocialUnityCreditCode = :SocialUnityCreditCode ";
		}
		if (null != paymentRiskBean.getTaxRegCer()&& !"".equals(paymentRiskBean.getTaxRegCer())) {
			param.put("TaxRegCer", paymentRiskBean.getTaxRegCer().trim());
			sql += " and TaxRegCer = :TaxRegCer ";
		}
		if (null != paymentRiskBean.getLegRepName()&& !"".equals(paymentRiskBean.getLegRepName())) {
			param.put("LegRepName", paymentRiskBean.getLegRepName().trim());
			sql += " and LegRepName = :LegRepName ";
		}
		if (null != paymentRiskBean.getLegDocCode()&& !"".equals(paymentRiskBean.getLegDocCode())) {
			param.put("LegDocCode", paymentRiskBean.getLegDocCode().trim());
			sql += " and LegDocCode = :LegDocCode ";
		}
		if (null != paymentRiskBean.getBankNo()&& !"".equals(paymentRiskBean.getBankNo())) {
			param.put("BankNo", paymentRiskBean.getBankNo().trim());
			sql += " and BankNo = :BankNo ";
		}
		if (null != paymentRiskBean.getOpenBank()&& !"".equals(paymentRiskBean.getOpenBank())) {
			param.put("OpenBank", paymentRiskBean.getOpenBank().trim());
			sql += " and OpenBank = :OpenBank ";
		}
		
		if (null != paymentRiskBean.getUrl()&& !"".equals(paymentRiskBean.getUrl())) {
			param.put("Url", paymentRiskBean.getUrl().trim());
			sql += " and Url = :Url ";
		}
		if (null != paymentRiskBean.getLevel()&& !"".equals(paymentRiskBean.getLevel())) {
			param.put("Level", paymentRiskBean.getLevel().trim());
			sql += " and infoLevel = :Level ";
		}
		if (null != paymentRiskBean.getMobileNo()&& !"".equals(paymentRiskBean.getMobileNo())) {
			param.put("MobileNo", paymentRiskBean.getMobileNo().trim());
			sql += " and MobileNo = :MobileNo ";
		}
		
		if (null != paymentRiskBean.getAddress()&& !"".equals(paymentRiskBean.getAddress())) {
			param.put("Address", paymentRiskBean.getAddress().trim());
			sql += " and Address = :Address ";
		}
		if (null != paymentRiskBean.getIcp()&& !"".equals(paymentRiskBean.getIcp())) {
			param.put("Icp", paymentRiskBean.getIcp().trim());
			sql += " and Icp = :Icp ";
		}
		if (null != paymentRiskBean.getOccurchan()&& !"".equals(paymentRiskBean.getOccurchan())) {
			param.put("Occurchan", paymentRiskBean.getOccurchan().trim());
			sql += " and Occurchan = :Occurchan ";
		}
		
		if (null != paymentRiskBean.getOccurarea()&& !"".equals(paymentRiskBean.getOccurarea())) {
			param.put("Occurarea", paymentRiskBean.getOccurarea().trim());
			sql += " and Occurarea = :Occurarea ";
		}
		if (null != paymentRiskBean.getServerIp()&& !"".equals(paymentRiskBean.getServerIp())) {
			param.put("ServerIp", paymentRiskBean.getServerIp().trim());
			sql += " and ServerIp = :ServerIp ";
		}
		if (null != paymentRiskBean.getValidDate()&& !"".equals(paymentRiskBean.getValidDate())) {
			param.put("ValidDate", paymentRiskBean.getValidDate().trim());
			sql += " and ValidDate = :ValidDate ";//substr(ValidDate,0,10)
		}
		if (null != paymentRiskBean.getOccurtimeb()&& !"".equals(paymentRiskBean.getOccurtimeb())&&null != paymentRiskBean.getOccurtimee()&& !"".equals(paymentRiskBean.getOccurtimee())) {
			param.put("Occurtimeb", paymentRiskBean.getOccurtimeb().replaceAll("-", ""));
			param.put("Occurtimee", paymentRiskBean.getOccurtimee().replaceAll("-", ""));
			String replaceAll = paymentRiskBean.getOccurtimee().replaceAll("-", "");
			String replaceAll2 = paymentRiskBean.getOccurtimeb().replaceAll("-", "");
			sql += " and Occurtimeb between :Occurtimeb and :Occurtimee and Occurtimee between :Occurtimeb and :Occurtimee";
		}
		
		sql += " order by repdate ";
		String count = "select count(*) from (" + sql + ") t ";
		BigDecimal counts;
		List<Map<String, String>> list;
		list=paymentRiskDao.queryObjectsBySqlList(sql,param,paymentRiskBean.getPage(), paymentRiskBean.getRows());
		for (int j = 0; j < list.size(); j++) {
			if(null!=list.get(j).get("OCCURAREA")){
				String Occurarea = String.valueOf(list.get(j).get("OCCURAREA"));
				String queryDictionary = queryDictionary(Occurarea);
				list.get(j).put("OCCURAREA", queryDictionary(Occurarea));
			}
		}
		
		counts = paymentRiskDao.querysqlCounts(count, param);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());// 查询数据库总条数
		dgd.setRows(list);
		return dgd;
	}
	
	//商户风险信息上报
	public Map reportMerchant(PaymentRiskBean paymentRiskBean) throws Exception{
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
		map1.put("RecSystemId", "R0001");// 协会系统编号
		map1.put("TrnxCode", paymentRiskBean.getTrnxCode());// 个人风险信息上报PR0001 商户风险信息上报ER0001
		map1.put("TrnxTime", date);
		map1.put("UserToken", paymentRiskBean.getUserToken());
		map1.put("SecretKey", "0");//密钥SecretKeySecretKey
		//商户名称、法定代表人（负责人）身份证件号码、银行结算账号、、法定代表人手机号。

		Map map2 = new LinkedHashMap();
		map2.put("CusType",paymentRiskBean.getCusType());  //01:自然人   02:企业商户   03:个体工商户
		map2.put("CusProperty", "02");  // Y客户属性;01个人 02商户
		map2.put("RiskType", paymentRiskBean.getRiskType());// Y风险类型01：虚假申请（受害人信息）02：虚假申请（嫌疑人信息）
		map2.put("CusNature", paymentRiskBean.getCusNature());// 商户属性 01实体商户 02 网络商户
		map2.put("CusName", paymentRiskBean.getCusName());// 客户姓名
		map2.put("RegName", paymentRiskBean.getRegName());// 商户营业注册名称
		map2.put("CusCode", paymentRiskBean.getCusCode());// 商户编码
		
		map2.put("DocType", paymentRiskBean.getDocType());//法人证件类型 
		map2.put("DocCode", paymentRiskBean.getDocCode());//法人证件号码
		
		map2.put("LegRepName", paymentRiskBean.getLegRepName());// 法定代表人姓名/负责人姓名
		map2.put("LegDocType", paymentRiskBean.getLegDocType());//人员证件类型
		
		map2.put("LegDocCode", paymentRiskBean.getLegDocCode());// 法人身份证件号码
		map2.put("BankNo", paymentRiskBean.getBankNo());// 银行卡账号
		map2.put("OpenBank", paymentRiskBean.getOpenBank()); //开户行
		map2.put("Url", paymentRiskBean.getUrl());// 网址
		map2.put("ServerIp",  paymentRiskBean.getServerIp());//服务器IP
		map2.put("MobileNo", paymentRiskBean.getMobileNo());
		map2.put("Address", paymentRiskBean.getAddress());//收货地址
		map2.put("Icp", paymentRiskBean.getIcp());//ICP编号
		map2.put("Level", paymentRiskBean.getLevel());// 信息级别Y
		map2.put("Occurtimeb", paymentRiskBean.getOccurtimeb());// 风险事件发生时间，时间格式，
		map2.put("Occurtimee",  paymentRiskBean.getOccurtimee());// 风险事件结束时间，时间格式，
		map2.put("Occurchan", paymentRiskBean.getOccurchan());// 发生渠道
		String occurarea = paymentRiskBean.getOccurarea();
		if(null==occurarea){
			occurarea="";
		}else{
			occurarea=occurarea.replace(" ", "");
		}
		map2.put("Occurarea", occurarea);// 风险事件发生地域，省级/地市，可多选
		
		map2.put("Note", paymentRiskBean.getNote());// 风险事件描述
		map2.put("ValidDate", paymentRiskBean.getValidDate());//有效期 格式：yyyy-MM-DD
		map2.put("OrgId", paymentRiskBean.getOrgId());// 上报机构（数字、下划线、字母）Y
		map2.put("RepDate",paymentRiskBean.getRepDate());// 上报日期 格式：yyyy-MM-DD HH:mm:ssY
		map2.put("RepType", "03");// 上传方式（值：03Y
		map2.put("RepPerson", paymentRiskBean.getRepPerson());// 上传人Y
		
		String xmlhead = PaymentRiskUtil.xmlByMap(map1);
		xmlhead = "<Head>" + xmlhead + "</Head>";
		String getxml = PaymentRiskUtil.xmlByMap(map2);
		String xmlbody = "<Body><PcacList><Count>1</Count><RiskInfo>" + getxml
				+ "</RiskInfo></PcacList></Body>";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF8\"?><Document><Request>"
				+ xmlhead
				+ xmlbody
				+ "</Request><Signature></Signature></Document>";
		
		String xsdPath =ParamPropUtils.props.getProperty("RiskXSD")+"pcac.ries.013.xsd";
		// 校验方法
		boolean checkXDS = false;
		try {
			checkXDS = PaymentRiskUtil.checkXDS(xml,xsdPath);
		} catch (SAXException e1) {
			Map resultMsg=new HashMap();
			resultMsg.put("error", "录入数据校验未通过！");
			e1.printStackTrace();
			return resultMsg;
		}
		// 加密
		Map getMM = new HashMap();
		if(!paymentRiskBean.getCusName().isEmpty()){
			getMM.put("CusName", paymentRiskBean.getCusName());
		}
		if(!paymentRiskBean.getLegDocCode().isEmpty()){
			getMM.put("LegDocCode", paymentRiskBean.getLegDocCode());
		}
		if(!paymentRiskBean.getBankNo().isEmpty()){
			getMM.put("BankNo", paymentRiskBean.getBankNo());
		}
		if(!paymentRiskBean.getMobileNo().isEmpty()){
			getMM.put("MobileNo", paymentRiskBean.getMobileNo());
		}
		String RiskPKC = ParamPropUtils.props.getProperty("RiskPKCertificate");
		Map resultMap = PaymentRiskUtil.getAEStoString(getMM,RiskPKC);
		//Map resultMap = PaymentRiskUtil.getAEStoString(getMM,"E:\\HrtApp\\src\\data\\协会公钥证书.cer");//E:\\HrtApp\\src\\data\\协会公钥证书.cer
		// 拼装加密后xml
		map1.put("SecretKey", resultMap.get("encryptedKey"));
		xmlhead =  PaymentRiskUtil.xmlByMap(map1);
		xmlhead = "<Head>" + xmlhead + "</Head>";
		if(!paymentRiskBean.getCusName().isEmpty()){
			map2.put("CusName", resultMap.get("CusName"));
		}
		if(!paymentRiskBean.getLegDocCode().isEmpty()){
			map2.put("LegDocCode", resultMap.get("LegDocCode"));
		}
		if(!paymentRiskBean.getBankNo().isEmpty()){
			map2.put("BankNo", resultMap.get("BankNo"));
		}
		if(!paymentRiskBean.getMobileNo().isEmpty()){
			map2.put("MobileNo", resultMap.get("MobileNo"));
		}
		getxml = PaymentRiskUtil.xmlByMap(map2);
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
					//String pfx = "E:\\HrtApp\\src\\data\\会员单位.pfx";
					String signPWD = ParamPropUtils.props.getProperty("signPWD");
					String addQian = PaymentRiskUtil.AddQian(xml,pfx,signPWD);
					xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
							+ xmlhead
							+ xmlbody
							+ "</Request><Signature>"
							+ addQian
							+ "</Signature></Document>";
				} catch (Exception e) {
					e.printStackTrace();
				}
				//发送请求
				String url = ParamPropUtils.props.getProperty("RiskElseUrl");
				String	Result = PaymentRiskUtil.doPost(url,xml);
				//String Result = PaymentRiskUtil.doPost("http://114.247.60.83:8080/ries_interface/httpServlet", xml);
				Map readMap = new HashMap();
				Map requestXml = PaymentRiskUtil.requestXml(Result, readMap);
				return requestXml;
			
	}
		public void saveMerchant (PaymentRiskBean paymentRiskBean){
			RiskSharingModel modle=new RiskSharingModel();
			BeanUtils.copyProperties(paymentRiskBean,modle );
			modle.setCusProperty("02");
			modle.setRepType("03");
			paymentRiskDao.saveOrUpdateObject(modle);
		}
	
	
	
	/**
	 * 个人风险信息查询
	 * @throws Exception 
	 * */
	public Map queryPersonal(PaymentRiskBean paymentRiskBean) throws Exception{
		Map map1 = new LinkedHashMap();
		map1.put("Version", "V1.0.1");
		String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
		.format(new java.util.Date());
		String substring = date.substring(0, 8);
		//获取10位随机数
		String randomString = String.valueOf(Math.random()).substring(2, 12);
		map1.put("Identification", date.substring(0, 8)+randomString);//报文唯一标识（8位日期+10顺序号）
		map1.put("OrigSender", paymentRiskBean.getOrigSender());// 会员单位机构号（字母、数字、下划线）
		map1.put("OrigSenderSID", "HrtApp2014");// 会员单位发送系统号（字母、数字、下划线）
		map1.put("RecSystemId", "R0001");// 协会系统编号
		map1.put("TrnxCode", "QR0001");//个人风险信息查询QR0001   商户风险信息查询QR0002
		map1.put("TrnxTime", date);
		map1.put("UserToken", paymentRiskBean.getUserToken());
		map1.put("SecretKey", "1");//密钥
		
		Map map2 = new LinkedHashMap();
		map2.put("RiskType", paymentRiskBean.getRiskType());// Y风险类型01：虚假申请（受害人信息）02：虚假申请（嫌疑人信息）
		map2.put("MobileNo", paymentRiskBean.getMobileNo());
		map2.put("Mac", paymentRiskBean.getMac());
		map2.put("Imei", paymentRiskBean.getImei());//Imei必须为小于或等于32位数字组成
		map2.put("BankNo", paymentRiskBean.getBankNo());// 银行卡账号
		map2.put("OpenBank", paymentRiskBean.getOpenBank()); //开户行
		map2.put("CusName", paymentRiskBean.getCusName());// 客户姓名
		map2.put("DocCode", paymentRiskBean.getDocCode());// 身份证
		map2.put("Ip",  paymentRiskBean.getIp());
		map2.put("Address", paymentRiskBean.getAddress());//收货地址
		map2.put("Telephone", paymentRiskBean.getTelephone());//固话 必须输入三位或四位区号，七位或八位电话号码，区号为三位时电话号码必须为八位），区号与电话号码以-隔开
		map2.put("RecBankNo", paymentRiskBean.getRecBankNo());//收款银行帐/卡号（只能输入数字且不能超过32位字符）
		map2.put("RecOpenBank", paymentRiskBean.getRecOpenBank());//收款账/卡号开户行
		map2.put("Email", paymentRiskBean.getEmail());
		String date2 = new java.text.SimpleDateFormat("yyyy-MM-dd")
				.format(new java.util.Date());
		map2.put("Occurtimeb", paymentRiskBean.getOccurtimeb());// 风险事件发生时间，时间格式，
		map2.put("Occurtimee",  paymentRiskBean.getOccurtimee());// 风险事件结束时间，时间格式，
		map2.put("Occurchan", paymentRiskBean.getOccurchan());// 发生渠道
		String occurarea = paymentRiskBean.getOccurarea();
		if(null==occurarea){
			occurarea="";
		}else{
			occurarea=occurarea.replace(" ", "");
		}
		map2.put("Occurarea", occurarea);// 风险事件发生地域，省级/地市，可多选
		map2.put("Scope", paymentRiskBean.getScope());//查询范围，默认全部 01全库 02：本机构
	    map2.put("ValidStatus", "01");//有效性01：有效 02：失效
		
		String xmlhead = PaymentRiskUtil.xmlByMap(map1);
		xmlhead = "<Head>" + xmlhead + "</Head>";
		String getxml = PaymentRiskUtil.xmlByMap(map2);
		String xmlbody = "<Body>"+getxml+"</Body>";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
				+ xmlhead
				+ xmlbody
				+ "</Request><Signature></Signature></Document>";
		String xsdPath =ParamPropUtils.props.getProperty("RiskXSD")+"pcac.ries.003.xsd";
		// 校验方法
		boolean checkXDS = false;
		try {
			checkXDS = PaymentRiskUtil.checkXDS(xml,xsdPath);
		} catch (SAXException e1) {
			Map resultMsg=new HashMap();
			resultMsg.put("error", "录入数据校验失败！");
			e1.printStackTrace();
			return resultMsg;
		}
		
		// 加签
				try {
					xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
							+ xmlhead + xmlbody + "</Request></Document>";
					String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
					String signPWD = ParamPropUtils.props.getProperty("signPWD");
					String addQian = PaymentRiskUtil.AddQian(xml,pfx,signPWD);
					xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
							+ xmlhead
							+ xmlbody
							+ "</Request><Signature>"
							+ addQian
							+ "</Signature></Document>";
				} catch (Exception e) {
					e.printStackTrace();
				}
				//发送请求
				String url = ParamPropUtils.props.getProperty("RiskElseUrl");
				String	Result = PaymentRiskUtil.doPost(url,xml);
				//String Result = PaymentRiskUtil.doPost("http://114.247.60.83:8080/ries_interface/httpServlet", xml);
				Map readMap = new HashMap();
				Map requestXml = PaymentRiskUtil.requestXml(Result, readMap);
				//解密
				String SecretKey = (String) requestXml.get("SecretKey");
				Map decrypt = null;
				List<Map<String,String>> RiskInfo = (List) requestXml.get("resultlist");
				try {
					for (int i = 0; i < RiskInfo.size(); i++) {
						if(!RiskInfo.get(i).get("Occurarea").isEmpty()){
							String Occurarea = String.valueOf(RiskInfo.get(i).get("Occurarea"));
							String queryDictionary = queryDictionary(Occurarea);
							RiskInfo.get(i).put("Occurarea", queryDictionary(Occurarea));
						}
						Map JieMiMap = new HashMap();
						if(!RiskInfo.get(i).get("MobileNo").isEmpty()){
							JieMiMap.put("MobileNo", String.valueOf(RiskInfo.get(i).get("MobileNo")));// 手机号
						}
						if(!RiskInfo.get(i).get("BankNo").isEmpty()){
							JieMiMap.put("BankNo", String.valueOf(RiskInfo.get(i).get("BankNo")));// kahao
						}
						if(!RiskInfo.get(i).get("CusName").isEmpty()){
							JieMiMap.put("CusName", String.valueOf(RiskInfo.get(i).get("CusName")));// 客户姓名
						}
						if(!RiskInfo.get(i).get("DocCode").isEmpty()){
							JieMiMap.put("DocCode", String.valueOf(RiskInfo.get(i).get("DocCode")));// 身份证件号码
						}
						if(!RiskInfo.get(i).get("Telephone").isEmpty()){
							JieMiMap.put("Telephone", String.valueOf(RiskInfo.get(i).get("Telephone")));// 固定电话
						}
						if(!RiskInfo.get(i).get("RecBankNo").isEmpty()){
							JieMiMap.put("RecBankNo", String.valueOf(RiskInfo.get(i).get("RecBankNo")));// 收款银行帐/卡号
						}
						String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
						String DecryptPWD = ParamPropUtils.props.getProperty("DecryptPWD");
						decrypt = PaymentRiskUtil.decrypt(SecretKey, JieMiMap,pfx,DecryptPWD);
						if(decrypt!=null){
							RiskInfo.get(i).putAll(decrypt);
						}
					}
				} catch (Exception e) {
					Map resultMsg=new HashMap();
					resultMsg.put("error", "响应数据解密失败！");
					e.printStackTrace();
					return resultMsg;
				}
				requestXml.put("resultlist", RiskInfo);				
				
			return requestXml;
		
	}

	/**
	 * 商户风险信息查询
	 * */
	public Map queryMerchant(PaymentRiskBean paymentRiskBean) throws Exception{
		Map map1 = new LinkedHashMap();
		map1.put("Version", "V1.0.1");
		String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
		.format(new java.util.Date());
		String substring = date.substring(0, 8);
		//获取10位随机数
		String randomString = String.valueOf(Math.random()).substring(2, 12);
		map1.put("Identification", date.substring(0, 8)+randomString);//报文唯一标识（8位日期+10顺序号）
		map1.put("OrigSender", paymentRiskBean.getOrigSender());// 会员单位机构号（字母、数字、下划线）
		map1.put("OrigSenderSID", "HrtApp2014");// 会员单位发送系统号（字母、数字、下划线）
		map1.put("RecSystemId", "R0001");// 协会系统编号
		map1.put("TrnxCode", "QR0002");//个人风险信息查询QR0001   商户风险信息查询QR0002
		map1.put("TrnxTime", date);
		map1.put("UserToken", paymentRiskBean.getUserToken());
		map1.put("SecretKey", "1");//密钥
		
		Map map2 = new LinkedHashMap();
		map2.put("RiskType", paymentRiskBean.getRiskType());// Y风险类型01：虚假申请（受害人信息）02：虚假申请（嫌疑人信息）
		map2.put("CusNature", paymentRiskBean.getCusNature());// 商户属性 01实体商户 02 网络商户
		map2.put("CusName", paymentRiskBean.getCusName());// 客户姓名
		map2.put("RegName", paymentRiskBean.getRegName());// 商户营业注册名称
		map2.put("CusCode", paymentRiskBean.getCusCode());// 商户编码
		map2.put("DocType", paymentRiskBean.getDocType());// 法人证件类型
		map2.put("DocCode", paymentRiskBean.getDocCode());// 法人证件号码
//		map2.put("OrgCode", paymentRiskBean.getOrgCode());// 组织机构代码
//		map2.put("BusLicCode", paymentRiskBean.getBusLicCode());// 营业执照编码
//		map2.put("SocialUnityCreditCode", paymentRiskBean.getSocialUnityCreditCode());// 统一社会信用代码
//		map2.put("TaxRegCer", paymentRiskBean.getTaxRegCer());// 税务登记证
		map2.put("LegRepName", paymentRiskBean.getLegRepName());// 法定代表人姓名/负责人姓名
		map2.put("LegDocCode", paymentRiskBean.getLegDocCode());// 法人身份证件号码
		map2.put("BankNo", paymentRiskBean.getBankNo());// 银行卡账号
		map2.put("OpenBank", paymentRiskBean.getOpenBank()); //开户行
		map2.put("Url", paymentRiskBean.getUrl());// 网址
		map2.put("ServerIp",  paymentRiskBean.getServerIp());//服务器IP
		map2.put("MobileNo", paymentRiskBean.getMobileNo());
		map2.put("Address", paymentRiskBean.getAddress());//收货地址
		map2.put("Icp", paymentRiskBean.getIcp());//ICP编号
		map2.put("Level", paymentRiskBean.getLevel());// 信息级别Y
		map2.put("Occurtimeb", paymentRiskBean.getOccurtimeb());// 风险事件发生时间，时间格式，
		map2.put("Occurtimee",  paymentRiskBean.getOccurtimee());// 风险事件结束时间，时间格式，
		map2.put("Occurchan", paymentRiskBean.getOccurchan());// 发生渠道
		String occurarea = paymentRiskBean.getOccurarea();
		if(null==occurarea){
			occurarea="";
		}else{
			occurarea=occurarea.replace(" ", "");
		}
		map2.put("Occurarea", occurarea);// 风险事件发生地域，省级/地市，可多选
		
		map2.put("Scope", "01");// Y查询范围，默认全部 01
		map2.put("ValidStatus", "01");// 有效性
		
		String xmlhead = PaymentRiskUtil.xmlByMap(map1);
		xmlhead = "<Head>" + xmlhead + "</Head>";
		String getxml = PaymentRiskUtil.xmlByMap(map2);
		String xmlbody = "<Body>"+getxml+"</Body>";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
				+ xmlhead
				+ xmlbody
				+ "</Request><Signature></Signature></Document>";
		String xsdPath =ParamPropUtils.props.getProperty("RiskXSD")+"pcac.ries.015.xsd";
		// 校验方法
		boolean checkXDS = false;
		try {
			checkXDS = PaymentRiskUtil.checkXDS(xml,xsdPath);
		} catch (SAXException e1) {
			Map resultMsg=new HashMap();
			resultMsg.put("error", "录入数据校验未通过！");
			e1.printStackTrace();
			return resultMsg;
		}		
		// 加签
				try {
					xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
							+ xmlhead + xmlbody + "</Request></Document>";
					String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
					String signPWD = ParamPropUtils.props.getProperty("signPWD");
					String addQian = PaymentRiskUtil.AddQian(xml,pfx,signPWD);
					xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
							+ xmlhead
							+ xmlbody
							+ "</Request><Signature>"
							+ addQian
							+ "</Signature></Document>";
				} catch (Exception e) {
					e.printStackTrace();
				}
				//发送请求
				String url = ParamPropUtils.props.getProperty("RiskElseUrl");
				String	Result = PaymentRiskUtil.doPost(url,xml);
				//String Result = PaymentRiskUtil.doPost("http://114.247.60.83:8080/ries_interface/httpServlet", xml);
				Map readMap = new HashMap();
				Map requestXml = PaymentRiskUtil.requestXml(Result, readMap);
				
				//解密
				String SecretKey = (String) requestXml.get("SecretKey");
				Map decrypt = null;
				List<Map<String,String>> RiskInfo = (List) requestXml.get("resultlist");
				
				try {
					for (int i = 0; i < RiskInfo.size(); i++) {
						if(!RiskInfo.get(i).get("Occurarea").isEmpty()){
							String Occurarea = String.valueOf(RiskInfo.get(i).get("Occurarea"));
							String queryDictionary = queryDictionary(Occurarea);
							RiskInfo.get(i).put("Occurarea", queryDictionary(Occurarea));
						}
						Map JieMiMap = new HashMap();
						if(!RiskInfo.get(i).get("CusName").isEmpty()){
							JieMiMap.put("CusName", String.valueOf(RiskInfo.get(i).get("CusName")));// 商户名称
						}
						if(!RiskInfo.get(i).get("LegDocCode").isEmpty()){
							JieMiMap.put("LegDocCode", String.valueOf(RiskInfo.get(i).get("LegDocCode")));// 身份证件号码
						}
						if(!RiskInfo.get(i).get("BankNo").isEmpty()){
							JieMiMap.put("BankNo", String.valueOf(RiskInfo.get(i).get("BankNo")));// 银行结算账号
						}
						if(!RiskInfo.get(i).get("MobileNo").isEmpty()){
							JieMiMap.put("MobileNo", String.valueOf(RiskInfo.get(i).get("MobileNo")));// 法定代表人手机号。
						}
						String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
						String DecryptPWD = ParamPropUtils.props.getProperty("DecryptPWD");
						decrypt = PaymentRiskUtil.decrypt(SecretKey, JieMiMap,pfx,DecryptPWD);
						if(decrypt!=null){
							RiskInfo.get(i).putAll(decrypt);
						}
					}
				} catch (Exception e) {
					//解密失败；
					Map resultMsg=new HashMap();
					resultMsg.put("error", "返回数据解密未通过！");
					e.printStackTrace();
					return resultMsg;
					
				}
				requestXml.put("resultlist", RiskInfo);				
				
			return requestXml;
	}
	
	
	/**
	 * 批量信息导入查询
	 * @throws Exception 
	 * */
	public Map queryBatchImport(PaymentRiskBean paymentRiskBean) throws Exception{
		
		Map map1 = new LinkedHashMap();
		map1.put("Version", "V1.0.1");
		String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
		.format(new java.util.Date());
		String substring = date.substring(0, 8);
		//获取10位随机数
		String randomString = String.valueOf(Math.random()).substring(2, 12);
		map1.put("Identification", date.substring(0, 8)+randomString);//报文唯一标识（8位日期+10顺序号）
		map1.put("OrigSender", paymentRiskBean.getOrigSender());// 会员单位机构号（字母、数字、下划线）
		map1.put("OrigSenderSID", "HrtApp2014");// 会员单位发送系统号（字母、数字、下划线）
		map1.put("RecSystemId", "R0001");// 协会系统编号
		map1.put("TrnxCode", "QR0003");//批量信息导入查询
		map1.put("TrnxTime", date);
		map1.put("UserToken", paymentRiskBean.getUserToken());
		map1.put("SecretKey", "1");//密钥
		
		Map map2 = new LinkedHashMap();
		map2.put("CusProperty",  paymentRiskBean.getCusProperty());// 01个人   02 商户
		map2.put("KeyWord", paymentRiskBean.getKeyWord());// 关键字
		map2.put("Infos", paymentRiskBean.getInfos());// 查询条件信息
		
		String xmlhead = PaymentRiskUtil.xmlByMap(map1);
		xmlhead = "<Head>" + xmlhead + "</Head>";
		String getxml = PaymentRiskUtil.xmlByMap(map2);
		String xmlbody = "<Body>"+getxml+"</Body>";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
				+ xmlhead
				+ xmlbody
				+ "</Request><Signature></Signature></Document>";
		String xsdPath =ParamPropUtils.props.getProperty("RiskXSD")+"pcac.ries.005.xsd";
		// 校验方法
		boolean checkXDS = false;
		try {
			checkXDS = PaymentRiskUtil.checkXDS(xml,xsdPath);
		} catch (SAXException e1) {
			Map resultMsg=new HashMap();
			resultMsg.put("error", "录入数据校验未通过！");
			e1.printStackTrace();
			return resultMsg;
		}		
				
		// 加签
		try {
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
					+ xmlhead + xmlbody + "</Request></Document>";
			String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
			String signPWD = ParamPropUtils.props.getProperty("signPWD");
			String addQian = PaymentRiskUtil.AddQian(xml,pfx,signPWD);
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
					+ xmlhead
					+ xmlbody
					+ "</Request><Signature>"
					+ addQian
					+ "</Signature></Document>";
		} catch (Exception e) {
			e.printStackTrace();
		}
		//发送请求
		String url = ParamPropUtils.props.getProperty("RiskElseUrl");
		String	Result = PaymentRiskUtil.doPost(url,xml);
		//String Result = PaymentRiskUtil.doPost("http://114.247.60.83:8080/ries_interface/httpServlet", xml);
		Map readMap = new HashMap();
		Map requestXml = PaymentRiskUtil.requestXml(Result, readMap);
		
		//解密
		String SecretKey = (String) requestXml.get("SecretKey");
		Map decrypt = null;
		List<Map<String,String>> RiskInfo = (List) requestXml.get("resultlist");
		try {
			for (int i = 0; i < RiskInfo.size(); i++) {
				if(!RiskInfo.get(i).get("Occurarea").isEmpty()){
					String Occurarea = String.valueOf(RiskInfo.get(i).get("Occurarea"));
					String queryDictionary = queryDictionary(Occurarea);
					RiskInfo.get(i).put("Occurarea", queryDictionary(Occurarea));
				}
				Map JieMiMap = new HashMap();
				if(!RiskInfo.get(i).get("MobileNo").isEmpty()){
					JieMiMap.put("MobileNo", String.valueOf(RiskInfo.get(i).get("MobileNo")));// 手机号
				}
				if(!RiskInfo.get(i).get("CusName").isEmpty()){
					JieMiMap.put("CusName", String.valueOf(RiskInfo.get(i).get("CusName")));// 客户姓名
				}
				if(!RiskInfo.get(i).get("BankNo").isEmpty()){
					JieMiMap.put("BankNo", String.valueOf(RiskInfo.get(i).get("BankNo")));// kahao
				}
				if(paymentRiskBean.getCusProperty().equals("01")){//个人
					if(!RiskInfo.get(i).get("DocCode").isEmpty()){
						JieMiMap.put("DocCode", String.valueOf(RiskInfo.get(i).get("DocCode")));// 身份证件号码
					}
					if(!RiskInfo.get(i).get("Telephone").isEmpty()){
						JieMiMap.put("Telephone", String.valueOf(RiskInfo.get(i).get("Telephone")));// 固定电话
					}
					if(!RiskInfo.get(i).get("RecBankNo").isEmpty()){
						JieMiMap.put("RecBankNo", String.valueOf(RiskInfo.get(i).get("RecBankNo")));// 收款银行帐/卡号
					}
					
				}else if(paymentRiskBean.getCusProperty().equals("02")){// 商户
					if(!RiskInfo.get(i).get("LegDocCode").isEmpty()){
						JieMiMap.put("LegDocCode", String.valueOf(RiskInfo.get(i).get("LegDocCode")));// 身份证件号码
					}
					
				}
				String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
				String DecryptPWD = ParamPropUtils.props.getProperty("DecryptPWD");
				decrypt = PaymentRiskUtil.decrypt(SecretKey, JieMiMap,pfx,DecryptPWD);
				if(decrypt!=null){
					RiskInfo.get(i).putAll(decrypt);
				}
				
			}
		} catch (Exception e) {
			//解密失败；
			e.printStackTrace();
			Map resultMsg=new HashMap();
			resultMsg.put("error", "响应数据解密未通过！");
			return resultMsg;
		}
		requestXml.put("resultlist", RiskInfo);
		
		return requestXml;
	}
	
	public Map queryConfluence(PaymentRiskBean paymentRiskBean) throws Exception{
		Map map1 = new LinkedHashMap();
		map1.put("Version", "V1.0.1");
		String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
		.format(new java.util.Date());
		String substring = date.substring(0, 8);
		//获取10位随机数
		String randomString = String.valueOf(Math.random()).substring(2, 12);
		map1.put("Identification", date.substring(0, 8)+randomString);//报文唯一标识（8位日期+10顺序号）
		map1.put("OrigSender", paymentRiskBean.getOrigSender());// 会员单位机构号（字母、数字、下划线）
		map1.put("OrigSenderSID", "HrtApp2014");// 会员单位发送系统号（字母、数字、下划线）
		map1.put("RecSystemId", "R0001");// 协会系统编号
		map1.put("TrnxCode", "QR0004");//
		map1.put("TrnxTime", date);
		map1.put("UserToken", paymentRiskBean.getUserToken());
		map1.put("SecretKey", "1");//密钥
		
		Map map2 = new LinkedHashMap();
		map2.put("CusProperty", paymentRiskBean.getCusProperty());// 客户属性 01 个人 02商户
		map2.put("KeyWord", paymentRiskBean.getKeyWord());// 关键字
		map2.put("Info", paymentRiskBean.getInfo());// 关键字信息
		map2.put("RiskType", paymentRiskBean.getRiskType());// 风险类型
		map2.put("StartTime",paymentRiskBean.getStartTime());// 上报开始时间
		map2.put("EndTime", paymentRiskBean.getEndTime());// 风险信息上报结束时间 格式：yyyy-MM-DD
		
		String xmlhead = PaymentRiskUtil.xmlByMap(map1);
		xmlhead = "<Head>" + xmlhead + "</Head>";
		String getxml = PaymentRiskUtil.xmlByMap(map2);
		String xmlbody = "<Body>"+getxml+"</Body>";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
				+ xmlhead
				+ xmlbody
				+ "</Request><Signature></Signature></Document>";
		String xsdPath =ParamPropUtils.props.getProperty("RiskXSD")+"pcac.ries.007.xsd";
		// 校验方法
		boolean checkXDS = false;
		try {
			checkXDS = PaymentRiskUtil.checkXDS(xml,xsdPath);
		} catch (SAXException e1) {
			Map resultMsg=new HashMap();
			resultMsg.put("error", "录入数据校验未通过！");
			e1.printStackTrace();
			return resultMsg;
		}		
				
		// 加签
		try {
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
					+ xmlhead + xmlbody + "</Request></Document>";
			String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
			String signPWD = ParamPropUtils.props.getProperty("signPWD");
			String addQian = PaymentRiskUtil.AddQian(xml,pfx,signPWD);
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
					+ xmlhead
					+ xmlbody
					+ "</Request><Signature>"
					+ addQian
					+ "</Signature></Document>";
		} catch (Exception e) {
			e.printStackTrace();
		}
		//发送请求
		String url = ParamPropUtils.props.getProperty("RiskElseUrl");
		String	Result = PaymentRiskUtil.doPost(url,xml);
		//String Result = PaymentRiskUtil.doPost("http://114.247.60.83:8080/ries_interface/httpServlet", xml);
		Map readMap = new HashMap();
		Map requestXml = PaymentRiskUtil.requestXml(Result, readMap);
		return requestXml;
	}


	@Override
	public Map queryPersonalChange(PaymentRiskBean paymentRiskBean) throws Exception {
		Map map1 = new LinkedHashMap();
		map1.put("Version", "V1.0.1");
		String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
		.format(new java.util.Date());
		String substring = date.substring(0, 8);
		//获取10位随机数
		String randomString = String.valueOf(Math.random()).substring(2, 12);
		map1.put("Identification", date.substring(0, 8)+randomString);//报文唯一标识（8位日期+10顺序号）
		map1.put("OrigSender", paymentRiskBean.getOrigSender());// 会员单位机构号（字母、数字、下划线）
		map1.put("OrigSenderSID", "HrtApp2014");// 会员单位发送系统号（字母、数字、下划线）
		map1.put("RecSystemId", "R0001");// 协会系统编号
		map1.put("TrnxCode", "UP0001");//
		map1.put("TrnxTime", date);
		map1.put("UserToken", paymentRiskBean.getUserToken());
		map1.put("SecretKey", "1");//密钥

		Map map2 = new LinkedHashMap();
		map2.put("RiskType", paymentRiskBean.getRiskType());// 风险类型 01：虚假申请（受害人信息）02：虚假申请（嫌疑人信息）
		map2.put("MobileNo", paymentRiskBean.getMobileNo());// 手机号
		map2.put("Mac", paymentRiskBean.getMac());// MAC地址
		map2.put("Imei", paymentRiskBean.getImei());// Imei
		map2.put("BankNo", paymentRiskBean.getBankNo());// 银行帐/卡号
		
		String xmlhead = PaymentRiskUtil.xmlByMap(map1);
		String xmlbody = PaymentRiskUtil.xmlByMap(map2);
		xmlhead = "<Head>" + xmlhead + "</Head>";
		xmlbody = "<Body>" + xmlbody + "</Body>";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
				+ xmlhead
				+ xmlbody
				+ "</Request><Signature></Signature></Document>";
		String xsdPath =ParamPropUtils.props.getProperty("RiskXSD")+"pcac.ries.009.xsd";
		// 校验方法
		boolean checkXDS = false;
		try {
			checkXDS = PaymentRiskUtil.checkXDS(xml,xsdPath);
		} catch (SAXException e1) {
			Map resultMsg=new HashMap();
			resultMsg.put("error", "录入数据校验未通过！");
			e1.printStackTrace();
			return resultMsg;
		}		
		
		// 加签
				try {
					xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
							+ xmlhead + xmlbody + "</Request></Document>";
					String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
					String signPWD = ParamPropUtils.props.getProperty("signPWD");
					String addQian = PaymentRiskUtil.AddQian(xml,pfx,signPWD);
					xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
							+ xmlhead
							+ xmlbody
							+ "</Request><Signature>"
							+ addQian
							+ "</Signature></Document>";
				} catch (Exception e) {
					e.printStackTrace();
				}
				//发送请求
				String url = ParamPropUtils.props.getProperty("RiskElseUrl");
				String	Result = PaymentRiskUtil.doPost(url,xml);
				//String Result = PaymentRiskUtil.doPost("http://114.247.60.83:8080/ries_interface/httpServlet", xml);
				//
				Map readMap = new HashMap();
				Map requestXml = PaymentRiskUtil.requestXml(Result, readMap);
				//解密
				String SecretKey = (String) requestXml.get("SecretKey");
				Map decrypt = null;
				List<Map<String,String>> RiskInfo = (List) requestXml.get("resultlist");
				try {
					for (int i = 0; i < RiskInfo.size(); i++) {
						if(!RiskInfo.get(i).get("Occurarea").isEmpty()){
							String Occurarea = String.valueOf(RiskInfo.get(i).get("Occurarea"));
							String queryDictionary = queryDictionary(Occurarea);
							RiskInfo.get(i).put("OccurareaTxt", queryDictionary(Occurarea));
						}
						Map JieMiMap = new HashMap();
						if(!RiskInfo.get(i).get("MobileNo").isEmpty()){
							JieMiMap.put("MobileNo", String.valueOf(RiskInfo.get(i).get("MobileNo")));// 手机号
						}
						if(!RiskInfo.get(i).get("BankNo").isEmpty()){
							JieMiMap.put("BankNo", String.valueOf(RiskInfo.get(i).get("BankNo")));// kahao
						}
						if(!RiskInfo.get(i).get("CusName").isEmpty()){
							JieMiMap.put("CusName", String.valueOf(RiskInfo.get(i).get("CusName")));// 客户姓名
						}
						if(!RiskInfo.get(i).get("DocCode").isEmpty()){
							JieMiMap.put("DocCode", String.valueOf(RiskInfo.get(i).get("DocCode")));// 身份证件号码
						}
						if(!RiskInfo.get(i).get("Telephone").isEmpty()){
							JieMiMap.put("Telephone", String.valueOf(RiskInfo.get(i).get("Telephone")));// 固定电话
						}
						if(!RiskInfo.get(i).get("RecBankNo").isEmpty()){
							JieMiMap.put("RecBankNo", String.valueOf(RiskInfo.get(i).get("RecBankNo")));// 收款银行帐/卡号
						}
						String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
						String DecryptPWD = ParamPropUtils.props.getProperty("DecryptPWD");
						decrypt = PaymentRiskUtil.decrypt(SecretKey, JieMiMap,pfx,DecryptPWD);
						if(decrypt!=null){
							RiskInfo.get(i).putAll(decrypt);
						}
					}
				} catch (Exception e) {
					//解密失败；
					Map resultMsg=new HashMap();
					resultMsg.put("error", "响应数据解密失败！");
					e.printStackTrace();
					//return resultMsg;
				}
				requestXml.put("resultlist", RiskInfo);
				
				return requestXml;
	}


	@Override
	public Map updatePersonalChange(PaymentRiskBean paymentRiskBean) throws Exception {
		Map map1 = new LinkedHashMap();
		map1.put("Version", "V1.0.1");
		String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
		.format(new java.util.Date());
		String substring = date.substring(0, 8);
		//获取10位随机数
		String randomString = String.valueOf(Math.random()).substring(2, 12);
		map1.put("Identification", date.substring(0, 8)+randomString);//报文唯一标识（8位日期+10顺序号）
		map1.put("OrigSender", paymentRiskBean.getOrigSender());// 会员单位机构号（字母、数字、下划线）
		map1.put("OrigSenderSID", "HrtApp2014");// 会员单位发送系统号（字母、数字、下划线）
		map1.put("RecSystemId", "R0001");// 协会系统编号
		map1.put("TrnxCode", "UP0002");//个人风险信息查询QR0001   商户风险信息查询QR0002
		map1.put("TrnxTime", date);
		map1.put("UserToken", paymentRiskBean.getUserToken());
		map1.put("SecretKey", "1");//密钥
		
		Map map2 = new LinkedHashMap();
		map2.put("Id", paymentRiskBean.getId());// 个人风险信息数据库唯一标识
		map2.put("UpdateType", paymentRiskBean.getUpdateType());// 变更类型（补录、失效）
		map2.put("CusProperty", paymentRiskBean.getCusProperty());//、、客户属性（默认值：01）
		map2.put("RiskType", paymentRiskBean.getRiskType());// Y风险类型01：虚假申请（受害人信息）02：虚假申请（嫌疑人信息）
		map2.put("MobileNo", paymentRiskBean.getMobileNo());
		map2.put("Mac", paymentRiskBean.getMac());
		map2.put("Imei", paymentRiskBean.getImei());
		map2.put("BankNo", paymentRiskBean.getBankNo());// 银行卡账号
		map2.put("OpenBank", paymentRiskBean.getOpenBank());
		map2.put("CusName", paymentRiskBean.getCusName());// 客户姓名
		map2.put("DocCode", paymentRiskBean.getDocCode());// 身份证
		map2.put("Ip", paymentRiskBean.getIp());
		map2.put("Address", paymentRiskBean.getAddress());//
		map2.put("Telephone", paymentRiskBean.getTelephone());
		map2.put("RecBankNo", paymentRiskBean.getRecBankNo());
		map2.put("RecOpenBank", paymentRiskBean.getRiskType());
		map2.put("Email", paymentRiskBean.getEmail());
		String date2 = new java.text.SimpleDateFormat("yyyy-MM-dd")
				.format(new java.util.Date());
		map2.put("ValidDate", date2);// Y有效期
		map2.put("Level", paymentRiskBean.getLevel());// 信息级别Y
		map2.put("Occurtimeb",paymentRiskBean.getOccurtimeb());// 风险事件发生时间，时间格式，
		map2.put("Occurtimee",paymentRiskBean.getOccurtimee());// 风险事件结束时间，时间格式，
		map2.put("Occurchan", paymentRiskBean.getOccurchan());// 发生渠道
		String occurarea = paymentRiskBean.getOccurarea();
		if(null==occurarea){
			occurarea="";
		}else{
			occurarea=occurarea.replace(" ", "");
		}
		map2.put("Occurarea", occurarea);// 风险事件发生地域，省级/地市，可多选
		map2.put("Note", paymentRiskBean.getNote());// 风险事件描述
		map2.put("OrgId", paymentRiskBean.getOrgId());// 上报机构（数字、下划线、字母）Y
		String date3 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date());
		map2.put("RepDate", date3);// 上报日期 格式：yyyy-MM-DD HH:mm:ssY
		map2.put("RepType", "03");// 上传方式（值：03Y
		map2.put("RepPerson", paymentRiskBean.getRepPerson());// 上传人Y
		
		String xmlhead = PaymentRiskUtil.xmlByMap(map1);
		String xmlbody = PaymentRiskUtil.xmlByMap(map2);
		xmlhead = "<Head>" + xmlhead + "</Head>";
		xmlbody = "<Body><PcacList><Count>1</Count><RiskInfo>" + xmlbody + "</RiskInfo></PcacList></Body>";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
				+ xmlhead
				+ xmlbody
				+ "</Request><Signature></Signature></Document>";
		String xsdPath =ParamPropUtils.props.getProperty("RiskXSD")+"pcac.ries.011.xsd";
		// 校验方法
		boolean checkXDS = false;
		try {
			checkXDS = PaymentRiskUtil.checkXDS(xml,xsdPath);
		} catch (SAXException e1) {
			Map resultMsg=new HashMap();
			resultMsg.put("error", "录入数据校验未通过！");
			e1.printStackTrace();
			return resultMsg;
		}		
		
		// 加密
				Map getMM = new HashMap();
				if(!paymentRiskBean.getMobileNo().isEmpty()){
					getMM.put("MobileNo", paymentRiskBean.getMobileNo());
				}
				if(!paymentRiskBean.getBankNo().isEmpty()){
					getMM.put("BankNo", paymentRiskBean.getBankNo());
				}
				if(!paymentRiskBean.getCusName().isEmpty()){
					getMM.put("CusName", paymentRiskBean.getCusName());
				}
				if(!paymentRiskBean.getDocCode().isEmpty()){
					getMM.put("DocCode", paymentRiskBean.getDocCode());
				}
				if(!paymentRiskBean.getTelephone().isEmpty()){
					getMM.put("Telephone", paymentRiskBean.getTelephone());
				}
				if(!paymentRiskBean.getRecBankNo().isEmpty()){
					getMM.put("RecBankNo", paymentRiskBean.getRecBankNo());
				}
				Map resultMap = null;
				if(getMM.size()>0){
					String RiskPKC = ParamPropUtils.props.getProperty("RiskPKCertificate");
					resultMap = PaymentRiskUtil.getAEStoString(getMM,RiskPKC);
					//resultMap = PaymentRiskUtil.getAEStoString(getMM,"E:\\\\HrtApp\\\\src\\\\data\\\\协会公钥证书.cer");//E:\\HrtApp\\src\\data\\会员单位.pfx
					map1.put("SecretKey", resultMap.get("encryptedKey"));
				}

				// 拼装加密后xml
				xmlhead = PaymentRiskUtil.xmlByMap(map1);
				
				xmlhead = "<Head>" + xmlhead + "</Head>";
				if(!paymentRiskBean.getMobileNo().isEmpty()){
					map2.put("MobileNo", resultMap.get("MobileNo"));
				}
				if(!paymentRiskBean.getBankNo().isEmpty()){
					map2.put("BankNo", resultMap.get("BankNo"));
				}
				if(!paymentRiskBean.getCusName().isEmpty()){
					map2.put("CusName", resultMap.get("CusName"));
				}
				if(!paymentRiskBean.getDocCode().isEmpty()){
					map2.put("DocCode", resultMap.get("DocCode"));
				}
				if(!paymentRiskBean.getTelephone().isEmpty()){
					map2.put("Telephone", resultMap.get("Telephone"));
				}
				if(!paymentRiskBean.getRecBankNo().isEmpty()){
					map2.put("RecBankNo", resultMap.get("RecBankNo"));
				}
				xmlbody = PaymentRiskUtil.xmlByMap(map2);
		//xmlbody = "<Body>" + xmlbody + "</Body>";
		 xmlbody = "<Body><PcacList><Count>1</Count><RiskInfo>" + xmlbody
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
			String addQian = PaymentRiskUtil.AddQian(xml,pfx,signPWD);
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
					+ xmlhead
					+ xmlbody
					+ "</Request><Signature>"
					+ addQian
					+ "</Signature></Document>";
		} catch (Exception e) {
			e.printStackTrace();
		}
		//发送请求
		String url = ParamPropUtils.props.getProperty("RiskElseUrl");
		String	Result = PaymentRiskUtil.doPost(url,xml);
		//String Result = PaymentRiskUtil.doPost("http://114.247.60.83:8080/ries_interface/httpServlet", xml);
		
		Map readMap = new HashMap();
		Map requestXml = PaymentRiskUtil.requestXml(Result, readMap);
		return requestXml;
	}


	@Override
	public Map queryMerchantChange(PaymentRiskBean paymentRiskBean) throws Exception {
		Map map1 = new LinkedHashMap();
		map1.put("Version", "V1.0.1");
		String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
		.format(new java.util.Date());
		String substring = date.substring(0, 8);
		//获取10位随机数
		String randomString = String.valueOf(Math.random()).substring(2, 12);
		map1.put("Identification", date.substring(0, 8)+randomString);//报文唯一标识（8位日期+10顺序号）
		map1.put("OrigSender", paymentRiskBean.getOrigSender());// 会员单位机构号（字母、数字、下划线）
		map1.put("OrigSenderSID", "HrtApp2014");// 会员单位发送系统号（字母、数字、下划线）
		map1.put("RecSystemId", "R0001");// 协会系统编号
		map1.put("TrnxCode", "UP0003");//
		map1.put("TrnxTime", date);
		map1.put("UserToken", paymentRiskBean.getUserToken());
		map1.put("SecretKey", "1");//密钥

		Map map2 = new LinkedHashMap();
		map2.put("RiskType", paymentRiskBean.getRiskType());// 风险类型 01：虚假申请（受害人信息）02：虚假申请（嫌疑人信息）
		map2.put("CusName", paymentRiskBean.getCusName());// 客户名称
		map2.put("DocType", paymentRiskBean.getDocType());// 法人证件类型
		map2.put("DocCode", paymentRiskBean.getDocCode());// 法人证件号码
		
		String xmlhead = PaymentRiskUtil.xmlByMap(map1);
		String xmlbody = PaymentRiskUtil.xmlByMap(map2);
		xmlhead = "<Head>" + xmlhead + "</Head>";
		xmlbody = "<Body>" + xmlbody + "</Body>";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
				+ xmlhead
				+ xmlbody
				+ "</Request><Signature></Signature></Document>";
		String xsdPath =ParamPropUtils.props.getProperty("RiskXSD")+"pcac.ries.017.xsd";
		// 校验方法
		boolean checkXDS = false;
		try {
			checkXDS = PaymentRiskUtil.checkXDS(xml,xsdPath);
		} catch (SAXException e1) {
			Map resultMsg=new HashMap();
			resultMsg.put("error", "录入数据校验未通过！");
			e1.printStackTrace();
			return resultMsg;
		}				
		// 加签
				try {
					xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
							+ xmlhead + xmlbody + "</Request></Document>";
					String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
					String signPWD = ParamPropUtils.props.getProperty("signPWD");
					String addQian = PaymentRiskUtil.AddQian(xml,pfx,signPWD);
					xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
							+ xmlhead
							+ xmlbody
							+ "</Request><Signature>"
							+ addQian
							+ "</Signature></Document>";
				} catch (Exception e) {
					e.printStackTrace();
				}
				//发送请求
				String url = ParamPropUtils.props.getProperty("RiskElseUrl");
				String	Result = PaymentRiskUtil.doPost(url,xml);
				//String Result = PaymentRiskUtil.doPost("http://114.247.60.83:8080/ries_interface/httpServlet", xml);
				//
				Map readMap = new HashMap();
				Map requestXml = PaymentRiskUtil.requestXml(Result, readMap);
				//解密
				String SecretKey = (String) requestXml.get("SecretKey");
				Map decrypt = null;
				List<Map<String,String>> RiskInfo = (List) requestXml.get("resultlist");
				
				try {
					for (int i = 0; i < RiskInfo.size(); i++) {
						if(!RiskInfo.get(i).get("Occurarea").isEmpty()){
							String Occurarea = String.valueOf(RiskInfo.get(i).get("Occurarea"));
							String queryDictionary = queryDictionary(Occurarea);
							RiskInfo.get(i).put("OccurareaTxt", queryDictionary(Occurarea));
						}
						Map JieMiMap = new HashMap();
						if(!RiskInfo.get(i).get("CusName").isEmpty()){
							JieMiMap.put("CusName", String.valueOf(RiskInfo.get(i).get("CusName")));// 商户名称
						}
						if(!RiskInfo.get(i).get("LegDocCode").isEmpty()){
							JieMiMap.put("LegDocCode", String.valueOf(RiskInfo.get(i).get("LegDocCode")));// 身份证件号码
						}
						if(!RiskInfo.get(i).get("BankNo").isEmpty()){
							JieMiMap.put("BankNo", String.valueOf(RiskInfo.get(i).get("BankNo")));// 银行结算账号
						}
						if(!RiskInfo.get(i).get("MobileNo").isEmpty()){
							JieMiMap.put("MobileNo", String.valueOf(RiskInfo.get(i).get("MobileNo")));// 法定代表人手机号。
						}
						String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
						String DecryptPWD = ParamPropUtils.props.getProperty("DecryptPWD");
						decrypt = PaymentRiskUtil.decrypt(SecretKey, JieMiMap,pfx,DecryptPWD);
						if(decrypt!=null){
							RiskInfo.get(i).putAll(decrypt);
						}
					}
				} catch (Exception e) {
					//解密失败；
					Map resultMsg=new HashMap();
					resultMsg.put("error", "响应数据解密失败！");
					e.printStackTrace();
					//return resultMsg;
				}
				requestXml.put("resultlist", RiskInfo);		
				return requestXml;
	}


	@Override
	public Map updateMerchantChange(PaymentRiskBean paymentRiskBean) throws Exception {
		Map map1 = new LinkedHashMap();
		map1.put("Version", "V1.0.1");
		String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
		.format(new java.util.Date());
		String substring = date.substring(0, 8);
		//获取10位随机数
		String randomString = String.valueOf(Math.random()).substring(2, 12);
		map1.put("Identification", date.substring(0, 8)+randomString);//报文唯一标识（8位日期+10顺序号）
		map1.put("OrigSender", paymentRiskBean.getOrigSender());// 会员单位机构号（字母、数字、下划线）
		map1.put("OrigSenderSID", "HrtApp2014");// 会员单位发送系统号（字母、数字、下划线）
		map1.put("RecSystemId", "R0001");// 协会系统编号
		map1.put("TrnxCode", "UP0004");//
		map1.put("TrnxTime", date);
		map1.put("UserToken", paymentRiskBean.getUserToken());
		map1.put("SecretKey", "1");//密钥
		
		Map map2 = new LinkedHashMap();
		map2.put("Id", paymentRiskBean.getId());// 风险信息数据库唯一标识
		map2.put("UpdateType", paymentRiskBean.getUpdateType());// 变更类型（补录、失效）
		map2.put("CusType", paymentRiskBean.getCusType());
		map2.put("CusProperty", paymentRiskBean.getCusProperty());//、、客户属性（默认值：01）
		map2.put("RiskType", paymentRiskBean.getRiskType());// Y风险类型01：虚假申请（受害人信息）02：虚假申请（嫌疑人信息）
		map2.put("CusNature",paymentRiskBean.getCusNature());//商户属性，参见数据字典定义（商户属性）
		map2.put("CusName", paymentRiskBean.getCusName());//商户名称
		map2.put("RegName", paymentRiskBean.getRegName());
		map2.put("CusCode", paymentRiskBean.getCusCode());//商户编码
		map2.put("OrgCode", paymentRiskBean.getOrgCode());//组织机构代码
		map2.put("BusLicCode", paymentRiskBean.getBusLicCode());//营业执照编码
		map2.put("SocialUnityCreditCode", paymentRiskBean.getSocialUnityCreditCode());// 统一社会信用代码
		map2.put("TaxRegCer", paymentRiskBean.getTaxRegCer());//税务登记证
		map2.put("LegRepName", paymentRiskBean.getLegRepName());//法定代表人姓名
		map2.put("LegDocCode", paymentRiskBean.getLegDocCode());//法人证件号码
		map2.put("BankNo", paymentRiskBean.getBankNo());
		map2.put("OpenBank", paymentRiskBean.getOpenBank());
		map2.put("Url", paymentRiskBean.getUrl());
		map2.put("ServerIp", paymentRiskBean.getServerIp());//ip
		String date2 = new java.text.SimpleDateFormat("yyyy-MM-dd")
				.format(new java.util.Date());
		map2.put("MobileNo", paymentRiskBean.getMobileNo());// 
		map2.put("Address", paymentRiskBean.getAddress());// 
		map2.put("Icp", paymentRiskBean.getIcp());// 
		map2.put("Level", paymentRiskBean.getLevel());// 信息级别Y
		map2.put("Occurtimeb", paymentRiskBean.getOccurtimeb());// 风险事件发生时间，时间格式，
		map2.put("Occurtimee", paymentRiskBean.getOccurtimee());// 风险事件结束时间，时间格式，
		map2.put("Occurchan", paymentRiskBean.getOccurchan());// 发生渠道
		String occurarea = paymentRiskBean.getOccurarea();
		if(null==occurarea){
			occurarea="";
		}else{
			occurarea=occurarea.replace(" ", "");
		}
		map2.put("Occurarea", occurarea);// 风险事件发生地域，省级/地市，可多选
		map2.put("Note", paymentRiskBean.getNote());// 风险事件描述
		String date3 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date());
		map2.put("ValidDate", date2);// 上报日期 格式：yyyy-MM-DD HH:mm:ssY
		map2.put("OrgId", paymentRiskBean.getOrgId());//
		map2.put("RepDate", date3);//
		map2.put("RepType", "03");// 上传方式（值：03Y
		map2.put("RepPerson", paymentRiskBean.getRepPerson());// 上传人Y
		
		String xmlhead = PaymentRiskUtil.xmlByMap(map1);
		String xmlbody = PaymentRiskUtil.xmlByMap(map2);
		xmlhead = "<Head>" + xmlhead + "</Head>";
		xmlbody = "<Body><PcacList><Count>1</Count><RiskInfo>" + xmlbody + "</RiskInfo></PcacList></Body>";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
				+ xmlhead
				+ xmlbody
				+ "</Request><Signature></Signature></Document>";
		String xsdPath = ParamPropUtils.props.getProperty("RiskXSD")+"pcac.ries.019.xsd";
		// 校验方法
		boolean checkXDS = false;
		try {
			checkXDS = PaymentRiskUtil.checkXDS(xml,xsdPath);
		} catch (SAXException e1) {
			Map resultMsg=new HashMap();
			resultMsg.put("error", "录入数据校验未通过！");
			e1.printStackTrace();
			return resultMsg;
		}				
		// 加密
		Map getMM = new HashMap();
		if(!paymentRiskBean.getCusName().isEmpty()){
			getMM.put("CusName", paymentRiskBean.getCusName());
		}
		if(!paymentRiskBean.getLegDocCode().isEmpty()){
			getMM.put("LegDocCode", paymentRiskBean.getLegDocCode());
		}
		if(!paymentRiskBean.getBankNo().isEmpty()){
			getMM.put("BankNo", paymentRiskBean.getBankNo());
		}
		if(!paymentRiskBean.getMobileNo().isEmpty()){
			getMM.put("MobileNo", paymentRiskBean.getMobileNo());
		}
		String RiskPKC = ParamPropUtils.props.getProperty("RiskPKCertificate");
		Map resultMap = PaymentRiskUtil.getAEStoString(getMM,RiskPKC);
		//Map resultMap = PaymentRiskUtil.getAEStoString(getMM,"E:\\HrtApp\\src\\data\\协会公钥证书.cer");
		// 拼装加密后xml
		map1.put("SecretKey", resultMap.get("encryptedKey"));
		xmlhead =  PaymentRiskUtil.xmlByMap(map1);
		xmlhead = "<Head>" + xmlhead + "</Head>";
		if(!paymentRiskBean.getCusName().isEmpty()){
			map2.put("CusName", resultMap.get("CusName"));
		}
		if(!paymentRiskBean.getLegDocCode().isEmpty()){
			map2.put("LegDocCode", resultMap.get("LegDocCode"));
		}
		if(!paymentRiskBean.getBankNo().isEmpty()){
			map2.put("BankNo", resultMap.get("BankNo"));
		}
		if(!paymentRiskBean.getMobileNo().isEmpty()){
			map2.put("MobileNo", resultMap.get("MobileNo"));
		}
		xmlbody = PaymentRiskUtil.xmlByMap(map2);
		xmlbody = "<Body><PcacList><Count>1</Count><RiskInfo>"+xmlbody+"</RiskInfo></PcacList></Body>";
		// 加密后xml
		xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"+xmlhead+xmlbody+"</Request><Signature></Signature></Document>";
		// 加签
		try {
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
					+ xmlhead + xmlbody + "</Request></Document>";
			String pfx = ParamPropUtils.props.getProperty("MemberUnitsPFX");
			String signPWD = ParamPropUtils.props.getProperty("signPWD");
			String addQian = PaymentRiskUtil.AddQian(xml,pfx,signPWD);
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><Request>"
					+ xmlhead
					+ xmlbody
					+ "</Request><Signature>"
					+ addQian
					+ "</Signature></Document>";
		} catch (Exception e) {
			e.printStackTrace();
		}
		//发送请求
		String url = ParamPropUtils.props.getProperty("RiskElseUrl");
		String	Result = PaymentRiskUtil.doPost(url,xml);
		
		Map readMap = new HashMap();
		Map requestXml = PaymentRiskUtil.requestXml(Result, readMap);
		return requestXml;
	}
	
	public List queryDictionary1(String menuid) throws Exception{
		String hql = "from DictionaryModel m where m.parent = 1 ";
		if (menuid != null) {
			hql = "from DictionaryModel m where m.parent.code = '"
					+ menuid+"'";
		}
		hql += " order by m.value asc";
		List<DictionaryModel> Dictionarylist = dictionaryDao.queryObjectsByHqlList(hql);
		List<TreeNodeBean> treeNodeList = new ArrayList<TreeNodeBean>();
		for (DictionaryModel menu : Dictionarylist) {
			treeNodeList.add(this.changeToTreeNode(menu, true));
		}

		return treeNodeList;
	}
	
	private TreeNodeBean changeToTreeNode(DictionaryModel menu, boolean recursive) {
		TreeNodeBean treeNodeBean = new TreeNodeBean();
		treeNodeBean.setId(menu.getCode() + "");
		treeNodeBean.setText(menu.getValue());

		if (menu.getChildren() != null && menu.getChildren().size() > 0) {
			treeNodeBean.setState("closed");
			if (recursive) {
				Set<DictionaryModel> set = menu.getChildren();
				List<TreeNodeBean> list = new ArrayList<TreeNodeBean>();
				for (DictionaryModel dictionaryModel : set) {
					TreeNodeBean treeNode = changeToTreeNode(dictionaryModel, true);
					list.add(treeNode);
				}
				treeNodeBean.setChildren(list);
				treeNodeBean.setState("closed");
			}
		}

		return treeNodeBean;
	}
	
	private String queryDictionary(String Occurarea){
		String result="";
		String sql = " select value from risk_dictionary where code in ("+Occurarea+")";
		List<Map> list = paymentRiskDao.queryObjectsBySqlList(sql);
		for (int i = 0; i < list.size(); i++) {
			if(i==list.size()-1){
				result += list.get(i).get("VALUE");
			}else{
				result += list.get(i).get("VALUE")+",";
			}
		}
		return result;
	}
	/**
	 * 查找响应码字典
	 * */
	public String queryResult(String ResultCode){
		Map param= new HashMap();
		param.put("code", ResultCode);
		String sql="select value from risk_result where code = :code";
		List<Map<String,Object>> list = paymentRiskDao.executeSql2(sql, param);
		String value = (String) list.get(0).get("VALUE");
		return value;
	}
	
}
