package com.hrt.biz.bill.action;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.PageData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.entity.pagebean.MIDSeqInfoBean;
import com.hrt.biz.bill.entity.pagebean.MerchantInfoBean;
import com.hrt.biz.bill.entity.pagebean.QRInvitationInfoBean;
import com.hrt.biz.bill.service.IMIDSeqInfoService;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.bill.service.IQRInvitationInfoService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 手刷商户二维码注册码邀请信息
 * @author tenglong
 *
 */
public class QRInvitationInfoAction extends BaseAction implements ModelDriven<QRInvitationInfoBean>{

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(QRInvitationInfoAction.class);
	
	private QRInvitationInfoBean qrInvitationInfoBean = new QRInvitationInfoBean();
	
	private IQRInvitationInfoService infoService;
	private IMerchantInfoService merchantInfoService;

	@Override
	public QRInvitationInfoBean getModel() {
		return qrInvitationInfoBean;
	}

	public IQRInvitationInfoService getInfoService() {
		return infoService;
	}

	public void setInfoService(IQRInvitationInfoService infoService) {
		this.infoService = infoService;
	}

	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}
	
	/**
	 * 生产邀请码
	 */
	public void addMerQRInvitationInfo(){
		JsonBean json = new JsonBean();
		UserBean user = (UserBean)super.getRequest().getSession().getAttribute("user");
		Integer result = 0;
		try{
			if(Integer.parseInt(qrInvitationInfoBean.getQrnum())<=5000){
				result = infoService.addQRInvitationStatus(qrInvitationInfoBean,user);
				if(result==0){
					json.setSuccess(false);
					json.setMsg("机构号不存在");
				}else{
					json.setSuccess(true);
					json.setMsg("生产"+result+"条邀请码");
				}
			}else{
				json.setSuccess(false);
				json.setMsg("生成数量请填写5000之内！");
			}
		}catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("生产邀请码失败");
			log.info("生产邀请码异常"+e);
		}
		super.writeJson(json);
	}
	
	/**
	 * 更新邀请码
	 */
	public void updateQRInvitationInfo(){
		JsonBean json = new JsonBean();
		Integer result=01;
		if(qrInvitationInfoBean.getIcPassword()!=null&&!"".equals(qrInvitationInfoBean.getIcPassword())&&
				qrInvitationInfoBean.getInvitationCode()!=null&&!"".equals(qrInvitationInfoBean.getInvitationCode())&&
				qrInvitationInfoBean.getMid()!=null&&!"".equals(qrInvitationInfoBean.getMid())&&
				qrInvitationInfoBean.getBankAccName()!=null&&!"".equals(qrInvitationInfoBean.getBankAccName())&&
				qrInvitationInfoBean.getBankAccNum()!=null&&!"".equals(qrInvitationInfoBean.getBankAccNum())){
			try{
				result = infoService.updateQRInvitationStatus(qrInvitationInfoBean);
				if(result == 02){
					DataGridBean dgb = merchantInfoService.queryMerchantInfoMid(qrInvitationInfoBean.getMid());
					if(dgb!=null&&dgb.getRows().size()>0){
						MerchantInfoBean merchantInfoBean=(MerchantInfoBean) dgb.getRows().get(0);
						json.setMsg("注册成功");
						json.setSuccess(true);
						json.setObj(merchantInfoBean.getQrUrl());
					}
				}else if(result == 01){
					json.setMsg("密码错误");
					json.setSuccess(false);
				}else if(result == 00){
					json.setMsg("入账信息错误");
					json.setSuccess(false);
				}else if(result == 03){
					json.setMsg("邀请码已被使用");
					json.setSuccess(false);
				}
			}catch (Exception e) {
				log.info("手刷商户注册邀请码异常:"+e);
				json.setMsg("注册异常");
				json.setSuccess(false);
			}
		}else{
			json.setMsg("请填全信息");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	/**
	 * 查询所有
	 * 
	 */
	public void queryMerQRInvitationInfo(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try{
			dgb = infoService.queryMerQRInvitationInfo(qrInvitationInfoBean,((UserBean)userSession));
		}catch (Exception e) {
			log.info("手刷商户注册邀请码异常:"+e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 分配邀请码
	 */
	public void updateQRIUnno(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		String icids = super.getRequest().getParameter("icids");
		String unno = super.getRequest().getParameter("UNNO");
		if(unno==null||"".equals(unno)){
			json.setSuccess(false);
			json.setMsg("请正确选择机构号");
			super.writeJson(json);
			return ;
		}
		qrInvitationInfoBean.setUnno(unno);
		Integer result = 0 ;
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			String qrs[]=icids.split(",");
			try{
				result = infoService.updateQRIUnno(qrInvitationInfoBean,icids,(UserBean)userSession);
				if(result>0){
					json.setSuccess(true);
					json.setMsg("成功分配给机构号:"+qrInvitationInfoBean.getUnno()+"-"+result+"个邀请码");
				}else{
					json.setSuccess(false);
					json.setMsg("请正确选择机构号");
				}
			}catch (Exception e) {
				log.info("分配邀请码异常"+e);
				json.setSuccess(false);
				json.setMsg("分配失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 导出所有
	 */
	public void exportMerQRAll(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=infoService.exportMerQRAll(qrInvitationInfoBean,((UserBean)userSession));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("邀请码资料导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("邀请码资料导出Excel成功");
			} catch (Exception e) {
				log.error("邀请码资料导出Excel异常：" + e);
				json.setMsg("邀请码资料导出Excel失败");
			}
		}
	}
	
}
