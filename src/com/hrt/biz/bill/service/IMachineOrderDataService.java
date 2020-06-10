package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.model.MachineOrderDataModel;
import com.hrt.biz.bill.entity.pagebean.MachineOrderDataBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IMachineOrderDataService {
	
	MachineOrderDataModel getOrderModel(Integer id);
	
	/**
	 * 分页查询机具订单
	 */
	DataGridBean queryOrders(MachineOrderDataBean machineOrderData, UserBean user) throws Exception;

	void save(MachineOrderDataBean machineOrderDataBean, UserBean userSession, String[] values);

	void delete(Integer parseInt);

	void updateSup(MachineOrderDataBean machineOrderDataBean, UserBean userSession);
	
	void updateSend(MachineOrderDataBean machineOrderDataBean, UserBean userSession);

	/**
	 * 查询订单详细信息
	 */
	List<Map<String, String>> getOrder(Integer ids) throws Exception;
	
	Integer getMaxId();

	List<MachineOrderDataModel> getOrderData(MachineOrderDataBean machineOrderData) throws Exception;

	void updateApp(MachineOrderDataBean machineOrderDataBean, UserBean user) throws Exception;
}
