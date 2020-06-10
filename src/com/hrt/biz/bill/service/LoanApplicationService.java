package com.hrt.biz.bill.service;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.entity.pagebean.LoanApplicationBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface LoanApplicationService {

	/**
	 * 贷款申请
	 */
	Integer saveLoanApply(LoanApplicationBean loanApplicationBean, UserBean user);
	/**
	 * 贷款查询
	 */
	Integer queryLoanApply(UserBean user);
	/**
	 * 贷款查询
	 */
	Integer queryLoanQuota(UserBean user);
	/**
	 * 查询贷款申请
	 */
	DataGridBean queryLoanList(LoanApplicationBean loanApplicationBean, UserBean user);
	HSSFWorkbook exportLoan(LoanApplicationBean loanApplicationBean);
	Integer saveBusName(LoanApplicationBean loanApplicationBean, UserBean user);
	DataGridBean queryLoanApplication(LoanApplicationBean loanApplicationBean, UserBean user);
	HSSFWorkbook exportLoanApplication(LoanApplicationBean loanApplicationBean);
}
