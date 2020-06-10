package com.hrt.biz.bill.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.model.PurchaseDetailModel;
import com.hrt.biz.bill.entity.model.PurchaseOrderModel;
import com.hrt.biz.bill.entity.pagebean.PurchaseInvoiceBean;
import com.hrt.biz.bill.service.IPurchaseInvoiceService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.opensymphony.xwork2.ModelDriven;

public class PurchaseInvoiceAction extends BaseAction implements ModelDriven<PurchaseInvoiceBean>{

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(PurchaseInvoiceAction.class);
	private PurchaseInvoiceBean purchaseInvoiceBean = new PurchaseInvoiceBean();
	private IPurchaseInvoiceService purchaseInvoiceService;
	@Override
	public PurchaseInvoiceBean getModel() {
		return purchaseInvoiceBean;
	}
	
	public IPurchaseInvoiceService getPurchaseInvoiceService() {
		return purchaseInvoiceService;
	}

	public void setPurchaseInvoiceService(IPurchaseInvoiceService purchaseInvoiceService) {
		this.purchaseInvoiceService = purchaseInvoiceService;
	}
	
	/**
	 * 查询发票核销
	 */
	public void listPurchaseInvoice(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				purchaseInvoiceBean.setInvoiceStatuss("1,2,10");
				dgb = purchaseInvoiceService.listPurchaseInvoice(purchaseInvoiceBean, ((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("分页查询发票核销信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 发票核销
	 */
	public void savePurchaseInvoice(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				Integer i = purchaseInvoiceService.savePurchaseInvoice(purchaseInvoiceBean, ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("发票核销成功");
				}else if(i==2){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("含税金额不能大于待核销金额");
				}else if(i==3){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("核销数量不能大于待核销数量");
				}else if(i==4){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("核销退货数量不能大于待核销退货数量");
				}else if(i==5){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("核销退款金额不能大于待核销退款金额");
				}
			}
		} catch (Exception e) {
			log.error("发票核销异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("发票核销失败");
			super.writeJson(jsonBean);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 提交发票核销
	 */
	public void submitPurchaseInvoice(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				Integer i = purchaseInvoiceService.updatePurchaseInvoiceStatus(purchaseInvoiceBean, ((UserBean)userSession));
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到发票核销信息");
				}else if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("提交发票核销成功");
				}else if(i==2){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("含税金额不能大于待核销金额,不能提交");
				}else if(i==3){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("核销数量不能大于待核销数量，不能提交");
				}else if(i==4){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("核销退货数量不能大于待核销退货数量");
				}else if(i==5){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("核销退款金额不能大于待核销退款金额");
				}
			}
		} catch (Exception e) {
			log.error("提交发票核销异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("提交发票核销失败");
			super.writeJson(jsonBean);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 删除发票核销
	 */
	public void deletePurchaseInvoice(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				//删除付款单
				Integer i = purchaseInvoiceService.deletePurchaseInvoice(purchaseInvoiceBean.getPiid(), ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("删除发票核销成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到发票核销单，请确认后再试");
				}
			} catch (Exception e) {
				log.error("删除发票核销异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("删除付款单失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 查询待发票核销待审核
	 */
	public void listPurchaseInvoiceWJoin(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				purchaseInvoiceBean.setInvoiceStatuss("2,10");
				dgb = purchaseInvoiceService.listPurchaseInvoice(purchaseInvoiceBean, ((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("分页查询审核发票核销信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 审核通过发票核销
	 */
	public void updatePurchaseInvoiceY(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				Integer i = purchaseInvoiceService.updatePurchaseInvoiceY(purchaseInvoiceBean, ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("审核通过发票核销成功");
				}else if(i==2){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("含税金额不能大于待核销金额,不能通过");
				}else if(i==3){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("核销数量不能大于待核销数量，不能通过");
				}else if(i==4){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("核销退货数量不能大于待核销退货数量");
				}else if(i==5){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("核销退款金额不能大于待核销退款金额");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("审核通过发票核销失败");
				}
			} catch (Exception e) {
				log.error("审核通过发票核销异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("审核通过发票核销失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 审核退回发票核销
	 */
	public void updatePurchaseInvoiceK(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				Integer i = purchaseInvoiceService.updatePurchaseInvoiceK(purchaseInvoiceBean, ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("审核退回发票核销成功");
					super.writeJson(jsonBean);
				}
				jsonBean.setSuccess(false);
				jsonBean.setMsg("审核退回发票核销失败");
			} catch (Exception e) {
				log.error("审核退回发票核销异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("审核退回发票核销失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 导出发票审核
	 */
	public void exportPurchaseInvoice(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				purchaseInvoiceBean.setInvoiceStatuss("2,10");
				List<Map<String,Object>> list = purchaseInvoiceService.exportPurchaseInvoice(purchaseInvoiceBean, ((UserBean)userSession));
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"采购单订单号","发票号码","申请核销金额", "状态", "发票项目","数量", "未税金额","税金","含税金额","备注","创建时间","创建人"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					if("2".equals(map.get("INVOICESTATUS").toString())){
						map.put("INVOICESTATUS","待审核");
					}else if("10".equals(map.get("INVOICESTATUS").toString())){
						map.put("INVOICESTATUS","已审核");
					}
					String []rowContents ={
							map.get("INVOICEORDERID")==null?"":map.get("INVOICEORDERID").toString(),	
							map.get("INVOICEID")==null?"":map.get("INVOICEID").toString(),
							map.get("INVOICEAMT")==null?"":map.get("INVOICEAMT").toString(),
							map.get("INVOICESTATUS")==null?"":map.get("INVOICESTATUS").toString(),
							map.get("TEXT")==null?"":map.get("TEXT").toString(),
							map.get("INVOICENUM")==null?"":map.get("INVOICENUM").toString(),
							map.get("NOTAX")==null?"":map.get("NOTAX").toString(),
							map.get("TAX")==null?"":map.get("TAX").toString(),
							map.get("HAVETAX")==null?"":map.get("HAVETAX").toString(),
							map.get("INVOICEREMARK")==null?"":map.get("INVOICEREMARK").toString(),
							map.get("INVOICECDATE")==null?"":map.get("INVOICECDATE").toString(),
							map.get("INVOICECBY")==null?"":map.get("INVOICECBY").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "发票审核资料.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出发票审核资料Excel成功");
			}
		} catch (Exception e) {
			log.error("导出发票审核资料：" + e);
		}
	}
}