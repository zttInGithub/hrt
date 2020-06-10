package com.hrt.biz.check.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.check.entity.model.CheckReFundModel;
import com.hrt.biz.check.entity.pagebean.CheckReFundBean;
import com.hrt.biz.check.service.CheckReFundService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.hrt.util.UsePioOutExcel;
import com.opensymphony.xwork2.ModelDriven;

/**
 *	@author tenglong
 *	2016-07-15
 *	差错平台-退款
 */
public class CheckReFundAction extends BaseAction implements ModelDriven<CheckReFundBean> {
	
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(CheckReFundAction.class);
	
	private CheckReFundBean checkReFundBean =new CheckReFundBean();
	private CheckReFundService checkReFundService;
	private File upload;
	private String refids;
	
	@Override
	public CheckReFundBean getModel() {
		return checkReFundBean;
	}
	public CheckReFundBean getCheckReFundBean() {
		return checkReFundBean;
	}


	public void setCheckReFundBean(CheckReFundBean checkReFundBean) {
		this.checkReFundBean = checkReFundBean;
	}


	public void setcheckReFundBean(CheckReFundBean checkReFundBean) {
		this.checkReFundBean = checkReFundBean;
	}

	
	public CheckReFundService getCheckReFundService() {
		return checkReFundService;
	}

	public void setCheckReFundService(CheckReFundService checkReFundService) {
		this.checkReFundService = checkReFundService;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getRefids() {
		return refids;
	}

	public void setRefids(String refids) {
		this.refids = refids;
	}

	/**
	 * 查询退款记录
	 */
	public void queryRefundInfo(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb=checkReFundService.queryRefundInfo(checkReFundBean,user);
		} catch (Exception e) {
			log.info("查询退款记录异常"+e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 聚合商户会员宝APP上传电子签名
	 */
	public void hybRefundImg(){
		JsonBean jsonBean = new JsonBean();
		try {
			if(checkReFundBean.getMid()==null||checkReFundBean.getOriOrderId()==null||checkReFundBean.getRefundImg()==null){
				jsonBean.setSuccess(false);
				jsonBean.setMsg("参数不全!");
			}else{
				checkReFundBean.setStatus("C");
				List<CheckReFundModel> list = checkReFundService.queryHybRefund(checkReFundBean);
				if(list.size()==0){
					checkReFundService.saveHybRefundRequest(checkReFundBean);
					jsonBean.setSuccess(true);
					jsonBean.setMsg("受理成功");
				}else{
					jsonBean.setSuccess(true);
					jsonBean.setMsg("存在处理中退款");
				}
			}
		} catch (Exception e) {
			log.info("聚合商户会员宝APP上传电子签名异常:"+e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("受理异常!");
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 聚合商户会员宝后台退款发起
	 */
	public void hybRefundRequest(){
		JsonBean jsonBean = new JsonBean();
		String rrn = null;
		try {
			if(checkReFundBean.getRrn()==null||checkReFundBean.getMid()==null||checkReFundBean.getOriOrderId()==null||
					checkReFundBean.getRamt()==null||checkReFundBean.getSamt()==null){
				jsonBean.setSuccess(false);
				jsonBean.setMsg("参数不全!");
			}else{
				rrn=checkReFundService.updateHybRefundRequest(checkReFundBean);
				if(rrn!=null){
					jsonBean.setNumberUnits(rrn);
					jsonBean.setSuccess(true);
					jsonBean.setMsg("受理成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("受理失败!");
				}
			}
		} catch (Exception e) {
			log.info("聚合商户会员宝后台退款发起异常:"+e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("受理异常!");
		}
		super.writeJson(jsonBean);
	}
	
	
	/**
	 * 聚合商户会员宝后台退款-批量审核
	 */
	public void updateErfundInfo(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user = (UserBean)userSession;
		String refids = super.getRequest().getParameter("refid");
		try {
			if(checkReFundBean.getStatus()==null||"".equals(refids)||refids==null){
				jsonBean.setSuccess(false);
				jsonBean.setMsg("操作失败");
			}else if ("".equals(checkReFundBean.getRrn().trim())){
				jsonBean.setSuccess(false);
				jsonBean.setMsg("退款订货单号为空");
			}else{
				Integer i = 0;
				String [] refid = refids.split(",");
				for(String id :refid){
					Integer num = checkReFundService.updateErfundInfo(checkReFundBean,id,user);
					i+=num;
				}
				jsonBean.setMsg("操作成功");
				jsonBean.setSuccess(true);
			}
		} catch (Exception e) {
			log.info("聚合商户会员宝后台退款-批量审核异常:"+e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("操作失败!");
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 车辆代缴审批
	 */
	public void updateErfundInfo2(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user = (UserBean)userSession;
		String refids = super.getRequest().getParameter("refid");
		try {
			if(checkReFundBean.getStatus()==null||"".equals(refids)||refids==null){
				jsonBean.setSuccess(false);
				jsonBean.setMsg("操作失败");
			}else if ("".equals(checkReFundBean.getRrn().trim())){
				jsonBean.setSuccess(false);
				jsonBean.setMsg("退款订货单号为空");
			}else{
				Integer i = 0;
				String [] refid = refids.split(",");
				for(String id :refid){
					Integer num = checkReFundService.updateErfundInfo2(checkReFundBean,id,user);
					i+=num;
				}
				jsonBean.setMsg("操作成功");
				jsonBean.setSuccess(true);
			}
		} catch (Exception e) {
			log.info("车辆代缴退款审核异常:"+e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("操作失败!");
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 退款信息匹配综合交易记录
	 */
	public void queryAdmTxnInfo(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		JsonBean json = new JsonBean();
		String mid = checkReFundBean.getMid();
		String rrn = checkReFundBean.getRrn();
		String cardpan = checkReFundBean.getCardPan();
		Date txnDay = checkReFundBean.getTxnDay();
		if(mid==null||mid==""||rrn==null||rrn==""||cardpan==null||cardpan==""||txnDay==null){
			dgb=null;
		}else{
			String txnDayStr = new SimpleDateFormat("yyyyMMdd").format(txnDay);
			checkReFundBean.setTxnDayStr(txnDayStr);
			try {
				//判断mid是否归属该用户
				Integer flag = checkReFundService.queryMidInUsr(checkReFundBean,user);
				if(flag==0){
					json.setMsg("此用户下不存在此商户");
					json.setSuccess(false);
					super.writeJson(json);
					return ;
				}else{
					dgb=checkReFundService.queryAdmTxnInfo(checkReFundBean,user);
					if(dgb.getTotal()==0L){
						json.setMsg("未查询到原交易");
						json.setSuccess(false);
						super.writeJson(json);
						return;
					}
				}
			} catch (Exception e) {
				log.info("退款信息匹配综合交易记录异常"+e);
			}
		}
		super.writeJson(dgb);
	}
	/**
	 * Excel 导入差错文件
	 */
	public void  importRefundInfo(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null){
			json.setSessionExpire(true);
		}
		// 把文件保存到服务器目录下
		String fileName = ServletActionContext.getRequest().getParameter("upload_refile");
		String basePath = ServletActionContext.getRequest().getRealPath("/upload");
		File dir = new File(basePath);
		dir.mkdir();
		String path = basePath +"/"+fileName;
		File destFile = new File(path);
		upload.renameTo(destFile);
		List<String> errlist =new ArrayList<String>();
		try {
			if(path.length() > 0 && path != null){
				errlist= checkReFundService.saveImportRefundInfo(path, user, fileName);
				if (errlist.size()>0){
					removeUploadFile(basePath,path);
					String[] cellFormatType = {"t","t"};
					UsePioOutExcel.writeExcel(errlist, 2, super.getResponse(), "失败记录","差错文件导入失败记录" + ".xls", cellFormatType); // 调用导出方法
					return;
				}
				json.setSuccess(true);
				json.setMsg("差错文件导入成功!");
				super.writeJson(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
			json.setSuccess(false);
			json.setMsg("文件导入失败！");
			super.writeJson(json);
		}
		removeUploadFile(basePath,path);
	}
	
	/**
	 * 提交退款
	 */
	public void saveRefundInfoInfoC(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		}else{
			try {
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
				if(sf.format(checkReFundBean.getTxnDay()).equals(sf.format(new Date()))){
					json.setSuccess(false);
					json.setMsg("当天交易不允许退款");
				}else{
	//				int result = 0;
	//				String refundIds[]=refids.split(",");
					Integer flag=checkReFundService.saveRefundInfoInfoC(checkReFundBean, ((UserBean)userSession));
					if(flag==1){
						json.setSuccess(true);
						json.setMsg("提交退款成功");
					}else if(flag==0){
						json.setSuccess(false);
						json.setMsg("提交退款失败");
					}else if(flag==-1){
						json.setSuccess(false);
						json.setMsg("余额不足");
					}
				}
			} catch (Exception e) {
				log.error("提交退款异常" + e);
				json.setSuccess(false);
				json.setMsg("提交退款失败");
			}
			super.writeJson(json);
		}
		
	}
	
	/**
	 * 退款审核
	 */
	public void updateRefund(){
		HttpServletRequest request =super.getRequest();
		JsonBean json = new JsonBean();
		boolean flag = false;
		try {
			request.setCharacterEncoding("UTF-8");
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
			String line = "";
			StringBuffer buf = new StringBuffer();
			while((line=br.readLine())!=null){
				buf.append(line);
			}
			log.info("退款审核:"+buf);
			//flag=proxyErrService.addProxyRefound(JSONObject.fromObject(buf.toString()));
			flag = checkReFundService.updateRefund(JSONObject.parseObject(buf.toString()));
			json.setSuccess(flag);
		} catch (Exception e) {
			log.info("退款审核"+e);
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	/**
	 * 退款导出
	 */
	public void exportRefundInfo(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				List<Map<String, Object>> list=checkReFundService.exportRefundInfo(checkReFundBean,((UserBean)userSession));
				List excelList = new ArrayList();
				String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t"};
					String title [] = {"商户编号", "参考号/聚合退款单号", "原订单号",  "交易卡号", "交易日期", "原始交易金额","退款金额","结算方式", "备注","提交时间","审核状态", "审核日期"};
					excelList.add(title);
					for (int i = 0; i < list.size(); i++) {
						Map map = list.get(i);
						String []rowContents ={
								map.get("MID")==null?"":map.get("MID").toString(),	
								map.get("RRN")==null?"":map.get("RRN").toString(),
								map.get("ORIORDERID")==null?"":map.get("ORIORDERID").toString(),
								map.get("CARDPAN")==null?"":map.get("CARDPAN").toString(),
								map.get("TXNDAY")==null?"":map.get("TXNDAY").toString(),
								map.get("SAMT")==null?"":map.get("SAMT").toString(),
								map.get("RAMT")==null?"":map.get("RAMT").toString(),
								map.get("SETTLEMENT")==null?"":map.get("SETTLEMENT").toString(),
								map.get("REMARKS")==null?"":map.get("REMARKS").toString(),
								map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
								map.get("STATUS1")==null?"":map.get("STATUS1").toString(),
								map.get("APPROVEDATE")==null?"":map.get("APPROVEDATE").toString(),	
						};
						excelList.add(rowContents);
					}
				String excelName = "退款资料导出";
				String sheetName = "退款资料导出";
				JxlOutExcelUtil.writeExcel(excelList, cellFormatType.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("退款资料导出Excel成功");
				excelList=null;
				list=null;
			} catch (Exception e) {
				log.error("退款资料导出Excel异常：" + e);
				json.setMsg("退款资料导出Excel失败");
			}
		}
	}
	
	/**
	 * 删除服务器上的上传文件
	 * @param basePath
	 * @param path
	 */
	public void removeUploadFile(String basePath,String path){
		File file = new File(basePath);
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(basePath + tempList[i]);
			} else {
				temp = new File(basePath + File.separator + tempList[i]);
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

}
