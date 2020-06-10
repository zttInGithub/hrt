package com.hrt.biz.check.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hrt.biz.check.entity.pagebean.CheckRebateBean;
import com.hrt.biz.check.service.CheckRebateService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.opensymphony.xwork2.ModelDriven;


public class CheckRebateAction extends BaseAction implements ModelDriven<CheckRebateBean> {

	/**
	 *	@author SL 
	 *	2018-07-12
	 *	激活返利情况
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(CheckRebateAction.class);
	
	private CheckRebateBean checkRebateBean =new CheckRebateBean();
	private CheckRebateService checkRebateService;
	
	@Override
	public CheckRebateBean getModel() {
		return checkRebateBean;
	}
	
	public CheckRebateService getCheckRebateService() {
		return checkRebateService;
	}

	public void setCheckRebateService(CheckRebateService checkRebateService) {
		this.checkRebateService = checkRebateService;
	}
	
	//----------------------------------业务处理-------------------------------//
	/**
	 * 查询活动3/13 商户返利明细
	 */
	public void queryRebate3_mer(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			if(null!=checkRebateBean.getKeyDay() &&!"".equals(checkRebateBean.getKeyDay()) ||
			   null!=checkRebateBean.getKeyDay1() &&!"".equals(checkRebateBean.getKeyDay1()) ||
			   null!=checkRebateBean.getTxnDay() &&!"".equals(checkRebateBean.getTxnDay())){
				dgb=checkRebateService.queryRebate3_mer(checkRebateBean,user);
			}
		} catch (Exception e) {
			log.info(e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 导出活动3/13 商户返利明细
	 */
	public void exportRebate3Excel_mer(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		try{
			List<Map<String, Object>> list = checkRebateService.exportRebate3_mer(checkRebateBean, user);
			List<String[]> excelList = new ArrayList<String[]>();
			String title [] = {"一代机构","归属分公司","商户编码","SN号","型号","出售日期","出售月","激活日期","A月+3扣款","A月+4扣款",
			"A月+5扣款","扣款汇总","是否可返","返利金额","返利月","交易金额","交易笔数","活动类型"};
			excelList.add(title);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				String rowContents[] = {
						map.get("UNNO1")==null?"":map.get("UNNO1").toString(),
						map.get("UNNO2")==null?"":map.get("UNNO2").toString(),
						map.get("MID")==null?"":map.get("MID").toString(),
						map.get("SN")==null?"":map.get("SN").toString(),
						map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
						map.get("KDATE")==null?"":map.get("KDATE").toString(),
						map.get("KMONTH")==null?"":map.get("KMONTH").toString(),
						map.get("UDATE")==null?"":map.get("UDATE").toString(),
						map.get("DEDUCT_A3")==null?"":map.get("DEDUCT_A3").toString(),
						map.get("DEDUCT_A4")==null?"":map.get("DEDUCT_A4").toString(),
						map.get("DEDUCT_A5")==null?"":map.get("DEDUCT_A5").toString(),
						map.get("DEDUCT_SUM")==null?"":map.get("DEDUCT_SUM").toString(),
						map.get("ISREBATE")==null?"":map.get("ISREBATE").toString(),
						map.get("REBATEAMT")==null?"":map.get("REBATEAMT").toString(),
						map.get("REBATEMONTH")==null?"":map.get("REBATEMONTH").toString(),
						map.get("SAMT")==null?"":map.get("SAMT").toString(),
						map.get("NUM")==null?"":map.get("NUM").toString(),
						map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString()
				};
				excelList.add(rowContents);
			}
			list=null;
			JxlOutExcelUtil.writeCSVFile(excelList, 18, super.getResponse(), "活动3,13商户返利明细表.csv");
			excelList=null;
			json.setSuccess(true);
			json.setMsg("导出活动3/13商户返利Excel成功");
		} catch (Exception e) {
			log.error("导出活动3/13商户返利Excel异常：" + e);
			json.setMsg("导出活动3/13商户返利Excel失败");
			json.setSuccess(false);
		}
	}
	/**
	 * 查询活动3/13 机构汇总返利
	 */
	public void queryRebate3_unno(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			if(null!=checkRebateBean.getTxnDay() && !"".equals(checkRebateBean.getTxnDay())){
				dgb=checkRebateService.queryRebate3_unno(checkRebateBean,user);
			}
		} catch (Exception e) {
			log.info(e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 导出活动3/13 机构汇总返利
	 */
	public void exportRebate3Excel_unno(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		try{
			List<Map<String, Object>> list = checkRebateService.exportRebate3_unno(checkRebateBean, user);
			List<String[]> excelList = new ArrayList<String[]>();
			String title [] = {"一代机构","归属分公司","扣款汇总","扣款SN台数","返利金额","返利SN台数","活动类型"};
			excelList.add(title);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				String rowContents[] = {
						map.get("UNNO1")==null?"":map.get("UNNO1").toString(),
						map.get("UNNO2")==null?"":map.get("UNNO2").toString(),
						map.get("DEDUCT_AMT")==null?"":map.get("DEDUCT_AMT").toString(),
						map.get("DEDUCT_SN")==null?"":map.get("DEDUCT_SN").toString(),
						map.get("REBATEAMT")==null?"":map.get("REBATEAMT").toString(),
						map.get("REBATENUM")==null?"":map.get("REBATENUM").toString(),
						map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString()
				};
				excelList.add(rowContents);
			}
			list=null;
			JxlOutExcelUtil.writeCSVFile(excelList, 7, super.getResponse(), "活动3,13机构汇总表.csv");
			excelList=null;
			json.setSuccess(true);
			json.setMsg("导出活动3,13机构汇总Excel成功");
		} catch (Exception e) {
			log.error("导出活动3,13机构汇总Excel异常：" + e);
			json.setMsg("导出活动3,13机构汇总Excel失败");
			json.setSuccess(false);
		}
	}
	/**
	 * 查询活动9 商户返利明细
	 */
	public void queryRebate9_mer(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			if(null!=checkRebateBean.getKeyDay() &&!"".equals(checkRebateBean.getKeyDay()) ||
			   null!=checkRebateBean.getKeyDay1() &&!"".equals(checkRebateBean.getKeyDay1()) ||
			   null!=checkRebateBean.getTxnDay() &&!"".equals(checkRebateBean.getTxnDay())){
				dgb=checkRebateService.queryRebate9_mer(checkRebateBean,user);
			}
		} catch (Exception e) {
			log.info(e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 导出活动9 商户返利明细
	 */
	public void exportRebate9Excel_mer(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		try{
			List<Map<String, Object>> list = checkRebateService.exportRebate9_mer(checkRebateBean, user);
			List<String[]> excelList = new ArrayList<String[]>();
			String title [] = {"一代机构","归属分公司","商户编码","SN号","型号","类别","出售日期","出售月","激活日期",
					"激活阶段","返利月","返利金额","活动类型","交易金额","交易笔数"};
			excelList.add(title);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				if("1".equals(map.get("SN_TYPE"))){
					map.put("SN_TYPE", "小蓝牙");
				}else{
					map.put("SN_TYPE", "大蓝牙");
				}
				String rowContents[] = {
						map.get("UNNO1")==null?"":map.get("UNNO1").toString(),
						map.get("UNNO2")==null?"":map.get("UNNO2").toString(),
						map.get("MID")==null?"":map.get("MID").toString(),
						map.get("SN")==null?"":map.get("SN").toString(),
						map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
						map.get("SN_TYPE")==null?"":map.get("SN_TYPE").toString(),
						map.get("KDATE")==null?"":map.get("KDATE").toString(),
						map.get("KMONTH")==null?"":map.get("KMONTH").toString(),
						map.get("UDATE")==null?"":map.get("UDATE").toString(),
						map.get("ACTIVA_TYPE")==null?"":map.get("ACTIVA_TYPE").toString(),
						map.get("REBATEMONTH")==null?"":map.get("REBATEMONTH").toString(),
						map.get("REBATEAMT")==null?"":map.get("REBATEAMT").toString(),
						map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
						map.get("SAMT")==null?"":map.get("SAMT").toString(),
						map.get("NUM")==null?"":map.get("NUM").toString()
				};
				excelList.add(rowContents);
			}
			list=null;
			JxlOutExcelUtil.writeCSVFile(excelList, 15, super.getResponse(), "活动9商户明细表.csv");
			excelList=null;
			json.setSuccess(true);
			json.setMsg("导出活动9商户明细Excel成功");
		} catch (Exception e) {
			log.error("导出活动9商户明细Excel异常：" + e);
			json.setMsg("导出活动9商户明细Excel失败");
			json.setSuccess(false);
		}
	}
	/**
	 * 查询活动9 机构汇总返利
	 */
	public void queryRebate9_unno(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			if(null!=checkRebateBean.getTxnDay() && !"".equals(checkRebateBean.getTxnDay())){
				dgb=checkRebateService.queryRebate9_unno(checkRebateBean,user);
			}
		} catch (Exception e) {
			log.info(e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 导出活动9 机构汇总返利
	 */
	public void exportRebate9Excel_unno(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		try{
			List<Map<String, Object>> list = checkRebateService.exportRebate9_unno(checkRebateBean, user);
			List<String[]> excelList = new ArrayList<String[]>();
			String title [] = {"一代机构","归属分公司","返利金额","返利SN台数","活动类型"};
			excelList.add(title);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				String rowContents[] = {
						map.get("UNNO1")==null?"":map.get("UNNO1").toString(),
						map.get("UNNO2")==null?"":map.get("UNNO2").toString(),
						map.get("REBATEAMT")==null?"":map.get("REBATEAMT").toString(),
						map.get("REBATENUM")==null?"":map.get("REBATENUM").toString(),
						map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString()
				};
				excelList.add(rowContents);
			}
			list=null;
			JxlOutExcelUtil.writeCSVFile(excelList, 5, super.getResponse(), "活动9机构汇总表.csv");
			excelList=null;
			json.setSuccess(true);
			json.setMsg("导出活动9机构汇总Excel成功");
		} catch (Exception e) {
			log.error("导出活动9机构汇总Excel异常：" + e);
			json.setMsg("导出活动9机构汇总Excel失败");
			json.setSuccess(false);
		}
	}
	/**
	 * 查询活动9 机构汇总返利-补差价
	 */
	public void queryRebate9_unno1(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			if(null!=checkRebateBean.getTxnDay() && !"".equals(checkRebateBean.getTxnDay())){
				dgb=checkRebateService.queryRebate9_unno1(checkRebateBean,user);
			}
		} catch (Exception e) {
			log.info(e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 导出活动9 机构汇总返利-补差价
	 */
	public void exportRebate9Excel_unno1(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		try{
			List<Map<String, Object>> list = checkRebateService.exportRebate9_unno1(checkRebateBean, user);
			List<String[]> excelList = new ArrayList<String[]>();
			String title [] = {"一代机构","归属分公司","SN总数","激活阶段1SN总数","激活率","补差价金额","返利金额","活动类型"};
			excelList.add(title);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				String rowContents[] = {
						map.get("UNNO1")==null?"":map.get("UNNO1").toString(),
						map.get("UNNO2")==null?"":map.get("UNNO2").toString(),
						map.get("SUM_SN")==null?"":map.get("SUM_SN").toString(),
						map.get("ACTIVA_SN")==null?"":map.get("ACTIVA_SN").toString(),
						map.get("ACTIVA_RATE")==null?"":map.get("ACTIVA_RATE").toString(),
						map.get("COMPENSA_AMT")==null?"":map.get("COMPENSA_AMT").toString(),
						map.get("REBATEAMT")==null?"":map.get("REBATEAMT").toString(),
						map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString()
				};
				excelList.add(rowContents);
			}
			list=null;
			JxlOutExcelUtil.writeCSVFile(excelList, 8, super.getResponse(), "活动9补差价表.csv");
			excelList=null;
			json.setSuccess(true);
			json.setMsg("导出活动9补差价Excel成功");
		} catch (Exception e) {
			log.error("导出活动9补差价Excel异常：" + e);
			json.setMsg("导出活动9补差价Excel失败");
			json.setSuccess(false);
		}
	}
	/**
	 * 查询活动11 商户返利明细
	 */
	public void queryRebate11_mer(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			if(null!=checkRebateBean.getKeyDay() &&!"".equals(checkRebateBean.getKeyDay()) ||
			   null!=checkRebateBean.getKeyDay1() &&!"".equals(checkRebateBean.getKeyDay1()) ||
			   null!=checkRebateBean.getTxnDay() &&!"".equals(checkRebateBean.getTxnDay())){
				dgb=checkRebateService.queryRebate11_mer(checkRebateBean,user);
			}
		} catch (Exception e) {
			log.info(e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 导出活动11 商户返利明细
	 */
	public void exportRebate11Excel_mer(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		try{
			List<Map<String, Object>> list = checkRebateService.exportRebate11_mer(checkRebateBean, user);
			List<String[]> excelList = new ArrayList<String[]>();
			String title [] = {"一代机构","归属分公司","商户编码","SN号","型号","类别","出售日期","出售月","激活日期","A月+3扣款",
			"A月+4扣款","A月+5扣款","扣款汇总","是否可返","返利金额","返利月","返利阶段1金额","返利阶段2金额","返利阶段1返利月份",
			"返利阶段2返利月份","交易金额","交易笔数","活动类型"};
			excelList.add(title);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				if("1".equals(map.get("SN_TYPE"))){
					map.put("SN_TYPE", "小蓝牙");
				}else{
					map.put("SN_TYPE", "大蓝牙");
				}
				String rowContents[] = {
						map.get("UNNO1")==null?"":map.get("UNNO1").toString(),
						map.get("UNNO2")==null?"":map.get("UNNO2").toString(),
						map.get("MID")==null?"":map.get("MID").toString(),
						map.get("SN")==null?"":map.get("SN").toString(),
						map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
						map.get("SN_TYPE")==null?"":map.get("SN_TYPE").toString(),
						map.get("KDATE")==null?"":map.get("KDATE").toString(),
						map.get("KMONTH")==null?"":map.get("KMONTH").toString(),
						map.get("UDATE")==null?"":map.get("UDATE").toString(),
						map.get("DEDUCT_A3")==null?"":map.get("DEDUCT_A3").toString(),
						map.get("DEDUCT_A4")==null?"":map.get("DEDUCT_A4").toString(),
						map.get("DEDUCT_A5")==null?"":map.get("DEDUCT_A5").toString(),
						map.get("DEDUCT_SUM")==null?"":map.get("DEDUCT_SUM").toString(),
						map.get("ISREBATE")==null?"":map.get("ISREBATE").toString(),
						map.get("REBATEAMT")==null?"":map.get("REBATEAMT").toString(),
						map.get("REBATEMONTH")==null?"":map.get("REBATEMONTH").toString(),
						map.get("REBATE1_AMT")==null?"":map.get("REBATE1_AMT").toString(),
						map.get("REBATE2_AMT")==null?"":map.get("REBATE2_AMT").toString(),
						map.get("REBATE1_MONTH")==null?"":map.get("REBATE1_MONTH").toString(),
						map.get("REBATE2_MONTH")==null?"":map.get("REBATE2_MONTH").toString(),
						map.get("SAMT")==null?"":map.get("SAMT").toString(),
						map.get("NUM")==null?"":map.get("NUM").toString(),
						map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString()
				};
				excelList.add(rowContents);
			}
			list=null;
			JxlOutExcelUtil.writeCSVFile(excelList, 23, super.getResponse(), "活动11商户明细表.csv");
			excelList=null;
			json.setSuccess(true);
			json.setMsg("导出活动11商户明细Excel成功");
		} catch (Exception e) {
			log.error("导出活动11商户明细Excel异常：" + e);
			json.setMsg("导出活动11商户明细Excel失败");
			json.setSuccess(false);
		}
	}
	/**
	 * 查询活动11 机构汇总返利
	 */
	public void queryRebate11_unno(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			if(null!=checkRebateBean.getTxnDay() && !"".equals(checkRebateBean.getTxnDay())){
				dgb=checkRebateService.queryRebate11_unno(checkRebateBean,user);
			}
		} catch (Exception e) {
			log.info(e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 导出活动11 机构汇总返利
	 */
	public void exportRebate11Excel_unno(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		try{
			List<Map<String, Object>> list = checkRebateService.exportRebate11_unno(checkRebateBean, user);
			List<String[]> excelList = new ArrayList<String[]>();
			String title [] = {"一代机构","归属分公司","扣款汇总","扣款SN台数","返利金额","返利SN台数","活动类型"};
			excelList.add(title);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				String rowContents[] = {
						map.get("UNNO1")==null?"":map.get("UNNO1").toString(),
						map.get("UNNO2")==null?"":map.get("UNNO2").toString(),
						map.get("DEDUCT_AMT")==null?"":map.get("DEDUCT_AMT").toString(),
						map.get("DEDUCT_SN")==null?"":map.get("DEDUCT_SN").toString(),
						map.get("REBATEAMT")==null?"":map.get("REBATEAMT").toString(),
						map.get("REBATENUM")==null?"":map.get("REBATENUM").toString(),
						map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString()
				};
				excelList.add(rowContents);
			}
			list=null;
			JxlOutExcelUtil.writeCSVFile(excelList, 7, super.getResponse(), "活动11机构汇总表.csv");
			excelList=null;
			json.setSuccess(true);
			json.setMsg("导出活动11机构汇总Excel成功");
		} catch (Exception e) {
			log.error("导出活动11机构汇总Excel异常：" + e);
			json.setMsg("导出活动11机构汇总Excel失败");
			json.setSuccess(false);
		}
	}
	
	/**
	 * 查询活动2商户明细
	 */
	public void queryRebate2Detail(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else{
			try {
				if(null!=checkRebateBean.getTxnDay() && !"".equals(checkRebateBean.getTxnDay())){
					dgb=checkRebateService.queryRebate2Detail(checkRebateBean);
				}
			} catch (Exception e) {
				log.error("查询活动2商户明细异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	/**
	 * 查询活动2汇总
	 */
	public void queryRebate2Summary(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else{
			try {
				if(null!=checkRebateBean.getTxnDay() && !"".equals(checkRebateBean.getTxnDay())){
					dgb=checkRebateService.queryRebate2Summary(checkRebateBean);
				}
			} catch (Exception e) {
				log.error("查询活动2汇总异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询活动10商户明细
	 */
	public void queryRebate10Detail(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else{
			try {
				if(null!=checkRebateBean.getTxnDay() && !"".equals(checkRebateBean.getTxnDay())){
					dgb=checkRebateService.queryRebate10Detail(checkRebateBean);
				}
			} catch (Exception e) {
				log.error("查询活动10商户明细异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	/**
	 * 查询活动10汇总
	 */
	public void queryRebate10Summary(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else{
			try {
				if(null!=checkRebateBean.getTxnDay() && !"".equals(checkRebateBean.getTxnDay())){
					dgb=checkRebateService.queryRebate10Summary(checkRebateBean);
				}
			} catch (Exception e) {
				log.error("查询活动10汇总异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	/**
	 * 查询活动12商户明细
	 */
	public void queryRebate12Detail(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else{
			try {
				if(null!=checkRebateBean.getTxnDay() && !"".equals(checkRebateBean.getTxnDay())){
					dgb=checkRebateService.queryRebate12Detail(checkRebateBean);
				}
			} catch (Exception e) {
				log.error("查询活动12商户明细异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	/**
	 * 查询活动12汇总
	 */
	public void queryRebate12Summary(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else{
			try {
				if(null!=checkRebateBean.getTxnDay() && !"".equals(checkRebateBean.getTxnDay())){
					dgb=checkRebateService.queryRebate12Summary(checkRebateBean);
				}
			} catch (Exception e) {
				log.error("查询活动12汇总异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	/**
	 * 导出活动2商户明细
	 */
	public void exportRebate2Detail(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String, Object>> list = checkRebateService.exportRebate2Detail(checkRebateBean);
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"一代机构", "归属分公司", "商户编号", "SN号", "型号","类别","出售日期", "出售月", "激活日期","返利金额","活动类型","是否可返","返利月","交易金额","交易笔数"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					if("1".equals(map.get("SN_TYPE"))){
						map.put("SN_TYPE", "小蓝牙");
					}else{
						map.put("SN_TYPE", "大蓝牙");
					}
					String []rowContents ={
							map.get("YIDAI")==null?"":map.get("YIDAI").toString(),	
							map.get("YUNYING")==null?"":map.get("YUNYING").toString(),
							map.get("MID")==null?"":map.get("MID").toString(),
							map.get("SN")==null?"":map.get("SN").toString(),
							map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
							map.get("SN_TYPE")==null?"":map.get("SN_TYPE").toString(),
							map.get("KEYCONFIRMDATE")==null?"":map.get("KEYCONFIRMDATE").toString(),
							map.get("KEYCONFIRMMNOTH")==null?"":map.get("KEYCONFIRMMNOTH").toString(),
							map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
							map.get("REBATEAMT")==null?"":map.get("REBATEAMT").toString(),	
							map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
							map.get("ISREBATE")==null?"":map.get("ISREBATE").toString(),
							map.get("REBATEMONTH")==null?"":map.get("REBATEMONTH").toString(),
							map.get("SAMT")==null?"":map.get("SAMT").toString(),
							map.get("NUM")==null?"":map.get("NUM").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "活动2商户明细.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出活动2商户明细成功");
			}
		} catch (Exception e) {
			log.error("导出活动2商户明细异常：" + e);
		}
	}
	/**
	 * 导出活动2机构汇总
	 */
	public void exportRebate2Summary(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String, Object>> list = checkRebateService.exportRebate2Summary(checkRebateBean);
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"一代机构", "归属分公司", "返利金额", "返利台数", "活动类型"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					String []rowContents ={
							map.get("YIDAI")==null?"":map.get("YIDAI").toString(),	
							map.get("YUNYING")==null?"":map.get("YUNYING").toString(),
							map.get("REBATEAMT")==null?"":map.get("REBATEAMT").toString(),
							map.get("REBATENUM")==null?"":map.get("REBATENUM").toString(),
							map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "活动2机构汇总.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出活动2机构汇总成功");
			}
		} catch (Exception e) {
			log.error("导出活动2机构汇总异常：" + e);
		}
	}
	/**
	 * 导出活动10商户明细
	 */
	public void exportRebate10Detail(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String, Object>> list = checkRebateService.exportRebate10Detail(checkRebateBean);
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"一代机构", "归属分公司", "商户编号", "SN号", "型号","类别","出售日期", "激活月", "激活日期","返利阶段1金额","返利阶段2金额","返利阶段3金额","返利阶段1返利月份","返利阶段2返利月份","返利阶段3返利月份","活动类型","交易金额","交易笔数"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					if("1".equals(map.get("SN_TYPE"))){
						map.put("SN_TYPE", "小蓝牙");
					}else{
						map.put("SN_TYPE", "大蓝牙");
					}
					String []rowContents ={
							map.get("YIDAI")==null?"":map.get("YIDAI").toString(),	
							map.get("YUNYING")==null?"":map.get("YUNYING").toString(),
							map.get("MID")==null?"":map.get("MID").toString(),
							map.get("SN")==null?"":map.get("SN").toString(),
							map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
							map.get("SN_TYPE")==null?"":map.get("SN_TYPE").toString(),
							map.get("KEYCONFIRMDATE")==null?"":map.get("KEYCONFIRMDATE").toString(),
							map.get("USEDCONFIRMMONTH")==null?"":map.get("USEDCONFIRMMONTH").toString(),
							map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
							map.get("REBATE1_AMT")==null?"":map.get("REBATE1_AMT").toString(),	
							map.get("REBATE2_AMT")==null?"":map.get("REBATE2_AMT").toString(),
							map.get("REBATE3_AMT")==null?"":map.get("REBATE3_AMT").toString(),
							map.get("REBATE1_MONTH")==null?"":map.get("REBATE1_MONTH").toString(),	
							map.get("REBATE2_MONTH")==null?"":map.get("REBATE2_MONTH").toString(),
							map.get("REBATE3_MONTH")==null?"":map.get("REBATE3_MONTH").toString(),
							map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
							map.get("SAMT")==null?"":map.get("SAMT").toString(),
							map.get("NUM")==null?"":map.get("NUM").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "活动10商户明细.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出活动10商户明细成功");
			}
		} catch (Exception e) {
			log.error("导出活动10商户明细异常：" + e);
		}
	}
	/**
	 * 导出活动10机构汇总
	 */
	public void exportRebate10Summary(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String, Object>> list = checkRebateService.exportRebate10Summary(checkRebateBean);
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"一代机构", "归属分公司", "返利金额", "返利台数", "活动类型"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					String []rowContents ={
							map.get("YIDAI")==null?"":map.get("YIDAI").toString(),	
							map.get("YUNYING")==null?"":map.get("YUNYING").toString(),
							map.get("REBATEAMT")==null?"":map.get("REBATEAMT").toString(),
							map.get("REBATENUM")==null?"":map.get("REBATENUM").toString(),
							map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "活动10机构汇总.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出活动10机构汇总成功");
			}
		} catch (Exception e) {
			log.error("导出活动10机构汇总异常：" + e);
		}
	}
	/**
	 * 导出活动12商户明细
	 */
	public void exportRebate12Detail(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String, Object>> list = checkRebateService.exportRebate12Detail(checkRebateBean);
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"一代机构", "归属分公司", "商户编号", "SN号", "型号","类别","出售日期", "激活月", "激活日期","返利金额","活动类型","返利月","交易金额","交易笔数"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					if("1".equals(map.get("SN_TYPE"))){
						map.put("SN_TYPE", "小蓝牙");
					}else{
						map.put("SN_TYPE", "大蓝牙");
					}
					String []rowContents ={
							map.get("YIDAI")==null?"":map.get("YIDAI").toString(),	
							map.get("YUNYING")==null?"":map.get("YUNYING").toString(),
							map.get("MID")==null?"":map.get("MID").toString(),
							map.get("SN")==null?"":map.get("SN").toString(),
							map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
							map.get("SN_TYPE")==null?"":map.get("SN_TYPE").toString(),
							map.get("KEYCONFIRMDATE")==null?"":map.get("KEYCONFIRMDATE").toString(),
							map.get("USEDCONFIRMMONTH")==null?"":map.get("USEDCONFIRMMONTH").toString(),
							map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
							map.get("REBATEAMT")==null?"":map.get("REBATEAMT").toString(),	
							map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
							map.get("REBATEMONTH")==null?"":map.get("REBATEMONTH").toString(),
							map.get("SAMT")==null?"":map.get("SAMT").toString(),
							map.get("NUM")==null?"":map.get("NUM").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "活动12商户明细.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出活动12商户明细成功");
			}
		} catch (Exception e) {
			log.error("导出活动12商户明细异常：" + e);
		}
	}
	/**
	 * 导出活动12机构汇总
	 */
	public void exportRebate12Summary(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String, Object>> list = checkRebateService.exportRebate12Summary(checkRebateBean);
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"一代机构", "归属分公司", "返利金额", "返利台数", "活动类型"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					String []rowContents ={
							map.get("YIDAI")==null?"":map.get("YIDAI").toString(),	
							map.get("YUNYING")==null?"":map.get("YUNYING").toString(),
							map.get("REBATEAMT")==null?"":map.get("REBATEAMT").toString(),
							map.get("REBATENUM")==null?"":map.get("REBATENUM").toString(),
							map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "活动12机构汇总.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出活动12机构汇总成功");
			}
		} catch (Exception e) {
			log.error("导出活动12机构汇总异常：" + e);
		}
	}
}
