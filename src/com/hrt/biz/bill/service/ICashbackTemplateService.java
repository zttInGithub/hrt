package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.pagebean.CashbackTemplateBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface ICashbackTemplateService {

	/**
	 * 中心及一代税点查询
	 * @param cashbackTemplateBean
	 * @param userSession 
	 * @return
	 */
	DataGridBean queryCashbackTaxPoint(CashbackTemplateBean cashbackTemplateBean, UserBean userSession);

	/**
	 * 中心及一代税点导出
	 * @param cashbackTemplateBean
	 * @param userSession 
	 * @return
	 */
	List<Map<String, Object>> reportCashbackTaxPoint(CashbackTemplateBean cashbackTemplateBean, UserBean userBean);

	/**
	 * 中心及一代税点根据id查询
	 * @param cashbackTemplateBean
	 * @param userSession 
	 * @return
	 */
	Map queryCashbackTaxPointForId(CashbackTemplateBean cashbackTemplateBean, UserBean userSession);

	/**
	 * 中心及一代税点修改
	 * @param cashbackTemplateBean
	 * @param userSession 
	 * @return
	 */
	String updateCashbackTaxPoint(CashbackTemplateBean cashbackTemplateBean, UserBean userSession);

	/**
	 * 中心及一代税点批量创建与变更
	 * @param cashbackTemplateBean
	 * @param userSession 
	 * @return
	 */
	String importCashbackTaxPointUpdate(String xlsfile, String fileName, UserBean user);

	/**
	 * 下级代理返现设置记录查询
	 * @param cashbackTemplateBean
	 * @param userSession
	 * @return
	 */
	DataGridBean queryCashbackTemplateLog(CashbackTemplateBean cashbackTemplateBean, UserBean userSession);

	/**
	 * 下级代理返现设置记录导出
	 * @param cashbackTemplateBean
	 * @param userSession
	 * @return
	 */
	List<Map<String, Object>> reportCashbackTemplateLog(CashbackTemplateBean cashbackTemplateBean, UserBean userBean);

	/**
	 * 查询下级代理机构号
	 * @param user
	 * @param nameCode
	 * @return
	 */
	DataGridBean getChildrenUnit(UserBean user, String nameCode);

	/**
	 * 下级代理返现模板机构查询
	 * @param cashbackTemplateBean
	 * @param userSession
	 * @return
	 */
	DataGridBean queryCashbackTemplateUnno(CashbackTemplateBean cashbackTemplateBean, UserBean userSession);

	/**
	 * 根据机构查询其所有返现模板
	 * @param cashbackTemplateBean
	 * @param userSession
	 * @return
	 */
	List<Map<String, Object>> queryCashbackTemplateForUnno(CashbackTemplateBean cashbackTemplateBean, UserBean userSession);

	/**
	 * 校验返现模板填写不为空
	 * @param cashbackTemplateBean
	 * @param userSession 
	 * @param i 
	 * @return
	 */
	String validateCashbackTemplateNotEmpty(CashbackTemplateBean cashbackTemplateBean, UserBean userSession, int i);

	/**
	 * 修改返现模板下月记录
	 * @param cashbackTemplateBean
	 * @param userSession
	 */
	void updateCashbackTemplateNext(CashbackTemplateBean cashbackTemplateBean, UserBean userSession);

	/**
	 * 添加返现模板
	 * @param cashbackTemplateBean
	 * @param userSession
	 */
	void addCashbackTemplateNext(CashbackTemplateBean cashbackTemplateBean, UserBean userSession);

	/**
	 * 删除返现模板
	 * @param cashbackTemplateBean
	 * @param userSession
	 */
	void deleteCashbackTemplate(CashbackTemplateBean cashbackTemplateBean, UserBean userSession);
	/**
	 * 查询活动类型配置
	 */
	DataGridBean listRebateRate(CashbackTemplateBean cashbackTemplateBean, UserBean userSession);

	/**
     * 查询活动返现类型
     */
	DataGridBean queryRebatetypeCashbackType(String type);

	/**
	 * 返现明细查询
	 */
	DataGridBean queryCashbackDetail(CashbackTemplateBean cashbackTemplateBean, UserBean userSession);

	/**
	 * 返现明细导出
	 */
	List<Map<String, Object>> reportCashbackDetail(CashbackTemplateBean cashbackTemplateBean, UserBean userBean);

}
