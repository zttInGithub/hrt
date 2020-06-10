package com.hrt.frame.dao.sysadmin.impl;

import java.util.List;
import java.util.Map;

import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;
import com.hrt.frame.dao.sysadmin.ITodoDao;
import com.hrt.frame.entity.model.TodoModel;

public class TodoDaoImpl extends BaseHibernateDaoImpl<TodoModel> implements ITodoDao {
	
	@Override
	public List<TodoModel> queryTodos(String hql, Map<String, Object> param,
			Integer page, Integer rows) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}

	@Override
	public void editStatusTodo(String bizType, String batchJobNo) {
		String[] param={bizType,batchJobNo};
		String hql="FROM TodoModel WHERE bizType=? AND batchJobNo=? AND status=0";
		TodoModel to=super.queryObjectByHql(hql, param);
		if(to != null){
			to.setStatus(1);
			super.updateObject(to);
		}
	}
	
}
