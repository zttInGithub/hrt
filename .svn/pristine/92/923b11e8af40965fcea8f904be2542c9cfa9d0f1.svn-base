package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.pagebean.PurchaseAccountBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IPurchaseAccountService {
	DataGridBean listPurchaseAccount(PurchaseAccountBean purchaseAccountBean,UserBean user);
	Integer savePurchaseAccount(PurchaseAccountBean purchaseAccountBean,UserBean user);
	Integer updatePurchaseAccountStatus(PurchaseAccountBean purchaseAccountBean,UserBean user);
	Integer updatePurchaseAccountY(PurchaseAccountBean purchaseAccountBean,UserBean user);
	Integer updatePurchaseAccountK(PurchaseAccountBean purchaseAccountBean,UserBean user);
	Integer updatePurchaseAccount(PurchaseAccountBean purchaseAccountBean,UserBean user);
	List<Map<String, Object>> exportPurchaseAccount(PurchaseAccountBean purchaseAccountBean,UserBean user);
	Integer deletePurchaseAccount(Integer paid,UserBean user);
}
