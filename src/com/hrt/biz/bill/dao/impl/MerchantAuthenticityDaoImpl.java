package com.hrt.biz.bill.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import com.hrt.biz.bill.dao.IMerchantAuthenticityDao;
import com.hrt.biz.bill.entity.model.MerchantAuthenticityModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class MerchantAuthenticityDaoImpl extends BaseHibernateDaoImpl<MerchantAuthenticityModel> implements IMerchantAuthenticityDao {
	
	/**
	 * 方法功能：分页查询商户认证
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<AgentUnitModel>
	 * 异常：
	 */
	
	@Override
	public List<MerchantAuthenticityModel> querySQLMerchantAuthenticity(
			String hql, Map<String, Object> param, Integer page, Integer rows,
			Class cls) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}

	@Override
	public String querySavePath(String title) {
		Session session=this.getCurrentSession();
		Query query=session.createSQLQuery("SELECT UPLOAD_PATH FROM SYS_PARAM WHERE TITLE='"+title+"'");
		return query.list().get(0).toString();
	}
}
