package com.hrt.biz.bill.dao.impl;

import com.hrt.biz.bill.dao.ICheckIncomeDao;
import com.hrt.biz.bill.entity.model.CheckIncomeModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class CheckIncomeDaoImpl extends BaseHibernateDaoImpl<CheckIncomeModel> implements ICheckIncomeDao {

	@Override
	public void updateSettleDate() {
		String sql="update check_unitdealdetail a set actionsetttledate = " +
				"(select b.settleday from CHECK_INCOME b where " +
						"  b.mid =a.mid  and a.txnday || substr(a.txndate, 0, 2) between b.txnday and b.txnday2" +
						"  and b.settleday = to_char(sysdate,'yyyyMMdd') and rownum=1 )" +
						"  where exists(select 1 from CHECK_INCOME b where " +
						"  b.mid =a.mid and a.txnday || substr(a.txndate, 0, 2) between b.txnday and b.txnday2" +
						"  and b.settleday = to_char(sysdate,'yyyyMMdd'))" +
					" and a.txntype = '0210' and a.actionsetttledate is null ";
		super.executeUpdate(sql);
	}

}
