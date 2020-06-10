package com.hrt.phone.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.phone.dao.IPhoneReceiptsUploadDao;
import com.hrt.phone.service.IPhoneReceiptsUploadService;

public class PhoneReceiptsUploadServiceImpl implements IPhoneReceiptsUploadService {

	private IPhoneReceiptsUploadDao phoneReceiptsUploadDao;

	public IPhoneReceiptsUploadDao getPhoneReceiptsUploadDao() {
		return phoneReceiptsUploadDao;
	}
	public void setPhoneReceiptsUploadDao(IPhoneReceiptsUploadDao phoneReceiptsUploadDao) {
		this.phoneReceiptsUploadDao = phoneReceiptsUploadDao;
	}
	
	@Override
	public DataGridBean queryMerchantBankCardData(String mid) {
		String sql="select T.MID,T.BANKACCNO,T.BANKACCNAME,substr(T.BANKBRANCH, 0, instr(T.BANKBRANCH, '行', 1, 1)) BANKBRANCH,T.PAYBANKID,nvl(t.acctype,2) as acctype,"+
				   " task.maintaindate,nvl(task.approvestatus,'Y') as approvestatus from bill_merchantinfo t "+
				   " left join bill_merchanttaskdata task on task.mid = t.mid and task.type=2 and task.approvestatus='Z' where t.mid =:MID";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("MID", mid);
		List<Map<String,Object>> list=phoneReceiptsUploadDao.queryObjectsBySqlListMap2(sql, map);
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(list.size());
		dgb.setRows(list);
		return dgb;
	}
	
	@Override
	public DataGridBean queryMerchantTaskDetail2(String mid) {
		String sql="select  substr(T2.BANKBRANCH, 0, instr(T2.BANKBRANCH, '行', 1, 1)) BANKBRANCH,"+
					" T2.BANKACCNAME,T2.BANKACCNO,task.maintaindate,task.approvestatus,nvl(task.processcontext,' ') as processcontext"+
					" from bill_merchanttaskdata task,bill_merchanttaskdetail2 t2 where  task.bmtkid=t2.bmtkid "+
					" and task.type='2' and task.mid =:MID order by task.maintaindate desc";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("MID", mid);
		List<Map<String,Object>> list=phoneReceiptsUploadDao.queryObjectsBySqlListMap2(sql, map);
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(list.size());
		dgb.setRows(list);
		return dgb;
	}
	
	@Override
	public void updateReceiptsUploadData(UserBean userSession, Integer pkid,String parth,String parth2) {
		String sql="update check_blocktradedetail t set t.ifupload=1 ,t.minfo1=''," +
					" t.riskupload='"+parth+"',t.riskupload2='"+parth2+"',t.uploaddate=sysdate where t.pkid='"+pkid+"'";
		phoneReceiptsUploadDao.executeUpdate(sql);
	}
	
	
	@Override
	public boolean queryReceiptsUploadData(Integer pkid) {
		String sql="select * from check_blocktradedetail t where t.ifupload in(0,2) and t.pkid='"+pkid+"'";
		List list =phoneReceiptsUploadDao.queryObjectsBySqlList(sql);
		if(list.size()>0 && list!=null ){
			return true;
		}
		return false;
	}
	
	
	@Override
	public String queryUploadPath() {
		String title="LargeTxn";
		return phoneReceiptsUploadDao.queryUploadPath(title);
	}
	
	
	@Override
	public String queryRiskUploadPath() {
		String title="RiskCard";
		return phoneReceiptsUploadDao.queryUploadPath(title);
	}
	
	
	@Override
	public void saveReceiptsUploadData(String rrn, String parth) {
		Map<String,Object> map= new HashMap<String, Object>();
		String sqlCount="select count(*) from check_risk_temp t where t.rrn=:RRN ";
		map.put("RRN", rrn.trim());
		Integer count=phoneReceiptsUploadDao.querysqlCounts2(sqlCount, map);
		if(count==0){
			String sql="insert into check_risk_temp (rrn,riskupload,mataindate,status) values ('"+rrn+"','"+parth+"',sysdate,0)";
			phoneReceiptsUploadDao.executeUpdate(sql);
		}
	}
	
}
