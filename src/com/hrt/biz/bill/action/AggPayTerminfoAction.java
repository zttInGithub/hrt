package com.hrt.biz.bill.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.pagebean.AggPayTerminfoBean;
import com.hrt.biz.bill.service.IAggPayTerminfoService;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 聚合商户终端
 * @author tenglong
 */
public class AggPayTerminfoAction extends BaseAction implements ModelDriven<AggPayTerminfoBean> {
	
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(AggPayTerminfoAction.class);

	private AggPayTerminfoBean aggPayTerminfoBean = new AggPayTerminfoBean();

	@Override
	public AggPayTerminfoBean getModel() {
		return aggPayTerminfoBean;
	}

	private IAggPayTerminfoService infoService;
	private IMerchantInfoService merchantInfoService;

	public AggPayTerminfoBean getAggPayTerminfoBean() {
		return aggPayTerminfoBean;
	}

	public void setAggPayTerminfoBean(AggPayTerminfoBean aggPayTerminfoBean) {
		this.aggPayTerminfoBean = aggPayTerminfoBean;
	}

	public IAggPayTerminfoService getInfoService() {
		return infoService;
	}

	public void setInfoService(IAggPayTerminfoService infoService) {
		this.infoService = infoService;
	}

	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}
	
	//增机审批资料勾选审批
	public void AddAggPayTerminalInfoSelected(){
		String bmtids =super.getRequest().getParameter("BAPIDs");
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				String ids[]=bmtids.split(",");
				infoService.updateAggPayTerminalinfosY(aggPayTerminfoBean, ((UserBean)userSession),ids);
				json.setSuccess(true);
				json.setMsg("增机审批成功");
			} catch (Exception e) {
				log.error("批量增机审批异常：" + e);
				json.setMsg("批量增机审失败");
				e.printStackTrace();
			}
		}
		super.writeJson(json);
	}
	
	public void listAggPayTerminalInfoZ(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(aggPayTerminfoBean==null){
				aggPayTerminfoBean = new AggPayTerminfoBean();
			}
			aggPayTerminfoBean.setStatus(1);
			dgb = infoService.queryAggPayTerminfoZ(aggPayTerminfoBean,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询聚合终端信息异常：" + e);
		}
		super.writeJson(dgb);
	}
}
