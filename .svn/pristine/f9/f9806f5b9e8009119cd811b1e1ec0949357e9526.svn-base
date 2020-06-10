package com.hrt.biz.check.service;

import com.hrt.biz.check.entity.pagebean.PgCashbackSpecialBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

import java.util.List;
import java.util.Map;

public interface PgCashbackSpecialService {

    /**
     *  特殊活动返现--查询
     * @param pgCashbackSpecialBean
     * @param user
     * @return
     */
    DataGridBean queryCustomizeActivityCashbackData(PgCashbackSpecialBean pgCashbackSpecialBean, UserBean user);
    DataGridBean queryCustomizeActivityCashbackData_selectRebateType();

    /**
     * 特殊活动返现--导出
     * @param pgCashbackSpecialBean
     * @param userBean
     * @return
     */
    List<Map<String, Object>> exportCustomizeActivityCashbackData(PgCashbackSpecialBean pgCashbackSpecialBean, UserBean userBean);
}
