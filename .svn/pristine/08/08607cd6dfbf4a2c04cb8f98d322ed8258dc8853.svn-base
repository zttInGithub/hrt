package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.model.PurchaseDetailModel;
import com.hrt.biz.bill.entity.pagebean.PurchaseDetailBean;
import com.hrt.biz.bill.entity.pagebean.PurchaseOrderBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IPurchaseDetailService {

	void savePurchaseDetail(PurchaseDetailModel purchaseDetailModel,UserBean user);

	/**
	 * 进销存订单详情Grid列表查询
	 * @param purchaseDetailBean
	 * @param user
	 * @return
	 */
	DataGridBean queryPurchaseDetail(PurchaseDetailBean purchaseDetailBean,UserBean user);

	/**
	 * 查询采购单&明细
	 * @param purchaseDetailBean
	 * @param user
	 * @return
	 */
	DataGridBean listPurchaseOrderAndDetail(PurchaseDetailBean purchaseDetailBean,UserBean user);

	void deletePurchaseDetail(Integer poid ,UserBean user);

	/**
	 * 删除订单退货详情,状态改为D
	 * @param pdid
	 * @param user
	 * @return
	 */
	Integer deletePurchaseDetailById(Integer pdid ,UserBean user);

	DataGridBean queryMachineModel();
	DataGridBean listPurConfigure(String type);
	List<Map<String, Object>> exportPurchase(PurchaseDetailBean purchaseDetailBean,UserBean user);

	/**
	 * 保存退货订单详情
	 * @param integer
	 * @param integer2
	 * @param returnNum
	 * @param loginName
	 */
	void savePurchaseDetailOrder(Integer integer, Integer integer2, String returnNum, String loginName);

	/**
	 * 获取同机型剩余可退货数量
	 * @param purchaseDetailBean
	 * @return
	 */
	List<Map<String, Object>> queryRemainMachinenum(PurchaseDetailBean purchaseDetailBean);

	DataGridBean listReturnOrderAndDetail(PurchaseDetailBean purchaseDetailBean, UserBean userBean);
	List<Map<String, Object>> exportReturnOrderAndDetail(PurchaseDetailBean purchaseDetailBean);
	DataGridBean queryPurchaseDetailById(PurchaseDetailBean purchaseDetailBean, UserBean user);
	Integer updateReturnDetail(PurchaseDetailBean purchaseDetailBean, UserBean userBean);
	Integer updateReturnDetail2(PurchaseDetailBean purchaseDetailBean, UserBean userBean);
	DataGridBean queryPurchaseDetailByPoid(PurchaseDetailBean purchaseDetailBean, UserBean userBean);
	Integer updateBackReturnDetail(PurchaseDetailBean purchaseDetailBean, UserBean userBean);
	List<Map<String, Object>> exportPurchaseOrderAndDetailForTongJi(PurchaseDetailBean purchaseDetailBean, UserBean userBean);
	Integer updatePurchaseDetailCancel(PurchaseOrderBean purchaseOrderBean, UserBean userBean);

	/**
	 * 查询采购单&明细
	 * @param purchaseDetailBean
	 * @param user
	 * @return
	 */
	DataGridBean listPurchaseOrderAndDetailForTongJi(PurchaseDetailBean purchaseDetailBean,UserBean user);

	Integer updatePurchaseDetail(PurchaseDetailBean purchaseDetailBean,UserBean user);
	List<Map<String, Object>> emportPurchaseInfo(PurchaseDetailBean purchaseDetailBean);
	DataGridBean queryCenterCheckout(PurchaseDetailBean purchaseDetailBean);

	/**
	 * 导出中心设备出库情况数据
	 * @param purchaseDetailBean
	 * @return
	 */
	List<Map<String, Object>> exportCenterCheckout(PurchaseDetailBean purchaseDetailBean);
	
}
