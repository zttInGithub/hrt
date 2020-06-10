package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.pagebean.PurchaseInvoiceBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IPurchaseInvoiceService {
	DataGridBean listPurchaseInvoice(PurchaseInvoiceBean purchaseInvoiceBean,UserBean user);
	Integer savePurchaseInvoice(PurchaseInvoiceBean purchaseInvoiceBean,UserBean user);
	Integer updatePurchaseInvoiceStatus(PurchaseInvoiceBean purchaseInvoiceBean,UserBean user);
	Integer updatePurchaseInvoiceY(PurchaseInvoiceBean purchaseInvoiceBean,UserBean user);
	Integer updatePurchaseInvoiceK(PurchaseInvoiceBean purchaseInvoiceBean,UserBean user);
	Integer deletePurchaseInvoice(Integer piid,UserBean user);
	List<Map<String,Object>> exportPurchaseInvoice(PurchaseInvoiceBean purchaseInvoiceBean,UserBean user);
}
