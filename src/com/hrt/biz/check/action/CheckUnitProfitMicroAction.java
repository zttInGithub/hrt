package com.hrt.biz.check.action;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.hrt.util.JxlOutExcelUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.check.entity.pagebean.CheckProfitTemplateMicroBean;
import com.hrt.biz.check.service.CheckUnitProfitMicroService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.service.sysadmin.IUnitService;
import com.opensymphony.xwork2.ModelDriven;

public class CheckUnitProfitMicroAction  extends BaseAction implements ModelDriven<CheckProfitTemplateMicroBean> {

	/**
	 *
	 */
	private static final Log log = LogFactory.getLog(CheckUnitProfitMicroAction.class);

	private CheckUnitProfitMicroService checkUnitProfitMicroService;
	
	private IUnitService unitService;

	public IUnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(IUnitService unitService) {
		this.unitService = unitService;
	}


	private CheckProfitTemplateMicroBean cptm=new CheckProfitTemplateMicroBean();

	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public CheckUnitProfitMicroService getCheckUnitProfitMicroService() {
		return checkUnitProfitMicroService;
	}

	public void setCheckUnitProfitMicroService(
			CheckUnitProfitMicroService checkUnitProfitMicroService) {
		this.checkUnitProfitMicroService = checkUnitProfitMicroService;
	}

	@Override
	public CheckProfitTemplateMicroBean getModel() {
		return cptm;
	}

	/*
	 * 代理分润汇总
	 */
	public void queryUnitProfitMicroSumData(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptm.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitMicroService.queryUnitProfitMicroSumData(cptm);
			} catch (Exception e) {
				log.error(e);
			}
		}
		super.writeJson(dg);
	}

	/*
	 * 代理扫码分润汇总
	 */
	public void queryUnitProfitMicroSumDataScan(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptm.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitMicroService.queryUnitProfitMicroSumDataScan(cptm);
			} catch (Exception e) {
				log.error(e);
			}
		}
		super.writeJson(dg);
	}

	/*
	 * 提现分润汇总
	 */
	public void queryUnitProfitMicroSumDataCash(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptm.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitMicroService.queryUnitProfitMicroSumDataCash(cptm);
			} catch (Exception e) {
				log.error(e);
			}
		}
		super.writeJson(dg);
	}

	/**
	 * 提现分润汇总(收银台)
	 */
	public void queryUnitProfitMicroSumDataCash2(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptm.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitMicroService.queryUnitProfitMicroSumDataCash2(cptm);
			} catch (Exception e) {
				log.error(e);
			}
		}
		super.writeJson(dg);
	}

	/**
	 * 分润模版维护
	 */
	public void queryPROFITTEMPLATE(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dgb=checkUnitProfitMicroService.queryPROFITTEMPLATE(cptm,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 代还分润模版维护
	 */
	public void querySubTemplate(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dgb=checkUnitProfitMicroService.querySubTemplate(cptm,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询代还模板信息异常：" + e);
		}
		super.writeJson(dgb);
	}

    /**
     * 实时生效/下月生效代还成本查询
     */
	public void queryNextMonthTemplate(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		Map map=null;
		try {
			cptm.setAptId(Integer.parseInt(getRequest().getParameter("aptid")));
			int type = cptm.getTxnType();
			if(type==1){
				map=checkUnitProfitMicroService.getReplayFirstDaySql(1,cptm);
			}else if(type==2){
				map=checkUnitProfitMicroService.getReplayFirstDaySql(2,cptm);
			}
//			map=checkUnitProfitMicroService.queryNextMonthTemplate(cptm,(UserBean)userSession);
		} catch (Exception e) {
			log.error("下月生效模板查询异常：" + e);
		}
		super.writeJson(map);
	}
	/**
	 * 收银台分润模版维护--查询
	 */
	public void querySytTemplate(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dgb=checkUnitProfitMicroService.querySytTemplate(cptm,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询收银台分润模版信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 收银台模板修改--本月生效模板查询
	 */
	public void querySytCurrentMonthTemplate() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {				
			list=checkUnitProfitMicroService.querySytCurrentMonthTemplate(cptm,(UserBean)userSession);
		} catch (Exception e) {
			log.error("查询本月收银台分润模版信息异常：" + e);
		}
		super.writeJson(list);
	}

	/**
	 * 收银台模板修改--下月生效模板查询
	 */
	public void querySytNextMonthTemplate() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			list=checkUnitProfitMicroService.querySytNextMonthTemplate(cptm,(UserBean)userSession);
		} catch (Exception e) {
			log.error("查询下月收银台分润模版信息异常：" + e);
		}
		super.writeJson(list);
	}
	/**
	 * 模版维护明细
	 */
	public void queryprofittemplateAll(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dgb=checkUnitProfitMicroService.queryprofittemplateAll(cptm,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 添加分润模版
	 */
	public void addprofitmicro(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				if(cptm.getEndAmount()!=null && (cptm.getEndAmount()<1000||cptm.getEndAmount()>10000)){
					json.setMsg("请输入借记卡封顶值 (1000--10000)元");
					json.setSuccess(false);
				}else if(cptm.getStartAmount()!=null && (cptm.getStartAmount()<20d||cptm.getStartAmount()>35)){
					json.setMsg("请输入借记卡封顶手续费(20--35)元");
					json.setSuccess(false);
				}else if (cptm.getCashRate()!=null && (cptm.getCashRate()<0.05||cptm.getCashRate()>0.07)){
					json.setMsg("请输入T0提现费率(0.05--0.07)%");
					json.setSuccess(false);
				}else{
					List list= checkUnitProfitMicroService.queryTempName(cptm,(UserBean)userSession);
					if(list.size()>0){
						json.setSuccess(false);
						json.setMsg("您设置的模板名称已被占用，请重新命名");
					}else{
					    String errMsg = checkUnitProfitMicroService.validateMposTemplate(cptm, (UserBean)userSession,1);
                        if (StringUtils.isNotEmpty(errMsg)) {
                            json.setSuccess(false);
                            json.setMsg(errMsg);
                        }else {
                            checkUnitProfitMicroService.addProfitmicro(cptm, (UserBean) userSession);
                            json.setSuccess(true);
                            json.setMsg("添加分润模版成功");
                        }
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
	 * 添加代还分润模版
	 */
	public void addSubTemplate(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				List list= checkUnitProfitMicroService.queryTempName(cptm,(UserBean)userSession);
				if(list.size()>0){
					json.setSuccess(false);
					json.setMsg("您设置的模板名称已被占用，请重新命名");
				}else{
					String errMsg=checkUnitProfitMicroService.validateSubTemplate(cptm, (UserBean)userSession,1);
					if(StringUtils.isNotEmpty(errMsg)){
						json.setSuccess(false);
						json.setMsg(errMsg);
					}else {
						checkUnitProfitMicroService.addSubTemplate(cptm, (UserBean) userSession);
						json.setSuccess(true);
						json.setMsg("添加代还分润模版成功");
					}
				}
			} catch (Exception e) {
				log.error("添加代还分润模版异常：" + e);
				json.setMsg("添加代还分润模版失败");
			}
		}
		super.writeJson(json);
	}

	/**
	 * 修改分润模版
	 */
	public void QUERYprofitmicro(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		List<CheckProfitTemplateMicroBean> list = new ArrayList<CheckProfitTemplateMicroBean>();
		try {
			CheckProfitTemplateMicroBean aa=checkUnitProfitMicroService.queryupdateProfitmicro(cptm,(UserBean)userSession);
			list.add(aa);
		} catch (Exception e) {
			log.error("修改分润模版异常：" + e);
		}
		super.writeJson(list);
	}

	/**
	 * 秒到下个月生效成本获取
	 */
	public void queryMdNextMonthTemplate(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		List<CheckProfitTemplateMicroBean> list = new ArrayList<CheckProfitTemplateMicroBean>();
		try {
            CheckProfitTemplateMicroBean aa=new CheckProfitTemplateMicroBean();
		    int txnType = cptm.getTxnType();
		    if(txnType==1){
                aa=checkUnitProfitMicroService.getMdCheckProfitTemplateMicroBeanInfo(1,cptm);
            }else if(txnType==2){
                aa=checkUnitProfitMicroService.getMdCheckProfitTemplateMicroBeanInfo(2,cptm);
            }
//			aa=checkUnitProfitMicroService.queryMdNextMonthTemplate(cptm,(UserBean)userSession);
			list.add(aa);
		} catch (Exception e) {
			log.error("秒到下个月生效成本获取异常：" + e);
		}
		super.writeJson(list);
	}
	/**
	 * 修改模版
	 */
	@SuppressWarnings("unchecked")
	public void updateProfitmicro(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			if(cptm.getEndAmount()!=null && (cptm.getEndAmount()<1000||cptm.getEndAmount()>10000)){
				json.setMsg("请输入借记卡封顶值 (1000--10000)元");
				json.setSuccess(false);
			}else if(cptm.getStartAmount()!=null && (cptm.getStartAmount()<20d||cptm.getStartAmount()>35d)){
				json.setMsg("请输入借记卡封顶手续费(20--35)元");
				json.setSuccess(false);
			}else if (cptm.getCashRate()!=null && (cptm.getCashRate()<0.05||cptm.getCashRate()>0.07)){
				json.setMsg("请输入T0提现费率(0.05--0.07)%");
				json.setSuccess(false);
			}else{
				try {
//					if(java.net.URLDecoder.decode(cptm.getTempname(),"UTF-8").trim().equals(cptm.getTempName())){
//						checkUnitProfitMicroService.updateProfitmicro(cptm, (UserBean)userSession);
//						json.setSuccess(true);
//						json.setMsg("修改分润模版成功");
//					}else{
//						List list= checkUnitProfitMicroService.queryTempName(cptm,(UserBean)userSession);
//						if(list.size()>0){
//							json.setSuccess(false);
//							json.setMsg("修改失败！模版名称已存在");
//						}else{
						    String errMsg = checkUnitProfitMicroService.validateMposTemplate(cptm, (UserBean)userSession,2);
                            if (StringUtils.isNotEmpty(errMsg)) {
                                json.setSuccess(false);
                                json.setMsg(errMsg);
                            }else {
                                checkUnitProfitMicroService.updateProfitmicro(cptm, (UserBean) userSession);
                                json.setSuccess(true);
                                json.setMsg("修改分润模版成功");
                            }
//						}
//					}
				} catch (Exception e) {
					log.error("修改分润模版异常：" + e);
					json.setMsg("修改分润模版失败");
				}
			}
		}
		super.writeJson(json);
	}
	/**
	 * 修改代还模版
	 */
	@SuppressWarnings("unchecked")
	public void updateSubTemplate(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				String errMsg=checkUnitProfitMicroService.validateSubTemplate(cptm, (UserBean)userSession,2);
				if(StringUtils.isNotEmpty(errMsg)) {
					json.setSuccess(false);
					json.setMsg(errMsg);
				}else {
					checkUnitProfitMicroService.updateSubTemplate(cptm, (UserBean) userSession);
					json.setSuccess(true);
					json.setMsg("修改代还分润模版成功");
				}
			} catch (Exception e) {
				log.error("修改分润模版异常：" + e);
				json.setMsg("修改分润模版失败");
			}
		}
		super.writeJson(json);
	}
	/**
	 * 修改收银台模版(现为修改下月生效的模板，本月不做修改)
	 */
	@SuppressWarnings("unchecked")
	public void updateSytTemplate(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				//非空校验
				String nullMsg = checkUnitProfitMicroService.validateSYTTextNotEmpty(cptm);
				if(nullMsg.contains("不能为空")) {
					json.setSuccess(false);
					json.setMsg(nullMsg);
					super.writeJson(json);
					return;
				}
				String errMsg = checkUnitProfitMicroService.validateSytTemplateNew(cptm, (UserBean)userSession);
				if (StringUtils.isNotEmpty(errMsg)) {
					json.setSuccess(false);
					json.setMsg(errMsg);
				}else {
					checkUnitProfitMicroService.updateSytTemplate(cptm, (UserBean) userSession);
					json.setSuccess(true);
					json.setMsg("修改收银台分润模版成功");
				}
			} catch (Exception e) {
				log.error("修改分润模版异常：" + e);
				json.setMsg("修改分润模版失败");
			}
		}
		super.writeJson(json);
	}
	/**
	 * 代理成本关系维护删除
	 */
	public void Delprofitmicro(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			checkUnitProfitMicroService.DeleteProfitmicro(ids,(UserBean)userSession);
			json.setMsg("删除成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("删除失败！");
			json.setSuccess(false);
			log.error("上送类别查询异常：" + e);
		}
		super.writeJson(json);
	}
	/**
	 * 查询下级代理
	 */
	public void searchUnno(){
		DataGridBean dgd = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			String nameCode = super.getRequest().getParameter("q");
			dgd = checkUnitProfitMicroService.searchunno(nameCode,(UserBean)userSession);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.writeJson(dgd);
	}
	/*
	 * 代理分润明细
	 */
	public void queryUnitProfitMicroDetailData(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptm.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitMicroService.queryUnitProfitMicroDetailData(cptm);
			} catch (Exception e) {
				log.error(e);
			}
		}
		super.writeJson(dg);
	}

	/*
	 * 代理扫码分润明细
	 */
	public void queryUnitProfitMicroDetailDataScan(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptm.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitMicroService.queryUnitProfitMicroDetailDataScan(cptm);
			} catch (Exception e) {
				log.error(e);
			}
		}
		super.writeJson(dg);
	}
	/*
	 * 代还分润明细
	 */
	public void queryUnitSubTemplateDetailDataScan2(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptm.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitMicroService.queryUnitSubTemplateDetailDataScan2(cptm,(UserBean)userSession);
			} catch (Exception e) {
				log.error(e);
			}
		}
		super.writeJson(dg);
	}
	/*
	 * 收银台分润明细
	 */
	public void queryUnitSytTemplateDetailDataScan2(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptm.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitMicroService.queryUnitSytTemplateDetailDataScan2(cptm,(UserBean)userSession);
			} catch (Exception e) {
				log.error(e);
			}
		}
		super.writeJson(dg);
	}
	/*
	 * 收银台分润汇总
	 */
	public void queryUnitSytTemplateDetailDataScan(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptm.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitMicroService.queryUnitSytTemplateDetailDataScan(cptm,(UserBean)userSession);
			} catch (Exception e) {
				log.error(e);
			}
		}
		super.writeJson(dg);
	}
	/*
	 * 代还明细
	 */
	public void queryUnitSubTemplateDetailDataScan(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dg =checkUnitProfitMicroService.queryUnitSubTemplateDetailDataScan(cptm,(UserBean)userSession);
		} catch (Exception e) {
			log.error(e);
		}
		super.writeJson(dg);
	}
	/*
	 * 代理商分润明细导出
	 */
	public void exportUnitProfitMicroDetailData(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (cptm.getUnno()!=null && !"".equals(cptm.getUnno())) {
			try{
				HSSFWorkbook wb=checkUnitProfitMicroService.exportUnitProfitMicroDetailData(cptm,((UserBean)userSession));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("代理商分润明细导出".getBytes("GBK"), "ISO-8859-1") + ".xls");
				OutputStream ouputStream= response.getOutputStream();
				wb.write(ouputStream);
				ouputStream.flush();
				ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出代理商分润明细成功");
			} catch (Exception e) {
				log.error("导出代理商分润明细异常：" + e);
				json.setMsg("导出代理商分润明细失败");
			}
		} else {
			json.setMsg("请选择代理机构！");
			json.setSuccess(false);
		}
	}

	/*
	 * 代理商扫码分润明细导出
	 */
	public void exportUnitProfitMicroDetailDataScan(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (cptm.getUnno()!=null && !"".equals(cptm.getUnno())) {
			try{
				HSSFWorkbook wb=checkUnitProfitMicroService.exportUnitProfitMicroDetailDataScan(cptm,((UserBean)userSession));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("手刷扫码分润明细导出".getBytes("GBK"), "ISO-8859-1") + ".xls");
				OutputStream ouputStream= response.getOutputStream();
				wb.write(ouputStream);
				ouputStream.flush();
				ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出手刷扫码分润明细成功");
			} catch (Exception e) {
				log.error("导出手刷扫码分润明细异常：" + e);
				json.setMsg("导出手刷扫码分润明细失败");
			}
		} else {
			json.setMsg("请选择代理机构！");
			json.setSuccess(false);
		}
	}
	/*
	 * 代理商代还导出
	 */
	public void exportUnitProfitMicroDetailDataScan2(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (cptm.getUnno()!=null && !"".equals(cptm.getUnno())) {
			try{
				HSSFWorkbook wb=checkUnitProfitMicroService.exportUnitProfitMicroDetailDataScan2(cptm,((UserBean)userSession));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("手刷扫码分润明细导出".getBytes("GBK"), "ISO-8859-1") + ".xls");
				OutputStream ouputStream= response.getOutputStream();
				wb.write(ouputStream);
				ouputStream.flush();
				ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出代理代还分润明细成功");
			} catch (Exception e) {
				log.error("导出代理代还分润明细异常：" + e);
				json.setMsg("导出代理代还分润明细失败");
			}
		} else {
			json.setMsg("请选择代理机构！");
			json.setSuccess(false);
		}
	}
	/*
	 * 代理商收银台分润明细导出
	 */
	public void exportUnitProfitMicroDetailDataScan3(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		//		if (cptm.getUnno()!=null && !"".equals(cptm.getUnno())) {
		try{
			HSSFWorkbook wb=checkUnitProfitMicroService.exportUnitProfitMicroDetailDataScan3(cptm,((UserBean)userSession));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + new String("收银台分润明细导出".getBytes("GBK"), "ISO-8859-1") + ".xls");
			OutputStream ouputStream= response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
			json.setSuccess(true);
			json.setMsg("导出收银台分润明细成功");
		} catch (Exception e) {
			log.error("导出收银台分润明细异常：" + e);
			json.setMsg("导出收银台分润明细失败");
		}
		//		} else {
		//			json.setMsg("请选择代理机构！");
		//			json.setSuccess(false);
		//		}
	}
	/**
	 * 得到代理商的简码
	 */
	public void getProfitUnitGodes(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgd = new DataGridBean();
		String nameCode=super.getRequest().getParameter("q");
		if(user == null) {
		}else {
			try{
				dgd=checkUnitProfitMicroService.getProfitUnitGodes(user,nameCode);
			}catch(Exception e){
				log.error("查询分润代理商简码异常：" + e);
			}
		}
		super.writeJson(dgd);
	}
	/**
	 * 得到代理商的简码包含如果是报单员业务员只显示自己签约的机构
	 */
	public void getProfitUnitGodesForSales(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgd = new DataGridBean();
		if(user == null) {
		}else {
			String nameCode=super.getRequest().getParameter("q");
			try{
				dgd=checkUnitProfitMicroService.getProfitUnitGodesForSales(user,nameCode);
			}catch(Exception e){
				log.error("查询分润代理商简码异常：" + e);
			}
		}
		super.writeJson(dgd);
	}


	/*
	 * 查询已经维护的代理商关联模板LIST
	 */
	public void queryUnitProfitTempList(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb=checkUnitProfitMicroService.queryUnitProfitTempList(cptm,user);
		} catch (Exception e) {
			log.error(e);
		}
		super.writeJson(dgb);
	}

	/*
	 * 查询已经维护的代还模板
	 */
	public void querySubTemplateList(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb=checkUnitProfitMicroService.querySubTemplateList(cptm,user);
		} catch (Exception e) {
			log.error(e);
		}
		super.writeJson(dgb);
	}

	/*
	 * 查询已经维护的收银台模板
	 */
	public void querySytTemplateList(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb=checkUnitProfitMicroService.querySytTemplateList(cptm,user);
		} catch (Exception e) {
			log.error(e);
		}
		super.writeJson(dgb);
	}

	/*
	 * 查询当前机构下的模板名称
	 */
	public void queryProfitTempByUnno(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try{
			list=checkUnitProfitMicroService.queryProfitTempByUnno(user);
		}catch(Exception e){
			log.error("查询代理商模板异常：" + e);
		}
		super.writeJson(list);
	}
	/*
	 * 查询当前机构下的代还模板名称
	 */
	public void querySubTemplateByUnno(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try{
			list=checkUnitProfitMicroService.querySubTemplateByUnno(user);
		}catch(Exception e){
			log.error("查询代理商模板异常：" + e);
		}
		super.writeJson(list);
	}
	/**
	 * 查询当前机构下的收银台模板名称
	 */
	public void querySytTemplateByUnno(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try{
			list=checkUnitProfitMicroService.querySytTemplateByUnno(user);
		}catch(Exception e){
			log.error("查询代理商模板异常：" + e);
		}
		super.writeJson(list);
	}
	/**
	 * 分配分润模版删除
	 */
	public void DeleteProfitMicroUnno(){
		JsonBean json = new JsonBean();
		String unno=super.getRequest().getParameter("unno");
		String settMethod=super.getRequest().getParameter("settMethod");
		try {
			checkUnitProfitMicroService.DeleteProfitMicroUnno(unno,settMethod);
			json.setMsg("解除成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("解除失败！");
			json.setSuccess(false);
			log.error("解除异常：" + e);
		}
		super.writeJson(json);
	}
	/**
	 * 分配代还分润模版删除
	 */
	public void deleteSubTemplateUnno(){
		JsonBean json = new JsonBean();
		String unno=super.getRequest().getParameter("unno");
		try {
			checkUnitProfitMicroService.deleteSubTemplateUnno(unno);
			json.setMsg("解除成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("解除失败！");
			json.setSuccess(false);
			log.error("解除异常：" + e);
		}
		super.writeJson(json);
	}
	/**
	 * 分配收银台分润模版删除
	 */
	public void deleteSytTemplateUnno(){
		JsonBean json = new JsonBean();
		String unno=super.getRequest().getParameter("unno");
		try {
			checkUnitProfitMicroService.deleteSytTemplateUnno(unno);
			json.setMsg("解除成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("解除失败！");
			json.setSuccess(false);
			log.error("解除异常：" + e);
		}
		super.writeJson(json);
	}
	/**
	 * 为下级代理设置分润模板
	 */
	public void addUnitProfitTempInfo(){
		JsonBean json= new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		boolean flag =false;
		try {
			List<Map<String, Object>> list = checkUnitProfitMicroService.queryUnno(cptm);
			if(list.size()>0){
				json.setSuccess(false);
				json.setMsg("重复不可分配！");
			}else{
				String errMsg=checkUnitProfitMicroService.saveUnitProfitTempInfo(cptm,user);
				if(StringUtils.isEmpty(errMsg)){
					json.setMsg("分配分润模板成功！");
					json.setSuccess(true);
				}else{
					json.setMsg(errMsg);
					json.setSuccess(false);
				}
			}
		} catch (Exception e) {
			log.error(e);
			json.setMsg("分配分润模板失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	/**
	 * 分配代还分润模板
	 */
	public void addSubTemplateInfo(){
		JsonBean json= new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		boolean flag =false;
		try {
			List<Map<String, Object>> list = checkUnitProfitMicroService.queryUnno2(cptm);
			if(list.size()>0){
				json.setSuccess(false);
				json.setMsg("重复不可分配！");
			}else{
				String errMsg=checkUnitProfitMicroService.saveSubTemplateInfo(cptm,user);
				if(StringUtils.isEmpty(errMsg)){
					json.setMsg("分配分润模板成功！");
					json.setSuccess(true);
				}else{
//					json.setMsg("模板可能不存在，请确认！");
					json.setMsg(errMsg);
					json.setSuccess(false);
				}
			}
		} catch (Exception e) {
			log.error(e);
			json.setMsg("分配分润模板失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	/**
	 * 分配收银台分润模板
	 */
	public void addSytTemplateInfo(){
		JsonBean json= new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		boolean flag =false;
		try {
			List<Map<String, Object>> list = checkUnitProfitMicroService.queryUnno3(cptm);
			if(list.size()>0){
				json.setSuccess(false);
				json.setMsg("重复不可分配！");
			}else{
				String errMsg=checkUnitProfitMicroService.saveSytTemplateInfo(cptm,user);
				if(StringUtils.isEmpty(errMsg)){
					json.setMsg("分配收银台分润模板成功！");
					json.setSuccess(true);
				}else{
					json.setMsg(errMsg);
					json.setSuccess(false);
				}
			}
		} catch (Exception e) {
			log.error(e);
			json.setMsg("分配分润模板失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}

	/**
	 * 查询MPOS活动分润模版
	 */
	public void queryMposTemplate(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dgb=checkUnitProfitMicroService.queryMposTemplate(cptm,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		super.writeJson(dgb);
	}

	/**
	 * 添加MPOS活动分润模版
	 */
	public void addMposTempla(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				List list= checkUnitProfitMicroService.queryTempName(cptm,(UserBean)userSession);
				if(list.size()>0){
					json.setSuccess(false);
					json.setMsg("您设置的模板名称已被占用，请重新命名");
				}else{
					String emptyMsg = checkUnitProfitMicroService.validatePlusTextNotEmpty(cptm);
					if(emptyMsg.contains("不能为空")) {
						json.setSuccess(false);
						json.setMsg(emptyMsg);
					}else {
						String errMsg = checkUnitProfitMicroService.validatePlusMposTempla(cptm, (UserBean)userSession,1);
	                    if (StringUtils.isNotEmpty(errMsg)) {
	                        json.setSuccess(false);
	                        json.setMsg(errMsg);
	                    }else {
	                        checkUnitProfitMicroService.addMposTempla(cptm, (UserBean) userSession);
	                        json.setSuccess(true);
	                        json.setMsg("添加MPOS活动分润模版成功");
	                    }
					}

				}
			} catch (Exception e) {
				log.error("添加MPOS活动分润模版异常：" + e);
				json.setMsg("添加MPOS活动分润模版失败");
			}
		}
		super.writeJson(json);
	}

	/**
	 * 查询修改Mpos活动分润模版QUERYMposprofitmicroNow---次月生效
	 */
	public void QUERYMposprofitmicro(){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			list=checkUnitProfitMicroService.queryupdateMposProfitmicroNextMonth(cptm,(UserBean)userSession);
		} catch (Exception e) {
			log.error("修改分润模版异常：" + e);
		}
		super.writeJson(list);
	}

	/**
	 * 查询修改Mpos活动分润模版---本月生效
	 */
	public void QUERYMposprofitmicroNow(){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			list=checkUnitProfitMicroService.queryupdateMposProfitmicro(cptm,(UserBean)userSession);
		} catch (Exception e) {
			log.error("修改分润模版异常：" + e);
		}
		super.writeJson(list);
	}

	/**
	 * 修改MPOS活动模版(本月不准修改，只能修改次月)
	 */
	public void updateProfitmicro20288(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				String emptyMsg = checkUnitProfitMicroService.validatePlusTextNotEmpty(cptm);
				if(emptyMsg.contains("不能为空")) {
					json.setSuccess(false);
					json.setMsg(emptyMsg);
				}else {
					String errMsg = checkUnitProfitMicroService.validatePlusMposTempla(cptm, (UserBean)userSession,2);
					if (StringUtils.isNotEmpty(errMsg)) {
						json.setSuccess(false);
						json.setMsg(errMsg);
					}else {
						checkUnitProfitMicroService.updateMposTemplate(cptm, (UserBean) userSession);
						json.setSuccess(true);
						json.setMsg("修改MPOS活动分润模版成功");
					}
				}
			} catch (Exception e) {
				log.error("修改MPOS活动分润模版异常：" + e);
				json.setMsg("修改MPOS活动分润模版失败");
			}
		}
		super.writeJson(json);
	}

	/*
	 * 查询已经维护的MPOS活动模板
	 */
	public void queryMposTemplateList(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb=checkUnitProfitMicroService.queryMposTemplateList(cptm,user);
		} catch (Exception e) {
			log.error(e);
		}
		super.writeJson(dgb);
	}

	/*
	 * 查询当前机构下的MPOS活动模板名称
	 */
	public void queryMPosTemplateByUnno(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try{
			list=checkUnitProfitMicroService.queryMposTemplateByUnno(user);
		}catch(Exception e){
			log.error("查询代理商模板异常：" + e);
		}
		super.writeJson(list);
	}

	/**
	 * 分配mpos活动分润模板
	 */
	public void addMposTemplateInfo(){
		JsonBean json= new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		boolean flag =false;
		try {
			List<Map<String, Object>> list = checkUnitProfitMicroService.queryUnnoMpos(cptm);
			if(list.size()>0){
				json.setSuccess(false);
				json.setMsg("重复不可分配！");
			}else{
				String errMsg=checkUnitProfitMicroService.saveMposTemplateInfo(cptm,user);
				if(StringUtils.isEmpty(errMsg)){
					json.setMsg("分配MPOS活动分润模板成功！");
					json.setSuccess(true);
				}else{
					json.setMsg(errMsg);
					json.setSuccess(false);
				}
			}
		} catch (Exception e) {
			log.error(e);
			json.setMsg("分配分润模板失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}

	/**
	 * 分配mpos活动分润模版删除
	 */
	public void deleteMposTemplateUnno(){
		JsonBean json = new JsonBean();
		String unno=super.getRequest().getParameter("unno");
		try {
			checkUnitProfitMicroService.deleteMposTemplateUnno(unno);
			json.setMsg("解除成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("解除失败！");
			json.setSuccess(false);
			log.error("解除异常：" + e);
		}
		super.writeJson(json);
	}

	/*
	 * plus模板分润汇总
	 */
	public void queryUnitMposTemplateDetailDataScan(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptm.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitMicroService.queryUnitMposTemplateDetailDataScan(cptm,(UserBean)userSession);
			} catch (Exception e) {
				log.error("plus模板分润汇总异常:"+e);
			}
		}
		super.writeJson(dg);
	}

	/**
	 * MPOS活动分润管理-MPOS活动提现分润汇总
	 */
	public void queryMposUnitProfitMicroSumDataCash(){
		DataGridBean dg = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(cptm.getUnno()!=null && !"".equals(cptm.getUnno())){
			try {
				boolean b = unitService.judgeUnnoIsInUser((UserBean)userSession, cptm.getUnno());
				if(!b) {
					super.writeJson(dg);
					return;
				}
				dg =checkUnitProfitMicroService.queryUnitMposProfitMicroSumDataCash(cptm);
			} catch (Exception e) {
				log.error("MPOS活动模板提现分润汇总异常"+e);
			}
		}
		super.writeJson(dg);
	}

	/**
	 * 代还月度成本查询
	 */
	public void queryMicroProfitDhLog(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession==null){
			json.setSuccess(false);
			json.setMsg("登录信息过期,请重新登录");
			super.writeJson(json);
		}else{
			DataGridBean dataGridBean=new DataGridBean();
			try {
				dataGridBean =checkUnitProfitMicroService.getMicroProfitDhLog(cptm,((UserBean)userSession));
			} catch (Exception e) {
				log.error("代还月度成本查询异常"+e);
			}
			super.writeJson(dataGridBean);
		}
	}

	/**
	 * 代还月度成本导出
	 */
    public void exportMicroProfitDhLog(){
        JsonBean json = new JsonBean();
		List<Map<String, Object>> list = null;
        List<String[]>cotents = new ArrayList<String[]>();
        String titles[] = {"下级代理机构号","下级代理名称","模板名称","开始时间","结束时间","代还费率"};
        cotents.add(titles);
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if(userSession!=null){
                list = checkUnitProfitMicroService.exportMicroProfitDhLog(cptm,(UserBean) userSession);
                if (list!=null&&list.size()>0) {
                    for (Map map:list) {
                        String rowCoents[] = {
								map.get("UNNO")+"",
								map.get("UN_NAME")+"",
								map.get("TEMPNAME")+"",
								map.get("STARTDATE")+"",
								map.get("ENDDATE")==null?"至今":map.get("ENDDATE")+"",
								map.get("SUBRATE")+""
                        };
                        cotents.add(rowCoents);
                    }
                }
                String excelName = "代还月度成本";
                JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), excelName, excelName+".xls", null);
                json.setSuccess(true);
                json.setMsg("代还月度成本导出成功");
            }
        } catch (Exception e) {
            log.error("代还月度成本导出异常：" + e);
            json.setSuccess(false);
            json.setMsg("代还月度成本导出失败");
            super.writeJson(json);
        }
    }
	/**
	 * 分润月度成本查询
	 */
	public void queryMicroProfitMdLog(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession==null){
			json.setSuccess(false);
			json.setMsg("登录信息过期,请重新登录");
			super.writeJson(json);
		}else{
			DataGridBean dataGridBean=new DataGridBean();
			try {
				dataGridBean =checkUnitProfitMicroService.getMicroProfitMdLog(cptm,((UserBean)userSession));
			} catch (Exception e) {
				log.error("分润月度成本查询异常"+e);
			}
			super.writeJson(dataGridBean);
		}
	}

	/**
	 * 分润月度成本详情
	 */
	public void queryMicroProfitMdLogDetail(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession!=null) {
			List<CheckProfitTemplateMicroBean> list = new ArrayList<CheckProfitTemplateMicroBean>();
			try {
				CheckProfitTemplateMicroBean aa=new CheckProfitTemplateMicroBean();
				aa=checkUnitProfitMicroService.getMicroProfitMdLogDetail(cptm);
				list.add(aa);
			} catch (Exception e) {
				log.error("传统分润月度成本详情获取异常：" + e);
			}
			super.writeJson(list);
		}
	}

	/**
	 * 分润月度成本导出-20200201前
	 */
	public void exportMicroProfitmdLog(){
		JsonBean json = new JsonBean();
		List<CheckProfitTemplateMicroBean> list = null;
		List<String[]>cotents = new ArrayList<String[]>();
		String titles[] = {"下级代理机构号","下级代理名称","模板名称","开始时间","结束时间",
				"(理财)借记卡封顶值","(理财)借记卡手续费","(理财)借记卡费率","(理财)T0提现费率","(理财)贷记卡费率","(理财)贷记卡利润百分比",
				"(秒到)贷记卡0.72%费率","(秒到)0.72利润百分比","(秒到)贷记卡非0.72%费率","(秒到)非0.72利润百分比","(秒到)转账费","(秒到)云闪付费率",
				"(扫码)微信费率","(扫码)支付宝费率","(扫码)银联二维码费率","(扫码)转账费",
				"(快捷)VIP用户费率","(快捷)完美账单费率","(快捷)快捷分润比例","(快捷)转账费"};
		cotents.add(titles);
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				list = checkUnitProfitMicroService.exportMicroProfitMdLog0312(cptm,(UserBean) userSession,1);
				if (list!=null&&list.size()>0) {
					for (CheckProfitTemplateMicroBean bean:list) {
						String rowCoents[] = {
								bean.getUnno(),bean.getMid(),bean.getTempName(),bean.getTxnDay(),bean.getTxnDay1()==null?"至今":bean.getTxnDay1(),
								bean.getEndAmount()+"",bean.getStartAmount()+"",bean.getRuleThreshold()+"",bean.getCashRate()+"",bean.getCashAmt()+"",bean.getCreditBankRate()+"",bean.getProfitPercent()+"",
								bean.getCreditBankRate1()+"",bean.getProfitPercent1()+"",bean.getCreditBankRate2()+"",bean.getProfitPercent2()+"",bean.getCashAmt1()+"",bean.getCloudQuickRate()+"",
								bean.getScanRate()+"",bean.getScanRate1()+"",bean.getScanRate2()+"",bean.getCashAmt2()+"",
								bean.getStartAmount2()+"",bean.getEndAmount2()+"",bean.getProfitPercent3()+"",bean.getCashAmt3()+""
						};
						cotents.add(rowCoents);
					}
				}
				String excelName = "分润月度成本.csv";
				JxlOutExcelUtil.writeCSVFile(cotents, titles.length, getResponse(), excelName);
				//JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), excelName, excelName+".xls", null);
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
	 * 查询收银台月度成本查询
	 */
	public void queryMicroProfitSytLog() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
			json.setSuccess(false);
			json.setMsg("登录信息过期,请重新登录");
			super.writeJson(json);
		} else {
			DataGridBean dataGridBean = new DataGridBean();
			try {
				dataGridBean = checkUnitProfitMicroService.getMicroProfitSytLog(cptm, ((UserBean) userSession));
			} catch (Exception e) {
				log.error("收银台月度成本查询异常" + e);
			}
			super.writeJson(dataGridBean);
		}
	}
	/**
	 * 收银台月度成本---导出
	 */
	public void exportMicroProfitSytLog(){
		JsonBean json = new JsonBean();
		List<Map<String, Object>> list = null;
        List<String[]>cotents = new ArrayList<String[]>();
        String titles[] = {"下级代理机构号","下级代理名称","模板名称","开始时间","结束时间",
        		"扫码1000以上（终端0.38）费率","扫码1000以上（终端0.38）转账费","扫码1000以上（终端0.45）费率","扫码1000以上（终端0.45）转账费",
        		"扫码1000以下（终端0.38）费率","扫码1000以下（终端0.38）转账费","扫码1000以下（终端0.45）费率","扫码1000以下（终端0.45）转账费",
        		"二维码费率","二维码转账费","花呗费率","花呗转账费","活动类型"};
        cotents.add(titles);
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if(userSession!=null){
                list = checkUnitProfitMicroService.exportMicroProfitSytLog(cptm,(UserBean) userSession);
                if (list!=null&&list.size()>0) {
                    for (Map map:list) {
                        String rowCoents[] = {
								map.get("UNNO")+"",
								map.get("UN_NAME")+"",
								map.get("TEMPNAME")+"",
								map.get("STARTDATE")+"",
								map.get("ENDDATE")==null?"至今":map.get("ENDDATE")+"",
								map.get("CREDITBANKRATE")+"",
								map.get("CASHRATE")+"",
								map.get("SCANRATE")+"",
								map.get("PROFITPERCENT1")+"",
								map.get("SUBRATE")+"",
								map.get("CASHAMT")+"",
								map.get("SCANRATE1")+"",
								map.get("CASHAMT1")+"",
								map.get("SCANRATE2")+"",
								map.get("CASHAMT2")+"",
								map.get("HUABEIRATE")+"",
								map.get("HUABEIFEE")+"",
								map.get("PROFITRULE")+""
                        };
                        cotents.add(rowCoents);
                    }
                }
                String excelName = "收银台月度成本";
                JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), excelName, excelName+".xls", null);
                json.setSuccess(true);
                json.setMsg("收银台月度成本导出成功");
            }
        } catch (Exception e) {
            log.error("收银台月度成本导出异常：" + e);
            json.setSuccess(false);
            json.setMsg("收银台月度成本导出失败");
            super.writeJson(json);
        } 
	}

	 /**
	 * 查询MPOS活动月度成本查询
	 */
	public void queryMicroProfitMposTemplateLog() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
			json.setSuccess(false);
			json.setMsg("登录信息过期,请重新登录");
			super.writeJson(json);
		} else {
			DataGridBean dataGridBean = new DataGridBean();
			try {
				dataGridBean = checkUnitProfitMicroService.getMicroProfitMposTemplateLog(cptm, ((UserBean) userSession));
			} catch (Exception e) {
				log.error("mpos活动月度成本查询异常" + e);
			}
			super.writeJson(dataGridBean);
		}
	}
	
	/**
	 * 查询MPOS活动月度成本查询---详细展示
	 */
	public void QUERYMposprofitmicroLogDetail() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
			json.setSuccess(false);
			json.setMsg("登录信息过期,请重新登录");
			super.writeJson(json);
		} else {
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			try {				
				list=checkUnitProfitMicroService.queryupdateMposProfitmicroLogDetail(cptm,(UserBean)userSession);
			} catch (Exception e) {
				log.error("修改分润模版异常：" + e);
			}
			super.writeJson(list);
		}
	}
	
	 /**
	 * MPOS活动月度成本----导出
	 */
	public void exportMicroProfitMposTemplateLog(){
		JsonBean json = new JsonBean();
		List<Map<String, Object>> list = null;
        List<String[]>cotents = new ArrayList<String[]>();
        String titles[] = {"下级代理机构号","下级代理名称","模板名称","活动","开始时间","结束时间",
        		"扫码1000以上（终端0.38）费率","扫码1000以上（终端0.38）转账费","扫码1000以上（终端0.45）费率","扫码1000以上（终端0.45）转账费",
        		"扫码1000以下（终端0.38）费率","扫码1000以下（终端0.38）转账费","扫码1000以下（终端0.45）费率","扫码1000以下（终端0.45）转账费","秒到费率","秒到转账费","银联二维码费率","银联二维码转账费","云闪付","花呗费率","花呗转账费"};
        cotents.add(titles);
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if(userSession!=null){
                list = checkUnitProfitMicroService.exportMicroProfitMposTempLateLog(cptm,(UserBean) userSession);
                if (list!=null&&list.size()>0) {
                    for (Map map:list) {
                        String rowCoents[] = {
								map.get("UNNO")+"",
								map.get("UN_NAME")+"",
								map.get("TEMPNAME")+"",
								map.get("PROFITRULE")+"",
								map.get("STARTDATE")+"",
								map.get("ENDDATE")==null?"至今":map.get("ENDDATE")+"",
								map.get("CREDITBANKRATE")+"",
								map.get("CASHRATE")+"",
								map.get("RULETHRESHOLD")+"",
								map.get("STARTAMOUNT")+"",
								map.get("SCANRATE")+"",
								map.get("CASHAMT1")+"",
								map.get("SCANRATE1")+"",
								map.get("PROFITPERCENT1")+"",
								map.get("SUBRATE")+"",
								map.get("CASHAMT")+"",
								map.get("SCANRATE2")+"",
								map.get("CASHAMT2")+"",
								map.get("CLOUDQUICKRATE")+"",
								map.get("HUABEIRATE")+"",
								map.get("HUABEIFEE")+""
                        };
                        cotents.add(rowCoents);
                    }
                }
                String excelName = "mpos活动月度成本";
                JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), excelName, excelName+".xls", null);
                json.setSuccess(true);
                json.setMsg("mpos活动月度成本导出成功");
            }
        } catch (Exception e) {
            log.error("mpos活动月度成本导出异常：" + e);
            json.setSuccess(false);
            json.setMsg("mpos活动月度成本导出失败");
            super.writeJson(json);
        } 
	}
	
	
	/**
	 * 查询登录机构及其直接下级机构
	 */
	public void getProfitUnitGodesForSalesForSpecial(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgd = new DataGridBean();
		String nameCode=super.getRequest().getParameter("q");
		if(user == null) {
		}else {
			try{
				dgd=checkUnitProfitMicroService.getProfitUnitGodesForSalesForSpecial(user,nameCode);
			}catch(Exception e){
				log.error("查询分润代理商简码异常：" + e);
			}
		}
		super.writeJson(dgd);
	}
	
	
	/**
	 * 分润月度成本导出-20200201后
	 */
	public void exportMicroProfitmdLogNew(){
		JsonBean json = new JsonBean();
		List<CheckProfitTemplateMicroBean> list = null;
		List<String[]>cotents = new ArrayList<String[]>();
		String titles[] = {"下级代理机构号","下级代理名称","模板名称","开始时间","结束时间",
				"(理财)借记卡封顶值","(理财)借记卡手续费","(理财)借记卡费率","(理财)T0提现费率","(理财)贷记卡费率","(理财)贷记卡利润百分比",
				"(秒到)贷记卡0.72%费率","(秒到)0.72利润百分比","(秒到)贷记卡非0.72%费率","(秒到)非0.72利润百分比","(秒到)转账费","(秒到)云闪付费率",
				"扫码1000下费率","扫码1000以上费率","(扫码)银联二维码费率","(扫码)银联二维码转账费",
				"(快捷)VIP用户费率","(快捷)完美账单费率","(快捷)快捷分润比例","(快捷)转账费",
				"扫码1000以下转账费","扫码1000以上转账费"};
		cotents.add(titles);
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				list = checkUnitProfitMicroService.exportMicroProfitMdLog0312(cptm,(UserBean) userSession,2);
				if (list!=null&&list.size()>0) {
					for (CheckProfitTemplateMicroBean bean:list) {
						String rowCoents[] = {
								bean.getUnno(),bean.getMid(),bean.getTempName(),bean.getTxnDay(),bean.getTxnDay1()==null?"至今":bean.getTxnDay1(),
								bean.getEndAmount()+"",bean.getStartAmount()+"",bean.getRuleThreshold()+"",bean.getCashRate()+"",bean.getCashAmt()+"",bean.getCreditBankRate()+"",bean.getProfitPercent()+"",
								bean.getCreditBankRate1()+"",bean.getProfitPercent1()+"",bean.getCreditBankRate2()+"",bean.getProfitPercent2()+"",bean.getCashAmt1()+"",bean.getCloudQuickRate()+"",
								bean.getScanRate()+"",bean.getScanRate1()+"",bean.getScanRate2()+"",bean.getCashAmt2()+"",
								bean.getStartAmount2()+"",bean.getEndAmount2()+"",bean.getProfitPercent3()+"",bean.getCashAmt3()+"",
								bean.getCashamtunder()+"",bean.getCashamtabove()+""
						};
						cotents.add(rowCoents);
					}
				}
				String excelName = "分润月度成本";
				JxlOutExcelUtil.writeCSVFile(cotents, titles.length, getResponse(), excelName);
				//JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), excelName, excelName+".xls", null);
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
/*---------------------------分割线---------------------------------*/	
	/**
	 *活动下拉选框
	 * anxinbo 2020-03-19
	 */
	public void listRebateRate(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				dgb = checkUnitProfitMicroService.listRebateRate();
			}
		} catch (Exception e) {
			log.error("查询活动类型配置成本异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 添加会员宝收银台活动分润模版
	 */
	public void addHybCashTempla(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {			
			try {
				List list= checkUnitProfitMicroService.queryTempName(cptm,(UserBean)userSession);
				if(list.size()>0){
					json.setSuccess(false);
					json.setMsg("您设置的模板名称已被占用，请重新命名");
				}else{
					String emptyMsg = checkUnitProfitMicroService.validateHybCashTextNotEmpty(cptm);
					if(emptyMsg.contains("不能为空")) {
						json.setSuccess(false);
						json.setMsg(emptyMsg);
					}else {
						String errMsg = checkUnitProfitMicroService.validateHybCashTempla(cptm, (UserBean)userSession,1);
	                    if (StringUtils.isNotEmpty(errMsg)) {
	                        json.setSuccess(false);
	                        json.setMsg(errMsg);
	                    }else {
	                        checkUnitProfitMicroService.addHybCashTempla(cptm, (UserBean) userSession);
	                        json.setSuccess(true);
	                        json.setMsg("添加会员宝收银台活动分润模版成功");
	                    }
					}
				   
				}
			} catch (Exception e) {
				log.error("添加会员宝收银台活动分润模版异常：" + e);
				json.setMsg("添加会员宝收银台活动分润模版失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 查询SYT月度成本查询---详细展示
	 */
	public void QUERYSytprofitmicroLogDetail() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
			json.setSuccess(false);
			json.setMsg("登录信息过期,请重新登录");
			super.writeJson(json);
		} else {
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			try {				
				list=checkUnitProfitMicroService.queryupdateSytProfitmicroLogDetail(cptm,(UserBean)userSession);
			} catch (Exception e) {
				log.error("修改分润模版异常：" + e);
			}
			super.writeJson(list);
		}
	}
	
	/**
	 * 代理商MPOS活动交易分润汇总导出
	 */
	public void exportUnitMposTemplateDetailDataScan(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try{
			HSSFWorkbook wb=checkUnitProfitMicroService.exportUnitMposTemplateDetailDataScan(cptm,((UserBean)userSession));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + new String("MPOS活动交易分润汇总导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
			OutputStream ouputStream= response.getOutputStream();
			wb.write(ouputStream);  
			ouputStream.flush();  
			ouputStream.close();
			json.setSuccess(true);
			json.setMsg("导出MPOS活动交易分润汇总成功");
		} catch (Exception e) {
			log.error("导出MPOS活动交易分润汇总异常：" + e);
			json.setMsg("导出MPOS活动交易分润汇总失败");
		}
	}
	/**
	 * 代理商MPOS活动提现分润汇总导出
	 */
	public void exportMposUnitProfitMicroSumDataCash(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try{
			HSSFWorkbook wb=checkUnitProfitMicroService.exportMposUnitProfitMicroSumDataCash(cptm,((UserBean)userSession));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + new String("MPOS活动提现分润汇总导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
			OutputStream ouputStream= response.getOutputStream();
			wb.write(ouputStream);  
			ouputStream.flush();  
			ouputStream.close();
			json.setSuccess(true);
			json.setMsg("导出MPOS活动提现分润汇总成功");
		} catch (Exception e) {
			log.error("导出MPOS活动提现分润汇总异常：" + e);
			json.setMsg("导出MPOS活动提现提现分润汇总失败");
		}
	}
	/**
	 * 代理商收银台提现转账分润汇总导出
	 */
	public void exportUnitProfitMicroSumDataCash2(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try{
			HSSFWorkbook wb=checkUnitProfitMicroService.exportUnitProfitMicroSumDataCash2(cptm,((UserBean)userSession));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + new String("收银台提现转账分润汇总导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
			OutputStream ouputStream= response.getOutputStream();
			wb.write(ouputStream);  
			ouputStream.flush();  
			ouputStream.close();
			json.setSuccess(true);
			json.setMsg("导出收银台提现转账分润汇总成功");
		} catch (Exception e) {
			log.error("导出收银台提现转账分润汇总异常：" + e);
			json.setMsg("导出收银台提现转账分润汇总失败");
		}
	}
	/**
	 * 代理商收银台分润汇总导出
	 */
	public void exportUnitSytTemplateDetailDataScan(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try{
			HSSFWorkbook wb=checkUnitProfitMicroService.exportUnitSytTemplateDetailDataScan(cptm,((UserBean)userSession));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + new String("收银台分润汇总导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
			OutputStream ouputStream= response.getOutputStream();
			wb.write(ouputStream);  
			ouputStream.flush();  
			ouputStream.close();
			json.setSuccess(true);
			json.setMsg("导出收银台分润汇总成功");
		} catch (Exception e) {
			log.error("导出收银台分润汇总异常：" + e);
			json.setMsg("导出收银台分润汇总失败");
		}
	}
	/**
	 * 代理商手刷刷卡分润汇总导出
	 */
	public void exportUnitProfitMicroSumData(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try{
			HSSFWorkbook wb=checkUnitProfitMicroService.exportUnitProfitMicroSumData(cptm,((UserBean)userSession));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + new String("手刷刷卡分润汇总导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
			OutputStream ouputStream= response.getOutputStream();
			wb.write(ouputStream);  
			ouputStream.flush();  
			ouputStream.close();
			json.setSuccess(true);
			json.setMsg("导出手刷刷卡分润汇总成功");
		} catch (Exception e) {
			log.error("导出手刷刷卡分润汇总异常：" + e);
			json.setMsg("导出手刷刷卡分润汇总失败");
		}
	}
	
	/**
	 * 代理商手刷扫码分润汇总导出
	 */
	public void exportUnitProfitMicroSumDataScan(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try{
			HSSFWorkbook wb=checkUnitProfitMicroService.exportUnitProfitMicroSumDataScan(cptm,((UserBean)userSession));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + new String("手刷扫码分润汇总导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
			OutputStream ouputStream= response.getOutputStream();
			wb.write(ouputStream);  
			ouputStream.flush();  
			ouputStream.close();
			json.setSuccess(true);
			json.setMsg("导出手刷扫码分润汇总成功");
		} catch (Exception e) {
			log.error("导出手刷扫码分润汇总异常：" + e);
			json.setMsg("导出手刷扫码分润汇总失败");
		}
	}
	/**
	 * 代理商手刷提现转账分润汇总导出
	 */
	public void exportUnitProfitMicroSumDataCash(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try{
			HSSFWorkbook wb=checkUnitProfitMicroService.exportUnitProfitMicroSumDataCash(cptm,((UserBean)userSession));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + new String("手刷提现转账分润汇总导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
			OutputStream ouputStream= response.getOutputStream();
			wb.write(ouputStream);  
			ouputStream.flush();  
			ouputStream.close();
			json.setSuccess(true);
			json.setMsg("导出手刷提现转账分润汇总成功");
		} catch (Exception e) {
			log.error("导出手刷提现转账分润汇总异常：" + e);
			json.setMsg("导出手刷提现转账分润汇总失败");
		}
	}
}