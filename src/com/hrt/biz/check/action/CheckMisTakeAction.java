package com.hrt.biz.check.action;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.pagebean.MerchantAuthenticityBean;
import com.hrt.biz.check.entity.pagebean.CheckCashBean;
import com.hrt.biz.check.entity.pagebean.CheckMisTakeBean;
import com.hrt.biz.check.entity.pagebean.CheckReFundBean;
import com.hrt.biz.check.entity.pagebean.CheckWechatTxnDetailBean;
import com.hrt.biz.check.service.CheckMisTakeService;
import com.hrt.biz.check.service.CheckReFundService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.UsePioOutExcel;
import com.opensymphony.xwork2.ModelDriven;

/**
 *	@author tenglong
 *	2016-07-25
 *	差错平台-调单查询，回复
 */
public class CheckMisTakeAction extends BaseAction implements ModelDriven<CheckMisTakeBean> {

	
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(CheckMisTakeAction.class);
	
	private CheckMisTakeBean checkMisTakeBean =new CheckMisTakeBean();
	private CheckMisTakeService checkMisTakeService;

	public CheckMisTakeBean getCheckMisTakeBean() {
		return checkMisTakeBean;
	}

	public void setCheckMisTakeBean(CheckMisTakeBean checkMisTakeBean) {
		this.checkMisTakeBean = checkMisTakeBean;
	}

	public CheckMisTakeService getCheckMisTakeService() {
		return checkMisTakeService;
	}

	public void setCheckMisTakeService(CheckMisTakeService checkMisTakeService) {
		this.checkMisTakeService = checkMisTakeService;
	}

	@Override
	public CheckMisTakeBean getModel() {
		return checkMisTakeBean;
	}

	/**
	 * 查询回复记录
	 */
	public void querMisTakeInfo(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb=checkMisTakeService.querMisTakeInfo(checkMisTakeBean,user);
		} catch (Exception e) {
			log.info("查询回复记录异常"+e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 差错查询导出所有
	 */
	public void querMisTakeInfoExport(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		String type = super.getRequest().getParameter("orderType");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=checkMisTakeService.querMisTakeInfoExport(checkMisTakeBean,((UserBean)userSession));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				if("3".equals(type)){
					response.setHeader("Content-Disposition", "attachment; filename=" + new String("查询回复导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
				}else if("2".equals(type)){
					response.setHeader("Content-Disposition", "attachment; filename=" + new String("差错调单回复导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
				}else if("4".equals(type)) {
					response.setHeader("Content-Disposition", "attachment; filename=" + new String("欺诈交易回复导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
				}
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("差错查询导出所有Excel成功");
			} catch (Exception e) {
				log.error("差错查询导出所有Excel异常：" + e);
				json.setMsg("差错查询导出所有Excel失败");
			}
		}
	}
	
	/**
	 * 查询回复报单接收
	 */
	public void saveDispatchOrder(){
		HttpServletRequest request =super.getRequest();
		JsonBean json = new JsonBean();
		try {
			request.setCharacterEncoding("UTF-8");
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
			String line = "";
			StringBuffer buf = new StringBuffer();
			while((line=br.readLine())!=null){
				buf.append(line);
			}
			log.info("查询回复报单接收:"+buf);
			//flag=proxyErrService.addProxyRefound(JSONObject.fromObject(buf.toString()));
			boolean flag = checkMisTakeService.saveDispatchOrder(JSONObject.parseObject(buf.toString()));
			json.setSuccess(flag);
		
		} catch (Exception e) {
			log.info("查询回复报单接收异常"+e);
			json.setSuccess(false);
		}
		super.writeJson(json);
	}

	/**
	 * 回复
	 */
	public void updateDispatchOrder(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		JsonBean json = new JsonBean();
		try {
			checkMisTakeService.updateDispatchOrder(checkMisTakeBean,user);
			json.setSuccess(true);
			json.setMsg("回复成功");
		} catch (Exception e) {
			log.info("查询 回复 修改异常"+e);
			json.setSuccess(false);
			json.setMsg("回复失败");
		}
		super.writeJson(json);
	}
	
	/**
	 * 查看明细
	 */
	public void serachMisTake(){
		
		CheckMisTakeBean mb = checkMisTakeService.queryMisTakeById(checkMisTakeBean.getDpid());
		super.writeJson(mb);
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
