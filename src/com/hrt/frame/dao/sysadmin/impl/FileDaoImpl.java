package com.hrt.frame.dao.sysadmin.impl;

import java.util.List;
import java.util.Map;

import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;
import com.hrt.frame.dao.sysadmin.IFileDao;
import com.hrt.frame.entity.model.FileModel;

public class FileDaoImpl extends BaseHibernateDaoImpl<FileModel> implements IFileDao{

	/**
	 * 方法功能：分页查询信息
	 * 参数：hql hql语句
	 *     param 参数Map
	 *     page 当前页
	 *     rows 每页记录数
	 * 返回值：List<FileModel>
	 * 异常：
	 */
	@Override
	public List<FileModel> queryFiles(String hql, Map<String, Object> param,
			Integer page, Integer rows) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}

}
