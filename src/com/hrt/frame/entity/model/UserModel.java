package com.hrt.frame.entity.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 类功能：SYS_USER实体类
 * 创建人：wwy
 * 创建日期：2012-12-4
 */
public class UserModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//用户ID
	private Integer userID;
	
	//账号
	private String loginName;
	
	//密码
	private String password;
	
	private String openid;
	
	private String sysFlag;
	
	//姓名
	private String userName;
	
	//创建日期
	private Date createDate;
	
	//创建者
	private String createUser;
	
	//修改日期
	private Date updateDate;
	
	//修改者
	private String updateUser;
	
	//用户对应的角色
	private Set<RoleModel> roles;
	
	//用户对应的机构
	private Set<UnitModel> units;
	
	//密码重置标识
	private Integer resetFlag; 
	
	//状态 0-停用  1-启用
	private Integer status;
	
	private Integer useLvl;
	
	private Integer isLogin;
	
	private String pwdModDate;

	/** 活动 **/
	private String rebateType;

	public String getRebateType() {
		return rebateType;
	}

	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}

	public String getPwdModDate() {
		return pwdModDate;
	}

	public void setPwdModDate(String pwdModDate) {
		this.pwdModDate = pwdModDate;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Set<UnitModel> getUnits() {
		return units;
	}

	public void setUnits(Set<UnitModel> units) {
		this.units = units;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Set<RoleModel> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleModel> roles) {
		this.roles = roles;
	}

	public Integer getResetFlag() {
		return resetFlag;
	}

	public void setResetFlag(Integer resetFlag) {
		this.resetFlag = resetFlag;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUseLvl() {
		return useLvl;
	}

	public void setUseLvl(Integer useLvl) {
		this.useLvl = useLvl;
	}

	public Integer getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Integer isLogin) {
		this.isLogin = isLogin;
	}

}
