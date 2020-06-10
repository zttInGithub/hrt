package com.hrt.frame.entity.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 类功能：SYS_ROLE实体类
 * 创建人：wwy
 * 创建日期：2012-12-18
 */
public class RoleModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//角色ID
	private Integer roleID;
	
	//传统角色ID
	private Integer roleID2;
	
	//角色名称
	private String roleName;
	
	//角色描述
	private String description;
	
	//角色对应的菜单
	private Set<MenuModel> menus;
	
	//角色对应的用户
	private Set<MenuModel> users;
	
	//创建日期
	private Date createDate;
	
	//创建者
	private String createUser;
	
	//修改日期
	private Date updateDate;	
	
	//修改者
	private String updateUser;
	
	//级别
	private Integer roleLevel;
	
	private Integer roleAttr;
	
	private Integer status;
	

	public Integer getRoleID2() {
		return roleID2;
	}

	public void setRoleID2(Integer roleID2) {
		this.roleID2 = roleID2;
	}

	public Integer getRoleID() {
		return roleID;
	}

	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}
 
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<MenuModel> getMenus() {
		return menus;
	}

	public void setMenus(Set<MenuModel> menus) {
		this.menus = menus;
	}

	public Set<MenuModel> getUsers() {
		return users;
	}

	public void setUsers(Set<MenuModel> users) {
		this.users = users;
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

	public Integer getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel = roleLevel;
	}

	public Integer getRoleAttr() {
		return roleAttr;
	}

	public void setRoleAttr(Integer roleAttr) {
		this.roleAttr = roleAttr;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
