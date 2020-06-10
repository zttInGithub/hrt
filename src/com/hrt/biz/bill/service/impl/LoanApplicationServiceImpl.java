package com.hrt.biz.bill.service.impl;


import java.math.BigDecimal;
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

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.bill.dao.LoanApplicationDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.entity.model.LoanApplicationModel;
import com.hrt.biz.bill.entity.pagebean.AgentSalesBean;
import com.hrt.biz.bill.entity.pagebean.LoanApplicationBean;
import com.hrt.biz.bill.service.LoanApplicationService;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;
import com.hrt.util.HttpXmlClient;

public class LoanApplicationServiceImpl implements LoanApplicationService {

	private LoanApplicationDao loanApplicationDao;
	private String loanUrl;
	
	private static final Log log = LogFactory.getLog(LoanApplicationServiceImpl.class);
	

	public String getLoanUrl() {
		return loanUrl;
	}


	public void setLoanUrl(String loanUrl) {
		this.loanUrl = loanUrl;
	}


	public LoanApplicationDao getLoanApplicationDao() {
		return loanApplicationDao;
	}


	public void setLoanApplicationDao(LoanApplicationDao loanApplicationDao) {
		this.loanApplicationDao = loanApplicationDao;
	}


	/**
	 * 贷款申请
	 */
	@Override
	public Integer saveLoanApply(LoanApplicationBean loanApplicationBean, UserBean user){
		LoanApplicationModel m = loanApplicationDao.queryObjectByHql("from LoanApplicationModel where unno=? and approveStatus!='K'", new Object[]{user.getUnNo()});
		if(m!=null){//已申请
			return 0;
		}
		LoanApplicationModel model = new LoanApplicationModel();
		BeanUtils.copyProperties(loanApplicationBean, model);
		model.setUnno(user.getUnNo());
		model.setCby(user.getLoginName());
		model.setCdate(new Date());
		model.setApproveStatus("A");
		loanApplicationDao.saveObject(model);
		//推送小贷
		JSONObject json = new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("certificateType", "1");
		map1.put("certificateId", model.getLegalNum());
		map1.put("userName", model.getApplicant());
		map1.put("celPhone", model.getPhone());
		map1.put("telPhone", model.getPhone());
		map.put("custBasicInfo", map1);
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("productId", "BusinessLoan");
		map2.put("capital", model.getApplyQuota().toString());
//		map2.put("purpose", "生产经营");
		//王磊20180807修改
		map2.put("loanPurpose", "生产经营");
		map2.put("periods", "0");
		map.put("custLoanDetail", map2);
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("merId", model.getUnno());
		map3.put("merSource", "HRT");
		map.put("agentMerInfo", map3);
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("merType", "1");
		map4.put("merId", model.getUnno());
		map.put("custLoanRelationship", map4);
		log.info("贷款通知请求:url="+loanUrl+";json="+json.toJSONString(map));
		String ss = HttpXmlClient.postForJson(loanUrl+"/txnservice/loan/preApply", json.toJSONString(map));
		log.info("贷款通知返回:url="+loanUrl+";msg="+ss);
		return 1;
	}


	@Override
	public Integer queryLoanApply(UserBean user) {
		String sql = "select count(1) from bill_LoanApplication where approveStatus!='K' and unno='"+user.getUnNo()+"'";
		return loanApplicationDao.querysqlCounts2(sql, null);
	}
	
	@Override
	public Integer queryLoanQuota(UserBean user) {
		//记录当前意愿机构
		loanApplicationDao.executeUpdate("insert into sys_login_log (login_type,user_id,login_name,pwd,login_message,status,logintime) values ('D','"+user.getUserID()+"','"+user.getLoginName()+"','D','贷款意愿记录',1,sysdate)");
		if("982125".equals(user.getUnNo())||"992107".equals(user.getUnNo()))return 0;//拦截易宝付和银收宝
		String sql = "select sum(a.aa+b.aa+b.ab)*10/3 amt from "
				+ "(select nvl(sum(sumamt),0)*0.0001 aa from  check_UnnoTxnSum where unno='"+user.getUnNo()+"' and txnday >= to_char(sysdate-90,'yyyymmdd')) a,"
				+ "(select nvl(sum(txnamount),0)*0.0004 aa,nvl(sum(txncount),0) ab from  check_UnitDealData where unno='"+user.getUnNo()+"' and txnday >= to_char(sysdate-90,'yyyymmdd')) b";
		BigDecimal counts = loanApplicationDao.querysqlCounts(sql, null);
		if(counts==null)return 0;
		int amt = counts.intValue();
		if(amt<10000){
			return 0;
		}else{
			amt = amt/10000;//取整
			return amt>300?300:amt;//最高3000000
		}
	}
	
	@Override
	public DataGridBean queryLoanList(LoanApplicationBean loanApplicationBean, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		String hql = "select a.*,c.un_name from bill_loanapplication a,sys_unit b,sys_unit c where a.unno=b.unno and b.upper_unit=c.unno ";
		String hqlCount = "select count(1) from bill_loanapplication a,sys_unit b,sys_unit c where a.unno=b.unno and b.upper_unit=c.unno ";
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(loanApplicationBean.getCdate()!=null&&!"".equals(loanApplicationBean.getCdate())){
			hql += " and a.cdate >= to_date('"+sdf.format(loanApplicationBean.getCdate())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and cdate <= to_date('"+sdf.format(loanApplicationBean.getCdate())+" 23:59:59','yyyy-mm-dd hh24:mi:ss')";
			hqlCount += " and a.cdate >= to_date('"+sdf.format(loanApplicationBean.getCdate())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and cdate <= to_date('"+sdf.format(loanApplicationBean.getCdate())+" 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		}
		if(loanApplicationBean.getApplicant()!=null&&!"".equals(loanApplicationBean.getApplicant())){
			hql += " and a.applicant = :applicant";
			hqlCount += " and a.applicant = :applicant";
			map.put("applicant", loanApplicationBean.getApplicant());
		}
		if(loanApplicationBean.getUnno()!=null&&!"".equals(loanApplicationBean.getUnno())){
			hql += " and a.unno = :unno";
			hqlCount += " and a.unno = :unno";
			map.put("unno", loanApplicationBean.getUnno());
		}
		if(loanApplicationBean.getApproveStatus()!=null&&!"".equals(loanApplicationBean.getApproveStatus())){
			hql += " and a.approveStatus = :approveStatus";
			hqlCount += " and a.approveStatus = :approveStatus";
			map.put("approveStatus", loanApplicationBean.getApproveStatus());
		}
		List<Map<String,String>> list = loanApplicationDao.queryObjectsBySqlList(hql, map, loanApplicationBean.getPage(), loanApplicationBean.getRows());
		Integer count = loanApplicationDao.querysqlCounts2(hqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}
	
	@Override
	public HSSFWorkbook exportLoan(LoanApplicationBean loanApplicationBean) {
		String sql=" SELECT * FROM BILL_loanApplication t WHERE 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(loanApplicationBean.getCdate()!=null&&!"".equals(loanApplicationBean.getCdate())){
			sql += " and cdate >= to_date('"+sdf.format(loanApplicationBean.getCdate())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and cdate <= to_date('"+sdf.format(loanApplicationBean.getCdate())+" 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		}
		if(loanApplicationBean.getApplicant()!=null&&!"".equals(loanApplicationBean.getApplicant())){
			sql += " and applicant = :applicant";
			map.put("applicant", loanApplicationBean.getApplicant());
		}
		if(loanApplicationBean.getUnno()!=null&&!"".equals(loanApplicationBean.getUnno())){
			sql += " and unno = :unno";
			map.put("unno", loanApplicationBean.getUnno());
		}
		if(loanApplicationBean.getApproveStatus()!=null&&!"".equals(loanApplicationBean.getApproveStatus())){
			sql += " and approveStatus = :approveStatus";
			map.put("approveStatus", loanApplicationBean.getApproveStatus());
		}
		List<Map<String, Object>> data=loanApplicationDao.queryObjectsBySqlListMap2(sql,map);
		for (Map<String, Object> map2 : data) {
			Integer i = ((BigDecimal) map2.get("MANAGETYPE")).intValue();
			Integer j = ((BigDecimal) map2.get("MANAGEMODE")).intValue();
			String m = ((Character) map2.get("APPROVESTATUS")).toString();
			if (i == 1){
				map2.put("MANAGETYPE", "个人经营");
			}else if(i == 2){
				map2.put("MANAGETYPE", "合伙经营");
			}else if(i == 3){
				map2.put("MANAGETYPE", "个体工商经营");
			}else if(i == 4){
				map2.put("MANAGETYPE", "企业经营");
			}else if(i == 5){
				map2.put("MANAGETYPE", "其他方式");
			}
			if (j == 1){
				map2.put("MANAGEMODE", "电销");
			}else if(j == 2){
				map2.put("MANAGEMODE", "地推");
			}else if(j == 3){
				map2.put("MANAGEMODE", "代理");
			}else if(j == 4){
				map2.put("MANAGEMODE", "其他");
			}
			if("Y".equals(m)){
				map2.put("APPROVESTATUS", "已分配");
			}else{
				map2.put("APPROVESTATUS", "未分配");
			}
		}
		List<String> excelHeader=new ArrayList<String>();
		Map<String, Object> headMap=new HashMap<String, Object>();
		excelHeader.add("UNNO");
		excelHeader.add("MANAGETYPE");
		excelHeader.add("MANAGEMODE");
		excelHeader.add("STAFFNUM");
		excelHeader.add("MANAGEADDR");
		excelHeader.add("PROFITAMT");
		excelHeader.add("APPLICANT");
		excelHeader.add("LEGALNUM");
		excelHeader.add("APPLYQUOTA");
		excelHeader.add("PHONE");
		excelHeader.add("BUSNAME");
		excelHeader.add("CDATE");
		excelHeader.add("APPROVESTATUS");
		headMap.put("UNNO", "机构编号");
		headMap.put("MANAGETYPE", "经营方式");
		headMap.put("MANAGEMODE", "主营方式");
		headMap.put("STAFFNUM", "员工数量");
		headMap.put("MANAGEADDR", "经营地址");
		headMap.put("PROFITAMT", "分润金额");
		headMap.put("APPLICANT", "申请人");
		headMap.put("LEGALNUM", "身份证");
		headMap.put("APPLYQUOTA", "申请额度");
		headMap.put("PHONE", "联系电话");
		headMap.put("BUSNAME", "信贷员姓名");
		headMap.put("CDATE", "申请时间");
		headMap.put("APPROVESTATUS", "状态");
		return ExcelUtil.export("贷款申请", data, excelHeader, headMap);
	}
	
	/**
	 * 贷款申请
	 */
	@Override
	public Integer saveBusName(LoanApplicationBean loanApplicationBean, UserBean user){
		LoanApplicationModel m = loanApplicationDao.queryObjectByHql("from LoanApplicationModel where approveStatus!='Y' and blaid=?", new Object[]{loanApplicationBean.getBlaid()});
		if(m==null){//已分配
			return 0;
		}
		m.setApproveDate(new Date());
		m.setBusName(loanApplicationBean.getBusName());
		m.setApproveStatus("Y");
		m.setApprover(user.getLoginName());
		loanApplicationDao.updateObject(m);
		return 1;
	}


	@Override
	public DataGridBean queryLoanApplication(LoanApplicationBean loanApplicationBean, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		String hql = "select a.*,c.un_name from sys_login_log a,sys_unit b,sys_unit c where substr(a.login_name,0,6)=b.unno and b.upper_unit=c.unno and a.login_type='D' ";
		String hqlCount = "select count(1) from sys_login_log a,sys_unit b,sys_unit c where substr(a.login_name,0,6)=b.unno and b.upper_unit=c.unno and a.login_type='D' ";
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(loanApplicationBean.getCdate()!=null&&!"".equals(loanApplicationBean.getCdate())){
			hql += " and a.logintime >= to_date('"+sdf.format(loanApplicationBean.getCdate())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.logintime <= to_date('"+sdf.format(loanApplicationBean.getCdate())+" 23:59:59','yyyy-mm-dd hh24:mi:ss')";
			hqlCount += " and a.logintime >= to_date('"+sdf.format(loanApplicationBean.getCdate())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.logintime <= to_date('"+sdf.format(loanApplicationBean.getCdate())+" 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		}
		hql += " order by a.slid desc";
		List<Map<String,String>> list = loanApplicationDao.queryObjectsBySqlList(hql, map, loanApplicationBean.getPage(), loanApplicationBean.getRows());
		Integer count = loanApplicationDao.querysqlCounts2(hqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}


	@Override
	public HSSFWorkbook exportLoanApplication(LoanApplicationBean loanApplicationBean) {
		String sql="select a.*,c.un_name from sys_login_log a,sys_unit b,sys_unit c where substr(a.login_name,0,6)=b.unno and b.upper_unit=c.unno and a.login_type='D' ";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(loanApplicationBean.getCdate()!=null&&!"".equals(loanApplicationBean.getCdate())){
			sql += " and a.logintime >= to_date('"+sdf.format(loanApplicationBean.getCdate())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and a.logintime <= to_date('"+sdf.format(loanApplicationBean.getCdate())+" 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		}
		sql += " order by a.slid desc";
		List<Map<String, Object>> data=loanApplicationDao.queryObjectsBySqlListMap2(sql,null);
		
		List<String> excelHeader=new ArrayList<String>();
		Map<String, Object> headMap=new HashMap<String, Object>();
		excelHeader.add("USER_ID");
		excelHeader.add("LOGIN_NAME");
		excelHeader.add("UN_NAME");
		excelHeader.add("LOGINTIME");
		
		headMap.put("USER_ID", "用户ID");
		headMap.put("LOGIN_NAME", "机构号（去除后两位99）");
		headMap.put("UN_NAME", "运营中心");
		headMap.put("LOGINTIME", "记录时间");
		return ExcelUtil.export("贷款申请意愿", data, excelHeader, headMap);
	}
}
