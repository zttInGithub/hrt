package com.hrt.biz.bill.action;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.hrt.biz.bill.entity.pagebean.MachineTaskDataBean;
import com.hrt.biz.bill.service.IMachineTaskDataService;
import com.hrt.biz.bill.service.IMerchantTerminalInfoService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.word;
import com.opensymphony.xwork2.ModelDriven;

public class MachineTaskDataAction extends BaseAction implements ModelDriven<MachineTaskDataBean>{
	
	private static final long serialVersionUID = 1L;

	private Log log = LogFactory.getLog(MachineTaskDataAction.class);
	
	private MachineTaskDataBean machineTaskDataBean = new MachineTaskDataBean();
	
	private IMachineTaskDataService machineTaskDataService;
	
	private IMerchantTerminalInfoService merchantTerminalInfoService;
	
	private String bmtdids;
	
	@Override
	public MachineTaskDataBean getModel() {
		return machineTaskDataBean;
	}

	public IMachineTaskDataService getMachineTaskDataService() {
		return machineTaskDataService;
	}

	public void setMachineTaskDataService(
			IMachineTaskDataService machineTaskDataService) {
		this.machineTaskDataService = machineTaskDataService;
	}
	
	public IMerchantTerminalInfoService getMerchantTerminalInfoService() {
		return merchantTerminalInfoService;
	}

	public void setMerchantTerminalInfoService(
			IMerchantTerminalInfoService merchantTerminalInfoService) {
		this.merchantTerminalInfoService = merchantTerminalInfoService;
	}

	public String getBmtdids() {
		return bmtdids;
	}

	public void setBmtdids(String bmtdids) {
		this.bmtdids = bmtdids;
	}
	
	

	public void serchMachineTaskData(){
		DataGridBean dgb =null;
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb=machineTaskDataService.queryMachineTaskData(machineTaskDataBean,((UserBean)userSession));
		} catch (Exception e) {
			log.error("运营工单作业查询异常：" + e);
		}
		super.writeJson(dgb);  
	}
	
	
	public  void serachMachineTaskDataSingleInfo(){
		List<Map<String,Object>> list=null;
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			list=machineTaskDataService.queryMachineTaskSingleData(machineTaskDataBean);
		} catch (Exception e) {
			log.error("上次换机记录查询异常：" + e);
		}
		super.writeJson(list);  
	}
	
	public void serchAddMachineTaskData(){
		DataGridBean dgb =null;
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb=machineTaskDataService.queryAddMachineTaskData(machineTaskDataBean,((UserBean)userSession));
		} catch (Exception e) {
			log.error("运营工单申请查询异常：" + e);
		}
		super.writeJson(dgb);  
	}
	
	public void updateMachineTaskData(){
		
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			 try {
				 String bmtdID=ServletActionContext.getRequest().getParameter("bmtdID");
				 System.out.println(machineTaskDataBean.getBmtdID());
				 machineTaskDataService.updateMachineTaskData(machineTaskDataBean, ((UserBean)userSession));
					json.setSuccess(true);
					json.setMsg("成功");
			} catch (Exception e) {
				log.error("信息异常" + e);
				json.setMsg("失败");
			}
		}
		
		super.writeJson(json);
	}	
public void updateMaintainType(){
		
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			 try {
				 machineTaskDataService.updateType(machineTaskDataBean, ((UserBean)userSession));
					json.setSuccess(true);
					json.setMsg("取消装机成功");
			} catch (Exception e) {
				log.error("信息异常" + e);
				json.setMsg("取消装机失败");
			}
		}
		
		super.writeJson(json);
	}
	
	
	
	
	
	/**
	 * 导出商户入网资料 ----word
	 */
    //装机
	@SuppressWarnings("unchecked")
	public void exportMachineTaskDataOne(){
	String bmtdIDs=ServletActionContext.getRequest().getParameter("bmtdID");
		List<Map<String, Object>> list=machineTaskDataService.exportMerchantInfo1(Integer.parseInt(bmtdIDs));
	    String inster="";
		for(int i=0;i<list.size();i++){
			Map headMap=list.get(i);
				machineTaskDataBean.setOldmid(headMap.get("OLDMID").toString());
				machineTaskDataBean.setRname(headMap.get("RNAME").toString());//商户名称
				machineTaskDataBean.setPrintname(headMap.get("PRINTNAME").toString());
				machineTaskDataBean.setOrdtid(headMap.get("ORDTID").toString());//oldtid
				machineTaskDataBean.setInstalledaddress(headMap.get("INSTALLEDADDRESS").toString());//注册地址
			machineTaskDataBean.setContactphone(headMap.get("CONTACTPHONE").toString()); //联系电话
		   if(headMap.get("INSTALLEDSIM").toString().equals("1")){inster="机构自备";}
			else if(headMap.get("INSTALLEDSIM").toString().equals("2")){inster="公司提供";	}
			else if(headMap.get("INSTALLEDSIM").toString().equals("3")){inster="网络";	}
			else if(headMap.get("INSTALLEDSIM").toString().equals("4")){inster="电话线";	}
			else{inster="";}
			machineTaskDataBean.setInstalledsim(inster);  //pos连接方式
				machineTaskDataBean.setBmtID(Integer.parseInt(headMap.get("BMTID").toString()));//编号
				machineTaskDataBean.setBmtidstring(headMap.get("BMTID").toString());
			machineTaskDataBean.setMachinemodel(headMap.get("MACHINEMODEL").toString());  //机型
		}
		Map dataMap=new HashMap();
		dataMap.put("machineTaskDataBean", machineTaskDataBean);
			word w=new word();
			String filename=machineTaskDataService.queryFileNameBybmtdID(bmtdIDs)+"装机单.doc";
		      String path="updatemodel5.xml";  
			w.createDoc(dataMap, path, filename);
	}
	
	/**
	 * 换机
	 */
	@SuppressWarnings("unchecked")
	public void exportMachineTaskDataOne1(){
		String bmtdIDs=ServletActionContext.getRequest().getParameter("bmtdID");
		List<Map<String, Object>> list=machineTaskDataService.exportMerchantInfo1(Integer.parseInt(bmtdIDs));
		System.out.println(list.size());
		 String inster="";
		for(int i=0;i<list.size();i++){
			Map<String, Object> headMap=list.get(i);
				machineTaskDataBean.setMid(headMap.get("MID").toString());
				machineTaskDataBean.setOldmid(headMap.get("OLDMID").toString());
				machineTaskDataBean.setOrdtid(headMap.get("ORDTID").toString());//old tid
			machineTaskDataBean.setTid(headMap.get("TID").toString());//new tid
				machineTaskDataBean.setRname(headMap.get("RNAME").toString());//商户名称
				machineTaskDataBean.setInstalledaddress(headMap.get("INSTALLEDADDRESS").toString());//注册地址
				machineTaskDataBean.setMachinesn(headMap.get("MACHINESN").toString());//sn号
			machineTaskDataBean.setContactphone(headMap.get("CONTACTPHONE").toString()); //联系电话
				machineTaskDataBean.setContactperson(headMap.get("CONTACTPERSON").toString());
				machineTaskDataBean.setBmtID(Integer.parseInt(headMap.get("BMTID").toString()));//编号
				machineTaskDataBean.setBmtidstring(headMap.get("BMTID").toString());
			if(headMap.get("INSTALLEDSIM").toString().equals("1")){
				inster="机构自备";
			}
			else if(headMap.get("INSTALLEDSIM").toString().equals("2")){
				inster="公司提供";
			}else if(headMap.get("INSTALLEDSIM").toString().equals("3")){
				inster="网络";
			}
			else if(headMap.get("INSTALLEDSIM").toString().equals("4")){
				inster="电话线";
			}
			else{
				inster="";
			}
			machineTaskDataBean.setInstalledsim(inster);  //pos连接方式
			machineTaskDataBean.setMachinemodel(headMap.get("MACHINEMODEL").toString());  //机型
			machineTaskDataBean.setOldmachinemodel(headMap.get("OLDMACHINEMODEL").toString());  //old 机型
		}
		Map dataMap=new HashMap();
		dataMap.put("machineTaskDataBean", machineTaskDataBean);
			word w=new word();
			String filename=machineTaskDataService.queryFileNameBybmtdID(bmtdIDs)+"换机单.doc";
		      String path="huanji.xml";  
			w.createDoc(dataMap, path, filename);
		
	}
	
	/**
	 * 撤机
	 */
	@SuppressWarnings("unchecked")
	public void exportMachineTaskDataOne2(){
		String bmtdIDs=ServletActionContext.getRequest().getParameter("bmtdID");
		List<Map<String, Object>> list=machineTaskDataService.exportMerchantInfo1(Integer.parseInt(bmtdIDs));
		System.out.println(list.size());
	    String inster="";
		for(int i=0;i<list.size();i++){
			Map<String, Object> headMap=list.get(i);
				machineTaskDataBean.setOldmid(headMap.get("OLDMID").toString());
				machineTaskDataBean.setSalename(headMap.get("SALENAME").toString());
				machineTaskDataBean.setOrdtid(headMap.get("ORDTID").toString());//old tid
				machineTaskDataBean.setRname(headMap.get("RNAME").toString());//商户名称
				machineTaskDataBean.setInstalledaddress(headMap.get("INSTALLEDADDRESS").toString());//注册地址
			machineTaskDataBean.setContactphone(headMap.get("CONTACTPHONE").toString()); //联系电话
				machineTaskDataBean.setContactperson(headMap.get("CONTACTPERSON").toString());
			 if(headMap.get("INSTALLEDSIM").toString().equals("1")){
				inster="机构自备";
			}
			else if(headMap.get("INSTALLEDSIM").toString().equals("2")){
				inster="公司提供";
			}else if(headMap.get("INSTALLEDSIM").toString().equals("3")){
				inster="网络";
			}
			else if(headMap.get("INSTALLEDSIM").toString().equals("4")){
				inster="电话线";
			}
			else{
				inster="";
			}
			machineTaskDataBean.setInstalledsim(inster);  //pos连接方式
			machineTaskDataBean.setBmtID(Integer.parseInt(headMap.get("BMTID").toString()));//编号
			machineTaskDataBean.setBmtidstring(headMap.get("BMTID").toString());
		machineTaskDataBean.setOldmachinemodel(headMap.get("OLDMACHINEMODEL").toString());  //old 机型
		}
		Map dataMap=new HashMap();
		dataMap.put("machineTaskDataBean", machineTaskDataBean);
			word w=new word();
			String filename=machineTaskDataService.queryFileNameBybmtdID(bmtdIDs)+"撤机单.doc";
		      String path="cheji.xml";  
			w.createDoc(dataMap, path, filename);
	}

	/**
	 * 服务
	 */
	@SuppressWarnings("unchecked")
	public void exportMachineTaskDataOne3(){
		String bmtdIDs=ServletActionContext.getRequest().getParameter("bmtdID");
		List<Map<String, Object>> list=machineTaskDataService.exportMerchantInfo1(Integer.parseInt(bmtdIDs));
		for(int i=0;i<list.size();i++){
			Map<String, Object> headMap=list.get(i);
				machineTaskDataBean.setOldmid(headMap.get("OLDMID").toString());
				machineTaskDataBean.setRname(headMap.get("RNAME").toString());//商户名称
				machineTaskDataBean.setInstalledaddress(headMap.get("INSTALLEDADDRESS").toString());//注册地址
			machineTaskDataBean.setContactphone(headMap.get("CONTACTPHONE").toString()); //联系电话
				machineTaskDataBean.setContactperson(headMap.get("CONTACTPERSON").toString());
				machineTaskDataBean.setBmtID(Integer.parseInt(headMap.get("BMTID").toString()));//编号
				machineTaskDataBean.setBmtidstring(headMap.get("BMTID").toString());
				machineTaskDataBean.setOrdtid(headMap.get("ORDTID").toString());//old tid
				if(headMap.get("MACHINEMODEL")==null){
					machineTaskDataBean.setMachinemodel("无");  //机型
				}else{
			machineTaskDataBean.setMachinemodel(headMap.get("MACHINEMODEL").toString());  //机型
				}
		}
		Map dataMap=new HashMap();
		dataMap.put("machineTaskDataBean", machineTaskDataBean);
			word w=new word();
			String filename=machineTaskDataService.queryFileNameBybmtdID(bmtdIDs)+"现场服务记录单.doc";
		      String path="water.xml";  
			w.createDoc(dataMap, path, filename);
	}
	
	public void addMachineTaskData(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				int result = 0;
				if(machineTaskDataBean.getChangeType() != null && !"".equals(machineTaskDataBean.getChangeType()) && "1".equals(machineTaskDataBean.getChangeType())){
					result += merchantTerminalInfoService.TerminalInfoTerMID(machineTaskDataBean.getTid());
				}
				
				if(result==0){
					machineTaskDataService.saveMachineTaskData(machineTaskDataBean, ((UserBean)userSession));
					json.setSuccess(true);
					json.setMsg("工单申请成功");
				}else{
					json.setSuccess(false);
					json.setMsg("失败，该终端已经被其它用户申请");
				}
			} catch (Exception e) {
				log.error("工单申请异常：" + e);
				json.setMsg("工单申请失败");
			}
		}
		super.writeJson(json);
	}
	
	public void serchMachineTaskDataTCD(){
		DataGridBean dgb =null;
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb=machineTaskDataService.queryMachineTaskDataTCD(machineTaskDataBean,((UserBean)userSession));
		} catch (Exception e) {
			log.error("运营工单作业查询异常：" + e);
		}
		super.writeJson(dgb);  
	}
	
	public void updateMachineTaskDataY(){

		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			 try {
				String strBmtdID=ServletActionContext.getRequest().getParameter("strBmtdID");
				machineTaskDataBean.setBmtdID(Integer.parseInt(strBmtdID));
				machineTaskDataService.updateMachineTaskDataY(machineTaskDataBean, ((UserBean)userSession));
				json.setSuccess(true);
				json.setMsg("审批成功");
			} catch (Exception e) {
				log.error("审批异常" + e);
				json.setMsg("审批失败");
			}
		}
		
		super.writeJson(json);
	}
	
	public void updateMachineTaskDataTask(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			 try {
				machineTaskDataService.updateMachineTaskDataTask(machineTaskDataBean,((UserBean)userSession));
				json.setSuccess(true);
				json.setMsg("操作成功");
			} catch (Exception e) {
				log.error("操作异常" + e);
				json.setMsg("操作失败");
			}
		}
		
		super.writeJson(json);
	}
	
	public void updateMachineTaskDataTask1(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			 try {
				machineTaskDataService.updateMachineTaskDataTask1(machineTaskDataBean);
				json.setSuccess(true);
				json.setMsg("操作成功");
			} catch (Exception e) {
				log.error("操作异常" + e);
				json.setMsg("操作失败");
			}
		}
		
		super.writeJson(json);
	}
	
	public void updateMachineTaskDataTask2(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			 try {
				machineTaskDataService.updateMachineTaskDataTask2(machineTaskDataBean);
				json.setSuccess(true);
				json.setMsg("操作成功");
			} catch (Exception e) {
				log.error("操作异常" + e);
				json.setMsg("操作失败");
			}
		}
		
		super.writeJson(json);
	}

	public void updateMachineTaskDataK(){

		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			 try {
				String strBmtdID=ServletActionContext.getRequest().getParameter("strBmtdID");
				machineTaskDataBean.setBmtdID(Integer.parseInt(strBmtdID));
				machineTaskDataService.updateMachineTaskDataK(machineTaskDataBean, ((UserBean)userSession));
				json.setSuccess(true);
				json.setMsg("退回成功");
			} catch (Exception e) {
				log.error("退回异常" + e);
				json.setMsg("退回失败");
			}
		}
		
		super.writeJson(json);
	}
	
	public void serchMachineTaskDataJudge(){
		DataGridBean dgb =null;
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb=machineTaskDataService.queryMachineTaskDataJudge(machineTaskDataBean,((UserBean)userSession));
		} catch (Exception e) {
			log.error("运营工单审批查询异常：" + e);
		}
		super.writeJson(dgb);  
	}
	public void serchMachineTaskDataJudgebaodan(){
		DataGridBean dgb =null;
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
//			dgb=machineTaskDataService.queryMachineTaskDataJudge(machineTaskDataBean,((UserBean)userSession));
		} catch (Exception e) {
			log.error("运营工单审批查询异常：" + e);
		}
		super.writeJson(dgb);  
	}
	
	public void exportMer(){
		String bmtdids=getRequest().getParameter("bmtdids");
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=machineTaskDataService.export(bmtdids);
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("运营工单资料导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出工单资料Excel成功");
			} catch (Exception e) {
				log.error("导出工单资料Excel异常：" + e);
				json.setMsg("导出工单资料Excel失败");
			}
		}
	}
	
	/*
	 * 运营工单审批导出资料
	 */
	public void exportMachineTaskDataJudge(){
		String bmtdids=getRequest().getParameter("bmtdids");
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=machineTaskDataService.exportMachineTaskDataJudge(bmtdids);
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("运营未审批工单资料导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出未审批工单资料Excel成功");
			} catch (Exception e) {
				log.error("导出未审批工单资料Excel异常：" + e);
				json.setMsg("导出未审批工单资料Excel失败");
			}
		}
	}
	
	public void deleteMachineTaskData(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			machineTaskDataService.deleteMachineTaskData(machineTaskDataBean.getBmtdID(),((UserBean)userSession).getUserID());
			json.setSuccess(true);
			json.setMsg("删除工单成功");
		} catch (Exception e) {
			log.error("删除角色异常：" + e);
			json.setMsg("删除角色失败");
		}
		super.writeJson(json);
	}
}
