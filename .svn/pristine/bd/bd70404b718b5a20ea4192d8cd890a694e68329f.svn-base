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
import com.hrt.frame.dao.sysadmin.IUserDao;
import com.hrt.frame.entity.model.MenuModel;
import com.hrt.frame.entity.model.RoleModel;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.model.UserModel;
import com.hrt.frame.entity.pagebean.MenuBean;
import com.hrt.frame.entity.pagebean.TreeNodeBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.service.sysadmin.IMenuService;

/**
 * 类功能：菜单管理Service 创建人：wwy 创建日期：2013-01-10
 */
public class MenuServiceImpl implements IMenuService {

	private IMenuDao menuDao;

	private IUserDao userDao;
	
	private IUnitDao unitDao;
	
	private IRoleDao roleDao;

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

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public IUnitDao getUnitDao() {
		return unitDao;
	}

	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}

	/**
	 * 方法功能：转换MenuModel为treegrid格式 参数：menuModel recursive 是否递归查询子菜单
	 * 返回值：MenuBean对象 异常：
	 */
	private MenuBean changeModelToBean(MenuModel menuModel, boolean recursive) {
		MenuBean menuBean = new MenuBean();
		menuBean.setMenuID(menuModel.getMenuID());
		menuBean.setSeq(menuModel.getSeq());
		menuBean.setSrc(menuModel.getSrc());
		menuBean.setText(menuModel.getText());
		menuBean.setTranCode(menuModel.getTranCode());
		menuBean.setCreateDate(menuModel.getCreateDate());
		menuBean.setCreateUser(menuModel.getCreateUser());
		menuBean.setUpdateDate(menuModel.getUpdateDate());
		menuBean.setUpdateUser(menuModel.getUpdateUser());
		if (menuModel.getStatus() == 1) {
			menuBean.setStatusName("启用");
		} else {
			menuBean.setStatusName("停用");
		}
		if (menuModel.getChildren() != null
				&& menuModel.getChildren().size() > 0) {
			menuBean.setState("closed");
			if (recursive) {
				Set<MenuModel> menuSet = menuModel.getChildren();
				List<MenuBean> menuList = new ArrayList<MenuBean>();
				for (MenuModel menu : menuSet) {
					MenuBean subMenu = changeModelToBean(menu, true);
					menuList.add(subMenu);
				}

				menuBean.setChildren(menuList);
				menuBean.setState("open");
			}
		}

		return menuBean;
	}
	private MenuBean changeModelToBeanbaodan(MenuModel menuModel, boolean recursive) {
		MenuBean menuBean = new MenuBean();
		menuBean.setMenuID(menuModel.getMenuID());
		menuBean.setSeq(menuModel.getSeq());
		menuBean.setSrc(menuModel.getSrc());
		menuBean.setText(menuModel.getText());
		menuBean.setTranCode(menuModel.getTranCode());
		menuBean.setCreateDate(menuModel.getCreateDate());
		menuBean.setCreateUser(menuModel.getCreateUser());
		menuBean.setUpdateDate(menuModel.getUpdateDate());
		menuBean.setUpdateUser(menuModel.getUpdateUser());
		if (menuModel.getStatus() == 1) {
			menuBean.setStatusName("启用");
		} else {
			menuBean.setStatusName("停用");
		}
		if (menuModel.getChildren() != null
				&& menuModel.getChildren().size() > 0) {
			menuBean.setState("closed");
			if (recursive) {
				Set<MenuModel> menuSet = menuModel.getChildren();
				List<MenuBean> menuList = new ArrayList<MenuBean>();
				for (MenuModel menu : menuSet) {
					if(menu.getCreateUser()!=null&&menu.getCreateUser().startsWith("baodan0")){
						MenuBean subMenu = changeModelToBeanbaodan(menu, true);
						menuList.add(subMenu);
					}
				}
				menuBean.setChildren(menuList);
				menuBean.setState("open");
			}
		}
		
		return menuBean;
	}

	/**
	 * 方法功能：转化MenuModel为树节点数据格式 参数：menu recursive 是否递归查询子菜单 返回值： 异常：
	 */
	private TreeNodeBean changeToTreeNode(MenuModel menu, boolean recursive) {
		TreeNodeBean treeNodeBean = new TreeNodeBean();
		treeNodeBean.setId(menu.getMenuID() + "");
		treeNodeBean.setText(menu.getText());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("url", menu.getSrc());
		treeNodeBean.setAttributes(attributes);

		if (menu.getChildren() != null && menu.getChildren().size() > 0) {
			treeNodeBean.setState("closed");
			if (recursive) {
				Set<MenuModel> set = menu.getChildren();
				List<TreeNodeBean> list = new ArrayList<TreeNodeBean>();
				for (MenuModel menuModel : set) {
					TreeNodeBean treeNode = changeToTreeNode(menuModel, true);
					list.add(treeNode);
				}
				treeNodeBean.setChildren(list);
				treeNodeBean.setState("open");
			}
		}

		return treeNodeBean;
	}

	/**
	 * 方法功能：判断当前用户是否关联此菜单 参数：menu userMenuSet 当前用户的菜单Set集合 返回值：true 当前用户关联此菜单
	 * false 当前用户不关联此菜单 异常：
	 */
	private boolean isAddNode(MenuModel menu, Set<MenuModel> userMenuSet) {
		boolean isAdd = false;
		if (menu.getParent() == null) {
			isAdd = true;
		} else {
			for (MenuModel menuModel : userMenuSet) {
				if (menuModel.getMenuID() == menu.getMenuID()) {
					isAdd = true;
					break;
				}
			}

			// 菜单下的子菜单是否与当前用户关联
			if (!isAdd) {
				if (menu.getChildren() != null && menu.getChildren().size() > 0) {

					for (MenuModel chiledMenu : menu.getChildren()) {
						boolean result = isAddNode(chiledMenu, userMenuSet);
						if (result) {
							isAdd = true;
							break;
						}
					}
				}
			}

		}

		return isAdd;
	}

	/**
	 * 方法功能：转化MenuModel为树节点数据格式 参数：menu userMenuSet 当前用户的菜单集合 recursive
	 * 是否递归查询子菜单 返回值：TreeNodeBean对象 异常：
	 */
	private TreeNodeBean changeToTreeNodeByUser(MenuModel menu,
			Set<MenuModel> userMenuSet, boolean recursive) {
		boolean isAdd = isAddNode(menu, userMenuSet);
		if (!isAdd) {
			return null;
		}

		TreeNodeBean treeNodeBean = new TreeNodeBean();
		treeNodeBean.setId(menu.getMenuID() + "");
		treeNodeBean.setText(menu.getText());
		treeNodeBean.setIconCls(menu.getIconcls());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("url", menu.getSrc());
		treeNodeBean.setAttributes(attributes);

		if (menu.getChildren() != null && menu.getChildren().size() > 0) {
			treeNodeBean.setState("closed");

			// 递归查询用户关联的子菜单
			if (recursive) {
				Set<MenuModel> set = menu.getChildren();
				List<TreeNodeBean> list = new ArrayList<TreeNodeBean>();
				for (MenuModel menuModel : set) {
					if (menuModel.getStatus() == 1) {
						TreeNodeBean treeNode = changeToTreeNodeByUser(
								menuModel, userMenuSet, true);
						if (treeNode != null) {
							list.add(treeNode);
						}
					}
				}
				if (list.size() > 0) {
					treeNodeBean.setChildren(list);
					treeNodeBean.setState("open");
				}
			}
		}

		return treeNodeBean;
	}

	@Override
	public List<TreeNodeBean> queryAllMenuTree(MenuBean menuBean)
			throws Exception {
		String hql = "from MenuModel m where m.parent is null and m.status = 1";
		if (menuBean != null && menuBean.getId() != null) {
			hql = "from MenuModel m where m.parent.menuID = "
					+ menuBean.getId();
		}
		hql += " order by seq asc";
		List<MenuModel> menuList = menuDao.queryObjectsByHqlList(hql);
		List<TreeNodeBean> treeNodeList = new ArrayList<TreeNodeBean>();
		for (MenuModel menu : menuList) {
			treeNodeList.add(this.changeToTreeNode(menu, true));
		}

		return treeNodeList;
	}

	@Override
	public List<TreeNodeBean> queryMenuTreeByUser(MenuBean menuBean,
			UserBean userBean) throws Exception {
		String hql = "from MenuModel m where m.parent is null and m.status = 1";
		if (menuBean != null && menuBean.getId() != null) {
			hql = "from MenuModel m where m.status =1 and  m.parent.menuID = "
					+ menuBean.getId();
		}
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		hql += " order by seq asc";
		List<MenuModel> menuList = menuDao.queryObjectsByHqlList(hql);
		List<TreeNodeBean> treeNodeList = new ArrayList<TreeNodeBean>();

		// 查询用户下菜单
		UserModel userModel = userDao.getObjectByID(UserModel.class,
				userBean.getUserID());
		Set<MenuModel> userMenuSet = new HashSet<MenuModel>();
		for (RoleModel role : userModel.getRoles()) {
//			判断是否存在传统角色
//			if("0".equals(userBean.getIsM35Type())){
//				if(role.getRoleID2()!=null&&!"".equals(role.getRoleID2())){
//					role = roleDao.getObjectByID(RoleModel.class, role.getRoleID2());
//				}else{
//					return null;
//				}
//			}
			if (role.getStatus() == 1) {
				for (MenuModel menuUser : role.getMenus()) {
					//如果是代理商级别
					if(userModel.getUseLvl()==0 && unitModel.getUnLvl()==2){
						//屏蔽北分交行的菜单
						if(unitModel.getParent().getUnNo().equals("901000")){
							if(unitModel.getUnitName().contains("支行")){
								if(menuUser.getMenuID() !=42 && menuUser.getMenuID() !=47 &&  menuUser.getMenuID() !=52){
									userMenuSet.add(menuUser);
								}
							}else{
								if(menuUser.getMenuID() !=47 &&  menuUser.getMenuID() !=52){
									userMenuSet.add(menuUser);
								}
							}
						}else{
							userMenuSet.add(menuUser);
						}
					}else{
						//如果不是代理商级别
						if(unitModel.getUnNo().equals("901000")){
							//如果是北分交行分公司 则屏蔽--"商户入网保单"
							if(menuUser.getMenuID() !=42 ){
								userMenuSet.add(menuUser);
							}
						}else{
							String dd=unitModel.getUnNo().substring(0,unitModel.getUnNo().length()-3);
							if(!dd.equals("110")){
								//如果不是总公司级别或者总公司部门 则屏蔽--"北分交行入网报单"菜单
								if(menuUser.getMenuID()!=191)
									userMenuSet.add(menuUser);
							}else{
								userMenuSet.add(menuUser);
							}
							
						}
				
					}
				}
			}
		}

		for (MenuModel menu : menuList) {
			TreeNodeBean node = this.changeToTreeNodeByUser(menu, userMenuSet,
					true);
			if(node.getChildren() != null){
				if (node != null) {
					treeNodeList.add(node);
				}
			}
		}

		return treeNodeList;
	}

	@Override
	public List<MenuBean> queryMenusTreegrid(MenuBean menuBean)
			throws Exception {
		String hql = "from MenuModel m where m.parent is null";
		if (menuBean != null && menuBean.getId() != null) {
			hql = "from MenuModel m where m.parent.menuID = "
					+ menuBean.getId();
		}
		hql += " order by seq asc";
		List<MenuModel> menuModelList = menuDao.queryObjectsByHqlList(hql);
		List<MenuBean> menuList = new ArrayList<MenuBean>();
		for (MenuModel menu : menuModelList) {
			menuList.add(changeModelToBean(menu, true));
		}

		return menuList;
	}
	
	@Override
	public List<MenuBean> queryMenusTreegridbaodan(MenuBean menuBean)
			throws Exception {
		String hql = "from MenuModel m where m.parent is null";
		if (menuBean != null && menuBean.getId() != null) {
			hql = "from MenuModel m where m.parent.menuID = "
					+ menuBean.getId();
		}
		hql += " and createUser like'baodan0%' order by seq asc";
		List<MenuModel> menuModelList = menuDao.queryObjectsByHqlList(hql);
		List<MenuBean> menuList = new ArrayList<MenuBean>();
		for (MenuModel menu : menuModelList) {
			menuList.add(changeModelToBeanbaodan(menu, true));
		}

		return menuList;
	}

	@Override
	public void saveMenu(MenuBean menuBean, String loginName) throws Exception {
		MenuModel menuModel = new MenuModel();
		BeanUtils.copyProperties(menuBean, menuModel);
		menuModel.setStatus(1);
		menuModel.setCreateDate(new Date());
		menuModel.setCreateUser(loginName);
		menuModel.setUpdateDate(new Date());
		menuModel.setUpdateUser(loginName);
		MenuModel parent = new MenuModel();
		if (menuBean.get_parentId() != null
				&& !"".equals(menuBean.get_parentId().toString().trim())) {
			parent.setMenuID(menuBean.get_parentId());
			menuModel.setParent(parent);
		}

		menuDao.saveObject(menuModel);
	}

	@Override
	public void updateMenu(MenuBean menuBean, String loginName)
			throws Exception {
		MenuModel menuModel = menuDao.getObjectByID(MenuModel.class,
				menuBean.getMenuID());
		menuModel.setText(menuBean.getText());
		menuModel.setSeq(menuBean.getSeq());
		menuModel.setSrc(menuBean.getSrc());
		menuModel.setTranCode(menuBean.getTranCode());
		menuModel.setUpdateDate(new Date());
		menuModel.setUpdateUser(loginName);
		MenuModel parent = new MenuModel();
		if (menuBean.get_parentId() != null
				&& !"".equals(menuBean.get_parentId().toString().trim())) {
			parent.setMenuID(menuBean.get_parentId());
			menuModel.setParent(parent);
		}
		menuDao.updateObject(menuModel);
	}

	@Override
	public boolean deleteMenu(MenuBean menuBean) throws Exception {
		MenuModel menuModel = menuDao.getObjectByID(MenuModel.class,
				menuBean.getMenuID());
		// 是否有角色关联此菜单
//		if (menuModel.getRoles() != null && menuModel.getRoles().size() > 0) {
//			return false;
//		}

		menuDao.deleteObject(menuModel);
		return true;
	}

	@Override
	public List<MenuBean> searchMenuTranCode() throws Exception {
		String hql = "from MenuModel m where 1 = 1 and m.tranCode is not null order by m.tranCode asc";
		List<MenuModel> menuModelList = menuDao.queryObjectsByHqlList(hql);
		List<MenuBean> menuList = new ArrayList<MenuBean>();
		for (MenuModel menu : menuModelList) {
			menuList.add(changeModelToBean(menu, true));
		}

		return menuList;
	}

	@Override
	public void updateStartMenu(MenuBean menuBean) throws Exception {
		MenuModel menuModel = menuDao.getObjectByID(MenuModel.class,
				menuBean.getMenuID());
		menuModel.setStatus(1);
		menuDao.updateObject(menuModel);
	}

	@Override
	public void updateCloseMenu(MenuBean menuBean) throws Exception {
		MenuModel menuModel = menuDao.getObjectByID(MenuModel.class,
				menuBean.getMenuID());
		menuModel.setStatus(0);
		menuDao.updateObject(menuModel);
	}

	@Override
	public List<Map<String, Object>> queryShowNode(UserBean userBean) {
		String sql = "select sm.menu_id ID,sm.texts from SYS_MENU sm, SYS_USER su,"
				+ " SYS_USER_ROLE sur,SYS_ROLE sr,SYS_ROLE_MENU srm WHERE srm.menu_id=sm.menu_id"
				+ " AND srm.role_id=nvl(sr.role_id2,sr.role_id) AND sur.role_id=nvl(sr.role_id2,sr.role_id) "
				+ " AND sur.user_id=su.user_id AND sm.menu_id in (10,11,17,13) AND su.user_id="+userBean.getUserID();
		return menuDao.queryObjectsBySqlList(sql);
	}
}
