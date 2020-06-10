package com.hrt.phone.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.hrt.biz.util.gateway.MD5Util;
import com.hrt.biz.util.unionpay.HrtRSAUtil;
import com.hrt.biz.util.unionpay.SHAEncUtil;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.JsonBeanForSign;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.phone.service.IPhoneCheckUnitDealDataService;
import com.hrt.phone.service.IPhoneWechatPublicAccService;
import com.hrt.util.SimpleXmlUtil;

public class PhoneCheckUnitDealDataAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(PhoneCheckUnitDealDataAction.class);
	
	private IPhoneCheckUnitDealDataService phoneCheckUnitDealDataService;
	private IPhoneWechatPublicAccService phoneWechatPublicAccService;
	
	private Integer page;
	private Integer rows;
	private String mid;
	private String startDay;
	private String endDay;
	private String tid;
	private String cardPan;
	private String stan;
	private String txnAmount;
    private Integer code;
	
	
	public IPhoneWechatPublicAccService getPhoneWechatPublicAccService() {
		return phoneWechatPublicAccService;
	}
	public void setPhoneWechatPublicAccService(IPhoneWechatPublicAccService phoneWechatPublicAccService) {
		this.phoneWechatPublicAccService = phoneWechatPublicAccService;
	}
	public IPhoneCheckUnitDealDataService getPhoneCheckUnitDealDataService() {
		return phoneCheckUnitDealDataService;
	}
	public void setPhoneCheckUnitDealDataService(
			IPhoneCheckUnitDealDataService phoneCheckUnitDealDataService) {
		this.phoneCheckUnitDealDataService = phoneCheckUnitDealDataService;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getCardPan() {
		return cardPan;
	}
	public void setCardPan(String cardPan) {
		this.cardPan = cardPan;
	}
	public String getStan() {
		return stan;
	}
	public void setStan(String stan) {
		this.stan = stan;
	}

	public String getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	//首页信息old
	public void findHomePageData(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			List<Map<String ,Object>> list= phoneCheckUnitDealDataService.findHomePageData(userSession);
			json.setMsg("查询成功！");
			json.setObj(list); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询信息异常：" + e);
			json.setMsg("查询失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	//首页信息new解密
	public void decFindHomePageDataNew(){
		JsonBean json = new JsonBean();
		boolean b = phoneWechatPublicAccService.addReplayAttack(super.getRequest().getParameter("uuid"), super.getRequest().getParameter("timeStamp"));
		if(!b) {
			json.setSuccess(false);
			json.setMsg("数据有误！");
		}else {
			String mid = super.getRequest().getParameter("mid");
			String sign = super.getRequest().getParameter("sign");
			Map<String,String> map1 = new HashMap<String, String>();
			map1.put("mid", mid);
			String md5 = MD5Util.MD5((SimpleXmlUtil.getSignBlock(map1)+"&key=dseesa325errtcyraswert749errtdyt"));
			if(md5.equals(sign)){
				try {
					Object userSession = super.getRequest().getSession().getAttribute("user");
					List<Map<String ,Object>> list= phoneCheckUnitDealDataService.findHomePageDataNew(userSession);
					json.setMsg("查询成功！");
					json.setObj(list); 
					json.setSuccess(true);
				} catch (Exception e) {
					log.error("查询信息异常：" + e);
					json.setMsg("查询失败！");
					json.setSuccess(false);
				}
			}else {
				json.setSuccess(false);
				json.setMsg("数据有误！");
			}
		}
		JsonBeanForSign json1 = new JsonBeanForSign();
		String data = JSON.toJSONStringWithDateFormat(json, "yyyy-MM-dd HH:mm:ss");
		String signs1 = SHAEncUtil.getSHA256Str(data);
		json1.setSign(signs1);
		json1.setData(data);
		super.writeJson(json1);
	}
	//首页信息new
	public void findHomePageDataNew(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			List<Map<String ,Object>> list= phoneCheckUnitDealDataService.findHomePageDataNew(userSession);
			json.setMsg("查询成功！");
			json.setObj(list); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询信息异常：" + e);
			json.setMsg("查询失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}

	//待结算
	public void findNoSettlement(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			DataGridBean dgb = phoneCheckUnitDealDataService.findNoSettlement(userSession,page,rows);
			json.setMsg("查询成功！");
			json.setObj(dgb); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询待结算信息异常：" + e);
			json.setMsg("查询待结算失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}

	//待结算明细
	public void findNoSettlementDetail(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			DataGridBean dgb = phoneCheckUnitDealDataService.findNoSettlementDetail(userSession,page,rows,mid);
			json.setMsg("查询成功！");
			json.setObj(dgb); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询待结算详细信息异常：" + e);
			json.setMsg("查询待结算详细信息失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	//当日交易额
	public void findTheDayTurnover(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			DataGridBean dgb = phoneCheckUnitDealDataService.findTheDayTurnover(userSession,page,rows);
			json.setMsg("查询成功！");
			json.setObj(dgb); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询当日交易额信息异常：" + e);
			json.setMsg("查询交易额细信息失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}

	
	//交易——>一周交易折线图
	public void findDynamicFormData(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			json = phoneCheckUnitDealDataService.findDynamicFormData(userSession);
		} catch (Exception e) {
			log.error("查询一周交易异常：" + e);
			json.setMsg("查询一周交易失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}

	
	//交易——>一月交易折线图
	public void findMonthDynamicFormData(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			json = phoneCheckUnitDealDataService.findMonthDynamicFormData(userSession);
		} catch (Exception e) {
			log.error("查询一月交易异常：" + e);
			json.setMsg("查询一月交易失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}

	//交易——>一年交易折线图
	public void findYearDynamicFormData(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			json = phoneCheckUnitDealDataService.findYearDynamicFormData(userSession);
		} catch (Exception e) {
			log.error("查询一年交易异常：" + e);
			json.setMsg("查询一年交易失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}

	//TOP20
	public void findTop20(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			DataGridBean dgb = phoneCheckUnitDealDataService.findTop20(userSession);
			json.setMsg("查询成功！");
			json.setObj(dgb); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询TOP20异常：" + e);
			json.setMsg("查询TOP20失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	
	//涨幅10%gains
	public void findGains(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			DataGridBean dgb = phoneCheckUnitDealDataService.findGains(userSession,page,rows);
			json.setMsg("查询成功！");
			json.setObj(dgb); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询涨幅异常：" + e);
			json.setMsg("查询涨幅失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	//降幅 10% drop
	public void findDrop(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			DataGridBean dgb = phoneCheckUnitDealDataService.findDrop(userSession,page,rows);
			json.setMsg("查询成功！");
			json.setObj(dgb); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询降幅异常：" + e);
			json.setMsg("查询降幅失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	//刷卡历史查询
	public void decFindHistertoyData(){
		JsonBean json=new JsonBean();
		boolean b = phoneWechatPublicAccService.addReplayAttack(super.getRequest().getParameter("uuid"), super.getRequest().getParameter("timeStamp"));
		if(!b) {
			json.setSuccess(false);
			json.setMsg("数据有误！");
		}else {
			String sign = super.getRequest().getParameter("sign");
			Map<String,String> map1 = new HashMap<String, String>();
			map1.put("page", page+"");
			map1.put("rows", rows+"");
			map1.put("startDay", startDay);
			map1.put("endDay", endDay);
			String md5 = MD5Util.MD5((SimpleXmlUtil.getSignBlock(map1)+"&key=dseesa325errtcyraswert749errtdyt"));
			if(md5.equals(sign)){
				try {
					Object userSession = super.getRequest().getSession().getAttribute("user");
					json = phoneCheckUnitDealDataService.findHistertoy(userSession,startDay,endDay,page,rows,txnAmount);
					json.setMsg("查询成功！");
					json.setSuccess(true);
				} catch (Exception e) {
					log.error("查询历史异常：" + e);
					json.setMsg("查询历史失败！");
					json.setSuccess(false);
				}
			}else {
				json.setSuccess(false);
				json.setMsg("数据有误！");
			}
		}
		JsonBeanForSign json1 = new JsonBeanForSign();
		String data = JSON.toJSONStringWithDateFormat(json, "yyyy-MM-dd HH:mm:ss");
		String signs1 = SHAEncUtil.getSHA256Str(data);
		json1.setSign(signs1);
		json1.setData(data);
		super.writeJson(json1);
	}
	
	//刷卡历史查询
	public void findHistertoyData(){
		JsonBean json=new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			json = phoneCheckUnitDealDataService.findHistertoy(userSession,startDay,endDay,page,rows,txnAmount);
			json.setMsg("查询成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询历史异常：" + e);
			json.setMsg("查询历史失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	//扫码历史查询
	public void findHistertoyScanData(){
		JsonBean json=new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			json = phoneCheckUnitDealDataService.findHistertoyScan(userSession,startDay,endDay,page,rows,txnAmount,"1");
			json.setMsg("查询成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("app查询扫码历史异常：" + e);
			json.setMsg("查询历史失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	//快捷历史查询
	public void findHistertoyKJData(){
		JsonBean json=new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			json = phoneCheckUnitDealDataService.findHistertoyScan(userSession,startDay,endDay,page,rows,txnAmount,"8");
			json.setMsg("查询成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("app查询扫码历史异常：" + e);
			json.setMsg("查询历史失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	//历史查询（code为1:昨天,code为2:本周）
	public void decFindHistertoyData2(){
		JsonBean json=new JsonBean();
		boolean b = phoneWechatPublicAccService.addReplayAttack(super.getRequest().getParameter("uuid"), super.getRequest().getParameter("timeStamp"));
		if(!b) {
			json.setSuccess(false);
			json.setMsg("数据有误！");
		}else {
			String sign = super.getRequest().getParameter("sign");
			Map<String,String> map1 = new HashMap<String, String>();
			map1.put("page", page+"");
			map1.put("rows", rows+"");
			map1.put("Code", code+"");
			String md5 = MD5Util.MD5((SimpleXmlUtil.getSignBlock(map1)+"&key=dseesa325errtcyraswert749errtdyt"));
			if(md5.equals(sign)){
				try {
					Object userSession = super.getRequest().getSession().getAttribute("user");
					json = phoneCheckUnitDealDataService.findHistertoy2(userSession,page,rows,code);
					json.setMsg("查询成功！");
					json.setSuccess(true);
				} catch (Exception e) {
					log.error("查询历史异常：" + e);
					json.setMsg("查询历史失败！");
					json.setSuccess(false);
				}
			}else {
				json.setSuccess(false);
				json.setMsg("数据有误！");
			}
		}
		JsonBeanForSign json1 = new JsonBeanForSign();
		String data = JSON.toJSONStringWithDateFormat(json, "yyyy-MM-dd HH:mm:ss");
		String signs1 = SHAEncUtil.getSHA256Str(data);
		json1.setSign(signs1);
		json1.setData(data);
		super.writeJson(json1);
	}

	//历史查询（code为1:昨天,code为2:本周）
	public void findHistertoyData2(){
		JsonBean json=new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			json = phoneCheckUnitDealDataService.findHistertoy2(userSession,page,rows,code);
			json.setMsg("查询成功！");
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询历史异常：" + e);
			json.setMsg("查询历史失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
//历史查询---》合计交易量
	
	public void findHistertoyDataCount(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			DataGridBean dgb = phoneCheckUnitDealDataService.findHistertoyCount(userSession,startDay,endDay,page,rows,txnAmount);
			json.setMsg("查询成功！");
			json.setObj(dgb); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询历史合计异常：" + e);
			json.setMsg("查询历史合计失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	//实时交易查询解密
	public void decFindRealTimeRrading(){
		JsonBean json = new JsonBean();
		boolean b = phoneWechatPublicAccService.addReplayAttack(super.getRequest().getParameter("uuid"), super.getRequest().getParameter("timeStamp"));
		if(!b) {
			json.setSuccess(false);
			json.setMsg("数据有误！");
		}else {
			String sign = super.getRequest().getParameter("sign");
			Map<String,String> map1 = new HashMap<String, String>();
			map1.put("page", page+"");
			map1.put("rows", rows+"");
			String md5 = MD5Util.MD5((SimpleXmlUtil.getSignBlock(map1)+"&key=dseesa325errtcyraswert749errtdyt"));
			if(md5.equals(sign)){
				try {
					Object userSession = super.getRequest().getSession().getAttribute("user");
					DataGridBean dgb = phoneCheckUnitDealDataService.findRealTimeRrading((UserBean)userSession,page,rows);
					
					json.setMsg("查询成功！");
					json.setObj(dgb); 
					json.setSuccess(true);
				} catch (Exception e) {
					log.error("实时交易查询异常：" + e);
					json.setMsg("实时交易查询失败！");
					json.setSuccess(false);
				}
			}else {
				json.setSuccess(false);
				json.setMsg("数据有误！");
			}
		}
		JsonBeanForSign json1 = new JsonBeanForSign();
		String data = JSON.toJSONStringWithDateFormat(json, "yyyy-MM-dd HH:mm:ss");
		String signs1 = SHAEncUtil.getSHA256Str(data);
		json1.setSign(signs1);
		json1.setData(data);
		super.writeJson(json1);
	}
	
	//实时交易查询
	public void findRealTimeRrading(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			DataGridBean dgb = phoneCheckUnitDealDataService.findRealTimeRrading((UserBean)userSession,page,rows);
			
			json.setMsg("查询成功！");
			json.setObj(dgb); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("实时交易查询异常：" + e);
			json.setMsg("实时交易查询失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	//实时交易查询-检查
	public void findRealTimeRradingForMid(){
		JsonBean json = new JsonBean();
		try {
			String mid = super.getRequest().getParameter("mid");
			UserBean userSession =new UserBean();
			userSession.setUseLvl(99);
			userSession.setLoginName(mid);
			DataGridBean dgb = phoneCheckUnitDealDataService.findRealTimeRrading(userSession,page,rows);
			json.setMsg("查询成功！");
			json.setObj(dgb); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("实时交易查询异常：" + e);
			json.setMsg("实时交易查询失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	//扫码实时交易查询
	public void findWechatRealTimeRrading(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			DataGridBean dgb = phoneCheckUnitDealDataService.findWechatRealTimeRrading((UserBean)userSession,page,rows);
			
			json.setMsg("查询成功！");
			json.setObj(dgb); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("扫码实时交易查询异常：" + e);
			json.setMsg("扫码实时交易查询失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}

	//扫码实时交易查询
	public void findWechatRealTimeRradingMd(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			DataGridBean dgb = phoneCheckUnitDealDataService.findWechatRealTimeRradingMd((UserBean)userSession,page,rows);

			json.setMsg("查询成功！");
			json.setObj(dgb);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("扫码实时交易查询异常：" + e);
			json.setMsg("扫码实时交易查询失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	//查询实时交易总数量
	public void decFindRealTimeRradingCount(){
		JsonBean json = new JsonBean();
		boolean b = phoneWechatPublicAccService.addReplayAttack(super.getRequest().getParameter("uuid"), super.getRequest().getParameter("timeStamp"));
		if(!b) {
			json.setSuccess(false);
			json.setMsg("数据有误！");
		}else {
			String sign = super.getRequest().getParameter("sign");
			Map<String,String> map1 = new HashMap<String, String>();
			map1.put("mid", mid);
			String md5 = MD5Util.MD5((SimpleXmlUtil.getSignBlock(map1)+"&key=dseesa325errtcyraswert749errtdyt"));
			if(md5.equals(sign)){
				try {
					Object userSession = super.getRequest().getSession().getAttribute("user");
					Integer count = phoneCheckUnitDealDataService.findRealTimeRradingCount((UserBean)userSession);
					
					json.setMsg("查询成功！");
					json.setObj(count); 
					json.setSuccess(true);
				} catch (Exception e) {
					log.error("查询实时交易总量异常：" + e);
					json.setMsg("查询实时交易总量失败！");
					json.setSuccess(false);
				}
			}else {
				json.setSuccess(false);
				json.setMsg("数据有误！");
			}
		}
		JsonBeanForSign json1 = new JsonBeanForSign();
		String data = JSON.toJSONStringWithDateFormat(json, "yyyy-MM-dd HH:mm:ss");
		String signs1 = SHAEncUtil.getSHA256Str(data);
		json1.setSign(signs1);
		json1.setData(data);
		super.writeJson(json1);
	}
	
	//查询扫码实时交易总金额
	public void findWechatRealTimeRradingTotal(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			Double count = phoneCheckUnitDealDataService.findWechatRealTimeRradingTotal((UserBean)userSession);
			
			json.setMsg("查询成功！");
			json.setObj(count); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询扫码实时交易总金额异常：" + e);
			json.setMsg("查询扫码实时交易总金额失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}

	//查询扫码实时交易总金额
	public void findWechatRealTimeRradingTotalMd(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			Double count = phoneCheckUnitDealDataService.findWechatRealTimeRradingTotalMd((UserBean)userSession);
			json.setMsg("查询成功！");
			json.setObj(count);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询扫码实时交易总金额异常：" + e);
			json.setMsg("查询扫码实时交易总金额失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	//查询实时交易总数量
	public void findRealTimeRradingCount(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			Integer count = phoneCheckUnitDealDataService.findRealTimeRradingCount((UserBean)userSession);
			
			json.setMsg("查询成功！");
			json.setObj(count); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询实时交易总量异常：" + e);
			json.setMsg("查询实时交易总量失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	
	//查询小票上传列表
	
	public void findReceiptsUploadData(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			DataGridBean dgb = phoneCheckUnitDealDataService.findReceiptsUpload(userSession,page,rows);
			
			json.setMsg("查询成功！");
			json.setObj(dgb); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询待上传小票异常：" + e);
			json.setMsg("查询待上传小票失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	//查询风控卡片上传列表     风险交易(app)
	
	public void findRiskReceiptsUploadData(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			DataGridBean dgb = phoneCheckUnitDealDataService.findRiskReceiptsUpload(userSession,page,rows);
			json.setMsg("查询成功！");
			json.setObj(dgb); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询待上传风控卡片异常：" + e);
			json.setMsg("查询待上传风控卡片失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	public void encFindRiskReceiptsUploadData(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			DataGridBean dgb = phoneCheckUnitDealDataService.findRiskReceiptsUpload(userSession,page,rows);
			List<Map<String,Object>> rows = (List<Map<String, Object>>) dgb.getRows();
			for (Map<String, Object> map : rows) {
				String cardPan = (String) map.get("CARDPAN");
				cardPan = HrtRSAUtil.encryptWithBase64(cardPan);
				map.put("CARDPAN", cardPan);
			}
			dgb.setRows(rows);
			json.setMsg("查询成功！");
			json.setObj(dgb); 
			json.setSuccess(true);
		} catch (Exception e) {
			log.error("查询待上传风控卡片异常：" + e);
			json.setMsg("查询待上传风控卡片失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	
}
