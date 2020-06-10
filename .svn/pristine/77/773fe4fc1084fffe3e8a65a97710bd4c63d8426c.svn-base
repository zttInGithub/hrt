package com.hrt.frame.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hrt.frame.action.sysadmin.UserAction;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.service.sysadmin.IUserService;

/**
 * @author xxx
 * 用户单点登录监听
 */

public class OnlineUserListener implements HttpSessionListener {

	private static final Log log = LogFactory.getLog(OnlineUserListener.class);

	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		//log.info("*************************用户登录");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		UserBean user=(UserBean)session.getAttribute("user");
		if(user!=null){
			ApplicationContext application=WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
			IUserService userService=(IUserService)application.getBean("userService");
			userService.updateIsLoginStatus(user.getLoginName(),0);
		}
		log.info("session过期，请重新登录！");
	}

}
