package com.hrt.biz.credit.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.check.entity.model.CheckWechatTxnDetailModel;
import com.hrt.biz.credit.dao.CreditAgentDao;
import com.hrt.biz.credit.dao.LoanDao;
import com.hrt.biz.credit.dao.LoanRepayDao;
import com.hrt.biz.credit.entity.pagebean.CreditInfoBean;
import com.hrt.biz.credit.entity.pagebean.DeAmtHistInfoBean;
import com.hrt.biz.credit.entity.pagebean.LoanInfoBean;
import com.hrt.biz.credit.entity.pagebean.LoanRepayInfoBean;
import com.hrt.biz.credit.service.CreditAgentService;
import com.hrt.biz.credit.service.LoanRepayService;
import com.hrt.biz.credit.service.LoanService;
import com.hrt.biz.util.JSONObject;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;
import com.hrt.util.HttpXmlClient;

public class LoanRepayServiceImpl implements LoanRepayService {

	private LoanRepayDao loanRepayDao;
	private IUnitDao unitDao;
	private String creditUrl;
	
	private static final Log log = LogFactory.getLog(LoanRepayServiceImpl.class);
	
	public String getCreditUrl() {
		return creditUrl;
	}

	public void setCreditUrl(String creditUrl) {
		this.creditUrl = creditUrl;
	}


	public IUnitDao getUnitDao() {
		return unitDao;
	}

	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}

	public LoanRepayDao getLoanRepayDao() {
		return loanRepayDao;
	}

	public void setLoanRepayDao(LoanRepayDao loanRepayDao) {
		this.loanRepayDao = loanRepayDao;
	}

	/**
	 * 还款申请
	 */
	@Override
	public Map<String, Object> LoanRepay(LoanRepayInfoBean loanRepayInfoBean, UserBean user){
		boolean flag = false;
		Map<String, Object> map = new HashMap<String, Object>();
		loanRepayInfoBean.setAgentName(user.getUnitName());
		loanRepayInfoBean.setUnno(user.getUnNo());
		try{
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		    String dateString = formatter.format(loanRepayInfoBean.getRepayTimeDate());
			loanRepayInfoBean.setRepayTime(dateString);
			String json = JSON.toJSONString(loanRepayInfoBean);
			log.info("还款申请 "+json);
			String ss=HttpXmlClient.postForJson(creditUrl+"/credit/repay_LoanRepay.action", json);
			JSONObject jsonObject = new JSONObject(ss);
			String status =(String)jsonObject.get("status");//"1"失败 0成功
			String msg =(String) jsonObject.get("msg");
			map.put("status", status);
			map.put("msg", msg);
		}catch (Exception e) {
			log.info("还款申请请求异常 "+e);
		}
		return map;
	}
	/**
	 * 查询还款记录
	 */
	public DataGridBean queryRepayInfo(LoanRepayInfoBean loanRepayInfoBean, UserBean user){
		
		DataGridBean dataGridBean = new DataGridBean();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("unno",user.getUnNo());
		params.put("protocolNo",loanRepayInfoBean.getProtocolNo());
		params.put("pageNum",loanRepayInfoBean.getPage());
		params.put("rowPerPage",loanRepayInfoBean.getRows());
		String json = JSON.toJSONString(params);
		try{
			log.info("查询还款记录返回 "+json);
			String ss=HttpXmlClient.postForJson(creditUrl+"/credit/repay_queryRepayInfo.action", json);
			dataGridBean= loanRepayInfoBean.XmlToOb(ss);
		}catch (Exception e) {
			log.info("查询还款记录请求异常 "+e);
		}
		return dataGridBean;
	}
	
	/**
	 * 查询扣款记录
	 */
	public DataGridBean queryDeAmtHist(LoanRepayInfoBean loanRepayInfoBean, UserBean user){
		
		DataGridBean dataGridBean = new DataGridBean();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("unno",user.getUnNo());
		params.put("protocolNo",loanRepayInfoBean.getProtocolNo());
		params.put("pageNum",loanRepayInfoBean.getPage());
		params.put("rowPerPage",loanRepayInfoBean.getRows());
		String json = JSON.toJSONString(params);
		try{
			log.info("查询扣款记录返回 "+json);
			String ss=HttpXmlClient.postForJson(creditUrl+"/credit/repay_queryDeAmtHist.action", json);
			dataGridBean= new DeAmtHistInfoBean().XmlToOb(ss);
		}catch (Exception e) {
			log.info("查询扣款记录请求异常 "+e);
		}
		return dataGridBean;
	}
}
