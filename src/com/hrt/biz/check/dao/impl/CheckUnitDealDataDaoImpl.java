package com.hrt.biz.check.dao.impl;

import java.util.List;
import java.util.Map;

import com.hrt.biz.check.dao.CheckUnitDealDataDao;
import com.hrt.biz.check.entity.model.CheckUnitDealDataModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class CheckUnitDealDataDaoImpl extends BaseHibernateDaoImpl<CheckUnitDealDataModel> implements CheckUnitDealDataDao {

	@Override
	public List<CheckUnitDealDataModel> queryCheckUnitDealDataModel(String hql,
			Map<String, Object> param, Integer page, Integer rows) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}

}
