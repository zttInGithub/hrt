package com.hrt.phone.action;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hrt.biz.bill.entity.model.ProblemFeedbackModel;
import com.hrt.biz.bill.service.IProblemFeedbackService;
import com.hrt.frame.constant.PhoneProdConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.bill.entity.pagebean.CheckIncomeBean;
import com.hrt.biz.bill.service.ICheckIncomeService;
import com.hrt.biz.bill.service.MertidCardService;
import com.hrt.biz.check.service.CheckUnitDealDetailService;
import com.hrt.biz.util.unionpay.DemoBase;
import com.hrt.biz.util.unionpay.HrtRSAUtil;
import com.hrt.biz.util.unionpay.SHAEncUtil;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.JsonBeanForSign;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.phone.service.IPhoneCheckUnitDealDataService;
import com.hrt.phone.service.IPhoneWechatPublicAccService;
import com.hrt.util.HttpXmlClient;
import com.opensymphony.xwork2.ModelDriven;

public class PhoneWechatPublicAccAction extends BaseAction implements
		ModelDriven<UserBean> {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(PhoneWechatPublicAccAction.class);

	private UserBean user = new UserBean();

	private IPhoneWechatPublicAccService phoneWechatPublicAccService;

	private IProblemFeedbackService problemFeedbackService;

	private CheckUnitDealDetailService checkUnitDealDetailService;
	
	private ICheckIncomeService checkincomeService;
	
	private MertidCardService mertidCardService;
	
	private IPhoneCheckUnitDealDataService phoneCheckUnitDealDataService;
	
	private String loginMposPhone;
	
	private static String signEnd = "&key=dseesa325errtcyraswert749errtdyt";

	private Object parse;
	
	private String admAppIp;

	public IProblemFeedbackService getProblemFeedbackService() {
		return problemFeedbackService;
	}
	public void setProblemFeedbackService(IProblemFeedbackService problemFeedbackService) {
		this.problemFeedbackService = problemFeedbackService;
	}
	public String getLoginMposPhone() {
		return loginMposPhone;
	}
	public void setLoginMposPhone(String loginMposPhone) {
		this.loginMposPhone = loginMposPhone;
	}
	public IPhoneCheckUnitDealDataService getPhoneCheckUnitDealDataService() {
		return phoneCheckUnitDealDataService;
	}
	public void setPhoneCheckUnitDealDataService(IPhoneCheckUnitDealDataService phoneCheckUnitDealDataService) {
		this.phoneCheckUnitDealDataService = phoneCheckUnitDealDataService;
	}
	public void setAdmAppIp(String admAppIp) {
		this.admAppIp = admAppIp;
	}
	public String getAdmAppIp() {
		return admAppIp;
	}
	
	public void setMertidCardService(MertidCardService mertidCardService) {
		this.mertidCardService = mertidCardService;
	}
	public MertidCardService getMertidCardService() {
		return mertidCardService;
	}

	public ICheckIncomeService getCheckincomeService() {
		return checkincomeService;
	}

	public void setCheckincomeService(ICheckIncomeService checkincomeService) {
		this.checkincomeService = checkincomeService;
	}

	public CheckUnitDealDetailService getCheckUnitDealDetailService() {
		return checkUnitDealDetailService;
	}

	public void setCheckUnitDealDetailService(
			CheckUnitDealDetailService checkUnitDealDetailService) {
		this.checkUnitDealDetailService = checkUnitDealDetailService;
	}

	public IPhoneWechatPublicAccService getPhoneWechatPublicAccService() {
		return phoneWechatPublicAccService;
	}

	public void setPhoneWechatPublicAccService(
			IPhoneWechatPublicAccService phoneWechatPublicAccService) {
		this.phoneWechatPublicAccService = phoneWechatPublicAccService;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	@Override
	public UserBean getModel() {
		return user;
	}

	public void loginPhone() {
		JsonBean json = new JsonBean();
		String LoginByUrl = (String) super.getRequest().getSession().getAttribute("LoginByUrl");
		try {
			String loginName = super.getRequest().getParameter("loginName").trim();
			String state = super.getRequest().getParameter("password");
			String rand = super.getRequest().getParameter("rand");
			String random = super.getRequest().getParameter("random");
			String prod = super.getRequest().getParameter("prod");
			if (!rand.equalsIgnoreCase(random)) {
				json.setSuccess(true);
				json.setMsg("验证码错误！");
				super.writeJson(json);
				return;
			}
			String openid = null;
			Cookie cookies[] = super.getRequest().getCookies(); // 读出用户硬盘上的Cookie，并将所有的Cookie放到一个cookie对象数组里面
			String agentId = null;
			int cookieflag = 0;
			for (int i = 0; i < cookies.length; i++) { // 用一个循环语句遍历刚才建立的Cookie对象数组
				log.info("【cookie:"+cookies[i].getName()+","+cookies[i].getValue()+"】");
				if(cookies[i].getName().equals("HrtOpenid")){
					openid = cookies[i].getValue();
					cookieflag = 1;
				}else if(cookies[i].getName().equals("HrtAgentId")&&!"null".equals(cookies[i].getValue())
						&& null !=cookies[i].getValue()){
					agentId = cookies[i].getValue();
				}
			}
			if (cookieflag == 0) {
				json.setSuccess(true);
				json.setMsg("登录超时，请重新进入！");
				super.writeJson(json);
				return;
			}
			user.setOpenid(openid);

			JSONObject jsonBody = new JSONObject();
			if(StringUtils.isNotEmpty(prod)) {
				jsonBody.put("agentId", prod);
			}else {
				jsonBody.put("agentId", "null".equals(agentId) || agentId == null ? PhoneProdConstant.PHONE_MD : agentId);
			}
			jsonBody.put("channel", "iso11.2");
			jsonBody.put("deviceId", "44F97A21-D2BF-4AC3-9A20-30BBC99C6A80");
			jsonBody.put("latitude", "39.959796");
			jsonBody.put("loginName", super.getRequest().getParameter("loginName").trim());
			jsonBody.put("longitude", "116.317437");
			jsonBody.put("model", "iPod Touch 6G");
			jsonBody.put("userPassword", state);
			jsonBody.put("versionNo", "4.5.1");
			
			JSONObject jsonHead = new JSONObject();
			if(StringUtils.isNotEmpty(prod)) {
				jsonHead.put("agent_id", prod);
			}else {
				jsonHead.put("agent_id", "null".equals(agentId) || agentId == null ? PhoneProdConstant.PHONE_MD : agentId);
			}
			jsonHead.put("channel", "ios");
			jsonHead.put("encryption_field", "");
			jsonHead.put("version_no", "4.5.1");
			
			JSONObject rejson = new JSONObject();
			rejson.put("body", jsonBody);
			rejson.put("header", jsonHead);
			
			String postForJson = HttpXmlClient.postForJson(loginMposPhone, rejson.toJSONString());
			JSONObject rpObject = JSONObject.parseObject(postForJson);
			if(rpObject.getString("status").equals("1")){
				log.info("手刷系统获取mid成功");
			}else if(rpObject.getString("status").equals("2") || rpObject.getString("status").equals("3")){ // 用户名或密码错误 || 用户名不存在
				log.info(rpObject.getString("message").toString());
				json.setSuccess(true);
				json.setMsg("用户名或密码错误");
				super.writeJson(json);
				return;
			}else if(rpObject.getString("status").equals("5")){	// 用户被锁定
				log.info(rpObject.getString("message").toString());
				json.setSuccess(true);
				json.setMsg(rpObject.getString("message").toString());
				super.writeJson(json);
				return;
			}else if(rpObject.getString("status").equals("6")){	// 用户被禁用
				log.info(rpObject.getString("message").toString());
				json.setSuccess(true);
				json.setMsg(rpObject.getString("message").toString());
				super.writeJson(json);
				return;
			} 
			loginName = rpObject.getString("mid").toString();
			if(StringUtils.isNotEmpty(prod) && StringUtils.isNotEmpty(loginName)){
				String bno = phoneWechatPublicAccService.getMerchantBnoByMid(loginName);
				if(!prod.equals(bno)) {
					json.setSuccess(true);
					json.setMsg("产品类型与登录信息不匹配!");
					super.writeJson(json);
					return;
				}
			}else{
                // 收银台公众号登录校验
				if(PhoneProdConstant.PHONE_SYT.equals(agentId) && StringUtils.isNotEmpty(loginName) && !loginName.contains("HRTSYT")){
					json.setSuccess(true);
					json.setMsg("该手机号未查询到收银台商户!");
					super.writeJson(json);
					return;
				}
			}
			user.setLoginName(loginName);
			UserBean userBean = phoneWechatPublicAccService.loginByMid(user);
			if (userBean != null) {
				// 校验openid
				String sysOpenid = null;
				if(StringUtils.isNotEmpty(prod)) {
					sysOpenid = phoneWechatPublicAccService.getWeBindOpenid(loginName, openid, prod);
				}else{
					if (agentId != null && !"null".equals(agentId)) {
						sysOpenid = phoneWechatPublicAccService.getWeBindOpenid(loginName, openid, agentId);
					} else {
						sysOpenid = phoneWechatPublicAccService.getSysOpenid(loginName, openid);
					}
				}
				if (!sysOpenid.equals(openid)) {
					json.setSuccess(true);
					json.setMsg("该商户已被其他微信账号绑定!");
					super.writeJson(json);
					return;
				}

				boolean flag = phoneWechatPublicAccService.findIfAuto();
				if (flag) {
					if (userBean.getIsLogin() == 0) {
						phoneWechatPublicAccService.updateIsLoginStatus(userBean.getLoginName(), 1);
						if (userBean.getUseLvl() == 3) {
							userBean.setResetFlag(1);
						}
						super.getRequest().getSession().setAttribute("user", userBean);
						if (("Login1").equals(LoginByUrl)) {
							json.setSuccess(false);
							json.setMsg("success");

						} else if (("Login2").equals(LoginByUrl)) {
							json.setSuccess(false);
							json.setMsg("success2");
						} else if (("bound").equals(LoginByUrl)) {
							boundSetSession(userBean.getUserName(),loginName,prod);
							json.setSuccess(false);
							json.setMsg("bound");
						} else {
							json.setSuccess(false);
							json.setMsg("success");
						}
						super.writeJson(json);
						return;

					} else {
						json.setSuccess(true);
						json.setMsg("用户已登录，不可重复登录！");
						super.writeJson(json);
						return;
					}
				} else {
					if (userBean.getUseLvl() == 3) {
						userBean.setResetFlag(1);
					}
					super.getRequest().getSession().setAttribute("user", userBean);

					if (("Login1").equals(LoginByUrl)) {
						json.setSuccess(false);
						json.setMsg("success");

					} else if (("Login2").equals(LoginByUrl)) {
						json.setSuccess(false);
						json.setMsg("success2");
					} else if (("bound").equals(LoginByUrl)) {
						boundSetSession(userBean.getUserName(),loginName,prod);
						json.setSuccess(false);
						json.setMsg("bound");
					} else {
						json.setSuccess(false);
						json.setMsg("success");
					}
					super.writeJson(json);
					return;
				}
			} else {
				json.setSuccess(true);
				json.setMsg("账号或密码错误！");
				super.writeJson(json);
				return;
			}
		} catch (Exception e) {
			log.error("用户登录异常：" + e);
			json.setSuccess(true);
			json.setMsg("用户登录异常！");
			super.writeJson(json);
			return;
		}
	}

	public void login() {
		JsonBean json = new JsonBean();
		String LoginByUrl = (String) super.getRequest().getSession().getAttribute("LoginByUrl");
		try {
			String loginName = super.getRequest().getParameter("loginName").trim();
			String state = super.getRequest().getParameter("password");
			String rand = super.getRequest().getParameter("rand");
			String random = super.getRequest().getParameter("random");
			String prod = super.getRequest().getParameter("prod");
			if(StringUtils.isEmpty(loginName)){
				json.setSuccess(true);
				json.setMsg("登录商户号不能为空!");
				super.writeJson(json);
				return;
			}
			if(StringUtils.isNotEmpty(prod)){
				String bno = phoneWechatPublicAccService.getMerchantBnoByMid(loginName);
				if(!prod.equals(bno)) {
					json.setSuccess(true);
					json.setMsg("产品类型与登录信息不匹配!");
					super.writeJson(json);
					return;
				}
			}else{
				// 收银台公众号登录校验
				if(!loginName.contains("HRTSYT")){
					json.setSuccess(true);
					json.setMsg("请输入收银台商户进行绑定!");
					super.writeJson(json);
					return;
				}
			}
			if(!rand.equalsIgnoreCase(random)){
				json.setSuccess(true);
				json.setMsg( "验证码错误！");
				super.writeJson(json);
				return;
			}
			
			String openid =null;
			Cookie cookies[]=super.getRequest().getCookies(); //读出用户硬盘上的Cookie，并将所有的Cookie放到一个cookie对象数组里面
			String agentId = null;
			int cookieflag = 0;
			for(int i=0;i<cookies.length;i++){    //用一个循环语句遍历刚才建立的Cookie对象数组 
				log.info("【cookie:"+cookies[i].getName()+","+cookies[i].getValue()+"】");
				if(cookies[i].getName().equals("HrtOpenid")){
					openid = cookies[i].getValue();
					cookieflag = 1;
				}else if(cookies[i].getName().equals("HrtAgentId")&&!"null".equals(cookies[i].getValue())
						&& null !=cookies[i].getValue()){
					agentId = cookies[i].getValue();
				}
			}
			if(cookieflag == 0){
				json.setSuccess(true);
				json.setMsg( "登录超时，请重新进入！");
				super.writeJson(json);
				return;
			}
			user.setOpenid(openid);
			user.setLoginName(loginName);
			user.setPassword(state);
		
			UserBean userBean = phoneWechatPublicAccService.login(user);
			if (userBean != null) {
				//校验openid
				String sysOpenid = null;
				if(StringUtils.isEmpty(prod)) {
					if (agentId != null && !"null".equals(agentId)) {
						sysOpenid = phoneWechatPublicAccService.getWeBindOpenid(loginName, openid, agentId);
					} else {
						sysOpenid = phoneWechatPublicAccService.getSysOpenid(loginName, openid);
					}
				}else{
					sysOpenid = phoneWechatPublicAccService.getWeBindOpenid(loginName, openid, prod);
				}
				if(!sysOpenid.equals(openid)){
					json.setSuccess(true);
					json.setMsg( "该商户已被其他微信账号绑定!");
					super.writeJson(json);
					return;
				}
				boolean flag = phoneWechatPublicAccService.findIfAuto();
				if (flag) {
					if (userBean.getIsLogin() == 0) {
						phoneWechatPublicAccService.updateIsLoginStatus(userBean.getLoginName(), 1);
							if (userBean.getUseLvl() == 3) {
								userBean.setResetFlag(1);
							}
							super.getRequest().getSession().setAttribute("user", userBean);
							if(("Login1").equals(LoginByUrl)){
								json.setSuccess(false);
								json.setMsg("success");
							}else if(("Login2").equals(LoginByUrl)){
								json.setSuccess(false);
								json.setMsg("success2");
							}else if(("bound").equals(LoginByUrl)){
//								super.getRequest().getSession().setAttribute("rname",userBean.getUserName());
//								super.getRequest().getSession().setAttribute("mid",loginName);
                                boundSetSession(userBean.getUserName(),loginName,prod);
								json.setSuccess(false);
								json.setMsg("bound");
							}else{
								json.setSuccess(false);
								json.setMsg("success");
							}
							super.writeJson(json);
							return;
						
					} else {
						json.setSuccess(true);
						json.setMsg("用户已登录，不可重复登录！");
						super.writeJson(json);
						return;
					}
				} else {
						if (userBean.getUseLvl() == 3) {
							userBean.setResetFlag(1);
						}
						super.getRequest().getSession().setAttribute("user",userBean);
						if(("Login1").equals(LoginByUrl)){
							json.setSuccess(false);
							json.setMsg("success");
							
						}else if(("Login2").equals(LoginByUrl)){
							json.setSuccess(false);
							json.setMsg("success2");
						}else if(("bound").equals(LoginByUrl)){
                            boundSetSession(userBean.getUserName(),loginName,prod);
							json.setSuccess(false);
							json.setMsg("bound");
						}else{
							json.setSuccess(false);
							json.setMsg("success");
						}
						super.writeJson(json);
						return;
				}
			} else {
				json.setSuccess(true);
				json.setMsg( "账号或密码错误！");
				super.writeJson(json);
				return;
				
			}
		} catch (Exception e) {
			log.error("用户登录异常：" + e);
			json.setSuccess(true);
			json.setMsg( "用户登录异常！");
			super.writeJson(json);
			return;
		}
		
	}

	/**
	 * 微信公众号登陆将登录成功的值设置到session中
	 * @param userName 商户名
	 * @param loginName 商户mid
	 * @param prod 产品id
	 */
	private void boundSetSession(String userName,String loginName,String prod){
		super.getRequest().getSession().setAttribute("rname",userName);
		super.getRequest().getSession().setAttribute("mid",loginName);
		super.getRequest().getSession().setAttribute("prod",prod);
		// 查询商户手机号
		String phoneValue = phoneWechatPublicAccService.getHybphoneByMid(loginName);
		Integer phoenLength = null==phoneValue?0:phoneValue.length();
		super.getRequest().getSession().setAttribute("phone",
				phoenLength==11?phoneValue.substring(0,3)+"****"+phoneValue.substring(phoenLength-4):"");
	}

	/**
	 * 解绑删除session中的信息
	 */
	private void boundDeleteSessionInfo(){
		super.getRequest().getSession().setAttribute("rname","");
		super.getRequest().getSession().setAttribute("mid","");
		super.getRequest().getSession().setAttribute("prod","");
		super.getRequest().getSession().setAttribute("phone","");
//        super.getRequest().getSession().setAttribute("LoginByUrl", "");
	}
	
	
	public String Login1() {
		String openid = null;
		String code = super.getRequest().getParameter("code");
		String isFirst = super.getRequest().getParameter("isFirst");
        String result = StringUtils.isNotEmpty(isFirst)?"WeChatPublicLogin":"error";
		if(code!=null&&!"".equals(code)){
			try {
				openid = phoneWechatPublicAccService.getOpenid(code,"getOpenidUrl");
			} catch (Exception e) {
				log.info("获取openid失败："+e);
				e.printStackTrace();
			}
			Cookie cookie=new Cookie("HrtOpenid", openid); 
			cookie.setMaxAge(30*60);   //存活期为30分钟 
			super.getResponse().addCookie(cookie);
			if(openid!=null){
				UserBean u=null;
				if(StringUtils.isNotEmpty(isFirst)){
					u = phoneWechatPublicAccService.queryMidUserByOpenidAndPord(openid,null);
				}else {
					u = phoneWechatPublicAccService.queryStatusByOpenid(openid);
				}
				if(u!=null){
					List<Map> list = phoneWechatPublicAccService.findProdByOpenIdAndMid(openid,u.getLoginName());
					super.getRequest().getSession().setAttribute("prod", list.size()>0?list.get(0).get("PROID"):"");
					super.getRequest().getSession().setAttribute("user", u);
					super.getRequest().getSession().setAttribute("openid", openid);
					if(StringUtils.isNotEmpty(isFirst)){
						result = "WeChatPublicTrade1";
					}else {
						result = "success";
					}
				}else{
                    super.getRequest().getSession().setAttribute("LoginByUrl", "Login1");
                }
			}
			log.info("微信公众号商户登录：code="+code+" openid="+openid+" result="+result);
		}
		return result;
	}
	/**
	 *  参数必传 agentId
	 * @return
	 */
	public String Login1Common() {
		super.getRequest().getSession().setAttribute("LoginByUrl", "Login1");
		String agentId = super.getRequest().getParameter("agentId");
		String result = "error";
		String openid = null;
		String appUrl = null;
		if(PhoneProdConstant.PHONE_MD.equals(agentId)){//会员宝收款器
			appUrl = "getOpenidUrl";
		}else if(PhoneProdConstant.PHONE_SYT.equals(agentId)){//会员宝收银台
			appUrl = "getSytOpenidUrl";
		}else {
			log.info("无效访问==》agentId:"+agentId);
			return result;
		}
		String code = super.getRequest().getParameter("code");
		if(code!=null&&!"".equals(code)){
			try {
				openid = phoneWechatPublicAccService.getOpenid(code,appUrl);
			} catch (Exception e) {
				log.info("获取openid失败："+e);
				e.printStackTrace();
			}
			Cookie cookie=new Cookie("HrtOpenid", openid); 
			cookie.setMaxAge(30*60);   //存活期为30分钟 
			super.getResponse().addCookie(cookie);
			if(openid!=null){
				UserBean u = phoneWechatPublicAccService.queryStatusByOpenidNew(openid);
				if(u!=null){
					super.getRequest().getSession().setAttribute("user",u);
					super.getRequest().getSession().setAttribute("openid", openid);
					result="success";
				}
			}
			log.info("微信公众号商户登录：code="+code+" openid="+openid+" result="+result);
		}
		return result;
	}
	
	public String hrtLogin1() {
		super.getRequest().getSession().setAttribute("LoginByUrl", "Login1");
		String result = "error2";
		String openid = null;
		String code = super.getRequest().getParameter("code");
		if(code!=null&&!"".equals(code)){
			try {
				openid = phoneWechatPublicAccService.getOpenid(code,"getHrtOpenidUrl");
			} catch (Exception e) {
				log.info("获取openid失败："+e);
				e.printStackTrace();
			}
			Cookie cookie=new Cookie("HrtOpenid", openid); 
			cookie.setMaxAge(30*60);   //存活期为30分钟 
			super.getResponse().addCookie(cookie);
			if(openid!=null){
				UserBean u = phoneWechatPublicAccService.queryStatusByOpenid(openid);
				if(u!=null){
					super.getRequest().getSession().setAttribute("user",u);
					super.getRequest().getSession().setAttribute("openid", openid);
					result="success";
				}
			}
			log.info("微信公众号商户登录：code="+code+" openid="+openid+" result="+result);
		}
		return result;
	}
		
	
	/*
	 * 20130交易对账明细报表
	 */
	public void listCheckUnitDealDetail() throws Exception{
		HttpServletRequest request =super.getRequest();
		request.setCharacterEncoding("UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
		String line = "";
		StringBuffer buf = new StringBuffer();
		while((line=br.readLine())!=null){
			buf.append(line);
		}
		com.alibaba.fastjson.JSONObject reqJson = JSONObject.parseObject(buf.toString());
		Integer  page = (Integer) reqJson.get("page");
		String  rowsPerPage = (String) reqJson.get("rowsPerPage");
	
		Object userSession = super.getRequest().getSession().getAttribute("user");
		String mid = super.getRequest().getParameter("mid");
		String txnDay = super.getRequest().getParameter("txnDay");
		String txnDay1 = super.getRequest().getParameter("txnDay1");
		String prod = super.getRequest().getParameter("prod");
		String openid = super.getRequest().getParameter("openid");
        UserBean userBean = phoneWechatPublicAccService.queryMidUserByOpenidAndPord(openid,prod);
		JsonBean jsonBean = new JsonBean();
		try {
			if(StringUtils.isNotEmpty(prod)){
				jsonBean = phoneCheckUnitDealDataService.findHistertoy(userBean, txnDay, txnDay1, page, Integer.parseInt(rowsPerPage), null);
			}else{
				jsonBean = phoneCheckUnitDealDataService.findHistertoy(userSession, txnDay, txnDay1, page, Integer.parseInt(rowsPerPage), null);
			}
		} catch (Exception e) {
			log.error("交易查询异常：" + e);
		}
		super.writeJson(jsonBean);
	  }
	
	/**
	 * 参数必传agentId
	 * @return
	 */
	public String Login2Common() {
		super.getRequest().getSession().setAttribute("LoginByUrl", "Login2");
		String agentId = super.getRequest().getParameter("agentId");
		String appUrl = null;
		String result = "error";
		String openid = null;
		String code = super.getRequest().getParameter("code");
		if(PhoneProdConstant.PHONE_MD.equals(agentId)){//会员宝收款器
			appUrl = "getOpenidUrl";
		}else if(PhoneProdConstant.PHONE_SYT.equals(agentId)){//会员宝收银台
			appUrl = "getSytOpenidUrl";
		}else {
			log.info("无效访问===》agentId:"+agentId);
			return result;
		}
		if(code!=null&&!"".equals(code)){
			try {
				openid = phoneWechatPublicAccService.getOpenid(code,appUrl);
			} catch (Exception e) {
				log.info("获取openid失败："+e);
				e.printStackTrace();
			}
			if(openid!=null){
				UserBean u = phoneWechatPublicAccService.queryStatusByOpenidNew(openid);
				Cookie cookie=new Cookie("HrtOpenid", openid); 
				cookie.setMaxAge(10*60);   //存活期为10分钟 
				super.getResponse().addCookie(cookie);
				if(u!=null){
					super.getRequest().getSession().setAttribute("user",u);
					super.getRequest().getSession().setAttribute("openid", openid);
					result="success2";
				}
			}
			log.info("微信公众号商户登录：code="+code+" openid="+openid+" result="+result);
		}
		return result;
	}
	
	public String Login2() {
		String openid = null;
		String code = super.getRequest().getParameter("code");
		String isFirst = super.getRequest().getParameter("isFirst");
        String result = StringUtils.isNotEmpty(isFirst)?"WeChatPublicLogin":"error";
		if(code!=null&&!"".equals(code)){
			try {
				openid = phoneWechatPublicAccService.getOpenid(code,"getOpenidUrl");
			} catch (Exception e) {
				log.info("获取openid失败："+e);
				e.printStackTrace();
			}
			if(openid!=null){
				UserBean u = null;
				if(StringUtils.isNotEmpty(isFirst)){
					u = phoneWechatPublicAccService.queryMidUserByOpenidAndPord(openid,null);
				}else {
					u = phoneWechatPublicAccService.queryStatusByOpenid(openid);
				}
				Cookie cookie=new Cookie("HrtOpenid", openid);
				cookie.setMaxAge(10*60);   //存活期为10分钟 
				super.getResponse().addCookie(cookie);
				if(u!=null){
					List<Map> list = phoneWechatPublicAccService.findProdByOpenIdAndMid(openid,u.getLoginName());
					super.getRequest().getSession().setAttribute("prod", list.size()>0?list.get(0).get("PROID"):"");
					super.getRequest().getSession().setAttribute("user",u);
					super.getRequest().getSession().setAttribute("openid", openid);
					if(StringUtils.isNotEmpty(isFirst)){
						result = "WeChatPublicTrade2";
					}else {
						result = "success2";
					}
				}else{
                    super.getRequest().getSession().setAttribute("LoginByUrl", "Login2");
                }
			}
			log.info("微信公众号商户登录：code="+code+" openid="+openid+" result="+result);
		}
		return result;
	}	
	
	public String hrtLogin2() {
		super.getRequest().getSession().setAttribute("LoginByUrl", "Login2");
		String result = "error2";
		String openid = null;
		String code = super.getRequest().getParameter("code");
		if(code!=null&&!"".equals(code)){
			try {
				openid = phoneWechatPublicAccService.getOpenid(code,"getHrtOpenidUrl");
			} catch (Exception e) {
				log.info("获取openid失败："+e);
				e.printStackTrace();
			}
			if(openid!=null){
				UserBean u = phoneWechatPublicAccService.queryStatusByOpenid(openid);
				Cookie cookie=new Cookie("HrtOpenid", openid); 
				cookie.setMaxAge(10*60);   //存活期为10分钟 
				super.getResponse().addCookie(cookie);
				if(u!=null){
					super.getRequest().getSession().setAttribute("user",u);
					super.getRequest().getSession().setAttribute("openid", openid);
					
					result="success2";
				}
			}
			log.info("微信公众号商户登录：code="+code+" openid="+openid+" result="+result);
		}
		return result;
	}	
	
	/*
	 * 20130结算明细报表
	 */
	public void querySettlementDetail() throws Exception{
		HttpServletRequest request =super.getRequest();
		request.setCharacterEncoding("UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
		String line = "";
		StringBuffer buf = new StringBuffer();
		while((line=br.readLine())!=null){
			buf.append(line);
		}
		com.alibaba.fastjson.JSONObject reqJson = JSONObject.parseObject(buf.toString());
		Integer  page = (Integer) reqJson.get("page");
		String  rowsPerPage = (String) reqJson.get("rowsPerPage");
		Object userSession = super.getRequest().getSession().getAttribute("user");
		String mid = super.getRequest().getParameter("mid");
		String txnDay = super.getRequest().getParameter("txnDay");
		String txnDay1 = super.getRequest().getParameter("txnDay1");
		DataGridBean dgb = new DataGridBean();
		try {
			CheckIncomeBean checkIncomeBean=new CheckIncomeBean();
			checkIncomeBean.setPage(page);
			checkIncomeBean.setRows(Integer.valueOf(rowsPerPage));
			checkIncomeBean.setMid(mid);
			checkIncomeBean.setDateone(txnDay);
			checkIncomeBean.setDatetwo(txnDay1);
			dgb = checkincomeService.CheckIncomequeryLists(checkIncomeBean,(UserBean)userSession);
		} catch (Exception e) {
			log.error("结算查询异常：" + e);
		}
		super.writeJson(dgb);
	  }
	
	//解绑按钮判断跳转
	public String Landing(){
		String code = super.getRequest().getParameter("code");
		String prod = super.getRequest().getParameter("prod");
		String isFirst = super.getRequest().getParameter("isFirst");
		String openid =null;
		if(code!=null&&!"".equals(code)){
			try {
				openid = phoneWechatPublicAccService.getOpenid(code,"getOpenidUrl");
			} catch (Exception e) {
				log.info("获取openid失败："+e);
				e.printStackTrace();
			}
			Cookie cookie1=new Cookie("HrtOpenid", openid);
			cookie1.setMaxAge(30*60);   //存活期为10分钟 
			Cookie cookie2 = new Cookie("HrtAgentId", "null");
			cookie2.setMaxAge(30*60);
			super.getResponse().addCookie(cookie1);
			super.getResponse().addCookie(cookie2);
		}else{
			super.addFieldError("errorInfo", "登录失败！");
			if(StringUtils.isNotEmpty(prod) || StringUtils.isNotEmpty(isFirst)){
			    return "WeChatPublicLogin";
            }else {
                return "error";
            }
		}
		List list=null;
		try {
            if(StringUtils.isNotEmpty(prod) || StringUtils.isNotEmpty(isFirst)){
                list = phoneWechatPublicAccService.findOpenidBindByOpenIdAndProd(openid,prod);
            }else {
                list = phoneWechatPublicAccService.findOpenid(openid);
            }
		} catch (Exception e) {
			log.info("微信公众号跳转异常:"+e.getMessage());
		}
		if(list.size()==0){
		    Object loginByUrl = super.getRequest().getSession().getAttribute("LoginByUrl");
		    if(null==loginByUrl) {
                super.getRequest().getSession().setAttribute("LoginByUrl", "bound");
            }
            if(StringUtils.isNotEmpty(prod) || StringUtils.isNotEmpty(isFirst)){
                return "WeChatPublicLogin";
            }else {
                return "error";
            }
		}else{
            if(StringUtils.isNotEmpty(prod) || StringUtils.isNotEmpty(isFirst)) {
                boundSetSession(((Map<String, Object>) list.get(0)).get("USER_NAME") + "",
                        ((Map<String, Object>) list.get(0)).get("LOGIN_NAME") + "",
                        ((Map<String, Object>) list.get(0)).get("PROID") + "");
                super.getRequest().getSession().setAttribute("openid",openid);
                return "WeChatPublicBound";
            }else {
                super.getRequest().getSession().setAttribute("rname", ((Map<String, Object>) list.get(0)).get("USER_NAME"));
                super.getRequest().getSession().setAttribute("mid", ((Map<String, Object>) list.get(0)).get("LOGIN_NAME"));
                super.getRequest().getSession().setAttribute("openid", "null");
                return "bound";
            }
		}
	}
	
	/**
	 * 该接口参数必须带agenId
	 * @return
	 */
	public String LandingCommon(){
		String code = super.getRequest().getParameter("code");
		String agentId = super.getRequest().getParameter("agentId");
		String openid =null;
		String appUrl = null;
		if(PhoneProdConstant.PHONE_MD.equals(agentId)){//会员宝收款器
			appUrl = "getOpenidUrl";
		}else if(PhoneProdConstant.PHONE_SYT.equals(agentId)){//会员宝收银台
			appUrl = "getSytOpenidUrl";
		}else{
			super.addFieldError("errorInfo", "登录失败！");
			return "error";
		}
		if(code!=null&&!"".equals(code)){
			try {
				openid = phoneWechatPublicAccService.getOpenid(code,appUrl);
			} catch (Exception e) {
				log.info("获取openid失败："+e);
				super.addFieldError("errorInfo", "登录失败！");
				return "error";
			}
			Cookie cookie1=new Cookie("HrtOpenid", openid);
			cookie1.setMaxAge(30*60);   //存活期为10分钟 
			Cookie cookie2 = new Cookie("HrtAgentId", agentId);
			cookie2.setMaxAge(30*60);
			super.getResponse().addCookie(cookie1);
			super.getResponse().addCookie(cookie2);
		}else{
			super.addFieldError("errorInfo", "登录失败！");
			return "error";
		}
		List list=null;
		try {
			list = phoneWechatPublicAccService.findOpenidNew(openid);
		} catch (Exception e) {
			super.addFieldError("errorInfo", "登录失败！");
			return "error";
		}
		if(list.size()==0){
			super.getRequest().getSession().setAttribute("LoginByUrl", "bound");
			return "error";
		}else{
			super.getRequest().getSession().setAttribute("rname",((Map<String,Object>) list.get(0)).get("USER_NAME") );
			super.getRequest().getSession().setAttribute("mid",((Map<String,Object>) list.get(0)).get("LOGIN_NAME") );
			super.getRequest().getSession().setAttribute("openid",((Map<String,Object>) list.get(0)).get("OPENID") );
			return "bound";
		}
	}
	
	//Hrtwechatservice按钮判断跳转
	public String Landing2(){
		String code = super.getRequest().getParameter("code");
		String openid =null;
		if(code!=null&&!"".equals(code)){
			try {
				openid = phoneWechatPublicAccService.getOpenid(code,"getHrtOpenidUrl");
			} catch (Exception e) {
				log.info("获取openid失败："+e);
				e.printStackTrace();
			}
			Cookie cookie1=new Cookie("HrtOpenid", openid);
			cookie1.setMaxAge(30*60);   //存活期为10分钟 
			Cookie cookie2 = new Cookie("HrtAgentId", "null");
			cookie2.setMaxAge(30*60);
			super.getResponse().addCookie(cookie1);
			super.getResponse().addCookie(cookie2);
		}else{
			super.addFieldError("errorInfo", "登录失败！");
			return "error2";
		}
		List list=null;
		try {
			list = phoneWechatPublicAccService.findOpenid(openid);
		} catch (Exception e) {
			log.error("Landing2() 异常:"+e.getMessage());
		}
		if(list.size()==0){
			super.getRequest().getSession().setAttribute("LoginByUrl", "bound");
			return "error2";
		}else{
			super.getRequest().getSession().setAttribute("rname",((Map<String,Object>) list.get(0)).get("USER_NAME") );
			super.getRequest().getSession().setAttribute("mid",((Map<String,Object>) list.get(0)).get("LOGIN_NAME") );
			return "bound2";
		}
	}
	
	//解绑
	public String Unbundling(){
		String mid = super.getRequest().getParameter("mid");
		String openid = super.getRequest().getParameter("openid");
		String prod = super.getRequest().getParameter("prod");
		int count = 1;
		if(StringUtils.isEmpty(prod)) {
            if ("null".equals(openid) || null == openid) {
                count = phoneWechatPublicAccService.Unbundling(mid);
            } else {
			    count = phoneWechatPublicAccService.UnbundlingNew(openid);
            }
        }else{
            count = phoneWechatPublicAccService.UnbundlingNewByOpenidAndMid(openid, mid, prod);
        }
		if(count==1 && StringUtils.isEmpty(prod)){
			super.getRequest().getSession().setAttribute("LoginByUrl", "bound");
			return "error";
		}else if(count==1 && StringUtils.isNotEmpty(prod)){
            super.getRequest().getSession().setAttribute("LoginByUrl", "bound");
			boundDeleteSessionInfo();
            return "WeChatPublicLogin";
        }
		super.getRequest().getSession().setAttribute("rname","");
		super.getRequest().getSession().setAttribute("mid","");
		return StringUtils.isNotEmpty(prod)?"WeChatPublicBound":"bound";
	}
	
	//hrtWeichatservice解绑
	public String Unbundling2(){
		String mid = super.getRequest().getParameter("mid");
		int count = phoneWechatPublicAccService.Unbundling(mid);
		if(count==1){
			super.getRequest().getSession().setAttribute("LoginByUrl", "bound");
			return "error2";
		}
		super.getRequest().getSession().setAttribute("rname","");
		super.getRequest().getSession().setAttribute("mid","");
		return "bound2";
	}
	
	//银联绑卡入口
	public void OpenCard()throws Exception{
		HttpServletRequest req = getRequest();
		HttpServletResponse resp = getResponse();
		resp.setContentType("text/html; charset="+ DemoBase.encoding);
		
		Map<String, String> map = new HashMap<String, String>();
		String txnTime = req.getParameter("txnTime");//时间
		String merId = req.getParameter("merId");//商户号
		String orderId = req.getParameter("orderId");//订单号
		String certifId = req.getParameter("certifId");//证件号
		String customerNm = req.getParameter("customerNm");//姓名
		String phoneNo = req.getParameter("phoneNo");//手机号
		String accNo1 = req.getParameter("accNo");//卡号
		String sign = req.getParameter("sign");//卡号
		String paymentnumber = req.getParameter("paymentnumber");
		map.put("txnTime", txnTime);
		map.put("merId", merId);
		map.put("orderId", orderId);
		map.put("certifId", certifId);
		map.put("customerNm", customerNm);
		map.put("phoneNo", phoneNo);
		map.put("accNo", accNo1);
		map.put("sign", sign);
		map.put("paymentnumber", paymentnumber);
		try{
			 map = phoneWechatPublicAccService.sendBangKa("/AdmApp/repayment/repayment_OpenCard.action",map);
		}catch (Exception e) {
			log.error("银联绑卡入口异常", e);
		}
		JsonBean jsonBean = new JsonBean();
		jsonBean.setSuccess(true);
		jsonBean.setObj(map);
		super.writeJson(jsonBean);
	}
	
	//OpenCardResponse 银联绑卡回调
	public void OpenCardResponse()throws ServletException, IOException{
		HttpServletRequest req = getRequest();
		HttpServletResponse resp = getResponse();
		log.info("FrontRcvResponse后台接收银联绑卡回调报文返回开始");
		String pageResult = "";
		pageResult = "/utf8_result.jsp";
		Map<String, String> respParam = getAllRequestParam(req);
		// 打印请求报文
		log.info(respParam);
		try{
			respParam = phoneWechatPublicAccService.sendBangKa("/AdmApp/repayment/repayment_OpenCardResponse.action",respParam);
		}catch (Exception e) {
			log.error("银联绑卡回调异常", e);
		}	
		req.setAttribute("result", null);
		req.getRequestDispatcher(pageResult).forward(req, resp);
	}
	
	//OpenCardResponse 银联绑卡前台回调
	public void OpenCardFrontResponse()throws ServletException, IOException{
		HttpServletRequest req = getRequest();
		HttpServletResponse resp = getResponse();
		log.info("FrontRcvResponse前台接收银联绑卡回调报文返回开始");
		try{
			String pageResult = "";
			pageResult = "/utf8_result.jsp";
			req.setAttribute("result", null);
			req.getRequestDispatcher(pageResult).forward(req, resp);
		}catch (Exception e) {
			log.error("银联绑卡回调异常", e);
		}	
	}
	public void retuenMainApp()throws ServletException, IOException{
		
	}
	
	/**
	 * 修改绑卡状态
	 * @param paramsMap
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public boolean updateCardStatus(Map<String, String>paramsMap){
		try {
			mertidCardService.updateMertiedCard(paramsMap);
			return true;
		} catch (Exception e) {
			log.info("修改绑卡状态失败"+e);
			return false;
		}
	}
	/**
	 * 商户删除卡
	 */
	public void deleteCard() {
		JsonBean jsonBean = new JsonBean();
		HttpServletRequest req = getRequest();
		String accNo = req.getParameter("qpcid");
		String mid =  req.getParameter("merId");
		String sign =  req.getParameter("sign");
		Map<String, String> map = new HashMap<String, String>();
		map.put("qpcid", accNo);
		map.put("merId", mid);
		map.put("sign", sign);
		try{
			map = phoneWechatPublicAccService.sendBangKa("/AdmApp/repayment/repayment_deleteCard.action",map);
			jsonBean.setSuccess(true);
			jsonBean.setObj(map);
		}catch (Exception e) {
			log.error("银联商户删除卡异常", e);
			jsonBean.setSuccess(false);
		}	
		super.writeJson(jsonBean);
	}
	
	/**
	 * 将绑卡信息添加到数据库parent_paymentline 
	 * @param paramsMap 用户帮卡信息
	 * @throws Exception 
	 */
	public boolean addCard(Map<String, String>paramsMap){
		try {
			mertidCardService.saveCard(paramsMap);
			return true;
		} catch (Exception e) {
			log.info("绑卡失败："+e);
			return false;
		}
	}
	
	public void getCardByid(){
		HttpServletRequest request = getRequest();
		JsonBean jsonBean = new JsonBean();
		String mid = request.getParameter("mid");
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		String sign = request.getParameter("sign");
		if (mid == null ||"".equals(mid)) {
			jsonBean.setMsg("商户号不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("mid", mid);
		map.put("page", page);
		map.put("size", size);
		map.put("sign", sign);
		try{
			map = phoneWechatPublicAccService.sendBangKa("/AdmApp/repayment/repayment_getCardByid.action",map);
			jsonBean.setMsg("请求成功");
			jsonBean.setSuccess(true);
			jsonBean.setObj(map);
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("查询失败!");
			log.info("查询用户卡列表失败"+e);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 添加代还
	 */
	public void addRePayment(){
		HttpServletRequest request = getRequest();
		JsonBean jsonBean = new JsonBean();
		String qpcid = request.getParameter("qpcid");//卡表标识
		String repayday = request.getParameter("repayday");//账单日
		String repaymonth = request.getParameter("repaymonth");//代还日
		String repayamt = request.getParameter("repayamt");//代还金额
		String cardbalance = request.getParameter("cardbalance");//卡额度
		String bfee = request.getParameter("bfee");//手续费
		String deviiceid = request.getParameter("deviiceid");//设备id
		String position = request.getParameter("position");//经纬度
		if (qpcid == null ||"".equals(qpcid)) {
			jsonBean.setMsg("qpcid不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("qpcid", qpcid);
		map.put("repayday", repayday);
		map.put("repaymonth", repaymonth);
		map.put("repayamt", repayamt);
		map.put("cardbalance", cardbalance);
		map.put("bfee", bfee);
		map.put("deviiceid", deviiceid);
		map.put("position", position);
		try{
			jsonBean.setSuccess(false);
			jsonBean.setMsg("该功能系统维护中");
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("添加代还失败!");
			log.info("添加代还失败"+e);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 查询代还明细
	 */
	public void queryRePayment(){
		HttpServletRequest request = getRequest();
		JsonBean jsonBean = new JsonBean();
		String qpcid = request.getParameter("qpcid");//卡表标识
		String type = request.getParameter("type");//1确认，2详情
		if (qpcid == null ||"".equals(qpcid)) {
			jsonBean.setMsg("qpcid不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("qpcid", qpcid);
		map.put("type", type);
		try{
			map = phoneWechatPublicAccService.sendBangKa("/AdmApp/repayment/repayment_queryRePayment.action",map);
			jsonBean.setMsg("请求成功");
			jsonBean.setSuccess(true);
			jsonBean.setObj(map);
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("查询代还明细失败!");
			log.info("查询代还明细失败"+e);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 查看代还计划
	 */
	public void getRepayPlanDetail(){
		HttpServletRequest request = getRequest();
		JsonBean jsonBean = new JsonBean();
		String qpcid = request.getParameter("qpcid");//卡表标识
		String mid = request.getParameter("mid");
		String sign = request.getParameter("sign");
		String orderid = request.getParameter("orderid");
		if (mid == null ||"".equals(mid)) {
			jsonBean.setMsg("mid不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("qpcid", qpcid);
		map.put("mid", mid);
		map.put("sign", sign);
		map.put("orderid", orderid);
		try{
			map = phoneWechatPublicAccService.sendBangKa("/AdmApp/repayment/repayment_getRepayPlanDetail.action",map);
			jsonBean.setMsg("请求成功");
			jsonBean.setSuccess(true);
			jsonBean.setObj(map);
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("查看代还计划失败!");
			log.info("查看代还计划失败"+e);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 查询费率
	 */
	public void getRepaymentInfo(){
		HttpServletRequest request = getRequest();
		JsonBean jsonBean = new JsonBean();
		String mid = request.getParameter("mid");
		String sign = request.getParameter("sign");
		if (mid == null ||"".equals(mid)) {
			jsonBean.setMsg("mid不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("mid", mid);
		map.put("sign", sign);
		try{
			map = phoneWechatPublicAccService.sendBangKa("/AdmApp/repayment/repayment_getRepaymentInfo.action",map);
			jsonBean.setMsg("请求成功");
			jsonBean.setSuccess(true);
			jsonBean.setObj(map);
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("查询费率失败!");
			log.info("查询费率失败"+e);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 终止代还
	 */
	public void endRePayment(){
		HttpServletRequest request = getRequest();
		JsonBean jsonBean = new JsonBean();
		String rmid = request.getParameter("rmid");
		if (rmid == null ||"".equals(rmid)) {
			jsonBean.setMsg("mid不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("rmid", rmid);
		try{
			map = phoneWechatPublicAccService.sendBangKa("/AdmApp/repayment/repayment_endRePayment.action",map);
			jsonBean.setMsg("请求成功");
			jsonBean.setSuccess(true);
			jsonBean.setObj(map);
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("终止代还失败!");
			log.info("终止代还失败"+e);
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 接受代还计划
	 */
	public void receiveRepay(){
		HttpServletRequest request = getRequest();
		JsonBean jsonBean = new JsonBean();
		String mid = request.getParameter("mid");
		String qpcid = request.getParameter("qpcid");
		String sign = request.getParameter("sign");
		if (mid == null ||"".equals(mid)) {
			jsonBean.setMsg("mid不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("qpcid", qpcid);
		map.put("mid", mid);
		map.put("sign", sign);
		try{
			map = phoneWechatPublicAccService.sendBangKa("/AdmApp/repayment/repayment_receiveRepay.action",map);
			jsonBean.setMsg("请求成功");
			jsonBean.setSuccess(true);
			jsonBean.setObj(map);
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("接受代还计划失败!");
			log.info("接受代还计划失败"+e);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 还款记录详情页
	 */
	public void getRepayCardListDetail(){
		HttpServletRequest request = getRequest();
		JsonBean jsonBean = new JsonBean();
		String mid = request.getParameter("mid");
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		String qpcid = request.getParameter("qpcid");
		String sign = request.getParameter("sign");
		if (mid == null ||"".equals(mid)) {
			jsonBean.setMsg("mid不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("mid", mid);
		map.put("page", page);
		map.put("size", size);
		map.put("sign", sign);
		map.put("qpcid", qpcid);
		try{
			map = phoneWechatPublicAccService.sendBangKa("/AdmApp/repayment/repayment_getRepayCardListDetail.action",map);
			jsonBean.setMsg("请求成功");
			jsonBean.setSuccess(true);
			jsonBean.setObj(map);
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("还款记录详情页失败!");
			log.info("还款记录详情页失败"+e);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 还款记录列表
	 */
	public void getRepayCardList(){
		HttpServletRequest request = getRequest();
		JsonBean jsonBean = new JsonBean();
		String mid = request.getParameter("mid");
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		String sign = request.getParameter("sign");
		if (mid == null ||"".equals(mid)) {
			jsonBean.setMsg("mid不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("mid", mid);
		map.put("page", page);
		map.put("size", size);
		map.put("sign", sign);
		try{
			map = phoneWechatPublicAccService.sendBangKa("/AdmApp/repayment/repayment_getRepayCardList.action",map);
			jsonBean.setMsg("请求成功");
			jsonBean.setSuccess(true);
			jsonBean.setObj(map);
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("还款记录列表失败!");
			log.info("还款记录列表失败"+e);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 商户根据卡号查询该卡是否开通成功
	 * 4+1原则：证件号、姓名、卡号、手机号  + mid
	 */
	public void ifDredgeSucess() {
		HttpServletRequest req = super.getRequest();
		JsonBean jsonBean = new JsonBean();
		String mid = req.getParameter("mid");
		String idnum = req.getParameter("certifId");//证件号
		String customerNm = req.getParameter("customerNm");//姓名
		String phoneNo = req.getParameter("phoneNo");//手机号
		String accNo = req.getParameter("accNo"); //卡号
		String sign = req.getParameter("sign");
		Map<String, String> map = new HashMap<String, String>();
		map.put("mid", mid);
		map.put("certifId", idnum);
		map.put("customerNm", customerNm);
		map.put("phoneNo", phoneNo);
		map.put("accNo", accNo);
		map.put("sign", sign);
		try{
			map = phoneWechatPublicAccService.sendBangKa("/AdmApp/repayment/repayment_ifDredgeSucess.action",map);
			jsonBean.setMsg("请求成功");
			jsonBean.setSuccess(true);
			jsonBean.setObj(map);
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("查询失败!");
			log.info("商户根据卡号查询该卡是否开通 失败"+e);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 获取请求参数中所有的信息
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(
			final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (res.get(en) == null || "".equals(res.get(en))) {
					// System.out.println("======为空的字段名===="+en);
					res.remove(en);
				}
			}
		}
		return res;
	}
	
	/**
	 * 商户支付密码校验(设置或修改)
	 */
	public void setOrChangeMerPwd() {
		JsonBean jsonBean = new JsonBean();
		HttpServletRequest request = super.getRequest();
		String pwd = request.getParameter("pwd");
		String mid = request.getParameter("mid");
		String type = request.getParameter("type");
		String sign = request.getParameter("sign");
		try{
			Map<String, String> map = new HashMap<String, String>();
			map.put("pwd", pwd);
			map.put("mid", mid);
			map.put("type", type);
			map.put("sign", sign);
			String url = admAppIp+"/AdmApp/merchacc/merchant_addMerPayPwd.action";
			String response = HttpXmlClient.post(url, map);
			JSONObject jsonObject = JSON.parseObject(response);
			jsonBean.setMsg(jsonObject.getString("msg"));
			jsonBean.setSuccess(jsonObject.getBoolean("status"));
			super.writeJson(jsonBean);
		}catch (Exception e) {
			log.error("商户支付密码校验异常", e);
		}	
	}
	/**
	 * 查询用户是否设置过密码
	 */
	public void ifMerSetPwd() {
		JsonBean jsonBean = new JsonBean();
		HttpServletRequest request = super.getRequest();
		String mid = request.getParameter("mid");
		try{
			Map<String, String> map = new HashMap<String, String>();
			map.put("mid", mid);
			String url = admAppIp+"/AdmApp/merchacc/merchant_queryMerPayPwd.action";
			String response = HttpXmlClient.post(url, map);
			JSONObject jsonObject = JSON.parseObject(response);
			jsonBean.setMsg(jsonObject.getString("msg"));
			jsonBean.setSuccess(jsonObject.getBoolean("status"));
			super.writeJson(jsonBean);
		}catch (Exception e) {
			log.error("查询用户是否设置过密码异常", e);
		}
	}
	
	public void cupsLogin() {
		// param ：mid pwd
		ServletRequest request = super.getRequest();
		Map<String, String> returnMap = new HashMap<String, String>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("mid", request.getParameter("mid"));
		map.put("pwd", request.getParameter("pwd"));
		//防重攻击
		boolean b = phoneWechatPublicAccService.addReplayAttack(request.getParameter("uuid"), request.getParameter("timeStamp"));
		if(!b) {
			returnMap.put("msg", "数据有误");
			returnMap.put("status", "E");
		}else {
			String mid = map.get("mid");
			try {
				String pwd = map.get("pwd");
				String decryptMid = HrtRSAUtil.decryptWithBase64(pwd);
				
				if (!mid.equals(decryptMid)) {
					log.info("校验签名失败:计算签名=" + decryptMid + ",上传签名={}" + mid);
					throw new Exception("登陆失败");
				}
				HttpServletRequest hrequest = (HttpServletRequest) request;
				HttpSession session = hrequest.getSession(true);
				String uuid = UUID.randomUUID().toString();
				session.setAttribute("APPID", uuid);
				session.setMaxInactiveInterval(500);//秒
				returnMap.put("sessionId", session.getId() + uuid);
				returnMap.put("msg", "登陆成功");
				returnMap.put("status", "S");
				log.info("[银联绑卡]mid"+mid+"登陆成功");
			} catch (Exception e) {
				log.error("[银联绑卡]mid"+mid+"登陆成功");
				returnMap.put("msg", "登陆失败");
				returnMap.put("status", "E");
			}
		}
		JsonBeanForSign json = new JsonBeanForSign();
		String data = JSON.toJSONStringWithDateFormat(returnMap, "yyyy-MM-dd HH:mm:ss");
		String signs1 = SHAEncUtil.getSHA256Str(data);
		json.setSign(signs1);
		json.setData(data);
		super.writeJson(json);
	}
	
	public void cupsLogout() {
		// param ：mid pwd
		ServletRequest request = super.getRequest();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		boolean b = phoneWechatPublicAccService.addReplayAttack(super.getRequest().getParameter("uuid"), super.getRequest().getParameter("timeStamp"));
		if(!b) {
			returnMap.put("msg", "数据有误");
			returnMap.put("status", "E");
		}else {
			Map<String, String> map = new HashMap<String, String>();
			map.put("mid", request.getParameter("mid"));
			map.put("pwd", request.getParameter("pwd"));
			String mid = map.get("mid");
			try {
				String pwd = map.get("pwd");
				String decryptMid = HrtRSAUtil.decryptWithBase64(pwd);
				
				if (!mid.equals(decryptMid)) {
					log.info("校验签名失败:计算签名=" + decryptMid + ",上传签名={}" + mid);
					throw new Exception("登出失败");
				}
				HttpServletRequest hrequest = (HttpServletRequest) request;
				HttpSession session = hrequest.getSession(true);
				session.removeAttribute("APPID");
				returnMap.put("msg", "登出成功");
				returnMap.put("status", "S");
				log.info("[银联绑卡]mid"+mid+"退出成功");
			} catch (Exception e) {
				log.error("[银联绑卡]mid"+mid+"退出失败");
				returnMap.put("sessionMsg", "登出失败");
				returnMap.put("status", "E");
			}
		}
		JsonBeanForSign json = new JsonBeanForSign();
		String data = JSON.toJSONStringWithDateFormat(returnMap, "yyyy-MM-dd HH:mm:ss");
		String signs1 = SHAEncUtil.getSHA256Str(data);
		json.setSign(signs1);
		json.setData(data);
		super.writeJson(json);
	}
	
	/*
	 * 无卡交易
	 */
	public void queryMySettlement() throws Exception{
		HttpServletRequest request =super.getRequest();
		request.setCharacterEncoding("UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
		String line = "";
		StringBuffer buf = new StringBuffer();
		while((line=br.readLine())!=null){
			buf.append(line);
		}
		com.alibaba.fastjson.JSONObject reqJson = JSONObject.parseObject(buf.toString());
		Integer  page = (Integer) reqJson.get("page");
		String  rowsPerPage = (String) reqJson.get("rowsPerPage");
		Object userSession = super.getRequest().getSession().getAttribute("user");
		String txnDay = super.getRequest().getParameter("txnDay");
		String txnDay1 = super.getRequest().getParameter("txnDay1");
		String prod = super.getRequest().getParameter("prod");
		String openid = super.getRequest().getParameter("openid");
		JsonBean json=new JsonBean();
		UserBean userBean = phoneWechatPublicAccService.queryMidUserByOpenidAndPord(openid,prod);
		DataGridBean dgb = new DataGridBean();
		try {
			if(StringUtils.isEmpty(prod)) {
				json = phoneCheckUnitDealDataService.findHistertoyScanWeChat(userSession, txnDay, txnDay1, page, Integer.valueOf(rowsPerPage), null, "2");
			}else {
				json = phoneCheckUnitDealDataService.findHistertoyScanWeChat(userBean, txnDay, txnDay1, page, Integer.valueOf(rowsPerPage), null, "2");
			}
			dgb = (DataGridBean) json.getObj();
		} catch (Exception e) {
			log.error("结算查询异常：" + e);
		}
		super.writeJson(dgb);
	  }
	
	/**
	 * 查询代金券
	 */
	public void myDevicePolicyList(){
		HttpServletRequest request = getRequest();
		JsonBean jsonBean = new JsonBean();
		String mid = request.getParameter("mid");
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		if (mid == null ||"".equals(mid)) {
			jsonBean.setMsg("mid不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("mid", mid);
		map.put("page", page);
		map.put("size", size);
		try{
			map = phoneWechatPublicAccService.myDevicePolicyList("/AdmApp/devicePolicy/devicePolicy_myDevicePolicyList.action",map);
			jsonBean.setMsg("请求成功");
			jsonBean.setSuccess(true);
			jsonBean.setObj(map);
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("查询代金券失败!");
			log.info("查询代金券失败"+e);
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 代金券折扣
	 */
	public void devicePolicyRecordList(){
		HttpServletRequest request = getRequest();
		JsonBean jsonBean = new JsonBean();
		String hdpid = request.getParameter("hdpid");
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		if (hdpid == null ||"".equals(hdpid)) {
			jsonBean.setMsg("hdpid不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("hdpid", hdpid);
		map.put("page", page);
		map.put("size", size);
		try{
			map = phoneWechatPublicAccService.devicePolicyRecordList("/AdmApp/devicePolicy/devicePolicy_devicePolicyRecordList.action",map);
			jsonBean.setMsg("请求成功");
			jsonBean.setSuccess(true);
			jsonBean.setObj(map);
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("查询代金券折扣失败!");
			log.info("查询代金券折扣失败"+e);
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 还款提示
	 */
	public void getCardIfLoan(){
		HttpServletRequest request = getRequest();
		JsonBean jsonBean = new JsonBean();
		String mid = request.getParameter("mid");
		String sign = request.getParameter("sign");
		if (mid == null ||"".equals(mid)) {
			jsonBean.setMsg("mid不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("mid", mid);
		map.put("sign", sign);
		try{
			map = phoneWechatPublicAccService.getCardIfLoan("/AdmApp/repayment/repayment_getCardIfLoan.action",map);
			jsonBean.setMsg("请求成功");
			jsonBean.setSuccess(true);
			jsonBean.setObj(map);
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("查询还款提示失败!");
			log.info("查询还款提示失败"+e);
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 还款通知
	 */
	public void pushRepaymentLoan(){
		HttpServletRequest request = getRequest();
		JsonBean jsonBean = new JsonBean();
		String orderid = request.getParameter("orderid");
		String sign = request.getParameter("sign");
		if (orderid == null ||"".equals(orderid)) {
			jsonBean.setMsg("orderid不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderid", orderid);
		map.put("sign", sign);
		try{
			map = phoneWechatPublicAccService.pushRepaymentLoan("/AdmApp/repayment/repayment_PushRepaymentLoan.action",map);
			jsonBean.setMsg("还款通知成功");
			jsonBean.setSuccess(true);
			jsonBean.setObj(map);
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("还款通知失败!");
			log.info("还款通知失败"+e);
		}
		super.writeJson(jsonBean);
	}

	//常见问题反馈页面跳转
	public String jumpProblemFeedbackPage(){

		HttpServletRequest req =super.getRequest();
//		'data':'{"orderid":"orderid1","mid":"mid1","trantype":"trantype1","amount":"amount33","trantime":"trantime11","problem":"不想支付 ","other":"6"}'
		String orderid = req.getParameter("orderid");
		String mid = req.getParameter("mid");
		String trantype = req.getParameter("trantype");
		String amount = req.getParameter("amount");
		String trantime = req.getParameter("trantime");
		log.info("常见问题反馈页面跳转传入参数:orderid="+orderid+",mid="+mid+",trantype="+trantype+",amount="+amount+",trantime="+trantime);
		super.getRequest().getSession().setAttribute("orderid",orderid);
		super.getRequest().getSession().setAttribute("mid",mid);
		super.getRequest().getSession().setAttribute("trantype",trantype);
		super.getRequest().getSession().setAttribute("amount",amount);
		super.getRequest().getSession().setAttribute("trantime",trantime);
		return "promoteinfo";
	}

	/**
	 * 常见问题反馈
	 */
	public void problemFeedback(){
		JsonBean jsonBean = new JsonBean();
		HttpServletRequest req =super.getRequest();

		Map<String, String> map = new HashMap<String, String>();
//		{"orderid":"orderid1","mid":"mid1","trantype":"trantype1","amount":"amount33","trantime":"trantime11","problem":"不想支付 ","other":"6"}
		JSONObject jsonObject = JSONObject.parseObject(req.getParameter("data"));
		log.info("问题反馈请求参数:"+jsonObject.toJSONString());
		String orderid = jsonObject.getString("orderid");//订单号
		String mid = jsonObject.getString("mid");//商户号
		String trantype = jsonObject.getString("trantype");//交易类型
		String amount = jsonObject.getString("amount");//交易金额
		String trantime = jsonObject.getString("trantime");//交易时间
		String problem = jsonObject.getString("problem");//反馈信息
		String other = jsonObject.getString("other");//其它

		ProblemFeedbackModel problemFeedbackModel = new ProblemFeedbackModel();
		problemFeedbackModel.setOrderId(orderid);
		problemFeedbackModel.setMid(mid);
		problemFeedbackModel.setTrantype(trantype);
		problemFeedbackModel.setAmount(amount);
		problemFeedbackModel.setTrantime(trantime);
		if(StringUtils.isNotEmpty(problem)) {
            problemFeedbackModel.setMsg(Integer.parseInt(problem));
        }
		problemFeedbackModel.setRemark(other);
		problemFeedbackModel.setCreateTime(new Date());
		try {
            log.info("问题反馈请求参数:"+JSONObject.toJSONString(problemFeedbackModel));
			jsonBean.setSuccess(true);
            List<ProblemFeedbackModel> exists = problemFeedbackService.getProblemFeedbackByOrderId(problemFeedbackModel.getOrderId());
            if(exists.size()>0){
                jsonBean.setObj("1");
                jsonBean.setMsg("该笔交易已存在反馈信息");
            }else{
                Integer count = (Integer) problemFeedbackService.saveProblemFeedback(problemFeedbackModel);
                log.info("问题反馈返回参数(主键id)::"+count);
                jsonBean.setObj("1");
                jsonBean.setMsg("反馈成功");
            }
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setObj("0");
			jsonBean.setMsg("反馈失败");
			log.info("反馈失败"+e);
		}
		super.writeJson(jsonBean);
	}
}
