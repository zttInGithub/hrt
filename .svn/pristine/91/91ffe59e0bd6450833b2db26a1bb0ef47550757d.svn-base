package com.hrt.biz.check.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.check.entity.model.CheckReFundModel;
import com.hrt.biz.check.entity.pagebean.CheckReFundBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface CheckReFundService {

	/**
	 * 查询退款记录
	 */
	DataGridBean queryRefundInfo(CheckReFundBean checkErFundBean, UserBean user);
	/**
	 * 聚合商户会员宝后台退款发起
	 */
	String saveHybRefundRequest(CheckReFundBean checkErFundBean);
	
	List<CheckReFundModel> queryHybRefund(CheckReFundBean checkErFundBean);
	/**
	 * 聚合商户会员宝后台退款发起
	 */
	String updateHybRefundRequest(CheckReFundBean checkErFundBean);
	/**
	 * 退款信息匹配综合交易记录
	 */
	DataGridBean queryAdmTxnInfo(CheckReFundBean checkErFundBean, UserBean user);
	/**
	 * 查询商户是否归属此商户
	 */
	Integer queryMidInUsr(CheckReFundBean checkErFundBean, UserBean user);
	/**
	 * 导入差错文件（Excell）
	 * @return 
	 */
	List<String> saveImportRefundInfo(String xlsfile, UserBean user, String fileName);
	/**
	 * 提交
	 * @return 
	 */
	Integer saveRefundInfoInfoC(CheckReFundBean checkErFundBean, UserBean user);
	/**
	 * 提交
	 * @return 
	 */
	Integer updateErfundInfo(CheckReFundBean checkErFundBean,String refids, UserBean user);
	/**
	 *  退款审核
	 * @return 
	 */
	boolean updateRefund(JSONObject reqJson);
	
	List<Map<String, Object>> exportRefundInfo(CheckReFundBean checkReFundBean, UserBean user);

	Integer updateErfundInfo2(CheckReFundBean checkErFundBean,String refids, UserBean user);
}
