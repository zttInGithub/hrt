package com.hrt.biz.bill.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.hrt.util.UsePioOutExcel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.biz.bill.entity.pagebean.AgentUnitBean;
import com.hrt.biz.bill.service.IAgentUnitService;
import com.hrt.biz.util.constants.HrtCostConstant;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.ExcelServiceImpl;
import com.hrt.util.FileZipUtil;
import com.hrt.util.JxlOutExcelUtil;
import com.hrt.util.word;
import com.opensymphony.xwork2.ModelDriven;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 代理商管理
 */
public class AgentUnitAction extends BaseAction implements ModelDriven<AgentUnitBean>{

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(AgentUnitAction.class);
	
	private AgentUnitBean agentUnit = new AgentUnitBean();
	
	private IAgentUnitService agentUnitService;
	
	private static final String TITLE="FRUpload";
	
	private String buids;
	private File upload=null;    //照片
	private File upload1=null;	 //成本
	
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}

	public File getUpload1() {
		return upload1;
	}
	public void setUpload1(File upload1) {
		this.upload1 = upload1;
	}
	@Override
	public AgentUnitBean getModel() {
		return agentUnit;
	}

	public IAgentUnitService getAgentUnitService() {
		return agentUnitService;
	}

	public void setAgentUnitService(IAgentUnitService agentUnitService) {
		this.agentUnitService = agentUnitService;
	}

	public String getBuids() {
		return buids;
	}
	public void setBuids(String buids) {
		this.buids = buids;
	}

	/**
	 * 查询表格机构
	 */
	public void listUnits(){
		DataGridBean dataGridBean = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				dataGridBean = agentUnitService.queryUnits(agentUnit,((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("查询菜单表格树异常：" + e);
		}
		super.writeJson(dataGridBean);
	}

	/**
	 * 导出表格机构
	 */
	public void exportUnits(){
		JsonBean json = new JsonBean();
		List<Map<String, Object>>list = null;
		List<String[]>cotents = new ArrayList<String[]>();
		String titles[] = {"机构名称","机构编号","上级机构","级别","创建时间","创建者"};
		cotents.add(titles);
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				list = agentUnitService.exportUnits(agentUnit,(UserBean)userSession);
				if (list!=null&&list.size()>0) {
					for (Map<String, Object>map  : list) {
						String rowCoents[] = {
								map.get("UN_NAME")==null?"":map.get("UN_NAME").toString(),
								map.get("UNNO")==null?"":map.get("UNNO").toString(),
								map.get("UPPER_UNIT")==null?"":map.get("UPPER_UNIT").toString(),		
								map.get("UN_LVL")==null?"":map.get("UN_LVL").toString(),
								map.get("CREATE_DATE")==null?"":map.get("CREATE_DATE").toString(),
								map.get("CREATE_USER")==null?"":map.get("CREATE_USER").toString()
						};
						cotents.add(rowCoents);
					}
				}
				String excelName = "各级机构资料";
				String sheetName = "各级机构资料";
				String [] cellFormatType = {"t","t","t","t","t","t"};
				JxlOutExcelUtil.writeExcel(cotents, 6, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("各级机构资料导出成功");
			}
		} catch (Exception e) {
			log.error("各级机构资料导出异常：" + e);
			json.setSuccess(false);
			json.setMsg("各级机构资料导出失败");
			super.writeJson(json);
		}
	}
	
	/**
	 * 查询代理商信息（未缴款）
	 */
	public void listAgentUnit(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = agentUnitService.queryAgentUnit(agentUnit,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询代理商信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询代理商信息（未缴款，运营中心角度）
	 */
	public void listAgentUnit00410(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = agentUnitService.queryAgentUnit00410(agentUnit,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询代理商信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询代理商信息（不管开通、缴款状态）
	 */
	public void listAgentUnitData(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = agentUnitService.queryAgentUnitData(agentUnit,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询代理商信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询代理商信息（修改）
	 */
	public void listAgentUnitDataForUpdate(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = agentUnitService.queryAgentUnitDataForUpdate(agentUnit,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询代理商信息（修改）：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询代理商信息（不管开通、缴款状态）
	 */
	public void listAgentUnitData1(){
		DataGridBean dgb = new DataGridBean();
		try {
//			if((agentUnit.getUnno()==null||"".equals(agentUnit.getUnno()))&&(agentUnit.getAgentName()==null||"".equals(agentUnit.getAgentName()))){
//			}else{
				Object userSession = super.getRequest().getSession().getAttribute("user");
				dgb = agentUnitService.queryAgentUnitDataBeiJing(agentUnit,((UserBean)userSession));
//			}
		} catch (Exception e) {
			log.error("分页查询代理商信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询运营中心资料
	 */
	public void listAgentUnitData10142(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = agentUnitService.queryAgentUnitData10142(agentUnit,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询代理商信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 添加代理商
	 */
	public void addAgentUnit() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				//验证 代理商全称 唯一
				Integer flag= agentUnitService.queryAgentUnitNameExits(agentUnit.getAgentName(),agentUnit.getBuid());
				if(flag>0){
					json.setSuccess(false);
					json.setMsg("当前代理商全称已存在，代理商签约失败！");
				}else{
					Integer flag2= agentUnitService.queryShortNmNameExits(agentUnit.getAgentShortNm(),agentUnit.getBuid());
					if(flag2>0){
						json.setSuccess(false);
						json.setMsg("当前代理商简称已存在，代理商签约失败！");
					}else{
						// TODO @author:lxg-20200518 成本上限校验
						String tip = agentUnitService.validateCostByLimit(agentUnit,((UserBean)userSession));
						if(StringUtils.isNotEmpty(tip)){
							json.setSuccess(false);
							json.setMsg(tip);
						}else {
							agentUnitService.saveAgentUnit(agentUnit, ((UserBean) userSession));
							json.setSuccess(true);
							json.setMsg("代理商签约成功");
						}
					}
				}
			} catch (Exception e) {
				log.error("代理商签约异常：" + e);
				json.setMsg("代理商签约失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 添加代理商-运营中心
	 */
	public void addAgentUnit00410() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效 
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				Integer flag= agentUnitService.queryAgentUnitNameExits(agentUnit.getAgentName(),agentUnit.getBuid());
				if(flag>0){
					json.setSuccess(false);
					json.setMsg("当前代理商全称已存在，代理商签约失败！");
				}else{
					Integer flag2= agentUnitService.queryShortNmNameExits(agentUnit.getAgentShortNm(),agentUnit.getBuid());
					if(flag2>0){
						json.setSuccess(false);
						json.setMsg("当前代理商简称已存在，代理商签约失败！");
					}else{
						// TODO @author:lxg-20200518 成本上限校验
						String tip=agentUnitService.validateCostByLimit(agentUnit, ((UserBean)userSession));
						if(StringUtils.isNotEmpty(tip)){
							json.setSuccess(false);
							json.setMsg(tip);
						}else {
							agentUnitService.saveAgentUnit00410(agentUnit, ((UserBean) userSession));
							json.setSuccess(true);
							json.setMsg("代理商签约成功");
						}
					}
				}
			} catch (Exception e) {
				log.error("代理商签约异常：" + e);
				json.setMsg("代理商签约失败");
			}
		}
		super.writeJson(json);
	}

    /**
     * 获取运营中心的成本信息
     */
	public void getYunYingHrtCost00310() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			UserBean userBean = (UserBean) userSession;
			log.info("运营中心成本获取:"+userBean.getUnNo());
			Map map = agentUnitService.getYunYingHrtUnnoCost(userBean.getUnNo(),1);
			if(null!=map){
				json.setSuccess(true);
				json.setMsg(JSONObject.toJSONString(map));
			}else{
				json.setSuccess(false);
				json.setMsg("失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 修改代理商信息
	 */
	public void editAgentUnit(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				Integer flag= agentUnitService.queryAgentUnitNameExits2(agentUnit);
				if(flag>0){
					json.setSuccess(false);
					json.setMsg("当前代理商全称已存在，代理商修改失败！");
				}else{
					Integer flag2= agentUnitService.queryShortNmNameExits2(agentUnit);
					if(flag2>0){
						json.setSuccess(false);
						json.setMsg("当前代理商简称已存在，代理商修改失败！");
					}else{
						// TODO @author:lxg-20200518 成本上限校验
						String tip = agentUnitService.validateCostByLimit(agentUnit,((UserBean)userSession));
						if(StringUtils.isNotEmpty(tip)){
							json.setSuccess(false);
							json.setMsg(tip);
						}else {
							agentUnitService.updateAgentUnit(agentUnit, ((UserBean) userSession));
							json.setSuccess(true);
							json.setMsg("修改代理商签约信息成功");
						}
					}
				}
			} catch (Exception e) {
				log.error("修改代理商签约异常" + e);
				json.setMsg("修改代理商签约失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 修改运营中心信息
	 */
	public void editAgentUnit00410(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				Integer flag= agentUnitService.queryAgentUnitNameExits(agentUnit.getAgentName(),agentUnit.getBuid());
				if(flag>0){
					json.setSuccess(false);
					json.setMsg("当前代理商全称已存在，修改代理商签约信息失败！");
				}else{
					Integer flag2= agentUnitService.queryShortNmNameExits(agentUnit.getAgentShortNm(),agentUnit.getBuid());
					if(flag2>0){
						json.setSuccess(false);
						json.setMsg("当前代理商简称已存在，代理商修改失败！");
					}else{
                        // TODO @author:lxg-20200518 成本上限校验
                        String tip = agentUnitService.validateCostByLimit(agentUnit,((UserBean)userSession));
                        if(StringUtils.isNotEmpty(tip)){
                            json.setSuccess(false);
                            json.setMsg(tip);
                        }else {
                            agentUnitService.updateAgentUnit00410(agentUnit, ((UserBean) userSession));
                            json.setSuccess(true);
                            json.setMsg("修改代理商签约信息成功");
                        }
					}
				}
			} catch (Exception e) {
				log.error("修改代理商签约异常" + e);
				json.setMsg("修改代理商签约失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 查询代理商信息（未缴款并且风险保障金大于0）
	 */
	public void listAgentConfirm(){
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = agentUnitService.queryAgentConfirm(agentUnit);
		} catch (Exception e) {
			log.error("分页查询代理商信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 确认代理商缴款
	 */
	public void editAgentConfirm(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				agentUnitService.updateAgentConfirm(agentUnit, ((UserBean)userSession));
				json.setSuccess(true);
				json.setMsg("代理商缴款成功");
			} catch (Exception e) {
				log.error("代理商缴款异常" + e);
				json.setMsg("代理商缴款失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 查询代理商信息（保障金大于0已缴款未开通）或（保障金等于0未开通）
	 */
	public void listAgentOpen(){
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = agentUnitService.queryAgentOpen(agentUnit,false);
		} catch (Exception e) {
			log.error("分页查询代理商信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 *  查询代理商信息-清算部（保障金大于0已缴款未开通）或（保障金等于0未开通）
	 */
	public void listAgentOpenForClearing(){
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = agentUnitService.queryAgentOpen(agentUnit,true);
		} catch (Exception e) {
			log.error("分页查询代理商信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	public void listAgentDlUnit3() {
		DataGridBean dataGridBean = new DataGridBean();
		try {
			dataGridBean = agentUnitService.queryAgentDlUnitList3();
		} catch (Exception e) {
			log.error("分页查询分销代理异常"+e);
		}
		super.writeJson(dataGridBean);
	}
	/**
	 * 确认代理商开通
	 */
	public void editAgentOpen(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
//				if(StringUtil.isNotEmpty(agentUnit.getUnitCode())
//						&&agentUnit.getUnitCode().length()==2){
//					agentUnit.setUnitCode(agentUnit.getUnitCode()+" ");
//				}
//				int falg=agentUnitService.queryUnitCounts(agentUnit.getUnitCode());
//				if(falg>0){
//					json.setSuccess(false);
//					json.setMsg("机构简码已存在");
//				}else{
					agentUnitService.updateAgentOpen(agentUnit, ((UserBean)userSession));
					json.setSuccess(true);
					json.setMsg("代理商开通成功");
//				}
			} catch (Exception e) {
				log.error("代理商开通异常" + e);
				json.setSuccess(false);
				json.setMsg("代理商开通失败");
			}
		}
		
		super.writeJson(json);
	}
	
	
	
	public void editAgentOpenToC(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				agentUnitService.updateAgentToC(agentUnit, ((UserBean)userSession));
				json.setSuccess(true);
				json.setMsg("代理商开通成功");
			} catch (Exception e) {
				log.error("代理商开通异常" + e);
				json.setMsg("代理商开通失败");
			}
		}
		super.writeJson(json);
	}

	/**
	 * 退回代理商开通
	 */
	public void editAgentToK(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				agentUnitService.updateAgentToK(agentUnit, ((UserBean)userSession));
				json.setSuccess(true);
				json.setMsg("退回成功");
			} catch (Exception e) {
				log.error("代理商退回异常" + e);
				json.setMsg("退回失败");
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 删除代理商信息
	 */
	public void deleteAgentUnit(){
		JsonBean json = new JsonBean();
		try {
			agentUnitService.deleteAgentUnit(Integer.parseInt(buids));
			json.setSuccess(true);
			json.setMsg("删除代理商签约信息成功");
		} catch (Exception e) {
			log.error("删除代理商签约信息异常：" + e);
			json.setMsg("删除代理商签约信息失败");
		}
		super.writeJson(json);
	}
	
	/**
	 * 导出代理商信息excel
	 */
	public void export(){
		String buids=getRequest().getParameter("buids");
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=agentUnitService.export(buids,((UserBean)userSession),agentUnit);
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("代理商资料导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出代理商资料Excel成功");
			} catch (Exception e) {
				log.error("导出代理商资料Excel异常：" + e);
				json.setMsg("导出代理商资料Excel失败");
			}
		}
	}
	/**
	 * 导出代理商信息excel
	 */
	public void export1(){
		String buids=getRequest().getParameter("searchids");
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=agentUnitService.export(buids,((UserBean)userSession),agentUnit);
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("代理商资料导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出代理商资料Excel成功");
			} catch (Exception e) {
				log.error("导出代理商资料Excel异常：" + e);
				json.setMsg("导出代理商资料Excel失败");
			}
		}
	}
	/**
	 * 导出全部代理
	 */
	public void exportAgents(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=agentUnitService.exportAgent(agentUnit,((UserBean)userSession));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("代理商资料导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出代理商资料Excel成功");
			} catch (Exception e) {
				log.error("导出代理商资料Excel异常：" + e);
				json.setMsg("导出代理商资料Excel失败");
			}
		}
	}

	/**
	 * 导入返现数据进行返现状态修改
	 */
	public void importBatchCashDate(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null){
			json.setSessionExpire(true);
		}
		String UUID = System.currentTimeMillis()+"";
		//把文件保存到服务器目录下
		String fileName = UUID+ServletActionContext.getRequest().getParameter("fileContact");
		String basePath = ServletActionContext.getRequest().getRealPath("/upload");
		File dir = new File(basePath);
		if(!dir.exists())
			dir.mkdir();
		String path = basePath +"/"+fileName;
		File destFile = new File(path);
		upload.renameTo(destFile);
		String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
		String xlsfile = folderPath +"/"+fileName;
		if(log.isInfoEnabled()){
			log.info("批量返现导入Excel文件路径:"+xlsfile);
		}
		try {
			if(xlsfile.length() > 0 && xlsfile != null){
				Map resultMap = agentUnitService.importCashStatusByExcel(xlsfile,fileName,user);
				int sumCount = Integer.parseInt(resultMap.get("sumCount").toString());
				int errCount = Integer.parseInt(resultMap.get("errCount").toString());
				int upCount = Integer.parseInt(resultMap.get("upCount").toString());
				if(sumCount!=errCount){
					json.setSuccess(true);
					json.setMsg("批量返现导入成功!-总记录数:"+resultMap.get("sumCount")+",修改记录数:"+resultMap.get("upCount"));
				}else{
					json.setSuccess(false);
					json.setMsg("批量返现导入存在重复机构,所有数据修改失败!");
				}
			}else{
				json.setSuccess(false);
				json.setMsg("导入文件异常!");
			}
		} catch (Exception e) {
			log.info("批量返现导入异常:"+e);
			json.setSuccess(false);
			json.setMsg("批量返现导入失败!");
		}
		ExcelServiceImpl.deleteUploadFile(folderPath,path);
		super.writeJson(json);
	}
	/**
	 * 导出一级代理归属和销售
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public void exportAgentsAndSales() throws RowsExceededException, WriteException, IOException{
		Object userSession = super.getRequest().getSession().getAttribute("user");
		List<Map<String, String>> list = agentUnitService.exportAgentsAndSales(agentUnit,(UserBean)userSession);
		String titles[] = {"机构号","代理商名","销售名","上级机构号","上级代理商名","开通时间"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("AGENTNAME")==null?"":map.get("AGENTNAME").toString(),
					map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
					map.get("UPPER_UNIT")==null?"":map.get("UPPER_UNIT").toString(),		
					map.get("UPPERNAME")==null?"":map.get("UPPERNAME").toString(),
					map.get("CREATE_DATE")==null?"":map.get("CREATE_DATE").toString(),
			};
			excelList.add(rowContents);
		}
		String excelName = "一级代理归属资料";
		String sheetName = "一级代理归属资料";
		String [] cellFormatType = {"t","t","t","t","t","t"};
		
		JxlOutExcelUtil.writeExcel(excelList, 6, getResponse(), sheetName, excelName+".xls", cellFormatType);
	}
	
	/**
	 * 查看明细
	 */
	public void serachAgentInfoDetailed(){
		List<Object> list = agentUnitService.queryAgentInfoDetailed(agentUnit.getBuid());
		super.writeJson(list);
	}
	/**
	 * 查看明细2
	 */
	public void serachAgentInfoDetailed2(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		List<Object> list = agentUnitService.queryAgentInfoDetailed2((UserBean)userSession);
		super.writeJson(list);
	}
	/**
	 * 查询代理提现金额和余额
	 */
	public void queryPurse10160(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {try{
				Object object = agentUnitService.queryPurse((UserBean)userSession);
				json.setObj(object);
				json.setSuccess(true);
				json.setMsg("查询可提现金额成功");
			}catch(Exception e){
				json.setMsg("查询可提现金额失败");
				json.setSuccess(false);
			}
		}
		super.writeJson(json);
	}

	public void queryPurseRc10167(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
		    try{
                UserBean t = (UserBean)userSession;
                Map map = new HashMap();
                // @author:xuegangliu-20190220 查询代理商提现状态-钱包余额
				Integer cashStatus = getCashStatusByUnno(t);

				if(cashStatus==0){
                    map.put("cashStatus",cashStatus);
                    map.put("message","返现提现功能未开通");
                    json.setObj(map);
                    json.setSuccess(true);
                    json.setMsg("返现提现功能未开通");
                }else if(cashStatus==1) {
                    Object object = agentUnitService.queryPurseRc((UserBean) userSession);
                    json.setObj(object);
                    json.setSuccess(true);
                    json.setMsg("查询可提现金额成功");
                }
            }catch(Exception e){
                json.setMsg("查询可提现金额失败");
                json.setSuccess(false);
            }
		}
		super.writeJson(json);
	}

	/**
	 * 根据用户机构查询代理商返现状态
	 * @param t
	 * @return
	 */
	private Integer getCashStatusByUnno(UserBean t) {
		Integer cashStatus = 0;
		if(StringUtils.isNotEmpty(t.getUnNo())){
			AgentUnitModel kk = agentUnitService.getInfoModelByUnno(t.getUnNo());
			if(null!= kk) {
				cashStatus = kk.getCashStatus();
			}
		}
		return cashStatus;
	}

	/**
	 * 转发 代理提现
	 */
	public void takeCurAmt10160(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			if (Float.parseFloat(agentUnit.getCurAmt()) < 500.00) {
				json.setMsg("申请提现失败,金额小于500元");
				json.setSuccess(false);
			} else {
				try {
					Object object = agentUnitService.takeCurAmt((UserBean)userSession,agentUnit.getCurAmt());
					json.setObj(object);
					json.setSuccess(true);
					json.setMsg("申请提现成功");
				} catch (Exception e) {
					json.setMsg(e.getMessage());
					json.setSuccess(false);
				}
			}
		}
		super.writeJson(json);
	}


    public void takeCurAmtRc10167(){
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        //session失效
        if (userSession == null) {
            json.setSessionExpire(true);
        } else {
            if (Float.parseFloat(agentUnit.getCurAmt()) < 500.00) {
                json.setMsg("申请提现失败,金额小于500元");
                json.setSuccess(false);
            } else {
                try {
					// @author:xuegangliu-20190220 钱包提现
                    Object object = agentUnitService.takeCurAmtRc((UserBean)userSession,agentUnit.getCurAmt());
                    json.setObj(object);
                    json.setSuccess(true);
                    json.setMsg("申请提现成功");
                } catch (Exception e) {
                    json.setMsg(e.getMessage());
                    json.setSuccess(false);
                }
            }
        }
        super.writeJson(json);
    }
	/**
	 * 查询提现历史记录
	 */
	public void queryCash10160() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		// session失效
		if (userSession == null) {
			// json.setSessionExpire(true);
		} else {
			dataGridBean = agentUnitService.queryCash((UserBean) userSession, agentUnit);
		}
		super.writeJson(dataGridBean);
	}
	public void queryCashRc10167() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		// session失效
		if (userSession == null) {
			// json.setSessionExpire(true);
		} else {
			// @author:xuegangliu-20190220 查询代理商提现状态-提现记录
//			Integer cashStatus = getCashStatusByUnno((UserBean) userSession);
//			if(cashStatus==1) {
				dataGridBean = agentUnitService.queryCashRc((UserBean) userSession, agentUnit);
//			}
		}
		super.writeJson(dataGridBean);
	}
	public void queryAdjtxn10162() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		// session失效
		if (userSession == null) {
			// json.setSessionExpire(true);
		} else {
			dataGridBean = agentUnitService.queryAdjtxn((UserBean) userSession, agentUnit);
		}
		super.writeJson(dataGridBean);
	}
	public void queryAdjtxnRc10168() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		// session失效
		if (userSession == null) {
			// json.setSessionExpire(true);
		} else {
			// @author:xuegangliu-20190220 查询代理商提现状态-分润调整项
//			Integer cashStatus = getCashStatusByUnno((UserBean) userSession);
//			if(cashStatus==1) {
				dataGridBean = agentUnitService.queryAdjtxnRc((UserBean) userSession, agentUnit);
//			}
		}
		super.writeJson(dataGridBean);
	}


	/**
	 * 分润冻结记录表
	 */
	public void queryPusreFrozenRecord10163() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		// session失效
		if (userSession == null) {
			// json.setSessionExpire(true);
		} else {
			dataGridBean = agentUnitService.queryPusreFrozenRecord((UserBean) userSession, agentUnit);
		}
		super.writeJson(dataGridBean);
	}
	
	/**
	 * 三级分销代理审批，推送到综合
	 */
	public void rePublishToZH() {
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			boolean b = agentUnitService.updatePublishToZH(agentUnit,(UserBean)userSession);
			if (b==true) {
				jsonBean.setSuccess(true);
				jsonBean.setMsg("审核成功");
			}else {
				jsonBean.setSuccess(false);
				jsonBean.setMsg("审核失败");
			}
			
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("审核失败!");
			log.error("三级分销推送综合失败"+e);
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 查询机构成本信息 	运营中心 AND 一代
	 */
	public void queryHrtUnnoCostSingle(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			JSONObject json  = agentUnitService.queryHrtUnnoCost(agentUnit,(UserBean)userSession);
			jsonBean.setObj(json);
			jsonBean.setSuccess(true);
			jsonBean.setMsg("查询机构成本信息成功");
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("查询机构成本信息失败！");
			log.error("查询机构成本信息失败！:"+e);
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 查询机构活动成本信息 	运营中心 AND 一代
	 */
	public void queryHrtUnnoCostSingleRebate(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			jsonBean  = agentUnitService.queryHrtUnnoCostSingleRebate(agentUnit,(UserBean)userSession);
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("查询机构活动成本信息失败！");
			log.error("查询机构活动成本信息失败！:"+e);
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 运营中心成本变更列表查询
	 */
	public void queryBillBpFile9(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = agentUnitService.queryBillBpFile9(agentUnit,((UserBean)userSession));
		} catch (Exception e) {
			log.info("运营中心成本变更列表查询异常：" + e);
		}
		super.writeJson(dgb);
	}

	/**
	 * 下载更改处理变更单
	 */
	public void downloadImgFile() {
		try {
			String FID = super.getRequest().getParameter("FID");
			agentUnit.setFID(FID);
			Map map = agentUnitService.getFileImgPath(agentUnit);
				String filename = new String((map.get("imgUpload")+"").getBytes("gb2312"), "ISO-8859-1"); // 该行代码用来解决下载的文件名是乱码
				File file = new File(map.get("filePath")+"");
				byte a[] = new byte[1024 * 1024];
				FileInputStream fin = new FileInputStream(file);
				OutputStream outs = ServletActionContext.getResponse().getOutputStream();
				ServletActionContext.getResponse().setHeader("Content-Disposition","attachment; filename=" + filename);
				ServletActionContext.getResponse().setContentType("application/x-msdownload;charset=utf-8");
				ServletActionContext.getResponse().flushBuffer();
				int read = 0;
				while ((read = fin.read(a)) != -1) {
					outs.write(a, 0, read);
				}
				outs.close();
				fin.close();
		} catch (Exception e) {
			log.error("下载文件异常：" + e);
			try {
				ServletActionContext.getResponse().setContentType("text/html;charset=GB2312");
				ServletActionContext.getRequest().setCharacterEncoding("utf-8");
				ServletActionContext.getResponse().setCharacterEncoding("utf-8");
				ServletActionContext.getResponse().getWriter().println("<script>alert('该文件已不存在');history.go(-1);</script>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 更改处理文件表列表Grid信息查询
	 */
	public void queryBillBpFile3Grid(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null) {
				dgb = agentUnitService.queryBillBpFileGrid3(agentUnit, ((UserBean) userSession));
			}
		} catch (Exception e) {
			log.info("更改处理文件表列表Grid信息查询异常：" + e);
		}
		super.writeJson(dgb);
	}

	/**
	 * sn修改导入模板下载
	 */
	public void dowloadSnModifyTempXls(){
		try {
			ExcelServiceImpl.download(getResponse(),"sn_modify_tmp.xls","sn修改导入模板.xls");
		} catch (Exception e) {
			log.info("sn修改导入模板下载失败:"+e);
		}
	}

	/**
	 * 更改文件信息状态修改
	 */
	public void billBpFile3AduitStatus(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			UserBean userBean = (UserBean) userSession;
			boolean b = agentUnitService.updateBillBpFile3AduitStatus(agentUnit,userBean);
			if (b==true) {
				jsonBean.setSuccess(true);
				jsonBean.setMsg("处理成功");
			}else {
				jsonBean.setSuccess(false);
				jsonBean.setMsg("处理失败");
			}
		} catch (Exception e) {
			log.info("处理审核更改数据异常："+e.getMessage());
			jsonBean.setSuccess(false);
			jsonBean.setMsg("处理失败异常");
			e.printStackTrace();
		}
		super.writeJson(jsonBean);
	}

	/**
	 * 上传变更通知单
	 */
	public void saveOrUpdateSnMerImg(){
		UserBean userBean = (UserBean) super.getRequest().getSession().getAttribute("user");
		String uploadName=ServletActionContext.getRequest().getParameter("fileContact");
		agentUnit.setLegalAUpLoad(System.currentTimeMillis()+"-"+uploadName);
		JsonBean jsonBean = new JsonBean();
		if(userBean!=null){
			boolean flag = agentUnitService.saveOrUpdateImportSnOrMerImg(agentUnit);
			if(flag){
				jsonBean.setSuccess(true);
				jsonBean.setMsg("上传文件成功");
			}else{
				jsonBean.setSuccess(false);
				jsonBean.setMsg("上传文件失败");
			}
		}
		super.writeJson(jsonBean);
	}

	/**
	 * sn更改导入
	 */
    public void importSnModifyTempXls(){
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user=(UserBean)userSession;
        if(user == null){
            json.setSessionExpire(true);
        }
        String UUID = System.currentTimeMillis()+"";
		String uploadName=ServletActionContext.getRequest().getParameter("fileContact");
		String fileName = UUID+uploadName;
        String basePath = ServletActionContext.getRequest().getRealPath("/upload");
        File dir = new File(basePath);
        if(!dir.exists())
            dir.mkdir();
        String path = basePath +"/"+fileName;
        File destFile = new File(path);
        upload.renameTo(destFile);
        agentUnit.setLegalAUpLoad(fileName);
        if(upload1!=null) {
			agentUnit.setLegalAUpLoadFile(upload1);
		}
        String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
        String xlsfile = folderPath +"/"+fileName;
        log.info("sn修改导入Excel文件路径:"+xlsfile);

        try {
            if(xlsfile.length() > 0 && xlsfile != null){
                List<Map> listMap = agentUnitService.saveImportSnModifyTempXls(agentUnit,xlsfile,uploadName,user);
                if(listMap.size()==0){
                    json.setSuccess(true);
                    json.setMsg("sn修改导入文件成功!");
                }else{
                    json.setSuccess(false);
                    json.setMsg("sn修改导入文件失败");

					String excelName= "导入失败记录";
					String[] title = {"SN","错误通知"};
					List excellist = new ArrayList();
					excellist.add(title);
					for(int rowId = 0;rowId<listMap.size();rowId++){
						Map errInfo = listMap.get(rowId);
						String[] rowContents = {
								errInfo.get("sn")+"",
								errInfo.get("errMsg")+""
						};
						excellist.add(rowContents);
					}
					String[] cellFormatType = {"t","t"};
					UsePioOutExcel.writeExcel(excellist,title.length, super.getResponse(), excelName, excelName+".xls", cellFormatType);
                }
            }else{
                json.setSuccess(false);
                json.setMsg("sn修改导入文件异常!");
            }
        } catch (Exception e) {
            log.info("sn修改导入异常:"+e);
            json.setSuccess(false);
            json.setMsg("sn修改导入失败!");
        }
        ExcelServiceImpl.deleteUploadFile(folderPath,path);
        super.writeJson(json);
    }

	/**
	 * 更改文件数据导出
	 */
	public void exportChangeTermMerXls(){
		String fid = super.getRequest().getParameter("fid");
		String costType=super.getRequest().getParameter("costType");
		String fType=super.getRequest().getParameter("fType");
		Map<String,Object> map = new HashMap<String,Object>();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			String[] title=new String[]{};
			String excelName="";
			String[] cellFormatType = new String[]{};
			if("1".equals(costType)){
				title = new String[]{"sn", "原unno", "变更后unno","变更后返利类型","变更后刷卡费率（RATE）","变更后提现手续费（SECONDRATE）","变更后扫码费率（RATE）","变更后押金金额","处理状态"};
				cellFormatType = new String[]{"t","t","t","t","t","t","t","t","t"};
				excelName="sn修改";
			}else if("2".equals(costType)){
				title = new String[]{"一级机构号", "商户编号","贷记卡费率","秒到手续费","变更后一级机构号","处理状态"};
				cellFormatType = new String[]{"t","t","t","t","t","t"};
				excelName="商户费率修改";
			}
			List excellist = new ArrayList();
			excellist.add(title);
			List<JSONObject> list = agentUnitService.getFileRecordExcelInfo(Integer.parseInt(fid),Integer.parseInt(fType),Integer.parseInt(costType));
			for(int rowId = 0;rowId<list.size();rowId++){
				JSONObject jsonObject = list.get(rowId);
				if("1".equals(costType)){
					String[] rowContents = {
							jsonObject.getString("sn"),
							jsonObject.getString("unno"),
							jsonObject.getString("unno1"),
							jsonObject.getString("rebateType"),
							jsonObject.getString("rate"),
							jsonObject.getString("secondRate"),
							jsonObject.getString("scanRate"),
							jsonObject.getString("depositAmt"),
							1==jsonObject.getInteger("status")?"已处理":"未处理",
					};
					excellist.add(rowContents);
				}else if("2".equals(costType)){
					String[] rowContents = {
							jsonObject.getString("unno"),
							jsonObject.getString("unno1"),
							jsonObject.getString("rate"),
							jsonObject.getString("secondRate"),
							jsonObject.getString("changeUnno"),
							1==jsonObject.getInteger("status")?"已处理":"未处理",
					};
					excellist.add(rowContents);
				}
			}
			UsePioOutExcel.writeExcel(excellist,cellFormatType.length, super.getResponse(), excelName, excelName+".xls", cellFormatType);
		} catch (Exception e) {
			log.info("更改处理导出异常："+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 商户费率更改导入
	 */
	public void importMerRateModifyTempXls(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null){
			json.setSessionExpire(true);
		}
		String UUID = System.currentTimeMillis()+"";
		String uploadName=ServletActionContext.getRequest().getParameter("fileContact");
		String fileName = UUID+uploadName;
		String basePath = ServletActionContext.getRequest().getRealPath("/upload");
		File dir = new File(basePath);
		if(!dir.exists())
			dir.mkdir();
		String path = basePath +"/"+fileName;
		File destFile = new File(path);
		upload.renameTo(destFile);
		String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
		String xlsfile = folderPath +"/"+fileName;
		log.info("商户费率更改导入Excel文件路径:"+xlsfile);
		try {
			if(xlsfile.length() > 0 && xlsfile != null){
				List<Map> errList = agentUnitService.saveImportMerRateModifyTempXls(agentUnit,xlsfile,uploadName,user);
				if(errList.size()==0){
					json.setSuccess(true);
					json.setMsg("商户费率更改导入文件成功!");
				}else{
					json.setSuccess(false);
					json.setMsg("商户费率更改导入失败");
					String excelName= "导入失败记录";
					String[] title = {"商户编号/机构号","错误通知"};
					List excellist = new ArrayList();
					excellist.add(title);
					for(int rowId = 0;rowId<errList.size();rowId++){
						Map errInfo = errList.get(rowId);
						String[] rowContents = {
								errInfo.get("unno1")+"",
								errInfo.get("errMsg")+""
						};
						excellist.add(rowContents);
					}
					String[] cellFormatType = {"t","t"};
					UsePioOutExcel.writeExcel(excellist,title.length, super.getResponse(), excelName, excelName+".xls", cellFormatType);
				}
			}else{
				json.setSuccess(false);
				json.setMsg("商户费率更改导入文件异常!");
			}
		} catch (Exception e) {
			log.info("商户费率更改导入异常:"+e);
			json.setSuccess(false);
			json.setMsg("商户费率更改导入失败!");
		}
		ExcelServiceImpl.deleteUploadFile(folderPath,path);
		super.writeJson(json);
	}

	/**
	 * 商户费率更改模板下载
	 */
	public void dowloadMerRateModifyTempXls(){
		try {
			ExcelServiceImpl.download(getResponse(),"mer_rate_modify.xls","商户费率修改导入模板.xls");
		} catch (Exception e) {
			log.info("商户费率修改导入模板下载失败:"+e);
		}
	}

	/**
	 *  结算成本模板-下载(非活动)
	 */
	public void exportUnnoCost()  throws IOException{
		try {
			ExcelServiceImpl.download(getResponse(),"jscb.xls","分润结算成本模板(非活动).xls");
		} catch (Exception e) {
			log.info("结算成本模板(非活动)下载失败："+e);
			e.printStackTrace();
		}
	}
	/**
	 *  结算成本模板-下载(活动)
	 */
	public void exportUnnoCost1()  throws IOException{
		try {
			ExcelServiceImpl.download(getResponse(),"jscb1.xls","分润结算成本模板(活动).xls");
		} catch (Exception e) {
			log.info("结算成本模板(活动)下载失败："+e);
			e.printStackTrace();
		}
	}
	/**
	 * 代理成本上传-非活动
	 */
	public void addUnnoCost(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession!=null) {
			UserBean userBean = (UserBean) userSession;
			//查询机构归属级别
			if (userBean.getUnitLvl() > 1 ) {
				json.setSuccess(false);
				json.setMsg("您无权操作");
			}else{
				json = agentUnitService.checkXLSFile(agentUnit,userBean,upload1,"1");
				if (!json.isSuccess()) {
					super.writeJson(json);
					return ;
				}
				//把文件保存到服务器的目录下
				String cbUpload = ServletActionContext.getRequest().getParameter("cbUpLoad");	//获取文件名
				String zipUpLoad = ServletActionContext.getRequest().getParameter("zipUpLoad");	//获取文件名
				String dasePath = ServletActionContext.getServletContext().getRealPath("/upload");	
				File dir = new File(dasePath);
				if(!dir.exists()){
		            dir.mkdirs();
		        }
				// 使用UUID做为文件名，以解决重名的问题
				String path = dasePath + "/" + zipUpLoad;
				File destFile = new File(path);
				upload.renameTo(destFile);
				
				// 使用UUID做为文件名，以解决重名的问题
				String path1 = dasePath + "/" + cbUpload;
				File destFile1 = new File(path1);
				upload1.renameTo(destFile1);
				
				String folderPath = ServletActionContext.getServletContext().getRealPath("/upload");// 存放上传文件的路径
//				String jscbXls = folderPath + "/" + filename;
				//结算成本
				agentUnit.setCbUpLoad(cbUpload);
				agentUnit.setCbUpLoadFile(folderPath + "/" + cbUpload);
				//分润照片
				agentUnit.setZipUpLoad(zipUpLoad);
				agentUnit.setZipUpLoadFile(folderPath + "/" + zipUpLoad);
				try {
					Map<String,Object> map = agentUnitService.addUnnoCost(agentUnit,userBean);
					if(map.get("rtnCode").equals("00")){
						json.setSuccess(true);
						json.setMsg("成本变更成功");
					}else{
						json.setSuccess(false);
						json.setMsg(map.get("rtnMsg").toString());
					}
				} catch (Exception e) {
					log.error("成本变更失败：" + e);
					json.setSuccess(false);
					json.setMsg("变更失败，请稍后重试。");
				}finally{
					ExcelServiceImpl.deleteUploadFile(folderPath,path1);
				}
			}
		}else {
			json.setSessionExpire(true);
			json.setSuccess(false);
			json.setMsg("用户登录过期，请重新登录");
		}
		super.writeJson(json);
	}
	/**
	 * 代理成本上传-活动
	 */
	public void addUnnoCost1(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession!=null) {
			UserBean userBean = (UserBean) userSession;
			if (userBean.getUnitLvl() > 1 ) {
				json.setSuccess(false);
				json.setMsg("您无权操作");
			}else{
				json = agentUnitService.checkXLSFile(agentUnit,userBean,upload1,"2");
				if (!json.isSuccess()) {
					super.writeJson(json);
					return ;
				}
				//把文件保存到服务器的目录下
				String cbUpload = ServletActionContext.getRequest().getParameter("cbUpLoad");	//获取文件名
				String zipUpLoad = ServletActionContext.getRequest().getParameter("zipUpLoad");	//获取文件名
				String dasePath = ServletActionContext.getServletContext().getRealPath("/upload");	
				File dir = new File(dasePath);
				if(!dir.exists()){
		            dir.mkdirs();
		        }
				// 使用UUID做为文件名，以解决重名的问题
				String path = dasePath + "/" + zipUpLoad;
				File destFile = new File(path);
				upload.renameTo(destFile);
				
				// 使用UUID做为文件名，以解决重名的问题
				String path1 = dasePath + "/" + cbUpload;
				File destFile1 = new File(path1);
				upload1.renameTo(destFile1);
				
				String folderPath = ServletActionContext.getServletContext().getRealPath("/upload");// 存放上传文件的路径
//				String jscbXls = folderPath + "/" + filename;
				//结算成本
				agentUnit.setCbUpLoad(cbUpload);
				agentUnit.setCbUpLoadFile(folderPath + "/" + cbUpload);
				//分润照片
				agentUnit.setZipUpLoad(zipUpLoad);
				agentUnit.setZipUpLoadFile(folderPath + "/" + zipUpLoad);
				try {
					Map<String,Object> map = agentUnitService.addUnnoCost1(agentUnit,userBean);
					if(map.get("rtnCode").equals("00")){
						json.setSuccess(true);
						json.setMsg("成本变更成功");
					}else{
						json.setSuccess(false);
						json.setMsg(map.get("rtnMsg").toString());
					}
				} catch (Exception e) {
					log.error("成本变更失败：" +e.getMessage(),e);
					json.setSuccess(false);
					json.setMsg("变更失败，请稍后重试。");
				}finally{
					File file = new File(folderPath);
					String[] tempList = file.list();
					File temp = null;
					for (int i = 0; i < tempList.length; i++) {
						if (path1.endsWith(File.separator)) {
							temp = new File(folderPath + tempList[i]);
						} else {
							temp = new File(folderPath + File.separator + tempList[i]);
						}
						if (temp.exists() && temp.isFile()) {
							boolean f = temp.delete(); // 删除上传到服务器的文件
							int j = i + 1;
							log.error("成功删除文件:第" + j + "个文件删除是否成功？" + f);
						} else {
							log.error("文件不存在");
						}
					}
				}
			}
		}else {
			json.setSessionExpire(true);
			json.setSuccess(false);
			json.setMsg("用户登录过期，请重新登录");
		}
		super.writeJson(json);
	}
	
	/**
	 * 下载ZIP文件-分润照片
	 */
	public void zipFile(){
		JsonBean json = new JsonBean();
		try {
//			 String title="FRUpload";
			 String imgUpload = super.getRequest().getParameter("imgUrl");
			 FileZipUtil zipUint = new FileZipUtil();
			 String path=agentUnitService.querySysParam(TITLE);
			 String servletPath = path+File.separator+imgUpload.substring(0,8)+File.separator+imgUpload;
			 File file  = new File(servletPath);
			 if (file.exists()) {
				//导出zip压缩包
				 zipUint.downZipFile(servletPath,imgUpload);
			 }else{
				 log.info("文件不存在，下载路径："+servletPath);
				 json.setSuccess(false);
				 json.setMsg("文件不存在");
				 super.writeJson(json);
			 }
		} catch (Exception e) {
			log.info("下载ZIP文件异常："+e.getMessage());
			json.setSuccess(false);
			json.setMsg("下载文件异常");
			super.writeJson(json);
		}
	 }
	/**
	 * 勾选导出各机构结算成本
	 */
	public void reportUnnoCost(){
		String fid = super.getRequest().getParameter("fid");
		String costType=super.getRequest().getParameter("costType");
		Map<String,Object> map = new HashMap<String,Object>(); 
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			List<Map<String, String>> list = agentUnitService.listUnnoCost(costType,fid,((UserBean)userSession));
			map.put("UnnoCost", list);
			word w=new word();
			String path ="";
			String name ="";
			if("1".equals(costType)){
				name="(非活动)";
				path="jscbReport.htm";
			}else{
				name="(活动)";
				path="jscb1Report.htm";
			}
			w.createDoc(map, path, ((UserBean)userSession).getUnitName()+name+fid+".xls");
		} catch (Exception e) {
			log.info("机构结算成本导出异常："+e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * 代理成本退回
	 */
	public void TUnnoCost(){
		JsonBean jsonBean = new JsonBean();
		String fid = super.getRequest().getParameter("fid");
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			//校验权限是否允许
			UserBean userBean = (UserBean) userSession;
			if (userBean.getUnitLvl() > 0 ) {
				jsonBean.setSuccess(false);
				jsonBean.setMsg("您无权操作");
			}else{
				boolean b = agentUnitService.updateUnnoCostTH(fid,agentUnit,userBean);
				if (b==true) {
					jsonBean.setSuccess(true);
					jsonBean.setMsg("退回成功");
				}else {
					jsonBean.setSuccess(false);
					jsonBean.setMsg("退回失败");
				}
			}
		} catch (Exception e) {
			log.info("代理结算成本退回异常："+e.getMessage());
			jsonBean.setSuccess(false);
			jsonBean.setMsg("退回失败");
			e.printStackTrace();
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 成本通过
	 */
	public void editPassUnits(){
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession != null) {
			try {
				//校验权限是否允许
				UserBean userBean = (UserBean) userSession;
				if (userBean.getUnitLvl() > 0 ) {
					jsonBean.setSuccess(false);
					jsonBean.setMsg("您无权操作");
				}else{
					jsonBean = agentUnitService.updateUnnoCostTY(agentUnit,userBean);
				}
			} catch (Exception e) {
				log.info("代理结算成本通过异常："+e);
				jsonBean.setSuccess(false);
				jsonBean.setMsg("代理结算成本通过失败");
			}
		}else{
			jsonBean.setSessionExpire(true);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("用户过期，重新登录");
		}
		
		super.writeJson(jsonBean);
	}
	
	/**
	 * 成本查询
	 */
	public void queryUnnoCostMultiply(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession != null) {
			try {
				dgb = agentUnitService.queryUnnoCostMult(agentUnit,(UserBean)userSession);
			} catch (Exception e) {
				log.info("结算成本查询异常："+e);
			}
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询活动类型配置成本
	 */
	public void listRebateRate(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				dgb = agentUnitService.listRebateRate(agentUnit,(UserBean)userSession);
			}
		} catch (Exception e) {
			log.error("查询活动类型配置成本异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询当前机构下可配置活动类型的机构
	 */
	public void listRebateRateForUnno(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				dgb = agentUnitService.listRebateRateForUnno(agentUnit,(UserBean)userSession);
			}
		} catch (Exception e) {
			log.error("查询活动类型配置成本异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询活动类型成本
	 */
	public void queryRebateRateList(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				dgb = agentUnitService.queryRebateRateList(agentUnit,(UserBean)userSession);
			}
		} catch (Exception e) {
			log.error("查询活动类型成本异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询活动类型待审核成本
	 */
	public void listRebateRateForW(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				dgb = agentUnitService.listRebateRateForW(agentUnit,(UserBean)userSession);
			}
		} catch (Exception e) {
			log.error("查询活动类型配置成本异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 新增活动成本
	 */
	public void addUnnoRebateRate() {
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			jsonBean = agentUnitService.addUnnoRebateRate(agentUnit,(UserBean)userSession);
		} catch (Exception e) {
			log.info("新增活动成本异常："+e.getMessage());
			jsonBean.setSuccess(false);
			jsonBean.setMsg("新增活动成本失败");
			e.printStackTrace();
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 修改退回的活动成本
	 */
	public void updateUnnoRebateRate() {
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			jsonBean = agentUnitService.updateUnnoRebateRate(agentUnit,(UserBean)userSession);
		} catch (Exception e) {
			log.info("修改成本失败异常："+e.getMessage());
			jsonBean.setSuccess(false);
			jsonBean.setMsg("修改成本失败");
			e.printStackTrace();
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 新增活动成本审核通过
	 */
	public void updateUnnoRebateRateGo() {
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			jsonBean = agentUnitService.updateUnnoRebateRateGo(agentUnit,(UserBean)userSession);
		} catch (Exception e) {
			log.info("通过活动成本异常："+e.getMessage());
			jsonBean.setSuccess(false);
			jsonBean.setMsg("通过活动成本失败");
			e.printStackTrace();
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 新增活动成本审核退回
	 */
	public void updateUnnoRebateRateBack() {
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			jsonBean = agentUnitService.updateUnnoRebateRateBack(agentUnit,(UserBean)userSession);
		} catch (Exception e) {
			log.info("退回活动成本异常："+e.getMessage());
			jsonBean.setSuccess(false);
			jsonBean.setMsg("退回活动成本失败");
			e.printStackTrace();
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 删除活动类型成本
	 */
	public void deleteRebateCost() {
		JsonBean jsonBean = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			jsonBean = agentUnitService.deleteRebateCost(agentUnit,(UserBean)userSession);
		} catch (Exception e) {
			log.info("退回活动成本异常："+e.getMessage());
			jsonBean.setSuccess(false);
			jsonBean.setMsg("退回活动成本失败");
			e.printStackTrace();
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 结算成本查询-非活动
	 */
	public void query00319q(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = agentUnitService.query00319q(agentUnit,((UserBean)userSession));
		} catch (Exception e) {
			log.info("运营中心结算成本(非活动)查询异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 结算成本查询-活动
	 */
	public void query00319w(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = agentUnitService.query00319w(agentUnit,((UserBean)userSession));
		} catch (Exception e) {
			log.info("运营中心结算成本(活动)查询异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 结算成本导出-非活动
	 */
	public void export00319q(){
		Map<String,Object> map = new HashMap<String,Object>(); 
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			List<Map<String, String>> list = agentUnitService.export00319q(agentUnit,((UserBean)userSession));
			map.put("UnnoCost", list);
			word w=new word();
			String path="jscbQuery.htm";
			w.createDoc(map, path, ((UserBean)userSession).getUnitName()+".xls");
		} catch (Exception e) {
			log.info("运营中心结算成本(非活动)导出异常："+e.getMessage());
			e.printStackTrace();
		}
	}


	/**
	 * 查询中心成本人工设置 (活动与非活动 hrt_unno_cost unno=RGSZ+unno)
	 */
	public void query00319h(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = agentUnitService.query00319h(agentUnit,((UserBean)userSession));
		} catch (Exception e) {
			log.info("中心成本人工设置查询异常：" + e);
		}
		super.writeJson(dgb);
	}

	/**
	 * 特殊活动设备数量列表查询
	 */
	public void queryTotalTerminalGrid(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = agentUnitService.queryTotalTerminalGrid(agentUnit,((UserBean)userSession));
		} catch (Exception e) {
			log.info("特殊活动设备数量列表查询异常：" + e);
		}
		super.writeJson(dgb);
	}

	/**
	 * 成本人工设置导入
	 */
	public void import00319hHrtCost(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null){
			json.setSessionExpire(true);
		}
		String UUID = System.currentTimeMillis()+"";
		//把文件保存到服务器目录下
		String fileName = UUID+ServletActionContext.getRequest().getParameter("fileContact");
		String basePath = ServletActionContext.getRequest().getRealPath("/upload");
		File dir = new File(basePath);
		if(!dir.exists())
			dir.mkdir();
		String path = basePath +"/"+fileName;
		File destFile = new File(path);
		upload.renameTo(destFile);
		String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
		String xlsfile = folderPath +"/"+fileName;
		log.info("导入人工成本设置Excel文件路径:"+xlsfile);
		try {
			if(xlsfile.length() > 0 && xlsfile != null){
				Map resultMap = agentUnitService.saveImportRGSZHrtCost(xlsfile,fileName,user);
				int sumCount = Integer.parseInt(resultMap.get("sumCount").toString());
				int errCount = Integer.parseInt(resultMap.get("errCount").toString());
				if(sumCount!=errCount){
					json.setSuccess(true);
					json.setMsg("导入人工成本设置成功!-总记录数:"+resultMap.get("sumCount")+",失败数:"+resultMap.get("errCount"));
				}else{
					json.setSuccess(false);
					json.setMsg("导入人工成本设置导入数据异常!");
				}
			}else{
				json.setSuccess(false);
				json.setMsg("导入文件异常!");
			}
		} catch (Exception e) {
			log.info("导入人工成本设置异常:",e);
			json.setSuccess(false);
			json.setMsg("人工成本设置导入失败!");
		}
		ExcelServiceImpl.deleteUploadFile(folderPath,path);
		super.writeJson(json);
	}

	/**
	 * 成本人工设置导出
	 */
	public void export00319h(){
		JsonBean json = new JsonBean();
		List<String[]>cotents = new ArrayList<String[]>();
		// @author:lxg-20190911 扫码费率拆分
		String titles[] = {"运营中心名称","运营中心机构号","机器类型","交易类型","产品细分","借记卡成本（%）(刷)","借记卡封项手续费(刷)","贷记卡成本（%）（扫码1000以下（终端0.38）费率(%)）","提现成本（扫码1000以下（终端0.38）转账费）","提现手续费成本（%）","扫码1000以上（终端0.38）费率(%)","扫码1000以上（终端0.38）转账费","扫码1000以上（终端0.45）费率(%)","扫码1000以上（终端0.45）转账费",
				"扫码1000以下（终端0.45）费率(%)","扫码1000以下（终端0.45）转账费","银联二维码费率(%)","银联二维码转账费","云闪付费率(%)","花呗费率(%)","花呗转账费"};

//		String key[] = {"UN_NAME","UNNO","MACHINE_TYPE","TXN_TYPE","TXN_DETAIL","DEBIT_RATE","DEBIT_FEEAMT","CREDIT_RATE","CASH_COST","CASH_RATE"};
		cotents.add(titles);
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				List<Map<String, Object>> list = agentUnitService.export00319h(agentUnit,((UserBean)userSession));
				if (list!=null&&list.size()>0) {
					for (Map<String, Object>map  : list) {
						String rowCoents[] = {
								null==map.get("UN_NAME")?"":map.get("UN_NAME")+"",
								null==map.get("UNNO")?"":map.get("UNNO")+"",
								null==map.get("MACHINE_TYPE")?"": HrtCostConstant.getValueByTypeAndKey("MACHINE_TYPE", map.get("MACHINE_TYPE") + ""),
								null==map.get("TXN_TYPE")?"":HrtCostConstant.getValueByTypeAndKey("TXN_TYPE", map.get("TXN_TYPE")+""),
								null==map.get("TXN_DETAIL")?"":HrtCostConstant.getValueByTypeAndKey("TXN_DETAIL", map.get("TXN_DETAIL")+""),
								null==map.get("DEBIT_RATE")?"":map.get("DEBIT_RATE")+"",
								null==map.get("DEBIT_FEEAMT")?"":map.get("DEBIT_FEEAMT")+"",
								null==map.get("CREDIT_RATE")?"":map.get("CREDIT_RATE")+"",
								null==map.get("CASH_COST")?"":map.get("CASH_COST")+"",
								null==map.get("CASH_RATE")?"":map.get("CASH_RATE")+"",
								null==map.get("WX_UPRATE")?"":map.get("WX_UPRATE")+"",
								null==map.get("WX_UPCASH")?"":map.get("WX_UPCASH")+"",
								null==map.get("WX_UPRATE1")?"":map.get("WX_UPRATE1")+"",
								null==map.get("WX_UPCASH1")?"":map.get("WX_UPCASH1")+"",
								null==map.get("ZFB_RATE")?"":map.get("ZFB_RATE")+"",
								null==map.get("ZFB_CASH")?"":map.get("ZFB_CASH")+"",
								null==map.get("EWM_RATE")?"":map.get("EWM_RATE")+"",
								null==map.get("EWM_CASH")?"":map.get("EWM_CASH")+"",
								null==map.get("YSF_RATE")?"":map.get("YSF_RATE")+"",
                                null==map.get("HB_RATE")?"":map.get("HB_RATE")+"",
                                null==map.get("HB_CASH")?"":map.get("HB_CASH")+"",
						};
						cotents.add(rowCoents);
					}
				}
				String excelName = "人工运营中心成本设置";
				String sheetName = "人工运营中心成本设置";
				String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
				JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("人工运营中心成本设置导出成功");
			}
		} catch (Exception e) {
			log.error("人工运营中心成本设置导出异常：" + e);
			json.setSuccess(false);
			json.setMsg("人工运营中心成本设置导出失败");
			super.writeJson(json);
		}
	}

	/**
	 * 删除成本人工设置
	 */
	public void deleteRGSZHrtCost(){
		JsonBean jsonBean = new JsonBean();
		try {
			String hucid = getRequest().getParameter("hucid");
			Object userSession = super.getRequest().getSession().getAttribute("user");
			Integer result = agentUnitService.deleteRGSZHrtCost(Integer.parseInt(hucid),(UserBean)userSession);
			if(result>0) {
				jsonBean.setSuccess(true);
				jsonBean.setMsg("删除成功");
			}else{
				jsonBean.setSuccess(false);
				jsonBean.setMsg("删除失败");
			}
		} catch (Exception e) {
			log.error("删除异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("删除失败");
		}
		super.writeJson(jsonBean);
	}

	/**
	 * 人工成本设置模板下载
	 */
	public void dowloadRGSZHrtCostXls(){
		try {
			ExcelServiceImpl.download(getResponse(),"hrtcost_rgsz.xls","人工成本设置模板.xls");
		} catch (Exception e) {
			log.info("人工成本设置模板模板下载失败:"+e);
		}
	}

	/**
	 * 新增活动类型成本批量新增模板下载
	 */
	public void dowloadBatchRebateRateXls(){
		try {
			ExcelServiceImpl.download(getResponse(),"batchImport.xls","批量新增成本模板.xls");
		} catch (Exception e) {
			log.info("批量新增成本导入模板下载失败:"+e);
		}
	}

	/**
	 * 新增活动类型成本批量导入
	 */
	public void importBatchRebateRate(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null){
			json.setSessionExpire(true);
		}
		String UUID = System.currentTimeMillis()+"";
		//把文件保存到服务器目录下
		String fileName = UUID+ServletActionContext.getRequest().getParameter("fileContact");
		String basePath = ServletActionContext.getRequest().getRealPath("/upload");
		File dir = new File(basePath);
		if(!dir.exists())
			dir.mkdir();
		String path = basePath +"/"+fileName;
		File destFile = new File(path);
		upload.renameTo(destFile);
		String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
		String xlsfile = folderPath +"/"+fileName;
		log.info("新增活动类型成本批量新增导入Excel文件路径:"+xlsfile);

		try {
			if(xlsfile.length() > 0 && xlsfile != null){
				String message = agentUnitService.saveImportBatchRebateRate(xlsfile,fileName,user);
				if(StringUtils.isEmpty(message)){
					json.setSuccess(true);
					json.setMsg("新增活动类型成本批量新增导入成功!");
				}else{
					json.setSuccess(false);
					json.setMsg(message);
				}
			}else{
				json.setSuccess(false);
				json.setMsg("导入文件异常!");
			}
		} catch (Exception e) {
			log.info("新增活动类型成本批量新增导入异常:"+e);
			json.setSuccess(false);
			json.setMsg("新增活动类型成本批量新增导入失败!");
		}
		ExcelServiceImpl.deleteUploadFile(folderPath,path);
		super.writeJson(json);
	}

	/**
	 * 结算成本导出-活动
	 */
	public void export00319w(){
		JsonBean json = new JsonBean();
		List<Map<String, Object>>list = null;
		List<String[]>cotents = new ArrayList<String[]>();
		// @author:lxg-20190911 扫码费率拆分
		String titles[] = {"运营中心名称","运营中心机构号","一级代理商/运营中心简称","一级代理/运营中心机构号","是否已生效",
				"活动类型","刷卡成本(%)","刷卡提现转账手续费","扫码1000以下（终端0.38）费率(%)","扫码1000以下（终端0.38）转账费",
				"扫码1000以上（终端0.38）费率(%)","扫码1000以上（终端0.38）转账费","扫码1000以上（终端0.45）费率(%)","扫码1000以上（终端0.45）转账费",
				"扫码1000以下（终端0.45）费率(%)","扫码1000以下（终端0.45）转账费","银联二维码费率(%)","银联二维码转账费","云闪付费率(%)","花呗费率(%)","花呗转账费"};
		cotents.add(titles);
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				list = agentUnitService.export00319w(agentUnit,(UserBean)userSession);
				if (list!=null&&list.size()>0) {
					for (Map<String, Object>map  : list) {
						String rowCoents[] = {
								map.get("T1")==null?"":map.get("T1").toString(),
								map.get("T2")==null?"":map.get("T2").toString(),
								map.get("T3")==null?"":map.get("T3").toString(),		
								map.get("T4")==null?"":map.get("T4").toString(),
								map.get("T10")==null?"":map.get("T10").toString(),
								map.get("T5")==null?"":map.get("T5").toString(),
								map.get("T6")==null?"":map.get("T6").toString(),
								map.get("T7")==null?"":map.get("T7").toString(),
								map.get("T8")==null?"":map.get("T8").toString(),
								map.get("T9")==null?"":map.get("T9").toString(),
								map.get("WXR1")==null?"":map.get("WXR1").toString(),
								map.get("WXC1")==null?"":map.get("WXC1").toString(),
								map.get("WXR2")==null?"":map.get("WXR2").toString(),
								map.get("WXC2")==null?"":map.get("WXC2").toString(),
								map.get("ZFBR1")==null?"":map.get("ZFBR1").toString(),
								map.get("ZFBC1")==null?"":map.get("ZFBC1").toString(),
								map.get("EWMR1")==null?"":map.get("EWMR1").toString(),
								map.get("EWMC1")==null?"":map.get("EWMC1").toString(),
								map.get("YSFR1")==null?"":map.get("YSFR1").toString(),
								map.get("HBR1")==null?"":map.get("HBR1").toString(),
								map.get("HBC1")==null?"":map.get("HBC1").toString()
						};
						cotents.add(rowCoents);
					}
				}
				String excelName = "结算成本(活动)表";
				String sheetName = "结算成本(活动)表";
				String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
				JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("结算成本(活动)导出成功");
			}
		} catch (Exception e) {
			log.error("结算成本(活动)导出异常：" + e);
			json.setSuccess(false);
			json.setMsg("结算成本(活动)导出");
			super.writeJson(json);
		}
	}
	/**
	 * 结算成本(活动)查询-活动条件下拉
	 */
	public void listRebateType(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
			} else {
				dgb = agentUnitService.listRebateType();
			}
		} catch (Exception e) {
			log.error("结算成本(活动)活动条件下拉查询异常：" + e);
		}
		super.writeJson(dgb);
	}

	/**
	 * 活动设备激活明细月报表
	 */
	public void salesActTermDetailGrid(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
			} else {
				dgb = agentUnitService.listSalesActTermDetailGrid(agentUnit,(UserBean)userSession);
			}
		} catch (Exception e) {
			log.error("活动设备激活明细月报表查询异常：" + e);
		}
		super.writeJson(dgb);
	}

	/**
	 * 活动设备激活明细月报表导出
	 */
	public void exportSalesActTermDetail(){
		JsonBean json = new JsonBean();
		List<Map> list = null;
		List<String[]>cotents = new ArrayList<String[]>();
		String titles[] = {"中心机构号","中心机构名称","一代机构号","一代机构名称"
				,"SN","销售日期","出库日期","绑定日期","激活日期","活动类型","归属销售","代理类别"};
		cotents.add(titles);
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				list = agentUnitService.exportSalesActTermDetail(agentUnit,(UserBean)userSession);
				if (list!=null&&list.size()>0) {
					for (Map<String, Object>map  : list) {
						String rowCoents[] = {
								map.get("YUNYING")==null?"":map.get("YUNYING").toString(),
								map.get("YUNYINGN")==null?"":map.get("YUNYINGN").toString(),
								map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
								map.get("YIDAIN")==null?"":map.get("YIDAIN").toString(),
								map.get("SN")==null?"":map.get("SN").toString(),
								map.get("KEYCONFIRMDATE")==null?"":map.get("KEYCONFIRMDATE").toString(),
								map.get("OUTDATE")==null?"":map.get("OUTDATE").toString(),
								map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
								map.get("ACTIVADAY")==null?"":map.get("ACTIVADAY").toString(),
								map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
								map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
								map.get("YUNYING")==null?"":"991000,j91000".contains(map.get("YUNYING").toString())?"直营":"非直营",
						};
						cotents.add(rowCoents);
					}
				}
				String excelName = "活动设备激活明细月报表";
				String sheetName = "活动设备激活明细月报表";
				String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t"};
				JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("活动设备激活明细月报表导出成功");
			}
		} catch (Exception e) {
			log.error("活动设备激活明细月报表导出异常：" + e);
			json.setSuccess(false);
			json.setMsg("活动设备激活明细月报表导出失败");
			super.writeJson(json);
		}
	}

	/**
	 * 活动设备激活汇总月报表Grid
	 */
	public void salesActTermSummaryGrid(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
			} else {
				dgb = agentUnitService.listSalesActTermSummaryGrid(agentUnit,(UserBean)userSession);
			}
		} catch (Exception e) {
			log.error("活动设备激活汇总月报表查询异常：" + e);
		}
		super.writeJson(dgb);
	}

	/**
	 * 活动设备激活汇总月报表Grid导出
	 */
	public void exportSalesActTermSummary(){
		JsonBean json = new JsonBean();
		List<Map> list = null;
		List<String[]>cotents = new ArrayList<String[]>();
		String titles[] = {"中心机构号/直营一代机构号","中心机构名称/直营一代机构号","设备总量","未绑定设备量"
				,"已绑定设备量","激活设备量","活动类型","归属销售"};
		cotents.add(titles);
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				list = agentUnitService.exportSalesActTermSummary(agentUnit,(UserBean)userSession);
				if (list!=null&&list.size()>0) {
					for (Map<String, Object>map  : list) {
						String rowCoents[] = {
								map.get("PP")==null?"":map.get("PP").toString(),
								map.get("UN_NAME")==null?"":map.get("UN_NAME").toString(),
								map.get("ALLCOUNT")==null?"":map.get("ALLCOUNT").toString(),
								map.get("NOCOUNT")==null?"":map.get("NOCOUNT").toString(),
								map.get("USECOUNT")==null?"":map.get("USECOUNT").toString(),
								map.get("ACTCOUNT")==null?"":map.get("ACTCOUNT").toString(),
								map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
								map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
						};
						cotents.add(rowCoents);
					}
				}
				String excelName = "活动设备激活汇总月报表";
				String sheetName = "活动设备激活汇总月报表";
				String [] cellFormatType = {"t","t","t","t","t","t","t","t"};
				JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("活动设备激活汇总月报表导出成功");
			}
		} catch (Exception e) {
			log.error("活动设备激活汇总月报表导出异常：" + e);
			json.setSuccess(false);
			json.setMsg("活动设备激活汇总月报表导出失败");
			super.writeJson(json);
		}
	}

	/**
	 * 销售维护Grid列表
	 */
	public void salesUnnoListGrid(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
			} else {
				dgb = agentUnitService.listSalesUnnoListGrid(agentUnit,(UserBean)userSession);
			}
		} catch (Exception e) {
			log.error("销售维护报表查询异常：" + e);
		}
		super.writeJson(dgb);
	}

	/**
	 * 销售维护Grid列表导出
	 */
    public void exportSalesUnnoList(){
        JsonBean json = new JsonBean();
        List<Map> list = null;
        List<String[]>cotents = new ArrayList<String[]>();
        String titles[] = {"代理类别","机构号","归属销售"};
        cotents.add(titles);
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if(userSession!=null){
                list = agentUnitService.exportSalesUnnoList(agentUnit,(UserBean)userSession);
                if (list!=null&&list.size()>0) {
                    for (Map<String, Object>map  : list) {
                        String rowCoents[] = {
                                map.get("TYPE")==null?"":("1".equals(map.get("TYPE").toString())?"自营":"非自营"),
                                map.get("UNNO")==null?"":map.get("UNNO").toString(),
                                map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
                        };
                        cotents.add(rowCoents);
                    }
                }
                String excelName = "销售维护资料";
                String sheetName = "销售维护资料";
                String [] cellFormatType = {"t","t","t"};
                JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
                json.setSuccess(true);
                json.setMsg("销售维护资料导出成功");
            }
        } catch (Exception e) {
            log.error("销售维护资料导出异常：" + e);
            json.setSuccess(false);
            json.setMsg("销售维护资料导出失败");
            super.writeJson(json);
        }
    }

	/**
	 * 销售维护导入模板下载
	 */
	public void dowloadSaleUnnoTempXls(){
		try {
			ExcelServiceImpl.download(getResponse(),"saleUnnoTemp.xls","销售维护导入模板.xls");
		} catch (Exception e) {
			log.info("销售维护模板下载失败:"+e);
		}
	}

	/**
	 * 销售维护模板导入
	 */
	public void importSaleUnnoTempXls(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null){
			json.setSessionExpire(true);
		}
		String UUID = System.currentTimeMillis()+"";
		String uploadName=ServletActionContext.getRequest().getParameter("fileContact");
		String fileName = UUID+uploadName;
		String basePath = ServletActionContext.getRequest().getRealPath("/upload");
		File dir = new File(basePath);
		if(!dir.exists())
			dir.mkdir();
		String path = basePath +"/"+fileName;
		File destFile = new File(path);
		upload.renameTo(destFile);
		String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
		String xlsfile = folderPath +"/"+fileName;
		log.info("销售维护导入Excel文件路径:"+xlsfile);
		try {
			if(xlsfile.length() > 0 && xlsfile != null){
				String errMsg = agentUnitService.saveImportSaleUnnoTempXls(agentUnit,xlsfile,uploadName,user);
				if(StringUtils.isEmpty(errMsg)){
					json.setSuccess(true);
					json.setMsg("销售维护导入文件成功!");
				}else {
					json.setSuccess(false);
					json.setMsg(errMsg);
				}
			}else{
				json.setSuccess(false);
				json.setMsg("销售维护导入文件异常!");
			}
		} catch (Exception e) {
			log.info("销售维护导入异常:"+e);
			json.setSuccess(false);
			json.setMsg("销售维护导入失败!");
		}
		ExcelServiceImpl.deleteUploadFile(folderPath,path);
		super.writeJson(json);
	}
	
	/**
	 * 活动设备活跃明细月报表
	 */
	public void salesActLiveTermDetailGrid(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
			} else {
				dgb = agentUnitService.listSalesActLiveTermDetailGrid(agentUnit,(UserBean)userSession);
			}
		} catch (Exception e) {
			log.error("活动设备活跃明细月报表查询异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 活动设备活跃明细月报表导出
	 */
	public void exportSalesActLiveTermDetail(){
		JsonBean json = new JsonBean();
		List<Map<String, Object>> list = null;
		List<String[]>cotents = new ArrayList<String[]>();
		String titles[] = {"中心机构号","中心机构名称","一代机构号","一代机构名称","商户号"
				,"SN","激活日期","活跃日期","活动类型"};
		cotents.add(titles);
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				list = agentUnitService.exportSalesActLiveTermDetail(agentUnit,(UserBean)userSession);
				if (list!=null&&list.size()>0) {
					for (Map<String, Object>map  : list) {
						String rowCoents[] = {
								map.get("YUNYING")==null?"":map.get("YUNYING").toString(),
								map.get("YUNYINGN")==null?"":map.get("YUNYINGN").toString(),
								map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
								map.get("YIDAIN")==null?"":map.get("YIDAIN").toString(),
							    map.get("MID")==null?"":map.get("MID").toString(),
								map.get("SN")==null?"":map.get("SN").toString(),
								map.get("ACTIVADAY")==null?"":map.get("ACTIVADAY").toString(),
								map.get("TXNDAY")==null?"":map.get("TXNDAY").toString(),
								map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
						};
						cotents.add(rowCoents);
					}
				}
				String excelName = "活动设备活跃明细月报表";
				String sheetName = "活动设备活跃明细月报表";
				String [] cellFormatType = {"t","t","t","t","t","t","t","t","t"};
				JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("活动设备活跃明细月报表导出成功");
			}
		} catch (Exception e) {
			log.error("活动设备活跃明细月报表导出异常：" + e);
			json.setSuccess(false);
			json.setMsg("活动设备活跃明细月报表导出失败");
			super.writeJson(json);
		}
	}
	
	/**
	 * 活动设备活跃汇总月报表
	 */
	public void salesActLiveTermSumGrid(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
			} else {
				dgb = agentUnitService.listSalesActLiveTermSumGrid(agentUnit,(UserBean)userSession);
			}
		} catch (Exception e) {
			log.error("活动设备活跃汇总月报表查询异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 活动设备活跃汇总月报表导出
	 */
	public void exportSalesActLiveTermSum(){
		JsonBean json = new JsonBean();
		List<Map<String, Object>> list = null;
		List<String[]>cotents = new ArrayList<String[]>();
		String titles[] = {"中心机构号","中心机构名称","一代机构号","一代机构名称"
				,"激活月","活跃月","活跃设备量","活动类型"};
		cotents.add(titles);
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				list = agentUnitService.exportSalesActLiveTermSum(agentUnit,(UserBean)userSession);
				if (list!=null&&list.size()>0) {
					for (Map<String, Object>map  : list) {
						String rowCoents[] = {
								map.get("YUNYING")==null?"":map.get("YUNYING").toString(),
								map.get("YUNYINGN")==null?"":map.get("YUNYINGN").toString(),
								map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
								map.get("YIDAIN")==null?"":map.get("YIDAIN").toString(),
								map.get("ACTIVADAY")==null?"":map.get("ACTIVADAY").toString(),
							    map.get("TXNDAY")==null?"":map.get("TXNDAY").toString(),
								map.get("LIVECOUNT")==null?"":map.get("LIVECOUNT").toString(),
								map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
						};
						cotents.add(rowCoents);
					}
				}
				String excelName = "活动设备活跃汇总月报表";
				String sheetName = "活动设备活跃汇总月报表";
				String [] cellFormatType = {"t","t","t","t","t","t","t","t"};
				JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("活动设备活跃汇总月报表导出成功");
			}
		} catch (Exception e) {
			log.error("活动设备活跃汇总月报表导出异常：" + e);
			json.setSuccess(false);
			json.setMsg("活动设备活跃汇总月报表导出失败");
			super.writeJson(json);
		}
	}
	
	/**
	 * 查询代理商钱包开关状态（一代及一代以下）
	 */
	public void rebatetypeWalletCashSwitch(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = agentUnitService.rebatetypeWalletCashSwitch(agentUnit,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询代理商信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	
	/**
	 * 变更活动钱包状态
	 */
    public void updateRebatetypeWalletCashSwitch(){
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        try {
        	String string = agentUnitService.updateRebatetypeWalletCashSwitch(agentUnit,((UserBean)userSession));
        	json.setMsg(string);
        } catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("活动钱包变更失败");
			super.writeJson(json);
		}
        json.setSuccess(true);
		super.writeJson(json);
    }
    
    /**
	 * 批量变更模板下载
	 */
	public void downloadRebatetypeWalletUpdateXls(){
		try {
			ExcelServiceImpl.download(getResponse(),"RebatetypeWalletUpdateXls.xls","批量变更模板.xls");
		} catch (Exception e) {
			log.info("批量变更模板下载失败:"+e);
		}
	}
	
	/**
     * 批量开通或者变更模板导入
     */
    public void importrebatetypeWalletCashSwitchUpdate(){
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
                String message = agentUnitService.importrebatetypeWalletCashSwitchUpdate(xlsfile,fileName,user);
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
	 *钱包开关功能--导出
	 */
	public void reportRebatetypeWalletCashSwitch() {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			String excelName;
			excelName = "活动钱包开关.csv";
			List<Map<String, Object>> list = agentUnitService.reportRebatetypeWalletCashSwitch(agentUnit,((UserBean)userSession));
			
			String[] titles = { "代理机构号","归属上级机构号","归属一代机构号","归属中心机构号","钱包提现状态"};
			List excelList = new ArrayList();
			excelList.add(titles);
			try {
				for (int rowId = 0; rowId < list.size(); rowId++) {
					Map order = list.get(rowId);
					String[] rowContents = { 
							String.valueOf(order.get("UNNO")==null?"":order.get("UNNO")),
							String.valueOf(order.get("BNO")==null?"":order.get("BNO")),
							String.valueOf(order.get("RNO")==null?"":order.get("RNO")),
							String.valueOf(order.get("REGISTRYNO")==null?"":order.get("REGISTRYNO")),
							String.valueOf(order.get("ISCASHSWITCH")==null?"":order.get("ISCASHSWITCH"))
					};
					excelList.add(rowContents);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			list=null;
			String[] cellFormatType = { "t","t","t","t","t"};
			JxlOutExcelUtil.writeCSVFile(excelList, titles.length, getResponse(), excelName);
			//UsePioOutExcel.writeExcel(excelList, 5, super.getResponse(), excelName,excelName+".xls", cellFormatType); // 调用导出方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 代理商户激活量查询
	 */
	public void queryAgentActiveSum(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		if(agentUnit.getCashDay()!=null && !"".equals(agentUnit.getCashDay()) && 
				agentUnit.getCashDay1()!=null && !"".equals(agentUnit.getCashDay1())){
			try {
				dgb = agentUnitService.queryAgentActiveSum(agentUnit,(UserBean)userSession);
			} catch (Exception e) {
				log.error("查询代理商户激活量异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 导出_代理商户激活量
	 */
	public void exportQueryAgentActiveSum() throws IOException, RowsExceededException, WriteException {
		if(agentUnit.getCashDay()==null || "".equals(agentUnit.getCashDay()) || 
				agentUnit.getCashDay1()==null || "".equals(agentUnit.getCashDay1())){
			return;
		}
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, Object>> list = agentUnitService.exportQueryAgentActiveSum(agentUnit,(UserBean)userSession);
		String titles[] = {"机构号","机构名名称","产品类型","注册商户数","注册终端数","激活数"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
					map.get("AGENTNAME")==null?"":map.get("AGENTNAME").toString(),
					map.get("PRODUCTTYPE")==null?"":map.get("PRODUCTTYPE").toString(),
					map.get("FLAG1")==null?"":map.get("FLAG1").toString(),
					map.get("FLAG2")==null?"":map.get("FLAG2").toString(),
					map.get("FLAG3")==null?"":map.get("FLAG3").toString()
					
			};
			excelList.add(rowContents);
		}
		String excelName = "代理户激活量查询统计.csv";
		JxlOutExcelUtil.writeCSVFile(excelList, titles.length, response, excelName);
	}
	
	/**
	 * 代理活跃及登录APP监测报表明细_查询
	 */
	public void queryUserLoginActiveDetail(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		if(agentUnit.getCashDay()!=null && !"".equals(agentUnit.getCashDay()) && 
				agentUnit.getCashDay1()!=null && !"".equals(agentUnit.getCashDay1())){
			try {
				dgb = agentUnitService.queryUserLoginActiveDetail(agentUnit,(UserBean)userSession);
			} catch (Exception e) {
				log.error("查询代理活跃及登录APP监测报表明细异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	/**
	 *  代理活跃及登录APP监测报表明细_导出
	 */
	public void exportUserLoginActiveDetail() throws IOException, RowsExceededException, WriteException {
		if(agentUnit.getCashDay()==null || "".equals(agentUnit.getCashDay()) || 
				agentUnit.getCashDay1()==null || "".equals(agentUnit.getCashDay1())){
			return;
		}
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, Object>> list = agentUnitService.exportUserLoginActiveDetail(agentUnit,(UserBean)userSession);
		String titles[] = {"代理机构号","代理机构名称","归属上级机构号","归属一代机构号","归属运营中心机构号","代理级别","活跃","登录APP","登录PC"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("AGENTNAME")==null?"":map.get("AGENTNAME").toString(),
					map.get("GUISHU")==null?"":map.get("GUISHU").toString(),
					map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
					map.get("YUNYING")==null?"":map.get("YUNYING").toString(),
					map.get("UNLEVEL")==null?"":map.get("UNLEVEL").toString(),
					map.get("ACTIVE")==null?"":map.get("ACTIVE").toString(),
					map.get("APP")==null?"":map.get("APP").toString(),
					map.get("PINTAI")==null?"":map.get("PINTAI").toString()
					
			};
			excelList.add(rowContents);
		}
		String excelName = "代理活跃及登录APP监测报表明细统计.csv";
		JxlOutExcelUtil.writeCSVFile(excelList, titles.length, response, excelName);
	}
	
	/**
	 * 代理活跃及登录APP监测报表汇总_查询
	 */
	public void queryUserLoginActiveSum(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		if(agentUnit.getCashDay()!=null && !"".equals(agentUnit.getCashDay()) && 
				agentUnit.getCashDay1()!=null && !"".equals(agentUnit.getCashDay1())){
			try {
				dgb = agentUnitService.queryUserLoginActiveSum(agentUnit,(UserBean)userSession);
			} catch (Exception e) {
				log.error("代理活跃及登录APP监测报表汇总异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	/**
	 *  代理活跃及登录APP监测报表汇总_导出
	 */
	public void exportUserLoginActiveSum() throws IOException, RowsExceededException, WriteException {
		if(agentUnit.getCashDay()==null || "".equals(agentUnit.getCashDay()) || 
				agentUnit.getCashDay1()==null || "".equals(agentUnit.getCashDay1())){
			return;
		}
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, Object>> list = agentUnitService.exportUserLoginActiveSum(agentUnit,(UserBean)userSession);
		String titles[] = {"代理级别","代理数量","活跃数量","登录APP数量","登录PC数量","登录PC占比","登录APP占比","活跃占比"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNLEVEL")==null?"":map.get("UNLEVEL").toString(),
					map.get("UNNOCOUNT")==null?"":map.get("UNNOCOUNT").toString(),
					map.get("ACTIVECOUNT")==null?"":map.get("ACTIVECOUNT").toString(),
					map.get("APPLOGINCOUNT")==null?"":map.get("APPLOGINCOUNT").toString(),
					map.get("PCLOGINCOUNT")==null?"":map.get("PCLOGINCOUNT").toString(),
					map.get("S1")==null?"":map.get("S1").toString(),
					map.get("S2")==null?"":map.get("S2").toString(),
					map.get("S3")==null?"":map.get("S3").toString()
			};
			excelList.add(rowContents);
		}
		String excelName = "代理活跃及登录APP监测报表汇总统计.csv";
		JxlOutExcelUtil.writeCSVFile(excelList, titles.length, response, excelName);
	}
}
