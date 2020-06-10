package com.hrt.frame.service.sysadmin.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeanUtils;

import com.hrt.frame.dao.sysadmin.IMenuDao;
import com.hrt.frame.dao.sysadmin.IRoleDao;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.MenuModel;
import com.hrt.frame.entity.model.RoleModel;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.RoleBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.service.sysadmin.IRoleService;

/**
 * 角色管理Service
 */
public class RoleServiceImpl implements IRoleService {
	
	private IRoleDao roleDao;
	
	private IMenuDao menuDao;
	
	private IUnitDao unitDao;
	
	public IRoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	public IMenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public IUnitDao getUnitDao() {
		return unitDao;
	}

	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}

	/**
	 * 方法功能：格式化RoleModel为datagrid数据格式
	 * 参数：roleList
	 *     total 总记录数
	 * 返回值：
	 * 异常：
	 */
	private DataGridBean formatToDataGrid(List<RoleModel> roleList, long total) {
		List<RoleBean> roleBeanList = new ArrayList<RoleBean>();
		for(RoleModel roleModel : roleList) {
			RoleBean roleBean = new RoleBean();
			BeanUtils.copyProperties(roleModel, roleBean);
			
			if(roleModel.getStatus() == 1){
				roleBean.setStatusName("启用");
			}else{
				roleBean.setStatusName("停用");
			}
			
			//获取该角色的权限
			Set<MenuModel> menuSet = roleModel.getMenus();
			if (menuSet != null && !menuSet.isEmpty()) {
				String menuIds = "";
				String menuTexts = "";
				for (MenuModel menu : menuSet) {
					menuIds += menu.getMenuID() + ",";
					menuTexts += menu.getText() + ",";
				}
				roleBean.setMenuIds(menuIds.substring(0, menuIds.lastIndexOf(",")));
				roleBean.setMenuTexts(menuTexts.substring(0, menuTexts.lastIndexOf(",")));
			}
			roleBeanList.add(roleBean);
		}
		
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(roleBeanList);
		
		return dgb;
	}
	
	/**
	 * 方法功能：格式化RoleModel为combogrid数据格式
	 * 参数：roleList
	 * 	   total 总记录数
	 * 返回值：
	 * 异常：
	 */
	private DataGridBean formatToCombogrid(List<RoleModel> roleList, long total) {
		List<RoleBean> roleBeanList = new ArrayList<RoleBean>();
		for(RoleModel roleModel : roleList) {
			RoleBean roleBean = new RoleBean();
			BeanUtils.copyProperties(roleModel, roleBean);
			
			roleBeanList.add(roleBean);
		}
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(roleBeanList);
		
		return dgb;
	}
	
	/**
	 * 方法功能：分页查询角色
	 * 参数：role 
	 * 返回值：DataGridBean实例
	 */
	@Override
	public DataGridBean queryRoles(RoleBean role) throws Exception{
		String hql = "from RoleModel where 1 = 1";
		String hqlCount = "select count(*) from RoleModel where 1 = 1";
		if(role.getCreateUser()!=null){
			hql += " and createUser like '"+role.getCreateUser()+"%' ";
			hqlCount += " and createUser like '"+role.getCreateUser()+"%' ";
		}
		if (role.getSort() != null) {
			hql += " order by " + role.getSort() + " " + role.getOrder();
		}
		long counts = roleDao.queryCounts(hqlCount, null);
		List<RoleModel> roleList = roleDao.queryRoles(hql, null, role.getPage(), role.getRows());
		
		DataGridBean dataGridBean = formatToDataGrid(roleList, counts);
		
		return dataGridBean;
	}
	
	/**
	 * 方法功能：查询所有角色
	 * 参数：role
	 * 返回值：DataGridBean实例
	 */
	@Override
	public DataGridBean queryRolesCombogrid(RoleBean role, UserBean user) throws Exception{
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		String sql = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if("110000".equals(user.getUnNo())){
			sql = "SELECT * FROM SYS_ROLE WHERE STATUS = 1 and ROLE_LEVEL IN(0,1) ";//and ROLE_ATTR =1
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
				sql = "SELECT * FROM SYS_ROLE WHERE STATUS = 1 and ROLE_LEVEL IN(0,1) and ROLE_ATTR =1";
			}else{
				sql = "SELECT * FROM SYS_ROLE WHERE ROLE_LEVEL = (SELECT UN_LVL FROM SYS_UNIT WHERE UNNO = :unNo AND STATUS = 1) AND STATUS = 1 AND ROLE_ATTR = 1";
				map.put("unNo", user.getUnNo());
			}
		}else if("901000".equals(unitModel.getUnNo())){
			sql = "SELECT * FROM SYS_ROLE WHERE STATUS = 1 and ROLE_LEVEL IN(1,2) and ROLE_ATTR =1";
		}else{
			sql = "SELECT * FROM SYS_ROLE WHERE ROLE_LEVEL = (SELECT UN_LVL FROM SYS_UNIT WHERE UNNO = :unNo AND STATUS = 1) AND STATUS = 1 AND ROLE_ATTR = 1";
			map.put("unNo", user.getUnNo());
		}
		if(user.getLoginName().startsWith("baodan0")){
			sql +=" and CREATE_USER like 'baodan0%' ";
		}
		List<RoleModel> roleList = roleDao.queryObjectsBySqlList(sql, map, RoleModel.class);
		
		DataGridBean dataGridBean = formatToCombogrid(roleList, roleList.size());
		
		return dataGridBean;
	}

	/**
	 * 添加角色
	 */
	@Override
	public void saveRole(RoleBean role, String loginName) throws Exception{
		RoleModel roleModel = new RoleModel();
		BeanUtils.copyProperties(role, roleModel);
		roleModel.setStatus(1);
		roleModel.setRoleAttr(1);
		roleModel.setCreateDate(new Date());
		roleModel.setCreateUser(loginName);
		roleModel.setUpdateDate(new Date());
		roleModel.setUpdateUser(loginName);
		
		//建立多对多对应关系
		Set<MenuModel> set = new HashSet<MenuModel>();
		if (role.getMenuIds() != null) {
			String[] ids = role.getMenuIds().split(",");
			for (String id : ids) {
				MenuModel menu = new MenuModel();
				menu.setMenuID(Integer.parseInt(id.trim()));
				set.add(menu);
			}
			roleModel.setMenus(set);
		}
		
		roleDao.saveObject(roleModel);
	}
	
	/**
	 * 修改角色
	 */
	@Override
	public void updateRole(RoleBean role, String loginName) throws Exception{
		RoleModel roleModel = roleDao.getObjectByID(RoleModel.class, role.getRoleID());
		roleModel.setRoleName(role.getRoleName());
		roleModel.setDescription(role.getDescription());
		roleModel.setUpdateDate(new Date());
		roleModel.setUpdateUser(loginName);
		roleModel.setRoleLevel(role.getRoleLevel());
		//建立多对多对应关系
		Set<MenuModel> set = new HashSet<MenuModel>();
		if (role.getMenuIds() != null) {
			String[] ids = role.getMenuIds().split(",");
			for (String id : ids) {
				MenuModel menu = new MenuModel();
				menu.setMenuID(Integer.parseInt(id.trim()));
				set.add(menu);
			}
			roleModel.setMenus(set);
		}
		
		roleDao.updateObject(roleModel);
	}
	
	/**
	 * 删除角色
	 */
	@Override
	public boolean deleteRole(Integer id) throws Exception{
		RoleModel role = roleDao.getObjectByID(RoleModel.class, id);
		if (role.getUsers() != null && role.getUsers().size() > 0) {
			return false;
		}
		roleDao.deleteObject(role);
		return true;
	}

	/**
	 * 启用角色
	 */
	@Override
	public void updateStartRole(RoleBean roleBean) throws Exception {
		RoleModel roleModel = roleDao.getObjectByID(RoleModel.class, roleBean.getRoleID());
		roleModel.setStatus(1);
		roleDao.updateObject(roleModel);
	}

	/**
	 * 停用角色
	 */
	@Override
	public void updateCloseRole(RoleBean roleBean) throws Exception {
		RoleModel roleModel = roleDao.getObjectByID(RoleModel.class, roleBean.getRoleID());
		roleModel.setStatus(0);
		roleDao.updateObject(roleModel);
	}
	
}
