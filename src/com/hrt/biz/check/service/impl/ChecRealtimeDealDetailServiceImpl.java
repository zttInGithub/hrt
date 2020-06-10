package com.hrt.biz.check.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.check.dao.ChecRealtimeDealDetailDao;
import com.hrt.biz.check.entity.pagebean.CheckRealtimeDealDetailBean;
import com.hrt.biz.check.service.ChecRealtimeDealDetailService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.gmms.webservice.IGmmsService;

public class ChecRealtimeDealDetailServiceImpl implements ChecRealtimeDealDetailService {

	 private ChecRealtimeDealDetailDao checRealtimeDealDetailDao;
	 private IMerchantInfoDao merchantInfoDao;
	 private IUnitDao unitDao;
	 private IMerchantInfoService merchantInfoService; 
	 
	 public IMerchantInfoDao getMerchantInfoDao() {
		return merchantInfoDao;
	}
	public void setMerchantInfoDao(IMerchantInfoDao merchantInfoDao) {
		this.merchantInfoDao = merchantInfoDao;
	}
	public ChecRealtimeDealDetailDao getChecRealtimeDealDetailDao() {
		return checRealtimeDealDetailDao;
	}
	public void setChecRealtimeDealDetailDao(
			ChecRealtimeDealDetailDao checRealtimeDealDetailDao) {
		this.checRealtimeDealDetailDao = checRealtimeDealDetailDao;
	}
	public IUnitDao getUnitDao() {
		return unitDao;
	}
	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}
	private IGmmsService gsClient;
	    
	public IGmmsService getGsClient() {
		return gsClient;
	}
	public void setGsClient(IGmmsService gsClient) {
		this.gsClient = gsClient;
	}
	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}
	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}
	/*
	 * 实时交易跟踪
	 * 
	 */
//   private int count=0;
//	public String savequeryTrade(String mid,String liu,String tid,String cardNum,String jj,UserBean user) {
//		//判断用户可以查询哪些mid
//		Map<String, Object> map = new HashMap<String, Object>();
//		String ss="";
//		String sqls=null;
//	
//		if(user.getUnNo().equals("110000")){
//			map.put("MID", mid);
//			 sqls="select *  from BILL_MERCHANTINFO where MID= :MID";
//			count++;
//		}else{
//			map.put("MID", mid);
//			map.put("UNNO", user.getUnNo());
//		  sqls="select * from BILL_MERCHANTINFO where MID= :MID and UNNO= :UNNO";
//		 count++;
//		}
//		List<MerchantInfoModel> list=merchantInfoDao.executeSqlT(sqls, MerchantInfoModel.class, map);
//		map=null;
//		List<MerchantInfoModel> list2=merchantInfoDao.executeSqlT("select * from BILL_MERCHANTINFO where 1=1 ", MerchantInfoModel.class, map);
//		if(list2.get(0).getRealtimeQueryTimes()<count){
//			return ss;
//		}
//		if(list.size()>0){
//			ss=gsClient.realQuery(mid,liu, tid, cardNum, jj);
//		
//			CheckRealtimeDealDetailModel crm=new CheckRealtimeDealDetailModel();
//			JSONArray js=JSONArray.parseArray(ss);
//			for(int i=0;i<js.size();i++){
//				//crm.setBrdid(2);
//				crm.setMid(js.getJSONObject(i).get("BIT42_HRT").toString());
//				crm.setTid(js.getJSONObject(i).get("BIT41_HRT").toString());
//				crm.setCardpan(js.getJSONObject(i).get("CARDPAN").toString());
//				crm.setTxnamount(Double.parseDouble(js.getJSONObject(i).get("BIT4").toString()));
//				crm.setTxnday(js.getJSONObject(i).get("BIT13").toString());
//				crm.setTxndate(js.getJSONObject(i).get("BIT12").toString());
//				crm.setTxntype(js.getJSONObject(i).get("MTI").toString());
//				crm.setAuthcode(js.getJSONObject(i).get("BIT38").toString());
//				crm.setStan(js.getJSONObject(i).get("BIT11").toString());
//				crm.setRrn(js.getJSONObject(i).get("BIT37").toString());
//				crm.setRespcode("0");
//				crm.setMaintainuid(user.getUserID());
//				crm.setMaintaindate(new Date());
//				crm.setMaintaintype("M");
//				
//				System.out.println(crm.getAuthcode()+"  "+crm.getCardpan()+"  "+crm.getMaintaintype()+"  "+crm.getMid()+"  "+crm.getRespcode()+"  "+crm.getRrn()+"  "+crm.getStan()+"  "+crm.getTid()+"  "+crm.getTxnamount()+"  "+crm.getTxndate()+"  "+crm.getTxnday()+"  "+crm.getTxntype()+"  "+crm.getMaintainuid()+"  "+crm.getMaintaindate());
//				checRealtimeDealDetailDao.saveObject(crm);
//			}
//			
//			}
//			//return ss;
//		return null;
//		
//	}

	@Override
	public DataGridBean queryRealTimeData(CheckRealtimeDealDetailBean crm,UserBean user,int index) {
		DataGridBean dgb = new DataGridBean();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		Map<String, Object> map = new HashMap<String, Object>();
		String sqls="select t.pkid,t.mid,t.tid, " +
					" substr(substr(t.cardpan, 3, length(t.cardpan)), 1, 6) ||'******'||" +
					" substr(substr(t.cardpan, 3, length(t.cardpan)),-4,4)  cardpan," +
					" CAST(CAST(t.txnamount AS DECIMAL(18,2))/100 AS DECIMAL(18,2)) as txnamount," +
					" t.txnday,t.txndate,t.txntype,t.authcode,t.stan ,t.rrn ,t.proccode ,t.respcode ,m.rname" +
					" from CHECK_REALTIMEDEALDETAIL t ";
		if(user.getUnNo().equals("110000")){
			//总公司
			 sqls += " ,bill_merchantinfo m  where t.MID=m.MID and 1=1";
		}
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			//总公司部门
			 sqls += " ,bill_merchantinfo m  where t.MID=m.MID and 1=1";
		}
		else{
			String childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
			sqls += " ,bill_merchantinfo m  where t.MID=m.MID and m.UNNO IN ("+childUnno+") ";
		}
//		else if (unitModel.getUnLvl() == 1){
//			//分公司
//			sqls += " ,bill_merchantinfo m  where t.MID=m.MID and m.UNNO IN (Select UNNO FROM SYS_UNIT WHERE UPPER_UNIT IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT =:UNNO OR UNNO=:UNNO ) OR UNNO=:UNNO)";
//			map.put("UNNO", user.getUnNo());
//		}else if (unitModel.getUnLvl() == 2){
//			//一级代理商
//			sqls += " ,bill_merchantinfo m  where t.MID=m.MID and m.UNNO in (select unno from sys_unit s where s.UNNO=:UNNO or upper_unit=:UNNO)";
//			map.put("UNNO", user.getUnNo());
//		}else{
//			//二级代理商
//		  sqls += " ,bill_merchantinfo m  where t.MID=m.MID and m.UNNO=:UNNO";
//			map.put("UNNO", user.getUnNo());
//		}
		//index为2，实时交易2,只查询结算周期为0
//		if("0".equals(user.getIsM35Type())){
//			sqls +=" and m.isM35 !=1 ";
//		}else if("1".equals(user.getIsM35Type())){
//			sqls +=" and m.isM35 = 1 ";
//		}
		if(index==2){
			sqls +=" and m.settlecycle=0";
		}
		
		if(crm.getMid()!=null && !"".equals(crm.getMid())){
			sqls += " and t.MID=:MID ";
			map.put("MID", crm.getMid());
		}
		 if(crm.getStan()!=null && !"".equals(crm.getStan())){
			sqls +=" and t.STAN=:STAN";
			map.put("STAN", crm.getStan());
		}
		 if(crm.getCardpan()!=null && !"".equals(crm.getCardpan())){
			sqls+=" and t.CARDPAN=:CARDPAN";
			map.put("CARDPAN", crm.getCardpan().length()+crm.getCardpan()+"");
		}
		 if(crm.getTid()!=null && !"".equals(crm.getTid())){
			sqls +=" and t.TID=:TID";
			map.put("TID", crm.getTid());
		}
		if(crm.getRname() !=null && !"".equals(crm.getRname())){
			sqls +=" and m.RNAME LIKE :RNAME";
			map.put("RNAME", crm.getRname()+'%');
		}
		 //index为2，实时交易2,起始时间条件设置
		 if(index==2){
		    if(crm.getTxnday()!=null&&!"".equals(crm.getTxnday())){
			   sqls +=" and t.TXNDAY>=to_char(to_date('"+crm.getTxnday()+"','yyyy-MM-dd'),'yyyyMMdd') ";
		    }
		    if(crm.getTxnday1()!=null&&!"".equals(crm.getTxnday1())){
			   sqls +=" and t.TXNDAY<=to_char(to_date('"+crm.getTxnday1()+"','yyyy-MM-dd'),'yyyyMMdd') ";
		    }
		 }
		 //若没有时间设置，实时交易跟踪与实时交易2都查询当天
		if(crm.getTxnday()==null||"".equals(crm.getTxnday())){
		    sqls+=" and t.txnday >=to_char(sysdate,'yyyyMMdd') ";
		}
		sqls+=" and trim(t.txnamount)!='null' ";
		if(crm.getTxntype()!=null && !"".equals(crm.getTxntype())){
			sqls+=" and t.txntype='"+crm.getTxntype()+"' ";
		}
		sqls+=" order by t.txnday,t.txndate desc ";
		String sqlCount="select count(*) from ("+sqls+")";
		Integer count = checRealtimeDealDetailDao.querysqlCounts2(sqlCount, map);
		List<Map<String,Object>> list=checRealtimeDealDetailDao.queryObjectsBySqlList2(sqls, map, crm.getPage(), crm.getRows());
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}
	
	
	@Override
	public DataGridBean queryRealTimeTop10(UserBean user) {
		DataGridBean dgb = new DataGridBean();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		Map<String, Object> map = new HashMap<String, Object>();
        String sqls=" select  T.mid, s.un_name, m.rname,T.txnday,nvl(sum(CAST(CAST(t.txnamount AS DECIMAL(18,2))/100 AS DECIMAL(18,2))),0) as TXNAMOUNT  " +
                " from check_realtxn T, bill_merchantinfo m, sys_unit s" +
                " where t.mid = m.mid" +
                " and m.unno = s.unno" +
                " and t.txntype in (0,5) " +
                " and txnday = to_char(sysdate, 'yyyyMMdd')";
		if(user.getUnNo().equals("110000")){
			//总公司
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			//总公司部门
		}else{
			String childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
			sqls += " and m.UNNO IN ("+childUnno+") ";
		
		}

		sqls+=" group by t.mid,s.un_name,t.txnday,m.rname " +
				" order by TXNAMOUNT desc";
		List<Map<String,Object>> list=checRealtimeDealDetailDao.queryObjectsBySqlList2(sqls, map, 0, 10);
		dgb.setTotal(list.size());
		dgb.setRows(list);
		return dgb;
	}
	public DataGridBean queryTodayDealDatas(CheckRealtimeDealDetailBean crm,UserBean userSession){
        String sql = " select  nvl(sum(TxnCount),0) totalTxnCount,to_char(nvl(SUM(TXNAMOUNT),0),'FM999,999,999,999,990.00') totalTxnAmount,COUNT(*) totalCount,'集团商户' as UNNOLVL from Bill_Merchantinfo a,sys_unit b,( "+
                " select t.mid,"+
                " sum(CAST(CAST(t.txnamount AS DECIMAL(18,2))/100 AS DECIMAL(18,2))) as txnamount , count(*)  TxnCount"+
                " from  check_realtxn t"+
                " where 1=1 "+
                " and t.txntype in (0,5) "+
                " and t.txnday = to_char(sysdate,'yyyyMMdd') "+
                " group by mid) c  where a.MID = c.MId and a.unno = b.unno  and b.un_lvl=1 ";
        String sql2 = " select  nvl(sum(TxnCount),0) totalTxnCount,to_char(nvl(SUM(TXNAMOUNT),0),'FM999,999,999,999,990.00') totalTxnAmount,COUNT(*) totalCount,'代理商户' as UNNOLVL from Bill_Merchantinfo a,sys_unit b,( "+
                " select t.mid,"+
                " sum(CAST(CAST(t.txnamount AS DECIMAL(18,2))/100 AS DECIMAL(18,2))) as txnamount , count(*)  TxnCount"+
                " from  check_realtxn t"+
                " where 1=1 "+
                " and t.txntype in (0,5) "+
                " and t.txnday = to_char(sysdate,'yyyyMMdd') "+
                " group by mid) c  where a.MID = c.MId and a.unno = b.unno and b.un_lvl!=1";
        String sql3 = " select  nvl(sum(TxnCount),0) totalTxnCount,to_char(nvl(SUM(TXNAMOUNT),0),'FM999,999,999,999,990.00') totalTxnAmount,COUNT(*) totalCount,'汇总' as UNNOLVL from Bill_Merchantinfo a,sys_unit b,( "+
                " select t.mid,"+
                " sum(CAST(CAST(t.txnamount AS DECIMAL(18,2))/100 AS DECIMAL(18,2))) as txnamount , count(*)  TxnCount"+
                " from  check_realtxn t"+
                " where 1=1 "+
                " and t.txntype in (0,5) "+
                " and t.txnday = to_char(sysdate,'yyyyMMdd') "+
                " group by mid) c  where a.MID = c.MId and a.unno = b.unno ";
			                                        
		List list = checRealtimeDealDetailDao.queryObjectsBySqlList(sql);
		List list2 = checRealtimeDealDetailDao.queryObjectsBySqlList(sql2);
		List list3 = checRealtimeDealDetailDao.queryObjectsBySqlList(sql3);
		list.add(0, list3.get(0));
		list.add(list2.get(0));
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(2);
		return dgb;
	}
}
