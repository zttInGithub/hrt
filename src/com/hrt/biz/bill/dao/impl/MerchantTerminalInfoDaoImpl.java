package com.hrt.biz.bill.dao.impl;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IMerchantTerminalInfoDao;
import com.hrt.biz.bill.entity.model.MerchantTerminalInfoModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class MerchantTerminalInfoDaoImpl extends BaseHibernateDaoImpl<MerchantTerminalInfoModel> implements IMerchantTerminalInfoDao{
	@Override
	public List<MerchantTerminalInfoModel> queryMerchantTerminalInfo(String hql,
			Map<String, Object> param, Integer page, Integer rows) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}

	@Override
	public Integer getBmtid() {
		List list=this.getCurrentSession().createSQLQuery("select S_BILL_MERCHANTTERMINALINFO.currval from dual").list();
		if(list==null){
			return 1;
		}else{
			return Integer.parseInt(list.get(0).toString());
		}
	}

}
