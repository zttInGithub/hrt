package com.hrt.frame.base.action;

import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 类功能：Action父类
 * 创建人：wwy
 * 创建日期：2012-11-29
 */
public class BaseAction extends ActionSupport implements RequestAware, 
	SessionAware, ApplicationAware, ServletRequestAware, ServletResponseAware {
	
	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(BaseAction.class);
	
	private Map<String, Object> requestMap;
	
	private Map<String, Object> sessionMap;
	
	private Map<String, Object> applicationMap;
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	public Map<String, Object> getRequestMap() {
		return requestMap;
	}

	public Map<String, Object> getSessionMap() {
		return sessionMap;
	}

	public Map<String, Object> getApplicationMap() {
		return applicationMap;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
	
	/**
	 * 方法功能：将对象转换成JSON字符串，并响应回前台
	 * 参数：object
	 * 返回值：void
	 * 异常：IOException
	 */
	public void writeJson(Object object) {
		PrintWriter out = null;
		try {
			// TODO FastJson的BUG 第一个驼峰前只有一个字母的属性无法序列化(类似aUser属性会丢失)
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.write(json);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("响应json输出异常：" + e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@Override
	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

	@Override
	public void setApplication(Map<String, Object> applicationMap) {
		this.applicationMap = applicationMap;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

}
