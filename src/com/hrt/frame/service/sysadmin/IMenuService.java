package com.hrt.frame.service.sysadmin;

import java.util.List;
import java.util.Map;

import com.hrt.frame.entity.pagebean.MenuBean;
import com.hrt.frame.entity.pagebean.TreeNodeBean;
import com.hrt.frame.entity.pagebean.UserBean;

/**
 * 类功能：菜单管理Service
 * 创建人：wwy
 * 创建日期：2013-01-10
 */
public interface IMenuService {
	
	/**
	 * 方法功能：查询系统所有菜单树
	 * 参数：menuBean 
	 * 返回值：List<TreeNodeBean>
	 * 异常：
	 */
	List<TreeNodeBean> queryAllMenuTree(MenuBean menuBean) throws Exception;
	
	/**
	 * 方法功能：查询用户下的菜单树
	 * 参数：menuBean
	 *     userBean
	 * 返回值：
	 * 异常：
	 */
	List<TreeNodeBean> queryMenuTreeByUser(MenuBean menuBean, UserBean userBean) throws Exception;
	
	/**
	 * 方法功能：查询菜单表格树
	 * 参数：menuBean
	 * 返回值：List<MenuBean>
	 * 异常：
	 */
	List<MenuBean> queryMenusTreegrid(MenuBean menuBean) throws Exception;
	List<MenuBean> queryMenusTreegridbaodan(MenuBean menuBean) throws Exception;
	
	/**
	 * 方法功能：添加菜单
	 * 参数：menuBean
	 *     loginName 当前登录用户名
	 * 返回值：
	 * 异常：
	 */
	void saveMenu(MenuBean menuBean, String loginName) throws Exception;
	
	/**
	 * 方法功能：修改菜单
	 * 参数：menuBean
	 *     loginName 当前登录用户名
	 * 返回值：
	 * 异常：
	 */
	void updateMenu(MenuBean menuBean, String loginName) throws Exception;
	
	/**
	 * 方法功能：删除菜单
	 * 参数：menuBean
	 * 返回值：true 删除成功 false 菜单已关联，不允许删除
	 * 异常：
	 */
	boolean deleteMenu(MenuBean menuBean) throws Exception;
	
	/**
	 * 查询菜单代码
	 */
	List<MenuBean> searchMenuTranCode() throws Exception;
	
	/**
	 * 启用菜单
	 */
	void updateStartMenu(MenuBean menuBean) throws Exception;
	
	/**
	 * 停用菜单
	 */
	void updateCloseMenu(MenuBean menuBean) throws Exception;

	List<Map<String, Object>> queryShowNode(UserBean userBean);

}
