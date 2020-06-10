package com.hrt.phone.service;

import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.NoticeBean;
import com.hrt.frame.entity.pagebean.UserBean;


public interface IPhoneNoticeService {

	DataGridBean queryNoticeNew(NoticeBean noticeBean, UserBean userBean);

}
