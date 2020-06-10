package com.hrt.frame.dao.sysadmin.impl;

import java.util.List;
import java.util.Map;

import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;
import com.hrt.frame.dao.sysadmin.INoticeDao;
import com.hrt.frame.entity.model.NoticeModel;

public class NoticeDaoImpl extends BaseHibernateDaoImpl<NoticeModel> implements INoticeDao {

	@Override
	public List<NoticeModel> queryNotice(String hql,
			Map<String, Object> param, Integer page, Integer rows) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}

}
