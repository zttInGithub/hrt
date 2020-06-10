package com.hrt.biz.bill.action;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.pagebean.MerchantTerminalInfoCommBean;
import com.hrt.biz.bill.service.IMerchantTerminalInfoCommService;
import com.hrt.frame.base.action.BaseAction;
import com.opensymphony.xwork2.ModelDriven;

public class MerchantTerminalInfoCommAction extends BaseAction implements ModelDriven<MerchantTerminalInfoCommBean>{

	/**
	 * xxx 交行数据信息信息更新
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(MerchantInfoAction.class);
	
	private File upload;
	
	MerchantTerminalInfoCommBean merchantTerminalInfoCommBean = new MerchantTerminalInfoCommBean();
	
	private IMerchantTerminalInfoCommService merchantTerminalInfoCommService ;
	
	public IMerchantTerminalInfoCommService getMerchantTerminalInfoCommService() {
		return merchantTerminalInfoCommService;
	}
	public void setMerchantTerminalInfoCommService(
			IMerchantTerminalInfoCommService merchantTerminalInfoCommService) {
		this.merchantTerminalInfoCommService = merchantTerminalInfoCommService;
	}
	
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	@Override
	public MerchantTerminalInfoCommBean getModel() {
		return merchantTerminalInfoCommBean;
	}
}
