package com.hrt.biz.bill.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hrt.biz.bill.entity.pagebean.MerAdjustRateBean;
import com.hrt.biz.bill.service.IMerAdjustRateService;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.opensymphony.xwork2.ModelDriven;
	
/**
 * 聚合支付商户费率调整记录
 * @author tenglong
 */
public class MerAdjustRateAction extends BaseAction implements ModelDriven<MerAdjustRateBean> {
	
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(MerAdjustRateAction.class);

	private MerAdjustRateBean  adjustRateBean = new MerAdjustRateBean();

	@Override
	public MerAdjustRateBean getModel() {
		return adjustRateBean;
	}

	private IMerAdjustRateService merAdjustRateService;
	private IMerchantInfoService merchantInfoService;

	public MerAdjustRateBean getAdjustRateBean() {
		return adjustRateBean;
	}

	public void setAdjustRateBean(MerAdjustRateBean adjustRateBean) {
		this.adjustRateBean = adjustRateBean;
	}

	public IMerAdjustRateService getMerAdjustRateService() {
		return merAdjustRateService;
	}

	public void setMerAdjustRateService(IMerAdjustRateService merAdjustRateService) {
		this.merAdjustRateService = merAdjustRateService;
	}

	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}
	
	/**
	 * 申请变更
	 */
	public void updateHYBAdjustRateInfo(){
		JsonBean json = new JsonBean();
		Integer result = 0;
		try{
			if ("400000".equals(adjustRateBean.getSettMethod())&&adjustRateBean.getQuotaAmt()==null){
				json.setSuccess(false);
				json.setMsg("请填写'金额结算值'");
				super.writeJson(json);
				return ;
			}
			result = merAdjustRateService.updateHYBAdjustRateInfo(adjustRateBean);
			if(result==0){
				json.setSuccess(false);
				json.setMsg("商户不存在");
			}else if(result==1){
				json.setSuccess(true);
				json.setMsg("申请成功");
			}else if(result==3){
				json.setSuccess(false);
				json.setMsg("机构被停用");
			}else{
				json.setSuccess(false);
				json.setMsg("申请失败");
			}
		}catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("申请失败!");
			log.info("申请失败!"+e);
		}
		log.info("会员宝申请变更结算周期：mid="+adjustRateBean.getMid()+";settleCycle="+adjustRateBean.getSettleCycle()+";settMethod="+adjustRateBean.getSettMethod()+";" +
				"secondRate="+adjustRateBean.getSecondRate()+";scanRate="+adjustRateBean.getScanRate()+";result="+json.getMsg());
		super.writeJson(json);
	}
}
