package com.hrt.phone.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.phone.entity.pagebean.GateOrderBean;
import com.hrt.phone.service.GateCarOrderService;
import com.hrt.phone.service.GateCarService;
import com.opensymphony.xwork2.ModelDriven;

public class GateCarOrderAction extends BaseAction implements ModelDriven<GateOrderBean>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(GateViolationAction.class);
	GateOrderBean gateOrderBean =  new  GateOrderBean();
	private GateCarService gateCarService;
	GateCarOrderService gateCarOrderService;
	
	public GateCarService getGateCarService() {
		return gateCarService;
	}
	public void setGateCarService(GateCarService gateCarService) {
		this.gateCarService = gateCarService;
	}
	public void setGateCarOrderService(GateCarOrderService gateCarOrderService) {
		this.gateCarOrderService = gateCarOrderService;
	}
	public GateCarOrderService getGateCarOrderService() {
		return gateCarOrderService;
	}
	
	public void setGateOrderBean(GateOrderBean gateOrderBean) {
		this.gateOrderBean = gateOrderBean;
	}
	@Override
	public GateOrderBean getModel() {
		return gateOrderBean;
	}
	/**
	 * 获取订单列表
	 */
	public void getOrderState() {
		JsonBean jsonBean = new JsonBean();
		try{
			List<Map<String, String>>list = gateCarOrderService.queryOrdersDetailBySql(gateOrderBean);
			jsonBean.setObj(list);
			jsonBean.setSuccess(true);
			jsonBean.setMsg("查询成功");
		}catch (Exception e) {
			log.info("车辆违章订单获取(异常)"+e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("查询失败!");
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * app下单
	 */
	public void saveAppOrdersDetail() {
		
		JsonBean jsonBean = new JsonBean();
		if(gateOrderBean.getUniqueCode()==null||gateOrderBean.getMid()==null||gateOrderBean.getPriceId()==null){
			jsonBean.setMsg("下单参数不全!");
			jsonBean.setSuccess(false);
		}else{
			try{
				String hrtOrderId = gateCarOrderService.saveAppOrdersDetail(gateOrderBean);
				if(hrtOrderId!=null){
					jsonBean.setMsg("下单成功");
					jsonBean.setSuccess(true);
					jsonBean.setObj(hrtOrderId);
				}else{
					jsonBean.setMsg("下单失败");
					jsonBean.setSuccess(false);
				}
			}catch (Exception e) {
				log.info("车辆违章下单(异常)"+e);
				jsonBean.setMsg("下单失败!");
				jsonBean.setSuccess(false);
			}
		}
        super.writeJson(jsonBean);
	}
	
	/**
	 * app支付通知&三方下单
	 */
	public void appOrdersNotification() {
		
		JsonBean jsonBean = new JsonBean();
		if(gateOrderBean.getHrtOrderId()==null||gateOrderBean.getState()==null||gateOrderBean.getPayOrderId()==null){
			jsonBean.setMsg("app支付通知参数不全!");
			jsonBean.setSuccess(false);
		}else{
			try{
				//app支付通知//三方下单
				Map<String, String> map = gateCarOrderService.saveOrdersDetail(gateOrderBean);
				jsonBean.setMsg(map.get("msg"));
				jsonBean.setSuccess(true);
			}catch (Exception e) {
				log.info("车辆违章app支付通知(异常)"+e);
				jsonBean.setMsg("通知失败!");
				jsonBean.setSuccess(false);
			}
		}
        super.writeJson(jsonBean);
	}
	
	/**
	 * 三方异步通知
	 */
	public void ordersNotification() {

		HttpServletRequest request =super.getRequest();
		JsonBean json = new JsonBean();
		try {
			request.setCharacterEncoding("UTF-8");
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
			String line = "";
			StringBuffer buf = new StringBuffer();
			while((line=br.readLine())!=null){
				buf.append(line);
			}
			log.info("三方异步通知接收:"+buf);
			boolean flag = gateCarOrderService.updateOrdersNotification(JSONObject.parseObject(buf.toString()));
			json.setSuccess(flag);
		} catch (Exception e) {
			log.info("三方异步通知接受(异常)"+e);
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	/**
	 * app查询订单
	 */
	public void queryOrders() {
		
		JsonBean jsonBean = new JsonBean();
		DataGridBean dgb = new DataGridBean();
		if(gateOrderBean.getMid()==null){
			jsonBean.setMsg("app查询订单参数不全!");
			jsonBean.setSuccess(false);
		}else{
			try{
				dgb = gateCarOrderService.queryOrders(gateOrderBean);
				jsonBean.setNumberUnits(dgb.getTotal()+"");
				jsonBean.setObj(dgb.getRows());
				jsonBean.setMsg("查询订单成功");
				jsonBean.setSuccess(true);
			}catch (Exception e) {
				log.info("app查询订单(异常)"+e);
				jsonBean.setMsg("查询订单失败!");
				jsonBean.setSuccess(false);
			}
		}
        super.writeJson(jsonBean);
	}

	/**
	 * 订单补款下单
	 */
	public void addOrderAmount() {
		JsonBean jsonBean = new JsonBean();
		if(gateOrderBean.getMid()==null||gateOrderBean.getHrtOrderId()==null||gateOrderBean.getUniqueCode()==null){
			jsonBean.setMsg("补款下单参数不全!");
			jsonBean.setSuccess(false);
		}else{
			try{
				String hrtOrderId = gateCarOrderService.saveOrderAmount(gateOrderBean);
				if(hrtOrderId!=null){
					jsonBean.setMsg("补款下单成功");
					jsonBean.setSuccess(true);
					jsonBean.setObj(hrtOrderId);
				}else{
					jsonBean.setMsg("补款下单失败");
					jsonBean.setSuccess(false);
				}
			}catch (Exception e) {
				log.info("车辆违章补款下单(异常)"+e);
				jsonBean.setMsg("补款下单失败!");
				jsonBean.setSuccess(false);
			}
		}
        super.writeJson(jsonBean);
	}
	
	/**
	 * app补款支付通知&三方下单
	 */
	public void appOrderAmountNotification() {
		
		JsonBean jsonBean = new JsonBean();
		if(gateOrderBean.getHrtOrderId()==null||gateOrderBean.getState()==null||gateOrderBean.getPayOrderId()==null||gateOrderBean.getMid()==null||gateOrderBean.getUniqueCode()==null){
			jsonBean.setMsg("app补款支付通知参数不全!");
			jsonBean.setSuccess(false);
		}else{
			try{
				//app补款支付通知//三方补款
				Map<String, String> map = gateCarOrderService.updateOrderDetail(gateOrderBean);
				jsonBean.setMsg(map.get("msg"));
				jsonBean.setSuccess(true);
			}catch (Exception e) {
				log.info("车辆违章app补款支付通知(异常)"+e);
				jsonBean.setMsg("通知失败!");
				jsonBean.setSuccess(false);
			}
		}
        super.writeJson(jsonBean);
	}
	
	/**
	 * app退款
	 */
	public void appOrderCancel() {
		
		JsonBean jsonBean = new JsonBean();
		if(gateOrderBean.getHrtOrderId()==null||gateOrderBean.getMid()==null||gateOrderBean.getPayOrderId()==null||gateOrderBean.getUniqueCode()==null){
			jsonBean.setMsg("app退款参数不全!");
			jsonBean.setSuccess(false);
		}else{
			try{
				//先发送三方退款接口
				Map<String, String> map = gateCarOrderService.updateOrderCancel(gateOrderBean);
				jsonBean.setMsg(map.get("msg"));
				jsonBean.setSuccess(true);
			}catch (Exception e) {
				log.info("车辆违章app退款(异常)"+e);
				jsonBean.setMsg("退款失败!");
				jsonBean.setSuccess(false);
			}
		}
        super.writeJson(jsonBean);
	}
}
