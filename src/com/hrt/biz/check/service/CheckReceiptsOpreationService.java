package com.hrt.biz.check.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.check.entity.pagebean.ReceiptsDataBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface CheckReceiptsOpreationService {

	DataGridBean queryReceiptsAuditList(ReceiptsDataBean receiptsDataBean);
	
	String queryUploadPath();

	void updateIfSettleFlagYes(Integer pkid,String mid,String money);

	void updateIfSettleFlagNo(ReceiptsDataBean receiptsDataBean);

	List<Map<String, String>> queryAuditReceiptsNoMerchantInfo(
			ReceiptsDataBean receiptsDataBean);
	
	//插入小票审核表
	boolean insertBlockTradeDetail(String mid,String cashmode);
	//风控小票审核list
	DataGridBean queryTransReceiptsAuditList(ReceiptsDataBean receiptsDataBean);

	List<Map<String, String>> queryAuditReceiptsNoMerchantInfo(
			ReceiptsDataBean receiptsDataBean, String ids);

	String queryRiskUploadPath();

	List<Map<String, String>> queryRiskAuditReceiptsNoMerchantInfo(
			ReceiptsDataBean receiptsDataBean);

	List<Map<String, String>> queryRiskAuditReceiptsMerchantInfo(
			ReceiptsDataBean receiptsDataBean);

	DataGridBean queryRiskReceiptsAuditList(ReceiptsDataBean receiptsDataBean,UserBean userBean);

	String queryImagePath(Integer pkid, int type);
	
	/**
	 * 增加风险商户（卡片审核）
	 */
	public String addRiskMerchantInfo(String mid);
	
}
