package com.hrt.biz.bill.dao.impl;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IMachineInfoDao;
import com.hrt.biz.bill.entity.model.MachineInfoModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class MachineInfoDaoImpl extends BaseHibernateDaoImpl<MachineInfoModel> implements IMachineInfoDao{

	/**
	 * 方法功能：分页查询机具库存
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<MachineInfoModel>
	 */
	@Override
	public List<MachineInfoModel> queryMachineInfos(String hql,
			Map<String, Object> param, Integer page, Integer rows) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}

}
