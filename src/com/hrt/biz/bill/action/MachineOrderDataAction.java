package com.hrt.biz.bill.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.model.MachineOrderDataModel;
import com.hrt.biz.bill.entity.pagebean.MachineOrderDataBean;
import com.hrt.biz.bill.service.IMachineOrderDataService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 机具订单处理类
 */
public class MachineOrderDataAction extends BaseAction implements ModelDriven<MachineOrderDataBean>{

	private static final long serialVersionUID = 1L;
	
	
	private Log log = LogFactory.getLog(MachineInfoAction.class);
	
	private IMachineOrderDataService machineOrderDataService;
	
	private String ids;
	
	private MachineOrderDataBean machineOrderDataBean=new MachineOrderDataBean();

	public IMachineOrderDataService getMachineOrderDataService() {
		return machineOrderDataService;
	}

	public void setMachineOrderDataService(
			IMachineOrderDataService machineOrderDataService) {
		this.machineOrderDataService = machineOrderDataService;
	}

	@Override
	public MachineOrderDataBean getModel() {
		return machineOrderDataBean;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	/**
	 * 审批初始化
	 */
	public void initApp(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		machineOrderDataBean.setApproveStatus("Z");
		machineOrderDataBean.setStatus(0);
		try {
			dgb = machineOrderDataService.queryOrders(machineOrderDataBean,user);
		} catch (Exception e) {
			log.error("分页查询机具库存信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 汇款确认初始化
	 */
	public void initSup(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		machineOrderDataBean.setApproveStatus("Y");
		machineOrderDataBean.setStatus(2);
		try {
			dgb = machineOrderDataService.queryOrders(machineOrderDataBean,user);
		} catch (Exception e) {
			log.error("分页查询机具库存信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 装箱发货初始化
	 */
	public void initSend(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		machineOrderDataBean.setApproveStatus("Y");
		machineOrderDataBean.setStatus(3);
		try {
			dgb = machineOrderDataService.queryOrders(machineOrderDataBean,user);
		} catch (Exception e) {
			log.error("分页查询机具库存信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 商户查询初始化
	 */
	public void initFind(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = machineOrderDataService.queryOrders(machineOrderDataBean,user);
		} catch (Exception e) {
			log.error("分页查询机具库存信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询订单详细信息
	 */
	public void find(){
		List<Map<String,String>> list = new ArrayList<Map<String, String>>();
		try {
			list = machineOrderDataService.getOrder(Integer.parseInt(ids));
		} catch (Exception e) {
			log.error("查询订单详细信息异常：" + e);
		}
		super.writeJson(list);
	}
	
	public void add(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				String[] values=getRequest().getParameterValues("IDNUM");
				machineOrderDataService.save(machineOrderDataBean, (UserBean)userSession,values);
				json.setSuccess(true);
				json.setMsg("提交订单成功");
			} catch (Exception e) {
				log.error("提交订单异常：" + e);
				json.setMsg("提交订单失败");
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 审批机具订单
	 */
	public void editApp() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				MachineOrderDataModel machineOrderDataModel=machineOrderDataService.getOrderModel(machineOrderDataBean.getBmoID());
				if(!"Z".equals(machineOrderDataModel.getApproveStatus())){
					json.setSuccess(false);
					json.setMsg("失败，机具订单已经被审批");
				}else{
					machineOrderDataService.updateApp(machineOrderDataBean, (UserBean)userSession);
					json.setSuccess(true);
					json.setMsg("审批机具订单成功");
				}
				
				
			} catch (Exception e) {
				log.error("审批机具订单异常" + e);
				json.setMsg("审批机具订单失败");
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 机具订单汇款确认
	 */
	public void editSup() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				MachineOrderDataModel machineOrderDataModel=machineOrderDataService.getOrderModel(machineOrderDataBean.getBmoID());
				if(!"Y".equals(machineOrderDataModel.getApproveStatus())||machineOrderDataModel.getStatus()!=2){
					json.setSuccess(false);
					json.setMsg("失败，机具订单已经被审批");
				}else{
					if(machineOrderDataBean.getStatus()==3){
						machineOrderDataService.updateSup(machineOrderDataBean, (UserBean)userSession);
						json.setSuccess(true);
						json.setMsg("机具缴款成功");
					}else{
						json.setSuccess(true);
						json.setMsg("未修改状态");
					}
				}
			} catch (Exception e) {
				log.error("机具缴款异常" + e);
				json.setMsg("机具缴款失败");
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 机具装箱发货
	 */
	public void editSend() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				MachineOrderDataModel machineOrderDataModel=machineOrderDataService.getOrderModel(machineOrderDataBean.getBmoID());
				if(!"Y".equals(machineOrderDataModel.getApproveStatus())||machineOrderDataModel.getStatus()!=3){
					json.setSuccess(false);
					json.setMsg("失败，机具订单已经被审批");
				}else{
					if(machineOrderDataBean.getStatus()==4){
						machineOrderDataService.updateSend(machineOrderDataBean, (UserBean)userSession);
						json.setSuccess(true);
						json.setMsg("审批机具订单成功");
					}else{
						json.setSuccess(true);
						json.setMsg("未修改发货状态");
					}
					
				}
			} catch (Exception e) {
				log.error("审批机具订单异常" + e);
				json.setMsg("审批机具订单失败");
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 删除机具订单
	 */
	public void delete() {
		JsonBean json = new JsonBean();
		try {
			machineOrderDataService.delete(Integer.parseInt(ids));
			json.setSuccess(true);
			json.setMsg("删除机具订单成功");
		} catch (Exception e) {
			log.error("删除机具订单异常：" + e);
			json.setMsg("删除机具订单失败");
		}
		super.writeJson(json);
	}

}
