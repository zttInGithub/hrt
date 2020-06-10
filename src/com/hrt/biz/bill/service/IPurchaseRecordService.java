package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.pagebean.PurchaseRecordBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IPurchaseRecordService {
	
	/**
	 * 查询来款记录
	 * @param purchaseRecordBean
	 * @param userBean
	 * @return
	 */
	DataGridBean queryPurchaseRecord(PurchaseRecordBean purchaseRecordBean, UserBean userBean);
	/**
	 * 新增来款
	 * @param purchaseRecordBean
	 * @param userBean
	 */
	void savePurchaseRecord(PurchaseRecordBean purchaseRecordBean, UserBean userBean);
	/**
	 * 查询订单ID
	 * @param purchaseRecordBean
	 * @return
	 */
	List<Map<String, Object>> queryPurchaseOrder(PurchaseRecordBean purchaseRecordBean);
	/**
	 * 来款匹配
	 * @param poid
	 * @param orderID
	 * @param purchaserName 
	 * @param unno 
	 * @param purchaseRecordBean 
	 * @param userBean
	 */
	void updatePurchaseRecord(String poid, String orderID, String unno, String purchaserName, PurchaseRecordBean purchaseRecordBean, UserBean userBean);
	/**
	 * 退回来款
	 * @param purchaseRecordBean
	 * @param userBean
	 */
	void returnPurchaseRecord(PurchaseRecordBean purchaseRecordBean, UserBean userBean);
	void deletePurchaseRecord(PurchaseRecordBean purchaseRecordBean, UserBean userBean);
	Integer confirmPurchaseRecord(PurchaseRecordBean purchaseRecordBean, UserBean userBean);
	DataGridBean listArraiveWay();
	/**
	 * 查询主订单ID（通用）
	 * @param province
	 * @return
	 */
	DataGridBean searchPurchaseOrderByOID(String orderID);
	/**
	 * 来款导入
	 * @param xlsfile
	 * @param fileName
	 * @param loginName
	 * @return
	 */
	List<Map<String, String>> addPurchaseRecordbyFile(String xlsfile, String fileName, String loginName);
	/**
	 * 来款总金额查询
	 * @param purchaseRecordBean
	 * @return
	 */
	List<Map<String, Object>> queryPurchaseOrderByID(PurchaseRecordBean purchaseRecordBean);
	/**
	 * 来款导出
	 * @param purchaseRecordBean
	 * @param userBean
	 * @return
	 */
	List<Map<String, Object>> exportPurchaseRecord(PurchaseRecordBean purchaseRecordBean, UserBean userBean);

}
