package com.hrt.biz.bill.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IMachineInfoDao;
import com.hrt.biz.bill.dao.IMachineOrderDataDao;
import com.hrt.biz.bill.dao.IMachineOrderDetailDao;
import com.hrt.biz.bill.entity.model.MachineInfoModel;
import com.hrt.biz.bill.entity.model.MachineOrderDataModel;
import com.hrt.biz.bill.entity.model.MachineOrderDetailModel;
import com.hrt.biz.bill.entity.pagebean.MachineOrderDataBean;
import com.hrt.biz.bill.service.IMachineOrderDataService;
import com.hrt.frame.dao.sysadmin.ITodoDao;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.TodoModel;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.TodoBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.PropertiesUtil;

public class MachineOrderDataServiceImpl implements IMachineOrderDataService{
	
	private IMachineInfoDao machineInfoDao;
	
	private IMachineOrderDataDao machineOrderDataDao;
	
	private IMachineOrderDetailDao machineOrderDetailDao;
	
	private ITodoDao todoDao;
	
	private IUnitDao unitDao;
	
	private DateFormat format=new SimpleDateFormat("yyyyMMdd");
	
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

	public IMachineOrderDetailDao getMachineOrderDetailDao() {
		return machineOrderDetailDao;
	}

	public void setMachineOrderDetailDao(
			IMachineOrderDetailDao machineOrderDetailDao) {
		this.machineOrderDetailDao = machineOrderDetailDao;
	}

	public IMachineOrderDataDao getMachineOrderDataDao() {
		return machineOrderDataDao;
	}

	public void setMachineOrderDataDao(IMachineOrderDataDao machineOrderDataDao) {
		this.machineOrderDataDao = machineOrderDataDao;
	}

	public IMachineInfoDao getMachineInfoDao() {
		return machineInfoDao;
	}

	public void setMachineInfoDao(IMachineInfoDao machineInfoDao) {
		this.machineInfoDao = machineInfoDao;
	}

	/**
	 * 方法功能：格式化MachineOrderDataModel为datagrid数据格式
	 * 参数：machineOrderDataList
	 *     total 总记录数
	 */
	private DataGridBean formatToDataGrid(List<MachineOrderDataModel> machineOrderDataList, long total) {
		List<MachineOrderDataBean> machineOrderDataBeanList = new ArrayList<MachineOrderDataBean>();
		for(MachineOrderDataModel machineOrderDataModel : machineOrderDataList) {
			MachineOrderDataBean machineOrderDataBean = new MachineOrderDataBean();
			BeanUtils.copyProperties(machineOrderDataModel, machineOrderDataBean);
			UnitModel unit=getSeqNoByUnno(machineOrderDataModel.getUnNO());
			machineOrderDataBean.setUnName(unit.getUnitName());
			machineOrderDataBeanList.add(machineOrderDataBean);
		}
		
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(machineOrderDataBeanList);
		
		return dgb;
	}
	
	/**
	 * 根据机构号得到机构信息
	 */
	public UnitModel getSeqNoByUnno(String unno){
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("unNO", unno);
		String hql=" FROM UnitModel WHERE unNO=:unNO";
		List<UnitModel> list=unitDao.queryObjectsByHqlList(hql, param);
		if(list.size()>0){
			return list.get(0);
		}else{
			return new UnitModel();
		}
		
	}
	
	/**
	 * 分页查询机具订单
	 */
	@Override
	public DataGridBean queryOrders(MachineOrderDataBean machineOrderData,UserBean user)
			throws Exception {
		Map<String, Object> param=new HashMap<String, Object>();
		String hql = "from MachineOrderDataModel where 1 = 1";
		String hqlCount = "select count(*) from MachineOrderDataModel where 1 = 1";
		if (machineOrderData.getApproveStatus() != null&&!"".equals(machineOrderData.getApproveStatus())) {
			hql += " AND approveStatus=:approveStatus" ;
			hqlCount += " AND approveStatus=:approveStatus" ;
			param.put("approveStatus", machineOrderData.getApproveStatus() );
		}
		if (machineOrderData.getStatus() != null&&!"".equals(machineOrderData.getStatus())) {
			hql += " AND status=:status" ;
			hqlCount += " AND status=:status" ;
			param.put("status", machineOrderData.getStatus() );
		}
		if (machineOrderData.getUnNO() != null&&!"".equals(machineOrderData.getUnNO())) {
			hql += " AND unNO=:unNO" ;
			hqlCount += " AND unNO=:unNO" ;
			param.put("unNO", machineOrderData.getUnNO() );
		}else{//权限控制
			List<Object> list= unitDao.queryUnno(user);
			if(list.size()>0&&!"110000".equals(list.get(0))){
				String unnos="";
				for (Object object : list) {
					unnos+="'"+object+"',";
				}
				hql += " AND unNO in ("+unnos.substring(0, unnos.length()-1) +")" ;
				hqlCount +=" AND unNO in ("+unnos.substring(0, unnos.length()-1) +")" ;
				
			}
		}
		if (machineOrderData.getOrderID() != null&&!"".equals(machineOrderData.getOrderID())) {
			hql += " AND orderID=:orderID" ;
			hqlCount += " AND orderID=:orderID" ;
			param.put("orderID", machineOrderData.getOrderID() );
		}
		if (machineOrderData.getTxnDayStart() != null&&!"".equals(machineOrderData.getTxnDayStart())) {
			hql += " AND txnDay>=:txnDayStart" ;
			hqlCount += " AND txnDay>=:txnDayStart" ;
			String start=machineOrderData.getTxnDayStart().replaceAll("-", "");
			param.put("txnDayStart", start );
		}
		if (machineOrderData.getTxnDayEnd() != null&&!"".equals(machineOrderData.getTxnDayEnd())) {
			hql += " AND txnDay<=:txnDayEnd" ;
			hqlCount += " AND txnDay<=:txnDayEnd" ;
			String end=machineOrderData.getTxnDayEnd().replaceAll("-", "");
			param.put("txnDayEnd",end);
		}
		if (machineOrderData.getSort() != null) {
			hql += " order by " + machineOrderData.getSort() + " " + machineOrderData.getOrder();
		}
		long counts = machineOrderDataDao.queryCounts(hqlCount, param);
		List<MachineOrderDataModel> machineOrderDataList = machineOrderDataDao.queryOrders(hql, param, machineOrderData.getPage(), machineOrderData.getRows());
		
		DataGridBean dataGridBean = formatToDataGrid(machineOrderDataList, counts);
		
		return dataGridBean;
	}
	
	/**
	 * 查询订单详细信息
	 */
	@Override
	public List<Map<String, String>> getOrder(Integer ids) throws Exception {
		
		String sql = "SELECT t.bmdID,info.machineModel,info.machineStock,info.machinePrice,info.machineType,t.actionPrice,t.orderAmount,t.orderNum FROM bill_MachineInfo info,bill_MachineOrderDetail t where 1 = 1 AND info.bmaID = t.bmaID AND t.bmoID="+ids;
		List<Map<String, String>> list=machineOrderDetailDao.executeSql(sql);
		return list;
	}
	@Override
	public List<MachineOrderDataModel> getOrderData(MachineOrderDataBean machineOrderData) throws Exception {
		Map<String, Object> param=new HashMap<String, Object>();
		String hql = "from MachineOrderDataModel where 1 = 1 ";
		
		if(machineOrderData.getBmoID()!=null&&!"".equals(machineOrderData.getBmoID())){
			hql+=" AND bmoID=:bmoID";
			param.put("bmoID", machineOrderData.getBmoID());
		}
		if (machineOrderData.getSort() != null) {
			hql += " order by " + machineOrderData.getSort() + " " + machineOrderData.getOrder();
		}
		
		return machineOrderDataDao.queryObjectsByHqlList(hql, param);
	}
	
	@Override
	public synchronized  void save(MachineOrderDataBean machineOrderDataBean, UserBean user,String[] values) {
		TodoModel todo=new TodoModel();
		
		if(getFatherUnno(user.getUnNo()).equals("110000")){
			todo.setUnNo("110008");
		}else{
			todo.setUnNo(getFatherUnno(user.getUnNo()));
		}
		todo.setMsgSender(user.getUserID());
		todo.setBizType("bill_order");
		todo.setMsgSendTime(new Date());
		todo.setMsgSendUnit(user.getUnNo());
		todo.setMsgTopic("待审核的机具订单");
		todo.setStatus(0);
		todo.setTranCode("10230");
			
		MachineOrderDataModel machineOrderDataModel = new MachineOrderDataModel();
		BeanUtils.copyProperties(machineOrderDataBean, machineOrderDataModel);
		Date date=new Date();
		int maxId=getMaxId()+1;
		machineOrderDataModel.setBmoID(maxId);
		String btoid="000000"+String.valueOf(maxId);
		StringBuffer orderId=new StringBuffer();
		orderId.append(format.format(date)+"-");
		orderId.append(user.getUnNo()+"-");
		orderId.append(btoid.substring(btoid.length()-6, btoid.length()));
		machineOrderDataModel.setUnNO(user.getUnNo());
		machineOrderDataModel.setApproveStatus("Z");
		machineOrderDataModel.setStatus(0);
		machineOrderDataModel.setOrderID(orderId.toString());
		machineOrderDataModel.setTxnDay(format.format(date));
		machineOrderDataModel.setMaintainDate(date);//操作日期默认当前
		machineOrderDataModel.setMaintainType("A");//操作类型添加
		machineOrderDataModel.setMaintainUID(user.getUserID());
		machineOrderDataDao.saveObject(machineOrderDataModel);
		for (int i = 0; i < values.length; i++) {
			String[] pram=values[i].split("#");
			MachineOrderDetailModel orderDetail=new MachineOrderDetailModel();
			orderDetail.setBmoID(maxId);
			orderDetail.setBmaID(Integer.valueOf(pram[0]));
			orderDetail.setOrderNum(pram[1]);
			orderDetail.setActionPrice(pram[2]);
			orderDetail.setOrderAmount(pram[3]);
			orderDetail.setStatus(1);
			orderDetail.setUnNO(user.getUnNo());
			orderDetail.setMaintainUID(user.getUserID());
			orderDetail.setMaintainType("A");
			orderDetail.setMaintainDate(date);
			machineOrderDetailDao.saveObject(orderDetail);
		}
		todo.setBatchJobNo(String.valueOf(maxId));
		if(!user.getLoginName().equals("superadmin")){
			todoDao.saveObject(todo);
		}	
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
	public void delete(Integer id) {
		MachineOrderDataModel machineOrderData=machineOrderDataDao.getObjectByID(MachineOrderDataModel.class, id);
		machineOrderDataDao.deleteObject(machineOrderData);
	}

	@Override
	public void updateApp(MachineOrderDataBean machineOrderDataBean, UserBean user) throws Exception {
			MachineOrderDataModel machineOrderDataModel = machineOrderDataDao.getObjectByID(MachineOrderDataModel.class, machineOrderDataBean.getBmoID());
			BeanUtils.copyProperties(machineOrderDataBean, machineOrderDataModel);
			if("Y".equals(machineOrderDataModel.getApproveStatus())){
				machineOrderDataModel.setStatus(2);
				List<Map<String, String>> list=getOrder(machineOrderDataModel.getBmoID());
				for(Map<String, String> map:list){
					String hql="update MachineInfoModel set machineStock=machineStock-";
					hql+=String.valueOf(map.get("ORDERNUM"))+" where bmaID="+String.valueOf(map.get("BMAID"));
					machineInfoDao.executeHql(hql);
				}
				
				Double total=0.00;
				String[] acIds=machineOrderDataBean.getActionID().split(",");
				
				for(int i=0;i<acIds.length;i++){
					String[] maPric=acIds[i].split("#");
					Integer[] para={Integer.valueOf(maPric[0])};
					String hql=" FROM MachineOrderDetailModel WHERE bmdID=?";
					MachineOrderDetailModel detail=machineOrderDetailDao.queryObjectByHql(hql, para);
					String price=maPric[1].toString();
					detail.setActionPrice(price);
					Double amount=Double.valueOf(maPric[1].toString())*Double.valueOf(detail.getOrderNum());
					detail.setOrderAmount(amount.toString());
					machineOrderDetailDao.updateObject(detail);
					total+=amount;
				}
				machineOrderDataModel.setOrderAmount(total.toString());
				machineOrderDataModel.setApproveUID(user.getUserID());
				machineOrderDataModel.setApproveDate(new Date());
				machineOrderDataDao.updateObject(machineOrderDataModel);
				todoDao.editStatusTodo("bill_order",machineOrderDataBean.getBmoID().toString());
			}else if("K".equals(machineOrderDataModel.getApproveStatus())){
				machineOrderDataModel.setStatus(0);
				machineOrderDataModel.setApproveUID(user.getUserID());
				machineOrderDataModel.setApproveDate(new Date());
				machineOrderDataDao.updateObject(machineOrderDataModel);
				todoDao.editStatusTodo("bill_order",machineOrderDataBean.getBmoID().toString());
			}
			
	}

	@Override
	public Integer getMaxId() {
		String sql="SELECT MAX(t.BMOID) MAXID FROM BILL_MACHINEORDERDATA t ";
		List<Map<String, Object>> list=machineOrderDataDao.queryObjectsBySqlList(sql);
		if(list.get(0).get("MAXID")!=null&&!"null".equals(list.get(0).get("MAXID"))){
			String max=list.get(0).get("MAXID").toString();
			return Integer.valueOf(max);
		}else{
			return 0;
		}
		
	}

	@Override
	public void updateSup(MachineOrderDataBean machineOrderDataBean,
			UserBean userSession) {
		MachineOrderDataModel machineOrderDataModel = machineOrderDataDao.getObjectByID(MachineOrderDataModel.class, machineOrderDataBean.getBmoID());
		BeanUtils.copyProperties(machineOrderDataBean, machineOrderDataModel);
		machineOrderDataModel.setStatus(3);
		machineOrderDataModel.setAmountConfirmDate(new Date());
		machineOrderDataDao.updateObject(machineOrderDataModel);
	}

	@Override
	public void updateSend(MachineOrderDataBean machineOrderDataBean,
			UserBean userSession) {
		MachineOrderDataModel machineOrderDataModel = machineOrderDataDao.getObjectByID(MachineOrderDataModel.class, machineOrderDataBean.getBmoID());
		BeanUtils.copyProperties(machineOrderDataBean, machineOrderDataModel);
		machineOrderDataModel.setStatus(4);
		machineOrderDataModel.setSendConfirmDate(new Date());
		machineOrderDataDao.updateObject(machineOrderDataModel);
	}

	@Override
	public MachineOrderDataModel getOrderModel(Integer id) {
		return machineOrderDataDao.getObjectByID(MachineOrderDataModel.class, id);
	}
	
}
