package com.hrt.biz.bill.dao;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.biz.bill.entity.model.MachineOrderDetailModel;
import com.hrt.biz.bill.entity.model.MachineTaskDataModel;
import com.hrt.frame.base.dao.IBaseHibernateDao;

public interface IMachineTaskDataDao extends IBaseHibernateDao<MachineTaskDataModel>{
	
	/**
	 * 方法功能：分页查询机具工单作业
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<MachineTaskDataModel>
	 * 异常：
	 */
	List<MachineTaskDataModel> queryMachineTaskData(String hql, Map<String, Object> param, Integer page, Integer rows, Class cls);
	
	Integer getBmtdID();
}
