package com.hrt.biz.check.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.hrt.biz.check.dao.CheckMisTakeDao;
import com.hrt.biz.check.entity.model.CheckMisTakeModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class CheckMisTakeDaoImpl extends BaseHibernateDaoImpl<CheckMisTakeModel> implements CheckMisTakeDao{

	/**
	 * 方法功能：分页查询
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<AgentUnitModel>
	 * 异常：
	 */
	
	@Override
	public List<CheckMisTakeModel> queryMisTakeInfo(
			String hql, Map<String, Object> param, Integer page, Integer rows,
			Class cls) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}
	//查询上传文件路径
	@Override
	public String querySavePath(String title) {
		Session session=this.getCurrentSession();
		Query query=session.createSQLQuery("SELECT UPLOAD_PATH FROM SYS_PARAM WHERE TITLE='"+title+"'");
		return query.list().get(0).toString();
	}
}
