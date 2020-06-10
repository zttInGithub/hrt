package com.hrt.phone.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.argorse.security.core.hash.MD5Wrapper;
import com.hrt.biz.bill.dao.IAgentUnitDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.pagebean.AgentUnitBean;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.dao.sysadmin.IUserDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.model.UserModel;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.phone.dao.IPhoneUserDao;
import com.hrt.phone.service.IPhoneUserService;

public class PhoneUserServiceImpl implements IPhoneUserService {

	private IPhoneUserDao phoneUserDao;
	private IUnitDao unitDao;
	private IUserDao userDao;
	private IAgentUnitDao agentUnitDao;
	
	public IPhoneUserDao getPhoneUserDao() {
		return phoneUserDao;
	}
	public void setPhoneUserDao(IPhoneUserDao phoneUserDao) {
		this.phoneUserDao = phoneUserDao;
	}
	public IAgentUnitDao getAgentUnitDao() {
		return agentUnitDao;
	}
	public void setAgentUnitDao(IAgentUnitDao agentUnitDao) {
		this.agentUnitDao = agentUnitDao;
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
	 * 登录
	 */
	@Override
	public UserBean loginAgent(UserBean userBean) throws Exception {
		UserBean result = null;
		String sql = "SELECT * FROM SYS_USER WHERE LOGIN_NAME = :name AND PWD = :pwd";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", userBean.getLoginName());
		String pwd = userBean.getPassword();
	 	map.put("pwd", MD5Wrapper.encryptMD5ToString(pwd));
		List<UserModel> userList = userDao.queryObjectsBySqlList(sql,map,UserModel.class);
		if (userList != null && userList.size() > 0) {
			UserModel user = userList.get(0);
			if(userBean.getUnNo() == null){
				Set<UnitModel> unitModel = userList.get(0).getUnits();
				for (UnitModel unit : unitModel) {
					userBean.setUnNo(unit.getUnNo());
					userBean.setUnitLvl(unit.getUnLvl());
				}
			}
			BeanUtils.copyProperties(user, userBean);
			userBean.setUnitName(unitDao.getObjectByID(UnitModel.class, userBean.getUnNo()).getUnitName());
			result = userBean;
		}
		
		return result;
	}
	
	public AgentUnitModel getAgentByUnno(UserBean user) throws Exception{
		AgentUnitModel m = agentUnitDao.queryObjectByHql("from AgentUnitModel t where t.unno=?", new Object[]{user.getUnNo()}); 
		return m;
	}
	/**
	 * 登录
	 */
	@Override
	public UserBean login(UserBean userBean) throws Exception {
		UserBean result = null;
		//String sql = "SELECT * FROM SYS_USER WHERE LOGIN_NAME = :name AND PWD = :pwd";
		String sql = "SELECT * FROM SYS_USER WHERE LOGIN_NAME = :name";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", userBean.getLoginName());
		//String pwd = userBean.getPassword();
	//	map.put("pwd", MD5Wrapper.encryptMD5ToString(pwd));
		List<UserModel> userList = userDao.queryObjectsBySqlList(sql,map,UserModel.class);
		if (userList != null && userList.size() > 0) {
			UserModel user = userList.get(0);
			if(userBean.getUnNo() == null){
				Set<UnitModel> unitModel = userList.get(0).getUnits();
				for (UnitModel unit : unitModel) {
					userBean.setUnNo(unit.getUnNo());
					userBean.setUnitLvl(unit.getUnLvl());
				}
			}
			BeanUtils.copyProperties(user, userBean);
			userBean.setUnitName(unitDao.getObjectByID(UnitModel.class, userBean.getUnNo()).getUnitName());
		//	userBean.setPassword(pwd);
			result = userBean;
		}
		
		return result;
	}

	@Override
	public Map loginYSB(UserBean user) throws Exception {
		List<Map<String, Object>> list= userDao.queryObjectsBySqlObject("select * from bill_merchantinfo t where t.mid='"+user.getLoginName()+"' and t.mid like '21%' ");
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 传统app登录
	 */
	@Override
	public UserBean agentLogin(UserBean userBean) throws Exception {
		UserBean result = null;
		String hql = "SELECT s FROM UserModel s WHERE s.loginName = :name AND s.password = :pwd and s.status=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", userBean.getLoginName());
		String pwd = userBean.getPassword();
		map.put("pwd", MD5Wrapper.encryptMD5ToString(pwd));
		List<UserModel> list = userDao.queryObjectsByHqlList(hql, map);
		if (list != null && list.size() > 0) {
				UserModel user = list.get(0);
				if(user.getUseLvl()==0){
				}else{
					String sql = "SELECT to_char(ag.BUSID) as BUSID FROM bill_agentsalesinfo ag where ag.user_id="+user.getUserID()+" and ag.maintainType!='D' ";
					List<Map<String, String>> list1 = userDao.queryObjectsBySqlListMap(sql, null);
					if(list1!=null&&list1.size()>0){
						userBean.setBusid(Integer.valueOf(list1.get(0).get("BUSID").toString()));
					}else{
						return null;
					}
				}
				if(userBean.getUnNo() == null){
					Set<UnitModel> unitModel = user.getUnits();
					for (UnitModel unit : unitModel) {
						userBean.setUnNo(unit.getUnNo());
						userBean.setUnitLvl(unit.getUnLvl());
					}
				}
				BeanUtils.copyProperties(user, userBean);
				userBean.setUnitName(unitDao.getObjectByID(UnitModel.class, userBean.getUnNo()).getUnitName());
			//	userBean.setPassword(pwd);
				result = userBean;
			}
		return result;
	}
	@Override
	public List<Map<String,String>> queryDataForHYB(String loginName) {
		String sql="select MID,rname MER_NAME,contactperson CONTACTER,'' as CONTACTOR_CELLPHONE,contactaddress BUSINESS_ADDRESS," +
					" b.LOGIN_Name USER_NAME,PWD PASSWORD,'DZ',sysdate,'LOGO_IMG_URL','BUSINESS_LICENSE_IMG_URL','QQ','LANDMARK'," +
					" 'CITY','INSUSTRY_TYPE' " +
					" from  bill_merchantinfo a, Sys_User b " +
					" where a.MID = b.LOGIN_Name and a.mid='"+loginName+"'";
		List<Map<String,String>> list = phoneUserDao.queryObjectsBySqlList(sql);
		return list;
	}
	@Override
	public List<Map<String,Object>> queryMerchantPassword(UserBean user) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "SELECT * FROM SYS_USER WHERE LOGIN_NAME = :name AND PWD = :pwd";
		String pwd = user.getPassword();
		map.put("name", user.getLoginName());
		map.put("pwd", MD5Wrapper.encryptMD5ToString(pwd));
		List<UserModel> userList = userDao.queryObjectsBySqlList(sql,map,UserModel.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> mapResponse= new HashMap<String, Object>();
		if (userList != null && userList.size() > 0) {
			mapResponse.put("flag", true);
			list.add(mapResponse);
		}else{
			mapResponse.put("flag", false);
			list.add(mapResponse);
		}
		return list;
	}
	@Override
	public Integer findMerchantIsM35(String loginName) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select count(1) from bill_merchantinfo t where t.mid=:loginName and t.isM35=1 ";
		map.put("loginName", loginName);
		Integer count=userDao.querysqlCounts2(sql, map);
		if(count>0){
			return 1;
		}else{
			return 0;
		}
	}

}
