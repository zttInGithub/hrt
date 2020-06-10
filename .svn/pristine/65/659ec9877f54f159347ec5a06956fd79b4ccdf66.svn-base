package com.hrt.biz.check.service;

import com.hrt.biz.check.entity.pagebean.CheckDeductionBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

import java.util.List;
import java.util.Map;

public interface CheckDeductionService {

    /**
     *  查询扣款
     * @param checkDeductionBean
     * @param user
     * @return
     */
    DataGridBean queryCheckDeductionInfo(CheckDeductionBean checkDeductionBean, UserBean user);
    //下拉框
    DataGridBean queryCheckDeductionListInfo10170_selectRebateType();
    
    /**
     * 扣款导出
     * @param checkDeductionBean
     * @param userBean
     * @return
     */
    List<Map<String, Object>> export10170(CheckDeductionBean checkDeductionBean, UserBean userBean);
}
