package com.hrt.frame.action.sysadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.model.NoticeModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.NoticeBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.service.sysadmin.INoticeService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 公告消息处理
 */
public class NoticeAction extends BaseAction implements ModelDriven<NoticeBean>{
	
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(NoticeAction.class);
	
	private NoticeBean notice = new NoticeBean();
	
	private INoticeService noticeService;
	
	//公告ID
	private String ids;
	
	@Override
	public NoticeBean getModel() {
		return notice;
	}

	public INoticeService getNoticeService() {
		return noticeService;
	}
	
	public void setNoticeService(INoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	/**
	 * 查询公告发布列表Grid
	 */
	public void listNoticeRelease() {
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = noticeService.queryNoticeRelease(notice,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询公告信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询公告消息
	 */
	public void listNoticeNew() {
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = noticeService.queryNoticeNew(notice,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询公告信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 添加公告
	 */
	public void addNotice(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				noticeService.saveNotice(notice, ((UserBean)userSession));
				json.setSuccess(true);
				json.setMsg("添加公告成功");
			} catch (Exception e) {
				log.error("新增公告异常：" + e);
				json.setMsg("添加公告失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 删除公告
	 */
	public void deleteNotice(){
		JsonBean json = new JsonBean();
		try {
			boolean res = noticeService.deleteNotice(Integer.parseInt(ids));
			if (res) {
				json.setSuccess(true);
				json.setMsg("删除公告成功");
			}
		} catch (Exception e) {
			log.error("公告角色异常：" + e);
			json.setMsg("公告角色失败");
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 修改公告
	 */
	public void editStatus(){
		try {
			noticeService.updateStatus(notice);
		} catch (Exception e) {
			log.error("修改公告状态异常：" + e);
		}
	}
	
	/**
	 * 查询公告内容
	 */
	public void findNoticeMsgById(){
		try {
			List<NoticeModel> list=noticeService.queryNoticeMsgById(notice.getNoticeID());
			super.writeJson(list);
		} catch (Exception e) {
			log.error("查询公告内容异常:"+e);
		}
	}
	
	/**
	 * 查询有多少还未读公告
	 */
	public void noticeCount(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			long counts = noticeService.noticeCount(0,((UserBean)userSession).getUnNo());
			map.put("noticeCount", counts);
		} catch (Exception e) {
			log.error("查询未读公告异常：" + e);
		}
		super.writeJson(map);
	}
}
