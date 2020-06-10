package com.hrt.frame.action.sysadmin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.RoleBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.service.sysadmin.IRoleService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 角色管理Action
 */
public class RoleAction extends BaseAction implements ModelDriven<RoleBean>{

	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(RoleAction.class);
	
	private RoleBean role = new RoleBean();
	
	private IRoleService roleService;
	
	private String ids;
	
	@Override
	public RoleBean getModel() {
		return role;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	/**
	 * 分页查询角色信息
	 */
	public void listRoles() {
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = roleService.queryRoles(role);
		} catch (Exception e) {
			log.error("分页查询角色信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	
	/**
	 * 分页查询角色信息
	 */
	public void listRoles002301() {
		DataGridBean dgb = new DataGridBean();
		try {
			UserBean userSession = (UserBean)super.getRequest().getSession().getAttribute("user");
			role.setCreateUser(userSession.getLoginName());
			dgb = roleService.queryRoles(role);
		} catch (Exception e) {
			log.error("分页查询角色信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	
	/**
	 * 查询所有角色信息
	 */
	public void listRoleCombogrid() {
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = roleService.queryRolesCombogrid(role,((UserBean)userSession));
		} catch (Exception e) {
			log.error("查询所有角色信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	
	/**
	 * 新增角色
	 */
	public void addRole() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				roleService.saveRole(role, ((UserBean)userSession).getLoginName());
				json.setSuccess(true);
				json.setMsg("添加角色成功");
			} catch (Exception e) {
				log.error("新增角色异常：" + e);
				json.setMsg("添加角色失败");
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 修改角色
	 */
	public void editRole() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				roleService.updateRole(role, ((UserBean)userSession).getLoginName());
				json.setSuccess(true);
				json.setMsg("修改角色成功");
			} catch (Exception e) {
				log.error("修改角色异常" + e);
				json.setMsg("修改角色失败");
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 删除角色
	 */
	public void deleteRole() {
		JsonBean json = new JsonBean();
		try {
			boolean res = roleService.deleteRole(Integer.parseInt(ids));
			if (res) {
				json.setSuccess(true);
				json.setMsg("删除角色成功");
			} else {
				json.setMsg("角色已使用，不可以删除");
			}
		} catch (Exception e) {
			log.error("删除角色异常：" + e);
			json.setMsg("删除角色失败");
		}
		super.writeJson(json);
	}

	/**
	 * 启用角色
	 */
	public void startRole(){
		JsonBean json = new JsonBean();
		try {
			roleService.updateStartRole(role);
			json.setSuccess(true);
			json.setMsg("启用角色成功");
		} catch (Exception e) {
			log.error("启用角色异常：" + e);
			json.setMsg(e.getMessage());
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 停用角色
	 */
	public void closeRole(){
		JsonBean json = new JsonBean();
		try {
			roleService.updateCloseRole(role);
			json.setSuccess(true);
			json.setMsg("停用角色成功");
		} catch (Exception e) {
			log.error("停用角色异常：" + e);
			json.setMsg(e.getMessage());
		}
		
		super.writeJson(json);
	}

}
