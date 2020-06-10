package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.entity.pagebean.MerchantTaskDataBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IMerchantTaskDataService {

	DataGridBean queryPage(MerchantTaskDataBean bean);

	boolean updateAuditThrough(MerchantTaskDataBean bean, UserBean userSession, String ids);

	boolean updateAuditReject(MerchantTaskDataBean bean, UserBean userSession, String ids);
 
	boolean updateAuditSingle(MerchantTaskDataBean bean, UserBean userSession);

	Map<String, Object> findDetail(Integer id);

	DataGridBean queryRiskPage(MerchantTaskDataBean bean);

	boolean updateAuditRiskThrough(MerchantTaskDataBean bean,
			UserBean userSession, Integer bmtkid);

	boolean updateAuditRiskSingle(MerchantTaskDataBean bean,
			UserBean userSession);

	HSSFWorkbook exportAll(MerchantTaskDataBean bean, UserBean userBean,
			String ids);

	DataGridBean queryChangeT0Page(MerchantTaskDataBean bean);

	boolean updateChangeT0Single(MerchantTaskDataBean bean, UserBean userSession);

	boolean updateMerchantLimit(MerchantTaskDataBean bean, UserBean userSession);

	List<Map<String, Object>> queryMerchantLimit4(UserBean userSession, String bmtkids);

	void updateTaskDataStatusById(Integer merTaskDataId, String status,UserBean user);

}
