package com.hrt.biz.check.service;

import com.hrt.biz.check.entity.pagebean.CheckRealtimeDealDetailBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface ChecRealtimeDealDetailService {

	//public String savequeryTrade(String mid,String liu,String tid,String cardNum,String j,UserBean user);

	public DataGridBean queryRealTimeData(CheckRealtimeDealDetailBean crm, UserBean userSession,int index);

	public DataGridBean queryRealTimeTop10(UserBean userSession);
	
	DataGridBean queryTodayDealDatas(CheckRealtimeDealDetailBean crm,UserBean userSession);

}
