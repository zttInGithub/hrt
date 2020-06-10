package com.hrt.biz.bill.service.impl;

import com.hrt.biz.bill.dao.IRebateRuleInfoDao;
import com.hrt.biz.bill.entity.model.RebateRuleInfoModel;
import com.hrt.biz.bill.service.RebateRuleInfoService;

/**
 * RebateRuleInfoServiceImpl
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2020/01/15
 * @since 1.8
 **/
public class RebateRuleInfoServiceImpl implements RebateRuleInfoService {
    private IRebateRuleInfoDao rebateRuleInfoDao;

    public IRebateRuleInfoDao getRebateRuleInfoDao() {
        return rebateRuleInfoDao;
    }

    public void setRebateRuleInfoDao(IRebateRuleInfoDao rebateRuleInfoDao) {
        this.rebateRuleInfoDao = rebateRuleInfoDao;
    }

    @Override
    public void saveRebateRuleInfo(RebateRuleInfoModel model) {
        rebateRuleInfoDao.saveObject(model);
    }
}
