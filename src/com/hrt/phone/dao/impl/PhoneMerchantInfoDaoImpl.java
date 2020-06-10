package com.hrt.phone.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;

import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;
import com.hrt.phone.dao.IPhoneMerchantInfoDao;

public class PhoneMerchantInfoDaoImpl extends BaseHibernateDaoImpl implements
		IPhoneMerchantInfoDao{

	@Override
	public String querySavePath(String title) {
		Session session=this.getCurrentSession();
		Query query=session.createSQLQuery("SELECT UPLOAD_PATH FROM SYS_PARAM WHERE TITLE='"+title+"'");
		return query.list().get(0).toString();
	}

}
