package com.hrt.biz.bill.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IMerAdjustRateDao;
import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.entity.model.MerAdjustRateModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.pagebean.MerAdjustRateBean;
import com.hrt.biz.bill.service.IMerAdjustRateService;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.gmms.webservice.IGmmsService;
import com.hrt.gmms.webservice.MerchantInfo;
import com.hrt.util.ClassToJavaBeanUtil;

public class MerAdjustRateServiceImpl implements IMerAdjustRateService {
	
	private IMerchantInfoDao merchantInfoDao;
	private IMerAdjustRateDao adjustRateDao;
	private IUnitDao unitDao;
	private IGmmsService gsClient;
	private IMerchantInfoService merchantInfoService;
	private static final Log log = LogFactory.getLog(MerAdjustRateServiceImpl.class);

	public IGmmsService getGsClient() {
		return gsClient;
	}

	public void setGsClient(IGmmsService gsClient) {
		this.gsClient = gsClient;
	}

	public IMerAdjustRateDao getAdjustRateDao() {
		return adjustRateDao;
	}

	public void setAdjustRateDao(IMerAdjustRateDao adjustRateDao) {
		this.adjustRateDao = adjustRateDao;
	}

	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	public IUnitDao getUnitDao() {
		return unitDao;
	}

	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}

	public IMerchantInfoDao getMerchantInfoDao() {
		return merchantInfoDao;
	}

	public void setMerchantInfoDao(IMerchantInfoDao merchantInfoDao) {
		this.merchantInfoDao = merchantInfoDao;
	}

	public Integer updateHYBAdjustRateInfo (MerAdjustRateBean adjustRateBean)throws Exception{
		Integer qrnum = 1;
		MerchantInfoModel m =merchantInfoDao.queryObjectByHql("from MerchantInfoModel t where t.mid=? and t.approveStatus in ('Y','C') and t.isM35=6 ", new Object[]{adjustRateBean.getMid()});
		if(m==null)return 0;
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, m.getUnno());
		//判断当前登录机构是否已停用
		if(unitModel.getStatus()!=1){
			return 3;
		}
		MerchantInfo info = new MerchantInfo();
		String username = adjustRateBean.getCby();
		Date date = new Date();
		info.setAccountStatus("2");
		info.setIsM35(6);
		info.setMid(adjustRateBean.getMid());
		info.setSettleCycle(adjustRateBean.getSettleCycle());
		if(adjustRateBean.getSettleCycle()==1){
			adjustRateBean.setSettMethod("000000");
			adjustRateBean.setPreSetTime("");
			adjustRateBean.setQuotaAmt(null);
			m.setSettleRange("00");
		}else if("300000".equals(adjustRateBean.getSettMethod())){
			info.setPresettime(adjustRateBean.getPreSetTime());
			m.setSettleRange(adjustRateBean.getPreSetTime());
			adjustRateBean.setQuotaAmt(null);
		}else if ("400000".equals(adjustRateBean.getSettMethod())){
			info.setQuotaamt(adjustRateBean.getQuotaAmt());
			adjustRateBean.setPreSetTime("");
			m.setSettleRange("00");
		}else{
			adjustRateBean.setPreSetTime("");
			adjustRateBean.setQuotaAmt(null);
		}
		info.setSettMethod(adjustRateBean.getSettMethod().substring(0, 1));// 000000--钱包提现  100000--秒到   300000 --定时结算 400000--按金额结算
		if(adjustRateBean.getScanRate()!=null&&!"".equals(adjustRateBean.getScanRate())){
			info.setScanRate(adjustRateBean.getScanRate());
		}
		if(adjustRateBean.getSecondRate()!=null&&!"".equals(adjustRateBean.getSecondRate())){
			info.setSecondRate(adjustRateBean.getSecondRate());
		}
		info.setCby(username);
		info.setSales("1");//业务人员
		info.setMainTenance("1");//维护人员
		info.setMaintainDate(ClassToJavaBeanUtil.convertToXMLGregorianCalendar(date));
		Boolean falg=gsClient.updateMerchantInfo(info,"hrtREX1234.Java");
		if(!falg){
			return 2;
		}else{
			MerAdjustRateModel adjustRateModel = new MerAdjustRateModel();
			BeanUtils.copyProperties(adjustRateBean, adjustRateModel);
			adjustRateModel.setCdate(date);
			adjustRateModel.setCby(username);
			adjustRateModel.setStatus("Y");
			adjustRateDao.saveObject(adjustRateModel);
			m.setSettleCycle(adjustRateBean.getSettleCycle());
			if(adjustRateBean.getScanRate()!=null&&!"".equals(adjustRateBean.getScanRate())){
				m.setScanRate(adjustRateBean.getScanRate());
			}
			if(adjustRateBean.getSecondRate()!=null&&!"".equals(adjustRateBean.getSecondRate())){
				m.setSecondRate(adjustRateBean.getSecondRate()+"");
			}
			if(adjustRateBean.getSettMethod()!=null){
				m.setSettMethod(adjustRateBean.getSettMethod());// 000000--钱包提现  100000--秒到   300000 --定时结算 400000--按金额结算
				if("300000".equals(adjustRateBean.getSettMethod())){
					m.setPreSetTime(adjustRateBean.getPreSetTime());
				}else if ("400000".equals(adjustRateBean.getSettMethod())){
					m.setQuotaAmt(adjustRateBean.getQuotaAmt());
				}
			}
		}
		return qrnum;
	}
}
