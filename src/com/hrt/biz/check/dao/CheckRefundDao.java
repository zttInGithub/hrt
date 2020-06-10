package com.hrt.biz.check.dao;

import java.util.List;
import java.util.Map;

import com.hrt.biz.check.entity.model.CheckReFundModel;
import com.hrt.frame.base.dao.IBaseHibernateDao;

public interface CheckRefundDao extends IBaseHibernateDao<CheckReFundModel> {

	/**
	 * 方法功能：分页查询
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<AgentUnitModel>
	 * 异常：
	 */
	
	public List<CheckReFundModel> queryErfundInfo(
			String hql, Map<String, Object> param, Integer page, Integer rows,
			Class cls);

}
