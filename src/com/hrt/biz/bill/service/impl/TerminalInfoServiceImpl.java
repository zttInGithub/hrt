package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.bill.dao.IBillTerminalSimDao;
import com.hrt.biz.bill.entity.model.BillTerminalSimModel;
import com.hrt.frame.constant.PhoneProdConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.dao.ITerminalInfoDao;
import com.hrt.biz.bill.entity.model.HrtUnnoCostModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.TerminalInfoModel;
import com.hrt.biz.bill.entity.pagebean.TerminalInfoBean;
import com.hrt.biz.bill.service.IAgentUnitService;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.bill.service.ITerminalInfoService;
import com.hrt.biz.check.entity.model.CheckProfitTemplateMicroModel;
import com.hrt.biz.check.service.CheckUnitProfitMicroService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;
import com.hrt.util.StringUtil;

public class TerminalInfoServiceImpl implements ITerminalInfoService{

	private ITerminalInfoDao terminalInfoDao;

	private IUnitDao unitDao;

	private IMerchantInfoDao merchantInfoDao;

	private IBillTerminalSimDao billTerminalSimDao;

	private IMerchantInfoService merchantInfoService;
	private IAgentUnitService agentUnitService;
	private CheckUnitProfitMicroService checkUnitProfitMicroService;
	private static final Log log = LogFactory.getLog(TerminalInfoServiceImpl.class);

	public IBillTerminalSimDao getBillTerminalSimDao() {
		return billTerminalSimDao;
	}

	public void setBillTerminalSimDao(IBillTerminalSimDao billTerminalSimDao) {
		this.billTerminalSimDao = billTerminalSimDao;
	}

	public CheckUnitProfitMicroService getCheckUnitProfitMicroService() {
		return checkUnitProfitMicroService;
	}

	public void setCheckUnitProfitMicroService(CheckUnitProfitMicroService checkUnitProfitMicroService) {
		this.checkUnitProfitMicroService = checkUnitProfitMicroService;
	}

	public IAgentUnitService getAgentUnitService() {
		return agentUnitService;
	}

	public void setAgentUnitService(IAgentUnitService agentUnitService) {
		this.agentUnitService = agentUnitService;
	}

	public IUnitDao getUnitDao() {
		return unitDao;
	}

	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}

	public ITerminalInfoDao getTerminalInfoDao() {
		return terminalInfoDao;
	}

	public void setTerminalInfoDao(ITerminalInfoDao terminalInfoDao) {
		this.terminalInfoDao = terminalInfoDao;
	}

	public IMerchantInfoDao getMerchantInfoDao() {
		return merchantInfoDao;
	}

	public void setMerchantInfoDao(IMerchantInfoDao merchantInfoDao) {
		this.merchantInfoDao = merchantInfoDao;
	}

	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}


	/**
	 * 修改
	 */
	public void updateTerminalInfo(String status,String bid){
		TerminalInfoModel terminalInfoModel=new TerminalInfoModel();
		terminalInfoModel.setBtID(Integer.parseInt(bid));
		terminalInfoModel=terminalInfoDao.getObjectByID(TerminalInfoModel.class, terminalInfoModel.getBtID());
		terminalInfoModel.setUsedConfirmDate(new Date());
		terminalInfoModel.setStatus(2);
		terminalInfoDao.updateObject(terminalInfoModel);
	}

	/**
	 * 查询全部
	 */
	public DataGridBean queryAgentSales(TerminalInfoBean bean){
		String hql = "from TerminalInfoModel where maintaintype!='D' and keyconfirmdate is not null and allotconfirmdate is not null and usedconfirmdate is null";
		String hqlCount = "select count(*) from TerminalInfoModel where maintaintype!='D' and keyconfirmdate is not null and allotconfirmdate is not null and usedconfirmdate is null";

		Map<String, Object> map = new HashMap<String, Object>();
		if (bean.getTermID() != null && !"".equals(bean.getTermID().trim())) {
			hql +=" and termID = :termID";
			hqlCount += " and termID = :termID";
			map.put("termID", bean.getTermID());
		} 

		if (bean.getTermIDStart() != null && !"".equals(bean.getTermIDStart())) {
			hql +=" AND termID >= :termIDStart";
			hqlCount += " AND termID >= :termIDStart";
			map.put("termIDStart", bean.getTermIDStart());
		} 

		if (bean.getTermIDEnd() != null && !"".equals(bean.getTermIDEnd())) {
			hql +=" AND termID <= :termIDEnd";
			hqlCount += " AND termID <= :termIDEnd";
			map.put("termIDEnd", bean.getTermIDEnd());
		}

		if (bean.getSort() != null) {
			hql += " order by " + bean.getSort() + " " + bean.getOrder();
		}

		long counts = terminalInfoDao.queryCounts(hqlCount, map);
		List<TerminalInfoModel> roleList = terminalInfoDao.queryTerminalInfos(hql, map, bean.getPage(), bean.getRows());

		DataGridBean dataGridBean = formatToDataGrid(roleList, counts);

		return dataGridBean;
	}

	@Override
	public List<Map<String, Object>> getUnitGodes(String unLvl,UserBean user) {

		String sql="SELECT t.UNNO,t.UN_NAME FROM SYS_UNIT  t WHERE 1=1 ";
		//权限
		String childUnno="";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if( unitModel.getUnLvl() == 0){
			sql+=" AND t.UN_LVL IN(1,2) ";
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
				sql+=" AND t.UN_LVL IN(1,2) ";
			}
		}else{
			childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
			sql+="AND t.UNNO IN ("+childUnno+")";
		}
		if(unLvl!=null && !"".equals(unLvl)){
			sql+=" AND t.UN_LVL IN("+unLvl+")";
		}
		sql+=" AND t.STATUS=1 order by t.UN_NAME";
		List<Map<String, Object>> unlist=terminalInfoDao.queryObjectsBySqlList(sql);
		return unlist;
	}

	@Override
	public List<Map<String, Object>> getUnitGodesByQ(String nameCode,String unLvl,UserBean user) {

		String sql="SELECT t.UNNO,t.UN_NAME FROM SYS_UNIT  t WHERE 1=1 ";
		//权限
		String childUnno="";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if( unitModel.getUnLvl() == 0){
			sql+=" AND t.UN_LVL IN(1,2) ";
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
				sql+=" AND t.UN_LVL IN(1,2) ";
			}
		}else{
			childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
			sql+="AND t.UNNO IN ("+childUnno+")";
		}
		if(unLvl!=null && !"".equals(unLvl)){
			sql+=" AND t.UN_LVL IN("+unLvl+")";
		}
		if(nameCode !=null){
			sql += " AND (t.UNNO LIKE '%" + nameCode.replaceAll("'", "") + "%' OR t.UN_NAME LIKE '%" + nameCode.replaceAll("'", "")+ "%') ";
		}
		sql+=" AND t.STATUS=1 order by t.UN_NAME";
		List<Map<String, Object>> unlist=terminalInfoDao.queryObjectsBySqlList(sql);
		return unlist;
	}

	@Override
	public List<Map<String, Object>> getUnitGodes3(String unLvl,UserBean user) {

		String sql = " SELECT t.UNNO,t.UN_NAME FROM SYS_UNIT  t WHERE (t.upper_unit in ('991000','111000','341000','121000') or "+
				" t.UN_LVL in (1) ) and t.unno not in ('111000','341000','121000') ";
		//权限
		String childUnno="";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if( unitModel.getUnLvl() == 0){
		}else{
			childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
			sql+="AND t.UNNO IN ("+childUnno+")";
		}
		if(unLvl!=null && !"".equals(unLvl)){
			sql+=" AND t.UN_LVL IN("+unLvl+")";
		}
		sql+=" AND t.STATUS=1 order by t.UN_NAME";
		List<Map<String, Object>> unlist=terminalInfoDao.queryObjectsBySqlList(sql);
		return unlist;
	}

	@Override
	public List<Map<String, Object>> getUnitGodes2(String unLvl,UserBean user) {
		String sql="SELECT t.UNNO,t.UN_NAME FROM SYS_UNIT  t WHERE 1=1 AND t.UN_LVL="+unLvl;
		sql+=" and t.STATUS=1 order by t.UN_NAME";
		List<Map<String, Object>> unlist=terminalInfoDao.queryObjectsBySqlList(sql);
		return unlist;
	}

	@Override
	public synchronized Integer addInfo(TerminalInfoBean bean, UserBean user,String params) throws Exception{
		Integer success_num = 0;
		String suffix="0000000000000000";
		String[] str=params.split("#");//8#100001#2#12
		String lvel=str[0];//首位
		String unno=str[1];
		String keyType=str[2];
		Integer num=Integer.valueOf(str[3]);
		//		String code=getCodeByUnno(unno);//简码
		//		if(code==null||"".equals(code)){
		//			throw new IllegalAccessError("该机构简码为空！不能生成终端号！");
		//		}
		//		String seqStr="";
		//		UnitModel info=getSeqNoByUnno(unno);
		Date date=new Date();
		String type="";
		if("5".equals(lvel)){//分公司
			type = "Tid1";
		}else{
			//一代
			type = "Tid2";
		}
		Integer maxTermid = getTidMax(type);
		for(int i=0;i<num;i++){
			TerminalInfoModel model=new TerminalInfoModel();
			model.setUnNO(unno);
			model.setStatus(0);
			model.setKeyType(keyType);
			model.setMaintainUID(user.getUserID());
			model.setMaintainDate(date);
			model.setKeyConfirmDate(date);
			model.setMaintainType("A");
			model.setIsM35(0);
			//			if("5".equals(lvel)){//分公司
			//				if(keyType!=null&&"1".equals(keyType)){//生成短密钥
			//					seqStr="0000"+(info.getSeqNo()+i+1);
			//					keyContext+=seqStr.substring(seqStr.length()-5, seqStr.length());
			//					model.setKeyContext(StringUtil.toHexString(keyContext));
			//				}else{//生成长密钥30000开始
			//					seqStr="0000"+(info.getSeqNo2()+i+1);
			//					keyContext+=seqStr.substring(seqStr.length()-5, seqStr.length());
			//					model.setKeyContext(StringUtil.toHexString(keyContext)+suffix);
			//				}
			//			}else if("7".equals(lvel)){//代理商
			//				if(code.length()<3){
			//					keyContext+="0";//代理商简码为2位后面补0
			//				}
			//				if(keyType!=null&&"1".equals(keyType)){//生成短密钥
			//					seqStr="000"+(info.getSeqNo()+i+1);
			//					keyContext+=seqStr.substring(seqStr.length()-4, seqStr.length());
			//					model.setKeyContext(StringUtil.toHexString(keyContext));
			//				}else{//生成长密钥3000开始
			//					seqStr="000"+(info.getSeqNo2()+i+1);
			//					keyContext+=seqStr.substring(seqStr.length()-4, seqStr.length());
			//					model.setKeyContext(StringUtil.toHexString(keyContext)+suffix);
			//				}
			//				
			//			}

			maxTermid++;
			if(keyType!=null&&"1".equals(keyType)){
				//生成短密钥
				model.setKeyContext(StringUtil.toHexString(maxTermid.toString()));
			}else{
				//生成长密钥30000开始
				model.setKeyContext(StringUtil.toHexString(maxTermid.toString())+suffix);
			}
			//生成tid是否存在于终端表
			String sql_unno="select count(1) from bill_terminalinfo t where t.termid='"+maxTermid+"'";
			BigDecimal sql_num = unitDao.querysqlCounts(sql_unno, null);
			if(sql_num.intValue()>0){
				success_num = i;
				break;
			}
			model.setTermID(maxTermid.toString());

			terminalInfoDao.saveObject(model);
		}
		Integer i = terminalInfoDao.executeSqlUpdate("update sys_configure set minfo2='"+maxTermid+"' WHERE groupName='"+type+"'", null);
		System.out.println("传统sn导入变更默认tid :"+i);

		return success_num;
	}

	/**
	 * 根据机构号得到机构简码
	 */
	public String getCodeByUnno(String unno){
		String code="";
		String sql="SELECT UNITCODE FROM SYS_UNIT WHERE UNNO='"+unno+"'";
		List list=unitDao.queryProvince(sql);
		if(list.size()>0){
			code=list.get(0).toString();
		}
		return code;
	}

	/**
	 * 根据机构号得到机构信息
	 */
	public UnitModel getSeqNoByUnno(String unno){
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("unNO", unno);
		String hql=" FROM UnitModel WHERE unNO=:unNO";
		List<UnitModel> list=unitDao.queryObjectsByHqlList(hql, param);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}

	}

	/**
	 * 方法功能：格式化MachineInfoModel为datagrid数据格式
	 * 参数：machineInfoList
	 *     total 总记录数
	 */
	private DataGridBean formatToDataGrid(List<TerminalInfoModel> list, long total) {
		List<TerminalInfoBean> beanList = new ArrayList<TerminalInfoBean>();
		for(TerminalInfoModel model : list) {
			TerminalInfoBean bean = new TerminalInfoBean();
			BeanUtils.copyProperties(model, bean);

			UnitModel unitModel=unitDao.getObjectByID(UnitModel.class, model.getUnNO());
			if(unitModel != null){
				bean.setUnitName(unitModel.getUnitName());
			}
			beanList.add(bean);
		}

		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(beanList);

		return dgb;
	}

	/**
	 * 方法功能：格式化MachineInfoModel为datagrid数据格式
	 * 参数：machineInfoList
	 *     total 总记录数
	 */
	private DataGridBean formatToDataGrid1(List<TerminalInfoModel> list, long total,List<Map<String, String>> queryList) {
		List<TerminalInfoBean> beanList = new ArrayList<TerminalInfoBean>();
		for(TerminalInfoModel model : list) {
			TerminalInfoBean bean = new TerminalInfoBean();
			BeanUtils.copyProperties(model, bean);
			UnitModel unitModel=unitDao.getObjectByID(UnitModel.class, model.getUnNO());
			if(unitModel != null){
				bean.setUnitName(unitModel.getUnitName());
			}
			bean.setType("0");
			for(int i = 0;i < queryList.size();i++){
				if(model.getRebateType() != null && model.getRebateType().toString().equals(queryList.get(i).get("VALUESTRING")))
					bean.setType("1");
			}
			beanList.add(bean);
		}

		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(beanList);

		return dgb;
	}

	@Override
	public DataGridBean findConfirm(TerminalInfoBean bean) {
		Map<String, Object> params=new HashMap<String, Object>();
		String hql = "";
		String hqlCount = "";
		if(bean.getUnNO() != null && !"".equals(bean.getUnNO())){
			hql+="from TerminalInfoModel where 1 = 1 AND keyConfirmDate is null";
			hqlCount+="select count(*) from TerminalInfoModel where 1 = 1  AND keyConfirmDate is null";
		}
		if(bean.getUnNO() != null && !"".equals(bean.getUnNO())){
			hql+=" AND unNO = :unNO";
			hqlCount+=" AND unNO = :unNO";
			params.put("unNO", bean.getUnNO());
		}
		if (bean.getSort() != null) {
			hql += " order by " + bean.getSort() + " " + bean.getOrder();
		}
		long counts = 0;
		List<TerminalInfoModel> list = new ArrayList<TerminalInfoModel>();
		if(bean.getUnNO() != null && !"".equals(bean.getUnNO())){
			counts = terminalInfoDao.queryCounts(hqlCount, params);
			list = terminalInfoDao.queryObjectsByHqlList(hql, params, bean.getPage(), bean.getRows());
		}
		DataGridBean dataGridBean = formatToDataGrid(list, counts);
		return dataGridBean;
	}

	@Override
	public HSSFWorkbook exportConfirm(String unno) {
		String sql=" SELECT t.TERMID,t.KEYTYPE,t.KEYCONTEXT FROM BILL_TERMINALINFO t WHERE t.unno='"+unno+"'" +
				" and t.keyConfirmDate is null and t.ism35=0";
		List<Map<String, Object>> data=terminalInfoDao.queryObjectsBySqlList(sql);
		List<String> excelHeader=new ArrayList<String>();
		Map<String, Object> headMap=new HashMap<String, Object>();
		excelHeader.add("TERMID");
		excelHeader.add("KEYTYPE");
		excelHeader.add("KEYCONTEXT");
		headMap.put("TERMID", "终端编号");
		headMap.put("KEYTYPE", "密钥类型(1短密钥,2长密钥)");
		headMap.put("KEYCONTEXT", "密钥明文");
		return ExcelUtil.export("终端号", data, excelHeader, headMap);
	}

	@Override
	public void editConfirm(String unno) {
		Map<String, Object> params=new HashMap<String, Object>();
		Date date=new Date();
		params.put("keyConfirmDate", date);
		String hql=" UPDATE TerminalInfoModel SET keyConfirmDate=:keyConfirmDate WHERE keyConfirmDate is null and isM35=0 and unno='"+unno+"'";
		terminalInfoDao.executeHql(hql,params);
	}

	@Override
	public DataGridBean findAllot(TerminalInfoBean bean,UserBean user) {
		Map<String, Object> params=new HashMap<String, Object>();
		String hql = "";
		String hqlCount = "";

		if((bean.getUnNO() != null && !"".equals(bean.getUnNO())) || (bean.getTermIDStart() != null && !"".equals(bean.getTermIDStart())) || (bean.getTermIDEnd() != null && !"".equals(bean.getTermIDEnd()))){
			hql = "from TerminalInfoModel where 1 = 1 AND keyConfirmDate is not null AND allotConfirmDate is null";
			hqlCount = "select count(*) from TerminalInfoModel where 1 = 1  AND keyConfirmDate is not null AND allotConfirmDate is null";
		}

		if (bean.getTermIDStart() != null && !"".equals(bean.getTermIDStart())) {
			hql +=" AND termID >= :termIDStart";
			hqlCount += " AND termID >= :termIDStart";
			params.put("termIDStart", bean.getTermIDStart());
		} 

		if (bean.getTermIDEnd() != null && !"".equals(bean.getTermIDEnd())) {
			hql +=" AND termID <= :termIDEnd";
			hqlCount += " AND termID <= :termIDEnd";
			params.put("termIDEnd", bean.getTermIDEnd());
		}

		if(bean.getUnNO()!=null&&!"".equals(bean.getUnNO())){
			hql+=" AND unNO=:unNO";
			hqlCount+=" AND unNO=:unNO";
			params.put("unNO", bean.getUnNO());
		}else{//权限控制
			List<Object> list= unitDao.queryUnno(user);
			if(list.size()>0&&!"110000".equals(list.get(0))){
				String unnos="";
				for (Object object : list) {
					unnos+=object+",";
				}
				hql += " AND unNO in ("+unnos.substring(0, unnos.length()-1)+")" ;
				hqlCount += " AND unNO in ("+unnos.substring(0, unnos.length()-1)+")" ;

			}

		}
		if (bean.getSort() != null) {
			hql += " order by " + bean.getSort() + " " + bean.getOrder();
		}
		long counts = 0;
		List<TerminalInfoModel> list = new ArrayList<TerminalInfoModel>();
		if((bean.getUnNO() != null && !"".equals(bean.getUnNO())) || (bean.getTermIDStart() != null && !"".equals(bean.getTermIDStart())) || (bean.getTermIDEnd() != null && !"".equals(bean.getTermIDEnd()))){
			counts = terminalInfoDao.queryCounts(hqlCount, params);
			list = terminalInfoDao.queryObjectsByHqlList(hql,params);
		}
		DataGridBean dataGridBean = formatToDataGrid(list, counts);
		return dataGridBean;
	}

	@Override
	public void editAllot(String ids) {
		Map<String, Object> params=new HashMap<String, Object>();
		Date date=new Date();
		params.put("allotConfirmDate", date);
		String hql=" UPDATE TerminalInfoModel SET allotConfirmDate=:allotConfirmDate ,status=1  WHERE BTID IN ("+ids+")";
		terminalInfoDao.executeHql(hql,params);
	}

	@Override
	public DataGridBean findUse(TerminalInfoBean bean,UserBean user) {
		Map<String, Object> params=new HashMap<String, Object>();
		String sql = "select * from bill_terminalinfo where 1 = 1";
		String sqlCount = "select count(*) from bill_terminalinfo where 1 = 1  ";
		if (bean.getTermIDStart() != null && !"".equals(bean.getTermIDStart())) {
			sql +=" AND termID >= :termIDStart";
			sqlCount += " AND termID >= :termIDStart";
			params.put("termIDStart", bean.getTermIDStart());
		} 

		if (bean.getTermIDEnd() != null && !"".equals(bean.getTermIDEnd())) {
			sql +=" AND termID <= :termIDEnd";
			sqlCount += " AND termID <= :termIDEnd";
			params.put("termIDEnd", bean.getTermIDEnd());
		}

		if(bean.getUnNO()!=null&&!"".equals(bean.getUnNO())){
			sql+=" AND unNO ='"+bean.getUnNO()+"' ";
			sqlCount+=" AND unNO ='"+bean.getUnNO()+"' ";
		}
		if(bean.getBatchID()!=null&&!"".equals(bean.getBatchID())){
			sql+=" AND batchID ='"+bean.getBatchID()+"' ";
			sqlCount+=" AND batchID ='"+bean.getBatchID()+"' ";
		}
		if(bean.getTerOrderID()!=null&&!"".equals(bean.getTerOrderID())){
			sql+=" AND terOrderID ='"+bean.getTerOrderID()+"' ";
			sqlCount+=" AND terOrderID ='"+bean.getTerOrderID()+"' ";
		}
		if(bean.getUnNO1()!=null&&!"".equals(bean.getUnNO1())){
			String  childUnno1=merchantInfoService.queryUnitUnnoUtil(bean.getUnNO1());
			sql += " AND unNO in ("+childUnno1+")" ;
			sqlCount += " AND unNO in ("+childUnno1+")" ;
		}
		if(bean.getMachineModel()!=null&&!"".equals(bean.getMachineModel())){
			sql += " AND machineModel='"+bean.getMachineModel()+"' " ;
			sqlCount += " AND machineModel='"+bean.getMachineModel()+"' " ;
		}
		if(bean.getStorage()!=null&&!"".equals(bean.getStorage())){
			sql += " AND storage='"+bean.getStorage()+"' " ;
			sqlCount += " AND storage='"+bean.getStorage()+"' " ;
		}
		if(bean.getSnType()!=null&&!"".equals(bean.getSnType())){
			sql += " AND snType='"+bean.getSnType()+"' " ;
			sqlCount += " AND snType='"+bean.getSnType()+"' " ;
		}
		//权限控制
		//			List<Object> list= unitDao.queryUnno(user);
		//			if(list.size()>0&&!"110000".equals(list.get(0))){
		//				String unnos="";
		//				for (Object object : list) {
		//					unnos+=object+",";
		//				}
		//				hql += " AND unNO in ("+unnos.substring(0, unnos.length()-1)+")" ;
		//				hqlCount += " AND unNO in ("+unnos.substring(0, unnos.length()-1)+")" ;
		//				
		//			}
		String childUnno="";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if( unitModel.getUnLvl() == 0){
		}
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
			sql += " AND unNO in ("+childUnno+")" ;
			sqlCount += " AND unNO in ("+childUnno+")" ;
		}

		if(!StringUtil.isBlank(bean.getSnStart())){
			sql += " AND sn >= :SN_START " ;
			sqlCount += " AND sn >= :SN_START " ;
			params.put("SN_START", bean.getSnStart());
		}
		if(!StringUtil.isBlank(bean.getSnEnd())){
			sql += " AND sn <= :SN_END " ;
			sqlCount += " AND sn <= :SN_END " ;
			params.put("SN_END", bean.getSnEnd());
		}

		if(bean.getKeyConfirmDate()!=null&&!"".equals(bean.getKeyConfirmDate())){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			sql += " AND  trunc(KEYCONFIRMDATE) >= to_date('"+df.format(bean.getKeyConfirmDate())+"','yyyy-MM-dd') ";
			sqlCount += " AND  trunc(KEYCONFIRMDATE) >= to_date('"+df.format(bean.getKeyConfirmDate())+"','yyyy-MM-dd') ";
		}
		if(bean.getKeyConfirmDateEnd()!=null&&!"".equals(bean.getKeyConfirmDateEnd())){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			sql += " AND  trunc(KEYCONFIRMDATE) <= to_date('"+df.format(bean.getKeyConfirmDateEnd())+"','yyyy-MM-dd') ";
			sqlCount += " AND  trunc(KEYCONFIRMDATE) <= to_date('"+df.format(bean.getKeyConfirmDateEnd())+"','yyyy-MM-dd') ";
		}
		if(bean.getOutDate()!=null&&!"".equals(bean.getOutDate())) {
			sql += " and outDate >= to_date('"+new SimpleDateFormat("yyyyMMdd").format(bean.getOutDate())+"000000','yyyymmddhh24miss') ";
			sqlCount += " and outDate >= to_date('"+new SimpleDateFormat("yyyyMMdd").format(bean.getOutDate())+"000000','yyyymmddhh24miss') ";
		}
		if(bean.getOutDateEnd()!=null&&!"".equals(bean.getOutDateEnd())) {
			sql += " and outDate <= to_date('"+new SimpleDateFormat("yyyyMMdd").format(bean.getOutDateEnd())+"235959','yyyymmddhh24miss') ";
			sqlCount += " and outDate <= to_date('"+new SimpleDateFormat("yyyyMMdd").format(bean.getOutDateEnd())+"235959','yyyymmddhh24miss') ";
		}
		if(bean.getRebateType()!=null&&!"".equals(bean.getRebateType()) && -1!=bean.getRebateType()) {
			sql += " and rebateType='"+bean.getRebateType()+"'";
			sqlCount += " and rebateType='"+bean.getRebateType()+"'";
		}
		if(bean.getStatus()==null || "".equals(bean.getStatus())){
		}else if (bean.getStatus()==2){
			sql += " AND  status = 2 " ;
			sqlCount += " AND  status = 2 " ;
		}else if (bean.getStatus()==0){
			sql += " AND  status !=2 " ;
			sqlCount += " AND  status !=2 " ;
		}
		if(bean.getIsM35()==null || "".equals(bean.getIsM35())){
		}else if (bean.getIsM35()==1){
			sql += " AND  ism35 = 1 " ;
			sqlCount += " AND  ism35 = 1 " ;
		}else if (bean.getIsM35()==0){
			sql += " AND  ism35 != 1 and sn is null " ;
			sqlCount += " AND  ism35 != 1 and sn is null " ;
		}else if (bean.getIsM35()==2){
			sql += " AND  ism35 != 1 and sn is not null " ;
			sqlCount += " AND  ism35 != 1 and sn is not null " ;
		}
		if (bean.getSort() != null) {
			sql += " order by " + bean.getSort() + " " + bean.getOrder();
		}
		long counts = terminalInfoDao.querysqlCounts2(sqlCount, params);
		List<TerminalInfoModel> list = terminalInfoDao.queryObjectsBySqlLists(sql, params, bean.getPage(), bean.getRows(), TerminalInfoModel.class);
		DataGridBean dataGridBean = formatToDataGrid(list, counts);
		return dataGridBean;
	}

	@Override
	public DataGridBean findUse1(TerminalInfoBean bean,UserBean user) {
		Map<String, Object> params=new HashMap<String, Object>();
		String sql = "select * from bill_terminalinfo where sn not like '128%' and sn not like 'QR%' ";
		String sqlCount = "select count(*) from bill_terminalinfo where sn not like '128%' and sn not like 'QR%'  ";
		//查询极速版活动类型
		String querySql = "select valuestring from bill_purconfigure where type=8 and status = 1 ";
		List<Map<String, String>> queryList =  terminalInfoDao.executeSql(querySql);
		String resultStr = "";
		for(int i = 0;i<queryList.size();i++){
			if(i!=0){
				resultStr += ",'"+queryList.get(i).get("VALUESTRING")+"'";
			}else{
				resultStr += "'"+queryList.get(i).get("VALUESTRING")+"'";
			}
		}
		//2019/10/15 YQ新增区分秒到版和极速版
		if (bean.getType() == null || "".equals(bean.getType())||bean.getType().equals("0")){//默认查询秒到版，rebateType not in()
			if(!"".equals(resultStr)){//极速版活动类型不为空
				sql +=" and (rebateType not in ("+resultStr+") or rebateType is null) ";
				sqlCount += " and (rebateType not in ("+resultStr+") or rebateType is null) ";
			}
		}else if(bean.getType().equals("1") && !"".equals(resultStr)){//极速版，rebateType in()){
			sql +=" and (rebateType in ("+resultStr+")) ";
			sqlCount += " and (rebateType in ("+resultStr+")) ";
		}else{
			DataGridBean dgb = new DataGridBean();
			dgb.setTotal(0);
			dgb.setRows(new ArrayList<TerminalInfoModel>());
			return dgb;
		}

		if (bean.getTermIDStart() != null && !"".equals(bean.getTermIDStart())) {
			sql +=" AND termID >= :termIDStart";
			sqlCount += " AND termID >= :termIDStart";
			params.put("termIDStart", bean.getTermIDStart());
		} 

		if (bean.getTermIDEnd() != null && !"".equals(bean.getTermIDEnd())) {
			sql +=" AND termID <= :termIDEnd";
			sqlCount += " AND termID <= :termIDEnd";
			params.put("termIDEnd", bean.getTermIDEnd());
		}

		if(bean.getUnNO()!=null&&!"".equals(bean.getUnNO())){
			sql+=" AND unNO ='"+bean.getUnNO()+"' ";
			sqlCount+=" AND unNO ='"+bean.getUnNO()+"' ";
		}
		if(bean.getBatchID()!=null&&!"".equals(bean.getBatchID())){
			sql+=" AND batchID ='"+bean.getBatchID()+"' ";
			sqlCount+=" AND batchID ='"+bean.getBatchID()+"' ";
		}
		if(bean.getTerOrderID()!=null&&!"".equals(bean.getTerOrderID())){
			sql+=" AND terOrderID ='"+bean.getTerOrderID()+"' ";
			sqlCount+=" AND terOrderID ='"+bean.getTerOrderID()+"' ";
		}
		if(bean.getUnNO1()!=null&&!"".equals(bean.getUnNO1())){
			String  childUnno1=merchantInfoService.queryUnitUnnoUtil(bean.getUnNO1());
			sql += " AND unNO in ("+childUnno1+")" ;
			sqlCount += " AND unNO in ("+childUnno1+")" ;
		}
		if(bean.getMachineModel()!=null&&!"".equals(bean.getMachineModel())){
			sql += " AND machineModel='"+bean.getMachineModel()+"' " ;
			sqlCount += " AND machineModel='"+bean.getMachineModel()+"' " ;
		}
		if(bean.getStorage()!=null&&!"".equals(bean.getStorage())){
			sql += " AND storage='"+bean.getStorage()+"' " ;
			sqlCount += " AND storage='"+bean.getStorage()+"' " ;
		}
		if(bean.getSnType()!=null&&!"".equals(bean.getSnType())){
			sql += " AND snType='"+bean.getSnType()+"' " ;
			sqlCount += " AND snType='"+bean.getSnType()+"' " ;
		}
		String childUnno="";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if( unitModel.getUnLvl() == 0){
		}
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
			sql += " AND unNO in ("+childUnno+")" ;
			sqlCount += " AND unNO in ("+childUnno+")" ;
		}

		if(!StringUtil.isBlank(bean.getSnStart())){
			sql += " AND sn >= :SN_START " ;
			sqlCount += " AND sn >= :SN_START " ;
			params.put("SN_START", bean.getSnStart());
		}
		if(!StringUtil.isBlank(bean.getSnEnd())){
			sql += " AND sn <= :SN_END " ;
			sqlCount += " AND sn <= :SN_END " ;
			params.put("SN_END", bean.getSnEnd());
		}

		if(bean.getKeyConfirmDate()!=null&&!"".equals(bean.getKeyConfirmDate())){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			sql += " AND  trunc(KEYCONFIRMDATE) >= to_date('"+df.format(bean.getKeyConfirmDate())+"','yyyy-MM-dd') ";
			sqlCount += " AND  trunc(KEYCONFIRMDATE) >= to_date('"+df.format(bean.getKeyConfirmDate())+"','yyyy-MM-dd') ";
		}
		if(bean.getKeyConfirmDateEnd()!=null&&!"".equals(bean.getKeyConfirmDateEnd())){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			sql += " AND  trunc(KEYCONFIRMDATE) <= to_date('"+df.format(bean.getKeyConfirmDateEnd())+"','yyyy-MM-dd') ";
			sqlCount += " AND  trunc(KEYCONFIRMDATE) <= to_date('"+df.format(bean.getKeyConfirmDateEnd())+"','yyyy-MM-dd') ";
		}
		if(bean.getOutDate()!=null&&!"".equals(bean.getOutDate())) {
			sql += " and outDate >= to_date('"+new SimpleDateFormat("yyyyMMdd").format(bean.getOutDate())+"000000','yyyymmddhh24miss') ";
			sqlCount += " and outDate >= to_date('"+new SimpleDateFormat("yyyyMMdd").format(bean.getOutDate())+"000000','yyyymmddhh24miss') ";
		}
		if(bean.getOutDateEnd()!=null&&!"".equals(bean.getOutDateEnd())) {
			sql += " and outDate <= to_date('"+new SimpleDateFormat("yyyyMMdd").format(bean.getOutDateEnd())+"235959','yyyymmddhh24miss') ";
			sqlCount += " and outDate <= to_date('"+new SimpleDateFormat("yyyyMMdd").format(bean.getOutDateEnd())+"235959','yyyymmddhh24miss') ";
		}
		if(bean.getStatus()==null || "".equals(bean.getStatus())){
		}else if (bean.getStatus()==2){
			sql += " AND  status = 2 " ;
			sqlCount += " AND  status = 2 " ;
		}else if (bean.getStatus()==0){
			sql += " AND  status !=2 " ;
			sqlCount += " AND  status !=2 " ;
		}
		if(bean.getIsM35()==null || "".equals(bean.getIsM35())){
		}else if (bean.getIsM35()==1){
			sql += " AND  ism35 = 1 " ;
			sqlCount += " AND  ism35 = 1 " ;
		}else if (bean.getIsM35()==0){
			sql += " AND  ism35 != 1 and sn is null " ;
			sqlCount += " AND  ism35 != 1 and sn is null " ;
		}else if (bean.getIsM35()==2){
			sql += " AND  ism35 != 1 and sn is not null " ;
			sqlCount += " AND  ism35 != 1 and sn is not null " ;
		}
		if (bean.getSort() != null) {
			sql += " order by " + bean.getSort() + " " + bean.getOrder();
		}
		long counts = terminalInfoDao.querysqlCounts2(sqlCount, params);
		List<TerminalInfoModel> list = terminalInfoDao.queryObjectsBySqlLists(sql, params, bean.getPage(), bean.getRows(), TerminalInfoModel.class);
		DataGridBean dataGridBean = formatToDataGrid1(list, counts,queryList);
		return dataGridBean;
	}
	@Override
	public DataGridBean findUse2(TerminalInfoBean bean,UserBean user) {
		Map<String, Object> params=new HashMap<String, Object>();
		String sql = "select * from bill_terminalinfo where (sn like '128%' or sn like 'QR%') ";
		String sqlCount = "select count(*) from bill_terminalinfo where (sn like '128%' or sn like 'QR%')  ";
		if (bean.getTermIDStart() != null && !"".equals(bean.getTermIDStart())) {
			sql +=" AND termID >= :termIDStart";
			sqlCount += " AND termID >= :termIDStart";
			params.put("termIDStart", bean.getTermIDStart());
		} 

		if (bean.getTermIDEnd() != null && !"".equals(bean.getTermIDEnd())) {
			sql +=" AND termID <= :termIDEnd";
			sqlCount += " AND termID <= :termIDEnd";
			params.put("termIDEnd", bean.getTermIDEnd());
		}

		if(bean.getUnNO()!=null&&!"".equals(bean.getUnNO())){
			sql+=" AND unNO ='"+bean.getUnNO()+"' ";
			sqlCount+=" AND unNO ='"+bean.getUnNO()+"' ";
		}
		if(bean.getBatchID()!=null&&!"".equals(bean.getBatchID())){
			sql+=" AND batchID ='"+bean.getBatchID()+"' ";
			sqlCount+=" AND batchID ='"+bean.getBatchID()+"' ";
		}
		if(bean.getTerOrderID()!=null&&!"".equals(bean.getTerOrderID())){
			sql+=" AND terOrderID ='"+bean.getTerOrderID()+"' ";
			sqlCount+=" AND terOrderID ='"+bean.getTerOrderID()+"' ";
		}
		if(bean.getUnNO1()!=null&&!"".equals(bean.getUnNO1())){
			String  childUnno1=merchantInfoService.queryUnitUnnoUtil(bean.getUnNO1());
			sql += " AND unNO in ("+childUnno1+")" ;
			sqlCount += " AND unNO in ("+childUnno1+")" ;
		}
		if(bean.getMachineModel()!=null&&!"".equals(bean.getMachineModel())){
			sql += " AND machineModel='"+bean.getMachineModel()+"' " ;
			sqlCount += " AND machineModel='"+bean.getMachineModel()+"' " ;
		}
		if(bean.getStorage()!=null&&!"".equals(bean.getStorage())){
			sql += " AND storage='"+bean.getStorage()+"' " ;
			sqlCount += " AND storage='"+bean.getStorage()+"' " ;
		}
		if(bean.getSnType()!=null&&!"".equals(bean.getSnType())){
			sql += " AND snType='"+bean.getSnType()+"' " ;
			sqlCount += " AND snType='"+bean.getSnType()+"' " ;
		}
		String childUnno="";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if( unitModel.getUnLvl() == 0){
		}
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
			sql += " AND unNO in ("+childUnno+")" ;
			sqlCount += " AND unNO in ("+childUnno+")" ;
		}

		if(!StringUtil.isBlank(bean.getSnStart())){
			sql += " AND sn >= :SN_START " ;
			sqlCount += " AND sn >= :SN_START " ;
			params.put("SN_START", bean.getSnStart());
		}
		if(!StringUtil.isBlank(bean.getSnEnd())){
			sql += " AND sn <= :SN_END " ;
			sqlCount += " AND sn <= :SN_END " ;
			params.put("SN_END", bean.getSnEnd());
		}

		if(bean.getKeyConfirmDate()!=null&&!"".equals(bean.getKeyConfirmDate())){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			sql += " AND  trunc(KEYCONFIRMDATE) >= to_date('"+df.format(bean.getKeyConfirmDate())+"','yyyy-MM-dd') ";
			sqlCount += " AND  trunc(KEYCONFIRMDATE) >= to_date('"+df.format(bean.getKeyConfirmDate())+"','yyyy-MM-dd') ";
		}
		if(bean.getKeyConfirmDateEnd()!=null&&!"".equals(bean.getKeyConfirmDateEnd())){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			sql += " AND  trunc(KEYCONFIRMDATE) <= to_date('"+df.format(bean.getKeyConfirmDateEnd())+"','yyyy-MM-dd') ";
			sqlCount += " AND  trunc(KEYCONFIRMDATE) <= to_date('"+df.format(bean.getKeyConfirmDateEnd())+"','yyyy-MM-dd') ";
		}
		if(bean.getOutDate()!=null&&!"".equals(bean.getOutDate())) {
			sql += " and outDate >= to_date('"+new SimpleDateFormat("yyyyMMdd").format(bean.getOutDate())+"000000','yyyymmddhh24miss') ";
			sqlCount += " and outDate >= to_date('"+new SimpleDateFormat("yyyyMMdd").format(bean.getOutDate())+"000000','yyyymmddhh24miss') ";
		}
		if(bean.getOutDateEnd()!=null&&!"".equals(bean.getOutDateEnd())) {
			sql += " and outDate <= to_date('"+new SimpleDateFormat("yyyyMMdd").format(bean.getOutDateEnd())+"235959','yyyymmddhh24miss') ";
			sqlCount += " and outDate <= to_date('"+new SimpleDateFormat("yyyyMMdd").format(bean.getOutDateEnd())+"235959','yyyymmddhh24miss') ";
		}
		if(bean.getStatus()==null || "".equals(bean.getStatus())){
		}else if (bean.getStatus()==2){
			sql += " AND  status = 2 " ;
			sqlCount += " AND  status = 2 " ;
		}else if (bean.getStatus()==0){
			sql += " AND  status !=2 " ;
			sqlCount += " AND  status !=2 " ;
		}
		if(bean.getIsM35()==null || "".equals(bean.getIsM35())){
		}else if (bean.getIsM35()==1){
			sql += " AND  ism35 = 1 " ;
			sqlCount += " AND  ism35 = 1 " ;
		}else if (bean.getIsM35()==0){
			sql += " AND  ism35 != 1 and sn is null " ;
			sqlCount += " AND  ism35 != 1 and sn is null " ;
		}else if (bean.getIsM35()==2){
			sql += " AND  ism35 != 1 and sn is not null " ;
			sqlCount += " AND  ism35 != 1 and sn is not null " ;
		}
		if (bean.getSort() != null) {
			sql += " order by " + bean.getSort() + " " + bean.getOrder();
		}
		long counts = terminalInfoDao.querysqlCounts2(sqlCount, params);
		List<TerminalInfoModel> list = terminalInfoDao.queryObjectsBySqlLists(sql, params, bean.getPage(), bean.getRows(), TerminalInfoModel.class);
		DataGridBean dataGridBean = formatToDataGrid(list, counts);
		return dataGridBean;
	}

	@Override
	public DataGridBean searchTerminalInfo(String mid) throws Exception {
		String hql = "from MerchantInfoModel where mid = '"+mid+"'";
		List<MerchantInfoModel> mimlist = merchantInfoDao.queryObjectsByHqlList(hql);
		String sql = "";
		if(mimlist != null && mimlist.size() > 0){
			sql = "SELECT BTID,TERMID,decode(KEYTYPE,1,'短密钥','长密钥') KEYTYPENAME,SN FROM BILL_TERMINALINFO t WHERE t.UNNO = '"+mimlist.get(0).getUnno()+"' AND t.ALLOTCONFIRMDATE IS NOT NULL AND t.USEDCONFIRMDATE IS NULL AND MAINTAINTYPE != 'D' and ism35 !=1 ORDER BY t.BTID DESC";
		}

		List<Map<String,String>> proList = terminalInfoDao.executeSql(sql);

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

	@Override
	public DataGridBean searchTerminalInfoUnno(String unno) throws Exception {
		String sql = "SELECT BTID,TERMID,KEYCONTEXT,decode (KEYTYPE,1,'短秘钥',2,'长秘钥',3,'微POSE秘钥') KEYTYPENAME,SN FROM BILL_TERMINALINFO t WHERE t.UNNO = '"+unno+"' AND t.ALLOTCONFIRMDATE IS NOT NULL AND t.USEDCONFIRMDATE IS NULL AND MAINTAINTYPE != 'D' and ism35 !=1 ORDER BY t.BTID DESC";
		List<Map<String,String>> proList = terminalInfoDao.executeSql(sql);

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

	@Override
	public boolean checkNum(String prams) {
		String[] str=prams.split("#");//8#100001#2#12
		String lvel=str[0];//首位
		String unno=str[1];
		String keyType=str[2];
		Integer num=Integer.valueOf(str[3]);
		UnitModel info=getSeqNoByUnno(unno);

		if("5".equals(lvel)){//分公司
			if(keyType!=null&&"1".equals(keyType)){//生成短密钥
				if(info.getSeqNo()+num>29999){
					return false;
				}
			}else{//生成长密钥30000开始
				if(info.getSeqNo2()+num>99999){
					return false;
				}
			}
		}else if("7".equals(lvel)){//代理商
			if(keyType!=null&&"1".equals(keyType)){//生成短密钥
				if(info.getSeqNo()+num>2999){
					return false;
				}
			}else{//生成长密钥3000开始
				if(info.getSeqNo2()+num>9999){
					return false;
				}
			}

		}
		return true;
	}

	@Override
	public DataGridBean searchTerminalInfoUnno2(String unno) {
		String aa =unno.substring(0, unno.length()-4);
		if(aa.equals("90")){
			unno="901000";
		}
		String sql = "SELECT BTID,TERMID,KEYCONTEXT,decode(KEYTYPE,1,'短密钥','长密钥') KEYTYPENAME FROM BILL_TERMINALINFO t WHERE t.UNNO = '"+unno+"' AND t.ALLOTCONFIRMDATE IS NOT NULL AND t.USEDCONFIRMDATE IS NULL AND MAINTAINTYPE != 'D' ORDER BY t.BTID DESC";
		List<Map<String,String>> proList = terminalInfoDao.executeSql(sql);

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

	public synchronized Integer getTidMax(String type){

		String sqlMax = "SELECT minfo2 FROM sys_configure WHERE groupName='"+type+"'";
		List<Map<String, Object>> listTermid6= terminalInfoDao.queryObjectsBySqlObject(sqlMax);
		Integer maxTermid = Integer.valueOf(listTermid6.get(0).get("MINFO2").toString());
		System.out.println("获取tid源 type："+type+";maxTermid："+maxTermid);

		return maxTermid;
	}

	@Override
	public synchronized Integer updateM35SnFlag(Integer type){
		Integer i = 0;
		//status 0可进入；1不可进入
		if(type==1){
			i = terminalInfoDao.executeSqlUpdate("update sys_configure set status=1 WHERE groupName='Tid2' and ( status = 0 or status is null) ", null);
			System.out.println("sn导入锁进入；标识 :"+i);
		}else{
			i = terminalInfoDao.executeSqlUpdate("update sys_configure set status=0 WHERE groupName='Tid2' ", null);
			System.out.println("sn导入锁退出；标识 :"+i);
		}
		return i;
	}

	@Override
	public synchronized List<Map<String, String>> saveM35SnInfo(String xlsfile,String name, UserBean user) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		//同文件sn记录map
		Map<String,String> tMap = new HashMap<String,String>();
		//判断机构号
		String [] s = name.split("-");
		String unnoValue=s[0];
		//导入批次号
		String batchID=s[1];
		String type="";
		try {
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);

			UnitModel info=getSeqNoByUnno(unnoValue);
			//得到数据库中总公司和分公司最大的termid
			if(info==null){
				Map<String,String> map = new HashMap<String,String>();
				map.put("unno", unnoValue);
				map.put("remark", "机构不存在");
				list.add(map);
				return list;
			}else if(info.getUnLvl()==1){
				//分公司
				type = "Tid1";
			}else{
				//一代
				type = "Tid2";
			}
			//先判断当前机构号是三级还是原来的
			if(info.getUpperUnit3()!=null&&!"".equals(info.getUpperUnit3())){
				Map<String,String> map = new HashMap<String,String>();
				map.put("unno", unnoValue);
				map.put("remark", "请确认导入是微商户设备后，再次导入");
				list.add(map);
				return list;
			}
			//判断是否填写批次号111000-20171016-sn.xls
			if(s.length!=3||batchID==null||"".equals(batchID)){
				Map<String,String> map = new HashMap<String,String>();
				map.put("unno", unnoValue);
				map.put("remark", "请填写批次号后，再次导入");
				list.add(map);
				return list;
			}
			Integer maxTermid = getTidMax(type);
			for(int i = 1; i < sheet.getLastRowNum()+1; i++){
				TerminalInfoModel terminalModel = new TerminalInfoModel();

				HSSFRow row = sheet.getRow(i);
				String snValue="";
				String rebateType="0";
				double rate=0;
				double secondRate=0;
				HSSFCell cell = row.getCell((short)0);
				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
					//SN号
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					snValue = row.getCell((short)0).getStringCellValue().trim();
					//					//机构号
					//					row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
					//					unnoValue=row.getCell((short)1).getStringCellValue();
					//借贷卡费率
					row.getCell((short)1).setCellType(Cell.CELL_TYPE_NUMERIC);
					rate=row.getCell((short)1).getNumericCellValue()/100;
					//秒到手续费
					row.getCell((short)2).setCellType(Cell.CELL_TYPE_NUMERIC);
					secondRate=row.getCell((short)2).getNumericCellValue();
					//返利类型 1循环送；2返款； 0无返利;3分期 ；4押金
					row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
					rebateType = row.getCell((short)3).getStringCellValue().trim();
					//押金
					//					row.getCell((short)4).setCellType(Cell.CELL_TYPE_NUMERIC);
					//					depositFlag=row.getCell((short)4).getNumericCellValue();
					try{
						if(!"".equals(rebateType)){
							terminalModel.setRebateType(Integer.valueOf(rebateType));
						}
					}catch(Exception e){
						Map<String,String> map = new HashMap<String,String>();
						String sn = snValue;
						String unno=unnoValue;
						map.put("sn", sn);
						map.put("unno", unno);
						map.put("remark", "返利类型不合规");
						list.add(map);
						continue;
					}
					String sql = "select sn,unno from bill_terminalinfo where sn='"+snValue+"'"; 
					List<Map<String, Object>> snlist=terminalInfoDao.queryObjectsBySqlObject(sql);
					if(rate<0.005d){
						Map<String,String> map = new HashMap<String,String>();
						String sn = snValue;
						String unno=unnoValue;
						map.put("sn", sn);
						map.put("unno", unno);
						map.put("remark", "费率低于0.5%");
						list.add(map);
						continue;
					}
					if(snlist.size()>0||tMap.containsKey(snValue)){
						Map<String,String> map = new HashMap<String,String>();
						String sn = snValue;
						String unno=unnoValue;
						map.put("sn", sn);
						map.put("unno", unno);
						map.put("remark", "sn号重复");
						list.add(map);
						continue;
					}else{
						maxTermid++;
						//生成tid是否存在于终端表
						String sql_unno="select count(1) from bill_terminalinfo t where t.termid='"+maxTermid+"'";
						BigDecimal num = unitDao.querysqlCounts(sql_unno, null);
						if(num.intValue()>0){
							//
							Map<String,String> map = new HashMap<String,String>();
							String sn = snValue;					
							map.put("sn", sn);
							map.put("unno", unnoValue);
							map.put("remark", "手刷终端号已满，原表以下数据皆失败");
							list.add(map);
							break;
						}
						terminalModel.setTermID(maxTermid.toString());
						terminalModel.setUnNO(unnoValue);
						terminalModel.setSn(snValue);
						terminalModel.setRate(rate);
						terminalModel.setSecondRate(secondRate);
						terminalModel.setKeyType("3");
						terminalModel.setIsM35(1);
						terminalModel.setMaintainUID(user.getUserID());
						terminalModel.setKeyConfirmDate(new Date());
						terminalModel.setAllotConfirmDate(new Date());
						terminalModel.setMaintainType("A");
						terminalModel.setBatchID(batchID);
						// 出库终端配置
//						String amtSql="select k.* from bill_purconfigure k where k.type=3 and k.valueinteger=:valueinteger ";
//						Map amtMap = new HashMap();
//						if(StringUtils.isNotEmpty(rebateType)) {
//							amtMap.put("valueinteger", rebateType);
//							List<Map<String, Object>> listAmt = terminalInfoDao.queryObjectsBySqlListMap2(amtSql, amtMap);
//							if (listAmt.size() == 1 ) {
//								if(null != listAmt.get(0).get("DEPOSITAMT")) {
//									terminalModel.setDepositAmt(Integer.parseInt(listAmt.get(0).get("DEPOSITAMT") + ""));
//								}
								// @author:lxg-20200506 分类导入是否也根据配置表设置默认费率
//								if(null != listAmt.get(0).get("RATE")) {
//									terminalModel.setRate(Double.parseDouble(listAmt.get(0).get("RATE") + ""));
//								}
//								if(null != listAmt.get(0).get("SECONDRATE")) {
//									terminalModel.setSecondRate(Double.parseDouble(listAmt.get(0).get("SECONDRATE") + ""));
//								}
//								if(null != listAmt.get(0).get("SCANRATE")) {
//									terminalModel.setScanRate(Double.parseDouble(listAmt.get(0).get("SCANRATE") + ""));
//								}
//								if(null != listAmt.get(0).get("SCANRATEUP")) {
//									terminalModel.setScanRateUp(new BigDecimal(listAmt.get(0).get("SCANRATEUP") + ""));
//								}
//								if(null != listAmt.get(0).get("HUABEIRATE")) {
//									terminalModel.setHuabeiRate(new BigDecimal(listAmt.get(0).get("HUABEIRATE") + ""));
//								}
//							} else {
//							}
//						}
						if(!"0".equals(rebateType)){
							terminalModel.setStatus(3);
						}else{
							terminalModel.setStatus(1);
						}
						terminalInfoDao.saveObject(terminalModel);
						tMap.put(snValue, "");
					}
				}
			}	
			Integer i = terminalInfoDao.executeSqlUpdate("update sys_configure set minfo2='"+maxTermid+"' WHERE groupName='"+type+"'", null);
			System.out.println("手刷sn导入变更默认tid :"+i);
			if(i==0){
				i = 1/0;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public synchronized List<Map<String, String>> saveAgentSnInfo(String xlsfile,String name, UserBean user) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		//同文件sn记录map
		Map<String,String> tMap = new HashMap<String,String>();
		//判断机构号
		String [] s = name.split("-");
		String unnoValue=s[0];
		//导入批次号
		String batchID=s[1];
		String type="";
		try {
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);

			UnitModel info=getSeqNoByUnno(unnoValue);
			//得到数据库中总公司和分公司最大的termid
			if(info==null){
				Map<String,String> map = new HashMap<String,String>();
				map.put("unno", unnoValue);
				map.put("remark", "机构不存在");
				list.add(map);
				return list;
			}else if(info.getUnLvl()==1){
				//分公司
				type = "Tid1";
			}else{
				//一代
				type = "Tid2";
			}
			//先判断当前机构号是三级还是原来的
			if(info.getUpperUnit3()==null||"".equals(info.getUpperUnit3())){
				Map<String,String> map = new HashMap<String,String>();
				map.put("unno", unnoValue);
				map.put("remark", "请确认导入是三级分销设备后，再次导入");
				list.add(map);
				return list;
			}
			//判断是否填写批次号111000-20171016-sn.xls
			if(s.length!=3||batchID==null||"".equals(batchID)){
				Map<String,String> map = new HashMap<String,String>();
				map.put("unno", unnoValue);
				map.put("remark", "请填写批次号后，再次导入");
				list.add(map);
				return list;
			}
			Integer maxTermid = getTidMax(type);
			for(int i = 1; i < sheet.getLastRowNum()+1; i++){
				TerminalInfoModel terminalModel = new TerminalInfoModel();

				HSSFRow row = sheet.getRow(i);
				String snValue="";
				String rebateType="0";
				String snType="";
				double rate=0;
				double secondRate=0;
				HSSFCell cell = row.getCell((short)0);
				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
					//SN号
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					snValue = row.getCell((short)0).getStringCellValue().trim();
					//					//机构号
					//					row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
					//					unnoValue=row.getCell((short)1).getStringCellValue();
					//借贷卡费率
					row.getCell((short)1).setCellType(Cell.CELL_TYPE_NUMERIC);
					rate=row.getCell((short)1).getNumericCellValue()/100;
					//秒到手续费
					row.getCell((short)2).setCellType(Cell.CELL_TYPE_NUMERIC);
					secondRate=row.getCell((short)2).getNumericCellValue();
					//返利类型 1循环送；2返款； 0无返利;3分期 ；4押金
					row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
					rebateType = row.getCell((short)3).getStringCellValue().trim();
					//sn类型  三级分销 1小蓝牙 2 大蓝牙
					row.getCell((short)4).setCellType(Cell.CELL_TYPE_STRING);
					snType = row.getCell((short)4).getStringCellValue().trim();
					try{
						if(!"".equals(rebateType)){
							terminalModel.setRebateType(Integer.valueOf(rebateType));
						}
					}catch(Exception e){
						Map<String,String> map = new HashMap<String,String>();
						String sn = snValue;
						String unno=unnoValue;
						map.put("sn", sn);
						map.put("unno", unno);
						map.put("remark", "返利或设备类型不合规");
						list.add(map);
						continue;
					}
					String sql = "select sn,unno from bill_terminalinfo where sn='"+snValue+"'"; 
					List<Map<String, Object>> snlist=terminalInfoDao.queryObjectsBySqlObject(sql);
					if(rate<0.005d){
						Map<String,String> map = new HashMap<String,String>();
						String sn = snValue;
						String unno=unnoValue;
						map.put("sn", sn);
						map.put("unno", unno);
						map.put("remark", "费率低于0.5%");
						list.add(map);
						continue;
					}
					if(snlist.size()>0||tMap.containsKey(snValue)){
						Map<String,String> map = new HashMap<String,String>();
						String sn = snValue;
						String unno=unnoValue;
						map.put("sn", sn);
						map.put("unno", unno);
						map.put("remark", "sn号重复");
						list.add(map);
						continue;
					}else{
						maxTermid++;
						//生成tid是否存在于终端表
						String sql_unno="select count(1) from bill_terminalinfo t where t.termid='"+maxTermid+"'";
						BigDecimal num = unitDao.querysqlCounts(sql_unno, null);
						if(num.intValue()>0){
							//
							Map<String,String> map = new HashMap<String,String>();
							String sn = snValue;					
							map.put("sn", sn);
							map.put("unno", unnoValue);
							map.put("remark", "手刷终端号已满，原表以下数据皆失败");
							list.add(map);
							break;
						}
						terminalModel.setTermID(maxTermid.toString());
						terminalModel.setUnNO(unnoValue);
						terminalModel.setSn(snValue);
						terminalModel.setRate(rate);
						terminalModel.setSecondRate(secondRate);
						terminalModel.setStatus(1);
						terminalModel.setKeyType("3");
						terminalModel.setIsM35(1);
						terminalModel.setMaintainUID(user.getUserID());
						terminalModel.setKeyConfirmDate(new Date());
						terminalModel.setAllotConfirmDate(new Date());
						terminalModel.setMaintainType("A");
						terminalModel.setBatchID(batchID);
						if("4".equals(rebateType)){
							terminalModel.setDepositAmt(109);
						}
						terminalModel.setSnType(Integer.valueOf(snType));
						terminalInfoDao.saveObject(terminalModel);
						tMap.put(snValue, "");
					}
				}
			}	
			Integer i = terminalInfoDao.executeSqlUpdate("update sys_configure set minfo2='"+maxTermid+"' WHERE groupName='"+type+"'", null);
			System.out.println("手刷sn导入变更默认tid :"+i);
			if(i==0){
				i = 1/0;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public synchronized List<Map<String, String>> saveM35SnInfoPur(String PDID,String xlsfile,String name,String ORDERID,String MACHINEMODEL,String snType, UserBean user,String storage,String ORDERMETHOD, String ORDERTYPE) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		//同文件sn记录map
		Map<String,String> tMap = new HashMap<String,String>();
		//判断机构号
		String unnoValue = "110000";
		//导入批次号
		String type = "Tid2";
		//1小蓝牙，2大蓝牙
		int snTypeInt = 0;
		int num1 = 0;
		int detailstatus = 7;
		if(!"".equals(snType)){
			snTypeInt = Integer.parseInt(snType);
		}
		Integer z = terminalInfoDao.querysqlCounts2("select l.machinenum-l.importnum from bill_purchasedetail l WHERE pdid="+PDID, null);
		log.info("sn入库：订单号="+ORDERID+";PDID="+PDID+";MACHINEMODEL="+MACHINEMODEL+";允许导入条目="+z);
		Integer maxTermid = getTidMax(type);
		try {
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<Object> arrayList = new ArrayList<Object>();
			List<Object> arraySimList = new ArrayList<Object>();
			for(int i = 1; i < sheet.getLastRowNum()+1&&i<=z; i++){
				TerminalInfoModel terminalModel = new TerminalInfoModel();

				HSSFRow row = sheet.getRow(i);
				String snValue="";
				String snSim="";
				String tryDate="";
				String initDate="";
				double secondRate=0;
				HSSFCell cell = row.getCell((short)0);
				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
					//SN号
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					snValue = row.getCell((short)0).getStringCellValue().trim();

					HSSFCell cell1 = row.getCell((short)1);
					if(cell1!=null){
						row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
						snSim = row.getCell((short)1).getStringCellValue().trim();
					}

					HSSFCell cell2 = row.getCell((short)2);
					if(cell2!=null) {
						row.getCell((short) 2).setCellType(Cell.CELL_TYPE_STRING);
                        tryDate = row.getCell((short)3).getStringCellValue().trim();
					}

					HSSFCell cell3 = row.getCell((short)3);
					if(cell3!=null) {
                        row.getCell((short) 3).setCellType(Cell.CELL_TYPE_STRING);
                        initDate = row.getCell((short) 3).getStringCellValue().trim();
					}
					//					//机构号
					//					row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
					//					unnoValue=row.getCell((short)1).getStringCellValue();
					//借贷卡费率
					//					row.getCell((short)1).setCellType(Cell.CELL_TYPE_NUMERIC);
					//					rate=row.getCell((short)1).getNumericCellValue()/100;
					//					//秒到手续费
					//					row.getCell((short)2).setCellType(Cell.CELL_TYPE_NUMERIC);
					//					secondRate=row.getCell((short)2).getNumericCellValue();
					//					//返利类型 1循环送；2返款； 0无返利;3分期 ；4押金
					//					row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
					//					rebateType = row.getCell((short)3).getStringCellValue().trim();
					//sn类型  三级分销 1小蓝牙 2 大蓝牙
					//					row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
					//					snType = row.getCell((short)3).getStringCellValue().trim();
					String sql = "select sn,unno from bill_terminalinfo where sn='"+snValue+"'"; 
					List<Map<String, Object>> snlist=terminalInfoDao.queryObjectsBySqlObject(sql);
					if(snlist.size()>0||tMap.containsKey(snValue)){
						Map<String,String> map = new HashMap<String,String>();
						String sn = snValue;
						String unno=unnoValue;
						map.put("sn", sn);
						map.put("unno", unno);
						map.put("remark", "sn号重复");
						list.add(map);
						continue;
					}else{
						maxTermid++;
						//生成tid是否存在于终端表
						String sql_unno="select count(1) from bill_terminalinfo t where t.termid='"+maxTermid+"'";
						BigDecimal num = unitDao.querysqlCounts(sql_unno, null);
						if(num.intValue()>0){
							//
							Map<String,String> map = new HashMap<String,String>();
							String sn = snValue;					
							map.put("sn", sn);
							map.put("unno", unnoValue);
							map.put("remark", "手刷终端号已满，原表以下数据皆失败");
							list.add(map);
							break;
						}
						terminalModel.setTermID(maxTermid.toString());
						terminalModel.setUnNO(unnoValue);
						terminalModel.setSn(snValue);
						//						terminalModel.setRate(rate);
						terminalModel.setSecondRate(secondRate);
						terminalModel.setStatus(0);//入库状态 0
						terminalModel.setKeyType("3");
						terminalModel.setIsM35(1);
						terminalModel.setMaintainUID(user.getUserID());
						terminalModel.setKeyConfirmDate(new Date());
						terminalModel.setAllotConfirmDate(new Date());
						terminalModel.setMaintainType("A");
						terminalModel.setBatchID(ORDERID);
						terminalModel.setMachineModel(MACHINEMODEL);
						terminalModel.setStorage(storage);
						//						if("4".equals(rebateType)){
						//							terminalModel.setDepositAmt(109);
						//						}
						terminalModel.setSnType(snTypeInt);
						terminalModel.setIsReturnCash(0);
						arrayList.add(terminalModel);
						BillTerminalSimModel simModel=setTermSimInfo(snValue,snSim,tryDate,initDate,user);
						if(simModel!=null) {
							arraySimList.add(simModel);
						}
						//						terminalInfoDao.saveObject(terminalModel);
						if(arrayList.size()==1000) {//1000提交事务
							terminalInfoDao.batchSaveObject(arrayList);
							arrayList = new ArrayList<Object>();//清空
							if(arraySimList.size()>0) {
								billTerminalSimDao.batchSaveObject(arraySimList);
								arraySimList = new ArrayList<Object>();//清空
							}
						}
						num1++;
						tMap.put(snValue, "");
					}
				}
			}
			if(arrayList.size()>0) {//不够1000的批量提交
				terminalInfoDao.batchSaveObject(arrayList);
				if(arraySimList.size()>0) {
					billTerminalSimDao.batchSaveObject(arraySimList);
				}
			}
		} catch (Exception e) {
			log.info("进销存系统入库异常:orderID="+ORDERID+";fileName="+name,e);
			e.printStackTrace();
		}
		if(num1>0){
			if(z==num1)detailstatus=8;
			Integer j = terminalInfoDao.executeSqlUpdate("update bill_purchasedetail set importnum=importnum+"+num1+",detailstatus="+detailstatus+" WHERE pdid="+PDID+" and machinenum>=importnum+"+num1, null);
			Integer i = terminalInfoDao.executeSqlUpdate("update sys_configure set minfo2='"+maxTermid+"' WHERE groupName='"+type+"'", null);
			System.out.println("手刷sn导入变更默认tid :"+i+";变更入库单已入库数量="+j);
			if(i==0){
				i = 1/0;
			}
		}
		return list;
	}

	/**
	 * sn-sim信息设置
	 * @param sn
	 * @param sim
	 * @param tryDate 测试结束日期
	 * @param initDate 出厂日期
	 * @param userBean
	 * @return
	 */
	private BillTerminalSimModel setTermSimInfo(String sn,String sim,String tryDate,String initDate,UserBean userBean){
		if(StringUtils.isNotEmpty(sn) && StringUtils.isNotEmpty(sim)) {
			String hql="from BillTerminalSimModel where sn=?";
			List<BillTerminalSimModel> list = billTerminalSimDao.queryObjectsByHqlList(hql,new Object[]{sn});
			if(list.size()>0){
				return null;
			}
			Date date = new Date();
			BillTerminalSimModel model = new BillTerminalSimModel();
//			model.setMid();
			model.setSim(sim);
			model.setSn(sn);
			model.setStatus(1);
//			model.setSnStatus();
			if(StringUtils.isNotEmpty(initDate)){
				model.setInitDate(initDate);
			}
			if(StringUtils.isNotEmpty(tryDate)){
				model.setTryDate(tryDate);
			}
//			model.setPayDate();
//			model.setDeductDate();
//			model.setActDate();
			model.setCreateDate(date);
			model.setCreateUser(userBean.getUserID() + "");
			model.setUpdateDate(date);
			model.setUpdateUser(userBean.getUserID() + "");
//			model.setRemark1();
//			model.setRemark2();
			return model;
		}
		return null;
	}

	@Override
	public synchronized List<Map<String, String>> saveM35SnInfoPur2(String PDID,String xlsfile,String name,String ORDERID,String MACHINEMODEL,String snType, UserBean user,String storage,String ORDERMETHOD, String ORDERTYPE) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		//同文件sn记录map
		Map<String,String> tMap = new HashMap<String,String>();
		//判断机构号
		String unnoValue = "110000";
		//导入批次号
		String type = "Tid2";
		//1小蓝牙，2大蓝牙
		int snTypeInt = 0;
		int num1 = 0;
		int detailstatus = 7;
		if(!"".equals(snType)){
			snTypeInt = Integer.parseInt(snType);
		}
		Integer z = terminalInfoDao.querysqlCounts2("select l.machinenum-l.importnum from bill_purchasedetail l WHERE pdid="+PDID, null);
		log.info("sn入库：订单号="+ORDERID+";PDID="+PDID+";MACHINEMODEL="+MACHINEMODEL+";允许导入条目="+z);
		try {
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);

			Integer maxTermid = getTidMax(type);
			//开始操作第j行
			for(int j=1;j<sheet.getLastRowNum()+1;j++){
				HSSFRow row = sheet.getRow(j);
				HSSFCell cell = row.getCell((short)0);

				String snValueHead="";
				Integer snValuePre=0;
				Integer snValueSuf=0;

				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
					//SN号前缀
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					snValueHead = row.getCell((short)0).getStringCellValue().trim();
					//SN号开始
					row.getCell((short)1).setCellType(Cell.CELL_TYPE_NUMERIC);
					snValuePre = (int) row.getCell((short)1).getNumericCellValue();
					//SN号结束
					row.getCell((short)2).setCellType(Cell.CELL_TYPE_NUMERIC);
					snValueSuf = (int) row.getCell((short)2).getNumericCellValue();
					//分段倒入最多100
					if(snValueSuf-snValuePre+1>100){
						snValueSuf = snValuePre+99;
					}
					Integer count=0;
					//导入数量小于待导入数量  200-100<=100-0
					if(snValueSuf-snValuePre+1<=z-num1){
						count = snValueSuf-snValuePre+1;
					}else if(z-num1==0){
						break;
					}else{
						count = z-num1;
					}
					for(int i = 1; i<=count; i++){
						TerminalInfoModel terminalModel = new TerminalInfoModel();

						String snValue="";
						double secondRate=0;

						snValue = snValueHead+(snValuePre+i-1);

						String sql = "select sn,unno from bill_terminalinfo where sn='"+snValue+"'"; 
						List<Map<String, Object>> snlist=terminalInfoDao.queryObjectsBySqlObject(sql);
						if(snlist.size()>0||tMap.containsKey(snValue)){
							Map<String,String> map = new HashMap<String,String>();
							String sn = snValue;
							String unno=unnoValue;
							map.put("sn", sn);
							map.put("unno", unno);
							map.put("remark", "sn号重复");
							list.add(map);
							continue;
						}else{
							maxTermid++;
							//生成tid是否存在于终端表
							String sql_unno="select count(1) from bill_terminalinfo t where t.termid='"+maxTermid+"'";
							BigDecimal num = unitDao.querysqlCounts(sql_unno, null);
							if(num.intValue()>0){
								//
								Map<String,String> map = new HashMap<String,String>();
								String sn = snValue;					
								map.put("sn", sn);
								map.put("unno", unnoValue);
								map.put("remark", "手刷终端号已满，原表以下数据皆失败");
								list.add(map);
								break;
							}
							terminalModel.setTermID(maxTermid.toString());
							terminalModel.setUnNO(unnoValue);
							terminalModel.setSn(snValue);
							//							terminalModel.setRate(rate);
							terminalModel.setSecondRate(secondRate);
							terminalModel.setStatus(1);
							terminalModel.setKeyType("3");
							terminalModel.setIsM35(1);
							terminalModel.setMaintainUID(user.getUserID());
							terminalModel.setKeyConfirmDate(new Date());
							terminalModel.setAllotConfirmDate(new Date());
							terminalModel.setMaintainType("A");
							terminalModel.setBatchID(ORDERID);
							terminalModel.setMachineModel(MACHINEMODEL);
							terminalModel.setSnType(snTypeInt);
							terminalModel.setStorage(storage);
							terminalInfoDao.saveObject(terminalModel);
							num1++;
							tMap.put(snValue, "");
						}

					}

				}

			}

			if(num1>0){
				if(z==num1)detailstatus=8;
				Integer j = terminalInfoDao.executeSqlUpdate("update bill_purchasedetail set importnum=importnum+"+num1+",detailstatus="+detailstatus+" WHERE pdid="+PDID+" and machinenum>=importnum+"+num1, null);
				Integer i = terminalInfoDao.executeSqlUpdate("update sys_configure set minfo2='"+maxTermid+"' WHERE groupName='"+type+"'", null);
				System.out.println("手刷sn导入变更默认tid :"+i+";变更入库单已入库数量="+j);
				if(i==0){
					i = 1/0;
				}
			}
		} catch (Exception e) {
			log.info("进销存系统入库异常:orderID="+ORDERID+";fileName="+name,e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public synchronized List<Map<String, String>> updateM35SnInfoPur(String xlsfile,String name,UserBean user,String psid,String outStorage,String inStorage,String storageMachineModel,Integer storageMachineNum,Integer loadStorageNum) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		//同文件sn记录map
		Map<String,String> tMap = new HashMap<String,String>();
		String snValue = "";
		log.info("sn库存调拨：调出库位="+outStorage+";调入库位="+inStorage+";数量="+storageMachineNum);
		try {
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);
			if(sheet.getLastRowNum()+1<=storageMachineNum-loadStorageNum){
				Map<String,String> map = new HashMap<String,String>();
				map.put("sn", "");
				map.put("remark", "调拨数量有误，请确认后再试");
				list.add(map);
				return list;
			}
			int num = 0;
			//开始操作第i行
			for(int i=1;i<sheet.getLastRowNum()+1;i++){
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell = row.getCell((short)0);

				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
					//待调拨的SN号
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					snValue = row.getCell((short)0).getStringCellValue().trim();
					TerminalInfoModel terminalInfoModel = terminalInfoDao.queryObjectByHql("from TerminalInfoModel where storage=? and unno=110000 and machineModel=? and sn=?", new Object[]{outStorage,storageMachineModel,snValue});
					if(terminalInfoModel==null){
						Map<String,String> map = new HashMap<String,String>();
						map.put("sn", snValue);
						map.put("remark", "未查询到设备，请重试");
						list.add(map);
						continue;
					}else{
						terminalInfoModel.setStorage(inStorage);
						terminalInfoModel.setMaintainType("M");
						terminalInfoModel.setMaintainDate(new Date());
						terminalInfoDao.updateObject(terminalInfoModel);
						num++;
					}
				}
			}
			Integer j = terminalInfoDao.executeSqlUpdate("update bill_purchasestorage set loadStorageNum=loadStorageNum+"+num+" WHERE psid="+psid+" and storageMachineNum>=loadStorageNum+"+num, null);

		} catch (Exception e) {
			log.info("进销存系统库存调拨异常:fileName="+name,e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public synchronized Integer updateM35ForLend(String xlsfile,String name,UserBean user,String unno,String machineModel,String loadStorageNum,String storageMachineNum,String psid,String storageID) {
		int num = 0;
		String snValue = "";
		log.info("sn借出：机构号="+unno);
		try {
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);

			if(Integer.parseInt(storageMachineNum)-Integer.parseInt(loadStorageNum)<sheet.getLastRowNum()){
				//导入数量大于待借出数量
				return -1;
			}

			//开始操作第i行
			for(int i=1;i<sheet.getLastRowNum()+1;i++){
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell = row.getCell((short)0);

				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
					//待借出的SN号
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					snValue = row.getCell((short)0).getStringCellValue().trim();
					TerminalInfoModel terminalInfoModel = terminalInfoDao.queryObjectByHql("from TerminalInfoModel where  unno=110000 and sn=? and machineModel=? ", new Object[]{snValue,machineModel});
					if(terminalInfoModel==null){
						continue;
					}else{
						terminalInfoModel.setStatus(1);//借样单修改设备状态，0——》1
						terminalInfoModel.setUnNO(unno);
						terminalInfoModel.setTerOrderID(storageID);
						terminalInfoModel.setMaintainType("M");
						terminalInfoModel.setMaintainDate(new Date());
						terminalInfoDao.updateObject(terminalInfoModel);
						num++;
					}
				}
			}
			//更新借样单表
			if(Integer.parseInt(storageMachineNum)==Integer.parseInt(loadStorageNum)+num){
				terminalInfoDao.executeSqlUpdate("update bill_purchasestorage set loadStorageNum=loadStorageNum+"+num+",storageStatus=2 WHERE psid="+psid+" and storageMachineNum>=loadStorageNum+"+num, null);
			}else{
				terminalInfoDao.executeSqlUpdate("update bill_purchasestorage set loadStorageNum=loadStorageNum+"+num+" WHERE psid="+psid+" and storageMachineNum>=loadStorageNum+"+num, null);
			}

		} catch (Exception e) {
			log.info("进销存系统借样单异常:fileName="+name,e);
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public synchronized Integer updateM35ForAdd(String xlsfile,String name, UserBean user,String machineModel,String loadStorageNum,String storageMachineNum,String psid) {
		//导入批次号
		String type = "Tid2";
		//1小蓝牙，2大蓝牙
		int snTypeInt = 0;
		int num1 = 0;
		try {
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);

			if(Integer.parseInt(storageMachineNum)-Integer.parseInt(loadStorageNum)<sheet.getLastRowNum()){
				//导入数量大于待盘盈数量
				return -1;
			}

			Integer maxTermid = getTidMax(type);
			for(int i = 1; i < sheet.getLastRowNum()+1; i++){
				TerminalInfoModel terminalModel = new TerminalInfoModel();

				HSSFRow row = sheet.getRow(i);
				String snValue="";
				double secondRate=0;
				HSSFCell cell = row.getCell((short)0);
				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
					//SN号
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					snValue = row.getCell((short)0).getStringCellValue().trim();
					String sql = "select sn,unno from bill_terminalinfo where sn='"+snValue+"'"; 
					List<Map<String, Object>> snlist=terminalInfoDao.queryObjectsBySqlObject(sql);
					if(snlist.size()>0){
						continue;
					}else{
						maxTermid++;
						//生成tid是否存在于终端表
						String sql_unno="select count(1) from bill_terminalinfo t where t.termid='"+maxTermid+"'";
						BigDecimal num = unitDao.querysqlCounts(sql_unno, null);
						if(num.intValue()>0){
							break;
						}
						terminalModel.setTermID(maxTermid.toString());
						terminalModel.setUnNO("110000");
						terminalModel.setSn(snValue);
						terminalModel.setSecondRate(secondRate);
						terminalModel.setStatus(1);
						terminalModel.setKeyType("3");
						terminalModel.setIsM35(1);
						terminalModel.setMaintainUID(user.getUserID());
						terminalModel.setKeyConfirmDate(new Date());
						terminalModel.setAllotConfirmDate(new Date());
						terminalModel.setMaintainType("A");
						terminalModel.setBatchID("000000000");
						terminalModel.setMachineModel(machineModel);
						terminalModel.setStorage("HRT");
						terminalModel.setSnType(snTypeInt);
						terminalInfoDao.saveObject(terminalModel);
						num1++;
					}
				}
			}
			//更新盘盈盘亏表
			terminalInfoDao.executeSqlUpdate("update bill_purchasestorage set loadStorageNum=loadStorageNum+"+num1+" WHERE psid="+psid+" and storageMachineNum>=loadStorageNum+"+num1, null);
			Integer i = terminalInfoDao.executeSqlUpdate("update sys_configure set minfo2='"+maxTermid+"' WHERE groupName='"+type+"'", null);
		} catch (Exception e) {
			log.info("进销存系统盘盈盘亏异常",e);
			e.printStackTrace();
		}
		return num1;
	}

	@Override
	public synchronized Integer updateM35ForRed(String xlsfile,String name, UserBean user,String machineModel,String loadStorageNum,String storageMachineNum,String psid) {
		int num1 = 0;
		try {
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);

			if(Integer.parseInt(storageMachineNum)-Integer.parseInt(loadStorageNum)<sheet.getLastRowNum()){
				//导入数量大于待盘亏数量
				return -1;
			}

			for(int i = 1; i < sheet.getLastRowNum()+1; i++){

				HSSFRow row = sheet.getRow(i);
				String snValue="";
				HSSFCell cell = row.getCell((short)0);
				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
					//SN号
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					snValue = row.getCell((short)0).getStringCellValue().trim();
					TerminalInfoModel terminalInfoModel = terminalInfoDao.queryObjectByHql("from TerminalInfoModel where sn=? and unno='110000' and machineModel=? ", new Object[]{snValue,machineModel});
					if(terminalInfoModel!=null){
						terminalInfoModel.setMaintainType("D");
						terminalInfoModel.setMaintainDate(new Date());
						terminalInfoDao.updateObject(terminalInfoModel);
						num1++;
					}
				}
			}
			//更新盘盈盘亏表
			terminalInfoDao.executeSqlUpdate("update bill_purchasestorage set loadStorageNum=loadStorageNum+"+num1+" WHERE psid="+psid+" and storageMachineNum>=loadStorageNum+"+num1, null);
		} catch (Exception e) {
			log.info("进销存系统盘亏异常",e);
			e.printStackTrace();
		}
		return num1;
	}

	@Override
	public synchronized List<Map<String, String>> saveMerSnInfo(String xlsfile,String name, UserBean user) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();

		//同文件sn记录map
		Map<String,String> tMap = new HashMap<String,String>();

		//长密匙后缀
		String suffix="0000000000000000";
		//判断机构号
		String [] s = name.split("-");
		String unnoValue=s[0];
		try {
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);

			//记录数量
			UnitModel info=getSeqNoByUnno(unnoValue);
			if (info == null) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("sn", "");
				map.put("unno", unnoValue);
				map.put("remark", "机构不存在");
				list.add(map);
				return list;
			}
			String type="";
			if(info.getUnLvl()==1){
				//分公司
				type = "Tid1";
			}else{
				//一代
				type = "Tid2";
			}
			Integer maxTermid = getTidMax(type);
			for(int i = 1; i < sheet.getLastRowNum()+1; i++){
				TerminalInfoModel terminalModel = new TerminalInfoModel();

				HSSFRow row = sheet.getRow(i);
				String snValue="";
				String keyType="0";
				HSSFCell cell = row.getCell((short)0);
				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
					//SN号
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					snValue = row.getCell((short)0).getStringCellValue().trim();
					//返利类型 1循环送；2返款； 0无返利;3分期 ；4押金
					row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
					keyType = row.getCell((short)1).getStringCellValue().trim();

					String sql = "select sn,unno from bill_terminalinfo where sn='"+snValue+"'"; 
					List<Map<String, Object>> snlist=terminalInfoDao.queryObjectsBySqlObject(sql);

					if(snlist.size()>0||tMap.containsKey(snValue)){
						Map<String,String> map = new HashMap<String,String>();
						String sn = snValue;
						String unno=unnoValue;
						map.put("sn", sn);
						map.put("unno", unno);
						map.put("remark", "sn号重复");
						list.add(map);
						continue;
					}else{
						maxTermid++;
						if(keyType!=null&&"1".equals(keyType)){//生成短密钥
							terminalModel.setKeyContext(StringUtil.toHexString(maxTermid.toString()));
						}else{//生成长密钥30000开始
							terminalModel.setKeyContext(StringUtil.toHexString(maxTermid.toString())+suffix);
						}
						//生成tid是否存在于终端表
						String sql_unno="select count(1) from bill_terminalinfo t where t.termid='"+maxTermid+"'";
						BigDecimal num = unitDao.querysqlCounts(sql_unno, null);
						if(num.intValue()>0){
							//
							Map<String,String> map = new HashMap<String,String>();
							String sn = snValue;					
							map.put("sn", sn);
							map.put("unno", unnoValue);
							map.put("remark", "传统终端号已满，原表以下数据皆失败");
							list.add(map);
							break;
						}
						terminalModel.setTermID(maxTermid.toString());
						terminalModel.setUnNO(unnoValue);
						terminalModel.setSn(snValue);
						terminalModel.setStatus(0);
						terminalModel.setKeyType(keyType);
						terminalModel.setMaintainDate(new Date());
						terminalModel.setIsM35(0);
						terminalModel.setMaintainUID(user.getUserID());
						terminalModel.setAllotConfirmDate(new Date());
						terminalModel.setMaintainType("A");
						terminalInfoDao.saveObject(terminalModel);
						tMap.put(snValue, "");
					}
				}	
			}	
			Integer i = terminalInfoDao.executeSqlUpdate("update sys_configure set minfo2='"+maxTermid+"' WHERE groupName='"+type+"'", null);
			System.out.println("传统sn导入变更默认tid :"+i);
			if(i==0){
				i = 1/0;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public DataGridBean queryExportList(TerminalInfoBean bean, UserBean user) {
		Map<String, Object> params=new HashMap<String, Object>();
		String hql = "from TerminalInfoModel where 1 = 1";
		String hqlCount = "select count(*) from TerminalInfoModel where 1 = 1  ";

		if (bean.getTermIDStart() != null && !"".equals(bean.getTermIDStart())) {
			hql +=" AND termID >= :termIDStart";
			hqlCount += " AND termID >= :termIDStart";
			params.put("termIDStart", bean.getTermIDStart());
		} 

		if (bean.getTermIDEnd() != null && !"".equals(bean.getTermIDEnd())) {
			hql +=" AND termID <= :termIDEnd";
			hqlCount += " AND termID <= :termIDEnd";
			params.put("termIDEnd", bean.getTermIDEnd());
		}

		if(bean.getUnNO()!=null&&!"".equals(bean.getUnNO())){
			hql+=" AND unNO=:unNO";
			hqlCount+=" AND unNO=:unNO";
			params.put("unNO", bean.getUnNO());
		}
		hql+=" AND status =1 and usedConfirmDate is null ";
		hqlCount+="  AND status =1 and usedConfirmDate is null";
		long counts = terminalInfoDao.queryCounts(hqlCount, params);
		List<TerminalInfoModel> list = terminalInfoDao.queryObjectsByHqlList(hql,params,bean.getPage(), bean.getRows());
		DataGridBean dataGridBean = formatToDataGrid(list, counts);
		return dataGridBean;
	}

	@Override
	public HSSFWorkbook exportTidInfo(TerminalInfoBean bean) {
		Map<String, Object> params=new HashMap<String, Object>();
		String sql=" SELECT t.UNNO,t.TERMID,t.KEYTYPE,t.SN,t.ISM35 FROM BILL_TERMINALINFO t where t.status=1 and t.unno= :UNNO ";
		params.put("UNNO", bean.getUnNO());
		if (bean.getTermIDStart() != null && !"".equals(bean.getTermIDStart())) {
			sql +=" AND t.termid >= :termIDStart";
			params.put("termIDStart", bean.getTermIDStart());
		} 

		if (bean.getTermIDEnd() != null && !"".equals(bean.getTermIDEnd())) {
			sql +=" AND t.termid <= :termIDEnd";
			params.put("termIDEnd", bean.getTermIDEnd());
		}

		sql+=" and t.usedconfirmdate is null";
		List<Map<String, String>> data=terminalInfoDao.queryObjectsBySqlListMap(sql, params);
		List<String> excelHeader=new ArrayList<String>();
		Map<String, Object> headMap=new HashMap<String, Object>();
		excelHeader.add("UNNO");
		excelHeader.add("TERMID");
		excelHeader.add("KEYTYPE");
		excelHeader.add("SN");
		excelHeader.add("ISM35");
		headMap.put("UNNO", "机构编号");
		headMap.put("TERMID", "终端编号");
		headMap.put("KEYTYPE", "秘钥类型");
		headMap.put("SN", "SN号");
		headMap.put("ISM35", "是否手刷");
		return ExcelUtil.export("终端号", data, excelHeader, headMap);
	}

	@Override
	public void updateExportTidInfo(TerminalInfoBean bean) {
		String sql="update  BILL_TERMINALINFO t   set t.status=1  where t.status=0 and t.unno=:UNNO ";
		if (bean.getTermIDStart() != null && !"".equals(bean.getTermIDStart())) {
			sql +=" AND t.termid >='"+bean.getTermIDStart()+"'";
		} 
		if (bean.getTermIDEnd() != null && !"".equals(bean.getTermIDEnd())) {
			sql +=" AND t.termid <='"+bean.getTermIDEnd()+"'";
		}
		sql+=" and t.usedconfirmdate is null";
		terminalInfoDao.executeUpdate(sql);
	}
	public DataGridBean searchTerminalInfo2(TerminalInfoBean TerminalInfo, UserBean user) {
		String sql="select  BTID,UNNO,termid,SN,ism35,RATE ,SECONDRATE from Bill_Terminalinfo where usedconfirmdate is null and AllotConfirmDate is not null  and UNNO = '"+user.getUnNo()+"'";


		if (TerminalInfo.getTermIDStart() != null && !"".equals(TerminalInfo.getTermIDStart())) {
			sql +=" and termid >='"+TerminalInfo.getTermIDStart()+"' ";
		}

		if (TerminalInfo.getTermIDEnd() != null && !"".equals(TerminalInfo.getTermIDEnd())) {
			sql +=" and termid <= '"+TerminalInfo.getTermIDEnd()+"' ";
		}
		if(TerminalInfo.getSnStart() !=null && !"".equals(TerminalInfo.getSnStart())){
			sql +=" and sn >= '"+TerminalInfo.getSnStart()+"' ";
		}
		if(TerminalInfo.getSnEnd() != null && !"".equals(TerminalInfo.getSnEnd())){
			sql +=" and sn <= '"+TerminalInfo.getSnEnd()+"' ";
		}
		String sqlCount ="select count(*) from ("+sql+")";
		Integer count =terminalInfoDao.querysqlCounts2(sqlCount, null);
		List<Map<String,String>> proList = terminalInfoDao.queryObjectsBySqlList(sql, null, TerminalInfo.getPage(), TerminalInfo.getRows());
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(count);
		dgd.setRows(proList);
		return dgd;
	}

	public synchronized String gaveTerminalInfo(TerminalInfoBean bean, UserBean user) {
		// @author:lxg-20190826 终端分配验证当前机构与分配机构是否拥有所分配的活动产品
		//        String errMsg = validateGaveTerminalInfo(bean,user);
		//        if(StringUtils.isNotEmpty(errMsg)){
		//            return errMsg;
		//        }
		String sql = "";
		if(null !=bean.getTermIDStart() && !"".equals(bean.getTermIDStart()) && null != bean.getTermIDEnd() && !"".equals(bean.getTermIDEnd())
				&& (bean.getSnStart() == null || "".equals(bean.getSnStart()) ) && (bean.getSnEnd()==null||"".equals(bean.getSnEnd()))){
			sql=" update  Bill_Terminalinfo t " +
					" set t.unno='"+bean.getUnNO()+"' ,t.allotconfirmdate=sysdate,t.maintainuid="+user.getUserID()+" "+
					" where usedconfirmdate is null and t.termid>='"+bean.getTermIDStart()+"'"+
					" and  t.termid<='"+bean.getTermIDEnd()+"'and UNNO ='"+user.getUnNo()+"'";
		}else{
			sql=" update  Bill_Terminalinfo t " +
					" set t.unno='"+bean.getUnNO()+"' ,t.allotconfirmdate=sysdate,t.maintainuid="+user.getUserID()+" "+
					" where usedconfirmdate is null and t.sn>='"+bean.getSnStart()+"'"+
					" and  t.sn<='"+bean.getSnEnd()+"'and UNNO ='"+user.getUnNo()+"'";
		}

		terminalInfoDao.executeUpdate(sql);
		return null;
	}

	/**
	 * 分配终端校验
	 * @param bean
	 * @param user
	 * @return
	 */
	public String validateGaveTerminalInfo(TerminalInfoBean bean, UserBean user){
		// @author:lxg-20190805 添加终端下发校验
		// 1.当前机构有该终端的产品时可以下发
		// 2.下发接收的机构也有该终端的产品
		String errMsg=null;
		String sql="";
		Map param = new HashMap(16);
		if (StringUtils.isNotEmpty(bean.getTermIDStart()) && StringUtils.isNotEmpty(bean.getTermIDEnd())) {
			sql="select distinct(trim(k.rebatetype)) rebatetype from bill_terminalinfo k where k.termid>=:startTermId and k.termid<=:endTermId and k.unno=:unno ";
			param.put("startTermId",bean.getTermIDStart());
			param.put("endTermId",bean.getTermIDEnd());
			param.put("unno",user.getUnNo());
		}else if(StringUtils.isNotEmpty(bean.getSnStart()) && StringUtils.isNotEmpty(bean.getSnEnd())){
			sql="select distinct(trim(k.rebatetype)) rebatetype from bill_terminalinfo k where k.sn>=:startSn and k.sn<=:endSn and k.unno=:unno ";
			param.put("startSn",bean.getSnStart());
			param.put("endSn",bean.getSnEnd());
			param.put("unno",user.getUnNo());
		}
		List<Map<String, String>> rebates = terminalInfoDao.queryObjectsBySqlListMap(sql,param);
		if(rebates.size()>0){
			// 产品活动获取
			String sqlByRebateType="select to_char(t.valueinteger) rebateType from bill_purconfigure t where t.type=5 and t.valuestring=:valuestring and t.status=1";
			List<String> plusList = new ArrayList<String>();
			List<String> mdList = new ArrayList<String>();
			Map paramsByType = new HashMap();
			paramsByType.put("valuestring","md");
			List<Map<String, String>> md = terminalInfoDao.queryObjectsBySqlListMap(sqlByRebateType,paramsByType);
			paramsByType.put("valuestring","plus");
			List<Map<String, String>> plus = terminalInfoDao.queryObjectsBySqlListMap(sqlByRebateType,paramsByType);
			for (Map<String, String> stringStringMap : md) {
				mdList.add(stringStringMap.get("REBATETYPE")+"");
			}
			for (Map<String, String> stringStringMap : plus) {
				plusList.add(stringStringMap.get("REBATETYPE")+"");
			}

			// 当前机构 下级机构产品查询
			String err1=rebateMatchProdErrInfo(getUnnoHasProd(user.getUnNo(),user.getUnitLvl(),mdList,plusList),rebates,mdList,plusList);
			if(StringUtils.isNotEmpty(err1)){
				return "分配的终端与当前机构拥有产品不符";
			}
			String err2=rebateMatchProdErrInfo(getUnnoHasProd(bean.getUnNO(),user.getUnitLvl()<=1?user.getUseLvl():3,mdList,plusList),rebates,mdList,plusList);
			if(StringUtils.isNotEmpty(err2)){
				return "分配的终端与分配机构号拥有产品不符";
			}
		}else{
			errMsg="该批设备不在当前机构下";
		}
		return errMsg;
	}

	private String rebateMatchProdErrInfo(List<String> prodList,List<Map<String, String>> rebates,List<String> mdList,List<String> plusList){
		Map map = new HashMap();
		if(rebates.size()>0){
			map.put(rebates.get(0).get("REBATETYPE")+"","1");
		}
		for(String prod:prodList){
			if(PhoneProdConstant.PHONE_MD.equals(prod)){
				for(int i=0;i<20;i++){
					map.remove(i+"");
				}
				for (String s : mdList) {
					map.remove(s);
				}
			}else if(PhoneProdConstant.PHONE_SYT.equals(prod)){
				map.remove("21");
				map.remove("20");
			}else if(PhoneProdConstant.PHONE_PLUS.equals(prod)){
				for (String s : plusList) {
					map.remove(s);
				}
			}
		}
		if(map.size()>0){
			return "分配的终端与当前机构或分配机构所属的产品不符,请联系上级设置模板/设置分配机构模板";
		}
		return null;
	}

	/**
	 * 获取机构当前拥有的产品模板
	 * @param unno
	 * @param unLvl
	 * @param mdList
	 * @param plusList
	 * @return
	 */
	private List<String> getUnnoHasProd(String unno,Integer unLvl,List<String> mdList,List<String> plusList){
		List<String> hasProd = new ArrayList<String>();
		if(unLvl<=2){
			Map<String, HrtUnnoCostModel> current = agentUnitService.getAllHrtCostMap(1,unno,unLvl,1);
			if(current.containsKey("1|1|1") || current.containsKey("1|1|2") || current.containsKey("1|4|5")
					|| current.containsKey("1|6|8") || current.containsKey("1|6|9") || current.containsKey("1|6|10")){
				hasProd.add(PhoneProdConstant.PHONE_MD);
			}else{
				for (String s : mdList) {
					if(current.containsKey("1|1|"+s)){
						hasProd.add(PhoneProdConstant.PHONE_MD);
						break;
					}
				}
			}
			if(current.containsKey("1|1|20") || current.containsKey("1|1|21")){
				hasProd.add(PhoneProdConstant.PHONE_SYT);
			}
			for (String s : plusList) {
				if(current.containsKey("1|1|"+s)){
					hasProd.add(PhoneProdConstant.PHONE_PLUS);
					break;
				}
			}
		}else{
			Map<String, CheckProfitTemplateMicroModel> sub = checkUnitProfitMicroService.getProfitTemplateMap(1, unno, unLvl,1);
			if(sub.containsKey("1|1|1") || sub.containsKey("2|1|1") || sub.containsKey("3|1|1")){
				hasProd.add(PhoneProdConstant.PHONE_MD);
			}else{
				for (String s : mdList) {
					if(sub.containsKey("6|0|"+s)){
						hasProd.add(PhoneProdConstant.PHONE_MD);
						break;
					}
				}
			}
			if(sub.containsKey("5|0|0")){
				hasProd.add(PhoneProdConstant.PHONE_SYT);
			}

			for (String s : plusList) {
				if(sub.containsKey("6|0|"+s)){
					hasProd.add(PhoneProdConstant.PHONE_PLUS);
					break;
				}
			}
		}
		return hasProd;
	}


	@Override
	public DataGridBean searchM35TerminalInfoUnno(String unno,String sn,String mid) {
		if(mid !=null && !"".equals(mid)){
			String hql = "from MerchantInfoModel where mid = '"+mid+"'";
			List<MerchantInfoModel> mimlist = merchantInfoDao.queryObjectsByHqlList(hql);
			String sql2 = "";
			if(mimlist != null && mimlist.size() > 0){
				unno=mimlist.get(0).getUnno();
			}
		}
		String sql = "SELECT BTID,TERMID,KEYCONTEXT,decode (KEYTYPE,1,'短秘钥',2,'长秘钥',3,'微POSE秘钥') KEYTYPENAME FROM BILL_TERMINALINFO t WHERE t.UNNO = '"+unno+"' AND t.ALLOTCONFIRMDATE IS NOT NULL AND t.USEDCONFIRMDATE IS NULL AND MAINTAINTYPE != 'D' and ism35 =1 and sn='"+sn.trim()+"' ORDER BY t.BTID DESC";
		List<Map<String,String>> proList = terminalInfoDao.executeSql(sql);

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

	@Override
	public Integer updateBackTerminalInfo(TerminalInfoBean bean, UserBean user) {
		String sql = "";
		//String childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
		if(null !=bean.getTermIDStart() && !"".equals(bean.getTermIDStart()) && null != bean.getTermIDEnd() && !"".equals(bean.getTermIDEnd())
				&& (bean.getSnStart() == null || "".equals(bean.getSnStart()) ) && (bean.getSnEnd()==null||"".equals(bean.getSnEnd()))){
			Integer i = searchTidExist(bean,user);
			if(i<=0){
				return 0;
			}
			sql=" update  Bill_Terminalinfo t " +
					" set t.unno='"+user.getUnNo()+"' ,t.allotconfirmdate=sysdate,t.maintainuid="+user.getUserID()+" "+
					" where usedconfirmdate is null and t.termid>='"+bean.getTermIDStart()+"'"+
					" and  t.termid<='"+bean.getTermIDEnd()+"' and t.unno in(select unno from sys_unit where upper_unit='"+user.getUnNo()+"') ";	
		}else{
			Integer i = searchSnExist(bean,user);
			if(i<=0){
				return 0;
			}
			sql=" update  Bill_Terminalinfo t " +
					" set t.unno='"+user.getUnNo()+"' ,t.allotconfirmdate=sysdate,t.maintainuid="+user.getUserID()+" "+
					" where usedconfirmdate is null and t.sn>='"+bean.getSnStart()+"'"+
					" and  t.sn<='"+bean.getSnEnd()+"' and t.unno in(select unno from sys_unit where upper_unit='"+user.getUnNo()+"') ";	
		}

		terminalInfoDao.executeUpdate(sql);
		return 1;
	}

	@Override
	public DataGridBean searchBackTerminalListInfo(TerminalInfoBean bean,
			UserBean user) {
		String sql="select t.BTID,s.un_name ,t.UNNO, t.termid, t.SN, t.ism35 from Bill_Terminalinfo t,sys_unit s " +
				" where  t.unno=s.unno and t.usedconfirmdate is null and t.AllotConfirmDate is not null " +
				" and s.upper_unit ='"+user.getUnNo()+"'";		
		if (bean.getTermIDStart() != null && !"".equals(bean.getTermIDStart())) {
			sql +=" and termid >='"+bean.getTermIDStart()+"' ";
		}

		if (bean.getTermIDEnd() != null && !"".equals(bean.getTermIDEnd())) {
			sql +=" and termid <= '"+bean.getTermIDEnd()+"' ";
		}
		if(bean.getSnStart() !=null && !"".equals(bean.getSnStart())){
			sql +=" and sn >= '"+bean.getSnStart()+"' ";
		}
		if(bean.getSnEnd() != null && !"".equals(bean.getSnEnd())){
			sql +=" and sn <= '"+bean.getSnEnd()+"' ";
		}
		String sqlCount ="select count(*) from ("+sql+")";
		Integer count =terminalInfoDao.querysqlCounts2(sqlCount, null);
		List<Map<String,String>> proList = terminalInfoDao.queryObjectsBySqlList(sql, null, bean.getPage(), bean.getRows());
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(count);
		dgd.setRows(proList);
		return dgd;
	}

	@Override
	public DataGridBean findNoUseSnData(TerminalInfoBean bean, UserBean user) {
		//Map<String, Object> params=new HashMap<String, Object>();
		//权限
		String sql=" select * from bill_terminalinfo t where  t.allotconfirmdate is not null  and t.usedconfirmdate is null " +
				" and t.ism35=1 ";
		String sqlCount= "select count(*) from bill_terminalinfo t where  t.allotconfirmdate is not null  and t.usedconfirmdate is null " +
				"  and t.ism35=1 ";
		String childUnno="";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if(unitModel.getUnLvl() == 0){
		}else{
			childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
			sql+=" and t.unno in("+childUnno+")";
			sqlCount+=" and t.unno in("+childUnno+")";
		}

		if(bean.getSn()!=null && !"".equals(bean.getSn())){
			sql+=" and t.SN='"+bean.getSn()+"'";
			sqlCount+=" and t.SN='"+bean.getSn()+"'";
		}
		List<TerminalInfoModel> list=terminalInfoDao.queryObjectsBySqlLists(sql, null, bean.getPage(), bean.getRows(), TerminalInfoModel.class);
		Integer count=terminalInfoDao.querysqlCounts2(sqlCount, null);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(count);
		dgd.setRows(list);
		return dgd;
	}

	@Override
	public void deleteSnTerminalInfo(TerminalInfoBean bean) {
		String sql="delete bill_terminalinfo t where t.usedconfirmdate is null and t.ism35=1 and t.btid="+bean.getBtID();
		terminalInfoDao.executeUpdate(sql);
	}

	/**
	 * 查询rate
	 */
	@Override
	public DataGridBean queryTerminalRateInfo(String type) throws Exception {
		String sql = "SELECT rate,secondrate ,minfo1||'' as MINFO1,MINFO2 FROM sys_configure WHERE groupName='"+type+"'";
		List<Map<String,String>> proList = terminalInfoDao.executeSql(sql);
		DataGridBean dgd = new DataGridBean();
		List<Object>  list = new ArrayList<Object>();
		for (int i = 0; i < proList.size(); i++) {
			Map map = proList.get(i);
			map.put("KEYCONTEXT", map.get("RATE").toString()+"-"+map.get("SECONDRATE").toString());
			list.add(map);

		}
		dgd.setTotal(list.size());
		dgd.setRows(list);

		return dgd;
	}

	/**
	 * 查询rate--不包括90活动（也即是minfo2=1）{业务工单申请中}
	 */
	@Override
	public DataGridBean queryTerminalRatebyActiveInfo(String type,String active) throws Exception{
		String sql = "SELECT rate,secondrate ,minfo1||'' as MINFO1,MINFO2 FROM sys_configure WHERE groupName='"+type+"' and minfo2 != '"+active+"'";
		List<Map<String,String>> proList = terminalInfoDao.executeSql(sql);
		DataGridBean dgd = new DataGridBean();
		List<Object>  list = new ArrayList<Object>();
		for (int i = 0; i < proList.size(); i++) {
			Map map = proList.get(i);
			map.put("KEYCONTEXT", map.get("RATE").toString()+"-"+map.get("SECONDRATE").toString());
			list.add(map);

		}
		dgd.setTotal(list.size());
		dgd.setRows(list);

		return dgd;
	}
	/**
	 * 查询rate---将minfo2也拼接到返回值中{sn费率调整中}
	 */
	@Override
	public DataGridBean queryTerminalRateIncludeActiveInfo(String type) throws Exception {
		String sql = "SELECT rate,secondrate ,minfo1||'' as MINFO1,MINFO2 FROM sys_configure WHERE groupName='"+type+"' order by MINFO2,MINFO1";
		List<Map<String,String>> proList = terminalInfoDao.executeSql(sql);
		DataGridBean dgd = new DataGridBean();
		List<Object>  list = new ArrayList<Object>();
		for (int i = 0; i < proList.size(); i++) {
			Map map = proList.get(i);
			map.put("KEYCONTEXT", map.get("RATE").toString()+"-"+map.get("SECONDRATE").toString()+"-"+map.get("MINFO2").toString());
			list.add(map);
		}
		dgd.setTotal(list.size());
		dgd.setRows(list);

		return dgd;
	}

	/**
	 * 批量修改终端费率
	 */
	@Override
	public String editTerminalRateInfo(TerminalInfoBean bean, String btids,UserBean user) {
		boolean flag =false;
		String key = bean.getKeyContext();
		String keys [] = key.split("-");
		if(keys.length==3){
			String sql="update bill_terminalinfo t set t.rate="+keys[0]+",t.secondrate="+keys[1]+",t.maintainuid="+user.getUserID()+
					", t.maintaindate = sysdate,t.maintaintype='M',scanRate="+(bean.getScanRate()/100)+" where t.status!=2 and t.ism35=1 and t.btid in("+btids+")";


			String rateActive = keys[2];
			//判断是否是亿米付机构20191008
			if("b11023".equals(rateActive)) {

			}else {
				//获得相应的终端包含的活动
				String terminalActives = "select s.* from bill_terminalinfo s"+
						" where s.btid in("+btids+")";
				//判断获得的添加的费率是属于90活动-----0非90，1是90. 比较是否与添加的设备活动相匹配-----20190930
				if("1".equals(rateActive)) {
					terminalActives += " and ( s.rebatetype != '90' or s.rebatetype is null)";
					List<Map<String,String>> list = terminalInfoDao.executeSql(terminalActives);
					if(list.size()>0) {
						return "设备活动与调整费率不匹配";
					}
				}else if("0".equals(rateActive)){
					terminalActives += " and s.rebatetype = '90'";
					List<Map<String,String>> list = terminalInfoDao.executeSql(terminalActives);
					if(list.size()>0) {
						return "设备活动与调整费率不匹配";
					}
				}
			}
			try{
				terminalInfoDao.executeUpdate(sql);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "保存成功";
	}
	/**
	 * 批量修改终端费率(收银台)
	 */
	@Override
	public boolean editTerminalRateInfo2(TerminalInfoBean bean, String btids,UserBean user) {
		boolean flag =false;
		String key = bean.getKeyContext();
		String keys [] = key.split("-");
		if(keys.length==2){
			String sql="update bill_terminalinfo t set t.rate="+keys[0]+",t.secondrate="+keys[1]+",t.maintainuid="+user.getUserID()+
					", t.maintaindate = sysdate,t.maintaintype='M',scanRate="+keys[0]+" where t.status!=2 and t.ism35=1 and t.btid in("+btids+")";
			try{
				terminalInfoDao.executeUpdate(sql);
				flag = true ;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	/**
	 * 判断是否存在已使用的终端
	 */
	@Override
	public boolean queryTerminalStatusInfo(TerminalInfoBean bean, String btids,UserBean user) {
		boolean flag =false;
		String sql ="select count(*) from bill_terminalinfo t where t.status=2 and t.btID in ("+btids+")";
		BigDecimal d = terminalInfoDao.querysqlCounts(sql, null);
		if(d!=null&&d.intValue()>0){
			flag = true;
		}
		return flag;
	}
	/**
	 * 判断是否特殊机构
	 */
	@Override
	public boolean queryTerminalLimitUnno(String limitUnno,String btids) {
		String[] bitdsArray = null;
		bitdsArray = btids.split(",");
		for (int i = 0; i < bitdsArray.length; i++) {
			String bitd = bitdsArray[i];
			String sql ="select count(*) from bill_terminalinfo t"+
					" where 1=1 and t.BTID = "+bitd+""+
					" and t.unno in (select s.unno from sys_unit s  start with unno in ('"+limitUnno+"')"+
					" connect by NOCYCLE  prior unno =  upper_unit)";
			BigDecimal d = terminalInfoDao.querysqlCounts(sql, null);
			if(d!=null&&d.intValue()>0){
				return true;
			}
		}
		return false;
	}

	/**
	 * 微商户SN号导入审核
	 */
	@Override
	public DataGridBean checkAddSn(TerminalInfoBean bean, UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql=" select * from bill_terminalinfo where status = 3 ";
		String sqlCount= "select count(*) from bill_terminalinfo where status = 3 ";
		//		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if(bean.getUnNO()!=null&&!"".equals(bean.getUnNO())){
			sql += " and unno=:unno ";
			sqlCount += " and unno=:unno";
			map.put("unno", bean.getUnNO());
		}
		if(StringUtils.isNotEmpty(bean.getMaintainType())){
			//			select * from sys_unit t where t.un_lvl=1 start with t.unno='111000' connect by t.unno=prior t.upper_unit
			sql += " and unno in (select t.unno from sys_unit t where t.un_lvl=1 start with t.unno=:maintainType connect by t.unno=prior t.upper_unit)";
			sqlCount += " and unno in (select t.unno from sys_unit t where t.un_lvl=1 start with t.unno=:maintainType connect by t.unno=prior t.upper_unit)";
			map.put("maintainType", bean.getMaintainType());
		}
		if(bean.getSnStart()!=null&&!"".equals(bean.getSnStart())){
			sql += " and sn>=:snStart ";
			sqlCount += " and sn>=:snStart ";
			map.put("snStart", bean.getSnStart());
		}
		if(bean.getSnEnd()!=null&&!"".equals(bean.getSnEnd())){
			sql += " and sn<=:snEnd ";
			sqlCount += " and sn<=:snEnd ";
			map.put("snEnd", bean.getSnEnd());
		}
		if(bean.getRebateType()!=null&&!"".equals(bean.getRebateType()) && bean.getRebateType().intValue()!=-1){
			sql += " and rebatetype=:rebatetype ";
			sqlCount += " and rebatetype=:rebatetype ";
			map.put("rebatetype", bean.getRebateType());
		}
		if(bean.getKeyConfirmDate()!=null&&!"".equals(bean.getKeyConfirmDate())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(bean.getKeyConfirmDate());
			sql += " and keyconfirmdate >= to_date('"+date+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and "
					+ "keyconfirmdate <= to_date('"+date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
			sqlCount += " and keyconfirmdate >= to_date('"+date+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and "
					+ "keyconfirmdate <= to_date('"+date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		if(StringUtils.isNotEmpty(bean.getTerOrderID())){
			sql += " and terorderid=:terorderid ";
			sqlCount += " and terorderid=:terorderid ";
			map.put("terorderid", bean.getTerOrderID());
		}

		List<TerminalInfoModel> list=terminalInfoDao.queryObjectsBySqlLists(sql, map, bean.getPage(), bean.getRows(), TerminalInfoModel.class);
		Integer count=terminalInfoDao.querysqlCounts2(sqlCount, map);
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}

	/**
	 * 审核通过所有sn
	 */
	@Override
	public void throughAllSn(String btids, UserBean user) {
		String sql="update bill_terminalinfo set status = 1 where status=3 and btid in ("+btids+")";
		terminalInfoDao.executeUpdate(sql);
	}

	/**
	 * 审核退回所有sn
	 */
	@Override
	public void rejectAllSn(String btids, UserBean user) {
		String sql="update bill_terminalinfo set status = 4 where status=3 and btid in ("+btids+")";
		terminalInfoDao.executeUpdate(sql);
	}

	/**
	 * 微商户SN号导入审核
	 */
	@Override
	public DataGridBean queryUpdateAddSn(TerminalInfoBean bean, UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql=" select * from bill_terminalinfo where status = 4 ";
		String sqlCount= "select count(*) from bill_terminalinfo where status = 4 ";
		if(bean.getUnNO()!=null&&!"".equals(bean.getUnNO())){
			sql += " and unno=:unno ";
			sqlCount += " and unno=:unno";
			map.put("unno", bean.getUnNO());
		}
		if(bean.getSnStart()!=null&&!"".equals(bean.getSnStart())){
			sql += " and sn>=:snStart ";
			sqlCount += " and sn>=:snStart ";
			map.put("snStart", bean.getSnStart());
		}
		if(bean.getSnEnd()!=null&&!"".equals(bean.getSnEnd())){
			sql += " and sn<=:snEnd ";
			sqlCount += " and sn<=:snEnd ";
			map.put("snEnd", bean.getSnEnd());
		}
		if(bean.getKeyConfirmDate()!=null&&!"".equals(bean.getKeyConfirmDate())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(bean.getKeyConfirmDate());
			sql += " and keyconfirmdate >= to_date('"+date+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and "
					+ "keyconfirmdate <= to_date('"+date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
			sqlCount += " and keyconfirmdate >= to_date('"+date+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and "
					+ "keyconfirmdate <= to_date('"+date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		List<TerminalInfoModel> list=terminalInfoDao.queryObjectsBySqlLists(sql, map, bean.getPage(), bean.getRows(), TerminalInfoModel.class);
		Integer count=terminalInfoDao.querysqlCounts2(sqlCount, map);
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}

	/**
	 * 修改返利类型及状态
	 */
	@Override
	public void updateAddSn(TerminalInfoBean bean) {
		Map<String, Object> param=new HashMap<String, Object>();
		String sql="update bill_terminalinfo set status = 1,rebateType =:rebateType where status=4 ";
		param.put("rebateType", bean.getRebateType());
		if(bean.getBtID()!=null&&!"".equals(bean.getBtID())){
			sql += " and btID = :btID ";
			param.put("btID", bean.getBtID());
		}else if((bean.getSnStart()!=null&&!"".equals(bean.getSnStart()))||(bean.getSnEnd()!=null&&!"".equals(bean.getSnEnd()))){
			if(bean.getUnNO()!=null&&!"".equals(bean.getUnNO())){
				sql += " and unno=:unno ";
				param.put("unno", bean.getUnNO());
			}
			if(bean.getSnStart()!=null&&!"".equals(bean.getSnStart())){
				sql += " and sn>=:snStart ";
				param.put("snStart", bean.getSnStart());
			}
			if(bean.getSnEnd()!=null&&!"".equals(bean.getSnEnd())){
				sql += " and sn<=:snEnd ";
				param.put("snEnd", bean.getSnEnd());
			}
			if(bean.getKeyConfirmDate()!=null&&!"".equals(bean.getKeyConfirmDate())){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(bean.getKeyConfirmDate());
				sql += " and keyconfirmdate >= to_date('"+date+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and "
						+ "keyconfirmdate <= to_date('"+date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
			}
		}
		terminalInfoDao.executeSqlUpdate(sql, param);
	}

	/**
	 * 审核通过所有sn2
	 */
	@Override
	public void throughSn(TerminalInfoBean bean, UserBean user) {
		String sql = "update bill_terminalinfo set status = 1 where status=3 ";
		Map<String, Object> param=new HashMap<String, Object>();
		if(bean.getUnNO()!=null&&!"".equals(bean.getUnNO())){
			sql += " and unno=:unno ";
			param.put("unno", bean.getUnNO());
		}
		if(bean.getSnStart()!=null&&!"".equals(bean.getSnStart())){
			sql += " and sn>=:snStart ";
			param.put("snStart", bean.getSnStart());
		}
		if(bean.getSnEnd()!=null&&!"".equals(bean.getSnEnd())){
			sql += " and sn<=:snEnd ";
			param.put("snEnd", bean.getSnEnd());
		}
		if(bean.getKeyConfirmDate()!=null&&!"".equals(bean.getKeyConfirmDate())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(bean.getKeyConfirmDate());
			sql += " and keyconfirmdate >= to_date('"+date+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and "
					+ "keyconfirmdate <= to_date('"+date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		terminalInfoDao.executeSqlUpdate(sql, param);
	}

	/**
	 * 审核退回所有sn2
	 */
	@Override
	public void rejectSn(TerminalInfoBean bean, UserBean user) {
		String sql = "update bill_terminalinfo set status = 4 where status=3 ";
		Map<String, Object> param=new HashMap<String, Object>();
		if(bean.getUnNO()!=null&&!"".equals(bean.getUnNO())){
			sql += " and unno=:unno ";
			param.put("unno", bean.getUnNO());
		}
		if(bean.getSnStart()!=null&&!"".equals(bean.getSnStart())){
			sql += " and sn>=:snStart ";
			param.put("snStart", bean.getSnStart());
		}
		if(bean.getSnEnd()!=null&&!"".equals(bean.getSnEnd())){
			sql += " and sn<=:snEnd ";
			param.put("snEnd", bean.getSnEnd());
		}
		if(bean.getKeyConfirmDate()!=null&&!"".equals(bean.getKeyConfirmDate())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(bean.getKeyConfirmDate());
			sql += " and keyconfirmdate >= to_date('"+date+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and "
					+ "keyconfirmdate <= to_date('"+date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		terminalInfoDao.executeSqlUpdate(sql, param);
	}

	/**
	 * 进销存系统出库
	 */
	@SuppressWarnings("all")
	@Override
	public synchronized List updateTerminalInfoJXC_chuku(String xlsfile, UserBean user,String fileName,String orderID,String pdlid,String NEW_UNNO,String NEW_REBATETYPE,double NEW_RATE,double SCANRATE,double SECONRATE,Double SCANRATEUP,Double HUABEIRATE,String DEPOSITAMT) {
		//返回的List
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try	{
			//读取文件
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);
			String SN = "";
			String OLD_UNNO = "110000";
			String MAINTAINDATE_String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime());
			int MAINTAINUID = user.getUserID();
			int num = 0;
			int deliverStatus = 3;
			int detailStatus=7;
			Integer z = terminalInfoDao.querysqlCounts2("select d.deliveNum-d.AllocatedNum from bill_PurchaseDeliver d WHERE pdlid="+pdlid, null);
			log.info("进销存系统出库开始:orderID="+orderID+";fileName="+fileName+";可分配="+z+";NEW_UNNO="+NEW_UNNO+";NEW_REBATETYPE="+NEW_REBATETYPE+";NEW_RATE="+NEW_RATE+";SECONRATE="+SECONRATE+";user="+MAINTAINUID+
					"SCANRATEUP="+SCANRATEUP+";HUABEIRATE="+HUABEIRATE);
			//读取表格信息1
			for(int i = 1; i < sheet.getLastRowNum()+1&&i<=z; i++){
				Map<String,String> map = new HashMap<String, String>();
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell;
				cell = row.getCell((short)0);
				if(cell == null || cell.toString().trim().equals("")){
					return null;
				}else{
					//读取到的SN
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					SN = row.getCell((short)0).getStringCellValue().trim();
					//					//读取到的OLD_UNNO
					//					row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
					//					OLD_UNNO = row.getCell((short)1).getStringCellValue().trim();
					//					读取到的OLD_REBATETYPE
					//					row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
					//					OLD_REBATETYPE = row.getCell((short)2).getStringCellValue();
					//					//读取到的NEW_UNNO
					//					row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
					//					NEW_UNNO = row.getCell((short)1).getStringCellValue().trim();
					//					//读取到的NEW_REBATETYPE
					//					row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
					//					NEW_REBATETYPE = row.getCell((short)2).getStringCellValue();
					//					//读取到的NEW_RATE
					//					row.getCell((short)3).setCellType(Cell.CELL_TYPE_NUMERIC);
					//					NEW_RATE = row.getCell((short)3).getNumericCellValue();
					//					//读取到的SECONDRATE
					//					row.getCell((short)4).setCellType(Cell.CELL_TYPE_NUMERIC);
					//					SECONRATE = row.getCell((short)4).getNumericCellValue();
					//判断空值
					if (SN != null && !"".equals(SN)&& NEW_UNNO!=null && !"".equals(NEW_UNNO)  
							&& NEW_REBATETYPE!=null  && !"".equals(NEW_REBATETYPE)&& NEW_RATE!=-1 && !"".equals(NEW_RATE)&& SECONRATE!=-1  && !"".equals(SECONRATE)){
						//SQL
						StringBuffer sql = new StringBuffer();
						sql.append("update bill_terminalinfo a set a.unno = '").append(NEW_UNNO).append("',a.rebatetype = '").append(NEW_REBATETYPE);							
						sql.append("',a.rate= ").append(NEW_RATE);                                                           //RATE       
						sql.append(",a.scanRate= ").append(SCANRATE);                                                           //SCANRATE       
						sql.append(",a.secondrate= ").append(SECONRATE);                                                     //SECONDRATE
						if(HUABEIRATE!=null){
							sql.append(",a.huabeirate= ").append(HUABEIRATE);
						}

						if(SCANRATEUP!=null){
							sql.append(",a.scanRateUp= ").append(SCANRATEUP);
						}
						boolean hasDEPOSITAMT=false;
						if(DEPOSITAMT!=null){
							hasDEPOSITAMT=true;
							sql.append(",a.depositAmt=").append(DEPOSITAMT);
						}
						sql.append(",a.maintaindate = to_date('").append(MAINTAINDATE_String).append("','yyyy-MM-dd HH24:mi:ss')"); 
						sql.append(",a.maintainuid = ").append(MAINTAINUID);                                                  //userid
						sql.append(",a.pdlid = ").append(pdlid);                                                              //pdlid
						sql.append(",a.terOrderID = '").append(orderID).append("' ");                                                       //terOrderID
						sql.append(",a.outdate = sysdate ");                                         //outDate出库日期
						if(!"0".equals(NEW_REBATETYPE)){
							sql.append(",a.status =3 ");
							String amtSql="select k.* from bill_purconfigure k where k.type=3 and k.valueinteger=:valueinteger ";
							Map amtMap = new HashMap();
							amtMap.put("valueinteger",NEW_REBATETYPE);
							List<Map<String,Object>> listAmt = terminalInfoDao.queryObjectsBySqlListMap2(amtSql,amtMap);
							// 出库终端配置
							if(!hasDEPOSITAMT && listAmt.size()==1){
								if(null!=listAmt.get(0).get("DEPOSITAMT")) {
									sql.append(",a.depositAmt = ").append(listAmt.get(0).get("DEPOSITAMT").toString());
								}
//								if(null != listAmt.get(0).get("RATE")) {
//									sql.append(",a.RATE = ").append(listAmt.get(0).get("RATE"));
//								}
//								if(null != listAmt.get(0).get("SECONDRATE")) {
//									sql.append(",a.SECONDRATE = ").append(listAmt.get(0).get("SECONDRATE"));
//								}
//								if(null != listAmt.get(0).get("SCANRATE")) {
//									sql.append(",a.SCANRATE = ").append(listAmt.get(0).get("SCANRATE"));
//								}
//								if(null != listAmt.get(0).get("SCANRATEUP")) {
//									sql.append(",a.SCANRATEUP = ").append(listAmt.get(0).get("SCANRATEUP"));
//								}
//								if(null != listAmt.get(0).get("HUABEIRATE")) {
//									sql.append(",a.HUABEIRATE = ").append(listAmt.get(0).get("HUABEIRATE"));
//								}
							}else{
							}
						}else{
							// @author:xuegangliu-20190225 活动为0的出库状态改为1
							sql.append(",a.status =1 ");
						}
						sql.append(" where a.unno = '").append(OLD_UNNO);                                                     
						sql.append("' and a.sn = '").append(SN).append("' and a.status in (0,1,3,4)");
						Integer integer = terminalInfoDao.executeSqlUpdate(sql.toString(),null);
						if(integer == 0){
							map.put("sn", SN);
							map.put("old_unno", OLD_UNNO);
							//						map.put("old_rebatetype", OLD_REBATETYPE);
							map.put("text", "SN未在此机构下使用");
							list.add(map);
						}else{
							//已入库数量+1
							num++;
						}
					}	
				}
			}
			if(num>0){
				//导入操作完成
				String s="select abs(machineNum)-importNum from  bill_PurchaseDetail WHERE pdid in (select pdid from bill_PurchaseDeliver where pdlid="+pdlid+")";
				//明细表剩余的数量
				Integer all = terminalInfoDao.querysqlCounts2(s, null);
				if(all==0&&z==num){
					deliverStatus=4;
					detailStatus=8;
				}else if(z==num){
					deliverStatus=4;
				}
				Integer i = terminalInfoDao.executeSqlUpdate("update bill_PurchaseDetail set detailStatus="+detailStatus+" WHERE pdid in (select pdid from bill_PurchaseDeliver where pdlid="+pdlid+")", null);
				Integer j = terminalInfoDao.executeSqlUpdate("update bill_PurchaseDeliver set AllocatedNum=AllocatedNum+"+num+",deliverStatus="+deliverStatus+" WHERE pdlid="+pdlid+" and deliveNum>=AllocatedNum+"+num, null);
				log.info("进销存系统c结束:orderID="+orderID+";fileName="+fileName+";已分配="+num+";j="+j);
			}
		} catch (Exception e) {
			log.info("进销存系统出库异常:orderID="+orderID+";fileName="+fileName,e);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 进销存系统出库
	 */
	@Override
	public synchronized Integer updateM35ForReturn(String xlsfile,String name, UserBean user,String pdlid,String machineModel) {
		int num1 = 0;
		try {
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);

			Integer z = terminalInfoDao.querysqlCounts2("select (-d.deliveNum)-d.AllocatedNum from bill_PurchaseDeliver d WHERE pdlid="+pdlid, null);
			log.info("进销存系统退货开始:fileName="+name+";可退货="+z);
			if(z<sheet.getLastRowNum()){
				//导入数量大于待退货数量
				return -1;
			}

			for(int i = 1; i < sheet.getLastRowNum()+1; i++){

				HSSFRow row = sheet.getRow(i);
				String snValue="";
				HSSFCell cell = row.getCell((short)0);
				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
					//SN号
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					snValue = row.getCell((short)0).getStringCellValue().trim();
					TerminalInfoModel terminalInfoModel = terminalInfoDao.queryObjectByHql("from TerminalInfoModel where sn=? and unno='110000' and machineModel=? ", new Object[]{snValue,machineModel});
					if(terminalInfoModel!=null){
						terminalInfoModel.setMaintainType("D");
						terminalInfoModel.setMaintainDate(new Date());
						terminalInfoDao.updateObject(terminalInfoModel);
						num1++;
					}
				}
			}
			//更新盘盈盘亏表
			if(z==num1){
				Integer j = terminalInfoDao.executeSqlUpdate("update bill_PurchaseDetail set detailStatus=8 WHERE pdid in (select pdid from bill_PurchaseDeliver where pdlid="+pdlid+")", null);
			}
			Integer j = terminalInfoDao.executeSqlUpdate("update bill_PurchaseDeliver set AllocatedNum=AllocatedNum+"+num1+",deliverStatus=5 WHERE pdlid="+pdlid+" and (-deliveNum)>=AllocatedNum+"+num1, null);
			log.info("进销存系统退货结束:fileName="+name+";已分配="+num1+";j="+j);
		} catch (Exception e) {
			log.info("进销存系统退货异常",e);
			e.printStackTrace();
		}
		return num1;
	}

	/**
	 * 未使用设备转移
	 */
	@SuppressWarnings("all")
	@Override
	public synchronized List updateTerminalInfoUET(String xlsfile, UserBean user,String fileName) {
		//返回的List
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		//boolean flag = false;
		try	{
			//读取文件
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);
			String SN = "";
			String OLD_UNNO = "";
			String OLD_REBATETYPE = "";
			String NEW_UNNO = "";
			String NEW_REBATETYPE = "";
			double NEW_RATE = -1;
			double SECONRATE = -1;
			String MAINTAINDATE_String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime());
			int MAINTAINUID = user.getUserID();
			//读取表格信息
			for(int i = 1; i < sheet.getLastRowNum()+1; i++){
				Map<String,String> map = new HashMap<String, String>();
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell;
				cell = row.getCell((short)0);
				if(cell == null || cell.toString().trim().equals("")){
					return null;
				}else{
					//读取到的SN
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					SN = row.getCell((short)0).getStringCellValue().trim();
					//读取到的OLD_UNNO
					row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
					OLD_UNNO = row.getCell((short)1).getStringCellValue().trim();
					//读取到的OLD_REBATETYPE
					//					row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
					//					OLD_REBATETYPE = row.getCell((short)2).getStringCellValue();
					//读取到的NEW_UNNO
					row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
					NEW_UNNO = row.getCell((short)2).getStringCellValue().trim();
					//读取到的NEW_REBATETYPE
					row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
					NEW_REBATETYPE = row.getCell((short)3).getStringCellValue();
					//读取到的NEW_RATE
					row.getCell((short)4).setCellType(Cell.CELL_TYPE_NUMERIC);
					NEW_RATE = row.getCell((short)4).getNumericCellValue();
					//读取到的SECONDRATE
					row.getCell((short)5).setCellType(Cell.CELL_TYPE_NUMERIC);
					SECONRATE = row.getCell((short)5).getNumericCellValue();
					//判断空值
					if (SN != null && !"".equals(SN)
							&& OLD_UNNO!=null  && !"".equals(OLD_UNNO)    
							&& NEW_UNNO!=null && !"".equals(NEW_UNNO)  
							&& NEW_REBATETYPE!=null  && !"".equals(NEW_REBATETYPE)  
							&& NEW_RATE!=-1 && !"".equals(NEW_RATE) 
							&& SECONRATE!=-1  && !"".equals(SECONRATE)){
						//SQL
						String sql ="update bill_terminalinfo a set " +
								"a.unno = '"+ NEW_UNNO+
								"',a.rebatetype = '"+NEW_REBATETYPE+							
								"',a.rate= "+NEW_RATE+                                                               //RATE       
								",a.secondrate= "+SECONRATE+                                                        //SECONDRATE  
								",a.maintaindate = to_date('"+MAINTAINDATE_String+"','yyyy-MM-dd HH24:mi:ss')"+    //系统时间 
								",a.maintainuid = "+MAINTAINUID+                                                  //userid
								" where "+
								" a.unno = '"+OLD_UNNO+                                     //REBATETYPE
								"' and a.sn = '"+SN+                                                          //SN
								"' and a.status= 1";
						Integer integer = terminalInfoDao.executeSqlUpdate(sql,null);
						if(integer == 0){
							map.put("sn", SN);
							map.put("old_unno", OLD_UNNO);
							//						map.put("old_rebatetype", OLD_REBATETYPE);
							map.put("text", "SN未在此机构下使用");
							list.add(map);
						}
					}	
				}
			}

		} catch (Exception e) {
			//flag=false;
			e.printStackTrace();
		}
		return list;
	}	
	/**
	 * 入库管理--上传明细文件--代理单-退货
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Override
	public List<Map<String, String>> updateM35SnInfoPur3(String PDID, String xlsfile, String fileName, String ORDERID,
			String MACHINEMODEL, String snType, UserBean user, String ORDERMETHOD, String ORDERTYPE, String UNNO) throws Exception {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		//判断机构号
		String unnoValue = "110000";
		int num1 = 0;
		int detailstatus = 7;
		Integer remainNum = terminalInfoDao.querysqlCounts2("select abs(l.machinenum)-l.importnum from bill_purchasedetail l WHERE pdid="+PDID, null);
		log.info("sn入库：订单号="+ORDERID+";PDID="+PDID+";MACHINEMODEL="+MACHINEMODEL+";允许导入条目="+remainNum);
		File filename = new File(xlsfile);
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
		HSSFSheet sheet = workbook.getSheetAt(0);			
		for(int i = 1; i < sheet.getLastRowNum()+1&&i<=remainNum; i++){
			HSSFRow row = sheet.getRow(i);
			String snValue="";
			HSSFCell cell = row.getCell((short)0);
			if(cell == null || cell.toString().trim().equals("")){
				break;
			}else{
				//SN号
				row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
				snValue = row.getCell((short)0).getStringCellValue().trim();
				//机构号
				row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
				String unno=row.getCell((short)1).getStringCellValue();

				String sql = "select sn,unno from bill_terminalinfo where sn='"+snValue+"' and unno='"+unno+"' and status= 1"; 
				List<Map<String, Object>> snlist=terminalInfoDao.queryObjectsBySqlObject(sql);
				if(snlist.size()<=0){
					Map<String,String> map = new HashMap<String,String>();
					String sn = snValue;
					map.put("sn", sn);
					map.put("unno", unno);
					map.put("remark", "sn号和对应的机构号不存在！");
					list.add(map);
					continue;
				}else{
					String upsql="update bill_terminalinfo set unno='"+unnoValue+"' where sn='"+snValue+"'";
					terminalInfoDao.executeUpdate(upsql);
					num1++;
				}
			}
		}
		if(num1>0){
			if(remainNum==num1)detailstatus=8;
			Integer j = terminalInfoDao.executeSqlUpdate("update bill_purchasedetail set importnum=importnum+"+num1+",detailstatus="+detailstatus+",importDate=sysdate,importer='"+user.getLoginName()+"' WHERE pdid="+PDID+" and abs(machinenum)>=importnum+"+num1, null);
			System.out.println("变更入库单已入库数量="+j);
			if(j==0){
				j=1/0;
			}
		}
		return list;
	}
	/**
	 * 入库管理--上传分段文件--代理单-退货
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Override
	public List<Map<String, String>> updateM35SnInfoPur2(String PDID, String xlsfile, String fileName, String ORDERID,
			String MACHINEMODEL, String snType, UserBean user, String ORDERMETHOD, String ORDERTYPE, String UNNO) throws Exception{
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		//判断机构号
		String unnoValue = "110000";
		int num1 = 0;
		int detailstatus = 7;
		Integer remainNum = terminalInfoDao.querysqlCounts2("select abs(l.machinenum)-l.importnum from bill_purchasedetail l WHERE pdid="+PDID, null);
		log.info("sn入库：订单号="+ORDERID+";PDID="+PDID+";MACHINEMODEL="+MACHINEMODEL+";允许导入条目="+remainNum);
		File filename = new File(xlsfile);
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
		HSSFSheet sheet = workbook.getSheetAt(0);

		//开始操作第j行
		for(int j=1;j<sheet.getLastRowNum()+1;j++){
			HSSFRow row = sheet.getRow(j);
			HSSFCell cell = row.getCell((short)0);

			String snValueHead="";
			Integer snValuePre=0;
			Integer snValueSuf=0;

			if(cell == null || cell.toString().trim().equals("")){
				break;
			}else{
				//SN号前缀
				row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
				snValueHead = row.getCell((short)0).getStringCellValue().trim();
				//SN号开始
				row.getCell((short)1).setCellType(Cell.CELL_TYPE_NUMERIC);
				snValuePre = (int) row.getCell((short)1).getNumericCellValue();
				//SN号结束
				row.getCell((short)2).setCellType(Cell.CELL_TYPE_NUMERIC);
				snValueSuf = (int) row.getCell((short)2).getNumericCellValue();
				//机构号
				row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
				String unno=row.getCell((short)3).getStringCellValue();
				//分段倒入最多100
				if(snValueSuf-snValuePre>100){
					snValueSuf = snValuePre+100;
				}
				Integer count=0;
				//导入数量小于待导入数量  200-100<=100-0
				if(snValueSuf-snValuePre<=remainNum-num1){
					count = snValueSuf-snValuePre;
				}else if(remainNum-num1==0){
					break;
				}else{
					count = remainNum-num1;
				}
				for(int i = 1; i<=count; i++){

					String snValue="";

					snValue = snValueHead+(snValuePre+i-1);
					String sql = "select sn,unno from bill_terminalinfo where sn='"+snValue+"' and unno='"+unno+"' and status=1 "; 
					List<Map<String, Object>> snlist=terminalInfoDao.queryObjectsBySqlObject(sql);
					if(snlist.size()<=0){
						Map<String,String> map = new HashMap<String,String>();
						String sn = snValue;
						map.put("sn", sn);
						map.put("unno", unno);
						map.put("remark", "sn号和对应的机构号不存在！");
						list.add(map);
						continue;
					}else{
						String upsql="update bill_terminalinfo set unno='"+unnoValue+"' where sn='"+snValue+"'";
						terminalInfoDao.executeUpdate(upsql);
						num1++;
					}

				}					
			}

		}

		if(num1>0){
			if(remainNum==num1)detailstatus=8;
			Integer j = terminalInfoDao.executeSqlUpdate("update bill_purchasedetail set importnum=importnum+"+num1+",detailstatus="+detailstatus+",importDate=sysdate,importer='"+user.getLoginName()+"' WHERE pdid="+PDID+" and abs(machinenum)>=importnum+"+num1, null);
			System.out.println("变更入库单已入库数量="+j);
			if(j==0){
				j = 1/0;
			}
		}
		return list;
	}
	@Override
	public DataGridBean listTerminalNumByMachineModel(TerminalInfoBean bean) {
		DataGridBean dgb = new DataGridBean();
		Map<String, Object> params=new HashMap<String, Object>();
		String sql = "select a.machinemodel,a.storage,count(a.btid) allnum from bill_terminalinfo a where a.unno='110000' and a.maintaintype!='D' ";
		String sqlCount = "select count(count(a.machinemodel)) from bill_terminalinfo a where a.unno='110000' and a.maintaintype!='D' ";
		if (bean.getMachineModel() != null && !"".equals(bean.getMachineModel())) {
			sql +=" AND a.machineModel = :machineModel";
			sqlCount += " AND a.machineModel = :machineModel";
			params.put("machineModel", bean.getMachineModel());
		}
		if (bean.getStorage() != null && !"".equals(bean.getStorage())) {
			sql +=" AND a.storage = :storage";
			sqlCount += " AND a.storage = :storage";
			params.put("storage", bean.getStorage());
		} 

		sql += " group by a.machinemodel,a.storage order by a.storage ";
		sqlCount += " group by a.machinemodel,a.storage ";
		long counts = terminalInfoDao.querysqlCounts2(sqlCount, params);
		List<Map<String,String>> list = terminalInfoDao.queryObjectsBySqlList(sql, params, bean.getPage(), bean.getRows());
		dgb.setRows(list);
		dgb.setTotal(counts);
		return dgb;
	}

	@Override
	public DataGridBean findMerterminalInfo(TerminalInfoBean bean, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		Map<String, Object> params=new HashMap<String, Object>();
		String sql = "select a.mtid,a.model,a.deliverdate,a.delivernum,a.unnoname,a.snstart,a.snend,a.unno from bill_merterminalinfo a where 1=1 ";
		String sqlCount = "select count(*) from bill_merterminalinfo a where 1=1 ";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (bean.getKeyConfirmDate() != null && !"".equals(bean.getKeyConfirmDate())) {
			sql +=" AND a.deliverdate >= "+sdf.format(bean.getKeyConfirmDate())+"";
			sqlCount += " AND a.deliverdate >= "+sdf.format(bean.getKeyConfirmDate())+"";
		}
		if (bean.getKeyConfirmDateEnd() != null && !"".equals(bean.getKeyConfirmDateEnd())) {
			sql +=" AND a.deliverdate <= "+sdf.format(bean.getKeyConfirmDateEnd())+"";
			sqlCount += " AND a.deliverdate <= "+sdf.format(bean.getKeyConfirmDateEnd())+"";
		}
		sql += " order by a.deliverdate ";
		long counts = terminalInfoDao.querysqlCounts2(sqlCount, params);
		List<Map<String,String>> list = terminalInfoDao.queryObjectsBySqlList(sql, params, bean.getPage(), bean.getRows());
		dgb.setRows(list);
		dgb.setTotal(counts);
		return dgb;
	}

	@Override
	public Integer addMerterminalInfo(TerminalInfoBean bean, UserBean user) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Integer i = terminalInfoDao.querysqlCounts2("select count(*) from bill_merterminalinfo where snstart="+bean.getSnStart()+" or snend="+bean.getSnEnd()+" ",null);
		if(i>0){
			return 0;
		}

		String sql = "insert into bill_merterminalinfo (unno,unnoname,model,delivernum,snstart,snend,deliverdate) values "
				+ "('"+bean.getUnNO()+"','"+bean.getUnitName()+"','"+bean.getMachineModel()+"',"+bean.getDeliverNum()+",'"+bean.getSnStart()+"','"+bean.getSnEnd()+"','"+sdf.format(bean.getMaintainDate())+"') ";

		terminalInfoDao.executeUpdate(sql);
		return 1;
	}

	private Integer searchSnExist(TerminalInfoBean bean, UserBean user) {
		Integer i = terminalInfoDao.querysqlCounts2("select count(*) from bill_terminalinfo where sn='"+bean.getSnStart()+"' or sn='"+bean.getSnEnd()+"' ",null);
		//判断两个sn是否都存在
		if(i==2){//两个都存在
			return 2;
		}else if(i==1&&bean.getSnStart().equals(bean.getSnEnd())){//开始结尾是一个
			return 1;
		}else{//不存在
			return 0;
		}
	}

	private Integer searchTidExist(TerminalInfoBean bean, UserBean user) {
		Integer i = terminalInfoDao.querysqlCounts2("select count(*) from bill_terminalinfo where termid='"+bean.getTermIDStart()+"' or termid='"+bean.getTermIDEnd()+"' ",null);
		//判断两个sn是否都存在
		if(i==2){//两个都存在
			return 2;
		}else if(i==1&&bean.getTermIDStart().equals(bean.getTermIDEnd())){//开始结尾是一个
			return 1;
		}else{//不存在
			return 0;
		}
	}

	@Override
	public DataGridBean queryTerminalStorage(TerminalInfoBean bean, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		Map<String, Object> params=new HashMap<String, Object>();
		String sql = "select a.machinemodel,count(a.btid) MACNUM from bill_terminalinfo a where 1=1 ";
		String sqlCount = "";
		//入库时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (bean.getKeyConfirmDate() != null && !"".equals(bean.getKeyConfirmDate())) {
			sql +=" AND a.KeyConfirmDate >= to_date('"+sdf.format(bean.getKeyConfirmDate())+"000000','yyyymmddhh24miss')";
		}
		if (bean.getKeyConfirmDateEnd() != null && !"".equals(bean.getKeyConfirmDateEnd())) {
			sql +=" AND a.KeyConfirmDate <= to_date('"+sdf.format(bean.getKeyConfirmDateEnd())+"235959','yyyymmddhh24miss')";
		}
		//出库时间
		if (bean.getOutDate() != null && !"".equals(bean.getOutDate())) {
			sql +=" AND a.outdate >= to_date('"+sdf.format(bean.getOutDate())+"000000','yyyymmddhh24miss')";
		}
		if (bean.getOutDateEnd() != null && !"".equals(bean.getOutDateEnd())) {
			sql +=" AND a.outdate <= to_date('"+sdf.format(bean.getOutDateEnd())+"235959','yyyymmddhh24miss')";
		}
		sql += " group by machinemodel ";
		sqlCount = "select count(*) from ("+sql+")";
		long counts = terminalInfoDao.querysqlCounts2(sqlCount, params);
		List<Map<String,String>> list = terminalInfoDao.queryObjectsBySqlList(sql, params, bean.getPage(), bean.getRows());
		dgb.setRows(list);
		dgb.setTotal(counts);
		return dgb;
	}
	@Override
	public HSSFWorkbook exportTerminalStorage(TerminalInfoBean bean) {
		Map<String, Object> params=new HashMap<String, Object>();
		String sql = "select a.machinemodel,count(a.btid) MACNUM from bill_terminalinfo a where 1=1 ";
		String sqlCount = "";
		//入库时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (bean.getKeyConfirmDate() != null && !"".equals(bean.getKeyConfirmDate())) {
			sql +=" AND a.KeyConfirmDate >= to_date('"+sdf.format(bean.getKeyConfirmDate())+"000000','yyyymmddhh24miss')";
		}
		if (bean.getKeyConfirmDateEnd() != null && !"".equals(bean.getKeyConfirmDateEnd())) {
			sql +=" AND a.KeyConfirmDate <= to_date('"+sdf.format(bean.getKeyConfirmDateEnd())+"235959','yyyymmddhh24miss')";
		}
		//出库时间
		if (bean.getOutDate() != null && !"".equals(bean.getOutDate())) {
			sql +=" AND a.outdate >= to_date('"+sdf.format(bean.getOutDate())+"000000','yyyymmddhh24miss')";
		}
		if (bean.getOutDateEnd() != null && !"".equals(bean.getOutDateEnd())) {
			sql +=" AND a.outdate <= to_date('"+sdf.format(bean.getOutDateEnd())+"235959','yyyymmddhh24miss')";
		}
		sql += " group by machinemodel ";
		List<Map<String,String>> list = terminalInfoDao.queryObjectsBySqlListMap(sql, params);
		List<String> excelHeader=new ArrayList<String>();
		Map<String, Object> headMap=new HashMap<String, Object>();
		excelHeader.add("MACHINEMODEL");
		excelHeader.add("MACNUM");
		headMap.put("MACHINEMODEL", "机型");
		headMap.put("MACNUM", "数量");
		return ExcelUtil.export("库存信息", list, excelHeader, headMap);
	}

	/**
	 * 收银台判断是否存在20/21活动的终端
	 */
	@Override
	public boolean queryTerminalRebateTypeInfo(TerminalInfoBean bean, String btids,UserBean user) {
		boolean flag =false;
		//String sql ="select count(*) from bill_terminalinfo t where t.status=1 and t.rebatetype in (20,21) and t.btID in ("+btids+")";
		String title="NotUseActivitySyt";
		String queryNotUseActivity = merchantInfoDao.queryNotUseActivity(title);

		if(queryNotUseActivity == null) {
			return flag;
		}
		String sql ="select count(*) from bill_terminalinfo t where t.status=1 "+
				" and t.rebatetype in ("+queryNotUseActivity +
				" ) and t.btID in ("+btids+")";
		BigDecimal d = terminalInfoDao.querysqlCounts(sql, null);
		if(d!=null&&d.intValue()>0){
			flag = true;
		}
		return flag;
	}
	/**
	 * plus判断是否存在22/23/81/82活动的终端
	 */
	@Override
	public boolean queryTerminalRebateTypeInfoPlus(TerminalInfoBean bean, String btids,UserBean user) {
		boolean flag =false;
		String title="NotUseActivityPlus";
		String queryNotUseActivity = merchantInfoDao.queryNotUseActivity(title);

		if(queryNotUseActivity == null) {
			return flag;
		}
		String sql ="select count(*) from bill_terminalinfo t where t.status=1 "+
				" and t.rebatetype in ("+queryNotUseActivity +
				" ) and t.btID in ("+btids+")";
		BigDecimal d = terminalInfoDao.querysqlCounts(sql, null);
		if(d!=null&&d.intValue()>0){
			flag = true;
		}
		return flag;
	}
	
	@Override
	public DataGridBean getListActivities() {
		DataGridBean dgb = new DataGridBean();
		String sql  = "select name,valueinteger from bill_PurConfigure where status = 1 and type=3";
		List<Map<String,String>> list = terminalInfoDao.queryObjectsBySqlListMap(sql, null);
		dgb.setRows(list);
		return dgb;
	}

	@Override
	public DataGridBean queryRegisteredMerchantActivities(TerminalInfoBean bean, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		//为总公司并且是部门机构(或为110000)---查询全部运营中心
		if("110000".equals(user.getUnNo()) || (unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0)){
		}else {
			return null;
		}
		String sql = queryRegisteredMerchantActivitiesForTerSql(bean);
		String sqlCount ="select count(*) from ("+sql+")";
		if(sql == null) {
			return null;
		}
		List<Map<String,Object>> listMap = terminalInfoDao.queryObjectsBySqlList2(sql, null, bean.getPage(), bean.getRows());
		Integer integer = terminalInfoDao.querysqlCounts2(sqlCount, null);
		dgb.setRows(listMap);
		dgb.setTotal(integer);
		return dgb;
	}
	
	@Override
	public List<Map<String, Object>> exportRegisteredMerchantActivities(TerminalInfoBean bean, UserBean userSession) {
		String sql = queryRegisteredMerchantActivitiesForTerSql(bean);
		List<Map<String, Object>> list = terminalInfoDao.queryObjectsBySqlObject(sql);
		return list;
	}

	private String queryRegisteredMerchantActivitiesForTerSql(TerminalInfoBean bean) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String sql = " select unno1 as FIRTUNNO,(select b.un_name from sys_unit b where b.unno = unno1) as FIRSTUNNONAME,rebatetype,totalCount,"+
				" case when flag = 1 then '注册'  else '出库' end COUNTTYPE " +
				" from (select unno1, rebatetype, count(1) totalCount, 1 flag from (select (select UNNO from sys_unit"+
				" where un_lvl = 1 start with unno = a.unno and status = 1 connect by NOCYCLE unno = prior upper_unit) unno1,"+
				" a.rebatetype from bill_terminalinfo a where 1=1";
		if(bean.getRebateType()!=null&&!"".equals(bean.getRebateType())){
			if(bean.getRebateType() != -1) {
				sql += " and a.rebatetype = "+bean.getRebateType()+"";
			}
		}
		
		if(bean.getKeyConfirmDate()!=null&&!"".equals(bean.getKeyConfirmDate())){
			sql +=" and a.usedconfirmdate >= to_date('"+df.format(bean.getKeyConfirmDate())+"', 'yyyy-MM-dd')" ;
		}else {
			sql +=" and a.usedconfirmdate >= trunc(sysdate-1,'dd')" ;
		}
		if(bean.getKeyConfirmDateEnd()!=null&&!"".equals(bean.getKeyConfirmDateEnd())){
			sql +=" and a.usedconfirmdate <= to_date('"+df.format(bean.getKeyConfirmDateEnd())+"', 'yyyy-MM-dd')+1" ;
		}else {
			sql +=" and a.usedconfirmdate <= trunc(sysdate,'dd')" ;
		}
		
		sql+=" and a.rebatetype is not null and a.status = 2)"+
			" group by unno1, rebatetype"+
			" union all select unno1, rebatetype, count(1) totalCount, 2 flag from (select (select UNNO"+
			" from sys_unit where un_lvl = 1 start with unno = a.unno and status = 1"+
			" connect by NOCYCLE unno = prior upper_unit) unno1,a.rebatetype from bill_terminalinfo a  where 1=1";
		
		if(bean.getRebateType()!=null&&!"".equals(bean.getRebateType())){
			if(bean.getRebateType() != -1) {
				sql += " and a.rebatetype = "+bean.getRebateType()+"";
			}
		}
		if(bean.getKeyConfirmDate()!=null&&!"".equals(bean.getKeyConfirmDate())){
			sql +=" and a.outdate >= to_date('"+df.format(bean.getKeyConfirmDate())+"', 'yyyy-MM-dd')" ;
		}else {
			sql +=" and a.outdate >= trunc(sysdate-1,'dd')" ;
		}
		if(bean.getKeyConfirmDateEnd()!=null&&!"".equals(bean.getKeyConfirmDateEnd())){
			sql +=" and a.outdate <= to_date('"+df.format(bean.getKeyConfirmDateEnd())+"', 'yyyy-MM-dd')+1" ;
		}else {
			sql +=" and a.outdate <= trunc(sysdate,'dd')" ;
		}
				
			sql += " and a.rebatetype is not null and a.status = 2) group by unno1, rebatetype) order by unno1, rebatetype";
			return sql;
	}

	@Override
	public DataGridBean queryTerminalRateFeiLv() {
		DataGridBean dgb = new DataGridBean();
		String sql  =  "SELECT distinct minfo1 FROM sys_configure WHERE groupName='SN_Rate'";
		List<Map<String,String>> list = terminalInfoDao.queryObjectsBySqlListMap(sql, null);
		dgb.setRows(list);
		return dgb;
	}

	@Override
	public DataGridBean queryTerminalRateSXUFei(String type) {
		DataGridBean dgb = new DataGridBean();
		String sql  = "select distinct secondrate from sys_configure where groupName='SN_Rate' and minfo1 ="+type;
		List<Map<String,String>> list = terminalInfoDao.queryObjectsBySqlListMap(sql, null);
		dgb.setRows(list);
		return dgb;
	}

	/**
	 * 批量修改特殊政策终端费率-20191219
	 */
	@Override
	public String editSpecialRateTerminalRateInfo(TerminalInfoBean bean, String btids,UserBean user) {
		try {
//			BigDecimal r1 = new BigDecimal(bean.getTermIDStart()+"");
//			String specialRate = r1.divide(new BigDecimal(100))+"";
//			String specialAmt = bean.getTermIDEnd();
//			String sql="update bill_terminalinfo t set t.rate="+specialRate+",t.secondrate="+specialAmt+",t.maintainuid="+user.getUserID()+
//					", t.maintaindate = sysdate,t.maintaintype='M' where t.status!=2 and t.ism35=1 and t.btid in("+btids+")";
//				
//			terminalInfoDao.executeUpdate(sql);
			
			String sql="update bill_terminalinfo t set t.maintainuid="+user.getUserID()+""+
					" , t.maintaindate = sysdate,t.maintaintype='M'";
			//修改刷卡费率
			if("1".equals(bean.getRatetype()) || "3".equals(bean.getRatetype())) {
				if(bean.getTermIDStart() == null || "".equals(bean.getTermIDStart()) ||
						bean.getTermIDEnd() == null || "".equals(bean.getTermIDEnd())) {
					return "刷卡费率或手续费填写不完整";
				}
				BigDecimal r1 = new BigDecimal(bean.getTermIDStart()+"");
				String specialRate = r1.divide(new BigDecimal(100))+"";
				String specialAmt = bean.getTermIDEnd();
				sql += " ,t.rate="+specialRate+",t.secondrate="+specialAmt+"";
			}
			//修改扫码费率
			if("2".equals(bean.getRatetype()) || "3".equals(bean.getRatetype())) {
				if(bean.getScanRate() == null || "".equals(bean.getScanRate()) ||
						bean.getScanRateUp() == null || "".equals(bean.getScanRateUp())) {
					return "扫码费率填写不完整";
				}
				BigDecimal r1 = new BigDecimal(bean.getScanRateUp()+"");
				BigDecimal r2 = new BigDecimal(bean.getScanRate()+"");
				String scanRateUp = r1.divide(new BigDecimal(100))+"";
				String scanRate = r2.divide(new BigDecimal(100))+"";
				sql += ",t.scanRateUp="+scanRateUp+",t.scanRate="+scanRate+"";
			}
			//修改花呗费率
			if("1".equals(bean.getIshuabei())) {
				if(bean.getHuabeiRate() == null || "".equals(bean.getHuabeiRate())) {
					return "花呗费率填写不完整";
				}
				BigDecimal r1 = new BigDecimal(bean.getHuabeiRate()+"");
				String huabeiRate = r1.divide(new BigDecimal(100))+"";
				sql += " ,t.huabeiRate="+huabeiRate+"";
			}
			//修改押金
			if("1".equals(bean.getIsdepositamt())) {
				if(bean.getDepositAmt() == null || "".equals(bean.getDepositAmt())) {
					return "押金金额填写不完整";
				}
				sql += " ,t.depositamt="+bean.getDepositAmt()+"";
			}
			sql += " where t.status!=2 and t.ism35=1 and t.btid in("+btids+")";
			terminalInfoDao.executeUpdate(sql);
		} catch (Exception e) {
			return "修改失败";
		}
		return "修改成功";
	}
	
	/**
	 * 判断修改sn费率的用户否是特殊机构-20191219
	 */
	@Override
	public String judgeIsSpecialRateUnno(String isActivety,String btids,UserBean user) {
		
		//判断是否是总公司下自定义政策活动20200403ztt
		String sql0 = " SELECT t.* FROM  bill_specialratecondition t"
				+" where t.unno = '110000' and t.rebatetype = '"+isActivety+"'";
		List<Map<String,Object>> list0 = terminalInfoDao.executeSql2(sql0, null);
		if(null ==list0 || "".equals(list0) || list0.size()<1) {
		}else {
			return list0.get(0).get("UNNO").toString() +","+ list0.get(0).get("REBATETYPE").toString();
		}
		
		String sql  =  " SELECT t.* FROM (SELECT unno FROM  sys_unit b"
				+" where 1 = 1 and b.un_lvl = 1 start with b.unno = '"+user.getUnNo()+"'"
				+" connect by b.unno = prior b.upper_unit) a,bill_specialratecondition t "
				+" where a.unno = t.unno";
		
		List<Map<String,Object>> list = terminalInfoDao.executeSql2(sql, null);
		if(null ==list || "".equals(list) || list.size()<1) {
			return null;
		}
		
		//特殊机构自定义费率---添加活动限制20200311
		String specialLimit_temp = null;
		for (Map<String, Object> map : list) {
			if(map.get("REBATETYPE")==null) {
				//说明没有限制机构下活动，整个中心下的所有设备都是使用这套限制费率
				String specialRate1 = map.get("SPECIALRATE1").toString();
				String specialRate2 = map.get("SPECIALRATE2").toString();
				String specialAmt1 = map.get("SPECIALAMT1").toString();
				String specialAmt2 = map.get("SPECIALAMT2").toString();
				//specialLimit_temp = specialRate1+","+specialRate2+","+specialAmt1+","+specialAmt2;
				String unno = map.get("UNNO").toString();
				specialLimit_temp = unno + ","+ isActivety;
			}else if(map.get("REBATETYPE").equals(isActivety)){
				String specialRate1 = map.get("SPECIALRATE1").toString();
				String specialRate2 = map.get("SPECIALRATE2").toString();
				String specialAmt1 = map.get("SPECIALAMT1").toString();
				String specialAmt2 = map.get("SPECIALAMT2").toString();
				//return specialRate1+","+specialRate2+","+specialAmt1+","+specialAmt2;
				String unno = map.get("UNNO").toString();
				String rebatetype = map.get("REBATETYPE").toString();
				return unno + ","+ rebatetype;
			}
		}
		if(specialLimit_temp != null) {
			//这个不为空说明此中心下设置有不限活动的特殊费率，且修改设备没有匹配到相应活动限制费率
			return specialLimit_temp;
		}
		return null;//到这里就说明该中心下有配置特殊费率，但是没有配置此终端活动的特殊费率所以让他走正常
		
		//String specialRate1 = list.get(0).get("SPECIALRATE1").toString();
		//String specialRate2 = list.get(0).get("SPECIALRATE2").toString();
		//String specialAmt1 = list.get(0).get("SPECIALAMT1").toString();
		//String specialAmt2 = list.get(0).get("SPECIALAMT2").toString();
		
		//return specialRate1+","+specialRate2+","+specialAmt1+","+specialAmt2;
	}

	@Override
	public String judgeIsSpecialRateTer(TerminalInfoBean bean, String btids, UserBean user) {
		
		//添加总公司名下特殊活动的判断20200403ztt
		String sql0 = "SELECT count(1) FROM  bill_specialratecondition a, "
				+" (SELECT t.rebatetype FROM  bill_terminalinfo t"
				+" where t.btid in ("+btids+"))b"
				+" where a.rebatetype = b.rebatetype"
				+" and a.unno = '110000'";
		
		String sql1 = " SELECT count(1) FROM  bill_specialratecondition a,(SELECT t.rebatetype,(SELECT u.unno FROM  sys_unit u" +
				" where u.un_lvl = 1"+
				" start with u.unno =t.unno connect by prior u.upper_unit = u.unno) unno FROM  bill_terminalinfo t" +
				" where t.btid in ("+btids+"))b"+
				" where a.rebatetype = b.rebatetype and a.unno = b.unno";
		String sql2 = " SELECT count(1) FROM  bill_terminalinfo t where t.btid in ("+btids+")";
		
		Integer i0 = terminalInfoDao.querysqlCounts2(sql0, null);//总公司下自定义政策活动
		Integer i1 = terminalInfoDao.querysqlCounts2(sql1, null);
		Integer i2 = terminalInfoDao.querysqlCounts2(sql2, null);
		
		if(i0>0) {
			if(i0<i2) {
				return " 存在不满足自定义活动的设备";
			}
			return null;
		}
		
		if(i1<i2) {
			return " 存在不满足自定义活动的设备";
		}
		return null;
	}

	@Override
	public Map querySpecialRateDetail(String limitunno, String limitrebatetype, UserBean userSession) {
		String sql = "SELECT t.*,"+
				" decode (nvl(t.depositamt,'0'),'0',0,1) isdepositamt,"+
				" decode (nvl(t.huabeirate1,0),0,0,1) ishuabei"+
				" FROM bill_specialratecondition t where 1=1 " + 
				" and t.unno = '"+limitunno+"' and t.rebatetype = '"+limitrebatetype+"'";
		List<Map<String,Object>> list = terminalInfoDao.queryObjectsBySqlListMap2(sql, null);
		if (list.size() == 1) {
			return list.get(0);
		}
		return new HashMap();
	}

	@Override
	public DataGridBean querySpecialDepoist(String limitunno, String limitrebatetype) {
		DataGridBean dgd = new DataGridBean();
		String sql = " SELECT a.depositamt FROM  bill_specialratecondition a"
				+" where 1=1"
				+" and a.unno = '"+limitunno+"' and a.rebatetype = '"+limitrebatetype+"'";
		List<Map<String,Object>> list = terminalInfoDao.queryObjectsBySqlListMap2(sql, null);
		List<Map> l = new ArrayList();
		if(list.size() == 1) {
			String str = list.get(0).get("DEPOSITAMT").toString();
			String[] split = str.split(",");
			for (String string : split) {
				Map a = new HashMap();
				a.put("depositAmt", string);
				l.add(a);
			}
		}
		dgd.setRows(l);
		return dgd;
	}

	@Override
	public List<Map<String, Object>> queryTermIsUse(String sns){
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		StringBuffer sb=new StringBuffer(256);
		JSONArray array = JSON.parseArray(sns);
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject=array.getJSONObject(i);
			sb.append("'").append(jsonObject.getString("sn")).append("'");
			if(i<array.size()-1){
				sb.append(",");
			}
		}
		String sql="select t.sn,t.status from bill_terminalinfo t where t.sn in ("+sb.toString()+")";
		listMap = terminalInfoDao.queryObjectsBySqlListMap2(sql, null);
		return listMap;
	}
}


