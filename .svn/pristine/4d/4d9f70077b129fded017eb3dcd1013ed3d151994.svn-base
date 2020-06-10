package com.hrt.biz.bill.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.hrt.biz.bill.entity.pagebean.HotCardBean;
import com.hrt.biz.bill.service.IHotCardService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.opensymphony.xwork2.ModelDriven;

public class HotCardAction extends BaseAction implements ModelDriven<HotCardBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(HotCardAction.class);
	
	private HotCardBean bean = new HotCardBean();
	
	private IHotCardService hotCardService;
	
	public IHotCardService getHotCardService() {
		return hotCardService;
	}
	public void setHotCardService(IHotCardService hotCardService) {
		this.hotCardService = hotCardService;
	}

	@Override
	public HotCardBean getModel() {
		return bean;
	}

	/*
	 * 查询黑商户信息list
	 */
	public void queryHotCardMerchant(){
		DataGridBean dgd = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession==null){
		}else{
			try {
				dgd =hotCardService.queryHotCardMerchant(bean);
			} catch (Exception e) {
				log.error("查询黑卡信息异常：" + e);
			}
			super.writeJson(dgd);
		}
	}
	
	/*
	 * 添加黑商户信息
	 */
	public void addHotCardMerchant(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			hotCardService.saveHotCardMerchant(bean,(UserBean) userSession );
			json.setMsg("添加成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("添加黑商户信息异常！");
			json.setSuccess(false);
			log.error(e);
		}
		super.writeJson(json);
	}
	
	/*
	 * 修改黑商户信息
	 */
	public void updateHotCardMerchant(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			hotCardService.updateHotCardMerchantInfo(bean,(UserBean) userSession);
			json.setMsg("修改黑商户信息成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("修改黑商户信息异常！");
			json.setSuccess(false);
			log.error(e);
		}
		super.writeJson(json);
	}
	
	/*
	 * 批量修改黑商户信息
	 */
	public void updateBatchHotCardMerchant(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			hotCardService.updateBatchHotCardMerchant(bean,(UserBean) userSession);
			json.setMsg("批量修改黑商户信息成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("批量修改黑商户信息异常！");
			json.setSuccess(false);
			log.error(e);
		}
		super.writeJson(json);
	}
	
	/*
	 * 删除黑商户信息
	 */
	public void deleteHotCardMerchant(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			hotCardService.deleteHotCardMerchantInfo(bean,(UserBean) userSession);
			json.setMsg("删除黑商户信息成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("删除黑商户信息异常！");
			json.setSuccess(false);
			log.error(e);
		}
		super.writeJson(json);
	}
	
	/**
	 * 导入黑名单商户
	 */
	public void uploadHotCardMerchant() {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//把文件保存到服务器的目录下
		String filename = ServletActionContext.getRequest().getParameter("hotMerchant_file");	//获取文件名
		String dasePath = ServletActionContext.getServletContext().getRealPath("/upload");	
		File dir = new File(dasePath);
		dir.mkdirs();
		// 使用UUID做为文件名，以解决重名的问题
		String path = dasePath + "/" + filename;
		File destFile = new File(path);
		upload.renameTo(destFile);
		String folderPath = ServletActionContext.getServletContext().getRealPath("/upload");// 存放上传文件的路径
		String xlsfile = folderPath + "/" + filename;
		JsonBean json = new JsonBean();
		try {
			if (xlsfile.length() > 0 && xlsfile != null) {
				List<Map<String,String>> list= hotCardService.saveimportHotCardMerchant(xlsfile,((UserBean)userSession)); // 上传文件
				if(list.size() > 0){
					String excelName= "导入黑名单商户失败记录";
					String[] title = {"商户名称","SN","法人身份证","营业执照号","入账卡号","备注"};
					List excellist = new ArrayList();
					excellist.add(title);
					for(int rowId = 0;rowId<list.size();rowId++){
						Map<String, String> order = list.get(rowId);
						String[] rowContents = {														
							order.get("tname"),
							order.get("sn"),
							order.get("identificationNumber"),												
							order.get("license"),
							order.get("bankAccNo"),
							order.get("remarks")
						};
						excellist.add(rowContents);
					}
					
					String[] cellFormatType = {"t","t","t","t","t","t"};
					JxlOutExcelUtil.writeExcel(excellist, 6, getResponse(), excelName, excelName+".xls", cellFormatType);					
				}else{
					json.setSuccess(true);
					json.setMsg("黑名单商户导入成功!");
					super.writeJson(json);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("黑名单商户导入失败!");
			super.writeJson(json);
		}
	
		File file = new File(folderPath);
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(folderPath + tempList[i]);
			} else {
				temp = new File(folderPath + File.separator + tempList[i]);
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
	
	/**
	 *订单Excel导出
	 */
	public void exportHotCardMerchant() throws IOException{
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				List<Map<String, Object>> list = hotCardService.exportHotCardMerchant(bean);
				List<String> excelHeader = new ArrayList<String>();
				Map<String, Object> headMap = new HashMap<String, Object>();
				excelHeader.add("TNAME");
				excelHeader.add("SN");
				excelHeader.add("IDENTIFICATIONNUMBER");
				excelHeader.add("LICENSE");
				excelHeader.add("BANKACCNO");
//				excelHeader.add("CDATE");
//				excelHeader.add("CBY");
				excelHeader.add("REMARKS");

				headMap.put("TNAME", "商户名称");
				headMap.put("SN", "sn");
				headMap.put("IDENTIFICATIONNUMBER", "身份证号");
				headMap.put("LICENSE", "营业执照号");
				headMap.put("BANKACCNO", "卡号");
//				headMap.put("CDATE", "操作日期");
//				headMap.put("CBY", "操作人员");
				headMap.put("REMARKS", "备注");
				
				JxlOutExcelUtil.export("黑名单商户信息", list, excelHeader, headMap,"黑名单商户信息.xls",super.getResponse());

				json.setSuccess(true);
				json.setMsg("导出黑名单商户成功");
			} catch (Exception e) {
				log.error("导出黑名单商户异常：" + e);
				json.setSuccess(false);
				json.setMsg("导出黑名单商户失败");
			}
		}
	}
	
	private File upload=null;


	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
}
