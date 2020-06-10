package com.hrt.biz.bill.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.hrt.biz.bill.entity.pagebean.AgentUnitTaskBean;
import com.hrt.biz.bill.service.IAgentUnitTaskService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.UsePioOutExcel;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 代理商工单管理
 */
public class AgentUnitTaskAction extends BaseAction implements ModelDriven<AgentUnitTaskBean>{

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(AgentUnitTaskAction.class);
	
	private AgentUnitTaskBean agentUnitTask = new AgentUnitTaskBean();
	
	private IAgentUnitTaskService agentUnitTaskService;
	
	private File upload;
	
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	@Override
	public AgentUnitTaskBean getModel() {
		return agentUnitTask;
	}

	public IAgentUnitTaskService getAgentUnitTaskService() {
		return agentUnitTaskService;
	}

	public void setAgentUnitTaskService(IAgentUnitTaskService agentUnitTaskService) {
		this.agentUnitTaskService = agentUnitTaskService;
	}
	
	/**
	 * 查询代理商工单
	 */
	public void queryAgentUnitTask(){
		DataGridBean dataGridBean = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				dataGridBean = agentUnitTaskService.queryAgentUnitTask(agentUnitTask,((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("查询代理商工单异常：" + e);
		}
		super.writeJson(dataGridBean);
	}
	/**
	 * 查询代理商工单明细
	 */
	public void queryAgentUnitTaskDetail(){
		List<Object> list = null;
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				list = agentUnitTaskService.queryAgentUnitTaskDetail(agentUnitTask,((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("查询代理商工单明细异常：" + e);
		}
		super.writeJson(list);
	}
	/**
	 * 代理商工单提交
	 */
	public void addAgentUnitTask(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			if(userSession!=null){
				Integer i  = agentUnitTaskService.addAgentUnitTask(agentUnitTask,((UserBean)userSession));
				if(i==0){
					json.setSuccess(false);
					json.setMsg("存在已提交未审核或已退回的工单,不能提交新的工单");
				}else if(i==1){
					json.setSuccess(true);
					json.setMsg("工单提交成功");
				}else if(i==2) {
					json.setSuccess(false);
					json.setMsg("您已申请贷款，无法变更账户名称");
				}
			}
		} catch (Exception e) {
			log.error("代理商工单提交异常：" + e);
			json.setSuccess(false);
			json.setMsg("工单提交失败");
		}
		super.writeJson(json);
	}
	
	/**
	 * 代理商工单删除
	 */
	public void deleteAgentUnitTask(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			if(userSession!=null){
				Integer i  = agentUnitTaskService.deleteAgentUnitTask(agentUnitTask,((UserBean)userSession));
				if(i==0){
					json.setSuccess(false);
					json.setMsg("工单删除失败");
				}else if(i==1){
					json.setSuccess(true);
					json.setMsg("工单删除成功");
				}
			}
		} catch (Exception e) {
			log.error("代理商工单删除异常：" + e);
			json.setSuccess(false);
			json.setMsg("工单删除失败");
		}
		super.writeJson(json);
	}
	
	/**
	 * 代理商工单修改
	 */
	public void updateAgentUnitTask(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			if(userSession!=null){
				Integer i  = agentUnitTaskService.updateAgentUnitTask(agentUnitTask,((UserBean)userSession));
				if(i==0){
					json.setSuccess(false);
					json.setMsg("工单修改失败");
				}else if(i==1){
					json.setSuccess(true);
					json.setMsg("工单修改成功");
				}
			}
		} catch (Exception e) {
			log.error("代理商工单修改异常：" + e);
			json.setSuccess(false);
			json.setMsg("工单修改失败");
		}
		super.writeJson(json);
	}
	
	/**
	 * 代理商工单退回
	 */
	public void updateAgentUnitTaskK(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			if(userSession!=null){
				Integer i  = agentUnitTaskService.updateAgentUnitTaskK(agentUnitTask,((UserBean)userSession));
				if(i==0){
					json.setSuccess(false);
					json.setMsg("工单退回失败");
				}else if(i==1){
					json.setSuccess(true);
					json.setMsg("工单退回成功");
				}
			}
		} catch (Exception e) {
			log.error("代理商工单退回异常：" + e);
			json.setSuccess(false);
			json.setMsg("工单退回失败");
		}
		super.writeJson(json);
	}
	
	/**
	 * 代理商工单通过
	 */
	public void updateAgentUnitTaskY(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			if(userSession!=null){
				Integer i  = agentUnitTaskService.updateAgentUnitTaskY(agentUnitTask,((UserBean)userSession));
				if(i==0){
					json.setSuccess(false);
					json.setMsg("工单通过失败");
				}else if(i==1){
					json.setSuccess(true);
					json.setMsg("工单通过成功");
				}
			}
		} catch (Exception e) {
			log.error("代理商工单通过异常：" + e);
			json.setSuccess(false);
			json.setMsg("工单通过失败");
		}
		super.writeJson(json);
	}
	
	/**
	 * 查询贷款机构
	 */
	public void queryLoanUnit(){
		DataGridBean dataGridBean = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				dataGridBean = agentUnitTaskService.queryLoanUnit(agentUnitTask,((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("查询贷款机构异常：" + e);
		}
		super.writeJson(dataGridBean);
	}
	
	/**
	 * 删除代理商贷款
	 */
	public void deleteLoanUnno(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			if(userSession!=null){
				Integer i  = agentUnitTaskService.deleteLoanUnno(agentUnitTask,((UserBean)userSession));
				if(i==1){
					json.setSuccess(true);
					json.setMsg("删除代理商贷款成功");
				}else if(i==1){
					json.setSuccess(false);
					json.setMsg("删除代理商贷款失败");
				}
			}
		} catch (Exception e) {
			log.error("删除代理商贷款异常：" + e);
			json.setSuccess(false);
			json.setMsg("删除代理商贷款失败");
		}
		super.writeJson(json);
	}
	
	/**
	 * 导入贷款机构
	 */
	public void addLoanUnit(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null)json.setSessionExpire(true);
		//把文件保存到服务器目录下
		String fileName = ServletActionContext.getRequest().getParameter("file10181Name");
		String basePath = ServletActionContext.getRequest().getRealPath("/upload");
		File dir = new File(basePath);
		dir.mkdir();
		// 使用UUID做为文件名，以解决重名的问题
		String path = basePath +"/"+fileName;
		File destFile = new File(path);
		upload.renameTo(destFile);
		String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
		String xlsfile = folderPath +"/"+fileName;
		try {
			if(xlsfile.length() > 0 && xlsfile != null){
				List<Map<String,String>> list = null;
				list = agentUnitTaskService.addLoanUnit(xlsfile,fileName,user);
				if(list!=null&&list.size() > 0){
					String excelName= "导入失败记录";
					String[] title = {"机构号","备注"};
					List excellist = new ArrayList();
					excellist.add(title);
					for(int rowId = 0;rowId<list.size();rowId++){
						Map<String, String> order = list.get(rowId);
						String[] rowContents = {order.get("unno"),order.get("remark")};
						excellist.add(rowContents);
					}
					String[] cellFormatType = {"t","t"};
					UsePioOutExcel.writeExcel(excellist, 2, super.getResponse(), excelName, excelName+".xls", cellFormatType);					
				}else{
					json.setSuccess(true);
					json.setMsg("记录更新成功!");
					super.writeJson(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(true);
			json.setMsg("文件更新失败!");
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
}
