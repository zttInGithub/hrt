package com.hrt.biz.check.dao.impl;

import java.util.List;
import java.util.Map;

import com.hrt.biz.check.dao.CheckReOrderDao;
import com.hrt.biz.check.dao.CheckRefundDao;
import com.hrt.biz.check.dao.CheckWechantTxnDetailDao;
import com.hrt.biz.check.entity.model.CheckReFundModel;
import com.hrt.biz.check.entity.model.CheckReOrderModel;
import com.hrt.biz.check.entity.model.CheckWechatTxnDetailModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class CheckReOrderDaoImpl extends BaseHibernateDaoImpl<CheckReOrderModel> implements CheckReOrderDao {

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
	public List<CheckReOrderModel> queryReOrder(
			String hql, Map<String, Object> param, Integer page, Integer rows,
			Class cls) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}
}
