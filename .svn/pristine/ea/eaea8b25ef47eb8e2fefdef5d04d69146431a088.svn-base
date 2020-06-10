package com.hrt.biz.bill.service;

import java.util.List;

import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail1Model;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IMerchantTaskDetail1Service {
	/**
	 * 根据mid判断是否存在未审批的工单
	 */
	boolean queryMerchantTaskDetailByMid(String mid);
	
	/**
	 * 方法功能：查询商户基本信息
	 * 参数：unno 
	 * 返回值：DataGridBean实例
	 * 异常：
	 */
	List<MerchantInfoModel> queryMerchantInfo(String mid,UserBean userSession);
	
	/**
	 * 方法功能：保存商户基本信息工单申请信息
	 * 参数：MerchantTaskDetail1
	 * 返回值：void
	 * 异常：
	 */
	void saveMerchantTaskDetail1(MerchantTaskDetail1Model merchantTaskDetail1,String unno,String mid,UserBean user);
	
	/**
	 * 方法功能：查询上传文件路径
	 * 参数：
	 * 返回值：String
	 * 异常：
	 */
	String queryUpLoadPath();

	/**
	 * 方法功能：查询下载文件路径
	 * 参数：
	 * 返回值：String
	 * 异常：
	 */ 
	String queryDownloadPath();
}
