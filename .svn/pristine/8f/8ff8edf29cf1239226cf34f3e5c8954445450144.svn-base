package com.hrt.biz.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import com.hrt.frame.service.sysadmin.IUserService;
import com.hrt.phone.service.GateCarService;

/**
 * 说明：对参数表的查询操作，可以通过类型（汉字，拼音）和key，查询参数值
 * 
 * @author tenglong
 * 
 */
public class ParamUtil {

	private Logger logger = Logger.getLogger(ParamUtil.class);

	private IUserService userService;
	
	private GateCarService carService;

	
	public GateCarService getCarService() {
		return carService;
	}

	public void setCarService(GateCarService carService) {
		this.carService = carService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	private static Map<String, Object> typeMap = new HashMap<String,Object>();
	
	public Map<String, Object> initParameter() {

		System.out.println("参数初始化加入缓存 ............");

		boolean mag_flag = userService.findIfAuto(3);
		
		Integer mer_flag = 5;
		Integer txn_flag = 6;
		Integer hrt_flag = 5;
		
		String token = null;
		
		try{
			mer_flag = userService.findIfAuth(4);
			txn_flag = userService.findIfAuth(5);
			hrt_flag = userService.findIfAuth(6);
		}catch(Exception e){
			logger.info("获取认证通道异常"+e);
		}
		
		try{
			token = carService.login();
		}catch(Exception e){
			logger.info("获取车教授登陆token异常"+e);
		}

		System.out.println("是否开启登陆短信验证码功能:"+mag_flag+"; 商户认证通道:"+mer_flag+"; 交易认证通道:"+txn_flag +"; 对外认证通道："+hrt_flag +" (5:民生;6：来宜); 车教授登陆token:"+token);
		
		System.out.println("参数初始化完成 ............");

		typeMap.put("mag_flag", mag_flag);

		typeMap.put("MER", mer_flag);
		
		typeMap.put("TXN", txn_flag);
		
		typeMap.put("HRT", hrt_flag);
		
		if(token!=null&&!"".equals(token)){
			typeMap.put("token", token);
		}
		
		//logger.info(typeMap.toString());

		return typeMap;

	}

	public Object getTypeMap(String mag_flag){
		return typeMap.get(mag_flag);
	}

}
