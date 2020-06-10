package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.pagebean.PurchaseDeliverBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IPurchaseDeliverService {
	DataGridBean queryPurchaseDeliver(PurchaseDeliverBean purchaseDeliverBean,UserBean user);

	/**
	 * queryPurchaseDeliverAndDetail
	 * @param purchaseDeliverBean
	 * @param user
	 * @return
	 */
	DataGridBean queryPurchaseDeliverAndDetail(PurchaseDeliverBean purchaseDeliverBean,UserBean user);

	Integer saveSendOutMachine(PurchaseDeliverBean purchaseDeliverBean,UserBean user);
	Integer addDeliverInfo(PurchaseDeliverBean purchaseDeliverBean,UserBean user);
	Integer updatePurchaseDeliver(PurchaseDeliverBean purchaseDeliverBean,UserBean user);
	Integer updateDeliverInfo(PurchaseDeliverBean purchaseDeliverBean,UserBean user);
	List<Map<String, Object>> exportPurchaseDeliver(PurchaseDeliverBean purchaseDeliverBean,UserBean user);
}
