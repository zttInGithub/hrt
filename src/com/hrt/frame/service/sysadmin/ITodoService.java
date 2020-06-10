package com.hrt.frame.service.sysadmin;

import com.hrt.frame.entity.model.MenuModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.TodoBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface ITodoService {

	/**
	 * 查询待办队列列表Grid
	 * @param todo1
	 * @param userBean 登录用户信息
	 * @return
	 * @throws Exception
	 */
	DataGridBean queryTodos(TodoBean todo1, UserBean userBean) throws Exception;

	/**
	 * 添加待办任务
	 * @param todo1
	 * @param user
	 * @throws Exception
	 */
	void saveTodo(TodoBean todo1, UserBean user) throws Exception;

	/**
	 * 查询业务类别
	 * @return
	 * @throws Exception
	 */
	DataGridBean searchBizType() throws Exception;

	/**
	 * 删除待办任务
	 * @param ids
	 * @throws Exception
	 */
	void deleteTodo(String ids) throws Exception;

	/**
	 * 根据tranCode得到MenuModel
	 * @param todoID
	 * @return
	 * @throws Exception
	 */
	MenuModel updateStruts(Integer todoID) throws Exception;

	/**
	 * 查询有多少未办的待办队列
	 * @param status
	 * @param unNo
	 * @return
	 * @throws Exception
	 */
	long todoCount(Integer status,String unNo) throws Exception;
	
}
