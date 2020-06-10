package com.hrt.phone.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.phone.entity.model.GateViolationModel;
import com.hrt.phone.entity.pagebean.GateViolationBean;
import com.hrt.phone.service.GateCarService;
import com.hrt.phone.service.GateViolationService;
import com.opensymphony.xwork2.ModelDriven;

public class GateViolationAction extends BaseAction implements ModelDriven<GateViolationBean> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GateViolationBean gateViolationBean = new GateViolationBean();
	private static final Log log = LogFactory.getLog(GateViolationAction.class);
	public GateViolationBean getGateViolationBean() {
		return gateViolationBean;
	}
	private GateViolationService gateViolationService;
	private GateCarService gateCarService;
	public void setGateViolationService(GateViolationService gateViolationService) {
		this.gateViolationService = gateViolationService;
	}
	
	public GateCarService getGateCarService() {
		return gateCarService;
	}

	public void setGateCarService(GateCarService gateCarService) {
		this.gateCarService = gateCarService;
	}

	public GateViolationService getGateViolationService() {
		return gateViolationService;
	}
	@Override
	public GateViolationBean getModel() {
		return gateViolationBean;
	}
	
	/**
	 * 违章查询、办理报价
	 */
	public void existingViolationPrice(){
		JsonBean jsonBean = new JsonBean();
		boolean flag=true;
		String msg="查询成功!";
		Integer leftTimes =0;
		String ifImg;
		Integer i = 0;
		try{
			//1.查询该辆车当天是否已查询过违章记录
			List<GateViolationModel> list = gateViolationService.queryViolationInfobyNumber(gateViolationBean);
			//2.查询该车是否需要上传资料
			ifImg = gateCarService.queryIfUploadPic(gateViolationBean.getCarNumber(),gateViolationBean.getMid());
			//查询mid当天次数
			leftTimes = gateCarService.queryLeftTimesByMid(gateViolationBean.getMid());
			//保存车辆信息
			Integer carid = gateViolationService.saveGateCarIfFrist(gateViolationBean,leftTimes);
	        if (list!=null&&list.size()>0) {
			}else {
				if (carid!=null&&leftTimes>0){
					gateViolationBean.setCarId(carid);
					//查询三方
					JsonBean json = gateViolationService.saveViolationInfobySANFANG(gateViolationBean);
					list = json.getList();
					if(list==null||list.size()==0){
						msg=json.getMsg();
						flag=false;
					}
					//修改用户剩余查询次数
					i = gateViolationService.updateQueryTimes(gateViolationBean.getMid());
				}
			}
	        if(list.size()==0&&carid!=null&&leftTimes<=0){
				leftTimes=0;
				flag=false;
				msg="查询次数已经超过限制次数!";
				log.info(gateViolationBean.getMid()+"查询次数已经超过限制次数! "+leftTimes );
			}
	        if(i!=0){
				leftTimes--;
				log.info(gateViolationBean.getMid()+"违章查询次数递减 ,剩余次数:"+leftTimes );
			}
	        jsonBean.setSuccess(flag);
	        jsonBean.setMsg(msg);
	        jsonBean.setObj(list);
	        jsonBean.setNumberUnits(ifImg);
	        jsonBean.setLeftTimes(leftTimes);
			System.out.println(leftTimes);
		}catch (Exception e) {
			log.info("违章查询、办理报价action(异常)"+e);
			jsonBean.setMsg("查询失败!");
        	jsonBean.setSuccess(false);
		}
       super.writeJson(jsonBean);
	}
	
}
