package com.hrt.biz.check.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.check.entity.pagebean.ToolDealBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface CheckUnitDealDataService {
	
	/**
	 * 方法功能：分页查询业务员资料
	 * 参数：agentSales 
	 * 返回值：DataGridBean实例
	 * 异常：
	 */
	DataGridBean queryCheckUnitDealData(ToolDealBean toolDealBean,UserBean userBean);
	DataGridBean queryIsM35RebateCheckData(ToolDealBean toolDealBean,UserBean userBean);
	DataGridBean queryIsM35RebateCheckData1(ToolDealBean toolDealBean,UserBean userBean);
	DataGridBean queryIsM35Rebate(ToolDealBean toolDealBean,UserBean userBean);
	List<Map<String, Object>> exportIsM35Rebate(ToolDealBean toolDealBean,UserBean userBean);
	List<String> addIsM35RebateCheckData(String xlsfile, UserBean user, String fileName, String rebateType);
	DataGridBean queryCheckUnitDealData_bill(ToolDealBean toolDealBean,UserBean userBean);
	public List<Map<String, String>> queryObjectsBySqlList(ToolDealBean toolDealBean, UserBean userBean);
	public List<Map<String, String>> queryObjectsBySqlList_bill(ToolDealBean toolDealBean,UserBean userBean);
	List<Map<String, String>> getOrder(ToolDealBean toolDealBean,UserBean userBean) throws Exception;
//	List<Map<String, String>> getdata(String txn1,String id,String txn,UserBean userBean) throws Exception;
	
	/**
	 * 业务员明细报表查询
	 */
	DataGridBean queryToolDealDatas(ToolDealBean toolDealBean, UserBean user) throws Exception;
	
	/**
	 * 业务员明细报表查询详细信息
	 */
	List<Map<String, String>> queryFindToolDealDatas(ToolDealBean toolDealBean) throws Exception;
	List<Map<String, String>> queryFindToolDealDatasE(ToolDealBean toolDealBean)throws Exception;
	 List<Map<String, String>> selectrname(ToolDealBean toolDealBean,UserBean userBean);
	/**
	 * 勾选导出
	 */
	public List<Map<String, String>> queryObjectsBySqlList_billxi(ToolDealBean toolDealBean,UserBean userBean);
	public List<Map<String, String>> queryObjectsBySqlList_billxi1(ToolDealBean toolDealBean,UserBean userBean);
	 List<Map<String, String>> queryObjectsBySqlList_billxiSum(ToolDealBean toolDealBean,UserBean userBean);
	DataGridBean findBeiJingAreaDealData(ToolDealBean toolDealBean,
			UserBean userSession);
	List<Map<String, String>> findBeiJingAreaDealData(ToolDealBean toolDealBean);
	DataGridBean queryYesDealDatas(ToolDealBean toolDealBean,UserBean userBean);
	DataGridBean findMposeUsage(ToolDealBean toolDealBean);
	DataGridBean queryMerchantRebateData(ToolDealBean toolDealBean, UserBean userSession);
	JsonBean queryCheckUnitDate(ToolDealBean toolDealBean) throws Exception;
	DataGridBean listUnitDealData(ToolDealBean toolDealBean, UserBean user);
	List<Map<String,Object>> listUnitDealDataTotal(ToolDealBean toolDealBean, UserBean user);
	List<Map<String,Object>> exportUnitDealData(ToolDealBean toolDealBean, UserBean user);

	/**
	 * 特殊活动交易汇总
	 * @param toolDealBean
	 * @param userBean
	 * @return
	 */
	DataGridBean listTotalSpTxnGrid(ToolDealBean toolDealBean, UserBean userBean);
}
