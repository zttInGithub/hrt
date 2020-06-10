package com.hrt.frame.service.sysadmin;

import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.RoleBean;
import com.hrt.frame.entity.pagebean.UserBean;

/**
 * 类功能：角色管理Service
 * 创建人：wwy
 * 创建日期：2012-12-18
 */
public interface IRoleService {
	
	/**
	 * 方法功能：分页查询角色
	 * 参数：role 
	 * 返回值：DataGridBean实例
	 */
	DataGridBean queryRoles(RoleBean role) throws Exception;
	
	/**
	 * 方法功能：查询所有角色
	 * 参数：role
	 * 返回值：DataGridBean实例
	 */
	DataGridBean queryRolesCombogrid(RoleBean role, UserBean user) throws Exception;
	
	/**
	 * 方法功能：添加角色
	 * 参数：role
	 * 	   loginName 当前登录用户名
	 * 返回值：void
	 */
	void saveRole(RoleBean role, String loginName) throws Exception;
	
	/**
	 * 方法功能：修改角色
	 * 参数：role
	 * 	   loginName 当前登录用户名
	 * 返回值：void
	 */
	void updateRole(RoleBean role, String loginName) throws Exception;
	
	/**
	 * 方法功能：删除角色
	 * 参数：id TB_ROLE表主键
	 * 返回值：true 删除成功  false 角色已关联，不允许删除
	 */
	boolean deleteRole(Integer id) throws Exception;
	
	/**
	 * 启用角色
	 */
	void updateStartRole(RoleBean roleBean) throws Exception;
	
	/**
	 * 停用角色
	 */
	void updateCloseRole(RoleBean roleBean) throws Exception;
	
}
