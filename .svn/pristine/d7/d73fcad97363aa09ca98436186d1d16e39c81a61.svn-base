package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.dao.IMerchantTaskOperateDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDataModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail1Model;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail2Model;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail3Model;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail4Model;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail5Model;
import com.hrt.biz.bill.entity.pagebean.MerchantTaskDataBeanX;
import com.hrt.biz.bill.entity.pagebean.MerchantTaskDetail5Bean;
import com.hrt.biz.bill.service.IMerchantTaskOperateService;
import com.hrt.frame.dao.sysadmin.ITodoDao;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.dao.sysadmin.IUserDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.model.UserModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class MerchantTaskOperateServiceImpl implements IMerchantTaskOperateService{
	
	private IMerchantTaskOperateDao merchantTaskOperateDao;
	private IUnitDao unitDao;
	private ITodoDao todoDao;
	private IAgentSalesDao agentSalesDao;
	private IUserDao userDao;
	
	public IMerchantTaskOperateDao getMerchantTaskOperateDao() {
		return merchantTaskOperateDao;
	}

	public void setMerchantTaskOperateDao(IMerchantTaskOperateDao merchantTaskOperateDao) {
		this.merchantTaskOperateDao = merchantTaskOperateDao;
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
	public IAgentSalesDao getAgentSalesDao() {
		return agentSalesDao;
	}
	public void setAgentSalesDao(IAgentSalesDao agentSalesDao) {
		this.agentSalesDao = agentSalesDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 方法功能：格式化AgentSalesModel为datagrid数据格式
	 * 参数：agentSalesList
	 *     total 总记录数
	 * 返回值：
	 * 异常：
	 */
	@SuppressWarnings("unused")
	private DataGridBean formatToDataGrid(List<Object> o, long total) {
		List<MerchantTaskDataBeanX> merchantTaskDataBeanList = new ArrayList<MerchantTaskDataBeanX>();
		List<MerchantTaskDataModel> list=new ArrayList<MerchantTaskDataModel>();
		for(Object util :o){
			MerchantTaskDataModel mm=(MerchantTaskDataModel) util;
			list.add(mm);
		} 
		for(MerchantTaskDataModel mm : list) {
			MerchantTaskDataBeanX merchantTaskDataBean = new MerchantTaskDataBeanX();
			BeanUtils.copyProperties(mm, merchantTaskDataBean);
			MerchantInfoModel merchantInfoModel=(MerchantInfoModel)merchantTaskOperateDao.queryObjectByHql("from MerchantInfoModel m where m.mid=?", new Object[]{mm.getMid()});
			if(merchantInfoModel!=null){
				merchantTaskDataBean.setRname(merchantInfoModel.getRname());
			}
			if(merchantTaskDataBean.getApproveUId()!=null){
				UserModel user= userDao.getObjectByID(UserModel.class,merchantTaskDataBean.getApproveUId());
				if(user!=null){
					merchantTaskDataBean.setApproveName(user.getLoginName());
				}
			}
			merchantTaskDataBeanList.add(merchantTaskDataBean);
		}
		
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total); 
		dgb.setRows(merchantTaskDataBeanList);
		return dgb;
	}
	
	public DataGridBean queryMerchantTaskData(UserBean user, String approveStatus,Integer page, Integer rows, String mid,String startDay, String endDay) {
		
		
		String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		Map<String, Object> usermap = new HashMap<String, Object>();
		usermap.put("userID", user.getUserID().toString());
		usermap.put("maintainType", "D");
		List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		String sql = "";
		String sqlCount = "";
		if(agentSalesModels.size()==0){
			if("110000".equals(user.getUnNo())){
				sql = "SELECT * FROM BILL_MERCHANTTASKDATA WHERE 1 = 1 and type!=8 ";
				sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTTASKDATA WHERE 1 = 1 and type!=8 ";
			}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
					sql = "SELECT * FROM BILL_MERCHANTTASKDATA WHERE 1 = 1 and type!=8 ";
					sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTTASKDATA WHERE 1 = 1 and type!=8 ";
				}
			}else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){
				sql = "SELECT * FROM BILL_MERCHANTTASKDATA WHERE 1 = 1 AND UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = '"+user.getUnNo()+"' OR UNNO = '"+user.getUnNo()+"' AND STATUS = 1) and type!=8 ";
				sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTTASKDATA WHERE 1 = 1 AND UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = '"+user.getUnNo()+"' OR UNNO = '"+user.getUnNo()+"' AND STATUS = 1) and type!=8 ";
			}else{
				sql = "SELECT * FROM BILL_MERCHANTTASKDATA WHERE 1 = 1 AND UNNO = '"+user.getUnNo()+"' and type!=8 ";
				sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTTASKDATA WHERE 1 = 1 AND UNNO = '"+user.getUnNo()+"' and type!=8 ";
			}
		}else{
			sql = "SELECT * FROM BILL_MERCHANTTASKDATA WHERE 1 = 1 AND MID IN (SELECT MID FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = '"+agentSalesModels.get(0).getBusid()+"' AND MAINTAINTYPE != 'D') and type!=8 ";
			sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTTASKDATA WHERE 1 = 1 AND MID IN (SELECT MID FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = '"+agentSalesModels.get(0).getBusid()+"' AND MAINTAINTYPE != 'D') and type!=8 ";
		}
		if(approveStatus != null && !"".equals(approveStatus)){
			sql += " AND APPROVESTATUS = '"+approveStatus+"'";
			sqlCount +=" AND APPROVESTATUS = '"+approveStatus+"'";
		}
		if(mid != null && !"".equals(mid)){
			sql+= " AND MID like '"+mid+"%'";
			sqlCount+=" AND MID like '"+mid+"%'";
		} 
		if(startDay != null && !"".equals(startDay)){
			sql+= " AND to_char(APPROVEDATE,'yyyy-MM-dd') >= '"+startDay+"'";
			sqlCount+=" AND to_char(APPROVEDATE,'yyyy-MM-dd') >= '"+startDay+"'";
		} 
		if(endDay != null && !"".equals(endDay)){
			sql+= " AND to_char(APPROVEDATE,'yyyy-MM-dd') <= '"+endDay+"'";
			sqlCount+=" AND to_char(APPROVEDATE,'yyyy-MM-dd') <= '"+endDay+"'";
		}
		sql+= " ORDER BY BMTKID DESC";
		BigDecimal counts = merchantTaskOperateDao.querysqlCounts(sqlCount, null);
		List<Object> list=merchantTaskOperateDao.queryObjectsBySqlLists(sql, null, page, rows, MerchantTaskDataModel.class);
		
		return formatToDataGrid(list,counts.longValue());
		
	}

	public DataGridBean queryMerchantRiskTaskData(UserBean user, String approveStatus,Integer page, Integer rows, String mid,String startDay, String endDay,String mtype) {
		
		
		String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		Map<String, Object> usermap = new HashMap<String, Object>();
		usermap.put("userID", user.getUserID().toString());
		usermap.put("maintainType", "D");
		List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		String sql = "";
		String sqlCount = "";
		if(agentSalesModels.size()==0){
			if("110000".equals(user.getUnNo())){
				sql = "SELECT a.* FROM BILL_MERCHANTTASKDATA a,bill_merchanttaskdetail4 b WHERE 1 = 1 and a.type=8 ";
				sqlCount = "SELECT COUNT(*) from BILL_MERCHANTTASKDATA a,bill_merchanttaskdetail4 b WHERE 1 = 1 and a.type=8 ";
			}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
					sql = "SELECT a.* from BILL_MERCHANTTASKDATA a,bill_merchanttaskdetail4 b WHERE 1 = 1 and a.type=8 ";
					sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTTASKDATA a,bill_merchanttaskdetail4 b WHERE 1 = 1 and a.type=8 ";
				}
			}else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){
				sql = "SELECT a.* FROM BILL_MERCHANTTASKDATA a,bill_merchanttaskdetail4 b WHERE 1 = 1 AND a.UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = '"+user.getUnNo()+"' OR UNNO = '"+user.getUnNo()+"' AND STATUS = 1) and a.type=8 ";
				sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTTASKDATA a,bill_merchanttaskdetail4 b WHERE 1 = 1 AND a.UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = '"+user.getUnNo()+"' OR UNNO = '"+user.getUnNo()+"' AND STATUS = 1) and a.type=8 ";
			}else{
				sql = "SELECT a.* FROM BILL_MERCHANTTASKDATA a,bill_merchanttaskdetail4 b WHERE 1 = 1 AND a.UNNO = '"+user.getUnNo()+"' and a.type=8 ";
				sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTTASKDATA a,bill_merchanttaskdetail4 b WHERE 1 = 1 AND a.UNNO = '"+user.getUnNo()+"' and a.type=8 ";
			}
		}else{
			sql = "SELECT a.* FROM BILL_MERCHANTTASKDATA a,bill_merchanttaskdetail4 b WHERE 1 = 1 AND a.MID IN (SELECT MID FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = '"+agentSalesModels.get(0).getBusid()+"' AND MAINTAINTYPE != 'D') and a.type=8 ";
			sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTTASKDATA a,bill_merchanttaskdetail4 b WHERE 1 = 1 AND a.MID IN (SELECT MID FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = '"+agentSalesModels.get(0).getBusid()+"' AND MAINTAINTYPE != 'D') and a.type=8  ";
		}
		sql+=" and a.bmtkid = b.bmtkid ";
		sqlCount+=" and a.bmtkid = b.bmtkid ";
		
		
		if(approveStatus != null && !"".equals(approveStatus)){
			sql += " AND a.APPROVESTATUS = '"+approveStatus+"'";
			sqlCount +=" AND a.APPROVESTATUS = '"+approveStatus+"'";
		}
		if(mid != null && !"".equals(mid)){
			sql+= " AND a.MID = '"+mid+"'";
			sqlCount+=" AND a.MID = '"+mid+"'";
		} 
		if(startDay != null && !"".equals(startDay)){
			sql+= " AND to_char(a.APPROVEDATE,'yyyy-MM-dd') >= '"+startDay+"'";
			sqlCount+=" AND to_char(a.APPROVEDATE,'yyyy-MM-dd') >= '"+startDay+"'";
		} 
		if(endDay != null && !"".equals(endDay)){
			sql+= " AND to_char(a.APPROVEDATE,'yyyy-MM-dd') <= '"+endDay+"'";
			sqlCount+=" AND to_char(a.APPROVEDATE,'yyyy-MM-dd') <= '"+endDay+"'";
		}
		if(mtype != null && !"".equals(mtype)){
			sql+=" and b.mtype = '"+mtype+"'  ";
			sqlCount+=" and b.mtype = '"+mtype+"'  ";
		}
		sql+= " ORDER BY a.BMTKID DESC";
		BigDecimal counts = merchantTaskOperateDao.querysqlCounts(sqlCount, null);
		List<Object> list=merchantTaskOperateDao.queryObjectsBySqlLists(sql, null, page, rows, MerchantTaskDataModel.class);
		
		return formatToDataGrid(list,counts.longValue());
		
	}
	
	private String getImageDay(String imageName){
		String[] split = imageName.split("\\.");
		if(split[1].length()==8){
			return split[1];
		}else{
			return split[2];
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> queryMerchantTaskDetail1(Integer bmtkid) {
		String sql="select * from SYS_PARAM t WHERE t.title='HrtFrameWork'";
		List<Map<String, Object>> list=merchantTaskOperateDao.queryObjectsBySqlList(sql);
		String upload =(String) list.get(0).get("UPLOAD_PATH");
		List list2=new ArrayList();
		MerchantTaskDetail1Model mm = (MerchantTaskDetail1Model) merchantTaskOperateDao.queryObjectByHql("from MerchantTaskDetail1Model m where m.bmtkid=?", new Object[]{bmtkid});
		list2.add(mm);
		if(mm.getLegalUploadFileName()!="" && mm.getLegalUploadFileName()!=null){
			list2.add(upload+File.separator+getImageDay(mm.getLegalUploadFileName())+File.separator+mm.getLegalUploadFileName());
		}else{
			list2.add("");
		}
		if(mm.getBupload()!="" && mm.getBupload()!=null){
			list2.add(upload+File.separator+getImageDay(mm.getBupload())+File.separator+mm.getBupload());
		}else{
			list2.add("");
		}
		if(mm.getRupload()!="" && mm.getRupload()!=null){
			list2.add(upload+File.separator+getImageDay(mm.getRupload())+File.separator+mm.getRupload());
		}else{
			list2.add("");
		}
		if(mm.getRegistryUpLoad()!="" && mm.getRegistryUpLoad()!=null){
			list2.add(upload+File.separator+getImageDay(mm.getRegistryUpLoad())+File.separator+mm.getRegistryUpLoad());
		}else{
			list2.add("");
		}
		if(mm.getMaterialUpLoad()!="" && mm.getMaterialUpLoad()!=null){
			list2.add(upload+File.separator+getImageDay(mm.getMaterialUpLoad())+File.separator+mm.getMaterialUpLoad());
		}else{
			list2.add("");
		}
		if(mm.getLegalUpload2FileName()!="" && mm.getLegalUpload2FileName()!=null){
			list2.add(upload+File.separator+getImageDay(mm.getLegalUpload2FileName())+File.separator+mm.getLegalUpload2FileName());
		}else{
			list2.add("");
		}
		return list2; 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> queryMerchantTaskDetail2(Integer bmtkid) {
		String sql="select * from SYS_PARAM t WHERE t.title='HrtFrameWork'";
		List<Map<String, Object>> list=merchantTaskOperateDao.queryObjectsBySqlList(sql);
		String upload =(String) list.get(0).get("UPLOAD_PATH");
		List list2=new ArrayList();
		MerchantTaskDetail2Model mm = (MerchantTaskDetail2Model) merchantTaskOperateDao.queryObjectByHql("from MerchantTaskDetail2Model m where m.bmtkid=?", new Object[]{bmtkid});
		list2.add(mm);
		if(mm.getAccUpLoad()!="" && mm.getAccUpLoad()!=null){
			list2.add(upload+File.separator+getImageDay(mm.getAccUpLoad())+File.separator+mm.getAccUpLoad());
		}else{
			list2.add("");
		}
		if(mm.getAuthUpLoad()!="" && mm.getAuthUpLoad()!=null){
			list2.add(upload+File.separator+getImageDay(mm.getAuthUpLoad())+File.separator+mm.getAuthUpLoad());
		}else{
			list2.add("");
		}
		if(mm.getDUpLoad()!="" && mm.getDUpLoad()!=null){
			list2.add(upload+File.separator+getImageDay(mm.getDUpLoad())+File.separator+mm.getDUpLoad());
		}else{
			list2.add("");
		}
		if(mm.getOpenUpLoad()!="" && mm.getOpenUpLoad()!=null){
			list2.add(upload+File.separator+getImageDay(mm.getOpenUpLoad())+File.separator+mm.getOpenUpLoad());
		}else{
			list2.add("");
		}
		return list2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> queryMerchantTaskDetail3(Integer bmtkid) {
		String sql="select * from SYS_PARAM t WHERE t.title='HrtFrameWork'";
		List<Map<String, Object>> list=merchantTaskOperateDao.queryObjectsBySqlList(sql);
		String upload =(String) list.get(0).get("UPLOAD_PATH");
		List list2=new ArrayList();
		MerchantTaskDetail3Model mm=(MerchantTaskDetail3Model) merchantTaskOperateDao.queryObjectByHql("from MerchantTaskDetail3Model m where m.bmtkid=?", new Object[]{bmtkid});
		list2.add(mm);
		if(mm.getFeeUpLoad()!="" && mm.getFeeUpLoad()!=null){
			list2.add(upload+File.separator+getImageDay(mm.getFeeUpLoad())+File.separator+mm.getFeeUpLoad());
		}else{
			list2.add("");
		} 
		return list2;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> queryMerchantTaskDetail4(Integer bmtkid) {
		List list3=new ArrayList();
		MerchantTaskDetail4Model mm=(MerchantTaskDetail4Model) merchantTaskOperateDao.queryObjectByHql("from MerchantTaskDetail4Model m where m.bmtkid=?", new Object[]{bmtkid});
		list3.add(mm);
		return list3;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> queryMerchantTaskDetail5(Integer bmtkid,String mid) {
		String sql="select * from SYS_PARAM t WHERE t.title='HrtFrameWork'";
		List<Map<String, Object>> list=merchantTaskOperateDao.queryObjectsBySqlList(sql);
		String upload =(String) list.get(0).get("UPLOAD_PATH");
		
		List list3=new ArrayList();
		MerchantTaskDetail5Bean merchantTaskDetail5Bean = new MerchantTaskDetail5Bean();

		MerchantTaskDetail5Model mm=(MerchantTaskDetail5Model) merchantTaskOperateDao.queryObjectByHql("from MerchantTaskDetail5Model m where m.bmtkid=?", new Object[]{bmtkid});
		BeanUtils.copyProperties(mm,merchantTaskDetail5Bean);
		
		if(mm.getLegalPositiveName()!=null && !"".equals(mm.getLegalPositiveName())){
			merchantTaskDetail5Bean.setLegalPositiveName(upload+File.separator+mm.getLegalPositiveName().substring(0,6)+File.separator+mm.getLegalPositiveName());
		}
		if(mm.getLegalReverseName()!=null && !"".equals(mm.getLegalReverseName())){
			merchantTaskDetail5Bean.setLegalReverseName(upload+File.separator+mm.getLegalReverseName().substring(0,6)+File.separator+mm.getLegalReverseName());
		}
		if(mm.getLegalHandName()!=null && !"".equals(mm.getLegalHandName())){
			merchantTaskDetail5Bean.setLegalHandName(upload+File.separator+mm.getLegalHandName().substring(0,6)+File.separator+mm.getLegalHandName());
		}
		
		MerchantInfoModel merchant=(MerchantInfoModel) merchantTaskOperateDao.queryObjectByHql("from MerchantInfoModel m where m.mid=?", new Object[]{mid});

		merchantTaskDetail5Bean.setSettleCycle(merchant.getSettleCycle());
		list3.add(merchantTaskDetail5Bean);
		return list3;
	}

	@Override
	public boolean deleteMerchantTaskDetail(Integer bmtkid) {
		Object o=merchantTaskOperateDao.queryObjectByHql("from MerchantTaskDataModel m where m.bmtkid=?", new Object[]{bmtkid});
		MerchantTaskDataModel m=(MerchantTaskDataModel) o;
		if(m.getApproveStatus().equals("Z")){
			m.setApproveStatus("K");
			merchantTaskOperateDao.updateObject(m);
			todoDao.editStatusTodo("bill_task",bmtkid.toString()); 
			return true;
		}else{
			return false;
		}
		
	}

//	@Override
//	public void deleteMoneyMerchantTaskDetail(String ids) {
//		String bmtkids []=ids.split(",");
//		for(int i = 0; i<bmtkids.length; i++){
//			Object o=merchantTaskOperateDao.queryObjectByHql("from MerchantTaskDataModel m where m.bmtkid=?", new Object[]{Integer.parseInt(bmtkids[i])});
//			MerchantTaskDataModel m=(MerchantTaskDataModel) o;
//			m.setApproveStatus("K");
//			merchantTaskOperateDao.updateObject(m);
//		}
//	}
//	


}
