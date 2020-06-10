package com.hrt.biz.check.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;

import com.ctc.wstx.util.StringUtil;
import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.check.dao.CheckWechantTxnDetailDao;
import com.hrt.biz.check.entity.model.CheckWechatTxnDetailModel;
import com.hrt.biz.check.entity.pagebean.CheckWechatTxnDetailBean;
import com.hrt.biz.check.service.CheckWechantTxnDetailService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;
import com.hrt.util.JxlOutExcelUtil;

public class CheckWechantTxnDetailServiceImpl implements CheckWechantTxnDetailService {

	private CheckWechantTxnDetailDao checkWechantTxnDetailDao;
	private IUnitDao unitDao;
	private IMerchantInfoService merchantInfoService; 
	private IMerchantInfoDao merchantInfoDao;
	private IAgentSalesDao agentSalesDao;
	
	private static final Log log = LogFactory.getLog(CheckWechantTxnDetailServiceImpl.class);
	
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

	public CheckWechantTxnDetailDao getCheckWechantTxnDetailDao() {
		return checkWechantTxnDetailDao;
	}

	public void setCheckWechantTxnDetailDao(
			CheckWechantTxnDetailDao checkWechantTxnDetailDao) {
		this.checkWechantTxnDetailDao = checkWechantTxnDetailDao;
	}

	/**
	 * 查询微信交易记录
	 */
	public DataGridBean queryWechantTxnDetailInfo(CheckWechatTxnDetailBean checkWechatTxnDetailBean, UserBean user){
		
		String sql="select w.*,m.isM35,m.unno,m.rname,f.FIINFO1,(select mer_orderid from check_wechattxndetail ww where w.oripwid=ww.pwid) oldorderid from check_wechattxndetail w  ,bill_merchantinfo m, sys_bankfi f   where w.mid=m.mid and w.fiid=f.fiid ";
		String sqlCount="select count(1) from check_wechattxndetail w ,bill_merchantinfo m, sys_bankfi f  where w.mid=m.mid and w.fiid=f.fiid ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		boolean date_flag = false ;
		boolean unno_flag = true ;
		//权限
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if(user.getUseLvl()==3){
			//	sql+=" and b.MID ='"+userBean.getLoginName()+"' ";
			//	sqlCount+= " and b.MID ='"+userBean.getLoginName()+"' ";
				sql+=" and w.MID in (select MID from  bill_MerchantInfo where  " +
				"PARENTMID= '"+user.getLoginName()+"' or MID ='"+user.getLoginName()+"')  ";
				sqlCount+=" and w.MID in (select MID from  bill_MerchantInfo where  " +
				"PARENTMID= '"+user.getLoginName()+"' or MID ='"+user.getLoginName()+"')  ";
		}
		else{
			if("110000".equals(user.getUnNo())){
			}else if(user.getUseLvl()==1||user.getUseLvl()==2) {//报单员业务员只能看到自己签约的机构下的交易
				sql += " and m.unno in (SELECT b1.unno FROM bill_agentsalesinfo a1, bill_agentunitinfo b1 WHERE a1.busid = b1.signuserid AND a1.user_id = "+user.getUserID()+") ";
				sqlCount += " and m.unno in (SELECT b1.unno FROM bill_agentsalesinfo a1, bill_agentunitinfo b1 WHERE a1.busid = b1.signuserid AND a1.user_id = "+user.getUserID()+") ";
			}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else{
				String agHql ="select ag from AgentSalesModel ag where ag.maintainType!='D' and ag.userID="+user.getUserID();
				List<AgentSalesModel> ifAgList = agentSalesDao.queryObjectsByHqlList(agHql);
				if(ifAgList.size()>0){
					//业务员 user.getUserID()
					sql+=" and m.busid="+ifAgList.get(0).getBusid();
					sqlCount+=" and m.busid="+ifAgList.get(0).getBusid();
				}else{
					String unno = user.getUnNo();
					if (checkWechatTxnDetailBean.getUnno() != null&&!"".equals(checkWechatTxnDetailBean.getUnno())) {
						unno = checkWechatTxnDetailBean.getUnno();
						unno_flag = false;
					}
					//代理
					String childUnno=merchantInfoService.queryUnitUnnoUtil(unno);
					sql+=" and m.unno IN ("+childUnno+" )";
					sqlCount+=" and m.unno IN ("+childUnno+" )";
				}
			}
		}
		if("8".equals(checkWechatTxnDetailBean.getTrantype())){
			sql +=" and w.trantype ='8' ";
			sqlCount +=" and w.trantype ='8' ";
		}else{
			//1
			sql +=" and (w.trantype !='8' or w.trantype is null) ";
			sqlCount +=" and (w.trantype !='8' or w.trantype is null) ";
		}
//		if("0".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 !=1 ";
//			sqlCount +=" and m.isM35 !=1 ";
//		}else if("1".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 in (1,6) ";
//			sqlCount +=" and m.isM35 in (1,6) ";
//		}
		if (unno_flag&&checkWechatTxnDetailBean.getUnno() != null&&!"".equals(checkWechatTxnDetailBean.getUnno())) {
			sql +=" and m.unno in ("+merchantInfoService.queryUnitUnnoUtil(checkWechatTxnDetailBean.getUnno())+") ";
			sqlCount +=" and m.unno in ("+merchantInfoService.queryUnitUnnoUtil(checkWechatTxnDetailBean.getUnno())+")  ";
			//map.put("unno", merchantInfoService.queryUnitUnnoUtil(checkWechatTxnDetailBean.getUnno()));
			date_flag = true ;
		}
		if (checkWechatTxnDetailBean.getMid() != null&&!"".equals(checkWechatTxnDetailBean.getMid())) {
			sql +=" and w.mid = :mid ";
			sqlCount +=" and w.mid = :mid ";
			map.put("mid", checkWechatTxnDetailBean.getMid());
			date_flag = true ;
		}
		if (checkWechatTxnDetailBean.getMer_orderId() != null&&!"".equals(checkWechatTxnDetailBean.getMer_orderId())) {
			sql +=" and w.mer_orderId =:mer_orderId ";
			sqlCount +=" and w.mer_orderId =:mer_orderId ";
			map.put("mer_orderId", checkWechatTxnDetailBean.getMer_orderId());
			date_flag = true ;
		}
		if (checkWechatTxnDetailBean.getStatus() != null&&!"".equals(checkWechatTxnDetailBean.getStatus())) {
			sql +=" and w.status =:status ";
			sqlCount +=" and w.status =:status ";
			map.put("status", checkWechatTxnDetailBean.getStatus());
			date_flag = true ;
		}
		if (checkWechatTxnDetailBean.getFiinfo2()!=null&&!"".equals(checkWechatTxnDetailBean.getFiinfo2())) {
			sql += " and f.fiinfo2 like :fiinfo2";
			sqlCount += " and f.fiinfo2 like :fiinfo2";
			map.put("fiinfo2", "%"+checkWechatTxnDetailBean.getFiinfo2()+"%");
			date_flag = true;
		}
		if (checkWechatTxnDetailBean.getIsM35()!=null) {
			if (checkWechatTxnDetailBean.getIsM35()==6) {
				sql += " and m.isM35 =:isM35";
				sqlCount += " and m.isM35 =:isM35";
				map.put("isM35", checkWechatTxnDetailBean.getIsM35());
			}else {
				sql += " and m.isM35 !=:isM35";
				sqlCount += " and m.isM35 !=:isM35";
				map.put("isM35", 6);
			}
			
			date_flag = true;
		}
//		if(checkWechatTxnDetailBean.getFiid()==null){
//		}else if (checkWechatTxnDetailBean.getFiid() == 10) {
//			sql +=" and w.fiid in (10,11,13,14,15) ";
//			sqlCount +=" and w.fiid in (10,11,13,14,15) ";
////			map.put("fiid", checkWechatTxnDetailBean.getFiid());
//			date_flag = true ;
//		}else if (checkWechatTxnDetailBean.getFiid() == 11) {
//			sql +=" and w.fiid in (12,16) ";
//			sqlCount +=" and w.fiid in (12,16) ";
//			date_flag = true ;
//		}else if (checkWechatTxnDetailBean.getFiid() == 18) {
//			sql +=" and w.fiid in (18) ";
//			sqlCount +=" and w.fiid in (18) ";
//			date_flag = true ;
//		}
		if (checkWechatTxnDetailBean.getTxnType() != null&&!"".equals(checkWechatTxnDetailBean.getTxnType())) {
			sql +=" and w.txnType =:txnType ";
			sqlCount +=" and w.txnType =:txnType ";
			map.put("txnType", checkWechatTxnDetailBean.getTxnType());
			date_flag = true ;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (checkWechatTxnDetailBean.getCdateStart() != null && !"".equals(checkWechatTxnDetailBean.getCdateStart())&&checkWechatTxnDetailBean.getCdateEnd() != null && !"".equals(checkWechatTxnDetailBean.getCdateEnd())) {
			sql +=" and w.cdate between to_date('"+df.format(checkWechatTxnDetailBean.getCdateStart())+" 00:00:00','YYYY-MM-DD HH24:MI:SS' )and to_date('"+df.format(checkWechatTxnDetailBean.getCdateEnd())+" 23:59:59','YYYY-MM-DD HH24:MI:SS' ) ";
			sqlCount +=" and w.cdate between to_date('"+df.format(checkWechatTxnDetailBean.getCdateStart())+" 00:00:00','YYYY-MM-DD HH24:MI:SS' )and to_date('"+df.format(checkWechatTxnDetailBean.getCdateEnd())+" 23:59:59','YYYY-MM-DD HH24:MI:SS' ) ";
		} 
		if(date_flag==false&&(checkWechatTxnDetailBean.getCdateStart()== null &&checkWechatTxnDetailBean.getCdateEnd() == null )){
//			sql +=" and w.cdate >= to_date(to_char(sysdate-1, 'yyyy-MM-dd'), 'yyyy-MM-dd HH:MI:ss')";
//			sqlCount +=" and w.cdate >= to_date(to_char(sysdate-1, 'yyyy-MM-dd'), 'yyyy-MM-dd HH:MI:ss') ";
			sql +=" and w.cdate >= trunc(sysdate-1) ";
			sqlCount +=" and w.cdate >= trunc(sysdate-1) ";
		}
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//		if(checkWechatTxnDetailBean.getCdate()== null && "".equals(checkWechatTxnDetailBean.getCdate())&&date_flag == true){
//			//当有其他条件且时间为空时 ，查寻 不考虑时间
//		}else if (checkWechatTxnDetailBean.getCdate()!= null && !"".equals(checkWechatTxnDetailBean.getCdate())) {
//			sql +=" and trunc(w.cdate) =  to_date('"+df.format(checkWechatTxnDetailBean.getCdate())+"','yyyy-mm-dd')" ;
//			sqlCount +=" and trunc(w.cdate) =  to_date('"+df.format(checkWechatTxnDetailBean.getCdate())+"','yyyy-mm-dd')" ;
//		}else if (date_flag == false ){
//			//查询当天 to_date('1987-09-18','yyyy-mm-dd')
//			sql +=" and trunc(w.cdate) >=  to_date('"+df.format(new Date())+"','yyyy-mm-dd')" ;
//			sqlCount +=" and trunc(w.cdate) >=  to_date('"+df.format(new Date())+"','yyyy-mm-dd')" ;
//		}
		if (checkWechatTxnDetailBean.getSort() != null) {
			sql += " ORDER BY " + checkWechatTxnDetailBean.getSort() + " " + checkWechatTxnDetailBean.getOrder();
		}
		BigDecimal count = checkWechantTxnDetailDao.querysqlCounts(sqlCount, map);
		List<Map<String, String>>list = checkWechantTxnDetailDao.queryObjectsBySqlList(sql, map,checkWechatTxnDetailBean.getPage(), checkWechatTxnDetailBean.getRows());
		DataGridBean dataGridBean = new DataGridBean();
		dataGridBean.setTotal(count.intValue());
		dataGridBean.setRows(list);
		return dataGridBean;
	}
	
	/**
	 * 微信交易明细导出所有
	 */
	@Override
	public HSSFWorkbook wechantTxnExport(CheckWechatTxnDetailBean checkWechatTxnDetailBean, UserBean user) {
		
		String sql="select w.*,m.unno,m.rname,f.FIINFO1,(select mer_orderid from check_wechattxndetail ww where w.oripwid=ww.pwid) oldorderid from check_wechattxndetail w ,bill_merchantinfo m,sys_bankfi f  where w.mid=m.mid and w.fiid = f.fiid";
		
		Map<String, Object> map = new HashMap<String, Object>();
		boolean date_flag = false ;
		boolean unno_flag = true ;
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
			}else if(user.getUseLvl()==1||user.getUseLvl()==2) {//报单员业务员只能看到自己签约的机构下的交易
				sql += " and m.unno in (SELECT b1.unno FROM bill_agentsalesinfo a1, bill_agentunitinfo b1 WHERE a1.busid = b1.signuserid AND a1.user_id = "+user.getUserID()+")";
			}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
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
					String unno = user.getUnNo();
					if (checkWechatTxnDetailBean.getUnno() != null&&!"".equals(checkWechatTxnDetailBean.getUnno())) {
						unno = checkWechatTxnDetailBean.getUnno();
						unno_flag = false;
					}
					//代理
					String childUnno=merchantInfoService.queryUnitUnnoUtil(unno);
					sql+=" and m.unno IN ("+childUnno+" )";
				}
			}
		}
		if("8".equals(checkWechatTxnDetailBean.getTrantype())){
			sql +=" and w.trantype ='8' ";
		}else{
			//1
			sql +=" and (w.trantype !='8' or w.trantype is null) ";
		}
//		if("0".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 !=1 ";
//		}else if("1".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 in (1,6) ";
//		}
		if (unno_flag&&checkWechatTxnDetailBean.getUnno() != null&&!"".equals(checkWechatTxnDetailBean.getUnno())) {
			sql +=" and m.unno in ("+merchantInfoService.queryUnitUnnoUtil(checkWechatTxnDetailBean.getUnno())+") ";
			//map.put("unno", checkWechatTxnDetailBean.getUnno());
			date_flag = true ;
		}
		if (checkWechatTxnDetailBean.getMid() != null&&!"".equals(checkWechatTxnDetailBean.getMid())) {
			sql +=" and w.mid = :mid ";
			map.put("mid", checkWechatTxnDetailBean.getMid());
			date_flag = true ;
		}
		if (checkWechatTxnDetailBean.getMer_orderId() != null&&!"".equals(checkWechatTxnDetailBean.getMer_orderId())) {
			sql +=" and w.mer_orderId =:mer_orderId ";
			map.put("mer_orderId", checkWechatTxnDetailBean.getMer_orderId());
			date_flag = true ;
		}
		if (checkWechatTxnDetailBean.getStatus() != null&&!"".equals(checkWechatTxnDetailBean.getStatus())) {
			sql +=" and w.status =:status ";
			map.put("status", checkWechatTxnDetailBean.getStatus());
			date_flag = true ;
		}
		if (checkWechatTxnDetailBean.getFiinfo2()!=null&&!"".equals(checkWechatTxnDetailBean.getFiinfo2())) {
			sql += " and f.fiinfo2 like :fiinfo2";
			map.put("fiinfo2", "%"+checkWechatTxnDetailBean.getFiinfo2()+"%");
			date_flag = true;
		}
		if (checkWechatTxnDetailBean.getIsM35()!=null) {
			if (checkWechatTxnDetailBean.getIsM35()==6) {
				sql += " and m.isM35 =:isM35";
				map.put("isM35", checkWechatTxnDetailBean.getIsM35());
			}else {
				sql += " and m.isM35 !=:isM35";
				map.put("isM35", 6);
			}
			
			date_flag = true;
		}
		if (checkWechatTxnDetailBean.getTxnType() != null&&!"".equals(checkWechatTxnDetailBean.getTxnType())) {
			sql +=" and w.txnType =:txnType ";
			map.put("txnType", checkWechatTxnDetailBean.getTxnType());
			date_flag = true ;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (checkWechatTxnDetailBean.getCdateStart() != null && !"".equals(checkWechatTxnDetailBean.getCdateStart())&&checkWechatTxnDetailBean.getCdateEnd() != null && !"".equals(checkWechatTxnDetailBean.getCdateEnd())) {
			sql +=" and w.cdate between to_date('"+df.format(checkWechatTxnDetailBean.getCdateStart())+" 00:00:00','YYYY-MM-DD HH24:MI:SS' )and to_date('"+df.format(checkWechatTxnDetailBean.getCdateEnd())+" 23:59:59','YYYY-MM-DD HH24:MI:SS' ) ";
		} 
		if(date_flag==false&&(checkWechatTxnDetailBean.getCdateStart()== null &&checkWechatTxnDetailBean.getCdateEnd() == null )){
//			sql +=" and w.cdate >= to_date(to_char(sysdate-1, 'yyyy-MM-dd'), 'yyyy-MM-dd HH:MI:ss')";
			sql +=" and w.cdate >= trunc(sysdate-1) ";
		}
		if (checkWechatTxnDetailBean.getSort() != null) {
			sql += " ORDER BY " + checkWechatTxnDetailBean.getSort() + " " + checkWechatTxnDetailBean.getOrder();
		}
		List<Map<String, Object>> data = checkWechantTxnDetailDao.queryObjectsBySqlListMap2(sql, map);
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNNO");
		excelHeader.add("MID");
		excelHeader.add("RNAME");
		excelHeader.add("DETAIL");
		excelHeader.add("ACCNO");
		excelHeader.add("MER_ORDERID");
		excelHeader.add("OLDORDERID");
		excelHeader.add("BK_ORDERID");
		excelHeader.add("FIINFO1");
		excelHeader.add("TXNTYPE");
		excelHeader.add("TXNAMT");
		excelHeader.add("MDA");
		excelHeader.add("STATUS");
		excelHeader.add("CDATE");

		headMap.put("UNNO", "机构号");
		headMap.put("MID", "商户编号");
		headMap.put("RNAME", "商户名称");
		headMap.put("DETAIL", "终端号");
		headMap.put("ACCNO", "卡号");
		headMap.put("MER_ORDERID", "商户订单号");
		headMap.put("OLDORDERID", "原订单号");
		headMap.put("BK_ORDERID", "银行端订单号");
		headMap.put("FIINFO1", "渠道");
		headMap.put("TXNTYPE", "交易类型");
		headMap.put("TXNAMT", "交易金额");
		headMap.put("MDA", "手续费");
		headMap.put("STATUS", "状态");
		headMap.put("CDATE", "交易日期");

		return ExcelUtil.export("微信交易明细导出资料", data, excelHeader, headMap);
	}
	
	/**
	 * 查询微信交易商户明细汇总
	 */
	@Override
	public DataGridBean listCheckMerWechatdealData(CheckWechatTxnDetailBean checkWechatTxnDetailBean, UserBean user){
		
		DataGridBean dataGridBean = new DataGridBean();
		
		String sql="select m.unno unno,f.FIINFO1,m.mid,m.rname,count(decode(w.txnType,0,1)) TXNCOUNT," +
				" to_char(sum(decode(w.txnType,0,w.txnAmt,0)), 'FM999,999,999,999,990.00') TXNAMOUNT," +
				" to_char(sum(decode(w.txnType,0,w.mda,0)), 'FM999,999,999,999,990.00') MDA," +
				" count(decode(w.txnType,1,1)) REFUNDCOUNT, " +
				" to_char(sum(decode(w.txnType,1,w.txnAmt,0)), 'FM999,999,999,999,990.00') REFUNDAMT ," +
				" to_char(sum(decode(w.txnType,1,w.mda,0)), 'FM999,999,999,999,990.00') REFUNDMDA  ," +
				" to_char(sum(decode(w.txnType,0,w.txnAmt,0))-sum(decode(w.txnType,0,w.mda,0))-to_char(sum(decode(w.txnType,1,w.txnAmt,0))+sum(decode(w.txnType,1,w.mda,0))), 'FM999,999,999,999,990.00') MNAMT " +
				" from check_wechattxndetail w  ,bill_merchantinfo m, sys_bankfi f   where w.mid=m.mid and w.fiid=f.fiid ";
		String countSql="select '汇总' as unno, nvl(sum(c.TXNCOUNT),0) as TXNCOUNT, to_char(nvl(sum(c.TXNAMOUNT),0), 'FM999,999,999,999,990.00') as TXNAMOUNT, "
				+ "to_char(nvl(sum(c.MDA),0), 'FM999,999,999,999,990.00') as MDA, nvl(sum(c.REFUNDCOUNT),0) as REFUNDCOUNT, to_char(nvl(sum(c.REFUNDAMT),0), 'FM999,999,999,999,990.00') as REFUNDAMT, "
				+ "to_char(nvl(sum(c.REFUNDMDA),0), 'FM999,999,999,999,990.00') as REFUNDMDA, to_char(nvl(sum(c.MNAMT),0), 'FM999,999,999,999,990.00') as MNAMT "
				+ "from (select m.unno unno, m.mid, m.rname, count(decode(w.txnType, 0, 1)) TXNCOUNT, "
				+ "sum(decode(w.txnType, 0, w.txnAmt, 0)) TXNAMOUNT, sum(decode(w.txnType, 0, w.mda, 0)) MDA, count(decode(w.txnType, 1, 1)) REFUNDCOUNT, "
				+ "sum(decode(w.txnType, 1, w.txnAmt, 0)) REFUNDAMT, sum(decode(w.txnType, 1, w.mda, 0)) REFUNDMDA, (sum(decode(w.txnType, 0, w.txnAmt, 0)) - "
				+ "sum(decode(w.txnType, 0, w.mda, 0)) - to_char(sum(decode(w.txnType, 1, w.txnAmt, 0)) + sum(decode(w.txnType, 1, w.mda, 0)))) MNAMT from "
				+ "check_wechattxndetail w, bill_merchantinfo m, sys_bankfi f where w.mid = m.mid and w.fiid=f.fiid ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		boolean date_flag = false ;
		boolean unno_flag = true ;
		//权限
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if(user.getUseLvl()==3){
			//	sql+=" and b.MID ='"+userBean.getLoginName()+"' ";
			//	sqlCount+= " and b.MID ='"+userBean.getLoginName()+"' ";
				sql+=" and w.MID in (select MID from  bill_MerchantInfo where  " +
				"PARENTMID= '"+user.getLoginName()+"' or MID ='"+user.getLoginName()+"')  ";
				countSql+=" and w.MID in (select MID from  bill_MerchantInfo where  " +
						"PARENTMID= '"+user.getLoginName()+"' or MID ='"+user.getLoginName()+"')  ";
		}
		else{
			if("110000".equals(user.getUnNo())){
			}else if(user.getUseLvl()==1||user.getUseLvl()==2) {//报单员业务员只能看到自己签约的机构下的交易
				sql += " and m.unno in (SELECT b1.unno FROM bill_agentsalesinfo a1, bill_agentunitinfo b1 WHERE a1.busid = b1.signuserid AND a1.user_id = "+user.getUserID()+")";
				countSql += " and m.unno in (SELECT b1.unno FROM bill_agentsalesinfo a1, bill_agentunitinfo b1 WHERE a1.busid = b1.signuserid AND a1.user_id = "+user.getUserID()+")";
			}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else{
				String agHql ="select ag from AgentSalesModel ag where ag.maintainType!='D' and ag.userID="+user.getUserID();
				List<AgentSalesModel> ifAgList = agentSalesDao.queryObjectsByHqlList(agHql);
				if(ifAgList.size()>0){
					//业务员 user.getUserID()
					sql+=" and m.busid="+ifAgList.get(0).getBusid();
					countSql+=" and m.busid="+ifAgList.get(0).getBusid();
				}else{
					String unno = user.getUnNo();
					if (checkWechatTxnDetailBean.getUnno() != null&&!"".equals(checkWechatTxnDetailBean.getUnno())) {
						unno = checkWechatTxnDetailBean.getUnno();
						unno_flag = false;
					}
					//代理
					String childUnno=merchantInfoService.queryUnitUnnoUtil(unno);
					sql+=" and m.unno IN ("+childUnno+" )";
					countSql+=" and m.unno IN ("+childUnno+" )";
				}
			}
		}
		if("8".equals(checkWechatTxnDetailBean.getTrantype())){
			sql +=" and w.trantype ='8' ";
			countSql +=" and w.trantype ='8' ";
		}else{
			//1
			sql +=" and (w.trantype !='8' or w.trantype is null) ";
			countSql +=" and (w.trantype !='8' or w.trantype is null) ";
		}
//		if("0".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 !=1 ";
//			countSql +=" and m.isM35 !=1 ";
//		}else if("1".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 in (1,6) ";
//			countSql +=" and m.isM35 in (1,6) ";
//		}
		if (checkWechatTxnDetailBean.getIsM35()!=null) {
			if (checkWechatTxnDetailBean.getIsM35()==6) {
				sql += " and m.isM35 =:isM35";
				countSql += " and m.isM35 =:isM35";
				map.put("isM35", 6);
			}else {
				sql += " and m.isM35 !=:isM35";
				countSql += " and m.isM35 !=:isM35";
				map.put("isM35", 6);
			}
			
			date_flag = true;
		}
		if (unno_flag&&checkWechatTxnDetailBean.getUnno() != null&&!"".equals(checkWechatTxnDetailBean.getUnno())) {
			sql +=" and m.unno in ("+merchantInfoService.queryUnitUnnoUtil(checkWechatTxnDetailBean.getUnno())+") ";
			countSql +=" and m.unno in ("+merchantInfoService.queryUnitUnnoUtil(checkWechatTxnDetailBean.getUnno())+") ";
			//map.put("unno", checkWechatTxnDetailBean.getUnno());
			date_flag = true ;
		}
		if (checkWechatTxnDetailBean.getMid() != null&&!"".equals(checkWechatTxnDetailBean.getMid())) {
			sql +=" and w.mid = :mid ";
			countSql +=" and w.mid = :mid ";
			map.put("mid", checkWechatTxnDetailBean.getMid());
			date_flag = true ;
		}
		if (checkWechatTxnDetailBean.getMer_orderId() != null&&!"".equals(checkWechatTxnDetailBean.getMer_orderId())) {
			sql +=" and w.mer_orderId =:mer_orderId ";
			countSql +=" and w.mer_orderId =:mer_orderId ";
			map.put("mer_orderId", checkWechatTxnDetailBean.getMer_orderId());
			date_flag = true ;
		}
		if (checkWechatTxnDetailBean.getStatus() != null&&!"".equals(checkWechatTxnDetailBean.getStatus())) {
			sql +=" and w.status =:status ";
			countSql +=" and w.status =:status ";
			map.put("status", checkWechatTxnDetailBean.getStatus());
			date_flag = true ;
		}
		if(checkWechatTxnDetailBean.getFiinfo1()==null){
			
		}else {
			sql += " and f.fiinfo1 like %:fiinfo1%";
			countSql += " and f.fiinfo1 like %:fiinfo1%";
			map.put("fiinfo1", checkWechatTxnDetailBean.getFiinfo1());
			date_flag = true;
		}
		if (checkWechatTxnDetailBean.getTxnType() != null&&!"".equals(checkWechatTxnDetailBean.getTxnType())) {
			sql +=" and w.txnType =:txnType ";
			countSql +=" and w.txnType =:txnType ";
			map.put("txnType", checkWechatTxnDetailBean.getTxnType());
			date_flag = true ;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (checkWechatTxnDetailBean.getCdateStart() != null && !"".equals(checkWechatTxnDetailBean.getCdateStart())&&checkWechatTxnDetailBean.getCdateEnd() != null && !"".equals(checkWechatTxnDetailBean.getCdateEnd())) {
			sql +=" and w.cdate between to_date('"+df.format(checkWechatTxnDetailBean.getCdateStart())+" 00:00:00','yyyy-MM-dd HH24:MI:SS' )and to_date('"+df.format(checkWechatTxnDetailBean.getCdateEnd())+" 23:59:59','yyyy-MM-dd HH24:MI:SS' ) ";
			countSql +=" and w.cdate between to_date('"+df.format(checkWechatTxnDetailBean.getCdateStart())+" 00:00:00','yyyy-MM-dd HH24:MI:SS' )and to_date('"+df.format(checkWechatTxnDetailBean.getCdateEnd())+" 23:59:59','yyyy-MM-dd HH24:MI:SS' ) ";
		}
		if(date_flag==false&&(checkWechatTxnDetailBean.getCdateStart()== null &&checkWechatTxnDetailBean.getCdateEnd() == null )){
//			sql +=" and w.cdate >= to_date(to_char(sysdate-1, 'yyyy-MM-dd'), 'yyyy-MM-dd HH:MI:ss')";
//			countSql +=" and w.cdate >= to_date(to_char(sysdate-1, 'yyyy-MM-dd'), 'yyyy-MM-dd HH:MI:ss') ";
			sql +=" and w.cdate >= trunc(sysdate-1) ";
			countSql +=" and w.cdate >= trunc(sysdate-1) ";
		}
		sql+="  group by m.unno ,m.mid,m.rname ,f.FIINFO1";
		countSql+="  group by m.unno, m.mid, m.rname,f.FIINFO1 ) c";
		if (checkWechatTxnDetailBean.getSort() != null) {
			sql += " ORDER BY " + checkWechatTxnDetailBean.getSort() + " " + checkWechatTxnDetailBean.getOrder();
		}
		String sqlCount = "select count(1) from ("+sql+")";
		
		List<Map<String, String>> CheckWechatTxnCountList = checkWechantTxnDetailDao.queryObjectsBySqlListMap(countSql, map);
		List<Map<String, String>> CheckWechatTxnList = checkWechantTxnDetailDao.queryObjectsBySqlList(sql, map, checkWechatTxnDetailBean.getPage(), checkWechatTxnDetailBean.getRows());
		BigDecimal count = checkWechantTxnDetailDao.querysqlCounts(sqlCount, map);
		CheckWechatTxnCountList.addAll(CheckWechatTxnList);
		dataGridBean.setTotal(count.intValue());
		dataGridBean.setRows(CheckWechatTxnCountList);
		
		return dataGridBean;
	}
	/**
	 * 微信交易商户汇总 导出所有
	 */
	public void checkWechatTxnDetailExcelAll(CheckWechatTxnDetailBean checkWechatTxnDetailBean, UserBean user,HttpServletResponse resp){
		
			String sql="select m.unno unno,m.mid,m.rname, f.fiinfo1,count(decode(w.txnType,0,1)) TXNCOUNT," +
				" to_char(sum(decode(w.txnType,0,w.txnAmt,0)), 'FM999,999,999,999,990.00') TXNAMOUNT," +
				" to_char(sum(decode(w.txnType,0,w.mda,0)), 'FM999,999,999,999,990.00') MDA," +
				" count(decode(w.txnType,1,1)) REFUNDCOUNT, " +
				" to_char(sum(decode(w.txnType,1,w.txnAmt,0)), 'FM999,999,999,999,990.00') REFUNDAMT ," +
				" to_char(sum(decode(w.txnType,1,w.mda,0)), 'FM999,999,999,999,990.00') REFUNDMDA  ," +
				" to_char(sum(decode(w.txnType,0,w.txnAmt,0))-sum(decode(w.txnType,0,w.mda,0))-to_char(sum(decode(w.txnType,1,w.txnAmt,0))+sum(decode(w.txnType,1,w.mda,0))), 'FM999,999,999,999,990.00') MNAMT " +
				" from check_wechattxndetail w  ,bill_merchantinfo m, sys_bankfi f  where w.mid=m.mid and w.fiid = f.fiid";

			Map<String, Object> map = new HashMap<String, Object>();
			boolean date_flag = false ;
			boolean unno_flag = true ;
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
				}else if(user.getUseLvl()==1||user.getUseLvl()==2) {//报单员业务员只能看到自己签约的机构下的交易
					sql += " and m.unno in (SELECT b1.unno FROM bill_agentsalesinfo a1, bill_agentunitinfo b1 WHERE a1.busid = b1.signuserid AND a1.user_id = "+user.getUserID()+")";
				}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
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
						String unno = user.getUnNo();
						if (checkWechatTxnDetailBean.getUnno() != null&&!"".equals(checkWechatTxnDetailBean.getUnno())) {
							unno = checkWechatTxnDetailBean.getUnno();
							unno_flag = false;
						}
						//代理
						String childUnno=merchantInfoService.queryUnitUnnoUtil(unno);
						sql+=" and m.unno IN ("+childUnno+" )";
					}
				}
			}
			if("8".equals(checkWechatTxnDetailBean.getTrantype())){
				sql +=" and w.trantype ='8' ";
			}else{
				//1
				sql +=" and (w.trantype !='8' or w.trantype is null) ";
			}
//			if("0".equals(user.getIsM35Type())){
//				sql +=" and m.isM35 !=1 ";
//			}else if("1".equals(user.getIsM35Type())){
//				sql +=" and m.isM35 in (1,6) ";
//			}
			if (checkWechatTxnDetailBean.getIsM35()!=null) {
				if (checkWechatTxnDetailBean.getIsM35()==6) {
					sql += " and m.isM35 =:isM35";
					map.put("isM35", 6);
				}else {
					sql += " and m.isM35 !=:isM35";
					map.put("isM35", 6);
				}
				
				date_flag = true;
			}
			if (unno_flag&&checkWechatTxnDetailBean.getUnno() != null&&!"".equals(checkWechatTxnDetailBean.getUnno())) {
				sql +=" and m.unno in ("+merchantInfoService.queryUnitUnnoUtil(checkWechatTxnDetailBean.getUnno())+") ";
				//map.put("unno", checkWechatTxnDetailBean.getUnno());
				date_flag = true ;
			}
			if (checkWechatTxnDetailBean.getMid() != null&&!"".equals(checkWechatTxnDetailBean.getMid())) {
				sql +=" and w.mid = :mid ";
				map.put("mid", checkWechatTxnDetailBean.getMid());
				date_flag = true ;
			}
			if (checkWechatTxnDetailBean.getMer_orderId() != null&&!"".equals(checkWechatTxnDetailBean.getMer_orderId())) {
				sql +=" and w.mer_orderId =:mer_orderId ";
				map.put("mer_orderId", checkWechatTxnDetailBean.getMer_orderId());
				date_flag = true ;
			}
			if (checkWechatTxnDetailBean.getStatus() != null&&!"".equals(checkWechatTxnDetailBean.getStatus())) {
				sql +=" and w.status =:status ";
				map.put("status", checkWechatTxnDetailBean.getStatus());
				date_flag = true ;
			}
			if(checkWechatTxnDetailBean.getFiinfo1()==null){
				
			}else {
				sql += " and f.fiinfo1 like %:fiinfo1%";
				map.put("fiinfo1", checkWechatTxnDetailBean.getFiinfo1());
				date_flag = true;
			}
			if (checkWechatTxnDetailBean.getTxnType() != null&&!"".equals(checkWechatTxnDetailBean.getTxnType())) {
				sql +=" and w.txnType =:txnType ";
				map.put("txnType", checkWechatTxnDetailBean.getTxnType());
				date_flag = true ;
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if (checkWechatTxnDetailBean.getCdateStart() != null && !"".equals(checkWechatTxnDetailBean.getCdateStart())&&checkWechatTxnDetailBean.getCdateEnd() != null && !"".equals(checkWechatTxnDetailBean.getCdateEnd())) {
				sql +=" and w.cdate between to_date('"+df.format(checkWechatTxnDetailBean.getCdateStart())+" 00:00:00','yyyy-MM-dd HH24:MI:SS' )and to_date('"+df.format(checkWechatTxnDetailBean.getCdateEnd())+" 23:59:59','yyyy-MM-dd HH24:MI:SS' ) ";
			}
			if(date_flag==false&&(checkWechatTxnDetailBean.getCdateStart()== null &&checkWechatTxnDetailBean.getCdateEnd() == null )){
//				sql +=" and w.cdate >= to_date(to_char(sysdate-1, 'yyyy-MM-dd'), 'yyyy-MM-dd HH:MI:ss')";
				sql +=" and w.cdate >= trunc(sysdate-1) ";
			}
			sql+="  group by m.unno ,m.mid,m.rname ,f.fiinfo1 order by m.unno asc";
			
			List<Map<String, Object>> data = checkWechantTxnDetailDao.queryObjectsBySqlListMap2(sql, map);
			List<String> excelHeader = new ArrayList<String>();
			Map<String, Object> headMap = new HashMap<String, Object>();
			excelHeader.add("UNNO");
			excelHeader.add("MID");
			excelHeader.add("RNAME");
			excelHeader.add("FIINFO1");
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
			headMap.put("FIINFO1", "支付渠道");
			headMap.put("TXNCOUNT", "消费次数");
			headMap.put("TXNAMOUNT", "消费金额");
			headMap.put("MDA", "消费手续费");
			headMap.put("REFUNDCOUNT", "退款次数");
			headMap.put("REFUNDAMT", "退款金额");
			headMap.put("REFUNDMDA", "退款手续费");
			headMap.put("MNAMT", "结算金额");
			try {
				JxlOutExcelUtil.export("微信交易商户汇总导出所有", data, excelHeader, headMap,"微信交易商户汇总.xls",resp);
			}  catch (Exception e) {
				log.error("微信交易商户汇总导出异常：" + e);
			}
//			return ExcelUtil.export("微信交易商户汇总导出所有", data, excelHeader, headMap);
	}
	/**
	 * 查询微信交易机构明细汇总
	 */
	@Override
	public DataGridBean listCheckUnitWechatdealData(CheckWechatTxnDetailBean checkWechatTxnDetailBean, UserBean user){
		
		DataGridBean dataGridBean = new DataGridBean();
		
		String sql="select FIINFO1, a.yidai,(select un_name from sys_unit where unno=a.yidai )as un_name, sum(a.TXNCOUNT) as TXNCOUNT, "+
				" to_char(sum(a.TXNAMOUNT),'FM999,999,999,999,990.00')  as TXNAMOUNT, count(a.HOTMERCOUNT) as HOTMERCOUNT  from  " +
				"(select m.unno,nvl((select s.unno from sys_unit s where  s.un_lvl=2 start with s.unno = m.unno "+
				" connect by NOCYCLE  s.unno = prior s.upper_unit ),m.unno) as yidai, " +
				"f.FIINFO1, count(decode(w.txnType,0,1)) TXNCOUNT,sum(decode(w.txnType,  0, w.txnAmt, 0)) TXNAMOUNT," +
				" sum(decode(w.txnType,0,w.txnAmt,0)) , count(distinct w.mid) HOTMERCOUNT " +
				" from check_wechattxndetail w, bill_merchantinfo m, sys_bankfi f where w.mid=m.mid and w.fiid=f.fiid  ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		boolean date_flag = false ;
		//权限
//		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
//		String agHql ="select ag from AgentSalesModel ag where ag.maintainType!='D' and ag.userID="+user.getUserID();
//		List<AgentSalesModel> ifAgList = agentSalesDao.queryObjectsByHqlList(agHql);
//		if("110000".equals(user.getUnNo())){
//		}
//		else if(ifAgList.size()>0){
//			//业务员 user.getUserID()
//			sql+=" and m.unno in (select UNNO from sys_unit start with unno  in (select agu.unno from bill_agentunitinfo agu where agu.signuserid ='"+ifAgList.get(0).getBusid()+"') " +
//					"and status=1 connect by NOCYCLE prior  unno =  upper_unit )";
//		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
//			UnitModel parent = unitModel.getParent();
//			if("110000".equals(parent.getUnNo())){
//			}
//		}else{
//			//代理
//			String childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
//			sql+=" and m.unno IN ("+childUnno+" )";
//		}
		if("8".equals(checkWechatTxnDetailBean.getTrantype())){
			sql +=" and w.trantype ='8' ";
		}else if ("1".equals(checkWechatTxnDetailBean.getTrantype())){
			sql +=" and (w.trantype !='8' or w.trantype is null) ";
		}
//		if("0".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 !=1 ";
//		}else if("1".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 in (1,6) ";
//		}
		if (checkWechatTxnDetailBean.getIsM35()!=null) {
			if (checkWechatTxnDetailBean.getIsM35()==6) {
				sql += " and m.isM35 =:isM35";
				map.put("isM35", 6);
			}else {
				sql += " and m.isM35 !=:isM35";
				map.put("isM35", 6);
			}
			date_flag = true;
		}
		if (checkWechatTxnDetailBean.getUnno() != null&&!"".equals(checkWechatTxnDetailBean.getUnno())) {
			sql +=" and m.unno in("+merchantInfoService.queryUnitUnnoUtil(checkWechatTxnDetailBean.getUnno())+") ";
			date_flag = true ;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (checkWechatTxnDetailBean.getCdateStart() != null && !"".equals(checkWechatTxnDetailBean.getCdateStart())&&checkWechatTxnDetailBean.getCdateEnd() != null && !"".equals(checkWechatTxnDetailBean.getCdateEnd())) {
			sql +=" and w.cdate between to_date('"+df.format(checkWechatTxnDetailBean.getCdateStart())+" 00:00:00','yyyy-MM-dd HH24:MI:SS' )and to_date('"+df.format(checkWechatTxnDetailBean.getCdateEnd())+" 23:59:59','yyyy-MM-dd HH24:MI:SS' ) ";
		}
		
		if(date_flag==false&&(checkWechatTxnDetailBean.getCdateStart()== null &&checkWechatTxnDetailBean.getCdateEnd() == null )){
//			sql +=" and w.cdate >= to_date(to_char(sysdate-1, 'yyyy-MM-dd'), 'yyyy-MM-dd HH:MI:ss')";
			sql +=" and w.cdate >= trunc(sysdate-1) ";
		}
		sql+="  group by f.FIINFO1, m.unno )a group by a.yidai,FIINFO1 ";
//		if (checkWechatTxnDetailBean.getSort() != null) {
//			sql += " ORDER BY " + checkWechatTxnDetailBean.getSort() + " " + checkWechatTxnDetailBean.getOrder();
//		}
		
		String sqlCount = "select count(1) from ("+sql+")";
		List<Map<String, String>> CheckWechatTxnList = checkWechantTxnDetailDao.queryObjectsBySqlList(sql, map, checkWechatTxnDetailBean.getPage(), checkWechatTxnDetailBean.getRows());
		BigDecimal count = checkWechantTxnDetailDao.querysqlCounts(sqlCount, map);
		
		dataGridBean.setTotal(count.intValue());
		dataGridBean.setRows(CheckWechatTxnList);
		
		return dataGridBean;
	}
	/**
	 * 微信交易机构汇总 导出所有
	 */
	public HSSFWorkbook checkWechatTxnUnitDetailExcelAll(CheckWechatTxnDetailBean checkWechatTxnDetailBean, UserBean user){
		
		String sql="select a.yidai,(select un_name from sys_unit where unno=a.yidai )as un_name, a.fiinfo1,sum(a.TXNCOUNT) as TXNCOUNT, "+
		" to_char(sum(a.TXNAMOUNT),'FM999,999,999,999,990.00')  as TXNAMOUNT, count(a.HOTMERCOUNT) as HOTMERCOUNT  from  " +
		"(select m.unno,nvl((select s.unno from sys_unit s where  s.un_lvl=2 start with s.unno = m.unno "+
		" connect by NOCYCLE  s.unno = prior s.upper_unit ),m.unno) as yidai, " +
		" f.fiinfo1,count(decode(w.txnType,0,1)) TXNCOUNT,sum(decode(w.txnType,  0, w.txnAmt, 0)) TXNAMOUNT," +
		" sum(decode(w.txnType,0,w.txnAmt,0)) , count(distinct w.mid) HOTMERCOUNT " +
		" from check_wechattxndetail w  ,bill_merchantinfo m, sys_bankfi f where w.mid=m.mid and w.fiid = f.fiid ";

		Map<String, Object> map = new HashMap<String, Object>();
		boolean date_flag = false ;
		//权限
//		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
//		String agHql ="select ag from AgentSalesModel ag where ag.maintainType!='D' and ag.userID="+user.getUserID();
//		List<AgentSalesModel> ifAgList = agentSalesDao.queryObjectsByHqlList(agHql);
//		if("110000".equals(user.getUnNo())){
//		}
//		else if(ifAgList.size()>0){
//			//业务员 user.getUserID()
//			sql+=" and m.unno in (select UNNO from sys_unit start with unno  in (select agu.unno from bill_agentunitinfo agu where agu.signuserid ='"+ifAgList.get(0).getBusid()+"') " +
//				"and status=1 connect by NOCYCLE prior  unno =  upper_unit )";
//		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
//			UnitModel parent = unitModel.getParent();
//			if("110000".equals(parent.getUnNo())){
//			}
//		}else{
//			//代理
//			String childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
//			sql+=" and m.unno IN ("+childUnno+" )";
//		}
		if("8".equals(checkWechatTxnDetailBean.getTrantype())){
			sql +=" and w.trantype ='8' ";
		}else if ("1".equals(checkWechatTxnDetailBean.getTrantype())){
			sql +=" and (w.trantype !='8' or w.trantype is null) ";
		}
//		if("0".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 !=1 ";
//		}else if("1".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 in (1,6) ";
//		}
		if (checkWechatTxnDetailBean.getIsM35()!=null) {
			if (checkWechatTxnDetailBean.getIsM35()==6) {
				sql += " and m.isM35 =:isM35";
				map.put("isM35", 6);
			}else {
				sql += " and m.isM35 !=:isM35";
				map.put("isM35", 6);
			}
			date_flag = true;
		}
		if (checkWechatTxnDetailBean.getUnno() != null&&!"".equals(checkWechatTxnDetailBean.getUnno())) {
			sql +=" and m.unno in("+merchantInfoService.queryUnitUnnoUtil(checkWechatTxnDetailBean.getUnno())+") ";
			date_flag = true ;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (checkWechatTxnDetailBean.getCdateStart() != null && !"".equals(checkWechatTxnDetailBean.getCdateStart())&&checkWechatTxnDetailBean.getCdateEnd() != null && !"".equals(checkWechatTxnDetailBean.getCdateEnd())) {
			sql +=" and w.cdate between to_date('"+df.format(checkWechatTxnDetailBean.getCdateStart())+" 00:00:00','yyyy-MM-dd HH24:MI:SS' )and to_date('"+df.format(checkWechatTxnDetailBean.getCdateEnd())+" 23:59:59','yyyy-MM-dd HH24:MI:SS' ) ";
		}
		
		if(date_flag==false&&(checkWechatTxnDetailBean.getCdateStart()== null &&checkWechatTxnDetailBean.getCdateEnd() == null )){
//			sql +=" and w.cdate >= to_date(to_char(sysdate-1, 'yyyy-MM-dd'), 'yyyy-MM-dd HH:MI:ss')";
			sql +=" and w.cdate >= trunc(sysdate-1) ";
		}
		sql+="  group by m.unno ,f.fiinfo1)a group by a.yidai, a.fiinfo1 ";
		
		List<Map<String, Object>> data = checkWechantTxnDetailDao.queryObjectsBySqlListMap2(sql, map);
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("YIDAI");
		excelHeader.add("UN_NAME");
		excelHeader.add("FIINFO1");
		excelHeader.add("TXNCOUNT");
		excelHeader.add("TXNAMOUNT");
		excelHeader.add("HOTMERCOUNT");
		
		headMap.put("YIDAI", "机构号");
		headMap.put("UN_NAME", "商户名称");
		headMap.put("FIINFO1", "支付渠道");
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
		List<CheckWechatTxnDetailBean> WechatTxnBeanList = new ArrayList<CheckWechatTxnDetailBean>();
		for(CheckWechatTxnDetailModel CheckWechatTxnDetailModel : CheckWechatTxnList) {
			CheckWechatTxnDetailBean WechatTxnABean = new CheckWechatTxnDetailBean();
			BeanUtils.copyProperties(CheckWechatTxnDetailModel, WechatTxnABean);
			String str = CheckWechatTxnDetailModel.getCdate().toString();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			//df.format(CheckWechatTxnDetailModel.getCdate();
			Date date;
			try {
				date = df.parse(str);
				WechatTxnABean.setCdate(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//查询机构号
			 List<MerchantInfoModel> list=merchantInfoDao.queryObjectsByHqlList("select m from MerchantInfoModel m where m.mid='"+WechatTxnABean.getMid()+"'");
			 String unno = list.get(0).getUnno()+"";
			 String rname = list.get(0).getRname()+"";
			WechatTxnABean.setUnno(unno);
			WechatTxnABean.setRname(rname);
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
//	public boolean saveWechantTxnRefund(CheckWechatTxnDetailBean cwtdb, UserBean user){
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
//	public boolean queryIfWechantTxnRefund(CheckWechatTxnDetailBean checkWechatTxnDetailBean){
//		boolean ifRefund = false ;
//		//本次退款
//		Double Refund = checkWechatTxnDetailBean.getRefundAmt();
//		//已经退款
//		Double yt = 0d ;
//		//原来交易额
//		Double txnAmt = checkWechatTxnDetailBean.getTxnAmt();
//		
//		String sql = "select sum(w.txnAmt) from check_wechattxndetail w where w.oriPwid="+checkWechatTxnDetailBean.getPwid()+" and w.txnType='1' ";
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
