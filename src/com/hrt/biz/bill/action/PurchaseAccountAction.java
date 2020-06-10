package com.hrt.biz.bill.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.model.PurchaseDetailModel;
import com.hrt.biz.bill.entity.model.PurchaseOrderModel;
import com.hrt.biz.bill.entity.pagebean.PurchaseAccountBean;
import com.hrt.biz.bill.service.IPurchaseAccountService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.opensymphony.xwork2.ModelDriven;

public class PurchaseAccountAction extends BaseAction implements ModelDriven<PurchaseAccountBean>{

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(PurchaseAccountAction.class);
	private PurchaseAccountBean purchaseAccountBean = new PurchaseAccountBean();
	private IPurchaseAccountService purchaseAccountService;
	@Override
	public PurchaseAccountBean getModel() {
		return purchaseAccountBean;
	}
	
	public IPurchaseAccountService getPurchaseAccountService() {
		return purchaseAccountService;
	}

	public void setPurchaseAccountService(IPurchaseAccountService purchaseAccountService) {
		this.purchaseAccountService = purchaseAccountService;
	}
	
	/**
	 * 查询采购付款单
	 */
	public void listPurchaseAccount(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				purchaseAccountBean.setAccountStatuss("1,2,3,9");
				dgb = purchaseAccountService.listPurchaseAccount(purchaseAccountBean, ((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("分页查询采购单付款账务信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 采购订单付款
	 */
	public void savePurchaseAccount(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				Integer i = purchaseAccountService.savePurchaseAccount(purchaseAccountBean, ((UserBean)userSession));
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("金额不能大于剩余付款金额");
				}else if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("采购单付款成功");
				}else if(i==2){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("退款金额不能大于已付款金额和剩余退款金额");
				}
			}
		} catch (Exception e) {
			log.error("采购单付款异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("采购单付款失败");
			super.writeJson(jsonBean);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 修改采购付款单
	 */
	public void updatePurchaseOrder(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		}else{
			try{
				//修改采购订单
				Integer i = purchaseAccountService.updatePurchaseAccount(purchaseAccountBean, ((UserBean)userSession));
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到付款单，请确认后再试");
				}else if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("修改采购付款单成功");
				}else if(i==2){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("付款金额不能大于剩余付款金额");
				}
			}catch (Exception e){
				log.error("修改采购付款单异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("修改采购付款单失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 提交付款单
	 */
	public void submitPurchaseAccount(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				Integer i = purchaseAccountService.updatePurchaseAccountStatus(purchaseAccountBean, ((UserBean)userSession));
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("金额不能大于剩余付款金额");
				}else if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("提交付款单成功");
				}
			}
		} catch (Exception e) {
			log.error("提交付款单异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("提交付款单失败");
			super.writeJson(jsonBean);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 删除付款单
	 */
	public void deletePurchaseAccount(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				//删除付款单
				Integer i = purchaseAccountService.deletePurchaseAccount(purchaseAccountBean.getPaid(), ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("删除付款单成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到付款单，请确认后再试");
				}
			} catch (Exception e) {
				log.error("删除付款单异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("删除付款单失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 查询待审核采购付款单
	 */
	public void listPurchaseAccountWJoin(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				purchaseAccountBean.setAccountStatuss("2,3,9");
				dgb = purchaseAccountService.listPurchaseAccount(purchaseAccountBean, ((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("分页查询采购单付款账务信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 审核通过付款单
	 */
	public void updatePurchaseAccountY(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				Integer i = purchaseAccountService.updatePurchaseAccountY(purchaseAccountBean, ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("审核通过采购付款单成功");
				}else if(i==2){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("通过付款金额大于待付款金额");
				}else if(i==3){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("通过退款金额不能大于待退款金额和已付款金额");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("审核通过采购付款单/退款单失败");
				}
			} catch (Exception e) {
				log.error("审核通过采购付款单异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("审核通过采购付款单失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 审核退回付款单
	 */
	public void updatePurchaseAccountK(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				Integer i = purchaseAccountService.updatePurchaseAccountK(purchaseAccountBean, ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("审核退回采购付款单成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("审核退回采购付款单失败");
				}
			} catch (Exception e) {
				log.error("审核退回采购付款单异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("审核退回采购付款单失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 导出付款单
	 */
	public void exportPurchaseAccount(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				purchaseAccountBean.setAccountStatuss("2,3,9");
				List<Map<String,Object>> list = purchaseAccountService.exportPurchaseAccount(purchaseAccountBean, ((UserBean)userSession));
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"采购单订单号","总金额","已付款金额", "付款金额", "付款方式","状态", "备注","创建时间","创建人"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					if("2".equals(map.get("ACCOUNTSTATUS").toString())){
						map.put("ACCOUNTSTATUS","待审核");
					}else if("3".equals(map.get("ACCOUNTSTATUS").toString())){
						map.put("ACCOUNTSTATUS","发票核销");
					}else if("9".equals(map.get("ACCOUNTSTATUS").toString())){
						map.put("ACCOUNTSTATUS","已审核");
					}
					String []rowContents ={
							map.get("ACCOUNTORDERID")==null?"":map.get("ACCOUNTORDERID").toString(),	
							map.get("ORDERAMT")==null?"":map.get("ORDERAMT").toString(),
							map.get("ORDERPAYAMT")==null?"":map.get("ORDERPAYAMT").toString(),
							map.get("ACCOUNTAMT")==null?"":map.get("ACCOUNTAMT").toString(),
							map.get("PAYTYPE")==null?"":map.get("PAYTYPE").toString(),
							map.get("ACCOUNTSTATUS")==null?"":map.get("ACCOUNTSTATUS").toString(),
							map.get("ACCOUNTREMARK")==null?"":map.get("ACCOUNTREMARK").toString(),
							map.get("ACCOUNTCDATE")==null?"":map.get("ACCOUNTCDATE").toString(),
							map.get("ACCOUNTCBY")==null?"":map.get("ACCOUNTCBY").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "付款单资料.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出付款单资料Excel成功");
			}
		} catch (Exception e) {
			log.error("导出付款单资料：" + e);
		}
	}
}