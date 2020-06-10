package com.hrt.biz.check.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.check.entity.pagebean.ToolDealBean;
import com.hrt.biz.check.service.CheckUnitDealDetailService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.hrt.util.UsePioOutExcel;
import com.hrt.util.word;
import com.opensymphony.xwork2.ModelDriven;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import sun.print.resources.serviceui;

public class CheckUnitDealDetailAction extends BaseAction implements ModelDriven<ToolDealBean> {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(CheckUnitDealDetailAction.class);
	
	private ToolDealBean toolDealBean=new ToolDealBean();
	private CheckUnitDealDetailService checkUnitDealDetailService;
	
	public CheckUnitDealDetailService getCheckUnitDealDetailService() {
		return checkUnitDealDetailService;
	}

	public void setCheckUnitDealDetailService(
			CheckUnitDealDetailService checkUnitDealDetailService) {
		this.checkUnitDealDetailService = checkUnitDealDetailService;
	}
	@Override
	public ToolDealBean getModel() {
		return toolDealBean;
	}

	/*
	 * 20130交易对账明细报表
	 */
	public void listCheckUnitDealDetail(){
	     
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = checkUnitDealDetailService.queryCheckUnitDealDetail(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("查询信息异常：" + e);
		}
		super.writeJson(dgb);
	  }
	/*
	 * 20130交易对账明细报表    详情
	 */
	public void findCheckUnitDealDetail(){
		List<Map<String,String>> list = new ArrayList<Map<String, String>>();
		try {
			list = checkUnitDealDetailService.queryFindCheckUnitDealDetail(toolDealBean);
		} catch (Exception e) {
			log.error("查询业务员明细报表详细信息异常：" + e);
		}
		super.writeJson(list);
	}
	
	/*
	 * 20140交易待结算报表
	 */
	public void listCheckUnitDealDetailNoClosing(){
			Object userSession = super.getRequest().getSession().getAttribute("user");
			DataGridBean dgb = new DataGridBean();
			try {
				dgb = checkUnitDealDetailService.queryCheckUnitDealDetailNoClosing(toolDealBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("分页查询角色信息异常：" + e);
			}
			super.writeJson(dgb);
		}
	
	//20190  零交易量查询 
	public void zerocountChecklist(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = checkUnitDealDetailService.queryzerocountCheckdata(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询角色信息异常：" + e);
		}
		super.writeJson(dgb);
	  }
	
	//20190  零交易量导出
		public void zerocountCheckExport() throws IOException{
			Object userSession = super.getRequest().getSession().getAttribute("user");
			List<Map<String, String>> list = checkUnitDealDetailService.zerocountCheckListExport(toolDealBean,(UserBean)userSession);
			
			// 查询总记录数
			String[] titles ={"商户编号","商户名称","销售","交易总笔数","交易总金额","日期","归属地"};
			List excelList = new ArrayList();
			excelList.add(titles);
			for (int rowId = 0; rowId < list.size(); rowId++) {
				
				Map order = list.get(rowId);
				
				String[] rowContents = { 
						 (String)order.get("MID"),
						 order.get("RNAME").toString(),
						 order.get("SALENAME").toString(),
						 order.get("ZEROCOUNT").toString(),
						 order.get("ZEROMONCOUNT").toString(),
						 order.get("ZEROTIME").toString(),
						 order.get("UN_NAME").toString(),
							};
				excelList.add(rowContents);
			}  
			String excelName="零交易量报表";
			String sheetName="零交易量报表";
			String[] cellFormatType = {"t","t","t","t","t","t","t"};
			UsePioOutExcel.writeExcel(excelList, 7, super.getResponse(), sheetName,excelName + ".xls", cellFormatType); // 调用导出方法
				
		}
		
	/*
	 * Excel导出  明细
	 */
	@SuppressWarnings("unchecked")
	public void listCheckUnitDealDetailExcel(){
		Map map1 = new HashMap(); 
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			List<Map<String, String>> listrname=checkUnitDealDetailService.selectrname(toolDealBean,(UserBean)userSession);
			for(int i=0;i<listrname.size();i++){Map m=listrname.get(i);	toolDealBean.setRname((String) m.get("RNAME"));}
			List<Map<String, String>> list =checkUnitDealDetailService.queryObjectsBySqlList(toolDealBean,(UserBean)userSession);  //分组后
			List<Map<String, String>> list1 =checkUnitDealDetailService.queryFindCheckUnitDealE(toolDealBean,(UserBean)userSession);  //分组明细
			List<Map<String, String>> listsum =checkUnitDealDetailService.queryObjectsBySqlListSum(toolDealBean,(UserBean)userSession);  //合计
			if(list==null||list.size()==0){
				return ;
			}
			map1.put("x", list);
			map1.put("xx", list1);
			map1.put("xxx", listsum);
			map1.put("toolDealBean", toolDealBean);
			word w=new word();
			String filename="交易对账明细表.xls";
			String path="duizhang.html";
			w.createDoc(map1, path, filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 机构交易汇总报表
	 */
	public void listCheckUnitTxnSum(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		if(toolDealBean.getTxnDay()!=null && !"".equals(toolDealBean.getTxnDay()) && 
				toolDealBean.getTxnDay1()!=null && !"".equals(toolDealBean.getTxnDay1())){
			try {
				dgb = checkUnitDealDetailService.queryCheckUnitTxnSum(toolDealBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("查询机构交易信息异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	/*
	 * 机构交易汇总报表(权限)
	 */
	public void listCheckUnitTxnSumByLimit(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		if(toolDealBean.getTxnDay()!=null && !"".equals(toolDealBean.getTxnDay()) && 
				toolDealBean.getTxnDay1()!=null && !"".equals(toolDealBean.getTxnDay1())){
			try {
				dgb = checkUnitDealDetailService.queryCheckUnitTxnSumByLimit(toolDealBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("查询机构交易信息异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	
	/**
	 * 钱包提现记录
	 */
	public void queryWalletCash(){	
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = checkUnitDealDetailService.queryCash(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("查询钱包提现记录信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 导出钱包提现记录
	 */
	@SuppressWarnings("unchecked")
	public void exportWalletCash() throws IOException{

		Object userSession = super.getRequest().getSession().getAttribute("user");
		List<Map<String, String>> list =checkUnitDealDetailService.exportWalletCash(toolDealBean,(UserBean)userSession);
		
		// 查询总记录数
		String[] titles = { "MID", "提现金额","提现日期","提现手续费","提现时间"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int rowId = 0; rowId < list.size(); rowId++) {
			
			Map order = list.get(rowId);
			
			String[] rowContents = {  
									 (String)order.get("MID"),
									 order.get("CASHAMT") == null ? "0" :order.get("CASHAMT").toString(), 
									 order.get("CASHDAY") == null ? "" :order.get("CASHDAY").toString(), 
									 order.get("CASHFEE") == null ? "0" :order.get("CASHFEE").toString(), 
									 order.get("CASHDATE") == null ? "" :order.get("CASHDATE").toString(), 							  									  
							};
			excelList.add(rowContents);
		}  
		String excelName="钱包提现记录";
		String sheetName="钱包提现记录";
		String[] cellFormatType = { "t","t","t","t","t"};
		UsePioOutExcel.writeExcel(excelList, 5, super.getResponse(), sheetName,excelName + ".xls", cellFormatType); // 调用导出方法

	}
	
	public void exportCheckUnitTxnSumData(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=checkUnitDealDetailService.exportCheckUnitTxnSumData(toolDealBean);
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("机构交易汇总导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出机构交易汇总Excel成功");
			} catch (Exception e) {
				log.error("导出机构交易汇总Excel异常：" + e);
				json.setMsg("导出机构交易汇总Excel失败");
			}
		}
	}
	
	/**
	 * 刷卡交易分润汇总
	 */
	public void queryFirstAgentProfitCollect(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = checkUnitDealDetailService.queryFirstAgentProfitCollect(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询一级代理分润报表信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 刷卡交易分润汇总mpos
	 */
	public void queryfirstAgentProfitCollect_20303(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = checkUnitDealDetailService.queryFirstAgentProfitCollect_20303(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询一级代理手刷分润报表信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 扫码交易分润汇总
	 */
	public void queryFirstAgentProfitScan() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dataGridBean = checkUnitDealDetailService.queryFirstAgentProfitScan(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询一级代理扫描分润报表信息异常：" + e);
		}
		super.writeJson(dataGridBean);
	}
	
	/**
	 * 收银台交易分润汇总
	 */
	public void queryBigScanProfit() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dataGridBean = checkUnitDealDetailService.queryBigScanProfit(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询一级代理收银台交易分润汇总异常：" + e);
		}
		super.writeJson(dataGridBean);
	}
	
	/**
	 * 查询转账和提现分润
	 */
	public void queryFirstAgentTransferAndGetCash() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dataGridBean = checkUnitDealDetailService.queryFirstAgentTransferAndGetCash(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询一级代理转账和提现异常：" +e);
		}
		super.writeJson(dataGridBean);		
	}

	/**
	 *
	 */
	public void queryFirstAgentTransferAndGetCashSyt() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dataGridBean = checkUnitDealDetailService.queryFirstAgentTransferAndGetCashSyt(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询一级代理转账和提现异常：" +e);
		}
		super.writeJson(dataGridBean);
	}

	/**
	 * 代理商分润-plus提现转账
	 */
	public void queryTransferAndGetCashPlus() {
		// @author:lxg-20190627 代理商分润-plus提现转账
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dataGridBean = checkUnitDealDetailService.queryTransferAndGetCashPlus(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询一级代理转账和提现异常：" +e);
		}
		super.writeJson(dataGridBean);
	}
	
	/**
	 * 查询刷卡分润明细（一级代理）
	 */
	public void queryFirstAgentSwipProfitDetail() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dataGridBean = checkUnitDealDetailService.queryFirstAgentSwipProfitDetail(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询刷卡分润明细：" +e);
		}
		super.writeJson(dataGridBean);	
	}
	
	/**
	 * 查询刷卡分润明细mpos（一级代理）
	 */
	public void queryFirstAgentSwipProfitDetail_20304() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dataGridBean = checkUnitDealDetailService.queryFirstAgentSwipProfitDetail_20304(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询刷卡分润明细：" +e);
		}
		super.writeJson(dataGridBean);	
	}
	
	/**
	 * 查询扫码分润明细
	 */
	public void queryFirstAgentScanProfitDetail() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dataGridBean = checkUnitDealDetailService.queryFirstAgentScanProfitDetail(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询扫码分润明细：" +e);
		}
		super.writeJson(dataGridBean);	
	}
	
	/**
	 * 查询收银台分润明细
	 */
	public void queryBigScanProfitDetail() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dataGridBean = checkUnitDealDetailService.queryBigScanProfitDetail(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询收银台分润明细：" +e);
		}
		super.writeJson(dataGridBean);	
	}
	
	/**
	 * 查询一级代理转账和取现分润明细
	 */
	public void queryFirstAgentTransferAndGetCashProfitDetail() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dataGridBean = checkUnitDealDetailService.queryFirstAgentTransferAndGetCashProfitDetail(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询一级代理转账和取现分润明细：" +e);
		}
		super.writeJson(dataGridBean);
	}

	public void queryFirstAgentTransferAndGetCashProfitDetailSyt() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dataGridBean = checkUnitDealDetailService.queryFirstAgentTransferAndGetCashProfitDetailSyt(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询一级代理转账和取现分润明细：" +e);
		}
		super.writeJson(dataGridBean);
	}
	
	/**
	 * 导出一阶代理刷卡交易分润(新)
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public void exportFirstAgentProfitCollect() throws IOException, RowsExceededException, WriteException{
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, String>> list = checkUnitDealDetailService.exportFirstAgentProfitCollect(toolDealBean, (UserBean)userSession);
		List excelList = new ArrayList();
		String title [] = {"机构号","机构名","商户种类","交易金额","交易笔数","商户手续费","手续费分润"};
		excelList.add(title);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("UNNAME")==null?"":map.get("UNNAME").toString(),
					map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
					map.get("MDA")==null?"":map.get("MDA").toString(),
					map.get("COUNT")==null?"0":map.get("COUNT").toString(),
					map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
					map.get("PROFIT")==null?"":map.get("PROFIT").toString()
			};
			excelList.add(rowContents);
		}
		String excelName = "非秒到刷卡交易分润汇总表.csv";
//		String sheetName = "非秒到刷卡交易分润汇总表";
//		String [] cellFormatType = {"t","t","t","t","t","t"};
//		JxlOutExcelUtil.writeExcel(excelList, 6, getResponse(), sheetName, excelName+".xls", cellFormatType);
		JxlOutExcelUtil.writeCSVFile(excelList, 7, response, excelName);
		list=null;
		excelList=null;
	}
	/**
	 * 导出一阶代理刷卡交易分润(新mpos)
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public void exportFirstAgentProfitCollect20303() throws IOException, RowsExceededException, WriteException{
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, String>> list = checkUnitDealDetailService.exportFirstAgentProfitCollect_20303(toolDealBean, (UserBean)userSession);
		List excelList = new ArrayList();
		// @author:lxg-20190520 代理商分润-刷卡分润汇总修改新表
		String title [] = {"机构号","机构名","商户种类","交易金额","交易笔数","手续费","手续费分润"};
		excelList.add(title);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("UNNAME")==null?"":map.get("UNNAME").toString(),
					map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
					map.get("MDA")==null?"":map.get("MDA").toString(),
					map.get("COUNT")==null?"0":map.get("COUNT").toString(),
					map.get("CHARGE")==null?"":map.get("CHARGE").toString(),
					map.get("PROFIT")==null?"":map.get("PROFIT").toString()
			};
			excelList.add(rowContents);
		}
		String excelName = "秒到刷卡交易分润汇总表.csv";
//		String sheetName = "秒到刷卡交易分润汇总表";
//		String [] cellFormatType = {"t","t","t","t","t","t"};
//		JxlOutExcelUtil.writeExcel(excelList, 6, getResponse(), sheetName, excelName+".xls", cellFormatType);
		JxlOutExcelUtil.writeCSVFile(excelList, title.length, response, excelName);
		list=null;
		excelList=null;
	}
	
	/**
	 * 导出一阶代理刷卡分润明细表
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public void exportFirstAgentProfitCollectDetail() throws IOException, RowsExceededException, WriteException{
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, String>> list  = checkUnitDealDetailService.exportFirstAgentProfitCollectDetail(toolDealBean, (UserBean)userSession);
		String title[] = {"机构号","机构名称","商户编号","商户名称","商户种类","维护销售","种类2(1:标准,2:优惠,3：减免)","交易金额","交易笔数","商户手续费","手续费分润"};
		List excelList = new ArrayList();
		excelList.add(title);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents [] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("UNNAME")==null?"":map.get("UNNAME").toString(),
					map.get("MID")==null?"":map.get("MID").toString(),
					map.get("RNAME")==null?"":map.get("RNAME").toString(),
					map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
					map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
					map.get("AREATYPE")==null?"":map.get("AREATYPE").toString(),
					map.get("TXNAMOUNT")==null?"0":map.get("TXNAMOUNT").toString(),
					map.get("COUNT")==null?"0":map.get("COUNT").toString(),
					map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
					map.get("PROFIT")==null?"0":map.get("PROFIT").toString()
					
			};
			excelList.add(rowContents);
		}
		String excelName = "非秒到刷卡交易分润明细表.csv";
//		String sheetName = "非秒到刷卡交易分润明细表";
//		String [] cellFormatType = {"t","t","t","t","t","t","t","t"};
//		JxlOutExcelUtil.writeExcel(excelList, 8, getResponse(), sheetName, excelName+".xls", cellFormatType);
		JxlOutExcelUtil.writeCSVFile(excelList, 11, response, excelName);
		excelList=null;
		list=null;
	}	
		/**
		 * 导出一阶代理刷卡分润明细表mpos
		 * @throws IOException 
		 * @throws WriteException 
		 * @throws RowsExceededException 
		 */
		public void exportFirstAgentProfitCollectDetail_20304() throws IOException, RowsExceededException, WriteException{
			Object userSession = super.getRequest().getSession().getAttribute("user");
			HttpServletResponse response = super.getResponse();
			List<Map<String, String>> list  = checkUnitDealDetailService.exportFirstAgentProfitCollectDetail_20304(toolDealBean, (UserBean)userSession);
			String title[] = {"机构号","机构名称","商户编号","商户名称","商户种类","交易金额","交易笔数","商户手续费","手续费分润"};
			List excelList = new ArrayList();
			excelList.add(title);
			for (int i = 0; i < list.size(); i++) {
				Map map = list.get(i);
				String rowContents [] = {
						map.get("UNNO")==null?"":map.get("UNNO").toString(),
						map.get("UNNAME")==null?"":map.get("UNNAME").toString(),
						map.get("MID")==null?"":map.get("MID").toString(),
						map.get("RNAME")==null?"":map.get("RNAME").toString(),
						map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
						map.get("TXNAMOUNT")==null?"0":map.get("TXNAMOUNT").toString(),
						map.get("COUNT")==null?"0":map.get("COUNT").toString(),
						map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
						map.get("PROFIT")==null?"0":map.get("PROFIT").toString()
																						
				};
				excelList.add(rowContents)	;
			}
			String excelName = "秒到刷卡交易分润明细表.csv";
//			String sheetName = "秒到刷卡交易分润明细表";
//			String [] cellFormatType = {"t","t","t","t","t","t","t","t"};
//			JxlOutExcelUtil.writeExcel(excelList, 8, getResponse(), sheetName, excelName+".xls", cellFormatType);
			JxlOutExcelUtil.writeCSVFile(excelList, 9, response, excelName);
			list=null;
			excelList=null;
	}
	
	/**
	 * 导出一阶代理扫码分润汇总
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public void exportFirstAgentProfitScan() throws IOException, RowsExceededException, WriteException {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, String>> list = checkUnitDealDetailService.exportFirstAgentProfitScan(toolDealBean,(UserBean)userSession);
		String titles[] = {"机构号","机构名","商户类型","交易金额","交易笔数","手续费分润"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("UN_NAME")==null?"":map.get("UN_NAME").toString(),
//					map.get("FIID")==null?"":map.get("FIID").toString(),
					map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),// settmethod
					map.get("TXNAMOUNT")==null?"":map.get("TXNAMOUNT").toString(),
					map.get("TXNCOUNT")==null?"0":map.get("TXNCOUNT").toString(),
					map.get("PROFIT")==null?"":map.get("PROFIT").toString()	
			};
			
			excelList.add(rowContents);
		}
		String excelName = "扫码交易分润汇总表.csv";
//		String sheetName = "扫码交易分润汇总表";
//		String [] cellFormatType = {"t","t","t","t","t","t"};
//		JxlOutExcelUtil.writeExcel(excelList, 6, getResponse(), sheetName, excelName+".xls", cellFormatType);
		JxlOutExcelUtil.writeCSVFile(excelList, 6, response, excelName);
		list=null;
		excelList=null;
	}
	
	/**
	 * 导出收银台分润汇总
	 */
	public void exportBigScanProfit() throws IOException, RowsExceededException, WriteException {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, String>> list = checkUnitDealDetailService.exportBigScanProfit(toolDealBean,(UserBean)userSession);
		String titles[] = {"机构号","机构名","类型","交易金额","交易笔数","手续费分润","活动类型"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("UNNAME")==null?"":map.get("UNNAME").toString(),
					//map.get("MERTYPE")==null?"":getBigScanProfitType(map.get("MERTYPE").toString(),
                    //        map.get("MINFO2")==null?"":map.get("MINFO2").toString()),
					map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
					map.get("TXNAMOUNT")==null?"":map.get("TXNAMOUNT").toString(),
					map.get("TXNCOUNT")==null?"":map.get("TXNCOUNT").toString(),
					map.get("PROFIT")==null?"":map.get("PROFIT").toString(),
					map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString()
			};
			
			excelList.add(rowContents);
		}
		String excelName = "收银台交易分润汇总表.csv";
		JxlOutExcelUtil.writeCSVFile(excelList, titles.length, response, excelName);
		list=null;
		excelList=null;
	}

	private static String getBigScanProfitType(String merType,String info2){
		if("2".equals(merType)){
			return "支付宝";
		}else if("3".equals(merType)){
			return "银联二维码";
		}else if("1".equals(merType) && "1".equals(info2)){
			return "微信0.38";
		}else if("1".equals(merType) && "2".equals(info2)){
			return "微信0.45";
		}else if("1".equals(merType) && "3".equals(info2)){
			return "微信(老)";
		}else if("1".equals(merType)){
			return "扫码";
		}
		return "";
	}
	
	/**
	 * 导出一阶代理扫码分润明细
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public void exportFirstAgentScanProfitDetail() throws IOException, RowsExceededException, WriteException {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, String>> list = checkUnitDealDetailService.exportFirstAgentScanProfitDetail(toolDealBean,(UserBean)userSession);
		String titles[] = {"机构号","机构名","商户编号","商户名称","交易类型","交易金额","交易笔数","手续费分润"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("UN_NAME")==null?"":map.get("UN_NAME").toString(),
					map.get("MID")==null?"":map.get("MID").toString(),
					map.get("RNAME")==null?"":map.get("RNAME").toString(),
					map.get("FIID")==null?"":map.get("FIID").toString(),
					map.get("TXNAMOUNT")==null?"":map.get("TXNAMOUNT").toString(),
					map.get("TXNCOUNT")==null?"0":map.get("TXNCOUNT").toString(),
					map.get("PROFIT")==null?"":map.get("PROFIT").toString()	
			};
			excelList.add(rowContents);
		}
		String excelName = "扫码交易分润明细表.csv";
//		String sheetName = "扫码交易分润明细表";
//		String [] cellFormatType = {"t","t","t","t","t","t","t","t"};
//		JxlOutExcelUtil.writeExcel(excelList, 8, getResponse(), sheetName, excelName+".xls", cellFormatType);
		JxlOutExcelUtil.writeCSVFile(excelList, 8, response, excelName);
		list=null;
		excelList=null;
	}
	
	/**
	 * 导出收银台分润明细
	 */
	public void exportBigScanProfitDetail() throws IOException, RowsExceededException, WriteException {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, String>> list = checkUnitDealDetailService.exportBigScanProfitDetail(toolDealBean,(UserBean)userSession);
		String titles[] = {"机构号","机构名","商户编号","商户名称","类型","交易金额","交易笔数","手续费分润","活动类型"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("UNNAME")==null?"":map.get("UNNAME").toString(),
					map.get("MID")==null?"":map.get("MID").toString(),
					map.get("RNAME")==null?"":map.get("RNAME").toString(),
					//map.get("TYPE")==null?"":getBigScanProfitDetailType(map.get("TYPE").toString(),
                    //        map.get("IFCARD")==null?"":map.get("IFCARD").toString()),
				    map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
					map.get("TXNAMOUNT")==null?"":map.get("TXNAMOUNT").toString(),
					map.get("TXNCOUNT")==null?"0":map.get("TXNCOUNT").toString(),
					map.get("PROFIT")==null?"":map.get("PROFIT").toString(),
					map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString()
							
			};
			excelList.add(rowContents);
		}
		String excelName = "收银台交易分润明细表.csv";
		JxlOutExcelUtil.writeCSVFile(excelList, titles.length, response, excelName);
		list=null;
		excelList=null;
	}

	private static String getBigScanProfitDetailType(String type,String ifCard){
		if("2".equals(type)){
			return "支付宝";
		}else if("3".equals(type)){
			return "银联二维码";
		}else if("1".equals(type) && "1".equals(ifCard)){
			return "微信0.38";
		}else if("1".equals(type) && "2".equals(ifCard)){
			return "微信0.45";
		}else if("1".equals(type) && "3".equals(ifCard)){
			return "微信(老)";
		}else if("1".equals(type)){
			return "微信";
		}
		return "";
	}

	public void exportFirstAgentTransferAndGetCashPlus() throws IOException, RowsExceededException, WriteException {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, String>> list = checkUnitDealDetailService.exportFirstAgentTransferAndGetCashPlus(toolDealBean,(UserBean)userSession);
		String titles[] = {"机构号","机构名","活动类型","交易类型","提现笔数","提现手续费","提现转账分润"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("UNNAME")==null?"":map.get("UNNAME").toString(),
					map.get("MINFO1")==null?"":map.get("MINFO1").toString(),
					map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
					//map.get("MERTYPE")==null?"":getFirstAgentTransferAndGetCashPlusType(map.get("MERTYPE").toString(),
					//		map.get("MINFO2")==null?"":map.get("MINFO2").toString()),
					map.get("COUNT")==null?"0":map.get("COUNT").toString(),
					map.get("SECONDRATE")==null?"":map.get("SECONDRATE").toString(),
					map.get("PROFIT")==null?"":map.get("PROFIT").toString()
			};
			excelList.add(rowContents);
		}
		String excelName = "提现转账分润MPOS活动提现分润汇总表.csv";
		JxlOutExcelUtil.writeCSVFile(excelList, titles.length, response, excelName);
	}

	private static String getFirstAgentTransferAndGetCashPlusType(String merType,String minfo2){
		if("10".equals(merType)){
			return "刷卡";
		}else if("11".equals(merType) && "1".equals(minfo2)){
			return "微信0.38";
		}else if("11".equals(merType) && "2".equals(minfo2)){
			return "微信0.45";
		}else if("11".equals(merType) && "3".equals(minfo2)){
			return "微信(老)";
		}else if("11".equals(merType) && "4".equals(minfo2)){
			return "支付宝";
		}else if("11".equals(merType) && "5".equals(minfo2)){
			return "银联二维码";
		}else if("11".equals(merType)){
			return "扫码";
		}
		return "";
	}
	
	public void exportFirstAgentTransferAndGetCash() throws IOException, RowsExceededException, WriteException {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, String>> list = checkUnitDealDetailService.exportFirstAgentTransferAndGetCash(toolDealBean,(UserBean)userSession);
		String titles[] = {"机构号","机构名","商户种类","交易金额","交易笔数","提现手续费","提现转账分润"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("UNNAME")==null?"":map.get("UNNAME").toString(),
					map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
					map.get("CASHAMT")==null?"":map.get("CASHAMT").toString(),
					map.get("COUNT")==null?"0":map.get("COUNT").toString(),
					map.get("SECONDRATE")==null?"":map.get("SECONDRATE").toString(),
					map.get("PROFIT")==null?"":map.get("PROFIT").toString()	
			};
			excelList.add(rowContents);
		}
		String excelName = "提现转账分润汇总表.csv";
//		String sheetName = "提现转账分润汇总表";
//		String [] cellFormatType = {"t","t","t","t","t","t","t","t"};
//		JxlOutExcelUtil.writeExcel(excelList, 6, getResponse(), sheetName, excelName+".xls", cellFormatType);
		JxlOutExcelUtil.writeCSVFile(excelList, 7, response, excelName);
		list=null;
		excelList=null;
	}

	/**
	 * 收银台提现转账分润汇总导出
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void exportFirstAgentTransferAndGetCashSyt() throws IOException, RowsExceededException, WriteException {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, String>> list = checkUnitDealDetailService.exportFirstAgentTransferAndGetCashSyt(toolDealBean,(UserBean)userSession);
//		String titles[] = {"机构号","机构名","商户","交易金额","交易笔数","提现手续费","提现转账分润"};
		String titles[] = {"机构号","机构名","类型","交易金额","交易笔数","提现手续费","提现转账分润","活动类型"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("UNNAME")==null?"":map.get("UNNAME").toString(),
					//map.get("MINFO2")==null?"扫码":getFirstAgentTransferAndGetCashSytType(map.get("MINFO2").toString()),
					map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),		
//					map.get("MID")==null?"":map.get("MID").toString(),
					map.get("CASHAMT")==null?"":map.get("CASHAMT").toString(),
					map.get("COUNT")==null?"0":map.get("COUNT").toString(),
					map.get("SECONDRATE")==null?"":map.get("SECONDRATE").toString(),
					map.get("PROFIT")==null?"":map.get("PROFIT").toString(),
					map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString()
			};
			excelList.add(rowContents);
		}
		String excelName = "收银台提现转账分润汇总.csv";
//		String sheetName = "提现转账分润汇总表";
//		String [] cellFormatType = {"t","t","t","t","t","t","t","t"};
//		JxlOutExcelUtil.writeExcel(excelList, 6, getResponse(), sheetName, excelName+".xls", cellFormatType);
		JxlOutExcelUtil.writeCSVFile(excelList, titles.length, response, excelName);
		list=null;
		excelList=null;
	}

	private static String getFirstAgentTransferAndGetCashSytType(String info2){
	    if("1".equals(info2)){
            return "微信0.38";
        }else if("2".equals(info2)){
            return "微信0.45";
        }else if("3".equals(info2)){
            return "微信(老)";
        }else if("4".equals(info2)){
            return "支付宝";
        }else if("5".equals(info2)){
            return "银联二维码";
        }
	    return "扫码";
    }
	
	
	public void exportFirstAgentTransferAndGetCashProfitDetail() throws IOException, RowsExceededException, WriteException {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, String>> list = checkUnitDealDetailService.exportFirstAgentTransferAndGetCashProfitDetail(toolDealBean,(UserBean)userSession);
		String titles[] = {"机构号","机构名","商户编号","商户名称","商户种类","交易金额","交易笔数","提现手续费","提现转账分润"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("UNNAME")==null?"":map.get("UNNAME").toString(),
					map.get("MID")==null?"":map.get("MID").toString(),
					map.get("RNAME")==null?"":map.get("RNAME").toString(),		
					map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
					map.get("CASHAMT")==null?"":map.get("CASHAMT").toString(),
					map.get("COUNT")==null?"0":map.get("COUNT").toString(),
					map.get("SECONDRATE")==null?"":map.get("SECONDRATE").toString(),
					map.get("PROFIT")==null?"":map.get("PROFIT").toString()	
			};
			excelList.add(rowContents);
		}
		String excelName = "提现转账分润明细表.csv";
//		String sheetName = "提现转账分润明细表";
//		String [] cellFormatType = {"t","t","t","t","t","t","t","t"};
//		JxlOutExcelUtil.writeExcel(excelList, 8, getResponse(), sheetName, excelName+".xls", cellFormatType);
		JxlOutExcelUtil.writeCSVFile(excelList, 9, response, excelName);
		list=null;
		excelList=null;
	}

	/**
	 * 收银台提现转账分润明细导出
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void exportFirstAgentTransferAndGetCashProfitDetailSyt() throws IOException, RowsExceededException, WriteException {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, String>> list = checkUnitDealDetailService.exportFirstAgentTransferAndGetCashProfitDetailSyt(toolDealBean,(UserBean)userSession);
		String titles[] = {"机构号","机构名","商户编号","商户名称","类型","交易金额","交易笔数","提现手续费","提现转账分润","活动类型"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("UNNAME")==null?"":map.get("UNNAME").toString(),
					map.get("MID")==null?"":map.get("MID").toString(),
					map.get("MRNAME")==null?"":map.get("MRNAME").toString(),
					map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
					//map.get("TXNTYPE")==null?"扫码":getScanType(map.get("TXNTYPE").toString()),
//					map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
					map.get("CASHAMT")==null?"":map.get("CASHAMT").toString(),
					map.get("COUNT")==null?"0":map.get("COUNT").toString(),
					map.get("SECONDRATE")==null?"":map.get("SECONDRATE").toString(),
					map.get("PROFIT")==null?"":map.get("PROFIT").toString(),
					map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString()
			};
			excelList.add(rowContents);
		}
		String excelName = "提现转账分润明细表.csv";
//		String sheetName = "提现转账分润明细表";
//		String [] cellFormatType = {"t","t","t","t","t","t","t","t"};
//		JxlOutExcelUtil.writeExcel(excelList, 8, getResponse(), sheetName, excelName+".xls", cellFormatType);
		JxlOutExcelUtil.writeCSVFile(excelList, titles.length, response, excelName);
		list=null;
		excelList=null;
	}

	private static String getScanType(String tranType){
		if("1".equals(tranType)){
			return "微信0.38";
		}else if("2".equals(tranType)){
			return "微信0.45";
		}else if("3".equals(tranType)){
			return "微信(老)";
		}else if("4".equals(tranType)){
			return "支付宝";
		}else if("5".equals(tranType)){
			return "银联二维码";
		}
		return "扫码";
	}
	
	/**
	 * @查询历史交易明细
	 */
	public void listUnitDealDetail(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		if (userSession == null) {
		} else {
			try {
				dgb = checkUnitDealDetailService.listUnitDealDetail(toolDealBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("实时交易信息查询异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	/**
	 * @查询历史交易明细汇总
	 */
	public void listUnitDealDetailTotal(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		List<Map<String, Object>> list = null;
		if (userSession == null) {
		} else {
			try {
				list = checkUnitDealDetailService.listUnitDealDetailTotal(toolDealBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("历史交易明细汇总查询异常：" + e);
			}
		}
		super.writeJson(list);
	}
	
	/**
	 * @导出历史交易明细
	 */
	public void exportUnitDealDetail() {
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String,Object>> list = checkUnitDealDetailService.exportUnitDealDetail(toolDealBean,(UserBean)userSession);
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"机构号","机构名称","业务员","商户编号","商户名称","交易日期","交易时间","交易金额","手续费","交易渠道","交易方式","交易类型","交易卡号","卡类型","处理状态","授权码","流水号/商户订单号","参考号","终端编号","SN号"};
				
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					if("1".equals(map.get("ISMPOS").toString())){
						map.put("ISMPOS","传统POS");
					}else if("0".equals(map.get("ISMPOS").toString())){
						map.put("ISMPOS","MPOS");
					}else if("2".equals(map.get("ISMPOS").toString())) {
						map.put("ISMPOS","会员宝收银台");
					}else if("3".equals(map.get("ISMPOS").toString())) {
						map.put("ISMPOS","会员宝plus");
					}else if("4".equals(map.get("ISMPOS").toString())) {
						map.put("ISMPOS","会员宝Pro");
					}
					
					if(map.get("CBRAND")==null){
						map.put("CBRAND","");
					}else if("1".equals(map.get("CBRAND").toString())){
						map.put("CBRAND","借记卡");
					}else if("2".equals(map.get("CBRAND").toString())){
						map.put("CBRAND","贷记卡");
					}
					if("0".equals(map.get("TYPE").toString())){
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
					}else if("12".equals(map.get("TYPE").toString())) {
						map.put("TYPE","花呗分期");
					}
					if("0".equals(map.get("MTI").toString())){
						map.put("MTI","消费");
					}else if("1".equals(map.get("MTI").toString())){
						map.put("MTI","预授权");
					}else if("2".equals(map.get("MTI").toString())){
						map.put("MTI","预授权撤销");
					}else if("3".equals(map.get("MTI").toString())){
						map.put("MTI","消费撤销");
					}else if("4".equals(map.get("MTI").toString())){
						map.put("MTI","冲正");
					}else if("5".equals(map.get("MTI").toString())){
						map.put("MTI","扫码退款");
					}else if("6".equals(map.get("MTI").toString())){
						map.put("MTI","银行卡退货");
					}
					if("0".equals(map.get("TXNSTATE").toString())){
						map.put("TXNSTATE","成功");
					}else if("1".equals(map.get("TXNSTATE").toString())){
						map.put("TXNSTATE","失败");
					}else if("2".equals(map.get("TXNSTATE").toString())){
						map.put("TXNSTATE","待付款");
					}
					String []rowContents ={
							map.get("UNNO")==null?"":map.get("UNNO").toString(),
							map.get("UN_NAME")==null?"":map.get("UN_NAME").toString(),
							map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
							map.get("MID")==null?"":map.get("MID").toString(),	
							map.get("RNAME")==null?"":map.get("RNAME").toString(),
							map.get("TXNDAY")==null?"":map.get("TXNDAY").toString(),
							map.get("TXNDATE")==null?"":map.get("TXNDATE").toString(),
							map.get("TXNAMOUNT")==null?"":map.get("TXNAMOUNT").toString(),
							map.get("MDA")==null?"":map.get("MDA").toString(),
							map.get("ISMPOS")==null?"":map.get("ISMPOS").toString(),	
							map.get("TYPE")==null?"":map.get("TYPE").toString(),
							map.get("MTI")==null?"":map.get("MTI").toString(),
							map.get("CARDPAN")==null?"":map.get("CARDPAN").toString(),
							map.get("CBRNAD")==null?"":map.get("CBRNAD").toString(),
							map.get("TXNSTATE")==null?"":map.get("TXNSTATE").toString(),
							map.get("AUTHCODE")==null?"":map.get("AUTHCODE").toString(),
							map.get("STAN")==null?"":map.get("STAN").toString(),
							map.get("RRN")==null?"":map.get("RRN").toString(),
							map.get("TID")==null?"":map.get("TID").toString(),
							map.get("INSTALLEDNAME")==null?"":map.get("INSTALLEDNAME").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "历史交易明细("+toolDealBean.getTxnDay()+"-"+toolDealBean.getTxnDay1()+").csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出历史交易明细成功");
			}
		} catch (Exception e) {
			log.error("导出历史交易明细异常：" + e);
		}
	}
	
	/**
	 * mpos活动分润汇总
	 */
	public void queryMposProfit() {
		DataGridBean dataGridBean = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dataGridBean = checkUnitDealDetailService.queryMposProfit(toolDealBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询一级代理Mpos活动交易分润汇总异常：" + e);
		}
		super.writeJson(dataGridBean);
	}
	
	/**
	 * 导出Mpos活动分润汇总
	 */
	public void exportMposProfit() throws IOException, RowsExceededException, WriteException {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, Object>> list = checkUnitDealDetailService.exportMposProfit(toolDealBean,(UserBean)userSession);
		String titles[] = {"机构号","机构名","活动类型","交易类型","交易总金额","交易总笔数","商户手续费","手续费分润"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("UN_NAME")==null?"":map.get("UN_NAME").toString(),
					map.get("MINFO1")==null?"":map.get("MINFO1").toString(),
					map.get("TXNTYPE")==null?"":map.get("TXNTYPE").toString(),
					map.get("TXNAMOUNT")==null?"":map.get("TXNAMOUNT").toString(),
					map.get("TXNCOUNT")==null?"":map.get("TXNCOUNT").toString(),
					map.get("MDA")==null?"":map.get("MDA").toString(),		
					map.get("PROFIT")==null?"":map.get("PROFIT").toString()	
			};
			excelList.add(rowContents);
		}
		String excelName = "MPOS活动交易分润汇总.csv";
		JxlOutExcelUtil.writeCSVFile(excelList, titles.length, response, excelName);
	}

	/**
	 * 导出代理汇总交易信息
	 */
	public void exportCheckUnitTxnSumDataByLimit() throws IOException, RowsExceededException, WriteException {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, Object>> list = checkUnitDealDetailService.exportCheckUnitTxnSumDataByLimit(toolDealBean,(UserBean)userSession);
		String titles[] = {"机构号","机构名名称","刷卡金额","刷卡笔数","刷卡活跃商户","扫码金额","扫码笔数","扫码活跃商户","合计金额","合计笔数","合计活跃商户"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("AGENTNAME")==null?"":map.get("AGENTNAME").toString(),
					map.get("S1")==null?"":map.get("S1").toString(),
					map.get("S2")==null?"":map.get("S2").toString(),
					map.get("S3")==null?"":map.get("S3").toString(),
					map.get("S4")==null?"":map.get("S4").toString(),
					map.get("S5")==null?"":map.get("S5").toString(),		
					map.get("S6")==null?"":map.get("S6").toString(),	
					map.get("JINEZONG")==null?"":map.get("JINEZONG").toString(),	
					map.get("BISHUZONG")==null?"":map.get("BISHUZONG").toString(),	
					map.get("HUOYUEZONG")==null?"":map.get("HUOYUEZONG").toString(),	
			};
			excelList.add(rowContents);
		}
		String excelName = "代理汇总交易查询.csv";
		JxlOutExcelUtil.writeCSVFile(excelList, titles.length, response, excelName);
	}
}