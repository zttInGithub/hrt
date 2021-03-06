package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.entity.pagebean.TerminalInfoBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface ITerminalInfoService {
	
   void updateTerminalInfo(String status,String bid);
   DataGridBean queryAgentSales(TerminalInfoBean bean);

	List<Map<String, Object>> getUnitGodes(String unLvl, UserBean user);
	
	List<Map<String, Object>> getUnitGodesByQ(String nameCode,String unLvl, UserBean user);
	
	List<Map<String, Object>> getUnitGodes3(String unLvl, UserBean user);

	DataGridBean findConfirm(TerminalInfoBean bean);

	HSSFWorkbook exportConfirm(String ids);

	void editConfirm(String ids);

	DataGridBean findAllot(TerminalInfoBean bean,UserBean user);

	void editAllot(String params);
	String editTerminalRateInfo(TerminalInfoBean bean,String btids,UserBean user);
	/**
	 * 修改收银台设备费率
	 */
	boolean editTerminalRateInfo2(TerminalInfoBean bean,String btids,UserBean user);
	boolean queryTerminalStatusInfo(TerminalInfoBean bean,String btids,UserBean user);
	/**
	 * 查询rate/secondrate
	 */
	DataGridBean queryTerminalRateInfo(String nameCode) throws Exception;
	DataGridBean findUse(TerminalInfoBean bean, UserBean user);
	
	/**
	 * 非收银台设备费率调整查询
	 */
	DataGridBean findUse1(TerminalInfoBean bean, UserBean user);
	/**
	 * 收银台设备费率调整查询
	 */
	DataGridBean findUse2(TerminalInfoBean bean, UserBean user);
	
	DataGridBean searchTerminalInfo(String mid) throws Exception;
	
	DataGridBean searchTerminalInfoUnno(String unno) throws Exception;

	Integer addInfo(TerminalInfoBean bean, UserBean user, String params) throws Exception;
	
	boolean checkNum(String prams);
	DataGridBean searchTerminalInfoUnno2(String unno);
	List<Map<String, String>> saveM35SnInfo(String xlsfile,String name, UserBean user);
	List<Map<String, String>> saveAgentSnInfo(String xlsfile,String name, UserBean user);

	/**
	 * 进销存入库-明细入库
	 * @param PDID 采购PDID
	 * @param xlsfile 上传的excel文件
	 * @param name 文件名
	 * @param ORDERID 订单号
	 * @param MACHINEMODEL 机型
	 * @param snType 机型大类
	 * @param user 登录用户
	 * @param storage 库存
	 * @param oRDERMETHOD
	 * @param oRDERTYPE
	 * @return
	 */
	List<Map<String, String>> saveM35SnInfoPur(String PDID,String xlsfile,String name,String ORDERID,String MACHINEMODEL,String snType, UserBean user,String storage,String oRDERMETHOD, String oRDERTYPE);

	/**
	 * 进销存入库-明段入库
	 * @param PDID 采购PDID
	 * @param xlsfile 上传的excel文件
	 * @param name 文件名
	 * @param ORDERID 订单号
	 * @param MACHINEMODEL 机型
	 * @param snType 机型大类
	 * @param user 登录用户
	 * @param storage 库存
	 * @param oRDERMETHOD
	 * @param oRDERTYPE
	 * @return
	 */
	List<Map<String, String>> saveM35SnInfoPur2(String PDID,String xlsfile,String name,String ORDERID,String MACHINEMODEL,String snType, UserBean user,String storage,String oRDERMETHOD, String oRDERTYPE);

	Integer updateM35ForLend(String xlsfile,String name, UserBean user,String unno,String machineModel,String loadStorageNum,String storageMachineNum,String psid,String storageID);
	Integer updateM35ForAdd(String xlsfile,String name, UserBean user,String machineModel,String loadStorageNum,String storageMachineNum,String psid);
	Integer updateM35ForRed(String xlsfile,String name, UserBean user,String machineModel,String loadStorageNum,String storageMachineNum,String psid);
	Integer updateM35ForReturn(String xlsfile,String name, UserBean user,String pdlid,String machineModel);
	List<Map<String, String>> updateM35SnInfoPur(String xlsfile,String name,UserBean user,String psid,String outStorage,String inStorage,String storageMachineModel,Integer storageMachineNum,Integer loadStorageNum);
	Integer updateM35SnFlag(Integer type);
	//智能pose导入
	List<Map<String, String>> saveMerSnInfo(String xlsfile,String name, UserBean user);
	DataGridBean queryExportList(TerminalInfoBean bean, UserBean user);
	HSSFWorkbook exportTidInfo(TerminalInfoBean bean);
	void updateExportTidInfo(TerminalInfoBean bean);
	DataGridBean searchTerminalInfo2(TerminalInfoBean TerminalInfo, UserBean user);
	String gaveTerminalInfo(TerminalInfoBean bean, UserBean user);
	DataGridBean searchM35TerminalInfoUnno(String sn,String unno, String mid);
	Integer updateBackTerminalInfo(TerminalInfoBean bean, UserBean user);
	DataGridBean searchBackTerminalListInfo(TerminalInfoBean bean, UserBean user);
	List<Map<String, Object>> getUnitGodes2(String unLvl, UserBean user);
	DataGridBean findNoUseSnData(TerminalInfoBean bean, UserBean user);
	void deleteSnTerminalInfo(TerminalInfoBean bean);
	DataGridBean checkAddSn(TerminalInfoBean bean, UserBean user);
	void throughAllSn(String btids, UserBean user);
	void rejectAllSn(String btids, UserBean user);
	void updateAddSn(TerminalInfoBean bean);
	DataGridBean queryUpdateAddSn(TerminalInfoBean bean, UserBean user);
	void throughSn(TerminalInfoBean bean, UserBean user);
	void rejectSn(TerminalInfoBean bean, UserBean user);
    //未使用设备转移
	List<Map<String,String>> updateTerminalInfoUET (String xlsfile, UserBean user,String fileName);

	/**
	 * 进销存系统出库
	 * @param xlsfile
	 * @param user
	 * @param fileName
	 * @param orderID
	 * @param pdlid
	 * @param NEW_UNNO 分配机构号
	 * @param NEW_REBATETYPE 活动类型
	 * @param NEW_RATE 刷卡费率
	 * @param SCANRATE 扫码费率
	 * @param SECONRATE 手续费
	 * @param SCANRATEUP 扫码1000以上费率
	 * @param HUABEIRATE 花呗费率
	 * @param DEPOSITAMT 押金
	 * @return
	 */
	List<Map<String,String>> updateTerminalInfoJXC_chuku (String xlsfile, UserBean user,String fileName,String orderID,String pdlid,String NEW_UNNO,String NEW_REBATETYPE,double NEW_RATE,double SCANRATE,double SECONRATE,Double SCANRATEUP,Double HUABEIRATE,String DEPOSITAMT);

	List<Map<String, String>> updateM35SnInfoPur3(String pDID, String xlsfile, String fileName, String oRDERID,
			String mACHINEMODEL, String snType, UserBean user, String oRDERMETHOD, String oRDERTYPE, String uNNO)throws Exception;
	List<Map<String, String>> updateM35SnInfoPur2(String pDID, String xlsfile, String fileName, String oRDERID,
			String mACHINEMODEL, String snType, UserBean user, String oRDERMETHOD, String oRDERTYPE, String uNNO)throws Exception;

	DataGridBean listTerminalNumByMachineModel(TerminalInfoBean bean);
	DataGridBean findMerterminalInfo(TerminalInfoBean bean, UserBean user);
	Integer addMerterminalInfo(TerminalInfoBean bean, UserBean user);
	DataGridBean queryTerminalStorage(TerminalInfoBean bean, UserBean user);
	HSSFWorkbook exportTerminalStorage(TerminalInfoBean bean);
	boolean queryTerminalRebateTypeInfo(TerminalInfoBean bean, String btids, UserBean user);
	boolean queryTerminalRebateTypeInfoPlus(TerminalInfoBean bean, String btids, UserBean user);
	/**
	 * 查询限制90的不查出来
	 * */
	DataGridBean queryTerminalRatebyActiveInfo(String type, String active) throws Exception;
	/**
	 * 查询费率时，将minfo2的信息也查询出来
	 * */
	DataGridBean queryTerminalRateIncludeActiveInfo(String type) throws Exception;
	/**
	 * 查询活动配置
	 */
	public DataGridBean getListActivities();
	/**
	 * 注册商户活动类型统计报表
	 */
	DataGridBean queryRegisteredMerchantActivities(TerminalInfoBean bean, UserBean user);
	/**
	 * 注册商户活动类型统计报表---导出
	 */
	List<Map<String, Object>> exportRegisteredMerchantActivities(TerminalInfoBean bean, UserBean userSession);
	/**
	 * 特殊机构限制修改
	 */
	boolean queryTerminalLimitUnno(String limitUnno,String btids);
	
	/**
	 * 查询极速版费率
	 * */
	DataGridBean queryTerminalRateFeiLv();
	/**
	 * 查询极速版手续费
	 * */
	DataGridBean queryTerminalRateSXUFei(String type);
	/**
	 * 特殊政策-自定义费率保存
	 * */
	String editSpecialRateTerminalRateInfo(TerminalInfoBean bean, String btids, UserBean user);
	/**
	 * 特殊政策-判断修改sn费率的机构是否是特殊机构的自定义机构
	 * */
	String judgeIsSpecialRateUnno(String isActivety,String btids, UserBean userUnno);
	/**
	 * 特殊政策-判断修改终端是否属于特殊费率配置活动与中心
	 * */
	String judgeIsSpecialRateTer(TerminalInfoBean bean, String btids, UserBean user);
	/**
	 * 特殊政策-根据中心机构号、活动号查询详细费率信息
	 * */
	Map querySpecialRateDetail(String limitunno, String limitrebatetype, UserBean userSession);
	/**
	 * 特殊政策-根据中心机构号、活动号查询押金金额
	 * */
	DataGridBean querySpecialDepoist(String limitunno, String limitrebatetype);

	/**
	 * 查询sn设备信息是否被使用
	 * @param sns
	 * @return
	 */
    List<Map<String, Object>> queryTermIsUse(String sns);
}
