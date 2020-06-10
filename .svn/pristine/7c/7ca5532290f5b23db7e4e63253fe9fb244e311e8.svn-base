package com.hrt.phone.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.phone.entity.model.GateViolationModel;
import com.hrt.phone.entity.pagebean.GateCarBean;
import com.hrt.phone.service.GateCarOrderService;
import com.hrt.phone.service.GateCarService;
import com.hrt.phone.service.GateViolationService;
import com.opensymphony.xwork2.ModelDriven;

public class GateCarAction extends BaseAction implements ModelDriven<GateCarBean> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(GateViolationAction.class);
	private GateCarService gateCarService;
	private GateCarOrderService gateCarOrderService;;
	GateCarBean gateCarBean =  new GateCarBean();
	
	public GateCarOrderService getGateCarOrderService() {
		return gateCarOrderService;
	}
	public void setGateCarOrderService(GateCarOrderService gateCarOrderService) {
		this.gateCarOrderService = gateCarOrderService;
	}
	public void setGateCarBean(GateCarBean gateCarBean) {
		this.gateCarBean = gateCarBean;
	}
	public GateCarBean getModel() {
		return gateCarBean;
	}
	public void setGateCarService(GateCarService gateCarService) {
		this.gateCarService = gateCarService;
	}
	public GateCarService getGateCarService() {
		return gateCarService;
	}
	GateViolationService gateViolationService;
	public void setGateViolationService(GateViolationService gateViolationService) {
		this.gateViolationService = gateViolationService;
	}
	public GateViolationService getGateViolationService() {
		return gateViolationService;
	}
	
	public List<GateViolationModel> getGateViolationModels(String mid) {
		return null;
	}
	public void login(){
		String token = gateCarService.login();
		System.out.println(token);
	}
	
	//查询支持的省市,以及车架号、发动机号所需要输入的长度
	public void getSupportCities() {
		JsonBean jsonBean = new JsonBean();
		try {
			List<Map<String, Object>>list = gateCarService.querySupportCities();
			jsonBean.setSuccess(true);
			jsonBean.setObj(list);
			jsonBean.setMsg("查询支持省市信息成功");
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("查询支持省市信息失败");
			log.info("查询支持省市信息(异常)："+e);
		}
		super.writeJson(jsonBean);
	}
	
	
	//APP获取车辆列表
	public void getCarInfo() {
		JsonBean jsonBean = new JsonBean();
		try {
			List<GateCarBean> list = gateCarService.queryCarInfo(gateCarBean);
			Integer leftTimes = gateCarService.queryLeftTimesByMid(gateCarBean.getMid());
			jsonBean.setLeftTimes(leftTimes);
			jsonBean.setSuccess(true);
			jsonBean.setMsg("查询成功!");
			jsonBean.setObj(list);
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("查询失败!");
			log.info("用户获取车辆列表(异常)："+e);
		}
		super.writeJson(jsonBean);
	}
	
	//App修改车辆信息及订单补资料
	public void updateCarInfo() {
		JsonBean jsonBean = new JsonBean();
		try {
			gateCarService.updateGateCarInfo(gateCarBean);
			//判断是否补交资料到三方
			if("1".equals(gateCarBean.getStatus())){
				Integer i = gateCarOrderService.updateGateCarInfo(gateCarBean.getMid(),gateCarBean.getOrderId(),gateCarBean.getImgId());
				if(i==0){//失败
					jsonBean.setMsg("数据有误，补充资料失败!");
					jsonBean.setSuccess(false);
					super.writeJson(jsonBean);
				}
				jsonBean.setMsg("补充资料成功");
				jsonBean.setSuccess(true);
				super.writeJson(jsonBean);
			}
			jsonBean.setSuccess(true);
			jsonBean.setMsg("车辆信息修改成功!");
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("车辆信息修改失败!");
			log.info("车辆信息修改(异常)"+e);
		}
		super.writeJson(jsonBean);
	}
	
	/**
	 * 删除车辆信息
	 */
	public void deleteCarInfo() {
		JsonBean jsonBean =  new JsonBean();
		try {
			gateCarService.deleteCarInfo(gateCarBean.getCarId());
			jsonBean.setSuccess(true);
			jsonBean.setMsg("删除成功!");
		} catch (Exception e) {
			jsonBean.setSuccess(false);
			jsonBean.setMsg("删除失败!");
		}
		super.writeJson(jsonBean);
	}
	
	public void showImage() {
		HttpServletResponse response = super.getResponse();
		try {
			FileInputStream fileInputStream =   new FileInputStream(new File(gateCarBean.getImage()));
			ServletOutputStream outputStream = response.getOutputStream();
			byte [] buffer = new byte[1024];
			int len = 0;
			while((len = fileInputStream.read(buffer))>0){
				outputStream.write(buffer,0,len);
			} 
			fileInputStream.close();
			outputStream.close();
		} catch (IOException e1) {
//			e1.printStackTrace();
			log.info("查看车辆图片信息异常"+e1);
		}
	}
}
