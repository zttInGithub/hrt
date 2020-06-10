package com.hrt.biz.credit.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.check.entity.model.CheckWechatTxnDetailModel;
import com.hrt.biz.credit.dao.CreditAgentDao;
import com.hrt.biz.credit.entity.pagebean.CreditInfoBean;
import com.hrt.biz.credit.service.CreditAgentService;
import com.hrt.biz.util.JSONObject;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;
import com.hrt.util.HttpXmlClient;
import com.hrt.util.word;

public class CreditAgentServiceImpl implements CreditAgentService {

	private CreditAgentDao creditAgentDao;
	private IUnitDao unitDao;
	private IMerchantInfoService merchantInfoService; 
	private IMerchantInfoDao merchantInfoDao;
	private IAgentSalesDao agentSalesDao;
	private String creditUrl;
	
	private static final Log log = LogFactory.getLog(CreditAgentServiceImpl.class);
	
	public String getCreditUrl() {
		return creditUrl;
	}

	public void setCreditUrl(String creditUrl) {
		this.creditUrl = creditUrl;
	}

	public IAgentSalesDao getAgentSalesDao() {
		return agentSalesDao;
	}

	public void setAgentSalesDao(IAgentSalesDao agentSalesDao) {
		this.agentSalesDao = agentSalesDao;
	}

	public IMerchantInfoDao getMerchantInfoDao() {
		return merchantInfoDao;
	}

	public void setMerchantInfoDao(IMerchantInfoDao merchantInfoDao) {
		this.merchantInfoDao = merchantInfoDao;
	}

	public IUnitDao getUnitDao() {
		return unitDao;
	}

	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}

	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	public CreditAgentDao getCreditAgentDao() {
		return creditAgentDao;
	}

	public void setCreditAgentDao(CreditAgentDao creditAgentDao) {
		this.creditAgentDao = creditAgentDao;
	}

	/**
	 * 查询机构额度
	 */
	public Map<String,String> queryAvailableLimit(CreditInfoBean creditInfoBean, UserBean user){
		
//		List list = new ArrayList();
		Map<String,String> params = new HashMap<String, String>();
		Map<String,String> rtnMap = new HashMap<String, String>();
		params.put("unno",user.getUnNo());
		String json = JSON.toJSONString(params);
		try{
			log.info("查询机构额度请求 "+json);
			String ss=HttpXmlClient.postForJson(creditUrl+"/credit/limitInfo_queryAvailableLimit.action", json);
//			JSONObject jsonObject = new JSONObject(ss);
//			String status =(String)jsonObject.get("status");//"1"失败 0成功
//			String msg =(String) jsonObject.get("msg");
//			String rate =(String) jsonObject.get("RATE");//利率
//			String baseLimit =(String) jsonObject.get("BASELIMIT");//可用额度
			//log.info("查询机构额度返回：状态("+status+");信息 ("+msg+");利率("+rate+");可用额度("+baseLimit+")");
			rtnMap = (Map<String,String> )JSON.parse(ss);
		}catch (Exception e) {
			log.info("查询机构额度 异常 "+e);
		}
		return rtnMap;
	}
	
	/**
	 * 查询贷款记录
	 */
	public DataGridBean queryCreditInfoData(CreditInfoBean creditInfoBean, UserBean user){
		
		DataGridBean dataGridBean = new DataGridBean();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("unno",user.getUnNo());
		params.put("pageNum",creditInfoBean.getPage());
		params.put("rowPerPage",creditInfoBean.getRows());
		if(creditInfoBean.getPROTOCOLNO()!=null&&!"".equals(creditInfoBean.getPROTOCOLNO())){
			params.put("protocolNo",creditInfoBean.getPROTOCOLNO());
		}
		String json = JSON.toJSONString(params);
		try{
			log.info("查询贷款记录请求"+json);
			String ss=HttpXmlClient.postForJson(creditUrl+"/credit/creInfo_queryCreInfoForApp.action", json);
			dataGridBean= CreditInfoBean.XmlToOb(ss);
		}catch (Exception e) {
			log.info("查询贷款记录异常 "+e);
		}
		return dataGridBean;
	}
	
	/**
	 * 报单代理开通推送到贷款系统
	 */
	@Override
	public boolean saveCreditAgent(AgentUnitModel agentUnitModel, UserBean user){
		boolean flag = false;
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("unno",agentUnitModel.getUnno());
		params.put("agentName",agentUnitModel.getAgentName());
		params.put("baddr",agentUnitModel.getBaddr());
		params.put("legalPerson",agentUnitModel.getLegalPerson());
		params.put("legalType",agentUnitModel.getLegalType());
		params.put("legalNum",agentUnitModel.getLegalNum());
		params.put("accType",agentUnitModel.getAccType());
		params.put("bankBranch",agentUnitModel.getBankBranch());
		params.put("bankAccNo",agentUnitModel.getBankAccNo());
		params.put("bankAccName",agentUnitModel.getBankAccName());
		params.put("bno",agentUnitModel.getBno());
		params.put("rno",agentUnitModel.getRno());
		params.put("registryNo",agentUnitModel.getRegistryNo());
		params.put("bankOpenAcc",agentUnitModel.getBankOpenAcc());
		params.put("contact",agentUnitModel.getContact());
		params.put("contactTel",agentUnitModel.getContactTel());
		params.put("contactPhone",agentUnitModel.getContactPhone());
		params.put("cronym",agentUnitModel.getAgentShortNm()); //代理商简称
		String json = JSON.toJSONString(params);
		try{
			log.info("报单代理开通推送到贷款系统请求 "+json);
			String ss=HttpXmlClient.postForJson(creditUrl+"/credit/creInfo_updateAgentInfo.action", json);
			JSONObject jsonObject = new JSONObject(ss);
			String status =(String)jsonObject.get("status");//"1"失败 0成功
			String msg =(String) jsonObject.get("msg");
			if("0".equals(status)){
				flag = true;
			}
		}catch (Exception e) {
			log.info("报单代理开通推送到贷款系统异常 "+e);
		}
		return flag;
	}
	
	/**
	 * 微信交易明细导出所有
	 */
	@Override
	public HSSFWorkbook wechantTxnExport(CreditInfoBean creditInfoBean, UserBean user) {
		
		String sql="select w.*,m.unno,m.rname from check_wechattxndetail w  ,bill_merchantinfo m  where w.mid=m.mid ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		boolean date_flag = false ;
		//权限
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if(user.getUseLvl()==3){
			//	sql+=" and b.MID ='"+userBean.getLoginName()+"' ";
			//	sqlCount+= " and b.MID ='"+userBean.getLoginName()+"' ";
				sql+=" and w.MID in (select MID from  bill_MerchantInfo where  " +
				"PARENTMID= '"+user.getLoginName()+"' or MID ='"+user.getLoginName()+"')  ";
		}
		else{
			if("110000".equals(user.getUnNo())){
			}
			else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else{
				String agHql ="select ag from AgentSalesModel ag where ag.maintainType!='D' and ag.userID="+user.getUserID();
				List<AgentSalesModel> ifAgList = agentSalesDao.queryObjectsByHqlList(agHql);
				if(ifAgList.size()>0){
					//业务员 user.getUserID()
					sql+=" and m.busid="+ifAgList.get(0).getBusid();
				}else{
					//代理
					String childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
					sql+=" and m.unno IN ("+childUnno+" )";
				}
			}
		}
//		if (creditInfoBean.getUnno() != null&&!"".equals(creditInfoBean.getUnno())) {
//			sql +=" and m.unno =:unno ";
//			map.put("unno", creditInfoBean.getUnno());
//			date_flag = true ;
//		}
//		if (creditInfoBean.getMid() != null&&!"".equals(creditInfoBean.getMid())) {
//			sql +=" and w.mid like :mid ";
//			map.put("mid", '%'+creditInfoBean.getMid()+'%');
//			date_flag = true ;
//		}
//		if (creditInfoBean.getMer_orderId() != null&&!"".equals(creditInfoBean.getMer_orderId())) {
//			sql +=" and w.mer_orderId =:mer_orderId ";
//			map.put("mer_orderId", creditInfoBean.getMer_orderId());
//			date_flag = true ;
//		}
//		if (creditInfoBean.getStatus() != null&&!"".equals(creditInfoBean.getStatus())) {
//			sql +=" and w.status =:status ";
//			map.put("status", creditInfoBean.getStatus());
//			date_flag = true ;
//		}
//		if(creditInfoBean.getFiid()==null){
//		}else if (creditInfoBean.getFiid() == 10) {
//			sql +=" and w.fiid in (10,11,13) ";
////			map.put("fiid", creditInfoBean.getFiid());
//			date_flag = true ;
//		}else if (creditInfoBean.getFiid() == 11) {
//			sql +=" and w.fiid in (12) ";
//			date_flag = true ;
//		}
//		if (creditInfoBean.getTxnType() != null&&!"".equals(creditInfoBean.getTxnType())) {
//			sql +=" and w.txnType =:txnType ";
//			map.put("txnType", creditInfoBean.getTxnType());
//			date_flag = true ;
//		}
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		if (creditInfoBean.getCdateStart() != null && !creditInfoBean.getCdateStart().equals("")) {
//			sql +=" and trunc(w.cdate) >=to_date('"+df.format(creditInfoBean.getCdateStart())+"','yyyy-mm-dd')" ;
//		} 
//		
//		if (creditInfoBean.getCdateEnd() != null && !creditInfoBean.getCdateEnd().equals("")) {
//			sql +=" and trunc(w.cdate) <= to_date('"+df.format(creditInfoBean.getCdateEnd())+"','yyyy-mm-dd')" ;
//		}
//		if(date_flag==false&&(creditInfoBean.getCdateStart()== null &&creditInfoBean.getCdateEnd() == null )){
//			sql +=" and trunc(w.cdate) = trunc(sysdate-1) " ;
//		}
//		if (creditInfoBean.getSort() != null) {
//			sql += " ORDER BY " + creditInfoBean.getSort() + " " + creditInfoBean.getOrder();
//		}
		List<Map<String, Object>> data = creditAgentDao.queryObjectsBySqlListMap2(sql, map);
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNNO");
		excelHeader.add("MID");
		excelHeader.add("RNAME");
		excelHeader.add("MER_ORDERID");
		excelHeader.add("BK_ORDERID");
		excelHeader.add("FIID");
		excelHeader.add("DETAIL");
		excelHeader.add("TXNTYPE");
		excelHeader.add("TXNAMT");
		excelHeader.add("MDA");
		excelHeader.add("STATUS");
		excelHeader.add("CDATE");

		headMap.put("UNNO", "机构号");
		headMap.put("MID", "商户编号");
		headMap.put("RNAME", "商户名称");
		headMap.put("MER_ORDERID", "商户订单号");
		headMap.put("BK_ORDERID", "银行端订单号");
		headMap.put("FIID", "渠道");
		headMap.put("DETAIL", "商品名称");
		headMap.put("TXNTYPE", "交易类型");
		headMap.put("TXNAMT", "交易金额");
		headMap.put("MDA", "手续费");
		headMap.put("STATUS", "状态");
		headMap.put("CDATE", "交易日期");

		return ExcelUtil.export("微信交易明细导出资料", data, excelHeader, headMap);
	}
	
	
	/**
	 * 微信交易商户汇总 导出所有
	 */
	public HSSFWorkbook checkWechatTxnDetailExcelAll(CreditInfoBean creditInfoBean, UserBean user){
		
			String sql="select m.unno unno,m.mid,m.rname,w.FIID,count(decode(w.txnType,0,1)) TXNCOUNT," +
				" to_char(sum(decode(w.txnType,0,w.txnAmt,0)), 'FM999,999,999,999,990.00') TXNAMOUNT," +
				" to_char(sum(decode(w.txnType,0,w.mda,0)), 'FM999,999,999,999,990.00') MDA," +
				" count(decode(w.txnType,1,1)) REFUNDCOUNT, " +
				" to_char(sum(decode(w.txnType,1,w.txnAmt,0)), 'FM999,999,999,999,990.00') REFUNDAMT ," +
				" to_char(sum(decode(w.txnType,1,w.mda,0)), 'FM999,999,999,999,990.00') REFUNDMDA  ," +
				" to_char(sum(decode(w.txnType,0,w.txnAmt,0))-sum(decode(w.txnType,0,w.mda,0))-to_char(sum(decode(w.txnType,1,w.txnAmt,0))+sum(decode(w.txnType,1,w.mda,0))), 'FM999,999,999,999,990.00') MNAMT " +
				" from check_wechattxndetail w  ,bill_merchantinfo m  where w.mid=m.mid ";

			Map<String, Object> map = new HashMap<String, Object>();
			boolean date_flag = false ;
			//权限
			UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
			if(user.getUseLvl()==3){
				//	sql+=" and b.MID ='"+userBean.getLoginName()+"' ";
				//	sqlCount+= " and b.MID ='"+userBean.getLoginName()+"' ";
					sql+=" and w.MID in (select MID from  bill_MerchantInfo where  " +
					"PARENTMID= '"+user.getLoginName()+"' or MID ='"+user.getLoginName()+"')  ";
			}
			else{
				if("110000".equals(user.getUnNo())){
				}
				else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
					UnitModel parent = unitModel.getParent();
					if("110000".equals(parent.getUnNo())){
					}
				}else{
					String agHql ="select ag from AgentSalesModel ag where ag.maintainType!='D' and ag.userID="+user.getUserID();
					List<AgentSalesModel> ifAgList = agentSalesDao.queryObjectsByHqlList(agHql);
					if(ifAgList.size()>0){
						//业务员 user.getUserID()
						sql+=" and m.busid="+ifAgList.get(0).getBusid();
					}else{
						//代理
						String childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
						sql+=" and m.unno IN ("+childUnno+" )";
					}
				}
			}
			
//			if (creditInfoBean.getUnno() != null&&!"".equals(creditInfoBean.getUnno())) {
//				sql +=" and m.unno =:unno ";
//				map.put("unno", creditInfoBean.getUnno());
//				date_flag = true ;
//			}
//			if (creditInfoBean.getMid() != null&&!"".equals(creditInfoBean.getMid())) {
//				sql +=" and w.mid like :mid ";
//				map.put("mid", '%'+creditInfoBean.getMid()+'%');
//				date_flag = true ;
//			}
//			if (creditInfoBean.getMer_orderId() != null&&!"".equals(creditInfoBean.getMer_orderId())) {
//				sql +=" and w.mer_orderId =:mer_orderId ";
//				map.put("mer_orderId", creditInfoBean.getMer_orderId());
//				date_flag = true ;
//			}
//			if (creditInfoBean.getStatus() != null&&!"".equals(creditInfoBean.getStatus())) {
//				sql +=" and w.status =:status ";
//				map.put("status", creditInfoBean.getStatus());
//				date_flag = true ;
//			}
//			if(creditInfoBean.getFiid()==null){
//			}else if (creditInfoBean.getFiid() == 10) {
//				sql +=" and w.fiid in (10,11,13) ";
////				map.put("fiid", creditInfoBean.getFiid());
//				date_flag = true ;
//			}else if (creditInfoBean.getFiid() == 11) {
//				sql +=" and w.fiid in (12) ";
//				date_flag = true ;
//			}
//			if (creditInfoBean.getTxnType() != null&&!"".equals(creditInfoBean.getTxnType())) {
//				sql +=" and w.txnType =:txnType ";
//				map.put("txnType", creditInfoBean.getTxnType());
//				date_flag = true ;
//			}
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//			if (creditInfoBean.getCdateStart() != null && !creditInfoBean.getCdateStart().equals("")) {
//				sql +=" and trunc(w.cdate) >=to_date('"+df.format(creditInfoBean.getCdateStart())+"','yyyy-mm-dd')" ;
//			} 
//			
//			if (creditInfoBean.getCdateEnd() != null && !creditInfoBean.getCdateEnd().equals("")) {
//				sql +=" and trunc(w.cdate) <= to_date('"+df.format(creditInfoBean.getCdateEnd())+"','yyyy-mm-dd')" ;
//			}
//			if(date_flag==false&&(creditInfoBean.getCdateStart()== null &&creditInfoBean.getCdateEnd() == null )){
//				sql +=" and trunc(w.cdate) = trunc(sysdate-1) " ;
//			}
//			sql+="  group by m.unno ,m.mid,m.rname ,w.FIID";
//			
			List<Map<String, Object>> data = creditAgentDao.queryObjectsBySqlListMap2(sql, map);
			List<String> excelHeader = new ArrayList<String>();
			Map<String, Object> headMap = new HashMap<String, Object>();
			excelHeader.add("UNNO");
			excelHeader.add("MID");
			excelHeader.add("RNAME");
			excelHeader.add("FIID");
			excelHeader.add("TXNCOUNT");
			excelHeader.add("TXNAMOUNT");
			excelHeader.add("MDA");
			excelHeader.add("REFUNDCOUNT");
			excelHeader.add("REFUNDAMT");
			excelHeader.add("REFUNDMDA");
			excelHeader.add("MNAMT");

			headMap.put("UNNO", "机构号");
			headMap.put("MID", "商户编号");
			headMap.put("RNAME", "商户名称");
			headMap.put("FIID", "支付渠道");
			headMap.put("TXNCOUNT", "消费次数");
			headMap.put("TXNAMOUNT", "消费金额");
			headMap.put("MDA", "消费手续费");
			headMap.put("REFUNDCOUNT", "退款次数");
			headMap.put("REFUNDAMT", "退款金额");
			headMap.put("REFUNDMDA", "退款手续费");
			headMap.put("MNAMT", "结算金额");

			return ExcelUtil.export("微信交易商户汇总导出所有", data, excelHeader, headMap);
	}
	/**
	 * 微信交易机构汇总 导出所有
	 */
	public HSSFWorkbook checkWechatTxnUnitDetailExcelAll(CreditInfoBean creditInfoBean, UserBean user){
		
		String sql="select m.unno unno,s.un_name,w.FIID,count(decode(w.txnType,0,1)) TXNCOUNT," +
				" to_char(sum(decode(w.txnType,0,w.txnAmt,0)), 'FM999,999,999,999,990.00') TXNAMOUNT," +
				" count(distinct w.mid) HOTMERCOUNT " +
				" from check_wechattxndetail w  ,bill_merchantinfo m ,sys_unit s where w.mid=m.mid and m.unno=s.unno ";

		Map<String, Object> map = new HashMap<String, Object>();
		boolean date_flag = false ;
		
//		if (creditInfoBean.getUnno() != null&&!"".equals(creditInfoBean.getUnno())) {
//			sql +=" and m.unno =:unno ";
//			map.put("unno", creditInfoBean.getUnno());
//			date_flag = true ;
//		}
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		if (creditInfoBean.getCdateStart() != null && !creditInfoBean.getCdateStart().equals("")) {
//			sql +=" and trunc(w.cdate) >=to_date('"+df.format(creditInfoBean.getCdateStart())+"','yyyy-mm-dd')" ;
//		} 
//		
//		if (creditInfoBean.getCdateEnd() != null && !creditInfoBean.getCdateEnd().equals("")) {
//			sql +=" and trunc(w.cdate) <= to_date('"+df.format(creditInfoBean.getCdateEnd())+"','yyyy-mm-dd')" ;
//		}
//		if(date_flag==false&&(creditInfoBean.getCdateStart()== null &&creditInfoBean.getCdateEnd() == null )){
//			sql +=" and trunc(w.cdate) = trunc(sysdate-1) " ;
//		}
		sql+="  group by m.unno,s.un_name,w.FIID";
		
		List<Map<String, Object>> data = creditAgentDao.queryObjectsBySqlListMap2(sql, map);
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNNO");
		excelHeader.add("UN_NAME");
		excelHeader.add("FIID");
		excelHeader.add("TXNCOUNT");
		excelHeader.add("TXNAMOUNT");
		excelHeader.add("HOTMERCOUNT");
		
		headMap.put("UNNO", "机构号");
		headMap.put("UN_NAME", "商户名称");
		headMap.put("FIID", "支付渠道");
		headMap.put("TXNCOUNT", "消费次数");
		headMap.put("TXNAMOUNT", "消费金额");
		headMap.put("HOTMERCOUNT", "活跃商户");

		return ExcelUtil.export("微信交易机构汇总导出所有", data, excelHeader, headMap);
	}
	/**
	 * 方法功能：格式化MerchantAuthenticityModel为datagrid数据格式
	 * 参数：MerchantAuthenticityList
	 * 		total 总记录数
	 */
	private DataGridBean formatToDataGrid(List<CheckWechatTxnDetailModel> CheckWechatTxnList, long total) {
		List<CreditInfoBean> WechatTxnBeanList = new ArrayList<CreditInfoBean>();
		for(CheckWechatTxnDetailModel CheckWechatTxnDetailModel : CheckWechatTxnList) {
			CreditInfoBean WechatTxnABean = new CreditInfoBean();
			BeanUtils.copyProperties(CheckWechatTxnDetailModel, WechatTxnABean);
			String str = CheckWechatTxnDetailModel.getCdate().toString();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			//df.format(CheckWechatTxnDetailModel.getCdate();
			Date date;
			try {
				date = df.parse(str);
				//WechatTxnABean.setCdate(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//查询机构号
//			 List<MerchantInfoModel> list=merchantInfoDao.queryObjectsByHqlList("select m from MerchantInfoModel m where m.mid='"+WechatTxnABean.getMid()+"'");
//			 String unno = list.get(0).getUnno()+"";
//			 String rname = list.get(0).getRname()+"";
			
			WechatTxnBeanList.add(WechatTxnABean);
		}
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(WechatTxnBeanList);
		
		return dgb;
	}
	/**
	 * 微信交易退款
	 */
//	public boolean saveWechantTxnRefund(creditInfoBean cwtdb, UserBean user){
//		boolean flag = false;
//		//退款记录
//		CheckWechatTxnDetailModel reFund = new CheckWechatTxnDetailModel();
//		reFund.setOriPwid(cwtdb.getPwid());
//		reFund.setTxnAmt(cwtdb.getRefundAmt());
//		reFund.setStatus("0");
//		reFund.setTxnType("1");
//		reFund.setCdate(new Date());
//		reFund.setLmdate(new Date());
//		
//		reFund.setRefundDetail(cwtdb.getRefundDetail());
//		reFund.setFiid(cwtdb.getFiid());
//		reFund.setMid(cwtdb.getMid());
//		reFund.setMer_orderId(cwtdb.getMer_orderId());
//		reFund.setBk_orderId(cwtdb.getBk_orderId());
//		reFund.setDetail(cwtdb.getDetail());
//		
//		try{
//			//?先推后存？
//			Serializable id =checkWechantTxnDetailDao.saveObject(reFund);
//			//生产
//			//String admUrl="http://10.51.25.42:7002/CubeMPOSConsole/mpos/setIdssentpityVerify.do";
//			//测试
//			String admUrl="http://10.51.133.17:8080/CubeMPOSConsole/mpos/setIdesntidtyVerify.do";
//			Map<String, String> weChantTxnMap = new HashMap<String, String>();
//			
//			String ss=HttpXmlClient.post(admUrl, weChantTxnMap);
//			log.info("HYB请求返回信息："+ss);
//			flag=true;
//		}catch(Exception e){
//			log.error("微信交易退款异常:发送综合异常"+e);
//		}
//		return flag;
//	}

	/**
	 * 查看退款金额是否合规
	 */
//	public boolean queryIfWechantTxnRefund(creditInfoBean creditInfoBean){
//		boolean ifRefund = false ;
//		//本次退款
//		Double Refund = creditInfoBean.getRefundAmt();
//		//已经退款
//		Double yt = 0d ;
//		//原来交易额
//		Double txnAmt = creditInfoBean.getTxnAmt();
//		
//		String sql = "select sum(w.txnAmt) from check_wechattxndetail w where w.oriPwid="+creditInfoBean.getPwid()+" and w.txnType='1' ";
//		Object model = checkWechantTxnDetailDao.queryObjectBySql(sql, new HashMap<String, Object>());
//		if(model!=null){
//			yt = Double.valueOf((model+""));
//		}
//		if(Refund<=(txnAmt-yt)){
//			ifRefund = true ;
//		}
//		return ifRefund;
//	}
//	
	
}
