package com.hrt.biz.bill.service.impl;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hrt.biz.bill.dao.ProducttypeInRebatetypeDao;
import com.hrt.biz.bill.entity.model.ProducttypeInRebatetypeModel;
import com.hrt.biz.bill.entity.pagebean.ProducttypeInRebatetypeBean;
import com.hrt.biz.bill.service.ProducttypeInRebatetypeService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class ProducttypeInRebatetypeServiceImpl implements ProducttypeInRebatetypeService{

	private ProducttypeInRebatetypeDao producttypeInRebatetypeDao;
	
	public ProducttypeInRebatetypeDao getProducttypeInRebatetypeDao() {
		return producttypeInRebatetypeDao;
	}

	public void setProducttypeInRebatetypeDao(ProducttypeInRebatetypeDao producttypeInRebatetypeDao) {
		this.producttypeInRebatetypeDao = producttypeInRebatetypeDao;
	}

	@Override
	public DataGridBean queryProducttypeInRebatetype(ProducttypeInRebatetypeBean bean, UserBean userSession) {
		
		  String sql="select  p.* from bill_producttypeInRebatetype p where 1=1 ";
		 
		  if(bean.getRebatetype()!=null && !"".equals(bean.getRebatetype())) {
			  sql += "and p.rebatetype = '"+bean.getRebatetype()+"'";
		  }
		  if(bean.getProducttype() != null && !"".equals(bean.getProducttype())) {
			  sql += "and p.producttype = '"+bean.getProducttype()+"'";
		  }
		  String count="select count(*) from ("+sql+")";
		  List<Map<String,String>> list = producttypeInRebatetypeDao.queryObjectsBySqlList(sql,null,bean.getPage(),bean.getRows()); 
		 
		  BigDecimal counts = producttypeInRebatetypeDao.querysqlCounts(count,null);
		  DataGridBean dgd = new DataGridBean();
		  dgd.setTotal(counts.intValue());//查询数据库总条数
		  dgd.setRows(list);
		  return dgd;
	}
	//
	@Override
	public Map queryProducttypeForId(ProducttypeInRebatetypeBean bean, UserBean userSession) {
		Map params = new HashMap();
		params.put("id", bean.getId());
		String sql = "select  p.* from bill_producttypeInRebatetype p where 1=1 " + " and p.id =:id";
		List<Map<String, String>> list = producttypeInRebatetypeDao.queryObjectsBySqlListMap(sql, params);
		if (list.size() == 1) {
			return list.get(0);
		}
		return new HashMap();
	}
	
	/**
	 * 查询产品类型
	 */
	@Override
	public DataGridBean queryProducttype() {
		String sql = "SELECT minfo1 PRODUCTTYPE FROM sys_configure WHERE groupName='ProductType'";
		List<Map<String,String>> proList = producttypeInRebatetypeDao.executeSql(sql);
		DataGridBean dgd = new DataGridBean();
		List<Object>  list = new ArrayList<Object>();
		for (int i = 0; i < proList.size(); i++) {
			Map map = proList.get(i);
			list.add(map);

		}
		dgd.setTotal(list.size());
		dgd.setRows(list);

		return dgd;
	}

	@Override
	public List queryRebatetype(ProducttypeInRebatetypeBean bean, UserBean userSession) {
		String sql="select p.* from bill_producttypeinrebatetype p where 1=1 "+
				" and p.rebatetype = "+bean.getRebatetype()+"";
		List list=producttypeInRebatetypeDao.executeSql(sql);
		return list;
	}

	@Override
	public void addProducttypeInRebatetype(ProducttypeInRebatetypeBean bean, UserBean userSession) {
		ProducttypeInRebatetypeModel model = new ProducttypeInRebatetypeModel();
		model.setRebatetype(bean.getRebatetype());
		model.setProducttype(bean.getProducttype());
		model.setCby(userSession.getLoginName());
		model.setMaintaindate(new Date());
		producttypeInRebatetypeDao.saveObject(model);
	}
	
	@Override
	public String updateProducttypeInRebatetype(ProducttypeInRebatetypeBean bean, UserBean userSession) {
		String sql = " update bill_producttypeinrebatetype p set p.producttype = '"+bean.getProducttype()+"'"+
				" ,p.cby = '"+userSession.getLoginName()+"',p.maintaindate = sysdate"+
				" where p.rebatetype = '"+bean.getRebatetype()+"'";
		Integer integer = producttypeInRebatetypeDao.executeSqlUpdate(sql, null);
		if(integer<1) {
			return "修改失败";
		}
		return "修改成功";
	}

	@Override
	public void delProducttypeInRebatetype(ProducttypeInRebatetypeBean bean, UserBean userSession) {
		//Map params = new HashMap();
		//params.put("id", bean.getId());
		String sql = "delete from bill_producttypeInRebatetype p where 1=1 " + " and p.id ="+bean.getId()+"";
		Integer integer = producttypeInRebatetypeDao.executeSqlUpdate(sql, null);
	}
	@Override
	public DataGridBean queryTerminalStatistics(ProducttypeInRebatetypeBean bean, UserBean userSession) {
		DataGridBean dgb = new DataGridBean();
		if(bean.getTxnDay()==null||"".equals(bean.getTxnDay())||bean.getTxnDay1()==null||"".equals(bean.getTxnDay1())||!bean.getTxnDay().substring(0, 6).equals(bean.getTxnDay1().substring(0, 6))) {
			return null;
		}
		bean.setTxnDay(bean.getTxnDay().replace("-", ""));
		bean.setTxnDay1(bean.getTxnDay1().replace("-", ""));
		
		String producttypeSql = getProducttypeSql(bean,userSession);
		
		List<Map<String,Object>> list = producttypeInRebatetypeDao.queryObjectsBySqlList2(producttypeSql, null, bean.getPage(), bean.getRows());
		
		String count="select count(*) from ("+producttypeSql+")";
		BigDecimal counts = producttypeInRebatetypeDao.querysqlCounts(count,null);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());//查询数据库总条数
		dgd.setRows(list);
		return dgd;
	}

	@Override
	public DataGridBean queryRebatetype() {
		String sql = "SELECT rebatetype FROM bill_producttypeinrebatetype";
		List<Map<String,String>> proList = producttypeInRebatetypeDao.executeSql(sql);
		DataGridBean dgd = new DataGridBean();
		List<Object>  list = new ArrayList<Object>();
		for (int i = 0; i < proList.size(); i++) {
			Map map = proList.get(i);
			list.add(map);
		}
		dgd.setTotal(list.size());
		dgd.setRows(list);

		return dgd;
	}

	@Override
	public DataGridBean queryTerminalStatisticsForRebatetype(ProducttypeInRebatetypeBean bean, UserBean userSession) {
		DataGridBean dgb = new DataGridBean();
		if(bean.getTxnDay()==null||"".equals(bean.getTxnDay())||bean.getTxnDay1()==null||"".equals(bean.getTxnDay1())||!bean.getTxnDay().substring(0, 6).equals(bean.getTxnDay1().substring(0, 6))) {
			return null;
		}
		bean.setTxnDay(bean.getTxnDay().replace("-", ""));
		bean.setTxnDay1(bean.getTxnDay1().replace("-", ""));
		
		String rebatetetypeSql = getRebatetetypeSql(bean,userSession);
		
		List<Map<String,Object>> list = producttypeInRebatetypeDao.executeSql2(rebatetetypeSql, null);
		
		String count="select count(*) from ("+rebatetetypeSql+")";
		BigDecimal counts = producttypeInRebatetypeDao.querysqlCounts(count,null);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());//查询数据库总条数
		dgd.setRows(list);
		return dgd;
	}

	@Override
	public List<Map<String, Object>> exportTerminalStatisticsForRebatetype(ProducttypeInRebatetypeBean bean,
			UserBean userSession) {
		DataGridBean dgb = new DataGridBean();
		if(bean.getTxnDay()==null||"".equals(bean.getTxnDay())||bean.getTxnDay1()==null||"".equals(bean.getTxnDay1())||!bean.getTxnDay().substring(0, 6).equals(bean.getTxnDay1().substring(0, 6))) {
			return null;
		}
		bean.setTxnDay(bean.getTxnDay().replace("-", ""));
		bean.setTxnDay1(bean.getTxnDay1().replace("-", ""));
		
		String rebatetetypeSql = getRebatetetypeSql(bean,userSession);
		
		return producttypeInRebatetypeDao.executeSql2(rebatetetypeSql,null);
	}
	
	@Override
	public List<Map<String, Object>> exportTerminalStatisticsForProducttype(ProducttypeInRebatetypeBean bean,
			UserBean userSession) {
		DataGridBean dgb = new DataGridBean();
		if(bean.getTxnDay()==null||"".equals(bean.getTxnDay())||bean.getTxnDay1()==null||"".equals(bean.getTxnDay1())||!bean.getTxnDay().substring(0, 6).equals(bean.getTxnDay1().substring(0, 6))) {
			return null;
		}
		bean.setTxnDay(bean.getTxnDay().replace("-", ""));
		bean.setTxnDay1(bean.getTxnDay1().replace("-", ""));
		
		String producttypeSql = getProducttypeSql(bean,userSession);
		
		return producttypeInRebatetypeDao.executeSql2(producttypeSql,null);
	}
	
	/**
	 * 终端状态统计---活动类型sql
	 */
	public String getRebatetetypeSql(ProducttypeInRebatetypeBean bean, UserBean userSession) {
		//flag1入库未出库\flag2已发货待注册\flag3已注册待激活\flag4激活量\flag5已使用非选中时间内激活
		String rebatetetypeSql = " SELECT s1 putstorenoout,s2 deliverynoregisternumber,"+
				" s3 registernoactivationnumber,s4 activationnumber,s5 activationnumbernoselecttime,rebatetype"+
				" FROM (SELECT "+
				" count(flag1) s1,count(flag2) s2,count(flag3) s3,count(flag4) s4,count(flag5) s5,a.rebatetype FROM ("+
				" SELECT"+
				" decode(t.status,0,1) flag1,"+
				" decode(t.status,1,case when (t.outdate is null or t.outdate<=to_date('"+bean.getTxnDay()+"','yyyy-mm-dd hh24:mi:ss'))"+
				" then 1 end,3,case when (t.outdate is null or t.outdate<=to_date('"+bean.getTxnDay()+"','yyyy-mm-dd hh24:mi:ss')) then 1 end) flag2,"+
				" decode(t.status,2,case when t.activaday is null then 1 end) flag3,"+
				" decode(t.status,2,case when t.activaday>='"+bean.getTxnDay()+"' and t.activaday<='"+bean.getTxnDay1()+"' then 1 end ) flag4,"+
				" decode(t.status,2,case when (t.activaday>'"+bean.getTxnDay1()+"' or t.activaday<'"+bean.getTxnDay()+"') then 1 end ) flag5,"+
				" t.rebatetype"+
				" FROM  bill_terminalinfo t, bill_producttypeinrebatetype pb"+
				" where t.rebatetype = pb.rebatetype"+
				" and t.ism35 = 1 and pb.status is null"+
				" and t.rebatetype >= 20";
		if(bean.getRebatetype()!=null && !"".equals(bean.getRebatetype())) {
			rebatetetypeSql += " and pb.rebatetype = '"+bean.getRebatetype()+"'";
		}
		rebatetetypeSql += " )a group by a.rebatetype)";
		return rebatetetypeSql;
	}
	
	/**
	 * 终端状态统计---产品类型sql
	 */
	public String getProducttypeSql(ProducttypeInRebatetypeBean bean, UserBean userSession) {
		String producttypeSql = " SELECT c.* FROM (SELECT s1 putstorenoout,"+
				" s2 deliverynoregisternumber,s3 registernoactivationnumber,s4 activationnumber,s5 activationnumbernoselecttime,"+
				" yunying,(SELECT u.un_name FROM  sys_unit u where u.unno = b.yunying) yunyingname,yidai,producttype,"+
				" (SELECT u.un_name FROM  sys_unit u where u.unno = b.yidai) yidainame"+
				" FROM (SELECT nvl(sum(flag1), 0) s1,"+
				" nvl(sum(flag2), 0) s2,nvl(sum(flag3), 0) s3,nvl(sum(flag4), 0) s4,nvl(sum(flag5), 0) s5,a.producttype,a.yunying,a.yidai"+
				" FROM (SELECT decode(t.status, 0, 1) flag1,"+
				" decode(t.status,1,case when (t.outdate is null or t.outdate <= to_date('"+bean.getTxnDay()+"','yyyy-mm-dd hh24:mi:ss')) then 1"+
				" end,3,case when (t.outdate is null or t.outdate <= to_date('"+bean.getTxnDay()+"','yyyy-mm-dd hh24:mi:ss')) then 1 end) flag2,"+
				" decode(t.status,2,case when t.activaday is null then 1 end) flag3,"+
				" decode(t.status,2,case when t.activaday >= '"+bean.getTxnDay()+"' and t.activaday <= '"+bean.getTxnDay1()+"' then 1 end) flag4,"+
				" decode(t.status,2,case when (t.activaday > '"+bean.getTxnDay1()+"' or t.activaday < '"+bean.getTxnDay()+"') then 1 end) flag5,"+
				" pb.producttype,(SELECT u.unno FROM  sys_unit u where u.un_lvl = 1 start with u.unno = t.unno"+
				" connect by prior u.upper_unit = u.unno) yunying,(SELECT u.unno FROM  sys_unit u where u.un_lvl = 2"+
				" start with u.unno = t.unno connect by prior u.upper_unit = u.unno) yidai"+
				" FROM  bill_terminalinfo t,  bill_producttypeinrebatetype pb"+
				" where t.rebatetype = pb.rebatetype"+
				" and t.isM35 = 1"+
				" and t.rebatetype >= 20"+
				" and pb.status is null) a"+
				" group by producttype, yidai, yunying) b) c where 1=1";
		if(bean.getProducttype()!=null && !"".equals(bean.getProducttype())) {
			producttypeSql += " and c.producttype = '"+bean.getProducttype()+"'";
		}
		if(bean.getYunying()!=null && !"".equals(bean.getYunying())) {
			producttypeSql += " and c.yunying= '"+bean.getYunying()+"'";
		}
		if(bean.getYunyingname()!=null && !"".equals(bean.getYunyingname())) {
			producttypeSql += " and c.yunyingname = '"+bean.getYunyingname()+"'";
		}
		return producttypeSql;
	}
}
