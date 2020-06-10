package com.hrt.biz.check.action;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.check.entity.pagebean.CheckCashBean;
import com.hrt.biz.check.service.CheckCashService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;


public class CheckCashAction extends BaseAction implements ModelDriven<CheckCashBean> {

	/**
	 *	@author XXX 
	 *	2015-09-21
	 *	提现记录
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(CheckCashAction.class);
	
	private CheckCashBean checkCashBean =new CheckCashBean();
	private CheckCashService checkCashService;
	
	@Override
	public CheckCashBean getModel() {
		return checkCashBean;
	}

	public CheckCashService getCheckCashService() {
		return checkCashService;
	}

	public void setCheckCashService(CheckCashService checkCashService) {
		this.checkCashService = checkCashService;
	}

	
	/**
	 * 查询提现记录
	 */
	public void queryCashListInfo(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb=checkCashService.queryCashListInfo(checkCashBean,user);
		} catch (Exception e) {
			log.info(e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 导出提现记录（Excell）
	 */
	public void listCashDataExcel(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		try{
			HSSFWorkbook wb=checkCashService.exportCashData(checkCashBean,user);
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + new String("商户提现记录导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
	        OutputStream ouputStream= response.getOutputStream();
	        wb.write(ouputStream);  
	        ouputStream.flush();  
	        ouputStream.close();
			json.setSuccess(true);
			json.setMsg("导出提现记录Excel成功");
		} catch (Exception e) {
			log.error("导出提现记录Excel异常：" + e);
			json.setMsg("导出提现记录Excel失败");
			json.setSuccess(false);
		}
	}
}
