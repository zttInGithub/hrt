package com.hrt.biz.bill.dao.impl;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class AgentSalesDaoImpl extends BaseHibernateDaoImpl<AgentSalesModel> implements IAgentSalesDao {

	/**
	 * 方法功能：分页查询业务员信息
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<AgentSalesModel>
	 */
	@Override
	public List<AgentSalesModel> queryAgentSales(String hql,
			Map<String, Object> param, Integer page, Integer rows) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}

}
