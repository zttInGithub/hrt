package com.hrt.biz.bill.service;

import com.hrt.biz.bill.entity.pagebean.RebateRuleBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface RebateRuleService {

    /**
     * 是否存在该规则名称
     * @param brrName
     * @return
     */
    boolean hasRebateRuleByName(String brrName);

    /**
     * 活动规则添加
     * @param rebateRuleBean
     * @param userBean
     */
    void saveRebateRule(RebateRuleBean rebateRuleBean, UserBean userBean);

    /**
     * 查询活动规则列表
     * @param rebateRuleBean
     * @param userBean
     * @return
     */
    DataGridBean queryRebateRuleAll(RebateRuleBean rebateRuleBean, UserBean userBean);

    /**
     * 活动规则删除
     * @param brrId
     * @param userBean
     * @return
     */
    boolean deleteRebateRuleById(Integer brrId, UserBean userBean);

    /**
     * 活动规则审核
     * @param rebateRuleBean
     * @param userBean
     * @return
     */
	Integer updateRebateInfoAuditStatus(RebateRuleBean rebateRuleBean,
			UserBean userBean);
}
