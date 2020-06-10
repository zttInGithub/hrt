package com.hrt.biz.bill.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.dao.TrafficBankInsertDao;
import com.hrt.biz.bill.dao.TrafficBankInsertFCDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.entity.model.CheckUnitdealdetailCommFCModel;
import com.hrt.biz.bill.entity.model.CheckUnitdealdetailCommModel;
import com.hrt.biz.bill.entity.pagebean.CheckUnitdealCommBean;
import com.hrt.biz.bill.service.TrafficBankInsertService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class TrafficBankInsertServiceImpl implements TrafficBankInsertService{
private TrafficBankInsertDao trafficBankInsertDao;
private TrafficBankInsertFCDao trafficBankInsertFCDao;
private IAgentSalesDao agentSalesDao;
	public IAgentSalesDao getAgentSalesDao() {
	return agentSalesDao;
}
public void setAgentSalesDao(IAgentSalesDao agentSalesDao) {
	this.agentSalesDao = agentSalesDao;
}
	public TrafficBankInsertDao getTrafficBankInsertDao() {
	return trafficBankInsertDao;
}
public void setTrafficBankInsertDao(TrafficBankInsertDao trafficBankInsertDao) {
	this.trafficBankInsertDao = trafficBankInsertDao;
}

public void setTrafficBankInsertFCDao(
		TrafficBankInsertFCDao trafficBankInsertFCDao) {
	this.trafficBankInsertFCDao = trafficBankInsertFCDao;
}

String sqlExcel=null;
/**
 * 数据导出
 */
public List<Map<String, String>> TrafficBankListExport(CheckUnitdealCommBean checkUnitdealCommBean,UserBean userBean){
	
	List<Map<String, String>> list=trafficBankInsertDao.queryObjectsBySqlList(sqlExcel);
	
	return list;
}

/**
 * 查询数据
 */
public DataGridBean TrafficBankLists(CheckUnitdealCommBean checkUnitdealCommBean,UserBean userBean) {
	Map<String, Object> map = new HashMap<String, Object>();
	String sqlD="select * from bill_agentsalesinfo where user_id="+userBean.getUserID();
	List<AgentSalesModel> agentSaleslist= agentSalesDao.executeSqlT(sqlD, AgentSalesModel.class,map);
   String sql=" select to_char(e.SIGNDATE,'yyyy-MM-dd') TRADEDATE,d.MID MID,d.MERCHNAME MERCHNAME,nvl(TXNAMT,0) TXNAMT ,nvl(e.COUNTS,0) COUNTS,nvl(f.WLTXNAMT,0) WLTXNAMT,nvl(f.WLCOUNTS,0) WLCOUNTS,(nvl(TXNAMT,0)+nvl(f.WLTXNAMT,0))  SUMTXNAMT," +
  		" (select SALENAME from  BILL_AGENTSALESINFO  where busid= d.SIGNUSERID) as ASALENAME," +
  		" (select SALENAME from  BILL_AGENTSALESINFO  where busid= d.MAINTAINUSERID) as BSALENAME" +
  		"  from  BILL_MERCHANTINFO_COMM d," +
  		" (select a.MID,TXNAMT,COUNTS,b.SIGNDATE from  BILL_MERCHANTINFO_COMM a left join (" +
  		" select MID,SUM(TXNAMT) TXNAMT,COUNT(*) COUNTS,SIGNDATE from  CHECK_UNITDEALDETAIL_COMM " +
  		" where CBRAND = 1 and to_char(SIGNDATE,'yyyy-MM-dd') between '"+checkUnitdealCommBean.getCreateDate()+"' and '"+checkUnitdealCommBean.getCreateDateone()+"'  group by MID,SIGNDATE) b " +
  		" on a.MID = b.MID) e," +
  		" (select a.mid,WLTXNAMT,WLCOUNTS from  BILL_MERCHANTINFO_COMM a left join (" +
  		" select b.mid,SUM(TXNAMT) WLTXNAMT,COUNT(*) WLCOUNTS,c.SIGNDATE from  CHECK_UNITDEALDETAIL_COMM_FC c," +
  		" BILL_MERCHANTTERMINALINFO_COMM b where c.mid=b.WILDMID and c.DEVNO=b.WILDTID and  to_char(SIGNDATE,'yyyy-MM-dd') between '"+checkUnitdealCommBean.getCreateDate()+"' and '"+checkUnitdealCommBean.getCreateDateone()+"' " +
  		"group by b.mid,c.SIGNDATE ) b   on a.MID = b.MID) f " +
  		"where d.MID = e.MID and e.MID = f.MID " +
  		"and (COUNTS > 0 or WLCOUNTS >0) ";
   if(agentSaleslist.size()>0){
	   sql+=" and d.MAINTAINUSERID='"+agentSaleslist.get(0).getBusid()+"' ";
   }
   if(checkUnitdealCommBean.getMerchname()!=null&&!checkUnitdealCommBean.getMerchname().equals("")){
	   sql+=" and d.MERCHNAME like '%"+checkUnitdealCommBean.getMerchname()+"%' ";
   }
   if(checkUnitdealCommBean.getMid()!=null&&!checkUnitdealCommBean.getMid().equals("")){
	   sql+=" and d.MID='"+checkUnitdealCommBean.getMid()+"' ";
   }
  
   sqlExcel=sql;
   
    String sqlCount="select count(*) from ( "+sql+" )";
	String sqlSum="select SUM(TXNAMT) TXNAMT,SUM(COUNTS) COUNTS,SUM(WLTXNAMT) WLTXNAMT,SUM(WLCOUNTS) WLCOUNTS,SUM(SUMTXNAMT) SUMTXNAMT from ( "+sql+" )";   
	List proListSum = trafficBankInsertDao.executeSql(sqlSum);
	
	BigDecimal counts = trafficBankInsertDao.querysqlCounts(sqlCount, null);
    List proList = trafficBankInsertDao.queryObjectsBySqlList(sql,map,checkUnitdealCommBean.getPage(), checkUnitdealCommBean.getRows());
   
    if(proList.size()>0)
    {
    proList.addAll(0, proListSum);
    }
    DataGridBean dgd = new DataGridBean();
    dgd.setTotal(counts.intValue());
	dgd.setRows(proList);
	return dgd;

}
/**
 * 判断是否重复内卡
 */
public Integer inportref(String date){
	String sql="select count(*) from CHECK_UNITDEALDETAIL_COMM where to_char(SIGNDATE,'yyyy-MM-dd')='"+date+"' ";
	BigDecimal count=trafficBankInsertDao.querysqlCounts(sql,null);
	return count.intValue();
}
/**
 * 判断是否重复外卡
 */
public Integer inportrefW(String date){
	String sql="select count(*) from CHECK_UNITDEALDETAIL_COMM_FC where to_char(SIGNDATE,'yyyy-MM-dd')='"+date+"' ";
	BigDecimal count=trafficBankInsertDao.querysqlCounts(sql,null);
	return count.intValue();
}

	/**
	 * 清算文件导入 交通
	 */
	CheckUnitdealdetailCommModel checkUnitdealdetailCommModel=null;
	String AccountDay=null;
	@SuppressWarnings("unchecked")
	public List<HashMap> saveTrafficBankInsertExport(String filename,String name) {
		  Calendar calendar=Calendar.getInstance();
		List list=new ArrayList();
		 String str=new String();//原有txt内容  
	        try {  
	    // AccountDay = filename.split("--")[1].replaceAll(".txt", "");
	        	int i = 0;
	            File f = new File(filename);   
	            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(f),"iso-8859-1"));  
	            while ((str = input.readLine()) != null) { 
	          
	           String response = new String(str.toString().getBytes("iso-8859-1"), "gb2312");
	               String aa[]= response.split(",");
	        CheckUnitdealdetailCommModel checkCommModel= new CheckUnitdealdetailCommModel();
	        checkCommModel.setMid(aa[0]);
	        checkCommModel.setMerchname(aa[1]);
	        checkCommModel.setDevno(aa[2]);
	        checkCommModel.setCardno(aa[3]);
	        checkCommModel.setTxnamt(Double.parseDouble(aa[4]));
	      
	        checkCommModel.setCbrand("1");
	        checkCommModel.setTxndate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((aa[8]).toString()));
	        checkCommModel.setSigndate(new SimpleDateFormat("yyyy-MM-dd").parse(aa[9].toString()));
	        trafficBankInsertDao.saveObject(checkCommModel);
	               i++;
	            }
	            input.close();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	       }
			return list;
	}
	@Override
	public List<HashMap> saveTrafficBankInsertWKExport(String filename,
			String name) {
		Calendar calendar=Calendar.getInstance();
		List list=new ArrayList();
		String str=new String();//原有txt内容  
		try {  
			//AccountDay = filename.split("--")[1].replaceAll(".txt", "");
			int i = 0;
			File f = new File(filename);   
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(f),"iso-8859-1"));  
			while ((str = input.readLine()) != null) { 
				
				String response = new String(str.toString().getBytes("iso-8859-1"), "gb2312");
				String aa[]= response.split(",");
				CheckUnitdealdetailCommFCModel checkCommFCModel= new CheckUnitdealdetailCommFCModel();
				checkCommFCModel.setMid(aa[0]);
				checkCommFCModel.setMerchname(aa[1]);
				checkCommFCModel.setDevno(aa[2]);
				checkCommFCModel.setCardkind(aa[3]);
				checkCommFCModel.setCardno(aa[4]);
				checkCommFCModel.setTxnamt(Double.parseDouble(aa[5]));
				checkCommFCModel.setTxndate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((aa[7]).toString()));
				checkCommFCModel.setSigndate(new SimpleDateFormat("yyyy-MM-dd").parse(aa[8].toString()));
				trafficBankInsertFCDao.saveObject(checkCommFCModel);
				i++;
			}
			input.close();  
		} catch (Exception e) {  
			e.printStackTrace();  
		}
		return list;
	}
}
