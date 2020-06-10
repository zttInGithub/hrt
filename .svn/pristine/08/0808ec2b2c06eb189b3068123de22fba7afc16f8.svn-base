package com.hrt.phone.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.check.dao.CheckRefundDao;
import com.hrt.biz.check.entity.model.CheckReFundModel;
import com.hrt.biz.util.Base64;
import com.hrt.biz.util.ParamUtil;
import com.hrt.biz.util.gateway.AESCryptPostUtil;
import com.hrt.biz.util.gateway.HttpUtils;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.phone.dao.GateCarOrderDao;
import com.hrt.phone.dao.GateViolationDao;
import com.hrt.phone.entity.model.GateOrderModel;
import com.hrt.phone.entity.model.GateViolationModel;
import com.hrt.phone.entity.pagebean.GateOrderBean;
import com.hrt.phone.service.GateCarOrderService;
import com.hrt.util.JxlOutExcelUtil;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class GateCarOrderServiceImpl implements GateCarOrderService{
	
	private GateCarOrderDao gateCarOrderDao;
	private GateViolationDao violationDao;
	private ParamUtil paramUtil;
	private static final Log log = LogFactory.getLog(GateCarOrderServiceImpl.class);
	//接口地址
	private String serviceHost;
	//版本号
	private String version;
	//密匙用户
	private String keyVersion;
	//密匙
	private String aesKey;
	
	private String webSiteId;
	

	private IUnitDao unitDao;
	
	private IMerchantInfoService merchantInfoService; 
	 
	private CheckRefundDao checkRefundDao;
	
	public CheckRefundDao getCheckRefundDao() {
		return checkRefundDao;
	}
	public void setCheckRefundDao(CheckRefundDao checkRefundDao) {
		this.checkRefundDao = checkRefundDao;
	}
	public IUnitDao getUnitDao() {
		return unitDao;
	}
	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}
	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}
	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}
	
	public GateViolationDao getViolationDao() {
		return violationDao;
	}
	public void setViolationDao(GateViolationDao violationDao) {
		this.violationDao = violationDao;
	}
	public ParamUtil getParamUtil() {
		return paramUtil;
	}
	public void setParamUtil(ParamUtil paramUtil) {
		this.paramUtil = paramUtil;
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
	public GateCarOrderDao getGateCarOrderDao() {
		return gateCarOrderDao;
	}
	public void setGateCarOrderDao(GateCarOrderDao gateCarOrderDao) {
		this.gateCarOrderDao = gateCarOrderDao;
	}
	@Override
	public List<Map<String, String>> queryOrdersDetailBySql(GateOrderBean gateOrderBean) {
		String sql = "selelct * from bill_gateviolation where mid = '"+gateOrderBean.getMid()+"'";
		return gateCarOrderDao.queryObjectsBySqlList(sql, null, gateOrderBean.getPage(), gateOrderBean.getRows());
	}
	
	/**
	 * app下单
	 */
	@Override
	public String saveAppOrdersDetail(GateOrderBean gateOrderBean) {
		Date date = new Date();
		//和融通订单
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
		StringBuffer hrtOrderId = new StringBuffer(sdf.format(date));
		while (hrtOrderId.length() < 16) {
			String s = Double.toString(Math.random()*10);
			if (s.length() > 3) {
				hrtOrderId.append(s.substring(0, 1));
			}
		}
		//获取uniqueCode
		String [] uniqueCodes = gateOrderBean.getUniqueCode().split(",");
		String [] totalFees = gateOrderBean.getTotalFee().split(",");
		for(int i = 0;i<uniqueCodes.length;i++){
			GateOrderModel model = new GateOrderModel();
			model.setMid(gateOrderBean.getMid());          		 //商户主键
			model.setPriceId(gateOrderBean.getPriceId());  		 //违章办理报价接口获得的id
			model.setUniqueCode(uniqueCodes[i]);     			 //唯一code
			model.setIsQuick(gateOrderBean.getIsQuick());        //是否快速办理 0：否  1：是
			model.setIsUsPrice(gateOrderBean.getIsUsPrice());    //是否本人下单 0：否  1：是
			model.setPrivateFlag(gateOrderBean.getPrivateFlag());//是否是私家车 0：否  1：是
			model.setTotalFee(totalFees[i]);				 	 //下单金额
			model.setState("0");
			model.setPassMemo("待支付");
			model.setPlaceOrderTime(date);
			model.setMainTainDate(date);
			model.setMainTainType("A");
			model.setHrtOrderId(hrtOrderId.toString());
			gateCarOrderDao.saveObject(model);
		}
        return hrtOrderId.toString();
	}
	/**
	 * app支付通知&三方下单
	 */
	@Override
	public Map<String, String> saveOrdersDetail(GateOrderBean gateOrderBean) {
		
		Map<String, String> result = new HashMap<String, String>();
		String hrtOrderId = gateOrderBean.getHrtOrderId();
		String isQuick = gateOrderBean.getIsQuick();
		String payOrderId = gateOrderBean.getPayOrderId();
		String uniqueCodes = gateOrderBean.getUniqueCode().replaceAll(",", "','");
		String state = gateOrderBean.getState();
		String mid = gateOrderBean.getMid();
		Date date = new Date();
		
		//更新已支付//通过和融通订单号和mid获取订单
		Map<String, Object> param1 = new HashMap<String, Object>();
		param1.put("hrtOrderId", hrtOrderId);
		param1.put("mid", mid);
		param1.put("uniqueCodes", uniqueCodes);
		List<GateOrderModel> mList = gateCarOrderDao.queryObjectsBySqlList("select * from bill_gateorder where hrtOrderId='"+hrtOrderId+"' and mid='"+mid+"' and uniqueCode in ('"+uniqueCodes+"')", null,GateOrderModel.class);
//		List<GateOrderModel> mList = gateCarOrderDao.queryObjectsBySqlList("select * from bill_gateorder where hrtOrderId=:hrtOrderId and mid=:mid and uniqueCode in (:uniqueCodes)", param1,GateOrderModel.class);
		if(mList==null||mList.size()==0){
			result.put("msg", "未查询到订单!");
			return result;
		}
		String priceId = mList.get(0).getPriceId();
		//支付成功,三方下单
		if("1".equals(state)){
			try {
				JSONObject jsonObject = new JSONObject();
		        jsonObject.put("id", priceId);
		        jsonObject.put("token", paramUtil.getTypeMap("token"));//"F283921F9D81483EAB2BAD2E5519F1DD"
		        jsonObject.put("uniqueCode", gateOrderBean.getUniqueCode());
		        jsonObject.put("isQuick", isQuick);
		        JSONObject jsonObject2 = new JSONObject();
		        jsonObject2.put("req_data", jsonObject.toString());
		        System.out.println("三方下单未加密请求数据:"+jsonObject2.toString());
		        //业务参数
		        String aesStr = AESCryptPostUtil.encryptByPassword(aesKey, jsonObject2.toString());
		        //请求参数
		        String param = "{\"content\":\"" + aesStr + "\"}";
		        System.out.println("三方下单请求数据：" + param);
		        //头文件
		        Map<String, String> headParams = new HashMap();
		        headParams.put("apiName", "Order.OrderSubmitAndPay");
		        headParams.put("keyVersion", keyVersion);
		        headParams.put("version", version);
		        String resultStr = "";
	            resultStr =HttpUtils.sendPostMessage(serviceHost, param,
	                    "utf-8", "POST", headParams);
	            resultStr = AESCryptPostUtil.decryptByPassword(aesKey, resultStr);
	            System.out.println("三方下单返回信息"+resultStr);
	            JSONObject returnJsonObject = JSONObject.parseObject(resultStr);
	            //状态
	            String code = returnJsonObject.getString("code");
	            //msg消息描述
	            String msg = returnJsonObject.getString("msg");
	            //batchId订单批次ID
	            String batchId = returnJsonObject.getJSONObject("data").getString("batchId");
	            //privateFlag
	            Integer privateFlag = returnJsonObject.getJSONObject("data").getInteger("privateFlag");
	            //uniqueCode违章唯一码//orderId订单ID
	            List jsonList = (List) returnJsonObject.getJSONObject("data").get("orderIds");
	            Map<String, String> jsonMap = new HashMap<String, String>();
	            if("1".equals(code)){
	            	//已下单成功
	            	code = "9";
	            }else{
	            	code = "1";
	            }
	            if(jsonList==null){
	            	log.info("app支付成功，三方下单失败："+gateOrderBean.getHrtOrderId());
	            	result.put("msg", "申请失败!订单号:"+gateOrderBean.getHrtOrderId());
	            }else{
	            	for(int i=0;i<jsonList.size();i++){
	            		JSONObject json=JSONObject.parseObject(jsonList.get(i).toString());
	            		String orderId = json.getString("orderId")+"";
	            		String uniqueCode = json.getString("uniqueCode")+"";
	            		jsonMap.put(uniqueCode, orderId);
	            	}
	            	result.put("msg", "申请成功,订单号:"+gateOrderBean.getHrtOrderId());
	            }
	            StringBuffer orderIds = new StringBuffer("0") ;
	            for(GateOrderModel model : mList){
	            	String orderderid = jsonMap.get(model.getUniqueCode());
	            	if(privateFlag==null){
	            		privateFlag = 0;
	            	}
	            	List<GateViolationModel> mlist = violationDao.queryObjectsByHqlList("select t from GateViolationModel t,GateCarModel c where t.carId=c.carId and t.uniqueCode=? and c.mid=?", new Object[]{model.getUniqueCode(),mid});
	            	for(GateViolationModel m:mlist){
	            		if(m!=null){
	            			if(!"".equals(m.getDeGree())&&!"0".equals(m.getDeGree())&&orderderid!=null){
	            				orderIds.append(",");
	            				orderIds.append(orderderid);
	            			}
	            		}
	            	}
	            	Integer i = gateCarOrderDao.executeSqlUpdate("update bill_gateorder o set o.msg='"+msg+"',o.privateflag='"+privateFlag+
	            			"',o.batchid='"+batchId+"',o.state='"+code+"',o.payorderid='"+payOrderId+"',o.apppayordertime=sysdate," +
	            			"o.orderderid='"+orderderid+"' where o.state = '0' and o.hrtOrderId='"+hrtOrderId+
	            			"' and o.mid='"+mid+"' and o.uniquecode='"+model.getUniqueCode()+"'", null);
	            	Integer k = gateCarOrderDao.executeSqlUpdate("update bill_gateorder o set o.mainTainType='D',o.msg='订单关闭',o.passMemo='订单关闭',o.state='7' where o.state = '0' and o.hrtOrderId!='"+hrtOrderId+"' and o.uniquecode='"+model.getUniqueCode()+"'", null);
	            	Integer j = gateCarOrderDao.executeSqlUpdate("update bill_gateviolation v set v.status='1' where v.uniquecode='"+model.getUniqueCode()+"' and v.status='0' ",null);
	            	log.info("app支付成功，三方下单状态更新 修改违章j："+j+";修改本订单i="+i+";关闭其他订单k="+k);
				}
	            //补充材料
	            addOrdersPicture(mid,orderIds.toString());
	        } catch (Exception e) {
	        	log.info("app支付成功，三方下单异常："+gateOrderBean.getHrtOrderId()+e);
	        	result.put("msg", "申请失败(500),订单号:"+gateOrderBean.getHrtOrderId());
	        }
		}else{
			for(GateOrderModel model : mList){
				model.setState("-1");
				model.setPayOrderId(payOrderId);
				model.setAppPayOrderTime(date);
				gateCarOrderDao.updateObject(model);
			}
        	result.put("msg", "未支付,未申请下单");
		}
        return result;
	}
	
	/**
	 * 订单补充材料
	 */
	public void addOrdersPicture(String mid, String orderIds){
		
		String [] orders = orderIds.split(",");
		log.info("app支付成功，三方订单补充材料："+orderIds);
		for(int i = 1;i<orders.length;i++){
			
			String orderId =orders[i];
			String sql ="select c.drilipic1,c.drilibackpic,c.identitycardpic,c.drilipic2,c.insurancepic from bill_gatecarinfo c,bill_gateviolation v,bill_gateorder o " +
			" where c.carid=v.carid and o.mid=c.mid and c.maintaintype!='D' and o.uniquecode=v.uniquecode and c.mid='"+mid+"' and o.orderderid='"+orderId+"'";
			
			List<Map<String, String>> imgList = gateCarOrderDao.queryObjectsBySqlListMap(sql, null);
			
			for (Map<String, String> map : imgList){
				
				//Drivinglicense_face（行驶证正面）drilipic1
				//Drivinglicense_side（行驶证反面）drilibackpic
				//ID_face（身份证正面）identitycardpic
				//Driverlicense_face（驾驶证正面）drilipic2
				//Strong_insurance（交强险正本）insurancepic
				//建议对于扣分单业务，下单时就将行驶证正面、反面资料通过此接口发给我们
				addOrdersPictureTo(orderId,map.get("DRILIPIC1"),"Drivinglicense_face");
				addOrdersPictureTo(orderId,map.get("DRILIBACKPIC"),"Drivinglicense_side");
			}
		}
	}
	
	/**
	 * 订单补充材料发送三方
	 */
	public Integer addOrdersPictureTo(String orderId,String img,String imgId){
		if(img==null||"".equals(img))return 0;
		try{
			InputStream in = null;
	        byte[] data = null;
	        //读取图片字节数组
            in = new FileInputStream(new File(img));   
            data = new byte[in.available()];
            in.read(data);
            in.close();
	        //对字节数组Base64编码
	        img = new String(Base64.encode(data));
			JSONObject jsonObject = new JSONObject();
	        jsonObject.put("orderId", orderId);
	        jsonObject.put("token", paramUtil.getTypeMap("token"));//"F283921F9D81483EAB2BAD2E5519F1DD"
	        jsonObject.put("img", img);//图片base64string，图片小于500KB
	        jsonObject.put("imgId", imgId);
	        JSONObject jsonObject2 = new JSONObject();
	        jsonObject2.put("req_data", jsonObject.toString());
	        System.out.println("订单补充材料未加密请求数据:"+jsonObject2.toString());
	        //业务参数
	        String aesStr = AESCryptPostUtil.encryptByPassword(aesKey, jsonObject2.toString());
	        //请求参数
	        String param = "{\"content\":\"" + aesStr + "\"}";
	        System.out.println("订单补充材料请求数据：" + param);
	        //头文件
	        Map<String, String> headParams = new HashMap();
	        headParams.put("apiName", "Order.OrderDataAdd");
	        headParams.put("keyVersion", keyVersion);
	        headParams.put("version", version);
	        String resultStr = "";
	        resultStr =HttpUtils.sendPostMessage(serviceHost, param,
	                "utf-8", "POST", headParams);
	        resultStr = AESCryptPostUtil.decryptByPassword(aesKey, resultStr);
	        System.out.println("订单补充材料返回信息"+resultStr);
	        JSONObject returnJsonObject = JSONObject.parseObject(resultStr);
	        //状态
	        String code = returnJsonObject.getString("code");
	        if("1".equals(code)){
	        	return 1;
	        }
	        return 0;
//	        //msg消息描述
//	        String msg = returnJsonObject.getString("msg");
		}catch (Exception e) {
        	log.info("订单补充材料异常："+e);
        	return 0;
        }
	}
	
	/**
	 *  三方异步通知
	 * @return 
	 */
	public boolean updateOrdersNotification(JSONObject reqJson){
		
		List jsonlist = (List) reqJson.get("data");
		String type =  (String) reqJson.get("type");
		Date date = new Date();
		Integer j = 0;
		//订单办理结果通知//7：订单处理失败，8：订单
		if("order".equals(type)){
			for(int i=0;i<jsonlist.size();i++){
				JSONObject json=JSONObject.parseObject(jsonlist.get(i).toString());
				String orderId = json.getString("orderId");
				String state = json.getString("state");
				String passMemo = json.getString("passMemo");
				GateOrderModel m =gateCarOrderDao.queryObjectByHql("from GateOrderModel t where t.orderId=? and t.oldHrtOrderId is null", new Object[]{orderId});
				if(m!=null){
					m.setState(state);
					m.setPassMemo(passMemo);
					m.setRebackorderTime(date);
					if("8".equals(state)){
		            	j = gateCarOrderDao.executeSqlUpdate("update bill_gateviolation v set v.status='2' where v.uniquecode='"+m.getUniqueCode()+"' ",null);
					}else{
						//三方返回失败，则订单状态修改为退款中，并修改违章信息为未处理。
		            	j = gateCarOrderDao.executeSqlUpdate("update bill_gateviolation v set v.status='0' where v.uniquecode='"+m.getUniqueCode()+"' ",null);
		            	m.setState("6");
		            	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		    	        Date now = new Date();
		    	        String formatNow = sdf.format(now);
		    	        CheckReFundModel checkReFundModel = new CheckReFundModel();
		            	checkReFundModel.setPkId(2);//车辆代缴退款交易表主键为2
		            	checkReFundModel.setMid(m.getMid());
		            	checkReFundModel.setRrn(m.getHrtOrderId());
		            	checkReFundModel.setOriOrderId(m.getPayOrderId());
		            	checkReFundModel.setSamt(Double.parseDouble(m.getTotalFee()));//原交易金额
		            	checkReFundModel.setTxnDayStr(formatNow);//交易时间20171205
		            	checkReFundModel.setRamt(Double.parseDouble(m.getTotalFee()));//退款金额
		            	checkReFundModel.setSettlement(1);//结算方式
		            	checkReFundModel.setRemarks("车辆代缴退款(系统提交)");//备注
		            	checkReFundModel.setCardPan("0000");
		            	checkReFundModel.setMaintainDate(now);//提交时间
		            	checkReFundModel.setStatus("C");
		            	checkRefundDao.saveObject(checkReFundModel);
					}
					log.info("订单办理结果通知-orderId："+orderId+" ; state = "+state+" ; j="+j);
				}else{
					log.info("订单办理结果通知订单号不存在-orderId："+orderId);
				}
			}
		}else if ("replenishment".equals(type)){
			//需补款通知//3
			for(int i=0;i<jsonlist.size();i++){
				JSONObject json=JSONObject.parseObject(jsonlist.get(i).toString());
				String orderId = json.getString("orderId");//orderId订单ID
				String amount = json.getString("amount");//amount需补款金额
				String memo = json.getString("memo");//memo需补款描述
				GateOrderModel m =gateCarOrderDao.queryObjectByHql("from GateOrderModel t where t.orderId=?", new Object[]{orderId});
				if(m!=null){
					if("4".equals(m.getState())){
						m.setState("5");
					}else{
						m.setState("3");
					}
					m.setPassMemo(memo);
					m.setRebackorderTime(date);
					m.setAmount(amount);
					gateCarOrderDao.updateObject(m);
					log.info("订单补款结果通知-orderId："+orderId+" ; amount = "+amount+" ; memo="+memo);
				}else{
					log.info("需补款通知订单号不存在-orderId："+orderId);
				}
			}
		}else if ("waitUpload".equals(type)){
			//需补资料通知//4
			for(int i=0;i<jsonlist.size();i++){
				JSONObject json=JSONObject.parseObject(jsonlist.get(i).toString());
				String orderId = json.getString("orderId");//orderId订单ID
				//Drivinglicense_face（行驶证正面）
				//Drivinglicense_side（行驶证反面）
				//ID_face（身份证正面）
				//Driverlicense_face（驾驶证正面）
				//Strong_insurance（交强险正本）
				String waitUploadImgIds = json.getString("imgId"); //1,2
				String memo = json.getString("memo");//memo描述
				GateOrderModel m =gateCarOrderDao.queryObjectByHql("from GateOrderModel t where t.orderId=?", new Object[]{orderId});
				if(m!=null){
					if("3".equals(m.getState())){
						m.setState("5");
					}else{
						m.setState("4");
					}
					m.setPassMemo(memo);
					m.setRebackorderTime(date);
					m.setWaitUploadImgIds(waitUploadImgIds);
					gateCarOrderDao.updateObject(m);
					log.info("订单补资料结果通知-orderId："+orderId+" ; waitUploadImgIds = "+waitUploadImgIds+" ; memo="+memo);
				}else{
					log.info("需补资料通知订单号不存在-orderId："+orderId);
				}
			}
		}
		return true;
	}
	
	/**
	 * 查询订单
	 */
	public DataGridBean queryOrders(GateOrderBean gateOrderBean){
		
		DataGridBean dgb = new DataGridBean();
		String sql = "select o.*,decode(o.state,0,0,7,7,-1,7,8,8,3) as status,v.time,v.location,v.reason,v.excuteDepartment,v.archive," +
					" v.pricesource,v.department,v.deGree,v.count,v.lateFine,v.poundage,v.poundage2,v.locationname,v.receivepoundage,c.carid,c.cartype,c.carcode,c.enginenumber,c.carnumber,c.DRILIPIC1,c.DRILIPIC2,c.IDENTITYCARDPIC,c.INSURANCEPIC,c.DRILIBACKPIC "+
					 " from bill_gateorder o,bill_gateviolation v,bill_gatecarinfo c where o.goid not in (select goid from  bill_gateorder o where state='0' and trunc(placeordertime)!=trunc(sysdate))"+
					 " and o.uniquecode=v.uniquecode and c.carid=v.carid and c.mid=o.mid  and v.mainTainType!='D' and o.mainTainType!='D' ";
		String sqlCount = "select count(*) from bill_gateorder o,bill_gateviolation v,bill_gatecarinfo c where o.goid not in (select goid from  bill_gateorder o where state='0' and trunc(placeordertime)!=trunc(sysdate)) "+
					 " and o.uniquecode=v.uniquecode and c.carid=v.carid and c.mid=o.mid and v.mainTainType!='D' and o.mainTainType!='D'";
		if(gateOrderBean.getMid()!=null){
			sql+=" and o.mid ='"+gateOrderBean.getMid()+"'";
			sqlCount+=" and o.mid ='"+gateOrderBean.getMid()+"'";
		}
		//0：等待支付;  7：订单处理失败(7,-1)，8：订单成功 ；4订单补资料 3订单处理中(1,9,3)
		if("0".equals(gateOrderBean.getStatus())){
			sql+=" and o.state ='0'";
			sqlCount+=" and o.state ='0'";
		}else if("7".equals(gateOrderBean.getStatus())){
			sql+=" and o.state in ('7','-1','6')";
			sqlCount+="and o.state in ('7','-1','6')";
		}else if("8".equals(gateOrderBean.getStatus())){
			sql+=" and o.state in ('8')";
			sqlCount+="and o.state in ('8')";
		}else if("3".equals(gateOrderBean.getStatus())){
			sql+=" and o.state in ('1','9','3','4','5')";
			sqlCount+=" and o.state in ('1','9','3','4','5')";
		}
		sql += " order by o.placeOrderTime desc ";
		long counts = gateCarOrderDao.querysqlCounts(sqlCount, null).longValue();
		List<Map<String, String>> GateOrderModelList = gateCarOrderDao.queryObjectsBySqlList(sql, null, gateOrderBean.getPage(), gateOrderBean.getRows());
		long now = new Date().getTime();
		for (Map<String, String> map : GateOrderModelList) {
			map.put("DRILIPIC1",map.get("DRILIPIC1")==null?"":"phone/gateCar_showImage.action?image="+map.get("DRILIPIC1")+"&meanless="+now);
			map.put("DRILIPIC2",map.get("DRILIPIC2")==null?"":"phone/gateCar_showImage.action?image="+map.get("DRILIPIC2")+"&meanless="+now);
			map.put("IDENTITYCARDPIC",map.get("IDENTITYCARDPIC")==null?"":"phone/gateCar_showImage.action?image="+map.get("IDENTITYCARDPIC")+"&meanless="+now);
			map.put("INSURANCEPIC",map.get("INSURANCEPIC")==null?"":"phone/gateCar_showImage.action?image="+map.get("INSURANCEPIC")+"&meanless="+now);
			map.put("DRILIBACKPIC",map.get("DRILIBACKPIC")==null?"":"phone/gateCar_showImage.action?image="+map.get("DRILIBACKPIC")+"&meanless="+now);
		}
		dgb.setRows(GateOrderModelList);
		dgb.setTotal(counts);
		return dgb;
	}
	
	private void exportHssBook(List<Map<String, Object>>list,HttpServletResponse response) throws RowsExceededException, WriteException, IOException {
		String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t","t"};
		String [] titles = {"商户mid","和融通号","三方订单号","付款单号","订单批次id","唯一编号","是否快速办理","是否本人下单","总计下单金额","是否是私家车","订单状态","下单时间","补款金额",};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object>map = list.get(i);
			String[] cellContent = {
					map.get("MID")==null?"":map.get("MID").toString(),
					map.get("HRTORDERID")==null?"":map.get("HRTORDERID").toString(),
					map.get("ORDERDERID")==null?"":map.get("ORDERDERID").toString(),
					map.get("PAYORDERID")==null?"":map.get("PAYORDERID").toString(),
					map.get("BATCHID")==null?"":map.get("BATCHID").toString(),
					map.get("UNIQUECODE")==null?"":map.get("UNIQUECODE").toString(),
					map.get("IDQUICK")==null?"":map.get("IDQUICK").toString(),
					map.get("ISUSPRICE")==null?"":map.get("ISUSPRICE").toString(),
					map.get("TOTALFEE")==null?"":map.get("TOTALFEE").toString(),
					map.get("PRIVATEFLAG")==null?"":map.get("PRIVATEFLAG").toString(),
					map.get("STATE")==null?"":map.get("STATE").toString(),
					map.get("PLACEORDERTIME")==null?"":map.get("PLACEORDERTIME").toString(),
					map.get("AMOUNT")==null?"":map.get("AMOUNT").toString()
			};
			excelList.add(cellContent);
		}
		String excelName = "车辆违章订单资料";
		String sheetName = "车辆违章订单资料";
		JxlOutExcelUtil.writeExcel(excelList, cellFormatType.length, response, sheetName, excelName+".xls", cellFormatType);
	}
	/**
	 * 将资料发给三方
	 * @return 0-失败 1-成功
	 */
	@Override
	public Integer updateGateCarInfo(String mid,String orderId,String imgIds) {
		String[] imgs = imgIds.split(",");
		if(mid==null||"".equals(mid)||orderId==null||"".equals(orderId))return 0;
		String sql ="select c.drilipic1,c.drilibackpic,c.identitycardpic,c.drilipic2,c.insurancepic from bill_gatecarinfo c,bill_gateviolation v,bill_gateorder o " +
				" where c.carid=v.carid and o.mid=c.mid and c.maintaintype!='D' and o.uniquecode=v.uniquecode and c.mid='"+mid+"' and o.orderderid='"+orderId+"' and o.state in (4,5)";
		List<Map<String, String>> imgList = gateCarOrderDao.queryObjectsBySqlListMap(sql, null);
		//orderID就是order表中的orderderid
		if(imgList!=null&&imgList.size()>0){
			Map<String, String> map = imgList.get(0);
			for(int i=0;i<imgs.length;i++){
				String imgId = imgs[i];
				//获取数据库中图片地址
				String img="";
				if("Drivinglicense_face".equals(imgId)){
					img=map.get("DRILIPIC1");//行驶证正面
				}else if("Drivinglicense_side".equals(imgId)){
					img=map.get("DRILIBACKPIC");//行驶证反面
				}else if("ID_face".equals(imgId)){
					img=map.get("IDENTITYCARDPIC");//身份证正面
				}else if ("Driverlicense_face".equals(imgId)){
					img=map.get("DRILIPIC2");//驾驶证正面
				}else if ("Strong_insurance".equals(imgId)){
					img=map.get("INSURANCEPIC");//交强险正面
				}	
				if(img==null||"".equals(img))continue;
				//发送三方0-失败 1-成功
				Integer flag = addOrdersPictureTo(orderId, img, imgId);
				if(flag==0){
					return 0;
				}
			}
		}else{
			return 0;
		}
		//补资料成功修改订单状态
		GateOrderModel m =gateCarOrderDao.queryObjectByHql("from GateOrderModel t where t.orderId=? and t.state in (4,5)", new Object[]{orderId});
		if("5".equals(m.getState())){
			m.setState("3");
		}else if("4".equals(m.getState())){
			m.setState("9");
		}
		gateCarOrderDao.updateObject(m);
		return 1;
	}
	/**
	 * 补款下单
	 */
	@Override
	public String saveOrderAmount(GateOrderBean gateOrderBean) {
		GateOrderModel gateOrder = gateCarOrderDao.queryObjectByHql("from GateOrderModel t where t.oldHrtOrderId=? and t.uniqueCode=? and t.state='0'", new Object[]{gateOrderBean.getHrtOrderId(),gateOrderBean.getUniqueCode()});
		if(gateOrder!=null){
			return gateOrder.getHrtOrderId();
		}
		Date date = new Date();
		GateOrderModel gateOrderModel = gateCarOrderDao.queryObjectByHql("from GateOrderModel t where t.hrtOrderId=? and t.uniqueCode=?", new Object[]{gateOrderBean.getHrtOrderId(),gateOrderBean.getUniqueCode()});
		GateOrderModel model = new GateOrderModel();
		
		//和融通订单
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
		StringBuffer hrtOrderId = new StringBuffer(sdf.format(date));
		while (hrtOrderId.length() < 16) {
			String s = Double.toString(Math.random()*10);
			if (s.length() > 3) {
				hrtOrderId.append(s.substring(0, 1));
			}
		}
		model.setMid(gateOrderModel.getMid());          		 //商户主键
		model.setOrderId(gateOrderModel.getOrderId());
		model.setPriceId(gateOrderModel.getPriceId());  		 //违章办理报价接口获得的id
		model.setUniqueCode(gateOrderModel.getUniqueCode());     			 //唯一code
		model.setIsQuick(gateOrderModel.getIsQuick());        //是否快速办理 0：否  1：是
		model.setIsUsPrice(gateOrderModel.getIsUsPrice());    //是否本人下单 0：否  1：是
		model.setTotalFee(gateOrderModel.getAmount());				 	 //下单金额
		model.setState("0");
		model.setPassMemo("待支付");
		model.setPlaceOrderTime(date);
		model.setMainTainDate(date);
		model.setMainTainType("A");
		model.setHrtOrderId(hrtOrderId.toString());
		model.setOldHrtOrderId(gateOrderBean.getHrtOrderId());
		gateCarOrderDao.saveObject(model);
		
        return hrtOrderId.toString();
	}
	@Override
	public Map<String, String> updateOrderDetail(GateOrderBean gateOrderBean) {
		
		Map<String, String> result = new HashMap<String, String>();
		String hrtOrderId = gateOrderBean.getHrtOrderId();
		String payOrderId = gateOrderBean.getPayOrderId();
		String state = gateOrderBean.getState();
		String mid = gateOrderBean.getMid();
		String uniqueCode = gateOrderBean.getUniqueCode();
		Date date = new Date();
		
		List<GateOrderModel> mList = gateCarOrderDao.queryObjectsBySqlList("select * from bill_gateorder where hrtOrderId='"+hrtOrderId+"' and mid='"+mid+"' ", null,GateOrderModel.class);
		if(mList==null||mList.size()==0){
			result.put("msg", "未查询到订单!");
			return result;
		}
		GateOrderModel gateOrderModel = mList.get(0);//补款订单
		//支付成功,三方下单
		if("1".equals(state)){
			try {
				JSONObject jsonObject = new JSONObject();
		        jsonObject.put("token", paramUtil.getTypeMap("token"));//"F283921F9D81483EAB2BAD2E5519F1DD"
		        jsonObject.put("orderId", gateOrderModel.getOrderId());
		        jsonObject.put("amount", gateOrderModel.getTotalFee());
		        JSONObject jsonObject2 = new JSONObject();
		        jsonObject2.put("req_data", jsonObject.toString());
//		        三方下单  三方补款
		        System.out.println("三方下单未加密请求数据:"+jsonObject2.toString());
		        //业务参数
		        String aesStr = AESCryptPostUtil.encryptByPassword(aesKey, jsonObject2.toString());
		        //请求参数
		        String param = "{\"content\":\"" + aesStr + "\"}";
		        System.out.println("三方下单请求数据：" + param);
		        //头文件
		        Map<String, String> headParams = new HashMap();
		        headParams.put("apiName", "Order.OrderAmountAdd");
		        headParams.put("keyVersion", keyVersion);
		        headParams.put("version", version);
		        String resultStr = "";
	            resultStr =HttpUtils.sendPostMessage(serviceHost, param,
	                    "utf-8", "POST", headParams);
	            resultStr = AESCryptPostUtil.decryptByPassword(aesKey, resultStr);
	            System.out.println("三方下单返回信息"+resultStr);
	            JSONObject returnJsonObject = JSONObject.parseObject(resultStr);
	            //状态
	            String code = returnJsonObject.getString("code");
	            //msg消息描述
	            String msg = returnJsonObject.getString("msg");
	            if("1".equals(code)){
	            	//补款成功修改补款订单及原订单
	            	gateOrderModel.setState("8");
	            	gateOrderModel.setPassMemo(msg);
	            	gateCarOrderDao.executeSqlUpdate("update bill_gateorder o set o.state='"+9+"' where o.state = '3' and o.hrtOrderId='"+gateOrderModel.getOldHrtOrderId()+"' and o.mid='"+mid+"' and o.uniquecode='"+uniqueCode+"' ", null);
	            	result.put("msg", "补款成功");
	            }else{
	            	gateOrderModel.setState("7");
	            	gateOrderModel.setPassMemo(msg);
	            	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    	        Date now = new Date();
	    	        String formatNow = sdf.format(now);
	    	        CheckReFundModel checkReFundModel = new CheckReFundModel();
	            	checkReFundModel.setPkId(2);//车辆代缴退款交易表主键为2
	            	checkReFundModel.setMid(gateOrderModel.getMid());
	            	checkReFundModel.setRrn(gateOrderModel.getHrtOrderId());
	            	checkReFundModel.setOriOrderId(gateOrderModel.getPayOrderId());
	            	checkReFundModel.setSamt(Double.parseDouble(gateOrderModel.getTotalFee()));//原交易金额
	            	checkReFundModel.setTxnDayStr(formatNow);//交易时间20171205
	            	checkReFundModel.setRamt(Double.parseDouble(gateOrderModel.getTotalFee()));//退款金额
	            	checkReFundModel.setSettlement(1);//结算方式
	            	checkReFundModel.setRemarks("车辆代缴退款(补款失败)");//备注
	            	checkReFundModel.setCardPan("0000");
	            	checkReFundModel.setMaintainDate(now);//提交时间
	            	checkReFundModel.setStatus("C");
	            	checkRefundDao.saveObject(checkReFundModel);
	            	gateCarOrderDao.executeSqlUpdate("update bill_gateorder o set o.state='"+3+"' where o.state = '3' and o.hrtOrderId='"+gateOrderModel.getOldHrtOrderId()+"' and o.mid='"+mid+"' and o.uniquecode='"+uniqueCode+"' ", null);
	            	result.put("msg", "补款失败");
	            }
	            
	            	log.info("app补款支付成功，三方下单状态更新 修改待补款订单："+gateOrderModel.getOldHrtOrderId()+";修改本订单"+hrtOrderId);
				
	            
	        } catch (Exception e) {
	        	log.info("app补款支付成功，三方下单异常："+gateOrderBean.getHrtOrderId()+e);
	        	result.put("msg", "补款失败(500),订单号:"+gateOrderBean.getHrtOrderId());
	        }
		}else{
			gateOrderModel.setState("-1");
			gateOrderModel.setPayOrderId(payOrderId);
			gateOrderModel.setAppPayOrderTime(date);
			gateCarOrderDao.updateObject(gateOrderModel);
        	result.put("msg", "未支付,未申请下单");
		}
        return result;
	}
	@Override
	public Map<String, String> updateOrderCancel(GateOrderBean gateOrderBean) {
		Map<String, String> result = new HashMap<String, String>();
		List<GateOrderModel> mList = gateCarOrderDao.queryObjectsBySqlList("select * from bill_gateorder where hrtOrderId='"+gateOrderBean.getHrtOrderId()+"' and mid='"+gateOrderBean.getMid()+"' and uniquecode='"+gateOrderBean.getUniqueCode()+"' and state in (3,4,5) ", null,GateOrderModel.class);
		if(mList==null||mList.size()==0){
			result.put("msg", "未查询到订单!");
			return result;
		}
		GateOrderModel gateOrderModel = mList.get(0);
		Integer integer = checkRefundDao.querysqlCounts2("select count(*) from check_refund where mid='"+gateOrderBean.getMid()+"' and oriorderid='"+gateOrderBean.getPayOrderId()+"' and rrn='"+gateOrderModel.getHrtOrderId()+"' and status='C' ", null);
		if(integer>0){
			result.put("msg", "已提交退款申请!");
			return result;
		}
		try{
			JSONObject jsonObject = new JSONObject();
	        jsonObject.put("token", paramUtil.getTypeMap("token"));//"F283921F9D81483EAB2BAD2E5519F1DD"
	        jsonObject.put("orderId", gateOrderModel.getOrderId());
	        JSONObject jsonObject2 = new JSONObject();
	        jsonObject2.put("req_data", jsonObject.toString());
	        System.out.println("三方退款未加密请求数据:"+jsonObject2.toString());
	        //业务参数
	        String aesStr = AESCryptPostUtil.encryptByPassword(aesKey, jsonObject2.toString());
	        //请求参数
	        String param = "{\"content\":\"" + aesStr + "\"}";
	        System.out.println("三方退款请求数据：" + param);
	        //头文件
	        Map<String, String> headParams = new HashMap<String, String>();
	        headParams.put("apiName", "Order.OrderCancel");
	        headParams.put("keyVersion", keyVersion);
	        headParams.put("version", version);
	        String resultStr = "";
	        resultStr =HttpUtils.sendPostMessage(serviceHost, param,
	                "utf-8", "POST", headParams);
	        resultStr = AESCryptPostUtil.decryptByPassword(aesKey, resultStr);
	        
	        System.out.println("三方退款返回信息"+resultStr);
	        JSONObject returnJsonObject = JSONObject.parseObject(resultStr);
	        //状态
	        String code = returnJsonObject.getString("code");
	        //msg消息描述
	        String msg = returnJsonObject.getString("msg");
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	        Date now = new Date();
	        String formatNow = sdf.format(now);
	        if("1".equals(code)){//退款成功
	        	gateOrderModel.setState("6");
	        	gateCarOrderDao.updateObject(gateOrderModel);
	        	violationDao.executeSqlUpdate("update bill_gateviolation v set v.status='0' where v.uniquecode='"+gateOrderModel.getUniqueCode()+"' and v.status='1' ",null);
	        	result.put("msg", "退款申请已提交，退款会在 T+1 日退回到原支付账户，请注意查收，如有疑问，请拨打客服电话：400-010-5670。");
	        }else{//退款失败
		        result.put("msg", "退款失败，请稍后再试");
	        }
		} catch (Exception e) {
	    	log.info("app请求成功，三方退款异常："+gateOrderBean.getHrtOrderId()+e);
	    	result.put("msg", "退款失败(500),订单号:"+gateOrderBean.getHrtOrderId());
	    }
		return result;
	}
	
}
