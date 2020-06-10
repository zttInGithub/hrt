package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.entity.model.MerchantTerminalInfoModel;
import com.hrt.biz.bill.entity.pagebean.MerchantTerminalInfoBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IMerchantTerminalInfoService {
	
	/**
	 * 添加
	 */
	Integer saveMerchantTerminalInfo(MerchantTerminalInfoBean merchantTerminalInfo, UserBean user, String[] values, Integer bmid, String mid) throws Exception;
	
	/**
	 * 查询待放行的布放
	 */
	DataGridBean queryMerchantTerminalInfoZ(MerchantTerminalInfoBean merchantTerminalInfo, UserBean user) throws Exception;
	/**
	 * 查询终端不分页,App使用
	 */
	DataGridBean queryMerchantTerminalInfoBmid(MerchantTerminalInfoBean merchantTerminalInfoBean) throws Exception;
	/**
	 * 查询终端不分页,平台使用
	 */
	DataGridBean queryMerchantTerminalInfoBmid(MerchantTerminalInfoBean merchantTerminalInfoBean,UserBean user) throws Exception;
	/**
	 * 查询终端不分页
	 */
	List<Map<String,Object>> queryMerchantTerminalInfoBmidPhone(MerchantTerminalInfoBean merchantTerminalInfoBean) throws Exception;

	/**
	 * 商户是否有激活设备
	 * @param merchantTerminalInfoBean
	 * @return 0:未激活  1:已激活
	 * @throws Exception
	 */
	Integer queryMerchantIsAct(MerchantTerminalInfoBean merchantTerminalInfoBean) throws Exception;
	/**
	 * 根据mid与tid查询当月是否激活
	 * @param merchantTerminalInfoBean
	 * @return 0:未激活 1:激活
	 * @throws Exception
	 */
	Map serachTerminalIsActByTid(MerchantTerminalInfoBean merchantTerminalInfoBean) throws Exception;
	/**
	 * 查询终端不分页（状态为待审批）
	 */
	DataGridBean queryMerchantTerminalInfoBmidZ(String mid) throws Exception;
	
	/**
	 * 开通
	 */
	void updateMerchantTerminalinfoY(MerchantTerminalInfoBean merchantTerminalInfo, UserBean user) throws Exception;
	/**
	 * 开通批量
	 */
	void updateMerchantTerminalinfosY(MerchantTerminalInfoBean merchantTerminalInfo, UserBean user,String [] ids) throws Exception;
	
	/**
	 * 退回
	 */
	void updateMerchantTerminalinfoK(MerchantTerminalInfoBean merchantTerminalInfo, UserBean user) throws Exception;
	
	/**
	 * 根据Mid查询相应的布放
	 */
	DataGridBean queryMerchantTerminalInfoMid(MerchantTerminalInfoBean merchantTerminalInfo, String unno) throws Exception;
	
	/**
	 * 布放查询
	 */
	DataGridBean queryMerchantTerminalInfo(MerchantTerminalInfoBean merchantTerminalInfo,String unno) throws Exception;

	/**
	 * 导出
	 */
	HSSFWorkbook export(String ids);

	/**
	 * 添加终端（页面增机申请）
	 */
	Integer saveInfo(MerchantTerminalInfoBean merchantTerminalInfo,UserBean userSession, String[] values);
	/**
	 * 一清增机导入（页面增机申请）
	 */
	List<Object> addMerchantTerminalInfoImport(String xlsfile, UserBean user, String fileName);
	
	/**
	 * 根据id得到终端信息
	 */
	MerchantTerminalInfoModel getInfoModel(Integer id);
	
	/**
	 * 判断终端号是否已被使用
	 */
	int TerminalInfoTerMID(String terMID) throws Exception;
	
	/**
	 * 删除终端
	 */
	void deleteMerchantTerminalInfo(Integer bmtid,Integer userID,String terMID) throws Exception;
	
	/**
	 * 根据Mid查询终端显示到相应的select
	 */
	DataGridBean searchMerchantTerminalInfo(String nameCode, String mid, String type) throws Exception;
	
	/**
	 * 根据BMTID查询原机型信息
	 */
	Map<String, String> searchMerchantTerminalInfoBMTID(Integer bmtid) throws Exception;

	void updateMerchantTerminalInfo(
			MerchantTerminalInfoBean merchantTerminalInfo);

	boolean queryMachineInfo(String id);

	void saveMerchantMicroTerminalInfo(
			MerchantTerminalInfoBean merchantTerminalInfo, UserBean userBean,
			String[] values, int parseInt, String mid) throws Exception;

	HSSFWorkbook exportAddTerminalInfo();

	HSSFWorkbook exportAddTerminalInfoSelected(String ids);
	
}