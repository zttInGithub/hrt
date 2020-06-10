package com.hrt.biz.check.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.check.dao.CheckReceiptsOpreationDao;
import com.hrt.biz.check.entity.pagebean.ReceiptsDataBean;
import com.hrt.biz.check.service.CheckReceiptsOpreationService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.HttpXmlClient;

public class CheckReceiptsOpreationServiceImpl implements
		CheckReceiptsOpreationService {
	
	private CheckReceiptsOpreationDao checkReceiptsOpreationDao;
	private IUnitDao unitDao;
	private IMerchantInfoService  merchantInfoService;
	private String admAppIp;

	public CheckReceiptsOpreationDao getCheckReceiptsOpreationDao() {
		return checkReceiptsOpreationDao;
	}

	public void setCheckReceiptsOpreationDao(
			CheckReceiptsOpreationDao checkReceiptsOpreationDao) {
		this.checkReceiptsOpreationDao = checkReceiptsOpreationDao;
	}

	public IUnitDao getUnitDao() {
		return unitDao;
	}

	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}

	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	public String getAdmAppIp() {
		return admAppIp;
	}

	public void setAdmAppIp(String admAppIp) {
		this.admAppIp = admAppIp;
	}

	@Override
	public DataGridBean queryReceiptsAuditList(ReceiptsDataBean receiptsDataBean) {
		DataGridBean dgd = new DataGridBean();
		Map map = new HashMap<String, Object>();
		String sql="select b.pkid,m.unno ,t.mid, m.rname ," +
						" substr(substr(t.cardpan, 3, length(t.cardpan)), 1, 6) ||'******'||" +
						" substr(substr(t.cardpan, 3, length(t.cardpan)),-4,4)  cardpan ," +
						" CAST(CAST(t.txnamount AS DECIMAL(18,2))/100 AS DECIMAL(18,2)) as txnamount," +
						" b.txnday ,b.ifupload,b.minfo1,b.ifsettleflag" +
						" from  check_blocktradedetail b , CHECK_REALTIMEDEALDETAIL t,  bill_merchantinfo m " +
						" where t.mid=m.mid and b.pkid =t.pkid " ;
			String aa=receiptsDataBean.getTxnday().replaceAll("-", "").trim();
			String bb=receiptsDataBean.getTxnday1().replaceAll("-", "").trim();
			sql+=" and b.txnday between '"+aa+"' and '"+bb+"'";
			sql+=" and b.risktype = 0 ";//小票
		if("1".equals(receiptsDataBean.getUploadStatus())){
			//已上传
			sql+=" and b.ifupload=1";
		}else if("2".equals(receiptsDataBean.getUploadStatus())){
			//未上传
			sql+=" and b.ifupload=0";
		}else if("3".equals(receiptsDataBean.getUploadStatus())){
			//已通过
			sql+=" and b.ifsettleflag=1";
		}else if("4".equals(receiptsDataBean.getUploadStatus())){
			//未通过
			sql+=" and b.ifsettleflag=0";
		}
		if(!"".equals(receiptsDataBean.getUnno()) && receiptsDataBean.getUnno() !=null){
			sql+=" and m.unno=:UNNO";
			map.put("UNNO", receiptsDataBean.getUnno());
			}
		if(!"".equals(receiptsDataBean.getRname()) && receiptsDataBean.getRname()!=null){
			sql+=" and m.rname=:RNAME";
			map.put("RNAME", receiptsDataBean.getRname());
		}
		if(!"".equals(receiptsDataBean.getMid()) && receiptsDataBean.getMid() !=null){
			sql+=" and t.mid=:MID";
			map.put("MID", receiptsDataBean.getMid());
		}
		sql+= " order by b.pkid desc ";
		String sqlCount="select count(*) from ("+sql+")";
		
		Integer count =checkReceiptsOpreationDao.querysqlCounts2(sqlCount, map);
		List<Map<String,String>> list = checkReceiptsOpreationDao.queryObjectsBySqlList(sql, map, receiptsDataBean.getPage(), receiptsDataBean.getRows());
		dgd.setTotal(count);
		dgd.setRows(list);
		return dgd;
	}

	@Override
	public String queryUploadPath() {
		String title="LargeTxn";
		String sql="select UPLOAD_PATH from SYS_PARAM where TITLE='"+title+"'";
		return checkReceiptsOpreationDao.queryUploadPath(sql);
	}
	
	@Override
	public String queryImagePath(Integer pkid,int type){
		String sql="";
		if(type==0){
			//大额交易小票
			 sql=" select riskupload from check_blocktradedetail t where t.risktype=0  and t.pkid='"+pkid+"' ";
		}else if(type==1){
			//风险银行卡照片
			 sql=" select riskupload from check_blocktradedetail t where t.risktype=1  and t.pkid='"+pkid+"' ";
		}else if(type==2){
			//传统交易小票
			 sql=" select riskupload2 from check_blocktradedetail t where t.risktype=1  and t.pkid='"+pkid+"' ";
		}
		return checkReceiptsOpreationDao.queryUploadPath(sql);
	}
	
	@Override
	public void updateIfSettleFlagYes(Integer pkid,String mid,String money) {
		String url=admAppIp+"/AdmApp/T0Report/T0Report_addRiskSettlement.action";
		Map<String,String> params = new HashMap<String, String>();
		params.put("mid",mid);
		params.put("money", money);
		String ss=HttpXmlClient.post(url, params);
		
		String sql ="update CHECK_BLOCKTRADEDETAIL t set t.IFSETTLEFLAG=1 , t.riskday = to_date(to_char(sysdate,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS') WHERE t.pkid='"+pkid+"' and t.ifupload!=2";
		checkReceiptsOpreationDao.executeUpdate(sql);
//		String sql2="select count(*)  from check_realtimedealdetail t1, check_blocktradedetail t2" +
//					" where t1.pkid = t2.pkid and t2.risktype=1 and t2.ifsettleflag=0" +
//					" and t1.mid='"+mid+"'";
//		Integer count =checkReceiptsOpreationDao.querysqlCounts2(sql2, null);
//		if(count==0){
//			String sql3="update check_risk_merchant set mataintype='D',mataindate=sysdate where mid='"+mid+"'";
//			checkReceiptsOpreationDao.executeUpdate(sql3);
//		}
	}

	@Override
	public void updateIfSettleFlagNo(ReceiptsDataBean receiptsDataBean) {
		String sql="update CHECK_BLOCKTRADEDETAIL t set t.ifupload=2, t.minfo1='"+receiptsDataBean.getMinfo1()+"'  WHERE t.pkid='"+receiptsDataBean.getPkid()+"' and t.IFSETTLEFLAG!=1";
		checkReceiptsOpreationDao.executeUpdate(sql);
	}

	@Override
	public List<Map<String,String>> queryAuditReceiptsNoMerchantInfo(
			ReceiptsDataBean receiptsDataBean) {
		String aa=receiptsDataBean.getTxnday().replaceAll("-", "");
		String sql="select t.mid ,t.rname  " +
					" from  bill_merchantinfo t , check_blocktradedetail b,  CHECK_REALTIMEDEALDETAIL r" +
					" where b.pkid=r.pkid and r.mid=t.mid   and b.ifsettleflag != 1 " +
					" and b.txnday='"+aa.trim()+"' and b.risktype = 0 group by t.mid,t.rname";
		List<Map<String,String>> list =checkReceiptsOpreationDao.queryObjectsBySqlList(sql);
		return list;
	}

	
	
	//插入小票审核表
	public boolean insertBlockTradeDetail(String mid,String cashmode) {
		String sql;
		//cashmode   1是今天   2是昨天
		if("2".equals(cashmode)){
			sql = "select * from check_realtimedealdetail where mid = '"+mid+"' and txnday = to_char(sysdate-1,'yyyymmdd')";
		}else{
			sql = "select * from check_realtimedealdetail where mid = '"+mid+"' and txnday = to_char(sysdate,'yyyymmdd')";
		}
		
		List<Map<String, String>> list =checkReceiptsOpreationDao.queryObjectsBySqlList(sql);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map) list.get(i);
				String pkid = map.get("PKID").toString();
				String sqlCount = "select count(*) from check_blocktradedetail where pkid = "+pkid+" ";
				Integer count =checkReceiptsOpreationDao.querysqlCounts2(sqlCount, null);
				if(count == 0){
					String insert_sql ="insert into check_blocktradedetail(pkid,ifupload,ifSettleFlag,txnday,risktype) values("+pkid+",0,0,to_char(sysdate,'yyyymmdd'),1)";
					checkReceiptsOpreationDao.executeUpdate(insert_sql);
				}
			}
			return true;
		}else{
			return false;
		}

	}

	//风控交易卡片审核list
	public DataGridBean queryTransReceiptsAuditList(
			ReceiptsDataBean receiptsDataBean) {
		DataGridBean dgd = new DataGridBean();
		Map map = new HashMap<String, Object>();
		String sql="select b.pkid,b.riskday,b.uploaddate,m.unno ,t.mid, m.rname , m.bmid," +
						" substr(substr(t.cardpan, 3, length(t.cardpan)),0,3) cardpan ," +
						" substr(substr(t.cardpan, 3, length(t.cardpan)),4) cardpan2 ," +
						" CAST(CAST(t.txnamount AS DECIMAL(18,2))/100 AS DECIMAL(18,2)) as txnamount," +
						" t.txnday ,b.ifupload,b.minfo1,b.ifsettleflag,m.ism35," +
						" (select count(1) from check_risk_merchant ct where ct.mid=t.mid and ct.mataintype!='D' ) as status" +
						" from  check_blocktradedetail b , CHECK_REALTIMEDEALDETAIL t,  bill_merchantinfo m " +
						" where t.mid=m.mid and b.pkid =t.pkid " ;
			String aa=receiptsDataBean.getTxnday().replaceAll("-", "").trim();
			String bb=receiptsDataBean.getTxnday1().replaceAll("-", "").trim();
		if("1".equals(receiptsDataBean.getDateType())){
			sql+=" and t.txnday between '"+aa+"' and '"+bb+"'";
		}else if("2".equals(receiptsDataBean.getDateType())){
			sql+=" and b.riskday between to_date('"+aa+"'||'000000','YYYY-MM-DD HH24:MI:SS') and to_date('"+bb+"'||'235959','YYYY-MM-DD HH24:MI:SS')";
		}
			sql+=" and b.txnday=t.txnday and b.risktype = 1 ";//风控交易卡片审核
		if("1".equals(receiptsDataBean.getUploadStatus())){
			//已上传
			sql+=" and b.ifupload=1";
		}else if("2".equals(receiptsDataBean.getUploadStatus())){
			//未上传
			sql+=" and b.ifupload=0";
		}
		if("3".equals(receiptsDataBean.getSettleFlagStatus())){
			//已通过
			sql+=" and b.ifsettleflag=1";
		}else if("4".equals(receiptsDataBean.getSettleFlagStatus())){
			//未通过
			sql+=" and b.ifsettleflag=0";
		}
		if(!"".equals(receiptsDataBean.getUnno()) && receiptsDataBean.getUnno() !=null){
			sql+=" and m.unno=:UNNO";
			map.put("UNNO", receiptsDataBean.getUnno());
			}
		if(!"".equals(receiptsDataBean.getRname()) && receiptsDataBean.getRname()!=null){
			sql+=" and m.rname=:RNAME";
			map.put("RNAME", receiptsDataBean.getRname());
		}
		if(!"".equals(receiptsDataBean.getMid()) && receiptsDataBean.getMid() !=null){
			sql+=" and t.mid=:MID";
			map.put("MID", receiptsDataBean.getMid());
		}
		sql+= " order by b.pkid ";
		if(receiptsDataBean.getRiskType()==1){
			sql="select * from ("+sql+") where status=1";
		}else{
			sql="select * from ("+sql+") where status=0";
		}
		String sqlCount="select count(*) from ("+sql+")";
		
		Integer count =checkReceiptsOpreationDao.querysqlCounts2(sqlCount, map);
		List<Map<String,String>> list = checkReceiptsOpreationDao.queryObjectsBySqlList(sql, map, receiptsDataBean.getPage(), receiptsDataBean.getRows());
		dgd.setTotal(count);
		dgd.setRows(list);
		return dgd;
	}

	@Override
	public List<Map<String, String>> queryAuditReceiptsNoMerchantInfo(
			ReceiptsDataBean receiptsDataBean, String ids) {
		String txnday=receiptsDataBean.getTxnday().replaceAll("-", "").trim();
		String txnday1=receiptsDataBean.getTxnday1().replaceAll("-", "").trim();
		String sql="select t.mid ,t.rname  " +
					" from  bill_merchantinfo t , check_blocktradedetail b,  CHECK_REALTIMEDEALDETAIL r" +
					" where b.pkid=r.pkid and r.mid=t.mid   and b.ifsettleflag != 1 " +
					" and r.txnday between '"+txnday+"' and '"+txnday1+"' and b.txnday=r.txnday and b.pkid in ("+ids+") and b.risktype = 1 group by t.mid,t.rname";
		List<Map<String,String>> list =checkReceiptsOpreationDao.queryObjectsBySqlList(sql);
		return list;
	}

	@Override
	public String queryRiskUploadPath() {
		String title="RiskCard";
		String sql="select UPLOAD_PATH from SYS_PARAM where TITLE='"+title+"'";
		return checkReceiptsOpreationDao.queryUploadPath(sql);
	}

	@Override
	public List<Map<String, String>> queryRiskAuditReceiptsNoMerchantInfo(
			ReceiptsDataBean receiptsDataBean) {
		String txnday=receiptsDataBean.getTxnday().replaceAll("-", "").trim();
		String txnday1=receiptsDataBean.getTxnday1().replaceAll("-", "").trim();
		String sql="select t.mid ,t.rname  " +
					" from  bill_merchantinfo t , check_blocktradedetail b,  CHECK_REALTIMEDEALDETAIL r" +
					" where b.pkid=r.pkid and r.mid=t.mid   and b.ifsettleflag != 1 " +
					" and r.txnday between '"+txnday+"' and '"+txnday1+"' and b.txnday=r.txnday and b.risktype = 1 group by t.mid,t.rname";
		List<Map<String,String>> list =checkReceiptsOpreationDao.queryObjectsBySqlList(sql);
		return list;
	}

	@Override
	public List<Map<String, String>> queryRiskAuditReceiptsMerchantInfo(
			ReceiptsDataBean receiptsDataBean) {
		String txnday=receiptsDataBean.getTxnday().replaceAll("-", "").trim();
		String txnday1=receiptsDataBean.getTxnday1().replaceAll("-", "").trim();
		String sql="select t.mid ,t.rname  ,b.riskday" +
					" from  bill_merchantinfo t , check_blocktradedetail b,  CHECK_REALTIMEDEALDETAIL r" +
					" where b.pkid=r.pkid and r.mid=t.mid   and b.ifsettleflag = 1 ";
		if("1".equals(receiptsDataBean.getDateType())){
			sql+=" and r.txnday between '"+txnday+"' and '"+txnday1+"'";
		}else if("2".equals(receiptsDataBean.getDateType())){
			sql+=" and b.riskday between to_date('"+txnday+"'||'000000','YYYY-MM-DD HH24:MI:SS') and to_date('"+txnday1+"'||'235959','YYYY-MM-DD HH24:MI:SS') ";
		}
		sql+= " and b.txnday=r.txnday and b.risktype = 1 group by t.mid,t.rname,b.riskday";
		List<Map<String,String>> list =checkReceiptsOpreationDao.queryObjectsBySqlList(sql);
		return list;
	}

	//T+0风险商户查询列表
	@Override
	public DataGridBean queryRiskReceiptsAuditList(
			ReceiptsDataBean receiptsDataBean,UserBean userBean) {
		DataGridBean dgd = new DataGridBean();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		Map map = new HashMap<String, Object>();
		String sql="select b.pkid,b.riskday,b.uploaddate,m.unno ,t.mid, m.rname , m.bmid," +
						" substr(substr(t.cardpan, 3, length(t.cardpan)), 1, 6) ||'******'||" +
						" substr(substr(t.cardpan, 3, length(t.cardpan)),-4,4)  cardpan ," +
						" CAST(CAST(t.txnamount AS DECIMAL(18,2))/100 AS DECIMAL(18,2)) as txnamount," +
						" t.txnday ,b.ifupload,b.minfo1,b.ifsettleflag,m.ism35" +
						" from  check_blocktradedetail b , CHECK_REALTIMEDEALDETAIL t,  bill_merchantinfo m " +
						" where t.mid=m.mid and b.pkid =t.pkid " ;
		//商户
		if(userBean.getUseLvl()==3){
			sql+=" and m.MID ='"+userBean.getLoginName()+"'";
//			sql+=" and m.MID in (select MID from  bill_MerchantInfo where  " +
//					"PARENTMID= '"+userBean.getLoginName()+"' or MID ='"+userBean.getLoginName()+"')  ";
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
				sql+=" and m.unno IN ("+childUnno+") ";
			}
		}
		String aa=receiptsDataBean.getTxnday().replaceAll("-", "").trim();
		String bb=receiptsDataBean.getTxnday1().replaceAll("-", "").trim();
		if("1".equals(receiptsDataBean.getDateType())){
			sql+=" and t.txnday between '"+aa+"' and '"+bb+"'";
		}else if("2".equals(receiptsDataBean.getDateType())){
			sql+=" and b.riskday between to_date('"+aa+"'||'000000','YYYY-MM-DD HH24:MI:SS') and to_date('"+bb+"'||'235959','YYYY-MM-DD HH24:MI:SS')";
		}
			sql+=" and b.txnday=t.txnday and b.risktype = 1 ";//风控交易卡片审核
		if("1".equals(receiptsDataBean.getUploadStatus())){
			//已上传
			sql+=" and b.ifupload=1";
		}else if("2".equals(receiptsDataBean.getUploadStatus())){
			//未上传
			sql+=" and b.ifupload=0";
		}
		if("3".equals(receiptsDataBean.getSettleFlagStatus())){
			//已通过
			sql+=" and b.ifsettleflag=1";
		}else if("4".equals(receiptsDataBean.getSettleFlagStatus())){
			//未通过
			sql+=" and b.ifsettleflag=0";
		}
		if(!"".equals(receiptsDataBean.getUnno()) && receiptsDataBean.getUnno() !=null){
			sql+=" and m.unno=:UNNO";
			map.put("UNNO", receiptsDataBean.getUnno());
			}
		if(!"".equals(receiptsDataBean.getRname()) && receiptsDataBean.getRname()!=null){
			sql+=" and m.rname=:RNAME";
			map.put("RNAME", receiptsDataBean.getRname());
		}
		if(!"".equals(receiptsDataBean.getMid()) && receiptsDataBean.getMid() !=null){
			sql+=" and t.mid=:MID";
			map.put("MID", receiptsDataBean.getMid());
		}
		sql+= " and t.txnamount !='null' order by b.pkid ";
		String sqlCount="select count(*) from ("+sql+")";
		
		Integer count =checkReceiptsOpreationDao.querysqlCounts2(sqlCount, map);
		List<Map<String,String>> list = checkReceiptsOpreationDao.queryObjectsBySqlList(sql, map, receiptsDataBean.getPage(), receiptsDataBean.getRows());
		dgd.setTotal(count);
		dgd.setRows(list);
		return dgd;
	}

	/**
	 * 增加风险商户（卡片审核）
	 */
	@SuppressWarnings("unchecked")
	public String addRiskMerchantInfo(String mid){
		String flag="";
		String mataintype = "";
		String sql="select MATAINTYPE from CHECK_RISK_MERCHANT where mid= '"+mid+"' ";
		List<Map<String,String>> list=checkReceiptsOpreationDao.executeSql(sql);
		if(list.size()>0){			
				Map map =list.get(0);
				mataintype = map.get("MATAINTYPE").toString();	
			if(mataintype.equals("D")){
				String sql1="update CHECK_RISK_MERCHANT m set m.MATAINTYPE='A' WHERE m.CRMID=(select CRMID from CHECK_RISK_MERCHANT where mid='"+mid+"' and MATAINTYPE='D')";
				checkReceiptsOpreationDao.executeUpdate(sql1);
				flag="1";
			}else{
				flag="2";
			}
			
		}else{
			String sql3="insert into CHECK_RISK_MERCHANT (MID,MATAINDATE,MATAINTYPE) values('"+mid+"',sysdate,'A') ";
			checkReceiptsOpreationDao.executeUpdate(sql3);
			flag="1";
		}
			
		return flag;
		
	}
	
}
