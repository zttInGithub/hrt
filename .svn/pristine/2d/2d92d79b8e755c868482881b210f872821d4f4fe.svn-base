package com.hrt.biz.bill.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.pagebean.MIDSeqInfoBean;
import com.hrt.biz.bill.entity.pagebean.MerchantInfoBean;
import com.hrt.biz.bill.service.IMIDSeqInfoService;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.frame.base.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 流水号
 * @author tanwb
 *
 */
public class MIDSeqInfoAction extends BaseAction implements ModelDriven<MIDSeqInfoBean>{

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(MIDSeqInfoAction.class);
	
	private MIDSeqInfoBean merchantInfo = new MIDSeqInfoBean();
	
	private IMIDSeqInfoService midSeqInfoService;

	@Override
	public MIDSeqInfoBean getModel() {
		return merchantInfo;
	}

	public IMIDSeqInfoService getMidSeqInfoService() {
		return midSeqInfoService;
	}

	public void setMidSeqInfoService(IMIDSeqInfoService midSeqInfoService) {
		this.midSeqInfoService = midSeqInfoService;
	}
	
}
