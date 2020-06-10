package com.hrt.biz.bill.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IMerchantTaskDetailDao;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDataModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail1Model;
import com.hrt.biz.bill.service.IMerchantTaskDetail1Service;
import com.hrt.frame.dao.sysadmin.ITodoDao;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.TodoModel;
import com.hrt.frame.entity.pagebean.UserBean;

public class MerchantTaskDetail1ServiceImpl implements IMerchantTaskDetail1Service{

	private IMerchantTaskDetailDao merchantTaskDetailDao;
	private IUnitDao unitDao;
	private ITodoDao todoDao;
	
	public IMerchantTaskDetailDao getMerchantTaskDetailDao() {
		return merchantTaskDetailDao;
	}
	public void setMerchantTaskDetailDao(IMerchantTaskDetailDao merchantTaskDetailDao) {
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
	

	/**
	 * 对应"商户基本信息工单申请"的action请求
	 */
	public List<MerchantInfoModel> queryMerchantInfo(String mid,UserBean user) { 
		//能够查询所有
		List<MerchantInfoModel> list=new ArrayList<MerchantInfoModel>();
		list.add(merchantTaskDetailDao.queryMerchantInfo(mid,user));
		return list;
	} 
	
	/**
	 * 根据mid判断是否存在未审批的工单
	 */
	public boolean queryMerchantTaskDetailByMid(String mid){
		boolean flag = false;
		String hql ="select count(m) from MerchantTaskDataModel m where m.approveStatus='Z' and m.mid='"+mid+"' and m.type in(1,2,3) ";
		long i = merchantTaskDetailDao.queryCounts(hql);
		if(i>0){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 保存商户提交的基本信息工单申请
	 */
	public void saveMerchantTaskDetail1(MerchantTaskDetail1Model merchantTaskDetail1,String unno,String mid,UserBean user) {
		
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
		merchantTaskDataModel.setType("1");
		merchantTaskDataModel.setApproveStatus("Z");
		merchantTaskDataModel.setMainTainDate(new Date());
		merchantTaskDataModel.setMainTainUId(user.getUserID());
		try { 
			merchantTaskDetailDao.saveObject(merchantTaskDataModel);                 //--->首先保存商户基本信息工单申请关联的“待审”对象
		} catch (Exception e) {
			e.printStackTrace(); 
		}    
		merchantTaskDetail1.setBmtkid(merchantTaskDataModel.getBmtkid()); 
		merchantTaskDetailDao.saveObject(merchantTaskDetail1);
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
		todo.setMsgTopic("待审核的商户工单申请");
		todo.setBatchJobNo(merchantTaskDataModel.getBmtkid().toString());
		todo.setStatus(0); 
		todo.setTranCode("10470");  
		if(!user.getLoginName().equals("superadmin")){
			todoDao.saveObject(todo);
		} 
	}
	
	@Override
	public String queryUpLoadPath() {
		String title="HrtFrameWork";
		return merchantTaskDetailDao.querySavePath(title);
	}
	
	@Override
	public String queryDownloadPath() {
		String title="HrtFrameWork";
		return merchantTaskDetailDao.queryDownloadPath(title);
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

}
