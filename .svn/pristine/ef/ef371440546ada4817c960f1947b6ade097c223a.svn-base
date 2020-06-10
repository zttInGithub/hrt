package com.hrt.frame.dao.sysadmin;

import java.util.List;
import java.util.Map;

import com.hrt.frame.base.dao.IBaseHibernateDao;
import com.hrt.frame.entity.model.FileModel;

public interface IFileDao extends IBaseHibernateDao<FileModel>{

	/**
	 * 方法功能：分页查询信息
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<FileModel>
	 * 异常：
	 */
	List<FileModel> queryFiles(String hql, Map<String, Object> param, Integer page, Integer rows);
	
}
