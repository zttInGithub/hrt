package com.hrt.biz.credit.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
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
import com.hrt.biz.credit.entity.pagebean.CreditInfoBean;
import com.hrt.biz.credit.entity.pagebean.LoanInfoBean;
import com.hrt.biz.credit.service.CreditAgentService;
import com.hrt.biz.credit.service.LoanService;
import com.hrt.biz.util.JSONObject;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;
import com.hrt.util.HttpXmlClient;

public class LoanServiceImpl implements LoanService {

	private LoanDao loanDao;
	private IUnitDao unitDao;
	private String creditUrl;
	
	private static final Log log = LogFactory.getLog(LoanServiceImpl.class);
	
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

	public LoanDao getLoanDao() {
		return loanDao;
	}

	public void setLoanDao(LoanDao loanDao) {
		this.loanDao = loanDao;
	}

	/**
	 * 贷款申请
	 */
	@Override
	public JsonBean addLoanApply(LoanInfoBean loanBean, UserBean user){
		JsonBean json1 = new JsonBean();
		boolean flag = false;
		loanBean.setAgentName(user.getUnitName());
		loanBean.setCreTimeLimit(Integer.parseInt(loanBean.getCreTimeLimit())*30+"");
		loanBean.setUnno(user.getUnNo());
		String json = JSON.toJSONString(loanBean);
		try{
			log.info("贷款申请 "+json);
			String ss=HttpXmlClient.postForJson(creditUrl+"/credit/creInfo_LoanApply.action", json);
			JSONObject jsonObject = new JSONObject(ss);
			String status =(String)jsonObject.get("status");//"1"失败 0成功
			String msg =(String) jsonObject.get("msg");
			if("0".equals(status)){
				json1.setSuccess(true);
			}else{
				json1.setSuccess(false);
			}
			json1.setMsg(msg);
		}catch (Exception e) {
			log.info("贷款申请请求异常 "+e);
			json1.setSuccess(false);
			json1.setMsg("贷款申请请求失败");
		}
		return json1;
	}
}
