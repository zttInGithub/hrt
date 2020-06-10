package com.hrt.biz.check.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.check.dao.CheckUnitDealDetailDao;
import com.hrt.biz.check.entity.pagebean.ToolDealBean;
import com.hrt.biz.check.service.CheckUnitDealDetailService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;

public class CheckUnitDealDetailServiceImpl implements
		CheckUnitDealDetailService {
	String zerosqlExcel="";
	private CheckUnitDealDetailDao checkUnitDealDetailDao;
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

	public CheckUnitDealDetailDao getCheckUnitDealDetailDao() {
		return checkUnitDealDetailDao;
	}

	public void setCheckUnitDealDetailDao(
			CheckUnitDealDetailDao checkUnitDealDetailDao) {
		this.checkUnitDealDetailDao = checkUnitDealDetailDao;
	}
	
	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	/*
	 * 显示全部   明细
	 */
	public List queryFindCheckUnitDealDetail(ToolDealBean toolDealBean) throws Exception {
//		Calendar cal = Calendar.getInstance();cal.add(Calendar.DAY_OF_YEAR, -1);
		String stxnday;
		String stxnday1;
		if(toolDealBean.getTxnDate()!= null&&(toolDealBean.getTxnDate().trim().length())<11)
		{
			stxnday=toolDealBean.getTxnDate().trim();	
			
		}else{
			stxnday=toolDealBean.getTxnDate();}
		if(toolDealBean.getTxnDate1()!= null&&(toolDealBean.getTxnDate1().trim().length())<11)
		{
			stxnday1=toolDealBean.getTxnDate1().trim(); 
		}else{stxnday1=toolDealBean.getTxnDate1();}
		String sql = "select STAN,RRN,AUTHCODE,CARDPAN,CBRAND,MID,TID,to_char(TXNAMOUNT,'FM999,999,999,999,990.00') TXNAMOUNT," +
				"TXNDAY,TXNDATE,to_char(MDA,'FM999,999,999,999,990.00') MDA, to_char(MNAMT,'FM999,999,999,999,990.00') MNAMT" +
				" from CHECK_UNITDEALDETAIL  where TID = '"+toolDealBean.getTid()+"' and MID='"+toolDealBean.getMid()+"' " +
				" and TXNDAY between '"+stxnday.replaceAll("-", "")+"' and '"+stxnday1.replaceAll("-", "")+"'";
				 
		if (toolDealBean.getFirsix() != null && !"".equals(toolDealBean.getFirsix().trim())) {
			sql += " AND substr(CARDPAN,1,6)='"+toolDealBean.getFirsix()+"'" ;
		}
		if (toolDealBean.getLastfour() != null && !"".equals(toolDealBean.getLastfour().trim())) {
			sql += " AND substr(CARDPAN,(length(CARDPAN)-3),length(CARDPAN))='"+toolDealBean.getLastfour()+"'" ;
		}
		sql +=" order by pkid asc";
		List<Map<String, String>> list=checkUnitDealDetailDao.executeSql(sql);
		
		return list;
		}
	/*
	 * 显示全部   明细
	 */
	public DataGridBean queryFindCheckUnitDealDetailPhone(ToolDealBean toolDealBean) throws Exception {
		DataGridBean dgd = new DataGridBean();
//		Calendar cal = Calendar.getInstance();cal.add(Calendar.DAY_OF_YEAR, -1);
//		Date d = cal.getTime(); 
		String stxnday;
		String stxnday1;
		if(toolDealBean.getTxnDate()!= null&&(toolDealBean.getTxnDate().trim().length())<11)
		{
			stxnday=toolDealBean.getTxnDate().trim();	
			
		}else{
			stxnday=toolDealBean.getTxnDate();}
		if(toolDealBean.getTxnDate1()!= null&&(toolDealBean.getTxnDate1().trim().length())<11)
		{
			stxnday1=toolDealBean.getTxnDate1().trim(); 
		}else{stxnday1=toolDealBean.getTxnDate1();}
		String sql = "select STAN,RRN,AUTHCODE,CARDPAN,CBRAND,MID,TID,to_char(TXNAMOUNT,'FM999,999,999,999,990.00') TXNAMOUNT," +
				"TXNDAY,TXNDATE,to_char(MDA,'FM999,999,999,999,990.00') MDA, to_char(MNAMT,'FM999,999,999,999,990.00') MNAMT" +
				" from CHECK_UNITDEALDETAIL  where TID = '"+toolDealBean.getTid()+"' and MID='"+toolDealBean.getMid()+"' " +
				"and TXNDAY between '"+stxnday.replaceAll("-", "")+"' and '"+stxnday1.replaceAll("-", "")+"'";
				 
		if (toolDealBean.getFirsix() != null && !"".equals(toolDealBean.getFirsix().trim())) {
			sql += " AND substr(CARDPAN,1,6)='"+toolDealBean.getFirsix()+"'" ;
		}
		if (toolDealBean.getLastfour() != null && !"".equals(toolDealBean.getLastfour().trim())) {
			sql += " AND substr(CARDPAN,(length(CARDPAN)-3),length(CARDPAN))='"+toolDealBean.getLastfour()+"'" ;
		}
		sql +=" order by pkid asc";
//		List<Map<String, String>> list=checkUnitDealDetailDao.executeSql(sql);
		
	     String  sqlCount="select count(*) from ("+sql+")";
			BigDecimal counts = checkUnitDealDetailDao.querysqlCounts(sqlCount,null);
			List<Map<String, String>> proList = checkUnitDealDetailDao
					.queryObjectsBySqlList(sql, null, toolDealBean.getPage(),
							toolDealBean.getRows());
			dgd.setTotal(counts.intValue());
			dgd.setRows(proList);
			return dgd;
		
		}
	
	//group by 中的明细
	public List<Map<String, String>> queryFindCheckUnitDealE(ToolDealBean toolDealBean, UserBean userBean)throws Exception {
		String stxnday;
		String stxnday1;
		if((toolDealBean.getTxnDay().trim().length())<11)
		{
			stxnday=toolDealBean.getTxnDay().trim();	
			
		}else{
			stxnday=toolDealBean.getTxnDay();}
		if((toolDealBean.getTxnDay1().trim().length())<11)
		{
			stxnday1=toolDealBean.getTxnDay1().trim();	
		}else{stxnday1=toolDealBean.getTxnDay1();}
		String sql = "select u.STAN,u.RRN,replace(nvl(u.AUTHCODE,' '),'null',' ') as AUTHCODE,u.CARDPAN,u.TXNDAY,u.TXNDATE,decode(u.CBRAND,1,'借记卡',2,'贷记卡','未知') CBRAND,u.MID,u.TID," +
				"to_char(u.TXNAMOUNT,'FM999,999,999,999,990.00') TXNAMOUNT,to_char(u.MDA,'FM999,999,999,999,990.00') MDA," +
				" to_char(u.MNAMT,'FM999,999,999,999,990.00') MNAMT from CHECK_UNITDEALDETAIL u,bill_merchantinfo t where 1=1 and u.mid = t.mid " ;
//		if("0".equals(userBean.getIsM35Type())){
//			sql +=" and t.isM35 !=1 ";
//		}else if("1".equals(userBean.getIsM35Type())){
//			sql +=" and t.isM35 = 1 ";
//		}
		if (toolDealBean.getMid() != null && !"".equals(toolDealBean.getMid())) {
			sql += " AND u.MID = '"+toolDealBean.getMid()+"'" ;
		}
		if (toolDealBean.getFirsix() != null && !"".equals(toolDealBean.getFirsix())) {
			sql += " AND substr(u.CARDPAN,1,6)='"+toolDealBean.getFirsix()+"'" ;
		}
		if (toolDealBean.getLastfour() != null && !"".equals(toolDealBean.getLastfour())) {
			sql += " AND substr(u.CARDPAN,(length(u.CARDPAN)-3),length(u.CARDPAN))='"+toolDealBean.getLastfour()+"'" ;
		}	
		sql+=" AND u.TXNDAY between '"+stxnday.replaceAll("-", "")+"' and '"+stxnday1.replaceAll("-", "")+
				"' order by TID,pkid asc";
		List<Map<String, String>> list=checkUnitDealDetailDao.executeSql(sql);
		return list;
		}


	/*
	 * 显示全部
	 */
	@Override
	public DataGridBean queryCheckUnitDealDetail(ToolDealBean toolDealBean,UserBean userBean) {
		String sqlsum="";
		String stxnday;
		String stxnday1;
//		Calendar cal = Calendar.getInstance();cal.add(Calendar.DAY_OF_YEAR, -1);
		Map<String, Object> map = new HashMap<String, Object>();
		String sql=null;
		String sqlCount=null;
		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		DataGridBean dgd = new DataGridBean();
		sql="select count(*) counts, to_char(sum(u.MNAMT), 'FM999,999,999,999,990.00') MNAMT," +
				"to_char(sum(u.MDA), 'FM999,999,999,999,990.00') MDA,u.MID,b.RNAME,u.TID," +
				"to_char(sum(u.TXNAMOUNT), 'FM999,999,999,999,999.00') TXNAMOUNT,c.INSTALLEDNAME from check_UnitDealDetail u inner join" +
				" bill_MerchantInfo b on  b.MID=u.MID inner join bill_merchantterminalinfo c on u.tid=c.tid  and c.mid=b.mid and c.maintaintype !='D' and c.approvestatus='Y' ";
		sqlsum="select count(*) counts, sum(u.MNAMT) MNAMT,sum(u.MDA) MDA,u.MID,b.RNAME,u.TID,sum(u.TXNAMOUNT) TXNAMOUNT" +
				" from check_UnitDealDetail u inner join bill_MerchantInfo b on  b.MID=u.MID inner join bill_merchantterminalinfo c on u.tid=c.tid and c.maintaintype !='D'  and c.approvestatus='Y' ";
		//商户
		if(userBean.getUseLvl()==3){
			//sql+=" and b.MID ='"+userBean.getLoginName()+"'";
			sql+=" and b.MID ='"+userBean.getLoginName()+"' ";
			sqlsum+=" and b.MID ='"+userBean.getLoginName()+"' ";
		}else{
			//为总公司
			if("110000".equals(userBean.getUnNo())){
			 }
			//为总公司并且是部门---查询全部
			else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else{
				String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
				sql+=" and b.unno IN ("+childUnno+") ";
				sqlsum+=" and b.unno IN ("+childUnno+" )";
			}
		}
//		if("0".equals(userBean.getIsM35Type())){
//			sql +=" and b.isM35 !=1 ";
//			sqlsum +=" and b.isM35 !=1 ";
//		}else if("1".equals(userBean.getIsM35Type())){
//			sql +=" and b.isM35 = 1 ";
//			sqlsum +=" and b.isM35 = 1 ";
//		}
		if(sql==null){return dgd;}
		if((null == toolDealBean.getTxnDay()&& null == toolDealBean.getTxnDay1())||toolDealBean.getTxnDay().equals("")&&toolDealBean.getTxnDay1().equals("")){
			return null;
		}
		if (null != toolDealBean.getMid() && !toolDealBean.getMid().equals("")) {
			sql += " and u.MID = '"+toolDealBean.getMid()+"'";
			sqlsum += " and u.MID = '"+toolDealBean.getMid()+"'";
		}
		if (null != toolDealBean.getUnno() && !toolDealBean.getUnno().equals("")) {
			sql += " and b.unno IN ("+merchantInfoService.queryUnitUnnoUtil(toolDealBean.getUnno())+")";
			sqlsum += " and b.unno in ("+merchantInfoService.queryUnitUnnoUtil(toolDealBean.getUnno())+")";
		}
		
		if (toolDealBean.getFirsix() != null && !"".equals(toolDealBean.getFirsix())) {
			sql += " AND substr(u.CARDPAN,1,6)='"+toolDealBean.getFirsix()+"'" ;
			sqlsum +=" AND substr(u.CARDPAN,1,6)='"+toolDealBean.getFirsix()+"'" ;
		}
		if (toolDealBean.getLastfour() != null && !"".equals(toolDealBean.getLastfour())) {
			sql += " AND substr(u.CARDPAN,(length(u.CARDPAN)-3),length(u.CARDPAN))='"+toolDealBean.getLastfour()+"'" ;
			sqlsum +=" AND substr(u.CARDPAN,(length(u.CARDPAN)-3),length(u.CARDPAN))='"+toolDealBean.getLastfour()+"'" ;
		}	
		
		/*if (null != toolDealBean.getTxnDay()
				&& null != toolDealBean.getTxnDay1()
				&& !toolDealBean.getTxnDay().equals("")
				&& !toolDealBean.getTxnDay1().equals("")) {
			sql += " and u.TXNDAY between to_char(to_date( '"+toolDealBean.getTxnDay()+"','yyyy-MM-dd'),'yyyyMMdd')" +
					" and  to_char(to_date( '"+toolDealBean.getTxnDay1()+"','yyyy-MM-dd'),'yyyyMMdd')";
			sqlsum += " and u.TXNDAY between to_char(to_date( '"+toolDealBean.getTxnDay()+"','yyyy-MM-dd'),'yyyyMMdd') " +
					"and  to_char(to_date( '"+toolDealBean.getTxnDay1()+"','yyyy-MM-dd'),'yyyyMMdd')";
		}*/
		if (null != toolDealBean.getTxnDay()
				&& null != toolDealBean.getTxnDay1()
				&& !toolDealBean.getTxnDay().equals("")
				&& !toolDealBean.getTxnDay1().equals("")) {
		if((toolDealBean.getTxnDay().trim().length())<11)
		{
			stxnday=toolDealBean.getTxnDay().trim();	
			
		}else{
			stxnday=toolDealBean.getTxnDay();}
		if((toolDealBean.getTxnDay1().trim().length())<11)
		{
			stxnday1=toolDealBean.getTxnDay1().trim();	
		}else{stxnday1=toolDealBean.getTxnDay1();}
//		sql += " and to_char(to_date(u.TXNDAY||u.txndate,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS') between '"+stxnday+"' and '"+stxnday1+"'";
//		sqlsum += " and to_char(to_date(u.TXNDAY||u.txndate,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS') between '"+stxnday+"' and '"+stxnday1+"'";
		sql += " and u.TXNDAY between '"+stxnday.replaceAll("-", "")+"' and '"+stxnday1.replaceAll("-", "")+"'";
		sqlsum += " and u.TXNDAY between '"+stxnday.replaceAll("-", "")+"' and '"+stxnday1.replaceAll("-", "")+"'";
		}

       sql+=" group by u.TID,b.rname,u.mid,c.installedname order by u.TID";
       sqlsum+=" group by u.TID,b.rname,u.mid,c.installedname order by u.TID";
       sqlCount="select count(*) from ("+sql+")";
		BigDecimal counts = checkUnitDealDetailDao.querysqlCounts(sqlCount,null);
		List<Map<String, String>> proList = checkUnitDealDetailDao
				.queryObjectsBySqlList(sql, map, toolDealBean.getPage(),
						toolDealBean.getRows());
		dgd.setTotal(counts.intValue());
		dgd.setRows(proList);
		return dgd;
	}

	/*
	 * 20140交易待结算报表
	 */
	public DataGridBean queryCheckUnitDealDetailNoClosing(
			ToolDealBean toolDealBean,UserBean userBean) {
		Calendar cal = Calendar.getInstance();cal.add(Calendar.DAY_OF_YEAR, -1);
		Date d = cal.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
		String reTime = df.format(d);
		String sql=null;
		String sqlCount =null;
		//数据条数
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		Map<String, Object> map = new HashMap<String, Object>();

		DataGridBean dgd = new DataGridBean();
		sqlCount="select count(*) from check_UnitDealDetail u inner join bill_MerchantInfo b on  b.MID=u.MID and" +
				" u.ACTIONSETTTLEDATE is null";
		sql="select to_char(u.MNAMT, 'FM999,999,999,999,990.00') MNAMT,to_char(u.MDA, 'FM999,999,999,999,990.00') MDA, " +
				"u.TXNDAY,u.TXNDATE,u.MID,b.RNAME,u.TID,u.TXNTYPE,to_char(u.TXNAMOUNT, 'FM999,999,999,999,990.00') TXNAMOUNT, " +
				"u.CARDPAN,u.AUTHCODE,u.RRN,u.CBRAND,u.STAN,u.ACTIONSETTTLEDATE,u.SCHEDULESETTLEDATE from " +
				"check_UnitDealDetail u  inner join bill_MerchantInfo b on  b.MID=u.MID and u.ACTIONSETTTLEDATE is null ";
		if(userBean.getUseLvl()==3){
			sql+=" and b.MID in (select MID from  bill_MerchantInfo where  " +
			"PARENTMID= '"+userBean.getLoginName()+"' or MID ='"+userBean.getLoginName()+"')  ";
			sqlCount+=" and b.MID in (select MID from  bill_MerchantInfo where  " +
			"PARENTMID= '"+userBean.getLoginName()+"' or MID ='"+userBean.getLoginName()+"')  ";
		}else{
			
			if("110000".equals(userBean.getUnNo())){
				
			}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else{
				String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
				sql+=" and b.unno IN ("+childUnno+" )";
				sqlCount+=" and b.unno IN ("+childUnno+" )";
			}				
		}
//		if("0".equals(userBean.getIsM35Type())){
//			sql +=" and b.isM35 !=1 ";
//			sqlCount +=" and b.isM35 !=1 ";
//		}else if("1".equals(userBean.getIsM35Type())){
//			sql +=" and b.isM35 = 1 ";
//			sqlCount +=" and b.isM35 = 1 ";
//		}
		if(sql==null){return dgd;}
		if((null == toolDealBean.getTxnDay()&& null == toolDealBean.getTxnDay1())||toolDealBean.getTxnDay().equals("")&&toolDealBean.getTxnDay1().equals("")){
			sql+=" and u.TXNDAY=to_char(to_date('"+reTime+"','yyyy-MM-dd'),'yyyyMMdd') ";
			sqlCount += " and u.TXNDAY=to_char(to_date('"+reTime+"','yyyy-MM-dd'),'yyyyMMdd') ";
		}
		if (null != toolDealBean.getRname()
				&& !toolDealBean.getRname().equals("")) {
			sql += " and b.RNAME like '"+toolDealBean.getRname()+"%'"+"";
			sqlCount+=" and b.RNAME like '"+toolDealBean.getRname()+"%'"+"";
		}
		if (null != toolDealBean.getMid() && !toolDealBean.getMid().equals("")) {
			sql += " and u.MID = '"+toolDealBean.getMid()+"'";
			sqlCount+=" and u.MID = '"+toolDealBean.getMid()+"'";
		}
		if (null != toolDealBean.getTxnDay()
				&& null != toolDealBean.getTxnDay1()
				&& !toolDealBean.getTxnDay().equals("")
				&& !toolDealBean.getTxnDay1().equals("")) {
			sql += " and u.TXNDAY between '"+ toolDealBean.getTxnDay().replaceAll("-", "")+"' and '"+ toolDealBean.getTxnDay1().replaceAll("-", "")+"'";
			sqlCount+=" and u.TXNDAY between '"+ toolDealBean.getTxnDay().replaceAll("-", "")+"' and '"+ toolDealBean.getTxnDay1().replaceAll("-", "")+"'";
		}
		
		if (null != toolDealBean.getAuthCode()
				&& !toolDealBean.getAuthCode().equals("")) {
			sql += " and u.AUTHCODE='"+toolDealBean.getAuthCode()+"'";
			sqlCount+= " and u.AUTHCODE='"+toolDealBean.getAuthCode()+"'";
		}

		if (null != toolDealBean.getCardPan()
				&& !toolDealBean.getCardPan().equals("")) {
			sql += " and u.CARDPAN = '"+toolDealBean.getCardPan()+"'";
			sqlCount+= " and u.CARDPAN = '"+toolDealBean.getCardPan()+"'";
		}
		  sql+=" order by u.TXNDAY";
		BigDecimal counts = checkUnitDealDetailDao.querysqlCounts(sqlCount,null);
		List<Map<String, String>> proListNoClosing = checkUnitDealDetailDao.queryObjectsBySqlList(sql, map, toolDealBean.getPage(),	toolDealBean.getRows());

		dgd.setTotal(counts.intValue());
		dgd.setRows(proListNoClosing);
		return dgd;
	}

	
	//查询零交易量
	public DataGridBean queryzerocountCheckdata(
			ToolDealBean toolDealBean,UserBean userBean) {
		String sql=null;
		String sqlcount=null;
		DataGridBean dgd = new DataGridBean();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
	
		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		sql = "select b.mid,b.RNAME,a.salename,(0) zerocount,('0.00') zeromoncount,('"+toolDealBean.getTxnDay()+"至"+toolDealBean.getTxnDay1()+"') zerotime,c.UN_NAME from  bill_MerchantInfo b " +
				"inner join bill_AgentSalesInfo a on b.maintainUserID = a.busid inner join " +
				"SYS_UNIT c on b.unno=c.unno and b.ApproveStatus='Y'"; 
        	
	    
		//查看角色
		String sqlRole="select ROLE_ID from SYS_USER_ROLE where USER_ID="+userBean.getUserID();
		List listrole=checkUnitDealDetailDao.executeSql(sqlRole);
		Map m11=null;
		for(int i=0;i<listrole.size();i++){
			 m11=(Map)listrole.get(i);
			//System.out.println(m11.get("ROLE_ID"));
		}
		
		String sqlD="select * from bill_agentsalesinfo where user_id='"+userBean.getUserID()+"'and MAINTAINTYPE !='D'";
		List<AgentSalesModel> agentSaleslist= agentSalesDao.executeSqlT(sqlD, AgentSalesModel.class,map2);
		if(agentSaleslist.size()>0){
			sql+="  and b.maintainUserID='"+agentSaleslist.get(0).getBusid()+"' ";
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
				String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
				sql+=" and a.unno=b.unno and  a.unno in("+childUnno+") ";
			}				
		}
//		if("0".equals(userBean.getIsM35Type())){
//			sql +=" and b.isM35 !=1 ";
//		}else if("1".equals(userBean.getIsM35Type())){
//			sql +=" and b.isM35 = 1 ";
//		}
		if(toolDealBean.getTxnDay()==null&&toolDealBean.getTxnDay1()==null||toolDealBean.getTxnDay().equals("")&&toolDealBean.getTxnDay1().equals(""))
         {
        	 return null; 
         }
         else{	 
        	 sql += " and b.mid not in (select mid from check_UnitDealData where txnday between to_char(to_date( '"+ toolDealBean.getTxnDay()+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+"','yyyy-MM-dd'),'yyyyMMdd') )"; 
         }
         if(null != toolDealBean.getMid() && !toolDealBean.getMid().equals(""))
         {
        	 sql += " and b.mid='"+toolDealBean.getMid()+"'";	 
         }
         if(null != toolDealBean.getRname()
 				&& !toolDealBean.getRname().equals(""))
         {
        	 sql += " and b.RNAME like '"+toolDealBean.getRname()+"%'"+"";
         }
		
         zerosqlExcel=sql;
         sqlcount="select count(*) from ("+sql+")";
 		BigDecimal counts = checkUnitDealDetailDao.querysqlCounts(sqlcount,null);
 		List<Map<String, String>> zeroproList = checkUnitDealDetailDao.queryObjectsBySqlList(sql, map, toolDealBean.getPage(),	toolDealBean.getRows());

 		dgd.setTotal(counts.intValue());
 		dgd.setRows(zeroproList);
 		return dgd;   
         
	}
	
	//导出零交易量报表
public List<Map<String, String>> zerocountCheckListExport(ToolDealBean toolDealBean, UserBean userBean){
	String sql=null;
//	String sqlcount=null;
//	Map<String, Object> map1 = new HashMap<String, Object>();
	Map<String, Object> map2 = new HashMap<String, Object>();
//	String untino=userBean.getUnNo();

	 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
//	String untisql="select * from sys_unit where unno='"+untino+"'";
	sql = "select b.mid,b.RNAME,a.salename,(0) zerocount,('0.00') zeromoncount,('"+toolDealBean.getTxnDay()+"至"+toolDealBean.getTxnDay1()+"') zerotime,c.UN_NAME from  bill_MerchantInfo b " +
			"inner join bill_AgentSalesInfo a on b.maintainUserID = a.busid inner join " +
			"SYS_UNIT c on b.unno=c.unno and b.ApproveStatus='Y'"; 
    	
    
	//查看角色
	String sqlRole="select ROLE_ID from SYS_USER_ROLE where USER_ID="+userBean.getUserID();
	List listrole=checkUnitDealDetailDao.executeSql(sqlRole);
	Map m11=null;
	for(int i=0;i<listrole.size();i++){
		 m11=(Map)listrole.get(i);
		//System.out.println(m11.get("ROLE_ID"));
	}
	
	String sqlD="select * from bill_agentsalesinfo where user_id='"+userBean.getUserID()+"'and MAINTAINTYPE !='D'";
	List<AgentSalesModel> agentSaleslist= agentSalesDao.executeSqlT(sqlD, AgentSalesModel.class,map2);
	if(agentSaleslist.size()>0){
		sql+="  and b.maintainUserID='"+agentSaleslist.get(0).getBusid()+"' ";
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
			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			sql+=" and a.unno=b.unno and  a.unno in("+childUnno+") ";
		}	 
	}
//	if("0".equals(userBean.getIsM35Type())){
//		sql +=" and b.isM35 !=1 ";
//	}else if("1".equals(userBean.getIsM35Type())){
//		sql +=" and b.isM35 = 1 ";
//	}
	if(toolDealBean.getTxnDay()==null&&toolDealBean.getTxnDay1()==null||toolDealBean.getTxnDay().equals("")&&toolDealBean.getTxnDay1().equals(""))
     {
    	 return null; 
     }
     else{	 
    	 sql += " and b.mid not in (select mid from check_UnitDealData where txnday between to_char(to_date( '"+ toolDealBean.getTxnDay()+"','yyyy-MM-dd'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+"','yyyy-MM-dd'),'yyyyMMdd') )"; 
     }
     if(null != toolDealBean.getMid() && !toolDealBean.getMid().equals(""))
     {
    	 sql += " and b.mid='"+toolDealBean.getMid()+"'";	 
     }
     if(null != toolDealBean.getRname()
				&& !toolDealBean.getRname().equals(""))
     {
    	 sql += " and b.RNAME like '"+toolDealBean.getRname()+"%'"+"";
     }
	
     zerosqlExcel=sql;
		List<Map<String, String>> list=checkUnitDealDetailDao.queryObjectsBySqlList(zerosqlExcel);
		
		return list;
	}
		
	/*
	 * EXCEl导出(non-Javadoc)
	 */

	public List<Map<String, String>> queryObjectsBySqlList(ToolDealBean toolDealBean,UserBean userBean) {
		String sqlExcel="";
		String stxnday;
		String stxnday1;
		Calendar cal = Calendar.getInstance();cal.add(Calendar.DAY_OF_YEAR, -1);
		String sql=null;
		String sqlCount=null;
		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		sql="select count(*) counts, to_char(sum(u.MNAMT), 'FM999,999,999,999,990.00') MNAMT," +
				"to_char(sum(u.MDA), 'FM999,999,999,999,990.00') MDA,u.MID,b.RNAME,u.TID," +
				"to_char(sum(u.TXNAMOUNT), 'FM999,999,999,999,999.00') TXNAMOUNT,nvl(c.INSTALLEDNAME,'  ') INSTALLEDNAME from check_UnitDealDetail u inner join" +
				" bill_MerchantInfo b on  b.MID=u.MID inner join bill_merchantterminalinfo c on u.tid=c.tid and u.mid=c.mid  and c.maintaintype !='D' and c.approvestatus='Y' ";
		//商户
		if(userBean.getUseLvl()==3){
			//sql+=" and b.MID ='"+userBean.getLoginName()+"'";
			sql+=" and b.MID  ='"+userBean.getLoginName()+"'  ";
		}else{
				//为总公司
			if("110000".equals(userBean.getUnNo())){
			 }
			//为总公司并且是部门---查询全部
			else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else{
				String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
				sql+=" and b.unno in ("+childUnno+") ";
			}
		}
//		if("0".equals(userBean.getIsM35Type())){
//			sql +=" and b.isM35 !=1 ";
//		}else if("1".equals(userBean.getIsM35Type())){
//			sql +=" and b.isM35 = 1 ";
//		}
		if((null == toolDealBean.getTxnDay()&& null == toolDealBean.getTxnDay1())||toolDealBean.getTxnDay().equals("")&&toolDealBean.getTxnDay1().equals("")){
			return null;
		}
		if (null != toolDealBean.getMid() && !toolDealBean.getMid().equals("")) {
			sql += " and u.MID = '"+toolDealBean.getMid()+"'";
		}
		if (null != toolDealBean.getUnno() && !toolDealBean.getUnno().equals("")) {
			sql += " and b.unno IN ("+merchantInfoService.queryUnitUnnoUtil(toolDealBean.getUnno())+")";
		}
		if (toolDealBean.getFirsix() != null && !"".equals(toolDealBean.getFirsix())) {
			sql += " AND substr(u.CARDPAN,1,6)='"+toolDealBean.getFirsix()+"'" ;
		}
		if (toolDealBean.getLastfour() != null && !"".equals(toolDealBean.getLastfour())) {
			sql += " AND substr(u.CARDPAN,(length(u.CARDPAN)-3),length(u.CARDPAN))='"+toolDealBean.getLastfour()+"'" ;
		}	
		
		if (null != toolDealBean.getTxnDay()
				&& null != toolDealBean.getTxnDay1()
				&& !toolDealBean.getTxnDay().equals("")
				&& !toolDealBean.getTxnDay1().equals("")) {
		if((toolDealBean.getTxnDay().trim().length())<11)
		{
			stxnday=toolDealBean.getTxnDay().trim();	
			
		}else{
			stxnday=toolDealBean.getTxnDay();}
		if((toolDealBean.getTxnDay1().trim().length())<11)
		{
			stxnday1=toolDealBean.getTxnDay1().trim();	
		}else{stxnday1=toolDealBean.getTxnDay1();}
		
		sql += " and u.TXNDAY between '"+stxnday.replaceAll("-", "")+"' and '"+stxnday1.replaceAll("-", "")+"'";
		}

       sql+=" group by u.TID,b.rname,u.mid,c.installedname order by u.TID";
       sqlCount="select count(*) from ("+sql+")";
       sqlExcel=sql;
		List<Map<String, String>> list = checkUnitDealDetailDao.queryObjectsBySqlList(sqlExcel);
		return list;
	}
	//总计
	public List<Map<String, String>> queryObjectsBySqlListSum(ToolDealBean toolDealBean, UserBean userBean) {
		String sqlsums="";
		String sqlsum="";
		String stxnday;
		String stxnday1;
		Calendar cal = Calendar.getInstance();cal.add(Calendar.DAY_OF_YEAR, -1);
		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		sqlsum="select count(*) counts, sum(u.MNAMT) MNAMT,sum(u.MDA) MDA,u.MID,b.RNAME,u.TID,sum(u.TXNAMOUNT) TXNAMOUNT" +
				" from check_UnitDealDetail u inner join bill_MerchantInfo b on  b.MID=u.MID";
		//商户
		if(userBean.getUseLvl()==3){
			//sql+=" and b.MID ='"+userBean.getLoginName()+"'";
			sqlsum+=" and b.MID in (select MID from  bill_MerchantInfo where " +
					" PARENTMID= '"+userBean.getLoginName()+"' or MID ='"+userBean.getLoginName()+"') ";
		}else{
			//为总公司
		if("110000".equals(userBean.getUnNo())){
		 }
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			sqlsum+=" and b.unno in ("+childUnno+") ";
		}
		}
//		if("0".equals(userBean.getIsM35Type())){
//			sqlsum +=" and b.isM35 !=1 ";
//		}else if("1".equals(userBean.getIsM35Type())){
//			sqlsum +=" and b.isM35 = 1 ";
//		}
		if((null == toolDealBean.getTxnDay()&& null == toolDealBean.getTxnDay1())||toolDealBean.getTxnDay().equals("")&&toolDealBean.getTxnDay1().equals("")){
			return null;
		}
		if (null != toolDealBean.getMid() && !toolDealBean.getMid().equals("")) {
			sqlsum += " and u.MID = '"+toolDealBean.getMid()+"'";
		}
		if (null != toolDealBean.getUnno() && !toolDealBean.getUnno().equals("")) {
			sqlsum += " and b.unno in ("+merchantInfoService.queryUnitUnnoUtil(toolDealBean.getUnno())+")";
		}
		if (toolDealBean.getFirsix() != null && !"".equals(toolDealBean.getFirsix())) {
			sqlsum +=" AND substr(u.CARDPAN,1,6)='"+toolDealBean.getFirsix()+"'" ;
		}
		if (toolDealBean.getLastfour() != null && !"".equals(toolDealBean.getLastfour())) {
			sqlsum +=" AND substr(u.CARDPAN,(length(u.CARDPAN)-3),length(u.CARDPAN))='"+toolDealBean.getLastfour()+"'" ;
		}	
		if (null != toolDealBean.getTxnDay()
				&& null != toolDealBean.getTxnDay1()
				&& !toolDealBean.getTxnDay().equals("")
				&& !toolDealBean.getTxnDay1().equals("")) {
		if((toolDealBean.getTxnDay().trim().length())<11)
		{
			stxnday=toolDealBean.getTxnDay().trim();	
			
		}else{
			stxnday=toolDealBean.getTxnDay();}
		if((toolDealBean.getTxnDay1().trim().length())<11)
		{
			stxnday1=toolDealBean.getTxnDay1().trim();	
		}else{stxnday1=toolDealBean.getTxnDay1();}

		sqlsum += " and u.TXNDAY between '"+stxnday.replaceAll("-", "")+"' and '"+stxnday1.replaceAll("-", "")+"'";
		}

       sqlsum+=" group by u.TID,b.rname,u.mid order by u.TID";
		
		sqlsums+="select sum(counts) countsum,to_char(sum(a.MNAMT), 'FM999,999,999,999,990.00') MNAMT,";
		sqlsums+="to_char(sum(a.MDA), 'FM999,999,999,999,990.00') MDA,to_char(sum(a.TXNAMOUNT), 'FM999,999,999,999,999.00') TXNAMOUNT";
		sqlsums+=" from ("+sqlsum+") a ";
		List<Map<String, String>> listsum = checkUnitDealDetailDao.queryObjectsBySqlList(sqlsums);
		return listsum;
	}

	//商户名称查询
	public List<Map<String, String>> selectrname(ToolDealBean toolDealBean,UserBean userBean){
		String sqlrname="select rname from bill_merchantinfo where mid='"+toolDealBean.getMid()+"'";
//		if("0".equals(userBean.getIsM35Type())){
//			sqlrname +=" and isM35 !=1 ";
//		}else if("1".equals(userBean.getIsM35Type())){
//			sqlrname +=" and isM35 = 1 ";
//		}
		List<Map<String, String>> selectrname = checkUnitDealDetailDao.queryObjectsBySqlList(sqlrname);
		return selectrname;
	}

	@Override
	public DataGridBean queryCheckUnitTxnSum(ToolDealBean toolDealBean,
			UserBean userSession) throws Exception {
		DataGridBean dgd = new DataGridBean();
		String sql ="select a.*,b.agentname uname from (select t.unno,sum(t.sumamt) sumamt ,sum(t.activemerch) activemerch ,sum(t.profit) profit,sum(t.txncount) txncount, ROUND(nvl((sum(t.profit) * 1000 / sum(t.sumamt)), 0), 2) rate " +
					//" from check_UnnoTxnSum t where t.type is null  ";
					" from check_UnnoTxnSum t where 1=1  ";
		
//		String sqlSum ="select nvl(sum(t.activemerch),0)activemerch,nvl(sum(t.sumamt),0) sumamt ,nvl(sum(txncount),0) txncount,nvl(sum(t.profit),0) profit " +
//					   "from check_UnnoTxnSum t where 1=1 ";
		Map<String ,Object> map = new HashMap<String, Object>();
		
		if(toolDealBean.getUnNo() !=null && !"".equals(toolDealBean.getUnNo())){
			sql+=" and t.unno=:UNNO ";
//			sqlCount+=" and t.unno=:UNNO ";
//			sqlSum+=" and t.unno=:UNNO ";
			map.put("UNNO", toolDealBean.getUnNo());
		}
		if(toolDealBean.getTxnDay()!=null && !"".equals(toolDealBean.getTxnDay())){
			sql +=" and t.txnday>= :TXNDAY ";
//			sqlCount +=" and t.txnday>= :TXNDAY and t.txnday<=:TXNDAY1 ";
//			sqlSum +=" and t.txnday>= :TXNDAY and t.txnday<=:TXNDAY1 ";
			map.put("TXNDAY", toolDealBean.getTxnDay().replace("-", ""));
		}
		
		if(toolDealBean.getTxnDay1()!=null && !"".equals(toolDealBean.getTxnDay1())){
			sql+=" and t.txnday<=:TXNDAY1";
			map.put("TXNDAY1", toolDealBean.getTxnDay1().replace("-", ""));
		}
		sql+=" group by unno";
		
		if(toolDealBean.getSort()!=null && !"".equals(toolDealBean.getSort()) && toolDealBean.getOrder()!=null && !"".equals(toolDealBean.getOrder())){
			sql +=" order by "+toolDealBean.getSort()+" "+toolDealBean.getOrder()+") a,bill_agentunitinfo b";
		}else{
			sql+=" order by sumamt desc) a,bill_agentunitinfo b";
		}
		sql += " where a.unno=b.unno";
		
		if(toolDealBean.getUnitName()!=null && !"".equals(toolDealBean.getUnitName())){
			sql+=" and b.agentname like :UNAME ";
//			sqlCount+=" and t.uname like :UNAME ";
//			sqlSum+=" and t.uname like :UNAME ";
			map.put("UNAME", "%"+toolDealBean.getUnitName()+"%");
		}

		String sqlCount ="select count(*) from ("+sql+")";
		String sqlSum=" select nvl(sum(activemerch),0)activemerch,nvl(sum(sumamt),0) sumamt ,nvl(sum(txncount),0) txncount,nvl(sum(profit),0) profit from("+sql+")";
		Integer counts = checkUnitDealDetailDao.querysqlCounts2(sqlCount,map);
		List<Map<String,Object>> list=checkUnitDealDetailDao.queryObjectsBySqlList2(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		List<Map<String,Object>> listSum=checkUnitDealDetailDao.queryObjectsBySqlList2(sqlSum, map, 0, 1);
		list.add(0, listSum.get(0));
		dgd.setRows(list);
		dgd.setTotal(counts);
		return dgd;
	}
	
	@Override
	public DataGridBean queryCheckUnitTxnSumByLimit(ToolDealBean toolDealBean,
			UserBean userSession) throws Exception {
		DataGridBean dgd = new DataGridBean();
		
		Map<String ,Object> map = new HashMap<String, Object>();
		
		String sql = getCheckUnitTxnSumByLimitSql(toolDealBean,userSession,map);
		

		String sqlCount ="select count(*) from ("+sql+")";
	//	String sqlSum=" select nvl(sum(activemerch),0)activemerch,nvl(sum(sumamt),0) sumamt ,nvl(sum(txncount),0) txncount from("+sql+")";
		Integer counts = checkUnitDealDetailDao.querysqlCounts2(sqlCount,map);
		List<Map<String,Object>> list=checkUnitDealDetailDao.queryObjectsBySqlList2(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		//List<Map<String,Object>> listSum=checkUnitDealDetailDao.queryObjectsBySqlList2(sqlSum, map, 0, 1);
		//list.add(0, listSum.get(0));
		dgd.setRows(list);
		dgd.setTotal(counts);
		return dgd;
	}
	
	
	/**
	 * 钱包提现记录
	 */
	public DataGridBean queryCash(ToolDealBean toolDealBean,UserBean userBean) throws Exception{

		DataGridBean dgd = new DataGridBean();
		String sql ="select  t.mid,t.CASHDAY,t.CASHAMT,t.CASHFEE,t.CASHSTATUS,t.CASHDATE from CHECK_CASH t where 1=1  ";
		String sqlCount="select  count(*) from CHECK_CASH t where 1=1 ";
		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		 
		if(userBean.getUseLvl()==3){
			//and b.MID ='"+userBean.getLoginName()+"'
			sql+=" and t.mid= '"+userBean.getLoginName()+"') ";
			sqlCount +="and t.mid= '"+userBean.getLoginName()+"') ";;
		}else{
		    if("110000".equals(userBean.getUnNo())){
		   //		sqlCount ="select count(*) from ("+sql;
		    	sql+="";
		    }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			    UnitModel parent = unitModel.getParent();
			    if("110000".equals(parent.getUnNo())){
			    sql+="";
			  }
		   }else{
				String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
				   sql+=" and t.mid in(select mid from bill_merchantInfo b where  b.unno IN ("+childUnno+")) ";
				   sqlCount+="  and t.mid in(select mid from bill_merchantInfo b where  b.unno IN ("+childUnno+")) ";
			}
		}
		Map<String ,Object> map = new HashMap<String, Object>();
		if(toolDealBean!=null && toolDealBean.getTxnDay()!=null&&toolDealBean.getTxnDay1()!=null && !"".equals(toolDealBean.getTxnDay()) && !"".equals(toolDealBean.getTxnDay1())){
			sql +=" and  t.CASHDAY between '"+toolDealBean.getTxnDay().replace("-", "")+"' and  '"+toolDealBean.getTxnDay1().replace("-", "")+"'";
			sqlCount +=" and  t.CASHDAY between '"+toolDealBean.getTxnDay().replace("-", "")+"' and  '"+toolDealBean.getTxnDay1().replace("-", "")+"'";
		}else{
			sql +=" and t.CASHDAY= to_char(sysdate-1,'yyyyMMdd')";
			sqlCount +=" and t.CASHDAY=to_char(sysdate-1,'yyyyMMdd') ";
		}
		if(toolDealBean.getMid()!=null && !"".equals(toolDealBean.getMid())){
			sql+=" and t.mid = :MID ";
			sqlCount+=" and t.mid = :MID ";
			map.put("MID", toolDealBean.getMid());
		}

		Integer counts = checkUnitDealDetailDao.querysqlCounts2(sqlCount,map);
		List<Map<String,Object>> list=checkUnitDealDetailDao.queryObjectsBySqlList2(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		dgd.setRows(list);
		dgd.setTotal(counts);
		return dgd;
	}

	/**
	 * 导出钱包提现记录
	 */
	@Override
	public List<Map<String, String>> exportWalletCash(
			ToolDealBean toolDealBean, UserBean userBean) {
		
		String sql ="select  t.mid,t.CASHDAY,t.CASHAMT,t.CASHFEE,t.CASHSTATUS,t.CASHDATE from CHECK_CASH t where 1=1  ";
		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		 
		if(userBean.getUseLvl()==3){
			sql+=" and t.mid= '"+userBean.getLoginName()+"') ";
		}else{
		    if("110000".equals(userBean.getUnNo())){
		    	sql+="";
		    }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			    UnitModel parent = unitModel.getParent();
			    if("110000".equals(parent.getUnNo())){
			    sql+="";
			  }
		   }else{
				String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
				sql+=" and t.mid in(select mid from bill_merchantInfo b where  b.unno IN ("+childUnno+")) ";
			}
		}
		if(toolDealBean!=null && toolDealBean.getTxnDay()!=null&&toolDealBean.getTxnDay1()!=null && !"".equals(toolDealBean.getTxnDay()) && !"".equals(toolDealBean.getTxnDay1())){
			sql +=" and  t.CASHDAY between '"+toolDealBean.getTxnDay().replace("-", "")+"' and  '"+toolDealBean.getTxnDay1().replace("-", "")+"'";
		}else{
			sql +=" and t.CASHDAY= to_char(sysdate-1,'yyyyMMdd')";
		}
		if(toolDealBean.getMid()!=null && !"".equals(toolDealBean.getMid())){
			sql+=" and t.mid = '"+toolDealBean.getMid()+"' ";
		}
		List<Map<String, String>> list=checkUnitDealDetailDao.executeSql(sql);		
		return list;		
	
	}

	@Override
	public HSSFWorkbook exportCheckUnitTxnSumData(ToolDealBean toolDealBean) {
		String sql ="select "+
				" t.unno,a.agentname uname,t.txnday,t.sumamt,t.activemerch,t.profit,t.txncount,t.type,t.maintaindate,"+
				" ROUND(nvl((t.profit/t.sumamt),0),5) rate from check_UnnoTxnSum t,bill_agentunitinfo a where 1 = 1 and t.unno = a.unno ";
		Map<String ,Object> map = new HashMap<String, Object>();
		if(toolDealBean.getUnitName()!=null && !"".equals(toolDealBean.getUnitName())){
			sql+=" and a.agentname like :UNAME ";
			map.put("UNAME", "%"+toolDealBean.getUnitName()+"%");
		}
		if(toolDealBean.getUnNo() !=null && !"".equals(toolDealBean.getUnNo())){
			sql+=" and t.unno=:UNNO ";
			map.put("UNNO", toolDealBean.getUnNo());
		}

		if(toolDealBean.getTxnDay()!=null && !"".equals(toolDealBean.getTxnDay())){
			sql +=" and t.txnday>= :TXNDAY ";
			map.put("TXNDAY", toolDealBean.getTxnDay().replace("-", ""));
		}
		
		if(toolDealBean.getTxnDay1()!=null && !"".equals(toolDealBean.getTxnDay1())){
			sql+=" and t.txnday<=:TXNDAY1";
			map.put("TXNDAY1", toolDealBean.getTxnDay1().replace("-", ""));
		}
		
		
		if(toolDealBean.getSort()!=null && !"".equals(toolDealBean.getSort()) && toolDealBean.getOrder()!=null && !"".equals(toolDealBean.getOrder())){
			sql +=" order by "+toolDealBean.getSort()+" "+toolDealBean.getOrder();
		}else{
			sql+=" order by sumamt desc";
		}
		List<Map<String,Object>> list=checkUnitDealDetailDao.queryObjectsBySqlListMap2(sql, map);
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNNO");
		excelHeader.add("UNAME");
		excelHeader.add("TXNDAY");
		excelHeader.add("SUMAMT");
		excelHeader.add("ACTIVEMERCH");
		excelHeader.add("TXNCOUNT");
		excelHeader.add("PROFIT");
		excelHeader.add("RATE");
		
		
		headMap.put("UNNO", "机构编号");
		headMap.put("UNAME", "机构名称");
		headMap.put("TXNDAY", "交易日期");
		headMap.put("SUMAMT", "交易金额");
		headMap.put("ACTIVEMERCH", "活跃商户数");
		headMap.put("TXNCOUNT", "交易笔数");
		headMap.put("PROFIT", "利润");
		headMap.put("RATE", "收益率");

		return ExcelUtil.export("机构交易汇总", list, excelHeader, headMap);
	}

	@Override
	public DataGridBean queryFirstAgentProfitCollect(ToolDealBean toolDealBean, UserBean userBean) {
		DataGridBean dataGridBean = new DataGridBean();
		String sql  =  "select to_char(sum(TXNAMOUNT),'FM999,999,999,999,990.00') as mda,count (txnamount) as count,to_char(sum(profit),'FM999,999,999,999,990.00') as profit, "+
		        	" unno ,unname,settmethod,bankfeerate from( select decode(a.cbrand,'1', case  when a.txnamount>=nvl(t.dealamt,4146) then decode(t.ism35,'1','理财借记卡大额','传统借记卡大额')"+
                    " else decode(t.ism35,'1','理财借记卡小额','传统借记卡小额')end, decode(t.ism35,'1','理财贷记卡','传统贷记卡')) as settmethod, "+
		        	" a.profit, a.mda mda,s.unno, t.rname, a.txnamount txnamount, s.un_name as unname,t.bankfeerate "+
					" from  bill_merchantinfo t,check_unitdealdetail a,sys_unit s where t.mid = a.mid  and s.unno =  nvl((select s.unno  from sys_unit s  where s.un_lvl=2 " +
					" start with s.unno = t.UNNO  connect  by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),t.unno) and (t.settmethod ='000000' or t.settmethod is null) ";
        UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if("110000".equals(userBean.getUnNo())){
		 }
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			sql+=" and t.unno IN ("+childUnno+") ";
		}
		String txnday = toolDealBean.getTxnDay();
		String txnday1 = toolDealBean.getTxnDay1();
		if(txnday==null&&txnday1==null||txnday.equals("")&&txnday1.equals("")){
       	 	return null; 
        }
        else{	
        	sql +=" and a.txnday between to_char(to_date( '"+ toolDealBean.getTxnDay()+"','yyyy/MM/dd'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+"','yyyy/MM/dd'),'yyyyMMdd') ";
        }
		sql+=") group by settmethod, unname, unno,bankfeerate ";
//		if (toolDealBean.getUnitName() != null && !"".equals(toolDealBean.getUnitName())) {
//			sql += " and s.un_name like '%" +toolDealBean.getUnitName()+"%'";
//		}
//		Integer isM35 = toolDealBean.getIsM35();
//		if (isM35 != null && !"".equals(isM35)) {
//			if (0==isM35) { //0.72秒到
//				sql += " and t.ism35 = '1' and t.settmethod='100000' and t.BANKFEERATE = 0.0072 ";
//			}else if (1==isM35) { //非0.72秒到
//				sql += " and t.ism35 = '1' and t.settmethod='100000'  and t.BANKFEERATE < 0.0072 ";
//			}else if (2==isM35) { //理财
//				sql += "  and t.ism35 = 1 and t.settmethod='000000'  ";
//			}else if (3==isM35) { //传统
//				sql += " and t.ism35 != '1' ";
//			}else if (4==isM35) {//云闪付
//				sql += " and a.ifcard = '3' ";
//			}
//		}
		
		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS ,to_char(nvl(sum(replace(mda,',','')),0),'FM999,999,999,999,990.00') as mda ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") b";
		
		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, null, toolDealBean.getPage(),toolDealBean.getRows());
		Map<String,String> mapS=new HashMap<String, String>();
		Integer count=0;
		Map m=(Map)porListSum.get(0);
		count=Integer.parseInt(m.get("COUNT").toString());
		mapS.put("MDA", m.get("MDA").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("COUNT", m.get("COUNTS").toString());
		proList.add(0, mapS);
		dataGridBean.setTotal(count);
		dataGridBean.setRows(proList);
		porListSum=null;
		proList=null;
		return dataGridBean;
	}
	public String getFirstAgentProfitCollect_20303Sql(ToolDealBean toolDealBean, UserBean userBean){
		String sql = " select to_char(sum(t.txnamount), 'FM999,999,999,999,990.00') as mda,sum(t.txncount) as count," +
				" to_char(sum(t.mda), 'FM999,999,999,999,990.00') as charge, " +
				" to_char(sum(t.profit), 'FM999,999,999,999,990.00') as profit,t.unno,k.un_name unname, " +
				" decode(t.mertype,4,'云闪付',(case when t.mertype=1 then '0.72秒到' else '非0.72秒到' end)) settmethod " +
				"  from pg_unno_txn t,sys_unit k where k.unno=t.unno and k.un_lvl in (1,2) and t.isnew = 0 and t.mertype in (1, 2, 4) ";
//				" and t.txnday>='20190501' and t.txnday<='20190505' " +
//				"   and t.unno='412016' " +
//				"   group by t.unno,t.mertype";

		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if("110000".equals(userBean.getUnNo())){
		}
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
//			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
            String childUnno = "select p.unno from sys_unit p where p.un_lvl in (1,2) start with p.unno='" + unitModel.getUnNo() + "' connect by prior p.unno=p.upper_unit ";
            sql += " and t.unno IN (" + childUnno + ") ";
		}
		String txnday = toolDealBean.getTxnDay();
		String txnday1 = toolDealBean.getTxnDay1();
		if(txnday==null&&txnday1==null||txnday.equals("")&&txnday1.equals("")){
			return null;
		}else{
			sql +=" and t.txnday >='"+ toolDealBean.getTxnDay().replace("-","")+"' and t.txnday <= '"+ toolDealBean.getTxnDay1().replace("-","")+"' ";
		}
		sql+=" group by t.unno,k.un_name,t.mertype";
		return sql;
	}
	@Override
	public DataGridBean queryFirstAgentProfitCollect_20303(ToolDealBean toolDealBean, UserBean userBean) {
		DataGridBean dataGridBean = new DataGridBean();
		// @author:lxg-20190520 代理商分润-刷卡分润汇总修改新表
		String sql=getFirstAgentProfitCollect_20303Sql(toolDealBean,userBean);
		if(StringUtils.isEmpty(sql)){
			return dataGridBean;
		}
//		String sql  =  "select to_char(sum(TXNAMOUNT),'FM999,999,999,999,990.00') as mda,count (txnamount) as count,to_char(sum(profit),'FM999,999,999,999,990.00') as profit, "+
//		        	" unno ,unname,settmethod,bankfeerate from( select decode(a.ifcard,'3','云闪付',(case when t.BANKFEERATE != 0.0072 then '非0.72秒到' else '0.72秒到' end)) as settmethod, "+
//		        	" a.profit, a.mda mda,s.unno, t.rname, a.txnamount txnamount, s.un_name as unname,t.bankfeerate "+
//					" from  bill_merchantinfo t,check_unitdealdetail a,sys_unit s where t.mid = a.mid  and s.unno =  nvl((select s.unno  from sys_unit s  where s.un_lvl=2 " +
//					" start with s.unno = t.UNNO  connect  by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),t.unno) and t.settmethod ='100000' ";
//        UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
//		//为总公司
//		if("110000".equals(userBean.getUnNo())){
//		 }
//		//为总公司并且是部门---查询全部
//		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
//			UnitModel parent = unitModel.getParent();
//			if("110000".equals(parent.getUnNo())){
//			}
//		}else{
//			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
//			sql+=" and t.unno IN ("+childUnno+") ";
//		}
//		String txnday = toolDealBean.getTxnDay();
//		String txnday1 = toolDealBean.getTxnDay1();
//		if(txnday==null&&txnday1==null||txnday.equals("")&&txnday1.equals("")){
//       	 	return null;
//        }
//        else{
//        	sql +=" and a.txnday between to_char(to_date( '"+ toolDealBean.getTxnDay()+"','yyyy/MM/dd'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+"','yyyy/MM/dd'),'yyyyMMdd') ";
//        }
//		sql+=") group by settmethod, unname, unno,bankfeerate ";
////		if (toolDealBean.getUnitName() != null && !"".equals(toolDealBean.getUnitName())) {
////			sql += " and s.un_name like '%" +toolDealBean.getUnitName()+"%'";
////		}
////		Integer isM35 = toolDealBean.getIsM35();
////		if (isM35 != null && !"".equals(isM35)) {
////			if (0==isM35) { //0.72秒到
////				sql += " and t.ism35 = '1' and t.settmethod='100000' and t.BANKFEERATE = 0.0072 ";
////			}else if (1==isM35) { //非0.72秒到
////				sql += " and t.ism35 = '1' and t.settmethod='100000'  and t.BANKFEERATE < 0.0072 ";
////			}else if (2==isM35) { //理财
////				sql += "  and t.ism35 = 1 and t.settmethod='000000'  ";
////			}else if (3==isM35) { //传统
////				sql += " and t.ism35 != '1' ";
////			}else if (4==isM35) {//云闪付
////				sql += " and a.ifcard = '3' ";
////			}
////		}

		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS ," +
				"to_char(nvl(sum(replace(charge,',','')),0),'FM999,999,999,999,990.00') as charge ,to_char(nvl(sum(replace(mda,',','')),0),'FM999,999,999,999,990.00') as mda ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") b";

		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, null, toolDealBean.getPage(),toolDealBean.getRows());
		Map<String,String> mapS=new HashMap<String, String>();
		Integer count=0;
		Map m=(Map)porListSum.get(0);
		count=Integer.parseInt(m.get("COUNT").toString());
		mapS.put("MDA", m.get("MDA").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("COUNT", m.get("COUNTS").toString());
		mapS.put("CHARGE", m.get("CHARGE").toString());
		proList.add(0, mapS);
		dataGridBean.setTotal(count);
		dataGridBean.setRows(proList);
		return dataGridBean;
	}
	
	@Override
	public List<Map<String, String>> exportFirstAgentProfitCollect(ToolDealBean toolDealBean, UserBean userBean) {
		
		String sql  =  "select to_char(sum(TXNAMOUNT),'FM999,999,999,999,990.00') as mda,count (txnamount) as count,to_char(sum(profit),'FM999,999,999,999,990.00') as profit, "+
		" unno ,unname,settmethod,bankfeerate from( select decode(a.cbrand,'1', case  when a.txnamount>=nvl(t.dealamt,4146) then decode(t.ism35,'1','理财借记卡大额','传统借记卡大额')"+
        " else decode(t.ism35,'1','理财借记卡小额','传统借记卡小额')end, decode(t.ism35,'1','理财贷记卡','传统贷记卡')) as settmethod, "+
		" a.profit, a.mda mda,s.unno, t.rname, a.txnamount txnamount, s.un_name as unname,t.bankfeerate "+
		" from bill_merchantinfo t, check_unitdealdetail a,sys_unit s where t.mid = a.mid  and s.unno =  nvl((select s.unno  from sys_unit s  " +
		"where s.un_lvl=2  start with s.unno = t.UNNO  connect  by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),t.unno) and (t.settmethod ='000000' or t.settmethod is null) ";
		
		//	if (toolDealBean.getUnitName() != null && !"".equals(toolDealBean.getUnitName())) {
		//		sql += " and s.un_name like '%" +toolDealBean.getUnitName()+"%'";
		//	}
		//	Integer isM35 = toolDealBean.getIsM35();
		//	if (isM35 != null && !"".equals(isM35)) {
		//		if (0==isM35) { //0.72秒到
		//			sql += " and t.ism35 = '1' and t.settmethod='100000' and t.BANKFEERATE = 0.0072 ";
		//		}else if (1==isM35) { //非0.72秒到
		//			sql += " and t.ism35 = '1' and t.settmethod='100000'  and t.BANKFEERATE < 0.0072 ";
		//		}else if (2==isM35) { //理财
		//			sql += "  and t.ism35 = 1 and t.settmethod='000000'  ";
		//		}else if (3==isM35) { //传统
		//			sql += " and t.ism35 != '1' ";
		//		}else if (4==isM35) {//云闪付
		//			sql += " and a.ifcard = '3' ";
		//		}
		//	}
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if("110000".equals(userBean.getUnNo())){
		}
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			sql+=" and t.unno IN ("+childUnno+") ";
		}
		String txnday = toolDealBean.getTxnDay();
		String txnday1 = toolDealBean.getTxnDay1();
		if(txnday==null&&txnday1==null||txnday.equals("")&&txnday1.equals("")){
			return null; 
		}
		else{	
			sql +=" and a.txnday between to_char(to_date( '"+ toolDealBean.getTxnDay()+"','yyyy/MM/dd'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+"','yyyy/MM/dd'),'yyyyMMdd') ";
		}
		sql += " ) group by settmethod, unname, unno,bankfeerate ";
		String sqlCount = "select nvl(sum(count),0)  COUNTS ,to_char(nvl(sum(replace(mda,',','')),0),'FM999,999,999,999,990.00') as mda ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") b";
		//List proList = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, null, toolDealBean.getPage(),toolDealBean.getRows());
		
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList(sql);
		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		Map<String,String> mapS=new HashMap<String, String>();
//		Integer count=0;
		Map m=(Map)porListSum.get(0);
		mapS.put("UNNO", "汇总");
		mapS.put("MDA", m.get("MDA").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("COUNT", m.get("COUNTS").toString());
		proList.add( mapS);
		return proList;
	}
	@Override
	public List<Map<String, String>> exportFirstAgentProfitCollect_20303(ToolDealBean toolDealBean, UserBean userBean) {
        // @author:lxg-20190520 代理商分润-刷卡分润汇总修改新表
		String sql=getFirstAgentProfitCollect_20303Sql(toolDealBean,userBean);
		if(StringUtils.isEmpty(sql)){
			return null;
		}
//		String sql  =  "select to_char(sum(TXNAMOUNT),'FM999,999,999,999,990.00') as mda,count (txnamount) as count,to_char(sum(profit),'FM999,999,999,999,990.00') as profit, "+
//		" unno ,unname,settmethod,bankfeerate from( select decode(a.ifcard,'3','云闪付',(case  when t.BANKFEERATE != 0.0072 then '非0.72秒到' else '0.72秒到' end)) as settmethod, "+
//		" a.profit, a.mda mda,s.unno, t.rname, a.txnamount txnamount, s.un_name as unname,t.bankfeerate "+
//		" from bill_merchantinfo t, check_unitdealdetail a,sys_unit s where t.mid = a.mid  and s.unno =  nvl((select s.unno  from sys_unit s  " +
//		"where s.un_lvl=2  start with s.unno = t.UNNO  connect  by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),t.unno) and t.settmethod ='100000'  ";
//
//		//	if (toolDealBean.getUnitName() != null && !"".equals(toolDealBean.getUnitName())) {
//		//		sql += " and s.un_name like '%" +toolDealBean.getUnitName()+"%'";
//		//	}
//		//	Integer isM35 = toolDealBean.getIsM35();
//		//	if (isM35 != null && !"".equals(isM35)) {
//		//		if (0==isM35) { //0.72秒到
//		//			sql += " and t.ism35 = '1' and t.settmethod='100000' and t.BANKFEERATE = 0.0072 ";
//		//		}else if (1==isM35) { //非0.72秒到
//		//			sql += " and t.ism35 = '1' and t.settmethod='100000'  and t.BANKFEERATE < 0.0072 ";
//		//		}else if (2==isM35) { //理财
//		//			sql += "  and t.ism35 = 1 and t.settmethod='000000'  ";
//		//		}else if (3==isM35) { //传统
//		//			sql += " and t.ism35 != '1' ";
//		//		}else if (4==isM35) {//云闪付
//		//			sql += " and a.ifcard = '3' ";
//		//		}
//		//	}
//		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
//		//为总公司
//		if("110000".equals(userBean.getUnNo())){
//		}
//		//为总公司并且是部门---查询全部
//		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
//			UnitModel parent = unitModel.getParent();
//			if("110000".equals(parent.getUnNo())){
//			}
//		}else{
//			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
//			sql+=" and t.unno IN ("+childUnno+") ";
//		}
//		String txnday = toolDealBean.getTxnDay();
//		String txnday1 = toolDealBean.getTxnDay1();
//		if(txnday==null&&txnday1==null||txnday.equals("")&&txnday1.equals("")){
//			return null;
//		}
//		else{
//			sql +=" and a.txnday between to_char(to_date( '"+ toolDealBean.getTxnDay()+"','yyyy/MM/dd'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+"','yyyy/MM/dd'),'yyyyMMdd') ";
//		}
//		sql += " ) group by settmethod, unname, unno,bankfeerate ";
		String sqlCount = "select nvl(sum(count),0)  COUNTS ,to_char(nvl(sum(replace(charge,',','')),0),'FM999,999,999,999,990.00') as charge," +
				" to_char(nvl(sum(replace(mda,',','')),0),'FM999,999,999,999,990.00') as mda ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") b";
		//List proList = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, null, toolDealBean.getPage(),toolDealBean.getRows());
		
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList(sql);
		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		Map<String,String> mapS=new HashMap<String, String>();
//		Integer count=0;
		Map m=(Map)porListSum.get(0);
		mapS.put("UNNO", "汇总");
		mapS.put("MDA", m.get("MDA").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("COUNT", m.get("COUNTS").toString());
		mapS.put("CHARGE", m.get("CHARGE").toString());
		proList.add( mapS);
		return proList;
	}
	
	@Override
	public DataGridBean queryFirstAgentProfitScan(ToolDealBean toolDealBean, UserBean userBean) {
        // @author:lxg-20190520 代理商分润-无卡交易分润汇总修改新表
		DataGridBean dataGridBean = new DataGridBean();
		String sql = getQueryFirstAgentProfitScanSql(toolDealBean,userBean);
		if(StringUtils.isEmpty(sql)){
			return dataGridBean;
		}
		// @author:xuegangliu-20190325 剔除收银台数据
//		String sql = "select to_char(sum(nvl(PROFIT, 0)),'FM999,999,999,999,990.00')  profit ,nvl(sum(mda), 0) mda , "+
//                     " to_char(sum(nvl(txnamount, 0)),'FM999,999,999,999,990.00') txnamount,count(mda) txncount ,unno, un_name,SETTMETHOD "+
//                     "from(select a1.profit,t1.mid, a1.mda mda, s.unno, s.un_name, a1.txnamt txnamount, decode(t1.ism35,'1','手刷', '传统') as settmethod  "+
//                     "from ( select * from bill_merchantinfo t where 1=1 and t.mid not like 'HRTSYT%' " ;
//        UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
//		//为总公司并且是部门---查询全部
//		if("110000".equals(userBean.getUnNo())){
//		 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
//			UnitModel parent = unitModel.getParent();
//			if("110000".equals(parent.getUnNo())){
//			}
//		}else{
//			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
//			sql+=" and t.unno IN ("+childUnno+") ";
//		}
//        sql+="and t.maintaintype != 'D'  and t.approvestatus = 'Y' )t1,(select * from check_wechattxndetail a where " ;
//        if(toolDealBean.getTxnDay()==null||toolDealBean.getTxnDay1()==null||toolDealBean.getTxnDay().equals("")||toolDealBean.getTxnDay1().equals("")){
//       	 	return null;
//        }else{	//交易日期
//        	sql +=" a.cdate between to_date( '"+ toolDealBean.getTxnDay()+" 00:00:00','yyyy-MM-dd hh24:mi:ss') and to_date( '"+ toolDealBean.getTxnDay1()+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
//        }
//       sql+=")a1, sys_unit s where t1.mid = a1.mid  and s.unno= nvl((select s.unno  from sys_unit s  where s.un_lvl=2  start with s.unno = t1.UNNO  connect  by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),t1.unno) ";
//
////		if (toolDealBean.getUnitName() != null && !"".equals(toolDealBean.getUnitName())) { // 机构名称
////			sql += " and s.un_name like '%"+ toolDealBean.getUnitName() +"%'";
////		}
//
//		sql += ") group by unno, un_name,settmethod ";
		
		String sqlCount = "select nvl(count(*),0) count, nvl(sum(txncount),0) COUNTS, to_char(nvl(sum(replace(txnamount,',','')),0),'FM999,999,999,999,990.00') as txnamount ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") ";
		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, null, toolDealBean.getPage(),toolDealBean.getRows());
		Map<String,String> mapS=new HashMap<String, String>();
		Integer count=0;
		Map m=(Map)porListSum.get(0);
		count=Integer.parseInt(m.get("COUNT").toString());
		mapS.put("TXNAMOUNT", m.get("TXNAMOUNT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("TXNCOUNT", m.get("COUNTS").toString());
		proList.add(0, mapS);
		dataGridBean.setTotal(count);
		dataGridBean.setRows(proList);
		return dataGridBean;
		
	}

	private String getQueryFirstAgentProfitScanSql(ToolDealBean toolDealBean, UserBean userBean){
		String sql = "select to_char(sum(nvl(t.profit, 0)), 'FM999,999,999,999,990.00') profit, " +
				"       nvl(sum(t.mda), 0) mda, " +
				"       to_char(sum(nvl(t.txnamount, 0)), 'FM999,999,999,999,990.00') txnamount, " +
				"       sum(t.txncount) txncount,t.unno,k.un_name, " +
				"       decode(t.ismpos, '0', '传统', '手刷') settmethod " +
				"  from pg_unno_wechat t, sys_unit k " +
				" where 1=1 and k.unno=t.unno and k.un_lvl in (1,2)  ";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司并且是部门---查询全部
		if("110000".equals(userBean.getUnNo())){
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
//			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
//			sql+=" and t.unno IN ("+childUnno+") ";
            String childUnno = "select p.unno from sys_unit p where p.un_lvl in (1,2) start with p.unno='" + unitModel.getUnNo() + "' connect by prior p.unno=p.upper_unit ";
            sql += " and t.unno IN (" + childUnno + ") ";
		}
		if(toolDealBean.getTxnDay()==null||toolDealBean.getTxnDay1()==null||toolDealBean.getTxnDay().equals("")||toolDealBean.getTxnDay1().equals("")){
			return null;
		}else{	//交易日期
			sql += " and t.txnday >= '"+toolDealBean.getTxnDay().replace("-","")+"' and t.txnday <= '"+toolDealBean.getTxnDay1().replace("-","")+"'";
		}
		sql += " and t.ismpos in (0,1,2) group by t.unno,k.un_name, decode(t.ismpos, '0', '传统', '手刷') ";
		return sql;
	}

	/**
	 * 一级分润-收银台分润汇总
	 * @param toolDealBean
	 * @param user
	 * @return
	 */
	@Override
	public DataGridBean queryBigScanProfit(ToolDealBean toolDealBean, UserBean user) {
	    // @author:xuegangliu 一级代理分润下的分润汇总,按当前用户的机构展示一代机构的数据(一代包含下边的子机构数据)
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
		String sql = " select b.*,case  when b.mertype = 1 and b.minfo2 = 1 then '微信0.38'"
			+" when b.mertype = 1 and b.minfo2 = 2 then '微信0.45'"
			+" when b.mertype = 1 and b.minfo2 = 3 then '微信（老）'"
			+" when b.mertype = 2 then '支付宝'"
			+" when b.mertype = 3 then '银联二维码'"
			+" when b.mertype = 5 and b.minfo2 = 1 then '扫码1000以上（终端0.38）'"
			+" when b.mertype = 5 and b.minfo2 = 2 then '扫码1000以上（终端0.45）'"
			+" when b.mertype = 5 and b.minfo2 = 3 then '扫码1000以下（终端0.38）'"
			+" when b.mertype = 5 and b.minfo2 = 4 then '扫码1000以下（终端0.45）'"
			+" when b.mertype = 6 then '花呗交易'"
			+" else '其他' end as SETTMETHOD from ("
			+"select a.unno,a.mertype,a.minfo2,a.minfo1 rebatetype,(select un_name from sys_unit where unno = a.unno) unname,sum(a.txnamount) txnamount,"
			+ "sum(a.TXNCOUNT) txncount,sum(a.profit) profit from pg_unno_wechat a "
			+ " where a.txnday >=:txnday and a.txnday <=:txnday1 and a.ismpos=3 and a.mertype!=4 ";
		map.put("txnday", toolDealBean.getTxnDay());
		map.put("txnday1", toolDealBean.getTxnDay1());
		if("110000".equals(user.getUnNo())) {//总公司权限
		}else if(user.getUseLvl()==0) {//代理商管理员
			sql += " and a.unno=:unno1 ";
			map.put("unno1", user.getUnNo());
		}
		//活动类型
		if(toolDealBean.getMaintainType() != null && !"".equals(toolDealBean.getMaintainType())) {
			sql += " and a.minfo1 = "+toolDealBean.getMaintainType();
		}
		sql += " group by a.unno,a.mertype,a.minfo2,a.minfo1) b ";
		String sqlCount = "select count(1) from ("+sql+")";
		sql += " order by unno desc ";
		List<Map<String,Object>> list = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		BigDecimal counts = checkUnitDealDetailDao.querysqlCounts(sqlCount, map);
		String sql2 = "select nvl(sum(txncount),0) COUNTS, nvl(sum(txnamount),0) txnamount ,nvl(sum(PROFIT),0) profit from ("+sql+") ";
		List porListSum = checkUnitDealDetailDao.executeSql2(sql2,map);
		Map<String,Object> mapS=new HashMap<String, Object>();
		Map m=(Map)porListSum.get(0);
		mapS.put("UNNO", "汇总");
		mapS.put("TXNAMOUNT", m.get("TXNAMOUNT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("TXNCOUNT", m.get("COUNTS").toString());
		list.add(0, mapS);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}
	@Override
	public List<Map<String, String>> exportFirstAgentProfitScan(ToolDealBean toolDealBean, UserBean userBean) {
        // @author:lxg-20190520 代理商分润-无卡交易分润汇总修改新表
		String sql = getQueryFirstAgentProfitScanSql(toolDealBean,userBean);
		if(StringUtils.isEmpty(sql)){
			return null;
		}
//		String sql = "select to_char(sum(nvl(PROFIT, 0)),'FM999,999,999,999,990.00')  profit ,nvl(sum(mda), 0) mda , "+
//		        " to_char(sum(nvl(txnamount, 0)),'FM999,999,999,999,990.00') txnamount,count(mda) txncount ,unno, un_name,SETTMETHOD "+
//		        "from(select a1.profit,t1.mid, a1.mda mda, s.unno, s.un_name, a1.txnamt txnamount, decode(t1.ism35,'1','手刷', '传统') as settmethod  "+
//		        "from ( select * from bill_merchantinfo t where 1=1 " ;
//		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
//		//为总公司并且是部门---查询全部
//		if("110000".equals(userBean.getUnNo())){
//		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
//		UnitModel parent = unitModel.getParent();
//		if("110000".equals(parent.getUnNo())){
//		}
//		}else{
//		String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
//		sql+=" and t.unno IN ("+childUnno+") ";
//		}
//		sql+="and t.maintaintype != 'D'  and t.approvestatus = 'Y' )t1,(select * from check_wechattxndetail a where " ;
//		if(toolDealBean.getTxnDay()==null||toolDealBean.getTxnDay1()==null||toolDealBean.getTxnDay().equals("")||toolDealBean.getTxnDay1().equals("")){
//			return null;
//		}else{	//交易日期
//		sql +=" a.cdate between to_date( '"+ toolDealBean.getTxnDay()+" 00:00:00','yyyy-MM-dd hh24:mi:ss') and to_date( '"+ toolDealBean.getTxnDay1()+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
//		}
//		sql+=")a1, sys_unit s where t1.mid = a1.mid  and s.unno= nvl((select s.unno  from sys_unit s  where s.un_lvl=2  start with s.unno = t1.UNNO  connect  by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),t1.unno) ";
//
//		//if (toolDealBean.getUnitName() != null && !"".equals(toolDealBean.getUnitName())) { // 机构名称
//		//sql += " and s.un_name like '%"+ toolDealBean.getUnitName() +"%'";
//		//}
//
//		sql += ") group by unno, un_name,settmethod ";
		String sqlCount = "select nvl(count(*),0) count, nvl(sum(txncount),0) COUNTS, to_char(nvl(sum(replace(txnamount,',','')),0),'FM999,999,999,999,990.00') as txnamount ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") ";
		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList(sql);
		Map<String,String> mapS=new HashMap<String, String>();
		Map m=(Map)porListSum.get(0);
		mapS.put("UNNO", "汇总");
		mapS.put("TXNAMOUNT", m.get("TXNAMOUNT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("TXNCOUNT", m.get("COUNTS").toString());
		proList.add(0, mapS);
		return proList;
	}
	
	@Override
	public List<Map<String, String>> exportBigScanProfit(ToolDealBean toolDealBean, UserBean user) {
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
		String sql = " select b.*,case"
				+" when b.mertype = 1 and b.minfo2 = 2 then '微信0.45'"
				+" when b.mertype = 1 and b.minfo2 = 3 then '微信（老）'"
				+" when b.mertype = 2 then '支付宝'"
				+" when b.mertype = 3 then '银联二维码'"
				+" when b.mertype = 5 and b.minfo2 = 1 then '扫码1000以上（终端0.38）'"
				+" when b.mertype = 5 and b.minfo2 = 2 then '扫码1000以上（终端0.45）'"
				+" when b.mertype = 5 and b.minfo2 = 3 then '扫码1000以下（终端0.38）'"
				+" when b.mertype = 5 and b.minfo2 = 4 then '扫码1000以下（终端0.45）'"
				+" when b.mertype = 6 then '花呗交易'"
				+" else '其他' end as SETTMETHOD from ("
				+"select a.unno,a.mertype,a.minfo2,a.minfo1 rebatetype,(select un_name from sys_unit where unno = a.unno) unname,sum(a.txnamount) txnamount,"
				+ "sum(a.TXNCOUNT) txncount,sum(a.profit) profit from pg_unno_wechat a "
				+ " where a.txnday >=:txnday and a.txnday <=:txnday1 and a.ismpos=3 and a.mertype!=4 ";
		map.put("txnday", toolDealBean.getTxnDay());
		map.put("txnday1", toolDealBean.getTxnDay1());
		if("110000".equals(user.getUnNo())) {//总公司权限
		}else if(user.getUseLvl()==0) {//代理商管理员
			sql += " and a.unno=:unno1 ";
			map.put("unno1", user.getUnNo());
		}
		//活动类型
		if(StringUtils.isNotEmpty(toolDealBean.getMaintainType())){
			sql += " and minfo1= '"+toolDealBean.getMaintainType()+"' ";
		}
		sql += " group by a.unno,a.mertype,a.minfo2,a.minfo1 )b";
		sql += " order by unno desc ";
		List<Map<String,String>> list = checkUnitDealDetailDao.queryObjectsBySqlListMap(sql,map);
//				.queryObjectsBySqlList(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		String sqlCount = "select nvl(sum(txncount),0) COUNTS, nvl(sum(txnamount),0) txnamount ,nvl(sum(PROFIT),0) profit from ("+sql+") ";
		List porListSum = checkUnitDealDetailDao.executeSql2(sqlCount,map);
		Map<String,String> mapS=new HashMap<String, String>();
		Map m=(Map)porListSum.get(0);
		mapS.put("UNNO", "汇总");
		mapS.put("TXNAMOUNT", m.get("TXNAMOUNT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("TXNCOUNT", m.get("COUNTS").toString());
		list.add(0, mapS);
		return list;
	}
	
	@Override
	public List<Map<String, String>> exportFirstAgentScanProfitDetail(ToolDealBean toolDealBean, UserBean userBean) {
		String sql = "select to_char(sum(nvl(PROFIT, 0)),'FM999,999,999,999,990.00')  profit ,nvl(sum(mda), 0) mda , "+
		        " to_char(sum(nvl(txnamount, 0)),'FM999,999,999,999,990.00') txnamount,count(mda) txncount ,unno, un_name,SETTMETHOD ,mid,rname "+
		        "from(select a1.profit,t1.mid, a1.mda mda, s.unno, s.un_name,t1.rname, a1.txnamt txnamount, decode(t1.ism35,'1','手刷', '传统') as settmethod  "+
		        "from ( select * from bill_merchantinfo t where 1=1 " ;
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司并且是部门---查询全部
		if("110000".equals(userBean.getUnNo())){
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
		UnitModel parent = unitModel.getParent();
		if("110000".equals(parent.getUnNo())){
		}
		}else{
		String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
		sql+=" and t.unno IN ("+childUnno+") ";
		}
		if (toolDealBean.getMid() != null && !"".equals(toolDealBean.getMid())) {
		sql += " and t.mid = '"+toolDealBean.getMid()+"'";
		}
		if (toolDealBean.getIsM35() != null && !"".equals(toolDealBean.getIsM35())) {
		if(toolDealBean.getIsM35()==1){
			sql += " and t.isM35 = '1'";
		}else if(toolDealBean.getIsM35()==0){
			sql += " and t.isM35 != '1'";
		}
		}
		sql+="and t.maintaintype != 'D'  and t.approvestatus = 'Y' )t1,(select * from check_wechattxndetail a where " ;
		if(toolDealBean.getTxnDay()==null||toolDealBean.getTxnDay1()==null||toolDealBean.getTxnDay().equals("")||toolDealBean.getTxnDay1().equals("")){
			return null; 
		}else{	//交易日期
		sql +=" a.cdate between to_date( '"+ toolDealBean.getTxnDay()+" 00:00:00','yyyy-MM-dd hh24:mi:ss') and to_date( '"+ toolDealBean.getTxnDay1()+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
		}
		sql+=")a1, sys_unit s where t1.mid = a1.mid  and s.unno= t1.UNNO ) group by unno, un_name,mid,rname,settmethod  ";
		String sqlCount = "select nvl(count(*),0) count, nvl(sum(txncount),0) COUNTS,to_char(nvl(sum(replace(txnamount,',','')),0),'FM999,999,999,999,990.00') as txnamount ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") b";

		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList(sql);
		Map<String,String> mapS=new HashMap<String, String>();
		Integer count=0;
		Map m=(Map)porListSum.get(0);
		count=Integer.parseInt(m.get("COUNT").toString());
		mapS.put("TXNAMOUNT", m.get("TXNAMOUNT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("TXNCOUNT", m.get("COUNTS").toString());
		proList.add(0, mapS);
		
		return proList;
	}
	@Override
	public List<Map<String, String>> exportBigScanProfitDetail(ToolDealBean toolDealBean, UserBean user) {
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
		//按照月份去查表check_unitdealdetail_xx
		String sql = " select sum(b.txnamount) txnamount,count(1) txncount,sum(b.profit) profit,b.unno,b.unname,b.mid,b.rname,b.rebatetype,SETTMETHOD"
				+ " from (select a.txnamount,a.profit,a.unno,a.un_name unname,a.mid,a.rname,a.maintainuid rebatetype,"
				+ " case when a.type = 1 and a.ifcard =1 and a.txnday < 20191201 then '微信0.38'"
				+ " when a.type = 1 and a.ifcard =2 and a.txnday < 20191201 then '微信0.45'"
				+ " when a.type = 1 and a.ifcard =3 and a.txnday < 20191201 then '微信（老）'"
				+ " when a.type = 2 and a.txnday < 20191201 then '支付宝'"
				+ " when a.type = 3 and a.txnday <20191201 then '二维码'"
				+ " when a.type in(1,2) and a.ifcard =1 and a.txnday >= 20191201 then '扫码1000以上（终端0.38）'"
				+ " when a.type in(1,2) and a.ifcard =2 and a.txnday >= 20191201 then '扫码1000以上（终端0.45）'"
				+ " when a.type in(1,2) and a.ifcard =3 and a.txnday >= 20191201 then '扫码1000以下（终端0.38）'"
				+ " when a.type in(1,2) and a.ifcard =4 and a.txnday >= 20191201 then '扫码1000以下（终端0.45）'"
				+ " when a.type = 3 and a.txnday >= 20191201 then '银联二维码'"
				+ " when a.type in(11,12) then '花呗交易'"
				+ " else '其他' end as SETTMETHOD "
				
			+" from  check_unitdealdetail_"+toolDealBean.getTxnDay().substring(4, 6)+" a "
			+ "where a.txnday >=:txnday and txnday <=:txnday1 and a.ismpos=2 and a.type in (1,2,3,11,12) ";
		map.put("txnday", toolDealBean.getTxnDay());
		map.put("txnday1", toolDealBean.getTxnDay1());
		if("110000".equals(user.getUnNo())) {//总公司权限
		}else if(user.getUseLvl()==0) {//代理商管理员
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno1 connect by prior unno = upper_unit) ";
			map.put("unno1", user.getUnNo());
		}
		if(toolDealBean.getMid()!=null&&!"".equals(toolDealBean.getMid())) {
			sql += " and a.mid=:mid ";
			map.put("mid", toolDealBean.getMid());
		}
		//活动类型
		if(StringUtils.isNotEmpty(toolDealBean.getMaintainType())){
			sql += " and a.maintainuid= '"+toolDealBean.getMaintainType()+"' ";
		}
		
		sql += " )b group by unno,unname,mid,rname,SETTMETHOD,rebatetype ";
		sql += " order by unno desc ";
		List<Map<String,String>> list = checkUnitDealDetailDao.queryObjectsBySqlListMap(sql, map);
//		List<Map<String,String>> list = checkUnitDealDetailDao.queryObjectsBySqlList(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		String sql2 = "select nvl(sum(txncount),0) COUNTS, nvl(sum(txnamount),0) txnamount ,nvl(sum(PROFIT),0) profit from ("+sql+") ";
		List porListSum = checkUnitDealDetailDao.executeSql2(sql2,map);
		Map<String,String> mapS=new HashMap<String, String>();
		Map m=(Map)porListSum.get(0);
		mapS.put("UNNO", "汇总");
		mapS.put("TXNAMOUNT", m.get("TXNAMOUNT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("TXNCOUNT", m.get("COUNTS").toString());
		list.add(0, mapS);
		return list;
	}
	@Override
	public DataGridBean queryFirstAgentTransferAndGetCash(ToolDealBean toolDealBean, UserBean userBean) {
		DataGridBean dataGridBean = new DataGridBean();
//		String sql = "select to_char(sum(nvl(cashamt, 0)),'FM999,999,999,999,990.00') as cashamt,count(cashamt) as count,to_char(sum(nvl(profit, 0)),'FM999,999,999,999,990.00') as profit, unno, unname, settmethod,secondrate "+
//				     "from (select decode(a1.cashmode,'5','扫码', decode(t1.ism35,'1', decode(t1.settmethod, "+
//				     "'000000','理财', case when t1.BANKFEERATE != 0.0072 then '非0.72秒到' else '0.72秒到'end), "+
//				     "'传统')) as settmethod,  a1.profit, s.unno, t1.rname, a1.cashamt cashamt,s.un_name as unname,t1.secondrate "+
//				     "from (select * from bill_merchantinfo t where 1=1 " ;
		String month = null;
		String start = StringUtils.isNotEmpty(toolDealBean.getTxnDay())?toolDealBean.getTxnDay().replaceAll("-",""):"";
		String end = StringUtils.isNotEmpty(toolDealBean.getTxnDay1())?toolDealBean.getTxnDay1().replaceAll("-",""):"";
		if(StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)){
			if(start.substring(0,6).equals(end.substring(0,6))){
				month=start.substring(4,6);
			}else{
				return dataGridBean;
			}
		}else{
			return dataGridBean;
		}
		String sql = getYidaiSumCashSql(toolDealBean, userBean, month, start, end);
		if (sql == null) return dataGridBean;
		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS, to_char(nvl(sum(replace(cashamt,',','')),0),'FM999,999,999,999,990.00') as cashamt ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") ";
		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, null, toolDealBean.getPage(),toolDealBean.getRows());
		Map<String,String> mapS=new HashMap<String, String>();
		Integer count=0;
		Map m=(Map)porListSum.get(0);
		count=Integer.parseInt(m.get("COUNT").toString());
		mapS.put("CASHAMT", m.get("CASHAMT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("COUNT", m.get("COUNTS").toString());
		proList.add(0, mapS);
		dataGridBean.setTotal(count);
		dataGridBean.setRows(proList);
		return dataGridBean;
	}

	/**
	 * 代理分润管理->提现转账分润汇总sql
	 * @param toolDealBean
	 * @param userBean
	 * @param month
	 * @param start
	 * @param end
	 * @return
	 */
	private String getYidaiSumCashSql(ToolDealBean toolDealBean, UserBean userBean, String month, String start, String end) {
		String sql = "select to_char(sum(nvl(cashamt, 0)), 'FM999,999,999,999,990.00') as cashamt, " +
				"       sum(cashcount) as count, " +
				"       to_char(sum(nvl(profit, 0)), 'FM999,999,999,999,990.00') as profit, " +
				"       yidai unno, " +
				"       (select su.un_name from sys_unit su where su.unno=yidai) unname, " +
				"       settmethod,secondrate " +
				"  from (select decode(t.mertype,7,'传统', " +
				"                      case " +
				"                        when t.mertype = 1 then '0.72秒到' " +
				"                        when t.mertype = 2 then '非0.72秒到' " +
				"                        when t.mertype = 3 or t.mertype = 6 then '理财' " +
				"                        when t.mertype = 4 or t.mertype = 5 then '扫码' " +
				"                      end) settmethod, " +
				"               nvl((select s.unno from sys_unit s where s.un_lvl = 2 start with s.unno = t.UNNO " +
				"                   connect by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),t.unno) yidai, " +
				"               t.* " +
				"          from check_cashtotal_" + month + " t " +
				"         where t.mertype in (1,2,3,4,5,6,7) ";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if ("110000".equals(userBean.getUnNo())) {
		}
		//为总公司并且是部门---查询全部
		else if (unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0) {
			UnitModel parent = unitModel.getParent();
			if ("110000".equals(parent.getUnNo())) {
			}
		} else {
			String childUnno = merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			sql += " and t.unno IN (" + childUnno + ") ";
		}
		if (toolDealBean.getTxnDay() == null || toolDealBean.getTxnDay1() == null || toolDealBean.getTxnDay().equals("") || toolDealBean.getTxnDay1().equals("")) {
			return null;
		} else {
			sql += " and t.cashday between '" + start + "' and '" + end + "' ";
		}
		sql += " )group by settmethod, yidai, secondrate ";
		return sql;
	}

	/**
	 * 一级代理-收银台提现转账分润汇总
	 * @param toolDealBean
	 * @param userBean
	 * @return
	 */
    @Override
    public DataGridBean queryFirstAgentTransferAndGetCashSyt(ToolDealBean toolDealBean, UserBean userBean) {
        DataGridBean dataGridBean = new DataGridBean();
        // @author:xuegangliu-20190221  收银台提现转账分润汇总
//        String sql = "select to_char(sum(nvl(cashamt, 0)),'FM999,999,999,999,990.00') as cashamt," +
//				" count(cashamt) as count,to_char(sum(nvl(profit, 0)),'FM999,999,999,999,990.00') as profit," +
//				" unno, unname,secondrate"+
//                " from (select a1.profit, s.unno, t1.rname, a1.cashamt cashamt,s.un_name as unname,t1.secondrate "+
//                "from (select * from bill_merchantinfo t where 1=1 " ;
		// @author:lxg-20190429 代理商分润转账 收银台汇总修改为新表
		String month = null;
		String start = StringUtils.isNotEmpty(toolDealBean.getTxnDay())?toolDealBean.getTxnDay().replaceAll("-",""):"";
		String end = StringUtils.isNotEmpty(toolDealBean.getTxnDay1())?toolDealBean.getTxnDay1().replaceAll("-",""):"";
		if(StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)){
			if(start.substring(0,6).equals(end.substring(0,6))){
				month=start.substring(4,6);
			}else{
				return dataGridBean;
			}
		}else{
			return dataGridBean;
		}
		String sql = getYidaiSytSumCashSql(toolDealBean, userBean, month, start, end);
		if (sql == null) return dataGridBean;

		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS, to_char(nvl(sum(replace(cashamt,',','')),0),'FM999,999,999,999,990.00') as cashamt ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") ";
        List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
        List proList = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, null, toolDealBean.getPage(),toolDealBean.getRows());
        Map<String,String> mapS=new HashMap<String, String>();
        Integer count=0;
        Map m=(Map)porListSum.get(0);
        count=Integer.parseInt(m.get("COUNT").toString());
        mapS.put("CASHAMT", m.get("CASHAMT").toString());
        mapS.put("PROFIT", m.get("PROFIT").toString());
        mapS.put("COUNT", m.get("COUNTS").toString());
        proList.add(0, mapS);
        dataGridBean.setTotal(count);
        dataGridBean.setRows(proList);
        return dataGridBean;
    }

	public DataGridBean queryTransferAndGetCashPlus(ToolDealBean toolDealBean, UserBean userBean) {
		//  @author:lxg-20190627 代理商分润-plus提现转账
		DataGridBean dataGridBean = new DataGridBean();
		String month = null;
		String start = StringUtils.isNotEmpty(toolDealBean.getTxnDay())?toolDealBean.getTxnDay().replaceAll("-",""):"";
		String end = StringUtils.isNotEmpty(toolDealBean.getTxnDay1())?toolDealBean.getTxnDay1().replaceAll("-",""):"";
		if(StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)){
			if(start.substring(0,6).equals(end.substring(0,6))){
				month=start.substring(4,6);
			}else{
				return dataGridBean;
			}
		}else{
			return dataGridBean;
		}
		String sql = getYidaiPlusSumCashSql(toolDealBean, userBean, month, start, end);
		if (sql == null) return dataGridBean;
		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS, to_char(nvl(sum(replace(cashamt,',','')),0),'FM999,999,999,999,990.00') as cashamt ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") ";
		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, null, toolDealBean.getPage(),toolDealBean.getRows());
		Map<String,String> mapS=new HashMap<String, String>();
		Integer count=0;
		Map m=(Map)porListSum.get(0);
		count=Integer.parseInt(m.get("COUNT").toString());
		mapS.put("CASHAMT", m.get("CASHAMT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("COUNT", m.get("COUNTS").toString());
		mapS.put("SETTMETHOD", "-1");
		proList.add(0, mapS);
		dataGridBean.setTotal(count);
		dataGridBean.setRows(proList);
		return dataGridBean;
	}

	/**
	 * 代理分润管理->收银台提现转账分润汇总sql
	 * @param toolDealBean
	 * @param userBean
	 * @param month
	 * @param start
	 * @param end
	 * @return
	 */
	private String getYidaiSytSumCashSql(ToolDealBean toolDealBean, UserBean userBean, String month, String start, String end) {
		String sql = "select SETTMETHOD,to_char(sum(nvl(cashamt, 0)), 'FM999,999,999,999,990.00') as cashamt, " +
				"       sum(cashcount) as count, " +
				"       to_char(sum(nvl(profit, 0)), 'FM999,999,999,999,990.00') as profit, " +
				"       yidai unno, " +
				"       (select su.un_name from sys_unit su where su.unno=yidai) unname, " +
				"       secondrate " +
				"       ,minfo1 rebatetype " +
				"  from (select nvl((select s.unno from sys_unit s where s.un_lvl = 2 start with s.unno = t.UNNO " +
				"                   connect by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),t.unno) yidai, " +
				
				" case when t.minfo2 = 1 and t.cashday < 20191201 then '微信0.38'"+
				" when t.minfo2 = 2 and t.cashday < 20191201 then '微信0.45'"+
				" when t.minfo2 = 3 and t.cashday < 20191201 then '微信（老）'"+
				" when t.minfo2 = 4 and t.cashday < 20191201 then '支付宝'"+
				" when t.minfo2 = 5 and t.cashday < 20191201 then '二维码'"+
				" when t.minfo2 = 1 and t.cashday >= 20191201 then '扫码1000以上（终端0.38）'"+
				" when t.minfo2 = 2 and t.cashday >= 20191201 then '扫码1000以下（终端0.45）'"+
				" when t.minfo2 = 3 and t.cashday >= 20191201 then '扫码1000以上（终端0.38）'"+
				" when t.minfo2 = 4 and t.cashday >= 20191201 then '扫码1000以下（终端0.45）'"+
				" when t.minfo2 = 5 and t.cashday >= 20191201 then '银联二维码'"+
				" when t.minfo2 = 6 then '花呗'"+
				" else '其他'  end as SETTMETHOD,"+
				"               t.* " +
				"          from check_cashtotal_" + month + " t " +
				"         where t.mertype = 8 ";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if ("110000".equals(userBean.getUnNo())) {
		}
		//为总公司并且是部门---查询全部
		else if (unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0) {
			UnitModel parent = unitModel.getParent();
			if ("110000".equals(parent.getUnNo())) {
			}
		} else {
			String childUnno = merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			sql += " and t.unno IN (" + childUnno + ") ";
		}

		//活动类型
		if(StringUtils.isNotEmpty(toolDealBean.getMaintainType())){
			sql += " and minfo1= '"+toolDealBean.getMaintainType()+"' ";
		}
		
		if (toolDealBean.getTxnDay() == null || toolDealBean.getTxnDay1() == null || toolDealBean.getTxnDay().equals("") || toolDealBean.getTxnDay1().equals("")) {
			return null;
		} else {
			sql += " and t.cashday between '" + start + "' and '" + end + "' ";
		}
		sql += " )group by yidai, secondrate,SETTMETHOD,minfo1 ";
		return sql;
	}

	private String getYidaiPlusSumCashSql(ToolDealBean toolDealBean, UserBean userBean, String month, String start, String end) {
		// @author:lxg-20190627 代理商分润-plus提现转账
		String sql = "select minfo1,SETTMETHOD,to_char(sum(nvl(cashamt, 0)), 'FM999,999,999,999,990.00') as cashamt, " +
				"       sum(cashcount) as count, " +
				"       to_char(sum(nvl(profit, 0)), 'FM999,999,999,999,990.00') as profit, " +
				"       yidai unno, " +
				"       (select su.un_name from sys_unit su where su.unno=yidai) unname, " +
				"       secondrate " +
				"  from (select nvl((select s.unno from sys_unit s where s.un_lvl = 2 start with s.unno = t.UNNO " +
				"                   connect by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),t.unno) yidai, " +
				" case when t.mertype =10 then '40'"+
				" when t.mertype =11 and t.minfo2 = 1 and t.cashday <20191201 then '41'"+
				" when t.mertype =11 and t.minfo2 = 2 and t.cashday <20191201 then '42'"+
				" when t.mertype =11 and t.minfo2 = 3 and t.cashday <20191201 then '43'"+
				" when t.mertype =11 and t.minfo2 = 4 and t.cashday <20191201 then '44'"+
				" when t.mertype =11 and t.minfo2 = 5 and t.cashday <20191201 then '45'"+
				" when t.mertype =11 and t.minfo2 = 1 and t.cashday >= 20191201 then '46'"+
				" when t.mertype =11 and t.minfo2 = 2 and t.cashday >= 20191201 then '47'"+
				" when t.mertype =11 and t.minfo2 = 3 and t.cashday >= 20191201 then '48'"+
				" when t.mertype =11 and t.minfo2 = 4 and t.cashday >= 20191201 then '49'"+
				" when t.mertype =11 and t.minfo2 = 5 and t.cashday >= 20191201 then '50'"+
				" when t.mertype =11 and t.minfo2 = 6 then '51'"+
				" end as SETTMETHOD,"+
				
				"               t.* " +
				"          from check_cashtotal_" + month + " t " +
				"         where (t.mertype in (10,11) or t.minfo1>21) ";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if ("110000".equals(userBean.getUnNo())) {
		}
		//为总公司并且是部门---查询全部
		else if (unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0) {
			UnitModel parent = unitModel.getParent();
			if ("110000".equals(parent.getUnNo())) {
			}
		} else {
			String childUnno = merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			sql += " and t.unno IN (" + childUnno + ") ";
		}

		if (toolDealBean.getTxnDay() == null || toolDealBean.getTxnDay1() == null || toolDealBean.getTxnDay().equals("") || toolDealBean.getTxnDay1().equals("")) {
			return null;
		} else {
			sql += " and t.cashday between '" + start + "' and '" + end + "') where 1=1";
		}

		//交易类型
		if(StringUtils.isNotEmpty(toolDealBean.getTxnType())){
			sql += " and SETTMETHOD ='"+toolDealBean.getTxnType()+"'";
		}
		//活动类型
		if(StringUtils.isNotEmpty(toolDealBean.getMaintainType())){
			sql += " and minfo1= '"+toolDealBean.getMaintainType()+"' ";
		}
		sql += "  group by yidai, secondrate,minfo1,SETTMETHOD ";
		
		return sql;
	}

	@Override
	public List<Map<String, String>> exportFirstAgentTransferAndGetCash(ToolDealBean toolDealBean, UserBean userBean) {
//		String sql = "select to_char(sum(nvl(cashamt, 0)),'FM999,999,999,999,990.00') as cashamt,count(cashamt) as count,to_char(sum(nvl(profit, 0)),'FM999,999,999,999,990.00') as profit, unno, unname, settmethod,secondrate "+
//			     "from (select decode(a1.cashmode,'5','扫码', decode(t1.ism35,'1', decode(t1.settmethod, "+
//			     "'000000','理财', case when t1.BANKFEERATE != 0.0072 then '非0.72秒到' else '0.72秒到'end), "+
//			     "'传统')) as settmethod,  a1.profit, s.unno, t1.rname, a1.cashamt cashamt,s.un_name as unname,t1.secondrate "+
//			     "from (select * from bill_merchantinfo t where 1=1 " ;
//		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
//		//为总公司
//		if("110000".equals(userBean.getUnNo())){
//		}
//		//为总公司并且是部门---查询全部
//		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
//		UnitModel parent = unitModel.getParent();
//		if("110000".equals(parent.getUnNo())){
//		}
//		}else{
//		String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
//		sql+=" and t.unno IN ("+childUnno+") ";
//		}
//
//		if (toolDealBean.getUnNo() != null &&!"".equals(toolDealBean.getUnNo())) { //机构号
//		if (toolDealBean.getUnNo().equals(userBean.getUnNo())) {
//			return null;
//		}
//		sql += " and t.unno = '"+toolDealBean.getUnNo()+"'";
//		}
//		Integer isM35 = toolDealBean.getIsM35();
//		if (isM35!=null) {
//		if (0==isM35) { //0.72秒到
//			sql += " and t.ism35 = '1' and t.settmethod='100000' and t.BANKFEERATE = 0.0072 and a.cashmode != '5'";
//		}else if (1==isM35) { //非0.72秒到
//			sql += " and t.ism35 = '1' and t.settmethod='100000'  and t.BANKFEERATE < 0.0072 and a.cashmode != '5'";
//		}else if (2==isM35) { //理财
//			sql += "  and t.ism35 = '1' and t.settmethod='000000' and a.cashmode != '5'";
//		}else if (3==isM35) { //传统
//			sql += " and t.ism35 != '1' and a.cashmode != '5' ";
//		}
//		}
//		sql += ")t1, (select * from check_cash a where a.cashmode in(1,4,5) " ;
//		if(toolDealBean.getTxnDay()==null||toolDealBean.getTxnDay1()==null||toolDealBean.getTxnDay().equals("")||toolDealBean.getTxnDay1().equals("")){
//			return null;
//		}else{
//		sql += " and a.cashday between to_char(to_date( '"+ toolDealBean.getTxnDay()+"','yyyy/MM/dd'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+"','yyyy/MM/dd'),'yyyyMMdd') ";
//		}
//		if (isM35!=null&&4==isM35) {//云闪付
//		sql += " and a.cashmode = '5' ";
//		}
//		sql += ")a1, sys_unit s  where t1.mid = a1.mid and  s.unno= nvl((select s.unno  from sys_unit s  where s.un_lvl=2  start with s.unno = t1.UNNO  connect  by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),t1.unno))group by settmethod, unname, unno,secondrate ";
//
//
//		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS, to_char(nvl(sum(replace(cashamt,',','')),0),'FM999,999,999,999,990.00') as cashamt ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") ";
		String start = StringUtils.isNotEmpty(toolDealBean.getTxnDay())?toolDealBean.getTxnDay().replaceAll("-",""):"";
		String end = StringUtils.isNotEmpty(toolDealBean.getTxnDay1())?toolDealBean.getTxnDay1().replaceAll("-",""):"";
		String month = getCashTableMonth(start, end);
		if (month == null) return null;
		String sql = getYidaiSumCashSql(toolDealBean, userBean, month, start, end);
		if (sql == null) return null;

		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS, to_char(nvl(sum(replace(cashamt,',','')),0),'FM999,999,999,999,990.00') as cashamt ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") ";
		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList(sql);
		Map<String,String> mapS=new HashMap<String, String>();
//		Integer count=0;
		Map m=(Map)porListSum.get(0);
		mapS.put("UNNO", "汇总");
		mapS.put("CASHAMT", m.get("CASHAMT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("COUNT", m.get("COUNTS").toString());
		proList.add(0, mapS);
		return proList;
	}

	/**
	 * 收银台提现转账分润汇总导出
	 * @param toolDealBean
	 * @param userBean
	 * @return
	 */
    @Override
    public List<Map<String, String>> exportFirstAgentTransferAndGetCashSyt(ToolDealBean toolDealBean, UserBean userBean) {
		// @author:xuegangliu-20190221  收银台提现转账分润汇总导出
//		String sql = "select to_char(sum(nvl(cashamt, 0)),'FM999,999,999,999,990.00') as cashamt," +
//				" count(cashamt) as count,to_char(sum(nvl(profit, 0)),'FM999,999,999,999,990.00') as profit," +
//				" unno, unname,secondrate"+
//				" from (select a1.profit, s.unno, t1.rname, a1.cashamt cashamt,s.un_name as unname,t1.secondrate "+
//				"from (select * from bill_merchantinfo t where 1=1 " ;
//		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
//		//为总公司
//		if("110000".equals(userBean.getUnNo())){
//		}
//		//为总公司并且是部门---查询全部
//		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
//			UnitModel parent = unitModel.getParent();
//			if("110000".equals(parent.getUnNo())){
//			}
//		}else{
//			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
//			sql+=" and t.unno IN ("+childUnno+") ";
//		}
//
//		sql += ")t1, (select * from check_cash a where a.cashmode = 6 " ;
//		if(toolDealBean.getTxnDay()==null||toolDealBean.getTxnDay1()==null||toolDealBean.getTxnDay().equals("")||toolDealBean.getTxnDay1().equals("")){
//			return null;
//		}else{
//			sql += " and a.cashday between to_char(to_date( '"+ toolDealBean.getTxnDay()+
//					" 00:00:00','yyyy-MM-dd hh24:mi:ss'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+
//					"','yyyy/MM/dd'),'yyyyMMdd') ";
//		}
//		sql += ")a1, sys_unit s  where t1.mid = a1.mid and  s.unno= nvl((select s.unno  from sys_unit s " +
//				" where s.un_lvl=2  start with s.unno = t1.UNNO  connect  by NOCYCLE s.unno = prior s.upper_unit and rownum = 1)," +
//				" t1.unno))group by unname, unno,secondrate";
//        String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS, to_char(nvl(sum(replace(cashamt,',','')),0),'FM999,999,999,999,990.00') as cashamt ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") ";
		// @author:lxg-20190429 代理商分润转账 收银台汇总修改为新表
		String month = null;
		String start = StringUtils.isNotEmpty(toolDealBean.getTxnDay())?toolDealBean.getTxnDay().replaceAll("-",""):"";
		String end = StringUtils.isNotEmpty(toolDealBean.getTxnDay1())?toolDealBean.getTxnDay1().replaceAll("-",""):"";
		month = getCashTableMonth(start, end);
		if (month == null) return null;
		String sql = getYidaiSytSumCashSql(toolDealBean, userBean, month, start, end);
		if (sql == null) return null;

        String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS, to_char(nvl(sum(replace(cashamt,',','')),0),'FM999,999,999,999,990.00') as cashamt ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") ";
        List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
        List proList = checkUnitDealDetailDao.queryObjectsBySqlList(sql);
        Map<String,String> mapS=new HashMap<String, String>();
//		Integer count=0;
        Map m=(Map)porListSum.get(0);
        mapS.put("UNNO", "汇总");
        mapS.put("CASHAMT", m.get("CASHAMT").toString());
        mapS.put("PROFIT", m.get("PROFIT").toString());
        mapS.put("COUNT", m.get("COUNTS").toString());
        proList.add(0, mapS);
        return proList;
    }
	@Override
	public List<Map<String, String>> exportFirstAgentTransferAndGetCashPlus(ToolDealBean toolDealBean, UserBean userBean) {
		String month = null;
		String start = StringUtils.isNotEmpty(toolDealBean.getTxnDay())?toolDealBean.getTxnDay().replaceAll("-",""):"";
		String end = StringUtils.isNotEmpty(toolDealBean.getTxnDay1())?toolDealBean.getTxnDay1().replaceAll("-",""):"";
		month = getCashTableMonth(start, end);
		if (month == null) return null;
		String sql = getYidaiPlusSumCashSql(toolDealBean, userBean, month, start, end);
		if (sql == null) return null;

		String sql1 = "select minfo1,decode(SETTMETHOD,40,'刷卡',41,'微信0.38',42,'微信0.45',43,'微信（老）',44,'支付宝',45,'二维码'," + 
				"46,'扫码1000以上(终端0.38)',47,'扫码1000以上(终端0.45)'," + 
				"48,'扫码1000以下(终端0.38)',49,'扫码1000以下(终端0.45)',50,'银联二维码',51,'花呗','扫码') SETTMETHOD," + 
				"cashamt,count,profit,unno,unname,secondrate from ("+sql+")";
		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS, to_char(nvl(sum(replace(cashamt,',','')),0),'FM999,999,999,999,990.00') as cashamt ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql1+") ";
		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList(sql1);
		Map<String,String> mapS=new HashMap<String, String>();
//		Integer count=0;
		Map m=(Map)porListSum.get(0);
		mapS.put("UNNO", "汇总");
		mapS.put("CASHAMT", m.get("CASHAMT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("COUNT", m.get("COUNTS").toString());
		proList.add(0, mapS);
		return proList;
	}

	private String getCashTableMonth(String start, String end) {
		String month;
		if (StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)) {
			if (start.substring(0, 6).equals(end.substring(0, 6))) {
				month = start.substring(4, 6);
			} else {
				return null;
			}
		} else {
			return null;
		}
		return month;
	}

	@Override
	public DataGridBean queryFirstAgentSwipProfitDetail(ToolDealBean toolDealBean, UserBean userBean) {
		DataGridBean dataGridBean = new DataGridBean();
		String sql  =  "select to_char(sum(TXNAMOUNT),'FM999,999,999,999,990.00') as TXNAMOUNT,count (txnamount) as count,to_char(sum(profit),'FM999,999,999,999,990.00') as profit,mid,rname, "+
		        	" unno ,unname,settmethod,areatype,salename，bankfeerate from( select decode(a.cbrand,'1', case  when a.txnamount>=nvl(t.dealamt,4146)then decode(t.ism35,'1','理财借记卡大额','传统借记卡大额')"+
                    " else decode(t.ism35,'1','理财借记卡小额','传统借记卡小额')end, decode(t.ism35,'1','理财贷记卡','传统贷记卡')) as settmethod, "+
		        	" a.profit, a.mda mda,s.unno, a.txnamount txnamount, s.un_name as unname,t.mid,t.rname,t.areatype,i.salename,t.bankfeerate "+
					" from  bill_merchantinfo t, check_unitdealdetail a,sys_unit s,bill_agentsalesinfo i where t.mid = a.mid  and s.unno = t.UNNO and (t.settmethod ='000000' or t.settmethod is null) and t.busid = i.busid " ;
	    
	    UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if("110000".equals(userBean.getUnNo())){
		 }
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			sql+=" and t.unno IN ("+childUnno+") ";
		}
		if(toolDealBean.getMid() != null &&!"".equals(toolDealBean.getMid())){
			sql+=" and t.mid='"+toolDealBean.getMid()+"' ";
		}
		Integer isM35 = toolDealBean.getIsM35();
		if (isM35 != null && !"".equals(isM35)) {
			if (0==isM35) { //0.72秒到
				sql += " and t.ism35 = '1' and t.settmethod='100000' and t.BANKFEERATE = 0.0072 ";
			}else if (1==isM35) { //非0.72秒到
				sql += " and t.ism35 = '1' and t.settmethod='100000'  and t.BANKFEERATE < 0.0072 ";
			}else if (2==isM35) { //理财
				sql += "  and t.ism35 = 1 and t.settmethod='000000'  ";
			}else if (3==isM35) { //传统
				sql += " and t.ism35 != '1' ";
			}else if (4==isM35) {//云闪付
				sql += " and a.ifcard = '3' ";
			}
		}
		String txnday = toolDealBean.getTxnDay();
		String txnday1 = toolDealBean.getTxnDay1();
		if(txnday==null&&txnday1==null||txnday.equals("")&&txnday1.equals("")){
	   	 	return null; 
	    }
	    else{	
	    	sql +=" and a.txnday between to_char(to_date( '"+ toolDealBean.getTxnDay()+"','yyyy/MM/dd'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+"','yyyy/MM/dd'),'yyyyMMdd') ";
	    }
	    sql+=") group by settmethod, unname, unno,mid,rname,areatype,bankfeerate,salename ";
		
		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS,to_char(nvl(sum(replace(txnamount,',','')),0),'FM999,999,999,999,990.00') as txnamount ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") b";
		
		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, null, toolDealBean.getPage(),toolDealBean.getRows());
		Map<String,String> mapS=new HashMap<String, String>();
		Integer count=0;
		Map m=(Map)porListSum.get(0);
		count=Integer.parseInt(m.get("COUNT").toString());
		mapS.put("TXNAMOUNT", m.get("TXNAMOUNT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("COUNT", m.get("COUNTS").toString());
		proList.add(0, mapS);
		dataGridBean.setTotal(count);
		dataGridBean.setRows(proList);
		porListSum=null;
		proList=null;
		return dataGridBean;
	}
	
	@Override
	public DataGridBean queryFirstAgentSwipProfitDetail_20304(ToolDealBean toolDealBean, UserBean userBean) {
		DataGridBean dataGridBean = new DataGridBean();
		String sql  =  "select to_char(sum(TXNAMOUNT),'FM999,999,999,999,990.00') as TXNAMOUNT,count (txnamount) as count,to_char(sum(profit),'FM999,999,999,999,990.00') as profit,mid,rname, "+
		" unno ,unname,settmethod，bankfeerate from( select decode(a.ifcard,'3','云闪付',(case when t.BANKFEERATE != 0.0072 then '非0.72秒到' else '0.72秒到' end)) as settmethod, "+
		" a.profit, a.mda mda,s.unno, a.txnamount txnamount, s.un_name as unname,t.mid,t.rname,t.bankfeerate "+
		" from  bill_merchantinfo t, check_unitdealdetail a,sys_unit s where t.mid = a.mid  and s.unno = t.UNNO and t.settmethod ='100000' " ;
		
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if("110000".equals(userBean.getUnNo())){
		}
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			sql+=" and t.unno IN ("+childUnno+") ";
		}
		if(toolDealBean.getMid() != null &&!"".equals(toolDealBean.getMid())){
			sql+=" and t.mid='"+toolDealBean.getMid()+"' ";
		}
		Integer isM35 = toolDealBean.getIsM35();
		if (isM35 != null && !"".equals(isM35)) {
			if (0==isM35) { //0.72秒到
				sql += " and t.ism35 = '1' and t.settmethod='100000' and t.BANKFEERATE = 0.0072 ";
			}else if (1==isM35) { //非0.72秒到
				sql += " and t.ism35 = '1' and t.settmethod='100000'  and t.BANKFEERATE < 0.0072 ";
			}else if (2==isM35) { //理财
				sql += "  and t.ism35 = 1 and t.settmethod='000000'  ";
			}else if (3==isM35) { //传统
				sql += " and t.ism35 != '1' ";
			}else if (4==isM35) {//云闪付
				sql += " and a.ifcard = '3' ";
			}
		}
		String txnday = toolDealBean.getTxnDay();
		String txnday1 = toolDealBean.getTxnDay1();
		if(txnday==null&&txnday1==null||txnday.equals("")&&txnday1.equals("")){
			return null; 
		}
		else{	
			sql +=" and a.txnday between to_char(to_date( '"+ toolDealBean.getTxnDay()+"','yyyy/MM/dd'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+"','yyyy/MM/dd'),'yyyyMMdd') ";
		}
		sql+=") group by settmethod, unname, unno,mid,rname,bankfeerate ";
		
		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS,to_char(nvl(sum(replace(txnamount,',','')),0),'FM999,999,999,999,990.00') as txnamount ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") b";
		
		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, null, toolDealBean.getPage(),toolDealBean.getRows());
		Map<String,String> mapS=new HashMap<String, String>();
		Integer count=0;
		Map m=(Map)porListSum.get(0);
		count=Integer.parseInt(m.get("COUNT").toString());
		mapS.put("TXNAMOUNT", m.get("TXNAMOUNT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("COUNT", m.get("COUNTS").toString());
		proList.add(0, mapS);
		dataGridBean.setTotal(count);
		dataGridBean.setRows(proList);
		porListSum=null;
		proList=null;
		return dataGridBean;
	}
	
	@Override
	public List<Map<String, String>> exportFirstAgentProfitCollectDetail(ToolDealBean toolDealBean, UserBean userBean){
		String sql  =  "select to_char(sum(TXNAMOUNT),'FM999,999,999,999,990.00') as TXNAMOUNT,count (txnamount) as count,to_char(sum(profit),'FM999,999,999,999,990.00') as profit,mid,rname, "+
			" unno ,unname,settmethod,AREATYPE,salename,bankfeerate from( select decode(a.cbrand,'1', case  when  a.txnamount>=nvl(t.dealamt,4146) then decode(t.ism35,'1','理财借记卡大额','传统借记卡大额')"+
                    " else decode(t.ism35,'1','理财借记卡小额','传统借记卡小额')end, decode(t.ism35,'1','理财贷记卡','传统贷记卡')) as settmethod, "+
			" a.profit, a.mda mda,s.unno, a.txnamount txnamount,t.AREATYPE, s.un_name as unname,t.mid,t.rname,t.bankfeerate,i.salename "+
			" from bill_merchantinfo t,check_unitdealdetail a,sys_unit s,bill_agentsalesinfo i where t.mid = a.mid  and s.unno = t.UNNO and (t.settmethod ='000000' or t.settmethod is null) and t.busid=i.busid " ;
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if("110000".equals(userBean.getUnNo())){
		}
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
		}
		}else{
		String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			sql+=" and t.unno IN ("+childUnno+") ";
		}
		if(toolDealBean.getMid() != null &&!"".equals(toolDealBean.getMid())){
			sql+=" and t.mid='"+toolDealBean.getMid()+"' ";
		}
		Integer isM35 = toolDealBean.getIsM35();
		if (isM35 != null && !"".equals(isM35)) {
		if (0==isM35) { //0.72秒到
			sql += " and t.ism35 = '1' and t.settmethod='100000' and t.BANKFEERATE = 0.0072 ";
		}else if (1==isM35) { //非0.72秒到
			sql += " and t.ism35 = '1' and t.settmethod='100000'  and t.BANKFEERATE < 0.0072 ";
		}else if (2==isM35) { //理财
			sql += "  and t.ism35 = 1 and t.settmethod='000000'  ";
		}else if (3==isM35) { //传统
			sql += " and t.ism35 != '1' ";
		}else if (4==isM35) {//云闪付
			sql += " and a.ifcard = '3' ";
		}
		}
		String txnday = toolDealBean.getTxnDay();
		String txnday1 = toolDealBean.getTxnDay1();
		if(txnday==null&&txnday1==null||txnday.equals("")&&txnday1.equals("")){
			return null; 
		}
		else{	
			sql +=" and a.txnday between to_char(to_date( '"+ toolDealBean.getTxnDay()+"','yyyy/MM/dd'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+"','yyyy/MM/dd'),'yyyyMMdd') ";
		}
		sql+=") group by settmethod, unname, unno,mid,rname,AREATYPE,bankfeerate,salename ";

		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS,to_char(nvl(sum(replace(txnamount,',','')),0),'FM999,999,999,999,990.00') as txnamount ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") b";
		
		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		//List proList = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, null, toolDealBean.getPage(),toolDealBean.getRows());
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList(sql);
		Map<String,String> mapS=new HashMap<String, String>();
		Map m=(Map)porListSum.get(0);
		mapS.put("UNNO", "汇总");
		mapS.put("TXNAMOUNT", m.get("TXNAMOUNT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("COUNT", m.get("COUNTS").toString());
		proList.add(0, mapS);
		return proList;
	}
	@Override
	public List<Map<String, String>> exportFirstAgentProfitCollectDetail_20304(ToolDealBean toolDealBean, UserBean userBean){
//		DataGridBean dataGridBean = new DataGridBean();
		String sql  =  "select to_char(sum(TXNAMOUNT),'FM999,999,999,999,990.00') as TXNAMOUNT,count (txnamount) as count,to_char(sum(profit),'FM999,999,999,999,990.00') as profit,mid,rname, "+
		" unno ,unname,settmethod，bankfeerate from( select decode(a.ifcard,'3','云闪付',(case  when t.BANKFEERATE != 0.0072 then '非0.72秒到' else '0.72秒到' end)) as settmethod, "+
		" a.profit, a.mda mda,s.unno, a.txnamount txnamount, s.un_name as unname,t.mid,t.rname,t.bankfeerate "+
		" from bill_merchantinfo t,check_unitdealdetail a,sys_unit s where t.mid = a.mid  and s.unno = t.UNNO and t.settmethod ='100000'" ;
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if("110000".equals(userBean.getUnNo())){
		}
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			sql+=" and t.unno IN ("+childUnno+") ";
		}
		if(toolDealBean.getMid() != null &&!"".equals(toolDealBean.getMid())){
			sql+=" and t.mid='"+toolDealBean.getMid()+"' ";
		}
		Integer isM35 = toolDealBean.getIsM35();
		if (isM35 != null && !"".equals(isM35)) {
			if (0==isM35) { //0.72秒到
				sql += " and t.ism35 = '1' and t.settmethod='100000' and t.BANKFEERATE = 0.0072 ";
			}else if (1==isM35) { //非0.72秒到
				sql += " and t.ism35 = '1' and t.settmethod='100000'  and t.BANKFEERATE < 0.0072 ";
			}else if (2==isM35) { //理财
				sql += "  and t.ism35 = 1 and t.settmethod='000000'  ";
			}else if (3==isM35) { //传统
				sql += " and t.ism35 != '1' ";
			}else if (4==isM35) {//云闪付
				sql += " and a.ifcard = '3' ";
			}
		}
		String txnday = toolDealBean.getTxnDay();
		String txnday1 = toolDealBean.getTxnDay1();
		if(txnday==null&&txnday1==null||txnday.equals("")&&txnday1.equals("")){
			return null; 
		}
		else{	
			sql +=" and a.txnday between to_char(to_date( '"+ toolDealBean.getTxnDay()+"','yyyy/MM/dd'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+"','yyyy/MM/dd'),'yyyyMMdd') ";
		}
		sql+=") group by settmethod, unname, unno,mid,rname,bankfeerate ";
		
		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS,to_char(nvl(sum(replace(txnamount,',','')),0),'FM999,999,999,999,990.00') as txnamount ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") b";
		
		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		//List proList = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, null, toolDealBean.getPage(),toolDealBean.getRows());
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList(sql);
		Map<String,String> mapS=new HashMap<String, String>();
		Map m=(Map)porListSum.get(0);
		mapS.put("UNNO", "汇总");
		mapS.put("TXNAMOUNT", m.get("TXNAMOUNT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("COUNT", m.get("COUNTS").toString());
		proList.add(0, mapS);
		return proList;
	}

	@Override
	public DataGridBean queryFirstAgentScanProfitDetail(ToolDealBean toolDealBean, UserBean userBean) {
		DataGridBean dataGridBean = new DataGridBean();
		// @author:xuegangliu 20190325 剔除收银台数据
		String sql = "select to_char(sum(nvl(PROFIT, 0)),'FM999,999,999,999,990.00')  profit ,nvl(sum(mda), 0) mda , "+
	                 " to_char(sum(nvl(txnamount, 0)),'FM999,999,999,999,990.00') txnamount,count(mda) txncount ,unno, un_name,SETTMETHOD ,mid,rname "+
	                 "from(select a1.profit,t1.mid, a1.mda mda, s.unno, s.un_name,t1.rname, a1.txnamt txnamount, decode(t1.ism35,'1','手刷', '传统') as settmethod  "+
	                 "from ( select * from bill_merchantinfo t where 1=1 and t.mid not like 'HRTSYT%' " ;
	    UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司并且是部门---查询全部
		if("110000".equals(userBean.getUnNo())){
		 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			sql+=" and t.unno IN ("+childUnno+") ";
		}
		if (toolDealBean.getMid() != null && !"".equals(toolDealBean.getMid())) {
			sql += " and t.mid = '"+toolDealBean.getMid()+"'";
		}
		if (toolDealBean.getIsM35() != null && !"".equals(toolDealBean.getIsM35())) {
			if(toolDealBean.getIsM35()==1){
				sql += " and t.isM35 = '1'";
			}else if(toolDealBean.getIsM35()==0){
				sql += " and t.isM35 != '1'";
			}
		}
	    sql+="and t.maintaintype != 'D'  and t.approvestatus = 'Y' )t1,(select * from check_wechattxndetail a where " ;
	    if(toolDealBean.getTxnDay()==null||toolDealBean.getTxnDay1()==null||toolDealBean.getTxnDay().equals("")||toolDealBean.getTxnDay1().equals("")){
	   	 	return null; 
	    }else{	//交易日期
	    	sql +=" a.cdate between to_date( '"+ toolDealBean.getTxnDay()+" 00:00:00','yyyy-MM-dd hh24:mi:ss') and to_date( '"+ toolDealBean.getTxnDay1()+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
	    }
	   sql+=")a1, sys_unit s where t1.mid = a1.mid  and s.unno= t1.UNNO ) group by unno, un_name,mid,rname,settmethod  ";
		
	//	if (toolDealBean.getUnitName() != null && !"".equals(toolDealBean.getUnitName())) { // 机构名称
	//		sql += " and s.un_name like '%"+ toolDealBean.getUnitName() +"%'";
	//	}
		
		String sqlCount = "select nvl(count(*),0) count, nvl(sum(txncount),0) COUNTS,to_char(nvl(sum(replace(txnamount,',','')),0),'FM999,999,999,999,990.00') as txnamount ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") b";

		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, null, toolDealBean.getPage(),toolDealBean.getRows());
		Map<String,String> mapS=new HashMap<String, String>();
		Integer count=0;
		Map m=(Map)porListSum.get(0);
		count=Integer.parseInt(m.get("COUNT").toString());
		mapS.put("TXNAMOUNT", m.get("TXNAMOUNT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("TXNCOUNT", m.get("COUNTS").toString());
		proList.add(0, mapS);
		dataGridBean.setTotal(count);
		dataGridBean.setRows(proList);
		return dataGridBean;
	}
	
	@Override
	public DataGridBean queryBigScanProfitDetail(ToolDealBean toolDealBean, UserBean user) {
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
		String sql = " select sum(b.txnamount) txnamount,count(1) txncount,sum(b.profit) profit,b.unno,b.unname,b.mid,b.rname,b.rebatetype,SETTMETHOD"
			+ " from (select a.txnamount,a.profit,a.unno,a.un_name unname,a.mid,a.rname,a.maintainuid rebatetype,"
			+ " case when a.type = 1 and a.ifcard =1 and a.txnday < 20191201 then '微信0.38'"
			+ " when a.type = 1 and a.ifcard =2 and a.txnday < 20191201 then '微信0.45'"
			+ " when a.type = 1 and a.ifcard =3 and a.txnday < 20191201 then '微信（老）'"
			+ " when a.type = 2 and a.txnday < 20191201 then '支付宝'"
			+ " when a.type = 3 and a.txnday <20191201 then '二维码'"
			+ " when a.type in(1,2) and a.ifcard =1 and a.txnday >= 20191201 then '扫码1000以上（终端0.38）'"
			+ " when a.type in(1,2) and a.ifcard =2 and a.txnday >= 20191201 then '扫码1000以上（终端0.45）'"
			+ " when a.type in(1,2) and a.ifcard =3 and a.txnday >= 20191201 then '扫码1000以下（终端0.38）'"
			+ " when a.type in(1,2) and a.ifcard =4 and a.txnday >= 20191201 then '扫码1000以下（终端0.45）'"
			+ " when a.type = 3 and a.txnday >= 20191201 then '银联二维码'"
			+ " when a.type in(11,12) then '花呗交易'"
			+ " else '其他' end as SETTMETHOD"
		
			+" from  check_unitdealdetail_"+toolDealBean.getTxnDay().substring(4, 6)+" a "
			+ "where a.txnday >=:txnday and txnday <=:txnday1 and a.ismpos=2 and a.type in (1,2,3,11,12) ";
		map.put("txnday", toolDealBean.getTxnDay());
		map.put("txnday1", toolDealBean.getTxnDay1());
		if("110000".equals(user.getUnNo())) {//总公司权限
		}else if(user.getUseLvl()==0) {//代理商管理员
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno1 connect by prior unno = upper_unit) ";
			map.put("unno1", user.getUnNo());
		}
		//活动类型
		if(StringUtils.isNotEmpty(toolDealBean.getMaintainType())){
			sql += " and a.maintainuid= '"+toolDealBean.getMaintainType()+"' ";
		}
		if(toolDealBean.getMid()!=null&&!"".equals(toolDealBean.getMid())) {
			sql += " and a.mid=:mid ";
			map.put("mid", toolDealBean.getMid());
		}
		sql += " )b group by unno,unname,mid,rname,SETTMETHOD,rebatetype ";
		String sqlCount = "select count(1) from ("+sql+")";
		sql += " order by unno desc ";
		List<Map<String,Object>> list = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		BigDecimal counts = checkUnitDealDetailDao.querysqlCounts(sqlCount, map);
		String sql2 = "select nvl(sum(txncount),0) COUNTS, nvl(sum(txnamount),0) txnamount ,nvl(sum(PROFIT),0) profit from ("+sql+") ";
		List porListSum = checkUnitDealDetailDao.executeSql2(sql2,map);
		Map<String,Object> mapS=new HashMap<String, Object>();
		Map m=(Map)porListSum.get(0);
		mapS.put("UNNO", "汇总");
		mapS.put("TXNAMOUNT", m.get("TXNAMOUNT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("TXNCOUNT", m.get("COUNTS").toString());
		list.add(0, mapS);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}

	@Override
	public DataGridBean queryFirstAgentTransferAndGetCashProfitDetail(ToolDealBean toolDealBean, UserBean userBean) {
		DataGridBean dataGridBean = new DataGridBean();
//		String sql = "select to_char(sum(nvl(cashamt, 0)),'FM999,999,999,999,990.00') as cashamt,count(cashamt) as count,to_char(sum(nvl(profit, 0)),'FM999,999,999,999,990.00') as profit, unno, unname, settmethod,mid,rname,secondrate "+
//				     "from (select decode(a1.cashmode,'5','扫码', decode(t1.ism35,'1', decode(t1.settmethod, "+
//				     "'000000','理财', case when t1.BANKFEERATE != 0.0072 then '非0.72秒到' else '0.72秒到'end), "+
//				     "'传统')) as settmethod,  a1.profit, s.unno,t1.mid,t1.rname, a1.cashamt cashamt,s.un_name as unname,t1.secondrate "+
//				     "from (select * from bill_merchantinfo t where 1=1 " ;
		// @author:lxg-20190429 代理商分润转账汇总明细修改为新表
		String month=null;
		String start = StringUtils.isNotEmpty(toolDealBean.getTxnDay())?toolDealBean.getTxnDay().replaceAll("-",""):"";
		String end = StringUtils.isNotEmpty(toolDealBean.getTxnDay1())?toolDealBean.getTxnDay1().replaceAll("-",""):"";
		if(StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)){
			if(start.substring(0,6).equals(end.substring(0,6))){
				month=start.substring(4,6);
			}else{
				return dataGridBean;
			}
		}else{
			return dataGridBean;
		}
		String sql = getYidaiCashDetailSql(toolDealBean, userBean, month, start, end);
		if (sql == null) return dataGridBean;

		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS,to_char(nvl(sum(replace(cashamt,',','')),0),'FM999,999,999,999,990.00') as cashamt ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") ";
		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, null, toolDealBean.getPage(),toolDealBean.getRows());
		Map<String,String> mapS=new HashMap<String, String>();
		Integer count=0;
		Map m=(Map)porListSum.get(0);
		count=Integer.parseInt(m.get("COUNT").toString());
		mapS.put("CASHAMT", m.get("CASHAMT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("COUNT", m.get("COUNTS").toString());
		proList.add(0, mapS);
		dataGridBean.setTotal(count);
		dataGridBean.setRows(proList);
		return dataGridBean;
	}

	/**
	 * 代理分润管理->提现转账分润明细sql
	 * @param toolDealBean
	 * @param userBean
	 * @param month
	 * @param start
	 * @param end
	 * @return
	 */
	private String getYidaiCashDetailSql(ToolDealBean toolDealBean, UserBean userBean, String month, String start, String end) {
		String sql = "select to_char(sum(nvl(cashamt, 0)), 'FM999,999,999,999,990.00') as cashamt," +
				"       count(cashamt) as count," +
				"       to_char(sum(nvl(profit, 0)), 'FM999,999,999,999,990.00') as profit," +
				"       unno,un_name unname,settmethod,mid,rname,secondrate" +
				"  from (select decode(t.mertype,4,'传统'," +
				"                      case" +
				"                        when t.cashmode = 4 and t.mertype = 2 then '0.72秒到'" +
				"                        when t.cashmode = 4 and t.mertype = 3 then '非0.72秒到'" +
				"                        when t.cashmode = 4 then " +
				" decode((select s.bankfeerate from bill_merchantinfo s where s.mid=t.mid),0.0072,'0.72秒到','非0.72秒到')" +
				"                        when t.cashmode = 1 then '理财'" +
				"                        when t.cashmode = 5 then '扫码'" +
				"                      end) settmethod, t.*" +
				"          from check_cash_" + month + " t" +
				"         where t.cashmode in (1,4,5) ";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if ("110000".equals(userBean.getUnNo())) {
		}
		//为总公司并且是部门---查询全部
		else if (unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0) {
			UnitModel parent = unitModel.getParent();
			if ("110000".equals(parent.getUnNo())) {
			}
		} else {
			String childUnno = merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
//			sql+=" and t.unno IN ("+childUnno+") ";
			sql += " and t.unno IN (" + childUnno + ") ";
		}

		if (toolDealBean.getMid() != null && !"".equals(toolDealBean.getMid())) {
			sql += " and t.mid = '" + toolDealBean.getMid() + "' ";
		}
//		sql += ")t1, (select * from check_cash a where a.cashmode in(1,4,5) " ;
		if (toolDealBean.getTxnDay() == null || toolDealBean.getTxnDay1() == null || toolDealBean.getTxnDay().equals("") || toolDealBean.getTxnDay1().equals("")) {
			return null;
		} else {
//	    	sql += " and a.cashday between to_char(to_date( '"+ toolDealBean.getTxnDay()+"','yyyy/MM/dd'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+"','yyyy/MM/dd'),'yyyyMMdd') ";
			sql += " and t.cashday between '" + start + "' and '" + end + "' ";
		}
//		sql += ")a1, sys_unit s  where t1.mid = a1.mid and s.unno= t1.UNNO" ;
//		<option value="">全部</option>
//		<option value="2">理财</option>
//		<option value="3">传统</option>
//		<option value="4">云闪付</option>
		Integer isM35 = toolDealBean.getIsM35();
		if (isM35 != null) {
//			if (0==isM35) { //0.72秒到
//				sql += " and t1.ism35 = '1' and t1.settmethod='100000' and t1.BANKFEERATE = 0.0072 and a1.cashmode != '5'";
//			}else if (1==isM35) { //非0.72秒到
//				sql += " and t1.ism35 = '1' and t1.settmethod='100000'  and t1.BANKFEERATE < 0.0072 and a1.cashmode != '5'";
//			}else if (2==isM35) { //理财
//				sql += "  and t1.ism35 = '1' and t1.settmethod='000000' and a1.cashmode != '5'";
//			}else if (3==isM35) { //传统
//				sql += " and t1.ism35 != '1' and a1.cashmode != '5' ";
//			}else if(4==isM35){
//				sql += " and a1.cashmode = '5' ";
//			}
			if (2 == isM35) { //理财
				sql += " and t.cashmode = 1 ";
			} else if (3 == isM35) { //传统
				sql += " and t.mertype = 4 ";
			} else if (4 == isM35) {
				sql += " and t.cashmode = '-1' ";
			}
		}
//		sql+=")group by settmethod, unname, unno,mid,rname,secondrate ";
		sql += " )group by settmethod, unno,un_name, mid, rname, secondrate ";
		return sql;
	}

	/**
	 *  一级代理-收银台提现转账分润明细
	 * @param toolDealBean
	 * @param userBean
	 * @return
	 */
    @Override
    public DataGridBean queryFirstAgentTransferAndGetCashProfitDetailSyt(ToolDealBean toolDealBean, UserBean userBean) {
		// @author:lxg-20190429 代理商分润转账 收银台明细修改为新表
        DataGridBean dataGridBean = new DataGridBean();
//		String sql = "select to_char(sum(nvl(cashamt, 0)),'FM999,999,999,999,990.00') as cashamt," +
//				" count(cashamt) as count,to_char(sum(nvl(profit, 0)),'FM999,999,999,999,990.00') as profit," +
//				" unno, unname,secondrate,mid,mrname "+
//				" from (select a1.profit, s.unno, t1.rname, a1.cashamt cashamt,s.un_name as unname,t1.mid,t1.rname as mrname,t1.secondrate "+
//				"from (select * from bill_merchantinfo t where 1=1 " ;
		String month=null;
		String start = StringUtils.isNotEmpty(toolDealBean.getTxnDay())?toolDealBean.getTxnDay().replaceAll("-",""):"";
		String end = StringUtils.isNotEmpty(toolDealBean.getTxnDay1())?toolDealBean.getTxnDay1().replaceAll("-",""):"";
		if(StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)){
			if(start.substring(0,6).equals(end.substring(0,6))){
				month=start.substring(4,6);
			}else{
				return dataGridBean;
			}
		}else{
			return dataGridBean;
		}
		String sql = getYidaiSytCashDetailSql(toolDealBean, userBean, month, start, end);
		if (sql == null) return dataGridBean;

		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS,to_char(nvl(sum(replace(cashamt,',','')),0),'FM999,999,999,999,990.00') as cashamt ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") ";
        List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
        List proList = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, null, toolDealBean.getPage(),toolDealBean.getRows());
        Map<String,String> mapS=new HashMap<String, String>();
        Integer count=0;
        Map m=(Map)porListSum.get(0);
        count=Integer.parseInt(m.get("COUNT").toString());
        mapS.put("CASHAMT", m.get("CASHAMT").toString());
        mapS.put("PROFIT", m.get("PROFIT").toString());
        mapS.put("COUNT", m.get("COUNTS").toString());
        proList.add(0, mapS);
        dataGridBean.setTotal(count);
        dataGridBean.setRows(proList);
        return dataGridBean;
    }

	private String getYidaiSytCashDetailSql(ToolDealBean toolDealBean, UserBean userBean, String month, String start, String end) {
		String sql = "select to_char(sum(nvl(cashamt, 0)), 'FM999,999,999,999,990.00') as cashamt," +
				"       count(cashamt) as count," +
				"       to_char(sum(nvl(profit, 0)), 'FM999,999,999,999,990.00') as profit," +
				"       yidai unno,(select kk.un_name from sys_unit kk where kk.unno=yidai) unname,mid,rname mrname,secondrate,SETTMETHOD,mertype rebatetype" +
				"  from (select t.*,nvl((select s.unno from sys_unit s where s.un_lvl = 2 " +
				"              start with s.unno = t.UNNO connect by NOCYCLE s.unno = prior s.upper_unit and rownum = 1),t.unno) yidai," +
				
				" case when t.txntype = 1 and t.cashday < 20191201 then '微信0.38'"+
				" when t.txntype = 2 and t.cashday < 20191201 then '微信0.45'"+
				" when t.txntype = 3 and t.cashday < 20191201 then '微信（老）'"+
				" when t.txntype = 4 and t.cashday < 20191201 then '支付宝'"+
				" when t.txntype = 5 and t.cashday < 20191201 then '二维码'"+
				" when t.txntype = 1 and t.cashday >= 20191201 then '扫码1000以上（终端0.38）'"+
				" when t.txntype = 2 and t.cashday >= 20191201 then '扫码1000以上（终端0.45）'"+
				" when t.txntype = 3 and t.cashday >= 20191201 then '扫码1000以下（终端0.38）'"+
				" when t.txntype = 4 and t.cashday >= 20191201 then '扫码1000以下（终端0.45）'"+
				" when t.txntype = 5 and t.cashday >= 20191201 then '银联二维码'"+
				" when t.txntype = 6 then '花呗'"+
				" else '其他' end as SETTMETHOD "+
				"          from check_cash_" + month + " t" +
				"         where t.cashmode = 6 ";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if ("110000".equals(userBean.getUnNo())) {
		}
		//为总公司并且是部门---查询全部
		else if (unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0) {
			UnitModel parent = unitModel.getParent();
			if ("110000".equals(parent.getUnNo())) {
			}
		} else {
			String childUnno = merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			sql += " and t.unno IN (" + childUnno + ") ";
		}
		
		if(StringUtils.isNotEmpty(toolDealBean.getMaintainType())) {
			sql += " and t.mertype = " + toolDealBean.getMaintainType() ;
		}

		if (toolDealBean.getTxnDay() == null || toolDealBean.getTxnDay1() == null || toolDealBean.getTxnDay().equals("") || toolDealBean.getTxnDay1().equals("")) {
			return null;
		} else {
			sql += " and t.cashday between '" + start + "' and '" + end + "' ";
		}
		if (StringUtils.isNotEmpty(toolDealBean.getMid())) {
			sql += " and t.mid= '" + toolDealBean.getMid() + "' ";
		}
		sql += " )group by yidai, mid, rname, secondrate,SETTMETHOD,mertype ";
		return sql;
	}

	@Override
	public List<Map<String, String>>exportFirstAgentTransferAndGetCashProfitDetail(ToolDealBean toolDealBean, UserBean userBean) {
//		String sql = "select to_char(sum(nvl(cashamt, 0)),'FM999,999,999,999,990.00') as cashamt,count(cashamt) as count,to_char(sum(nvl(profit, 0)),'FM999,999,999,999,990.00') as profit, unno, unname, settmethod,mid,rname,secondrate "+
//			     "from (select decode(a1.cashmode,'5','扫码', decode(t1.ism35,'1', decode(t1.settmethod, "+
//			     "'000000','理财', case when t1.BANKFEERATE != 0.0072 then '非0.72秒到' else '0.72秒到'end), "+
//			     "'传统')) as settmethod,  a1.profit, s.unno,t1.mid,t1.rname, a1.cashamt cashamt,s.un_name as unname,t1.secondrate "+
//			     "from (select * from bill_merchantinfo t where 1=1 " ;
//		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
//		//为总公司
//		if("110000".equals(userBean.getUnNo())){
//		}
//		//为总公司并且是部门---查询全部
//		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
//		UnitModel parent = unitModel.getParent();
//		if("110000".equals(parent.getUnNo())){
//		}
//		}else{
//		String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
//		sql+=" and t.unno IN ("+childUnno+") ";
//		}
//
//		if(toolDealBean.getMid() != null &&!"".equals(toolDealBean.getMid())){
//		sql += " and t.mid = '"+toolDealBean.getMid()+"' ";
//		}
//		sql += ")t1, (select * from check_cash a where a.cashmode in(1,4,5) " ;
//		if(toolDealBean.getTxnDay()==null||toolDealBean.getTxnDay1()==null||toolDealBean.getTxnDay().equals("")||toolDealBean.getTxnDay1().equals("")){
//			return null;
//		}else{
//			sql += " and a.cashday between to_char(to_date( '"+ toolDealBean.getTxnDay()+"','yyyy/MM/dd'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+"','yyyy/MM/dd'),'yyyyMMdd') ";
//		}
//		sql += ")a1, sys_unit s  where t1.mid = a1.mid and s.unno= t1.UNNO" ;
//		Integer isM35 = toolDealBean.getIsM35();
//		if (isM35!=null) {
//			if (0==isM35) { //0.72秒到
//				sql += " and t1.ism35 = '1' and t1.settmethod='100000' and t1.BANKFEERATE = 0.0072 and a1.cashmode != '5'";
//			}else if (1==isM35) { //非0.72秒到
//				sql += " and t1.ism35 = '1' and t1.settmethod='100000'  and t1.BANKFEERATE < 0.0072 and a1.cashmode != '5'";
//			}else if (2==isM35) { //理财
//				sql += "  and t1.ism35 = '1' and t1.settmethod='000000' and a1.cashmode != '5'";
//			}else if (3==isM35) { //传统
//				sql += " and t1.ism35 != '1' and a1.cashmode != '5' ";
//			}else if(4==isM35){
//				sql += " and a1.cashmode = '5' ";
//			}
//		}
//		sql+=")group by settmethod, unname, unno,mid,rname,secondrate ";
//
//		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS,to_char(nvl(sum(replace(cashamt,',','')),0),'FM999,999,999,999,990.00') as cashamt ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") ";

		String start = StringUtils.isNotEmpty(toolDealBean.getTxnDay())?toolDealBean.getTxnDay().replaceAll("-",""):"";
		String end = StringUtils.isNotEmpty(toolDealBean.getTxnDay1())?toolDealBean.getTxnDay1().replaceAll("-",""):"";
		String month = getCashTableMonth(start, end);
		if (month == null) return null;
		String sql = getYidaiCashDetailSql(toolDealBean, userBean, month, start, end);
		if (sql == null) return null;

		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS,to_char(nvl(sum(replace(cashamt,',','')),0),'FM999,999,999,999,990.00') as cashamt ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") ";
		List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
		List proList = checkUnitDealDetailDao.queryObjectsBySqlList(sql);
		Map<String,String> mapS=new HashMap<String, String>();
		Integer count=0;
		Map m=(Map)porListSum.get(0);
		mapS.put("UNNO", "汇总");
		mapS.put("CASHAMT", m.get("CASHAMT").toString());
		mapS.put("PROFIT", m.get("PROFIT").toString());
		mapS.put("COUNT", m.get("COUNTS").toString());
		proList.add(0, mapS);
		return proList;
	}

	/**
	 * 收银台提现转账分润明细导出
	 * @param toolDealBean
	 * @param userBean
	 * @return
	 */
    @Override
    public List<Map<String, String>>exportFirstAgentTransferAndGetCashProfitDetailSyt(ToolDealBean toolDealBean, UserBean userBean) {
//		String sql = "select to_char(sum(nvl(cashamt, 0)),'FM999,999,999,999,990.00') as cashamt," +
//				" count(cashamt) as count,to_char(sum(nvl(profit, 0)),'FM999,999,999,999,990.00') as profit," +
//				" unno, unname,secondrate,mid,mrname "+
//				" from (select a1.profit, s.unno, t1.rname, a1.cashamt cashamt,s.un_name as unname,t1.mid,t1.rname as mrname,t1.secondrate "+
//				"from (select * from bill_merchantinfo t where 1=1 " ;
//		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
//		//为总公司
//		if("110000".equals(userBean.getUnNo())){
//		}
//		//为总公司并且是部门---查询全部
//		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
//			UnitModel parent = unitModel.getParent();
//			if("110000".equals(parent.getUnNo())){
//			}
//		}else{
//			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
//			sql+=" and t.unno IN ("+childUnno+") ";
//		}
//
//		sql += ")t1, (select * from check_cash a where a.cashmode = 6 " ;
//		if(toolDealBean.getTxnDay()==null||toolDealBean.getTxnDay1()==null||toolDealBean.getTxnDay().equals("")||toolDealBean.getTxnDay1().equals("")){
//			return null;
//		}else{
//			sql += " and a.cashday between to_char(to_date( '"+ toolDealBean.getTxnDay()+
//					" 00:00:00','yyyy-MM-dd hh24:mi:ss'),'yyyyMMdd') and to_char(to_date( '"+ toolDealBean.getTxnDay1()+
//					"','yyyy/MM/dd'),'yyyyMMdd') ";
//		}
//		sql += ")a1, sys_unit s  where t1.mid = a1.mid and  s.unno= nvl((select s.unno  from sys_unit s " +
//				" where s.un_lvl=2  start with s.unno = t1.UNNO  connect  by NOCYCLE s.unno = prior s.upper_unit and rownum = 1)," +
//				" t1.unno) ";
//		if(StringUtils.isNotEmpty(toolDealBean.getMid())){
//            sql += " and t1.mid= '"+toolDealBean.getMid()+"' ";
//        }
//		sql += " )group by unname, unno,secondrate,mid,mrname ";
//        String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS,to_char(nvl(sum(replace(cashamt,',','')),0),'FM999,999,999,999,990.00') as cashamt ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") ";
		String start = StringUtils.isNotEmpty(toolDealBean.getTxnDay())?toolDealBean.getTxnDay().replaceAll("-",""):"";
		String end = StringUtils.isNotEmpty(toolDealBean.getTxnDay1())?toolDealBean.getTxnDay1().replaceAll("-",""):"";
		String month = getCashTableMonth(start, end);
		if (month == null) return null;
		String sql = getYidaiSytCashDetailSql(toolDealBean, userBean, month, start, end);
		if (sql == null) return null;
		String sqlCount = "select nvl(count(*),0) count, nvl(sum(count),0) COUNTS,to_char(nvl(sum(replace(cashamt,',','')),0),'FM999,999,999,999,990.00') as cashamt ,to_char(nvl(sum(replace(PROFIT,',','')),0),'FM999,999,999,999,990.00') as profit from ("+sql+") ";
        List porListSum = checkUnitDealDetailDao.executeSql(sqlCount);
        List proList = checkUnitDealDetailDao.queryObjectsBySqlList(sql);
        Map<String,String> mapS=new HashMap<String, String>();
        Integer count=0;
        Map m=(Map)porListSum.get(0);
        mapS.put("UNNO", "汇总");
        mapS.put("CASHAMT", m.get("CASHAMT").toString());
        mapS.put("PROFIT", m.get("PROFIT").toString());
        mapS.put("COUNT", m.get("COUNTS").toString());
        proList.add(0, mapS);
        return proList;
    }
	@Override
	public DataGridBean listUnitDealDetail(ToolDealBean toolDealBean, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		//必须传日期，且日期必须是同年同月
//		if(toolDealBean.getTxnDay()==null||"".equals(toolDealBean.getTxnDay())||toolDealBean.getTxnDay1()==null||"".equals(toolDealBean.getTxnDay1())||!toolDealBean.getTxnDay().substring(0, 6).equals(toolDealBean.getTxnDay1().substring(0, 6))) {
//			Calendar cal=Calendar.getInstance();
//			cal.add(Calendar.DATE,-1);
//			Date time=cal.getTime();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			toolDealBean.setTxnDay(sdf.format(time));
//			toolDealBean.setTxnDay1(sdf.format(time));
//		}
//		//2018-10-10 转为 20181010
//		toolDealBean.setTxnDay(toolDealBean.getTxnDay().replace("-", ""));
//		toolDealBean.setTxnDay1(toolDealBean.getTxnDay1().replace("-", ""));
//		Map<String, Object> map = new HashMap<String,Object>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		//按照月份去查表check_unitdealdetail_xx
//		String sql = "select a.* from check_unitdealdetail_"+toolDealBean.getTxnDay().substring(4, 6)+" a where a.txnday >=:txnday and txnday <=:txnday1 ";
//		//销售
//		String sql1 = "";
//		map.put("txnday", toolDealBean.getTxnDay());
//		map.put("txnday1", toolDealBean.getTxnDay1());
//		if("110000".equals(user.getUnNo())) {//总公司权限
//		}else if(user.getUseLvl()==2||user.getUseLvl()==1){//业务人员
//			//销售
//			sql1 = sql +" and (a.unno in (select unno from sys_unit start with unno in (select unno from bill_agentunitinfo a "
//					+ "where a.signuserid = (select busid from bill_agentsalesinfo where user_id = "+user.getUserID()+" and MAINTAINTYPE != 'D') and unno != '"+user.getUnNo()+"') connect by prior unno = upper_unit)) ";
//			sql += " and (a.saleName=( select salename from bill_agentsalesinfo where user_id="+user.getUserID()+" and maintaintype!='D' ) and a.ismpos=1) ";
//		}else if(user.getUseLvl()==3) {//商户
//			//  @author:xuegangliu-20190304 查询子商户数据
//			sql+=" and a.mid in (select MID from  bill_MerchantInfo where  PARENTMID= :mid1 or MID =:mid1) ";
////			sql += " and a.mid=:mid1";
//			map.put("mid1", user.getLoginName());
//		}else if(user.getUseLvl()==0) {//代理商管理员
//			sql += " and a.unno in (select unno from sys_unit start with unno=:unno1 connect by prior unno = upper_unit) ";
//			map.put("unno1", user.getUnNo());
//		}
//		if(toolDealBean.getMid()!=null&&!"".equals(toolDealBean.getMid())) {
//			sql += " and a.mid=:mid ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.mid=:mid ";
//			}
//			map.put("mid", toolDealBean.getMid());
//		}
//		if(toolDealBean.getRname()!=null&&!"".equals(toolDealBean.getRname())) {
//			sql += " and a.rname=:rname ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.rname=:rname ";
//			}
//			map.put("rname", toolDealBean.getRname());
//		}
//		if(toolDealBean.getStan()!=null&&!"".equals(toolDealBean.getStan())) {
//			sql += " and a.stan=:stan ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.stan=:stan ";
//			}
//			map.put("stan", toolDealBean.getStan());
//		}
//		if(toolDealBean.getCardPan()!=null&&!"".equals(toolDealBean.getCardPan())) {
//			//卡号数据库是明文，返给前台是前六后四
//			sql += " and a.cardPan like '"+toolDealBean.getCardPan().substring(0,6)+"%"+toolDealBean.getCardPan().substring(toolDealBean.getCardPan().length()-4)+"' ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.cardPan like '"+toolDealBean.getCardPan().substring(0,6)+"%"+toolDealBean.getCardPan().substring(toolDealBean.getCardPan().length()-4)+"' ";
//			}
//		}
//		if(toolDealBean.getTid()!=null&&!"".equals(toolDealBean.getTid())) {
//			sql += " and a.tid=:tid ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.tid=:tid ";
//			}
//			map.put("tid", toolDealBean.getTid());
//		}
//		if(toolDealBean.getUnno()!=null&&!"".equals(toolDealBean.getUnno())) {
//			sql += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
//			}
//			map.put("unno", toolDealBean.getUnno());
//		}
//		if(toolDealBean.getSaleName()!=null&&!"".equals(toolDealBean.getSaleName())) {
//			sql += " and a.saleName=:saleName ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.saleName=:saleName ";
//			}
//			map.put("saleName", toolDealBean.getSaleName());
//		}
//		if(toolDealBean.getIsMpos()!=null) {
//			sql += " and a.ismpos=:ismpos ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.ismpos=:ismpos ";
//			}
//			map.put("ismpos", toolDealBean.getIsMpos());
//		}
//		if(toolDealBean.getType()!=null) {
//			sql += " and a.type=:type ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.type=:type ";
//			}
//			map.put("type", toolDealBean.getType());
//		}
//		if(toolDealBean.getMti()!=null) {
//			sql += " and a.mti=:mti ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.mti=:mti ";
//			}
//			map.put("mti", toolDealBean.getMti());
//		}
//		if(toolDealBean.getTxnState()!=null) {
//			sql += " and a.txnstate=:txnstate ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.txnstate=:txnstate ";
//			}
//			map.put("txnstate", toolDealBean.getTxnState());
//		}
//		if(!"".equals(sql1)) {
//			sql = "select * from ( "+sql+" union "+sql1+")";
//		}
		Map<String, Object> map = new HashMap<String,Object>();
		String sql = getListUnitDealDetailSql(toolDealBean,user,map);
		String sqlCount = "select count(1) from ("+sql+")";
		sql += " order by txnday desc,bdid desc ";
		List<Map<String,Object>> list = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		BigDecimal counts = checkUnitDealDetailDao.querysqlCounts(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}

	@Override
	public List<Map<String, Object>> listUnitDealDetailTotal(ToolDealBean toolDealBean, UserBean user) {
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
		String txnDay = toolDealBean.getTxnDay();
		String txnDay1 = toolDealBean.getTxnDay1();
		toolDealBean.setTxnDay(toolDealBean.getTxnDay().replace("-", ""));
		toolDealBean.setTxnDay1(toolDealBean.getTxnDay1().replace("-", ""));
		Map<String, Object> map = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//按照月份去查表check_unitdealdetail_xx
		String sql = "select nvl(to_char(count(1),'FM999,999,999,999,990'),0) totalNum,nvl(to_char(sum(txnamount),'FM999,999,999,999,990.00'),0) totalAmount from check_unitdealdetail_"+toolDealBean.getTxnDay().substring(4, 6)+" a where a.txnday >=:txnday and txnday <=:txnday1 ";
		//销售
		String sql1 = "";
		map.put("txnday", toolDealBean.getTxnDay());
		map.put("txnday1", toolDealBean.getTxnDay1());
		if("110000".equals(user.getUnNo())) {//总公司权限
		}else if(user.getUseLvl()==2||user.getUseLvl()==1){//业务人员
			sql = "SELECT nvl(COUNT(1), 0) AS totalNum , nvl(SUM(txnamount), 0) AS totalAmount FROM check_unitdealdetail_"+toolDealBean.getTxnDay().substring(4, 6)+" a "
				+ "WHERE a.txnday >= :txnday AND txnday <= :txnday1 AND (a.saleName = ( SELECT salename FROM bill_agentsalesinfo WHERE user_id = "+user.getUserID()+" AND maintaintype != 'D' ) AND a.ismpos = 1) ";
			sql1 = "SELECT nvl(COUNT(1), 0) AS totalNum , nvl(SUM(txnamount), 0) AS totalAmount FROM check_unitdealdetail_"+toolDealBean.getTxnDay().substring(4, 6)+" a "
				+ "WHERE a.txnday >= :txnday AND txnday <= :txnday1 AND a.unno IN ( SELECT unno FROM sys_unit START WITH unno IN ( "
				+ "SELECT unno FROM bill_agentunitinfo a WHERE a.signuserid = ( SELECT busid FROM bill_agentsalesinfo WHERE user_id "
				+ "= "+user.getUserID()+" AND MAINTAINTYPE != 'D' ) AND unno != '"+user.getUnNo()+"' ) CONNECT BY PRIOR unno = upper_unit ) ";
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
		if(toolDealBean.getStan()!=null&&!"".equals(toolDealBean.getStan())) {
			sql += " and a.stan=:stan ";
			if(!"".equals(sql1)) {
				sql1 += " and a.stan=:stan ";
			}
			map.put("stan", toolDealBean.getStan());
		}
		if(toolDealBean.getCardPan()!=null&&!"".equals(toolDealBean.getCardPan())) {
			sql += " and a.cardPan=:cardPan ";
			if(!"".equals(sql1)) {
				sql1 += " and a.cardPan=:cardPan ";
			}
			map.put("cardPan", toolDealBean.getCardPan());
		}
		if(toolDealBean.getTid()!=null&&!"".equals(toolDealBean.getTid())) {
			sql += " and a.tid=:tid ";
			if(!"".equals(sql1)) {
				sql1 += " and a.tid=:tid ";
			}
			map.put("tid", toolDealBean.getTid());
		}
		if(toolDealBean.getUnno()!=null&&!"".equals(toolDealBean.getUnno())) {
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			if(!"".equals(sql1)) {
				sql1 += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			}
			map.put("unno", toolDealBean.getUnno());
		}
		if(toolDealBean.getSaleName()!=null&&!"".equals(toolDealBean.getSaleName())) {
			sql += " and a.saleName=:saleName ";
			if(!"".equals(sql1)) {
				sql1 += " and a.saleName=:saleName ";
			}
			map.put("saleName", toolDealBean.getSaleName());
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
		sql += " and a.mti=:mti ";
		if(!"".equals(sql1)) {
			sql1 += " and a.mti=:mti ";
		}
		map.put("mti", 0);
		if(toolDealBean.getTxnState()!=null) {
			sql += " and a.txnstate=:txnstate ";
			if(!"".equals(sql1)) {
				sql1 += " and a.txnstate=:txnstate ";
			}
			map.put("txnstate", toolDealBean.getTxnState());
		}
		if(!"".equals(sql1)) {
			sql = "select nvl(to_char(sum(totalNum),'FM999,999,999,999,990'),0) totalNum,nvl(to_char(sum(totalAmount),'FM999,999,999,999,990.00'),0) totalAmount  from ("+sql+" union "+sql1+")";
		}
		list = checkUnitDealDetailDao.queryObjectsBySqlListMap2(sql, map);
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
	public List<Map<String, Object>> exportUnitDealDetail(ToolDealBean toolDealBean, UserBean user) {
		List<Map<String, Object>> list = null;
		//必须传日期，且日期必须是同年同月
//		if(toolDealBean.getTxnDay()==null||"".equals(toolDealBean.getTxnDay())||toolDealBean.getTxnDay1()==null||"".equals(toolDealBean.getTxnDay1())||!toolDealBean.getTxnDay().substring(0, 6).equals(toolDealBean.getTxnDay1().substring(0, 6))) {
//			Calendar cal=Calendar.getInstance();
//			cal.add(Calendar.DATE,-1);
//			Date time=cal.getTime();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			toolDealBean.setTxnDay(sdf.format(time));
//			toolDealBean.setTxnDay1(sdf.format(time));
//		}
//		//2018-10-10 转为 20181010
//		toolDealBean.setTxnDay(toolDealBean.getTxnDay().replace("-", ""));
//		toolDealBean.setTxnDay1(toolDealBean.getTxnDay1().replace("-", ""));
//		Map<String, Object> map = new HashMap<String,Object>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		//按照月份去查表check_unitdealdetail_xx
//		String sql = "select a.* from check_unitdealdetail_"+toolDealBean.getTxnDay().substring(4, 6)+" a where a.txnday >=:txnday and txnday <=:txnday1 ";
//		//销售
//		String sql1 = "";
//		map.put("txnday", toolDealBean.getTxnDay());
//		map.put("txnday1", toolDealBean.getTxnDay1());
//		if("110000".equals(user.getUnNo())) {//总公司权限
//		}else if(user.getUseLvl()==2||user.getUseLvl()==1){//业务人员
//			//销售
//			sql1 = sql +" and (a.unno in (select unno from sys_unit start with unno in (select unno from bill_agentunitinfo a "
//					+ "where a.signuserid = (select busid from bill_agentsalesinfo where user_id = "+user.getUserID()+" and MAINTAINTYPE != 'D') and unno != '"+user.getUnNo()+"') connect by prior unno = upper_unit)) ";
//			sql += " and (a.saleName=( select salename from bill_agentsalesinfo where user_id="+user.getUserID()+" and maintaintype!='D' ) and a.ismpos=1) ";
//		}else if(user.getUseLvl()==3) {//商户
//			//  @author:xuegangliu-20190304 查询子商户数据
//			sql+=" and a.mid in (select MID from  bill_MerchantInfo where  PARENTMID= :mid1 or MID =:mid1) ";
////			sql += " and a.mid=:mid1";
//			map.put("mid1", user.getLoginName());
//		}else if(user.getUseLvl()==0) {//代理商管理员
//			sql += " and a.unno in (select unno from sys_unit start with unno=:unno1 connect by prior unno = upper_unit) ";
//			map.put("unno1", user.getUnNo());
//		}
//		if(toolDealBean.getMid()!=null&&!"".equals(toolDealBean.getMid())) {
//			sql += " and a.mid=:mid ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.mid=:mid ";
//			}
//			map.put("mid", toolDealBean.getMid());
//		}
//		if(toolDealBean.getRname()!=null&&!"".equals(toolDealBean.getRname())) {
//			sql += " and a.rname=:rname ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.rname=:rname ";
//			}
//			map.put("rname", toolDealBean.getRname());
//		}
//		if(toolDealBean.getStan()!=null&&!"".equals(toolDealBean.getStan())) {
//			sql += " and a.stan=:stan ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.stan=:stan ";
//			}
//			map.put("stan", toolDealBean.getStan());
//		}
//		if(toolDealBean.getCardPan()!=null&&!"".equals(toolDealBean.getCardPan())) {
//			sql += " and a.cardPan=:cardPan ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.cardPan=:cardPan ";
//			}
//			map.put("cardPan", toolDealBean.getCardPan());
//		}
//		if(toolDealBean.getTid()!=null&&!"".equals(toolDealBean.getTid())) {
//			sql += " and a.tid=:tid ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.tid=:tid ";
//			}
//			map.put("tid", toolDealBean.getTid());
//		}
//		if(toolDealBean.getUnno()!=null&&!"".equals(toolDealBean.getUnno())) {
//			sql += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
//			}
//			map.put("unno", toolDealBean.getUnno());
//		}
//		if(toolDealBean.getSaleName()!=null&&!"".equals(toolDealBean.getSaleName())) {
//			sql += " and a.saleName=:saleName ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.saleName=:saleName ";
//			}
//			map.put("saleName", toolDealBean.getSaleName());
//		}
//		if(toolDealBean.getIsMpos()!=null) {
//			sql += " and a.ismpos=:ismpos ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.ismpos=:ismpos ";
//			}
//			map.put("ismpos", toolDealBean.getIsMpos());
//		}
//		if(toolDealBean.getType()!=null) {
//			sql += " and a.type=:type ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.type=:type ";
//			}
//			map.put("type", toolDealBean.getType());
//		}
//		if(toolDealBean.getMti()!=null) {
//			sql += " and a.mti=:mti ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.mti=:mti ";
//			}
//			map.put("mti", toolDealBean.getMti());
//		}
//		if(toolDealBean.getTxnState()!=null) {
//			sql += " and a.txnstate=:txnstate ";
//			if(!"".equals(sql1)) {
//				sql1 += " and a.txnstate=:txnstate ";
//			}
//			map.put("txnstate", toolDealBean.getTxnState());
//		}
//		if(!"".equals(sql1)) {
//			sql = "select * from ("+sql+" union "+sql1+") ";
//		}
//		sql += " order by txnday desc ";
		Map<String, Object> map = new HashMap<String,Object>();
		String sql = getListUnitDealDetailSql(toolDealBean,user,map);
		sql += " order by txnday desc,bdid desc ";
		list = checkUnitDealDetailDao.queryObjectsBySqlListMap2(sql, map);
		return list;
	}

	private String getListUnitDealDetailSql(ToolDealBean toolDealBean,UserBean user,Map<String, Object> map){
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//按照月份去查表check_unitdealdetail_xx
		String sql = "select a.* from check_unitdealdetail_"+toolDealBean.getTxnDay().substring(4, 6)+" a where a.txnday >=:txnday and txnday <=:txnday1 ";
		//销售
		String sql1 = "";
		map.put("txnday", toolDealBean.getTxnDay());
		map.put("txnday1", toolDealBean.getTxnDay1());
		if("110000".equals(user.getUnNo())) {//总公司权限
		}else if(user.getUseLvl()==2||user.getUseLvl()==1){//业务人员
			//销售
			sql1 = sql +" and (a.unno in (select unno from sys_unit start with unno in (select unno from bill_agentunitinfo a "
					+ "where a.signuserid = (select busid from bill_agentsalesinfo where user_id = "+user.getUserID()+" and MAINTAINTYPE != 'D') and unno != '"+user.getUnNo()+"') connect by prior unno = upper_unit)) ";
			sql += " and (a.saleName=( select salename from bill_agentsalesinfo where user_id="+user.getUserID()+" and maintaintype!='D' ) and a.ismpos=1) ";
		}else if(user.getUseLvl()==3) {//商户
			//  @author:xuegangliu-20190304 查询子商户数据
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
		if(toolDealBean.getStan()!=null&&!"".equals(toolDealBean.getStan())) {
			sql += " and a.stan=:stan ";
			if(!"".equals(sql1)) {
				sql1 += " and a.stan=:stan ";
			}
			map.put("stan", toolDealBean.getStan());
		}
		if(toolDealBean.getCardPan()!=null&&!"".equals(toolDealBean.getCardPan())) {
			sql += " and a.cardPan=:cardPan ";
			if(!"".equals(sql1)) {
				sql1 += " and a.cardPan=:cardPan ";
			}
			map.put("cardPan", toolDealBean.getCardPan());
		}
		if(toolDealBean.getTid()!=null&&!"".equals(toolDealBean.getTid())) {
			sql += " and a.tid=:tid ";
			if(!"".equals(sql1)) {
				sql1 += " and a.tid=:tid ";
			}
			map.put("tid", toolDealBean.getTid());
		}
		if(toolDealBean.getUnno()!=null&&!"".equals(toolDealBean.getUnno())) {
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			if(!"".equals(sql1)) {
				sql1 += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit) ";
			}
			map.put("unno", toolDealBean.getUnno());
		}
		if(toolDealBean.getSaleName()!=null&&!"".equals(toolDealBean.getSaleName())) {
			sql += " and a.saleName=:saleName ";
			if(!"".equals(sql1)) {
				sql1 += " and a.saleName=:saleName ";
			}
			map.put("saleName", toolDealBean.getSaleName());
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
		if(toolDealBean.getMti()!=null) {
			sql += " and a.mti=:mti ";
			if(!"".equals(sql1)) {
				sql1 += " and a.mti=:mti ";
			}
			map.put("mti", toolDealBean.getMti());
		}
		if(toolDealBean.getTxnState()!=null) {
			sql += " and a.txnstate=:txnstate ";
			if(!"".equals(sql1)) {
				sql1 += " and a.txnstate=:txnstate ";
			}
			map.put("txnstate", toolDealBean.getTxnState());
		}
		if(!"".equals(sql1)) {
			sql = "select * from ("+sql+" union "+sql1+") ";
		}
//		sql += " order by txnday desc ";
    	return sql;
	}

	/**
	 * 一级分润-MPOS活动分润汇总
	 * @author yq
	 */
	@Override
	public DataGridBean queryMposProfit(ToolDealBean toolDealBean, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		if(toolDealBean.getTxnDay()==null||"".equals(toolDealBean.getTxnDay())||toolDealBean.getTxnDay1()==null||"".equals(toolDealBean.getTxnDay1())||!toolDealBean.getTxnDay().substring(0, 6).equals(toolDealBean.getTxnDay1().substring(0, 6))) {
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE,-1);
			Date time=cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			toolDealBean.setTxnDay(sdf.format(time));
			toolDealBean.setTxnDay1(sdf.format(time));
		}

		toolDealBean.setTxnDay(toolDealBean.getTxnDay().replace("-", ""));
		toolDealBean.setTxnDay1(toolDealBean.getTxnDay1().replace("-", ""));
		Map map = new HashMap();
		String sql = "select unno,un_name,minfo1,txnType,sum(txnamount) txnamount,sum(txncount) txncount,sum(mda) mda,sum(profit) profit from (" +
				"select s.unno,s.un_name,a.txnamount,a.txncount,a.mda,a.profit, a.minfo1,decode(mertype,4,4,1) as txnType " +
				" from pg_unno_txn a, sys_unit s " +
				" where a.unno = s.unno and s.un_lvl in (1, 2) " +
				" and a.isnew = 1 and a.txnday >= :txnday and a.txnday <= :txnday1 union all " +
				" select s.unno,s.un_name,b.txnamount,b.txncount,b.mda,b.profit,b.minfo1,(case when b.mertype=2 then 22 " +
				"                  when b.mertype=3 then 33 " +
				"                  when b.mertype=1 and b.minfo2='1' and b.txnday < 20191201 then 41 " +
				"                  when b.mertype=1 and b.minfo2='2' and b.txnday < 20191201 then 42 " +
				"                  when b.mertype=1 and b.minfo2='3' and b.txnday < 20191201 then 43 " +
				"                  when b.mertype=5 and b.minfo2='1' and b.txnday >= 20191201 then 44 " +
				"                  when b.mertype=5 and b.minfo2='2' and b.txnday >= 20191201 then 45 " +
				"                  when b.mertype=5 and b.minfo2='3' and b.txnday >= 20191201 then 46 " +
				"                  when b.mertype=5 and b.minfo2='4' and b.txnday >= 20191201 then 47 " +
				"                  when b.mertype=6 then 48 " +
				
				"                    end) txnType  " +
				" from pg_unno_wechat b, sys_unit s " +
				" where b.unno = s.unno and b.mertype!=4 " +
				" and s.un_lvl in (1, 2) and b.ismpos = 4 and b.txnday >= :txnday and b.txnday <= :txnday1 ) where 1=1 ";
		map.put("txnday", toolDealBean.getTxnDay());
		map.put("txnday1", toolDealBean.getTxnDay1());
		if("110000".equals(user.getUnNo())) {//总公司权限
		}else if(user.getUseLvl()==0) {//代理商管理员
			sql += " and unno=:unno  ";
			map.put("unno", user.getUnNo());
		}
		// 活动类型
		if(StringUtils.isNotEmpty(toolDealBean.getMaintainType())){
			sql+=" and minfo1 = :minfo1 ";
			map.put("minfo1", toolDealBean.getMaintainType());
		}

		// 交易类型
		if(StringUtils.isNotEmpty(toolDealBean.getTxnType())){
			sql += " and txnType = :txnType ";
			map.put("txnType", toolDealBean.getTxnType());
		}
		sql+=" group by unno,un_name,minfo1,txnType ";
		String sqlCount = "select count(1) from ("+sql+")";
		sql += " order by unno";
		List<Map<String,Object>> list = checkUnitDealDetailDao.queryObjectsBySqlList2(sql, map, toolDealBean.getPage(), toolDealBean.getRows());
		BigDecimal counts = checkUnitDealDetailDao.querysqlCounts(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}
	
	/**
	 * 一级分润-MPOS活动分润汇总导出
	 * @author yq
	 */
	@Override
	public List<Map<String, Object>> exportMposProfit(ToolDealBean toolDealBean, UserBean user) {
		if(toolDealBean.getTxnDay()==null||"".equals(toolDealBean.getTxnDay())||toolDealBean.getTxnDay1()==null||"".equals(toolDealBean.getTxnDay1())||!toolDealBean.getTxnDay().substring(0, 6).equals(toolDealBean.getTxnDay1().substring(0, 6))) {
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE,-1);
			Date time=cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			toolDealBean.setTxnDay(sdf.format(time));
			toolDealBean.setTxnDay1(sdf.format(time));
		}
		toolDealBean.setTxnDay(toolDealBean.getTxnDay().replace("-", ""));
		toolDealBean.setTxnDay1(toolDealBean.getTxnDay1().replace("-", ""));
		Map map = new HashMap();
		String weChatSql="";
		String sql = "select unno,un_name,minfo1," +
				" decode(txnType,1,'刷卡',4,'云闪付',22,'支付宝',33,'银联二维码',41,'微信0.38',42,'微信0.45',43,'微信(老)',44,'扫码1000以上(终端0.38)',45,'扫码1000以上(终端0.45)',46,'扫码1000以下(终端0.38)',47,'扫码1000以下(终端0.45)',48,'花呗',"+
				"'扫码') txnType,sum(txnamount) txnamount,sum(txncount) txncount,sum(mda) mda,sum(profit) profit from (" +
				"select s.unno,s.un_name,a.txnamount,a.txncount,a.mda,a.profit, a.minfo1,decode(mertype,4,4,1) as txnType " +
				" from pg_unno_txn a, sys_unit s " +
				" where a.unno = s.unno and s.un_lvl in (1, 2) " +
				" and a.isnew = 1 and a.txnday >= :txnday and a.txnday <= :txnday1 union all " +
				" select s.unno,s.un_name,b.txnamount,b.txncount,b.mda,b.profit,b.minfo1,(case when b.mertype=2 and b.txnday < 20191201 then 22 " +
				"                  when b.mertype=3 then 33 " +
				"                  when b.mertype=1 and b.minfo2='1' and b.txnday < 20191201 then 41 " +
				"                  when b.mertype=1 and b.minfo2='2' and b.txnday < 20191201 then 42 " +
				"                  when b.mertype=1 and b.minfo2='3' and b.txnday < 20191201 then 43 " +
				"                  when b.mertype=5 and b.minfo2='1' and b.txnday >= 20191201 then 44 " +
				"                  when b.mertype=5 and b.minfo2='2' and b.txnday >= 20191201 then 45 " +
				"                  when b.mertype=5 and b.minfo2='3' and b.txnday >= 20191201 then 46 " +
				"                  when b.mertype=5 and b.minfo2='4' and b.txnday >= 20191201 then 47 " +
				"                  when b.mertype=6 then 48 " +
				"                    end) txnType  " +
				" from pg_unno_wechat b, sys_unit s " +
				" where b.unno = s.unno and b.mertype!=4 " +
				" and s.un_lvl in (1, 2) and b.ismpos = 4 and b.txnday >= :txnday and b.txnday <= :txnday1 ) where 1=1 ";
		map.put("txnday", toolDealBean.getTxnDay());
		map.put("txnday1", toolDealBean.getTxnDay1());

		if("110000".equals(user.getUnNo())) {//总公司权限
		}else if(user.getUseLvl()==0) {//代理商管理员
			sql += " and unno=:unno  ";
			map.put("unno", user.getUnNo());
		}
		if(StringUtils.isNotEmpty(toolDealBean.getMaintainType())){
			sql+=" and minfo1 = :minfo1 ";
			map.put("minfo1", toolDealBean.getMaintainType());
		}

		if(StringUtils.isNotEmpty(toolDealBean.getTxnType())){
			sql += " and txnType = :txnType ";
			map.put("txnType", toolDealBean.getTxnType());
		}
		sql+=" group by unno,un_name,minfo1,txnType ";
		return checkUnitDealDetailDao.executeSql2(sql,map);
	}
	
	
	private String getCheckUnitTxnSumByLimitSql(ToolDealBean toolDealBean,
			UserBean userSession,
			Map<String ,Object> map) {
		String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		Map<String, Object> usermap = new HashMap<String, Object>();
		usermap.put("userID", userSession.getUserID().toString());
		usermap.put("maintainType", "D");
		List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		
		String sql = " SELECT a.*,(s1+s4) jinezong,(s2+s5) bishuzong,(s3+s6) huoyuezong"+
				" FROM (SELECT unno,agentname,sum(shuaka) s1,sum(shuakabishu) s2,"+
				" sum(shuakahuoyue) s3,sum(saoma) s4,sum(saomabishu) s5,sum(saomahuoyue) s6"+
				" FROM (SELECT t1.unno,t1.agentname,0 shuaka,0 shuakabishu,0 shuakahuoyue,t.sumamt saoma,t.txncount saomabishu,t.activemerch saomahuoyue"+
				" FROM check_UnnoTxnSum t,bill_agentunitinfo t1"+
				" where 1=1 and t.unno = t1.unno and t.type = 8";
				
		if(toolDealBean.getTxnDay1()!=null && !"".equals(toolDealBean.getTxnDay1())
				&& toolDealBean.getTxnDay()!=null && !"".equals(toolDealBean.getTxnDay())){
			 sql +=" and t.txnday>= :TXNDAY ";
		     sql +=" and t.txnday<=:TXNDAY1";
		     map.put("TXNDAY", toolDealBean.getTxnDay().replace("-", ""));
		     map.put("TXNDAY1", toolDealBean.getTxnDay1().replace("-", ""));
		}
				
		sql+=" union all"+
				" SELECT t1.unno,t1.agentname,t.sumamt shuaka,t.txncount shuakabishu,t.activemerch shuakahuoyue,0 saoma,0 saomabishu,0 saomahuoyue"+
				" FROM check_UnnoTxnSum t,bill_agentunitinfo t1"+
				" where 1=1 and t.unno = t1.unno";
		  
	    if(toolDealBean.getTxnDay1()!=null && !"".equals(toolDealBean.getTxnDay1())
	    		&& toolDealBean.getTxnDay()!=null && !"".equals(toolDealBean.getTxnDay())){
	    	 sql +=" and t.txnday>= :TXNDAY ";
		     sql +=" and t.txnday<=:TXNDAY1";
		     map.put("TXNDAY", toolDealBean.getTxnDay().replace("-", ""));
		     map.put("TXNDAY1", toolDealBean.getTxnDay1().replace("-", ""));
	    }	
		
	    sql+=" and t.type is null)"+
			 " group by unno,agentname)a where 1=1";
		 
		  if(userSession.getUnitLvl()==0){
		 }else if(userSession.getUnitLvl()==1){
			sql+=" and a.unno in(select unno from sys_unit where upper_unit =:PARENT or unno=:PARENT) ";
			 map.put("PARENT", userSession.getUnNo());
		 }else{
			 sql+="  and a.unno in ("+userSession.getUnNo()+") ";
		 }

		if(toolDealBean.getUnitName()!=null && !"".equals(toolDealBean.getUnitName())){
			sql+=" and a.agentname like :UNAME ";

			map.put("UNAME", "%"+toolDealBean.getUnitName()+"%");
		}
		if(toolDealBean.getUnNo() !=null && !"".equals(toolDealBean.getUnNo())){
			sql+=" and a.unno=:UNNO ";
			 map.put("UNNO", toolDealBean.getUnNo());
		}
		
//		if(toolDealBean.getSort()!=null && !"".equals(toolDealBean.getSort()) && toolDealBean.getOrder()!=null && !"".equals(toolDealBean.getOrder())){
//			sql +=" order by "+toolDealBean.getSort()+" "+toolDealBean.getOrder();
//		}else{
//			sql+=" order by sumamt desc";
//		}
		
		return sql;
	}

	@Override
	public List<Map<String, Object>> exportCheckUnitTxnSumDataByLimit(ToolDealBean toolDealBean, UserBean userSession) {
		Map<String ,Object> map = new HashMap<String, Object>();
		String sql = getCheckUnitTxnSumByLimitSql(toolDealBean,userSession,map);
		return checkUnitDealDetailDao.executeSql2(sql,map);
	}
	
}
