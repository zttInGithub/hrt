package com.hrt.biz.bill.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.service.IMerchantTaskDetailOtherService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.JsonBean;

/**
 * 商户费率工单申请
 * @author xxx
 *
 */
public class MerchantTaskDetailOtherAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(MerchantTaskDetailOtherAction.class);
	//根据商户机构编号查询商户信息
	private String unno;		
	private IMerchantTaskDetailOtherService merchantTaskDetailOtherService;
	private String mid;
	private List<MerchantInfoModel> list;
	private String descr;

	
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public List<MerchantInfoModel> getList() {
		return list;
	}
	public void setList(List<MerchantInfoModel> list) {
		this.list = list;
	}
	public IMerchantTaskDetailOtherService getMerchantTaskDetailOtherService() {
		return merchantTaskDetailOtherService;
	}
	public void setMerchantTaskDetailOtherService(
			IMerchantTaskDetailOtherService merchantTaskDetailOtherService) {
		this.merchantTaskDetailOtherService = merchantTaskDetailOtherService;
	}
	public String getDescr() {	
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	
	
	/**
	 * 根据商户机构编号查询商户费率信息
	 */
	public void serchMerchantInfo(){
		//DataGridBean dgd = new DataGridBean(); 
		list=merchantTaskDetailOtherService.queryMerchantInfo(mid);
		super.writeJson(list);
	}

	/**
	 * 保存商户提交工单申请4
	 */
	public void addMerchantTaskDetail4(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
					//判断是否存在待审批的工单
					boolean flag = merchantTaskDetailOtherService.queryMerchantTaskDetailByMid(mid);
					if(flag){
						json.setSuccess(false);  
						json.setMsg("已存在待审批的工单！请等待其审批后再次提交");
						super.writeJson(json); 
						return ;
					}
					merchantTaskDetailOtherService.saveMerchantTaskDetail4(unno, mid,descr);
					json.setSuccess(true); 
					json.setMsg("工单提交成功");  
				}catch (Exception e) {
				log.error("工单提交异常：" + e);
				json.setMsg("工单提交失败");
			
		}
		super.writeJson(json);
		}
	}
	
	/**
	 * 保存商户提交工单申请5
	 */
	public void addMerchantTaskDetail5(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {	
					//判断是否存在待审批的工单
					boolean flag = merchantTaskDetailOtherService.queryMerchantTaskDetailByMid(mid);
					if(flag){
						json.setSuccess(false);  
						json.setMsg("已存在待审批的工单！请等待其审批后再次提交");
						super.writeJson(json); 
						return ;
					}
					merchantTaskDetailOtherService.saveMerchantTaskDetail5(unno, mid,descr);
					json.setSuccess(true); 
					json.setMsg("工单提交成功");  
				}catch (Exception e) {
				log.error("工单提交异常：" + e);
				json.setMsg("工单提交失败");
			
		}
		super.writeJson(json);
		}
	}
	
	/**
	 * 保存商户提交工单申请6
	 */
	public void addMerchantTaskDetail6(){
		System.out.println("%%%%%%%%%%%%%%%%%%%%%+descr"+descr);
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
					merchantTaskDetailOtherService.saveMerchantTaskDetail6(unno, mid,descr);
					json.setSuccess(true); 
					json.setMsg("工单提交成功");  
				}catch (Exception e) {
				log.error("工单提交异常：" + e);
				json.setMsg("工单提交失败");
			
		}
		super.writeJson(json);
		}
	}
	
	/**
	 * 保存商户提交工单申请7
	 */
	public void addMerchantTaskDetail7(){
		System.out.println("%%%%%%%%%%%%%%%%%%%%%+descr"+descr);
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
					merchantTaskDetailOtherService.saveMerchantTaskDetail7(unno, mid,descr);
					json.setSuccess(true); 
					json.setMsg("工单提交成功");  
				}catch (Exception e) {
				log.error("工单提交异常：" + e);
				json.setMsg("工单提交失败");
			
		}
		super.writeJson(json);
		} 
	}
	
	
}
