package com.hrt.biz.check.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.check.entity.pagebean.CheckCashBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface CheckCashService {

	/**
	 * 查询提现记录
	 */
	DataGridBean queryCashListInfo(CheckCashBean checkCashBean, UserBean user);
	
	/**
	 * 导出提现记录
	 */
	HSSFWorkbook exportCashData(CheckCashBean checkCashBean, UserBean user);

}
