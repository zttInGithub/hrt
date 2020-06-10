package com.hrt.biz.credit.dao;

import java.util.List;
import java.util.Map;
import com.hrt.biz.check.entity.model.CheckWechatTxnDetailModel;
import com.hrt.frame.base.dao.IBaseHibernateDao;

public interface CreditAgentDao extends IBaseHibernateDao<CheckWechatTxnDetailModel> {

	/**
	 * 方法功能：分页查询
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<AgentUnitModel>
	 * 异常：
	 */
	
	public List<CheckWechatTxnDetailModel> queryWechantTxnDetailInfo(
			String hql, Map<String, Object> param, Integer page, Integer rows,
			Class cls);

}
