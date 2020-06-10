package com.hrt.biz.bill.dao;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.frame.base.dao.IBaseHibernateDao;

public interface IMerchantInfoDao extends IBaseHibernateDao<MerchantInfoModel>{
	
	/**
	 * 方法功能：分页查询商户
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<MerchantInfoModel>
	 * 异常：
	 */
	List<MerchantInfoModel> queryMerchantInfo(String hql, Map<String, Object> param, Integer page, Integer rows);
	
	/**
	 * 方法功能：分页查询商户
	 * 参数：sql sql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<MerchantInfoModel>
	 * 异常：
	 */
	List<MerchantInfoModel> queryMerchantInfoSql(String sql, Map<String, Object> param, Integer page, Integer rows, Class cls);
	
	/**
	 * 查询上传文件路径
	 */
	String querySavePath(String title);
	
	/**
	 * 查询bmid
	 */
	Integer getBmid();

	/**
	 * 查询法人身份证号
	 */
	String queryLegalNumByMid(String mid);

	/**
	 * 查询禁用活动
	 */
	String queryNotUseActivity(String title);
	
}
