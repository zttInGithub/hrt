package com.argorse.java.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hrt.frame.service.sysadmin.IUserService;

public class UserTest {
	@Test
	public void testUser() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		IUserService service = ctx.getBean("userService", IUserService.class);
		service.deleteUser("u1,u2");
	}
}
