package com.hrt.phone.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.hrt.biz.util.gateway.MD5Util;
import com.hrt.biz.util.unionpay.HrtRSAUtil;
import com.hrt.biz.util.unionpay.SHAEncUtil;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.JsonBeanForSign;
import com.hrt.phone.service.IPhoneMerchantWalletService;
import com.hrt.phone.service.IPhoneWechatPublicAccService;
import com.hrt.util.SimpleXmlUtil;

public class PhoneMerchantWalletAction extends BaseAction{

	/**
	 * @author xxx
	 *  钱包
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(PhoneMerchantWalletAction.class);

	private IPhoneMerchantWalletService phoneMerchantWalletService;
	private IPhoneWechatPublicAccService phoneWechatPublicAccService;
	
	public IPhoneWechatPublicAccService getPhoneWechatPublicAccService() {
		return phoneWechatPublicAccService;
	}
	public void setPhoneWechatPublicAccService(IPhoneWechatPublicAccService phoneWechatPublicAccService) {
		this.phoneWechatPublicAccService = phoneWechatPublicAccService;
	}

	private static String signEnd = "&key=dseesa325errtcyraswert749errtdyt";
	
	public IPhoneMerchantWalletService getPhoneMerchantWalletService() {
		return phoneMerchantWalletService;
	}
	public void setPhoneMerchantWalletService(IPhoneMerchantWalletService phoneMerchantWalletService) {
		this.phoneMerchantWalletService = phoneMerchantWalletService;
	}

	/**
	 * 查询余额
	 */
	public void queryBalance(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		try {
			Map<String ,Object>  map=phoneMerchantWalletService.queryBalance(mid);
			json.setSuccess(true);
			json.setObj(map);
			json.setMsg("查询成功！");
		} catch (Exception e) {
			log.error("queryBalance 异常:"+e);
			json.setSuccess(false);
			json.setMsg("查询余额失败！");
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 解密查询余额
	 */
	public void decQueryBalance(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		try {
			mid = HrtRSAUtil.decryptWithBase64(mid);
			if(mid!=null&&!"".equals(mid)){
				Map<String ,Object>  map=phoneMerchantWalletService.queryBalance(mid);
				json.setSuccess(true);
				json.setObj(map);
				json.setMsg("查询成功！");
			}else{
				json.setSuccess(false);
				json.setMsg("数据有误！");
			}
		} catch (Exception e) {
			log.error("decQueryBalance 异常:"+e);
			json.setSuccess(false);
			json.setMsg("查询余额失败！");
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 查询总资产
	 */
	public void queryAsset(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		try {
			Map<String ,Object>  map=phoneMerchantWalletService.queryAsset(mid);
			json.setSuccess(true);
			json.setObj(map);
			json.setMsg("查询成功！");
		} catch (Exception e) {
			log.error("queryAsset 异常:"+e);
			json.setSuccess(false);
			json.setMsg("查询余额失败！");
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 解密查询总资产
	 */
	public void decQueryAsset(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		try {
			mid= HrtRSAUtil.decryptWithBase64(mid);
			if(mid!=null&&!"".equals(mid)){
				Map<String ,Object>  map=phoneMerchantWalletService.queryAsset(mid);
				json.setSuccess(true);
				json.setObj(map);
				json.setMsg("查询成功！");
			}else{
				json.setSuccess(false);
				json.setMsg("数据有误！");
			}
		} catch (Exception e) {
			log.error("decQueryAsset 异常:"+e);
			json.setSuccess(false);
			json.setMsg("查询余额失败！");
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 根据Mid查看聚合商户结算信息
	 */
	public void queryMerAdjustRate(){
		JsonBean json = new JsonBean();
		String mid = super.getRequest().getParameter("mid");
		try {
			Map<String, Object> map = phoneMerchantWalletService.queryMerAdjustRate(mid);
			json.setObj(map);
			json.setSuccess(true);
			json.setMsg("查询成功");
		} catch (Exception e) {
			log.error("根据Mid查看聚合商户结算信息异常：mid = "+mid + e);
			json.setSuccess(false);
			json.setMsg("请稍后查询");
		}
		super.writeJson(json);
	}
	
	/**
	 * 余额提现
	 */
	public void queryCashWithDrawal(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		if(isLimitBalance(0) || mid.trim().equals("486000053110968")||(mid.startsWith("HRTPAY")&&isLimitBalance(9))){
			String money=super.getRequest().getParameter("money");
			String bankAccNo=super.getRequest().getParameter("bankAccNo");
			boolean flag=phoneMerchantWalletService.isRiskImageUpLoad(mid,money);
			if(flag){
				try {
					Map<String,Object> map=phoneMerchantWalletService.queryCashWithDrawal(mid,money,bankAccNo);
						json.setSuccess(true);
						json.setObj(map);
				} catch (Exception e) {
					log.error(e);
					json.setSuccess(false);
					json.setMsg("提现失败！");
				}
			}else{
				json.setSuccess(false);
				json.setMsg("有交易卡片照片未上传！");
			}

		}else{
			json.setSuccess(false);
			json.setMsg("当前时间段不允许提现！");
		}
		super.writeJson(json);
	}
	
	/**
	 * 解密余额提现
	 */
	public void decQueryCashWithDrawal(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		mid = HrtRSAUtil.decryptWithBase64(mid);
		if(mid!=null&&!"".equals(mid)){
			if(isLimitBalance(0) || mid.trim().equals("486000053110968")){
				String money=super.getRequest().getParameter("money");
				String bankAccNo=super.getRequest().getParameter("bankAccNo");
				money = HrtRSAUtil.decryptWithBase64(money);
				bankAccNo = HrtRSAUtil.decryptWithBase64(bankAccNo);
				if(money!=null&&!"".equals(money)&&bankAccNo!=null&&!"".equals(bankAccNo)){
					boolean flag=phoneMerchantWalletService.isRiskImageUpLoad(mid,money);
					if(flag){
						try {
							Map<String,Object> map=phoneMerchantWalletService.queryCashWithDrawal(mid,money,bankAccNo);
								json.setSuccess(true);
								json.setObj(map);
						} catch (Exception e) {
							log.error(e);
							json.setSuccess(false);
							json.setMsg("提现失败！");
						}
					}else{
						json.setSuccess(false);
						json.setMsg("有交易卡片照片未上传！");
					}
				}else{
					json.setSuccess(false);
					json.setMsg("数据有误！");
				}
			}else{
				json.setSuccess(false);
				json.setMsg("当前时间段不允许提现！");
			}
		}else{
			json.setSuccess(false);
			json.setMsg("数据有误！");
		}
		super.writeJson(json);
	}
	
	/*
	 * 余额提现2
	 */
//	public void queryCashWithDrawal2(){
//		JsonBean json = new JsonBean();
//		
//		if(isLimitBalance()){
//			String mid=super.getRequest().getParameter("mid");
//			String money=super.getRequest().getParameter("money");
//			String bankAccNo=super.getRequest().getParameter("bankAccNo");
//			try {
//				Map<String,Object> map=phoneMerchantWalletService.queryCashWithDrawal2(mid,money,bankAccNo);
//					json.setSuccess(true);
//					json.setObj(map);
//			} catch (Exception e) {
//				log.error(e);
//				json.setSuccess(false);
//				json.setMsg("提现失败！");
//			}
//		}else{
//			json.setSuccess(false);
//			json.setMsg("当前时间段不允许提现！");
//		}
//		super.writeJson(json);
//	}
	
	
	/**
	 * 资产提现
	 */
	public void queryCashWithAsset(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		if(isLimitBalance(mid.startsWith("HRTPAY")?2:1)){
			String money=super.getRequest().getParameter("money");
			String bankAccNo=super.getRequest().getParameter("bankAccNo");
			try {
				Map<String,Object> map=phoneMerchantWalletService.queryCashWithAsset(mid,money,bankAccNo);
					json.setSuccess(true);
					json.setObj(map);
			} catch (Exception e) {
				log.error(e);
				json.setSuccess(false);
				json.setMsg("提现失败！");
			}
		}else{
			json.setSuccess(false);
			json.setMsg("当前时间段不允许提现！");
		}
		super.writeJson(json);
	}

	/**
	 * 解密资产提现
	 */
	public void decQueryCashWithAsset(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		mid= HrtRSAUtil.decryptWithBase64(mid);
		if(mid!=null&&!"".equals(mid)){
			if(isLimitBalance(mid.startsWith("HRTPAY")?2:1)){
				String money=super.getRequest().getParameter("money");
				String bankAccNo=super.getRequest().getParameter("bankAccNo");
				money= HrtRSAUtil.decryptWithBase64(money);
				bankAccNo= HrtRSAUtil.decryptWithBase64(bankAccNo);
				if(money!=null&&!"".equals(money)&&bankAccNo!=null&&!"".equals(bankAccNo)){
					try {
						Map<String,Object> map=phoneMerchantWalletService.queryCashWithAsset(mid,money,bankAccNo);
							json.setSuccess(true);
							json.setObj(map);
					} catch (Exception e) {
						log.error(e);
						json.setSuccess(false);
						json.setMsg("提现失败！");
					}
				}else{
					json.setSuccess(false);
					json.setMsg("数据有误！");
				}
			}else{
				json.setSuccess(false);
				json.setMsg("当前时间段不允许提现！");
			}
		}else{
			json.setSuccess(false);
			json.setMsg("数据有误！");
		}
		super.writeJson(json);
	}

	public boolean isLimitBalance(Integer type){
		boolean flag=phoneMerchantWalletService.queryIsLimitDay(type);
		return flag;
	}
	
	
	/**
	 * 提现记录
	 */
	public void queryCashWithDrawalListData(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		Integer page=Integer.parseInt(super.getRequest().getParameter("page"));
		Integer rows=Integer.parseInt(super.getRequest().getParameter("rows"));
		String startDay=super.getRequest().getParameter("startdate");
		String endDay=super.getRequest().getParameter("enddate");
		try {
			json=phoneMerchantWalletService.queryCashWithDrawalListData(mid,page,rows,startDay,endDay);
		} catch (Exception e) {
			log.error("queryCashWithDrawalListData 异常:"+e);
			json.setSuccess(false);
			json.setMsg("提现失败！");
		}
		super.writeJson(json);
	}

	/**
	 * 解密提现记录
	 */
	public void decQueryCashWithDrawalListData(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		Integer page=Integer.parseInt(super.getRequest().getParameter("page"));
		Integer rows=Integer.parseInt(super.getRequest().getParameter("rows"));
		String startDay=super.getRequest().getParameter("startdate");
		String endDay=super.getRequest().getParameter("enddate");
		mid = HrtRSAUtil.decryptWithBase64(mid);
		if(mid!=null&&!"".equals(mid)){
			try {
				json=phoneMerchantWalletService.queryCashWithDrawalListData(mid,page,rows,startDay,endDay);
			} catch (Exception e) {
				log.error(e);
				json.setSuccess(false);
				json.setMsg("提现失败！");
			}
		}else{
			json.setSuccess(false);
			json.setMsg("数据有误！");
		}
		super.writeJson(json);
	}
	
	/**
	 * 查询商户结算周期
	 */
	public void queryMerchantSettleCycle(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		try {
			Map<String ,Object>  map=phoneMerchantWalletService.queryMerchantSettleCycle(mid);
			json.setSuccess(true);
			json.setObj(map);
			json.setMsg("查询成功！");
		} catch (Exception e) {
			log.error("queryMerchantSettleCycle 异常:"+e);
			json.setSuccess(false);
			json.setMsg("查询失败！");
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 添加银行卡
	 */
	public void addBankCard(){
		JsonBean json = new JsonBean();
		String bankAccNo=super.getRequest().getParameter("bankAccNo");
		String mid=super.getRequest().getParameter("mid");
		String bankAccName=super.getRequest().getParameter("bankAccName");
		String payBankId=super.getRequest().getParameter("payBankId");
		String bankBranch = super.getRequest().getParameter("bankBranch");
		if(mid!=null && !"".equals(mid) && bankAccNo!=null && !"".equals(bankAccNo) &&  bankAccName!=null && !"".equals(bankAccName) 
				&& payBankId !=null && !"".equals(payBankId) && bankBranch !=null && !"".equals(bankBranch)){
			try {
				boolean flag=phoneMerchantWalletService.saveBankCard(bankAccName,bankAccNo,mid,payBankId,bankBranch);
				if(flag){
					json.setSuccess(true);
					json.setMsg("添加成功！");
				}else{
					json.setSuccess(false);
					json.setMsg("该卡已绑定，不允许重复绑定！");
				}
			} catch (Exception e) {
				log.info(e);
				json.setSuccess(false);
				json.setMsg("添加失败！");
			}
		}else{
			json.setSuccess(false);
			json.setMsg("参数有空值！");
		}
		super.writeJson(json);
	}
	
	/**
	 * 添加银行卡
	 */
	public void decAddBankCard(){
		JsonBean json = new JsonBean();
		boolean b = phoneWechatPublicAccService.addReplayAttack(super.getRequest().getParameter("uuid"), super.getRequest().getParameter("timeStamp"));
		if(!b) {
			json.setSuccess(false);
			json.setMsg("数据有误！");
		}else {
			String bankAccNo=super.getRequest().getParameter("bankAccNo");
			String mid=super.getRequest().getParameter("mid");
			String bankAccName=super.getRequest().getParameter("bankAccName");
			String payBankId=super.getRequest().getParameter("payBankId");
			String bankBranch = super.getRequest().getParameter("bankBranch");
			String sign = super.getRequest().getParameter("sign");
			Map<String,String> map1 = new HashMap<String, String>();
			map1.put("bankAccNo", bankAccNo);
			map1.put("bankAccName", bankAccName);
			String md5 = MD5Util.MD5((SimpleXmlUtil.getSignBlock(map1)+signEnd));
			if(md5.equals(sign)){
				if(mid!=null && !"".equals(mid) && bankAccNo!=null && !"".equals(bankAccNo) &&  bankAccName!=null && !"".equals(bankAccName) 
						&& payBankId !=null && !"".equals(payBankId) && bankBranch !=null && !"".equals(bankBranch)){
					try {
	
						bankAccNo= HrtRSAUtil.decryptWithBase64(bankAccNo);
						boolean flag=phoneMerchantWalletService.saveBankCard(bankAccName,bankAccNo,mid,payBankId,bankBranch);
						if(flag){
							json.setSuccess(true);
							json.setMsg("添加成功！");
						}else{
							json.setSuccess(false);
							json.setMsg("该卡已绑定，不允许重复绑定！");
						}
					} catch (Exception e) {
						log.info(e);
						json.setSuccess(false);
						json.setMsg("添加失败！");
					}
				}else{
					json.setSuccess(false);
					json.setMsg("参数有空值！");
				}
			}else{
				json.setSuccess(false);
				json.setMsg("数据有误！");
			}
		}
		JsonBeanForSign json1 = new JsonBeanForSign();
		String data = JSON.toJSONStringWithDateFormat(json, "yyyy-MM-dd HH:mm:ss");
		String signs1 = SHAEncUtil.getSHA256Str(data);
		json1.setSign(signs1);
		json1.setData(data);
		super.writeJson(json1);
	}
	
	/**
	 * 取消银行卡
	 */
	public void deleteMerchantBankCard(){
		JsonBean json = new JsonBean();
		String bankAccNo=super.getRequest().getParameter("bankAccNo");
		String mid=super.getRequest().getParameter("mid");
		try {
			phoneMerchantWalletService.deleteMerchantBankCard(mid,bankAccNo);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			log.info(e);
			json.setMsg("删除失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	/**
	 * 取消银行卡
	 */
	public void decDeleteMerchantBankCard(){
		JsonBean json = new JsonBean();
		String bankAccNo=super.getRequest().getParameter("bankAccNo");
		String mid=super.getRequest().getParameter("mid");
		try {
			bankAccNo= HrtRSAUtil.decryptWithBase64(bankAccNo);
			phoneMerchantWalletService.deleteMerchantBankCard(mid,bankAccNo);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			log.info(e);
			json.setMsg("删除失败！");
			json.setSuccess(false);
		}		
		super.writeJson(json);
	}
	
	/**
	 * 查询银行卡
	 */
	public void queryMerchantBankCardList(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		if(mid!=null && !"".equals(mid)){
			try {
				DataGridBean dgb=phoneMerchantWalletService.queryMerchantBankCardListData(mid);
				json.setSuccess(true);
				json.setMsg("查询成功！");
				json.setObj(dgb);
			} catch (Exception e) {
				log.info(e);
				json.setSuccess(false);
				json.setMsg("查询失败！");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 加密查询银行卡
	 */
	public void encQueryMerchantBankCardList(){
		JsonBeanForSign json = new JsonBeanForSign();
		Map<String,String> map1 = new HashMap<String, String>();
		boolean b = phoneWechatPublicAccService.addReplayAttack(super.getRequest().getParameter("uuid"), super.getRequest().getParameter("timeStamp"));
		if(!b) {
			json.setSuccess(false);
			json.setMsg("数据有误！");
		}else {
			String mid=super.getRequest().getParameter("mid");
			String sign = super.getRequest().getParameter("sign");
			map1.put("mid", mid);
			String md5 = MD5Util.MD5((SimpleXmlUtil.getSignBlock(map1)+signEnd));
			if(md5.equals(sign)){
				if(mid!=null && !"".equals(mid)){
					try {
						DataGridBean dgb=phoneMerchantWalletService.queryMerchantBankCardListData(mid);
						List<Map<String,Object>> rows = (List<Map<String, Object>>) dgb.getRows();
						for (Map<String, Object> map : rows) {
							String bankAccNo = (String) map.get("BANKACCNO");
							String a = "";
							for(int i = 0;i<bankAccNo.length()-10;i++) {
								a += "*";
							}
							map.put("BANKACCNO", bankAccNo.substring(0, 6)+a+bankAccNo.substring(bankAccNo.length()-4));
						}
						Map<String, Object> map = rows.get(0);
						map1.clear();
						map1.put("BANKACCNO", (String) map.get("BANKACCNO"));
						map1.put("BANKACCNAME", (String) map.get("BANKACCNAME"));
						sign = MD5Util.MD5((SimpleXmlUtil.getSignBlock(map1)+signEnd));
						dgb.setRows(rows);
						json.setSuccess(true);
						json.setMsg("查询成功！");
						json.setObj(dgb);
						json.setSign(sign);
					} catch (Exception e) {
						log.info(e);
						json.setSuccess(false);
						json.setMsg("查询失败！");
					}
				}
			}else{
				json.setSuccess(false);
				json.setMsg("数据有误！");
			}
		}
		JsonBeanForSign json1 = new JsonBeanForSign();
		String data = JSON.toJSONStringWithDateFormat(json, "yyyy-MM-dd HH:mm:ss");
		String signs1 = SHAEncUtil.getSHA256Str(data);
		json1.setSign(signs1);
		json1.setData(data);
		super.writeJson(json1);
	}
	
	/**
	 * 查询银行卡(PC)
	 */
	public void queryMerchantBankCardListPC(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		DataGridBean dgb = new DataGridBean();
		if(mid!=null && !"".equals(mid)){
			try {
				dgb=phoneMerchantWalletService.queryMerchantBankCardListData(mid);
			} catch (Exception e) {
				log.info(e);
			}
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 银行卡修改
	 */
	
	public void updateMerchantBankCard(){
		JsonBean json = new JsonBean();
		Integer mbcid=Integer.parseInt(super.getRequest().getParameter("MBCID"));
		String bankAccNo=super.getRequest().getParameter("BANKACCNO");
		String payBankId=super.getRequest().getParameter("PAYBANKID");
		String bankAccName=super.getRequest().getParameter("BANKACCNAME");
		
		try {
			phoneMerchantWalletService.updateMerchantBankCardInfo(mbcid,bankAccNo,payBankId,bankAccName);
			json.setSuccess(true);
			json.setMsg("修改成功！");
		} catch (Exception e) {
			log.info(e);
			json.setSuccess(false);
			json.setMsg("修改银行卡信息失败！");
		}
		super.writeJson(json);
	}
	
	/**
	 * 自动结转到资产
	 */
	public void updateAutoToAsset(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		String ifauto = super.getRequest().getParameter("ifauto");
		try {
			Map<String ,Object>  map=phoneMerchantWalletService.updateAutoToAsset(mid,ifauto);
			json.setSuccess(true);
			json.setObj(map);
			json.setMsg("修改成功！");
		} catch (Exception e) {
			log.error("updateAutoToAsset 异常:"+e);
			json.setSuccess(false);
			json.setMsg("修改失败！");
		}
		
		super.writeJson(json);
	}
	
	public void querySettleListData(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		Integer page=Integer.parseInt(super.getRequest().getParameter("page"));
		Integer rows=Integer.parseInt(super.getRequest().getParameter("rows"));
		try {
			List<Map<String,Object>> list=phoneMerchantWalletService.findSettleListData(mid,page,rows);
			json.setObj(list);
			json.setSuccess(true);
			json.setMsg("结算记录查询成功！");
		} catch (Exception e) {
			log.error(e);
			json.setSuccess(false);
			json.setMsg("结算记录查询失败！");
		}
		super.writeJson(json);
	}

}
