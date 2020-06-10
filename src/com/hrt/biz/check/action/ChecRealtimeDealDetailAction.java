package com.hrt.biz.check.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.check.entity.model.CheckRealtimeDealDetailModel;
import com.hrt.biz.check.entity.pagebean.CheckRealtimeDealDetailBean;
import com.hrt.biz.check.service.ChecRealtimeDealDetailService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.UsePioOutExcel;
import com.opensymphony.xwork2.ModelDriven;

public class ChecRealtimeDealDetailAction  extends BaseAction implements ModelDriven<CheckRealtimeDealDetailBean> {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(ChecRealtimeDealDetailAction.class);
	
	CheckRealtimeDealDetailBean crm=new CheckRealtimeDealDetailBean();
	 private ChecRealtimeDealDetailService checRealtimeDealDetailService;
	public ChecRealtimeDealDetailService getChecRealtimeDealDetailService() {
		return checRealtimeDealDetailService;
	}

	public void setChecRealtimeDealDetailService(
			ChecRealtimeDealDetailService checRealtimeDealDetailService) {
		this.checRealtimeDealDetailService = checRealtimeDealDetailService;
	}

	@Override
	public CheckRealtimeDealDetailBean getModel() {
		return crm;
	}
	
	public void queryRealTimeDatebaodan() {
		DataGridBean dgb = new DataGridBean();

		UserBean user = (UserBean)super.getRequest().getSession().getAttribute("user");
		try {
			if(user.getLoginName().startsWith("baodan0")||user.getLoginName().startsWith("11100099"))user.setUnNo("110000");
			dgb=checRealtimeDealDetailService.queryRealTimeData(crm, user,1);
		} catch (Exception e) {
			log.error("查询实时交易异常：" + e);
		}
		super.writeJson(dgb);
		
	}
	
	/*
	 * 实时交易2
	 * 
	 */
	public void queryRealTimeDate01() {
		DataGridBean dgb = new DataGridBean();
		if(crm.getTxnday()==null&&crm.getTxnday1()==null&&crm.getTid()==null&&crm.getCardpan()==null && crm.getMid()==null){
			
		}
		else if("".equals(crm.getTxnday())&&"".equals(crm.getTxnday1())&&"".equals(crm.getTid())&&"".equals(crm.getCardpan())&&"".equals( crm.getMid()))
		{
			
		}
		else{
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			dgb=checRealtimeDealDetailService.queryRealTimeData(crm,(UserBean) userSession,2);
		} catch (Exception e) {
			log.error("查询实时交易异常：" + e);
		}
		}
		super.writeJson(dgb);
		
	}
	
	/*
	 *实时 交易2Excel导出
	 */
	public void listRealTimeDataExcel01() throws IOException{
		DataGridBean dgb = new DataGridBean();
        if(crm.getStan()==null&&crm.getTid()==null&&crm.getCardpan()==null && crm.getMid()==null){
        	
		}
		else if("".equals(crm.getStan())&&"".equals(crm.getTid())&&"".equals(crm.getCardpan())&&"".equals( crm.getMid()))
		{
			
		}
		else{
		  Object userSession = super.getRequest().getSession().getAttribute("user");
		   try {
		
		   List<Map<String, String>> list =(List<Map<String, String>>) checRealtimeDealDetailService.queryRealTimeData(crm,(UserBean) userSession,2).getRows();
		
		  // 查询总记录数
		  String[] titles = { "交易日期", "交易时间","商户编号","卡号","交易金额","终端号","交易种类","授权号","流水号","系统参考号","处理码","响应码"};
		  List excelList = new ArrayList();
		  excelList.add(titles);
		  for (int rowId = 0; rowId < list.size(); rowId++) {
		     Map order = list.get(rowId);
		     String txtType=null;
		     if(order.get("TXNTYPE").equals("0210")){
					txtType="正常";
				}else if(order.get("TXNTYPE").equals("0210")){
					txtType="退货";
				}else{
					txtType="冲正";
				}
		     String[] rowContents = {  
									 (String) order.get("TXNDAY"),
									 (String)order.get("TXNDATE"),
									 (String) order.get("MID"),
									 (String)order.get("CARDPAN"),
									  order.get("TXNAMOUNT").toString(),
									  order.get("TID").toString(),
									  txtType,
									  order.get("AUTHCODE").toString(),
									  order.get("STAN").toString(),
									  order.get("RRN").toString(),
									  order.get("PROCCODE").toString(),
									  order.get("RESPCODE").toString(),	  
							};
			excelList.add(rowContents);
		 }  
		 String excelName="实时交易";
		 String sheetName="实时交易";
		 String[] cellFormatType = { "t","t","t","t","t","t","t","t","t","t","t","t"};
		 UsePioOutExcel.writeExcel(excelList, 12, super.getResponse(), sheetName,excelName + ".xls", cellFormatType); // 调用导出方法
		 } catch (Exception e) {
				log.error("导出实时交易异常：" + e);
	     }
	}
}
	/*
	 * 实时交易top10跟踪
	 * 
	 */
	public void queryRealTimeTop10() {
		DataGridBean dgb = new DataGridBean();
			Object userSession = super.getRequest().getSession().getAttribute("user");
			try {
				dgb=checRealtimeDealDetailService.queryRealTimeTop10((UserBean) userSession);
			} catch (Exception e) {
				log.error("查询实时交易top10异常：" + e);
			}
		super.writeJson(dgb);
		
	}
	/**
	 * 值班前日交易查询
	 */
	public void searchTodayDealDatas(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = checRealtimeDealDetailService.queryTodayDealDatas(crm,(UserBean)userSession);
		} catch (Exception e) {
			log.error("分页业务员明细报表查询信息异常：" + e);
		}
		super.writeJson(dgb);
	}

}