package com.hrt.frame.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 权限检查过滤器
 * 
 * @author lichao
 * @date 2014-4-1
 * @version 1.0
 */
@SuppressWarnings("serial")
public class UrlFilter extends HttpServlet implements Filter {

	private Log log = LogFactory.getLog(UrlFilter.class);
	private final String LOGIN_PATH = "/HrtApp/";//登陆页
	private final String LOGIN_REDIRECT = "user_login.action";//登陆跳转
	private final String LOGIN_RAND = "rand.action";//验证码
	private final String LOGIN_POP="popup.html";	//广告页

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse hresponse = (HttpServletResponse) response;
		HttpServletRequest hrequest = (HttpServletRequest) request;
		HttpSession session = hrequest.getSession(true);
		Object user = session.getAttribute("user");//
		String url = hrequest.getRequestURI();
		if (user == null) {
			if (!isTrue(url)) {
				log.info("非法路径请求url-----》"+url);
				hresponse.getWriter().write("<script>window.top.location='Login.jsp'</script>");
			//	hresponse.sendRedirect(LOGIN_PATH);
				return;
			}
		}
		// 已通过验证，用户访问继续
		filterChain.doFilter(request, response);
		return;
	}

	public void init(FilterConfig arg0) throws ServletException {

	}
	
	public Boolean isTrue(String url){
		//过滤空值
		if(url == null){
			return false;
		}
		if("".equals(url)){
			return false;
		}
		//放开商户帮卡页面
		if (url.endsWith("token_openCard_front.jsp")) {
			return true;
		}
		//放开接收黑名单
		if (url.endsWith("addPushMsgList.action")) {
			return true;
		}
		//添加推送信息 风险信息
		if (url.endsWith("addPushBlackList.action")) {
			return true;
		}
		//商户信息比对协查推送
		if (url.endsWith("verifyPushMsgList.action")) {
			return true;
		}
		//放开action
		if(url.endsWith("loginNameUnit.action")){
			return true;
		}
		//放开action
		if(url.endsWith("creInfo_queryCreInfo.action")){
			return true;
		}
		//放开action
		if(url.toLowerCase().indexOf("/service")>0){
			return true;
		}
		//放开验证码
		if (url.endsWith(LOGIN_RAND)) {
			return true;
		}
		//放开登陆跳转
		if(url.endsWith(LOGIN_REDIRECT)){
			return true;
		}
		//放开广告页面
		if(url.endsWith(LOGIN_POP)){
			return true;
		}
		//放开登陆页面
		if(url.endsWith("Login.jsp")){
			return true;
		}
		//放开微信公众号登陆页面
		if(url.endsWith("WeChatLogin.html")){
			return true;
		}
		//放开微信公众号查询明细页面
		if(url.endsWith("WeChatCheckDetail.html")){
			return true;
		}
		if(url.endsWith("promoteinfo.html")){
			return true;
		}
		//放开微信公众号查询明细页面
		if(url.endsWith("WeiXinLoginSuccess.jsp")){
			return true;
		}
		//放开微信公众号查询明细页面1
		if(url.endsWith("WeiXinLoginSuccess1.jsp")){
			return true;
		}
		//放开微信公众号查询明细页面12
		if(url.endsWith("WeiXinLoginSuccess2.jsp")){
			return true;
		}
		
		//过滤jsp
		if(url.toLowerCase().indexOf(".jsp")>0){
			return false;
		}
		//过滤html
		if(url.toLowerCase().indexOf(".html")>0){
			return false;
		}
		//过滤html
		if(url.toLowerCase().indexOf(".action")>0){
			return false;
		}
		return true;
	}

}
