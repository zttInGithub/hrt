package com.hrt.biz.check.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.hrt.biz.check.entity.model.CheckProfitTemplateMicroModel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.check.entity.pagebean.CheckProfitTemplateMicroBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface CheckUnitProfitMicroService {
	/**
	 * 查询分润模版
	 */
	DataGridBean queryPROFITTEMPLATE(CheckProfitTemplateMicroBean cptm,UserBean userBean);
	public DataGridBean queryprofittemplateAll(CheckProfitTemplateMicroBean cptm,UserBean userBean) throws UnsupportedEncodingException;
	/**
	 * 查询添加模版是否重复
	 */
	public List<Map<String, String>> queryTempName(CheckProfitTemplateMicroBean cptm,UserBean user) throws UnsupportedEncodingException ;
	/**
	 * 添加分润模版
	 */
	public void addProfitmicro(CheckProfitTemplateMicroBean cptm, UserBean user);
	public void addSubTemplate(CheckProfitTemplateMicroBean cptm, UserBean user);

	/**
	 * 校验代还成本
	 * @param cptm
	 * @param user
	 * @param checkType 1:新增成本校验 2:修改成本校验
	 * @return
	 */
	String validateSubTemplate(CheckProfitTemplateMicroBean cptm, UserBean user,Integer checkType);

	/**
	 * 校验收银台成本
	 * @param cptm
	 * @param user
	 * @param checkType 1:新增时校验 2:修改时校验
	 * @return
	 */
	String validateSytTemplate(CheckProfitTemplateMicroBean cptm, UserBean user,Integer checkType);

	/**
	 * mpos模板校验
	 * @param cptm
	 * @param user
	 * @param checkType 1:新增时校验 2:修改时校验
	 * @return
	 */
    String validateMposTemplate(CheckProfitTemplateMicroBean cptm, UserBean user,Integer checkType);

	/**
	 * Plus模板校验
	 * @param cptm
	 * @param user
	 * @param checkType 1:新增时校验 2:修改是校验
	 * @return
	 */
	String validatePlusMposTempla(CheckProfitTemplateMicroBean cptm, UserBean user,Integer checkType);

	void updateSubTemplate(CheckProfitTemplateMicroBean cptm, UserBean user) throws Exception;
	void updateSytTemplate(CheckProfitTemplateMicroBean cptm, UserBean user) throws Exception;

	/**
	 * 查询代还分润模板列表
	 * @param cptm
	 * @param userBean
	 * @return
	 */
	DataGridBean querySubTemplate(CheckProfitTemplateMicroBean cptm,UserBean userBean);

	/**
	 *
	 * @param type 1:代还实时生效 2:代还下月生效
	 * @param cptm
	 * @return
	 */
	Map getReplayFirstDaySql(int type,CheckProfitTemplateMicroBean cptm);

	DataGridBean querySytTemplate(CheckProfitTemplateMicroBean cptm,UserBean userBean);
	/**
	 * 修改分润模版
	 */
	public CheckProfitTemplateMicroBean queryupdateProfitmicro(CheckProfitTemplateMicroBean cptm,UserBean user) throws Exception;

    /**
     * 手刷实时生效模板与下月生效模板查询
     * @param type 1:实时生效 2:下月生效
     * @param cptm
     * @return
     * @throws UnsupportedEncodingException
     */
    CheckProfitTemplateMicroBean getMdCheckProfitTemplateMicroBeanInfo(int type,CheckProfitTemplateMicroBean cptm) throws UnsupportedEncodingException;

	public void updateProfitmicro(CheckProfitTemplateMicroBean cptm, UserBean user) throws Exception;
	/**
	 * 删除分润模版
	 * @throws UnsupportedEncodingException
	 */
	public void DeleteProfitmicro(String TEMPNAME,UserBean user) throws UnsupportedEncodingException;
	public void deleteSubTemplateUnno(String unno);
	void deleteSytTemplateUnno(String unno);
	/**
	 * 查询下级代理
	 */
	public DataGridBean searchunno(String nameCode,UserBean user) throws Exception;

	DataGridBean queryUnitProfitMicroSumData(CheckProfitTemplateMicroBean cptm);

	DataGridBean queryUnitProfitMicroSumDataScan(CheckProfitTemplateMicroBean cptm);

	DataGridBean queryUnitProfitMicroSumDataCash(CheckProfitTemplateMicroBean cptm);

	DataGridBean queryUnitProfitMicroSumDataCash2(CheckProfitTemplateMicroBean cptm);

	DataGridBean queryUnitProfitMicroDetailData(CheckProfitTemplateMicroBean cptm);

	DataGridBean queryUnitProfitMicroDetailDataScan(CheckProfitTemplateMicroBean cptm);

	DataGridBean queryUnitSubTemplateDetailDataScan2(CheckProfitTemplateMicroBean cptm,UserBean user);

	DataGridBean queryUnitSytTemplateDetailDataScan2(CheckProfitTemplateMicroBean cptm,UserBean user);

	DataGridBean queryUnitSytTemplateDetailDataScan(CheckProfitTemplateMicroBean cptm,UserBean user);

	DataGridBean queryUnitSubTemplateDetailDataScan(CheckProfitTemplateMicroBean cptm,UserBean user);

	DataGridBean getProfitUnitGodes(UserBean user,String nameCode) throws Exception;

	DataGridBean getProfitUnitGodesForSales(UserBean user,String nameCode) throws Exception;

	HSSFWorkbook exportUnitProfitMicroDetailData(
			CheckProfitTemplateMicroBean cptm, UserBean userBean);

	HSSFWorkbook exportUnitProfitMicroDetailDataScan(
			CheckProfitTemplateMicroBean cptm, UserBean userBean);
	HSSFWorkbook exportUnitProfitMicroDetailDataScan3(
			CheckProfitTemplateMicroBean cptm, UserBean userBean);
	HSSFWorkbook exportUnitProfitMicroDetailDataScan2(
			CheckProfitTemplateMicroBean cptm, UserBean userBean);
	DataGridBean queryUnitProfitTempList(CheckProfitTemplateMicroBean cptm,
			UserBean user);
	DataGridBean querySubTemplateList(CheckProfitTemplateMicroBean cptm,
			UserBean user);
	DataGridBean querySytTemplateList(CheckProfitTemplateMicroBean cptm,UserBean user);
	List<Map<String, Object>> queryProfitTempByUnno(UserBean user);
	List<Map<String, Object>> querySubTemplateByUnno(UserBean user);
	List<Map<String, Object>> querySytTemplateByUnno(UserBean user);

	String saveUnitProfitTempInfo(CheckProfitTemplateMicroBean cptm, UserBean user);
	String saveSubTemplateInfo(CheckProfitTemplateMicroBean cptm, UserBean user);
	String saveSytTemplateInfo(CheckProfitTemplateMicroBean cptm, UserBean user);
	/**
	 * 判断分配模版是否重复
	 */
	public List<Map<String, Object>> queryUnno(CheckProfitTemplateMicroBean cptm);
	public List<Map<String, Object>> queryUnno2(CheckProfitTemplateMicroBean cptm);
	public List<Map<String, Object>> queryUnno3(CheckProfitTemplateMicroBean cptm);
	/**
	 * 删除分配分润模版
	 */
	public void DeleteProfitMicroUnno(String unno,String settMethod);

	/**
	 * plus活动分润模板添加
	 */
	void addMposTempla(CheckProfitTemplateMicroBean cptm, UserBean user);

	/**
	 * plus活动分润模板查询
	 * @param cptm
	 * @param userBean
	 * @return
	 */
	DataGridBean queryMposTemplate(CheckProfitTemplateMicroBean cptm,UserBean userBean);

	/**
	 * 修改分润模版
	 */
	List<Map<String, Object>> queryupdateMposProfitmicro(CheckProfitTemplateMicroBean cptm,UserBean user) throws Exception;
	/**
	 * 修改分润模版查询---下月
	 * @throws Exception
	 */
	List<Map<String, Object>> queryupdateMposProfitmicroNextMonth(CheckProfitTemplateMicroBean cptm,UserBean userSession) throws Exception;
	/**
	 * plus分润模板修改
	 * @param cptm
	 * @param user
	 * @throws Exception
	 */
	void updateMposTemplate(CheckProfitTemplateMicroBean cptm, UserBean user) throws Exception;

	/*
	 * MPOS活动模板已分配查询
	 */
	DataGridBean queryMposTemplateList(CheckProfitTemplateMicroBean cptm, UserBean user);
	List<Map<String, Object>> queryMposTemplateByUnno(UserBean user);
	List<Map<String, Object>> queryUnnoMpos(CheckProfitTemplateMicroBean cptm);
	String saveMposTemplateInfo(CheckProfitTemplateMicroBean cptm,UserBean user);
	void deleteMposTemplateUnno(String unno);

	/**
	 * plus活动模板分润汇总
	 * @param cptm
	 * @param user
	 * @return
	 */
	DataGridBean queryUnitMposTemplateDetailDataScan(CheckProfitTemplateMicroBean cptm,UserBean user);

	/**
	 * MPOS活动模板分润提现汇总
	 * @param cptm
	 * @return
	 */
	DataGridBean queryUnitMposProfitMicroSumDataCash(CheckProfitTemplateMicroBean cptm);

	/**
	 * 二代所有模板获取
	 * @param type 1:当前机构成本 2:当前机构的上级成本 3:当前机构的下级机构成本(2,3不支持)
	 * @param unno
	 * @param unLvl
	 * @param subType 1:新增模板校验,修改模板校验
	 * @return
	 */
    Map<String, CheckProfitTemplateMicroModel> getProfitTemplateMap(int type, String unno, Integer unLvl,int subType);

    /**
	 * 用来校验表单提交的是否有空值
	 * @param cptm
	 * @return
	 */
    String validatePlusTextNotEmpty(CheckProfitTemplateMicroBean cptm);
    /**
	 * 查询下月生效模板SYT
	 * @param aptid
     * @return
	 */
	List<Map<String, Object>> querySytNextMonthTemplate(CheckProfitTemplateMicroBean cptm, UserBean userSession);
	 /**
	 * 查询本月生效模板SYT
	 * @param aptid
     * @return
	 */
	List<Map<String, Object>> querySytCurrentMonthTemplate(CheckProfitTemplateMicroBean cptm, UserBean userSession);
	/**
	 * 代还月度成本查询
	 * @param cptt
	 * @param userBean
	 * @return
	 */
	DataGridBean getMicroProfitDhLog(CheckProfitTemplateMicroBean cptt,UserBean userBean);

	/**
	 * 代还月度成本导出
	 * @param cptt
	 * @param userBean
	 * @return
	 */
	List<Map<String, Object>> exportMicroProfitDhLog(CheckProfitTemplateMicroBean cptt,UserBean userBean);

	/**
	 * 收银台月度成本查询
	 */
	DataGridBean getMicroProfitSytLog(CheckProfitTemplateMicroBean cptm, UserBean userBean);
	/**
	 * 收银台月度成本导出
	 */
	List<Map<String, Object>> exportMicroProfitSytLog(CheckProfitTemplateMicroBean cptm, UserBean userBean);
	/**
	 * Mpos活动月度成本查询
	 */
	DataGridBean getMicroProfitMposTemplateLog(CheckProfitTemplateMicroBean cptm, UserBean userBean);
	/**
	 * Mpos活动月度成本查询----详细查询
	 */
	List<Map<String, Object>> queryupdateMposProfitmicroLogDetail(CheckProfitTemplateMicroBean cptm,
			UserBean userSession);
	/**
	 * Mpos活动月度成本导出
	 */
	List<Map<String, Object>> exportMicroProfitMposTempLateLog(CheckProfitTemplateMicroBean cptm, UserBean userSession);

	/**
	 * md分润月度成本查询
	 * @param cptt
	 * @param userBean
	 * @return
	 */
	DataGridBean getMicroProfitMdLog(CheckProfitTemplateMicroBean cptt,UserBean userBean);

	/**
	 * md分润月度成本详情
	 * @param cptt
	 * @return
	 */
	CheckProfitTemplateMicroBean getMicroProfitMdLogDetail(CheckProfitTemplateMicroBean cptt);

	List<CheckProfitTemplateMicroBean> exportMicroProfitMdLog(CheckProfitTemplateMicroBean cptt,UserBean userBean,Integer type);

	/**
	 * 一代 判断某机构号是否有某活动类型
	 * @param type 1:mpos活动 传0  2:大于等于活动20
	 * @param rebateType 活动
	 * @param unno 机构号
	 * @return
	 */
	boolean hasRebateTypeByYiDaiUnno(int type,String rebateType,String unno);

	/**
	 * 二代之后 判断某机构号是否有某活动类型
	 * @param type 1:mpos活动 传0  5:收银台活动20,21 6:大于活动21
	 * @param rebateType 活动
	 * @param unno 机构号
	 * @return
	 */
	boolean hasRebateTypeByUnno(int type,String rebateType,String unno);
	/**
     * Plus模板校验(校验绑定模板费率不能为空)
     * @return
     */
	String validatePlusMposTemplaIsNull(CheckProfitTemplateMicroBean cptm, UserBean user);
	/**
     * 收银台模板校验(绑定模板成本不能为空值)
     * @param cptm
     * @param user
     * @return
     */
	String validateSytTemplateIsNull(CheckProfitTemplateMicroBean cptm, UserBean user);
	/**
     * 特殊登陆机构显示历史交易记录时，查询他的下级机构
     */
	DataGridBean getProfitUnitGodesForSalesForSpecial(UserBean user,String nameCode) throws Exception;
	
	
	
	List<CheckProfitTemplateMicroBean> exportMicroProfitMdLog0312(CheckProfitTemplateMicroBean cptt, UserBean userBean,
			Integer type);
	
	
	DataGridBean listRebateRate();
	void addHybCashTempla(CheckProfitTemplateMicroBean cptm,UserBean userSession);
	String validateHybCashTextNotEmpty(CheckProfitTemplateMicroBean cptm);

	/**
	 * 会员宝收银台模板校验
	 * @param cptm
	 * @param userSession
	 * @param checkType 1:新增校验 2:修改校验
	 * @return
	 */
	String validateHybCashTempla(CheckProfitTemplateMicroBean cptm,UserBean userSession,Integer checkType);

	String validateSYTTextNotEmpty(CheckProfitTemplateMicroBean cpt);
	String validateSytTemplateNew(CheckProfitTemplateMicroBean cptm, UserBean user);
	List<Map<String, Object>> queryupdateSytProfitmicroLogDetail(CheckProfitTemplateMicroBean cptm,
			UserBean userSession);
	/**
	 * 代理商MPOS活动交易分润汇总导出
	 */
	HSSFWorkbook exportUnitMposTemplateDetailDataScan(CheckProfitTemplateMicroBean cptm, UserBean userBean);
	/**
	 * 代理商MPOS活动提现分润汇总导出
	 */
	HSSFWorkbook exportMposUnitProfitMicroSumDataCash(CheckProfitTemplateMicroBean cptm, UserBean userBean);
	/**
	 * 代理商收银台提现转账分润汇总导出
	 */
	HSSFWorkbook exportUnitProfitMicroSumDataCash2(CheckProfitTemplateMicroBean cptm, UserBean userBean);
	/**
	 * 代理商收银台分润汇总导出
	 */
	HSSFWorkbook exportUnitSytTemplateDetailDataScan(CheckProfitTemplateMicroBean cptm, UserBean userBean);
	/**
	 * 代理商手刷刷卡分润汇总导出
	 */
	HSSFWorkbook exportUnitProfitMicroSumData(CheckProfitTemplateMicroBean cptm, UserBean userBean);
	/**
	 * 代理商手刷扫码分润汇总导出
	 */
	HSSFWorkbook exportUnitProfitMicroSumDataScan(CheckProfitTemplateMicroBean cptm, UserBean userBean);
	/**
	 * 代理商手刷提现转账分润汇总导出
	 */
	HSSFWorkbook exportUnitProfitMicroSumDataCash(CheckProfitTemplateMicroBean cptm, UserBean userBean);
}
