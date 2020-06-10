package com.hrt.biz.bill.service;

import com.hrt.biz.bill.entity.model.RebateInfoModel;
import com.hrt.biz.bill.entity.pagebean.RebateInfoBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface RebateInfoService {

    /**
     * 保存活动定义
     * @param rebateInfoBean
     * @param userBean
     */
    void saveRebateInfo(RebateInfoBean rebateInfoBean, UserBean userBean);

    /**
     * 是否存在改活动定义
     * @param rebateType
     * @return
     */
    boolean existsRebateInfoByRebate(String rebateType);

    /**
     * 修改活动定义
     * @param rebateInfoBean
     * @param userBean
     */
    void updateRebateInfo(RebateInfoBean rebateInfoBean,UserBean userBean);

    /**
     * 获取单个活动定义
     * @param brId
     * @return
     */
    RebateInfoModel getRebateInfoById(Integer brId);

    /**
     * 查询活动定义列表
     * @param rebateInfoBean
     * @param userBean
     * @return
     */
    DataGridBean queryRebateInfoAll(RebateInfoBean rebateInfoBean, UserBean userBean);

    /**
     * 删除活动定义
     * @param brId
     * @param userBean
     * @return
     */
    boolean deleteRebateInfoById(Integer brId, UserBean userBean);

    /**
     * 审核活动定义
     * @param rebateInfoBean
     * @param userBean
     * @return
     */
	Integer updateRebateInfoAuditStatus(RebateInfoBean rebateInfoBean,
			UserBean userBean);
}
