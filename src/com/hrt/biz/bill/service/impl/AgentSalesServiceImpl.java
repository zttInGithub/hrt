package com.hrt.biz.bill.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.entity.pagebean.AgentSalesBean;
import com.hrt.biz.bill.service.IAgentSalesService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.dao.sysadmin.IUserDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.model.UserModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;

public class AgentSalesServiceImpl implements IAgentSalesService {

		private IAgentSalesDao agentSalesDao;
		
		private IUnitDao unitDao;
		
		private IUserDao userDao;

		public IAgentSalesDao getAgentSalesDao() {
			return agentSalesDao;
		}

		public void setAgentSalesDao(IAgentSalesDao agentSalesDao) {
			this.agentSalesDao = agentSalesDao;
		}
		
		public IUnitDao getUnitDao() {
			return unitDao;
		}

		public void setUnitDao(IUnitDao unitDao) {
			this.unitDao = unitDao;
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
		 * 		total 总记录数
		 */
		private DataGridBean formatToDataGrid(List<AgentSalesModel> agentSalesList, long total) {
			List<AgentSalesBean> agentSalesBeanList = new ArrayList<AgentSalesBean>();
			for(AgentSalesModel agentSalesModel : agentSalesList) {
				AgentSalesBean agentSalesBean = new AgentSalesBean();
				BeanUtils.copyProperties(agentSalesModel, agentSalesBean);
				
				UnitModel unit = unitDao.getObjectByID(UnitModel.class, agentSalesModel.getUnno());
				agentSalesBean.setUnnoName(unit.getUnitName());
				
				agentSalesBeanList.add(agentSalesBean);
			}
			
			DataGridBean dgb = new DataGridBean();
			dgb.setTotal(total);
			dgb.setRows(agentSalesBeanList);
			
			return dgb;
		}

		/**
		 * 分页查询业务员资料
		 */
		@Override
		public DataGridBean queryAgentSales(AgentSalesBean agentSales,String unno) {
			UnitModel unit = unitDao.getObjectByID(UnitModel.class, unno);
			String hql = " from AgentSalesModel a where a.maintainType != :maintainType";
			String hqlCount = "select count(*) from AgentSalesModel a where a.maintainType != :maintainType";
			Map<String, Object> map = new HashMap<String, Object>();
			if(unit.getUnLvl() != 0 && !"901000".equals(unno)){
				hql += " and a.unno = :unno";
				hqlCount += " and a.unno = :unno";
				map.put("unno", unno);
			}
			if("901000".equals(unno)){
				hql += " and a.unno  like '90%'";
				hqlCount += " and a.unno like '90%'";
			}
			if(agentSales.getSaleName() != null && !"".equals(agentSales.getSaleName())){
				hql += " and a.saleName = :saleName";
				hqlCount += " and a.saleName = :saleName";
				map.put("saleName", agentSales.getSaleName());
			}
			map.put("maintainType", "D");
			if (agentSales.getSort() != null) {
				hql += " order by " + agentSales.getSort() + " " + agentSales.getOrder();
			}
			List<AgentSalesModel> agentSalesList = agentSalesDao.queryAgentSales(hql, map, agentSales.getPage(), agentSales.getRows());
			Long count = agentSalesDao.queryCounts(hqlCount, map);
			
			DataGridBean dataGridBean = formatToDataGrid(agentSalesList, count);
			
			return dataGridBean;
		}

		/**
		 * 添加业务员
		 */
		@Override
		public void saveAgentSales(AgentSalesBean agentSales, UserBean user)
				throws Exception {
			AgentSalesModel agentSalesModel = new AgentSalesModel();
			
			if(!"1".equals(agentSales.getIsleader())) {
				agentSales.setIsleader(null);
			}
			
			BeanUtils.copyProperties(agentSales, agentSalesModel);
			
			UserModel userModel = userDao.getObjectByID(UserModel.class, Integer.parseInt(agentSales.getUserID()));
			agentSalesModel.setSaleName(userModel.getUserName());
			
			agentSalesModel.setMaintainUid(user.getUserID());
			agentSalesModel.setMaintainDate(new Date());
			agentSalesModel.setMaintainDate(new Date());
			agentSalesModel.setMaintainType("A");		//A-add   M-Modify  D-Delete
			
			agentSalesDao.saveObject(agentSalesModel);
		}
		/**
		 * 查询业务员信息显示到select中
		 */
		@Override
		public DataGridBean searchAgentSales(String unno,String nameCode) throws Exception {
			
			UnitModel unit = unitDao.getObjectByID(UnitModel.class, unno);
			String sql = "SELECT BUSID,UNNO,SALENAME FROM BILL_AGENTSALESINFO WHERE MAINTAINTYPE != 'D'";
			if(unit.getUnLvl() != 0){
				sql += " AND UNNO = '" + unno +"'";
			}
			if(nameCode !=null){
				sql += " AND SALENAME LIKE '%" + nameCode + "%'";
			}
			sql += " order by SALENAME asc";
			
			List<Map<String,String>> proList = agentSalesDao.executeSql(sql);
			
			List<Object>  list = new ArrayList<Object>();
			for (int i = 0; i < proList.size(); i++) {
				Map map = proList.get(i);
				UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, map.get("UNNO").toString());
				if(unitModel!=null) {//结果判断
					map.put("unitName", unitModel.getUnitName());
					list.add(map);
				}
			}
			DataGridBean dgd = new DataGridBean();
			dgd.setTotal(proList.size());
			dgd.setRows(list);
			
			return dgd;
		}

		@Override
		public DataGridBean searchAgentSales3(UserBean user) throws Exception {
			
			UnitModel unit = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
			String sql = "SELECT BUSID,UNNO,SALENAME FROM BILL_AGENTSALESINFO WHERE MAINTAINTYPE != 'D'";
			if(unit.getUnLvl() != 0){
				sql += " AND UNNO = '" + user.getUnNo() +"'";
			}
			//业务员只能查看自己
			if(user.getUseLvl()==2||user.getUseLvl()==1) {
				sql += " AND USER_ID = "+user.getUserID()+" ";
			}
			sql += " order by SALENAME asc";
			
			List<Map<String,String>> proList = agentSalesDao.executeSql(sql);
			
			List<Object>  list = new ArrayList<Object>();
			for (int i = 0; i < proList.size(); i++) {
				Map map = proList.get(i);
				UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, map.get("UNNO").toString());
				if(unitModel!=null) {//结果判断
					map.put("unitName", unitModel.getUnitName());
					list.add(map);
				}
			}
			DataGridBean dgd = new DataGridBean();
			dgd.setTotal(proList.size());
			dgd.setRows(list);
			
			return dgd;
		}
		
		/**
		 * 查询业务员信息
		 */
		@Override
		public AgentSalesBean searchAgentSalesByName(String nameCode){
			AgentSalesBean agb;
			if(nameCode!=null&&!"".equals(nameCode)){
				String hql = "SELECT f FROM AgentSalesModel f WHERE f.maintainType != 'D' and f.saleName='"+nameCode+"'";
				List<AgentSalesModel> agList = agentSalesDao.queryObjectsByHqlList(hql);
				if(agList!=null&&agList.size()>0){
					agb = new AgentSalesBean();
					agb.setBusid(agList.get(0).getBusid());
					agb.setUnno(agList.get(0).getUnno());
					return agb;
				}
			}
			return null;
		}
		
		/**
		 * 修改业务员信息
		 */
		@Override
		public void updateAgentSales(AgentSalesBean agentSales, UserBean user)
				throws Exception {
			AgentSalesModel agentSalesModel = agentSalesDao.getObjectByID(AgentSalesModel.class, agentSales.getBusid());
			BeanUtils.copyProperties(agentSales, agentSalesModel);
			
			UserModel userModel = userDao.getObjectByID(UserModel.class, Integer.parseInt(agentSales.getUserID()));
			agentSalesModel.setSaleName(userModel.getUserName());
			
			agentSalesModel.setMaintainUid(user.getUserID());
			agentSalesModel.setMaintainDate(new Date());
			agentSalesModel.setMaintainType("M");		//A-add   M-Modify  D-Delete
			
			agentSalesDao.updateObject(agentSalesModel);
		}

		/**
		 * 删除业务员信息
		 */
		@Override
		public void deleteAgentSales(Integer id) throws Exception {
			AgentSalesModel agentSales = agentSalesDao.getObjectByID(AgentSalesModel.class, id);
			agentSales.setMaintainType("D");
			agentSalesDao.updateObject(agentSales);
		}

		/**
		 * 导出业务员资料excel
		 */
		@Override
		public HSSFWorkbook export(String ids) {
			String sql=" SELECT u.UN_NAME,t.UNNO, t.BUSID, t.SALENAME, t.PHONE, t.EMAIL, t.TELEPHONE FROM BILL_AGENTSALESINFO t,SYS_UNIT u WHERE t.BUSID IN ("+ids+")  AND u.UNNO=t.UNNO";
			
			//String sql1="SELECT COLUMN_NAME FROM user_tab_columns WHERE table_name = 'BILL_MERCHANTINFO'";
			List<Map<String, Object>> data=agentSalesDao.queryObjectsBySqlList(sql);
			List<String> excelHeader=new ArrayList<String>();
			Map<String, Object> headMap=new HashMap<String, Object>();
			
			excelHeader.add("BUSID");
			excelHeader.add("SALENAME");
			excelHeader.add("UNNO");
			excelHeader.add("UN_NAME");
			excelHeader.add("PHONE");
			excelHeader.add("EMAIL");
			excelHeader.add("TELEPHONE");
			
			headMap.put("BUSID", "业务员编号");
			headMap.put("UNNO", "机构编号");
			headMap.put("UN_NAME", "机构名称");
			headMap.put("SALENAME", "业务员姓名");
			headMap.put("PHONE", "手机");
			headMap.put("EMAIL", "邮箱");
			headMap.put("TELEPHONE", "电话");
			return ExcelUtil.export("业务员资料", data, excelHeader, headMap);
		}

		/**
		 * 添加时查询用户是否已有对应的业务员（返回>0为有）
		 */
		@Override
		public Integer queryAgentSalesCounts(AgentSalesBean agentSalesBean) {
			String hqlCount = "select count(busid) from AgentSalesModel where userID = :userID and unno = :unno ";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userID", agentSalesBean.getUserID());
			map.put("unno", agentSalesBean.getUnno());
			Long agentSalesCounts = agentSalesDao.queryCounts(hqlCount, map);
			return agentSalesCounts.intValue();
		}

		/**
		 * 修改时查询用户是否已有对应的业务员（返回>0为有）
		 */
		@Override
		public Integer queryUpdateAgentSalesCounts(AgentSalesBean agentSalesBean) {
			AgentSalesModel agentSalesModel = agentSalesDao.getObjectByID(AgentSalesModel.class, agentSalesBean.getBusid());
			String hqlCount = "select count(busid) from AgentSalesModel where 1 = 1 ";
			if(agentSalesBean.getUserID().equals(agentSalesModel.getUserID().trim()) && agentSalesBean.getUnno().equals(agentSalesModel.getUnno().trim())){
				return 0;
			}else{
				hqlCount += " and userID = :userID and unno = :unno and maintainType != :maintainType";
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userID", agentSalesBean.getUserID());
				map.put("unno", agentSalesBean.getUnno());
				map.put("maintainType", "D");
				Long agentSalesCounts = agentSalesDao.queryCounts(hqlCount, map);
				return agentSalesCounts.intValue();
			}
		}

		@Override
		public DataGridBean searchUnno(String unNo, String nameCode) {
			String sql = "select  unno , un_name  from sys_unit  t where t.unno in(select unno from sys_unit m where m.upper_unit='"+unNo+"' or m.unno ='"+unNo+"' )";
			if(nameCode !=null){
				sql += " AND t.unno LIKE '%" + nameCode + "%'";
			}
			sql += " order by unno";
			
			List<Map<String,String>> proList = unitDao.executeSql(sql);
			DataGridBean dgd = new DataGridBean();
			dgd.setTotal(proList.size());
			dgd.setRows(proList);
			
			return dgd;
		}

		@Override
		public DataGridBean queryAgentSalesGroup(AgentSalesBean agentSales, String unNo) {
			DataGridBean dataGridBean = new DataGridBean();
			String sql = "SELECT t.*,u.login_name FROM bill_agentsalesinfo t,sys_user u"
					+" where( t.ismanager = '1' or t.salesgroup is not null)"
					+" and t.maintaintype != 'D'"
					+" and t.user_id = u.user_id"
					+" and t.unno in ('110000','b10000','111000','b11000','991000','j91000')";
			
			//组查询
			if(agentSales.getSalesgroup() != null && !"".equals(agentSales.getSalesgroup())) {
				sql += " and t.salesgroup = '"+agentSales.getSalesgroup()+"'";
			}
			//职能查询（组长/组员）
			if(agentSales.getIsleader() != null && !"".equals(agentSales.getIsleader())) {
				if("1".equals(agentSales.getIsleader())) {
					sql += " and t.isleader = '1'";
				}else {
					sql += " and nvl(t.isleader,0) != '1'";
				}
			}
			//销售姓名查询
			if(agentSales.getSaleName() != null && !"".equals(agentSales.getSaleName())) {
				sql += " and t.salename = '"+agentSales.getSaleName()+"'";
			}
			
			String countSql = " select count(1) from ("+sql+")";
			List<Map<String,Object>> list = agentSalesDao.queryObjectsBySqlList2(sql, null, agentSales.getPage(), agentSales.getRows());
			Integer total = agentSalesDao.querysqlCounts2(countSql, null);
			dataGridBean.setRows(list);
			dataGridBean.setTotal(total);
			
			return dataGridBean;
		}

		@Override
		public DataGridBean queryAgentsalesGroup() {
			String sql = "SELECT minfo1 SALESGROUPNAME,minfo2 SALESGROUP FROM sys_configure WHERE groupName='SalesGroup'";
			List<Map<String,String>> proList = agentSalesDao.executeSql(sql);
			DataGridBean dgd = new DataGridBean();
			List<Object>  list = new ArrayList<Object>();
			for (int i = 0; i < proList.size(); i++) {
				Map map = proList.get(i);
				list.add(map);

			}
			dgd.setTotal(list.size());
			dgd.setRows(list);

			return dgd;
		}

		@Override
		public Map queryAgentsalesGroupForId(AgentSalesBean agentSales, UserBean userSession) {
			Map params = new HashMap();
			params.put("busid", agentSales.getBusid());
			String sql = "SELECT t.* FROM bill_agentsalesinfo t where 1=1 " + " and t.busid =:busid";
			List<Map<String, String>> list = agentSalesDao.queryObjectsBySqlListMap(sql, params);
			if (list.size() == 1) {
				return list.get(0);
			}
			return new HashMap();
		}

		@Override
		public String updateAgentSalesGroup(AgentSalesBean agentSales, UserBean userSession) {
			String sql = " update bill_agentsalesinfo t set t.salesgroup = '"+agentSales.getSalesgroup()+"' "+
					" ,t.maintaintype = 'M' ,t.maintaindate = sysdate,t.maintainuid = '"+userSession.getUserID()+"'";
			
			if("1".equals(agentSales.getIsleader())) {
				sql += ",t.isleader = '1'";
			}else {
				sql += ",t.isleader = ''";
			}
			sql += "  where t.busid = '"+agentSales.getBusid()+"'";
			Integer integer = agentSalesDao.executeSqlUpdate(sql, null);
			if(integer<1) {
				return "修改失败";
			}
			return "修改成功";
		}
}
