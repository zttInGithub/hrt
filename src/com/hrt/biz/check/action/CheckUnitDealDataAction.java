package com.hrt.biz.check.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.metamodel.domain.Superclass;

import com.hrt.biz.check.entity.pagebean.ToolDealBean;
import com.hrt.biz.check.service.CheckUnitDealDataService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.hrt.util.UsePioOutExcel;
import com.hrt.util.word;
import com.opensymphony.xwork2.ModelDriven;

public class CheckUnitDealDataAction  extends BaseAction implements ModelDriven<ToolDealBean> {

	private static final long serialVersionUID = 1L;
	private String id;
	private String txn;
	private String txn1;
	private String ids;
	private String tids;
	
	private File upload_20122;
	
	public File getUpload_20122() {
		return upload_20122;
	}
	public void setUpload_20122(File upload_20122) {
		this.upload_20122 = upload_20122;
	}
	public String getTids() {
		return tids;
	}
	public void setTids(String tids) {
		this.tids = tids;
	}
	public String getTxn1() {
		return txn1;
	}
	public void setTxn1(String txn1) {
		this.txn1 = txn1;
	}
	public String getTxn() {
		return txn;
	}
	public void setTxn(String txn) {
		this.txn = txn;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}

	private static final Log log = LogFactory.getLog(CheckUnitDealDataAction.class);
	
    private CheckUnitDealDataService checkUnitDealDataService;
    
    private ToolDealBean toolDealBean=new ToolDealBean();

	public CheckUnitDealDataService getCheckUnitDealDataService() {
		return checkUnitDealDataService;
	}
	public void setCheckUnitDealDataService(
			CheckUnitDealDataService checkUnitDealDataService) {
		this.checkUnitDealDataService = checkUnitDealDataService;
	}

	@Override
	public ToolDealBean getModel() {
		return toolDealBean;
	}
	
	public void find(){
		List<Map<String,String>> list = new ArrayList<Map<String, String>>();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			list = checkUnitDealDataService.getOrder(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("查询总汇信息异常：" + e);
		}
		super.writeJson(list);
	}
//	public void find1(){
//		List<Map<String,String>> list = new ArrayList<Map<String, String>>();
//		Object userSession = super.getRequest().getSession().getAttribute("user");
//		try {
//			System.out.println(txn1+"  "+txn);
//			list = checkUnitDealDataService.getdata(txn1,id,txn,(UserBean)userSession);
//		} catch (Exception e) {
//			log.error("查询总汇信息异常：" + e);
//		}
//		super.writeJson(list);
//	}
	
	public void listCheckUnitDealData(){
		
		Object userSession = super.getRequest().getSession().getAttribute("user");
		
		DataGridBean dgb = new DataGridBean();
		try {
			System.out.println(toolDealBean.getTxnDay());
			dgb = checkUnitDealDataService.queryCheckUnitDealData(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	public void listCheckUnitDealDatabaodan(){
		UserBean user = (UserBean)super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		try {
			if(user.getLoginName().startsWith("baodan0")||user.getLoginName().startsWith("11100099"))user.setUnNo("110000");
			dgb = checkUnitDealDataService.queryCheckUnitDealData(toolDealBean,user);
		} catch (Exception e) {
			log.error("信息异常：" + e);
		}
		super.writeJson(dgb);
	}

	/**
	 * 特殊活动交易汇总
	 */
	public void listTotalSpTxn(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = checkUnitDealDataService.listTotalSpTxnGrid(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("特殊活动交易汇总查询异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 手刷采购返利统计报表
	 */
	public void listIsM35RebateCheckData(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = checkUnitDealDataService.queryIsM35RebateCheckData(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("手刷采购返利统计报表异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * mpos激活统计
	 * @author yq
	 */
	public void listIsM35RebateCheckData1(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = checkUnitDealDataService.queryIsM35RebateCheckData1(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("手刷采购返利统计报表异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 返利统计报表导入查询
	 */
	public void queryIsM35Rebate(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = checkUnitDealDataService.queryIsM35Rebate(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("返利统计报表导入查询异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 返利统计报表导出
	 */
	public void exportIsM35Rebate(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String, Object>> list = checkUnitDealDataService.exportIsM35Rebate(toolDealBean,(UserBean)userSession);
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"商户号", "SN号", "返利日期", "返利金额", "导入时间"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					String []rowContents ={
							map.get("MID")==null?"":map.get("MID").toString(),	
							map.get("SN")==null?"":map.get("SN").toString(),
							map.get("REBATEDATE")==null?"":map.get("REBATEDATE").toString(),
							map.get("REBATEAMT")==null?"":map.get("REBATEAMT").toString(),
							map.get("CDATE")==null?"":map.get("CDATE").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "返利统计导出.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出返利统计");
			}
		} catch (Exception e) {
			log.error("导出返利统计：" + e);
		}
	}
	
	/**
	 * 商户押金返利统计
	 */
	public void listMerchantRebateCheckData() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dataGridBean = checkUnitDealDataService.queryMerchantRebateData(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("商户押金返利查询异常"+e);
		}
		super.writeJson(dataGridBean);
	}
	
	/**
	 * 手刷采购返利统计报表导出
	 */
	public void listIsM35RebateCheckData_Excel(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			toolDealBean.setPage(1);
			toolDealBean.setRows(1000000);
			List<Map<String, String>> list = (List<Map<String, String>>) checkUnitDealDataService.queryIsM35RebateCheckData(toolDealBean,(UserBean)userSession).getRows();
			// 查询总记录数
			String[] titles = { "一代机构", "机构编号","商户编号","SN号","型号","出售日期","入网日期","交易金额","交易笔数","返利类型","返利日期","返利金额","返利导入时间"};
			List excelList = new ArrayList();
			excelList.add(titles);
			for (int rowId = 0; rowId < list.size(); rowId++) {
				String type = "无";
				Map order = list.get(rowId);
				if("1".equals(order.get("REBATETYPE")+"")){
					type = "循环送";
				}else if ("2".equals(order.get("REBATETYPE")+"")){
					type = "激活返利";
				}else if ("3".equals(order.get("REBATETYPE")+"")){
					type = "分期返利";
				}else if ("4".equals(order.get("REBATETYPE")+"")){
					type = "购机返利";
				}else if ("5".equals(order.get("REBATETYPE")+"")){
					type = "激活返利2";
				}else if ("6".equals(order.get("REBATETYPE")+"")){
					type = "类型6";
				}else if ("7".equals(order.get("REBATETYPE")+"")){
					type = "类型7";
				}else{
					type = order.get("REBATETYPE")+"";
				}
				String[] rowContents = {
										 (String)order.get("YIDAI"),
										 (String)order.get("UNNO"),
										 (String)order.get("HRT_MID"),
										 (String)order.get("SN1"),
										 order.get("MACHINEMODEL")+"",
										 order.get("KEYCONFIRMDATE").toString(),
										 order.get("USEDCONFIRMDATE")+"",
										 order.get("TXNAMOUNT")+"",
										 order.get("TXNCOUNT")+"",
										 type,
										 null==order.get("REBATEDATE")?"":(order.get("REBATEDATE")+""),
										 null==order.get("REBATEAMT")?"":(order.get("REBATEAMT")+""),
										 null==order.get("CDATE")?"":(order.get("CDATE")+"")
								};
				excelList.add(rowContents);
			}  
			String excelName="手刷机具返利统计报表";
			String sheetName="手刷机具返利统计报表";
			String[] cellFormatType = { "t","t","t","t","t","t","t","t","t","t","t","t","t"};
//			UsePioOutExcel.writeExcel(excelList, 11, super.getResponse(), sheetName,excelName + ".xls", cellFormatType); // 调用导出方法
			JxlOutExcelUtil.writeExcel(excelList, cellFormatType.length, super.getResponse(), sheetName,excelName + ".xls", cellFormatType); // 调用导出方法
			excelList=null;
		} catch (Exception e) {
			log.error("手刷采购返利统计报表导出异常：" + e);
		}
	}
	
	/**
	 * mpos激活返利统计表导出
	 */
	public void listIsM35RebateCheckData_Excel1(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			toolDealBean.setPage(1);
			toolDealBean.setRows(1000000);
			List<Map<String, String>> list = (List<Map<String, String>>) checkUnitDealDataService.queryIsM35RebateCheckData1(toolDealBean,(UserBean)userSession).getRows();
			// 查询总记录数
			String[] titles = { "最终机构号","最终机构名称","一代机构号","一代机构名称", "商户编号","SN号","型号","出售日期","入网日期","返利类型","返利日期","返利金额","返利导入时间"};
			List excelList = new ArrayList();
			excelList.add(titles);
			for (int rowId = 0; rowId < list.size(); rowId++) {
				String type = "无";
				Map order = list.get(rowId);
				if("1".equals(order.get("REBATETYPE")+"")){
					type = "循环送";
				}else if ("2".equals(order.get("REBATETYPE")+"")){
					type = "激活返利";
				}else if ("3".equals(order.get("REBATETYPE")+"")){
					type = "分期返利";
				}else if ("4".equals(order.get("REBATETYPE")+"")){
					type = "购机返利";
				}else if ("5".equals(order.get("REBATETYPE")+"")){
					type = "激活返利2";
				}else if ("6".equals(order.get("REBATETYPE")+"")){
					type = "类型6";
				}else if ("7".equals(order.get("REBATETYPE")+"")){
					type = "类型7";
				}else{
					type = order.get("REBATETYPE")+"";
				}
				String[] rowContents = {
										 (String)order.get("UNNO"),
						 				 (String)order.get("UN_NAME"),
										 (String)order.get("YIDAI"),
										 (String)order.get("YIDAINAME"),
										 (String)order.get("HRT_MID"),
										 (String)order.get("SN1"),
										 order.get("MACHINEMODEL")+"",
										 order.get("KEYCONFIRMDATE").toString(),
										 order.get("USEDCONFIRMDATE")+"",
										 type,
										 null==order.get("REBATEDATE")?"":(order.get("REBATEDATE")+""),
										 null==order.get("REBATEAMT")?"":(order.get("REBATEAMT")+""),
										 null==order.get("CDATE")?"":(order.get("CDATE")+"")
								};
				excelList.add(rowContents);
			}  
			String excelName="mpos激活统计报表";
			String sheetName="mpos激活统计报表";
			String[] cellFormatType = { "t","t","t","t","t","t","t","t","t","t","t","t","t"};
//			UsePioOutExcel.writeExcel(excelList, 11, super.getResponse(), sheetName,excelName + ".xls", cellFormatType); // 调用导出方法
			JxlOutExcelUtil.writeExcel(excelList, cellFormatType.length, super.getResponse(), sheetName,excelName + ".xls", cellFormatType); // 调用导出方法
			excelList=null;
		} catch (Exception e) {
			log.error("手刷采购返利统计报表导出异常：" + e);
		}
	}
	/**
	 * 商户押金返利统计导出
	 */
	public void listMerchantRebate_Excel(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			toolDealBean.setPage(1);
			toolDealBean.setRows(1000000);
			List<Map<String, String>> list = (List<Map<String, String>>) checkUnitDealDataService.queryMerchantRebateData(toolDealBean,(UserBean)userSession).getRows();
			// 查询总记录数
			String[] titles = { "一代机构", "机构编号","商户编号","SN号","型号","出售日期","入网日期","交易金额","返利金额","商户名","入账卡","入账人","开户行","行号"};
			List excelList = new ArrayList();
			excelList.add(titles);
			for (int rowId = 0; rowId < list.size(); rowId++) {
				Map order = list.get(rowId);
				String[] rowContents = {
										 (String)order.get("YIDAI"),
										 (String)order.get("UNNO"),
										 (String)order.get("HRT_MID"),
										 (String)order.get("SN1"),
										 order.get("MACHINEMODEL")+"",
										 order.get("KEYCONFIRMDATE").toString(),
										 order.get("USEDCONFIRMDATE")+"",
										 order.get("TXNAMOUNT")+"",
										 order.get("REBATEAMT")+"",
										 order.get("RNAME")+"",
										 order.get("BANKACCNO")+"",
										 order.get("BANKACCNAME")+"",
										 order.get("BANKBRANCH")+"",
										 order.get("PAYBANKID")+"",
								};
				excelList.add(rowContents);
			}  
			String excelName="商户押金返利统计报表";
			String sheetName="商户押金返利统计报表";
			String[] cellFormatType = { "t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
			JxlOutExcelUtil.writeExcel(excelList, 14, super.getResponse(), sheetName,excelName + ".xls", cellFormatType); // 调用导出方法
			excelList=null;
		} catch (Exception e) {
			log.error("商户押金返利统计报表导出异常：" + e);
		}
	}
	/**
	 * Excel 导入手刷返利
	 */
	public void  addIsM35RebateCheckData(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null){
			json.setSessionExpire(true);
		}
		// 把文件保存到服务器目录下
		String fileName = ServletActionContext.getRequest().getParameter("file20122Name");
		String rebateType = ServletActionContext.getRequest().getParameter("rebateType");
		String basePath = ServletActionContext.getRequest().getRealPath("/upload");
		List<String> errlist =new ArrayList<String>();
		File dir = new File(basePath);
		dir.mkdir();
		String path = basePath +"/"+fileName;
		File destFile = new File(path);
		upload_20122.renameTo(destFile);
		try {
			if(path.length() > 0 && path != null){
				errlist= checkUnitDealDataService.addIsM35RebateCheckData(path, user, fileName, rebateType);
				if (errlist!=null&&errlist.size()>0){
					removeUploadFile(basePath,path);
					String[] cellFormatType = {"t","t","t"};
					UsePioOutExcel.writeExcel(errlist, 3, super.getResponse(), "失败记录","返利数据导入失败记录" + ".xls", cellFormatType); // 调用导出方法
					return;
				}else{
					json.setSuccess(true);
					json.setMsg("返利数据导入成功!");
					super.writeJson(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("返利导入异常："+e);
			json.setSuccess(false);
			json.setMsg("返利数据导入失败！");
			super.writeJson(json);
		}
		removeUploadFile(basePath,path);
	}

	public void listCheckUnitDealData_bill(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = checkUnitDealDataService.queryCheckUnitDealData_bill(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	
	/*
	 * Excel导出
	 */
	@SuppressWarnings("unchecked")
	public void listCheckUnitDealDataExcel() throws IOException{
		Object userSession = super.getRequest().getSession().getAttribute("user");
		List<Map<String, String>> list =checkUnitDealDataService.queryObjectsBySqlList(toolDealBean,(UserBean)userSession);
		
		// 查询总记录数
		String[] titles = { "机构编号", "商户编号","商户名称","商户类型","终端号","交易笔数","交易金额","交易手续费","退款笔数","退款金额","退款手续费","结算金额"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int rowId = 0; rowId < list.size(); rowId++) {
			
			Map order = list.get(rowId);
			String type ="";
			if("1".equals(order.get("ISM35")+"")&&("100000".equals((String) order.get("SETTMETHOD"))||"200000".equals((String) order.get("SETTMETHOD")))){
				type="秒到";
			}else if("1".equals(order.get("ISM35")+"")&&"000000".equals((String) order.get("SETTMETHOD"))){
				type="理财";
			}else{
				type="传统 ";
			}
			String[] rowContents = {  
									 (String) order.get("UNNO"),
									 (String)order.get("MID"),
									 (String) order.get("RNAME"),
									 type,
									 (String)order.get("TID"),
									 order.get("TXNCOUNT").toString(),
									  order.get("TXNAMOUNT").toString(),
									  order.get("MDA").toString(),
									  order.get("REFUNDCOUNT").toString(),
									  order.get("REFUNDAMT").toString(),
									  order.get("REFUNDMDA").toString(),
									  order.get("MNAMT").toString(),
									  
									  
							};
			excelList.add(rowContents);
		}  
		String excelName="对账总汇";
		String sheetName="对账总汇";
		String[] cellFormatType = { "t","t","t","t","t","t","t","t","t","t","t","t"};
		try {
			JxlOutExcelUtil.writeExcel(excelList, 11, super.getResponse(), sheetName,excelName + ".xls", cellFormatType);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("对账总汇异常"+e);
		}
		excelList=null;
	}
	
	/*
	 * Excel导出   业务员
	 */
	@SuppressWarnings("unchecked")
	public void listCheckUnitDealData_billExcel() throws IOException{
		Object userSession = super.getRequest().getSession().getAttribute("user");
		List<Map<String, String>> list =checkUnitDealDataService.queryObjectsBySqlList_bill(toolDealBean,(UserBean)userSession);
		
		// 查询总记录数
		String[] titles ={"机构编号", "商户名称","商户编号","销售","交易笔数","交易金额","结算金额","退款笔数","退款金额"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int rowId = 0; rowId < list.size(); rowId++) {
			
			Map order = list.get(rowId);
			
			String[] rowContents = { 
					 (String) order.get("UNNO"),
					 (String)order.get("RNAME"),
					 (String)order.get("MID"),
					 order.get("SALENAME").toString(),
					 order.get("TXNCOUNT").toString(),
					  order.get("TXNAMOUNT").toString(),
					  order.get("MNAMT").toString(),
					  order.get("REFUNDCOUNT").toString(),
					  order.get("REFUNDAMT").toString(),
							};
			excelList.add(rowContents);
		}  
		String excelName="业务员交易报表";
		String sheetName="业务员交易报表";
		String[] cellFormatType = { "t","t","t","t","t","t","t","t","t"};
		try {
			JxlOutExcelUtil.writeExcel(excelList, 9, super.getResponse(), sheetName,excelName + ".xls", cellFormatType);
		}catch (Exception e) {
			e.printStackTrace();
			log.info("业务员交易报表异常"+e);
		}
		excelList=null;
	}
	
	/**
	 * 业务员明细报表查询
	 */
	public void searchToolDealDatas(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = checkUnitDealDataService.queryToolDealDatas(toolDealBean,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页业务员明细报表查询信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 业务员明细报表查询详细信息
	 */
	public void findToolDealDatas(){
		List<Map<String,String>> list = new ArrayList<Map<String, String>>();
		try {
			list = checkUnitDealDataService.queryFindToolDealDatas(toolDealBean);
		} catch (Exception e) {
			log.error("查询业务员明细报表详细信息异常：" + e);
		}
		super.writeJson(list);
	}
	
	/**
	 *  资料导出  业务员明细报表
	 */
	@SuppressWarnings("unchecked")
	public void exportxxx()  throws IOException{
		Map map1 = new HashMap(); 
		Object userSession = super.getRequest().getSession().getAttribute("user");
		List<Map<String, String>> list;
		List<Map<String, String>> list1;
		List<Map<String, String>> listSum;
		List<Map<String, String>> listrname;
		try {
			listrname=checkUnitDealDataService.selectrname(toolDealBean,(UserBean)userSession);
			for(int i=0;i<listrname.size();i++){Map m=listrname.get(i);	toolDealBean.setRname((String) m.get("RNAME"));}
			list = checkUnitDealDataService.queryFindToolDealDatasE(toolDealBean);
			list1 = checkUnitDealDataService.queryObjectsBySqlList_billxi1(toolDealBean,(UserBean)userSession);
			listSum=checkUnitDealDataService.queryObjectsBySqlList_billxiSum(toolDealBean,(UserBean)userSession);
			  map1.put("w", list);
			  map1.put("ww", list1);
			  map1.put("www", listSum);
			  map1.put("toolDealBean", toolDealBean);
				word w=new word();
				String filename="业务员明细报表.xls";
			      String path="mingxi.html";
				w.createDoc(map1, path, filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 查询北京地区商户——>运维使用
	 */
	public void findBeiJingAreaDealData(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		if((toolDealBean.getTxnDay()!=null && !"".equals(toolDealBean.getTxnDay())) && (toolDealBean.getTxnDay1()!=null && !"".equals(toolDealBean.getTxnDay1())) ){

			try {
				dgb = checkUnitDealDataService.findBeiJingAreaDealData(toolDealBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("查询数据异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	/*
	 * 导出北京地区商户——>运维使用
	 */
	public void exportBeiJingAreaData()throws Exception{
		List<Map<String,String>> list =checkUnitDealDataService.findBeiJingAreaDealData(toolDealBean);
		String[] titles ={"安装日期","商户名称","商户编号","终端编号","装机地址","交易额"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int rowId = 0; rowId < list.size(); rowId++) {
			
			Map order = list.get(rowId);
			
			String[] rowContents = { 
					   order.get("MAINTAINDATE").toString(),
					  (String)order.get("RNAME"),
					  (String)order.get("MID"),
					  order.get("TID").toString(),
					  order.get("INSTALLEDADDRESS").toString(),
					  order.get("TXNAMOUNT").toString(),
							};
			excelList.add(rowContents);
		}  
		String excelName="区域交易查询报表";
		String sheetName="区域交易查询报表";
		String[] cellFormatType = { "t","t","t","t","t","t"};
		JxlOutExcelUtil.writeExcel(excelList, 6, super.getResponse(), sheetName,excelName + ".xls", cellFormatType); // 调用导出方法
	}
	/**
	 * 值班前日交易查询
	 */
	public void searchYesDealDatas(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = checkUnitDealDataService.queryYesDealDatas(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("查询信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * mpose使用率，激活率
	 */
	public void queryMposeUsage(){
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = checkUnitDealDataService.findMposeUsage(toolDealBean);
		} catch (Exception e) {
			log.error("查询信息异常：" + e);
		}
		super.writeJson(dgb);
	}	

	/**
	 * 删除服务器上的上传文件
	 * @param basePath
	 * @param path
	 */
	public void removeUploadFile(String basePath,String path){
		File file = new File(basePath);
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(basePath + tempList[i]);
			} else {
				temp = new File(basePath + File.separator + tempList[i]);
			}
			if (temp.exists() && temp.isFile()) {
				boolean flag = temp.delete(); // 删除上传到服务器的文件
				int j = i + 1;
				log.error("成功删除文件:第" + j + "个文件删除是否成功？" + flag);
			} else {
				log.error("文件不存在");
			}
		}
	}
	//限制查询时间
	public void queryCheckUnitDate(){
		JsonBean json = new JsonBean();
		try {
			json = checkUnitDealDataService.queryCheckUnitDate(toolDealBean);
		} catch (Exception e) {
			log.error("查询起始日期异常：" + e);
			json.setSuccess(true);
		}
		super.writeJson(json);
	}
	
	/**
	 * @查询历史交易汇总
	 */
	public void listUnitDealData(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		if (userSession == null) {
		} else {
			try {
				dgb = checkUnitDealDataService.listUnitDealData(toolDealBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("历史交易汇总查询异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	/**
	 * @查询历史交易汇总汇总
	 */
	public void listUnitDealDataTotal(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		List<Map<String, Object>> list = null;
		if (userSession == null) {
		} else {
			try {
				list = checkUnitDealDataService.listUnitDealDataTotal(toolDealBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("历史交易汇总汇总查询异常：" + e);
			}
		}
		super.writeJson(list);
	}
	
	/**
	 * @导出历史交易汇总
	 */
	public void exportUnitDealData() {
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String,Object>> list = checkUnitDealDataService.exportUnitDealData(toolDealBean,(UserBean)userSession);
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"机构号","商户编号","商户名称","交易渠道","交易方式","交易笔数","交易金额","交易手续费","退款笔数","退款金额","退款手续费","结算金额"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					if("0".equals(map.get("ISMPOS").toString())){
						map.put("ISMPOS","MPOS");
					}else if("1".equals(map.get("ISMPOS").toString())){
						map.put("ISMPOS","传统MPOS");
					}else if("2".equals(map.get("ISMPOS").toString())) {
						map.put("ISMPOS","会员宝收银台");
					}else if("3".equals(map.get("ISMPOS").toString())) {
						map.put("ISMPOS","会员宝plus");
					}else if("4".equals(map.get("ISMPOS").toString())) {
						map.put("ISMPOS","会员宝Pro");
					}
					
					if(map.get("TYPE")==null||"".equals(map.get("TYPE"))) {
						map.put("TYPE","所有");
					}else if("0".equals(map.get("TYPE").toString())){
						map.put("TYPE","银行卡");
					}else if("1".equals(map.get("TYPE").toString())){
						map.put("TYPE","微信");
					}else if("2".equals(map.get("TYPE").toString())){
						map.put("TYPE","支付宝");
					}else if("3".equals(map.get("TYPE").toString())){
						map.put("TYPE","银联二维码");
					}else if("4".equals(map.get("TYPE").toString())){
						map.put("TYPE","快捷支付");
					}else if("5".equals(map.get("TYPE").toString())){
						map.put("TYPE","手机Pay");
					}else if("6".equals(map.get("TYPE").toString())){
						map.put("TYPE","信用卡还款");
					}else if("11".equals(map.get("TYPE").toString())){
						map.put("TYPE","花呗");
					}else if("12".equals(map.get("TYPE").toString())){
						map.put("TYPE","花呗分期");
					}
					String []rowContents ={
							map.get("UNNO")==null?"":map.get("UNNO").toString(),
							map.get("MID")==null?"":map.get("MID").toString(),	
							map.get("RNAME")==null?"":map.get("RNAME").toString(),
							map.get("ISMPOS")==null?"":map.get("ISMPOS").toString(),	
							map.get("TYPE")==null?"":map.get("TYPE").toString(),
							map.get("TXNCOUNT")==null?"":map.get("TXNCOUNT").toString(),
							map.get("TXNAMOUNT")==null?"":map.get("TXNAMOUNT").toString(),
							map.get("MDA")==null?"":map.get("MDA").toString(),
							map.get("REFUNDCOUNT")==null?"":map.get("REFUNDCOUNT").toString(),
							map.get("REFUNDAMT")==null?"":map.get("REFUNDAMT").toString(),
							map.get("REFUNDMDA")==null?"":map.get("REFUNDMDA").toString(),
							map.get("MNAMT")==null?"":map.get("MNAMT").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "历史交易汇总("+toolDealBean.getTxnDay()+"-"+toolDealBean.getTxnDay1()+").csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出历史交易汇总成功");
			}
		} catch (Exception e) {
			log.error("导出历史交易汇总异常：" + e);
		}
	}
}
