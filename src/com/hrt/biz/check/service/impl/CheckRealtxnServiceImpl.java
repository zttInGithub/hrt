package com.hrt.biz.check.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.biz.check.dao.CheckRealtxnDao;
import com.hrt.biz.check.entity.pagebean.CheckRealtxnBean;
import com.hrt.biz.check.service.CheckRealtxnService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class CheckRealtxnServiceImpl implements CheckRealtxnService {
 
	private CheckRealtxnDao checkRealtxnDao;

	public CheckRealtxnDao getCheckRealtxnDao() {
		return checkRealtxnDao;
	}

	public void setCheckRealtxnDao(CheckRealtxnDao checkRealtxnDao) {
		this.checkRealtxnDao = checkRealtxnDao;
	}

	@Override
	public DataGridBean listRealtxn(CheckRealtxnBean checkRealtxnBean, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		Map<String, Object> map = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String sql = "select nvl(kk.sn,decode(a.ismpos,2,a.tid,'')) sn,a.* from check_realtxn a " +
				" left join bill_terminalinfo kk on a.tid=kk.termid where a.txnday=:txnday ";
		//销售
		String sql1 = "";
		map.put("txnday", sdf.format(new Date()));
		if("110000".equals(user.getUnNo())) {//总公司权限
		}else if(user.getUseLvl()==2||user.getUseLvl()==1){//业务人员
			sql1 = sql + " and (a.unno in (select unno from sys_unit start with unno in (select unno from bill_agentunitinfo a where a.signuserid = "
				+ "(select busid from  bill_agentsalesinfo where user_id = "+user.getUserID()+" and MAINTAINTYPE != 'D') and unno != '"+user.getUnNo()+"') connect by prior unno = upper_unit)) ";
			sql += " and (a.saleName=( select salename from bill_agentsalesinfo where user_id="+user.getUserID()+" and maintaintype!='D' ) and a.ismpos=0) ";
		}else if(user.getUseLvl()==3) {//商户
			sql += " and a.mid=:mid1";
			map.put("mid1", user.getLoginName());
		}else if(user.getUseLvl()==0) {//代理商管理员
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno1 connect by prior unno = upper_unit) ";
			map.put("unno1", user.getUnNo());
		}
		if(checkRealtxnBean.getMid()!=null&&!"".equals(checkRealtxnBean.getMid())) {
			sql += " and a.mid=:mid ";
			if(!"".equals(sql1)) {
				sql1 += " and a.mid=:mid ";
			}
			map.put("mid", checkRealtxnBean.getMid());
		}
		if(checkRealtxnBean.getRname()!=null&&!"".equals(checkRealtxnBean.getRname())) {
			sql += " and a.rname=:rname ";
			if(!"".equals(sql1)) {
				sql1 += " and a.rname=:rname ";
			}
			map.put("rname", checkRealtxnBean.getRname());
		}
		if(checkRealtxnBean.getOrderID()!=null&&!"".equals(checkRealtxnBean.getOrderID())) {
			sql += " and a.orderid=:orderID ";
			if(!"".equals(sql1)) {
				sql1 += " and a.orderid=:orderID ";
			}
			map.put("orderID", checkRealtxnBean.getOrderID());
		}
		if(checkRealtxnBean.getCardPan()!=null&&!"".equals(checkRealtxnBean.getCardPan())) {
			sql += " and a.cardPan=:cardPan ";
			if(!"".equals(sql1)) {
				sql1 += " and a.cardPan=:cardPan ";
			}
			map.put("cardPan", checkRealtxnBean.getCardPan());
		}
		if(checkRealtxnBean.getTid()!=null&&!"".equals(checkRealtxnBean.getTid())) {
			sql += " and a.tid=:tid ";
			if(!"".equals(sql1)) {
				sql1 += " and a.tid=:tid ";
			}
			map.put("tid", checkRealtxnBean.getTid());
		}
		if(checkRealtxnBean.getUnno()!=null&&!"".equals(checkRealtxnBean.getUnno())) {
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			if(!"".equals(sql1)) {
				sql1 += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			}
			map.put("unno", checkRealtxnBean.getUnno());
		}
		if(checkRealtxnBean.getSaleName()!=null&&!"".equals(checkRealtxnBean.getSaleName())) {
			sql += " and a.saleName=:saleName ";
			if(!"".equals(sql1)) {
				sql1 += " and a.saleName=:saleName ";
			}
			map.put("saleName", checkRealtxnBean.getSaleName());
		}
		if(checkRealtxnBean.getIsMpos()!=null) {
			sql += " and a.ismpos=:ismpos ";
			if(!"".equals(sql1)) {
				sql1 += " and a.ismpos=:ismpos ";
			}
			map.put("ismpos", checkRealtxnBean.getIsMpos());
		}
		if(checkRealtxnBean.getTxnType()!=null) {
			sql += " and a.txnType=:txnType ";
			if(!"".equals(sql1)) {
				sql1 += " and a.txnType=:txnType ";
			}
			map.put("txnType", checkRealtxnBean.getTxnType());
		}
		if(checkRealtxnBean.getMti()!=null) {
			sql += " and a.mti=:mti ";
			if(!"".equals(sql1)) {
				sql1 += " and a.mti=:mti ";
			}
			map.put("mti", checkRealtxnBean.getMti());
		}
		if(checkRealtxnBean.getTxnState()!=null) {
			sql += " and a.txnstate=:txnstate ";
			if(!"".equals(sql1)) {
				sql1 += " and a.txnstate=:txnstate ";
			}
			map.put("txnstate", checkRealtxnBean.getTxnState());
		}
		if(checkRealtxnBean.getTxnTime()!=null&&!"".equals(checkRealtxnBean.getTxnTime())) {
			sql += " and a.txntime>=:txntime ";
			if(!"".equals(sql1)) {
				sql1 += " and a.txntime>=:txntime ";
			}
			map.put("txntime", checkRealtxnBean.getTxnTime());
		}
		if(checkRealtxnBean.getTxnTimeEnd()!=null&&!"".equals(checkRealtxnBean.getTxnTimeEnd())) {
			sql += " and a.txntime<=:txntimeend ";
			if(!"".equals(sql1)) {
				sql1 += " and a.txntime<=:txntimeend ";
			}
			map.put("txntimeend", checkRealtxnBean.getTxnTimeEnd());
		}
		if(!"".equals(sql1)) {
			sql = "select * from ( "+sql+" union "+sql1+") a ";
		}
		String sqlCount = "select count(1) from ("+sql+")";
		sql += " order by a.txntime desc ";
		List<Map<String,Object>> list = checkRealtxnDao.queryObjectsBySqlList2(sql, map, checkRealtxnBean.getPage(), checkRealtxnBean.getRows());
		for (Map<String, Object> map2 : list) {
			String card = map2.get("CARDPAN")+"";
			if(card.length()>10) {
				map2.put("CARDPAN", card.substring(0, 6)+card.substring(6,card.length()-4).replaceAll("\\d", "*")+card.substring(card.length()-4));
			}
		}
		BigDecimal counts = checkRealtxnDao.querysqlCounts(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}
	
	@Override
	public List<Map<String, Object>> listRealtxnTotal(CheckRealtxnBean checkRealtxnBean, UserBean user) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String sql = "select nvl(to_char(count(1),'FM999,999,999,999,990'),0) totalNum,nvl(to_char(sum(txnamount),'FM999,999,999,999,990.00'),0) totalAmount from check_realtxn a where a.txnday=:txnday ";
		String sql1 = "";
		map.put("txnday", sdf.format(new Date()));
		if("110000".equals(user.getUnNo())) {//总公司权限
		}else if(user.getUseLvl()==2||user.getUseLvl()==1){//业务人员
			sql = "SELECT nvl(COUNT(1), 0) AS totalNum , nvl(SUM(txnamount), 0) AS totalAmount FROM check_realtxn a "
				+ "WHERE a.txnday = :txnday AND a.mid IN ( SELECT mid FROM bill_merchantinfo WHERE busid = ( SELECT busid FROM "
				+ "bill_agentsalesinfo WHERE user_id = "+user.getUserID()+" AND maintaintype != 'D' ) AND unno = '"+user.getUnNo()+"' ) ";
			sql1 = "SELECT nvl(COUNT(1), 0) AS totalNum , nvl(SUM(txnamount), 0) AS totalAmount FROM check_realtxn a "
				+ "WHERE a.txnday = :txnday AND a.unno IN ( SELECT unno FROM sys_unit START WITH unno IN ( SELECT unno FROM "
				+ "bill_agentunitinfo a WHERE a.signuserid = ( SELECT busid FROM bill_agentsalesinfo WHERE user_id = "+user.getUserID()+" "
				+ "AND MAINTAINTYPE != 'D' ) AND unno != '"+user.getUnNo()+"' ) CONNECT BY PRIOR unno = upper_unit ) ";
		}else if(user.getUseLvl()==3) {//商户
			sql += " and a.mid=:mid1";
			map.put("mid1", user.getLoginName());
		}else if(user.getUseLvl()==0) {//代理商管理员
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno1 connect by prior unno = upper_unit) ";
			map.put("unno1", user.getUnNo());
		}
		if(checkRealtxnBean.getMid()!=null&&!"".equals(checkRealtxnBean.getMid())) {
			sql += " and a.mid=:mid ";
			if(!"".equals(sql1)) {
				sql1 += " and a.mid=:mid ";
			}
			map.put("mid", checkRealtxnBean.getMid());
		}
		if(checkRealtxnBean.getRname()!=null&&!"".equals(checkRealtxnBean.getRname())) {
			sql += " and a.rname=:rname ";
			if(!"".equals(sql1)) {
				sql1 += " and a.rname=:rname ";
			}
			map.put("rname", checkRealtxnBean.getRname());
		}
		if(checkRealtxnBean.getOrderID()!=null&&!"".equals(checkRealtxnBean.getOrderID())) {
			sql += " and a.orderid=:orderID ";
			if(!"".equals(sql1)) {
				sql1 += " and a.orderid=:orderID ";
			}
			map.put("orderID", checkRealtxnBean.getOrderID());
		}
		if(checkRealtxnBean.getCardPan()!=null&&!"".equals(checkRealtxnBean.getCardPan())) {
			sql += " and a.cardPan=:cardPan ";
			if(!"".equals(sql1)) {
				sql1 += " and a.cardPan=:cardPan ";
			}
			map.put("cardPan", checkRealtxnBean.getCardPan());
		}
		if(checkRealtxnBean.getTid()!=null&&!"".equals(checkRealtxnBean.getTid())) {
			sql += " and a.tid=:tid ";
			if(!"".equals(sql1)) {
				sql1 += " and a.tid=:tid ";
			}
			map.put("tid", checkRealtxnBean.getTid());
		}
		if(checkRealtxnBean.getUnno()!=null&&!"".equals(checkRealtxnBean.getUnno())) {
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			if(!"".equals(sql1)) {
				sql1 += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			}
			map.put("unno", checkRealtxnBean.getUnno());
		}
		if(checkRealtxnBean.getSaleName()!=null&&!"".equals(checkRealtxnBean.getSaleName())) {
			sql += " and a.saleName=:saleName ";
			if(!"".equals(sql1)) {
				sql1 += " and a.saleName=:saleName ";
			}
			map.put("saleName", checkRealtxnBean.getSaleName());
		}
		if(checkRealtxnBean.getIsMpos()!=null) {
			sql += " and a.ismpos=:ismpos ";
			if(!"".equals(sql1)) {
				sql1 += " and a.ismpos=:ismpos ";
			}
			map.put("ismpos", checkRealtxnBean.getIsMpos());
		}
		if(checkRealtxnBean.getTxnType()!=null) {
			sql += " and a.txnType=:txnType ";
			if(!"".equals(sql1)) {
				sql1 += " and a.txnType=:txnType ";
			}
			map.put("txnType", checkRealtxnBean.getTxnType());
		}
		sql += " and a.mti=:mti ";
		if(!"".equals(sql1)) {
			sql1 += " and a.mti=:mti ";
		}
		map.put("mti", 0);
		if(checkRealtxnBean.getTxnState()!=null) {
			sql += " and a.txnstate=:txnstate ";
			if(!"".equals(sql1)) {
				sql1 += " and a.txnstate=:txnstate ";
			}
			map.put("txnstate", checkRealtxnBean.getTxnState());
		}
		if(checkRealtxnBean.getTxnTime()!=null&&!"".equals(checkRealtxnBean.getTxnTime())) {
			sql += " and a.txntime>=:txntime ";
			if(!"".equals(sql1)) {
				sql1 += " and a.txntime>=:txntime ";
			}
			map.put("txntime", checkRealtxnBean.getTxnTime());
		}
		if(checkRealtxnBean.getTxnTimeEnd()!=null&&!"".equals(checkRealtxnBean.getTxnTimeEnd())) {
			sql += " and a.txntime<=:txntimeend ";
			if(!"".equals(sql1)) {
				sql1 += " and a.txntime<=:txntimeend ";
			}
			map.put("txntimeend", checkRealtxnBean.getTxnTimeEnd());
		}
		if(!"".equals(sql1)) {
			sql = "select nvl(to_char(sum(totalNum),'FM999,999,999,999,990'),0) totalNum,nvl(to_char(sum(totalAmount),'FM999,999,999,999,990.00'),0) totalAmount  from ("+sql+" union "+sql1+")";
		}
		list = checkRealtxnDao.queryObjectsBySqlListMap2(sql, map);
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		String msg = "";
		if(list.size()>0) {
			msg = sdf.format(new Date())+" | 总交易金额：￥ "+list.get(0).get("TOTALAMOUNT")+" | "+"总交易笔数："+list.get(0).get("TOTALNUM");
		}
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("msg", msg);
		list.add(map1);
		return list;
	}

	@Override
	public List<Map<String, Object>> exportRealtxn(CheckRealtxnBean checkRealtxnBean, UserBean user) {
		List<Map<String,Object>> list = null;
		Map<String, Object> map = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String sql = "select nvl(kk.sn,decode(a.ismpos,2,a.tid,'')) sn,a.* from check_realtxn a " +
				" left join bill_terminalinfo kk on a.tid=kk.termid  where a.txnday=:txnday ";
		//销售
		String sql1 = "";
		map.put("txnday", sdf.format(new Date()));
		if("110000".equals(user.getUnNo())) {//总公司权限
		}else if(user.getUseLvl()==2||user.getUseLvl()==1){//业务人员
			sql1 = sql + " and (a.unno in (select unno from sys_unit start with unno in (select unno from bill_agentunitinfo a where a.signuserid = "
				+ "(select busid from  bill_agentsalesinfo where user_id = "+user.getUserID()+" and MAINTAINTYPE != 'D') and unno != '"+user.getUnNo()+"') connect by prior unno = upper_unit)) ";
			sql += " and (a.saleName=( select salename from bill_agentsalesinfo where user_id="+user.getUserID()+" and maintaintype!='D' ) and a.ismpos=0) ";
		}else if(user.getUseLvl()==3) {//商户
			sql += " and a.mid=:mid1";
			map.put("mid1", user.getLoginName());
		}else if(user.getUseLvl()==0) {//代理商管理员
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno1 connect by prior unno = upper_unit) ";
			map.put("unno1", user.getUnNo());
		}
		if(checkRealtxnBean.getMid()!=null&&!"".equals(checkRealtxnBean.getMid())) {
			sql += " and a.mid=:mid ";
			if(!"".equals(sql1)) {
				sql1 += " and a.mid=:mid ";
			}
			map.put("mid", checkRealtxnBean.getMid());
		}
		if(checkRealtxnBean.getRname()!=null&&!"".equals(checkRealtxnBean.getRname())) {
			sql += " and a.rname=:rname ";
			if(!"".equals(sql1)) {
				sql1 += " and a.rname=:rname ";
			}
			map.put("rname", checkRealtxnBean.getRname());
		}
		if(checkRealtxnBean.getOrderID()!=null&&!"".equals(checkRealtxnBean.getOrderID())) {
			sql += " and a.orderid=:orderID ";
			if(!"".equals(sql1)) {
				sql1 += " and a.orderid=:orderID ";
			}
			map.put("orderID", checkRealtxnBean.getOrderID());
		}
		if(checkRealtxnBean.getCardPan()!=null&&!"".equals(checkRealtxnBean.getCardPan())) {
			sql += " and a.cardPan=:cardPan ";
			if(!"".equals(sql1)) {
				sql1 += " and a.cardPan=:cardPan ";
			}
			map.put("cardPan", checkRealtxnBean.getCardPan());
		}
		if(checkRealtxnBean.getTid()!=null&&!"".equals(checkRealtxnBean.getTid())) {
			sql += " and a.tid=:tid ";
			if(!"".equals(sql1)) {
				sql1 += " and a.tid=:tid ";
			}
			map.put("tid", checkRealtxnBean.getTid());
		}
		if(checkRealtxnBean.getUnno()!=null&&!"".equals(checkRealtxnBean.getUnno())) {
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			if(!"".equals(sql1)) {
				sql1 += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			}
			map.put("unno", checkRealtxnBean.getUnno());
		}
		if(checkRealtxnBean.getSaleName()!=null&&!"".equals(checkRealtxnBean.getSaleName())) {
			sql += " and a.saleName=:saleName ";
			if(!"".equals(sql1)) {
				sql1 += " and a.saleName=:saleName ";
			}
			map.put("saleName", checkRealtxnBean.getSaleName());
		}
		if(checkRealtxnBean.getIsMpos()!=null) {
			sql += " and a.ismpos=:ismpos ";
			if(!"".equals(sql1)) {
				sql1 += " and a.ismpos=:ismpos ";
			}
			map.put("ismpos", checkRealtxnBean.getIsMpos());
		}
		if(checkRealtxnBean.getTxnType()!=null) {
			sql += " and a.txnType=:txnType ";
			if(!"".equals(sql1)) {
				sql1 += " and a.txnType=:txnType ";
			}
			map.put("txnType", checkRealtxnBean.getTxnType());
		}
		if(checkRealtxnBean.getMti()!=null) {
			sql += " and a.mti=:mti ";
			if(!"".equals(sql1)) {
				sql1 += " and a.mti=:mti ";
			}
			map.put("mti", checkRealtxnBean.getMti());
		}
		if(checkRealtxnBean.getTxnState()!=null) {
			sql += " and a.txnstate=:txnstate ";
			if(!"".equals(sql1)) {
				sql1 += " and a.txnstate=:txnstate ";
			}
			map.put("txnstate", checkRealtxnBean.getTxnState());
		}
		if(checkRealtxnBean.getTxnTime()!=null&&!"".equals(checkRealtxnBean.getTxnTime())) {
			sql += " and a.txntime>=:txntime ";
			if(!"".equals(sql1)) {
				sql1 += " and a.txntime>=:txntime ";
			}
			map.put("txntime", checkRealtxnBean.getTxnTime());
		}
		if(checkRealtxnBean.getTxnTimeEnd()!=null&&!"".equals(checkRealtxnBean.getTxnTimeEnd())) {
			sql += " and a.txntime<=:txntimeend ";
			if(!"".equals(sql1)) {
				sql1 += " and a.txntime<=:txntimeend ";
			}
			map.put("txntimeend", checkRealtxnBean.getTxnTimeEnd());
		}
		if(!"".equals(sql1)) {
			sql = "select * from ( "+sql+" union "+sql1+") a ";
		}
		sql += " order by a.txntime desc ";
		list = checkRealtxnDao.queryObjectsBySqlListMap2(sql, map);
		return list;
	}
	
}
