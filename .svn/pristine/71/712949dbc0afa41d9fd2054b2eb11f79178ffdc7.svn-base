package com.hrt.biz.check.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hrt.biz.check.dao.CheckRebateDao;
import com.hrt.biz.check.entity.pagebean.CheckRebateBean;
import com.hrt.biz.check.service.CheckRebateService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class CheckRebateServiceImpl implements CheckRebateService {

	private CheckRebateDao checkRebateDao;
	
	public CheckRebateDao getCheckRebateDao() {
		return checkRebateDao;
	}

	public void setCheckRebateDao(CheckRebateDao checkRebateDao) {
		this.checkRebateDao = checkRebateDao;
	}

	/**
	 * 查询活动3/13商户明细
	 */
	public DataGridBean queryRebate3_mer(CheckRebateBean checkRebateBean,UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select c.*,(c.deduct_a3+deduct_a4+deduct_a5)deduct_sum from ( "+
		" select a.*,b.isrebate,b.rebateamt,b.rebatemonth,b.samt,b.num,"+
		" (case when b.deduct_a3 is null and months_between(to_date('"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"','yyyy-mm'),"+
		" to_date(to_char(trunc(a.keyconfirmdate),'yyyyMM'),'yyyy-mm'))>=3 then 20 else b.deduct_a3 end)deduct_a3,"+
		" (case when b.deduct_a4 is null and months_between(to_date('"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"','yyyy-mm'),"+
		" to_date(to_char(trunc(a.keyconfirmdate),'yyyyMM'),'yyyy-mm'))>=4 then 20 else b.deduct_a4 end)deduct_a4,"+
		" (case when b.deduct_a5 is null and months_between(to_date('"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"','yyyy-mm'),"+
		" to_date(to_char(trunc(a.keyconfirmdate),'yyyyMM'),'yyyy-mm'))>=5 then 19 else b.deduct_a5 end)deduct_a5 from ( "+
		" select * from ( "+
		" select bt.unno,nvl((select s.unno from sys_unit s  where s.un_lvl=2  start with s.unno = bt.UNNO  connect "+ 
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),bt.unno)unno1,"+
		" nvl((select s.unno from sys_unit s  where s.un_lvl=1  start with s.unno = bt.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),bt.unno)unno2,"+
		" mt.mid,bt.sn,nvl(bt.machinemodel,(select b.machinemodel from bill_machineinfo b where b.bmaid=mt.bmaid))machinemodel,"+
		" to_char(bt.keyconfirmdate,'yyyy-mm-dd')kdate,to_char(bt.keyconfirmdate,'yyyy-mm')kmonth,bt.keyconfirmdate,"+
		" to_char(bt.usedconfirmdate,'yyyy-mm-dd')udate,bt.rebatetype from bill_terminalinfo bt "+
		" left join bill_merchantterminalinfo mt on bt.termid=mt.tid and bt.sn=mt.sn and mt.maintaintype<>'D')where 1=1 ";
		if(null!=checkRebateBean.getRebateType() && !"".equals(checkRebateBean.getRebateType())){
			map.put("REBATETYPE", checkRebateBean.getRebateType());
			sql += " and rebatetype=:REBATETYPE ";
		}else{
			sql += " and rebatetype in(3,13) ";
		}
		if(null!=checkRebateBean.getKeyDay() || !"".equals(checkRebateBean.getKeyDay()) &&
		   null!=checkRebateBean.getKeyDay1() || !"".equals(checkRebateBean.getKeyDay1())){
			map.put("DAY", checkRebateBean.getKeyDay().replaceAll("-", "").substring(0, 6));
			map.put("DAY1", checkRebateBean.getKeyDay1().replaceAll("-", "").substring(0, 6));
			sql += " and to_char(keyconfirmdate,'yyyymm') between :DAY and :DAY1 ";
		}
		if(null!=checkRebateBean.getUnno() && !"".equals(checkRebateBean.getUnno())){
			map.put("UNNO", checkRebateBean.getUnno().trim());
			sql += " and unno1=:UNNO ";
		}
		if(null!=checkRebateBean.getMid() && !"".equals(checkRebateBean.getMid())){
			map.put("MID", checkRebateBean.getMid().trim());
			sql += " and mid=:MID ";
		}
		if(null!=checkRebateBean.getSn() && !"".equals(checkRebateBean.getSn())){
			map.put("SN", checkRebateBean.getSn().trim());
			sql += " and sn=:SN ";
		}
		sql += " )a left join(select m.unno,m.mid,m.sn,m.deduct_a3,m.deduct_a4,m.deduct_a5,m.deduct_sum,m.isrebate,m.rebateamt,"+
		" m.rebatemonth,m.samt,m.num from check_mersumactiva m where 1=1 ";
		if(null!=checkRebateBean.getRebateType() && !"".equals(checkRebateBean.getRebateType())){
			sql += " and m.rebatetype=:REBATETYPE ";
		}else{
			sql += " and m.rebatetype in(3,13) ";
		}
		sql += " and m.txnmonth='"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"' "+
		" )b on a.unno=b.unno and a.mid=b.mid and a.sn=b.sn "+
		" )c where 1=1 ";
		if(null!=checkRebateBean.getTxnDay1() && !"".equals(checkRebateBean.getTxnDay1())){
			map.put("REBATEMONTH", checkRebateBean.getTxnDay1().replaceAll("-", "").replaceAll("-", "").substring(0, 6));
			sql += " and c.rebatemonth=:REBATEMONTH ";
		}
		String sqlCount="select count(*) from ("+sql+")";
		Integer count=checkRebateDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list =checkRebateDao.queryObjectsBySqlList2(sql, map, checkRebateBean.getPage(), checkRebateBean.getRows());
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}
	/**
	 * 导出活动3/13商户明细
	 */
	public List<Map<String, Object>> exportRebate3_mer(CheckRebateBean checkRebateBean,UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select c.*,(c.deduct_a3+deduct_a4+deduct_a5)deduct_sum from ( "+
		" select a.*,b.isrebate,b.rebateamt,b.rebatemonth,b.samt,b.num,"+
		" (case when b.deduct_a3 is null and months_between(to_date('"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"','yyyy-mm'),"+
		" to_date(to_char(trunc(a.keyconfirmdate),'yyyyMM'),'yyyy-mm'))>=3 then 20 else b.deduct_a3 end)deduct_a3,"+
		" (case when b.deduct_a4 is null and months_between(to_date('"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"','yyyy-mm'),"+
		" to_date(to_char(trunc(a.keyconfirmdate),'yyyyMM'),'yyyy-mm'))>=4 then 20 else b.deduct_a4 end)deduct_a4,"+
		" (case when b.deduct_a5 is null and months_between(to_date('"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"','yyyy-mm'),"+
		" to_date(to_char(trunc(a.keyconfirmdate),'yyyyMM'),'yyyy-mm'))>=5 then 19 else b.deduct_a5 end)deduct_a5 from ( "+
		" select * from ( "+
		" select bt.unno,nvl((select s.unno from sys_unit s  where s.un_lvl=2  start with s.unno = bt.UNNO  connect "+ 
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),bt.unno)unno1,"+
		" nvl((select s.unno from sys_unit s  where s.un_lvl=1  start with s.unno = bt.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),bt.unno)unno2,"+
		" mt.mid,bt.sn,nvl(bt.machinemodel,(select b.machinemodel from bill_machineinfo b where b.bmaid=mt.bmaid))machinemodel,"+
		" to_char(bt.keyconfirmdate,'yyyy-mm-dd')kdate,to_char(bt.keyconfirmdate,'yyyy-mm')kmonth,bt.keyconfirmdate,"+
		" to_char(bt.usedconfirmdate,'yyyy-mm-dd')udate,bt.rebatetype from bill_terminalinfo bt "+
		" left join bill_merchantterminalinfo mt on bt.termid=mt.tid and bt.sn=mt.sn and mt.maintaintype<>'D')where 1=1 ";
		if(null!=checkRebateBean.getRebateType() && !"".equals(checkRebateBean.getRebateType())){
			map.put("REBATETYPE", checkRebateBean.getRebateType());
			sql += " and rebatetype=:REBATETYPE ";
		}else{
			sql += " and rebatetype in(3,13) ";
		}
		if(null!=checkRebateBean.getKeyDay() || !"".equals(checkRebateBean.getKeyDay()) &&
		   null!=checkRebateBean.getKeyDay1() || !"".equals(checkRebateBean.getKeyDay1())){
			map.put("DAY", checkRebateBean.getKeyDay().replaceAll("-", "").substring(0, 6));
			map.put("DAY1", checkRebateBean.getKeyDay1().replaceAll("-", "").substring(0, 6));
			sql += " and to_char(keyconfirmdate,'yyyymm') between :DAY and :DAY1 ";
		}
		if(null!=checkRebateBean.getUnno() && !"".equals(checkRebateBean.getUnno())){
			map.put("UNNO", checkRebateBean.getUnno().trim());
			sql += " and unno1=:UNNO ";
		}
		if(null!=checkRebateBean.getMid() && !"".equals(checkRebateBean.getMid())){
			map.put("MID", checkRebateBean.getMid().trim());
			sql += " and mid=:MID ";
		}
		if(null!=checkRebateBean.getSn() && !"".equals(checkRebateBean.getSn())){
			map.put("SN", checkRebateBean.getSn().trim());
			sql += " and sn=:SN ";
		}
		sql += " )a left join(select m.unno,m.mid,m.sn,m.deduct_a3,m.deduct_a4,m.deduct_a5,m.deduct_sum,m.isrebate,m.rebateamt,"+
		" m.rebatemonth,m.samt,m.num from check_mersumactiva m where 1=1 ";
		if(null!=checkRebateBean.getRebateType() && !"".equals(checkRebateBean.getRebateType())){
			sql += " and m.rebatetype=:REBATETYPE ";
		}else{
			sql += " and m.rebatetype in(3,13) ";
		}
		sql += " and m.txnmonth='"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"' "+
		" )b on a.unno=b.unno and a.mid=b.mid and a.sn=b.sn "+
		" )c where 1=1 ";
		if(null!=checkRebateBean.getTxnDay1() && !"".equals(checkRebateBean.getTxnDay1())){
			map.put("REBATEMONTH", checkRebateBean.getTxnDay1().replaceAll("-", "").substring(0, 6));
			sql += " and c.rebatemonth=:REBATEMONTH ";
		}
		List<Map<String,Object>> list =checkRebateDao.executeSql2(sql, map);
		return list;
	}
	/**
	 * 查询活动3/13机构汇总
	 */
	public DataGridBean queryRebate3_unno(CheckRebateBean checkRebateBean,UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select a.* from ( "+
		" select u.unno,nvl((select s.unno from sys_unit s  where s.un_lvl=2  start with s.unno = u.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno1,"+
		" nvl((select s.unno from sys_unit s  where s.un_lvl=1  start with s.unno = u.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno2,"+
		" u.deduct_amt,u.deduct_sn,u.rebateamt,u.rebatenum,u.rebatetype from check_unnosumactiva u " +
		" where u.txnmonth='"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"' ";
		if(null!=checkRebateBean.getRebateType() && !"".equals(checkRebateBean.getRebateType())){
			map.put("REBATETYPE", checkRebateBean.getRebateType());
			sql += " and u.rebatetype=:REBATETYPE ";
		}else{
			sql += " and u.rebatetype in(3,13) ";
		}
		sql +=")a where 1=1 ";
		if(null!=checkRebateBean.getUnno() && !"".equals(checkRebateBean.getUnno())){
			map.put("UNNO", checkRebateBean.getUnno().trim());
			sql += " and a.unno1=:UNNO ";
		}
		String sqlCount="select count(*) from ("+sql+")";
		Integer count=checkRebateDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list =checkRebateDao.queryObjectsBySqlList2(sql, map, checkRebateBean.getPage(), checkRebateBean.getRows());
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}
	/**
	 * 导出活动3/13机构汇总
	 */
	public List<Map<String, Object>> exportRebate3_unno(CheckRebateBean checkRebateBean,UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select a.* from ( "+
		" select u.unno,nvl((select s.unno from sys_unit s  where s.un_lvl=2  start with s.unno = u.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno1,"+
		" nvl((select s.unno from sys_unit s  where s.un_lvl=1  start with s.unno = u.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno2,"+
		" u.deduct_amt,u.deduct_sn,u.rebateamt,u.rebatenum,u.rebatetype from check_unnosumactiva u " +
		" where u.txnmonth='"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"' ";
		if(null!=checkRebateBean.getRebateType() && !"".equals(checkRebateBean.getRebateType())){
			map.put("REBATETYPE", checkRebateBean.getRebateType());
			sql += " and u.rebatetype=:REBATETYPE ";
		}else{
			sql += " and u.rebatetype in(3,13) ";
		}
		sql +=")a where 1=1 ";
		if(null!=checkRebateBean.getUnno() && !"".equals(checkRebateBean.getUnno())){
			map.put("UNNO", checkRebateBean.getUnno().trim());
			sql += " and a.unno1=:UNNO ";
		}
		List<Map<String,Object>> list =checkRebateDao.executeSql2(sql, map);
		return list;
	}
	/**
	 * 查询活动9商户明细
	 */
	public DataGridBean queryRebate9_mer(CheckRebateBean checkRebateBean,UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select a.*,nvl(b.activa_type,(select c.activa_type from ( "+
		" select t.*,row_number() over(partition by t.unno,t.mid,t.sn order by t.txnmonth desc) rn from ( "+
		" select m.unno,m.mid,m.sn,m.activa_type,m.txnmonth from check_mersumactiva m "+
		" where m.rebatetype=9 and m.txnmonth<'"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"' " +
		" order by m.txnmonth desc)t)c where c.rn=1  " +
		" and c.unno=a.unno and c.mid=a.mid and c.sn=a.sn))activa_type,b.rebateamt,b.rebatemonth,b.samt,b.num from ( "+
		" select * from ( "+
		" select bt.unno,nvl((select s.unno from sys_unit s  where s.un_lvl=2  start with s.unno = bt.UNNO  connect "+ 
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),bt.unno)unno1,"+
		" nvl((select s.unno from sys_unit s  where s.un_lvl=1  start with s.unno = bt.UNNO  connect "+ 
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),bt.unno)unno2,"+
		" mt.mid,bt.sn,nvl(bt.machinemodel,(select b.machinemodel from bill_machineinfo b where b.bmaid=mt.bmaid))machinemodel,"+
		" (case when (bt.sn like 'HYB1%' or bt.sn like 'HYB4%' or bt.sn like 'HYB5%' or bt.sn like 'HYB0%' or bt.sn like 'HYB7%' "+
		" or bt.sn like 'HYB8%' or bt.sn like 'AACA%' or bt.sn like 'BBCA%' or bt.sn like 'HYB2%' or bt.sn like 'HYB3%') then '1' else '2' end)sn_type,"+
		" to_char(bt.keyconfirmdate,'yyyy-mm-dd')kdate,to_char(bt.keyconfirmdate,'yyyy-mm')kmonth,bt.keyconfirmdate,"+
		" to_char(bt.usedconfirmdate,'yyyy-mm-dd')udate,bt.rebatetype from bill_terminalinfo bt "+
		" left join bill_merchantterminalinfo mt on bt.termid=mt.tid and bt.sn=mt.sn and mt.maintaintype<>'D')where rebatetype=9 ";
		if(null!=checkRebateBean.getKeyDay() || !"".equals(checkRebateBean.getKeyDay()) &&
		   null!=checkRebateBean.getKeyDay1() || !"".equals(checkRebateBean.getKeyDay1())){
			map.put("DAY", checkRebateBean.getKeyDay().replaceAll("-", "").substring(0, 6));
			map.put("DAY1", checkRebateBean.getKeyDay1().replaceAll("-", "").substring(0, 6));
			sql += " and to_char(keyconfirmdate,'yyyymm') between :DAY and :DAY1 ";
		}
		if(null!=checkRebateBean.getUnno() && !"".equals(checkRebateBean.getUnno())){
			map.put("UNNO", checkRebateBean.getUnno().trim());
			sql += " and unno1=:UNNO ";
		}
		if(null!=checkRebateBean.getMid() && !"".equals(checkRebateBean.getMid())){
			map.put("MID", checkRebateBean.getMid().trim());
			sql += " and mid=:MID ";
		}
		if(null!=checkRebateBean.getSn() && !"".equals(checkRebateBean.getSn())){
			map.put("SN", checkRebateBean.getSn().trim());
			sql += " and sn=:SN ";
		}
		sql += " )a left join(select m.unno,m.mid,m.sn,m.activa_type,m.rebateamt,m.rebatemonth,m.samt,m.num "+
		" from check_mersumactiva m where m.txnmonth='"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"' and m.rebatetype=9 "+
		" )b on a.unno=b.unno and a.mid=b.mid and a.sn=b.sn";
		String sqlCount="select count(*) from ("+sql+")";
		Integer count=checkRebateDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list =checkRebateDao.queryObjectsBySqlList2(sql, map, checkRebateBean.getPage(), checkRebateBean.getRows());
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}
	/**
	 * 导出活动9商户明细
	 */
	public List<Map<String,Object>> exportRebate9_mer(CheckRebateBean checkRebateBean,UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select a.*,nvl(b.activa_type,(select c.activa_type from ( "+
		" select t.*,row_number() over(partition by t.unno,t.mid,t.sn order by t.txnmonth desc) rn from ( "+
		" select m.unno,m.mid,m.sn,m.activa_type,m.txnmonth from check_mersumactiva m "+
		" where m.rebatetype=9 and m.txnmonth<'"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"' " +
		" order by m.txnmonth desc)t)c where c.rn=1  " +
		" and c.unno=a.unno and c.mid=a.mid and c.sn=a.sn))activa_type,b.rebateamt,b.rebatemonth,b.samt,b.num from ( "+
		" select * from ( "+
		" select bt.unno,nvl((select s.unno from sys_unit s  where s.un_lvl=2  start with s.unno = bt.UNNO  connect "+ 
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),bt.unno)unno1,"+
		" nvl((select s.unno from sys_unit s  where s.un_lvl=1  start with s.unno = bt.UNNO  connect "+ 
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),bt.unno)unno2,"+
		" mt.mid,bt.sn,nvl(bt.machinemodel,(select b.machinemodel from bill_machineinfo b where b.bmaid=mt.bmaid))machinemodel,"+
		" (case when (bt.sn like 'HYB1%' or bt.sn like 'HYB4%' or bt.sn like 'HYB5%' or bt.sn like 'HYB0%' or bt.sn like 'HYB7%' "+
		" or bt.sn like 'HYB8%' or bt.sn like 'AACA%' or bt.sn like 'BBCA%' or bt.sn like 'HYB2%' or bt.sn like 'HYB3%') then '1' else '2' end)sn_type,"+
		" to_char(bt.keyconfirmdate,'yyyy-mm-dd')kdate,to_char(bt.keyconfirmdate,'yyyy-mm')kmonth,bt.keyconfirmdate,"+
		" to_char(bt.usedconfirmdate,'yyyy-mm-dd')udate,bt.rebatetype from bill_terminalinfo bt "+
		" left join bill_merchantterminalinfo mt on bt.termid=mt.tid and bt.sn=mt.sn and mt.maintaintype<>'D')where rebatetype=9 ";
		if(null!=checkRebateBean.getKeyDay() || !"".equals(checkRebateBean.getKeyDay()) &&
		   null!=checkRebateBean.getKeyDay1() || !"".equals(checkRebateBean.getKeyDay1())){
			map.put("DAY", checkRebateBean.getKeyDay().replaceAll("-", "").substring(0, 6));
			map.put("DAY1", checkRebateBean.getKeyDay1().replaceAll("-", "").substring(0, 6));
			sql += " and to_char(keyconfirmdate,'yyyymm') between :DAY and :DAY1 ";
		}
		if(null!=checkRebateBean.getUnno() && !"".equals(checkRebateBean.getUnno())){
			map.put("UNNO", checkRebateBean.getUnno().trim());
			sql += " and unno1=:UNNO ";
		}
		if(null!=checkRebateBean.getMid() && !"".equals(checkRebateBean.getMid())){
			map.put("MID", checkRebateBean.getMid().trim());
			sql += " and mid=:MID ";
		}
		if(null!=checkRebateBean.getSn() && !"".equals(checkRebateBean.getSn())){
			map.put("SN", checkRebateBean.getSn().trim());
			sql += " and sn=:SN ";
		}
		sql += " )a left join(select m.unno,m.mid,m.sn,m.activa_type,m.rebateamt,m.rebatemonth,m.samt,m.num "+
		" from check_mersumactiva m where m.txnmonth='"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"' and m.rebatetype=9 "+
		" )b on a.unno=b.unno and a.mid=b.mid and a.sn=b.sn";
		List<Map<String,Object>> list =checkRebateDao.executeSql2(sql, map);
		return list;
	}
	/**
	 * 查询活动9机构汇总
	 */
	public DataGridBean queryRebate9_unno(CheckRebateBean checkRebateBean,UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select a.* from ( "+
		" select u.unno,nvl((select s.unno from sys_unit s  where s.un_lvl=2  start with s.unno = u.UNNO  connect "+ 
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno1,"+
		" nvl((select s.unno from sys_unit s  where s.un_lvl=1  start with s.unno = u.UNNO  connect "+ 
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno2,"+
		" u.rebateamt,u.rebatenum,u.rebatetype from check_unnosumactiva u " +
		" where u.txnmonth='"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"' and u.rebatetype=9 and u.sum_sn=0 " +
		" )a where 1=1 ";
		if(null!=checkRebateBean.getUnno() && !"".equals(checkRebateBean.getUnno())){
			map.put("UNNO", checkRebateBean.getUnno().trim());
			sql += " and a.unno1=:UNNO ";
		}
		String sqlCount="select count(*) from ("+sql+")";
		Integer count=checkRebateDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list =checkRebateDao.queryObjectsBySqlList2(sql, map, checkRebateBean.getPage(), checkRebateBean.getRows());
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}
	/**
	 * 导出活动9机构汇总
	 */
	public List<Map<String, Object>> exportRebate9_unno(CheckRebateBean checkRebateBean,UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select a.* from ( "+
		" select u.unno,nvl((select s.unno from sys_unit s  where s.un_lvl=2  start with s.unno = u.UNNO  connect "+ 
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno1,"+
		" nvl((select s.unno from sys_unit s  where s.un_lvl=1  start with s.unno = u.UNNO  connect "+ 
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno2,"+
		" u.rebateamt,u.rebatenum,u.rebatetype from check_unnosumactiva u " +
		" where u.txnmonth='"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"' and u.rebatetype=9 and u.sum_sn=0 " +
		" )a where 1=1 ";
		if(null!=checkRebateBean.getUnno() && !"".equals(checkRebateBean.getUnno())){
			map.put("UNNO", checkRebateBean.getUnno().trim());
			sql += " and a.unno1=:UNNO ";
		}
		List<Map<String,Object>> list =checkRebateDao.executeSql2(sql, map);
		return list;
	}
	/**
	 * 查询活动9机构汇总-补差价
	 */
	public DataGridBean queryRebate9_unno1(CheckRebateBean checkRebateBean,UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("TXNMONTH", checkRebateBean.getTxnDay().replaceAll("-", "").substring(0,6));
		String sql="select a.* from ( "+
		" select u.unno,nvl((select s.unno from sys_unit s  where s.un_lvl=2  start with s.unno = u.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno1,"+
		" nvl((select s.unno from sys_unit s  where s.un_lvl=1  start with s.unno = u.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno2,u.sum_sn,u.activa_sn,u.activa_rate,u.compensa_amt,"+
		" u.rebateamt,u.rebatetype from check_unnosumactiva u where u.txnmonth=:TXNMONTH and u.rebatetype=9 and u.sum_sn>0 "+
		" )a where 1=1 ";
		if(null!=checkRebateBean.getUnno() && !"".equals(checkRebateBean.getUnno())){
			map.put("UNNO", checkRebateBean.getUnno().trim());
			sql += " and a.unno1=:UNNO ";
		}
		String sqlCount="select count(*) from ("+sql+")";
		Integer count=checkRebateDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list =checkRebateDao.queryObjectsBySqlList2(sql, map, checkRebateBean.getPage(), checkRebateBean.getRows());
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}
	/**
	 * 导出活动9机构汇总-补差价
	 */
	public List<Map<String,Object>> exportRebate9_unno1(CheckRebateBean checkRebateBean,UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("TXNMONTH", checkRebateBean.getTxnDay().replaceAll("-", "").substring(0,6));
		String sql="select a.* from ( "+
		" select u.unno,nvl((select s.unno from sys_unit s  where s.un_lvl=2  start with s.unno = u.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno1,"+
		" nvl((select s.unno from sys_unit s  where s.un_lvl=1  start with s.unno = u.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno2,u.sum_sn,u.activa_sn,u.activa_rate,u.compensa_amt,"+
		" u.rebateamt,u.rebatetype from check_unnosumactiva u where u.txnmonth=:TXNMONTH and u.rebatetype=9 and u.sum_sn>0 "+
		" )a where 1=1 ";
		if(null!=checkRebateBean.getUnno() && !"".equals(checkRebateBean.getUnno())){
			map.put("UNNO", checkRebateBean.getUnno().trim());
			sql += " and a.unno1=:UNNO ";
		}
		List<Map<String,Object>> list =checkRebateDao.executeSql2(sql, map);
		return list;
	}
	/**
	 * 查询活动11商户明细
	 */
	public DataGridBean queryRebate11_mer(CheckRebateBean checkRebateBean,UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select c.*,(c.deduct_a3+deduct_a4+deduct_a5)deduct_sum from ( "+
		" select a.*,b.isrebate,b.rebateamt,b.rebatemonth,b.samt,b.num,"+
		" (case when b.deduct_a3 is null and months_between(to_date('"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"','yyyy-mm'),"+
		" to_date(to_char(trunc(a.keyconfirmdate),'yyyyMM'),'yyyy-mm'))>=3 then 20 else b.deduct_a3 end)deduct_a3,"+
		" (case when b.deduct_a4 is null and months_between(to_date('"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"','yyyy-mm'),"+
		" to_date(to_char(trunc(a.keyconfirmdate),'yyyyMM'),'yyyy-mm'))>=4 then 20 else b.deduct_a4 end)deduct_a4,"+
		" (case when b.deduct_a5 is null and months_between(to_date('"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"','yyyy-mm'),"+
		" to_date(to_char(trunc(a.keyconfirmdate),'yyyyMM'),'yyyy-mm'))>=5 then 19 else b.deduct_a5 end)deduct_a5,"+
		" b.rebate1_amt,b.rebate1_month,b.rebate2_amt,b.rebate2_month from ( "+
		" select * from ( "+
		" select bt.unno,nvl((select s.unno from sys_unit s  where s.un_lvl=2  start with s.unno = bt.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),bt.unno)unno1,"+
		" nvl((select s.unno from sys_unit s  where s.un_lvl=1  start with s.unno = bt.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),bt.unno)unno2,"+
		" mt.mid,bt.sn,nvl(bt.machinemodel,(select b.machinemodel from bill_machineinfo b where b.bmaid=mt.bmaid))machinemodel,"+
		" (case when (bt.sn like 'HYB1%' or bt.sn like 'HYB4%' or bt.sn like 'HYB5%' or bt.sn like 'HYB0%' or bt.sn like 'HYB7%' "+
		" or bt.sn like 'HYB8%' or bt.sn like 'AACA%' or bt.sn like 'BBCA%' or bt.sn like 'HYB2%' or bt.sn like 'HYB3%') "+
		" then '1' else '2' end)sn_type,to_char(bt.keyconfirmdate,'yyyy-mm-dd')kdate,to_char(bt.keyconfirmdate,'yyyy-mm')kmonth,"+
		" bt.keyconfirmdate,to_char(bt.usedconfirmdate,'yyyy-mm-dd')udate,bt.rebatetype from bill_terminalinfo bt "+
		" left join bill_merchantterminalinfo mt on bt.termid=mt.tid and bt.sn=mt.sn and mt.maintaintype<>'D')where rebatetype=11 ";
		if(null!=checkRebateBean.getKeyDay() || !"".equals(checkRebateBean.getKeyDay()) &&
		   null!=checkRebateBean.getKeyDay1() || !"".equals(checkRebateBean.getKeyDay1())){
			map.put("DAY", checkRebateBean.getKeyDay().replaceAll("-", "").substring(0, 6));
			map.put("DAY1", checkRebateBean.getKeyDay1().replaceAll("-", "").substring(0, 6));
			sql += " and to_char(keyconfirmdate,'yyyymm') between :DAY and :DAY1 ";
		}
		if(null!=checkRebateBean.getUnno() && !"".equals(checkRebateBean.getUnno())){
			map.put("UNNO", checkRebateBean.getUnno().trim());
			sql += " and unno1=:UNNO ";
		}
		if(null!=checkRebateBean.getMid() && !"".equals(checkRebateBean.getMid())){
			map.put("MID", checkRebateBean.getMid().trim());
			sql += " and mid=:MID ";
		}
		if(null!=checkRebateBean.getSn() && !"".equals(checkRebateBean.getSn())){
			map.put("SN", checkRebateBean.getSn().trim());
			sql += " and sn=:SN ";
		}
		sql += " )a left join(select m.unno,m.mid,m.sn,m.deduct_a3,m.deduct_a4,m.deduct_a5,m.deduct_sum,m.isrebate,"+
		" m.rebateamt,m.rebatemonth,m.rebate1_amt,m.rebate1_month,m.rebate2_amt,m.rebate2_month,"+
		" m.samt,m.num from check_mersumactiva m where m.rebatetype=11 " +
		" and m.txnmonth='"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"' "+
		" )b on a.unno=b.unno and a.mid=b.mid and a.sn=b.sn "+
		" )c where 1=1 ";
		if(null!=checkRebateBean.getTxnDay1() && !"".equals(checkRebateBean.getTxnDay1())){
			map.put("REBATEMONTH", checkRebateBean.getTxnDay1().replaceAll("-", "").substring(0, 6));
			sql += " and(c.rebatemonth=:REBATEMONTH or c.REBATE1_MONTH=:REBATEMONTH or c.REBATE2_MONTH=:REBATEMONTH) ";
		}
		String sqlCount="select count(*) from ("+sql+")";
		Integer count=checkRebateDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list =checkRebateDao.queryObjectsBySqlList2(sql, map, checkRebateBean.getPage(), checkRebateBean.getRows());
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}
	/**
	 * 导出活动11商户明细
	 */
	public List<Map<String, Object>> exportRebate11_mer(CheckRebateBean checkRebateBean,UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select c.*,(c.deduct_a3+deduct_a4+deduct_a5)deduct_sum from ( "+
		" select a.*,b.isrebate,b.rebateamt,b.rebatemonth,b.samt,b.num,"+
		" (case when b.deduct_a3 is null and months_between(to_date('"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"','yyyy-mm'),"+
		" to_date(to_char(trunc(a.keyconfirmdate),'yyyyMM'),'yyyy-mm'))>=3 then 20 else b.deduct_a3 end)deduct_a3,"+
		" (case when b.deduct_a4 is null and months_between(to_date('"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"','yyyy-mm'),"+
		" to_date(to_char(trunc(a.keyconfirmdate),'yyyyMM'),'yyyy-mm'))>=4 then 20 else b.deduct_a4 end)deduct_a4,"+
		" (case when b.deduct_a5 is null and months_between(to_date('"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"','yyyy-mm'),"+
		" to_date(to_char(trunc(a.keyconfirmdate),'yyyyMM'),'yyyy-mm'))>=5 then 19 else b.deduct_a5 end)deduct_a5,"+
		" b.rebate1_amt,b.rebate1_month,b.rebate2_amt,b.rebate2_month from ( "+
		" select * from ( "+
		" select bt.unno,nvl((select s.unno from sys_unit s  where s.un_lvl=2  start with s.unno = bt.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),bt.unno)unno1,"+
		" nvl((select s.unno from sys_unit s  where s.un_lvl=1  start with s.unno = bt.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),bt.unno)unno2,"+
		" mt.mid,bt.sn,nvl(bt.machinemodel,(select b.machinemodel from bill_machineinfo b where b.bmaid=mt.bmaid))machinemodel,"+
		" (case when (bt.sn like 'HYB1%' or bt.sn like 'HYB4%' or bt.sn like 'HYB5%' or bt.sn like 'HYB0%' or bt.sn like 'HYB7%' "+
		" or bt.sn like 'HYB8%' or bt.sn like 'AACA%' or bt.sn like 'BBCA%' or bt.sn like 'HYB2%' or bt.sn like 'HYB3%') "+
		" then '1' else '2' end)sn_type,to_char(bt.keyconfirmdate,'yyyy-mm-dd')kdate,to_char(bt.keyconfirmdate,'yyyy-mm')kmonth,"+
		" bt.keyconfirmdate,to_char(bt.usedconfirmdate,'yyyy-mm-dd')udate,bt.rebatetype from bill_terminalinfo bt "+
		" left join bill_merchantterminalinfo mt on bt.termid=mt.tid and bt.sn=mt.sn and mt.maintaintype<>'D')where rebatetype=11 ";
		if(null!=checkRebateBean.getKeyDay() || !"".equals(checkRebateBean.getKeyDay()) &&
		   null!=checkRebateBean.getKeyDay1() || !"".equals(checkRebateBean.getKeyDay1())){
			map.put("DAY", checkRebateBean.getKeyDay().replaceAll("-", "").substring(0, 6));
			map.put("DAY1", checkRebateBean.getKeyDay1().replaceAll("-", "").substring(0, 6));
			sql += " and to_char(keyconfirmdate,'yyyymm') between :DAY and :DAY1 ";
		}
		if(null!=checkRebateBean.getUnno() && !"".equals(checkRebateBean.getUnno())){
			map.put("UNNO", checkRebateBean.getUnno().trim());
			sql += " and unno1=:UNNO ";
		}
		if(null!=checkRebateBean.getMid() && !"".equals(checkRebateBean.getMid())){
			map.put("MID", checkRebateBean.getMid().trim());
			sql += " and mid=:MID ";
		}
		if(null!=checkRebateBean.getSn() && !"".equals(checkRebateBean.getSn())){
			map.put("SN", checkRebateBean.getSn().trim());
			sql += " and sn=:SN ";
		}
		sql += " )a left join(select m.unno,m.mid,m.sn,m.deduct_a3,m.deduct_a4,m.deduct_a5,m.deduct_sum,m.isrebate,"+
		" m.rebateamt,m.rebatemonth,m.rebate1_amt,m.rebate1_month,m.rebate2_amt,m.rebate2_month,"+
		" m.samt,m.num from check_mersumactiva m where m.rebatetype=11 " +
		" and m.txnmonth='"+checkRebateBean.getTxnDay().replaceAll("-", "").substring(0, 6)+"' "+
		" )b on a.unno=b.unno and a.mid=b.mid and a.sn=b.sn "+
		" )c where 1=1 ";
		if(null!=checkRebateBean.getTxnDay1() && !"".equals(checkRebateBean.getTxnDay1())){
			map.put("REBATEMONTH", checkRebateBean.getTxnDay1().replaceAll("-", "").substring(0, 6));
			sql += " and(c.rebatemonth=:REBATEMONTH or c.REBATE1_MONTH=:REBATEMONTH or c.REBATE2_MONTH=:REBATEMONTH) ";
		}
		List<Map<String,Object>> list =checkRebateDao.executeSql2(sql, map);
		return list;
	}
	/**
	 * 查询活动11机构汇总
	 */
	public DataGridBean queryRebate11_unno(CheckRebateBean checkRebateBean,UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("TXNMONTH", checkRebateBean.getTxnDay().replaceAll("-", "").subSequence(0, 6));
		String sql="select a.* from ( "+
		" select u.unno,nvl((select s.unno from sys_unit s  where s.un_lvl=2  start with s.unno = u.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno1,"+
		" nvl((select s.unno from sys_unit s  where s.un_lvl=1  start with s.unno = u.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno2,u.deduct_amt,u.deduct_sn,"+
		" u.rebateamt,u.rebatenum,u.rebatetype from check_unnosumactiva u " +
		" where u.txnmonth=:TXNMONTH and u.rebatetype=11 "+
		" )a where 1=1 ";
		if(null!=checkRebateBean.getUnno() && !"".equals(checkRebateBean.getUnno())){
			map.put("UNNO", checkRebateBean.getUnno().trim());
			sql += " and a.unno1=:UNNO ";
		}
		String sqlCount="select count(*) from ("+sql+")";
		Integer count=checkRebateDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list =checkRebateDao.queryObjectsBySqlList2(sql, map, checkRebateBean.getPage(), checkRebateBean.getRows());
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}
	/**
	 * 导出活动11机构汇总
	 */
	public List<Map<String, Object>> exportRebate11_unno(CheckRebateBean checkRebateBean,UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("TXNMONTH", checkRebateBean.getTxnDay().replaceAll("-", "").subSequence(0, 6));
		String sql="select a.* from ( "+
		" select u.unno,nvl((select s.unno from sys_unit s  where s.un_lvl=2  start with s.unno = u.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno1,"+
		" nvl((select s.unno from sys_unit s  where s.un_lvl=1  start with s.unno = u.UNNO  connect "+
		" by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),u.unno)unno2,u.deduct_amt,u.deduct_sn,"+
		" u.rebateamt,u.rebatenum,u.rebatetype from check_unnosumactiva u " +
		" where u.txnmonth=:TXNMONTH and u.rebatetype=11 "+
		" )a where 1=1 ";
		if(null!=checkRebateBean.getUnno() && !"".equals(checkRebateBean.getUnno())){
			map.put("UNNO", checkRebateBean.getUnno().trim());
			sql += " and a.unno1=:UNNO ";
		}
		List<Map<String,Object>> list =checkRebateDao.executeSql2(sql, map);
		return list;
	}
	
	
	@Override
	public DataGridBean queryRebate2Detail(CheckRebateBean checkRebateBean) {
		DataGridBean dgb = new DataGridBean();
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="";
		String sqlCount ="";
		sql = "select (select unno from sys_unit b where b.un_lvl=2 start with b.unno=a.unno connect by prior upper_unit = unno) yidai,"
			+ "(select unno from sys_unit b where b.un_lvl=1 start with b.unno=a.unno connect by prior upper_unit = unno) yunying,"
			+ "a.mid,a.sn,a.machinemodel,a.sn_type,a.keyconfirmdate," +
			" to_char(a.keyconfirmdate,'yyyymm') keyconfirmmonth,a.usedconfirmdate,"
			+ "a.rebateamt,a.rebatetype,a.isrebate,a.rebatemonth,a.samt,a.num,a.unno from check_MerSumActiva a "
			+ "where a.rebatetype = 2";
		if(checkRebateBean.getUnno()!=null&&!"".equals(checkRebateBean.getUnno())){
			sql += " and a.unno in (select unno from sys_unit start with unno = :unno connect by prior unno = upper_unit)";
			map.put("unno", checkRebateBean.getUnno());
		}
		if(checkRebateBean.getMid()!=null&&!"".equals(checkRebateBean.getMid())){
			sql += " and a.mid=:mid";
			map.put("mid", checkRebateBean.getMid());
		}
		if(checkRebateBean.getSn()!=null&&!"".equals(checkRebateBean.getSn())){
			sql += " and a.sn=:sn";
			map.put("sn", checkRebateBean.getSn());
		}
		if(checkRebateBean.getTxnDay()!=null&&!"".equals(checkRebateBean.getTxnDay())){
			sql += " and a.txnmonth=:txnmonth";
			map.put("txnmonth", checkRebateBean.getTxnDay().replace("-", "").substring(0, 6));
		}
		sqlCount = "select count(1) from ("+sql+")";
		List<Map<String,Object>> list = checkRebateDao.queryObjectsBySqlList2(sql, map, checkRebateBean.getPage(), checkRebateBean.getRows());
		BigDecimal counts = checkRebateDao.querysqlCounts(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}

	@Override
	public DataGridBean queryRebate2Summary(CheckRebateBean checkRebateBean) {
		DataGridBean dgb = new DataGridBean();
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="";
		String sqlCount ="";
		sql = "select (select unno from sys_unit b where b.un_lvl=2 start with b.unno=a.unno connect by prior upper_unit = unno) yidai,"
				+ "(select unno from sys_unit b where b.un_lvl=1 start with b.unno=a.unno connect by prior upper_unit = unno) yunying,"
				+ "a.rebateamt,a.rebatenum,a.rebatetype from check_UnnoSumActiva a where a.rebatetype = 2";
		if(checkRebateBean.getUnno()!=null&&!"".equals(checkRebateBean.getUnno())){
			sql += " and a.unno in (select unno from sys_unit start with unno = :unno connect by prior unno = upper_unit)";
			map.put("unno", checkRebateBean.getUnno());
		}
		if(checkRebateBean.getTxnDay()!=null&&!"".equals(checkRebateBean.getTxnDay())){
			sql += " and a.txnmonth=:txnmonth";
			map.put("txnmonth", checkRebateBean.getTxnDay().replace("-", "").substring(0, 6));
		}
		sqlCount = "select count(1) from ("+sql+")";
		List<Map<String,Object>> list = checkRebateDao.queryObjectsBySqlList2(sql, map, checkRebateBean.getPage(), checkRebateBean.getRows());
		BigDecimal counts = checkRebateDao.querysqlCounts(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}
	@Override
	public DataGridBean queryRebate10Detail(CheckRebateBean checkRebateBean) {
		DataGridBean dgb = new DataGridBean();
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="";
		String sqlCount ="";
		sql = "select (select unno from sys_unit b where b.un_lvl=2 start with b.unno=a.unno connect by prior upper_unit = unno) yidai,"
				+ "(select unno from sys_unit b where b.un_lvl=1 start with b.unno=a.unno connect by prior upper_unit = unno) yunying,"
				+ "a.mid,a.sn,a.machinemodel,a.sn_type,a.keyconfirmdate,"
				+ " to_char(a.usedconfirmdate,'yyyymm') usedconfirmmonth,"
				+ "a.usedconfirmdate,a.rebate1_amt,a.rebate2_amt,a.rebate3_amt,a.rebate1_month,a.rebate2_month,"
				+ "a.rebate3_month,a.rebatetype,a.samt,a.num,a.unno from check_MerSumActiva a "
				+ "where a.rebatetype = 10";
		if(checkRebateBean.getUnno()!=null&&!"".equals(checkRebateBean.getUnno())){
			sql += " and a.unno in (select unno from sys_unit start with unno = :unno connect by prior unno = upper_unit)";
			map.put("unno", checkRebateBean.getUnno());
		}
		if(checkRebateBean.getMid()!=null&&!"".equals(checkRebateBean.getMid())){
			sql += " and a.mid=:mid";
			map.put("mid", checkRebateBean.getMid());
		}
		if(checkRebateBean.getSn()!=null&&!"".equals(checkRebateBean.getSn())){
			sql += " and a.sn=:sn";
			map.put("sn", checkRebateBean.getSn());
		}
		if(checkRebateBean.getTxnDay()!=null&&!"".equals(checkRebateBean.getTxnDay())){
			sql += " and a.txnmonth=:txnmonth";
			map.put("txnmonth", checkRebateBean.getTxnDay().replace("-", "").substring(0, 6));
		}
		sqlCount = "select count(1) from ("+sql+")";
		List<Map<String,Object>> list = checkRebateDao.queryObjectsBySqlList2(sql, map, checkRebateBean.getPage(), checkRebateBean.getRows());
		BigDecimal counts = checkRebateDao.querysqlCounts(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}

	@Override
	public DataGridBean queryRebate10Summary(CheckRebateBean checkRebateBean) {
		DataGridBean dgb = new DataGridBean();
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="";
		String sqlCount ="";
		sql = "select (select unno from sys_unit b where b.un_lvl=2 start with b.unno=a.unno connect by prior upper_unit = unno) yidai,"
				+ "(select unno from sys_unit b where b.un_lvl=1 start with b.unno=a.unno connect by prior upper_unit = unno) yunying,"
				+ "a.rebateamt,a.rebatenum,a.rebatetype from check_UnnoSumActiva a where a.rebatetype = 10";
		if(checkRebateBean.getUnno()!=null&&!"".equals(checkRebateBean.getUnno())){
			sql += " and a.unno in (select unno from sys_unit start with unno = :unno connect by prior unno = upper_unit)";
			map.put("unno", checkRebateBean.getUnno());
		}
		if(checkRebateBean.getTxnDay()!=null&&!"".equals(checkRebateBean.getTxnDay())){
			sql += " and a.txnmonth=:txnmonth";
			map.put("txnmonth", checkRebateBean.getTxnDay().replace("-", "").substring(0, 6));
		}
		sqlCount = "select count(1) from ("+sql+")";
		List<Map<String,Object>> list = checkRebateDao.queryObjectsBySqlList2(sql, map, checkRebateBean.getPage(), checkRebateBean.getRows());
		BigDecimal counts = checkRebateDao.querysqlCounts(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}
	
	@Override
	public DataGridBean queryRebate12Detail(CheckRebateBean checkRebateBean) {
		DataGridBean dgb = new DataGridBean();
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="";
		String sqlCount ="";
		sql = "select (select unno from sys_unit b where b.un_lvl=2 start with b.unno=a.unno connect by prior upper_unit = unno) yidai,"
				+ "(select unno from sys_unit b where b.un_lvl=1 start with b.unno=a.unno connect by prior upper_unit = unno) yunying,"
				+ "a.mid,a.sn,a.machinemodel,a.sn_type,a.keyconfirmdate,"
				+ "to_char(a.usedconfirmdate,'yyyymm') usedconfirmmonth,"
				+ "a.usedconfirmdate,a.rebateamt,a.rebatetype,a.rebatemonth,a.samt,a.num,a.unno from check_MerSumActiva a "
				+ "where a.rebatetype = 12";
		if(checkRebateBean.getUnno()!=null&&!"".equals(checkRebateBean.getUnno())){
			sql += " and a.unno in (select unno from sys_unit start with unno = :unno connect by prior unno = upper_unit)";
			map.put("unno", checkRebateBean.getUnno());
		}
		if(checkRebateBean.getMid()!=null&&!"".equals(checkRebateBean.getMid())){
			sql += " and a.mid=:mid";
			map.put("mid", checkRebateBean.getMid());
		}
		if(checkRebateBean.getSn()!=null&&!"".equals(checkRebateBean.getSn())){
			sql += " and a.sn=:sn";
			map.put("sn", checkRebateBean.getSn());
		}
		if(checkRebateBean.getTxnDay()!=null&&!"".equals(checkRebateBean.getTxnDay())){
			sql += " and a.txnmonth=:txnmonth";
			map.put("txnmonth", checkRebateBean.getTxnDay().replace("-", "").substring(0, 6));
		}
		sqlCount = "select count(1) from ("+sql+")";
		List<Map<String,Object>> list = checkRebateDao.queryObjectsBySqlList2(sql, map, checkRebateBean.getPage(), checkRebateBean.getRows());
		BigDecimal counts = checkRebateDao.querysqlCounts(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}

	@Override
	public DataGridBean queryRebate12Summary(CheckRebateBean checkRebateBean) {
		DataGridBean dgb = new DataGridBean();
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="";
		String sqlCount ="";
		sql = "select (select unno from sys_unit b where b.un_lvl=2 start with b.unno=a.unno connect by prior upper_unit = unno) yidai,"
				+ "(select unno from sys_unit b where b.un_lvl=1 start with b.unno=a.unno connect by prior upper_unit = unno) yunying,"
				+ "a.rebateamt,a.rebatenum,a.rebatetype from check_UnnoSumActiva a where a.rebatetype = 12";
		if(checkRebateBean.getUnno()!=null&&!"".equals(checkRebateBean.getUnno())){
			sql += " and a.unno in (select unno from sys_unit start with unno = :unno connect by prior unno = upper_unit)";
			map.put("unno", checkRebateBean.getUnno());
		}
		if(checkRebateBean.getTxnDay()!=null&&!"".equals(checkRebateBean.getTxnDay())){
			sql += " and a.txnmonth=:txnmonth";
			map.put("txnmonth", checkRebateBean.getTxnDay().replace("-", "").substring(0, 6));
		}
		sqlCount = "select count(1) from ("+sql+")";
		List<Map<String,Object>> list = checkRebateDao.queryObjectsBySqlList2(sql, map, checkRebateBean.getPage(), checkRebateBean.getRows());
		BigDecimal counts = checkRebateDao.querysqlCounts(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}

	@Override
	public List<Map<String, Object>> exportRebate2Detail(CheckRebateBean checkRebateBean) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="";
		sql = "select (select unno from sys_unit b where b.un_lvl=2 start with b.unno=a.unno connect by prior upper_unit = unno) yidai,"
			+ "(select unno from sys_unit b where b.un_lvl=1 start with b.unno=a.unno connect by prior upper_unit = unno) yunying,"
			+ "a.mid,a.sn,a.machinemodel,a.sn_type,a.keyconfirmdate,"
			+ "to_char(a.keyconfirmdate,'yyyymm') keyconfirmmonth,a.usedconfirmdate,"
			+ "a.rebateamt,a.rebatetype,a.isrebate,a.rebatemonth,a.samt,a.num from check_MerSumActiva a "
			+ "where a.rebatetype = 2";
		if(checkRebateBean.getUnno()!=null&&!"".equals(checkRebateBean.getUnno())){
			sql += " and a.unno in (select unno from sys_unit start with unno = :unno connect by prior unno = upper_unit)";
			map.put("unno", checkRebateBean.getUnno());
		}
		if(checkRebateBean.getMid()!=null&&!"".equals(checkRebateBean.getMid())){
			sql += " and a.mid=:mid";
			map.put("mid", checkRebateBean.getMid());
		}
		if(checkRebateBean.getSn()!=null&&!"".equals(checkRebateBean.getSn())){
			sql += " and a.sn=:sn";
			map.put("sn", checkRebateBean.getSn());
		}
		if(checkRebateBean.getTxnDay()!=null&&!"".equals(checkRebateBean.getTxnDay())){
			sql += " and a.txnmonth=:txnmonth";
			map.put("txnmonth", checkRebateBean.getTxnDay().replace("-", "").substring(0, 6));
		}
		list = checkRebateDao.queryObjectsBySqlListMap2(sql, map);
		return list;
	}

	@Override
	public List<Map<String, Object>> exportRebate2Summary(CheckRebateBean checkRebateBean) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="";
		sql = "select (select unno from sys_unit b where b.un_lvl=2 start with b.unno=a.unno connect by prior upper_unit = unno) yidai,"
				+ "(select unno from sys_unit b where b.un_lvl=1 start with b.unno=a.unno connect by prior upper_unit = unno) yunying,"
				+ "a.rebateamt,a.rebatenum,a.rebatetype from check_UnnoSumActiva a where a.rebatetype = 2";
		if(checkRebateBean.getUnno()!=null&&!"".equals(checkRebateBean.getUnno())){
			sql += " and a.unno in (select unno from sys_unit start with unno = :unno connect by prior unno = upper_unit)";
			map.put("unno", checkRebateBean.getUnno());
		}
		if(checkRebateBean.getTxnDay()!=null&&!"".equals(checkRebateBean.getTxnDay())){
			sql += " and a.txnmonth=:txnmonth";
			map.put("txnmonth", checkRebateBean.getTxnDay().replace("-", "").substring(0, 6));
		}
		list = checkRebateDao.queryObjectsBySqlListMap2(sql, map);
		return list;
	}

	@Override
	public List<Map<String, Object>> exportRebate10Detail(CheckRebateBean checkRebateBean) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="";
		sql = "select (select unno from sys_unit b where b.un_lvl=2 start with b.unno=a.unno connect by prior upper_unit = unno) yidai,"
				+ "(select unno from sys_unit b where b.un_lvl=1 start with b.unno=a.unno connect by prior upper_unit = unno) yunying,"
				+ "a.mid,a.sn,a.machinemodel,a.sn_type,a.keyconfirmdate,"
				+ "to_char(a.usedconfirmdate,'yyyymm') usedconfirmmonth,"
				+ "a.usedconfirmdate,a.rebate1_amt,a.rebate2_amt,a.rebate3_amt,a.rebate1_month,a.rebate2_month,"
				+ "a.rebate3_month,a.rebatetype,a.samt,a.num from check_MerSumActiva a "
				+ "where a.rebatetype = 10";
		if(checkRebateBean.getUnno()!=null&&!"".equals(checkRebateBean.getUnno())){
			sql += " and a.unno in (select unno from sys_unit start with unno = :unno connect by prior unno = upper_unit)";
			map.put("unno", checkRebateBean.getUnno());
		}
		if(checkRebateBean.getMid()!=null&&!"".equals(checkRebateBean.getMid())){
			sql += " and a.mid=:mid";
			map.put("mid", checkRebateBean.getMid());
		}
		if(checkRebateBean.getSn()!=null&&!"".equals(checkRebateBean.getSn())){
			sql += " and a.sn=:sn";
			map.put("sn", checkRebateBean.getSn());
		}
		if(checkRebateBean.getTxnDay()!=null&&!"".equals(checkRebateBean.getTxnDay())){
			sql += " and a.txnmonth=:txnmonth";
			map.put("txnmonth", checkRebateBean.getTxnDay().replace("-", "").substring(0, 6));
		}
		list = checkRebateDao.queryObjectsBySqlListMap2(sql, map);
		return list;
	}

	@Override
	public List<Map<String, Object>> exportRebate10Summary(CheckRebateBean checkRebateBean) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="";
		sql = "select (select unno from sys_unit b where b.un_lvl=2 start with b.unno=a.unno connect by prior upper_unit = unno) yidai,"
				+ "(select unno from sys_unit b where b.un_lvl=1 start with b.unno=a.unno connect by prior upper_unit = unno) yunying,"
				+ "a.rebateamt,a.rebatenum,a.rebatetype from check_UnnoSumActiva a where a.rebatetype = 10";
		if(checkRebateBean.getUnno()!=null&&!"".equals(checkRebateBean.getUnno())){
			sql += " and a.unno in (select unno from sys_unit start with unno = :unno connect by prior unno = upper_unit)";
			map.put("unno", checkRebateBean.getUnno());
		}
		if(checkRebateBean.getTxnDay()!=null&&!"".equals(checkRebateBean.getTxnDay())){
			sql += " and a.txnmonth=:txnmonth";
			map.put("txnmonth", checkRebateBean.getTxnDay().replace("-", "").substring(0, 6));
		}
		list = checkRebateDao.queryObjectsBySqlListMap2(sql, map);
		return list;
	}

	@Override
	public List<Map<String, Object>> exportRebate12Detail(CheckRebateBean checkRebateBean) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="";
		sql = "select (select unno from sys_unit b where b.un_lvl=2 start with b.unno=a.unno connect by prior upper_unit = unno) yidai,"
				+ "(select unno from sys_unit b where b.un_lvl=1 start with b.unno=a.unno connect by prior upper_unit = unno) yunying,"
				+ "a.mid,a.sn,a.machinemodel,a.sn_type,a.keyconfirmdate,"
				+ "to_char(a.usedconfirmdate,'yyyymm') usedconfirmmonth,"
				+ "a.usedconfirmdate,a.rebateamt,a.rebatetype,a.rebatemonth,a.samt,a.num from check_MerSumActiva a "
				+ "where a.rebatetype = 12";
		if(checkRebateBean.getUnno()!=null&&!"".equals(checkRebateBean.getUnno())){
			sql += " and a.unno in (select unno from sys_unit start with unno = :unno connect by prior unno = upper_unit)";
			map.put("unno", checkRebateBean.getUnno());
		}
		if(checkRebateBean.getMid()!=null&&!"".equals(checkRebateBean.getMid())){
			sql += " and a.mid=:mid";
			map.put("mid", checkRebateBean.getMid());
		}
		if(checkRebateBean.getSn()!=null&&!"".equals(checkRebateBean.getSn())){
			sql += " and a.sn=:sn";
			map.put("sn", checkRebateBean.getSn());
		}
		if(checkRebateBean.getTxnDay()!=null&&!"".equals(checkRebateBean.getTxnDay())){
			sql += " and a.txnmonth=:txnmonth";
			map.put("txnmonth", checkRebateBean.getTxnDay().replace("-", "").substring(0, 6));
		}
		list = checkRebateDao.queryObjectsBySqlListMap2(sql, map);
		return list;
	}

	@Override
	public List<Map<String, Object>> exportRebate12Summary(CheckRebateBean checkRebateBean) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		String sql ="";
		sql = "select (select unno from sys_unit b where b.un_lvl=2 start with b.unno=a.unno connect by prior upper_unit = unno) yidai,"
				+ "(select unno from sys_unit b where b.un_lvl=1 start with b.unno=a.unno connect by prior upper_unit = unno) yunying,"
				+ "a.rebateamt,a.rebatenum,a.rebatetype from check_UnnoSumActiva a where a.rebatetype = 12";
		if(checkRebateBean.getUnno()!=null&&!"".equals(checkRebateBean.getUnno())){
			sql += " and a.unno in (select unno from sys_unit start with unno = :unno connect by prior unno = upper_unit)";
			map.put("unno", checkRebateBean.getUnno());
		}
		if(checkRebateBean.getTxnDay()!=null&&!"".equals(checkRebateBean.getTxnDay())){
			sql += " and a.txnmonth=:txnmonth";
			map.put("txnmonth", checkRebateBean.getTxnDay().replace("-", "").substring(0, 6));
		}
		list = checkRebateDao.queryObjectsBySqlListMap2(sql, map);
		return list;
	}
}
