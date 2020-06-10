package com.hrt.frame.service.sysadmin;

import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IUserService {
	
	/**
	 * 方法功能：添加用户
	 * 参数：userBean
	 *     loginName 当前登录用户名 
	 * 返回值：void
	 * 异常：
	 */
	void saveUser(UserBean userBean, UserBean user) throws Exception;
	
	/**
	 * 方法功能：分页查询用户信息
	 * 参数：user
	 * 返回值：DataGridBean实例
	 * 异常：
	 */
	DataGridBean queryUsers(UserBean user,String unNo,Integer userID) throws Exception;
	DataGridBean queryUsers002401(UserBean user,String unNo,Integer userID) throws Exception;
	
	/**
	 * 方法功能：删除用户
	 * 参数：ids TB_USER表主键
	 * 返回值：void
	 * 异常：
	 */
	void deleteUser(String ids);
	
	/**
	 * 方法功能：修改用户信息
	 * 参数：userBean
	 * 	   loginName 当前登录用户名
	 * 返回值：void
	 * 异常：
	 */
	void updateUser(UserBean userBean, String loginName) throws Exception;
	
	/**
	 * 方法功能：用户登录
	 * 参数：userBean
	 * 返回值：UserBean实例
	 * 异常：
	 */
	UserBean login(UserBean userBean) throws Exception;
	
	/**
	 * 修改密码
	 */
	boolean updatePwd(UserBean userBean, UserBean user) throws Exception ;

	/**
	 * 重置密码
	 */
	void updatePwd(UserBean userBean, String loginName) throws Exception;
	
	/**
	 * 判断当前账号是否有两个机构
	 */
	UserBean loginNameUnit(String loginName);
	
	/**
	 * 查询用户码
	 */
	DataGridBean searchUser(String nameCode,String unno) throws Exception;
	
	/**
	 * 停用用户
	 */
	void updateCloseUser(UserBean userBean) throws Exception;
	
	/**
	 * 启用用户
	 */
	void updateStartRole(UserBean userBean) throws Exception;
	
	
	/**
	 * 查询用户是否存在
	 */
	Integer queryUserCounts(UserBean userBean);
	
	/**
	 * 查询短信验证码
	 */
	String queryMesRand(UserBean userBean);

	
	/**
	 * 重置商户密码
	 */
	DataGridBean queryUsersForMerchant(UserBean user);
	
	void updateIsLoginStatus(String loginName, int status);

	/**
	 * 判断是否开启单用户登录/是否限制登录失败次数
	 * ture:开启；false 关闭
	 */
	boolean findIfAuto();
	boolean updateIfAuto(String f,String name);
	/**
	 * ifauto=3 开启短信验证码  ifauto=4 关闭
	 * ture:开启；false 关闭
	 */
	boolean findIfAuto(Integer ifauto);
	
	Integer findIfAuth(Integer ifauto);

	DataGridBean queryUserLoginLog(UserBean user);
	
	void saveUserLoginLog(UserBean user,String login_type,String login_message,Integer status);
	
	boolean queryRoleLvlByUser(UserBean user);
	
	void updateBannerToClose(UserBean user);
	
	/**
	 * 修改登录密码已错误次数，超过三次，不允许登录
	 */
	void updateErrorPass(UserBean user);
	/**
	 * 查询密码失败次数
	 */
	Integer queryErrorPassTime(UserBean user);
	/**
	 * 更新密码错误次数
	 */
	void updateErrorTimeRest(UserBean user);
	/**
	 * 最后修改密码日期是否在90天内
	 * @return true-不在90天 false-在90天 
	 */
	boolean queryPwdModDate(UserBean user);
	/**
	 * 查询本公司名下的用户
	 */
	DataGridBean searchUserGroup(String nameCode, UserBean user);
}
