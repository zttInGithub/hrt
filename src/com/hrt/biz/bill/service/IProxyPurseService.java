package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.pagebean.ProxyPurseBean;
import com.hrt.frame.entity.pagebean.DataGridBean;

public interface IProxyPurseService {

	/**
	 * 分润调整查询
	 * @param proxyPurseBean
	 * @return
	 */
	DataGridBean queryUnnoAdj(ProxyPurseBean proxyPurseBean);

	/**
	 * 导出分润调整查询
	 * @param proxyPurseBean
	 * @return
	 */
	List<Map<String, Object>> reportUnnoAdj(ProxyPurseBean proxyPurseBean);

	/**
	 * 钱包实时余额Grid
	 * @param proxyPurseBean
	 * @return
	 */
	DataGridBean queryUnnoPurseBalanceGrid(ProxyPurseBean proxyPurseBean);

	/**
	 * 导出钱包实时余额Grid
	 * @param proxyPurseBean
	 * @return
	 */
	List<Map<String, Object>> exportUnnoPurseBalanceGrid(ProxyPurseBean proxyPurseBean);

	//机构查询
	List<Map<String, Object>> queryUnno(ProxyPurseBean proxyPurseBean);
	//分润钱包中机构查询（判断钱包中是否有此机构）
	List<Map<String, Object>> queryPurse(String unno,Integer walletType);
	//分润调整插入
	boolean addUnnAdj(ProxyPurseBean proxyPurseBean, String loginName, String string);
	//判断该批次是否已经上传
	List<Map<String, String>> queryPsmj(String fileName);
	//批量上传保存
	List<Map<String, Object>> saveUnnoAdj(String xlsfile, String loginName, String filename) throws Exception;

	/**
	 * 钱包一代额度查询统计---查询
	 */
	DataGridBean queryGenerationQuotaStatistics(ProxyPurseBean proxyPurseBean);
	/**
	 * 钱包一代额度查询统计---导出
	 */
	List<Map<String, Object>> reportGenerationQuotaStatistics(ProxyPurseBean proxyPurseBean);
	/**
	 *分活动钱包流水--查询
	 */
	DataGridBean queryPurseTurnoverRebatety(ProxyPurseBean proxyPurseBean);
	/**
	 *分活动钱包流水--导出
	 */
	List<Map<String, Object>> reportPurseTurnoverRebatety(ProxyPurseBean proxyPurseBean);
	/**
	 *分日钱包流水--查询
	 */
	DataGridBean queryPurseTurnoverDay(ProxyPurseBean proxyPurseBean);
	/**
	 *分日钱包流水--导出
	 */
	List<Map<String, Object>> reportPurseTurnoverDay(ProxyPurseBean proxyPurseBean);

}
