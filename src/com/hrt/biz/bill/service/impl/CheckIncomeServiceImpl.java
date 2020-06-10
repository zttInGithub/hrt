package com.hrt.biz.bill.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.dao.ICheckIncomeDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.entity.pagebean.CheckIncomeBean;
import com.hrt.biz.bill.service.ICheckIncomeService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class CheckIncomeServiceImpl implements ICheckIncomeService
{
	private static final int CELL_TYPE_NUMERIC = 0;
	private ICheckIncomeDao checkIncomeDao;
	private IUnitDao unitDao;
	private IAgentSalesDao agentSalesDao;
	public IUnitDao getUnitDao() {
		return unitDao;
	}

	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}

	public ICheckIncomeDao getCheckIncomeDao() {
		return checkIncomeDao;
	}

	public void setCheckIncomeDao(ICheckIncomeDao checkIncomeDao) {
		this.checkIncomeDao = checkIncomeDao;
	}

	public IAgentSalesDao getAgentSalesDao() {
		return agentSalesDao;
	}

	public void setAgentSalesDao(IAgentSalesDao agentSalesDao) {
		this.agentSalesDao = agentSalesDao;
	}

	public DataGridBean CheckIncomequeryLists(CheckIncomeBean checkIncomeBean,UserBean userBean) {
		String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		Map<String, Object> usermap = new HashMap<String, Object>();
		usermap.put("userID", userBean.getUserID().toString());
		usermap.put("maintainType", "D");
		List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		
		Map<String, Object> map = new HashMap<String, Object>();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		String untino=userBean.getUnNo();
        String sql="select (c.totsamt-c.totmfee) SHOUCOUNT,c.mid,c.SETTLEDAY,c.TOTSAMT,c.TOTMFEE,c.TOTRAMT," +
       				" c.TOTAAMT,c.TOTMNAMT,c.REMARKS,c.minfo1,b.RNAME, c.txnday ||'至'||c.txnday2 as TXNDAY,c.status,c.failmsg  " +
       				" from check_income c inner join bill_merchantinfo b on c.mid=b.mid"; 
//        if("0".equals(userBean.getIsM35Type())){
//			sql +=" and b.isM35 !=1 ";
//		}else if("1".equals(userBean.getIsM35Type())){
//			sql +=" and b.isM35 = 1 ";
//		}
        if(agentSalesModels.size()==0){
	   		if(userBean.getUseLvl()==3){
				sql+=" and c.MID='"+userBean.getLoginName()+"'";
			}else{
		        if("110000".equals(userBean.getUnNo())){
		        }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				}else{
		        	sql+=" and b.unno in (select UNNO from sys_unit start with unno ='"+untino+"' and status=1 connect by NOCYCLE prior  unno =  upper_unit)  ";
		        }	       
			}
       }else{
    	   sql+=" and b.busid='"+agentSalesModels.get(0).getBusid()+"'";
       }
	   //查询条件
	   if (null != checkIncomeBean.getDateone()&& null != checkIncomeBean.getDatetwo()
			&& !checkIncomeBean.getDateone().equals("")&& !checkIncomeBean.getDatetwo().equals("")){
		   sql+=" and c.settleday >='"+checkIncomeBean.getDateone().replace("-", "")+"' and c.settleday<='"+checkIncomeBean.getDatetwo().replace("-", "")+"'";	
	   }else{
		   sql+=" and c.settleday=to_char(sysdate,'yyyyMMdd')";		
	   }
	   if (null != checkIncomeBean.getMid() && !checkIncomeBean.getMid().equals("")){
		   sql+=" and c.mid='"+checkIncomeBean.getMid()+"'";
	   }
	   sql+=" order by c.ciid ";
	   String sqlCount="select count(*) from ( "+sql+" )";
	 
	   BigDecimal counts = checkIncomeDao.querysqlCounts(sqlCount, null);
	   List proList = checkIncomeDao.queryObjectsBySqlList(sql,map,checkIncomeBean.getPage(), checkIncomeBean.getRows());
	   DataGridBean dgd = new DataGridBean();
	   dgd.setTotal(counts.intValue());
	   dgd.setRows(proList);
	   return dgd;
		
	}
	
	
	
	/**
	 * 数据导出
	 */
	public List<Map<String, String>> CheckIncomeListExport(CheckIncomeBean checkIncomeBean,UserBean userBean){
		String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		Map<String, Object> usermap = new HashMap<String, Object>();
		usermap.put("userID", userBean.getUserID().toString());
		usermap.put("maintainType", "D");
		List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		
		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		 String untino=userBean.getUnNo();
         String sql="select (c.totsamt-c.totmfee) SHOUCOUNT,c.mid,c.SETTLEDAY,c.TOTSAMT,c.TOTMFEE,c.TOTRAMT,nvl(c.TOTAAMT,'0') as TOTAAMT,c.TOTMNAMT,c.REMARKS,b.RNAME, c.txnday ||'至'||c.txnday2 as TXNDAY  " +
       				" from check_income c,bill_merchantinfo b where c.mid=b.mid"; 
//        if("0".equals(userBean.getIsM35Type())){
// 			sql +=" and b.isM35 !=1 ";
// 		}else if("1".equals(userBean.getIsM35Type())){
// 			sql +=" and b.isM35 = 1 ";
// 		}
       if(agentSalesModels.size()==0){
   		if(userBean.getUseLvl()==3){
			sql+=" and c.MID in (select MID from  bill_MerchantInfo where  PARENTMID= '"+userBean.getLoginName()+"' or MID ='"+userBean.getLoginName()+"')";
		}else{
	        if("110000".equals(userBean.getUnNo())){
	        	
	        }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){
				sql+=" and b.unno IN (SELECT UNNO  FROM SYS_UNIT  WHERE UPPER_UNIT IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = '"+untino+"' OR UNNO = '"+untino+"') OR UNNO = '"+untino+"')";
			}else{
	        	sql+=" and b.unno IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = '"+untino+"' OR UNNO = '"+untino+"')";
	        }	       
		}
       }else{
    	   sql+=" and b.busid='"+agentSalesModels.get(0).getBusid()+"'";
       }

		//查询条件
		if (null != checkIncomeBean.getDateone()
				&& null != checkIncomeBean.getDatetwo()
				&& !checkIncomeBean.getDateone().equals("")
				&& !checkIncomeBean.getDatetwo().equals("")) 
		{
			sql+=" and to_char(to_date(c.settleday,'yyyy-MM-dd'),'yyyy-MM-dd') between '"+checkIncomeBean.getDateone()+"' and '"+checkIncomeBean.getDatetwo()+"'";	
		}
		else{
			return null;
//			Calendar calendar = Calendar.getInstance();
//			int YY = calendar.get(Calendar.YEAR);
//		    int MM = calendar.get(Calendar.MONTH)+1;
//		    int DD = calendar.get(Calendar.DATE);
//		    String timeDate=YY+"-";
//		    if(MM<=9)
//		    { timeDate=timeDate+"0"+MM;}
//		    else{timeDate=timeDate+MM;}
//		    if(DD<=9)
//		    { timeDate=timeDate+"-"+"0"+DD;}
//		    else{timeDate=timeDate+"-"+DD;}
//		    
//		    System.out.print(timeDate);
//			sql+=" and to_char(to_date(c.settleday,'yyyy-MM-dd'),'yyyy-MM-dd')='"+timeDate+"'";		
		}
		if (null != checkIncomeBean.getMid()
				&& !checkIncomeBean.getMid().equals("")) 
		{
			sql+=" and c.mid='"+checkIncomeBean.getMid()+"'";
		}
		sql+=" order by c.ciid ";
		
		List<Map<String, String>> list=checkIncomeDao.queryObjectsBySqlList(sql);
		
		return list;
	}
	
}
