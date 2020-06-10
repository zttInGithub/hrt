package com.hrt.biz.bill.action;

import com.hrt.util.ExcelServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.pagebean.ChangeTerRateBean;
import com.hrt.biz.bill.service.IChangeTerRateService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.util.Map;

/**
 * 修改设备费率
 */
public class ChangeTerRateAction extends BaseAction implements ModelDriven<ChangeTerRateBean>{

	private static final long serialVersionUID = 1L;
	
	private Log log = LogFactory.getLog(ChangeTerRateAction.class);
	
	private IChangeTerRateService changeTerRateService;
	
	private ChangeTerRateBean changeTerRateBean=new ChangeTerRateBean();

	private File upload ;

	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	@Override
	public ChangeTerRateBean getModel() {
		return changeTerRateBean;
	}
	
	public IChangeTerRateService getChangeTerRateService() {
		return changeTerRateService;
	}

	public void setChangeTerRateService(IChangeTerRateService changeTerRateService) {
		this.changeTerRateService = changeTerRateService;
	}

	/**
	 * 查询费率修改
	 */
	public void queryChangeTerRate() {
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else {
			try {
				dgb = changeTerRateService.queryChangeTerRate(changeTerRateBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("分页查询费率修改异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 新增费率修改
	 */
	public void addChangeTerRate() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else {
			try {
				Integer i = changeTerRateService.addChangeTerRate(changeTerRateBean,(UserBean)userSession);
				if(i==1) {
					json.setSuccess(true);
					json.setMsg("费率修改添加成功");
				}else if(i==0) {
					json.setSuccess(true);
					json.setMsg("费率修改添加失败");
				}
			} catch (Exception e) {
				log.error("新增费率修改异常：" + e);
			}
		}
		super.writeJson(json);
	}

	/**
	 * 费率修改审核通过
	 */
	public void updateChangeTerRateGo() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else {
			try {
				Integer i = changeTerRateService.updateChangeTerRateGo(changeTerRateBean,(UserBean)userSession);
				if(i==1) {
					json.setSuccess(true);
					json.setMsg("费率修改审核通过成功");
				}else if(i==0) {
					json.setSuccess(true);
					json.setMsg("费率修改审核通过失败");
				}
			} catch (Exception e) {
				log.error("费率修改审核通过异常：" + e);
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 费率修改审核通过勾选
	 */
	public void updateChangeAllTerRateGo() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else {
			try {
				Integer i = changeTerRateService.updateChangeAllTerRateGo(changeTerRateBean,(UserBean)userSession);
				if(i==1) {
					json.setSuccess(true);
					json.setMsg("费率修改审核通过成功");
				}else if(i==0) {
					json.setSuccess(true);
					json.setMsg("费率修改审核通过失败");
				}
			} catch (Exception e) {
				log.error("费率修改审核通过异常：" + e);
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 费率修改审核退回
	 */
	public void updateChangeTerRateBack() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else {
			try {
				Integer i = changeTerRateService.updateChangeTerRateBack(changeTerRateBean,(UserBean)userSession);
				if(i==1) {
					json.setSuccess(true);
					json.setMsg("费率修改审核退回成功");
				}else if(i==0) {
					json.setSuccess(true);
					json.setMsg("费率修改审核退回失败");
				}
			} catch (Exception e) {
				log.error("费率修改审核退回异常：" + e);
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 费率修改审核退回勾选
	 */
	public void updateChangeAllTerRateBack() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else {
			try {
				Integer i = changeTerRateService.updateChangeAllTerRateBack(changeTerRateBean,(UserBean)userSession);
				if(i==1) {
					json.setSuccess(true);
					json.setMsg("费率修改审核退回成功");
				}else if(i==0) {
					json.setSuccess(true);
					json.setMsg("费率修改审核退回失败");
				}
			} catch (Exception e) {
				log.error("费率修改审核退回异常：" + e);
			}
		}
		super.writeJson(json);
	}

	/**
	 * 批量费率修改导入
	 */
	public void importBatchChangeTerRate(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null){
			json.setSessionExpire(true);
		}
		String UUID = System.currentTimeMillis()+"";
		//把文件保存到服务器目录下
		String fileName = UUID+ServletActionContext.getRequest().getParameter("fileContact");
		String basePath = ServletActionContext.getRequest().getRealPath("/upload");
		File dir = new File(basePath);
		if(!dir.exists())
			dir.mkdir();
		String path = basePath +"/"+fileName;
		File destFile = new File(path);
		upload.renameTo(destFile);
		String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
		String xlsfile = folderPath +"/"+fileName;
		if(log.isInfoEnabled()){
			log.info("批量费率导入Excel文件路径:"+xlsfile);
		}
		try {
			if(xlsfile.length() > 0 && xlsfile != null){
				Map resultMap = changeTerRateService.addBatchChangeTerRate(xlsfile,fileName,user);
				int sumCount = Integer.parseInt(resultMap.get("sumCount").toString());
				int errCount = Integer.parseInt(resultMap.get("errCount").toString());
				if(sumCount!=errCount){
					json.setSuccess(true);
					json.setMsg("批量费率导入成功!-总记录数:"+resultMap.get("sumCount")+",失败数:"+resultMap.get("errCount"));
				}else{
					json.setSuccess(false);
					json.setMsg("批量费率导入数据异常!");
				}
			}else{
				json.setSuccess(false);
				json.setMsg("导入文件异常!");
			}
		} catch (Exception e) {
			log.info("批量修改费率导入异常:"+e);
			json.setSuccess(false);
			json.setMsg("批量费率导入失败!");
		}
		ExcelServiceImpl.deleteUploadFile(folderPath,path);
		super.writeJson(json);
	}
}
