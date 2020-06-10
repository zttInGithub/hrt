package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.pagebean.ProducttypeInRebatetypeBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface ProducttypeInRebatetypeService {
	/**
	 * 查询活动产品对应关系
	 */
	DataGridBean queryProducttypeInRebatetype(ProducttypeInRebatetypeBean bean, UserBean userSession);
	/**
	 * 查询产品类型
	 */
	DataGridBean queryProducttype();
	/**
	 * 查询产品/活动关系表中是否存在此活动
	 */
	List queryRebatetype(ProducttypeInRebatetypeBean bean, UserBean userSession);
	/**
	 * 添加产品/活动关系
	 */
	void addProducttypeInRebatetype(ProducttypeInRebatetypeBean bean, UserBean userSession);
	/**
	 * 查询活动产品对应关系--根据id
	 */
	Map queryProducttypeForId(ProducttypeInRebatetypeBean bean, UserBean userSession);
	/**
	 * 修改产品/活动关系
	 */
	String updateProducttypeInRebatetype(ProducttypeInRebatetypeBean bean, UserBean userSession);
	/**
	 * 产品/活动关系---删除--根据id
	 */
	void delProducttypeInRebatetype(ProducttypeInRebatetypeBean bean, UserBean userSession);
	/**
	 * 设备状态统计
	 */
	DataGridBean queryTerminalStatistics(ProducttypeInRebatetypeBean bean, UserBean userSession);
	/**
	 * 查询活动类型--产品类型
	 */
	DataGridBean queryRebatetype();
	/**
	 * 设备状态统计---活动类型
	 */
	DataGridBean queryTerminalStatisticsForRebatetype(ProducttypeInRebatetypeBean bean, UserBean userSession);
	/**
	 * 导出设备状态统计---活动类型
	 */
	List<Map<String, Object>> exportTerminalStatisticsForRebatetype(ProducttypeInRebatetypeBean bean,
			UserBean userSession);
	/**
	 * 导出设备状态统计---产品类型
	 */
	List<Map<String, Object>> exportTerminalStatisticsForProducttype(ProducttypeInRebatetypeBean bean,
			UserBean userSession);
}
