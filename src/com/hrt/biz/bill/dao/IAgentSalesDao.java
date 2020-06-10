package com.hrt.biz.bill.dao;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.frame.base.dao.IBaseHibernateDao;

public interface IAgentSalesDao extends IBaseHibernateDao<AgentSalesModel>{
	/**
	 * 方法功能：分页查询业务员信息
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<AgentSalesModel>
	 */
	List<AgentSalesModel> queryAgentSales(String hql, Map<String, Object> param, Integer page, Integer rows);
}
