package com.hrt.biz.bill.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.model.PurchaseDetailModel;
import com.hrt.biz.bill.entity.model.PurchaseOrderModel;
import com.hrt.biz.bill.entity.pagebean.PurchaseOrderBean;
import com.hrt.biz.bill.service.IPurchaseDetailService;
import com.hrt.biz.bill.service.IPurchaseOrderService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.opensymphony.xwork2.ModelDriven;

public class PurchaseOrderAction extends BaseAction implements ModelDriven<PurchaseOrderBean>{

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(PurchaseOrderAction.class);
	private PurchaseOrderBean purchaseOrderBean = new PurchaseOrderBean();
	private IPurchaseOrderService purchaseOrderService;
	private IPurchaseDetailService purchaseDetailService;
	@Override
	public PurchaseOrderBean getModel() {
		return purchaseOrderBean;
	}
	
	public IPurchaseDetailService getPurchaseDetailService() {
		return purchaseDetailService;
	}

	public void setPurchaseDetailService(IPurchaseDetailService purchaseDetailService) {
		this.purchaseDetailService = purchaseDetailService;
	}

	public IPurchaseOrderService getPurchaseOrderService() {
		return purchaseOrderService;
	}

	public void setPurchaseOrderService(IPurchaseOrderService purchaseOrderService) {
		this.purchaseOrderService = purchaseOrderService;
	}

	/**
	 * 查询采购单
	 */
	public void listPurchaseOrders(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
		} else {
			try {
				
				//1--厂商单  2--代理单
				purchaseOrderBean.setOrderMethod(1);
				dgb = purchaseOrderService.queryPurchaseOrders(purchaseOrderBean, ((UserBean)userSession));
			} catch (Exception e) {
				log.error("分页查询采购单信息异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	/**
	 * 查询采购单(全)
	 */
	public void listPurchaseOrdersAll(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
		} else {
			try {
//				if (purchaseOrderBean.getOrderID()!=null&&!"".equals(purchaseOrderBean.getOrderID())) {
					purchaseOrderBean.setStatus("3,4,5");//查看已结款FOR陈如玉
					dgb = purchaseOrderService.queryPurchaseOrders(purchaseOrderBean, ((UserBean)userSession));
				
//				}
			} catch (Exception e) {
				log.error("分页查询采购单信息异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	/**
	 * 查询可取消订单（销售订单）
	 */
	public void listCancelPurchaseOrder(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
		} else {
			try {
				if((!"".equals(purchaseOrderBean.getOrderID())&&purchaseOrderBean.getOrderID()!=null)||(!"".equals(purchaseOrderBean.getUnno())&&purchaseOrderBean.getUnno()!=null)){
					purchaseOrderBean.setOrderMethod(2);
					dgb = purchaseOrderService.listCancelPurchaseOrder(purchaseOrderBean, ((UserBean)userSession));
				}else{}
			} catch (Exception e) {
				log.error("分页查询可取消订单异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	/**
	 * 新增采购单
	 */
	public void addPurchaseOrder(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				//订单号已存在
				boolean flag = purchaseOrderService.findOrderID(purchaseOrderBean.getOrderID());
				if(flag==false){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("订单号已存在，请重新添加");
					super.writeJson(jsonBean);
					return;
				}
				String[] values = getRequest().getParameterValues("ADDNEWPD");
				//计算采购总额总数
				Integer orderNum = 0;
				Double orderAmt = 0.0;
				if(values != null && values.length > 0){
					for (int i = 0; i < values.length; i++) {
						//"+sntype+"#separate#"+orderType+"#separate#"+brandName+"#separate#"+machineModel+"#separate#"+machinePrice+"#separate#"+machineNum+"#separate#"+samt1+"
						String[] param = values[i].split("#separate#");
						orderNum += Integer.parseInt(param[5]);
						orderAmt += Double.parseDouble(param[6]);
					}
				}
				purchaseOrderBean.setOrderNum(orderNum);
				purchaseOrderBean.setOrderAmt(orderAmt);
				//保存新增采购单
				PurchaseOrderModel purchaseOrderModel = purchaseOrderService.savePurchaseOrder(purchaseOrderBean, ((UserBean)userSession));
				if(purchaseOrderModel==null){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查找到机构，请确认后再试");
					super.writeJson(jsonBean);
					return;
				}
				if(values != null && values.length > 0){
					for (int i = 0; i < values.length; i++) {
						//"+sntype+"#separate#"+orderType+"#separate#"+brandName+"#separate#"+machineModel+"#separate#"+machinePrice+"#separate#"+machineNum+"#separate#"+samt1+"
						String[] param = values[i].split("#separate#");
						PurchaseDetailModel purchaseDetailModel = new PurchaseDetailModel();
						purchaseDetailModel.setSnType(Integer.parseInt(param[0]));
						purchaseDetailModel.setOrderType(param[1]);
						purchaseDetailModel.setBrandName(param[2]);
						purchaseDetailModel.setMachineModel(param[3]);
						purchaseDetailModel.setMachinePrice(Double.parseDouble(param[4]));
						purchaseDetailModel.setMachineNum(Integer.parseInt(param[5]));
						purchaseDetailModel.setDetailAmt(Double.parseDouble(param[6]));
						if(purchaseOrderModel.getOrderMethod()==2){
							purchaseDetailModel.setRate(Double.parseDouble(param[7])/100);
							purchaseDetailModel.setScanRate(Double.parseDouble(param[8])/100);
							purchaseDetailModel.setSecondRate(Double.parseDouble(param[9]));
							purchaseDetailModel.setRebateType(param[10]);
							if(param.length>=12 && StringUtils.isNotEmpty(param[11])) {
								if(param[11]!=null) {
									purchaseDetailModel.setScanRateUp(Double.parseDouble(param[11]) / 100);
								}
							}
							if(param.length>=13) {
								if(param[12]!=null && StringUtils.isNotEmpty(param[12])) {
									purchaseDetailModel.setHuaBeiRate(Double.parseDouble(param[12]) / 100);
								}
							}
							if(param.length>=14) {
								if(param[13]!=null && StringUtils.isNotEmpty(param[13])) {
									purchaseDetailModel.setDepositAmt(param[13]);
								}
							}
						}
						purchaseDetailModel.setDetailOrderID(purchaseOrderModel.getOrderID());
						purchaseDetailModel.setPoid(purchaseOrderModel.getPoid());
						//保存采购明细
						purchaseDetailService.savePurchaseDetail(purchaseDetailModel,((UserBean)userSession));
					}
				}
				jsonBean.setSuccess(true);
				jsonBean.setMsg("新增采购订单成功");
			} catch (Exception e) {
				log.error("新增采购订单异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("新增采购订单失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 删除采购单
	 */
	public void deletePurchaseOrder(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				//删除采购订单
				Integer i = purchaseOrderService.deletePurchaseOrder(purchaseOrderBean.getPoid(), ((UserBean)userSession));
				if(i==1){
					//删除采购明细
					purchaseDetailService.deletePurchaseDetail(purchaseOrderBean.getPoid(), ((UserBean)userSession));
					jsonBean.setSuccess(true);
					jsonBean.setMsg("删除采购单成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到采购单，请确认后再试");
				}
			} catch (Exception e) {
				log.error("删除采购单异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("删除采购单失败");
			}
		}
		super.writeJson(jsonBean);
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
				//采购详情
				String[] values = getRequest().getParameterValues("ADDNEWPD");
				//修改采购订单
				PurchaseOrderModel purchaseOrderModel = purchaseOrderService.updatePurchaseOrder(purchaseOrderBean, ((UserBean)userSession));
				if(purchaseOrderModel==null){
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查找到机构，请确认后再试");
					super.writeJson(jsonBean);
					return;
				}
				if(values != null && values.length > 0){
					for (int i = 0; i < values.length; i++) {
						//"+sntype+"#separate#"+orderType+"#separate#"+brandName+"#separate#"+machineModel+"#separate#"+machinePrice+"#separate#"+machineNum+"#separate#"+samt1+"
						String[] param = values[i].split("#separate#");
						PurchaseDetailModel purchaseDetailModel = new PurchaseDetailModel();
						purchaseDetailModel.setSnType(Integer.parseInt(param[0]));
						purchaseDetailModel.setOrderType(param[1]);
						purchaseDetailModel.setBrandName(param[2]);
						purchaseDetailModel.setMachineModel(param[3]);
						purchaseDetailModel.setMachinePrice(Double.parseDouble(param[4]));
						purchaseDetailModel.setMachineNum(Integer.parseInt(param[5]));
						purchaseDetailModel.setDetailAmt(Double.parseDouble(param[6]));
						if(purchaseOrderModel.getOrderMethod()==2){
							purchaseDetailModel.setRate(Double.parseDouble(param[7])/100);
							purchaseDetailModel.setScanRate(Double.parseDouble(param[8])/100);
							purchaseDetailModel.setSecondRate(Double.parseDouble(param[9]));
							purchaseDetailModel.setRebateType(param[10]);
                            if(param.length>=12) {
                                if(param[11]!=null && StringUtils.isNotEmpty(param[11])) {
                                    purchaseDetailModel.setScanRateUp(Double.parseDouble(param[11]) / 100);
                                }
                            }
                            if(param.length>=13 && StringUtils.isNotEmpty(param[12])) {
                                if(param[12]!=null) {
                                    purchaseDetailModel.setHuaBeiRate(Double.parseDouble(param[12]) / 100);
                                }
                            }
							if(param.length>=14) {
								if(param[13]!=null && StringUtils.isNotEmpty(param[13])) {
									purchaseDetailModel.setDepositAmt(param[13]);
								}
							}
						}
						purchaseDetailModel.setDetailOrderID(purchaseOrderBean.getOrderID());
						purchaseDetailModel.setPoid(purchaseOrderBean.getPoid());
						//将新增的明细保存
						purchaseDetailService.savePurchaseDetail(purchaseDetailModel,((UserBean)userSession));
					}
				}
				//重新计算订单总额总数
				purchaseOrderService.saveNewPurchaseOrder(purchaseOrderBean.getPoid());
				jsonBean.setSuccess(true);
				jsonBean.setMsg("修改采购订单成功");
			} catch (Exception e) {
				log.error("修改采购订单异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("修改采购订单失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 变更订单为可修改状态
	 */
	public void updatePurchaseOrderStatusToEdit() {
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				//提交审核采购订单
				Integer i = purchaseOrderService.updatePurchaseOrderStatusToEdit(purchaseOrderBean.getPoid(), ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("变更订单为可修改成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到待审核采购单，请确认后再试");
				}
			} catch (Exception e) {
				log.error("变更订单为可修改异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("变更订单为可修改失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 提交审核(已审核的订单再次变更为可修改之后提交)
	 */
	public void submitEditPurchaseOrder(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				//提交审核采购订单
				Integer i = purchaseOrderService.updateEditPurchaseOrder(purchaseOrderBean.getPoid(), ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("提交审成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到待审核采购单，请确认后再试");
				}
			} catch (Exception e) {
				log.error("提交审核异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("提交审核失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 提交审核采购单
	 */
	public void submitPurchaseOrder(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				//提交审核采购订单
				Integer i = purchaseOrderService.updatePurchaseOrderStatus(purchaseOrderBean.getPoid(), ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("提交审核采购单成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("未查询到待审核采购单，请确认后再试");
				}
			} catch (Exception e) {
				log.error("提交审核采购单异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("提交审核采购单失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 查询审核采购单
	 */
	public void listPurchaseOrderWJoin(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
		} else {
			try {
				dgb = purchaseOrderService.queryPurchaseOrderWJoin(purchaseOrderBean, ((UserBean)userSession));
			} catch (Exception e) {
				log.error("分页查询审核采购单信息异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 审核通过采购单
	 */
	public void updatePurchaseOrderY(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				Integer i = purchaseOrderService.updatePurchaseOrderY(purchaseOrderBean, ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("审核通过采购单成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("审核通过采购单失败");
				}
			} catch (Exception e) {
				log.error("审核通过采购单异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("审核通过采购单失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 审核退回采购单
	 */
	public void updatePurchaseOrderK(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				Integer i = purchaseOrderService.updatePurchaseOrderK(purchaseOrderBean, ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("审核退回采购单成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("审核退回采购单失败");
				}
			} catch (Exception e) {
				log.error("审核退回采购单异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("审核退回采购单失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 查询采购单(代理)
	 */
	public void listPurchaseOrdersM(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
		} else {
			try {
				//1--厂商单  2--代理单
				purchaseOrderBean.setOrderMethod(2);
				dgb = purchaseOrderService.queryPurchaseOrders(purchaseOrderBean, ((UserBean)userSession));
			} catch (Exception e) {
				log.error("分页查询采购单信息异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 导出采购单及明细（代理）
	 */
	public void exportPurchaseWJoin(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String, Object>> list = purchaseOrderService.exportPurchaseOrderWJoin(purchaseOrderBean, ((UserBean)userSession));
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"订单号", "订单日期", "大类", "供应商名称", "采购机构号","采购机构名称","联系人", "联系电话", "联系邮箱","品牌","订单类型","机型","机型大类","返利类型","刷卡费率","扫码费率","提现手续费","单价","数量","金额", "总数量", "总金额","采购人", "状态","审核人","审核时间"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					if("1".equals(map.get("ORDERMETHOD").toString())){
						map.put("ORDERMETHOD","厂商单");
					}else if("2".equals(map.get("ORDERMETHOD").toString())){
						map.put("ORDERMETHOD","代理单");
					}
					if("1".equals(map.get("STATUS").toString())){
						map.put("STATUS","待提交");
					}else if("2".equals(map.get("STATUS").toString())){
						map.put("STATUS","订单待审");
					}else if("3".equals(map.get("STATUS").toString())){
						map.put("STATUS","已审核");
					}else if("4".equals(map.get("STATUS").toString())){
						map.put("STATUS","发票待审");
					}else if("5".equals(map.get("STATUS").toString())){
						map.put("STATUS","审核通过");
					}else if("9".equals(map.get("STATUS").toString())){
						map.put("STATUS","已结款");
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
							map.get("BRANDNAME")==null?"":map.get("BRANDNAME").toString(),	
							map.get("ORDERTYPE")==null?"":map.get("ORDERTYPE").toString(),
							map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
							map.get("SNTYPE")==null?"":map.get("SNTYPE").toString(),
							map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
							map.get("RATE")==null?"":map.get("RATE").toString(),
							map.get("SCANRATE")==null?"":map.get("SCANRATE").toString(),
							map.get("SECONDRATE")==null?"":map.get("SECONDRATE").toString(),
							map.get("MACHINEPRICE")==null?"":map.get("MACHINEPRICE").toString(),
							map.get("MACHINENUM")==null?"":map.get("MACHINENUM").toString(),
							map.get("DETAILAMT")==null?"":map.get("DETAILAMT").toString(),
							map.get("ORDERNUM")==null?"":map.get("ORDERNUM").toString(),
							map.get("ORDERAMT")==null?"":map.get("ORDERAMT").toString(),
							map.get("PURCHASER")==null?"":map.get("PURCHASER").toString(),
							map.get("STATUS")==null?"":map.get("STATUS").toString(),
							map.get("APPROVER")==null?"":map.get("APPROVER").toString(),
							map.get("APPROVEDATE")==null?"":map.get("APPROVEDATE").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "采购审核资料.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出采购审核Excel成功");
			}
		} catch (Exception e) {
			log.error("导出采购审核：" + e);
		}
	}
	/**
	 * 取消订单
	 */
	public void updatePurchaseOrderCancel(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				//判断是否还有未发货订单 0-没有
				Integer i = purchaseOrderService.queryPurchaseOrderCancel(purchaseOrderBean, ((UserBean)userSession));
				if(i==0){
					//修改订单明细数量，金额，状态
					purchaseDetailService.updatePurchaseDetailCancel(purchaseOrderBean, ((UserBean)userSession));
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("此订单存在未发货，请发货后再试");
					super.writeJson(jsonBean);
					return;
				}
				//重新计算订单总量
				purchaseOrderService.saveNewPurchaseOrder(purchaseOrderBean.getPoid());
				jsonBean.setSuccess(true);
				jsonBean.setMsg("取消订单成功");
			} catch (Exception e) {
				log.error("取消订单异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("取消订单失败");
			}
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 手刷POS应收账款统计
	 */
	public void queryPOSAccounts(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
		} else {
			try {
				if(purchaseOrderBean.getCdate()!=null&&!"".equals(purchaseOrderBean.getCdate())&&purchaseOrderBean.getCdateEnd()!=null&&!"".equals(purchaseOrderBean.getCdateEnd())){
					dgb = purchaseOrderService.queryPOSAccounts(purchaseOrderBean, ((UserBean)userSession));
				}
			} catch (Exception e) {
				log.error("分页查询手刷POS应收账款统计异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	/**
	 * 导出手刷POS应收账款统计
	 */
	public void exportPOSAccounts(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String, Object>> list = purchaseOrderService.exportPOSAccounts(purchaseOrderBean, ((UserBean)userSession));
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"机构号", "机构名称", "期初应收", "销售出库数量", "销售金额","回款金额","已扣分润", "激活奖励抵扣", "回购款及其他","期末应收账款"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					String []rowContents ={
							map.get("UNNO")==null?"":map.get("UNNO").toString(),	
							map.get("PURCHASERNAME")==null?"":map.get("PURCHASERNAME").toString(),
							map.get("ORDERAMT")==null?"":map.get("ORDERAMT").toString(),
							map.get("NUMS")==null?"":map.get("NUMS").toString(),
							map.get("AMT")==null?"":map.get("AMT").toString(),
							map.get("REMITAMT")==null?"":map.get("REMITAMT").toString(),
							map.get("SHAREAMT")==null?"":map.get("SHAREAMT").toString(),
							map.get("ACTIVEAMT")==null?"":map.get("ACTIVEAMT").toString(),
							map.get("BACKAMT")==null?"":map.get("BACKAMT").toString(),
							map.get("SHOULDAMT")==null?"":map.get("SHOULDAMT").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "手刷POS应收账款统计.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出手刷POS应收账款统计Excel成功");
			}
		} catch (Exception e) {
			log.error("导出手刷POS应收账款统计：" + e);
		}
	}
	/**
	 * 手刷POS损益情况统计
	 */
	public void queryPOSProfitAndLoss(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
		} else {
			try {
				if(purchaseOrderBean.getCdate()!=null&&!"".equals(purchaseOrderBean.getCdate())&&purchaseOrderBean.getCdateEnd()!=null&&!"".equals(purchaseOrderBean.getCdateEnd())){
					dgb = purchaseOrderService.queryPOSProfitAndLoss(purchaseOrderBean, ((UserBean)userSession));
				}
			} catch (Exception e) {
				log.error("分页查询手刷POS应收账款统计异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	/**
	 * 导出手刷POS损益情况统计
	 */
	public void exportPOSProfitAndLoss(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String, Object>> list = purchaseOrderService.exportPOSProfitAndLoss(purchaseOrderBean, ((UserBean)userSession));
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"机构号", "机构名称", "销售金额", "成本金额", "赠品数量","赠品金额","激活返现金额", "亏损"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					String []rowContents ={
							map.get("UNNO")==null?"":map.get("UNNO").toString(),	
							map.get("PURCHASERNAME")==null?"":map.get("PURCHASERNAME").toString(),
							map.get("OUTAMT")==null?"":map.get("OUTAMT").toString(),
							map.get("INAMT")==null?"":map.get("INAMT").toString(),
							map.get("MACNUM")==null?"":map.get("MACNUM").toString(),
							map.get("GIVEAMT")==null?"":map.get("GIVEAMT").toString(),
							map.get("REBATEAMT")==null?"":map.get("REBATEAMT").toString(),
							map.get("LOSSAMT")==null?"":map.get("LOSSAMT").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "手刷POS损益情况统计.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出手刷POS损益情况统计Excel成功");
			}
		} catch (Exception e) {
			log.error("导出手刷POS损益情况统计：" + e);
		}
	}
	/**
	 * 手刷POS综合收益
	 */
	public void queryPOSIncome(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
		} else {
			try {
				if(purchaseOrderBean.getYear()!=null&&!"".equals(purchaseOrderBean.getYear())){
					dgb = purchaseOrderService.queryPOSIncome(purchaseOrderBean, ((UserBean)userSession));
				}
			} catch (Exception e) {
				log.error("分页查询手刷POS应收账款统计异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	/**
	 * 导出手刷POS综合收益
	 */
	public void exportPOSIncome(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String, Object>> list = purchaseOrderService.exportPOSIncome(purchaseOrderBean, ((UserBean)userSession));
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"月份", "销售数量", "销售金额", "成本金额", "亏损金额", "赠品数量","赠品金额","返利金额", "亏损合计","回款数","回购款及差价","扣分润数","其他扣款","激活奖励抵欠款","每月累计应收数"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					String []rowContents ={
							map.get("MONTH")==null?"":map.get("MONTH").toString(),	
							map.get("MACNUM")==null?"":map.get("MACNUM").toString(),
							map.get("OUTAMT")==null?"":map.get("OUTAMT").toString(),
							map.get("INAMT")==null?"":map.get("INAMT").toString(),
							map.get("LOSSAMT")==null?"":map.get("LOSSAMT").toString(),
							map.get("GIVENUM")==null?"":map.get("GIVENUM").toString(),
							map.get("GIVEAMT")==null?"":map.get("GIVEAMT").toString(),
							map.get("REBATEAMT")==null?"":map.get("REBATEAMT").toString(),	
							map.get("ALLLOSSAMT")==null?"":map.get("ALLLOSSAMT").toString(),
							map.get("INCOME")==null?"":map.get("INCOME").toString(),
							map.get("BACKAMT")==null?"":map.get("BACKAMT").toString(),
							map.get("PROFIT")==null?"":map.get("PROFIT").toString(),
							map.get("OTHERAMT")==null?"":map.get("OTHERAMT").toString(),
							map.get("ACHAMT")==null?"":map.get("ACHAMT").toString(),
							map.get("TOTALAMT")==null?"":map.get("TOTALAMT").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "手刷POS综合收益.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出手刷POS综合收益成功");
			}
		} catch (Exception e) {
			log.error("导出手刷POS综合收益：" + e);
		}
	}
	
	/**
	 * 审核通过采购单(再次修改)
	 */
	public void updatePurchaseOrderAgainY(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				Integer i = purchaseOrderService.updatePurchaseOrderAgainY(purchaseOrderBean, ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("审核通过采购单成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("审核通过采购单失败");
				}
			} catch (Exception e) {
				log.error("审核通过采购单(再次修改)异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("审核通过采购单失败");
			}
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 审核退回采购单(再次修改)
	 */
	public void updatePurchaseOrderAgainK(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				Integer i = purchaseOrderService.updatePurchaseOrderAgainK(purchaseOrderBean, ((UserBean)userSession));
				if(i==1){
					jsonBean.setSuccess(true);
					jsonBean.setMsg("审核退回采购单成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("审核退回采购单失败");
				}
			} catch (Exception e) {
				log.error("审核退回采购单(再次修改)异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("审核退回采购单失败");
			}
		}
		super.writeJson(jsonBean);
	}

    /**
     * 获取活动下的押金列表
     */
	public void listDepositAmtByPurConfigure(){
		DataGridBean dataGridBean=new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
		} else {
			try {
				String remarks = super.getRequest().getParameter("remarks");
				purchaseOrderBean.setRemarks(remarks);
				dataGridBean = purchaseOrderService.listDepositAmtByPurConfigure(purchaseOrderBean, ((UserBean)userSession));
			} catch (Exception e) {
				log.error("活动押金配置查询异常：",e);
			}
		}
		super.writeJson(dataGridBean);
	}
}