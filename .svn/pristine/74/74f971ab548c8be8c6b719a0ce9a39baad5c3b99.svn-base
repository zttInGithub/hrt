package com.hrt.phone.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.util.ParamUtil;
import com.hrt.biz.util.gateway.AESCryptPostUtil;
import com.hrt.biz.util.gateway.HttpUtils;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.phone.dao.GateViolationDao;
import com.hrt.phone.entity.model.GateCarModel;
import com.hrt.phone.entity.model.GateViolationModel;
import com.hrt.phone.entity.pagebean.GateCarBean;
import com.hrt.phone.entity.pagebean.GateViolationBean;
import com.hrt.phone.service.GateCarService;
import com.hrt.phone.service.GateViolationService;

import oracle.sql.DATE;

public class GateViolationServiceImpl implements GateViolationService{
	
	private GateViolationDao violationDao;
	private GateCarService gateCarService;
	//接口地址
	private String serviceHost;
	//版本号
	private String version;
	//密匙用户
	private String keyVersion;
	//密匙
	private String aesKey;
	
	private String webSiteId;
	
	private ParamUtil paramUtil;
	
	public void setParamUtil(ParamUtil paramUtil) {
		this.paramUtil = paramUtil;
	}
	public ParamUtil getParamUtil() {
		return paramUtil;
	}
	private static final Log log = LogFactory.getLog(GateViolationServiceImpl.class);
	
	public GateCarService getGateCarService() {
		return gateCarService;
	}
	public void setGateCarService(GateCarService gateCarService) {
		this.gateCarService = gateCarService;
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
	public String getWebSiteId() {
		return webSiteId;
	}
	public void setWebSiteId(String webSiteId) {
		this.webSiteId = webSiteId;
	}
	public void setViolationDao(GateViolationDao violationDao) {
		this.violationDao = violationDao;
	}
	public GateViolationDao getViolationDao() {
		return violationDao;
	}
	@Override
	public List<Map<String, Object>> queryViolations(String sql) {
		return null;
	}
	@Override
	public void save(GateViolationModel gateViolationModel) {
		violationDao.saveObject(gateViolationModel);
	}
	
	//
	public Integer saveGateCarIfFrist(GateViolationBean gateViolationBean,Integer leftTimes){
		GateCarModel gateCarModel;
    	//将车辆信息存放到数据库
		GateCarBean gateCarBean = new GateCarBean();
		gateCarBean.setMid(gateViolationBean.getMid());
		gateCarBean.setCarNumber(gateViolationBean.getCarNumber());
		List<GateCarModel>list = gateCarService.queryCarInfo1(gateCarBean);
		if (list.size()>0) {
			gateCarModel = list.get(0);
			SimpleDateFormat smf = new SimpleDateFormat("yyyyMMdd");
			String date1 = smf.format(new Date());
			String date2 = "";
			gateCarModel.setMainTainType("M");
			if(gateCarModel.getQueryTime()!=null){
				date2 = smf.format(gateCarModel.getQueryTime());
			}
			if(gateCarModel.getEngineNumber()==null)gateCarModel.setEngineNumber("");
			if(gateCarModel.getCarCode()==null)gateCarModel.setCarCode("");
			if (date1.equals(date2)&&gateViolationBean.getCarCode().equals(gateCarModel.getCarCode())&&gateViolationBean.getCarType().equals(gateCarModel.getCarType())
					&&gateViolationBean.getEngineNumber().equals(gateCarModel.getEngineNumber())) {
				return null;
			}
		}else {
			if(leftTimes<=0)return 0;
			gateCarModel = new GateCarModel();
			gateCarModel.setMainTainType("A");
			gateCarModel.setMainTainDate(new Date());
		}
    	gateCarModel.setCarCode(gateViolationBean.getCarCode());
        gateCarModel.setCarNumber(gateViolationBean.getCarNumber());
        gateCarModel.setCarType(gateViolationBean.getCarType());
        gateCarModel.setEngineNumber(gateViolationBean.getEngineNumber());
        gateCarModel.setMid(gateViolationBean.getMid());
        gateCarModel.setQueryTime(new Date());
        gateCarService.saveGateCarInfo(gateCarModel);
		return gateCarModel.getCarId();
	}
	
	@Override
	public List<GateViolationModel> queryViolationInfobyNumber(GateViolationBean gateViolationBean) throws ParseException {
		String sql = "select t2.* from bill_gatecarinfo t1,bill_gateviolation t2 where t1.carid = t2.carid  and t1.carnumber=:CarNumber and" +
				" t1.carType=:carType and to_char(t2.QUERYTIME,'yyyyMMdd')=to_char(sysdate,'yyyyMMdd') and t1.mid=:mid and t2.mainTainType!='D' ";
		HashMap map=new HashMap();
		map.put("CarNumber", gateViolationBean.getCarNumber());
		map.put("carType", gateViolationBean.getCarType());
		map.put("mid", gateViolationBean.getMid());
		if(gateViolationBean.getEngineNumber()!=null&&!"".equals(gateViolationBean.getEngineNumber())){
			sql+=" and t1.engineNumber=:engineNumber ";
			map.put("engineNumber", gateViolationBean.getEngineNumber());
		}
		if(gateViolationBean.getCarCode()!=null&&!"".equals(gateViolationBean.getCarCode())){
			sql+=" and t1.carCode=:carCode ";
			map.put("carCode", gateViolationBean.getCarCode());
		}
		sql+= " order by t2.time desc ";
		List<GateViolationModel> list = violationDao.queryObjectsBySqlList(sql, map, GateViolationModel.class);
		return list;
	}
	
	public JsonBean saveViolationInfobySANFANG(GateViolationBean gateViolationBean){
		List list = new ArrayList();
		String msg =null;
		JSONObject jsonObject = new JSONObject();
        String carNumber = gateViolationBean.getCarNumber();
        String carCode = gateViolationBean.getCarCode();
        String carType = gateViolationBean.getCarType();
        String engineNumber = gateViolationBean.getEngineNumber();
        String mid = gateViolationBean.getMid();
        jsonObject.put("token", paramUtil.getTypeMap("token"));
        jsonObject.put("carNumber", carNumber);
        jsonObject.put("carCode", carCode );
        jsonObject.put("engineNumber", engineNumber);
        jsonObject.put("carType", carType);
		JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("req_data", jsonObject.toString());
        System.out.println("未加密请求数据:"+jsonObject2.toString());
        //业务参数
        String aesStr = AESCryptPostUtil.encryptByPassword(aesKey, jsonObject2.toString());
        //请求参数
        String param = "{\"content\":\"" + aesStr + "\"}";
        System.out.println("请求数据：" + param);
        //头文件
        Map<String, String> headParams = new HashMap();
        headParams.put("apiName", "Violation.ViolationPrice");
        headParams.put("keyVersion", keyVersion);
        headParams.put("version", version);
        String resultStr = "";
        try {
            resultStr =HttpUtils.sendPostMessage(serviceHost, param,
                    "utf-8", "POST", headParams);
            resultStr = AESCryptPostUtil.decryptByPassword(aesKey, resultStr);
//        	resultStr="{\"code\":1,\"msg\":\"成功\",\"data\":{\"id\":\"66bdbfb3-3d62-448a-b57f-8c1dac736a6c\",\"PrivateFlag\":1,\"ISChoose\":1,\"order\":[{  \"usPriceFenChengQuery\": [    {      \"WebSiteId\": \"109024\",      \"FCID\": null,      \"ORDERID\": \"\",      \"FCUSERID\": \"109024201609261525400000000002\",      \"FC\": 0.0,      \"GDLR\": 0.0,      \"FCTYPE\": \"滞纳金\",      \"CalculationExpression\": \"\",      \"ProfitType\": 19    },    {      \"WebSiteId\": \"109024\",      \"FCID\": null,      \"ORDERID\": \"\",      \"FCUSERID\": \"109024201609261525400000000002\",      \"FC\": 0.0,      \"GDLR\": 0.0,      \"FCTYPE\": \"下级分成\",      \"CalculationExpression\": \"\",      \"ProfitType\": 1    },    {      \"WebSiteId\": \"109024\",      \"FCID\": null,      \"ORDERID\": \"\",      \"FCUSERID\": \"109024201609261525400000000002\",      \"FC\": 0.0,      \"GDLR\": 0.0,      \"FCTYPE\": \"下级分成\",      \"CalculationExpression\": \"\",      \"ProfitType\": 1    },    {      \"WebSiteId\": \"109024\",      \"FCID\": null,      \"ORDERID\": \"\",      \"FCUSERID\": \"109024201609261525400000000002\",      \"FC\": 0.0,      \"GDLR\": 0.0,      \"FCTYPE\": \"网站利润\",      \"CalculationExpression\": \"\",      \"ProfitType\": 2    },    {      \"WebSiteId\": \"109024\",      \"FCID\": null,      \"ORDERID\": \"\",      \"FCUSERID\": \"109024201609261525400000000002\",      \"FC\": 0.0,      \"GDLR\": 0.0,      \"FCTYPE\": \"手价分成\",      \"CalculationExpression\": \"\",      \"ProfitType\": 18    }  ],  \"CarNumber\": \"粤A136LN\",  \"Time\": \"2017-05-02 10:29:00\",  \"Location\": \"花地大道中_花地大道芳村客运站\",  \"Reason\": \"机动车违反禁令标志指示的\",  \"count\": \"200\",  \"revicecount\": null,  \"reviceid\": null,  \"status\": \"0\",  \"department\": \"芳村大队一中队\",  \"Degree\": \"3\",  \"Code\": \"1344\",  \"Archive\": \"4401117900535626\",  \"Telephone\": \"\",  \"Excutelocation\": \"\",  \"Excutedepartment\": \"\",  \"Latefine\": \"0\",  \"Punishmentaccording\": \"\",  \"Illegalentry\": \"\",  \"Locationid\": \"4401\",  \"LocationName\": \"广东广州\",  \"DataSourceID\": \"9992\",  \"Poundage\": \"6\",  \"CanProcess\": \"1\",  \"UniqueCode\": \"095244133246c9658db980dacb76c554\",  \"CanprocessMsg\": \"成功报价！\",  \"Other\": \"3\",  \"BLSM\": \"违章所在地区：全国，类型：扣分单；车牌：粤A136LN[小型汽车]；\",  \"WFLX\": \"扣分单\",  \"Poundage1\": \"6\",  \"Poundage2\": \"6\",  \"FSBL\": \"0\",  \"FSBLJE\": \"11.00\",  \"PriceSource\": \"违章地\",  \"CanProcessUsOrder\": \"1\",  \"UsPriceFirst\": \"420\",  \"Difference\": \"\",  \"Difference1\": \"0\",  \"Difference2\": \"0\"}]}}";
            System.out.println("返回信息"+resultStr);
            JSONObject returnJsonObject = JSONObject.parseObject(resultStr);
            Integer code = returnJsonObject.getInteger("code");
            msg = returnJsonObject.getString("msg");
            //token失效后，重新登录
            if(code == 0&&"token已失效".equals(msg)){
            	paramUtil.initParameter();
            }else{
            	List orders = (List) returnJsonObject.getJSONObject("data").getJSONArray("order");
                String id=returnJsonObject.getJSONObject("data").getString("id");
                Integer ISChoose=returnJsonObject.getJSONObject("data").getInteger("ISChoose");
                if (orders != null && orders.size()>0) {
                	for (int i =0;i<orders.size();i++) {
                		JSONObject json = (JSONObject)orders.get(i);
                		json.remove("usPriceFenChengQuery");
                		String UniqueCode=json.getString("UniqueCode");
                		String hql="from GateViolationModel where uniqueCode =:uniqueCode and carId=:carId";
                		GateViolationModel gateViolationModel = null;
                		HashMap map=new HashMap();
                		map.put("uniqueCode", UniqueCode);
                		map.put("carId", gateViolationBean.getCarId());
                		List<GateViolationModel>  list2=violationDao.queryObjectsByHqlList(hql, map);
                		if(list2.size()>0){
                			gateViolationModel=list2.get(0);
                		}else{
                			 gateViolationModel = new GateViolationModel();
                		}
        				gateViolationModel.setUniqueCode(json.getString("UniqueCode"));
        				gateViolationModel.setArchive(json.getString("Archive"));
        				gateViolationModel.setMainTainType("A");
        				gateViolationModel.setTime(json.getDate("Time"));
        				gateViolationModel.setLocation(json.getString("Location"));
        				gateViolationModel.setReason(json.getString("Reason"));
        				gateViolationModel.setCount(json.getString("count"));
        				gateViolationModel.setStatus(json.getString("status"));
        				gateViolationModel.setDepartment(json.getString("department"));
        				gateViolationModel.setDeGree(json.getString("Degree"));
        				gateViolationModel.setExcuteDepartment(json.getString("Excutedepartment"));
        				gateViolationModel.setLateFine(json.getString("Latefine"));
        				gateViolationModel.setPunishmentAccording(json.getString("Punishmentaccording"));
        				gateViolationModel.setLocation(json.getString("Locationid"));
        				gateViolationModel.setLocationName(json.getString("LocationName"));
        				gateViolationModel.setPoundage(json.getString("Poundage"));
        				gateViolationModel.setCanProcess(json.getString("CanProcess"));
        				gateViolationModel.setUniqueCode(json.getString("UniqueCode"));
        				gateViolationModel.setPoundage1(json.getString("Poundage1"));
        				gateViolationModel.setPoundage2(json.getString("Poundage2"));
        				gateViolationModel.setFsblje(json.getString("FSBLJE"));
        				gateViolationModel.setPriceSource(json.getString("PriceSource"));
        				gateViolationModel.setCanProcessUsOrder(json.getString("CanProcessUsOrder"));
        				gateViolationModel.setUsPriceFirst(json.getString("UsPriceFirst"));
        				gateViolationModel.setDifference(json.getString("Difference"));
        				gateViolationModel.setWflx(json.getString("WFLX"));
        				gateViolationModel.setQueryTime(new Date());
        				gateViolationModel.setCarId(gateViolationBean.getCarId());
        				gateViolationModel.setIsChoose(ISChoose);
        				gateViolationModel.setPriceId(id);
        				gateViolationModel.setCanprocessMsg(json.getString("CanprocessMsg"));
        				violationDao.saveOrUpdateObject(gateViolationModel);	
        				double d1 = Double.parseDouble(gateViolationModel.getCount()==null?"0":gateViolationModel.getCount());
        				double d2 = Double.parseDouble(gateViolationModel.getLateFine()==null?"0":gateViolationModel.getLateFine());
        				double d3 = 0;
        				if (json.getString("Degree")==null||"".equals(json.getString("Degree"))||"0".equals(json.getString("Degree"))) {
    						d3 = Double.parseDouble(gateViolationModel.getPoundage())+10;
    					}else {
    						d3 = Double.parseDouble(gateViolationModel.getPoundage());
    						if (d3*0.4<10) {
    							d3+=10.0;
    						}else{
    							d3 = d3*1.4;
    						}
    					}
        				DecimalFormat decimalFormat = new DecimalFormat("0");
        				gateViolationModel.setReceivePoundage(decimalFormat.format(d3));
        				gateViolationModel.setTotal(Double.parseDouble(decimalFormat.format(d3+d2+d1)));
        				list.add(gateViolationModel);
                	}
    			}
            }
        } catch (Exception e) {
            log.info("违章查询、办理报价1(异常)"+e);
        }
        JsonBean bean = new JsonBean();
        bean.setList(list);
        bean.setMsg(msg);
		return bean;
	}
	
	@Override
	public Integer updateQueryTimes(String mid) {
		String sql = "update bill_gatecarlefttimes set lefttimes = lefttimes -1 where mid = '"+mid+"' and lefttimes>0 and querytime=to_char(sysdate,'yyyymmdd')";
		return violationDao.executeSqlUpdate(sql, null);
	}
}
