package com.hrt.biz.bill.dao.impl;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.ITerminalInfoDao;
import com.hrt.biz.bill.entity.model.TerminalInfoModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;
import com.hrt.frame.entity.model.RoleModel;

public class TerminalInfoDaoImpl  extends BaseHibernateDaoImpl<TerminalInfoModel> implements ITerminalInfoDao{

	@Override
	public List<TerminalInfoModel> queryTerminalInfos(String hql,
			Map<String, Object> param, Integer page, Integer rows) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}
	
	
}
