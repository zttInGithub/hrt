package com.hrt.biz.check.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.check.entity.pagebean.ToolDealBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface CheckUnitDealDetailService {

	/**
	 * 方法功能：分页查询业务员资料
	 * 参数：agentSales 
	 * 返回值：DataGridBean实例
	 * 异常：
	 * @param userSession 
	 * @param toolDealBean 
	 */
	List<Map<String, String>> zerocountCheckListExport(ToolDealBean toolDealBean, UserBean userSession);
	 DataGridBean queryzerocountCheckdata(ToolDealBean toolDealBean,UserBean userBean);
	DataGridBean queryCheckUnitDealDetail(ToolDealBean toolDealBean,UserBean userBean);
	DataGridBean queryFindCheckUnitDealDetailPhone(ToolDealBean toolDealBean) throws Exception;
	List queryFindCheckUnitDealDetail(ToolDealBean toolDealBean)	throws Exception ;
	public List<Map<String, String>> selectrname(ToolDealBean toolDealBean,UserBean userBean);
	/*
	 * 待结算
	 */
	DataGridBean queryCheckUnitDealDetailNoClosing(ToolDealBean toolDealBean,UserBean userBean);
	public List<Map<String, String>> queryObjectsBySqlList(ToolDealBean toolDealBean, UserBean userBean);
	List<Map<String, String>> queryFindCheckUnitDealE(ToolDealBean toolDealBean, UserBean userBean)throws Exception;
	public List<Map<String, String>> queryObjectsBySqlListSum(ToolDealBean toolDealBean, UserBean userSession);
	DataGridBean queryCheckUnitTxnSum(ToolDealBean toolDealBean,UserBean userSession) throws Exception;
	/**
	 * 钱包提现记录
	 */
	public DataGridBean queryCash(ToolDealBean toolDealBean,UserBean userBean) throws Exception;
	public List<Map<String, String>> exportWalletCash(ToolDealBean toolDealBean, UserBean userBean);
	HSSFWorkbook exportCheckUnitTxnSumData(ToolDealBean toolDealBean);

	DataGridBean queryCheckUnitTxnSumByLimit(ToolDealBean toolDealBean,UserBean userSession) throws Exception;
	DataGridBean queryFirstAgentProfitCollect(ToolDealBean toolDealBean, UserBean userSession);
	DataGridBean queryFirstAgentProfitCollect_20303(ToolDealBean toolDealBean, UserBean userSession);
	DataGridBean queryFirstAgentProfitScan(ToolDealBean toolDealBean, UserBean userSession);
	DataGridBean queryBigScanProfit(ToolDealBean toolDealBean, UserBean userSession);
	DataGridBean queryFirstAgentTransferAndGetCash(ToolDealBean toolDealBean, UserBean userSession);
	DataGridBean queryFirstAgentTransferAndGetCashSyt(ToolDealBean toolDealBean, UserBean userSession);
	DataGridBean queryFirstAgentSwipProfitDetail(ToolDealBean toolDealBean, UserBean userSession);
	DataGridBean queryFirstAgentSwipProfitDetail_20304(ToolDealBean toolDealBean, UserBean userSession);
	DataGridBean queryFirstAgentScanProfitDetail(ToolDealBean toolDealBean, UserBean userSession);
	DataGridBean queryBigScanProfitDetail(ToolDealBean toolDealBean, UserBean userSession);
	DataGridBean queryFirstAgentTransferAndGetCashProfitDetail(ToolDealBean toolDealBean, UserBean userSession);
	DataGridBean queryFirstAgentTransferAndGetCashProfitDetailSyt(ToolDealBean toolDealBean, UserBean userSession);
	public List<Map<String, String>> exportFirstAgentProfitCollect(ToolDealBean toolDealBean, UserBean userBean);
	public List<Map<String, String>> exportFirstAgentProfitCollect_20303(ToolDealBean toolDealBean, UserBean userBean);
	public List<Map<String, String>> exportFirstAgentProfitCollectDetail(ToolDealBean toolDealBean, UserBean userBean);
	public List<Map<String, String>> exportFirstAgentProfitCollectDetail_20304(ToolDealBean toolDealBean, UserBean userBean);
	List<Map<String, String>> exportFirstAgentProfitScan(ToolDealBean toolDealBean, UserBean userSession);
	List<Map<String, String>> exportBigScanProfit(ToolDealBean toolDealBean, UserBean userSession);
	List<Map<String, String>> exportFirstAgentScanProfitDetail(ToolDealBean toolDealBean, UserBean userBean);
	List<Map<String, String>> exportBigScanProfitDetail(ToolDealBean toolDealBean, UserBean userBean);
	List<Map<String, String>> exportFirstAgentTransferAndGetCash(ToolDealBean toolDealBean, UserBean userBean);
	List<Map<String, String>> exportFirstAgentTransferAndGetCashPlus(ToolDealBean toolDealBean, UserBean userBean);
	List<Map<String, String>> exportFirstAgentTransferAndGetCashSyt(ToolDealBean toolDealBean, UserBean userBean);
	List<Map<String, String>> exportFirstAgentTransferAndGetCashProfitDetail(ToolDealBean toolDealBean,
			UserBean userBean);
	List<Map<String, String>> exportFirstAgentTransferAndGetCashProfitDetailSyt(ToolDealBean toolDealBean,
			UserBean userBean);
	DataGridBean listUnitDealDetail(ToolDealBean toolDealBean, UserBean userBean);
	List<Map<String,Object>> listUnitDealDetailTotal(ToolDealBean toolDealBean, UserBean userBean);
	List<Map<String,Object>> exportUnitDealDetail(ToolDealBean toolDealBean, UserBean userBean);

	/**
	 * plus 代理商分润汇总
	 * @param toolDealBean
	 * @param userSession
	 * @return
	 */
	DataGridBean queryMposProfit(ToolDealBean toolDealBean, UserBean userSession);

	/**
	 * plus 代理商分润汇总导出
	 * @param toolDealBean
	 * @param user
	 * @return
	 */
	List<Map<String, Object>> exportMposProfit(ToolDealBean toolDealBean, UserBean user);

	/**
	 * plus 代理商提现汇总
	 * @param toolDealBean
	 * @param userBean
	 * @return
	 */
	DataGridBean queryTransferAndGetCashPlus(ToolDealBean toolDealBean, UserBean userBean);
	/**
	 * 代理汇总交易导出
	 * @param toolDealBean
	 * @param userBean
	 * @return
	 */
	
	List<Map<String, Object>> exportCheckUnitTxnSumDataByLimit(ToolDealBean toolDealBean, UserBean userSession);
}
