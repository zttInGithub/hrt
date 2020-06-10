package com.hrt.phone.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.phone.entity.pagebean.GateOrderBean;

public interface GateCarOrderService {

	List<Map<String, String>> queryOrdersDetailBySql(GateOrderBean gateOrderBean);
	
	Map<String, String> saveOrdersDetail(GateOrderBean gateOrderBean);

	String saveAppOrdersDetail(GateOrderBean gateOrderBean);
	
	boolean updateOrdersNotification(JSONObject reqJson);
	
	DataGridBean queryOrders(GateOrderBean gateOrderBean);
	
	Integer updateGateCarInfo(String mid,String orderId,String imageId);
	
	String saveOrderAmount(GateOrderBean gateOrderBean);
	
	Map<String, String> updateOrderDetail(GateOrderBean gateOrderBean);
	
	Map<String, String> updateOrderCancel(GateOrderBean gateOrderBean);
	
}
