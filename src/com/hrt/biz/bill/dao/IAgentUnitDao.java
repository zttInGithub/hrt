package com.hrt.biz.bill.dao;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.frame.base.dao.IBaseHibernateDao;

public interface IAgentUnitDao extends IBaseHibernateDao<AgentUnitModel>{
	
	/**
	 * 方法功能：分页查询代理商
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<AgentUnitModel>
	 * 异常：
	 */
	List<AgentUnitModel> queryAgentUnit(String hql, Map<String, Object> param, Integer page, Integer rows);
	
	/**
	 * 方法功能：分页查询代理商
	 * 参数：sql sql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<AgentUnitModel>
	 * 异常：
	 */
	List<AgentUnitModel> querySQLAgentUnit(String hql, Map<String, Object> param, Integer page, Integer rows, Class cls);

	/**
	 * 获取下一个id
	 * @return
	 */
	String findMaxId();

	/**
	 * 查询上传文件路径
	 * @param title
	 * @return
	 */
	String querySavePath(String title);
}
