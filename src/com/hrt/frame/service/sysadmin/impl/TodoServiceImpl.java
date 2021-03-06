package com.hrt.frame.service.sysadmin.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.hrt.frame.dao.sysadmin.IMenuDao;
import com.hrt.frame.dao.sysadmin.ITodoDao;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.dao.sysadmin.IUserDao;
import com.hrt.frame.entity.model.MenuModel;
import com.hrt.frame.entity.model.TodoModel;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.model.UserModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.TodoBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.service.sysadmin.ITodoService;

public class TodoServiceImpl implements ITodoService {

	private ITodoDao todoDao;
	
	private IUserDao userDao;
	
	private IUnitDao unitDao;
	
	private IMenuDao menuDao;
	
	public ITodoDao getTodoDao() {
		return todoDao;
	}

	public void setTodoDao(ITodoDao todoDao) {
		this.todoDao = todoDao;
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
	
	public IMenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}
	
	private String bizTypeName(String bizType){
		String sql = "SELECT BIZDESC FROM SYS_BIZTYPE WHERE BIZTYPE = '" + bizType +"'";
		List<Map<String, String>> list = menuDao.executeSql(sql);
		return list.get(0).get("BIZDESC");
	}
	
	/**
	 * 方法功能：格式化TodoModel为datagrid数据格式
	 */
	private DataGridBean formatToDataGrid(List<TodoModel> todoList, long total) {
		List<TodoBean> todoBeanList = new ArrayList<TodoBean>();
		for(TodoModel todoModel : todoList) {
			TodoBean todoBean = new TodoBean();
			BeanUtils.copyProperties(todoModel, todoBean);
			
			//消息状态名称
			todoBean.setStatusName(todoModel.getStatus()==0?"未办":"已办");
			
			UserModel userModel = userDao.getObjectByID(UserModel.class, todoModel.getMsgSender());
			todoBean.setMsgSendName(userModel.getUserName());
			
			UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, todoModel.getMsgSendUnit());
			todoBean.setMsgSendUnitName(unitModel.getUnitName());
			
			UnitModel unnoName = unitDao.getObjectByID(UnitModel.class, todoModel.getUnNo());
			todoBean.setUnnoName(unnoName.getUnitName());
			
			//业务类别名称
			todoBean.setBizTypeName(bizTypeName(todoModel.getBizType()));
			
			todoBeanList.add(todoBean);
		}
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(todoBeanList);
		return dgb;
	}

	@Override
	public DataGridBean queryTodos(TodoBean todo, UserBean userBean) throws Exception{
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		String hql = "";
		String hqlCount = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if("110000".equals(userBean.getUnNo())){
			hql = "from TodoModel t where 1 = 1 and t.status=:status";
			hqlCount = "select count(*) from TodoModel t where 1 = 1 and t.status=:status";
			map.put("status", 0 );
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
				hql = "from TodoModel t where 1 = 1 and t.status=:status";
				hqlCount = "select count(*) from TodoModel t where 1 = 1 and t.status=:status";
				map.put("status", 0 );
			}
		}else{
			hql = "from TodoModel t where 1 = 1 and t.status=:status and t.unNo in ('public',:unNo)";
			hqlCount = "from TodoModel t where 1 = 1 and t.status=:status and t.unNo in ('public',:unNo)";
			map.put("status", 0 );
			map.put("unNo", userBean.getUnNo());
		}
		
		if (todo.getSort() != null) {
			hql += " order by " + todo.getSort() + " " + todo.getOrder();
		}
		
		long counts = todoDao.queryCounts(hqlCount, map);
		List<TodoModel> todoList = todoDao.queryTodos(hql, map, todo.getPage(), todo.getRows());
		DataGridBean dataGridBean = formatToDataGrid(todoList, counts);
		return dataGridBean;
	}
	
	@Override
	public void saveTodo(TodoBean todo, UserBean user) throws Exception{
		TodoModel todoModel = new TodoModel();
		BeanUtils.copyProperties(todo, todoModel);
		todoModel.setMsgSendUnit(user.getUnNo());
		todoModel.setMsgSender(user.getUserID());
		todoModel.setMsgSendTime(new Date());
		todoModel.setStatus(0);	//默认未办
		todoDao.saveObject(todoModel);
	}

	@Override
	public DataGridBean searchBizType() throws Exception {
		String sql="SELECT BIZTYPE,BIZDESC FROM SYS_BIZTYPE";
		List<Map<String,String>> listMap = todoDao.executeSql(sql);
		
		List<Object>  list = new ArrayList<Object>();
		for (int i = 0; i < listMap.size(); i++) {
			Map map =listMap.get(i);
			list.add(map);
		}
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(listMap.size());
		dgd.setRows(list);
		return dgd;
	}

	@Override
	public void deleteTodo(String ids) throws Exception{
		TodoModel todoModel = new TodoModel();
		todoModel.setTodoID(Integer.parseInt(ids));
		todoDao.deleteObject(todoModel);
	}

	@Override
	public MenuModel updateStruts(Integer todoID) throws Exception {
		TodoModel todoModel = todoDao.getObjectByID(TodoModel.class, todoID);
		String hql = "from MenuModel m where m.tranCode = :tranCode";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("tranCode", todoModel.getTranCode());
		List<MenuModel> menuModel = menuDao.queryObjectsByHqlList(hql, map);
		return menuModel.get(0);
	}

	@Override
	public long todoCount(Integer status,String unno) throws Exception {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, unno);
		String hql = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if("110000".equals(unno)){
			hql = "select count(*) from TodoModel t where 1 = 1 and t.status=:status";
			map.put("status", 0 );
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
				hql = "select count(*) from TodoModel t where 1 = 1 and t.status=:status";
				map.put("status", 0 );
			}
		}else{
			hql = "select count(*) from TodoModel t where 1 = 1 and t.status=:status and t.unNo in ('public',:unNo)";
			map.put("status", 0 );
			map.put("unNo", unno);
		}
		long counts = todoDao.queryCounts(hql, map);
		return counts;
	}

}
