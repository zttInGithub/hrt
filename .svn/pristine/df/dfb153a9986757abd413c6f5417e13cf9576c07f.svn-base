package com.hrt.biz.check.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.check.entity.pagebean.ReceiptsDataBean;
import com.hrt.biz.check.service.CheckReceiptsOpreationService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.UsePioOutExcel;
import com.opensymphony.xwork2.ModelDriven;

public class CheckReceiptsOpreationAction extends BaseAction implements ModelDriven<ReceiptsDataBean>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(CheckReceiptsOpreationAction.class);
	
	private CheckReceiptsOpreationService checkReceiptsOpreationService;
	
	private ReceiptsDataBean receiptsDataBean =new ReceiptsDataBean();
	
	public CheckReceiptsOpreationService getCheckReceiptsOpreationService() {
		return checkReceiptsOpreationService;
	}

	public void setCheckReceiptsOpreationService(
			CheckReceiptsOpreationService checkReceiptsOpreationService) {
		this.checkReceiptsOpreationService = checkReceiptsOpreationService;
	}

	@Override
	public ReceiptsDataBean getModel() {
		return receiptsDataBean;
	}
	
	
	/*
	 * 小票审核list
	 * 
	 */
	public void queryReceiptsAuditList(){
		DataGridBean dgb = new DataGridBean();
		if((!"".equals(receiptsDataBean.getTxnday()) && receiptsDataBean.getTxnday()!=null) && (!"".equals(receiptsDataBean.getTxnday1()) && receiptsDataBean.getTxnday1()!=null) ){
			try {
				dgb=checkReceiptsOpreationService.queryReceiptsAuditList(receiptsDataBean);
			} catch (Exception e) {
				log.error("查询小票审核列表异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	/*
	 * 读取小票图片
	 */
	
	public void queryReceiptsImageShow() throws IOException{   
		HttpServletResponse response= super.getResponse();
		OutputStream os = null;  
		String path=checkReceiptsOpreationService.queryUploadPath();
		String imagePath=checkReceiptsOpreationService.queryImagePath(receiptsDataBean.getPkid(),0);
		String aa = super.getRequest().getParameter("timestamp");
        BufferedInputStream bis = null; 
        try{
          FileInputStream fileInputStream = new FileInputStream(new File(path+imagePath)); 
              bis = new BufferedInputStream(fileInputStream);  
                byte[] buffer = new byte[512];  
                response.setCharacterEncoding("UTF-8");  
                        //不同类型的文件对应不同的MIME类型  
                response.setContentType("image/*");  
                        //文件以流的方式发送到客户端浏览器
                response.setContentLength(bis.available());  
                  
                os = response.getOutputStream();  
                int n;  
                while ((n = bis.read(buffer)) != -1) {  
                  os.write(buffer, 0, n);  
                }
                fileInputStream.close();
                bis.close();
                os.flush();  
                os.close();
        }catch(Exception e){
        	
        }
	}
	
	
	/*
	 * 读取二维码图片
	 */
	
	public void queryQrCodeImageShow() throws IOException{   
		HttpServletResponse response= super.getResponse();
		OutputStream os = null;  
		String mid = super.getRequest().getParameter("mid");
		String path=File.separator+"u01"+File.separator+"hrtwork"+File.separator+"QRCODE"+File.separator+mid+".jpg";
        //String path="D:\\864100045113771.jpg";
		BufferedInputStream bis = null; 
        try{
          FileInputStream fileInputStream = new FileInputStream(new File(path)); 
              bis = new BufferedInputStream(fileInputStream);  
                byte[] buffer = new byte[512];  
                response.setCharacterEncoding("UTF-8");  
                        //不同类型的文件对应不同的MIME类型  
                response.setContentType("image/*");  
                        //文件以流的方式发送到客户端浏览器
                response.setContentLength(bis.available());  
                  
                os = response.getOutputStream();  
                int n;  
                while ((n = bis.read(buffer)) != -1) {  
                  os.write(buffer, 0, n);  
                }
                fileInputStream.close();
                bis.close();
                os.flush();  
                os.close();
        }catch(Exception e){
        	
        }
	}

	public void auditReceiptsYes(){
		JsonBean json = new JsonBean();
		String mid = super.getRequest().getParameter("mid");
		String money = super.getRequest().getParameter("money");
		try {
			checkReceiptsOpreationService.updateIfSettleFlagYes(receiptsDataBean.getPkid(),mid,money);
			json.setSuccess(true);
			json.setMsg("审评成功！");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("小票审核异常！");
		}
		super.writeJson(json);
	}
	
	public void auditReceiptsNo(){
		JsonBean json = new JsonBean();
		try {
			checkReceiptsOpreationService.updateIfSettleFlagNo(receiptsDataBean);
			json.setSuccess(true);
			json.setMsg("审核退回成功！");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("小票审核异常！");
		}
		super.writeJson(json);
	}
	
	public void exportAuditReceiptsNoMerchantInfo()throws IOException{
		if(!"".equals(receiptsDataBean.getTxnday()) && receiptsDataBean.getTxnday()!=null ){
			List<Map<String,String>> list=checkReceiptsOpreationService.queryAuditReceiptsNoMerchantInfo(receiptsDataBean);
			String[] titles ={"商户编号","商户名称"};
			List excelList = new ArrayList();
			excelList.add(titles);
			for (int rowId = 0; rowId < list.size(); rowId++) {
				
				Map order = list.get(rowId);
				
				String[] rowContents = { 
						 order.get("MID").toString(),
						 order.get("RNAME").toString(),
							};
				excelList.add(rowContents);
			}  
			String excelName="调单不合规商户";
			String sheetName="调单不合规商户";
			String[] cellFormatType = {"t","t"};
			UsePioOutExcel.writeExcel(excelList, 2, super.getResponse(), sheetName,excelName + ".xls", cellFormatType); // 调用导出方法
		}
	}
	
	//*******************************风控交易卡片审核************************************************************
	/**
	 * 接收MID   查询实时交易表        插入小票审核表   设置RISKTYPE为 1
	 */
	public void receiveMID(){
		JsonBean json = new JsonBean();
		String mid = super.getRequest().getParameter("MID");
		String cashmode =super.getRequest().getParameter("CASHMODE");
		if(mid !=null && !"".equals(mid)){
			boolean flag=checkReceiptsOpreationService.insertBlockTradeDetail(mid,cashmode);
			if(flag){
				json.setSuccess(true);
				json.setMsg("操作成功!");
			}else{
				json.setSuccess(false);
				json.setMsg("报单系统无此交易!");
			}
		}else{
			json.setSuccess(false);
			json.setMsg("接收MID为空!");
		}
		super.writeJson(json);
	}
	
	/*
	 * 风控交易卡片审核list
	 * 
	 */
	public void queryTransReceiptsAuditList(){
		DataGridBean dgb = new DataGridBean();
		if((!"".equals(receiptsDataBean.getTxnday()) && receiptsDataBean.getTxnday()!=null) && (!"".equals(receiptsDataBean.getTxnday1()) && receiptsDataBean.getTxnday1()!=null) ){
			try {
				dgb=checkReceiptsOpreationService.queryTransReceiptsAuditList(receiptsDataBean);
			} catch (Exception e) {
				log.error("查询风控交易卡片审核列表异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	//导出所有风控不合规商户
	public void exportRiskAuditReceiptsNoMerchantInfo()throws IOException{
		if(!"".equals(receiptsDataBean.getTxnday()) && receiptsDataBean.getTxnday()!=null ){
			List<Map<String,String>> list=checkReceiptsOpreationService.queryRiskAuditReceiptsNoMerchantInfo(receiptsDataBean);
			String[] titles ={"商户编号","商户名称"};
			List excelList = new ArrayList();
			excelList.add(titles);
			for (int rowId = 0; rowId < list.size(); rowId++) {
				
				Map order = list.get(rowId);
				
				String[] rowContents = { 
						 order.get("MID").toString(),
						 order.get("RNAME").toString(),
							};
				excelList.add(rowContents);
			}  
			String excelName="调单风控不合规商户";
			String sheetName="调单风控不合规商户";
			String[] cellFormatType = {"t","t"};
			UsePioOutExcel.writeExcel(excelList, 2, super.getResponse(), sheetName,excelName + ".xls", cellFormatType); // 调用导出方法
		}
	}
	
	//导出所有风控审核通过商户
	public void exportRiskAuditReceiptsMerchantInfo()throws IOException{
		if(!"".equals(receiptsDataBean.getTxnday()) && receiptsDataBean.getTxnday()!=null ){
			List<Map<String,String>> list=checkReceiptsOpreationService.queryRiskAuditReceiptsMerchantInfo(receiptsDataBean);
			String[] titles ={"商户编号","商户名称","审核日期"};
			List excelList = new ArrayList();
			excelList.add(titles);
			for (int rowId = 0; rowId < list.size(); rowId++) {
				
				Map order = list.get(rowId);
				
				String[] rowContents = { 
						 order.get("MID").toString(),
						 order.get("RNAME").toString(),
						 order.get("RISKDAY").toString(),
							};
				excelList.add(rowContents);
			}  
			String excelName="调单风控审批通过商户";
			String sheetName="调单风控审批通过商户";
			String[] cellFormatType = {"t","t","t"};
			UsePioOutExcel.writeExcel(excelList, 3, super.getResponse(), sheetName,excelName + ".xls", cellFormatType); // 调用导出方法
		}
	}
	
	/**
	 * 勾选导出
	 * @throws IOException 
	 */
	public void exportRiskTransCardInfo() throws IOException{
		String ids=getRequest().getParameter("ids");
		if(!"".equals(receiptsDataBean.getTxnday()) && receiptsDataBean.getTxnday()!=null ){
			List<Map<String,String>> list=checkReceiptsOpreationService.queryAuditReceiptsNoMerchantInfo(receiptsDataBean,ids);
			String[] titles ={"商户编号","商户名称"};
			List excelList = new ArrayList();
			excelList.add(titles);
			for (int rowId = 0; rowId < list.size(); rowId++) {
				
				Map order = list.get(rowId);
				
				String[] rowContents = { 
						 order.get("MID").toString(),
						 order.get("RNAME").toString(),
							};
				excelList.add(rowContents);
			}  
			String excelName="风控交易调单不合规商户";
			String sheetName="风控交易调单不合规商户";
			String[] cellFormatType = {"t","t"};
			UsePioOutExcel.writeExcel(excelList, 2, super.getResponse(), sheetName,excelName + ".xls", cellFormatType); // 调用导出方法
		}
	}
	
	/*
	 * 读取风控交易图片
	 */
	
	public void queryRiskReceiptsImageShow() throws IOException{   
		HttpServletResponse response= super.getResponse();
		OutputStream os = null;  
		String path=checkReceiptsOpreationService.queryRiskUploadPath();
		String imagePath=checkReceiptsOpreationService.queryImagePath(receiptsDataBean.getPkid(), receiptsDataBean.getImageType());
		String aa = super.getRequest().getParameter("timestamp");
        BufferedInputStream bis = null; 
        try{
          FileInputStream fileInputStream = new FileInputStream(new File(path+imagePath));  
              bis = new BufferedInputStream(fileInputStream);  
                byte[] buffer = new byte[512];  
                response.setCharacterEncoding("UTF-8");  
                        //不同类型的文件对应不同的MIME类型  
                response.setContentType("image/*");  
                        //文件以流的方式发送到客户端浏览器
                response.setContentLength(bis.available());  
                  
                os = response.getOutputStream();  
                int n;  
                while ((n = bis.read(buffer)) != -1) {  
                  os.write(buffer, 0, n);  
                }
                fileInputStream.close();
                bis.close();
                os.flush();  
                os.close();
        }catch(Exception e){
        	
        }
	}
	
	/**
	 * T+0风险商户查询列表
	 */
	public void queryRiskReceiptsAuditList(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		if((!"".equals(receiptsDataBean.getTxnday()) && receiptsDataBean.getTxnday()!=null) && (!"".equals(receiptsDataBean.getTxnday1()) && receiptsDataBean.getTxnday1()!=null) ){
			try {
				dgb=checkReceiptsOpreationService.queryRiskReceiptsAuditList(receiptsDataBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("T+0风险商户查询列表异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	/**
	 * 增加风险商户（卡片审核）
	 */
	public void addRiskMerchantInfo(){
		Log log = LogFactory.getLog(CheckReceiptsOpreationAction.class);
		JsonBean json=new JsonBean();
		String mid = super.getRequest().getParameter("mid");
		String info = super.getRequest().getParameter("info");
		if(mid!=null && !"".equals(mid)){
			try {
				String flag=checkReceiptsOpreationService.addRiskMerchantInfo(mid);
				if(flag.equals("1")){
					json.setSuccess(true);
					json.setMsg("成功");
				}else{
					json.setSuccess(true);
					json.setMsg("MID已存在");
				}
			} catch (Exception e) {
				json.setSuccess(false);
				json.setMsg("失败");
				log.info("失败");
				e.printStackTrace();
			}
		}else{
			json.setSuccess(false);
			json.setMsg("参数mid为空！");
		}
		super.writeJson(json);
	}
}
