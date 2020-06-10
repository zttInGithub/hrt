package com.hrt.frame.dao.sysadmin.impl;

import java.util.List;
import java.util.Map;

import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;
import com.hrt.frame.dao.sysadmin.IRoleDao;
import com.hrt.frame.entity.model.RoleModel;
/**
 * 类功能：角色管理Dao
 * 创建人：wwy	
 * 创建日期：2012-12-18
 */
public class RoleDaoImpl extends BaseHibernateDaoImpl<RoleModel> implements IRoleDao{
	
	@Override
	public List<RoleModel> queryRoles(String hql, Map<String, Object> param,
			Integer page, Integer rows) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}

	
}
