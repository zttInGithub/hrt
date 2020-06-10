package com.hrt.phone.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;

import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;
import com.hrt.phone.dao.IPhoneReceiptsUploadDao;

public class PhoneReceiptsUploadDaoImpl extends BaseHibernateDaoImpl implements IPhoneReceiptsUploadDao {

	@Override
	public String queryUploadPath(String title) {
		Session session=this.getCurrentSession();
		Query query=session.createSQLQuery("select UPLOAD_PATH from SYS_PARAM where TITLE='"+title+"'");
		return query.list().get(0).toString();
	}

}
