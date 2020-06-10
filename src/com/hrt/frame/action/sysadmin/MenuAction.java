package com.hrt.frame.action.sysadmin;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.MenuBean;
import com.hrt.frame.entity.pagebean.TreeNodeBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.service.sysadmin.IMenuService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 菜单管理Action
 */
public class MenuAction extends BaseAction implements ModelDriven<MenuBean>{

	private static final long serialVersionUID = 1L;
	
	private Log log = LogFactory.getLog(MenuAction.class);
	
	private MenuBean menu = new MenuBean();
	
	private IMenuService menuService;
	
	@Override
	public MenuBean getModel() {
		return menu;
	}

	public IMenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}
	
	/**
	 * 查询菜单树
	 */
	public void listAllMenuTree() {
		List<TreeNodeBean> nodeList = null;
		try {
			nodeList = menuService.queryAllMenuTree(menu);
		} catch (Exception e) {
			log.error("查询菜单树异常：" + e);
		}
		
		super.writeJson(nodeList);
	}
	
	/**
	 * 查询用户下的菜单树
	 */
	public void listMenuTreeByUser() {
		Object userMenus = super.getRequest().getSession().getAttribute("userMenus");
		List<TreeNodeBean> nodeList = new ArrayList<TreeNodeBean>();
		//session 失效返回登录页面
		if (userMenus != null) {
			try {
				nodeList = (List<TreeNodeBean>)userMenus;
			} catch (Exception e) {
				log.error("查询用户下的菜单树异常：" + e);
			}
		}
		
		super.writeJson(nodeList);
 	}
	
	/**
	 * 查询菜单表格树
	 */
	public void listMenusTreegrid() {
		List<MenuBean> menuList = null;
		try {
			menuList = menuService.queryMenusTreegrid(menu);
		} catch (Exception e) {
			log.error("查询菜单表格树异常：" + e);
		}
		
		super.writeJson(menuList);
	}
	
	/**
	 * 查询菜单表格树
	 */
	public void listMenusTreegridbaodan() {
		List<MenuBean> menuList = null;
		try {
			UserBean userSession = (UserBean)super.getRequest().getSession().getAttribute("user");
			if(userSession.getLoginName().startsWith("baodan0")){
				menuList = menuService.queryMenusTreegridbaodan(menu);
			}
		} catch (Exception e) {
			log.error("查询菜单表格树异常：" + e);
		}
		
		super.writeJson(menuList);
	}
	
	/**
	 * 添加菜单
	 */
	public void addMenu() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				menuService.saveMenu(menu, ((UserBean)userSession).getLoginName());
				json.setSuccess(true);
				json.setMsg("添加菜单成功");
			} catch (Exception e) {
				log.error("添加菜单异常：" + e);
				json.setMsg(e.getMessage());
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 修改菜单
	 */
	public void editMenu() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				menuService.updateMenu(menu, ((UserBean)userSession).getLoginName());
				json.setSuccess(true);
				json.setMsg("修改菜单成功");
			} catch (Exception e) {
				log.error("修改菜单异常：" + e);
				json.setMsg(e.getMessage());
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 删除菜单
	 */
	public void deleteMenu() {
		JsonBean json = new JsonBean();
		try {
			boolean result = menuService.deleteMenu(menu);
			if (result) {
				json.setSuccess(true);
				json.setMsg("删除菜单成功");
			} /*else {
				json.setMsg("此菜单已使用，不可以删除!");
			}*/
		} catch (Exception e) {
			log.error("删除菜单异常：" + e);
			json.setMsg(e.getMessage());
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 查询菜单代码
	 */
	public void searchMenuTranCode(){
		List<MenuBean> menuList = null;
		try {
			menuList = menuService.searchMenuTranCode();
		} catch (Exception e) {
			log.error("查询菜单表格树异常：" + e);
		}
		
		super.writeJson(menuList);
	}
	
	/**
	 * 启用菜单
	 */
	public void startMenu(){
		JsonBean json = new JsonBean();
		try {
			menuService.updateStartMenu(menu);
			json.setSuccess(true);
			json.setMsg("启用菜单成功");
		} catch (Exception e) {
			log.error("启用菜单异常：" + e);
			json.setMsg(e.getMessage());
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 停用菜单
	 */
	public void closeMenu(){
		JsonBean json = new JsonBean();
		try {
			menuService.updateCloseMenu(menu);
			json.setSuccess(true);
			json.setMsg("停用菜单成功");
		} catch (Exception e) {
			log.error("停用菜单异常：" + e);
			json.setMsg(e.getMessage());
		}
		
		super.writeJson(json);
	}

}
