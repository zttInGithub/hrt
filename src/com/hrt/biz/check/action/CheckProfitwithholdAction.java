package com.hrt.biz.check.action;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.check.entity.pagebean.CheckProfitwithholdBean;
import com.hrt.biz.check.service.CheckProfitwithholdService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 中心代扣款
 *
 */
public class CheckProfitwithholdAction extends BaseAction implements ModelDriven<CheckProfitwithholdBean> {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(CheckProfitwithholdAction.class);

	private CheckProfitwithholdBean checkProfitwithholdBean = new CheckProfitwithholdBean();
	private CheckProfitwithholdService checkProfitwithholdService;

	@Override
	public CheckProfitwithholdBean getModel() {
		return checkProfitwithholdBean;
	}

	public CheckProfitwithholdBean getCheckProfitwithholdBean() {
		return checkProfitwithholdBean;
	}

	public void setCheckProfitwithholdBean(CheckProfitwithholdBean checkProfitwithholdBean) {
		this.checkProfitwithholdBean = checkProfitwithholdBean;
	}

	public CheckProfitwithholdService getCheckProfitwithholdService() {
		return checkProfitwithholdService;
	}

	public void setCheckProfitwithholdService(CheckProfitwithholdService checkProfitwithholdService) {
		this.checkProfitwithholdService = checkProfitwithholdService;
	}

	/**
	 * 查询记录
	 */
	public void queryWithholdInfo() {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user = (UserBean) userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = checkProfitwithholdService.queryWithholdInfo(checkProfitwithholdBean, user);
		} catch (Exception e) {
			log.info("查询代扣分润信息" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 一代确认操作
	 */
	public void yidaiProfitWithholdEdit() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		String flag =  super.getRequest().getParameter("flag");
		checkProfitwithholdBean.setApproveStatus(flag);
		UserBean user = (UserBean) userSession;
		try {
			checkProfitwithholdService.yidaiProfitWithholdEdit(checkProfitwithholdBean, user);
			json.setSuccess(true);
			json.setMsg("操作成功");
		} catch (Exception e) {
			log.info("一代代扣分润信息确认" + e);
			json.setMsg(e.getMessage());
		}
		super.writeJson(json);
	}

	/**
	 * 微信交易明细导出所有
	 */
	
	public void profitWithholdExport() {
		JsonBean json = new JsonBean();
		List<Map<String, Object>> list = null;
		List<String[]> cotents = new ArrayList<String[]>();
		String titles[] = { "申请方", "代扣金额", "扣款来源", "提交时间", "生效月", "一代机构号","一代机构名称","状态","归属中心机构号","归属中心名称"};
		cotents.add(titles);
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession != null) {
				list = checkProfitwithholdService.profitWithholdExport(checkProfitwithholdBean, (UserBean) userSession);
				if (list != null && list.size() > 0) {
					for (Map<String, Object> map : list) {
						String rowCoents[] = { map.get("YUNYINGZHONGXINNAME") == null ? "" : map.get("YUNYINGZHONGXINNAME").toString(),
								map.get("WITHHOLDINGAMOUNT") == null ? "" : map.get("WITHHOLDINGAMOUNT").toString(),
								map.get("DEDUCTIONSOURCE") == null ? "" : map.get("DEDUCTIONSOURCE").toString(),
								map.get("MAINTAINDATE") == null ? "" : map.get("MAINTAINDATE").toString(),
								map.get("EFFECTIVEDATE") == null ? "" : map.get("EFFECTIVEDATE").toString(),
								map.get("EXECUTINGUNNO") == null ? "" : map.get("EXECUTINGUNNO").toString(),
								map.get("EXECUTINGUNNONAME") == null ? "" : map.get("EXECUTINGUNNONAME").toString(),
								map.get("APPROVESTATUS") == null ? "" : map.get("APPROVESTATUS").toString(),
								map.get("APPLICANTUNNO") == null ? "" : map.get("APPLICANTUNNO").toString(),
								map.get("YUNYINGZHONGXINNAME") == null ? "" : map.get("YUNYINGZHONGXINNAME").toString(),
								};
						cotents.add(rowCoents);
					}
				}
				String excelName = "代扣分润数据.csv";
				JxlOutExcelUtil.writeCSVFile(cotents, titles.length, getResponse(), excelName);
				json.setSuccess(true);
				json.setMsg("代扣分润数据导出成功");
			}
		} catch (Exception e) {
			log.error("代扣分润数据导出异常：" + e);
			json.setSuccess(false);
			json.setMsg("代扣分润数据导出");
			super.writeJson(json);
		}
	}
	
	/**
	 * 添加记录
	 */
	public void addprofitWithholdInfo() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user = (UserBean) userSession;
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {			
			try {
				//添加需要判断当前登录机构是否为运营中心
				if(user.getUnitLvl()!=1) {
					json.setSuccess(false);
					json.setMsg("您无此添加权限");
					super.writeJson(json);
					return;
				}
				if(checkProfitwithholdBean.getExecutingUnno()==null||checkProfitwithholdBean.getWithholdingAmount()==null) {
					json.setSuccess(false);
					json.setMsg("请确保所有信息已填写");
					super.writeJson(json);
					return;
				}
				//判断代扣机构是否为1代，本机构下级代理，一个自然月内的状态
				String flag= checkProfitwithholdService.judgeProfitWithholdInfo(checkProfitwithholdBean,user);
				if(!"可添加".equals(flag)){
					json.setSuccess(false);
					json.setMsg(flag);
				}else{
					checkProfitwithholdService.addprofitWithholdInfo(checkProfitwithholdBean, user);
					json.setSuccess(true);
					json.setMsg("添加代扣记录成功");
				}
			} catch (Exception e) {
				log.error("添加代扣记录异常：" + e);
				json.setMsg("添加代扣记录失败");
			}
		}
		super.writeJson(json);
	}
	
}
