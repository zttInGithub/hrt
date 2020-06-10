package com.hrt.biz.bill.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.dao.IAggPayTerminfoDao;
import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.entity.model.AggPayTerminfoModel;
import com.hrt.biz.bill.entity.pagebean.AggPayTerminfoBean;
import com.hrt.biz.bill.service.IAggPayTerminfoService;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.gmms.webservice.AggPayTerm;
import com.hrt.gmms.webservice.IGmmsService;
import com.hrt.phone.service.IPhoneMicroMerchantInfoService;

public class AggPayTerminfoServiceImpl implements IAggPayTerminfoService {
	
	private IAggPayTerminfoDao aggPayTerminfoDao;
	private IMerchantInfoDao merchantInfoDao;
	private IUnitDao unitDao;
	private IGmmsService gsClient;
	private IPhoneMicroMerchantInfoService microMerchantInfoService;
	private IMerchantInfoService merchantInfoService;
	private static final Log log = LogFactory.getLog(AggPayTerminfoServiceImpl.class);

	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	public IPhoneMicroMerchantInfoService getMicroMerchantInfoService() {
		return microMerchantInfoService;
	}

	public void setMicroMerchantInfoService(
			IPhoneMicroMerchantInfoService microMerchantInfoService) {
		this.microMerchantInfoService = microMerchantInfoService;
	}

	public IUnitDao getUnitDao() {
		return unitDao;
	}

	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}

	public IGmmsService getGsClient() {
		return gsClient;
	}

	public void setGsClient(IGmmsService gsClient) {
		this.gsClient = gsClient;
	}

	public IMerchantInfoDao getMerchantInfoDao() {
		return merchantInfoDao;
	}

	public void setMerchantInfoDao(IMerchantInfoDao merchantInfoDao) {
		this.merchantInfoDao = merchantInfoDao;
	}
	
	public IAggPayTerminfoDao getAggPayTerminfoDao() {
		return aggPayTerminfoDao;
	}

	public void setAggPayTerminfoDao(IAggPayTerminfoDao aggPayTerminfoDao) {
		this.aggPayTerminfoDao = aggPayTerminfoDao;
	}
	
	public void updateAggPayTerminalinfosY (AggPayTerminfoBean infoBean,UserBean user,String [] ids)throws Exception{
		for(int i = 0; i < ids.length; i++){
			AggPayTerminfoModel term =aggPayTerminfoDao.queryObjectByHql("from AggPayTerminfoModel t where t.bapid="+ids[i], new Object[]{});
			String yidai = merchantInfoService.getProvince(term.getUnno());
			AggPayTerm payTermAcc = new AggPayTerm();
			payTermAcc.setUnno(yidai);
			payTermAcc.setCby("SYS");
			payTermAcc.setMid(term.getMid());
			payTermAcc.setQrtid(term.getQrtid());
			payTermAcc.setBapid(term.getBapid());
			payTermAcc.setShopname(term.getShopname());
			
			term.setApproveDate(new Date());
			term.setStatus(2);
			term.setApproveId(544924);

			Boolean falg2 = false ;
			try{
				log.info("聚合终端审批(业务):mid="+payTermAcc.getMid()+";Qrtid="+payTermAcc.getQrtid()+";typeflag="+payTermAcc.getTypeflag());
				falg2=gsClient.saveAggPayTermSub(payTermAcc,"hrtREX1234.Java");
			}catch(Exception e ){
				log.error("聚合商户推送终端调用webservice失败：" + e);
			}
			if(!falg2){
				term.setStatus(1);
				log.error("调用webservice失败：");
				//throw new IllegalAccessError("调用webservice失败");
			}
		}
	}

	/**
	 * 查询聚合终端不分页
	 */
	@Override
	public List<Map<String,String>> queryAggPayTerminalInfoBmidPhone(AggPayTerminfoBean aggPayTerminfoBean)throws Exception {
		String sql = " select f.*,m.rname from BILL_aggpayterminfo f,bill_merchantinfo m where m.mid=f.mid and f.mid=:mid ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mid", aggPayTerminfoBean.getMid());
		
		if(aggPayTerminfoBean.getQrtid() != null && !"".equals(aggPayTerminfoBean.getQrtid())){
			sql += "  and tid = :tid ";
			map.put("tid", aggPayTerminfoBean.getQrtid());
		}
		
		List<Map<String,String>> result = aggPayTerminfoDao.queryObjectsBySqlListMap(sql, map);
		return result;
	}
	
	public DataGridBean queryAggPayTerminfoZ (AggPayTerminfoBean infoBean,UserBean user)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		DataGridBean dgb = new DataGridBean();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		String sql =" select f.* from BILL_aggpayterminfo f,bill_merchantinfo m  where 1=1 and m.mid = f.mid and m.approvestatus in ('C','Y') and m.maintaintype !='D' ";
		if("110000".equals(user.getUnNo())){
			
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and f.unno ="+user.getUnNo();
		}
		if(infoBean.getUnno()!=null&&!"".equals(infoBean.getUnno())){
			sql+=" and f.unno =:unno ";
			map.put("unno", infoBean.getUnno());
		}
		if(infoBean.getMid()!=null&&!"".equals(infoBean.getMid())){
			sql+=" and f.mid =:mid ";
			map.put("mid", infoBean.getMid());
		}
		if(infoBean.getOrderid()!=null&&!"".equals(infoBean.getOrderid())){
			sql+=" and f.orderid =:orderid ";
			map.put("orderid", infoBean.getOrderid());
		}
		if(infoBean.getQrtid()!=null&&!"".equals(infoBean.getQrtid())){
			sql+=" and f.qrtid >= '"+infoBean.getQrtid()+"'";
		}
		if(infoBean.getQrtid1()!=null&&!"".equals(infoBean.getQrtid1())){
			sql+=" and f.qrtid <= '"+infoBean.getQrtid1()+"'";
		}
		if(infoBean.getStatus()!=null&&!"".equals(infoBean.getStatus())){
			sql+=" and f.status in ("+infoBean.getStatus()+") ";
		}
		if(infoBean.getStatus1()!=null&&!"".equals(infoBean.getStatus1())){
			sql+=" and f.status in ("+infoBean.getStatus1()+") ";
		}
		if(infoBean.getMaintainDate()!=null&&!"".equals(infoBean.getMaintainDate())){
			sql+=" and trunc(f.maintainDate) >=trunc(:createDateStart) ";
			map.put("createDateStart", infoBean.getMaintainDate());
		}
		if(infoBean.getMaintainDate2()!=null&&!"".equals(infoBean.getMaintainDate2())){
			sql+=" and trunc(f.maintainDate) <=trunc(:createDateEnd) ";
			map.put("createDateEnd", infoBean.getMaintainDate2());
		}
		sql+=" order by f.qrtid asc ";
		List<Map<String, String>> qrList = aggPayTerminfoDao.queryObjectsBySqlList(sql, map, infoBean.getPage(), infoBean.getRows());
		String sqlCount = " select count(1) from ("+sql+")";
		BigDecimal counts = aggPayTerminfoDao.querysqlCounts(sqlCount, map);
		dgb.setRows(qrList);
		dgb.setTotal(counts.intValue());
		return dgb;
	}
}
