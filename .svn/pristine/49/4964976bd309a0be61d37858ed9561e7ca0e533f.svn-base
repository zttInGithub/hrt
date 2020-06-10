package com.hrt.frame.dao.sysadmin.impl;

import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;
import com.hrt.frame.dao.sysadmin.IQuartzDao;

public class QuartzDaoImpl extends BaseHibernateDaoImpl<Object> implements IQuartzDao{

	@Override
	public void batchJob1() {
		super.getCurrentSession().createSQLQuery("{Call BILL_POLL_TXN()}").executeUpdate();
	}

}
