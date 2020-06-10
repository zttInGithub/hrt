package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.pagebean.MerchantTaskDetail5Bean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IMerchantTaskDetail5Service {

	void saveMerchantTaskDetail5Data(MerchantTaskDetail5Bean merchantTaskDetail5Bean, UserBean user);

	boolean findMidInfo(String mid);

	List<Map<String, Object>> findMidStatusInfo(String mid);

	boolean queryMerchantIsMicro(String mid);

}
