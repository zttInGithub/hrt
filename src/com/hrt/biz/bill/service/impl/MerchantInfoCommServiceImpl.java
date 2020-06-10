package com.hrt.biz.bill.service.impl;


import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.dao.IMerchantTerminalInfoCommDao;
import com.hrt.biz.bill.dao.MerchantInfoCommDao;
import com.hrt.biz.bill.service.IMerchantInfoCommService;

public class MerchantInfoCommServiceImpl implements IMerchantInfoCommService {

	private MerchantInfoCommDao merchantInfoCommDao;
	private IAgentSalesDao agentSalesDao;
	private IMerchantTerminalInfoCommDao merchantTerminalInfoCommDao;

	
	public MerchantInfoCommDao getMerchantInfoCommDao() {
		return merchantInfoCommDao;
	}
	public void setMerchantInfoCommDao(MerchantInfoCommDao merchantInfoCommDao) {
		this.merchantInfoCommDao = merchantInfoCommDao;
	}
	public IAgentSalesDao getAgentSalesDao() {
		return agentSalesDao;
	}
	public void setAgentSalesDao(IAgentSalesDao agentSalesDao) {
		this.agentSalesDao = agentSalesDao;
	}
	public IMerchantTerminalInfoCommDao getMerchantTerminalInfoCommDao() {
		return merchantTerminalInfoCommDao;
	}
	public void setMerchantTerminalInfoCommDao(
			IMerchantTerminalInfoCommDao merchantTerminalInfoCommDao) {
		this.merchantTerminalInfoCommDao = merchantTerminalInfoCommDao;
	}
}
