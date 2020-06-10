package com.hrt.biz.bill.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.hrt.biz.bill.entity.pagebean.MerchantThreeUploadBean;
import com.hrt.biz.bill.service.IMerchantThreeUploadService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.model.FileModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 银联云闪付资料
 */
public class MerchantThreeUploadAction extends BaseAction implements ModelDriven<MerchantThreeUploadBean>{

	private static final long serialVersionUID = 1L;
	
	private Log log = LogFactory.getLog(MerchantThreeUploadAction.class);
	
	private IMerchantThreeUploadService merchantThreeUploadService;
	
	private MerchantThreeUploadBean merchantThreeUploadBean=new MerchantThreeUploadBean();

	@Override
	public MerchantThreeUploadBean getModel() {
		return merchantThreeUploadBean;
	}

	public IMerchantThreeUploadService getMerchantThreeUploadService() {
		return merchantThreeUploadService;
	}

	public void setMerchantThreeUploadService(IMerchantThreeUploadService merchantThreeUploadService) {
		this.merchantThreeUploadService = merchantThreeUploadService;
	}
	
	//查看银联云闪付资料
	public void queryMerchantThreeUpload() {
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else {
			try {
				dgb = merchantThreeUploadService.queryMerchantThreeUpload(merchantThreeUploadBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("分页查询银联云闪付资料异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	//查看银联云闪付资料(审核)
	public void queryMerchantThreeUploadFor() {
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else {
			try {
				dgb = merchantThreeUploadService.queryMerchantThreeUploadFor(merchantThreeUploadBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("分页查询银联云闪付资料(审核)异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	//查看明细
	public void queryDetail() {
		List<Object> list = merchantThreeUploadService.queryDetail(merchantThreeUploadBean);
		super.writeJson(list);
	}
	
	//通过银联云闪付资料上传
	public void updateMerchantThreeUploadGo() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else {
			try {
				json = merchantThreeUploadService.updateMerchantThreeUploadGo(merchantThreeUploadBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("通过银联云闪付资料上传异常：" + e);
				json.setSuccess(false);
				json.setMsg("通过失败");
			}
		}
		super.writeJson(json);
	}
	
	//退回银联云闪付资料上传
	public void updateMerchantThreeUploadBack() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null){
			return;
		}else {
			try {
				json = merchantThreeUploadService.updateMerchantThreeUploadBack(merchantThreeUploadBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("退回银联云闪付资料上传异常：" + e);
				json.setSuccess(false);
				json.setMsg("退回失败");
			}
		}
		super.writeJson(json);
	}
	
	//下载视频
	public void downloadVideo() {
		try {
			String realPath= merchantThreeUploadBean.getMerUpload8();
			String filename = new String((merchantThreeUploadBean.getMid()+merchantThreeUploadBean.getMerUpload8().substring(merchantThreeUploadBean.getMerUpload8().lastIndexOf(".")).toLowerCase()).getBytes("gb2312"), "ISO-8859-1"); // 该行代码用来解决下载的文件名是乱码
			File file = new File(realPath);
			byte a[] = new byte[1024 * 1024];
			FileInputStream fin = new FileInputStream(file);
			OutputStream outs = ServletActionContext.getResponse().getOutputStream();
			ServletActionContext.getResponse().setHeader("Content-Disposition","attachment; filename=" + filename);
			ServletActionContext.getResponse().setContentType("application/x-msdownload;charset=utf-8");
			ServletActionContext.getResponse().flushBuffer();

			int read = 0;
			while ((read = fin.read(a)) != -1) {
				outs.write(a, 0, read);
			}
			outs.close();
			fin.close();
		} catch (Exception e) {
			log.error("下载视频异常：" + e);
			try {
				ServletActionContext.getResponse().setContentType("text/html;charset=GB2312"); 
				ServletActionContext.getRequest().setCharacterEncoding("utf-8");
				ServletActionContext.getResponse().setCharacterEncoding("utf-8");
				ServletActionContext.getResponse().getWriter().println("<script>alert('该文件不存在');history.go(-1);</script>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
