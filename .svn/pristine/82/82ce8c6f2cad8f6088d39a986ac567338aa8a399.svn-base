package com.hrt.biz.bill.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IMachineInfoDao;
import com.hrt.biz.bill.dao.IMachineTaskDataDao;
import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.dao.IMerchantTerminalInfoDao;
import com.hrt.biz.bill.dao.ITerminalInfoDao;
import com.hrt.biz.bill.entity.model.MachineInfoModel;
import com.hrt.biz.bill.entity.model.MachineTaskDataModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTerminalInfoModel;
import com.hrt.biz.bill.entity.model.TerminalInfoModel;
import com.hrt.biz.bill.entity.pagebean.MachineTaskDataBean;
import com.hrt.biz.bill.service.IMachineTaskDataService;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.frame.dao.sysadmin.ITodoDao;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.TodoModel;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;
import com.hrt.gmms.webservice.IGmmsService;
import com.hrt.gmms.webservice.TermAcc;

public class MachineTaskDataServiceImpl implements IMachineTaskDataService {

	private IMachineTaskDataDao machineTaskDataDao;
	
	private IMachineInfoDao machineInfoDao;
	
	private IUnitDao unitDao;
	
	private IMerchantInfoDao merchantInfoDao;
	
	private ITodoDao todoDao;
	
	private IMerchantTerminalInfoDao merchantTerminalInfoDao;
	
	private IGmmsService gsClient;
	
	private ITerminalInfoDao terminalInfoDao;
	
	private IMerchantInfoService merchantInfoService;

	public IMachineTaskDataDao getMachineTaskDataDao() {
		return machineTaskDataDao;
	}

	public void setMachineTaskDataDao(IMachineTaskDataDao machineTaskDataDao) {
		this.machineTaskDataDao = machineTaskDataDao;
	}

	public IMachineInfoDao getMachineInfoDao() {
		return machineInfoDao;
	}

	public void setMachineInfoDao(IMachineInfoDao machineInfoDao) {
		this.machineInfoDao = machineInfoDao;
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

	public ITodoDao getTodoDao() {
		return todoDao;
	}

	public void setTodoDao(ITodoDao todoDao) {
		this.todoDao = todoDao;
	}

	public IMerchantTerminalInfoDao getMerchantTerminalInfoDao() {
		return merchantTerminalInfoDao;
	}

	public void setMerchantTerminalInfoDao(
			IMerchantTerminalInfoDao merchantTerminalInfoDao) {
		this.merchantTerminalInfoDao = merchantTerminalInfoDao;
	}
	
	public IGmmsService getGsClient() {
		return gsClient;
	}

	public void setGsClient(IGmmsService gsClient) {
		this.gsClient = gsClient;
	}

	public ITerminalInfoDao getTerminalInfoDao() {
		return terminalInfoDao;
	}

	public void setTerminalInfoDao(ITerminalInfoDao terminalInfoDao) {
		this.terminalInfoDao = terminalInfoDao;
	}

	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}
	
	

	/**
	 * 方法功能：格式化AgentUnitModel为datagrid数据格式
	 * 参数：agentUnitList
	 *     total 总记录数
	 * 返回值：
	 * 异常：
	 */
	private DataGridBean formatToDataGrid(List<MachineTaskDataModel> machineTaskDataList, long total) {
		List<MachineTaskDataBean> machineTaskDataBeanList = new ArrayList<MachineTaskDataBean>();
		for(MachineTaskDataModel machineTaskDataModel : machineTaskDataList) {
			MachineTaskDataBean machineTaskDataBean = new MachineTaskDataBean();
			BeanUtils.copyProperties(machineTaskDataModel, machineTaskDataBean);
			if(machineTaskDataModel.getBmaID() != null){
				MachineInfoModel machineInfoModel = machineInfoDao.getObjectByID(MachineInfoModel.class, machineTaskDataModel.getBmaID());
				if(machineInfoModel != null){
					machineTaskDataBean.setBmaIDName(machineInfoModel.getMachineModel());
				}
			}
			
			MerchantTerminalInfoModel merchantTerminalInfoModel = merchantTerminalInfoDao.getObjectByID(MerchantTerminalInfoModel.class, machineTaskDataModel.getBmtID());
			String hql="from TerminalInfoModel t where t.termID=? ";
			TerminalInfoModel tt = terminalInfoDao.queryObjectByHql(hql, new Object[]{merchantTerminalInfoModel.getTid()});
			if(merchantTerminalInfoModel != null){
				machineTaskDataBean.setBmtIDName(merchantTerminalInfoModel.getTid());
			}
			if(tt != null){
				machineTaskDataBean.setSn(tt.getSn());
			}else{
				machineTaskDataBean.setSn(merchantTerminalInfoModel.getSn());
			}
			
			machineTaskDataBeanList.add(machineTaskDataBean);
		}
		
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(machineTaskDataBeanList);
		
		return dgb;
	}
	
	@Override
	public void updateMachineTaskData(MachineTaskDataBean machineTaskDataBean, UserBean user)
			throws Exception {
		MachineTaskDataModel machineTaskDataModel = machineTaskDataDao.getObjectByID(MachineTaskDataModel.class, machineTaskDataBean.getBmtdID());
		machineTaskDataModel.setMachinesn(machineTaskDataBean.getMachinesn());
		machineTaskDataModel.setSimtel(machineTaskDataBean.getSimtel());
		machineTaskDataDao.updateObject(machineTaskDataModel);
	}
	
	
	//修改maintype类型
	public void updateType(MachineTaskDataBean machineTaskDataBean, UserBean user){
		String sql="UPDATE bill_MachineTaskData SET MaintainType = 'D' WHERE BMTDID='"+machineTaskDataBean.getBmtdID()+"'";
		machineTaskDataDao.executeUpdate(sql);
		String sqlstu="UPDATE bill_merchantterminalinfo SET status = '3' WHERE bmtid='"+machineTaskDataBean.getBmtID()+"'";
		machineTaskDataDao.executeUpdate(sqlstu);	
	}

	@Override
	public DataGridBean queryMachineTaskData(MachineTaskDataBean machineTaskDataBean,UserBean userBean) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		String sql = "";
		String sqlCount = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if("110000".equals(userBean.getUnNo())){
			sql = "SELECT * FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND (APPROVESTATUS = 'N' OR APPROVESTATUS = 'Y') AND TASKSTARTDATE IS NOT NULL";
			//sqlCount = "SELECT COUNT(*) FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND (APPROVESTATUS = 'N' OR APPROVESTATUS = 'Y') AND TASKSTARTDATE IS NOT NULL";
			map.put("maintainType", "D");
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
				sql = "SELECT * FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND (APPROVESTATUS = 'N' OR APPROVESTATUS = 'Y') AND TASKSTARTDATE IS NOT NULL";
				//sqlCount = "SELECT COUNT(*) FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND (APPROVESTATUS = 'N' OR APPROVESTATUS = 'Y') AND TASKSTARTDATE IS NOT NULL";
				map.put("maintainType", "D");
			}
		}else{
			sql = "SELECT * FROM BILL_MACHINETASKDATA WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND (APPROVESTATUS = 'N' OR APPROVESTATUS = 'Y') AND TASKSTARTDATE IS NOT NULL";
			//sqlCount = "SELECT COUNT(*) FROM BILL_MACHINETASKDATA WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND (APPROVESTATUS = 'N' OR APPROVESTATUS = 'Y') AND TASKSTARTDATE IS NOT NULL";
			map.put("unno", userBean.getUnNo());
			map.put("maintainType", "D");
		}
		
		if(machineTaskDataBean.getMid() != null && !"".equals(machineTaskDataBean.getMid().trim())){
			sql += " AND MID = :mid";
		//	sqlCount += " AND MID = :mid";
			map.put("mid", machineTaskDataBean.getMid());
		}
		
		if(machineTaskDataBean.getTidBmtID() != null && !"".equals(machineTaskDataBean.getTidBmtID().trim())){
			sql += " AND (TID = :tidBmtID OR BMTID = (SELECT BMTID FROM BILL_MERCHANTTERMINALINFO WHERE TID = :tidBmtID))";
			//sqlCount += " AND (TID = :tidBmtID OR BMTID = (SELECT BMTID FROM BILL_MERCHANTTERMINALINFO WHERE TID = :tidBmtID))";
			map.put("tidBmtID",machineTaskDataBean.getTidBmtID());
		}
		
		if (machineTaskDataBean.getCreateDateStart() != null && !"".equals(machineTaskDataBean.getCreateDateStart())) {
			sql +=" AND TASKSTARTDATE >= to_date('"+machineTaskDataBean.getCreateDateStart()+" 00:00:00', 'yyyy-MM-dd HH24:MI:SS') ";
			//sql +=" AND to_char(TASKSTARTDATE,'yyyy-MM-dd') >= :createDateTimeStart";
			//sqlCount += " AND to_char(TASKSTARTDATE,'yyyy-MM-dd') >= :createDateTimeStart";
			//map.put("createDateTimeStart", machineTaskDataBean.getCreateDateStart());
		}else{
//			sql +=" AND TASKSTARTDATE >= to_date(to_char(sysdate, 'yyyy-MM-dd'), 'yyyy-MM-dd HH:MI:ss') ";
			sql +=" AND TASKSTARTDATE >= trunc(sysdate) ";
		}
		
		if (machineTaskDataBean.getCreateDateEnd() != null && !"".equals(machineTaskDataBean.getCreateDateEnd()) ) {
			sql +=" AND TASKSTARTDATE <= to_date('"+machineTaskDataBean.getCreateDateEnd()+" 23:59:59', 'yyyy-MM-dd HH24:MI:SS') ";
			//sql +=" AND to_char(TASKSTARTDATE,'yyyy-MM-dd') <= :createdatetimeEnd";
			//sqlCount += " AND to_char(TASKSTARTDATE,'yyyy-MM-dd') <= :createdatetimeEnd";
			//map.put("createdatetimeEnd", machineTaskDataBean.getCreateDateEnd());
		}
		
		if(machineTaskDataBean.getRname() != null && !"".equals(machineTaskDataBean.getRname().trim())){
			sql += " AND MID IN (SELECT MID FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != 'D' AND RNAME LIKE :rname)";
			//sqlCount += " AND MID IN (SELECT MID FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != 'D' AND RNAME LIKE :rname)";
			map.put("rname",machineTaskDataBean.getRname()+'%');
		}

		if("1".equals(machineTaskDataBean.getTaskStartDateName())){
			sql += " AND TASKSTEP1DATE IS NULL AND TASKSTEP2DATE IS NULL AND TASKCONFIRMDATE IS NULL";
			//sqlCount += " AND TASKSTEP1DATE IS NULL AND TASKSTEP2DATE IS NULL AND TASKCONFIRMDATE IS NULL";
		}else if("2".equals(machineTaskDataBean.getTaskStartDateName())){
			sql += " AND TASKSTEP1DATE IS NOT NULL AND TASKSTEP2DATE IS NULL AND TASKCONFIRMDATE IS NULL";
			//sqlCount += " AND TASKSTEP1DATE IS NOT NULL AND TASKSTEP2DATE IS NULL AND TASKCONFIRMDATE IS NULL";
		}else if("3".equals(machineTaskDataBean.getTaskStartDateName())){
			sql += " AND TASKSTEP1DATE IS NOT NULL AND TASKSTEP2DATE IS NOT NULL AND TASKCONFIRMDATE IS NULL";
			//sqlCount += " AND TASKSTEP1DATE IS NOT NULL AND TASKSTEP2DATE IS NOT NULL AND TASKCONFIRMDATE IS NULL";
		}
		
		sql="select q.RNAME RNAME,p.*,w.tid BMTIDNAME,NVL((select  s.machinemodel from bill_machineInfo s where s.bmaid=p.bmaid ),'') as BMAIDNAME from ("+sql+") p,bill_merchantinfo q,bill_merchantTerminalInfo w where p.mid=q.mid and p.bmtid=w.bmtid ";
		sqlCount="SELECT COUNT(*) FROM ("+sql+")";
		if (machineTaskDataBean.getSort() != null) {
			sql += " ORDER BY p." + machineTaskDataBean.getSort() + " " + machineTaskDataBean.getOrder();
		}
		//List<MachineTaskDataModel> machineTaskDataList = machineTaskDataDao.queryMachineTaskData(sql, map, machineTaskDataBean.getPage(), machineTaskDataBean.getRows(), MachineTaskDataModel.class);
		 List machineTaskDataList = machineTaskDataDao.queryObjectsBySqlList(sql,map,machineTaskDataBean.getPage(), machineTaskDataBean.getRows());
		   
		BigDecimal counts = machineTaskDataDao.querysqlCounts(sqlCount, map);
		
		//DataGridBean dataGridBean = formatToDataGrid(machineTaskDataList, counts.longValue());
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(counts.intValue());
		dgb.setRows(machineTaskDataList);
		return dgb;
	}
	
	/**
	 * 导出商户  终端    word文档
	 */
	public List<Map<String, Object>> exportMerchantInfo1(int bmtdID) {
		String sql="SELECT lpad(BMTID,12,0) BMTID, (SELECT nvl(bmid.RNAME,' ') FROM BILL_MERCHANTINFO bmid WHERE bmid.MID = bmtdid.MID) RNAME, (SELECT nvl(bmtid.INSTALLEDADDRESS,' ') FROM BILL_MERCHANTTERMINALINFO bmtid WHERE bmtid.BMTID = bmtdid.BMTID) INSTALLEDADDRESS,  (SELECT nvl(bmtid.CONTACTPERSON,' ') FROM BILL_MERCHANTTERMINALINFO bmtid WHERE bmtid.BMTID = bmtdid.BMTID) CONTACTPERSON,  (SELECT nvl(bmtid.CONTACTPHONE,' ') FROM BILL_MERCHANTTERMINALINFO bmtid WHERE bmtid.BMTID = bmtdid.BMTID) CONTACTPHONE,(SELECT nvl(bmtid.INSTALLEDSIM,' ') FROM BILL_MERCHANTTERMINALINFO bmtid WHERE bmtid.BMTID = bmtdid.BMTID) INSTALLEDSIM,  (SELECT nvl(bmtid.MID,' ') FROM BILL_MERCHANTTERMINALINFO bmtid WHERE bmtid.BMTID = bmtdid.BMTID) OLDMID,(SELECT nvl(bmtid.TID,' ') FROM BILL_MERCHANTTERMINALINFO bmtid WHERE bmtid.BMTID = bmtdid.BMTID) ORDTID,  (SELECT (SELECT nvl(bmaid.MACHINEMODEL,' ') MACHINEMODEL  FROM BILL_MACHINEINFO bmaid WHERE bmaid.BMAID = bmtid.BMAID) MACHINEMODEL FROM BILL_MERCHANTTERMINALINFO bmtid where bmtid.BMTID = bmtdid.BMTID) OLDMACHINEMODEL,   nvl(MID,' ') MID,nvl(TID,' ') TID,  nvl(MACHINESN,' ') MACHINESN,  (SELECT nvl(bmaid.MACHINEMODEL,' ') FROM BILL_MACHINEINFO bmaid WHERE bmtdid.BMAID = bmaid.BMAID) MACHINEMODEL, (SELECT nvl(busid.SALENAME,' ') FROM BILL_AGENTSALESINFO busid WHERE busid.BUSID = (SELECT bmid.busid FROM BILL_MERCHANTINFO bmid WHERE bmid.MID = bmtdid.MID)) SALENAME , (SELECT nvl(bmid.printname,' ') FROM BILL_MERCHANTINFO bmid WHERE bmid.MID = bmtdid.MID) PRINTNAME FROM  BILL_MACHINETASKDATA bmtdid WHERE bmtdID='"+bmtdID+"' ";
		List<Map<String, Object>> list=machineTaskDataDao.queryObjectsBySqlObject(sql);
		return list;
	}

	@Override
	public void saveMachineTaskData(MachineTaskDataBean machineTaskData,
			UserBean user) throws Exception {
		MachineTaskDataModel machineTaskDataModel = new MachineTaskDataModel();
		BeanUtils.copyProperties(machineTaskData, machineTaskDataModel);
		
		String tidHql = "from TerminalInfoModel where termID = :termID and maintainType != :maintainType";
		Map<String, Object> mapHql = new HashMap<String, Object>();
		mapHql.put("termID", machineTaskData.getTid());
		mapHql.put("maintainType", "D");
		List<TerminalInfoModel> terminalInfoModelList = terminalInfoDao.queryObjectsByHqlList(tidHql, mapHql);
		if(terminalInfoModelList != null && terminalInfoModelList.size() > 0){
			TerminalInfoModel terminalInfoModel = terminalInfoModelList.get(0);
			terminalInfoModel.setUsedConfirmDate(new Date());
			terminalInfoDao.updateObject(terminalInfoModel);
		}
		
		String hql = "from MerchantInfoModel where mid = :mid and maintainType != :maintainType";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mid", machineTaskData.getMid());
		map.put("maintainType", "D");
		List<MerchantInfoModel> merchantInfoModelList = merchantInfoDao.queryObjectsByHqlList(hql,map);
		if(merchantInfoModelList != null && merchantInfoModelList.size()>0){
			machineTaskDataModel.setUnno(merchantInfoModelList.get(0).getUnno());
		}
		
		machineTaskDataModel.setMaintainUid(user.getUserID());
		machineTaskDataModel.setMaintainDate(new Date());
		machineTaskDataModel.setMaintainType("A");
		machineTaskDataModel.setApproveUid(user.getUserID());
		machineTaskDataModel.setApproveDate(new Date());
		machineTaskDataModel.setRemarks(machineTaskData.getRemarks());
		if(!"2".equals(machineTaskData.getType())){
			machineTaskDataModel.setTaskStartDate(new Date());
			machineTaskDataModel.setTaskStep1Date(new Date());
			machineTaskDataModel.setApproveStatus("Y");
		}else{
			machineTaskDataModel.setApproveStatus("Z");
		}
		machineTaskDataDao.saveObject(machineTaskDataModel);
		
		StringBuffer taskID = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		taskID.append(sdf.format(new Date()));
		taskID.append("-");
		taskID.append(machineTaskDataModel.getUnno());
		taskID.append("-");
		DecimalFormat deFormat = new DecimalFormat("000000");
		taskID.append(deFormat.format(machineTaskDataDao.getBmtdID()));
		machineTaskDataModel.setTaskID(taskID.toString());
		machineTaskDataDao.updateObject(machineTaskDataModel);
		
		if("2".equals(machineTaskData.getType())){
			Integer batchJobNo = machineTaskDataDao.getBmtdID();
			//添加待办
			TodoModel todo = new TodoModel();
			todo.setUnNo("110003");
			todo.setBatchJobNo(batchJobNo.toString());
			todo.setMsgSendUnit(user.getUnNo());
			todo.setMsgSender(user.getUserID());
			todo.setMsgTopic("待审批工单申请");
			todo.setMsgSendTime(new Date());
			todo.setTranCode("10290");	//菜单代码
			todo.setBizType("bill_machinetask");
			todo.setStatus(0);			//0-未办，1-已办
			todoDao.saveObject(todo);
		}
	}

	@Override
	public DataGridBean queryMachineTaskDataTCD(
			MachineTaskDataBean machineTaskDataBean, UserBean userBean) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		String sql = "";
		String sqlCount = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if("110000".equals(userBean.getUnNo())){
			sql = "SELECT * FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NOT NULL";
			sqlCount = "SELECT COUNT(*) FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NOT NULL";
			map.put("maintainType", "D");
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
				sql = "SELECT * FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NOT NULL";
				sqlCount = "SELECT COUNT(*) FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NOT NULL";
				map.put("maintainType", "D");
			}
		}else{
			String childUnno=merchantInfoService.queryUnitUnnoUtil(userBean.getUnNo());
			sql = "SELECT * FROM BILL_MACHINETASKDATA WHERE UNNO IN ("+childUnno+") AND MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NOT NULL";
			sqlCount = "SELECT COUNT(*) FROM BILL_MACHINETASKDATA WHERE UNNO IN ("+childUnno+") AND MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NOT NULL";
			map.put("maintainType", "D");
		}
		
		if(machineTaskDataBean.getMid() != null && !"".equals(machineTaskDataBean.getMid().trim())){
			sql += " AND MID = :mid";
			sqlCount += " AND MID = :mid";
			map.put("mid", machineTaskDataBean.getMid().trim());
		}
		
		if (machineTaskDataBean.getCreateDateStart() != null
				&& !machineTaskDataBean.getCreateDateStart().trim().equals("")) {
//			sql +=" AND to_char(TASKCONFIRMDATE,'yyyy-MM-dd') >= :createDateTimeStart";
//			sqlCount += " AND to_char(TASKCONFIRMDATE,'yyyy-MM-dd') >= :createDateTimeStart";
			sql +=" AND TASKCONFIRMDATE >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
			sqlCount += " AND TASKCONFIRMDATE >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
			map.put("createDateTimeStart", machineTaskDataBean.getCreateDateStart());
		} 
		
		if (machineTaskDataBean.getCreateDateEnd() != null
				&& !machineTaskDataBean.getCreateDateEnd().trim().equals("")) {
//			sql +=" AND to_char(TASKCONFIRMDATE,'yyyy-MM-dd') <= :createdatetimeEnd";
//			sqlCount += " AND to_char(TASKCONFIRMDATE,'yyyy-MM-dd') <= :createdatetimeEnd";
			sql +=" AND TASKCONFIRMDATE < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
			sqlCount += " AND TASKCONFIRMDATE < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
			map.put("createdatetimeEnd", machineTaskDataBean.getCreateDateEnd());
		}
		
		if (machineTaskDataBean.getSort() != null) {
			sql += " ORDER BY " + machineTaskDataBean.getSort() + " " + machineTaskDataBean.getOrder();
		}
		
		if(machineTaskDataBean.getBmtIDName()!= null && !"".equals(machineTaskDataBean.getBmtIDName().trim())){
           sqlCount="select COUNT(*) from ("+sql+") a,BILL_MERCHANTTERMINALINFO b where a.BMTID=b.BMTID and b.TID="+machineTaskDataBean.getBmtIDName();
			sql="select a.* from ("+sql+") a,BILL_MERCHANTTERMINALINFO b where a.BMTID=b.BMTID and b.TID="+machineTaskDataBean.getBmtIDName();
		}
		List<MachineTaskDataModel> machineTaskDataList = machineTaskDataDao.queryMachineTaskData(sql, map, machineTaskDataBean.getPage(), machineTaskDataBean.getRows(), MachineTaskDataModel.class);
		BigDecimal counts = machineTaskDataDao.querysqlCounts(sqlCount, map);
		
		DataGridBean dataGridBean = formatToDataGrid(machineTaskDataList, counts.longValue());
		
		return dataGridBean;
	}

	@Override
	public DataGridBean queryAddMachineTaskData(
			MachineTaskDataBean machineTaskDataBean, UserBean userBean) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		String sql = "";
		String sqlCount = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if("110000".equals(userBean.getUnNo())){
			sql = "SELECT * FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND APPROVESTATUS != 'N'";
			sqlCount = "SELECT COUNT(*) FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND APPROVESTATUS != 'N'";
			map.put("maintainType", "D");
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
				sql = "SELECT * FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND APPROVESTATUS != 'N'";
				sqlCount = "SELECT COUNT(*) FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND APPROVESTATUS != 'N'";
				map.put("maintainType", "D");
			}
		}else{
			sql = "SELECT * FROM BILL_MACHINETASKDATA WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND APPROVESTATUS != 'N'";
			sqlCount = "SELECT COUNT(*) FROM BILL_MACHINETASKDATA WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND APPROVESTATUS != 'N'";
			map.put("unno", userBean.getUnNo());
			map.put("maintainType", "D");
		}
		
		if(machineTaskDataBean.getMid()!=null&&!"".equals(machineTaskDataBean.getMid())){
			sql +=" and MID=:MID ";
			sqlCount +=" and MID =:MID ";
			map.put("MID", machineTaskDataBean.getMid().trim());
			
		}
		
		if (machineTaskDataBean.getSort() != null) {
			sql += " ORDER BY " + machineTaskDataBean.getSort() + " " + machineTaskDataBean.getOrder();
		}
		
		List<MachineTaskDataModel> machineTaskDataList = machineTaskDataDao.queryMachineTaskData(sql, map, machineTaskDataBean.getPage(), machineTaskDataBean.getRows(), MachineTaskDataModel.class);
		BigDecimal counts = machineTaskDataDao.querysqlCounts(sqlCount, map);
		
		DataGridBean dataGridBean = formatToDataGrid(machineTaskDataList, counts.longValue());
		
		return dataGridBean;
	}

	@Override
	public void updateMachineTaskDataY(MachineTaskDataBean machineTaskDataBean,UserBean user)
			throws Exception {
		MachineTaskDataModel machineTaskDataModel = machineTaskDataDao.getObjectByID(MachineTaskDataModel.class, machineTaskDataBean.getBmtdID());
		
		machineTaskDataModel.setTaskStartDate(new Date());
		machineTaskDataModel.setProCessconText(machineTaskDataBean.getProCessconText());
		machineTaskDataModel.setApproveUid(user.getUserID());
		machineTaskDataModel.setApproveDate(new Date());
		machineTaskDataModel.setApproveStatus("Y");
		machineTaskDataDao.updateObject(machineTaskDataModel);
		todoDao.editStatusTodo("bill_machinetask", machineTaskDataModel.getBmtdID().toString());

		if("2".equals(machineTaskDataModel.getType())){
			if(machineTaskDataModel.getChangeType() != null && !"".equals(machineTaskDataModel.getChangeType().trim()) && "1".equals(machineTaskDataModel.getChangeType())){
				
				MerchantTerminalInfoModel bmtModel = merchantTerminalInfoDao.getObjectByID(MerchantTerminalInfoModel.class, machineTaskDataModel.getBmtID());
				MerchantTerminalInfoModel merchantTerminalInfoModel = new MerchantTerminalInfoModel();
				merchantTerminalInfoModel.setMid(machineTaskDataModel.getMid());
				merchantTerminalInfoModel.setTid(machineTaskDataModel.getTid());
				merchantTerminalInfoModel.setBmaid(machineTaskDataModel.getBmaID());
				merchantTerminalInfoModel.setInstalledAddress(bmtModel.getInstalledAddress());
				merchantTerminalInfoModel.setInstalledName(bmtModel.getInstalledName());
				merchantTerminalInfoModel.setContactPerson(bmtModel.getContactPerson());
				merchantTerminalInfoModel.setContactPhone(bmtModel.getContactPhone());
				merchantTerminalInfoModel.setContactTel(bmtModel.getContactTel());
				merchantTerminalInfoModel.setInstalledSIM(bmtModel.getInstalledSIM());
				merchantTerminalInfoModel.setUnno(user.getUnNo());
				merchantTerminalInfoModel.setApproveStatus("Y");		//默认为待受理 Y-已受理   Z-待受理  K-不受理
				merchantTerminalInfoModel.setMaintainUid(user.getUserID());
				merchantTerminalInfoModel.setMaintainDate(new Date());
				merchantTerminalInfoModel.setMaintainType("A");		//A-add   M-Modify  D-Delete
				merchantTerminalInfoModel.setStatus(1);
				merchantTerminalInfoDao.saveObject(merchantTerminalInfoModel);
				
				//调用webservice，对GMMS添加布放信息
				TermAcc acc=new TermAcc();
				//一代机构号
				String yUnno =machineTaskDataModel.getUnno();
				acc.setUnno(yUnno);
				if("3".equals(yUnno.substring(2, 3))){
					UnitModel unit =unitDao.getObjectByID(UnitModel.class,yUnno);
					acc.setUnno(unit.getParent().getUnNo());
				}
				if("5".equals(yUnno.substring(2, 3))){
					UnitModel unit =unitDao.getObjectByID(UnitModel.class,yUnno);
					acc.setUnno(unit.getParent().getParent().getUnNo());
				}
				if("6".equals(yUnno.substring(2, 3))){
					UnitModel unit =unitDao.getObjectByID(UnitModel.class,yUnno);
					acc.setUnno(unit.getParent().getParent().getParent().getUnNo());
				}
				if("7".equals(yUnno.substring(2, 3))){
					UnitModel unit =unitDao.getObjectByID(UnitModel.class,yUnno);
					acc.setUnno(unit.getParent().getParent().getParent().getParent().getUnNo());
				}
				acc.setCby(user.getLoginName());
				MachineInfoModel infoModel=machineInfoDao.getObjectByID(MachineInfoModel.class, machineTaskDataModel.getBmaID());
				acc.setModleId(infoModel.getMachineModel());
				acc.setMid(machineTaskDataModel.getMid());
				acc.setTid(machineTaskDataModel.getTid());
				Boolean falg=gsClient.saveTermAcc(acc,"hrtREX1234.Java");
				if(!falg){
					throw new IllegalAccessError("调用webservice失败");
				}
			}
		}
	}
	
	@Override
	public void updateMachineTaskDataK(MachineTaskDataBean machineTaskDataBean,UserBean user)
			throws Exception {
		MachineTaskDataModel machineTaskDataModel = machineTaskDataDao.getObjectByID(MachineTaskDataModel.class, machineTaskDataBean.getBmtdID());

		String tidHql = "from TerminalInfoModel where termID = :termID and maintainType != :maintainType";
		Map<String, Object> mapHql = new HashMap<String, Object>();
		mapHql.put("termID", machineTaskDataModel.getTid());
		mapHql.put("maintainType", "D");
		List<TerminalInfoModel> terminalInfoModelList = terminalInfoDao.queryObjectsByHqlList(tidHql, mapHql);
		if(terminalInfoModelList != null && terminalInfoModelList.size() > 0){
			TerminalInfoModel terminalInfoModel = terminalInfoModelList.get(0);
			terminalInfoModel.setUsedConfirmDate(null);
			terminalInfoDao.updateObject(terminalInfoModel);
		}
		
		machineTaskDataModel.setProCessconText(machineTaskDataBean.getProCessconText());
		machineTaskDataModel.setApproveUid(user.getUserID());
		machineTaskDataModel.setApproveDate(new Date());
		machineTaskDataModel.setApproveStatus("K");
		machineTaskDataDao.updateObject(machineTaskDataModel);
		todoDao.editStatusTodo("bill_machinetask", machineTaskDataModel.getBmtdID().toString());
	}

	@Override
	public DataGridBean queryMachineTaskDataJudge(
			MachineTaskDataBean machineTaskDataBean, UserBean userBean) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		String sql = "";
		String sqlCount = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if("110000".equals(userBean.getUnNo())){
			sql = "SELECT * FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND APPROVESTATUS = 'Z'";
			sqlCount = "SELECT COUNT(*) FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND APPROVESTATUS = 'Z'";
			map.put("maintainType", "D");
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
				sql = "SELECT * FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND APPROVESTATUS = 'Z'";
				sqlCount = "SELECT COUNT(*) FROM BILL_MACHINETASKDATA WHERE MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND APPROVESTATUS = 'Z'";
				map.put("maintainType", "D");
			}
		}else{
			sql = "SELECT * FROM BILL_MACHINETASKDATA WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND APPROVESTATUS = 'Z'";
			sqlCount = "SELECT COUNT(*) FROM BILL_MACHINETASKDATA WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND TASKCONFIRMDATE IS NULL AND APPROVESTATUS = 'Z'";
			map.put("unno", userBean.getUnNo());
			map.put("maintainType", "D");
		}
		
		if(machineTaskDataBean.getMid()!=null&&!"".equals(machineTaskDataBean.getMid()) ){
			sql +=" and MID=:MID ";
			sqlCount +=" and MID =:MID ";
			map.put("MID", machineTaskDataBean.getMid().trim());
			
		}
		
		if (machineTaskDataBean.getSort() != null) {
			sql += " ORDER BY " + machineTaskDataBean.getSort() + " " + machineTaskDataBean.getOrder();
		}
		
		List<MachineTaskDataModel> machineTaskDataList = machineTaskDataDao.queryMachineTaskData(sql, map, machineTaskDataBean.getPage(), machineTaskDataBean.getRows(), MachineTaskDataModel.class);
		BigDecimal counts = machineTaskDataDao.querysqlCounts(sqlCount, map);
		
		DataGridBean dataGridBean = formatToDataGrid(machineTaskDataList, counts.longValue());
		
		return dataGridBean;
	}

	@Override
	public void updateMachineTaskDataTask(
			MachineTaskDataBean machineTaskDataBean,UserBean user) throws Exception {
		MachineTaskDataModel machineTaskDataModel = machineTaskDataDao.getObjectByID(MachineTaskDataModel.class, machineTaskDataBean.getBmtdID());
		
		if("2".equals(machineTaskDataModel.getType())){
			if(machineTaskDataModel.getChangeType() != null && !"".equals(machineTaskDataModel.getChangeType().trim()) && "2".equals(machineTaskDataModel.getChangeType())){
				MerchantTerminalInfoModel merchantTerminalInfoModel = merchantTerminalInfoDao.getObjectByID(MerchantTerminalInfoModel.class, machineTaskDataModel.getBmtID());
				merchantTerminalInfoModel.setBmaid(machineTaskDataModel.getBmaID());
				merchantTerminalInfoDao.updateObject(merchantTerminalInfoModel);
			}else{
				MerchantTerminalInfoModel merchantTerminalInfoModel = merchantTerminalInfoDao.getObjectByID(MerchantTerminalInfoModel.class, machineTaskDataModel.getBmtID());
				merchantTerminalInfoModel.setMaintainType("D");
				merchantTerminalInfoModel.setMaintainDate(new Date());
				merchantTerminalInfoModel.setMaintainUid(user.getUserID());
				merchantTerminalInfoModel.setStatus(4);
				merchantTerminalInfoDao.updateObject(merchantTerminalInfoModel);
			}
		}else if("3".equals(machineTaskDataModel.getType())){
			MerchantTerminalInfoModel merchantTerminalInfoModel = merchantTerminalInfoDao.getObjectByID(MerchantTerminalInfoModel.class, machineTaskDataModel.getBmtID());
			merchantTerminalInfoModel.setMaintainType("D");
			merchantTerminalInfoModel.setMaintainDate(new Date());
			merchantTerminalInfoModel.setMaintainUid(user.getUserID());
			merchantTerminalInfoDao.updateObject(merchantTerminalInfoModel);
			
//			String hql = "from MerchantTerminalInfoModel where maintainType != :maintainType and mid = :mid";
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("maintainType", "D");
//			map.put("mid", merchantTerminalInfoModel.getMid());
//			List<MerchantTerminalInfoModel> list = merchantTerminalInfoDao.queryObjectsByHqlList(hql, map);
//			if(list == null || list.size() == 0){
//				hql = "from MerchantInfoModel where maintainType != :maintainType and mid = mid";
//				List<MerchantInfoModel> listMerchantInfo = merchantInfoDao.queryObjectsByHqlList(hql, map);
//				if(listMerchantInfo != null && listMerchantInfo.size() > 0){
//					MerchantInfoModel merchantInfoModel = listMerchantInfo.get(0);
//					merchantInfoModel.setMaintainType("D");
//					merchantInfoModel.setMaintainDate(new Date());
//					merchantInfoModel.setMaintainUid(user.getUserID());
//					merchantInfoDao.updateObject(merchantInfoModel);
//				}
//			}
		}
		
		machineTaskDataModel.setTaskConfirmDate(new Date());
		machineTaskDataDao.updateObject(machineTaskDataModel);
	}
	@Override
	public void updateMachineTaskDataTask1(
			MachineTaskDataBean machineTaskDataBean) throws Exception {
		MachineTaskDataModel machineTaskDataModel = machineTaskDataDao.getObjectByID(MachineTaskDataModel.class, machineTaskDataBean.getBmtdID());
		machineTaskDataModel.setTaskStep1Date(new Date());
		machineTaskDataDao.updateObject(machineTaskDataModel);
	}
	@Override
	public void updateMachineTaskDataTask2(
			MachineTaskDataBean machineTaskDataBean) throws Exception {
		MachineTaskDataModel machineTaskDataModel = machineTaskDataDao.getObjectByID(MachineTaskDataModel.class, machineTaskDataBean.getBmtdID());
		machineTaskDataModel.setTaskStep2Date(new Date());
		machineTaskDataDao.updateObject(machineTaskDataModel);
	}

	@Override
	public HSSFWorkbook export(String ids) {
		//String sql = "SELECT * FROM BILL_MACHINETASKDATA m WHERE m.BMTDID IN ("+ids+")";
		String sql = "";
		sql += " SELECT bmtdid.TASKID,bmtdid.UNNO,bmtdid.MID,(SELECT bmtid.TID FROM BILL_MERCHANTTERMINALINFO bmtid WHERE bmtid.BMTID=bmtdid.BMTID) BMTIDNAME, ";
		sql += " (CASE TYPE WHEN '1' THEN '装机' WHEN '2' THEN '换机' WHEN '3' THEN '撤机' ELSE '服务' END) TYPENAME, ";
        sql += " (SELECT bmaid.MACHINEMODEL FROM BILL_MACHINEINFO bmaid WHERE bmtdid.BMAID = bmaid.BMAID) MACHINEMODEL, ";
        sql += " bmtdid.TASKCONFIRMDATE, ";
        sql += " (CASE APPROVESTATUS WHEN 'Y' THEN '已审批' WHEN 'Z' THEN '待审批' WHEN 'K' THEN '退回' ELSE '无需审批' END) APPROVESTATUS, ";
        sql += " bmtdid.TASKSTARTDATE, ";
        sql += " bmtdid.TASKSTEP1DATE, ";
        sql += " bmtdid.TASKSTEP2DATE ";
        sql += "  FROM BILL_MACHINETASKDATA bmtdid WHERE bmtdid.BMTDID IN ("+ids+")";

		List<Map<String, Object>> data = machineTaskDataDao.queryObjectsBySqlList(sql);
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("TASKID");
		excelHeader.add("UNNO");
		excelHeader.add("MID");
		excelHeader.add("BMTIDNAME");
		excelHeader.add("TYPENAME");
		excelHeader.add("MACHINEMODEL");
		excelHeader.add("TASKSTARTDATE");
		excelHeader.add("TASKSTEP1DATE");
		excelHeader.add("TASKSTEP2DATE");
		excelHeader.add("TASKCONFIRMDATE");
		excelHeader.add("APPROVESTATUS");

		headMap.put("TASKID", "工单编号");
		headMap.put("UNNO", "机构编号");
		headMap.put("MID", "商户编号");
		headMap.put("BMTIDNAME", "终端编号");
		headMap.put("TYPENAME", "工单类别");
		headMap.put("MACHINEMODEL", "机具型号");
		headMap.put("TASKSTARTDATE", "开始时间");
		headMap.put("TASKSTEP1DATE", "测试时间");
		headMap.put("TASKSTEP2DATE", "派工时间");
		headMap.put("TASKCONFIRMDATE", "完成时间");
		headMap.put("APPROVESTATUS", "授权状态");

		return ExcelUtil.export("运营工单资料", data, excelHeader, headMap);
	}

	@Override
	public void deleteMachineTaskData(Integer bmtdID,Integer userID) {
		MachineTaskDataModel machineTaskDataModel = machineTaskDataDao.getObjectByID(MachineTaskDataModel.class, bmtdID);
		machineTaskDataModel.setMaintainUid(userID);
		machineTaskDataModel.setMaintainDate(new Date());
		machineTaskDataModel.setMaintainType("D");
		machineTaskDataDao.updateObject(machineTaskDataModel);
	}
	
	
	@Override
	public List<Map<String ,Object>> queryMachineTaskSingleData(MachineTaskDataBean machineTaskDataBean) {
		String sql = "select * from (select m.installedaddress,"
				+ "  m.contactphone, "
				+ " m.tid, " 
				+ " n.remarks, "
				+ " i.machinemodel " 
				+ "  from bill_merchantterminalinfo m, "
				+ " bill_machinetaskdata n, "
				+ " bill_machineinfo   i "
				+ " where n.tid =:tid "
				+ "  and n.BMTID = m.BMTID "
				+ " and n.bmaid = i.bmaid "
				+ " and n.maintaintype !='D' "
				+ "  AND (n.APPROVESTATUS = 'N' OR n.APPROVESTATUS = 'Y')  "
				+ " and n.type = 2  "
				+ " order by n.taskid desc) "
				+ " where rownum = 1";
		Map map = new HashMap<String, Object>();
		map.put("tid", machineTaskDataBean.getTid());
		List<Map<String, Object>> list = machineTaskDataDao.executeSql2(sql,map);
		return list;
	}

	@Override
	public String queryFileNameBybmtdID(String bmtdIDs) {
		String sql="select t.mid ,n.rname from bill_machinetaskdata t ,bill_merchantinfo n where t.mid=n.mid  and t.bmtdid='"+bmtdIDs+"'";
		List<Map<String,Object>> list=machineTaskDataDao.queryObjectsBySqlObject(sql);
		String fileName=list.get(0).get("MID").toString()+"-"+list.get(0).get("RNAME").toString();
		return fileName;
	}

	@Override
	public HSSFWorkbook exportMachineTaskDataJudge(String ids) {
		String sql = " SELECT t.TASKID, t.UNNO,t.MID,nvl(n.TID,'  ') as BMTIDNAME , nvl(t.TID,' ') as TID ,m.MACHINEMODEL, " +
						" (CASE t.changeType WHEN '1' THEN '加号换机' WHEN '2' THEN '不加号换机' ELSE '  ' END) TYPENAME, " +
						" (CASE t.APPROVESTATUS WHEN 'Y' THEN '已审批' WHEN 'Z' THEN '待审批' WHEN 'K' THEN '退回' ELSE '无需审批' END) APPROVESTATUS " +
						"  FROM BILL_MACHINETASKDATA t ,BILL_MACHINEINFO m,BILL_MERCHANTTERMINALINFO n " +
						"  WHERE t.BMAID=m.BMAID AND t.BMTID=n.BMTID AND t.MAINTAINTYPE !='D' AND t.TASKCONFIRMDATE IS NULL " +
						"  AND t.APPROVESTATUS = 'Z'  AND t.BMTDID IN ("+ids+") ";
		List<Map<String, Object>> data = machineTaskDataDao.queryObjectsBySqlList(sql);
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("TASKID");
		excelHeader.add("UNNO");
		excelHeader.add("MID");
		excelHeader.add("BMTIDNAME");
		excelHeader.add("TID");
		excelHeader.add("MACHINEMODEL");
		excelHeader.add("TYPENAME");
		excelHeader.add("APPROVESTATUS");

		headMap.put("TASKID", "工单编号");
		headMap.put("UNNO", "机构编号");
		headMap.put("MID", "商户编号");
		headMap.put("BMTIDNAME", "原终端编号");
		headMap.put("TID", "终端编号");
		headMap.put("MACHINEMODEL", "新机具型号");
		headMap.put("TYPENAME", "工单类别");
		headMap.put("APPROVESTATUS", "授权状态");

		return ExcelUtil.export("运营工单资料", data, excelHeader, headMap);
	}
	
}
