package com.hrt.frame.service.sysadmin.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.argorse.security.core.hash.MD5Wrapper;
import com.hrt.frame.dao.sysadmin.IRoleDao;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.dao.sysadmin.IUserDao;
import com.hrt.frame.entity.model.RoleModel;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.model.UserModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.TreeNodeBean;
import com.hrt.frame.entity.pagebean.UnitBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.service.sysadmin.IUnitService;

public class UnitServiceImpl implements IUnitService {
	
	private IUnitDao unitDao;
	
	private IUserDao userDao;
	
	private IRoleDao roleDao;
	
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
	
	public IRoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	/**
	 * 转换UnitModel为树节点数据格式
	 */
	private TreeNodeBean changeToTreeNode(UnitModel unit,boolean recursive){
		TreeNodeBean treeNodeBean = new TreeNodeBean();
		treeNodeBean.setId(unit.getUnNo());
		treeNodeBean.setText(unit.getUnitName());

		if(unit.getChildren() != null && unit.getChildren().size() > 0){
			treeNodeBean.setState("closed");
			if(recursive){
				Set<UnitModel> set = unit.getChildren();
				List<TreeNodeBean> list = new ArrayList<TreeNodeBean>();
				for (UnitModel unitModel : set) {
					TreeNodeBean treeNode=changeToTreeNode(unitModel, true);
					list.add(treeNode);
				}
				treeNodeBean.setChildren(list);
				treeNodeBean.setState("open");
			}
		}
		return treeNodeBean;
	}
	
	/**
	 * 转换UnitModel为treegrid格式
	 */
	private UnitBean changeModelToBean(UnitModel unitModel, boolean recursive) {
		UnitBean unitBean = new UnitBean();
		unitBean.setUnNo(unitModel.getUnNo().trim());
		unitBean.setUnitName(unitModel.getUnitName());
		unitBean.setCreateDate(unitModel.getCreateDate());
		unitBean.setCreateUser(unitModel.getCreateUser());
		unitBean.setUpdateDate(unitModel.getUpdateDate());
		unitBean.setUpdateUser(unitModel.getUpdateUser());
		unitBean.setProvinceCode(unitModel.getProvinceCode());
		unitBean.setUnLvl(unitModel.getUnLvl());
		unitBean.setStatus(unitModel.getStatus());
		unitBean.setUnAttr(unitModel.getUnAttr());
		unitBean.setUnitCode(unitModel.getUnitCode());
		
		if(unitModel.getStatus() == 1){
			unitBean.setStatusName("启用");
		}else{
			unitBean.setStatusName("停用");
		}
		
		if (unitModel.getChildren() != null && unitModel.getChildren().size() > 0) {
			if (recursive) {
				Set<UnitModel> unitSet = unitModel.getChildren();
				List<UnitBean> unitList = new ArrayList<UnitBean>();
				for (UnitModel unit : unitSet) {
					UnitBean subUnit = changeModelToBean(unit, true);
					unitList.add(subUnit);
				}
				
				unitBean.setChildren(unitList);
			}
		} 
		
		return unitBean;
	}
	
	/**
	 * 格式化UnitModel为combogrid数据格式
	 */
	private DataGridBean formatToCombogrid(List<UnitModel> unitList, long total) {
		List<UnitBean> unitBeanList = new ArrayList<UnitBean>();
		for(UnitModel unitModel : unitList) {
			UnitBean unitBean = new UnitBean();
			unitBean.setUnNo(unitModel.getUnNo());
			unitBean.setUnitName(unitModel.getUnitName());
			unitBean.setUnLvl(unitModel.getUnLvl());
			unitBeanList.add(unitBean);
		}
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(unitBeanList);
		return dgb;
	}
	
	/**
	 * 添加机构编号时判断后三位是否符合规则
	 */
	private String addUnNoIsNumber(String unNo){
		String hql="select max(u.unNo) from UnitModel u where u.unNo like :unNo";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("unNo", unNo+'%');
		String result = unitDao.queryUnitMaxUnNo(hql, map);
		if(result != null){
			String u = result.substring(3, 6);
			if(Integer.parseInt(u)==999){
				throw new IllegalAccessError("开通代理商失败：unNo"+unNo);
			}
			//result = unNo +(Integer.parseInt(u)+1001+"").substring(1,3);
			result = unNo +String.format("%03d", Integer.parseInt(u)+1);
		}else{
			result = unNo+"000";
		}
		return result.toString();
	}

	/**
	 * 查询表格机构
	 */
	@Override
	public List<UnitBean> queryUnits(UnitBean unitBean,UserBean user) throws Exception {
		String hql = "from UnitModel u where u.parent is null";
		if (unitBean != null && unitBean.getUnNo() != null) {
			hql = "from UnitModel u where u.parent.unNo = " + unitBean.getUnNo();
		}
		List<UnitModel> unitModelList = unitDao.queryObjectsByHqlList(hql);
		List<UnitBean> unitList = new ArrayList<UnitBean>();
		for (UnitModel unit : unitModelList) {
			unitList.add(changeModelToBean(unit, true));
		}
		
		return unitList;
	}
	
	/**
	 * 根据unno得到机构
	 */
	@Override
	public UnitModel getUnitModel(String unNo) {
		UnitModel unit = unitDao.getObjectByID(UnitModel.class,unNo);
		return unit;
	}

	/**
	 * 添加机构
	 */
	@Override
	public void saveUnit(UnitBean unitBean, String loginName)  throws Exception {
		//添加机构
		String provinceCode=unitBean.getProvinceCode().replaceAll(",", "").trim();		//区域编号
		String unLvl = unitBean.getUnLvl().toString();		//级别编号
		String unitType=null;		//后三位
		if(StringUtils.isNotEmpty(unLvl)){
			if(unLvl.equals("0")){
				unitBean.setUnAttr(2);
				unitBean.setSeqNo2(30000);
				unitType=addUnNoIsNumber(provinceCode+unLvl);
			}else if(unLvl.equals("1")){		//分公司
				unitBean.setUnAttr(1);
				unitBean.setSeqNo2(30000);
				if("110000".equals(unitBean.get_parentId())){
					unitType=provinceCode+unLvl+"000";
				}else{
					unitType=addUnNoIsNumber(provinceCode+unLvl);
				}
			}else{		//代理机构
				unitBean.setUnAttr(1);
				unitBean.setSeqNo2(4000);
				unitType=addUnNoIsNumber(provinceCode+unLvl);
			}
		}
		
		UnitModel unitModel = new UnitModel();
		BeanUtils.copyProperties(unitBean, unitModel);	
		unitModel.setStatus(1);
		unitModel.setSeqNo(0);
		unitModel.setUnNo(unitType);
		unitModel.setProvinceCode(provinceCode);
		unitModel.setCreateDate(new Date());
		unitModel.setCreateUser(loginName);
		unitModel.setUpdateDate(new Date());
		unitModel.setUpdateUser(loginName);
		UnitModel parent = new UnitModel();
		if(unitBean.get_parentId() != null && !"".equals(unitBean.get_parentId().toString().trim())){
			parent.setUnNo(unitBean.get_parentId());
			unitModel.setParent(parent);
		}
		unitDao.saveObject(unitModel);
		
		if(unitModel.getUnAttr() == 1){
			//添加用户
			UserModel userModel = new UserModel();
			userModel.setCreateDate(new Date());
			userModel.setUseLvl(0);
			userModel.setCreateUser(loginName);
			userModel.setUserName(unitBean.getUnitName()+"管理员");
			userModel.setLoginName(unitType+"99");	//账号默认加99
			userModel.setPassword(MD5Wrapper.encryptMD5ToString("123456"));		//默认密码123456
			Set<UnitModel> units = new HashSet<UnitModel>();
			units.add(unitModel);
			userModel.setUnits(units);
			String hql="from RoleModel r where r.roleLevel=:roleLevel and r.roleAttr = 0";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleLevel",unitModel.getUnLvl());
			List<RoleModel> roleList = roleDao.queryObjectsByHqlList(hql, map);
			Set<RoleModel> roles = new HashSet<RoleModel>();
			for (RoleModel role : roleList) {
				roles.add(role);
			}
			userModel.setRoles(roles);
			userModel.setResetFlag(0);
			userModel.setStatus(1);
			userDao.saveObject(userModel);
		}
	}

	/**
	 * 修改机构
	 */
	@Override
	public void updateUnit(UnitBean unitBean, String loginName) throws Exception {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, unitBean.getUnNo());

		//机构状态如果为0则当前机构下所有用户的状态都为停用
		if(unitBean.getStatus() == 0){
			Set<UserModel> userModle = unitModel.getUsers();
			for (UserModel user : userModle) {
				user.setStatus(0);
				userDao.updateObject(user);
			}
		}
		
		BeanUtils.copyProperties(unitBean, unitModel);	
		unitModel.setUpdateDate(new Date());
		unitModel.setUpdateUser(loginName);
		UnitModel parent = new UnitModel();
		if(unitBean.get_parentId() != null && !"".equals(unitBean.get_parentId().toString().trim())){
			parent.setUnNo(unitBean.get_parentId());
			unitModel.setParent(parent);
		}
		unitDao.updateObject(unitModel);
	}

	/**
	 * 删除机构
	 */
	@Override
	public boolean deleteUser(UnitBean unitBean) throws Exception {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, unitBean.getUnNo());
		//是否有角色关联此菜单
		if(unitModel.getUsers() != null && unitModel.getUsers().size() > 0){
			return false;
		}
		
		unitDao.deleteObject(unitModel);
		return true;
	}

	/**
	 * 查询菜单机构
	 */
	@Override
	public List<TreeNodeBean> queryTreeUnits(UnitBean unitBean,UserBean user) throws Exception {
		String hql="";
		if( user.getUnitLvl()==0 | user.getUnNo().equals("110000") ){
		    hql = "from UnitModel u where u.parent is null";
		}else if(user.getUnNo() != null && !"".equals(user.getUnNo())){
			hql = "from UnitModel u where u.unNo="+user.getUnNo();
		}
		
		List<UnitModel> unitList = unitDao.queryObjectsByHqlList(hql);
		List<TreeNodeBean> treeNodeList = new ArrayList<TreeNodeBean>();
		for (UnitModel unit : unitList) {
			treeNodeList.add(this.changeToTreeNode(unit,true));
		}
		return treeNodeList;
	}
	
	/**
	 * 查询机构显示在相应的select
	 */
	@Override
	public DataGridBean queryUnitsCombogrid(UnitBean unit) throws Exception {
		String hql = "from UnitModel where 1 = 1";
		String hqlCount = "select count(*) from UnitModel";
		if (unit.getSort() != null) {
			hql += " order by " + unit.getSort() + " " + unit.getOrder();
		}
		long counts = unitDao.queryCounts(hqlCount);
		List<UnitModel> unitList = unitDao.queryObjectsByHqlList(hql);
		
		DataGridBean dataGridBean = formatToCombogrid(unitList, counts);
		
		return dataGridBean;
	}
	
	/**
	 * 查询省市信息
	 */
	@Override
	public DataGridBean searchProvince() throws Exception {
		String sql="SELECT PROVINCE_CODE,PROVINCE_NAME FROM SYS_PROVINCE ORDER BY PROVINCE_CODE";
		List<Map<String,String>> proList = unitDao.executeSql(sql);
		
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(proList.size());
		dgd.setRows(proList);
		
		return dgd;
	}
	
	/**
	 * 查询机构下的用户
	 */
	@Override
	public List<UserBean> searchUnitUser(String unNo) throws Exception {
		List<UserBean> userList = new ArrayList<UserBean>();
		UnitModel unit = unitDao.getObjectByID(UnitModel.class, unNo);
		for (UserModel userModel : unit.getUsers()) {
			UserBean userBean = new UserBean();
			BeanUtils.copyProperties(userModel, userBean);
			userList.add(userBean);
		}
		return userList;
	}

	/**
	 * 启用机构
	 */
	@Override
	public void updateStartUnit(UnitBean unitBean) throws Exception {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, unitBean.getUnNo());
		unitModel.setStatus(1);
		unitDao.updateObject(unitModel);
	}

	/**
	 * 停用机构
	 */
	@Override
	public void updateCloseUnit(UnitBean unitBean) throws Exception {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, unitBean.getUnNo());
		unitModel.setStatus(0);
		unitDao.updateObject(unitModel);
	}

	/**
	 * 方法功能：查询所有机构
	 * 参数：role
	 * 返回值：DataGridBean实例
	 * 异常：
	 */
	@Override
	public DataGridBean queryUnitsCombogrid(UnitBean unit, UserBean user)
			throws Exception {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		String sql ="";
		Map<String, Object> map = new HashMap<String, Object>();
		if(("110000".equals(user.getUnNo()) || ("110000".equals(unitModel.getParent().getUnNo())) && unitModel.getUnAttr() ==2)){
		 sql = "SELECT * FROM SYS_UNIT WHERE (UPPER_UNIT = :unNo  AND STATUS = 1) OR UNNO = :unNo";
		 map.put("unNo", "110000");
		}else if("901000".equals(user.getUnNo())){
			sql = "SELECT * FROM SYS_UNIT  WHERE (UPPER_UNIT = :unNo  AND STATUS = 1) OR UNNO = :unNo";
			map.put("unNo", user.getUnNo());
		}else{
			sql = "SELECT * FROM SYS_UNIT WHERE UNNO = :unNo";
			map.put("unNo", user.getUnNo());
		}
		if(user.getLoginName()!=null&&user.getLoginName().startsWith("baodan0")){
			sql = "SELECT * FROM SYS_UNIT WHERE UNNO ='111000' or UNNO ='110000' ";
			map = null;
		}
		List<UnitModel> unitList = unitDao.queryObjectsBySqlList(sql, map, UnitModel.class);
		
		DataGridBean dataGridBean = formatToCombogrid(unitList, unitList.size());
		
		return dataGridBean;
	}

    @Override
    public DataGridBean queryRebateUnitsCombogrid(UnitBean unit, UserBean user) {
        String sql = "SELECT * FROM SYS_UNIT WHERE un_lvl<=1 ";
        List<UnitModel> unitList = unitDao.queryObjectsBySqlList(sql, null, UnitModel.class);
        DataGridBean dataGridBean = formatToCombogrid(unitList, unitList.size());
        return dataGridBean;
    }

	/**
	 * 查询简码是否存在
	 */
	@Override
	public Integer queryUnitCounts(String unitCode){
		String hqlCount = "select count(unNo) from UnitModel where unitCode = :unitCode";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unitCode", unitCode);
		Long userCounts = unitDao.queryCounts(hqlCount, map);
		return userCounts.intValue();
	}

	/**
	 * 方法功能：查询用户对应的机构
	 * 参数：role
	 * 返回值：DataGridBean实例
	 * 异常：
	 */
	@Override
	public DataGridBean queryUnitsCombogrid(Integer userID)
			throws Exception {
		String sql ="SELECT * FROM SYS_UNIT WHERE UNNO IN (SELECT UNNO FROM SYS_UNIT_USER WHERE USER_ID = "+userID+")";

		List<UnitModel> unitList = unitDao.queryObjectsBySqlList(sql, null, UnitModel.class);
		
		DataGridBean dataGridBean = formatToCombogrid(unitList, unitList.size());
		
		return dataGridBean;
	}

	/**
	 * 方法功能：20200109判断查询的机构是否是登录用户下的
	 */
	@Override
	public boolean judgeUnnoIsInUser(UserBean user,String unno) {
		if(user.getUnitLvl()==0) {
			return true;
		}
		String judgeUnno = "SELECT count(1) FROM sys_unit u where 1=1"+
				" and u.unno = '"+unno+"'"+
				" start with u.unno = '"+user.getUnNo()+"' connect by prior u.unno = u.upper_unit";
		Integer counts2 = unitDao.querysqlCounts2(judgeUnno, null);
		return counts2>0 ? true:false;
	}
}
