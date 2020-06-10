package com.hrt.biz.bill.action;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.entity.pagebean.LoanApplicationBean;
import com.hrt.biz.bill.service.LoanApplicationService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

/**
 *	@author lxp
 *	2018-05-17
 *	贷款申请
 */
public class LoanApplicationAction extends BaseAction implements ModelDriven<LoanApplicationBean> {

	
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(LoanApplicationAction.class);
	
	private LoanApplicationBean loanApplicationBean = new LoanApplicationBean();
	private LoanApplicationService loanApplicationService;
	
	@Override
	public LoanApplicationBean getModel() {
		return loanApplicationBean;
	}

	public LoanApplicationService getLoanApplicationService() {
		return loanApplicationService;
	}

	public void setLoanApplicationService(LoanApplicationService loanApplicationService) {
		this.loanApplicationService = loanApplicationService;
	}

	/**
	 * 贷款申请
	 */
	public void addLoanApply(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		JsonBean json = new JsonBean();
		try {
			Integer i = loanApplicationService.saveLoanApply(loanApplicationBean,user);
			if(i==1){
				json.setMsg("您的申请已成功提交，我们会在两个工作日内联系您，请保持电话畅通，谢谢！");
				json.setSuccess(true);
			}else{
				json.setMsg("申请失败，已提交申请");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			log.info("贷款申请异常"+e);
			json.setMsg("申请失败");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	/**
	 * 贷款查询判断当前机构是否已经申请
	 */
	public void queryLoanApply(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		JsonBean json = new JsonBean();
		try {
			Integer i = loanApplicationService.queryLoanApply(user);
			if(i==1){//已有记录
				json.setSuccess(false);
			}else{//未申请
				json.setSuccess(true);
			}
		} catch (Exception e) {
			json.setSuccess(false);
			log.info("查询贷款申请异常"+e);
		}
		super.writeJson(json);
	}
	/**
	 * 查询当前机构的贷款额度
	 */
	public void queryLoanQuota(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		JsonBean json = new JsonBean();
		try {
			Integer i = loanApplicationService.queryLoanQuota(user);
			if(i==0){//小于10000，不显示最高额度
				json.setSuccess(false);
			}else{
				json.setMsg(i+"");
				json.setSuccess(true);
			}
		} catch (Exception e) {
			json.setSuccess(false);
			log.info("查询贷款额度异常"+e);
		}
		super.writeJson(json);
	}
	/**
	 * 查询贷款申请信息
	 */
	public void queryLoanList(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = loanApplicationService.queryLoanList(loanApplicationBean,user);
		} catch (Exception e) {
			log.error("分页查询贷款申请信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	/**
	 * 导出贷款申请信息
	 */
	public void exportLoan(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=loanApplicationService.exportLoan(loanApplicationBean);
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("贷款申请".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出Excel成功");
			} catch (Exception e) {
				log.error("导出Excel异常：" + e);
				json.setMsg("导出Excel失败");
			}
		}
	}
	/**
	 * 分配
	 */
	public void saveBusName(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		JsonBean json = new JsonBean();
		try {
			Integer i = loanApplicationService.saveBusName(loanApplicationBean,user);
			if(i==0){
				json.setMsg("分配失败");
				json.setSuccess(false);
			}else{
				json.setMsg("分配成功");
				json.setSuccess(true);
			}
		} catch (Exception e) {
			json.setMsg("分配失败");
			json.setSuccess(false);
			log.info("分配异常"+e);
		}
		super.writeJson(json);
	}
	/**
	 * 查询贷款意愿
	 */
	public void queryLoanApplication(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = loanApplicationService.queryLoanApplication(loanApplicationBean,user);
		} catch (Exception e) {
			log.error("分页查询贷款申请信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	/**
	 * 导出贷款申请意愿
	 */
	public void exportLoanApplication(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=loanApplicationService.exportLoanApplication(loanApplicationBean);
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("贷款申请意愿".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出Excel成功");
			} catch (Exception e) {
				log.error("导出Excel异常：" + e);
				json.setMsg("导出Excel失败");
			}
		}
	}
}
