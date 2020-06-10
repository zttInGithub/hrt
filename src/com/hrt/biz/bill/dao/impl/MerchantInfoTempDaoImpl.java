package com.hrt.biz.bill.dao.impl;

import java.util.List;

import com.hrt.biz.bill.dao.IMerchantInfoTempDao;
import com.hrt.biz.bill.entity.model.MerchantInfoTempModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class MerchantInfoTempDaoImpl extends BaseHibernateDaoImpl<MerchantInfoTempModel> implements IMerchantInfoTempDao {
	
	public Integer getFid(){
		List list=this.getCurrentSession().createSQLQuery("select S_Bill_BP_FILE.currval from dual").list();
		if(list==null){
			return 1;
		}else{
			return Integer.parseInt(list.get(0).toString());
		}
	}

}
