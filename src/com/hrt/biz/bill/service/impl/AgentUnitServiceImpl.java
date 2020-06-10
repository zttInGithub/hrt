package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;

import com.hrt.biz.bill.dao.IBillFileRecordDao;
import com.hrt.biz.bill.dao.IBillSaleUnnoDao;
import com.hrt.biz.bill.dao.IHrtUnnoCostNDao;
import com.hrt.biz.bill.entity.model.BillFileRecordModel;
import com.hrt.biz.bill.entity.model.BillSaleUnnoModel;
import com.hrt.biz.bill.entity.model.HrtUnnoCostNModel;
import com.hrt.util.DateTools;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.argorse.security.core.hash.MD5Wrapper;
import com.hrt.biz.bill.dao.AgentDlUnitDao;
import com.hrt.biz.bill.dao.BillBpFileDao;
import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.dao.IAgentUnitDao;
import com.hrt.biz.bill.dao.IHrtUnnoCostDao;
import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.entity.model.AgentDLUnitModel;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.biz.bill.entity.model.BillBpFileModel;
import com.hrt.biz.bill.entity.model.HrtUnnoCostModel;
import com.hrt.biz.bill.entity.model.HrtUnnoCostWModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.pagebean.AgentUnitBean;
import com.hrt.biz.bill.entity.pagebean.HrtUnnoCostBean;
import com.hrt.biz.bill.service.IAgentUnitService;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.check.service.CheckUnitProfitMicroService;
import com.hrt.biz.check.service.CheckUnitProfitTraditService;
import com.hrt.biz.credit.service.CreditAgentService;
import com.hrt.biz.util.constants.HrtCostConstant;
import com.hrt.frame.dao.sysadmin.IRoleDao;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.dao.sysadmin.IUserDao;
import com.hrt.frame.entity.model.RoleModel;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.model.UserModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UnitBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;
import com.hrt.gmms.webservice.AgentUnitInfo;
import com.hrt.gmms.webservice.IGmmsService;
import com.hrt.util.ClassToJavaBeanUtil;
import com.hrt.util.HttpXmlClient;
import com.hrt.util.StringUtil;

import sun.management.resources.agent;

public class AgentUnitServiceImpl implements IAgentUnitService {
	
	private IAgentUnitDao agentUnitDao;
	private IGmmsService gsClient;
	private IUnitDao unitDao;
	private IRoleDao roleDao;
	private IUserDao userDao;
	private IAgentSalesDao agentSalesDao;
	private IMerchantInfoService merchantInfoService;
	private CreditAgentService creditAgentService;
	private String admAppIp;
	private AgentDlUnitDao agentDlUnitDao;
	private IMerchantInfoDao merchantInfoDao;
	private String hrtAppUrl;
	private IHrtUnnoCostDao hrtUnnoCostDao;
	private BillBpFileDao billBpFileDao;
	private CheckUnitProfitMicroService checkUnitProfitMicroService;
	private CheckUnitProfitTraditService checkUnitProfitTraditService;
	private IBillFileRecordDao billFileRecordDao;
	private IBillSaleUnnoDao billSaleUnnoDao;
	private IHrtUnnoCostNDao hrtUnnoCostNDao;

	public IHrtUnnoCostNDao getHrtUnnoCostNDao() {
		return hrtUnnoCostNDao;
	}

	public void setHrtUnnoCostNDao(IHrtUnnoCostNDao hrtUnnoCostNDao) {
		this.hrtUnnoCostNDao = hrtUnnoCostNDao;
	}

	public IBillSaleUnnoDao getBillSaleUnnoDao() {
		return billSaleUnnoDao;
	}

	public void setBillSaleUnnoDao(IBillSaleUnnoDao billSaleUnnoDao) {
		this.billSaleUnnoDao = billSaleUnnoDao;
	}

	public IBillFileRecordDao getBillFileRecordDao() {
		return billFileRecordDao;
	}

	public void setBillFileRecordDao(IBillFileRecordDao billFileRecordDao) {
		this.billFileRecordDao = billFileRecordDao;
	}

	public CheckUnitProfitTraditService getCheckUnitProfitTraditService() {
		return checkUnitProfitTraditService;
	}

	public void setCheckUnitProfitTraditService(CheckUnitProfitTraditService checkUnitProfitTraditService) {
		this.checkUnitProfitTraditService = checkUnitProfitTraditService;
	}

	public CheckUnitProfitMicroService getCheckUnitProfitMicroService() {
		return checkUnitProfitMicroService;
	}

	public void setCheckUnitProfitMicroService(CheckUnitProfitMicroService checkUnitProfitMicroService) {
		this.checkUnitProfitMicroService = checkUnitProfitMicroService;
	}

	public IHrtUnnoCostDao getHrtUnnoCostDao() {
		return hrtUnnoCostDao;
	}
	public void setHrtUnnoCostDao(IHrtUnnoCostDao hrtUnnoCostDao) {
		this.hrtUnnoCostDao = hrtUnnoCostDao;
	}
	public BillBpFileDao getBillBpFileDao() {
		return billBpFileDao;
	}
	public void setBillBpFileDao(BillBpFileDao billBpFileDao) {
		this.billBpFileDao = billBpFileDao;
	}
	
	//对象转换
	public Class<Object> obj(Class a){
		Class<Object> c=a;
		return c;
	}

	public IMerchantInfoDao getMerchantInfoDao() {
		return merchantInfoDao;
	}
	public void setMerchantInfoDao(IMerchantInfoDao merchantInfoDao) {
		this.merchantInfoDao = merchantInfoDao;
	}
	public String getHrtAppUrl() {
		return hrtAppUrl;
	}
	public void setHrtAppUrl(String hrtAppUrl) {
		this.hrtAppUrl = hrtAppUrl;
	}
	public void setAgentDlUnitDao(AgentDlUnitDao agentDlUnitDao) {
		this.agentDlUnitDao = agentDlUnitDao;
	}
	public AgentDlUnitDao getAgentDlUnitDao() {
		return agentDlUnitDao;
	}
	private static final Log log = LogFactory.getLog(AgentUnitServiceImpl.class);
	public CreditAgentService getCreditAgentService() {
		return creditAgentService;
	}
	public void setCreditAgentService(CreditAgentService creditAgentService) {
		this.creditAgentService = creditAgentService;
	}
	public IGmmsService getGsClient() {
		return gsClient;
	}
	public void setGsClient(IGmmsService gsClient) {
		this.gsClient = gsClient;
	}
	public IAgentUnitDao getAgentUnitDao() {
		return agentUnitDao;
	}
	public void setAgentUnitDao(IAgentUnitDao agentUnitDao) {
		this.agentUnitDao = agentUnitDao;
	}
	public IUnitDao getUnitDao() {
		return unitDao;
	}
	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}
	public IRoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public IUserDao getUserDao() { 
		return userDao;
	}
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	public IAgentSalesDao getAgentSalesDao() {
		return agentSalesDao;
	}
	public void setAgentSalesDao(IAgentSalesDao agentSalesDao) {
		this.agentSalesDao = agentSalesDao;
	}

	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}
	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}
	
	public String getAdmAppIp() {
		return admAppIp;
	}
	public void setAdmAppIp(String admAppIp) {
		this.admAppIp = admAppIp;
	}
	/**
	 * 查询表格机构
	 */
	@Override
	public DataGridBean queryUnits(AgentUnitBean agentUnit,UserBean user) throws Exception {
		DataGridBean dataGridBean = new DataGridBean();
		String sql = "";
		if (user != null && user.getUnNo() != null) {
			//hql = "from UnitModel u where u.status = 1 and u.parent.unNo = '" + user.getUnNo()+"'";
			sql = "select * from sys_unit where un_lvl>=1 start with unno = '"+user.getUnNo()+"' connect by prior unno= upper_unit  order by upper_unit asc";
		}else{
			return null;
		}
		if(agentUnit.getUnno()!=null&&!"".equals(agentUnit.getUnno())){
			List<Map<String, Object>> unnolist = unitDao.queryObjectsBySqlObject(sql);
			for (Map<String, Object> unlist : unnolist) {
				if (unlist.get("UNNO").equals(agentUnit.getUnno())) {
					sql="select * from sys_unit where un_lvl>=1 start with unno = '"+agentUnit.getUnno()+"' connect by prior unno= upper_unit  order by upper_unit asc";
					break;
				}
			}
		}
		String sqlCount =" Select Count (*) From ("+sql+")";
		List<Map<String, String>> maps = unitDao.queryObjectsBySqlList(sql, null, agentUnit.getPage(), agentUnit.getRows());
		Integer count = unitDao.querysqlCounts2(sqlCount, null);
		dataGridBean.setRows(maps);
		dataGridBean.setTotal(count);
		return dataGridBean;
	}

	@Override
	public List<Map<String, Object>> exportUnits(AgentUnitBean agentUnit, UserBean user) {
		String sql = "";
		if (user != null && user.getUnNo() != null) {
			//hql = "from UnitModel u where u.status = 1 and u.parent.unNo = '" + user.getUnNo()+"'";
			sql = "select * from sys_unit where un_lvl>=1 start with unno = '"+user.getUnNo()+"' connect by prior unno= upper_unit  order by upper_unit asc";
		}else{
			return null;
		}
		if(agentUnit.getUnno()!=null&&!"".equals(agentUnit.getUnno())){
			List<Map<String, Object>> unnolist = unitDao.queryObjectsBySqlObject(sql);
			for (Map<String, Object> unlist : unnolist) {
				if (unlist.get("UNNO").equals(agentUnit.getUnno())) {
					sql="select * from sys_unit where un_lvl>=1 start with unno = '"+agentUnit.getUnno()+"' connect by prior unno= upper_unit  order by upper_unit asc";
					break;
				}
			}
		}
		List<Map<String, Object>> maps  = unitDao.queryObjectsBySqlObject(sql);
		return maps;
	}

	/**
	 * 转换UnitModel为treegrid格式
	 */
	private UnitBean changeModelToBean(UnitModel unitModel, boolean recursive,String unnovalue) {
		UnitBean unitBean = new UnitBean();
		unitBean.setUnNo(unitModel.getUnNo().trim());
		unitBean.setUnitName(unitModel.getUnitName());
		unitBean.setCreateDate(unitModel.getCreateDate());
		unitBean.setCreateUser(unitModel.getCreateUser());
		unitBean.setUpdateDate(unitModel.getUpdateDate());
		unitBean.setUpdateUser(unitModel.getUpdateUser());
		unitBean.setProvinceCode(unitModel.getProvinceCode());
		unitBean.setUnLvl(unitModel.getUnLvl());
		unitBean.setStatus(unitModel.getStatus());
		unitBean.setUnAttr(unitModel.getUnAttr());
		unitBean.setUnitCode(unitModel.getUnitCode());
		
		if (unitModel.getChildren() != null && unitModel.getChildren().size() > 0) {
			if (recursive) {
				Set<UnitModel> unitSet = unitModel.getChildren();
				List<UnitBean> unitList = new ArrayList<UnitBean>();
				for (UnitModel unit : unitSet) {
					if((unnovalue==null||"".equals(unnovalue))||unnovalue.indexOf(unit.getUnNo())>0){
						UnitBean subUnit = changeModelToBean(unit, true,unnovalue);
						unitList.add(subUnit);
					}
				}
				unitBean.setChildren(unitList);
			}
		} 
		
		return unitBean;
	}
	
	/**
	 * 开通代理商时判断机构编号后三位是否符合规则
	 */
	private String addUnNoIsNumber(String unNo){
		String hql="select max(u.unNo) from UnitModel u where u.unNo like :unNo";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("unNo", unNo+'%');
		String result = unitDao.queryUnitMaxUnNo(hql, map);
		if(result != null){
			String u = result.substring(3, 6);
			if(Integer.parseInt(u)==999){
				throw new IllegalAccessError("开通代理商失败：unNo"+unNo);
			}
			//result = unNo +(Integer.parseInt(u)+1001+"").substring(1,3);
			result = unNo +String.format("%03d", Integer.parseInt(u)+1);
		}else{
			result = unNo+"000";
		}
		return result.toString();
	}
	
	/**
	 * 方法功能：格式化AgentUnitModel为datagrid数据格式
	 * 参数：agentUnitList
	 * 		total 总记录数
	 */
	private DataGridBean formatToDataGrid(List<AgentUnitModel> agentUnitList, long total) {
		List<AgentUnitBean> agentUnitBeanList = new ArrayList<AgentUnitBean>();
		for(AgentUnitModel agentUnitModel : agentUnitList) {
			AgentUnitBean agentUnitBean = new AgentUnitBean();
			BeanUtils.copyProperties(agentUnitModel, agentUnitBean);
			
			if(agentUnitModel.getAmountConfirmDate() != null){
				agentUnitBean.setAmountConfirmName("已缴款");
			}else{
				agentUnitBean.setAmountConfirmName("未缴款");
			}
			
			if(agentUnitModel.getOpenDate() != null){
				agentUnitBean.setOpenName("已开通");
			}else{
				agentUnitBean.setOpenName("未开通");
			}
			
			AgentSalesModel agentSales = agentSalesDao.getObjectByID(AgentSalesModel.class, agentUnitModel.getMaintainUserId());
			if(agentSales != null){
				agentUnitBean.setMaintainUserName(agentSales.getSaleName());
				agentUnitBean.setSignUserIdName(agentSales.getSaleName());
			}
			
			if("1".equals(agentUnitModel.getLegalType())){
				agentUnitBean.setLegalTypeName("身份证");
			}else if("2".equals(agentUnitModel.getLegalType())){
				agentUnitBean.setLegalTypeName("军官证");
			}else if("3".equals(agentUnitModel.getLegalType())){
				agentUnitBean.setLegalTypeName("护照");
			}else if("4".equals(agentUnitModel.getLegalType())){
				agentUnitBean.setLegalTypeName("港澳通行证");
			}else{
				agentUnitBean.setLegalTypeName("其他");
			}
			
			if("1".equals(agentUnitModel.getAccType())){
				agentUnitBean.setAccTypeName("对公");
			}else{
				agentUnitBean.setAccTypeName("对私");
			}
			
			if("1".equals(agentUnitModel.getBankType())){
				agentUnitBean.setBankTypeName("交通银行");
			}else{
				agentUnitBean.setBankTypeName("非交通银行");
			}
			
			if("1".equals(agentUnitModel.getAreaType())){
				agentUnitBean.setAreaTypeName("北京");
			}else{
				agentUnitBean.setAreaTypeName("非北京");
			}
			
			//签约人员名称
			if(agentUnitModel.getSignUserId() != null){
				String name = signUserIdName(agentUnitModel.getSignUserId());
				if(name != null && !"".equals(name)){
					agentUnitBean.setSignUserIdName(name);
					agentUnitBean.setMaintainUserIdName(name);
				}
			}
//			if(agentUnitModel.getMaintainUserId() != null){
//				if(signUserIdName(agentUnitModel.getMaintainUserId()) != null && !"".equals(signUserIdName(agentUnitModel.getSignUserId()).trim())){
//					agentUnitBean.setMaintainUserIdName(signUserIdName(agentUnitModel.getSignUserId()));
//				}
//			}
			if(agentUnitModel.getUnno()!=null&&!"".equals(agentUnitModel.getUnno())){
				UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, agentUnitModel.getUnno());
				if(unitModel != null){
					agentUnitBean.setStatusName(unitModel.getStatus());
				}
			}
			agentUnitBeanList.add(agentUnitBean);
		}
		
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(agentUnitBeanList);
		
		return dgb;
	}
	
	/**
	 * 获得业务人员名称
	 */
	private String signUserIdName(Integer busid){
		String sql = "SELECT SALENAME FROM BILL_AGENTSALESINFO A WHERE A.BUSID = " + busid;
		List<Map<String, String>> s = agentSalesDao.queryObjectsBySqlList(sql);
		if(s != null && s.size() > 0){
			return s.get(0).get("SALENAME");
		}
		return null;
	}

	/**
	 * 查询代理商信息（未缴款）
	 */
	@Override
	public DataGridBean queryAgentUnit(AgentUnitBean agentUnit,UserBean user)
			throws Exception {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "";
		String hqlCount = "";
		if("110000".equals(user.getUnNo())){ 
			 hql = "from AgentUnitModel a where a.maintainType != :maintainType and openDate is null and agentLvl is null ";
			 hqlCount = "select count(*) from AgentUnitModel a where a.maintainType != :maintainType and openDate is null and agentLvl is null ";
			map.put("maintainType", "D");
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//总公司&部门机构
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
				 hql = "from AgentUnitModel a where a.maintainType != :maintainType and openDate is null and agentLvl is null ";
				 hqlCount = "select count(*) from AgentUnitModel a where a.maintainType != :maintainType and openDate is null and agentLvl is null ";
				map.put("maintainType", "D");
			}
		}else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){     //单位机构&分公司
			hql = " from AgentUnitModel a where a.maintainType != :maintainType and openDate is null and agentLvl is null and ( " +
					" a.unno in (select b.unNo from UnitModel b where b.parent.unNo = :unno  OR unNo = :unno ) ";
			hqlCount = "SELECT COUNT(*) from AgentUnitModel a where a.maintainType != :maintainType and openDate is null and agentLvl is null and (  " +
					" a.unno in (select b.unNo from UnitModel b where b.parent.unNo = :unno  OR unNo = :unno ) ";
			map.put("unno", user.getUnNo());
			map.put("maintainType", "D");
			
			//针对查询一代退回 但是还没机构号 
			String sqlD = "select * from bill_agentsalesinfo where MAINTAINTYPE!='D' and user_id=" + user.getUserID();
			List<AgentSalesModel> agentSaleslist = agentSalesDao.executeSqlT(sqlD, AgentSalesModel.class,
					new HashMap<String, Object>());
			if (agentSaleslist.size() > 0) {
				Integer busid = agentSaleslist.get(0).getBusid();
				hql += " or a.signUserId='" + busid + "') ";
				hqlCount += " or a.signUserId='" + busid + "') ";
			}else{
				hql += " or a.parentUnno='" + user.getUnNo() + "') ";
				hqlCount += " or a.parentUnno='" + user.getUnNo() + "') ";
			}
		}else{
			hql = " from AgentUnitModel a where a.maintainType != :maintainType and openDate is null and a.unno = :unno and agentLvl is null ";
			hqlCount = "SELECT COUNT(*) from AgentUnitModel a where a.maintainType != :maintainType and openDate is null and a.unno = :unno and agentLvl is null ";
			map.put("unno", user.getUnNo());
			map.put("maintainType", "D");
		}
		if (StringUtil.isNotEmpty(agentUnit.getAgentName()) ) {
			hql +=" and agentname like :agentName";
			hqlCount +=" and agentname like :agentName";
			map.put("agentName", '%'+agentUnit.getAgentName()+'%');
		}	
		if (StringUtil.isNotEmpty(agentUnit.getSort())) {
			hql += " order by " + agentUnit.getSort() + " " + agentUnit.getOrder();
		}
		
		List<AgentUnitModel> agentUnitList = agentUnitDao.queryAgentUnit(hql, map, agentUnit.getPage(), agentUnit.getRows());
		Long counts = agentUnitDao.queryCounts(hqlCount, map);
		
		DataGridBean dataGridBean = formatToDataGrid(agentUnitList, counts);
		
		return dataGridBean;
	}

	@Override
	public DataGridBean queryAgentUnit00410(AgentUnitBean agentUnit,UserBean user)
			throws Exception {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "";
		String hqlCount = "";
		if("110000".equals(user.getUnNo())){ 
			 hql = "from AgentUnitModel a where a.maintainType != :maintainType and openDate is null and agentLvl IN (1,2) ";
			 hqlCount = "select count(*) from AgentUnitModel a where a.maintainType != :maintainType and openDate is null and agentLvl =1 ";
			map.put("maintainType", "D");
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){	//总公司&部门机构
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
				 hql = "from AgentUnitModel a where a.maintainType != :maintainType and openDate is null and agentLvl IN (1,2) ";
				 hqlCount = "select count(*) from AgentUnitModel a where a.maintainType != :openDate and amountConfirmDate is null and agentLvl =1 ";
				map.put("maintainType", "D");
			}
		}else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){  //单位机构&分公司
			hql = " from AgentUnitModel a where a.maintainType != :maintainType and openDate is null and agentLvl IN (1,2) and (  " +
					"  a.unno in (select b.unNo from UnitModel b where b.parent.unNo = :unno  OR unNo = :unno ) ";
			hqlCount = "SELECT COUNT(*) from AgentUnitModel a where a.maintainType != :maintainType and openDate is null and agentLvl =1 and ( " +
					"  a.unno in (select b.unNo from UnitModel b where b.parent.unNo = :unno  OR unNo = :unno ) ";
			map.put("unno", "110000");
			map.put("maintainType", "D");
			
			//针对查询一代退回 但是还没机构号 
			String sqlD = "select * from bill_agentsalesinfo where MAINTAINTYPE!='D' and user_id=" + user.getUserID();
			List<AgentSalesModel> agentSaleslist = agentSalesDao.executeSqlT(sqlD, AgentSalesModel.class,
					new HashMap<String, Object>());
			if (agentSaleslist.size() > 0) {
				Integer busid = agentSaleslist.get(0).getBusid();
				hql += " or a.signUserId='" + busid + "') ";
				hqlCount += " or a.signUserId='" + busid + "') ";
			}else{
				hql += " or a.parentUnno='110000') ";
				hqlCount += " or a.parentUnno='110000') ";
			}
			
		}else{
			hql = " from AgentUnitModel a where a.maintainType != :maintainType and openDate is null and a.unno = :unno and agentLvl IN (1,2) ";
			hqlCount = "SELECT COUNT(*) from AgentUnitModel a where a.maintainType != :maintainType and openDate is null and a.unno = :unno and agentLvl IN (1,2) ";
			map.put("unno", user.getUnNo());
			map.put("maintainType", "D");
		}
		
		if (StringUtil.isNotEmpty(agentUnit.getAgentName()) ) {
			hql +=" and agentname like :agentName";
			hqlCount +=" and agentname like :agentName";
			map.put("agentName", '%'+agentUnit.getAgentName()+'%');
		}	
		if (StringUtil.isNotEmpty(agentUnit.getSort())) {
			hql += " order by " + agentUnit.getSort() + " " + agentUnit.getOrder();
		}
		
		List<AgentUnitModel> agentUnitList = agentUnitDao.queryAgentUnit(hql, map, agentUnit.getPage(), agentUnit.getRows());
		Long counts = agentUnitDao.queryCounts(hqlCount, map);
		
		DataGridBean dataGridBean = formatToDataGrid(agentUnitList, counts);
		
		return dataGridBean;
	}

	
	/**
	 * 添加代理商
	 */
	@Override
	public void saveAgentUnit(AgentUnitBean agentUnit, UserBean user)
			throws Exception {
		UnitModel unitModel1 = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if(unitModel1.getUnLvl() !=1 && unitModel1.getUnLvl() !=0){ //非 0-总公司     1-分公司
			AgentUnitModel agentUnitModel = new AgentUnitModel();
			BeanUtils.copyProperties(agentUnit, agentUnitModel);
			
			UnitModel unitModel = new UnitModel();
			UnitModel parent = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
			
			//添加机构
			String provinceCode = parent.getProvinceCode().replaceAll(",", "").trim();		//区域编号
			Integer childUnlvl=0;
			String childUnlvlA ="0";
			
			if(unitModel1.getUnLvl()==3){ //3-二级作业中心/二级代理机构
				//父级为二级代理(因为unlvl 没有4)
				childUnlvl=unitModel1.getUnLvl()+2;
				childUnlvlA = childUnlvl+"";
			}else if(unitModel1.getUnLvl()<9){
				//父级代理
				childUnlvl=unitModel1.getUnLvl()+1;
				childUnlvlA = childUnlvl+"";
			}else if (unitModel1.getUnLvl()==9){
				childUnlvl=unitModel1.getUnLvl()+1;
				childUnlvlA = "A";
			}else if (unitModel1.getUnLvl()==10){
				childUnlvl=unitModel1.getUnLvl()+1;
				childUnlvlA = "B";
			}else if (unitModel1.getUnLvl()==11){
				childUnlvl=unitModel1.getUnLvl()+1;
				childUnlvlA = "C";
			}else {
				int i = 1/0;
			}
			
			agentUnitModel.setMaintainUserId(agentUnit.getSignUserId());		//添加的时候签约人员和维护人员一样
			agentUnitModel.setMaintainUid(user.getUserID());
			agentUnitModel.setMaintainDate(new Date());
			agentUnitModel.setAmountConfirmDate(new Date());
			agentUnitModel.setOpenDate(new Date());
			agentUnitModel.setMaintainType("M");			//A-add   M-Modify  D-Delete
			agentUnitModel.setApproveStatus("Y");   //二代之后 默认不需要审核
			agentUnitModel.setRno("******");
			agentUnitModel.setRegistryNo("******");
			agentUnitModel.setBankOpenAcc("******");
			uploadImg(agentUnit,agentUnitModel,user,false);
			if(agentUnit.getBankAccName().indexOf("交通")>-1){
				agentUnitModel.setBankType("1");
			}else{
				agentUnitModel.setBankType("2");
			}
			if(agentUnit.getBankArea().indexOf("北京")>-1){
				agentUnitModel.setAreaType("1");
			}else{
				agentUnitModel.setAreaType("2");
			}
			String unitType = "";
			if("a".equals(parent.getUnNo().substring(0, 1))||"b".equals(parent.getUnNo().substring(0, 1))||
					"c".equals(parent.getUnNo().substring(0, 1))||"d".equals(parent.getUnNo().substring(0, 1))||
					"e".equals(parent.getUnNo().substring(0, 1))||"f".equals(parent.getUnNo().substring(0, 1))||
					"g".equals(parent.getUnNo().substring(0, 1))||"h".equals(parent.getUnNo().substring(0, 1))||
					"i".equals(parent.getUnNo().substring(0, 1))||"j".equals(parent.getUnNo().substring(0, 1))||
					"k".equals(parent.getUnNo().substring(0, 1))) {
				unitType = addUnno(provinceCode,childUnlvlA,true);
			}else {
				unitType = addUnno(provinceCode,childUnlvlA,false);
			}
			
			agentUnitModel.setUnno(unitType);
			agentUnitDao.saveObject(agentUnitModel);
			
			unitModel.setProvinceCode(provinceCode);
			//unitModel.setUnitCode(agentUnit.getUnitCode());
			unitModel.setSeqNo(1000);
			unitModel.setSeqNo2(4000);
			unitModel.setParent(parent);
			unitModel.setUnNo(unitType);
			unitModel.setUnitName(agentUnitModel.getAgentName());
			unitModel.setUnLvl(childUnlvl);//N级代理
			unitModel.setStatus(1);
			unitModel.setCreateDate(new Date());
			unitModel.setCreateUser(user.getLoginName());
			unitModel.setUnAttr(1);
			unitDao.saveObject(unitModel);
			
			//添加用户
			UserModel userModel = new UserModel();
			userModel.setCreateDate(new Date());
			userModel.setCreateUser(user.getLoginName());
			userModel.setUseLvl(0);
			userModel.setIsLogin(0);
			userModel.setUserName(agentUnitModel.getAgentName()+"管理员");
			userModel.setLoginName(unitType+"99");	//账号默认加99
			userModel.setPassword(MD5Wrapper.encryptMD5ToString("123456"));		//默认密码123456
			Set<UnitModel> units = new HashSet<UnitModel>();
			units.add(unitModel);
			userModel.setUnits(units);
			String hql="from RoleModel r where r.roleAttr = 0 and r.roleLevel ="+childUnlvl;
			List<RoleModel> roleList = roleDao.queryObjectsByHqlList(hql);
			Set<RoleModel> roles = new HashSet<RoleModel>();
			for (RoleModel role : roleList) {
				roles.add(role);
			}
			userModel.setRoles(roles);
			userModel.setResetFlag(0);
			userModel.setStatus(1);
			userDao.saveObject(userModel);
			
			//调用webservice，对GMMS添加开通商户信息
//			AgentUnitInfo info=ClassToJavaBeanUtil.toAgentInfo(agentUnitModel);
//			Boolean falg=gsClient.saveAgentInfo(info,"hrtREX1234.Java");
//			if(!falg){
//				throw new IllegalAccessError("调用webservice失败");
//			}
		
		}else{
			agentUnit.setParentUnno(user.getUnNo());
			AgentUnitModel agentUnitModel = new AgentUnitModel();
			BeanUtils.copyProperties(agentUnit, agentUnitModel);
			if(agentUnit.getRiskAmount() == 0){
				agentUnitModel.setAmountConfirmDate(new Date());
			}
			agentUnitModel.setMaintainUserId(agentUnit.getSignUserId());		//添加的时候签约人员和维护人员一样
			agentUnitModel.setMaintainUid(user.getUserID());
			agentUnitModel.setMaintainDate(new Date());
			agentUnitModel.setMaintainType("A");		//A-add   M-Modify  D-Delete	
			agentUnitModel.setApproveStatus("W");
			agentUnitModel.setRno("******");
			agentUnitModel.setRegistryNo("******");
			agentUnitModel.setBankOpenAcc("******");
			if(agentUnit.getBankAccName().indexOf("交通")>-1){
				agentUnitModel.setBankType("1");
			}else{
				agentUnitModel.setBankType("2");
			}
			if(agentUnit.getBankArea().indexOf("北京")>-1){
				agentUnitModel.setAreaType("1");
			}else{
				agentUnitModel.setAreaType("2");
			}
			uploadImg(agentUnit,agentUnitModel,user,false);
			Integer buid = (Integer)agentUnitDao.saveObject(agentUnitModel);
			// 运营中心、分公司     成本维护
			if (user.getUnitLvl() != null && user.getUnitLvl()==1) {
				List<HrtUnnoCostBean> costList = JSONObject.parseArray(agentUnit.getCostData(), HrtUnnoCostBean.class);
				List<Object> models = new ArrayList<Object>();
				List<Object> modelNs = new ArrayList<Object>();
				for (HrtUnnoCostBean bean : costList) {
					HrtUnnoCostModel model = new HrtUnnoCostModel();
					BeanUtils.copyProperties(bean, model);
					model.setBuid(buid);
					model.setStatus(0);
					model.setFlag(0);
					model.setOperateType(1);
					model.setCdate(new Date());
					model.setCby(user.getLoginName());
					model.setLmDate(new Date());
					model.setLmby(user.getLoginName());

                    HrtUnnoCostNModel modelN = new HrtUnnoCostNModel();
                    BeanUtils.copyProperties(model,modelN);
                    modelNs.add(modelN);
                    models.add(model);
				}

				hrtUnnoCostDao.batchSaveObject(models);
				hrtUnnoCostNDao.batchSaveObject(modelNs);
				log.info("代理商成本插入成功,buid:" + buid);
			}
		}
	}
	
	private String formatUnno(String unno) {
//		'0' -> 'a'
//		'1' -> 'b'
//		'2' -> 'c'
//		'3' -> 'd'
//		'4' -> 'e'
//		'5' -> 'f'
//		'6' -> 'g'
//		'7' -> 'h'
//		'8' -> 'i'
//		'9' -> 'j'
//		'A' -> 'k'
		if("0".equals(unno.substring(0, 1))) {
			return "a"+unno.substring(1, unno.length());
		}else if("1".equals(unno.substring(0, 1))) {
			return "b"+unno.substring(1, unno.length());
		}else if("2".equals(unno.substring(0, 1))) {
			return "c"+unno.substring(1, unno.length());
		}else if("3".equals(unno.substring(0, 1))) {
			return "d"+unno.substring(1, unno.length());
		}else if("4".equals(unno.substring(0, 1))) {
			return "e"+unno.substring(1, unno.length());
		}else if("5".equals(unno.substring(0, 1))) {
			return "f"+unno.substring(1, unno.length());
		}else if("6".equals(unno.substring(0, 1))) {
			return "g"+unno.substring(1, unno.length());
		}else if("7".equals(unno.substring(0, 1))) {
			return "h"+unno.substring(1, unno.length());
		}else if("8".equals(unno.substring(0, 1))) {
			return "i"+unno.substring(1, unno.length());
		}else if("9".equals(unno.substring(0, 1))) {
			return "j"+unno.substring(1, unno.length());
		}else if("A".equals(unno.substring(0, 1))) {
			return "k"+unno.substring(1, unno.length());
		}
		return "";
	}
	private String formatUnno2(String unno) {
//		'0' -> 'a'
//		'1' -> 'b'
//		'2' -> 'c'
//		'3' -> 'd'
//		'4' -> 'e'
//		'5' -> 'f'
//		'6' -> 'g'
//		'7' -> 'h'
//		'8' -> 'i'
//		'9' -> 'j'
//		'A' -> 'k'
		if("a".equals(unno.substring(0, 1))) {
			return "0"+unno.substring(1, unno.length());
		}else if("b".equals(unno.substring(0, 1))) {
			return "1"+unno.substring(1, unno.length());
		}else if("c".equals(unno.substring(0, 1))) {
			return "2"+unno.substring(1, unno.length());
		}else if("d".equals(unno.substring(0, 1))) {
			return "3"+unno.substring(1, unno.length());
		}else if("e".equals(unno.substring(0, 1))) {
			return "4"+unno.substring(1, unno.length());
		}else if("f".equals(unno.substring(0, 1))) {
			return "5"+unno.substring(1, unno.length());
		}else if("g".equals(unno.substring(0, 1))) {
			return "6"+unno.substring(1, unno.length());
		}else if("h".equals(unno.substring(0, 1))) {
			return "7"+unno.substring(1, unno.length());
		}else if("i".equals(unno.substring(0, 1))) {
			return "8"+unno.substring(1, unno.length());
		}else if("j".equals(unno.substring(0, 1))) {
			return "9"+unno.substring(1, unno.length());
		}else if("k".equals(unno.substring(0, 1))) {
			return "A"+unno.substring(1, unno.length());
		}
		return "";
	}
	/**
	 * @param provinceCode
	 * @param childUnlvlA
	 * @param flag 上级机构是否是字母开头 true-是 false-不是
	 * @return
	 */
	private String addUnno(String provinceCode,String childUnlvlA,boolean flag){
		log.info("开通N级代理商 生成机构号：GROUPNAME = "+provinceCode+";childUnlvlA="+childUnlvlA);
		String unitType = null;
		try{
			if(flag) {
				unitType = addUnNoIsNumber(formatUnno(provinceCode + childUnlvlA));	//如果是字母机构新增，则查询字母开头的最大值
			}else {
				unitType = addUnNoIsNumber(provinceCode + childUnlvlA);	//后三位
			}
		}catch(Error e){
			Integer i = roleDao.querysqlCounts2("select MINFO2 from sys_configure where GROUPNAME = 'provinceCode"+childUnlvlA+"'",null);
			if(i!=null&&i>0){
				try{
					if(flag) {
						unitType = addUnNoIsNumber(formatUnno(i + "" + childUnlvlA));	//如果是字母机构新增，则查询字母开头的最大值
					}else {
						unitType = addUnNoIsNumber(i + "" + childUnlvlA);	//后三位
					}
				}catch(Error e1){
					Integer j = roleDao.executeSqlUpdate("update sys_configure set MINFO2=(MINFO2-1) where GROUPNAME = 'provinceCode"+childUnlvlA+"'", null);
					log.info("开通代理商查询默认区域码满值,自动调整结束：GROUPNAME = provinceCode"+childUnlvlA+";j="+j);
					if(j>0){
						// @author:lxg-20190411 当前配置表下的机构满了,按下一个配置获取机构
						unitType = addUnNoIsNumber(formatUnno((i-1) + "" + childUnlvlA));	//如果是字母机构新增，则查询字母开头的最大值

//						unitType = (i-1)+childUnlvlA+"001";
//						unitType = addUnNoIsNumber((i-1) + "" + childUnlvlA);	
					}else{
						throw new IllegalAccessError("开通代理商查询默认区域码满值,自动调整失败：GROUPNAME = provinceCode"+childUnlvlA);
					}
				}
			}else{
				log.info("开通代理商查询默认区域码失败：GROUPNAME = provinceCode"+childUnlvlA);
				throw new IllegalAccessError("开通代理商查询默认区域码失败：GROUPNAME = provinceCode"+childUnlvlA);
			}
		}
		log.info("开通N级代理商 生成机构号完成;unno="+unitType);
		return unitType;
	}
	
	private AgentUnitModel uploadImg(AgentUnitBean agentUnit, AgentUnitModel agentUnitModel, UserBean user,boolean isEdit)
			throws Exception {
		String imageDay = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		String nextVal ="";
		if(!isEdit){
			nextVal=agentUnitDao.findMaxId();
		}else{
			nextVal=agentUnit.getBuid().toString();
		}
		
		agentUnitModel.setBuid(Integer.parseInt(nextVal));
		//保存图片
		//机构号.id.日期yyyyMMdd.字段名.扩展名
		//协议签章页照片
		if (agentUnit.getDealUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getDealUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".dealUpLoadFile");
			fName.append(agentUnit.getDealUpLoad().substring(agentUnit.getDealUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getDealUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setDealUpLoad(imageDay+File.separator+fName.toString());
		}
		//营业执照（企业必传）
		if (agentUnit.getBusLicUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getBusLicUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".busLicUpLoadFile");
			fName.append(agentUnit.getBusLicUpLoad().substring(agentUnit.getBusLicUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getBusLicUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setBusLicUpLoad(imageDay+File.separator+fName.toString());
		}
		//对公开户证明（企业必传）
		if (agentUnit.getProofOfOpenAccountUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getProofOfOpenAccountUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".proofOfOpenAccountUpLoadFile");
			fName.append(agentUnit.getProofOfOpenAccountUpLoad().substring(agentUnit.getProofOfOpenAccountUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getProofOfOpenAccountUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setProofOfOpenAccountUpLoad(imageDay+File.separator+fName.toString());
		}
		//法人身份证正面
		if (agentUnit.getLegalAUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getLegalAUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".legalAUploadFile");
			fName.append(agentUnit.getLegalAUpLoad().substring(agentUnit.getLegalAUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getLegalAUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setLegalAUpLoad(imageDay+File.separator+fName.toString());
		}
		//法人身份证反面
		if (agentUnit.getLegalBUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getLegalBUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".legalBUploadFile");
			fName.append(agentUnit.getLegalBUpLoad().substring(agentUnit.getLegalBUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getLegalBUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setLegalBUpLoad(imageDay+File.separator+fName.toString());
		}
		//入账授权书
		if (agentUnit.getAccountAuthUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getAccountAuthUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".accountAuthUpLoadFile");
			fName.append(agentUnit.getAccountAuthUpLoad().substring(agentUnit.getAccountAuthUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getAccountAuthUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setAccountAuthUpLoad(imageDay+File.separator+fName.toString());
		}
		//入账人身份证正面
		if (agentUnit.getAccountLegalAUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getAccountLegalAUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".accountLegalAUpLoadFile");
			fName.append(agentUnit.getAccountLegalAUpLoad().substring(agentUnit.getAccountLegalAUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getAccountLegalAUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setAccountLegalAUpLoad(imageDay+File.separator+fName.toString());
		}
		//入账人身份证反面
		if (agentUnit.getAccountLegalBUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getAccountLegalBUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".accountLegalBUpLoadFile");
			fName.append(agentUnit.getAccountLegalBUpLoad().substring(agentUnit.getAccountLegalBUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getAccountLegalBUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setAccountLegalBUpLoad(imageDay+File.separator+fName.toString());
		}
		//入账人手持身份证
		if (agentUnit.getAccountLegalHandUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getAccountLegalHandUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".accountLegalHandUpLoadFile");
			fName.append(agentUnit.getAccountLegalHandUpLoad().substring(agentUnit.getAccountLegalHandUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getAccountLegalHandUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setAccountLegalHandUpLoad(imageDay+File.separator+fName.toString());
		}
		//办公地点照片
		if (agentUnit.getOfficeAddressUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getOfficeAddressUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".officeAddressUpLoadFile");
			fName.append(agentUnit.getOfficeAddressUpLoad().substring(agentUnit.getOfficeAddressUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getOfficeAddressUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setOfficeAddressUpLoad(imageDay+File.separator+fName.toString());
		}
		//分润照片
		if (agentUnit.getProfitUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getProfitUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".profitUpLoadFile");
			fName.append(agentUnit.getProfitUpLoad().substring(agentUnit.getProfitUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getProfitUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setProfitUpLoad(imageDay+File.separator+fName.toString());
		}
		return agentUnitModel;
	}
	
	
	@Override
	public void saveAgentUnit00410(AgentUnitBean agentUnit, UserBean user) throws Exception {
		if (agentUnit.getCostData() == null || "".equals(agentUnit.getCostData())) {
			throw new RuntimeException("运营中心签约，成本信息参数错误!");
		}
		AgentUnitModel agentUnitModel = new AgentUnitModel();
		BeanUtils.copyProperties(agentUnit, agentUnitModel);
		agentUnitModel.setParentUnno("110000");
		if (agentUnit.getRiskAmount() == 0) {
			agentUnitModel.setAmountConfirmDate(new Date());
		}
		agentUnitModel.setAgentLvl(agentUnit.getAgentLvl());//标记 为签约运营中心或三级营销中心
		agentUnitModel.setApproveStatus("W");
		agentUnitModel.setMaintainUserId(agentUnit.getSignUserId()); // 添加的时候签约人员和维护人员一样
		agentUnitModel.setMaintainUid(user.getUserID());
		agentUnitModel.setMaintainDate(new Date());
		agentUnitModel.setMaintainType("A"); // A-add M-Modify D-Delete
		agentUnitModel.setRno("******");
		agentUnitModel.setRegistryNo("******");
		agentUnitModel.setBankOpenAcc("******");
		if(agentUnit.getBankAccName().indexOf("交通")>-1){
			agentUnitModel.setBankType("1");
		}else{
			agentUnitModel.setBankType("2");
		}
		if(agentUnit.getBankArea().indexOf("北京")>-1){
			agentUnitModel.setAreaType("1");
		}else{
			agentUnitModel.setAreaType("2");
		}
		
		String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		String nextVal =agentUnitDao.findMaxId();
		agentUnitModel.setBuid(Integer.parseInt(nextVal));
		//保存图片
		//机构号.id.日期yyyyMMdd.字段名.扩展名
		//协议签章页照片
		if (agentUnit.getDealUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getDealUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".dealUpLoadFile");
			fName.append(agentUnit.getDealUpLoad().substring(agentUnit.getDealUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getDealUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setDealUpLoad(imageDay+File.separator+fName.toString());
		}
		//营业执照（企业必传）
		if (agentUnit.getBusLicUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getBusLicUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".busLicUpLoadFile");
			fName.append(agentUnit.getBusLicUpLoad().substring(agentUnit.getBusLicUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getBusLicUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setBusLicUpLoad(imageDay+File.separator+fName.toString());
		}
		//对公开户证明（企业必传）
		if (agentUnit.getProofOfOpenAccountUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getProofOfOpenAccountUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".proofOfOpenAccountUpLoadFile");
			fName.append(agentUnit.getProofOfOpenAccountUpLoad().substring(agentUnit.getProofOfOpenAccountUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getProofOfOpenAccountUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setProofOfOpenAccountUpLoad(imageDay+File.separator+fName.toString());
		}
		//法人身份证正面
		if (agentUnit.getLegalAUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getLegalAUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".legalAUploadFile");
			fName.append(agentUnit.getLegalAUpLoad().substring(agentUnit.getLegalAUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getLegalAUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setLegalAUpLoad(imageDay+File.separator+fName.toString());
		}
		//法人身份证反面
		if (agentUnit.getLegalBUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getLegalBUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".legalBUploadFile");
			fName.append(agentUnit.getLegalBUpLoad().substring(agentUnit.getLegalBUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getLegalBUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setLegalBUpLoad(imageDay+File.separator+fName.toString());
		}
		//入账授权书
		if (agentUnit.getAccountAuthUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getAccountAuthUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".accountAuthUpLoadFile");
			fName.append(agentUnit.getAccountAuthUpLoad().substring(agentUnit.getAccountAuthUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getAccountAuthUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setAccountAuthUpLoad(imageDay+File.separator+fName.toString());
		}
		//入账人身份证正面
		if (agentUnit.getAccountLegalAUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getAccountLegalAUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".accountLegalAUpLoadFile");
			fName.append(agentUnit.getAccountLegalAUpLoad().substring(agentUnit.getAccountLegalAUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getAccountLegalAUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setAccountLegalAUpLoad(imageDay+File.separator+fName.toString());
		}
		//入账人身份证反面
		if (agentUnit.getAccountLegalBUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getAccountLegalBUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".accountLegalBUpLoadFile");
			fName.append(agentUnit.getAccountLegalBUpLoad().substring(agentUnit.getAccountLegalBUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getAccountLegalBUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setAccountLegalBUpLoad(imageDay+File.separator+fName.toString());
		}
		//入账人手持身份证
		if (agentUnit.getAccountLegalHandUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getAccountLegalHandUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".accountLegalHandUpLoadFile");
			fName.append(agentUnit.getAccountLegalHandUpLoad().substring(agentUnit.getAccountLegalHandUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getAccountLegalHandUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setAccountLegalHandUpLoad(imageDay+File.separator+fName.toString());
		}
		//办公地点照片
		if (agentUnit.getOfficeAddressUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getOfficeAddressUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".officeAddressUpLoadFile");
			fName.append(agentUnit.getOfficeAddressUpLoad().substring(agentUnit.getOfficeAddressUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getOfficeAddressUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setOfficeAddressUpLoad(imageDay+File.separator+fName.toString());
		}
		//分润照片
		if (agentUnit.getProfitUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getProfitUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".profitUpLoadFile");
			fName.append(agentUnit.getProfitUpLoad().substring(agentUnit.getProfitUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getProfitUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setProfitUpLoad(imageDay+File.separator+fName.toString());
		}
		Integer buid = (Integer)agentUnitDao.saveObject(agentUnitModel);
		// 运营中心、分公司     成本维护
		if (user.getUnitLvl() != null && user.getUnitLvl()==0) {//总公司去维护运营中心
			List<HrtUnnoCostBean> costList = JSONObject.parseArray(agentUnit.getCostData(), HrtUnnoCostBean.class);
			List<Object> models = new ArrayList<Object>();
			List<Object> modelNs = new ArrayList<Object>();
			for (HrtUnnoCostBean bean : costList) {
				HrtUnnoCostModel model = new HrtUnnoCostModel();
				BeanUtils.copyProperties(bean, model);
				model.setBuid(buid);
				model.setStatus(0);
				model.setFlag(0);
				model.setOperateType(1);
				model.setCdate(new Date());
				model.setCby(user.getLoginName());
				model.setLmDate(new Date());
				model.setLmby(user.getLoginName());

                HrtUnnoCostNModel modelN = new HrtUnnoCostNModel();
                BeanUtils.copyProperties(model, modelN);
                modelNs.add(modelN);
				models.add(model);
			}
			
			hrtUnnoCostDao.batchSaveObject(models);
			hrtUnnoCostNDao.batchSaveObject(modelNs);
			log.info("运营中心签约，成本插入成功,buid:" + buid);
		}

	}
	
	private void uploadFile(File upload, String fName, String imageDay) throws Exception {
		try {
			String title="AgentInfo";
			String savePath = agentUnitDao.querySavePath(title);
			if(null==savePath){
				throw new Exception("获取代理商图片保存路径错误");
			}
			String realPath = savePath + File.separator + imageDay;
			File dir = new File(realPath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			String fPath = realPath + File.separator + fName;
			File destFile = new File(fPath);
			FileInputStream fis = new FileInputStream(upload);
			FileOutputStream fos = new FileOutputStream(destFile);
			byte [] buffer = new byte[1024];
			int len = 0;
			while((len = fis.read(buffer))>0){
				fos.write(buffer,0,len);
			} 
			fos.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("代理商图片保存错误");
		}
	}

	private void uploadFileByTitle(String title,File upload, String fName) throws Exception {
		try {
			String imageDay = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			String savePath = agentUnitDao.querySavePath(title);
			if(null==savePath){
				throw new Exception("获取代理商图片保存路径错误");
			}
//			String realPath = savePath + File.separator + imageDay;
			String realPath = savePath;
			File dir = new File(realPath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			String fPath = realPath + File.separator + fName;
			File destFile = new File(fPath);
			FileInputStream fis = new FileInputStream(upload);
			FileOutputStream fos = new FileOutputStream(destFile);
			byte [] buffer = new byte[1024];
			int len = 0;
			while((len = fis.read(buffer))>0){
				fos.write(buffer,0,len);
			}
			fos.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("代理商图片保存错误");
		}
	}

	/**
	 * 修改代理商信息
	 */
	@Override
	public void updateAgentUnit(AgentUnitBean agentUnit, UserBean user)
			throws Exception {
		AgentUnitModel agentUnitModel = agentUnitDao.getObjectByID(AgentUnitModel.class, agentUnit.getBuid());
		if(agentUnitModel.getOpenDate()!=null && !"".equals(agentUnitModel.getOpenDate())){
			agentUnit.setOpenDate(agentUnitModel.getOpenDate());
		}
		if(agentUnitModel.getAmountConfirmDate()!=null && !"".equals(agentUnitModel.getAmountConfirmDate())){
			agentUnit.setAmountConfirmDate(agentUnitModel.getAmountConfirmDate());
		}
		if(agentUnit.getMaintainUserId()==null||"".equals(agentUnit.getMaintainUserId())){
			agentUnit.setMaintainUserId(agentUnitModel.getMaintainUserId());
		}
		if(agentUnit.getSignUserId()==null||"".equals(agentUnit.getSignUserId())){
			agentUnit.setSignUserId(agentUnitModel.getSignUserId());
		}
		//修改代理商信息，页面不传ACCTYPE,默认不修改
		if(agentUnit.getAccType()==null||"".equals(agentUnit.getAccType())) {
			agentUnit.setAccType(agentUnitModel.getAccType());
		}
		BeanUtils.copyProperties(agentUnit, agentUnitModel);
		agentUnitModel.setMaintainUid(user.getUserID());
		agentUnitModel.setMaintainDate(new Date());
		agentUnitModel.setMaintainType("M");		//A-add   M-Modify  D-Delete
		agentUnitModel.setApproveStatus("W");       //编辑过后重新变为待审
		uploadImg(agentUnit,agentUnitModel,user,true);
		agentUnitModel.setRno("******");
		agentUnitModel.setRegistryNo("******");
		agentUnitModel.setBankOpenAcc("******");
		if(StringUtil.isNotEmpty(agentUnit.getBankAccName())&&
				agentUnit.getBankAccName().indexOf("交通")>-1){
			agentUnitModel.setBankType("1");
		}else{
			agentUnitModel.setBankType("2");
		}
		if(StringUtil.isNotEmpty(agentUnit.getBankArea())&&
				agentUnit.getBankArea().indexOf("北京")>-1){
			agentUnitModel.setAreaType("1");
		}else{
			agentUnitModel.setAreaType("2");
		}
		agentUnitDao.updateObject(agentUnitModel);
		if(StringUtil.isNotEmpty(agentUnit.getUnno())){
			UnitModel unnoModel = unitDao.getObjectByID(UnitModel.class, agentUnit.getUnno());
			unnoModel.setUnitName(agentUnit.getAgentName());
			unnoModel.setUpdateDate(new Date());
			unnoModel.setUpdateUser(user.getUserName());
			unitDao.updateObject(unnoModel);
		}
		// 修改代理成本信息
		if (agentUnit.getCostData()!=null && !"".equals(agentUnit.getCostData())) {
			List<HrtUnnoCostBean> costList = JSONObject.parseArray(agentUnit.getCostData(), HrtUnnoCostBean.class);
			updateHrtCostBean(costList,agentUnitModel,user);
		}
	}

	private void updateHrtCostBean(List<HrtUnnoCostBean> costList,AgentUnitModel agentUnitModel, UserBean user){
		for (HrtUnnoCostBean bean : costList) {
			if(bean.getTxnDetail()==null) {
				continue;
			}
			HrtUnnoCostModel model = null;
			HrtUnnoCostNModel modelN = null;
			if (bean.getHucid() != null) {
				model = hrtUnnoCostDao.getObjectByID(HrtUnnoCostModel.class, bean.getHucid());
				if(model!=null) {
					String hql="from HrtUnnoCostNModel where buid=? and machineType=? and txnType=? and txnDetail=?";
					modelN = hrtUnnoCostNDao.queryObjectByHql(hql,
							new Object[]{model.getBuid(),model.getMachineType(),model.getTxnType(),model.getTxnDetail()});
				}
			}else {
				//新增的活动类型
				HrtUnnoCostModel m = new HrtUnnoCostModel();
				BeanUtils.copyProperties(bean, m);
				m.setBuid(agentUnitModel.getBuid());
				m.setStatus(0);
				m.setFlag(0);
				m.setOperateType(1);
				m.setCdate(new Date());
				m.setCby(user.getLoginName());
				m.setLmDate(new Date());
				m.setLmby(user.getLoginName());

                HrtUnnoCostNModel mN = new HrtUnnoCostNModel();
                BeanUtils.copyProperties(m, mN);
                hrtUnnoCostNDao.saveObject(mN);
                hrtUnnoCostDao.saveObject(m);
			}
			if (model!=null) {
				if (!"".equals(bean.getDebitRate())) {
					model.setDebitRate(bean.getDebitRate());
				}
				if (!"".equals(bean.getDebitFeeamt())) {
					model.setDebitFeeamt(bean.getDebitFeeamt());
				}
				if (!"".equals(bean.getCreditRate())) {
					model.setCreditRate(bean.getCreditRate());
				}
				if (!"".equals(bean.getCashCost())) {
					model.setCashCost(bean.getCashCost());
				}
				if (!"".equals(bean.getCashRate())) {
					model.setCashRate(bean.getCashRate());
				}

				// @author:lxg-20190912 扫码费率拆分
				if (!"".equals(bean.getWxUpRate())) {
					model.setWxUpRate(bean.getWxUpRate());
				}
				if (!"".equals(bean.getWxUpCash())) {
					model.setWxUpCash(bean.getWxUpCash());
				}
				if (!"".equals(bean.getWxUpRate1())) {
					model.setWxUpRate1(bean.getWxUpRate1());
				}
				if (!"".equals(bean.getWxUpCash1())) {
					model.setWxUpCash1(bean.getWxUpCash1());
				}
				if (!"".equals(bean.getZfbRate())) {
					model.setZfbRate(bean.getZfbRate());
				}
				if (!"".equals(bean.getZfbCash())) {
					model.setZfbCash(bean.getZfbCash());
				}
				if (!"".equals(bean.getEwmRate())) {
					model.setEwmRate(bean.getEwmRate());
				}
				if (!"".equals(bean.getEwmCash())) {
					model.setEwmCash(bean.getEwmCash());
				}
				if (!"".equals(bean.getYsfRate())) {
					model.setYsfRate(bean.getYsfRate());
				}
				if (!"".equals(bean.getHbRate())) {
					model.setHbRate(bean.getHbRate());
				}
				if (!"".equals(bean.getHbCash())) {
					model.setHbCash(bean.getHbCash());
				}
				model.setLmby(user.getLoginName());
				model.setLmDate(new Date());
				model.setOperateType(0);
				hrtUnnoCostDao.updateObject(model);
			}

			if (modelN!=null) {
				if (!"".equals(bean.getDebitRate())) {
					modelN.setDebitRate(bean.getDebitRate());
				}
				if (!"".equals(bean.getDebitFeeamt())) {
					modelN.setDebitFeeamt(bean.getDebitFeeamt());
				}
				if (!"".equals(bean.getCreditRate())) {
					modelN.setCreditRate(bean.getCreditRate());
				}
				if (!"".equals(bean.getCashCost())) {
					modelN.setCashCost(bean.getCashCost());
				}
				if (!"".equals(bean.getCashRate())) {
					modelN.setCashRate(bean.getCashRate());
				}

				// @author:lxg-20190912 扫码费率拆分
				if (!"".equals(bean.getWxUpRate())) {
					modelN.setWxUpRate(bean.getWxUpRate());
				}
				if (!"".equals(bean.getWxUpCash())) {
					modelN.setWxUpCash(bean.getWxUpCash());
				}
				if (!"".equals(bean.getWxUpRate1())) {
					modelN.setWxUpRate1(bean.getWxUpRate1());
				}
				if (!"".equals(bean.getWxUpCash1())) {
					modelN.setWxUpCash1(bean.getWxUpCash1());
				}
				if (!"".equals(bean.getZfbRate())) {
					modelN.setZfbRate(bean.getZfbRate());
				}
				if (!"".equals(bean.getZfbCash())) {
					modelN.setZfbCash(bean.getZfbCash());
				}
				if (!"".equals(bean.getEwmRate())) {
					modelN.setEwmRate(bean.getEwmRate());
				}
				if (!"".equals(bean.getEwmCash())) {
					modelN.setEwmCash(bean.getEwmCash());
				}
				if (!"".equals(bean.getYsfRate())) {
					modelN.setYsfRate(bean.getYsfRate());
				}
				if (!"".equals(bean.getHbRate())) {
					modelN.setHbRate(bean.getHbRate());
				}
				if (!"".equals(bean.getHbCash())) {
					modelN.setHbCash(bean.getHbCash());
				}

				modelN.setLmby(user.getLoginName());
				modelN.setLmDate(new Date());
				modelN.setOperateType(0);
				hrtUnnoCostNDao.updateObject(modelN);
			}
		}
	}
	
	
	@Override
	public void updateAgentUnit00410(AgentUnitBean agentUnit, UserBean user)
			throws Exception {
		// 修改签约运营中心数据
		AgentUnitModel agentUnitModel = agentUnitDao.getObjectByID(AgentUnitModel.class, agentUnit.getBuid());
		if(agentUnitModel.getOpenDate()!=null && !"".equals(agentUnitModel.getOpenDate())){
			agentUnit.setOpenDate(agentUnitModel.getOpenDate());
		}
		if(agentUnitModel.getAmountConfirmDate()!=null && !"".equals(agentUnitModel.getAmountConfirmDate())){
			agentUnit.setAmountConfirmDate(agentUnitModel.getAmountConfirmDate());
		}
		if(agentUnit.getMaintainUserId()==null||"".equals(agentUnit.getMaintainUserId())){
			agentUnit.setMaintainUserId(agentUnitModel.getMaintainUserId());
		}
		if(agentUnit.getSignUserId()==null||"".equals(agentUnit.getSignUserId())){
			agentUnit.setSignUserId(agentUnitModel.getSignUserId());
		}
		BeanUtils.copyProperties(agentUnit, agentUnitModel);
		agentUnitModel.setAgentLvl(1);
		agentUnitModel.setApproveStatus("W");       //编辑过后重新变为待审
		agentUnitModel.setMaintainUserId(agentUnit.getSignUserId());
		agentUnitModel.setMaintainUid(user.getUserID());
		agentUnitModel.setMaintainDate(new Date());
		agentUnitModel.setMaintainType("M");		//A-add   M-Modify  D-Delete
		agentUnitModel.setRno("******");
		agentUnitModel.setRegistryNo("******");
		agentUnitModel.setBankOpenAcc("******");
		if(StringUtil.isNotEmpty(agentUnit.getBankAccName())&&
				agentUnit.getBankAccName().indexOf("交通")>-1){
			agentUnitModel.setBankType("1");
		}else{
			agentUnitModel.setBankType("2");
		}
		if(StringUtil.isNotEmpty(agentUnit.getBankArea())&&
				agentUnit.getBankArea().indexOf("北京")>-1){
			agentUnitModel.setAreaType("1");
		}else{
			agentUnitModel.setAreaType("2");
		}
		
		String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		String buid =agentUnitModel.getBuid()+"";
//		agentUnitModel.setBuid(Integer.parseInt(nextVal));
		
		//保存图片
		//机构号.id.日期yyyyMMdd.字段名.扩展名
		//协议签章页照片
		if (agentUnit.getDealUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getDealUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(buid + ".");
			fName.append(imageDay);
			fName.append(".dealUpLoadFile");
			fName.append(agentUnit.getDealUpLoad().substring(agentUnit.getDealUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getDealUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setDealUpLoad(imageDay+File.separator+fName.toString());
		}
		//营业执照（企业必传）
		if (agentUnit.getBusLicUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getBusLicUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(buid + ".");
			fName.append(imageDay);
			fName.append(".busLicUpLoadFile");
			fName.append(agentUnit.getBusLicUpLoad().substring(agentUnit.getBusLicUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getBusLicUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setBusLicUpLoad(imageDay+File.separator+fName.toString());
		}
		//对公开户证明（企业必传）
		if (agentUnit.getProofOfOpenAccountUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getProofOfOpenAccountUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(buid + ".");
			fName.append(imageDay);
			fName.append(".proofOfOpenAccountUpLoadFile");
			fName.append(agentUnit.getProofOfOpenAccountUpLoad().substring(agentUnit.getProofOfOpenAccountUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getProofOfOpenAccountUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setProofOfOpenAccountUpLoad(imageDay+File.separator+fName.toString());
		}
		//法人身份证正面
		if (agentUnit.getLegalAUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getLegalAUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(buid + ".");
			fName.append(imageDay);
			fName.append(".legalAUploadFile");
			fName.append(agentUnit.getLegalAUpLoad().substring(agentUnit.getLegalAUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getLegalAUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setLegalAUpLoad(imageDay+File.separator+fName.toString());
		}
		//法人身份证反面
		if (agentUnit.getLegalBUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getLegalBUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(buid + ".");
			fName.append(imageDay);
			fName.append(".legalBUploadFile");
			fName.append(agentUnit.getLegalBUpLoad().substring(agentUnit.getLegalBUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getLegalBUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setLegalBUpLoad(imageDay+File.separator+fName.toString());
		}
		//入账授权书
		if (agentUnit.getAccountAuthUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getAccountAuthUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(buid + ".");
			fName.append(imageDay);
			fName.append(".accountAuthUpLoadFile");
			fName.append(agentUnit.getAccountAuthUpLoad().substring(agentUnit.getAccountAuthUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getAccountAuthUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setAccountAuthUpLoad(imageDay+File.separator+fName.toString());
		}
		//入账人身份证正面
		if (agentUnit.getAccountLegalAUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getAccountLegalAUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(buid + ".");
			fName.append(imageDay);
			fName.append(".accountLegalAUpLoadFile");
			fName.append(agentUnit.getAccountLegalAUpLoad().substring(agentUnit.getAccountLegalAUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getAccountLegalAUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setAccountLegalAUpLoad(imageDay+File.separator+fName.toString());
		}
		//入账人身份证反面
		if (agentUnit.getAccountLegalBUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getAccountLegalBUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(buid + ".");
			fName.append(imageDay);
			fName.append(".accountLegalBUpLoadFile");
			fName.append(agentUnit.getAccountLegalBUpLoad().substring(agentUnit.getAccountLegalBUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getAccountLegalBUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setAccountLegalBUpLoad(imageDay+File.separator+fName.toString());
		}
		//入账人手持身份证
		if (agentUnit.getAccountLegalHandUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getAccountLegalHandUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(buid + ".");
			fName.append(imageDay);
			fName.append(".accountLegalHandUpLoadFile");
			fName.append(agentUnit.getAccountLegalHandUpLoad().substring(agentUnit.getAccountLegalHandUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getAccountLegalHandUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setAccountLegalHandUpLoad(imageDay+File.separator+fName.toString());
		}
		//办公地点照片
		if (agentUnit.getOfficeAddressUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getOfficeAddressUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(buid + ".");
			fName.append(imageDay);
			fName.append(".officeAddressUpLoadFile");
			fName.append(agentUnit.getOfficeAddressUpLoad().substring(agentUnit.getOfficeAddressUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getOfficeAddressUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setOfficeAddressUpLoad(imageDay+File.separator+fName.toString());
		}
		//分润照片
		if (agentUnit.getProfitUpLoadFile() != null && StringUtil.isNotEmpty(agentUnit.getProfitUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(buid + ".");
			fName.append(imageDay);
			fName.append(".profitUpLoadFile");
			fName.append(agentUnit.getProfitUpLoad().substring(agentUnit.getProfitUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnit.getProfitUpLoadFile(), fName.toString(), imageDay);
			agentUnitModel.setProfitUpLoad(imageDay+File.separator+fName.toString());
		}
		agentUnitDao.updateObject(agentUnitModel);
		
		//初始化的 没有机构号
		if(StringUtil.isNotEmpty(agentUnit.getUnno())){
			UnitModel unnoModel = unitDao.getObjectByID(UnitModel.class, agentUnit.getUnno());
			unnoModel.setUnitName(agentUnit.getAgentName());
			unnoModel.setUpdateDate(new Date());
			unnoModel.setUpdateUser(user.getUserName());
			unitDao.updateObject(unnoModel);
		}
		
		// 修改代理成本信息
		if (agentUnit.getCostData()!=null && !"".equals(agentUnit.getCostData())) {
			List<HrtUnnoCostBean> costList = JSONObject.parseArray(agentUnit.getCostData(), HrtUnnoCostBean.class);
			updateHrtCostBean(costList,agentUnitModel,user);
		}
	}

	/**
	 * 查询代理商信息（未缴款并且风险保障金大于0）
	 */
	@Override
	public DataGridBean queryAgentConfirm(AgentUnitBean agentUnit)
			throws Exception {
		String hql = "from AgentUnitModel a where a.riskAmount > :riskAmount and a.maintainType != :maintainType and a.amountConfirmDate is null";
		String hqlCount = "select count(*) from AgentUnitModel a where a.riskAmount > :riskAmount and a.maintainType != :maintainType and a.amountConfirmDate is null";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("riskAmount", Double.parseDouble("0"));
		map.put("maintainType", "D");
		
		if (agentUnit.getSort() != null) {
			hql += " order by " + agentUnit.getSort() + " " + agentUnit.getOrder();
		}
		long counts = agentUnitDao.queryCounts(hqlCount, map);
		List<AgentUnitModel> agentUnitList = agentUnitDao.queryAgentUnit(hql, map, agentUnit.getPage(), agentUnit.getRows());
		
		DataGridBean dataGridBean = formatToDataGrid(agentUnitList, counts);
		
		return dataGridBean;
	}

	/**
	 * 确认代理商缴款
	 */
	@Override
	public void updateAgentConfirm(AgentUnitBean agentUnit, UserBean user) throws Exception {
		AgentUnitModel agentUnitModel = agentUnitDao.getObjectByID(AgentUnitModel.class, agentUnit.getBuid());
		agentUnitModel.setAmountConfirmDate(new Date());
		agentUnitDao.updateObject(agentUnitModel);
	}

	/**
	 * 查询代理商信息（保障金大于0已缴款未开通）或（保障金等于0未开通）
	 */
	@Override
	public DataGridBean queryAgentOpen(AgentUnitBean agentUnit,boolean isClearing)
			throws Exception {
		String hql = "from AgentUnitModel a where ((a.riskAmount > :riskAmount and a.maintainType != :maintainType and a.amountConfirmDate is not null and a.openDate is null) or (a.riskAmount = :riskAmount and a.maintainType != :maintainType and a.openDate is null))";
		String hqlCount = "select count(*) from AgentUnitModel a where ((a.riskAmount > :riskAmount and a.maintainType != :maintainType and a.amountConfirmDate is not null and a.openDate is null) or (a.riskAmount = :riskAmount and a.maintainType != :maintainType and a.openDate is null))";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("riskAmount", Double.parseDouble("0"));
		map.put("maintainType", "D");
		if(isClearing){//是否为清算部查看列表
			hql +=" and (a.approveStatus in('W','Y') or a.approveStatus is null) ";
			hqlCount+=" and (a.approveStatus in('W','Y') or a.approveStatus is null) ";
		}else{
			hql +=" and a.approveStatus in('C','Y') ";
			hqlCount+=" and a.approveStatus in('C','Y') ";
		}
		
		if (agentUnit.getSort() != null) {
			hql += " order by " + agentUnit.getSort() + " " + agentUnit.getOrder();
		}
		long counts = agentUnitDao.queryCounts(hqlCount, map);
		List<AgentUnitModel> agentUnitList = agentUnitDao.queryAgentUnit(hql, map, agentUnit.getPage(), agentUnit.getRows());
		
		DataGridBean dataGridBean = formatToDataGrid(agentUnitList, counts);
		
		return dataGridBean;
	}

	/**
	 * 确认代理商开通
	 */
	@Override
	public void updateAgentOpen(AgentUnitBean agentUnit, UserBean user)
			throws Exception {
		if(!"000000".equals(agentUnit.getParentUnno())){
			agentUnit.setUnno(agentUnit.getParentUnno());
		}else{
			throw new Exception("二级代理以下无需审核！");
		}
		
		UnitModel parent = unitDao.getObjectByID(UnitModel.class, agentUnit.getUnno());
		String provinceCode = "";	//区域编号
		String unitType = "";	
		boolean falg = false;
		//更新代理
		AgentUnitModel agentUnitModel = agentUnitDao.getObjectByID(AgentUnitModel.class, agentUnit.getBuid());
		if(agentUnitModel.getAgentLvl()!=null &&( agentUnitModel.getAgentLvl()==1||agentUnitModel.getAgentLvl()==2)){//运营中心|三级分销
			provinceCode=agentUnit.getProvinceCode();
			//这里表示如果为 分公司则每个省份 只能开通一个 否则会unno重复，
			//但是现在挂在渠道部 991000 所以不存在这种情况
			if("a".equals(parent.getUnNo().substring(0, 1))||"b".equals(parent.getUnNo().substring(0, 1))||
					"c".equals(parent.getUnNo().substring(0, 1))||"d".equals(parent.getUnNo().substring(0, 1))||
					"e".equals(parent.getUnNo().substring(0, 1))||"f".equals(parent.getUnNo().substring(0, 1))||
					"g".equals(parent.getUnNo().substring(0, 1))||"h".equals(parent.getUnNo().substring(0, 1))||
					"i".equals(parent.getUnNo().substring(0, 1))||"j".equals(parent.getUnNo().substring(0, 1))||
					"k".equals(parent.getUnNo().substring(0, 1))||"110000".equals(parent.getUnNo())) {
				unitType=addUnNoIsNumber(formatUnno(provinceCode+"1"));
			}else {
				unitType=addUnNoIsNumber(formatUnno(provinceCode+"1"));
//				unitType=addUnNoIsNumber(provinceCode+"1");
			}
			agentUnitModel.setApproveStatus("Y");
			agentUnitModel.setAgentShortNm(agentUnit.getAgentShortNm());
		}else{//代理
			agentUnitModel.setApproveStatus("Y");
			provinceCode = parent.getProvinceCode().replaceAll(",", "").trim();
			if("a".equals(parent.getUnNo().substring(0, 1))||"b".equals(parent.getUnNo().substring(0, 1))||
					"c".equals(parent.getUnNo().substring(0, 1))||"d".equals(parent.getUnNo().substring(0, 1))||
					"e".equals(parent.getUnNo().substring(0, 1))||"f".equals(parent.getUnNo().substring(0, 1))||
					"g".equals(parent.getUnNo().substring(0, 1))||"h".equals(parent.getUnNo().substring(0, 1))||
					"i".equals(parent.getUnNo().substring(0, 1))||"j".equals(parent.getUnNo().substring(0, 1))||
					"k".equals(parent.getUnNo().substring(0, 1))) {
				unitType=addUnNoIsNumber(formatUnno(provinceCode+"2"));
			}else {
				unitType=addUnNoIsNumber(formatUnno(provinceCode+"2"));
//				unitType=addUnNoIsNumber(provinceCode+"2");//后三位
			}
			agentUnitModel.setAgentShortNm(agentUnit.getAgentShortNm());
		}
		
		agentUnitModel.setReturnReason("");//清空退回原因
		agentUnitModel.setUnno(unitType);
		agentUnitModel.setOpenDate(new Date());
		agentUnitModel.setMaintainUid(user.getUserID());
		agentUnitModel.setMaintainDate(new Date());
		agentUnitModel.setMaintainType("M");			//A-add   M-Modify  D-Delete
		agentUnitDao.updateObject(agentUnitModel);
		String unitCodeSql="insert into sys_unitcode (unit_code,cdate,creat_user) values ('"+agentUnit.getUnitCode()+"',sysdate,'"+user.getLoginName()+"')";
		unitDao.executeUpdate(unitCodeSql);
		UnitModel unitModel = new UnitModel();
		if (agentUnit.getAgentLvl() != null && agentUnit.getAgentLvl() == 2) { //添加三级分销
			//添加机构
			unitModel.setProvinceCode(provinceCode);
			unitModel.setUnitCode(agentUnit.getUnitCode());
			unitModel.setParent(parent);
			unitModel.setUpperUnit3(unitType);
			unitModel.setUnNo(unitType);
			unitModel.setUnitName(agentUnitModel.getAgentName());
			unitModel.setUnLvl(1); 
			unitModel.setSeqNo(1000);
			unitModel.setSeqNo2(4000);
			unitModel.setStatus(1);	
			unitModel.setCreateDate(new Date());
			unitModel.setCreateUser(user.getLoginName());
			unitModel.setUnAttr(1);
			unitModel.setAgentAttr(agentUnit.getAgentAttr());
			unitDao.saveObject(unitModel);
			
			AgentDLUnitModel dlUnitModel = new AgentDLUnitModel();
			dlUnitModel.setUnno(unitType);
			dlUnitModel.setAgentMid(unitType);
			dlUnitModel.setAgentName(agentUnitModel.getAgentName());
			dlUnitModel.setPayBankId(agentUnitModel.getPayBankID());
			dlUnitModel.setBankAccName(agentUnitModel.getBankAccName());
			dlUnitModel.setBankAccno(agentUnitModel.getBankAccNo());
			dlUnitModel.setBankBranch(agentUnitModel.getBankBranch());
			dlUnitModel.setPromoCode(agentUnitModel.getContactTel());
			dlUnitModel.setStatus(1);
			dlUnitModel.setCreateDate(new Date());
			dlUnitModel.setCreateUser(user.getUserName());
			dlUnitModel.setReferralLink(hrtAppUrl+"/AgentConsole/shareRegister.jsp?inviteCode="+unitType+"&invitePerson="+URLEncoder.encode(agentUnitModel.getAgentName(),"UTF-8")+"&invitePhone="+agentUnitModel.getContactTel());
			agentDlUnitDao.saveObject(dlUnitModel);
			
			try{
				AgentUnitInfo info = new AgentUnitInfo();
				XMLGregorianCalendar calendar =ClassToJavaBeanUtil.convertToXMLGregorianCalendar(new Date());
				info.setUnno(unitType);
				info.setBankAccNo(agentUnitModel.getPayBankID());
//				info.setUpperUnit1(unitModel.getParent().getUnNo());
				info.setUpperUnit3(unitModel.getUpperUnit3());
				info.setParCompany(unitModel.getUpperUnit3());
				info.setAccType(agentUnitModel.getAccType());//?
				info.setPayBankId(agentUnitModel.getPayBankID());
				info.setAgentName(agentUnitModel.getAgentName());
				info.setAmountConfirmDate(calendar);
				info.setAreaType("1");
				info.setBankAccName(agentUnitModel.getBankAccName());
				info.setBankAccNo(agentUnitModel.getBankAccNo());
				info.setBankArea("北京");
				info.setBankBranch(agentUnitModel.getBankBranch());
				info.setBankOpenAcc("1");//企业银行开户许可证号
				info.setBankType("2");
				info.setBno("1");
				info.setBuid(1);
				info.setBaddr(agentUnitModel.getBaddr());
				info.setLegalNum(agentUnitModel.getLegalNum());
				info.setLegalPerson(agentUnitModel.getLegalPerson());
				info.setLegalType("1");
				info.setMaintainDate(calendar);
				info.setMaintainType("A");
				info.setMaintainUserId(1);
				info.setOpenDate(calendar);
				info.setRegistryNo("1");
				info.setRno("1");
				info.setSignUserId(1);
				info.setContactPhone(agentUnitModel.getContactPhone());
				info.setCronym(unitType+agentUnitModel.getAgentName());
				falg = gsClient.saveAgentInfo(info,"hrtREX1234.Java");
				System.out.println("三级分销-机构-推送综合结果"+falg);
			}catch (Exception e) {
				log.info("三级分销-机构-推送综合异常"+e);
			}
			if(!falg){
				throw new IllegalAccessError("调用webservice失败");
			}
		}else {
			unitModel.setProvinceCode(provinceCode);
			unitModel.setUnitCode(agentUnit.getUnitCode());
			unitModel.setParent(parent);
			unitModel.setUnNo(unitType);
			unitModel.setUnitName(agentUnitModel.getAgentName());
			AgentUnitInfo info=ClassToJavaBeanUtil.toAgentInfo(agentUnitModel);
			//添加机构
			if(agentUnit.getAgentLvl() != null && agentUnit.getAgentLvl() == 1){
				unitModel.setUnLvl(1);//当开通运营中心时  需要需要设置为 -1分公司
				info.setParCompany(info.getUnno());
			}else{
				info.setParCompany(parent.getUnNo());
				unitModel.setUnLvl(2);
			}
			unitModel.setSeqNo(0);
			unitModel.setSeqNo2(30000);
			unitModel.setStatus(1);	
			unitModel.setCreateDate(new Date());
			unitModel.setCreateUser(user.getLoginName());
			unitModel.setUnAttr(1);
			unitModel.setAgentAttr(agentUnit.getAgentAttr());
			unitDao.saveObject(unitModel);
			//推送贷款授信
//			try{
//				boolean flag = creditAgentService.saveCreditAgent(agentUnitModel, user);
//				System.out.println("推送贷款授信系统代理信息"+flag);
//			}catch (Exception e) {
//				throw new IllegalAccessError("推送贷款授信系统代理信息异常"+e);
//			}
			//调用webservice，对GMMS添加开通商户信息
			falg=gsClient.saveAgentInfo(info,"hrtREX1234.Java");
			System.out.println("返回信息:"+ falg);
			if(!falg){
				throw new IllegalAccessError("调用webservice失败");
			}
			// 修改成本信息和状态
			String sql = "update hrt_unno_cost t set t.unno = :unno,t.status = 1,t.flag = 0,t.operate_type = 1,t.lmby=:lmby,"
					+ "t.lmdate=:lmdate where t.buid = :buid ";
            String sqlN = "update hrt_unno_cost_n t set t.unno = :unno,t.status = 1,t.flag = 1,t.operate_type = 1,t.lmby=:lmby,"
                    + "t.lmdate=:lmdate where t.buid = :buid ";
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("unno", unitType);
			param.put("lmby", user.getLoginName());
			param.put("lmdate", new Date());
			param.put("buid", agentUnit.getBuid());
			hrtUnnoCostDao.executeSqlUpdate(sql, param);
			hrtUnnoCostNDao.executeSqlUpdate(sqlN, param);
		}
		
		//添加用户
		UserModel userModel = new UserModel();
		userModel.setCreateDate(new Date());
		userModel.setCreateUser(user.getLoginName());
		userModel.setUseLvl(0);
		userModel.setIsLogin(0);
		userModel.setUserName(agentUnitModel.getAgentName()+"管理员");
		userModel.setLoginName(unitType+"99");	//账号默认加99
		userModel.setPassword(MD5Wrapper.encryptMD5ToString("123456"));		//默认密码123456
		Set<UnitModel> units = new HashSet<UnitModel>();
		units.add(unitModel);
		userModel.setUnits(units);
		if (agentUnit.getAgentLvl() != null && agentUnit.getAgentLvl() == 2){//三级分销用户
			String hql="from RoleModel r where r.roleLevel = 1 and r.roleAttr = 0 and r.status=3 ";
    		List<RoleModel> roleList = roleDao.queryObjectsByHqlList(hql);
    		Set<RoleModel> roles = new HashSet<RoleModel>();
    		for (RoleModel role : roleList) {
    			roles.add(role);
    		}
    		userModel.setRoles(roles);
    		userModel.setResetFlag(0);
    		userModel.setStatus(1);
    		userModel.setSysFlag("3");
		}else {//其它用户
			String hql ="";
			if(agentUnit.getAgentLvl()!=null&&agentUnit.getAgentLvl() == 1){
				//运营中心
				hql="from RoleModel r where r.roleID = 21";
			}else if (agentUnit.getAgentLvl()!=null&&agentUnit.getAgentLvl() == 3){
				//快捷代理
				hql="from RoleModel r where r.roleLevel =99 and r.roleAttr = 0 ";
			}else{
				hql="from RoleModel r where r.roleLevel = 2 and r.roleAttr = 0 and r.status= 1 ";
			}
			List<RoleModel> roleList = roleDao.queryObjectsByHqlList(hql);
			Set<RoleModel> roles = new HashSet<RoleModel>();
			for (RoleModel role : roleList) {
				roles.add(role);
			}
			userModel.setRoles(roles);
			userModel.setResetFlag(0);
			userModel.setStatus(1);
		}
		userDao.saveObject(userModel);
	}

	
	
	@Override
	public void updateAgentToC(AgentUnitBean agentUnit, UserBean userBean) {
		AgentUnitModel agentUnitModel = agentUnitDao.getObjectByID(AgentUnitModel.class, agentUnit.getBuid());
		agentUnitModel.setApproveStatus("C");
		agentUnitDao.updateObject(agentUnitModel);
	}
	
	@Override
	public void updateAgentToK(AgentUnitBean agentUnit, UserBean userBean) {
		AgentUnitModel agentUnitModel = agentUnitDao.getObjectByID(AgentUnitModel.class, agentUnit.getBuid());
		agentUnitModel.setApproveStatus("K");
		agentUnitModel.setReturnReason(agentUnit.getReturnReason());
		agentUnitModel.setOpenDate(null);
		agentUnitDao.updateObject(agentUnitModel);
	}
	/**
	 * 删除代理商信息
	 */
	@Override
	public void deleteAgentUnit(Integer id) throws Exception {
		AgentUnitModel agentUnit = agentUnitDao.getObjectByID(AgentUnitModel.class, id);
		agentUnit.setMaintainType("D");
		agentUnitDao.updateObject(agentUnit);
		if(StringUtil.isNotEmpty(agentUnit.getUnno())){ //运营中心签约 默认没有机构 
			UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, agentUnit.getUnno());
			unitModel.setStatus(0);
			unitDao.updateObject(unitModel);
		}
	}

	/**
	 * 查询代理商信息（不管开通、缴款状态）
	 */
	@Override
	public DataGridBean queryAgentUnitData(AgentUnitBean agentUnit,
			UserBean user) throws Exception {
		String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		Map<String, Object> usermap = new HashMap<String, Object>();
		usermap.put("userID", user.getUserID().toString());
		usermap.put("maintainType", "D");
		List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		String sql = "";
		String sqlCount = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if(agentSalesModels.size()==0){
			if("110000".equals(user.getUnNo())){
				//当查询级别时 ，不加su.unno=ba.unno，结果全是相同的
				sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null ";
				sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null ";
				map.put("maintainType", "D");
			}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){ 
					sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null ";
					sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null ";
					map.put("maintainType", "D");
				}
			}else{
				String childUnno="("+merchantInfoService.queryUnitUnnoUtil(user.getUnNo())+")";
				sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null AND ba.UNNO in"+childUnno;
				sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null AND ba.UNNO in"+childUnno;
				map.put("maintainType", "D");
			}
		}else{
			sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType AND SIGNUSERID= :singUserId and su.unno=ba.unno and ba.agentLvl is null ";
			sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType AND SIGNUSERID= :singUserId and su.unno=ba.unno and ba.agentLvl is null ";
			map.put("maintainType", "D");
			map.put("singUserId", agentSalesModels.get(0).getBusid());
		}

		if (StringUtil.isNotEmpty(agentUnit.getAgentName())) {
			sql +=" and agentname like :agentName ";
			sqlCount +=" and agentname like :agentName ";
			map.put("agentName", '%'+agentUnit.getAgentName()+'%');
		}
		if(StringUtils.isNotEmpty(agentUnit.getRemarks())){ // @author:xuegangliu-20190226 运营中心查询
			// ba.unno in (select k.unno from  sys_unit k start with k.unno = '371000' and k.un_lvl = 1 connect by prior k.unno=k.upper_unit)
			sql +=" and ba.unno in (select ss.unno from sys_unit ss start with ss.unno = :REMARKS and ss.un_lvl = 1 connect by prior ss.unno=ss.upper_unit) ";
			sqlCount +=" and ba.unno in (select ss.unno from sys_unit ss start with ss.unno = :REMARKS and ss.un_lvl = 1 connect by prior ss.unno=ss.upper_unit) ";
			map.put("REMARKS", agentUnit.getRemarks());
		}
		if (agentUnit.getUnno() != null && !"".equals(agentUnit.getUnno())) {
			sql +=" and ba.unno=:UNNO ";
			sqlCount +=" and ba.unno=:UNNO ";
			map.put("UNNO", agentUnit.getUnno());
		}	
		//tenglong 
		if (agentUnit.getUnLvl() != null && !"".equals(agentUnit.getUnLvl())) {
			sql +=" and su.un_lvl=:UN_LVL ";
			sqlCount +=" and su.un_lvl=:UN_LVL ";
			map.put("UN_LVL", agentUnit.getUnLvl());
		}
		if (agentUnit.getOpenDate() != null) {
			sql += " and opendate =:OPENDATE";
			map.put("OPENDATE", agentUnit.getOpenDate());
		}
		if (agentUnit.getSort() != null) {
			sql += " ORDER BY " + agentUnit.getSort() + " " + agentUnit.getOrder();
		}
		
		List<AgentUnitModel> agentUnitList = agentUnitDao.querySQLAgentUnit(sql, map, agentUnit.getPage(), agentUnit.getRows(), AgentUnitModel.class);
		BigDecimal count = agentUnitDao.querysqlCounts(sqlCount, map);
		
		DataGridBean dataGridBean = formatToDataGrid(agentUnitList, count.intValue());
		
		return dataGridBean;
	}
	
	/**
	 * 查询代理商信息（修改）
	 */
	@Override
	public DataGridBean queryAgentUnitDataForUpdate(AgentUnitBean agentUnit,
			UserBean user) throws Exception {
		String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		Map<String, Object> usermap = new HashMap<String, Object>();
		usermap.put("userID", user.getUserID().toString());
		usermap.put("maintainType", "D");
		List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		String sql = "";
		String sqlCount = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if(agentSalesModels.size()==0){
			if("110000".equals(user.getUnNo())){
				//当查询级别时 ，不加su.unno=ba.unno，结果全是相同的
				sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null ";
				sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null ";
				map.put("maintainType", "D");
			}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){ 
					sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null ";
					sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null ";
					map.put("maintainType", "D");
				}
			}else{
				String childUnno="("+merchantInfoService.queryUnitUnnoUtil(user.getUnNo())+")";
				sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null and su.un_lvl>2 AND ba.UNNO in"+childUnno;
				sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null and su.un_lvl>2 AND ba.UNNO in"+childUnno;
				map.put("maintainType", "D");
			}
		}else{
			sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType AND SIGNUSERID= :singUserId and su.unno=ba.unno and ba.agentLvl is null and su.un_lvl>2 ";
			sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType AND SIGNUSERID= :singUserId and su.unno=ba.unno and ba.agentLvl is null and su.un_lvl>2 ";
			map.put("maintainType", "D");
			map.put("singUserId", agentSalesModels.get(0).getBusid());
		}

		if (StringUtil.isNotEmpty(agentUnit.getAgentName())) {
			sql +=" and agentname like :agentName ";
			sqlCount +=" and agentname like :agentName ";
			map.put("agentName", '%'+agentUnit.getAgentName()+'%');
		}	
		if (agentUnit.getUnno() != null && !"".equals(agentUnit.getUnno())) {
			sql +=" and ba.unno=:UNNO ";
			sqlCount +=" and ba.unno=:UNNO ";
			map.put("UNNO", agentUnit.getUnno());
		}	
		//tenglong 
		if (agentUnit.getUnLvl() != null && !"".equals(agentUnit.getUnLvl())) {
			sql +=" and su.un_lvl=:UN_LVL ";
			sqlCount +=" and su.un_lvl=:UN_LVL ";
			map.put("UN_LVL", agentUnit.getUnLvl());
		}
		if (agentUnit.getOpenDate() != null) {
			sql += " and opendate =:OPENDATE";
			map.put("OPENDATE", agentUnit.getOpenDate());
		}
		if (agentUnit.getSort() != null) {
			sql += " ORDER BY " + agentUnit.getSort() + " " + agentUnit.getOrder();
		}
		
		List<AgentUnitModel> agentUnitList = agentUnitDao.querySQLAgentUnit(sql, map, agentUnit.getPage(), agentUnit.getRows(), AgentUnitModel.class);
		BigDecimal count = agentUnitDao.querysqlCounts(sqlCount, map);
		
		DataGridBean dataGridBean = formatToDataGrid(agentUnitList, count.intValue());
		
		return dataGridBean;
	}

	public Map importCashStatusByExcel(String xlsfile, String name, UserBean user){
		Map resutMap = new HashMap();
		int sumCount = 0;
		int errCount = 0;
		int upCount = 0;
		File filename = new File(xlsfile);
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);
			sumCount = sheet.getPhysicalNumberOfRows()-1;
			List<Map<String,String>> importData = new ArrayList<Map<String, String>>();
			Set<String> keySet = new HashSet<String>();

			for(int i = 1; i <= sumCount; i++){
				Map<String,String> map = new HashMap<String, String>();
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell = row.getCell(0);
				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
					//一代机构号
					row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					String unno = row.getCell(0).getStringCellValue().trim();
					map.put("UNNO",unno);
					keySet.add(unno);
					//是否开通返现钱包
					row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					String cashStatus = row.getCell(1).getStringCellValue().trim();
					if(cashStatus.contains("是")){
						map.put("CASHSTATUS","1");
					}else if(cashStatus.contains("否")){
						map.put("CASHSTATUS","0");
					}
					importData.add(map);
				}
			}
			if(keySet.size()!=importData.size()){
				errCount = sumCount;
			}else{
				for(Map updata:importData){
					String updateSql = "update bill_agentunitinfo ba set ba.cashstatus='"+updata.get("CASHSTATUS")+
							"' where ba.unno='"+updata.get("UNNO")+"'";
					// @author:xuegangliu-20190226 导入返现修改数据状态
					int result = agentSalesDao.executeSqlUpdate(updateSql,null);
					if(result>0){
						upCount++;
					}
					updateSql="";
				}
			}
		} catch (IOException e) {
			log.error("批量返现导入修改异常："+e);
			e.printStackTrace();
		}
		resutMap.put("errCount",errCount);
		resutMap.put("sumCount",sumCount);
		resutMap.put("upCount",upCount);
		return resutMap;
	}
	/**
	 * 查询代理商信息（不管开通、缴款状态）
	 */
	@Override
	public DataGridBean queryAgentUnitDataBeiJing(AgentUnitBean agentUnit,
			UserBean user) throws Exception {
		String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		Map<String, Object> usermap = new HashMap<String, Object>();
		usermap.put("userID", user.getUserID().toString());
		usermap.put("maintainType", "D");
		List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		String sql = "";
		String sqlCount = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if(agentSalesModels.size()==0){
			if("110000".equals(user.getUnNo())){
				//当查询级别时 ，不加su.unno=ba.unno，结果全是相同的
				sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null ";
				sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null ";
				map.put("maintainType", "D");
			}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){ 
					sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null ";
					sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null ";
					map.put("maintainType", "D");
				}
			}else{
				String childUnno="("+merchantInfoService.queryUnitUnnoUtil(user.getUnNo())+")";
				sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null AND ba.UNNO in"+childUnno;
				sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl is null AND ba.UNNO in"+childUnno;
				map.put("maintainType", "D");
			}
		}else{
			sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType AND SIGNUSERID= :singUserId and su.unno=ba.unno and ba.agentLvl is null ";
			sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType AND SIGNUSERID= :singUserId and su.unno=ba.unno and ba.agentLvl is null ";
			map.put("maintainType", "D");
			map.put("singUserId", agentSalesModels.get(0).getBusid());
		}
		
		if (StringUtil.isNotEmpty(agentUnit.getAgentName())) {
			sql +=" and agentname = :agentName ";
			sqlCount +=" and agentname = :agentName ";
			map.put("agentName", agentUnit.getAgentName());
		}	
		sql +=" and agentname like '北京%公司%' ";
		sqlCount +=" and agentname like '北京%公司%' ";
		if (agentUnit.getUnno() != null && !"".equals(agentUnit.getUnno())) {
			sql +=" and ba.unno=:UNNO ";
			sqlCount +=" and ba.unno=:UNNO ";
			map.put("UNNO", agentUnit.getUnno());
		}	
		//tenglong 
		if (agentUnit.getUnLvl() != null && !"".equals(agentUnit.getUnLvl())) {
			sql +=" and su.un_lvl=:UN_LVL ";
			sqlCount +=" and su.un_lvl=:UN_LVL ";
			map.put("UN_LVL", agentUnit.getUnLvl());
		}
		if (agentUnit.getOpenDate() != null) {
			sql += " and opendate =:OPENDATE";
			map.put("OPENDATE", agentUnit.getOpenDate());
		}
		if (agentUnit.getSort() != null) {
			sql += " ORDER BY " + agentUnit.getSort() + " " + agentUnit.getOrder();
		}
		
		List<AgentUnitModel> agentUnitList = agentUnitDao.querySQLAgentUnit(sql, map, agentUnit.getPage(), agentUnit.getRows(), AgentUnitModel.class);
		BigDecimal count = agentUnitDao.querysqlCounts(sqlCount, map);
		
		DataGridBean dataGridBean = formatToDataGrid(agentUnitList, count.intValue());
		
		return dataGridBean;
	}
	
	/**
	 * 查询代理商信息（不管开通、缴款状态）
	 */
	@Override
	public DataGridBean queryAgentUnitData10142(AgentUnitBean agentUnit,
			UserBean user) throws Exception {
		String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		Map<String, Object> usermap = new HashMap<String, Object>();
		usermap.put("userID", user.getUserID().toString());
		usermap.put("maintainType", "D");
		List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		String sql = "";
		String sqlCount = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if(agentSalesModels.size()==0){
			if("110000".equals(user.getUnNo())){
				//当查询级别时 ，不加su.unno=ba.unno，结果全是相同的
				sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl in (1,2) ";
				sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl in (1,2) ";
				map.put("maintainType", "D");
			}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){ 
					sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl in (1,2)";
					sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl in (1,2) ";
					map.put("maintainType", "D");
				}
			}else{
				String childUnno="("+merchantInfoService.queryUnitUnnoUtil(user.getUnNo())+")";
				sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl =1 AND ba.UNNO in"+childUnno;
				sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType and su.unno=ba.unno and ba.agentLvl =1 AND ba.UNNO in"+childUnno;
				map.put("maintainType", "D");
			}
		}else{
			sql = "SELECT ba.* FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType AND SIGNUSERID= :singUserId and su.unno=ba.unno and ba.agentLvl in (1,2) ";
			sqlCount = "SELECT COUNT(*) FROM BILL_AGENTUNITINFO ba,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType AND SIGNUSERID= :singUserId and su.unno=ba.unno and ba.agentLvl in (1,2) ";
			map.put("maintainType", "D");
			map.put("singUserId", agentSalesModels.get(0).getBusid());
		}

		if (StringUtil.isNotEmpty(agentUnit.getAgentName())) {
			sql +=" and agentname like :agentName ";
			sqlCount +=" and agentname like :agentName ";
			map.put("agentName", '%'+agentUnit.getAgentName()+'%');
		}	
		if (agentUnit.getUnno() != null && !"".equals(agentUnit.getUnno())) {
			sql +=" and ba.unno=:UNNO ";
			sqlCount +=" and ba.unno=:UNNO ";
			map.put("UNNO", agentUnit.getUnno());
		}	
		//tenglong 
		if (agentUnit.getUnLvl() != null && !"".equals(agentUnit.getUnLvl())) {
			sql +=" and su.un_lvl=:UN_LVL ";
			sqlCount +=" and su.un_lvl=:UN_LVL ";
			map.put("UN_LVL", agentUnit.getUnLvl());
		}	
		if (agentUnit.getSort() != null) {
			sql += " ORDER BY " + agentUnit.getSort() + " " + agentUnit.getOrder();
		}
		
		List<AgentUnitModel> agentUnitList = agentUnitDao.querySQLAgentUnit(sql, map, agentUnit.getPage(), agentUnit.getRows(), AgentUnitModel.class);
		BigDecimal count = agentUnitDao.querysqlCounts(sqlCount, map);
		
		DataGridBean dataGridBean = formatToDataGrid(agentUnitList, count.intValue());
		
		return dataGridBean;
	}
	
	/**
	 * 导出全部
	 */
	@Override
	public HSSFWorkbook exportAgent(AgentUnitBean agentUnit,UserBean user) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		String sql = "";
		String sqlCount = "";
		Map<String, Object> map = new HashMap<String, Object>();
		String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		Map<String, Object> usermap = new HashMap<String, Object>();
		usermap.put("userID", user.getUserID().toString());
		usermap.put("maintainType", "D");
		List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		if(agentSalesModels.size()==0){
			if("110000".equals(user.getUnNo())){
				  sql = " select buid,t.unno,agentname,baddr,legalperson,(case legaltype when '1' then '身份证' when '2' then '军官证' when '3' then '护照' when '4' then '港澳通行证' else '其他' end) legaltype, "+
						" legalnum,(case acctype when '1' then '对公' when '2' then '对私' end) acctype,bankbranch,bankaccno,bankaccname,(case banktype when '1' then '交通银行' when '2' then '非交通银行' end) banktype, "+
						" decode(areatype, '1','北京','2','非北京') areatype,bankarea,riskamount,amountconfirmdate,opendate,contractno,signuserid,bno,rno,registryno,bankopenacc,maintainuserid,contact,contacttel,contactphone, "+
						" secondcontact,secondcontacttel,secondcontactphone,riskcontact,riskcontactphone,riskcontactmail,businesscontacts,businesscontactsphone,businesscontactsmail,maintainuid,maintaindate,maintaintype,remarks,v.province_name provincename,cashstatus"+
						" from BILL_AGENTUNITINFO t,sys_province v,sys_unit su WHERE substr(t.unno,1,2)=v.province_code(+) and t.unno=su.unno and MAINTAINTYPE != :maintainType ";
				  map.put("maintainType", 'D');
			}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
					sql = " select buid,t.unno,agentname,baddr,legalperson,(case legaltype when '1' then '身份证' when '2' then '军官证' when '3' then '护照' when '4' then '港澳通行证' else '其他' end) legaltype, "+
						" legalnum,(case acctype when '1' then '对公' when '2' then '对私' end) acctype,bankbranch,bankaccno,bankaccname,(case banktype when '1' then '交通银行' when '2' then '非交通银行' end) banktype, "+
						" decode(areatype, '1','北京','2','非北京') areatype,bankarea,riskamount,amountconfirmdate,opendate,contractno,signuserid,bno,rno,registryno,bankopenacc,maintainuserid,contact,contacttel,contactphone, "+
						" secondcontact,secondcontacttel,secondcontactphone,riskcontact,riskcontactphone,riskcontactmail,businesscontacts,businesscontactsphone,businesscontactsmail,maintainuid,maintaindate,maintaintype,remarks,v.province_name provincename,cashstatus"+
						" from BILL_AGENTUNITINFO t,sys_province v,sys_unit su WHERE substr(t.unno,1,2)=v.province_code(+) and t.unno=su.unno and MAINTAINTYPE != :maintainType ";
					map.put("maintainType", 'D');
				}
			}else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){
				sql = " select buid,unno,agentname,baddr,legalperson,(case legaltype when '1' then '身份证' when '2' then '军官证' when '3' then '护照' when '4' then '港澳通行证' else '其他' end) legaltype, "+
						" legalnum,(case acctype when '1' then '对公' when '2' then '对私' end) acctype,bankbranch,bankaccno,bankaccname,(case banktype when '1' then '交通银行' when '2' then '非交通银行' end) banktype, "+
						" decode(areatype, '1','北京','2','非北京') areatype,bankarea,riskamount,amountconfirmdate,opendate,contractno,signuserid,bno,rno,registryno,bankopenacc,maintainuserid,contact,contacttel,contactphone, "+
						" secondcontact,secondcontacttel,secondcontactphone,riskcontact,riskcontactphone,riskcontactmail,businesscontacts,businesscontactsphone,businesscontactsmail,maintainuid,maintaindate,maintaintype,remarks,v.province_name provincename,cashstatus"+
						" from BILL_AGENTUNITINFO t,sys_province v,SYS_UNIT su WHERE substr(t.unno,1,2)=v.province_code(+) and t.unno=su.unno and MAINTAINTYPE != :maintainType AND UNNO IN (SELECT UNNO  FROM SYS_UNIT  WHERE UPPER_UNIT IN (SELECT UNNO FROM SYS_UNIT WHERE (UPPER_UNIT = :unno OR UNNO = :unno)  AND STATUS = 1) OR (UNNO = :unno and status =1)) ";
				map.put("maintainType", 'D');
				map.put("unno", user.getUnNo());
			}else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 2){
				sql = 	" select buid,unno,agentname,baddr,legalperson,(case legaltype when '1' then '身份证' when '2' then '军官证' when '3' then '护照' when '4' then '港澳通行证' else '其他' end) legaltype, "+
						" legalnum,(case acctype when '1' then '对公' when '2' then '对私' end) acctype,bankbranch,bankaccno,bankaccname,(case banktype when '1' then '交通银行' when '2' then '非交通银行' end) banktype, "+
						" decode(areatype, '1','北京','2','非北京') areatype,bankarea,riskamount,amountconfirmdate,opendate,contractno,signuserid,bno,rno,registryno,bankopenacc,maintainuserid,contact,contacttel,contactphone, "+
						" secondcontact,secondcontacttel,secondcontactphone,riskcontact,riskcontactphone,riskcontactmail,businesscontacts,businesscontactsphone,businesscontactsmail,maintainuid,maintaindate,maintaintype,remarks,v.province_name provincename,cashstatus"+
						" from BILL_AGENTUNITINFO t,sys_province v,SYS_UNIT su WHERE substr(t.unno,1,2)=v.province_code(+) and t.unno=su.unno and MAINTAINTYPE != :maintainType AND t.UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE (UPPER_UNIT = :unno OR UNNO = :unno) AND STATUS = 1) ";
				map.put("maintainType", 'D');
				map.put("unno", user.getUnNo());
			}else{
				sql = " select buid,unno,agentname,baddr,legalperson,(case legaltype when '1' then '身份证' when '2' then '军官证' when '3' then '护照' when '4' then '港澳通行证' else '其他' end) legaltype, "+
						" legalnum,(case acctype when '1' then '对公' when '2' then '对私' end) acctype,bankbranch,bankaccno,bankaccname,(case banktype when '1' then '交通银行' when '2' then '非交通银行' end) banktype, "+
						" decode(areatype, '1','北京','2','非北京') areatype,bankarea,riskamount,amountconfirmdate,opendate,contractno,signuserid,bno,rno,registryno,bankopenacc,maintainuserid,contact,contacttel,contactphone, "+
						" secondcontact,secondcontacttel,secondcontactphone,riskcontact,riskcontactphone,riskcontactmail,businesscontacts,businesscontactsphone,businesscontactsmail,maintainuid,maintaindate,maintaintype,remarks,v.province_name provincename,cashstatus"+
						" from BILL_AGENTUNITINFO t,sys_province v,SYS_UNIT su WHERE substr(t.unno,1,2)=v.province_code(+) and t.unno=su.unno and MAINTAINTYPE != :maintainType AND UNNO = :unno ";
				map.put("maintainType", 'D');
				map.put("unno", user.getUnNo());
			}
		}else{
			sql = "SELECT t.* FROM BILL_AGENTUNITINFO t,SYS_UNIT su WHERE MAINTAINTYPE != :maintainType AND SIGNUSERID= :singUserId and su.unno=t.unno ";
			map.put("maintainType", "D");
			map.put("singUserId", agentSalesModels.get(0).getBusid());
		}
		//用于区分导出运营中心 和代理商
		if(agentUnit.getAgentLvl()!=null && agentUnit.getAgentLvl()==1){
			sql+=" and t.agentLvl =1 ";
		}else{
			sql+=" and t.agentLvl is null ";
		}
		if (StringUtil.isNotEmpty(agentUnit.getAgentName())) {
			sql +=" and t.agentname like :agentName ";
			map.put("agentName", '%'+agentUnit.getAgentName()+'%');
		}
		if(StringUtils.isNotEmpty(agentUnit.getRemarks())){ // @author:xuegangliu-20190226 运营中心查询
			// ba.unno in (select k.unno from  sys_unit k start with k.unno = '371000' and k.un_lvl = 1 connect by prior k.unno=k.upper_unit)
			sql +=" and t.unno in (select ss.unno from sys_unit ss start with ss.unno = :REMARKS and ss.un_lvl = 1 connect by prior ss.unno=ss.upper_unit) ";
			sqlCount +=" and t.unno in (select ss.unno from sys_unit ss start with ss.unno = :REMARKS and ss.un_lvl = 1 connect by prior ss.unno=ss.upper_unit) ";
			map.put("REMARKS", agentUnit.getRemarks());
		}
		if (agentUnit.getUnno() != null && !"".equals(agentUnit.getUnno())) {
			sql +=" and t.unno=:UNNO ";
			map.put("UNNO", agentUnit.getUnno());
		}	
		if (agentUnit.getUnLvl() != null && !"".equals(agentUnit.getUnLvl())) {
			sql +=" and su.un_lvl=:UN_LVL ";
			map.put("UN_LVL", agentUnit.getUnLvl());
		}
		if (agentUnit.getSort() != null) {
			sql += " ORDER BY " + agentUnit.getSort() + " " + agentUnit.getOrder();
		}

		//String sql1="SELECT COLUMN_NAME FROM user_tab_columns WHERE table_name = 'BILL_MERCHANTINFO'";
		List<Map<String, Object>> data=agentUnitDao.executeSql2(sql, map);
		List<String> excelHeader=new ArrayList<String>();
		Map<String, Object> headMap=new HashMap<String, Object>();
		//excelHeader.add("BUID");
		excelHeader.add("UNNO");		
		excelHeader.add("AGENTNAME");
		excelHeader.add("PROVINCENAME");//provincename
		excelHeader.add("BADDR");
		excelHeader.add("LEGALPERSON");
		excelHeader.add("LEGALTYPE");//LegalType法人证件类型
		excelHeader.add("LEGALNUM");//LegalNum法人证件号码
		excelHeader.add("BNO");
		excelHeader.add("RNO");
		excelHeader.add("REGISTRYNO");//RegistryNo税务登记证号
		excelHeader.add("BANKOPENACC");//BankOpenAcc银行开户许可证号
		excelHeader.add("RISKAMOUNT");//RiskAmount风保障金
		excelHeader.add("CONTRACTNO");//ContractNO签约合同编号
		excelHeader.add("ACCTYPE");//AccType开户类型
		excelHeader.add("BANKBRANCH");//BankBranch开户银行
		excelHeader.add("BANKACCNO");//BankAccNo开户银行账号
		excelHeader.add("BANKACCNAME");//BankAccName开户账号名称
		excelHeader.add("BANKTYPE");//BankType是否为交通银行
		excelHeader.add("AREATYPE");//AreaType开户银行所在地类别
		excelHeader.add("BANKAREA");//BankArea开户地
		excelHeader.add("CONTACT");//Contact负责人
		excelHeader.add("CONTACTTEL");//ContactTel负责人固定电话
		excelHeader.add("CONTACTPHONE");//ContactPhone负责人手机
		excelHeader.add("SECONDCONTACT");//SecondContact第二联系人
		excelHeader.add("SECONDCONTACTTEL");//SecondContactTel第二联系人固定电话
		excelHeader.add("SECONDCONTACTPHONE");//SecondContactPhone第二联系人手机
		excelHeader.add("RISKCONTACT");//RiskContact风险调单联系人
		excelHeader.add("RISKCONTACTPHONE");//RiskContactPhone风险调单联系手机
		excelHeader.add("RISKCONTACTMAIL");//RiskContactMail风险调单联系邮箱
		excelHeader.add("BUSINESSCONTACTS");//BusinessContacts业务联系人
		excelHeader.add("BUSINESSCONTACTSPHONE");//BusinessContactsPhone业务联系手机
		excelHeader.add("BUSINESSCONTACTSMAIL");//BusinessContactsMail业务联系邮箱
		//excelHeader.add("MAINTAINUSERID");
		excelHeader.add("OPENDATE");
		excelHeader.add("AMOUNTCONFIRMDATE");
		excelHeader.add("CASHSTATUS");

		//headMap.put("BUID", "代理商编号");
		headMap.put("UNNO", "机构编号");
		headMap.put("AGENTNAME", "代理商名称");
		headMap.put("PROVINCENAME", "归属地");
		headMap.put("BADDR", "经营地址");
		headMap.put("LEGALPERSON", "法人");
		headMap.put("LEGALTYPE", "法人证件类型");
		headMap.put("LEGALNUM", "法人证件号码");
		headMap.put("BNO", "营业执照编号");
		headMap.put("RNO", "组织机构代码");
		headMap.put("REGISTRYNO", "税务登记证号");
		headMap.put("BANKOPENACC", "银行开户许可证号");
		headMap.put("RISKAMOUNT", "风险保障金");
		headMap.put("CONTRACTNO", "签约合同编号");
		headMap.put("ACCTYPE", "开户类型");
		headMap.put("BANKBRANCH", "开户银行");
		headMap.put("BANKACCNO", "开户银行账号");
		headMap.put("BANKACCNAME", "开户账号名称");
		headMap.put("BANKTYPE", "是否为交通银行");
		headMap.put("AREATYPE", "开户银行所在地类别");
		headMap.put("BANKAREA", "开户地");
		headMap.put("CONTACT", "负责人");
		headMap.put("CONTACTTEL", "负责人固定电话");
		headMap.put("CONTACTPHONE", "负责人手机");
		headMap.put("SECONDCONTACT", "第二联系人");
		headMap.put("SECONDCONTACTTEL", "第二联系人固定电话");
		headMap.put("SECONDCONTACTPHONE", "第二联系人手机");
		headMap.put("RISKCONTACT", "风险调单联系人");
		headMap.put("RISKCONTACTPHONE", "风险调单联系手机");
		headMap.put("RISKCONTACTMAIL", "风险调单联系邮箱");
		headMap.put("BUSINESSCONTACTS", "业务联系人");
		headMap.put("BUSINESSCONTACTSPHONE", "业务联系手机");
		headMap.put("BUSINESSCONTACTSMAIL", "业务联系邮箱");
		headMap.put("OPENDATE", "开通日期");
		headMap.put("AMOUNTCONFIRMDATE", "缴款日期");
		headMap.put("CASHSTATUS","是否开通提现钱包(0否,1是)");
		//headMap.put("MAINTAINUSERID", "维护人员");
		saveExportLog(agentUnit.getAgentLvl()!=null?agentUnit.getAgentLvl()==1?3:2:2,data.size(),user.getUserID());//添加导出记录
		return ExcelUtil.export("代理商资料", data, excelHeader, headMap);
	}
	/**
	 * 导出代理商信息excel
	 */
	@Override
	public HSSFWorkbook export(String ids,UserBean user,AgentUnitBean agentUnit) {
		String sql=" select buid,unno,agentname,baddr,legalperson,(case legaltype when '1' then '身份证' when '2' then '军官证' when '3' then '护照' when '4' then '港澳通行证' else '其他' end) legaltype, "+
					" legalnum,(case acctype when '1' then '对公' when '2' then '对私' end) acctype,bankbranch,bankaccno,bankaccname,(case banktype when '1' then '交通银行' when '2' then '非交通银行' end) banktype, "+
					" decode(areatype, '1','北京','2','非北京') areatype,bankarea,riskamount,amountconfirmdate,opendate,contractno,signuserid,bno,rno,registryno,bankopenacc,maintainuserid,contact,contacttel,contactphone, "+
					" secondcontact,secondcontacttel,secondcontactphone,riskcontact,riskcontactphone,riskcontactmail,businesscontacts,businesscontactsphone,businesscontactsmail,maintainuid,maintaindate,maintaintype,remarks,v.province_name provincename"+
					" from BILL_AGENTUNITINFO t,sys_province v WHERE substr(t.unno,1,2)=v.province_code(+) and t.BUID IN ("+ids+") ";

		//String sql1="SELECT COLUMN_NAME FROM user_tab_columns WHERE table_name = 'BILL_MERCHANTINFO'";
		List<Map<String, Object>> data=agentUnitDao.queryObjectsBySqlList(sql);
		List<String> excelHeader=new ArrayList<String>();
		Map<String, Object> headMap=new HashMap<String, Object>();
		//excelHeader.add("BUID");
		excelHeader.add("UNNO");		
		excelHeader.add("AGENTNAME");
		excelHeader.add("PROVINCENAME");//provincename
		excelHeader.add("BADDR");
		excelHeader.add("LEGALPERSON");
		excelHeader.add("LEGALTYPE");//LegalType法人证件类型
		excelHeader.add("LEGALNUM");//LegalNum法人证件号码
		excelHeader.add("BNO");
		excelHeader.add("RNO");
		excelHeader.add("REGISTRYNO");//RegistryNo税务登记证号
		excelHeader.add("BANKOPENACC");//BankOpenAcc银行开户许可证号
		excelHeader.add("RISKAMOUNT");//RiskAmount风保障金
		excelHeader.add("CONTRACTNO");//ContractNO签约合同编号
		excelHeader.add("ACCTYPE");//AccType开户类型
		excelHeader.add("BANKBRANCH");//BankBranch开户银行
		excelHeader.add("BANKACCNO");//BankAccNo开户银行账号
		excelHeader.add("BANKACCNAME");//BankAccName开户账号名称
		excelHeader.add("BANKTYPE");//BankType是否为交通银行
		excelHeader.add("AREATYPE");//AreaType开户银行所在地类别
		excelHeader.add("BANKAREA");//BankArea开户地
		excelHeader.add("CONTACT");//Contact负责人
		excelHeader.add("CONTACTTEL");//ContactTel负责人固定电话
		excelHeader.add("CONTACTPHONE");//ContactPhone负责人手机
		excelHeader.add("SECONDCONTACT");//SecondContact第二联系人
		excelHeader.add("SECONDCONTACTTEL");//SecondContactTel第二联系人固定电话
		excelHeader.add("SECONDCONTACTPHONE");//SecondContactPhone第二联系人手机
		excelHeader.add("RISKCONTACT");//RiskContact风险调单联系人
		excelHeader.add("RISKCONTACTPHONE");//RiskContactPhone风险调单联系手机
		excelHeader.add("RISKCONTACTMAIL");//RiskContactMail风险调单联系邮箱
		excelHeader.add("BUSINESSCONTACTS");//BusinessContacts业务联系人
		excelHeader.add("BUSINESSCONTACTSPHONE");//BusinessContactsPhone业务联系手机
		excelHeader.add("BUSINESSCONTACTSMAIL");//BusinessContactsMail业务联系邮箱
		//excelHeader.add("MAINTAINUSERID");
		excelHeader.add("OPENDATE");
		excelHeader.add("AMOUNTCONFIRMDATE");
		
		//headMap.put("BUID", "代理商编号");
		headMap.put("UNNO", "机构编号");
		headMap.put("AGENTNAME", "代理商名称");
		headMap.put("PROVINCENAME", "归属地");
		headMap.put("BADDR", "经营地址");
		headMap.put("LEGALPERSON", "法人");
		headMap.put("LEGALTYPE", "法人证件类型");
		headMap.put("LEGALNUM", "法人证件号码");
		headMap.put("BNO", "营业执照编号");
		headMap.put("RNO", "组织机构代码");
		headMap.put("REGISTRYNO", "税务登记证号");
		headMap.put("BANKOPENACC", "银行开户许可证号");
		headMap.put("RISKAMOUNT", "风险保障金");
		headMap.put("CONTRACTNO", "签约合同编号");
		headMap.put("ACCTYPE", "开户类型");
		headMap.put("BANKBRANCH", "开户银行");
		headMap.put("BANKACCNO", "开户银行账号");
		headMap.put("BANKACCNAME", "开户账号名称");
		headMap.put("BANKTYPE", "是否为交通银行");
		headMap.put("AREATYPE", "开户银行所在地类别");
		headMap.put("BANKAREA", "开户地");
		headMap.put("CONTACT", "负责人");
		headMap.put("CONTACTTEL", "负责人固定电话");
		headMap.put("CONTACTPHONE", "负责人手机");
		headMap.put("SECONDCONTACT", "第二联系人");
		headMap.put("SECONDCONTACTTEL", "第二联系人固定电话");
		headMap.put("SECONDCONTACTPHONE", "第二联系人手机");
		headMap.put("RISKCONTACT", "风险调单联系人");
		headMap.put("RISKCONTACTPHONE", "风险调单联系手机");
		headMap.put("RISKCONTACTMAIL", "风险调单联系邮箱");
		headMap.put("BUSINESSCONTACTS", "业务联系人");
		headMap.put("BUSINESSCONTACTSPHONE", "业务联系手机");
		headMap.put("BUSINESSCONTACTSMAIL", "业务联系邮箱");
		headMap.put("OPENDATE", "开通日期");
		headMap.put("AMOUNTCONFIRMDATE", "缴款日期");
		//headMap.put("MAINTAINUSERID", "维护人员");
		saveExportLog(agentUnit.getAgentLvl()!=null?agentUnit.getAgentLvl()==1?3:2:2,data.size(),user.getUserID());//添加导出记录
		return ExcelUtil.export("代理商资料", data, excelHeader, headMap);
	}

	/**
	 * 查询简码是否存在
	 */
	@Override
	public Integer queryUnitCounts(String unitCode) {
		String sql="select count(*) from sys_unitcode where unit_code=:unitCode";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unitCode", unitCode);
		Integer count=unitDao.querysqlCounts2(sql, map);
		return count;
	}

	/**
	 * 根据id得到代理商
	 */
	@Override
	public AgentUnitModel getInfoModel(Integer id) {
		return agentUnitDao.getObjectByID(AgentUnitModel.class, id);
	}

    @Override
    public AgentUnitModel getInfoModelByUnno(String unno) {
        return (AgentUnitModel) agentUnitDao.queryObjectByHql("from AgentUnitModel m where m.unno=?", new Object[]{unno});
    }

	@Override
	public List<Object> queryAgentInfoDetailed(Integer buid) {
		AgentUnitModel mm=(AgentUnitModel) agentUnitDao.queryObjectByHql("from AgentUnitModel m where m.buid=?", new Object[]{buid});
		String sql="select * from SYS_PARAM t WHERE t.title='AgentInfo'";
		List<Map<String, Object>> list=agentUnitDao.queryObjectsBySqlList(sql);
		String upload =(String) list.get(0).get("UPLOAD_PATH");
		List<Object> list2=new ArrayList<Object>();

		if(StringUtil.isNotEmpty(mm.getDealUpLoad())){
			list2.add(upload+File.separator + mm.getDealUpLoad());
		}else{
			list2.add("");
		}
		
		if(StringUtil.isNotEmpty(mm.getBusLicUpLoad())){
			list2.add(upload+File.separator + mm.getBusLicUpLoad());
		}else{
			list2.add("");
		}
		
		if(StringUtil.isNotEmpty(mm.getProofOfOpenAccountUpLoad())){
			list2.add(upload+File.separator + mm.getProofOfOpenAccountUpLoad());
		}else{
			list2.add("");
		}
		
		if(StringUtil.isNotEmpty(mm.getLegalAUpLoad())){
			list2.add(upload+File.separator + mm.getLegalAUpLoad());
		}else{
			list2.add("");
		}
		
		if(StringUtil.isNotEmpty(mm.getLegalBUpLoad())){
			list2.add(upload+File.separator + mm.getLegalBUpLoad());
		}else{
			list2.add("");
		}
		
		if(StringUtil.isNotEmpty(mm.getAccountAuthUpLoad())){
			list2.add(upload+File.separator + mm.getAccountAuthUpLoad());
		}else{
			list2.add("");
		}
		if(StringUtil.isNotEmpty(mm.getAccountLegalAUpLoad())){
			list2.add(upload+File.separator + mm.getAccountLegalAUpLoad());
		}else{
			list2.add("");
		}
		if(StringUtil.isNotEmpty(mm.getAccountLegalBUpLoad())){
			list2.add(upload+File.separator + mm.getAccountLegalBUpLoad());
		}else{
			list2.add("");
		}
		if(StringUtil.isNotEmpty(mm.getAccountLegalHandUpLoad())){
			list2.add(upload+File.separator + mm.getAccountLegalHandUpLoad());
		}else{
			list2.add("");
		}
		
		if(StringUtil.isNotEmpty(mm.getOfficeAddressUpLoad())){
			list2.add(upload+File.separator + mm.getOfficeAddressUpLoad());
		}else{
			list2.add("");
		}
		if(StringUtil.isNotEmpty(mm.getProfitUpLoad())){
			list2.add(upload+File.separator + mm.getProfitUpLoad());
		}else{
			list2.add("");
		}
		return list2; 
	}
	@Override
	public List<Object> queryAgentInfoDetailed2(UserBean user) {
		AgentUnitModel mm=(AgentUnitModel) agentUnitDao.queryObjectByHql("from AgentUnitModel m where m.unno=?", new Object[]{user.getUnNo()});
		String sql="select * from SYS_PARAM t WHERE t.title='AgentInfo'";
		List<Map<String, Object>> list=agentUnitDao.queryObjectsBySqlList(sql);
		String upload =(String) list.get(0).get("UPLOAD_PATH");
		List<Object> list2=new ArrayList<Object>();
		list2.add(mm);
		if(StringUtil.isNotEmpty(mm.getDealUpLoad())){
			list2.add(upload+File.separator + mm.getDealUpLoad());
		}else{
			list2.add("");
		}
		
		if(StringUtil.isNotEmpty(mm.getBusLicUpLoad())){
			list2.add(upload+File.separator + mm.getBusLicUpLoad());
		}else{
			list2.add("");
		}
		
		if(StringUtil.isNotEmpty(mm.getProofOfOpenAccountUpLoad())){
			list2.add(upload+File.separator + mm.getProofOfOpenAccountUpLoad());
		}else{
			list2.add("");
		}
		
		if(StringUtil.isNotEmpty(mm.getLegalAUpLoad())){
			list2.add(upload+File.separator + mm.getLegalAUpLoad());
		}else{
			list2.add("");
		}
		
		if(StringUtil.isNotEmpty(mm.getLegalBUpLoad())){
			list2.add(upload+File.separator + mm.getLegalBUpLoad());
		}else{
			list2.add("");
		}
		
		if(StringUtil.isNotEmpty(mm.getAccountAuthUpLoad())){
			list2.add(upload+File.separator + mm.getAccountAuthUpLoad());
		}else{
			list2.add("");
		}
		if(StringUtil.isNotEmpty(mm.getAccountLegalAUpLoad())){
			list2.add(upload+File.separator + mm.getAccountLegalAUpLoad());
		}else{
			list2.add("");
		}
		if(StringUtil.isNotEmpty(mm.getAccountLegalBUpLoad())){
			list2.add(upload+File.separator + mm.getAccountLegalBUpLoad());
		}else{
			list2.add("");
		}
		if(StringUtil.isNotEmpty(mm.getAccountLegalHandUpLoad())){
			list2.add(upload+File.separator + mm.getAccountLegalHandUpLoad());
		}else{
			list2.add("");
		}
		
		if(StringUtil.isNotEmpty(mm.getOfficeAddressUpLoad())){
			list2.add(upload+File.separator + mm.getOfficeAddressUpLoad());
		}else{
			list2.add("");
		}
		if(StringUtil.isNotEmpty(mm.getProfitUpLoad())){
			list2.add(upload+File.separator + mm.getProfitUpLoad());
		}else{
			list2.add("");
		}
		return list2; 
	}
	@Override
	public Integer queryAgentUnitNameExits(String agentName,Integer buid) {
		String sql="select count(1) from bill_AgentUnitInfo where agentName=:agentName ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agentName", agentName);
		if (buid != null && StringUtil.isNotEmpty(buid.toString())) {
			sql += " and buid != :buid";
			map.put("buid", buid);
		}
		
		Integer count=unitDao.querysqlCounts2(sql, map);
		return count;
	}
	@Override
	public Integer queryAgentUnitNameExits2(AgentUnitBean agentUnit) {
		// @author:xuegangliu-20190218 代理商签约,退回修改提交显示 当前代理商全称已存在，代理商修改失败 bug处理
		String sql="select count(1) from bill_AgentUnitInfo where agentName=:agentName and buid!=:buid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agentName", agentUnit.getAgentName());
		map.put("buid", agentUnit.getBuid());
		if(agentUnit.getUnno()!= null && StringUtil.isNotEmpty(agentUnit.getUnno())) {
			sql += " and unno != :unno and unno != :unno2";
			map.put("unno", agentUnit.getUnno());
			if("1".equals(agentUnit.getUnno())||"2".equals(agentUnit.getUnno())||"3".equals(agentUnit.getUnno())
			||"4".equals(agentUnit.getUnno())||"5".equals(agentUnit.getUnno())||"6".equals(agentUnit.getUnno())
			||"7".equals(agentUnit.getUnno())||"8".equals(agentUnit.getUnno())||"9".equals(agentUnit.getUnno())
			||"A".equals(agentUnit.getUnno())||"0".equals(agentUnit.getUnno())) {
				map.put("unno2", formatUnno(agentUnit.getUnno()));
			}else{
				map.put("unno2", formatUnno2(agentUnit.getUnno()));
			}
		}
		Integer count=unitDao.querysqlCounts2(sql, map);
		return count;
	}
	@Override
	public Integer queryShortNmNameExits(String shortNm,Integer buid) {
		String sql="select count(1) from bill_AgentUnitInfo where agentShortNm=:agentShortNm ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agentShortNm", shortNm);
		if (buid != null && StringUtil.isNotEmpty(buid.toString())) {
			sql += " and buid != :buid";
			map.put("buid", buid);
		}
		
		Integer count=unitDao.querysqlCounts2(sql, map);
		return count;
	}
	@Override
	public Integer queryShortNmNameExits2(AgentUnitBean agentUnit) {
		// @author:xuegangliu-20190227 剔除自己简称
		String sql="select count(1) from bill_AgentUnitInfo where agentShortNm=:agentShortNm and buid!=:buid ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agentShortNm", agentUnit.getAgentShortNm());
		map.put("buid", agentUnit.getBuid());
		if(agentUnit.getUnno()!= null && StringUtil.isNotEmpty(agentUnit.getUnno())) {
			sql += " and unno != :unno and unno != :unno2";
			map.put("unno", agentUnit.getUnno());
			if("1".equals(agentUnit.getUnno())||"2".equals(agentUnit.getUnno())||"3".equals(agentUnit.getUnno())
			||"4".equals(agentUnit.getUnno())||"5".equals(agentUnit.getUnno())||"6".equals(agentUnit.getUnno())
			||"7".equals(agentUnit.getUnno())||"8".equals(agentUnit.getUnno())||"9".equals(agentUnit.getUnno())
			||"A".equals(agentUnit.getUnno())||"0".equals(agentUnit.getUnno())) {
				map.put("unno2", formatUnno(agentUnit.getUnno()));
			}else{
				map.put("unno2", formatUnno2(agentUnit.getUnno()));
			}
		}
		Integer count=unitDao.querysqlCounts2(sql, map);
		return count;
	}
	@Override
	public List<Map<String, String>> exportAgentsAndSales(AgentUnitBean agentUnit, UserBean userSession) {
		// @author:lxg-20191231 代理商资料修改-一级代理资料归属修改导出只查当前登录机构所拥有的一代
		String sql = "select ag.unno,ag.agentname, ae.salename, uo.upper_unit, "+
					 "(select u.un_name from sys_unit u where u.unno = uo.upper_unit) upperName,uo.create_date  "+
					 "from bill_agentunitinfo  ag,  sys_unit uo,bill_agentsalesinfo ae "+
					 "where ag.unno = uo.unno and ag.signuserid = ae.busid and uo.un_lvl = 2 " +
					 " and ag.unno in ( select su.unno from sys_unit su where su.un_lvl=2 start with su.unno='"+userSession.getUnNo()+"' connect by prior su.unno=su.upper_unit )";
		List<Map<String, String>> list = agentSalesDao.executeSql(sql);
		//添加导出记录
		saveExportLog(4,list.size(),userSession.getUserID());
		return list;
	}
	
	
	@Override
	public Map<String, Object> queryPurse(UserBean user) {
		if (user == null || StringUtil.isEmpty(user.getUnNo()) ) {
			throw new RuntimeException("查询可提现金额失败");
		}
//		if(user.getUnitLvl()!=1){
//			throw new RuntimeException("查询可提现金额失败");
//		}
		Map<String , String> param=new HashMap<String, String>();
		param.put("unno", user.getUnNo());
//		param.put("unno", "000002");
		String result=HttpXmlClient.post(admAppIp+"/AdmApp/proxyPurse/ProxyPurse_queryPurse.action",param );
		@SuppressWarnings("unchecked")
		Map<String, Object> map= com.alibaba.fastjson.JSONObject.toJavaObject( 
				com.alibaba.fastjson.JSONObject.parseObject(result), Map.class);
		if (map.get("status") == null) {
			throw new RuntimeException("查询可提现金额失败");
		}
		return map;
	}

    @Override
    public Map<String, Object> queryPurseRc(UserBean user) {
        if (user == null || StringUtil.isEmpty(user.getUnNo()) ) {
            throw new RuntimeException("查询可提现金额失败");
        }
//		if(user.getUnitLvl()!=1){
//			throw new RuntimeException("查询可提现金额失败");
//		}
        Map<String , String> param=new HashMap<String, String>();
        param.put("unno", user.getUnNo());
//		param.put("unno", "000002");
        String result=HttpXmlClient.post(admAppIp+"/AdmApp/proxyPurse/ProxyPurse_queryPurseRc.action",param );
        @SuppressWarnings("unchecked")
        Map<String, Object> map= com.alibaba.fastjson.JSONObject.toJavaObject(
                com.alibaba.fastjson.JSONObject.parseObject(result), Map.class);
        if (map.get("status") == null) {
            throw new RuntimeException("查询可提现金额失败");
        }
        return map;
    }
	@Override
	public Map<String, Object> takeCurAmt(UserBean user,String amount) {
		if (user == null || StringUtil.isEmpty(user.getUnNo())) {
			throw new RuntimeException("申请提现失败");
		}
//		if(user.getUnitLvl()!=1){
//			throw new RuntimeException("申请提现失败");
//		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("unno", user.getUnNo());
//		param.put("unno", "000002");
		param.put("curAmt", amount);
		String result = HttpXmlClient.post(admAppIp + "/AdmApp/proxyPurse/ProxyPurse_takeCurAmt.action", param);
		@SuppressWarnings("unchecked")
		Map<String, Object> map= com.alibaba.fastjson.JSONObject.toJavaObject(
				com.alibaba.fastjson.JSONObject.parseObject(result), Map.class);

		if (map.get("status") != null && !"0".equals(map.get("status"))) {
			if(map.get("message")!=null && !map.get("message").toString().isEmpty()){
				throw new RuntimeException(map.get("message").toString());
			}
			throw new RuntimeException("申请提现失败");
		}

		return map;
	}
	@Override
	public Map<String, Object> takeCurAmtRc(UserBean user,String amount) {
		if (user == null || StringUtil.isEmpty(user.getUnNo())) {
			throw new RuntimeException("申请提现失败");
		}
//		if(user.getUnitLvl()!=1){
//			throw new RuntimeException("申请提现失败");
//		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("unno", user.getUnNo());
//		param.put("unno", "000002");
		param.put("curAmt", amount);
		String result = HttpXmlClient.post(admAppIp + "/AdmApp/proxyPurse/ProxyPurse_takeCurAmtRc.action", param);
		@SuppressWarnings("unchecked")
		Map<String, Object> map= com.alibaba.fastjson.JSONObject.toJavaObject(
				com.alibaba.fastjson.JSONObject.parseObject(result), Map.class);

		if (map.get("status") != null && !"0".equals(map.get("status"))) {
			if(map.get("message")!=null && !map.get("message").toString().isEmpty()){
				throw new RuntimeException(map.get("message").toString());
			}
			throw new RuntimeException("申请提现失败");
		}

		return map;
	}
	@Override
	public DataGridBean queryCash(UserBean user,AgentUnitBean agentUnit) {
		if (user == null || StringUtil.isEmpty(user.getUnNo())) {
			throw new RuntimeException("查询提现记录失败");
		}
//		if(user.getUnitLvl()!=1){
//			throw new RuntimeException("查询提现记录失败");
//		}

		Map<String, String> param = new HashMap<String, String>();
		param.put("unno", user.getUnNo());
//		param.put("unno", "000002");
		param.put("cashDay", agentUnit.getCashDay());
		param.put("cashDay1", agentUnit.getCashDay1());
		param.put("page", agentUnit.getPage()+"");
		param.put("size", agentUnit.getRows()+"");
		String result = HttpXmlClient.post(admAppIp + "/AdmApp/proxyPurse/ProxyPurse_queryCash.action", param);

		DataGridBean dataGridBean =new DataGridBean();

		com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(result);
		dataGridBean.setRows(com.alibaba.fastjson.JSONArray
				.parseArray(json.getString("cashList")));
		dataGridBean.setTotal(json.getLong("total"));

		return dataGridBean;
	}
    @Override
    public DataGridBean queryCashRc(UserBean user,AgentUnitBean agentUnit) {
        if (user == null || StringUtil.isEmpty(user.getUnNo())) {
            throw new RuntimeException("查询提现记录失败");
        }
//		if(user.getUnitLvl()!=1){
//			throw new RuntimeException("查询提现记录失败");
//		}

        Map<String, String> param = new HashMap<String, String>();
        param.put("unno", user.getUnNo());
//		param.put("unno", "000002");
        param.put("cashDay", agentUnit.getCashDay());
        param.put("cashDay1", agentUnit.getCashDay1());
        param.put("page", agentUnit.getPage()+"");
        param.put("size", agentUnit.getRows()+"");
        String result = HttpXmlClient.post(admAppIp + "/AdmApp/proxyPurse/ProxyPurse_queryCashRc.action", param);

        DataGridBean dataGridBean =new DataGridBean();

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(result);
        dataGridBean.setRows(com.alibaba.fastjson.JSONArray
                .parseArray(json.getString("cashList")));
        dataGridBean.setTotal(json.getLong("total"));

        return dataGridBean;
    }
	@Override
	public DataGridBean queryAdjtxn(UserBean user, AgentUnitBean agentUnit) {
		if (user == null || StringUtil.isEmpty(user.getUnNo())) {
			throw new RuntimeException("查询提现记录失败");
		}
//		if(user.getUnitLvl()!=1){
//			throw new RuntimeException("查询提现记录失败");
//		}

		Map<String, String> param = new HashMap<String, String>();
		param.put("unno", user.getUnNo());
//		param.put("unno", "000002");
		param.put("settleday", agentUnit.getCashDay());
		param.put("settleday1", agentUnit.getCashDay1());
		param.put("page", agentUnit.getPage()+"");
		param.put("size", agentUnit.getRows()+"");
		String result = HttpXmlClient.post(admAppIp + "/AdmApp/proxyPurse/ProxyPurse_queryAdjtxn.action", param);

		DataGridBean dataGridBean =new DataGridBean();

		com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(result);
		dataGridBean.setRows(com.alibaba.fastjson.JSONArray
				.parseArray(json.getString("adjList")));
		dataGridBean.setTotal(json.getLong("total"));

		return dataGridBean;
	}
    @Override
    public DataGridBean queryAdjtxnRc(UserBean user, AgentUnitBean agentUnit) {
        if (user == null || StringUtil.isEmpty(user.getUnNo())) {
            throw new RuntimeException("查询提现记录失败");
        }
//		if(user.getUnitLvl()!=1){
//			throw new RuntimeException("查询提现记录失败");
//		}

        Map<String, String> param = new HashMap<String, String>();
        param.put("unno", user.getUnNo());
//		param.put("unno", "000002");
        param.put("settleday", agentUnit.getCashDay());
        param.put("settleday1", agentUnit.getCashDay1());
        param.put("page", agentUnit.getPage()+"");
        param.put("size", agentUnit.getRows()+"");
        String result = HttpXmlClient.post(admAppIp + "/AdmApp/proxyPurse/ProxyPurse_queryAdjtxnRc.action", param);

        DataGridBean dataGridBean =new DataGridBean();

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(result);
        dataGridBean.setRows(com.alibaba.fastjson.JSONArray
                .parseArray(json.getString("adjList")));
        dataGridBean.setTotal(json.getLong("total"));

        return dataGridBean;
    }
	@Override
	public DataGridBean queryPusreFrozenRecord(UserBean user, AgentUnitBean agentUnit) {
		if (user == null || StringUtil.isEmpty(user.getUnNo())) {
			throw new RuntimeException("查询提现记录失败");
		}
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("unno", user.getUnNo());
		param.put("mid", agentUnit.getAgentMid()==null?"":agentUnit.getAgentMid());
		param.put("status", agentUnit.getStatus());
		
		param.put("startDay", agentUnit.getCashDay()==null?"":agentUnit.getCashDay());
		param.put("endDay", agentUnit.getCashDay1()==null?"":agentUnit.getCashDay1());
		param.put("page", agentUnit.getPage()+"");
		param.put("pageRows", agentUnit.getRows()+"");
		String result = HttpXmlClient.post(admAppIp + "/AdmApp/proxyPurse/ProxyPurse_BDqueryPusreFrozenRecord.action", param);
		
		DataGridBean dataGridBean =new DataGridBean();
		
		com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(result);
		dataGridBean.setRows(com.alibaba.fastjson.JSONArray
				.parseArray(json.getString("data")));
		dataGridBean.setTotal(json.getLong("total"));
		
		return dataGridBean;
	}
	/**
	 * 查询推送综合失败分销代理
	 */
	@Override
	public DataGridBean queryAgentDlUnitList3() { 
		DataGridBean dataGridBean = new DataGridBean();
		Map<String, Object>param = new HashMap<String, Object>();
		String sql = "from AgentDLUnitModel where status = '3' ";
		String sqlCount = "select count(*) from AgentDLUnitModel a where a.status = '3' ";
		List<AgentDLUnitModel> list = agentDlUnitDao.queryObjectsByHqlList(sql, param);
		long count = agentDlUnitDao.queryCounts(sqlCount,param);
		dataGridBean.setRows(list);
		dataGridBean.setTotal(count);
		return dataGridBean;
	}
	@Override
	public boolean updatePublishToZH(AgentUnitBean agentUnit, UserBean userSession) {
		boolean result = true;
		String agentMids = agentUnit.getAgentMid();
		if (null!=agentMids&&agentMids.length()>0) {
			String [] agentMid = agentMids.split(",");
			for (int i = 0; i < agentMid.length; i++) {
//				AgentDLUnitModel agentDLUnitModel = agentDlUnitDao.getObjectByID(AgentDLUnitModel.class,agentMid[i] );
				AgentDLUnitModel agentDLUnitModel = agentDlUnitDao.queryObjectByHql("from AgentDLUnitModel t where t.agentMid=?", new Object[]{agentMid[i]});

				Map<String, Object>paramMap = new HashMap<String, Object>();
				paramMap.put("unno", agentDLUnitModel.getUnno());
				MerchantInfoModel mer = merchantInfoDao.queryObjectByHql("from MerchantInfoModel t where t.mid=?", new Object[]{agentDLUnitModel.getAgentMid()});
//				AgentUnitModel agentUnitModel = new AgentUnitModel();
//				List<AgentUnitModel> list = agentUnitDao.queryObjectsByHqlList("from AgentUnitModel where unno = :unno", paramMap);1
//				if (list!=null&&list.size()>0) {
//					agentUnitModel = list.get(0);
//				}else {
//					return false;
//				}
				UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, agentDLUnitModel.getUnno());
				try{
					AgentUnitInfo info = new AgentUnitInfo();
					XMLGregorianCalendar calendar =ClassToJavaBeanUtil.convertToXMLGregorianCalendar(new Date());
					info.setUnno(agentDLUnitModel.getUnno());
					info.setUpperUnit1(unitModel.getParent().getUnNo());
					info.setUpperUnit3(unitModel.getUpperUnit3());
					info.setAccType(mer.getAccType());//?
					info.setPayBankId(mer.getPayBankId());
					info.setAgentName(unitModel.getUnitName());
					info.setAmountConfirmDate(calendar);
					info.setAreaType("1");
					info.setBankAccName(mer.getBankAccName());
					info.setBankAccNo(mer.getBankAccNo());
					info.setBankArea("北京");
					info.setBankBranch(mer.getBankBranch());
					info.setBankOpenAcc("1");//企业银行开户许可证号
					info.setBankType("2");
					info.setBno("1");
					info.setBuid(1);
					info.setBaddr(mer.getBaddr());
					info.setLegalNum(mer.getLegalNum());
					info.setLegalPerson(mer.getLegalPerson());
					info.setLegalType("1");
					info.setMaintainDate(calendar);
					info.setMaintainType("A");
					info.setMaintainUserId(1);
					info.setOpenDate(calendar);
					info.setRegistryNo("1");
					info.setRno("1");
					info.setSignUserId(1);
					info.setIsM35("7");
					info.setContactPhone(mer.getHybPhone());
					info.setCronym(unitModel.getUnNo()+"99");
					
					info.setMid(mer.getMid());
					info.setParCompany(unitModel.getUpperUnit3());
					info.setUpperUnit2(unitModel.getUpperUnit2());
					result = gsClient.saveAgentInfo(info,"hrtREX1234.Java");
					log.info("三级分销-机构-推送综合结果"+result);
					if (result==true) {
						agentDLUnitModel.setStatus(1);
						agentDlUnitDao.updateObject(agentDLUnitModel);
					}
				}catch (Exception e) {
					log.info("三级分销-机构-推送综合异常"+e);
					throw new IllegalAccessError("三级分销-机构-推送综合异常"+e);
				}
			}
		}
		return result;
	}
	@Override
	public JSONObject queryHrtUnnoCost(AgentUnitBean agentUnit, UserBean userSession) {
		if (agentUnit.getBuid() == null || "".equals(agentUnit.getUnno())) {
			throw new RuntimeException("查询机构成本，参数有误");
		}
		String hql = "from HrtUnnoCostModel h where h.buid = ? ";
		List<HrtUnnoCostModel> queryList = hrtUnnoCostDao.queryObjectsByHqlList(hql, new Integer[]{agentUnit.getBuid()});
		JSONObject rs = new JSONObject();
		StringBuilder key = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		for(HrtUnnoCostModel model : queryList){
			key.append("K").append(model.getMachineType()).append(model.getTxnType()).append(model.getTxnDetail());
			if (StringUtil.isNotEmpty(model.getDebitRate()+"")) {
				temp.append(key).append("A");
				rs.put(temp.toString(), model.getDebitRate());
				temp.delete(0, temp.length());
			}
			if (StringUtil.isNotEmpty(model.getDebitFeeamt()+"")) {
				temp.append(key).append("B");
				rs.put(temp.toString(), model.getDebitFeeamt());
				temp.delete(0, temp.length());
			}
			if (StringUtil.isNotEmpty(model.getCreditRate()+"")) {
				temp.append(key).append("C");
				rs.put(temp.toString(), model.getCreditRate());
				temp.delete(0, temp.length());
			}
			if (StringUtil.isNotEmpty(model.getCashCost()+"")) {
				temp.append(key).append("D");
				rs.put(temp.toString(), model.getCashCost());
				temp.delete(0, temp.length());
			}
			if (StringUtil.isNotEmpty(model.getCashRate()+"")) {
				temp.append(key).append("E");
				rs.put(temp.toString(), model.getCashRate());
				temp.delete(0, temp.length());
			}
			key.replace(0, 1, "H");
			rs.put(key.toString(), model.getHucid());
			key.delete(0, key.length());
		}
		return rs;
	}
	@Override
	public JsonBean queryHrtUnnoCostSingleRebate(AgentUnitBean agentUnit, UserBean userSession) {
		// @author:lxg-20200507 将是否展示某些费率配置信息添加进去
		JsonBean jsonBean = new JsonBean();
		if (agentUnit.getBuid() == null || "".equals(agentUnit.getUnno())) {
			throw new RuntimeException("查询机构成本，参数有误");
		}
		String sql = "select h.*,b.subtype from hrt_unno_cost h,bill_purconfigure b where h.txn_detail = b.valueinteger " +
				" and b.type = 3 and h.buid = :buid and h.txn_Detail >=20 ";
		Map map=new HashMap(16);
		map.put("buid",agentUnit.getBuid());
		List<Map<String,Object>> list = hrtUnnoCostDao.queryObjectsBySqlListMap2(sql, map);
		jsonBean.setList(list);
		jsonBean.setSuccess(true);
		return jsonBean;
	}

	@Override
	public DataGridBean queryUnnoCostMult(AgentUnitBean agentUnit, UserBean user) {
		String sql = " select UNNO from sys_unit start with unno =:unno and status = 1 connect by NOCYCLE prior unno = upper_unit";
		
		String hql = "from HrtUnnoCostModel h where h.unno in ";
		List<HrtUnnoCostModel> queryList = hrtUnnoCostDao.queryObjectsByHqlList(hql, new Integer[]{agentUnit.getBuid()});
		JSONObject rs = new JSONObject();
		StringBuilder key = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		for(HrtUnnoCostModel model : queryList){
			key.append("K").append(model.getMachineType()).append(model.getTxnType()).append(model.getTxnDetail());
			if (StringUtil.isNotEmpty(model.getDebitRate()+"")) {
				temp.append(key).append("A");
				rs.put(temp.toString(), model.getDebitRate());
				temp.delete(0, temp.length());
			}
			if (StringUtil.isNotEmpty(model.getDebitFeeamt()+"")) {
				temp.append(key).append("B");
				rs.put(temp.toString(), model.getDebitFeeamt());
				temp.delete(0, temp.length());
			}
			if (StringUtil.isNotEmpty(model.getCreditRate()+"")) {
				temp.append(key).append("C");
				rs.put(temp.toString(), model.getCreditRate());
				temp.delete(0, temp.length());
			}
			if (StringUtil.isNotEmpty(model.getCashCost()+"")) {
				temp.append(key).append("D");
				rs.put(temp.toString(), model.getCashCost());
				temp.delete(0, temp.length());
			}
			if (StringUtil.isNotEmpty(model.getCashRate()+"")) {
				temp.append(key).append("E");
				rs.put(temp.toString(), model.getCashRate());
				temp.delete(0, temp.length());
			}
			key.replace(0, 1, "H");
			rs.put(key.toString(), model.getHucid());
			key.delete(0, key.length());
		}
		return null;
	}
	@Override
	public DataGridBean listRebateRate(AgentUnitBean agentUnit, UserBean userSession) {
		DataGridBean dgb = new DataGridBean();
		String sql  = "select * from bill_PurConfigure where status = 1 and valuestring='rate' and type=3";
		if(agentUnit!=null && StringUtils.isNotEmpty(agentUnit.getStatus()) && "plus".equals(agentUnit.getStatus())) {
			//sql += " and valueinteger not in (20,21)";
			sql += " and prod = 'plus'";
		}
		//添加syt活动20200319
		if(agentUnit!=null && StringUtils.isNotEmpty(agentUnit.getStatus()) && "syt".equals(agentUnit.getStatus())) {
			sql += " and prod = 'syt'";
		}
		sql += " order by valueinteger asc";
		List<Map<String,String>> list = hrtUnnoCostDao.queryObjectsBySqlListMap(sql, null);
		dgb.setRows(list);
		return dgb;
	}
	@Override
	public DataGridBean listRebateRateForUnno(AgentUnitBean agentUnit, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		String sql = "select unno,un_name from sys_unit where un_lvl in (1,2) ";
		if("110000".equals(user.getUnNo())) {
		}else {
			sql += " start with unno='"+user.getUnNo()+"' connect by prior unno = upper_unit ";
		}
		List<Map<String,String>> list = hrtUnnoCostDao.queryObjectsBySqlListMap(sql, null);
		dgb.setRows(list);
		return dgb;
	}
	@Override
	public DataGridBean queryRebateRateList(AgentUnitBean agentUnit, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		String sql  = "select a.*,b.un_name from hrt_unno_cost a,sys_unit b where a.unno=b.unno and a.status = 1 and a.txn_detail>=20 ";
		if("110000".equals(user.getUnNo())) {//总公司权限
		}else {//运营中心
			sql += " and unno in (select unno from sys_unit where un_lvl in (1,2) start with unno='"+user.getUnNo()+"' connect by prior unno = upper_unit)";
		}
		if(agentUnit.getUnno()!=null&&!"".equals(agentUnit.getUnno())) {
			sql += " and unno = '"+agentUnit.getUnno()+"'";
		}
		String sqlCount = "select count(1) from ("+sql+")";
		List<Map<String,String>> list = hrtUnnoCostDao.queryObjectsBySqlListMap(sql, null);
		Integer counts = hrtUnnoCostDao.querysqlCounts2(sqlCount, null);
		dgb.setRows(list);
		dgb.setTotal(counts);
		return dgb;
	}
	@Override
	public DataGridBean listRebateRateForW(AgentUnitBean agentUnit, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		String sql  = "select a.*,b.un_name from hrt_unno_cost a,sys_unit b where a.unno=b.unno and a.status != 1 and a.txn_detail>=20 ";
		if("110000".equals(user.getUnNo())) {//总公司权限
		}else {//运营中心
			sql += " and a.unno in (select unno from sys_unit where un_lvl in (1,2) start with unno='"+user.getUnNo()+"' connect by prior unno = upper_unit)";
		}
		if(agentUnit.getUnno()!=null&&!"".equals(agentUnit.getUnno())) {
			sql += " and a.unno = '"+agentUnit.getUnno()+"'";
		}
		if(StringUtils.isNotEmpty(agentUnit.getStatus())){
            sql += " and a.status = '"+agentUnit.getStatus()+"'";
        }
        if(null!=agentUnit.getAdate() || null!=agentUnit.getZdate()){
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            if(null!=agentUnit.getAdate() && null!=agentUnit.getZdate()) {
                sql += " and a.lmdate between to_date('" + sf.format(agentUnit.getAdate()) + "','yyyyMMdd') and to_date('" + sf.format(agentUnit.getZdate()) + "','yyyyMMdd')+1 ";
            }else if(null==agentUnit.getAdate() && null!=agentUnit.getZdate()){
                sql += " and a.lmdate < to_date('" + sf.format(agentUnit.getZdate()) + "','yyyyMMdd')+1 ";
            }else if(null!=agentUnit.getAdate() && null==agentUnit.getZdate()){
                sql += " and a.lmdate >= to_date('" + sf.format(agentUnit.getAdate()) + "','yyyyMMdd') ";
            }
        }
		String sqlCount = "select count(1) from ("+sql+")";
//		List<Map<String,String>> list = hrtUnnoCostDao.queryObjectsBySqlListMap(sql, null);
		List<Map<String, Object>> list = hrtUnnoCostDao.queryObjectsBySqlList2(sql,null,agentUnit.getPage(),agentUnit.getRows());
		Integer counts = hrtUnnoCostDao.querysqlCounts2(sqlCount, null);
		dgb.setRows(list);
		dgb.setTotal(counts);
		return dgb;
	}

	@Override
	public String saveImportBatchRebateRate(String xlsfile, String name, UserBean user) {
		Map resutMap = new HashMap();
		int sumCount = 0;
		int errCount = 0;
		File filename = new File(xlsfile);
		HSSFWorkbook workbook = null;
		Map<String,String> uniqueUnno = new HashMap<String,String>();
		List<HrtUnnoCostModel> list = new ArrayList<HrtUnnoCostModel>();
        try {
            workbook = new HSSFWorkbook(new FileInputStream(filename));
        } catch (IOException e) {
            log.info("导入批量成本文件异常:"+e.getMessage());
        }
        HSSFSheet sheet = workbook.getSheetAt(0);
        sumCount = sheet.getPhysicalNumberOfRows()-1;
        for(int i = 1; i <= sumCount; i++){
            HrtUnnoCostModel model = new HrtUnnoCostModel();
            HSSFRow row = sheet.getRow(i);
            HSSFCell cell = row.getCell((short)0);
            if(cell == null || cell.toString().trim().equals("")){
                break;
            }else{
                // 一级代理 /运营中心机构号
                row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
                String unno = row.getCell((short)0).getStringCellValue().trim();
                model.setUnno(unno);

                // 活动类型(例如:20)
                HSSFCell cell1= row.getCell((short)1);
                if(cell1 != null && !cell1.toString().trim().equals("")){
                    row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
                    String txnDetail = row.getCell((short)1).getStringCellValue().trim();
                    model.setTxnDetail(Integer.parseInt(txnDetail));
                }

                // 借记卡成本(%) DEBIT_RATE
				HSSFCell cell2= row.getCell((short)2);
				if(cell2!=null && !cell2.toString().trim().equals("")) {
					row.getCell((short) 2).setCellType(Cell.CELL_TYPE_STRING);
					String debitRate = row.getCell((short) 2).getStringCellValue().trim();
					model.setDebitRate(new BigDecimal(debitRate + "").setScale(6, BigDecimal.ROUND_HALF_UP));
				}

                // 借记卡封顶值
                //  提现转账手续费=提现成本
                //  其它对应关系如你箭头所示
                //  借记卡封顶值 目前还没有与其对应的字段，主要是为以后留存空间用的
                //  比如新增了活动类型30 有借记卡封顶值成本了，那么这个批量新增可以无缝对接了
                HSSFCell cell3= row.getCell((short)3);
                if(cell3!=null && !cell3.toString().trim().equals("")) {
					row.getCell((short) 3).setCellType(Cell.CELL_TYPE_STRING);
                	if(!cell3.getStringCellValue().isEmpty()){
						String debitFeeamt = row.getCell((short) 3).getStringCellValue().trim();
						model.setDebitFeeamt(new BigDecimal(debitFeeamt+"").setScale(6, BigDecimal.ROUND_HALF_UP));
					}
                }
                // 贷记卡成本(%)
                row.getCell((short)4).setCellType(Cell.CELL_TYPE_STRING);
                String creditRate = row.getCell((short)4).getStringCellValue().trim();
                model.setCreditRate(new BigDecimal(creditRate+"").setScale(6, BigDecimal.ROUND_HALF_UP));

                // 提现转账手续费(%)
                row.getCell((short)5).setCellType(Cell.CELL_TYPE_STRING);
                String cashRate = row.getCell((short)5).getStringCellValue().trim();
//					model.setCashRate(new BigDecimal(cashRate).setScale(6, BigDecimal.ROUND_HALF_UP));
                model.setCashCost(new BigDecimal(cashRate+"").setScale(6, BigDecimal.ROUND_HALF_UP));

				// @author:lxg-20190910 扫码费率拆分
				// 微信1000以上0.38费率(%)
				row.getCell((short)6).setCellType(Cell.CELL_TYPE_STRING);
				String wxUpRate = row.getCell((short)6).getStringCellValue().trim();
				model.setWxUpRate(new BigDecimal(wxUpRate+"").setScale(6, BigDecimal.ROUND_HALF_UP));

				// 微信1000以上0.38转账费
				row.getCell((short)7).setCellType(Cell.CELL_TYPE_STRING);
				String wxUpCash = row.getCell((short)7).getStringCellValue().trim();
				model.setWxUpCash(new BigDecimal(wxUpCash+"").setScale(6, BigDecimal.ROUND_HALF_UP));

				// 微信1000以上0.45费率(%)
				row.getCell((short)8).setCellType(Cell.CELL_TYPE_STRING);
				String wxUpRate1 = row.getCell((short)8).getStringCellValue().trim();
				model.setWxUpRate1(new BigDecimal(wxUpRate1+"").setScale(6, BigDecimal.ROUND_HALF_UP));

				// 微信1000以上0.45转账费
				row.getCell((short)9).setCellType(Cell.CELL_TYPE_STRING);
				String wxUpCash1 = row.getCell((short)9).getStringCellValue().trim();
				model.setWxUpCash1(new BigDecimal(wxUpCash1+"").setScale(6, BigDecimal.ROUND_HALF_UP));

				// 支付宝费率(%)
				row.getCell((short)10).setCellType(Cell.CELL_TYPE_STRING);
				String zfbRate = row.getCell((short)10).getStringCellValue().trim();
				model.setZfbRate(new BigDecimal(zfbRate+"").setScale(6, BigDecimal.ROUND_HALF_UP));

				// 支付宝转账费
				row.getCell((short)11).setCellType(Cell.CELL_TYPE_STRING);
				String zfbCash = row.getCell((short)11).getStringCellValue().trim();
				model.setZfbCash(new BigDecimal(zfbCash+"").setScale(6, BigDecimal.ROUND_HALF_UP));

				// 二维码费率(%)
				row.getCell((short)12).setCellType(Cell.CELL_TYPE_STRING);
				String ewmRate = row.getCell((short)12).getStringCellValue().trim();
				model.setEwmRate(new BigDecimal(ewmRate+"").setScale(6, BigDecimal.ROUND_HALF_UP));

				// 二维码转账费
				row.getCell((short)13).setCellType(Cell.CELL_TYPE_STRING);
				String ewmCash = row.getCell((short)13).getStringCellValue().trim();
				model.setEwmCash(new BigDecimal(ewmCash+"").setScale(6, BigDecimal.ROUND_HALF_UP));

				// 云闪付费率(%)
				HSSFCell cell14 = row.getCell((short)14);
				if(cell14!=null && !cell14.toString().trim().equals("")) {
					row.getCell((short) 14).setCellType(Cell.CELL_TYPE_STRING);
					String ysfRate = row.getCell((short) 14).getStringCellValue().trim();
					model.setYsfRate(new BigDecimal(ysfRate + "").setScale(6, BigDecimal.ROUND_HALF_UP));
				}

				HSSFCell cell15 = row.getCell((short)15);
				if(cell15!=null && !cell15.toString().trim().equals("")){
					row.getCell((short)15).setCellType(Cell.CELL_TYPE_STRING);
					String hbRate = row.getCell((short)15).getStringCellValue().trim();
					model.setHbRate(new BigDecimal(hbRate+"").setScale(6, BigDecimal.ROUND_HALF_UP));
				}
				HSSFCell cell16 = row.getCell((short)16);
				if(cell16!=null && !cell16.toString().trim().equals("")){
					row.getCell((short)16).setCellType(Cell.CELL_TYPE_STRING);
					String hbCash = row.getCell((short)16).getStringCellValue().trim();
					model.setHbCash(new BigDecimal(hbCash+"").setScale(6, BigDecimal.ROUND_HALF_UP));
				}

                AgentUnitModel agentUnitModel = agentUnitDao.queryObjectByHql("from AgentUnitModel where unno = ?", new Object[] {unno});
                if(agentUnitModel!=null){
                    model.setBuid(agentUnitModel.getBuid());
                }
                model.setTxnType(1);
                model.setMachineType(1);
                model.setStatus(0);
                model.setFlag(0);
                model.setOperateType(1);
                model.setCdate(new Date());
                model.setCby(user.getLoginName());
                model.setLmby(user.getLoginName());
                model.setLmDate(new Date());

                String check = checkHrtUnnoCost(model,i,uniqueUnno);
                if(StringUtils.isEmpty(check)) {
                    list.add(model);
//                    hrtUnnoCostDao.saveObject(model);
                }else{
                    return check;
                }

				// TODO @author:lxg-20200518 成本上限校验
				String tip = validateLimitCost(model);
				if(StringUtils.isNotEmpty(tip)){
					return tip;
				}
            }
        }
        for(HrtUnnoCostModel hrt:list){
            HrtUnnoCostNModel hrtN = new HrtUnnoCostNModel();
            BeanUtils.copyProperties(hrt,hrtN);
            hrtUnnoCostDao.saveObject(hrt);
            // 下个月生效表中,新增的数据综合不会扫,默认设置为已扫
			hrtN.setFlag(1);
            hrtUnnoCostNDao.saveObject(hrtN);
        }
		return "";
	}

	/**
	 * 成本校验
	 * @param model
	 * @param line
	 * @return
	 */
	public String checkHrtUnnoCost(HrtUnnoCostModel model,int line,Map map){
	    int errLine = line+1;
	    // @author:xuegangliu 批量活动成本导入校验
		StringBuffer sb = new StringBuffer();
		if(map.containsKey(model.getUnno()+"|"+model.getTxnDetail())){
            sb.append("第"+errLine+"行成本在模板中该机构该活动存在多条.");
            return sb.toString();
        }else{
		    map.put(model.getUnno()+"|"+model.getTxnDetail(),"1");
        }

//		（1）一代机构（包含中心机构）已有相应活动成本，提示某某行成本已存在不允许创建
//		（2）一代机构（包含中心机构）同一活动类型，不允许上传多条（大于1条），若违规上传，提示某某行重复上传
		Integer i = hrtUnnoCostDao.querysqlCounts2("select count(1) from hrt_unno_cost where unno='"+
				model.getUnno()+"' and txn_detail='"+model.getTxnDetail()+"'", null);
		if(i>0) {
			sb.append("第"+errLine+"行成本已存在不允许创建.");
            return sb.toString();
		}
//		（3）针对活动20/21借记卡封顶值必须为空
		if(null!=model.getTxnDetail() && (20==model.getTxnDetail() || 21==model.getTxnDetail())){
			if(null!=model.getDebitFeeamt()){
				sb.append("第"+errLine+"行活动20/21借记卡封顶值必须为空.");
                return sb.toString();
			}
		}
//		（4）若（1）-（3）任何一条出现违规上传情况，整个上传批次全部失败，直至整个上传批次完全正确 提示“导入成功”
		UnitModel unitModel = unitDao.queryObjectByHql("from UnitModel where unNo=?", new Object[] {model.getUnno()});
		if(unitModel==null||(unitModel.getUnLvl()!=2&&unitModel.getUnLvl()!=1)) {
			sb.append("第"+errLine+"行机构无法设置活动成本");
            return sb.toString();
		}

        String yunyingUnno = getYunYingUnnoByUnno(model.getUnno());
        Map hrtCost = getYunYingHrtUnnoCost(yunyingUnno,1);
        //String prefix = t.getMachineType()+"|"+t.getTxnType()+"|"+t.getTxnDetail()+"|";cashCost cashRate debitRate debitFeeamt creditRate

        String keyHrt = "1|1|"+model.getTxnDetail()+"|";
        if(hrtCost.containsKey(keyHrt+"creditRate") && null!=model.getCreditRate()){
            Double yidai = Double.parseDouble(model.getCreditRate()+"");
            Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"creditRate")+"");
            if(yidai<yunYing){
                sb.append("第"+errLine+"行扫码1000以下（终端0.38）费率(%)低于运营中心成本");
                return sb.toString();
            }
        }
        if(hrtCost.containsKey(keyHrt+"debitFeeamt") && null!=model.getDebitFeeamt()){
            Double yidai = Double.parseDouble(model.getDebitFeeamt()+"");
            Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"debitFeeamt")+"");
            if(yidai<yunYing){
                sb.append("第"+errLine+"行刷卡提现转账手续费低于运营中心成本");
                return sb.toString();
            }
        }
        if(hrtCost.containsKey(keyHrt+"cashCost") && null!=model.getCashCost()){
            Double yidai = Double.parseDouble(model.getCashCost()+"");
            Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"cashCost")+"");
            if(yidai<yunYing){
                sb.append("第"+errLine+"行扫码1000以下（终端0.38）转账费低于运营中心成本");
                return sb.toString();
            }
        }
        if(hrtCost.containsKey(keyHrt+"debitRate") && null!=model.getDebitRate()){
            Double yidai = Double.parseDouble(model.getDebitRate()+"");
            Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"debitRate")+"");
            if(yidai<yunYing){
                sb.append("第"+errLine+"行刷卡成本低于运营中心成本");
                return sb.toString();
            }
        }

        // @author:lxg-20190911 扫码费率拆分
		if(hrtCost.containsKey(keyHrt+"wxUpRate") && null!=model.getWxUpRate()){
			Double yidai = Double.parseDouble(model.getWxUpRate()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"wxUpRate")+"");
			if(yidai<yunYing){
				sb.append("第"+errLine+"行扫码1000以上（终端0.38）费率(%)低于运营中心成本");
				return sb.toString();
			}
		}
		if(hrtCost.containsKey(keyHrt+"wxUpCash") && null!=model.getWxUpCash()){
			Double yidai = Double.parseDouble(model.getWxUpCash()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"wxUpCash")+"");
			if(yidai<yunYing){
				sb.append("第"+errLine+"行扫码1000以上（终端0.38）转账费低于运营中心成本");
				return sb.toString();
			}
		}
		if(hrtCost.containsKey(keyHrt+"wxUpRate1") && null!=model.getWxUpRate1()){
			Double yidai = Double.parseDouble(model.getWxUpRate1()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"wxUpRate1")+"");
			if(yidai<yunYing){
				sb.append("第"+errLine+"行扫码1000以上（终端0.45）费率(%)低于运营中心成本");
				return sb.toString();
			}
		}
		if(hrtCost.containsKey(keyHrt+"wxUpCash1") && null!=model.getWxUpCash1()){
			Double yidai = Double.parseDouble(model.getWxUpCash1()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"wxUpCash1")+"");
			if(yidai<yunYing){
				sb.append("第"+errLine+"行扫码1000以上（终端0.45）转账费低于运营中心成本");
				return sb.toString();
			}
		}
		if(hrtCost.containsKey(keyHrt+"zfbRate") && null!=model.getZfbRate()){
			Double yidai = Double.parseDouble(model.getZfbRate()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"zfbRate")+"");
			if(yidai<yunYing){
				sb.append("第"+errLine+"行扫码1000以下（终端0.45）费率(%)低于运营中心成本");
				return sb.toString();
			}
		}
		if(hrtCost.containsKey(keyHrt+"zfbCash") && null!=model.getZfbCash()){
			Double yidai = Double.parseDouble(model.getZfbCash()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"zfbCash")+"");
			if(yidai<yunYing){
				sb.append("第"+errLine+"行扫码1000以下（终端0.45）转账费低于运营中心成本");
				return sb.toString();
			}
		}
		if(hrtCost.containsKey(keyHrt+"ewmRate") && null!=model.getEwmRate()){
			Double yidai = Double.parseDouble(model.getEwmRate()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"ewmRate")+"");
			if(yidai<yunYing){
				sb.append("第"+errLine+"行银联二维码费率低于运营中心成本");
				return sb.toString();
			}
		}
		if(hrtCost.containsKey(keyHrt+"ewmCash") && null!=model.getEwmCash()){
			Double yidai = Double.parseDouble(model.getEwmCash()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"ewmCash")+"");
			if(yidai<yunYing){
				sb.append("第"+errLine+"行银联二维码转账费低于运营中心成本");
				return sb.toString();
			}
		}
		if(hrtCost.containsKey(keyHrt+"ysfRate") && null!=model.getYsfRate()){
			Double yidai = Double.parseDouble(model.getYsfRate()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"ysfRate")+"");
			if(yidai<yunYing){
				sb.append("第"+errLine+"行云闪付费率账费低于运营中心成本");
				return sb.toString();
			}
		}
		if(hrtCost.containsKey(keyHrt+"hbRate") && null!=model.getHbRate()){
			Double yidai = Double.parseDouble(model.getHbRate()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"hbRate")+"");
			if(yidai<yunYing){
				sb.append("第"+errLine+"行花呗费率账费低于运营中心成本");
				return sb.toString();
			}
		}
		if(hrtCost.containsKey(keyHrt+"hbCash") && null!=model.getHbCash()){
			Double yidai = Double.parseDouble(model.getHbCash()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"hbCash")+"");
			if(yidai<yunYing){
				sb.append("第"+errLine+"行花呗转账费低于运营中心成本");
				return sb.toString();
			}
		}
		return sb.toString();
	}

	@Override
	public JsonBean addUnnoRebateRate(AgentUnitBean agentUnit, UserBean user) {
		JsonBean json = new JsonBean();
		Integer i = hrtUnnoCostDao.querysqlCounts2("select count(1) from hrt_unno_cost where unno='"+agentUnit.getUnno()+"' and txn_detail='"+agentUnit.getTxnDetail()+"'", null);
		if(i>0) {
			json.setMsg("该机构已设置本活动成本，请勿重复添加");
			json.setSuccess(false);
			return json;
		}
		UnitModel unitModel = unitDao.queryObjectByHql("from UnitModel where unNo=?", new Object[] {agentUnit.getUnno()});
		if(unitModel==null||(unitModel.getUnLvl()!=2&&unitModel.getUnLvl()!=1)) {
			json.setMsg("该机构无法设置活动成本");
			json.setSuccess(false);
			return json;
		}
		if (checkNewAddActiveHrt(agentUnit, user, json)) return json;
		AgentUnitModel agentUnitModel = agentUnitDao.queryObjectByHql("from AgentUnitModel where unno = ?", new Object[] {agentUnit.getUnno()});
		if(null==agentUnitModel){
			json.setMsg("添加失败,该机构的代理商信息不存在");
			json.setSuccess(false);
			return json;
		}
		HrtUnnoCostModel model = new HrtUnnoCostModel();
		HrtUnnoCostNModel modelN = new HrtUnnoCostNModel();
		model.setTxnType(1);
		model.setMachineType(1);
		model.setTxnDetail(agentUnit.getTxnDetail());
		model.setCreditRate(new BigDecimal(agentUnit.getRate()));
		if(agentUnit.getTxnDetail().compareTo(21)>0){
		    if(StringUtils.isNotEmpty(agentUnit.getRemarks())){
                model.setDebitRate(new BigDecimal(agentUnit.getRemarks()));
            }
		}else{
		    if(null != agentUnit.getRate()){
                model.setDebitRate(new BigDecimal(agentUnit.getRate()));
            }
        }
		model.setCashCost(agentUnit.getCashRate());
		if(StringUtils.isNotEmpty(agentUnit.getCurAmt())) {
			model.setDebitFeeamt(new BigDecimal(agentUnit.getCurAmt()));
		}
		model.setUnno(agentUnit.getUnno());
		model.setBuid(agentUnitModel.getBuid());
		model.setStatus(0);
		model.setFlag(0);
		model.setOperateType(1);
		model.setCdate(new Date());
		model.setCby(user.getLoginName());
		model.setLmby(user.getLoginName());
		model.setLmDate(new Date());

		// @author:lxg-20190910 扫码费率拆分
		model.setWxUpRate(agentUnit.getWxUpRate());
		model.setWxUpCash(agentUnit.getWxUpCash());
		model.setWxUpRate1(agentUnit.getWxUpRate1());
		model.setWxUpCash1(agentUnit.getWxUpCash1());
		model.setZfbRate(agentUnit.getZfbRate());
		model.setZfbCash(agentUnit.getZfbCash());
		model.setEwmRate(agentUnit.getEwmRate());
		model.setEwmCash(agentUnit.getEwmCash());
		model.setYsfRate(agentUnit.getYsfRate());
		model.setHbRate(agentUnit.getHbRate());
		model.setHbCash(agentUnit.getHbCash());
		// TODO @author:lxg-20200518 成本上限校验
		String tip = validateLimitCost(model);
		if(StringUtils.isNotEmpty(tip)){
			json.setMsg(tip);
			json.setSuccess(false);
			return json;
		}

		BeanUtils.copyProperties(model,modelN);
        // 下个月生效表中,新增的数据综合不会扫,默认设置为已扫
		modelN.setFlag(1);
		hrtUnnoCostDao.saveObject(model);
		hrtUnnoCostNDao.saveObject(modelN);
		json.setMsg("添加成功");
		json.setSuccess(true);
		return json;
	}

    private String getYunYingUnnoByUnno(String unno) {
        String sql = "select s.unno from  sys_unit s where s.un_lvl=1 and s.unno!=:unno " +
                " start with s.unno=:unno connect by  s.unno= prior s.upper_unit";
        Map map = new HashMap();
        map.put("unno",unno);
        List<Map<String,String>> unnoListMap = unitDao.queryObjectsBySqlListMap(sql,map);
        return unnoListMap.size()>0?unnoListMap.get(0).get("UNNO"):"";
    }

    @Override
	public JsonBean updateUnnoRebateRate(AgentUnitBean agentUnit, UserBean user) {
		JsonBean json = new JsonBean();
		HrtUnnoCostModel model = hrtUnnoCostDao.queryObjectByHql("from HrtUnnoCostModel where hucid=? ", new Object[] {agentUnit.getHucid()});
		if(model!=null) {
			if (checkNewAddActiveHrt(agentUnit, user, json)) return json;

            String hql="from HrtUnnoCostNModel where buid=? and machineType=? and txnType=? and txnDetail=?";
            HrtUnnoCostNModel modelN = hrtUnnoCostNDao.queryObjectByHql(hql,
                    new Object[]{model.getBuid(),model.getMachineType(),model.getTxnType(),model.getTxnDetail()});
            if(modelN!=null){
                modelN.setTxnDetail(agentUnit.getTxnDetail());
                modelN.setCreditRate(new BigDecimal(agentUnit.getRate()));
                if(agentUnit.getTxnDetail()>21 && StringUtils.isNotEmpty(agentUnit.getRemarks())){
                    modelN.setDebitRate(new BigDecimal(agentUnit.getRemarks()));
                }else if(agentUnit.getTxnDetail()<=21) {
                    if(agentUnit.getRate()!=null) {
                        modelN.setDebitRate(new BigDecimal(agentUnit.getRate()));
                    }
                }
                modelN.setCashCost(agentUnit.getCashRate());
                if(null!=agentUnit.getCurAmt()) {
                    modelN.setDebitFeeamt(new BigDecimal(agentUnit.getCurAmt()));
                }
                modelN.setStatus(0);
                modelN.setLmby(user.getLoginName());
                modelN.setLmDate(new Date());

                // @author:lxg-20190910 扫码费率拆分
                modelN.setWxUpRate(agentUnit.getWxUpRate());
                modelN.setWxUpCash(agentUnit.getWxUpCash());
                modelN.setWxUpRate1(agentUnit.getWxUpRate1());
                modelN.setWxUpCash1(agentUnit.getWxUpCash1());
                modelN.setZfbRate(agentUnit.getZfbRate());
                modelN.setZfbCash(agentUnit.getZfbCash());
                modelN.setEwmRate(agentUnit.getEwmRate());
                modelN.setEwmCash(agentUnit.getEwmCash());
                modelN.setYsfRate(agentUnit.getYsfRate());
                if(agentUnit.getHbRate()!=null){
                    modelN.setHbRate(agentUnit.getHbRate());
                }
                if(agentUnit.getHbCash()!=null){
                    modelN.setHbCash(agentUnit.getHbCash());
                }
                // TODO @author:lxg-20200518 成本上限校验
                HrtUnnoCostModel limitModel = new HrtUnnoCostModel();
                BeanUtils.copyProperties(modelN,limitModel);
                String tip = validateLimitCost(limitModel);
                if(StringUtils.isNotEmpty(tip)){
                    json.setMsg(tip);
                    json.setSuccess(false);
                    return json;
                }
                hrtUnnoCostNDao.updateObject(modelN);
            }

			model.setTxnDetail(agentUnit.getTxnDetail());
			model.setCreditRate(new BigDecimal(agentUnit.getRate()));
			if(agentUnit.getTxnDetail()>21 && StringUtils.isNotEmpty(agentUnit.getRemarks())){
				model.setDebitRate(new BigDecimal(agentUnit.getRemarks()));
			}else if(agentUnit.getTxnDetail()<=21) {
			    if(agentUnit.getRate()!=null) {
                    model.setDebitRate(new BigDecimal(agentUnit.getRate()));
                }
			}
			model.setCashCost(agentUnit.getCashRate());
			if(null!=agentUnit.getCurAmt()) {
				model.setDebitFeeamt(new BigDecimal(agentUnit.getCurAmt()));
			}
			model.setStatus(0);
			model.setLmby(user.getLoginName());
			model.setLmDate(new Date());

			// @author:lxg-20190910 扫码费率拆分
			model.setWxUpRate(agentUnit.getWxUpRate());
			model.setWxUpCash(agentUnit.getWxUpCash());
			model.setWxUpRate1(agentUnit.getWxUpRate1());
			model.setWxUpCash1(agentUnit.getWxUpCash1());
			model.setZfbRate(agentUnit.getZfbRate());
			model.setZfbCash(agentUnit.getZfbCash());
			model.setEwmRate(agentUnit.getEwmRate());
			model.setEwmCash(agentUnit.getEwmCash());
			model.setYsfRate(agentUnit.getYsfRate());
			if(agentUnit.getHbRate()!=null){
				model.setHbRate(agentUnit.getHbRate());
			}
			if(agentUnit.getHbCash()!=null){
				model.setHbCash(agentUnit.getHbCash());
			}
			hrtUnnoCostDao.updateObject(model);
			json.setMsg("修改成功");
			json.setSuccess(true);
		}else {
			json.setMsg("未查询到成本信息，请确认后重试");
			json.setSuccess(false);
		}
		return json;
	}

	private boolean checkNewAddActiveHrt(AgentUnitBean agentUnit, UserBean user, JsonBean json) {
		//  @author:xuegangliu-20190319 新增活动 单条 添加校验
//            （1）若中心成本为空，代理成本可不必校验，允许新增；
//            （2）若中心成本不为空，代理成本大于等于中心成本，允许新增；
//            （3）若中心成本不为空，代理成本小于中心成本，禁止新增，提示“该代理成本低于中心成本”
		// 获取运营中心的成本
		//String prefix = t.getMachineType()+"|"+t.getTxnType()+"|"+t.getTxnDetail()+"|";cashCost cashRate debitRate debitFeeamt creditRate
        String yunyingUnno = getYunYingUnnoByUnno(agentUnit.getUnno());
//		String yunyingUnno = getYunYingUnnoByUnno(user.getUnNo());
		Map hrtCost = getYunYingHrtUnnoCost(yunyingUnno,1);

        String sqlConfig="select nvl(k.subtype,0)||'' subtype from bill_purconfigure k where k.type=3 and k.valuestring='rate' and k.valueinteger=:valueinteger";
        Map mapConfig=new HashMap();
        mapConfig.put("valueinteger",agentUnit.getTxnDetail());
		List<Map<String, Object>> mapList =hrtUnnoCostDao.executeSql2(sqlConfig,mapConfig);
//        Integer configCount = hrtUnnoCostDao.querysqlCounts2(sqlConfig,mapConfig);
//        boolean subType1=configCount>0;
		String subType1=mapList.size()==1?mapList.get(0).get("SUBTYPE")+"":null;
		String keyHrt = "1|1|" + agentUnit.getTxnDetail() + "|";
		if (hrtCost.containsKey(keyHrt + "creditRate")) {
			Double yidai = Double.parseDouble(agentUnit.getRate() + "");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt + "creditRate") + "");
			if (yidai < yunYing) {
				json.setMsg("扫码1000以下（终端0.38）费率(%)低于运营中心成本");
				json.setSuccess(false);
				return true;
			}
		}
		if (hrtCost.containsKey(keyHrt + "cashCost")) {
			Double yidai = Double.parseDouble(agentUnit.getCashRate() + "");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt + "cashCost") + "");
			if (yidai < yunYing) {
				json.setMsg("扫码1000以下（终端0.38）转账费低于运营中心成本");
				json.setSuccess(false);
				return true;
			}
		}

		// @author:lxg-20190911 扫码费率拆分
		if(hrtCost.containsKey(keyHrt+"wxUpRate") && null!=agentUnit.getWxUpRate()){
			Double yidai = Double.parseDouble(agentUnit.getWxUpRate()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"wxUpRate")+"");
			if(yidai<yunYing){
				json.setMsg("扫码1000以上（终端0.38）费率(%)低于运营中心成本");
				json.setSuccess(false);
				return true;
			}
		}
		if(hrtCost.containsKey(keyHrt+"wxUpCash") && null!=agentUnit.getWxUpCash()){
			Double yidai = Double.parseDouble(agentUnit.getWxUpCash()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"wxUpCash")+"");
			if(yidai<yunYing){
				json.setMsg("扫码1000以上（终端0.38）转账费低于运营中心成本");
				json.setSuccess(false);
				return true;
			}
		}
		if(hrtCost.containsKey(keyHrt+"wxUpRate1") && null!=agentUnit.getWxUpRate1()){
			Double yidai = Double.parseDouble(agentUnit.getWxUpRate1()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"wxUpRate1")+"");
			if(yidai<yunYing){
				json.setMsg("扫码1000以上（终端0.45）费率(%)低于运营中心成本");
				json.setSuccess(false);
				return true;
			}
		}
		if(hrtCost.containsKey(keyHrt+"wxUpCash1") && null!=agentUnit.getWxUpCash1()){
			Double yidai = Double.parseDouble(agentUnit.getWxUpCash1()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"wxUpCash1")+"");
			if(yidai<yunYing){
				json.setMsg("扫码1000以上（终端0.45）转账费低于运营中心成本");
				json.setSuccess(false);
				return true;
			}
		}
		if(hrtCost.containsKey(keyHrt+"zfbRate") && null!=agentUnit.getZfbRate()){
			Double yidai = Double.parseDouble(agentUnit.getZfbRate()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"zfbRate")+"");
			if(yidai<yunYing){
				json.setMsg("扫码1000以下（终端0.45）费率(%)低于运营中心成本");
				json.setSuccess(false);
				return true;
			}
		}
		if(hrtCost.containsKey(keyHrt+"zfbCash") && null!=agentUnit.getZfbCash()){
			Double yidai = Double.parseDouble(agentUnit.getZfbCash()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"zfbCash")+"");
			if(yidai<yunYing){
				json.setMsg("扫码1000以下（终端0.45）转账费低于运营中心成本");
				json.setSuccess(false);
				return true;
			}
		}
		if(hrtCost.containsKey(keyHrt+"ewmRate") && null!=agentUnit.getEwmRate()){
			Double yidai = Double.parseDouble(agentUnit.getEwmRate()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"ewmRate")+"");
			if(yidai<yunYing){
				json.setMsg("银联二维码费率低于运营中心成本");
				json.setSuccess(false);
				return true;
			}
		}
		if(hrtCost.containsKey(keyHrt+"ewmCash") && null!=agentUnit.getEwmCash()){
			Double yidai = Double.parseDouble(agentUnit.getEwmCash()+"");
			Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"ewmCash")+"");
			if(yidai<yunYing){
				json.setMsg("银联二维码转账费低于运营中心成本");
				json.setSuccess(false);
				return true;
			}
		}
		if("1".equals(subType1)) {
			if(hrtCost.containsKey(keyHrt+"hbRate") && null!=agentUnit.getHbRate()){
				Double yidai = Double.parseDouble(agentUnit.getHbRate()+"");
				Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"hbRate")+"");
				if(yidai<yunYing){
					json.setMsg("花呗费率低于运营中心成本");
					json.setSuccess(false);
					return true;
				}
			}
			if(hrtCost.containsKey(keyHrt+"hbCash") && null!=agentUnit.getHbCash()){
				Double yidai = Double.parseDouble(agentUnit.getHbCash()+"");
				Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"hbCash")+"");
				if(yidai<yunYing){
					json.setMsg("花呗转账费低于运营中心成本");
					json.setSuccess(false);
					return true;
				}
			}
        }else if("2".equals(subType1)){
			if(hrtCost.containsKey(keyHrt+"hbRate") && null!=agentUnit.getHbRate()){
				Double yidai = Double.parseDouble(agentUnit.getHbRate()+"");
				Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"hbRate")+"");
				if(yidai<yunYing){
					json.setMsg("花呗费率低于运营中心成本");
					json.setSuccess(false);
					return true;
				}
			}
			if(hrtCost.containsKey(keyHrt+"hbCash") && null!=agentUnit.getHbCash()){
				Double yidai = Double.parseDouble(agentUnit.getHbCash()+"");
				Double yunYing = Double.parseDouble(hrtCost.get(keyHrt+"hbCash")+"");
				if(yidai<yunYing){
					json.setMsg("花呗转账费低于运营中心成本");
					json.setSuccess(false);
					return true;
				}
			}
			if (hrtCost.containsKey(keyHrt + "ysfRate") && null != agentUnit.getYsfRate()) {
				Double yidai = Double.parseDouble(agentUnit.getYsfRate() + "");
				Double yunYing = Double.parseDouble(hrtCost.get(keyHrt + "ysfRate") + "");
				if (yidai < yunYing) {
					json.setMsg("云闪付费率低于运营中心成本");
					json.setSuccess(false);
					return true;
				}
			}
			if (hrtCost.containsKey(keyHrt + "debitRate")) {
				Double yidai = Double.parseDouble(agentUnit.getTxnDetail().compareTo(21) > 0 ? agentUnit.getRemarks() : agentUnit.getRate().toString());
				Double yunYing = Double.parseDouble(hrtCost.get(keyHrt + "debitRate") + "");
				if (yidai < yunYing) {
					json.setMsg("刷卡成本低于运营中心成本");
					json.setSuccess(false);
					return true;
				}
			}
			if (hrtCost.containsKey(keyHrt + "debitFeeamt") && StringUtils.isNotEmpty(agentUnit.getCurAmt())) {
				Double yidai = Double.parseDouble(agentUnit.getCurAmt());
				Double yunYing = Double.parseDouble(hrtCost.get(keyHrt + "debitFeeamt") + "");
				if (yidai < yunYing) {
					json.setMsg("刷卡提现成本低于运营中心成本");
					json.setSuccess(false);
					return true;
				}
			}
		}else{
			if (hrtCost.containsKey(keyHrt + "ysfRate") && null != agentUnit.getYsfRate()) {
				Double yidai = Double.parseDouble(agentUnit.getYsfRate() + "");
				Double yunYing = Double.parseDouble(hrtCost.get(keyHrt + "ysfRate") + "");
				if (yidai < yunYing) {
					json.setMsg("云闪付费率低于运营中心成本");
					json.setSuccess(false);
					return true;
				}
			}
			if (hrtCost.containsKey(keyHrt + "debitRate")) {
				Double yidai = Double.parseDouble(agentUnit.getTxnDetail().compareTo(21) > 0 ? agentUnit.getRemarks() : agentUnit.getRate().toString());
				Double yunYing = Double.parseDouble(hrtCost.get(keyHrt + "debitRate") + "");
				if (yidai < yunYing) {
					json.setMsg("刷卡成本低于运营中心成本");
					json.setSuccess(false);
					return true;
				}
			}
			if (hrtCost.containsKey(keyHrt + "debitFeeamt") && StringUtils.isNotEmpty(agentUnit.getCurAmt())) {
				Double yidai = Double.parseDouble(agentUnit.getCurAmt());
				Double yunYing = Double.parseDouble(hrtCost.get(keyHrt + "debitFeeamt") + "");
				if (yidai < yunYing) {
					json.setMsg("刷卡提现成本低于运营中心成本");
					json.setSuccess(false);
					return true;
				}
			}
        }
		return false;
	}

	@Override
	public JsonBean updateUnnoRebateRateGo(AgentUnitBean agentUnit, UserBean user) {
		JsonBean json = new JsonBean();
		HrtUnnoCostModel model = hrtUnnoCostDao.queryObjectByHql("from HrtUnnoCostModel where hucid=? and status=0", new Object[] {agentUnit.getHucid()});
		if(model!=null) {
            HrtUnnoCostNModel modelN = hrtUnnoCostNDao.queryObjectByHql("from HrtUnnoCostNModel where buid=? and status=0 and machineType=?" +
                    " and txnType=? and txnDetail=?", new Object[] {model.getBuid(),model.getMachineType(),model.getTxnType(),model.getTxnDetail()});
			model.setStatus(1);
			model.setFlag(0);
			model.setLmby(user.getLoginName());
			model.setLmDate(new Date());
			hrtUnnoCostDao.updateObject(model);
            if(modelN!=null) {
                modelN.setStatus(1);
                modelN.setLmby(user.getLoginName());
                modelN.setLmDate(new Date());
                hrtUnnoCostNDao.updateObject(modelN);
            }
			json.setMsg("通过成功");
			json.setSuccess(true);
		}else {
			json.setMsg("未查询到待审核信息，请确认后重试");
			json.setSuccess(false);
		}
		return json;
	}
	@Override
	public JsonBean updateUnnoRebateRateBack(AgentUnitBean agentUnit, UserBean user) {
		JsonBean json = new JsonBean();
		HrtUnnoCostModel model = hrtUnnoCostDao.queryObjectByHql("from HrtUnnoCostModel where hucid=? and status=0", new Object[] {agentUnit.getHucid()});
		if(model!=null) {
			HrtUnnoCostNModel modelN = hrtUnnoCostNDao.queryObjectByHql("from HrtUnnoCostNModel where buid=? and status=0 and machineType=?" +
					" and txnType=? and txnDetail=?", new Object[] {model.getBuid(),model.getMachineType(),model.getTxnType(),model.getTxnDetail()});

			model.setStatus(2);
			model.setLmby(user.getLoginName());
			model.setLmDate(new Date());
			hrtUnnoCostDao.updateObject(model);
            modelN.setStatus(2);
            modelN.setLmby(user.getLoginName());
            modelN.setLmDate(new Date());
            hrtUnnoCostNDao.updateObject(modelN);
			json.setMsg("退回成功");
			json.setSuccess(true);
		}else {
			json.setMsg("未查询到待审核信息，请确认后重试");
			json.setSuccess(false);
		}
		return json;
	}
	@Override
	public JsonBean deleteRebateCost(AgentUnitBean agentUnit, UserBean user) {
		JsonBean json = new JsonBean();
		HrtUnnoCostModel model = hrtUnnoCostDao.queryObjectByHql("from HrtUnnoCostModel where hucid=? and status!=1", new Object[] {agentUnit.getHucid()});
		if(model!=null) {
			String hql="from HrtUnnoCostNModel where buid=? and machineType=? and txnType=? and txnDetail=?";
			HrtUnnoCostNModel modelN = hrtUnnoCostNDao.queryObjectByHql(hql,
					new Object[]{model.getBuid(),model.getMachineType(),model.getTxnType(),model.getTxnDetail()});
			hrtUnnoCostDao.deleteObject(model);
			hrtUnnoCostNDao.deleteObject(modelN);
			json.setMsg("删除成功");
			json.setSuccess(true);
		}else {
			json.setMsg("未查询到成本信息，请确认后重试");
			json.setSuccess(false);
		}
		return json;
	}
	/**
	 * 运营中心成本变更列表查询
	 */
	public DataGridBean queryBillBpFile9(AgentUnitBean agentUnit, UserBean userBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FTYPE", 9);
		String sql="select b.fid,b.fid as fids,b.fname,b.ftype,b.status,b.cdate,b.cby,b.aby,b.adate," +
		"b.remarks,s.un_name,b.imgupload,b.costtype from bill_bp_file b,Sys_Unit s where b.cby=s.unno and b.ftype=:FTYPE ";
		if(userBean.getUnitLvl()!=0){
			map.put("CBY", userBean.getUnNo());
			sql+=" and b.cby in (select UNNO from sys_unit start with unno =:CBY "+
				 " and status = 1 connect by NOCYCLE prior unno = upper_unit)  ";
		}
		if (agentUnit.getCbUpLoad() != null && !"".equals(agentUnit.getCbUpLoad())) {
			sql+=" and b.fname = :fname ";
			map.put("fname", agentUnit.getCbUpLoad());
		}
		if (agentUnit.getUnno() != null && !"".equals(agentUnit.getUnno())) {
			sql+=" and b.cby = :unno ";
			map.put("unno", agentUnit.getUnno());
		}
		if (agentUnit.getStatus()!= null && !"".equals(agentUnit.getStatus())) {
			sql+=" and b.status = :status ";
			map.put("status", agentUnit.getStatus());
		}
		if (agentUnit.getBaddr()!= null && !"".equals(agentUnit.getBaddr())) {
			sql+=" and b.costtype = :costType ";
			map.put("costType", agentUnit.getBaddr());
		}
		if (agentUnit.getAdate() != null && !"".equals(agentUnit.getAdate())) {
			sql+=" and b.cdate >= :adate ";
			map.put("adate", agentUnit.getAdate());
		}
		if (agentUnit.getZdate() != null && !"".equals(agentUnit.getZdate())) {
			sql+=" and b.cdate <= :zdate +1 ";
			map.put("zdate", agentUnit.getZdate());
		}
		sql +=" order by b.fid desc";
		String count="select count(1) from ("+sql+")";
		List<Map<String,String>> list=agentSalesDao.queryObjectsBySqlList(sql,map,agentUnit.getPage(),agentUnit.getRows());
		BigDecimal counts = agentSalesDao.querysqlCounts(count,map);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());//查询数据库总条数
		dgd.setRows(list);
		list=null;
		return dgd;
	}


	@Override
	public DataGridBean queryBillBpFileGrid3(AgentUnitBean agentUnit, UserBean userBean){
		if(userBean.getUnitLvl()>=1){
			return new DataGridBean();
		}
		StringBuilder sql=new StringBuilder(64);
		StringBuilder sqlCount=new StringBuilder(64);
		Map map=new HashMap();
		sql.append("select a.*,a.FID FIDS from bill_bp_file a where 1=1 and a.ftype=:ftype ");
		map.put("ftype",3);

		if (StringUtils.isNotEmpty(agentUnit.getCbUpLoad())) {
			sql.append(" and a.fname=:fname ");
			map.put("fname",agentUnit.getCbUpLoad());
		}

		if (agentUnit.getAdate()!=null && agentUnit.getZdate()!=null) {
			sql.append(" and a.cdate>=:cdate and a.cdate<(:cdate1+1) ");
			map.put("cdate",agentUnit.getAdate());
			map.put("cdate1",agentUnit.getZdate());
		}

		if (StringUtils.isNotEmpty(agentUnit.getBaddr())) {
			sql.append(" and a.costtype=:costtype ");
			map.put("costtype",agentUnit.getBaddr());
		}

		if (StringUtils.isNotEmpty(agentUnit.getStatus())) {
			sql.append(" and a.status=:status ");
			map.put("status",agentUnit.getStatus());
		}

		sqlCount.append(" select count(*) from (").append(sql.toString()).append(")");
		sql.append(" order by a.cdate desc ");
		BigDecimal count = billBpFileDao.querysqlCounts(sqlCount.toString(),map);
		List list = billBpFileDao.queryObjectsBySqlList(sql.toString(),map,agentUnit.getPage(),agentUnit.getRows());
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(count.intValue());
		dgd.setRows(list);
		return dgd;
	}

	@Override
	public List<JSONObject> getFileRecordExcelInfo(Integer fId,Integer type,Integer subtype){
		StringBuilder hql=new StringBuilder(32);
		Map map=new HashMap();
		hql.append(" from BillFileRecordModel where fId=:fId and type=:type and subType=:subType");
		map.put("fId",fId);
		map.put("type",type);
		map.put("subType",subtype);
		List<BillFileRecordModel> list = billFileRecordDao.queryObjectsByHqlList(hql.toString(),map);
		List<JSONObject> jsonObjectList=new ArrayList<JSONObject>();
		if(list.size()>0){
			for (BillFileRecordModel model : list) {
				if(model.getExcelInfo()!=null) {
					JSONObject execlInfo = JSONObject.parseObject(model.getExcelInfo());
					execlInfo.put("status",model.getStatus());
					jsonObjectList.add(execlInfo);
				}
			}
		}
		return jsonObjectList;
	}

	public Map getFileImgPath(AgentUnitBean agentUnit){
		Map map=new HashMap();
		String filePath="";
		BillBpFileModel billBpFileModel = (BillBpFileModel) billBpFileDao.getObjectByID(obj(BillBpFileModel.class), Integer.parseInt(agentUnit.getFID()));
		String savePath = agentUnitDao.querySavePath("Change_Sn_Mer_Img");
		if(billBpFileModel!=null && billBpFileModel.getImgUpload()!=null){
			filePath=savePath+File.separator+billBpFileModel.getImgUpload();
		}
		map.put("filePath",filePath);
		map.put("imgUpload",billBpFileModel.getImgUpload());
		return map;
	}

	@Override
	public boolean saveOrUpdateImportSnOrMerImg(AgentUnitBean agentUnit){
		boolean flag=true;
		BillBpFileModel billBpFileModel = (BillBpFileModel) billBpFileDao.getObjectByID(obj(BillBpFileModel.class), Integer.parseInt(agentUnit.getFID()));
		if(billBpFileModel!=null){
			if(agentUnit.getLegalAUpLoadFile()!=null) {
				try {
					uploadFileByTitle("Change_Sn_Mer_Img", agentUnit.getLegalAUpLoadFile(), agentUnit.getLegalAUpLoad());
					billBpFileModel.setImgUpload(agentUnit.getLegalAUpLoad());
				} catch (Exception e) {
					flag=false;
					e.printStackTrace();
				}
			}
		}
		billBpFileDao.saveOrUpdateObject(billBpFileModel);
		return flag;
	}

	@Override
	public List<Map> saveImportSnModifyTempXls(AgentUnitBean agentUnit,String xlsfile, String name, UserBean user) {
		Map resutMap = new HashMap();
		int sumCount = 0;
		File filename = new File(xlsfile);
		HSSFWorkbook workbook = null;
		Map<String,String> uniqueUnno = new HashMap<String,String>();
		List<Map> errList=new ArrayList<Map>();
		List<JSONObject> jsonObjectList=new ArrayList<JSONObject>();
		try {
			workbook = new HSSFWorkbook(new FileInputStream(filename));
		} catch (IOException e) {
			log.info("导入批量成本文件异常:"+e.getMessage());
		}

		BillBpFileModel billBpFileModel = new BillBpFileModel();
		if(StringUtils.isNotEmpty(agentUnit.getFID())){
			billBpFileModel= (BillBpFileModel) billBpFileDao.getObjectByID(obj(BillBpFileModel.class), Integer.parseInt(agentUnit.getFID()));
			billBpFileModel.setfName(name);
			if(billBpFileModel==null){
				billBpFileModel = new BillBpFileModel();
				billBpFileModel.setCostType(1);
				billBpFileModel.setfName(name);
				billBpFileModel.setfType(3);
				billBpFileModel.setCdate(new Date());
				billBpFileModel.setCby(user.getUserID().toString());
			}
		}else {
			billBpFileModel.setCostType(1);
			billBpFileModel.setfName(name);
			billBpFileModel.setfType(3);
			billBpFileModel.setCdate(new Date());
			billBpFileModel.setCby(user.getUserID().toString());
		}
		HSSFSheet sheet = workbook.getSheetAt(0);
		sumCount = sheet.getPhysicalNumberOfRows()-1;

		for(int i = 1; i <= sumCount; i++){
			JSONObject jsonObject=new JSONObject();
//			sn	原unno	变更后unno	变更后返利类型	变更后刷卡费率（RATE）	变更后提现手续费（SECONDRATE）	变更后扫码费率（RATE）
			HSSFRow row = sheet.getRow(i);
			HSSFCell cell = row.getCell((short)0);
			if(cell == null || cell.toString().trim().equals("")){
				break;
			}else{
				// sn
				row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
				String sn = row.getCell((short)0).getStringCellValue().trim();
				jsonObject.put("sn",sn);

				// 原unno
				HSSFCell cell1= row.getCell((short)1);
				row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
				String unno = row.getCell((short)1).getStringCellValue().trim();
				jsonObject.put("unno",unno);

				// 变更后unno
				row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
				String unno1=row.getCell((short)2).getStringCellValue().trim();
				jsonObject.put("unno1",unno1);

				// 变更后返利类型
				HSSFCell cell3= row.getCell((short)3);
				row.getCell((short) 3).setCellType(Cell.CELL_TYPE_STRING);
				String rebateType = row.getCell((short) 3).getStringCellValue().trim();
				jsonObject.put("rebateType",rebateType);

				// 变更后刷卡费率（RATE）
				row.getCell((short)4).setCellType(Cell.CELL_TYPE_STRING);
				String rate = row.getCell((short)4).getStringCellValue().trim();
				jsonObject.put("rate",rate);

				// 变更后提现手续费（SECONDRATE）
				row.getCell((short)5).setCellType(Cell.CELL_TYPE_STRING);
				String secondRate = row.getCell((short)5).getStringCellValue().trim();
				jsonObject.put("secondRate",secondRate);

				// 变更后扫码费率（RATE）
				row.getCell((short)6).setCellType(Cell.CELL_TYPE_STRING);
				String scanRate = row.getCell((short)6).getStringCellValue().trim();
				jsonObject.put("scanRate",scanRate);

				// 押金金额
				HSSFCell cell7 = row.getCell((short)7);
//				if(cell7 != null && !cell7.toString().trim().equals("")){
                row.getCell((short)7).setCellType(Cell.CELL_TYPE_STRING);
                String depositAmt = row.getCell((short)7).getStringCellValue().trim();
                jsonObject.put("depositAmt",depositAmt);
//				}

				String errMsg=validateSnModifyTempXls(jsonObject,i);
				if(StringUtils.isNotEmpty(errMsg)){
					Map errMap=new HashMap();
					errMap.put("sn",jsonObject.getString("sn"));
					errMap.put("errMsg",errMsg);
					errList.add(errMap);
				}
				jsonObjectList.add(jsonObject);
			}
		}
		if(errList.size()>0){
			billBpFileModel.setStatus(-2);
			billBpFileDao.saveOrUpdateObject(billBpFileModel);
			return errList;
		}else{
			billBpFileModel.setStatus(0);
			if(billBpFileModel.getFid()!=null){
				billFileRecordDao.executeHql(" delete from BillFileRecordModel where fId=?",new Object[]{billBpFileModel.getFid()});
			}
			billBpFileDao.saveOrUpdateObject(billBpFileModel);

			for (JSONObject object : jsonObjectList) {
				BillFileRecordModel model=new BillFileRecordModel();
				model.setfId(billBpFileModel.getFid());
				model.setType(3);
				model.setSubType(1);
				model.setStatus(0);
                model.setFlag(0);
				model.setMainInfo(object.getString("sn"));
				model.setExcelInfo(object.toJSONString());
				model.setCreateUser(user.getUserID().toString());
				model.setCreateDate(new Date());
				model.setRemark1(object.getString("unno"));
				model.setRemark2(object.getString("unno1"));
				billFileRecordDao.saveObject(model);
			}
		}
		return new ArrayList<Map>();
	}

	private String validateSnModifyTempXls(JSONObject jsonObject,int lineNum){
		//  1.所有项为必填项，sn归属在原机构名下方可修改，且sn未使用状态，是显示待审核，否显示失败并返回失败文件，失败文件显示失败的内容及原因
		StringBuilder sb=new StringBuilder();
		Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
		for (Map.Entry<String, Object> entry : entrySet) {
			if(entry.getValue()==null || "".equals(entry.getValue())){
				sb.append("第").append(lineNum+1).append("行数据必填项存在为空数据.");
				return sb.toString();
			}
		}
		StringBuilder sql=new StringBuilder(64);
		sql.append("select count(1) from  bill_terminalinfo t where t.sn=:sn and t.unno=:unno and t.status!=:status");
		Map map=new HashMap();
		map.put("sn",jsonObject.getString("sn"));
		map.put("unno",jsonObject.getString("unno"));
		map.put("status",2);
		Integer result = billBpFileDao.querysqlCounts2(sql.toString(),map);
		if(result==0){
			sb.append("第").append(lineNum+1).append("行数据未在原机构下或sn被绑定.");
		}

		// 如果填写押金,校验押金是否与活动匹配
		if(jsonObject.containsKey("depositAmt") && jsonObject.get("depositAmt")!=null) {
			Integer amt = jsonObject.getInteger("depositAmt");
            Integer rebateType = jsonObject.getInteger("rebateType");
            if (rebateType != null) {
                String amtSql = "select t.configinfo,nvl(t.depositamt,0)||'' as depositamt from bill_purconfigure t where t.type=3 and t.valueinteger=:rebateType ";
                Map amtMap = new HashMap();
                amtMap.put("rebateType", rebateType);
                List<Map<String, String>> amtList = billBpFileDao.queryObjectsBySqlListMap(amtSql, amtMap);
                if (amtList.size() == 1) {
                    String configInfo = amtList.get(0).get("CONFIGINFO");
                    String depositamt = amtList.get(0).get("DEPOSITAMT");
                    //是否与配置表里的押金金额匹配
                    if(!depositamt.equals(amt.toString())){
                        if (StringUtils.isNotEmpty(configInfo)) {
                            JSONObject jsonObject1 = com.alibaba.fastjson.JSON.parseObject(configInfo);
                            JSONObject tradeJSON = jsonObject1.getJSONObject("tradeAmt");
                            if (!tradeJSON.containsKey(amt.toString())) {
                                sb.append("第").append(lineNum+1).append("行押金金额不正确(与活动不匹配).");
                            }
                        }else{
							sb.append("第").append(lineNum+1).append("行押金金额不正确(与活动不匹配).");
                        }
                    }

                }else{
                    // 活动未配置
                    sb.append("第").append(lineNum+1).append("行变更返利类型填写错误(未在系统中存在).");
                }
            }
		}
		return sb.toString();
	}

	@Override
	public boolean updateBillBpFile3AduitStatus(AgentUnitBean agentUnit,UserBean userSession){
		BillBpFileModel billBpFile=(BillBpFileModel) billBpFileDao.getObjectByID(obj(BillBpFileModel.class), Integer.parseInt(agentUnit.getFID()));
		billBpFile.setStatus(agentUnit.getCycle());
		billBpFile.setRemarks(agentUnit.getRemarks());
		billBpFile.setAby(userSession.getUserName());
		billBpFile.setAdate(new Date());
		billBpFileDao.updateObject(billBpFile);
		List<BillFileRecordModel> list = billFileRecordDao.queryObjectsByHqlList(" from BillFileRecordModel where fId=? ",new Object[]{billBpFile.getFid()});
        if(agentUnit.getCycle()==1){
            if (billBpFile.getCostType()==1) {
                for (BillFileRecordModel model : list) {
                	// {"rate":"0.0058","rebateType":"18","scanRate":"0.0048","secondRate":"3","sn":"03374515","unno":"110000","unno1":"110000"}
					JSONObject jsonObject=JSONObject.parseObject(model.getExcelInfo());
					StringBuilder sql=new StringBuilder(256);
					Map map=new HashMap();
					if(StringUtils.isNotEmpty(jsonObject.getString("depositAmt"))){
						sql.append("update bill_terminalinfo a set a.rebatetype=:rebateType,a.rate=:rate,a.scanrate=:scanRate,a.secondrate=:secondRate,a.unno=:unno1,a.depositamt=:depositAmt where a.unno=:unno and a.sn=:sn and a.status!=2");
						map.put("depositAmt",jsonObject.get("depositAmt"));
					}else{
						sql.append("update bill_terminalinfo a set a.rebatetype=:rebateType,a.rate=:rate,a.scanrate=:scanRate,a.secondrate=:secondRate,a.unno=:unno1 where a.unno=:unno and a.sn=:sn and a.status!=2");
					}
					map.put("rebateType",jsonObject.get("rebateType"));
					map.put("rate",jsonObject.get("rate"));
					map.put("scanRate",jsonObject.get("scanRate"));
					map.put("secondRate",jsonObject.get("secondRate"));
					map.put("unno1",jsonObject.get("unno1"));
					map.put("unno",jsonObject.get("unno"));
					map.put("sn",jsonObject.get("sn"));
					Integer result = billBpFileDao.executeSqlUpdate(sql.toString(),map);
					if(result>0){
						model.setStatus(1);
						model.setUpdateUser(userSession.getUserID()+"");
						model.setUpdateDate(new Date());
						billFileRecordDao.updateObject(model);
					}
                }
            }else if (billBpFile.getCostType()==2){
				for (BillFileRecordModel model : list) {
					JSONObject jsonObject=JSONObject.parseObject(model.getExcelInfo());
					StringBuilder sql=new StringBuilder(512);
					Map map=new HashMap();
					sql.append("update bill_merchantinfo a set a.mid=a.mid ");
					if(StringUtils.isNotEmpty(jsonObject.getString("rate"))){
						sql.append(",a.bankfeerate=:bankfeerate,a.creditbankrate=:creditbankrate ");
						map.put("bankfeerate",jsonObject.get("rate"));
						map.put("creditbankrate",jsonObject.get("rate"));
					}
					if(StringUtils.isNotEmpty(jsonObject.getString("secondRate"))){
						sql.append(",a.secondrate=:secondrate ");
						map.put("secondrate",jsonObject.get("secondRate"));
					}
					if(StringUtils.isNotEmpty(jsonObject.getString("changeUnno"))){
						sql.append(",a.unno=:changeUnno ");
						map.put("changeUnno",jsonObject.get("changeUnno"));
					}
					sql.append(" where a.mid=:unno1 and a.unno in ");
					sql.append(" (select s.unno from sys_unit s start with s.unno = :unno connect by prior s.unno = s.upper_unit) ");
					map.put("unno",jsonObject.get("unno"));
					map.put("unno1",jsonObject.get("unno1"));
					Integer result = billBpFileDao.executeSqlUpdate(sql.toString(),map);
					if(result>0){
						if(StringUtils.isNotEmpty(jsonObject.getString("changeUnno"))){
							String changeTerm="update bill_terminalinfo k set k.unno=:unno where k.termid in (" +
									" select a.tid from bill_merchantterminalinfo a where a.mid=:mid and a.maintaintype!='D' )";
							Map ctMap = new HashMap();
							ctMap.put("unno",jsonObject.getString("changeUnno"));
							ctMap.put("mid",jsonObject.getString("unno1"));
							Integer ctCount = billBpFileDao.executeSqlUpdate(changeTerm,ctMap);

							String changeMt="update bill_merchantterminalinfo a set a.unno=:unno where a.mid=:mid and a.maintaintype!='D' ";
							Integer mtCount = billBpFileDao.executeSqlUpdate(changeMt,ctMap);
						}
						model.setUpdateUser(userSession.getUserID()+"");
						model.setUpdateDate(new Date());
						model.setStatus(1);
						billFileRecordDao.updateObject(model);
					}
				}
            }
        }

		return true;
	}

	@Override
	public List<Map> saveImportMerRateModifyTempXls(AgentUnitBean agentUnit,String xlsfile, String name, UserBean user) {
		Map resutMap = new HashMap();
		int sumCount = 0;
		File filename = new File(xlsfile);
		HSSFWorkbook workbook = null;
		List<JSONObject> jsonObjectList=new ArrayList<JSONObject>();
		List<Map> errList=new ArrayList<Map>();
		try {
			workbook = new HSSFWorkbook(new FileInputStream(filename));
		} catch (IOException e) {
			log.info("导入批量成本文件异常:"+e.getMessage());
		}
		BillBpFileModel billBpFileModel=new BillBpFileModel();
		if(StringUtils.isNotEmpty(agentUnit.getFID())){
			billBpFileModel= (BillBpFileModel) billBpFileDao.getObjectByID(obj(BillBpFileModel.class), Integer.parseInt(agentUnit.getFID()));
			billBpFileModel.setfName(name);
			if(billBpFileModel==null){
				billBpFileModel = new BillBpFileModel();
				billBpFileModel.setCostType(2);
				billBpFileModel.setfName(name);
				billBpFileModel.setfType(3);
				billBpFileModel.setCdate(new Date());
				billBpFileModel.setCby(user.getUserID().toString());
			}
		}else {
			billBpFileModel.setCostType(2);
			billBpFileModel.setfName(name);
			billBpFileModel.setfType(3);
			billBpFileModel.setCdate(new Date());
			billBpFileModel.setCby(user.getUserID().toString());
		}

		HSSFSheet sheet = workbook.getSheetAt(0);
		sumCount = sheet.getPhysicalNumberOfRows()-1;
		for(int i = 1; i <= sumCount; i++){
			JSONObject jsonObject=new JSONObject();
//			一级机构号	商户编号	贷记卡费率	秒到手续费
			HSSFRow row = sheet.getRow(i);
			HSSFCell cell = row.getCell((short)0);
			if(cell == null || cell.toString().trim().equals("")){
				break;
			}else{
				// 一级机构号
				HSSFCell cell1= row.getCell((short)0);
				row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
				String unno = row.getCell((short)0).getStringCellValue().trim();
				jsonObject.put("unno",unno);

				// 商户编号
				row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
				String unno1=row.getCell((short)1).getStringCellValue().trim();
				jsonObject.put("unno1",unno1);

				// 贷记卡费率
				HSSFCell cell2= row.getCell((short)2);
				if(cell2 != null ) {
					row.getCell((short) 2).setCellType(Cell.CELL_TYPE_STRING);
					String rate = row.getCell((short) 2).getStringCellValue().trim();
					jsonObject.put("rate", rate);
				}
				// 秒到手续费
				HSSFCell cell3= row.getCell((short)3);
				if(cell3 != null ) {
					row.getCell((short) 3).setCellType(Cell.CELL_TYPE_STRING);
					String secondRate = row.getCell((short) 3).getStringCellValue().trim();
					jsonObject.put("secondRate", secondRate);
				}
				HSSFCell cell4= row.getCell((short)4);
				if(cell4 != null ) {
					// 变更后的机构号
					row.getCell((short) 4).setCellType(Cell.CELL_TYPE_STRING);
					String changeUnno = row.getCell((short) 4).getStringCellValue().trim();
					jsonObject.put("changeUnno", changeUnno);
				}

				String errMsg = validateImportMerRateModifyTemp(jsonObject,i);
				if(StringUtils.isNotEmpty(errMsg)){
					Map errMap=new HashMap();
					errMap.put("unno1",unno1);
					errMap.put("errMsg",errMsg);
					errList.add(errMap);
				}

				jsonObjectList.add(jsonObject);
			}
		}
		if(errList.size()>0){
			billBpFileModel.setStatus(-2);
			billBpFileDao.saveOrUpdateObject(billBpFileModel);
			return errList;
		}else {
			billBpFileModel.setStatus(0);
			if(billBpFileModel.getFid()!=null){
				billFileRecordDao.executeHql(" delete from BillFileRecordModel where fId=?",new Object[]{billBpFileModel.getFid()});
			}
			billBpFileDao.saveOrUpdateObject(billBpFileModel);
			for (JSONObject object : jsonObjectList) {
				BillFileRecordModel model = new BillFileRecordModel();
				model.setfId(billBpFileModel.getFid());
				model.setType(3);
				model.setSubType(2);
				model.setStatus(0);
				model.setFlag(0);
				model.setMainInfo(object.getString("unno1"));
				model.setExcelInfo(object.toJSONString());
				model.setCreateUser(user.getUserID().toString());
				model.setCreateDate(new Date());
				model.setRemark1(object.getString("unno"));
				model.setRemark2(object.getString("unno1"));
				billFileRecordDao.saveObject(model);
			}
		}
		return new ArrayList<Map>();
	}

	public String validateImportMerRateModifyTemp(JSONObject jsonObject,int lineNum){
//		1、类型商户：判断商户是否归属一级机构名下，是显示待审核，否显示失败并返回具体失败原因。
		// 商户/机构	一级机构号	商户编号/机构号	贷记卡费率	秒到手续费	扫码费率
		StringBuilder errMsg=new StringBuilder();
		StringBuilder sql=new StringBuilder(64);
		Map map=new HashMap();
		// 商户
		sql.append("select count(1) from (select s.unno from sys_unit s start with s.unno=:unno connect by prior s.unno=s.upper_unit) xx where xx.unno in (");
		sql.append(" select t.unno from bill_merchantinfo t where t.mid=:mid ) ");
		map.put("unno",jsonObject.getString("unno"));
		map.put("mid",jsonObject.getString("unno1"));
		Integer count = billBpFileDao.querysqlCounts2(sql.toString(),map);
		if(count==0){
			errMsg.append("第").append(lineNum+1).append("行商户归属不在一级机构名下;");
		}

		// 变更的机构号是否存在
		if(StringUtils.isNotEmpty(jsonObject.getString("changeUnno"))) {
			String changeSql = "select count(1) from sys_unit s where s.unno=:unno";
			Map changeMap = new HashMap();
			changeMap.put("unno", jsonObject.getString("changeUnno"));
			Integer changeCount = billBpFileDao.querysqlCounts2(changeSql, changeMap);
			if (changeCount == 0) {
				errMsg.append("第").append(lineNum + 1).append("行商户变更机构号不存在");
			}
		}
		return errMsg.toString();
	}


	/**
	 * 成本变更-非活动
	 * @throws Exception 
	 */
	public Map<String, Object> addUnnoCost(AgentUnitBean agentUnit,UserBean userBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		// TODO @author:lxg-20200518 成本上限校验
		List<HrtUnnoCostWModel> list = validateSaveCBXls(agentUnit);
		for (HrtUnnoCostWModel costWModel : list) {
            String tip = validateLimitCost(convertModelByW(costWModel));
			if(StringUtils.isNotEmpty(tip)){
				map.put("rtnCode", "01");
				map.put("rtnMsg", tip);
				return map;
			}
		}

		String imageDay = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		//文件表
		BillBpFileModel billBpFile = new BillBpFileModel();
//		try {
			billBpFile.setfName(agentUnit.getCbUpLoad());
			billBpFile.setCostType(1);
			billBpFile.setfType(9);
			billBpFile.setStatus(0);
			billBpFile.setCby(userBean.getUnNo());
			billBpFile.setCdate(new Date());
			billBpFileDao.saveObject(billBpFile);
			String ImgUpload=imageDay+"."+billBpFile.getFid()+"."+userBean.getUnNo()+"."+agentUnit.getZipUpLoad();
			billBpFile.setImgUpload(ImgUpload);
			billBpFileDao.updateObject(billBpFile);
//		} catch (Exception e) {
//			map.put("rtnCode", "01");
//			map.put("rtnMsg", "数据异常，请稍后重试！");
//			log.info("成本表插入异常："+e.getMessage());
//			e.printStackTrace();
//		}
		try {
			//分润照片ZIP压缩包上传
			saveZip(imageDay,ImgUpload,billBpFile,agentUnit,userBean);
		} catch (Exception e) {
			log.info("分润照片上传异常："+e.getMessage());
			map.put("rtnCode", "01");
			map.put("rtnMsg", "分润照片上传错误，请检查照片格式！");
			billBpFileDao.deleteObject(billBpFile);
			return map;
		}
		try {
			//成本变更文件
			saveCBXls(billBpFile,agentUnit,userBean);
		} catch (Exception e) {
			log.info("读取结算成本文件异常："+e.getMessage());
			map.put("rtnCode", "01");
			map.put("rtnMsg", e.getMessage());
			billBpFileDao.deleteObject(billBpFile);
			return map;
		}
		map.put("rtnCode", "00");
		return map;
	}

    /**
     * HrtUnnoCostWModel对象转换为HrtUnnoCostModel 对象属性名称不一致需要转换
     * @param costWModel
     * @return
     */
	private HrtUnnoCostModel convertModelByW(HrtUnnoCostWModel costWModel){
        HrtUnnoCostModel t = new HrtUnnoCostModel();
        if(costWModel.getHbRate()!=null) {
            t.setHbRate(costWModel.getHbRate());
        }
        if(costWModel.getHbCash()!=null) {
            t.setHbCash(costWModel.getHbCash());
        }
        if(costWModel.getWxUpRate()!=null){
            t.setWxUpRate(costWModel.getWxUpRate());
        }
        if(costWModel.getWxUpCash()!=null) {
            t.setWxUpCash(costWModel.getWxUpCash());
        }
        if(costWModel.getWxUpRate1()!=null){
            t.setWxUpRate1(costWModel.getWxUpRate1());
        }
        if(costWModel.getWxUpCash1()!=null) {
            t.setWxUpCash1(costWModel.getWxUpCash1());
        }
        if(costWModel.getZfbRate()!=null){
            t.setZfbRate(costWModel.getZfbRate());
        }
        if(costWModel.getZfbCash()!=null) {
            t.setZfbCash(costWModel.getZfbCash());
        }
        if(costWModel.getEwmRate()!=null) {
            t.setEwmRate(costWModel.getEwmRate());
        }
        if(costWModel.getEwmCash()!=null) {
            t.setEwmCash(costWModel.getEwmCash());
        }
        if(costWModel.getYsfRate()!=null) {
            t.setYsfRate(costWModel.getYsfRate());
        }
        if(costWModel.getTxn_detail()!=null){
            t.setTxnDetail(costWModel.getTxn_detail());
        }
//        t.setHucid(0);
        if(costWModel.getUnno()!=null){
            t.setUnno(costWModel.getUnno());
        }
        if(costWModel.getMachine_type()!=null){
            t.setMachineType(costWModel.getMachine_type());
        }
        if(costWModel.getTxn_type()!=null) {
            t.setTxnType(costWModel.getTxn_type());
        }
        if(costWModel.getDebit_rate()!=null){
            t.setDebitRate(new BigDecimal(costWModel.getDebit_rate().toString()));
        }
        if(costWModel.getDebit_feeamt()!=null) {
            t.setDebitFeeamt(new BigDecimal(costWModel.getDebit_feeamt().toString()));
        }
        if(costWModel.getCredit_rate()!=null){
            t.setCreditRate(new BigDecimal(costWModel.getCredit_rate().toString()));
        }
        if(costWModel.getCash_cost()!=null){
            t.setCashCost(new BigDecimal(costWModel.getCash_cost().toString()));
        }
        if(costWModel.getCash_rate()!=null) {
            t.setCashRate(new BigDecimal(costWModel.getCash_rate().toString()));
        }
//        t.setStatus(0);
//        t.setFlag(0);
//        t.setOperateType(0);
//        t.setCdate(new Date());
//        t.setCby("");
//        t.setLmDate(new Date());
//        t.setLmby("");
//        t.setBuid(0);
	    return t;
    }

	/**
	 * 成本变更-活动
	 * @throws Exception 
	 */
	public Map<String, Object> addUnnoCost1(AgentUnitBean agentUnit,UserBean userBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String imageDay = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();

		// TODO @author:lxg-20200518 成本上限校验
		List<HrtUnnoCostWModel> list = validateSaveCB1Xls(agentUnit);
		for (HrtUnnoCostWModel costWModel : list) {
			String tip = validateLimitCost(convertModelByW(costWModel));
			if(StringUtils.isNotEmpty(tip)){
				map.put("rtnCode", "01");
				map.put("rtnMsg", tip);
				return map;
			}
		}

		//文件表
		BillBpFileModel billBpFile = new BillBpFileModel();
//		try {
			billBpFile.setfName(agentUnit.getCbUpLoad());
			billBpFile.setCostType(2);
			billBpFile.setfType(9);
			billBpFile.setStatus(0);
			billBpFile.setCby(userBean.getUnNo());
			billBpFile.setCdate(new Date());
			billBpFileDao.saveObject(billBpFile);
			String ImgUpload=imageDay+"."+billBpFile.getFid()+"."+userBean.getUnNo()+"."+agentUnit.getZipUpLoad();
			billBpFile.setImgUpload(ImgUpload);
			billBpFileDao.updateObject(billBpFile);
//		} catch (Exception e) {
//			map.put("rtnCode", "01");
//			map.put("rtnMsg", "数据异常，请稍后重试！");
//			log.info("成本表插入异常："+e.getMessage());
//			e.printStackTrace();
//		}
		try {
			//分润照片ZIP压缩包上传
			saveZip(imageDay,ImgUpload,billBpFile,agentUnit,userBean);
		} catch (Exception e) {
			log.info("分润照片上传异常："+e.getMessage());
			map.put("rtnCode", "01");
			map.put("rtnMsg", "分润照片上传错误，请检查照片格式！");
			billBpFileDao.deleteObject(billBpFile);
			return map;
		}
		try {
			//成本变更文件
			saveCB1Xls(billBpFile,agentUnit,userBean);
		} catch (Exception e) {
			log.info("读取结算成本文件异常："+e.getMessage());
			map.put("rtnCode", "01");
			map.put("rtnMsg", e.getMessage());
			billBpFileDao.deleteObject(billBpFile);
			return map;
		}
		map.put("rtnCode", "00");
		return map;
	}
	/**
	 * 分润照片ZIP压缩包上传
	 * @throws IOException
	 */
	public void saveZip(String day,String ImgUpload,BillBpFileModel billBpFile,AgentUnitBean agentUnit,UserBean userBean) throws Exception{
//		FileZipUtil zipUint = new FileZipUtil();
//		File filename = new File(agentUnit.getZipUpLoad());
//		zipUint.unZipFiles(filename, "D:\\upload\\test\\");
//	    String savePath = paramModel.get().getUploadPath();
		File zipFile=new File(agentUnit.getZipUpLoadFile());
	    String savePath = querySysParam("FRUpload");
	    String realPath = savePath + File.separator + day;
	    File dir = new File(realPath);
	    if(!dir.exists()){
	        dir.mkdirs();
	    }
	    String fPath = realPath + File.separator + ImgUpload;
	    File destFile = new File(fPath);
//	    zipFile.renameTo(destFile);
		FileInputStream fis = new FileInputStream(zipFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		byte [] buffer = new byte[1024];
		int len = 0;
		while((len = fis.read(buffer))>0){
			fos.write(buffer,0,len);
		} 
		fos.close();
		fis.close();
	}
	/**
	 * 成本文件上传-非活动
	 * @param billBpFile
	 * @param agentUnit
	 * @param userBean
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void saveCBXls(BillBpFileModel billBpFile,AgentUnitBean agentUnit,UserBean userBean) throws FileNotFoundException, IOException {
		File filename=new File(agentUnit.getCbUpLoadFile());
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
		HSSFSheet sheet=workbook.getSheetAt(0);
		//成本审核表
		for(int i=4;i<sheet.getLastRowNum()+1 ;i++){
			HSSFRow row=sheet.getRow(i);
			HSSFCell cell;
			cell = row.getCell((short)3);
			row.getCell(3).setCellType(cell.CELL_TYPE_STRING);
			String unno=row.getCell((short)3).getStringCellValue().trim(); //unno
			//0.72秒到+是否直发分润返现数据等
			if(null!=row.getCell(4) || null!=row.getCell(5)||null!=row.getCell(35)||null!=row.getCell(36)||null!=row.getCell(37)
				||null!=row.getCell(38)||null!=row.getCell(39)){
				HrtUnnoCostWModel hrtUnnoCost1 = new HrtUnnoCostWModel();
				hrtUnnoCost1.setFiid(billBpFile.getFid());
				hrtUnnoCost1.setUnno(unno);
				hrtUnnoCost1.setMachine_type(1);
				hrtUnnoCost1.setTxn_type(1);
				hrtUnnoCost1.setTxn_detail(2);
				hrtUnnoCost1.setStatus(1);
				//借贷费率
				if(null!=row.getCell(4) && !"".equals(String.valueOf(row.getCell(4)))){
					row.getCell(4).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setDebit_rate(row.getCell((short)4).getNumericCellValue());
					hrtUnnoCost1.setCredit_rate(row.getCell((short)4).getNumericCellValue());
				}
				//提现成本
				if(null!=row.getCell(5) && !"".equals(String.valueOf(row.getCell(5)))){
					row.getCell(5).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setCash_cost(row.getCell((short)5).getNumericCellValue());
				}
				//是否直发分润 
				if(null!=row.getCell(35) && !"".equals(String.valueOf(row.getCell(35)))){
					row.getCell(35).setCellType(cell.CELL_TYPE_STRING);
					hrtUnnoCost1.setProfittype(row.getCell((short)35).getStringCellValue().equals("是")?1:0);    
				}
				//分润税点
				if(null!=row.getCell(36) && !"".equals(String.valueOf(row.getCell(36)))){
					row.getCell(36).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setRate(row.getCell((short)36).getNumericCellValue());
				}
				//大蓝牙是否直发返现 
				if(null!=row.getCell(37) && !"".equals(String.valueOf(row.getCell(37)))){
					row.getCell(37).setCellType(cell.CELL_TYPE_STRING);
					hrtUnnoCost1.setMpostype(row.getCell((short)37).getStringCellValue().equals("是")?1:0);
				}
				//小蓝牙是否直发返现 
				if(null!=row.getCell(38) && !"".equals(String.valueOf(row.getCell(38)))){
					row.getCell(38).setCellType(cell.CELL_TYPE_STRING);
					hrtUnnoCost1.setSpostype(row.getCell((short)38).getStringCellValue().equals("是")?1:0);
				}
				//代扣机器款
				if(null!=row.getCell(39) && !"".equals(String.valueOf(row.getCell(39)))){
					row.getCell(39).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setTeramt(row.getCell((short)39).getNumericCellValue());
				}
				billBpFileDao.saveObject(hrtUnnoCost1);
			}
			//0.6秒到
			if(null!=row.getCell(6) && !"".equals(String.valueOf(row.getCell(6))) 
			|| null!=row.getCell(7) && !"".equals(String.valueOf(row.getCell(7)))){
				HrtUnnoCostWModel hrtUnnoCost2 = new HrtUnnoCostWModel();
				hrtUnnoCost2.setFiid(billBpFile.getFid());
				hrtUnnoCost2.setUnno(unno);
				hrtUnnoCost2.setMachine_type(1);
				hrtUnnoCost2.setTxn_type(1);
				hrtUnnoCost2.setTxn_detail(1);
				hrtUnnoCost2.setStatus(1);
				//借贷费率
				if(null!=row.getCell(6) && !"".equals(String.valueOf(row.getCell(6)))){
					row.getCell(6).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost2.setDebit_rate(row.getCell((short)6).getNumericCellValue());
					hrtUnnoCost2.setCredit_rate(row.getCell((short)6).getNumericCellValue());
				}
				//提现成本
				if(null!=row.getCell(7) && !"".equals(String.valueOf(row.getCell(7)))){
					row.getCell(7).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost2.setCash_cost(row.getCell((short)7).getNumericCellValue());
				}
				billBpFileDao.saveObject(hrtUnnoCost2);
			}
			//理财
			if(null!=row.getCell(8) && !"".equals(String.valueOf(row.getCell(8))) 
			|| null!=row.getCell(9) && !"".equals(String.valueOf(row.getCell(9)))
			|| null!=row.getCell(10) && !"".equals(String.valueOf(row.getCell(10)))){
				HrtUnnoCostWModel hrtUnnoCost3 = new HrtUnnoCostWModel();
				hrtUnnoCost3.setFiid(billBpFile.getFid());
				hrtUnnoCost3.setUnno(unno);
				hrtUnnoCost3.setMachine_type(1);
				hrtUnnoCost3.setTxn_type(2);
				hrtUnnoCost3.setTxn_detail(3);
				hrtUnnoCost3.setStatus(1);
				//借费率
				if(null!=row.getCell(8) && !"".equals(String.valueOf(8))){
					row.getCell(8).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost3.setDebit_rate(row.getCell((short)8).getNumericCellValue());
				}
				//借手续费
				if(null!=row.getCell(9) && !"".equals(String.valueOf(9))){
					row.getCell(9).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost3.setDebit_feeamt(row.getCell((short)9).getNumericCellValue());
				}
				//贷费率
				if(null!=row.getCell(10) && !"".equals(String.valueOf(10))){
					row.getCell(10).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost3.setCredit_rate(row.getCell((short)10).getNumericCellValue());
				}
				billBpFileDao.saveObject(hrtUnnoCost3);
			}
			//扫码T0-微信
			if(null!=row.getCell(11) && !"".equals(String.valueOf(row.getCell(11))) 
			|| null!=row.getCell(12) && !"".equals(String.valueOf(row.getCell(12)))){
				HrtUnnoCostWModel hrtUnnoCost4 = new HrtUnnoCostWModel();
				hrtUnnoCost4.setFiid(billBpFile.getFid());
				hrtUnnoCost4.setUnno(unno);
				hrtUnnoCost4.setMachine_type(1);
				hrtUnnoCost4.setTxn_type(6);
				hrtUnnoCost4.setTxn_detail(8);
				hrtUnnoCost4.setStatus(1);
				//借贷费率
				if(null!=row.getCell(11) && !"".equals(String.valueOf(row.getCell(11)))){
					row.getCell(11).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost4.setDebit_rate(row.getCell((short)11).getNumericCellValue());
					hrtUnnoCost4.setCredit_rate(row.getCell((short)11).getNumericCellValue());
				}
				//提现成本
				if(null!=row.getCell(12) && !"".equals(String.valueOf(row.getCell(12)))){
					row.getCell(12).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost4.setCash_cost(row.getCell((short)12).getNumericCellValue());
				}
				billBpFileDao.saveObject(hrtUnnoCost4);
			}
			//扫码T0-支付宝
			if(null!=row.getCell(13) && !"".equals(String.valueOf(row.getCell(13))) 
			|| null!=row.getCell(14) && !"".equals(String.valueOf(row.getCell(14)))){
				HrtUnnoCostWModel hrtUnnoCost5 = new HrtUnnoCostWModel();
				hrtUnnoCost5.setFiid(billBpFile.getFid());
				hrtUnnoCost5.setUnno(unno);
				hrtUnnoCost5.setMachine_type(1);
				hrtUnnoCost5.setTxn_type(6);
				hrtUnnoCost5.setTxn_detail(9);
				hrtUnnoCost5.setStatus(1);
				//借贷费率
				if(null!=row.getCell(13) && !"".equals(String.valueOf(row.getCell(13)))){
					row.getCell(13).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost5.setDebit_rate(row.getCell((short)13).getNumericCellValue());
					hrtUnnoCost5.setCredit_rate(row.getCell((short)13).getNumericCellValue());
				}
				//提现成本
				if(null!=row.getCell(14) && !"".equals(String.valueOf(row.getCell(14)))){
					row.getCell(14).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost5.setCash_cost(row.getCell((short)14).getNumericCellValue());
				}
				billBpFileDao.saveObject(hrtUnnoCost5);
			}
			
			//扫码T0-银联二维码
			if(null!=row.getCell(15) && !"".equals(String.valueOf(row.getCell(15))) 
			|| null!=row.getCell(16) && !"".equals(String.valueOf(row.getCell(16)))){
				HrtUnnoCostWModel hrtUnnoCost6 = new HrtUnnoCostWModel();
				hrtUnnoCost6.setFiid(billBpFile.getFid());
				hrtUnnoCost6.setUnno(unno);
				hrtUnnoCost6.setMachine_type(1);
				hrtUnnoCost6.setTxn_type(6);
				hrtUnnoCost6.setTxn_detail(10);
				hrtUnnoCost6.setStatus(1);
				//借贷费率
				if(null!=row.getCell(15) && !"".equals(String.valueOf(row.getCell(15)))){
					row.getCell(15).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost6.setDebit_rate(row.getCell((short)15).getNumericCellValue());
					hrtUnnoCost6.setCredit_rate(row.getCell((short)15).getNumericCellValue());
				}
				//提现成本
				if(null!=row.getCell(16) && !"".equals(String.valueOf(row.getCell(16)))){
					row.getCell(16).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost6.setCash_cost(row.getCell((short)16).getNumericCellValue());
				}
				billBpFileDao.saveObject(hrtUnnoCost6);
			}
			//扫码T1-微信
			if(null!=row.getCell(17) && !"".equals(String.valueOf(row.getCell(17)))){
				HrtUnnoCostWModel hrtUnnoCost7 = new HrtUnnoCostWModel();
				hrtUnnoCost7.setFiid(billBpFile.getFid());
				hrtUnnoCost7.setUnno(unno);
				hrtUnnoCost7.setMachine_type(1);
				hrtUnnoCost7.setTxn_type(7);
				hrtUnnoCost7.setTxn_detail(8);
				hrtUnnoCost7.setStatus(1);
				//借贷费率
				row.getCell(17).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost7.setDebit_rate(row.getCell((short)17).getNumericCellValue());
				hrtUnnoCost7.setCredit_rate(row.getCell((short)17).getNumericCellValue());
				billBpFileDao.saveObject(hrtUnnoCost7);
			}
			//扫码T1-支付宝
			if(null!=row.getCell(18) && !"".equals(String.valueOf(row.getCell(18)))){
				HrtUnnoCostWModel hrtUnnoCost8 = new HrtUnnoCostWModel();
				hrtUnnoCost8.setFiid(billBpFile.getFid());
				hrtUnnoCost8.setUnno(unno);
				hrtUnnoCost8.setMachine_type(1);
				hrtUnnoCost8.setTxn_type(7);
				hrtUnnoCost8.setTxn_detail(9);
				hrtUnnoCost8.setStatus(1);
				//借贷费率
				row.getCell(18).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost8.setDebit_rate(row.getCell((short)18).getNumericCellValue());
				hrtUnnoCost8.setCredit_rate(row.getCell((short)18).getNumericCellValue());
				billBpFileDao.saveObject(hrtUnnoCost8);
			}
			//扫码T1-银联二维码
			if(null!=row.getCell(19) && !"".equals(String.valueOf(row.getCell(19)))){
				HrtUnnoCostWModel hrtUnnoCost9 = new HrtUnnoCostWModel();
				hrtUnnoCost9.setFiid(billBpFile.getFid());
				hrtUnnoCost9.setUnno(unno);
				hrtUnnoCost9.setMachine_type(1);
				hrtUnnoCost9.setTxn_type(7);
				hrtUnnoCost9.setTxn_detail(10);
				hrtUnnoCost9.setStatus(1);
				//借贷费率
				row.getCell(19).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost9.setDebit_rate(row.getCell((short)19).getNumericCellValue());
				hrtUnnoCost9.setCredit_rate(row.getCell((short)19).getNumericCellValue());
				billBpFileDao.saveObject(hrtUnnoCost9);
			}
			//代还
			if(null!=row.getCell(20) && !"".equals(String.valueOf(row.getCell(20)))){
				HrtUnnoCostWModel hrtUnnoCost10 = new HrtUnnoCostWModel();
				hrtUnnoCost10.setFiid(billBpFile.getFid());
				hrtUnnoCost10.setUnno(unno);
				hrtUnnoCost10.setMachine_type(1);
				hrtUnnoCost10.setTxn_type(3);
				hrtUnnoCost10.setTxn_detail(4);
				hrtUnnoCost10.setStatus(1);
				//借贷费率
				row.getCell(20).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost10.setDebit_rate(row.getCell((short)20).getNumericCellValue());
				hrtUnnoCost10.setCredit_rate(row.getCell((short)20).getNumericCellValue());
				billBpFileDao.saveObject(hrtUnnoCost10);
			}
			//快捷-VIP
			if(null!=row.getCell(21) && !"".equals(String.valueOf(row.getCell(21))) 
			|| null!=row.getCell(23) && !"".equals(String.valueOf(row.getCell(23)))){
				HrtUnnoCostWModel hrtUnnoCost11 = new HrtUnnoCostWModel();
				hrtUnnoCost11.setFiid(billBpFile.getFid());
				hrtUnnoCost11.setUnno(unno);
				hrtUnnoCost11.setMachine_type(1);
				hrtUnnoCost11.setTxn_type(5);
				hrtUnnoCost11.setTxn_detail(6);
				hrtUnnoCost11.setStatus(1);
				//借贷费率
				if(null!=row.getCell(21) && !"".equals(String.valueOf(row.getCell(21)))){
					row.getCell(21).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost11.setDebit_rate(row.getCell((short)21).getNumericCellValue());
					hrtUnnoCost11.setCredit_rate(row.getCell((short)21).getNumericCellValue());
				}
				//提现成本
				if(null!=row.getCell(23) && !"".equals(String.valueOf(row.getCell(23)))){
					row.getCell(23).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost11.setCash_cost(row.getCell((short)23).getNumericCellValue());
				}
				billBpFileDao.saveObject(hrtUnnoCost11);
			}
			//快捷-完美
			if(null!=row.getCell(22) && !"".equals(String.valueOf(row.getCell(22))) 
			|| null!=row.getCell(23) && !"".equals(String.valueOf(row.getCell(23)))){
				HrtUnnoCostWModel hrtUnnoCost12 = new HrtUnnoCostWModel();
				hrtUnnoCost12.setFiid(billBpFile.getFid());
				hrtUnnoCost12.setUnno(unno);
				hrtUnnoCost12.setMachine_type(1);
				hrtUnnoCost12.setTxn_type(5);
				hrtUnnoCost12.setTxn_detail(7);
				hrtUnnoCost12.setStatus(1);
				//借贷费率
				if(null!=row.getCell(22) && !"".equals(String.valueOf(row.getCell(22)))){
					row.getCell(22).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost12.setDebit_rate(row.getCell((short)22).getNumericCellValue());
					hrtUnnoCost12.setCredit_rate(row.getCell((short)22).getNumericCellValue());
				}
				//提现成本
				if(null!=row.getCell(23) && !"".equals(String.valueOf(row.getCell(23)))){
					row.getCell(23).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost12.setCash_cost(row.getCell((short)23).getNumericCellValue());
				}
				billBpFileDao.saveObject(hrtUnnoCost12);
			}
			//云闪付
			if(null!=row.getCell(24) && !"".equals(String.valueOf(row.getCell(24)))){
				HrtUnnoCostWModel hrtUnnoCost13 = new HrtUnnoCostWModel();
				hrtUnnoCost13.setFiid(billBpFile.getFid());
				hrtUnnoCost13.setUnno(unno);
				hrtUnnoCost13.setMachine_type(1);
				hrtUnnoCost13.setTxn_type(4);
				hrtUnnoCost13.setTxn_detail(5);
				hrtUnnoCost13.setStatus(1);
				//借贷费率
				row.getCell(24).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost13.setDebit_rate(row.getCell((short)24).getNumericCellValue());
				hrtUnnoCost13.setCredit_rate(row.getCell((short)24).getNumericCellValue());
				billBpFileDao.saveObject(hrtUnnoCost13);
			}
			//传统标准
			if(null!=row.getCell(25) && !"".equals(String.valueOf(row.getCell(25))) 
			|| null!=row.getCell(26) && !"".equals(String.valueOf(row.getCell(26)))
			|| null!=row.getCell(27) && !"".equals(String.valueOf(row.getCell(27)))){
				HrtUnnoCostWModel hrtUnnoCost14 = new HrtUnnoCostWModel();
				hrtUnnoCost14.setFiid(billBpFile.getFid());
				hrtUnnoCost14.setUnno(unno);
				hrtUnnoCost14.setMachine_type(2);
				hrtUnnoCost14.setTxn_type(6);
				hrtUnnoCost14.setTxn_detail(11);
				hrtUnnoCost14.setStatus(1);
				//借费率
				if(null!=row.getCell(25) && !"".equals(String.valueOf(row.getCell(25)))){
					row.getCell(25).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost14.setDebit_rate(row.getCell((short)25).getNumericCellValue());
				}
				//借手续费
				if(null!=row.getCell(26) && !"".equals(String.valueOf(row.getCell(26)))){
					row.getCell(26).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost14.setDebit_feeamt(row.getCell((short)26).getNumericCellValue());
				}
				//贷费率
				if(null!=row.getCell(27) && !"".equals(String.valueOf(row.getCell(27)))){
					row.getCell(27).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost14.setCredit_rate(row.getCell((short)27).getNumericCellValue());
				}
				billBpFileDao.saveObject(hrtUnnoCost14);
			}
			//传统优惠刷卡
			if(null!=row.getCell(28) && !"".equals(String.valueOf(row.getCell(28))) 
			|| null!=row.getCell(29) && !"".equals(String.valueOf(row.getCell(29)))
			|| null!=row.getCell(30) && !"".equals(String.valueOf(row.getCell(30)))){
				HrtUnnoCostWModel hrtUnnoCost15 = new HrtUnnoCostWModel();
				hrtUnnoCost15.setFiid(billBpFile.getFid());
				hrtUnnoCost15.setUnno(unno);
				hrtUnnoCost15.setMachine_type(2);
				hrtUnnoCost15.setTxn_type(6);
				hrtUnnoCost15.setTxn_detail(12);
				hrtUnnoCost15.setStatus(1);
				//借费率
				if(null!=row.getCell(28) && !"".equals(String.valueOf(row.getCell(28)))){
					row.getCell(28).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost15.setDebit_rate(row.getCell((short)28).getNumericCellValue());
				}
				//借手续费
				if(null!=row.getCell(29) && !"".equals(String.valueOf(row.getCell(29)))){
					row.getCell(29).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost15.setDebit_feeamt(row.getCell((short)29).getNumericCellValue());
				}
				//贷费率
				if(null!=row.getCell(30) && !"".equals(String.valueOf(row.getCell(30)))){
					row.getCell(30).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost15.setCredit_rate(row.getCell((short)30).getNumericCellValue());
				}
				billBpFileDao.saveObject(hrtUnnoCost15);
			}
			//传统减免
			if(null!=row.getCell(31) && !"".equals(String.valueOf(row.getCell(31)))){
				HrtUnnoCostWModel hrtUnnoCost16 = new HrtUnnoCostWModel();
				hrtUnnoCost16.setFiid(billBpFile.getFid());
				hrtUnnoCost16.setUnno(unno);
				hrtUnnoCost16.setMachine_type(2);
				hrtUnnoCost16.setTxn_type(6);
				hrtUnnoCost16.setTxn_detail(13);
				hrtUnnoCost16.setStatus(1);
				//借贷费率
				row.getCell(31).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost16.setDebit_rate(row.getCell((short)31).getNumericCellValue());
				hrtUnnoCost16.setCredit_rate(row.getCell((short)31).getNumericCellValue());
				billBpFileDao.saveObject(hrtUnnoCost16);
			}
			//传统-微信
			if(null!=row.getCell(32) && !"".equals(String.valueOf(row.getCell(32)))){
				HrtUnnoCostWModel hrtUnnoCost17 = new HrtUnnoCostWModel();
				hrtUnnoCost17.setFiid(billBpFile.getFid());
				hrtUnnoCost17.setUnno(unno);
				hrtUnnoCost17.setMachine_type(2);
				hrtUnnoCost17.setTxn_type(6);
				hrtUnnoCost17.setTxn_detail(8);
				hrtUnnoCost17.setStatus(1);
				//借贷费率
				row.getCell(32).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost17.setDebit_rate(row.getCell((short)32).getNumericCellValue());
				hrtUnnoCost17.setCredit_rate(row.getCell((short)32).getNumericCellValue());
				billBpFileDao.saveObject(hrtUnnoCost17);
			}
			//传统-支付宝
			if(null!=row.getCell(33) && !"".equals(String.valueOf(row.getCell(33)))){
				HrtUnnoCostWModel hrtUnnoCost18 = new HrtUnnoCostWModel();
				hrtUnnoCost18.setFiid(billBpFile.getFid());
				hrtUnnoCost18.setUnno(unno);
				hrtUnnoCost18.setMachine_type(2);
				hrtUnnoCost18.setTxn_type(6);
				hrtUnnoCost18.setTxn_detail(9);
				hrtUnnoCost18.setStatus(1);
				//借贷费率
				row.getCell(33).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost18.setDebit_rate(row.getCell((short)33).getNumericCellValue());
				hrtUnnoCost18.setCredit_rate(row.getCell((short)33).getNumericCellValue());
				billBpFileDao.saveObject(hrtUnnoCost18);
			}
			//传统-银联二维码
			if(null!=row.getCell(34) && !"".equals(String.valueOf(row.getCell(34)))){
				HrtUnnoCostWModel hrtUnnoCost19 = new HrtUnnoCostWModel();
				hrtUnnoCost19.setFiid(billBpFile.getFid());
				hrtUnnoCost19.setUnno(unno);
				hrtUnnoCost19.setMachine_type(2);
				hrtUnnoCost19.setTxn_type(6);
				hrtUnnoCost19.setTxn_detail(10);
				hrtUnnoCost19.setStatus(1);
				//借贷费率
				row.getCell(34).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost19.setDebit_rate(row.getCell((short)34).getNumericCellValue());
				hrtUnnoCost19.setCredit_rate(row.getCell((short)34).getNumericCellValue());
				billBpFileDao.saveObject(hrtUnnoCost19);
			}
		}
	}

	private List<HrtUnnoCostWModel> validateSaveCBXls(AgentUnitBean agentUnit) throws FileNotFoundException, IOException {
		List<HrtUnnoCostWModel> list = new ArrayList<HrtUnnoCostWModel>();

		File filename=new File(agentUnit.getCbUpLoadFile());
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
		HSSFSheet sheet=workbook.getSheetAt(0);
		//成本审核表
		for(int i=4;i<sheet.getLastRowNum()+1 ;i++){
			HSSFRow row=sheet.getRow(i);
			HSSFCell cell;
			cell = row.getCell((short)3);
			row.getCell(3).setCellType(cell.CELL_TYPE_STRING);
			String unno=row.getCell((short)3).getStringCellValue().trim(); //unno
			//0.72秒到+是否直发分润返现数据等
			if(null!=row.getCell(4) || null!=row.getCell(5)||null!=row.getCell(35)||null!=row.getCell(36)||null!=row.getCell(37)
					||null!=row.getCell(38)||null!=row.getCell(39)){
				HrtUnnoCostWModel hrtUnnoCost1 = new HrtUnnoCostWModel();
				hrtUnnoCost1.setUnno(unno);
				hrtUnnoCost1.setMachine_type(1);
				hrtUnnoCost1.setTxn_type(1);
				hrtUnnoCost1.setTxn_detail(2);
				hrtUnnoCost1.setStatus(1);
				//借贷费率
				if(null!=row.getCell(4) && !"".equals(String.valueOf(row.getCell(4)))){
					row.getCell(4).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setDebit_rate(row.getCell((short)4).getNumericCellValue());
					hrtUnnoCost1.setCredit_rate(row.getCell((short)4).getNumericCellValue());
				}
				//提现成本
				if(null!=row.getCell(5) && !"".equals(String.valueOf(row.getCell(5)))){
					row.getCell(5).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setCash_cost(row.getCell((short)5).getNumericCellValue());
				}
				//是否直发分润
				if(null!=row.getCell(35) && !"".equals(String.valueOf(row.getCell(35)))){
					row.getCell(35).setCellType(cell.CELL_TYPE_STRING);
					hrtUnnoCost1.setProfittype(row.getCell((short)35).getStringCellValue().equals("是")?1:0);
				}
				//分润税点
				if(null!=row.getCell(36) && !"".equals(String.valueOf(row.getCell(36)))){
					row.getCell(36).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setRate(row.getCell((short)36).getNumericCellValue());
				}
				//大蓝牙是否直发返现
				if(null!=row.getCell(37) && !"".equals(String.valueOf(row.getCell(37)))){
					row.getCell(37).setCellType(cell.CELL_TYPE_STRING);
					hrtUnnoCost1.setMpostype(row.getCell((short)37).getStringCellValue().equals("是")?1:0);
				}
				//小蓝牙是否直发返现
				if(null!=row.getCell(38) && !"".equals(String.valueOf(row.getCell(38)))){
					row.getCell(38).setCellType(cell.CELL_TYPE_STRING);
					hrtUnnoCost1.setSpostype(row.getCell((short)38).getStringCellValue().equals("是")?1:0);
				}
				//代扣机器款
				if(null!=row.getCell(39) && !"".equals(String.valueOf(row.getCell(39)))){
					row.getCell(39).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setTeramt(row.getCell((short)39).getNumericCellValue());
				}
				list.add(hrtUnnoCost1);
			}
			//0.6秒到
			if(null!=row.getCell(6) && !"".equals(String.valueOf(row.getCell(6)))
					|| null!=row.getCell(7) && !"".equals(String.valueOf(row.getCell(7)))){
				HrtUnnoCostWModel hrtUnnoCost2 = new HrtUnnoCostWModel();
				hrtUnnoCost2.setUnno(unno);
				hrtUnnoCost2.setMachine_type(1);
				hrtUnnoCost2.setTxn_type(1);
				hrtUnnoCost2.setTxn_detail(1);
				hrtUnnoCost2.setStatus(1);
				//借贷费率
				if(null!=row.getCell(6) && !"".equals(String.valueOf(row.getCell(6)))){
					row.getCell(6).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost2.setDebit_rate(row.getCell((short)6).getNumericCellValue());
					hrtUnnoCost2.setCredit_rate(row.getCell((short)6).getNumericCellValue());
				}
				//提现成本
				if(null!=row.getCell(7) && !"".equals(String.valueOf(row.getCell(7)))){
					row.getCell(7).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost2.setCash_cost(row.getCell((short)7).getNumericCellValue());
				}
				list.add(hrtUnnoCost2);
			}
			//理财
			if(null!=row.getCell(8) && !"".equals(String.valueOf(row.getCell(8)))
					|| null!=row.getCell(9) && !"".equals(String.valueOf(row.getCell(9)))
					|| null!=row.getCell(10) && !"".equals(String.valueOf(row.getCell(10)))){
				HrtUnnoCostWModel hrtUnnoCost3 = new HrtUnnoCostWModel();
				hrtUnnoCost3.setUnno(unno);
				hrtUnnoCost3.setMachine_type(1);
				hrtUnnoCost3.setTxn_type(2);
				hrtUnnoCost3.setTxn_detail(3);
				hrtUnnoCost3.setStatus(1);
				//借费率
				if(null!=row.getCell(8) && !"".equals(String.valueOf(8))){
					row.getCell(8).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost3.setDebit_rate(row.getCell((short)8).getNumericCellValue());
				}
				//借手续费
				if(null!=row.getCell(9) && !"".equals(String.valueOf(9))){
					row.getCell(9).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost3.setDebit_feeamt(row.getCell((short)9).getNumericCellValue());
				}
				//贷费率
				if(null!=row.getCell(10) && !"".equals(String.valueOf(10))){
					row.getCell(10).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost3.setCredit_rate(row.getCell((short)10).getNumericCellValue());
				}
				list.add(hrtUnnoCost3);
			}
			//扫码T0-微信
			if(null!=row.getCell(11) && !"".equals(String.valueOf(row.getCell(11)))
					|| null!=row.getCell(12) && !"".equals(String.valueOf(row.getCell(12)))){
				HrtUnnoCostWModel hrtUnnoCost4 = new HrtUnnoCostWModel();
				hrtUnnoCost4.setUnno(unno);
				hrtUnnoCost4.setMachine_type(1);
				hrtUnnoCost4.setTxn_type(6);
				hrtUnnoCost4.setTxn_detail(8);
				hrtUnnoCost4.setStatus(1);
				//借贷费率
				if(null!=row.getCell(11) && !"".equals(String.valueOf(row.getCell(11)))){
					row.getCell(11).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost4.setDebit_rate(row.getCell((short)11).getNumericCellValue());
					hrtUnnoCost4.setCredit_rate(row.getCell((short)11).getNumericCellValue());
				}
				//提现成本
				if(null!=row.getCell(12) && !"".equals(String.valueOf(row.getCell(12)))){
					row.getCell(12).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost4.setCash_cost(row.getCell((short)12).getNumericCellValue());
				}
				list.add(hrtUnnoCost4);
			}
			//扫码T0-支付宝
			if(null!=row.getCell(13) && !"".equals(String.valueOf(row.getCell(13)))
					|| null!=row.getCell(14) && !"".equals(String.valueOf(row.getCell(14)))){
				HrtUnnoCostWModel hrtUnnoCost5 = new HrtUnnoCostWModel();
				hrtUnnoCost5.setUnno(unno);
				hrtUnnoCost5.setMachine_type(1);
				hrtUnnoCost5.setTxn_type(6);
				hrtUnnoCost5.setTxn_detail(9);
				hrtUnnoCost5.setStatus(1);
				//借贷费率
				if(null!=row.getCell(13) && !"".equals(String.valueOf(row.getCell(13)))){
					row.getCell(13).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost5.setDebit_rate(row.getCell((short)13).getNumericCellValue());
					hrtUnnoCost5.setCredit_rate(row.getCell((short)13).getNumericCellValue());
				}
				//提现成本
				if(null!=row.getCell(14) && !"".equals(String.valueOf(row.getCell(14)))){
					row.getCell(14).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost5.setCash_cost(row.getCell((short)14).getNumericCellValue());
				}
				list.add(hrtUnnoCost5);
			}

			//扫码T0-银联二维码
			if(null!=row.getCell(15) && !"".equals(String.valueOf(row.getCell(15)))
					|| null!=row.getCell(16) && !"".equals(String.valueOf(row.getCell(16)))){
				HrtUnnoCostWModel hrtUnnoCost6 = new HrtUnnoCostWModel();
				hrtUnnoCost6.setUnno(unno);
				hrtUnnoCost6.setMachine_type(1);
				hrtUnnoCost6.setTxn_type(6);
				hrtUnnoCost6.setTxn_detail(10);
				hrtUnnoCost6.setStatus(1);
				//借贷费率
				if(null!=row.getCell(15) && !"".equals(String.valueOf(row.getCell(15)))){
					row.getCell(15).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost6.setDebit_rate(row.getCell((short)15).getNumericCellValue());
					hrtUnnoCost6.setCredit_rate(row.getCell((short)15).getNumericCellValue());
				}
				//提现成本
				if(null!=row.getCell(16) && !"".equals(String.valueOf(row.getCell(16)))){
					row.getCell(16).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost6.setCash_cost(row.getCell((short)16).getNumericCellValue());
				}
				list.add(hrtUnnoCost6);
			}
			//扫码T1-微信
			if(null!=row.getCell(17) && !"".equals(String.valueOf(row.getCell(17)))){
				HrtUnnoCostWModel hrtUnnoCost7 = new HrtUnnoCostWModel();
				hrtUnnoCost7.setUnno(unno);
				hrtUnnoCost7.setMachine_type(1);
				hrtUnnoCost7.setTxn_type(7);
				hrtUnnoCost7.setTxn_detail(8);
				hrtUnnoCost7.setStatus(1);
				//借贷费率
				row.getCell(17).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost7.setDebit_rate(row.getCell((short)17).getNumericCellValue());
				hrtUnnoCost7.setCredit_rate(row.getCell((short)17).getNumericCellValue());
				list.add(hrtUnnoCost7);
			}
			//扫码T1-支付宝
			if(null!=row.getCell(18) && !"".equals(String.valueOf(row.getCell(18)))){
				HrtUnnoCostWModel hrtUnnoCost8 = new HrtUnnoCostWModel();
				hrtUnnoCost8.setUnno(unno);
				hrtUnnoCost8.setMachine_type(1);
				hrtUnnoCost8.setTxn_type(7);
				hrtUnnoCost8.setTxn_detail(9);
				hrtUnnoCost8.setStatus(1);
				//借贷费率
				row.getCell(18).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost8.setDebit_rate(row.getCell((short)18).getNumericCellValue());
				hrtUnnoCost8.setCredit_rate(row.getCell((short)18).getNumericCellValue());
				list.add(hrtUnnoCost8);
			}
			//扫码T1-银联二维码
			if(null!=row.getCell(19) && !"".equals(String.valueOf(row.getCell(19)))){
				HrtUnnoCostWModel hrtUnnoCost9 = new HrtUnnoCostWModel();
				hrtUnnoCost9.setUnno(unno);
				hrtUnnoCost9.setMachine_type(1);
				hrtUnnoCost9.setTxn_type(7);
				hrtUnnoCost9.setTxn_detail(10);
				hrtUnnoCost9.setStatus(1);
				//借贷费率
				row.getCell(19).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost9.setDebit_rate(row.getCell((short)19).getNumericCellValue());
				hrtUnnoCost9.setCredit_rate(row.getCell((short)19).getNumericCellValue());
				list.add(hrtUnnoCost9);
			}
			//代还
			if(null!=row.getCell(20) && !"".equals(String.valueOf(row.getCell(20)))){
				HrtUnnoCostWModel hrtUnnoCost10 = new HrtUnnoCostWModel();
				hrtUnnoCost10.setUnno(unno);
				hrtUnnoCost10.setMachine_type(1);
				hrtUnnoCost10.setTxn_type(3);
				hrtUnnoCost10.setTxn_detail(4);
				hrtUnnoCost10.setStatus(1);
				//借贷费率
				row.getCell(20).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost10.setDebit_rate(row.getCell((short)20).getNumericCellValue());
				hrtUnnoCost10.setCredit_rate(row.getCell((short)20).getNumericCellValue());
				list.add(hrtUnnoCost10);
			}
			//快捷-VIP
			if(null!=row.getCell(21) && !"".equals(String.valueOf(row.getCell(21)))
					|| null!=row.getCell(23) && !"".equals(String.valueOf(row.getCell(23)))){
				HrtUnnoCostWModel hrtUnnoCost11 = new HrtUnnoCostWModel();
				hrtUnnoCost11.setUnno(unno);
				hrtUnnoCost11.setMachine_type(1);
				hrtUnnoCost11.setTxn_type(5);
				hrtUnnoCost11.setTxn_detail(6);
				hrtUnnoCost11.setStatus(1);
				//借贷费率
				if(null!=row.getCell(21) && !"".equals(String.valueOf(row.getCell(21)))){
					row.getCell(21).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost11.setDebit_rate(row.getCell((short)21).getNumericCellValue());
					hrtUnnoCost11.setCredit_rate(row.getCell((short)21).getNumericCellValue());
				}
				//提现成本
				if(null!=row.getCell(23) && !"".equals(String.valueOf(row.getCell(23)))){
					row.getCell(23).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost11.setCash_cost(row.getCell((short)23).getNumericCellValue());
				}
				list.add(hrtUnnoCost11);
			}
			//快捷-完美
			if(null!=row.getCell(22) && !"".equals(String.valueOf(row.getCell(22)))
					|| null!=row.getCell(23) && !"".equals(String.valueOf(row.getCell(23)))){
				HrtUnnoCostWModel hrtUnnoCost12 = new HrtUnnoCostWModel();
				hrtUnnoCost12.setUnno(unno);
				hrtUnnoCost12.setMachine_type(1);
				hrtUnnoCost12.setTxn_type(5);
				hrtUnnoCost12.setTxn_detail(7);
				hrtUnnoCost12.setStatus(1);
				//借贷费率
				if(null!=row.getCell(22) && !"".equals(String.valueOf(row.getCell(22)))){
					row.getCell(22).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost12.setDebit_rate(row.getCell((short)22).getNumericCellValue());
					hrtUnnoCost12.setCredit_rate(row.getCell((short)22).getNumericCellValue());
				}
				//提现成本
				if(null!=row.getCell(23) && !"".equals(String.valueOf(row.getCell(23)))){
					row.getCell(23).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost12.setCash_cost(row.getCell((short)23).getNumericCellValue());
				}
				list.add(hrtUnnoCost12);
			}
			//云闪付
			if(null!=row.getCell(24) && !"".equals(String.valueOf(row.getCell(24)))){
				HrtUnnoCostWModel hrtUnnoCost13 = new HrtUnnoCostWModel();
				hrtUnnoCost13.setUnno(unno);
				hrtUnnoCost13.setMachine_type(1);
				hrtUnnoCost13.setTxn_type(4);
				hrtUnnoCost13.setTxn_detail(5);
				hrtUnnoCost13.setStatus(1);
				//借贷费率
				row.getCell(24).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost13.setDebit_rate(row.getCell((short)24).getNumericCellValue());
				hrtUnnoCost13.setCredit_rate(row.getCell((short)24).getNumericCellValue());
				list.add(hrtUnnoCost13);
			}
			//传统标准
			if(null!=row.getCell(25) && !"".equals(String.valueOf(row.getCell(25)))
					|| null!=row.getCell(26) && !"".equals(String.valueOf(row.getCell(26)))
					|| null!=row.getCell(27) && !"".equals(String.valueOf(row.getCell(27)))){
				HrtUnnoCostWModel hrtUnnoCost14 = new HrtUnnoCostWModel();
				hrtUnnoCost14.setUnno(unno);
				hrtUnnoCost14.setMachine_type(2);
				hrtUnnoCost14.setTxn_type(6);
				hrtUnnoCost14.setTxn_detail(11);
				hrtUnnoCost14.setStatus(1);
				//借费率
				if(null!=row.getCell(25) && !"".equals(String.valueOf(row.getCell(25)))){
					row.getCell(25).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost14.setDebit_rate(row.getCell((short)25).getNumericCellValue());
				}
				//借手续费
				if(null!=row.getCell(26) && !"".equals(String.valueOf(row.getCell(26)))){
					row.getCell(26).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost14.setDebit_feeamt(row.getCell((short)26).getNumericCellValue());
				}
				//贷费率
				if(null!=row.getCell(27) && !"".equals(String.valueOf(row.getCell(27)))){
					row.getCell(27).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost14.setCredit_rate(row.getCell((short)27).getNumericCellValue());
				}
				list.add(hrtUnnoCost14);
			}
			//传统优惠刷卡
			if(null!=row.getCell(28) && !"".equals(String.valueOf(row.getCell(28)))
					|| null!=row.getCell(29) && !"".equals(String.valueOf(row.getCell(29)))
					|| null!=row.getCell(30) && !"".equals(String.valueOf(row.getCell(30)))){
				HrtUnnoCostWModel hrtUnnoCost15 = new HrtUnnoCostWModel();
				hrtUnnoCost15.setUnno(unno);
				hrtUnnoCost15.setMachine_type(2);
				hrtUnnoCost15.setTxn_type(6);
				hrtUnnoCost15.setTxn_detail(12);
				hrtUnnoCost15.setStatus(1);
				//借费率
				if(null!=row.getCell(28) && !"".equals(String.valueOf(row.getCell(28)))){
					row.getCell(28).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost15.setDebit_rate(row.getCell((short)28).getNumericCellValue());
				}
				//借手续费
				if(null!=row.getCell(29) && !"".equals(String.valueOf(row.getCell(29)))){
					row.getCell(29).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost15.setDebit_feeamt(row.getCell((short)29).getNumericCellValue());
				}
				//贷费率
				if(null!=row.getCell(30) && !"".equals(String.valueOf(row.getCell(30)))){
					row.getCell(30).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost15.setCredit_rate(row.getCell((short)30).getNumericCellValue());
				}
				list.add(hrtUnnoCost15);
			}
			//传统减免
			if(null!=row.getCell(31) && !"".equals(String.valueOf(row.getCell(31)))){
				HrtUnnoCostWModel hrtUnnoCost16 = new HrtUnnoCostWModel();
				hrtUnnoCost16.setUnno(unno);
				hrtUnnoCost16.setMachine_type(2);
				hrtUnnoCost16.setTxn_type(6);
				hrtUnnoCost16.setTxn_detail(13);
				hrtUnnoCost16.setStatus(1);
				//借贷费率
				row.getCell(31).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost16.setDebit_rate(row.getCell((short)31).getNumericCellValue());
				hrtUnnoCost16.setCredit_rate(row.getCell((short)31).getNumericCellValue());
				list.add(hrtUnnoCost16);
			}
			//传统-微信
			if(null!=row.getCell(32) && !"".equals(String.valueOf(row.getCell(32)))){
				HrtUnnoCostWModel hrtUnnoCost17 = new HrtUnnoCostWModel();
				hrtUnnoCost17.setUnno(unno);
				hrtUnnoCost17.setMachine_type(2);
				hrtUnnoCost17.setTxn_type(6);
				hrtUnnoCost17.setTxn_detail(8);
				hrtUnnoCost17.setStatus(1);
				//借贷费率
				row.getCell(32).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost17.setDebit_rate(row.getCell((short)32).getNumericCellValue());
				hrtUnnoCost17.setCredit_rate(row.getCell((short)32).getNumericCellValue());
				list.add(hrtUnnoCost17);
			}
			//传统-支付宝
			if(null!=row.getCell(33) && !"".equals(String.valueOf(row.getCell(33)))){
				HrtUnnoCostWModel hrtUnnoCost18 = new HrtUnnoCostWModel();
				hrtUnnoCost18.setUnno(unno);
				hrtUnnoCost18.setMachine_type(2);
				hrtUnnoCost18.setTxn_type(6);
				hrtUnnoCost18.setTxn_detail(9);
				hrtUnnoCost18.setStatus(1);
				//借贷费率
				row.getCell(33).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost18.setDebit_rate(row.getCell((short)33).getNumericCellValue());
				hrtUnnoCost18.setCredit_rate(row.getCell((short)33).getNumericCellValue());
				list.add(hrtUnnoCost18);
			}
			//传统-银联二维码
			if(null!=row.getCell(34) && !"".equals(String.valueOf(row.getCell(34)))){
				HrtUnnoCostWModel hrtUnnoCost19 = new HrtUnnoCostWModel();
				hrtUnnoCost19.setUnno(unno);
				hrtUnnoCost19.setMachine_type(2);
				hrtUnnoCost19.setTxn_type(6);
				hrtUnnoCost19.setTxn_detail(10);
				hrtUnnoCost19.setStatus(1);
				//借贷费率
				row.getCell(34).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost19.setDebit_rate(row.getCell((short)34).getNumericCellValue());
				hrtUnnoCost19.setCredit_rate(row.getCell((short)34).getNumericCellValue());
				list.add(hrtUnnoCost19);
			}
		}
		return list;
	}

	/**
	 * 成本文件上传-活动
	 * @param billBpFile
	 * @param agentUnit
	 * @param userBean
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void saveCB1Xls(BillBpFileModel billBpFile,AgentUnitBean agentUnit,UserBean userBean) throws FileNotFoundException, IOException {
		File filename=new File(agentUnit.getCbUpLoadFile());
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
		HSSFSheet sheet=workbook.getSheetAt(0);
		//成本审核表
		for(int i=1;i<sheet.getLastRowNum()+1 ;i++){
			HSSFRow row=sheet.getRow(i);
			HSSFCell cell;
			cell = row.getCell((short)3);
			row.getCell(3).setCellType(cell.CELL_TYPE_STRING);
			String unno=row.getCell((short)3).getStringCellValue().trim(); //unno
			//活动成本
			HrtUnnoCostWModel hrtUnnoCost1 = new HrtUnnoCostWModel();
			hrtUnnoCost1.setFiid(billBpFile.getFid());
			hrtUnnoCost1.setUnno(unno);
			hrtUnnoCost1.setMachine_type(1);
			hrtUnnoCost1.setTxn_type(1);
			hrtUnnoCost1.setStatus(1);
			//活动类型
			row.getCell(4).setCellType(cell.CELL_TYPE_NUMERIC);
			hrtUnnoCost1.setTxn_detail((new Double(row.getCell((short)4).getNumericCellValue())).intValue());
			Integer rebateType=hrtUnnoCost1.getTxn_detail();
			String sqlConfig="select nvl(k.subtype,0)||'' subtype from bill_purconfigure k where k.type=3 and k.valuestring='rate' and k.valueinteger=:valueinteger";
			Map mapConfig=new HashMap();
			mapConfig.put("valueinteger",rebateType);
			List<Map<String,Object>> mapList= hrtUnnoCostDao.queryObjectsBySqlListMap2(sqlConfig,mapConfig);
//			boolean subType1=configCount>0;
			String subType1=mapList.size()==1?mapList.get(0).get("SUBTYPE")+"":null;
			
			//贷记卡成本(%)
			if(null!=row.getCell(7) && !"".equals(String.valueOf(row.getCell(7)))){
				row.getCell(7).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setCredit_rate(row.getCell((short)7).getNumericCellValue());
			}
			//提现转账手续费
			if(null!=row.getCell(8) && !"".equals(String.valueOf(row.getCell(8)))){
				row.getCell(8).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setCash_cost(row.getCell((short)8).getNumericCellValue());
			}

			// @author:lxg-20190910 扫码费率拆分
			// 微信1000以上0.38费率(%)
			if(null!=row.getCell(9) && !"".equals(String.valueOf(row.getCell(9)))){
				row.getCell(9).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setWxUpRate(BigDecimal.valueOf(row.getCell((short)9).getNumericCellValue()));
			}

			// 微信1000以上0.38转账费
			if(null!=row.getCell(10) && !"".equals(String.valueOf(row.getCell(10)))){
				row.getCell(10).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setWxUpCash(BigDecimal.valueOf(row.getCell((short)10).getNumericCellValue()));
			}

			// 微信1000以上0.45费率(%)
			if(null!=row.getCell(11) && !"".equals(String.valueOf(row.getCell(11)))){
				row.getCell(11).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setWxUpRate1(BigDecimal.valueOf(row.getCell((short)11).getNumericCellValue()));
			}

			// 微信1000以上0.45转账费
			if(null!=row.getCell(12) && !"".equals(String.valueOf(row.getCell(12)))){
				row.getCell(12).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setWxUpCash1(BigDecimal.valueOf(row.getCell((short)12).getNumericCellValue()));
			}

			// 支付宝费率(%)
			if(null!=row.getCell(13) && !"".equals(String.valueOf(row.getCell(13)))){
				row.getCell(13).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setZfbRate(BigDecimal.valueOf(row.getCell((short)13).getNumericCellValue()));
			}

			// 支付宝转账费
			if(null!=row.getCell(14) && !"".equals(String.valueOf(row.getCell(14)))){
				row.getCell(14).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setZfbCash(BigDecimal.valueOf(row.getCell((short)14).getNumericCellValue()));
			}

			// 二维码费率(%)
			if(null!=row.getCell(15) && !"".equals(String.valueOf(row.getCell(15)))){
				row.getCell(15).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setEwmRate(BigDecimal.valueOf(row.getCell((short)15).getNumericCellValue()));
			}

			// 二维码转账费
			if(null!=row.getCell(16) && !"".equals(String.valueOf(row.getCell(16)))){
				row.getCell(16).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setEwmCash(BigDecimal.valueOf(row.getCell((short)16).getNumericCellValue()));
			}

			if("1".equals(subType1)) {
				if (null != row.getCell(18) && !"".equals(String.valueOf(row.getCell(18)))) {
					row.getCell(18).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setHbRate(BigDecimal.valueOf(row.getCell((short) 18).getNumericCellValue()));
				}
				if (null != row.getCell(19) && !"".equals(String.valueOf(row.getCell(19)))) {
					row.getCell(19).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setHbCash(BigDecimal.valueOf(row.getCell((short) 19).getNumericCellValue()));
				}
			}else if("2".equals(subType1)){
				if (null != row.getCell(18) && !"".equals(String.valueOf(row.getCell(18)))) {
					row.getCell(18).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setHbRate(BigDecimal.valueOf(row.getCell((short) 18).getNumericCellValue()));
				}
				if (null != row.getCell(19) && !"".equals(String.valueOf(row.getCell(19)))) {
					row.getCell(19).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setHbCash(BigDecimal.valueOf(row.getCell((short) 19).getNumericCellValue()));
				}
				//借记卡成本(%)
				if(null!=row.getCell(5) && !"".equals(String.valueOf(row.getCell(5)))){
					row.getCell(5).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setDebit_rate(row.getCell((short)5).getNumericCellValue());
				}
				//借记卡封顶值
				if(null!=row.getCell(6) && !"".equals(String.valueOf(row.getCell(6)))){
					row.getCell(6).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setDebit_feeamt(row.getCell((short)6).getNumericCellValue());
				}
				// 云闪付费率(%)
				if(null!=row.getCell(17) && !"".equals(String.valueOf(row.getCell(17)))){
					row.getCell(17).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setYsfRate(BigDecimal.valueOf(row.getCell((short)17).getNumericCellValue()));
				}
			}else{
				//借记卡成本(%)
				if(null!=row.getCell(5) && !"".equals(String.valueOf(row.getCell(5)))){
					row.getCell(5).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setDebit_rate(row.getCell((short)5).getNumericCellValue());
				}
				//借记卡封顶值
				if(null!=row.getCell(6) && !"".equals(String.valueOf(row.getCell(6)))){
					row.getCell(6).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setDebit_feeamt(row.getCell((short)6).getNumericCellValue());
				}
				// 云闪付费率(%)
				if(null!=row.getCell(17) && !"".equals(String.valueOf(row.getCell(17)))){
					row.getCell(17).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setYsfRate(BigDecimal.valueOf(row.getCell((short)17).getNumericCellValue()));
				}
			}

			billBpFileDao.saveObject(hrtUnnoCost1);
		}
	}

	private List<HrtUnnoCostWModel> validateSaveCB1Xls(AgentUnitBean agentUnit) throws FileNotFoundException, IOException {
		List<HrtUnnoCostWModel> list = new ArrayList<HrtUnnoCostWModel>();
		File filename=new File(agentUnit.getCbUpLoadFile());
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
		HSSFSheet sheet=workbook.getSheetAt(0);
		//成本审核表
		for(int i=1;i<sheet.getLastRowNum()+1 ;i++){
			HSSFRow row=sheet.getRow(i);
			HSSFCell cell;
			cell = row.getCell((short)3);
			row.getCell(3).setCellType(cell.CELL_TYPE_STRING);
			String unno=row.getCell((short)3).getStringCellValue().trim(); //unno
			//活动成本
			HrtUnnoCostWModel hrtUnnoCost1 = new HrtUnnoCostWModel();
			hrtUnnoCost1.setUnno(unno);
			hrtUnnoCost1.setMachine_type(1);
			hrtUnnoCost1.setTxn_type(1);
			hrtUnnoCost1.setStatus(1);
			//活动类型
			row.getCell(4).setCellType(cell.CELL_TYPE_NUMERIC);
			hrtUnnoCost1.setTxn_detail((new Double(row.getCell((short)4).getNumericCellValue())).intValue());
			Integer rebateType=hrtUnnoCost1.getTxn_detail();
			String sqlConfig="select nvl(k.subtype,0)||'' subtype from  bill_purconfigure k where k.type=3 and k.valuestring='rate' and k.valueinteger=:valueinteger";
			Map mapConfig=new HashMap();
			mapConfig.put("valueinteger",rebateType);
			List<Map<String,Object>> mapList= hrtUnnoCostDao.queryObjectsBySqlListMap2(sqlConfig,mapConfig);
//			boolean subType1=configCount>0;
			String subType1=mapList.size()==1?mapList.get(0).get("SUBTYPE")+"":null;

			//贷记卡成本(%)
			if(null!=row.getCell(7) && !"".equals(String.valueOf(row.getCell(7)))){
				row.getCell(7).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setCredit_rate(row.getCell((short)7).getNumericCellValue());
			}
			//提现转账手续费
			if(null!=row.getCell(8) && !"".equals(String.valueOf(row.getCell(8)))){
				row.getCell(8).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setCash_cost(row.getCell((short)8).getNumericCellValue());
			}

			// @author:lxg-20190910 扫码费率拆分
			// 微信1000以上0.38费率(%)
			if(null!=row.getCell(9) && !"".equals(String.valueOf(row.getCell(9)))){
				row.getCell(9).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setWxUpRate(BigDecimal.valueOf(row.getCell((short)9).getNumericCellValue()));
			}

			// 微信1000以上0.38转账费
			if(null!=row.getCell(10) && !"".equals(String.valueOf(row.getCell(10)))){
				row.getCell(10).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setWxUpCash(BigDecimal.valueOf(row.getCell((short)10).getNumericCellValue()));
			}

			// 微信1000以上0.45费率(%)
			if(null!=row.getCell(11) && !"".equals(String.valueOf(row.getCell(11)))){
				row.getCell(11).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setWxUpRate1(BigDecimal.valueOf(row.getCell((short)11).getNumericCellValue()));
			}

			// 微信1000以上0.45转账费
			if(null!=row.getCell(12) && !"".equals(String.valueOf(row.getCell(12)))){
				row.getCell(12).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setWxUpCash1(BigDecimal.valueOf(row.getCell((short)12).getNumericCellValue()));
			}

			// 支付宝费率(%)
			if(null!=row.getCell(13) && !"".equals(String.valueOf(row.getCell(13)))){
				row.getCell(13).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setZfbRate(BigDecimal.valueOf(row.getCell((short)13).getNumericCellValue()));
			}

			// 支付宝转账费
			if(null!=row.getCell(14) && !"".equals(String.valueOf(row.getCell(14)))){
				row.getCell(14).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setZfbCash(BigDecimal.valueOf(row.getCell((short)14).getNumericCellValue()));
			}

			// 二维码费率(%)
			if(null!=row.getCell(15) && !"".equals(String.valueOf(row.getCell(15)))){
				row.getCell(15).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setEwmRate(BigDecimal.valueOf(row.getCell((short)15).getNumericCellValue()));
			}

			// 二维码转账费
			if(null!=row.getCell(16) && !"".equals(String.valueOf(row.getCell(16)))){
				row.getCell(16).setCellType(cell.CELL_TYPE_NUMERIC);
				hrtUnnoCost1.setEwmCash(BigDecimal.valueOf(row.getCell((short)16).getNumericCellValue()));
			}

			if("1".equals(subType1)) {
				if (null != row.getCell(18) && !"".equals(String.valueOf(row.getCell(18)))) {
					row.getCell(18).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setHbRate(BigDecimal.valueOf(row.getCell((short) 18).getNumericCellValue()));
				}
				if (null != row.getCell(19) && !"".equals(String.valueOf(row.getCell(19)))) {
					row.getCell(19).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setHbCash(BigDecimal.valueOf(row.getCell((short) 19).getNumericCellValue()));
				}
			}else if("2".equals(subType1)){
				if (null != row.getCell(18) && !"".equals(String.valueOf(row.getCell(18)))) {
					row.getCell(18).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setHbRate(BigDecimal.valueOf(row.getCell((short) 18).getNumericCellValue()));
				}
				if (null != row.getCell(19) && !"".equals(String.valueOf(row.getCell(19)))) {
					row.getCell(19).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setHbCash(BigDecimal.valueOf(row.getCell((short) 19).getNumericCellValue()));
				}
				//借记卡成本(%)
				if(null!=row.getCell(5) && !"".equals(String.valueOf(row.getCell(5)))){
					row.getCell(5).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setDebit_rate(row.getCell((short)5).getNumericCellValue());
				}
				//借记卡封顶值
				if(null!=row.getCell(6) && !"".equals(String.valueOf(row.getCell(6)))){
					row.getCell(6).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setDebit_feeamt(row.getCell((short)6).getNumericCellValue());
				}
				// 云闪付费率(%)
				if(null!=row.getCell(17) && !"".equals(String.valueOf(row.getCell(17)))){
					row.getCell(17).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setYsfRate(BigDecimal.valueOf(row.getCell((short)17).getNumericCellValue()));
				}
			}else{
				//借记卡成本(%)
				if(null!=row.getCell(5) && !"".equals(String.valueOf(row.getCell(5)))){
					row.getCell(5).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setDebit_rate(row.getCell((short)5).getNumericCellValue());
				}
				//借记卡封顶值
				if(null!=row.getCell(6) && !"".equals(String.valueOf(row.getCell(6)))){
					row.getCell(6).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setDebit_feeamt(row.getCell((short)6).getNumericCellValue());
				}
				// 云闪付费率(%)
				if(null!=row.getCell(17) && !"".equals(String.valueOf(row.getCell(17)))){
					row.getCell(17).setCellType(cell.CELL_TYPE_NUMERIC);
					hrtUnnoCost1.setYsfRate(BigDecimal.valueOf(row.getCell((short)17).getNumericCellValue()));
				}
			}
			list.add(hrtUnnoCost1);
		}
		return list;
	}

	/**
	 * 查询分润照片文件存储路径
	 */
	public String querySysParam(String title) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("TITLE", title);
		String sql="select upload_path from sys_param where title=:TITLE ";
		List<Map<String,Object>> list = billBpFileDao.executeSql2(sql, map);
		return list.get(0).get("UPLOAD_PATH").toString();
	}
	/**
	 * 代理结算成本导出
	 */
	public List<Map<String, String>> listUnnoCost(String costType,String fid,UserBean userSession) {
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("fiid", fid);
		String sql="";
		if("1".equals(costType)){
			sql = "select a.t1,a.t2,a.t3,a.t4,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 1 "+
			" and w.txn_detail = 2 and w.fiid=a.fiid and w.unno=a.t4),'')t5,"+
			" nvl((select w.cash_cost from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 1 "+
			" and w.txn_detail = 2 and w.fiid=a.fiid and w.unno=a.t4),'')t6,"+
			" (select case when w.profittype = 1 then '是' else '否' end from hrt_unno_cost_w w where w.machine_type = 1 "+
			" and w.txn_type = 1 and w.txn_detail = 2 and w.fiid=a.fiid and w.unno=a.t4)t36,"+
			" nvl((select w.rate from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 1 "+
			" and w.txn_detail = 2 and w.fiid=a.fiid and w.unno=a.t4),'')t37,"+
			" (select case when w.mpostype = 1 then '是' else '否' end from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 1 "+
			" and w.txn_detail = 2 and w.fiid=a.fiid and w.unno=a.t4)t38,"+
			" (select case when w.spostype = 1 then '是' else '否' end from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 1 "+
			" and w.txn_detail = 2 and w.fiid=a.fiid and w.unno=a.t4)t39,"+
			" nvl((select w.teramt from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 1 "+
			" and w.txn_detail = 2 and w.fiid=a.fiid and w.unno=a.t4),'')t40,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 1 "+
			" and w.txn_detail = 1 and w.fiid=a.fiid and w.unno=a.t4),'')t7,"+
			" nvl((select w.cash_cost from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 1 "+
			" and w.txn_detail = 1 and w.fiid=a.fiid and w.unno=a.t4),'')t8,"+
			" nvl((select w.debit_rate from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 2 "+
			" and w.txn_detail = 3 and w.fiid=a.fiid and w.unno=a.t4),'')t9,"+
			" nvl((select w.debit_feeamt from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 2 "+
			" and w.txn_detail = 3 and w.fiid=a.fiid and w.unno=a.t4),'')t10,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 2 "+
			" and w.txn_detail = 3 and w.fiid=a.fiid and w.unno=a.t4),'')t11,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 6 "+
			" and w.txn_detail = 8 and w.fiid=a.fiid and w.unno=a.t4),'')t12,"+
			" nvl((select w.cash_cost from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 6 "+
			" and w.txn_detail = 8 and w.fiid=a.fiid and w.unno=a.t4),'')t13,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 6 "+
			" and w.txn_detail = 9 and w.fiid=a.fiid and w.unno=a.t4),'')t14,"+
			" nvl((select w.cash_cost from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 6 "+
			" and w.txn_detail = 9 and w.fiid=a.fiid and w.unno=a.t4),'')t15,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 6 "+
			" and w.txn_detail = 10 and w.fiid=a.fiid and w.unno=a.t4),'')t16,"+
			" nvl((select w.cash_cost from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 6 "+
			" and w.txn_detail = 10 and w.fiid=a.fiid and w.unno=a.t4),'')t17,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 7 "+
			" and w.txn_detail = 8 and w.fiid=a.fiid and w.unno=a.t4),'')t18,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 7 "+
			" and w.txn_detail = 9 and w.fiid=a.fiid and w.unno=a.t4),'')t19,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 7 "+
			" and w.txn_detail = 10 and w.fiid=a.fiid and w.unno=a.t4),'')t20,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 3 "+
			" and w.txn_detail = 4 and w.fiid=a.fiid and w.unno=a.t4),'')t21,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 5 "+
			" and w.txn_detail = 6 and w.fiid=a.fiid and w.unno=a.t4),'')t22,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 5 "+
			" and w.txn_detail = 7 and w.fiid=a.fiid and w.unno=a.t4),'')t23,"+
			" nvl((select w.cash_cost from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 5 "+
			" and w.txn_detail = 7 and w.fiid=a.fiid and w.unno=a.t4),'')t24,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 1 and w.txn_type = 4 "+
			" and w.txn_detail = 5 and w.fiid=a.fiid and w.unno=a.t4),'')t25,"+
			" nvl((select w.debit_rate from hrt_unno_cost_w w where w.machine_type = 2 and w.txn_type = 6 "+
			" and w.txn_detail = 11 and w.fiid=a.fiid and w.unno=a.t4),'')t26,"+
			" nvl((select w.debit_feeamt from hrt_unno_cost_w w where w.machine_type = 2 and w.txn_type = 6 "+
			" and w.txn_detail = 11 and w.fiid=a.fiid and w.unno=a.t4),'')t27,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 2 and w.txn_type = 6 "+
			" and w.txn_detail = 11 and w.fiid=a.fiid and w.unno=a.t4),'')t28,"+
			" nvl((select w.debit_rate from hrt_unno_cost_w w where w.machine_type = 2 and w.txn_type = 6 "+
			" and w.txn_detail = 12 and w.fiid=a.fiid and w.unno=a.t4),'')t29,"+
			" nvl((select w.debit_feeamt from hrt_unno_cost_w w where w.machine_type = 2 and w.txn_type = 6 "+
			" and w.txn_detail = 12 and w.fiid=a.fiid and w.unno=a.t4),'')t30,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 2 and w.txn_type = 6 "+
			" and w.txn_detail = 12 and w.fiid=a.fiid and w.unno=a.t4),'')t31,"+
			" nvl((select w.debit_rate from hrt_unno_cost_w w where w.machine_type = 2 and w.txn_type = 6 "+
			" and w.txn_detail = 13 and w.fiid=a.fiid and w.unno=a.t4),'')t32,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 2 and w.txn_type = 6 "+
			" and w.txn_detail = 8 and w.fiid=a.fiid and w.unno=a.t4),'')t33,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 2 and w.txn_type = 6 "+
			" and w.txn_detail = 9 and w.fiid=a.fiid and w.unno=a.t4),'')t34,"+
			" nvl((select w.credit_rate from hrt_unno_cost_w w where w.machine_type = 2 and w.txn_type = 6 "+
			" and w.txn_detail = 10 and w.fiid=a.fiid and w.unno=a.t4),'')t35 from ( "+
			" select distinct s.upper_unit as t1,(select ss.un_name from sys_unit ss where ss.unno=s.upper_unit)t2,s.un_name t3, s.unno t4,w.fiid "+
			" from hrt_unno_cost_w w, Sys_Unit s where w.unno = s.unno and w.fiid =:fiid "+
			" )a";
		}else{
			// @author:lxg-20190910 扫码费率拆分
			sql = "select s.upper_unit as t1,(select ss.un_name from sys_unit ss where ss.unno=s.upper_unit)t2,s.un_name t3, s.unno t4,"+
            " w.txn_detail as t5,w.debit_rate as t6,w.debit_feeamt as t7,w.credit_rate as t8,w.cash_cost as t9," +
			" w.wx_uprate as t10,w.wx_upcash as t11,w.wx_uprate1 as t12,w.wx_upcash1 as t13," +
			" w.zfb_rate as t14,w.zfb_cash as t15,w.ewm_rate as t16,w.ewm_cash as t17,ysf_rate as t18,hb_rate as t19,hb_cash as t20 "+
			" from hrt_unno_cost_w w, Sys_Unit s where w.unno = s.unno and w.fiid =:fiid ";
		}
		List<Map<String, String>> list=billBpFileDao.executeSql(sql,map);
		return list;
	}
	/**
	 * 代理结算成本退回
	 */
	public boolean updateUnnoCostTH(String fid,AgentUnitBean agentUnit,UserBean userSession) throws Exception{
		BillBpFileModel billBpFile=(BillBpFileModel) billBpFileDao.getObjectByID(obj(BillBpFileModel.class), Integer.parseInt(agentUnit.getFID()));
		billBpFile.setStatus(-1);
		billBpFile.setRemarks(agentUnit.getRemarks());
		billBpFile.setAby(userSession.getUserName());
		billBpFile.setAdate(new Date());
		billBpFileDao.updateObject(billBpFile);
		return true;
	}
	/**
	 * 代理成本变更通过
	 */
	@Override
	public JsonBean updateUnnoCostTY(AgentUnitBean agentUnit, UserBean user) {
		JsonBean rsjson = new JsonBean();
		if (agentUnit == null || agentUnit.getFID() == null || "".equals(agentUnit.getFID())) {
			log.info("文件fid为空，参数错误");
			rsjson.setSuccess(false);
			rsjson.setMsg("文件fid为空，参数错误");
			return rsjson;
		}
		// 修改文件
		BillBpFileModel billBpFile=(BillBpFileModel) billBpFileDao.getObjectByID(obj(BillBpFileModel.class), Integer.parseInt(agentUnit.getFID()));
		billBpFile.setStatus(1);
		billBpFile.setAby(user.getUserName());
		billBpFile.setAdate(new Date());
		billBpFileDao.updateObject(billBpFile);

        // 主表只将新增的数据新增到实时表中，下月生效记录表数据，都做处理
		// 修改主表
		StringBuilder sql = new StringBuilder();
		StringBuilder sqlN = new StringBuilder();
        // @author:lxg-20190911 扫码费率拆分
		sql.append(" merge into hrt_unno_cost a USING (SELECT t2.buid, t1.* FROM hrt_unno_cost_w t1 LEFT JOIN bill_agentunitinfo t2 ON t1.unno = ");
		sql.append(" t2.unno WHERE t1.fiid =:fiid) b ON (b.unno = a.unno AND b.machine_type = a.machine_type AND b.txn_type = a.txn_type AND ");
		sql.append(" b.txn_detail = a.txn_detail) ");
		sql.append(" WHEN NOT MATCHED THEN insert (unno, debit_rate, debit_feeamt, ");
		sql.append(" credit_rate, cash_cost, cash_rate, machine_type, txn_type, txn_detail, status, flag, operate_type, cdate, cby, lmdate, lmby, ");
		sql.append(" buid,WX_UPRATE,WX_UPCASH,WX_UPRATE1,WX_UPCASH1,ZFB_RATE,ZFB_CASH,EWM_RATE,EWM_CASH,YSF_RATE) VALUES (b.unno, b.debit_rate, b.debit_feeamt, b.credit_rate, b.cash_cost, b.cash_rate, b.machine_type, b.txn_type, ");
		sql.append(" b.txn_detail, '1', 0, 1, sysdate, :cby, sysdate, :lmby,b.buid,b.WX_UPRATE,b.WX_UPCASH,b.WX_UPRATE1,b.WX_UPCASH1,b.ZFB_RATE,b.ZFB_CASH,b.EWM_RATE,b.EWM_CASH,b.YSF_RATE)");

		sqlN.append(" merge into hrt_unno_cost_n a USING (SELECT t2.buid, t1.* FROM hrt_unno_cost_w t1 LEFT JOIN bill_agentunitinfo t2 ON t1.unno = ");
		sqlN.append(" t2.unno WHERE t1.fiid =:fiid) b ON (b.unno = a.unno AND b.machine_type = a.machine_type AND b.txn_type = a.txn_type AND ");
		sqlN.append(" b.txn_detail = a.txn_detail) WHEN matched THEN UPDATE SET a.flag = 0, a.operate_type = 0, a.lmdate = sysdate, a.lmby = :lmby, ");
		sqlN.append(" a.debit_rate = CASE WHEN b.debit_rate is NULL THEN a.debit_rate ELSE b.debit_rate end, a.debit_feeamt = CASE WHEN b.debit_feeamt ");
		sqlN.append(" is NULL THEN a.debit_feeamt ELSE b.debit_feeamt end, a.credit_rate = CASE WHEN b.credit_rate is NULL THEN a.credit_rate ELSE ");
		sqlN.append(" b.credit_rate end, a.cash_cost = CASE WHEN b.cash_cost is NULL THEN a.cash_cost ELSE b.cash_cost END , a.cash_rate = CASE WHEN ");
		sqlN.append(" b.cash_rate is NULL THEN a.cash_rate ELSE b.cash_rate END,");
		sqlN.append(" a.WX_UPRATE = CASE WHEN b.WX_UPRATE is NULL THEN a.WX_UPRATE ELSE b.WX_UPRATE END,");
		sqlN.append(" a.WX_UPCASH = CASE WHEN b.WX_UPCASH is NULL THEN a.WX_UPCASH ELSE b.WX_UPCASH END,");
		sqlN.append(" a.WX_UPRATE1 = CASE WHEN b.WX_UPRATE1 is NULL THEN a.WX_UPRATE1 ELSE b.WX_UPRATE1 END,");
		sqlN.append(" a.WX_UPCASH1 = CASE WHEN b.WX_UPCASH1 is NULL THEN a.WX_UPCASH1 ELSE b.WX_UPCASH1 END,");
		sqlN.append(" a.ZFB_RATE = CASE WHEN b.ZFB_RATE is NULL THEN a.ZFB_RATE ELSE b.ZFB_RATE END,");
		sqlN.append(" a.ZFB_CASH = CASE WHEN b.ZFB_CASH is NULL THEN a.ZFB_CASH ELSE b.ZFB_CASH END,");
		sqlN.append(" a.EWM_RATE = CASE WHEN b.EWM_RATE is NULL THEN a.EWM_RATE ELSE b.EWM_RATE END,");
		sqlN.append(" a.EWM_CASH = CASE WHEN b.EWM_CASH is NULL THEN a.EWM_CASH ELSE b.EWM_CASH END,");
		sqlN.append(" a.YSF_RATE = CASE WHEN b.YSF_RATE is NULL THEN a.YSF_RATE ELSE b.YSF_RATE END, ");
		sqlN.append(" a.HB_RATE = CASE WHEN b.HB_RATE is NULL THEN a.HB_RATE ELSE b.HB_RATE END, ");
		sqlN.append(" a.HB_CASH = CASE WHEN b.HB_CASH is NULL THEN a.HB_CASH ELSE b.HB_CASH END ");
		sqlN.append(" WHEN NOT MATCHED THEN insert (unno, debit_rate, debit_feeamt, ");
		sqlN.append(" credit_rate, cash_cost, cash_rate, machine_type, txn_type, txn_detail, status, flag, operate_type, cdate, cby, lmdate, lmby, ");
		sqlN.append(" buid,WX_UPRATE,WX_UPCASH,WX_UPRATE1,WX_UPCASH1,ZFB_RATE,ZFB_CASH,EWM_RATE,EWM_CASH,YSF_RATE) VALUES (b.unno, b.debit_rate, b.debit_feeamt, b.credit_rate, b.cash_cost, b.cash_rate, b.machine_type, b.txn_type, ");
		sqlN.append(" b.txn_detail, '1', 0, 1, sysdate, :cby, sysdate, :lmby,b.buid,b.WX_UPRATE,b.WX_UPCASH,b.WX_UPRATE1,b.WX_UPCASH1,b.ZFB_RATE,b.ZFB_CASH,b.EWM_RATE,b.EWM_CASH,b.YSF_RATE)");

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("lmby", user.getLoginName());
		param.put("cby", user.getLoginName());
		param.put("fiid",Integer.valueOf(agentUnit.getFID()));
		Integer rs = billBpFileDao.executeSqlUpdate(sql.toString(), param);
		Integer rsN = billBpFileDao.executeSqlUpdate(sqlN.toString(), param);
		log.info("修改成功，条数rs:"+rs+";rsN:"+rsN);
		rsjson.setSuccess(true);
		rsjson.setMsg("变更成功");
		return rsjson;
	}

	public Map getYunYingHrtUnnoCost(String unno,Integer type){
		Map map = new HashMap();
		List<HrtUnnoCostModel> yunYingHrt=new ArrayList<HrtUnnoCostModel>();
		List<HrtUnnoCostNModel> yunYingHrtN=new ArrayList<HrtUnnoCostNModel>();
		// @author:lxg-20190428 添加人工成本校验
		String checkHql="from HrtUnnoCostModel r where r.status=-1 and r.unno ='"+(HrtCostConstant.RGSZ_PREX+unno)+"'";
		List<HrtUnnoCostModel> checkyunYingHrt = hrtUnnoCostDao.queryObjectsByHqlList(checkHql);
		if(checkyunYingHrt.size()>0){
			yunYingHrt=checkyunYingHrt;
		}else{
			// @author:lxg-20200413 代理下月生效成本表添加
			if(type==1){
				if(DateTools.isFirstDay()){
					String hql = "from HrtUnnoCostNModel r where r.status=1 and r.unno ='" + unno + "'";
					yunYingHrtN = hrtUnnoCostNDao.queryObjectsByHqlList(hql);
					for (HrtUnnoCostNModel hrtUnnoCostNModel : yunYingHrtN) {
						HrtUnnoCostModel mm = new HrtUnnoCostModel();
						BeanUtils.copyProperties(hrtUnnoCostNModel,mm);
						yunYingHrt.add(mm);
					}
				}else {
					String hql = "from HrtUnnoCostModel r where r.status=1 and r.unno ='" + unno + "'";
					yunYingHrt = hrtUnnoCostDao.queryObjectsByHqlList(hql);
				}
			}else if(type==2){
				// 修改时取下月生效表
				String hql = "from HrtUnnoCostNModel r where r.status=1 and r.unno ='" + unno + "'";
				yunYingHrtN = hrtUnnoCostNDao.queryObjectsByHqlList(hql);
				for (HrtUnnoCostNModel hrtUnnoCostNModel : yunYingHrtN) {
					HrtUnnoCostModel mm = new HrtUnnoCostModel();
					BeanUtils.copyProperties(hrtUnnoCostNModel,mm);
					yunYingHrt.add(mm);
				}
			}
		}

		for(HrtUnnoCostModel t:yunYingHrt){
			String prefix = t.getMachineType()+"|"+t.getTxnType()+"|"+t.getTxnDetail()+"|";
//			String value = t.getDebitRate()+"|"+t.getDebitFeeamt()+"|"+t.getCreditRate()+"|"+t.getCashCost()+"|"+t.getCashRate();
			if(null!=t.getCashCost()){
				map.put(prefix+"cashCost",t.getCashCost());
			}
			if(null!=t.getCashRate()){
				map.put(prefix+"cashRate",t.getCashRate());
			}
			if(null!=t.getDebitRate()){
				map.put(prefix+"debitRate",t.getDebitRate());
			}
			if(null!=t.getDebitFeeamt()){
				map.put(prefix+"debitFeeamt",t.getDebitFeeamt());
			}
			if(null!=t.getCreditRate()){
				map.put(prefix+"creditRate",t.getCreditRate());
			}
			if(null!=t.getWxUpRate()){
				map.put(prefix+"wxUpRate",t.getWxUpRate());
			}
			if(null!=t.getWxUpCash()){
				map.put(prefix+"wxUpCash",t.getWxUpCash());
			}
			if(null!=t.getWxUpRate1()){
				map.put(prefix+"wxUpRate1",t.getWxUpRate1());
			}
			if(null!=t.getWxUpCash()){
				map.put(prefix+"wxUpCash1",t.getWxUpCash1());
			}
			if(null!=t.getZfbRate()){
				map.put(prefix+"zfbRate",t.getZfbRate());
			}
			if(null!=t.getZfbCash()){
				map.put(prefix+"zfbCash",t.getZfbCash());
			}
			if(null!=t.getEwmRate()){
				map.put(prefix+"ewmRate",t.getEwmRate());
			}
			if(null!=t.getEwmCash()){
				map.put(prefix+"ewmCash",t.getEwmCash());
			}
			if(null!=t.getYsfRate()){
				map.put(prefix+"ysfRate",t.getYsfRate());
			}
			if(null!=t.getHbRate()){
				map.put(prefix+"hbRate",t.getHbRate());
			}
			if(null!=t.getHbCash()){
				map.put(prefix+"hbCash",t.getHbCash());
			}
		}
		return map;
	}

	@Override
	public String validateCostByLimit(AgentUnitBean agentUnit, UserBean user){
		if (user.getUnitLvl() != null && user.getUnitLvl()==1 && StringUtils.isNotEmpty(agentUnit.getCostData())) {
			List<HrtUnnoCostBean> costList = JSONObject.parseArray(agentUnit.getCostData(), HrtUnnoCostBean.class);
//			List<Object> models = new ArrayList<Object>();
			for (HrtUnnoCostBean bean : costList) {
				HrtUnnoCostModel model = new HrtUnnoCostModel();
				BeanUtils.copyProperties(bean, model);
//				model.setBuid(buid);
				model.setStatus(0);
				model.setFlag(0);
				model.setOperateType(1);
				model.setCdate(new Date());
				model.setCby(user.getLoginName());
				model.setLmDate(new Date());
				model.setLmby(user.getLoginName());
//				models.add(model);

				String limitValidate = validateLimitCost(model);
				if(StringUtils.isNotEmpty(limitValidate)){
					return limitValidate;
				}
			}
		}
		return null;
	}

	private String validateLimitCost(HrtUnnoCostModel t){
		StringBuilder errTip=new StringBuilder();
		Map hrtMap = getValidateCostMap(t);
		Map limitMap=getDefaultLimitHrtCost();
		if(!hrtMap.isEmpty() && !limitMap.isEmpty()){
			Set<Map.Entry> hrtSet = hrtMap.entrySet();
			for (Map.Entry entry : hrtSet) {
				String hrtKey = (String) entry.getKey();
				String hrtValue = entry.getValue().toString();
				boolean allHasKey=hrtKey!=null && limitMap.containsKey(hrtKey) && limitMap.get(hrtKey)!=null;
				if(allHasKey){
					String limitValue = limitMap.get(hrtKey).toString();
					if(StringUtils.isNotEmpty(hrtValue) && StringUtils.isNotEmpty(limitValue) &&
                            new BigDecimal(hrtValue).compareTo(new BigDecimal(limitValue))>0){
						String machineType = HrtCostConstant.getValueByTypeAndKey(HrtCostConstant.MACHINE_TYPE,t.getMachineType()+"");
						boolean isAct=false;
						String txnType = HrtCostConstant.getValueByTypeAndKey(HrtCostConstant.TXN_TYPE,t.getTxnType()+"");
						Integer rebateType=t.getTxnDetail();
						String txnDetail = HrtCostConstant.getValueByTypeAndKey(HrtCostConstant.TXN_DETAIL,t.getTxnDetail()+"");
						if(rebateType!=null
                                && Integer.valueOf(rebateType)!=null && Integer.valueOf(rebateType).compareTo(20)>0){
                            isAct=true;
                        }
						if(isAct){
                            if(StringUtils.isNotEmpty(t.getUnno())){
                                errTip.append("机构号").append(t.getUnno()).append("\n");
                            }
							errTip.append("活动").append(rebateType).append("\n");
//							errTip.append(machineType)
//									.append("-").append(txnType)
//									.append("-").append(txnDetail)
							errTip.append(getValidateFiledName(hrtKey,isAct))
									.append("成本高于商户终端成本，请修改");
						}else{
                            if(StringUtils.isNotEmpty(t.getUnno())){
                                errTip.append("机构号").append(t.getUnno()).append("\n");
                            }
							errTip.append(machineType)
									.append("-").append(txnType)
									.append("-").append(txnDetail)
									.append(getValidateFiledName(hrtKey,isAct)).append("\n")
									.append("成本高于商户终端成本，请修改");
						}

						return errTip.toString();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 规则字段名称获取
	 * @param key 校验key
	 * @param flag 是否为活动成本
	 * @return
	 */
	private String getValidateFiledName(String key,boolean flag){
		if(StringUtils.isNotEmpty(key)){
			if(flag){
				if(key.contains("|cashCost")){
					return "扫码1000以下0.38转账费";
				}else if(key.contains("|cashRate")){
					return "";
				}else if(key.contains("|debitRate")){
					return "刷卡";
				}else if(key.contains("|debitFeeamt")){
					return "刷卡提现";
				}else if(key.contains("|creditRate")){
					return "扫码1000以下0.38费率";
				}
			}else{
				if(key.contains("|cashCost")){
					return "提现";
				}else if(key.contains("|cashRate")){
					return "提现手续费";
				}else if(key.contains("|debitRate")){
					return "借记卡";
				}else if(key.contains("|debitFeeamt")){
					return "借记卡封项手续费";
				}else if(key.contains("|creditRate")){
					return "贷记卡";
				}
			}

			// 共用字段
			if(key.contains("|wxUpRate1")){
				return "扫码1000以上0.45费率";
			}else if(key.contains("|wxUpCash1")){
				return "扫码1000以上0.45转账费";
			}else if(key.contains("|wxUpRate")){
				return "扫码1000以上0.38费率";
			}else if(key.contains("|wxUpCash")){
				return "扫码1000以上0.38转账费";
			}else if(key.contains("|zfbRate")){
				return "扫码1000以下0.45费率";
			}else if(key.contains("|zfbCash")){
				return "扫码1000以下0.45手续费";
			}else if(key.contains("|ewmRate")){
				return "银联二维码费率";
			}else if(key.contains("|ewmCash")){
				return "银联二维码转账费";
			}else if(key.contains("|ysfRate")){
				return "云闪付费率";
			}else if(key.contains("|hbRate")){
				return "花呗";
			}else if(key.contains("|hbCash")){
				return "花呗提现";
			}
		}
		return null;
	}

	/**
	 * 页面传过来的成本转化为校验的map
	 * @param t
	 * @return
	 */
	private Map getValidateCostMap(HrtUnnoCostModel t){
		Map map = new HashMap();
		if(t!=null) {
			// 上限的活动成本限制只设置20,将活动都与20比较
			String limtTxnDetail=t.getTxnDetail()!=null && t.getTxnDetail().compareTo(20)>0?"20":t.getTxnDetail()+"";
			String prefix = t.getMachineType() + "|" + t.getTxnType() + "|" + limtTxnDetail + "|";
			if (null != t.getCashCost()) {
				map.put(prefix + "cashCost", t.getCashCost());
			}
			if (null != t.getCashRate()) {
				map.put(prefix + "cashRate", t.getCashRate());
			}
			if (null != t.getDebitRate()) {
				map.put(prefix + "debitRate", t.getDebitRate());
			}
			if (null != t.getDebitFeeamt()) {
				map.put(prefix + "debitFeeamt", t.getDebitFeeamt());
			}
			if (null != t.getCreditRate()) {
				map.put(prefix + "creditRate", t.getCreditRate());
			}
			if (null != t.getWxUpRate()) {
				map.put(prefix + "wxUpRate", t.getWxUpRate());
			}
			if (null != t.getWxUpCash()) {
				map.put(prefix + "wxUpCash", t.getWxUpCash());
			}
			if (null != t.getWxUpRate1()) {
				map.put(prefix + "wxUpRate1", t.getWxUpRate1());
			}
			if (null != t.getWxUpCash()) {
				map.put(prefix + "wxUpCash1", t.getWxUpCash1());
			}
			if (null != t.getZfbRate()) {
				map.put(prefix + "zfbRate", t.getZfbRate());
			}
			if (null != t.getZfbCash()) {
				map.put(prefix + "zfbCash", t.getZfbCash());
			}
			if (null != t.getEwmRate()) {
				map.put(prefix + "ewmRate", t.getEwmRate());
			}
			if (null != t.getEwmCash()) {
				map.put(prefix + "ewmCash", t.getEwmCash());
			}
			if (null != t.getYsfRate()) {
				map.put(prefix + "ysfRate", t.getYsfRate());
			}
			if (null != t.getHbRate()) {
				map.put(prefix + "hbRate", t.getHbRate());
			}
			if (null != t.getHbCash()) {
				map.put(prefix + "hbCash", t.getHbCash());
			}
		}
		return map;
	}

	/**
	 * 成本上限费率添加(中心成本人工设置110000时为上限的设置)
	 * @return
	 */
	private Map getDefaultLimitHrtCost(){
		Map map = new HashMap();
		List<HrtUnnoCostModel> yunYingHrt=new ArrayList<HrtUnnoCostModel>();
		String limitUnno="110000";
		String checkHql="from HrtUnnoCostModel r where r.status=-1 and r.unno ='"+(HrtCostConstant.RGSZ_PREX+limitUnno)+"'";
		List<HrtUnnoCostModel> checkyunYingHrt = hrtUnnoCostDao.queryObjectsByHqlList(checkHql);
		if(checkyunYingHrt.size()>0){
			yunYingHrt=checkyunYingHrt;
		}

		for(HrtUnnoCostModel t:yunYingHrt){
			String prefix = t.getMachineType()+"|"+t.getTxnType()+"|"+t.getTxnDetail()+"|";
			if(null!=t.getCashCost()){
				map.put(prefix+"cashCost",t.getCashCost());
			}
			if(null!=t.getCashRate()){
				map.put(prefix+"cashRate",t.getCashRate());
			}
			if(null!=t.getDebitRate()){
				map.put(prefix+"debitRate",t.getDebitRate());
			}
			if(null!=t.getDebitFeeamt()){
				map.put(prefix+"debitFeeamt",t.getDebitFeeamt());
			}
			if(null!=t.getCreditRate()){
				map.put(prefix+"creditRate",t.getCreditRate());
			}
			if(null!=t.getWxUpRate()){
				map.put(prefix+"wxUpRate",t.getWxUpRate());
			}
			if(null!=t.getWxUpCash()){
				map.put(prefix+"wxUpCash",t.getWxUpCash());
			}
			if(null!=t.getWxUpRate1()){
				map.put(prefix+"wxUpRate1",t.getWxUpRate1());
			}
			if(null!=t.getWxUpCash()){
				map.put(prefix+"wxUpCash1",t.getWxUpCash1());
			}
			if(null!=t.getZfbRate()){
				map.put(prefix+"zfbRate",t.getZfbRate());
			}
			if(null!=t.getZfbCash()){
				map.put(prefix+"zfbCash",t.getZfbCash());
			}
			if(null!=t.getEwmRate()){
				map.put(prefix+"ewmRate",t.getEwmRate());
			}
			if(null!=t.getEwmCash()){
				map.put(prefix+"ewmCash",t.getEwmCash());
			}
			if(null!=t.getYsfRate()){
				map.put(prefix+"ysfRate",t.getYsfRate());
			}
			if(null!=t.getHbRate()){
				map.put(prefix+"hbRate",t.getHbRate());
			}
			if(null!=t.getHbCash()){
				map.put(prefix+"hbCash",t.getHbCash());
			}
		}
		return map;
	}

	@Override
	public JsonBean checkXLSFile(AgentUnitBean agentUnit, UserBean userBean,File upload,String costType) {
		JsonBean rsJsonBean = new JsonBean();
		HSSFWorkbook workbook;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(upload));
		} catch (IOException e) {
			log.info("文件解析异常"+e);
			rsJsonBean.setSuccess(false);
			rsJsonBean.setMsg("文件格式错误，请另存.xls后重新上传");
			return rsJsonBean;
		}
		HSSFSheet sheet=workbook.getSheetAt(0);
		//数据校验
		String sql0;
		if (userBean.getUnitLvl() == 0) {// 总公司
			sql0 = "SELECT   XMLAGG(XMLPARSE(CONTENT t0.unno || ',' WELLFORMED) ORDER BY t0.unno).GETCLOBVAL() UNNOS FROM sys_unit t0 WHERE t0.un_lvl in(1,2) start WITH unno = '"
					+userBean.getUnNo()+"' connect by prior t0.unno = t0.upper_unit ";
		}else if(userBean.getUnitLvl() == 1){// 运营中心
			sql0 = "SELECT XMLAGG(XMLPARSE(CONTENT t0.unno || ',' WELLFORMED) ORDER BY t0.unno).GETCLOBVAL() UNNOS FROM sys_unit t0 WHERE t0.un_lvl = 2 OR t0.unno='"
					+userBean.getUnNo()+"' start WITH unno = '"+userBean.getUnNo()+"' connect by prior t0.unno = t0.upper_unit ";
		}else {
			rsJsonBean.setSuccess(false);
			rsJsonBean.setMsg("您无权操作");
			return rsJsonBean;
		}
		List list = billBpFileDao.queryObjectsBySqlList(sql0);
		if (list == null || list.size() == 0) {
			rsJsonBean.setSuccess(false);
			rsJsonBean.setMsg("机构查询不存在");
			return rsJsonBean;
		}
		int k;
		//模板校验
		if("2".equals(costType)){
			k=1;
			// @author:lxg-20190910 扫码费率拆分
			if(null!=sheet.getRow(0).getCell(21)){
				log.info("成本变更(活动)模板有误，请检查模板！！！");
				rsJsonBean.setSuccess(false);
				rsJsonBean.setMsg("成本变更(活动)模板有误，请检查模板！！！");
				return rsJsonBean;
			}
		}else{
			k=4;
			if(null!=sheet.getRow(0).getCell(40) || null==sheet.getRow(0).getCell(9)){
				log.info("成本变更(非活动)模板有误，请检查模板！！！");
				rsJsonBean.setSuccess(false);
				rsJsonBean.setMsg("成本变更(非活动)模板有误，请检查模板！！！");
				return rsJsonBean;
			}	
		}
		//生产
		Map<String, Clob> map = (Map<String, Clob>)list.get(0);
		List<String> unnoList = new ArrayList<String>();
		Clob temp = map.get("UNNOS");
		String string = clobToString(temp);
		String[] tempSplit = string.split(",");
		for (String temp1 : tempSplit) {
			unnoList.add(temp1);
		}
		//测试
//		Map<String, String> map = (Map<String, String>)list.get(0);
//		List<String> unnoList = new ArrayList<String>(Arrays.asList(map.get("UNNOS").split(",")));
		try {
			//同文件记录机构活动类型用户判断同文件同机构同活动成本多次变更的情况
//			Map<String,String> tMap = new HashMap<String,String>();
			Set<String> set = new HashSet<String>(); 
			for(int i=k;i<sheet.getLastRowNum()+1 ;i++){
				HSSFRow row=sheet.getRow(i);
				HSSFCell cell = row.getCell((short)3);
				if (cell == null) {
					rsJsonBean.setSuccess(false);
					rsJsonBean.setMsg("第"+(i+1)+"行机构号不能为空");
					return rsJsonBean;
				}
				row.getCell(3).setCellType(cell.CELL_TYPE_STRING);
				String unno=row.getCell((short)3).getStringCellValue().trim();
                // 获取运营中心的成本
                String yunyingUnno = getYunYingUnnoByUnno(unno);
                Map hrtCost = getYunYingHrtUnnoCost(yunyingUnno,2);
                int index = unnoList.indexOf(unno);
				if (index != -1) {
					//活动成本部分
					if("2".equals(costType)){
						HSSFCell cell1 = row.getCell((short)4);
						if (cell1 == null) {
							rsJsonBean.setSuccess(false);
							rsJsonBean.setMsg("第"+(i+1)+"行活动类型不能为空");
							return rsJsonBean;
						}
						row.getCell(4).setCellType(cell1.CELL_TYPE_NUMERIC);
						String txn_detail=String.valueOf(row.getCell((short)4).getNumericCellValue()).trim();
//						if(tMap.containsKey(unno) && tMap.get(unno).contains(txn_detail)){
						if(set.contains(unno+txn_detail)){
							rsJsonBean.setSuccess(false);
							rsJsonBean.setMsg("第"+(i+1)+"行该机构下活动"+txn_detail+"成本已存在表中，请核实!");
							return rsJsonBean;
						}else{
							String sql="select valueinteger from bill_PurConfigure where type=3 and valuestring='rate' and valueinteger='"+txn_detail+"' ";
							List lis=billBpFileDao.queryObjectsBySqlList(sql);
							if(lis.size()<=0){
								rsJsonBean.setSuccess(false);
								rsJsonBean.setMsg("第"+(i+1)+"行活动类型"+txn_detail+"不属于活动成本，请核实!");
								return rsJsonBean;
							}
							// 只能变更成功状态的成本
							String sql1="select unno from hrt_unno_cost u where u.status=1 and u.unno='"+unno+"' and u.txn_detail='"+txn_detail+"' ";
							List list1 = billBpFileDao.queryObjectsBySqlList(sql1);
							if(list1.size()<=0){
								rsJsonBean.setSuccess(false);
								rsJsonBean.setMsg("第"+(i+1)+"行该机构下活动"+txn_detail+"成本未维护，请联系结算!");
								return rsJsonBean;
							}
						}
						// @author:xuegangliu-20190319 代理成本上传-活动,一代成本校验,  运营中心本身不校验
                        if(StringUtils.isNotEmpty(yunyingUnno) && !yunyingUnno.equals(unno)){
                            String errInfo = checkChangeHrtCostAct(row,hrtCost,cell,i);
                            if(StringUtils.isNotEmpty(errInfo)){
                                rsJsonBean.setSuccess(false);
                                rsJsonBean.setMsg(errInfo);
                                return rsJsonBean;
                            }
                        }
//						tMap.put(unno,txn_detail);
						set.add(unno+txn_detail);
					}else{
						unnoList.remove(index);
						if(null==row.getCell((short)35) && null==row.getCell((short)36) && null==row.getCell((short)37)
						&&null==row.getCell((short)38) && null==row.getCell((short)39)){
							rsJsonBean.setSuccess(false);
							rsJsonBean.setMsg("第"+(i+1)+"行其他项目不能有空,请检查");
							return rsJsonBean;
						}
						row.getCell(35).setCellType(cell.CELL_TYPE_STRING);
						row.getCell(37).setCellType(cell.CELL_TYPE_STRING);
						row.getCell(38).setCellType(cell.CELL_TYPE_STRING);
						if(!"是|否".contains(row.getCell((short)35).getStringCellValue().trim())||
						   !"是|否".contains(row.getCell((short)37).getStringCellValue().trim())||
						   !"是|否".contains(row.getCell((short)38).getStringCellValue().trim())){
							rsJsonBean.setSuccess(false);
							rsJsonBean.setMsg("第"+(i+1)+"行是否直发分润/大蓝牙/小蓝牙只允许填是或者否,请检查");
							return rsJsonBean;
						}
						try {
							row.getCell(36).setCellType(cell.CELL_TYPE_NUMERIC);
							row.getCell(39).setCellType(cell.CELL_TYPE_NUMERIC);
							row.getCell((short)36).getNumericCellValue();
							row.getCell((short)39).getNumericCellValue();
						} catch (Exception e) {
							log.info(unno+"非活动分润税点/代扣机器款数值错误:"+e.getMessage());
							rsJsonBean.setSuccess(false);
							rsJsonBean.setMsg("第"+(i+1)+"行分润税点/代扣机器款错误,请检查");
							return rsJsonBean;
						}
						// @author:xuegangliu 代理成本上传-非活动,一代成本校验,运营中心本身不校验
                        if(StringUtils.isNotEmpty(yunyingUnno) && !yunyingUnno.equals(unno)) {
                            String errInfo = checkChangeHrtCost(row, hrtCost, cell, i);
                            if (StringUtils.isNotEmpty(errInfo)) {
                                rsJsonBean.setSuccess(false);
                                rsJsonBean.setMsg(errInfo);
                                return rsJsonBean;
                            }
                        }
					}
					continue;
				}else{
					rsJsonBean.setSuccess(false);
					rsJsonBean.setMsg("第"+(i+1)+"行机构号与您无关或重复，请检查");
					return rsJsonBean;
				}
			}
		} catch (Exception e) {
			log.info("读取结算成本文件异常："+e.getMessage());
			rsJsonBean.setSuccess(false);
			rsJsonBean.setMsg(e.getMessage());
			return rsJsonBean;
		}
		
		log.info("文件校验完成");
		rsJsonBean.setSuccess(true);
		return rsJsonBean;
	}

    /**
     * 一代活动成本校验
     * @param row
     * @param hrtcost
     * @param cell
     * @param i
     * @return
     */
    public String checkChangeHrtCostAct(HSSFRow row,Map hrtcost,HSSFCell cell,int i){
		int errCount = i+1;
	    StringBuffer errInfo = new StringBuffer();

        //活动成本
//        hrtUnnoCost1.setMachine_type(1);
////        hrtUnnoCost1.setTxn_type(1);
        //活动类型
//        row.getCell(4).setCellType(cell.CELL_TYPE_NUMERIC);
//        hrtUnnoCost1.setTxn_detail((new Double(row.getCell((short)4).getNumericCellValue())).intValue());
        row.getCell(4).setCellType(cell.CELL_TYPE_STRING);
        String act =row.getCell((short)4).getStringCellValue();
        String keyHrt = "1|1|"+act+"|";

		String sqlConfig="select count(1) from bill_purconfigure k where k.type=3 and k.subtype=1 and k.valuestring='rate' and k.valueinteger=:valueinteger";
		Map mapConfig=new HashMap();
		mapConfig.put("valueinteger",act);
		Integer configCount = hrtUnnoCostDao.querysqlCounts2(sqlConfig,mapConfig);
		boolean subType1=configCount>0;

        //贷记卡成本(%)
        if(null!=row.getCell(7) && !"".equals(String.valueOf(row.getCell(7)))
                && hrtcost.containsKey(keyHrt+"creditRate")){
            row.getCell(7).setCellType(cell.CELL_TYPE_NUMERIC);
            Double yidai = row.getCell((short)7).getNumericCellValue();
            Double yunYing = Double.parseDouble(hrtcost.get(keyHrt+"creditRate")+"");
            if(yidai<yunYing){
                errInfo.append("第"+errCount+"行代理扫码1000以下（终端0.38）费率(%)低于运营中心成本");
                return errInfo.toString();
            }
//            hrtUnnoCost1.setCredit_rate(row.getCell((short)7).getNumericCellValue());
        }
        //提现转账手续费
        if(null!=row.getCell(8) && !"".equals(String.valueOf(row.getCell(8)))
                && hrtcost.containsKey(keyHrt+"cashCost")){
            row.getCell(8).setCellType(cell.CELL_TYPE_NUMERIC);
            Double yidai = row.getCell((short)8).getNumericCellValue();
            Double yunYing = Double.parseDouble(hrtcost.get(keyHrt+"cashCost")+"");
            if(yidai<yunYing){
                errInfo.append("第"+errCount+"行代理扫码1000以下（终端0.38）转账费低于运营中心成本");
                return errInfo.toString();
            }
//            hrtUnnoCost1.setCash_cost(row.getCell((short)8).getNumericCellValue());
        }

		// @author:lxg-20190911 扫码费率拆分
		if(hrtcost.containsKey(keyHrt+"wxUpRate") && null!=row.getCell(9) && !"".equals(String.valueOf(row.getCell(9)))){
			row.getCell(9).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)9).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get(keyHrt+"wxUpRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行扫码1000以上（终端0.38）费率(%)低于运营中心成本");
				return errInfo.toString();
			}
		}
		if(hrtcost.containsKey(keyHrt+"wxUpCash") && null!=row.getCell(10) && !"".equals(String.valueOf(row.getCell(10)))){
			row.getCell(10).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)10).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get(keyHrt+"wxUpCash")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行扫码1000以上（终端0.38）转账费低于运营中心成本");
				return errInfo.toString();
			}
		}
		if(hrtcost.containsKey(keyHrt+"wxUpRate1") && null!=row.getCell(11) && !"".equals(String.valueOf(row.getCell(11)))){
			row.getCell(11).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)11).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get(keyHrt+"wxUpRate1")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行扫码1000以上（终端0.45）费率(%)低于运营中心成本");
				return errInfo.toString();
			}
		}
		if(hrtcost.containsKey(keyHrt+"wxUpCash1") && null!=row.getCell(12) && !"".equals(String.valueOf(row.getCell(12)))){
			row.getCell(12).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)12).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get(keyHrt+"wxUpCash1")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行扫码1000以上（终端0.45）转账费低于运营中心成本");
				return errInfo.toString();
			}
		}
		if(hrtcost.containsKey(keyHrt+"zfbRate") && null!=row.getCell(13) && !"".equals(String.valueOf(row.getCell(13)))){
			row.getCell(13).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)13).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get(keyHrt+"zfbRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行扫码1000以下（终端0.45）费率(%)低于运营中心成本");
				return errInfo.toString();
			}
		}
		if(hrtcost.containsKey(keyHrt+"zfbCash") && null!=row.getCell(14) && !"".equals(String.valueOf(row.getCell(14)))){
			row.getCell(14).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)14).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get(keyHrt+"zfbCash")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行扫码1000以下（终端0.45）转账费低于运营中心成本");
				return errInfo.toString();
			}
		}
		if(hrtcost.containsKey(keyHrt+"ewmRate") && null!=row.getCell(15) && !"".equals(String.valueOf(row.getCell(15)))){
			row.getCell(15).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)15).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get(keyHrt+"ewmRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行银联二维码费率低于运营中心成本");
				return errInfo.toString();
			}
		}
		if(hrtcost.containsKey(keyHrt+"ewmCash") && null!=row.getCell(16) && !"".equals(String.valueOf(row.getCell(16)))){
			row.getCell(16).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)16).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get(keyHrt+"ewmCash")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行银联二维码转账费低于运营中心成本");
				return errInfo.toString();
			}
		}
		if(subType1) {
			if(hrtcost.containsKey(keyHrt+"hbRate") && null!=row.getCell(18) && !"".equals(String.valueOf(row.getCell(18)))){
				row.getCell(18).setCellType(cell.CELL_TYPE_NUMERIC);
				Double yidai = row.getCell((short)18).getNumericCellValue();
				Double yunYing = Double.parseDouble(hrtcost.get(keyHrt+"hbRate")+"");
				if(yidai<yunYing){
					errInfo.append("第"+errCount+"行花呗费率低于运营中心成本");
					return errInfo.toString();
				}
			}
			if(hrtcost.containsKey(keyHrt+"hbCash") && null!=row.getCell(19) && !"".equals(String.valueOf(row.getCell(19)))){
				row.getCell(19).setCellType(cell.CELL_TYPE_NUMERIC);
				Double yidai = row.getCell((short)19).getNumericCellValue();
				Double yunYing = Double.parseDouble(hrtcost.get(keyHrt+"hbCash")+"");
				if(yidai<yunYing){
					errInfo.append("第"+errCount+"行花呗转账费低于运营中心成本");
					return errInfo.toString();
				}
			}
			if(null != row.getCell(17) && !"".equals(String.valueOf(row.getCell(17)))){
				errInfo.append("第"+errCount+"行云闪付费率必须为空");
				return errInfo.toString();
			}
			if(null != row.getCell(5) && !"".equals(String.valueOf(row.getCell(5)))){
				errInfo.append("第"+errCount+"行代理刷卡费率必须为空");
				return errInfo.toString();
			}
			if(null != row.getCell(6) && !"".equals(String.valueOf(row.getCell(6)))){
				errInfo.append("第"+errCount+"行代理刷卡提现必须为空");
				return errInfo.toString();
			}
		}else{
			if(null!=row.getCell(18) && !"".equals(String.valueOf(row.getCell(18)))){
				errInfo.append("第"+errCount+"行花呗费率必须为空");
				return errInfo.toString();
			}
			if(null!=row.getCell(19) && !"".equals(String.valueOf(row.getCell(19)))){
				errInfo.append("第"+errCount+"行花呗转账费必须为空");
				return errInfo.toString();
			}
			if (hrtcost.containsKey(keyHrt + "ysfRate") && null != row.getCell(17) && !"".equals(String.valueOf(row.getCell(17)))) {
				row.getCell(17).setCellType(cell.CELL_TYPE_NUMERIC);
				Double yidai = row.getCell((short) 17).getNumericCellValue();
				Double yunYing = Double.parseDouble(hrtcost.get(keyHrt + "ysfRate") + "");
				if (yidai < yunYing) {
					errInfo.append("第" + errCount + "行云闪付费率低于运营中心成本");
					return errInfo.toString();
				}
			}
			//借记卡成本(%)
			if (null != row.getCell(5) && !"".equals(String.valueOf(row.getCell(5)))
					&& hrtcost.containsKey(keyHrt + "debitRate")) {
				row.getCell(5).setCellType(cell.CELL_TYPE_NUMERIC);
				Double yidai = row.getCell((short) 5).getNumericCellValue();
				Double yunYing = Double.parseDouble(hrtcost.get(keyHrt + "debitRate") + "");
				if (yidai < yunYing) {
					errInfo.append("第" + errCount + "行代理刷卡费率低于运营中心成本");
					return errInfo.toString();
				}
//            hrtUnnoCost1.setDebit_rate(row.getCell((short)5).getNumericCellValue());
			}
			//借记卡封顶值
			if (null != row.getCell(6) && !"".equals(String.valueOf(row.getCell(6)))
					&& hrtcost.containsKey(keyHrt + "debitFeeamt")) {
				row.getCell(6).setCellType(cell.CELL_TYPE_NUMERIC);
				Double yidai = row.getCell((short) 6).getNumericCellValue();
				Double yunYing = Double.parseDouble(hrtcost.get(keyHrt + "debitFeeamt") + "");
				if (yidai < yunYing) {
					errInfo.append("第" + errCount + "行代理刷卡提现低于运营中心成本");
					return errInfo.toString();
				}
//            hrtUnnoCost1.setDebit_feeamt(row.getCell((short)6).getNumericCellValue());
			}
		}
        return errInfo.toString();
    }

    /**
     * 一代非活动成本校验
     * @param row
     * @param hrtcost
     * @param cell
     * @param i
     * @return
     */
	public String checkChangeHrtCost(HSSFRow row,Map hrtcost,HSSFCell cell,int i){
		int errCount = i+1;
		StringBuffer errInfo = new StringBuffer();
		//（1）若中心成本为空，代理成本可不必校验，允许导入；
		//（2）若中心成本不为空，代理成本大于等于中心成本，允许导入；
		//（3）若中心成本不为空，代理成本小于中心成本，禁止导入，提示“某某行代理成本低于中心成本”

		// 0.72秒到
		//String prefix = t.getMachineType()+"|"+t.getTxnType()+"|"+t.getTxnDetail()+"|";cashCost cashRate debitRate debitFeeamt creditRate
//		hrtUnnoCost1.setMachine_type(1);
//		hrtUnnoCost1.setTxn_type(1);
//		hrtUnnoCost1.setTxn_detail(2);
		//借贷费率
		if(null!=row.getCell(4) && !"".equals(String.valueOf(row.getCell(4))) && hrtcost.containsKey("1|1|2|debitRate")){
            row.getCell(4).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)4).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|1|2|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost1.setDebit_rate();
//			hrtUnnoCost1.setCredit_rate(row.getCell((short)4).getNumericCellValue());
		}
		if(null!=row.getCell(4) && !"".equals(String.valueOf(row.getCell(4))) && hrtcost.containsKey("1|1|2|creditRate")){
			row.getCell(4).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)4).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|1|2|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost1.setDebit_rate();
//			hrtUnnoCost1.setCredit_rate(row.getCell((short)4).getNumericCellValue());
		}
		//提现成本
		if(null!=row.getCell(5) && !"".equals(String.valueOf(row.getCell(5))) && hrtcost.containsKey("1|1|2|cashCost")){
            row.getCell(5).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)5).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|1|2|cashCost")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost1.setCash_cost(row.getCell((short)5).getNumericCellValue());
		}

		//0.6秒到
//		hrtUnnoCost2.setMachine_type(1);
//		hrtUnnoCost2.setTxn_type(1);
//		hrtUnnoCost2.setTxn_detail(1);
		//借贷费率
		if(null!=row.getCell(6) && !"".equals(String.valueOf(row.getCell(6))) && hrtcost.containsKey("1|1|1|debitRate")){
            row.getCell(6).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)6).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|1|1|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost2.setDebit_rate(row.getCell((short)6).getNumericCellValue());
//			hrtUnnoCost2.setCredit_rate(row.getCell((short)6).getNumericCellValue());
		}
		if(null!=row.getCell(6) && !"".equals(String.valueOf(row.getCell(6))) && hrtcost.containsKey("1|1|1|creditRate")){
			row.getCell(6).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)6).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|1|1|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost2.setDebit_rate(row.getCell((short)6).getNumericCellValue());
//			hrtUnnoCost2.setCredit_rate(row.getCell((short)6).getNumericCellValue());
		}
		//提现成本
		if(null!=row.getCell(7) && !"".equals(String.valueOf(row.getCell(7))) && hrtcost.containsKey("1|1|1|cashCost")){
            row.getCell(7).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)7).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|1|1|cashCost")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
		}

		//理财
//		hrtUnnoCost3.setMachine_type(1);
//		hrtUnnoCost3.setTxn_type(2);
//		hrtUnnoCost3.setTxn_detail(3);
		//借费率
		if(null!=row.getCell(8) && !"".equals(String.valueOf(8)) && hrtcost.containsKey("1|2|3|debitRate")){
            row.getCell(8).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)8).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|2|3|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost3.setDebit_rate(row.getCell((short)8).getNumericCellValue());
		}
		//借手续费
		if(null!=row.getCell(9) && !"".equals(String.valueOf(9)) && hrtcost.containsKey("1|2|3|debitFeeamt")){
            row.getCell(9).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)9).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|2|3|debitFeeamt")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost3.setDebit_feeamt(row.getCell((short)9).getNumericCellValue());
		}
		//贷费率
		if(null!=row.getCell(10) && !"".equals(String.valueOf(10)) && hrtcost.containsKey("1|2|3|creditRate")){
            row.getCell(10).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)10).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|2|3|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost3.setCredit_rate(row.getCell((short)10).getNumericCellValue());
		}

		//扫码T0-微信
//		hrtUnnoCost4.setMachine_type(1);
//		hrtUnnoCost4.setTxn_type(6);
//		hrtUnnoCost4.setTxn_detail(8);
		//借贷费率
		if(null!=row.getCell(11) && !"".equals(String.valueOf(row.getCell(11))) && hrtcost.containsKey("1|6|8|debitRate")){
            row.getCell(11).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)11).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|6|8|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost4.setDebit_rate(row.getCell((short)11).getNumericCellValue());
//			hrtUnnoCost4.setCredit_rate(row.getCell((short)11).getNumericCellValue());
		}
		if(null!=row.getCell(11) && !"".equals(String.valueOf(row.getCell(11))) && hrtcost.containsKey("1|6|8|creditRate")){
			row.getCell(11).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)11).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|6|8|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost4.setDebit_rate(row.getCell((short)11).getNumericCellValue());
//			hrtUnnoCost4.setCredit_rate(row.getCell((short)11).getNumericCellValue());
		}
		//提现成本
		if(null!=row.getCell(12) && !"".equals(String.valueOf(row.getCell(12))) && hrtcost.containsKey("1|6|8|cashCost")){
            row.getCell(12).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)12).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|6|8|cashCost")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost4.setCash_cost(row.getCell((short)12).getNumericCellValue());
		}

		//扫码T0-支付宝
//		hrtUnnoCost5.setMachine_type(1);
//		hrtUnnoCost5.setTxn_type(6);
//		hrtUnnoCost5.setTxn_detail(9);
		//借贷费率
		if(null!=row.getCell(13) && !"".equals(String.valueOf(row.getCell(13))) && hrtcost.containsKey("1|6|9|debitRate")){
            row.getCell(13).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)13).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|6|9|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost5.setDebit_rate(row.getCell((short)13).getNumericCellValue());
//			hrtUnnoCost5.setCredit_rate(row.getCell((short)13).getNumericCellValue());
		}
		if(null!=row.getCell(13) && !"".equals(String.valueOf(row.getCell(13))) && hrtcost.containsKey("1|6|9|creditRate")){
			row.getCell(13).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)13).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|6|9|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost5.setDebit_rate(row.getCell((short)13).getNumericCellValue());
//			hrtUnnoCost5.setCredit_rate(row.getCell((short)13).getNumericCellValue());
		}
		//提现成本
		if(null!=row.getCell(14) && !"".equals(String.valueOf(row.getCell(14))) && hrtcost.containsKey("1|6|9|cashCost")){
            row.getCell(14).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)14).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|6|9|cashCost")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost5.setCash_cost(row.getCell((short)14).getNumericCellValue());
		}

		//扫码T0-银联二维码
//		hrtUnnoCost6.setMachine_type(1);
//		hrtUnnoCost6.setTxn_type(6);
//		hrtUnnoCost6.setTxn_detail(10);
		//借贷费率
		if(null!=row.getCell(15) && !"".equals(String.valueOf(row.getCell(15))) && hrtcost.containsKey("1|6|10|debitRate")){
            row.getCell(15).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)15).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|6|10|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost6.setDebit_rate(row.getCell((short)15).getNumericCellValue());
//			hrtUnnoCost6.setCredit_rate(row.getCell((short)15).getNumericCellValue());
		}
		if(null!=row.getCell(15) && !"".equals(String.valueOf(row.getCell(15))) && hrtcost.containsKey("1|6|10|creditRate")){
			row.getCell(15).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)15).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|6|10|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost6.setDebit_rate(row.getCell((short)15).getNumericCellValue());
//			hrtUnnoCost6.setCredit_rate(row.getCell((short)15).getNumericCellValue());
		}
		//提现成本
		if(null!=row.getCell(16) && !"".equals(String.valueOf(row.getCell(16))) && hrtcost.containsKey("1|6|10|cashCost")){
            row.getCell(16).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)16).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|6|10|cashCost")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost6.setCash_cost(row.getCell((short)16).getNumericCellValue());
		}

		//扫码T1-微信
		if(null!=row.getCell(17) && !"".equals(String.valueOf(row.getCell(17))) && hrtcost.containsKey("1|7|8|debitRate")){
			row.getCell(17).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost7.setMachine_type(1);
//			hrtUnnoCost7.setTxn_type(7);
//			hrtUnnoCost7.setTxn_detail(8);
			//借贷费率
			Double yidai = row.getCell((short)17).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|7|8|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost7.setDebit_rate(row.getCell((short)17).getNumericCellValue());
//			hrtUnnoCost7.setCredit_rate(row.getCell((short)17).getNumericCellValue());
		}
		if(null!=row.getCell(17) && !"".equals(String.valueOf(row.getCell(17))) && hrtcost.containsKey("1|7|8|creditRate")){
			row.getCell(17).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost7.setMachine_type(1);
//			hrtUnnoCost7.setTxn_type(7);
//			hrtUnnoCost7.setTxn_detail(8);
			//借贷费率
			Double yidai = row.getCell((short)17).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|7|8|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost7.setDebit_rate(row.getCell((short)17).getNumericCellValue());
//			hrtUnnoCost7.setCredit_rate(row.getCell((short)17).getNumericCellValue());
		}

		//扫码T1-支付宝
		if(null!=row.getCell(18) && !"".equals(String.valueOf(row.getCell(18))) && hrtcost.containsKey("1|7|9|debitRate")){
            row.getCell(18).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost8.setMachine_type(1);
//			hrtUnnoCost8.setTxn_type(7);
//			hrtUnnoCost8.setTxn_detail(9);
			//借贷费率
			Double yidai = row.getCell((short)18).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|7|9|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost8.setDebit_rate(row.getCell((short)18).getNumericCellValue());
//			hrtUnnoCost8.setCredit_rate(row.getCell((short)18).getNumericCellValue());
		}
		if(null!=row.getCell(18) && !"".equals(String.valueOf(row.getCell(18))) && hrtcost.containsKey("1|7|9|creditRate")){
			row.getCell(18).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost8.setMachine_type(1);
//			hrtUnnoCost8.setTxn_type(7);
//			hrtUnnoCost8.setTxn_detail(9);
			//借贷费率
			Double yidai = row.getCell((short)18).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|7|9|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost8.setDebit_rate(row.getCell((short)18).getNumericCellValue());
//			hrtUnnoCost8.setCredit_rate(row.getCell((short)18).getNumericCellValue());
		}
		//扫码T1-银联二维码
		if(null!=row.getCell(19) && !"".equals(String.valueOf(row.getCell(19))) && hrtcost.containsKey("1|7|10|debitRate")){
            row.getCell(19).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost9.setMachine_type(1);
//			hrtUnnoCost9.setTxn_type(7);
//			hrtUnnoCost9.setTxn_detail(10);
			//借贷费率
			Double yidai = row.getCell((short)19).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|7|10|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost9.setDebit_rate(row.getCell((short)19).getNumericCellValue());
//			hrtUnnoCost9.setCredit_rate(row.getCell((short)19).getNumericCellValue());
		}
		if(null!=row.getCell(19) && !"".equals(String.valueOf(row.getCell(19))) && hrtcost.containsKey("1|7|10|creditRate")){
			row.getCell(19).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost9.setMachine_type(1);
//			hrtUnnoCost9.setTxn_type(7);
//			hrtUnnoCost9.setTxn_detail(10);
			//借贷费率
			Double yidai = row.getCell((short)19).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|7|10|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost9.setDebit_rate(row.getCell((short)19).getNumericCellValue());
//			hrtUnnoCost9.setCredit_rate(row.getCell((short)19).getNumericCellValue());
		}

		//代还
		if(null!=row.getCell(20) && !"".equals(String.valueOf(row.getCell(20))) && hrtcost.containsKey("1|3|4|debitRate")){
            row.getCell(20).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost10.setMachine_type(1);
//			hrtUnnoCost10.setTxn_type(3);
//			hrtUnnoCost10.setTxn_detail(4);
			//借贷费率
			Double yidai = row.getCell((short)20).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|3|4|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost10.setDebit_rate(row.getCell((short)20).getNumericCellValue());
//			hrtUnnoCost10.setCredit_rate(row.getCell((short)20).getNumericCellValue());
		}
		if(null!=row.getCell(20) && !"".equals(String.valueOf(row.getCell(20))) && hrtcost.containsKey("1|3|4|creditRate")){
			row.getCell(20).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost10.setMachine_type(1);
//			hrtUnnoCost10.setTxn_type(3);
//			hrtUnnoCost10.setTxn_detail(4);
			//借贷费率
			Double yidai = row.getCell((short)20).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|3|4|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost10.setDebit_rate(row.getCell((short)20).getNumericCellValue());
//			hrtUnnoCost10.setCredit_rate(row.getCell((short)20).getNumericCellValue());
		}

		//快捷-VIP
//		hrtUnnoCost11.setMachine_type(1);
//		hrtUnnoCost11.setTxn_type(5);
//		hrtUnnoCost11.setTxn_detail(6);
		//借贷费率
		if(null!=row.getCell(21) && !"".equals(String.valueOf(row.getCell(21))) && hrtcost.containsKey("1|5|6|debitRate")){
			row.getCell(21).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)21).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|5|6|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost11.setDebit_rate(row.getCell((short)21).getNumericCellValue());
//			hrtUnnoCost11.setCredit_rate(row.getCell((short)21).getNumericCellValue());
		}
		if(null!=row.getCell(21) && !"".equals(String.valueOf(row.getCell(21))) && hrtcost.containsKey("1|5|6|creditRate")){
			row.getCell(21).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)21).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|5|6|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost11.setDebit_rate(row.getCell((short)21).getNumericCellValue());
//			hrtUnnoCost11.setCredit_rate(row.getCell((short)21).getNumericCellValue());
		}
		//提现成本
		if(null!=row.getCell(23) && !"".equals(String.valueOf(row.getCell(23))) && hrtcost.containsKey("1|5|6|cashCost")){
            row.getCell(23).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)23).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|5|6|cashCost")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost11.setCash_cost(row.getCell((short)23).getNumericCellValue());
		}

		//快捷-完美
//		hrtUnnoCost12.setMachine_type(1);
//		hrtUnnoCost12.setTxn_type(5);
//		hrtUnnoCost12.setTxn_detail(7);
		//借贷费率
		if(null!=row.getCell(22) && !"".equals(String.valueOf(row.getCell(22))) && hrtcost.containsKey("1|5|7|debitRate")){
            row.getCell(22).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)22).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|5|7|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost12.setDebit_rate(row.getCell((short)22).getNumericCellValue());
//			hrtUnnoCost12.setCredit_rate(row.getCell((short)22).getNumericCellValue());
		}
		if(null!=row.getCell(22) && !"".equals(String.valueOf(row.getCell(22))) && hrtcost.containsKey("1|5|7|creditRate")){
			row.getCell(22).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)22).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|5|7|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost12.setDebit_rate(row.getCell((short)22).getNumericCellValue());
//			hrtUnnoCost12.setCredit_rate(row.getCell((short)22).getNumericCellValue());
		}
		//提现成本
		if(null!=row.getCell(23) && !"".equals(String.valueOf(row.getCell(23))) && hrtcost.containsKey("1|5|7|cashCost")){
            row.getCell(23).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)23).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|5|7|cashCost")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost12.setCash_cost(row.getCell((short)23).getNumericCellValue());
		}

		//云闪付
		if(null!=row.getCell(24) && !"".equals(String.valueOf(row.getCell(24))) && hrtcost.containsKey("1|4|5|debitRate")){
            row.getCell(24).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost13.setMachine_type(1);
//			hrtUnnoCost13.setTxn_type(4);
//			hrtUnnoCost13.setTxn_detail(5);
			//借贷费率
			Double yidai = row.getCell((short)24).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|4|5|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost13.setDebit_rate(row.getCell((short)24).getNumericCellValue());
//			hrtUnnoCost13.setCredit_rate(row.getCell((short)24).getNumericCellValue());
		}
		if(null!=row.getCell(24) && !"".equals(String.valueOf(row.getCell(24))) && hrtcost.containsKey("1|4|5|creditRate")){
			row.getCell(24).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost13.setMachine_type(1);
//			hrtUnnoCost13.setTxn_type(4);
//			hrtUnnoCost13.setTxn_detail(5);
			//借贷费率
			Double yidai = row.getCell((short)24).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("1|4|5|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost13.setDebit_rate(row.getCell((short)24).getNumericCellValue());
//			hrtUnnoCost13.setCredit_rate(row.getCell((short)24).getNumericCellValue());
		}

		//传统标准
//		hrtUnnoCost14.setMachine_type(2);
//		hrtUnnoCost14.setTxn_type(6);
//		hrtUnnoCost14.setTxn_detail(11);
		//借费率
		if(null!=row.getCell(25) && !"".equals(String.valueOf(row.getCell(25))) && hrtcost.containsKey("2|6|11|debitRate")){
            row.getCell(25).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)25).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("2|6|11|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost14.setDebit_rate(row.getCell((short)25).getNumericCellValue());
		}
		//借手续费
		if(null!=row.getCell(26) && !"".equals(String.valueOf(row.getCell(26))) && hrtcost.containsKey("2|6|11|debitFeeamt")){
            row.getCell(26).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)26).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("2|6|11|debitFeeamt")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost14.setDebit_feeamt(row.getCell((short)26).getNumericCellValue());
		}
		//贷费率
		if(null!=row.getCell(27) && !"".equals(String.valueOf(row.getCell(27))) && hrtcost.containsKey("2|6|11|creditRate")){
            row.getCell(27).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)27).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("2|6|11|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost14.setCredit_rate(row.getCell((short)27).getNumericCellValue());
		}

		//String prefix = t.getMachineType()+"|"+t.getTxnType()+"|"+t.getTxnDetail()+"|";cashCost cashRate debitRate debitFeeamt creditRate
		//传统优惠刷卡
//		hrtUnnoCost15.setMachine_type(2);
//		hrtUnnoCost15.setTxn_type(6);
//		hrtUnnoCost15.setTxn_detail(12);
		//借费率
		if(null!=row.getCell(28) && !"".equals(String.valueOf(row.getCell(28))) && hrtcost.containsKey("2|6|12|debitRate")){
            row.getCell(28).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)28).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("2|6|12|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost15.setDebit_rate(row.getCell((short)28).getNumericCellValue());
		}
		//借手续费
		if(null!=row.getCell(29) && !"".equals(String.valueOf(row.getCell(29))) && hrtcost.containsKey("2|6|12|debitFeeamt")){
            row.getCell(29).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)29).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("2|6|12|debitFeeamt")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost15.setDebit_feeamt(row.getCell((short)29).getNumericCellValue());
		}
		//贷费率
		if(null!=row.getCell(30) && !"".equals(String.valueOf(row.getCell(30))) && hrtcost.containsKey("2|6|12|creditRate")){
            row.getCell(30).setCellType(cell.CELL_TYPE_NUMERIC);
			Double yidai = row.getCell((short)30).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("2|6|12|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost15.setCredit_rate(row.getCell((short)30).getNumericCellValue());
		}

		//传统减免
		if(null!=row.getCell(31) && !"".equals(String.valueOf(row.getCell(31))) && hrtcost.containsKey("2|6|13|debitRate")){
            row.getCell(31).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost16.setMachine_type(2);
//			hrtUnnoCost16.setTxn_type(6);
//			hrtUnnoCost16.setTxn_detail(13);
			//借贷费率
			Double yidai = row.getCell((short)31).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("2|6|13|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost16.setDebit_rate(row.getCell((short)31).getNumericCellValue());
//			hrtUnnoCost16.setCredit_rate(row.getCell((short)31).getNumericCellValue());
		}
		if(null!=row.getCell(31) && !"".equals(String.valueOf(row.getCell(31))) && hrtcost.containsKey("2|6|13|creditRate")){
			row.getCell(31).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost16.setMachine_type(2);
//			hrtUnnoCost16.setTxn_type(6);
//			hrtUnnoCost16.setTxn_detail(13);
			//借贷费率
			Double yidai = row.getCell((short)31).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("2|6|13|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost16.setDebit_rate(row.getCell((short)31).getNumericCellValue());
//			hrtUnnoCost16.setCredit_rate(row.getCell((short)31).getNumericCellValue());
		}

		//传统-微信
		if(null!=row.getCell(32) && !"".equals(String.valueOf(row.getCell(32))) && hrtcost.containsKey("2|6|8|debitRate")){
            row.getCell(32).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost17.setMachine_type(2);
//			hrtUnnoCost17.setTxn_type(6);
//			hrtUnnoCost17.setTxn_detail(8);
			//借贷费率
			Double yidai = row.getCell((short)32).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("2|6|8|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost17.setDebit_rate(row.getCell((short)32).getNumericCellValue());
//			hrtUnnoCost17.setCredit_rate(row.getCell((short)32).getNumericCellValue());
		}
		if(null!=row.getCell(32) && !"".equals(String.valueOf(row.getCell(32))) && hrtcost.containsKey("2|6|8|creditRate")){
			row.getCell(32).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost17.setMachine_type(2);
//			hrtUnnoCost17.setTxn_type(6);
//			hrtUnnoCost17.setTxn_detail(8);
			//借贷费率
			Double yidai = row.getCell((short)32).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("2|6|8|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost17.setDebit_rate(row.getCell((short)32).getNumericCellValue());
//			hrtUnnoCost17.setCredit_rate(row.getCell((short)32).getNumericCellValue());
		}

		//传统-支付宝
		if(null!=row.getCell(33) && !"".equals(String.valueOf(row.getCell(33))) && hrtcost.containsKey("2|6|9|debitRate")){
            row.getCell(33).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost18.setMachine_type(2);
//			hrtUnnoCost18.setTxn_type(6);
//			hrtUnnoCost18.setTxn_detail(9);
			//借贷费率
			Double yidai = row.getCell((short)33).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("2|6|9|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost18.setDebit_rate(row.getCell((short)33).getNumericCellValue());
//			hrtUnnoCost18.setCredit_rate(row.getCell((short)33).getNumericCellValue());
		}
		if(null!=row.getCell(33) && !"".equals(String.valueOf(row.getCell(33))) && hrtcost.containsKey("2|6|9|creditRate")){
			row.getCell(33).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost18.setMachine_type(2);
//			hrtUnnoCost18.setTxn_type(6);
//			hrtUnnoCost18.setTxn_detail(9);
			//借贷费率
			Double yidai = row.getCell((short)33).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("2|6|9|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost18.setDebit_rate(row.getCell((short)33).getNumericCellValue());
//			hrtUnnoCost18.setCredit_rate(row.getCell((short)33).getNumericCellValue());
		}

		//传统-银联二维码
		if(null!=row.getCell(34) && !"".equals(String.valueOf(row.getCell(34))) && hrtcost.containsKey("2|6|10|debitRate")){
            row.getCell(34).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost19.setMachine_type(2);
//			hrtUnnoCost19.setTxn_type(6);
//			hrtUnnoCost19.setTxn_detail(10);
			//借贷费率
			Double yidai = row.getCell((short)34).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("2|6|10|debitRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
                return errInfo.toString();
			}
//			hrtUnnoCost19.setDebit_rate(row.getCell((short)34).getNumericCellValue());
//			hrtUnnoCost19.setCredit_rate(row.getCell((short)34).getNumericCellValue());
		}
		if(null!=row.getCell(34) && !"".equals(String.valueOf(row.getCell(34))) && hrtcost.containsKey("2|6|10|creditRate")){
			row.getCell(34).setCellType(cell.CELL_TYPE_NUMERIC);
//			hrtUnnoCost19.setMachine_type(2);
//			hrtUnnoCost19.setTxn_type(6);
//			hrtUnnoCost19.setTxn_detail(10);
			//借贷费率
			Double yidai = row.getCell((short)34).getNumericCellValue();
			Double yunYing = Double.parseDouble(hrtcost.get("2|6|10|creditRate")+"");
			if(yidai<yunYing){
				errInfo.append("第"+errCount+"行代理成本低于运营中心成本");
				return errInfo.toString();
			}
//			hrtUnnoCost19.setDebit_rate(row.getCell((short)34).getNumericCellValue());
//			hrtUnnoCost19.setCredit_rate(row.getCell((short)34).getNumericCellValue());
		}
        return errInfo.toString();
	}

	public String clobToString(Clob clob) {
		if (clob == null) {
			return null;
		}
		try {
			Reader inStreamDoc = clob.getCharacterStream();

			char[] tempDoc = new char[(int) clob.length()];
			inStreamDoc.read(tempDoc);
			inStreamDoc.close();
			return new String(tempDoc);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException es) {
			es.printStackTrace();
		}
		return null;
	}
	/**
	 * 结算成本(非活动)查询SQL
	 * @param agentUnit
	 * @param userBean
	 * @return
	 */
	public String sql00319q(AgentUnitBean agentUnit,UserBean userBean){
		String sql="select nvl((select s.un_name from sys_unit s where s.un_lvl=1 start with s.unno = su.UNNO  connect "+ 
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),su.un_name)t1,"+
		" nvl((select s.unno from sys_unit s where s.un_lvl=1  start with s.unno = su.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),su.unno)t2,su.un_name as t3,su.unno as t4,"+
		" decode((select count(1) from hrt_unno_cost_n w where w.flag=0 and w.unno=su.unno),0,'已生效','未生效')t42,"+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 1 "+
		" and w.txn_detail = 2 and w.unno=su.unno),'')t5,"+
		" nvl((select w.cash_cost from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 1 "+
		" and w.txn_detail = 2 and w.unno=su.unno),'')t6,"+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 1  "+
		" and w.txn_detail = 1 and w.unno=su.unno),'')t7, "+
		" nvl((select w.cash_cost from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 1 "+
		" and w.txn_detail = 1 and w.unno=su.unno),'')t8, "+
		" nvl((select w.debit_rate from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 2 "+
		" and w.txn_detail = 3 and w.unno=su.unno),'')t9, "+
		" nvl((select w.debit_feeamt from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 2 "+
		" and w.txn_detail = 3 and w.unno=su.unno),'')t10, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 2 "+
		" and w.txn_detail = 3 and w.unno=su.unno),'')t11, "+
		" nvl((select w.cash_cost from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 2 "+
		" and w.txn_detail = 3 and w.unno=su.unno),'')t12, "+
		" nvl((select w.cash_rate from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 2 "+
		" and w.txn_detail = 3 and w.unno=su.unno),'')t13, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 6 "+
		" and w.txn_detail = 8 and w.unno=su.unno),'')t14, "+
		" nvl((select w.cash_cost from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 6 "+
		" and w.txn_detail = 8 and w.unno=su.unno),'')t15, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 6  "+
		" and w.txn_detail = 9 and w.unno=su.unno),'')t16, "+
		" nvl((select w.cash_cost from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 6 "+
		" and w.txn_detail = 9 and w.unno=su.unno),'')t17, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 6 "+
		" and w.txn_detail = 10 and w.unno=su.unno),'')t18, "+
		" nvl((select w.cash_cost from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 6  "+
		" and w.txn_detail = 10 and w.unno=su.unno),'')t19, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 7 "+
		" and w.txn_detail = 8 and w.unno=su.unno),'')t20, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 7 "+
		" and w.txn_detail = 9 and w.unno=su.unno),'')t21, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 7 "+
		" and w.txn_detail = 10 and w.unno=su.unno),'')t22, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 3 "+
		" and w.txn_detail = 4 and w.unno=su.unno),'')t23, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 5 "+
		" and w.txn_detail = 6 and w.unno=su.unno),'')t24, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 5 "+
		" and w.txn_detail = 7 and w.unno=su.unno),'')t25, "+
		" nvl((select w.cash_cost from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 5 "+
		" and w.txn_detail = 7 and w.unno=su.unno),'')t26, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 1 and w.txn_type = 4 "+
		" and w.txn_detail = 5 and w.unno=su.unno),'')t27, "+
		" nvl((select w.debit_rate from hrt_unno_cost_n w where w.machine_type = 2 and w.txn_type = 6 "+
		" and w.txn_detail = 11 and w.unno=su.unno),'')t28, "+
		" nvl((select w.debit_feeamt from hrt_unno_cost_n w where w.machine_type = 2 and w.txn_type = 6 "+
		" and w.txn_detail = 11 and w.unno=su.unno),'')t29, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 2 and w.txn_type = 6 "+
		" and w.txn_detail = 11 and w.unno=su.unno),'')t30, "+
		" nvl((select w.debit_rate from hrt_unno_cost_n w where w.machine_type = 2 and w.txn_type = 6 "+
		" and w.txn_detail = 12 and w.unno=su.unno),'')t31, "+
		" nvl((select w.debit_feeamt from hrt_unno_cost_n w where w.machine_type = 2 and w.txn_type = 6 "+
		" and w.txn_detail = 12 and w.unno=su.unno),'')t32, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 2 and w.txn_type = 6 "+
		" and w.txn_detail = 12 and w.unno=su.unno),'')t33, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 2 and w.txn_type = 6 "+
		" and w.txn_detail = 13 and w.unno=su.unno),'')t34, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 2 and w.txn_type = 6 "+
		" and w.txn_detail = 8 and w.unno=su.unno),'')t35, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 2 and w.txn_type = 6 "+
		" and w.txn_detail = 9 and w.unno=su.unno),'')t36, "+
		" nvl((select w.credit_rate from hrt_unno_cost_n w where w.machine_type = 2 and w.txn_type = 6 "+
		" and w.txn_detail = 10 and w.unno=su.unno),'')t37, "+
		" nvl((select w.cash_cost from hrt_unno_cost_n w where w.machine_type = 2 and w.txn_type = 6 "+
		" and w.txn_detail = 11 and w.unno=su.unno),'')t38, "+
		" nvl((select w.cash_rate from hrt_unno_cost_n w where w.machine_type = 2 and w.txn_type = 6 "+
		" and w.txn_detail = 11 and w.unno=su.unno),'')t39, "+
		" nvl((select ag.cycle from bill_agentunitinfo ag where ag.unno=su.unno),30)t40, "+
		" nvl((select ag.cashrate from bill_agentunitinfo ag where ag.unno=su.unno),'')t41 "+
		" from sys_unit su where su.unno in( "+
        " select unno from hrt_unno_cost_n where status=1 group by unno) ";
		return sql;
	}

	public String sql00319h(){
		// @author:lxg-20190911 扫码费率拆分
		String sql="select t.hucid,substr(t.unno,5) unno, " +
				"       (select s.un_name from sys_unit s where s.unno=substr(t.unno,5)) un_name," +
				"       t.machine_type, " +
				"       t.txn_type, " +
				"       t.txn_detail, " +
				"       t.debit_feeamt, " +
				"       t.debit_rate, " +
				"       t.credit_rate, " +
				"       t.cash_rate, " +
				"       t.cash_cost ,t.wx_uprate,t.wx_upcash,t.wx_uprate1,t.wx_upcash1,t.zfb_rate,t.zfb_cash,t.ewm_rate,t.ewm_cash,t.ysf_rate,t.hb_rate,t.hb_cash" +
				"  from hrt_unno_cost t " +
				" where t.unno like '"+HrtCostConstant.RGSZ_PREX+"%' ";
		return sql;
	}
	/**
	 * 运营中心结算成本查询-非活动
	 */
	public DataGridBean query00319q(AgentUnitBean agentUnit,UserBean userBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql=sql00319q(agentUnit,userBean);
		if(userBean.getUnitLvl()==0){
//			sql += " and su.unno in ( "+
//			" select unno from sys_unit where un_lvl in(1,2) "+
//			" )";
			sql += " and su.un_lvl in(1,2) ";
		}else if(userBean.getUnitLvl()==1){
			sql += " and su.unno in ( "+
			" select unno from sys_unit start with unno =:UPPUNIT "+
			" and status = 1 connect by NOCYCLE prior unno = upper_unit and un_lvl<3 "+
			" )";
			map.put("UPPUNIT", userBean.getUnNo());
		}
		if (agentUnit.getUnno() != null && !"".equals(agentUnit.getUnno())) {
			sql+=" and (su.unno=:UNNO or su.upper_unit=:UNNO) ";
			map.put("UNNO", agentUnit.getUnno());
		}
		if (agentUnit.getAgentName() != null && !"".equals(agentUnit.getAgentName())) {
			sql+=" and (su.un_name=:UN_NAME or su.upper_unit in(select s.unno from sys_unit s where s.un_name=:UN_NAME)) ";
			map.put("UN_NAME", agentUnit.getAgentName());
		}
		if (agentUnit.getAccType()!= null && !"".equals(agentUnit.getAccType())) {
			sql+=" and su.unno=:UNNO1 ";
			map.put("UNNO1", agentUnit.getAccType());
		}
		if (agentUnit.getAccTypeName()!= null && !"".equals(agentUnit.getAccTypeName())) {
			sql+=" and su.un_name=:UN_NAME1 ";
			map.put("UN_NAME1", agentUnit.getAccTypeName());
		}
		String count="select count(1) from ("+sql+")";
		List<Map<String,String>> list=agentSalesDao.queryObjectsBySqlList(sql,map,agentUnit.getPage(),agentUnit.getRows());
		BigDecimal counts = agentSalesDao.querysqlCounts(count,map);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());//查询数据库总条数
		dgd.setRows(list);
		list=null;
		return dgd;
	}

	public List<Map<String, Object>> export00319h(AgentUnitBean agentUnit,UserBean userBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql=sql00319h();

		if (agentUnit.getUnno() != null && !"".equals(agentUnit.getUnno())) {
			sql+=" and t.unno=:UNNO ";
			map.put("UNNO", "RGSZ"+agentUnit.getUnno());
		}

		List<Map<String, Object>> list=agentUnitDao.queryObjectsBySqlListMap2(sql,map);
		return list;
	}

	public DataGridBean query00319h(AgentUnitBean agentUnit,UserBean userBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql=sql00319h();

		if (agentUnit.getUnno() != null && !"".equals(agentUnit.getUnno())) {
			sql+=" and t.unno=:UNNO ";
			map.put("UNNO", HrtCostConstant.RGSZ_PREX+agentUnit.getUnno());
		}

		String count="select count(1) from ("+sql+")";
		List<Map<String,String>> list=agentSalesDao.queryObjectsBySqlList(sql,map,agentUnit.getPage(),agentUnit.getRows());
		BigDecimal counts = agentSalesDao.querysqlCounts(count,map);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());
		dgd.setRows(list);
		list=null;
		return dgd;
	}

	@Override
	public DataGridBean queryTotalTerminalGrid(AgentUnitBean agentUnit,UserBean userBean) {
		DataGridBean dataGridBean=new DataGridBean();
		if(userBean.getUseLvl()!=6){
			return dataGridBean;
		}else {
			StringBuilder sql = new StringBuilder(100);
			StringBuilder sqlCount = new StringBuilder(100);
			Map map=new HashMap();
			sql.append("select t.maintype,t.rebatetype,(select nvl(max(m.allcount),0) from bill_total_type_info m where m.rebatetype=t.rebatetype) allcount,sum(allcount1) allcount1 ,sum(allcount2) allcount2 ");
			sql.append(" from bill_total_type_info t where t.maintype=:maintype ");
			map.put("maintype",2);
			// 限制只查用户自己的活动
			if(StringUtils.isNotEmpty(userBean.getRebateType())){
			    StringBuilder rb=new StringBuilder(50);
                String[] rbTypes = userBean.getRebateType().split(",");
                for (int i = 0,n=rbTypes.length; i < n; i++) {
                    rb.append("'").append(rbTypes[i]).append("'");
                    if(i!=n-1){
                        rb.append(",");
                    }
                }
                sql.append(" and t.rebatetype in (").append(rb.toString()).append(") ");
            }

			if(StringUtils.isNotEmpty(agentUnit.getMaintainType())){
                sql.append(" and t.rebatetype=:maintainType ");
                map.put("maintainType",agentUnit.getMaintainType());
            }
			if(agentUnit.getAdate()!=null && agentUnit.getZdate()!=null){
			    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			    sql.append(" and t.txnday>=:txnday and t.txnday<=:txnday1 ");
			    map.put("txnday",df.format(agentUnit.getAdate()));
			    map.put("txnday1",df.format(agentUnit.getZdate()));
            }else{
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				sql.append(" and t.txnday=:txnday ");
				map.put("txnday",df.format(new Date()));
			}
            sql.append(" group by t.maintype,t.rebatetype ");
			sqlCount.append("select count(1) from (").append(sql.toString()).append(")");
			StringBuilder sqlSum = new StringBuilder(256);
			sqlSum.append("select nvl(sum(allcount),0) allcount,nvl(sum(allcount1),0) allcount1 ,nvl(sum(allcount2),0) allcount2  from (").append(sql.toString()).append(")");
			List<Map<String, String>> list = agentSalesDao.queryObjectsBySqlList(sql.toString(), map, agentUnit.getPage(), agentUnit.getRows());
			List<Map<String, String>> listSum = agentSalesDao.queryObjectsBySqlListMap(sqlSum.toString(),map);
			BigDecimal counts = agentSalesDao.querysqlCounts(sqlCount.toString(), map);
			List<Map<String, String>> listAll=new ArrayList<Map<String, String>>();
			listAll.add(listSum.get(0));
			listAll.addAll(list);
			dataGridBean.setTotal(counts.intValue());
			dataGridBean.setRows(listAll);
			return dataGridBean;
		}
	}

	public Map saveImportRGSZHrtCost(String xlsfile, String name, UserBean user){
		Map resutMap = new HashMap();
		int sumCount = 0;
		int errCount = 0;
		File filename = new File(xlsfile);
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);
			sumCount = sheet.getPhysicalNumberOfRows()-1;
			for(int i = 1; i <= sumCount; i++){
				HrtUnnoCostModel model = new HrtUnnoCostModel();
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell = row.getCell((short)0);
				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
                    setHrtUnnoCostModelByRow(row,model);
					String hql = "from HrtUnnoCostModel where status=-1 and unno='"+model.getUnno()+"' and machineType='"+model.getMachineType()+
                            "' and txnType='"+model.getTxnType()+"' and txnDetail='"+model.getTxnDetail()+"' ";
					List<HrtUnnoCostModel> list = hrtUnnoCostDao.queryObjectsByHqlList(hql);
					if(list.size()>0){
                        HrtUnnoCostModel updateModel=list.get(0);
                        updateModel.setCashRate(model.getCashRate());
                        updateModel.setCashCost(model.getCashCost());
                        updateModel.setCreditRate(model.getCreditRate());
                        updateModel.setDebitRate(model.getDebitRate());
                        updateModel.setDebitFeeamt(model.getDebitFeeamt());
                        updateModel.setLmby(user.getLoginName());
                        updateModel.setLmDate(new Date());
						// @author:lxg-20190910 扫码费率拆分
						if(model.getWxUpRate()!=null) {
							updateModel.setWxUpRate(model.getWxUpRate());
						}
						if(model.getWxUpCash()!=null) {
							updateModel.setWxUpCash(model.getWxUpCash());
						}
						if(model.getWxUpRate1()!=null) {
							updateModel.setWxUpRate1(model.getWxUpRate1());
						}
						if(model.getWxUpCash1()!=null) {
							updateModel.setWxUpCash1(model.getWxUpCash1());
						}
						if(model.getZfbRate()!=null) {
							updateModel.setZfbRate(model.getZfbRate());
						}
						if(model.getZfbRate()!=null) {
							updateModel.setZfbCash(model.getZfbCash());
						}
						if(model.getEwmRate()!=null) {
							updateModel.setEwmRate(model.getEwmRate());
						}
						if(model.getEwmCash()!=null) {
							updateModel.setEwmCash(model.getEwmCash());
						}
						if(model.getYsfRate()!=null) {
							updateModel.setYsfRate(model.getYsfRate());
						}
						hrtUnnoCostDao.updateObject(updateModel);
					}else{
						model.setBuid(-1);
						model.setStatus(-1);
						model.setFlag(-1);
						model.setOperateType(-1);
						Date date = new Date();
						model.setCdate(date);
						model.setCby(user.getLoginName());
						model.setLmDate(date);
						model.setLmby(user.getLoginName());
						hrtUnnoCostDao.saveObject(model);
					}
				}
			}
		} catch (IOException e) {
			log.error("人工成本设置导入异常："+e);
		}
		resutMap.put("errCount",errCount);
		resutMap.put("sumCount",sumCount);
		return resutMap;
	}

	@Override
	public Integer deleteRGSZHrtCost(Integer hucid, UserBean user) {
		String sql = "delete from HrtUnnoCostModel where status=-1 and hucid = '"+hucid+"'";
		return hrtUnnoCostDao.executeHql(sql);
	}

	private void setHrtUnnoCostModelByRow(HSSFRow row,HrtUnnoCostModel model){
        //运营中心名称
        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
        String unName = row.getCell(0).getStringCellValue().trim();

        //运营中心机构号
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
        String unno = row.getCell(1).getStringCellValue().trim();
        String selfUnno=HrtCostConstant.RGSZ_PREX+unno;
        model.setUnno(selfUnno);

        //机器类型
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
        String machineType = row.getCell(2).getStringCellValue().trim();
        model.setMachineType(Integer.parseInt(HrtCostConstant.getKeyByTypeAndValue(HrtCostConstant.MACHINE_TYPE,machineType)));

        //交易类型
        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
        String txnType=row.getCell(3).getStringCellValue().trim();
        model.setTxnType(Integer.parseInt(HrtCostConstant.getKeyByTypeAndValue(HrtCostConstant.TXN_TYPE,txnType)));

        //产品细分
        row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
        String txnDetail=row.getCell(4).getStringCellValue().trim();
        model.setTxnDetail(Integer.parseInt(HrtCostConstant.getKeyByTypeAndValue(HrtCostConstant.TXN_DETAIL,txnDetail)));

        // 借记卡成本（%）DEBIT_RATE
        HSSFCell cell5 = row.getCell(5);
        if(cell5!=null) {
            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
            String debitRate = row.getCell(5).getStringCellValue().trim();
            if (StringUtils.isNotEmpty(debitRate))
                model.setDebitRate(new BigDecimal(debitRate + "").setScale(6, BigDecimal.ROUND_HALF_UP));
        }

        // 借记卡封项手续费 DEBIT_FEEAMT
        HSSFCell cell6 = row.getCell(6);
        if(cell6!=null) {
            row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
            String debitFeeAmt = row.getCell(6).getStringCellValue().trim();
            if (StringUtils.isNotEmpty(debitFeeAmt))
                model.setDebitFeeamt(new BigDecimal(debitFeeAmt + "").setScale(6, BigDecimal.ROUND_HALF_UP));
        }
        // 贷记卡成本（%）CREDIT_RATE
        // 借记卡封项手续费 DEBIT_FEEAMT
        HSSFCell cell7 = row.getCell(7);
        if(cell7!=null) {
            row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
            String creditRate = row.getCell(7).getStringCellValue().trim();
            if (StringUtils.isNotEmpty(creditRate))
                model.setCreditRate(new BigDecimal(creditRate + "").setScale(6, BigDecimal.ROUND_HALF_UP));
        }

        // 提现成本 CASH_COST
        HSSFCell cell8 = row.getCell(8);
        if(cell8!=null) {
            row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
            String cashCost = row.getCell(8).getStringCellValue().trim();
            if (StringUtils.isNotEmpty(cashCost))
                model.setCashCost(new BigDecimal(cashCost + "").setScale(6, BigDecimal.ROUND_HALF_UP));
        }

        // 提现手续费成本（%）CASH_RATE
        HSSFCell cell9 = row.getCell(9);
        if(cell9!=null) {
            row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
            String cashRate = row.getCell(9).getStringCellValue().trim();
            if (StringUtils.isNotEmpty(cashRate))
                model.setCashRate(new BigDecimal(cashRate + "").setScale(6, BigDecimal.ROUND_HALF_UP));
        }

		// @author:lxg-20190910 扫码费率拆分
		// 微信1000以上0.38费率(%)
		HSSFCell cell10 = row.getCell(10);
		if(cell10!=null) {
			row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
			String wxUpRate = row.getCell(10).getStringCellValue().trim();
			if (StringUtils.isNotEmpty(wxUpRate))
				model.setWxUpRate(new BigDecimal(wxUpRate + "").setScale(6, BigDecimal.ROUND_HALF_UP));
		}

		// 微信1000以上0.38转账费
		HSSFCell cell11 = row.getCell(11);
		if(cell11!=null) {
			row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
			String wxUpCash = row.getCell(11).getStringCellValue().trim();
			if (StringUtils.isNotEmpty(wxUpCash))
				model.setWxUpCash(new BigDecimal(wxUpCash + "").setScale(6, BigDecimal.ROUND_HALF_UP));
		}

		// 微信1000以上0.45费率(%)
		HSSFCell cell12 = row.getCell(12);
		if(cell12!=null) {
			row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
			String wxUpRate1 = row.getCell(12).getStringCellValue().trim();
			if (StringUtils.isNotEmpty(wxUpRate1))
				model.setWxUpRate1(new BigDecimal(wxUpRate1 + "").setScale(6, BigDecimal.ROUND_HALF_UP));
		}

		// 微信1000以上0.45转账费
		HSSFCell cell13 = row.getCell(13);
		if(cell13!=null) {
			row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
			String wxUpCash1 = row.getCell(13).getStringCellValue().trim();
			if (StringUtils.isNotEmpty(wxUpCash1))
				model.setWxUpCash1(new BigDecimal(wxUpCash1 + "").setScale(6, BigDecimal.ROUND_HALF_UP));
		}

		// 支付宝费率(%)
		HSSFCell cell14 = row.getCell(14);
		if(cell14!=null) {
			row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
			String zfbRate = row.getCell(14).getStringCellValue().trim();
			if (StringUtils.isNotEmpty(zfbRate))
				model.setZfbRate(new BigDecimal(zfbRate + "").setScale(6, BigDecimal.ROUND_HALF_UP));
		}

		// 支付宝转账费
		HSSFCell cell15 = row.getCell(15);
		if(cell15!=null) {
			row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
			String zfbCash = row.getCell(15).getStringCellValue().trim();
			if (StringUtils.isNotEmpty(zfbCash))
				model.setZfbCash(new BigDecimal(zfbCash + "").setScale(6, BigDecimal.ROUND_HALF_UP));
		}

		// 二维码费率(%)
		HSSFCell cell16 = row.getCell(16);
		if(cell16!=null) {
			row.getCell(16).setCellType(Cell.CELL_TYPE_STRING);
			String ewmRate = row.getCell(16).getStringCellValue().trim();
			if (StringUtils.isNotEmpty(ewmRate))
				model.setEwmRate(new BigDecimal(ewmRate + "").setScale(6, BigDecimal.ROUND_HALF_UP));
		}

		// 二维码转账费
		HSSFCell cell17 = row.getCell(17);
		if(cell17!=null) {
			row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
			String ewmCash = row.getCell(17).getStringCellValue().trim();
			if (StringUtils.isNotEmpty(ewmCash))
				model.setEwmCash(new BigDecimal(ewmCash + "").setScale(6, BigDecimal.ROUND_HALF_UP));
		}

		// 云闪付费率(%)
		HSSFCell cell18 = row.getCell(18);
		if(cell18!=null) {
			row.getCell(18).setCellType(Cell.CELL_TYPE_STRING);
			String ysfRate = row.getCell(18).getStringCellValue().trim();
			if (StringUtils.isNotEmpty(ysfRate))
				model.setYsfRate(new BigDecimal(ysfRate + "").setScale(6, BigDecimal.ROUND_HALF_UP));
		}

		HSSFCell cell19 = row.getCell(19);
		if(cell19!=null) {
			row.getCell(19).setCellType(Cell.CELL_TYPE_STRING);
			String hbRate = row.getCell(19).getStringCellValue().trim();
			if (StringUtils.isNotEmpty(hbRate))
				model.setHbRate(new BigDecimal(hbRate + "").setScale(6, BigDecimal.ROUND_HALF_UP));
		}

		HSSFCell cell20 = row.getCell(20);
		if(cell20!=null) {
			row.getCell(20).setCellType(Cell.CELL_TYPE_STRING);
			String hbCash = row.getCell(20).getStringCellValue().trim();
			if (StringUtils.isNotEmpty(hbCash))
				model.setHbCash(new BigDecimal(hbCash + "").setScale(6, BigDecimal.ROUND_HALF_UP));
		}
	}

	/**
	 * 运营中心结算成本查询-活动
	 */
	public DataGridBean query00319w(AgentUnitBean agentUnit,UserBean userBean) {
		Map<String, Object> map = new HashMap<String, Object>();
        // @author:lxg-20190911 扫码费率拆分
		String sql="select a.*,b.valueinteger as t5,w.debit_rate as t6,w.debit_feeamt as t7,w.credit_rate as t8,w.cash_cost as t9,decode(w.flag,1,'已生效','未生效') t10," +
				" w.wx_uprate as wxr1,w.wx_upcash as wxc1,w.wx_uprate1 as wxr2,w.wx_upcash1 as wxc2," +
				" w.zfb_rate as zfbr1,w.zfb_cash as zfbc1,w.ewm_rate as ewmr1,w.ewm_cash as ewmc1,ysf_rate as ysfr1,hb_rate as hbr1,hb_cash as hbc1 from ( "
		+"select nvl((select s.un_name from sys_unit s where s.un_lvl=1 start with s.unno = su.UNNO  connect  by "
		+"NOCYCLE s.unno = prior s.upper_unit and rownum = 1),su.un_name)t1,"
		+"nvl((select s.unno from sys_unit s where s.un_lvl=1  start with s.unno = su.UNNO  connect "
		+"by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),su.unno)t2,"
		+"su.un_name as t3,su.unno as t4 from sys_unit su where 1=1 ";
		if(userBean.getUnitLvl()==0){
			sql += "and su.un_lvl in(1,2) ";
		}else if(userBean.getUnitLvl()==1){
			sql += " and su.unno in ( "+
			" select unno from sys_unit start with unno =:UPPUNIT "+
			" and status = 1 connect by NOCYCLE prior unno = upper_unit and un_lvl<3 "+
			" )";
			map.put("UPPUNIT", userBean.getUnNo());
		}
		if (agentUnit.getUnno() != null && !"".equals(agentUnit.getUnno())) {
			sql+=" and (su.unno=:UNNO or su.upper_unit=:UNNO) ";
			map.put("UNNO", agentUnit.getUnno());
		}
		if (agentUnit.getAgentName() != null && !"".equals(agentUnit.getAgentName())) {
			sql+=" and (su.un_name=:UN_NAME or su.upper_unit in(select s.unno from sys_unit s where s.un_name=:UN_NAME)) ";
			map.put("UN_NAME", agentUnit.getAgentName());
		}
		if (agentUnit.getAccType()!= null && !"".equals(agentUnit.getAccType())) {
			sql+=" and su.unno=:UNNO1 ";
			map.put("UNNO1", agentUnit.getAccType());
		}
		if (agentUnit.getAccTypeName()!= null && !"".equals(agentUnit.getAccTypeName())) {
			sql+=" and su.un_name=:UN_NAME1 ";
			map.put("UN_NAME1", agentUnit.getAccTypeName());
		}
		sql += ")a,hrt_unno_cost_n w,bill_PurConfigure b where w.txn_detail=b.valueinteger and a.t4=w.unno "
		+"and b.valuestring='rate' ";
		if(agentUnit.getRemarks()!= null && !"".equals(agentUnit.getRemarks()) && !"ALL".equals(agentUnit.getRemarks())){
			sql += " and w.txn_detail=:REBATETYPE";
			map.put("REBATETYPE", agentUnit.getRemarks());
		}
		String count="select count(1) from ("+sql+")";
		List<Map<String,String>> list=agentSalesDao.queryObjectsBySqlList(sql,map,agentUnit.getPage(),agentUnit.getRows());
		BigDecimal counts = agentSalesDao.querysqlCounts(count,map);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());//查询数据库总条数
		dgd.setRows(list);
		list=null;
		return dgd;
	}
	/**
	 * 运营中心结算成本导出-非活动
	 */
	public List<Map<String, String>> export00319q(AgentUnitBean agentUnit,UserBean userBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql=sql00319q(agentUnit,userBean);
		if(userBean.getUnitLvl()==0){
			sql += " and su.un_lvl in(1,2) ";
		}else if(userBean.getUnitLvl()==1){
			sql += " and su.unno in ( "+
			" select unno from sys_unit start with unno =:UPPUNIT "+
			" and status = 1 connect by NOCYCLE prior unno = upper_unit and un_lvl<3 "+
			" )";
			map.put("UPPUNIT", userBean.getUnNo());
		}
		if (agentUnit.getUnno() != null && !"".equals(agentUnit.getUnno())) {
			sql+=" and (su.unno=:UNNO or su.upper_unit=:UNNO) ";
			map.put("UNNO", agentUnit.getUnno());
		}
		if (agentUnit.getAgentName() != null && !"".equals(agentUnit.getAgentName())) {
			sql+=" and (su.un_name=:UN_NAME or su.upper_unit in(select s.unno from sys_unit s where s.un_name=:UN_NAME)) ";
			map.put("UN_NAME", agentUnit.getAgentName());
		}
		if (agentUnit.getAccType()!= null && !"".equals(agentUnit.getAccType())) {
			sql+=" and su.unno=:UNNO1 ";
			map.put("UNNO1", agentUnit.getAccType());
		}
		if (agentUnit.getAccTypeName()!= null && !"".equals(agentUnit.getAccTypeName())) {
			sql+=" and su.un_name=:UN_NAME1 ";
			map.put("UN_NAME1", agentUnit.getAccTypeName());
		}
		List<Map<String, String>> list=billBpFileDao.executeSql(sql,map);
		return list;
	}
	/**
	 * 运营中心结算成本导出-活动
	 */
	public List<Map<String, Object>> export00319w(AgentUnitBean agentUnit,UserBean userBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		// @author:lxg-20190910 扫码费率拆分
		String sql="select a.*,b.valueinteger as t5,w.debit_rate as t6,w.debit_feeamt as t7,w.credit_rate as t8,w.cash_cost as t9,decode(w.flag,1,'已生效','未生效') t10," +
				" w.wx_uprate as wxr1,w.wx_upcash as wxc1,w.wx_uprate1 as wxr2,w.wx_upcash1 as wxc2," +
				" w.zfb_rate as zfbr1,w.zfb_cash as zfbc1,w.ewm_rate as ewmr1,w.ewm_cash as ewmc1,ysf_rate as ysfr1,hb_rate as hbr1,hb_cash as hbc1 from ( "
		+"select nvl((select s.un_name from sys_unit s where s.un_lvl=1 start with s.unno = su.UNNO  connect  by "
		+"NOCYCLE s.unno = prior s.upper_unit and rownum = 1),su.un_name)t1,"
		+"nvl((select s.unno from sys_unit s where s.un_lvl=1  start with s.unno = su.UNNO  connect "
		+"by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),su.unno)t2,"
		+"su.un_name as t3,su.unno as t4 from sys_unit su where 1=1 ";
		if(userBean.getUnitLvl()==0){
			sql += "and su.un_lvl in(1,2) ";
		}else if(userBean.getUnitLvl()==1){
			sql += " and su.unno in ( "+
			" select unno from sys_unit start with unno =:UPPUNIT "+
			" and status = 1 connect by NOCYCLE prior unno = upper_unit and un_lvl<3 "+
			" )";
			map.put("UPPUNIT", userBean.getUnNo());
		}
		if (agentUnit.getUnno() != null && !"".equals(agentUnit.getUnno())) {
			sql+=" and (su.unno=:UNNO or su.upper_unit=:UNNO) ";
			map.put("UNNO", agentUnit.getUnno());
		}
		if (agentUnit.getAgentName() != null && !"".equals(agentUnit.getAgentName())) {
			sql+=" and (su.un_name=:UN_NAME or su.upper_unit in(select s.unno from sys_unit s where s.un_name=:UN_NAME)) ";
			map.put("UN_NAME", agentUnit.getAgentName());
		}
		if (agentUnit.getAccType()!= null && !"".equals(agentUnit.getAccType())) {
			sql+=" and su.unno=:UNNO1 ";
			map.put("UNNO1", agentUnit.getAccType());
		}
		if (agentUnit.getAccTypeName()!= null && !"".equals(agentUnit.getAccTypeName())) {
			sql+=" and su.un_name=:UN_NAME1 ";
			map.put("UN_NAME1", agentUnit.getAccTypeName());
		}
		sql += ")a,hrt_unno_cost_n w,bill_PurConfigure b where w.txn_detail=b.valueinteger and a.t4=w.unno "
		+"and b.valuestring='rate' ";
		if(agentUnit.getRemarks()!= null && !"".equals(agentUnit.getRemarks()) && !"ALL".equals(agentUnit.getRemarks())){
			sql += " and w.txn_detail=:REBATETYPE";
			map.put("REBATETYPE", agentUnit.getRemarks());
		}
		List<Map<String, Object>> list=agentSalesDao.executeSql2(sql,map);
		return list;
	}
	/**
	 * 结算成本(活动)查询-活动条件下拉
	 */
	public DataGridBean listRebateType() {
		DataGridBean dgb = new DataGridBean();
		String sql  = "select * from bill_PurConfigure where status = 1 and type=3 and valuestring='rate' ";
		List<Map<String,String>> list = agentSalesDao.queryObjectsBySqlListMap(sql, null);
		dgb.setRows(list);
		return dgb;
	}

	/**
	 * 机构unno的当前成本/上级成本/下级最小成本获取 (当前、上下级级别<=2)
	 * @param type 1:当前成本 ----2:上级成本 3:下级最小成本 已弃用----
	 * @param unno 机构号
	 * @param unLvl 级别
	 * @param checkType 1:新增成本 2:修改成本
	 * @return
	 */
	@Override
	public Map<String,HrtUnnoCostModel> getAllHrtCostMap(int type,String unno,Integer unLvl,int checkType){
		Map<String,HrtUnnoCostModel> map = new HashMap<String, HrtUnnoCostModel>();
		if (unLvl<=2) {
			List<HrtUnnoCostModel> list = new ArrayList<HrtUnnoCostModel>();
			if(type==1) {
				if(checkType==1){
					if(DateTools.isFirstDay()){
						String hql = "from HrtUnnoCostNModel r where r.status=1 and r.unno ='" + unno + "'";
						List<HrtUnnoCostNModel> hrtUnnoCostNModels = hrtUnnoCostNDao.queryObjectsByHqlList(hql);
						for (HrtUnnoCostNModel hrtUnnoCostNModel : hrtUnnoCostNModels) {
							HrtUnnoCostModel mm = new HrtUnnoCostModel();
							BeanUtils.copyProperties(hrtUnnoCostNModel,mm);
							list.add(mm);
						}
					}else {
						String hql = "from HrtUnnoCostModel h where h.unno = ? and h.status = ?";
						list = hrtUnnoCostDao.queryObjectsByHqlList(hql, new Object[]{unno, 1});
					}
				}else if(checkType==2){
					// 修改时取下月生效表记录
					String hql = "from HrtUnnoCostNModel r where r.status=1 and r.unno ='" + unno + "'";
					List<HrtUnnoCostNModel> hrtUnnoCostNModels = hrtUnnoCostNDao.queryObjectsByHqlList(hql);
					for (HrtUnnoCostNModel hrtUnnoCostNModel : hrtUnnoCostNModels) {
						HrtUnnoCostModel mm = new HrtUnnoCostModel();
						BeanUtils.copyProperties(hrtUnnoCostNModel,mm);
						list.add(mm);
					}
				}

			}/*else if(type==2) {
				String sql="select t.* from hrt_unno_cost t,sys_unit k where t.unno=k.upper_unit and k.unno=:unno and t.status=:status";
				Map t = new HashMap();
				t.put("unno",unno);
				t.put("status",1);
				list = hrtUnnoCostDao.queryObjectsBySqlList(sql, t,HrtUnnoCostModel.class);
			}else if(type==3){
				return getSubMinHrtCostMap(unno,2);
			}*/
			for (HrtUnnoCostModel hrtUnnoCostModel : list) {
				// 剔除无效成本记录
				if (hrtUnnoCostModel.getCreditRate() != null || hrtUnnoCostModel.getDebitFeeamt()!=null
						|| hrtUnnoCostModel.getCashCost()!=null || hrtUnnoCostModel.getDebitRate()!=null
						|| hrtUnnoCostModel.getCashRate()!=null) {
					String key =hrtUnnoCostModel.getMachineType()+"|"+hrtUnnoCostModel.getTxnType()+"|"+hrtUnnoCostModel.getTxnDetail();
					map.put(key,hrtUnnoCostModel);
				}
			}
		}
		return map;
	}

	@Override
	public Map<String,HrtUnnoCostModel> getAllLimitHrtCostMap(){
		Map<String,HrtUnnoCostModel> map = new HashMap<String,HrtUnnoCostModel>();
		String limitUnno="110000";
		String checkHql="from HrtUnnoCostModel r where r.status=-1 and r.unno ='"+(HrtCostConstant.RGSZ_PREX+limitUnno)+"'";
		List<HrtUnnoCostModel> checkyunYingHrt = hrtUnnoCostDao.queryObjectsByHqlList(checkHql);
		for (HrtUnnoCostModel hrtUnnoCostModel : checkyunYingHrt) {
			if (hrtUnnoCostModel.getCreditRate() != null || hrtUnnoCostModel.getDebitFeeamt()!=null
					|| hrtUnnoCostModel.getCashCost()!=null || hrtUnnoCostModel.getDebitRate()!=null
					|| hrtUnnoCostModel.getCashRate()!=null) {
				String key =hrtUnnoCostModel.getMachineType()+"|"+hrtUnnoCostModel.getTxnType()+"|"+hrtUnnoCostModel.getTxnDetail();
				map.put(key,hrtUnnoCostModel);
			}
		}
		return map;
	}

	/**
	 * 所有子代理商的最小成本获取
	 * @param unno
	 * @param unLvl
	 * @return
	 */
	private Map<String,HrtUnnoCostModel> getSubMinHrtCostMap(String unno,Integer unLvl){
		Map<String,HrtUnnoCostModel> map = new HashMap<String, HrtUnnoCostModel>();
		if (unLvl<=2) {
			String sql="select t.* from hrt_unno_cost t,sys_unit k where t.unno=k.unno and k.upper_unit=:unno and t.status=:status";
			Map t = new HashMap();
			t.put("unno",unno);
			t.put("status",1);
			List<HrtUnnoCostModel> list = hrtUnnoCostDao.queryObjectsBySqlList(sql, t,HrtUnnoCostModel.class);
			for (HrtUnnoCostModel hrtUnnoCostModel : list) {
				// 剔除无效成本记录
				if (hrtUnnoCostModel.getCreditRate() != null || hrtUnnoCostModel.getDebitFeeamt()!=null
						|| hrtUnnoCostModel.getCashCost()!=null || hrtUnnoCostModel.getDebitRate()!=null
						|| hrtUnnoCostModel.getCashRate()!=null) {
					String key =hrtUnnoCostModel.getMachineType()+"|"+hrtUnnoCostModel.getTxnType()+"|"+hrtUnnoCostModel.getTxnDetail();
					if (map.containsKey(key)) {
						setMinHrtCost(key,map,map.get(key),hrtUnnoCostModel);
					}else {
						map.put(key, hrtUnnoCostModel);
					}
				}
			}
		}
		return map;
	}

	private void setMinHrtCost(String key,Map map,HrtUnnoCostModel oldHrtCost,HrtUnnoCostModel newHrtCost){
		if (oldHrtCost.getDebitRate() != null && newHrtCost.getDebitRate()!=null) {
			if(oldHrtCost.getDebitRate().compareTo(newHrtCost.getDebitRate())>0){
				oldHrtCost.setDebitRate(newHrtCost.getDebitRate());
			}
		}

		if (oldHrtCost.getDebitFeeamt() != null && newHrtCost.getDebitFeeamt()!=null) {
			if(oldHrtCost.getDebitFeeamt().compareTo(newHrtCost.getDebitFeeamt())>0){
				oldHrtCost.setDebitFeeamt(newHrtCost.getDebitFeeamt());
			}
		}

		if (oldHrtCost.getCreditRate() != null && newHrtCost.getCreditRate()!=null) {
			if(oldHrtCost.getCreditRate().compareTo(newHrtCost.getCreditRate())>0){
				oldHrtCost.setCreditRate(newHrtCost.getCreditRate());
			}
		}

		if (oldHrtCost.getCashCost() != null && newHrtCost.getCashCost()!=null) {
			if(oldHrtCost.getCashCost().compareTo(newHrtCost.getCashCost())<0){
				oldHrtCost.setCashCost(newHrtCost.getCashCost());
			}
		}

		if (oldHrtCost.getCashRate() != null && newHrtCost.getCashRate()!=null) {
			if (oldHrtCost.getCashRate().compareTo(newHrtCost.getCashRate())>0) {
				oldHrtCost.setCashRate(newHrtCost.getCashRate());
			}
		}
		map.put(key,oldHrtCost);
	}
	
	/**
	 * 添加导出记录
	 */
	private void saveExportLog(int type, int rows, Integer userId) {
		String sql = "insert into bill_exportLog(type,sysuser,cdate,totals) values('"+type+"','"+userId+"',sysdate,"+rows+")";
		merchantInfoDao.executeUpdate(sql);
	}


	@Override
	public DataGridBean listSalesActTermDetailGrid(AgentUnitBean agentUnit, UserBean user){
		DataGridBean dataGridBean=new DataGridBean();
		StringBuilder sql = new StringBuilder(512);
		StringBuilder sqlCount = new StringBuilder(512);
		Map map = new HashMap();
		sql.append("select * from (");
		sql.append("select m.*,")
				.append("(case when m.yunying in ('991000','j91000') then (select k.salename from bill_sale_unno k where k.unno=yidai and k.type=1 ) else (select k.salename from bill_sale_unno k where k.unno=m.yunying and k.type=0 ) end) salename ")
				.append(" from (")
				.append("select ")
				.append("(select s.unno from  sys_unit s where s.un_lvl=1 start with s.unno=t.unno connect by s.unno=prior s.upper_unit) yunying,")
				.append("(select s.un_name from  sys_unit s where s.un_lvl=1 start with s.unno=t.unno connect by s.unno=prior s.upper_unit) yunyingN,")
				.append("(select s.unno from  sys_unit s where s.un_lvl=2 start with s.unno=t.unno connect by s.unno=prior s.upper_unit) yidai,")
				.append("(select s.un_name from  sys_unit s where s.un_lvl=2 start with s.unno=t.unno connect by s.unno=prior s.upper_unit) yidaiN,")
				.append("(select s.un_name from  sys_unit s where s.unno=t.unno) unnoN,")
				.append("t.unno,t.sn,t.keyconfirmdate,t.allotconfirmdate,t.outdate,t.usedconfirmdate,t.activaday,t.rebatetype ")
				.append(" from bill_terminalinfo t ) m ");
		sql.append(" ) where 1=1 ");

		if(agentUnit.getAdate()!=null && agentUnit.getZdate()!=null){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			sql.append(" and activaday>=:activaday1 and activaday<=:activaday2 ");
			map.put("activaday1",df.format(agentUnit.getAdate()));
			map.put("activaday2",df.format(agentUnit.getZdate()));
		}else{
			return dataGridBean;
		}

		if(StringUtils.isNotEmpty(agentUnit.getRemarks())){
			sql.append(" and sn=:sn ");
			map.put("sn",agentUnit.getRemarks());
		}

		if(StringUtils.isNotEmpty(agentUnit.getLegalNum())){
			sql.append(" and yunying=:yunying ");
			map.put("yunying",agentUnit.getLegalNum());
		}

		if(StringUtils.isNotEmpty(agentUnit.getLegalAUpLoad())){
			sql.append(" and yidai=:yidai ");
			map.put("yidai",agentUnit.getLegalAUpLoad());
		}

		if(StringUtils.isNotEmpty(agentUnit.getAccType())){
			sql.append(" and rebatetype=:rebatetype ");
			map.put("rebatetype",agentUnit.getAccType());
		}

		if(StringUtils.isNotEmpty(agentUnit.getAccTypeName())){
			sql.append(" and salename=:salename ");
			map.put("salename",agentUnit.getAccTypeName());
		}

		sqlCount.append("select count(1) from (").append(sql).append(")");
		List<Map<String, Object>> list = hrtUnnoCostDao.queryObjectsBySqlList2(sql.toString(),map,agentUnit.getPage(),agentUnit.getRows());
		Integer counts = hrtUnnoCostDao.querysqlCounts2(sqlCount.toString(), map);
		dataGridBean.setRows(list);
		dataGridBean.setTotal(counts);
		return dataGridBean;
	}

	public List exportSalesActTermDetail(AgentUnitBean agentUnit, UserBean user){
		StringBuilder sql = new StringBuilder(512);
		Map map = new HashMap();
		sql.append("select * from (");
		sql.append("select m.*,")
				.append("(case when m.yunying in ('991000','j91000') then (select k.salename from bill_sale_unno k where k.unno=yidai and k.type=1 ) else (select k.salename from bill_sale_unno k where k.unno=m.yunying and k.type=0 ) end) salename ")
				.append(" from (")
				.append("select ")
				.append("(select s.unno from  sys_unit s where s.un_lvl=1 start with s.unno=t.unno connect by s.unno=prior s.upper_unit) yunying,")
				.append("(select s.un_name from  sys_unit s where s.un_lvl=1 start with s.unno=t.unno connect by s.unno=prior s.upper_unit) yunyingN,")
				.append("(select s.unno from  sys_unit s where s.un_lvl=2 start with s.unno=t.unno connect by s.unno=prior s.upper_unit) yidai,")
				.append("(select s.un_name from sys_unit s where s.un_lvl=2 start with s.unno=t.unno connect by s.unno=prior s.upper_unit) yidaiN,")
				.append("(select s.un_name from sys_unit s where s.unno=t.unno) unnoN,")
				.append("t.unno,t.sn,t.keyconfirmdate,t.allotconfirmdate,t.outdate,t.usedconfirmdate,t.activaday,t.rebatetype ")
				.append(" from bill_terminalinfo t ) m ");
		sql.append(" ) where 1=1 ");

		if(agentUnit.getAdate()!=null && agentUnit.getZdate()!=null){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			sql.append(" and activaday>=:activaday1 and activaday<=:activaday2");
			map.put("activaday1",df.format(agentUnit.getAdate()));
			map.put("activaday2",df.format(agentUnit.getZdate()));
		}else{
			return new ArrayList();
		}

		if(StringUtils.isNotEmpty(agentUnit.getRemarks())){
			sql.append(" and sn=:sn ");
			map.put("sn",agentUnit.getRemarks());
		}

		if(StringUtils.isNotEmpty(agentUnit.getLegalNum())){
			sql.append(" and yunying=:yunying ");
			map.put("yunying",agentUnit.getLegalNum());
		}

		if(StringUtils.isNotEmpty(agentUnit.getLegalAUpLoad())){
			sql.append(" and yidai=:yidai ");
			map.put("yidai",agentUnit.getLegalAUpLoad());
		}

		if(StringUtils.isNotEmpty(agentUnit.getAccType())){
			sql.append(" and rebatetype=:rebatetype ");
			map.put("rebatetype",agentUnit.getAccType());
		}

		if(StringUtils.isNotEmpty(agentUnit.getAccTypeName())){
			sql.append(" and salename=:salename ");
			map.put("salename",agentUnit.getAccTypeName());
		}
		List<Map<String, Object>> list = hrtUnnoCostDao.queryObjectsBySqlListMap2(sql.toString(),map);
		return list;
	}

	@Override
	public DataGridBean listSalesActTermSummaryGrid(AgentUnitBean agentUnit, UserBean user){
		DataGridBean dataGridBean=new DataGridBean();
		StringBuilder sql = new StringBuilder(512);
		StringBuilder sqlCount = new StringBuilder(512);
		Map map = new HashMap();
		sql.append("select * from  ( ");
		sql.append("   select unnoAA pp,(select s.un_name from sys_unit s where s.unno=unnoAA) UN_NAME,unnoLvl,rebatetype REBATETYPE,sum(allCount) ALLCOUNT,sum(noCount) NOCOUNT,sum(useCount) USECOUNT,sum(actCount) ACTCOUNT, ");
		sql.append(" (select b.salename from bill_sale_unno b where b.unno=unnoAA and rownum=1) SALENAME from  ( ");
		sql.append(" select (case when a.yunying in ('991000', 'j91000') then a.yidai else a.yunying end) unnoAA, ");
		sql.append(" (case when a.yunying in ('991000', 'j91000') then 2 else 1 end) unnoLvl,a.* from ( ");
		sql.append(" select k.unno,k.sn,k.status,k.activaday,k.rebatetype,decode(k.status,2,1,0) useCount,decode(k.status,2,0,1) noCount,1 allCount, ");
		sql.append(" (case when k.activaday>='");
		if(agentUnit.getAdate()!=null && agentUnit.getZdate()!=null){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			sql.append(df.format(agentUnit.getAdate())).append("' and k.activaday<='").append(df.format(agentUnit.getZdate())).append("' ");
			sql.append(" then 1 else 0 end) actCount, ");
		}else{
			return dataGridBean;
		}
		sql.append(" (select s.unno from sys_unit s where s.un_lvl=1 start with s.unno=k.unno connect by s.unno=prior s.upper_unit) yunying, ");
		sql.append(" (select s.unno from sys_unit s where s.un_lvl=2 start with s.unno=k.unno connect by s.unno=prior s.upper_unit) yidai ");
		sql.append(" from bill_terminalinfo k ");
        if(agentUnit.getAdate()!=null && agentUnit.getZdate()!=null){
            sql.append(" where k.keyconfirmdate<:keyconfirmdate+1 or k.keyconfirmdate is null ");
            map.put("keyconfirmdate",agentUnit.getZdate());
        }else{
            return dataGridBean;
        }

		sql.append(" ) a )group by unnoAA,unnoLvl,rebatetype ");
		sql.append(" ) where 1=1 ");

		if(StringUtils.isNotEmpty(agentUnit.getLegalNum())){
			sql.append(" and pp=:pp ");
			map.put("pp",agentUnit.getLegalNum());
		}

		if(StringUtils.isNotEmpty(agentUnit.getAccType())){
			sql.append(" and REBATETYPE=:rebatetype ");
			map.put("rebatetype",agentUnit.getAccType());
		}

		if(StringUtils.isNotEmpty(agentUnit.getAccTypeName())){
			sql.append(" and SALENAME=:salename ");
			map.put("salename",agentUnit.getAccTypeName());
		}

		sqlCount.append("select count(1) from (").append(sql).append(")");
		List<Map<String, Object>> list = hrtUnnoCostDao.queryObjectsBySqlList2(sql.toString(),map,agentUnit.getPage(),agentUnit.getRows());
		Integer counts = hrtUnnoCostDao.querysqlCounts2(sqlCount.toString(), map);
		dataGridBean.setRows(list);
		dataGridBean.setTotal(counts);
		return dataGridBean;
	}

	public List exportSalesActTermSummary(AgentUnitBean agentUnit, UserBean user) {
		StringBuilder sql = new StringBuilder(512);
		Map map = new HashMap();
		sql.append("select * from  ( ");
		sql.append("   select unnoAA pp,(select s.un_name from sys_unit s where s.unno=unnoAA) UN_NAME,unnoLvl,rebatetype REBATETYPE,sum(allCount) ALLCOUNT,sum(noCount) NOCOUNT,sum(useCount) USECOUNT,sum(actCount) ACTCOUNT, ");
		sql.append(" (select b.salename from bill_sale_unno b where b.unno=unnoAA and rownum=1) SALENAME from  ( ");
		sql.append(" select (case when a.yunying in ('991000', 'j91000') then a.yidai else a.yunying end) unnoAA, ");
		sql.append(" (case when a.yunying in ('991000', 'j91000') then 2 else 1 end) unnoLvl,a.* from ( ");
		sql.append(" select k.unno,k.sn,k.status,k.activaday,k.rebatetype,decode(k.status,2,1,0) useCount,decode(k.status,2,0,1) noCount,1 allCount, ");
		sql.append(" (case when k.activaday>='");
		if(agentUnit.getAdate()!=null && agentUnit.getZdate()!=null){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			sql.append(df.format(agentUnit.getAdate())).append("' and k.activaday<='").append(df.format(agentUnit.getZdate())).append("' ");
			sql.append(" then 1 else 0 end) actCount, ");
		}else{
			return new ArrayList();
		}
		sql.append(" (select s.unno from sys_unit s where s.un_lvl=1 start with s.unno=k.unno connect by s.unno=prior s.upper_unit) yunying, ");
		sql.append(" (select s.unno from sys_unit s where s.un_lvl=2 start with s.unno=k.unno connect by s.unno=prior s.upper_unit) yidai ");
		sql.append(" from  bill_terminalinfo k ");

        if(agentUnit.getAdate()!=null && agentUnit.getZdate()!=null){
            sql.append(" where k.keyconfirmdate<:keyconfirmdate+1 or k.keyconfirmdate is null ");
            map.put("keyconfirmdate",agentUnit.getZdate());
        }else{
            return  new ArrayList();
        }

		sql.append(" ) a )group by unnoAA,unnoLvl,rebatetype ");
		sql.append(" ) where 1=1 ");

		if(StringUtils.isNotEmpty(agentUnit.getLegalNum())){
			sql.append(" and pp=:pp ");
			map.put("pp",agentUnit.getLegalNum());
		}
		if(StringUtils.isNotEmpty(agentUnit.getAccType())){
			sql.append(" and REBATETYPE=:rebatetype ");
			map.put("rebatetype",agentUnit.getAccType());
		}

		if(StringUtils.isNotEmpty(agentUnit.getAccTypeName())){
			sql.append(" and SALENAME=:salename ");
			map.put("salename",agentUnit.getAccTypeName());
		}
		List<Map<String, Object>> list = hrtUnnoCostDao.queryObjectsBySqlListMap2(sql.toString(), map);
		return list;
	}

	@Override
	public DataGridBean listSalesUnnoListGrid(AgentUnitBean agentUnit, UserBean user){
		DataGridBean dataGridBean=new DataGridBean();
		StringBuilder sql = new StringBuilder(512);
		StringBuilder sqlCount = new StringBuilder(512);
		Map map = new HashMap();
        sql.append("select a.*,(select s.un_name from sys_unit s where s.unno=a.unno) un_name,(select s.un_lvl from sys_unit s where s.unno=a.unno) un_lvl from bill_sale_unno a where 1=1 ");

		if(StringUtils.isNotEmpty(agentUnit.getUnno())){
			sql.append(" and a.unno=:unno ");
			map.put("unno", agentUnit.getUnno());
		}
		if(StringUtils.isNotEmpty(agentUnit.getAgentName())){
			sql.append(" and a.salename=:saleName ");
			map.put("saleName", agentUnit.getAgentName());
		}

		sqlCount.append("select count(1) from (").append(sql).append(")");
		List<Map<String, Object>> list = hrtUnnoCostDao.queryObjectsBySqlList2(sql.toString(),map,agentUnit.getPage(),agentUnit.getRows());
		Integer counts = hrtUnnoCostDao.querysqlCounts2(sqlCount.toString(), map);
		dataGridBean.setRows(list);
		dataGridBean.setTotal(counts);
		return dataGridBean;
	}

	public List exportSalesUnnoList(AgentUnitBean agentUnit, UserBean user){
		StringBuilder sql = new StringBuilder(512);
		Map map = new HashMap();
        sql.append("select a.*,(select s.un_name from sys_unit s where s.unno=a.unno) un_name,(select s.un_lvl from sys_unit s where s.unno=a.unno) un_lvl from bill_sale_unno a where 1=1 ");

        if(StringUtils.isNotEmpty(agentUnit.getUnno())){
            sql.append(" and a.unno=:unno ");
            map.put("unno", agentUnit.getUnno());
        }
        if(StringUtils.isNotEmpty(agentUnit.getAgentName())){
            sql.append(" and a.salename=:saleName ");
            map.put("saleName", agentUnit.getAgentName());
        }

		List<Map<String, Object>> list = hrtUnnoCostDao.queryObjectsBySqlListMap2(sql.toString(),map);
		return list;
	}

	@Override
	public String saveImportSaleUnnoTempXls(AgentUnitBean agentUnit,String xlsfile, String name, UserBean user) {
		int sumCount = 0;
		File filename = new File(xlsfile);
		HSSFWorkbook workbook = null;
		List<JSONObject> jsonObjectList=new ArrayList<JSONObject>();
		List<Map> errList=new ArrayList<Map>();
		try {
			workbook = new HSSFWorkbook(new FileInputStream(filename));
		} catch (IOException e) {
			log.info("导入销售维护异常:"+e.getMessage());
		}

		HSSFSheet sheet = workbook.getSheetAt(0);
		sumCount = sheet.getPhysicalNumberOfRows()-1;
		for(int i = 1; i <= sumCount; i++){
			JSONObject jsonObject=new JSONObject();
//			代理类别	机构号 维护销售
			HSSFRow row = sheet.getRow(i);
			HSSFCell cell = row.getCell((short)0);
			if(cell == null || cell.toString().trim().equals("")){
				break;
			}else{
				// 代理类别 @author:lxg-20200217 代理类别还是代理级别 非直营/直营
				HSSFCell cell1= row.getCell((short)0);
				row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
				String type = row.getCell((short)0).getStringCellValue().trim();
				if("非直营".equals(type)){
                    jsonObject.put("type",0);
                }else if("直营".equals(type)){
                    jsonObject.put("type",1);
                }

				// 机构号
				row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
				String unno=row.getCell((short)1).getStringCellValue().trim();
				jsonObject.put("unno",unno);

				// 维护销售
				row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
				String saleName = row.getCell((short)2).getStringCellValue().trim();
				jsonObject.put("saleName",saleName);

//				row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
//				String rule = row.getCell((short)3).getStringCellValue().trim();
//				jsonObject.put("ruleInfo","是".equals(rule)?1:0);
//
//				row.getCell((short)4).setCellType(Cell.CELL_TYPE_STRING);
//				String group = row.getCell((short)4).getStringCellValue().trim();
//				jsonObject.put("groupInfo",group);
//
//				row.getCell((short)5).setCellType(Cell.CELL_TYPE_STRING);
//				String leader = row.getCell((short)5).getStringCellValue().trim();
//				jsonObject.put("leaderInfo","是".equals(leader)?1:0);

				String errMsg = validateSaleUnnoImport(jsonObject,i);
				if(StringUtils.isNotEmpty(errMsg)){
					return errMsg;
				}
				jsonObjectList.add(jsonObject);
			}
		}
		if (jsonObjectList.size()>0) {
			for (JSONObject object : jsonObjectList) {
//		代理级别+机构号 若已经存在 则进行替换；
//		代理级别+机构号 若不存在   则进行新增；
				List<BillSaleUnnoModel> list = billSaleUnnoDao.queryObjectsByHqlList("from BillSaleUnnoModel where unno=? ",new Object[]{object.getString("unno")});
				if(list.size()>0 && list.size()==1){
					BillSaleUnnoModel billSaleUnnoModel=list.get(0);
					billSaleUnnoModel.setUnno(object.getString("unno"));
					billSaleUnnoModel.setType(object.getInteger("type"));
					billSaleUnnoModel.setSaleName(object.getString("saleName"));
					billSaleUnnoModel.setUpdateUser(user.getUserID()+"");
					billSaleUnnoModel.setRemark1(object.getString("busId"));
					billSaleUnnoModel.setUpdateDate(new Date());
//					if(object.get("leaderInfo")!=null){
//						billSaleUnnoModel.setLeaderInfo(object.getInteger("leaderInfo"));
//					}
//					if(object.get("groupInfo")!=null){
//						billSaleUnnoModel.setGroupInfo(object.getString("groupInfo"));
//					}
//					if(object.get("ruleInfo")!=null){
//						billSaleUnnoModel.setRuleInfo(object.getInteger("ruleInfo"));
//					}
					billSaleUnnoDao.updateObject(billSaleUnnoModel);
				}else if(list.size()==0) {
					BillSaleUnnoModel billSaleUnnoModel = new BillSaleUnnoModel();
					billSaleUnnoModel.setUnno(object.getString("unno"));
//					billSaleUnnoModel.setUnLvl();
					billSaleUnnoModel.setSaleName(object.getString("saleName"));
//					billSaleUnnoModel.setLeaderInfo(object.getInteger("leaderInfo"));
//					billSaleUnnoModel.setGroupInfo(object.getString("groupInfo"));
//					billSaleUnnoModel.setRuleInfo(object.getInteger("ruleInfo"));
					billSaleUnnoModel.setSakeLvl(0);
					billSaleUnnoModel.setType(object.getInteger("type"));
					billSaleUnnoModel.setStatus(0);
					billSaleUnnoModel.setCreateUser(user.getUserID() + "");
					billSaleUnnoModel.setCreateDate(new Date());
					billSaleUnnoModel.setUpdateUser("");
					billSaleUnnoModel.setUpdateDate(new Date());
					billSaleUnnoModel.setRemark1(object.getString("busId"));
					billSaleUnnoModel.setRemark2("");
					billSaleUnnoDao.saveObject(billSaleUnnoModel);
				}
			}
		}
		return null;
	}

	private String validateSaleUnnoImport(JSONObject jsonObject,int rowNum){
		String errMsg=null;
//		代理级别+机构号 若已经存在 则进行替换；
//		代理级别+机构号 若不存在   则进行新增；

		// @author:lxg-20200219 销售是否存在
		String saleName = jsonObject.getString("saleName");
		StringBuilder sql=new StringBuilder("select a.busid from bill_agentsalesinfo a, sys_user b ")
			.append("where b.user_lvl in (1, 2) and a.user_id = b.user_id and a.salesgroup is not null and a.salename = :salename ");
		Map map=new HashMap();
		map.put("salename",saleName);
		List<Map<String, String>>  list = billSaleUnnoDao.queryObjectsBySqlListMap(sql.toString(),map);
		if(list.size()==1){
			jsonObject.put("busId",list.get(0).get("BUSID"));
		}else{
			return "第"+(rowNum+1)+"行销售不存在或存在冲突";
		}
		return errMsg;
	}

	@Override
	public DataGridBean listSalesActLiveTermDetailGrid(AgentUnitBean agentUnit, UserBean userSession) {
		DataGridBean dataGridBean=new DataGridBean();
		Map map = new HashMap();
		
		String detailSql = getSalesActLiveTermDetail(agentUnit,map);
		if(detailSql==null) {
			return dataGridBean;
		}

		String sqlCount = "select count(1) from ("+detailSql+")";
		List<Map<String, Object>> list = hrtUnnoCostDao.queryObjectsBySqlList2(detailSql.toString(),map,agentUnit.getPage(),agentUnit.getRows());
		Integer counts = hrtUnnoCostDao.querysqlCounts2(sqlCount.toString(), map);
		dataGridBean.setRows(list);
		dataGridBean.setTotal(counts);
		return dataGridBean;
	}
	
	@Override
	public List<Map<String, Object>> exportSalesActLiveTermDetail(AgentUnitBean agentUnit, UserBean userSession) {
		DataGridBean dataGridBean=new DataGridBean();
		List<Map<String, Object>> list = null;
		Map map = new HashMap();
		
		String detailSql = getSalesActLiveTermDetail(agentUnit,map);
		if(detailSql==null) {
			return list;
		}
		list = hrtUnnoCostDao.executeSql2(detailSql, map);
		return list;
	}
	
	//活跃设备月明细查询sql
	private String getSalesActLiveTermDetail(AgentUnitBean agentUnit,Map map){
		if(agentUnit.getAdate()!=null && agentUnit.getZdate()!=null){
		}else {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String sql = " SELECT * FROM (SELECT"+
				" (SELECT u.unno FROM sys_unit u where u.un_lvl = 1 start with u.unno = a.unno"+
				" connect by prior u.upper_unit = u.unno) yunying,"+
				" (SELECT u.un_name FROM sys_unit u where u.un_lvl = 1 start with u.unno = a.unno"+
				" connect by prior u.upper_unit = u.unno) yunyingn,(SELECT u.unno FROM sys_unit u where u.un_lvl = 2"+
				" start with u.unno = a.unno connect by prior u.upper_unit = u.unno) yidai,"+
				" (SELECT u.un_name FROM sys_unit u where u.un_lvl = 2"+
				" start with u.unno = a.unno connect by prior u.upper_unit = u.unno) yidain,"+
				" a.mid,a.tid,t.sn,a.txnday,t.unno,t.activaday,t.rebatetype,"+
				" row_number() over(partition by a.tid order by a.txnday) as flag"+
				" FROM check_unitdealdata_"+df.format(agentUnit.getAdate()).substring(4, 6)+" a,bill_terminalinfo t"+
				" where a.tid = t.termid";
		if(agentUnit.getAdate()!=null && agentUnit.getZdate()!=null){
			sql +=" and a.txnday>=:txnday1 and a.txnday<=:txnday2 ) te where flag=1";
			map.put("txnday1",df.format(agentUnit.getAdate()));
			map.put("txnday2",df.format(agentUnit.getZdate()));
		}else{
			return null;
		}
		
		if(StringUtils.isNotEmpty(agentUnit.getRemarks())){
			sql+=" and sn=:sn ";
			map.put("sn",agentUnit.getRemarks());
		}

		if(StringUtils.isNotEmpty(agentUnit.getLegalNum())){
			sql+=" and yunying=:yunying ";
			map.put("yunying",agentUnit.getLegalNum());
		}

		if(StringUtils.isNotEmpty(agentUnit.getLegalAUpLoad())){
			sql+=" and yidai=:yidai ";
			map.put("yidai",agentUnit.getLegalAUpLoad());
		}

		if(StringUtils.isNotEmpty(agentUnit.getAccType())){
			sql+=" and rebatetype=:rebatetype ";
			map.put("rebatetype",agentUnit.getAccType());
		}

		if(StringUtils.isNotEmpty(agentUnit.getAccTypeName())){
			sql+=" and mid=:mid ";
			map.put("mid",agentUnit.getAccTypeName());
		}
		return sql;
	}
	
	@Override
	public DataGridBean listSalesActLiveTermSumGrid(AgentUnitBean agentUnit, UserBean userSession) {
		DataGridBean dataGridBean=new DataGridBean();
		Map map = new HashMap();
		String sumSql = getSalesActLiveTermSum(agentUnit,map);
		if(sumSql==null) {
			return dataGridBean;
		}
		String sqlCount = "select count(1) from ("+sumSql+")";
		List<Map<String, Object>> list = hrtUnnoCostDao.queryObjectsBySqlList2(sumSql.toString(),map,agentUnit.getPage(),agentUnit.getRows());
		Integer counts = hrtUnnoCostDao.querysqlCounts2(sqlCount.toString(), map);
		dataGridBean.setRows(list);
		dataGridBean.setTotal(counts);
		return dataGridBean;
	}

	@Override
	public List<Map<String, Object>> exportSalesActLiveTermSum(AgentUnitBean agentUnit, UserBean userSession) {
		DataGridBean dataGridBean=new DataGridBean();
		List<Map<String, Object>> list = null;
		Map map = new HashMap();
		String sumSql = getSalesActLiveTermSum(agentUnit,map);
		if(sumSql==null) {
			return list;
		}
		list = hrtUnnoCostDao.executeSql2(sumSql, map);

		return list;
	}
	
	//活跃设备月汇总sql
	private String getSalesActLiveTermSum(AgentUnitBean agentUnit,Map map){
		if(agentUnit.getAdate()!=null && agentUnit.getZdate()!=null){
		}else {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String sql = " SELECT te.yunying,te.yunyingn,te.yidai,te.yidain,te.rebatetype,te.txnday,te.activaday,count(1) livecount"+
				" FROM (SELECT"+
				" (SELECT u.unno FROM sys_unit u where u.un_lvl = 1 start with u.unno = a.unno"+
				" connect by prior u.upper_unit = u.unno) yunying,"+
				" (SELECT u.un_name FROM sys_unit u where u.un_lvl = 1 start with u.unno = a.unno"+
				" connect by prior u.upper_unit = u.unno) yunyingn,(SELECT u.unno FROM sys_unit u where u.un_lvl = 2"+
				" start with u.unno = a.unno connect by prior u.upper_unit = u.unno) yidai,"+
				" (SELECT u.un_name FROM sys_unit u where u.un_lvl = 2"+
				" start with u.unno = a.unno connect by prior u.upper_unit = u.unno) yidain,"+
				" a.mid,a.tid,t.sn,substr(a.txnday,0,6) txnday,t.unno,substr(t.activaday,0,6) activaday,t.rebatetype,"+
				" row_number() over(partition by a.tid order by a.txnday) as flag"+
				" FROM check_unitdealdata_"+df.format(agentUnit.getAdate()).substring(4, 6)+" a,bill_terminalinfo t"+
				" where a.tid = t.termid";
		if(agentUnit.getAmountConfirmDate()!=null && agentUnit.getOpenDate()!=null) {
			sql += " and t.activaday >= :activaday1 and t.activaday <= :activaday2";
			map.put("activaday1", df.format(agentUnit.getAmountConfirmDate()));
			map.put("activaday2", df.format(agentUnit.getOpenDate()));
		}
		
		if(agentUnit.getAdate()!=null && agentUnit.getZdate()!=null){
			sql +=" and a.txnday>=:txnday1 and a.txnday<=:txnday2 ) te where flag=1";
			map.put("txnday1",df.format(agentUnit.getAdate()));
			map.put("txnday2",df.format(agentUnit.getZdate()));
		}else{
			return null;
		}

		if(StringUtils.isNotEmpty(agentUnit.getLegalNum())){
			sql+=" and yunying=:yunying ";
			map.put("yunying",agentUnit.getLegalNum());
		}

		if(StringUtils.isNotEmpty(agentUnit.getLegalAUpLoad())){
			sql+=" and yidai=:yidai ";
			map.put("yidai",agentUnit.getLegalAUpLoad());
		}

		if(StringUtils.isNotEmpty(agentUnit.getAccType())){
			sql+=" and rebatetype=:rebatetype ";
			map.put("rebatetype",agentUnit.getAccType());
		}
		sql+=" group by te.yunying,te.yunyingn,te.yidai,te.yidain,te.rebatetype,te.txnday,te.activaday";
		return sql;
	}

	@Override
	public DataGridBean rebatetypeWalletCashSwitch(AgentUnitBean agentUnit, UserBean userBean) {
		DataGridBean dataGridBean=new DataGridBean();
		Map map = new HashMap();
		
		String sql=getRebatetypeWalletCashSwitchSql(agentUnit,userBean,map);
		String sqlCount = "select count(1) from ("+sql+")";
		List<Map<String, Object>> list = agentUnitDao.queryObjectsBySqlList2(sql.toString(),map,agentUnit.getPage(),agentUnit.getRows());
		Integer counts = agentUnitDao.querysqlCounts2(sqlCount.toString(), map);
		dataGridBean.setRows(list);
		dataGridBean.setTotal(counts);
		return dataGridBean;
	}
	
	@Override
	public List<Map<String, Object>> reportRebatetypeWalletCashSwitch(AgentUnitBean agentUnit, UserBean userBean) {
		Map<String,Object> param=new HashMap<String, Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = getRebatetypeWalletCashSwitchSql(agentUnit,userBean,param);
		
		list=agentUnitDao.executeSql2(sql, param);
//		for (Map<String, Object> map : list) {
//			Object object = map.get("CASHSWITCH");
//			if(object != null && "0".equals(object.toString())) {
//				map.put("CASHSWITCH", "关");
//			}else {
//				map.put("CASHSWITCH", "开");
//			}
//			
//		}
		return list;
	}
	
	
	private String getRebatetypeWalletCashSwitchSql(AgentUnitBean agentUnit, UserBean userBean,Map map) {
		//sql中也许处理开关展示的问题，诚宜融的与普通机构不太一样
		String sql = " SELECT c.*"+
				" FROM (SELECT case when isb11030 = 1 and cashswitch = 1 then '开'"+
				" when isb11030 = 1 and cashswitch != 1 then '关'"+
				" when isb11030 = 1 and cashswitch is null then '关'"+
				" when isb11030 != 1 and cashswitch = 0 then '关'"+
				" when isb11030 != 1 and cashswitch != 0 then '开'"+
				" when isb11030 != 1 and cashswitch is null then '开'"+
				" else '其他' end as iscashswitch,"+
				" isb11030,cashswitch,unno,bno,rno,registryNo"+
				" FROM (SELECT decode(ischeng, 'b11030', 1, 0) isb11030, b.*"+
				" FROM (SELECT (SELECT u.unno FROM sys_unit u where u.unno = 'b11030'"+
				" start with u.unno = a.unno connect by prior u.upper_unit = u.unno) ischeng,"+
				" a.cashswitch,a.unno,(SELECT u.upper_unit FROM sys_unit u where u.unno = a.unno) bno,"+
				" (SELECT u.unno FROM sys_unit u where u.un_lvl = 2 start with u.unno = a.unno"+
				" connect by prior u.upper_unit = u.unno) rno,"+
				" (SELECT u.unno FROM sys_unit u where u.un_lvl = 1 start with u.unno = a.unno"+
				" connect by prior u.upper_unit = u.unno) registryNo"+
				" FROM bill_agentunitinfo a where a.agentlvl is null) b))c"+
				" where 1=1";
		if(StringUtils.isNotEmpty(agentUnit.getUnno())){
			sql+=" and unno=:unno ";
			map.put("unno",agentUnit.getUnno());
		}

		if(StringUtils.isNotEmpty(agentUnit.getBno())){
			sql+=" and bno=:bno ";
			map.put("bno",agentUnit.getBno());
		}

		if(StringUtils.isNotEmpty(agentUnit.getRno())){
			sql+=" and rno=:rno ";
			map.put("rno",agentUnit.getRno());
		}
		
		if(StringUtils.isNotEmpty(agentUnit.getRegistryNo())){
			sql+=" and registryNo=:registryNo ";
			map.put("registryNo",agentUnit.getRegistryNo());
		}
		
		if(agentUnit.getAccType()!=null&&!"".equals(agentUnit.getAccType())){
			sql+=" and iscashswitch=:iscashswitch ";
			map.put("iscashswitch","1".equals(agentUnit.getAccType())?"开":"关");
		}
		return sql;
	}

	@Override
	public String updateRebatetypeWalletCashSwitch(AgentUnitBean agentUnit, UserBean userBean) {
		String sql = "update bill_agentunitinfo a set cashswitch=:cashswitch where a.unno=:unno";
        Map map=new HashMap();
        map.put("unno",agentUnit.getUnno());
        map.put("cashswitch", agentUnit.getCashsWitch());
        //添加诚宜融判断，当是诚宜融下机构开关时，需要判定其直属一代钱包提现开关的状态
        //直属一代--开-----》可以关，开
        //		  --关-----》不允许开
        //直属一代关闭时----》其下级所有机构的提现钱包开关均关闭
        //所有的判断都是以直属一代作为判定条件
       
        //判断是否b11030下所属机构
        String IsB11030Sql = " SELECT count(1) FROM sys_unit u"+
        		" where u.unno = '"+agentUnit.getUnno()+"'"+
        		" and u.unno in(SELECT u.unno FROM sys_unit u"+
        		" start with u.unno = 'b11030'"+
        		" connect by  u.upper_unit = prior u.unno)";
        
        Integer  IsB11030Unno= agentUnitDao.querysqlCounts2(IsB11030Sql, null);
        if(IsB11030Unno>0) {
        	String UnnoUnlevelSql = IsB11030Sql+" and u.un_lvl=2";
        	Integer  unnoUnlevel = agentUnitDao.querysqlCounts2(UnnoUnlevelSql, null);
        	if(unnoUnlevel>0) {
        		agentUnit.setUnLvl("2");
        	}
        	//判断修改代理商是否为b11030下直属一代
        	//是一代修改时，关则下级所有全关，开只开自己,一代一下关则关，开需要判定其直属一代是否开
        	if("2".equals(agentUnit.getUnLvl())) {
        		//判断此次是关还是开的操作
        		if(agentUnit.getCashsWitch() != 1) {
        			sql =" update bill_agentunitinfo a set cashswitch=:cashswitch where a.unno in(SELECT u.unno FROM sys_unit u"+
        					" start with u.unno = :unno connect by  u.upper_unit = prior u.unno)";
        		}
        	}else {
        		//非一代的b11030下机构开启需要判断其直属一代开关状态
        		String IsB11030AndYiDaiSwitchSql= " SELECT unno,yidai,(SELECT a.cashswitch FROM bill_agentunitinfo a"+
            			" where a.unno = yidai ) cashswitch FROM (SELECT u.unno,(SELECT u1.unno FROM sys_unit u1 where u1.un_lvl = 2" + 
            			" start with u1.unno = u.unno"+
            			" connect by prior u1.upper_unit = u1.unno) yidai FROM sys_unit u where u.unno = '"+agentUnit.getUnno()+"')";
        		List<Map<String,Object>> executeSql2 = agentUnitDao.executeSql2(IsB11030AndYiDaiSwitchSql, null);
        		if(executeSql2.size()<1) {
        			return "无此一代";
        		}
        		Map<String, Object> map2 = executeSql2.get(0);
        		Object object = map2.get("CASHSWITCH");
        		String cashswitch= null;
        		if(object!=null) {
        			cashswitch = object.toString();
        		}
        		if(agentUnit.getCashsWitch() == 1) {
        			if(!"1".equals(cashswitch)) {
        				return agentUnit.getUnno()+"归属一代提现钱包为关闭状态";
        			}
        		}
        	}
        }
        
        Integer integer = agentUnitDao.executeSqlUpdate(sql, map);
        if(integer>0) {
        	return "活动钱包开关变更成功";
        }
        return "活动钱包开关变更失败"; 
	}
	
	@Override
    public String importrebatetypeWalletCashSwitchUpdate(String xlsfile, String name, UserBean user) throws InvocationTargetException, IllegalAccessException {
        String flag1= null;
        Map uniqeMap = new HashMap();
        int sumCount = 0;
        File filename = new File(xlsfile);
        HSSFWorkbook workbook = null;
        Map<String,String> uniqueUnno = new HashMap<String,String>();
        List<AgentUnitBean> list = new ArrayList<AgentUnitBean>();
        try {
            workbook = new HSSFWorkbook(new FileInputStream(filename));
        } catch (IOException e) {
            log.info("导入批量变更文件异常:"+e.getMessage());
        }
        HSSFSheet sheet = workbook.getSheetAt(0);
        sumCount = sheet.getPhysicalNumberOfRows()-1;
        String numberInfo="";
        for(int i = 1; i <= sumCount; i++){
            numberInfo="第"+(i+1)+"行,";
            AgentUnitBean model = new AgentUnitBean();
            HSSFRow row = sheet.getRow(i);
            HSSFCell cell = row.getCell((short)0);
            if(cell == null || cell.toString().trim().equals("")){
                break;
            }else{
                // 代理机构号
                row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
                String unno = row.getCell((short)0).getStringCellValue().trim();
                model.setUnno(unno);

                // 分润差额钱包开关
                row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
                String cashswitch=row.getCell((short)1).getStringCellValue().trim();
                if(StringUtils.isNotEmpty(cashswitch) && "开".equals(cashswitch)) {
                    model.setCashsWitch(1);
                }else{
                	model.setCashsWitch(0);
                }
                list.add(model);

                // 1、不允许有空字符；
                if(cashswitch.isEmpty()){
                	flag1 = "空字符串，禁止提交";
                    return numberInfo + flag1;
                }
                
                //查询机构是否存在
                String ishaveSql = "SELECT t.* FROM bill_agentunitinfo t where t.unno = '"+unno+"'";
                List<Map<String,String>> a1 = agentUnitDao.executeSql(ishaveSql);
                if(a1.size()<1) {
                	flag1 = unno + "不存在，请核实";
                	return flag1;
                }
                
                //查询机构是否是中心
                String iszhongxinSql = "SELECT t.* FROM bill_agentunitinfo t where t.agentlvl in(1,2) and t.unno = '"+unno+"'";
                List<Map<String,String>> a2 = agentUnitDao.executeSql(iszhongxinSql);
                if(a2.size()>0) {
                	flag1 = unno + "为中心机构，禁止修改";
                	return flag1;
                }
                
                String key=unno;
                // 2、同一文件，同一代理机构号不能出现2次及其以上，否则提示“XX代理重复”整批文件无法导入
                if(uniqeMap.containsKey(key)){
                    flag1 = unno + "代理重复提交";
                    return flag1;
                }
                uniqeMap.put(key,"1");
            }
        }
        for (AgentUnitBean model : list) {
        	String updateSql = " update bill_agentunitinfo a set a.cashswitch = '"+model.getCashsWitch()+"' where a.unno = '"+model.getUnno()+"'";
        	//添加诚宜融特殊处理
        	 //判断是否b11030下所属机构
            String IsB11030Sql = " SELECT count(1) FROM sys_unit u"+
            		" where u.unno = '"+model.getUnno()+"'"+
            		" and u.unno in(SELECT u.unno FROM sys_unit u"+
            		" start with u.unno = 'b11030'"+
            		" connect by  u.upper_unit = prior u.unno)";
            
            Integer  IsB11030Unno= agentUnitDao.querysqlCounts2(IsB11030Sql, null);
            if(IsB11030Unno>0) {
            	String UnnoUnlevelSql = IsB11030Sql+" and u.un_lvl=2";
            	Integer  unnoUnlevel = agentUnitDao.querysqlCounts2(UnnoUnlevelSql, null);
            	if(unnoUnlevel>0) {
            		model.setUnLvl("2");
            	}
            	//判断修改代理商是否为b11030下直属一代
            	//是一代修改时，关则下级所有全关，开只开自己,一代一下关则关，开需要判定其直属一代是否开
            	if("2".equals(model.getUnLvl())) {
            		//判断此次是关还是开的操作
            		if(model.getCashsWitch() != 1) {
            			updateSql =" update bill_agentunitinfo a set cashswitch="+model.getCashsWitch()+" where a.unno in(SELECT u.unno FROM sys_unit u"+
            					" start with u.unno = '"+model.getUnno()+"' connect by  u.upper_unit = prior u.unno)";
            		}
            	}else {
            		//非一代的b11030下机构开启需要判断其直属一代开关状态
            		String IsB11030AndYiDaiSwitchSql= " SELECT unno,yidai,(SELECT a.cashswitch FROM bill_agentunitinfo a"+
                			" where a.unno = yidai ) cashswitch FROM (SELECT u.unno,(SELECT u1.unno FROM sys_unit u1 where u1.un_lvl = 2" + 
                			" start with u1.unno = u.unno"+
                			" connect by prior u1.upper_unit = u1.unno) yidai FROM sys_unit u where u.unno = '"+model.getUnno()+"')";
            		List<Map<String,Object>> executeSql2 = agentUnitDao.executeSql2(IsB11030AndYiDaiSwitchSql, null);
            		if(executeSql2.size()<1) {
            			return "无此一代";
            		}
            		Map<String, Object> map2 = executeSql2.get(0);
            		Object object = map2.get("CASHSWITCH");
            		String cashswitch= null;
            		if(object!=null) {
            			cashswitch = object.toString();
            		}
            		if(model.getCashsWitch() == 1) {
            			if(!"1".equals(cashswitch)) {
            				return model.getUnno()+"归属一代提现钱包为关闭状态";
            			}
            		}
            	}
            }
            agentUnitDao.executeUpdate(updateSql);
        }
        return null;
    }

	@Override
	public DataGridBean queryAgentActiveSum(AgentUnitBean agentUnit, UserBean userSession) {
		DataGridBean dgd = new DataGridBean();
		Map<String ,Object> map = new HashMap<String, Object>();
		String sql = queryAgentActiveSumSql(agentUnit,userSession,map);
		if(sql== null || "".equals(sql)) {
			return dgd;
		}

		String sqlCount ="select count(*) from ("+sql+")";
		Integer counts = agentUnitDao.querysqlCounts2(sqlCount,map);
		List<Map<String,Object>> list=agentUnitDao.queryObjectsBySqlList2(sql, map, agentUnit.getPage(), agentUnit.getRows());
		dgd.setRows(list);
		dgd.setTotal(counts);
		return dgd;
	}

	@Override
	public List<Map<String, Object>> exportQueryAgentActiveSum(AgentUnitBean agentUnit, UserBean userSession) {
		Map<String ,Object> map = new HashMap<String, Object>();
		String sql = queryAgentActiveSumSql(agentUnit,userSession,map);
		if(sql== null || "".equals(sql)) {
			return new ArrayList();
		}
		return agentUnitDao.executeSql2(sql,map);
	}
	
	//代理商户激活量查询sql
	private String queryAgentActiveSumSql(AgentUnitBean agentUnit, UserBean userSession, Map<String, Object> map) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userSession.getUnNo());
		agentUnit.setCashDay(agentUnit.getCashDay().replace("-", ""));
		agentUnit.setCashDay1(agentUnit.getCashDay1().replace("-", ""));
		
		String sql =" SELECT b.yidai," + 
				"       AGENTNAME," + 
				"       p.producttype," + 
				"       sum(flag1) flag1," + 
				"       sum(flag2) flag2," + 
				"       sum(flag3) flag3" + 
				"  FROM (SELECT (SELECT u.unno" + 
				"                  FROM sys_unit u" + 
				"                 where u.un_lvl = 2" + 
				"                 start with u.unno = a.unno" + 
				"                connect by prior u.upper_unit = u.unno) yidai," + 
				"               (SELECT u.un_name" + 
				"                  FROM sys_unit u" + 
				"                 where u.un_lvl = 2" + 
				"                 start with u.unno = a.unno" + 
				"                connect by prior u.upper_unit = u.unno) AGENTNAME," + 
				"               unno," + 
				"               rebatetype," + 
				"               sum(flag1) flag1," + 
				"               sum(flag2) flag2," + 
				"               sum(flag3) flag3" + 
				"          FROM (SELECT unno, rebatetype, count(1) flag1, 0 flag2, 0 flag3" + 
				"                  FROM (SELECT t.rebatetype, mt.unno, mt.mid" +
				"                          FROM bill_merchantterminalinfo mt," +
				"                               bill_terminalinfo           t," +
				"                               bill_merchantinfo         m" +
				"                         where mt.tid = t.termid" + 
				"                           and m.mid = mt.mid" + 
				"                           and mt.maintaintype != 'D'" +
				"                           and m.approvedate >=" + 
				"                               to_date('"+agentUnit.getCashDay()+"', 'yyyy-mm-dd hh24:mi:ss')" + 
				"                           and m.approvedate <=" + 
				"                               to_date('"+agentUnit.getCashDay1()+"', 'yyyy-mm-dd hh24:mi:ss')+1" + 
				"                           and mt.approvedate >=" + 
				"                               to_date('"+agentUnit.getCashDay()+"', 'yyyy-mm-dd hh24:mi:ss')" + 
				"                           and mt.approvedate <=" + 
				"                               to_date('"+agentUnit.getCashDay1()+"', 'yyyy-mm-dd hh24:mi:ss')+1" + 
				"                         group by mt.unno, mt.mid, rebatetype)" + 
				"                 group by unno, rebatetype" + 
				"                union all" + 
				"                SELECT mt.unno," + 
				"                       t.rebatetype," + 
				"                       0 flag1," + 
				"                       count(1) flag2," + 
				"                       0 flag3" + 
				"                  FROM bill_merchantterminalinfo mt," + 
				"                       bill_terminalinfo           t" + 
				"                 where mt.tid = t.termid" + 
				"                   and mt.approvedate >=" + 
				"                       to_date('"+agentUnit.getCashDay()+"', 'yyyy-mm-dd hh24:mi:ss')" + 
				"                   and mt.approvedate <=" + 
				"                       to_date('"+agentUnit.getCashDay1()+"', 'yyyy-mm-dd hh24:mi:ss')+1" + 
				"                   and mt.maintaintype != 'D'" + 
				"                 group by mt.unno, t.rebatetype" + 
				"                union all" + 
				"                SELECT t.unno, t.rebatetype, 0 flag1, 0 flag2, count(1) flag3" + 
				"                  FROM bill_terminalinfo t" + 
				"                 where t.activaday >= '"+agentUnit.getCashDay()+"'" + 
				"                   and t.activaday <= '"+agentUnit.getCashDay1()+"'" + 
				"                 group by t.unno, t.rebatetype) a" + 
				"         group by unno, rebatetype) b" + 
				"  left join bill_producttypeinrebatetype p" + 
				"    on b.rebatetype = p.rebatetype" + 
				" where b.yidai is not null";
		
		if("110000".equals(userSession.getUnNo())){
		}
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else if(unitModel.getUnLvl() == 1){
			
			sql+=" and b.yidai in(SELECT u.unno FROM sys_unit u where u.un_lvl=2 " + 
					" start with u.unno = '"+unitModel.getUnNo()+"'" + 
					" connect by prior u.unno = u.upper_unit)";
		}else {
			return "";
		}
		
		if(StringUtils.isNotEmpty(agentUnit.getUnno())){
			sql+=" and yidai=:yidai ";
			map.put("yidai",agentUnit.getUnno());
		}
		
		if(StringUtils.isNotEmpty(agentUnit.getAgentName())){
			sql+=" and AGENTNAME=:AGENTNAME ";
			map.put("AGENTNAME",agentUnit.getAgentName());
		}
		sql += " group by b.yidai, p.producttype, AGENTNAME";
		return sql;
	}

	@Override
	public DataGridBean queryUserLoginActiveDetail(AgentUnitBean agentUnit, UserBean userSession) {
		DataGridBean dgd = new DataGridBean();
		Map<String ,Object> map = new HashMap<String, Object>();
		String sql = getUserLoginActiveDetailSql(agentUnit,userSession,map);

		String sqlCount ="select count(*) from ("+sql+")";
		Integer counts = agentUnitDao.querysqlCounts2(sqlCount,map);
		List<Map<String,Object>> list=agentUnitDao.queryObjectsBySqlList2(sql, map, agentUnit.getPage(), agentUnit.getRows());
		dgd.setRows(list);
		dgd.setTotal(counts);
		return dgd;
	}

	@Override
	public List<Map<String, Object>> exportUserLoginActiveDetail(AgentUnitBean agentUnit, UserBean userSession) {
		Map<String ,Object> map = new HashMap<String, Object>();
		String sql = getUserLoginActiveDetailSql(agentUnit,userSession,map);
		return agentUnitDao.executeSql2(sql,map);
	}
	
	//代理活跃及登录APP监测报表明细_查询sql
	private String getUserLoginActiveDetailSql(AgentUnitBean agentUnit, UserBean userSession, Map<String, Object> map) {
		
		agentUnit.setCashDay(agentUnit.getCashDay().replace("-", ""));
		agentUnit.setCashDay1(agentUnit.getCashDay1().replace("-", ""));
		
		String sql = " SELECT r.*" + 
				"  FROM (SELECT p.unno," + 
				"               sum(flag1) PINTAI," + 
				"               sum(flag2) APP," + 
				"               sum(flag3) ACTIVE," + 
				" (SELECT u.un_name FROM sys_unit u where u.unno = p.unno) AGENTNAME,"+
				"               (SELECT u.upper_unit" + 
				"                  FROM sys_unit u" + 
				"                 where u.unno = p.unno) guishu," + 
				"               (SELECT u.unno" + 
				"                  FROM sys_unit u" + 
				"                 where u.un_lvl = 2" + 
				"                 start with u.unno = p.unno" + 
				"                connect by prior u.upper_unit = u.unno) yidai," + 
				"               (SELECT u.unno" + 
				"                  FROM sys_unit u" + 
				"                 where u.un_lvl = 1" + 
				"                 start with u.unno = p.unno" + 
				"                connect by prior u.upper_unit = u.unno) yunying," + 
				"               (SELECT u.un_lvl FROM sys_unit u where u.unno = p.unno) unlevel" + 
				"          FROM (SELECT un.unno, 1 flag1, 0 flag2, 0 flag3" + 
				"                  FROM sys_login_log t, sys_unit_user un" + 
				"                 where t.user_id = un.user_id" + 
				"                   and t.login_type = 'L'" + 
				"                   and t.status = 1" + 
				"                   and t.isos is null" + 
				"                   and t.logintime >=" + 
				"                       to_date('"+agentUnit.getCashDay()+"', 'yyyy-mm-dd hh24:mi:ss')" + 
				"                   and t.logintime <=" + 
				"                       to_date('"+agentUnit.getCashDay1()+"', 'yyyy-mm-dd hh24:mi:ss')+1" + 
				"                 group by un.unno" + 
				"                union all" + 
				"                SELECT un.unno, 0 flag1, 1 flag2, 0 flag3" + 
				"                  FROM sys_login_log t, sys_unit_user un" + 
				"                 where t.user_id = un.user_id" + 
				"                   and t.login_type = 'L'" + 
				"                   and t.status = 1" + 
				"                   and t.isos is not null" + 
				"                   and t.logintime >=" + 
				"                       to_date('"+agentUnit.getCashDay()+"', 'yyyy-mm-dd hh24:mi:ss')" + 
				"                   and t.logintime <=" + 
				"                       to_date('"+agentUnit.getCashDay1()+"', 'yyyy-mm-dd hh24:mi:ss')+1" + 
				"                 group by un.unno" + 
				"                union all" + 
				"                SELECT unno, 0 flag1, 0 flag2, 1 flag3" + 
				"                  FROM (SELECT a.unno" + 
				"                          FROM pg_unno_txn a" + 
				"                         where a.txnday >= '"+agentUnit.getCashDay()+"'" + 
				"                           and a.txnday <= '"+agentUnit.getCashDay1()+"'" + 
				"                         group by a.unno" + 
				"                        union all" + 
				"                        SELECT a.unno" + 
				"                          FROM pg_unno_wechat a" + 
				"                         where a.txnday >= '"+agentUnit.getCashDay()+"'" + 
				"                           and a.txnday <= '"+agentUnit.getCashDay1()+"'" + 
				"                         group by a.unno)" + 
				"                 group by unno) p" + 
				"         group by p.unno) r where 1=1";
		if(StringUtils.isNotEmpty(agentUnit.getUnno())){
			sql+=" and unno=:unno ";
			map.put("unno",agentUnit.getUnno());
		}
		if(StringUtils.isNotEmpty(agentUnit.getParentUnno())){
			sql+=" and guishu=:guishu ";
			map.put("guishu",agentUnit.getParentUnno());
		}
		if(StringUtils.isNotEmpty(agentUnit.getBno())){
			sql+=" and yidai=:yidai ";
			map.put("yidai",agentUnit.getBno());
		}
		if(StringUtils.isNotEmpty(agentUnit.getRno())){
			sql+=" and yunying=:yunying ";
			map.put("yunying",agentUnit.getRno());
		}
		return sql;
	}

	@Override
	public DataGridBean queryUserLoginActiveSum(AgentUnitBean agentUnit, UserBean userSession) {
		DataGridBean dgd = new DataGridBean();
		Map<String ,Object> map = new HashMap<String, Object>();
		String sql = getUserLoginActiveSumSql(agentUnit,userSession,map);

		String sqlCount ="select count(*) from ("+sql+")";
		Integer counts = agentUnitDao.querysqlCounts2(sqlCount,map);
		List<Map<String,Object>> list=agentUnitDao.queryObjectsBySqlList2(sql, map, agentUnit.getPage(), agentUnit.getRows());
		dgd.setRows(list);
		dgd.setTotal(counts);
		return dgd;
	}

	@Override
	public List<Map<String, Object>> exportUserLoginActiveSum(AgentUnitBean agentUnit, UserBean userSession) {
		Map<String ,Object> map = new HashMap<String, Object>();
		String sql = getUserLoginActiveSumSql(agentUnit,userSession,map);
		return agentUnitDao.executeSql2(sql,map);
	}
	
	//代理活跃及登录APP监测报表汇总_查询sql
	private String getUserLoginActiveSumSql(AgentUnitBean agentUnit, UserBean userSession, Map<String, Object> map) {
		
		agentUnit.setCashDay(agentUnit.getCashDay().replace("-", ""));
		agentUnit.setCashDay1(agentUnit.getCashDay1().replace("-", ""));
		
		String sql = " SELECT t1.unlevel," + 
				"       t1.UNNOCOUNT," + 
				"       nvl(t2.PCLOGINCOUNT, 0) PCLOGINCOUNT," + 
				"       nvl(t2.APPLOGINCOUNT, 0) APPLOGINCOUNT," + 
				"       nvl(t2.ACTIVECOUNT, 0) ACTIVECOUNT," + 
				"       trunc(nvl(t2.PCLOGINCOUNT, 0) / UNNOCOUNT,5) s1," + 
				"       trunc(nvl(t2.APPLOGINCOUNT, 0) / UNNOCOUNT,5) s2," + 
				"       trunc(nvl(t2.ACTIVECOUNT, 0) / UNNOCOUNT,5) s3" + 
				"  FROM (SELECT u.un_lvl unlevel, count(1) UNNOCOUNT" + 
				"          FROM sys_unit u where 1=1";
			if(StringUtils.isNotEmpty(agentUnit.getCashDay1())) {
				sql+=" and u.create_date <= to_date('"+agentUnit.getCashDay1()+"', 'yyyy-mm-dd hh24:mi:ss')";
			}
		
			if(StringUtils.isNotEmpty(agentUnit.getRno())){
				sql+=" and u.unno in (SELECT u.unno FROM sys_unit u "
						+ " start with u.unno = :yunying connect by prior u.unno = u.upper_unit)";
				map.put("yunying",agentUnit.getRno());
			}
			sql+="         group by u.un_lvl) t1" + 
				"  left join (SELECT r.unlevel," + 
				"                    sum(flag1) PCLOGINCOUNT," + 
				"                    sum(flag2) APPLOGINCOUNT," + 
				"                    sum(flag3) ACTIVECOUNT" + 
				"               FROM (SELECT p.unno," + 
				"                            sum(flag1) flag1," + 
				"                            sum(flag2) flag2," + 
				"                            sum(flag3) flag3," + 
				"                            (SELECT u.un_lvl" + 
				"                               FROM sys_unit u" + 
				"                              where u.unno = p.unno) unlevel" + 
				"                       FROM (SELECT un.unno, 1 flag1, 0 flag2, 0 flag3" + 
				"                               FROM sys_login_log t," + 
				"                                    sys_unit_user un" + 
				"                              where t.user_id = un.user_id" + 
				"                                and t.login_type = 'L'" + 
				"                                and t.status = 1" + 
				"                                and t.isos is null" + 
				"                                and t.logintime >=" + 
				"                                    to_date('"+agentUnit.getCashDay()+"'," + 
				"                                            'yyyy-mm-dd hh24:mi:ss')" + 
				"                                and t.logintime <=" + 
				"                                    to_date('"+agentUnit.getCashDay1()+"'," + 
				"                                            'yyyy-mm-dd hh24:mi:ss')+1" + 
				"                              group by un.unno" + 
				"                             union all" + 
				"                             SELECT un.unno, 0 flag1, 1 flag2, 0 flag3" + 
				"                               FROM sys_login_log t," + 
				"                                    sys_unit_user un" + 
				"                              where t.user_id = un.user_id" + 
				"                                and t.login_type = 'L'" + 
				"                                and t.status = 1" + 
				"                                and t.isos is not null" + 
				"                                and t.logintime >=" + 
				"                                    to_date('"+agentUnit.getCashDay()+"'," + 
				"                                            'yyyy-mm-dd hh24:mi:ss')" + 
				"                                and t.logintime <=" + 
				"                                    to_date('"+agentUnit.getCashDay1()+"'," + 
				"                                            'yyyy-mm-dd hh24:mi:ss')+1" + 
				"                              group by un.unno" + 
				"                             union all" + 
				"                             SELECT unno, 0 flag1, 0 flag2, 1 flag3" + 
				"                               FROM (SELECT a.unno" + 
				"                                       FROM pg_unno_txn a" + 
				"                                      where a.txnday >= '"+agentUnit.getCashDay()+"'" + 
				"                                        and a.txnday <= '"+agentUnit.getCashDay1()+"'" + 
				"                                      group by a.unno" + 
				"                                     union all" + 
				"                                     SELECT a.unno" + 
				"                                       FROM pg_unno_wechat a" + 
				"                                      where a.txnday >= '"+agentUnit.getCashDay()+"'" + 
				"                                        and a.txnday <= '"+agentUnit.getCashDay1()+"'" + 
				"                                      group by a.unno)" + 
				"                              group by unno) p";
			if(StringUtils.isNotEmpty(agentUnit.getRno())){
				sql+=" where p.unno in (SELECT u.unno FROM sys_unit u "
						+ " start with u.unno = :yunying connect by prior u.unno = u.upper_unit)";
				map.put("yunying",agentUnit.getRno());
			}
		sql+="                      group by p.unno) r" + 
				"              group by r.unlevel) t2" + 
				"    on t1.unlevel = t2.unlevel" + 
				" order by t1.unlevel";
		return sql;
	}
}
