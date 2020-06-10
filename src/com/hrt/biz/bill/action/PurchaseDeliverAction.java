package com.hrt.biz.bill.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.hrt.biz.bill.entity.pagebean.PurchaseDeliverBean;
import com.hrt.biz.bill.service.IPurchaseDeliverService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.hrt.util.UsePioOutExcel;
import com.opensymphony.xwork2.ModelDriven;

public class PurchaseDeliverAction extends BaseAction implements ModelDriven<PurchaseDeliverBean>{

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(PurchaseDeliverAction.class);
	private PurchaseDeliverBean purchaseDeliverBean = new PurchaseDeliverBean();
	private IPurchaseDeliverService purchaseDeliverService;
	private File upload ;
	
	@Override
	public PurchaseDeliverBean getModel() {
		return purchaseDeliverBean;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public IPurchaseDeliverService getPurchaseDeliverService() {
		return purchaseDeliverService;
	}

	public void setPurchaseDeliverService(IPurchaseDeliverService purchaseDeliverService) {
		this.purchaseDeliverService = purchaseDeliverService;
	}
	
	/**
	 * 查询发货信息
	 */
	public void listPurchaseDeliver(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				dgb = purchaseDeliverService.queryPurchaseDeliver(purchaseDeliverBean, ((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("分页查询发货信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询发货信息&采购单明细
	 */
	public void queryPurchaseDeliverAndDetail(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				dgb = purchaseDeliverService.queryPurchaseDeliverAndDetail(purchaseDeliverBean, ((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("查询发货信息&采购单明细异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 销售发货
	 */
	public void saveSendOutMachine(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			UserBean user=(UserBean)userSession;
			if(user == null){
				jsonBean.setSessionExpire(true);
			}else{
				Integer i = purchaseDeliverService.saveSendOutMachine(purchaseDeliverBean, user);
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到订单");
				}else if(i==2){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("发货数量应小于待发货数量");
				}else if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("发货成功");
				}else if(i==3){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("订单未通过审核");
				}else if(i==4){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("订单变更申请未通过审核");
				}
			}
		} catch (Exception e) {
			log.error("发货异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("发货失败");
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 提交发货信息
	 */
	public void submitPurchaseDeliver(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			UserBean user=(UserBean)userSession;
			if(user == null){
				jsonBean.setSessionExpire(true);
			}else{
				Integer i = purchaseDeliverService.updatePurchaseDeliver(purchaseDeliverBean, user);
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到待发货信息，请确认后再试");
				}else if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("提交发货信息成功");
				}
			}
		} catch (Exception e) {
			log.error("提交发货信息异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("提交发货信息失败");
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 添加快递信息
	 */
	public void addDeliverInfo(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			UserBean user=(UserBean)userSession;
			if(user == null){
				jsonBean.setSessionExpire(true);
			}else{
				purchaseDeliverBean.setDeliverId(super.getRequest().getParameter("DELIVERID"));
				purchaseDeliverBean.setDeliverName(super.getRequest().getParameter("DELIVERNAME"));
				purchaseDeliverBean.setPdlid(Integer.valueOf(super.getRequest().getParameter("PDLID")));
				Integer i = purchaseDeliverService.addDeliverInfo(purchaseDeliverBean, user);
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到订单");
				}else if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("添加快递信息成功");
				}
			}
		} catch (Exception e) {
			log.error("添加快递信息异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("添加快递信息失败");
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 修改发货信息
	 */
	public void updateDeliverInfo(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			UserBean user=(UserBean)userSession;
			if(user == null){
				jsonBean.setSessionExpire(true);
			}else{
				Integer i = purchaseDeliverService.updateDeliverInfo(purchaseDeliverBean, user);
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到订单");
				}else if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("修改发货信息成功");
				}
			}
		} catch (Exception e) {
			log.error("修改发货信息异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("修改发货信息失败");
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 导出发货信息&采购单明细
	 */
	public void exportPurchaseDeliver(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String, Object>> list = purchaseDeliverService.exportPurchaseDeliver(purchaseDeliverBean, ((UserBean)userSession));
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"机构号", "机构名称", "快递单号", "快递公司","联系人","联系电话","联系邮箱","联系地址","采购单","采购单日期","订单类型","机型","返利类型","刷卡费率","扫码费率", "提现费","数量","已入数量","状态","发货创建时间"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					if("1".equals(map.get("ORDERTYPE").toString())){
						map.put("ORDERTYPE","采购订单");
					}else if("2".equals(map.get("ORDERTYPE").toString())){
						map.put("ORDERTYPE","赠品订单");
					}else if("3".equals(map.get("ORDERTYPE").toString())){
						map.put("ORDERTYPE","回购订单");
					}
					if("1".equals(map.get("DELIVERSTATUS").toString())){
						map.put("DELIVERSTATUS","待提交");
					}else if("2".equals(map.get("DELIVERSTATUS").toString())){
						map.put("DELIVERSTATUS","待发货");
					}else if("3".equals(map.get("DELIVERSTATUS").toString())){
						map.put("DELIVERSTATUS","已发货");
					}else if("4".equals(map.get("DELIVERSTATUS").toString())){
						map.put("DELIVERSTATUS","已分配");
					}
					String []rowContents ={
							map.get("DELIVERUNNO")==null?"":map.get("DELIVERUNNO").toString(),	
							map.get("DELIVERPURNAME")==null?"":map.get("DELIVERPURNAME").toString(),
							map.get("DELIVERID")==null?"":map.get("DELIVERID").toString(),
							map.get("DELIVERNAME")==null?"":map.get("DELIVERNAME").toString(),
							map.get("DELIVERCONTACTS")==null?"":map.get("DELIVERCONTACTS").toString(),
							map.get("DELIVERCONTACTPHONE")==null?"":map.get("DELIVERCONTACTPHONE").toString(),
							map.get("DELIVERCONTACTMAIL")==null?"":map.get("DELIVERCONTACTMAIL").toString(),
							map.get("DELIVERRECEIVEADDR")==null?"":map.get("DELIVERRECEIVEADDR").toString(),
							map.get("ORDERID")==null?"":map.get("ORDERID").toString(),
							map.get("ORDERDAY")==null?"":map.get("ORDERDAY").toString(),
							map.get("ORDERTYPE")==null?"":map.get("ORDERTYPE").toString(),
							map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
							map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
							map.get("RATE")==null?"":map.get("RATE").toString(),
							map.get("SCANRATE")==null?"":map.get("SCANRATE").toString(),
							map.get("SECONDRATE")==null?"":map.get("SECONDRATE").toString(),
							map.get("DELIVENUM")==null?"":map.get("DELIVENUM").toString(),
							map.get("ALLOCATEDNUM")==null?"":map.get("ALLOCATEDNUM").toString(),
							map.get("DELIVERSTATUS")==null?"":map.get("DELIVERSTATUS").toString(),
							map.get("DELIVERCDATE")==null?"":map.get("DELIVERCDATE").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "出库资料.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出出库资料Excel成功");
			}
		} catch (Exception e) {
			log.error("导出出库资料：" + e);
		}
	}
}