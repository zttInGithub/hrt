package com.hrt.biz.bill.service;

import com.hrt.biz.bill.entity.pagebean.MerAdjustRateBean;

public interface IMerAdjustRateService {
	
	Integer updateHYBAdjustRateInfo (MerAdjustRateBean adjustRateBean)throws Exception;
}
