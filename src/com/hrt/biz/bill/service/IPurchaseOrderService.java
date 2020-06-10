package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.model.PurchaseOrderModel;
import com.hrt.biz.bill.entity.pagebean.PurchaseOrderBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IPurchaseOrderService {
	DataGridBean queryPurchaseOrders(PurchaseOrderBean purchaseOrderBean,UserBean user);

	/**
	 * 新增进销存订单
	 * @param purchaseOrderBean
	 * @param user
	 * @return
	 */
	PurchaseOrderModel savePurchaseOrder(PurchaseOrderBean purchaseOrderBean,UserBean user);

	Integer deletePurchaseOrder(Integer poid ,UserBean user);

	/**
	 * 重新计算订单总额总数
	 * @param poid
	 * @return 成功true 未查询到订单false
	 */
	boolean saveNewPurchaseOrder(Integer poid);

    /**
     * 修改采购单
     * @param purchaseOrderBean
     * @param user
     * @return
     */
	PurchaseOrderModel updatePurchaseOrder(PurchaseOrderBean purchaseOrderBean,UserBean user);

	Integer updatePurchaseOrderStatus(Integer poid ,UserBean user);
	Integer updateEditPurchaseOrder(Integer poid ,UserBean user);
	Integer updatePurchaseOrderStatusToEdit(Integer poid ,UserBean user);
	DataGridBean queryPurchaseOrderWJoin(PurchaseOrderBean purchaseOrderBean,UserBean user);
	Integer updatePurchaseOrderY(PurchaseOrderBean purchaseOrderBean,UserBean user);
	Integer updatePurchaseOrderK(PurchaseOrderBean purchaseOrderBean,UserBean user);
	Integer updatePurchaseOrderAgainY(PurchaseOrderBean purchaseOrderBean,UserBean user);
	Integer updatePurchaseOrderAgainK(PurchaseOrderBean purchaseOrderBean,UserBean user);
	List<Map<String, Object>> exportPurchaseOrderWJoin(PurchaseOrderBean purchaseOrderBean,UserBean user);

	/**
	 * 进销存订单是否存在
	 * @param orderID 订单号
	 * @return
	 */
	boolean findOrderID(String orderID);

	Integer queryPurchaseOrderCancel(PurchaseOrderBean purchaseOrderBean,UserBean user);
	DataGridBean listCancelPurchaseOrder(PurchaseOrderBean purchaseOrderBean,UserBean user);
	DataGridBean queryPOSAccounts(PurchaseOrderBean purchaseOrderBean,UserBean user);
	List<Map<String, Object>> exportPOSAccounts(PurchaseOrderBean purchaseOrderBean,UserBean user);
	DataGridBean queryPOSProfitAndLoss(PurchaseOrderBean purchaseOrderBean,UserBean user);
	List<Map<String, Object>> exportPOSProfitAndLoss(PurchaseOrderBean purchaseOrderBean,UserBean user);
	DataGridBean queryPOSIncome(PurchaseOrderBean purchaseOrderBean,UserBean user);
	List<Map<String, Object>> exportPOSIncome(PurchaseOrderBean purchaseOrderBean,UserBean user);

	/**
	 * 活动押金列表选择
	 * @param purchaseOrderBean
	 * @param user
	 * @return
	 */
	DataGridBean listDepositAmtByPurConfigure(PurchaseOrderBean purchaseOrderBean,UserBean user);
}
