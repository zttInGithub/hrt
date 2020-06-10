package com.hrt.biz.bill.dao.impl;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IMachineOrderDataDao;
import com.hrt.biz.bill.entity.model.MachineOrderDataModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class MachineOrderDataDaoImpl extends BaseHibernateDaoImpl<MachineOrderDataModel> implements IMachineOrderDataDao{

	/**
	 * 方法功能：分页查询机具订单信息
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<MachineOrderDataModel>
	 * 异常：
	 */
	@Override
	public List<MachineOrderDataModel> queryOrders(String hql,
			Map<String, Object> param, Integer page, Integer rows) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}

}
