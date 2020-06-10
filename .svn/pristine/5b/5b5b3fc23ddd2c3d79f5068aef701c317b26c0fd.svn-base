package com.hrt.biz.bill.dao;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTerminalInfoModel;
import com.hrt.frame.base.dao.IBaseHibernateDao;

public interface IMerchantTerminalInfoDao extends IBaseHibernateDao<MerchantTerminalInfoModel>{
	
	/**
	 * 方法功能：分页布放
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<MerchantTerminalInfoModel>
	 * 异常：
	 */
	List<MerchantTerminalInfoModel> queryMerchantTerminalInfo(String hql, Map<String, Object> param, Integer page, Integer rows);
	
	Integer getBmtid();
}
