package com.hrt.biz.bill.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hrt.biz.bill.dao.IMerchantTaskDetailDao;
import com.hrt.biz.bill.dao.IMerchantTaskDetailOtherDao;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDataModel;
import com.hrt.biz.bill.service.IMerchantTaskDetailOtherService;

public class MerchantTaskDetailOtherServiceImpl   implements IMerchantTaskDetailOtherService {

	private IMerchantTaskDetailOtherDao merchantTaskDetailOtherDao;
	private IMerchantTaskDetailDao merchantTaskDetailDao;
	
	public IMerchantTaskDetailOtherDao getMerchantTaskDetailOtherDao() {
		return merchantTaskDetailOtherDao;
	}
	public void setMerchantTaskDetailOtherDao(IMerchantTaskDetailOtherDao merchantTaskDetailOtherDao) {
		this.merchantTaskDetailOtherDao = merchantTaskDetailOtherDao;
	}
	public IMerchantTaskDetailDao getMerchantTaskDetailDao() {
		return merchantTaskDetailDao;
	}
	public void setMerchantTaskDetailDao(
			IMerchantTaskDetailDao merchantTaskDetailDao) {
		this.merchantTaskDetailDao = merchantTaskDetailDao;
	}
	
	@Override
	public List<MerchantInfoModel> queryMerchantInfo(String mid) {
		List<MerchantInfoModel> list=new ArrayList<MerchantInfoModel>();
		list.add(merchantTaskDetailOtherDao.queryMerchantInfo(mid));
		return list;
	}
	
	//保存商户4工单申请关联的“待审”对象
	@Override 
	public void saveMerchantTaskDetail4(String unno, String mid, String descr) {
//		DecimalFormat deFormat = new DecimalFormat("000000");
//		String ll=merchantTaskDetailDao.findMaxId();
//		System.out.println(ll+"sequence序列值*************************************");
//		Integer utilBmkid=Integer.parseInt(ll);
//		Integer bmtkid=0;
//		if(utilBmkid==1){
//			bmtkid=1;
//		}else{
//			bmtkid=utilBmkid+1;
//		}
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		MerchantTaskDataModel merchantTaskDataModel =new MerchantTaskDataModel();
		StringBuffer sb=new StringBuffer();
		sb.append(sf.format(new Date())).append("-").append(unno).append("-").append(System.currentTimeMillis());
		merchantTaskDataModel.setTaskId(sb.toString());
		merchantTaskDataModel.setUnno(unno);
		merchantTaskDataModel.setMid(mid);
		merchantTaskDataModel.setTaskContext(descr);
		merchantTaskDataModel.setType("4");
		merchantTaskDataModel.setApproveStatus("Z");
		try { 
			merchantTaskDetailOtherDao.saveObject(merchantTaskDataModel);                
		} catch (Exception e) {
			e.printStackTrace(); 
		}    
	}
	
	/**
	 * 根据mid判断是否存在未审批的工单
	 */
	public boolean queryMerchantTaskDetailByMid(String mid){
		boolean flag = false;
		String hql ="select count(m) from MerchantTaskDataModel m where m.approveStatus='Z' and m.mid='"+mid+"'";
		long i = merchantTaskDetailDao.queryCounts(hql);
		if(i>0){
			flag = true;
		}
		return flag;
	}
	 //保存商户5工单申请关联的“待审”对象
	@Override
	public void saveMerchantTaskDetail5(String unno, String mid, String descr) {
//		DecimalFormat deFormat = new DecimalFormat("000000");
//		String ll=merchantTaskDetailDao.findMaxId();
//		System.out.println(ll+"sequence序列值*************************************");
//		Integer utilBmkid=Integer.parseInt(ll);
//		Integer bmtkid=0;
//		if(utilBmkid==1){
//			bmtkid=1;
//		}else{
//			bmtkid=utilBmkid+1;
//		} 
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		MerchantTaskDataModel merchantTaskDataModel =new MerchantTaskDataModel();
		StringBuffer sb=new StringBuffer();
		sb.append(sf.format(new Date())).append("-").append(unno).append("-").append(System.currentTimeMillis());
		merchantTaskDataModel.setTaskId(sb.toString());
		merchantTaskDataModel.setUnno(unno);
		merchantTaskDataModel.setMid(mid);
		merchantTaskDataModel.setTaskContext(descr);
		merchantTaskDataModel.setType("5");
		merchantTaskDataModel.setApproveStatus("Z");
		try { 
			merchantTaskDetailOtherDao.saveObject(merchantTaskDataModel);                
		} catch (Exception e) {
			e.printStackTrace(); 
		}   
	}
	
	//保存商户6工单申请关联的“待审”对象
	@Override
	public void saveMerchantTaskDetail6(String unno, String mid, String descr) {
//		DecimalFormat deFormat = new DecimalFormat("000000");
//		String ll=merchantTaskDetailDao.findMaxId();
//		System.out.println(ll+"sequence序列值*************************************");
//		Integer utilBmkid=Integer.parseInt(ll);
//		Integer bmtkid=0;
//		if(utilBmkid==1){
//			bmtkid=1;
//		}else{
//			bmtkid=utilBmkid+1;
//		}
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		MerchantTaskDataModel merchantTaskDataModel =new MerchantTaskDataModel();
		StringBuffer sb=new StringBuffer();
		sb.append(sf.format(new Date())).append("-").append(unno).append("-").append(System.currentTimeMillis());
		merchantTaskDataModel.setTaskId(sb.toString());
		merchantTaskDataModel.setUnno(unno);
		merchantTaskDataModel.setMid(mid);
		merchantTaskDataModel.setTaskContext(descr);
		merchantTaskDataModel.setType("6");
		merchantTaskDataModel.setApproveStatus("Z");
		try { 
			merchantTaskDetailOtherDao.saveObject(merchantTaskDataModel);                 //--->首先保存商户基本信息工单申请关联的“待审”对象
		} catch (Exception e) {
			e.printStackTrace(); 
		}   
	}
	 
	//保存商户7工单申请关联的“待审”对象
	@Override
	public void saveMerchantTaskDetail7(String unno, String mid, String descr) {
//		DecimalFormat deFormat = new DecimalFormat("000000");
//		String ll=merchantTaskDetailDao.findMaxId();
//		System.out.println(ll+"sequence序列值*************************************");
//		Integer utilBmkid=Integer.parseInt(ll);
//		Integer bmtkid=0;
//		if(utilBmkid==1){
//			bmtkid=1;
//		}else{
//			bmtkid=utilBmkid+1;
//		}
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		MerchantTaskDataModel merchantTaskDataModel =new MerchantTaskDataModel();
		StringBuffer sb=new StringBuffer();
		sb.append(sf.format(new Date())).append("-").append(unno).append("-").append(System.currentTimeMillis());
		merchantTaskDataModel.setTaskId(sb.toString());
		merchantTaskDataModel.setUnno(unno);
		merchantTaskDataModel.setMid(mid);
		merchantTaskDataModel.setTaskContext(descr);
		merchantTaskDataModel.setType("7");
		merchantTaskDataModel.setApproveStatus("Z");
		try { 
			merchantTaskDetailOtherDao.saveObject(merchantTaskDataModel);                 //--->首先保存商户基本信息工单申请关联的“待审”对象
		} catch (Exception e) {
			e.printStackTrace();  
		}   
	}
}
