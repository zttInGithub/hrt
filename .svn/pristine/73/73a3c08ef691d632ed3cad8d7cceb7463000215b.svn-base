package com.hrt.biz.bill.service.impl;

import com.hrt.biz.bill.dao.IRebateInfoDao;
import com.hrt.biz.bill.dao.IRebateRuleInfoDao;
import com.hrt.biz.bill.entity.model.RebateInfoModel;
import com.hrt.biz.bill.entity.model.RebateRuleInfoModel;
import com.hrt.biz.bill.entity.pagebean.RebateInfoBean;
import com.hrt.biz.bill.service.RebateInfoService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RebateInfoServiceImpl
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2020/01/15
 * @since 1.8
 **/
public class RebateInfoServiceImpl implements RebateInfoService {

    private IRebateInfoDao rebateInfoDao;

    public IRebateInfoDao getRebateInfoDao() {
        return rebateInfoDao;
    }

    public void setRebateInfoDao(IRebateInfoDao rebateInfoDao) {
        this.rebateInfoDao = rebateInfoDao;
    }
    
    private IRebateRuleInfoDao rebateRuleInfoDao;
    

	public IRebateRuleInfoDao getRebateRuleInfoDao() {
		return rebateRuleInfoDao;
	}

	public void setRebateRuleInfoDao(IRebateRuleInfoDao rebateRuleInfoDao) {
		this.rebateRuleInfoDao = rebateRuleInfoDao;
	}

	@Override
    public void saveRebateInfo(RebateInfoBean rebateInfoBean, UserBean userBean){
        RebateInfoModel model = new RebateInfoModel();
        BeanUtils.copyProperties(rebateInfoBean, model);
        setFiledDefultValue(model,rebateInfoBean);
        model.setCreateUser(userBean.getUserID() + "");
        model.setAuditStatus(1);
        model.setCreateDate(new Date());
        //标识 0原始状态 1已做修改
        model.setFlag(0);
        //操作标识 0修改 1新增
        model.setOperateType(1);
        rebateInfoDao.saveObject(model);

        String brrIds[] = model.getRemark1().split(",");
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < brrIds.length; i++) {
        	RebateRuleInfoModel ruleInfo = new RebateRuleInfoModel();
        	ruleInfo.setBrId(model.getBrId());
        	ruleInfo.setBrrId(Integer.parseInt(brrIds[i]));
        	ruleInfo.setCreateDate(new Date());
        	ruleInfo.setCreateUser(model.getCreateUser());
        	ruleInfo.setStatus(1);
        	list.add(ruleInfo);
		}
        rebateRuleInfoDao.batchSaveObject(list);
    }

    /**
     * 默认值设置
     * @param rebateInfoModel 保存的活动定义
     * @param rebateInfoBean 页面传参的对象
     */
    private void setFiledDefultValue(RebateInfoModel rebateInfoModel,RebateInfoBean rebateInfoBean){
	    if(rebateInfoModel!=null && rebateInfoBean!=null) {
            if (rebateInfoBean.getDefaultTxnRate() == null) {
                rebateInfoModel.setDefaultTxnRate(new BigDecimal(-1));
            }
            if (rebateInfoBean.getDefaultTxnCashfee() == null) {
                rebateInfoModel.setDefaultTxnCashfee(-1);
            }
            if (rebateInfoBean.getDefaultScanRate() == null) {
                rebateInfoModel.setDefaultScanRate(new BigDecimal(-1));
            }
            if (rebateInfoBean.getDefaultScanRateUp() == null) {
                rebateInfoModel.setDefaultScanRateUp(new BigDecimal(-1));
            }
            if (rebateInfoBean.getDefaultScanCashfee() == null) {
                rebateInfoModel.setDefaultScanCashfee(-1);
            }
            if (rebateInfoBean.getDefaultScanCashfeeUp() == null) {
                rebateInfoModel.setDefaultScanCashfeeUp(-1);
            }
            if (rebateInfoBean.getDefaultScanhbRate() == null) {
                rebateInfoModel.setDefaultScanhbRate(new BigDecimal(-1));
            }
            if (rebateInfoBean.getDefaultScanhbCashfee() == null) {
                rebateInfoModel.setDefaultScanhbCashfee(-1);
            }
            if (rebateInfoBean.getSumMonth() == null) {
                rebateInfoModel.setSumMonth(-1);
            }
            if(rebateInfoBean.getNoCashfeeMonth()==null){
                rebateInfoModel.setNoCashfeeMonth(0);
            }
            if(rebateInfoBean.getCycleMonth()==null){
                rebateInfoModel.setCycleMonth(0);
            }
        }
    }

    private void setFieldValueByBean(RebateInfoBean bean,RebateInfoModel model){
        if(bean.getDefaultTxnRate()!=null){
            model.setDefaultTxnRate(bean.getDefaultTxnRate());
        }
        if(bean.getDefaultTxnCashfee()!=null){
            model.setDefaultTxnCashfee(bean.getDefaultTxnCashfee());
        }
        if(bean.getDefaultScanRate()!=null){
            model.setDefaultScanRate(bean.getDefaultScanRate());
        }
        if(bean.getDefaultScanRateUp()!=null){
            model.setDefaultScanRateUp(bean.getDefaultScanRateUp());
        }
        if(bean.getDefaultScanCashfee()!=null){
            model.setDefaultScanCashfee(bean.getDefaultScanCashfee());
        }
        if(bean.getDefaultScanCashfeeUp()!=null){
            model.setDefaultScanCashfeeUp(bean.getDefaultScanCashfeeUp());
        }
        if(bean.getDefaultScanhbRate()!=null){
            model.setDefaultScanhbRate(bean.getDefaultScanhbRate());
        }
        if(bean.getDefaultScanhbCashfee()!=null){
            model.setDefaultScanhbCashfee(bean.getDefaultScanhbCashfee());
        }
        if(bean.getSumMonth()!=null){
            model.setSumMonth(bean.getSumMonth());
        }
        if(bean.getFlag()!=null){
            model.setFlag(bean.getFlag());
        }
        if(bean.getOperateType()!=null){
            model.setOperateType(bean.getOperateType());
        }
        if(bean.getAuditDate()!=null){
            model.setAuditDate(bean.getAuditDate());
        }
        if(bean.getAuditUser()!=null){
            model.setAuditUser(bean.getAuditUser());
        }
        if(bean.getAuditStatus()!=null){
            model.setAuditStatus(bean.getAuditStatus());
        }
        if(bean.getRejectReason()!=null){
            model.setRejectReason(bean.getRejectReason());
        }
        if(bean.getRebateType()!=null){
            model.setRebateType(bean.getRebateType());
        }
        if(bean.getRebateName()!=null){
            model.setRebateName(bean.getRebateName());
        }
        if(bean.getStartDate()!=null){
            model.setStartDate(bean.getStartDate());
        }
        if(bean.getEndDate()!=null){
            model.setEndDate(bean.getEndDate());
        }
        if(bean.getSettlement()!=null){
            model.setSettlement(bean.getSettlement());
        }
        if(bean.getStatus()!=null){
            model.setStatus(bean.getStatus());
        }
        if(bean.getCardBack()!=null){
            model.setCardBack(bean.getCardBack());
        }
        if(bean.getMerDefine()!=null){
            model.setMerDefine(bean.getMerDefine());
        }
        if(bean.getRemark1()!=null){
            model.setRemark1(bean.getRemark1());
        }
        if(bean.getRemark2()!=null){
            model.setRemark2(bean.getRemark2());
        }
        if(bean.getUnno()!=null){
            model.setUnno(bean.getUnno());
        }
        if(bean.getTradeAmt()!=null){
            model.setTradeAmt(bean.getTradeAmt());
        }
        if(bean.getDepositAmt()!=null){
            model.setDepositAmt(bean.getDepositAmt());
        }
        if(bean.getTxntype()!=null){
            model.setTxntype(bean.getTxntype());
        }
        if(bean.getWalletStatus()!=null){
            model.setWalletStatus(bean.getWalletStatus());
        }
        if(bean.getProdInfo()!=null){
            model.setProdInfo(bean.getProdInfo());
        }
        if(bean.getConfigStatus()!=null){
            model.setConfigStatus(bean.getConfigStatus());
        }
        if(bean.getConfigAmt()!=null){
            model.setConfigAmt(bean.getConfigAmt());
        }
        if(bean.getSpecialType()!=null){
            model.setSpecialType(bean.getSpecialType());
        }
        if(bean.getLimitRate1()!=null){
            model.setLimitRate1(bean.getLimitRate1());
        }
        if(bean.getLimitRate2()!=null){
            model.setLimitRate2(bean.getLimitRate2());
        }
        if(bean.getLimitAmt1()!=null){
            model.setLimitAmt1(bean.getLimitAmt1());
        }
        if(bean.getLimitAmt2()!=null){
            model.setLimitAmt2(bean.getLimitAmt2());
        }
        if(bean.getLimitScanUpRate1()!=null){
            model.setLimitScanUpRate1(bean.getLimitScanUpRate1());
        }
        if(bean.getLimitScanUpRate2()!=null){
            model.setLimitScanUpRate2(bean.getLimitScanUpRate2());
        }
        if(bean.getLimitScanDownRate1()!=null){
            model.setLimitScanDownRate1(bean.getLimitScanDownRate1());
        }
        if(bean.getLimitScanDownRate2()!=null){
            model.setLimitScanDownRate2(bean.getLimitScanDownRate2());
        }
        if(bean.getLimitHbRate1()!=null){
            model.setLimitHbRate1(bean.getLimitHbRate1());
        }
        if(bean.getLimitHbRate2()!=null){
            model.setLimitHbRate2(bean.getLimitHbRate2());
        }
        if(bean.getLimitDeposit()!=null){
            model.setLimitDeposit(bean.getLimitDeposit());
        }
        if(bean.getIfFirCashfee()!=null){
            model.setIfFirCashfee(bean.getIfFirCashfee());
        }
        if(bean.getNoCashfeeMonth()!=null){
            model.setNoCashfeeMonth(bean.getNoCashfeeMonth());
        }
        if(bean.getExeUnno()!=null){
            model.setExeUnno(bean.getExeUnno());
        }
        if(bean.getCycleMonth()!=null){
            model.setCycleMonth(bean.getCycleMonth());
        }
        if(bean.getRemark1()!=null){
            model.setRemark1(bean.getRemark1());
        }
        if(bean.getRemark2()!=null){
            model.setRemark2(bean.getRemark2());
        }
        if(bean.getCardBack()!=null){
            model.setCardBack(bean.getCardBack());
        }
        if(bean.getMerDefine()!=null){
            model.setMerDefine(bean.getMerDefine());
        }
    }

    @Override
    public boolean existsRebateInfoByRebate(String rebateType){
	    String hql="from RebateInfoModel where rebateType=?";
	    return rebateInfoDao.queryObjectsByHqlList(hql,new Object[]{rebateType}).size()>0;
    }

    @Override
    public RebateInfoModel getRebateInfoById(Integer brId){
        return rebateInfoDao.queryObjectByHql(" from RebateInfoModel where brId=?",new Object[]{brId});
    }

    @Override
    public void updateRebateInfo(RebateInfoBean rebateInfoBean,UserBean userBean){
        if(null!=rebateInfoBean.getBrId()) {
            String hqlRebateInfo="from RebateInfoModel where brId=?";
            RebateInfoModel rebateInfoModel=rebateInfoDao.queryObjectByHql(hqlRebateInfo,new Object[]{rebateInfoBean.getBrId()});
            if(rebateInfoModel!=null) {
                setFiledDefultValue(rebateInfoModel,rebateInfoBean);
                setFieldValueByBean(rebateInfoBean,rebateInfoModel);
//                rebateInfoModel.setUpdateUser(userBean.getUserID() + "");
//                rebateInfoModel.setUpdateDate(new Date());
                rebateInfoModel.setAuditStatus(1);
                // 此处为初次新增审核,非审核通过后再次修改
                rebateInfoModel.setRejectReason(null);

//            comment on column bill_rebate_info.flag
//            is '标识 0原始状态 1已做修改';
//            comment on column bill_rebate_info.operate_type
//            is '操作标识 0修改 1新增';
                rebateInfoDao.updateObject(rebateInfoModel);

            }
            String deleteSql ="delete bill_rebaterule_info where brId=:brId";
            Map map = new HashMap();
            map.put("brId",rebateInfoBean.getBrId());
            rebateRuleInfoDao.executeSqlUpdate(deleteSql,map);

            String brrIds[] = rebateInfoModel.getRemark1().split(",");
            List<Object> list = new ArrayList<Object>();
            for (int i = 0; i < brrIds.length; i++) {
            	RebateRuleInfoModel ruleInfo = new RebateRuleInfoModel();
            	ruleInfo.setBrId(rebateInfoModel.getBrId());
            	ruleInfo.setBrrId(Integer.parseInt(brrIds[i]));
            	ruleInfo.setCreateDate(new Date());
            	ruleInfo.setCreateUser(rebateInfoModel.getUpdateUser());
            	ruleInfo.setStatus(1);
            	list.add(ruleInfo);
    		}
            rebateRuleInfoDao.batchSaveObject(list);
        }
    }

    @Override
    public DataGridBean queryRebateInfoAll(RebateInfoBean rebateInfoBean, UserBean userBean) {
        StringBuilder hql=new StringBuilder(32);
        StringBuilder hqlCount=new StringBuilder(32);
        Map map=new HashMap();
        hql.append(" from RebateInfoModel where 1=1 ");

        if(StringUtils.isNotEmpty(rebateInfoBean.getRebateType())){
            hql.append(" and rebateType=:rebateType ");
            map.put("rebateType",rebateInfoBean.getRebateType());
        }
        if(rebateInfoBean.getAuditStatus()!=null){
            hql.append(" and auditStatus=:auditStatus ");
            map.put("auditStatus",rebateInfoBean.getAuditStatus());
        }

        hqlCount.append("select count(*) ").append(hql.toString());
        Long count = rebateInfoDao.queryCounts(hqlCount.toString(), map);
        hql.append(" order by createDate desc ");
        List<RebateInfoModel> list=rebateInfoDao.queryObjectsByHqlList(hql.toString(), map,rebateInfoBean.getPage(),rebateInfoBean.getRows());
        DataGridBean dgb = new DataGridBean();
        dgb.setTotal(count);
        dgb.setRows(list);
        return dgb;
    }

    @Override
    public boolean deleteRebateInfoById(Integer brId, UserBean userBean) {
        String deleteSql ="delete bill_rebate_info where brId=:brId";
        Map map = new HashMap();
        map.put("brId",brId);
        rebateRuleInfoDao.executeSqlUpdate(deleteSql,map);

        RebateInfoModel rebateInfoModel = new RebateInfoModel();
        rebateInfoModel.setBrId(brId);
        rebateInfoDao.deleteObject(rebateInfoModel);
        return true;
    }

	@Override
	public Integer updateRebateInfoAuditStatus(RebateInfoBean rebateInfoBean,
			UserBean userBean) {
		String hql ="from RebateInfoModel where brId=?";
		RebateInfoModel model = rebateInfoDao.queryObjectByHql(hql,  new Object[]{rebateInfoBean.getBrId()});
		if(model!=null){
			if(StringUtil.isNotEmpty(rebateInfoBean.getRejectReason())){
				model.setAuditStatus(3);
				model.setRejectReason(rebateInfoBean.getRejectReason());
			}else{
				model.setAuditStatus(2);
			}
			model.setAuditDate(new Date());
			model.setAuditUser(userBean.getLoginName());
			try {
				rebateInfoDao.updateObject(model);
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		return 0;
	}
	
	//获取S_bill_rebate_info序列
	public Integer queryBillRebateInfoSeq(){
		String sql = "select s_bill_rebate_info.nextval from dual";
		String num = rebateInfoDao.querySequence(sql);
		return Integer.parseInt(num);
	}
	
}
