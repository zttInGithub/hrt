package com.hrt.biz.bill.service;

import com.hrt.biz.bill.entity.model.AgentUnitTaskDetail3Model;
import com.hrt.biz.bill.entity.model.AgentUnitTaskModel;
import com.hrt.biz.bill.entity.pagebean.AgentUnitTaskBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.gmms.webservice.AgentUnitInfo;

public interface IAgentUnitTaskDetail3Service {
	AgentUnitTaskDetail3Model queryAgentUnitTaskDetail3(AgentUnitTaskBean agentUnitTask,UserBean user);
	AgentUnitTaskDetail3Model saveAgentUnitTaskDetail3(AgentUnitTaskBean agentUnitTask,UserBean user);
	AgentUnitTaskDetail3Model updateAgentUnitTaskDetail3(AgentUnitTaskBean agentUnitTask,UserBean user);
	AgentUnitInfo updateAgentUnitTaskDetail3Y(AgentUnitInfo agentUnitInfo,AgentUnitTaskModel model,UserBean user);
}
