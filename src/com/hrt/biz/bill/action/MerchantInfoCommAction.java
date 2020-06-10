package com.hrt.biz.bill.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.pagebean.MerchantInfoCommBean;
import com.hrt.biz.bill.service.IMerchantInfoCommService;
import com.hrt.frame.base.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;

public class MerchantInfoCommAction extends BaseAction implements ModelDriven<MerchantInfoCommBean>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(MerchantInfoAction.class); 
	
	private MerchantInfoCommBean merchantInfoCommBean = new MerchantInfoCommBean();
	
	private IMerchantInfoCommService merchantInfoCommService;
	
	private String maid;
	
	
	public IMerchantInfoCommService getMerchantInfoCommService() {
		return merchantInfoCommService;
	}
	public void setMerchantInfoCommService(
			IMerchantInfoCommService merchantInfoCommService) {
		this.merchantInfoCommService = merchantInfoCommService;
	}
	public String getMaid() {
		return maid;
	}
	public void setMaid(String maid) {
		this.maid = maid;
	}
	
	@Override
	public MerchantInfoCommBean getModel() {
		return merchantInfoCommBean;
	}
}
