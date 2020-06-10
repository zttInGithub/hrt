package com.hrt.biz.bill.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.pagebean.CheckIncomeBean;
import com.hrt.biz.bill.service.ICheckIncomeService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.UsePioOutExcel;
import com.opensymphony.xwork2.ModelDriven;


public class CheckIncomeAction extends BaseAction implements ModelDriven<CheckIncomeBean>
{
	private File upload=null;
	private Log log = LogFactory.getLog(CheckIncomeAction.class);
	private ICheckIncomeService checkincomeservice;
	private CheckIncomeBean checkIncomeBean=new CheckIncomeBean();
	
	public CheckIncomeBean getModel() {
		return checkIncomeBean;
	}
	public File getUpload() {
		return upload;
	}


	public void setUpload(File upload) {
		this.upload = upload;
	}


	public Log getLog() {
		return log;
	}


	public void setLog(Log log) {
		this.log = log;
	}


	public ICheckIncomeService getCheckincomeservice() {
		return checkincomeservice;
	}


	public void setCheckincomeservice(ICheckIncomeService checkincomeservice) {
		this.checkincomeservice = checkincomeservice;
	}


	public CheckIncomeBean getCheckIncomeBean() {
		return checkIncomeBean;
	}


	public void setCheckIncomeBean(CheckIncomeBean checkIncomeBean) {
		this.checkIncomeBean = checkIncomeBean;
	}


		//查询
		public void CheckIncomeList(){
			Object userSession = super.getRequest().getSession().getAttribute("user");
			DataGridBean dgb = new DataGridBean();
			try {
				dgb = checkincomeservice.CheckIncomequeryLists(checkIncomeBean,(UserBean)userSession);
				super.writeJson(dgb);
			} catch (Exception e) {
				log.error("信息异常：" + e);
			}
		}
	
	
	//导出
	public void CheckIncomeListExport() throws IOException{
		Object userSession = super.getRequest().getSession().getAttribute("user");
		List<Map<String, String>> list =checkincomeservice.CheckIncomeListExport(checkIncomeBean,(UserBean)userSession);
		
		// 查询总记录数
		String[] titles ={"商户编号","商户名称","结算日期","交易日期","交易金额","商户手续费","应结算金额","退货扣款","结算调整 ","实结金额","备注"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int rowId = 0; rowId < list.size(); rowId++) {
			
			Map order = list.get(rowId);
			
			String[] rowContents = { 
					 (String)order.get("MID"),
					 order.get("RNAME").toString(),
					 order.get("SETTLEDAY").toString(),
					 order.get("TXNDAY").toString(),
					 order.get("TOTSAMT").toString(),
					 order.get("TOTMFEE").toString(),
					 order.get("SHOUCOUNT").toString(),
					 order.get("TOTRAMT").toString(),
					 order.get("TOTAAMT").toString(),
					 order.get("TOTMNAMT").toString(),//.toString()
					 (String)order.get("REMARKS")//.toString()
						};
			excelList.add(rowContents);
		}  
		String excelName="进账单信息报表";
		String sheetName="进账单信息报表";
		String[] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t"};
		UsePioOutExcel.writeExcel(excelList, 11, super.getResponse(), sheetName,excelName + ".xls", cellFormatType); // 调用导出方法
	
		
	}
}
