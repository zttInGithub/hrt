package com.hrt.biz.bill.service.impl;

import com.hrt.biz.bill.dao.IMerchantTerminalInfoCommDao;
import com.hrt.biz.bill.dao.MerchantInfoCommDao;
import com.hrt.biz.bill.service.IMerchantTerminalInfoCommService;

public class MerchantTerminalInfoCommServiceImpl implements IMerchantTerminalInfoCommService {

	private IMerchantTerminalInfoCommDao merchantTerminalInfoCommDao;
	
	private MerchantInfoCommDao merchantInfoCommDao;
	
	public IMerchantTerminalInfoCommDao getMerchantTerminalInfoCommDao() {
		return merchantTerminalInfoCommDao;
	}
	public void setMerchantTerminalInfoCommDao(IMerchantTerminalInfoCommDao merchantTerminalInfoCommDao) {
		this.merchantTerminalInfoCommDao = merchantTerminalInfoCommDao;
	}
	public MerchantInfoCommDao getMerchantInfoCommDao() {
		return merchantInfoCommDao;
	}
	public void setMerchantInfoCommDao(MerchantInfoCommDao merchantInfoCommDao) {
		this.merchantInfoCommDao = merchantInfoCommDao;
	}
}
