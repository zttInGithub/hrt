package com.hrt.biz.bill.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.pagebean.MerchantUpdateTerBean;
import com.hrt.biz.bill.service.IMerchantUpdateTerService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 撤换机关闭商户
 */
public class MerchantUpdateTerAction extends BaseAction implements ModelDriven<MerchantUpdateTerBean>{

	private static final long serialVersionUID = 1L;
	
	private Log log = LogFactory.getLog(MerchantUpdateTerAction.class);
	
	private IMerchantUpdateTerService merchantUpdateTerService;
	
	private MerchantUpdateTerBean merchantUpdateTerBean=new MerchantUpdateTerBean();

	@Override
	public MerchantUpdateTerBean getModel() {
		return merchantUpdateTerBean;
	}

	public IMerchantUpdateTerService getMerchantUpdateTerService() {
		return merchantUpdateTerService;
	}

	public void setMerchantUpdateTerService(IMerchantUpdateTerService merchantUpdateTerService) {
		this.merchantUpdateTerService = merchantUpdateTerService;
	}
	
	//查看撤换机，关闭商户
	public void queryMerUpdateTer() {
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else {
			try {
				dgb = merchantUpdateTerService.queryMerchantUpdateTer(merchantUpdateTerBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("分页查询撤换机，关闭商户异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	//查看撤换机，关闭商户(审核)
	public void queryMerUpdateTerFor() {
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else {
			try {
				dgb = merchantUpdateTerService.queryMerchantUpdateTerFor(merchantUpdateTerBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("分页查询撤换机，关闭商户(审核)异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	//退回换机
	public void updateMerTerBack() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				json = merchantUpdateTerService.updateMerTerBack(merchantUpdateTerBean, ((UserBean)userSession));
			} catch (Exception e) {
				log.error("退回换机异常：" + e);
				json.setSuccess(false);
				json.setMsg("退回换机失败");
			}
		}
		super.writeJson(json);
	}
	
	//通过换机
	public void updateMerTerGo() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				json = merchantUpdateTerService.updateMerTerGo(merchantUpdateTerBean, ((UserBean)userSession));
			} catch (Exception e) {
				log.error("通过换机异常：" + e);
				json.setSuccess(false);
				json.setMsg("通过换机失败");
			}
		}
		super.writeJson(json);
	}
	
	//新增换机
	public void addMerUpdateTer1() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				json = merchantUpdateTerService.addMerUpdateTer1(merchantUpdateTerBean, ((UserBean)userSession));
			} catch (Exception e) {
				log.error("换机异常：" + e);
				json.setSuccess(false);
				json.setMsg("换机失败");
			}
		}
		super.writeJson(json);
	}
	//新增撤机
	public void addMerUpdateTer2() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				json = merchantUpdateTerService.addMerUpdateTer2(merchantUpdateTerBean, ((UserBean)userSession));
			} catch (Exception e) {
				log.error("撤机异常：" + e);
				json.setSuccess(false);
				json.setMsg("撤机失败");
			}
		}
		super.writeJson(json);
	}
	
	//新增关闭商户
	public void addMerUpdateTer3() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				json = merchantUpdateTerService.addMerUpdateTer3(merchantUpdateTerBean, ((UserBean)userSession));
			} catch (Exception e) {
				log.error("关闭商户异常：" + e);
				json.setSuccess(false);
				json.setMsg("关闭商户失败");
			}
		}
		super.writeJson(json);
	}
	
	//查看明细
	public void queryDetail() {
		List<Object> list = merchantUpdateTerService.queryDetail(merchantUpdateTerBean);
		super.writeJson(list);
	}
}
