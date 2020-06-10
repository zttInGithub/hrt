package com.hrt.biz.bill.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.pagebean.MerchantTaskDetail5Bean;
import com.hrt.biz.bill.service.IMerchantTaskDetail5Service;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 商户传统商户转T+0工单申请
 * @author xxx
 *
 */
public class MerchantTaskDetail5Action extends BaseAction implements ModelDriven<MerchantTaskDetail5Bean> {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(MerchantTaskDetail5Action.class);
	
	private MerchantTaskDetail5Bean merchantTaskDetail5Bean = new MerchantTaskDetail5Bean();
	private IMerchantTaskDetail5Service merchantTaskDetail5Service;
	
	@Override
	public MerchantTaskDetail5Bean getModel() {
		return merchantTaskDetail5Bean;
	}

	public IMerchantTaskDetail5Service getMerchantTaskDetail5Service() {
		return merchantTaskDetail5Service;
	}

	public void setMerchantTaskDetail5Service(IMerchantTaskDetail5Service merchantTaskDetail5Service) {
		this.merchantTaskDetail5Service = merchantTaskDetail5Service;
	}
	
	public void addMerchantTaskDetail5Data(){
		JsonBean json = new JsonBean();
		Object user = super.getRequest().getSession().getAttribute("user");
		try {
			if(merchantTaskDetail5Bean.getLegalPositiveFile()!=null && merchantTaskDetail5Bean.getLegalHandFile()!=null && merchantTaskDetail5Bean.getLegalReverseFile()!=null){
				merchantTaskDetail5Service.saveMerchantTaskDetail5Data(merchantTaskDetail5Bean,(UserBean)user);
				json.setSuccess(true);
				json.setMsg("实名认证申请成功！");
			}else{
				json.setSuccess(false);
				json.setMsg("照片数量不完整!");
			}

		} catch (Exception e) {
			log.error(e);
			json.setMsg("实名认证申请异常！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	/**
	 * 查询商户审批状态
	 */
	public void queryChangeT0Status(){
		JsonBean json = new JsonBean();
		if(merchantTaskDetail5Bean.getMid()!=null && !"".equals(merchantTaskDetail5Bean.getMid())){
			boolean flag=merchantTaskDetail5Service.findMidInfo(merchantTaskDetail5Bean.getMid());
			if(flag){
				boolean flag2=merchantTaskDetail5Service.queryMerchantIsMicro(merchantTaskDetail5Bean.getMid());
				if(flag2){
					List<Map<String,Object>> list =merchantTaskDetail5Service.findMidStatusInfo(merchantTaskDetail5Bean.getMid());
					json.setObj(list);
					json.setMsg("查询成功!");
					json.setSuccess(true);
				}else{
					json.setMsg("非传统商户，此功能不可使用！");
					json.setSuccess(false);
				}

			}else{
				json.setMsg("MID不存在！");
				json.setSuccess(false);
			}
		}else{
			json.setMsg("上传MID参数为空！");
			json.setSuccess(false);
		}

		super.writeJson(json);
	}

}
