package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.dao.IMerchantTaskDetail4Dao;
import com.hrt.biz.bill.dao.IMerchantTaskDetailDao;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDataModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail4Model;
import com.hrt.biz.bill.entity.pagebean.MerchantTaskDetail4Bean;
import com.hrt.biz.bill.service.IMerchantTaskDetail4Service;
import com.hrt.frame.dao.sysadmin.ITodoDao;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.TodoModel;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.gmms.webservice.IGmmsService;
import com.hrt.gmms.webservice.MerchantInfo;
import com.hrt.util.ClassToJavaBeanUtil;

public class MerchantTaskDetail4ServiceImpl implements IMerchantTaskDetail4Service {

	private IMerchantTaskDetail4Dao merchantTaskDetail4Dao;
	private IMerchantTaskDetailDao merchantTaskDetailDao;
	private IMerchantInfoDao merchantInfoDao;
	private IUnitDao unitDao;
	private ITodoDao todoDao;
	private static final Log log = LogFactory.getLog(MerchantTaskDetail4ServiceImpl.class);

	public IMerchantInfoDao getMerchantInfoDao() {
		return merchantInfoDao;
	}
	public void setMerchantInfoDao(IMerchantInfoDao merchantInfoDao) {
		this.merchantInfoDao = merchantInfoDao;
	}
	public IMerchantTaskDetail4Dao getMerchantTaskDetail4Dao() {
		return merchantTaskDetail4Dao;
	}
	public void setMerchantTaskDetail4Dao(IMerchantTaskDetail4Dao merchantTaskDetail4Dao) {
		this.merchantTaskDetail4Dao = merchantTaskDetail4Dao;
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
	public IMerchantTaskDetailDao getMerchantTaskDetailDao() {
		return merchantTaskDetailDao;
	}
	public void setMerchantTaskDetailDao(
			IMerchantTaskDetailDao merchantTaskDetailDao) {
		this.merchantTaskDetailDao = merchantTaskDetailDao;
	}
	@Override
	public void saveMerchantRiskTaskData(MerchantTaskDetail4Bean merchantTaskDetail4Bean, UserBean user) {
		MerchantInfoModel mer =merchantInfoDao.queryObjectByHql("from MerchantInfoModel t where t.mid=?", new Object[]{merchantTaskDetail4Bean.getMid()});

		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		MerchantTaskDataModel merchantTaskDataModel =new MerchantTaskDataModel();
		StringBuffer sb=new StringBuffer();
		sb.append(sf.format(new Date())).append("-").append(user.getUnNo()).append("-").append(System.currentTimeMillis());
		merchantTaskDataModel.setTaskId(sb.toString());
		merchantTaskDataModel.setUnno(user.getUnNo());
		merchantTaskDataModel.setMid(merchantTaskDetail4Bean.getMid());
		merchantTaskDataModel.setMainTainDate(new Date());
		merchantTaskDataModel.setType("8");
		merchantTaskDataModel.setApproveStatus("Z");
		merchantTaskDataModel.setMainTainDate(new Date());
		merchantTaskDataModel.setMainTainUId(user.getUserID());
		 //--->首先保存商户银行信息工单申请关联的“待审”对象
		merchantTaskDetailDao.saveObject(merchantTaskDataModel);    
		MerchantTaskDetail4Model m= new MerchantTaskDetail4Model();
		m.setBmtkid(merchantTaskDataModel.getBmtkid());
		m.setIsAuthorized(mer.getSettleCycle());
		m.setMtype(merchantTaskDetail4Bean.getMtype());
		String sql = "select upload_path as path from sys_param where title = 'MerchantScanTask'";
		List<Map<String, Object>>list2 = merchantTaskDetailDao.queryObjectsBySqlObject(sql);
		String imagePathFront = list2.get(0).get("PATH").toString();
		m.setDailyLimit(merchantTaskDetail4Bean.getDailyLimit());
		m.setSingleLimit(merchantTaskDetail4Bean.getSingleLimit());
		//借记卡单日限额
		if(null!=merchantTaskDetail4Bean.getDailyLimit1() && !"".equals(merchantTaskDetail4Bean.getDailyLimit1())){
			m.setDailyLimit1(merchantTaskDetail4Bean.getDailyLimit1());
		}
		//借记卡单笔限额
		if(null!=merchantTaskDetail4Bean.getSingleLimit1() && !"".equals(merchantTaskDetail4Bean.getSingleLimit1())){
			m.setSingleLimit1(merchantTaskDetail4Bean.getSingleLimit1());
		}
		if (merchantTaskDetail4Bean.getActivityImgFile()!=null) {
			String picName = merchantTaskDetail4Bean.getMid()+"_activity.png";
			m.setActivityImg(uploadFile(merchantTaskDetail4Bean.getActivityImgFile(), picName,imagePathFront));
		}
		if (merchantTaskDetail4Bean.getBankCardImgFile()!=null) {
			String picName = merchantTaskDetail4Bean.getMid()+"_bankcard.png";
			m.setBankCardImg(uploadFile(merchantTaskDetail4Bean.getBankCardImgFile(), picName,imagePathFront));
		}
		if (merchantTaskDetail4Bean.getBnoImgFile()!=null) {
			String picName = merchantTaskDetail4Bean.getMid()+"_bno.png";
			m.setBnoImg(uploadFile(merchantTaskDetail4Bean.getBnoImgFile(),picName,imagePathFront));
		}
		if (merchantTaskDetail4Bean.getIdentityImgFile()!=null) {
			String picName = merchantTaskDetail4Bean.getMid()+"_identity.png";
			m.setIdentityImg(uploadFile(merchantTaskDetail4Bean.getIdentityImgFile(),picName,imagePathFront));
		}
		if (merchantTaskDetail4Bean.getOrderImgFile()!=null) {
			String picName = merchantTaskDetail4Bean.getMid()+"_order.png";
			m.setOrderImg(uploadFile(merchantTaskDetail4Bean.getOrderImgFile(), picName,imagePathFront));
		}
		if (merchantTaskDetail4Bean.getOrtherImgFile()!=null) {
			String picName = merchantTaskDetail4Bean.getMid()+"_orther.png";
			m.setOrtherImg(uploadFile(merchantTaskDetail4Bean.getOrtherImgFile(), picName,imagePathFront));
		}
		merchantTaskDetail4Dao.saveObject(m);
		
		//保存工单申请到todo
		TodoModel todo=new TodoModel();
		if(getFatherUnno(user.getUnNo()).equals("110000")){
			todo.setUnNo("110003");
		}else{
			todo.setUnNo(getFatherUnno(user.getUnNo()));
		}
		todo.setMsgSender(user.getUserID());
		todo.setBizType("bill_task");
		todo.setMsgSendTime(new Date());
		todo.setMsgSendUnit(user.getUnNo());
		todo.setMsgTopic("待审核的商户风控工单申请");
		todo.setBatchJobNo(merchantTaskDataModel.getBmtkid().toString());
		todo.setStatus(0);
		todo.setTranCode("10535");
		if(!user.getLoginName().equals("superadmin")){
			todoDao.saveObject(todo);
		} 
	}
	
	
	private String uploadFile(File upload, String fName, String uploadRealPath) {
		String fPath="";
		try {
			String imageDay = getImageDay();
			String realPath = uploadRealPath + File.separator + imageDay;
			File dir = new File(realPath);
			dir.mkdirs();
			fPath = realPath + File.separator + fName;
			File destFile = new File(fPath);
			FileInputStream fis = new FileInputStream(upload);
			FileOutputStream fos = new FileOutputStream(destFile);
			byte [] buffer = new byte[1024];
			int len = 0;
			while((len = fis.read(buffer))>0){
				fos.write(buffer,0,len);
			} 
			fis.close();
			fos.close();
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
		return fPath;
	}
	public String getImageDay() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String name = sdf.format(date);
		return name;
	}
	
	private String getFatherUnno(String unNo) {
		String fUnno="";
		String sql="SELECT t.UPPER_UNIT FROM SYS_UNIT t WHERE t.UNNO='"+unNo+"' ORDER BY t.UPPER_UNIT ";
		List<Map<String, Object>> list=unitDao.queryObjectsBySqlList(sql);
		if(list.size()>0){
			if(list.get(0).get("UPPER_UNIT")!=null){
			fUnno=list.get(0).get("UPPER_UNIT").toString();
			}
		}
		return fUnno;
	}
	
	
	@Override
	public Boolean queryMerchantInfoIsRight(String mid,Integer type, UserBean user) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql ="select count(*) from bill_merchantinfo t where t.mid=:MID and t.unno=:UNNO and t.maintaintype !='D'";
		map.put("MID", mid);
		map.put("UNNO", user.getUnNo());
		if(type==0){
			sql +=" and t.isM35!='1' ";
		}
		Integer count=merchantTaskDetail4Dao.querysqlCounts2(sql, map);
		if(count>0||user.getUnitLvl()<1){
			return true;
		}else{
			return false;
		}

	}
	/* 
	 * 查询该商户是否存在未审批的工单
	 */
	@Override
	public boolean queryMerchantTaskDataByMid(String mid) {
//		merchantTaskDataModel.setType("8");
//		merchantTaskDataModel.setApproveStatus("Z");
		String hql ="select count(m) from MerchantTaskDataModel m where m.approveStatus='Z' and m.mid='"+mid+"' and m.type ='8'";
		long i = merchantTaskDetailDao.queryCounts(hql);
		if(i>0){
			return true;
		}
		return false;
	}
	
	

}
