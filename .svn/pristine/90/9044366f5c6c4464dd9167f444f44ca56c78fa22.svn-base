package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IMerchantTaskDetail5Dao;
import com.hrt.biz.bill.dao.IMerchantTaskDetailDao;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDataModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail5Model;
import com.hrt.biz.bill.entity.pagebean.MerchantTaskDetail5Bean;
import com.hrt.biz.bill.service.IMerchantTaskDetail5Service;
import com.hrt.frame.dao.sysadmin.ITodoDao;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.TodoModel;
import com.hrt.frame.entity.pagebean.UserBean;

public class MerchantTaskDetail5ServiceImpl implements IMerchantTaskDetail5Service {

	private IMerchantTaskDetail5Dao merchantTaskDetail5Dao;
	private IMerchantTaskDetailDao merchantTaskDetailDao;
	private IUnitDao unitDao;
	private ITodoDao todoDao;
	
	
	public IMerchantTaskDetail5Dao getMerchantTaskDetail5Dao() {
		return merchantTaskDetail5Dao;
	}
	public void setMerchantTaskDetail5Dao(
			IMerchantTaskDetail5Dao merchantTaskDetail5Dao) {
		this.merchantTaskDetail5Dao = merchantTaskDetail5Dao;
	}
	public IMerchantTaskDetailDao getMerchantTaskDetailDao() {
		return merchantTaskDetailDao;
	}
	public void setMerchantTaskDetailDao(
			IMerchantTaskDetailDao merchantTaskDetailDao) {
		this.merchantTaskDetailDao = merchantTaskDetailDao;
	}
	public IUnitDao getUnitDao() {
		return unitDao;
	}
	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}
	public ITodoDao getTodoDao() {
		return todoDao;
	}
	public void setTodoDao(ITodoDao todoDao) {
		this.todoDao = todoDao;
	}


	@Override
	public void saveMerchantTaskDetail5Data(MerchantTaskDetail5Bean merchantTaskDetail5Bean, UserBean user) {
//		DecimalFormat deFormat = new DecimalFormat("000000");
//		String ll=merchantTaskDetailDao.findMaxId();
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
		sb.append(sf.format(new Date())).append("-").append(user.getUnNo()).append("-").append(System.currentTimeMillis());
		merchantTaskDataModel.setTaskId(sb.toString());
		merchantTaskDataModel.setUnno(user.getUnNo());
		merchantTaskDataModel.setMid(merchantTaskDetail5Bean.getMid());
		merchantTaskDataModel.setMainTainDate(new Date());
		merchantTaskDataModel.setType("9");
		merchantTaskDataModel.setApproveStatus("Z");
		merchantTaskDataModel.setMainTainDate(new Date());
		merchantTaskDataModel.setMainTainUId(user.getUserID());
		 //--->首先保存商户银行信息工单申请关联的“待审”对象
		merchantTaskDetailDao.saveObject(merchantTaskDataModel);    
		MerchantTaskDetail5Model m= new MerchantTaskDetail5Model();
		m.setBmtkid(merchantTaskDataModel.getBmtkid());
		m.setBankAccName(merchantTaskDetail5Bean.getBankAccName());
		m.setLegalNum(merchantTaskDetail5Bean.getLegalNum());
		//上传文件
		if(merchantTaskDetail5Bean.getLegalPositiveFile() != null ){
			StringBuffer fName1 = new StringBuffer();
			fName1.append(user.getUnNo());
			fName1.append(".");
			fName1.append(merchantTaskDetail5Bean.getMid());
			fName1.append(".");
			fName1.append(new SimpleDateFormat("yyyyMMdd").format(new Date()).toString());
			fName1.append(".1");
			fName1.append(".jpg");
			uploadFile(merchantTaskDetail5Bean.getLegalPositiveFile(),fName1.toString(),user.getUnNo());
			m.setLegalPositiveName(fName1.toString());
		}
		
		if(merchantTaskDetail5Bean.getLegalReverseFile() != null){
			StringBuffer fName2 = new StringBuffer();
			fName2.append(user.getUnNo());
			fName2.append(".");
			fName2.append(merchantTaskDetail5Bean.getMid());
			fName2.append(".");
			fName2.append(new SimpleDateFormat("yyyyMMdd").format(new Date()).toString());
			fName2.append(".2");
			fName2.append(".jpg");
			uploadFile(merchantTaskDetail5Bean.getLegalReverseFile(),fName2.toString(),user.getUnNo());
			m.setLegalReverseName(fName2.toString());
		}
		
		if(merchantTaskDetail5Bean.getLegalHandFile() != null ){
			StringBuffer fName3 = new StringBuffer();
			fName3.append(user.getUnNo());
			fName3.append(".");
			fName3.append(merchantTaskDetail5Bean.getMid());
			fName3.append(".");
			fName3.append(new SimpleDateFormat("yyyyMMdd").format(new Date()).toString());
			fName3.append(".3");
			fName3.append(".jpg");
			uploadFile(merchantTaskDetail5Bean.getLegalHandFile(),fName3.toString(),user.getUnNo());
			m.setLegalHandName(fName3.toString());
		}
		merchantTaskDetail5Dao.saveObject(m);
		
		//保存工单申请到todo
		TodoModel todo=new TodoModel();
		todo.setUnNo("110003");
		todo.setMsgSender(user.getUserID());
		todo.setBizType("bill_task");
		todo.setMsgSendTime(new Date());
		todo.setMsgSendUnit(user.getUnNo());
		todo.setMsgTopic("待审核的商户风控工单申请");
		todo.setBatchJobNo(merchantTaskDataModel.getBmtkid().toString());
		todo.setStatus(0);
		todo.setTranCode("10540");
		if(!user.getLoginName().equals("superadmin")){
			todoDao.saveObject(todo);
		} 
	}
	
	
	/**
	 * 上传
	 */
	private void uploadFile(File upload, String fName, String unno) {
		try {
			String realPath = queryUpLoadPath() + File.separator + unno;
			File dir = new File(realPath);
			dir.mkdirs();
			String fPath = realPath + File.separator + fName;
			File destFile = new File(fPath);
			FileInputStream fis = new FileInputStream(upload);
			FileOutputStream fos = new FileOutputStream(destFile);
			byte [] buffer = new byte[1024];
			int len = 0;
			while((len = fis.read(buffer))>0){
				fos.write(buffer,0,len);
			} 
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询上传路径
	 */
	private String queryUpLoadPath() {
		String title="HrtFrameWork";
		return merchantTaskDetailDao.querySavePath(title);
	}
	
	@Override
	public boolean findMidInfo(String mid) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql ="select mid from bill_merchantInfo t where t.mid=:mid";
		map.put("mid", mid);
		Object aa =merchantTaskDetailDao.queryObjectBySql(sql, map);
		if(aa!=null && !"".equals(aa)){
			return true;
		}
		return false;
	}
	
	@Override
	public List<Map<String, Object>> findMidStatusInfo(String mid) {
		String sql ="select  APPROVESTATUS,nvl(PROCESSCONTEXT,' ') as PROCESSCONTEXT  from bill_merchanttaskdata t where t.type=9  and t.mid='"+mid+"' order by bmtkid desc";
		List<Map<String,Object>> list = merchantTaskDetailDao.queryObjectsBySqlList2(sql, null, 1, 1);
		return list;
	}
	@Override
	public boolean queryMerchantIsMicro(String mid) {
		String hql="from MerchantInfoModel t where t.mid=?";
		MerchantInfoModel m=(MerchantInfoModel) merchantTaskDetailDao.queryObjectByHql(hql, new Object[]{mid});
		if(m.getIsM35()==1){
			return false;
		}
		return true;
	}
	

}
