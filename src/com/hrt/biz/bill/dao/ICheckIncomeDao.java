package com.hrt.biz.bill.dao;


import com.hrt.biz.bill.entity.model.CheckIncomeModel;
import com.hrt.frame.base.dao.IBaseHibernateDao;

public interface ICheckIncomeDao extends IBaseHibernateDao<CheckIncomeModel>{

	void updateSettleDate();

}
