package com.hrt.biz.bill.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.entity.pagebean.AgentSalesBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IAgentSalesService {
	
	/**
	 * 分页查询业务员资料
	 */
	DataGridBean queryAgentSales(AgentSalesBean agentSales,String unno);
	
	/**
	 * 添加业务员
	 */
	void saveAgentSales(AgentSalesBean agentSales, UserBean user) throws Exception;
	
	/**
	 * 查询业务员信息显示到select中
	 */
	DataGridBean searchAgentSales(String unno,String nameCode) throws Exception;
	/**
	 * 查询业务员信息显示到select中
	 */
	DataGridBean searchAgentSales3(UserBean user) throws Exception;
	/**
	 * 查询业务员信息
	 */
	AgentSalesBean searchAgentSalesByName(String nameCode);
	
	/**
	 * 修改业务员信息
	 */
	void updateAgentSales(AgentSalesBean agentSales, UserBean user) throws Exception;
	
	/**
	 * 删除业务员信息
	 */
	void deleteAgentSales(Integer id) throws Exception;

	/**
	 * 导出业务员资料excel
	 */
	HSSFWorkbook export(String ids);
	
	/**
	 * 添加时查询用户是否已有对应的业务员（返回>0为有）
	 */
	Integer queryAgentSalesCounts(AgentSalesBean agentSalesBean);
	
	/**
	 * 修改时查询用户是否已有对应的业务员（返回>0为有）
	 */
	Integer queryUpdateAgentSalesCounts(AgentSalesBean agentSalesBean);

	DataGridBean searchUnno(String unNo, String nameCode);
	/**
	 * 查询本公司下-维护的组成员
	 */
	DataGridBean queryAgentSalesGroup(AgentSalesBean agentSales, String unNo);
	/**
	 * 查询组
	 */
	DataGridBean queryAgentsalesGroup();
	/**
	 * 查询销售详细信息
	 */
	Map queryAgentsalesGroupForId(AgentSalesBean agentSales, UserBean userSession);
	/**
	 * 修改销售组别信息
	 */
	String updateAgentSalesGroup(AgentSalesBean agentSales, UserBean userSession);
}
