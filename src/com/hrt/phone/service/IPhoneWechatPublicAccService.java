package com.hrt.phone.service;

import java.util.List;
import java.util.Map;

import com.hrt.frame.entity.model.UserModel;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IPhoneWechatPublicAccService {

	/**
	 * 方法功能：用户登录
	 * 参数：userBean
	 * 返回值：UserBean实例
	 * 异常：
	 */
	UserBean login(UserBean userBean) throws Exception;
	/**
	 * 方法功能：用户登录
	 * 参数：userBean
	 * 返回值：UserBean实例
	 * 异常：
	 */
	UserBean loginByMid(UserBean user);
	
	/**
	 * 判断是否开启单用户登录
	 * ture:开启；false 关闭
	 */
	boolean findIfAuto();
	
	void updateIsLoginStatus(String loginName, int status);
	
	String getOpenid(String code,String getOpenidUrl) throws Exception;
	public String getSysOpenid(String mid,String weixinID);
	public String getWeBindOpenid(String mid,String weixinID,String proid);
	UserBean queryStatusByOpenid(String openid);

	/**
	 * 根据公众号唯一id与绑定的产品类型获取绑定的商户登录信息
	 * @param openid 公众号唯一id
	 * @param prod 公众号绑定的产品类型
	 * @return
	 */
	UserBean queryMidUserByOpenidAndPord(String openid,String prod);
	UserBean queryStatusByOpenidNew(String openid);
	List findOpenid(String code);
	List findOpenidNew(String openid);

	/**
	 * 根据openid与mid查询产品proid信息
	 * @param openid
	 * @param mid
	 * @return
	 */
	List findProdByOpenIdAndMid(String openid,String mid);
    /**
     * 是否有绑定的产品
     * @param openid 公众号唯一用户id
     * @param prod 产品prod为空,查询所有的绑定
     * @return
     */
	List findOpenidBindByOpenIdAndProd(String openid,String prod);

	/**
	 * 获取商户绑定的手机号
	 * @param mid 商户mid
	 * @return
	 */
    String getHybphoneByMid(String mid);
	int Unbundling(String mid);
	int UnbundlingNew(String openid);

	/**
	 * 根据openid与mid,prod解绑微信公众号绑定的产品
	 * @param openid 公众号唯一用户id
	 * @param mid 商户mid
	 * @param prod 产品类型
	 * @return
	 */
	int UnbundlingNewByOpenidAndMid(String openid,String mid,String prod);
	boolean addReplayAttack(String uuid,String timeStamps);
	Map<String, String> sendBangKa(String url,Map<String, String> map)throws Exception;
	Map<String, String> myDevicePolicyList(String url,Map<String, String> map)throws Exception;
	Map<String, String> devicePolicyRecordList(String url,Map<String, String> map)throws Exception;
	Map<String, String> getCardIfLoan(String url,Map<String, String> map)throws Exception;
	Map<String, String> pushRepaymentLoan(String url,Map<String, String> map)throws Exception;

	/**
	 * 获取商户mid的产品类型
	 * @param mid
	 * @return
	 * @throws Exception
	 */
	String getMerchantBnoByMid(String mid) throws Exception;
}
