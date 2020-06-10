package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.pagebean.AgentUnitTaskBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IAgentUnitTaskService {
	
	DataGridBean queryAgentUnitTask(AgentUnitTaskBean agentUnitTask,UserBean user);
	Integer addAgentUnitTask(AgentUnitTaskBean agentUnitTask,UserBean user) throws Exception;
	Integer deleteAgentUnitTask(AgentUnitTaskBean agentUnitTask,UserBean user);
	Integer updateAgentUnitTask(AgentUnitTaskBean agentUnitTask,UserBean user) throws Exception;
	Integer updateAgentUnitTaskK(AgentUnitTaskBean agentUnitTask,UserBean user);
	Integer updateAgentUnitTaskY(AgentUnitTaskBean agentUnitTask,UserBean user);
	List<Object> queryAgentUnitTaskDetail(AgentUnitTaskBean agentUnitTask,UserBean user);
	DataGridBean queryLoanUnit(AgentUnitTaskBean agentUnitTask,UserBean user);
	Integer deleteLoanUnno(AgentUnitTaskBean agentUnitTask,UserBean user);
	List<Map<String,String>> addLoanUnit(String xlsfile,String fileName,UserBean user);
}
