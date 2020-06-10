package com.hrt.biz.bill.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.pagebean.PurchaseStorageBean;
import com.hrt.biz.bill.service.IPurchaseStorageService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.opensymphony.xwork2.ModelDriven;

public class PurchaseStorageAction extends BaseAction implements ModelDriven<PurchaseStorageBean>{

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(PurchaseStorageAction.class);
	private PurchaseStorageBean purchaseStorageBean = new PurchaseStorageBean();
	private IPurchaseStorageService purchaseStorageService;
	@Override
	public PurchaseStorageBean getModel() {
		return purchaseStorageBean;
	}
	
	public IPurchaseStorageService getPurchaseStorageService() {
		return purchaseStorageService;
	}

	public void setPurchaseStorageService(IPurchaseStorageService purchaseStorageService) {
		this.purchaseStorageService = purchaseStorageService;
	}
	
	/**
	 * 查询库存管理-借样单
	 */
	public void listPurchaseStorage(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				purchaseStorageBean.setStorageTypes(1);
				dgb = purchaseStorageService.listPurchaseStorage(purchaseStorageBean, ((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("分页查询查询库存管理-借样单信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询库存管理-盘盈盘亏
	 */
	public void listPurchaseStorageP(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				purchaseStorageBean.setStorageTypes(2);
				dgb = purchaseStorageService.listPurchaseStorage(purchaseStorageBean, ((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("分页查询查询库存管理-盘盈盘亏信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询库存管理-库存调拨
	 */
	public void listPurchaseStorageD(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				purchaseStorageBean.setStorageTypes(4);
				dgb = purchaseStorageService.listPurchaseStorage(purchaseStorageBean, ((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("分页查询查询库存管理-库存调拨信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 新增借样单
	 */
	public void savePurchaseStorage(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				purchaseStorageService.savePurchaseStorage(purchaseStorageBean, ((UserBean)userSession));
				jsonBean.setSuccess(true);
				jsonBean.setMsg("新增借样单成功");
			}
		} catch (Exception e) {
			log.error("新增借样单异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("新增借样单失败");
			super.writeJson(jsonBean);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 新增盘盈盘亏
	 */
	public void savePurchaseStorageP(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				purchaseStorageService.savePurchaseStorage(purchaseStorageBean, ((UserBean)userSession));
				jsonBean.setSuccess(true);
				jsonBean.setMsg("新增盘盈盘亏成功");
			}
		} catch (Exception e) {
			log.error("新增盘盈盘亏异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("新增盘盈盘亏失败");
			super.writeJson(jsonBean);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 新增库存调拨
	 */
	public void savePurchaseStorageD(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				purchaseStorageService.savePurchaseStorage(purchaseStorageBean, ((UserBean)userSession));
				jsonBean.setSuccess(true);
				jsonBean.setMsg("新增库存调拨成功");
			}
		} catch (Exception e) {
			log.error("新增库存调拨异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("新增库存调拨失败");
			super.writeJson(jsonBean);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 修改借样单
	 */
	public void updatePurchaseStorage(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		}else{
			try{
				//修改采购订单
				Integer i = purchaseStorageService.updatePurchaseStorage(purchaseStorageBean, ((UserBean)userSession));
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到借样单，请确认后再试");
				}else if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("修改借样单成功");
				}
			}catch (Exception e){
				log.error("修改借样单异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("修改借样单失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 修改盘盈盘亏
	 */
	public void updatePurchaseStorageP(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		}else{
			try{
				//修改采购订单
				Integer i = purchaseStorageService.updatePurchaseStorage(purchaseStorageBean, ((UserBean)userSession));
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到盘盈盘亏，请确认后再试");
				}else if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("修改盘盈盘亏成功");
				}
			}catch (Exception e){
				log.error("修改盘盈盘亏异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("修改盘盈盘亏失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 修改库存调拨
	 */
	public void updatePurchaseStorageD(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		}else{
			try{
				//修改采购订单
				Integer i = purchaseStorageService.updatePurchaseStorage(purchaseStorageBean, ((UserBean)userSession));
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到库存调拨，请确认后再试");
				}else if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("修改库存调拨成功");
				}
			}catch (Exception e){
				log.error("修改库存调拨异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("修改库存调拨失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 确认归还借样单
	 */
	public void submitPurchaseStorage(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
				jsonBean.setSessionExpire(true);
			} else {
				Integer i = purchaseStorageService.updatePurchaseStorageStatus(purchaseStorageBean, ((UserBean)userSession));
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到借样单，请确认后再试");
				}else if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("确认归还借样单成功");
				}
			}
		} catch (Exception e) {
			log.error("确认归还借样单异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("确认归还借样单失败");
			super.writeJson(jsonBean);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 提交盘盈盘亏
	 */
	public void submitPurchaseStorageP(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
				jsonBean.setSessionExpire(true);
			} else {
				Integer i = purchaseStorageService.updatePurchaseStorageStatus(purchaseStorageBean, ((UserBean)userSession));
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到盘盈盘亏，请确认后再试");
				}else if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("提交盘盈盘亏成功");
				}
			}
		} catch (Exception e) {
			log.error("提交盘盈盘亏异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("提交盘盈盘亏失败");
			super.writeJson(jsonBean);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 提交库存调拨
	 */
	public void submitPurchaseStorageD(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
				jsonBean.setSessionExpire(true);
			} else {
				Integer i = purchaseStorageService.updatePurchaseStorageStatus(purchaseStorageBean, ((UserBean)userSession));
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到库存调拨，请确认后再试");
				}else if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("提交库存调拨成功");
				}
			}
		} catch (Exception e) {
			log.error("提交库存调拨异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("提交库存调拨失败");
			super.writeJson(jsonBean);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 删除
	 */
	public void deletePurchaseStorage(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
				jsonBean.setSessionExpire(true);
			} else {
				Integer i = purchaseStorageService.deletePurchaseStorage(purchaseStorageBean, ((UserBean)userSession));
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到该信息，请确认后再试");
				}else if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("删除成功");
				}
			}
		} catch (Exception e) {
			log.error("删除异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("删除失败");
			super.writeJson(jsonBean);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 查询待审核盘盈盘亏
	 */
	public void listPurchaseStorageWJoin(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				purchaseStorageBean.setStorageStatuss("2,3");
				dgb = purchaseStorageService.listPurchaseStorage(purchaseStorageBean, ((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("分页查询盘盈盘亏库存调拨信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 审核通过
	 */
	public void updatePurchaseStorageY(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				Integer i = purchaseStorageService.updatePurchaseStorageY(purchaseStorageBean, ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("审核通过成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("审核通过失败");
				}
			} catch (Exception e) {
				log.error("审核通过异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("审核通过失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 审核退回
	 */
	public void updatePurchaseStorageK(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				Integer i = purchaseStorageService.updatePurchaseStorageK(purchaseStorageBean, ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("审核退回成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("审核退回失败");
				}
			} catch (Exception e) {
				log.error("审核退回异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("审核退回失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 导出盘盈盘亏
	 */
	public void exportPurchaseStorage(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				purchaseStorageBean.setStorageStatuss("2,3");
				purchaseStorageBean.setStorageTypes(2);
				List<Map<String,Object>> list = purchaseStorageService.exportPurchaseStorage(purchaseStorageBean, ((UserBean)userSession));
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"类型","时间","品牌", "机型", "数量","状态","创建人"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					if("2".equals(map.get("STORAGESTATUS").toString())){
						map.put("STORAGESTATUS","待审核");
					}else if("3".equals(map.get("STORAGESTATUS").toString())){
						map.put("STORAGESTATUS","已审核");
					}
					if("2".equals(map.get("STORAGETYPE").toString())){
						map.put("STORAGETYPE","盘盈");
					}else if("3".equals(map.get("STORAGETYPE").toString())){
						map.put("STORAGETYPE","盘亏");
					}
					String []rowContents ={
							map.get("STORAGETYPE")==null?"":map.get("STORAGETYPE").toString(),	
							map.get("STORAGECDATE")==null?"":map.get("STORAGECDATE").toString(),
							map.get("STORAGEBRANDNAME")==null?"":map.get("STORAGEBRANDNAME").toString(),
							map.get("STORAGEMACHINEMODEL")==null?"":map.get("STORAGEMACHINEMODEL").toString(),
							map.get("STORAGEMACHINENUM")==null?"":map.get("STORAGEMACHINENUM").toString(),
							map.get("STORAGESTATUS")==null?"":map.get("STORAGESTATUS").toString(),
							map.get("STORAGECBY")==null?"":map.get("STORAGECBY").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "盘盈盘亏资料.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出盘盈盘亏Excel成功");
			}
		} catch (Exception e) {
			log.error("导出盘盈盘亏资料：" + e);
		}
	}
	
	/**
	 * 导出库存调拨
	 */
	public void exportPurchaseStorageD(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				purchaseStorageBean.setStorageStatuss("2,3");
				purchaseStorageBean.setStorageTypes(4);
				List<Map<String,Object>> list = purchaseStorageService.exportPurchaseStorageD(purchaseStorageBean, ((UserBean)userSession));
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"调拨时间","调出库位","调入库位", "品牌", "机型","数量","状态","创建人"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					if("2".equals(map.get("STORAGESTATUS").toString())){
						map.put("STORAGESTATUS","待审核");
					}else if("3".equals(map.get("STORAGESTATUS").toString())){
						map.put("STORAGESTATUS","已审核");
					}
					String []rowContents ={
							map.get("STORAGECDATE")==null?"":map.get("STORAGECDATE").toString(),	
							map.get("OUTSTORAGE")==null?"":map.get("OUTSTORAGE").toString(),
							map.get("INSTORAGE")==null?"":map.get("INSTORAGE").toString(),
							map.get("STORAGEBRANDNAME")==null?"":map.get("STORAGEBRANDNAME").toString(),
							map.get("STORAGEMACHINEMODEL")==null?"":map.get("STORAGEMACHINEMODEL").toString(),
							map.get("STORAGEMACHINENUM")==null?"":map.get("STORAGEMACHINENUM").toString(),
							map.get("STORAGESTATUS")==null?"":map.get("STORAGESTATUS").toString(),
							map.get("STORAGECBY")==null?"":map.get("STORAGECBY").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "库存调拨资料.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出库存调拨Excel成功");
			}
		} catch (Exception e) {
			log.error("导出库存调拨资料：" + e);
		}
	}
}