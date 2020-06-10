package com.hrt.biz.bill.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.hrt.biz.bill.entity.pagebean.PaymentRiskBean;
import com.hrt.biz.bill.service.IPaymentRiskService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.TreeNodeBean;
import com.opensymphony.xwork2.ModelDriven;

public class PaymentRiskAction extends BaseAction implements
		ModelDriven<PaymentRiskBean> {
	private static final Log log = LogFactory.getLog(PaymentRiskAction.class);

	private PaymentRiskBean paymentRiskBean = new PaymentRiskBean();

	@Override
	public PaymentRiskBean getModel() {
		return paymentRiskBean;
	}

	private IPaymentRiskService paymentRiskService;

	public IPaymentRiskService getPaymentRiskService() {
		return paymentRiskService;
	}

	public void setPaymentRiskService(IPaymentRiskService paymentRiskService) {
		this.paymentRiskService = paymentRiskService;
	}
	// ***************************************************************************//
	public void Login() {
		HttpServletRequest request=ServletActionContext.getRequest();
		String realPath = (String) request.getSession().getServletContext().getRealPath("/resource"); 
		JsonBean json = new JsonBean();
		try {
			Map map = paymentRiskService.login(paymentRiskBean,realPath);
			String ResultStatus = (String) map.get("ResultStatus");// 交易结果
			String ResultCode = (String) map.get("ResultCode");// 返回码
			String UserToken = (String) map.get("UserToken");// 用户信息凭证
			if (ResultCode.equals("S00000")) {
				super.getRequest().getSession().setAttribute("UserToken", UserToken);
				super.getRequest().getSession().setAttribute("OrigSender", paymentRiskBean.getOrigSender());
				json.setSuccess(true);
				json.setMsg("登录成功！");
			}else{
				String queryResult = paymentRiskService.queryResult(ResultCode);
				json.setSuccess(false);
				json.setMsg(queryResult);
			}
		} catch (Exception e) {
			log.error("登录异常：" + e);
			json.setSuccess(false);
			json.setMsg("登录异常，请重新登陆！");
		}

		super.writeJson(json);
	}
	
	
	public PaymentRiskBean getPaymentRiskBean() {
		return paymentRiskBean;
	}

	public void setPaymentRiskBean(PaymentRiskBean paymentRiskBean) {
		this.paymentRiskBean = paymentRiskBean;
	}

	public void logout() {
		JsonBean json = new JsonBean();
		String path =  super.getRequest().getContextPath();
		String OrigSender = String.valueOf(super.getRequest().getSession().getAttribute("OrigSender"));
		if(OrigSender==""||OrigSender==null){
			json.setSuccess(false);
			json.setMsg("对不起，您还没有登录！");
		}else{
			try {
				paymentRiskBean.setOrigSender(OrigSender);
				Map map = paymentRiskService.logout(paymentRiskBean);
				String ResultStatus = (String) map.get("ResultStatus");// 交易结果
				String ResultCode = (String) map.get("ResultCode");// 返回码
				String UserToken = (String) map.get("UserToken");// 用户信息凭证
				if (ResultCode.equals("S00000")) {
					HttpSession session1 = super.getRequest().getSession();
					super.getRequest().getSession().setAttribute("UserToken", "");
					super.getRequest().getSession().setAttribute("OrigSender", "");
					HttpSession session = super.getRequest().getSession();
					json.setSuccess(true);
					json.setMsg("退出成功！");
				}else{
					String queryResult = paymentRiskService.queryResult(ResultCode);
					json.setSuccess(false);
					json.setMsg(queryResult);
				}
			} catch (Exception e) {
				log.error("退出登录异常：" + e);
				json.setSuccess(false);
				json.setMsg("退出异常，请重新退出！");
			}
		}
		super.writeJson(json);
	}

	/**
	 * 个人风险信息上报查询
	 * 
	 */
	public void queryBeenPersonal() {
		DataGridBean dgb = new DataGridBean();
		try {
			paymentRiskBean.setTrnxCode("PR0001");
			dgb = paymentRiskService.queryBeenPersonal(paymentRiskBean);
			super.writeJson(dgb);
		} catch (Exception e) {
			log.error("个人风险上报查询信息异常：" + e);
		}
	}
	/**
	 * 个人风险信息上报
	 * 
	 */
	public void reportPersonal() {
		JsonBean json = new JsonBean();
		String UserToken = String.valueOf(super.getRequest().getSession().getAttribute("UserToken"));
		if (UserToken==null) {
			json.setSuccess(false);
			json.setMsg("请先登录");
		} else {
			String OrigSender = String.valueOf(super.getRequest().getSession().getAttribute("OrigSender"));
			
			String date3 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			paymentRiskBean.setRepDate(date3);
			paymentRiskBean.setOrigSender(OrigSender);
			paymentRiskBean.setTrnxCode("PR0001");
			paymentRiskBean.setUserToken(UserToken);
			Map map=null;
			try {
				map = paymentRiskService.reportPersonal(paymentRiskBean);
			} catch (Exception e) {
				json.setSuccess(false);
				json.setMsg("个人风险信息上报失败！");
				e.printStackTrace();
			}
			String error = (String) map.get("error");
			if(null!=error){
				json.setSuccess(false);
				json.setMsg(String.valueOf(map.get("error")));
			}else{
				String ResultStatus = (String) map.get("ResultStatus");// 交易结果
				String ResultCode = (String) map.get("ResultCode");// 交易返回码
				log.error("查看数据字典 返回交易码 ResultCode:"+ResultCode);
				//判断成功  S00000
				if(ResultCode.equals("S00000")){
					paymentRiskBean.setOccurtimeb(paymentRiskBean.getOccurtimeb().replaceAll("-", ""));
					paymentRiskBean.setOccurtimee(paymentRiskBean.getOccurtimee().replaceAll("-", ""));
					paymentRiskService.savePersonal(paymentRiskBean);
					json.setSuccess(true);
					json.setMsg("个人风险信息上报成功！");
				}else{
					String queryResult = paymentRiskService.queryResult(ResultCode);
					json.setSuccess(false);
					json.setMsg(queryResult);
				}
			}
		}
		super.writeJson(json);
	}

	/**
	 * 商户风险信息上报查询
	 * 
	 */
	public void queryBeenMerchant() {
		DataGridBean dgb = new DataGridBean();
		try {
			paymentRiskBean.setTrnxCode("ER0001");
			dgb = paymentRiskService.queryBeenMerchant(paymentRiskBean);
			super.writeJson(dgb);
		} catch (Exception e) {
			log.error("个人风险上报查询信息异常：" + e);
		}
	}
	/**
	 * 商户风险信息上报
	 * 
	 */
	public void reportMerchant() {
		JsonBean json = new JsonBean();
		String UserToken = (String) super.getRequest().getSession()
				.getAttribute("UserToken");
		
		if (UserToken==null) {
			json.setSuccess(false);
			json.setMsg("请先登录");
		} else {
			String OrigSender = String.valueOf(super.getRequest().getSession().getAttribute("OrigSender"));
			String date3 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			paymentRiskBean.setRepDate(date3);
			paymentRiskBean.setOrigSender(OrigSender);
			paymentRiskBean.setTrnxCode("ER0001");
			paymentRiskBean.setUserToken(UserToken);
			Map map = null;
			try {
				map = paymentRiskService.reportMerchant(paymentRiskBean);
			} catch (Exception e) {
				log.error("reportMerchant 异常:"+e);
			}
			String error = (String) map.get("error");
			if(null!=error){
				json.setSuccess(false);
				json.setMsg(String.valueOf(map.get("error")));
			}else{
				String ResultStatus = String.valueOf(map.get("ResultStatus"));// 交易结果
				String ResultCode = String.valueOf(map.get("ResultCode")) ;// 交易返回码
				//判断成功  S00000
				if(ResultCode.equals("S00000")){
					paymentRiskBean.setOccurtimeb(paymentRiskBean.getOccurtimeb().replaceAll("-", ""));
					paymentRiskBean.setOccurtimee(paymentRiskBean.getOccurtimee().replaceAll("-", ""));
					paymentRiskService.saveMerchant(paymentRiskBean);
					json.setSuccess(true);
					json.setMsg("商户风险信息上报成功！");
				}else{
					String queryResult = paymentRiskService.queryResult(ResultCode);
					json.setSuccess(false);
					json.setMsg(queryResult);
				}
			}
		}
		super.writeJson(json);
	}
	
	
	/**
	 * 个人风险信息查询
	 * 
	 */
	public void queryPersonal() {
		DataGridBean dgb = new DataGridBean();
		JsonBean json = new JsonBean();
		String UserToken = (String) super.getRequest().getSession() 
				.getAttribute("UserToken");
		if (UserToken==null) {
			json.setSuccess(true);
			json.setMsg("请先登录");
			super.writeJson(json);
		} else {
			//HttpServletRequest request = super.getRequest();
			//paymentRiskBean.setIp(getIp(request));
			String OrigSender = String.valueOf(super.getRequest().getSession().getAttribute("OrigSender"));
			paymentRiskBean.setOrigSender(OrigSender);
			paymentRiskBean.setUserToken(UserToken);
			Map map = null;
			try {
				map = paymentRiskService.queryPersonal(paymentRiskBean);
			} catch (Exception e) {
				log.error("queryPersonal 异常:"+e);
			}
			String error=(String)map.get("error");
			if(null!=error){
				json.setSuccess(true);
				json.setMsg(String.valueOf(map.get("error")));
				super.writeJson(json);
			}else{
				String ResultStatus = String.valueOf(map.get("ResultStatus")) ;// 交易结果
				String ResultCode = String.valueOf(map.get("ResultCode")) ;// 交易返回码
				//判断成功  S00000
				if(ResultCode.equals("S00000")){
					List resultlist = (List) map.get("resultlist");
					dgb.setRows(resultlist);
					dgb.setTotal(resultlist.size());
					super.writeJson(dgb);
					
				}else{
					String queryResult = paymentRiskService.queryResult(ResultCode);
					json.setSuccess(true);
					json.setMsg(queryResult);
					super.writeJson(json);
				}
			}
		}
		
	}
	
	
	/**
	 * 商户风险信息查询
	 * 
	 */
	public void queryMerchant() {
		DataGridBean dgb = new DataGridBean();
		JsonBean json = new JsonBean();
		String UserToken = (String) super.getRequest().getSession() 
				.getAttribute("UserToken");
		if (UserToken==null) {
			json.setMsg("请先登录");
			json.setSuccess(true);
			super.writeJson(json);
		} else {
			String OrigSender = String.valueOf(super.getRequest().getSession().getAttribute("OrigSender"));
			paymentRiskBean.setOrigSender(OrigSender);
			paymentRiskBean.setUserToken(UserToken);
			Map map = null;
			try {
				map = paymentRiskService.queryMerchant(paymentRiskBean);
			} catch (Exception e) {
				log.error("queryMerchant 异常:"+e);
			}
			String error = (String) map.get("error");
			if(null!=error){
				json.setSuccess(true);
				json.setMsg(String.valueOf(map.get("error")));
				super.writeJson(json);
			}else{
				String ResultStatus = (String) map.get("ResultStatus");// 交易结果
				String ResultCode = (String) map.get("ResultCode");// 交易返回码
				//判断成功  S00000
				if(ResultCode.equals("S00000")){
					List resultlist = (List) map.get("resultlist");
					dgb.setRows(resultlist);
					dgb.setTotal(resultlist.size());
					super.writeJson(dgb);
				}else{
					String queryResult = paymentRiskService.queryResult(ResultCode);
					json.setSuccess(true);
					json.setMsg(queryResult);
					super.writeJson(json);
				}
			}
		}
	}
	
	/**
	 * 批量信息导入查询
	 * 
	 */
	public void queryBatchImport() {
		DataGridBean dgb = new DataGridBean();
		JsonBean json = new JsonBean();
		String UserToken = (String) super.getRequest().getSession() 
				.getAttribute("UserToken");
		if (UserToken==null) {
			json.setSuccess(true);
			json.setMsg("请先登录");
			super.writeJson(json);
		}else if(paymentRiskBean.getKeyWord()==""||paymentRiskBean.getKeyWord()==null||paymentRiskBean.getInfos()==""||paymentRiskBean.getInfos()==null){
			json.setSuccess(true);
			json.setMsg("关键字和查询条件信息不可为空！");
			super.writeJson(json);
		}else {
			String OrigSender = String.valueOf(super.getRequest().getSession().getAttribute("OrigSender"));
			paymentRiskBean.setOrigSender(OrigSender);
			paymentRiskBean.setUserToken(UserToken);
			Map map = null;
			try {
				map = paymentRiskService.queryBatchImport(paymentRiskBean);
			} catch (Exception e) {
				log.error("queryBatchImport 异常:"+e);
			}
			String error = (String) map.get("error");
			if(null!=error){
				json.setSuccess(true);
				json.setMsg(error);
				super.writeJson(json);
			}else{
				String ResultStatus = (String) map.get("ResultStatus");// 交易结果
				String ResultCode = (String) map.get("ResultCode");// 交易返回码
				//判断成功  S00000
				if(ResultCode.equals("S00000")){
					List resultlist = (List) map.get("resultlist");
					dgb.setRows(resultlist);
					dgb.setTotal(resultlist.size());
					super.writeJson(dgb);
				}else{
					String queryResult = paymentRiskService.queryResult(ResultCode);
					json.setSuccess(true);
					json.setMsg(queryResult);
					super.writeJson(json);
				}
			}
		}
	}
	
	
	/**
	 * 风险信息汇总查询
	 * 
	 */
	public void queryConfluence() {
		DataGridBean dgb = new DataGridBean();
		JsonBean json = new JsonBean();
		String UserToken = (String) super.getRequest().getSession() 
				.getAttribute("UserToken");
		
		if (UserToken==null) {
			json.setSuccess(true);
			json.setMsg("请先登录");
			super.writeJson(json);
		} else {
			HttpServletRequest request = super.getRequest();
			String OrigSender = String.valueOf(super.getRequest().getSession().getAttribute("OrigSender"));
			paymentRiskBean.setOrigSender(OrigSender);
			paymentRiskBean.setIp(getIp(request));
			paymentRiskBean.setUserToken(UserToken);
			Map map = null;
			try {
				map = paymentRiskService.queryConfluence(paymentRiskBean);
			} catch (Exception e) {
				log.error("queryConfluence 异常:"+e);
			}
			String error = (String) map.get("error");
			if(null!=error){
				json.setSuccess(true);
				json.setMsg(error);
				super.writeJson(json);
			}else{
				String ResultStatus = (String) map.get("ResultStatus");// 交易结果
				String ResultCode = (String) map.get("ResultCode");// 交易返回码
				//判断成功  S00000
				if(ResultCode.equals("S00000")){
					List resultlist = (List) map.get("resultlist");
					dgb.setRows(resultlist);
					dgb.setTotal(resultlist.size());
					super.writeJson(dgb);
				}else{
					String queryResult = paymentRiskService.queryResult(ResultCode);
					json.setSuccess(true);
					json.setMsg(queryResult);
					super.writeJson(json);
				}
			}
		}
	}
	
	
	
	/**
	 * 个人风险信息变更之查询
	 * 
	 */
	public void queryPersonalChange() {
		DataGridBean dgb = new DataGridBean();
		JsonBean json = new JsonBean();
		String UserToken = (String) super.getRequest().getSession() 
				.getAttribute("UserToken");
		
		if (UserToken==null) {
			json.setSuccess(true);
			json.setMsg("请先登录");
			super.writeJson(json);
		} else {
			String OrigSender = String.valueOf(super.getRequest().getSession().getAttribute("OrigSender"));
			paymentRiskBean.setOrigSender(OrigSender);
			paymentRiskBean.setUserToken(UserToken);
			Map map = null;
			try {
				map = paymentRiskService.queryPersonalChange(paymentRiskBean);
			} catch (Exception e) {
				log.error("queryPersonalChange 异常:"+e);
			}
			String error = (String) map.get("error");
			if(null!=error){
				json.setSuccess(true);
				json.setMsg(error);
				super.writeJson(json);

			}else{
				String ResultStatus = (String) map.get("ResultStatus");// 交易结果
				String ResultCode = (String) map.get("ResultCode");// 交易返回码
				//判断成功  S00000
				if(ResultCode.equals("S00000")){
					List resultlist = (List) map.get("resultlist");
					dgb.setRows(resultlist);
					dgb.setTotal(resultlist.size());
					super.writeJson(dgb);
				}else{
					String queryResult = paymentRiskService.queryResult(ResultCode);
					json.setSuccess(true);
					json.setMsg(queryResult);
					super.writeJson(json);
				}
			}
		}
	}
	
	/**
	 * 个人风险信息变更之修改
	 */
	public void updatePersonalChange() {
		DataGridBean dgb = new DataGridBean();
		JsonBean json = new JsonBean();
		String UserToken = (String) super.getRequest().getSession() 
				.getAttribute("UserToken");
		
		if (UserToken==null) {
			json.setSuccess(false);
			json.setMsg("请先登录");
		} else {
			String OrigSender = String.valueOf(super.getRequest().getSession().getAttribute("OrigSender"));
			paymentRiskBean.setOrigSender(OrigSender);
			paymentRiskBean.setUserToken(UserToken);
			Map map =null;
			try {
				map = paymentRiskService.updatePersonalChange(paymentRiskBean);
			} catch (Exception e) {
				log.error("updatePersonalChange 异常:"+e);
			}
			String error = (String) map.get("error");
			if(null!=error){
				json.setSuccess(false);
				json.setMsg(String.valueOf(map.get("error")));
			}else{
				String ResultStatus = (String) map.get("ResultStatus");// 交易结果
				String ResultCode = (String) map.get("ResultCode");// 交易返回码
				//判断成功  S00000
				if(ResultCode.equals("S00000")){
					json.setSuccess(true);
					json.setMsg("修改成功！");
				}else{
					String queryResult = paymentRiskService.queryResult(ResultCode);
					json.setSuccess(false);
					json.setMsg(queryResult);
				}
			}
		}
		super.writeJson(json);
	}
	
	
	/**
	 * 商户风险信息变更之查询
	 * 
	 */
	public void queryMerchantChange() {
		DataGridBean dgb = new DataGridBean();
		JsonBean json = new JsonBean();
		String UserToken = (String) super.getRequest().getSession() 
				.getAttribute("UserToken");
		
		if (UserToken==null) {
			json.setSuccess(true);
			json.setMsg("请先登录");
			super.writeJson(json);
		} else {
			String OrigSender = String.valueOf(super.getRequest().getSession().getAttribute("OrigSender"));
			paymentRiskBean.setOrigSender(OrigSender);
			paymentRiskBean.setUserToken(UserToken);
			Map map = null;
			try {
				map = paymentRiskService.queryMerchantChange(paymentRiskBean);
			} catch (Exception e) {
				log.error("queryMerchantChange 异常:"+e);
			}
			String error = (String) map.get("error");
			if(null!=error){
				json.setSuccess(true);
				json.setMsg(error);
				super.writeJson(json);
			}else{
				String ResultStatus = (String) map.get("ResultStatus");// 交易结果
				String ResultCode = (String) map.get("ResultCode");// 交易返回码
				//判断成功  S00000
				if(ResultCode.equals("S00000")){
					List resultlist = (List) map.get("resultlist");
					dgb.setRows(resultlist);
					dgb.setTotal(resultlist.size());
					super.writeJson(dgb);
				}else{
					String queryResult = paymentRiskService.queryResult(ResultCode);
					json.setSuccess(true);
					json.setMsg(queryResult);
					super.writeJson(json);
				}
			}
		}
	}
	
	/**
	 * 商户风险信息变更之修改
	 */
	public void updateMerchantChange() {
		DataGridBean dgb = new DataGridBean();
		JsonBean json = new JsonBean();
		String UserToken = (String) super.getRequest().getSession() 
				.getAttribute("UserToken");
		if (UserToken==null) {
			json.setSuccess(false);
			json.setMsg("请先登录");
		} else {
			String OrigSender = String.valueOf(super.getRequest().getSession().getAttribute("OrigSender"));
			paymentRiskBean.setOrigSender(OrigSender);
			paymentRiskBean.setUserToken(UserToken);
			Map map = null;
			try {
				map = paymentRiskService.updateMerchantChange(paymentRiskBean);
			} catch (Exception e) {
				log.error("updateMerchantChange 异常:"+e);
			}
			String error = (String) map.get("error");
			if(null!=error){
				json.setSuccess(false);
				json.setMsg(String.valueOf(map.get("error")));
			}else{
				String ResultStatus = (String) map.get("ResultStatus");// 交易结果
				String ResultCode = (String) map.get("ResultCode");// 交易返回码
				//判断成功  S00000
				if(ResultCode.equals("S00000")){
					json.setSuccess(true);
					json.setMsg("修改成功！");
				}else{
					String queryResult = paymentRiskService.queryResult(ResultCode);
					json.setSuccess(false);
					json.setMsg(queryResult);
				}
			}
		}
		super.writeJson(json);
	}
	
	  /**
	   * 行政区字典
	   * */
	public void queryDictionary() {
		List<TreeNodeBean> nodeList = null;
		try {
			nodeList = paymentRiskService.queryDictionary1(null);
		} catch (Exception e) {
			log.error("queryDictionary 异常:"+e);
		}
		super.writeJson(nodeList);
	}
	  /**
	   * 获取IP*/
	 
	private String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
