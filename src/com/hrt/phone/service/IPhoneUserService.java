package com.hrt.phone.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IPhoneUserService {

	public UserBean login(UserBean user) throws Exception;

	Map loginYSB(UserBean user) throws Exception;

	public UserBean loginAgent(UserBean user) throws Exception;
	
	public AgentUnitModel getAgentByUnno(UserBean user) throws Exception;
	
	public UserBean agentLogin(UserBean user) throws Exception;

	public List<Map<String,String>> queryDataForHYB(String loginName);

	public List<Map<String, Object>> queryMerchantPassword(UserBean user) throws Exception;

	/**
	 * 查询是否是手刷商户
	 * @param loginName
	 * @return 0:非手刷  1：手刷
	 */
	public Integer findMerchantIsM35(String loginName);
}
