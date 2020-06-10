package com.hrt.phone.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.hrt.util.ParamPropUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.argorse.security.core.hash.MD5Wrapper;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.dao.sysadmin.IUserDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.model.UserModel;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.phone.dao.IPhoneWechatPublicAccDao;
import com.hrt.phone.service.IPhoneWechatPublicAccService;
import com.hrt.util.HttpXmlClient;

public class PhoneWechatPublicAccServiceImpl implements IPhoneWechatPublicAccService{
	
	private static final Log log = LogFactory.getLog(PhoneWechatPublicAccServiceImpl.class);
	private IPhoneWechatPublicAccDao phoneWechatPublicAccDao;
	private IUnitDao unitDao;
	private IUserDao userDao;
	private String admAppIp;

	public String getAdmAppIp() {
		return admAppIp;
	}
	public void setAdmAppIp(String admAppIp) {
		this.admAppIp = admAppIp;
	}
	public IPhoneWechatPublicAccDao getPhoneWechatPublicAccDao() {
		return phoneWechatPublicAccDao;
	}
	public void setPhoneWechatPublicAccDao(
			IPhoneWechatPublicAccDao phoneWechatPublicAccDao) {
		this.phoneWechatPublicAccDao = phoneWechatPublicAccDao;
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
	public UserBean login(UserBean userBean) throws Exception {
		UserBean result = null;
		String sql = "SELECT * FROM SYS_USER WHERE LOGIN_NAME = :name AND PWD = :pwd and SYSFLAG is null ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", userBean.getLoginName());
		String pwd = userBean.getPassword();
		String openid = userBean.getOpenid();

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
			userBean.setPassword(pwd);
			if(userBean!=null&&userBean.getUseLvl()==3){
				result = userBean;
				//if(userBean.getStatus()==1&&openid!=null){
					//userDao.executeHql("update UserModel s set s.openid='"+openid+"' where s.userID="+user.getUserID());
//					userDao.updateObject(user);
//					user.setOpenid(openid);
				//}
			}
		}
		return result;
	}
	
	@Override
	public UserBean loginByMid(UserBean userBean) {
		UserBean result = null;
		String sql = "SELECT * FROM SYS_USER WHERE LOGIN_NAME = :name and SYSFLAG is null ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", userBean.getLoginName());
//		String pwd = userBean.getPassword();
		String openid = userBean.getOpenid();

//		map.put("pwd", MD5Wrapper.encryptMD5ToString(pwd));
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
//			userBean.setPassword(pwd);
			if(userBean!=null&&userBean.getUseLvl()==3){
				result = userBean;
				//if(userBean.getStatus()==1&&openid!=null){
					//userDao.executeHql("update UserModel s set s.openid='"+openid+"' where s.userID="+user.getUserID());
//					userDao.updateObject(user);
//					user.setOpenid(openid);
				//}
			}
		}
		return result;
	}
	
	
	@Override
	public String getOpenid(String code,String getOpenidUrl) throws Exception{
		String openid = null;
		String url = ParamPropUtils.props.getProperty(getOpenidUrl)+code+"&grant_type=authorization_code";
		Map<String, String> maps = new HashMap<String, String>();//appid =wxe2f3635bde10c3f9   wxc427f61bd6079427 secret =126253c01a0840da4ecd7a149396229f  631b36526fb14b136c544eb137051b1
		try{
			String resultj=HttpXmlClient.post(url, maps);
			//解析返回结果
			Map<String ,Object> map=(Map<String, Object>) JSONObject.parse(resultj);
			if(map!=null){
				openid = (String) map.get("openid");
			}
		}catch(Exception e){
			log.info("微信公众号 获取用户openid异常"+e);
		}
		
		return openid;
	}
	
	public String getWeBindOpenid(String mid,String weixinID,String proid){
		String sql = "select * from we_mer_bind where mid=:mid and proid=:proid";
		Map param =new HashMap();
		param.put("mid", mid);
		param.put("proid", proid);
		String openid = "";
		List<Map<String,Object>> list = userDao.executeSql2(sql, param);
		if(list.size()==0&&!weixinID.isEmpty()){
			String sql2 = "insert into we_mer_bind(openid,mid,proid,cdate,ldate) values(:openid,:mid,:proid,sysdate,sysdate)";
			param.put("openid", weixinID);
			Integer count = userDao.executeSqlUpdate(sql2, param);
			if(count==1){
				openid=weixinID;
			}
		}else if(list.size()==1&&!weixinID.isEmpty()&&"null".equals(list.get(0).get("OPENID")+"")) {
			String sql2 = "update we_mer_bind set openid=:openid,ldate=sysdate where mid=:mid and proid=:proid ";
			param.put("openid", weixinID);
			Integer count = userDao.executeSqlUpdate(sql2, param);
			if(count==1){
				openid=weixinID;
			}
		}else{
			log.error("绑定出现异常：listSize->"+list.size()+",mid->"+mid+",proid->"+proid+",sysopenid->"+(list.size()>0?list.get(0).get("OPENID"):"")+",openid->"+weixinID);
		}
		return openid;
	}
	
	public String getSysOpenid(String mid,String weixinID){
		String sql = "select * from sys_user where login_name=:mid";
		Map param =new HashMap();
		param.put("mid", mid);
		
		List<Map<String,Object>> list = userDao.executeSql2(sql, param);
		String  openid = String.valueOf(list.get(0).get("OPENID")) ;
		//String  Status = String.valueOf( list.get(0).get("STATUS"));
		if(openid=="null"&&!weixinID.isEmpty()){
			String sql2 = "update sys_user set openid=:weixinID where login_name=:mid";
			param.put("weixinID", weixinID);
			Integer count = userDao.executeSqlUpdate(sql2, param);
			if(count==1){
				openid=weixinID;
			}
		}
		return openid;
	}
	
	@Override
	public UserBean queryStatusByOpenidNew(String openid){
		String sql = "select MID from we_mer_bind where openid = '"+openid+"'";
		List<Map<String, Object>> list = userDao.queryObjectsBySqlObject(sql);
		if (list.size()==0) {
			return null;
		}
		String hql=" select u from UserModel u where u.loginName='"+list.get(0).get("MID")+"'";
		List<UserModel> li = userDao.queryObjectsByHqlList(hql);
		if(li.size()>0){
			UserBean ub = new UserBean();
			UserModel u=li.get(0);
			BeanUtils.copyProperties(u, ub);
			if(ub.getUnNo() == null){
				Set<UnitModel> unitModel = u.getUnits();
				for (UnitModel unit : unitModel) {
					ub.setUnNo(unit.getUnNo());
					ub.setUnitLvl(unit.getUnLvl());
				}
			}
			return ub;
		}else{
			return null;
		}
	}
	
	@Override
	public UserBean queryStatusByOpenid(String openid){
		String hql=" select u from UserModel u where u.openid='"+openid+"'";
		List<UserModel> li = userDao.queryObjectsByHqlList(hql);
		if(li.size()>0){
			UserBean ub = new UserBean();
			UserModel u=li.get(0);
			BeanUtils.copyProperties(u, ub);
			if(ub.getUnNo() == null){
				Set<UnitModel> unitModel = u.getUnits();
				for (UnitModel unit : unitModel) {
					ub.setUnNo(unit.getUnNo());
					ub.setUnitLvl(unit.getUnLvl());
				}
			}
			return ub;
		}else{
			return null;
		}
	}

	@Override
	public UserBean queryMidUserByOpenidAndPord(String openid,String prod){
		List<Map<String,Object>> list = findOpenidBindByOpenIdAndProd(openid,prod);
		if(list.size()!=0){
			String mid = list.get(0).get("LOGIN_NAME")+"";
			String hql = " select u from UserModel u where u.loginName='" + mid + "'";
			List<UserModel> li = userDao.queryObjectsByHqlList(hql);
			if (li.size() > 0) {
				UserBean ub = new UserBean();
				UserModel u = li.get(0);
				BeanUtils.copyProperties(u, ub);
				if (ub.getUnNo() == null) {
					Set<UnitModel> unitModel = u.getUnits();
					for (UnitModel unit : unitModel) {
						ub.setUnNo(unit.getUnNo());
						ub.setUnitLvl(unit.getUnLvl());
					}
				}
				return ub;
			} else {
				return null;
			}
		}else{
			return null;
		}
	}
	
	@Override
	public boolean findIfAuto() {
		String sql="select count(*) from sys_auto t where t.ifauto=0";
		Integer count=userDao.querysqlCounts2(sql, null);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public void updateIsLoginStatus(String loginName,int status) {
		String sql="";
		Map<String, Object> map =new HashMap<String, Object>();
		if(status==0){
			sql="update sys_user t set t.islogin=:status,t.end_date=sysdate where t.login_name=:loginName";
			map.put("status", status);
			map.put("loginName", loginName);
		}else{
			sql="update sys_user t set t.islogin=:status,t.start_date=sysdate where t.login_name=:loginName";
			map.put("status", status);
			map.put("loginName", loginName);
		}
		userDao.executeSqlUpdate(sql,map);
	}

	@Override
	public List findOpenidNew(String openid){
		Map param =new HashMap();
		String sql=" select u.USER_NAME,u.LOGIN_NAME,w.openid,w.proid from we_mer_bind w,sys_user u where w.mid=u.login_name and w.openid=:openid and w.proid='10005' ";
		param.put("openid", openid);
		List<Map<String,Object>> list = userDao.executeSql2(sql, param);
		return list;
	}

	@Override
	public List findProdByOpenIdAndMid(String openid,String mid){
		Map param =new HashMap();
		String sql=" select u.USER_NAME,u.LOGIN_NAME,w.openid,w.proid from we_mer_bind w,sys_user u where w.mid=u.login_name and w.openid=:openid and w.mid=:mid ";
		param.put("openid", openid);
		param.put("mid", mid);
		List<Map<String,Object>> list = userDao.executeSql2(sql, param);
		return list;
	}

	@Override
	public String getHybphoneByMid(String mid){
        String phoneSql = "select m.hybphone from bill_merchantinfo m where m.mid='"+mid+"'";
        List<Map<String, Object>> list = userDao.queryObjectsBySqlObject(phoneSql);
        if(list.size()==1){
            return null==list.get(0).get("HYBPHONE")?null:list.get(0).get("HYBPHONE")+"";
        }
	    return null;
    }

	@Override
	public List findOpenidBindByOpenIdAndProd(String openid,String prod){
		Map param =new HashMap();
		String sql=" select u.USER_NAME,u.LOGIN_NAME,w.openid,w.proid from " +
                " we_mer_bind w,sys_user u where w.mid=u.login_name and w.openid=:openid";
		if(StringUtils.isNotEmpty(prod)){
		    sql+=" and w.proid=:proid ";
			param.put("proid", prod);
        }
		param.put("openid", openid);
		List<Map<String,Object>> list = userDao.executeSql2(sql, param);
		return list;
	}
	
	public List findOpenid(String openid){
		Map param =new HashMap();
		String sql=" select * from sys_user where openid=:openid";
		param.put("openid", openid);
		List<Map<String,Object>> list = userDao.executeSql2(sql, param);
		/*if(list.size()==0){
			return null;
		}*/
		return list;
	}
	
	public int UnbundlingNew(String openid){
		Map param =new HashMap();
		param.put("openid", openid);
		String sql2 = "update we_mer_bind set openid='',ldate=sysdate where openid=:openid ";
		Integer count = userDao.executeSqlUpdate(sql2, param);
		return count;
	}

	@Override
	public int UnbundlingNewByOpenidAndMid(String openid,String mid,String prod){
		Map param =new HashMap();
		param.put("openid", openid);
		param.put("mid", mid);
		param.put("proid", prod);
		String sql2 = "update we_mer_bind set openid='',ldate=sysdate where openid=:openid and mid=:mid and proid=:proid ";
		Integer count = userDao.executeSqlUpdate(sql2, param);
		return count;
	}
	
	public int Unbundling(String mid){
		Map param =new HashMap();
		param.put("mid", mid);
		String sql2 = "update sys_user set openid='' where login_name=:mid";
		Integer count = userDao.executeSqlUpdate(sql2, param);
		return count;
	}
	public Map<String, String> sendBangKa(String url,Map<String, String> map) throws Exception{
		JSONObject json = new JSONObject();
		log.info("银联绑卡请求:url="+url+";map="+json.toJSONString(map));
		String ss=HttpXmlClient.postSendBangKa(admAppIp+url, map);
		//log.info("银联绑卡返回:url="+url+";msg="+ss);
		//张洪强2018-06-26
		log.info("银联绑卡返回:url="+url+";map="+json.toJSONString(map)+";msg="+ss);
		map = (Map<String, String>) json.parse(ss);
		return map;
	}
	public Map<String, String> myDevicePolicyList(String url,Map<String, String> map) throws Exception{
		JSONObject json = new JSONObject();
		log.info("查询代金券请求:url="+url+";map="+json.toJSONString(map));
		String ss=HttpXmlClient.post(admAppIp+url, map);
		//log.info("银联绑卡返回:url="+url+";msg="+ss);
		//张洪强2018-06-26
		log.info("查询代金券返回:url="+url+";map="+json.toJSONString(map)+";msg="+ss);
		map = (Map<String, String>) json.parse(ss);
		return map;
	}
	public Map<String, String> devicePolicyRecordList(String url,Map<String, String> map) throws Exception{
		JSONObject json = new JSONObject();
		log.info("查询代金券折扣请求:url="+url+";map="+json.toJSONString(map));
		String ss=HttpXmlClient.post(admAppIp+url, map);
		//log.info("银联绑卡返回:url="+url+";msg="+ss);
		//张洪强2018-06-26
		log.info("查询代金券折扣返回:url="+url+";map="+json.toJSONString(map)+";msg="+ss);
		map = (Map<String, String>) json.parse(ss);
		return map;
	}
	public Map<String, String> getCardIfLoan(String url,Map<String, String> map) throws Exception{
		JSONObject json = new JSONObject();
		log.info("还款提示请求:url="+url+";map="+json.toJSONString(map));
		String ss=HttpXmlClient.post(admAppIp+url, map);
		//log.info("银联绑卡返回:url="+url+";msg="+ss);
		//张洪强2018-07-25
		log.info("还款提示返回:url="+url+";map="+json.toJSONString(map)+";msg="+ss);
		map = (Map<String, String>) json.parse(ss);
		return map;
	}
	public Map<String, String> pushRepaymentLoan(String url,Map<String, String> map) throws Exception{
		JSONObject json = new JSONObject();
		log.info("还款通知请求:url="+url+";map="+json.toJSONString(map));
		//上线修改并删除本注释
		String ss=HttpXmlClient.post(admAppIp+url, map);
		//log.info("银联绑卡返回:url="+url+";msg="+ss);
		log.info("还款通知返回:url="+url+";map="+json.toJSONString(map)+";msg="+ss);
		map = (Map<String, String>) json.parse(ss);
		return map;
	}
	@Override
	public boolean addReplayAttack(String uuid, String timeStamps) {
		if(uuid==null||timeStamps==null||"".equals(uuid)||"".equals(timeStamps)) {
			return false;
		}
		String sql = "select count(1) from temp t where t.uuid='"+uuid+"' and t.timestamps='"+timeStamps+"'";
		Integer i = phoneWechatPublicAccDao.querysqlCounts2(sql, null);
		if(i>0) {
			return false;
		}else {
			phoneWechatPublicAccDao.executeUpdate("insert into temp (UUID, "
					+ "TIMESTAMPS) values ('"+uuid+"', '"+timeStamps+"')");
			return true;
		}
	}

	@Override
	public String getMerchantBnoByMid(String mid) throws Exception {
		String sql="select t.bno from bill_merchantinfo t where t.mid=:mid";
		Map t=new HashMap();
		t.put("mid",mid);
		List<Map<String, String>> list = phoneWechatPublicAccDao.queryObjectsBySqlListMap(sql,t);
		return list.size()==1 && list.get(0).get("BNO")!=null?list.get(0).get("BNO").toString():"";
	}

}
