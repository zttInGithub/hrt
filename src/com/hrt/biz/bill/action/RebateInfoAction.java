package com.hrt.biz.bill.action;

import com.hrt.biz.bill.entity.model.RebateInfoModel;
import com.hrt.biz.bill.entity.pagebean.RebateInfoBean;
import com.hrt.biz.bill.service.RebateInfoService;
import com.hrt.biz.bill.service.RebateRuleInfoService;
import com.hrt.biz.bill.service.RebateRuleService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

import org.apache.commons.lang3.StringUtils;
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
public class RebateInfoAction extends BaseAction implements ModelDriven<RebateInfoBean> {

    private static final Log log = LogFactory.getLog(RebateInfoAction.class);

    private RebateInfoService rebateInfoService;
    private RebateRuleService rebateRuleService;
    private RebateRuleInfoService rebateRuleInfoService;

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

    public RebateInfoBean getRebateInfoBean() {
        return rebateInfoBean;
    }

    public void setRebateInfoBean(RebateInfoBean rebateInfoBean) {
        this.rebateInfoBean = rebateInfoBean;
    }

    private RebateInfoBean rebateInfoBean = new RebateInfoBean();

    @Override
    public RebateInfoBean getModel() {
        return rebateInfoBean;
    }

    /**
     * 活动详情列表
     */
    public void listRebateInfos(){
        DataGridBean dgb = new DataGridBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if(userSession==null){

            }else{
                UserBean userBean= (UserBean) userSession;
                dgb=rebateInfoService.queryRebateInfoAll(rebateInfoBean,userBean);
            }
        } catch (Exception e) {
            log.error("分页查询活动详情异常：" + e);
        }
        super.writeJson(dgb);
    }

    /**
     * 获取活动定义
     */
    public void getRebateInfo(){
        JsonBean jsonBean=new JsonBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if(userSession==null || rebateInfoBean.getBrId()==null){
                jsonBean.setSessionExpire(true);
                jsonBean.setSuccess(false);
                jsonBean.setMsg("用户已过期或请求参数不正确");
            }else{
                RebateInfoModel rebateInfoModel=rebateInfoService.getRebateInfoById(rebateInfoBean.getBrId());
                jsonBean.setSuccess(true);
                jsonBean.setMsg("获取活动定义成功");
                jsonBean.setObj(rebateInfoModel);
            }
        } catch (Exception e) {
            log.error("获取活动定义异常：" + e);
        }
        super.writeJson(jsonBean);
    }

    /**
     * 活动详情添加
     */
    public void saveRebateInfo(){
        JsonBean jsonBean=new JsonBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if(userSession==null){
                jsonBean.setSuccess(false);
                jsonBean.setMsg("用户信息已过期");
            }else{
                if(rebateInfoBean.getBrId()==null) {
                    if(StringUtils.isEmpty(rebateInfoBean.getRemark1())){
                        jsonBean.setSuccess(false);
                        jsonBean.setMsg("未选择规则信息");
                    }else if(rebateInfoService.existsRebateInfoByRebate(rebateInfoBean.getRebateType())){
                        jsonBean.setSuccess(false);
                        jsonBean.setMsg("该活动定义已存在");
                    }else {
                        UserBean userBean = (UserBean) userSession;
                        rebateInfoService.saveRebateInfo(rebateInfoBean,userBean);
                        jsonBean.setSuccess(true);
                        jsonBean.setMsg("添加活动详情成功");
                    }
                }else{
                    jsonBean.setSuccess(false);
                    jsonBean.setMsg("添加活动详情失败");
                }
            }
        } catch (Exception e) {
            log.error("添加活动详情异常：" + e);
            jsonBean.setMsg("添加活动详情失败");
        }
        super.writeJson(jsonBean);
    }

    /**
     * 修改活动详情
     */
    public void editRebateInfo(){
        JsonBean jsonBean=new JsonBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if(userSession==null){
                jsonBean.setSuccess(false);
                jsonBean.setMsg("用户信息已过期");
            }else{
                if(rebateInfoBean.getBrId()!=null) {
                    UserBean userBean = (UserBean) userSession;
                    rebateInfoService.updateRebateInfo(rebateInfoBean,userBean);
                    jsonBean.setSuccess(true);
                    jsonBean.setMsg("修改活动详情成功");
                }else{
                    jsonBean.setSuccess(false);
                    jsonBean.setMsg("修改活动详情失败");
                }
            }
        } catch (Exception e) {
            log.error("修改活动详情异常：" + e);
            jsonBean.setMsg("修改活动详情失败");
        }
        super.writeJson(jsonBean);
    }


    /**
     * 活动定义删除
     */
    public void removeRebateInfo(){
        JsonBean jsonBean=new JsonBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if(userSession==null){
                jsonBean.setSuccess(false);
                jsonBean.setMsg("用户信息已过期");
            }else{
                UserBean userBean= (UserBean) userSession;
                rebateInfoService.deleteRebateInfoById(rebateInfoBean.getBrId(),userBean);
                jsonBean.setSuccess(true);
                jsonBean.setMsg("删除活动详情成功");
            }
        } catch (Exception e) {
            log.error("删除活动详情异常：" + e);
            jsonBean.setMsg("删除活动详情失败");
        }
        super.writeJson(jsonBean);
    }
    
    /**
     * 活动定义审核列表
     * anxinbo 
     * 20200227
     */
    public void listRebateInfoAudits(){
        DataGridBean dgb = new DataGridBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if(userSession==null){
            }else{
                UserBean userBean= (UserBean) userSession;
                dgb=rebateInfoService.queryRebateInfoAll(rebateInfoBean,userBean);
            }
        } catch (Exception e) {
            log.error("分页查询活动详情异常：" + e);
        }
        super.writeJson(dgb);
    }
    
    /**
     * 活动定义审核/退回
     * anxinbo
     * 20200227
     */
    public void updateRebateInfoAuditStatus(){
    	JsonBean jsonBean=new JsonBean();
    	 try {
             Object userSession = super.getRequest().getSession().getAttribute("user");
             if(userSession==null){
                 jsonBean.setSuccess(false);
                 jsonBean.setMsg("用户信息已过期");
             }else{
                 UserBean userBean= (UserBean) userSession;
                 Integer i = rebateInfoService.updateRebateInfoAuditStatus(rebateInfoBean,userBean);
                 if(i==0){
                	 jsonBean.setSuccess(false);
                	 jsonBean.setMsg("审核活动类型失败");
 				}else if(i==1){
 					jsonBean.setSuccess(true);
 					jsonBean.setMsg("审核活动类型成功");
 				}
             }
         } catch (Exception e) {
             log.error("审核活动定义异常：" + e);
             jsonBean.setMsg("审核活动定义失败");
         }
         super.writeJson(jsonBean);
    }
}