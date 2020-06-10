package com.argorse.java.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hrt.frame.entity.pagebean.MenuBean;
import com.hrt.frame.service.sysadmin.IMenuService;

public class RoleTest {
	@Test
	public void testRole() throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		IMenuService menuService = ctx.getBean("menuService", IMenuService.class);
		MenuBean menuBean = new MenuBean();
		menuBean.setId(11);
		menuService.queryMenusTreegrid(menuBean);
	}
}
