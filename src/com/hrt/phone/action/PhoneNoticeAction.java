package com.hrt.phone.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.NoticeBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.phone.service.IPhoneNoticeService;
import com.opensymphony.xwork2.ModelDriven;

public class PhoneNoticeAction extends BaseAction implements ModelDriven<NoticeBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(PhoneNoticeAction.class);

	private NoticeBean noticeBean =new NoticeBean();
	private IPhoneNoticeService phoneNoticeService;

	public IPhoneNoticeService getPhoneNoticeService() {
		return phoneNoticeService;
	}
	public void setPhoneNoticeService(IPhoneNoticeService phoneNoticeService) {
		this.phoneNoticeService = phoneNoticeService;
	}
	
	@Override
	public NoticeBean getModel() {
		return noticeBean;
	}
	//首页公告查询
	public void findNoticeList(){
		DataGridBean dgb = new DataGridBean();
		JsonBean json = new JsonBean();
		Map<String ,Object> map =new HashMap<String, Object>();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = phoneNoticeService.queryNoticeNew(noticeBean,((UserBean)userSession));
			map.put("data", dgb);
			json.setMsg("查询成功！");
			json.setObj(map);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("分页查询公告信息异常：" + e);
			json.setMsg("查询失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	
}
