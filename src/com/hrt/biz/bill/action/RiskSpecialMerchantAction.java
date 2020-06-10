package com.hrt.biz.bill.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.pagebean.RiskSpecialMerchantBean;
import com.hrt.biz.bill.service.IRiskSpecialMerchantService;
import com.hrt.biz.util.PaymentRiskUtil;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("all")
public class RiskSpecialMerchantAction extends BaseAction implements ModelDriven<RiskSpecialMerchantBean>{

	private static final Log log = LogFactory.getLog(RiskSpecialMerchantAction.class);
	private RiskSpecialMerchantBean specialMerchantBean = new RiskSpecialMerchantBean(); 
	@Override
	public RiskSpecialMerchantBean getModel() {
		return specialMerchantBean;
	}
	
	private IRiskSpecialMerchantService specialMerchantService;
	
	public IRiskSpecialMerchantService getSpecialMerchantService() {
		return specialMerchantService;
	}
	public void setSpecialMerchantService(IRiskSpecialMerchantService specialMerchantService) {
		this.specialMerchantService = specialMerchantService;
	}
	
	/**
	 *特约商户信息（查询）
	 * 
	 */
	public void querySpecialPersonal() {
		DataGridBean dgb = new DataGridBean();
		try {
			specialMerchantBean.setCUSTYPE("01");
			dgb = specialMerchantService.querySpecialPersonal(specialMerchantBean);
			super.writeJson(dgb);
		} catch (Exception e) {
			log.error("特约商户信息查询异常：" + e);
		}
	}
	/**
	 * 特约商户信息上报（个人）
	 * 
	 */
	public void reportSpecialPersonal() {
		JsonBean json = new JsonBean();
		String UserToken = String.valueOf(super.getRequest().getSession().getAttribute("UserToken"));
		UserBean user = (UserBean)super.getRequest().getSession().getAttribute("user");
		if (UserToken==null||user==null) {
			json.setSuccess(false);
			json.setMsg("请先登录");
		} else {
			String OrigSender = String.valueOf(super.getRequest().getSession().getAttribute("OrigSender"));
			String date3 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			specialMerchantBean.setREPDATE(date3);
			specialMerchantBean.setOrigSender(OrigSender);
			specialMerchantBean.setTrnxCode("EPR001");
			specialMerchantBean.setUserToken(UserToken);
			specialMerchantBean.setRepPerson(user.getLoginName());
			Map map=null;
			try {
				map = specialMerchantService.reportSpecialPersonal(specialMerchantBean);
			} catch (Exception e) {
				json.setSuccess(false);
				json.setMsg("个特约商户信息上报失败（个人）！");
				e.printStackTrace();
			}
			String error = (String) map.get("error");
			if(null!=error){
				json.setSuccess(false);
				json.setMsg(String.valueOf(map.get("error")));
			}else{
				//String ResultStatus = (String) map.get("ResultStatus");// 交易结果
				String ResultCode = (String) map.get("ResultCode");// 交易返回码
				log.error("查看数据字典 返回交易码 ResultCode:"+ResultCode);
				//判断成功  S00000
				if(ResultCode.equals("S00000")){
					specialMerchantBean.setStartTime(specialMerchantBean.getStartTime().replaceAll("-", ""));
					specialMerchantBean.setEndTime(specialMerchantBean.getEndTime().replaceAll("-", ""));
					specialMerchantBean.setRepDate(date3);
					specialMerchantService.saveMerchantPersonal(specialMerchantBean);
					json.setSuccess(true);
					json.setMsg("特约商户信息上报成功（个人）！");
				}else{
					String queryResult = specialMerchantService.queryMerchantResult(ResultCode);
					json.setSuccess(false);
					json.setMsg(queryResult);
				}
			}
		}
		super.writeJson(json);
	}
	
	
	/**
	 *特约商户信息（商户查询）
	 * 
	 */
	public void querySpecialMerchant() {
		DataGridBean dgb = new DataGridBean();
		try {
			specialMerchantBean.setCUSTYPE("02,03");
			dgb = specialMerchantService.querySpecialPersonal(specialMerchantBean);
			super.writeJson(dgb);
		} catch (Exception e) {
			log.error("特约商户信息查询异常：" + e);
		}
	}
	/**
	 * 特约商户信息上报（商户）
	 * 
	 */
	public void reportSpecialMerchant() {
		JsonBean json = new JsonBean();
		String UserToken = String.valueOf(super.getRequest().getSession().getAttribute("UserToken"));
		UserBean user = (UserBean)super.getRequest().getSession().getAttribute("user");
		if (UserToken==null||user==null) {
			json.setSuccess(false);
			json.setMsg("请先登录");
		} else {
			String OrigSender = String.valueOf(super.getRequest().getSession().getAttribute("OrigSender"));
			String date3 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
			specialMerchantBean.setREPDATE(date3);
			specialMerchantBean.setOrigSender(OrigSender);
			specialMerchantBean.setTrnxCode("EER001");
			specialMerchantBean.setUserToken(UserToken);
			specialMerchantBean.setRepPerson(user.getLoginName());
			Map map=null;
			try {
				map = specialMerchantService.reportSpecialMerchant(specialMerchantBean);
			} catch (Exception e) {
				json.setSuccess(false);
				json.setMsg("特约商户信息上报失败（商户）！");
				e.printStackTrace();
			}
			String error = (String) map.get("error");
			if(null!=error){
				json.setSuccess(false);
				json.setMsg(String.valueOf(map.get("error")));
			}else{
				//String ResultStatus = (String) map.get("ResultStatus");// 交易结果
				String ResultCode = (String) map.get("ResultCode");// 交易返回码
				log.error("查看数据字典 返回交易码 ResultCode:"+ResultCode);
				//判断成功  S00000
				ResultCode="S00000";
				if(ResultCode.equals("S00000")){
					specialMerchantBean.setStartTime(specialMerchantBean.getStartTime().replaceAll("-", ""));
					specialMerchantBean.setEndTime(specialMerchantBean.getEndTime().replaceAll("-", ""));
					specialMerchantBean.setRepDate(date3);
					specialMerchantService.saveMerchantPersonal(specialMerchantBean);
					json.setSuccess(true);
					json.setMsg("特约商户信息上报成功（商户）");
				}else{
					String queryResult = specialMerchantService.queryMerchantResult(ResultCode);
					json.setSuccess(false);
					json.setMsg(queryResult);
				}
			}
		}
		super.writeJson(json);
	}

	
	/**
	 *查询推送信息 黑名单）
	 * 
	 */
	public void queryPushMsgList1() {
		DataGridBean dgb = new DataGridBean();
		try {
			specialMerchantBean.setFlag("1");
			dgb = specialMerchantService.queryPushMsg(specialMerchantBean);
			super.writeJson(dgb);
		} catch (Exception e) {
			log.error("查询推送信息 (黑名单)查询 异常：" + e);
		}
	}
	/**
	 * 添加推送信息 黑名单
	 * */
	public void addPushBlackList() throws Exception{
		/*super.getRequest().setCharacterEncoding("UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader(super.getRequest().getInputStream(),"UTF-8"));
		String line = "";
		StringBuffer buf = new StringBuffer();
		while((line=br.readLine())!=null){
			buf.append(line);
		}
		//请求的xml
		String requeststring = buf.toString();*/
		
		String parameter = super.getRequest().getParameter("xml");
		parameter = new String(parameter.getBytes("iso8859-1"),"UTF-8");
		Map readMap = new HashMap();
		Map requestXml = PaymentRiskUtil.requestXml(parameter, readMap);
		requestXml.put("Flag", "1");//黑名单
		String xml = "";
		try {
			specialMerchantService.addPushMsgList(requestXml);
			xml = specialMerchantService.repPushMsgList("R0001",true);
			
		} catch (Exception e) {
			xml = specialMerchantService.repPushMsgList("R0001",false);
			log.error("添加推送信息异常：" + e);
		}
		 
		super.getResponse().getWriter().print(xml); 
		
	}
	
	
	/**
	 *查询推送信息 风险信息）
	 * 
	 */
	public void queryPushMsgList2() {
		DataGridBean dgb = new DataGridBean();
		try {
			specialMerchantBean.setFlag("2");
			dgb = specialMerchantService.queryPushMsg(specialMerchantBean);
			super.writeJson(dgb);
		} catch (Exception e) {
			log.error("查询推送信息 (风险信息)查询 异常：" + e);
		}
	}
	/**
	 * 添加推送信息 风险信息
	 * */
	public void addPushMsgList() throws Exception{
		String parameter = super.getRequest().getParameter("xml");
		parameter = new String(parameter.getBytes("iso8859-1"),"UTF-8");
		Map readMap = new HashMap();
		Map requestXml = PaymentRiskUtil.requestXml(parameter, readMap);
		requestXml.put("Flag", "2");//黑名单
		String xml = "";
		try {
			specialMerchantService.addPushMsgList(requestXml);
			xml = specialMerchantService.repPushMsgList("R0001",true);
			
		} catch (Exception e) {
			xml = specialMerchantService.repPushMsgList("R0001",false);
			log.error("添加推送信息异常：" + e);
		}
		 
		super.getResponse().getWriter().print(xml); 
		
	}
	
	/**
	 * 商户信息比对协查推送
	 * */
	public void verifyPushMsgList() throws Exception{
		String parameter = super.getRequest().getParameter("xml");
		parameter = new String(parameter.getBytes("iso8859-1"),"UTF-8");
		Map readMap = new HashMap();
		Map requestXml = PaymentRiskUtil.requestXml(parameter, readMap);
		String xml = "";
		try {
			specialMerchantService.addPendingMerList(requestXml);
			xml = specialMerchantService.repPushMsgList("SECB01",true);
		} catch (Exception e) {
			xml = specialMerchantService.repPushMsgList("SECB01",false);
			log.error("商户信息比对协查推送：" + e);
		}
		super.getResponse().getWriter().print(xml); 
	}
	
	/**
	 * 商户信息比对协查推送 查询
	 * */
	public void queryPushMsgList(){
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = specialMerchantService.queryPushMsgList(specialMerchantBean);
			super.writeJson(dgb);
		} catch (Exception e) {
			log.error("商户信息比对协查推送 查询查询异常：" + e);
		}
	}
}
