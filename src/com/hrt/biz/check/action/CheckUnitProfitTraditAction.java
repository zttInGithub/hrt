package com.hrt.biz.check.action;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.hrt.util.JxlOutExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.check.entity.pagebean.CheckProfitTempTraditBean;
import com.hrt.biz.check.service.CheckUnitProfitTraditService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.service.sysadmin.IUnitService;
import com.opensymphony.xwork2.ModelDriven;

public class CheckUnitProfitTraditAction  extends BaseAction implements ModelDriven<CheckProfitTempTraditBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(CheckUnitProfitTraditAction.class);
	private String ids;
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	private CheckUnitProfitTraditService checkUnitProfitTraditService;
	
	private IUnitService unitService;

	public IUnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(IUnitService unitService) {
		this.unitService = unitService;
	}
	
	private CheckProfitTempTraditBean cptt=new CheckProfitTempTraditBean();

	public CheckUnitProfitTraditService getCheckUnitProfitTraditService() {
		return checkUnitProfitTraditService;
	}

	public void setCheckUnitProfitTraditService(
			CheckUnitProfitTraditService checkUnitProfitTraditService) {
		this.checkUnitProfitTraditService = checkUnitProfitTraditService;
	}

	@Override
	public CheckProfitTempTraditBean getModel() {
		return cptt;
	}
	/**
	 * 分润模版维护
	 */
	public void queryPROFITTEMPLATE(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dgb=checkUnitProfitTraditService.queryPROFITTEMPLATE(cptt,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询分润模版维护信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 传统模版明细
	 */
	public void queryprofittemplateAll(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dgb=checkUnitProfitTraditService.queryprofittemplateAll(cptt,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询传统模版明细信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 添加传统模版
	 */
	@SuppressWarnings("unchecked")
	public void addProfitTradit(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {			
			try {		
				List list= checkUnitProfitTraditService.queryTempName(cptt,(UserBean)userSession);
				if(list.size()>0){
					json.setSuccess(false);
					json.setMsg("该代理已存在分润模版，请不要重复添加！");
				}else{
					String errMsg = checkUnitProfitTraditService.validateProfitTradit(cptt, (UserBean)userSession,1);
					if(StringUtils.isNotEmpty(errMsg)){
						json.setSuccess(false);
						json.setMsg(errMsg);
					}else {
						checkUnitProfitTraditService.addProfitTradit(cptt, (UserBean) userSession);
						json.setSuccess(true);
						json.setMsg("添加分润模版成功");
					}
				}
			} catch (Exception e) {
				log.error("添加分润模版异常：" + e);
				json.setMsg("添加分润模版失败");
			}
		}
		super.writeJson(json);
	}
	/**
	 * 传统模版删除
	 */
	public void DelProfitTradit(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			checkUnitProfitTraditService.Delprofittradit(ids,(UserBean)userSession);
			json.setMsg("删除成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("删除失败！");
			json.setSuccess(true);
			log.error("上送类别查询异常：" + e);
		}
		super.writeJson(json);
	}
	/**
	 * 修改分润模版
	 */
	public void QUERYProfitTradit(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		List<CheckProfitTempTraditBean> list = new ArrayList<CheckProfitTempTraditBean>();
		try {				
			CheckProfitTempTraditBean aa=checkUnitProfitTraditService.queryupdateProfitTradit(cptt,(UserBean)userSession);
			list.add(aa);		
		} catch (Exception e) {
			log.error("修改分润模版异常：" + e);
		}
		super.writeJson(list);
	}

	/**
	 * 传统实时生效/下月生效分润模板获取
	 */
	public void queryProfitTraditNext(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		List<CheckProfitTempTraditBean> list = new ArrayList<CheckProfitTempTraditBean>();
		try {
			CheckProfitTempTraditBean aa=new CheckProfitTempTraditBean();
			int type = cptt.getMatainUserId();
			if(type==1){
				aa=checkUnitProfitTraditService.getTraditCheckProfitTempTraditBeanInfo(1,cptt);
			}else if(type==2){
				aa=checkUnitProfitTraditService.getTraditCheckProfitTempTraditBeanInfo(2,cptt);
			}
			list.add(aa);
		} catch (Exception e) {
			log.error("传统实时生效/下月生效分润模板获取异常：" + e);
		}
		super.writeJson(list);
	}
	/**
	 * 修改模版
	 */
	@SuppressWarnings("unchecked")
	public void updateProfitTradit(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				String errMsg = checkUnitProfitTraditService.validateProfitTradit(cptt, (UserBean)userSession,2);
				if(StringUtils.isNotEmpty(errMsg)) {
					json.setSuccess(false);
					json.setMsg(errMsg);
				}else {
					checkUnitProfitTraditService.updateProfitTradit(cptt, (UserBean) userSession);
					json.setSuccess(true);
					json.setMsg("修改分润模版成功");
				}
			} catch (Exception e) {
				log.error("修改分润模版异常：" + e);
				json.setMsg("修改分润模版失败");
			}
		}
		super.writeJson(json);
	}
	
	/*
	 * 代理分润汇总
	 */
	public void queryUnitProfitTraditSumData(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptt.getUnno()!=null && !"".equals(cptt.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptt.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitTraditService.queryUnitProfitTraditSumData(cptt);
			} catch (Exception e) {
				log.error(e);
			}
		}
		super.writeJson(dg);
	}
	
	/*
	 * 代理分润汇总（扫码）
	 */
	public void queryUnitProfitTraditSumDataScan(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptt.getUnno()!=null && !"".equals(cptt.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptt.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitTraditService.queryUnitProfitTraditSumDataScan(cptt);
			} catch (Exception e) {
				log.error(e);
			}
		}
		super.writeJson(dg);
	}

	/*
	 * 代理分润明细
	 */
	public void queryUnitProfitTraditDetailData(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptt.getUnno()!=null && !"".equals(cptt.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptt.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitTraditService.queryUnitProfitTraditDetailData(cptt);
			} catch (Exception e) {
				log.error(e);
			}
		}
		super.writeJson(dg);
	}
	
	/*
	 * 代理分润明细(扫码)
	 */
	public void queryUnitProfitTraditDetailDataScan(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptt.getUnno()!=null && !"".equals(cptt.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptt.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitTraditService.queryUnitProfitTraditDetailDataScan(cptt);
			} catch (Exception e) {
				log.error(e);
			}
		}
		super.writeJson(dg);
	}
	
	/*
	 * 代理商分润明细导出
	 */
	public void exportUnitProfitTraditDetailData(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (cptt.getUnno()!=null && !"".equals(cptt.getUnno())) {
			try{
				HSSFWorkbook wb=checkUnitProfitTraditService.exportUnitProfitTraditDetailData(cptt,((UserBean)userSession));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("代理商传统分润明细导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出代理商传统分润明细成功");
			} catch (Exception e) {
				log.error("导出代理商传统分润明细异常：" + e);
				json.setMsg("导出代理商传统分润明细失败");
			}
		} else {
			json.setMsg("请选择代理机构！");
			json.setSuccess(false);
		}
	}
	
	/*
	 * 代理商分润明细导出（扫码）
	 */
	public void exportUnitProfitTraditDetailDataScan(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (cptt.getUnno()!=null && !"".equals(cptt.getUnno())) {
			try{
				HSSFWorkbook wb=checkUnitProfitTraditService.exportUnitProfitTraditDetailDataScan(cptt,((UserBean)userSession));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("代理商传统扫码分润明细导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
				OutputStream ouputStream= response.getOutputStream();
				wb.write(ouputStream);  
				ouputStream.flush();  
				ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出代理商传统扫码分润明细成功");
			} catch (Exception e) {
				log.error("导出代理商传统扫码分润明细异常：" + e);
				json.setMsg("导出代理商传统扫码分润明细失败");
			}
		} else {
			json.setMsg("请选择代理机构！");
			json.setSuccess(false);
		}
	}

	/**
	 * 传统分润月度成本查询
	 */
	public void queryTraditProfitLog(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession==null){
			json.setSuccess(false);
			json.setMsg("登录信息过期,请重新登录");
			super.writeJson(json);
		}else{
			DataGridBean dataGridBean=new DataGridBean();
			try {
				dataGridBean =checkUnitProfitTraditService.getTraditProfitLog(cptt,((UserBean)userSession));
			} catch (Exception e) {
				log.error("传统分润月度成本查询异常"+e);
			}
			super.writeJson(dataGridBean);
		}
	}

	/**
	 * 传统分润月度成本详情
	 */
	public void queryProfitTraditLogDetail(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession!=null) {
			List<CheckProfitTempTraditBean> list = new ArrayList<CheckProfitTempTraditBean>();
			try {
				CheckProfitTempTraditBean aa=new CheckProfitTempTraditBean();
				aa=checkUnitProfitTraditService.queryProfitTraditLogDetail(cptt);
				list.add(aa);
			} catch (Exception e) {
				log.error("传统分润月度成本详情获取异常：" + e);
			}
			super.writeJson(list);
		}
	}

	/**
	 * 分润月度成本
	 */
	public void exportProfitTraditLog(){
		JsonBean json = new JsonBean();
		List<CheckProfitTempTraditBean>list = null;
		List<String[]>cotents = new ArrayList<String[]>();
		String titles[] = {"机构编号","机构名称","开始时间","结束时间","总利润比例","(标准)借记卡费率","(标准)借卡大额手续费","(标准)借卡大额封顶值","(标准)贷记卡费率",
				"(优惠)借记卡费率","(优惠)借卡大额手续费","(优惠)借卡大额封顶值","(优惠)贷记卡费率",
				"(减免)借记卡费率","(减免)借卡大额手续费","(减免)借卡大额封顶值","(减免)贷记卡费率",
				"T0提现费率","转账费","传统扫码1000以下费率","传统扫码1000以上费率"};
		cotents.add(titles);
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				list = checkUnitProfitTraditService.exportProfitTraditLog(cptt,(UserBean) userSession);
				if (list!=null&&list.size()>0) {
					for (CheckProfitTempTraditBean bean:list) {
						String rowCoents[] = {
								bean.getUnno(),bean.getTempName(),bean.getTxnDay(),bean.getTxnDay1()==null?"至今":bean.getTxnDay1(),bean.getProfitPercent()+"",
								bean.getCostRate()+"",bean.getFeeAmt()+"",bean.getDealAmt()+"",bean.getCreditBankRate()+"",
								bean.getCostRate1()+"",bean.getFeeAmt1()+"",bean.getDealAmt1()+"",bean.getCreditBankRate1()+"",
								bean.getCostRate2()+"",bean.getFeeAmt2()+"",bean.getDealAmt2()+"",bean.getCreditBankRate2()+"",
								bean.getCashRate()+"",bean.getCashAmt()+"",bean.getScanRate()+"",bean.getScanRateUp()+""
						};
						cotents.add(rowCoents);
					}
				}
				String excelName = "分润月度成本";
				JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), excelName, excelName+".xls", null);
				json.setSuccess(true);
				json.setMsg("分润月度成本导出成功");
			}
		} catch (Exception e) {
			log.error("分润月度成本导出异常：" + e);
			json.setSuccess(false);
			json.setMsg("分润月度成本导出失败");
			super.writeJson(json);
		}
	}

	/**
	 * 传统提现汇总
	 */
	public void queryTraditionCashTransferSum() {
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptt.getUnno()!=null && !"".equals(cptt.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptt.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitTraditService.queryTraditionCashTransferSum(cptt);
			} catch (Exception e) {
				log.error(e);
			}
		}
		super.writeJson(dg);
	}
}
