package com.hrt.biz.check.action;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.check.entity.pagebean.CheckWechatTxnDetailBean;
import com.hrt.biz.check.service.CheckWechantTxnDetailService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

/**
 *	@author tenglong
 *	2016-05-10
 *	微信支付交易
 */
public class CheckWechatTxnDetailAction extends BaseAction implements ModelDriven<CheckWechatTxnDetailBean> {

	
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(CheckWechatTxnDetailAction.class);
	
	private CheckWechatTxnDetailBean checkWechatTxnDetailBean =new CheckWechatTxnDetailBean();
	private CheckWechantTxnDetailService checkWechantTxnDetailService;
	
	@Override
	public CheckWechatTxnDetailBean getModel() {
		return checkWechatTxnDetailBean;
	}

	
	public CheckWechatTxnDetailBean getCheckWechatTxnDetailBean() {
		return checkWechatTxnDetailBean;
	}


	public void setCheckWechatTxnDetailBean(
			CheckWechatTxnDetailBean checkWechatTxnDetailBean) {
		this.checkWechatTxnDetailBean = checkWechatTxnDetailBean;
	}


	public CheckWechantTxnDetailService getCheckWechantTxnDetailService() {
		return checkWechantTxnDetailService;
	}


	public void setCheckWechantTxnDetailService(
			CheckWechantTxnDetailService checkWechantTxnDetailService) {
		this.checkWechantTxnDetailService = checkWechantTxnDetailService;
	}

	/**
	 * 查询微信交易记录
	 */
	public void queryWechantTxnDetailInfo(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb=checkWechantTxnDetailService.queryWechantTxnDetailInfo(checkWechatTxnDetailBean,user);
		} catch (Exception e) {
			log.info("查询微信交易记录异常"+e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 微信交易明细导出所有
	 */
	public void wechantTxnExport(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=checkWechantTxnDetailService.wechantTxnExport(checkWechatTxnDetailBean,((UserBean)userSession));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("微信交易明细导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("微信交易明细导出Excel成功");
			} catch (Exception e) {
				log.error("微信交易明细导出Excel异常：" + e);
				json.setMsg("微信交易明细导出Excel失败");
			}
		}
	}
	
	/**
	 * 查询微信交易商户明细汇总
	 */
	public void listCheckMerWechatdealData(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb=checkWechantTxnDetailService.listCheckMerWechatdealData(checkWechatTxnDetailBean,user);
		} catch (Exception e) {
			log.info("查询微信交易明细汇总异常"+e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 微信交易商户汇总 导出所有
	 */
	public void checkWechatTxnDetailExcelAll(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
//				HSSFWorkbook wb=
						checkWechantTxnDetailService.checkWechatTxnDetailExcelAll(checkWechatTxnDetailBean,
								((UserBean)userSession),super.getResponse());
//				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//				response.setHeader("Content-Disposition", "attachment; filename=" + new String("微信交易商户汇总".getBytes("GBK"), "ISO-8859-1") + ".xls");  
//		        OutputStream ouputStream= response.getOutputStream();
//		        wb.write(ouputStream);  
//		        ouputStream.flush();  
//		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("微信交易商户汇总Excel成功");
			} catch (Exception e) {
				log.error("微信交易商户汇总Excel异常：" + e);
				json.setMsg("查询微信交易商户汇总Excel失败");
			}
		}
	}
	/**
	 * 查询微信交易机构明细汇总
	 */
	public void listCheckUnitwechatunitdealData(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb=checkWechantTxnDetailService.listCheckUnitWechatdealData(checkWechatTxnDetailBean,user);
		} catch (Exception e) {
			log.info("查询微信交易机构明细汇总异常"+e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 微信交易机构汇总 导出所有
	 */
	public void checkWechatTxnUnitDetailExcelAll(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=checkWechantTxnDetailService.checkWechatTxnUnitDetailExcelAll(checkWechatTxnDetailBean,((UserBean)userSession));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("微信交易机构汇总导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
				OutputStream ouputStream= response.getOutputStream();
				wb.write(ouputStream);  
				ouputStream.flush();  
				ouputStream.close();
				json.setSuccess(true);
				json.setMsg("微信交易机构汇 导出Excel成功");
			} catch (Exception e) {
				log.error("微信交易机构汇 导出Excel异常：" + e);
				json.setMsg("微信交易机构汇 导出Excel失败");
			}
		}
	}
	/**
	 * 微信交易退款
	 */
//	public void wechantTxnRefund(){
//		Object userSession = super.getRequest().getSession().getAttribute("user");
//		UserBean user=(UserBean)userSession;
//		JsonBean json =new JsonBean();
//		boolean flag2 =false;
//		try {
//			//查看退款金额是否合规
//			boolean flag1 = checkWechantTxnDetailService.queryIfWechantTxnRefund(checkWechatTxnDetailBean);
//				if(flag1){
//				//实现退款
//				flag2 = checkWechantTxnDetailService.saveWechantTxnRefund(checkWechatTxnDetailBean,user);
//				if(flag2){
//					json.setSuccess(true);
//					json.setMsg("退款成功");
//				}else{
//					json.setSuccess(false);
//					json.setMsg("退款失败");
//				}
//			}else{
//				json.setSuccess(false);
//				json.setMsg("退款金额不足");
//			}
//		} catch (Exception e) {
//			log.info("微信交易退款异常"+e);
//			json.setSuccess(false);
//			json.setMsg("退款失败");
//		}
//		super.writeJson(json);
//	}
}
