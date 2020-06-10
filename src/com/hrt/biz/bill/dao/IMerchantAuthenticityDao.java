package com.hrt.biz.bill.dao;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.model.MerchantAuthenticityModel;
import com.hrt.frame.base.dao.IBaseHibernateDao;

public interface IMerchantAuthenticityDao extends IBaseHibernateDao<MerchantAuthenticityModel> {
	
	/**
	 * 方法功能：分页查询商户认证
	 * 参数：sql sql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<AgentUnitModel>
	 * 异常：
	 */
	List<MerchantAuthenticityModel> querySQLMerchantAuthenticity(String hql, Map<String, Object> param, Integer page, Integer rows, Class cls);
	
	//
	public String querySavePath(String title);
}
