package com.hrt.phone.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.phone.dao.IPhoneCheckUnitDealDataDao;
import com.hrt.phone.service.IPhoneCheckUnitDealDataService;
import org.apache.commons.lang3.StringUtils;

public class PhoneCheckUnitDealDataServiceImpl implements
		IPhoneCheckUnitDealDataService {

	private IPhoneCheckUnitDealDataDao phoneCheckUnitDealDataDao;

	public IPhoneCheckUnitDealDataDao getPhoneCheckUnitDealDataDao() {
		return phoneCheckUnitDealDataDao;
	}

	public void setPhoneCheckUnitDealDataDao(
			IPhoneCheckUnitDealDataDao phoneCheckUnitDealDataDao) {
		this.phoneCheckUnitDealDataDao = phoneCheckUnitDealDataDao;
	}

	@Override
	public List<Map<String, Object>> findHomePageData(Object user) {
UserBean userBean = (UserBean) user;
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "";
		if (userBean.getUseLvl() == 3) {
			sql = "  select round(nvl(a, 0), 2) as noMnamtAll,"
					+ " round(nvl(b, 0), 2) as mnamtAll, "
					+ " tcycle,"
					+ " status, "
					+ " mid, " 
					+ " rname"
					+ " from (select nvl(sum(t.MNAMT), 0) AS a,"
					+ " m.SettleCycle as tcycle,"
					+ " m.SettleStatus as status,"
					+ " m.mid as mid ,"
					+ " m.rname "
					+ " from  CHECK_UNITDEALDETAIL t,  BILL_MERCHANTINFO m "
					+ " where t.mid = m.mid "
					+ " and m.mid =:MID "
					+ " and t.ACTIONSETTTLEDATE is null "
					+ " group by m.mid, m.SettleCycle, m.SettleStatus,m.rname)"
					+
					"  full outer join "
					+
					" (select nvl(sum(t.MNAMT), 0) AS b,"
					+ " m.SettleCycle, "
					+ " m.SettleStatus, "
					+ " m.mid as d "
					+ " from  CHECK_UNITDEALDETAIL t,  BILL_MERCHANTINFO m "
					+ " where t.mid = m.mid "
					+ "  and m.mid = :MID "
					+ " and t.ACTIONSETTTLEDATE = to_char(sysdate, 'YYYYMMDD') "
					+ " group by m.mid, m.SettleCycle, m.SettleStatus) "
					+ " on mid = d";
			map.put("MID", userBean.getLoginName());
		} else {
			if (userBean.getUnitLvl() == 0) {
				// 总公司级别角色用户
				sql = "select round(nvl(a,0),2) as noMnamtAll,  round(nvl(b,0),2) as mnamtAll from "
						+ " (select nvl(sum(t.MNAMT),0) AS a  "
						+ " from  CHECK_UNITDEALDETAIL t  "
						+ " where  t.ACTIONSETTTLEDATE    is  null )"
						+ " full outer join "
						+ " (select nvl(sum(t.MNAMT),0) AS b  "
						+ " from  CHECK_UNITDEALDETAIL t  "
						+ " where  t.ACTIONSETTTLEDATE =to_char(sysdate,'YYYYMMDD')  ) "
						+ " on 1=1 ";
			} else if (userBean.getUnitLvl() == 2) {
				// 代理商级别用户
				sql = "   select round(nvl(a, 0), 2) as noMnamtAll, round(nvl(b, 0), 2) as mnamtAll"
						+ " from (select nvl(sum(t.MNAMT), 0) AS a"
						+ "    from CHECK_UNITDEALDETAIL t"
						+ "   where t.mid in (select c.mid"
						+ "          from CHECK_UNITDEALDATA c"
						+ "           where c.unno = :UNNO)"
						+ "     and t.ACTIONSETTTLEDATE is null)"
						+ "    full outer join (select nvl(sum(t.MNAMT), 0) AS b"
						+ "               from CHECK_UNITDEALDETAIL t"
						+ "                where t.mid in (select c.mid"
						+ "                           from CHECK_UNITDEALDATA c"
						+ "                          where c.unno = :UNNO)"
						+ "              and t.ACTIONSETTTLEDATE = "
						+ "               to_char(sysdate, 'YYYYMMDD')) "
						+ "   on 1 = 1 ";
				map.put("UNNO", userBean.getUnNo());
			} else {
				// 分公司级别商户
				sql = "   select round(nvl(a, 0), 2) as noMnamtAll, round(nvl(b, 0), 2) as mnamtAll "
						+ "  from (select nvl(sum(t.MNAMT), 0) AS a "
						+ "        from  CHECK_UNITDEALDETAIL t "
						+ "     where t.mid in "
						+ "        (select c.mid "
						+ "             from  CHECK_UNITDEALDATA c "
						+ "           where c.unno in (select unno "
						+ "                           from  SYS_UNIT "
						+ "                           where unno = UNNO "
						+ "                              or upper_unit =:UNNO2)) "
						+ "   and t.ACTIONSETTTLEDATE is null) "
						+ "  full outer join (select nvl(sum(t.MNAMT), 0) AS b "
						+ "              from  CHECK_UNITDEALDETAIL t "
						+ "        where t.mid in "
						+ "          (select c.mid "
						+ "                from CHECK_UNITDEALDATA c "
						+ "               where c.unno in "
						+ "                 (select unno "
						+ "                 from  SYS_UNIT "
						+ "             where unno =:UNNO "
						+ "                 or upper_unit = :UNNO2)) "
						+ "  and t.ACTIONSETTTLEDATE = "
						+ "    to_char(sysdate, 'YYYYMMDD')) " +

						"  on 1 = 1 ";
				map.put("UNNO", userBean.getUnNo());
				map.put("UNNO2", userBean.getUnNo());
			}
		}
		List<Map<String, Object>> list = phoneCheckUnitDealDataDao.executeSql2(
				sql, map);
		if (userBean.getUseLvl() == 3) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map2 = list.get(i);
				int a = Integer.parseInt(map2.get("TCYCLE").toString());
				map2.put("TCYCLE", "T+" + a);
				String ss = (Integer.parseInt(map2.get("STATUS").toString())) == 1 ? "正常"
						: "冻结";
				map2.put("STATUS", ss);
				map2.put("REMAINING", map2.get("NOMNAMTALL"));
				map2.put("trading", "");
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map2 = list.get(i);
				map2.put("trading", findTodayTrading(user));
				map2.put("TCYCLE", "");
				map2.put("STATUS", "");
				map2.put("REMAINING", "");
			}
		}
		return list;
	}
	
	
	@Override
	public List<Map<String, Object>> findHomePageDataNew(Object user) {
		UserBean userBean = (UserBean) user;
		Map<String, Object> map = new HashMap<String, Object>();
		String sumAmountSql = "select nvl(sum(T.txnamount),0) TXNAMOUNTSUM from check_unitdealdata t " +
								"  where  t.mid =:MID ";
		String realtimeAmountSql="select nvl(sum(CAST(t.txnamount AS DECIMAL(18, 2))),0) as REALTIMEALLTXN" +
				" from check_realtxn t  where   t.txntype in (0,5) " +
				" and t.txnday = to_char(sysdate, 'yyyyMMdd') and t.txnstate=0 and " +
				" t.txnamount not like '%null%' and  t.mid =:MID ";
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		map.put("MID", userBean.getLoginName());
		List<Map<String, Object>> sumAmountList =phoneCheckUnitDealDataDao.executeSql2(sumAmountSql, map);
		List<Map<String, Object>> realtimeAmountList =phoneCheckUnitDealDataDao.executeSql2(realtimeAmountSql, map);
		Map<String,Object> mapResult = new HashMap<String, Object>();
		BigDecimal a= (BigDecimal) sumAmountList.get(0).get("TXNAMOUNTSUM");
		BigDecimal b= (BigDecimal) realtimeAmountList.get(0).get("REALTIMEALLTXN");
		mapResult.put("TXNAMOUNTSUM", a.add(b)+"");
		mapResult.put("REALTIMEALLTXN", realtimeAmountList.get(0).get("REALTIMEALLTXN")+"");
		list.add(mapResult);
		return list;
	}

	@Override
	public DataGridBean findNoSettlement(Object userSession, Integer page,
			Integer rows) {
		UserBean userBean = (UserBean) userSession;
		Map<String, Object> map = new HashMap<String, Object>();

		String sql = " select  to_char(nvl(SUM(t.MNAMT ),0),'999,999,999,999.99') as noMnamtAll ,m.mid,m.rname "
				+ " from CHECK_UNITDEALDETAIL t , BILL_MERCHANTINFO  m "
				+ " where 1=1 and  t.mid=m.mid  ";
		if (userBean.getUseLvl() == 3) {
			sql += "  and  m.mid= :MID  ";
			map.put("MID", userBean.getLoginName());
		} else {
			if (userBean.getUnitLvl() == 0) {
				// 总公司级别角色用户
			} else if (userBean.getUnitLvl() == 2) {
				// 代理商级别用户
				sql += "  and  t.mid "
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno=:UNNO) ";
				map.put("UNNO", userBean.getUnNo());
			} else {
				// 分公司级别商户
				sql += " and  t.mid"
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno "
						+ " in( select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 ))  ";
				map.put("UNNO", userBean.getUnNo());
				map.put("UNNO2", userBean.getUnNo());
			}
		}
		sql += " and t.ACTIONSETTTLEDATE is null group by  m.mid,m.rname order by noMnamtAll  desc ";
		int count = phoneCheckUnitDealDataDao.executeSql2(sql, map).size();
		List<Map<String, Object>> list = phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sql, map, page, rows);
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}

	@Override
	public DataGridBean findNoSettlementDetail(Object userSession,
			Integer page, Integer rows, String mid) {
		String sql = " select t.CardPAN ,round(t.TxnAmount,2)as TxnAmount ,round(t.MNAMT,2) as MNAMT ,t.TxnDay,t.ScheduleSettleDate "
				+ " from  CHECK_UNITDEALDETAIL t   "
				+ " where  t.mid=:MID   and t.ACTIONSETTTLEDATE is null  order by t.ScheduleSettleDate desc ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("MID", mid);
		int count = phoneCheckUnitDealDataDao.executeSql2(sql, map).size();
		List<Map<String, Object>> list = phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sql, map, page, rows);
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}

	@Override
	public DataGridBean findTheDayTurnover(Object userSession, Integer page,
			Integer rows) {
		UserBean userBean = (UserBean) userSession;
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = " select  t.CardPAN ,t.TxnAmount, (t.TxnAmount -t.MNAMT) as profits ,t.TxnDay "
				+ " from CHECK_UNITDEALDETAIL t where 1=1  ";
		if (userBean.getUseLvl() == 3) {
			sql += " and t.mid=:MID  ";
			map.put("MID", userBean.getLoginName());
		} else {
			if (userBean.getUnitLvl() == 0) {
				// 总公司级别角色用户
			} else if (userBean.getUnitLvl() == 2) {
				// 代理商级别用户
				sql += " and  t.mid in(select c.mid from CHECK_UNITDEALDATA c where c.unno=:UNNO)  ";
				map.put("UNNO", userBean.getUnNo());
			} else {
				// 分公司级别商户
				sql += " and  t.mid "
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno "
						+ "in( select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 )) ";
				map.put("UNNO", userBean.getUnNo());
				map.put("UNNO2", userBean.getUnNo());
			}
		}
		sql += " and t.ACTIONSETTTLEDATE= to_char(sysdate-1,'YYYYMMDD') order by t.TxnDay desc ";
		int count = phoneCheckUnitDealDataDao.executeSql2(sql, map).size();
		List<Map<String, Object>> list = phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sql, map, page, rows);
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}

	// 首页信息的交易量查询
	public String findTodayTrading(Object user) {
		UserBean userBean = (UserBean) user;
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = " select  round(nvl( SUM(t.TXNAMOUNT),0),2) as trading  "
				+ " from CHECK_UNITDEALDATA t  where 1=1 ";
		if (userBean.getUnitLvl() == 0) {
			// 总公司级别角色用户
		} else if (userBean.getUnitLvl() == 2) {
			// 代理商级别用户
			sql += " and t.mid in(select c.mid from CHECK_UNITDEALDATA c where c.unno=:UNNO) ";
			map.put("UNNO", userBean.getUnNo());
		} else {
			// 分公司级别商户
			sql += " and t.mid"
					+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno "
					+ "in( select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 )) ";
			map.put("UNNO", userBean.getUnNo());
			map.put("UNNO2", userBean.getUnNo());
		}
		sql += " and t.txnday=to_char(sysdate-1,'YYYYMMDD') ";
		List<Map<String, Object>> list = phoneCheckUnitDealDataDao.executeSql2(sql, map);
		return list.get(0).get("TRADING").toString();
	}

	@Override
	public JsonBean findDynamicFormData(Object userSession) {

		UserBean userBean = (UserBean) userSession;
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select nvl(sum(t.txnamount),0) c  ,to_char(to_date(t.txnday,'yyyy/mm/dd'),'mm/dd') a "
				+ " from CHECK_UNITDEALDATA t  where 1=1  and t.txnday >= to_char(sysdate - 7, 'yyyymmdd') ";
		if (userBean.getUseLvl() == 3) {
			sql += " and  t.mid=:MID ";
			map.put("MID", userBean.getLoginName());
		} else {
			if (userBean.getUnitLvl() == 0) {
				// 总公司级别角色用户
			} else if (userBean.getUnitLvl() == 2) {
				// 代理商级别用户
				sql += " and  t.mid in(select c.mid from CHECK_UNITDEALDATA c where c.unno=:UNNO) ";
				map.put("UNNO", userBean.getUnNo());
			} else {
				// 分公司级别商户
				sql += " and t.mid "
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno "
						+ "in( select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 )) ";
				map.put("UNNO", userBean.getUnNo());
				map.put("UNNO2", userBean.getUnNo());
			}
		}
		sql += " group by  t.txnday order by t.txnday desc";
		String sql2 ="select to_char(sysdate - level, 'mm/dd') b" +
				" from dual connect by level <= 7 " +
				" order by to_char(sysdate - level, 'mm/dd') desc";
		String sql3="select nvl(c,0) TXNAMOUNTALL , b txnday from ("+sql+") right outer join ("+sql2+") on a=b order by txnday desc ";
		int count = phoneCheckUnitDealDataDao.executeSql2(sql, map).size();
		JsonBean json = toDealWithData(phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sql3, map, 0, 7),count);
		return json;
	}

	@Override
	public DataGridBean findTop20(Object userSession) {
		UserBean userBean = (UserBean) userSession;
		Map<String, Object> map = new HashMap<String, Object>();

		String sql = " select  round(nvl(SUM(t.TXNAMOUNT ),0),2) as txnAmount ,m.mid,m.rname "
				+ " from CHECK_UNITDEALDETAIL t , BILL_MERCHANTINFO  m "
				+ " where 1=1 and  t.mid=m.mid  ";
		if (userBean.getUseLvl() == 3) {
			return null;
		} else {
			if (userBean.getUnitLvl() == 0) {
				// 总公司级别角色用户
			} else if (userBean.getUnitLvl() == 2) {
				// 代理商级别用户
				sql += "  and  t.mid "
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno=:UNNO) ";
				map.put("UNNO", userBean.getUnNo());
			} else {
				// 分公司级别商户
				sql += " and  t.mid"
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno "
						+ " in( select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 ))  ";
				map.put("UNNO", userBean.getUnNo());
				map.put("UNNO2", userBean.getUnNo());
			}
		}
		sql += " and t.txnday=to_char(sysdate-1,'yyyymmdd')  group by  m.mid,m.rname order by txnAmount  desc ";
		int count = phoneCheckUnitDealDataDao.executeSql2(sql, map).size();
		List<Map<String, Object>> list = phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sql, map, 1, 20);

		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}

	@Override
	public DataGridBean findGains(Object userSession, Integer page, Integer rows) {
		UserBean userBean = (UserBean) userSession;
		Map<String, Object> map = new HashMap<String, Object>();

		String sql = " select a AS todayTrading, b as yesterdayTrading, gains, MER_NAME "
				+ " FROM ( "
				+ "   select round(nvl(a, 0), 2) as A, "
				+ "  round(nvl(b, 0), 2) as B, "
				+ " round((A - B) / B * 100, 2) AS gains, "
				+ "  MER_NAME "
				+ "   from (select nvl(sum(t.TXNAMOUNT), 0) AS a, "
				+ "              m.rname as MER_NAME, "
				+ "             m.mid AS D "
				+ "   from  CHECK_UNITDEALDATA t,  BILL_MERCHANTINFO m "
				+ "    where t.mid = m.mid ";
		if (userBean.getUseLvl() == 3) {
			return null;
		} else {
			if (userBean.getUnitLvl() == 0) {
				// 总公司级别角色用户
				sql += " and t.TXNDAY = to_char(sysdate - 1, 'yyyymmdd') "
						+ "   group by m.mid, m.rname) "
						+ "  full outer join "
						+ " (select nvl(sum(t.TXNAMOUNT), 0) AS b, m.rname as F, m.mid AS E "
						+ "     from  CHECK_UNITDEALDATA t,  BILL_MERCHANTINFO m "
						+ "   where t.mid = m.mid ";
			} else if (userBean.getUnitLvl() == 2) {
				// 代理商级别用户
				sql += "  and t.mid in (select c.mid "
						+ "          from  CHECK_UNITDEALDATA c "
						+ "                   where c.unno = 121000) "
						+ "      and t.TXNDAY = to_char(sysdate - 1, 'yyyymmdd') "
						+ "   group by m.mid, m.rname) "
						+ "  full outer join "
						+ " (select nvl(sum(t.TXNAMOUNT), 0) AS b, m.rname as F, m.mid AS E "
						+ "     from  CHECK_UNITDEALDATA t,  BILL_MERCHANTINFO m "
						+ "   where t.mid = m.mid "
						+ "    and t.mid in (select c.mid "
						+ "                  from  CHECK_UNITDEALDATA c "
						+ "                where c.unno = 121000) ";
				map.put("UNNO", userBean.getUnNo());
			} else {
				// 分公司级别商户
				sql += "   and t.mid in (select c.mid "
						+ "                      from  CHECK_UNITDEALDATA c "
						+ "                   where c.unno in( "
						+ " select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 )) "
						+ "      and t.TXNDAY = to_char(sysdate - 1, 'yyyymmdd') "
						+ "   group by m.mid, m.rname) "
						+ "  full outer join "
						+ " (select nvl(sum(t.TXNAMOUNT), 0) AS b, m.rname as F, m.mid AS E "
						+ "     from  CHECK_UNITDEALDATA t,  BILL_MERCHANTINFO m "
						+ "   where t.mid = m.mid "
						+ "    and t.mid in (select c.mid "
						+ "                  from  CHECK_UNITDEALDATA c "
						+ "                where c.unno in("
						+ "		 select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 )) ";
				map.put("UNNO", userBean.getUnNo());
				map.put("UNNO2", userBean.getUnNo());
			}
		}
		sql += "  and t.TXNDAY = to_char(sysdate - 2, 'yyyymmdd') "
				+ " group by m.mid, m.rname) " + "    on D = E) "
				+ " WHERE gains >= 10 " + "   or B = 0 ";
		int count = phoneCheckUnitDealDataDao.executeSql2(sql, map).size();
		List<Map<String, Object>> list = phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sql, map, page, rows);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> mapUtil = list.get(i);
			if (Integer.parseInt(mapUtil.get("YESTERDAYTRADING").toString()) == 0) {
				mapUtil.put("GAINS", 100);
			}
		}
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}

	@Override
	public DataGridBean findDrop(Object userSession, Integer page, Integer rows) {
		UserBean userBean = (UserBean) userSession;
		Map<String, Object> map = new HashMap<String, Object>();

		String sql = " select a AS todayTrading, b as yesterdayTrading, gains, MER_NAME "
				+ " FROM ( "
				+ "   select round(nvl(a, 0), 2) as A, "
				+ "        round(nvl(b, 0), 2) as B, "
				+ "      round((A - B) / B * 100, 2) AS gains, "
				+ "       MER_NAME "
				+ "   from (select nvl(sum(t.TXNAMOUNT), 0) AS a, "
				+ "              m.rname , "
				+ "             m.mid AS D "
				+ "   from  CHECK_UNITDEALDATA t,  BILL_MERCHANTINFO m "
				+ "    where t.mid = m.mid ";
		if (userBean.getUseLvl() == 3) {
			return null;
		} else {
			if (userBean.getUnitLvl() == 0) {
				// 总公司级别角色用户
				sql += " and t.TXNDAY = to_char(sysdate - 1, 'yyyymmdd') "
						+ "   group by m.mid, m.rname) "
						+ "  full outer join "
						+ " (select nvl(sum(t.TXNAMOUNT), 0) AS b,  m.rname as MER_NAME, m.mid AS E "
						+ "     from  CHECK_UNITDEALDATA t,  BILL_MERCHANTINFO m "
						+ "   where t.mid = m.mid ";
			} else if (userBean.getUnitLvl() == 2) {
				// 代理商级别用户
				sql += "  and t.mid in (select c.mid "
						+ "   from  CHECK_UNITDEALDATA c "
						+ "   where c.unno = 121000) "
						+ "   and t.TXNDAY = to_char(sysdate - 1, 'yyyymmdd') "
						+ "   group by m.mid, m.rname) "
						+ "   full outer join "
						+ "   (select nvl(sum(t.TXNAMOUNT), 0) AS b, m.rname as MER_NAME, m.mid AS E "
						+ "   from  CHECK_UNITDEALDATA t,  BILL_MERCHANTINFO m "
						+ "   where t.mid = m.mid "
						+ "   and t.mid in (select c.mid "
						+ "   from  CHECK_UNITDEALDATA c "
						+ "   where c.unno = 121000) ";
				map.put("UNNO", userBean.getUnNo());
			} else {
				// 分公司级别商户
				sql += "   and t.mid in (select c.mid "
						+ "   from  CHECK_UNITDEALDATA c "
						+ "    where c.unno in( "
						+ " select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 )) "
						+ "    and t.TXNDAY = to_char(sysdate - 1, 'yyyymmdd') "
						+ "  group by m.mid, m.rname) "
						+ "  full outer join "
						+ " (select nvl(sum(t.TXNAMOUNT), 0) AS b, m.rname as MER_NAME, m.mid AS E "
						+ "  from  CHECK_UNITDEALDATA t,  BILL_MERCHANTINFO m "
						+ "  where t.mid = m.mid "
						+ "  and t.mid in (select c.mid "
						+ "       from  CHECK_UNITDEALDATA c "
						+ "     where c.unno in("
						+ "  select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 )) ";
				map.put("UNNO", userBean.getUnNo());
				map.put("UNNO2", userBean.getUnNo());
			}
		}
		sql += "  and t.TXNDAY = to_char(sysdate - 2, 'yyyymmdd') "
				+ " group by m.mid, m.rname)  on D = E) " 
				+ " WHERE gains <=(-10) " + "   or A = 0 ";
		int count = phoneCheckUnitDealDataDao.executeSql2(sql, map).size();
		List<Map<String, Object>> list = phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sql, map, page, rows);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> mapUtil = list.get(i);
			if (Integer.parseInt(mapUtil.get("TODAYTRADING").toString()) == 0) {
				mapUtil.put("GAINS", (-100));
			}
		}
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}

	@Override
	public JsonBean findHistertoy(Object userSession, String startDay,
			String endDay,Integer page,Integer rows,String txnAmount) {
		String start=null;
		String end=null;
		UserBean userBean = (UserBean) userSession;
		Map<String, Object> map = new HashMap<String, Object>();
//		String sql = "  select t.mid,t.STAN,t.TID , t.CardPAN , t.cbrand ,t.TxnDay,t.txndate,t.TxnAmount ,t.mda,t.mnamt ,t.txntype,t.authcode,t.rrn" +
//					 ",to_char((t.mnamt - (case when t.ifcard=3 and t.mnamt<=1000 then 0 else nvl(decode(m.isM35,1,m.secondrate,0),2) end)), 'FM999,999,999,990.00') as smAmount from  CHECK_UNITDEALDETAIL t,bill_merchantinfo m " +
//					 " where t.mid = m.mid ";
		String sql = "  select t.mid,t.STAN,t.TID , t.CardPAN , t.cbrand ,t.TxnDay,t.txndate,t.TxnAmount ,t.mda,t.mnamt ,t.txntype,t.authcode,t.rrn" +
				",to_char((t.mnamt - m.cashfee), 'FM999,999,999,990.00') as smAmount from  CHECK_UNITDEALDETAIL t,check_cash m" +
				" where t.mid = m.mid and t.pkid=m.orpkwid and m.cashmode in (4,7) ";
		if(null!=userBean) {
			if (userBean.getUseLvl() == 3) {
				sql += " and t.mid=:MID ";
				map.put("MID", userBean.getLoginName());
			} else {
				if (userBean.getUnitLvl() == 0) {
					// 总公司级别角色用户
				} else if (userBean.getUnitLvl() == 2) {
					// 代理商级别用户
					sql += "  and  t.mid "
							+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno=:UNNO) ";
					map.put("UNNO", userBean.getUnNo());
				} else {
					// 分公司级别商户
					sql += " and  t.mid"
							+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno "
							+ " in( select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 ))  ";
					map.put("UNNO", userBean.getUnNo());
					map.put("UNNO2", userBean.getUnNo());
				}
			}
		}else{
			sql += " and t.mid=:MID ";
			map.put("MID", "");
		}
		if((startDay!=null && !"".equals(startDay)) && (endDay!=null && !"".equals(endDay)) && (txnAmount==null || "".equals(txnAmount)) ){
			SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
			Date s1=null;
			Date s2=null;
			try {
				s1=sf.parse(startDay);
				s2=sf.parse(endDay);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			SimpleDateFormat sf2 =new SimpleDateFormat("yyyyMMdd");
			 start=sf2.format(s1);
			 end=sf2.format(s2);
			 sql+="and t.txnday>=to_number('"+start+"') and  t.txnday <=to_number('"+end+"') order by t.TxnDay desc,t.bdid desc";
		}else if(txnAmount!=null && !"".equals(txnAmount)){
			sql+="and t.txnday>=to_char(sysdate-1,'yyyyMMdd') and t.TxnAmount>'"+txnAmount+"' order by t.TxnDay desc,t.bdid desc";
		}else{
			sql+="and t.txnday>=to_char(sysdate-1,'yyyyMMdd') order by t.TxnDay desc,t.bdid desc";
		}
		String sqlCount="select count(*) as counts,nvl(sum(txnAmount),0) CountTxnAmount,nvl(sum(to_number(smAmount,'FM999,999,999,999,990.00')),0) sumAmount from ("+sql+")";
		//int count=phoneCheckUnitDealDataDao.querysqlCounts2(sqlCount, map);
		List<Map<String, Object>> list2 =phoneCheckUnitDealDataDao.queryObjectsBySqlListMap(sqlCount,map);
		List<Map<String, Object>> list = phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sql, map, page, rows);
		int count=Integer.parseInt(list2.get(0).get("COUNTS").toString());
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		
		JsonBean json = new JsonBean();
		json.setObj(dgb);
		json.setCountTxnAmount(list2.get(0).get("COUNTTXNAMOUNT").toString());
		json.setNumberUnits(list2.get(0).get("SUMAMOUNT").toString());
		return json;
	}
	@Override
	public JsonBean findHistertoyScan(Object userSession, String startDay,
			String endDay,Integer page,Integer rows,String txnAmount,String trantype) {
//		String start=null;
//		String end=null;
		UserBean userBean = (UserBean) userSession;
		Map<String, Object> map = new HashMap<String, Object>();
//		String sql = "  select t.mid,t.mer_orderid,f.FIINFO1 as FIID,to_char(t.txnamt,'FM999,999,999,990.00') as txnamt,t.mda,t.status,to_char(t.cdate,'yyyyMMdd')as cdate,t.respcode " +
//		",to_char((t.txnamt-t.mda-(case when t.trantype='8' then 3 when f.fiid='18' then 0 when m.ism35='1' then 3 when m.ism35='1' and m.settmethod='000000' then 2 when m.settlecycle='1' then 0 else nvl(m.secondrate,2) end)),'FM999,999,999,990.00') as smAmount ,t.accno from  check_wechattxndetail t,bill_merchantinfo m, sys_bankfi f" +
//		" where t.mid=m.mid and t.fiid=f.fiid ";
        // @author:xuegangliu:20190322 添加收银台商户交易金额:小于等于1000 交易不减去3
        // 表查询修改
        String sql = "select " +
                "       t.mid," +
                "       t.mer_orderid," +
                "       f.FIINFO1 as FIID," +
                "       to_char(t.txnamt, 'FM999,999,999,990.00') as txnamt," +
                "       t.mda," +
                "       t.status," +
                "       to_char(t.cdate, 'yyyyMMdd') as cdate," +
                "       t.respcode," +
                "       to_char(cc.cashamt,'FM999,999,999,990.00') smAmount," +
                "       t.accno" +
                "  from check_cash cc, check_wechattxndetail t,sys_bankfi f" +
                " where cc.orpkwid = t.pwid and t.fiid = f.fiid and cc.cashmode in (5,6,8) ";

//		String sql = "  select t.mid,t.mer_orderid,f.FIINFO1 as FIID,to_char(t.txnamt,'FM999,999,999,990.00') as txnamt,t.mda,t.status,to_char(t.cdate,'yyyyMMdd')as cdate,t.respcode " +
//		",to_char((t.txnamt-t.mda-(case when t.trantype='8' then 3 when f.fiid='18' then 0 " +
//				" when m.ism35 = '1' and substr(t.mid,1,6) ='HRTSYT' and t.txnamt<=1000 then 0 " +
//				" when m.ism35='1' and m.settmethod='000000' then 2 " +
//				" when m.ism35='1' then 3 " +
//				" when m.settlecycle='1' then 0 else nvl(m.secondrate,2) end)),'FM999,999,999,990.00') as smAmount ,t.accno from  check_wechattxndetail t,bill_merchantinfo m, sys_bankfi f" +
//		" where t.mid=m.mid and t.fiid=f.fiid ";
		if(null!=userBean) {
			if (userBean.getUseLvl() == 3) {
				sql += " and cc.mid=:MID and t.mid=:MID ";
				map.put("MID", userBean.getLoginName());
			} else {
				if (userBean.getUnitLvl() == 0) {
					// 总公司级别角色用户
				} else if (userBean.getUnitLvl() == 2) {
					// 代理商级别用户
					sql += "  and  cc.mid "
							+ " in(select c.mid from bill_merchantinfo c where c.unno=:UNNO) ";
					map.put("UNNO", userBean.getUnNo());
				} else {
					// 分公司级别商户
					sql += " and  cc.mid"
							+ " in(select c.mid from bill_merchantinfo c where c.unno "
							+ " in( select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 ))  ";
					map.put("UNNO", userBean.getUnNo());
					map.put("UNNO2", userBean.getUnNo());
				}
			}
		}else{
			sql += " and cc.mid=:MID and t.mid=:MID ";
			map.put("MID", "");
		}
		//快捷--8 扫码--1 无卡--2
		if("8".equals(trantype)){
			sql+=" and t.trantype='8' ";
		}else if("1".equals(trantype)){
			sql+=" and (t.trantype!='8' or t.trantype is null )";
		}else if("2".equals(trantype)){
		}
		if((startDay!=null && !"".equals(startDay)) && (endDay!=null && !"".equals(endDay)) && (txnAmount==null || "".equals(txnAmount)) ){
			sql+="and t.cdate>=to_date(:startDay,'yyyy-MM-dd') and t.cdate < to_date(:endDay,'yyyy-MM-dd')+1 order by t.cdate desc,t.pwid desc";
			map.put("startDay", startDay);
			map.put("endDay", endDay);
		}else if(txnAmount!=null && !"".equals(txnAmount)){
//			sql+="and trunc(t.cdate)>=trunc(sysdate-1) and t.txnamt>'"+txnAmount+"' order by t.cdate desc,t.pwid desc";
			sql+="and t.cdate>=trunc(sysdate-1) and t.txnamt>'"+txnAmount+"' order by t.cdate desc,t.pwid desc";
		}else{
//			sql+="and trunc(t.cdate)>=trunc(sysdate-1) order by t.cdate desc,t.pwid desc";
			sql+="and t.cdate>=trunc(sysdate-1) order by t.cdate desc,t.pwid desc";
		}
		String sqlCount="select count(*) as counts,nvl(sum(to_number(txnamt,'FM999,999,999,999,990.00')),0) CountTxnAmount,nvl(sum(to_number(smAmount,'FM999,999,999,999,990.00')),0) sumAmount from ("+sql+")";
		List<Map<String, Object>> list2 =phoneCheckUnitDealDataDao.queryObjectsBySqlListMap(sqlCount,map);
		List<Map<String, Object>> list = phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sql, map, page, rows);
		int count=Integer.parseInt(list2.get(0).get("COUNTS").toString());
		DataGridBean dgb = new DataGridBean();
		if(list.size()>0){
			Map<String, Object> map2 = list.get(0);
			map2.put("sum", list2.get(0).get("COUNTTXNAMOUNT"));
			map2.put("sumAmount", list2.get(0).get("SUMAMOUNT"));
			list.set(0, map2);
		}
		dgb.setTotal(count);
		dgb.setRows(list);
		JsonBean json = new JsonBean();
		json.setObj(dgb);
		json.setCountTxnAmount(list2.get(0).get("COUNTTXNAMOUNT").toString());
		return json;
	}

	@Override
	public JsonBean findHistertoyScanWeChat(Object userSession, String startDay,
									  String endDay,Integer page,Integer rows,String txnAmount,String trantype) {
		
		UserBean userBean = (UserBean) userSession;
		
		Map<String, Object> map = new HashMap<String, Object>();
		String month=null;
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotEmpty(startDay) && StringUtils.isNotEmpty(endDay)){
			if(startDay.substring(0,7).equals(startDay.substring(0,7))){
				month=startDay.substring(5,7);
			}else{
				month=dateFormat.format(new Date()).substring(5,7);
			}
		}else{
			month=dateFormat.format(new Date()).substring(5,7);
		}
//		type, integer, optional, 交易方式:0-银行卡、1-微信、2-支付宝、3-银联扫码、4-快捷、5-手机Pay、6-信用卡还款
		String tSql=" select t.mid," +
				" case " +
				" when t.type=1 then '微信' " +
				" when t.type=2 then '支付宝' " +
				" when t.type=3 then '银联扫码' " +
				" when t.type=4 then '快捷' " +
				" when t.type=6 then '信用卡还款' else '其它' end FIID, " +
				" to_char(t.txnamount, 'FM999,999,999,990.00') txnamt, " +
				"        t.txnday cdate,t.mnamt, to_char(cc.cashamt, 'FM999,999,999,990.00') smAmount" +
				"   from check_unitdealdetail_"+month+" t,check_cash cc" +
				"  where cc.orpkwid = t.pkid and cc.cashmode in (5, 6, 8)" +
//				"    and t.ismpos = 0" +
				"    and t.type not in (0, 5)" +
				"    and t.txnstate = 0 " ;
		if(null!=userBean) {
			if (userBean.getUseLvl() == 3) {
				tSql += " and cc.mid=:MID ";
				map.put("MID", userBean.getLoginName());
			} else {
				if (userBean.getUnitLvl() == 0) {
					// 总公司级别角色用户
				} else if (userBean.getUnitLvl() == 2) {
					// 代理商级别用户
					tSql += "  and  cc.mid "
							+ " in(select c.mid from bill_merchantinfo c where c.unno=:UNNO) "
							
							+" t.unno in (select unno from sys_unit start with unno=:UNNO connect by prior unno = upper_unit)";
					map.put("UNNO", userBean.getUnNo());
				
				} else {
					// 分公司级别商户
					tSql += " and  cc.mid"
							+ " in(select c.mid from bill_merchantinfo c where c.unno "
							+ " in( select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 ))  "
							
							+" t.unno in (select unno from sys_unit start with unno=:UNNO connect by prior unno = upper_unit)";
					map.put("UNNO", userBean.getUnNo());
					map.put("UNNO2", userBean.getUnNo());
				}
			}
		}else{
			tSql += " and cc.mid=:MID ";
			map.put("MID", "");
		}
		if((startDay!=null && !"".equals(startDay)) && (endDay!=null && !"".equals(endDay)) && (txnAmount==null || "".equals(txnAmount)) ){
			tSql+=" and t.txnday>=:startDay and t.txnday <= :endDay order by t.txnday desc ";
			map.put("startDay", startDay.replaceAll("-",""));
			map.put("endDay", endDay.replaceAll("-",""));
		}else if(txnAmount!=null && !"".equals(txnAmount)){
			tSql+=" and t.txnday>=to_char(trunc(sysdate-1),'yyyyMMdd') and t.txnamount>'"+txnAmount+"' order by t.txnday desc ";
		}else{
			tSql+=" and t.txnday>=to_char(trunc(sysdate-1),'yyyyMMdd') order by t.txnday desc ";
		}
		String sqlCount="select count(*) as counts,nvl(sum(to_number(txnamt,'FM999,999,999,999,990.00')),0) CountTxnAmount,nvl(sum(to_number(smAmount,'FM999,999,999,999,990.00')),0) sumAmount from ("+tSql+")";
		List<Map<String, Object>> list2 =phoneCheckUnitDealDataDao.queryObjectsBySqlListMap(sqlCount,map);
		List<Map<String, Object>> list = phoneCheckUnitDealDataDao.queryObjectsBySqlList2(tSql, map, page, rows);
		int count=Integer.parseInt(list2.get(0).get("COUNTS").toString());
		DataGridBean dgb = new DataGridBean();
		if(list.size()>0){
			Map<String, Object> map2 = list.get(0);
			map2.put("sum", list2.get(0).get("COUNTTXNAMOUNT"));
			map2.put("sumAmount", list2.get(0).get("SUMAMOUNT"));
			list.set(0, map2);
		}
		dgb.setTotal(count);
		dgb.setRows(list);
		JsonBean json = new JsonBean();
		json.setObj(dgb);
		json.setCountTxnAmount(list2.get(0).get("COUNTTXNAMOUNT").toString());
		return json;
	}

	public JsonBean findHistertoy2(Object userSession, Integer page, Integer rows,
			Integer code) {
		UserBean userBean = (UserBean) userSession;
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "  select t.mid,t.STAN,t.TID , t.CardPAN , t.cbrand ,t.TxnDay,t.txndate,t.TxnAmount ,t.mda,t.mnamt ,t.txntype,t.authcode,t.rrn" +
					 "  from  CHECK_UNITDEALDETAIL t " +
					 " where  1=1 ";
		if (userBean.getUseLvl() == 3) {
			sql+= " and t.mid=:MID ";
			map.put("MID", userBean.getLoginName());
		} else {
			if (userBean.getUnitLvl() == 0) {
				// 总公司级别角色用户
			} else if (userBean.getUnitLvl() == 2) {
				// 代理商级别用户
				sql += "  and  t.mid "
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno=:UNNO) ";
				map.put("UNNO", userBean.getUnNo());
			} else {
				// 分公司级别商户
				sql += " and  t.mid"
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno "
						+ " in( select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 ))  ";
				map.put("UNNO", userBean.getUnNo());
				map.put("UNNO2", userBean.getUnNo());
			}
		}
	
		if(code==1){
			sql+="and t.txnday=to_char(sysdate-1,'yyyyMMdd')";	
		}else if(code==2){
			sql+="and t.txnday in (select to_char(sysdate - level+1, 'yyyyMMdd') b" +
					" from dual connect by level <= (select to_char(sysdate,'d')-1 from dual ))";
		 }
		String sqlCount="select count(*) as counts,nvl(sum(txnAmount),0) CountTxnAmount from ("+sql+")";
		//int count=phoneCheckUnitDealDataDao.querysqlCounts2(sqlCount, map);
		List<Map<String, Object>> list2 =phoneCheckUnitDealDataDao.queryObjectsBySqlListMap(sqlCount,map);
		List<Map<String, Object>> list = phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sql, map, page, rows);
		int count=Integer.parseInt(list2.get(0).get("COUNTS").toString());
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		
		JsonBean json = new JsonBean();
		json.setObj(dgb);
		json.setCountTxnAmount(list2.get(0).get("COUNTTXNAMOUNT").toString());
		return json;
	}

	@Override
	public JsonBean findMonthDynamicFormData(Object userSession) {

		UserBean userBean = (UserBean) userSession;
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select nvl(sum(t.txnamount),0) c  ,t.txnday a  "
				+ " from CHECK_UNITDEALDATA t  where 1=1  and t.txnday >= to_char(sysdate - 30, 'yyyymmdd') ";
		if (userBean.getUseLvl() == 3) {
			sql += " and  t.mid=:MID ";
			map.put("MID", userBean.getLoginName());
		} else {
			if (userBean.getUnitLvl() == 0) {
				// 总公司级别角色用户
			} else if (userBean.getUnitLvl() == 2) {
				// 代理商级别用户
				sql += " and  t.mid in(select c.mid from CHECK_UNITDEALDATA c where c.unno=:UNNO) ";
				map.put("UNNO", userBean.getUnNo());
			} else {
				// 分公司级别商户
				sql += " and t.mid "
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno "
						+ "in( select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 )) ";
				map.put("UNNO", userBean.getUnNo());
				map.put("UNNO2", userBean.getUnNo());
			}
		}
		sql += " group by  t.txnday order by t.txnday desc";
		String sql2 ="select to_char(sysdate - level, 'yyyymmdd') b" +
				" from dual connect by level <= 30 " +
				" order by to_char(sysdate - level, 'yyyymmdd') desc";
		String sql3="select nvl(c,0) TXNAMOUNTALL , b txnday from ("+sql+") right outer join ("+sql2+") on a=b order by txnday desc ";
		int count = phoneCheckUnitDealDataDao.executeSql2(sql, map).size();
		JsonBean json = toDealWithData(phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sql3, map, 0, 30),count);
		return json;
	}

	@Override
	public JsonBean findYearDynamicFormData(Object userSession) {
		UserBean userBean = (UserBean) userSession;
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select  a, sum(b) txnamount from " +
		"	(select to_char(to_date(t.txnday ,'yyMMdd'),'yy/mm' )as a, t.txnamount b from CHECK_UNITDEALDATA t where 1=1 ";
		if (userBean.getUseLvl() == 3) {
			sql += " and  t.mid=:MID ";
			map.put("MID", userBean.getLoginName());
		} else {
			if (userBean.getUnitLvl() == 0) {
				// 总公司级别角色用户
			} else if (userBean.getUnitLvl() == 2) {
				// 代理商级别用户
				sql += " and  t.mid in(select c.mid from CHECK_UNITDEALDATA c where c.unno=:UNNO) ";
				map.put("UNNO", userBean.getUnNo());
			} else {
				// 分公司级别商户
				sql += " and t.mid "
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno "
						+ "in( select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 )) ";
				map.put("UNNO", userBean.getUnNo());
				map.put("UNNO2", userBean.getUnNo());
			}
		}
		sql += " ) cd  group by cd.a order by a desc";
		String sql2 ="select to_char(add_months(trunc(sysdate, 'mm'),-level + 1),'yy/mm') tyear" +
						" from dual connect by level <= 12 " +
						" order by tyear desc";
		String sql3="select nvl(txnamount,0) TXNAMOUNTALL , tyear txnday from ("+sql+") right outer join ("+sql2+")  on tyear = a  order by txnday desc ";
		//int count = phoneCheckUnitDealDataDao.executeSql2(sql, map).size();
		JsonBean json = toDealWithData(phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sql3, map, 0, 12),12);
		return json;
	}

	@Override
	public DataGridBean findRealTimeRrading(UserBean user,Integer page, Integer rows) {
		DataGridBean dgb = new DataGridBean();
		Map<String, Object> map = new HashMap<String, Object>();
		String sqls="SELECT t.pkid, t.mid, t.tid , substr(t.cardpan, 1, 6) || '******' || "
				+ "substr(t.cardpan, -4, 4) AS cardpan ,t.txnamount txnamount, t.txnday , "
				+ "to_char(to_DATE(t.txntime, 'HH24:MI:SS'), 'HH24:MI:SS') AS txndate , t.txntype, t.authcode, t.ORDERID AS stan, "
				+ "t.rrn, t.rname , t.cardtype FROM check_realtxn t where t.txnstate=0 and t.txntype in (0,5) ";
		if(user.getUseLvl() == 3){
			sqls+=" and t.UNNO=:UNNO and t.MID=:MID";
			map.put("UNNO", user.getUnNo());
			map.put("MID", user.getLoginName());
		}else if (user.getUseLvl() == 99){
			//对外开放
			sqls+=" and t.MID=:MID";
			map.put("MID", user.getLoginName());
		}else{
			if(user.getUnitLvl()==0){
			}else if (user.getUnitLvl() == 2){
				//代理商
				 sqls += " and t.UNNO=:UNNO";
					map.put("UNNO", user.getUnNo());
			}else{
				//分公司
				sqls += " and t.UNNO in(select unno from sys_unit s where s.UNNO=:UNNO or upper_unit=:UNNO2 )";
				map.put("UNNO", user.getUnNo());
				map.put("UNNO2", user.getUnNo());
			}
		}
		sqls+=" and t.txnday >=to_char(sysdate,'yyyyMMdd') " +
				" and t.txnamount not like '%null%' order by txnday,txntime desc ";
		String sqlCount="select count(*) from ("+sqls+")";
		Integer count=phoneCheckUnitDealDataDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list=phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sqls, map, page, rows);
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}
	
	@Override
	public DataGridBean findWechatRealTimeRrading(UserBean user,Integer page, Integer rows) {
		DataGridBean dgb = new DataGridBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Map<String, Object> map = new HashMap<String, Object>();
		String sqls="select t.* from check_realtxn t where t.txnstate = 0 and t.txnday='"+sdf.format(new Date())+"'  and t.ismpos=2 ";
		if(user.getUseLvl() == 3){
			sqls+=" and t.UNNO=:UNNO and t.MID=:MID";
			map.put("UNNO", user.getUnNo());
			map.put("MID", user.getLoginName());
		}else if (user.getUseLvl() == 99){
			//对外开放
			sqls+=" and t.MID=:MID";
			map.put("MID", user.getLoginName());
		}else{
			if(user.getUnitLvl()==0){
			}else if (user.getUnitLvl() == 2){
				//代理商
				 sqls += " and t.UNNO=:UNNO";
					map.put("UNNO", user.getUnNo());
			}else{
				//分公司
				sqls += " and t.UNNO in(select unno from sys_unit s where s.UNNO=:UNNO or upper_unit=:UNNO2 )";
				map.put("UNNO", user.getUnNo());
				map.put("UNNO2", user.getUnNo());
			}
		}
		sqls+=" order by txntime desc ";
		String sqlCount="select count(*) from ("+sqls+")";
		Integer count=phoneCheckUnitDealDataDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list=phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sqls, map, page, rows);
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}

	@Override
	public DataGridBean findWechatRealTimeRradingMd(UserBean user,Integer page, Integer rows) {
		DataGridBean dgb = new DataGridBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Map<String, Object> map = new HashMap<String, Object>();
		// txntype in (1,2,3,8,10)   txnstate=0成功 ismpos!=2
		String sqls="select t.* from check_realtxn t where t.txnstate = 0 and t.txnday='"+sdf.format(new Date())+"'  and t.ismpos!=2 and t.txntype in (1,2,3,8,10)";
		if(user.getUseLvl() == 3){
			sqls+=" and t.UNNO=:UNNO and t.MID=:MID";
			map.put("UNNO", user.getUnNo());
			map.put("MID", user.getLoginName());
		}else if (user.getUseLvl() == 99){
			//对外开放
			sqls+=" and t.MID=:MID";
			map.put("MID", user.getLoginName());
		}else{
			if(user.getUnitLvl()==0){
			}else if (user.getUnitLvl() == 2){
				//代理商
				sqls += " and t.UNNO=:UNNO";
				map.put("UNNO", user.getUnNo());
			}else{
				//分公司
				sqls += " and t.UNNO in(select unno from sys_unit s where s.UNNO=:UNNO or upper_unit=:UNNO2 )";
				map.put("UNNO", user.getUnNo());
				map.put("UNNO2", user.getUnNo());
			}
		}
		sqls+=" order by txntime desc ";
		String sqlCount="select count(*) from ("+sqls+")";
		Integer count=phoneCheckUnitDealDataDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list=phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sqls, map, page, rows);
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}
	@Override
	public Integer findRealTimeRradingCount(UserBean user) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sqls="select t.pkid,t.mid,t.tid, " +
				" substr(substr(t.cardpan, 3, length(t.cardpan)), 1, 6) ||'******'||" +
				" substr(substr(t.cardpan, 3, length(t.cardpan)),-4,4)  cardpan," +
				" CAST(CAST(t.txnamount AS DECIMAL(18,2))/100 AS DECIMAL(18,2)) as txnamount," +
				" t.txnday,t.txndate,t.txntype,t.authcode,t.stan ,t.rrn ,t.proccode ,t.respcode" +
				" from check_RealtimeDealDetail t,bill_merchantinfo m  ";
		if(user.getUseLvl() == 3){
			sqls+="where t.MID=m.MID and m.UNNO=:UNNO and t.MID=:MID";
			map.put("UNNO", user.getUnNo());
			map.put("MID", user.getLoginName());
		}else{
			if(user.getUnitLvl()==0){
				//总公司部门
				 sqls += "where 1=1 ";
			}else if (user.getUnitLvl() == 2){
				//代理商
				 sqls += "  where t.MID=m.MID and m.UNNO=:UNNO";
					map.put("UNNO", user.getUnNo());
			}else{
				//分公司
				sqls += "  where t.MID=m.MID and m.UNNO in(select unno from sys_unit s where s.UNNO=:UNNO or upper_unit=:UNNO2 )";
				map.put("UNNO", user.getUnNo());
				map.put("UNNO2", user.getUnNo()); 
			}
		}
		sqls+=" and t.txnday >=to_char(sysdate,'yyyyMMdd') " +
		" and t.txnamount not like '%null%' order by txnday,txndate desc ";
		String sqlCount="select count(*) from ("+sqls+")";
		Integer count=phoneCheckUnitDealDataDao.querysqlCounts2(sqlCount, map);
		return count;
	}
	
	@Override
	public Double findWechatRealTimeRradingTotal(UserBean user) {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String sqls="select nvl(sum(t.txnamount),0) sumamt from check_realtxn t where t.txnstate = 0 and t.ismpos=2  and t.txnday='"+sdf.format(new Date())+"'";
		if(user.getUseLvl() == 3){
			sqls+=" and t.UNNO=:UNNO and t.MID=:MID";
			map.put("UNNO", user.getUnNo());
			map.put("MID", user.getLoginName());
		}else if (user.getUseLvl() == 99){
			//对外开放
			sqls+=" and t.MID=:MID";
			map.put("MID", user.getLoginName());
		}else{
			if(user.getUnitLvl()==0){
				//总公司部门
			}else if (user.getUnitLvl() == 2){
				//代理商
				 sqls += " and t.UNNO=:UNNO";
					map.put("UNNO", user.getUnNo());
			}else{
				//分公司
				sqls += "  and t.UNNO in(select unno from sys_unit s where s.UNNO=:UNNO or upper_unit=:UNNO2 )";
				map.put("UNNO", user.getUnNo());
				map.put("UNNO2", user.getUnNo());
			}
		}
		BigDecimal sumAmt = (BigDecimal) phoneCheckUnitDealDataDao.queryObjectBySql(sqls, map);
		return sumAmt.doubleValue();
	}

    @Override
    public Double findWechatRealTimeRradingTotalMd(UserBean user) {
        Map<String, Object> map = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // txntype in (1,2,3,8,10)   txnstate=0成功 ismpos!=2
        String sqls="select nvl(sum(t.txnamount),0) sumamt from check_realtxn t where t.txnstate = 0 and t.ismpos!=2  and t.txnday='"+sdf.format(new Date())+"'  and t.txntype in (1,2,3,8,10) ";
        if(user.getUseLvl() == 3){
            sqls+=" and t.UNNO=:UNNO and t.MID=:MID";
            map.put("UNNO", user.getUnNo());
            map.put("MID", user.getLoginName());
        }else if (user.getUseLvl() == 99){
            //对外开放
            sqls+=" and t.MID=:MID";
            map.put("MID", user.getLoginName());
        }else{
            if(user.getUnitLvl()==0){
                //总公司部门
            }else if (user.getUnitLvl() == 2){
                //代理商
                sqls += " and t.UNNO=:UNNO";
                map.put("UNNO", user.getUnNo());
            }else{
                //分公司
                sqls += "  and t.UNNO in(select unno from sys_unit s where s.UNNO=:UNNO or upper_unit=:UNNO2 )";
                map.put("UNNO", user.getUnNo());
                map.put("UNNO2", user.getUnNo());
            }
        }
        BigDecimal sumAmt = (BigDecimal) phoneCheckUnitDealDataDao.queryObjectBySql(sqls, map);
        return sumAmt.doubleValue();
    }

	@Override
	public DataGridBean findReceiptsUpload(Object userSession, Integer page,
			Integer rows) {
		UserBean userBean = (UserBean) userSession;
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "  select t.pkid ,t.STAN,t.TID ," +
				" substr(substr(t.cardpan, 3, length(t.cardpan)), 1, 6) ||'******'||" +
				" substr(substr(t.cardpan, 3, length(t.cardpan)),-4,4)  cardpan " +
				" ,t.TxnDay, b.ifupload,b.minfo1," +
				" CAST(CAST(t.txnamount AS DECIMAL(18,2))/100 AS DECIMAL(18,2)) as txnamount" +
				" from  check_realtimedealdetail t , check_blocktradedetail b " +
				" where  1=1  and b.pkid=t.pkid ";
		if (userBean.getUseLvl() == 3) {
			sql+= " and t.mid=:MID ";
			map.put("MID", userBean.getLoginName());
		} else {
			if (userBean.getUnitLvl() == 0) {
				// 总公司级别角色用户
			} else if (userBean.getUnitLvl() == 2) {
				// 代理商级别用户
				sql += "  and  t.mid "
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno=:UNNO) ";
				map.put("UNNO", userBean.getUnNo());
			} else {
				// 分公司级别商户
				sql += " and  t.mid"
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno "
						+ " in( select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 ))  ";
				map.put("UNNO", userBean.getUnNo());
				map.put("UNNO2", userBean.getUnNo());
			}
		}
		sql+=" and b.risktype = 0 and b.ifupload in(0,2) and b.ifsettleflag !=1 order by t.TxnDay  desc";
		String sqlCount="select count(*) from ("+sql+")";
		int count=phoneCheckUnitDealDataDao.querysqlCounts2(sqlCount, map);
		List<Map<String, Object>> list = phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sql, map, page, rows);
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}

	@Override
	public DataGridBean findHistertoyCount(Object userSession, String startDay,
			String endDay, Integer page, Integer rows, String txnAmount) {
		String start=null;
		String end=null;
		UserBean userBean = (UserBean) userSession;
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "  select t.STAN,t.TID , t.CardPAN ,t.TxnDay,t.TxnAmount  from  CHECK_UNITDEALDETAIL t " +
				" where  1=1 ";
		if (userBean.getUseLvl() == 3) {
			sql+= " and t.mid=:MID ";
			map.put("MID", userBean.getLoginName());
		} else {
			if (userBean.getUnitLvl() == 0) {
				// 总公司级别角色用户
			} else if (userBean.getUnitLvl() == 2) {
				// 代理商级别用户
				sql += "  and  t.mid "
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno=:UNNO) ";
				map.put("UNNO", userBean.getUnNo());
			} else {
				// 分公司级别商户
				sql += " and  t.mid"
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno "
						+ " in( select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 ))  ";
				map.put("UNNO", userBean.getUnNo());
				map.put("UNNO2", userBean.getUnNo());
			}
		}
		if((startDay!=null && !"".equals(startDay)) && (endDay!=null && !"".equals(endDay)) && (txnAmount==null || "".equals(txnAmount)) ){
			SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
			Date s1=null;
			Date s2=null;
			try {
				s1=sf.parse(startDay);
				s2=sf.parse(endDay);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			SimpleDateFormat sf2 =new SimpleDateFormat("yyyyMMdd");
			 start=sf2.format(s1);
			 end=sf2.format(s2);
			 sql+="and t.txnday>=to_number('"+start+"') and  t.txnday <=to_number('"+end+"') order by t.TxnDay  desc";
		}else if(txnAmount!=null && !"".equals(txnAmount)){
			sql+="and t.TxnAmount>'"+txnAmount+"'  order by  t.txnAmount desc";
		}else{
			sql+="order by t.TxnDay  desc";
		}
		String countTxnAmountsql="select sum(txnAmount) CountTxnAmount from ("+sql+")";
		List<Map<String ,Object>> list=phoneCheckUnitDealDataDao.queryObjectsBySqlList2(countTxnAmountsql, map, 0, 1);
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(1);
		dgb.setRows(list);
		return dgb;
	}

	/**
	 * 动态处理直折线图--->"单位"
	 * @param lists
	 * @param count
	 * @return
	 */
	public JsonBean toDealWithData(List<Map<String,Object>> lists, int count){
		BigDecimal [] a= new BigDecimal[lists.size()];
		int sum =0;
		String numberUnits = null;
		Integer minAmount=0;
		int divisor = 0;
		Double result=0.0;
		for(int i=0;i<lists.size();i++){
			a[i]=(BigDecimal) lists.get(i).get("TXNAMOUNTALL");
		}
		Arrays.sort(a);
		for(int j=0;j<a.length;j++){
			if(a[j].intValue()>0){
				minAmount=a[j].intValue();
				break;
			}
		}
		
		if(minAmount.toString().length()==1){
			divisor=1;
			numberUnits="元";
		}
		if(minAmount.toString().length()==2){
			divisor=10;
			numberUnits="十";
		}
		if(minAmount.toString().length()==3){
			divisor=100;
			numberUnits="百";
		}
		if(minAmount.toString().length()==4){
			divisor=1000;
			numberUnits="千";
		}
		if(minAmount.toString().length()==5){
			divisor=10000;
			numberUnits="万";
		}
		if(minAmount.toString().length()==6){
			divisor=100000;
			numberUnits="十万";
		}
		if(minAmount.toString().length()==7){
			divisor=1000000;
			numberUnits="百万";
		}
		if(minAmount.toString().length()==8){
			divisor=10000000;
			numberUnits="千万";
		}
		if(minAmount.toString().length()==9){
			divisor=100000000;
			numberUnits="亿";
		}
		if(minAmount!=0){
			 BigDecimal bd = new BigDecimal((a[a.length-1].doubleValue())/divisor);  
		     result=bd.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		}
		for(int i=0;i<lists.size();i++){
			BigDecimal big= new BigDecimal(divisor);
			lists.get(i).put("TXNAMOUNTALL",((BigDecimal)lists.get(i).get("TXNAMOUNTALL")).divide(big,2,BigDecimal.ROUND_HALF_UP));
		}
		if(result<10){
			result=10.0;
		}
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(lists);
		JsonBean json= new JsonBean();
		json.setNumberUnits(numberUnits);
		json.setBeiShu(result);
		json.setMsg("查询成功！");
		json.setObj(dgb); 
		json.setSuccess(true);
		return json;
	}

	@Override
	public DataGridBean findRiskReceiptsUpload(Object userSession, 
			Integer page, Integer rows) {
		UserBean userBean = (UserBean) userSession;
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select t.pkid ,t.STAN,t.TID , " +
				"substr(substr(t.cardpan, 3, length(t.cardpan)), 1, 6) ||'******'|| " +
				"substr(substr(t.cardpan, 3, length(t.cardpan)),-4,4)  cardpan ," +
				"t.txnday txnday,b.ifupload,nvl(b.minfo1,' ') minfo1,t.txndate txndate ," +
				"CAST(CAST(t.txnamount AS DECIMAL(18,2))/100 AS DECIMAL(18,2)) as txnamount " +
				" from  check_realtimedealdetail t , check_blocktradedetail b " +
				" where  1=1  and b.pkid=t.pkid ";
		if (userBean.getUseLvl() == 3) {
			sql+= " and t.mid=:MID ";
			map.put("MID", userBean.getLoginName());
		} else {
			if (userBean.getUnitLvl() == 0) {
				// 总公司级别角色用户
			} else if (userBean.getUnitLvl() == 2) {
				// 代理商级别用户
				sql += "  and  t.mid "
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno=:UNNO) ";
				map.put("UNNO", userBean.getUnNo());
			} else {
				// 分公司级别商户
				sql += " and  t.mid"
						+ " in(select c.mid from CHECK_UNITDEALDATA c where c.unno "
						+ " in( select  unno  from SYS_UNIT where unno=:UNNO or upper_unit=:UNNO2 ))  ";
				map.put("UNNO", userBean.getUnNo());
				map.put("UNNO2", userBean.getUnNo());
			}
		}
		sql+=" and b.risktype = 1 and b.ifupload in(0,2) and b.ifsettleflag !=1 order by t.TxnDay  desc";
		String sqlCount="select count(*) from ("+sql+")";
		int count=phoneCheckUnitDealDataDao.querysqlCounts2(sqlCount, map);
		List<Map<String, Object>> list = phoneCheckUnitDealDataDao.queryObjectsBySqlList2(sql, map, page, rows);
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}
}