package com.hrt.frame.action.sysadmin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.model.MenuModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.TodoBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.service.sysadmin.ITodoService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 待办任务
 */
public class TodoAction extends BaseAction implements ModelDriven<TodoBean>{

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(TodoAction.class);
	
	private ITodoService todoService;
	
	private TodoBean todo = new TodoBean();
	
	//待办任务ID
	private String ids;
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	@Override
	public TodoBean getModel() {
		return todo;
	}

	public ITodoService getTodoService() {
		return todoService;
	}

	public void setTodoService(ITodoService todoService) {
		this.todoService = todoService;
	}

	/**
	 * 查询待办队列列表Grid
	 */
	public void listTodos(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = todoService.queryTodos(todo, ((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询待办任务信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 添加待办
	 */
	public void addTodo(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				todoService.saveTodo(todo, (UserBean)userSession);
				json.setSuccess(true);
				json.setMsg("添加待办任务成功");
			} catch (Exception e) {
				log.error("新增待办任务异常：" + e);
				json.setMsg("添加待办任务失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 查询SYS_BIZTYPE
	 */
	public void searchBizType(){
		DataGridBean dgd = new DataGridBean();
		try {
			dgd=todoService.searchBizType();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询SYS_BIZTYPE异常："+e);
		}
    	super.writeJson(dgd);
	}
	
	/**
	 * 删除待办
	 */
	public void deleteTodo(){
		JsonBean json = new JsonBean();
		try {
			todoService.deleteTodo(ids);
			json.setSuccess(true);
			json.setMsg("删除待办任务成功");
		} catch (Exception e) {
			log.error("删除待办任务异常：" + e);
			json.setMsg(e.getMessage());
		}
		super.writeJson(json);
	}
	
	/**
	 * 根据tranCode得到MenuModel
	 */
	public void updateStruts(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			MenuModel menuModel = todoService.updateStruts(todo.getTodoID());
			map.put("text", menuModel.getText());
			map.put("src", menuModel.getSrc());
		} catch (Exception e) {
			log.error("跳转菜单异常：" + e);
		}
		super.writeJson(map);
	}
	
	/**
	 * 查询有多少还未待办
	 */
	public void todoCount(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			long counts = todoService.todoCount(0,((UserBean)userSession).getUnNo());
			map.put("todoCount", counts);
		} catch (Exception e) {
			log.error("查询待办队列异常：" + e);
		}
		super.writeJson(map);
	}
	
}
