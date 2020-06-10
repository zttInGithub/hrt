package com.hrt.biz.bill.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.pagebean.MerchantTaskDetail4Bean;
import com.hrt.biz.bill.service.IMerchantTaskDetail4Service;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 商户风控工单申请
 * @author xxx
 *
 */
public class MerchantTaskDetail4Action extends BaseAction implements ModelDriven<MerchantTaskDetail4Bean> {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(MerchantTaskDetail4Action.class);
	
	private MerchantTaskDetail4Bean merchantTaskDetail4Bean = new MerchantTaskDetail4Bean();
	private IMerchantTaskDetail4Service merchantTaskDetail4Service;
	
	@Override
	public MerchantTaskDetail4Bean getModel() {
		return merchantTaskDetail4Bean;
	}

	public IMerchantTaskDetail4Service getMerchantTaskDetail4Service() {
		return merchantTaskDetail4Service;
	}

	public void setMerchantTaskDetail4Service(IMerchantTaskDetail4Service merchantTaskDetail4Service) {
		this.merchantTaskDetail4Service = merchantTaskDetail4Service;
	}
	
	public void addMerchantRiskTaskData(){
		JsonBean json = new JsonBean();
		Object user = super.getRequest().getSession().getAttribute("user");
		try {
			//判断是否存在待审批的工单
			boolean existFlag = merchantTaskDetail4Service.queryMerchantTaskDataByMid(merchantTaskDetail4Bean.getMid());
			if(existFlag){
				json.setSuccess(false);  
				json.setMsg("已存在待审批的工单！请等待其审批后再次提交");
				super.writeJson(json); 
				return ;
			}
			Boolean flag = merchantTaskDetail4Service.queryMerchantInfoIsRight(merchantTaskDetail4Bean.getMid(),
					merchantTaskDetail4Bean.getMtype(), (UserBean) user);
			if (flag) {
				merchantTaskDetail4Service.saveMerchantRiskTaskData(merchantTaskDetail4Bean, (UserBean) user);
				json.setSuccess(true);
				json.setMsg("工单申请成功！");
			} else {
				json.setSuccess(false);
				json.setMsg("当前机构下不存在该商户！");
			}
		} catch (Exception e) {
			log.error(e);
			json.setMsg("工单申请异常！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
}
