package com.hrt.phone.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.util.CommonTools;
import org.apache.commons.io.CopyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.action.MerchantTaskDetail2Action;
import com.hrt.biz.bill.entity.model.MerchantTaskDataModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail2Model;
import com.hrt.biz.bill.entity.pagebean.MerchantInfoBean;
import com.hrt.biz.bill.entity.pagebean.MerchantTaskDataBean;
import com.hrt.biz.bill.service.IMerchantTaskDataService;
import com.hrt.biz.bill.service.IMerchantTaskDetail2Service;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.phone.service.IPhoneReceiptsUploadService;
import com.opensymphony.xwork2.ModelDriven;

public class PhoneReceiptsUploadAction extends BaseAction implements ModelDriven<MerchantTaskDetail2Model> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IPhoneReceiptsUploadService phoneReceiptsUploadService;
	
	private IMerchantTaskDetail2Service merchantTaskDetail2Service;
	
	private IMerchantTaskDataService merchantTaskDataService;
	
	private MerchantTaskDetail2Model merchantTaskDetail2 = new MerchantTaskDetail2Model();
	
	private static final Log log = LogFactory.getLog(PhoneReceiptsUploadAction.class);

	// 上传文件域
	private File image;
	private File image2;

	// 上传文件名
	private String imageFileName;
	//流水号
	private String stan;
	//卡号
	private String cardPan;
	//主键ID
	private Integer pkid;
	//交易日期
	private String txnday;
	//系统参考号
	private String rrn;
	
	//工单图片
	private File dUpLoad;
	private String mid;

	public void addMerchantTaskDetail2DataV2(){
		// @author:lxg-20191114 敏感信息加密处理
		try {
			String data=getRequest().getParameter("data");
			String aesEn=getRequest().getParameter("aesEn");
			String sck= CommonTools.getSck(aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck,data);
			JSONObject jsonObject=JSON.parseObject(data0);
			if(jsonObject.containsKey("mid")){
				mid=jsonObject.getString("mid");
			}
			merchantTaskDetail2= JSONObject.parseObject(data0,MerchantTaskDetail2Model.class);
			merchantTaskDetail2.setSck(sck);
			merchantTaskDetail2.setEnc(true);
			log.error("addMerchantTaskDetail2DataV2解密后的请求参数:"+ JSON.toJSONString(merchantTaskDetail2));
			addMerchantTaskDetail2();
		} catch (Exception e) {
			log.error("addMerchantTaskDetail2DataV2解密请求出错:"+e.getMessage());
		}
	}

	/**
	 * 保存商户提交的银行信息工单申请 
	 */
	public void addMerchantTaskDetail2(){
		Map<String ,String> map=new HashMap<String ,String >();
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sfname=new SimpleDateFormat("mm");
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		String uploadPath=merchantTaskDetail2Service.queryUpLoadPath();
		Integer imgIsRight=0;
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
			json.setSuccess(false);
			json.setMsg("申请超时!请重新进入");
		} else {
			//判断是否存在待审批的工单
			boolean flag = merchantTaskDetail2Service.queryMerchantTaskDetailByMid(mid);
			if(flag){
				json.setSuccess(false);
				json.setMsg("已存在待审批的变更申请！请等待其审批后再次提交");
//						super.writeJson(json);
				// @author:lxg-20191114 敏感信息加密处理
				super.writeJson(CommonTools.jsonBeanToString(json,merchantTaskDetail2.isEnc(),merchantTaskDetail2.getSck()));
				return ;
			}
			UserBean user = (UserBean)userSession;
			map.put("3", user.getUnNo().trim() + "." + mid + "." + sf.format(new Date()) + "." + sfname.format(new Date()) + ".jpg");
			try {
				if(dUpLoad!=null || stan!=null){
					if(stan!=null){
						if (stan.getBytes().length > 1024 * 1024) {
							++imgIsRight;
						}else{
							merchantTaskDetail2Service.uploadFile2byteWeChat(stan,uploadPath + File.separator + sf.format(new Date()).trim(),map.get("3"));
							merchantTaskDetail2.setDUpLoad(map.get("3"));
						}
					}
					if(dUpLoad!=null) {
						FileInputStream fis = new FileInputStream(dUpLoad);
						if (fis.available() > 1024 * 1024) {
							++imgIsRight;
						} else {
							File file = new File(uploadPath + File.separator + sf.format(new Date()).trim());
							if (!file.exists()) {
								file.mkdirs();
							}
							FileOutputStream fos = new FileOutputStream(file + File.separator + map.get("3"));
							byte[] buffer = new byte[1024];
							int len = 0;
							while ((len = fis.read(buffer)) > 0) {
								fos.write(buffer, 0, len);
							}
							fos.close();
							merchantTaskDetail2.setDUpLoad(map.get("3"));
						}
						fis.close();
					}
					if(imgIsRight>0){
						json.setMsg("工单提交失败，你的图片大于512K！");
						json.setSuccess(false);
					}else{
						MerchantTaskDataModel m = merchantTaskDetail2Service.saveMerchantTaskDetail2(merchantTaskDetail2, user.getUnNo(), mid,user);
						try{
							MerchantTaskDataBean bean = new MerchantTaskDataBean();
							BeanUtils.copyProperties(m, bean);
							bean.setProcessContext("App提交");
							bean.setApproveStatus("Y");
							merchantTaskDataService.updateAuditSingle(bean,user);
						}catch(Exception e){
							log.error("app工单提交推送综合异常：" + e);
						}
						json.setSuccess(true);
						json.setMsg("工单提交成功");
					}
				}else{
					json.setSuccess(false);
					json.setMsg("请上传图片");
				}
			} catch (Exception e) {
				log.error("app工单提交异常：" + e);
				json.setSuccess(false);
				json.setMsg("工单提交失败");
			} 
		}
//		super.writeJson(json);
		// @author:lxg-20191114 敏感信息加密处理
		super.writeJson(CommonTools.jsonBeanToString(json,merchantTaskDetail2.isEnc(),merchantTaskDetail2.getSck()));
	}

	public void queryMerchantBankCardDataV2(){
		// @author:lxg-20191114 敏感信息加密处理
		try {
			String data=getRequest().getParameter("data");
			String aesEn=getRequest().getParameter("aesEn");
			String sck= CommonTools.getSck(aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck,data);
			super.getRequest().setAttribute("mid",JSON.parseObject(data0).getString("mid"));
			merchantTaskDetail2.setSck(sck);
			merchantTaskDetail2.setEnc(true);
			queryMerchantBankCard();
		} catch (Exception e) {
			log.error("syncMerchantInfoByPhoneToSpeedMdDataV2解密请求出错:"+e.getMessage());
		}
	}
	
	/**
	 * 秒到查询银行卡&工单审批状态
	 */
	public void queryMerchantBankCard(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		if(merchantTaskDetail2.isEnc()){
			mid=super.getRequest().getAttribute("mid").toString();
		}
		if(mid!=null && !"".equals(mid)){
			try {
				DataGridBean dgb=phoneReceiptsUploadService.queryMerchantBankCardData(mid);
				json.setSuccess(true);
				json.setMsg("查询成功！");
				json.setObj(dgb);
			} catch (Exception e) {
				log.info(e);
				json.setSuccess(false);
				json.setMsg("查询失败！");
			}
		}
//		super.writeJson(json);
		// @author:lxg-20191114 敏感信息加密处理
		super.writeJson(CommonTools.jsonBeanToString(json,merchantTaskDetail2.isEnc(),merchantTaskDetail2.getSck()));
	}

	public void queryMerchantTaskDetail2DataV2(){
		// @author:lxg-20191114 敏感信息加密处理
		try {
			String data=getRequest().getParameter("data");
			String aesEn=getRequest().getParameter("aesEn");
			String sck= CommonTools.getSck(aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck,data);
			super.getRequest().setAttribute("mid",JSON.parseObject(data0).getString("mid"));
			merchantTaskDetail2.setSck(sck);
			merchantTaskDetail2.setEnc(true);
			queryMerchantTaskDetail2();
		} catch (Exception e) {
			log.error("queryMerchantTaskDetail2DataV2解密请求出错:"+e.getMessage());
		}

	}
	
	/**
	 * 秒到查询工单
	 */
	public void queryMerchantTaskDetail2(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		if(null!=super.getRequest().getAttribute("mid")){
			mid=super.getRequest().getAttribute("mid").toString();
		}
		if(mid!=null && !"".equals(mid)){
			try {
				DataGridBean dgb=phoneReceiptsUploadService.queryMerchantTaskDetail2(mid);
				json.setSuccess(true);
				json.setMsg("查询成功！");
				json.setObj(dgb);
			} catch (Exception e) {
				log.info(e);
				json.setSuccess(false);
				json.setMsg("查询失败！");
			}
		}
//		super.writeJson(json);
		// @author:lxg-20191114 敏感信息加密处理
		super.writeJson(CommonTools.jsonBeanToString(json,merchantTaskDetail2.isEnc(),merchantTaskDetail2.getSck()));
	}
	
	/**
	 * 小票上传
	 */
	public void saveReceiptsImg() {
		JsonBean json = new JsonBean();
		FileOutputStream fos = null;
		FileInputStream fis = null;
		Object userSession = super.getRequest().getSession().getAttribute("user");
		String uploadPath=phoneReceiptsUploadService.queryUploadPath();
		if (getImage() != null && txnday !=null) {
			try {
				File file= new File(uploadPath+File.separator+txnday.trim()); 
				if(!file.exists()){
					file.mkdirs();
				}
				String parth=File.separator+txnday.trim()+File.separator+pkid+".jpg";
				fos = new FileOutputStream(uploadPath+File.separator+txnday.trim()+File.separator+pkid+".jpg");
				fis = new FileInputStream(getImage());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				boolean flag =phoneReceiptsUploadService.queryReceiptsUploadData(pkid);
				if(flag){
					phoneReceiptsUploadService.updateReceiptsUploadData((UserBean)userSession,pkid,parth,"");
					json.setMsg("上传成功！");
					json.setSuccess(true);
				}else{
					json.setMsg("审核(小票)状态已被修改，不可重复修改！");
					json.setSuccess(true);
				}
				
			} catch (Exception e) {
				json.setMsg("小票上传失败");
				json.setSuccess(false);
				e.printStackTrace();
			} finally {
				close(fos, fis);
			}
		} else {
			json.setMsg("无上传小票或此条交易无效！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	/**
	 * 补传风控交易卡片照片
	 */
	public void saveRiskReceiptsImg() {
		JsonBean json = new JsonBean();
		FileOutputStream fos = null;
		FileInputStream fis = null;
		FileOutputStream fos2 = null;
		FileInputStream fis2 = null;
		Object userSession = super.getRequest().getSession().getAttribute("user");
		String uploadPath=phoneReceiptsUploadService.queryRiskUploadPath();
		if (getImage() != null && txnday !=null) {
			try {
				File file= new File(uploadPath+File.separator+txnday.trim()); 
				if(!file.exists()){
					file.mkdirs();
				}
				String parth=File.separator+txnday.trim()+File.separator+pkid+".jpg";
				String parth2="";
				fos = new FileOutputStream(uploadPath+parth);
				fis = new FileInputStream(getImage());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				//传统pos的小票照片
				if(getImage2()!=null){
					File file2= new File(uploadPath+File.separator+txnday.trim()); 
					parth2=File.separator+txnday.trim()+File.separator+pkid+"_1.jpg";
					if(!file2.exists()){
						file2.mkdirs();
					}
					fos2 = new FileOutputStream(uploadPath+File.separator+txnday.trim()+File.separator+pkid+"_1.jpg");
					fis2 = new FileInputStream(getImage2());
					byte[] buffer2 = new byte[1024];
					int len2 = 0;
					while ((len2 = fis2.read(buffer2)) != -1) {
						fos2.write(buffer2, 0, len2);
					}
				}
				
				boolean flag =phoneReceiptsUploadService.queryReceiptsUploadData(pkid);
				if(flag){
					phoneReceiptsUploadService.updateReceiptsUploadData((UserBean)userSession,pkid,parth,parth2);
					json.setMsg("上传成功！");
					json.setSuccess(true);
				}else{
					json.setMsg("审核(风控交易卡片)状态已被修改，不可重复修改！");
					json.setSuccess(true);
				}
				
			} catch (Exception e) {
				json.setMsg("风控交易卡片上传失败");
				json.setSuccess(false);
				e.printStackTrace();
			} finally {
				close(fos, fis);
				close(fos2,fis2);
			}
		} else {
			json.setMsg("无上传风控交易卡片或此条交易无效！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	
	/**
	 * 上传风控交易卡片照片（交易完成之时上传）
	 */
	public void saveRiskReceiptsImg2() {
		JsonBean json = new JsonBean();
		FileOutputStream fos = null;
		FileInputStream fis = null;
		String uploadPath=phoneReceiptsUploadService.queryRiskUploadPath();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String day=sf.format(new Date());
		if (getImage() != null && rrn !=null) {
			try {
				File file= new File(uploadPath+File.separator+day.trim()); 
				if(!file.exists()){
					file.mkdirs();
				}
				String parth=File.separator+day.trim()+File.separator+rrn+".jpg";
				fos = new FileOutputStream(uploadPath+File.separator+day.trim()+File.separator+rrn+".jpg");
				fis = new FileInputStream(getImage());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				phoneReceiptsUploadService.saveReceiptsUploadData(rrn,parth);
				json.setMsg("照片上传成功！");
				json.setSuccess(true);
				
			} catch (Exception e) {
				json.setMsg("照片上传失败");
				json.setSuccess(false);
				e.printStackTrace();
			} finally {
				close(fos, fis);
			}
		} else {
			json.setMsg("无上传照片或此条交易无效！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	
/****************************************************************************/
	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public IPhoneReceiptsUploadService getPhoneReceiptsUploadService() {
		return phoneReceiptsUploadService;
	}

	public void setPhoneReceiptsUploadService(
			IPhoneReceiptsUploadService phoneReceiptsUploadService) {
		this.phoneReceiptsUploadService = phoneReceiptsUploadService;
	}

	public String getStan() {
		return stan;
	}

	public void setStan(String stan) {
		this.stan = stan;
	}

	public String getCardPan() {
		return cardPan;
	}

	public void setCardPan(String cardPan) {
		this.cardPan = cardPan;
	}

	public Integer getPkid() {
		return pkid;
	}

	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}

	public String getTxnday() {
		return txnday;
	}

	public void setTxnday(String txnday) {
		this.txnday = txnday;
	}

	public File getImage2() {
		return image2;
	}

	public void setImage2(File image2) {
		this.image2 = image2;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public IMerchantTaskDetail2Service getMerchantTaskDetail2Service() {
		return merchantTaskDetail2Service;
	}

	public void setMerchantTaskDetail2Service(
			IMerchantTaskDetail2Service merchantTaskDetail2Service) {
		this.merchantTaskDetail2Service = merchantTaskDetail2Service;
	}

	public MerchantTaskDetail2Model getMerchantTaskDetail2() {
		return merchantTaskDetail2;
	}

	public void setMerchantTaskDetail2(MerchantTaskDetail2Model merchantTaskDetail2) {
		this.merchantTaskDetail2 = merchantTaskDetail2;
	}

	public File getdUpLoad() {
		return dUpLoad;
	}

	public void setdUpLoad(File dUpLoad) {
		this.dUpLoad = dUpLoad;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}
	@Override
	public MerchantTaskDetail2Model getModel() {
		return merchantTaskDetail2;
	}

	public IMerchantTaskDataService getMerchantTaskDataService() {
		return merchantTaskDataService;
	}

	public void setMerchantTaskDataService(
			IMerchantTaskDataService merchantTaskDataService) {
		this.merchantTaskDataService = merchantTaskDataService;
	}

	private void close(FileOutputStream fos, FileInputStream fis) {
		if (fis != null) {
			try {
				fis.close();
				fis = null;
			} catch (IOException e) {
				System.out.println("FileInputStream关闭失败");
				e.printStackTrace();
			}
		}
		if (fos != null) {
			try {
				fos.close();
				fis = null;
			} catch (IOException e) {
				System.out.println("FileOutputStream关闭失败");
				e.printStackTrace();
			}
		}
	}

}
