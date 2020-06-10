package com.hrt.biz.credit.action;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.credit.entity.pagebean.CreditInfoBean;
import com.hrt.biz.credit.entity.pagebean.LoanInfoBean;
import com.hrt.biz.credit.service.CreditAgentService;
import com.hrt.biz.credit.service.LoanService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

/**
 *	@author tenglong
 *	2017-01-5
 *	贷款授信代理基本业务
 */
public class LoanAction extends BaseAction implements ModelDriven<LoanInfoBean> {

	
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(LoanAction.class);
	
	private LoanInfoBean loanInfoBean = new LoanInfoBean();
	private LoanService loanService;
	
	@Override
	public LoanInfoBean getModel() {
		return loanInfoBean;
	}

	public LoanInfoBean getLoanInfoBean() {
		return loanInfoBean;
	}

	public void setLoanInfoBean(LoanInfoBean loanInfoBean) {
		this.loanInfoBean = loanInfoBean;
	}


	public LoanService getLoanService() {
		return loanService;
	}

	public void setLoanService(LoanService loanService) {
		this.loanService = loanService;
	}

	/**
	 * 贷款申请
	 */
	public void addLoanApply(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		JsonBean json = new JsonBean();
		try {
			json=loanService.addLoanApply(loanInfoBean,user);
		} catch (Exception e) {
			log.info("贷款申请异常"+e);
		}
		//json.setSuccess(flag);
		super.writeJson(json);
	}
	
}
