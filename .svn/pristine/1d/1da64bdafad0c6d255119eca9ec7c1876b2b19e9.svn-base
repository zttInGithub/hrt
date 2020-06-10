package com.hrt.biz.credit.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.credit.entity.pagebean.CreditInfoBean;
import com.hrt.biz.credit.entity.pagebean.LoanRepayInfoBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface LoanRepayService {

	/**
	 * 还款申请
	 */
	Map<String, Object> LoanRepay(LoanRepayInfoBean loanRepayInfoBean, UserBean user);
	/**
	 * 查询还款记录
	 */
	DataGridBean queryRepayInfo(LoanRepayInfoBean loanRepayInfoBean, UserBean user);
	/**
	 * 查询扣款记录
	 */
	DataGridBean queryDeAmtHist(LoanRepayInfoBean loanRepayInfoBean, UserBean user);
}
