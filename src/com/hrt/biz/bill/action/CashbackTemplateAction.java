package com.hrt.biz.bill.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.hrt.biz.bill.entity.pagebean.CashbackTemplateBean;
import com.hrt.biz.bill.service.ICashbackTemplateService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.DateTools;
import com.hrt.util.ExcelServiceImpl;
import com.hrt.util.JxlOutExcelUtil;
import com.opensymphony.xwork2.ModelDriven;

/**
 *   返现模板
 * */
public class CashbackTemplateAction extends BaseAction implements ModelDriven<CashbackTemplateBean>{
	
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(CashbackTemplateAction.class);
	
	private CashbackTemplateBean cashbackTemplateBean = new CashbackTemplateBean();
	
	private ICashbackTemplateService cashbackTemplateService;
	
	@Override
	public CashbackTemplateBean getModel() {
		return cashbackTemplateBean;
	}
	
	public CashbackTemplateBean getCashbackTemplateBean() {
		return cashbackTemplateBean;
	}
	public void setCashbackTemplateBean(CashbackTemplateBean cashbackTemplateBean) {
		this.cashbackTemplateBean = cashbackTemplateBean;
	}
	public ICashbackTemplateService getCashbackTemplateService() {
		return cashbackTemplateService;
	}
	public void setCashbackTemplateService(ICashbackTemplateService cashbackTemplateService) {
		this.cashbackTemplateService = cashbackTemplateService;
	}
	
	File upload=null;

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	
	/**
	 * 中心及一代税点查询
	 */
	public void queryCashbackTaxPoint(){
		DataGridBean dgd = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dgd = cashbackTemplateService.queryCashbackTaxPoint(cashbackTemplateBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("中心及一代税点查询异常：" + e);
		}
		super.writeJson(dgd);
	}
	
	/**
	 * 中心及一代税点--导出
	 */
	public void reportCashbackTaxPoint() {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			String excelName;
			excelName = "中心及一代税点.csv";
			List<Map<String, Object>> list = cashbackTemplateService.reportCashbackTaxPoint(cashbackTemplateBean,((UserBean)userSession));
			
			String[] titles = { "中心及一代机构号","归属上级机构号","活动编码","返现类型","税点"};
			List excelList = new ArrayList();
			excelList.add(titles);
			try {
				for (int rowId = 0; rowId < list.size(); rowId++) {
					Map order = list.get(rowId);
					String[] rowContents = { 
							String.valueOf(order.get("UNNO")==null?"":order.get("UNNO")),
							String.valueOf(order.get("UPPERUNNO")==null?"":order.get("UPPERUNNO")),
							String.valueOf(order.get("REBATETYPE")==null?"":order.get("REBATETYPE")),
							String.valueOf(order.get("CASHBACKTYPE")==null?"":order.get("CASHBACKTYPE")),
							String.valueOf(order.get("TAXPOINT")==null?"":order.get("TAXPOINT"))
					};
					excelList.add(rowContents);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			list=null;
			String[] cellFormatType = { "t","t","t","t","t"};
			JxlOutExcelUtil.writeCSVFile(excelList, titles.length, getResponse(), excelName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 中心一代--根据id查询
	 */
	public void queryCashbackTaxPointForId(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		cashbackTemplateBean.setNid(Integer.parseInt(getRequest().getParameter("id")));
		Map map=null;
		try {
			map = cashbackTemplateService.queryCashbackTaxPointForId(cashbackTemplateBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("查询中心一代税点信息异常：" + e);
		}
		super.writeJson(map);
	}
	
	/**
	 * 中心一代税点--修改
	 */
	public void updateCashbackTaxPoint(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {			
			try {
				if(cashbackTemplateBean.getTaxpoint()==null) {
					json.setSuccess(false);
					json.setMsg("请确保所有信息已填写");
					super.writeJson(json);
					return;
				}
				String string = cashbackTemplateService.updateCashbackTaxPoint(cashbackTemplateBean, (UserBean) userSession);
				if(!"修改成功".equals(string)) {
					json.setSuccess(false);
					json.setMsg("修改中心一代税点失败");
					super.writeJson(json);
					return;
				}
				json.setSuccess(true);
				json.setMsg("修改中心一代税点成功");
			} catch (Exception e) {
				log.error("修改中心一代税点异常：" + e);
				json.setMsg("修改中心一代税点失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 批量变更模板下载
	 */
	public void downloadCashbackTaxPointUpdateXls(){
		try {
			ExcelServiceImpl.download(getResponse(),"CashbackTaxPointUpdateXls.xls","批量创建与变更模板.xls");
		} catch (Exception e) {
			log.info("批量创建与变更模板下载失败:"+e);
		}
	}
	
	/**
     * 批量开通或者变更模板导入
     */
    public void importCashbackTaxPointUpdate(){
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user=(UserBean)userSession;
        if(user == null){
            json.setSessionExpire(true);
            super.writeJson(json);
            return;
        }
        String UUID = System.currentTimeMillis()+"";
        String fileName = UUID+ ServletActionContext.getRequest().getParameter("fileContact");
        String basePath = ServletActionContext.getRequest().getRealPath("/upload");
        File dir = new File(basePath);
        if(!dir.exists())
            dir.mkdir();
        String path = basePath +"/"+fileName;
        File destFile = new File(path);
        upload.renameTo(destFile);
        String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
        String xlsfile = folderPath +"/"+fileName;
        log.info("批量变更文件导入Excel文件路径:"+xlsfile);
        try {
            if(xlsfile.length() > 0 && xlsfile != null){
                String message = cashbackTemplateService.importCashbackTaxPointUpdate(xlsfile,fileName,user);
                if(StringUtils.isEmpty(message)){
                    json.setSuccess(true);
                    json.setMsg("批量变更文件导入成功!");
                }else{
                    json.setSuccess(false);
                    json.setMsg(message);
                }
            }else{
                json.setSuccess(false);
                json.setMsg("导入文件异常!");
            }
        } catch (Exception e) {
            log.info("批量变更文件导入异常:"+e);
            json.setSuccess(false);
            json.setMsg("批量变更文件导入失败!");
        }
        ExcelServiceImpl.deleteUploadFile(folderPath,path);
        super.writeJson(json);
    }
    
    /**
	 * 下级代理返现设置记录查询
	 */
	public void queryCashbackTemplateLog(){
		DataGridBean dgd = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dgd = cashbackTemplateService.queryCashbackTemplateLog(cashbackTemplateBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("下级代理返现设置记录查询异常：" + e);
		}
		super.writeJson(dgd);
	}
	
	/**
	 * 返现钱包模板日志--导出
	 */
	public void reportCashbackTemplateLog() {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			String excelName;
			excelName = "返现钱包模板日志记录.csv";
			List<Map<String, Object>> list = cashbackTemplateService.reportCashbackTemplateLog(cashbackTemplateBean,((UserBean)userSession));
			
			String[] titles = { "下级代理机构号","下级代理机构名称","活动编码","返现类型","下级代理可分比例","起始时间","结束时间"};
			List excelList = new ArrayList();
			excelList.add(titles);
			try {
				for (int rowId = 0; rowId < list.size(); rowId++) {
					Map order = list.get(rowId);
					String[] rowContents = { 
							String.valueOf(order.get("UNNO")==null?"":order.get("UNNO")),
							String.valueOf(order.get("UNNONAME")==null?"":order.get("UNNONAME")),
							String.valueOf(order.get("REBATETYPE")==null?"":order.get("REBATETYPE")),
							String.valueOf(order.get("CASHBACKTYPE")==null?"":order.get("CASHBACKTYPE")),
							String.valueOf(order.get("CASHBACKRATIO")==null?"":order.get("CASHBACKRATIO")),
							String.valueOf(order.get("STARTDATE")==null?"":order.get("STARTDATE")),
							String.valueOf(order.get("ENDDATE")==null?"至今":order.get("ENDDATE"))
					};
					excelList.add(rowContents);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			list=null;
			String[] cellFormatType = { "t","t","t","t","t"};
			JxlOutExcelUtil.writeCSVFile(excelList, titles.length, getResponse(), excelName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询下级代理机构号
	 */
	public void getChildrenUnit(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgd = new DataGridBean();
		String nameCode=super.getRequest().getParameter("q");
		if(user == null) {
		}else {
			try{
				dgd=cashbackTemplateService.getChildrenUnit(user,nameCode);
			}catch(Exception e){
				log.error("查询下级代理机构号异常：" + e);
			}
		}
		super.writeJson(dgd);
	}
	
	/**
	 * 下级代理返现模板机构查询
	 */
	public void queryCashbackTemplateUnno(){
		DataGridBean dgd = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dgd = cashbackTemplateService.queryCashbackTemplateUnno(cashbackTemplateBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("下级代理返现模板机构查询异常：" + e);
		}
		super.writeJson(dgd);
	}
	
	/**
	 * 根据机构查询其所有返现模板
	 */
	public void queryCashbackTemplateForUnno(){
		//DataGridBean dgd = new DataGridBean();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			list = cashbackTemplateService.queryCashbackTemplateForUnno(cashbackTemplateBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("下级代理返现模板机构查询异常：" + e);
		}
		super.writeJson(list);
	}
	
	/**
	 * 修改返现模板
	 */
	public void updateCashbackTemplateNext(){
		JsonBean json = new JsonBean();
		boolean b = DateTools.isFirstDay();
		if(b) {
			json.setSuccess(false);
			json.setMsg("1号为系统维护周期，请于当月2号以后进行变更操作");
			super.writeJson(json);
			return;
		}
		
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				String emptyMsg = cashbackTemplateService.validateCashbackTemplateNotEmpty(cashbackTemplateBean,(UserBean) userSession,2);
				if(emptyMsg!=null) {
					json.setSuccess(false);
					json.setMsg(emptyMsg);
				}else {
					cashbackTemplateService.updateCashbackTemplateNext(cashbackTemplateBean, (UserBean) userSession);
					json.setSuccess(true);
					json.setMsg("修改返现模板成功");
				}
			} catch (Exception e) {
				log.error("修改返现模板异常：" + e);
				json.setMsg("修改返现模板失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 添加返现模板
	 */
	public void addCashbackTemplate(){
		JsonBean json = new JsonBean();
//		boolean b = DateTools.isFirstDay();
//		if(b) {
//			json.setSuccess(false);
//			json.setMsg("1号为系统维护周期，请于当月2号以后进行变更操作");
//			super.writeJson(json);
//			return;
//		}
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				String emptyMsg = cashbackTemplateService.validateCashbackTemplateNotEmpty(cashbackTemplateBean,(UserBean) userSession,1);
				if(emptyMsg!=null) {
					json.setSuccess(false);
					json.setMsg(emptyMsg);
				}else {
					cashbackTemplateService.addCashbackTemplateNext(cashbackTemplateBean, (UserBean) userSession);
					json.setSuccess(true);
					json.setMsg("添加返现模板成功");
				}
			} catch (Exception e) {
				log.error("添加返现模板异常：" + e);
				json.setMsg("添加返现模板失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 返现钱包模板删除
	 */
	public void deleteCashbackTemplate(){
		JsonBean json = new JsonBean();
//		boolean b = DateTools.isFirstDay();
//		if(b) {
//			json.setSuccess(false);
//			json.setMsg("1号为系统维护周期，请于当月2号以后进行变更操作");
//			super.writeJson(json);
//			return;
//		}
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			cashbackTemplateService.deleteCashbackTemplate(cashbackTemplateBean,(UserBean)userSession);
			json.setMsg("删除成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg("删除失败！");
			json.setSuccess(false);
			log.error("返现钱包模板删除异常：" + e);
		}
		super.writeJson(json);
	}
	
	/**
	 * 查询活动类型配置
	 */
	public void listRebateRate(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				dgb = cashbackTemplateService.listRebateRate(cashbackTemplateBean,(UserBean)userSession);
			}
		} catch (Exception e) {
			log.error("查询活动类型配置异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
     * 查询活动返现类型
     */
    public void queryRebatetypeCashbackType() {
        DataGridBean dgd = new DataGridBean();
        try {
            String type = super.getRequest().getParameter("rebateType");
            if(type==null) {
            }else {
            	dgd = cashbackTemplateService.queryRebatetypeCashbackType(type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.writeJson(dgd);
    }
    
    /**
	 * 返现明细查询
	 */
	public void queryCashbackDetail(){
		DataGridBean dgd = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dgd = cashbackTemplateService.queryCashbackDetail(cashbackTemplateBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("返现明细查询异常：" + e);
		}
		super.writeJson(dgd);
	}
	
	/**
	 * 返现明细--导出
	 */
	public void reportCashbackDetail() {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			String excelName;
			excelName = "返现明细.csv";
			List<Map<String, Object>> list = cashbackTemplateService.reportCashbackDetail(cashbackTemplateBean,((UserBean)userSession));
			
			String[] titles = { "归属本级机构号","归属下级代理机构号","归属下级代理机构名称","mid","sn","交易金额","本级返现总盘金额","返现日期","活动编码","返现类型"};
			List excelList = new ArrayList();
			excelList.add(titles);
			try {
				for (int rowId = 0; rowId < list.size(); rowId++) {
					Map order = list.get(rowId);
					String[] rowContents = { 
							String.valueOf(order.get("UPP_UNNO")==null?"":order.get("UPP_UNNO")),
							String.valueOf(order.get("UNNO")==null?"":order.get("UNNO")),
							String.valueOf(order.get("UN_NAME")==null?"":order.get("UN_NAME")),
							String.valueOf(order.get("MID")==null?"":order.get("MID")),
							String.valueOf(order.get("SN")==null?"":order.get("SN")),
							String.valueOf(order.get("SAMT")==null?"":order.get("SAMT")),
							String.valueOf(order.get("CASHBACK_AMT")==null?"":order.get("CASHBACK_AMT")),
							String.valueOf(order.get("CDATE")==null?"":order.get("CDATE")),
							String.valueOf(order.get("REBATETYPE")==null?"":order.get("REBATETYPE")),
							String.valueOf(order.get("CASHBACKTYPE")==null?"":order.get("CASHBACKTYPE"))
					};
					excelList.add(rowContents);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			list=null;
			String[] cellFormatType = { "t","t","t","t","t","t","t","t","t","t"};
			JxlOutExcelUtil.writeCSVFile(excelList, titles.length, getResponse(), excelName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
