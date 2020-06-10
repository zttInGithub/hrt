package com.hrt.biz.bill.service;

import java.util.Map;

import com.hrt.biz.bill.entity.pagebean.RiskSpecialMerchantBean;
import com.hrt.frame.entity.pagebean.DataGridBean;

public interface IRiskSpecialMerchantService {

	DataGridBean querySpecialPersonal(RiskSpecialMerchantBean specialMerchantBean);

	Map reportSpecialPersonal(RiskSpecialMerchantBean specialMerchantBean) throws Exception;
	
	public String queryMerchantResult(String ResultCode);

	void saveMerchantPersonal(RiskSpecialMerchantBean specialMerchantBean);

	Map reportSpecialMerchant(RiskSpecialMerchantBean specialMerchantBean) throws Exception;

	void addPushMsgList(Map requestXml) throws Exception;
	
	public String repPushMsgList(String RecSystemId,boolean flag) throws Exception;

	void addPendingMerList(Map requestXml)  throws Exception;

	DataGridBean queryPushMsgList(RiskSpecialMerchantBean specialMerchantBean);

	DataGridBean queryPushMsg(RiskSpecialMerchantBean specialMerchantBean);

}
