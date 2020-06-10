package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.hrt.frame.constant.PhoneProdConstant;
import com.hrt.util.DateTools;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
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
import com.hrt.biz.bill.entity.pagebean.MerchantTerminalInfoBean;
import com.hrt.biz.bill.service.IMerchantTerminalInfoService;
import com.hrt.frame.dao.sysadmin.ITodoDao;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.dao.sysadmin.IUserDao;
import com.hrt.frame.entity.model.TodoModel;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.model.UserModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;
import com.hrt.gmms.webservice.IGmmsService;
import com.hrt.gmms.webservice.TermAcc;
import com.hrt.util.HttpXmlClient;

/**
 * 代理商商户终端资料
 */
public class MerchantTerminalInfoServiceImpl implements IMerchantTerminalInfoService{

	private IMerchantTerminalInfoDao merchantTerminalInfoDao;
	
	private IGmmsService gsClient;
	
	private IMachineInfoDao machineInfoDao;
	
	private ITodoDao todoDao;
	
	private IMerchantInfoDao merchantInfoDao;
	
	private ITerminalInfoDao terminalInfoDao;
	
	private IUnitDao unitDao;
	
	private IUserDao userDao;
	
	private IMachineTaskDataDao machineTaskDataDao;
	
	private String hybUrl;
	
	private String admAppIp;

	public String getAdmAppIp() {
		return admAppIp;
	}

	public void setAdmAppIp(String admAppIp) {
		this.admAppIp = admAppIp;
	}

	public IMachineInfoDao getMachineInfoDao() {
		return machineInfoDao;
	}

	public void setMachineInfoDao(IMachineInfoDao machineInfoDao) {
		this.machineInfoDao = machineInfoDao;
	}

	public IGmmsService getGsClient() {
		return gsClient;
	}

	public void setGsClient(IGmmsService gsClient) {
		this.gsClient = gsClient;
	}

	public IMerchantTerminalInfoDao getMerchantTerminalInfoDao() {
		return merchantTerminalInfoDao;
	}

	public void setMerchantTerminalInfoDao(
			IMerchantTerminalInfoDao merchantTerminalInfoDao) {
		this.merchantTerminalInfoDao = merchantTerminalInfoDao;
	}
	
	public ITodoDao getTodoDao() {
		return todoDao;
	}

	public void setTodoDao(ITodoDao todoDao) {
		this.todoDao = todoDao;
	}
	
	public IMerchantInfoDao getMerchantInfoDao() {
		return merchantInfoDao;
	}

	public void setMerchantInfoDao(IMerchantInfoDao merchantInfoDao) {
		this.merchantInfoDao = merchantInfoDao;
	}

	public ITerminalInfoDao getTerminalInfoDao() {
		return terminalInfoDao;
	}

	public void setTerminalInfoDao(ITerminalInfoDao terminalInfoDao) {
		this.terminalInfoDao = terminalInfoDao;
	}
	
	public IUnitDao getUnitDao() {
		return unitDao;
	}

	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}
	
	public IMachineTaskDataDao getMachineTaskDataDao() {
		return machineTaskDataDao;
	}

	public void setMachineTaskDataDao(IMachineTaskDataDao machineTaskDataDao) {
		this.machineTaskDataDao = machineTaskDataDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public String getHybUrl() {
		return hybUrl;
	}

	public void setHybUrl(String hybUrl) {
		this.hybUrl = hybUrl;
	}
	
	/**
	 * 手机号隐藏处理
	 * @param isShow true隐藏 false不隐藏
	 * @param phone
	 * @return
	 */
	private String getHiddenPhoneNumber(boolean isShow,String phone){
		if(isShow) {
			if (StringUtils.isEmpty(phone))
				return "*";
			else if (phone.length() == 11)
				return phone.substring(0, 3) + "****" + phone.substring(8);
			else if (phone.length() >= 3 && phone.length() < 11)
				return phone.substring(0, 3) + "***********";
			else
				return "*";
		}else{
			return phone;
		}
	}
	
	

	/**
	 * 方法功能：格式化MerchantTerminalInfoModel为datagrid数据格式
	 * 参数：merchantTerminalInfoList
	 *     total 总记录数
	 * 返回值：
	 * 异常：
	 */
	private DataGridBean formatToDataGrid(List<MerchantTerminalInfoModel> merchantTerminalInfoList, long total) {
		List<MerchantTerminalInfoBean> merchantTerminalInfoBeanList = new ArrayList<MerchantTerminalInfoBean>();
		
		for(MerchantTerminalInfoModel merchantTerminalInfoModel : merchantTerminalInfoList) {
			MerchantTerminalInfoBean merchantTerminalInfoBean = new MerchantTerminalInfoBean();
			BeanUtils.copyProperties(merchantTerminalInfoModel, merchantTerminalInfoBean);
			
			if("Z".equals(merchantTerminalInfoBean.getApproveStatus())){
				merchantTerminalInfoBean.setApproveStatusName("待受理");
			}else if("Y".equals(merchantTerminalInfoBean.getApproveStatus())){
				merchantTerminalInfoBean.setApproveStatusName("已受理");
			}else if("C".equals(merchantTerminalInfoBean.getApproveStatus())){
				merchantTerminalInfoBean.setApproveStatusName("待受理");
			}else{
				merchantTerminalInfoBean.setApproveStatusName("退回");
			}
			//终端信息手机号处理
			merchantTerminalInfoBean.setContactPhone(getHiddenPhoneNumber(true,merchantTerminalInfoBean.getContactPhone()));
			if(merchantTerminalInfoBean.getBmaid()!=null){
				merchantTerminalInfoBean.setBmaidName(bmaidName(merchantTerminalInfoBean.getBmaid()));
			}
			
			if(merchantTerminalInfoBean.getTid() !=null){
				String hql="from TerminalInfoModel t where t.termID=? ";
				TerminalInfoModel tt = terminalInfoDao.queryObjectByHql(hql, new Object[]{merchantTerminalInfoBean.getTid()});
				if(tt!=null){
					merchantTerminalInfoBean.setMerSn(merchantTerminalInfoBean.getSn());//商户输入的SN
					merchantTerminalInfoBean.setSn(tt.getSn());
					merchantTerminalInfoBean.setIsM35(tt.getIsM35());
					merchantTerminalInfoBean.setDepositAmt(tt.getDepositAmt());
				}
			}
			
			UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, merchantTerminalInfoBean.getUnno());
			if(unitModel != null){
				merchantTerminalInfoBean.setUnitName(unitModel.getUnitName());
			}
			
			if(merchantTerminalInfoBean.getApproveUid() != null && !"".equals(merchantTerminalInfoBean.getApproveUid())){
				UserModel user = userDao.getObjectByID(UserModel.class, merchantTerminalInfoBean.getApproveUid());
				if(user != null){
					merchantTerminalInfoBean.setApproveUidName(user.getUserName());
				}
			}
			
			//日期转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(merchantTerminalInfoBean.getMaintainDate() != null){
				merchantTerminalInfoBean.setMaintainDateStr(sdf.format(merchantTerminalInfoBean.getMaintainDate()));
			}
			if(merchantTerminalInfoBean.getApproveDate() != null){
				merchantTerminalInfoBean.setApproveDateStr(sdf.format(merchantTerminalInfoBean.getApproveDate()));
			}
			
			merchantTerminalInfoBeanList.add(merchantTerminalInfoBean);
		}
		
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(merchantTerminalInfoBeanList);
		
		return dgb;
	}

	/**
	 * 方法功能：格式化MerchantTerminalInfoModel为datagrid数据格式
	 * 电话隐藏 i>0
	 */
	private DataGridBean formatToDataGrid(List<MerchantTerminalInfoModel> merchantTerminalInfoList, long total,Integer i) {
		List<MerchantTerminalInfoBean> merchantTerminalInfoBeanList = new ArrayList<MerchantTerminalInfoBean>();
		
		for(MerchantTerminalInfoModel merchantTerminalInfoModel : merchantTerminalInfoList) {
			MerchantTerminalInfoBean merchantTerminalInfoBean = new MerchantTerminalInfoBean();
			BeanUtils.copyProperties(merchantTerminalInfoModel, merchantTerminalInfoBean);
//			if(merchantTerminalInfoModel.getContactPhone()!=null&&i>0){
//				merchantTerminalInfoBean.setContactPhone(merchantTerminalInfoModel.getContactPhone().substring(0, 3)+"****"+merchantTerminalInfoModel.getContactPhone().substring(7));
//			}
			merchantTerminalInfoBean.setContactPhone(getHiddenPhoneNumber(true,merchantTerminalInfoBean.getContactPhone()));
			merchantTerminalInfoBean.setContactTel("");
			if("Z".equals(merchantTerminalInfoBean.getApproveStatus())){
				merchantTerminalInfoBean.setApproveStatusName("待受理");
			}else if("Y".equals(merchantTerminalInfoBean.getApproveStatus())){
				merchantTerminalInfoBean.setApproveStatusName("已受理");
			}else if("C".equals(merchantTerminalInfoBean.getApproveStatus())){
				merchantTerminalInfoBean.setApproveStatusName("待受理");
			}else{
				merchantTerminalInfoBean.setApproveStatusName("退回");
			}
			
			if(merchantTerminalInfoBean.getBmaid()!=null){
				merchantTerminalInfoBean.setBmaidName(bmaidName(merchantTerminalInfoBean.getBmaid()));
			}
			
			if(merchantTerminalInfoBean.getTid() !=null){
				String hql="from TerminalInfoModel t where t.termID=? ";
				TerminalInfoModel tt = terminalInfoDao.queryObjectByHql(hql, new Object[]{merchantTerminalInfoBean.getTid()});
				if(tt!=null){
					merchantTerminalInfoBean.setMerSn(merchantTerminalInfoBean.getSn());//商户输入的SN
					merchantTerminalInfoBean.setSn(tt.getSn());
					merchantTerminalInfoBean.setIsM35(tt.getIsM35());
					merchantTerminalInfoBean.setDepositAmt(tt.getDepositAmt());
				}
			}
			
			UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, merchantTerminalInfoBean.getUnno());
			if(unitModel != null){
				merchantTerminalInfoBean.setUnitName(unitModel.getUnitName());
			}
			
			if(merchantTerminalInfoBean.getApproveUid() != null && !"".equals(merchantTerminalInfoBean.getApproveUid())){
				UserModel user = userDao.getObjectByID(UserModel.class, merchantTerminalInfoBean.getApproveUid());
				if(user != null){
					merchantTerminalInfoBean.setApproveUidName(user.getUserName());
				}
			}
			
			//日期转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(merchantTerminalInfoBean.getMaintainDate() != null){
				merchantTerminalInfoBean.setMaintainDateStr(sdf.format(merchantTerminalInfoBean.getMaintainDate()));
			}
			if(merchantTerminalInfoBean.getApproveDate() != null){
				merchantTerminalInfoBean.setApproveDateStr(sdf.format(merchantTerminalInfoBean.getApproveDate()));
			}
			
			merchantTerminalInfoBeanList.add(merchantTerminalInfoBean);
		}
		
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(merchantTerminalInfoBeanList);
		
		return dgb;
	}
	
	/**
	 * 得到设备名称
	 */
	private String bmaidName(Integer bmaID){
		MachineInfoModel machineInfoModel = machineInfoDao.getObjectByID(MachineInfoModel.class, bmaID);
		if(machineInfoModel != null){
			return machineInfoModel.getMachineModel();
		}
		return "";
	}
	
	/**
	 * 判断终端是否已被使用
	 */
	public int TerminalInfoTerMID(String terMID) throws Exception{
		String hql = "from TerminalInfoModel where termID = :termID";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("termID", terMID);
		List<TerminalInfoModel> timList = terminalInfoDao.queryObjectsByHqlList(hql, map);
		if(timList != null && timList.size() > 0){
			if(timList.get(0).getUsedConfirmDate() == null && timList.get(0).getAllotConfirmDate() != null){
				return 0;
			}
		}
		return 1;
	}
	
	/**
	 * 添加终端（商户入网报单中的修改终端）
	 */
	@Override
	public Integer saveMerchantTerminalInfo(
			MerchantTerminalInfoBean merchantTerminalInfo, UserBean user, String[] values, Integer bmid, String mid)
			throws Exception {
		if(values != null && values.length > 0){
			for (int i = 0; i < values.length; i++) {
				String[] pram=values[i].split("#separate#");
				String hql = "from TerminalInfoModel where termID = :termID";
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("termID", pram[0]);
				MerchantTerminalInfoModel merchantTerminalInfoModel = new MerchantTerminalInfoModel();
				List<TerminalInfoModel> timList = terminalInfoDao.queryObjectsByHqlList(hql,map);
				//判断当前入网TUSN是否重复
				Map<String,String> map2 = new HashMap<String, String>();
				map2.put("sn", pram[8]);
				String post = HttpXmlClient.post(admAppIp+"/AdmApp/bank/bankTermAcc_querySnIfExist.action", map2);
				Integer flag = (Integer) ((Map<String, Object>) JSONObject.parse(post)).get("obj");
				if(flag==1){
					return 0;
				}
				merchantTerminalInfoModel.setSn(pram[8]);
				if(timList != null && timList.size() > 0){
					timList.get(0).setUsedConfirmDate(new Date());
					timList.get(0).setStatus(2);
					terminalInfoDao.updateObject(timList.get(0));
//					if(timList.get(0).getSn()!=null &&!"".equals(timList.get(0).getSn())){
//						merchantTerminalInfoModel.setSn(timList.get(0).getSn());
//					}
				}
				merchantTerminalInfoModel.setMid(mid);
				
				merchantTerminalInfoModel.setTid(pram[0]);
				merchantTerminalInfoModel.setBmaid(Integer.parseInt(pram[1]));
				merchantTerminalInfoModel.setInstalledAddress(pram[2]);
				merchantTerminalInfoModel.setInstalledName(pram[3]);
				merchantTerminalInfoModel.setContactPerson(pram[4]);
				merchantTerminalInfoModel.setContactPhone(pram[5]);
				merchantTerminalInfoModel.setContactTel(pram[6]);
				merchantTerminalInfoModel.setInstalledSIM(pram[7]);
				
				merchantTerminalInfoModel.setUnno(user.getUnNo());
				merchantTerminalInfoModel.setApproveStatus("Z");		//默认为待受理 Y-已受理   Z-待受理  K-不受理
				merchantTerminalInfoModel.setMaintainUid(user.getUserID());
				merchantTerminalInfoModel.setMaintainDate(new Date());
				merchantTerminalInfoModel.setMaintainType("A");		//A-add   M-Modify  D-Delete
				merchantTerminalInfoModel.setStatus(1);
				merchantTerminalInfoDao.saveObject(merchantTerminalInfoModel);
			}
		}
		
		MerchantInfoModel merchantInfoModel = merchantInfoDao.getObjectByID(MerchantInfoModel.class, bmid);
		merchantInfoModel.setApproveUid(user.getUserID());
		merchantInfoModel.setApproveDate(new Date());
		merchantInfoModel.setApproveStatus("W");		//Y-放行   Z-待放行  K-踢回
		merchantInfoDao.updateObject(merchantInfoModel);
		
		Integer batchJobNo = merchantInfoModel.getBmid();
		
		String hqlTodo="from TodoModel where batchJobNo = :batchJobNo and status = :status and bizType = :bizType";
		Map<String, Object> mapTodo = new HashMap<String, Object>();
		mapTodo.put("batchJobNo", batchJobNo.toString());
		mapTodo.put("status", 0);
		mapTodo.put("bizType", "bill_merchant");
		List<TodoModel> todoList = todoDao.queryObjectsByHqlList(hqlTodo, mapTodo);
		if(todoList == null || todoList.size() == 0){
			//添加待办
			TodoModel todo = new TodoModel();
			todo.setUnNo("110003");
			todo.setBatchJobNo(batchJobNo.toString());
			todo.setMsgSendUnit(user.getUnNo());
			todo.setMsgSender(user.getUserID());
			todo.setMsgTopic("待审批商户入网报单");
			todo.setMsgSendTime(new Date());
			todo.setTranCode("10420");	//菜单代码
			todo.setBizType("bill_merchant");
			todo.setStatus(0);			//0-未办，1-已办
			todoDao.saveObject(todo);
		}
		return 1;
	}

	/**
	 * 查询待受理终端
	 */
	@Override
	public DataGridBean queryMerchantTerminalInfoZ(
			MerchantTerminalInfoBean merchantTerminalInfo,UserBean user) throws Exception {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		String sql = "";
		String sqlCount = "";
		Map<String, Object> map = new HashMap<String, Object>();
		/*暂时先注掉，验证下or修改in后查询速度有变化吗20190807ztt
		if("110000".equals(user.getUnNo())){
			sql = " SELECT A.*  FROM  BILL_MERCHANTTERMINALINFO A, BILL_MERCHANTINFO B " +
					" WHERE A.MID = B.MID  AND A.MAINTAINTYPE != :maintainType AND (A.APPROVESTATUS= :approveStatus OR A.APPROVESTATUS= :approveStatus2) AND B.APPROVESTATUS='Y'";
			sqlCount = " SELECT COUNT(*)  FROM  BILL_MERCHANTTERMINALINFO A, BILL_MERCHANTINFO B " +
					" WHERE A.MID = B.MID  AND A.MAINTAINTYPE != :maintainType AND (A.APPROVESTATUS= :approveStatus OR A.APPROVESTATUS= :approveStatus2) AND B.APPROVESTATUS='Y'";
			map.put("maintainType", "D");
			map.put("approveStatus", "Z");
			map.put("approveStatus2", "C");
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
				sql = " SELECT A.*  FROM  BILL_MERCHANTTERMINALINFO A, BILL_MERCHANTINFO B " +
						" WHERE A.MID = B.MID  AND A.MAINTAINTYPE != :maintainType AND (A.APPROVESTATUS= :approveStatus OR A.APPROVESTATUS= :approveStatus2) AND B.APPROVESTATUS='Y'";
				sqlCount = " SELECT COUNT(*)  FROM  BILL_MERCHANTTERMINALINFO A, BILL_MERCHANTINFO B " +
						" WHERE A.MID = B.MID  AND A.MAINTAINTYPE != :maintainType AND (A.APPROVESTATUS= :approveStatus OR A.APPROVESTATUS= :approveStatus2) AND B.APPROVESTATUS='Y'";
				map.put("maintainType", "D");
				map.put("approveStatus", "Z");
				map.put("approveStatus2", "C");
			}
		}else{
			sql = " SELECT A.*  FROM  BILL_MERCHANTTERMINALINFO A, BILL_MERCHANTINFO B " +
					" WHERE A.MID = B.MID  AND A.MAINTAINTYPE != :maintainType AND (A.APPROVESTATUS= :approveStatus OR A.APPROVESTATUS= :approveStatus2) AND B.APPROVESTATUS='Y' AND A.UNNO= :UNNO ";
			sqlCount = " SELECT COUNT(*)  FROM  BILL_MERCHANTTERMINALINFO A, BILL_MERCHANTINFO B " +
					" WHERE A.MID = B.MID  AND A.MAINTAINTYPE != :maintainType AND (A.APPROVESTATUS= :approveStatus OR A.APPROVESTATUS= :approveStatus2) AND B.APPROVESTATUS='Y' AND A.UNNO= :UNNO ";
			map.put("UNNO", user.getUnNo());
			map.put("maintainType", "D");
			map.put("approveStatus", "Z");
			map.put("approveStatus2", "C");
		}
		*/
		if("110000".equals(user.getUnNo())){
			sql = " SELECT A.* FROM BILL_MERCHANTTERMINALINFO A, BILL_MERCHANTINFO B " +
					" WHERE A.MID = B.MID  AND A.MAINTAINTYPE != :maintainType AND A.APPROVESTATUS in ( :approveStatus , :approveStatus2 ) AND B.APPROVESTATUS='Y'";
			sqlCount = " SELECT COUNT(*)  FROM  BILL_MERCHANTTERMINALINFO A, BILL_MERCHANTINFO B " +
					" WHERE A.MID = B.MID  AND A.MAINTAINTYPE != :maintainType AND A.APPROVESTATUS in ( :approveStatus , :approveStatus2 ) AND B.APPROVESTATUS='Y'";
			map.put("maintainType", "D");
			map.put("approveStatus", "Z");
			map.put("approveStatus2", "C");
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
				sql = " SELECT A.* FROM  BILL_MERCHANTTERMINALINFO A, BILL_MERCHANTINFO B " +
						" WHERE A.MID = B.MID  AND A.MAINTAINTYPE != :maintainType AND A.APPROVESTATUS in ( :approveStatus , :approveStatus2 ) AND B.APPROVESTATUS='Y'";
				sqlCount = " SELECT COUNT(*)  FROM  BILL_MERCHANTTERMINALINFO A, BILL_MERCHANTINFO B " +
						" WHERE A.MID = B.MID  AND A.MAINTAINTYPE != :maintainType AND A.APPROVESTATUS in ( :approveStatus , :approveStatus2 ) AND B.APPROVESTATUS='Y'";
				map.put("maintainType", "D");
				map.put("approveStatus", "Z");
				map.put("approveStatus2", "C");
			}
		}else{
			sql = " SELECT A.* FROM  BILL_MERCHANTTERMINALINFO A, BILL_MERCHANTINFO B " +
					" WHERE A.MID = B.MID  AND A.MAINTAINTYPE != :maintainType AND A.APPROVESTATUS in ( :approveStatus , :approveStatus2 ) AND B.APPROVESTATUS='Y' AND A.UNNO= :UNNO ";
			sqlCount = " SELECT COUNT(*)  FROM  BILL_MERCHANTTERMINALINFO A, BILL_MERCHANTINFO B " +
					" WHERE A.MID = B.MID  AND A.MAINTAINTYPE != :maintainType AND A.APPROVESTATUS in ( :approveStatus , :approveStatus2 ) AND B.APPROVESTATUS='Y' AND A.UNNO= :UNNO ";
			map.put("UNNO", user.getUnNo());
			map.put("maintainType", "D");
			map.put("approveStatus", "Z");
			map.put("approveStatus2", "C");
		}
		boolean flag = false;
		if(merchantTerminalInfo.getMid()!=null && !"".equals(merchantTerminalInfo.getMid())){
			sql  += " and A.mid like :mid";
			sqlCount += " and A.mid like :mid";
			map.put("mid", merchantTerminalInfo.getMid()+"%");
			flag = true;
		}
		if(merchantTerminalInfo.getUnno()!=null && !"".equals(merchantTerminalInfo.getUnno())){
			sql  += " and A.unno=:unno";
			sqlCount += " and A.unno=:unno";
			map.put("unno", merchantTerminalInfo.getUnno());
			flag = true;
		}
		if(!flag) {
			sql  += " and A.maintaindate >= sysdate-5";
			sqlCount += " and A.maintaindate >= sysdate-5 ";
		}
		if (merchantTerminalInfo.getSort() != null) {
			sql += " order by " + merchantTerminalInfo.getSort() + " " + merchantTerminalInfo.getOrder();
		}
		long counts = merchantTerminalInfoDao.querysqlCounts2(sqlCount, map);
		List<MerchantTerminalInfoModel> merchantTerminalInfoList = merchantTerminalInfoDao.queryObjectsBySqlLists(sql, map, merchantTerminalInfo.getPage(), merchantTerminalInfo.getRows(), MerchantTerminalInfoModel.class);
		
		DataGridBean dataGridBean = formatToDataGrid(merchantTerminalInfoList, counts);
		
		return dataGridBean;
	}

	/**
	 * 审批通过
	 */
	@Override
	public void updateMerchantTerminalinfosY(MerchantTerminalInfoBean merchantTerminalInfo, UserBean user,String [] ids)throws Exception {
		for(int i = 0; i < ids.length; i++){
			merchantTerminalInfo.setBmtid(Integer.valueOf(ids[i]));
			merchantTerminalInfo.setProcessContext("批量审批");
			updateMerchantTerminalinfoY(merchantTerminalInfo, ((UserBean)user));
		}
	}
	/**
	 * 审批通过
	 */
	@Override
	public void updateMerchantTerminalinfoY(
			MerchantTerminalInfoBean merchantTerminalInfo, UserBean user)
			throws Exception {
		MerchantTerminalInfoModel merchantTerminalInfoModel = merchantTerminalInfoDao.getObjectByID(MerchantTerminalInfoModel.class, merchantTerminalInfo.getBmtid());
		
		//添加机具工单作业
		if(!"C".equals(merchantTerminalInfoModel.getApproveStatus())){
			MachineTaskDataModel machineTaskDataModel = new MachineTaskDataModel();
			machineTaskDataModel.setTaskStartDate(new Date());
			machineTaskDataModel.setUnno(merchantTerminalInfoModel.getUnno());
			machineTaskDataModel.setMid(merchantTerminalInfoModel.getMid());
			machineTaskDataModel.setType("1");
			machineTaskDataModel.setBmtID(merchantTerminalInfoModel.getBmtid());
			machineTaskDataModel.setBmaID(merchantTerminalInfoModel.getBmaid());
			machineTaskDataModel.setMaintainUid(user.getUserID());
			machineTaskDataModel.setMaintainDate(new Date());
			machineTaskDataModel.setMaintainType("A");
			machineTaskDataModel.setApproveUid(user.getUserID());
			machineTaskDataModel.setApproveDate(new Date());
			machineTaskDataModel.setApproveStatus("N");
			machineTaskDataDao.saveObject(machineTaskDataModel);
			
			StringBuffer taskID = new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			taskID.append(sdf.format(new Date()));
			taskID.append("-");
			taskID.append(merchantTerminalInfoModel	.getUnno());
			taskID.append("-");
			DecimalFormat deFormat = new DecimalFormat("000000");
			taskID.append(deFormat.format(machineTaskDataDao.getBmtdID()));
			machineTaskDataModel.setTaskID(taskID.toString());
			machineTaskDataDao.updateObject(machineTaskDataModel);
		}
		merchantTerminalInfoModel.setProcessContext(merchantTerminalInfo.getProcessContext());
		merchantTerminalInfoModel.setApproveUid(user.getUserID());
		merchantTerminalInfoModel.setApproveDate(new Date());
		merchantTerminalInfoModel.setApproveStatus("Y");		//Y-已受理   Z-待受理  K-不受理
		merchantTerminalInfoDao.updateObject(merchantTerminalInfoModel);
		todoDao.editStatusTodo("bill_terminal",merchantTerminalInfo.getBmtid().toString());
		
		String terminalHql="from TerminalInfoModel t where t.termID=?";
		TerminalInfoModel t=terminalInfoDao.queryObjectByHql(terminalHql, new Object[]{merchantTerminalInfoModel.getTid()});
		
		//调用webservice，对GMMS添加布放信息
		TermAcc acc=new TermAcc();
		if(merchantTerminalInfoModel.getSn()!=null&&!"".equals(merchantTerminalInfoModel.getSn())){
			acc.setSn(merchantTerminalInfoModel.getSn());
		}
		if(t!=null){
			if(!"".equals(t.getSnType())&&t.getSnType()!=null){
				acc.setSnType(t.getSnType().toString());
			}
			acc.setSn(t.getSn());
			acc.setRebateType(t.getRebateType());
			acc.setIsReturnCash(t.getIsReturnCash());
			acc.setKeyConfirmDate(new SimpleDateFormat("yyyyMMdd").format(t.getKeyConfirmDate()));
			acc.setOutDate(t.getOutDate());
			acc.setSnsigndate(t.getAcceptDate());
		}
		acc.setCby(user.getLoginName());
		MachineInfoModel infoModel=machineInfoDao.getObjectByID(MachineInfoModel.class, merchantTerminalInfoModel.getBmaid());
		if(infoModel==null){
			acc.setModleId("0");
		}else{
			acc.setModleId(infoModel.getMachineModel());
		}
		acc.setMid(merchantTerminalInfoModel.getMid());
		acc.setTid(merchantTerminalInfoModel.getTid());
		Boolean falg =false;
		String merHql="from MerchantInfoModel m where m.mid=?";
		MerchantInfoModel m=merchantInfoDao.queryObjectByHql(merHql, new Object[]{merchantTerminalInfoModel.getMid()});
		//一代机构号
		String yUnno =m.getUnno();
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
		if(!"".equals(merchantTerminalInfoModel.getDepositFlag())&&merchantTerminalInfoModel.getDepositFlag()!=null){
			if(t!=null && t.getRebateType()!=null){
				Map configInfo = getSelfTermConfig(t.getRebateType(),t.getDepositAmt());
				if(configInfo!=null) {
					if (configInfo.containsKey("depositamt")) {
						acc.setDepositAmt(configInfo.get("depositamt") != null ?
								Integer.parseInt(configInfo.get("depositamt").toString()) : null);
					}
					if (configInfo.containsKey("tradeamt")) {
                        acc.setFirmoney(Double.parseDouble(configInfo.get("tradeamt").toString()));
					}
					if (configInfo.containsKey("adm_deposit_flag")) {
						acc.setDepositFlag(configInfo.get("adm_deposit_flag") != null ?
								Integer.parseInt(configInfo.get("adm_deposit_flag").toString()) : null);
						merchantTerminalInfoModel.setDepositFlag(configInfo.get("app_deposit_flag") != null ?
								Integer.parseInt(configInfo.get("app_deposit_flag").toString()) : null);
						merchantTerminalInfoDao.updateObject(merchantTerminalInfoModel);
					}
				}
			}
		}
		if(t!=null && t.getRebateType()==null && t.getDepositAmt()!=null && t.getDepositAmt()>0){
			acc.setDepositAmt(t.getDepositAmt());
		}
		if(m!=null&&("1".equals(m.getIsM35())||m.getIsM35()==1)){
			Integer typeFlag;
			if("000000".equals(m.getSettMethod())){
				typeFlag=2;
			}else{
				typeFlag=3;
			}
			acc.setTypeflag(typeFlag);
			falg=gsClient.saveTermAccSub(acc,"hrtREX1234.Java");
		}else{
			falg=gsClient.saveTermAcc(acc,"hrtREX1234.Java");
		}
		if(!falg){
			throw new IllegalAccessError("调用webservice失败");
		}
	}

	/**
	 * 获取活动配置
	 * @param rebateType 活动类型
	 * @param amt 终端押金金额
	 * @return Map
	 * 	@key depositamt 押金金额
	 * 	@key tradeamt   最低消费金额
	 * 	@key adm_deposit_flag 综合推送值
	 * 	@key app_deposit_flag app推送值
	 */
	private Map getSelfTermConfig(Integer rebateType,Integer amt){
		Map result = new HashMap();
		String amtSql="select k.depositamt,k.tradeamt,k.remark,k.configinfo from bill_purconfigure k where k.type=3 and k.valueinteger=:valueinteger ";
		Map amtMap = new HashMap();
		amtMap.put("valueinteger",rebateType);
		List<Map<String,Object>> listAmt = terminalInfoDao.queryObjectsBySqlListMap2(amtSql,amtMap);
		if(listAmt.size()==1){
			// 默认押金
			result.put("depositamt",listAmt.get(0).get("DEPOSITAMT"));
			// 默认最低消费
			result.put("tradeamt",listAmt.get(0).get("TRADEAMT"));
			if(null!=listAmt.get(0).get("REMARK")) {
				// {"app_deposit_flag":5,"adm_deposit_flag",5}
				com.alibaba.fastjson.JSONObject configJson = com.alibaba.fastjson.JSONObject.parseObject(listAmt.get(0).get("REMARK").toString());
				if (configJson.containsKey("adm_deposit_flag") && null != configJson.get("adm_deposit_flag")) {
					result.put("adm_deposit_flag", configJson.getInteger("adm_deposit_flag"));
					result.put("app_deposit_flag", configJson.getInteger("app_deposit_flag"));
				}
			}
			if(null!=listAmt.get(0).get("CONFIGINFO")) {
				// {"tradeAmt":{"294":"300.00"}}
				com.alibaba.fastjson.JSONObject configJson = com.alibaba.fastjson.JSONObject.parseObject(listAmt.get(0).get("CONFIGINFO").toString());
				com.alibaba.fastjson.JSONObject amtJson = configJson.getJSONObject("tradeAmt");
				if (amt!=null && amtJson.containsKey(amt+"") && null != amtJson.get(amt+"")) {
					// 自定义最低消费
					result.put("depositamt",amt);
					result.put("tradeamt",amtJson.getString(amt+""));
				}
			}
		}
		return result;
	}

	/**
	 * 退回
	 */
	@Override
	public void updateMerchantTerminalinfoK(
			MerchantTerminalInfoBean merchantTerminalInfo, UserBean user)
			throws Exception {
		MerchantTerminalInfoModel merchantTerminalInfoModel = merchantTerminalInfoDao.getObjectByID(MerchantTerminalInfoModel.class, merchantTerminalInfo.getBmtid());
		
		String hql = "from TerminalInfoModel where termID = :termID";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("termID", merchantTerminalInfoModel.getTid());
		List<TerminalInfoModel> terminalInfoModelList = terminalInfoDao.queryObjectsByHqlList(hql, param);
		if(terminalInfoModelList != null && terminalInfoModelList.size() > 0){
			TerminalInfoModel terminalInfoModel = terminalInfoModelList.get(0);
			terminalInfoModel.setUsedConfirmDate(null);
			terminalInfoModel.setStatus(1);
			terminalInfoDao.updateObject(terminalInfoModel);
		}
		
		merchantTerminalInfoModel.setProcessContext(merchantTerminalInfo.getProcessContext());
		merchantTerminalInfoModel.setApproveUid(user.getUserID());
		merchantTerminalInfoModel.setApproveDate(new Date());
		merchantTerminalInfoModel.setApproveStatus("K");		//Y-已受理   Z-待受理  K-不受理
		merchantTerminalInfoDao.updateObject(merchantTerminalInfoModel);
		todoDao.editStatusTodo("bill_terminal",merchantTerminalInfo.getBmtid().toString());
	}

	/**
	 * 根据Mid查询终端
	 */
	@Override
	public DataGridBean queryMerchantTerminalInfoMid(
			MerchantTerminalInfoBean merchantTerminalInfo,String unno) throws Exception {
		String hql = "from MerchantTerminalInfoModel m where m.unno = :unno and m.mid = :mid and m.maintainType != :maintainType and m.approveStatus = :approveStatus ";
		String hqlCount = "select count(*) from MerchantTerminalInfoModel m where m.unno = :unno and m.mid = :mid and m.maintainType != :maintainType and m.approveStatus = :approveStatus ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unno", unno);
		map.put("mid", merchantTerminalInfo.getMid());
		map.put("maintainType", "D");
		map.put("approveStatus", "Y");
		
		if (merchantTerminalInfo.getSort() != null) {
			hql += " order by " + merchantTerminalInfo.getSort() + " " + merchantTerminalInfo.getOrder();
		}
		
		long counts = merchantTerminalInfoDao.queryCounts(hqlCount, map);
		List<MerchantTerminalInfoModel> merchantTerminalInfoList = merchantTerminalInfoDao.queryMerchantTerminalInfo(hql, map, merchantTerminalInfo.getPage(), merchantTerminalInfo.getRows());
		
		DataGridBean dataGridBean = formatToDataGrid(merchantTerminalInfoList, counts);
		
		return dataGridBean;
	}

	/**
	 * 查询终端分页
	 */
	@Override
	public DataGridBean queryMerchantTerminalInfo(
			MerchantTerminalInfoBean merchantTerminalInfo, String unno)
			throws Exception {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, unno);
		String hql = "";
		String hqlCount = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if("110000".equals(unno)){
			hql = "from MerchantTerminalInfoModel m where m.maintainType != :maintainType and status=:status ";
			hqlCount = "select count(*) from MerchantTerminalInfoModel m where m.maintainType != :maintainType and status=:status ";
			map.put("maintainType", "D");
			map.put("status", 2);
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
				hql = "from MerchantTerminalInfoModel m where m.maintainType != :maintainType and status=:status ";
				hqlCount = "select count(*) from MerchantTerminalInfoModel m where m.maintainType != :maintainType and status=:status ";
				map.put("maintainType", "D");
				map.put("status", 2);
			}
		}else{
			hql = "from MerchantTerminalInfoModel m where m.unno = :unno and m.maintainType != :maintainType and status=:status ";
			hqlCount = "select count(*) from MerchantTerminalInfoModel m where m.unno = :unno and m.maintainType != :maintainType and status=:status ";
			map.put("unno", unno);
			map.put("maintainType", "D");
			map.put("status", 2);
		}
		
		if(merchantTerminalInfo.getMid()!=null && !"".equals(merchantTerminalInfo.getMid().trim())){
			hql += " and mid = :mid";
			hqlCount += " and mid = :mid";
			map.put("mid", merchantTerminalInfo.getMid());
		}
		
		if(merchantTerminalInfo.getTid()!=null && !"".equals(merchantTerminalInfo.getTid().trim())){
			hql += " and tid = :tid";
			hqlCount += " and tid = :tid";
			map.put("tid", merchantTerminalInfo.getTid());
		}
		if(merchantTerminalInfo.getMaintainDateStr()!=null && !"".equals(merchantTerminalInfo.getMaintainDateStr().trim())){
			hql += " and maintainDate >= to_date(:maintainDate1,'yyyy-MM-dd')";
			hqlCount += " and maintainDate >= to_date(:maintainDate1,'yyyy-MM-dd')";
			map.put("maintainDate1", merchantTerminalInfo.getMaintainDateStr());
			hql += " and maintainDate >= to_date('2018-01-01','yyyy-MM-dd')";
			hqlCount += " and maintainDate >= to_date('2018-01-01','yyyy-MM-dd')";
		}
		if(merchantTerminalInfo.getApproveDateStr()!=null && !"".equals(merchantTerminalInfo.getApproveDateStr().trim())){
			hql += " and maintainDate <= to_date(:maintainDate2,'yyyy-MM-dd')";
			hqlCount += " and maintainDate <= to_date(:maintainDate2,'yyyy-MM-dd')";
			map.put("maintainDate2", merchantTerminalInfo.getApproveDateStr());
		}
		
		if (merchantTerminalInfo.getSort() != null) {
			hql += " order by " + merchantTerminalInfo.getSort() + " " + merchantTerminalInfo.getOrder();
		}
		long counts = merchantTerminalInfoDao.queryCounts(hqlCount, map);
		List<MerchantTerminalInfoModel> merchantTerminalInfoList = merchantTerminalInfoDao.queryMerchantTerminalInfo(hql, map, merchantTerminalInfo.getPage(), merchantTerminalInfo.getRows());
		
		DataGridBean dataGridBean = formatToDataGrid(merchantTerminalInfoList, counts);
		
		return dataGridBean;
	}

	/**
	 * 导出终端excel
	 */
	@Override
	public HSSFWorkbook export(String ids) {
		String sql = " SELECT m.RNAME, to_char(t.APPROVEDATE,'yyyy-MM-dd') as APPROVEDATE, m.DEPOSIT,m.CHARGE,m.BADDR, m.MID, t.TID,"
				+ "a.SALENAME, e.MACHINEMODEL,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME FROM BILL_MERCHANTTERMINALINFO t, BILL_MERCHANTINFO         m,"
				+ "BILL_MACHINEINFO          e, BILL_AGENTSALESINFO       a WHERE t.MID = m.MID"
				+ " AND e.BMAID = t.BMAID  AND a.BUSID = m.MAINTAINUSERID  AND t.STATUS = 2 AND t.BMTID IN ("
				+ ids + ") ";
		
		//String sql1="SELECT COLUMN_NAME FROM user_tab_columns WHERE table_name = 'BILL_MERCHANTINFO'";
		List<Map<String, Object>> data=merchantTerminalInfoDao.queryObjectsBySqlList(sql);
		List<String> excelHeader=new ArrayList<String>();
		Map<String, Object> headMap=new HashMap<String, Object>();
		
		excelHeader.add("RNAME");
		excelHeader.add("APPROVEDATE");
		excelHeader.add("DEPOSIT");
		excelHeader.add("CHARGE");
		excelHeader.add("BADDR");
		excelHeader.add("MID");
		excelHeader.add("TID");
		excelHeader.add("SALENAME");
		excelHeader.add("MACHINEMODEL");
		excelHeader.add("APPROVEUIDNAME");
		
		headMap.put("RNAME", "商户名称");
		headMap.put("APPROVEDATE", "批准日期");
		headMap.put("DEPOSIT", "押金");
		headMap.put("CHARGE", "服务费");
		headMap.put("BADDR", "营业地址");
		headMap.put("MID", "公司mid");
		headMap.put("TID", "公司tid");
		headMap.put("SALENAME", "销售");
		headMap.put("MACHINEMODEL", "机型");
		headMap.put("APPROVEUIDNAME", "受理人员");
		return ExcelUtil.export("装机资料", data, excelHeader, headMap);
	}
	/**
	 * 一清增机导入（页面增机申请）
	 */
	@Override
	public  List<Object> addMerchantTerminalInfoImport(String xlsfile, UserBean user, String fileName){
		List<Object> list = new ArrayList<Object>();
		try{
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<Object> merTerList = new ArrayList<Object>();
			Map<String, String> logMap = new HashMap<String, String>();
			for(int i = 1; i < sheet.getLastRowNum()+1; i++){
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell;
				MerchantInfoModel merch = null;
				MerchantTerminalInfoModel merTer=new MerchantTerminalInfoModel();
				cell = row.getCell((short)0);
				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
					//1.unno
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					String unno = row.getCell((short)0).getStringCellValue().trim();
					merTer.setUnno(unno);
					//2.mid
					row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
					String mid = row.getCell((short)1).getStringCellValue().trim();
					merTer.setMid(mid);
					//3.tid
					row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
					String tid = row.getCell((short)2).getStringCellValue().trim();
					merTer.setTid(tid);
					//查询商户
					String hql = "select m from MerchantInfoModel m where m.mid='"+mid+"' and m.unno='"+unno+"'";
					List<MerchantInfoModel> list2 = merchantInfoDao.queryObjectsByHqlList(hql);
					if(list2.size()==0){
						list.add(new String[]{mid,tid,"商户不存在"});
					}else{
						//查询导入文件之上记录查重
						if(!tid.equals(logMap.get(mid))){
							//查询此商户下是否有重复tid
							merch = list2.get(0);
							String hql1 = "select count(m) from MerchantTerminalInfoModel m where m.mid='"+mid+"' and m.unno='"+unno+"' and m.tid='"+tid+"'";
							long l = merchantInfoDao.queryCounts(hql1);
							if(l<1){
								logMap.put(mid, tid);
								merTer.setInstalledSIM("1");
								merTer.setStatus(1);
								merTer.setMaintainDate(new Date());
								merTer.setMaintainUid(user.getUserID());
								merTer.setApproveStatus("Z");
								merTer.setMaintainType("A");
								merTer.setBmaid(184);
								merTer.setInstalledAddress(merch.getBaddr()+".");
								merTerList.add(merTer);
							}else{
								list.add(new String[]{mid,tid,"tid存在于系统"});
							}
						}else{
							list.add(new String[]{mid,tid,"tid存在于导入文件"});
						}
					}
				}
			}
			merchantTerminalInfoDao.batchSaveObject(merTerList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 添加终端（页面增机申请）
	 */
	@Override
	public Integer saveInfo(MerchantTerminalInfoBean merchantTerminalInfo,
			UserBean user, String[] values) {
		
		Date date=new Date();
		for (int i = 0; i < values.length; i++) {
			String[] pram=values[i].split("#separate#");
			String hql = "from TerminalInfoModel where termID = :termID";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("termID", pram[1]);
			MerchantTerminalInfoModel info=new MerchantTerminalInfoModel();
			List<TerminalInfoModel> timList = terminalInfoDao.queryObjectsByHqlList(hql,map);
			info.setSn(pram[9]);
			//判断当前入网TUSN是否重复
			Map<String,String> map2 = new HashMap<String, String>();
			map2.put("sn", pram[9]);
			String post = HttpXmlClient.post(admAppIp+"/AdmApp/bank/bankTermAcc_querySnIfExist.action", map2);
			Integer flag = (Integer) ((Map<String, Object>) JSONObject.parse(post)).get("obj");
			if(flag==1){
				return 0;
			}
			if(timList != null && timList.size() > 0){
				timList.get(0).setUsedConfirmDate(new Date());
				timList.get(0).setStatus(2);
				terminalInfoDao.updateObject(timList.get(0));
//				if(timList.get(0).getSn()!=null&&!"".equals(timList.get(0).getSn())){
//					info.setSn(timList.get(0).getSn());
//				}
			}
			info.setUnno(user.getUnNo());
			info.setMid(pram[0]);
			info.setTid(pram[1]);
			info.setBmaid(Integer.valueOf(pram[2]));
			info.setInstalledAddress(pram[3]);
			if(!"null".equals(pram[4])){
				info.setInstalledName(pram[4]);
			}
			if(!"null".equals(pram[5])){
				info.setContactPerson(pram[5]);
			}
			if(!"null".equals(pram[6])){
				info.setContactPhone(pram[6]);
			}
			if(!"null".equals(pram[7])){
				info.setContactTel(pram[7]);
			}
			if(!"null".equals(pram[8])){
				info.setInstalledSIM(pram[8]);
			}
			
			info.setMaintainUid(user.getUserID());
			info.setMaintainType("A");
			info.setMaintainDate(date);
			info.setApproveStatus("Z");
			info.setStatus(2);
			merchantTerminalInfoDao.saveObject(info);
		}
		
		Integer batchJobNo = merchantTerminalInfoDao.getBmtid();
		
		//添加待办
		TodoModel todo = new TodoModel();
		todo.setUnNo("110003");
		todo.setBatchJobNo(batchJobNo.toString());
		todo.setMsgSendUnit(user.getUnNo());
		todo.setMsgSender(user.getUserID());
		todo.setMsgTopic("待审批商户增机申请");
		todo.setMsgSendTime(new Date());
		todo.setTranCode("10430");	//菜单代码
		todo.setBizType("bill_terminal");
		todo.setStatus(0);			//0-未办，1-已办
		todoDao.saveObject(todo);
		return 1;
	}

	/**
	 * 根据id得到终端信息
	 */
	@Override
	public MerchantTerminalInfoModel getInfoModel(Integer id) {
		return merchantTerminalInfoDao.getObjectByID(MerchantTerminalInfoModel.class, id);
	}
	
	/**
	 * 查询终端不分页平台使用
	 */
	@Override
	public DataGridBean queryMerchantTerminalInfoBmid(MerchantTerminalInfoBean merchantTerminalInfoBean,UserBean user)
			throws Exception {
		String hql = "from MerchantTerminalInfoModel m where m.bmtid in (select max(bmtid) from MerchantTerminalInfoModel " +
						"where mid = :mid and maintainType != :maintainType and approveStatus = :approveStatus  group by mid,tid) ";
		String hqlCount = "select count(*) from MerchantTerminalInfoModel m where m.bmtid in (select max(bmtid) from MerchantTerminalInfoModel " +
						"where mid = :mid and maintainType != :maintainType and approveStatus = :approveStatus  group by mid,tid) ";
//		String hql = "from MerchantTerminalInfoModel m where m.mid = :mid and m.maintainType != :maintainType and m.approveStatus = :approveStatus ";
//		String hqlCount = "select count(*) from MerchantTerminalInfoModel m where m.mid = :mid and m.maintainType != :maintainType and m.approveStatus = :approveStatus ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mid", merchantTerminalInfoBean.getMid());
		map.put("maintainType", "D");
		map.put("approveStatus", "Y");
		
		if(merchantTerminalInfoBean.getTid() != null && !"".equals(merchantTerminalInfoBean.getTid())){
			hql += "  and tid = :tid";
			hqlCount += " and tid = :tid";
			map.put("tid", merchantTerminalInfoBean.getTid());
		}
		
		long counts = merchantTerminalInfoDao.queryCounts(hqlCount, map);
		List<MerchantTerminalInfoModel> merchantTerminalInfoList = merchantTerminalInfoDao.queryObjectsByHqlList(hql,map);
		//是否隐藏手机号信息
		Integer i = merchantTerminalInfoDao.querysqlCounts2("select count(1) from sys_configure a where a.groupname='phoneFilter' and a.minfo1='"+user.getUnNo()+"'", null);
		DataGridBean dataGridBean = formatToDataGrid(merchantTerminalInfoList, counts,i);
		return dataGridBean;
	}
	/**
	 * 查询终端不分页app使用
	 */
	@Override
	public DataGridBean queryMerchantTerminalInfoBmid(MerchantTerminalInfoBean merchantTerminalInfoBean)
			throws Exception {
		String hql = "from MerchantTerminalInfoModel m where m.bmtid in (select max(bmtid) from MerchantTerminalInfoModel " +
						"where mid = :mid and maintainType != :maintainType and approveStatus = :approveStatus  group by mid,tid) ";
		String hqlCount = "select count(*) from MerchantTerminalInfoModel m where m.bmtid in (select max(bmtid) from MerchantTerminalInfoModel " +
						"where mid = :mid and maintainType != :maintainType and approveStatus = :approveStatus  group by mid,tid) ";
//		String hql = "from MerchantTerminalInfoModel m where m.mid = :mid and m.maintainType != :maintainType and m.approveStatus = :approveStatus ";
//		String hqlCount = "select count(*) from MerchantTerminalInfoModel m where m.mid = :mid and m.maintainType != :maintainType and m.approveStatus = :approveStatus ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mid", merchantTerminalInfoBean.getMid());
		map.put("maintainType", "D");
		map.put("approveStatus", "Y");
		
		if(merchantTerminalInfoBean.getTid() != null && !"".equals(merchantTerminalInfoBean.getTid())){
			hql += "  and tid = :tid";
			hqlCount += " and tid = :tid";
			map.put("tid", merchantTerminalInfoBean.getTid());
		}
		
		long counts = merchantTerminalInfoDao.queryCounts(hqlCount, map);
		List<MerchantTerminalInfoModel> merchantTerminalInfoList = merchantTerminalInfoDao.queryObjectsByHqlList(hql,map);
		
		DataGridBean dataGridBean = formatToDataGrid(merchantTerminalInfoList, counts,1);
		return dataGridBean;
	}

	/**
	 * 查询终端不分页(押金)
	 */
	@Override
	public List<Map<String,Object>> queryMerchantTerminalInfoBmidPhone(MerchantTerminalInfoBean merchantTerminalInfoBean)
			throws Exception {
		String sql = "select nvl(m.actStatus,0)||'' actStatus,m.MID,m.TID,m.unno,m.contactPerson,'' as contactPhone,m.installedAddress,nvl(m.depositFlag,0) as depositFlag," +
				"nvl((select k.tradeamt from bill_purconfigure k where k.type=3 and k.valueinteger=t.rebatetype),0) depositAmt,"+
				"nvl((select k.depositamt from bill_purconfigure k where k.type=3 and k.valueinteger=t.rebatetype),0) aaMt,"+
				"nvl(t.depositAmt,0) depositAmt1," +
				"t.sn,t.rebatetype,t.isselect," +
				" case when t.rebatetype in ('14','15','70') and sysdate>=to_date('20200101','yyyyMMdd') then 1 else 0 end as isExpire " +
				"from bill_merchantterminalinfo m,bill_terminalinfo t where m.tid=t.termID " +
				" and m.mid=:mid and m.maintainType != :maintainType and m.approveStatus = :approveStatus ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mid", merchantTerminalInfoBean.getMid());
		map.put("maintainType", "D");
		map.put("approveStatus", "Y");
		
		if(merchantTerminalInfoBean.getTid() != null && !"".equals(merchantTerminalInfoBean.getTid())){
			sql += "  and tid = :tid";
			map.put("tid", merchantTerminalInfoBean.getTid());
		}
		
		List<Map<String,Object>> result = merchantTerminalInfoDao.queryObjectsBySqlListMap2(sql, map);
		boolean scanTxnLimit=true;
		// @author:lxg-20200316 由于一个活动有多个押金情况
		for (Map<String, Object> stringMap : result) {
			String amtSql="select t.configinfo,t.SPECIALINFO,t.rule_info from bill_purconfigure t where t.type=3 and t.valueinteger=:rebateType ";
			Map amtMap = new HashMap();
            String depositflag = stringMap.get("DEPOSITFLAG").toString();
			if(stringMap.get("REBATETYPE")!=null) {
				String rebateType = stringMap.get("REBATETYPE") + "";
				String depositAmt1 = null != stringMap.get("DEPOSITAMT1") ? stringMap.get("DEPOSITAMT1").toString() : "";
				amtMap.put("rebateType", rebateType);
				List<Map<String, String>> amtList = merchantTerminalInfoDao.queryObjectsBySqlListMap(amtSql, amtMap);
				
				if (amtList.size() == 1) {
					// {"tradeAmt":{"39":"51.00"}}
					String configInfo = amtList.get(0).get("CONFIGINFO");
					if (StringUtils.isNotEmpty(configInfo) && StringUtils.isNotEmpty(depositAmt1)) {
						JSONObject jsonObject = JSON.parseObject(configInfo);
						JSONObject tradeJSON = jsonObject.getJSONObject("tradeAmt");
						if (tradeJSON.containsKey(depositAmt1)) {
							stringMap.put("DEPOSITAMT", tradeJSON.getString(depositAmt1));
						}
					}

					// 给app展示的押金金额 depositAmt1为设备表里押金
                    String specialInfo = amtList.get(0).get("SPECIALINFO");
                    if(StringUtils.isNotEmpty(specialInfo)){
                        JSONObject depostDef = JSON.parseObject(specialInfo);
                        if (depostDef!=null && depostDef.containsKey(depositAmt1)) {
                            stringMap.put("DEPOSITAMT1", depostDef.getInteger(depositAmt1));
                        }else{
                            // 设置配置表押金金额
                            stringMap.put("DEPOSITAMT1", stringMap.get("AAMT"));
                        }
                    }

                    // 是否限制首笔扫码交易
					String ruleInfo = amtList.get(0).get("RULE_INFO");
                    if(StringUtils.isNotEmpty(ruleInfo)){
						JSONObject jsonRule = JSON.parseObject(ruleInfo);
						// {"scan_txn_limit":"1"} 1为限制,0不限制
						if(jsonRule!=null && jsonRule.getString("scan_txn_limit")!=null){
                            if("0".equals(jsonRule.getString("scan_txn_limit"))){
                                // 0不限制扫码交易
                                stringMap.put("ACTSTATUS","1");
                            }
							if("1".equals(jsonRule.getString("scan_txn_limit"))){
							    // 1PRO限制扫码交易 显示关联表的激活状态
							}else if("2".equals(jsonRule.getString("scan_txn_limit"))){
                                // 2秒到限制扫码交易
                                boolean mdScanTxnLimit = ".2.4.6.".contains("."+depositflag+".");
                                if(mdScanTxnLimit){
                                    stringMap.put("ACTSTATUS","1");
                                }else{
                                    stringMap.put("ACTSTATUS","0");
                                }
                            }else{
								scanTxnLimit=false;
							}
						}else{
							scanTxnLimit=false;
						}
						
						//pro增加返回字段提示
						if(jsonRule!=null && jsonRule.getString("info_type")!=null && "1".equals(jsonRule.getString("info_type"))) {
							String proAmtSql="select t.rule_info,t.configinfo,t.specialinfo from "
									+ "bill_purconfigure t where t.type=12 and t.valueinteger=:rebateType";
							List<Map<String, String>> proAmtList = merchantTerminalInfoDao.queryObjectsBySqlListMap(proAmtSql, amtMap);
							if(proAmtList.size() == 1) {
								BigDecimal bigDecimal = new BigDecimal("0");
								if(stringMap.get("DEPOSITAMT")!=null) {
									bigDecimal = new BigDecimal(stringMap.get("DEPOSITAMT").toString());
								}
								if(stringMap.get("DEPOSITAMT1")!=null&&!"0".equals(stringMap.get("DEPOSITAMT1").toString())
										&&stringMap.get("DEPOSITAMT")!=null&&bigDecimal.compareTo(BigDecimal.ZERO)>0) {
									//有押金有首笔最低
									if(StringUtils.isNotEmpty(proAmtList.get(0).get("CONFIGINFO"))) {
										stringMap.put("ACTIVETIPSINFO",proAmtList.get(0).get("CONFIGINFO")
												.replace("X", ""+stringMap.get("DEPOSITAMT1").toString()+"")
												.replace("Y", ""+stringMap.get("DEPOSITAMT").toString()+""));
									}
								}
								if(stringMap.get("DEPOSITAMT1")!=null&&!"0".equals(stringMap.get("DEPOSITAMT1").toString())
										&&(stringMap.get("DEPOSITAMT")==null||bigDecimal.compareTo(BigDecimal.ZERO)<=0)) {
									//有押金无首笔
									if(StringUtils.isNotEmpty(proAmtList.get(0).get("SPECIALINFO"))) {
										stringMap.put("ACTIVETIPSINFO",proAmtList.get(0).get("SPECIALINFO")
												.replace("X", ""+stringMap.get("DEPOSITAMT1").toString()+""));
									}
								}
								if(stringMap.get("DEPOSITAMT")!=null&&bigDecimal.compareTo(BigDecimal.ZERO)>0
										&&(stringMap.get("DEPOSITAMT1")==null||"0".equals(stringMap.get("DEPOSITAMT1").toString()))) {
									//无押金有首笔
									if(StringUtils.isNotEmpty(proAmtList.get(0).get("RULE_INFO"))) {
										stringMap.put("ACTIVETIPSINFO",proAmtList.get(0).get("RULE_INFO")
												.replace("Y", ""+stringMap.get("DEPOSITAMT").toString()+""));
									}
								}
								if(((stringMap.get("DEPOSITAMT")==null||bigDecimal.compareTo(BigDecimal.ZERO)<=0)
										&&(stringMap.get("DEPOSITAMT1")==null||"0".equals(stringMap.get("DEPOSITAMT1").toString())))
										||stringMap.get("ACTIVETIPSINFO")==null) {
									//无押金无首笔或者是数据库这个提示信息没有配置相应信息
									stringMap.put("ACTIVETIPSINFO","");
								}
							}else {
								stringMap.put("ACTIVETIPSINFO","");
							}
						}else {
							stringMap.put("ACTIVETIPSINFO","");
						}
						
						
					}else{
						scanTxnLimit=false;
					}
				}else {
					scanTxnLimit=false;
				}
			}else{
				scanTxnLimit=false;
			}
			if(!scanTxnLimit){
				stringMap.put("ACTSTATUS","1");
			}
            stringMap.remove("AAMT");
		}
		return result;
	}

	@Override
	public Integer queryMerchantIsAct(MerchantTerminalInfoBean merchantTerminalInfoBean){
        Map param = new HashMap();
        param.put("mid",merchantTerminalInfoBean.getMid());
	    // @author:lxg-20200407 商户名下存在remark=2的活动，存在未激活的设备，返回未激活
        String aSql="select count(1) from bill_terminalinfo a, bill_merchantterminalinfo b where a.termid=b.tid and a.rebatetype in (" +
                "  select m.valueinteger||''  from  bill_purconfigure m where m.type=7 and m.remark='2' ) " +
                " and b.maintaintype!='D' and b.mid=:mid and a.activaday is null and (a.isreturncash=0 or a.isreturncash is null) ";
        Integer aCount = merchantTerminalInfoDao.querysqlCounts2(aSql,param);
        if(aCount>0){
            return 0;
        }

		// @author:lxg-20191012 活动24 25 56限制,商户名下存在已返现状态,返回激活1,否则判断名下是否存在所限制的活动,存在返回未激活0,否则返回激活状态1
		String hasActSql = "select a.sn,b.mid,a.isreturncash from bill_terminalinfo a, bill_merchantterminalinfo b " +
				" where a.termid=b.tid and a.isreturncash in (1,2) and b.mid=:mid";
		List<Map<String,String>> hasActList = merchantTerminalInfoDao.queryObjectsBySqlListMap(hasActSql, param);
		if(hasActList.size()>0){
			return 1;
		}else{
			String limitActSql = "select a.rebatetype,b.mid " +
					"   from bill_terminalinfo a, bill_merchantterminalinfo b " +
					"  where a.termid=b.tid and b.mid=:mid " +
					"   and a.rebatetype in (select c.valuestring from bill_purconfigure c where c.type=7 and c.status=1 and c.remark!='2' )";
			List<Map<String,String>> limitActList = merchantTerminalInfoDao.queryObjectsBySqlListMap(limitActSql, param);
			if(limitActList.size()>0){
				return 0;
			}else{
				return 1;
			}
		}
	}

	@Override
	public Map serachTerminalIsActByTid(MerchantTerminalInfoBean merchantTerminalInfoBean) throws Exception{
		Map result=new HashMap();
        // @author:lxg-20200407 是否是限制交易的活动设备,否返回激活;是判断是否是激活设备,是激活设备，返回激活；否则查询交易是否激活
        String sql1="select count(1) from bill_purconfigure b where b.type=7 and b.status=1 and b.valuestring in (" +
                "  select k.rebatetype from bill_terminalinfo k where k.termid=:tid)";
        Map map1=new HashMap();
        map1.put("tid",merchantTerminalInfoBean.getTid());
        Integer count1 = merchantTerminalInfoDao.querysqlCounts2(sql1,map1);
        // 是交易限制的设备
        if(count1>0){
            // 该设备是否为激活设备
            String sql3="select count(1) from bill_terminalinfo t where t.termid=:tid and (t.activaday is not null or t.isreturncash in (1,2) )";
            Integer count3 = merchantTerminalInfoDao.querysqlCounts2(sql3,map1);
            // 激活的设备
            if(count3>0){
                result.put("ISRETURNCASH",1);
            }else{
                // 未激活的设备
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                Date date=new Date();
                String month = df.format(date).substring(4,6);
                String start = df.format(DateTools.getStartMonth(date));
                String end = df.format(DateTools.getEndtMonth(date));
                String sql = "select aa.tid,bb.rebatetype," +
                        " to_char((case " +
//                        "  when rebatetype in ('53','54','530','540','43','44') and sum(txnamount)>=50 then 1 " +
                        "  when sum(txnamount) >= (select nvl(p.tradeamt,0) from bill_purconfigure p where p.type=7 and p.valuestring=bb.rebatetype) then 1 " +
                        "  else 0 end)) ISRETURNCASH " +
                        "  from (" +
                        " select t.tid,nvl(sum(nvl(t.txnamount, 0)),0) txnamount from check_unitdealdata_"+month+" t " +
                        "  where t.mid = :mid  and t.tid = :tid and t.txnday >= :start and t.txnday <= :end  and t.type in (0,5) group by t.tid " +
                        " union all " +
                        "  select k.tid,nvl(sum(nvl(k.txnamount, 0)),0) txnamount  from check_realtxn k " +
                        "    where k.txnstate=0 and k.txntype in(0,5) and k.mid = :mid " +
                        "     and k.tid =:tid group by k.tid " +
                        " ) aa,bill_terminalinfo bb where aa.tid=bb.termid group by aa.tid,bb.rebatetype ";
                Map map = new HashMap();
                map.put("mid",merchantTerminalInfoBean.getMid());
                map.put("tid",merchantTerminalInfoBean.getTid());
                map.put("start",start);
                map.put("end",end);
                List<Map<String,String>> listMap = merchantTerminalInfoDao.queryObjectsBySqlListMap(sql,map);
                if(listMap.size()!=0){
                    result.put("ISRETURNCASH",null==listMap.get(0).get("ISRETURNCASH")?"0":listMap.get(0).get("ISRETURNCASH"));
                }else{
                    result.put("ISRETURNCASH",0);
                }
            }
        }else{
            // 不是交易限制的活动
            result.put("ISRETURNCASH",1);
        }
		return result;
	}

	/**
	 * 删除终端
	 */
	@Override
	public void deleteMerchantTerminalInfo(Integer bmtid,Integer userID,String terMID) throws Exception {
		String hql = "from TerminalInfoModel where termID = :termID";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("termID", terMID);
		List<TerminalInfoModel> timList = terminalInfoDao.queryObjectsByHqlList(hql, map);
		for (TerminalInfoModel terminalInfoModel : timList) {
			terminalInfoModel.setStatus(0);
			terminalInfoModel.setUsedConfirmDate(null);
			terminalInfoDao.updateObject(terminalInfoModel);
		}
		
		MerchantTerminalInfoModel merchantTerminalInfoModel = merchantTerminalInfoDao.getObjectByID(MerchantTerminalInfoModel.class, bmtid);
		merchantTerminalInfoModel.setMaintainType("D");
		merchantTerminalInfoModel.setMaintainDate(new Date());
		merchantTerminalInfoModel.setMaintainUid(userID);
		merchantTerminalInfoDao.updateObject(merchantTerminalInfoModel);
	}

	/**
	 * 根据Mid查询终端显示到相应的select
	 */
	@Override
	public DataGridBean searchMerchantTerminalInfo(
			String nameCode, String mid, String type) throws Exception {
		String sql = "SELECT m.BMTID,m.TID FROM BILL_MERCHANTTERMINALINFO m,bill_merchantinfo f WHERE f.mid=m.mid and  m.MAINTAINTYPE != 'D' AND m.APPROVESTATUS = 'Y' AND m.MID = '"+mid+"'";
		
		if(nameCode !=null){
			sql += " AND m.TID LIKE '%" + nameCode + "%'";
		}
		if(type!=null && !"".equals(type)){
			sql+=" and f.ism35 in ("+type+") ";
			//map.put("isM35", type);
		}
		sql += " ORDER BY m.BMTID DESC";
		
		List<Map<String,String>> proList = merchantTerminalInfoDao.executeSql(sql);
		
		List<Object>  list = new ArrayList<Object>();
		for (int i = 0; i < proList.size(); i++) {
			Map map = proList.get(i);
			list.add(map);
		}
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(proList.size());
		dgd.setRows(list);
		
		return dgd;
	}

	/**
	 * 查询终端不分页（状态为待审批）
	 */
	@Override
	public DataGridBean queryMerchantTerminalInfoBmidZ(String mid)
			throws Exception {
		String hql = "from MerchantTerminalInfoModel m where m.mid = :mid and m.maintainType != :maintainType and m.approveStatus in ('Z','Y') ";
		String hqlCount = "select count(*) from MerchantTerminalInfoModel m where m.mid = :mid and m.maintainType != :maintainType and m.approveStatus in ('Z','Y') ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mid", mid);
		map.put("maintainType", "D");
		
		long counts = merchantTerminalInfoDao.queryCounts(hqlCount, map);
		List<MerchantTerminalInfoModel> merchantTerminalInfoList = merchantTerminalInfoDao.queryObjectsByHqlList(hql,map);
		
		DataGridBean dataGridBean = formatToDataGrid(merchantTerminalInfoList, counts);
		return dataGridBean;
	}

	@Override
	public Map<String, String> searchMerchantTerminalInfoBMTID(Integer bmtid)
			throws Exception {
		MerchantTerminalInfoModel merchantTerminalInfoModel = merchantTerminalInfoDao.getObjectByID(MerchantTerminalInfoModel.class, bmtid);
		MachineInfoModel machineInfoModel = machineInfoDao.getObjectByID(MachineInfoModel.class, merchantTerminalInfoModel.getBmaid());
		Map<String, String> map = new HashMap<String, String>();
		map.put("machineModel", machineInfoModel.getMachineModel());
		return map;
	}

	@Override
	public void updateMerchantTerminalInfo(MerchantTerminalInfoBean merchantTerminalInfo) {
		MerchantTerminalInfoModel merchantTerminalInfoModel =merchantTerminalInfoDao.getObjectByID(MerchantTerminalInfoModel.class, merchantTerminalInfo.getBmtid());
		merchantTerminalInfoModel.setInstalledAddress(merchantTerminalInfo.getInstalledAddress());
		merchantTerminalInfoModel.setContactPerson(merchantTerminalInfo.getContactPerson());
		merchantTerminalInfoModel.setContactPhone(merchantTerminalInfo.getContactPhone());
		merchantTerminalInfoDao.updateObject(merchantTerminalInfoModel);
	}

	@Override
	public boolean queryMachineInfo(String id) {
		MachineInfoModel m = machineInfoDao.getObjectByID(MachineInfoModel.class, Integer.parseInt(id));
		if(m!=null){
			return true;
		}
		return false;
	}

	@Override
	public void saveMerchantMicroTerminalInfo(
			MerchantTerminalInfoBean merchantTerminalInfo, UserBean user,
			String[] values, int bmid, String mid) 	throws Exception {
		
		MerchantInfoModel merchantInfoModel = merchantInfoDao.getObjectByID(MerchantInfoModel.class, bmid);
		String agentId="";
		if("000000".equals(merchantInfoModel.getSettMethod())|| "".equals(merchantInfoModel.getSettMethod()) ||merchantInfoModel.getSettMethod()==null){
			//结算方式：日结
			agentId="0";
		}else{
			//结算方式为：实时
			agentId=PhoneProdConstant.PHONE_MD;
		}
		try {
			if(values != null && values.length > 0){
				for (int i = 0; i < values.length; i++) {
					String[] pram=values[i].split("#separate#");
					Map<String,String> params = new HashMap<String, String>();
					params.put("userName",merchantInfoModel.getHybPhone());
					params.put("mid",mid);
					params.put("tid", pram[0]);
					params.put("sn", pram[8]);
					params.put("agentId", agentId);		//0: 和融通系统M35报单 久玖为1，托付为2
					params.put("mark", "0");	//0：待挂终端增机   1：增机申请增机
					params.put("settMethod", merchantInfoModel.getSettMethod());
					params.put("rname", merchantInfoModel.getRname());
					String ss=HttpXmlClient.post(hybUrl, params);
				}
			}
		} catch (Exception e) {
		}finally{
			if(values != null && values.length > 0){
				for (int i = 0; i < values.length; i++) {
					String[] pram=values[i].split("#separate#");
					String hql = "from TerminalInfoModel where termID = :termID";
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("termID", pram[0]);
					MerchantTerminalInfoModel merchantTerminalInfoModel = new MerchantTerminalInfoModel();
					List<TerminalInfoModel> timList = terminalInfoDao.queryObjectsByHqlList(hql,map);
					if(timList != null && timList.size() > 0){
						timList.get(0).setUsedConfirmDate(new Date());
						timList.get(0).setStatus(2);
						terminalInfoDao.updateObject(timList.get(0));
						merchantTerminalInfoModel.setSn(timList.get(0).getSn());
					}
					
					merchantTerminalInfoModel.setMid(mid);
					
					merchantTerminalInfoModel.setTid(pram[0]);
					merchantTerminalInfoModel.setBmaid(Integer.parseInt(pram[1]));
					merchantTerminalInfoModel.setInstalledAddress(pram[2]);
					merchantTerminalInfoModel.setInstalledName(pram[3]);
					merchantTerminalInfoModel.setContactPerson(pram[4]);
					merchantTerminalInfoModel.setContactPhone(pram[5]);
					merchantTerminalInfoModel.setContactTel(pram[6]);
					merchantTerminalInfoModel.setInstalledSIM(pram[7]);
					
					merchantTerminalInfoModel.setUnno(user.getUnNo());
					merchantTerminalInfoModel.setApproveStatus("Z");		//默认为待受理 Y-已受理   Z-待受理  K-不受理
					merchantTerminalInfoModel.setMaintainUid(user.getUserID());
					merchantTerminalInfoModel.setMaintainDate(new Date());
					merchantTerminalInfoModel.setMaintainType("A");		//A-add   M-Modify  D-Delete
					merchantTerminalInfoModel.setStatus(1);
					merchantTerminalInfoDao.saveObject(merchantTerminalInfoModel);
				}
			}
			
			merchantInfoModel.setApproveUid(user.getUserID());
			merchantInfoModel.setApproveDate(new Date());
			merchantInfoModel.setApproveStatus("W");		//// Y-放行   Z-待挂银行终端  W-待审核  K-踢回  C-审核中
			merchantInfoModel.setBankArea(merchantTerminalInfo.getRemarks());
			merchantInfoDao.updateObject(merchantInfoModel);
			
			Integer batchJobNo = merchantInfoModel.getBmid();
			
			String hqlTodo="from TodoModel where batchJobNo = :batchJobNo and status = :status and bizType = :bizType";
			Map<String, Object> mapTodo = new HashMap<String, Object>();
			mapTodo.put("batchJobNo", batchJobNo.toString());
			mapTodo.put("status", 0);
			mapTodo.put("bizType", "bill_merchant");
			List<TodoModel> todoList = todoDao.queryObjectsByHqlList(hqlTodo, mapTodo);
			if(todoList == null || todoList.size() == 0){
				//添加待办
				TodoModel todo = new TodoModel();
				todo.setUnNo("110003");
				todo.setBatchJobNo(batchJobNo.toString());
				todo.setMsgSendUnit(user.getUnNo());
				todo.setMsgSender(user.getUserID());
				todo.setMsgTopic("待审批商户入网报单");
				todo.setMsgSendTime(new Date());
				todo.setTranCode("10420");	//菜单代码
				todo.setBizType("bill_merchant");
				todo.setStatus(0);			//0-未办，1-已办
				todoDao.saveObject(todo);
			}
		}


	}

	@Override
	public HSSFWorkbook exportAddTerminalInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="SELECT A.UNNO,A.MID,A.TID,C.MACHINEMODEL,A.INSTALLEDADDRESS,A.INSTALLEDNAME,A.CONTACTPERSON  FROM BILL_MERCHANTTERMINALINFO A, BILL_MERCHANTINFO B,BILL_MACHINEINFO C " +
					" WHERE A.MID = B.MID AND A.BMAID=C.BMAID AND A.MAINTAINTYPE != :maintainType AND (A.APPROVESTATUS= :approveStatus OR A.APPROVESTATUS= :approveStatus2) AND B.APPROVESTATUS='Y'";
		map.put("maintainType", "D");
		map.put("approveStatus", "Z");
		map.put("approveStatus2", "C");
		List<Map<String,String>> data=merchantTerminalInfoDao.queryObjectsBySqlListMap(sql, map);
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNNO");
		excelHeader.add("MID");
		excelHeader.add("TID");
		excelHeader.add("MACHINEMODEL");
		excelHeader.add("INSTALLEDADDRESS");
		excelHeader.add("INSTALLEDNAME");
		excelHeader.add("CONTACTPERSON");

		headMap.put("UNNO", "机构编号");
		headMap.put("MID", "商户编号");
		headMap.put("TID", "终端编号");
		headMap.put("MACHINEMODEL", "机型");
		headMap.put("INSTALLEDADDRESS", "装机地址");
		headMap.put("INSTALLEDNAME", "装机名称");
		headMap.put("CONTACTPERSON", "联系人");
		
		return ExcelUtil.export("增机审批资料", data, excelHeader, headMap);
	}

	@Override
	public HSSFWorkbook exportAddTerminalInfoSelected(String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="SELECT A.UNNO,A.MID,A.TID,C.MACHINEMODEL,A.INSTALLEDADDRESS,A.INSTALLEDNAME,A.CONTACTPERSON  FROM BILL_MERCHANTTERMINALINFO A, BILL_MERCHANTINFO B,BILL_MACHINEINFO C " +
					" WHERE A.MID = B.MID AND A.BMAID=C.BMAID AND A.MAINTAINTYPE != :maintainType AND (A.APPROVESTATUS= :approveStatus OR A.APPROVESTATUS= :approveStatus2) AND B.APPROVESTATUS='Y' AND A.BMTID IN ("+ids+") ";
		map.put("maintainType", "D");
		map.put("approveStatus", "Z");
		map.put("approveStatus2", "C");
		List<Map<String,String>> data=merchantTerminalInfoDao.queryObjectsBySqlListMap(sql, map);
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNNO");
		excelHeader.add("MID");
		excelHeader.add("TID");
		excelHeader.add("MACHINEMODEL");
		excelHeader.add("INSTALLEDADDRESS");
		excelHeader.add("INSTALLEDNAME");
		excelHeader.add("CONTACTPERSON");

		headMap.put("UNNO", "机构编号");
		headMap.put("MID", "商户编号");
		headMap.put("TID", "终端编号");
		headMap.put("MACHINEMODEL", "机型");
		headMap.put("INSTALLEDADDRESS", "装机地址");
		headMap.put("INSTALLEDNAME", "装机名称");
		headMap.put("CONTACTPERSON", "联系人");
		

		return ExcelUtil.export("增机审批资料", data, excelHeader, headMap);
	}
}