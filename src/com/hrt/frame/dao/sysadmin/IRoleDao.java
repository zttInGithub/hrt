package com.hrt.frame.dao.sysadmin;

import java.util.List;
import java.util.Map;

import com.hrt.frame.base.dao.IBaseHibernateDao;
import com.hrt.frame.entity.model.RoleModel;

/**
 * 类功能：角色管理Dao
 * 创建人：wwy	
 * 创建日期：2012-12-18
 */
public interface IRoleDao extends IBaseHibernateDao<RoleModel> {
	
	/**
	 * 方法功能：分页查询角色
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<RoleModel>
	 * 异常：
	 */
	List<RoleModel> queryRoles(String hql, Map<String, Object> param, Integer page, Integer rows);

}
