package com.hrt.biz.bill.service;


import java.util.Date;
import java.util.List;

import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IMerchantTaskOperateService {

	/**
	 * 方法功能：查询商户待审核工单基本信息
	 * 参数：unno ,approveStatus,page,rows
	 * 返回值：
	 * 异常： 
	 */
	DataGridBean queryMerchantTaskData(UserBean user, String approveStatus,Integer page, Integer rows, String mid, String startDay, String endDay);

	/**
	 * 方法功能：查询商户基本信息工单
	 * 参数：bmtkid
	 * 返回值：
	 * 异常： 
	 */
	List<Object> queryMerchantTaskDetail1(Integer bmtkid);

	/**
	 * 方法功能：查询商户银行信息工单
	 * 参数：bmtkid
	 * 返回值：
	 * 异常： 
	 */
	List<Object> queryMerchantTaskDetail2(Integer bmtkid);

	/**
	 * 方法功能：查询商户费率信息工单
	 * 参数：bmtkid
	 * 返回值：
	 * 异常： 
	 */
	List<Object> queryMerchantTaskDetail3(Integer bmtkid);

	/**
	 * 方法功能：商户取消工单
	 * 参数：bmtkid
	 * 返回值：
	 * 异常： 
	 */
	boolean deleteMerchantTaskDetail(Integer bmtkid);

	DataGridBean queryMerchantRiskTaskData(UserBean user, String approveStatus,
			Integer page, Integer rows, String mid, String startDay,
			String endDay,String mtype);

	List<Object> queryMerchantTaskDetail4(Integer bmtkid);

	Object queryMerchantTaskDetail5(Integer bmtkid, String mid);

	
	
	/**
	 * 方法功能：商户批量取消工单
	 * 参数：ids
	 * 返回值：
	 * 异常： 
	 */
//	void deleteMoneyMerchantTaskDetail(String ids);
}
