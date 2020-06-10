package com.hrt.phone.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.phone.dao.IPhoneMerchantWalletDao;
import com.hrt.phone.entity.model.MerchantBankCardModel;
import com.hrt.phone.service.IPhoneMerchantWalletService;
import com.hrt.util.HttpXmlClient;

public class PhoneMerchantWalletServiceImpl implements IPhoneMerchantWalletService {

	private IPhoneMerchantWalletDao phoneMerchantWalletDao;
	
	private String admAppIp;
	
	
	public IPhoneMerchantWalletDao getPhoneMerchantWalletDao() {
		return phoneMerchantWalletDao;
	}
	public void setPhoneMerchantWalletDao(
			IPhoneMerchantWalletDao phoneMerchantWalletDao) {
		this.phoneMerchantWalletDao = phoneMerchantWalletDao;
	}

	public String getAdmAppIp() {
		return admAppIp;
	}
	public void setAdmAppIp(String admAppIp) {
		this.admAppIp = admAppIp;
	}
	
	
	/*
	 * 查询余额
	 */
	@Override
	public Map<String ,Object>  queryBalance(String mid) {
		String url=admAppIp+"/AdmApp/T0/T0_realQueryBalance.action";
		Map<String,String> params = new HashMap<String, String>();
		params.put("mid",mid);
		String ss=HttpXmlClient.post(url, params);
		System.out.println(ss);
		JSONObject json = new JSONObject();
		Map<String ,Object> map=(Map<String, Object>) json.parse(ss);
		return map;
	}
	
	
	/**
	 * 资产：累计收益，总资产，今日收益
	 */
	@Override
	public Map<String ,Object>  queryAsset(String mid) {
		String url=admAppIp+"/AdmApp/T0/T0_realQueryAsset.action";
		Map<String,String> params = new HashMap<String, String>();
		params.put("mid",mid);
		String ss=HttpXmlClient.post(url, params);
		System.out.println(ss);
		JSONObject json = new JSONObject();
		Map<String ,Object> map=(Map<String, Object>) json.parse(ss);
		return map;
	}
	
	/**
	 * 根据Mid查看聚合商户结算信息
	 */
	@Override
	public Map<String ,Object>  queryMerAdjustRate(String mid) {
		String url=admAppIp+"/AdmApp/merchacc/merchant_QueryMerInfo.action";
		Map<String,String> params = new HashMap<String, String>();
		params.put("mid",mid);
		String ss=HttpXmlClient.post(url, params);
		System.out.println(ss);
		JSONObject json = new JSONObject();
		Map<String ,Object> map=(Map<String, Object>) json.parse(ss);
		return map;
	}

	/**
	 * 余额提现
	 */
	@Override
	public Map<String,Object> queryCashWithDrawal(String mid, String money,String bankAccNo) throws Exception {
		String sql ="select t.bankAccName,t.payBankId,t.bankBranch from bill_merchantbankcardinfo t where t.mid=:MID AND t.bankAccNo=:BANKACCNO AND t.status=0 ";
		Map<String,String> reqParam = new HashMap<String, String>();
		reqParam.put("MID", mid);
		reqParam.put("BANKACCNO", bankAccNo);
		List<Map<String,Object>> list=phoneMerchantWalletDao.queryObjectsBySqlListMap2(sql, reqParam);
		
		String url=admAppIp+"/AdmApp/T0/T0_TakeBalance.action";
		Map<String,String> params = new HashMap<String, String>();
		params.put("mid",mid);
		params.put("money", money);
		//params.put("bankAccNo", ThreeDes.des3EncodeCBC(bankAccNo));

		if(list!=null && list.size()>0){
			params.put("bankAccName", list.get(0).get("BANKACCNAME")+"");
			params.put("payBankId", list.get(0).get("PAYBANKID")+"");
			params.put("bankBranch", list.get(0).get("BANKBRANCH")+"");
			params.put("bankAccNo", bankAccNo);
		}else{
			params.put("bankAccName", "");
			params.put("payBankId", "");
			params.put("bankBranch", "");
			params.put("bankAccNo", "");
		}
		String ss=HttpXmlClient.post(url, params);
		JSONObject json = new JSONObject();
		Map<String ,Object> map=(Map<String, Object>) json.parse(ss);
		return map;
	}
	
	
	@Override
	public Map<String,Object> queryCashWithAsset(String mid, String money,String bankAccNo) throws Exception {
		String sql ="select t.bankAccName,t.payBankId,t.bankBranch from bill_merchantbankcardinfo t where t.mid=:MID AND t.bankAccNo=:BANKACCNO AND t.status=0 ";
		Map<String,String> reqParam = new HashMap<String, String>();
		reqParam.put("MID", mid);
		reqParam.put("BANKACCNO", bankAccNo);
		List<Map<String,Object>> list=phoneMerchantWalletDao.queryObjectsBySqlListMap2(sql, reqParam);
		
		String url=admAppIp+"/AdmApp/T0/T0_TakeAsset.action";
		Map<String,String> params = new HashMap<String, String>();
		params.put("mid",mid);
		params.put("money", money);
		//params.put("bankAccNo", ThreeDes.des3EncodeCBC(bankAccNo));

		if(list!=null && list.size()>0){
			params.put("bankAccName", list.get(0).get("BANKACCNAME")+"");
			params.put("payBankId", list.get(0).get("PAYBANKID")+"");
			params.put("bankBranch", list.get(0).get("BANKBRANCH")+"");
			params.put("bankAccNo", bankAccNo);
		}else{
			params.put("bankAccName", "");
			params.put("payBankId", "");
			params.put("bankBranch", "");
			params.put("bankAccNo", "");
		}
		String ss=HttpXmlClient.post(url, params);
		JSONObject json = new JSONObject();
		Map<String ,Object> map=(Map<String, Object>) json.parse(ss);
		return map;
	}
	
	@Override
	public boolean queryIsLimitDay(Integer type) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		Map<String,Object> map= new HashMap<String,Object>();
		Date date= new Date();
		String sql="select count(*) from sys_holiday t where t.holiday=:HOLIDAY";
		map.put("HOLIDAY", sf.format(date));
		Integer count =phoneMerchantWalletDao.querysqlCounts2(sql, map);
		boolean flag=false;
		if(type==0){
			flag=date.getHours()>7 && date.getHours()<20?true:false;
		}else if(type==1){
			flag=date.getHours()>9 && date.getHours()<20?true:false;
		}else if(type==9){//立码富余额
			flag=date.getHours()>7 && (date.getHours()<23||(date.getHours()==23&&date.getMinutes()<40))?true:false;
		}else{
			flag=date.getHours()>8 && date.getHours()<20?true:false;
		}
		if(flag && count==0){
			return true;
		}else{
			return false;
		}
		
	}
	@Override
	public JsonBean queryCashWithDrawalListData(String mid,Integer page,Integer rows,String startDay,String endDay) {
		String url=admAppIp+"/AdmApp/T0/T0_CashRecord.action";
		Map<String,String> params = new HashMap<String, String>();
		params.put("mid",mid);
		params.put("page", page.toString());
		params.put("rows", rows.toString());
		params.put("startdate", startDay);
		params.put("enddate", endDay);
		String ss=HttpXmlClient.post(url, params);
		JSONObject json = new JSONObject();
		JsonBean jj=json.parseObject(ss, JsonBean.class);
		return jj;
	}
	@Override
	public Map<String, Object> queryMerchantSettleCycle(String mid) {
		String url=admAppIp+"/AdmApp/T0/T0_APPUndo.action";
		Map<String,String> params = new HashMap<String, String>();
		params.put("mid",mid);
		String ss=HttpXmlClient.post(url, params);
		System.out.println(ss);
		JSONObject json = new JSONObject();
		Map<String ,Object> map=(Map<String, Object>) json.parse(ss);
		return map;
	}
	
	
	@Override
	public boolean saveBankCard(String bankAccName, String bankAccNo, String mid,String payBankId, String bankBranch) {
		String  sql ="select count(*) from bill_merchantbankcardinfo t where t.mid=:MID and t.bankaccname=:BANKACCNAME  and t.bankAccNo=:BANKACCNO and status=0";
		String  sql2 ="select count(*) from bill_merchantbankcardinfo t where t.mid=:MID and t.bankaccname=:BANKACCNAME  and t.bankAccNo=:BANKACCNO and status=1";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("MID", mid);
		map.put("BANKACCNAME", bankAccName);
		map.put("BANKACCNO", bankAccNo);
		Integer count=phoneMerchantWalletDao.querysqlCounts2(sql, map);
		if(count>0){
			return false;
		}else{
			Integer count2=phoneMerchantWalletDao.querysqlCounts2(sql2, map);
			if(count2>0){
				String updateSql="update bill_merchantbankcardinfo t set t.status=0,t.paybankid='"+payBankId+"',t.bankBranch='"+bankBranch+"' where t.mid='"+mid+"' and t.bankAccNo='"+bankAccNo+"' and t.bankAccName='"+bankAccName+"'";
				phoneMerchantWalletDao.executeUpdate(updateSql);
			}else{
				MerchantBankCardModel mbc = new MerchantBankCardModel();
				mbc.setBankAccName(bankAccName);
				mbc.setBankAccNo(bankAccNo);
				mbc.setMid(mid);
				mbc.setCreateDate(new Date());
				mbc.setStatus(0);
				mbc.setMerCardType(1);
				mbc.setBankBranch(bankBranch);
				mbc.setPayBankId(payBankId);
				phoneMerchantWalletDao.saveObject(mbc);
			}
			return true;
		}
	}
	
	
	@Override
	public DataGridBean queryMerchantBankCardListData(String mid) {
		String sql="select T.MBCID,T.MID,T.BANKACCNO,T.BANKACCNAME,T.CREATEDATE,T.MATAINDATE,T.STATUS,T.MERCARDTYPE," +
					" substr(T.BANKBRANCH,0,instr(T.BANKBRANCH,'行',1,1)) BANKBRANCH," +
					" T.PAYBANKID from bill_merchantbankcardinfo t where t.mid=:MID and status=0 order by MBCID ASC ";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("MID", mid);
		List<Map<String,Object>> list=phoneMerchantWalletDao.queryObjectsBySqlListMap2(sql, map);
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(list.size());
		dgb.setRows(list);
		return dgb;
	}
	
	
	@Override
	public void deleteMerchantBankCard(String mid, String bankAccNo) {
		String sql=" update bill_merchantbankcardinfo set status=1 where mid='"+mid+"' and bankaccno='"+bankAccNo+"' and merCardType !=0 ";
		phoneMerchantWalletDao.executeUpdate(sql);
	}
	
	/**
	 * (修改银行卡信息)
	 */
	@Override
	public void updateMerchantBankCardInfo(Integer mbcid, String bankAccNo,
			String payBankId, String bankAccName) {
		String hql = "from MerchantBankCardModel t where t.mbcid=?";
		MerchantBankCardModel mbc =(MerchantBankCardModel) phoneMerchantWalletDao.getObjectByID(MerchantBankCardModel.class, mbcid);
		if(bankAccNo!=null && !"".equals(bankAccNo)){
			mbc.setBankAccNo(bankAccNo);
		}
		if(payBankId!=null && !"".equals(payBankId)){
			mbc.setPayBankId(payBankId);
		}
		if(bankAccName!=null && !"".equals(bankAccName)){
			mbc.setBankAccName(bankAccName);
		}
		phoneMerchantWalletDao.updateObject(mbc);
	}
	
	
	@Override
	public Map<String, Object> updateAutoToAsset(String mid, String ifauto) {
		String url=admAppIp+"/AdmApp/T0/T0_interestButton.action";
		Map<String,String> params = new HashMap<String, String>();
		params.put("mid",mid);
		params.put("ifauto", ifauto);
		String ss=HttpXmlClient.post(url, params);
		JSONObject json = new JSONObject();
		Map<String ,Object> map=(Map<String, Object>) json.parse(ss);
		return map;
	}
	@Override
	public boolean isRiskImageUpLoad(String mid,String money) {
		if(mid.startsWith("HRTPAY"))return true;
		String sql="select count(*) from check_blocktradedetail t,check_realtimedealdetail t1 " +
					"where t.pkid=t1.pkid and t1.mid='"+mid+"' and t1.txnday=to_char(sysdate,'yyyyMMdd') and t.ifupload=0";
		Integer count=phoneMerchantWalletDao.querysqlCounts2(sql, null);
		if(count==0){
			Double moneyD =Double.parseDouble(money);
			// @author:xuegangliu-20180202 提现接口处理
//			String uploadMoneySql="select nvl(sum(CAST(CAST(t1.txnamount AS DECIMAL(18,2))/100 AS DECIMAL(18,2))),0) as txnamount from check_blocktradedetail t,check_realtimedealdetail t1 " +
//									"where t.pkid=t1.pkid and t1.mid='"+mid+"' and t1.txnday=to_char(sysdate,'yyyyMMdd') and t.ifupload=1";
			String uploadMoneySql="select nvl(sum(CAST(t.txnamount AS DECIMAL(18, 2))),0) as txnamount " +
					" from check_realtxn t where t.mid='"+mid+"' and t.txnday=to_char(sysdate,'yyyyMMdd') and t.txnstate=0";
			List<Map<String,Object>> list=phoneMerchantWalletDao.queryObjectsBySqlObject(uploadMoneySql);
			BigDecimal uploadMoney = list.size()>0?(BigDecimal)list.get(0).get("TXNAMOUNT"): new BigDecimal(0);
			if(moneyD<=uploadMoney.doubleValue()){
				return true;
			}else{
				String insertSql="insert into CHECK_INTERCEPT_CASH (mid,cashmoney,cashdate,remarks) values ('"+mid+"','"+money+"',sysdate,'当前提现金额大于已上传照片交易金额！')";
				phoneMerchantWalletDao.executeUpdate(insertSql);
				return false;
			}
		}else{
			String insertSql="insert into CHECK_INTERCEPT_CASH (mid,cashmoney,cashdate,remarks) values ('"+mid+"','"+money+"',sysdate,'有交易照片未上传！')";
			phoneMerchantWalletDao.executeUpdate(insertSql);
			return false;
		}
	}
	@Override
	public List<Map<String, Object>> findSettleListData(String mid,
			Integer page, Integer rows) {
		String sql=" select settleday,totmnamt,(case when remarks is null  then '已付款' " +
					" else substr(remarks,0,4) end ) as status , " +
					" (case when remarks is  null  then settleday else substr(remarks,6,length(remarks))  end ) as orpeaDate  " +
					" from check_income  where mid='"+mid+"' order by ciid desc ";
		List<Map<String,Object>> list=phoneMerchantWalletDao.queryObjectsBySqlList(sql, null, page, rows);
		return list;
	}

}
