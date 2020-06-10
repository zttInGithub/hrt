package com.hrt.biz.bill.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.pagebean.MerchantTwoUploadBean;
import com.hrt.biz.bill.service.IMerchantTwoUploadService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 商户资料二次上传
 */
public class MerchantTwoUploadAction extends BaseAction implements ModelDriven<MerchantTwoUploadBean>{

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(MerchantTwoUploadAction.class);
	
	private MerchantTwoUploadBean merchantTwoUpload = new MerchantTwoUploadBean();
	
	private IMerchantTwoUploadService merchantTwoUploadService;
	
	@Override
	public MerchantTwoUploadBean getModel() {
		return merchantTwoUpload;
	}

	public IMerchantTwoUploadService getMerchantTwoUploadService() {
		return merchantTwoUploadService;
	}

	public void setMerchantTwoUploadService(IMerchantTwoUploadService merchantTwoUploadService) {
		this.merchantTwoUploadService = merchantTwoUploadService;
	}

	/**
	 * 查询商户二次上传信息
	 */
	public void listMerchantTwoUpload(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantTwoUploadService.queryMerchantTwoUpload(merchantTwoUpload, (UserBean)userSession);
		} catch (Exception e) {
			log.error("分页查询商户二次上传信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	/**
	 * 资料二次上传
	 */
	public  void addMerchantInfo(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			Integer i = merchantTwoUploadService.queryMerchant(merchantTwoUpload, ((UserBean)userSession));
			if(i==1){
				json.setSuccess(false);
				json.setMsg("已上传资料，请耐心等待审核结果");
				super.writeJson(json);
				return;
			}else if(i==2){
				json.setSuccess(false);
				json.setMsg("商户号错误，请重新填写");
				super.writeJson(json);
				return;
			}
			boolean imgflag=true;
			try {
				if(merchantTwoUpload.getLegalNameFile() != null && merchantTwoUpload.getLegalNameFile().length()>1024*512){
					json.setMsg("您上传的法人身份图片大于512KB，请重新添加！");
					imgflag=false;
				}if(merchantTwoUpload.getLegalName2File() != null && merchantTwoUpload.getLegalName2File().length()>1024*512){
					json.setMsg("您上传的法人身份图片大于512KB，请重新添加！");
					imgflag=false;
				}if(merchantTwoUpload.getBupLoadFile() != null && merchantTwoUpload.getBupLoadFile().length()>1024*512){
					json.setMsg("您上传的法人身份图片大于512KB，请重新添加！");
					imgflag=false;
				}if(merchantTwoUpload.getBigdealUpLoadFile() != null && merchantTwoUpload.getBigdealUpLoadFile().length()>1024*512){
					json.setMsg("您上传的法人身份图片大于512KB，请重新添加！");
					imgflag=false;
				}if(merchantTwoUpload.getLaborContractImgFile() != null && merchantTwoUpload.getLaborContractImgFile().length()>1024*512){
					json.setMsg("您上传的法人身份图片大于512KB，请重新添加！");
					imgflag=false;
				}if(imgflag){
					merchantTwoUploadService.saveMerchantTwoUpload(merchantTwoUpload, ((UserBean)userSession));
					json.setSuccess(true);
					json.setMsg("上传资料成功"); 
				}
			} catch (Exception e) {
				log.error("上传资料异常：" + e);
				json.setSuccess(false);
				json.setMsg("上传资料失败");
			}
		}
		super.writeJson(json);
	}
	/**
	 * 查看明细
	 */
	public void serachMerchantDetailed(){
		List<Object> list = merchantTwoUploadService.queryMerchantInfoDetailed(merchantTwoUpload.getBmtuid());
		super.writeJson(list);
	}
	/**
	 * 资料二次上传退回
	 */
	public  void updateMerchantInfoK(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				Integer i = merchantTwoUploadService.updateMerchantInfoK(merchantTwoUpload, ((UserBean)userSession));
				if(i==1){
					json.setSuccess(true);
					json.setMsg("退回成功"); 
				}else{
					json.setSuccess(false);
					json.setMsg("退回失败"); 
				}
			} catch (Exception e) {
				log.error("退回资料异常：" + e);
				json.setSuccess(false);
				json.setMsg("退回失败"); 
			}
		}
		super.writeJson(json);
	}
	/**
	 * 资料二次上传通过
	 */
	public  void updateMerchantInfoY(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				Integer i = merchantTwoUploadService.updateMerchantInfoY(merchantTwoUpload, ((UserBean)userSession));
				if(i==1){
					json.setSuccess(true);
					json.setMsg("通过成功"); 
				}else{
					json.setSuccess(false);
					json.setMsg("通过失败"); 
				}
			} catch (Exception e) {
				log.error("通过资料异常：" + e);
				json.setSuccess(false);
				json.setMsg("通过失败"); 
			}
		}
		super.writeJson(json);
	}
	/**
	 * 资料二次上传退回后再次修改修改
	 */
	public  void updateMerchantInfo(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			boolean imgflag=true;
			try {
				if(merchantTwoUpload.getLegalNameFile() != null && merchantTwoUpload.getLegalNameFile().length()>1024*512){
					json.setMsg("您上传的法人身份图片大于512KB，请重新添加！");
					imgflag=false;
				}if(merchantTwoUpload.getLegalName2File() != null && merchantTwoUpload.getLegalName2File().length()>1024*512){
					json.setMsg("您上传的法人身份图片大于512KB，请重新添加！");
					imgflag=false;
				}if(merchantTwoUpload.getBupLoadFile() != null && merchantTwoUpload.getBupLoadFile().length()>1024*512){
					json.setMsg("您上传的法人身份图片大于512KB，请重新添加！");
					imgflag=false;
				}if(merchantTwoUpload.getBigdealUpLoadFile() != null && merchantTwoUpload.getBigdealUpLoadFile().length()>1024*512){
					json.setMsg("您上传的法人身份图片大于512KB，请重新添加！");
					imgflag=false;
				}if(merchantTwoUpload.getLaborContractImgFile() != null && merchantTwoUpload.getLaborContractImgFile().length()>1024*512){
					json.setMsg("您上传的法人身份图片大于512KB，请重新添加！");
					imgflag=false;
				}if(imgflag){
					merchantTwoUploadService.updateMerchantInfo(merchantTwoUpload, ((UserBean)userSession));
					json.setSuccess(true);
					json.setMsg("修改资料成功"); 
				}
			} catch (Exception e) {
				log.error("修改资料异常：" + e);
				json.setSuccess(false);
				json.setMsg("修改资料失败");
			}
		}
		super.writeJson(json);
	}
	/**
	 * 图片清除
	 */
	public  void deleteUploadImg(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				Integer i = merchantTwoUploadService.deleteUploadImg(merchantTwoUpload, ((UserBean)userSession));
				if(i==1){
					json.setSuccess(true);
					json.setMsg("图片清除成功"); 
				}else{
					json.setSuccess(false);
					json.setMsg("图片清除失败"); 
				}
			} catch (Exception e) {
				log.error("通过资料异常：" + e);
				json.setSuccess(false);
				json.setMsg("图片清除失败"); 
			}
		}
		super.writeJson(json);
	}
}
