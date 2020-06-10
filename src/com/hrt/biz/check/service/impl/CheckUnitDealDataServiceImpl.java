package com.hrt.biz.check.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTerminalInfoModel;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.check.dao.CheckUnitDealDataDao;
import com.hrt.biz.check.entity.pagebean.ToolDealBean;
import com.hrt.biz.check.service.CheckUnitDealDataService;
import com.hrt.frame.constant.Constant;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class CheckUnitDealDataServiceImpl implements CheckUnitDealDataService {
 
	private CheckUnitDealDataDao checkUnitDealDataDao;
	private IMerchantInfoDao merchantInfoDao;
	private IUnitDao unitDao;
    private IAgentSalesDao agentSalesDao;
    private IMerchantInfoService  merchantInfoService;
    
	public IAgentSalesDao getAgentSalesDao() {
		return agentSalesDao;
	}
	public void setAgentSalesDao(IAgentSalesDao agentSalesDao) {
		this.agentSalesDao = agentSalesDao;
	}
	public IUnitDao getUnitDao() {
		return unitDao;
	}
	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}
	public IMerchantInfoDao getMerchantInfoDao() {
		return merchantInfoDao;
	}
	public void setMerchantInfoDao(IMerchantInfoDao merchantInfoDao) {
		this.merchantInfoDao = merchantInfoDao;
	}

	public CheckUnitDealDataDao getCheckUnitDealDataDao() {
		return checkUnitDealDataDao;
	}

	public void setCheckUnitDealDataDao(CheckUnitDealDataDao checkUnitDealDataDao) {
		this.checkUnitDealDataDao = checkUnitDealDataDao;
	}
	
	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}
	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	//业务员 id查询
//	public List<Map<String, String>> getdata(String txn1,String id,String txn,UserBean userBean) throws Exception{
//		//代理商业务人员--查看的数据
//		String sql="select c.tid,sum(c.REFUNDCOUNT) REFUNDCOUNT,to_char(sum(c.REFUNDAMT),'FM999,999,999,999,990.00') REFUNDAMT,sum(c.TXNCOUNT) TXNCOUNT ,to_char(sum(c.MNAMT),'FM999,999,999,999,990.00') MNAMT ,sum(c.TXNAMOUNT) TXNAMOUNT from ( select * from check_unitdealdata cc where cc.mid='"+id+"' and cc.TXNDAY between to_char(to_date('"+txn+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date('"+txn1+"','yyyy-MM-dd'),'yyyyMMdd'))  c inner join bill_merchantinfo b  on c.mid=b.mid inner join bill_agentsalesinfo a on b.maintainUserID = a.busid group by c.tid";
//		List<Map<String, String>> list=checkUnitDealDataDao.executeSql(sql);
//		return list;
//		
//	}

	//通过页面传来的id查询
	@Override
	public List<Map<String, String>> getOrder(ToolDealBean toolDealBean,UserBean userBean) throws Exception {
//		String untino=userBean.getUnNo();
		String sql="select b.rname,to_char(bb.TXNAMOUNT, 'FM999,999,999,999,990.00') TXNAMOUNT,bb.REFUNDCOUNT,bb.TXNCOUNT," +		
  "to_char(nvl(bb.MDA,0),'FM999,999,999,999,990.00') MDA,to_char(nvl(bb.REFUNDMDA,0),'FM999,999,999,999,990.00') REFUNDMDA,"+				
				"bb.UNNO,bb.TID,to_char(bb.MNAMT, 'FM999,999,999,999,990.00') MNAMT," +
				"to_char(bb.REFUNDAMT, 'FM999,999,999,999,990.00') REFUNDAMT,s.UN_NAME" +
				" from((select c.tid,c.MID,c.unno,sum(TXNCOUNT) TXNCOUNT,sum(TXNAMOUNT) TXNAMOUNT,sum(REFUNDCOUNT) REFUNDCOUNT," +
				"sum(REFUNDAMT) REFUNDAMT,sum(MDA) MDA,sum(REFUNDMDA) REFUNDMDA,sum(MNAMT) MNAMT from check_UnitDealData c" +
				" where c.MID='"+toolDealBean.getMid()+"' and c.TXNDAY between to_char(to_date('"+toolDealBean.getTxnDate()+"','yyyy-MM-dd'),'yyyyMMdd')" +
						" and to_char(to_date('"+toolDealBean.getTxnDate1()+"','yyyy-MM-dd'),'yyyyMMdd') group by c.TID,c.MID,c.unno) bb" +
										" inner join bill_MerchantInfo b on  b.MID=bb.MID inner join sys_unit s " +
										"on s.unno=bb.unno  ) where 1=1 ";
	     	List<Map<String, String>> list=checkUnitDealDataDao.executeSql(sql);
		return list;
	}
	
/*
 * 显示全部
 */

	@Override
	public DataGridBean queryCheckUnitDealData(ToolDealBean toolDealBean,UserBean userBean) {
		String sql=null;String sql11=null;String sqlsumcount=null;
		Calendar cal = Calendar.getInstance();cal.add(Calendar.DAY_OF_YEAR, -1);
//		Date d = cal.getTime();
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
//			 String reTime = df.format(d);
			 
		String sqlExcel=null;
		  DataGridBean dgd = new DataGridBean();
	    UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
//		String untino=userBean.getUnNo();
		map1.put("unNO1", userBean.getUnNo());
		String untisql="select * from sys_unit s where s.unNo=:unNO1";
//		List<UnitModel> unitlist = unitDao.executeSqlT(untisql, UnitModel.class,map1);
		sql="select b.unno, b.rname,b.ism35,b.settmethod, bb.MID, "+
			            "   sum(bb.REFUNDCOUNT) REFUNDCOUNT, "+
			             "  to_char(sum(bb.REFUNDAMT), 'FM999,999,999,999,990.00') REFUNDAMT, "+
			            "   to_char(nvl(sum(bb.MDA), 0), 'FM999,999,999,999,990.00') MDA, "+
			             "  to_char(nvl(sum(bb.REFUNDMDA), 0), 'FM999,999,999,999,990.00') REFUNDMDA, "+
			             "  nvl(sum(bb.TXNCOUNT), 0) TXNCOUNT, "+
			            "   to_char(nvl(sum(bb.MNAMT), 0), 'FM999,999,999,999,990.00') MNAMT, "+
			             "  to_char(nvl(sum(bb.TXNAMOUNT), 0), 'FM999,999,999,999,990.00') TXNAMOUNT "+
			"  from Bill_Merchantinfo b,check_UnitDealData bb  where  1=1 " ;
//		if("0".equals(userBean.getIsM35Type())){
//			sql +=" and b.isM35 !=1 ";
//			sqlExcel +=" and b.isM35 !=1 ";
//		}else if("1".equals(userBean.getIsM35Type())){
//			sql +=" and b.isM35 = 1 ";
//			sqlExcel +=" and b.isM35 = 1 ";
//		}
		if (null != toolDealBean.getTxnDay()
				&& null != toolDealBean.getTxnDay1()
				&& !toolDealBean.getTxnDay().equals("")
				&& !toolDealBean.getTxnDay1().equals("")) {
				sql += " and bb.TXNDAY between '"+ toolDealBean.getTxnDay().replace("-", "")+"' and '"+ toolDealBean.getTxnDay1().replace("-", "")+"'";
			//sqlCount+= " and bb.TXNDAY between to_char(to_date('"+ toolDealBean.getTxnDay()+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date('"+ toolDealBean.getTxnDay1()+"','yyyy-MM-dd'),'yyyyMMdd')";
		   //sqlExcel += " and bb.TXNDAY between to_char(to_date('"+ toolDealBean.getTxnDay()+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date('"+ toolDealBean.getTxnDay1()+"','yyyy-MM-dd'),'yyyyMMdd')";
		}	     
		sql+=  " and b.mid = bb.MID  ";
		sql11="select bb.UNNO,bb.MID,bb.TID,b.rname ,nvl(sum(TXNCOUNT),0) TXNCOUNT,to_char(nvl(sum(TXNAMOUNT),0), 'FM999,999,999,999,990.00') TXNAMOUNT," +
				" nvl(sum(REFUNDCOUNT),0) REFUNDCOUNT,to_char(nvl(sum(REFUNDAMT),0), 'FM999,999,999,999,990.00') REFUNDAMT," +
				"nvl(sum(MDA),0) MDA,nvl(sum(REFUNDMDA),0) REFUNDMDA,to_char(nvl(sum(MNAMT),0), 'FM999,999,999,999,990.00') MNAMT from check_UnitDealData bb inner join" +
				" bill_MerchantInfo b on  b.MID=bb.MID ";
		if(userBean.getUseLvl()==3){
			//and b.MID ='"+userBean.getLoginName()+"'
			sql+=" and b.MID in (select MID from  bill_MerchantInfo where  PARENTMID= '"+userBean.getLoginName()+"' or MID ='"+userBean.getLoginName()+"') ";
		//	sqlCount ="select count(*) from ("+sql+" ";
				sqlExcel=sql11+" and b.MID in (select MID from  bill_MerchantInfo where  PARENTMID= '"+userBean.getLoginName()+"' or MID ='"+userBean.getLoginName()+"')";
		}
		else{
		    if("110000".equals(userBean.getUnNo())){
		   //		sqlCount ="select count(*) from ("+sql;
				  sqlExcel=sql11;
		    }
		    else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			    UnitModel parent = unitModel.getParent();
			    if("110000".equals(parent.getUnNo())){
			    //	sqlCount ="select count(*) from ("+sql;
			     sqlExcel=sql11;
			  }
		   }
//		    else{
//			   String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
//			   sql+=" and b.unno IN ("+childUnno+")";
//			   sqlExcel=sql11+" and b.unno IN ("+childUnno+")";
//
//		   }
		}
		if(sql==null){return dgd;}
		 //条件查询  
		if ((null == toolDealBean.getTxnDay()&& null == toolDealBean.getTxnDay1())||toolDealBean.getTxnDay().equals("")&&toolDealBean.getTxnDay1().equals("")){
		     return null;
	     }
	    if(null!=toolDealBean.getRname()&&!toolDealBean.getRname().equals("")){
			sql += " and b.RNAME like '"+toolDealBean.getRname()+"%'"+"";
		//	sqlCount+=" and b.RNAME like '"+"%"+toolDealBean.getRname()+"%'"+"";
			sqlExcel+= " and b.RNAME like '"+toolDealBean.getRname()+"%'"+"";
		}
	    if(null!=toolDealBean.getUnno()&&!toolDealBean.getUnno().equals("")){
	    	sql += " and b.unno in ("+merchantInfoService.queryUnitUnnoUtil(toolDealBean.getUnno())+") ";
	    	sqlExcel+= " and b.unno in ("+merchantInfoService.queryUnitUnnoUtil(toolDealBean.getUnno())+") ";
	    }else if(!"110000".equals(userBean.getUnNo()) && !(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0)){
			  String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			  sql+=" and b.unno IN ("+childUnno+")";
			  sqlExcel=sql11+" and b.unno IN ("+childUnno+")";
	    	
		}
		if(null!=toolDealBean.getMid()&&!toolDealBean.getMid().equals("")){
			sql+=" and b.MID = '"+toolDealBean.getMid()+"'";
		//	sqlCount+=" and bb.MID = '"+toolDealBean.getMid()+"'";
			sqlExcel+=" and b.MID = '"+toolDealBean.getMid()+"'";
		}
		if(null!=toolDealBean.getTxnType()&&toolDealBean.getTxnType().equals("1")){
			sql+=" and b.ism35 != 1 ";
			sqlExcel+=" and b.ism35 != 1 ";
		}else if (null!=toolDealBean.getTxnType()&&toolDealBean.getTxnType().equals("2")){
			sql+=" and b.ism35 = 1 and b.settmethod='000000' ";
			sqlExcel+=" and b.ism35 = 1 and b.settmethod='000000' ";
		}else if (null!=toolDealBean.getTxnType()&&toolDealBean.getTxnType().equals("3")){
			sql+=" and b.ism35 = 1 and ( b.settmethod='100000' or b.settmethod='200000' )";
			sqlExcel+=" and b.ism35 = 1 and ( b.settmethod='100000' or b.settmethod='200000' ) ";
		}
			
			sql+="  group by bb.MID, tid, b.unno, b.rname, b.ism35, b.settmethod ";
			//sqlCount="select count(*) from ("+sql+")";
			sqlExcel+=" group by  bb.UNNO,bb.MID,bb.TID,b.rname,b.ism35,b.settmethod ";
//			sql+=" order by sum(bb.TXNAMOUNT) desc";

			sqlsumcount=" select count(*) COUNTS,nvl(sum(a.REFUNDCOUNT),0) REFUNDCOUNT,nvl(to_char(sum(replace(a.REFUNDAMT,',','')),'FM999,999,999,999,990.00'),0) REFUNDAMT," +
					"nvl(to_char(sum(replace(a.MDA,',','')),'FM999,999,999,999,990.00'),0) MDA,nvl(to_char(sum(replace(a.REFUNDMDA,',','')),'FM999,999,999,999,990.00'),0) REFUNDMDA,"+
					"nvl(sum(a.TXNCOUNT),0) TXNCOUNT ,nvl(to_char(sum(replace(a.MNAMT,',','')),'FM999,999,999,999,990.00'),0) MNAMT ,nvl(to_char(sum(replace(a.TXNAMOUNT,',','')),'FM999,999,999,999,990.00'),0)" +
					" TXNAMOUNT from ("+sql+") a";
	    //BigDecimal counts = checkUnitDealDataDao.querysqlCounts(sqlCount, null);
		//if(counts==null){counts=new BigDecimal(0); }
	    List proList = checkUnitDealDataDao.queryObjectsBySqlList(sql,  map, toolDealBean.getPage(), toolDealBean.getRows());
	    List porListSum=checkUnitDealDataDao.executeSql(sqlsumcount);
	    
	    Map<String,String> mapSum=new HashMap<String, String>();
		Integer count=0;
			   Map mo=(Map)porListSum.get(0);
			   count=Integer.parseInt(mo.get("COUNTS").toString());
			   mapSum.put("REFUNDCOUNT", mo.get("REFUNDCOUNT").toString());
			   mapSum.put("REFUNDAMT", mo.get("REFUNDAMT").toString());
			   mapSum.put("TXNCOUNT", mo.get("TXNCOUNT").toString());
			   mapSum.put("MNAMT", mo.get("MNAMT").toString());
			   mapSum.put("TXNAMOUNT", mo.get("TXNAMOUNT").toString());
			   mapSum.put("MDA", mo.get("MDA").toString());
			   mapSum.put("REFUNDMDA", mo.get("REFUNDMDA").toString());
			   proList.add(0, mapSum);
	    dgd.setTotal(count);
		dgd.setRows(proList);
		return dgd;		
	}
	
	/*
	 * 手刷采购返利统计报表
	 */
	@Override
	public DataGridBean queryIsM35RebateCheckData(ToolDealBean toolDealBean,UserBean userBean) {
		DataGridBean dgd = new DataGridBean();
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = " select bb.* from (select * from (select (select s.unno from sys_unit s  where s.upper_unit in ('110000', '991000','111000','341000','121000','b10000', 'j91000','b11000','d41000','b21000') " +
			" start with s.unno = t1.UNNO  connect by NOCYCLE s.unno = prior s.upper_unit  and rownum = 1) as yidai," +
			" t1.unno, t1.sn as sn1,d1.mid as hrt_mid, to_char(t1.keyconfirmdate,'yyyy-MM-dd') as keyconfirmdate, sum(d1.txnamount) as txnamount,mc.machinemodel," +
			" to_char(t1.usedconfirmdate,'yyyy-MM-dd') as usedconfirmdate,sum(d1.txncount) as txncount, t1.rebateType "+
            "  from (select t.termid,t.unno,t.sn,t.keyconfirmdate,t.usedconfirmdate,t.rebateType from bill_terminalinfo t where t.ism35 = 1 ";
      //激活日期 返机器
		if("1".equals(toolDealBean.getCbrand())&&null != toolDealBean.getTxnDay()&& null != toolDealBean.getTxnDay1()&& !toolDealBean.getTxnDay().equals("")&& !toolDealBean.getTxnDay1().equals("")) {
			sql += " and t.usedconfirmdate between to_date('"+toolDealBean.getTxnDay()+" 00:00:00','yyyy-MM-dd hh24:mi:ss') and to_date('"+toolDealBean.getTxnDay1()+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
		}else if (!"".equals(toolDealBean.getCbrand())&&null != toolDealBean.getTxnDay()&& null != toolDealBean.getTxnDay1()&& !toolDealBean.getTxnDay().equals("")&& !toolDealBean.getTxnDay1().equals("")) {
			//导入日期 返款 +分期+押金
			sql += " and t.keyconfirmdate between to_date('"+toolDealBean.getTxnDay()+" 00:00:00','yyyy-MM-dd hh24:mi:ss') and to_date('"+toolDealBean.getTxnDay1()+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
		}else{
			return dgd;
		}     
		if(userBean.getUseLvl()==3){
			return dgd;		
		}
		else{
			UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
			if("110000".equals(userBean.getUnNo())){
			}
			else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else{
				sql+=" and t.unno in ("+merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo())+") ";
			}
		}
		if(null!=toolDealBean.getUnno()&&!toolDealBean.getUnno().equals("")){
			sql += " and t.unno in ("+merchantInfoService.queryUnitUnnoUtil(toolDealBean.getUnno())+") ";
		}
		if(null==toolDealBean.getCbrand()){
		}else{
			sql+=" and t.rebateType='"+toolDealBean.getCbrand()+"' ";
		}
		sql+=")t1,(select d.tid, d.mid, d.txnamount, d.txncount from check_unitdealdata_"+toolDealBean.getTxnDate().replace("-", "").substring(4, 6)+" d where d.txnday between ";
      //交易日期
		if (null != toolDealBean.getTxnDate()&& null != toolDealBean.getTxnDate1()&& !toolDealBean.getTxnDate().equals("")&& !toolDealBean.getTxnDate1().equals("")) {
			sql += "  to_char(to_date('"+toolDealBean.getTxnDate()+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date('"+toolDealBean.getTxnDate1()+"','yyyy-MM-dd'),'yyyyMMdd') ";
		}else{
			return dgd;
		}
		if(null!=toolDealBean.getMid()&&!toolDealBean.getMid().equals("")){
			sql+=" and d.MID = '"+toolDealBean.getMid()+"'";
		}
        sql+=") d1,bill_merchantterminalinfo f, bill_machineinfo mc where t1.termid = d1.tid and f.bmaid=mc.bmaid and f.tid=t1.termid and f.mid =d1.mid and f.tid=d1.tid " ;
        sql+=" group by t1.unno, t1.sn, d1.mid, t1.keyconfirmdate,t1.usedconfirmdate, t1.rebateType,machinemodel) a "+
            //返现机具出售日期超限 0正常 1 超限
//          " decode(t.rebateType,2,,0) "+
            " left join check_isM35Rebate m  on a.sn1 = m.sn and m.mid=a.hrt_mid)bb where 1=1 ";
        if(null!=toolDealBean.getTxnType()&&toolDealBean.getTxnType().equals("1")){
			sql+=" and bb.REBATEDATE is not null ";
		}else if (null!=toolDealBean.getTxnType()&&toolDealBean.getTxnType().equals("2")){
			sql+=" and bb.REBATEDATE is null ";
		}
        sql+=" order by bb.keyconfirmdate ,bb.usedconfirmdate desc ";
		String sqlsumcount = "select count(*) from ("+sql+")";
		List<Map<String, String>> listMap = checkUnitDealDataDao.queryObjectsBySqlList(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		BigDecimal sum=checkUnitDealDataDao.querysqlCounts(sqlsumcount, null);
		dgd.setTotal(sum.intValue());
		dgd.setRows(listMap);
		return dgd;		
	}
	
	/*
	 * mpos激活统计报表
	 */
	@Override
	public DataGridBean queryIsM35RebateCheckData1(ToolDealBean toolDealBean,UserBean userBean) {
		DataGridBean dgd = new DataGridBean();
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select bb.* from (select a.*,m.REBATEDATE,m.REBATEAMT,m.CDATE,"
				+ "(select un_name from sys_unit where unno = a.yidai) as yidainame,"
				+ "(select un_name from sys_unit where unno=a.unno) as un_name from ("
				+ "select (select s.unno from sys_unit s where "
//				+ " s.upper_unit in ('110000','991000','111000','341000','121000','b10000','j91000','b11000','d41000','b21000') "
				+ " s.un_lvl = 2 start with s.unno = t1.UNNO connect by NOCYCLE s.unno = prior s.upper_unit and rownum = 1) as yidai,"
				+ "t1.unno,t1.sn as sn1,f.mid as hrt_mid,to_char(t1.keyconfirmdate, 'yyyy-MM-dd') as keyconfirmdate,"
				+ "mc.machinemodel,to_char(t1.usedconfirmdate, 'yyyy-MM-dd') as usedconfirmdate,"
				+ "t1.rebateType from (select t.termid,t.unno,t.sn,t.keyconfirmdate,t.usedconfirmdate,t.rebateType "
				+ "from bill_terminalinfo t where t.ism35 = 1 ";
		if(userBean.getUseLvl()==3){
			return dgd;		
		}else{
			UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
			if("110000".equals(userBean.getUnNo())){
			}
			else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else{
				sql+=" and t.unno in ("+merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo())+") ";
			}
		}
		if(null!=toolDealBean.getUnno()&&!toolDealBean.getUnno().equals("")){
			sql += " and t.unno in ("+merchantInfoService.queryUnitUnnoUtil(toolDealBean.getUnno())+") ";
		}
		if(null==toolDealBean.getCbrand()){
		}else{
			sql+=" and t.rebateType='"+toolDealBean.getCbrand()+"'";
		}
		sql+=") t1,bill_merchantterminalinfo f,bill_machineinfo mc "
				+ "where f.bmaid = mc.bmaid and f.tid = t1.termid ";
		//商户编号
		if(null!=toolDealBean.getMid()&&!toolDealBean.getMid().equals("")){
			sql+="and f.mid = '"+toolDealBean.getMid()+"' ";
		}
		sql +=") a left join check_isM35Rebate m on a.sn1 = m.sn and m.mid = a.hrt_mid "
			+ "where REBATEDATE between ";
		//返利日期
		if (null != toolDealBean.getTxnDate()&& null != toolDealBean.getTxnDate1()&& !toolDealBean.getTxnDate().equals("")&& !toolDealBean.getTxnDate1().equals("")) {
			sql += " to_char(to_date('"+toolDealBean.getTxnDate()+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date('"+toolDealBean.getTxnDate1()+"','yyyy-MM-dd'),'yyyyMMdd') ";
		}else{
			return dgd;
		}
		sql += ") bb where 1 = 1 order by bb.keyconfirmdate, bb.usedconfirmdate desc";
		String sqlsumcount = "select count(*) from ("+sql+")";
		List<Map<String, String>> listMap = checkUnitDealDataDao.queryObjectsBySqlList(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		BigDecimal sum=checkUnitDealDataDao.querysqlCounts(sqlsumcount, null);
		dgd.setTotal(sum.intValue());
		dgd.setRows(listMap);
		return dgd;		
	}
	
	//Excel 导入手刷返利
	@Override
	public List<String> addIsM35RebateCheckData(String xlsfile, UserBean user,String fileName, String rebateType) {
		List errlist = new ArrayList();
		try {
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);
			Date date = new Date();
			if("1".equals(rebateType)){
				//返机具导入
				for(int i = 1; i < sheet.getLastRowNum()+1; i++){
					HSSFRow row = sheet.getRow(i);
					HSSFCell cell;
					cell = row.getCell((short)0);
					if(cell == null || cell.toString().trim().equals("")){
						break;
					}else{
						//1.mid
						row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
						String mid = row.getCell((short)0).getStringCellValue().trim();
						//2.SN号
						row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
						String sn = row.getCell((short)1).getStringCellValue().trim();
						//3.返利日期 
						row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
						String rebateDate = ((row.getCell((short)2).getStringCellValue().trim()));
						
						String sql = "select count(1) from bill_merchantterminalinfo tm,bill_terminalinfo t where tm.maintaintype!='D' and" +
								" tm.approvestatus='Y' and t.termid=tm.tid and t.rebateType=1 and tm.mid='"+mid+"' and t.sn='"+sn+"'";
						Integer count = agentSalesDao.querysqlCounts2(sql, null);
//						long count = agentSalesDao.queryCounts("select count(1) from bill_merchantterminalinfo tm,bill_terminalinfo t where tm.maintaintype!='D' and" +
//								" tm.approvestatus='Y' and t.termid=tm.tid and tm.mid='"+mid+"' and t.sn='"+sn+"'");
						if(count==0){
							errlist.add(new String[]{mid,sn,"返机具类型mid&sn不存在"});
							continue;
						}
						String sql1 = "select count(1) from check_ism35rebate ir where ir.mid='"+mid+"' and ir.sn='"+sn+"'";
						Integer count1 = agentSalesDao.querysqlCounts2(sql1, null);
						if(count1>0){
							errlist.add(new String[]{mid,sn,"已返机具"});
							continue;
						}
						Map<String ,Object> map= new HashMap<String, Object>();
						String insert = "insert into check_ism35rebate (mid,sn,RebateDate,CDATE,cby) values(:mid,:sn,:RebateDate,:CDATE,:cby)";
						map.put("mid", mid);
						map.put("sn", sn);
						map.put("RebateDate", rebateDate);
						map.put("CDATE", date);
						map.put("cby", user.getUserName());
						Integer in = agentSalesDao.executeSqlUpdate(insert, map);
					}
				}
			}else{
				//返现导入
				for(int i = 1; i < sheet.getLastRowNum()+1; i++){
					HSSFRow row = sheet.getRow(i);
					HSSFCell cell;
					cell = row.getCell((short)0);
					if(cell == null || cell.toString().trim().equals("")){
						break;
					}else{
						//1.mid
						row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
						String mid = row.getCell((short)0).getStringCellValue().trim();
						//2.SN号
						row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
						String sn = row.getCell((short)1).getStringCellValue().trim();
						//3.返利日期 
						row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
						String rebateDate = ((row.getCell((short)2).getStringCellValue().trim()));
						//4.返利金额
						row.getCell((short)3).setCellType(Cell.CELL_TYPE_NUMERIC);
						Double rebateAmt = ((row.getCell((short)3).getNumericCellValue()));
						//取消返利类型限制，改为非NULL，非0
						String sql = "select count(1) from bill_merchantterminalinfo tm,bill_terminalinfo t where tm.maintaintype!='D' and" +
						" tm.approvestatus='Y' and t.termid=tm.tid and t.rebateType is not null and t.rebatetype !='0' and tm.mid='"+mid+"' and t.sn='"+sn+"'";
						Integer count = agentSalesDao.querysqlCounts2(sql, null);
						//						long count = agentSalesDao.queryCounts("select count(1) from bill_merchantterminalinfo tm,bill_terminalinfo t where tm.maintaintype!='D' and" +
						//								" tm.approvestatus='Y' and t.termid=tm.tid and tm.mid='"+mid+"' and t.sn='"+sn+"'");
						if(count==0){
							errlist.add(new String[]{mid,sn,"返款类型mid&sn不存在"});
							continue;
						}
						//查询返利类型
						Integer rebate = agentSalesDao.querysqlCounts2("select rebateType from bill_terminalinfo where sn='"+sn+"'", null);
						//添加活动24、25、56可多次添加返利记录----ztt20191204
						//活动设备允许多次导入需求--20200521
						String rebateToString = rebate+"";
						if(rebate!=4 &&!Constant.ism35rebate_2.contains(rebateToString)
								&&!Constant.ism35rebate_3.contains(rebateToString)
								&&!Constant.ism35rebate_4.contains(rebateToString)
								&&!Constant.ism35rebate_5.contains(rebateToString)
								&&!Constant.ism35rebate_n.contains(rebateToString)){
							String sql1 = "select count(1) from check_ism35rebate ir where ir.mid='"+mid+"' and ir.sn='"+sn+"'";
							Integer count1 = agentSalesDao.querysqlCounts2(sql1, null);
							if(count1>0){
								errlist.add(new String[]{mid,sn,"已返现"});
								continue;
							}
						}
						if(Constant.ism35rebate_2.contains(rebateToString)){
							String sql1 = "select count(1) from check_ism35rebate ir where ir.mid='"+mid+"' and ir.sn='"+sn+"'";
							Integer count1 = agentSalesDao.querysqlCounts2(sql1, null);
							if(count1>0&&count1<2){//已返现的，新加
								Map<String ,Object> map= new HashMap<String, Object>();
								String insert = "insert into check_ism35rebate (mid,sn,RebateDate,RebateAmt,tpye1,CDATE,cby) values(:mid,:sn,:RebateDate,:RebateAmt,:tpye1,:CDATE,:cby)";
								map.put("mid", mid);
								map.put("sn", sn);
								map.put("RebateDate", rebateDate);
								map.put("RebateAmt", rebateAmt);
								map.put("tpye1", 1);
								map.put("CDATE", date);
								map.put("cby", user.getUserName());
								Integer in = agentSalesDao.executeSqlUpdate(insert, map);
								continue;
							}else if(count1>=2){
								errlist.add(new String[]{mid,sn,"返现次数已达2次"});
								continue;
							}
						}
						if(Constant.ism35rebate_3.contains(rebateToString)){
							String sql1 = "select count(1) from check_ism35rebate ir where ir.mid='"+mid+"' and ir.sn='"+sn+"'";
							Integer count1 = agentSalesDao.querysqlCounts2(sql1, null);
							if(count1>0&&count1<3){//已返现的，新加
								Map<String ,Object> map= new HashMap<String, Object>();
								String insert = "insert into check_ism35rebate (mid,sn,RebateDate,RebateAmt,tpye1,CDATE,cby) values(:mid,:sn,:RebateDate,:RebateAmt,:tpye1,:CDATE,:cby)";
								map.put("mid", mid);
								map.put("sn", sn);
								map.put("RebateDate", rebateDate);
								map.put("RebateAmt", rebateAmt);
								map.put("tpye1", 1);
								map.put("CDATE", date);
								map.put("cby", user.getUserName());
								Integer in = agentSalesDao.executeSqlUpdate(insert, map);
								continue;
							}else if(count1>=3){
								errlist.add(new String[]{mid,sn,"返现次数已达3次"});
								continue;
							}
						}
						if(Constant.ism35rebate_4.contains(rebateToString)){
							String sql1 = "select count(1) from check_ism35rebate ir where ir.mid='"+mid+"' and ir.sn='"+sn+"'";
							Integer count1 = agentSalesDao.querysqlCounts2(sql1, null);
							if(count1>0&&count1<4){//已返现的，新加
								Map<String ,Object> map= new HashMap<String, Object>();
								String insert = "insert into check_ism35rebate (mid,sn,RebateDate,RebateAmt,tpye1,CDATE,cby) values(:mid,:sn,:RebateDate,:RebateAmt,:tpye1,:CDATE,:cby)";
								map.put("mid", mid);
								map.put("sn", sn);
								map.put("RebateDate", rebateDate);
								map.put("RebateAmt", rebateAmt);
								map.put("tpye1", 1);
								map.put("CDATE", date);
								map.put("cby", user.getUserName());
								Integer in = agentSalesDao.executeSqlUpdate(insert, map);
								continue;
							}else if(count1>=4){
								errlist.add(new String[]{mid,sn,"返现次数已达4次"});
								continue;
							}
						}
						if(Constant.ism35rebate_5.contains(rebateToString)){
							String sql1 = "select count(1) from check_ism35rebate ir where ir.mid='"+mid+"' and ir.sn='"+sn+"'";
							Integer count1 = agentSalesDao.querysqlCounts2(sql1, null);
							if(count1>0&&count1<5){//已返现的，新加
								Map<String ,Object> map= new HashMap<String, Object>();
								String insert = "insert into check_ism35rebate (mid,sn,RebateDate,RebateAmt,tpye1,CDATE,cby) values(:mid,:sn,:RebateDate,:RebateAmt,:tpye1,:CDATE,:cby)";
								map.put("mid", mid);
								map.put("sn", sn);
								map.put("RebateDate", rebateDate);
								map.put("RebateAmt", rebateAmt);
								map.put("tpye1", 1);
								map.put("CDATE", date);
								map.put("cby", user.getUserName());
								Integer in = agentSalesDao.executeSqlUpdate(insert, map);
								continue;
							}else if(count1>=5){
								errlist.add(new String[]{mid,sn,"返现次数已达5次"});
								continue;
							}
						}
						if(Constant.ism35rebate_n.contains(rebateToString)){
							String sql1 = "select count(1) from check_ism35rebate ir where ir.mid='"+mid+"' and ir.sn='"+sn+"'";
							Integer count1 = agentSalesDao.querysqlCounts2(sql1, null);
							if(count1>0){//已返现的，新加
								Map<String ,Object> map= new HashMap<String, Object>();
								String insert = "insert into check_ism35rebate (mid,sn,RebateDate,RebateAmt,tpye1,CDATE,cby) values(:mid,:sn,:RebateDate,:RebateAmt,:tpye1,:CDATE,:cby)";
								map.put("mid", mid);
								map.put("sn", sn);
								map.put("RebateDate", rebateDate);
								map.put("RebateAmt", rebateAmt);
								map.put("tpye1", 1);
								map.put("CDATE", date);
								map.put("cby", user.getUserName());
								Integer in = agentSalesDao.executeSqlUpdate(insert, map);
								continue;
							}
						}
						Map<String ,Object> map= new HashMap<String, Object>();
						String insert = "insert into check_ism35rebate (mid,sn,RebateDate,RebateAmt,CDATE,cby) values(:mid,:sn,:RebateDate,:RebateAmt,:CDATE,:cby)";
						map.put("mid", mid);
						map.put("sn", sn);
						map.put("RebateDate", rebateDate);
						map.put("RebateAmt", rebateAmt);
						map.put("CDATE", date);
						map.put("cby", user.getUserName());
						Integer in = agentSalesDao.executeSqlUpdate(insert, map);
					}
				}
			}
		}catch (IOException e) {
			System.out.println("导入手刷返利数据异常");
			e.printStackTrace();
		}	
		return errlist;
	}
	
	/*
	 * 业务员交易报表
	 */

@SuppressWarnings("unchecked")
public DataGridBean queryCheckUnitDealData_bill(ToolDealBean toolDealBean,UserBean userBean) {
	 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
//	    String untino=userBean.getUnNo();
		String sql=null;
		DataGridBean dgd = new DataGridBean();
		String sqlCount="";
		//数据条数
		Calendar cal = Calendar.getInstance();cal.add(Calendar.DAY_OF_YEAR, -1);
		Date d = cal.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
			 String reTime = df.format(d);
		Map<String, Object> map = new HashMap<String, Object>();
		//查看角色
		String sqlRole="select ROLE_ID from SYS_USER_ROLE where USER_ID="+userBean.getUserID();
		List listrole=checkUnitDealDataDao.executeSql(sqlRole);
		Map m11=null;
		for(int i=0;i<listrole.size();i++){
			 m11=(Map)listrole.get(i);
			System.out.println(m11.get("ROLE_ID"));
		}
		
		//代理商业务人员--查看的数据
		String sqlD="select * from bill_agentsalesinfo where MAINTAINTYPE!='D' and user_id="+userBean.getUserID();
		List<AgentSalesModel> agentSaleslist= agentSalesDao.executeSqlT(sqlD, AgentSalesModel.class,map);
		sql="select aa.salename,bb.UNNO,bb.MID,b.rname ,sum(bb.REFUNDCOUNT) REFUNDCOUNT,to_char(sum(bb.REFUNDAMT),'FM999,999,999,999,990.00') REFUNDAMT," +
				"sum(bb.TXNCOUNT) TXNCOUNT ,to_char(sum(bb.MNAMT),'FM999,999,999,999,990.00') MNAMT ," +
				"to_char(sum(bb.TXNAMOUNT),'FM999,999,999,999,990.00') TXNAMOUNT from check_UnitDealData bb inner join " +
				"BILL_MERCHANTTERMINALINFO c on c.TID = bb.TID and c.mid = bb.mid and c.APPROVESTATUS='Y' " +
				"inner join bill_merchantinfo b  on bb.mid=b.mid inner join bill_AgentSalesInfo aa on b.maintainUserID = aa.busid ";
		if(agentSaleslist.size()>0){
				sql+=" and b.maintainUserID='"+agentSaleslist.get(0).getBusid()+"' ";
		}else if(m11.get("ROLE_ID").toString().equals("42") || m11.get("ROLE_ID").toString().equals("45")){
			return dgd;
		}else{
			//总公司
			if("110000".equals(userBean.getUnNo())){
			}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else{
				if(null!=toolDealBean.getUnno()&&!toolDealBean.getUnno().equals("")){
					sql+=" and aa.unno=bb.unno ";
				}else{
					String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
					sql+=" and aa.unno=bb.unno and aa.unno IN ("+childUnno+") ";
				}
			}
			if(sql==null){
				sql="select mid from check_UnitDealData where 1!=1 ";
			}	
		}

		if ((null == toolDealBean.getTxnDay()&& null == toolDealBean.getTxnDay1())||toolDealBean.getTxnDay().equals("")&&toolDealBean.getTxnDay1().equals("")){
			sql+=" and bb.TXNDAY=to_char(to_date('"+reTime+"','yyyy-MM-dd'),'yyyyMMdd') ";
		}
			if(null!=toolDealBean.getRname()&&!toolDealBean.getRname().equals("")){
				sql += " and b.RNAME like '"+toolDealBean.getRname()+"%'"+"";
			}
			if(null!=toolDealBean.getMid()&&!toolDealBean.getMid().equals("")){
				sql+=" and bb.MID = '"+toolDealBean.getMid()+"'";
			}
			if (null != toolDealBean.getTxnDay()
					&& null != toolDealBean.getTxnDay1()
					&& !toolDealBean.getTxnDay().equals("")
					&& !toolDealBean.getTxnDay1().equals("")) {
				sql += " and bb.TXNDAY between to_char(to_date( '"+ toolDealBean.getTxnDay()+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+"','yyyy-MM-dd'),'yyyyMMdd')";
			}
			if(null!=toolDealBean.getUnno()&&!toolDealBean.getUnno().equals("")){
				sql+=" and bb.unno = '"+toolDealBean.getUnno()+"'";
			}
			sql+=" group by bb.mid,b.rname,bb.UNNO,aa.salename having 1=1 order by sum(bb.TXNAMOUNT) desc";
			sqlCount=" select count(*) COUNTS,nvl(sum(a.REFUNDCOUNT),0) REFUNDCOUNT,nvl(to_char(sum(replace(a.REFUNDAMT,',','')),'FM999,999,999,999,990.00'),0) REFUNDAMT," +
		"nvl(sum(a.TXNCOUNT),0) TXNCOUNT ,nvl(to_char(sum(replace(a.MNAMT,',','')),'FM999,999,999,999,990.00'),0) MNAMT ,nvl(to_char(sum(replace(a.TXNAMOUNT,',','')),'FM999,999,999,999,990.00'),0)" +
		" TXNAMOUNT from ("+sql+") a";
		List proList= checkUnitDealDataDao.queryObjectsBySqlList(sql,  map, toolDealBean.getPage(), toolDealBean.getRows());
		List porListSum=checkUnitDealDataDao.executeSql(sqlCount);
		Map<String,String> mapS=new HashMap<String, String>();
		Integer count=0;
			   Map m=(Map)porListSum.get(0);
			   count=Integer.parseInt(m.get("COUNTS").toString());
			   mapS.put("REFUNDCOUNT", m.get("REFUNDCOUNT").toString());
			   mapS.put("REFUNDAMT", m.get("REFUNDAMT").toString());
			   mapS.put("TXNCOUNT", m.get("TXNCOUNT").toString());
			   mapS.put("MNAMT", m.get("MNAMT").toString());
			   mapS.put("TXNAMOUNT", m.get("TXNAMOUNT").toString());
			   
		proList.add(0, mapS);
		dgd.setTotal(count);
		dgd.setRows(proList);
		return dgd;
	}

	
	/*
	 * 导出数据(non-Javadoc)
	 * @see com.hrt.biz.check.service.CheckUnitDealDataService#queryObjectsBySqlList(com.hrt.frame.entity.pagebean.UserBean)
	 */
	
	public List<Map<String, String>> queryObjectsBySqlList(ToolDealBean toolDealBean ,UserBean userBean){
		String sql=null;
//		String sql11=null;String sqlsumcount=null;
		Calendar cal = Calendar.getInstance();cal.add(Calendar.DAY_OF_YEAR, -1);
//		Date d = cal.getTime();
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
//			 String reTime = df.format(d);
		
		String sqlExcel=null;
//		  DataGridBean dgd = new DataGridBean();
		//数据条数
	    UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
//		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
//		String untino=userBean.getUnNo();
		//System.out.println(upperuntino);
		map1.put("unNO1", userBean.getUnNo());
//		String untisql="select * from sys_unit s where s.unNo=:unNO1";
//		List<UnitModel> unitlist = unitDao.executeSqlT(untisql, UnitModel.class,map1);
		sqlExcel="select b.unno, b.rname,b.ism35,b.settmethod, bb.MID,bb.TID, "+
		            "   sum(bb.REFUNDCOUNT) REFUNDCOUNT, "+
		             "  to_char(sum(bb.REFUNDAMT), 'FM999,999,999,999,990.00') REFUNDAMT, "+
		            "   to_char(nvl(sum(bb.MDA), 0), 'FM999,999,999,999,990.00') MDA, "+
		             "  to_char(nvl(sum(bb.REFUNDMDA), 0), 'FM999,999,999,999,990.00') REFUNDMDA, "+
		             "  nvl(sum(bb.TXNCOUNT), 0) TXNCOUNT, "+
		            "   to_char(nvl(sum(bb.MNAMT), 0), 'FM999,999,999,999,990.00') MNAMT, "+
		             "  to_char(nvl(sum(bb.TXNAMOUNT), 0), 'FM999,999,999,999,990.00') TXNAMOUNT "+
		"  from Bill_Merchantinfo b,check_UnitDealData bb  where 1=1 " ;
//		if("0".equals(userBean.getIsM35Type())){
//			sqlExcel +=" and b.isM35 !=1 ";
//		}else if("1".equals(userBean.getIsM35Type())){
//			sqlExcel +=" and b.isM35 = 1 ";
//		}
		if (null != toolDealBean.getTxnDay()
				&& null != toolDealBean.getTxnDay1()
				&& !toolDealBean.getTxnDay().equals("")
				&& !toolDealBean.getTxnDay1().equals("")) {
				//sql += " and bb.TXNDAY between to_char(to_date('"+ toolDealBean.getTxnDay()+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date('"+ toolDealBean.getTxnDay1()+"','yyyy-MM-dd'),'yyyyMMdd')";
			//sqlCount+= " and bb.TXNDAY between to_char(to_date('"+ toolDealBean.getTxnDay()+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date('"+ toolDealBean.getTxnDay1()+"','yyyy-MM-dd'),'yyyyMMdd')";
			sqlExcel += " and bb.TXNDAY between '"+ toolDealBean.getTxnDay().replace("-", "")+"' and '"+ toolDealBean.getTxnDay1().replace("-", "")+"'";
		}	     
		sqlExcel+= "  and b.mid = bb.MID  ";
		if(userBean.getUseLvl()==3){
				sqlExcel+=" and b.MID in (select MID from  bill_MerchantInfo where  PARENTMID= '"+userBean.getLoginName()+"' or MID ='"+userBean.getLoginName()+"')";
		}
		else{
		if("110000".equals(userBean.getUnNo())){
//				sqlExcel=sql11;
		}
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			//	sqlCount ="select count(*) from ("+sql;
			//	sqlExcel=sql11;
			}
		}else{
			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			sqlExcel +=" and b.unno IN ("+childUnno+")";
		}
		}
		//条件查询  
		    if ((null == toolDealBean.getTxnDay()&& null == toolDealBean.getTxnDay1())||toolDealBean.getTxnDay().equals("")&&toolDealBean.getTxnDay1().equals("")){
		    	return null;
	     	}
			if(null!=toolDealBean.getRname()&&!toolDealBean.getRname().equals("")){
				sqlExcel+= " and b.RNAME like '"+toolDealBean.getRname()+"%'"+"";
			}
			if(null!=toolDealBean.getMid()&&!toolDealBean.getMid().equals("")){
				sqlExcel+=" and b.MID = '"+toolDealBean.getMid()+"'";
			}
			if(null!=toolDealBean.getUnno()&&!toolDealBean.getUnno().equals("")){
				sqlExcel+=" and b.unno in ("+merchantInfoService.queryUnitUnnoUtil(toolDealBean.getUnno())+")";
			}
			if(null!=toolDealBean.getTxnType()&&toolDealBean.getTxnType().equals("1")){
				sql+=" and b.ism35 != 1 ";
				sqlExcel+=" and b.ism35 != 1 ";
			}else if (null!=toolDealBean.getTxnType()&&toolDealBean.getTxnType().equals("2")){
				sql+=" and b.ism35 = 1 and b.settmethod='000000' ";
				sqlExcel+=" and b.ism35 = 1 and b.settmethod='000000' ";
			}else if (null!=toolDealBean.getTxnType()&&toolDealBean.getTxnType().equals("3")){
				sql+=" and b.ism35 = 1 and ( b.settmethod='100000' or b.settmethod='200000' ) ";
				sqlExcel+=" and b.ism35 = 1 and ( b.settmethod='100000' or b.settmethod='200000' ) ";
			}
//			if (null != toolDealBean.getTxnDay()
//					&& null != toolDealBean.getTxnDay1()
//					&& !toolDealBean.getTxnDay().equals("")
//					&& !toolDealBean.getTxnDay1().equals("")) {
//			sqlExcel += " and bb.TXNDAY between to_char(to_date('"+ toolDealBean.getTxnDay()+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date('"+ toolDealBean.getTxnDay1()+"','yyyy-MM-dd'),'yyyyMMdd')";
//			}
			
//			sqlExcel+=" group by  bb.UNNO,bb.MID,bb.TID,b.rname ";
			sqlExcel+="  group by bb.MID, tid, b.unno, b.rname, b.ism35, b.settmethod ";
		List<Map<String, String>> list=checkUnitDealDataDao.queryObjectsBySqlList(sqlExcel);
		return list;
	}
	
	//业务员  --数据导出
	public List<Map<String, String>> queryObjectsBySqlList_bill(ToolDealBean toolDealBean,UserBean userBean){
		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
//		    String untino=userBean.getUnNo();
			String sql=null;
			Calendar cal = Calendar.getInstance();cal.add(Calendar.DAY_OF_YEAR, -1);
			Date d = cal.getTime();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
				 String reTime = df.format(d);
			Map<String, Object> map = new HashMap<String, Object>();
			//查看角色
			String sqlRole="select ROLE_ID from SYS_USER_ROLE where USER_ID="+userBean.getUserID();
			List listrole=checkUnitDealDataDao.executeSql(sqlRole);
			Map m11=null;
			for(int i=0;i<listrole.size();i++){
				 m11=(Map)listrole.get(i);
				System.out.println(m11.get("ROLE_ID"));
			}
			
			//代理商业务人员--查看的数据
			String sqlD="select * from bill_agentsalesinfo where MAINTAINTYPE!='D' and user_id="+userBean.getUserID();
			List<AgentSalesModel> agentSaleslist= agentSalesDao.executeSqlT(sqlD, AgentSalesModel.class,map);
			sql="select aa.salename,bb.UNNO,bb.MID,b.rname ,sum(bb.REFUNDCOUNT) REFUNDCOUNT,to_char(sum(bb.REFUNDAMT),'FM999,999,999,999,990.00') REFUNDAMT," +
					"sum(bb.TXNCOUNT) TXNCOUNT ,to_char(sum(bb.MNAMT),'FM999,999,999,999,990.00') MNAMT ," +
					"to_char(sum(bb.TXNAMOUNT),'FM999,999,999,999,990.00') TXNAMOUNT from check_UnitDealData bb inner join " +
					"BILL_MERCHANTTERMINALINFO c on c.TID = bb.TID and c.mid = bb.mid and c.APPROVESTATUS='Y' " +
					"inner join bill_merchantinfo b  on bb.mid=b.mid inner join bill_AgentSalesInfo aa on b.maintainUserID = aa.busid ";
			if(agentSaleslist.size()>0){
					sql+=" and b.maintainUserID='"+agentSaleslist.get(0).getBusid()+"' ";
			}else if(m11.get("ROLE_ID").toString().equals("42") || m11.get("ROLE_ID").toString().equals("45")){
				return null;
			}else{
				//总公司
				if("110000".equals(userBean.getUnNo())){
				}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
					UnitModel parent = unitModel.getParent();
					if("110000".equals(parent.getUnNo())){
					}
				}else{
					if(null!=toolDealBean.getUnno()&&!toolDealBean.getUnno().equals("")){
						sql+=" and aa.unno=bb.unno ";
					}else{
						String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
						sql+=" and aa.unno=bb.unno and aa.unno IN ("+childUnno+") ";
					}
				}		 			
				if(sql==null){
					sql="select mid from check_UnitDealData where 1!=1 ";
				}	
			}

			if ((null == toolDealBean.getTxnDay()&& null == toolDealBean.getTxnDay1())||toolDealBean.getTxnDay().equals("")&&toolDealBean.getTxnDay1().equals("")){
				sql+=" and bb.TXNDAY=to_char(to_date('"+reTime+"','yyyy-MM-dd'),'yyyyMMdd') ";
			}
				if(null!=toolDealBean.getRname()&&!toolDealBean.getRname().equals("")){
					sql += " and b.RNAME like '"+toolDealBean.getRname()+"%'"+"";
				}
				if(null!=toolDealBean.getMid()&&!toolDealBean.getMid().equals("")){
					sql+=" and bb.MID = '"+toolDealBean.getMid()+"'";
				}
				if (null != toolDealBean.getTxnDay()
						&& null != toolDealBean.getTxnDay1()
						&& !toolDealBean.getTxnDay().equals("")
						&& !toolDealBean.getTxnDay1().equals("")) {
					sql += " and bb.TXNDAY between to_char(to_date( '"+ toolDealBean.getTxnDay()+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+"','yyyy-MM-dd'),'yyyyMMdd')";
				}
				if(null!=toolDealBean.getUnno()&&!toolDealBean.getUnno().equals("")){
					sql+=" and bb.unno = '"+toolDealBean.getUnno()+"'";
				}
				sql+=" group by bb.mid,b.rname,bb.UNNO,aa.salename having 1=1 order by sum(bb.TXNAMOUNT) desc";
				String sql_bill=sql;
		List<Map<String, String>> list=checkUnitDealDataDao.queryObjectsBySqlList(sql_bill);
		return list;
	}
	
	/**
	 * 业务员明细报表查询
	 */
	@Override
	public DataGridBean queryToolDealDatas(ToolDealBean toolDealBean, UserBean userBean)
			throws Exception {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
//		String untino=userBean.getUnNo();
		//查看角色
			String sqlRole="select ROLE_ID from SYS_USER_ROLE where USER_ID="+userBean.getUserID();
			List listrole=checkUnitDealDataDao.executeSql(sqlRole);
			Map m11=null;
			for(int i=0;i<listrole.size();i++){
				 m11=(Map)listrole.get(i);
				System.out.println(m11.get("ROLE_ID"));
			}
//		 Calendar cal = Calendar.getInstance();cal.add(Calendar.DAY_OF_YEAR, -1);
//			Date d = cal.getTime();
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
//				 String reTime = df.format(d);
		DataGridBean dgb = new DataGridBean();
		Map<String, Object> param=new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		String sqlming="select bb.MID,bb.UNNO,bb.TID,b.RNAME ,sum(bb.REFUNDCOUNT) REFUNDCOUNT," +
				"to_char(sum(bb.REFUNDAMT),'FM999,999,999,999,990.00') REFUNDAMT,sum(bb.TXNCOUNT) TXNCOUNT," +
				"to_char(sum(bb.MNAMT),'FM999,999,999,999,990.00') MNAMT ,sum(bb.TXNAMOUNT) TXNAMOUNT, c.INSTALLEDNAME " +
				" from check_UnitDealData bb  inner join bill_merchantinfo b on  bb.mid = b.mid inner join " +
				"BILL_MERCHANTTERMINALINFO c on c.TID = bb.TID  and c.mid = bb.mid " +
				"and c.APPROVESTATUS='Y' and c.maintaintype!='D' ";
		String sqlD="select * from bill_agentsalesinfo where maintaintype!='D' and user_id="+userBean.getUserID();
		List<AgentSalesModel> agentSaleslist= agentSalesDao.executeSqlT(sqlD, AgentSalesModel.class,map);
		if(agentSaleslist.size()>0){
			sqlming+="  and b.maintainUserID='"+agentSaleslist.get(0).getBusid()+"' ";
		}else if(m11.get("ROLE_ID").toString().equals("42") || m11.get("ROLE_ID").toString().equals("45")){
			return dgb;
		}else{
			//总公司
			if("110000".equals(userBean.getUnNo())){
				
			}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else{
				String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
				sqlming+=" inner join bill_agentsalesinfo a on b.maintainUserID = a.busid  and a.unno=bb.unno and  a.unno in("+childUnno+") ";
			}
	    }
		if ((null == toolDealBean.getTxnDayEnd()&& null == toolDealBean.getTxnDayStart())||toolDealBean.getTxnDayEnd().equals("")&&toolDealBean.getTxnDayStart().equals("")){
			return null;
		}
		if (null != toolDealBean.getTxnDayStart()
				&& null != toolDealBean.getTxnDayEnd()
				&& !toolDealBean.getTxnDayStart().equals("")
				&& !toolDealBean.getTxnDayEnd().equals("")) {
			sqlming += " and bb.TXNDAY between to_char(to_date('"+ toolDealBean.getTxnDayStart()+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date('"+ toolDealBean.getTxnDayEnd()+"','yyyy-MM-dd'),'yyyyMMdd')";
		}
		if (toolDealBean.getMid() != null && !"".equals(toolDealBean.getMid())) {
			sqlming += " AND bb.MID = :mid" ;
			param.put("mid",toolDealBean.getMid());
		}
		
		if (toolDealBean.getFirsix() != null && !"".equals(toolDealBean.getFirsix())) {
		sqlming +="  and  bb.mid in ( select mid from CHECK_UNITDEALDETAIL dd where bb.tid=dd.tid and substr(dd.CARDPAN,1,6)='"+toolDealBean.getFirsix()+"'  ";
	                }
		if (toolDealBean.getFirsix() == null || "".equals(toolDealBean.getFirsix())) {
			sqlming +=" and  bb.mid in (select mid from CHECK_UNITDEALDETAIL dd where bb.tid=dd.tid and 1=1  ";
		           }
	    if (toolDealBean.getLastfour() != null && !"".equals(toolDealBean.getLastfour())) {
		    sqlming += " AND substr(dd.CARDPAN,(length(dd.CARDPAN)-3),length(dd.CARDPAN))='"+toolDealBean.getLastfour()+"' )" ;
	              }	
	   if (toolDealBean.getLastfour() == null || "".equals(toolDealBean.getLastfour())) {
		          sqlming += " )" ;
	           }	
			
		sqlming += " group by bb.Tid,b.rname,bb.UNNO,bb.MID,c.INSTALLEDNAME  order by TXNAMOUNT desc";


		
		
//		if (toolDealBean.getSort() != null) {
//			sqlming += " order by " + toolDealBean.getSort() + " " + toolDealBean.getOrder();
//		}
		List<Map<String, String>> listMap = checkUnitDealDataDao.queryObjectsBySqlList(sqlming, param, toolDealBean.getPage(), toolDealBean.getRows());
		String sqlCount = "select count(*) from ("+sqlming+")";
		BigDecimal counts = checkUnitDealDataDao.querysqlCounts(sqlCount, param);
		dgb.setTotal(counts.intValue());
		dgb.setRows(listMap);
		return dgb;
	}
	
	//业务员明细报表导出
	public List<Map<String, String>> queryObjectsBySqlList_billxi(ToolDealBean toolDealBean,UserBean userBean){
		Map<String, Object> map = new HashMap<String, Object>();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
//		String untino=userBean.getUnNo();
		String sqlming="select bb.MID,bb.UNNO,bb.TID,b.RNAME ,sum(bb.REFUNDCOUNT) REFUNDCOUNT," +
				"to_char(sum(bb.REFUNDAMT),'FM999,999,999,999,990.00') REFUNDAMT,sum(bb.TXNCOUNT) TXNCOUNT," +
				"to_char(sum(bb.MNAMT),'FM999,999,999,999,990.00') MNAMT ,sum(bb.TXNAMOUNT) TXNAMOUNT, " +
				"c.INSTALLEDNAME  from  check_UnitDealData bb  inner join bill_merchantinfo b on  bb.mid = b.mid " +
				"inner join BILL_MERCHANTTERMINALINFO c on c.TID = bb.TID";
		String sqlD="select * from bill_agentsalesinfo where user_id="+userBean.getUserID();
		List<AgentSalesModel> agentSaleslist= agentSalesDao.executeSqlT(sqlD, AgentSalesModel.class,map);
		if(agentSaleslist.size()>0){
			sqlming+="  and b.maintainUserID='"+agentSaleslist.get(0).getBusid()+"' ";
		}else{
			//总公司
			if("110000".equals(userBean.getUnNo())){
			}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else{
				//分公司  代理商
				String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
				sqlming+=" inner join bill_agentsalesinfo a on b.maintainUserID = a.busid  and a.unno=bb.unno and  a.unno in("+childUnno+") ";
			}
	      }
		if (null != toolDealBean.getTxnDayStart()
				&& null != toolDealBean.getTxnDayEnd()
				&& !toolDealBean.getTxnDayStart().equals("")
				&& !toolDealBean.getTxnDayEnd().equals("")) {
			sqlming += " and bb.TXNDAY between to_char(to_date('"+ toolDealBean.getTxnDayStart()+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date('"+ toolDealBean.getTxnDayEnd()+"','yyyy-MM-dd'),'yyyyMMdd')";
		}
		if (toolDealBean.getMid() != null && !"".equals(toolDealBean.getMid())) {
			sqlming += " AND bb.MID = '"+toolDealBean.getMid()+"'" ;
		}
		sqlming += " order by TXNAMOUNT desc";
//		if (toolDealBean.getSort() != null) {
//			sql += " order by " + toolDealBean.getSort() + " " + toolDealBean.getOrder();
//		}
		List<Map<String, String>> listMap = checkUnitDealDataDao.queryObjectsBySqlList(sqlming);
		return listMap;
	}
	
	/**
	 * 业务员明细报表查询详细信息
	 */
	@Override
	public List<Map<String, String>> queryFindToolDealDatas(ToolDealBean toolDealBean)
			throws Exception {
		String sql = "select MID,TID,CARDPAN,to_char(TXNAMOUNT,'FM999,999,999,999,990.00') TXNAMOUNT,TXNDAY,TXNDATE,CBRAND," +
				"STAN,replace(nvl(AUTHCODE,' '),'null',' ') as AUTHCODE,to_char(MDA,'FM999,999,999,999,990.00') MDA, to_char(MNAMT,'FM999,999,999,999,990.00') MNAMT" +
				" from CHECK_UNITDEALDETAIL  where TID = '"+toolDealBean.getTid()+"' and TXNDAY between " +
						"to_char(to_date( '"+toolDealBean.getTxnDate()+"','yyyy-MM-dd'),'yyyyMMdd') and  " +
								"to_char(to_date( '"+toolDealBean.getTxnDate1()+"','yyyy-MM-dd'),'yyyyMMdd') ";
		
		if (toolDealBean.getFirsix().trim() != null && !"".equals(toolDealBean.getFirsix().trim())) {
			sql += " AND substr(CARDPAN,1,6)='"+toolDealBean.getFirsix()+"'" ;
		}
		if (toolDealBean.getLastfour().trim() != null && !"".equals(toolDealBean.getLastfour().trim())) {
			sql += " AND substr(CARDPAN,(length(CARDPAN)-3),length(CARDPAN))='"+toolDealBean.getLastfour()+"'" ;
		}	
		sql +=" order by pkid asc";
		List<Map<String, String>> list=checkUnitDealDataDao.executeSql(sql);
		return list;
	}
	//group by 中的明细
	public List<Map<String, String>> queryFindToolDealDatasE(ToolDealBean toolDealBean)throws Exception {
	String sql = "select MID,TID,CARDPAN,to_char(TXNAMOUNT,'FM999,999,999,999,990.00') TXNAMOUNT,TXNDAY,TXNDATE,STAN,replace(nvl(AUTHCODE,' '),'null',' ') as AUTHCODE," +
			"to_char(MDA,'FM999,999,999,999,990.00') MDA, to_char(MNAMT,'FM999,999,999,999,990.00') MNAMT ,decode(CBRAND,1,'借记卡',2,'贷记卡','未知') CBRAND  " +
			"from CHECK_UNITDEALDETAIL  where 1=1";
		if (toolDealBean.getMid() != null && !"".equals(toolDealBean.getMid())) {
			sql += " AND MID = '"+toolDealBean.getMid()+"' " ;
		}
		if(toolDealBean.getTxnDayStart()!=null&&!"".equals(toolDealBean.getTxnDayStart())) {
			sql += " AND  TXNDAY between to_char(to_date('"+ toolDealBean.getTxnDayStart()+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date('"+ toolDealBean.getTxnDayEnd()+"','yyyy-MM-dd'),'yyyyMMdd')";
		}
		if (toolDealBean.getFirsix() != null && !"".equals(toolDealBean.getFirsix())) {
			sql += " AND substr(CARDPAN,1,6)='"+toolDealBean.getFirsix()+"'" ;
			
		}
		if (toolDealBean.getLastfour() != null && !"".equals(toolDealBean.getLastfour())) {
			sql += " AND substr(CARDPAN,(length(CARDPAN)-3),length(CARDPAN))='"+toolDealBean.getLastfour()+"'" ;
			
		}
		sql +=" order by pkid asc";
		List<Map<String, String>> list=checkUnitDealDataDao.executeSql(sql);
		return list;
		}
	
	//  分组  tid
	 
	  String sum="";
	public List<Map<String, String>> queryObjectsBySqlList_billxi1(ToolDealBean toolDealBean,UserBean userBean){
		 String sqlming = "";
		 String sqlSum="";
		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
//		 String untino=userBean.getUnNo();
		 Map<String, Object> map = new HashMap<String, Object>();
			 sqlming="select bb.TID,bb.MID,c.INSTALLEDNAME, count(*) COUNTS, to_char(sum(bb.MDA),'FM999,999,999,999,990.00')" +
			 		" MDA,to_char(sum(bb.MNAMT),'FM999,999,999,999,990.00') MNAMT ," +
			 		"to_char(sum(bb.TXNAMOUNT),'FM999,999,999,999,990.00') TXNAMOUNT  from  CHECK_UNITDEALDETAIL bb  " +
			 		"inner join bill_merchantinfo b on  bb.mid = b.mid inner join BILL_MERCHANTTERMINALINFO c on " +
			 		"c.TID = bb.TID  and c.MaintainType !='D' and c.APPROVESTATUS='Y' ";
			 sqlSum="select count(*) COUNTS, sum(bb.MNAMT) MNAMT,sum(bb.MDA) MDA ,sum(bb.TXNAMOUNT) TXNAMOUNT from " +
			 		" CHECK_UNITDEALDETAIL bb  inner join bill_merchantinfo b on  bb.mid = b.mid inner join " +
			 		"BILL_MERCHANTTERMINALINFO c on c.TID = bb.TID  and c.MaintainType !='D' and c.APPROVESTATUS='Y' ";
			String sqlD="select * from bill_agentsalesinfo where user_id="+userBean.getUserID();
			List<AgentSalesModel> agentSaleslist= agentSalesDao.executeSqlT(sqlD, AgentSalesModel.class,map);
			//业务员
			if(agentSaleslist.size()>0){
				sqlming+="  and b.maintainUserID='"+agentSaleslist.get(0).getBusid()+"' ";
				sqlSum+="  and b.maintainUserID='"+agentSaleslist.get(0).getBusid()+"' ";
			}else{
				//总公司
				if("110000".equals(userBean.getUnNo())){
				}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
					UnitModel parent = unitModel.getParent();
					if("110000".equals(parent.getUnNo())){
					}
				}else{
					String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
					sqlming+=" inner join bill_agentsalesinfo a on b.maintainUserID = a.busid  and  a.unno in("+childUnno+") ";
					sqlSum+=" inner join bill_agentsalesinfo a on b.maintainUserID = a.busid and  a.unno IN("+childUnno+") ";	
				}
			}
		if (toolDealBean.getTxnDayStart() != null && !"".equals(toolDealBean.getTxnDayStart())) {
			String start=toolDealBean.getTxnDayStart().replaceAll("-", "");
			sqlming += " AND bb.TXNDAY >= "+start ;
			sqlSum+=" AND bb.TXNDAY >= "+start ;
		}
		if (toolDealBean.getTxnDayEnd() != null && !"".equals(toolDealBean.getTxnDayEnd())) {
			String end=toolDealBean.getTxnDayEnd().replaceAll("-", "");
			sqlming += " AND bb.TXNDAY <= "+end ;
			sqlSum+= " AND bb.TXNDAY <= "+end ;
		}
		if (toolDealBean.getMid() != null && !"".equals(toolDealBean.getMid())) {
			sqlming += " AND bb.MID = '"+toolDealBean.getMid()+"'" ;
			sqlSum+= " AND bb.MID = '"+toolDealBean.getMid()+"'" ;
		}
		
		if (toolDealBean.getFirsix() != null && !"".equals(toolDealBean.getFirsix())) {
			sqlming += " AND substr(bb.CARDPAN,1,6)='"+toolDealBean.getFirsix()+"'" ;
			sqlSum+= " AND substr(bb.CARDPAN,1,6)='"+toolDealBean.getFirsix()+"'" ;
		}
		if (toolDealBean.getLastfour() != null && !"".equals(toolDealBean.getLastfour())) {
			sqlming += " AND substr(bb.CARDPAN,(length(bb.CARDPAN)-3),length(bb.CARDPAN))='"+toolDealBean.getLastfour()+"'" ;
			sqlSum+= " AND substr(bb.CARDPAN,(length(bb.CARDPAN)-3),length(bb.CARDPAN))='"+toolDealBean.getLastfour()+"'" ;
		}	
		
		
		
//		if (toolDealBean.getSort() != null) {
//			sql1 += " order by " + toolDealBean.getSort() + " " + toolDealBean.getOrder();
//		}
		sqlming+=" group by  bb.TID,b.rname,bb.MID,c.INSTALLEDNAME order by TXNAMOUNT desc ";
		sqlSum+= " group by  bb.TID order by TXNAMOUNT desc ";
		sum=sqlSum;
		List<Map<String, String>> listMap1 = checkUnitDealDataDao.queryObjectsBySqlList(sqlming);
		return listMap1;
	}
      //总计
	public List<Map<String, String>> queryObjectsBySqlList_billxiSum(ToolDealBean toolDealBean,UserBean userBean){
		 String sumsql="";
		 sumsql+="select sum(a.COUNTS) COUNTSU, to_char(sum(a.MDA),'FM999,999,999,999,990.00') MDA , ";
		 sumsql+=" to_char(sum(a.MNAMT),'FM999,999,999,999,990.00') MNAMT ,";
		 sumsql+="to_char(sum(a.TXNAMOUNT),'FM999,999,999,999,990.00') TXNAMOUNT from ("+sum+") a";
		List<Map<String, String>> listMapsum = checkUnitDealDataDao.queryObjectsBySqlList(sumsql);
		return listMapsum;
	}
	//商户名称查询  
	public List<Map<String, String>> selectrname(ToolDealBean toolDealBean,UserBean userBean){
		String sqlrname="select rname from bill_merchantinfo where mid='"+toolDealBean.getMid()+"'";
		List<Map<String, String>> selectrname = checkUnitDealDataDao.queryObjectsBySqlList(sqlrname);
		return selectrname;
	}
	@Override
	public DataGridBean findBeiJingAreaDealData(ToolDealBean toolDealBean,
			UserBean userSession) {
		DataGridBean dgb = new DataGridBean();
		 Map<String, Object> map = new HashMap<String, Object>();
		String sql="select e.maintaindate, t.rname,f.mid, f.tid ,e.installedaddress ,f.txnamount " +
						"  from  bill_merchantinfo t,  bill_merchantterminalinfo e," +
						" (select c.MID, c.TID, nvl(sum(c.txnamount), 0) txnamount " +
						" from  check_unitdealdata c " +
						" where c.unno = '111000'";
		if(toolDealBean.getMid() !=null && !"".equals(toolDealBean.getMid())){
			sql+=" and c.mid=:MID";
			map.put("MID", toolDealBean.getMid());
		}
		if(toolDealBean.getUnNo() != null && !"".equals(toolDealBean.getUnNo())){
			sql+=" and c.unno=:UNNO ";
			map.put("UNNO", toolDealBean.getUnNo());
		}
		if(toolDealBean.getTid() !=null && !"".equals(toolDealBean.getTid())){
			sql+=" and c.tid=:TID";
			map.put("TID", toolDealBean.getTid());
		}
		if((toolDealBean.getTxnDay()!=null && !"".equals(toolDealBean.getTxnDay())) && (toolDealBean.getTxnDay1()!=null && !"".equals(toolDealBean.getTxnDay1())) ){
			String aa = toolDealBean.getTxnDay().replaceAll("-", "");
			String bb = toolDealBean.getTxnDay1().replaceAll("-", "");
			sql+=" and c.txnday between '"+aa+"' and '"+bb+"'";
		}
		
		sql+="  group by MID,TID)f " +
				"  where t.MID = e.MID and e.MID = f.MID and e.TID = f.TID ";
		if(toolDealBean.getRname() !=null && !"".equals(toolDealBean.getRname())){
			sql+=" and t.rname like '%"+toolDealBean.getRname()+"%'";
		}
		String sqlCount= "select count(*) from ("+sql+")";
		Integer count =checkUnitDealDataDao.querysqlCounts2(sqlCount, map);
		List<Map<String,String>> list=checkUnitDealDataDao.queryObjectsBySqlList(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}
	@Override
	public List<Map<String, String>> findBeiJingAreaDealData(ToolDealBean toolDealBean) {
		 Map<String, Object> map = new HashMap<String, Object>();
			String sql="select e.maintaindate, t.rname,f.mid, f.tid ,nvl(e.installedaddress,' ') installedaddress ,f.txnamount " +
							"  from  bill_merchantinfo t,  bill_merchantterminalinfo e," +
							" (select c.MID, c.TID, nvl(sum(c.txnamount), 0) txnamount " +
							" from  check_unitdealdata c " +
							" where c.unno = '111000'";
			if(toolDealBean.getMid() !=null && !"".equals(toolDealBean.getMid())){
				sql+=" and c.mid=:MID";
				map.put("MID", toolDealBean.getMid());
			}
			if(toolDealBean.getUnNo() != null && !"".equals(toolDealBean.getUnNo())){
				sql+=" and c.unno=:UNNO ";
				map.put("UNNO", toolDealBean.getUnNo());
			}
			if(toolDealBean.getTid() !=null && !"".equals(toolDealBean.getTid())){
				sql+=" and c.tid=:TID";
				map.put("TID", toolDealBean.getTid());
			}
			String aa = toolDealBean.getTxnDay().replaceAll("-", "");
			String bb = toolDealBean.getTxnDay1().replaceAll("-", "");
			sql+=" and c.txnday between '"+aa+"' and '"+bb+"'";
			sql+="  group by MID,TID)f " +
					"  where t.MID = e.MID and e.MID = f.MID and e.TID = f.TID ";
			if(toolDealBean.getRname() !=null && !"".equals(toolDealBean.getRname())){
				sql+=" and t.rname like '%"+toolDealBean.getRname()+"%'";
			}
			String sqlCount= "select count(*) from ("+sql+")";
			Integer count =checkUnitDealDataDao.querysqlCounts2(sqlCount, map);
			List<Map<String,String>> list=checkUnitDealDataDao.queryObjectsBySqlList(sql, map, 0, count);
			return list;
	}
	/**
	 * 值班前日交易查询
	 */
	@Override
	public DataGridBean queryYesDealDatas(ToolDealBean toolDealBean,UserBean userBean){	
		
		String sql ="select sum(TXNcOUNT) totalTxnCount,to_char(SUM(TXNAMOUNT),'FM999,999,999,999,990.00') totalTxnAmount,COUNT(*) totalCount, '集团商户' as UNNOLVL from check_unitdealdata a ,sys_unit b   where a.Unno = b.unno ";
		String sql2 ="select sum(TXNcOUNT) totalTxnCount,to_char(SUM(TXNAMOUNT),'FM999,999,999,999,990.00') totalTxnAmount,COUNT(*) totalCount,'代理商户' as UNNOLVL from check_unitdealdata a ,sys_unit b   where a.Unno = b.unno ";
		String sql3 ="select sum(TXNcOUNT) totalTxnCount,to_char(SUM(TXNAMOUNT),'FM999,999,999,999,990.00') totalTxnAmount,COUNT(*) totalCount,'汇总' as UNNOLVL from check_unitdealdata a ,sys_unit b   where a.Unno = b.unno ";
		if(toolDealBean.getTxnDayStart() != null && !"".equals(toolDealBean.getTxnDayStart()) && toolDealBean.getTxnDayEnd() !=null && !"".equals(toolDealBean.getTxnDayEnd())){
			String start = toolDealBean.getTxnDayStart().replace("-", "");
			String end = toolDealBean.getTxnDayEnd().replace("-", "");
			
			sql+=" and Txnday >= '"+start+"' and Txnday <= '"+end+"'";
			sql2+=" and Txnday >= '"+start+"' and Txnday <= '"+end+"'";
			sql3+=" and Txnday >= '"+start+"' and Txnday <= '"+end+"'";
		}else{
			
			sql+=" and Txnday = to_char(sysdate-1,'yyyyMMdd')";
			sql2+=" and Txnday = to_char(sysdate-1,'yyyyMMdd')";
			sql3+=" and Txnday = to_char(sysdate-1,'yyyyMMdd')";
		}
		sql+=" and b.un_lvl=1";
		sql2+=" and b.un_lvl!=1";
		//String sqlCount= "select count(*) from ("+sql+")";
		List list = checkUnitDealDataDao.queryObjectsBySqlList(sql);
		List list2 = checkUnitDealDataDao.queryObjectsBySqlList(sql2);
		List list3 = checkUnitDealDataDao.queryObjectsBySqlList(sql3);
		list.add(0,list3.get(0));
		list.add(list2.get(0));
		//Integer count = checkUnitDealDataDao.querysqlCounts2(sqlCount, null);	
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(2);
		return dgb;
	}
	
	
	
	@Override
	public DataGridBean findMposeUsage(ToolDealBean toolDealBean) {
		String sql=" select r.*,q.un_name from (select t1.unno,t1.count1 ,nvl(t1.count3 ,0) count3 ,nvl(round(count3/count1*100,2),0) activRate ,nvl(t2.count2,0) count2, nvl(round(count2/count1*100,2),0) usage from  (select a.unno,a.count1,b.count3 from  (select unno,count(1) as count1 from  bill_terminalinfo where isM35=1 group by unno) a" +
					" full outer join " +
					" (select unno,count(1) as count3 from  bill_terminalinfo  where isM35=1 and usedconfirmdate is not null   group by unno ) b " +
					" on a.unno=b.unno ) t1 full outer join " +
					" (select m.unno,count(1) count2 from  check_unitdealdata m, bill_terminalinfo m1  where m.tid=m1.termid and m1.ism35=1 and m.txnday=:TXNDAY group by m.unno) t2" +
					" on t1.unno=t2.unno) r,sys_unit q  where r.unno=q.unno ";
		String sqlSum=" select count1,count3,nvl(round(count3 / count1 * 100, 2), 0) activRate,count2,nvl(round(count2 / count1 * 100, 2), 0) usage from (select sum(t1.count1) count1,sum(nvl(t1.count3 ,0)) count3, sum(nvl(t2.count2,0)) count2   from  (select a.unno,a.count1,b.count3 from  (select unno,count(1) as count1 from  bill_terminalinfo where isM35=1 group by unno) a" +
					" full outer join " +
					" (select unno,count(1) as count3 from  bill_terminalinfo  where isM35=1 and usedconfirmdate is not null   group by unno ) b " +
					" on a.unno=b.unno ) t1 full outer join " +
					" (select m.unno,count(1) count2 from  check_unitdealdata m, bill_terminalinfo m1  where m.tid=m1.termid and m1.ism35=1 and m.txnday=:TXNDAY group by m.unno) t2" +
					" on t1.unno=t2.unno ";
		Map<String,Object> map = new HashMap<String, Object>();
		if(toolDealBean.getTxnDay()!=null && !"".equals(toolDealBean.getTxnDay())){
			map.put("TXNDAY", toolDealBean.getTxnDay().replaceAll("-", ""));
		}else{
			Calendar c = Calendar.getInstance(); 
			c.setTime(new Date());
			c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			map.put("TXNDAY", sf.format(c.getTime()));
		}
		if(toolDealBean.getUnno()!=null && !"".equals(toolDealBean.getUnno())){
			sql+=" and r.unno=:UNNO ";
			sqlSum+=" where t1.unno=:UNNO ) ";
			map.put("UNNO", toolDealBean.getUnno());
		}else{
			sqlSum+=" ) ";
		}
		String sqlCount="select count(*) from ("+sql+")";
		Integer count=checkUnitDealDataDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list =checkUnitDealDataDao.queryObjectsBySqlList2(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		List<Map<String,Object>> list2 =checkUnitDealDataDao.queryObjectsBySqlList2(sqlSum, map,0, 1);
		list.add(0,list2.get(0));
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}
	@Override
	public DataGridBean queryMerchantRebateData(ToolDealBean toolDealBean, UserBean userBean) {
		DataGridBean dgd = new DataGridBean();
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = " select * from (select bb.txnamount,bb.yidai,bb.unno,bb.sn1,bb.hrt_mid,bb.keyconfirmdate,bb.usedconfirmdate,bb.machinemodel,bb.rname,bb.bankaccno,bb.bankaccname,bb.bankbranch,bb.paybankid,"+
			"sum(bb.rebateamt) as rebateamt from (select * from (select (select s.unno from sys_unit s  where s.upper_unit in ('110000', '991000','111000','341000','121000','b10000', 'j91000','b11000','d41000','b21000') " +
			" start with s.unno = t1.UNNO  connect by NOCYCLE s.unno = prior s.upper_unit  and rownum = 1) as yidai," +
			" t1.unno, t1.sn as sn1,d1.mid as hrt_mid, to_char(t1.keyconfirmdate,'yyyy-MM-dd') as keyconfirmdate, sum(d1.txnamount) as txnamount,mc.machinemodel," +
			" to_char(t1.usedconfirmdate,'yyyy-MM-dd') as usedconfirmdate,sum(d1.txncount) as txncount, t1.rebateType,m1.rname,m1.bankaccno,m1.bankaccname,m1.bankbranch,m1.paybankid "+
            "  from (select t.termid,t.unno,t.sn,t.keyconfirmdate,t.usedconfirmdate,t.rebateType from bill_terminalinfo t where t.ism35 = 1 ";
      //激活日期 返机器
		if (!"".equals(toolDealBean.getCbrand())&&null != toolDealBean.getTxnDay()&& null != toolDealBean.getTxnDay1()&& !toolDealBean.getTxnDay().equals("")&& !toolDealBean.getTxnDay1().equals("")) {
			//导入日期 返款 +分期+押金
			sql += " and t.keyconfirmdate between to_date('"+toolDealBean.getTxnDay()+" 00:00:00','yyyy-MM-dd hh24:mi:ss') and to_date('"+toolDealBean.getTxnDay1()+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
		}else{
			return dgd;
		}     
		if(userBean.getUseLvl()==3){
			return dgd;		
		}
		else{
			UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
			if("110000".equals(userBean.getUnNo())){
			}
			else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else{
				sql+=" and t.unno in ("+merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo())+") ";
			}
		}
		if(null!=toolDealBean.getUnno()&&!toolDealBean.getUnno().equals("")){
			sql += " and t.unno in ("+merchantInfoService.queryUnitUnnoUtil(toolDealBean.getUnno())+") ";
		}
		sql+=" and t.rebateType='4' )t1,(select d.tid, d.mid, d.txnamount, d.txncount from check_unitdealdata d where d.txnday between ";
      //交易日期
		if (null != toolDealBean.getTxnDate()&& null != toolDealBean.getTxnDate1()&& !toolDealBean.getTxnDate().equals("")&& !toolDealBean.getTxnDate1().equals("")) {
			sql += "  to_char(to_date('"+toolDealBean.getTxnDate()+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date('"+toolDealBean.getTxnDate1()+"','yyyy-MM-dd'),'yyyyMMdd') ";
		}else{
			return dgd;
		}
		if(null!=toolDealBean.getMid()&&!toolDealBean.getMid().equals("")){
			sql+=" and d.MID = '"+toolDealBean.getMid()+"'";
		}
        sql+=") d1,bill_merchantterminalinfo f, bill_machineinfo mc,bill_merchantinfo m1 where f.mid=m1.mid and t1.termid = d1.tid and f.bmaid=mc.bmaid and f.tid=t1.termid and f.mid =d1.mid and f.tid=d1.tid " ;
        sql+=" group by t1.unno, t1.sn, d1.mid, t1.keyconfirmdate,t1.usedconfirmdate, t1.rebateType,machinemodel, m1.rname,m1.bankaccno,m1.bankaccname,m1.bankbranch,m1.paybankid) a "+
            //返现机具出售日期超限 0正常 1 超限
//          " decode(t.rebateType,2,,0) "+
            " left join check_isM35Rebate m  on a.sn1 = m.sn and m.mid=a.hrt_mid)bb where 1=1 ";
        if(null!=toolDealBean.getTxnType()&&toolDealBean.getTxnType().equals("1")){
			sql+=" and bb.REBATEDATE is not null ";
		}else if (null!=toolDealBean.getTxnType()&&toolDealBean.getTxnType().equals("2")){
			sql+=" and bb.REBATEDATE is null ";
		}
        sql+=" group by bb.txnamount,bb.yidai,bb.unno,bb.sn1,bb.hrt_mid,bb.keyconfirmdate,bb.usedconfirmdate,bb.machinemodel,bb.rname,bb.bankaccno,bb.bankaccname,bb.bankbranch,bb.paybankid order by bb.keyconfirmdate ,bb.usedconfirmdate desc ";
        if("".equals(toolDealBean.getTxnType())){
        	sql+=")b1 ";
        }else if(toolDealBean.getTxnType().equals("1")){
        	sql+=")b1 where b1.rebateamt >= 120 ";
        }else if (toolDealBean.getTxnType().equals("2")){
        	sql+=")b1 where b1.rebateamt is null ";
        }else {
        	sql+=")b1 where b1.rebateamt between 0 and 119  ";
        }
		String sqlsumcount = "select count(*) from ("+sql+")";
		List<Map<String, String>> listMap = checkUnitDealDataDao.queryObjectsBySqlList(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		BigDecimal sum=checkUnitDealDataDao.querysqlCounts(sqlsumcount, null);
		dgd.setTotal(sum.intValue());
		dgd.setRows(listMap);
		return dgd;		
	}
	@Override
	public JsonBean queryCheckUnitDate(ToolDealBean toolDealBean) {
		JsonBean json = new JsonBean();
		String sql = "select count(1) from sys_param where upload_path<='"+toolDealBean.getTxnDay()+"' and title='dataTransferTime'";
		Integer i = checkUnitDealDataDao.querysqlCounts2(sql, null);
		if(i==1){
			json.setSuccess(true);
		}else{
			String sql2 = "select upload_path from sys_param where title='dataTransferTime'";
			List<Map<String,String>> list = checkUnitDealDataDao.queryObjectsBySqlListMap(sql2, null);
			String date = list.get(0).get("UPLOAD_PATH");
			json.setSuccess(false);
			json.setMsg(date);
		}
		return json;
	}
	@Override
	public DataGridBean queryIsM35Rebate(ToolDealBean toolDealBean, UserBean userBean) {
		DataGridBean dgd = new DataGridBean();
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select * from check_ism35rebate where 1=1";
        //导入时间
		if (null != toolDealBean.getTxnDate()&& !toolDealBean.getTxnDate().equals("")) {
			sql += " and cdate>=to_date('"+toolDealBean.getTxnDate()+" 00:00:00','yyyy-MM-dd hh24:mi:ss')";
		}
		if (null != toolDealBean.getTxnDate1()&& !toolDealBean.getTxnDate1().equals("")) {
			sql += " and cdate<=to_date('"+toolDealBean.getTxnDate1()+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
		}
		String sqlCount = "select count(*) from ("+sql+")";
		List<Map<String, String>> listMap = checkUnitDealDataDao.queryObjectsBySqlList(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		BigDecimal sum=checkUnitDealDataDao.querysqlCounts(sqlCount, null);
		dgd.setTotal(sum.intValue());
		dgd.setRows(listMap);
		return dgd;	
	}
	@Override
	public List<Map<String, Object>> exportIsM35Rebate(ToolDealBean toolDealBean, UserBean userBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select * from check_ism35rebate where 1=1";
        //导入时间
		if (null != toolDealBean.getTxnDate()&& !toolDealBean.getTxnDate().equals("")) {
			sql += " and cdate>=to_date('"+toolDealBean.getTxnDate()+" 00:00:00','yyyy-MM-dd hh24:mi:ss')";
		}
		if (null != toolDealBean.getTxnDate1()&& !toolDealBean.getTxnDate1().equals("")) {
			sql += " and cdate<=to_date('"+toolDealBean.getTxnDate1()+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
		}
		String sqlCount = "select count(*) from ("+sql+")";
		List<Map<String, Object>> list = checkUnitDealDataDao.queryObjectsBySqlListMap2(sql, map);
		return list;
	}
	@Override
	public DataGridBean listUnitDealData(ToolDealBean toolDealBean, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		//必须传日期，且日期必须是同年同月
		if(toolDealBean.getTxnDay()==null||"".equals(toolDealBean.getTxnDay())||toolDealBean.getTxnDay1()==null||"".equals(toolDealBean.getTxnDay1())||!toolDealBean.getTxnDay().substring(0, 6).equals(toolDealBean.getTxnDay1().substring(0, 6))) {
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE,-1);
			Date time=cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			toolDealBean.setTxnDay(sdf.format(time));
			toolDealBean.setTxnDay1(sdf.format(time));
		}
		//2018-10-10 转为 20181010
		toolDealBean.setTxnDay(toolDealBean.getTxnDay().replace("-", ""));
		toolDealBean.setTxnDay1(toolDealBean.getTxnDay1().replace("-", ""));
		Map<String, Object> map = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//判断是不是有交易类型，没有的话所有的汇总
		String typeString = "";
		if(toolDealBean.getType()!=null) {
			typeString = ",a.Type";
		}
		//按照月份去查表check_unitdealdetail_xx
		String sql = "select a.unno,a.mid,a.rname,a.ISMPOS"+typeString+",sum(a.TXNCOUNT) TXNCOUNT,sum(a.TXNAMOUNT) TXNAMOUNT,sum(a.MDA) MDA,"
				+ "sum(a.REFUNDCOUNT) REFUNDCOUNT,sum(a.REFUNDAMT) REFUNDAMT,sum(a.REFUNDMDA) REFUNDMDA,sum(a.MNAMT) MNAMT "
				+ "from check_unitdealdata_"+toolDealBean.getTxnDay().substring(4, 6)+" a where a.txnday >=:txnday and txnday <=:txnday1 ";
		//销售
		String sql1 = "";
		map.put("txnday", toolDealBean.getTxnDay());
		map.put("txnday1", toolDealBean.getTxnDay1());
		if("110000".equals(user.getUnNo())) {//总公司权限
		}else if(user.getUseLvl()==2||user.getUseLvl()==1){//业务人员
			//业务员名称可能重复，加UNNO限制
			sql = "SELECT a.unno, a.mid, a.rname, a.ISMPOS"+typeString+", TXNCOUNT , TXNAMOUNT, MDA, REFUNDCOUNT, REFUNDAMT, REFUNDMDA , MNAMT FROM check_unitdealdata_"+toolDealBean.getTxnDay().substring(4, 6)+" a WHERE a.txnday >=:txnday AND txnday <= :txnday1 ";
			sql1 = sql + " and a.unno in (select unno from sys_unit start with unno in (select unno from bill_agentunitinfo a "
					+ "where a.signuserid = (select busid from bill_agentsalesinfo where user_id = "+user.getUserID()+" and MAINTAINTYPE != 'D') and unno != '"+user.getUnNo()+"') connect by prior unno = upper_unit) ";
			sql += " and a.mid in (select mid from bill_merchantinfo where busid = (select busid from bill_agentsalesinfo where user_id="+user.getUserID()+" and maintaintype!='D') and unno = '"+user.getUnNo()+"') ";
		}else if(user.getUseLvl()==3) {//商户
			// @author:xuegangliu-20190304 按旧版的查询显示,查询子商户数据
			sql+=" and a.mid in (select MID from  bill_MerchantInfo where  PARENTMID= :mid1 or MID =:mid1) ";
//            sql += " and a.mid=:mid1";
			map.put("mid1", user.getLoginName());
		}else if(user.getUseLvl()==0) {//代理商管理员
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno1 connect by prior unno = upper_unit) ";
			map.put("unno1", user.getUnNo());
		}
		if(toolDealBean.getMid()!=null&&!"".equals(toolDealBean.getMid())) {
			sql += " and a.mid=:mid ";
			if(!"".equals(sql1)) {
				sql1 += " and a.mid=:mid ";
			}
			map.put("mid", toolDealBean.getMid());
		}
		if(toolDealBean.getRname()!=null&&!"".equals(toolDealBean.getRname())) {
			sql += " and a.rname=:rname ";
			if(!"".equals(sql1)) {
				sql1 += " and a.rname=:rname ";
			}
			map.put("rname", toolDealBean.getRname());
		}
		if(toolDealBean.getUnno()!=null&&!"".equals(toolDealBean.getUnno())) {
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			if(!"".equals(sql1)) {
				sql1 += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			}
			map.put("unno", toolDealBean.getUnno());
		}
		if(toolDealBean.getIsMpos()!=null) {
			sql += " and a.ismpos=:ismpos ";
			if(!"".equals(sql1)) {
				sql1 += " and a.ismpos=:ismpos ";
			}
			map.put("ismpos", toolDealBean.getIsMpos());
		}
		if(toolDealBean.getType()!=null) {
			sql += " and a.type=:type ";
			if(!"".equals(sql1)) {
				sql1 += " and a.type=:type ";
			}
			map.put("type", toolDealBean.getType());
		}
		if(!"".equals(sql1)) {
			sql = "select a.unno, a.mid, a.rname, a.ISMPOS"+typeString+", sum(a.TXNCOUNT) TXNCOUNT, sum(a.TXNAMOUNT) TXNAMOUNT, sum(a.MDA) MDA, sum(a.REFUNDCOUNT) REFUNDCOUNT, sum(a.REFUNDAMT) REFUNDAMT, sum(a.REFUNDMDA) REFUNDMDA, sum(a.MNAMT) MNAMT from ("+sql+" union "+sql1+") a ";
		}
		sql +=" group by a.unno,a.mid,a.rname,a.ISMPOS"+typeString+" ";
		String sqlCount = "select count(1) from ("+sql+")";
		if(toolDealBean.getSort()!=null&&!"".equals(toolDealBean.getSort())&&toolDealBean.getOrder()!=null&&!"".equals(toolDealBean.getOrder())) {
			sql += " order by a."+toolDealBean.getSort()+" "+toolDealBean.getOrder()+" ";
		}
		List<Map<String,Object>> list = checkUnitDealDataDao.queryObjectsBySqlList2(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		BigDecimal counts = checkUnitDealDataDao.querysqlCounts(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}
	
	@Override
	public List<Map<String,Object>> listUnitDealDataTotal(ToolDealBean toolDealBean, UserBean user) {
		List<Map<String,Object>> list = null;
		//必须传日期，且日期必须是同年同月
		if(toolDealBean.getTxnDay()==null||"".equals(toolDealBean.getTxnDay())||toolDealBean.getTxnDay1()==null||"".equals(toolDealBean.getTxnDay1())||!toolDealBean.getTxnDay().substring(0, 6).equals(toolDealBean.getTxnDay1().substring(0, 6))) {
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE,-1);
			Date time=cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			toolDealBean.setTxnDay(sdf.format(time));
			toolDealBean.setTxnDay1(sdf.format(time));
		}
		//2018-10-10 转为 20181010
		String txnDay = toolDealBean.getTxnDay();
		String txnDay1 = toolDealBean.getTxnDay1();
		toolDealBean.setTxnDay(toolDealBean.getTxnDay().replace("-", ""));
		toolDealBean.setTxnDay1(toolDealBean.getTxnDay1().replace("-", ""));
		Map<String, Object> map = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//按照月份去查表check_unitdealdetail_xx
		String sql = "select nvl(to_char(sum(txncount),'FM999,999,999,999,990'),0) totalNum,nvl(to_char(sum(txnamount),'FM999,999,999,999,990.00'),0) totalAmount "
				+ "from check_unitdealdata_"+toolDealBean.getTxnDay().substring(4, 6)+" a where a.txnday >=:txnday and txnday <=:txnday1 ";
		//销售
		String sql1 = "";
		map.put("txnday", toolDealBean.getTxnDay());
		map.put("txnday1", toolDealBean.getTxnDay1());
		if("110000".equals(user.getUnNo())) {//总公司权限
		}else if(user.getUseLvl()==2||user.getUseLvl()==1){//业务人员
			sql = "SELECT nvl(SUM(txncount), 0) AS totalNum , nvl(SUM(txnamount), 0) AS totalAmount FROM check_unitdealdata_"+toolDealBean.getTxnDay().substring(4, 6)+" "
				+ "a WHERE a.txnday >= :txnday AND a.txnday <= :txnday1 AND a.mid IN ( SELECT mid FROM bill_merchantinfo "
				+ "WHERE busid = ( SELECT busid FROM bill_agentsalesinfo WHERE user_id = "+user.getUserID()+" AND maintaintype != 'D' ) AND unno = '"+user.getUnNo()+"' ) ";
			sql1 = "SELECT nvl(SUM(txncount), 0) AS totalNum , nvl(SUM(txnamount), 0) AS totalAmount FROM check_unitdealdata_"+toolDealBean.getTxnDay().substring(4, 6)+""
				+ " a WHERE a.txnday >= :txnday AND a.txnday <= :txnday1 AND a.unno IN ( SELECT unno FROM sys_unit START WITH unno "
				+ "IN ( SELECT unno FROM bill_agentunitinfo a WHERE a.signuserid = ( SELECT busid FROM bill_agentsalesinfo WHERE "
				+ "user_id = "+user.getUserID()+" AND MAINTAINTYPE != 'D' ) AND unno != '"+user.getUnNo()+"' ) CONNECT BY PRIOR unno = upper_unit ) ";
		}else if(user.getUseLvl()==3) {//商户
			sql += " and a.mid=:mid1";
			map.put("mid1", user.getLoginName());
		}else if(user.getUseLvl()==0) {//代理商管理员
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno1 connect by prior unno = upper_unit) ";
			map.put("unno1", user.getUnNo());
		}
		if(toolDealBean.getMid()!=null&&!"".equals(toolDealBean.getMid())) {
			sql += " and a.mid=:mid ";
			if(!"".equals(sql1)) {
				sql1 += " and a.mid=:mid ";
			}
			map.put("mid", toolDealBean.getMid());
		}
		if(toolDealBean.getRname()!=null&&!"".equals(toolDealBean.getRname())) {
			sql += " and a.rname=:rname ";
			if(!"".equals(sql1)) {
				sql1 += " and a.rname=:rname ";
			}
			map.put("rname", toolDealBean.getRname());
		}
		if(toolDealBean.getUnno()!=null&&!"".equals(toolDealBean.getUnno())) {
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			if(!"".equals(sql1)) {
				sql1 += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			}
			map.put("unno", toolDealBean.getUnno());
		}
		if(toolDealBean.getIsMpos()!=null) {
			sql += " and a.ismpos=:ismpos ";
			if(!"".equals(sql1)) {
				sql1 += " and a.ismpos=:ismpos ";
			}
			map.put("ismpos", toolDealBean.getIsMpos());
		}
		if(toolDealBean.getType()!=null) {
			sql += " and a.type=:type ";
			if(!"".equals(sql1)) {
				sql1 += " and a.type=:type ";
			}
			map.put("type", toolDealBean.getType());
		}
		if(!"".equals(sql1)) {
			sql = "select nvl(to_char(sum(totalNum),'FM999,999,999,999,990'),0) totalNum,nvl(to_char(sum(totalAmount),'FM999,999,999,999,990.00'),0) totalAmount  from ("+sql+" union "+sql1+")";
		}
		list = checkUnitDealDataDao.queryObjectsBySqlListMap2(sql, map);
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		String msg = "";
		if(list.size()>0) {
			msg = txnDay+"~"+txnDay1+" | 总交易金额：￥ "+list.get(0).get("TOTALAMOUNT")+" | "+"总交易笔数："+list.get(0).get("TOTALNUM");
		}
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("msg", msg);
		list.add(map1);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> exportUnitDealData(ToolDealBean toolDealBean, UserBean user) {
		List<Map<String, Object>> list = null;
		//必须传日期，且日期必须是同年同月
		if(toolDealBean.getTxnDay()==null||"".equals(toolDealBean.getTxnDay())||toolDealBean.getTxnDay1()==null||"".equals(toolDealBean.getTxnDay1())||!toolDealBean.getTxnDay().substring(0, 6).equals(toolDealBean.getTxnDay1().substring(0, 6))) {
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE,-1);
			Date time=cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			toolDealBean.setTxnDay(sdf.format(time));
			toolDealBean.setTxnDay1(sdf.format(time));
		}
		//2018-10-10 转为 20181010
		toolDealBean.setTxnDay(toolDealBean.getTxnDay().replace("-", ""));
		toolDealBean.setTxnDay1(toolDealBean.getTxnDay1().replace("-", ""));
		Map<String, Object> map = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//判断是不是有交易类型，没有的话所有的汇总
		String typeString = "";
		if(toolDealBean.getType()!=null) {
			typeString = ",a.Type";
		}
		//按照月份去查表check_unitdealdetail_xx
		String sql = "select a.unno,a.mid,a.rname,a.ISMPOS"+typeString+",sum(a.TXNCOUNT) TXNCOUNT,sum(a.TXNAMOUNT) TXNAMOUNT,sum(a.MDA) MDA,"
				+ "sum(a.REFUNDCOUNT) REFUNDCOUNT,sum(a.REFUNDAMT) REFUNDAMT,sum(a.REFUNDMDA) REFUNDMDA,sum(a.MNAMT) MNAMT "
				+ "from check_unitdealdata_"+toolDealBean.getTxnDay().substring(4, 6)+" a where a.txnday >=:txnday and txnday <=:txnday1 ";
		//销售
		String sql1 = "";
		map.put("txnday", toolDealBean.getTxnDay());
		map.put("txnday1", toolDealBean.getTxnDay1());
		if("110000".equals(user.getUnNo())) {//总公司权限
		}else if(user.getUseLvl()==2||user.getUseLvl()==1){//业务人员
			//业务员名称可能重复，加UNNO限制
			sql = "SELECT a.unno, a.mid, a.rname, a.ISMPOS"+typeString+", TXNCOUNT , TXNAMOUNT, MDA, REFUNDCOUNT, REFUNDAMT, REFUNDMDA , MNAMT FROM check_unitdealdata_"+toolDealBean.getTxnDay().substring(4, 6)+" a WHERE a.txnday >=:txnday AND txnday <= :txnday1 ";
			sql1 = sql + " and a.unno in (select unno from sys_unit start with unno in (select unno from bill_agentunitinfo a "
					+ "where a.signuserid = (select busid from bill_agentsalesinfo where user_id = "+user.getUserID()+" and MAINTAINTYPE != 'D') and unno != '"+user.getUnNo()+"') connect by prior unno = upper_unit) ";
			sql += " and a.mid in (select mid from bill_merchantinfo where busid = (select busid from bill_agentsalesinfo where user_id="+user.getUserID()+" and maintaintype!='D') and unno = '"+user.getUnNo()+"') ";
		}else if(user.getUseLvl()==3) {//商户
			// @author:xuegangliu-20190304 按旧版的查询显示,查询子商户数据
			sql+=" and a.mid in (select MID from  bill_MerchantInfo where  PARENTMID= :mid1 or MID =:mid1) ";
//			sql += " and a.mid=:mid1";
			map.put("mid1", user.getLoginName());
		}else if(user.getUseLvl()==0) {//代理商管理员
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno1 connect by prior unno = upper_unit) ";
			map.put("unno1", user.getUnNo());
		}
		if(toolDealBean.getMid()!=null&&!"".equals(toolDealBean.getMid())) {
			sql += " and a.mid=:mid ";
			if(!"".equals(sql1)) {
				sql1 += " and a.mid=:mid ";
			}
			map.put("mid", toolDealBean.getMid());
		}
		if(toolDealBean.getRname()!=null&&!"".equals(toolDealBean.getRname())) {
			sql += " and a.rname=:rname ";
			if(!"".equals(sql1)) {
				sql1 += " and a.rname=:rname ";
			}
			map.put("rname", toolDealBean.getRname());
		}
		if(toolDealBean.getUnno()!=null&&!"".equals(toolDealBean.getUnno())) {
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			if(!"".equals(sql1)) {
				sql1 += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			}
			map.put("unno", toolDealBean.getUnno());
		}
		if(toolDealBean.getIsMpos()!=null) {
			sql += " and a.ismpos=:ismpos ";
			if(!"".equals(sql1)) {
				sql1 += " and a.ismpos=:ismpos ";
			}
			map.put("ismpos", toolDealBean.getIsMpos());
		}
		if(toolDealBean.getType()!=null) {
			sql += " and a.type=:type ";
			if(!"".equals(sql1)) {
				sql1 += " and a.type=:type ";
			}
			map.put("type", toolDealBean.getType());
		}
		if(!"".equals(sql1)) {
			sql = "select a.unno, a.mid, a.rname, a.ISMPOS"+typeString+", sum(a.TXNCOUNT) TXNCOUNT, sum(a.TXNAMOUNT) TXNAMOUNT, sum(a.MDA) MDA, sum(a.REFUNDCOUNT) REFUNDCOUNT, sum(a.REFUNDAMT) REFUNDAMT, sum(a.REFUNDMDA) REFUNDMDA, sum(a.MNAMT) MNAMT from ("+sql+" union "+sql1+") a ";
		}
		sql +=" group by a.unno,a.mid,a.rname,a.ISMPOS"+typeString+" ";
		if(toolDealBean.getSort()!=null&&!"".equals(toolDealBean.getSort())&&toolDealBean.getOrder()!=null&&!"".equals(toolDealBean.getOrder())) {
			sql += " order by a."+toolDealBean.getSort()+" "+toolDealBean.getOrder()+" ";
		}
		list = checkUnitDealDataDao.queryObjectsBySqlListMap2(sql, map);
		return list;
	}

	@Override
	public DataGridBean listTotalSpTxnGrid(ToolDealBean toolDealBean, UserBean userBean) {
		DataGridBean dataGridBean=new DataGridBean();
		if(userBean.getUseLvl()!=6){
			return dataGridBean;
		}else {
			StringBuilder sql = new StringBuilder(100);
			StringBuilder sqlCount = new StringBuilder(100);
			Map map=new HashMap();
			sql.append("select t.maintype,t.subtype,sum(t.allamount) allamount from bill_total_type_info t where t.maintype=:maintype ");
			sql.append(" and t.unno=:unno");
			map.put("maintype",1);
			map.put("unno",userBean.getUnNo());
			if(StringUtils.isNotEmpty(toolDealBean.getTxnDay())
					&& StringUtils.isNotEmpty(toolDealBean.getTxnDay1())){
				sql.append(" and t.txnday>=:txnday and t.txnday<=:txnday1 ");
                map.put("txnday",toolDealBean.getTxnDay().replace("-", ""));
                map.put("txnday1",toolDealBean.getTxnDay1().replace("-", ""));
			}else{
				SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
				sql.append(" and t.txnday=:txnday ");
				map.put("txnday",df.format(new Date()));
			}

			if(StringUtils.isNotEmpty(toolDealBean.getTxnType())){
				sql.append(" and t.subType=:subType ");
				map.put("subType",Integer.valueOf(toolDealBean.getTxnType()));
			}
			sql.append(" group by t.maintype,t.subtype");
			sqlCount.append("select count(1) from (").append(sql.toString()).append(")");
			StringBuilder sqlSum = new StringBuilder(256);
			sqlSum.append("select -1 as subType, nvl(sum(allamount),0) allamount  from (").append(sql.toString()).append(")");
			List<Map<String, String>> list = checkUnitDealDataDao.queryObjectsBySqlList(sql.toString(), map, toolDealBean.getPage(), toolDealBean.getRows());
			List<Map<String, String>> listSum = checkUnitDealDataDao.queryObjectsBySqlListMap(sqlSum.toString(),map);
			List<Map<String, String>> listAll=new ArrayList<Map<String, String>>();
			listAll.add(listSum.get(0));
			listAll.addAll(list);
			BigDecimal counts = checkUnitDealDataDao.querysqlCounts(sqlCount.toString(), map);
			dataGridBean.setTotal(counts.intValue());
			dataGridBean.setRows(listAll);
			return dataGridBean;
		}
	}
}