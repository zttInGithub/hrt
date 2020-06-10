package com.hrt.biz.check.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.bill.entity.model.HrtUnnoCostModel;
import com.hrt.biz.bill.service.IAgentUnitService;
import com.hrt.biz.check.dao.CheckUnitProfitMicroLogDao;
import com.hrt.biz.check.dao.CheckUnitProfitMicroWDao;
import com.hrt.biz.check.entity.model.CheckProfitTemplateMicroLogModel;
import com.hrt.biz.check.entity.model.CheckProfitTemplateMicroWModel;
import com.hrt.util.DateTools;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.internal.jaxb.mapping.hbm.SubEntityElement;

import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.check.dao.CheckUnitProfitMicroDao;
import com.hrt.biz.check.entity.model.CheckProfitTemplateMicroModel;
import com.hrt.biz.check.entity.pagebean.CheckProfitTemplateMicroBean;
import com.hrt.biz.check.service.CheckUnitProfitMicroService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;

import org.springframework.beans.BeanUtils;

public class CheckUnitProfitMicroServiceImpl implements CheckUnitProfitMicroService {

	private CheckUnitProfitMicroDao checkUnitProfitMicroDao;
	private CheckUnitProfitMicroWDao checkUnitProfitMicroWDao;
	private CheckUnitProfitMicroLogDao checkUnitProfitMicroLogDao;
	private IUnitDao unitDao;
	private IMerchantInfoService merchantInfoService;
	private IAgentUnitService agentUnitService;

	public CheckUnitProfitMicroDao getCheckUnitProfitMicroDao() {
		return checkUnitProfitMicroDao;
	}

	public void setCheckUnitProfitMicroDao(
			CheckUnitProfitMicroDao checkUnitProfitMicroDao) {
		this.checkUnitProfitMicroDao = checkUnitProfitMicroDao;
	}

	public CheckUnitProfitMicroLogDao getCheckUnitProfitMicroLogDao() {
		return checkUnitProfitMicroLogDao;
	}

	public void setCheckUnitProfitMicroLogDao(CheckUnitProfitMicroLogDao checkUnitProfitMicroLogDao) {
		this.checkUnitProfitMicroLogDao = checkUnitProfitMicroLogDao;
	}

	public CheckUnitProfitMicroWDao getCheckUnitProfitMicroWDao() {
		return checkUnitProfitMicroWDao;
	}

	public void setCheckUnitProfitMicroWDao(CheckUnitProfitMicroWDao checkUnitProfitMicroWDao) {
		this.checkUnitProfitMicroWDao = checkUnitProfitMicroWDao;
	}

	public IAgentUnitService getAgentUnitService() {
		return agentUnitService;
	}

	public void setAgentUnitService(IAgentUnitService agentUnitService) {
		this.agentUnitService = agentUnitService;
	}

	/**
	 * 查询分润模版
	 */
	public DataGridBean queryPROFITTEMPLATE(CheckProfitTemplateMicroBean cptm,UserBean userBean){
		String sql="select  p.TEMPNAME,decode(p.mataintype,'P','P','A') mataintype from CHECK_MICRO_PROFITTEMPLATE p where 1=1 and p.merchanttype in (1,2,3) ";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if("110000".equals(userBean.getUnNo())){
		 }
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and p.unno ='"+userBean.getUnNo()+"' " ;
		}
		if(cptm!=null&&cptm.getTempName()!=null&&!cptm.getTempName().equals("")){
			sql+="and  p.TEMPNAME='"+cptm.getTempName()+"' ";
		}
		sql+=" and MatainType in ('A','M','P') group by p.TEMPNAME,decode(p.mataintype,'P','P','A')  ";
		String count="select count(*) from ("+sql+")";
		List<Map<String,String>> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql,null,cptm.getPage(),cptm.getRows());
		BigDecimal counts = checkUnitProfitMicroDao.querysqlCounts(count,null);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());//查询数据库总条数
		dgd.setRows(list);
		return dgd;
	}
	/**
	 * 查询分润模版
	 */
	public DataGridBean querySubTemplate(CheckProfitTemplateMicroBean cptm,UserBean userBean){
		String sql="select  p.* from CHECK_MICRO_PROFITTEMPLATE p where merchanttype=4 ";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if("110000".equals(userBean.getUnNo())){
		 }
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and p.unno ='"+userBean.getUnNo()+"' " ;
		}
		if(cptm!=null&&cptm.getTempName()!=null&&!cptm.getTempName().equals("")){
			sql+="and  p.TEMPNAME='"+cptm.getTempName()+"' ";
		}
		sql+=" and MatainType in ('A','M','P') ";
		String count="select count(*) from ("+sql+")";
		List<Map<String,String>> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql,null,cptm.getPage(),cptm.getRows());
		BigDecimal counts = checkUnitProfitMicroDao.querysqlCounts(count,null);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());//查询数据库总条数
		dgd.setRows(list);
		return dgd;
	}

	/**
	 *
	 * @param type 1:代还实时生效查询 2:下月生效查询
	 * @param cptm
	 * @return
	 */
	public Map getReplayFirstDaySql(int type,CheckProfitTemplateMicroBean cptm){
		Map params = new HashMap();
		params.put("aptid", cptm.getAptId());
		if(type==1) {
			if (DateTools.isFirstDay()) {
				String sqlBefore = "select  p.* from CHECK_MICRO_PROFITTEMPLATE_W p where merchanttype=4 " +
						" and p.aptid=:aptid " +
						" and p.mataindate>=trunc(sysdate-2,'mm') and p.mataindate<trunc(sysdate)";
				List<Map<String, String>> list = checkUnitProfitMicroDao.queryObjectsBySqlListMap(sqlBefore, params);
				if (list.size() > 0) {
					return list.get(0);
				} else {
					String currentSql = "select  p.* from CHECK_MICRO_PROFITTEMPLATE p where merchanttype=4 " +
							" and p.aptid=:aptid ";
					List<Map<String, String>> currentList = checkUnitProfitMicroDao.queryObjectsBySqlListMap(currentSql, params);
					if (currentList.size() > 0) {
						return currentList.get(0);
					}
				}
			}else{
				String currentSql = "select p.* from CHECK_MICRO_PROFITTEMPLATE p where merchanttype=4 " +
						" and p.aptid=:aptid ";
				List<Map<String, String>> currentList = checkUnitProfitMicroDao.queryObjectsBySqlListMap(currentSql, params);
				if (currentList.size() > 0) {
					return currentList.get(0);
				}
			}
		}else if(type==2){
			String NextSql = "select p.* from CHECK_MICRO_PROFITTEMPLATE_W p where merchanttype=4 " +
					" and p.aptid=:aptid and p.mataindate>=trunc(sysdate,'mm') ";
			List<Map<String, String>> nextList = checkUnitProfitMicroDao.queryObjectsBySqlListMap(NextSql, params);
			if (nextList.size() > 0) {
				return nextList.get(0);
			}

			if (DateTools.isFirstDay()) {
				String sqlBefore = "select  p.* from CHECK_MICRO_PROFITTEMPLATE_W p where merchanttype=4 " +
						" and p.aptid=:aptid " +
						" and p.mataindate>=trunc(sysdate-2,'mm') and p.mataindate<trunc(sysdate)";
				List<Map<String, String>> list = checkUnitProfitMicroDao.queryObjectsBySqlListMap(sqlBefore, params);
				if (list.size() > 0) {
					return list.get(0);
				} else {
					String currentSql = "select  p.* from CHECK_MICRO_PROFITTEMPLATE p where merchanttype=4 " +
							" and p.aptid=:aptid ";
					List<Map<String, String>> currentList = checkUnitProfitMicroDao.queryObjectsBySqlListMap(currentSql, params);
					if (currentList.size() > 0) {
						return currentList.get(0);
					}
				}
			}else{
				String currentSql = "select  p.* from CHECK_MICRO_PROFITTEMPLATE p where merchanttype=4 " +
						" and p.aptid=:aptid ";
				List<Map<String, String>> currentList = checkUnitProfitMicroDao.queryObjectsBySqlListMap(currentSql, params);
				if (currentList.size() > 0) {
					return currentList.get(0);
				}
			}
		}
		return new HashMap();
	}

	/**
	 * 查询收银台分润模版
	 */
	public DataGridBean querySytTemplate(CheckProfitTemplateMicroBean cptm,UserBean userBean){
		String sql="select  p.tempname from CHECK_MICRO_PROFITTEMPLATE p where merchanttype=5  ";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if("110000".equals(userBean.getUnNo())){
		 }
		//为总公司并且是部门---查询全部
		else if((unitModel.getUnAttr()!=null&&unitModel.getUnLvl()!=null)&&(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0)){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and p.unno ='"+userBean.getUnNo()+"' " ;
		}
		if(cptm!=null&&cptm.getTempName()!=null&&!cptm.getTempName().equals("")){
			sql+="and  p.TEMPNAME='"+cptm.getTempName()+"' ";
		}
		sql+=" and MatainType in ('A','M','P') group by p.tempname ";
		String count="select count(*) from ("+sql+")";
		List<Map<String,String>> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql,null,cptm.getPage(),cptm.getRows());
		BigDecimal counts = checkUnitProfitMicroDao.querysqlCounts(count,null);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());//查询数据库总条数
		dgd.setRows(list);
		return dgd;
	}
	/**
	 * 查询收银台分润模版(修改时)---本月
	 */
	public List<Map<String, Object>> querySytCurrentMonthTemplate(CheckProfitTemplateMicroBean cptm,UserBean userBean){
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		List<Map<String, Object>> lst = new ArrayList<Map<String,Object>>();
		Map params = new HashMap();
		params.put("tempname",cptm.getTempname());
		//判断是否是本月1号【由于_w表数据是2号跑，所以这两个表代表的含义有个时间差，所以需要判断是否为1号】---20190925
		if(DateTools.isFirstDay()) {
			//判断是否_w表中存在数据（mataindate时间为上个月）---20190925
			String sql="select  "+
			" p.wid,\r\n" + 
			"p.aptid,\r\n" + 
			"p.unno,\r\n" + 
			"p.merchanttype,\r\n" + 
			"p.profittype,\r\n" + 
			"nvl(p.profitrule,'21') profitrule,\r\n" + 
			"p.startamount,\r\n" + 
			"p.endamount,\r\n" + 
			"p.rulethreshold,\r\n" + 
			"p.profitpercent,\r\n" + 
			"p.mataintype,\r\n" + 
			"p.mataindate,\r\n" + 
			"p.tempname,\r\n" + 
			"p.matainuserid,\r\n" + 
			"p.settmethod,\r\n" + 
			"p.creditbankrate,\r\n" + 
			"p.cashrate,\r\n" + 
			"p.cashamt,\r\n" + 
			"p.scanrate,\r\n" + 
			"p.cloudquickrate,\r\n" + 
			"p.subrate,\r\n" + 
			"p.scanrate1,\r\n" + 
			"p.scanrate2,\r\n" + 
			"p.cashamt1,\r\n" + 
			"p.cashamt2,\r\n" + 
			"p.profitpercent1,\r\n" + 
			"p.cashamtabove,\r\n" + 
			"p.cashamtunder,\r\n" + 
			"p.huabeirate,\r\n" + 
			"p.huabeifee"+
			" from CHECK_MICRO_PROFITTEMPLATE_W p where merchanttype=5 and p.tempname=:tempname "+
			" and MatainType in ('A','M','P') "+
			" and p.mataindate <= trunc(sysdate,'mm')"+
			" and p.mataindate >= add_months(trunc(sysdate,'mm'),-1) ";
			
			//为总公司
			if("110000".equals(userBean.getUnNo())){
			 }
			//为总公司并且是部门---查询全部
			else if((unitModel.getUnAttr()!=null&&unitModel.getUnLvl()!=null)&&(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0)){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else{
				sql+=" and p.unno ='"+userBean.getUnNo()+"' " ;
			}
			String count="select count(*) from ("+sql+")";
			BigDecimal counts = checkUnitProfitMicroWDao.querysqlCounts(count,params);
			if(counts.intValue() > 0) {
				//显示_w表中模板数据（代表的就是现在当前月的数据）---20190925
				List<CheckProfitTemplateMicroWModel> list=checkUnitProfitMicroWDao.queryObjectsBySqlList(sql, params,CheckProfitTemplateMicroWModel.class );
				for(int i=0;i<list.size();i++){
		            CheckProfitTemplateMicroWModel cc =list.get(i);
		            Map<String, Object> map = new HashMap<String, Object>();
		            map.put("profitRule",cc.getProfitRule());
		            
		            map.put("creditBankRate", null==cc.getCreditBankRate()?"":new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100")));
		            map.put("cashRate", null==cc.getCashRate()?"":cc.getCashRate());
		            map.put("scanRate", null==cc.getScanRate()?"":new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
		            map.put("profitPercent1", null==cc.getProfitPercent1()?"":cc.getProfitPercent1());
		            map.put("subRate", null==cc.getSubRate()?"":new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
		            map.put("cashAmt", null==cc.getCashAmt()?"":cc.getCashAmt());
		            map.put("cashAmt1", null==cc.getCashAmt1()?"":cc.getCashAmt1());
		            map.put("scanRate1", null==cc.getScanRate1()?"":new BigDecimal(cc.getScanRate1()+"").multiply(new BigDecimal("100")));
		            map.put("scanRate2", null==cc.getScanRate2()?"":new BigDecimal(cc.getScanRate2()+"").multiply(new BigDecimal("100")));
		            map.put("cashAmt2", null==cc.getCashAmt2()?"":cc.getCashAmt2());
		            map.put("huabeiRate", null==cc.getHuabeiRate()?"":new BigDecimal(cc.getHuabeiRate()+"").multiply(new BigDecimal("100")));
		            map.put("huabeiFee", null==cc.getHuabeiFee()?"":cc.getHuabeiFee());
		            
		            String ishuabei = querySubTypeByProfitRule(cc.getProfitRule());
		            if(ishuabei!=null) {
		            	map.put("ishuabei", 1);
		            }else {
		            	map.put("ishuabei", 2);
		            }
		            lst.add(map);
		        }
		        return lst;
			}else {
				//显示实时表中的数据（代表的是上个月时，未操作下个月的_w表。即上个月实时表中的数据依旧作为当前月的成本）--20190925
				String currentSql="select  "
			    +" p.aptid,\r\n" + 
			    "p.unno,\r\n" + 
			    "p.merchanttype,\r\n" + 
			    "p.profittype,\r\n" + 
			    "nvl(p.profitrule,'21') profitrule,\r\n" + 
			    "p.startamount,\r\n" + 
			    "p.endamount,\r\n" + 
			    "p.rulethreshold,\r\n" + 
			    "p.profitpercent,\r\n" + 
			    "p.mataintype,\r\n" + 
			    "p.mataindate,\r\n" + 
			    "p.tempname,\r\n" + 
			    "p.matainuserid,\r\n" + 
			    "p.settmethod,\r\n" + 
			    "p.creditbankrate,\r\n" + 
			    "p.cashrate,\r\n" + 
			    "p.cashamt,\r\n" + 
			    "p.scanrate,\r\n" + 
			    "p.cloudquickrate,\r\n" + 
			    "p.subrate,\r\n" + 
			    "p.scanrate1,\r\n" + 
			    "p.scanrate2,\r\n" + 
			    "p.cashamt1,\r\n" + 
			    "p.cashamt2,\r\n" + 
			    "p.profitpercent1,\r\n" + 
			    "p.cashamtabove,\r\n" + 
			    "p.cashamtunder,\r\n" + 
			    "p.huabeirate,\r\n" + 
			    "p.huabeifee"
				+" from CHECK_MICRO_PROFITTEMPLATE p where merchanttype=5"+
						" and p.MatainType in ('A','M','P') and p.tempname=:tempname  ";
				//为总公司
				if("110000".equals(userBean.getUnNo())){
				 }
				//为总公司并且是部门---查询全部
				else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
					UnitModel parent = unitModel.getParent();
					if("110000".equals(parent.getUnNo())){
					}
				}else{
					currentSql+=" and p.unno ='"+userBean.getUnNo()+"' " ;
				}
				
				List<CheckProfitTemplateMicroModel> list=checkUnitProfitMicroDao.queryObjectsBySqlList(currentSql, params,CheckProfitTemplateMicroModel.class );
				for(int i=0;i<list.size();i++){
		            CheckProfitTemplateMicroModel cc =list.get(i);
		            Map<String, Object> map = new HashMap<String, Object>();
		            map.put("profitRule",cc.getProfitRule());
		            
		            map.put("creditBankRate", null==cc.getCreditBankRate()?"":new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100")));
		            map.put("cashRate", null==cc.getCashRate()?"":cc.getCashRate());
		            map.put("scanRate", null==cc.getScanRate()?"":new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
		            map.put("profitPercent1", null==cc.getProfitPercent1()?"":cc.getProfitPercent1());
		            map.put("subRate", null==cc.getSubRate()?"":new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
		            map.put("cashAmt", null==cc.getCashAmt()?"":cc.getCashAmt());
		            map.put("cashAmt1", null==cc.getCashAmt1()?"":cc.getCashAmt1());
		            map.put("scanRate1", null==cc.getScanRate1()?"":new BigDecimal(cc.getScanRate1()+"").multiply(new BigDecimal("100")));
		            map.put("scanRate2", null==cc.getScanRate2()?"":new BigDecimal(cc.getScanRate2()+"").multiply(new BigDecimal("100")));
		            map.put("cashAmt2", null==cc.getCashAmt2()?"":cc.getCashAmt2());
		            map.put("huabeiRate", null==cc.getHuabeiRate()?"":new BigDecimal(cc.getHuabeiRate()+"").multiply(new BigDecimal("100")));
		            map.put("huabeiFee", null==cc.getHuabeiFee()?"":cc.getHuabeiFee());
		            
		            String ishuabei = querySubTypeByProfitRule(cc.getProfitRule());
		            if(ishuabei!=null) {
		            	map.put("ishuabei", 1);
		            }else {
		            	map.put("ishuabei", 2);
		            }
		            lst.add(map);
		        }
		        return lst;
			}
		}else {
			//不是1号直接去实时表获取相应成本
			String currentSql="select  "
			+" p.aptid,\r\n" + 
			"p.unno,\r\n" + 
			"p.merchanttype,\r\n" + 
			"p.profittype,\r\n" + 
			"nvl(p.profitrule,'21') profitrule,\r\n" + 
			"p.startamount,\r\n" + 
			"p.endamount,\r\n" + 
			"p.rulethreshold,\r\n" + 
			"p.profitpercent,\r\n" + 
			"p.mataintype,\r\n" + 
			"p.mataindate,\r\n" + 
			"p.tempname,\r\n" + 
			"p.matainuserid,\r\n" + 
			"p.settmethod,\r\n" + 
			"p.creditbankrate,\r\n" + 
			"p.cashrate,\r\n" + 
			"p.cashamt,\r\n" + 
			"p.scanrate,\r\n" + 
			"p.cloudquickrate,\r\n" + 
			"p.subrate,\r\n" + 
			"p.scanrate1,\r\n" + 
			"p.scanrate2,\r\n" + 
			"p.cashamt1,\r\n" + 
			"p.cashamt2,\r\n" + 
			"p.profitpercent1,\r\n" + 
			"p.cashamtabove,\r\n" + 
			"p.cashamtunder,\r\n" + 
			"p.huabeirate,\r\n" + 
			"p.huabeifee"
			+" from CHECK_MICRO_PROFITTEMPLATE p where merchanttype=5"+
					" and p.MatainType in ('A','M','P') and p.tempname=:tempname  ";
			//为总公司
			if("110000".equals(userBean.getUnNo())){
			 }
			//为总公司并且是部门---查询全部
			else if((unitModel.getUnAttr()!=null&&unitModel.getUnLvl()!=null)&&(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0)){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else{
				currentSql+=" and p.unno ='"+userBean.getUnNo()+"' " ;
			}
			List<CheckProfitTemplateMicroModel> list=checkUnitProfitMicroDao.queryObjectsBySqlList(currentSql, params,CheckProfitTemplateMicroModel.class );
			for(int i=0;i<list.size();i++){
	            CheckProfitTemplateMicroModel cc =list.get(i);
	            Map<String, Object> map = new HashMap<String, Object>();
	            map.put("profitRule",cc.getProfitRule());
	            
	            map.put("creditBankRate", null==cc.getCreditBankRate()?"":new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100")));
	            map.put("cashRate", null==cc.getCashRate()?"":cc.getCashRate());
	            map.put("scanRate", null==cc.getScanRate()?"":new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
	            map.put("profitPercent1", null==cc.getProfitPercent1()?"":cc.getProfitPercent1());
	            map.put("subRate", null==cc.getSubRate()?"":new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
	            map.put("cashAmt", null==cc.getCashAmt()?"":cc.getCashAmt());
	            map.put("cashAmt1", null==cc.getCashAmt1()?"":cc.getCashAmt1());
	            map.put("scanRate1", null==cc.getScanRate1()?"":new BigDecimal(cc.getScanRate1()+"").multiply(new BigDecimal("100")));
	            map.put("scanRate2", null==cc.getScanRate2()?"":new BigDecimal(cc.getScanRate2()+"").multiply(new BigDecimal("100")));
	            map.put("cashAmt2", null==cc.getCashAmt2()?"":cc.getCashAmt2());
	            map.put("huabeiRate", null==cc.getHuabeiRate()?"":new BigDecimal(cc.getHuabeiRate()+"").multiply(new BigDecimal("100")));
	            map.put("huabeiFee", null==cc.getHuabeiFee()?"":cc.getHuabeiFee());
	            
	            String ishuabei = querySubTypeByProfitRule(cc.getProfitRule());
	            if(ishuabei!=null) {
	            	map.put("ishuabei", 1);
	            }else {
	            	map.put("ishuabei", 2);
	            }
	            lst.add(map);
	        }
	        return lst;
		}
	}
	
	/**
	 * 查询收银台分润模版(修改时)---下月
	 */
	public List<Map<String, Object>> querySytNextMonthTemplate(CheckProfitTemplateMicroBean cptm,UserBean userBean){
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		Map params = new HashMap();
		params.put("tempname",cptm.getTempname());
		List<Map<String, Object>> lst = new ArrayList<Map<String,Object>>();
		String unnoSql = "";
		//为总公司
		if("110000".equals(userBean.getUnNo())){
		 }
		//为总公司并且是部门---查询全部
		else if((unitModel.getUnAttr()!=null&&unitModel.getUnLvl()!=null)&&(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0)){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			unnoSql = " and p.unno ='"+userBean.getUnNo()+"' " ;
		
		}
		//判断是否为当月1号
		if(DateTools.isFirstDay()) {
			String sql="select  "
					+ " p.wid," + 
					"p.aptid," + 
					"p.unno," + 
					"p.merchanttype," + 
					"p.profittype," + 
					"nvl(p.profitrule,'21') profitrule," + 
					"p.startamount," + 
					"p.endamount," + 
					"p.rulethreshold," + 
					"p.profitpercent," + 
					"p.mataintype,\r\n" + 
					"p.mataindate,\r\n" + 
					"p.tempname,\r\n" + 
					"p.matainuserid,\r\n" + 
					"p.settmethod,\r\n" + 
					"p.creditbankrate,\r\n" + 
					"p.cashrate,\r\n" + 
					"p.cashamt,\r\n" + 
					"p.scanrate,\r\n" + 
					"p.cloudquickrate,\r\n" + 
					"p.subrate,\r\n" + 
					"p.scanrate1,\r\n" + 
					"p.scanrate2,\r\n" + 
					"p.cashamt1,\r\n" + 
					"p.cashamt2,\r\n" + 
					"p.profitpercent1,\r\n" + 
					"p.cashamtabove,\r\n" + 
					"p.cashamtunder,\r\n" + 
					"p.huabeirate,\r\n" + 
					"p.huabeifee"
					+ " from CHECK_MICRO_PROFITTEMPLATE_W p where merchanttype=5 and p.tempname=:tempname"+
					" and p.MatainType in ('A','M','P') "+
					" and p.mataindate <= trunc(sysdate,'mm')"+
					" and p.mataindate >= add_months(trunc(sysdate,'mm'),-1) ";
			sql += unnoSql;
			String count="select count(*) from ("+sql+")";
			BigDecimal counts = checkUnitProfitMicroWDao.querysqlCounts(count,params);
			//判断_w表中是否有值（mataindate在上月1号到当前月1号的均作为下月模板展示）
			if(counts.intValue() > 0) {
				//_w有值
				List<CheckProfitTemplateMicroWModel> list=checkUnitProfitMicroWDao.queryObjectsBySqlList(sql, params,CheckProfitTemplateMicroWModel.class );
				for(int i=0;i<list.size();i++){
		            CheckProfitTemplateMicroWModel cc =list.get(i);
		            Map<String, Object> map = new HashMap<String, Object>();
		            map.put("profitRule",cc.getProfitRule());
		            
		            map.put("creditBankRate", null==cc.getCreditBankRate()?"":new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100")));
		            map.put("cashRate", null==cc.getCashRate()?"":cc.getCashRate());
		            map.put("scanRate", null==cc.getScanRate()?"":new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
		            map.put("profitPercent1", null==cc.getProfitPercent1()?"":cc.getProfitPercent1());
		            map.put("subRate", null==cc.getSubRate()?"":new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
		            map.put("cashAmt", null==cc.getCashAmt()?"":cc.getCashAmt());
		            map.put("cashAmt1", null==cc.getCashAmt1()?"":cc.getCashAmt1());
		            map.put("scanRate1", null==cc.getScanRate1()?"":new BigDecimal(cc.getScanRate1()+"").multiply(new BigDecimal("100")));
		            map.put("scanRate2", null==cc.getScanRate2()?"":new BigDecimal(cc.getScanRate2()+"").multiply(new BigDecimal("100")));
		            map.put("cashAmt2", null==cc.getCashAmt2()?"":cc.getCashAmt2());
		            map.put("huabeiRate", null==cc.getHuabeiRate()?"":new BigDecimal(cc.getHuabeiRate()+"").multiply(new BigDecimal("100")));
		            map.put("huabeiFee", null==cc.getHuabeiFee()?"":cc.getHuabeiFee());
		            map.put("aptId", cc.getAptId());
    	            map.put("matainType", cc.getMatainType());
    	            String ishuabei = querySubTypeByProfitRule(cc.getProfitRule());
		            if(ishuabei!=null) {
		            	map.put("ishuabei", 1);
		            }else {
		            	map.put("ishuabei", 2);
		            }
		            lst.add(map);
				}
				return lst;
			}else {
				//_w无值
				String currentSql="select  "+
						"p.aptid,\r\n" + 
						"p.unno,\r\n" + 
						"p.merchanttype,\r\n" + 
						"p.profittype,\r\n" + 
						"nvl(p.profitrule,'21') profitrule,\r\n" + 
						"p.startamount,\r\n" + 
						"p.endamount,\r\n" + 
						"p.rulethreshold,\r\n" + 
						"p.profitpercent,\r\n" + 
						"p.mataintype,\r\n" + 
						"p.mataindate,\r\n" + 
						"p.tempname,\r\n" + 
						"p.matainuserid,\r\n" + 
						"p.settmethod,\r\n" + 
						"p.creditbankrate,\r\n" + 
						"p.cashrate,\r\n" + 
						"p.cashamt,\r\n" + 
						"p.scanrate,\r\n" + 
						"p.cloudquickrate,\r\n" + 
						"p.subrate,\r\n" + 
						"p.scanrate1,\r\n" + 
						"p.scanrate2,\r\n" + 
						"p.cashamt1,\r\n" + 
						"p.cashamt2,\r\n" + 
						"p.profitpercent1,\r\n" + 
						"p.cashamtabove,\r\n" + 
						"p.cashamtunder,\r\n" + 
						"p.huabeirate,\r\n" + 
						"p.huabeifee"+
						" from CHECK_MICRO_PROFITTEMPLATE p where merchanttype=5 and p.MatainType in ('A','M','P') and p.tempname=:tempname  ";
				List<CheckProfitTemplateMicroModel> list=checkUnitProfitMicroDao.queryObjectsBySqlList(currentSql, params,CheckProfitTemplateMicroModel.class );
				for(int i=0;i<list.size();i++){
		            CheckProfitTemplateMicroModel cc =list.get(i);
		            Map<String, Object> map = new HashMap<String, Object>();
		            map.put("profitRule",cc.getProfitRule());
		            
		            map.put("creditBankRate", null==cc.getCreditBankRate()?"":new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100")));
		            map.put("cashRate", null==cc.getCashRate()?"":cc.getCashRate());
		            map.put("scanRate", null==cc.getScanRate()?"":new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
		            map.put("profitPercent1", null==cc.getProfitPercent1()?"":cc.getProfitPercent1());
		            map.put("subRate", null==cc.getSubRate()?"":new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
		            map.put("cashAmt", null==cc.getCashAmt()?"":cc.getCashAmt());
		            map.put("cashAmt1", null==cc.getCashAmt1()?"":cc.getCashAmt1());
		            map.put("scanRate1", null==cc.getScanRate1()?"":new BigDecimal(cc.getScanRate1()+"").multiply(new BigDecimal("100")));
		            map.put("scanRate2", null==cc.getScanRate2()?"":new BigDecimal(cc.getScanRate2()+"").multiply(new BigDecimal("100")));
		            map.put("cashAmt2", null==cc.getCashAmt2()?"":cc.getCashAmt2());
		            map.put("huabeiRate", null==cc.getHuabeiRate()?"":new BigDecimal(cc.getHuabeiRate()+"").multiply(new BigDecimal("100")));
		            map.put("huabeiFee", null==cc.getHuabeiFee()?"":cc.getHuabeiFee());
		            map.put("aptId", cc.getAptId());
    	            map.put("matainType", cc.getMatainType());
    	            String ishuabei = querySubTypeByProfitRule(cc.getProfitRule());
		            if(ishuabei!=null) {
		            	map.put("ishuabei", 1);
		            }else {
		            	map.put("ishuabei", 2);
		            }
		            lst.add(map);
		        }
		        return lst;
			}
		}else {
			String sql="select  "+
					" p.wid,\r\n" + 
					"p.aptid,\r\n" + 
					"p.unno,\r\n" + 
					"p.merchanttype,\r\n" + 
					"p.profittype,\r\n" + 
					"nvl(p.profitrule,'21') profitrule,\r\n" + 
					"p.startamount,\r\n" + 
					"p.endamount,\r\n" + 
					"p.rulethreshold,\r\n" + 
					"p.profitpercent,\r\n" + 
					"p.mataintype,\r\n" + 
					"p.mataindate,\r\n" + 
					"p.tempname,\r\n" + 
					"p.matainuserid,\r\n" + 
					"p.settmethod,\r\n" + 
					"p.creditbankrate,\r\n" + 
					"p.cashrate,\r\n" + 
					"p.cashamt,\r\n" + 
					"p.scanrate,\r\n" + 
					"p.cloudquickrate,\r\n" + 
					"p.subrate,\r\n" + 
					"p.scanrate1,\r\n" + 
					"p.scanrate2,\r\n" + 
					"p.cashamt1,\r\n" + 
					"p.cashamt2,\r\n" + 
					"p.profitpercent1,\r\n" + 
					"p.cashamtabove,\r\n" + 
					"p.cashamtunder,\r\n" + 
					"p.huabeirate,\r\n" + 
					"p.huabeifee"+
					" from CHECK_MICRO_PROFITTEMPLATE_W p where merchanttype=5 and p.tempname=:tempname"+
					" and p.MatainType in ('A','M','P') "+
					" and p.mataindate>=trunc(sysdate,'mm')";
			sql += unnoSql;
			String count="select count(*) from ("+sql+")";
			BigDecimal counts = checkUnitProfitMicroWDao.querysqlCounts(count,params);
			//判断_w表中是否有值（mataindate在当前月1号之后）
			if(counts.intValue() > 0) {
				//_w有值
				List<CheckProfitTemplateMicroWModel> list=checkUnitProfitMicroWDao.queryObjectsBySqlList(sql, params,CheckProfitTemplateMicroWModel.class );
				for(int i=0;i<list.size();i++){
		            CheckProfitTemplateMicroWModel cc =list.get(i);
		            Map<String, Object> map = new HashMap<String, Object>();
		            map.put("profitRule",cc.getProfitRule());
		            
		            map.put("creditBankRate", null==cc.getCreditBankRate()?"":new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100")));
		            map.put("cashRate", null==cc.getCashRate()?"":cc.getCashRate());
		            map.put("scanRate", null==cc.getScanRate()?"":new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
		            map.put("profitPercent1", null==cc.getProfitPercent1()?"":cc.getProfitPercent1());
		            map.put("subRate", null==cc.getSubRate()?"":new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
		            map.put("cashAmt", null==cc.getCashAmt()?"":cc.getCashAmt());
		            map.put("cashAmt1", null==cc.getCashAmt1()?"":cc.getCashAmt1());
		            map.put("scanRate1", null==cc.getScanRate1()?"":new BigDecimal(cc.getScanRate1()+"").multiply(new BigDecimal("100")));
		            map.put("scanRate2", null==cc.getScanRate2()?"":new BigDecimal(cc.getScanRate2()+"").multiply(new BigDecimal("100")));
		            map.put("cashAmt2", null==cc.getCashAmt2()?"":cc.getCashAmt2());
		            map.put("huabeiRate", null==cc.getHuabeiRate()?"":new BigDecimal(cc.getHuabeiRate()+"").multiply(new BigDecimal("100")));
		            map.put("huabeiFee", null==cc.getHuabeiFee()?"":cc.getHuabeiFee());
		            map.put("aptId", cc.getAptId());
    	            map.put("matainType", cc.getMatainType());
    	            String ishuabei = querySubTypeByProfitRule(cc.getProfitRule());
		            if(ishuabei!=null) {
		            	map.put("ishuabei", 1);
		            }else {
		            	map.put("ishuabei", 2);
		            }
		            lst.add(map);
				}
				return lst;
			}else {
				//_w无值
				String currentSql="select  "
				+" p.aptid,\r\n" + 
				"p.unno,\r\n" + 
				"p.merchanttype,\r\n" + 
				"p.profittype,\r\n" + 
				"nvl(p.profitrule,'21') profitrule,\r\n" + 
				"p.startamount,\r\n" + 
				"p.endamount,\r\n" + 
				"p.rulethreshold,\r\n" + 
				"p.profitpercent,\r\n" + 
				"p.mataintype,\r\n" + 
				"p.mataindate,\r\n" + 
				"p.tempname,\r\n" + 
				"p.matainuserid,\r\n" + 
				"p.settmethod,\r\n" + 
				"p.creditbankrate,\r\n" + 
				"p.cashrate,\r\n" + 
				"p.cashamt,\r\n" + 
				"p.scanrate,\r\n" + 
				"p.cloudquickrate,\r\n" + 
				"p.subrate,\r\n" + 
				"p.scanrate1,\r\n" + 
				"p.scanrate2,\r\n" + 
				"p.cashamt1,\r\n" + 
				"p.cashamt2,\r\n" + 
				"p.profitpercent1,\r\n" + 
				"p.cashamtabove,\r\n" + 
				"p.cashamtunder,\r\n" + 
				"p.huabeirate,\r\n" + 
				"p.huabeifee"
				+" from CHECK_MICRO_PROFITTEMPLATE p where merchanttype=5 and p.MatainType in ('A','M','P') and p.tempname=:tempname  ";
				List<CheckProfitTemplateMicroModel> list=checkUnitProfitMicroDao.queryObjectsBySqlList(currentSql, params,CheckProfitTemplateMicroModel.class );
				for(int i=0;i<list.size();i++){
		            CheckProfitTemplateMicroModel cc =list.get(i);
		            Map<String, Object> map = new HashMap<String, Object>();
		            map.put("profitRule",cc.getProfitRule());
		            
		            map.put("creditBankRate", null==cc.getCreditBankRate()?"":new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100")));
		            map.put("cashRate", null==cc.getCashRate()?"":cc.getCashRate());
		            map.put("scanRate", null==cc.getScanRate()?"":new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
		            map.put("profitPercent1", null==cc.getProfitPercent1()?"":cc.getProfitPercent1());
		            map.put("subRate", null==cc.getSubRate()?"":new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
		            map.put("cashAmt", null==cc.getCashAmt()?"":cc.getCashAmt());
		            map.put("cashAmt1", null==cc.getCashAmt1()?"":cc.getCashAmt1());
		            map.put("scanRate1", null==cc.getScanRate1()?"":new BigDecimal(cc.getScanRate1()+"").multiply(new BigDecimal("100")));
		            map.put("scanRate2", null==cc.getScanRate2()?"":new BigDecimal(cc.getScanRate2()+"").multiply(new BigDecimal("100")));
		            map.put("cashAmt2", null==cc.getCashAmt2()?"":cc.getCashAmt2());
		            map.put("huabeiRate", null==cc.getHuabeiRate()?"":new BigDecimal(cc.getHuabeiRate()+"").multiply(new BigDecimal("100")));
		            map.put("huabeiFee", null==cc.getHuabeiFee()?"":cc.getHuabeiFee());
		            map.put("aptId", cc.getAptId());
    	            map.put("matainType", cc.getMatainType());
    	            String ishuabei = querySubTypeByProfitRule(cc.getProfitRule());
		            if(ishuabei!=null) {
		            	map.put("ishuabei", 1);
		            }else {
		            	map.put("ishuabei", 2);
		            }
		            lst.add(map);
		        }
		        return lst;
			}
		}
	}
	
	/**
	 * 查询模版是否重复添加
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> queryTempName(CheckProfitTemplateMicroBean cptm,UserBean user) throws UnsupportedEncodingException {
		String sql="select p.TEMPNAME from CHECK_MICRO_PROFITTEMPLATE p where (p.tempname='"+cptm.getTempName().trim()+"' " +
					" or (p.tempname='"+cptm.getTempName().trim()+"' and unno ='"+user.getUnNo()+"') )" +
					" and MatainType in ('A','M')";
		if(cptm.getAptId()!=null&&!"".equals(cptm.getAptId())){
			sql+=" and aptId!="+cptm.getAptId();
		}
		List list=checkUnitProfitMicroDao.executeSql(sql);
		return list;
	}
	/**
	 * 查询分润模版明细
	 * @throws UnsupportedEncodingException
	 */
	public DataGridBean queryprofittemplateAll(CheckProfitTemplateMicroBean cptm,UserBean userBean) throws UnsupportedEncodingException{

		String sql= "select p.APTID,p.UNNO,p.TEMPNAME,p.MERCHANTTYPE,p.PROFITTYPE,p.PROFITRULE,p.STARTAMOUNT,p.ENDAMOUNT," +
					"p.RULETHRESHOLD,p.PROFITPERCENT,p.MATAINTYPE,p.SETTMETHOD,p.CREDITBANKRATE,p.CASHRATE,p.CASHAMT,p.SCANRATE  " +
					"from CHECK_MICRO_PROFITTEMPLATE p where p.tempname='"+java.net.URLDecoder.decode(cptm.getTempName(),"UTF-8").trim()+"' " ;
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if("110000".equals(userBean.getUnNo())){
		 }
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and p.unno ='"+userBean.getUnNo()+"' " ;
		}
		String count="select count(*) from ("+sql+")";
		List<Map<String,String>> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql,null,cptm.getPage(),cptm.getRows());
		BigDecimal counts = checkUnitProfitMicroDao.querysqlCounts(count,null);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());//查询数据库总条数
		dgd.setRows(list);
		return dgd;
	}
	/**
	 * 添加分润模版
	 */
	public void addProfitmicro(CheckProfitTemplateMicroBean cptm, UserBean user) {
		//理财商户
		CheckProfitTemplateMicroModel MicroModel = new CheckProfitTemplateMicroModel();
		MicroModel.setUnno(user.getUnNo());
		MicroModel.setMerchantType(1);
		MicroModel.setProfitType(1);
		MicroModel.setProfitRule(1);
		if(cptm.getStartAmount()!=null) {
			MicroModel.setStartAmount(cptm.getStartAmount());
		}
		if(cptm.getEndAmount()!=null) {
			MicroModel.setEndAmount(cptm.getEndAmount());
		}
		if(cptm.getRuleThreshold()!=null) {
			MicroModel.setRuleThreshold(cptm.getRuleThreshold() / 100);
		}
		if(cptm.getProfitPercent()!=null) {
			MicroModel.setProfitPercent(cptm.getProfitPercent() / 100);
		}
		if(cptm.getProfitPercent3()!=null) {
			MicroModel.setProfitPercent1(cptm.getProfitPercent3() / 100);
		}
		if(cptm.getCashRate()!=null) {
			MicroModel.setCashRate(cptm.getCashRate() / 100);
		}
		if(cptm.getCashAmt()!=null) {
			MicroModel.setCashAmt(cptm.getCashAmt());
		}
		MicroModel.setCashAmt1(cptm.getCashAmt2());
		if(cptm.getCashAmt3()!=null) {
			MicroModel.setCashAmt2(cptm.getCashAmt3());
		}
		if(cptm.getCreditBankRate()!=null) {
			MicroModel.setCreditBankRate(cptm.getCreditBankRate() / 100);
		}
		MicroModel.setScanRate(cptm.getScanRate()/100);
		MicroModel.setScanRate1(cptm.getScanRate1()/100);
		MicroModel.setScanRate2(cptm.getScanRate2()/100);
		MicroModel.setCloudQuickRate(cptm.getCloudQuickRate()/100);
		MicroModel.setMatainDate(new Date());
		MicroModel.setTempName(cptm.getTempName());
		MicroModel.setMatainUserId(user.getUserID());
		MicroModel.setMatainType("A");
		MicroModel.setSettMethod("000000");
		checkUnitProfitMicroDao.saveObject(MicroModel);
		//秒到商户0.72
		CheckProfitTemplateMicroModel MicroModel1 = new CheckProfitTemplateMicroModel();
		MicroModel1.setUnno(user.getUnNo());
		MicroModel1.setMerchantType(2);
		MicroModel1.setProfitType(1);
		MicroModel1.setProfitRule(1);
//		startAmount2;   		 // 秒到快捷vip
		if(cptm.getStartAmount2()!=null) {
			MicroModel1.setStartAmount(cptm.getStartAmount2() / 100);
		}
//		endAmount2  ; 		 //秒到快捷完美
		if(cptm.getEndAmount2()!=null) {
			MicroModel1.setEndAmount(cptm.getEndAmount2() / 100);
		}
//		ruleThreshold2 ;   	// 秒到快捷提现
		MicroModel1.setRuleThreshold(cptm.getRuleThreshold2());
		MicroModel1.setProfitPercent(cptm.getProfitPercent1()/100);
		if(cptm.getProfitPercent3()!=null) {
			MicroModel.setProfitPercent1(cptm.getProfitPercent3() / 100);
		}
		if(cptm.getCashRate()!=null) {
			MicroModel1.setCashRate(cptm.getCashRate() / 100);
		}
		MicroModel1.setCashAmt(cptm.getCashAmt1());
		MicroModel1.setCashAmt1(cptm.getCashAmt2());
		if(cptm.getCashAmt3()!=null) {
			MicroModel1.setCashAmt2(cptm.getCashAmt3());
		}
		MicroModel1.setCreditBankRate(cptm.getCreditBankRate1()/100);
		MicroModel1.setScanRate(cptm.getScanRate()/100);
		MicroModel1.setScanRate1(cptm.getScanRate1()/100);
		MicroModel1.setScanRate2(cptm.getScanRate2()/100);
		MicroModel1.setCloudQuickRate(cptm.getCloudQuickRate()/100);
		
		//20200226-ztt添加扫码1000以上、以下转账费
		if(cptm.getCashamtabove()!=null) {
			MicroModel1.setCashamtabove(cptm.getCashamtabove());
		}
		if(cptm.getCashamtunder()!=null) {
			MicroModel1.setCashamtunder(cptm.getCashamtunder());
		}
		
		MicroModel1.setMatainDate(new Date());
		MicroModel1.setTempName(cptm.getTempName());
		MicroModel1.setMatainUserId(user.getUserID());
		MicroModel1.setMatainType("A");
		MicroModel1.setSettMethod("100000");
		checkUnitProfitMicroDao.saveObject(MicroModel1);
		//秒到商户非0.72
		CheckProfitTemplateMicroModel MicroModel2 = new CheckProfitTemplateMicroModel();
		MicroModel2.setUnno(user.getUnNo());
		MicroModel2.setMerchantType(3);
		MicroModel2.setProfitType(1);
		MicroModel2.setProfitRule(1);
//		startAmount2;   		 // 秒到快捷vip
		if(cptm.getStartAmount2()!=null) {
			MicroModel2.setStartAmount(cptm.getStartAmount2() / 100);
		}
//		endAmount2  ; 		 //秒到快捷完美
		if(cptm.getEndAmount2()!=null) {
			MicroModel2.setEndAmount(cptm.getEndAmount2() / 100);
		}
//		ruleThreshold2 ;   	// 秒到快捷提现
		MicroModel2.setRuleThreshold(cptm.getRuleThreshold2());
		MicroModel2.setProfitPercent(cptm.getProfitPercent2()/100);
		if(cptm.getProfitPercent3()!=null) {
			MicroModel.setProfitPercent1(cptm.getProfitPercent3() / 100);
		}
		if(cptm.getCashRate()!=null) {
			MicroModel2.setCashRate(cptm.getCashRate() / 100);
		}
		MicroModel2.setCashAmt(cptm.getCashAmt1());
		MicroModel2.setCashAmt1(cptm.getCashAmt2());
		if(cptm.getCashAmt3()!=null) {
			MicroModel2.setCashAmt2(cptm.getCashAmt3());
		}
		MicroModel2.setCreditBankRate(cptm.getCreditBankRate2()/100);
		MicroModel2.setScanRate(cptm.getScanRate()/100);
		MicroModel2.setScanRate1(cptm.getScanRate1()/100);
		MicroModel2.setScanRate2(cptm.getScanRate2()/100);
		MicroModel2.setCloudQuickRate(cptm.getCloudQuickRate()/100);
		MicroModel2.setMatainDate(new Date());
		MicroModel2.setTempName(cptm.getTempName());
		MicroModel2.setMatainUserId(user.getUserID());
		MicroModel2.setMatainType("A");
		MicroModel2.setSettMethod("100000");
		checkUnitProfitMicroDao.saveObject(MicroModel2);
	}
	/**
	 * 添加代还分润模版
	 */
	public void addSubTemplate(CheckProfitTemplateMicroBean cptm, UserBean user) {
		//代还模板
		CheckProfitTemplateMicroModel MicroModel = new CheckProfitTemplateMicroModel();
		MicroModel.setUnno(user.getUnNo());
		MicroModel.setMerchantType(4);//表示4--代还模板
		MicroModel.setSubRate(cptm.getSubRate()/100);
		MicroModel.setTempName(cptm.getTempName());
		MicroModel.setMatainDate(new Date());
		MicroModel.setMatainType("A");
		checkUnitProfitMicroDao.saveObject(MicroModel);
	}

	@Override
	public String validateMposTemplate(CheckProfitTemplateMicroBean cptm, UserBean user,Integer checkType){
		if(user.getUnitLvl()>=2) {
            // TODO @author:lxg-20200521 秒到模板多级成本上限限制
            Map<String, HrtUnnoCostModel> limit = agentUnitService.getAllLimitHrtCostMap();
			// 下级机构最小成本
//			Map<String, CheckProfitTemplateMicroModel> microModelMap = getProfitTemplateMap(3, user.getUnNo(), user.getUnitLvl());
			if(user.getUnitLvl()==2){
				// 上一代为一代成本
				Map<String, HrtUnnoCostModel> hrtMap = agentUnitService.getAllHrtCostMap(1, user.getUnNo(), 2,checkType);
				// 理财商户
				String x = getValidateMdFinancial(cptm, null, hrtMap,limit);
				if (x != null) return x;
				// 秒到商户
				String x72 = getValidateMd72(cptm, null, hrtMap,limit);
				if (x72 != null) return x72;
				// 扫码支付商户
				String xSm = getValidateSm(cptm, null, hrtMap,limit);
				if (xSm != null) return xSm;
				// 快捷支付商户
				String xVip = getValidateVip(cptm, null, hrtMap,limit);
				if (xVip != null) return xVip;
			}else{
				Map<String, CheckProfitTemplateMicroModel> profitTemplateMap = getProfitTemplateMap(1, user.getUnNo(), user.getUnitLvl(),checkType);
				String x = getSubValidateMdFinancialString(cptm, null, profitTemplateMap,limit);
				if (x != null) return x;
				String xMd = getSubValidateMd(cptm, null, profitTemplateMap,limit);
				if (xMd != null) return xMd;
				String xSm = getSubVaildateSm(cptm, null, profitTemplateMap,limit);
				if (xSm != null) return xSm;
				String xVip = getSubValidateVip(cptm, null, profitTemplateMap,limit);
				if (xVip != null) return xVip;
			}
		}
		return null;
	}

	private String getSubValidateVip(CheckProfitTemplateMicroBean cptm, Map<String, CheckProfitTemplateMicroModel> microModelMap1, Map<String, CheckProfitTemplateMicroModel> profitTemplateMap,Map<String, HrtUnnoCostModel> limit) {
		// VIP用户费率
		if (profitTemplateMap.get("3|1|1")!=null && profitTemplateMap.get("3|1|1").getStartAmount()!=null) {
			if(cptm.getStartAmount2()!=null && new BigDecimal(cptm.getStartAmount2().toString()).compareTo(new BigDecimal(profitTemplateMap.get("3|1|1").getStartAmount().toString()).multiply(new BigDecimal("100")))<0){
				return "VIP用户费率小于上级成本";
			}
			if(limit.get("1|5|6")!=null && limit.get("1|5|6").getCreditRate()!=null
					&& cptm.getStartAmount2()!=null && new BigDecimal(cptm.getStartAmount2().toString()).compareTo(limit.get("1|5|6").getCreditRate())>0){
				return "VIP用户费率成本高于商户终端成本，请修改";
			}
		}else if(cptm.getStartAmount2()!=null){
			return "VIP用户费率成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 完美账单费率
		if (profitTemplateMap.get("3|1|1")!=null && profitTemplateMap.get("3|1|1").getEndAmount()!=null) {
			if(cptm.getEndAmount2()!=null && new BigDecimal(cptm.getEndAmount2().toString()).compareTo(new BigDecimal(profitTemplateMap.get("3|1|1").getEndAmount().toString()).multiply(new BigDecimal("100")))<0){
				return "完美账单费率小于上级成本";
			}
			if(limit.get("1|5|7")!=null && limit.get("1|5|7").getCreditRate()!=null
					&& cptm.getEndAmount2()!=null && new BigDecimal(cptm.getEndAmount2().toString()).compareTo(limit.get("1|5|7").getCreditRate())>0){
				return "完美账单费率成本高于商户终端成本，请修改";
			}
		}else if(cptm.getEndAmount2()!=null){
			return "完美账单费率成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 转账费
		if (profitTemplateMap.get("3|1|1")!=null && profitTemplateMap.get("3|1|1").getCashAmt2()!=null) {
			if(cptm.getCashAmt3()!=null && new BigDecimal(cptm.getCashAmt3().toString()).compareTo(new BigDecimal(profitTemplateMap.get("3|1|1").getCashAmt2().toString()))<0){
				return "快捷转账费小于上级成本";
			}
			if(limit.get("1|5|6")!=null && limit.get("1|5|6").getCashCost()!=null
					&& cptm.getCashAmt3()!=null && new BigDecimal(cptm.getCashAmt3().toString()).compareTo(limit.get("1|5|6").getCashCost())>0){
				return "快捷转账费成本高于商户终端成本，请修改";
			}
		}else if(cptm.getCashAmt3()!=null){
			return "快捷转账费成本上级代理未维护，请联系上级代理进行成本设置";
		}
		return null;
	}

	private String getSubVaildateSm(CheckProfitTemplateMicroBean cptm, Map<String, CheckProfitTemplateMicroModel> microModelMap1, Map<String, CheckProfitTemplateMicroModel> profitTemplateMap,Map<String, HrtUnnoCostModel> limit) {
		// 微信费率
		if (profitTemplateMap.get("2|1|1")!=null && profitTemplateMap.get("2|1|1").getScanRate()!=null) {
			if(cptm.getScanRate()!=null && new BigDecimal(cptm.getScanRate().toString()).compareTo(new BigDecimal(profitTemplateMap.get("2|1|1").getScanRate().toString()).multiply(new BigDecimal("100")))<0){
			//	return "微信费率小于上级成本";
				return "扫码1000以下费率小于上级成本";
			}
			if(cptm.getScanRate()!=null && limit.get("1|6|8")!=null && limit.get("1|6|8").getCreditRate()!=null
					&& new BigDecimal(cptm.getScanRate().toString()).compareTo(limit.get("1|6|8").getCreditRate())>0){
				//return "微信费率小于上级成本";
				return "扫码1000以下费率成本高于商户终端成本，请修改";
			}
		}else{
//			return "微信费率成本上级代理未维护，请联系上级代理进行成本设置";
			return "扫码1000以下费率成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 支付宝费率
		if (profitTemplateMap.get("2|1|1")!=null && profitTemplateMap.get("2|1|1").getScanRate1()!=null) {
			if(cptm.getScanRate1()!=null && new BigDecimal(cptm.getScanRate1().toString()).compareTo(new BigDecimal(profitTemplateMap.get("2|1|1").getScanRate1().toString()).multiply(new BigDecimal("100")))<0){
				//return "支付宝费率小于上级成本";
				return "扫码1000以上费率小于上级成本";
			}
			if(cptm.getScanRate1()!=null && limit.get("1|6|9")!=null && limit.get("1|6|9").getCreditRate()!=null
					&& new BigDecimal(cptm.getScanRate1().toString()).compareTo(limit.get("1|6|9").getCreditRate())>0){
				//return "支付宝费率小于上级成本";
				return "扫码1000以上费率成本高于商户终端成本，请修改";
			}
		}else{
			//return "支付宝费率成本上级代理未维护，请联系上级代理进行成本设置";
			return "扫码1000以上费率成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 银联二维码费率
		if (profitTemplateMap.get("2|1|1")!=null && profitTemplateMap.get("2|1|1").getScanRate2()!=null) {
			if(cptm.getScanRate2()!=null && new BigDecimal(cptm.getScanRate2().toString()).compareTo(new BigDecimal(profitTemplateMap.get("2|1|1").getScanRate2().toString()).multiply(new BigDecimal("100")))<0){
				return "银联二维码费率小于上级成本";
			}
			if(cptm.getScanRate2()!=null && limit.get("1|6|10")!=null && limit.get("1|6|10").getCreditRate()!=null
					&& new BigDecimal(cptm.getScanRate2().toString()).compareTo(limit.get("1|6|10").getCreditRate())>0){
				return "银联二维码费率成本高于商户终端成本，请修改";
			}
		}else{
			return "银联二维码费率成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 扫码转账费
		if (profitTemplateMap.get("2|1|1")!=null && profitTemplateMap.get("2|1|1").getCashAmt1()!=null) {
			if(cptm.getCashAmt2()!=null && new BigDecimal(cptm.getCashAmt2().toString()).compareTo(new BigDecimal(profitTemplateMap.get("2|1|1").getCashAmt1().toString()))<0){
				//return "扫码转账费小于上级成本";
				return "银联二维码转账费小于上级成本";
			}
			if (cptm.getCashAmt2()!=null && limit.get("1|6|10")!=null && limit.get("1|6|10").getCashCost()!=null
					&& new BigDecimal(cptm.getCashAmt2().toString()).compareTo(limit.get("1|6|10").getCashCost()) > 0) {
				return "银联二维码转账费成本高于商户终端成本，请修改";
			}
		}else{
			//return "扫码转账费成本上级代理未维护，请联系上级代理进行成本设置";
			return "银联二维码转账费成本上级代理未维护，请联系上级代理进行成本设置";
		}
		//扫码1000以下转账费
		if (profitTemplateMap.get("2|1|1")!=null && profitTemplateMap.get("2|1|1").getCashamtunder()!=null) {
			if(cptm.getCashamtunder()!=null && new BigDecimal(cptm.getCashamtunder().toString()).compareTo(new BigDecimal(profitTemplateMap.get("2|1|1").getCashamtunder().toString()))<0){
				return "扫码1000以下转账费小于上级成本";
			}
			if (cptm.getCashamtunder()!=null && limit.get("1|6|8")!=null && limit.get("1|6|8").getCashCost()!=null
					&& new BigDecimal(cptm.getCashamtunder().toString()).compareTo(limit.get("1|6|8").getCashCost()) > 0) {
				return "扫码1000以下转账费成本高于商户终端成本，请修改";
			}
		}else{
			return "扫码1000以下转账费成本上级代理未维护，请联系上级代理进行成本设置";
		}
		//扫码1000以上转账费
		if (profitTemplateMap.get("2|1|1")!=null && profitTemplateMap.get("2|1|1").getCashamtabove()!=null) {
			if(cptm.getCashamtabove()!=null && new BigDecimal(cptm.getCashamtabove().toString()).compareTo(new BigDecimal(profitTemplateMap.get("2|1|1").getCashamtabove().toString()))<0){
				return "扫码1000以上转账费小于上级成本";
			}
			if (cptm.getCashamtabove()!=null && limit.get("1|6|9")!=null && limit.get("1|6|9").getCashCost()!=null
					&& new BigDecimal(cptm.getCashamtabove().toString()).compareTo(limit.get("1|6|9").getCashCost()) > 0) {
				return "扫码1000以上转账费成本高于商户终端成本，请修改";
			}
		}else{
			return "扫码1000以上转账费成本上级代理未维护，请联系上级代理进行成本设置";
		}
		return null;
	}

	private String getSubValidateMd(CheckProfitTemplateMicroBean cptm, Map<String, CheckProfitTemplateMicroModel> microModelMap1, Map<String, CheckProfitTemplateMicroModel> profitTemplateMap,Map<String, HrtUnnoCostModel> limit) {
		// 贷记卡0.72%费率
		if (profitTemplateMap.get("2|1|1")!=null && profitTemplateMap.get("2|1|1").getCreditBankRate()!=null) {
			if(cptm.getCreditBankRate1()!=null && new BigDecimal(cptm.getCreditBankRate1().toString()).compareTo(new BigDecimal(profitTemplateMap.get("2|1|1").getCreditBankRate().toString()).multiply(new BigDecimal("100")))<0){
				return "秒到0.72贷记卡费率小于上级成本";
			}
			if(cptm.getCreditBankRate1()!=null && limit.get("1|1|2")!=null && limit.get("1|1|2").getCreditRate()!=null
					&& new BigDecimal(cptm.getCreditBankRate1().toString()).compareTo(limit.get("1|1|2").getCreditRate())>0){
				return "秒到0.72贷记卡费率成本高于商户终端成本，请修改";
			}
		}else{
			return "秒到0.72贷记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
		}
		// 利润百分比

		// 贷记卡非0.72%费率
		if (profitTemplateMap.get("3|1|1")!=null && profitTemplateMap.get("3|1|1").getCreditBankRate()!=null) {
			if(cptm.getCreditBankRate2()!=null && new BigDecimal(cptm.getCreditBankRate2().toString()).compareTo(new BigDecimal(profitTemplateMap.get("3|1|1").getCreditBankRate().toString()).multiply(new BigDecimal("100")))<0){
				return "秒到非0.72贷记卡费率小于上级成本";
			}
			if(cptm.getCreditBankRate2()!=null && limit.get("1|1|1")!=null && limit.get("1|1|1").getCreditRate()!=null
					&& new BigDecimal(cptm.getCreditBankRate2().toString()).compareTo(limit.get("1|1|1").getCreditRate())>0){
				return "秒到非0.72贷记卡费率成本高于商户终端成本，请修改";
			}
		}else{
			return "秒到非0.72贷记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
		}
		// 利润百分比

		// 转账费
		if (profitTemplateMap.get("2|1|1")!=null && profitTemplateMap.get("2|1|1").getCashAmt()!=null) {
			if(cptm.getCashAmt1()!=null && new BigDecimal(cptm.getCashAmt1().toString()).compareTo(new BigDecimal(profitTemplateMap.get("2|1|1").getCashAmt().toString()))<0){
				return "秒到转账费小于上级成本";
			}
			if(cptm.getCashAmt1()!=null && limit.get("1|1|1")!=null && limit.get("1|1|1").getCashCost()!=null
					&& new BigDecimal(cptm.getCashAmt1().toString()).compareTo(limit.get("1|1|1").getCashCost())>0){
				return "秒到转账费成本高于商户终端成本，请修改";
			}
		}else{
			return "秒到转账费成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 云闪付费率
		if (profitTemplateMap.get("2|1|1")!=null && profitTemplateMap.get("2|1|1").getCloudQuickRate()!=null) {
			if(cptm.getCloudQuickRate()!=null && new BigDecimal(cptm.getCloudQuickRate().toString()).compareTo(new BigDecimal(profitTemplateMap.get("2|1|1").getCloudQuickRate().toString()).multiply(new BigDecimal("100")))<0){
				return "秒到云闪付费率小于上级成本";
			}
			if(cptm.getCloudQuickRate()!=null && limit.get("1|4|5")!=null && limit.get("1|4|5").getCreditRate()!=null
					&& new BigDecimal(cptm.getCloudQuickRate().toString()).compareTo(limit.get("1|4|5").getCreditRate())>0){
				return "秒到云闪付费率成本高于商户终端成本，请修改";
			}
		}else{
			return "秒到云闪付费率成本上级代理未维护，请联系上级代理进行成本设置";
		}
		return null;
	}

	private String getValidateVip(CheckProfitTemplateMicroBean cptm, Map<String, CheckProfitTemplateMicroModel> microModelMap1, Map<String, HrtUnnoCostModel> hrtMap,Map<String, HrtUnnoCostModel> limit) {
		// VIP用户费率
		if (hrtMap.get("1|5|6")!=null && hrtMap.get("1|5|6").getCreditRate()!=null) {
			if(cptm.getStartAmount2()!=null && new BigDecimal(cptm.getStartAmount2().toString()).compareTo(hrtMap.get("1|5|6").getCreditRate())<0){
				return "VIP用户费率小于上级成本";
			}
			if(limit.get("1|5|6")!=null && limit.get("1|5|6").getCreditRate()!=null
					&& cptm.getStartAmount2()!=null && new BigDecimal(cptm.getStartAmount2().toString()).compareTo(limit.get("1|5|6").getCreditRate())>0){
				return "VIP用户费率成本高于商户终端成本，请修改";
			}
		}else if(cptm.getStartAmount2()!=null){
			return "VIP用户费率成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 完美账单费率
		if (hrtMap.get("1|5|7")!=null && hrtMap.get("1|5|7").getCreditRate()!=null) {
			if(cptm.getEndAmount2()!=null && new BigDecimal(cptm.getEndAmount2().toString()).compareTo(hrtMap.get("1|5|7").getCreditRate())<0){
				return "完美账单费率小于上级成本";
			}
			if(limit.get("1|5|7")!=null && limit.get("1|5|7").getCreditRate()!=null
					&& cptm.getEndAmount2()!=null && new BigDecimal(cptm.getEndAmount2().toString()).compareTo(limit.get("1|5|7").getCreditRate())>0){
				return "完美账单费率成本高于商户终端成本，请修改";
			}
		}else if(cptm.getEndAmount2()!=null){
			return "完美账单费率成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 转账费
		BigDecimal vipCost=null;
		if (hrtMap.get("1|5|6")!=null && hrtMap.get("1|5|6").getCashCost()!=null) {
			vipCost=hrtMap.get("1|5|6").getCashCost();
		}
		if (hrtMap.get("1|5|7")!=null && hrtMap.get("1|5|7").getCashCost()!=null) {
			if(vipCost!=null){
				if(vipCost.compareTo(hrtMap.get("1|5|7").getCashCost())<0){
					vipCost=hrtMap.get("1|5|7").getCashCost();
				}
			}else{
				vipCost=hrtMap.get("1|5|7").getCashCost();
			}
		}
		if (vipCost!=null) {
			if(cptm.getCashAmt3()!=null && new BigDecimal(cptm.getCashAmt3().toString()).compareTo(vipCost)<0){
				return "快捷转账费小于上级成本";
			}
			if(limit.get("1|5|6")!=null && limit.get("1|5|6").getCashCost()!=null
					&& cptm.getCashAmt3()!=null && new BigDecimal(cptm.getCashAmt3().toString()).compareTo(limit.get("1|5|6").getCashCost())>0){
				return "快捷转账费成本高于商户终端成本，请修改";
			}
		}else if(cptm.getCashAmt3()!=null){
			return "快捷转账费成本上级代理未维护，请联系上级代理进行成本设置";
		}
		return null;
	}

	private String getValidateSm(CheckProfitTemplateMicroBean cptm, Map<String, CheckProfitTemplateMicroModel> microModelMap1, Map<String, HrtUnnoCostModel> hrtMap,Map<String, HrtUnnoCostModel> limit) {
		// 微信费率
		if (hrtMap.get("1|6|8")!=null && hrtMap.get("1|6|8").getCreditRate()!=null) {
			if(new BigDecimal(cptm.getScanRate().toString()).compareTo(hrtMap.get("1|6|8").getCreditRate())<0){
				//return "微信费率小于上级成本";
				return "扫码1000以下费率小于上级成本";
			}
			if(limit.get("1|6|8")!=null && limit.get("1|6|8").getCreditRate()!=null
					&& new BigDecimal(cptm.getScanRate().toString()).compareTo(limit.get("1|6|8").getCreditRate())>0){
				//return "微信费率小于上级成本";
				return "扫码1000以下费率成本高于商户终端成本，请修改";
			}
		}else{
			//return "微信费率成本上级代理未维护，请联系上级代理进行成本设置";
			return "扫码1000以下费率成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 支付宝费率
		if (hrtMap.get("1|6|9")!=null && hrtMap.get("1|6|9").getCreditRate()!=null) {
			if(new BigDecimal(cptm.getScanRate1().toString()).compareTo(hrtMap.get("1|6|9").getCreditRate())<0){
				//return "支付宝费率小于上级成本";
				return "扫码1000以上费率小于上级成本";
			}
			if(limit.get("1|6|9")!=null && limit.get("1|6|9").getCreditRate()!=null
					&& new BigDecimal(cptm.getScanRate1().toString()).compareTo(limit.get("1|6|9").getCreditRate())>0){
				//return "支付宝费率小于上级成本";
				return "扫码1000以上费率成本高于商户终端成本，请修改";
			}
		}else{
			//return "支付宝费率成本上级代理未维护，请联系上级代理进行成本设置";
			return "扫码1000以上费率成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 银联二维码费率
		if (hrtMap.get("1|6|10")!=null && hrtMap.get("1|6|10").getCreditRate()!=null) {
			if(new BigDecimal(cptm.getScanRate2().toString()).compareTo(hrtMap.get("1|6|10").getCreditRate())<0){
				return "银联二维码费率小于上级成本";
			}
			if(limit.get("1|6|10")!=null && limit.get("1|6|10").getCreditRate()!=null
					&& new BigDecimal(cptm.getScanRate2().toString()).compareTo(limit.get("1|6|10").getCreditRate())>0){
				return "银联二维码费率成本高于商户终端成本，请修改";
			}
		}else{
			return "银联二维码费率成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 扫码1000以下转账费
		if (hrtMap.get("1|6|8") != null && hrtMap.get("1|6|8").getCashCost() != null) {
			if (new BigDecimal(cptm.getCashamtunder().toString()).compareTo(hrtMap.get("1|6|8").getCashCost()) < 0) {
				return "扫码1000以下转账费小于上级成本";
			}
			if (limit.get("1|6|8")!=null && limit.get("1|6|8").getCashCost()!=null
					&& new BigDecimal(cptm.getCashamtunder().toString()).compareTo(limit.get("1|6|8").getCashCost()) > 0) {
				return "扫码1000以下转账费成本高于商户终端成本，请修改";
			}
		} else {
			return "扫码1000以下转账费成本上级代理未维护，请联系上级代理进行成本设置";
		}
		// 扫码1000以上转账费
		if (hrtMap.get("1|6|9") != null && hrtMap.get("1|6|9").getCashCost() != null) {
			if (new BigDecimal(cptm.getCashamtabove().toString()).compareTo(hrtMap.get("1|6|9").getCashCost()) < 0) {
				return "扫码1000以上转账费小于上级成本";
			}
			if (limit.get("1|6|9")!=null && limit.get("1|6|9").getCashCost()!=null
					&& new BigDecimal(cptm.getCashamtabove().toString()).compareTo(limit.get("1|6|9").getCashCost()) > 0) {
				return "扫码1000以上转账费成本高于商户终端成本，请修改";
			}
		} else {
			return "扫码1000以上转账费成本上级代理未维护，请联系上级代理进行成本设置";
		}
		// 扫码1000以上转账费
		if (hrtMap.get("1|6|10") != null && hrtMap.get("1|6|10").getCashCost() != null) {
			if (new BigDecimal(cptm.getCashAmt2().toString()).compareTo(hrtMap.get("1|6|10").getCashCost()) < 0) {
				return "银联二维码转账费小于上级成本";
			}
			if (limit.get("1|6|10")!=null && limit.get("1|6|10").getCashCost()!=null
					&& new BigDecimal(cptm.getCashAmt2().toString()).compareTo(limit.get("1|6|10").getCashCost()) > 0) {
				return "银联二维码转账费成本高于商户终端成本，请修改";
			}
		} else {
			return "银联二维码转账费成本上级代理未维护，请联系上级代理进行成本设置";
		}
		return null;
	}

	private String getValidateMd72(CheckProfitTemplateMicroBean cptm, Map<String, CheckProfitTemplateMicroModel> microModelMap1, Map<String, HrtUnnoCostModel> hrtMap,Map<String, HrtUnnoCostModel> limit) {
		// 贷记卡0.72%费率
		if (hrtMap.get("1|1|2")!=null && hrtMap.get("1|1|2").getCreditRate()!=null) {
			if(new BigDecimal(cptm.getCreditBankRate1().toString()).compareTo(hrtMap.get("1|1|2").getCreditRate())<0){
				return "秒到0.72贷记卡费率小于上级成本";
			}
			if(limit.get("1|1|2")!=null && limit.get("1|1|2").getCreditRate()!=null
					&& new BigDecimal(cptm.getCreditBankRate1().toString()).compareTo(limit.get("1|1|2").getCreditRate())>0){
				return "秒到0.72贷记卡费率成本高于商户终端成本，请修改";
			}
		}else{
			return "秒到0.72贷记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
		}
		// 利润百分比

		// 贷记卡非0.72%费率
		if (hrtMap.get("1|1|1")!=null && hrtMap.get("1|1|1").getCreditRate()!=null) {
			if(new BigDecimal(cptm.getCreditBankRate2().toString()).compareTo(hrtMap.get("1|1|1").getCreditRate())<0){
				return "秒到非0.72贷记卡费率小于上级成本";
			}
			if(limit.get("1|1|1")!=null && limit.get("1|1|1").getCreditRate()!=null
					&& new BigDecimal(cptm.getCreditBankRate2().toString()).compareTo(limit.get("1|1|1").getCreditRate())>0){
				return "秒到非0.72贷记卡费率成本高于商户终端成本，请修改";
			}
		}else{
			return "秒到非0.72贷记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
		}
		// 利润百分比

		// 转账费 一代0.6
		BigDecimal yidaiCash=null;
		if (hrtMap.get("1|1|1")!=null && hrtMap.get("1|1|1").getCashCost()!=null) {
			yidaiCash=hrtMap.get("1|1|1").getCashCost();
		}
		// 转账费 一代0.72
		if (hrtMap.get("1|1|2")!=null && hrtMap.get("1|1|2").getCashCost()!=null) {
			if(yidaiCash!=null){
				if(yidaiCash.compareTo(hrtMap.get("1|1|2").getCashCost())<0){
					yidaiCash=hrtMap.get("1|1|2").getCashCost();
				}
			}else{
				yidaiCash=hrtMap.get("1|1|2").getCashCost();
			}
		}
		if(yidaiCash!=null) {
			if(new BigDecimal(cptm.getCashAmt1().toString()).compareTo(yidaiCash)<0){
				return "秒到转账费小于上级成本";
			}
			if(limit.get("1|1|1")!=null && limit.get("1|1|1").getCashCost()!=null
					&& new BigDecimal(cptm.getCashAmt1().toString()).compareTo(limit.get("1|1|1").getCashCost())>0){
				return "秒到转账费成本高于商户终端成本，请修改";
			}
		}else{
			return "秒到转账费成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 云闪付费率
		if (hrtMap.get("1|4|5")!=null && hrtMap.get("1|4|5").getCreditRate()!=null) {
			if(new BigDecimal(cptm.getCloudQuickRate().toString()).compareTo(hrtMap.get("1|4|5").getCreditRate())<0){
				return "秒到云闪付费率小于上级成本";
			}
			if(limit.get("1|4|5")!=null && limit.get("1|4|5").getCreditRate()!=null
					&& new BigDecimal(cptm.getCloudQuickRate().toString()).compareTo(limit.get("1|4|5").getCreditRate())>0){
				return "秒到云闪付费率成本高于商户终端成本，请修改";
			}
		}else{
			return "秒到云闪付费率成本上级代理未维护，请联系上级代理进行成本设置";
		}
		return null;
	}

	private String getSubValidateMdFinancialString(CheckProfitTemplateMicroBean cptm, Map<String, CheckProfitTemplateMicroModel> microModelMap1, Map<String, CheckProfitTemplateMicroModel> profitTemplateMap,Map<String, HrtUnnoCostModel> limit) {
        // 理财上限不需要校验
		String key="1|1|1";
		// 借记卡手续费
		if (profitTemplateMap.get(key) != null && profitTemplateMap.get(key).getStartAmount()!=null) {
			if(cptm.getStartAmount()!=null && new BigDecimal(cptm.getStartAmount().toString()).compareTo(new BigDecimal(profitTemplateMap.get(key).getStartAmount().toString()))<0){
				return "理财借记卡手续费小于上级成本";
			}
		}else if(cptm.getStartAmount()!=null){
			return "理财借记卡手续费成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 借记卡费率
		if (profitTemplateMap.get(key)!=null && profitTemplateMap.get(key).getRuleThreshold()!=null) {
			if(cptm.getRuleThreshold()!=null && new BigDecimal(cptm.getRuleThreshold().toString()).compareTo(new BigDecimal(profitTemplateMap.get(key).getRuleThreshold().toString()).multiply(new BigDecimal("100")))<0){
				return "理财借记卡费率小于上级成本";
			}
		}else if(cptm.getRuleThreshold()!=null){
			return "理财借记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// T0提现费率
		if (profitTemplateMap.get(key)!=null && profitTemplateMap.get(key).getCashRate()!=null) {
			if(cptm.getCashRate()!=null && new BigDecimal(cptm.getCashRate().toString()).compareTo(new BigDecimal(profitTemplateMap.get(key).getCashRate().toString()).multiply(new BigDecimal("100")))<0){
				return "理财T0提现费率小于上级成本";
			}
		}else if(cptm.getCashRate()!=null){
			return "理财T0提现费率成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 转账费
		if (profitTemplateMap.get(key)!=null && profitTemplateMap.get(key).getCashAmt()!=null) {
			if(cptm.getCashAmt()!=null && new BigDecimal(cptm.getCashAmt().toString()).compareTo(new BigDecimal(profitTemplateMap.get(key).getCashAmt().toString()))<0){
				return "理财转账费小于上级成本";
			}
		}else if(cptm.getCashAmt()!=null){
			return "理财转账费成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 贷记卡费率
		if (profitTemplateMap.get(key)!=null && profitTemplateMap.get(key).getCreditBankRate()!=null) {
			if(cptm.getCreditBankRate()!=null && new BigDecimal(cptm.getCreditBankRate().toString()).compareTo(new BigDecimal(profitTemplateMap.get(key).getCreditBankRate().toString()).multiply(new BigDecimal("100")))<0){
				return "理财贷记卡费率小于上级成本";
			}
		}else if(cptm.getCreditBankRate()!=null){
			return "理财贷记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
		}
		return null;
	}

	private String getValidateMdFinancial(CheckProfitTemplateMicroBean cptm, Map<String, CheckProfitTemplateMicroModel> microModelMap1, Map<String, HrtUnnoCostModel> hrtMap,Map<String, HrtUnnoCostModel> limit) {
        // 理财上限不需要校验
		String hrtKey="1|2|3";
		String key="1|1|1";
		// 借记卡封顶值 不需要校验

		// 借记卡手续费
		if (hrtMap.get(hrtKey) != null && hrtMap.get(hrtKey).getDebitFeeamt()!=null) {
			if(cptm.getStartAmount()!=null && new BigDecimal(cptm.getStartAmount().toString()).compareTo(hrtMap.get(hrtKey).getDebitFeeamt())<0){
				return "理财借记卡手续费小于上级成本";
			}
		}else if(cptm.getStartAmount()!=null){
			return "理财借记卡手续费成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 借记卡费率
		if (hrtMap.get(hrtKey)!=null && hrtMap.get(hrtKey).getDebitRate()!=null) {
			if(cptm.getRuleThreshold()!=null && new BigDecimal(cptm.getRuleThreshold().toString()).compareTo(hrtMap.get(hrtKey).getDebitRate())<0){
				return "理财借记卡费率小于上级成本";
			}
		}else if(cptm.getRuleThreshold()!=null){
			return "理财借记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// T0提现费率
		if (hrtMap.get(hrtKey)!=null && hrtMap.get(hrtKey).getCashRate()!=null) {
			if(cptm.getCashRate()!=null && new BigDecimal(cptm.getCashRate().toString()).compareTo(hrtMap.get(hrtKey).getCashRate())<0){
				return "理财T0提现费率小于上级成本";
			}
		}else if(cptm.getCashRate()!=null){
			return "理财T0提现费率成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 转账费
		if (hrtMap.get(hrtKey)!=null && hrtMap.get(hrtKey).getCashCost()!=null) {
			if(cptm.getCashAmt()!=null && new BigDecimal(cptm.getCashAmt().toString()).compareTo(hrtMap.get(hrtKey).getCashCost())<0){
				return "理财转账费小于上级成本";
			}
		}else if(cptm.getCashAmt()!=null){
			return "理财转账费成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 贷记卡费率
		if (hrtMap.get(hrtKey)!=null && hrtMap.get(hrtKey).getCreditRate()!=null) {
			if(cptm.getCreditBankRate()!=null && new BigDecimal(cptm.getCreditBankRate().toString()).compareTo(hrtMap.get(hrtKey).getCreditRate())<0){
				return "理财贷记卡费率小于上级成本";
			}
		}else if(cptm.getCreditBankRate()!=null){
			return "理财贷记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
		}

		// 利润百分比 不做限制 都为1
		return null;
	}

    @Override
    public String validatePlusMposTempla(CheckProfitTemplateMicroBean cptm, UserBean user,Integer checkType){
		// @author:lxg-20190729 Plus成本校验
    	// @author:ztt-20191203,统一划分微信、支付宝为扫码类后，修改相关内容
		if(user.getUnitLvl()>=2) {
			String str = cptm.getTempname();
			Integer rebateType=null;
			JSONArray json1 = JSONArray.parseArray(str);
			for(int i=0;i<json1.size();i++) {
				String profitRule = JSONObject.parseObject(json1.getString(i)).getString("profitRule");
				//微信1000以上0.38费率
				String creditBankRate = JSONObject.parseObject(json1.getString(i)).getString("creditBankRate");
				//微信1000以上0.38转账费
				String cashRate = JSONObject.parseObject(json1.getString(i)).getString("cashRate");
				//微信1000以上0.45费率
				String ruleThreshold = JSONObject.parseObject(json1.getString(i)).getString("ruleThreshold");
				//微信1000以上0.45转账费
				String startAmount = JSONObject.parseObject(json1.getString(i)).getString("startAmount");
				//微信1000以下费率
				String scanRate = JSONObject.parseObject(json1.getString(i)).getString("scanRate");
				//微信1000以下转账费
				String cashAmt1 = JSONObject.parseObject(json1.getString(i)).getString("cashAmt1");
				//支付宝上费率
				String scanRate1 = JSONObject.parseObject(json1.getString(i)).getString("scanRate1");
				//支付宝转账费
				String profitPercent1 = JSONObject.parseObject(json1.getString(i)).getString("profitPercent1");
				//二维码费率
				String scanRate2 = JSONObject.parseObject(json1.getString(i)).getString("scanRate2");
				//二维码转账费
				String cashAmt2 = JSONObject.parseObject(json1.getString(i)).getString("cashAmt2");
				//秒到刷卡费率
				String subRate = JSONObject.parseObject(json1.getString(i)).getString("subRate");
				//秒到刷卡转账费
				String cashAmt = JSONObject.parseObject(json1.getString(i)).getString("cashAmt");
				//云闪付费率
				String cloudQuickRate = JSONObject.parseObject(json1.getString(i)).getString("cloudQuickRate");

				//花呗费率
				String huabeiRate = JSONObject.parseObject(json1.getString(i)).getString("huabeiRate");
				//花呗转账费
				String huabeiFee = JSONObject.parseObject(json1.getString(i)).getString("huabeiFee");

				rebateType = Integer.parseInt(profitRule);
				
				String subtype = querySubTypeByPlusProfitRule(rebateType);
				
				String key = "6|0|" + rebateType;
				String keyHrt = "1|1|" + rebateType;
				String keyLimit = "1|1|20";
				// TODO @author:lxg-20200521 Mpos活动多级成本上限限制
				Map<String, HrtUnnoCostModel> limit = agentUnitService.getAllLimitHrtCostMap();
				if (user.getUnitLvl() == 2) {
					// 上级为1代成本
					// 扫码成本 微信1000以下费率
					Map<String, HrtUnnoCostModel> hrtMap = agentUnitService.getAllHrtCostMap(1, user.getUnNo(), 2,checkType);
					if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getCreditRate() != null) {
						BigDecimal parent = hrtMap.get(keyHrt).getCreditRate();
						if (scanRate!=null && new BigDecimal(scanRate).compareTo(parent) < 0) {
							return "扫码1000以下（终端0.38）费率小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getCreditRate()!=null
								&& scanRate!=null && new BigDecimal(scanRate).compareTo(limit.get(keyLimit).getCreditRate()) > 0) {
							return "扫码1000以下（终端0.38）费率成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以下（终端0.38）费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
					// 微信1000以上0.38费率
					if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getWxUpRate() != null) {
						BigDecimal parent = hrtMap.get(keyHrt).getWxUpRate();
						if (creditBankRate!=null && new BigDecimal(creditBankRate).compareTo(parent) < 0) {
							return "扫码1000以上（终端0.38）费率小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpRate()!=null
								&& creditBankRate!=null && new BigDecimal(creditBankRate).compareTo(limit.get(keyLimit).getWxUpRate()) > 0) {
							return "扫码1000以上（终端0.38）费率成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以上（终端0.38）费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//微信1000以上0.45费率
					if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getWxUpRate1() != null) {
						BigDecimal parent = hrtMap.get(keyHrt).getWxUpRate1();
						if (ruleThreshold!=null && new BigDecimal(ruleThreshold).compareTo(parent) < 0) {
							return "扫码1000以上（终端0.45）费率小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpRate1()!=null
								&& ruleThreshold!=null && new BigDecimal(ruleThreshold).compareTo(limit.get(keyLimit).getWxUpRate1()) > 0) {
							return "扫码1000以上（终端0.45）费率成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以上（终端0.45）费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//支付宝费率
					if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getZfbRate() != null) {
						BigDecimal parent = hrtMap.get(keyHrt).getZfbRate();
						if (scanRate1!=null && new BigDecimal(scanRate1).compareTo(parent) < 0) {
							return "扫码1000以下（终端0.45）费率小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getZfbRate()!=null
								&& scanRate1!=null && new BigDecimal(scanRate1).compareTo(limit.get(keyLimit).getZfbRate()) > 0) {
							return "扫码1000以下（终端0.45）费率成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以下（终端0.45）费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//二维码费率
					if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getEwmRate() != null) {
						BigDecimal parent = hrtMap.get(keyHrt).getEwmRate();
						if (scanRate2!=null && new BigDecimal(scanRate2).compareTo(parent) < 0) {
							return "银联二维码费率小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getEwmRate()!=null
								&& scanRate2!=null && new BigDecimal(scanRate2).compareTo(limit.get(keyLimit).getEwmRate()) > 0) {
							return "银联二维码费率成本高于商户终端成本，请修改";
						}
					} else {
						return "银联二维码费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//云闪付费率
					if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getYsfRate() != null) {
						BigDecimal parent = hrtMap.get(keyHrt).getYsfRate();
						if (cloudQuickRate!=null && new BigDecimal(cloudQuickRate).compareTo(parent) < 0) {
							return "云闪付费率小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getYsfRate()!=null
								&& cloudQuickRate!=null && new BigDecimal(cloudQuickRate).compareTo(limit.get(keyLimit).getYsfRate()) > 0) {
							return "云闪付费率成本高于商户终端成本，请修改";
						}
					} else {
						return "云闪付费率成本上级代理未维护，请联系上级代理进行成本设置";
					}

					// 扫码提现成本 微信1000以下转账费
					if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getCashCost() != null) {
						BigDecimal parent = hrtMap.get(keyHrt).getCashCost();
						if (cashAmt1!=null && new BigDecimal(cashAmt1).compareTo(parent) < 0) {
							return "扫码1000以下（终端0.38）转账费小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getCashCost()!=null
								&& cashAmt1!=null && new BigDecimal(cashAmt1).compareTo(limit.get(keyLimit).getCashCost()) > 0) {
							return "扫码1000以下（终端0.38）转账费成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以下（终端0.38）转账费成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//微信1000以上0.38转账费
					if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getWxUpCash() != null) {
						BigDecimal parent = hrtMap.get(keyHrt).getWxUpCash();
						if (cashRate!=null && new BigDecimal(cashRate).compareTo(parent) < 0) {
							return "扫码1000以上（终端0.38）转账费小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpCash()!=null
								&& cashRate!=null && new BigDecimal(cashRate).compareTo(limit.get(keyLimit).getWxUpCash()) > 0) {
							return "扫码1000以上（终端0.38）转账费成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以上（终端0.38）转账费成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//微信1000以上0.45转账费
					if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getWxUpCash1() != null) {
						BigDecimal parent = hrtMap.get(keyHrt).getWxUpCash1();
						if (startAmount!=null && new BigDecimal(startAmount).compareTo(parent) < 0) {
							return "扫码1000以上（终端0.45）转账费小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpCash1()!=null
								&& startAmount!=null && new BigDecimal(startAmount).compareTo(limit.get(keyLimit).getWxUpCash1()) > 0) {
							return "扫码1000以上（终端0.45）转账费成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以上（终端0.45）转账费成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//支付宝转账费
					if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getZfbCash() != null) {
						BigDecimal parent = hrtMap.get(keyHrt).getZfbCash();
						if (profitPercent1!=null && new BigDecimal(profitPercent1).compareTo(parent) < 0) {
							return "扫码1000以下（终端0.45）转账费小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getZfbCash()!=null
								&& profitPercent1!=null && new BigDecimal(profitPercent1).compareTo(limit.get(keyLimit).getZfbCash()) > 0) {
							return "扫码1000以下（终端0.45）转账费成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以下（终端0.45）转账费成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//二维码转账费
					if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getEwmCash() != null) {
						BigDecimal parent = hrtMap.get(keyHrt).getEwmCash();
						if (cashAmt2!=null && new BigDecimal(cashAmt2).compareTo(parent) < 0) {
							return "银联二维码转账费小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getEwmCash()!=null
								&& cashAmt2!=null && new BigDecimal(cashAmt2).compareTo(limit.get(keyLimit).getEwmCash()) > 0) {
							return "银联二维码转账费成本高于商户终端成本，请修改";
						}
					} else {
						return "银联二维码转账费成本上级代理未维护，请联系上级代理进行成本设置";
					}

//					if(rebateType==23 || rebateType==82){
						// 刷卡提现成本
						if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getDebitFeeamt() != null) {
							BigDecimal parent = hrtMap.get(keyHrt).getDebitFeeamt();
							if (cashAmt!=null && new BigDecimal(cashAmt).compareTo(parent) < 0) {
								return "刷卡提现成本小于上级成本";
							}
							if (limit.get(keyLimit)!=null && limit.get(keyLimit).getDebitFeeamt()!=null
									&& cashAmt!=null && new BigDecimal(cashAmt).compareTo(limit.get(keyLimit).getDebitFeeamt()) > 0) {
								return "刷卡提现成本高于商户终端成本，请修改";
							}
						} else {
							return "刷卡提现成本上级代理未维护，请联系上级代理进行成本设置";
						}
//					}

					// 刷卡成本
					if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getDebitRate() != null) {
						BigDecimal parent = hrtMap.get(keyHrt).getDebitRate();
						if (subRate!=null && new BigDecimal(subRate).compareTo(parent) < 0) {
							return "刷卡成本小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getDebitRate()!=null
								&& subRate!=null && new BigDecimal(subRate).compareTo(limit.get(keyLimit).getDebitRate()) > 0) {
							return "刷卡成本高于商户终端成本，请修改";
						}
					} else {
						return "刷卡成本上级代理未维护，请联系上级代理进行成本设置";
					}
					
					// 花呗
					if(subtype!=null && "2".equals(subtype)){
						//花呗费率
						if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getHbRate() != null) {
							BigDecimal parent = hrtMap.get(keyHrt).getHbRate();
							if (huabeiRate!=null && new BigDecimal(huabeiRate).compareTo(parent) < 0) {
								return "花呗费率小于上级成本";
							}
							if (limit.get(keyLimit)!=null && limit.get(keyLimit).getHbRate()!=null
									&& huabeiRate!=null && new BigDecimal(huabeiRate).compareTo(limit.get(keyLimit).getHbRate()) > 0) {
								return "花呗费率成本高于商户终端成本，请修改";
							}
						} else {
							return "花呗费率成本上级代理未维护，请联系上级代理进行成本设置";
						}
						//花呗转账费
						if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getHbCash() != null) {
							BigDecimal parent = hrtMap.get(keyHrt).getHbCash();
							if (huabeiFee!=null && new BigDecimal(huabeiFee).compareTo(parent) < 0) {
								return "花呗转账费小于上级成本";
							}
							if (limit.get(keyLimit)!=null && limit.get(keyLimit).getHbCash()!=null
									&& huabeiFee!=null && new BigDecimal(huabeiFee).compareTo(limit.get(keyLimit).getHbCash()) > 0) {
								return "花呗转账费成本高于商户终端成本，请修改";
							}
						} else {
							return "花呗转账费成本上级代理未维护，请联系上级代理进行成本设置";
						}
					}
					
				} else {
					Map<String, CheckProfitTemplateMicroModel> t = getProfitTemplateMap(1, user.getUnNo(), user.getUnitLvl(),checkType);
					// 刷卡
					if (t.get(key) != null && t.get(key).getSubRate() != null) {
						BigDecimal parent = new BigDecimal(t.get(key).getSubRate().toString()).multiply(new BigDecimal(100));
						if (subRate!=null && new BigDecimal(subRate).compareTo(parent) < 0) {
							return "刷卡成本小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getDebitRate()!=null
								&& subRate!=null && new BigDecimal(subRate).compareTo(limit.get(keyLimit).getDebitRate()) > 0) {
							return "刷卡成本高于商户终端成本，请修改";
						}
					} else {
						return "刷卡成本上级代理未维护，请联系上级代理进行成本设置";
					}

					if(rebateType==23 || rebateType==82){
						// 刷卡提现成本
						if (t.get(key) != null && t.get(key).getCashAmt() != null) {
							BigDecimal parent = new BigDecimal(t.get(key).getCashAmt().toString());
							if (cashAmt!=null && new BigDecimal(cashAmt).compareTo(parent) < 0) {
								return "刷卡提现成本小于上级成本";
							}
							if (limit.get(keyLimit)!=null && limit.get(keyLimit).getDebitFeeamt()!=null
									&& cashAmt!=null && new BigDecimal(cashAmt).compareTo(limit.get(keyLimit).getDebitFeeamt()) > 0) {
								return "刷卡提现成本高于商户终端成本，请修改";
							}
						} else {
							return "刷卡提现成本上级代理未维护，请联系上级代理进行成本设置";
						}
					}

					// 扫码成本 微信1000以下
					if (t.get(key) != null && t.get(key).getScanRate() != null) {
						BigDecimal parent = new BigDecimal(t.get(key).getScanRate().toString()).multiply(new BigDecimal(100));
						if (scanRate!=null && new BigDecimal(scanRate).compareTo(parent) < 0) {
							return "扫码1000以下（终端0.38）费率小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getCreditRate()!=null
								&& scanRate!=null && new BigDecimal(scanRate).compareTo(limit.get(keyLimit).getCreditRate()) > 0) {
							return "扫码1000以下（终端0.38）费率成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以下（终端0.38）费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//微信1000以上0.38费率
					if (t.get(key) != null && t.get(key).getCreditBankRate() != null) {
						BigDecimal parent = new BigDecimal(t.get(key).getCreditBankRate().toString()).multiply(new BigDecimal(100));
						if (creditBankRate!=null && new BigDecimal(creditBankRate).compareTo(parent) < 0) {
							return "扫码1000以上（终端0.38）费率小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpRate()!=null
								&& creditBankRate!=null && new BigDecimal(creditBankRate).compareTo(limit.get(keyLimit).getWxUpRate()) > 0) {
							return "扫码1000以上（终端0.38）费率成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以上（终端0.38）费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//微信1000以上0.45费率
					if (t.get(key) != null && t.get(key).getRuleThreshold() != null) {
						BigDecimal parent = new BigDecimal(t.get(key).getRuleThreshold().toString()).multiply(new BigDecimal(100));
						if (ruleThreshold!=null && new BigDecimal(ruleThreshold).compareTo(parent) < 0) {
							return "扫码1000以上（终端0.45）费率小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpRate1()!=null
								&& ruleThreshold!=null && new BigDecimal(ruleThreshold).compareTo(limit.get(keyLimit).getWxUpRate1()) > 0) {
							return "扫码1000以上（终端0.45）费率成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以上（终端0.45）费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//支付宝费率
					if (t.get(key) != null && t.get(key).getScanRate1() != null) {
						BigDecimal parent = new BigDecimal(t.get(key).getScanRate1().toString()).multiply(new BigDecimal(100));
						if (scanRate1!=null && new BigDecimal(scanRate1).compareTo(parent) < 0) {
							return "扫码1000以下（终端0.45）费率小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getZfbRate()!=null
								&& scanRate1!=null && new BigDecimal(scanRate1).compareTo(limit.get(keyLimit).getZfbRate()) > 0) {
							return "扫码1000以下（终端0.45）费率成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以下（终端0.45）费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//二维码费率
					if (t.get(key) != null && t.get(key).getScanRate2() != null) {
						BigDecimal parent = new BigDecimal(t.get(key).getScanRate2().toString()).multiply(new BigDecimal(100));
						if (scanRate2!=null && new BigDecimal(scanRate2).compareTo(parent) < 0) {
							return "银联二维码费率小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getEwmRate()!=null
								&& scanRate2!=null && new BigDecimal(scanRate2).compareTo(limit.get(keyLimit).getEwmRate()) > 0) {
							return "银联二维码费率成本高于商户终端成本，请修改";
						}
					} else {
						return "银联二维码费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//云闪付费率
					if (t.get(key) != null && t.get(key).getCloudQuickRate() != null) {
						BigDecimal parent = new BigDecimal(t.get(key).getCloudQuickRate().toString()).multiply(new BigDecimal(100));
						if (cloudQuickRate!=null && new BigDecimal(cloudQuickRate).compareTo(parent) < 0) {
							return "云闪付费率小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getYsfRate()!=null
								&& cloudQuickRate!=null && new BigDecimal(cloudQuickRate).compareTo(limit.get(keyLimit).getYsfRate()) > 0) {
							return "云闪付费率成本高于商户终端成本，请修改";
						}
					} else {
						return "云闪付费率成本上级代理未维护，请联系上级代理进行成本设置";
					}

					// 扫码提现成本
					if (t.get(key) != null && t.get(key).getCashAmt1() != null) {
						BigDecimal parentCash = new BigDecimal(t.get(key).getCashAmt1().toString());
						if (cashAmt1!=null && new BigDecimal(cashAmt1).compareTo(parentCash) < 0) {
							return "扫码1000以下（终端0.38）转账费小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getCashCost()!=null
								&& cashAmt1!=null && new BigDecimal(cashAmt1).compareTo(limit.get(keyLimit).getCashCost()) > 0) {
							return "扫码1000以下（终端0.38）转账费成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以下（终端0.38）转账费成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//微信1000以上0.38转账费
					if (t.get(key) != null && t.get(key).getCashRate() != null) {
						BigDecimal parentCash = new BigDecimal(t.get(key).getCashRate().toString());
						if (cashRate!=null && new BigDecimal(cashRate).compareTo(parentCash) < 0) {
							return "扫码1000以上（终端0.38）转账费小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpCash()!=null
								&& cashRate!=null && new BigDecimal(cashRate).compareTo(limit.get(keyLimit).getWxUpCash()) > 0) {
							return "扫码1000以上（终端0.38）转账费成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以上（终端0.38）转账费成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//微信1000以上0.45转账费
					if (t.get(key) != null && t.get(key).getStartAmount() != null) {
						BigDecimal parentCash = new BigDecimal(t.get(key).getStartAmount().toString());
						if (startAmount!=null && new BigDecimal(startAmount).compareTo(parentCash) < 0) {
							return "扫码1000以上（终端0.45）转账费小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpCash1()!=null
								&& startAmount!=null && new BigDecimal(startAmount).compareTo(limit.get(keyLimit).getWxUpCash1()) > 0) {
							return "扫码1000以上（终端0.45）转账费成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以上（终端0.45）转账费成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//支付宝转账费
					if (t.get(key) != null && t.get(key).getProfitPercent1() != null) {
						BigDecimal parentCash = new BigDecimal(t.get(key).getProfitPercent1().toString());
						if (profitPercent1!=null && new BigDecimal(profitPercent1).compareTo(parentCash) < 0) {
							return "扫码1000以下（终端0.45）转账费小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getZfbCash()!=null
								&& profitPercent1!=null && new BigDecimal(profitPercent1).compareTo(limit.get(keyLimit).getZfbCash()) > 0) {
							return "扫码1000以下（终端0.45）转账费成本高于商户终端成本，请修改";
						}
					} else {
						return "扫码1000以下（终端0.45）转账费成本上级代理未维护，请联系上级代理进行成本设置";
					}
//					//二维码转账费
					if (t.get(key) != null && t.get(key).getCashAmt2() != null) {
						BigDecimal parentCash = new BigDecimal(t.get(key).getCashAmt2().toString());
						if (cashAmt2!=null && new BigDecimal(cashAmt2).compareTo(parentCash) < 0) {
							return "银联二维码转账费小于上级成本";
						}
						if (limit.get(keyLimit)!=null && limit.get(keyLimit).getEwmCash()!=null
								&& cashAmt2!=null && new BigDecimal(cashAmt2).compareTo(limit.get(keyLimit).getEwmCash()) > 0) {
							return "银联二维码转账费成本高于商户终端成本，请修改";
						}
					} else {
						return "银联二维码转账费成本上级代理未维护，请联系上级代理进行成本设置";
					}
					//花呗
					if(subtype!=null&&"2".equals(subtype)){
						//花呗费率
						if (t.get(key) != null && t.get(key).getHuabeiRate() != null) {
							BigDecimal parent = new BigDecimal(t.get(key).getHuabeiRate().toString()).multiply(new BigDecimal(100));
							if (huabeiRate!=null && new BigDecimal(huabeiRate).compareTo(parent) < 0) {
								return "花呗费率小于上级成本";
							}
							if (limit.get(keyLimit)!=null && limit.get(keyLimit).getHbRate()!=null
									&& huabeiRate!=null && new BigDecimal(huabeiRate).compareTo(limit.get(keyLimit).getHbRate()) > 0) {
								return "花呗费率成本高于商户终端成本，请修改";
							}
						} else {
							return "花呗费率成本上级代理未维护，请联系上级代理进行成本设置";
						}
						//花呗转账费
						if (t.get(key) != null && t.get(key).getHuabeiFee() != null) {
							BigDecimal parentCash = new BigDecimal(t.get(key).getHuabeiFee().toString());
							if (huabeiFee!=null && new BigDecimal(huabeiFee).compareTo(parentCash) < 0) {
								return "花呗转账费小于上级成本";
							}
							if (limit.get(keyLimit)!=null && limit.get(keyLimit).getHbCash()!=null
									&& huabeiFee!=null && new BigDecimal(huabeiFee).compareTo(limit.get(keyLimit).getHbCash()) > 0) {
								return "花呗转账费成本高于商户终端成本，请修改";
							}
						} else {
							return "花呗转账费成本上级代理未维护，请联系上级代理进行成本设置";
						}
					}
				}
			}
		}
	    return null;
    }

    @Override
    public String validateSytTemplate(CheckProfitTemplateMicroBean cptm, UserBean user,Integer checkType){
		int  rebateType = cptm.getProfitRule();
		String subtype = querySubTypeByProfitRule(rebateType);
		String key = "5|0|" + rebateType;
		String keyHrt = "1|1|" + rebateType;

		String keyLimit = "1|1|20";
		// TODO @author:lxg-20200521 收银台多级成本上限限制
		Map<String, HrtUnnoCostModel> limit = agentUnitService.getAllLimitHrtCostMap();
		// 后台存的数据
//				MicroModel.setScanRate(Double.parseDouble(new BigDecimal(cptm.getRuleThreshold()扫码1000以上（终端0.45）费率+"").divide(new BigDecimal("100"))+""));
//				MicroModel.setProfitPercent1(cptm.getStartAmount()扫码1000以上（终端0.45）转账费);
//				MicroModel.setSubRate(Double.parseDouble(new BigDecimal(cptm.getScanRate()扫码1000以下（终端0.38）费率+"").divide(new BigDecimal("100"))+""));
//				MicroModel.setCashAmt1(cptm.getProfitPercent1()扫码1000以下（终端0.45）转账费);
//				MicroModel.setCashAmt(cptm.getCashAmt1()扫码1000以下（终端0.38）转账费);
		// 分配时,按取存储的字段
		return validateHybCashTemplaRateInfo(
				cptm.getCreditBankRate()==null?null:cptm.getCreditBankRate()+"",
				cptm.getCashRate()==null?null:cptm.getCashRate()+"",
				cptm.getScanRate()==null?null:cptm.getScanRate()+"",
				cptm.getProfitPercent1()==null?null:cptm.getProfitPercent1()+"",
				cptm.getSubRate()==null?null:cptm.getSubRate()+"",
				cptm.getCashAmt()==null?null:cptm.getCashAmt()+"",
				cptm.getScanRate1()==null?null:cptm.getScanRate1()+"",
				cptm.getCashAmt1()==null?null:cptm.getCashAmt1()+"",
				cptm.getScanRate2()==null?null:cptm.getScanRate2()+"",
				cptm.getCashAmt2()==null?null:cptm.getCashAmt2()+"",
				cptm.getHuabeiRate()==null?null:cptm.getHuabeiRate()+"",
				cptm.getHuabeiFee()==null?null:cptm.getHuabeiFee()+"",
				key,keyHrt,keyLimit,limit,checkType,subtype,user);
    }

	/**
	 * 代还模板校验
	 * @param cptm
	 * @param user
	 * @param checkType
	 * @return
	 */
	@Override
	public String validateSubTemplate(CheckProfitTemplateMicroBean cptm, UserBean user,Integer checkType){
		// TODO @author:lxg-20200521 代还多级成本上限限制
		Map<String, HrtUnnoCostModel> limit = agentUnitService.getAllLimitHrtCostMap();
		if(cptm.getSubRate()!=null && limit.get("1|3|4").getCreditRate()!=null ){
			BigDecimal limitRate = limit.get("1|3|4").getCreditRate();
			if (new BigDecimal(cptm.getSubRate().toString()).compareTo(limitRate)>0) {
				return "代还成本高于商户终端成本，请修改";
			}
		}

		if(user.getUnitLvl()>=2) {
			// 下级机构最小成本
//			Map<String,CheckProfitTemplateMicroModel> microModelMap = getProfitTemplateMap(3, user.getUnNo(), user.getUnitLvl());
//			BigDecimal min = microModelMap.get("4|0|0")!=null&&microModelMap.get("4|0|0").getSubRate()!=null?
//					new BigDecimal(microModelMap.get("4|0|0").getSubRate().toString()).multiply(new BigDecimal("100")):new BigDecimal("0.75");
			if (user.getUnitLvl() == 2) {
				// 上级为1代成本
				Map<String, HrtUnnoCostModel> hrtMap = agentUnitService.getAllHrtCostMap(1, user.getUnNo(), 2,checkType);
				if(hrtMap.get("1|3|4")!=null && hrtMap.get("1|3|4").getCreditRate()!=null){
					BigDecimal parent = hrtMap.get("1|3|4").getCreditRate();
					if (cptm.getSubRate()!=null && new BigDecimal(cptm.getSubRate().toString()).compareTo(parent)<0) {
						return "代还成本小于上级成本";
					}
				}else{
					return "代还成本上级代理未维护，请联系上级代理进行成本设置";
				}
			} else {
				Map<String,CheckProfitTemplateMicroModel> t = getProfitTemplateMap(1,user.getUnNo(),user.getUnitLvl(),checkType);
				if(t.get("4|0|0")!=null && t.get("4|0|0").getSubRate()!=null){
					BigDecimal parent = new BigDecimal(t.get("4|0|0").getSubRate().toString()).multiply(new BigDecimal(100));
					if (cptm.getSubRate()!=null && new BigDecimal(cptm.getSubRate().toString()).compareTo(parent)<0) {
						return "代还成本小于上级成本";
					}
				}else{
					return "代还成本上级代理未维护，请联系上级代理进行成本设置";
				}
			}
		}
		return null;
	}

	/**
	 * 分润模版修改查询
	 * @throws Exception
	 */
	public CheckProfitTemplateMicroBean queryupdateProfitmicro(CheckProfitTemplateMicroBean cptm,UserBean user) throws Exception{
		String sql="select * from CHECK_MICRO_PROFITTEMPLATE where TEMPNAME='"+java.net.URLDecoder.decode(cptm.getTempName(),"UTF-8").trim()+"' and MatainType in ('A','M') ";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		//为总公司
		if("110000".equals(user.getUnNo())){
		 }
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and unno ='"+user.getUnNo()+"'";
		}
		List<CheckProfitTemplateMicroModel> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql, null,CheckProfitTemplateMicroModel.class );
		CheckProfitTemplateMicroBean cpt= new CheckProfitTemplateMicroBean();
		for(int i=0;i<list.size();i++){
			CheckProfitTemplateMicroModel cc =list.get(i);
			if(cc.getSettMethod()=="000000"||"000000".equals(cc.getSettMethod())){
				cpt.setStartAmount(cc.getStartAmount());
				cpt.setEndAmount(cc.getEndAmount());
				cpt.setRuleThreshold(cc.getRuleThreshold());
				cpt.setCreditBankRate(cc.getCreditBankRate());
				cpt.setCashAmt(cc.getCashAmt());
				cpt.setCashRate(cc.getCashRate());
				cpt.setScanRate(cc.getScanRate());
				cpt.setScanRate1(cc.getScanRate1());
				cpt.setScanRate2(cc.getScanRate2());
				cpt.setCashAmt2(cc.getCashAmt1());
				cpt.setCashAmt3(cc.getCashAmt2());
				cpt.setProfitPercent(cc.getProfitPercent());
				cpt.setProfitPercent3(cc.getProfitPercent1());
			}
			if((cc.getSettMethod()=="100000"||"100000".equals(cc.getSettMethod()))&&cc.getMerchantType()==2){
				cpt.setCreditBankRate1(cc.getCreditBankRate());
				cpt.setCashAmt1(cc.getCashAmt());
				cpt.setProfitPercent1(cc.getProfitPercent());
				cpt.setStartAmount2(cc.getStartAmount());
				cpt.setEndAmount2(cc.getEndAmount());
				cpt.setRuleThreshold2(cc.getRuleThreshold());
			}else if ((cc.getSettMethod()=="100000"||"100000".equals(cc.getSettMethod()))&&cc.getMerchantType()==3){
				cpt.setCreditBankRate2(cc.getCreditBankRate());
				cpt.setProfitPercent2(cc.getProfitPercent());
			}
			cpt.setCloudQuickRate(cc.getCloudQuickRate());
			cpt.setTempName(cc.getTempName());
		}
		return cpt;
	}

    private CheckProfitTemplateMicroBean queryupdateProfitmicroOnly(CheckProfitTemplateMicroBean cptm,UserBean user) throws Exception{
        String sql="select * from CHECK_MICRO_PROFITTEMPLATE where TEMPNAME='"+cptm.getTempName()+"' and MatainType in ('A','M') and unno ='"+user.getUnNo()+"'";
        List<CheckProfitTemplateMicroModel> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql, null,CheckProfitTemplateMicroModel.class );
        CheckProfitTemplateMicroBean cpt= new CheckProfitTemplateMicroBean();
        for(int i=0;i<list.size();i++){
            CheckProfitTemplateMicroModel cc =list.get(i);
            if(cc.getSettMethod()=="000000"||"000000".equals(cc.getSettMethod())){
                cpt.setStartAmount(cc.getStartAmount());
                cpt.setEndAmount(cc.getEndAmount());
                if(cc.getRuleThreshold()!=null) {
					cpt.setRuleThreshold(Double.parseDouble(new BigDecimal(cc.getRuleThreshold()+"").multiply(new BigDecimal("100"))+""));
				}
                if(cc.getCreditBankRate()!=null) {
					cpt.setCreditBankRate(Double.parseDouble(new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100"))+""));
				}
                cpt.setCashAmt(cc.getCashAmt());
				if (cc.getCashRate() != null) {
					cpt.setCashRate(Double.parseDouble(new BigDecimal(cc.getCashRate()+"").multiply(new BigDecimal("100"))+""));
				}
				if (cc.getScanRate() != null) {
					cpt.setScanRate(Double.parseDouble(new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100"))+""));
				}
				if (cc.getScanRate1() != null) {
					cpt.setScanRate1(Double.parseDouble(new BigDecimal(cc.getScanRate1()+"").multiply(new BigDecimal("100"))+""));
				}
				if (cc.getScanRate2() != null) {
					cpt.setScanRate2(Double.parseDouble(new BigDecimal(cc.getScanRate2()+"").multiply(new BigDecimal("100"))+""));
				}
                cpt.setCashAmt2(cc.getCashAmt1());
                cpt.setCashAmt3(cc.getCashAmt2());
                cpt.setProfitPercent(cc.getProfitPercent());
                cpt.setProfitPercent3(cc.getProfitPercent1());
            }
            if((cc.getSettMethod()=="100000"||"100000".equals(cc.getSettMethod()))&&cc.getMerchantType()==2){
				if (cc.getCreditBankRate() != null) {
					cpt.setCreditBankRate1(Double.parseDouble(new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100"))+""));
				}
                cpt.setCashAmt1(cc.getCashAmt());
                cpt.setProfitPercent1(cc.getProfitPercent());
				if (cc.getStartAmount() != null) {
					cpt.setStartAmount2(Double.parseDouble(new BigDecimal(cc.getStartAmount()+"").multiply(new BigDecimal("100"))+""));
				}
				if (cc.getEndAmount() != null) {
					cpt.setEndAmount2(Double.parseDouble(new BigDecimal(cc.getEndAmount()+"").multiply(new BigDecimal("100"))+""));
				}
				if (cc.getRuleThreshold() != null) {
					cpt.setRuleThreshold2(Double.parseDouble(new BigDecimal(cc.getRuleThreshold()+"").multiply(new BigDecimal("100"))+""));
				}
				cpt.setCashamtabove(cc.getCashamtabove());
				cpt.setCashamtunder(cc.getCashamtunder());
            }else if ((cc.getSettMethod()=="100000"||"100000".equals(cc.getSettMethod()))&&cc.getMerchantType()==3){
				if (cc.getCreditBankRate() != null) {
					cpt.setCreditBankRate2(Double.parseDouble(new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100"))+""));
				}
                cpt.setProfitPercent2(cc.getProfitPercent());
            }
            if(cc.getCloudQuickRate()!=null) {
				cpt.setCloudQuickRate(Double.parseDouble(new BigDecimal(cc.getCloudQuickRate() + "").multiply(new BigDecimal("100")) + ""));
			}
            cpt.setTempName(cc.getTempName());
        }
        return cpt;
    }

	/**
	 * 手刷实时生效模板与下月生效模板查询
	 * @param type 1:实时生效 2:下月生效
	 * @param cptm
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public CheckProfitTemplateMicroBean getMdCheckProfitTemplateMicroBeanInfo(int type,CheckProfitTemplateMicroBean cptm) throws UnsupportedEncodingException {
		CheckProfitTemplateMicroBean cpt = new CheckProfitTemplateMicroBean();
	    if(type==1){
			if(DateTools.isFirstDay()){
				String sqlUse="select * from CHECK_MICRO_PROFITTEMPLATE_W " +
						" where TEMPNAME='"+java.net.URLDecoder.decode(cptm.getTempName(),"UTF-8").trim()+"' " +
						" and MatainType in ('A','M','P')  and mataindate>=trunc(sysdate-2,'mm') and mataindate<trunc(sysdate)";
				List<CheckProfitTemplateMicroWModel> list=checkUnitProfitMicroWDao.queryObjectsBySqlList(sqlUse, null,CheckProfitTemplateMicroWModel.class );
				if(list.size()>0){
					cpt=getCheckProfitTemplateMicroBeanInfo(null,list);
					return cpt;
				}else{
					String sqlOld="select * from CHECK_MICRO_PROFITTEMPLATE where TEMPNAME='"+java.net.URLDecoder.decode(cptm.getTempName(),"UTF-8").trim()+"' and MatainType in ('A','M','P') ";
					List<CheckProfitTemplateMicroModel> listOld=checkUnitProfitMicroDao.queryObjectsBySqlList(sqlOld, null,CheckProfitTemplateMicroModel.class );
					if(listOld.size()>0){
						cpt=getCheckProfitTemplateMicroBeanInfo(listOld,null);
						return cpt;
					}
				}
			}else{
				String sqlOld="select * from CHECK_MICRO_PROFITTEMPLATE where TEMPNAME='"+java.net.URLDecoder.decode(cptm.getTempName(),"UTF-8").trim()+"' and MatainType in ('A','M','P') ";
				List<CheckProfitTemplateMicroModel> listOld=checkUnitProfitMicroDao.queryObjectsBySqlList(sqlOld, null,CheckProfitTemplateMicroModel.class );
				if(listOld.size()>0){
					cpt=getCheckProfitTemplateMicroBeanInfo(listOld,null);
				}
			}
        }else if(type==2){
			String sqlNext="select * from CHECK_MICRO_PROFITTEMPLATE_W " +
					" where TEMPNAME='"+java.net.URLDecoder.decode(cptm.getTempName(),"UTF-8").trim()+"' " +
					" and MatainType in ('A','M','P')  and mataindate>=trunc(sysdate,'mm')";
			List<CheckProfitTemplateMicroWModel> listNext=checkUnitProfitMicroWDao.queryObjectsBySqlList(sqlNext, null,CheckProfitTemplateMicroWModel.class );
			if(listNext.size()>0){
				cpt=getCheckProfitTemplateMicroBeanInfo(null,listNext);
				return cpt;
			}

			if(DateTools.isFirstDay()){
				String sqlUse="select * from CHECK_MICRO_PROFITTEMPLATE_W " +
						" where TEMPNAME='"+java.net.URLDecoder.decode(cptm.getTempName(),"UTF-8").trim()+"' " +
						" and MatainType in ('A','M','P')  and mataindate>=trunc(sysdate-2,'mm') and mataindate<trunc(sysdate)";
				List<CheckProfitTemplateMicroWModel> list=checkUnitProfitMicroWDao.queryObjectsBySqlList(sqlUse, null,CheckProfitTemplateMicroWModel.class );
				if(list.size()>0){
					cpt=getCheckProfitTemplateMicroBeanInfo(null,list);
					return cpt;
				}else{
					String sqlOld="select * from CHECK_MICRO_PROFITTEMPLATE where TEMPNAME='"+java.net.URLDecoder.decode(cptm.getTempName(),"UTF-8").trim()+"' and MatainType in ('A','M','P') ";
					List<CheckProfitTemplateMicroModel> listOld=checkUnitProfitMicroDao.queryObjectsBySqlList(sqlOld, null,CheckProfitTemplateMicroModel.class );
					if(listOld.size()>0){
						cpt=getCheckProfitTemplateMicroBeanInfo(listOld,null);
						return cpt;
					}
				}
			}else{
				String sqlOld="select * from CHECK_MICRO_PROFITTEMPLATE where TEMPNAME='"+java.net.URLDecoder.decode(cptm.getTempName(),"UTF-8").trim()+"' and MatainType in ('A','M','P') ";
				List<CheckProfitTemplateMicroModel> listOld=checkUnitProfitMicroDao.queryObjectsBySqlList(sqlOld, null,CheckProfitTemplateMicroModel.class );
				if(listOld.size()>0){
					cpt=getCheckProfitTemplateMicroBeanInfo(listOld,null);
					return cpt;
				}
			}
        }
	    return cpt;
    }

	private static CheckProfitTemplateMicroBean getCheckProfitTemplateMicroBeanInfo(
			List<CheckProfitTemplateMicroModel> listOld,List<CheckProfitTemplateMicroWModel> listNew){
		CheckProfitTemplateMicroBean cpt=new CheckProfitTemplateMicroBean();
		if(listOld!=null && listOld.size()>0){
			for (int i = 0; i < listOld.size(); i++) {
				CheckProfitTemplateMicroModel cc = listOld.get(i);
				if (cc.getSettMethod() == "000000" || "000000".equals(cc.getSettMethod())) {
					cpt.setStartAmount(cc.getStartAmount());
					cpt.setEndAmount(cc.getEndAmount());
					cpt.setRuleThreshold(cc.getRuleThreshold());
					cpt.setCreditBankRate(cc.getCreditBankRate());
					cpt.setCashAmt(cc.getCashAmt());
					cpt.setCashRate(cc.getCashRate());
					cpt.setScanRate(cc.getScanRate());
					cpt.setScanRate1(cc.getScanRate1());
					cpt.setScanRate2(cc.getScanRate2());
					cpt.setCashAmt2(cc.getCashAmt1());
					cpt.setCashAmt3(cc.getCashAmt2());
					cpt.setProfitPercent(cc.getProfitPercent());
					cpt.setProfitPercent3(cc.getProfitPercent1());
				}
				if ((cc.getSettMethod() == "100000" || "100000".equals(cc.getSettMethod())) && cc.getMerchantType() == 2) {
					cpt.setCreditBankRate1(cc.getCreditBankRate());
					cpt.setCashAmt1(cc.getCashAmt());
					cpt.setProfitPercent1(cc.getProfitPercent());
					cpt.setStartAmount2(cc.getStartAmount());
					cpt.setEndAmount2(cc.getEndAmount());
					cpt.setRuleThreshold2(cc.getRuleThreshold());
					//20200226-ztt扫码1000上下
					cpt.setCashamtabove(cc.getCashamtabove());
					cpt.setCashamtunder(cc.getCashamtunder());
				} else if ((cc.getSettMethod() == "100000" || "100000".equals(cc.getSettMethod())) && cc.getMerchantType() == 3) {
					cpt.setCreditBankRate2(cc.getCreditBankRate());
					cpt.setProfitPercent2(cc.getProfitPercent());
				}
				cpt.setCloudQuickRate(cc.getCloudQuickRate());
				cpt.setTempName(cc.getTempName());
			}
		}else if(listNew!=null && listNew.size()>0){
			for (int i = 0; i < listNew.size(); i++) {
				CheckProfitTemplateMicroWModel cc = listNew.get(i);
				if (cc.getSettMethod() == "000000" || "000000".equals(cc.getSettMethod())) {
					cpt.setStartAmount(cc.getStartAmount());
					cpt.setEndAmount(cc.getEndAmount());
					cpt.setRuleThreshold(cc.getRuleThreshold());
					cpt.setCreditBankRate(cc.getCreditBankRate());
					cpt.setCashAmt(cc.getCashAmt());
					cpt.setCashRate(cc.getCashRate());
					cpt.setScanRate(cc.getScanRate());
					cpt.setScanRate1(cc.getScanRate1());
					cpt.setScanRate2(cc.getScanRate2());
					cpt.setCashAmt2(cc.getCashAmt1());
					cpt.setCashAmt3(cc.getCashAmt2());
					cpt.setProfitPercent(cc.getProfitPercent());
					cpt.setProfitPercent3(cc.getProfitPercent1());
				}
				if ((cc.getSettMethod() == "100000" || "100000".equals(cc.getSettMethod())) && cc.getMerchantType() == 2) {
					cpt.setCreditBankRate1(cc.getCreditBankRate());
					cpt.setCashAmt1(cc.getCashAmt());
					cpt.setProfitPercent1(cc.getProfitPercent());
					cpt.setStartAmount2(cc.getStartAmount());
					cpt.setEndAmount2(cc.getEndAmount());
					cpt.setRuleThreshold2(cc.getRuleThreshold());
					//20200226-ztt扫码1000上下
					cpt.setCashamtabove(cc.getCashamtabove());
					cpt.setCashamtunder(cc.getCashamtunder());
				} else if ((cc.getSettMethod() == "100000" || "100000".equals(cc.getSettMethod())) && cc.getMerchantType() == 3) {
					cpt.setCreditBankRate2(cc.getCreditBankRate());
					cpt.setProfitPercent2(cc.getProfitPercent());
				}
				cpt.setCloudQuickRate(cc.getCloudQuickRate());
				cpt.setTempName(cc.getTempName());
			}
		}
		return cpt;
	}

	private void updateMdNextProfit(List<CheckProfitTemplateMicroWModel> list,CheckProfitTemplateMicroBean cpt, UserBean user){
		for(int i=0;i<list.size();i++){
			CheckProfitTemplateMicroWModel cc =list.get(i);
			if("000000".equals(cc.getSettMethod())){
				if(cpt.getStartAmount()!=null) {
					cc.setStartAmount(cpt.getStartAmount());
				}else{
					cc.setStartAmount(null);
				}
				if(cpt.getEndAmount()!=null) {
					cc.setEndAmount(cpt.getEndAmount());
				}else{
					cc.setEndAmount(null);
				}
				if(cpt.getRuleThreshold()!=null) {
					cc.setRuleThreshold(cpt.getRuleThreshold() / 100);
				}else{
					cc.setRuleThreshold(null);
				}
				if(cpt.getProfitPercent()!=null) {
					cc.setProfitPercent(cpt.getProfitPercent() / 100);
				}else{
					cc.setProfitPercent(null);
				}
				if(cpt.getProfitPercent3()!=null) {
					cc.setProfitPercent1(cpt.getProfitPercent3() / 100);
				}else{
					cc.setProfitPercent1(null);
				}
				if(cpt.getCashRate()!=null) {
					cc.setCashRate(cpt.getCashRate() / 100);
				}else{
					cc.setCashRate(null);
				}
				if(cpt.getCashAmt()!=null) {
					cc.setCashAmt(cpt.getCashAmt());
				}else{
					cc.setCashAmt(null);
				}
				cc.setCashAmt1(cpt.getCashAmt2());
				if(cpt.getCashAmt3()!=null) {
					cc.setCashAmt2(cpt.getCashAmt3());
				}else{
					cc.setCashAmt2(null);
				}
				if(cpt.getCreditBankRate()!=null) {
					cc.setCreditBankRate(cpt.getCreditBankRate() / 100);
				}else{
					cc.setCreditBankRate(null);
				}
				cc.setScanRate(cpt.getScanRate()/100);
				cc.setScanRate1(cpt.getScanRate1()/100);
				cc.setScanRate2(cpt.getScanRate2()/100);
				cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
//				cc.setMatainType("M");
				cc.setMatainUserId(user.getUserID());
				cc.setMatainDate(new Date());
				cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
				checkUnitProfitMicroWDao.updateObject(cc);
			}
			if("100000".equals(cc.getSettMethod())&&cc.getMerchantType()==2){
				if(cpt.getStartAmount2()!=null) {
					cc.setStartAmount(cpt.getStartAmount2() / 100);
				}else{
					cc.setStartAmount(null);
				}
				if(cpt.getEndAmount2()!=null) {
					cc.setEndAmount(cpt.getEndAmount2() / 100);
				}else{
					cc.setEndAmount(null);
				}
				cc.setRuleThreshold(cpt.getRuleThreshold2());
				cc.setProfitPercent(cpt.getProfitPercent1()/100);
				if(cpt.getProfitPercent3()!=null) {
					cc.setProfitPercent1(cpt.getProfitPercent3() / 100);
				}else{
					cc.setProfitPercent1(null);
				}
				if(cpt.getCashRate()!=null) {
					cc.setCashRate(cpt.getCashRate() / 100);
				}else{
					cc.setCashRate(null);
				}
				cc.setCashAmt(cpt.getCashAmt1());
				cc.setCashAmt1(cpt.getCashAmt2());
				if(cpt.getCashAmt3()!=null) {
					cc.setCashAmt2(cpt.getCashAmt3());
				}else{
					cc.setCashAmt2(null);
				}
				cc.setCreditBankRate(cpt.getCreditBankRate1()/100);
				cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
				cc.setScanRate(cpt.getScanRate()/100);
				cc.setScanRate1(cpt.getScanRate1()/100);
				cc.setScanRate2(cpt.getScanRate2()/100);
				
				//20200226-ztt扫码转账费添加
				if(cpt.getCashamtunder()!=null) {
					cc.setCashamtunder(cpt.getCashamtunder());
				}else {
					cc.setCashamtunder(null);
				}
				if(cpt.getCashamtabove()!=null) {
					cc.setCashamtabove(cpt.getCashamtabove());
				}else {
					cc.setCashamtabove(null);
				}
				
//			cc.setTempName(cpt.getTempName());
//				cc.setMatainType("M");
				cc.setMatainUserId(user.getUserID());
				cc.setMatainDate(new Date());
				cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
				checkUnitProfitMicroWDao.updateObject(cc);
			}
			if("100000".equals(cc.getSettMethod())&&cc.getMerchantType()==3){
				if(cpt.getStartAmount2()!=null) {
					cc.setStartAmount(cpt.getStartAmount2() / 100);
				}else{
					cc.setStartAmount(null);
				}
				if(cpt.getEndAmount2()!=null) {
					cc.setEndAmount(cpt.getEndAmount2() / 100);
				}else{
					cc.setEndAmount(null);
				}
				cc.setRuleThreshold(cpt.getRuleThreshold2());
				cc.setProfitPercent(cpt.getProfitPercent2()/100);
				if(cpt.getProfitPercent3()!=null) {
					cc.setProfitPercent1(cpt.getProfitPercent3() / 100);
				}else{
					cc.setProfitPercent1(null);
				}
				if(cpt.getCashRate()!=null) {
					cc.setCashRate(cpt.getCashRate() / 100);
				}else{
					cc.setCashRate(null);
				}
				cc.setCashAmt(cpt.getCashAmt1());
				cc.setCashAmt1(cpt.getCashAmt2());
				if(cpt.getCashAmt3()!=null) {
					cc.setCashAmt2(cpt.getCashAmt3());
				}else{
					cc.setCashAmt2(null);
				}
				cc.setCreditBankRate(cpt.getCreditBankRate2()/100);
				cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
				cc.setScanRate(cpt.getScanRate()/100);
				cc.setScanRate1(cpt.getScanRate1()/100);
				cc.setScanRate2(cpt.getScanRate2()/100);
//			cc.setTempName(cpt.getTempName());
//				cc.setMatainType("M");
				cc.setMatainUserId(user.getUserID());
				cc.setMatainDate(new Date());
				cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
				checkUnitProfitMicroWDao.updateObject(cc);
			}
		}
	}

	private void saveMdNextProfit(List<CheckProfitTemplateMicroModel> list,CheckProfitTemplateMicroBean cpt, UserBean user){
		for(int i=0;i<list.size();i++){
			CheckProfitTemplateMicroModel cc = new CheckProfitTemplateMicroModel();
			CheckProfitTemplateMicroModel bean =list.get(i);
			BeanUtils.copyProperties(bean,cc);
			if("000000".equals(cc.getSettMethod())){
				if(cpt.getStartAmount()!=null) {
					cc.setStartAmount(cpt.getStartAmount());
				}else{
					cc.setStartAmount(null);
				}
				if(cpt.getEndAmount()!=null) {
					cc.setEndAmount(cpt.getEndAmount());
				}else{
					cc.setEndAmount(null);
				}
				if(cpt.getRuleThreshold()!=null) {
					cc.setRuleThreshold(cpt.getRuleThreshold() / 100);
				}else{
					cc.setRuleThreshold(null);
				}
				if(cpt.getProfitPercent()!=null) {
					cc.setProfitPercent(cpt.getProfitPercent() / 100);
				}else{
					cc.setProfitPercent(null);
				}
				if(cpt.getProfitPercent3()!=null) {
					cc.setProfitPercent1(cpt.getProfitPercent3() / 100);
				}else{
					cc.setProfitPercent1(null);
				}
				if(cpt.getCashRate()!=null) {
					cc.setCashRate(cpt.getCashRate() / 100);
				}else{
					cc.setCashRate(null);
				}
				if(cpt.getCashAmt()!=null) {
					cc.setCashAmt(cpt.getCashAmt());
				}else{
					cc.setCashAmt(null);
				}
				cc.setCashAmt1(cpt.getCashAmt2());
				if(cpt.getCashAmt3()!=null) {
					cc.setCashAmt2(cpt.getCashAmt3());
				}else{
					cc.setCashAmt2(null);
				}
				if(cpt.getCreditBankRate()!=null) {
					cc.setCreditBankRate(cpt.getCreditBankRate() / 100);
				}else{
					cc.setCreditBankRate(null);
				}
				cc.setScanRate(cpt.getScanRate()/100);
				cc.setScanRate1(cpt.getScanRate1()/100);
				cc.setScanRate2(cpt.getScanRate2()/100);
				cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
//			cc.setTempName(cpt.getTempName());
//				cc.setMatainType("M");
				cc.setMatainUserId(user.getUserID());
				cc.setMatainDate(new Date());
				cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
				CheckProfitTemplateMicroWModel nextModel = new CheckProfitTemplateMicroWModel();
				BeanUtils.copyProperties(cc,nextModel);
				checkUnitProfitMicroWDao.saveObject(nextModel);
			}
			if("100000".equals(cc.getSettMethod())&&cc.getMerchantType()==2){
				if(cpt.getStartAmount2()!=null) {
					cc.setStartAmount(cpt.getStartAmount2() / 100);
				}else{
					cc.setStartAmount(null);
				}
				if(cpt.getEndAmount2()!=null) {
					cc.setEndAmount(cpt.getEndAmount2() / 100);
				}else{
					cc.setEndAmount(null);
				}
				cc.setRuleThreshold(cpt.getRuleThreshold2());
				cc.setProfitPercent(cpt.getProfitPercent1()/100);
				if(cpt.getProfitPercent3()!=null) {
					cc.setProfitPercent1(cpt.getProfitPercent3() / 100);
				}else{
					cc.setProfitPercent1(null);
				}
				if(cpt.getCashRate()!=null) {
					cc.setCashRate(cpt.getCashRate() / 100);
				}else{
					cc.setCashRate(null);
				}
				cc.setCashAmt(cpt.getCashAmt1());
				cc.setCashAmt1(cpt.getCashAmt2());
				if(cpt.getCashAmt3()!=null) {
					cc.setCashAmt2(cpt.getCashAmt3());
				}else{
					cc.setCashAmt2(null);
				}
				cc.setCreditBankRate(cpt.getCreditBankRate1()/100);
				cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
				cc.setScanRate(cpt.getScanRate()/100);
				cc.setScanRate1(cpt.getScanRate1()/100);
				cc.setScanRate2(cpt.getScanRate2()/100);
				
				//20200226-ztt扫码1000以上、以下转账费添加
				if(cpt.getCashamtabove()!=null) {
					cc.setCashamtabove(cpt.getCashamtabove());
				}else{
					cc.setCashamtabove(null);
				}
				if(cpt.getCashamtunder()!=null) {
					cc.setCashamtunder(cpt.getCashamtunder());
				}else{
					cc.setCashamtunder(null);
				}
//			cc.setTempName(cpt.getTempName());
//				cc.setMatainType("M");
				cc.setMatainUserId(user.getUserID());
				cc.setMatainDate(new Date());
				cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
				CheckProfitTemplateMicroWModel nextModel = new CheckProfitTemplateMicroWModel();
				BeanUtils.copyProperties(cc,nextModel);
				checkUnitProfitMicroWDao.saveObject(nextModel);
			}
			if("100000".equals(cc.getSettMethod())&&cc.getMerchantType()==3){
				if(cpt.getStartAmount2()!=null) {
					cc.setStartAmount(cpt.getStartAmount2() / 100);
				}else{
					cc.setStartAmount(null);
				}
				if(cpt.getEndAmount2()!=null) {
					cc.setEndAmount(cpt.getEndAmount2() / 100);
				}else{
					cc.setEndAmount(null);
				}
				cc.setRuleThreshold(cpt.getRuleThreshold2());
				cc.setProfitPercent(cpt.getProfitPercent2()/100);
				if(cpt.getProfitPercent3()!=null) {
					cc.setProfitPercent1(cpt.getProfitPercent3() / 100);
				}else{
					cc.setProfitPercent1(null);
				}
				if(cpt.getCashRate()!=null) {
					cc.setCashRate(cpt.getCashRate() / 100);
				}else{
					cc.setCashRate(null);
				}
				cc.setCashAmt(cpt.getCashAmt1());
				cc.setCashAmt1(cpt.getCashAmt2());
				if(cpt.getCashAmt3()!=null) {
					cc.setCashAmt2(cpt.getCashAmt3());
				}else{
					cc.setCashAmt2(null);
				}
				cc.setCreditBankRate(cpt.getCreditBankRate2()/100);
				cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
				cc.setScanRate(cpt.getScanRate()/100);
				cc.setScanRate1(cpt.getScanRate1()/100);
				cc.setScanRate2(cpt.getScanRate2()/100);
//			cc.setTempName(cpt.getTempName());
//				cc.setMatainType("M");
				cc.setMatainUserId(user.getUserID());
				cc.setMatainDate(new Date());
				cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
				CheckProfitTemplateMicroWModel nextModel = new CheckProfitTemplateMicroWModel();
				BeanUtils.copyProperties(cc,nextModel);
				checkUnitProfitMicroWDao.saveObject(nextModel);
			}
		}
	}
	/**
	 * 修改模版
	 * @throws Exception
	 */
	public void updateProfitmicro(CheckProfitTemplateMicroBean cpt, UserBean user) throws Exception {
		String sqlNext="select * from CHECK_MICRO_PROFITTEMPLATE_W where TEMPNAME='"+java.net.URLDecoder.decode(cpt.getTempname(),"UTF-8").trim()+"' and MatainType in ('A','M') and mataindate>=trunc(sysdate,'mm')";
		String sql="select * from CHECK_MICRO_PROFITTEMPLATE where TEMPNAME='"+java.net.URLDecoder.decode(cpt.getTempname(),"UTF-8").trim()+"' and MatainType in ('A','M') ";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		//为总公司
		if("110000".equals(user.getUnNo())){
		 }
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and unno ='"+user.getUnNo()+"'";
			sqlNext+=" and unno ='"+user.getUnNo()+"'";
		}
		List<CheckProfitTemplateMicroWModel> listNext=checkUnitProfitMicroWDao.queryObjectsBySqlList(sqlNext, null,CheckProfitTemplateMicroWModel.class );
		if(listNext.size()>0){
			updateMdNextProfit(listNext,cpt,user);
		}else{
			List<CheckProfitTemplateMicroModel> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql, null,CheckProfitTemplateMicroModel.class );
			saveMdNextProfit(list,cpt,user);
		}

		//CheckProfitTemplateMicroBean cpt= new CheckProfitTemplateMicroBean();
		/*for(int i=0;i<list.size();i++){
		CheckProfitTemplateMicroModel cc =list.get(i);
		if("000000".equals(cc.getSettMethod())){
			if(cpt.getStartAmount()!=null) {
				cc.setStartAmount(cpt.getStartAmount());
			}else{
				cc.setStartAmount(null);
			}
			if(cpt.getEndAmount()!=null) {
				cc.setEndAmount(cpt.getEndAmount());
			}else{
				cc.setEndAmount(null);
			}
			if(cpt.getRuleThreshold()!=null) {
				cc.setRuleThreshold(cpt.getRuleThreshold() / 100);
			}else{
				cc.setRuleThreshold(null);
			}
			if(cpt.getProfitPercent()!=null) {
				cc.setProfitPercent(cpt.getProfitPercent() / 100);
			}else{
				cc.setProfitPercent(null);
			}
			if(cpt.getProfitPercent3()!=null) {
				cc.setProfitPercent1(cpt.getProfitPercent3() / 100);
			}else{
				cc.setProfitPercent1(null);
			}
			if(cpt.getCashRate()!=null) {
				cc.setCashRate(cpt.getCashRate() / 100);
			}else{
				cc.setCashRate(null);
			}
			if(cpt.getCashAmt()!=null) {
				cc.setCashAmt(cpt.getCashAmt());
			}else{
				cc.setCashAmt(null);
			}
			cc.setCashAmt1(cpt.getCashAmt2());
			if(cpt.getCashAmt3()!=null) {
				cc.setCashAmt2(cpt.getCashAmt3());
			}else{
				cc.setCashAmt2(null);
			}
			if(cpt.getCreditBankRate()!=null) {
				cc.setCreditBankRate(cpt.getCreditBankRate() / 100);
			}else{
				cc.setCreditBankRate(null);
			}
			cc.setScanRate(cpt.getScanRate()/100);
			cc.setScanRate1(cpt.getScanRate1()/100);
			cc.setScanRate2(cpt.getScanRate2()/100);
			cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
//			cc.setTempName(cpt.getTempName());
			cc.setMatainType("M");
			cc.setMatainUserId(user.getUserID());
			cc.setMatainDate(new Date());
			cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
			checkUnitProfitMicroDao.updateObject(cc);
		}
		if("100000".equals(cc.getSettMethod())&&cc.getMerchantType()==2){
			if(cpt.getStartAmount2()!=null) {
				cc.setStartAmount(cpt.getStartAmount2() / 100);
			}else{
				cc.setStartAmount(null);
			}
			if(cpt.getEndAmount2()!=null) {
				cc.setEndAmount(cpt.getEndAmount2() / 100);
			}else{
				cc.setEndAmount(null);
			}
			cc.setRuleThreshold(cpt.getRuleThreshold2());
			cc.setProfitPercent(cpt.getProfitPercent1()/100);
			if(cpt.getProfitPercent3()!=null) {
				cc.setProfitPercent1(cpt.getProfitPercent3() / 100);
			}else{
				cc.setProfitPercent1(null);
			}
			if(cpt.getCashRate()!=null) {
				cc.setCashRate(cpt.getCashRate() / 100);
			}else{
				cc.setCashRate(null);
			}
			cc.setCashAmt(cpt.getCashAmt1());
			cc.setCashAmt1(cpt.getCashAmt2());
			if(cpt.getCashAmt3()!=null) {
				cc.setCashAmt2(cpt.getCashAmt3());
			}else{
				cc.setCashAmt2(null);
			}
			cc.setCreditBankRate(cpt.getCreditBankRate1()/100);
			cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
			cc.setScanRate(cpt.getScanRate()/100);
			cc.setScanRate1(cpt.getScanRate1()/100);
			cc.setScanRate2(cpt.getScanRate2()/100);
//			cc.setTempName(cpt.getTempName());
			cc.setMatainType("M");
			cc.setMatainUserId(user.getUserID());
			cc.setMatainDate(new Date());
			cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
			checkUnitProfitMicroDao.updateObject(cc);
		}
		if("100000".equals(cc.getSettMethod())&&cc.getMerchantType()==3){
			if(cpt.getStartAmount2()!=null) {
				cc.setStartAmount(cpt.getStartAmount2() / 100);
			}else{
				cc.setStartAmount(null);
			}
			if(cpt.getEndAmount2()!=null) {
				cc.setEndAmount(cpt.getEndAmount2() / 100);
			}else{
				cc.setEndAmount(null);
			}
			cc.setRuleThreshold(cpt.getRuleThreshold2());
			cc.setProfitPercent(cpt.getProfitPercent2()/100);
			if(cpt.getProfitPercent3()!=null) {
				cc.setProfitPercent1(cpt.getProfitPercent3() / 100);
			}else{
				cc.setProfitPercent1(null);
			}
			if(cpt.getCashRate()!=null) {
				cc.setCashRate(cpt.getCashRate() / 100);
			}else{
				cc.setCashRate(null);
			}
			cc.setCashAmt(cpt.getCashAmt1());
			cc.setCashAmt1(cpt.getCashAmt2());
			if(cpt.getCashAmt3()!=null) {
				cc.setCashAmt2(cpt.getCashAmt3());
			}else{
				cc.setCashAmt2(null);
			}
			cc.setCreditBankRate(cpt.getCreditBankRate2()/100);
			cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
			cc.setScanRate(cpt.getScanRate()/100);
			cc.setScanRate1(cpt.getScanRate1()/100);
			cc.setScanRate2(cpt.getScanRate2()/100);
//			cc.setTempName(cpt.getTempName());
			cc.setMatainType("M");
			cc.setMatainUserId(user.getUserID());
			cc.setMatainDate(new Date());
			cc.setCloudQuickRate(cpt.getCloudQuickRate()/100);
			checkUnitProfitMicroDao.updateObject(cc);
		}
//		cc.setTempName(cpt.getTempName());
//		cc.setMatainType("M");
//		checkUnitProfitMicroDao.updateObject(cc);
	  }*/
		/*if (list.size()==2){
			CheckProfitTemplateMicroModel MicroModel2 = new CheckProfitTemplateMicroModel();
			MicroModel2.setUnno(user.getUnNo());
			MicroModel2.setMerchantType(3);
			MicroModel2.setProfitType(1);
			MicroModel2.setProfitRule(1);
			if(cpt.getStartAmount()!=null) {
				MicroModel2.setStartAmount(cpt.getStartAmount());
			}
			if(cpt.getEndAmount()!=null) {
				MicroModel2.setEndAmount(cpt.getEndAmount());
			}
			if(cpt.getRuleThreshold()!=null) {
				MicroModel2.setRuleThreshold(cpt.getRuleThreshold() / 100);
			}
			MicroModel2.setProfitPercent(cpt.getProfitPercent2()/100);
			if(cpt.getCashRate()!=null) {
				MicroModel2.setCashRate(cpt.getCashRate() / 100);
			}
			if(cpt.getCashAmt()!=null) {
				MicroModel2.setCashAmt(cpt.getCashAmt());
			}
			MicroModel2.setCreditBankRate(cpt.getCreditBankRate2()/100);
			MicroModel2.setScanRate(cpt.getScanRate()/100);
			MicroModel2.setMatainDate(new Date());
			MicroModel2.setTempName(cpt.getTempName());
			MicroModel2.setMatainUserId(user.getUserID());
			MicroModel2.setMatainType("A");
			MicroModel2.setSettMethod("100000");
			MicroModel2.setCloudQuickRate(cpt.getCloudQuickRate());
			checkUnitProfitMicroDao.saveObject(MicroModel2);
		}*/
	}
	/**
	 * 修改模版
	 * @throws Exception 
	 */
	public void updateSubTemplate(CheckProfitTemplateMicroBean cpt, UserBean user) throws Exception {
//		String sql="from CheckProfitTemplateMicroModel where aptId=? and MatainType in ('A','M') and merchanttype=4 ";
//		CheckProfitTemplateMicroModel model = checkUnitProfitMicroDao.queryObjectByHql(sql, new Object[]{cpt.getAptId()});
//		model.setSubRate(cpt.getSubRate()/100);
//		model.setMatainDate(new Date());
//		model.setMatainType("M");
//		checkUnitProfitMicroDao.updateObject(model);
		// @author:lxg-20190906 代还修改存到下月生效中,当月无该aptId数据,进行插入,有aptId,进行更新
		String sql="from CheckProfitTemplateMicroWModel where aptId=? and MatainType in ('A','M') and merchanttype=4 and matainDate>=trunc(sysdate,'mm')";
		CheckProfitTemplateMicroWModel model = checkUnitProfitMicroWDao.queryObjectByHql(sql, new Object[]{cpt.getAptId()});
		if(model!=null) {
			model.setSubRate(cpt.getSubRate() / 100);
			model.setMatainDate(new Date());
			model.setMatainType("M");
			checkUnitProfitMicroWDao.updateObject(model);
		}else{
			String sqlBefore="from CheckProfitTemplateMicroModel where aptId=? and MatainType in ('A','M') and merchanttype=4 ";
			CheckProfitTemplateMicroModel modelBefore = checkUnitProfitMicroDao.queryObjectByHql(sqlBefore, new Object[]{cpt.getAptId()});
			CheckProfitTemplateMicroWModel nextModel = new CheckProfitTemplateMicroWModel();
			if(modelBefore!=null) {
				BeanUtils.copyProperties(modelBefore, nextModel);
				nextModel.setSubRate(cpt.getSubRate() / 100);
				nextModel.setMatainDate(new Date());
				nextModel.setMatainType("M");
				checkUnitProfitMicroWDao.saveObject(nextModel);
			}
		}
	}
	/**
	 * 修改模版
	 * @throws Exception 
	 */
	public void updateSytTemplate(CheckProfitTemplateMicroBean cpt, UserBean user) throws Exception {
		String tempName = cpt.getTempName();
		
		String str = cpt.getTempname();
	    JSONArray json = JSONArray.parseArray(str);
		
		for (int i = 0; i < json.size(); i++) {
			//扫码1000以上0.38费率
        	String creditBankRate = JSONObject.parseObject(json.getString(i)).getString("creditBankRate");
        	//扫码1000以上0.38转账费
        	String cashRate = JSONObject.parseObject(json.getString(i)).getString("cashRate");
        	//扫码1000以上0.45费率
        	String scanRate = JSONObject.parseObject(json.getString(i)).getString("scanRate");
        	//扫码1000以上0.45转账费
        	String profitPercent1 = JSONObject.parseObject(json.getString(i)).getString("profitPercent1");
        	//扫码1000以下0.38费率
        	String subRate = JSONObject.parseObject(json.getString(i)).getString("subRate");
        	//扫码1000以下0.38转账费
        	String cashAmt = JSONObject.parseObject(json.getString(i)).getString("cashAmt");
        	//扫码1000以下0.45费率
        	String scanRate1 = JSONObject.parseObject(json.getString(i)).getString("scanRate1");
        	//扫码1000以下0.45转账费
        	String cashAmt1 = JSONObject.parseObject(json.getString(i)).getString("cashAmt1");
        	//二维码费率
        	String scanRate2 = JSONObject.parseObject(json.getString(i)).getString("scanRate2");
        	//二维码转账费
        	String cashAmt2 = JSONObject.parseObject(json.getString(i)).getString("cashAmt2");
        	//aptId
        	String aptId = JSONObject.parseObject(json.getString(i)).getString("aptId");
        	//活动类型
        	String profitRule = JSONObject.parseObject(json.getString(i)).getString("profitRule");
        	
        	//判断是否是花呗活动
        	String subtype = querySubTypeByProfitRule(Integer.parseInt(profitRule));
        	
        	String huabeiRate = null;
    		String huabeiFee = null;
        	if(subtype != null) {
        		//花呗费率
        		huabeiRate = JSONObject.parseObject(json.getString(i)).getString("huabeiRate");
        		//花呗转账费
        		huabeiFee = JSONObject.parseObject(json.getString(i)).getString("huabeiFee");
        	}
            //获得查询出数据本身的matainType(用于保证插入的数据类型与之前保持一致)
        	String matainType = JSONObject.parseObject(json.getString(i)).getString("matainType");
        	String sql="select * from CHECK_MICRO_PROFITTEMPLATE_W where TEMPNAME='"+tempName.trim()+"' and MatainType in ('A','M','P') and merchanttype = 5 and matainDate>=trunc(sysdate,'mm') and profitRule = '"+JSONObject.parseObject(json.getString(i)).getString("profitRule")+"'";
            UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
            //为总公司
            if("110000".equals(user.getUnNo())){
            }
            //为总公司并且是部门---查询全部
            else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
                UnitModel parent = unitModel.getParent();
                if("110000".equals(parent.getUnNo())){
                }
            }else{
                sql+=" and unno ='"+user.getUnNo()+"'";
            }
            List<CheckProfitTemplateMicroWModel> list=checkUnitProfitMicroWDao.queryObjectsBySqlList(sql, null,CheckProfitTemplateMicroWModel.class );
            if(list.size()>0){
                CheckProfitTemplateMicroWModel model = list.get(0);
               
                model.setCreditBankRate(Double.parseDouble(new BigDecimal(creditBankRate+"").divide(new BigDecimal("100"))+""));
                model.setCashRate(Double.parseDouble(cashRate));
                model.setScanRate(Double.parseDouble(new BigDecimal(scanRate+"").divide(new BigDecimal("100"))+""));
                model.setCashAmt1(Double.parseDouble(cashAmt1));
                model.setScanRate1(Double.parseDouble(new BigDecimal(scanRate1+"").divide(new BigDecimal("100"))+""));
                model.setProfitPercent1(Double.parseDouble(profitPercent1));
                model.setScanRate2(Double.parseDouble(new BigDecimal(scanRate2+"").divide(new BigDecimal("100"))+""));
                model.setCashAmt2(Double.parseDouble(cashAmt2));
                model.setSubRate(Double.parseDouble(new BigDecimal(subRate+"").divide(new BigDecimal("100"))+""));
                model.setCashAmt(Double.parseDouble(cashAmt));
                if(subtype != null) {
                	model.setHuabeiRate(Double.parseDouble(new BigDecimal(huabeiRate+"").divide(new BigDecimal("100"))+""));
                    model.setHuabeiFee(Double.parseDouble(huabeiFee));
                }
                model.setMatainUserId(user.getUserID());
                model.setMatainDate(new Date());
                checkUnitProfitMicroWDao.updateObject(model);
            }else{
            	CheckProfitTemplateMicroWModel MicroModel = new CheckProfitTemplateMicroWModel();
                MicroModel.setUnno(user.getUnNo());
                MicroModel.setMerchantType(5);//表示5--syt模板
                MicroModel.setProfitRule(Integer.parseInt(profitRule));//表示20--活动20
               
                MicroModel.setCreditBankRate(Double.parseDouble(new BigDecimal(creditBankRate+"").divide(new BigDecimal("100"))+""));
                MicroModel.setCashRate(Double.parseDouble(cashRate));
                MicroModel.setScanRate(Double.parseDouble(new BigDecimal(scanRate+"").divide(new BigDecimal("100"))+""));
                MicroModel.setCashAmt1(Double.parseDouble(cashAmt1));
                MicroModel.setScanRate1(Double.parseDouble(new BigDecimal(scanRate1+"").divide(new BigDecimal("100"))+""));
                MicroModel.setProfitPercent1(Double.parseDouble(profitPercent1));
                MicroModel.setScanRate2(Double.parseDouble(new BigDecimal(scanRate2+"").divide(new BigDecimal("100"))+""));
                MicroModel.setCashAmt2(Double.parseDouble(cashAmt2));
                MicroModel.setSubRate(Double.parseDouble(new BigDecimal(subRate+"").divide(new BigDecimal("100"))+""));
                MicroModel.setCashAmt(Double.parseDouble(cashAmt));
                if(subtype != null) {
                	MicroModel.setHuabeiRate(Double.parseDouble(new BigDecimal(huabeiRate+"").divide(new BigDecimal("100"))+""));
                	MicroModel.setHuabeiFee(Double.parseDouble(huabeiFee));
                }
               
                MicroModel.setAptId(Integer.parseInt(aptId));
                MicroModel.setTempName(tempName);
                MicroModel.setMatainDate(new Date());
               // MicroModel.setMatainType("A");
                MicroModel.setMatainType(matainType);
                checkUnitProfitMicroWDao.saveObject(MicroModel);
            }
		}
	}
	/**
	 * 删除模版
	 */
	public void DeleteProfitmicro(String TEMPNAME,UserBean user) {
		String sql="update CHECK_MICRO_PROFITTEMPLATE set MatainType='D' where aptid in (select aptid from CHECK_MICRO_PROFITTEMPLATE where TEMPNAME='"+TEMPNAME+"' and unno ='"+user.getUnNo()+"' and MatainType in ('A','M') )";
		checkUnitProfitMicroDao.executeUpdate(sql);
		String sql1="update CHECK_UNIT_PROFITEMPLATE set STATUS=0 where APTID in (select aptid from CHECK_MICRO_PROFITTEMPLATE where TEMPNAME='"+TEMPNAME+"' and unno ='"+user.getUnNo()+"' and MatainType in ('D') )";
		checkUnitProfitMicroDao.executeUpdate(sql1);
	}
	/**
	 * 查询下级代理
	 */
	@SuppressWarnings("unchecked")
	public DataGridBean searchunno(String nameCode,UserBean user) throws Exception {
		StringBuffer sb = new StringBuffer("select u.UNNO,u.UN_NAME from SYS_UNIT u where UPPER_UNIT='"+user.getUnNo()+"' ");
		if(nameCode !=null){
			sb.append("  AND u.UN_NAME LIKE '%"+nameCode.replaceAll("'", "")+"%' ");
		}
		sb.append("  ORDER BY u.unno ASC"); 
		List<Map<String,String>> proList = checkUnitProfitMicroDao.executeSql(sb.toString());
		
		List<Object>  list = new ArrayList<Object>();
		for (int i = 0; i < proList.size(); i++) {
			Map map = proList.get(i);
			list.add(map);
		}
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(proList.size());
		dgd.setRows(list);
		
		return dgd;
	}

	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	public IUnitDao getUnitDao() {
		return unitDao;
	}

	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}

	private String getUnitProfitMicroSumDataSql(CheckProfitTemplateMicroBean cptm){
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, cptm.getUnno());
//		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptm.getUnno());
		String sql = "select '"+unitModel.getUnitName()+"' as unitName, a.*, rownum rm from (" +
				" select nvl(round(sum(profit), 2), 0) profit,nvl(sum(mda), 0) mda,nvl(sum(txnamount), 0) txnamount,sum(txncount) txncount,settmethod,t " +
				" from (select decode(a.mertype,3,'000000','100000') as settmethod, " +
				" (select nvl(case " +
				"  when a.mertype=4 then (mda - txnamount * nvl(y.cloudquickrate, 0.003)) * y.profitpercent " +
//				"  when a.mertype=1 then (mda - y.startamount) * y.profitpercent " +
//				"  when a.mertype=3 then (mda - txnamount * y.rulethreshold) * y.profitpercent " +
//				"  when a.mertype=3 and txnamount > y.endamount then (mda - y.startamount) * y.profitpercent " +
//				"  when a.mertype=3 then (mda - txnamount * y.rulethreshold) * y.profitpercent " +
//				"  when a.mertype in (1,2) then (mda - txnamount * y.creditbankrate) * y.profitpercent " +
				"  when a.mertype = 3 and a.minfo1=1 then (mda - y.startamount) * y.profitpercent "+
				"  when a.mertype = 3 and a.minfo1=2 then (mda - txnamount * y.rulethreshold) * y.profitpercent "+
				"  else (mda - txnamount * y.creditbankrate) * y.profitpercent " +
				"  end, " +
				"  0) " +
				"  from check_micro_profittemplate y,check_unit_profitemplate   uy " +
				" where uy.unno = '"+unitModel.getUnNo()+"' and uy.aptid = y.aptid and uy.status != 0 " +
				"  and y.merchanttype = decode(a.mertype, 3,1,1,2,3) " +
				" and y.settmethod = decode(a.mertype,3,'000000','100000') " +
				" and y.mataintype != 'D') profit, " +
				" a.mda mda,a.unno,a.txnamount txnamount,a.txncount, " +
				" decode(a.mertype,3,1,4,4,1,2,3) as t " +
				" from  pg_unno_txn a " +
				" where  a.mertype in (1,2,3,4) and a.isnew=0 and a.unno = '"+unitModel.getUnNo()+"' ";
//				"  and a.txnday >= '20190501' and a.txnday <= '20190505') " +
//				"  group by settmethod, t) a";
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.txnday>='"+cptm.getTxnDay().replace("-", "")+"' and a.txnday<='"+cptm.getTxnDay1().replace("-", "")+"'" ;
		}else{
			sql+=" and a.txnday=to_char(sysdate-1,'yyyyMMdd')";
		}
		sql+=" ) group by settmethod, t) a ";
		return sql;
	}
	@Override
	public DataGridBean queryUnitProfitMicroSumData(CheckProfitTemplateMicroBean cptm) {
		// @author:lxg-20190520 手刷刷卡分润汇总-修改为新表
		String sql = getUnitProfitMicroSumDataSql(cptm);
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlListMap2(sql, null);
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(list.size());
		return dgb;
	}
	private String getUnitProfitMicroSumDataScanSql(CheckProfitTemplateMicroBean cptm){
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, cptm.getUnno());
//		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptm.getUnno());
		String sql = "select '"+unitModel.getUnitName()+"' as unitName, a.*, rownum rm from (" +
				"	select nvl(round(sum(profit), 2), 0) profit,nvl(sum(mda), 0) mda,nvl(sum(txnamount), 0) txnamount,  " +
				"     sum(txncount) txncount,settmethod  " +
				"     from (select case  " +
				"       when a.ismpos=1 and a.mertype = 1 then '000001'  " +
				"       when a.ismpos=1 and a.mertype = 2 then '000002'  " +
				"       when a.ismpos=1 and a.mertype = 3 then '000003'  " +

				//20200226-ztt,微信/支付宝交易在20200201之后显示为扫码1000上下
				"       when a.ismpos=2 and a.mertype = 1 and a.txnday < '20200101' then '100001'  " +
				"       when a.ismpos=2 and a.mertype = 2 and a.txnday < '20200101' then '100002'  " +
				"       when a.ismpos=2 and a.mertype = 1 and a.txnday >= '20200101' then '100004'  " +
				"       when a.ismpos=2 and a.mertype = 2 and a.txnday >= '20200101' then '100005'  " +
				
				"       when a.ismpos=2 and a.mertype = 3 then '100003'  " +
				"      end as settmethod,  " +
				"    (select nvl(case  " +
				"       when a.mertype = '1' then mda - txnamount * y.scanrate  " +
				"       when a.mertype = '2' then mda - txnamount * y.scanrate1  " +
				"       when a.mertype = '3' then mda - txnamount * y.scanrate2  " +
				"      end,  " +
				"    0)  " +
				"  from check_micro_profittemplate y,check_unit_profitemplate uy " +
				"   where uy.unno = '"+unitModel.getUnNo()+"' and uy.aptid = y.aptid  " +
				"    and uy.status != 0 and y.merchanttype = 2  and y.settmethod = '100000'  " +
				"    and y.mataintype != 'D') profit,a.mda mda,a.unno,a.txnamount, a.txncount  " +
				"    from pg_unno_wechat a  " +
				"  where a.ismpos in(1,2) and a.mertype in (1,2,3) and a.unno = '"+unitModel.getUnNo()+"' ";
//				"   and a.txnday >= '20190501'  " +
//				"   and a.txnday <= '20190519' ) " +
//				"  group by settmethod) a";
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.txnday>='"+cptm.getTxnDay().replace("-","")+"' and a.txnday<='"+cptm.getTxnDay1().replace("-","")+"'" ;
		}else{
			sql+=" and a.txnday=to_char(sysdate-1,'yyyyMMdd')" ;
		}
		sql+=") group by settmethod) a";
		return sql;
	}
	@Override
	public DataGridBean queryUnitProfitMicroSumDataScan(CheckProfitTemplateMicroBean cptm) {
		// @author:lxg-20190521 MPOS-手刷扫码分润汇总
		String sql = getUnitProfitMicroSumDataScanSql(cptm);
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlListMap2(sql, null);
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(list.size());
		return dgb;
	}
	
	@Override
	public DataGridBean queryUnitProfitMicroSumDataCash(CheckProfitTemplateMicroBean cptm) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, cptm.getUnno());
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptm.getUnno());
		// @author:xuegangliu-20190404 商户提现手续费非0
		String sql = getMposCashSql(cptm, unitModel, childUnno);
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlListMap2(sql, null);
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(list.size());
		return dgb;
	}

	/**
	 * MPOS分润管理->提现转账分润汇总sql
	 * @param cptm
	 * @param unitModel
	 * @param childUnno
	 * @return
	 */
	private String getMposCashSql(CheckProfitTemplateMicroBean cptm, UnitModel unitModel, String childUnno) {
		String month=null;
		String start = StringUtils.isNotEmpty(cptm.getTxnDay())?cptm.getTxnDay().replace("-", ""):"";
		String end = StringUtils.isNotEmpty(cptm.getTxnDay1())?cptm.getTxnDay1().replace("-", ""):"";
		if(StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end) && start.substring(0,6).equals(end.substring(0,6))){
			month=start.substring(4,6);
		}else{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			month=simpleDateFormat.format(new Date()).substring(4,6);
		}
//		if (value=='000001'){
//			return "理财刷卡";
//		}else if(value=='000002'){
//			return "理财扫码";
//		}else if(value=='100001'){
//			return "秒到刷卡";
//		}else if(value=='100002'){
//			return "秒到扫码";
//		}
		String sql =" select '"+unitModel.getUnitName()+"' as unitName ,a.*, rownum rm " +
				" from (select nvl(round(sum(case when profit <0 then 0 else profit end), 2), 0) profit," +
				" nvl(sum(mda), 0) mda, nvl(sum(txnamount), 0) txnamount,"+
				"  count(mda), count(mda) txncount, settmethod  from (select (case when a.cashmode = 4 then '100001' "+
				//20200226-ztt扫码1000上下变更
				" when a.cashmode = 5 and a.cashday < '20200101' then '100002' "+
				" when a.cashmode = 5 and a.txntype = 3 and a.cashday >= '20200101' then '100004' "+
				" when a.cashmode = 5 and a.txntype = 1 and a.cashday >= '20200101' then '100005' "+
				//20200310-ztt增加区分银联二维码
				" when a.cashmode = 5 and a.txntype = 5 and a.cashday >= '20200101' then '100006' "+
				
				" when a.cashmode = 1 and a.mertype=1 then '000002' "+
				" when a.cashmode = 1 then '000001' end) as settmethod,"+
				"     (select nvl(case when a.cashfee=0 then 0 " +
				" when a.cashmode = 4 then (a.cashfee - y.cashamt) "+
				" when a.cashmode = 5 and a.cashday < '20200101' then (a.cashfee - y.cashamt1) "+
				
				" when a.cashmode = 5 and a.txntype = 1 and a.cashday >= '20200101' then (a.cashfee - y.cashamtabove) "+
				" when a.cashmode = 5 and a.txntype = 3 and a.cashday >= '20200101' then (a.cashfee - y.cashamtunder) "+
				
				" when a.cashmode = 1 and a.mertype=1 then (a.cashfee - a.cashamt * y.cashrate-y.cashamt1) "+
				" when a.cashmode = 1 then (a.cashfee - a.cashamt * y.cashrate-y.cashamt) "+
				"                end,0) from check_micro_profittemplate y, check_unit_profitemplate   uy"+
				"       where uy.unno = '"+cptm.getUnno()+"' and uy.aptid = y.aptid and uy.status != 0 and y.merchanttype in (1,2,4) " +
				" and y.settmethod=(select k.settmethod from bill_merchantinfo k where k.mid=a.mid) " +
				"          and y.mataintype != 'D') profit, a.mid, a.cashfee mda, a.unno, a.rname, a.cashamt txnamount "+
				"         from check_cash_"+month+" a where a.unno in ("+childUnno+")"+
				" and a.cashmode in (1,4,5) ";// and t.maintaintype != 'D' and t.approvestatus = 'Y'
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.cashday>='"+start+"' and a.cashday<='"+end+"'" ;
		}else{
			sql+=" and a.cashday =to_char(sysdate-1,'yyyyMMdd')";
		}
		sql+=" ) group by settmethod)a  ";
		return sql;
	}

	@Override
	public DataGridBean queryUnitProfitMicroSumDataCash2(CheckProfitTemplateMicroBean cptm) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, cptm.getUnno());
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptm.getUnno());
		String sql = getSytCashSql(cptm, unitModel, childUnno);
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlListMap2(sql, null);
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(list.size());
		return dgb;
	}

	/**
	 * 会员宝收银台->收银台提现转账分润 sql
	 * @param cptm
	 * @param unitModel
	 * @param childUnno
	 * @return
	 */
	private String getSytCashSql(CheckProfitTemplateMicroBean cptm, UnitModel unitModel, String childUnno) {
		String month=null;
		String start = StringUtils.isNotEmpty(cptm.getTxnDay())?cptm.getTxnDay().replace("-", ""):"";
		String end = StringUtils.isNotEmpty(cptm.getTxnDay1())?cptm.getTxnDay1().replace("-", ""):"";
		if(StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end) && start.substring(0,6).equals(end.substring(0,6))){
			month=start.substring(4,6);
		}else{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			month=simpleDateFormat.format(new Date()).substring(4,6);
		}
		String sql =" select '"+unitModel.getUnitName()+"' as unitName ,a.*, rownum rm " +
				" from (select nvl(round(sum(case when profit<0 then 0 else profit end), 2), 0) profit," +
				" nvl(sum(mda), 0) mda, nvl(sum(txnamount), 0) txnamount,"+
				"  count(mda), count(mda) txncount ,settmethod,nvl(mertype,'21') mertype from (select  (select "+
				/* " nvl(a.cashfee - y.cashamt,0)"+ */
				" CASE "+
				" WHEN a.txntype = '1' and a.cashday < 20191201 THEN nvl(a.cashfee - y.cashrate, 0)"+
				" WHEN a.txntype = '2' and a.cashday < 20191201 THEN nvl(a.cashfee - y.profitpercent1, 0)"+
				" WHEN a.txntype = '3' and a.cashday < 20191201 THEN nvl(a.cashfee - y.cashamt, 0)"+
				" WHEN a.txntype = '4' and a.cashday < 20191201 THEN nvl(a.cashfee - y.cashamt1, 0)"+
				" WHEN a.txntype = '5' and a.cashday < 20191201 THEN nvl(a.cashfee - y.cashamt2, 0)"+
				
				//ztt20191205,微信支付宝统一划分后添加判断（cashday>20191201为新，反之为旧）
				" WHEN a.txntype = '1' and a.cashday >= 20191201 THEN nvl(a.cashfee - y.cashrate, 0)"+
				" WHEN a.txntype = '2' and a.cashday >= 20191201 THEN nvl(a.cashfee - y.profitpercent1, 0)"+
				" WHEN a.txntype = '3' and a.cashday >= 20191201 THEN nvl(a.cashfee - y.cashamt, 0)"+
				" WHEN a.txntype = '4' and a.cashday >= 20191201 THEN nvl(a.cashfee - y.cashamt1, 0)"+
				" WHEN a.txntype = '5' and a.cashday >= 20191201 THEN nvl(a.cashfee - y.cashamt2, 0)"+
				" when a.txntype = '6' then nvl(a.cashfee - y.huabeifee, 0)"+
				" ELSE nvl(a.cashfee - y.cashamt,0)"+
				" END AS profitone"+
				
				
				" from check_micro_profittemplate y, check_unit_profitemplate   uy"+
				"       where uy.unno = '"+cptm.getUnno()+"'"+
				" and nvl(a.mertype,'21')=nvl(y.profitrule,'21')"+
			    " and uy.aptid = y.aptid and uy.status != 0 and y.merchanttype = 5 " +
				"         and y.mataintype != 'D') profit, a.mid, a.cashfee mda, a.unno, a.rname, a.cashamt txnamount ,txntype,"+
				
				" case WHEN a.txntype = '1' and a.cashday < 20191201 THEN '微信0.38'"+
				" WHEN a.txntype = '2' and a.cashday < 20191201 THEN '微信0.45'"+
				" WHEN a.txntype = '3' and a.cashday < 20191201 THEN '微信（老）'"+
				" WHEN a.txntype = '4' and a.cashday < 20191201 THEN '支付宝'"+
				" WHEN a.txntype = '5' and a.cashday < 20191201 THEN '二维码'"+
				
				" WHEN a.txntype = '1' and a.cashday >= 20191201 THEN '扫码1000以上（终端0.38）'"+
				" WHEN a.txntype = '2' and a.cashday >= 20191201 THEN '扫码1000以上（终端0.45）'"+
				" WHEN a.txntype = '3' and a.cashday >= 20191201 THEN '扫码1000以下（终端0.38）'"+
				" WHEN a.txntype = '4' and a.cashday >= 20191201 THEN '扫码1000以下（终端0.45）'"+
				" WHEN a.txntype = '5' and a.cashday >= 20191201 THEN '银联二维码'"+
				" when a.txntype = '6' then '花呗'"+
				" else '其他' end as settmethod"+
				" ,a.mertype"+
				
				" from check_cash_"+month+" a where a.unno in ("+childUnno+")"+
				" and a.cashmode = 6 ";

		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.cashday>='"+start+"' and a.cashday<='"+end+"'" ;
		}else{
			sql+=" and a.cashday =to_char(sysdate-1,'yyyyMMdd')";
		}
		
		if(cptm.getProfitRule()!=null && !"".equals(cptm.getProfitRule())){
			sql+=" and a.mertype = '"+cptm.getProfitRule()+"'" ;
		}
		
		
		sql+=" ) group by settmethod,mertype )a  ";
		return sql;
	}

	@Override
	public DataGridBean queryUnitProfitMicroDetailData(CheckProfitTemplateMicroBean cptm) {
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptm.getUnno());
		String sql=" select  a.*,   rownum rm   from ( select nvl(round(sum(profit), 2), 0) profit ,  nvl(sum(mda),  0) mda ,"+
                   " nvl(sum(txnamount), 0) txnamount,  mid, unno, rname, count(mda), count(mda) txncount ,settmethod ," +
                   " (case when feeamt>0 then 2 else 1 end) as MERCHANTTYPE ,t from "+
               	   " (select decode(t.settmethod,000000,'000000','100000') as settmethod, (select nvl(case when a.ifcard=3 then (mda - txnamount*nvl(y.cloudquickrate,0.003)) * y.profitpercent when a.cbrand='1' and t.settmethod='000000' then "+
                            "  case when txnamount > y.endamount then  (mda - y.startamount) * y.profitpercent  "+
                            "        else (mda - txnamount * y.rulethreshold) * y.profitpercent   END  "+
                            " else (mda - txnamount * y.creditbankrate) * y.profitpercent   end, 0) "+
                   " from check_micro_profittemplate y ,check_unit_profitemplate uy where  uy.unno = '"+cptm.getUnno()+"' and uy.aptid=y.aptid and uy.status!=0 " +
                   " and y.merchanttype =decode(t.settmethod,000000,1,case when a.mda/a.txnamount>0.007 then 2 else 3 end) "+
                   // " and y.merchanttype =decode(t.settmethod,100000,decode(t.creditbankrate, 0.007200, 2, 3),1) "+
                   " and y.settmethod = t.settmethod and y.mataintype != 'D') profit,  t.mid, a.mda mda, t.unno, t.rname, a.txnamount txnamount,t.feeamt, " +
                   " decode(t.settmethod,000000,1,case when a.ifcard=3 then 4 when a.mda/a.txnamount>0.007 then 2 else 3 end) as t "+
                   //" decode(t.settmethod,100000,decode(t.creditbankrate, 0.007200, 2, 3),1) as t "+
                   " from bill_merchantinfo t, check_unitdealdetail a  where  t.mid = a.mid  "+
                   " and t.unno in ("+childUnno+") and t.ism35=1  ";// and t.maintaintype != 'D' and t.approvestatus = 'Y' 
		
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.txnday>='"+cptm.getTxnDay().replace("-", "")+"' and a.txnday<='"+cptm.getTxnDay1().replace("-", "")+"'" ;
		}else{
			sql+=" and a.txnday= to_char(sysdate-1,'yyyyMMdd')";
		}
		sql+=" ) group by mid, unno, rname ,settmethod ,feeamt,t)a  ";
		String sqlCount ="select  count(*) from ("+sql+")";
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, null, cptm.getPage(), cptm.getRows());
		Integer count=checkUnitProfitMicroDao.querysqlCounts2(sqlCount, null);
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}
	
	@Override
	public DataGridBean queryUnitProfitMicroDetailDataScan(CheckProfitTemplateMicroBean cptm) {
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptm.getUnno());
//		String sql=" select a.*, rownum rm  from (select nvl(round(sum(profit), 2), 0) profit, nvl(sum(mda), 0) mda, nvl(sum(txnamount), 0) txnamount,"+
//                   " mid, unno, rname, count(mda), count(mda) txncount, settmethod  from (select decode(t.settmethod,100000,'100000',200000,'100000','000000') as settmethod,"+
//                   "     (select nvl((mda - txnamt * y.scanrate),0) from check_micro_profittemplate y, check_unit_profitemplate   uy"+
//                   "       where uy.unno = '"+cptm.getUnno()+"' and uy.aptid = y.aptid and uy.status != 0 and y.merchanttype = 1 " +
//                   "         and y.settmethod ='000000' and y.mataintype != 'D') profit, t.mid, a.mda mda, t.unno, t.rname, a.txnamt txnamount "+
//                   "         from bill_merchantinfo t, check_wechattxndetail a where t.mid = a.mid and t.unno in ("+childUnno+")"+
//                   " and t.ism35 = 1 and a.status='1'  ";
		String sql="select nvl(round(sum(profit), 2), 0) profit, nvl(sum(mda), 0) mda, nvl(sum(txnamount), 0) txnamount,"+
        " mid, unno, rname, count(mda), count(mda) txncount, settmethod  from (select case when a.trantype = '8' and a.ispoint = 0 then '800000' when a.trantype = '8' then  '800001' "+
		" when decode(t.settmethod,000000,'000000','100000')='000000' and a.trantype='1' then '000001' "+
		" when decode(t.settmethod,000000,'000000','100000')='000000' and a.trantype='2' then '000002' "+
		" when decode(t.settmethod,000000,'000000','100000')='000000' and a.trantype='3' then '000003' "+
		" when decode(t.settmethod,000000,'000000','100000')='100000' and a.trantype='1' and a.cdate < to_date('20200101000000', 'yyyymmddhh24miss') then '100001' "+
		" when decode(t.settmethod,000000,'000000','100000')='100000' and a.trantype='2' and a.cdate < to_date('20200101000000', 'yyyymmddhh24miss') then '100002' "+
		" when decode(t.settmethod,000000,'000000','100000')='100000' and a.trantype='3' then '100003'"+
		//20200226-ztt扫码1000上下(根据交易金额判断上下)
		" when decode(t.settmethod,000000,'000000','100000')='100000' and a.txnamt<1000 and a.cdate >= to_date('20200101000000', 'yyyymmddhh24miss') then '100004' "+
		" when decode(t.settmethod,000000,'000000','100000')='100000' and a.txnamt>=1000 and a.cdate >= to_date('20200101000000', 'yyyymmddhh24miss') then '100005' "+
		
		"  end as settmethod,"+
        " (select nvl(case when a.trantype='8' and a.ispoint=0 then (mda - txnamt * y.startamount) * nvl(y.profitpercent1,1) "+
		" when a.trantype='8' then (mda - txnamt * y.endamount) * nvl(y.profitpercent1,1) "+
		" when a.trantype='3' then mda - txnamt * y.scanrate2 "+
		
		" when decode(t.settmethod, 000000, '000000', '100000')='000000' and a.trantype = '1' then mda - txnamt * y.scanrate "+
		" when decode(t.settmethod, 000000, '000000', '100000') ='000000' and a.trantype = '2' then mda - txnamt * y.scanrate1"+
		" when decode(t.settmethod, 000000, '000000', '100000') ='100000' and a.trantype = '1' and a.cdate < to_date('20200101000000', 'yyyymmddhh24miss') then mda - txnamt * y.scanrate"+
		" when decode(t.settmethod, 000000, '000000', '100000') ='100000' and a.trantype = '2' and a.cdate < to_date('20200101000000', 'yyyymmddhh24miss') then mda - txnamt * y.scanrate1"+
		" when decode(t.settmethod, 000000, '000000', '100000') ='100000' and a.txnamt < 1000 and a.cdate >= to_date('20200101000000', 'yyyymmddhh24miss') then mda - txnamt * y.scanrate"+
		" when decode(t.settmethod, 000000, '000000', '100000') ='100000' and a.txnamt >= 1000 and a.cdate >= to_date('20200101000000', 'yyyymmddhh24miss') then mda - txnamt * y.scanrate1"+
		
		" end,0) from check_micro_profittemplate y, check_unit_profitemplate uy"+
        "       where uy.unno = '"+cptm.getUnno()+"' and uy.aptid = y.aptid and uy.status != 0 and y.merchanttype = 2 " +
        "         and y.settmethod ='100000' and y.mataintype != 'D') profit, t.mid, a.mda mda, t.unno, t.rname, a.txnamt txnamount "+
        "         from bill_merchantinfo t, check_wechattxndetail a where t.mid not like 'HRTSYT%' and t.mid = a.mid and a.mer_orderid not like '886600%' and a.txntype='0' and t.unno in ("+childUnno+")"+
        " and t.ism35 = 1 and a.status='1'  ";
		
//		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
//			sql+=" and to_char(a.cdate,'yyyyMMdd')>='"+cptm.getTxnDay().replace("-", "")+
//				 "' and to_char(a.cdate,'yyyyMMdd')<='"+cptm.getTxnDay1().replace("-", "")+"'" ;
//		}else{
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.cdate>=to_date('"+cptm.getTxnDay()+"','yyyy-MM-dd') and a.cdate<=to_date('"+cptm.getTxnDay1()+" 23:59:59','yyyy-MM-dd HH24:mi:ss')" ;
		}else{
//			sql+=" and to_char(a.cdate,'yyyyMMdd')= to_char(sysdate-1,'yyyyMMdd')";
			return null;
		}
		sql+=" ) group by mid, unno, rname ,settmethod ";
		String sqlCount ="select  count(*) from ("+sql+")";
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, null, cptm.getPage(), cptm.getRows());
		Integer count=checkUnitProfitMicroDao.querysqlCounts2(sqlCount, null);
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}
	@Override
	public DataGridBean queryUnitSubTemplateDetailDataScan2(CheckProfitTemplateMicroBean cptm,UserBean user) {
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptm.getUnno());
		String sql="select nvl(round(sum(profit),2),0) profit,nvl(sum(mda),0) mda,nvl(sum(txnamount),0) txnamount,mid,unno,rname,count(mda) txncount "+
				"from (select (select nvl((mda - (txnamt-mda) * y.subrate),0) from check_micro_profittemplate y, check_unit_profitemplate uy   "+    
				"where uy.unno = '"+cptm.getUnno()+"' and uy.aptid = y.aptid and uy.status != 0 and y.merchanttype = 4 and y.mataintype != 'D') profit, "+
				"t.mid,a.mda mda,t.unno,t.rname,a.txnamt txnamount from bill_merchantinfo t,check_wechattxndetail a "+
				"where t.mid = a.mid and t.unno in ("+childUnno+") and a.status='1' and a.mer_orderid like '886600%'";
		
		String sql2="select nvl(round(sum(profit),2),0) profit,nvl(sum(mda),0) mda,nvl(sum(txnamount),0) txnamount,count(mda) txncount "+
				"from (select (select nvl((mda - (txnamt-mda) * y.subrate),0) from check_micro_profittemplate y, check_unit_profitemplate uy   "+    
				"where uy.unno = '"+cptm.getUnno()+"' and uy.aptid = y.aptid and uy.status != 0 and y.merchanttype = 4 and y.mataintype != 'D') profit, "+
				"t.mid,a.mda mda,t.unno,t.rname,a.txnamt txnamount from bill_merchantinfo t,check_wechattxndetail a "+
				"where t.mid = a.mid and t.unno in ("+childUnno+") and a.status='1' and a.mer_orderid like '886600%'";
		
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.cdate>=to_date('"+cptm.getTxnDay()+"','yyyy-MM-dd') and a.cdate<=to_date('"+cptm.getTxnDay1()+" 23:59:59','yyyy-MM-dd HH24:mi:ss')" ;
			sql2+=" and a.cdate>=to_date('"+cptm.getTxnDay()+"','yyyy-MM-dd') and a.cdate<=to_date('"+cptm.getTxnDay1()+" 23:59:59','yyyy-MM-dd HH24:mi:ss')" ;
		}else{
			return null;
		}
		sql+=" ) group by mid, unno, rname ";
		sql2+=")";
		String sqlCount ="select  count(*) from ("+sql+")";
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, null, cptm.getPage(), cptm.getRows());
		Integer count=checkUnitProfitMicroDao.querysqlCounts2(sqlCount, null);
		List<Map<String,Object>> list2 = checkUnitProfitMicroDao.queryObjectsBySqlListMap2(sql2, null);
		list.add(0, list2.get(0));
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}
	@Override
	public DataGridBean queryUnitSytTemplateDetailDataScan2(CheckProfitTemplateMicroBean cptm,UserBean user) {
		// @author:xuegangiu-20190221 收银台-收银台明细 查自己子代数据与自身数据明细
		DataGridBean dgb = new DataGridBean();
		//必须传日期，且日期必须是同年同月
		if(cptm.getTxnDay()==null||"".equals(cptm.getTxnDay())||cptm.getTxnDay1()==null||"".equals(cptm.getTxnDay1())||!cptm.getTxnDay().substring(0, 6).equals(cptm.getTxnDay1().substring(0, 6))) {
			return null;
		}
		//2018-10-10 转为 20181010
		cptm.setTxnDay(cptm.getTxnDay().replace("-", ""));
		cptm.setTxnDay1(cptm.getTxnDay1().replace("-", ""));
//        String selectUnno = StringUtils.isEmpty(cptm.getUnno())?user.getUnNo():cptm.getUnno();
        String selectUnno = cptm.getUnno();
		String childUnno=merchantInfoService.queryUnitUnnoUtil(selectUnno);
		// @author:lxg-20190412 收银台分润明细 分润算法 mda - TXNAMOUNT * y.subrate
		String sql="SELECT case when nvl(round(SUM(profit), 2), 0)<=0 then 0 else nvl(round(SUM(profit), 2), 0) end AS profit"
				+ " , nvl(SUM(mda), 0) AS mda , nvl(SUM(txnamount), 0) AS txnamount , "
				+ "mid, unno, rname,un_name, COUNT(mda) AS txncount ,SETTMETHOD,nvl(maintainuid,'21') maintainuid FROM ( SELECT ( SELECT"
				/* +" nvl(mda - TXNAMOUNT * y.subrate, 0) " */
				
				+" CASE "
				+" WHEN a.type = '1' and a.ifcard = '1' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.creditbankrate, 0)"
				+" WHEN a.type = '1' and a.ifcard = '2' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate, 0)"
				+" WHEN a.type = '1' and a.ifcard = '3' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.subrate, 0)"
				+" WHEN a.type = '2' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate1, 0)"
				+" WHEN a.type = '3'and a.txnday < 20191201  THEN nvl(a.mda - a.txnamount * y.scanrate2, 0)"
				
				+" WHEN a.type in (1,2) and a.ifcard = '1' and a.txnday >= 20191201 THEN nvl(a.mda - a.txnamount * y.creditbankrate, 0)"
				+" WHEN a.type in (1,2) and a.ifcard = '2' and a.txnday >= 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate, 0)"
				+" WHEN a.type in (1,2) and a.ifcard = '3' and a.txnday >= 20191201 THEN nvl(a.mda - a.txnamount * y.subrate, 0)"
				+" WHEN a.type in (1,2) and a.ifcard = '4' and a.txnday >= 20191201  THEN nvl(a.mda - a.txnamount * y.scanrate1, 0)"
				+" WHEN a.type = '3' and a.txnday > 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate2, 0)"
				+" WHEN a.type in (11,12)  THEN nvl(a.mda - a.txnamount * y.huabeirate, 0)"
				+" ELSE nvl(a.mda - a.txnamount * y.subrate, 0)"
				+" END AS profitone"
				
				+ " FROM check_micro_profittemplate y, check_unit_profitemplate uy WHERE uy.unno = '"+selectUnno+"'"
			    + "AND uy.aptid = y.aptid  "
				
			    +" and nvl(y.profitrule,'21') = nvl(a.maintainuid,'21') "
				+ "  AND uy.status != 0 AND y.merchanttype = 5 AND y.mataintype != 'D' ) AS profit, a.mid, a.mda AS mda, a.unno,a.un_name, a.rname , "
				+ "a.TXNAMOUNT AS txnamount, "
				
				+" case WHEN a.type = '1' and a.ifcard = '1' and a.txnday < 20191201 THEN '微信0.38'"
				+" WHEN a.type = '1' and a.ifcard = '2' and a.txnday < 20191201 THEN '微信0.45'"
				+" WHEN a.type = '1' and a.ifcard = '3' and a.txnday < 20191201 THEN '微信（老）'"
				+" WHEN a.type = '2' and a.txnday < 20191201 THEN '支付宝'"
				+" WHEN a.type = '3' and a.txnday < 20191201 THEN '二维码'"
				+" WHEN a.type in (1,2) and a.ifcard = '1' and a.txnday >= 20191201 THEN '扫码1000以上（终端0.38）'"
				+" WHEN a.type in (1,2) and a.ifcard = '2' and a.txnday >= 20191201 THEN '扫码1000以上（终端0.45）'"
				+" WHEN a.type in (1,2) and a.ifcard = '3' and a.txnday >= 20191201 THEN '扫码1000以下（终端0.38）'"
				+" WHEN a.type in (1,2) and a.ifcard = '4' and a.txnday >= 20191201 THEN '扫码1000以下（终端0.45）'"
				+" WHEN a.type = '3' and a.txnday >= 20191201 THEN '银联二维码'"
				+" WHEN a.type in (11,12) then '花呗' "
				+" ELSE '其他'  END AS SETTMETHOD"
				+" ,a.maintainuid"
				
				+" FROM check_unitdealdetail_"+cptm.getTxnDay().substring(4, 6)+" a WHERE "
				+ "a.unno IN ("+childUnno+") AND a.txnstate = 0 AND a.ismpos = 2 ";
		
		String sql2="SELECT nvl(round(SUM(profit), 2), 0) AS profit , nvl(SUM(mda), 0) AS mda , nvl(SUM(txnamount), 0) AS txnamount , "
				+ "COUNT(mda) AS txncount FROM ( SELECT ( SELECT "
				/* +"nvl(mda - TXNAMOUNT * y.subrate, 0) " */
				
				+" CASE "
				+" WHEN a.type = '1' and a.ifcard = '1' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.creditbankrate, 0)"
				+" WHEN a.type = '1' and a.ifcard = '2' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate, 0)"
				+" WHEN a.type = '1' and a.ifcard = '3' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.subrate, 0)"
				+" WHEN a.type = '2' and a.txnday < 20191201  THEN nvl(a.mda - a.txnamount * y.scanrate1, 0)"
				+" WHEN a.type = '3' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate2, 0)"
				
				+" WHEN a.type in (1,2) and a.ifcard = '1' and a.txnday >= 20191201 THEN nvl(a.mda - a.txnamount * y.creditbankrate, 0)"
				+" WHEN a.type in (1,2) and a.ifcard = '2' and a.txnday >= 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate, 0)"
				+" WHEN a.type in (1,2) and a.ifcard = '3' and a.txnday >= 20191201 THEN nvl(a.mda - a.txnamount * y.subrate, 0)"
				+" WHEN a.type in (1,2) and a.ifcard = '4' and a.txnday >= 20191201  THEN nvl(a.mda - a.txnamount * y.scanrate1, 0)"
				+" WHEN a.type = '3' and a.txnday >= 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate2, 0)"
				+" WHEN a.type in (11,12) THEN nvl(a.mda - a.txnamount * y.huabeiRate, 0)"
				
				+" ELSE nvl(a.mda - a.txnamount * y.subrate, 0)"
				+" END AS profitone"
				
				
				+ " FROM check_micro_profittemplate y, check_unit_profitemplate uy WHERE uy.unno = '"+selectUnno+"'"
				+ " and nvl(y.profitrule,'21') = nvl(a.maintainuid,'21')"
				+ " AND uy.aptid = y.aptid AND "
				+ "uy.status != 0 AND y.merchanttype = 5 AND y.mataintype != 'D' ) AS profit, a.mid, a.mda AS mda, a.unno, a.rname , "
				+ "a.TXNAMOUNT AS txnamount"+
				" FROM check_unitdealdetail_"+cptm.getTxnDay().substring(4, 6)+" a WHERE "
				+ "a.unno IN ("+childUnno+") AND a.txnstate = 0 AND a.ismpos = 2 ";
		
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.txnday>='"+cptm.getTxnDay()+"' and a.txnday<='"+cptm.getTxnDay1()+"'" ;
			sql2+=" and a.txnday>='"+cptm.getTxnDay()+"' and a.txnday<='"+cptm.getTxnDay1()+"'" ;
		}
		
		//添加活动类型
		if(cptm.getProfitRule()!=null && !"".equals(cptm.getProfitRule())){
			sql+=" and a.maintainuid = "+cptm.getProfitRule()+"";
			sql2+=" and a.maintainuid = "+cptm.getProfitRule()+"";
		}
		
//		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
//			sql+=" and a.unno = '"+cptm.getUnno()+"'" ;
//			sql2+=" and a.unno = '"+cptm.getUnno()+"'" ;
//		}
		sql+=" )b group by mid, unno, rname,un_name,SETTMETHOD,maintainuid ";
		sql2+=")";
		String sqlCount ="select  count(*) from ("+sql+")";
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, null, cptm.getPage(), cptm.getRows());
		Integer count=checkUnitProfitMicroDao.querysqlCounts2(sqlCount, null);
		List<Map<String,Object>> list2 = checkUnitProfitMicroDao.queryObjectsBySqlListMap2(sql2, null);
		list.add(0, list2.get(0));
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}
	@Override
	public DataGridBean queryUnitSytTemplateDetailDataScan(CheckProfitTemplateMicroBean cptm,UserBean user) {
		DataGridBean dgb = new DataGridBean();
		if(cptm.getTxnDay()==null||"".equals(cptm.getTxnDay())||cptm.getTxnDay1()==null||"".equals(cptm.getTxnDay1())) {
			return dgb;
		}
		Map parm = new HashMap();
		String sql = getUnitSytTemplateDetailDataScanSql(cptm,parm);
		String sqlCount ="select  count(*) from ("+sql+")";
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, parm, cptm.getPage(), cptm.getRows());
		Integer count=checkUnitProfitMicroDao.querysqlCounts2(sqlCount, parm);
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}
	@Override
	public DataGridBean queryUnitSubTemplateDetailDataScan(CheckProfitTemplateMicroBean cptm,UserBean user) {
		String childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
		String sql="select b.unno,b.mid,b.rname,a.lmdate,a.txnamt,a.mda from check_wechattxndetail a,bill_merchantinfo b "+
					"where a.mid=b.mid and a.trantype='10' and b.unno in ("+childUnno+") "+
					"and b.maintaintype != 'D' and b.approvestatus = 'Y' ";
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.cdate>=to_date('"+cptm.getTxnDay()+"','yyyy-MM-dd') and a.cdate<=to_date('"+cptm.getTxnDay1()+" 23:59:59','yyyy-MM-dd HH24:mi:ss')" ;
		}else{
			return null;
		}
		if(cptm.getMid()!=null && !"".equals(cptm.getMid())){
			sql+=" and b.mid="+cptm.getMid() ;
		}
		if(cptm.getRname()!=null && !"".equals(cptm.getRname())){
			sql+=" and b.rname like '%"+cptm.getRname()+"%'" ;
		}
		String sqlCount ="select  count(*) from ("+sql+")";
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, null, cptm.getPage(), cptm.getRows());
		Integer count=checkUnitProfitMicroDao.querysqlCounts2(sqlCount, null);
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}
	@SuppressWarnings("unchecked")
	@Override
	public DataGridBean getProfitUnitGodes(UserBean user,String nameCode) throws Exception{			
		String sql = "SELECT t.UNNO,t.UN_NAME FROM SYS_UNIT  t WHERE 1=1  ";
		DataGridBean dgd = new DataGridBean();
		if(user.getUnitLvl()==0){
			sql+=" and t.un_lvl in(3,5,6,7,8) ";
		}else if (user.getUseLvl()==3){
			return dgd;
		}else {
			sql+=" and (upper_unit='"+user.getUnNo()+"' or unno='"+user.getUnNo()+"')";
		}
		if(nameCode !=null){
			sql += " AND (t.UNNO LIKE '%" + nameCode.replaceAll("'", "") + "%' OR t.UN_NAME LIKE '%" + nameCode.replaceAll("'", "")+ "%') ";
		}
		sql+=" order by t.UN_NAME ASC";
		List<Map<String,String>> proList = checkUnitProfitMicroDao.executeSql(sql);	
		dgd.setTotal(proList.size());
		dgd.setRows(proList);
		
		return dgd;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DataGridBean getProfitUnitGodesForSales(UserBean user,String nameCode) throws Exception{			
		String sql = "SELECT t.UNNO,t.UN_NAME FROM SYS_UNIT  t WHERE 1=1  ";
		DataGridBean dgd = new DataGridBean();
		Map map = new HashMap(16);
		if(user.getUnitLvl()==0){
			sql+=" and t.un_lvl in(3,5,6,7,8) ";
		}else if (user.getUseLvl()==3){
			return dgd;
		}else if(user.getUseLvl()==1||user.getUseLvl()==2){//报单员和业务员只能看自己签约的机构
			sql += " and t.unno in (SELECT b1.unno FROM bill_agentsalesinfo a1, bill_agentunitinfo b1 WHERE a1.busid = b1.signuserid AND a1.user_id = :user_id ) ";
			map.put("user_id",user.getUserID());
		}else {
			sql+=" and (t.upper_unit=:unno or t.unno=:unno )";
			map.put("unno",user.getUnNo());
		}
		if(nameCode !=null){
			sql += " AND (t.UNNO LIKE :nameCode OR t.UN_NAME LIKE :nameCode ) ";
			map.put("nameCode","%"+nameCode.replaceAll("'", "")+"%");
		}
		sql+=" order by t.UN_NAME ASC";
		List<Map<String,String>> proList = checkUnitProfitMicroDao.queryObjectsBySqlListMap(sql,map);
		dgd.setTotal(proList.size());
		dgd.setRows(proList);
		
		return dgd;
	}

	@Override
	public HSSFWorkbook exportUnitProfitMicroDetailDataScan(CheckProfitTemplateMicroBean cptm, UserBean userBean) {
		
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptm.getUnno());
		String sql =" select nvl(round(sum(profit), 2), 0) profit, nvl(sum(mda), 0) mda, nvl(sum(txnamount), 0) txnamount,"+
			        " mid, unno, rname, count(mda), count(mda) txncount, settmethod  from (select case when a.trantype = '8' and a.ispoint = 0 then '800000' when a.trantype = '8' then  '800001' "+
					" when decode(t.settmethod,000000,'000000','100000')='000000' and a.trantype='1' then '000001' "+
					" when decode(t.settmethod,000000,'000000','100000')='000000' and a.trantype='2' then '000002' "+
					" when decode(t.settmethod,000000,'000000','100000')='000000' and a.trantype='3' then '000003' "+
					" when decode(t.settmethod,000000,'000000','100000')='100000' and a.trantype='1' then '100001' "+
					" when decode(t.settmethod,000000,'000000','100000')='100000' and a.trantype='2' then '100002' "+
					" when decode(t.settmethod,000000,'000000','100000')='100000' and a.trantype='3' then '100003' end as settmethod,"+
			        " (select nvl(case when a.trantype='8' and a.ispoint=0 then (mda - txnamt * y.startamount) * nvl(y.profitpercent1,1) "+
					" when a.trantype='8' then (mda - txnamt * y.endamount) * nvl(y.profitpercent1,1) "+
					" when a.trantype='1' then mda - txnamt * y.scanrate "+
					" when a.trantype='2' then mda - txnamt * y.scanrate1 "+
					" when a.trantype='3' then mda - txnamt * y.scanrate2 end,0) from check_micro_profittemplate y, check_unit_profitemplate   uy"+
			        "       where uy.unno = '"+cptm.getUnno()+"' and uy.aptid = y.aptid and uy.status != 0 and y.merchanttype = 2 " +
			        "         and y.settmethod ='100000' and y.mataintype != 'D') profit, t.mid, a.mda mda, t.unno, t.rname, a.txnamt txnamount "+
			        "         from bill_merchantinfo t, check_wechattxndetail a where t.mid = a.mid and t.unno in ("+childUnno+")"+
			        " and t.ism35 = 1  and a.status='1'  ";//and t.maintaintype != 'D' and t.approvestatus = 'Y'

//		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
//			sql+=" and to_char(a.cdate,'yyyyMMdd')>='"+cptm.getTxnDay().replace("-", "")+
//				 "' and to_char(a.cdate,'yyyyMMdd')<='"+cptm.getTxnDay1().replace("-", "")+"'" ;
//		}else{
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.cdate>=to_date('"+cptm.getTxnDay()+"','yyyy-MM-dd') and a.cdate<=to_date('"+cptm.getTxnDay1()+" 23:59:59','yyyy-MM-dd HH24:mi:ss')" ;
		}else{	
			sql+=" and to_char(a.cdate,'yyyyMMdd')= to_char(sysdate-1,'yyyyMMdd')";
		}
		sql+=" ) group by mid, unno, rname ,settmethod  ";
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlListMap2(sql, null);
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNNO");
		excelHeader.add("MID");
		excelHeader.add("RNAME");
		excelHeader.add("SETTMETHOD");
		excelHeader.add("TXNAMOUNT");
		excelHeader.add("MDA");
		excelHeader.add("PROFIT");

		headMap.put("UNNO", "机构号");
		headMap.put("MID", "商户编号");
		headMap.put("RNAME", "商户名称");
		headMap.put("SETTMETHOD", "结算类型(100001:秒到微信；100002:秒到支付宝；100003:秒到银联二维码；000001：理财微信;000002：理财支付宝;000003：理财银联二维码;800000VIP;800001完美)");
		headMap.put("TXNAMOUNT", "交易金额");
		headMap.put("MDA", "手续费金额");
		headMap.put("PROFIT", "分润金额");

		return ExcelUtil.export("代理分润明细", list, excelHeader, headMap);
	}
	@Override
	public HSSFWorkbook exportUnitProfitMicroDetailDataScan2(CheckProfitTemplateMicroBean cptm, UserBean userBean) {
		
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptm.getUnno());
		String sql="select nvl(round(sum(profit),2),0) profit,nvl(sum(mda),0) mda,nvl(sum(txnamount),0) txnamount,mid,unno,rname,count(mda) txncount "+
				"from (select (select nvl((mda - (txnamt-mda) * y.subrate),0) from check_micro_profittemplate y, check_unit_profitemplate uy   "+    
				"where uy.unno = '"+cptm.getUnno()+"' and uy.aptid = y.aptid and uy.status != 0 and y.merchanttype = 4 and y.mataintype != 'D') profit, "+
				"t.mid,a.mda mda,t.unno,t.rname,a.txnamt txnamount from bill_merchantinfo t,check_wechattxndetail a "+
				"where t.mid = a.mid and t.unno in ("+childUnno+") and a.status='1' and a.mer_orderid like '886600%'";
		
		String sql2="select nvl(round(sum(profit),2),0) profit,nvl(sum(mda),0) mda,nvl(sum(txnamount),0) txnamount,count(mda) txncount "+
				"from (select (select nvl((mda - (txnamt-mda) * y.subrate),0) from check_micro_profittemplate y, check_unit_profitemplate uy   "+    
				"where uy.unno = '"+cptm.getUnno()+"' and uy.aptid = y.aptid and uy.status != 0 and y.merchanttype = 4 and y.mataintype != 'D') profit, "+
				"t.mid,a.mda mda,t.unno,t.rname,a.txnamt txnamount from bill_merchantinfo t,check_wechattxndetail a "+
				"where t.mid = a.mid and t.unno in ("+childUnno+") and a.status='1' and a.mer_orderid like '886600%'";
		
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.cdate>=to_date('"+cptm.getTxnDay()+"','yyyy-MM-dd') and a.cdate<=to_date('"+cptm.getTxnDay1()+" 23:59:59','yyyy-MM-dd HH24:mi:ss')" ;
			sql2+=" and a.cdate>=to_date('"+cptm.getTxnDay()+"','yyyy-MM-dd') and a.cdate<=to_date('"+cptm.getTxnDay1()+" 23:59:59','yyyy-MM-dd HH24:mi:ss')" ;
		}else{
			return null;
		}
		sql+=" ) group by mid, unno, rname ";
		sql2+=")";
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, null, cptm.getPage(), cptm.getRows());
		List<Map<String,Object>> list2 = checkUnitProfitMicroDao.queryObjectsBySqlListMap2(sql2, null);
		list.add(0, list2.get(0));
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNNO");
		excelHeader.add("MID");
		excelHeader.add("RNAME");
		excelHeader.add("TXNAMOUNT");
		excelHeader.add("TXNCOUNT");
		excelHeader.add("MDA");
		excelHeader.add("PROFIT");

		headMap.put("UNNO", "机构号");
		headMap.put("MID", "商户编号");
		headMap.put("RNAME", "商户名称");
		headMap.put("TXNAMOUNT", "扣款金额");
		headMap.put("TXNCOUNT", "扣款笔数");
		headMap.put("MDA", "手续费金额");
		headMap.put("PROFIT", "分润金额");

		return ExcelUtil.export("代理代还分润明细", list, excelHeader, headMap);
	}
	@Override
	public HSSFWorkbook exportUnitProfitMicroDetailDataScan3(CheckProfitTemplateMicroBean cptm, UserBean userBean) {
	    //  收银台-收银台明细导出
		//必须传日期，且日期必须是同年同月
		if(cptm.getTxnDay()==null||"".equals(cptm.getTxnDay())||cptm.getTxnDay1()==null||"".equals(cptm.getTxnDay1())||!cptm.getTxnDay().substring(0, 6).equals(cptm.getTxnDay1().substring(0, 6))) {
			return null;
		}
		//2018-10-10 转为 20181010
		cptm.setTxnDay(cptm.getTxnDay().replace("-", ""));
		cptm.setTxnDay1(cptm.getTxnDay1().replace("-", ""));
//        String selectUnno = StringUtils.isEmpty(cptm.getUnno())?userBean.getUnNo():cptm.getUnno();
        String selectUnno = cptm.getUnno();
		String childUnno=merchantInfoService.queryUnitUnnoUtil(selectUnno);
		// @author:lxg-20190412 收银台分润明细 分润算法为 mda - TXNAMOUNT* y.subrate
		String sql="SELECT"
			//	+" nvl(round(SUM(profit), 2), 0) AS profit "
				+" case "
				+" when nvl(round(SUM(profit), 2), 0) >=0 then nvl(round(SUM(profit), 2), 0)"
				+" else 0 end as profit"

				+" , nvl(SUM(mda), 0) AS mda , nvl(SUM(txnamount), 0) AS txnamount , "
				+ "mid, unno, rname,transactionType,nvl(maintainuid,'21') maintainuid, COUNT(mda) AS txncount FROM ( SELECT ( SELECT"
//				+" nvl(mda - TXNAMOUNT * y.subrate, 0)"
				
				+" CASE "
				+" WHEN a.type = '1' and a.ifcard = '1' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.creditbankrate, 0)"
				+" WHEN a.type = '1' and a.ifcard = '2' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate, 0)"
				+" WHEN a.type = '1' and a.ifcard = '3' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.subrate, 0)"
				+" WHEN a.type = '2' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate1, 0)"
				+" WHEN a.type = '3' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate2, 0)"
				+" WHEN a.type in (1,2) and a.ifcard = '1' and a.txnday >= 20191201 THEN nvl(a.mda - a.txnamount * y.creditbankrate, 0)"
				+" WHEN a.type in (1,2) and a.ifcard = '2' and a.txnday >= 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate, 0)"
				+" WHEN a.type in (1,2) and a.ifcard = '3' and a.txnday >= 20191201 THEN nvl(a.mda - a.txnamount * y.subrate, 0)"
				+" WHEN a.type in (1,2) and a.ifcard = '4' and a.txnday >= 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate1, 0)"
				+" WHEN a.type = '3' and a.txnday > 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate2, 0)"
				+" WHEN a.type in (11,12)  THEN nvl(a.mda - a.txnamount * y.huabeirate, 0)"
				+" ELSE nvl(a.mda - a.txnamount * y.subrate, 0)"
				+" END AS profitone"
				
				+ " FROM check_micro_profittemplate y, check_unit_profitemplate uy WHERE uy.unno = '"+selectUnno+"'"
				+" AND uy.aptid = y.aptid AND "
				+ "uy.status != 0 and nvl(y.profitrule,'21') = nvl(a.maintainuid,'21') AND y.merchanttype = 5 AND y.mataintype != 'D' ) AS profit,"
				
				+" CASE "
				+" WHEN a.type = '1' and a.ifcard = '1' and a.txnday < 20191201 THEN '微信0.38'"
				+" WHEN a.type = '1' and a.ifcard = '2' and a.txnday < 20191201 THEN '微信0.45'"
				+" WHEN a.type = '1' and a.ifcard = '3' and a.txnday < 20191201 THEN '微信(老)'"
				+" WHEN a.type = '2' and a.txnday < 20191201 THEN '支付宝'"
				+" WHEN a.type = '3' and a.txnday < 20191201 THEN '二维码'"
				+" WHEN a.type in (1,2) and a.ifcard = '1' and a.txnday >= 20191201 THEN '扫码1000以上（终端0.38）'"
				+" WHEN a.type in (1,2) and a.ifcard = '2' and a.txnday >= 20191201 THEN '扫码1000以上（终端0.45）'"
				+" WHEN a.type in (1,2) and a.ifcard = '3' and a.txnday >= 20191201 THEN '扫码1000以下（终端0.38）'"
				+" WHEN a.type in (1,2) and a.ifcard = '4' and a.txnday >= 20191201 THEN '扫码1000以下（终端0.45）'"
				+" WHEN a.type = '3' and a.txnday < 20191201 THEN '银联二维码'"
				+"when a.type in (11,12) then '花呗'"
				+" ELSE '微信'"
				+" END AS transactionType,"
				
				+" a.mid, a.mda AS mda, a.unno, a.rname ,a.type,ifcard,a.maintainuid,  "
				+ "a.TXNAMOUNT AS txnamount FROM check_unitdealdetail_"+cptm.getTxnDay().substring(4, 6)+" a WHERE "
				+ "a.unno IN ("+childUnno+") AND a.txnstate = 0 AND a.ismpos = 2 ";
		
		String sql2="SELECT"
//				+" nvl(round(SUM(profit), 2), 0) AS profit "
				+" case "
				+" when nvl(round(SUM(profit), 2), 0) >=0 then nvl(round(SUM(profit), 2), 0)"
				+" else 0 end as profit"

				+", nvl(SUM(mda), 0) AS mda , nvl(SUM(txnamount), 0) AS txnamount , "
				+ "COUNT(mda) AS txncount FROM ( SELECT ( SELECT"
//				+" nvl(mda - TXNAMOUNT * y.subrate, 0)"
				
				+" CASE "
				+" WHEN a.type = '1' and a.ifcard = '1' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.creditbankrate, 0)"
				+" WHEN a.type = '1' and a.ifcard = '2' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate, 0)"
				+" WHEN a.type = '1' and a.ifcard = '3' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.subrate, 0)"
				+" WHEN a.type = '2' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate1, 0)"
				+" WHEN a.type = '3' and a.txnday < 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate2, 0)"
				
				+" WHEN a.type in (1,2) and a.ifcard = '1' and a.txnday >= 20191201 THEN nvl(a.mda - a.txnamount * y.creditbankrate, 0)"
				+" WHEN a.type in (1,2) and a.ifcard = '2' and a.txnday >= 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate, 0)"
				+" WHEN a.type in (1,2) and a.ifcard = '3' and a.txnday >= 20191201 THEN nvl(a.mda - a.txnamount * y.subrate, 0)"
				+" WHEN a.type in (1,2) and a.ifcard = '4' and a.txnday >= 20191201  THEN nvl(a.mda - a.txnamount * y.scanrate1, 0)"
				+" WHEN a.type = '3' and a.txnday >= 20191201 THEN nvl(a.mda - a.txnamount * y.scanrate2, 0)"
				+" WHEN a.type in (11,12) THEN nvl(a.mda - a.txnamount * y.huabeiRate, 0)"
				
				+" ELSE nvl(a.mda - a.txnamount * y.subrate, 0)"
				+" END AS profitone"
				
				+ " FROM check_micro_profittemplate y, check_unit_profitemplate uy WHERE uy.unno = '"+selectUnno+"'"
				+ " and nvl(y.profitrule,'21') = nvl(a.maintainuid,'21')"
				+ " AND uy.aptid = y.aptid AND "
				+ "uy.status != 0 AND y.merchanttype = 5 AND y.mataintype != 'D' ) AS profit, a.mid, a.mda AS mda, a.unno, a.rname , "
				+ "a.TXNAMOUNT AS txnamount FROM check_unitdealdetail_"+cptm.getTxnDay().substring(4, 6)+" a WHERE "
				+ "a.unno IN ("+childUnno+") AND a.txnstate = 0 AND a.ismpos = 2 ";
		
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.txnday>='"+cptm.getTxnDay()+"' and a.txnday<='"+cptm.getTxnDay1()+"'" ;
			sql2+=" and a.txnday>='"+cptm.getTxnDay()+"' and a.txnday<='"+cptm.getTxnDay1()+"'" ;
		}
		
		//添加活动类型
		if(cptm.getProfitRule()!=null && !"".equals(cptm.getProfitRule())){
			sql+=" and a.maintainuid = "+cptm.getProfitRule()+"";
			sql2+=" and a.maintainuid = "+cptm.getProfitRule()+"";
		}
//		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
//			sql+=" and a.unno = '"+cptm.getUnno()+"'" ;
//			sql2+=" and a.unno = '"+cptm.getUnno()+"'" ;
//		}
		sql+=" ) group by mid, unno, rname ,transactionType,maintainuid ";
		sql2+=")";
		List<Map<String,Object>> list= checkUnitProfitMicroDao.executeSql2(sql, null);
		List<Map<String,Object>> list2 = checkUnitProfitMicroDao.queryObjectsBySqlListMap2(sql2, null);
		list.add(0, list2.get(0));
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNNO");
		excelHeader.add("UN_NAME");
		excelHeader.add("MID");
		excelHeader.add("RNAME");
		excelHeader.add("TXNAMOUNT");
		excelHeader.add("TXNCOUNT");
		excelHeader.add("MDA");
		excelHeader.add("PROFIT");
		excelHeader.add("MAINTAINUID");
		excelHeader.add("TRANSACTIONTYPE");

		headMap.put("UNNO", "机构号");
		headMap.put("UN_NAME", "机构名称");
		headMap.put("MID", "商户编号");
		headMap.put("RNAME", "商户名称");
		headMap.put("TXNAMOUNT", "交易金额");
		headMap.put("TXNCOUNT", "交易笔数");
		headMap.put("MDA", "手续费");
		headMap.put("PROFIT", "分润金额");
		headMap.put("MAINTAINUID", "活动类型");
		headMap.put("TRANSACTIONTYPE", "交易类型");
		

		return ExcelUtil.export("收银台分润明细", list, excelHeader, headMap);
	}
	@Override
	public HSSFWorkbook exportUnitProfitMicroDetailData(CheckProfitTemplateMicroBean cptm, UserBean userBean) {
		
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptm.getUnno());
		String sql=" select nvl(round(sum(profit), 2), 0) profit ,  nvl(sum(mda),  0) mda ,nvl(sum(txnamount), 0) txnamount,  mid, unno, rname, count(mda), count(mda) txncount ," +
		" DECODE((case when feeamt>0 then 2 else 1 end),1,'小额商户',2,'大额商户','其他' ) as merchantType,DECODE(t,1,'理财',2,'0.72%秒到','非0.72%秒到') as settmethod from "+
		" (select decode(t.settmethod,100000,'100000',200000,'100000','000000') as settmethod, (select nvl(case when a.ifcard=3 then (mda - txnamount*nvl(y.cloudquickrate,0.003)) * y.profitpercent when a.cbrand='1' and t.settmethod='000000' then "+
		"  case when txnamount > y.endamount then  (mda - y.startamount) * y.profitpercent "+
		"        else (mda - txnamount * y.rulethreshold) * y.profitpercent   END  "+
		" else (mda - txnamount * y.creditbankrate) * y.profitpercent   end, 0) "+
		" from check_micro_profittemplate y ,check_unit_profitemplate uy where  uy.unno = '"+cptm.getUnno()+"' and uy.aptid=y.aptid and uy.status!=0 " +
		" and y.merchanttype =decode(t.settmethod,000000,1,case when a.mda/a.txnamount>0.007 then 2 else 3 end) "+
		" and y.settmethod = t.settmethod and y.mataintype != 'D') profit,  t.mid, a.mda mda, t.unno, t.rname, a.txnamount txnamount,t.feeamt, "+
		" decode(t.settmethod,000000,1,case when a.mda/a.txnamount>0.007 then 2 else 3 end) as t from bill_merchantinfo t, check_unitdealdetail a  where  t.mid = a.mid  "+
		" and t.unno in ("+childUnno+") and t.ism35=1  ";
		
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.txnday>='"+cptm.getTxnDay().replace("-", "")+"' and a.txnday<='"+cptm.getTxnDay1().replace("-", "")+"'" ;
		}else{
			sql+=" and a.txnday= to_char(sysdate-1,'yyyyMMdd')";
		}
		sql+=" ) group by mid, unno, rname ,settmethod ,feeamt,t";
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlListMap2(sql, null);
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNNO");
		excelHeader.add("MID");
		excelHeader.add("RNAME");
		excelHeader.add("MERCHANTTYPE");
		excelHeader.add("SETTMETHOD");
		excelHeader.add("TXNAMOUNT");
		excelHeader.add("MDA");
		excelHeader.add("PROFIT");
		
		
		headMap.put("UNNO", "机构号");
		headMap.put("MID", "商户编号");
		headMap.put("RNAME", "商户名称");
		headMap.put("MERCHANTTYPE", "商户类型");
		headMap.put("SETTMETHOD", "结算类型");
		headMap.put("TXNAMOUNT", "交易金额");
		headMap.put("MDA", "手续费金额");
		headMap.put("PROFIT", "分润金额");
		
		return ExcelUtil.export("代理分润明细", list, excelHeader, headMap);
	}

	@Override
	public DataGridBean queryUnitProfitTempList(CheckProfitTemplateMicroBean cptm, UserBean user) {
	    // @author:xuegangliu-20190215 分润没有剔除收银台数据
		String sql="select t.unno,t.unno as unno1,a.tempname,decode(a.mataintype, 'P', 'P', 'A') mataintypeA,c.un_name as unitName " +
					" from check_unit_profitemplate t,check_micro_profittemplate a ,sys_unit c " +
					" where t.aptid=a.aptid and t.unno=c.unno and t.STATUS!=0 " +
                    " and a.merchanttype in (1,2,3)";
		Map<String,Object> map = new HashMap<String,Object>();
		if(user.getUnitLvl()==0){
		}else{
			sql+=" and c.upper_unit=:UNNO ";
			map.put("UNNO", user.getUnNo());
		}
		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
			sql+=" and t.unno=:UNNO2";
			map.put("UNNO2", cptm.getUnno());
		}
		sql+=" group by t.unno,a.tempname ,c.un_name,decode(a.mataintype, 'P', 'P', 'A')";
		String sqlCount="select count(*) from ("+sql+")";
		Integer count= checkUnitProfitMicroDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list = checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, map, cptm.getPage(), cptm.getRows());
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}
	@Override
	public DataGridBean querySubTemplateList(CheckProfitTemplateMicroBean cptm, UserBean user) {
		String sql="select t.unno,a.mataintype,a.tempname,c.un_name as unitName " +
					" from check_unit_profitemplate t,check_micro_profittemplate a ,sys_unit c " +
					" where t.aptid=a.aptid and t.unno=c.unno and t.STATUS!=0 and a.merchanttype=4 ";
		Map<String,Object> map = new HashMap<String,Object>();
		if(user.getUnitLvl()==0){
		}else{
			sql+=" and c.upper_unit=:UNNO ";
			map.put("UNNO", user.getUnNo());
		}
		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
			sql+=" and t.unno=:UNNO2";
			map.put("UNNO2", cptm.getUnno());
		}
		String sqlCount="select count(*) from ("+sql+")";
		Integer count= checkUnitProfitMicroDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list = checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, map, cptm.getPage(), cptm.getRows());
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}
	@Override
	public DataGridBean querySytTemplateList(CheckProfitTemplateMicroBean cptm, UserBean user) {
		String sql="select t.unno,a.tempname,decode(a.mataintype, 'P', 'P', 'A') mataintype,c.un_name as unitName " +
					" from check_unit_profitemplate t,check_micro_profittemplate a ,sys_unit c " +
					" where t.aptid=a.aptid and t.unno=c.unno and t.STATUS!=0 and a.merchanttype=5 ";
		Map<String,Object> map = new HashMap<String,Object>();
		if(user.getUnitLvl()==0){
		}else{
			sql+=" and c.upper_unit=:UNNO ";
			map.put("UNNO", user.getUnNo());
		}
		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
			sql+=" and t.unno=:UNNO2";
			map.put("UNNO2", cptm.getUnno());
		}
		sql +=" group by t.unno,a.tempname,c.un_name,decode(a.mataintype, 'P', 'P', 'A')";
		String sqlCount="select count(*) from ("+sql+")";
		Integer count= checkUnitProfitMicroDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list = checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, map, cptm.getPage(), cptm.getRows());
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}
	/**
	 * 删除分配分润模版
	 */
	public void DeleteProfitMicroUnno(String unno,String settMethod) {
		//String sql1="update CHECK_UNIT_PROFITEMPLATE set STATUS=0 where unno ='"+unno+"' ";
		String sql ="update CHECK_UNIT_PROFITEMPLATE set STATUS=0 where aptid in(" +
					" select p.aptid  from CHECK_MICRO_PROFITTEMPLATE p, check_unit_profitemplate p1" +
					" where p.aptid=p1.aptid and p1.unno='"+unno+"' and p1.status!=0 and p.merchanttype in (1,2,3)) and unno='"+unno+"'";
					//" and p1.status!=0  and p.settmethod='"+settMethod+"') and unno='"+unno+"'";
		String sqlLog ="update check_micro_profittemplate_log set ENDDATE=sysdate " +
				" where merchanttype in (1,2,3) and unno='"+unno+"' and ENDDATE is null";
		checkUnitProfitMicroLogDao.executeUpdate(sqlLog);
		checkUnitProfitMicroDao.executeUpdate(sql);
	}

    @Override
    public void addMposTempla(CheckProfitTemplateMicroBean cptm, UserBean user) {
        String tempName = cptm.getTempName();
        Map<String, String> map = new HashMap<String, String>();
        String str = cptm.getTempname();
        JSONArray json1 = JSONArray.parseArray(str);
        for(int i = 0;i<json1.size();i++){
//            String subRate = JSONObject.parseObject(json1.getString(i)).getString("subRate");
//            String scanRate = JSONObject.parseObject(json1.getString(i)).getString("scanRate");
//            String cashAmt = JSONObject.parseObject(json1.getString(i)).getString("cashAmt");
//            String cashAmt1 = JSONObject.parseObject(json1.getString(i)).getString("cashAmt1");
        	//微信1000以上0.38费率
        	String creditBankRate = JSONObject.parseObject(json1.getString(i)).getString("creditBankRate");
        	//微信1000以上0.38转账费
        	String cashRate = JSONObject.parseObject(json1.getString(i)).getString("cashRate");
        	//微信1000以上0.45费率
        	String ruleThreshold = JSONObject.parseObject(json1.getString(i)).getString("ruleThreshold");
        	//微信1000以上0.45转账费
        	String startAmount = JSONObject.parseObject(json1.getString(i)).getString("startAmount");
        	//微信1000以下费率
        	String scanRate = JSONObject.parseObject(json1.getString(i)).getString("scanRate");
        	//微信1000以下转账费
        	String cashAmt1 = JSONObject.parseObject(json1.getString(i)).getString("cashAmt1");
        	//支付宝上费率
        	String scanRate1 = JSONObject.parseObject(json1.getString(i)).getString("scanRate1");
        	//支付宝转账费
        	String profitPercent1 = JSONObject.parseObject(json1.getString(i)).getString("profitPercent1");
        	//二维码费率
        	String scanRate2 = JSONObject.parseObject(json1.getString(i)).getString("scanRate2");
        	//二维码转账费
        	String cashAmt2 = JSONObject.parseObject(json1.getString(i)).getString("cashAmt2");
        	//秒到刷卡费率
        	String subRate = JSONObject.parseObject(json1.getString(i)).getString("subRate");
        	//秒到刷卡转账费
        	String cashAmt = JSONObject.parseObject(json1.getString(i)).getString("cashAmt");
        	//云闪付费率
        	String cloudQuickRate = JSONObject.parseObject(json1.getString(i)).getString("cloudQuickRate");
           
        	//花呗费率
        	String huabeiRate = JSONObject.parseObject(json1.getString(i)).getString("huabeiRate");
        	//花呗转账费
        	String huabeiFee = JSONObject.parseObject(json1.getString(i)).getString("huabeiFee");
        	
        	String profitRule = JSONObject.parseObject(json1.getString(i)).getString("profitRule");
        	
            if(map.containsKey(profitRule)){
                continue;
            }
            map.put(profitRule, "");
            CheckProfitTemplateMicroBean bean = new CheckProfitTemplateMicroBean();
//            bean.setSubRate(Double.parseDouble(subRate));
//            bean.setScanRate(Double.parseDouble(scanRate));
//            bean.setCashAmt(Double.parseDouble(cashAmt));
//            bean.setCashAmt1(Double.parseDouble(cashAmt1));
            
            bean.setCreditBankRate(Double.parseDouble(creditBankRate));
            bean.setCashRate(Double.parseDouble(cashRate));
            bean.setRuleThreshold(Double.parseDouble(ruleThreshold));
            bean.setStartAmount(Double.parseDouble(startAmount));
            bean.setScanRate(Double.parseDouble(scanRate));
            bean.setCashAmt1(Double.parseDouble(cashAmt1));
            bean.setScanRate1(Double.parseDouble(scanRate1));
            bean.setProfitPercent1(Double.parseDouble(profitPercent1));
            bean.setScanRate2(Double.parseDouble(scanRate2));
            bean.setCashAmt2(Double.parseDouble(cashAmt2));
            bean.setSubRate(Double.parseDouble(subRate));
            bean.setCashAmt(Double.parseDouble(cashAmt));
            bean.setCloudQuickRate(Double.parseDouble(cloudQuickRate));
            
            if(huabeiRate!=null&&!huabeiRate.equals("")){
            	bean.setHuabeiRate(Double.parseDouble(huabeiRate));
            }
            if(huabeiFee!=null&&!huabeiFee.equals("")){
            	bean.setHuabeiFee(Double.parseDouble(huabeiFee));
            }
            
            bean.setProfitRule(Integer.parseInt(profitRule));
            bean.setTempName(tempName);
            //改为调用新的方法，主要是增加对支付宝/微信1000上下的区分字段20190910
            saveMposTempla(bean,user);//这个将其保存在本月生效表中
            //saveMposTemplaIncrease(bean,user);这个添加是修改时保存在下月表中
        }
    }

    //mpos活动模板添加，增加了微信/支付宝1000等的区分20190910--ztt
    public void saveMposTemplaIncrease(CheckProfitTemplateMicroBean cptm, UserBean user) {
    	 CheckProfitTemplateMicroWModel MicroModel = new CheckProfitTemplateMicroWModel();
         MicroModel.setUnno(user.getUnNo());
         MicroModel.setMerchantType(6);//表示6--MPOS活动模板
         MicroModel.setProfitRule(cptm.getProfitRule());//表示20--MPOS活动20
        
         MicroModel.setCreditBankRate(Double.parseDouble(new BigDecimal(cptm.getCreditBankRate()+"").divide(new BigDecimal("100"))+""));
         MicroModel.setCashRate(cptm.getCashRate());
         MicroModel.setRuleThreshold(Double.parseDouble(new BigDecimal(cptm.getRuleThreshold()+"").divide(new BigDecimal("100"))+""));
         MicroModel.setStartAmount(cptm.getStartAmount());
         MicroModel.setScanRate(Double.parseDouble(new BigDecimal(cptm.getScanRate()+"").divide(new BigDecimal("100"))+""));
         MicroModel.setCashAmt1(cptm.getCashAmt1());
         MicroModel.setScanRate1(Double.parseDouble(new BigDecimal(cptm.getScanRate1()+"").divide(new BigDecimal("100"))+""));
         MicroModel.setProfitPercent1(cptm.getProfitPercent1());
         MicroModel.setScanRate2(Double.parseDouble(new BigDecimal(cptm.getScanRate2()+"").divide(new BigDecimal("100"))+""));
         MicroModel.setCashAmt2(cptm.getCashAmt2());
         MicroModel.setSubRate(Double.parseDouble(new BigDecimal(cptm.getSubRate()+"").divide(new BigDecimal("100"))+""));
         MicroModel.setCashAmt(cptm.getCashAmt());
         MicroModel.setCloudQuickRate(Double.parseDouble(new BigDecimal(cptm.getCloudQuickRate()+"").divide(new BigDecimal("100"))+""));
        
         MicroModel.setAptId(cptm.getAptId());
         MicroModel.setTempName(cptm.getTempName());
         MicroModel.setMatainDate(new Date());
         
         MicroModel.setHuabeiRate(cptm.getHuabeiRate());
         MicroModel.setHuabeiFee(cptm.getHuabeiFee());
        // MicroModel.setMatainType("A");
         MicroModel.setMatainType(cptm.getMatainType());
         checkUnitProfitMicroWDao.saveObject(MicroModel);
    }
    //mpos模板存储数据库方法 @author yq
    public void saveMposTempla(CheckProfitTemplateMicroBean cptm, UserBean user){
        CheckProfitTemplateMicroModel MicroModel = new CheckProfitTemplateMicroModel();
        MicroModel.setUnno(user.getUnNo());
        MicroModel.setMerchantType(6);//表示6--MPOS活动模板
        MicroModel.setProfitRule(cptm.getProfitRule());//表示20--MPOS活动20
       
        MicroModel.setCreditBankRate(Double.parseDouble(new BigDecimal(cptm.getCreditBankRate()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setCashRate(cptm.getCashRate());
        MicroModel.setRuleThreshold(Double.parseDouble(new BigDecimal(cptm.getRuleThreshold()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setStartAmount(cptm.getStartAmount());
        MicroModel.setScanRate(Double.parseDouble(new BigDecimal(cptm.getScanRate()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setCashAmt1(cptm.getCashAmt1());
        MicroModel.setScanRate1(Double.parseDouble(new BigDecimal(cptm.getScanRate1()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setProfitPercent1(cptm.getProfitPercent1());
        MicroModel.setScanRate2(Double.parseDouble(new BigDecimal(cptm.getScanRate2()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setCashAmt2(cptm.getCashAmt2());
        MicroModel.setSubRate(Double.parseDouble(new BigDecimal(cptm.getSubRate()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setCashAmt(cptm.getCashAmt());
        MicroModel.setCloudQuickRate(Double.parseDouble(new BigDecimal(cptm.getCloudQuickRate()+"").divide(new BigDecimal("100"))+""));
        String subtype = querySubTypeByPlusProfitRule(cptm.getProfitRule());
        //是否花呗活动
        if("2".equals(subtype)) {
        	if(cptm.getHuabeiRate()!=null&&!cptm.getHuabeiRate().equals("")){
        		MicroModel.setHuabeiRate(Double.parseDouble(new BigDecimal(cptm.getHuabeiRate()+"").divide(new BigDecimal("100"))+""));
        		MicroModel.setHuabeiFee(cptm.getHuabeiFee());
        	}
        }
        
        MicroModel.setTempName(cptm.getTempName());
        MicroModel.setMatainDate(new Date());
        MicroModel.setMatainType("A");
        checkUnitProfitMicroDao.saveObject(MicroModel);
    }

    @Override
    public DataGridBean queryMposTemplate(CheckProfitTemplateMicroBean cptm, UserBean userBean) {
        String sql="select  p.TEMPNAME,p.MATAINTYPE from CHECK_MICRO_PROFITTEMPLATE p where 1=1 and p.merchanttype = 6 ";
        UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
        //为总公司
        if("110000".equals(userBean.getUnNo())){
        }
        //为总公司并且是部门---查询全部
        else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
            UnitModel parent = unitModel.getParent();
            if("110000".equals(parent.getUnNo())){
            }
        }else{
            sql+=" and p.unno ='"+userBean.getUnNo()+"' " ;
        }
        if(cptm!=null&&cptm.getTempName()!=null&&!cptm.getTempName().equals("")){
            sql+="and  p.TEMPNAME='"+cptm.getTempName()+"' ";
        }
        sql+=" and MatainType in ('A','M','P') group by p.TEMPNAME,p.MATAINTYPE  ";
        String count="select count(*) from ("+sql+")";
        List<Map<String,String>> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql,null,cptm.getPage(),cptm.getRows());
        BigDecimal counts = checkUnitProfitMicroDao.querysqlCounts(count,null);
        DataGridBean dgd = new DataGridBean();
        dgd.setTotal(counts.intValue());//查询数据库总条数
        dgd.setRows(list);
        return dgd;
    }

    @Override
    public List<Map<String, Object>> queryupdateMposProfitmicro(CheckProfitTemplateMicroBean cptm, UserBean user) throws Exception {
    	List<Map<String, Object>> lst = new ArrayList<Map<String,Object>>();
    	//判断是否是当月1号---20190925
    	if(DateTools.isFirstDay()) {
    		String sql="select t.* from CHECK_MICRO_PROFITTEMPLATE_W t where t.TEMPNAME='"+cptm.getTempname()+"' and t.MatainType in ('A','M','P') and t.merchanttype = 6"+
    				"and t.mataindate < trunc(sysdate,'mm')"+
    				"and t.mataindate >= add_months(trunc(sysdate,'mm'),-1)";
    		String isHaveSql = "select count(*) from ("+sql+")";
    		BigDecimal counts = checkUnitProfitMicroWDao.querysqlCounts(isHaveSql, null);
    		//判断_w表中是否存在值，存在本月成本显示_w表中的数据。不存在显示显示实时表中成本---20190925
    		if(counts.intValue() > 0) {
    			UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
    	        //为总公司
    	        if("110000".equals(user.getUnNo())){
    	        }
    	        //为总公司并且是部门---查询全部
    	        else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
    	            UnitModel parent = unitModel.getParent();
    	            if("110000".equals(parent.getUnNo())){
    	            }
    	        }else{
    	            sql+=" and unno ='"+user.getUnNo()+"'";
    	        }
    	        List<CheckProfitTemplateMicroWModel> list=checkUnitProfitMicroWDao.queryObjectsBySqlList(sql, null,CheckProfitTemplateMicroWModel.class );
    	        for(int i=0;i<list.size();i++){
                    CheckProfitTemplateMicroWModel cc =list.get(i);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("profitRule",cc.getProfitRule());
                    
                    map.put("creditBankRate", null==cc.getCreditBankRate()?"":new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100")));
                    map.put("cashRate", null==cc.getCashRate()?"":cc.getCashRate());
                    map.put("ruleThreshold", null==cc.getRuleThreshold()?"":new BigDecimal(cc.getRuleThreshold()+"").multiply(new BigDecimal("100")));
                    map.put("startAmount", null==cc.getStartAmount()?"":cc.getStartAmount());
                    map.put("scanRate", null==cc.getScanRate()?"":new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
                    map.put("cashAmt1", null==cc.getCashAmt1()?"":cc.getCashAmt1());
                    map.put("scanRate1", null==cc.getScanRate1()?"":new BigDecimal(cc.getScanRate1()+"").multiply(new BigDecimal("100")));
                    map.put("profitPercent1", null==cc.getProfitPercent1()?"":cc.getProfitPercent1());
                    map.put("scanRate2", null==cc.getScanRate2()?"":new BigDecimal(cc.getScanRate2()+"").multiply(new BigDecimal("100")));
                    map.put("cashAmt2", null==cc.getCashAmt2()?"":cc.getCashAmt2());
                    map.put("subRate", null==cc.getSubRate()?"":new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
                    map.put("cashAmt", null==cc.getCashAmt()?"":cc.getCashAmt());
                    map.put("cloudQuickRate", null==cc.getCloudQuickRate()?"":new BigDecimal(cc.getCloudQuickRate()+"").multiply(new BigDecimal("100")));
                    
                    map.put("huabeiRate", null==cc.getHuabeiRate()?"":new BigDecimal(cc.getHuabeiRate()+"").multiply(new BigDecimal("100")));
                    map.put("huabeiFee", null==cc.getHuabeiFee()?"":cc.getHuabeiFee());
                    String ishuabei = querySubTypeByPlusProfitRule(cc.getProfitRule());
                    if(ishuabei!=null&&"2".equals(ishuabei)) {
                    	map.put("ishuabei", 1);
                    }else {
                    	map.put("ishuabei", 2);
                    }
                    
                    lst.add(map);
                }
    		}else {
    			querycurrentMonthMposProfit(cptm,user,lst);
    		}
        }else {
        	querycurrentMonthMposProfit(cptm,user,lst);
        }
        return lst;
    }
    
    //mpos本月模板展示（非1号或1号但上月_w表中没有修改记录）
    public List querycurrentMonthMposProfit(CheckProfitTemplateMicroBean cptm, UserBean user,List<Map<String, Object>> lst) {
    	String sql="select * from CHECK_MICRO_PROFITTEMPLATE where TEMPNAME='"+cptm.getTempname()+"' and MatainType in ('A','M','P') and merchanttype = 6";
        UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
        //为总公司
        if("110000".equals(user.getUnNo())){
        }
        //为总公司并且是部门---查询全部
        else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
            UnitModel parent = unitModel.getParent();
            if("110000".equals(parent.getUnNo())){
            }
        }else{
            sql+=" and unno ='"+user.getUnNo()+"'";
        }
        List<CheckProfitTemplateMicroModel> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql, null,CheckProfitTemplateMicroModel.class );
        for(int i=0;i<list.size();i++){
            CheckProfitTemplateMicroModel cc =list.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("profitRule",cc.getProfitRule());
            
            map.put("creditBankRate", null==cc.getCreditBankRate()?"":new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100")));
            map.put("cashRate", null==cc.getCashRate()?"":cc.getCashRate());
            map.put("ruleThreshold", null==cc.getRuleThreshold()?"":new BigDecimal(cc.getRuleThreshold()+"").multiply(new BigDecimal("100")));
            map.put("startAmount", null==cc.getStartAmount()?"":cc.getStartAmount());
            map.put("scanRate", null==cc.getScanRate()?"":new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
            map.put("cashAmt1", null==cc.getCashAmt1()?"":cc.getCashAmt1());
            map.put("scanRate1", null==cc.getScanRate1()?"":new BigDecimal(cc.getScanRate1()+"").multiply(new BigDecimal("100")));
            map.put("profitPercent1", null==cc.getProfitPercent1()?"":cc.getProfitPercent1());
            map.put("scanRate2", null==cc.getScanRate2()?"":new BigDecimal(cc.getScanRate2()+"").multiply(new BigDecimal("100")));
            map.put("cashAmt2", null==cc.getCashAmt2()?"":cc.getCashAmt2());
            map.put("subRate", null==cc.getSubRate()?"":new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
            map.put("cashAmt", null==cc.getCashAmt()?"":cc.getCashAmt());
            map.put("cloudQuickRate", null==cc.getCloudQuickRate()?"":new BigDecimal(cc.getCloudQuickRate()+"").multiply(new BigDecimal("100")));
            
//          map.put("subRate", null==cc.getSubRate()?0:new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
//			map.put("cashAmt", null==cc.getCashAmt()?0:cc.getCashAmt());
//			map.put("scanRate", null==cc.getScanRate()?0:new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
//			map.put("cashAmt1", null==cc.getCashAmt1()?0:cc.getCashAmt1());
           
            map.put("huabeiRate", null==cc.getHuabeiRate()?"":new BigDecimal(cc.getHuabeiRate()+"").multiply(new BigDecimal("100")));
            map.put("huabeiFee", null==cc.getHuabeiFee()?"":cc.getHuabeiFee());
            String ishuabei = querySubTypeByPlusProfitRule(cc.getProfitRule());
            if(ishuabei!=null&&"2".equals(ishuabei)) {
            	map.put("ishuabei", 1);
            }else {
            	map.put("ishuabei", 2);
            }
            lst.add(map);
        }
        return lst;
    }
    
	/* mops下月模板查询 */
    @Override
    public List<Map<String, Object>> queryupdateMposProfitmicroNextMonth(CheckProfitTemplateMicroBean cptm, UserBean user) throws Exception {
    	List<Map<String, Object>> lst = new ArrayList<Map<String,Object>>();
    	UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
        Map params = new HashMap();
		params.put("TEMPNAME",cptm.getTempname());
    	//判断是否是当月1号---20190925
    	if(DateTools.isFirstDay()) {
    		String sql="select t.* from CHECK_MICRO_PROFITTEMPLATE_W t where t.TEMPNAME='"+cptm.getTempname()+"' and t.MatainType in ('A','M','P') and t.merchanttype = 6"+
    				"and t.mataindate <= trunc(sysdate,'mm')"+
    				"and t.mataindate >= add_months(trunc(sysdate,'mm'),-1)";
    		String isHaveSql = "select count(*) from ("+sql+")";
    		BigDecimal counts = checkUnitProfitMicroWDao.querysqlCounts(isHaveSql, null);
    		//判断_w表中是否有上个月设立的次月模板（包括本月1号设置的），都将其显示在对应的次月模板上----20190925
    		if(counts.intValue() > 0) {
    			//为总公司
    	        if("110000".equals(user.getUnNo())){
    	        }
    	        //为总公司并且是部门---查询全部
    	        else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
    	            UnitModel parent = unitModel.getParent();
    	            if("110000".equals(parent.getUnNo())){
    	            }
    	        }else{
    	            sql+=" and unno ='"+user.getUnNo()+"'";
    	        }
    	        List<CheckProfitTemplateMicroWModel> list=checkUnitProfitMicroWDao.queryObjectsBySqlList(sql, null,CheckProfitTemplateMicroWModel.class );
    	        for(int i=0;i<list.size();i++){
    	            CheckProfitTemplateMicroWModel cc =list.get(i);
    	            Map<String, Object> map = new HashMap<String, Object>();
    	            map.put("profitRule",cc.getProfitRule());
    	            
    	            map.put("creditBankRate", null==cc.getCreditBankRate()?"":new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100")));
    	            map.put("cashRate", null==cc.getCashRate()?"":cc.getCashRate());
    	            map.put("ruleThreshold", null==cc.getRuleThreshold()?"":new BigDecimal(cc.getRuleThreshold()+"").multiply(new BigDecimal("100")));
    	            map.put("startAmount", null==cc.getStartAmount()?"":cc.getStartAmount());
    	            map.put("scanRate", null==cc.getScanRate()?"":new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
    	            map.put("cashAmt1", null==cc.getCashAmt1()?"":cc.getCashAmt1());
    	            map.put("scanRate1", null==cc.getScanRate1()?"":new BigDecimal(cc.getScanRate1()+"").multiply(new BigDecimal("100")));
    	            map.put("profitPercent1", null==cc.getProfitPercent1()?"":cc.getProfitPercent1());
    	            map.put("scanRate2", null==cc.getScanRate2()?"":new BigDecimal(cc.getScanRate2()+"").multiply(new BigDecimal("100")));
    	            map.put("cashAmt2", null==cc.getCashAmt2()?"":cc.getCashAmt2());
    	            map.put("subRate", null==cc.getSubRate()?"":new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
    	            map.put("cashAmt", null==cc.getCashAmt()?"":cc.getCashAmt());
    	            map.put("cloudQuickRate", null==cc.getCloudQuickRate()?"":new BigDecimal(cc.getCloudQuickRate()+"").multiply(new BigDecimal("100")));
    	            
    	            map.put("aptId", cc.getAptId());
    	            map.put("matainType", cc.getMatainType());
//    	            map.put("subRate", null==cc.getSubRate()?0:new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
//    				map.put("cashAmt", null==cc.getCashAmt()?0:cc.getCashAmt());
//    				map.put("scanRate", null==cc.getScanRate()?0:new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
//    				map.put("cashAmt1", null==cc.getCashAmt1()?0:cc.getCashAmt1());
    	           
    	            
    	            map.put("huabeiRate", null==cc.getHuabeiRate()?"":new BigDecimal(cc.getHuabeiRate()+"").multiply(new BigDecimal("100")));
    	            map.put("huabeiFee", null==cc.getHuabeiFee()?"":cc.getHuabeiFee());
    	            String ishuabei = querySubTypeByPlusProfitRule(cc.getProfitRule());
    	            if(ishuabei!=null&&"2".equals(ishuabei)) {
    	            	map.put("ishuabei", 1);
    	            }else {
    	            	map.put("ishuabei", 2);
    	            }
    	            lst.add(map);
    	        }
    	        return lst;
    		}else {
    			//1号没有展示实时表中的
    			return queryNowMposProfit(cptm,user,lst,unitModel);
    		}
    	}else {
    		String sql="select * from CHECK_MICRO_PROFITTEMPLATE_W where TEMPNAME=:TEMPNAME and MatainType in ('A','M','P') and merchanttype = 6 "+
    			"and mataindate>=trunc(sysdate,'mm')";
    		String isHaveSql = "select count(*) from ("+sql+")";
    		BigDecimal counts = checkUnitProfitMicroWDao.querysqlCounts(isHaveSql, params);
    		if(counts.intValue() > 0) {
    			//不是1号且_w表有值    _w展示
    			String sqllist="select * from CHECK_MICRO_PROFITTEMPLATE_W where TEMPNAME='"+cptm.getTempname()+"'  and MatainType in ('A','M','P') and merchanttype = 6 and mataindate>=trunc(sysdate,'mm')";
    			//为总公司
    	        if("110000".equals(user.getUnNo())){
    	        }
    	        //为总公司并且是部门---查询全部
    	        else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
    	            UnitModel parent = unitModel.getParent();
    	            if("110000".equals(parent.getUnNo())){
    	            }
    	        }else{
    	        	sqllist+=" and unno ='"+user.getUnNo()+"'";
    	        }
    			List<CheckProfitTemplateMicroWModel> list=checkUnitProfitMicroWDao.queryObjectsBySqlList(sqllist, null,CheckProfitTemplateMicroWModel.class );
    			for(int i=0;i<list.size();i++){
    	            CheckProfitTemplateMicroWModel cc =list.get(i);
    	            Map<String, Object> map = new HashMap<String, Object>();
    	            map.put("profitRule",cc.getProfitRule());
    	            
    	            map.put("creditBankRate", null==cc.getCreditBankRate()?"":new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100")));
    	            map.put("cashRate", null==cc.getCashRate()?"":cc.getCashRate());
    	            map.put("ruleThreshold", null==cc.getRuleThreshold()?"":new BigDecimal(cc.getRuleThreshold()+"").multiply(new BigDecimal("100")));
    	            map.put("startAmount", null==cc.getStartAmount()?"":cc.getStartAmount());
    	            map.put("scanRate", null==cc.getScanRate()?"":new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
    	            map.put("cashAmt1", null==cc.getCashAmt1()?"":cc.getCashAmt1());
    	            map.put("scanRate1", null==cc.getScanRate1()?"":new BigDecimal(cc.getScanRate1()+"").multiply(new BigDecimal("100")));
    	            map.put("profitPercent1", null==cc.getProfitPercent1()?"":cc.getProfitPercent1());
    	            map.put("scanRate2", null==cc.getScanRate2()?"":new BigDecimal(cc.getScanRate2()+"").multiply(new BigDecimal("100")));
    	            map.put("cashAmt2", null==cc.getCashAmt2()?"":cc.getCashAmt2());
    	            map.put("subRate", null==cc.getSubRate()?"":new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
    	            map.put("cashAmt", null==cc.getCashAmt()?"":cc.getCashAmt());
    	            map.put("cloudQuickRate", null==cc.getCloudQuickRate()?"":new BigDecimal(cc.getCloudQuickRate()+"").multiply(new BigDecimal("100")));
    	            
    	            map.put("aptId", cc.getAptId());
    	            map.put("matainType", cc.getMatainType());
//    	            map.put("subRate", null==cc.getSubRate()?0:new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
//    				map.put("cashAmt", null==cc.getCashAmt()?0:cc.getCashAmt());
//    				map.put("scanRate", null==cc.getScanRate()?0:new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
//    				map.put("cashAmt1", null==cc.getCashAmt1()?0:cc.getCashAmt1());
    	           
    	            map.put("huabeiRate", null==cc.getHuabeiRate()?"":new BigDecimal(cc.getHuabeiRate()+"").multiply(new BigDecimal("100")));
    	            map.put("huabeiFee", null==cc.getHuabeiFee()?"":cc.getHuabeiFee());
    	            String ishuabei = querySubTypeByPlusProfitRule(cc.getProfitRule());
    	            if(ishuabei!=null&&"2".equals(ishuabei)) {
    	            	map.put("ishuabei", 1);
    	            }else {
    	            	map.put("ishuabei", 2);
    	            }
    	            lst.add(map);
    	        }
    	        return lst;
    		}else {
    			//不是1号且_w表无值    实时表展示
    			return queryNowMposProfit(cptm,user,lst,unitModel);
    		}
    	}
    }
    
    
    //查询实时表中对应模板数据：1号且_w表中没有数据、非1号且_w表中没有数据
    public List queryNowMposProfit(CheckProfitTemplateMicroBean cptm, UserBean user,List<Map<String, Object>> lst,UnitModel unitModel) {
    	String currentsql="select * from CHECK_MICRO_PROFITTEMPLATE where TEMPNAME='"+cptm.getTempname()+"' and MatainType in ('A','M','P') and merchanttype = 6";
		//为总公司
        if("110000".equals(user.getUnNo())){
        }
        //为总公司并且是部门---查询全部
        else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
            UnitModel parent = unitModel.getParent();
            if("110000".equals(parent.getUnNo())){
            }
        }else{
        	currentsql+=" and unno ='"+user.getUnNo()+"'";
        }
		List<CheckProfitTemplateMicroModel> list=checkUnitProfitMicroDao.queryObjectsBySqlList(currentsql, null,CheckProfitTemplateMicroModel.class );
		for(int i=0;i<list.size();i++){
            CheckProfitTemplateMicroModel cc =list.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("profitRule",cc.getProfitRule());
            
            map.put("creditBankRate", null==cc.getCreditBankRate()?"":new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100")));
            map.put("cashRate", null==cc.getCashRate()?"":cc.getCashRate());
            map.put("ruleThreshold", null==cc.getRuleThreshold()?"":new BigDecimal(cc.getRuleThreshold()+"").multiply(new BigDecimal("100")));
            map.put("startAmount", null==cc.getStartAmount()?"":cc.getStartAmount());
            map.put("scanRate", null==cc.getScanRate()?"":new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
            map.put("cashAmt1", null==cc.getCashAmt1()?"":cc.getCashAmt1());
            map.put("scanRate1", null==cc.getScanRate1()?"":new BigDecimal(cc.getScanRate1()+"").multiply(new BigDecimal("100")));
            map.put("profitPercent1", null==cc.getProfitPercent1()?"":cc.getProfitPercent1());
            map.put("scanRate2", null==cc.getScanRate2()?"":new BigDecimal(cc.getScanRate2()+"").multiply(new BigDecimal("100")));
            map.put("cashAmt2", null==cc.getCashAmt2()?"":cc.getCashAmt2());
            map.put("subRate", null==cc.getSubRate()?"":new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
            map.put("cashAmt", null==cc.getCashAmt()?"":cc.getCashAmt());
            map.put("cloudQuickRate", null==cc.getCloudQuickRate()?"":new BigDecimal(cc.getCloudQuickRate()+"").multiply(new BigDecimal("100")));
            
            map.put("aptId", cc.getAptId());
            map.put("matainType", cc.getMatainType());
//          map.put("subRate", null==cc.getSubRate()?0:new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
//			map.put("cashAmt", null==cc.getCashAmt()?0:cc.getCashAmt());
//			map.put("scanRate", null==cc.getScanRate()?0:new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
//			map.put("cashAmt1", null==cc.getCashAmt1()?0:cc.getCashAmt1());
            
            map.put("huabeiRate", null==cc.getHuabeiRate()?"":new BigDecimal(cc.getHuabeiRate()+"").multiply(new BigDecimal("100")));
            map.put("huabeiFee", null==cc.getHuabeiFee()?"":cc.getHuabeiFee());
            String ishuabei = querySubTypeByPlusProfitRule(cc.getProfitRule());
            if(ishuabei!=null&&"2".equals(ishuabei)) {
            	map.put("ishuabei", 1);
            }else {
            	map.put("ishuabei", 2);
            }
            lst.add(map);
        }
		return lst;
    }
    
    
    

    @Override
    public void updateMposTemplate(CheckProfitTemplateMicroBean cpt, UserBean user) throws Exception {
        String tempName = cpt.getTempName();
        
        //String querysql="from CheckProfitTemplateMicroWModel where tempname=? and MatainType in ('A','M') and merchanttype=5 and matainDate>=trunc(sysdate,'mm')";		
		//CheckProfitTemplateMicroWModel model = checkUnitProfitMicroWDao.queryObjectByHql(querysql, new Object[]{cpt.getTempName()});
        
        String str = cpt.getTempname();
        JSONArray json = JSONArray.parseArray(str);
        for(int i=0;i<json.size();i++){
        	//微信1000以上0.38费率
        	String creditBankRate = JSONObject.parseObject(json.getString(i)).getString("creditBankRate");
        	//微信1000以上0.38转账费
        	String cashRate = JSONObject.parseObject(json.getString(i)).getString("cashRate");
        	//微信1000以上0.45费率
        	String ruleThreshold = JSONObject.parseObject(json.getString(i)).getString("ruleThreshold");
        	//微信1000以上0.45转账费
        	String startAmount = JSONObject.parseObject(json.getString(i)).getString("startAmount");
        	//微信1000以下费率
        	String scanRate = JSONObject.parseObject(json.getString(i)).getString("scanRate");
        	//微信1000以下转账费
        	String cashAmt1 = JSONObject.parseObject(json.getString(i)).getString("cashAmt1");
        	//支付宝费率
        	String scanRate1 = JSONObject.parseObject(json.getString(i)).getString("scanRate1");
        	//支付宝转账费
        	String profitPercent1 = JSONObject.parseObject(json.getString(i)).getString("profitPercent1");
        	//二维码费率
        	String scanRate2 = JSONObject.parseObject(json.getString(i)).getString("scanRate2");
        	//二维码转账费
        	String cashAmt2 = JSONObject.parseObject(json.getString(i)).getString("cashAmt2");
        	//秒到刷卡费率
        	String subRate = JSONObject.parseObject(json.getString(i)).getString("subRate");
        	//秒到刷卡转账费
        	String cashAmt = JSONObject.parseObject(json.getString(i)).getString("cashAmt");
        	//云闪付费率
        	String cloudQuickRate = JSONObject.parseObject(json.getString(i)).getString("cloudQuickRate");
        	//aptId
        	String aptId = JSONObject.parseObject(json.getString(i)).getString("aptId");
            //获得查询出数据本身的matainType(用于保证插入的数据类型与之前保持一致)
        	String matainType = JSONObject.parseObject(json.getString(i)).getString("matainType");
        	
        	//活动类型
        	String profitRule = JSONObject.parseObject(json.getString(i)).getString("profitRule");
        	String huabeiRate = null;
        	String huabeiFee = null;
        	//判断是否是花呗活动
        	String subtype = querySubTypeByPlusProfitRule(Integer.parseInt(profitRule));
        	if(subtype!=null) {
        		//花呗费率
        		huabeiRate = JSONObject.parseObject(json.getString(i)).getString("huabeiRate");
        		//花呗转账费
        		huabeiFee = JSONObject.parseObject(json.getString(i)).getString("huabeiFee");
        	}
        	
        	String sql="select * from CHECK_MICRO_PROFITTEMPLATE_W where TEMPNAME='"+tempName.trim()+"' and MatainType in ('A','M','P') and merchanttype = 6 and matainDate>=trunc(sysdate,'mm') and profitRule = '"+JSONObject.parseObject(json.getString(i)).getString("profitRule")+"'";
            UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
            //为总公司
            if("110000".equals(user.getUnNo())){
            }
            //为总公司并且是部门---查询全部
            else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
                UnitModel parent = unitModel.getParent();
                if("110000".equals(parent.getUnNo())){
                }
            }else{
                sql+=" and unno ='"+user.getUnNo()+"'";
            }
            List<CheckProfitTemplateMicroWModel> list=checkUnitProfitMicroWDao.queryObjectsBySqlList(sql, null,CheckProfitTemplateMicroWModel.class );
            if(list.size()>0){
                CheckProfitTemplateMicroWModel model = list.get(0);
               
                model.setCreditBankRate(Double.parseDouble(new BigDecimal(creditBankRate+"").divide(new BigDecimal("100"))+""));
                model.setCashRate(Double.parseDouble(cashRate));
                model.setRuleThreshold(Double.parseDouble(new BigDecimal(ruleThreshold+"").divide(new BigDecimal("100"))+""));
                model.setStartAmount(Double.parseDouble(startAmount));
                model.setScanRate(Double.parseDouble(new BigDecimal(scanRate+"").divide(new BigDecimal("100"))+""));
                model.setCashAmt1(Double.parseDouble(cashAmt1));
                model.setScanRate1(Double.parseDouble(new BigDecimal(scanRate1+"").divide(new BigDecimal("100"))+""));
                model.setProfitPercent1(Double.parseDouble(profitPercent1));
                model.setScanRate2(Double.parseDouble(new BigDecimal(scanRate2+"").divide(new BigDecimal("100"))+""));
                model.setCashAmt2(Double.parseDouble(cashAmt2));
                model.setSubRate(Double.parseDouble(new BigDecimal(subRate+"").divide(new BigDecimal("100"))+""));
                model.setCashAmt(Double.parseDouble(cashAmt));
                model.setCloudQuickRate(Double.parseDouble(new BigDecimal(cloudQuickRate+"").divide(new BigDecimal("100"))+""));
                
               // model.setMatainType("M");
                if(subtype != null) {
                	model.setHuabeiRate(Double.parseDouble(new BigDecimal(huabeiRate+"").divide(new BigDecimal("100"))+""));
                	model.setHuabeiFee(Double.parseDouble(huabeiFee));
                }
                model.setMatainUserId(user.getUserID());
                model.setMatainDate(new Date());
                checkUnitProfitMicroWDao.updateObject(model);
            }else{
                //新增
                CheckProfitTemplateMicroBean bean = new CheckProfitTemplateMicroBean();
                
                bean.setCreditBankRate(Double.parseDouble(creditBankRate));
                bean.setCashRate(Double.parseDouble(cashRate));
                bean.setRuleThreshold(Double.parseDouble(ruleThreshold));
                bean.setStartAmount(Double.parseDouble(startAmount));
                bean.setScanRate(Double.parseDouble(scanRate));
                bean.setCashAmt1(Double.parseDouble(cashAmt1));
                bean.setScanRate1(Double.parseDouble(scanRate1));
                bean.setProfitPercent1(Double.parseDouble(profitPercent1));
                bean.setScanRate2(Double.parseDouble(scanRate2));
                bean.setCashAmt2(Double.parseDouble(cashAmt2));
                bean.setSubRate(Double.parseDouble(subRate));
                bean.setCashAmt(Double.parseDouble(cashAmt));
                bean.setCloudQuickRate(Double.parseDouble(cloudQuickRate));
                
                bean.setMatainType(matainType);
                bean.setAptId(Integer.parseInt(aptId));
                bean.setProfitRule(Integer.parseInt(JSONObject.parseObject(json.getString(i)).getString("profitRule")));
                bean.setTempName(tempName);
                
                if(subtype != null) {
                	bean.setHuabeiRate(Double.parseDouble(new BigDecimal(huabeiRate+"").divide(new BigDecimal("100"))+""));
                	bean.setHuabeiFee(Double.parseDouble(huabeiFee));
                }
                //saveMposTempla(bean,user);
                saveMposTemplaIncrease(bean,user);
            }
        }
    }

    @Override
    public DataGridBean queryMposTemplateList(CheckProfitTemplateMicroBean cptm, UserBean user) {
		String sql="select t.unno,a.tempname,decode(a.mataintype, 'P', 'P', 'A') mataintype,c.un_name as unitName " +
				" from check_unit_profitemplate t,check_micro_profittemplate a ,sys_unit c " +
				" where t.aptid=a.aptid and t.unno=c.unno and t.STATUS!=0 and a.merchanttype=6 ";
		Map<String,Object> map = new HashMap<String,Object>();
		if(user.getUnitLvl()==0){
		}else{
			sql+=" and c.upper_unit=:UNNO ";
			map.put("UNNO", user.getUnNo());
		}
		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
			sql+=" and t.unno=:UNNO2";
			map.put("UNNO2", cptm.getUnno());
		}
		sql +=" group by t.unno,a.tempname,c.un_name,decode(a.mataintype, 'P', 'P', 'A')";
		String sqlCount="select count(*) from ("+sql+")";
		Integer count= checkUnitProfitMicroDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list = checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, map, cptm.getPage(), cptm.getRows());
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
    }

    @Override
    public List<Map<String, Object>> queryMposTemplateByUnno(UserBean user) {
		String sql="select t.tempname from check_micro_profittemplate t where t.MatainType in('A','M') and merchanttype=6 ";
		if(user.getUnitLvl()!=0){
			sql+=" and t.unno='"+user.getUnNo()+"'";
		}
		sql+=" group by t.tempname";
		List<Map<String, Object>> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql);
		return list;
    }

    @Override
    public List<Map<String, Object>> queryUnnoMpos(CheckProfitTemplateMicroBean cptm) {
    	// @author:lxg-20191108 plus活动1个机构号可以分配多个模板,但多个模板不能存在相同活动的类型
		String sql="select c.profitrule from check_micro_profittemplate c where c.profitrule in ( " +
				" select b.profitrule from check_unit_profitemplate a, check_micro_profittemplate b " +
				" where a.unno = '"+cptm.getUnno()+"' and a.aptid = b.aptid and a.status = 1 and b.mataintype != 'D' and b.merchanttype=6 " +
				") and c.tempname='"+cptm.getTempName()+"' and c.merchanttype=6 ";
//		java.net.URLDecoder.decode(cptm.getTempName(),"UTF-8").trim()
		List<Map<String, Object>> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql);
		return list;
    }

    @Override
    public String saveMposTemplateInfo(CheckProfitTemplateMicroBean cptm, UserBean user) {
		String hql="from CheckProfitTemplateMicroModel t where t.unno=:UNNO and t.tempName=:TEMPNAME and t.matainType !=:MATAINTYPE and t.merchantType=6";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("UNNO", user.getUnNo());
		map.put("TEMPNAME", cptm.getTempName());
		map.put("MATAINTYPE", "D");
		List<CheckProfitTemplateMicroModel> list=checkUnitProfitMicroDao.queryObjectsByHqlList(hql, map);
		// @author:lxg-20190822 plus活动分配校验
        CheckProfitTemplateMicroBean t=new CheckProfitTemplateMicroBean();
        JSONArray tempName= new JSONArray();
        for (CheckProfitTemplateMicroModel model : list) {
            JSONObject jsonObject = new JSONObject();
			if (model.getCreditBankRate() != null) {
				// jsonObject.put("creditBankRate",model.getCreditBankRate()*100);
				jsonObject.put("creditBankRate", new BigDecimal(model.getCreditBankRate() + "").multiply(new BigDecimal(100)));
			}
			if (model.getRuleThreshold() != null) {
				// jsonObject.put("ruleThreshold",model.getRuleThreshold()*100);
				jsonObject.put("ruleThreshold", new BigDecimal(model.getRuleThreshold() + "").multiply(new BigDecimal(100)));
			}
			if (model.getScanRate() != null) {
				// jsonObject.put("scanRate",model.getScanRate()*100);
				jsonObject.put("scanRate", new BigDecimal(model.getScanRate() + "").multiply(new BigDecimal(100)));
			}
			if (model.getScanRate1() != null) {
				// jsonObject.put("scanRate1",model.getScanRate1()*100);
				jsonObject.put("scanRate1", new BigDecimal(model.getScanRate1() + "").multiply(new BigDecimal(100)));
			}
			if (model.getSubRate() != null) {
				// jsonObject.put("subRate",model.getSubRate()*100);
				jsonObject.put("subRate", new BigDecimal(model.getSubRate() + "").multiply(new BigDecimal(100)));
			}
			if (model.getScanRate2() != null) {
				// jsonObject.put("scanRate2",model.getScanRate2()*100);
				jsonObject.put("scanRate2", new BigDecimal(model.getScanRate2() + "").multiply(new BigDecimal(100)));
			}
			if (model.getCloudQuickRate() != null) {
				// jsonObject.put("cloudQuickRate",model.getCloudQuickRate()*100);
				jsonObject.put("cloudQuickRate", new BigDecimal(model.getCloudQuickRate() + "").multiply(new BigDecimal(100)));
			}
            if(model.getCashRate()!=null) {
                jsonObject.put("cashRate", model.getCashRate() + "");
            }
            if(model.getStartAmount()!=null) {
                jsonObject.put("startAmount", model.getStartAmount() + "");
            }
            if(model.getCashAmt1()!=null) {
            	jsonObject.put("cashAmt1", model.getCashAmt1() + "");
            }
            if(model.getProfitPercent1()!=null) {
            	jsonObject.put("profitPercent1", model.getProfitPercent1() + "");
            }
            if(model.getCashAmt()!=null) {
            	jsonObject.put("cashAmt", model.getCashAmt() + "");
            }
            if(model.getCashAmt2()!=null) {
            	jsonObject.put("cashAmt2", model.getCashAmt2() + "");
            }
            
            if(model.getHuabeiRate()!=null) {
            	jsonObject.put("huabeiRate", new BigDecimal(model.getHuabeiRate() + "").multiply(new BigDecimal(100)));
            }
            if(model.getHuabeiFee()!=null) {
            	jsonObject.put("huabeiFee", model.getHuabeiFee() + "");
            }
            
            jsonObject.put("profitRule",model.getProfitRule()+"");
            tempName.add(jsonObject);
        }
        t.setTempname(tempName.toJSONString());
        //添加校验绑定时，绑定模板时，模板费率不可以为null--ztt
        String errMsg1 = validatePlusMposTemplaIsNull(t,user);
        if(StringUtils.isNotEmpty(errMsg1)){
        	return errMsg1+",请修改或创建模板后再进行分配";
		}
        String errMsg = validatePlusMposTempla(t,user,1);
        if(StringUtils.isNotEmpty(errMsg)){
        	return errMsg+",请修改或创建模板后再进行分配";
		}
		if(list.size()==0){
			return "模板可能不存在，请确认！";
		}else{
			for(int i=0;i<list.size();i++){
				String sql="insert into check_unit_profitemplate values  ('"+ cptm.getUnno()+"',"+list.get(i).getAptId()+",1,sysdate,"+user.getUserID()+")";
				checkUnitProfitMicroDao.executeUpdate(sql);
				
				CheckProfitTemplateMicroLogModel logModel = new CheckProfitTemplateMicroLogModel();
				BeanUtils.copyProperties(list.get(i), logModel);
				logModel.setUnno(cptm.getUnno());
				logModel.setStartdate(new Date());
				logModel.setStatus(1);
				logModel.setMatainUserId(user.getUserID());
				checkUnitProfitMicroLogDao.saveObject(logModel);
			}
			return null;
		}
    }

    @Override
    public void deleteMposTemplateUnno(String unno) {
		String sql ="update CHECK_UNIT_PROFITEMPLATE set STATUS=0 where aptid in(" +
				" select p.aptid  from CHECK_MICRO_PROFITTEMPLATE p, check_unit_profitemplate p1" +
				" where p.aptid=p1.aptid and p1.unno='"+unno+"' and p1.status!=0 and p.merchanttype=6 ) and unno='"+unno+"'";
		checkUnitProfitMicroDao.executeUpdate(sql);
		
		String logSql = " update CHECK_MICRO_PROFITTEMPLATE_LOG set ENDDATE = sysdate where merchanttype=6 and enddate is null and unno='"+unno+"'";
		checkUnitProfitMicroLogDao.executeUpdate(logSql);
    }

    @Override
    public DataGridBean queryUnitMposTemplateDetailDataScan(CheckProfitTemplateMicroBean cptm, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		if(cptm.getTxnDay()==null||"".equals(cptm.getTxnDay())||cptm.getTxnDay1()==null||"".equals(cptm.getTxnDay1())||!cptm.getTxnDay().substring(0, 6).equals(cptm.getTxnDay1().substring(0, 6))) {
			return null;
		}
		cptm.setTxnDay(cptm.getTxnDay().replace("-", ""));
		cptm.setTxnDay1(cptm.getTxnDay1().replace("-", ""));
		//mpos分润汇总 分润算法为mda - TXNAMOUNT * y.subrate
		Map map = new HashMap();
		String sql = getUnitMposTemplateDetailDataScanSql(cptm,map);
		String sqlCount ="select  count(*) from ("+sql+")";
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, map, cptm.getPage(), cptm.getRows());
		Integer count=checkUnitProfitMicroDao.querysqlCounts2(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
    }

    @Override
    public DataGridBean queryUnitMposProfitMicroSumDataCash(CheckProfitTemplateMicroBean cptm) {
    	Map map = new HashMap();
    	String sql = getUnitMposProfitMicroSumDataCash(cptm,map);
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlListMap2(sql, map);
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(list.size());
		return dgb;
    }

    /**
	 * 删除分配代还分润模版
	 */
	public void deleteSubTemplateUnno(String unno) {
		String sql ="update CHECK_UNIT_PROFITEMPLATE set STATUS=0 where aptid in(" +
					" select p.aptid  from CHECK_MICRO_PROFITTEMPLATE p, check_unit_profitemplate p1" +
					" where p.aptid=p1.aptid and p1.unno='"+unno+"' and p1.status!=0 and p.merchanttype=4 ) and unno='"+unno+"'";
		checkUnitProfitMicroDao.executeUpdate(sql);
		String sqlLog ="update check_micro_profittemplate_log set ENDDATE=sysdate " +
				" where merchanttype=4 and unno='"+unno+"' and ENDDATE is null";
		checkUnitProfitMicroLogDao.executeUpdate(sqlLog);
	}
	/**
	 * 删除分配收银台分润模版
	 */
	public void deleteSytTemplateUnno(String unno) {
		String sql ="update CHECK_UNIT_PROFITEMPLATE set STATUS=0 where aptid in(" +
					" select p.aptid  from CHECK_MICRO_PROFITTEMPLATE p, check_unit_profitemplate p1" +
					" where p.aptid=p1.aptid and p1.unno='"+unno+"' and p1.status!=0 and p.merchanttype=5 ) and unno='"+unno+"'";
		checkUnitProfitMicroDao.executeUpdate(sql);
		
		//解除绑定时,将log表中绑定模板记录时间修改为当前时间-----20190926
		String logSql = " update CHECK_MICRO_PROFITTEMPLATE_LOG set ENDDATE= sysdate where merchanttype=5 and enddate is null and unno='"+unno+"'";
		checkUnitProfitMicroLogDao.executeUpdate(logSql);
		
	}
	@Override
	public List<Map<String, Object>> queryProfitTempByUnno(UserBean user) {
		String sql="select t.tempname from check_micro_profittemplate t where t.MatainType !='D' and t.MatainType !='P' and merchanttype in (1,2,3)";
		if(user.getUnitLvl()!=0){
			sql+=" and t.unno='"+user.getUnNo()+"'";
		}
		sql+=" group by t.tempname";
		List<Map<String, Object>> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql);
		return list;
	}
	@Override
	public List<Map<String, Object>> querySubTemplateByUnno(UserBean user) {
		String sql="select t.tempname from check_micro_profittemplate t where t.MatainType !='D' and t.MatainType !='P' and merchanttype=4 ";
		if(user.getUnitLvl()!=0){
			sql+=" and t.unno='"+user.getUnNo()+"'";
		}
		sql+=" group by t.tempname";
		List<Map<String, Object>> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql);
		return list;
	}
	@Override
	public List<Map<String, Object>> querySytTemplateByUnno(UserBean user) {
		String sql="select t.tempname from check_micro_profittemplate t where t.MatainType in ('A','M') and merchanttype=5 ";
		if(user.getUnitLvl()!=0){
			sql+=" and t.unno='"+user.getUnNo()+"'";
		}
		sql+=" group by t.tempname";
		List<Map<String, Object>> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql);
		return list;
	}
	/**
	 * 判断分配模版是否重复
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryUnno(CheckProfitTemplateMicroBean cptm) {
//		String sqlSettMethod="select t.settMethod from CHECK_MICRO_PROFITTEMPLATE t where t.unno =(select upper_unit from sys_unit where unno='"+cptm.getUnno()+"' ) and  t.tempname ='"+cptm.getTempName()+"' and t.matainType!='D' ";
//		List<Map<String, Object>> listSett=checkUnitProfitMicroDao.queryObjectsBySqlList(sqlSettMethod);
//		String settMethod=listSett.get(0).get("SETTMETHOD").toString();
		String sql="select p1.unno from CHECK_MICRO_PROFITTEMPLATE p ,check_unit_profitemplate p1 where p.aptid=p1.aptid " +
				" and p1.unno='"+cptm.getUnno()+"' and p1.STATUS!=0 and p.merchanttype in (1,2,3) ";//and p.settmethod='"+settMethod+"'
		List<Map<String, Object>> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql);
		return list;
	}
	/**
	 * 判断分配代还模版是否重复
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryUnno2(CheckProfitTemplateMicroBean cptm) {
		String sql="select p1.unno from CHECK_MICRO_PROFITTEMPLATE p ,check_unit_profitemplate p1 where p.aptid=p1.aptid and p1.unno='"+cptm.getUnno()+"' and p1.STATUS!=0 and p.merchanttype=4";//and p.settmethod='"+settMethod+"'
		List<Map<String, Object>> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql);
		return list;
	}
	/**
	 * 判断分配收银台模版是否重复
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryUnno3(CheckProfitTemplateMicroBean cptm) {
		String sql="select p1.unno from CHECK_MICRO_PROFITTEMPLATE p ,check_unit_profitemplate p1 where p.aptid=p1.aptid and p1.unno='"+cptm.getUnno()+"' and p1.STATUS!=0 and p.merchanttype=5";//and p.settmethod='"+settMethod+"'
		List<Map<String, Object>> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql);
		return list;
	}
	@Override
	public String saveUnitProfitTempInfo(CheckProfitTemplateMicroBean cptm,UserBean user) {
		String hql="from CheckProfitTemplateMicroModel t where t.unno=:UNNO and t.tempName=:TEMPNAME and t.matainType !=:MATAINTYPE ";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("UNNO", user.getUnNo());
		map.put("TEMPNAME", cptm.getTempName());
		map.put("MATAINTYPE", "D");
		List<CheckProfitTemplateMicroModel> list=checkUnitProfitMicroDao.queryObjectsByHqlList(hql, map);
		// @author:lxg-20190822 分润模板分配校验
		CheckProfitTemplateMicroBean t=null;
		try {
			t = queryupdateProfitmicroOnly(cptm,user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String errMsg = validateMposTemplate(t,user,1);
		if(StringUtils.isNotEmpty(errMsg)){
			return errMsg+",请修改或创建模板后,再进行分配";
		}
		if(list.size()==0){
			return "模板可能不存在，请确认！";
		}else{
			for(int i=0;i<list.size();i++){
				String sql="insert into check_unit_profitemplate values  ('"+ cptm.getUnno()+"',"+list.get(i).getAptId()+",1,sysdate,"+user.getUserID()+")";
				checkUnitProfitMicroDao.executeUpdate(sql);

				CheckProfitTemplateMicroLogModel logModel = new CheckProfitTemplateMicroLogModel();
				CheckProfitTemplateMicroModel checkProfitTemplateMicroModel = list.get(i);
				BeanUtils.copyProperties(checkProfitTemplateMicroModel,logModel);
				logModel.setStatus(1);
				logModel.setStartdate(new Date());
				logModel.setUnno(cptm.getUnno());
				logModel.setMatainUserId(user.getUserID());
				checkUnitProfitMicroLogDao.saveObject(logModel);
			}
			return null;
		}

	}
	@Override
	public String saveSubTemplateInfo(CheckProfitTemplateMicroBean cptm,UserBean user) {
		String hql="from CheckProfitTemplateMicroModel t where t.unno=:UNNO and t.tempName=:TEMPNAME and t.matainType !=:MATAINTYPE and t.merchantType=4";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("UNNO", user.getUnNo());
		map.put("TEMPNAME", cptm.getTempName());
		map.put("MATAINTYPE", "D");
		List<CheckProfitTemplateMicroModel> list=checkUnitProfitMicroDao.queryObjectsByHqlList(hql, map);
		// @author:lxg20190822 代还分配校验
        for (CheckProfitTemplateMicroModel model : list) {
            CheckProfitTemplateMicroBean t=new CheckProfitTemplateMicroBean();
            t.setSubRate(model.getSubRate()*100);
            String errMsg = validateSubTemplate(t,user,1);
            if(StringUtils.isNotEmpty(errMsg)){
                return errMsg+",请修改或创建模板后再进行分配";
            }
        }
		if(list.size()==0){
			return "模板可能不存在，请确认！";
		}else{
			for(int i=0;i<list.size();i++){
				String sql="insert into check_unit_profitemplate values  ('"+ cptm.getUnno()+"',"+list.get(i).getAptId()+",1,sysdate,"+user.getUserID()+")";
				checkUnitProfitMicroDao.executeUpdate(sql);

				CheckProfitTemplateMicroModel model=list.get(i);
				CheckProfitTemplateMicroLogModel logModel = new CheckProfitTemplateMicroLogModel();
				BeanUtils.copyProperties(model,logModel);
				logModel.setMatainUserId(user.getUserID());
				logModel.setUnno(cptm.getUnno());
				logModel.setStartdate(new Date());
				logModel.setStatus(1);
				checkUnitProfitMicroLogDao.saveObject(logModel);
			}
			return null;
		}

	}
	@Override
	public String saveSytTemplateInfo(CheckProfitTemplateMicroBean cptm,UserBean user) {
		String hql="from CheckProfitTemplateMicroModel t where t.unno=:UNNO and t.tempName=:TEMPNAME and t.matainType !=:MATAINTYPE and t.merchantType=5";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("UNNO", user.getUnNo());
		map.put("TEMPNAME", cptm.getTempName());
		map.put("MATAINTYPE", "D");
		List<CheckProfitTemplateMicroModel> list=checkUnitProfitMicroDao.queryObjectsByHqlList(hql, map);
        for (CheckProfitTemplateMicroModel model : list) {
            CheckProfitTemplateMicroBean t = new CheckProfitTemplateMicroBean();
            if(model.getCreditBankRate()!=null) {
                //   t.setCreditBankRate(model.getCreditBankRate()*100);
                t.setCreditBankRate(Double.parseDouble(new BigDecimal(model.getCreditBankRate() + "").multiply(new BigDecimal(100 + "")) + ""));
            }
            if (model.getScanRate() != null) {
                //   t.setScanRate(model.getScanRate()*100);
                t.setScanRate(Double.parseDouble(new BigDecimal(model.getScanRate() + "").multiply(new BigDecimal(100 + "")) + ""));
            }
            if (model.getSubRate() != null) {
                //   t.setSubRate(model.getSubRate()*100);
                t.setSubRate(Double.parseDouble(new BigDecimal(model.getSubRate() + "").multiply(new BigDecimal(100 + "")) + ""));
            }
            if (model.getScanRate1() != null) {
                //   t.setScanRate1(model.getScanRate1()*100);
                t.setScanRate1(Double.parseDouble(new BigDecimal(model.getScanRate1() + "").multiply(new BigDecimal(100 + "")) + ""));
            }
            if (model.getScanRate2() != null) {
                //   t.setScanRate2(model.getScanRate2()*100);
                t.setScanRate2(Double.parseDouble(new BigDecimal(model.getScanRate2() + "").multiply(new BigDecimal(100 + "")) + ""));
            }
            if(model.getHuabeiRate()!=null){
            	t.setHuabeiRate(Double.parseDouble(new BigDecimal(model.getHuabeiRate() + "").multiply(new BigDecimal(100 + "")) + ""));
            }
            t.setCashRate(model.getCashRate());
            t.setProfitPercent1(model.getProfitPercent1());
            t.setCashAmt(model.getCashAmt());
            t.setCashAmt1(model.getCashAmt1());
            t.setCashAmt2(model.getCashAmt2());
            t.setHuabeiFee(model.getHuabeiFee());
           
            //添加判断取到的模板是空值活动的--赋值默认21
            if(model.getProfitRule() !=null) {
            	t.setProfitRule(model.getProfitRule());
            }else {
            	t.setProfitRule(21);
            }
            
            //绑定模板时，模板成本不能为空--ztt
            String errMsg1 = validateSytTemplateIsNull(t,user);
            if(StringUtils.isNotEmpty(errMsg1)){
                return errMsg1+",请修改模板后再进行分配";
            }
            // 分配syt模板时校验,按存储字段校验
            String errMsg = validateSytTemplate(t,user,1);
            if(StringUtils.isNotEmpty(errMsg)){
                return errMsg+",请修改模板后再进行分配";
            }
        }
		if(list.size()==0){
			return "模板可能不存在，请确认！";
		}else{
			Date date = new Date();//确保同一次模板分配的开始时间一样
			for(int i=0;i<list.size();i++){
				String sql="insert into check_unit_profitemplate values  ('"+ cptm.getUnno()+"',"+list.get(i).getAptId()+",1,sysdate,"+user.getUserID()+")";
				checkUnitProfitMicroDao.executeUpdate(sql);
				
				CheckProfitTemplateMicroLogModel logModel = new CheckProfitTemplateMicroLogModel();
				BeanUtils.copyProperties(list.get(i), logModel);
				logModel.setMatainUserId(user.getUserID());
				logModel.setUnno(cptm.getUnno());
				logModel.setStartdate(date);
				logModel.setStatus(1);
				if(list.get(i).getHuabeiRate()!=null){
					logModel.setHuabeiRate(list.get(i).getHuabeiRate());
					logModel.setHuabeiFee(list.get(i).getHuabeiFee());
				}
					
				
				checkUnitProfitMicroLogDao.saveObject(logModel);
			}
			return null;
		}
	}

	@Override
	public Map<String,CheckProfitTemplateMicroModel> getProfitTemplateMap(int type,String unno,Integer unLvl,int subType){
		Map<String, CheckProfitTemplateMicroModel> result = new HashMap<String, CheckProfitTemplateMicroModel>();
		List<CheckProfitTemplateMicroModel> list=new ArrayList<CheckProfitTemplateMicroModel>();
		if(unLvl>=3) {
			if(type==1) {
				String sql = "select t.* from check_micro_profittemplate t, check_unit_profitemplate k" +
						" where t.aptid = k.aptid and k.status = 1 and t.mataintype != 'D' and k.unno = :unno ";
				Map map = new HashMap();
				map.put("unno", unno);
				list = checkUnitProfitMicroDao.queryObjectsBySqlList(sql, map,CheckProfitTemplateMicroModel.class);
				if (list.size() == 0) {
					return new HashMap<String, CheckProfitTemplateMicroModel>();
				}
			}/*else if(type==2){
				String sql ="select t.* from check_micro_profittemplate t, check_unit_profitemplate k,sys_unit s" +
						" where t.aptid = k.aptid and k.status = 1 and t.mataintype != 'D' and k.unno = s.upper_unit and s.unno=:unno";
				Map map = new HashMap();
				map.put("unno",unno);
				list = checkUnitProfitMicroDao.queryObjectsBySqlList(sql,map,CheckProfitTemplateMicroModel.class);
			}else if(type==3){
				return getSubUnnoMinTemplate(unno);
			}*/
			for (CheckProfitTemplateMicroModel microModel : list) {
				String profitType = microModel.getProfitType() == null ? "0" : microModel.getProfitType() + "";
				String profitRule = microModel.getProfitRule() == null ? "21" : microModel.getProfitRule() + "";
				String key = microModel.getMerchantType() + "|" + profitType + "|" + profitRule;
				result.put(key, microModel);
			}

			if(subType==2){
				List<CheckProfitTemplateMicroWModel> listW=new ArrayList<CheckProfitTemplateMicroWModel>();
				if(DateTools.isFirstDay() || true){
					// 1号取上个月修改的生效记录
					String sqlUp = "select t.* from check_micro_profittemplate_w t, check_unit_profitemplate k" +
							" where t.aptid = k.aptid and k.status = 1 and t.mataintype != 'D' and k.unno = :unno" +
							" and t.mataindate>=:date1 and t.mataindate<=:date2 ";
					Map map = new HashMap();
					map.put("unno", unno);
					map.put("date1", DateTools.getStartMonth(DateTools.getUpMonth(new Date())));
					map.put("date2", DateTools.getEndtMonth(DateTools.getUpMonth(new Date())));
					listW = checkUnitProfitMicroWDao.queryObjectsBySqlList(sqlUp, map,CheckProfitTemplateMicroWModel.class);
				}else{
					// 非1号取当月修改的生效记录
					String sqlUp = "select t.* from check_micro_profittemplate_w t, check_unit_profitemplate k" +
							" where t.aptid = k.aptid and k.status = 1 and t.mataintype != 'D' and k.unno = :unno" +
							" and t.mataindate>=:date1 and t.mataindate<=:date2 ";
					Map map = new HashMap();
					map.put("unno", unno);
					map.put("date1", DateTools.getStartMonth(new Date()));
					map.put("date2", DateTools.getEndtMonth(new Date()));
					listW = checkUnitProfitMicroWDao.queryObjectsBySqlList(sqlUp, map,CheckProfitTemplateMicroWModel.class);
				}

				for (CheckProfitTemplateMicroWModel microWModel : listW) {
					CheckProfitTemplateMicroModel microModelA=new CheckProfitTemplateMicroModel();
					BeanUtils.copyProperties(microWModel,microModelA);
					String profitType = microModelA.getProfitType() == null ? "0" : microModelA.getProfitType() + "";
					String profitRule = microModelA.getProfitRule() == null ? "21" : microModelA.getProfitRule() + "";
					String key = microModelA.getMerchantType() + "|" + profitType + "|" + profitRule;
					result.put(key, microModelA);
				}
			}
		}
		return result;
	}

	/**
	 * 子代最小成本获取
	 * @param unno
	 * @return
	 */
	private Map<String,CheckProfitTemplateMicroModel> getSubUnnoMinTemplate(String unno){
		Map<String,CheckProfitTemplateMicroModel> result = new HashMap<String, CheckProfitTemplateMicroModel>();
		String sql ="select t.* from check_micro_profittemplate t, check_unit_profitemplate k,sys_unit s" +
				" where t.aptid = k.aptid and k.status = 1 and t.mataintype != 'D' and k.unno = s.unno and s.upper_unit=:unno";
		Map map = new HashMap();
		map.put("unno",unno);
		List<CheckProfitTemplateMicroModel> list=checkUnitProfitMicroDao.queryObjectsBySqlList(sql,map,CheckProfitTemplateMicroModel.class);
		if(list.size()==0){
			return new HashMap<String, CheckProfitTemplateMicroModel>();
		}
		for (CheckProfitTemplateMicroModel microModel : list) {
			String profitType=microModel.getProfitType()==null?"0":microModel.getProfitType()+"";
			String profitRule=microModel.getProfitRule()==null?"0":microModel.getProfitRule()+"";
			String key=microModel.getMerchantType()+"|"+profitType+"|"+profitRule;
			if (result.containsKey(key)) {
				// 获取对应的最小的值
				setOldProfitAndNewProfitMinProfit(key,result,result.get(key),microModel);
			}else {
				result.put(key, microModel);
			}
		}
		return result;
	}

	private void setOldProfitAndNewProfitMinProfit(String key,Map map,CheckProfitTemplateMicroModel oldMicro,
												   CheckProfitTemplateMicroModel newMicro){
		// @author:lxg-20190729 之前确定的那个模板里的封顶值和手续费也要控制，分润比例确定了全部按照百分百
		if (oldMicro.getStartAmount() != null && newMicro.getStartAmount()!=null) {
			if(oldMicro.getStartAmount().compareTo(newMicro.getStartAmount())>0){
				oldMicro.setStartAmount(newMicro.getStartAmount());
			}
		}

		if (oldMicro.getEndAmount() != null && newMicro.getEndAmount()!=null) {
			if(oldMicro.getEndAmount().compareTo(newMicro.getEndAmount())>0){
				oldMicro.setEndAmount(newMicro.getEndAmount());
			}
		}

		if (oldMicro.getRuleThreshold() != null && newMicro.getRuleThreshold()!=null) {
			if(oldMicro.getRuleThreshold().compareTo(newMicro.getRuleThreshold())>0){
				oldMicro.setRuleThreshold(newMicro.getRuleThreshold());
			}
		}

		if(oldMicro.getProfitPercent()!=null && newMicro.getProfitPercent()!=null){
			if(oldMicro.getProfitPercent().compareTo(newMicro.getProfitPercent())>0){
				oldMicro.setProfitPercent(newMicro.getProfitPercent());
			}
		}

		if(oldMicro.getProfitPercent1()!=null && newMicro.getProfitPercent1()!=null){
			if(oldMicro.getProfitPercent1().compareTo(newMicro.getProfitPercent1())>0){
				oldMicro.setProfitPercent1(newMicro.getProfitPercent1());
			}
		}

		if(oldMicro.getCreditBankRate()!=null && newMicro.getCreditBankRate()!=null){
			if(oldMicro.getCreditBankRate().compareTo(newMicro.getCreditBankRate())>0){
				oldMicro.setCreditBankRate(newMicro.getCreditBankRate());
			}
		}

		if(oldMicro.getCashRate()!=null && newMicro.getCashRate()!=null){
			if(oldMicro.getCashRate().compareTo(newMicro.getCashRate())>0){
				oldMicro.setCashRate(newMicro.getCashRate());
			}
		}

		if(oldMicro.getCashAmt()!=null && newMicro.getCashAmt()!=null){
			if(oldMicro.getCashAmt().compareTo(newMicro.getCashAmt())>0){
				oldMicro.setCashAmt(newMicro.getCashAmt());
			}
		}

		if(oldMicro.getScanRate()!=null && newMicro.getScanRate()!=null){
			if(oldMicro.getScanRate().compareTo(newMicro.getScanRate())>0){
				oldMicro.setScanRate(newMicro.getScanRate());
			}
		}

		if(oldMicro.getCloudQuickRate()!=null && newMicro.getCloudQuickRate()!=null){
			if(oldMicro.getCloudQuickRate().compareTo(newMicro.getCloudQuickRate())>0){
				oldMicro.setCloudQuickRate(newMicro.getCloudQuickRate());
			}
		}

		if(oldMicro.getSubRate()!=null && newMicro.getSubRate()!=null){
			if(oldMicro.getSubRate().compareTo(newMicro.getSubRate())>0){
				oldMicro.setSubRate(newMicro.getSubRate());
			}
		}

		if(oldMicro.getCashAmt2()!=null && newMicro.getCashAmt2()!=null){
			if(oldMicro.getCashAmt2().compareTo(newMicro.getCashAmt2())>0){
				oldMicro.setCashAmt2(newMicro.getCashAmt2());
			}
		}

		if(oldMicro.getCashAmt1()!=null && newMicro.getCashAmt1()!=null){
			if(oldMicro.getCashAmt1().compareTo(newMicro.getCashAmt1())>0){
				oldMicro.setCashAmt1(newMicro.getCashAmt1());
			}
		}

		if(oldMicro.getScanRate1()!=null && newMicro.getScanRate1()!=null){
			if(oldMicro.getScanRate1().compareTo(newMicro.getScanRate1())>0){
				oldMicro.setScanRate1(newMicro.getScanRate1());
			}
		}

		if(oldMicro.getScanRate2()!=null && newMicro.getScanRate2()!=null){
			if(oldMicro.getScanRate2().compareTo(newMicro.getScanRate2())>0){
				oldMicro.setScanRate2(newMicro.getScanRate2());
			}
		}
		map.put(key,oldMicro);
	}
	
	/**
	 * 用来校验表单提交的是否有空值（其实页面验证是最好的，但是页面验证没有成功，先暂时后台做校验）
	 * 
	 * 20191203-ztt这里将微信与支付宝费率信息统一修改为扫码1000以上0.38/0.45、扫码1000以下0.38/0.45
	 * */
	@Override
	public String validatePlusTextNotEmpty(CheckProfitTemplateMicroBean cpt) {
		String tempName = cpt.getTempName();
        String str = cpt.getTempname();
        JSONArray json = JSONArray.parseArray(str);
        for(int i=0;i<json.size();i++){
        	//微信1000以上0.38费率
        	String creditBankRate = JSONObject.parseObject(json.getString(i)).getString("creditBankRate");
        	if(null == creditBankRate || "".equals(creditBankRate)) {
        		return "扫码1000以上（终端0.38）费率,不能为空";
        	}
        	//微信1000以上0.38转账费
        	String cashRate = JSONObject.parseObject(json.getString(i)).getString("cashRate");
        	if(null == cashRate || "".equals(cashRate)) {
        		return "扫码1000以上（终端0.38）转账费,不能为空";
        	}
        	//微信1000以上0.45费率
        	String ruleThreshold = JSONObject.parseObject(json.getString(i)).getString("ruleThreshold");
        	if(null == ruleThreshold || "".equals(ruleThreshold)) {
        		return "扫码1000以上（终端0.45）费率,不能为空";
        	}
        	//微信1000以上0.45转账费
        	String startAmount = JSONObject.parseObject(json.getString(i)).getString("startAmount");
        	if(null == startAmount || "".equals(startAmount)) {
        		return "扫码1000以上（终端0.45）转账费,不能为空";
        	}
        	//微信1000以下费率
        	String scanRate = JSONObject.parseObject(json.getString(i)).getString("scanRate");
        	if(null == scanRate || "".equals(scanRate)) {
        		return "扫码1000以下（终端0.38）费率,不能为空";
        	}
        	//微信1000以下转账费
        	String cashAmt1 = JSONObject.parseObject(json.getString(i)).getString("cashAmt1");
        	if(null == cashAmt1 || "".equals(cashAmt1)) {
        		return "扫码1000以下（终端0.38）转账费,不能为空";
        	}
        	//支付宝费率
        	String scanRate1 = JSONObject.parseObject(json.getString(i)).getString("scanRate1");
        	if(null == scanRate1 || "".equals(scanRate1)) {
        		return "扫码1000以下（终端0.45）费率,不能为空";
        	}
        	//支付宝转账费
        	String profitPercent1 = JSONObject.parseObject(json.getString(i)).getString("profitPercent1");
        	if(null == profitPercent1 || "".equals(profitPercent1)) {
        		return "扫码1000以下（终端0.45）转账费,不能为空";
        	}
        	//二维码费率
        	String scanRate2 = JSONObject.parseObject(json.getString(i)).getString("scanRate2");
        	if(null == scanRate2 || "".equals(scanRate2)) {
        		return "银联二维码费率,不能为空";
        	}
        	//二维码转账费
        	String cashAmt2 = JSONObject.parseObject(json.getString(i)).getString("cashAmt2");
        	if(null == cashAmt2 || "".equals(cashAmt2)) {
        		return "银联二维码转账费,不能为空";
        	}
        	//秒到刷卡费率
        	String subRate = JSONObject.parseObject(json.getString(i)).getString("subRate");
        	if(null == subRate || "".equals(subRate)) {
        		return "秒到刷卡费率,不能为空";
        	}
        	//秒到刷卡转账费
        	String cashAmt = JSONObject.parseObject(json.getString(i)).getString("cashAmt");
        	if(null == cashAmt || "".equals(cashAmt)) {
        		return "秒到刷卡转账费,不能为空";
        	}
        	//云闪付费率
        	String cloudQuickRate = JSONObject.parseObject(json.getString(i)).getString("cloudQuickRate");
        	if(null == cloudQuickRate || "".equals(cloudQuickRate)) {
        		return "云闪付费率,不能为空";
        	}
        	
        	//活动类型
        	String profitRule = JSONObject.parseObject(json.getString(i)).getString("profitRule");
        	//判断是否是花呗活动
        	String subtype = querySubTypeByPlusProfitRule(Integer.parseInt(profitRule));
        	if(subtype!=null&&"2".equals(subtype)) {
        		//花呗费率
        		String huabeiRate = JSONObject.parseObject(json.getString(i)).getString("huabeiRate");
        		if(null == huabeiRate || "".equals(huabeiRate)) {
        			return "花呗费率,不能为空";
        		}
        		//花呗转账费
        		String huabeiFee = JSONObject.parseObject(json.getString(i)).getString("huabeiFee");
        		if(null == huabeiFee || "".equals(huabeiFee)) {
        			return "花呗转账费,不能为空";
        		}
        	}
        }
        return "非空校验通过";
	}
	

	@Override
	public DataGridBean getMicroProfitDhLog(CheckProfitTemplateMicroBean cptt,UserBean userBean){
		String sql = "select p.*,(select s.un_name from sys_unit s where s.unno=p.unno) un_name from CHECK_MICRO_PROFITTEMPLATE_LOG p " +
                " where 1=1 and p.merchanttype=4 ";
		Map params=new HashMap();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		if("110000".equals(userBean.getUnNo())){
			//为总公司
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			//为总公司并且是部门---查询全部
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and p.unno in(select unno from SYS_UNIT where UPPER_UNIT=:unno) " ;
			params.put("unno",userBean.getUnNo());
		}
		if(cptt.getUnno()!=null&&!"".equals(cptt.getUnno())){
			sql+= " and p.unno =:unno1 ";
			params.put("unno1",cptt.getUnno());
		}
		if(cptt!=null&&cptt.getTempName()!=null&&!cptt.getTempName().equals("")){
			sql+=" and p.TEMPNAME=:tempName ";
			params.put("tempName",cptt.getTempName());
		}
		sql+=" order by p.STARTDATE desc";
		String sqlCount = "select count(1) from ("+sql+")";
		Integer counts = checkUnitProfitMicroLogDao.querysqlCounts2(sqlCount, params);
		List<Map<String, String>> list = checkUnitProfitMicroLogDao.queryObjectsBySqlList(sql, params, cptt.getPage(), cptt.getRows());
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(counts);
		return dgb;
	}

	@Override
	public List<Map<String, Object>> exportMicroProfitDhLog(CheckProfitTemplateMicroBean cptt,UserBean userBean){
		String sql = "select p.*,(select s.un_name from sys_unit s where s.unno=p.unno) un_name from CHECK_MICRO_PROFITTEMPLATE_LOG p " +
				" where 1=1 and p.merchanttype=4 ";
		Map params=new HashMap();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		if("110000".equals(userBean.getUnNo())){
			//为总公司
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			//为总公司并且是部门---查询全部
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and p.unno in(select unno from SYS_UNIT where UPPER_UNIT=:unno) " ;
			params.put("unno",userBean.getUnNo());
		}
		if(cptt.getUnno()!=null&&!"".equals(cptt.getUnno())){
			sql+= " and p.unno =:unno1 ";
			params.put("unno1",cptt.getUnno());
		}
		if(cptt!=null&&cptt.getTempName()!=null&&!cptt.getTempName().equals("")){
			sql+=" and p.TEMPNAME=:tempName ";
			params.put("tempName",cptt.getTempName());
		}
		sql+=" order by p.STARTDATE desc";
		List<Map<String, Object>> list = checkUnitProfitMicroLogDao.queryObjectsBySqlListMap2(sql, params);
		return list;
	}

	@Override
	public DataGridBean getMicroProfitMdLog(CheckProfitTemplateMicroBean cptm,UserBean userBean){
		String sql = "select unno,un_name,TEMPNAME,to_char(STARTDATE,'yyyy-MM-dd') STARTDATE,to_char(ENDDATE,'yyyy-MM-dd') ENDDATE from ( " +
				" select p.*,(select s.un_name from sys_unit s where s.unno=p.unno) un_name from CHECK_MICRO_PROFITTEMPLATE_LOG p " +
				" where 1=1 and p.merchanttype in (1,2,3) ";
		Map params=new HashMap();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		if("110000".equals(userBean.getUnNo())){
			//为总公司
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			//为总公司并且是部门---查询全部
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and p.unno in(select unno from SYS_UNIT where UPPER_UNIT=:unno) " ;
			params.put("unno",userBean.getUnNo());
		}
		if(cptm.getUnno()!=null&&!"".equals(cptm.getUnno())){
			sql+= " and p.unno =:unno1 ";
			params.put("unno1",cptm.getUnno());
		}
		if(cptm!=null&&cptm.getTempName()!=null&&!cptm.getTempName().equals("")){
			sql+=" and p.TEMPNAME=:tempName ";
			params.put("tempName",cptm.getTempName());
		}
		sql+=" ) group by unno,un_name,TEMPNAME,to_char(STARTDATE,'yyyy-MM-dd'),to_char(ENDDATE,'yyyy-MM-dd') " ;
		sql+=" order by STARTDATE desc";
		String sqlCount = "select count(1) from ("+sql+")";
		Integer counts = checkUnitProfitMicroLogDao.querysqlCounts2(sqlCount, params);
		List<Map<String, String>> list = checkUnitProfitMicroLogDao.queryObjectsBySqlList(sql, params, cptm.getPage(), cptm.getRows());
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(counts);
		return dgb;
	}

	@Override
	public CheckProfitTemplateMicroBean getMicroProfitMdLogDetail(CheckProfitTemplateMicroBean cptm){
		CheckProfitTemplateMicroBean cpt = new CheckProfitTemplateMicroBean();
		String sql = "select * " +
				" from CHECK_MICRO_PROFITTEMPLATE_LOG p where 1=1 and p.merchanttype in (1,2,3) " +
				" and p.unno=:unno" +
				" and p.startdate>=to_date(:startDate,'yyyy-MM-dd') and p.startdate<to_date(:startDate,'yyyy-MM-dd')+1 ";
		Map params = new HashMap();
		params.put("unno",cptm.getUnno());
		params.put("startDate",cptm.getTxnDay());
//		List<Map<String, Object>> list = checkUnitProfitMicroLogDao.queryObjectsBySqlListMap2(sql, params);
		List<CheckProfitTemplateMicroLogModel> logModelList = checkUnitProfitMicroLogDao.queryObjectsBySqlList(sql,params,CheckProfitTemplateMicroLogModel.class);
		for (int i = 0; i < logModelList.size(); i++) {
			CheckProfitTemplateMicroLogModel cc = logModelList.get(i);
			if (cc.getSettMethod() == "000000" || "000000".equals(cc.getSettMethod())) {
				cpt.setStartAmount(cc.getStartAmount());
				cpt.setEndAmount(cc.getEndAmount());
				cpt.setRuleThreshold(cc.getRuleThreshold());
				cpt.setCreditBankRate(cc.getCreditBankRate());
				cpt.setCashAmt(cc.getCashAmt());
				cpt.setCashRate(cc.getCashRate());
				cpt.setScanRate(cc.getScanRate());
				cpt.setScanRate1(cc.getScanRate1());
				cpt.setScanRate2(cc.getScanRate2());
				cpt.setCashAmt2(cc.getCashAmt1());
				cpt.setCashAmt3(cc.getCashAmt2());
				cpt.setProfitPercent(cc.getProfitPercent());
				cpt.setProfitPercent3(cc.getProfitPercent1());
			}
			if ((cc.getSettMethod() == "100000" || "100000".equals(cc.getSettMethod())) && cc.getMerchantType() == 2) {
				cpt.setCreditBankRate1(cc.getCreditBankRate());
				cpt.setCashAmt1(cc.getCashAmt());
				cpt.setProfitPercent1(cc.getProfitPercent());
				cpt.setStartAmount2(cc.getStartAmount());
				cpt.setEndAmount2(cc.getEndAmount());
				cpt.setRuleThreshold2(cc.getRuleThreshold());
				//20200226-ztt添加扫码1000上下转账费
				cpt.setCashamtunder(cc.getCashamtunder());
				cpt.setCashamtabove(cc.getCashamtabove());
			} else if ((cc.getSettMethod() == "100000" || "100000".equals(cc.getSettMethod())) && cc.getMerchantType() == 3) {
				cpt.setCreditBankRate2(cc.getCreditBankRate());
				cpt.setProfitPercent2(cc.getProfitPercent());
			}
			cpt.setCloudQuickRate(cc.getCloudQuickRate());
			cpt.setTempName(cc.getTempName());
		}
		return cpt;
	}

    @Override
    public List<CheckProfitTemplateMicroBean> exportMicroProfitMdLog(CheckProfitTemplateMicroBean cptt,UserBean userBean,Integer type){
		List<CheckProfitTemplateMicroBean> result = new ArrayList<CheckProfitTemplateMicroBean>();
		// TODO @author:lxg-20190927 待优化
		String sql = "select unno,un_name,TEMPNAME,to_char(STARTDATE,'yyyy-MM-dd') STARTDATE,to_char(ENDDATE,'yyyy-MM-dd') ENDDATE from ( " +
				" select p.*,(select s.un_name from sys_unit s where s.unno=p.unno) un_name from CHECK_MICRO_PROFITTEMPLATE_LOG p " +
				" where 1=1 and p.merchanttype in (1,2,3) ";
		Map params=new HashMap();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		if("110000".equals(userBean.getUnNo())){
			//为总公司
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			//为总公司并且是部门---查询全部
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and p.unno in(select unno from SYS_UNIT where UPPER_UNIT=:unno) " ;
			params.put("unno",userBean.getUnNo());
		}
		if(cptt.getUnno()!=null&&!"".equals(cptt.getUnno())){
			sql+= " and p.unno =:unno1 ";
			params.put("unno1",cptt.getUnno());
		}
		if(cptt!=null&&cptt.getTempName()!=null&&!cptt.getTempName().equals("")){
			sql+=" and p.TEMPNAME=:tempName ";
			params.put("tempName",cptt.getTempName());
		}
		
		if(type==1) {
			sql+=" and p.startdate<to_date('20200101000000', 'yyyymmddhh24miss')";
		}else {
			sql+=" and p.startdate>=to_date('20200101000000', 'yyyymmddhh24miss')";
		}
		
		sql+=" ) group by unno,un_name,TEMPNAME,to_char(STARTDATE,'yyyy-MM-dd'),to_char(ENDDATE,'yyyy-MM-dd') " ;
		List<Map<String, String>> list = checkUnitProfitMicroLogDao.queryObjectsBySqlListMap(sql, params);
		CheckProfitTemplateMicroBean param=new CheckProfitTemplateMicroBean();
		for(Map t:list){
			String unno = String.valueOf(t.get("UNNO"));
			String unName = String.valueOf(t.get("UN_NAME"));
			String tempName = String.valueOf(t.get("TEMPNAME"));
			String startDate = String.valueOf(t.get("STARTDATE"));
			String enddate = t.get("ENDDATE")==null?null:String.valueOf(t.get("ENDDATE"));
			param.setUnno(unno);
			param.setTxnDay(startDate);
			CheckProfitTemplateMicroBean bean=getMicroProfitMdLogDetail(param);
			bean.setMid(unName);
			bean.setTxnDay(startDate);
			bean.setTxnDay1(enddate);
			bean.setUnno(unno);
			result.add(bean);
		}
		return result;
    }
	
	
	@Override
	public DataGridBean getMicroProfitSytLog(CheckProfitTemplateMicroBean cptm, UserBean userBean) {
		String sql="select a.*,(select s.un_name from sys_unit s where s.unno=a.unno) un_name from"+
				" (select l.tempname,l.unno,l.startdate,l.enddate from check_micro_profittemplate_log l "+
				" where l.merchanttype = 5";
		Map params=new HashMap();
        UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
        //为总公司
        if("110000".equals(userBean.getUnNo())){
        }
        //为总公司并且是部门---查询全部
        else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
            UnitModel parent = unitModel.getParent();
            if("110000".equals(parent.getUnNo())){
            }
        }else{
        	sql+=" and l.unno in(select unno from SYS_UNIT where UPPER_UNIT=:unno) " ;
			params.put("unno",userBean.getUnNo());
        }
        if(cptm.getUnno()!=null&&!"".equals(cptm.getUnno())){
			sql+= " and l.unno =:unno1 ";
			params.put("unno1",cptm.getUnno());
		}
        if(cptm!=null&&cptm.getTempName()!=null&&!cptm.getTempName().equals("")){
        	sql+=" and l.TEMPNAME= :tempName ";
			params.put("tempName",cptm.getTempName());
        }
        sql+=" and MatainType in ('A','M','P') group by l.tempname,l.unno,l.startdate,l.enddate)a order by startdate desc";
        String count="select count(*) from ("+sql+")";
        
        List<Map<String,String>> list=checkUnitProfitMicroLogDao.queryObjectsBySqlList(sql,params,cptm.getPage(),cptm.getRows());
        BigDecimal counts = checkUnitProfitMicroLogDao.querysqlCounts(count,params);
        DataGridBean dgd = new DataGridBean();
        dgd.setTotal(counts.intValue());//查询数据库总条数
        dgd.setRows(list);
        return dgd;
	}

	@Override
	public List<Map<String, Object>> exportMicroProfitSytLog(CheckProfitTemplateMicroBean cptm, UserBean userBean) {
		String sql = "select p.*,(select s.un_name from sys_unit s where s.unno=p.unno) un_name from CHECK_MICRO_PROFITTEMPLATE_LOG p " +
                " where 1=1 and p.merchanttype=5 ";
		Map params=new HashMap();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		if("110000".equals(userBean.getUnNo())){
			//为总公司
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			//为总公司并且是部门---查询全部
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and p.unno in(select unno from SYS_UNIT where UPPER_UNIT=:unno) " ;
			params.put("unno",userBean.getUnNo());
		}
		if(cptm.getUnno()!=null&&!"".equals(cptm.getUnno())){
			sql+= " and p.unno =:unno1 ";
			params.put("unno1",cptm.getUnno());
		}
		if(cptm!=null&&cptm.getTempName()!=null&&!cptm.getTempName().equals("")){
			sql+=" and p.TEMPNAME=:tempName ";
			params.put("tempName",cptm.getTempName());
		}
		sql+=" order by p.startdate desc";
		List<Map<String,Object>> list = checkUnitProfitMicroLogDao.queryObjectsBySqlListMap2(sql, params);
		return list;
	}
	
	@Override
	public DataGridBean getMicroProfitMposTemplateLog(CheckProfitTemplateMicroBean cptm, UserBean userBean) {
		String sql="select a.*,(select s.un_name from sys_unit s where s.unno=a.unno) un_name from"+
				" (select l.tempname,l.unno,l.startdate,l.enddate from check_micro_profittemplate_log l "+
				" where l.merchanttype = 6";
		Map params=new HashMap();
        UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
        //为总公司
        if("110000".equals(userBean.getUnNo())){
        }
        //为总公司并且是部门---查询全部
        else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
            UnitModel parent = unitModel.getParent();
            if("110000".equals(parent.getUnNo())){
            }
        }else{
        	sql+=" and l.unno in(select unno from SYS_UNIT where UPPER_UNIT=:unno) " ;
			params.put("unno",userBean.getUnNo());
        }
        if(cptm.getUnno()!=null&&!"".equals(cptm.getUnno())){
			sql+= " and l.unno =:unno1 ";
			params.put("unno1",cptm.getUnno());
		}
        if(cptm!=null&&cptm.getTempName()!=null&&!cptm.getTempName().equals("")){
        	sql+=" and l.TEMPNAME= :tempName ";
			params.put("tempName",cptm.getTempName());
        }
        sql+=" and MatainType in ('A','M','P') group by l.tempname,l.unno,l.startdate,l.enddate)a order by startdate desc";
        String count="select count(*) from ("+sql+")";
        
        List<Map<String,String>> list=checkUnitProfitMicroLogDao.queryObjectsBySqlList(sql,params,cptm.getPage(),cptm.getRows());
        BigDecimal counts = checkUnitProfitMicroLogDao.querysqlCounts(count,params);
        DataGridBean dgd = new DataGridBean();
        dgd.setTotal(counts.intValue());//查询数据库总条数
        dgd.setRows(list);
        return dgd;
	}
	

	@Override
	public List<Map<String, Object>> queryupdateMposProfitmicroLogDetail(CheckProfitTemplateMicroBean cptm,
			UserBean userBean) {
		List<Map<String, Object>> lst = new ArrayList<Map<String,Object>>();
		
		String sql="select t.* from CHECK_MICRO_PROFITTEMPLATE_LOG t where t.UNNO='"+cptm.getUnno()+"' and t.MatainType in ('A','M','P')"+
				" and t.merchanttype = 6"+
				" and t.tempname = '"+cptm.getTempName()+"'"+
				" and t.startdate >= to_date(substr('"+cptm.getTxnDay()+"',1,10), 'yyyy-MM-dd')"+
				" and t.startdate < to_date(substr('"+cptm.getTxnDay()+"',1,10), 'yyyy-MM-dd') + 1";
		if(!cptm.getTxnDay1().isEmpty() && !"undefined".equals(cptm.getTxnDay1()) ) {
			sql+= " and t.enddate = to_date('"+cptm.getTxnDay1()+"', 'yyyy-MM-dd HH24:mi:ss')";
		}else {
			sql += " and t.enddate is null";
		}
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		if("110000".equals(userBean.getUnNo())){
        }
        //为总公司并且是部门---查询全部
        else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
            UnitModel parent = unitModel.getParent();
            if("110000".equals(parent.getUnNo())){
            }
        }else{
        	sql+=" and t.unno in(select unno from SYS_UNIT where UPPER_UNIT='"+userBean.getUnNo()+"') " ;
        }
        List<CheckProfitTemplateMicroLogModel> list=checkUnitProfitMicroLogDao.queryObjectsBySqlList(sql, null,CheckProfitTemplateMicroLogModel.class );
        for(int i=0;i<list.size();i++){
        	CheckProfitTemplateMicroLogModel cc =list.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("profitRule",cc.getProfitRule());
            
            map.put("creditBankRate", null==cc.getCreditBankRate()?"":new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100")));
            map.put("cashRate", null==cc.getCashRate()?"":cc.getCashRate());
            map.put("ruleThreshold", null==cc.getRuleThreshold()?"":new BigDecimal(cc.getRuleThreshold()+"").multiply(new BigDecimal("100")));
            map.put("startAmount", null==cc.getStartAmount()?"":cc.getStartAmount());
            map.put("scanRate", null==cc.getScanRate()?"":new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
            map.put("cashAmt1", null==cc.getCashAmt1()?"":cc.getCashAmt1());
            map.put("scanRate1", null==cc.getScanRate1()?"":new BigDecimal(cc.getScanRate1()+"").multiply(new BigDecimal("100")));
            map.put("profitPercent1", null==cc.getProfitPercent1()?"":cc.getProfitPercent1());
            map.put("scanRate2", null==cc.getScanRate2()?"":new BigDecimal(cc.getScanRate2()+"").multiply(new BigDecimal("100")));
            map.put("cashAmt2", null==cc.getCashAmt2()?"":cc.getCashAmt2());
            map.put("subRate", null==cc.getSubRate()?"":new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
            map.put("cashAmt", null==cc.getCashAmt()?"":cc.getCashAmt());
            map.put("cloudQuickRate", null==cc.getCloudQuickRate()?"":new BigDecimal(cc.getCloudQuickRate()+"").multiply(new BigDecimal("100")));
            
            map.put("huabeiFee", null==cc.getHuabeiFee()?"":cc.getHuabeiFee());
            map.put("huabeiRate", null==cc.getHuabeiRate()?"":new BigDecimal(cc.getHuabeiRate()+"").multiply(new BigDecimal("100")));
            String ishuabei = querySubTypeByPlusProfitRule(cc.getProfitRule());
            if(ishuabei!=null) {
            	map.put("ishuabei", 1);
            }else {
            	map.put("ishuabei", 2);
            }
            lst.add(map);
            }
        return lst;
	}
	

	@Override
	public List<Map<String, Object>> exportMicroProfitMposTempLateLog(CheckProfitTemplateMicroBean cptm,
			UserBean userBean) {
		String sql = "select p.*,(select s.un_name from sys_unit s where s.unno=p.unno) un_name from CHECK_MICRO_PROFITTEMPLATE_LOG p " +
                " where 1=1 and p.merchanttype=6 ";
		Map params=new HashMap();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		if("110000".equals(userBean.getUnNo())){
			//为总公司
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			//为总公司并且是部门---查询全部
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and p.unno in(select unno from SYS_UNIT where UPPER_UNIT=:unno) " ;
			params.put("unno",userBean.getUnNo());
		}
		if(cptm.getUnno()!=null&&!"".equals(cptm.getUnno())){
			sql+= " and p.unno =:unno1 ";
			params.put("unno1",cptm.getUnno());
		}
		if(cptm!=null&&cptm.getTempName()!=null&&!cptm.getTempName().equals("")){
			sql+=" and p.TEMPNAME=:tempName ";
			params.put("tempName",cptm.getTempName());
		}
		sql+=" order by p.startdate desc";
		List<Map<String,Object>> list = checkUnitProfitMicroLogDao.queryObjectsBySqlListMap2(sql, params);
		return list;
	}

	@Override
	public boolean hasRebateTypeByYiDaiUnno(int type,String rebateType,String unno){
		boolean flag=false;
		if(StringUtils.isEmpty(rebateType) || StringUtils.isEmpty(unno)){
			return flag;
		}
		Map params = new HashMap();
		if(type==1){
			String sql="select count(1) from hrt_unno_cost t" +
					" where t.machine_type = 1 and t.txn_type = 1 and t.txn_detail in (1, 2) and t.status=1 and t.debit_rate is not null and t.unno=:unno";
			params.put("unno",unno);
			Integer count = checkUnitProfitMicroLogDao.querysqlCounts2(sql,params);
			if(count>0 && "0".equals(rebateType)){
				flag=true;
			}else{
				flag=false;
			}
		}else if(type==2){
			String sql="select count(1) from hrt_unno_cost t" +
					" where t.machine_type = 1 and t.txn_type = 1 and t.status=1 and t.txn_detail=:txn_detail and t.debit_rate is not null and t.unno=:unno";
			params.put("txn_detail",Integer.parseInt(rebateType));
			params.put("unno",unno);
			Integer count = checkUnitProfitMicroLogDao.querysqlCounts2(sql,params);
			if(count>0){
				flag=true;
			}else{
				flag=false;
			}
		}
		return flag;
	}

	@Override
	public boolean hasRebateTypeByUnno(int type,String rebateType,String unno){
		boolean flag=false;
		if(StringUtils.isEmpty(rebateType) || StringUtils.isEmpty(unno)){
			return flag;
		}
		Map params = new HashMap();
		if(type==1){
			String mposSql="select count(1) from check_unit_profitemplate a,check_micro_profittemplate b" +
					" where a.aptid=b.aptid and a.status=1 and b.merchanttype in (1,2,3) and a.unno=:unno";
			params.put("unno",unno);
			Integer count = checkUnitProfitMicroLogDao.querysqlCounts2(mposSql,params);
			// 小于活动20的,都传0,认为mpos产品
			if(count>0 && "0".equals(rebateType)){
				flag=true;
			}else{
				flag=false;
			}
		}else if(type==5){
			String sytSql="select count(1) from check_unit_profitemplate a,check_micro_profittemplate b" +
					" where a.aptid=b.aptid and a.status=1 and b.merchanttype in (5) and a.unno=:unno";
			params.put("unno",unno);
			Integer count = checkUnitProfitMicroLogDao.querysqlCounts2(sytSql,params);
			// 小于活动20的,都传0,认为mpos产品
			if(count>0 && ".20.21.".contains("."+rebateType+".")){
				flag=true;
			}else{
				flag=false;
			}
		}else if(type==6){
			String plusSql="select count(1) from check_unit_profitemplate a,check_micro_profittemplate b" +
					" where a.aptid=b.aptid and a.status=1 and b.merchanttype in (6) and a.unno=:unno and b.profitrule=:profitrule";
			params.put("unno",unno);
			params.put("profitrule",Integer.parseInt(rebateType));
			Integer count = checkUnitProfitMicroLogDao.querysqlCounts2(plusSql,params);
			// 小于活动20的,都传0,认为mpos产品
			if(count>0){
				flag=true;
			}else{
				flag=false;
			}
		}
		return flag;
	}
	
	/**
     * Plus模板校验(校验绑定模板费率不能为空)
     * @return
     */
    @Override
    public String validatePlusMposTemplaIsNull(CheckProfitTemplateMicroBean cptm, UserBean user){
		if(user.getUnitLvl()>=2) {
			String str = cptm.getTempname();
			Integer rebateType=null;
			JSONArray json1 = JSONArray.parseArray(str);
			for(int i=0;i<json1.size();i++) {
				String profitRule = JSONObject.parseObject(json1.getString(i)).getString("profitRule");
				//微信1000以上0.38费率
				String creditBankRate = JSONObject.parseObject(json1.getString(i)).getString("creditBankRate");
				if(creditBankRate == null) {
					return "扫码费率1000以上（终端0.38）费率为空";
				}
				//微信1000以上0.38转账费
				String cashRate = JSONObject.parseObject(json1.getString(i)).getString("cashRate");
				if(cashRate == null) {
					return "扫码费率1000以上（终端0.38）转账费为空";
				}
				//微信1000以上0.45费率
				String ruleThreshold = JSONObject.parseObject(json1.getString(i)).getString("ruleThreshold");
				if(ruleThreshold == null) {
					return "扫码费率1000以上（终端0.45）费率为空";
				}
				//微信1000以上0.45转账费
				String startAmount = JSONObject.parseObject(json1.getString(i)).getString("startAmount");
				if(startAmount == null) {
					return "扫码费率1000以上（终端0.45）转账费为空";
				}
				//微信1000以下费率
				String scanRate = JSONObject.parseObject(json1.getString(i)).getString("scanRate");
				if(scanRate == null) {
					return "扫码费率1000以下（终端0.38）费率为空";
				}
				//微信1000以下转账费
				String cashAmt1 = JSONObject.parseObject(json1.getString(i)).getString("cashAmt1");
				if(cashAmt1 == null) {
					return "扫码费率1000以下（终端0.38）转账费为空";
				}
				//支付宝上费率
				String scanRate1 = JSONObject.parseObject(json1.getString(i)).getString("scanRate1");
				if(scanRate1 == null) {
					return "扫码费率1000以下（终端0.45）费率为空";
				}
				//支付宝转账费
				String profitPercent1 = JSONObject.parseObject(json1.getString(i)).getString("profitPercent1");
				if(profitPercent1 == null) {
					return "扫码费率1000以下（终端0.45）转账费为空";
				}
				//二维码费率
				String scanRate2 = JSONObject.parseObject(json1.getString(i)).getString("scanRate2");
				if(scanRate2 == null) {
					return "银联二维码费率为空";
				}
				//二维码转账费
				String cashAmt2 = JSONObject.parseObject(json1.getString(i)).getString("cashAmt2");
				if(cashAmt2 == null) {
					return "银联二维码转账费为空";
				}
				//秒到刷卡费率
				String subRate = JSONObject.parseObject(json1.getString(i)).getString("subRate");
				if(subRate == null) {
					return "刷卡费率为空";
				}
				//秒到刷卡转账费
				String cashAmt = JSONObject.parseObject(json1.getString(i)).getString("cashAmt");
				if(cashAmt == null) {
					return "刷卡转账费为空";
				}
				//云闪付费率
				String cloudQuickRate = JSONObject.parseObject(json1.getString(i)).getString("cloudQuickRate");
				if(cloudQuickRate == null) {
					return "云闪付费率为空";
				}
				
				//花呗
				String subtype = querySubTypeByPlusProfitRule(Integer.parseInt(profitRule));
				String huabeiRate = JSONObject.parseObject(json1.getString(i)).getString("huabeiRate");
				String huabeiFee = JSONObject.parseObject(json1.getString(i)).getString("huabeiFee");
				if(subtype!=null && "2".equals(subtype)) {
					if(huabeiRate==null) {
						return "花呗费率为空";
					}
					if(huabeiFee==null) {
						return "花呗转账费为空";
					}
				}
			}
		}
	    return null;
    }
    
    /**
     * 收银台模板校验(绑定模板成本不能为空值)
     * @param cptm
     * @param user
     * @return
     */
    @Override
    public String validateSytTemplateIsNull(CheckProfitTemplateMicroBean cptm, UserBean user){
    	if(user.getUnitLvl()>=2) {
    		if(cptm.getCreditBankRate() == null) {
    			return "扫码1000以上（终端0.38）费率为空";
    		}
    		if(cptm.getCashRate() == null) {
    			return "扫码1000以上（终端0.38）转账费为空";
    		}
    		if(cptm.getScanRate() == null) {
    			return "扫码1000以上（终端0.45）费率为空";
    		}
    		if(cptm.getProfitPercent1() == null) {
    			return "扫码1000以上（终端0.45）转账费为空";
    		}
    		if(cptm.getSubRate() == null) {
    			return "扫码1000以下（终端0.38）费率为空";
    		}
    		if(cptm.getCashAmt() == null) {
    			return "扫码1000以下（终端0.38）转账费为空";
    		}
    		if(cptm.getScanRate1() == null) {
    			return "扫码1000以下（终端0.45）费率为空";
    		}
    		if(cptm.getCashAmt1() == null) {
    			return "扫码1000以下（终端0.45）转账费为空";
    		}
    		if(cptm.getScanRate2() == null) {
    			return "银联二维码费率为空";
    		}
    		if(cptm.getCashAmt2() == null) {
    			return "银联二维码转账费为空";
    		}
        }
        return null;
    }
	
    @SuppressWarnings("unchecked")
	@Override
	public DataGridBean getProfitUnitGodesForSalesForSpecial(UserBean user,String nameCode) throws Exception{			
		String sql = "SELECT t.UNNO,t.UN_NAME FROM SYS_UNIT  t WHERE 1=1  "+
				//" and (t.upper_unit=:unno or t.unno=:unno )";
				" start with t.unno = '"+user.getUnNo()+"'"+
				" connect by prior t.unno = t.upper_unit";
		DataGridBean dgd = new DataGridBean();
		//Map map = new HashMap(16);
		//map.put("unno",user.getUnNo());
		sql+=" order by t.UN_NAME ASC";
		List<Map<String,String>> proList = checkUnitProfitMicroDao.queryObjectsBySqlListMap(sql,null);
		dgd.setTotal(proList.size());
		dgd.setRows(proList);
		
		return dgd;
	}
    
    
    @Override
    public List<CheckProfitTemplateMicroBean> exportMicroProfitMdLog0312(CheckProfitTemplateMicroBean cptt,UserBean userBean,Integer type){
		String sql = " select * from ("+
				" SELECT t1.*,t2.creditbankrate1,t2.cashamt1,t2.profitpercent1,"+
				" t2.startamount2,t2.endamount2,t2.rulethreshold2,t2.cashamtabove,t2.cashamtunder,t3.creditbankrate2,t3.profitpercent2,"+
				" (SELECT u.un_name FROM  sys_unit u  where u.unno = t1.unno) unnoname"+
				" FROM"+
				" (SELECT l.startamount,l.endamount,l.rulethreshold,l.creditbankrate,l.cashamt,l.cashrate,l.scanrate,l.scanrate1,l.scanrate2,l.cashamt1 cashamt2,"+
				" l.cashamt2 cashamt3,l.profitpercent,l.profitpercent1 profitpercent3,l.cloudquickrate,l.tempname,l.unno,l.enddate,l.startdate startdateold,	a.startdate1 startdate"+
				" FROM  check_micro_profittemplate_log l,(SELECT b.unno unno1,b.un_name,b.TEMPNAME TEMPNAME1,b.STARTDATE STARTDATE1,"+
				" b.ENDDATE ENDDATE1 FROM (select unno,un_name,TEMPNAME,to_char(STARTDATE, 'yyyy-MM-dd') STARTDATE,"+
				" to_char(ENDDATE, 'yyyy-MM-dd') ENDDATE from (select p.*,(select s.un_name from  sys_unit s where s.unno = p.unno) un_name"+
				" from  CHECK_MICRO_PROFITTEMPLATE_LOG p where 1 = 1 and p.merchanttype in (1, 2, 3)) group by unno,un_name,TEMPNAME,to_char(STARTDATE, 'yyyy-MM-dd') ,"+
				" to_char(ENDDATE, 'yyyy-MM-dd') )b) a where l.unno = a.unno1 and l.tempname = a.TEMPNAME1 and to_char(l.STARTDATE, 'yyyy-MM-dd') = a.startdate1"+
				" and l.settmethod = '000000')t1,"+
				" (SELECT l.creditbankrate creditbankrate1,l.cashamt,cashamt1,l.profitpercent profitpercent1,l.startamount startamount2,"+
				" l.endamount endamount2,l.rulethreshold rulethreshold2,l.cashamtabove,l.cashamtunder,l.unno,a.startdate1 startdate"+
				" FROM  check_micro_profittemplate_log l,(SELECT b.unno unno1,b.un_name,b.TEMPNAME TEMPNAME1,b.STARTDATE STARTDATE1,b.ENDDATE ENDDATE1 FROM (select unno,"+
				" un_name,TEMPNAME,to_char(STARTDATE, 'yyyy-MM-dd') STARTDATE,to_char(ENDDATE, 'yyyy-MM-dd') ENDDATE"+
				" from (select p.*,(select s.un_name from  sys_unit s where s.unno = p.unno) un_name"+
				" from  CHECK_MICRO_PROFITTEMPLATE_LOG p where 1 = 1 and p.merchanttype in (1, 2, 3)"+
				" )group by unno,un_name,TEMPNAME,to_char(STARTDATE, 'yyyy-MM-dd') ,to_char(ENDDATE, 'yyyy-MM-dd'))b) a"+
				" where l.unno = a.unno1 and l.tempname = a.TEMPNAME1 and to_char(l.STARTDATE, 'yyyy-MM-dd') = a.startdate1"+
				" and l.settmethod = '100000' and l.merchanttype = '2')t2,"+
				" (SELECT l.creditbankrate creditbankrate2,l.profitpercent profitpercent2,l.unno,a.startdate1 startdate"+
				" FROM  check_micro_profittemplate_log l,(SELECT b.unno unno1,b.un_name,"+
				" b.TEMPNAME TEMPNAME1,b.STARTDATE STARTDATE1,b.ENDDATE ENDDATE1 FROM (select unno,"+
				" un_name,TEMPNAME,to_char(STARTDATE, 'yyyy-MM-dd') STARTDATE,to_char(ENDDATE, 'yyyy-MM-dd') ENDDATE"+
				" from (select p.*,(select s.un_name from  sys_unit s where s.unno = p.unno) un_name"+
				" from  CHECK_MICRO_PROFITTEMPLATE_LOG p where 1 = 1 and p.merchanttype in (1, 2, 3)"+
				" )group by unno,un_name,TEMPNAME,to_char(STARTDATE, 'yyyy-MM-dd') ,to_char(ENDDATE, 'yyyy-MM-dd'))b) a"+
				" where l.unno = a.unno1 and l.tempname = a.TEMPNAME1 and to_char(l.STARTDATE, 'yyyy-MM-dd') = a.startdate1"+
				" and l.settmethod = '100000' and l.merchanttype = '3')t3"+
				" where t1.unno = t2.unno and t2.unno = t3.unno and t1.startdate = t2.startdate and t2.startdate = t3.startdate"+
				" )j where 1=1 ";
		
		Map params=new HashMap();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		if("110000".equals(userBean.getUnNo())){
			//为总公司
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			//为总公司并且是部门---查询全部
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and j.unno in(select unno from SYS_UNIT where UPPER_UNIT=:unno) " ;
			params.put("unno",userBean.getUnNo());
		}
		if(cptt.getUnno()!=null&&!"".equals(cptt.getUnno())){
			sql+= " and j.unno =:unno1 ";
			params.put("unno1",cptt.getUnno());
		}
		if(cptt!=null&&cptt.getTempName()!=null&&!cptt.getTempName().equals("")){
			sql+=" and j.TEMPNAME=:tempName ";
			params.put("tempName",cptt.getTempName());
		}
		
		if(type==1) {
			sql+=" and j.startdateold<to_date('20200101000000', 'yyyymmddhh24miss')";
		}else {
			sql+=" and j.startdateold>=to_date('20200101000000', 'yyyymmddhh24miss')";
		}
		
		List<Map<String,Object>> list = checkUnitProfitMicroLogDao.queryObjectsBySqlListMap2(sql, params);
		List<CheckProfitTemplateMicroBean> result = new ArrayList<CheckProfitTemplateMicroBean>();
		for(Map t:list){
			CheckProfitTemplateMicroBean param=new CheckProfitTemplateMicroBean();
			String unno = String.valueOf(t.get("UNNO"));
			String unName = String.valueOf(t.get("UNNONAME"));
			String tempName = String.valueOf(t.get("TEMPNAME"));
			String startDate = String.valueOf(t.get("STARTDATEOLD"));
			String enddate = t.get("ENDDATE")==null?null:String.valueOf(t.get("ENDDATE"));
			
			
			param.setStartAmount(t.get("STARTAMOUNT")==null?null:Double.valueOf(t.get("STARTAMOUNT").toString()));
			param.setEndAmount(t.get("ENDAMOUNT")==null?null:Double.valueOf(t.get("ENDAMOUNT").toString()));
			param.setRuleThreshold(t.get("RULETHRESHOLD")==null?null:Double.valueOf(t.get("RULETHRESHOLD").toString()));
			param.setCreditBankRate(t.get("CREDITBANKRATE")==null?null:Double.valueOf(t.get("CREDITBANKRATE").toString()));
			param.setCashAmt(t.get("CASHAMT")==null?null:Double.valueOf(t.get("CASHAMT").toString()));
			param.setCashRate(t.get("CASHRATE")==null?null:Double.valueOf(t.get("CASHRATE").toString()));
			param.setScanRate(t.get("SCANRATE")==null?null:Double.valueOf(t.get("SCANRATE").toString()));
			param.setScanRate1(t.get("SCANRATE1")==null?null:Double.valueOf(t.get("SCANRATE1").toString()));
			param.setScanRate2(t.get("SCANRATE2")==null?null:Double.valueOf(t.get("SCANRATE2").toString()));
			param.setCashAmt2(t.get("CASHAMT2")==null?null:Double.valueOf(t.get("CASHAMT2").toString()));
			param.setCashAmt3(t.get("CASHAMT3")==null?null:Double.valueOf(t.get("CASHAMT3").toString()));
			param.setProfitPercent(t.get("PROFITPERCENT")==null?null:Double.valueOf(t.get("PROFITPERCENT").toString()));
			param.setProfitPercent3(t.get("PROFITPERCENT3")==null?null:Double.valueOf(t.get("PROFITPERCENT3").toString()));
			param.setCloudQuickRate(t.get("CLOUDQUICKRATE")==null?null:Double.valueOf(t.get("CLOUDQUICKRATE").toString()));
			param.setCreditBankRate1(t.get("CREDITBANKRATE1")==null?null:Double.valueOf(t.get("CREDITBANKRATE1").toString()));
			param.setCashAmt1(t.get("CASHAMT1")==null?null:Double.valueOf( t.get("CASHAMT1").toString()));
			param.setProfitPercent1(t.get("PROFITPERCENT1")==null?null:Double.valueOf(t.get("PROFITPERCENT1").toString()));
			param.setStartAmount2(t.get("STARTAMOUNT2")==null?null:Double.valueOf(t.get("STARTAMOUNT2").toString()));
			param.setEndAmount2(t.get("ENDAMOUNT2")==null?null:Double.valueOf( t.get("ENDAMOUNT2").toString()));
			param.setRuleThreshold2(t.get("RULETHRESHOLD2")==null?null:Double.valueOf( t.get("RULETHRESHOLD2").toString()));
			param.setCashamtabove(t.get("CASHAMTABOVE")==null?null:Double.valueOf(t.get("CASHAMTABOVE").toString()));
			param.setCashamtunder(t.get("CASHAMTUNDER")==null?null:Double.valueOf(t.get("CASHAMTUNDER").toString()));
			param.setCreditBankRate2(t.get("CREDITBANKRATE2")==null?null:Double.valueOf( t.get("CREDITBANKRATE2").toString()));
			param.setProfitPercent2(t.get("PROFITPERCENT2")==null?null:Double.valueOf( t.get("PROFITPERCENT2").toString()));
			
			param.setUnno(unno);
			param.setMid(unName);
			param.setTxnDay(startDate);
			param.setTxnDay1(enddate);
			param.setTempName(tempName);
			result.add(param);
		}
		return result;
    }
    
	public static void main(String[] args) {
		double a=1.1-0.2-0.9;
		System.out.println(a);
		String aa=null;
		System.out.println(aa!=null);

	}

	@Override
	public DataGridBean listRebateRate() {
		DataGridBean dgb = new DataGridBean();
		String sql ="select * from bill_purconfigure t where t.type=3 and t.valuestring='rate' and t.prod='syt'";
		List<Map<String,String>> list = checkUnitProfitMicroDao.queryObjectsBySqlListMap(sql, null);
		dgb.setRows(list);
		return dgb;
	}

	@Override
	public void addHybCashTempla(CheckProfitTemplateMicroBean cptm,UserBean user) {

        String tempName = cptm.getTempName();
        Map<String, String> map = new HashMap<String, String>();
        String str = cptm.getTempname();
        JSONArray json1 = JSONArray.parseArray(str);
        for(int i = 0;i<json1.size();i++){
        	//扫码1000以上（终端0.38）费率
        	String creditBankRate = JSONObject.parseObject(json1.getString(i)).getString("creditBankRate");
        	//扫码1000以上（终端0.38）转账费：
        	String cashRate = JSONObject.parseObject(json1.getString(i)).getString("cashRate");
        	//扫码1000以上（终端0.45）费率：
        	String ruleThreshold = JSONObject.parseObject(json1.getString(i)).getString("ruleThreshold");
        	//扫码1000以上（终端0.45）转账费：
        	String startAmount = JSONObject.parseObject(json1.getString(i)).getString("startAmount");
        	//扫码1000以下（终端0.38）费率
        	String scanRate = JSONObject.parseObject(json1.getString(i)).getString("scanRate");
        	//扫码1000以下（终端0.38）转账费：
        	String cashAmt1 = JSONObject.parseObject(json1.getString(i)).getString("cashAmt1");
        	//扫码1000以下（终端0.45）费率：
        	String scanRate1 = JSONObject.parseObject(json1.getString(i)).getString("scanRate1");
        	//扫码1000以下（终端0.45）转账费
        	String profitPercent1 = JSONObject.parseObject(json1.getString(i)).getString("profitPercent1");
        	//银联二维码费率
        	String scanRate2 = JSONObject.parseObject(json1.getString(i)).getString("scanRate2");
        	//银联二维码转账费
        	String cashAmt2 = JSONObject.parseObject(json1.getString(i)).getString("cashAmt2");
        	//花呗费率
        	String huabeiRate = JSONObject.parseObject(json1.getString(i)).getString("huabeiRate");
        	//花呗转账费
        	String huabeiFee = JSONObject.parseObject(json1.getString(i)).getString("huabeiFee");
       	
           
        	String profitRule = JSONObject.parseObject(json1.getString(i)).getString("profitRule");
            if(map.containsKey(profitRule)){
                continue;
            }
            map.put(profitRule, "");
            CheckProfitTemplateMicroBean bean = new CheckProfitTemplateMicroBean();
            
            bean.setCreditBankRate(Double.parseDouble(creditBankRate));
            bean.setCashRate(Double.parseDouble(cashRate));
            bean.setRuleThreshold(Double.parseDouble(ruleThreshold));
            bean.setStartAmount(Double.parseDouble(startAmount));
            bean.setScanRate(Double.parseDouble(scanRate));
            bean.setCashAmt1(Double.parseDouble(cashAmt1));
            bean.setScanRate1(Double.parseDouble(scanRate1));
            bean.setProfitPercent1(Double.parseDouble(profitPercent1));
            bean.setScanRate2(Double.parseDouble(scanRate2));
            bean.setCashAmt2(Double.parseDouble(cashAmt2));
            if(huabeiRate!=null&&!huabeiRate.equals("")){
            	bean.setHuabeiRate(Double.parseDouble(huabeiRate));
            }
            if(huabeiFee!=null&&!huabeiFee.equals("")){
            	bean.setHuabeiFee(Double.parseDouble(huabeiFee));
            }
            
            bean.setProfitRule(Integer.parseInt(profitRule));
            bean.setTempName(tempName);
            //改为调用新的方法，主要是增加对支付宝/微信1000上下的区分字段20190910
            saveHybCashTempla(bean,user);//这个将其保存在本月生效表中
            //saveHybTemplaIncrease(bean,user);//保存到下个月
        }
    
		
	}
	public void saveHybTemplaIncrease(CheckProfitTemplateMicroBean cptm, UserBean user) {
   	 CheckProfitTemplateMicroWModel MicroModel = new CheckProfitTemplateMicroWModel();
        MicroModel.setUnno(user.getUnNo());
        MicroModel.setMerchantType(5);//表示6--MPOS活动模板
        MicroModel.setProfitRule(cptm.getProfitRule());//表示20--MPOS活动20
        //1000以上.38
        MicroModel.setCreditBankRate(Double.parseDouble(new BigDecimal(cptm.getCreditBankRate()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setCashRate(cptm.getCashRate());
        //1000以上.45
        MicroModel.setRuleThreshold(Double.parseDouble(new BigDecimal(cptm.getRuleThreshold()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setStartAmount(cptm.getStartAmount());
        //1000以下.38
        MicroModel.setScanRate(Double.parseDouble(new BigDecimal(cptm.getScanRate()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setCashAmt1(cptm.getCashAmt1());
        //1000以下.45
        MicroModel.setScanRate1(Double.parseDouble(new BigDecimal(cptm.getScanRate1()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setProfitPercent1(cptm.getProfitPercent1());
                
        //银联二维码
        MicroModel.setScanRate2(Double.parseDouble(new BigDecimal(cptm.getScanRate2()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setCashAmt2(cptm.getCashAmt2());
        //花呗
        if(cptm.getHuabeiRate()!=null&&!cptm.getHuabeiRate().equals("")){
        	MicroModel.setHuabeiRate(Double.parseDouble(new BigDecimal(cptm.getHuabeiRate()+"").divide(new BigDecimal("100"))+""));
        	MicroModel.setHuabeiFee(cptm.getHuabeiFee());
        }
        MicroModel.setAptId(cptm.getAptId());
        MicroModel.setTempName(cptm.getTempName());
        MicroModel.setMatainDate(new Date());
       // MicroModel.setMatainType("A");
        MicroModel.setMatainType(cptm.getMatainType());
        checkUnitProfitMicroWDao.saveObject(MicroModel);
   }
	private void saveHybCashTempla(CheckProfitTemplateMicroBean cptm,
			UserBean user) {

        CheckProfitTemplateMicroModel MicroModel = new CheckProfitTemplateMicroModel();
        MicroModel.setUnno(user.getUnNo());
        MicroModel.setMerchantType(5);//表示6--MPOS活动模板
        MicroModel.setProfitRule(cptm.getProfitRule());//表示20--MPOS活动20
       //1000以上.38
        MicroModel.setCreditBankRate(Double.parseDouble(new BigDecimal(cptm.getCreditBankRate()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setCashRate(cptm.getCashRate());
        //1000以上.45
        MicroModel.setScanRate(Double.parseDouble(new BigDecimal(cptm.getRuleThreshold()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setProfitPercent1(cptm.getStartAmount());
        //1000以下.38
        MicroModel.setSubRate(Double.parseDouble(new BigDecimal(cptm.getScanRate()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setCashAmt(cptm.getCashAmt1());
       //1000以下.45
        MicroModel.setScanRate1(Double.parseDouble(new BigDecimal(cptm.getScanRate1()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setCashAmt1(cptm.getProfitPercent1());
       //银联二维码
        MicroModel.setScanRate2(Double.parseDouble(new BigDecimal(cptm.getScanRate2()+"").divide(new BigDecimal("100"))+""));
        MicroModel.setCashAmt2(cptm.getCashAmt2());
        //花呗
        if(cptm.getHuabeiRate()!=null&&!cptm.getHuabeiRate().equals("")){
        	MicroModel.setHuabeiRate(Double.parseDouble(new BigDecimal(cptm.getHuabeiRate()+"").divide(new BigDecimal("100"))+""));
        	MicroModel.setHuabeiFee(cptm.getHuabeiFee());
        }
       
        MicroModel.setTempName(cptm.getTempName());
        MicroModel.setMatainDate(new Date());
        MicroModel.setMatainType("A");
        checkUnitProfitMicroDao.saveObject(MicroModel);


	}

	@Override
	public String validateHybCashTextNotEmpty(CheckProfitTemplateMicroBean cptm) {

		String tempName = cptm.getTempName();
        String str = cptm.getTempname();
        JSONArray json = JSONArray.parseArray(str);
        for(int i=0;i<json.size();i++){
        	//微信1000以上0.38费率
        	String creditBankRate = JSONObject.parseObject(json.getString(i)).getString("creditBankRate");
        	if(null == creditBankRate || "".equals(creditBankRate)) {
        		return "扫码1000以上（终端0.38）费率,不能为空";
        	}
        	//微信1000以上0.38转账费
        	String cashRate = JSONObject.parseObject(json.getString(i)).getString("cashRate");
        	if(null == cashRate || "".equals(cashRate)) {
        		return "扫码1000以上（终端0.38）转账费,不能为空";
        	}
        	//微信1000以上0.45费率
        	String ruleThreshold = JSONObject.parseObject(json.getString(i)).getString("ruleThreshold");
        	if(null == ruleThreshold || "".equals(ruleThreshold)) {
        		return "扫码1000以上（终端0.45）费率,不能为空";
        	}
        	//微信1000以上0.45转账费
        	String startAmount = JSONObject.parseObject(json.getString(i)).getString("startAmount");
        	if(null == startAmount || "".equals(startAmount)) {
        		return "扫码1000以上（终端0.45）转账费,不能为空";
        	}
        	//微信1000以下费率
        	String scanRate = JSONObject.parseObject(json.getString(i)).getString("scanRate");
        	if(null == scanRate || "".equals(scanRate)) {
        		return "扫码1000以下（终端0.38）费率,不能为空";
        	}
        	//微信1000以下转账费
        	String cashAmt1 = JSONObject.parseObject(json.getString(i)).getString("cashAmt1");
        	if(null == cashAmt1 || "".equals(cashAmt1)) {
        		return "扫码1000以下（终端0.38）转账费,不能为空";
        	}
        	//
        	String scanRate1 = JSONObject.parseObject(json.getString(i)).getString("scanRate1");
        	if(null == scanRate1 || "".equals(scanRate1)) {
        		return "扫码1000以下（终端0.45）费率,不能为空";
        	}
        	//
        	String profitPercent1 = JSONObject.parseObject(json.getString(i)).getString("profitPercent1");
        	if(null == profitPercent1 || "".equals(profitPercent1)) {
        		return "扫码1000以下（终端0.45）转账费,不能为空";
        	}
        	//二维码费率
        	String scanRate2 = JSONObject.parseObject(json.getString(i)).getString("scanRate2");
        	if(null == scanRate2 || "".equals(scanRate2)) {
        		return "银联二维码费率,不能为空";
        	}
        	//二维码转账费
        	String cashAmt2 = JSONObject.parseObject(json.getString(i)).getString("cashAmt2");
        	if(null == cashAmt2 || "".equals(cashAmt2)) {
        		return "银联二维码转账费,不能为空";
        	}
        	
        }
        return "非空校验通过";
	
	}

	@Override
	public String validateHybCashTempla(CheckProfitTemplateMicroBean cptm,UserBean user,Integer checkType) {

		// @author:lxg-20190729 Plus成本校验
    	// @author:ztt-20191203,统一划分微信、支付宝为扫码类后，修改相关内容
		if(user.getUnitLvl()>=2) {
			String str = cptm.getTempname();
			Integer rebateType=null;
			JSONArray json1 = JSONArray.parseArray(str);
			for(int i=0;i<json1.size();i++) {
				String profitRule = JSONObject.parseObject(json1.getString(i)).getString("profitRule");
				//微信1000以上0.38费率
				String creditBankRate = JSONObject.parseObject(json1.getString(i)).getString("creditBankRate");
				//微信1000以上0.38转账费
				String cashRate = JSONObject.parseObject(json1.getString(i)).getString("cashRate");
				//微信1000以上0.45费率
				String ruleThreshold = JSONObject.parseObject(json1.getString(i)).getString("ruleThreshold");
				//微信1000以上0.45转账费
				String startAmount = JSONObject.parseObject(json1.getString(i)).getString("startAmount");
				//微信1000以下费率
				String scanRate = JSONObject.parseObject(json1.getString(i)).getString("scanRate");
				//微信1000以下转账费
				String cashAmt1 = JSONObject.parseObject(json1.getString(i)).getString("cashAmt1");
				//支付宝上费率
				String scanRate1 = JSONObject.parseObject(json1.getString(i)).getString("scanRate1");
				//支付宝转账费
				String profitPercent1 = JSONObject.parseObject(json1.getString(i)).getString("profitPercent1");
				//二维码费率
				String scanRate2 = JSONObject.parseObject(json1.getString(i)).getString("scanRate2");
				//二维码转账费
				String cashAmt2 = JSONObject.parseObject(json1.getString(i)).getString("cashAmt2");
				//花呗费率
				String huabeiRate = JSONObject.parseObject(json1.getString(i)).getString("huabeiRate");
				//花呗转账费
				String huabeiFee = JSONObject.parseObject(json1.getString(i)).getString("huabeiFee");

				rebateType = Integer.parseInt(profitRule);
				String subtype = querySubTypeByProfitRule(rebateType);
				String key = "5|0|" + rebateType;
				String keyHrt = "1|1|" + rebateType;
				String keyLimit = "1|1|20";
				// TODO @author:lxg-20200521 收银台多级成本上限限制
				// 添加收银台模板校验,按传来的字段校验
				Map<String, HrtUnnoCostModel> limit = agentUnitService.getAllLimitHrtCostMap();
				return validateHybCashTemplaRateInfo(creditBankRate,cashRate,ruleThreshold,startAmount,scanRate,
						cashAmt1,scanRate1,profitPercent1,scanRate2,cashAmt2,huabeiRate,huabeiFee,key,keyHrt,keyLimit,limit,checkType,subtype,user);
			}
		}
	    return null;
    
	}

	/**
	 * 收银台模板校验
	 * @param creditBankRate 扫码1000以上（终端0.38）费率
	 * @param cashRate 扫码1000以上（终端0.38）转账费
	 * @param ruleThreshold 扫码1000以上（终端0.45）费率
	 * @param startAmount 扫码1000以上（终端0.45）转账费
	 * @param scanRate 扫码1000以下（终端0.38）费率
	 * @param cashAmt1 扫码1000以下（终端0.38）转账费
	 * @param scanRate1 扫码1000以下（终端0.45）费率
	 * @param profitPercent1 扫码1000以下（终端0.45）转账费
	 * @param scanRate2 银联二维码费率
	 * @param cashAmt2 银联二维码转账费
	 * @param huabeiRate 花呗费率
	 * @param huabeiFee 花呗转账费
	 * @param key
	 * @param keyHrt 一代成本校验key
	 * @param keyLimit 上限成本key
	 * @param limit 上限成本限制
	 * @param checkType 校验时类型 1:新增成本 2:修改成本
	 * @param subtype 1:有花呗费率 其它:无花呗费率
	 * @param user
	 * @return
	 */
	private String validateHybCashTemplaRateInfo(String creditBankRate,String cashRate,String ruleThreshold,String startAmount,
												 String scanRate,String cashAmt1,String scanRate1,String profitPercent1,String scanRate2,
												 String cashAmt2,String huabeiRate,String huabeiFee, String key,String keyHrt,String keyLimit,
												 Map<String, HrtUnnoCostModel> limit,Integer checkType, String subtype,UserBean user){
		if (user.getUnitLvl() == 2) {
			// 上级为1代成本
			// 扫码成本 微信1000以下费率
			Map<String, HrtUnnoCostModel> hrtMap = agentUnitService.getAllHrtCostMap(1, user.getUnNo(), 2,checkType);
			if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getCreditRate() != null) {
				BigDecimal parent = hrtMap.get(keyHrt).getCreditRate();
				if (scanRate!=null && new BigDecimal(scanRate).compareTo(parent) < 0) {
					return "扫码1000以下（终端0.38）费率小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getCreditRate()!=null
						&& scanRate!=null && new BigDecimal(scanRate).compareTo(limit.get(keyLimit).getCreditRate()) > 0) {
					return "扫码1000以下（终端0.38）费率成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以下（终端0.38）费率成本上级代理未维护，请联系上级代理进行成本设置";
			}
			// 微信1000以上0.38费率
			if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getWxUpRate() != null) {
				BigDecimal parent = hrtMap.get(keyHrt).getWxUpRate();
				if (creditBankRate!=null && new BigDecimal(creditBankRate).compareTo(parent) < 0) {
					return "扫码1000以上（终端0.38）费率小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpRate()!=null
						&& creditBankRate!=null && new BigDecimal(creditBankRate).compareTo(limit.get(keyLimit).getWxUpRate()) > 0) {
					return "扫码1000以上（终端0.38）费率成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以上（终端0.38）费率成本上级代理未维护，请联系上级代理进行成本设置";
			}
			//微信1000以上0.45费率
			if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getWxUpRate1() != null) {
				BigDecimal parent = hrtMap.get(keyHrt).getWxUpRate1();
				if (ruleThreshold!=null && new BigDecimal(ruleThreshold).compareTo(parent) < 0) {
					return "扫码1000以上（终端0.45）费率小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpRate1()!=null
						&& ruleThreshold!=null && new BigDecimal(ruleThreshold).compareTo(limit.get(keyLimit).getWxUpRate1()) > 0) {
					return "扫码1000以上（终端0.45）费率成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以上（终端0.45）费率成本上级代理未维护，请联系上级代理进行成本设置";
			}
			//支付宝费率
			if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getZfbRate() != null) {
				BigDecimal parent = hrtMap.get(keyHrt).getZfbRate();
				if (scanRate1!=null && new BigDecimal(scanRate1).compareTo(parent) < 0) {
					return "扫码1000以下（终端0.45）费率小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getZfbRate()!=null
						&& scanRate1!=null && new BigDecimal(scanRate1).compareTo(limit.get(keyLimit).getZfbRate()) > 0) {
					return "扫码1000以下（终端0.45）费率成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以下（终端0.45）费率成本上级代理未维护，请联系上级代理进行成本设置";
			}
			//二维码费率
			if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getEwmRate() != null) {
				BigDecimal parent = hrtMap.get(keyHrt).getEwmRate();
				if (scanRate2!=null && new BigDecimal(scanRate2).compareTo(parent) < 0) {
					return "银联二维码费率小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getEwmRate()!=null
						&& scanRate2!=null && new BigDecimal(scanRate2).compareTo(limit.get(keyLimit).getEwmRate()) > 0) {
					return "银联二维码费率成本高于商户终端成本，请修改";
				}
			} else {
				return "银联二维码费率成本上级代理未维护，请联系上级代理进行成本设置";
			}

			// 扫码提现成本 微信1000以下转账费
			if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getCashCost() != null) {
				BigDecimal parent = hrtMap.get(keyHrt).getCashCost();
				if (cashAmt1!=null && new BigDecimal(cashAmt1).compareTo(parent) < 0) {
					return "扫码1000以下（终端0.38）转账费小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getCashCost()!=null
						&& cashAmt1!=null && new BigDecimal(cashAmt1).compareTo(limit.get(keyLimit).getCashCost()) > 0) {
					return "扫码1000以下（终端0.38）转账费成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以下（终端0.38）转账费成本上级代理未维护，请联系上级代理进行成本设置";
			}
			//微信1000以上0.38转账费
			if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getWxUpCash() != null) {
				BigDecimal parent = hrtMap.get(keyHrt).getWxUpCash();
				if (cashRate!=null && new BigDecimal(cashRate).compareTo(parent) < 0) {
					return "扫码1000以上（终端0.38）转账费小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpCash()!=null
						&& cashRate!=null && new BigDecimal(cashRate).compareTo(limit.get(keyLimit).getWxUpCash()) > 0) {
					return "扫码1000以上（终端0.38）转账费成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以上（终端0.38）转账费成本上级代理未维护，请联系上级代理进行成本设置";
			}
			//微信1000以上0.45转账费
			if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getWxUpCash1() != null) {
				BigDecimal parent = hrtMap.get(keyHrt).getWxUpCash1();
				if (startAmount!=null && new BigDecimal(startAmount).compareTo(parent) < 0) {
					return "扫码1000以上（终端0.45）转账费小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpCash1()!=null
						&& startAmount!=null && new BigDecimal(startAmount).compareTo(limit.get(keyLimit).getWxUpCash1()) > 0) {
					return "扫码1000以上（终端0.45）转账费成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以上（终端0.45）转账费成本上级代理未维护，请联系上级代理进行成本设置";
			}
			//支付宝转账费
			if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getZfbCash() != null) {
				BigDecimal parent = hrtMap.get(keyHrt).getZfbCash();
				if (profitPercent1!=null && new BigDecimal(profitPercent1).compareTo(parent) < 0) {
					return "扫码1000以下（终端0.45）转账费小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getZfbCash()!=null
						&& profitPercent1!=null && new BigDecimal(profitPercent1).compareTo(limit.get(keyLimit).getZfbCash()) > 0) {
					return "扫码1000以下（终端0.45）转账费成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以下（终端0.45）转账费成本上级代理未维护，请联系上级代理进行成本设置";
			}
			//二维码转账费
			if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getEwmCash() != null) {
				BigDecimal parent = hrtMap.get(keyHrt).getEwmCash();
				if (cashAmt2!=null && new BigDecimal(cashAmt2).compareTo(parent) < 0) {
					return "银联二维码转账费小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getEwmCash()!=null
						&& cashAmt2!=null && new BigDecimal(cashAmt2).compareTo(limit.get(keyLimit).getEwmCash()) > 0) {
					return "银联二维码转账费成本高于商户终端成本，请修改";
				}
			} else {
				return "银联二维码转账费成本上级代理未维护，请联系上级代理进行成本设置";
			}

			if(subtype!=null&&subtype.equals("1")){
				//花呗费率
				if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getHbRate() != null) {
					BigDecimal parent = hrtMap.get(keyHrt).getHbRate();
					if (huabeiRate!=null && new BigDecimal(huabeiRate).compareTo(parent) < 0) {
						return "花呗费率小于上级成本";
					}
					if (limit.get(keyLimit)!=null && limit.get(keyLimit).getHbRate()!=null
							&& huabeiRate!=null && new BigDecimal(huabeiRate).compareTo(limit.get(keyLimit).getHbRate()) > 0) {
						return "花呗费率成本高于商户终端成本，请修改";
					}
				} else {
					return "花呗费率成本上级代理未维护，请联系上级代理进行成本设置";
				}
				//花呗转账费
				if (hrtMap.get(keyHrt) != null && hrtMap.get(keyHrt).getHbCash() != null) {
					BigDecimal parent = hrtMap.get(keyHrt).getHbCash();
					if (huabeiFee!=null && new BigDecimal(huabeiFee).compareTo(parent) < 0) {
						return "花呗转账费小于上级成本";
					}
					if (limit.get(keyLimit)!=null && limit.get(keyLimit).getHbCash()!=null
							&& huabeiFee!=null && new BigDecimal(huabeiFee).compareTo(limit.get(keyLimit).getHbCash()) > 0) {
						return "花呗转账费成本高于商户终端成本，请修改";
					}
				} else {
					return "花呗转账费成本上级代理未维护，请联系上级代理进行成本设置";
				}
			}
		} else {
			Map<String, CheckProfitTemplateMicroModel> t = getProfitTemplateMap(1, user.getUnNo(), user.getUnitLvl(),checkType);


			// 扫码成本 微信1000以下
			if (t.get(key) != null && t.get(key).getSubRate() != null) {
				BigDecimal parent = new BigDecimal(t.get(key).getSubRate().toString()).multiply(new BigDecimal(100));
				if (scanRate!=null && new BigDecimal(scanRate).compareTo(parent) < 0) {
					return "扫码1000以下（终端0.38）费率小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getCreditRate()!=null
						&& scanRate!=null && new BigDecimal(scanRate).compareTo(limit.get(keyLimit).getCreditRate()) > 0) {
					return "扫码1000以下（终端0.38）费率成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以下（终端0.38）费率成本上级代理未维护，请联系上级代理进行成本设置";
			}
			//微信1000以上0.38费率
			if (t.get(key) != null && t.get(key).getCreditBankRate() != null) {
				BigDecimal parent = new BigDecimal(t.get(key).getCreditBankRate().toString()).multiply(new BigDecimal(100));
				if (creditBankRate!=null && new BigDecimal(creditBankRate).compareTo(parent) < 0) {
					return "扫码1000以上（终端0.38）费率小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpRate()!=null
						&& creditBankRate!=null && new BigDecimal(creditBankRate).compareTo(limit.get(keyLimit).getWxUpRate()) > 0) {
					return "扫码1000以上（终端0.38）费率成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以上（终端0.38）费率成本上级代理未维护，请联系上级代理进行成本设置";
			}
			//微信1000以上0.45费率
			if (t.get(key) != null && t.get(key).getScanRate() != null) {
				BigDecimal parent = new BigDecimal(t.get(key).getScanRate().toString()).multiply(new BigDecimal(100));
				if (ruleThreshold!=null && new BigDecimal(ruleThreshold).compareTo(parent) < 0) {
					return "扫码1000以上（终端0.45）费率小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpRate1()!=null
						&& ruleThreshold!=null && new BigDecimal(ruleThreshold).compareTo(limit.get(keyLimit).getWxUpRate1()) > 0) {
					return "扫码1000以上（终端0.45）费率成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以上（终端0.45）费率成本上级代理未维护，请联系上级代理进行成本设置";
			}
			//支付宝费率
			if (t.get(key) != null && t.get(key).getScanRate1() != null) {
				BigDecimal parent = new BigDecimal(t.get(key).getScanRate1().toString()).multiply(new BigDecimal(100));
				if (scanRate1!=null && new BigDecimal(scanRate1).compareTo(parent) < 0) {
					return "扫码1000以下（终端0.45）费率小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getZfbRate()!=null
						&& scanRate1!=null && new BigDecimal(scanRate1).compareTo(limit.get(keyLimit).getZfbRate()) > 0) {
					return "扫码1000以下（终端0.45）费率成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以下（终端0.45）费率成本上级代理未维护，请联系上级代理进行成本设置";
			}
			//二维码费率
			if (t.get(key) != null && t.get(key).getScanRate2() != null) {
				BigDecimal parent = new BigDecimal(t.get(key).getScanRate2().toString()).multiply(new BigDecimal(100));
				if (scanRate2!=null && new BigDecimal(scanRate2).compareTo(parent) < 0) {
					return "银联二维码费率小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getEwmRate()!=null
						&& scanRate2!=null && new BigDecimal(scanRate2).compareTo(limit.get(keyLimit).getEwmRate()) > 0) {
					return "银联二维码费率成本高于商户终端成本，请修改";
				}
			} else {
				return "银联二维码费率成本上级代理未维护，请联系上级代理进行成本设置";
			}


			// 扫码提现成本
			if (t.get(key) != null && t.get(key).getCashAmt() != null) {
				BigDecimal parentCash = new BigDecimal(t.get(key).getCashAmt().toString());
				if (cashAmt1!=null && new BigDecimal(cashAmt1).compareTo(parentCash) < 0) {
					return "扫码1000以下（终端0.38）转账费小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getCashCost()!=null
						&& cashAmt1!=null && new BigDecimal(cashAmt1).compareTo(limit.get(keyLimit).getCashCost()) > 0) {
					return "扫码1000以下（终端0.38）转账费成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以下（终端0.38）转账费成本上级代理未维护，请联系上级代理进行成本设置";
			}
			//微信1000以上0.38转账费
			if (t.get(key) != null && t.get(key).getCashRate() != null) {
				BigDecimal parentCash = new BigDecimal(t.get(key).getCashRate().toString());
				if (cashRate!=null && new BigDecimal(cashRate).compareTo(parentCash) < 0) {
					return "扫码1000以上（终端0.38）转账费小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpCash()!=null
						&& cashRate!=null && new BigDecimal(cashRate).compareTo(limit.get(keyLimit).getWxUpCash()) > 0) {
					return "扫码1000以上（终端0.38）转账费成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以上（终端0.38）转账费成本上级代理未维护，请联系上级代理进行成本设置";
			}
			//微信1000以上0.45转账费
			if (t.get(key) != null && t.get(key).getProfitPercent1() != null) {
				BigDecimal parentCash = new BigDecimal(t.get(key).getProfitPercent1().toString());
				if (startAmount!=null && new BigDecimal(startAmount).compareTo(parentCash) < 0) {
					return "扫码1000以上（终端0.45）转账费小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getWxUpCash1()!=null
						&& startAmount!=null && new BigDecimal(startAmount).compareTo(limit.get(keyLimit).getWxUpCash1()) > 0) {
					return "扫码1000以上（终端0.45）转账费成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以上（终端0.45）转账费成本上级代理未维护，请联系上级代理进行成本设置";
			}
			//支付宝转账费
			if (t.get(key) != null && t.get(key).getCashAmt1() != null) {
				BigDecimal parentCash = new BigDecimal(t.get(key).getCashAmt1().toString());
				if (profitPercent1!=null && new BigDecimal(profitPercent1).compareTo(parentCash) < 0) {
					return "扫码1000以下（终端0.45）转账费小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getZfbCash()!=null
						&& profitPercent1!=null && new BigDecimal(profitPercent1).compareTo(limit.get(keyLimit).getZfbCash()) > 0) {
					return "扫码1000以下（终端0.45）转账费成本高于商户终端成本，请修改";
				}
			} else {
				return "扫码1000以下（终端0.45）转账费成本上级代理未维护，请联系上级代理进行成本设置";
			}
//					//二维码转账费
			if (t.get(key) != null && t.get(key).getCashAmt2() != null) {
				BigDecimal parentCash = new BigDecimal(t.get(key).getCashAmt2().toString());
				if (cashAmt2!=null && new BigDecimal(cashAmt2).compareTo(parentCash) < 0) {
					return "银联二维码转账费小于上级成本";
				}
				if (limit.get(keyLimit)!=null && limit.get(keyLimit).getEwmCash()!=null
						&& cashAmt2!=null && new BigDecimal(cashAmt2).compareTo(limit.get(keyLimit).getEwmCash()) > 0) {
					return "银联二维码转账费成本高于商户终端成本，请修改";
				}
			} else {
				return "银联二维码转账费成本上级代理未维护，请联系上级代理进行成本设置";
			}
			if(subtype!=null&&subtype.equals("1")){
				//花呗费率
				if (t.get(key) != null && t.get(key).getHuabeiRate() != null) {
					BigDecimal parent = new BigDecimal(t.get(key).getHuabeiRate().toString()).multiply(new BigDecimal(100));
					if (huabeiRate!=null && new BigDecimal(huabeiRate).compareTo(parent) < 0) {
						return "花呗费率小于上级成本";
					}
					if (limit.get(keyLimit)!=null && limit.get(keyLimit).getHbRate()!=null
							&& huabeiRate!=null && new BigDecimal(huabeiRate).compareTo(limit.get(keyLimit).getHbRate()) > 0) {
						return "花呗费率成本高于商户终端成本，请修改";
					}
				} else {
					return "花呗费率成本上级代理未维护，请联系上级代理进行成本设置";
				}
				//花呗转账费
				if (t.get(key) != null && t.get(key).getHuabeiFee() != null) {
					BigDecimal parentCash = new BigDecimal(t.get(key).getHuabeiFee().toString());
					if (huabeiFee!=null && new BigDecimal(huabeiFee).compareTo(parentCash) < 0) {
						return "花呗转账费小于上级成本";
					}
					if (limit.get(keyLimit)!=null && limit.get(keyLimit).getHbCash()!=null
							&& huabeiFee!=null && new BigDecimal(huabeiFee).compareTo(limit.get(keyLimit).getHbCash()) > 0) {
						return "花呗转账费成本高于商户终端成本，请修改";
					}
				} else {
					return "花呗转账费成本上级代理未维护，请联系上级代理进行成本设置";
				}
			}
		}
		return null;
	}

	private String querySubTypeByProfitRule(Integer rebateType) {
		String sql ="select t.subtype from  bill_purconfigure t where t.type=3 and t.valuestring='rate' and t.prod='syt' and t.valueinteger='"+rebateType+"'";
		List<Map<String, Object>> typeList = checkUnitProfitMicroDao.queryObjectsBySqlList(sql);
		if(typeList.size()==0) {
			return null;
		}
		Map<String, Object> obj = typeList.get(0);
		Object subtype=obj.get("SUBTYPE");
		if(subtype!=null){
			return subtype.toString();
		}
		return null;
	}
	
	@Override
	public String validateSYTTextNotEmpty(CheckProfitTemplateMicroBean cpt) {
		String tempName = cpt.getTempName();
		String str = cpt.getTempname();
		JSONArray json = JSONArray.parseArray(str);
		for(int i=0;i<json.size();i++){
			//扫码1000以上0.38费率
			String creditBankRate = JSONObject.parseObject(json.getString(i)).getString("creditBankRate");
			if(null == creditBankRate || "".equals(creditBankRate)) {
				return "扫码1000以上（终端0.38）费率,不能为空";
			}
			//扫码1000以上0.38转账费
			String cashRate = JSONObject.parseObject(json.getString(i)).getString("cashRate");
			if(null == cashRate || "".equals(cashRate)) {
				return "扫码1000以上（终端0.38）转账费,不能为空";
			}
			//扫码1000以上0.45费率
			String scanRate = JSONObject.parseObject(json.getString(i)).getString("scanRate");
			if(null == scanRate || "".equals(scanRate)) {
				return "扫码1000以上（终端0.45）费率,不能为空";
			}
			//扫码1000以上0.45转账费
			String profitPercent1 = JSONObject.parseObject(json.getString(i)).getString("profitPercent1");
			if(null == profitPercent1 || "".equals(profitPercent1)) {
				return "扫码1000以上（终端0.45）转账费,不能为空";
			}
			//扫码1000以下0.38费率
			String subRate = JSONObject.parseObject(json.getString(i)).getString("subRate");
			if(null == subRate || "".equals(subRate)) {
				return "扫码1000以下（终端0.38）费率,不能为空";
			}
			//扫码1000以下0.38转账费
			String cashAmt = JSONObject.parseObject(json.getString(i)).getString("cashAmt");
			if(null == cashAmt || "".equals(cashAmt)) {
				return "扫码1000以下（终端0.38）转账费,不能为空";
			}
			//扫码1000以下0.45费率
			String scanRate1 = JSONObject.parseObject(json.getString(i)).getString("scanRate1");
			if(null == scanRate1 || "".equals(scanRate1)) {
				return "扫码1000以下（终端0.45）费率,不能为空";
			}
			//扫码1000以下0.45转账费
			String cashAmt1 = JSONObject.parseObject(json.getString(i)).getString("cashAmt1");
			if(null == cashAmt1 || "".equals(cashAmt1)) {
				return "扫码1000以下（终端0.45）转账费,不能为空";
			}
			//二维码费率
			String scanRate2 = JSONObject.parseObject(json.getString(i)).getString("scanRate2");
			if(null == scanRate2 || "".equals(scanRate2)) {
				return "银联二维码费率,不能为空";
			}
			//二维码转账费
			String cashAmt2 = JSONObject.parseObject(json.getString(i)).getString("cashAmt2");
			if(null == cashAmt2 || "".equals(cashAmt2)) {
				return "银联二维码转账费,不能为空";
			}
			
			//活动类型
        	String profitRule = JSONObject.parseObject(json.getString(i)).getString("profitRule");
        	//判断是否是花呗活动
        	String subtype = querySubTypeByProfitRule(Integer.parseInt(profitRule));
        	if(subtype!=null) {
        		//花呗费率
        		String huabeiRate = JSONObject.parseObject(json.getString(i)).getString("huabeiRate");
        		if(null == huabeiRate || "".equals(huabeiRate)) {
        			return "花呗费率,不能为空";
        		}
        		//花呗转账费
        		String huabeiFee = JSONObject.parseObject(json.getString(i)).getString("huabeiFee");
        		if(null == huabeiFee || "".equals(huabeiFee)) {
        			return "花呗转账费,不能为空";
        		}
        	}
		}
		return "非空校验通过";
	}


	@Override
    public String validateSytTemplateNew(CheckProfitTemplateMicroBean cptm, UserBean user){
		if(user.getUnitLvl()>=2) {
			String str = cptm.getTempname();
			Integer rebateType=null;
			JSONArray json1 = JSONArray.parseArray(str);
			for(int i=0;i<json1.size();i++) {
				String profitRule = JSONObject.parseObject(json1.getString(i)).getString("profitRule");
				String creditBankRate = JSONObject.parseObject(json1.getString(i)).getString("creditBankRate");
				String cashRate = JSONObject.parseObject(json1.getString(i)).getString("cashRate");
				String scanRate = JSONObject.parseObject(json1.getString(i)).getString("scanRate");
				String cashAmt1 = JSONObject.parseObject(json1.getString(i)).getString("cashAmt1");
				String scanRate1 = JSONObject.parseObject(json1.getString(i)).getString("scanRate1");
				String profitPercent1 = JSONObject.parseObject(json1.getString(i)).getString("profitPercent1");
				String scanRate2 = JSONObject.parseObject(json1.getString(i)).getString("scanRate2");
				String cashAmt2 = JSONObject.parseObject(json1.getString(i)).getString("cashAmt2");
				String subRate = JSONObject.parseObject(json1.getString(i)).getString("subRate");
				String cashAmt = JSONObject.parseObject(json1.getString(i)).getString("cashAmt");
				
				String huabeiRate = null;
				String huabeiFee = null;
				//判断是否是花呗活动
	        	String subtype = querySubTypeByProfitRule(Integer.parseInt(profitRule));
	        	if(subtype!=null) {
	        		//花呗费率
	        		huabeiRate = JSONObject.parseObject(json1.getString(i)).getString("huabeiRate");
	        		//花呗转账费
	        		huabeiFee = JSONObject.parseObject(json1.getString(i)).getString("huabeiFee");
	        	}

				rebateType = Integer.parseInt(profitRule);
				String key = "5|0|" + rebateType;
				String keyHrt = "1|1|" + rebateType;

				String keyLimit = "1|1|20";
				// TODO @author:lxg-20200521 收银台多级成本上限限制
				Map<String, HrtUnnoCostModel> limit = agentUnitService.getAllLimitHrtCostMap();
				// 后台存的数据
//				MicroModel.setScanRate(Double.parseDouble(new BigDecimal(cptm.getRuleThreshold()扫码1000以上（终端0.45）费率+"").divide(new BigDecimal("100"))+""));
//				MicroModel.setProfitPercent1(cptm.getStartAmount()扫码1000以上（终端0.45）转账费);
//				MicroModel.setSubRate(Double.parseDouble(new BigDecimal(cptm.getScanRate()扫码1000以下（终端0.38）费率+"").divide(new BigDecimal("100"))+""));
//				MicroModel.setCashAmt1(cptm.getProfitPercent1()扫码1000以下（终端0.45）转账费);
//				MicroModel.setCashAmt(cptm.getCashAmt1()扫码1000以下（终端0.38）转账费);
				// syt模板修改时校验,按存储字段校验
				String errMsg = validateHybCashTemplaRateInfo(creditBankRate,cashRate,scanRate,profitPercent1,subRate,
						cashAmt,scanRate1,cashAmt1,scanRate2,cashAmt2,huabeiRate,huabeiFee,key,keyHrt,keyLimit,limit,2,subtype,user);
				if(StringUtils.isNotEmpty(errMsg)){
					return errMsg;
				}

			}
		}
	    return null;
	}
	
	@Override
	public List<Map<String, Object>> queryupdateSytProfitmicroLogDetail(CheckProfitTemplateMicroBean cptm,
			UserBean userBean) {
		List<Map<String, Object>> lst = new ArrayList<Map<String,Object>>();
		
		String sql="select t.* from CHECK_MICRO_PROFITTEMPLATE_LOG t where t.UNNO='"+cptm.getUnno()+"' and t.MatainType in ('A','M','P')"+
				" and t.merchanttype = 5"+
				" and t.tempname ='"+cptm.getTempName()+"'"+
				" and t.startdate >= to_date(substr('"+cptm.getTxnDay()+"',1,10), 'yyyy-MM-dd')"+
				" and t.startdate < to_date(substr('"+cptm.getTxnDay()+"',1,10), 'yyyy-MM-dd') + 1";
		if(!cptm.getTxnDay1().isEmpty() && !"undefined".equals(cptm.getTxnDay1()) ) {
			sql+= " and t.enddate = to_date('"+cptm.getTxnDay1()+"', 'yyyy-MM-dd HH24:mi:ss')";
		}else {
			sql += " and t.enddate is null";
		}
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		if("110000".equals(userBean.getUnNo())){
        }
        //为总公司并且是部门---查询全部
        else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
            UnitModel parent = unitModel.getParent();
            if("110000".equals(parent.getUnNo())){
            }
        }else{
        	sql+=" and t.unno in(select unno from SYS_UNIT where UPPER_UNIT='"+userBean.getUnNo()+"') " ;
        }
        List<CheckProfitTemplateMicroLogModel> list=checkUnitProfitMicroLogDao.queryObjectsBySqlList(sql, null,CheckProfitTemplateMicroLogModel.class );
        for(int i=0;i<list.size();i++){
        	CheckProfitTemplateMicroLogModel cc =list.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("profitRule",null==cc.getProfitRule()?"21":cc.getProfitRule());
            
            map.put("creditBankRate", null==cc.getCreditBankRate()?"":new BigDecimal(cc.getCreditBankRate()+"").multiply(new BigDecimal("100")));
            map.put("cashRate", null==cc.getCashRate()?"":cc.getCashRate());
            map.put("scanRate", null==cc.getScanRate()?"":new BigDecimal(cc.getScanRate()+"").multiply(new BigDecimal("100")));
            map.put("profitPercent1", null==cc.getProfitPercent1()?"":cc.getProfitPercent1());
            map.put("subRate", null==cc.getSubRate()?"":new BigDecimal(cc.getSubRate()+"").multiply(new BigDecimal("100")));
            map.put("cashAmt", null==cc.getCashAmt()?"":cc.getCashAmt());
            map.put("cashAmt1", null==cc.getCashAmt1()?"":cc.getCashAmt1());
            map.put("scanRate1", null==cc.getScanRate1()?"":new BigDecimal(cc.getScanRate1()+"").multiply(new BigDecimal("100")));
            map.put("scanRate2", null==cc.getScanRate2()?"":new BigDecimal(cc.getScanRate2()+"").multiply(new BigDecimal("100")));
            map.put("cashAmt2", null==cc.getCashAmt2()?"":cc.getCashAmt2());
            map.put("huabeiRate", null==cc.getHuabeiRate()?"":new BigDecimal(cc.getHuabeiRate()+"").multiply(new BigDecimal("100")));
            map.put("huabeiFee", null==cc.getHuabeiFee()?"":cc.getHuabeiFee());
            String ishuabei = querySubTypeByProfitRule(Integer.parseInt( map.get("profitRule").toString()));
            if(ishuabei!=null) {
            	map.put("ishuabei", 1);
            }else {
            	map.put("ishuabei", 2);
            }
            lst.add(map);
        }
        return lst;
	}

	@Override
	public HSSFWorkbook exportUnitMposTemplateDetailDataScan(CheckProfitTemplateMicroBean cptm, UserBean userBean) {
		if(cptm.getTxnDay()==null||"".equals(cptm.getTxnDay())||cptm.getTxnDay1()==null||"".equals(cptm.getTxnDay1())||!cptm.getTxnDay().substring(0, 6).equals(cptm.getTxnDay1().substring(0, 6))) {
			return null;
		}
		cptm.setTxnDay(cptm.getTxnDay().replace("-", ""));
		cptm.setTxnDay1(cptm.getTxnDay1().replace("-", ""));
		Map map = new HashMap();
		String sql = getUnitMposTemplateDetailDataScanSql(cptm,map);
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, map, cptm.getPage(), cptm.getRows());
			List<String> excelHeader = new ArrayList<String>();
			Map<String, Object> headMap = new HashMap<String, Object>();
			excelHeader.add("UNNO");
			excelHeader.add("UNITNAME");
			excelHeader.add("MINFO1");
			excelHeader.add("SETTMETHOD");
			excelHeader.add("TXNCOUNT");
			excelHeader.add("TXNAMOUNT");
			excelHeader.add("MDA");
			excelHeader.add("PROFIT");

			headMap.put("UNNO", "机构号");
			headMap.put("UNITNAME", "机构名称");
			headMap.put("MINFO1", "活动类型");
			headMap.put("SETTMETHOD", "交易类型");
			headMap.put("TXNCOUNT", "交易总笔数");
			headMap.put("TXNAMOUNT", "交易总金额");
			headMap.put("MDA", "商户手续费");
			headMap.put("PROFIT", "分润金额");
			

			return ExcelUtil.export("代理商MPOS活动交易分润汇总", list, excelHeader, headMap);
	}
	
	/**
	 * 代理商MPOS活动交易分润汇总Sql
	 * */
	private String getUnitMposTemplateDetailDataScanSql(CheckProfitTemplateMicroBean cptm,Map map) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, cptm.getUnno());
		String sql = "select :unName as unitName, a.*, rownum rm from ("+
				" select unno,settmethod,minfo1,"+
				" case when nvl(round(sum(profitone), 2), 0)>=0 then nvl(round(sum(profitone), 2), 0) else 0 end as profit,"+
				" nvl(sum(mda), 0) mda,nvl(sum(txnamount), 0) txnamount,sum(txncount) txncount "+
				" from (select unno,txnamount,txncount,mda,minfo1,minfo2,mertype,"+
				" case when b.mertype = 1 and b.minfo2 = 1 then '微信0.38' when b.mertype = 1 and b.minfo2 = 2 then '微信0.45'"+
				" when b.mertype = 1 and b.minfo2 = 3 then '微信(老)' when b.mertype = 2 then '支付宝'"+
				" when b.mertype = 3 then '银联二维码' when b.mertype = 4 then '云闪付'"+
				" when b.mertype = 5 and minfo2 = 1 then '扫码1000以上（终端0.38）'"+
				" when b.mertype = 5 and minfo2 = 2 then '扫码1000以上（终端0.45）'"+
				" when b.mertype = 5 and minfo2 = 3 then '扫码1000以下（终端0.38）'"+
				" when b.mertype = 5 and minfo2 = 4 then '扫码1000以下（终端0.45）'"+
				
				" when b.mertype = 6 then '花呗'"+
				" when b.mertype = 7 then '刷卡'"+
				" when b.mertype = 1 then '微信' end as settmethod,"+
				" (select case when b.mertype = 1 and b.minfo2 = 1 then (mda - txnamount * y.creditbankrate)"+
				" when b.mertype = 1 and b.minfo2 = 2 then (mda - txnamount * y.rulethreshold)"+
				" when b.mertype = 1 and b.minfo2 = 3 then (mda - txnamount * y.scanrate)"+
				" when b.mertype = 2 then (mda - txnamount * y.scanrate1)"+
				" when b.mertype = 3 then (mda - txnamount * y.scanrate2)"+
				" when b.mertype = 4 then (mda - txnamount * y.cloudquickrate)"+
				
				" when b.mertype = 5 and b.minfo2 = 1 then (mda - txnamount * y.creditBankRate )"+
				" when b.mertype = 5 and b.minfo2 = 2 then (mda - txnamount * y.ruleThreshold )"+
				" when b.mertype = 5 and b.minfo2 = 3 then (mda - txnamount * y.scanRate )"+
				" when b.mertype = 5 and b.minfo2 = 4 then (mda - txnamount * y.scanRate1 )"+
				
				" when b.mertype = 6 then (mda - txnamount * y.huabeiRate)"+
				" when b.mertype = 7 then (mda - txnamount * y.subrate)"+
				" when b.mertype = 1 then (mda - txnamount * y.scanrate) end as profit"+
				" from check_micro_profittemplate y,check_unit_profitemplate uy"+
				" where uy.aptid = y.aptid AND y.merchanttype = 6"+
				" AND uy.status != 0 AND y.profitrule = minfo1 AND y.mataintype != 'D'  AND uy.unno = :unno) as profitone"+
				" from (select a.unno,a.txnamount,a.txncount,a.mda,a.profit,a.minfo1,a.minfo2,a.mertype"+
				" from pg_unno_wechat a"+
				" where a.ismpos = 4"+
				" AND a.mertype != 4"+
				" AND a.unno = :unno ";
		map.put("unName",unitModel.getUnitName());
		map.put("unno",unitModel.getUnNo());
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.txnday>=:txnday1 and a.txnday<=:txnday2 " ;
			map.put("txnday1",cptm.getTxnDay());
			map.put("txnday2",cptm.getTxnDay1());
		}else{
			sql+=" and a.txnday=to_char(sysdate-1,'yyyyMMdd')";
		}
		sql+=" union all " +
				"  select c.unno,c.txnamount,c.txncount,c.mda,c.profit,c.minfo1,c.minfo2,decode(c.mertype,4,4,7) as mertype " +
				"    from pg_unno_txn c " +
				"   where c.isnew = 1 and c.unno = :unno ";
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and c.txnday>=:txnday1 and c.txnday<=:txnday2 " ;
			map.put("txnday1",cptm.getTxnDay());
			map.put("txnday2",cptm.getTxnDay1());
		}else{
			sql+=" and c.txnday=to_char(sysdate-1,'yyyyMMdd')";
		}
		sql+=") b where 1=1";
		if(cptm.getRebateType()!=null&&!"".equals(cptm.getRebateType())){
			sql += "AND minfo1 = :minfo1 ";
			map.put("minfo1",cptm.getRebateType());
		}
		sql+=" ) group by unno,settmethod,minfo1) a ";
		
		return sql;
	}
	
	/**
	 * 代理商MPOS活动提现分润汇总Sql
	 * */
	private String getUnitMposProfitMicroSumDataCash(CheckProfitTemplateMicroBean cptm,Map map) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, cptm.getUnno());
		String month=null;
		String start = StringUtils.isNotEmpty(cptm.getTxnDay())?cptm.getTxnDay().replace("-", ""):"";
		String end = StringUtils.isNotEmpty(cptm.getTxnDay1())?cptm.getTxnDay1().replace("-", ""):"";
		if(StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end) && start.substring(0,6).equals(end.substring(0,6))){
			month=start.substring(4,6);
		}else{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			month=simpleDateFormat.format(new Date()).substring(4,6);
		}
		
		String sql = "select :unno as unno,:unitName as unitName ,a.*, rownum rm "+
				"  from (select settmethod,mertype,nvl(round(sum(case when profit<0 then 0 else profit end), 2), 0) profit,"+
				"  nvl(sum(mda), 0) mda, nvl(sum(txnamount), 0) txnamount,count(mda) txncount from "+
				" (select case when a.cashmode = 7 then '刷卡'"+
				" when a.cashmode = 8 and a.txntype = 1 and a.cashday < 20191201 then '微信0.38'"+
				" when a.cashmode = 8 and a.txntype = 2 and a.cashday < 20191201 then '微信0.45'"+
				" when a.cashmode = 8 and a.txntype = 3 and a.cashday < 20191201 then '微信(老)'"+
				" when a.cashmode = 8 and a.txntype = 4 and a.cashday < 20191201 then '支付宝'"+
				" when a.cashmode = 8 and a.txntype = 5 and a.cashday < 20191201 then '二维码'"+
				
				//ztt20191204--微信支付宝统一划分为扫码分类后添加判断,字段定义与之前一样，
				//只是添加20191201的时间判断a.cashday < 20191201为之前老的显示，反之为变更扫码后的显示
				" when a.cashmode = 8 and a.txntype = 1 and a.cashday >= 20191201 then '扫码1000以上（终端0.38）'"+
				" when a.cashmode = 8 and a.txntype = 2 and a.cashday >= 20191201 then '扫码1000以上（终端0.45）'"+
				" when a.cashmode = 8 and a.txntype = 3 and a.cashday >= 20191201 then '扫码1000以下（终端0.38）'"+
				" when a.cashmode = 8 and a.txntype = 4 and a.cashday >= 20191201 then '扫码1000以下（终端0.45）'"+
				" when a.cashmode = 8 and a.txntype = 5 and a.cashday >= 20191201 then '银联二维码'"+
				
				" when a.cashmode = 8 and a.txntype = 6 then '花呗'"+
				
				" when a.cashmode = 8 then '扫码'"+
				" end as settmethod,"+
				" (select case when a.cashmode = 7 then a.cashfee - y.cashamt"+
				" when a.cashmode = 8 and a.txntype = 1 and a.cashday < 20191201 then a.cashfee - y.cashrate"+
				" when a.cashmode = 8 and a.txntype = 2 and a.cashday < 20191201 then a.cashfee - y.startamount"+
				" when a.cashmode = 8 and a.txntype = 3 and a.cashday < 20191201 then a.cashfee - y.cashamt1"+
				" when a.cashmode = 8 and a.txntype = 4 and a.cashday < 20191201 then a.cashfee - y.profitpercent1"+
				" when a.cashmode = 8 and a.txntype = 5 and a.cashday < 20191201 then a.cashfee - y.cashamt2"+
				
				" when a.cashmode = 8 and a.txntype = 1 and a.cashday >= 20191201 then a.cashfee - y.cashrate"+
				" when a.cashmode = 8 and a.txntype = 2 and a.cashday >= 20191201 then a.cashfee - y.startamount"+
				" when a.cashmode = 8 and a.txntype = 3 and a.cashday >= 20191201 then a.cashfee - y.cashamt1"+
				" when a.cashmode = 8 and a.txntype = 4 and a.cashday >= 20191201 then a.cashfee - y.profitpercent1"+
				" when a.cashmode = 8 and a.txntype = 5 and a.cashday >= 20191201 then a.cashfee - y.cashamt2"+
				
				" when a.cashmode = 8 and a.txntype = 6 then a.cashfee - y.huabeiFee"+
				
				" when a.cashmode = 8 then a.cashfee - y.cashamt1"+
				" end as profit0"+
				" from check_micro_profittemplate y,check_unit_profitemplate uy"+
				" where uy.unno = :unno AND uy.aptid = y.aptid AND uy.status != 0 AND y.merchanttype = 6 AND y.mataintype != 'D' AND y.profitrule = a.mertype) as profit"+
				" ,a.mid, a.cashfee AS mda, a.unno, a.rname, a.cashamt AS txnamount, a.cashmode, a.mertype,a.txntype,a.cashday"+
				" from check_cash_"+month+" a where a.unno IN (SELECT UNNO FROM sys_unit START WITH unno = :unno AND status = 1 CONNECT BY NOCYCLE PRIOR unno = upper_unit)"+
				" and a.cashmode in (7,8) ";
		map.put("unitName",unitModel.getUnitName());
		map.put("unno",cptm.getUnno());
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.cashday>=:cashday1 and a.cashday<=:cashday2 " ;
			map.put("cashday1",start);
			map.put("cashday2",end);
		}else{
			sql+=" and a.cashday =to_char(sysdate-1,'yyyyMMdd')";
		}
		// 活动类型
		if(StringUtils.isNotEmpty(cptm.getRebateType())){
			sql+=" and a.mertype =:mertype";
			map.put("mertype",cptm.getRebateType());
		}
		sql+=" ) group by settmethod,mertype )a  ";
		return sql;
	}

	/**
	 * 代理商收银台分润汇总Sql
	 * */
	private String getUnitSytTemplateDetailDataScanSql(CheckProfitTemplateMicroBean cptm,Map parm) {
		cptm.setTxnDay(cptm.getTxnDay().replace("-", ""));
		cptm.setTxnDay1(cptm.getTxnDay1().replace("-", ""));
		String selectUnno = cptm.getUnno();
		String sql="SELECT nvl(round(SUM(profit), 2), 0) AS profit , nvl(SUM(mda), 0) AS mda , nvl(SUM(txnamount), 0) AS txnamount , "
				+ "unnoA unno,unnameA un_name, sum(txncount) AS txncount ,mertype ,nvl(minfo1,'21') minfo1,minfo2 FROM ( SELECT ( SELECT "
				/* +"nvl(mda - TXNAMOUNT * y.subrate, 0) " */
			
				+" CASE "
				+" WHEN a.mertype = '1' and a.minfo2 = '1'  THEN nvl(a.mda - a.txnamount * y.creditbankrate, 0)"
				+" WHEN a.mertype = '1' and a.minfo2 = '2' THEN nvl(a.mda - a.txnamount * y.scanrate, 0)"
				+" WHEN a.mertype = '1' and a.minfo2 = '3' THEN nvl(a.mda - a.txnamount * y.subrate, 0)"
				+" WHEN a.mertype = '2'  THEN nvl(a.mda - a.txnamount * y.scanrate1, 0)"
				+" WHEN a.mertype = '3'  THEN nvl(a.mda - a.txnamount * y.scanrate2, 0)"
				
				//ztt20191205统一扫码后添加判断mertype=5，minfo2=(1,2,3,4)
				+" WHEN a.mertype = '5' and a.minfo2 = '1'  THEN nvl(a.mda - a.txnamount * y.creditbankrate, 0)"
				+" WHEN a.mertype = '5' and a.minfo2 = '2'  THEN nvl(a.mda - a.txnamount * y.scanrate, 0)"
				+" WHEN a.mertype = '5' and a.minfo2 = '3'  THEN nvl(a.mda - a.txnamount * y.subrate, 0)"
				+" WHEN a.mertype = '5' and a.minfo2 = '4'  THEN nvl(a.mda - a.txnamount * y.scanrate1, 0)"
				//花呗
				+"WHEN a.mertype='6' then nvl(a.mda - a.txnamount * y.huabeirate, 0)"
				
				+" ELSE nvl(a.mda - a.txnamount * y.subrate, 0)"
				+" END AS profitone"
				
				+ " FROM check_micro_profittemplate y, check_unit_profitemplate uy WHERE uy.unno = :unno "

				+" and nvl(y.profitrule,'21')= nvl(a.minfo1,'21')"
				
			    + " AND uy.aptid = y.aptid AND "
				+ "uy.status != 0 AND y.merchanttype = 5 AND y.mataintype != 'D' ) AS profit, a.mda AS mda, a.unno, :unno as unnoA,"
				+ "b.un_name unnameA,"
				+ "a.TXNAMOUNT AS txnamount,a.txncount ,a.mertype,a.minfo1 ,minfo2 FROM pg_unno_wechat a,sys_unit b WHERE "
				+ "a.unno = :unno AND a.unno=b.unno AND a.ismpos = 3 ";
		parm.put("unno",selectUnno);
		if(cptm.getTxnDay()!=null && !"".equals(cptm.getTxnDay()) && cptm.getTxnDay1()!=null && !"".equals(cptm.getTxnDay1())){
			sql+=" and a.txnday>=:txnday1 and a.txnday<=:txnday2" ;
			parm.put("txnday1",cptm.getTxnDay());
			parm.put("txnday2",cptm.getTxnDay1());
		}
		//添加活动筛选
		if(cptm.getProfitRule()!=null && !"".equals(cptm.getProfitRule())){
			sql+=" and a.minfo1='"+cptm.getProfitRule()+"'" ;
		}
		sql+=" ) group by unnoA,unnameA,mertype,minfo2,minfo1";
		return sql;
	}
	@Override
	public HSSFWorkbook exportMposUnitProfitMicroSumDataCash(CheckProfitTemplateMicroBean cptm, UserBean userBean) {
		if(cptm.getTxnDay()==null||"".equals(cptm.getTxnDay())||cptm.getTxnDay1()==null||"".equals(cptm.getTxnDay1())||!cptm.getTxnDay().substring(0, 6).equals(cptm.getTxnDay1().substring(0, 6))) {
			return null;
		}
		Map map = new HashMap();
		String sql = getUnitMposProfitMicroSumDataCash(cptm,map);
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, map, cptm.getPage(), cptm.getRows());
			List<String> excelHeader = new ArrayList<String>();
			Map<String, Object> headMap = new HashMap<String, Object>();
			excelHeader.add("UNNO");
			excelHeader.add("UNITNAME");
			excelHeader.add("MERTYPE");
			excelHeader.add("SETTMETHOD");
			excelHeader.add("TXNCOUNT");
			excelHeader.add("MDA");
			excelHeader.add("PROFIT");

			headMap.put("UNNO", "机构号");
			headMap.put("UNITNAME", "机构名称");
			headMap.put("MERTYPE", "活动类型");
			headMap.put("SETTMETHOD", "交易类型");
			headMap.put("TXNCOUNT", "提现总笔数");
			headMap.put("MDA", "提现手续费");
			headMap.put("PROFIT", "提现转账分润");
			

			return ExcelUtil.export("代理商MPOS活动提现分润汇总", list, excelHeader, headMap);
	}

	@Override
	public HSSFWorkbook exportUnitProfitMicroSumDataCash2(CheckProfitTemplateMicroBean cptm, UserBean userBean) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, cptm.getUnno());
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptm.getUnno());
		String sql = getSytCashSql(cptm, unitModel, childUnno);
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, null, cptm.getPage(), cptm.getRows());
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNITNAME");
		excelHeader.add("TXNCOUNT");
		excelHeader.add("TXNAMOUNT");
		excelHeader.add("MDA");
		excelHeader.add("MERTYPE");
		excelHeader.add("PROFIT");
		excelHeader.add("SETTMETHOD");

		headMap.put("UNITNAME", "机构名称");
		headMap.put("TXNCOUNT", "提现总笔数");
		headMap.put("TXNAMOUNT", "提现总金额");
		headMap.put("MDA", "手续费总金额");
		headMap.put("MERTYPE", "活动类型");
		headMap.put("PROFIT", "分润总金额");
		headMap.put("SETTMETHOD", "交易类型");
		
		return ExcelUtil.export("代理商收银台提现转账分润汇总", list, excelHeader, headMap);
	}

	@Override
	public HSSFWorkbook exportUnitSytTemplateDetailDataScan(CheckProfitTemplateMicroBean cptm, UserBean userBean) {
		if(cptm.getTxnDay()==null||"".equals(cptm.getTxnDay())||cptm.getTxnDay1()==null||"".equals(cptm.getTxnDay1())) {
			return null;
		}
		Map parm = new HashMap();
		String sql = getUnitSytTemplateDetailDataScanSql(cptm,parm);
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, parm, cptm.getPage(), cptm.getRows());
		for (Map<String, Object> map : list) {
			String MERTYPE = map.get("MERTYPE")==null?"":map.get("MERTYPE").toString();
			String MINFO2 = map.get("MINFO2")==null?"":map.get("MINFO2").toString();
			if("1".equals(MERTYPE)) {
				if("1".equals(MINFO2)) {
					map.put("SETTMETHOD", "微信0.38");
				}else if("2".equals(MINFO2)) {
					map.put("SETTMETHOD", "微信0.45");
				}else if("3".equals(MINFO2)) {
					map.put("SETTMETHOD", "微信（老）");
				}else {
					map.put("SETTMETHOD", "微信");
				}
			}else if("2".equals(MERTYPE)) {
				map.put("SETTMETHOD", "支付宝");
			}else if("3".equals(MERTYPE)) {
				map.put("SETTMETHOD", "银联二维码");
			}else if("5".equals(MERTYPE)) {
				if("1".equals(MINFO2)) {
					map.put("SETTMETHOD", "扫码1000以上（终端0.38");
				}else if("2".equals(MINFO2)) {
					map.put("SETTMETHOD", "扫码1000以上（终端0.45）");
				}else if("3".equals(MINFO2)) {
					map.put("SETTMETHOD", "扫码1000以下（终端0.38）");
				}else if("4".equals(MINFO2)) {
					map.put("SETTMETHOD", "扫码1000以下（终端0.45）");
				}
			}else if("6".equals(MERTYPE)) {
				map.put("SETTMETHOD", "花呗");
			}else {
				map.put("SETTMETHOD", "其他");
			}
			
			String PROFIT = map.get("PROFIT")==null?"":map.get("PROFIT").toString();
			if("0".compareTo(PROFIT)>= 0) {
				map.put("PROFIT", 0);
			}
		}
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNNO");
		excelHeader.add("UN_NAME");
		excelHeader.add("TXNCOUNT");
		excelHeader.add("MDA");
		excelHeader.add("TXNAMOUNT");
		excelHeader.add("PROFIT");
		excelHeader.add("MINFO1");
		excelHeader.add("SETTMETHOD");

		headMap.put("UNNO", "机构号");
		headMap.put("UN_NAME", "机构名称");
		headMap.put("TXNCOUNT", "交易总笔数");
		headMap.put("MDA", "商户手续费");
		headMap.put("TXNAMOUNT", "交易总金额");
		headMap.put("PROFIT", "分润金额");
		headMap.put("MINFO1", "活动类型");
		headMap.put("SETTMETHOD", "交易类型");
		
		return ExcelUtil.export("代理商收银台分润汇总", list, excelHeader, headMap);
	}

	@Override
	public HSSFWorkbook exportUnitProfitMicroSumData(CheckProfitTemplateMicroBean cptm, UserBean userBean) {
		String sql = getUnitProfitMicroSumDataSql(cptm);
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, null, cptm.getPage(), cptm.getRows());
		for (Map<String, Object> map : list) {
			String T = map.get("T")==null?"":map.get("T").toString();
			if ("2".equals(T)){
				map.put("T", "秒到(0.72%)");
			}else if("1".equals(T)){
				map.put("T", "理财");
			}else if ("3".equals(T)){
				map.put("T", "秒到(非0.72%)");
			}else if ("4".equals(T)){
				map.put("T", "云闪付");
			}
		}
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNITNAME");
		excelHeader.add("T");
		excelHeader.add("TXNCOUNT");
		excelHeader.add("TXNAMOUNT");
		excelHeader.add("PROFIT");

		headMap.put("UNITNAME", "机构名称");
		headMap.put("T", "结算类型");
		headMap.put("TXNCOUNT", "交易总笔数");
		headMap.put("TXNAMOUNT", "交易总金额");
		headMap.put("PROFIT", "分润金额");
		
		return ExcelUtil.export("代理商手刷刷卡分润汇总", list, excelHeader, headMap);
	}

	@Override
	public HSSFWorkbook exportUnitProfitMicroSumDataScan(CheckProfitTemplateMicroBean cptm, UserBean userBean) {
		String sql = getUnitProfitMicroSumDataScanSql(cptm);
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlList2(sql, null, cptm.getPage(), cptm.getRows());
		for (Map<String, Object> map : list) {
			String SETTMETHOD = map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString();
			if ("000001".equals(SETTMETHOD)){
				   map.put("SETTMETHOD", "理财微信");
				}else if ("000002".equals(SETTMETHOD)){
					map.put("SETTMETHOD", "理财支付宝");
				}else if ("000003".equals(SETTMETHOD)){
					map.put("SETTMETHOD", "理财银联二维码");
				}else if("100001".equals(SETTMETHOD)){
					map.put("SETTMETHOD", "秒到微信");
				}else if("100002".equals(SETTMETHOD)){
					map.put("SETTMETHOD", "秒到支付宝");
				}else if("100003".equals(SETTMETHOD)){
					map.put("SETTMETHOD", "秒到银联二维码");
				}else if("800000".equals(SETTMETHOD)){
					map.put("SETTMETHOD", "快捷VIP");
				}else if("800001".equals(SETTMETHOD)){
					map.put("SETTMETHOD", "快捷完美");
				}else if("100004".equals(SETTMETHOD)){
					map.put("SETTMETHOD", "扫码1000以下");
				}else if("100005".equals(SETTMETHOD)){
					map.put("SETTMETHOD", "扫码1000以上");
				}
		}
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNITNAME");
		excelHeader.add("SETTMETHOD");
		excelHeader.add("TXNCOUNT");
		excelHeader.add("TXNAMOUNT");
		excelHeader.add("PROFIT");

		headMap.put("UNITNAME", "机构名称");
		headMap.put("SETTMETHOD", "结算类型");
		headMap.put("TXNCOUNT", "交易总笔数");
		headMap.put("TXNAMOUNT", "交易总金额");
		headMap.put("PROFIT", "分润金额");
		
		return ExcelUtil.export("代理商手刷扫码分润汇总", list, excelHeader, headMap);
	}

	@Override
	public HSSFWorkbook exportUnitProfitMicroSumDataCash(CheckProfitTemplateMicroBean cptm, UserBean userBean) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, cptm.getUnno());
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptm.getUnno());
		String sql = getMposCashSql(cptm, unitModel, childUnno);
		List<Map<String,Object>> list= checkUnitProfitMicroDao.queryObjectsBySqlListMap2(sql, null);
		for (Map<String, Object> map : list) {
			String SETTMETHOD = map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString();
			if ("000001".equals(SETTMETHOD)){
				map.put("SETTMETHOD", "理财刷卡");
			}else if("000002".equals(SETTMETHOD)){
				map.put("SETTMETHOD", "理财扫码");
			}else if("100001".equals(SETTMETHOD)){
				map.put("SETTMETHOD", "秒到刷卡");
			}else if("100002".equals(SETTMETHOD)){
				map.put("SETTMETHOD", "秒到扫码");
			}else if("100004".equals(SETTMETHOD)){
				map.put("SETTMETHOD", "扫码1000以下");
			}else if("100005".equals(SETTMETHOD)){
				map.put("SETTMETHOD", "扫码1000以上");
			}else if("100006".equals(SETTMETHOD)){
				map.put("SETTMETHOD", "二维码");
			}
		}
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNITNAME");
		excelHeader.add("SETTMETHOD");
		excelHeader.add("TXNCOUNT");
		excelHeader.add("TXNAMOUNT");
		excelHeader.add("MDA");
		excelHeader.add("PROFIT");

		headMap.put("UNITNAME", "机构名称");
		headMap.put("SETTMETHOD", "结算类型");
		headMap.put("TXNCOUNT", "提现总笔数");
		headMap.put("TXNAMOUNT", "提现总金额");
		headMap.put("MDA", "手续费总金额");
		headMap.put("PROFIT", "分润总金额");
		
		return ExcelUtil.export("代理商手刷提现转账分润汇总", list, excelHeader, headMap);
	}
	
	/* 活动类查询subtype */
	private String querySubTypeByPlusProfitRule(Integer rebateType) {
		String sql ="select t.subtype from  bill_purconfigure t where t.type=3 and t.valuestring='rate' and t.prod='plus' and t.valueinteger='"+rebateType+"'";
		List<Map<String, Object>> typeList = checkUnitProfitMicroDao.queryObjectsBySqlList(sql);
		if(typeList.size()==0) {
			return null;
		}
		Map<String, Object> obj = typeList.get(0);
		Object subtype=obj.get("SUBTYPE");
		if(subtype!=null){
			return subtype.toString();
		}
		return null;
	}
}
