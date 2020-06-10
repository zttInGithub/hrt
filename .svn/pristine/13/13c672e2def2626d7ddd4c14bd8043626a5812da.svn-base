package com.hrt.biz.bill.service;

import java.util.List;

import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail3Model;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IMerchantTaskDetail3Service {
	/**
	 * 根据mid判断是否存在未审批的工单
	 */
	boolean queryMerchantTaskDetailByMid(String mid);
	/**
	 * 方法功能：查询商户费率信息
	 * 参数：unno 
	 * 返回值：DataGridBean实例
	 * 异常：
	 */
	List<MerchantInfoModel> queryMerchantInfo(String mid,UserBean user);
	
	/**
	 * 方法功能：保存商户基本信息工单申请信息
	 * 参数：MerchantTaskDetail1
	 * 返回值：Integer
	 * 异常：
	 */
	Integer saveMerchantTaskDetail3(MerchantTaskDetail3Model merchantTaskDetail3,String unno,String mid,Double maxAmt,UserBean user);

	/**
	 * 方法功能：获取上传文件路径
	 * 参数：
	 * 返回值：String 
	 * 异常：
	 */
	String queryUpLoadPath();

	/**
	 * 商户是否在temp表中存在,存在的商户不允许做费率变更
	 * @param mid
	 * @return
	 */
	boolean midExistsTemp(String mid);
	/**
	 * 商户是否有90活动存在,存在的商户不允许做费率变更
	 * @param mid
	 * @return
	 */
	boolean judgeActiveIsMatching(String sendMid);
	/**
	 * 商户是为特殊机构下，不允许修改工单
	 * @param mid
	 * @return
	 */
	boolean judgeLimitUnno(String sendMid,String unno);
	/**
	 * 商户是为特殊费率配置的中心机构下，查询是否是相应特殊活动
	 * @param mid
	 * @return
	 */
	String judgeIsSpecialUnno(String sendMid);
}
