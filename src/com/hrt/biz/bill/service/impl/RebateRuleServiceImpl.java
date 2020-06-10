package com.hrt.biz.bill.service.impl;

import com.hrt.biz.bill.dao.IRebateRuleDao;
import com.hrt.biz.bill.entity.model.RebateRuleModel;
import com.hrt.biz.bill.entity.pagebean.RebateRuleBean;
import com.hrt.biz.bill.service.RebateRuleService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RebateRuleServiceImpl
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2020/01/15
 * @since 1.8
 **/
public class RebateRuleServiceImpl implements RebateRuleService {
    private IRebateRuleDao rebateRuleDao;

    public IRebateRuleDao getRebateRuleDao() {
        return rebateRuleDao;
    }

    public void setRebateRuleDao(IRebateRuleDao rebateRuleDao) {
        this.rebateRuleDao = rebateRuleDao;
    }

    @Override
    public boolean hasRebateRuleByName(String brrName){
        String hql="from RebateRuleModel where brrName=? ";
        List<RebateRuleModel> list = rebateRuleDao.queryObjectsByHqlList(hql,new Object[]{brrName});
        return list.size()>0;
    }

    @Override
    public void saveRebateRule(RebateRuleBean rebateRuleBean, UserBean userBean) {
        if(rebateRuleBean.getBrrId()==null) {
            RebateRuleModel model = new RebateRuleModel();
            rebateRuleBean.setAuditStatus(1);
            BeanUtils.copyProperties(rebateRuleBean, model);
            model.setCreateDate(new Date());
            model.setCreateUser(userBean.getUserID()+"");
            rebateRuleDao.saveObject(model);
        }else{
            String hql="from RebateRuleModel where brrId=?";
            RebateRuleModel model = rebateRuleDao.queryObjectByHql(hql,new Object[]{rebateRuleBean.getBrrId()});
            if(model!=null) {
                setFiledValueByBean(model,rebateRuleBean);
                model.setAuditStatus(1);
                model.setUpdateDate(new Date());
                model.setUpdateUser(userBean.getUserID() + "");
                model.setRejectReason(null);
                rebateRuleDao.saveOrUpdateObject(model);
            }
        }
    }

    private void setFiledValueByBean(RebateRuleModel model,RebateRuleBean rebateRuleBean){
        if(rebateRuleBean.getPointType()!=null){
            model.setPointType(rebateRuleBean.getPointType());
        }
        if(rebateRuleBean.getTxnWay()!=null){
            model.setTxnWay(rebateRuleBean.getTxnWay());
        }
        if(rebateRuleBean.getTimeType()!=null){
            model.setTimeType(rebateRuleBean.getTimeType());
        }
        if(rebateRuleBean.getCycle()!=null){
            model.setCycle(rebateRuleBean.getCycle());
        }
        if(rebateRuleBean.getStartTime()!=null){
            model.setStartTime(rebateRuleBean.getStartTime());
        }
        if(rebateRuleBean.getEndTime()!=null){
            model.setEndTime(rebateRuleBean.getEndTime());
        }
        if(rebateRuleBean.getTxnType()!=null){
            model.setTxnType(rebateRuleBean.getTxnType());
        }
        if(rebateRuleBean.getTxnAmount()!=null){
            model.setTxnAmount(rebateRuleBean.getTxnAmount());
        }
        if(rebateRuleBean.getBackType1()!=null){
            model.setBackType1(rebateRuleBean.getBackType1());
        }
        if(rebateRuleBean.getBackAmount1()!=null){
            model.setBackAmount1(rebateRuleBean.getBackAmount1());
        }
        if(rebateRuleBean.getBackType2()!=null){
            model.setBackType2(rebateRuleBean.getBackType2());
        }
        if(rebateRuleBean.getBackAmount2()!=null){
            model.setBackAmount2(rebateRuleBean.getBackAmount2());
        }
        if(rebateRuleBean.getStartMonth()!=null){
            model.setStartMonth(rebateRuleBean.getStartMonth());
        }
        if(rebateRuleBean.getEndMonth()!=null){
            model.setEndMonth(rebateRuleBean.getEndMonth());
        }
        if(rebateRuleBean.getPaymentAmount()!=null){
            model.setPaymentAmount(rebateRuleBean.getPaymentAmount());
        }
        if(rebateRuleBean.getRemark1()!=null){
            model.setRemark1(rebateRuleBean.getRemark1());
        }
        if(rebateRuleBean.getRemark2()!=null){
            model.setRemark2(rebateRuleBean.getRemark2());
        }
    }

    @Override
    public DataGridBean queryRebateRuleAll(RebateRuleBean rebateRuleBean, UserBean userBean) {
        StringBuilder hql=new StringBuilder(32);
        StringBuilder hqlCount=new StringBuilder(32);
        Map map=new HashMap();
        hql.append(" from RebateRuleModel where 1=1 ");

        if(StringUtils.isNotEmpty(rebateRuleBean.getBrrName())){
            hql.append(" and brrName=:brrName ");
            map.put("brrName",rebateRuleBean.getBrrName());
        }
        if(null!=rebateRuleBean.getTxnWay()){
            hql.append(" and txnWay=:txnWay ");
            map.put("txnWay",rebateRuleBean.getTxnWay());
        }
        if(rebateRuleBean.getAuditStatus()!=null){
        	  hql.append(" and auditStatus=:auditStatus ");
              map.put("auditStatus",rebateRuleBean.getAuditStatus());
        }

        hqlCount.append("select count(*) ").append(hql.toString());
        Long count = rebateRuleDao.queryCounts(hqlCount.toString(), map);
        List<RebateRuleModel> list=rebateRuleDao.queryObjectsByHqlList(hql.toString(), map,rebateRuleBean.getPage(),rebateRuleBean.getRows());
        DataGridBean dgb = new DataGridBean();
        dgb.setTotal(count);
        dgb.setRows(list);
        return dgb;
    }

    @Override
    public boolean deleteRebateRuleById(Integer brrId, UserBean userBean) {
        RebateRuleModel rebateRuleModel=new RebateRuleModel();
        rebateRuleModel.setBrrId(brrId);
        rebateRuleDao.deleteObject(rebateRuleModel);
        return true;
    }

	@Override
	public Integer updateRebateInfoAuditStatus(RebateRuleBean rebateRuleBean,
			UserBean userBean) {
		String hql ="from RebateRuleModel where brrId=?";
		RebateRuleModel model = rebateRuleDao.queryObjectByHql(hql,  new Object[]{rebateRuleBean.getBrrId()});
		if(model!=null){
			if(StringUtil.isNotEmpty(rebateRuleBean.getRejectReason())){
				model.setAuditStatus(3);
				model.setRejectReason(rebateRuleBean.getRejectReason());
			}else{
				model.setAuditStatus(2);
			}
			model.setAuditDate(new Date());
			model.setAuditUser(userBean.getLoginName());
			try {
				rebateRuleDao.updateObject(model);
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		return 0;
	}
}
