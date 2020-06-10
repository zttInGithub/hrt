package com.hrt.biz.check.service;

import java.util.List;
import java.util.Map;
import com.hrt.biz.check.entity.pagebean.CheckRebateBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface CheckRebateService {
	/**
	 * 查询活动3/13商户明细
	 */
	DataGridBean queryRebate3_mer(CheckRebateBean checkRebateBean,UserBean user);
	/**
	 * 导出活动3/13商户明细
	 */
	List<Map<String, Object>> exportRebate3_mer(CheckRebateBean checkRebateBean, UserBean user);
	/**
	 * 查询活动3/13机构汇总
	 */
	DataGridBean queryRebate3_unno(CheckRebateBean checkRebateBean,UserBean user);
	/**
	 * 导出活动3/13机构汇总
	 */
	List<Map<String, Object>> exportRebate3_unno(CheckRebateBean checkRebateBean, UserBean user);
	/**
	 * 查询活动9商户明细
	 */
	DataGridBean queryRebate9_mer(CheckRebateBean checkRebateBean,UserBean user);
	/**
	 * 导出活动9商户明细
	 */
	List<Map<String,Object>> exportRebate9_mer(CheckRebateBean checkRebateBean, UserBean user);
	/**
	 * 查询活动9机构汇总
	 */
	DataGridBean queryRebate9_unno(CheckRebateBean checkRebateBean,UserBean user);
	/**
	 * 导出活动9机构汇总
	 */
	List<Map<String, Object>> exportRebate9_unno(CheckRebateBean checkRebateBean, UserBean user);
	/**
	 * 查询活动9机构汇总-补差价
	 */
	DataGridBean queryRebate9_unno1(CheckRebateBean checkRebateBean,UserBean user);
	/**
	 * 导出活动9机构汇总-补差价
	 */
	List<Map<String, Object>> exportRebate9_unno1(CheckRebateBean checkRebateBean, UserBean user);
	/**
	 * 查询活动11商户明细
	 */
	DataGridBean queryRebate11_mer(CheckRebateBean checkRebateBean,UserBean user);
	/**
	 * 导出活动11商户明细
	 */
	List<Map<String, Object>> exportRebate11_mer(CheckRebateBean checkRebateBean, UserBean user);
	/**
	 * 查询活动11机构汇总
	 */
	DataGridBean queryRebate11_unno(CheckRebateBean checkRebateBean,UserBean user);
	/**
	 * 导出活动11机构汇总
	 */
	List<Map<String, Object>> exportRebate11_unno(CheckRebateBean checkRebateBean, UserBean user);

	DataGridBean queryRebate2Detail(CheckRebateBean checkRebateBean);
	DataGridBean queryRebate2Summary(CheckRebateBean checkRebateBean);
	DataGridBean queryRebate10Detail(CheckRebateBean checkRebateBean);
	DataGridBean queryRebate10Summary(CheckRebateBean checkRebateBean);
	DataGridBean queryRebate12Detail(CheckRebateBean checkRebateBean);
	DataGridBean queryRebate12Summary(CheckRebateBean checkRebateBean);
	List<Map<String, Object>> exportRebate2Detail(CheckRebateBean checkRebateBean);
	List<Map<String, Object>> exportRebate2Summary(CheckRebateBean checkRebateBean);
	List<Map<String, Object>> exportRebate10Detail(CheckRebateBean checkRebateBean);
	List<Map<String, Object>> exportRebate10Summary(CheckRebateBean checkRebateBean);
	List<Map<String, Object>> exportRebate12Detail(CheckRebateBean checkRebateBean);
	List<Map<String, Object>> exportRebate12Summary(CheckRebateBean checkRebateBean);
}
