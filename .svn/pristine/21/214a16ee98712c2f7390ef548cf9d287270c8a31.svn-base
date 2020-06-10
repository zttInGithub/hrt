package com.hrt.biz.bill.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.hrt.biz.bill.entity.model.PurchaseDetailModel;
import com.hrt.biz.bill.entity.model.PurchaseOrderModel;
import com.hrt.biz.bill.entity.pagebean.PurchaseRecordBean;
import com.hrt.biz.bill.service.IPurchaseRecordService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.hrt.util.UsePioOutExcel;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 来款记录
 */
public class PurchaseRecordAction extends BaseAction implements ModelDriven<PurchaseRecordBean>{
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(PurchaseRecordAction.class);
	private PurchaseRecordBean purchaseRecordBean = new PurchaseRecordBean();
	private IPurchaseRecordService purchaseRecordService;
	private File upload ;
	/**
	 * 来款导出
	 */
	public void exportPurchaseRecord(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String, Object>> list = purchaseRecordService.exportPurchaseRecord(purchaseRecordBean, ((UserBean)userSession));
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"订单号", "来款金额", "来款人姓名", "来款卡号", "来款日期","来款状态","来款方式", "代理机构号", "代理机构名称","登记时间","登记人","匹配时间","匹配人"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					if("1".equals(map.get("ARRAIVESTATUS").toString())){
						map.put("ARRAIVESTATUS","财务新增");
					}else if("2".equals(map.get("ARRAIVESTATUS").toString())){
						map.put("ARRAIVESTATUS","销售匹配");
					}else if("3".equals(map.get("ARRAIVESTATUS").toString())){
						map.put("ARRAIVESTATUS","财务确认");
					}else if("4".equals(map.get("ARRAIVESTATUS").toString())){
						map.put("ARRAIVESTATUS","退回");
					}
					String []rowContents ={
							map.get("ORDERID")==null?"":map.get("ORDERID").toString(),	
							map.get("ARRAIVEAMT")==null?"":map.get("ARRAIVEAMT").toString(),
							map.get("ARRAIVERNAME")==null?"":map.get("ARRAIVERNAME").toString(),
							map.get("ARRAIVECARD")==null?"":map.get("ARRAIVECARD").toString(),
							map.get("ARRAIVEDATE")==null?"":map.get("ARRAIVEDATE").toString(),
							map.get("ARRAIVESTATUS")==null?"":map.get("ARRAIVESTATUS").toString(),
							map.get("ARRAIVEWAY")==null?"":map.get("ARRAIVEWAY").toString(),
							map.get("UNNO")==null?"":map.get("UNNO").toString(),
							map.get("PURCHASERNAME")==null?"":map.get("PURCHASERNAME").toString(),
							map.get("RECORDCDATE")==null?"":map.get("RECORDCDATE").toString(),	
							map.get("RECORDCBY")==null?"":map.get("RECORDCBY").toString(),
							map.get("RECORDLMDATE")==null?"":map.get("RECORDLMDATE").toString(),
							map.get("RECORDLMBY")==null?"":map.get("RECORDLMBY").toString(),
						};
					excelList.add(rowContents);
					}
					String excelName = "来款记录.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出来款记录Excel成功");
			}
		} catch (Exception e) {
			log.error("导出来款记录：" + e);
		}
	}
	/**
	 * 来款导入
	 */
	public void addPurchaseRecordbyFile(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null)json.setSessionExpire(true);
		//把文件保存到服务器目录下
		String fileName = ServletActionContext.getRequest().getParameter("file10511Name");
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
				list = purchaseRecordService.addPurchaseRecordbyFile(xlsfile,fileName,((UserBean) userSession).getLoginName());
				if(list!=null&&list.size() > 0){
					String excelName= "导入失败记录";
					String[] title = {"来款金额","来款人姓名","来款卡号","来款日期","来款方式","提示"};
					List excellist = new ArrayList();
					excellist.add(title);
					for(int rowId = 0;rowId<list.size();rowId++){
						Map<String, String> order = list.get(rowId);
						String[] rowContents = {order.get("arraiveAmt"),order.get("arraiverName"),
								order.get("arraiveCard"),
								order.get("arraiveDate"),order.get("arraiveWay"),order.get("msg")};
						excellist.add(rowContents);
					}
					String[] cellFormatType = {"t","t","t","t","t","t"};
					UsePioOutExcel.writeExcel(excellist, 6, super.getResponse(), excelName, excelName+".xls", cellFormatType);					
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
	/**
	 * 查询主订单ID（通用）
	 */
	public void searchPurchaseOrderByOID(){
		String orderID = super.getRequest().getParameter("q");
		DataGridBean dgd = new DataGridBean();
		if (orderID!=null) {
			dgd=purchaseRecordService.searchPurchaseOrderByOID(orderID);
		}
		super.writeJson(dgd);
	}
	/**
	 * 查询来款通道
	 */
	public void listArraiveWay(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				dgb = purchaseRecordService.listArraiveWay();
			}
		} catch (Exception e) {
			log.error("查询来款通道异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 财务确认
	 */
	public void confirmPurchaseRecord(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				Integer record = purchaseRecordService.confirmPurchaseRecord(purchaseRecordBean,((UserBean)userSession));
				if (record>0) {
					jsonBean.setSuccess(true);
					jsonBean.setMsg("财务确认成功");
				} else {
					jsonBean.setSuccess(false);
					jsonBean.setMsg("财务确认失败，确认的金额大于订单待付款金额！");
				}
			} catch (Exception e) {
				log.error("财务确认异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("财务确认失败");
			}
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 删除来款
	 */
	public void deletePurchaseRecord(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				//退回来款
				purchaseRecordService.deletePurchaseRecord(purchaseRecordBean,((UserBean)userSession));
				jsonBean.setSuccess(true);
				jsonBean.setMsg("删除来款成功");
			} catch (Exception e) {
				log.error("删除来款异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("删除来款失败");
			}
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 退回来款
	 */
	public void returnPurchaseRecord(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				//退回来款
				purchaseRecordService.returnPurchaseRecord(purchaseRecordBean,((UserBean)userSession));
				jsonBean.setSuccess(true);
				jsonBean.setMsg("退回来款成功");
			} catch (Exception e) {
				log.error("退回来款异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("退回来款失败");
			}
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 来款匹配
	 */
	public void updatePurchaseRecord(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				//查询是否有这个订单				
				List<Map<String,Object>> list=purchaseRecordService.queryPurchaseOrder(purchaseRecordBean);
				if (list.size()>0) {
					String poid = ((BigDecimal)list.get(0).get("POID")).toString();
					String orderID = String.valueOf(list.get(0).get("ORDERID"));
					String unno = String.valueOf(list.get(0).get("UNNO"));
					String purchaserName = String.valueOf(list.get(0).get("PURCHASERNAME"));
					purchaseRecordService.updatePurchaseRecord(poid,orderID,unno,purchaserName,purchaseRecordBean, ((UserBean)userSession));
					jsonBean.setSuccess(true);
					jsonBean.setMsg("来款匹配成功");
				} else {
					jsonBean.setSuccess(false);
					jsonBean.setMsg("超过待付款金额");
				}

			} catch (Exception e) {
				log.error("来款匹配异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("来款匹配失败");
			}
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 新增来款
	 */
	public void addPurchaseRecord(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
			jsonBean.setSessionExpire(true);
		} else {
			try {
				purchaseRecordService.savePurchaseRecord(purchaseRecordBean, ((UserBean)userSession));
				jsonBean.setSuccess(true);
				jsonBean.setMsg("新增来款成功");
			} catch (Exception e) {
				log.error("新增来款异常：" + e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("新增来款失败");
			}
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 查询来款记录
	 */
	public void listPurchaseRecord(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
			} else {
				dgb = purchaseRecordService.queryPurchaseRecord(purchaseRecordBean, ((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("查询来款记录异常：" + e);
		}
		super.writeJson(dgb);
	
	}

	@Override
	public PurchaseRecordBean getModel() {
		
		return purchaseRecordBean;
	}

	public PurchaseRecordBean getPurchaseRecordBean() {
		return purchaseRecordBean;
	}

	public IPurchaseRecordService getPurchaseRecordService() {
		return purchaseRecordService;
	}

	public void setPurchaseRecordBean(PurchaseRecordBean purchaseRecordBean) {
		this.purchaseRecordBean = purchaseRecordBean;
	}

	public void setPurchaseRecordService(IPurchaseRecordService purchaseRecordService) {
		this.purchaseRecordService = purchaseRecordService;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	
}
