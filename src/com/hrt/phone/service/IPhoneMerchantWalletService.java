package com.hrt.phone.service;

import java.util.List;
import java.util.Map;

import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;

public interface IPhoneMerchantWalletService {

	Map<String, Object> queryBalance(String mid);

	Map<String, Object> queryCashWithDrawal(String mid, String money, String bankAccNo) throws Exception;

	boolean queryIsLimitDay(Integer type);

	JsonBean queryCashWithDrawalListData(String mid, Integer page, Integer rows, String startDay, String endDay);

	Map<String, Object> queryMerchantSettleCycle(String mid);

	boolean saveBankCard(String bankAccName, String bankAccNo, String mid, String payBankId, String bankBranch);

	DataGridBean queryMerchantBankCardListData(String mid);

	void deleteMerchantBankCard(String mid, String bankAccNo);

	void updateMerchantBankCardInfo(Integer mbcid, String bankAccNo,
			String payBankId, String bankAccName);

//	Map<String, Object> queryCashWithDrawal2(String mid, String money,
//			String bankAccNo) throws Exception;

	Map<String, Object> queryCashWithAsset(String mid, String money,
			String bankAccNo) throws Exception;

	Map<String, Object> queryAsset(String mid);
	
	Map<String, Object> queryMerAdjustRate(String mid);

	Map<String, Object> updateAutoToAsset(String mid, String ifauto);

	boolean isRiskImageUpLoad(String mid, String money);

	List<Map<String, Object>> findSettleListData(String mid, Integer page,
			Integer rows);

}
