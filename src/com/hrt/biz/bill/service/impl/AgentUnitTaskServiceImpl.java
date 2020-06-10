package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IAgentUnitDao;
import com.hrt.biz.bill.dao.IAgentUnitTaskDao;
import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.biz.bill.entity.model.AgentUnitTaskDetail1Model;
import com.hrt.biz.bill.entity.model.AgentUnitTaskDetail2Model;
import com.hrt.biz.bill.entity.model.AgentUnitTaskDetail3Model;
import com.hrt.biz.bill.entity.model.AgentUnitTaskModel;
import com.hrt.biz.bill.entity.pagebean.AgentUnitBean;
import com.hrt.biz.bill.entity.pagebean.AgentUnitTaskBean;
import com.hrt.biz.bill.service.IAgentUnitTaskDetail1Service;
import com.hrt.biz.bill.service.IAgentUnitTaskDetail2Service;
import com.hrt.biz.bill.service.IAgentUnitTaskDetail3Service;
import com.hrt.biz.bill.service.IAgentUnitTaskService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.gmms.webservice.AgentUnitInfo;
import com.hrt.gmms.webservice.IGmmsService;
import com.hrt.util.ClassToJavaBeanUtil;
import com.hrt.util.StringUtil;

public class AgentUnitTaskServiceImpl implements IAgentUnitTaskService {
	
	private IAgentUnitTaskDao agentUnitTaskDao;
	private IGmmsService gsClient;
	private IAgentUnitTaskDetail1Service agentUnitTaskDetail1Service;
	private IAgentUnitTaskDetail2Service agentUnitTaskDetail2Service;
	private IAgentUnitTaskDetail3Service agentUnitTaskDetail3Service;
	
	public IAgentUnitTaskDetail1Service getAgentUnitTaskDetail1Service() {
		return agentUnitTaskDetail1Service;
	}
	public void setAgentUnitTaskDetail1Service(IAgentUnitTaskDetail1Service agentUnitTaskDetail1Service) {
		this.agentUnitTaskDetail1Service = agentUnitTaskDetail1Service;
	}
	public IAgentUnitTaskDetail2Service getAgentUnitTaskDetail2Service() {
		return agentUnitTaskDetail2Service;
	}
	public void setAgentUnitTaskDetail2Service(IAgentUnitTaskDetail2Service agentUnitTaskDetail2Service) {
		this.agentUnitTaskDetail2Service = agentUnitTaskDetail2Service;
	}
	public IAgentUnitTaskDetail3Service getAgentUnitTaskDetail3Service() {
		return agentUnitTaskDetail3Service;
	}
	public void setAgentUnitTaskDetail3Service(IAgentUnitTaskDetail3Service agentUnitTaskDetail3Service) {
		this.agentUnitTaskDetail3Service = agentUnitTaskDetail3Service;
	}
	public IAgentUnitTaskDao getAgentUnitTaskDao() {
		return agentUnitTaskDao;
	}
	public void setAgentUnitTaskDao(IAgentUnitTaskDao agentUnitTaskDao) {
		this.agentUnitTaskDao = agentUnitTaskDao;
	}
	public IGmmsService getGsClient() {
		return gsClient;
	}
	public void setGsClient(IGmmsService gsClient) {
		this.gsClient = gsClient;
	}
	@Override
	public DataGridBean queryAgentUnitTask(AgentUnitTaskBean agentUnitTask, UserBean user) {
		DataGridBean dataGridBean = new DataGridBean();
		Map<String,Object> map = new HashMap<String, Object>();
		String sql = "select a.*,b.agentname un_name from bill_agentUnitTask a,bill_agentunitinfo b where a.unno=b.unno and a.maintaintype!='D' ";
		String sqlCount = "select count(1) from bill_agentUnitTask a,bill_agentunitinfo b where a.unno=b.unno and  a.maintaintype!='D' ";
		if("110000".equals(user.getUnNo())){
		}else{
			sql += " and a.unno = '"+user.getUnNo()+"'";
			sqlCount += " and a.unno = '"+user.getUnNo()+"'";
		}
		if(agentUnitTask.getTaskType()!=null && !"".equals(agentUnitTask.getTaskType())){
			sql += " and a.taskType = :taskType";
			sqlCount += " and a.taskType = :taskType";
			map.put("taskType", agentUnitTask.getTaskType());
		}
		if(agentUnitTask.getUnno()!=null && !"".equals(agentUnitTask.getUnno())){
			sql += " and a.unno = :unno";
			sqlCount += " and a.unno = :unno";
			map.put("unno", agentUnitTask.getUnno());
		}
		if(agentUnitTask.getApproveStatus()!=null && !"".equals(agentUnitTask.getApproveStatus())){
			sql += " and a.approveStatus = :approveStatus";
			sqlCount += " and a.approveStatus = :approveStatus";
			map.put("approveStatus", agentUnitTask.getApproveStatus());
		}
		if(agentUnitTask.getTaskType()!=null && !"".equals(agentUnitTask.getTaskType())){
			sql += " and a.taskType = :taskType";
			sqlCount += " and a.taskType = :taskType";
			map.put("taskType", agentUnitTask.getTaskType());
		}
		sql += " order by a.bautid desc";
		List<Map<String,Object>> list = agentUnitTaskDao.queryObjectsBySqlList2(sql, map, agentUnitTask.getPage(), agentUnitTask.getRows());
		Integer count = agentUnitTaskDao.querysqlCounts2(sqlCount, map);
		dataGridBean.setRows(list);
		dataGridBean.setTotal(count);
		return dataGridBean;
	}
	@Override
	public Integer addAgentUnitTask(AgentUnitTaskBean agentUnitTask, UserBean user) throws Exception {
		String sql = "select count(1) from bill_agentUnitTask where maintaintype!='D' and approveStatus in ('K','W') and unno = '"+user.getUnNo()+"'";
		Integer i = agentUnitTaskDao.querysqlCounts2(sql, null);
		//已提交未审核已退回
		if(i>0){
			return 0;
		}
		AgentUnitTaskModel autm = new AgentUnitTaskModel();
		if("1".equals(agentUnitTask.getTaskType())){
			//代理商基础信息变更申请
			AgentUnitTaskDetail1Model model = agentUnitTaskDetail1Service.saveAgentUnitTaskDetail1(agentUnitTask, user);
			autm.setBautdid(model.getBautdid());
			autm.setApproveStatus("W");
		}else if("2".equals(agentUnitTask.getTaskType())){
			//如果申请了贷款则不能修改账户名称
			Integer flag = agentUnitTaskDao.querysqlCounts2("select count(1) from bill_loanunit where unno = '"+user.getUnNo()+"' and maintaintype!='D' ", null);
			if(flag>0) {
				Integer f = agentUnitTaskDao.querysqlCounts2("select count(1) from bill_agentunitinfo where unno = '"+user.getUnNo()+"' and bankaccname='"+agentUnitTask.getBankAccName()+"'", null);
				if(f<1) {
					return 2;
				}
			}
			//代理商结算信息变更申请
			AgentUnitTaskDetail2Model model = agentUnitTaskDetail2Service.saveAgentUnitTaskDetail2(agentUnitTask, user);
			autm.setBautdid(model.getBautdid());
			autm.setApproveStatus("W");
		}else if("3".equals(agentUnitTask.getTaskType())){
			//代理商联系人变更申请,不审核直接通过
			AgentUnitTaskDetail3Model model = agentUnitTaskDetail3Service.saveAgentUnitTaskDetail3(agentUnitTask, user);
			autm.setBautdid(model.getBautdid());
			AgentUnitInfo info = new AgentUnitInfo();
			info = agentUnitTaskDetail3Service.updateAgentUnitTaskDetail3Y(info, autm, user);
			Boolean flag = gsClient.updateAgentInfo(info,"hrtREX1234.Java");
			if(!flag){
				throw new IllegalAccessError("调用webservice失败");
			}
			autm.setApproveStatus("Y");
			autm.setApproveDate(new Date());
		}
		autm.setTaskType(agentUnitTask.getTaskType());
		autm.setUnno(user.getUnNo());
		autm.setMaintainDate(new Date());
		autm.setMaintainType("A");
		agentUnitTaskDao.saveObject(autm);
		return 1;
	}
	
	@Override
	public Integer deleteAgentUnitTask(AgentUnitTaskBean agentUnitTask, UserBean user) {
		String hql = "from AgentUnitTaskModel where maintaintype!='D' and approveStatus in ('K','W') and bautid = '"+agentUnitTask.getBautid()+"'";
		AgentUnitTaskModel model = agentUnitTaskDao.queryObjectByHql(hql, new Object[]{});
		if(model!=null){
			model.setMaintainDate(new Date());
			model.setMaintainType("D");
			agentUnitTaskDao.updateObject(model);
			return 1;
		}
		return 0;
	}
	@Override
	public Integer updateAgentUnitTask(AgentUnitTaskBean agentUnitTask, UserBean user) throws Exception {
		String hql = "from AgentUnitTaskModel where maintaintype!='D' and approveStatus = 'K' and unno = '"+user.getUnNo()+"'";
		AgentUnitTaskModel model = agentUnitTaskDao.queryObjectByHql(hql, new Object[]{});
		if(model!=null){
			if("1".equals(agentUnitTask.getTaskType())){
				AgentUnitTaskDetail1Model model2 = agentUnitTaskDetail1Service.updateAgentUnitTaskDetail1(agentUnitTask, user);
			}else if("2".equals(agentUnitTask.getTaskType())){
				AgentUnitTaskDetail2Model model2 = agentUnitTaskDetail2Service.updateAgentUnitTaskDetail2(agentUnitTask, user);
			}else if("3".equals(agentUnitTask.getTaskType())){
				AgentUnitTaskDetail3Model model2 = agentUnitTaskDetail3Service.updateAgentUnitTaskDetail3(agentUnitTask, user);
			}
			model.setMaintainDate(new Date());
			model.setMaintainType("M");
			model.setApproveStatus("W");
			agentUnitTaskDao.updateObject(model);
			return 1;
		}
		return 0;
	}
	@Override
	public Integer updateAgentUnitTaskK(AgentUnitTaskBean agentUnitTask, UserBean user) {
		String hql = "from AgentUnitTaskModel where maintaintype!='D' and approveStatus = 'W' and bautid = '"+agentUnitTask.getBautid()+"'";
		AgentUnitTaskModel model = agentUnitTaskDao.queryObjectByHql(hql, new Object[]{});
		if(model!=null){
			model.setApproveDate(new Date());
			model.setApproveNote(agentUnitTask.getApproveNote());
			model.setApproveStatus("K");
			agentUnitTaskDao.updateObject(model);
			return 1;
		}
		return 0;
	}
	@Override
	public Integer updateAgentUnitTaskY(AgentUnitTaskBean agentUnitTask, UserBean user) {
		String hql = "from AgentUnitTaskModel where maintaintype!='D' and approveStatus = 'W' and bautid = '"+agentUnitTask.getBautid()+"'";
		AgentUnitTaskModel model = agentUnitTaskDao.queryObjectByHql(hql, new Object[]{});
		if(model!=null){
			AgentUnitInfo info = new AgentUnitInfo();
			info.setUnno(model.getUnno());
			model.setApproveDate(new Date());
			model.setApproveStatus("Y");
			Boolean flag = null;
			//通过审核修改信息
			if("1".equals(model.getTaskType())){
				info = agentUnitTaskDetail1Service.updateAgentUnitTaskDetail1Y(info, model, user);
			}else if("2".equals(model.getTaskType())){
				info = agentUnitTaskDetail2Service.updateAgentUnitTaskDetail2Y(info, model, user);
			}else if("3".equals(model.getTaskType())){
				info = agentUnitTaskDetail3Service.updateAgentUnitTaskDetail3Y(info, model, user);
			}
			System.out.println("代理商工单调用webservice:unno="+model.getUnno());
			flag = gsClient.updateAgentInfo(info,"hrtREX1234.Java");
			System.out.println("代理商工单webservice返回"+flag);
			if(!flag){
				throw new IllegalAccessError("调用webservice失败");
			}
			return 1;
		}
		return 0;
	}
	@Override
	public List<Object> queryAgentUnitTaskDetail(AgentUnitTaskBean agentUnitTask, UserBean user) {
		String sql="select * from SYS_PARAM t WHERE t.title='AgentInfo'";
		List<Map<String, Object>> list=agentUnitTaskDao.queryObjectsBySqlList(sql);
		String upload =(String) list.get(0).get("UPLOAD_PATH");
		List<Object> list2=new ArrayList<Object>();
		if("1".equals(agentUnitTask.getTaskType())){
			AgentUnitTaskDetail1Model model = agentUnitTaskDetail1Service.queryAgentUnitTaskDetail1(agentUnitTask, user);
			list2.add(model);
			if(StringUtil.isNotEmpty(model.getDealUpLoad())){
				list2.add(upload+File.separator + model.getDealUpLoad());
			}else{
				list2.add("");
			}
			if(StringUtil.isNotEmpty(model.getBusLicUpLoad())){
				list2.add(upload+File.separator + model.getBusLicUpLoad());
			}else{
				list2.add("");
			}
			if(StringUtil.isNotEmpty(model.getLegalAUpLoad())){
				list2.add(upload+File.separator + model.getLegalAUpLoad());
			}else{
				list2.add("");
			}
			
			if(StringUtil.isNotEmpty(model.getLegalBUpLoad())){
				list2.add(upload+File.separator + model.getLegalBUpLoad());
			}else{
				list2.add("");
			}
		}else if("2".equals(agentUnitTask.getTaskType())){
			AgentUnitTaskDetail2Model model = agentUnitTaskDetail2Service.queryAgentUnitTaskDetail2(agentUnitTask, user);
			list2.add(model);
			if(StringUtil.isNotEmpty(model.getAccountAuthUpLoad())){
				list2.add(upload+File.separator + model.getAccountAuthUpLoad());
			}else{
				list2.add("");
			}
			if(StringUtil.isNotEmpty(model.getAccountLegalAUpLoad())){
				list2.add(upload+File.separator + model.getAccountLegalAUpLoad());
			}else{
				list2.add("");
			}
			if(StringUtil.isNotEmpty(model.getAccountLegalBUpLoad())){
				list2.add(upload+File.separator + model.getAccountLegalBUpLoad());
			}else{
				list2.add("");
			}
			if(StringUtil.isNotEmpty(model.getAccountLegalHandUpLoad())){
				list2.add(upload+File.separator + model.getAccountLegalHandUpLoad());
			}else{
				list2.add("");
			}
		}else if("3".equals(agentUnitTask.getTaskType())){
			AgentUnitTaskDetail3Model model = agentUnitTaskDetail3Service.queryAgentUnitTaskDetail3(agentUnitTask, user);
			list2.add(model);
		}
		return list2;
	}
	
	@Override
	public DataGridBean queryLoanUnit(AgentUnitTaskBean agentUnitTask, UserBean user) {
		DataGridBean dataGridBean = new DataGridBean();
		Map<String,Object> map = new HashMap<String, Object>();
		String sql = "select * from bill_loanUnit a where a.maintaintype!='D' ";
		String sqlCount = "select count(1) from bill_loanUnit a where a.maintaintype!='D' ";
		if(agentUnitTask.getUnno()!=null && !"".equals(agentUnitTask.getUnno())){
			sql += " and a.unno = :unno";
			sqlCount += " and a.unno = :unno";
			map.put("unno", agentUnitTask.getUnno());
		}
		List<Map<String,Object>> list = agentUnitTaskDao.queryObjectsBySqlList2(sql, map, agentUnitTask.getPage(), agentUnitTask.getRows());
		Integer count = agentUnitTaskDao.querysqlCounts2(sqlCount, map);
		dataGridBean.setRows(list);
		dataGridBean.setTotal(count);
		return dataGridBean;
	}
	
	@Override
	public Integer deleteLoanUnno(AgentUnitTaskBean agentUnitTask, UserBean user) {
		String sql = "update bill_loanUnit set maintaintype='D' where unno = '"+agentUnitTask.getUnno()+"'";
		agentUnitTaskDao.executeUpdate(sql);
		return 1;
	}
	
	@Override
	public List<Map<String, String>> addLoanUnit(String xlsfile, String fileName, UserBean user) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<Object> arrayList = new ArrayList<Object>();
			for(int i = 1; i < sheet.getLastRowNum()+1; i++){
				HSSFRow row = sheet.getRow(i);
				String unno="";
				HSSFCell cell = row.getCell((short)0);
				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
					//SN号
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					unno = row.getCell((short)0).getStringCellValue().trim();
					String sql = "select count(1) from sys_unit where unno='"+unno+"'"; 
					Integer count = agentUnitTaskDao.querysqlCounts2(sql, null);
					if(count<1){
						Map<String,String> map = new HashMap<String,String>();
						map.put("unno", unno);
						map.put("remark", "机构不存在");
						list.add(map);
						continue;
					}else{
						String sql1 = "select count(1) from bill_loanUnit where unno='"+unno+"' and maintaintype!='D'"; 
						Integer count1 = agentUnitTaskDao.querysqlCounts2(sql1, null);
						if(count1>0){
							Map<String,String> map = new HashMap<String,String>();
							map.put("unno", unno);
							map.put("remark", "机构已加入贷款机构");
							list.add(map);
							continue;
						}else{
							String sqlU = "insert into bill_loanUnit (UNNO, MAINTAINTYPE, MAINTAINUSER, MAINTAINDATE) values ('"+unno+"', 'A', '"+user.getLoginName()+"', sysdate)";
							agentUnitTaskDao.executeUpdate(sqlU);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
