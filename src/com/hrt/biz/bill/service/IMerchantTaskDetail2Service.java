package com.hrt.biz.bill.service;

import java.util.List;

import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDataModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail2Model;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IMerchantTaskDetail2Service {
	/**
	 * 根据mid判断是否存在未审批的工单
	 */
	boolean queryMerchantTaskDetailByMid(String mid);

	/**
	 * base64图片码保存
	 * @param upload
	 * @param realPath
	 * @param fName
	 */
	void uploadFile2byteWeChat(String upload, String realPath, String fName);

	/**
	 * 方法功能：查询商户银行信息
	 * 参数：unno 
	 * 返回值：DataGridBean实例
	 * 异常：
	 */
	List<MerchantInfoModel> queryMerchantInfo(String mid,UserBean user);
	
	/**
	 * 方法功能：保存商户基本信息工单申请信息
	 * 参数：MerchantTaskDetail2
	 * 返回值：void
	 * 异常：
	 */
	MerchantTaskDataModel saveMerchantTaskDetail2(MerchantTaskDetail2Model merchantTaskDetail2,String unno,String mid,UserBean user);
	
	/**
	 * 方法功能：获取上传文件路径
	 * 参数：
	 * 返回值：String
	 * 异常：
	 */
	String queryUpLoadPath();
}
