package com.hrt.biz.bill.action;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.entity.pagebean.AgentSalesBean;
import com.hrt.biz.bill.service.IAgentSalesService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 业务员管理
 */
public class AgentSalesAction extends BaseAction implements ModelDriven<AgentSalesBean>{

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(AgentSalesAction.class);
	
	private AgentSalesBean agentSales = new AgentSalesBean();
	
	private IAgentSalesService agentSalesService;
	
	private String busids;
	
	@Override
	public AgentSalesBean getModel() {
		return agentSales;
	}

	public IAgentSalesService getAgentSalesService() {
		return agentSalesService;
	}

	public void setAgentSalesService(IAgentSalesService agentSalesService) {
		this.agentSalesService = agentSalesService;
	}
	
	public String getBusids() {
		return busids;
	}

	public void setBusids(String busids) {
		this.busids = busids;
	}
	
	

	/**
	 * 查询业务员信息
	 */
	public void listAgentSales(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = agentSalesService.queryAgentSales(agentSales, ((UserBean)userSession).getUnNo());
		} catch (Exception e) {
			log.error("分页查询业务员信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	
	/**
	 * 业务员添加
	 */
	public void addAgentSales(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				int falg=agentSalesService.queryAgentSalesCounts(agentSales);
				if(falg>0){
					json.setSuccess(false);
					json.setMsg("此用户已有对应的业务员");
				}else{
					agentSalesService.saveAgentSales(agentSales, ((UserBean)userSession));
					json.setSuccess(true);
					json.setMsg("业务员添加成功");
				}
			} catch (Exception e) {
				log.error("业务员添加异常：" + e);
				json.setMsg("业务员添加失败");
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 查询业务员信息显示到select中
	 */
	public void searchAgentSales(){
		DataGridBean dgd = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			String nameCode = super.getRequest().getParameter("q");
			dgd = agentSalesService.searchAgentSales(((UserBean)userSession).getUnNo(),nameCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	super.writeJson(dgd);
	}
	
	/**
	 * 查询业务员信息显示到select中
	 */
	public void searchAgentSales3(){
		DataGridBean dgd = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgd = agentSalesService.searchAgentSales3((UserBean)userSession);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	super.writeJson(dgd);
	}
	
	/**
	 * 查询业务员信息显示到select中(北分交行单独所用)
	 */
	public void searchAgentSales2(){
		DataGridBean dgd = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			String nameCode = super.getRequest().getParameter("q");
			dgd = agentSalesService.searchAgentSales(agentSales.getUnno(),nameCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	super.writeJson(dgd);
	}
	
	
	/**
	 * 查询归属信息显示到select中（北分交行单独所用）
	 */
	public void searchUnno(){
		DataGridBean dgd = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			String nameCode = super.getRequest().getParameter("q");
			dgd = agentSalesService.searchUnno(((UserBean)userSession).getUnNo(),nameCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	super.writeJson(dgd);
	}
	
	/**
	 * 修改业务员信息
	 */
	public void editAgentSales(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				int falg=agentSalesService.queryUpdateAgentSalesCounts(agentSales);
				if(falg>0){
					json.setSuccess(false);
					json.setMsg("此用户已有对应的业务员");
				}else{
					agentSalesService.updateAgentSales(agentSales, ((UserBean)userSession));
					json.setSuccess(true);
					json.setMsg("修改业务员信息成功");
				}
			} catch (Exception e) {
				log.error("修改业务员信息异常" + e);
				json.setMsg("修改业务员信息失败");
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 删除业务员
	 */
	public void deleteAgentSales() {
		JsonBean json = new JsonBean();
		try {
			agentSalesService.deleteAgentSales(Integer.parseInt(busids));
			json.setSuccess(true);
			json.setMsg("删除业务员信息成功");
		} catch (Exception e) {
			log.error("删除业务员信息异常：" + e);
			json.setMsg("删除业务员信息失败");
		}
		super.writeJson(json);
	}
	
	/**
	 * 导出业务员资料excel
	 */
	public void export(){
		String busids=getRequest().getParameter("busids");
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=agentSalesService.export(busids);
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("业务员资料导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出业务员资料Excel成功");
			} catch (Exception e) {
				log.error("导出业务员资料Excel异常：" + e);
				json.setMsg("导出业务员资料Excel失败");
			}
		}
	}
	
	/**
	 * 查询业务员信息--本公司下维护的组销售
	 */
	public void listAgentSalesGroup(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = agentSalesService.queryAgentSalesGroup(agentSales, ((UserBean)userSession).getUnNo());
		} catch (Exception e) {
			log.error("分页查询业务员信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	
	/**
	 * 组查询
	 */
	public void queryAgentsalesGroup(){
		DataGridBean dgd = new DataGridBean();
		try {
			dgd = agentSalesService.queryAgentsalesGroup();
		} catch (Exception e) {
			e.printStackTrace();
		}
			super.writeJson(dgd);
	}
	
	/**
	 * 销售员--根据id查询
	 */
	public void queryAgentsalesGroupForId(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		agentSales.setBusid(Integer.parseInt(getRequest().getParameter("busid")));
		Map map=null;
		try {
			map = agentSalesService.queryAgentsalesGroupForId(agentSales,(UserBean)userSession);
		} catch (Exception e) {
			log.error("查询销售信息异常：" + e);
		}
		super.writeJson(map);
	}
	
	/**
	 * 销售组别信息--修改
	 */
	public void updateAgentSalesGroup(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {			
			try {
				if(agentSales.getSalesgroup() == null || agentSales.getSaleName()==null 
						|| agentSales.getIsleader() == null || agentSales.getBusid() == null) {
					json.setSuccess(false);
					json.setMsg("请确保所有信息已填写");
					super.writeJson(json);
				}
					String string = agentSalesService.updateAgentSalesGroup(agentSales, (UserBean) userSession);
					if(!"修改成功".equals(string)) {
						json.setSuccess(false);
						json.setMsg("修改销售组别信息失败");
						super.writeJson(json);
					}
					json.setSuccess(true);
					json.setMsg("修改销售组别信息成功");
			} catch (Exception e) {
				log.error("修改销售组别信息异常：" + e);
				json.setMsg("修改销售组别信息失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 业务员添加
	 */
	public void addAgentSalesGroup(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				if(agentSales.getSalesgroup()==null || agentSales.getUserID() == null
						|| agentSales.getUnno() == null || agentSales.getIsleader() == null) {
					json.setSuccess(false);
					json.setMsg("请录入完整数据");
					super.writeJson(json);
					return;
				}
				
				int falg=agentSalesService.queryAgentSalesCounts(agentSales);
				if(falg>0){
					json.setSuccess(false);
					json.setMsg("此用户已有对应的业务员");
				}else{
					agentSalesService.saveAgentSales(agentSales, ((UserBean)userSession));
					json.setSuccess(true);
					json.setMsg("公司组别业务员添加成功");
				}
			} catch (Exception e) {
				log.error("公司组别添加异常：" + e);
				json.setMsg("公司组别添加失败");
			}
		}
		
		super.writeJson(json);
	}
}
