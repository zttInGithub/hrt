package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.pagebean.PaymentRiskBean;
import com.hrt.frame.entity.pagebean.DataGridBean;


public interface IPaymentRiskService {
	//登录
	public Map login(PaymentRiskBean paymentRiskBean,String realPath) throws Exception;
	//登出
	public Map logout(PaymentRiskBean paymentRiskBean) throws Exception;
	//个人信息上报
	public Map reportPersonal(PaymentRiskBean paymentRiskBean) throws Exception;
	//个人信息上报查询
	public DataGridBean queryBeenPersonal(PaymentRiskBean paymentRiskBean) throws Exception;
	//个人信息上报成功信息
	public void savePersonal (PaymentRiskBean paymentRiskBean);
	//商户信息上报查询
	public DataGridBean queryBeenMerchant(PaymentRiskBean paymentRiskBean) throws Exception;
	//商户信息上报
	public Map reportMerchant(PaymentRiskBean paymentRiskBean) throws Exception;
	//商户信息上报成功信息
	public void saveMerchant (PaymentRiskBean paymentRiskBean);
	//个人风险信息查询
	public Map queryPersonal(PaymentRiskBean paymentRiskBean)throws Exception;
	//商户风险信息查询
	public Map queryMerchant(PaymentRiskBean paymentRiskBean)throws Exception;
	//批量导入信息查询
	public Map queryBatchImport(PaymentRiskBean paymentRiskBean) throws Exception;;
	//风险信息汇总查询
	public Map queryConfluence(PaymentRiskBean paymentRiskBean) throws Exception;;
	//个人风险信息变更之查询
	public Map queryPersonalChange(PaymentRiskBean paymentRiskBean) throws Exception;;
	//个人风险信息变更之修改
	public Map updatePersonalChange(PaymentRiskBean paymentRiskBean) throws Exception;;
	//商户风险信息变更之查询
	public Map queryMerchantChange(PaymentRiskBean paymentRiskBean) throws Exception;;
	//商户风险信息变更之修改
	public Map updateMerchantChange(PaymentRiskBean paymentRiskBean) throws Exception;
	
	public List queryDictionary1(String menuid) throws Exception;
	
	public String queryResult(String ResultCode);
	
	
	
}
