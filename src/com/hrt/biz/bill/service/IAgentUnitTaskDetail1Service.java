package com.hrt.biz.bill.service;

import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.biz.bill.entity.model.AgentUnitTaskDetail1Model;
import com.hrt.biz.bill.entity.model.AgentUnitTaskModel;
import com.hrt.biz.bill.entity.pagebean.AgentUnitTaskBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.gmms.webservice.AgentUnitInfo;

public interface IAgentUnitTaskDetail1Service {
	AgentUnitTaskDetail1Model queryAgentUnitTaskDetail1(AgentUnitTaskBean agentUnitTask,UserBean user);
	AgentUnitTaskDetail1Model saveAgentUnitTaskDetail1(AgentUnitTaskBean agentUnitTask,UserBean user) throws Exception;
	AgentUnitTaskDetail1Model updateAgentUnitTaskDetail1(AgentUnitTaskBean agentUnitTask,UserBean user) throws Exception;
	AgentUnitInfo updateAgentUnitTaskDetail1Y(AgentUnitInfo agentUnitInfo,AgentUnitTaskModel model,UserBean user);
}
