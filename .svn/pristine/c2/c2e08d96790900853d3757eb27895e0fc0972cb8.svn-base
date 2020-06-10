package com.hrt.frame.action.sysadmin;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.util.WebUtils;

import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.service.sysadmin.IQuartzService;

/**
 * 定时任务
 */
public class QuartzAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private Log log=LogFactory.getLog(QuartzAction.class);
	
	private IQuartzService quartzService;
	
	public IQuartzService getQuartzService() {
		return quartzService;
	}

	public void setQuartzService(IQuartzService quartzService) {
		this.quartzService = quartzService;
	}

	/**
	 * 任务监控列表Grid查询
	 */
	public void queryQrtzTriggers() {
		List list = new ArrayList();
		try {
			list = quartzService.queryQrtzTriggers();
		} catch (Exception e) {
			log.error("查询任务监控列表Grid信息异常："+e);
		}
		super.writeJson(list);
	}

	/**
	 * 添加
	 */
	public void insertQrtzTrigger() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if(userSession == null){
			json.setSessionExpire(true);
		}else{
			try {
				Map<String, Object> filterMap = WebUtils.getParametersStartingWith(ServletActionContext.getRequest(), "p_");
				quartzService.insertQrtzTrigger(filterMap);
				json.setSuccess(true);
				json.setMsg("添加任务成功!");
			} catch (Exception e) {
				log.error("添加任务异常："+e);
				json.setMsg(e.getMessage());
			}
		}
		super.writeJson(json);
	}

	/**
	 * 判断执行暂停、恢复或删除
	 */
	public void isTrigdger(){
		String action = ServletActionContext.getRequest().getParameter("action");
		if("pause".equals(action)){
			this.pauseTrigger();
		}else if("resume".equals(action)){
			this.resumeTrigger();
		}else if("remove".equals(action)){
			this.removeTrigger();
		}
	}
	
	/**
	 * 暂停
	 */
	public void pauseTrigger(){
		JsonBean json = new JsonBean();
		try {
			String triggerName = URLDecoder.decode(ServletActionContext.getRequest().getParameter("triggerName"), "utf-8");
			String group = URLDecoder.decode(ServletActionContext.getRequest().getParameter("group"), "utf-8");
			quartzService.pauseTrigger(triggerName,group);
			json.setSuccess(true);
			json.setMsg("任务暂停成功!");
		} catch (Exception e) {
			log.error("暂停任务信息异常："+e);
			json.setMsg(e.getMessage());
		}
		super.writeJson(json);
	}
	
	/**
	 * 恢复
	 */
	public void resumeTrigger(){
		JsonBean json = new JsonBean();
		try {
			String triggerName = URLDecoder.decode(ServletActionContext.getRequest().getParameter("triggerName"), "utf-8");
			String group = URLDecoder.decode(ServletActionContext.getRequest().getParameter("group"), "utf-8");
			quartzService.resumeTrigger(triggerName, group);
			json.setSuccess(true);
			json.setMsg("任务恢复成功!");
		} catch (Exception e) {
			log.error("任务恢复异常："+e);
			json.setMsg(e.getMessage());
		}
		super.writeJson(json);
	}

	/**
	 * 刪除
	 */
	public void removeTrigger() {
		JsonBean json = new JsonBean();
		try {
			String triggerName = URLDecoder.decode(ServletActionContext.getRequest().getParameter("triggerName"), "utf-8");
			String group = URLDecoder.decode(ServletActionContext.getRequest().getParameter("group"), "utf-8");
			quartzService.removeTrigger(triggerName, group);
			json.setSuccess(true);
			json.setMsg("删除任务成功!");
		} catch (Exception e) {
			log.error("删除任务异常："+e);
			json.setMsg(e.getMessage());
		}
		super.writeJson(json);
	}

}
