package com.hrt.biz.check.service.impl;

import com.hrt.biz.check.dao.PgCashbackSpecialDao;
import com.hrt.biz.check.entity.pagebean.PgCashbackSpecialBean;
import com.hrt.biz.check.service.PgCashbackSpecialService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class PgCashbackSpecialServiceImpl implements PgCashbackSpecialService {

    private PgCashbackSpecialDao pgCashbackSpecialDao;

    public PgCashbackSpecialDao getPgCashbackSpecialDao() {
		return pgCashbackSpecialDao;
	}

	public void setPgCashbackSpecialDao(PgCashbackSpecialDao pgCashbackSpecialDao) {
		this.pgCashbackSpecialDao = pgCashbackSpecialDao;
	}

	@Override
	public DataGridBean queryCustomizeActivityCashbackData(PgCashbackSpecialBean pgCashbackSpecialBean, UserBean user) {
		String sql = getCustomizeActivityCashbackDataSql(pgCashbackSpecialBean,user);
		String sqlCount = "select count(1) from ("+sql+")";
        List<Map<String,Object>> list = pgCashbackSpecialDao.queryObjectsBySqlList2(sql, null, pgCashbackSpecialBean.getPage(), pgCashbackSpecialBean.getRows());
        BigDecimal counts = pgCashbackSpecialDao.querysqlCounts(sqlCount, null);
        DataGridBean dgb = new DataGridBean();
        dgb.setRows(list);
        dgb.setTotal(counts.longValue());
        return dgb;
	}

	@Override
	public List<Map<String, Object>> exportCustomizeActivityCashbackData(PgCashbackSpecialBean pgCashbackSpecialBean,
			UserBean userBean) {
		String sql = getCustomizeActivityCashbackDataSql(pgCashbackSpecialBean,userBean);
		List<Map<String,Object>> list = pgCashbackSpecialDao.executeSql2(sql, null);
	    return list;
	}

	public String getCustomizeActivityCashbackDataSql(PgCashbackSpecialBean pgCashbackSpecialBean, UserBean user) {
		String sql = " SELECT * FROM (SELECT (SELECT t.unno FROM sys_unit t where t.un_lvl = 1"+
				" start with t.unno = m.unno connect by t.unno = prior t.upper_unit) zhongxin,"+
				" (SELECT t.un_name FROM sys_unit t where t.un_lvl = 1 start with t.unno = m.unno"+
				" connect by t.unno = prior t.upper_unit) zhongxinName,(SELECT t.unno FROM sys_unit t"+
				" where t.un_lvl = 2 start with t.unno = m.unno connect by t.unno = prior t.upper_unit) yidai,"+
				" (SELECT t.un_name FROM sys_unit t where t.un_lvl = 2 start with t.unno = m.unno"+
				" connect by t.unno = prior t.upper_unit) yidaiName,unno zuizhong,"+
				" (SELECT t.un_name FROM sys_unit t where t.unno = m.unno) zuizhongname,"+
				" m.mid,m.rname,m.contactphone,to_char(m.joinconfirmdate,'yyyyMMdd') joinconfirmdate,pc.summoney,pc.rebatetype,"+
				" pc.returnmoney,pc.returnday,pc.returntime,pc.remarks1 "+
				" FROM PG_CASHBACK_SPECIAL pc,bill_merchantinfo m"+
				" where pc.mid = m.mid) where 1=1 ";
		if(user.getUnitLvl()==0){
		}else{
			sql += " and zhongxin in (select ss.UNNO from sys_unit ss start with ss.unno ='"+user.getUnNo()+"' connect by NOCYCLE prior  ss.unno =  ss.upper_unit) ";
		}

		//一代机构精确查找
		if(StringUtils.isNotEmpty(pgCashbackSpecialBean.getYidai())) {
			sql += " and yidai = '"+pgCashbackSpecialBean.getYidai()+"'";
		}
		//一代机构名称精确查找
		if(StringUtils.isNotEmpty(pgCashbackSpecialBean.getYidaiName())) {
			sql += " and yidaiName = '"+pgCashbackSpecialBean.getYidaiName()+"'";
		}
		//活动类型精确查找
		if(null != pgCashbackSpecialBean.getRebatetype() && !"".equals(pgCashbackSpecialBean.getRebatetype())) {
			sql += " and rebatetype = "+pgCashbackSpecialBean.getRebatetype()+"";
		}
		
		 SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		//返现日期查询
		if(null != pgCashbackSpecialBean.getTxnDay() && !"".equals(pgCashbackSpecialBean.getTxnDay())
				&& null != pgCashbackSpecialBean.getTxnDay1() && !"".equals(pgCashbackSpecialBean.getTxnDay1())) {
			String startDate = sf.format(pgCashbackSpecialBean.getTxnDay());
			String endDate = sf.format(pgCashbackSpecialBean.getTxnDay1());
			sql += " and returnday >= '"+startDate+"' and returnday <= '"+endDate+"'";
		}else {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			String yesterDay= sf.format(c.getTime());
			sql += " and returnday = '"+yesterDay+"'";
		}
		return sql;
	}
	
	@Override
	public DataGridBean queryCustomizeActivityCashbackData_selectRebateType(){
		String sql = "select valuestring,name from bill_purconfigure a where type = 9 and status = 1";
		List<Map<String,Object>> list = pgCashbackSpecialDao.queryObjectsBySqlObject(sql);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("VALUESTRING", "");
        map.put("NAME", "ALL");
		list.add(0, map);;
		DataGridBean dgb = new DataGridBean();
        dgb.setRows(list);
        dgb.setTotal(list.size());
        return dgb;
	}
}
