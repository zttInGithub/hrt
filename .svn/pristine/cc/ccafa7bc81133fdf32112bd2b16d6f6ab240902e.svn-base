package com.hrt.biz.bill.dao;

import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.frame.base.dao.IBaseHibernateDao;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IMerchantTaskDetailDao extends IBaseHibernateDao<Object> {

	String findMaxId();

	MerchantInfoModel queryMerchantInfo(String mid,UserBean user);

	String querySavePath(String title);

	String queryDownloadPath(String title);

}
