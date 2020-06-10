package com.hrt.biz.bill.dao.impl;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IMachineTaskDataDao;
import com.hrt.biz.bill.entity.model.MachineOrderDetailModel;
import com.hrt.biz.bill.entity.model.MachineTaskDataModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class MachineTaskDataDaoImpl  extends BaseHibernateDaoImpl<MachineTaskDataModel> implements IMachineTaskDataDao {

	@Override
	public List<MachineTaskDataModel> queryMachineTaskData(String sql,
			Map<String, Object> param, Integer page, Integer rows, Class cls) {
		return super.queryObjectsBySqlLists(sql, param, page, rows, cls);
	}
	
	@Override
	public Integer getBmtdID() {
		List list=this.getCurrentSession().createSQLQuery("select S_BILL_MACHINETASKDATA.currval from dual").list();
		if(list==null){
			return 1;
		}else{
			return Integer.parseInt(list.get(0).toString());
		}
	}

}
