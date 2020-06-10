package com.hrt.biz.bill.service.impl;

import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IAgentUnitDao;
import com.hrt.biz.bill.dao.IAgentUnitTaskDetail3Dao;
import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.biz.bill.entity.model.AgentUnitTaskDetail1Model;
import com.hrt.biz.bill.entity.model.AgentUnitTaskDetail3Model;
import com.hrt.biz.bill.entity.model.AgentUnitTaskModel;
import com.hrt.biz.bill.entity.pagebean.AgentUnitTaskBean;
import com.hrt.biz.bill.service.IAgentUnitTaskDetail3Service;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.gmms.webservice.AgentUnitInfo;

public class AgentUnitTaskDetail3ServiceImpl implements IAgentUnitTaskDetail3Service {
	
	private IAgentUnitTaskDetail3Dao agentUnitTaskDetail3Dao;
	private IAgentUnitDao agentUnitDao;

	public IAgentUnitDao getAgentUnitDao() {
		return agentUnitDao;
	}

	public void setAgentUnitDao(IAgentUnitDao agentUnitDao) {
		this.agentUnitDao = agentUnitDao;
	}
	
	public IAgentUnitTaskDetail3Dao getAgentUnitTaskDetail3Dao() {
		return agentUnitTaskDetail3Dao;
	}

	public void setAgentUnitTaskDetail3Dao(IAgentUnitTaskDetail3Dao agentUnitTaskDetail3Dao) {
		this.agentUnitTaskDetail3Dao = agentUnitTaskDetail3Dao;
	}

	@Override
	public AgentUnitTaskDetail3Model queryAgentUnitTaskDetail3(AgentUnitTaskBean agentUnitTask,UserBean user) {
		AgentUnitTaskDetail3Model model = agentUnitTaskDetail3Dao.queryObjectByHql("from AgentUnitTaskDetail3Model where bautdid="+agentUnitTask.getBautdid()+" ", new Object[]{});
		return model;
	}

	@Override
	public AgentUnitTaskDetail3Model saveAgentUnitTaskDetail3(AgentUnitTaskBean agentUnitTask, UserBean user) {
		AgentUnitTaskDetail3Model model = new AgentUnitTaskDetail3Model();
		BeanUtils.copyProperties(agentUnitTask,model);
		agentUnitTaskDetail3Dao.saveObject(model);
		return model;
	}

	@Override
	public AgentUnitTaskDetail3Model updateAgentUnitTaskDetail3(AgentUnitTaskBean agentUnitTask, UserBean user) {
		AgentUnitTaskDetail3Model model = agentUnitTaskDetail3Dao.queryObjectByHql("from AgentUnitTaskDetail3Model where bautdid="+agentUnitTask.getBautdid()+"", new Object[]{});
		BeanUtils.copyProperties(agentUnitTask,model);
		agentUnitTaskDetail3Dao.updateObject(model);
		return model;
	}
	
	@Override
	public AgentUnitInfo updateAgentUnitTaskDetail3Y(AgentUnitInfo agentUnitInfo,AgentUnitTaskModel model, UserBean user) {
		AgentUnitTaskDetail3Model model2 = agentUnitTaskDetail3Dao.queryObjectByHql("from AgentUnitTaskDetail3Model where bautdid="+model.getBautdid()+"", new Object[]{});
		AgentUnitModel agentUnitModel = agentUnitDao.queryObjectByHql("from AgentUnitModel where unno = '"+user.getUnNo()+"' ", new Object[]{});
		agentUnitModel.setContact(model2.getContact());
		agentUnitModel.setContactTel(model2.getContactTel());
		agentUnitModel.setBusinessContacts(model2.getBusinessContacts());
		agentUnitModel.setBusinessContactsPhone(model2.getBusinessContactsPhone());
		agentUnitModel.setBusinessContactsMail(model2.getBusinessContactsMail());
		agentUnitModel.setRiskContact(model2.getRiskContact());
		agentUnitModel.setRiskContactPhone(model2.getRiskContactPhone());
		agentUnitModel.setRiskContactMail(model2.getRiskContactMail());
		agentUnitDao.updateObject(agentUnitModel);
		agentUnitInfo.setUnno(agentUnitModel.getUnno());
		agentUnitInfo.setContact(model2.getContact());
		agentUnitInfo.setContactTel(model2.getContactTel());
		agentUnitInfo.setBusinessContacts(model2.getBusinessContacts());
		agentUnitInfo.setBusinessContactsPhone(model2.getBusinessContactsPhone());
		agentUnitInfo.setBusinessContactsMail(model2.getBusinessContactsMail());
		agentUnitInfo.setRiskContact(model2.getRiskContact());
		agentUnitInfo.setRiskContactPhone(model2.getRiskContactPhone());
		agentUnitInfo.setRiskContactMail(model2.getRiskContactMail());
		return agentUnitInfo;
	}
}
