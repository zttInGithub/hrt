package com.hrt.frame.entity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 类功能：SYS_LOGIN_LOG实体类
 */
public class UserLoginLogModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer slid; //主键
	private String login_type; //L、登陆  Q、退出
	private Integer user_ID;  //sys_user表中user_ID
	private String login_name; //登陆用户名
	private String pwd; //登陆密码
	private String login_message; //登陆时返回的消息
	private Integer status; //状态  1、登陆成功 2、登陆失败
	private Date logintime; //日期

	
	public Integer getSlid() {
		return slid;
	}
	public void setSlid(Integer slid) {
		this.slid = slid;
	}
	public String getLogin_type() {
		return login_type;
	}
	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
	public Integer getUser_ID() {
		return user_ID;
	}
	public void setUser_ID(Integer user_ID) {
		this.user_ID = user_ID;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getLogin_message() {
		return login_message;
	}
	public void setLogin_message(String login_message) {
		this.login_message = login_message;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getLogintime() {
		return logintime;
	}
	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}
	
}
