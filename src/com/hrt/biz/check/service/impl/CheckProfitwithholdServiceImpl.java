package com.hrt.biz.check.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.check.dao.CheckProfitwithholdDao;
import com.hrt.biz.check.entity.model.CheckProfitwithholdModel;
import com.hrt.biz.check.entity.pagebean.CheckProfitwithholdBean;
import com.hrt.biz.check.service.CheckProfitwithholdService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class CheckProfitwithholdServiceImpl implements CheckProfitwithholdService{

	private CheckProfitwithholdDao checkProfitwithholdDao;
	private IUnitDao unitDao;
	private IMerchantInfoService merchantInfoService;
	
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

	public CheckProfitwithholdDao getCheckProfitwithholdDao() {
		return checkProfitwithholdDao;
	}

	public void setCheckProfitwithholdDao(CheckProfitwithholdDao checkProfitwithholdDao) {
		this.checkProfitwithholdDao = checkProfitwithholdDao;
	}

	@Override
	public DataGridBean queryWithholdInfo(CheckProfitwithholdBean bean, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		String sql = getProfitWithholdSql(bean,user);
		
		String countSql = "select count(1) from ("+sql+")";
		List<Map<String, String>> executeSql = checkProfitwithholdDao.executeSql(sql);
		Integer querysqlCounts2 = checkProfitwithholdDao.querysqlCounts2(countSql, null);
		dgb.setRows(executeSql);
		dgb.setTotal(querysqlCounts2);
		return dgb;
	}
	

	@Override
	public List<Map<String, Object>> profitWithholdExport(CheckProfitwithholdBean bean,
			UserBean user) {
		String sql = getProfitWithholdSql(bean,user);
		List<Map<String, Object>> list = checkProfitwithholdDao.executeSql2(sql, null);
		return formatToData(user,list);
	}

	private List<Map<String, Object>> formatToData(UserBean userSession,List<Map<String, Object>> lst){
		List list = new ArrayList();
		for (Map<String, Object> map : lst) {
			if(map.get("DEDUCTIONSOURCE")==null) {
				map.put("DEDUCTIONSOURCE", "一代分润");
			}
			if("2".equals(map.get("APPROVESTATUS"))) {
				map.put("APPROVESTATUS", "已驳回");
			}else if("3".equals(map.get("APPROVESTATUS"))){
				map.put("APPROVESTATUS", "已确认");
			}else if("1".equals(map.get("APPROVESTATUS")) && "2".equals(map.get("FLAG").toString())){
				map.put("APPROVESTATUS", "待确认");
			}else if("1".equals(map.get("APPROVESTATUS")) && "1".equals(map.get("FLAG").toString())) {
				map.put("APPROVESTATUS", "已过期未确认");
			}
			list.add(map);
		}
		return list;
	}
	public String getProfitWithholdSql(CheckProfitwithholdBean bean, UserBean user) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		//flag 1:过期未确认 2：其他       isyidai  1:一代  2其他
		String sql = "select t.*,u.un_name executingUnnoName,"+
				" case when t.approvestatus = 1 and t.effectivedate<'"+format.format(date).substring(0, 6)+"'"+
				" then 1 else 2 end as flag,"+
				" case when 2 = "+unitModel.getUnLvl()+""+
				" then 1 else 2 end as isyidai,"+
				" (SELECT m.un_name FROM  sys_unit m"+
				" where m.unno = t.applicantunno ) yunyingzhongxinName"+
		        " from check_profitWithhold t,sys_unit u where t.executingunno = u.unno";
		
		//一代
		if(bean.getExecutingUnno()!=null&&!"".equals(bean.getExecutingUnno())) {
			sql += " and t.executingUnno = '"+bean.getExecutingUnno()+"'";
		}
		//一代名称
		if(bean.getExecutingUnnoName()!=null&&!"".equals(bean.getExecutingUnnoName())) {
			sql += " and u.un_name = '"+bean.getExecutingUnnoName()+"'";
		}
		//归属中心机构号
		if(bean.getApplicantUnno()!=null&&!"".equals(bean.getApplicantUnno())) {
			sql += " and t.applicantUnno = '"+bean.getApplicantUnno()+"'";
		}
		//生效月
		if(bean.getTxnMoth1()!=null&&!"".equals(bean.getTxnMoth1())) {
			sql +=" and t.effectiveDate >= '"+bean.getTxnMoth1()+"'";
		}
		if(bean.getTxnMoth2()!=null&&!"".equals(bean.getTxnMoth2())&&bean.getTxnMoth2().length()<9) {
			sql += " and t.effectiveDate <= '"+bean.getTxnMoth2()+"'";
		}
		//申请日期
		if(bean.getTxnDay()!=null&&!"".equals(bean.getTxnDay())){
			//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			sql += " AND  trunc(MAINTAINDATE) >= to_date('"+bean.getTxnDay()+"','yyyy-MM-dd') ";
		}
		if(bean.getTxnDay1()!=null&&!"".equals(bean.getTxnDay1())){
			//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			sql += " AND  trunc(MAINTAINDATE) <= to_date('"+bean.getTxnDay1()+"','yyyy-MM-dd') ";
		}
		//状态查询
		if(bean.getApproveStatus()!=null&&!"".equals(bean.getApproveStatus())) {
			if("4".equals(bean.getApproveStatus())) {
				sql += " and t.approveStatus = '1' and t.effectiveDate<"+format.format(date).substring(0, 6)+"";
			}else {
				sql += " and t.approveStatus = '"+bean.getApproveStatus()+"'";
			}
		}
		//一代查看自己，运营中心查看自己名下的，结算查看所有
		if( unitModel.getUnLvl() == 0){
		}
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else if(unitModel.getUnLvl() == 1){//运营中心查看运营中心下所有
			sql += " and t.applicantUnno = '"+unitModel.getUnNo()+"'";
		}else if(unitModel.getUnLvl() == 2){//一代查看自己所有
			sql += " AND t.executingUnno = '"+unitModel.getUnNo()+"'" ;
		}
		
		return sql;
	}

	@Override
	public void addprofitWithholdInfo(CheckProfitwithholdBean bean, UserBean user) {
		CheckProfitwithholdModel model = new CheckProfitwithholdModel();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String substring = format.format(date).substring(0, 6);
//		model.setExecutingUnno(bean.getExecutingUnno());
//		model.setWithholdingAmount(bean.getWithholdingAmount());
//		model.setApplicantUnno(user.getUnNo());
//		model.setEffectiveDate(substring);
//		model.setCby(user.getUserName());
//		model.setApproveStatus("1");
//		model.setMaintainDate(date);
//		model.setMaintainType("A");
		
		String sql ="insert into  check_profitwithhold t"+
				" (id,EXECUTINGUNNO,WITHHOLDINGAMOUNT,APPLICANTUNNO,EFFECTIVEDATE,CBY,APPROVESTATUS,MAINTAINTYPE,MAINTAINDATE)"+
				" values (S_check_profitWithhold.Nextval,'"+bean.getExecutingUnno()+"','"+bean.getWithholdingAmount()+"','"+user.getUnNo()+"','"+substring+"','"+user.getUserName()+"','1','A',sysdate)";
		checkProfitwithholdDao.executeUpdate(sql);
	}

	@Override
	public String judgeProfitWithholdInfo(CheckProfitwithholdBean bean, UserBean user) {
		//判断是否被添加的机构属于一代
		String sql = " SELECT u.* FROM  sys_unit u where u.un_lvl = 2 and u.unno = '"+bean.getExecutingUnno()+"'";
		 List<Map<String, String>> list = checkProfitwithholdDao.executeSql(sql);
		if(list==null || list.size()<1) {
			return "代理非法，请重新添加";
		}
		//判断被添加机构是否属于登陆机构下一代
		String sql1 = " SELECT a.* FROM (SELECT * FROM  sys_unit u"+
				" where u.un_lvl = 2 start with u.unno = '"+user.getUnNo()+"'"+
				" connect by prior u.unno = u.upper_unit)a"+
				" where a.unno = '"+bean.getExecutingUnno()+"'";
		List<Map<String,String>> list1 = checkProfitwithholdDao.executeSql(sql1);
		if(list1==null || list1.size()<1) {
			return "提交代理不属本中心，请重新添加";
		}
		//判断一个自然月内是否有待确认与已确认数据
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String substring = format.format(date).substring(0, 6);
		String sql2 = " SELECT * FROM  check_profitwithhold t"+
				" where t.executingunno = '"+bean.getExecutingUnno()+"'"+
				" and t.approvestatus in (1,3) and t.effectivedate = '"+substring+"'";
		List<Map<String,String>> list2 = checkProfitwithholdDao.executeSql(sql2);
		if(list != null && list2.size()>0) {
			return "本月已提交数据，不允许重复添加";
		}
		return "可添加";
	}

	@Override
	public void yidaiProfitWithholdEdit(CheckProfitwithholdBean bean, UserBean user) {
		String sql = " update check_profitwithhold t set t.approvestatus = '"+bean.getApproveStatus()+"',t.agreeperson = '"+user.getUserName()+"'"+
				" ,t.agreedate = sysdate where t.id = "+bean.getId()+"";
		checkProfitwithholdDao.executeUpdate(sql);
	}

}
