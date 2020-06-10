package com.hrt.frame.entity.pagebean;

import com.hrt.biz.bill.entity.pagebean.SecurityInfoBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 类功能：用户管理
 * 创建人：wwy
 * 创建日期：2012-12-04
 */
public class UserBean extends SecurityInfoBean implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	//当前页数
	private int page;
	
	//总记录数
	private int rows;
	
	//排序字段
	private String sort;
	
	//排序规则 ASC DESC
	private String order;

	private String flag;
	
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
	
	//创建日期 最小值
	private String createDateStart;
	
	//创建日期 最大值
	private String createDateEnd;
	
	//修改日期 最小值
	private String updateDateStart;
	
	//修改日期 最大值
	private String updateDateEnd;
	
	//角色ID
	private String roleIds;
	
	//角色名称
	private String roleNames;
	
	//机构编号
	private String unNo;
	
	//机构名称
	private String unitName;
	
	//新密码
	private String newPassword;
	
	//密码重置标识
	private Integer resetFlag; 
	
	//状态 0-停用  1-启用
	private Integer status;
	
	//1手刷；0传统
	private String isM35Type;
	
	//状态名称
	private String statusName;
	
	private String rand;
	
	//用户级别
	private Integer useLvl;
	
	private Integer unitLvl;
	
	private Integer unitStatus;
	
	private Integer isLogin;
	
	private Integer busid;
	
	private String macTry;//MAC系统登录密码
	//日志审计
	private String login_type;
	private Date logintime;


	/** 活动 **/
	private String rebateType;

	public String getRebateType() {
		return rebateType;
	}

	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}
	
	public Integer getUnitStatus() {
		return unitStatus;
	}

	public void setUnitStatus(Integer unitStatus) {
		this.unitStatus = unitStatus;
	}

	public String getMacTry() {
		return macTry;
	}

	public void setMacTry(String macTry) {
		this.macTry = macTry;
	}

	public String getLogin_type() {
		return login_type;
	}

	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}

	public Date getLogintime() {
		return logintime;
	}

	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}

	public String getIsM35Type() {
		return isM35Type;
	}

	public void setIsM35Type(String isM35Type) {
		this.isM35Type = isM35Type;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getBusid() {
		return busid;
	}

	public void setBusid(Integer busid) {
		this.busid = busid;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(String createDateStart) {
		this.createDateStart = createDateStart;
	}

	public String getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public String getUpdateDateStart() {
		return updateDateStart;
	}

	public void setUpdateDateStart(String updateDateStart) {
		this.updateDateStart = updateDateStart;
	}

	public String getUpdateDateEnd() {
		return updateDateEnd;
	}

	public void setUpdateDateEnd(String updateDateEnd) {
		this.updateDateEnd = updateDateEnd;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getUnNo() {
		return unNo;
	}

	public void setUnNo(String unNo) {
		this.unNo = unNo;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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

	public String getRand() {
		return rand;
	}

	public void setRand(String rand) {
		this.rand = rand;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getUseLvl() {
		return useLvl;
	}

	public void setUseLvl(Integer useLvl) {
		this.useLvl = useLvl;
	}

	public Integer getUnitLvl() {
		return unitLvl;
	}

	public void setUnitLvl(Integer unitLvl) {
		this.unitLvl = unitLvl;
	}

	public void setIsLogin(Integer isLogin) {
		this.isLogin = isLogin;
	}

	public Integer getIsLogin() {
		return isLogin;
	}
	
}
