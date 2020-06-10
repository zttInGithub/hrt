package com.hrt.phone.dao;

import com.hrt.frame.base.dao.IBaseHibernateDao;

public interface IPhoneReceiptsUploadDao extends IBaseHibernateDao  {

	String queryUploadPath(String title);

}
