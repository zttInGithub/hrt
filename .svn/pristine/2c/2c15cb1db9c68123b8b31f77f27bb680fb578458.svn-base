package com.hrt.biz.bill.dao.impl;

import java.util.List;

import com.hrt.biz.bill.dao.IMerchantTaskDetailOtherDao;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class MerchantTaskDetailOtherDaoImpl extends BaseHibernateDaoImpl<Object>   implements IMerchantTaskDetailOtherDao {

	@SuppressWarnings("unchecked")
	@Override
	public MerchantInfoModel queryMerchantInfo(String mid) {
		if(mid!=null && mid.length()>0){
			List<MerchantInfoModel> list= this.getCurrentSession().createQuery("from MerchantInfoModel m where m.mid=?").setParameter(0, mid).list();
			if(list.size()>0){ 
				return list.get(0); 
			}else{
				return null; 
			}
		}else{ 
			return null; 
		}
	}
	
}
