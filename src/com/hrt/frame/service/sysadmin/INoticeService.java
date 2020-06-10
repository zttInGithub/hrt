package com.hrt.frame.service.sysadmin;

import java.util.List;

import com.hrt.frame.entity.model.NoticeModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.NoticeBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface INoticeService {

	/**
	 * 查询公告发布列表Grid
	 * @param notice 请求参数信息
	 * @param userBean 登录用户信息
	 * @return
	 * @throws Exception
	 */
	DataGridBean queryNoticeRelease(NoticeBean notice,UserBean userBean) throws Exception;

	/**
	 * 查询公告消息
	 * @param notice
	 * @param userBean
	 * @return
	 * @throws Exception
	 */
	DataGridBean queryNoticeNew(NoticeBean notice,UserBean userBean) throws Exception;

	/**
	 * 添加公告
	 * @param notice
	 * @param user
	 * @throws Exception
	 */
	void saveNotice(NoticeBean notice, UserBean user) throws Exception;

	/**
	 * 删除公告
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean deleteNotice(Integer id) throws Exception;
	
	/**
	 * 修改状态为已读
	 * @param notice
	 * @throws Exception
	 */
	void updateStatus(NoticeBean notice) throws Exception;

	/**
	 * 查询公告内容
	 * @param noticeID
	 * @return
	 */
	List<NoticeModel> queryNoticeMsgById(Integer noticeID);
	
	/**
	 * 查询有多少还未读公告
	 * @param status 状态
	 * @param unNo 机构号
	 * @return
	 * @throws Exception
	 */
	long noticeCount(Integer status,String unNo) throws Exception;
	
}
