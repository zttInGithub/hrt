package com.hrt.biz.bill.dao.impl;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IMerchantTaskDataDao;
import com.hrt.biz.bill.entity.model.MerchantTaskDataModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class MerchantTaskDataDaoImpl extends BaseHibernateDaoImpl<MerchantTaskDataModel> implements IMerchantTaskDataDao{

	@Override
	public List<MerchantTaskDataModel> queryPage(String hql,
			Map<String, Object> param, Integer page, Integer rows) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}

}
