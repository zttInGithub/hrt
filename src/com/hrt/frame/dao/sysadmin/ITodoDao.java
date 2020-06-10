package com.hrt.frame.dao.sysadmin;

import java.util.List;
import java.util.Map;

import com.hrt.frame.base.dao.IBaseHibernateDao;
import com.hrt.frame.entity.model.TodoModel;

public interface ITodoDao extends IBaseHibernateDao<TodoModel>{
	
	/**
	 * 方法功能：分页查询待办
	 */
	List<TodoModel> queryTodos(String hql, Map<String, Object> param, Integer page, Integer rows);
	
	/**
	 * 修改已办
	 */
	void editStatusTodo(String bizType,String batchJobNo);

}
