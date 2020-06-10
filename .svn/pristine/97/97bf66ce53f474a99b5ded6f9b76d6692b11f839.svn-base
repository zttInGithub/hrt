package com.hrt.biz.bill.action;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.entity.pagebean.MerchantTaskDataBean;
import com.hrt.biz.bill.service.IMerchantTaskDataService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 商户工单审批
 */
public class MerchantTaskDataAction extends BaseAction implements ModelDriven<MerchantTaskDataBean>{
	
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(MerchantTaskDataAction.class);
	
	private MerchantTaskDataBean bean=new MerchantTaskDataBean();
	
	private String bmtkids;

	public String getBmtkids() {
		return bmtkids;
	}

	public void setBmtkids(String bmtkids) {
		this.bmtkids = bmtkids;
	}


	private IMerchantTaskDataService merchantTaskDataService;

	public IMerchantTaskDataService getMerchantTaskDataService() {
		return merchantTaskDataService;
	}

	public void setMerchantTaskDataService(
			IMerchantTaskDataService merchantTaskDataService) {
		this.merchantTaskDataService = merchantTaskDataService;
	}

	@Override
	public MerchantTaskDataBean getModel() {
		return bean;
	}
	
	/**
	 * 工单审批初始化
	 */
	public void initApp(){
		DataGridBean dgb = new DataGridBean();
		bean.setApproveStatus("Z");
		try {
			dgb = merchantTaskDataService.queryPage(bean);
		} catch (Exception e) {
			log.error("分页查询工单申请信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 工单审批初始化
	 */
	public void initApp1(){
		DataGridBean dgb = new DataGridBean();
		bean.setApproveStatus("Z");
		try {
			if("".equals(bean.getMid())||bean.getMid()==null){
				bean.setMid("864");
			}else if(!bean.getMid().startsWith("864")){
				return;
			}
			dgb = merchantTaskDataService.queryPage(bean);
		} catch (Exception e) {
			log.error("分页查询工单申请信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 风控工单审批初始化
	 */
	public void initRiskApp(){
		DataGridBean dgb = new DataGridBean();
		bean.setApproveStatus("Z");
		try {
			dgb = merchantTaskDataService.queryRiskPage(bean);
		} catch (Exception e) {
			log.error("分页查询工单申请信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 风控工单审批初始化
	 */
	public void initRiskAppbaodan(){
		DataGridBean dgb = new DataGridBean();
		bean.setApproveStatus("Z");
		try {
			bean.setMtype(0);
			dgb = merchantTaskDataService.queryRiskPage(bean);
		} catch (Exception e) {
			log.error("分页查询工单申请信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 传统商户转T+0工单审批初始化
	 */
	public void initChangeT0App(){
		DataGridBean dgb = new DataGridBean();
		bean.setApproveStatus("Z");
		try {
			dgb = merchantTaskDataService.queryChangeT0Page(bean);
		} catch (Exception e) {
			log.error("分页查询工单申请信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 传统商户转T+0工单(所有)
	 */
	public void initChangeT0AllApp(){
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = merchantTaskDataService.queryChangeT0Page(bean);
		} catch (Exception e) {
			log.error("分页查询工单申请信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 批量工单审批通过
	 */
	public void auditThrough(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				boolean f=merchantTaskDataService.updateAuditThrough(bean, (UserBean)userSession,bmtkids);
				if(f){
					json.setSuccess(true);
					json.setMsg("审批通过");
				}else{
					json.setSuccess(true);
					json.setMsg("批量审批之中包含有已审批的工单！");
				}
				
			} catch (Exception e) {
				log.error("工单审批通过异常：" + e);
				json.setMsg("工单审批失败");
			}
		}
		
		super.writeJson(json);
	}
	
	
	/**
	 * 批量风控工单审批通过
	 */
	public void auditRiskThrough(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				boolean b = true;
				List<Map<String, Object>>list = merchantTaskDataService.queryMerchantLimit4((UserBean)userSession,bmtkids);
				Integer bmtkid = null;
				for (int i = 0; i < list.size(); i++) {
					bmtkid = Integer.parseInt(list.get(i).get("BMTKID").toString());
					bean.setBmtkid(bmtkid);
					bean.setMid(list.get(i).get("MID").toString());
					bean.setMtype(list.get(i).get("MTYPE")==null?null:Integer.parseInt(list.get(i).get("MTYPE").toString()));
					bean.setDailyLimit(list.get(i).get("DAILYLIMIT")==null?null:Double.parseDouble(list.get(i).get("DAILYLIMIT").toString()));
					bean.setSingleLimit(list.get(i).get("SINGLELIMIT")==null?null:Double.parseDouble(list.get(i).get("SINGLELIMIT").toString()));
					//借记卡
					bean.setDailyLimit1(list.get(i).get("DAILYLIMIT1")==null?null:Double.parseDouble(list.get(i).get("DAILYLIMIT1").toString()));
					bean.setSingleLimit1(list.get(i).get("SINGLELIMIT1")==null?null:Double.parseDouble(list.get(i).get("SINGLELIMIT1").toString()));
					b = merchantTaskDataService.updateMerchantLimit(bean, (UserBean)userSession);
					if (b==false) {
						json.setSuccess(false);
						json.setMsg("工单审批通过失败！");
						super.writeJson(json);
						return;
					}else {
						b = merchantTaskDataService.updateAuditRiskThrough(bean, (UserBean)userSession,bmtkid);
					}
				}
				if(b){
					json.setSuccess(true);
					json.setMsg("审批通过");
				}else{
					json.setSuccess(true);
					json.setMsg("批量审批之中包含有已审批的工单！");
				}
				
			} catch (Exception e) {
				log.error("工单审批通过异常：" + e);
				json.setMsg("工单审批失败");
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 批量工单审批拒绝
	 */
	public void auditReject(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				boolean f=merchantTaskDataService.updateAuditReject(bean, (UserBean)userSession,bmtkids);
				if(f){
					json.setSuccess(true);
					json.setMsg("工单审批拒绝成功");
				}else{
					json.setSuccess(true);
					json.setMsg("批量审批之中包含有已审批的工单！");
				}
			} catch (Exception e) {
				log.error("工单审批拒绝异常：" + e);
				json.setMsg("工单审批拒绝失败");
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 单笔审批
	 */
	public void auditSingle(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
			   if(bean.getProcessContext1().equals("0")){
			   }else{
				   if(bean.getProcessContext1().equals("其他")){
				   }else{
				   bean.setProcessContext(bean.getProcessContext1());
				   }
			   }
			  if(bean.getProcessContext2().equals("0")){
			   }else{
				   if(bean.getProcessContext2().equals("其他")){
				   }else{
				   bean.setProcessContext(bean.getProcessContext2());
				   }
			   }
				
				boolean f=merchantTaskDataService.updateAuditSingle(bean, (UserBean)userSession);
				if(f){
					json.setSuccess(true);
					json.setMsg("工单审批成功");
				}else{
					json.setSuccess(true);
					json.setMsg("该工单可能已被审批，或者你没有选择审批类型（不受理，已受理）！");
				}
				
			} catch (Exception e) {
				log.error("工单审批异常：" + e);
				json.setMsg("工单审批失败");
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 单笔风控审批
	 */
	public void auditRiskSingle(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				//推送
				boolean f = merchantTaskDataService.updateMerchantLimit(bean,(UserBean)userSession);
				if(f){
					//
					f=merchantTaskDataService.updateAuditRiskSingle(bean, (UserBean)userSession);
					json.setSuccess(true);
					json.setMsg("工单审批成功");
				}else{
					json.setSuccess(true);
					json.setMsg("该工单可能已被审批，或者你没有选择审批类型（不受理，已受理）！");
				}
				
			} catch (Exception e) {
				log.error("工单审批异常：" + e);
				json.setMsg("工单审批失败");
			}
		}
		
		super.writeJson(json);
	}
	
	
	/**
	 * 传统商户转T+0单笔审批
	 */
	public void auditChangeT0Single(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				boolean f=merchantTaskDataService.updateChangeT0Single(bean, (UserBean)userSession);
				if(f){
					json.setSuccess(true);
					json.setMsg("工单审批成功");
				}else{
					json.setSuccess(true);
					json.setMsg("该工单可能已被审批，或者你没有选择审批类型（不受理，已受理）！");
				}
				
			} catch (Exception e) {
				log.error("工单审批异常：" + e);
				json.setMsg("工单审批失败");
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 工单详细查询
	 */
	public void findDetail(){
		String id=getRequest().getParameter("id");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = merchantTaskDataService.findDetail(Integer.valueOf(id));
		} catch (Exception e) {
			log.error("查询工单详细信息异常：" + e);
		}
		super.writeJson(map);
	}
	
	
	/**
	 * 风控工单详细导出
	 */
	public void exportMerchantTaskRiskSelectedData(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=merchantTaskDataService.exportAll(bean,((UserBean)userSession),bmtkids);
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("风控工单详细资料导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出风控工单资料Excel成功");
			} catch (Exception e) {
				log.error("导出风控工单资料Excel异常：" + e);
				json.setMsg("导出风控工单资料Excel失败");
			}
		}
	}
}
