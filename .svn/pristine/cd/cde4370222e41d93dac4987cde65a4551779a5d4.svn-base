package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.hrt.biz.bill.entity.pagebean.HotCardBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IHotCardService {

	DataGridBean queryHotCardMerchant(HotCardBean bean);

	void saveHotCardMerchant(HotCardBean bean, UserBean userSession);

	void updateHotCardMerchantInfo(HotCardBean bean, UserBean userSession);
	
	void updateBatchHotCardMerchant(HotCardBean bean, UserBean userSession);

	void deleteHotCardMerchantInfo(HotCardBean bean, UserBean userSession);

	List<Map<String,String>> saveimportHotCardMerchant(String xlsfile,UserBean user);
	
	List<Map<String, Object>> exportHotCardMerchant(HotCardBean bean);
}
