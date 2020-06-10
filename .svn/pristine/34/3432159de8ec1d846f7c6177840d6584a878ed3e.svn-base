package com.hrt.biz.check.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.metamodel.domain.Superclass;

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.check.entity.pagebean.CheckCashBean;
import com.hrt.biz.check.entity.pagebean.CheckMisTakeBean;
import com.hrt.biz.check.entity.pagebean.CheckReOrderBean;
import com.hrt.biz.check.service.CheckReOrderService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.UsePioOutExcel;
import com.opensymphony.xwork2.ModelDriven;

import sun.print.resources.serviceui;

/**
 *	@author tenglong
 *	2016-07-15
 *	差错平台-退单
 */
public class CheckReOrderAction extends BaseAction implements ModelDriven<CheckReOrderBean> {
	
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(CheckReOrderAction.class);
	
	private CheckReOrderBean checkReOrderBean =new CheckReOrderBean();
	private CheckReOrderService checkReOrderService;
	private File upload;
	
	@Override
	public CheckReOrderBean getModel() {
		return checkReOrderBean;
	}
	

	public CheckReOrderBean getCheckReOrderBean() {
		return checkReOrderBean;
	}


	public void setCheckReOrderBean(CheckReOrderBean checkReOrderBean) {
		this.checkReOrderBean = checkReOrderBean;
	}


	public CheckReOrderService getCheckReOrderService() {
		return checkReOrderService;
	}


	public void setCheckReOrderService(CheckReOrderService checkReOrderService) {
		this.checkReOrderService = checkReOrderService;
	}


	public File getUpload() {
		return upload;
	}


	public void setUpload(File upload) {
		this.upload = upload;
	}


	/**
	 * 查询退单记录
	 */
	public void queryReOrderInfo(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb=checkReOrderService.queryReOrderInfo(checkReOrderBean,user);
		} catch (Exception e) {
			log.info("查询退单记录异常"+e);
		}
		super.writeJson(dgb);
	}
	
	/*
	 * 查询已回复退单
	 */
	public void queryReplyReOrderInfo(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb=checkReOrderService.queryReplyReOrderInfo(checkReOrderBean,user);
		} catch (Exception e) {
			log.info("查询退单记录异常"+e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 接收退单
	 */
	public void saveReOrder(){
		HttpServletRequest request =super.getRequest();
		JsonBean json = new JsonBean();
		boolean flag = false;
		try {
			request.setCharacterEncoding("UTF-8");
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
			String line = "";
			StringBuffer buf = new StringBuffer();
			while((line=br.readLine())!=null){
				buf.append(line);
			}
			log.info("接收退单:"+buf);
			//flag=proxyErrService.addProxyRefound(JSONObject.fromObject(buf.toString()));
			flag = checkReOrderService.saveReOrder(JSONObject.parseObject(buf.toString()));
			json.setSuccess(flag);
		} catch (Exception e) {
			log.info("接收退单"+e);
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	/**
	 * 回复
	 */
	public void updateReOrder(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		JsonBean json = new JsonBean();
		try {
			Map<String, String> map = checkReOrderService.updateReOrder(checkReOrderBean,user);
			json.setSuccess(true);
			json.setMsg(map.get("msg"));
		} catch (Exception e) {
			log.info("查询 回复 修改异常"+e);
			json.setSuccess(false);
			json.setMsg("回复失败");
		}
		super.writeJson(json);
	}
	
	/*
	 * 退回
	 */
	public void updateRebackReOrder() {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean userBean = (UserBean)userSession;
		JsonBean jsonBean = new JsonBean();
		try {
			checkReOrderService.updateRebackReOrder(checkReOrderBean,userBean);
			jsonBean.setSuccess(true);
			jsonBean.setMsg("退回成功");
		} catch (Exception e) {
			log.info("退回失败 ："+e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("退回失败");
		}
		super.writeJson(jsonBean);
	}
	
	/*
	 * 通过
	 */
	public void updatePassReOrder(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean userBean = (UserBean)userSession;
		JsonBean jsonBean = new JsonBean();
		try {
			checkReOrderService.updatePassReOrder(checkReOrderBean,userBean);
			jsonBean.setSuccess(true);
			jsonBean.setMsg("已通过");
		} catch (Exception e) {
			log.info("通过失败 ："+e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("操作失败");
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 查看明细
	 */
	public void serachReOrder(){
		
		CheckReOrderBean mb = checkReOrderService.queryReOrderById(checkReOrderBean.getRoid());
		super.writeJson(mb);
	}
	
	/**
	 * 退单导出所有
	 */
	public void queryReOrderExport(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=checkReOrderService.queryReOrderExport(checkReOrderBean,((UserBean)userSession));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("退单导出导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("退单导出所有Excel成功");
			} catch (Exception e) {
				log.error("退单导出所有Excel异常：" + e);
				json.setMsg("退单导出所有Excel失败");
			}
		}
	}
	
	/**
	 * 删除服务器上的上传文件
	 * @param basePath
	 * @param path
	 */
	public void removeUploadFile(String basePath,String path){
		File file = new File(basePath);
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(basePath + tempList[i]);
			} else {
				temp = new File(basePath + File.separator + tempList[i]);
			}
			if (temp.exists() && temp.isFile()) {
				boolean flag = temp.delete(); // 删除上传到服务器的文件
				int j = i + 1;
				log.error("成功删除文件:第" + j + "个文件删除是否成功？" + flag);
			} else {
				log.error("文件不存在");
			}
		}
	}

}
