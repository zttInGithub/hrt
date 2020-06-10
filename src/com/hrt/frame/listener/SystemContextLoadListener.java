package com.hrt.frame.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.alibaba.fastjson.JSONObject;
import com.hrt.frame.service.sysadmin.IEnumService;
import com.hrt.frame.utility.ConfigureCache;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.Map;

/**
 * 类功能：自定义Listener
 * 创建人：wwy
 * 创建日期：2012-12-18
 */
public class SystemContextLoadListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext servletContext = arg0.getServletContext();
		servletContext.setAttribute("ctx", servletContext.getContextPath());
		servletContext.setAttribute("title", ConfigureCache.getTitle());
		servletContext.setAttribute("titleEnglish", ConfigureCache.getTitleEnglish());

	}

}
