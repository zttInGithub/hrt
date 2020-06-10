package com.hrt.biz.check.service;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.check.entity.pagebean.CheckWechatTxnDetailBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface CheckWechantTxnDetailService {

	/**
	 * 查询微信交易记录
	 */
	DataGridBean queryWechantTxnDetailInfo(CheckWechatTxnDetailBean checkWechatTxnDetailBean, UserBean user);
	/**
	 * 微信交易明细导出所有
	 */
	HSSFWorkbook wechantTxnExport(CheckWechatTxnDetailBean checkWechatTxnDetailBean,UserBean user);
	/**
	 * 查询微信交易商户明细汇总
	 */
	DataGridBean listCheckMerWechatdealData(CheckWechatTxnDetailBean checkWechatTxnDetailBean, UserBean user);
	/**
	 * 微信交易商户汇总 导出所有
	 */
//	HSSFWorkbook checkWechatTxnDetailExcelAll(CheckWechatTxnDetailBean checkWechatTxnDetailBean, UserBean user);
	void checkWechatTxnDetailExcelAll(CheckWechatTxnDetailBean checkWechatTxnDetailBean, UserBean user,HttpServletResponse resp);
	/**
	 * 查询微信交易机构明细汇总
	 */
	DataGridBean listCheckUnitWechatdealData(CheckWechatTxnDetailBean checkWechatTxnDetailBean, UserBean user);
	/**
	 * 微信交易机构汇总 导出所有
	 */
	HSSFWorkbook checkWechatTxnUnitDetailExcelAll(CheckWechatTxnDetailBean checkWechatTxnDetailBean, UserBean user);
	/**
	 * 微信交易退款
	 */
	//boolean saveWechantTxnRefund(CheckWechatTxnDetailBean checkWechatTxnDetailBean, UserBean user);
	/**
	 * 查看退款金额是否合规
	 */
	//boolean queryIfWechantTxnRefund(CheckWechatTxnDetailBean checkWechatTxnDetailBean);
	

}
