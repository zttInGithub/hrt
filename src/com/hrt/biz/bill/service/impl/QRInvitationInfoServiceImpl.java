package com.hrt.biz.bill.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.frame.constant.PhoneProdConstant;
import com.hrt.util.ParamPropUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.dao.IQRInvitationInfoDao;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.QRInvitationInfoModel;
import com.hrt.biz.bill.entity.pagebean.MerchantInfoBean;
import com.hrt.biz.bill.entity.pagebean.QRInvitationInfoBean;
import com.hrt.biz.bill.service.IQRInvitationInfoService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;
import com.hrt.gmms.webservice.IGmmsService;
import com.hrt.phone.service.IPhoneMicroMerchantInfoService;

public class QRInvitationInfoServiceImpl implements IQRInvitationInfoService {
	
	private IQRInvitationInfoDao invitationInfoDao;
	private IMerchantInfoDao merchantInfoDao;
	private IUnitDao unitDao;
	private IGmmsService gsClient;
	private IPhoneMicroMerchantInfoService microMerchantInfoService;
	private static final Log log = LogFactory.getLog(QRInvitationInfoServiceImpl.class);

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

	public IQRInvitationInfoDao getInvitationInfoDao() {
		return invitationInfoDao;
	}

	public void setInvitationInfoDao(IQRInvitationInfoDao invitationInfoDao) {
		this.invitationInfoDao = invitationInfoDao;
	}
	
	public Integer addQRInvitationStatus (QRInvitationInfoBean infoBean,UserBean user)throws Exception{
		Integer result = 0;
		Integer qrnum = Integer.parseInt(infoBean.getQrnum());
		double scanRate = Double.valueOf(infoBean.getScanRate());
		String unno = infoBean.getUnno();
		Date date = new Date();
		Integer userid = user.getUserID();
		String sql = "select max(invitationCode) as code from bill_merQRInvitationInfo ";
		List<Map<String, String>> map = invitationInfoDao.queryObjectsBySqlListMap(sql, null);
		String invitationCode = map.get(0).get("CODE");
		String sql1 = "select count(1)||'' as num from sys_unit where unno ='"+unno+"' ";
		List<Map<String, String>> map1 = invitationInfoDao.queryObjectsBySqlListMap(sql1, null);
		String unitFlag = map1.get(0).get("NUM");
		List<Object> listQRM = new ArrayList<Object>();
		if(invitationCode==null||"".equals(invitationCode)){
			invitationCode="1000000000";
		}
		if(!"0".equals(unitFlag)){
			for(int j=0;j<qrnum;j++){
				QRInvitationInfoModel qrm = new QRInvitationInfoModel();
				String icPassword="";
				for(int i=0;i<4;i++){
					icPassword=icPassword+String.valueOf((int)(Math.random()*10));
				}
				qrm.setUnno(unno);
				qrm.setIcPassword(icPassword);
				qrm.setInvitationCode((Long.parseLong(invitationCode)+1l)+"");
				qrm.setScanRate(scanRate);
				qrm.setMaintainDate(date);
				qrm.setMaintainUserId(userid);
				qrm.setStatus(0);
				listQRM.add(qrm);
				invitationCode=qrm.getInvitationCode();
			}
			invitationInfoDao.batchSaveObject(listQRM);
		}else{
			qrnum=0;
		}
		return qrnum;
	}
	
	@Override
	public Integer updateQRInvitationStatus(QRInvitationInfoBean infoBean)throws Exception{
		Integer result = 01;
		//入账信息
		String hql="from MerchantInfoModel t where t.mid=? and t.bankAccName=? and t.legalNum=? ";
		MerchantInfoModel merchantInfoModel=(MerchantInfoModel) merchantInfoDao.queryObjectByHql(hql, new Object[]{infoBean.getMid(),infoBean.getBankAccName(),infoBean.getBankAccNum()});
		if(merchantInfoModel==null){
			return 00;
		}
		//邀请码信息
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = " SELECT * FROM BILL_MERQRINVITATIONINFO F  WHERE F.INVITATIONCODE=:invitationCode AND F.ICPASSWORD =:icPassword ";// AND F.STATUS=0  ";
		map.put("invitationCode", infoBean.getInvitationCode());
		map.put("icPassword", infoBean.getIcPassword());
		List<QRInvitationInfoModel> list = invitationInfoDao.queryObjectsBySqlList(sql, map,QRInvitationInfoModel.class);
//		List<QRInvitationInfoModel> list = invitationInfoDao.queryObjectsBySqlList(sql, map);
		if(list!=null&&list.size()>0){
			QRInvitationInfoModel model =(QRInvitationInfoModel) list.get(0);
			if(model.getStatus()==1||"1".equals(model.getStatus())){
				return 03;
			}
			model.setStatus(1);
			model.setMid(infoBean.getMid());
			model.setUsedConfirmDate(new Date());
			
			if(merchantInfoModel!=null){
				
				if("000000".equals(merchantInfoModel.getUnno())){
					List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
					merchantInfoModel.setScanRate(model.getScanRate());
					Map<String, Object> map1 =new HashMap<String, Object>();
					map1.put("UNNO", merchantInfoModel.getUnno());
					map1.put("TID", "");
					map1.put("MID", merchantInfoModel.getMid());
					map1.put("SN", "");
					map1.put("merchantInfoModel", merchantInfoModel);
					list1.add(map1);
					merchantInfoModel.setUnno(model.getUnno());
					MerchantInfoBean merchantInfoBean = new MerchantInfoBean();
					BeanUtils.copyProperties(merchantInfoModel, merchantInfoBean);
					if("100000".equals(merchantInfoBean.getSettMethod())){
						merchantInfoBean.setAgentId(PhoneProdConstant.PHONE_MD);
					}else if ("000000".equals(merchantInfoBean.getSettMethod())){
						merchantInfoBean.setAgentId("0");
					}
					microMerchantInfoService.saveHrtMerToADMDB(merchantInfoBean, list1);
				}else{
					String sign="";
					for(int i=0;i<10;i++){
						sign=sign+String.valueOf((char)(Math.random()*26+65));
					}
					merchantInfoModel.setQrUrl(ParamPropUtils.props.get("xpayPath")+"/qrpayment?mid="+infoBean.getMid()+"&sign="+sign);
					merchantInfoModel.setQrUpload("0");
				}
				result=02;
			}
		}
		return result;
	}
	
	public DataGridBean queryMerQRInvitationInfo (QRInvitationInfoBean infoBean,UserBean user)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		DataGridBean dgb = new DataGridBean();
		
		String sql =" select f.* from bill_merQRInvitationInfo f ,sys_unit s where  s.unno=f.unno ";
		if("110000".equals(user.getUnNo())){
			
		}else{
			sql+=" and s.unno ="+user.getUnNo();
		}
		
		if(infoBean.getUnno()!=null&&!"".equals(infoBean.getUnno())){
			sql+=" and f.unno =:unno ";
			map.put("unno", infoBean.getUnno());
		}
		if(infoBean.getMid()!=null&&!"".equals(infoBean.getMid())){
			sql+=" and f.mid =:mid ";
			map.put("mid", infoBean.getMid());
		}
		if(infoBean.getInvitationCode()!=null&&!"".equals(infoBean.getInvitationCode())){
			sql+=" and f.invitationCode =:invitationCode ";
			map.put("invitationCode", infoBean.getInvitationCode());
		}
		if(infoBean.getStatus()!=null&&!"".equals(infoBean.getStatus())){
			sql+=" and f.status =:status ";
			map.put("status", infoBean.getStatus());
		}
		if(infoBean.getCreateDateStart()!=null&&!"".equals(infoBean.getCreateDateStart())){
			sql+=" and f.maintainDate >=:createDateStart ";
			map.put("createDateStart", infoBean.getCreateDateStart());
		}
		if(infoBean.getCreateDateEnd()!=null&&!"".equals(infoBean.getCreateDateEnd())){
			sql+=" and f.maintainDate <=:createDateEnd ";
			map.put("createDateEnd", infoBean.getCreateDateEnd());
		}
		sql+=" order by "+infoBean.getSort()+" desc ";
		List<Map<String, String>> qrList = invitationInfoDao.queryObjectsBySqlList(sql, map, infoBean.getPage(), infoBean.getRows());
		String sqlCount = " select count(1) from ("+sql+")";
		BigDecimal counts = invitationInfoDao.querysqlCounts(sqlCount, map);
		dgb.setRows(qrList);
		dgb.setTotal(counts.intValue());
		return dgb;
	}
	
	public HSSFWorkbook exportMerQRAll(QRInvitationInfoBean infoBean,UserBean user){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql =" select f.* from bill_merQRInvitationInfo f ,sys_unit s where f.unno=s.unno ";
		if("110000".equals(user.getUnNo())){
			
		}else{
			sql+=" and s.unno ="+user.getUnNo();
		}
		
		if(infoBean.getUnno()!=null&&!"".equals(infoBean.getUnno())){
			sql+=" and f.unno =:unno ";
			map.put("unno", infoBean.getUnno());
		}
		if(infoBean.getMid()!=null&&!"".equals(infoBean.getMid())){
			sql+=" and f.mid =:mid ";
			map.put("mid", infoBean.getMid());
		}
		if(infoBean.getInvitationCode()!=null&&!"".equals(infoBean.getInvitationCode())){
			sql+=" and f.invitationCode =:invitationCode ";
			map.put("invitationCode", infoBean.getInvitationCode());
		}
		if(infoBean.getStatus()!=null&&!"".equals(infoBean.getStatus())){
			sql+=" and f.status =:status ";
			map.put("status", infoBean.getStatus());
		}
		if(infoBean.getCreateDateStart()!=null&&!"".equals(infoBean.getCreateDateStart())){
			sql+=" and f.maintainDate >=:createDateStart ";
			map.put("createDateStart", infoBean.getCreateDateStart());
		}
		if(infoBean.getCreateDateEnd()!=null&&!"".equals(infoBean.getCreateDateEnd())){
			sql+=" and f.maintainDate <=:createDateEnd ";
			map.put("createDateEnd", infoBean.getCreateDateEnd());
		}
		
		List<Map<String, Object>> data = invitationInfoDao.queryObjectsBySqlListMap2(sql, map);
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		
		excelHeader.add("UNNO");
		excelHeader.add("MID");
		excelHeader.add("INVITATIONCODE");
		excelHeader.add("ICPASSWORD");
		excelHeader.add("SCANRATE");
		excelHeader.add("STATUS");
		excelHeader.add("USEDCONFIRMDATE");
		excelHeader.add("MAINTAINDATE");
		
		headMap.put("UNNO", "机构号");
		headMap.put("MID", "绑定商户编号");
		headMap.put("INVITATIONCODE", "邀请码");
		headMap.put("ICPASSWORD", "密码");
		headMap.put("SCANRATE", "扫码费率");
		headMap.put("STATUS", "使用状态(0未使用;1已使用;2停用)");
		headMap.put("USEDCONFIRMDATE", "使用时间");
		headMap.put("MAINTAINDATE", "生产时间");
		
		return ExcelUtil.export("入网资料", data, excelHeader, headMap);
	}

	@Override
	public Integer updateQRIUnno(QRInvitationInfoBean infoBean, String icids,UserBean user) throws Exception {
		
		String sql1 = "select count(1)||'' as num from sys_unit where unno ='"+infoBean.getUnno()+"' and unno in " +
				"(select UNNO from sys_unit start with unno ='"+user.getUnNo()+"' and status=1 connect by NOCYCLE prior  unno =  upper_unit)";
		List<Map<String, String>> map1 = invitationInfoDao.queryObjectsBySqlListMap(sql1, null);
		String unitFlag = map1.get(0).get("NUM");
		Integer result = 0;
		if(!"0".equals(unitFlag)){
			String sql ="update bill_merQRInvitationInfo f set" +
					" f.unno='"+infoBean.getUnno()+"',f.maintainDate=sysdate,f.maintainUserId="+user.getUserID()+" " +
					" where f.status=0 and f.icid in ("+icids+")";
			result = invitationInfoDao.executeSqlUpdate(sql, null);
		}
		
		return result;
	}
	
}
