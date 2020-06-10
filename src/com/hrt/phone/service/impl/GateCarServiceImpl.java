package com.hrt.phone.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.util.gateway.AESCryptPostUtil;
import com.hrt.biz.util.gateway.HttpUtils;
import com.hrt.phone.dao.GateCarDao;
import com.hrt.phone.entity.model.GateCarModel;
import com.hrt.phone.entity.pagebean.GateCarBean;
import com.hrt.phone.service.GateCarService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GateCarServiceImpl implements GateCarService {

	private static final Log log = LogFactory.getLog(GateCarServiceImpl.class);
	//接口地址
	private String serviceHost;
	//版本号
	private String version;
	//密匙用户
	private String keyVersion;
	private String loginName;
	private String userPWD;
	//密匙
	private String aesKey;
	private String webSiteId;
	private GateCarDao gateCarDao;
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserPWD() {
		return userPWD;
	}
	public void setUserPWD(String userPWD) {
		this.userPWD = userPWD;
	}
	public GateCarDao getGateCarDao() {
		return gateCarDao;
	}
	public void setGateCarDao(GateCarDao gateWayDao) {
		this.gateCarDao = gateWayDao;
	}
	
	public String getWebSiteId() {
		return webSiteId;
	}
	public void setWebSiteId(String webSiteId) {
		this.webSiteId = webSiteId;
	}
	public String getServiceHost() {
		return serviceHost;
	}
	public void setServiceHost(String serviceHost) {
		this.serviceHost = serviceHost;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getKeyVersion() {
		return keyVersion;
	}
	public void setKeyVersion(String keyVersion) {
		this.keyVersion = keyVersion;
	}
	public String getAesKey() {
		return aesKey;
	}
	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}
	
	@Override
	public void saveGateCarInfo(GateCarModel gateCarModel) {
//		gateCarDao.saveObject(gateCarModel);
		gateCarDao.saveOrUpdateObject(gateCarModel);
	}
	@Override
	public List<GateCarBean> queryCarInfo(GateCarBean gateCarBean) {
		String hql = "from GateCarModel where mid='"+gateCarBean.getMid()+"' and mainTainType!='D' ";
		if(gateCarBean.getCarNumber()!=null&&!"".equals(gateCarBean.getCarNumber())){
			hql+=" and carNumber='"+gateCarBean.getCarNumber()+"'";
		}
		hql+=" order by mainTainDate desc ";
		List<GateCarModel>list = gateCarDao.queryObjectsByHqlList(hql);
		List<GateCarBean> list2 = new ArrayList<GateCarBean>();
		long now = new Date().getTime();
		for (GateCarModel gateCarModel : list) {
			GateCarBean gateCarBean2 = new GateCarBean();
			try {
				gateCarBean2.setCarCode(gateCarModel.getCarCode());
				gateCarBean2.setCarId(gateCarModel.getCarId());
				gateCarBean2.setCarNumber(gateCarModel.getCarNumber());
				gateCarBean2.setCarType(gateCarModel.getCarType());
				gateCarBean2.setEngineNumber(gateCarModel.getEngineNumber());
				gateCarBean2.setMainTainDate(gateCarModel.getMainTainDate());
				gateCarBean2.setMainTainType(gateCarModel.getMainTainType());
				gateCarBean2.setMainTainUid(gateCarModel.getMainTainUid());
				gateCarBean2.setQueryTime(gateCarModel.getQueryTime());
				gateCarBean2.setMid(gateCarModel.getMid());
				gateCarBean2.setDriliBackPic(gateCarModel.getDriliBackPic()==null?"":"phone/gateCar_showImage.action?image="+gateCarModel.getDriliBackPic()+"&meanless="+now);
				gateCarBean2.setDriliPic1(gateCarModel.getDriliPic1()==null?"":"phone/gateCar_showImage.action?image="+gateCarModel.getDriliPic1()+"&meanless="+now);
				gateCarBean2.setDriliPic2(gateCarModel.getDriliPic2()==null?"":"phone/gateCar_showImage.action?image="+gateCarModel.getDriliPic2()+"&meanless="+now);
				gateCarBean2.setIdentityCardPic(gateCarModel.getIdentityCardPic()==null?"":"phone/gateCar_showImage.action?image="+gateCarModel.getIdentityCardPic()+"&meanless="+now);
				gateCarBean2.setInsurancePic(gateCarModel.getInsurancePic()==null?"":"phone/gateCar_showImage.action?image="+gateCarModel.getInsurancePic()+"&meanless="+now);
				list2.add(gateCarBean2);
			} catch (Exception e) {
				log.error("queryCarInfo 异常:"+e);
			}
			
		}
		return list2;
	}
	
	//获取记录条数
	@Override
	public Integer queryGateCarInfoCount(String mid) {
		Map<String, Object>params = new HashMap<String, Object>();
		String sql = " select count(1) as count from bill_gatecarinfo where mid = :mid";
		params.put("mid", mid);
		Integer count =0;
		count = gateCarDao.querysqlCounts2(sql, params);
		return count;
	}
	@Override
	public List<Map<String, Object>> querySupportCities() {
		String sql = "select distinct provincecitycode,AGIN from bill_gatecity";
		List<Map<String, Object>> list = gateCarDao.queryObjectsBySqlObject(sql);
		sql = "select * from bill_gatecity order by provincecitycode,citycode";
		List<Map<String, Object>> cityList = gateCarDao.queryObjectsBySqlList(sql);
		List<Map< String, Object>> returnList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object>map = list.get(i);
			List<Map<String, Object>>list2 = new ArrayList<Map<String,Object>>();
			for (int j = 0; j < cityList.size(); j++) {
				if (map.get("PROVINCECITYCODE").equals(cityList.get(j).get("PROVINCECITYCODE"))) {
					Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("cityId", cityList.get(j).get("CITYID"));
					map2.put("cityCode", cityList.get(j).get("CITYCODE"));
					map2.put("cityName", cityList.get(j).get("CITYNAME"));
					map2.put("carCodeLen", cityList.get(j).get("CARCODELEN"));
					map2.put("carEnginLen", cityList.get(j).get("CARENGINELEN"));
					list2.add(map2);
				}
			}
			map.put("detail", list2);
			returnList.add(map);
		}
		return returnList;
	}
	@Override
	public String login() {
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("loginName", loginName);
	    jsonObject.put("userPWD", userPWD);
	    jsonObject.put("webSiteId", webSiteId);
	    JSONObject jsonObject2 = new JSONObject();
	    jsonObject2.put("req_data", jsonObject);
	    //业务参数
	    String aesStr = AESCryptPostUtil.encryptByPassword(aesKey, jsonObject2.toString());
	    //请求参数
	    String param = "{\"content\":\"" + aesStr + "\"}";
	    System.out.println("请求数据：" + param);
	    //头文件
	    Map<String, String> headParams = new HashMap();
	    headParams.put("apiName", "User.UserLogin");
	    headParams.put("keyVersion", keyVersion);
	    headParams.put("version", version);
	    
	    String resultStr = "";
	    String token = null;
	    try {
	        resultStr = HttpUtils.sendPostMessage(serviceHost, param,
	                "utf-8", "POST", headParams);
	        resultStr = AESCryptPostUtil.decryptByPassword(aesKey, resultStr);
	        JSONObject returnjJsonObject = JSONObject.parseObject(resultStr);
	        if (returnjJsonObject.getInteger("code")==1) {
	        	//token
	        	token = returnjJsonObject.getJSONObject("data").getString("token");
			}
	    } catch (Exception e) {
	    	System.out.println("车教授登陆异常"+e);
	        e.printStackTrace();
	    }
	   return token;
	}
//	@Override
//	public List<Map<String, Object>> getCarcodeAndEngineLength(GateCarBean gateCarBean) {
//		String sql = "select carcodelen,carenginelen  from bill_gatecity where 1=1 ";
//		if (!gateCarBean.getCityCode().isEmpty()) {
//			sql +=" and citycode ='"+gateCarBean.getCityCode()+"'";
//		}
//		if (gateCarBean.getProvinceCityCode()!=null&&!"".equals(gateCarBean.getProvinceCityCode())) {
//			sql +=" and provincecitycode = '"+gateCarBean.getProvinceCityCode()+"'";
//		}
//		if (gateCarBean.getCityId()!=null&&!"".equals(gateCarBean.getCityId())) {
//			sql +="";
//		}
//		List<Map<String, Object>>list = gateCarDao.queryObjectsBySqlList(sql);
//		return list;
//	}
	@Override
	public void updateGateCarInfo(GateCarBean gateCarBean) throws FileNotFoundException {
		Map<String, Object>param = new HashMap<String, Object>();
		String hqlString = "from GateCarModel where carId = :carId";
		param.put("carId", gateCarBean.getCarId());
		List<GateCarModel>list = gateCarDao.queryObjectsByHqlList(hqlString, param);
		GateCarModel gateCarModel = null;
	    gateCarModel = list.get(0);
	    if (gateCarBean.getCarCode() != null ) {
	    	gateCarModel.setCarCode(gateCarBean.getCarCode());
		}
	    if (gateCarBean.getCarType() != null ) {
	    	gateCarModel.setCarType(gateCarBean.getCarType());
		}
	    if (gateCarBean.getEngineNumber()!= null ) {
	    	gateCarModel.setEngineNumber(gateCarBean.getEngineNumber());
		}
	    gateCarModel.setQueryTime(null);
	    gateCarModel.setMainTainDate(new Date());
	    gateCarModel.setMainTainType("M");
		String sql = "select upload_path as path from sys_param  where title = 'GateCarInfo'";
		List<Map<String, Object>>list2 = gateCarDao.queryObjectsBySqlObject(sql);
		String imagePathFront = list2.get(0).get("PATH").toString();
		String imageNameFront = gateCarBean.getCarId()+".";
		if (gateCarBean.getDriverLienceFrontFile()!=null) {
			String imageName = imageNameFront+"DriverLienceFrontPic"+ ".png";
			String imagePath = uploadFile(gateCarBean.getDriverLienceFrontFile(), imageName, imagePathFront);
			gateCarModel.setDriliPic1(imagePath);
		}
		if (gateCarBean.getDriverLienceBackFile() != null) {
			String imageName = imageNameFront+"DriliBackPic"+ ".png";
			String imagePath = uploadFile(gateCarBean.getDriverLienceBackFile(), imageName, imagePathFront);
			gateCarModel.setDriliBackPic(imagePath);
		}
		if (gateCarBean.getDrivingLienceFile()!=null) {
			String imageName = imageNameFront+"DrivingLiencePic"+".png";
			String imagePath = uploadFile(gateCarBean.getDrivingLienceFile(),imageName, imagePathFront);
			gateCarModel.setDriliPic2(imagePath);
		}
		if (gateCarBean.getIdentityCardFile()!=null) {
			String imageName = imageNameFront+"IdentityCardPic"+ ".png";
			String imagePath = uploadFile(gateCarBean.getIdentityCardFile(),imageName, imagePathFront);
			gateCarModel.setIdentityCardPic(imagePath);
		}
		if (gateCarBean.getInsurancePicFile()!=null) {
			String imageName = imageNameFront+"InsurancePic"+".png";
			String imagePath = uploadFile(gateCarBean.getInsurancePicFile(),imageName, imagePathFront);
			gateCarModel.setInsurancePic(imagePath);;
		}
		gateCarDao.updateObject(gateCarModel);
 	}
	private String uploadFile(File upload, String fName, String uploadRealPath) {
		String fPath="";
		try {
			String imageDay = getImageDay();
			String realPath = uploadRealPath + File.separator + imageDay;
			File dir = new File(realPath);
			dir.mkdirs();
			fPath = realPath + File.separator + fName;
			File destFile = new File(fPath);
			FileInputStream fis = new FileInputStream(upload);
			FileOutputStream fos = new FileOutputStream(destFile);
			byte [] buffer = new byte[1024];
			int len = 0;
			while((len = fis.read(buffer))>0){
				fos.write(buffer,0,len);
			} 
			fos.close();
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
		return fPath;
	}
	public String getImageName(String carNumber,String sub) {
		
		String imageName = carNumber;
		for (int i = 0; i < 4; i++) {
			imageName += (int)Math.random()*10;
		}
		return imageName+sub;
	}
	public String getImageDay() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String name = sdf.format(date);
		return name;
	}
	@Override
	public Integer queryLeftTimesByMid(String mid) {
		
		Map<String, Object>params = new HashMap<String, Object>();
		String sql = "select LEFTTIMES from bill_gatecarlefttimes where mid= :mid and querytime=to_char(sysdate,'yyyymmdd')";
		params.put("mid", mid);
		List<Map<String, Object>>list = gateCarDao.queryObjectsBySqlListMap2(sql, params);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (list.size()==0) {
			//用户从未查询过
			sql = "insert into bill_gatecarlefttimes ( mid, querytime, lefttimes, maintaindate,  maintaintype)"
					+ " values( '"+mid+"', to_char(sysdate,'yyyymmdd'),3,sysdate, 'A')";
			gateCarDao.executeUpdate(sql);
			return 3;
		}else {
			//当天已查询过
			Map<String, Object> map = list.get(0);
		    return Integer.parseInt(map.get("LEFTTIMES").toString());
		}
	}
	@Override
	public void deleteCarInfo(Integer carId) {
		String hql = "from GateCarModel where carId = :carId ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("carId", carId);
		List<GateCarModel>list = gateCarDao.queryObjectsByHqlList(hql, map);
		GateCarModel gateCarModel = list.get(0);
		gateCarModel.setMainTainType("D");
		gateCarDao.updateObject(gateCarModel);
	}
	@Override
	public void updateGateCarInfo(GateCarModel gateCarMode) {
		gateCarDao.updateObject(gateCarMode);
	}
	@Override
	public String queryIfUploadPic(String carNumber,String mid) {
		String sql = "select count(1)  from bill_gateCarinfo where carnumber = :carnumber and mid = :mid and DRILIPIC1 is not null and  DRILIPIC2 is not null and  IDENTITYCARDPIC is not null and  INSURANCEPIC is not null and  DRILIBACKPIC is not null ";
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("carnumber", carNumber);
		map.put("mid", mid);
		Integer i = gateCarDao.querysqlCounts2(sql, map);
		return i.toString();
	}
	@Override
	public List<GateCarModel> queryCarInfo1(GateCarBean gateCarBean) {
		String hql = "from GateCarModel where mid='"+gateCarBean.getMid()+"' ";
		if(gateCarBean.getCarNumber()!=null&&!"".equals(gateCarBean.getCarNumber())){
			hql+=" and carNumber='"+gateCarBean.getCarNumber()+"'";
		}
		List<GateCarModel>list = gateCarDao.queryObjectsByHqlList(hql);
		return list;
	}
}
