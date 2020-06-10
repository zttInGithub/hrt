package com.hrt.biz.bill.action;

import com.hrt.biz.bill.entity.pagebean.RebateRuleBean;
import com.hrt.biz.bill.service.RebateInfoService;
import com.hrt.biz.bill.service.RebateRuleInfoService;
import com.hrt.biz.bill.service.RebateRuleService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.service.sysadmin.IEnumService;
import com.opensymphony.xwork2.ModelDriven;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * RebateInfoAction
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2020/01/15
 * @since 1.8
 **/
public class RebateRuleAction extends BaseAction implements ModelDriven<RebateRuleBean> {

    private static final Log log = LogFactory.getLog(RebateRuleAction.class);

    private RebateInfoService rebateInfoService;
    private RebateRuleService rebateRuleService;
    private RebateRuleInfoService rebateRuleInfoService;
    private IEnumService enumService;

    public IEnumService getEnumService() {
        return enumService;
    }

    public void setEnumService(IEnumService enumService) {
        this.enumService = enumService;
    }

    public RebateInfoService getRebateInfoService() {
        return rebateInfoService;
    }

    public void setRebateInfoService(RebateInfoService rebateInfoService) {
        this.rebateInfoService = rebateInfoService;
    }

    public RebateRuleService getRebateRuleService() {
        return rebateRuleService;
    }

    public void setRebateRuleService(RebateRuleService rebateRuleService) {
        this.rebateRuleService = rebateRuleService;
    }

    public RebateRuleInfoService getRebateRuleInfoService() {
        return rebateRuleInfoService;
    }

    public void setRebateRuleInfoService(RebateRuleInfoService rebateRuleInfoService) {
        this.rebateRuleInfoService = rebateRuleInfoService;
    }

    private RebateRuleBean rebateRuleBean = new RebateRuleBean();

    public RebateRuleBean getRebateRuleBean() {
        return rebateRuleBean;
    }

    public void setRebateRuleBean(RebateRuleBean rebateRuleBean) {
        this.rebateRuleBean = rebateRuleBean;
    }

    @Override
    public RebateRuleBean getModel() {
        return rebateRuleBean;
    }

    /**
     * 活动规则列表
     */
    public void listRebateRules() {
        DataGridBean dgb = new DataGridBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if (userSession == null) {

            } else {
                UserBean userBean = (UserBean) userSession;
                dgb = rebateRuleService.queryRebateRuleAll(rebateRuleBean, userBean);
            }
        } catch (Exception e) {
            log.error("分页查询活动规则异常：" + e);
        }
        super.writeJson(dgb);
    }

    public void listRebateRulesYes() {
        DataGridBean dgb = new DataGridBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if (userSession == null) {

            } else {
                UserBean userBean = (UserBean) userSession;
                rebateRuleBean.setAuditStatus(2);
                dgb = rebateRuleService.queryRebateRuleAll(rebateRuleBean, userBean);
            }
        } catch (Exception e) {
            log.error("分页查询活动规则异常：" + e);
        }
        super.writeJson(dgb);
    }

    /**
     * 活动规则添加
     */
    public void saveRebateRule() {
        JsonBean jsonBean = new JsonBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if (userSession == null) {
                jsonBean.setMsg("用户信息已过期");
                jsonBean.setSuccess(false);
            } else {
                boolean isHasName = rebateRuleService.hasRebateRuleByName(rebateRuleBean.getBrrName());
                if (isHasName) {
                    jsonBean.setMsg("活动规则名称已存在,请换一个名称");
                    jsonBean.setSuccess(false);
                } else {
                    UserBean userBean = (UserBean) userSession;
                    rebateRuleService.saveRebateRule(rebateRuleBean, userBean);
                    jsonBean.setSuccess(true);
                    jsonBean.setMsg("添加活动规则成功");
                }
            }
        } catch (Exception e) {
            log.error("添加活动规则常：" + e);
            jsonBean.setMsg("添加活动规则失败");
            jsonBean.setSuccess(false);
        }
        super.writeJson(jsonBean);
    }

    /**
     * 修改活动规则
     */
    public void updateRebateRule() {
        JsonBean jsonBean = new JsonBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if (userSession == null) {
                jsonBean.setMsg("用户信息已过期");
                jsonBean.setSuccess(false);
            } else {
                UserBean userBean = (UserBean) userSession;
                rebateRuleService.saveRebateRule(rebateRuleBean, userBean);
                jsonBean.setSuccess(true);
                jsonBean.setMsg("修改活动规则成功");
            }
        } catch (Exception e) {
            log.error("修改活动规则常：" + e);
            jsonBean.setMsg("修改活动规则失败");
            jsonBean.setSuccess(false);
        }
        super.writeJson(jsonBean);
    }

    /**
     * 活动规则删除
     */
    public void removeRebateRule() {
        JsonBean jsonBean = new JsonBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if (userSession == null) {
                jsonBean.setSuccess(false);
                jsonBean.setMsg("用户信息已过期");
            } else {
                UserBean userBean = (UserBean) userSession;
                rebateRuleService.deleteRebateRuleById(rebateRuleBean.getBrrId(), userBean);
                jsonBean.setSuccess(true);
                jsonBean.setMsg("删除活动规则成功");
            }
        } catch (Exception e) {
            log.error("删除活动规则异常：" + e);
            jsonBean.setMsg("删除活动规则失败");
            jsonBean.setSuccess(false);
        }
        super.writeJson(jsonBean);
    }

    /**
     * 活动规则审核、退回
     */
    public void updateRebateRuleAuditStatus() {
        JsonBean jsonBean = new JsonBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if (userSession == null) {
                jsonBean.setSuccess(false);
                jsonBean.setMsg("用户信息已过期");
            } else {
                UserBean userBean = (UserBean) userSession;
                Integer i = rebateRuleService.updateRebateInfoAuditStatus(rebateRuleBean, userBean);
                if (i == 0) {
                    jsonBean.setSuccess(false);
                    jsonBean.setMsg("审核活动规则失败");
                } else if (i == 1) {
                    jsonBean.setSuccess(true);
                    jsonBean.setMsg("审核活动规则成功");
                }
            }
        } catch (Exception e) {
            log.error("审核活动规则异常：" + e);
            jsonBean.setSuccess(false);
            jsonBean.setMsg("审核活动规则失败");
        }
        super.writeJson(jsonBean);
    }
}
