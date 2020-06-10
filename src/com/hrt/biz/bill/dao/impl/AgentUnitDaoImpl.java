package com.hrt.biz.bill.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.hrt.biz.bill.dao.IAgentUnitDao;
import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class AgentUnitDaoImpl extends BaseHibernateDaoImpl<AgentUnitModel> implements IAgentUnitDao {

	/**
	 * 方法功能：分页查询代理商
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<AgentUnitModel>
	 * 异常：
	 */
	@Override
	public List<AgentUnitModel> queryAgentUnit(String hql, Map<String, Object> param,
			Integer page, Integer rows) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}
	
	/**
	 * 方法功能：分页查询代理商
	 * 参数：sql sql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<AgentUnitModel>
	 * 异常：
	 */
	@Override
	public List<AgentUnitModel> querySQLAgentUnit(String hql, Map<String, Object> param,
			Integer page, Integer rows, Class cls) {
		return super.queryObjectsBySqlLists(hql, param, page, rows, cls);
	}

	@Override
	public String findMaxId() {
		List list=this.getCurrentSession().createSQLQuery("select S_BILL_AGENTUNITINFO.nextval from dual").list();
		if(list==null&&!list.isEmpty()){
			return "1";
		}else{
			return list.get(0).toString();
		}
	}
	
	@Override
	public String querySavePath(String title) {
		Session session=this.getCurrentSession();
		List list=session.createSQLQuery("SELECT UPLOAD_PATH FROM SYS_PARAM WHERE TITLE='"+title+"'").list();
		if(list==null&&!list.isEmpty()){
			return null;
		}else{
			return list.get(0).toString();
		}
	}
	
}
