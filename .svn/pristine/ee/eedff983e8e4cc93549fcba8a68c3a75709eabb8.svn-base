package com.hrt.frame.service.sysadmin;

import java.util.List;

import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.TreeNodeBean;
import com.hrt.frame.entity.pagebean.UnitBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IUnitService {
	
	/**
	 * 查询表格机构
	 */
	List<UnitBean> queryUnits(UnitBean unitBean,UserBean user) throws Exception;
	
	/**
	 * 添加机构
	 */
	void saveUnit(UnitBean unit, String loginName) throws Exception;
	
	/**
	 * 修改机构
	 */
	void updateUnit(UnitBean unitBean, String loginName) throws Exception;
	
	/**
	 * 删除机构
	 */
	boolean deleteUser(UnitBean unitBean) throws Exception;
	
	/**
	 * 查询菜单机构
	 * @param userSession 
	 */
	List<TreeNodeBean> queryTreeUnits(UnitBean unitBean, UserBean userSession) throws Exception;
	
	/**
	 * 查询省市信息
	 */
	DataGridBean searchProvince() throws Exception;
	
	/**
	 * 查询机构显示在相应的select
	 */
	DataGridBean queryUnitsCombogrid(UnitBean unit) throws Exception;
	
	/**
	 * 根据unno得到机构
	 */
	UnitModel getUnitModel(String unNo);
	
	/**
	 * 查询机构下的用户
	 */
	List<UserBean> searchUnitUser(String unNo) throws Exception;
	
	/**
	 * 启用机构
	 */
	void updateStartUnit(UnitBean unitBean) throws Exception;
	
	/**
	 * 停用机构
	 */
	void updateCloseUnit(UnitBean unitBean) throws Exception;
	
	/**
	 * 方法功能：查询所有机构
	 * 参数：role
	 * 返回值：DataGridBean实例
	 * 异常：
	 */
	DataGridBean queryUnitsCombogrid(UnitBean unit, UserBean user) throws Exception;
	DataGridBean queryRebateUnitsCombogrid(UnitBean unit, UserBean user);
	
	/**
	 * 方法功能：查询用户对应的机构
	 * 参数：role
	 * 返回值：DataGridBean实例
	 * 异常：
	 */
	DataGridBean queryUnitsCombogrid(Integer userID) throws Exception;
	
	/**
	 * 查询简码是否存在
	 */
	Integer queryUnitCounts(String unitCode);
	/**
	 * 判断查询的机构是否是登录用户下的
	 */
	boolean judgeUnnoIsInUser(UserBean user, String unno);
	
}
