package com.hrt.biz.bill.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.pagebean.PurchaseDetailBean;
import com.hrt.biz.bill.service.IPurchaseDetailService;
import com.hrt.biz.bill.service.IPurchaseOrderService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.opensymphony.xwork2.ModelDriven;

public class PurchaseDetailAction extends BaseAction implements ModelDriven<PurchaseDetailBean>{

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(PurchaseDetailAction.class);
	private PurchaseDetailBean purchaseDetailBean = new PurchaseDetailBean();
	private IPurchaseDetailService purchaseDetailService;
	private IPurchaseOrderService purchaseOrderService;
	
	@Override
	public PurchaseDetailBean getModel() {
		return purchaseDetailBean;
	}

	public IPurchaseOrderService getPurchaseOrderService() {
		return purchaseOrderService;
	}

	public void setPurchaseOrderService(IPurchaseOrderService purchaseOrderService) {
		this.purchaseOrderService = purchaseOrderService;
	}

	public PurchaseDetailBean getPurchaseDetailBean() {
		return purchaseDetailBean;
	}

	public void setPurchaseDetailBean(PurchaseDetailBean purchaseDetailBean) {
		this.purchaseDetailBean = purchaseDetailBean;
	}

	public IPurchaseDetailService getPurchaseDetailService() {
		return purchaseDetailService;
	}

	public void setPurchaseDetailService(IPurchaseDetailService purchaseDetailService) {
		this.purchaseDetailService = purchaseDetailService;
	}
	/**
	 * 审核退回退货
	 */
	public void updateBackReturnDetail(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			Integer i = purchaseDetailService.updateBackReturnDetail(purchaseDetailBean, ((UserBean)userSession));
			if(i==1){
				jsonBean.setSuccess(true);
				jsonBean.setMsg("审核退回退货成功");
			}else{
				jsonBean.setSuccess(false);
				jsonBean.setMsg("审核退回退货失败");
			}
		} catch (Exception e) {
			log.error("审核退回退货异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("审核退回退货失败");
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 审核通过退货
	 */
	public void updateReturnDetail(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			Integer i = purchaseDetailService.updateReturnDetail(purchaseDetailBean, ((UserBean)userSession));
			if(i==1){
				jsonBean.setSuccess(true);
				jsonBean.setMsg("审核通过退货成功");
			}else{
				jsonBean.setSuccess(false);
				jsonBean.setMsg("审核通过退货失败");
			}
		} catch (Exception e) {
			log.error("审核通过退货异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("审核通过退货失败");
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 审核通过退货（厂商单）
	 */
	public void updateReturnDetail2(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			Integer i = purchaseDetailService.updateReturnDetail2(purchaseDetailBean, ((UserBean)userSession));
			if(i==1){
				jsonBean.setSuccess(true);
				jsonBean.setMsg("审核通过退货成功");
			}else{
				jsonBean.setSuccess(false);
				jsonBean.setMsg("审核通过退货失败");
			}
		} catch (Exception e) {
			log.error("审核通过退货异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("审核通过退货失败");
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 查询采购单明细(不包含已审批/退回)
	 */
	public void listPurchaseDetailByPoid(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = purchaseDetailService.queryPurchaseDetailByPoid(purchaseDetailBean, ((UserBean)userSession));
		} catch (Exception e) {
			log.error("查询采购单明细异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 通过ID查询采购单明细
	 */
	public void listPurchaseDetailById(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = purchaseDetailService.queryPurchaseDetailById(purchaseDetailBean, ((UserBean)userSession));
		} catch (Exception e) {
			log.error("查询采购单明细异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 导出退货明细&采购单总
	 */
	public void exportReturnOrderAndDetail(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			List<Map<String, Object>> list = purchaseDetailService.exportReturnOrderAndDetail(purchaseDetailBean);
			
			List<String[]> excelList = new ArrayList<String[]>();
			String title [] = {"订单号", "订单日期", "大类", "供应商名称", "采购机构号","采购机构名称","联系人", "联系电话", "联系邮箱", "退货数量", "退货金额","退货人", "状态","申请退货时间","审核人","审核时间","退回原因"};
			
			excelList.add(title);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				if("1".equals(map.get("ORDERMETHOD").toString())){
					map.put("ORDERMETHOD","厂商单");
				}else if("2".equals(map.get("ORDERMETHOD").toString())){
					map.put("ORDERMETHOD","代理单");
				}
				if("6".equals(map.get("DETAILSTATUS").toString())&&!"K".equals(map.get("DETAILAPPROVESTATUS").toString())){
					map.put("DETAILSTATUS","待审核");
				}else if("7".equals(map.get("DETAILSTATUS").toString())&&!"K".equals(map.get("DETAILAPPROVESTATUS").toString())){
					map.put("DETAILSTATUS","已审核");
				}else if("K".equals(map.get("DETAILAPPROVESTATUS").toString())){
					map.put("DETAILSTATUS","已退回");
				}
				String []rowContents ={
						map.get("ORDERID")==null?"":map.get("ORDERID").toString(),	
						map.get("ORDERDAY")==null?"":map.get("ORDERDAY").toString(),
						map.get("ORDERMETHOD")==null?"":map.get("ORDERMETHOD").toString(),
						map.get("VENDORNAME")==null?"":map.get("VENDORNAME").toString(),
						map.get("UNNO")==null?"":map.get("UNNO").toString(),
						map.get("PURCHASERNAME")==null?"":map.get("PURCHASERNAME").toString(),
						map.get("CONTACTS")==null?"":map.get("CONTACTS").toString(),
						map.get("CONTACTPHONE")==null?"":map.get("CONTACTPHONE").toString(),
						map.get("CONTACTMAIL")==null?"":map.get("CONTACTMAIL").toString(),
						map.get("MACHINENUM")==null?"":map.get("MACHINENUM").toString(),
						map.get("DETAILAMT")==null?"":map.get("DETAILAMT").toString(),
						map.get("DETAILCBY")==null?"":map.get("DETAILCBY").toString(),
						map.get("DETAILSTATUS")==null?"":map.get("DETAILSTATUS").toString(),
						map.get("DETAILCDATE")==null?"":map.get("DETAILCDATE").toString(),
						map.get("DETAILAPPROVER")==null?"":map.get("DETAILAPPROVER").toString(),
						map.get("DETAILAPPROVEDATE")==null?"":map.get("DETAILAPPROVEDATE").toString(),
						map.get("DETAILAPPROVENOTE")==null?"":map.get("DETAILAPPROVENOTE").toString()
					};
				excelList.add(rowContents);
				}
			
				String excelName = "退货审核资料.csv";
				
				JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
				json.setSuccess(true);
				json.setMsg("导出退货审核Excel成功");
		} catch (Exception e) {
			log.error("导出退货审核：" + e);
			json.setSuccess(true);
			json.setMsg("导出退货审核Excel失败");
		}
		super.writeJson(json);
	}
	/**
	 * 查询退货明细&采购单总
	 */
	public void listReturnOrderAndDetail(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = purchaseDetailService.listReturnOrderAndDetail(purchaseDetailBean, ((UserBean)userSession));
		} catch (Exception e) {
			log.error("查询退货明细&采购单总：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 查询采购单明细
	 */
	public void listPurchaseDetail(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
			} else {
				dgb = purchaseDetailService.queryPurchaseDetail(purchaseDetailBean, ((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("查询采购单明细异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 删除退回退货
	 */
	public void deletePurchaseReturn(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//删除采购明细删除退回退货
			Integer i = purchaseDetailService.deletePurchaseDetailById(purchaseDetailBean.getPdid(), ((UserBean)userSession));
			if(i==1){
				jsonBean.setSuccess(true);
				jsonBean.setMsg("删除退回退货成功");
			}else{
				jsonBean.setSuccess(false);
				jsonBean.setMsg("未查询到该退货信息，请确认后再试");
			}
		} catch (Exception e) {
			log.error("删除退回退货异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("删除退回退货失败");
		}
		super.writeJson(jsonBean);
	}

	/**
	 * 创建退货
	 */
	public void addPurchaseDetailOrder(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			String returnNum = getRequest().getParameter("returnNum");//remainMachinenum
			List<Map<String, Object>> remainList = purchaseDetailService.queryRemainMachinenum(purchaseDetailBean);
			if (remainList.size()>0) {
				Integer remainNumI = ((BigDecimal)remainList.get(0).get("RMACHINENUM")).intValue();
				Integer returnNumI = Integer.valueOf(returnNum);
				if ((remainNumI-returnNumI)>=0) {
					purchaseDetailService.savePurchaseDetailOrder(purchaseDetailBean.getPoid(),purchaseDetailBean.getPdid(),returnNum,((UserBean)userSession).getLoginName());
					jsonBean.setSuccess(true);
					jsonBean.setMsg("创建退货成功");
				} else {
					jsonBean.setSuccess(false);
					jsonBean.setMsg("创建退货失败,退货数量不能大于出库数量！");
				}
			} else {
				jsonBean.setSuccess(false);
				jsonBean.setMsg("创建退货失败,没有对应的采购订单！");
				log.info("创建退货失败,没有对应的采购订单！[采购订单ID]:"+purchaseDetailBean.getPoid());
			}
		} catch (Exception e) {
			log.error("创建退货异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("创建退货失败");
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 创建退货
	 */
	public void addPurchaseDetailOrder2(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			String returnNum = getRequest().getParameter("returnNum");//remainMachinenum
			List<Map<String, Object>> remainList = purchaseDetailService.queryRemainMachinenum(purchaseDetailBean);
			if (remainList.size()>0) {
				Integer remainNumI = ((BigDecimal)remainList.get(0).get("RMACHINENUM")).intValue();
				Integer returnNumI = Integer.valueOf(returnNum);
				if ((remainNumI-returnNumI)>=0) {
					purchaseDetailService.savePurchaseDetailOrder(purchaseDetailBean.getPoid(),purchaseDetailBean.getPdid(),returnNum,((UserBean)userSession).getLoginName());
					jsonBean.setSuccess(true);
					jsonBean.setMsg("创建退货成功");
				} else {
					jsonBean.setSuccess(false);
					jsonBean.setMsg("创建退货失败,退货数量不能大于已入库数量！");
				}
			} else {
				jsonBean.setSuccess(false);
				jsonBean.setMsg("创建退货失败,没有对应的采购订单！");
				log.info("创建退货失败,没有对应的采购订单！[采购订单ID]:"+purchaseDetailBean.getPoid());
			}
		} catch (Exception e) {
			log.error("创建退货异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("创建退货失败");
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 查询采购单总&明细
	 */
	public void listPurchaseOrderAndDetail(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
			} else {
				purchaseDetailBean.setPutType("1");//入货管理（厂商单+代理单（退货））
				//默认查全部
//				if(purchaseDetailBean.getDetailStatus()==null){
//					purchaseDetailBean.setDetailStatus(6);
//				}
				//1待提交;2订单待审;3厂商单待付款/代理单已审核;4发票待审;5审核通过;9已结款;10已全部核销
				purchaseDetailBean.setStatus("3,4,5,9,10");
				dgb = purchaseDetailService.listPurchaseOrderAndDetail(purchaseDetailBean, ((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("查询采购单总&明细：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询采购单总&明细&订单报表
	 */
	public void listPurchaseOrderAndDetailForTongJi(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
			} else {
				//1待提交;2订单待审;3厂商单待付款/代理单已审核;4发票待审;5审核通过;
				purchaseDetailBean.setStatuss("3,4,5");
				purchaseDetailBean.setDetailMinfo1(1);//统计
				dgb = purchaseDetailService.listPurchaseOrderAndDetailForTongJi(purchaseDetailBean, ((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("查询采购单总&明细&订单报表：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询采购单总&明细(销售发货)
	 */
	public void listPurchaseForDeliver(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
			} else {
				purchaseDetailBean.setOrderMethod(2);
				purchaseDetailBean.setDetailStatuss("6,7,8");
//				purchaseDetailBean.setStatus("3");
				purchaseDetailBean.setOrderType2("4");
				dgb = purchaseDetailService.listPurchaseOrderAndDetail(purchaseDetailBean, ((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("查询采购单总&明细（发货）：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 导出采购单总&明细(销售发货)
	 */
	public void emportPurchaseInfo(){
		JsonBean json = new JsonBean();
		List<Map<String, Object>>list = null;
		List<String[]>cotents = new ArrayList<String[]>();
		String titles[] = {"订单号","采购单日期","大类","采购机构号","采购机构名称","品牌","类型","返利类型","大类",
				"机型","数量","已出库数量","采购人","状态","创建时间"};
		cotents.add(titles);
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				purchaseDetailBean.setOrderMethod(2);
				purchaseDetailBean.setDetailStatuss("6,7,8");
//				purchaseDetailBean.setStatus("3");
				purchaseDetailBean.setOrderType2("4");
				list =  purchaseDetailService.emportPurchaseInfo(purchaseDetailBean);
				if (list!=null&&list.size()>0) {
					for (Map<String, Object>map  : list) {
						String DETAILSTATUS="";
						if("6".equals(map.get("DETAILSTATUS").toString())){
							DETAILSTATUS="未出库";
						}else if ("7".equals(map.get("DETAILSTATUS").toString())){
							DETAILSTATUS="出库中";
						}else if ("8".equals(map.get("DETAILSTATUS").toString())){
							DETAILSTATUS="已出库";
						}
						String ORDERMETHOD="";
						if("1".equals(map.get("ORDERMETHOD").toString())){
							ORDERMETHOD="厂商单";
						}else{
							ORDERMETHOD="代理单";
						}
						String  ORDERTYPE="";
						if("1".equals(map.get("ORDERTYPE").toString())){
							ORDERTYPE="采购订单";
						}else if("2".equals(map.get("ORDERTYPE").toString())){
							ORDERTYPE="赠品订单";
						}else if("3".equals(map.get("ORDERTYPE").toString())){
							ORDERTYPE="回购订单";
						}
						String  SNTYPE="";
						if("1".equals(map.get("SNTYPE").toString())){
							SNTYPE="小蓝牙";
						}else if("2".equals(map.get("SNTYPE").toString())){
							SNTYPE="大蓝牙";
						}
						String rowCoents[] = {
								map.get("ORDERID")==null?"":map.get("ORDERID").toString(),
								map.get("ORDERDAY")==null?"":map.get("ORDERDAY").toString(),
								ORDERMETHOD,		
								map.get("UNNO")==null?"":map.get("UNNO").toString(),
								map.get("PURCHASERNAME")==null?"":map.get("PURCHASERNAME").toString(),
								map.get("BRANDNAME")==null?"":map.get("BRANDNAME").toString(),
								ORDERTYPE,
								map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
								SNTYPE,
								map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
								map.get("MACHINENUM")==null?"":map.get("MACHINENUM").toString(),
								map.get("IMPORTNUM")==null?"":map.get("IMPORTNUM").toString(),
								map.get("PURCHASER")==null?"":map.get("PURCHASER").toString(),
								DETAILSTATUS,
								map.get("DETAILCDATE")==null?"":map.get("DETAILCDATE").toString()
								
						};
						cotents.add(rowCoents);
					}
				}
				String excelName = "发货管理表";
				String sheetName = "发货管理表";
				String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
				JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("发货管理导出成功");
			}
		} catch (Exception e) {
			log.error("发货管理导出异常：" + e);
			json.setSuccess(false);
			json.setMsg("发货管理导出");
			super.writeJson(json);
		}
	}
	
	
	/**
	 * 删除采购单明细
	 */
	public void deletePurchaseDetail(){
		JsonBean jsonBean = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				jsonBean.setSessionExpire(true);
			} else {
				Integer i = purchaseDetailService.deletePurchaseDetailById(purchaseDetailBean.getPdid(), ((UserBean)userSession));
				if(i==1){
					purchaseOrderService.saveNewPurchaseOrder(purchaseDetailBean.getPoid());
					jsonBean.setSuccess(true);
					jsonBean.setMsg("删除采购详情成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到采购详情，请确认后再试");
				}
			}
		} catch (Exception e) {
			log.error("查询采购单明细异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("删除采购详情失败");
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 查询机型名称
	 */
	public void listMachineModel(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				dgb = purchaseDetailService.queryMachineModel();
			}
		} catch (Exception e) {
			log.error("查询机型名称异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询配置
	 */
	public void listPurConfigure(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				String type = super.getRequest().getParameter("type");
				dgb = purchaseDetailService.listPurConfigure(type);
			}
		} catch (Exception e) {
			log.error("查询进销存配置异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 导出采购单及明细（代理）
	 */
	public void exportPurchase(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				purchaseDetailBean.setPutType("1");//入货管理（厂商单+代理单（退货））
				if(purchaseDetailBean.getDetailStatus()==null){
					purchaseDetailBean.setDetailStatus(6);
				}
				//1待提交;2订单待审;3厂商单待付款/代理单已审核;4发票待审;5审核通过;9已结款;10已全部核销
				purchaseDetailBean.setStatus("3,4,5,9,10");
				List<Map<String, Object>> list = purchaseDetailService.exportPurchase(purchaseDetailBean, ((UserBean)userSession));
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"订单号", "采购单日期", "大类", "供应商名称","品牌","订单类型","机型大类","机型","数量","已入数量","采购人", "状态","创建时间"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					if("1".equals(map.get("ORDERMETHOD").toString())){
						map.put("ORDERMETHOD","厂商单");
					}else if("2".equals(map.get("ORDERMETHOD").toString())){
						map.put("ORDERMETHOD","代理单");
					}
					if("1".equals(map.get("ORDERTYPE").toString())){
						map.put("ORDERTYPE","采购订单");
					}else if("2".equals(map.get("ORDERTYPE").toString())){
						map.put("ORDERTYPE","赠品订单");
					}else if("3".equals(map.get("ORDERTYPE").toString())){
						map.put("ORDERTYPE","回购订单");
					}
					if("1".equals(map.get("SNTYPE").toString())){
						map.put("SNTYPE","小蓝牙");
					}else if("2".equals(map.get("SNTYPE").toString())){
						map.put("SNTYPE","大蓝牙");
					}
					if("6".equals(map.get("DETAILSTATUS").toString())){
						map.put("DETAILSTATUS","未入库");
					}else if("7".equals(map.get("DETAILSTATUS").toString())){
						map.put("DETAILSTATUS","入库中");
					}else if("8".equals(map.get("DETAILSTATUS").toString())){
						map.put("DETAILSTATUS","已入库");
					}
					String []rowContents ={
							map.get("ORDERID")==null?"":map.get("ORDERID").toString(),	
							map.get("ORDERDAY")==null?"":map.get("ORDERDAY").toString(),
							map.get("ORDERMETHOD")==null?"":map.get("ORDERMETHOD").toString(),
							map.get("VENDORNAME")==null?"":map.get("VENDORNAME").toString(),
							map.get("BRANDNAME")==null?"":map.get("BRANDNAME").toString(),
							map.get("ORDERTYPE")==null?"":map.get("ORDERTYPE").toString(),
							map.get("SNTYPE")==null?"":map.get("SNTYPE").toString(),
							map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
							map.get("MACHINENUM")==null?"":map.get("MACHINENUM").toString(),
							map.get("IMPORTNUM")==null?"":map.get("IMPORTNUM").toString(),
							map.get("PURCHASER")==null?"":map.get("PURCHASER").toString(),
							map.get("DETAILSTATUS")==null?"":map.get("DETAILSTATUS").toString(),
							map.get("DETAILCDATE")==null?"":map.get("DETAILCDATE").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "入库资料.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出入库资料Excel成功");
			}
		} catch (Exception e) {
			log.error("导出入库资料：" + e);
		}
	}
	
	/**
	 * 导出订单统计
	 */
	public void exportPurchaseOrderAndDetailForTongJi(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				//1待提交;2订单待审;3厂商单待付款/代理单已审核;4发票待审;5审核通过;
				purchaseDetailBean.setStatuss("3,4,5");
				purchaseDetailBean.setDetailMinfo1(1);//统计
				List<Map<String, Object>> list = purchaseDetailService.exportPurchaseOrderAndDetailForTongJi(purchaseDetailBean, ((UserBean)userSession));
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"订单号", "采购日期", "大类(1-厂商单,2-代理单)", "供应商名称", "采购机构名称","品牌","类型(1-采购,2-赠品,3-回购,4-退货,5-换购)","机型","蓝牙类型(1-小蓝牙,2-大蓝牙)","返利类型","总数量", "总金额","单价", "已付款金额","付款状态(3-未付款,4-付款中,5-已结款)","已核销金额","已核销数量","发票状态(1-未核销，2-核销中,3-已核销)","明细金额","明细数量","已入出数量","库房状态(6-未完成,7-完成中,8-已完成)","创建时间"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					String []rowContents ={
							map.get("ORDERID")==null?"":map.get("ORDERID").toString(),	
							map.get("ORDERDAY")==null?"":map.get("ORDERDAY").toString(),
							map.get("ORDERMETHOD")==null?"":map.get("ORDERMETHOD").toString(),
							map.get("VENDORNAME")==null?"":map.get("VENDORNAME").toString(),
							map.get("PURCHASERNAME")==null?"":map.get("PURCHASERNAME").toString(),
							map.get("BRANDNAME")==null?"":map.get("BRANDNAME").toString(),	
							map.get("ORDERTYPE")==null?"":map.get("ORDERTYPE").toString(),
							map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
							map.get("SNTYPE")==null?"":map.get("SNTYPE").toString(),
							map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
							map.get("ORDERNUM")==null?"":map.get("ORDERNUM").toString(),
							map.get("ORDERAMT")==null?"":map.get("ORDERAMT").toString(),
							map.get("MACHINEPRICE")==null?"":map.get("MACHINEPRICE").toString(),
							map.get("ORDERPAYAMT")==null?"":map.get("ORDERPAYAMT").toString(),
							map.get("STATUS")==null?"":map.get("STATUS").toString(),
							map.get("WRITEOFFAMT")==null?"":map.get("WRITEOFFAMT").toString(),
							map.get("WRITEOFFNUM")==null?"":map.get("WRITEOFFNUM").toString(),
							map.get("WRITEOFFSTATUS")==null?"":map.get("WRITEOFFSTATUS").toString(),
							map.get("DETAILAMT")==null?"":map.get("DETAILAMT").toString(),
							map.get("MACHINENUM")==null?"":map.get("MACHINENUM").toString(),
							map.get("OUTNUM")==null?"":map.get("OUTNUM").toString(),
							map.get("DETAILSTATUS")==null?"":map.get("DETAILSTATUS").toString(),
							map.get("DETAILCDATE")==null?"":map.get("DETAILCDATE").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "订单统计资料.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出订单统计Excel成功");
			}
		} catch (Exception e) {
			log.error("导出订单统计：" + e);
		}
	}
	
	/**
	 * 修改采购单
	 */
	public void updatePurchaseOrder(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				//修改采购订单
				Integer i = purchaseDetailService.updatePurchaseDetail(purchaseDetailBean, ((UserBean)userSession));
				if(i==0){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到订单，请确认！");
					super.writeJson(jsonBean);
					return;
				}else if(i==2) {
					jsonBean.setSuccess(false);
					jsonBean.setMsg("数量不能低于已出库数量");
					super.writeJson(jsonBean);
					return;
				}
				//重新计算订单总额总数
				purchaseOrderService.saveNewPurchaseOrder(purchaseDetailBean.getPoid());
				jsonBean.setSuccess(true);
				jsonBean.setMsg("修改订单明细成功");
			} catch (Exception e) {
				log.error("修改订单明细异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("修改订单明细失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	

	/**
	 * 查询中心设备出库情况数据 
	 */
	public void queryCenterCheckout(){
		DataGridBean dgd = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
			} else {
				if(purchaseDetailBean.getDetailCdate()==null||"".equals(purchaseDetailBean.getDetailCdate())||
						purchaseDetailBean.getDetailCdateEnd()==null||"".equals(purchaseDetailBean.getDetailCdateEnd())){
					dgd = null;
				}else{	
					dgd = purchaseDetailService.queryCenterCheckout(purchaseDetailBean);
				}	
			}
		} catch (Exception e) {
			LOG.error("中心设备出库情况数据  error" + e);
		}
		super.writeJson(dgd);
	}
	
	/**
	 * 导出中心设备出库情况数据 
	 */
	public void exportCenterCheckout(){
		JsonBean json = new JsonBean();
		List<Map<String, Object>>list = null;
		List<String[]>cotents = new ArrayList<String[]>();
		String titles[] = {"中心","中心名称","销售","活动类型","出库总数"};
		cotents.add(titles);
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				list =  purchaseDetailService.exportCenterCheckout(purchaseDetailBean);
				if (list!=null&&list.size()>0) {
					for (Map<String, Object>map  : list) {
						String rowCoents[] = {
								map.get("YUNGYING")==null?"":map.get("YUNGYING").toString(),
								map.get("UNNAME")==null?"":map.get("UNNAME").toString(),		
								map.get("PURCHASER")==null?"":map.get("PURCHASER").toString(),
								map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
								map.get("ZCOUNT")==null?"":map.get("ZCOUNT").toString()
						};
						cotents.add(rowCoents);
					}
				}
				String excelName = "中心设备出库情况数据表";
				String sheetName = "中心设备出库情况数据";
				String [] cellFormatType = {"t","t","t","t","t"};
				JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("中心设备出库情况数据导出成功");
			}
		} catch (Exception e) {
			log.error("中心设备出库情况数据导出异常：" + e);
			json.setSuccess(false);
			json.setMsg("中心设备出库情况数据导出");
			super.writeJson(json);
		}
	}
	
	
	
	
}