package com.hrt.biz.util.auth;

import java.io.File;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.xml.ws.BindingProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xwf.cloudauth.api.uid.AuthCardWithoutOTPRequest;
import com.xwf.cloudauth.api.uid.AuthCardWithoutOTPResponse;
import com.xwf.cloudauth.api.uid.MatchIdentityRequest;
import com.xwf.cloudauth.api.uid.MatchIdentityResponse;
import com.xwf.cloudauth.api.uid.UserIdentityApiService;
import com.xwf.cloudauth.api.uid.UserIdentityApiServiceImplService;


/**
 * 小微封 SFID API 封装
 * @author tenglong
 * 
 */
public class SfidAuthMain {

	// ws服务器地址
	//三方测试
	private final static String wsHttp = "https://prmcap.xwf-id.com/api/UidApiService";
	//三方生产
	//private final static String wsHttp = "https://prdcap.xwf-id.com/api/UidApiService";
	
	// ICP 唯一编码 
	private final static String merchantCode = "www.hrtpayment.com";
	
	// 网站Session 标识符 
	private  String sessionId ;
    
	private UserIdentityApiService uidApiService;

    //通过工厂生成一个WebServiceImpl实例，WebServiceImpl是wsimport工具生成的
    //static UserIdentityApiService service ;
    
    public UserIdentityApiService getUidApiService() {
		return uidApiService;
	}

	public void setUidApiService(UserIdentityApiService uidApiService) {
		this.uidApiService = uidApiService;
	}

	private static final Log log = LogFactory.getLog(SfidAuthMain.class);
/*	static {
		//测试
		String path  ="D:/u01/sign/";
		//开发
		//String path  =File.separator+"u01"+File.separator+"sign"+File.separator;
		// 配置信任证书地址及密码
		System.setProperty("javax.net.ssl.trustStore",path+"cacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		// 配置客户端证书地址及密码
		System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
	
		System.setProperty("javax.net.ssl.keyStore",path+"www.hrtpayment.com.p12");
		
		System.setProperty("javax.net.ssl.keyStorePassword", "hrt1234");
		
		//初始化代理类
	    UserIdentityApiServiceImplService factory = new UserIdentityApiServiceImplService();
	    //通过工厂生成一个WebServiceImpl实例，WebServiceImpl是wsimport工具生成的
	    service = factory.getUserIdentityApiServiceImplPort();
	    
		 //设置ws服务器地址，如果和wsdl中一致，不需要设置
	    BindingProvider provider = (BindingProvider) service;
		provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsHttp);
		
	}*/

	/**
	 * 
	 * 认证身份证 
	 * userName String 身份证姓名 
	 * idNumber String 身份证号码
	 * @throws RemoteException 
	 * 
	 */
	public  Map<String, String> matchIdentity(String userName,String idNumber) throws RemoteException {
		MatchIdentityRequest mir = new MatchIdentityRequest();
		sessionId = UUID.randomUUID().toString();
		mir.setUserName(userName);
		mir.setIdNumber(idNumber);
		mir.setMerchantCode(merchantCode);
		mir.setSessionId(sessionId);
		MatchIdentityResponse mr = uidApiService.matchIdentity(mir);
		Map<String, String> result = new HashMap<String, String>();
		
		if("100".endsWith(mr.getResultCode())){
			result.put("msg", "认证成功");
			result.put("status", "2");
		}else if("431".endsWith(mr.getResultCode())){
			result.put("msg", "身份证信息不匹配");
			result.put("status", "1");
		}else if("432".endsWith(mr.getResultCode())){
			result.put("msg", "身份证信息匹配失败");
			result.put("status", "1");
		}else if("200".endsWith(mr.getResultCode())){
			result.put("msg", "无效的商户号");
			result.put("status", "1");
		}else if("204".endsWith(mr.getResultCode())){
			result.put("msg", "无效的 sessionId");
			result.put("status", "1");
		}else if("303".endsWith(mr.getResultCode())){
			result.put("msg", "商户号与服务访问账号不匹配");
			result.put("status", "1");
		}else if("420".endsWith(mr.getResultCode())){
			result.put("msg", "请求参数不合法");
			result.put("status", "1");
		}else{
			result.put("msg", "访问三方异常");
			result.put("status", "1");
		}
		
		return result;
	}

	/**
	 * 
	 * 认证卡(无短信)和身份证 
	 * userName 用户姓名（最长16 位）
	 * idNumber 用户身份证号（最长 18 位）
	 * cardNo 银行卡号（数字字符，最长 19 位）
	 * phoneNo 手机号（数字字符，最长 11 位）
	 * @throws RemoteException 
	 * 
	 */
	public  Map<String, String> authCardWithoutOTP(String userName,String idNumber, String cardNo, String phoneNo) throws RemoteException {
		
		AuthCardWithoutOTPRequest acwr = new AuthCardWithoutOTPRequest();
		sessionId = UUID.randomUUID().toString();
		acwr.setUserName(userName);
		acwr.setIdNumber(idNumber);
		acwr.setCardNo(cardNo);
		acwr.setPhoneNo(phoneNo);
		acwr.setMerchantCode(merchantCode);
		acwr.setSessionId(sessionId);
		AuthCardWithoutOTPResponse ar = uidApiService.authCardWithoutOTP(acwr);
		Map<String, String> result = new HashMap<String, String>();
		log.info("第三方返回码："+ar.getResultCode()+",第三方返回信息："+ar.getMessage());
		
		if("100".endsWith(ar.getResultCode())){
			result.put("msg", "认证成功");
			result.put("status", "2");
		}else if("441".endsWith(ar.getResultCode())){
			//"银行卡信息认证失败"
			result.put("msg", ar.getMessage());
			result.put("status", "1");
		}else if("442".endsWith(ar.getResultCode())){
			//"银行卡信息认证请求异常"
			result.put("msg",ar.getMessage() );
			result.put("status", "1");
		}else if("443".endsWith(ar.getResultCode())){
			//"银行卡信息认证请求超时"
			result.put("msg",ar.getMessage() );
			result.put("status", "1");
		}else if("451".endsWith(ar.getResultCode())){
			//"银行卡信息认证校验失败"
			result.put("msg",ar.getMessage() );
			result.put("status", "1");
		}else if("431".endsWith(ar.getResultCode())){
			//"身份证信息不匹配"
			result.put("msg",ar.getMessage() );
			result.put("status", "1");
		}else if("432".endsWith(ar.getResultCode())){
			//"身份证信息匹配失败"
			result.put("msg",ar.getMessage() );
			result.put("status", "1");
		}else if("452".endsWith(ar.getResultCode())){
			//"银行卡信息认证校验异常"
			result.put("msg", ar.getMessage());
			result.put("status", "1");
		}else if("200".endsWith(ar.getResultCode())){
			//"无效的商户号"
			result.put("msg",ar.getMessage() );
			result.put("status", "1");
		}else if("204".endsWith(ar.getResultCode())){
			//"无效的 sessionId"
			result.put("msg",ar.getMessage());
			result.put("status", "1");
		}else if("303".endsWith(ar.getResultCode())){
			//"商户号与服务访问账号不匹配"
			result.put("msg", ar.getMessage());
			result.put("status", "1");
		}else if("420".endsWith(ar.getResultCode())){
			//"请求参数不合法"
			result.put("msg", ar.getMessage());
			result.put("status", "1");
		}else{
			result.put("msg", "访问三方认证异常");
			result.put("status", "1");
		}
		result.put("sessionId",sessionId);
		return result;
	}
	/**
	 * 对外接口
	 * 认证卡(无短信)和身份证 
	 * userName 用户姓名（最长16 位）
	 * idNumber 用户身份证号（最长 18 位）
	 * cardNo 银行卡号（数字字符，最长 19 位）
	 * phoneNo 手机号（数字字符，最长 11 位）
	 * @throws RemoteException 
	 * 
	 */
	public  Map<String, String> hrtAuthCardWithoutOTP(String sessionId,String userName,String idNumber, String cardNo, String phoneNo) throws RemoteException {
		
		AuthCardWithoutOTPRequest acwr = new AuthCardWithoutOTPRequest();
		acwr.setUserName(userName);
		acwr.setIdNumber(idNumber);
		acwr.setCardNo(cardNo);
		if(phoneNo==null||"".equals(phoneNo)){
			acwr.setPhoneNo(null);
		}else{
			acwr.setPhoneNo(phoneNo);
		}
		acwr.setMerchantCode(merchantCode);
		acwr.setSessionId(sessionId);
		AuthCardWithoutOTPResponse ar = uidApiService.authCardWithoutOTP(acwr);
		Map<String, String> result = new HashMap<String, String>();
		log.info("对外接口 -第三方返回码："+ar.getResultCode()+",第三方返回信息："+ar.getMessage());
		
		if("100".endsWith(ar.getResultCode())){
			result.put("msg", "认证成功");
			result.put("resultCode", "100");
			result.put("status", "2");
			result.put("sysseqnb",sessionId);
		}else if("441".endsWith(ar.getResultCode())||"442".endsWith(ar.getResultCode())||"443".endsWith(ar.getResultCode())){
			//"银行卡信息认证失败" "银行卡信息认证请求异常" "银行卡信息认证请求超时"
			result.put("msg", "银行卡信息认证失败");
			result.put("status", "1");
			result.put("resultCode", "441");
			result.put("sysseqnb",sessionId);
		}else if("451".endsWith(ar.getResultCode())||"452".endsWith(ar.getResultCode())){
			//"银行卡信息认证校验失败" "银行卡信息认证校验异常"
			result.put("msg","银行卡信息认证校验失败");
			result.put("status", "1");
			result.put("resultCode", "451");
			result.put("sysseqnb",sessionId);
		}else if("431".endsWith(ar.getResultCode())){
			//"身份证信息不匹配"
			result.put("msg",ar.getMessage() );
			result.put("status", "1");
			result.put("resultCode", "431");
			result.put("sysseqnb",sessionId);
		}else if("432".endsWith(ar.getResultCode())){
			//"身份证信息匹配失败"
			result.put("msg",ar.getMessage() );
			result.put("status", "1");
			result.put("resultCode", "432");
			result.put("sysseqnb",sessionId);
		}else if("200".endsWith(ar.getResultCode())||"204".endsWith(ar.getResultCode())||"303".endsWith(ar.getResultCode())||"420".endsWith(ar.getResultCode())){
			//"无效的商户号" "无效的 sessionId" "商户号与服务访问账号不匹配" "请求参数不合法"
			result.put("msg","认证失败");
			result.put("status", "1");
			result.put("resultCode", "200");
			result.put("sysseqnb",sessionId);
		}else{
			result.put("msg", "访问三方认证异常");
			result.put("status", "1");
			result.put("resultCode", "201");
			result.put("sysseqnb",sessionId);
		}
		result.put("sessionId",sessionId);
		return result;
	}

}
