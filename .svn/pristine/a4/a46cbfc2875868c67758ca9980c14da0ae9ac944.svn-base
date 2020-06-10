package com.hrt.biz.credit.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.biz.credit.entity.pagebean.CreditInfoBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface CreditAgentService {

	/**
	 * 查询微信交易记录
	 */
	Map<String,String> queryAvailableLimit(CreditInfoBean creditInfoBean, UserBean user);
	/**
	 * 微信交易明细导出所有
	 */
	HSSFWorkbook wechantTxnExport(CreditInfoBean creditInfoBean,UserBean user);
	/**
	 * 查询贷款记录
	 */
	DataGridBean queryCreditInfoData(CreditInfoBean creditInfoBean, UserBean user);
	/**
	 * 报单代理开通推送到贷款系统
	 */
	boolean saveCreditAgent(AgentUnitModel agentUnitModel, UserBean user);
	/**
	 * 微信交易商户汇总 导出所有
	 */
	HSSFWorkbook checkWechatTxnDetailExcelAll(CreditInfoBean creditInfoBean, UserBean user);
	/**
	 * 微信交易机构汇总 导出所有
	 */
	HSSFWorkbook checkWechatTxnUnitDetailExcelAll(CreditInfoBean creditInfoBean, UserBean user);
	/**
	 * 微信交易退款
	 */
	//boolean saveWechantTxnRefund(CheckWechatTxnDetailBean checkWechatTxnDetailBean, UserBean user);
	/**
	 * 查看退款金额是否合规
	 */
	//boolean queryIfWechantTxnRefund(CheckWechatTxnDetailBean checkWechatTxnDetailBean);
	

}
