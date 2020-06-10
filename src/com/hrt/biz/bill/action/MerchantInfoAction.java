package com.hrt.biz.bill.action;

import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import com.hrt.util.ExcelServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.hrt.biz.bill.entity.model.MerchantAuthenticityModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.pagebean.MerchantAuthenticityBean;
import com.hrt.biz.bill.entity.pagebean.MerchantInfoBean;
import com.hrt.biz.bill.service.IMerchantAuthenticityService;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.hrt.util.UsePioOutExcel;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 商户管理
 */
public class MerchantInfoAction extends BaseAction implements ModelDriven<MerchantInfoBean>{

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(MerchantInfoAction.class);
	
	private MerchantInfoBean merchantInfo = new MerchantInfoBean();
	
	private IMerchantInfoService merchantInfoService;
	
	private IMerchantAuthenticityService merchantAuthenticityService;
	
	private File upload;
	
	
	private String bmids;

	private String[] cellFormatType2;
	@Override
	public MerchantInfoBean getModel() {
		return merchantInfo;
	}

	public MerchantInfoBean getMerchantInfo() {
		return merchantInfo;
	}

	public void setMerchantInfo(MerchantInfoBean merchantInfo) {
		this.merchantInfo = merchantInfo;
	}

	public IMerchantAuthenticityService getMerchantAuthenticityService() {
		return merchantAuthenticityService;
	}

	public void setMerchantAuthenticityService(
			IMerchantAuthenticityService merchantAuthenticityService) {
		this.merchantAuthenticityService = merchantAuthenticityService;
	}

	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	public String getBmids() {
		return bmids;
	}

	public void setBmids(String bmids) {
		this.bmids = bmids;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	/**
	 * 查询出踢回和未开通的商户
	 */
	public void listMerchantInfoZK(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMerchantInfoZK(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	/**
	 * 查询出踢回和未开通的商户 新
	 */
	public void listMerchantInfoZKbaodan(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			merchantInfo.setIsM35(0);
			merchantInfo.setMaintainDate(new Date());
			dgb = merchantInfoService.queryMerchantInfoZK(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	
	/**
	 * 查询未认证商户（手录功能）
	 */
	public void listNoMerchant(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryNoMerchant(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询未商户信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 修改未认证商户为认证成功（手录认证）
	 */
	public void updateMerchantGoByMid(){
		JsonBean json = new JsonBean();
		String mid = super.getRequest().getParameter("mid");
		boolean flag = false ;
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			flag = merchantInfoService.updateMerchantGoByMid(mid,((UserBean)userSession));
			if(flag){
				Map<String, String> map =new HashMap<String, String>();
				MerchantAuthenticityBean matb =new MerchantAuthenticityBean();
				matb.setMid(merchantInfo.getMid());
				matb.setUsername(merchantInfo.getHybPhone());
				map.put("userName",matb.getUsername());
				map.put("mid",matb.getMid());
				map.put("msg","认证成功");
				map.put("status","2");
				json.setMsg("商户认证成功");
				merchantAuthenticityService.sendResultToHyb(map,matb);
			}else{
				json.setMsg("商户认证失败");
			}
		} catch (Exception e) {
			log.error("分页查询未商户信息异常：" + e);
			json.setMsg("商户认证失败");
		}
		json.setSuccess(flag);
		super.writeJson(json);
	}
	
	/**
	 * 认证失败统一查询
	 */
	public void listMerchantFalse(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMerchantFalse(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户认证信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 认证通过/回退认证失败统一查询
	 *  0未认证  1失败  2成功 3入账中 4入账失败
	 */
	public void MerchantAuthGoOrBack()throws Exception{
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		
		boolean is = false;
		String mid = super.getRequest().getParameter("mid");
		String bankAccNo = super.getRequest().getParameter("bankAccNo");
		//修改获得数据中文乱码
		//String bankAccName = new String(super.getRequest().getParameter("bankAccName").getBytes("ISO-8859-1"),"utf-8");
		String bankAccName = super.getRequest().getParameter("bankAccName");
		String legalNum = super.getRequest().getParameter("legalNum");
		String flag = super.getRequest().getParameter("flag");
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			if("go".equals(flag)){
				is = merchantInfoService.MerchantAuthGo(mid,bankAccNo,bankAccName,legalNum);
				if(is){
					MerchantAuthenticityBean  matb= new MerchantAuthenticityBean();
					Map<String, String> result= new HashMap<String, String>();
					MerchantAuthenticityModel authenticityModel = merchantAuthenticityService.queryMerchantAuthenticityByMid(mid);
					matb.setUsername(authenticityModel.getUsername());
					matb.setMid(authenticityModel.getMid());
					result.put("userName",matb.getUsername());
					result.put("mid",matb.getMid());
					result.put("msg","认证成功");
					result.put("status","2");
					merchantAuthenticityService.sendResultToHyb(result,matb);
					json.setSuccess(true);
					json.setMsg("认证通过成功");
				}else{
					json.setSuccess(false);
					json.setMsg("认证通过失败");
				}
			}else if("back".endsWith(flag)){
				is = merchantInfoService.MerchantAuthBack(mid,bankAccNo,bankAccName,legalNum);
				if(is){
					json.setSuccess(true);
					json.setMsg("认证回退成功");
				}else{
					json.setSuccess(false);
					json.setMsg("认证回退失败");
				}
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 提交结算
	 */
	public void export(){
		String bmids=getRequest().getParameter("searchids");
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				SimpleDateFormat format =new SimpleDateFormat("yyyyMMddhhmmss");
				//修改商户authtype为3，并提交
				merchantInfoService.updateMerAuthType(bmids);
				HSSFWorkbook wb=merchantInfoService.export(bmids);
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String(("提交结算"+format.format(new Date())).getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("提交结算,导出资料Excel成功");
			} catch (Exception e) {
				log.error("提交结算,导出资料Excel异常：" + e);
				json.setMsg("提交结算,导出资料Excel失败");
			}
		}
	}
	
	/**
	 * 添加商户
	 */
	public  void addMerchantInfo(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			boolean flag3=merchantInfoService.queryIsHotMerch(merchantInfo);
			if(flag3){
				String tt=merchantInfo.getBankProvinceCode()+merchantInfo.getBankName();
				if("".equals(merchantInfo.getBankSendCode())||"".equals(merchantInfo.getBankProvinceCode())||"".equals(merchantInfo.getBankName())){
					json.setMsg("请填写开户行信息！");
					json.setSuccess(false);
				}else if(tt.indexOf("青海")>=0 || tt.indexOf("西藏")>=0 || tt.indexOf("新疆")>=0 ){
					json.setMsg("不支持此开户行！");
					json.setSuccess(false);
				}else{
						boolean flag2=true;
						if(merchantInfo.getAreaCode()==null ||"".equals(merchantInfo.getAreaCode())){
							if(merchantInfo.getCity()!=null&&!"".equals(merchantInfo.getCity())&&merchantInfo.getIsM35()!=null){
								merchantInfo.setAreaCode(merchantInfo.getCity());
							}else{flag2=false;}
						}
						//判断页面传来的areacode是否是数字形式，不是则查出相应的数字格式
						if(!isNumeric(merchantInfo.getAreaCode().trim())){
							String aa=merchantInfoService.queryAreaCode(merchantInfo.getAreaCode());
							if(aa!=null && !"".equals(aa)){
								merchantInfo.setAreaCode(aa.trim());
							}else{
								flag2=false;
							}
						}
						if(flag2){
							boolean imgflag=true;
							try {
								if(merchantInfo.getLegalUploadFile() != null && merchantInfo.getLegalUploadFile().length()>1024*512){
									json.setMsg("您上传的法人身份图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getRegistryUpLoadFile() != null && merchantInfo.getRegistryUpLoadFile().length()>1024*512){
									json.setMsg("您上传的店内图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getBupLoadFile() != null && merchantInfo.getBupLoadFile().length()>1024*512){
									json.setMsg("您上传的营业执照图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoadFile() != null && merchantInfo.getMaterialUpLoadFile().length()>1024*512){ 
									json.setMsg("您上传的法人身份证反面图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getRupLoadFile() != null && merchantInfo.getRupLoadFile().length()>1024*512){
									json.setMsg("您上传的账户证明图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getPhotoUpLoadFile() != null && merchantInfo.getPhotoUpLoadFile().length()>1024*512){
									json.setMsg("您上传的门面照片图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getBigdealUpLoadFile() != null && merchantInfo.getBigdealUpLoadFile().length()>1024*512){
									json.setMsg("您上传的大协议照片图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoad1File() != null && merchantInfo.getMaterialUpLoad1File().length()>1024*512){
									json.setMsg("您上传的POS结算授权书图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoad2File() != null && merchantInfo.getMaterialUpLoad2File().length()>1024*512){
									json.setMsg("您上传的结算卡正面图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoad3File() != null && merchantInfo.getMaterialUpLoad3File().length()>1024*512){
									json.setMsg("您上传的入账人身份证正面图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoad4File() != null && merchantInfo.getMaterialUpLoad4File().length()>1024*512){
									json.setMsg("您上传的入账人身份证反面图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoad5File() != null && merchantInfo.getMaterialUpLoad5File().length()>1024*512){
									json.setMsg("您上传的入账人手持身份证图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoad6File() != null && merchantInfo.getMaterialUpLoad6File().length()>1024*512){
									json.setMsg("您上传的优惠类专用资质/减免类专用资质证明1图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoad7File() != null && merchantInfo.getMaterialUpLoad7File().length()>1024*512){
									json.setMsg("您上传的优惠类专用资质/减免类专用资质证明2图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getPosBackImgFile() != null && merchantInfo.getPosBackImgFile().length()>1024*512){
									json.setMsg("您上传的安装POS机背面图片大于512KB，请重新添加！");
									imgflag=false;
								}if(imgflag){
									boolean flag =merchantInfoService.searchMCCById(merchantInfo.getMccid());
									if(flag){
										MerchantInfoModel merchantInfoModel = merchantInfoService.saveMerchantInfo(merchantInfo, ((UserBean)userSession));
										merchantInfoService.uploadImage(merchantInfoModel, merchantInfo);
										json.setSuccess(true);
										json.setMsg("商户添加成功"); 
									}else{
										json.setSuccess(false);
										json.setMsg("行业编码不存在！");
									}
								}
							} catch (Exception e) {
								log.error("商户添加异常：" + e);
								json.setSuccess(false);
								json.setMsg("商户添加失败");
							}
						}else{
							json.setSuccess(false);
							json.setMsg("商户地区码/营业省市不存在！");
						}
					}
		}else{
			json.setSuccess(false);
			json.setMsg("该户在黑名单商户中，请核查！");
		}
	  }
		super.writeJson(json);
	}
	
	/**
	 * 添加商户
	 */
	public  void addMerchantInfobaodan(){
		JsonBean json = new JsonBean();
		Object user = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (user == null) {
			json.setSessionExpire(true);
		} else {
			UserBean userSession=(UserBean)user;
			boolean flag3=merchantInfoService.queryIsHotMerch(merchantInfo);
			if(flag3){
				String tt=merchantInfo.getBankProvinceCode()+merchantInfo.getBankName();
				if("".equals(merchantInfo.getBankSendCode())||"".equals(merchantInfo.getBankProvinceCode())||"".equals(merchantInfo.getBankName())){
					json.setMsg("请填写开户行信息！");
					json.setSuccess(false);
				}else if(tt.indexOf("青海")>=0 || tt.indexOf("西藏")>=0 || tt.indexOf("新疆")>=0 ){
					json.setMsg("不支持此开户行！");
					json.setSuccess(false);
				}else{
						boolean flag2=true;
						if(merchantInfo.getAreaCode()==null ||"".equals(merchantInfo.getAreaCode())){
							if(merchantInfo.getCity()!=null&&!"".equals(merchantInfo.getCity())&&merchantInfo.getIsM35()!=null){
								merchantInfo.setAreaCode(merchantInfo.getCity());
							}else{flag2=false;}
						}
						//判断页面传来的areacode是否是数字形式，不是则查出相应的数字格式
						if(!isNumeric(merchantInfo.getAreaCode().trim())){
							String aa=merchantInfoService.queryAreaCode(merchantInfo.getAreaCode());
							if(aa!=null && !"".equals(aa)){
								merchantInfo.setAreaCode(aa.trim());
							}else{
								flag2=false;
							}
						}
						if(flag2){
							boolean imgflag=true;
							try {
								if(merchantInfo.getLegalUploadFile() != null && merchantInfo.getLegalUploadFile().length()>1024*512){
									json.setMsg("您上传的法人身份图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getRegistryUpLoadFile() != null && merchantInfo.getRegistryUpLoadFile().length()>1024*512){
									json.setMsg("您上传的店内图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getBupLoadFile() != null && merchantInfo.getBupLoadFile().length()>1024*512){
									json.setMsg("您上传的营业执照图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoadFile() != null && merchantInfo.getMaterialUpLoadFile().length()>1024*512){ 
									json.setMsg("您上传的法人身份证反面图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getRupLoadFile() != null && merchantInfo.getRupLoadFile().length()>1024*512){
									json.setMsg("您上传的账户证明图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getPhotoUpLoadFile() != null && merchantInfo.getPhotoUpLoadFile().length()>1024*512){
									json.setMsg("您上传的门面照片图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getBigdealUpLoadFile() != null && merchantInfo.getBigdealUpLoadFile().length()>1024*512){
									json.setMsg("您上传的大协议照片图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoad1File() != null && merchantInfo.getMaterialUpLoad1File().length()>1024*512){
									json.setMsg("您上传的POS结算授权书图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoad2File() != null && merchantInfo.getMaterialUpLoad2File().length()>1024*512){
									json.setMsg("您上传的结算卡正面图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoad3File() != null && merchantInfo.getMaterialUpLoad3File().length()>1024*512){
									json.setMsg("您上传的入账人身份证正面图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoad4File() != null && merchantInfo.getMaterialUpLoad4File().length()>1024*512){
									json.setMsg("您上传的入账人身份证反面图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoad5File() != null && merchantInfo.getMaterialUpLoad5File().length()>1024*512){
									json.setMsg("您上传的入账人手持身份证图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoad6File() != null && merchantInfo.getMaterialUpLoad6File().length()>1024*512){
									json.setMsg("您上传的优惠类专用资质/减免类专用资质证明1图片大于512KB，请重新添加！");
									imgflag=false;
								}if(merchantInfo.getMaterialUpLoad7File() != null && merchantInfo.getMaterialUpLoad7File().length()>1024*512){
									json.setMsg("您上传的优惠类专用资质/减免类专用资质证明2图片大于512KB，请重新添加！");
									imgflag=false;
								}if(imgflag){
									boolean flag =merchantInfoService.searchMCCById(merchantInfo.getMccid());
									if(flag){
										Integer  i =merchantInfoService.saveUnnoInfobaodan(merchantInfo.getUnno(),merchantInfo.getParentMID(), userSession);
										if(i==2){
											json.setSuccess(false);
											json.setMsg("扩展名称不存在！");
										}else if (i==3){
											json.setSuccess(false);
											json.setMsg("扩展编号已存在！");
										}else{
											merchantInfo.setBusid(1);
											UserBean userSession2 = new UserBean();
											BeanUtils.copyProperties(userSession, userSession2);
											userSession2.setUnNo(merchantInfo.getUnno());
											merchantInfoService.saveMerchantInfobaodan(merchantInfo, userSession2);
											json.setSuccess(true);
											json.setMsg("商户添加成功"); 
										}
									}else{
										json.setSuccess(false);
										json.setMsg("行业编码不存在！");
									}
								}
							} catch (Exception e) {
								log.error("商户添加异常：" + e);
								json.setSuccess(false);
								json.setMsg("商户添加失败");
							}
						}else{
							json.setSuccess(false);
							json.setMsg("商户地区码/营业省市不存在！");
						}
					}
		}else{
			json.setSuccess(false);
			json.setMsg("该户在黑名单商户中，请核查！");
		}
	  }
		super.writeJson(json);
	}
	
	/**
	 * 查询可开通的商户
	 */
	public void listMerchantInfoWJoin(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMerchantInfoWJoin(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	/**
	 * 查询可开通的商户(会员宝立马富)
	 */
	public void listMerchantInfoWJoin2(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			merchantInfo.setUnno("'111006','112052'");
			dgb = merchantInfoService.queryMerchantInfoWJoin(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}

	public void queryUnnoCodeInfobaodan(){
		DataGridBean dgb = new DataGridBean();
		try {
			String nameCode = super.getRequest().getParameter("q");
			merchantInfo.setUnitName(nameCode);
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryUnnoCodeInfobaodan(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	/**
	 * 查询可开通的商户
	 */
	public void listMerchantInfoWJoin1(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if("".equals(merchantInfo.getMid())||merchantInfo.getMid()==null){
				merchantInfo.setMid("864");
			}else if(!merchantInfo.getMid().startsWith("864")){
				return ;
			}
			dgb = merchantInfoService.queryMerchantInfoWJoin1(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}

	/**
	 * 批量生成二维码
	 */
	public void updateMerchantWithQR(){
		JsonBean jsonBean = new JsonBean();
		String bmids=getRequest().getParameter("bmids");
		try{
			merchantInfoService.updateMerchantWithQR(bmids);
			jsonBean.setSuccess(true);
			jsonBean.setMsg("生成二维码成功");
		}catch (Exception e) {
			log.error("生成二维码异常：" + e);
			jsonBean.setSuccess(false);
			jsonBean.setMsg("生成二维码失败");
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 查询二维码url
	 */
	public void queryMerchanWithQRUrl(){
		JsonBean jsonBean = new JsonBean();
		String qrurl=null;
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			qrurl = merchantInfoService.queryMerchantWithQRUrl(merchantInfo,((UserBean)userSession));
			jsonBean.setMsg(qrurl);
		} catch (Exception e) {
			log.error("商户二维码url异常：" + e);
		}
		
		super.writeJson(jsonBean);
	}
	
	/**
	 * 开通
	 */
	public void updateMerchantInfoC(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			boolean flag2=true;
			if(merchantInfo.getSendCode()==null ||"".equals(merchantInfo.getSendCode()))
				flag2=false;
			//判断页面传来的sendcode是否是数字形式，不是则查出相应的数字格式
			if(!isNumeric(merchantInfo.getSendCode().trim())){
				String aa=merchantInfoService.querySendCode(merchantInfo.getSendCode());
				if(aa!=null && !"".equals(aa)){
					merchantInfo.setSendCode(aa);
				}else{
					flag2=false;
				}
			}
			if(flag2){
				try {
					String merchantInfoIds[]=bmids.split(",");
					for(String id :merchantInfoIds){
						MerchantInfoModel model=merchantInfoService.getInfoModel(Integer.parseInt(id));
						if("W".equals(model.getApproveStatus())){
							MerchantInfoModel m = merchantInfoService.updateMerchantInfoC(merchantInfo, ((UserBean)userSession), id);
							boolean f = merchantInfoService.updateMerchantInfoCToADM(m, ((UserBean)userSession), id);
							if(!f){
								json.setSuccess(false);
								json.setMsg("商户开通失败");
								super.writeJson(json);
								return ;
							}
						}
					}
					json.setSuccess(true);
					json.setMsg("商户开通成功");
				} catch (Exception e) {
					log.error("商户开通异常" + e);
					json.setMsg("商户开通失败");
				}
		  }else{
			    json.setSuccess(false);
				json.setMsg("商户地区码不存在！");
		  }
		}
		super.writeJson(json);
	}
	
	/**
	 * 聚合商户调整限额
	 */
	public void updateAggMerchantLimit(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
//				MerchantInfoModel m = merchantInfoService.updateMerchantInfoC(merchantInfo, ((UserBean)userSession), id);
				boolean f = merchantInfoService.updateAggMerchantLimitCToADM(merchantInfo, ((UserBean)userSession));
				if(!f){
					json.setSuccess(false);
					json.setMsg("限额调整失败");
					super.writeJson(json);
					return ;
				}
				json.setSuccess(true);
				json.setMsg("限额调整成功");
			} catch (Exception e) {
				log.error("限额调整异常" + e);
				json.setMsg("限额调整失败!");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 退回
	 */
	public void updateMerchantInfoK(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				int result = 0;
				String merchantInfoIds[]=bmids.split(",");
				for (int i = 0; i < merchantInfoIds.length; i++) {
					MerchantInfoModel model=merchantInfoService.getInfoModel(Integer.parseInt(merchantInfoIds[i]));
					if(!"W".equals(model.getApproveStatus())){
						result = result+1;
					}else{
						result = result;
					}
				}
				if(result==0){
					if(merchantInfo.getProcessContext().equals("其他")){
						merchantInfo.setProcessContext(merchantInfo.getProcessContext1());
					}
					merchantInfoService.updateMerchantInfoK(merchantInfo, ((UserBean)userSession), bmids);
					json.setSuccess(true);
					json.setMsg("商户退回成功");
				}else{
					json.setSuccess(false);
					json.setMsg("失败，该记录中有已被退回商户");
				}
			} catch (Exception e) {
				log.error("商户退回异常" + e);
				json.setMsg("商户退回失败");
			}
		}
		
		super.writeJson(json);
	}
	
	/**
	 * 聚合退回
	 */
	public void updateJHMerchantInfoK(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				if("2".equals(merchantInfo.getRemarks())){
					merchantInfo.setProcessContext(merchantInfo.getProcessContext1());
				}
				MerchantInfoModel model=merchantInfoService.getMerInfoByMidAll(merchantInfo.getMid());
				if(model==null||"K".equals(model.getApproveStatus())||"Y".equals(model.getApproveStatus())){
					json.setSuccess(false);
					json.setMsg("失败，商户已被他人处理");
				}else{
					merchantInfoService.updateMerchantInfoK(merchantInfo, ((UserBean)userSession), model.getBmid().toString());
					json.setSuccess(true);
					json.setMsg("商户退回成功");
				}
			} catch (Exception e) {
				log.error("商户退回异常" + e);
				json.setMsg("商户退回失败");
			}
		}
		super.writeJson(json);
	}

	/**
	 * 查询已开通的商户
	 */
	public void listMerchantInfoY(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMerchantInfoY(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	/**
	 * 查询已开通的商户传统&手刷
	 */
	public void listMerchantInfoY3(){
		DataGridBean dgb = new DataGridBean();
		try {
			UserBean userSession =(UserBean) super.getRequest().getSession().getAttribute("user");
			if("baodan0".equals(userSession.getLoginName())||(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals(""))||
					(merchantInfo.getTid() !=null && !merchantInfo.getTid().equals(""))){
				dgb = merchantInfoService.queryMerchantInfoY3(merchantInfo,userSession);
			}
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	/**
	 * 查询已开通的商户日志
	 */
	public void listMerchantInfoYLog(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.listMerchantInfoYLog(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	
	/**
	 * 查询已开通的商户（立马富）
	 */
	public void listMerchantInfoY1(){
		DataGridBean dgb = new DataGridBean();
		try {
			if(merchantInfo.getUnno()==null)merchantInfo.setUnno("112052");
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMerchantInfoY(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	
	/**
	 * 修改
	 */
	public void editMerchantInfo(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
//			if(merchantInfo.getAreaCode()==null ||"".equals(merchantInfo.getAreaCode())){
//				if(merchantInfo.getCity()!=null&&!"".equals(merchantInfo.getCity())&&merchantInfo.getIsM35()!=null)
//					merchantInfo.setAreaCode(merchantInfo.getCity());
//			}
			String tt=merchantInfo.getBankBranch();
			if(tt.indexOf("青海")>=0 || tt.indexOf("西藏")>=0 || tt.indexOf("新疆")>=0 ){
				json.setMsg("不支持此开户行！");
				json.setSuccess(false);
			}else{
				boolean imgflag =true;
				try {
					if(merchantInfo.getLegalUploadFile() != null && merchantInfo.getLegalUploadFile().length()>1024*512){
						json.setMsg("您上传的法人身份图片大于512KB，请重新添加！");
						imgflag=false;
					}if(merchantInfo.getRegistryUpLoadFile() != null && merchantInfo.getRegistryUpLoadFile().length()>1024*512){
						json.setMsg("您上传的店内图片大于512KB，请重新添加！");
						imgflag=false;
					}if(merchantInfo.getBupLoadFile() != null && merchantInfo.getBupLoadFile().length()>1024*512){
						json.setMsg("您上传的营业执照图片大于512KB，请重新添加！");
						imgflag=false;
					}if(merchantInfo.getMaterialUpLoadFile() != null && merchantInfo.getMaterialUpLoadFile().length()>1024*512){ 
						json.setMsg("您上传的法人身份证反面图片大于512KB，请重新添加！");
						imgflag=false;
					}if(merchantInfo.getRupLoadFile() != null && merchantInfo.getRupLoadFile().length()>1024*512){
						json.setMsg("您上传的账户证明图片大于512KB，请重新添加！");
						imgflag=false;
					}if(merchantInfo.getPhotoUpLoadFile() != null && merchantInfo.getPhotoUpLoadFile().length()>1024*512){
						json.setMsg("您上传的门面照片图片大于512KB，请重新添加！");
						imgflag=false;
					}if(merchantInfo.getBigdealUpLoadFile() != null && merchantInfo.getBigdealUpLoadFile().length()>1024*512){
						json.setMsg("您上传的大协议照片图片大于512KB，请重新添加！");
						imgflag=false;
					}if(merchantInfo.getMaterialUpLoad1File() != null && merchantInfo.getMaterialUpLoad1File().length()>1024*512){
						json.setMsg("您上传的POS结算授权书图片大于512KB，请重新添加！");
						imgflag=false;
					}if(merchantInfo.getMaterialUpLoad2File() != null && merchantInfo.getMaterialUpLoad2File().length()>1024*512){
						json.setMsg("您上传的结算卡正面图片大于512KB，请重新添加！");
						imgflag=false;
					}if(merchantInfo.getMaterialUpLoad3File() != null && merchantInfo.getMaterialUpLoad3File().length()>1024*512){
						json.setMsg("您上传的入账人身份证正面图片大于512KB，请重新添加！");
						imgflag=false;
					}if(merchantInfo.getMaterialUpLoad4File() != null && merchantInfo.getMaterialUpLoad4File().length()>1024*512){
						json.setMsg("您上传的入账人身份证反面图片大于512KB，请重新添加！");
						imgflag=false;
					}if(merchantInfo.getMaterialUpLoad5File() != null && merchantInfo.getMaterialUpLoad5File().length()>1024*512){
						json.setMsg("您上传的入账人手持身份证图片大于512KB，请重新添加！");
						imgflag=false;
					}if(merchantInfo.getMaterialUpLoad6File() != null && merchantInfo.getMaterialUpLoad6File().length()>1024*512){
						json.setMsg("您上传的优惠类专用资质/减免类专用资质证明1图片大于512KB，请重新添加！");
						imgflag=false;
					}if(merchantInfo.getMaterialUpLoad7File() != null && merchantInfo.getMaterialUpLoad7File().length()>1024*512){
						json.setMsg("您上传的优惠类专用资质/减免类专用资质证明2图片大于512KB，请重新添加！");
						imgflag=false;
					}if(merchantInfo.getPosBackImgFile() != null && merchantInfo.getPosBackImgFile().length()>1024*512){
						json.setMsg("您上传的安装POS机背面图片大于512KB，请重新添加！");
						imgflag=false;
					}if(imgflag){
						//synchronized(MerchantInfoServiceImpl.class){
						boolean flag=merchantInfoService.updateMerchantInfo(merchantInfo, ((UserBean)userSession));
						if(flag){
							json.setSuccess(true);
							json.setMsg("修改商户成功");
						}else{
							json.setSuccess(false);
							json.setMsg("此刻状态为锁定状态，请刷新该页面再进行操作！");
						}
						//}
					}
				} catch (Exception e) {
					log.error("修改商户异常" + e);
					json.setMsg("修改商户失败");
				}
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 删除商户
	 */
	public void deleteMerchantInfo(){
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			merchantInfoService.deleteMerchantInfo(merchantInfo, ((UserBean)userSession));
			json.setSuccess(true);
			json.setMsg("删除商户信息成功");
		} catch (Exception e) {
			log.error("删除商户信息异常：" + e);
			json.setMsg("删除商户信息失败");
		}
		super.writeJson(json);
	}
	
	/**
	 * 商户代码(布放中用到)
	 */
	public void searchMerchantInfo(){
		DataGridBean dgb = new DataGridBean();
		try {
			String type=super.getRequest().getParameter("type");
			String nameCode = super.getRequest().getParameter("q");
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.searchMerchantInfo(type,nameCode, ((UserBean)userSession), merchantInfo);
		} catch (Exception e) {
			log.error("查询商户异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询区域码
	 */
	public void searchCUPSendCodeInfo(){
		DataGridBean dgd = new DataGridBean();
		String nameCode = super.getRequest().getParameter("q");
		try {
			dgd = merchantInfoService.searchCUPSendCodeInfo(nameCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	super.writeJson(dgd);
	}
	
	/**
	 * 查询MCCID
	 */
	public void searchMCC(){
		DataGridBean dgd = new DataGridBean();
		try {
			String nameCode = super.getRequest().getParameter("q");
			dgd = merchantInfoService.searchMCC(nameCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	super.writeJson(dgd);
	}
	
	/**
	 * 查询SYS_AREA
	 */
	public void searchAreaCode(){
		DataGridBean dgd = new DataGridBean();
		try {
			String nameCode = super.getRequest().getParameter("q");
			dgd = merchantInfoService.searchAreaCode(nameCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	super.writeJson(dgd);
	}
	
	/**
	 * 查询bill_IndustryInfo
	 */
	public void searchIndustryInfo(){
		DataGridBean dgd = new DataGridBean();
		try {
			String nameCode = super.getRequest().getParameter("q");
			dgd = merchantInfoService.searchIndustryInfo(nameCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	super.writeJson(dgd);
	}
	
	/**
	 * 查询出商户显示到下拉框select
	 */
	public void searchMerchantInfoTree(){
		DataGridBean dgd = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgd = merchantInfoService.searchMerchantInfoTree(((UserBean)userSession).getUnNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	super.writeJson(dgd);
	}
	
	/**
	 * 查看明细
	 */
	public void serachMerchantInfoDetailed(){
		List<Object> list = merchantInfoService.queryMerchantInfoDetailed(merchantInfo.getBmid());
		super.writeJson(list);
	}
	
	/**
	 * 根据Mid查看明细
	 */
	public void serachMerchantInfoMid(){
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = merchantInfoService.queryMerchantInfoMid(merchantInfo.getMid());
		} catch (Exception e) {
			log.error("查询商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	
	/**
	 * 导出所有
	 */
	public void exportMer(){
			HttpServletResponse response=getResponse();
			JsonBean json = new JsonBean();
			Object userSession = super.getRequest().getSession().getAttribute("user");
			//session失效
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				try{
					List<Map<String, Object>> list=merchantInfoService.export(merchantInfo,((UserBean)userSession));
					List<String> excelHeader = new ArrayList<String>();
					List excelList = new ArrayList();
					UserBean userBean = (UserBean)userSession;
					String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
						String title [] = {		 "公司mid", "公司tid", "商户名称", "行业类别", "计价类别", "机构编号", "机构名称", "一级机构", "商户性质"
												, "归属机构"	, "营业执照号","营业执照号有效期", "法人姓名","法人证件号","法人身份证有效期","开户银行","账户名称",
												"账号","入账人身份证号","入账人身份证有效期", "支付系统行号"
												,"营业地址省", "营业地址市", "营业地址", "营业地址详细", "联系人", "电话", "商户结算借记卡费率", "借记卡封顶手续费"
												, "商户贷记卡费率", "结算周期", "销售", "机型", "入库SN","TUSN", "商户审批时间", "商户操作日期", "受理人员", "备注"};

						if (userBean.getUnitLvl() != 0) {
							cellFormatType = new String[]{"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
							title = new String[]{"公司mid", "公司tid", "商户名称", "行业类别", "计价类别", "机构编号", "机构名称", "一级机构", "商户性质"
									, "归属机构"	, "营业执照号","营业执照号有效期", "法人姓名","账户名称"
									, "营业地址省", "营业地址市", "商户结算借记卡费率", "借记卡封顶手续费"
									, "商户贷记卡费率", "结算周期", "销售", "机型", "入库SN","TUSN", "商户审批时间", "商户操作日期", "受理人员", "备注"};
						}
						excelList.add(title);
						//添加导出记录
						for (int i = 0; i < list.size(); i++) {
							Map map = list.get(i);
							if (userBean.getUnitLvl() != 0) {
								String []rowContents ={
										map.get("MID")==null?"":map.get("MID").toString(),
										map.get("TID")==null?"":map.get("TID").toString(),
										map.get("RNAME")==null?"":map.get("RNAME").toString(),
										map.get("MCCID")==null?"":map.get("MCCID").toString(),
										map.get("ISM35")==null?"":map.get("ISM35").toString(),
										map.get("UNNO")==null?"":map.get("UNNO").toString(),
										map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
										map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
										map.get("AREATYPE")==null?"":map.get("AREATYPE").toString(),
										map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
										map.get("BNO")==null?"":map.get("BNO").toString(),
										map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
										map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),
										map.get("BANKACCNAME")==null?"":map.get("BANKACCNAME").toString(),
										map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
										map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
										map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
										map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
										map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
										map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
										map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
										map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
										map.get("SN")==null?"":map.get("SN").toString(),
										map.get("MERSN")==null?"":map.get("MERSN").toString(),
										map.get("APPROVEDATE")==null?"":map.get("APPROVEDATE").toString(),
										map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
										map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
										map.get("REMARKS")==null?"":map.get("REMARKS").toString()
									};
								excelList.add(rowContents);
								}else {
									String []rowContents ={
											map.get("MID")==null?"":map.get("MID").toString(),
											map.get("TID")==null?"":map.get("TID").toString(),
											map.get("RNAME")==null?"":map.get("RNAME").toString(),
											map.get("MCCID")==null?"":map.get("MCCID").toString(),
											map.get("ISM35")==null?"":map.get("ISM35").toString(),
											map.get("UNNO")==null?"":map.get("UNNO").toString(),
											map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
											map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
											map.get("AREATYPE")==null?"":map.get("AREATYPE").toString(),
											map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
											map.get("BNO")==null?"":map.get("BNO").toString(),
											map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
											map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),
											map.get("LEGALNUM")==null?"":map.get("LEGALNUM").toString(),
											map.get("LEGALEXPDATE")==null?"":map.get("LEGALEXPDATE").toString(),
											map.get("BANKBRANCH")==null?"":map.get("BANKBRANCH").toString(),
											map.get("BANKACCNAME")==null?"":map.get("BANKACCNAME").toString(),
											map.get("BANKACCNO")==null?"0":map.get("BANKACCNO").toString(),
											map.get("ACCNUM")==null?"":map.get("ACCNUM").toString(),
											map.get("ACCEXPDATE")==null?"":map.get("ACCEXPDATE").toString(),
											map.get("PAYBANKID")==null?"":map.get("PAYBANKID").toString(),
											map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
											map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
											map.get("BADDR")==null?"":map.get("BADDR").toString(),
											map.get("RADDR")==null?"":map.get("RADDR").toString(),
											map.get("CONTACTPERSON")==null?"":map.get("CONTACTPERSON").toString(),
											map.get("CONTACTPHONE")==null?"0":map.get("CONTACTPHONE").toString(),
											map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
											map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
											map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
											map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
											map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
											map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
											map.get("SN")==null?"":map.get("SN").toString(),
											map.get("MERSN")==null?"":map.get("MERSN").toString(),
											map.get("APPROVEDATE")==null?"":map.get("APPROVEDATE").toString(),
											map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
											map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
											map.get("REMARKS")==null?"":map.get("REMARKS").toString(),
								};
								excelList.add(rowContents);
						}
				}
				String excelName = "传统商户入网资料";
				String sheetName = "传统商户入网资料";
				merchantInfoService.saveExportLog(1,list.size(),userBean.getUserID());
				JxlOutExcelUtil.writeExcel(excelList, cellFormatType.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("导出传统网资料Excel成功");
				excelList=null;
			} catch (Exception e) {
				log.error("导出传统入网资料Excel异常：" + e);
				json.setMsg("导出传统入网资料Excel失败");
			}
		}

	}

	/**
	 * 微商户入网资料导出所有
	 */
	public void exportMicroMer(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
					List<Map<String, Object>> list=merchantInfoService.exportMicro(merchantInfo,((UserBean)userSession));
					boolean isHidden = isHiddenPhoneNumber(merchantInfo);
					List<String> excelHeader = new ArrayList<String>();
					List excelList = new ArrayList();
					UserBean userBean = (UserBean)userSession;
					String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
						String title [] = {		 "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
												, "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","法人证件号","法人身份证有效期","开户银行","账户名称",
												"账号","支付系统行号","入账人身份证号","入账人身份证有效期","营业地址省","营业地址市", "营业地址详细", "联系人", "电话", "商户结算借记卡费率", "借记卡封顶手续费"
												, "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
						if (userBean.getUnitLvl() != 0) {
							cellFormatType = new String[]{"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
							title = new String[]{ "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
									, "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","营业地址省","营业地址市", "商户结算借记卡费率", "借记卡封顶手续费"
									, "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
						}
						excelList.add(title);
						
						for (int i = 0; i < list.size(); i++) {
							Map map = list.get(i);
							if (userBean.getUnitLvl() != 0) {
								String []rowContents ={
										map.get("MID")==null?"":map.get("MID").toString(),	
										map.get("TID")==null?"":map.get("TID").toString(),
										map.get("RNAME")==null?"":map.get("RNAME").toString(),
										map.get("UNNO")==null?"":map.get("UNNO").toString(),
										map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
										map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
										map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
										map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
										map.get("BNO")==null?"":map.get("BNO").toString(),
										map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
										map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
										map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),	
										map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
										map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
										map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
										map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
										map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
										map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
										map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
										map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
										map.get("SN")==null?"":map.get("SN").toString(),
										map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
										map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
										map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
										map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
										map.get("REMARKS")==null?"":map.get("REMARKS").toString(),
												
								};
								excelList.add(rowContents);
							}else {
								String []rowContents ={
										map.get("MID")==null?"":map.get("MID").toString(),	
										map.get("TID")==null?"":map.get("TID").toString(),
										map.get("RNAME")==null?"":map.get("RNAME").toString(),
										map.get("UNNO")==null?"":map.get("UNNO").toString(),
										map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
										map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
										map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
										map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
										map.get("BNO")==null?"":map.get("BNO").toString(),
										map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
										map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
										map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),	
										map.get("LEGALNUM")==null?"":map.get("LEGALNUM").toString().length()==18?map.get("LEGALNUM").toString().substring(0,4)+"****"+map.get("LEGALNUM").toString().substring(14):map.get("LEGALNUM").toString().substring(0,4)+"****",
										map.get("LEGALEXPDATE")==null?"":map.get("LEGALEXPDATE").toString(),
										map.get("BANKBRANCH")==null?"":map.get("BANKBRANCH").toString(),
										map.get("BANKACCNAME")==null?"":map.get("BANKACCNAME").toString(),
										map.get("BANKACCNO")==null?"0":map.get("BANKACCNO").toString(),
										map.get("ACCNUM")==null?"":map.get("ACCNUM").toString().length()==18?map.get("ACCNUM").toString().substring(0,4)+"****"+map.get("ACCNUM").toString().substring(14):map.get("ACCNUM").toString().substring(0,4)+"****",
										map.get("ACCEXPDATE")==null?"":map.get("ACCEXPDATE").toString(),
										map.get("PAYBANKID")==null?"":map.get("PAYBANKID").toString(),
										map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
										map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
										map.get("BADDR")==null?"":map.get("BADDR").toString(),
										map.get("CONTACTPERSON")==null?"":map.get("CONTACTPERSON").toString(),
										map.get("CONTACTPHONE")==null?"0":getHiddenPhoneNumber(isHidden,map.get("CONTACTPHONE").toString()),
										map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
										map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
										map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
										map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
										map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
										map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
										map.get("SN")==null?"":map.get("SN").toString(),
										map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
										map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
										map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
										map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
										map.get("REMARKS")==null?"":map.get("REMARKS").toString(),
												
								};
								excelList.add(rowContents);
							}
							
						}
				String excelName = "微商户入网资料";
				String sheetName = "微商户入网资料";
				JxlOutExcelUtil.writeExcel(excelList, cellFormatType.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("导出微入网资料Excel成功");
			} catch (Exception e) {
				log.error("导出微入网资料Excel异常：" + e);
				json.setMsg("导出微入网资料Excel失败");
			}
		}
	}
	public void exportMicroMerSyt(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				boolean isHidden = isHiddenPhoneNumber(merchantInfo);
				List<Map<String, Object>> list=merchantInfoService.exportMicroSyt(merchantInfo,((UserBean)userSession));
				log.info(list.size());
				List<String> excelHeader = new ArrayList<String>();
				List excelList = new ArrayList();
				UserBean userBean = (UserBean)userSession;
				String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
				String title [] = {		 "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
						, "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","法人证件号","法人身份证有效期","开户银行","账户名称",
						"账号","支付系统行号","入账人身份证号","入账人身份证有效期","营业地址省","营业地址市", "营业地址详细", "联系人", "电话", "商户结算借记卡费率", "借记卡封顶手续费"
						, "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
				if (userBean.getUnitLvl() != 0) {
					cellFormatType = new String[]{"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
					title = new String[]{ "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
							, "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","营业地址省","营业地址市", "商户结算借记卡费率", "借记卡封顶手续费"
							, "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
				}
				excelList.add(title);

				for (int i = 0; i < list.size(); i++) {
					Map map = list.get(i);
					if (userBean.getUnitLvl() != 0) {
						String []rowContents ={
								map.get("MID")==null?"":map.get("MID").toString(),
								map.get("TID")==null?"":map.get("TID").toString(),
								map.get("RNAME")==null?"":map.get("RNAME").toString(),
								map.get("UNNO")==null?"":map.get("UNNO").toString(),
								map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
								map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
								map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
								map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
								map.get("BNO")==null?"":map.get("BNO").toString(),
								map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
								map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
								map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),
								map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
								map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
								map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
								map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
								map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
								map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
								map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
								map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
								map.get("SN")==null?"":map.get("SN").toString(),
								map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
								map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
								map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
								map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
								map.get("REMARKS")==null?"":map.get("REMARKS").toString(),

						};
						excelList.add(rowContents);
					}else {
						String []rowContents ={
								map.get("MID")==null?"":map.get("MID").toString(),
								map.get("TID")==null?"":map.get("TID").toString(),
								map.get("RNAME")==null?"":map.get("RNAME").toString(),
								map.get("UNNO")==null?"":map.get("UNNO").toString(),
								map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
								map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
								map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
								map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
								map.get("BNO")==null?"":map.get("BNO").toString(),
								map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
								map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
								map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),
								map.get("LEGALNUM")==null?"":map.get("LEGALNUM").toString().length()==18?map.get("LEGALNUM").toString().substring(0,4)+"****"+map.get("LEGALNUM").toString().substring(14):map.get("LEGALNUM").toString().substring(0,4)+"****",
								map.get("LEGALEXPDATE")==null?"":map.get("LEGALEXPDATE").toString(),
								map.get("BANKBRANCH")==null?"":map.get("BANKBRANCH").toString(),
								map.get("BANKACCNAME")==null?"":map.get("BANKACCNAME").toString(),
								map.get("BANKACCNO")==null?"0":map.get("BANKACCNO").toString(),
								map.get("ACCNUM")==null?"":map.get("ACCNUM").toString().length()==18?map.get("ACCNUM").toString().substring(0,4)+"****"+map.get("ACCNUM").toString().substring(14):map.get("ACCNUM").toString().substring(0,4)+"****",
								map.get("ACCEXPDATE")==null?"":map.get("ACCEXPDATE").toString(),
								map.get("PAYBANKID")==null?"":map.get("PAYBANKID").toString(),
								map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
								map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
								map.get("BADDR")==null?"":map.get("BADDR").toString(),
								map.get("CONTACTPERSON")==null?"":map.get("CONTACTPERSON").toString(),
								map.get("CONTACTPHONE")==null?"0":getHiddenPhoneNumber(isHidden,map.get("CONTACTPHONE").toString()),
								map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
								map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
								map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
								map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
								map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
								map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
								map.get("SN")==null?"":map.get("SN").toString(),
								map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
								map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
								map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
								map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
								map.get("REMARKS")==null?"":map.get("REMARKS").toString(),

						};
						excelList.add(rowContents);
					}

				}
				String excelName = "收银台商户入网资料";
				String sheetName = "收银台商户入网资料";
				JxlOutExcelUtil.writeExcel(excelList, cellFormatType.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("导出收银台入网资料Excel成功");
			} catch (Exception e) {
				log.error("导出收银台入网资料Excel异常：" + e);
				json.setMsg("导出收银台入网资料Excel失败");
			}
		}
	}

	/**
	 * 勾选导出
	 */
	public void exportMerSelected(){
		String bmids=getRequest().getParameter("bmids");
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
					List<Map<String, Object>> list=merchantInfoService.exportSelected(bmids,(UserBean)userSession);
					List<String> excelHeader = new ArrayList<String>();
					List excelList = new ArrayList();
					UserBean userBean = (UserBean)userSession;
					String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
						String title [] = {		 "公司mid", "公司tid", "商户名称", "行业类别", "计价类别", "机构编号", "机构名称", "一级机构", "商户性质"
												, "归属机构"	, "营业执照号","营业执照号有效期", "法人姓名","法人证件号","法人身份证有效期","开户银行","账户名称",
												"账号","入账人身份证号","入账人身份证有效期", "支付系统行号"
												, "营业地址省", "营业地址市", "营业地址","营业地址详细", "联系人", "电话", "商户结算借记卡费率", "借记卡封顶手续费"
												, "商户贷记卡费率", "结算周期", "销售", "机型", "入库SN","TUSN", "商户审批时间", "商户操作日期", "受理人员", "备注"};
						if (userBean.getUnitLvl() != 0) {
							cellFormatType = new String[]{"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
							title = new String[]{"公司mid", "公司tid", "商户名称", "行业类别", "计价类别", "机构编号", "机构名称", "一级机构", "商户性质"
									, "归属机构"	, "营业执照号","营业执照号有效期", "法人姓名","账户名称"
									, "营业地址省", "营业地址市", "商户结算借记卡费率", "借记卡封顶手续费"
									, "商户贷记卡费率", "结算周期", "销售", "机型", "入库SN","TUSN", "商户审批时间", "商户操作日期", "受理人员", "备注"};
						}
						excelList.add(title);
						for (int i = 0; i < list.size(); i++) {
							Map map = list.get(i);
							if (userBean.getUnitLvl() != 0) {
								String []rowContents ={
										map.get("MID")==null?"":map.get("MID").toString(),	
										map.get("TID")==null?"":map.get("TID").toString(),
										map.get("RNAME")==null?"":map.get("RNAME").toString(),
										map.get("MCCID")==null?"":map.get("MCCID").toString(),
										map.get("ISM35")==null?"":map.get("ISM35").toString(),
										map.get("UNNO")==null?"":map.get("UNNO").toString(),
										map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
										map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
										map.get("AREATYPE")==null?"":map.get("AREATYPE").toString(),
										map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
										map.get("BNO")==null?"":map.get("BNO").toString(),
										map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
										map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),	
										map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
										map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
										map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
										map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
										map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
										map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
										map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
										map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
										map.get("SN")==null?"":map.get("SN").toString(),
										map.get("MERSN")==null?"":map.get("MERSN").toString(),
										map.get("APPROVEDATE")==null?"":map.get("APPROVEDATE").toString(),
										map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
										map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
										map.get("REMARKS")==null?"":map.get("REMARKS").toString()
									};
								excelList.add(rowContents);
								}else {
									String []rowContents ={
											map.get("MID")==null?"":map.get("MID").toString(),	
											map.get("TID")==null?"":map.get("TID").toString(),
											map.get("RNAME")==null?"":map.get("RNAME").toString(),
											map.get("MCCID")==null?"":map.get("MCCID").toString(),
											map.get("ISM35")==null?"":map.get("ISM35").toString(),
											map.get("UNNO")==null?"":map.get("UNNO").toString(),
											map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
											map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
											map.get("AREATYPE")==null?"":map.get("AREATYPE").toString(),
											map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
											map.get("BNO")==null?"":map.get("BNO").toString(),
											map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
											map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),	
											map.get("LEGALNUM")==null?"":map.get("LEGALNUM").toString().length()==18?map.get("LEGALNUM").toString().substring(0,4)+"****"+map.get("LEGALNUM").toString().substring(14):map.get("LEGALNUM").toString().substring(0,4)+"****",
											map.get("LEGALEXPDATE")==null?"":map.get("LEGALEXPDATE").toString(),
											map.get("BANKBRANCH")==null?"":map.get("BANKBRANCH").toString(),
											map.get("BANKACCNAME")==null?"":map.get("BANKACCNAME").toString(),
											map.get("BANKACCNO")==null?"0":map.get("BANKACCNO").toString().substring(0,4)+"****"+map.get("BANKACCNO").toString().substring(map.get("BANKACCNO").toString().length()-4),
											map.get("ACCNUM")==null?"":map.get("ACCNUM").toString().length()==18?map.get("ACCNUM").toString().substring(0,4)+"****"+map.get("ACCNUM").toString().substring(14):map.get("ACCNUM").toString().substring(0,4)+"****",
											map.get("ACCEXPDATE")==null?"":map.get("ACCEXPDATE").toString(),
											map.get("PAYBANKID")==null?"":map.get("PAYBANKID").toString(),
											map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
											map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
											map.get("BADDR")==null?"":map.get("BADDR").toString(),
											map.get("RADDR")==null?"":map.get("RADDR").toString(),
											map.get("CONTACTPERSON")==null?"":map.get("CONTACTPERSON").toString(),
											map.get("CONTACTPHONE")==null?"0":map.get("CONTACTPHONE").toString().length()==11?map.get("CONTACTPHONE").toString().substring(0,4)+"****"+map.get("CONTACTPHONE").toString().substring(7):map.get("CONTACTPHONE").toString().substring(0,4)+"****",
											map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
											map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
											map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
											map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
											map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
											map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
											map.get("SN")==null?"":map.get("SN").toString(),
											map.get("MERSN")==null?"":map.get("MERSN").toString(),
											map.get("APPROVEDATE")==null?"":map.get("APPROVEDATE").toString(),
											map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
											map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
											map.get("REMARKS")==null?"":map.get("REMARKS").toString(),
									};
									excelList.add(rowContents);
							}
						}
				String excelName = "传统商户入网资料";
				String sheetName = "传统商户入网资料";
				merchantInfoService.saveExportLog(1,list.size(),userBean.getUserID());
				JxlOutExcelUtil.writeExcel(excelList, cellFormatType.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("导出传统商户入网资料Excel成功");
				excelList=null;
			} catch (Exception e) {
				log.error("导出传统商户入网资料Excel异常：" + e);
				json.setMsg("导出传统商户入网资料Excel失败");
			}
		}
	}
	/**
	 * 手刷勾选导出
	 */
	public void exportMicroMerSelected(){
		String bmids=getRequest().getParameter("bmids");
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				boolean isHidden = isHiddenPhoneNumber(merchantInfo);
				List<Map<String, Object>> list=merchantInfoService.exportMicroMerSelected(bmids,(UserBean)userSession);
				List<String> excelHeader = new ArrayList<String>();
				List excelList = new ArrayList();
				UserBean userBean = (UserBean)userSession;
				String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
					String title [] = {		 "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
											, "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","法人证件号","法人身份证有效期","开户银行","账户名称",
											"账号","支付系统行号","入账人身份证号","入账人身份证有效期","营业地址省","营业地址市", "营业地址详细", "联系人", "电话", "商户结算借记卡费率", "借记卡封顶手续费"
											, "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
					if (userBean.getUnitLvl() != 0) {
						cellFormatType = new String[]{"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
						title = new String[]{ "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
								, "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","营业地址省","营业地址市", "商户结算借记卡费率", "借记卡封顶手续费"
								, "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
					}
					excelList.add(title);
					
					for (int i = 0; i < list.size(); i++) {
						Map map = list.get(i);
						if (userBean.getUnitLvl() != 0) {
							String []rowContents ={
									map.get("MID")==null?"":map.get("MID").toString(),	
									map.get("TID")==null?"":map.get("TID").toString(),
									map.get("RNAME")==null?"":map.get("RNAME").toString(),
									map.get("UNNO")==null?"":map.get("UNNO").toString(),
									map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
									map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
									map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
									map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
									map.get("BNO")==null?"":map.get("BNO").toString(),
									map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
									map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
									map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),	
									map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
									map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
									map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
									map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
									map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
									map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
									map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
									map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
									map.get("SN")==null?"":map.get("SN").toString(),
									map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
									map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
									map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
									map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
									map.get("REMARKS")==null?"":map.get("REMARKS").toString(),
											
							};
							excelList.add(rowContents);
						}else {
							String []rowContents ={
									map.get("MID")==null?"":map.get("MID").toString(),	
									map.get("TID")==null?"":map.get("TID").toString(),
									map.get("RNAME")==null?"":map.get("RNAME").toString(),
									map.get("UNNO")==null?"":map.get("UNNO").toString(),
									map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
									map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
									map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
									map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
									map.get("BNO")==null?"":map.get("BNO").toString(),
									map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
									map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
									map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),	
									map.get("LEGALNUM")==null?"":map.get("LEGALNUM").toString().length()==18?map.get("LEGALNUM").toString().substring(0,4)+"****"+map.get("LEGALNUM").toString().substring(14):map.get("LEGALNUM").toString().substring(0,4)+"****",
									map.get("LEGALEXPDATE")==null?"":map.get("LEGALEXPDATE").toString(),
									map.get("BANKBRANCH")==null?"":map.get("BANKBRANCH").toString(),
									map.get("BANKACCNAME")==null?"":map.get("BANKACCNAME").toString(),
									map.get("BANKACCNO")==null?"0":map.get("BANKACCNO").toString(),
									map.get("ACCNUM")==null?"":map.get("ACCNUM").toString().length()==18?map.get("ACCNUM").toString().substring(0,4)+"****"+map.get("ACCNUM").toString().substring(14):map.get("ACCNUM").toString().substring(0,4)+"****",
									map.get("ACCEXPDATE")==null?"":map.get("ACCEXPDATE").toString(),
									map.get("PAYBANKID")==null?"":map.get("PAYBANKID").toString(),
									map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
									map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
									map.get("BADDR")==null?"":map.get("BADDR").toString(),
									map.get("CONTACTPERSON")==null?"":map.get("CONTACTPERSON").toString(),
									map.get("CONTACTPHONE")==null?"0":getHiddenPhoneNumber(isHidden,map.get("CONTACTPHONE").toString()),
									map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
									map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
									map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
									map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
									map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
									map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
									map.get("SN")==null?"":map.get("SN").toString(),
									map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
									map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
									map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
									map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
									map.get("REMARKS")==null?"":map.get("REMARKS").toString(),
											
							};
							excelList.add(rowContents);
						}
						
					}
			String excelName = "手刷商户入网资料";
			String sheetName = "手刷商户入网资料";
			JxlOutExcelUtil.writeExcel(excelList, cellFormatType.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("导出手刷商户入网资料Excel成功");
			} catch (Exception e) {
				log.error("导出手刷商户入网资料Excel异常：" + e);
				json.setMsg("导出手刷商户入网资料Excel失败");
			}
		}
	}


	/**
	 * 是否处理手机号
	 * @param merchantInfo
	 * @return
	 */
	private boolean isHiddenPhoneNumber(MerchantInfoBean merchantInfo){
		if(StringUtils.isNotEmpty(merchantInfo.getMid()) || StringUtils.isNotEmpty(merchantInfo.getSn())
				|| StringUtils.isNotEmpty(merchantInfo.getBankAccNo()) || StringUtils.isNotEmpty(merchantInfo.getTid())) {
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 手机号隐藏处理
	 * @param isShow true隐藏 false不隐藏
	 * @param phone
	 * @return
	 */
	private String getHiddenPhoneNumber(boolean isShow,String phone){
		if(isShow) {
			if (StringUtils.isEmpty(phone))
				return "*";
			else if (phone.length() == 11)
				return phone.substring(0, 3) + "****" + phone.substring(8);
			else if (phone.length() >= 3 && phone.length() < 11)
				return phone.substring(0, 3) + "***********";
			else
				return "*";
		}else{
			return phone;
		}
	}
	public void exportMicroMerSelectedSyt(){
		String bmids=getRequest().getParameter("bmids");
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				boolean isShow = isHiddenPhoneNumber(merchantInfo);
				List<Map<String, Object>> list=merchantInfoService.exportMicroMerSelectedSyt(bmids,(UserBean)userSession);
				List<String> excelHeader = new ArrayList<String>();
				List excelList = new ArrayList();
				UserBean userBean = (UserBean)userSession;
				String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
				String title [] = {		 "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
						, "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","法人证件号","法人身份证有效期","开户银行","账户名称",
						"账号","支付系统行号","入账人身份证号","入账人身份证有效期","营业地址省","营业地址市", "营业地址详细", "联系人", "电话", "商户结算借记卡费率", "借记卡封顶手续费"
						, "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
				if (userBean.getUnitLvl() != 0) {
					cellFormatType = new String[]{"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
					title = new String[]{ "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
							, "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","营业地址省","营业地址市", "商户结算借记卡费率", "借记卡封顶手续费"
							, "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
				}
				excelList.add(title);

				for (int i = 0; i < list.size(); i++) {
					Map map = list.get(i);
					if (userBean.getUnitLvl() != 0) {
						String []rowContents ={
								map.get("MID")==null?"":map.get("MID").toString(),
								map.get("TID")==null?"":map.get("TID").toString(),
								map.get("RNAME")==null?"":map.get("RNAME").toString(),
								map.get("UNNO")==null?"":map.get("UNNO").toString(),
								map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
								map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
								map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
								map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
								map.get("BNO")==null?"":map.get("BNO").toString(),
								map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
								map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
								map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),
								map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
								map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
								map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
								map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
								map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
								map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
								map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
								map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
								map.get("SN")==null?"":map.get("SN").toString(),
								map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
								map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
								map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
								map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
								map.get("REMARKS")==null?"":map.get("REMARKS").toString(),

						};
						excelList.add(rowContents);
					}else {
						String []rowContents ={
								map.get("MID")==null?"":map.get("MID").toString(),
								map.get("TID")==null?"":map.get("TID").toString(),
								map.get("RNAME")==null?"":map.get("RNAME").toString(),
								map.get("UNNO")==null?"":map.get("UNNO").toString(),
								map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
								map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
								map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
								map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
								map.get("BNO")==null?"":map.get("BNO").toString(),
								map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
								map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
								map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),
								map.get("LEGALNUM")==null?"":map.get("LEGALNUM").toString().length()==18?map.get("LEGALNUM").toString().substring(0,4)+"****"+map.get("LEGALNUM").toString().substring(14):map.get("LEGALNUM").toString().substring(0,4)+"****",
								map.get("LEGALEXPDATE")==null?"":map.get("LEGALEXPDATE").toString(),
								map.get("BANKBRANCH")==null?"":map.get("BANKBRANCH").toString(),
								map.get("BANKACCNAME")==null?"":map.get("BANKACCNAME").toString(),
								map.get("BANKACCNO")==null?"0":map.get("BANKACCNO").toString(),
								map.get("ACCNUM")==null?"":map.get("ACCNUM").toString().length()==18?map.get("ACCNUM").toString().substring(0,4)+"****"+map.get("ACCNUM").toString().substring(14):map.get("ACCNUM").toString().substring(0,4)+"****",
								map.get("ACCEXPDATE")==null?"":map.get("ACCEXPDATE").toString(),
								map.get("PAYBANKID")==null?"":map.get("PAYBANKID").toString(),
								map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
								map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
								map.get("BADDR")==null?"":map.get("BADDR").toString(),
								map.get("CONTACTPERSON")==null?"":map.get("CONTACTPERSON").toString(),
								map.get("CONTACTPHONE")==null?"0":getHiddenPhoneNumber(isShow,map.get("CONTACTPHONE").toString()),
								map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
								map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
								map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
								map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
								map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
								map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
								map.get("SN")==null?"":map.get("SN").toString(),
								map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
								map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
								map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
								map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
								map.get("REMARKS")==null?"":map.get("REMARKS").toString(),

						};
						excelList.add(rowContents);
					}

				}
				String excelName = "收银台商户入网资料";
				String sheetName = "收银台商户入网资料";
				JxlOutExcelUtil.writeExcel(excelList, cellFormatType.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
				json.setSuccess(true);
				json.setMsg("导出收银台商户入网资料Excel成功");
			} catch (Exception e) {
				log.error("导出收银台商户入网资料Excel异常：" + e);
				json.setMsg("导出收银台商户入网资料Excel失败");
			}
		}
	}
	
	/**
	 * 审批成功
	 */
	public void updateMerchantInfoY(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			 try {
				merchantInfoService.updateMerchantInfoY(merchantInfo,(UserBean)userSession);
				json.setSuccess(true);
				json.setMsg("操作成功");
			} catch (Exception e) {
				log.error("操作异常" + e);
				json.setMsg("操作失败");
			}
		}
		super.writeJson(json);
	}
	
	//批量审批通过
	public void updateBatchMerchantInfoY(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
				try {
					int result = 0;
					String merchantInfoIds[]=bmids.split(",");
					for (int i = 0; i < merchantInfoIds.length; i++) {
						MerchantInfoModel model=merchantInfoService.getInfoModel(Integer.parseInt(merchantInfoIds[i]));
						if(!"C".equals(model.getApproveStatus())){
							result = result+1;
						}else{
							result = result;
						}
					}
					if(result==0){
						for (int i = 0; i < merchantInfoIds.length; i++) {
							MerchantInfoModel model=merchantInfoService.getInfoModel(Integer.parseInt(merchantInfoIds[i]));
							merchantInfoService.updateMerchantInfoY(model,(UserBean)userSession);
						}
						json.setSuccess(true);
						json.setMsg("商户开通成功");
					}else{
						json.setSuccess(false);
						json.setMsg("失败，该批量记录有已被审批");
					}
				} catch (Exception e) {
					log.error("商户开通异常" + e);
					json.setMsg("商户开通失败");
				}
		}
		super.writeJson(json);
	}
	
	/**
	 * 根据注册名称或营业执照编号查询商户
	 */
	public void listMerchantInfoBnoRname(){
		DataGridBean dgb = new DataGridBean();
		try {
			dgb = merchantInfoService.queryMerchantInfoBnoRname(merchantInfo);
		} catch (Exception e) {
			log.error("根据注册名称或营业执照编号查询商户异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	
	
	/*
	 * 判断字符串是否是数字或者‘非数字’
	 */
	public  boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	
	
	/**
	 * 查询可开通微商户list
	 */
	public void listMicroMerchantInfoWJoin(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMicroMerchantInfoWJoin(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询微商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}

	public void listMicroMerchantInfoWJoinSyt(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMicroMerchantInfoWJoinSyt(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询微商户信息异常：" + e);
		}

		super.writeJson(dgb);
	}

	/**
	 * plus商户审批
	 */
	public void listMicroMerchantInfoWJoinPlus(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMicroMerchantInfoWJoinPlus(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询微商户信息异常：" + e);
		}

		super.writeJson(dgb);
	}
	/**
	 * 查询可开通微商户list
	 */
	public void listMicroMerchantInfoWJoin1(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if("".equals(merchantInfo.getMid())||merchantInfo.getMid()==null){
				merchantInfo.setMid("864");
			}else if(!merchantInfo.getMid().startsWith("864")){
				return;
			}
			dgb = merchantInfoService.queryMicroMerchantInfoWJoin1(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询微商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	
	/**
	 * 批量审批通过微商户----->
	 */
	public void updateMerchantMicroInfos(){

		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				String merchantInfoIds[]=bmids.split(",");
				for(String id :merchantInfoIds){
					MerchantInfoModel model=merchantInfoService.getInfoModel(Integer.parseInt(id));
					if("W".equals(model.getApproveStatus())&&!"000000".equals(model.getUnno())){
						merchantInfo.setBmid(Integer.parseInt(id));
						MerchantInfoModel m = merchantInfoService.updateMerchantMicroInfoC(merchantInfo, ((UserBean)userSession));
						if(m!=null){
							merchantInfoService.updateMerchantMicroInfoCToADM(m, ((UserBean)userSession));
						}else{
							json.setSuccess(false);
							json.setMsg("其中商户下无终端！请挂终端！");
							super.writeJson(json);
							return ;
						}
					}else{
						json.setSuccess(false);
						json.setMsg(model.getMid()+ "  商户待选择消费方式！");
						super.writeJson(json);
						return ;
					}
				}
				json.setSuccess(true);
				json.setMsg("微商户批量开通成功");
			} catch (Exception e) {
				log.error("微商户批量开通异常" + e);
				json.setMsg("微商户批量开通失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 审批通过微商户----->C
	 */
	public void updateMerchantMicroInfoC(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		}else{
			try {
				String merchantInfoIds[]=bmids.split(",");
				boolean flag =false;
				MerchantInfoModel model=merchantInfoService.getInfoModel(Integer.parseInt(merchantInfoIds[0]));
				if("W".equals(model.getApproveStatus())&&!"000000".equals(model.getUnno())){
					MerchantInfoModel m = merchantInfoService.updateMerchantMicroInfoC(merchantInfo, ((UserBean)userSession));
					if(m!=null){
						flag=merchantInfoService.updateMerchantMicroInfoCToADM(m, ((UserBean)userSession));
					}
					if(flag){
						json.setSuccess(true);
						json.setMsg("微商户开通成功");
					}else{
						json.setSuccess(false);
						json.setMsg("该户下无终端！请挂终端！");
					}
				}else{
					json.setSuccess(false);
					json.setMsg(model.getMid()+"  商户待选择消费方式！");
				}
			} catch (Exception e) {
				log.error("微商户开通异常" + e);
				json.setSuccess(false);
				json.setMsg("微商户开通失败");
			}
			super.writeJson(json);
		}
	}
	
	
	/**
	 * 审批成功 --->Y
	 */
	public void updateMerchantMicroInfoY(){
		
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			 try {
				merchantInfoService.updateMerchantMicroInfoY(merchantInfo,(UserBean)userSession);
				json.setSuccess(true);
				json.setMsg("操作成功");
			} catch (Exception e) {
				log.error("操作异常" + e);
				json.setMsg("操作失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 批量审批成功 --->Y
	 */
	public void updateMerchantMicroBatchInfoY(){
		
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			 try {
				merchantInfoService.updateMerchantBatchMicroInfoY(bmids,(UserBean)userSession);
				json.setSuccess(true);
				json.setMsg("操作成功");
			} catch (Exception e) {
				log.error("操作异常" + e);
				json.setMsg("操作失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 审批退回微商户
	 */
	public void updateMerchantMicroInfoK(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		}else{
			try {
				boolean result = false;
				MerchantInfoModel model=merchantInfoService.getInfoModel(merchantInfo.getBmid());
				if(!"W".equals(model.getApproveStatus())){
					result = false;
				}else{
					result = true;
				}
				if(result){
					merchantInfoService.updateMerchantMicroInfoK(merchantInfo, ((UserBean)userSession));
					json.setSuccess(true);
					json.setMsg("微商户退回成功");
				}else{
					json.setSuccess(false);
					json.setMsg("失败，该记录已经被其它用户审批");
				}
			} catch (Exception e) {
				log.error("微商户退回异常" + e);
				json.setSuccess(false);
				json.setMsg("微商户退回失败");
			}
			super.writeJson(json);
		}
		
	}
	
	/**
	 * 批量退回微商户
	 */
	public void updateMerchantBackAll(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		}else{
			try {
				String bmids=getRequest().getParameter("bmids");
				String[] bmid = bmids.split(",");
				for(int i=0;i<bmid.length;i++){
					merchantInfo.setBmid(Integer.parseInt(bmid[i]));
					boolean result = false;
					MerchantInfoModel model=merchantInfoService.getInfoModel(merchantInfo.getBmid());
					if(!"W".equals(model.getApproveStatus())){
						result = false;
					}else{
						result = true;
					}
					if(result){
						merchantInfoService.updateMerchantMicroInfoK(merchantInfo, ((UserBean)userSession));
						json.setSuccess(true);
						json.setMsg("微商户退回成功");
					}else{
						json.setSuccess(false);
						json.setMsg("失败，该记录已经被其它用户审批");
						break;
					}
				}
			} catch (Exception e) {
				log.error("微商户退回异常" + e);
				json.setSuccess(false);
				json.setMsg("微商户退回失败");
			}
			super.writeJson(json);
		}
		
	}
	
	/**
	 * 微商户查看明细
	 */
	public void serachMerchantMicroInfoDetailed(){
		List<Object> list = merchantInfoService.queryMerchantMicroInfoDetailed(merchantInfo.getBmid());
		super.writeJson(list);
	}
	
	
	/**
	 * 微商户入网报单---->查询出踢回和未开通的微商户
	 */
	public void listMerchantMicroInfoZK(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMerchantMicroInfoZK(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}

	public void listMerchantMicroInfoZKSyt(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMerchantMicroInfoZKSyt(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}

		super.writeJson(dgb);
	}

    /**
     * Plus入网报单
     */
	public void listMerchantMicroInfoZKPlus(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMerchantMicroInfoZKPlus(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}

		super.writeJson(dgb);
	}
	
	/**
	 * 微商户系统入网报单
	 */
	public void addMerchantMicroInfo(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
				//判断手刷商户编号总数量是否已满！
				boolean flag2=merchantInfoService.queryMicroMerchantCount();
				if(flag2){
					boolean flag3=merchantInfoService.queryIsHotMerch(merchantInfo);
					if(flag3){
						String tt=merchantInfo.getBankProvinceCode()+merchantInfo.getBankName();
						if(tt.indexOf("青海")>=0 || tt.indexOf("西藏")>=0 || tt.indexOf("新疆")>=0 ){
							json.setMsg("不支持此开户行！");
							json.setSuccess(false);
						}else{
								try {
									if(merchantInfo.getLegalUploadFile() != null && merchantInfo.getLegalUploadFile().length()>1024*512){
										json.setMsg("您上传的法人身份图片大于512KB，请重新添加！");
									}else if(merchantInfo.getBupLoadFile() != null && merchantInfo.getBupLoadFile().length()>1024*512){
										json.setMsg("您上传的营业执照图片大于512KB，请重新添加！");
									}else if(merchantInfo.getMaterialUpLoadFile() != null && merchantInfo.getMaterialUpLoadFile().length()>1024*512){ 
										json.setMsg("您上传的补充材料图片大于512KB，请重新添加！");
									}else if(merchantInfo.getMaterialUpLoad1File() != null && merchantInfo.getMaterialUpLoad1File().length()>1024*512){
										json.setMsg("您上传的补充材料1图片大于512KB，请重新添加！");
									}else if(merchantInfo.getMaterialUpLoad2File() != null && merchantInfo.getMaterialUpLoad2File().length()>1024*512){
										json.setMsg("您上传的补充材料2图片大于512KB，请重新添加！");
									}else if(merchantInfo.getMaterialUpLoad3File() != null && merchantInfo.getMaterialUpLoad3File().length()>1024*512){
										json.setMsg("您上传的补充材料2图片大于512KB，请重新添加！");
									}else if(merchantInfo.getMaterialUpLoad5File() != null && merchantInfo.getMaterialUpLoad5File().length()>1024*512){
										json.setMsg("您上传的补充材料2图片大于512KB，请重新添加！");
									}else{
											//synchronized (merchantInfoService){
												merchantInfoService.saveMerchantMicroInfo(merchantInfo, ((UserBean)userSession));
												json.setSuccess(true);
												json.setMsg("商户添加成功"); 
											//}
									}
								} catch (Exception e) {
									log.error("商户添加异常：" + e);
									json.setSuccess(false);
									json.setMsg("商户添加失败");
								}
						}
					}else{
						json.setSuccess(false);
						json.setMsg("该户在黑名单商户中！请核查");
					}

				}else{
					json.setSuccess(false);
					json.setMsg("手刷商户编号已满，请通知管理员！");
				}
			}
		super.writeJson(json);
	}
	
	
	/**
	 * 微商户系统入网报单---修改
	 */
	public void editMerchantMicroInfo(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			String tt=merchantInfo.getBankProvinceCode()+merchantInfo.getBankName();
			if(tt.indexOf("青海")>=0 || tt.indexOf("西藏")>=0 || tt.indexOf("新疆")>=0 ){
				json.setMsg("不支持此开户行！");
				json.setSuccess(false);
			}else{
					try {
						if(merchantInfo.getLegalUploadFile() != null && merchantInfo.getLegalUploadFile().length()>1024*512){
							json.setMsg("您上传的法人身份图片大于512KB，请重新添加！");
						}else if(merchantInfo.getBupLoadFile() != null && merchantInfo.getBupLoadFile().length()>1024*512){
							json.setMsg("您上传的营业执照图片大于512KB，请重新添加！");
						}else if(merchantInfo.getMaterialUpLoadFile() != null && merchantInfo.getMaterialUpLoadFile().length()>1024*512){ 
							json.setMsg("您上传的补充材料图片大于512KB，请重新添加！");
						}else if(merchantInfo.getMaterialUpLoad1File() != null && merchantInfo.getMaterialUpLoad1File().length()>1024*512){
							json.setMsg("您上传的补充材料1图片大于512KB，请重新添加！");
						}else if(merchantInfo.getMaterialUpLoad2File() != null && merchantInfo.getMaterialUpLoad2File().length()>1024*512){
							json.setMsg("您上传的补充材料2图片大于512KB，请重新添加！");
						}else if(merchantInfo.getMaterialUpLoad3File() != null && merchantInfo.getMaterialUpLoad3File().length()>1024*512){
							json.setMsg("您上传的补充材料2图片大于512KB，请重新添加！");
						}else if(merchantInfo.getMaterialUpLoad5File() != null && merchantInfo.getMaterialUpLoad5File().length()>1024*512){
							json.setMsg("您上传的补充材料2图片大于512KB，请重新添加！");
						}
						else{
							boolean flag=merchantInfoService.updateMerchantMicroInfo(merchantInfo, ((UserBean)userSession));
							if(flag){
								json.setSuccess(true);
								json.setMsg("修改商户成功");
							}else{
								json.setSuccess(false);
								json.setMsg("此刻状态为锁定状态，请刷新该页面再进行操作！");
							}
					  }
					} catch (Exception e) {
						log.error("修改商户异常" + e);
						json.setMsg("修改商户失败");
					}
				}

			}
				
				super.writeJson(json);
	}
	
	
	public void listMerchantMicroToUnbid(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMerchantMicroToUnbid(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	
	
	/**
	 * 解绑终端号
	 */
	public void unbindSN(){
		JsonBean json = new JsonBean();
		String sn = merchantInfo.getSn();
		String mid = merchantInfo.getMid();
		if(null != sn && !"".equals(sn)){
			try {
				String errMsg=merchantInfoService.updateUnbindSn(sn,mid);
				if(StringUtils.isNotEmpty(errMsg)){
					json.setSuccess(false);
					json.setMsg(errMsg);
				}else {
					json.setSuccess(true);
					json.setMsg("解绑终端号成功");
				}
			} catch (Exception e) {
				
				json.setSuccess(false);
				json.setMsg("解绑终端号异常");
				e.printStackTrace();
			}
		}else{
			json.setSuccess(false);
			json.setMsg("终端号为空");
		}
		
		super.writeJson(json);
	}
	
	
	/*
	 * 批量更新维护销售
	 */
	public void  updateMoreSalesFromExcel(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null){
			json.setSessionExpire(true);
		}
		//把文件保存到服务器目录下
		String fileName = ServletActionContext.getRequest().getParameter("importHrtFile");
		String basePath = ServletActionContext.getRequest().getRealPath("/upload");
		File dir = new File(basePath);
		dir.mkdir();		
		// 使用UUID做为文件名，以解决重名的问题
		String path = basePath +"/"+fileName;
		File destFile = new File(path);
		upload.renameTo(destFile);
		String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
		String xlsfile = folderPath +"/"+fileName;
		try {
			if(xlsfile.length() > 0 && xlsfile != null){
				boolean flag=merchantInfoService.updateMoreSales(xlsfile,user);
				if(flag){
					json.setSuccess(true);
					json.setMsg("记录更新成功!");
				}else{
					json.setSuccess(false);
					json.setMsg("文件记录格式错误（空值或无该销售）！");
				}

					super.writeJson(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(true);
			json.setMsg("文件更新失败!");
			super.writeJson(json);
		}
		File file = new File(folderPath);
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(folderPath + tempList[i]);
			} else {
				temp = new File(folderPath + File.separator + tempList[i]);
			}
			if (temp.exists() && temp.isFile()) {
				boolean flag = temp.delete(); // 删除上传到服务器的文件
				int j = i + 1;
				log.error("成功删除文件:第" + j + "个文件删除是否成功？" + flag);
			} else {
				log.error("文件不存在");
			}
		}
	}
	
	/**
	 * Excel 导入商户信息
	 */
	public void  addMoreMerchantFromExcel(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null){
			json.setSessionExpire(true);
		}
		//把文件保存到服务器目录下
		String fileName = ServletActionContext.getRequest().getParameter("importMerchantFile");
		String type=super.getRequest().getParameter("type");
		String basePath = ServletActionContext.getRequest().getRealPath("/upload");
		File dir = new File(basePath);
		dir.mkdir();		
		// 使用UUID做为文件名，以解决重名的问题
		String path = basePath +"/"+fileName;
		File destFile = new File(path);
		upload.renameTo(destFile);
		String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
		String xlsfile = folderPath +"/"+fileName;
		try {
			if(xlsfile.length() > 0 && xlsfile != null){
				boolean flag=false;
				if("1".equals(type)){
					flag=merchantInfoService.saveMoreMerchantInfo(xlsfile,user,fileName);
				}else{
					//判断手刷商户编号总数量是否已满！
					boolean flag2=merchantInfoService.queryMicroMerchantCount();
					if(flag2){
						flag=merchantInfoService.saveMoreMicroMerchantInfo(xlsfile,user,fileName);
					}else{
						json.setSuccess(false);
						json.setMsg("手刷商户编号已满，请通知管理员！");
						super.writeJson(json);
					}
				}
				if(flag){
					json.setSuccess(true);
					json.setMsg("商户导入成功!");
				}else{
					json.setSuccess(false);
					json.setMsg("文件记录格式错误（空值或无该销售）！");
				}
					super.writeJson(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(true);
			json.setMsg("文件导入失败（请检查 '区域码','销售'）！");
			super.writeJson(json);
		}
		File file = new File(folderPath);
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(folderPath + tempList[i]);
			} else {
				temp = new File(folderPath + File.separator + tempList[i]);
			}
			if (temp.exists() && temp.isFile()) {
				boolean flag = temp.delete(); // 删除上传到服务器的文件
				int j = i + 1;
				log.error("成功删除文件:第" + j + "个文件删除是否成功？" + flag);
			} else {
				log.error("文件不存在");
			}
		}
	}
	
	/**
	 * 查询已开通的微商户
	 */
	public void listMicroMerchantInfoY(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMicroMerchantInfoY(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}

	/**
	 * 解绑页面商户列表查询,必须输入mid/hybphone,否则为空列表
	 */
	public void listMicroMerchantInfoY10631(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMicroMerchantInfoY10631(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	/**
	 * 收银台入网资料
	 * */
	public void listMicroMerchantInfoYSyt(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMicroMerchantInfoYSyt(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		super.writeJson(dgb);
	}

    /**
     * plus入网资料
     */
	public void listMicroMerchantInfoYPlus(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMicroMerchantInfoYPlus(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}

		super.writeJson(dgb);
	}

	/**
	 * 收银台激活统计
	 */
	public void listSytRebateTotal(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.querySytRebateTotal(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询收银台激活异常：" + e);
		}

		super.writeJson(dgb);
	}

	public void exportListSytRebateTotal(){
		JsonBean json = new JsonBean();
		List<Map<String, Object>> list = null;
		List<String[]>cotents = new ArrayList<String[]>();
		String titles[] = {"机构号","机构名称","新增出库已激活数","出库数","激活数","累计激活数 ","累计出库数","激活率 "};
		cotents.add(titles);
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(userSession!=null){
				list = merchantInfoService.querySytRebateTotalAll(merchantInfo,(UserBean)userSession);
				if (list!=null&&list.size()>0) {
					for (Map<String, Object>map  : list) {
						String rowCoents[] = {
								map.get("UNNO")==null?"":map.get("UNNO").toString(),
								map.get("UN_NAME")==null?"":map.get("UN_NAME").toString(),
								map.get("ADD_OUT_ACT")==null?"":map.get("ADD_OUT_ACT").toString(),
								map.get("OUT_COUNT")==null?"":map.get("OUT_COUNT").toString(),
								map.get("ACT_COUNT")==null?"":map.get("ACT_COUNT").toString(),
								map.get("OUT_TOTAL")==null?"":map.get("OUT_TOTAL").toString(),
								map.get("ACT_TOTAL")==null?"":map.get("ACT_TOTAL").toString(),
								map.get("ACT_RATE")==null?"":map.get("ACT_RATE").toString()
						};
						cotents.add(rowCoents);
					}
				}
				String excelName = "运营中心激活情况明细表.csv";
//                String [] cellFormatType = {"t","t","t","t","t","t","t","t","t"};
				JxlOutExcelUtil.writeCSVFile(cotents, titles.length, getResponse(), excelName);
			}
		} catch (Exception e) {
			log.error("运营中心激活情况明细表导出异常：" + e);
			json.setSuccess(false);
			json.setMsg("运营中心激活情况明细表导出失败");
			super.writeJson(json);
		}
	}
	
	
	/**
	 * 查询所有商户（风控）
	 */
	public void listAllMerchantInfo(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryAllMerchantInfo(merchantInfo);
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		
		super.writeJson(dgb);
	}
	
	/**
	 * 导出所有(风控)
	 */
	public void exportAllMer(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=merchantInfoService.exportAll(merchantInfo,((UserBean)userSession));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("商户入网资料导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出入网资料Excel成功");
			} catch (Exception e) {
				log.error("导出入网资料Excel异常：" + e);
				json.setMsg("导出入网资料Excel失败");
			}
		}
	}
	
	/*
	 * 代理通过java底层HttpClient报单
	 */
//	public void addMerchantInfoByHttpClientWay(){
//		JsonBean json= new JsonBean();
//		try {
//			merchantInfoService.saveMerchantInfoByHttpClient(merchantInfo);
//			json.setMsg("报单成功！");
//			json.setSuccess(true);
//		} catch (Exception e) {
//			log.error("代理通过java底层HttpClient报单异常：" + e);
//			json.setSuccess(false);
//			json.setMsg("报单失败！");
//		}
//		super.writeJson(json);
//	}
	
	/**
	 * 根据Excel表格进行商户信息更新
	 */
	public void updateMerchantToUnit(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null){
			json.setSessionExpire(true);
		}
		//把文件保存到服务器目录下
		String fileName = ServletActionContext.getRequest().getParameter("importMerchantFile2");
		String basePath = ServletActionContext.getRequest().getRealPath("/upload");
		File dir = new File(basePath);
		dir.mkdir();		
		// 使用UUID做为文件名，以解决重名的问题
		String path = basePath +"/"+fileName;
		File destFile = new File(path);
		upload.renameTo(destFile);
		String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
		String xlsfile = folderPath +"/"+fileName;
		try {
			if(xlsfile.length() > 0 && xlsfile != null){
				boolean flag=false;
				flag=merchantInfoService.updateMerchantToUnit(xlsfile,user,fileName);
				if(flag){
					json.setSuccess(true);
					json.setMsg("文件导入成功");
				}else{
					json.setSuccess(false);
					json.setMsg("文件数据格式有误！");
				}
			}else{
				json.setSuccess(false);
				json.setMsg("文件为空文件！");
			}
			super.writeJson(json);
		} catch (Exception e) {
			log.error(e);
			json.setSuccess(false);
			json.setMsg("文件导入失败！");
			super.writeJson(json);
		}
		File file = new File(folderPath);
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(folderPath + tempList[i]);
			} else {
				temp = new File(folderPath + File.separator + tempList[i]);
			}
			if (temp.exists() && temp.isFile()) {
				boolean flag = temp.delete(); // 删除上传到服务器的文件
				int j = i + 1;
				log.error("成功删除文件:第" + j + "个文件删除是否成功？" + flag);
			} else {
				log.error("文件不存在");
			}
		}
	}
	
	
	/**
	 * 导出代理商终端使用情况
	 */
	public void exportTidUseCount(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=merchantInfoService.queryTidUseing();
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("代理商终端使用统计".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("导出Excel成功");
			} catch (Exception e) {
				log.error("导出异常：" + e);
				json.setMsg("导出Excel失败");
			}
		}
	}
	
	/**
	 * 删除服务器上的上传文件
	 * @param basePath
	 * @param path
	 */
	public void removeUploadFile(String basePath,String path){
		File file = new File(basePath);
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(basePath + tempList[i]);
			} else {
				temp = new File(basePath + File.separator + tempList[i]);
			}
			if (temp.exists() && temp.isFile()) {
				boolean flag = temp.delete(); // 删除上传到服务器的文件
				int j = i + 1;
				log.error("成功删除文件:第" + j + "个文件删除是否成功？" + flag);
			} else {
				log.error("文件不存在");
			}
		}
	}
	
	/**
	 * 人工实名认证 ---批量导入
	 */
	public void updateAllMerchantAuth(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null)json.setSessionExpire(true);
		//把文件保存到服务器目录下
		String fileName = ServletActionContext.getRequest().getParameter("file10649Name");
		String basePath = ServletActionContext.getRequest().getRealPath("/upload");
		File dir = new File(basePath);
		dir.mkdir();
		// 使用UUID做为文件名，以解决重名的问题
		String path = basePath +"/"+fileName;
		File destFile = new File(path);
		upload.renameTo(destFile);
		String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
		String xlsfile = folderPath +"/"+fileName;
		try {
			if(xlsfile.length() > 0 && xlsfile != null){
				List<Map<String,String>> list = null;
				list = merchantInfoService.updateAllMerchantAuth(xlsfile,fileName,user);
				if(list!=null&&list.size() > 0){
					String excelName= "人工实名认证失败记录";
					String[] title = {"商户号","账户卡号","账户名称","身份证号","操作","备注"};
					List excellist = new ArrayList();
					excellist.add(title);
					for(int rowId = 0;rowId<list.size();rowId++){
						Map<String, String> order = list.get(rowId);
						String[] rowContents = {
								order.get("mid")==null?"":order.get("mid"),
								order.get("bankAccNo")==null?"":order.get("bankAccNo"),
								order.get("bankAccName")==null?"":order.get("bankAccName"),
								order.get("legalNum")==null?"":order.get("legalNum"),
								order.get("goOrBack")==null?"":order.get("goOrBack"),
								order.get("remark")==null?"":order.get("remark")};
						excellist.add(rowContents);
					}
					String[] cellFormatType = {"t","t","t","t","t","t"};
					UsePioOutExcel.writeExcel(excellist, 6, super.getResponse(), excelName, excelName+".xls", cellFormatType);					
				}else{
					json.setSuccess(true);
					json.setMsg("记录更新成功!");
					super.writeJson(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("文件更新失败!");
			super.writeJson(json);
		}
		File file = new File(folderPath);
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(folderPath + tempList[i]);
			} else {
				temp = new File(folderPath + File.separator + tempList[i]);
			}
			if (temp.exists() && temp.isFile()) {
				boolean flag = temp.delete(); // 删除上传到服务器的文件
				int j = i + 1;
				log.error("成功删除文件:第" + j + "个文件删除是否成功？" + flag);
			} else {
				log.error("文件不存在");
			}
		}
	}
	
	/**
	 * 勾选通过/退回实名认证
	 */
	public void AllMerchantAuthGoOrBack() throws Exception{
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		boolean is = false;
		try{
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				String flag = super.getRequest().getParameter("flag");
				
				if("go".equals(flag)){
					String names = super.getRequest().getParameter("names");
					List<MerchantAuthenticityBean> list = JSON.parseArray(names, MerchantAuthenticityBean.class);
					if(list.size()<1){
						json.setSuccess(false);
						json.setMsg("勾选异常");
						super.writeJson(json);
					}
					for (MerchantAuthenticityBean merchantAuthenticityBean : list) {
						is = merchantInfoService.MerchantAuthGo(merchantAuthenticityBean.getMid(),merchantAuthenticityBean.getBankAccNo(),merchantAuthenticityBean.getBankAccName(),merchantAuthenticityBean.getLegalNum());
						if(is){
							MerchantAuthenticityBean  matb= new MerchantAuthenticityBean();
							Map<String, String> result= new HashMap<String, String>();
							MerchantAuthenticityModel authenticityModel = merchantAuthenticityService.queryMerchantAuthenticityByMid(merchantAuthenticityBean.getMid());
							matb.setUsername(authenticityModel.getUsername());
							matb.setMid(authenticityModel.getMid());
							result.put("userName",matb.getUsername());
							result.put("mid",matb.getMid());
							result.put("msg","认证成功");
							result.put("status","2");
							merchantAuthenticityService.sendResultToHyb(result,matb);
						}else{
							json.setSuccess(false);
							json.setMsg("认证通过失败");
							super.writeJson(json);
						}
					}
					json.setSuccess(true);
					json.setMsg("认证通过成功");
				}else if("back".equals(flag)){
					String names = super.getRequest().getParameter("names");
					List<MerchantAuthenticityBean> list = JSON.parseArray(names, MerchantAuthenticityBean.class);
					for (MerchantAuthenticityBean merchantAuthenticityBean : list) {
						is = merchantInfoService.MerchantAuthBack(merchantAuthenticityBean.getMid(),merchantAuthenticityBean.getBankAccNo(),merchantAuthenticityBean.getBankAccName(),merchantAuthenticityBean.getLegalNum());
						if(!is){
							json.setSuccess(false);
							json.setMsg("勾选回退失败");
							super.writeJson(json);
						}
					}
					json.setSuccess(true);
					json.setMsg("勾选回退成功");
				}
			}
		}catch(Exception e){
			log.error("勾选通过/退回实名认证异常:"+e);
			json.setSuccess(false);
			json.setMsg("勾选异常");
			super.writeJson(json);
		}
		super.writeJson(json);
	}
	/**
	 * 注销微商户
	 */
	public void deleteMposMer(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		try {
			Integer i = merchantInfoService.deleteMposMer(merchantInfo,(UserBean)userSession);
			if(i==1){
				json.setSuccess(true);
				json.setMsg("注销商户成功");
			}else{
				json.setSuccess(false);
				json.setMsg("注销商户失败");
			}
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("注销商户异常");
			e.printStackTrace();
		}
		super.writeJson(json);
	}
	
	/**
	 * 查询业务员开通商户数汇总
	 * @author yq
	 */
	public void queryYwyMerchantinfo(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if(merchantInfo.getCreateDateStart() == null ||"".equals(merchantInfo.getCreateDateStart() == null) || merchantInfo.getCreateDateEnd() == null || "".equals(merchantInfo.getCreateDateEnd())){
				dgb = null;
			}else{              
				dgb = merchantInfoService.queryYwyMerchantinfo(merchantInfo,((UserBean)userSession));
			}
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询业务员开通商户数导出
	 * @author yq
	 */
	public void exportYwyMerchantinfo(){
		JsonBean json = new JsonBean();
        List<Map<String, Object>> list = null;
        List<String[]>cotents = new ArrayList<String[]>();
        String titles[] = {"机构号","机构名称","开通手刷商户数","开通收银台商户数","开通传统商户数","业务员"};
        cotents.add(titles);
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if(userSession!=null){
                list = merchantInfoService.exportYwyMerchantinfo(merchantInfo,(UserBean)userSession);
                if (list!=null&&list.size()>0) {
                    for (Map<String, Object>map  : list) {
                        String rowCoents[] = {
                                map.get("UNNO")==null?"":map.get("UNNO").toString(),
                                map.get("UN_NAME")==null?"":map.get("UN_NAME").toString(),
                                map.get("SSMER")==null?"":map.get("SSMER").toString(),
                                map.get("SYTMER")==null?"":map.get("SYTMER").toString(),
                                map.get("CTMER")==null?"":map.get("CTMER").toString(),
                                map.get("SALENAME")==null?"":map.get("SALENAME").toString()
                        };
                        cotents.add(rowCoents);
                    }
                }
                String excelName = "业务员开通商户数汇总报表.csv";
//                String [] cellFormatType = {"t","t","t","t","t","t","t","t","t"};
                JxlOutExcelUtil.writeCSVFile(cotents, titles.length, getResponse(), excelName);
            }
        } catch (Exception e) {
            log.error("业务员开通商户数汇总报表导出异常：" + e);
            json.setSuccess(false);
            json.setMsg("业务员开通商户数汇总报表导出失败");
            super.writeJson(json);
        }	
	}

    /**
     * @author ztt
     * plus商户入网资料-资料导出
     * */
    public void exportMicroMerPlus(){
        HttpServletResponse response=getResponse();
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        //session失效
        if (userSession == null) {
            json.setSessionExpire(true);
        } else {
            try{
                boolean isHidden = isHiddenPhoneNumber(merchantInfo);
                List<Map<String, Object>> list=merchantInfoService.exportMicroPlus(merchantInfo,((UserBean)userSession));

                List<String> excelHeader = new ArrayList<String>();
                List excelList = new ArrayList();
                UserBean userBean = (UserBean)userSession;
                String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
                String title [] = {		 "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
                        , "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","法人证件号","法人身份证有效期","开户银行","账户名称",
                        "账号","支付系统行号","入账人身份证号","入账人身份证有效期","营业地址省","营业地址市", "营业地址详细", "联系人", "电话", "商户结算借记卡费率", "借记卡封顶手续费"
                        , "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
                if (userBean.getUnitLvl() != 0) {
                    cellFormatType = new String[]{"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
                    title = new String[]{ "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
                            , "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","营业地址省","营业地址市", "商户结算借记卡费率", "借记卡封顶手续费"
                            , "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
                }
                excelList.add(title);

                for (int i = 0; i < list.size(); i++) {
                    Map map = list.get(i);
                    if (userBean.getUnitLvl() != 0) {
                        String []rowContents ={
                                map.get("MID")==null?"":map.get("MID").toString(),
                                map.get("TID")==null?"":map.get("TID").toString(),
                                map.get("RNAME")==null?"":map.get("RNAME").toString(),
                                map.get("UNNO")==null?"":map.get("UNNO").toString(),
                                map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
                                map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
                                map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
                                map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
                                map.get("BNO")==null?"":map.get("BNO").toString(),
                                map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
                                map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
                                map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),
                                map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
                                map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
                                map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
                                map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
                                map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
                                map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
                                map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
                                map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
                                map.get("SN")==null?"":map.get("SN").toString(),
                                map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
                                map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
                                map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
                                map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
                                map.get("REMARKS")==null?"":map.get("REMARKS").toString(),

                        };
                        excelList.add(rowContents);
                    }else {
                        String []rowContents ={
                                map.get("MID")==null?"":map.get("MID").toString(),
                                map.get("TID")==null?"":map.get("TID").toString(),
                                map.get("RNAME")==null?"":map.get("RNAME").toString(),
                                map.get("UNNO")==null?"":map.get("UNNO").toString(),
                                map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
                                map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
                                map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
                                map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
                                map.get("BNO")==null?"":map.get("BNO").toString(),
                                map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
                                map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
                                map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),
                                map.get("LEGALNUM")==null?"":map.get("LEGALNUM").toString().length()==18?map.get("LEGALNUM").toString().substring(0,4)+"****"+map.get("LEGALNUM").toString().substring(14):map.get("LEGALNUM").toString().substring(0,4)+"****",
                                map.get("LEGALEXPDATE")==null?"":map.get("LEGALEXPDATE").toString(),
                                map.get("BANKBRANCH")==null?"":map.get("BANKBRANCH").toString(),
                                map.get("BANKACCNAME")==null?"":map.get("BANKACCNAME").toString(),
                                map.get("BANKACCNO")==null?"0":map.get("BANKACCNO").toString(),
                                map.get("ACCNUM")==null?"":map.get("ACCNUM").toString().length()==18?map.get("ACCNUM").toString().substring(0,4)+"****"+map.get("ACCNUM").toString().substring(14):map.get("ACCNUM").toString().substring(0,4)+"****",
                                map.get("ACCEXPDATE")==null?"":map.get("ACCEXPDATE").toString(),
                                map.get("PAYBANKID")==null?"":map.get("PAYBANKID").toString(),
                                map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
                                map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
                                map.get("BADDR")==null?"":map.get("BADDR").toString(),
                                map.get("CONTACTPERSON")==null?"":map.get("CONTACTPERSON").toString(),
                                map.get("CONTACTPHONE")==null?"0":getHiddenPhoneNumber(isHidden,map.get("CONTACTPHONE").toString()),
                                map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
                                map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
                                map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
                                map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
                                map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
                                map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
                                map.get("SN")==null?"":map.get("SN").toString(),
                                map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
                                map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
                                map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
                                map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
                                map.get("REMARKS")==null?"":map.get("REMARKS").toString(),

                        };
                        excelList.add(rowContents);
                    }

                }
                String excelName = "Plus商户入网资料";
                String sheetName = "Plus商户入网资料";
                JxlOutExcelUtil.writeExcel(excelList, cellFormatType.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
                json.setSuccess(true);
                json.setMsg("导出Plus入网资料Excel成功");
            } catch (Exception e) {
                log.error("导出Plus入网资料Excel异常：" + e);
                json.setMsg("导出Plus入网资料Excel失败");
            }
        }
    }

    /**
     * @author ztt
     * plus商户入网资料-资料导出---选择导出
     * */
    public void exportMicroMerSelectedPlus(){
        String bmids=getRequest().getParameter("bmids");
        HttpServletResponse response=getResponse();
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        //session失效
        if (userSession == null) {
            json.setSessionExpire(true);
        } else {
            try{
                boolean isShow = isHiddenPhoneNumber(merchantInfo);
                List<Map<String, Object>> list=merchantInfoService.exportMicroMerSelectedPlus(bmids,(UserBean)userSession);
                List<String> excelHeader = new ArrayList<String>();
                List excelList = new ArrayList();
                UserBean userBean = (UserBean)userSession;
                String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
                String title [] = {		 "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
                        , "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","法人证件号","法人身份证有效期","开户银行","账户名称",
                        "账号","支付系统行号","入账人身份证号","入账人身份证有效期","营业地址省","营业地址市", "营业地址详细", "联系人", "电话", "商户结算借记卡费率", "借记卡封顶手续费"
                        , "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
                if (userBean.getUnitLvl() != 0) {
                    cellFormatType = new String[]{"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
                    title = new String[]{ "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
                            , "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","营业地址省","营业地址市", "商户结算借记卡费率", "借记卡封顶手续费"
                            , "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
                }
                excelList.add(title);

                for (int i = 0; i < list.size(); i++) {
                    Map map = list.get(i);
                    if (userBean.getUnitLvl() != 0) {
                        String []rowContents ={
                                map.get("MID")==null?"":map.get("MID").toString(),
                                map.get("TID")==null?"":map.get("TID").toString(),
                                map.get("RNAME")==null?"":map.get("RNAME").toString(),
                                map.get("UNNO")==null?"":map.get("UNNO").toString(),
                                map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
                                map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
                                map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
                                map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
                                map.get("BNO")==null?"":map.get("BNO").toString(),
                                map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
                                map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
                                map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),
                                map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
                                map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
                                map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
                                map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
                                map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
                                map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
                                map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
                                map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
                                map.get("SN")==null?"":map.get("SN").toString(),
                                map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
                                map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
                                map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
                                map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
                                map.get("REMARKS")==null?"":map.get("REMARKS").toString(),

                        };
                        excelList.add(rowContents);
                    }else {
                        String []rowContents ={
                                map.get("MID")==null?"":map.get("MID").toString(),
                                map.get("TID")==null?"":map.get("TID").toString(),
                                map.get("RNAME")==null?"":map.get("RNAME").toString(),
                                map.get("UNNO")==null?"":map.get("UNNO").toString(),
                                map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
                                map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
                                map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
                                map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
                                map.get("BNO")==null?"":map.get("BNO").toString(),
                                map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
                                map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
                                map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),
                                map.get("LEGALNUM")==null?"":map.get("LEGALNUM").toString().length()==18?map.get("LEGALNUM").toString().substring(0,4)+"****"+map.get("LEGALNUM").toString().substring(14):map.get("LEGALNUM").toString().substring(0,4)+"****",
                                map.get("LEGALEXPDATE")==null?"":map.get("LEGALEXPDATE").toString(),
                                map.get("BANKBRANCH")==null?"":map.get("BANKBRANCH").toString(),
                                map.get("BANKACCNAME")==null?"":map.get("BANKACCNAME").toString(),
                                map.get("BANKACCNO")==null?"0":map.get("BANKACCNO").toString(),
                                map.get("ACCNUM")==null?"":map.get("ACCNUM").toString().length()==18?map.get("ACCNUM").toString().substring(0,4)+"****"+map.get("ACCNUM").toString().substring(14):map.get("ACCNUM").toString().substring(0,4)+"****",
                                map.get("ACCEXPDATE")==null?"":map.get("ACCEXPDATE").toString(),
                                map.get("PAYBANKID")==null?"":map.get("PAYBANKID").toString(),
                                map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
                                map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
                                map.get("BADDR")==null?"":map.get("BADDR").toString(),
                                map.get("CONTACTPERSON")==null?"":map.get("CONTACTPERSON").toString(),
                                map.get("CONTACTPHONE")==null?"0":getHiddenPhoneNumber(isShow,map.get("CONTACTPHONE").toString()),
                                map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
                                map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
                                map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
                                map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
                                map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
                                map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
                                map.get("SN")==null?"":map.get("SN").toString(),
                                map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
                                map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
                                map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
                                map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
                                map.get("REMARKS")==null?"":map.get("REMARKS").toString(),

                        };
                        excelList.add(rowContents);
                    }

                }
                String excelName = "Plus商户入网资料";
                String sheetName = "Plus商户入网资料";
                JxlOutExcelUtil.writeExcel(excelList, cellFormatType.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
                json.setSuccess(true);
                json.setMsg("导出Plus商户入网资料Excel成功");
            } catch (Exception e) {
                log.error("导出Plus商户入网资料Excel异常：" + e);
                json.setMsg("导出Plus商户入网资料Excel失败");
            }
        }
    }

    /**
     * @author ztt
     * plus解绑-页面数据显示(只是查询到plus即可)
     * */
    public void listMerchantMicroToUnbidPlus(){
        DataGridBean dgb = new DataGridBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            dgb = merchantInfoService.queryMerchantMicroToUnbidPlus(merchantInfo,((UserBean)userSession));
        } catch (Exception e) {
            log.error("分页查询商户信息异常：" + e);
        }

        super.writeJson(dgb);
    }

    /**
     * 解绑Plus终端号
     */
    public void unbindPlusSN(){
        JsonBean json = new JsonBean();
        String sn = merchantInfo.getSn();
        String mid = merchantInfo.getMid();
        if(null != sn && !"".equals(sn)){
            try {
                String data = merchantInfoService.updateUnbindPlusSn(sn,mid);
                json.setSuccess(true);
                json.setMsg(data);
            } catch (Exception e) {

                json.setSuccess(false);
                json.setMsg("解绑终端号异常");
                e.printStackTrace();
            }
        }else{
            json.setSuccess(false);
            json.setMsg("终端号为空");
        }

        super.writeJson(json);
    }


	/**
	 * 批量解绑模板下载
	 */
	public void dowloadMerchantMicroToUnbid(){
		try {
			ExcelServiceImpl.download(getResponse(),"SnUnbidTemp.xls","批量解绑导入模板.xls");
		} catch (Exception e) {
			log.info("批量解绑导入模板下载失败:"+e);
		}
	}

	/**
	 * 批量解绑模板导入
	 */
	public void importMicroToUnbid(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null){
			json.setSessionExpire(true);
		}
		String UUID = System.currentTimeMillis()+"";
		//把文件保存到服务器目录下
		String fileName = UUID+ServletActionContext.getRequest().getParameter("fileContact");
		String basePath = ServletActionContext.getRequest().getRealPath("/upload");
		File dir = new File(basePath);
		if(!dir.exists())
			dir.mkdir();
		String path = basePath +"/"+fileName;
		File destFile = new File(path);
		upload.renameTo(destFile);
		String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
		String xlsfile = folderPath +"/"+fileName;
		log.info("批量解绑导入Excel文件路径:"+xlsfile);

		try {
			if(xlsfile.length() > 0 && xlsfile != null){
				List<Map> list = merchantInfoService.updateImportMicroToUnbid(xlsfile,fileName,user);
				if(list.size()==0){
					json.setSuccess(true);
					json.setMsg("批量解绑导入成功!");
				}else{
					List<String[]>cotents = new ArrayList<String[]>();
					String titles[] = {"mid","sn","失败信息"};
					cotents.add(titles);
					if (list!=null&&list.size()>0) {
						for (Map<String, String> map : list) {
							String rowCoents[] = {
									map.get("mid")==null?"":map.get("mid").toString(),
									map.get("sn")==null?"":map.get("sn").toString(),
									map.get("errorInfo")==null?"":map.get("errorInfo").toString(),
							};
							cotents.add(rowCoents);
						}
					}
					String excelName = "批量解绑导入失败.csv";
					JxlOutExcelUtil.writeCSVFile(cotents, titles.length, getResponse(), excelName);
					json.setSuccess(false);
					json.setMsg("解绑失败");
				}
			}else{
				json.setSuccess(false);
				json.setMsg("导入文件异常!");
			}
		} catch (Exception e) {
			log.info("批量解绑导入异常:"+e);
			json.setSuccess(false);
			json.setMsg("批量解绑导入失败!");
		}
		ExcelServiceImpl.deleteUploadFile(folderPath,path);
		super.writeJson(json);
	}
	
	/**
	 * 查看导出日志记录
	 * @author YQ -20191118
	 */
	public void queryExportLog(){
		 DataGridBean dgb = new DataGridBean();
	     try {
	    	 Object userSession = super.getRequest().getSession().getAttribute("user");
	    	 if(userSession == null){
	    		 super.writeJson(dgb);
	 		 }
	         dgb = merchantInfoService.queryExportLog(merchantInfo);
	     } catch (Exception e) {
	         log.error("分页查询日志信息异常：" + e);
	     }
	     super.writeJson(dgb);
	}
	
	/**
	 * 导出导出日志记录
	 * @author YQ -20191118
	 */
	public void export10972(){
		JsonBean json = new JsonBean();
        List<Map<String, Object>> list = null;
        List<String[]>cotents = new ArrayList<String[]>();
        String titles[] = {"导出类型","导出人员","导出时间","导出数量"};
        cotents.add(titles);
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if(userSession!=null){
                list = merchantInfoService.export10972(merchantInfo);
                if (list!=null&&list.size()>0) {
                    for (Map<String, Object>map  : list) {
                        String rowCoents[] = {
                                map.get("TYPE")==null?"":map.get("TYPE").toString(),
                                map.get("USERNAME")==null?"":map.get("USERNAME").toString(),
                                map.get("CDATE")==null?"":map.get("CDATE").toString(),
                                map.get("TOTALS")==null?"":map.get("TOTALS").toString(),
                        };
                        cotents.add(rowCoents);
                    }
                }
                String excelName = "导出记录报表.csv";
                JxlOutExcelUtil.writeCSVFile(cotents, titles.length, getResponse(), excelName);
            }
        } catch (Exception e) {
            log.error("导出记录报表导出异常：" + e);
            json.setSuccess(false);
            json.setMsg("导出记录报表导出失败");
            super.writeJson(json);
        }	
	}
	
	/**
	 * 人工实名认证 ---线下导入
	 */
	public void ImportAuthType(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		if(user == null)json.setSessionExpire(true);
		//把文件保存到服务器目录下
		String fileName = ServletActionContext.getRequest().getParameter("file10973Name");
		String basePath = ServletActionContext.getRequest().getRealPath("/upload");
		File dir = new File(basePath);
		dir.mkdir();
		// 使用UUID做为文件名，以解决重名的问题
		String path = basePath +"/"+fileName;
		File destFile = new File(path);
		upload.renameTo(destFile);
		String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
		String xlsfile = folderPath +"/"+fileName;
		try {
			if(xlsfile.length() > 0 && xlsfile != null){
				List<Map<String,String>> list = null;
				list = merchantInfoService.ImportAuthType(xlsfile,fileName,user);
				if(list!=null&&list.size() > 0){
					String excelName= "人工实名认证线下导入记录";
					String[] title = {"商户号","收款人账号","收款人名称","身份证号","操作","备注"};
					List excellist = new ArrayList();
					excellist.add(title);
					for(int rowId = 0;rowId<list.size();rowId++){
						Map<String, String> order = list.get(rowId);
						String[] rowContents = {
								order.get("mid")==null?"":order.get("mid"),
								order.get("bankAccNo")==null?"":order.get("bankAccNo"),
								order.get("bankAccName")==null?"":order.get("bankAccName"),
								order.get("legalNum")==null?"":order.get("legalNum"),
								order.get("authTypeStatus")==null?"":order.get("authTypeStatus"),
								order.get("remark")==null?"":order.get("remark")};
						excellist.add(rowContents);
					}
					String[] cellFormatType = {"t","t","t","t","t","t"};
					UsePioOutExcel.writeExcel(excellist, 6, super.getResponse(), excelName, excelName+".xls", cellFormatType);					
				}else{
					json.setSuccess(true);
					json.setMsg("记录更新失败!");
					super.writeJson(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("文件更新失败!");
			super.writeJson(json);
		}
		File file = new File(folderPath);
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(folderPath + tempList[i]);
			} else {
				temp = new File(folderPath + File.separator + tempList[i]);
			}
			if (temp.exists() && temp.isFile()) {
				boolean flag = temp.delete(); // 删除上传到服务器的文件
				int j = i + 1;
				log.error("成功删除文件:第" + j + "个文件删除是否成功？" + flag);
			} else {
				log.error("文件不存在");
			}
		}
	}
	
	 /**
     * 会员宝Pro入网报单
     */
	public void listMerchantMicroInfoZKPro(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMerchantMicroInfoZKPro(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}

		super.writeJson(dgb);
	}
	
	/**
	 * 会员宝Pro商户审批
	 */
	public void listMicroMerchantInfoWJoinPro(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMicroMerchantInfoWJoinPro(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询微商户信息异常：" + e);
		}

		super.writeJson(dgb);
	}
	
	/**
	 * 会员宝Pro入网资料
	 */
	public void listMicroMerchantInfoYPro(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantInfoService.queryMicroMerchantInfoYPro(merchantInfo,((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询商户信息异常：" + e);
		}

		super.writeJson(dgb);
	}
	
	/**
     * 会员宝Pro商户入网资料-资料导出
     * YQ
     **/
    public void exportMicroMerPro(){
        HttpServletResponse response=getResponse();
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        //session失效
        if (userSession == null) {
            json.setSessionExpire(true);
        } else {
            try{
                boolean isHidden = isHiddenPhoneNumber(merchantInfo);
                List<Map<String, Object>> list=merchantInfoService.exportMicroPro(merchantInfo,((UserBean)userSession));
                List<String> excelHeader = new ArrayList<String>();
                List excelList = new ArrayList();
                UserBean userBean = (UserBean)userSession;
                String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
                String title [] = {		 "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
                        , "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","法人证件号","法人身份证有效期","开户银行","账户名称",
                        "账号","支付系统行号","入账人身份证号","入账人身份证有效期","营业地址省","营业地址市", "营业地址详细", "联系人", "电话", "商户结算借记卡费率", "借记卡封顶手续费"
                        , "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
                if (userBean.getUnitLvl() != 0) {
                    cellFormatType = new String[]{"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
                    title = new String[]{ "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
                            , "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","营业地址省","营业地址市", "商户结算借记卡费率", "借记卡封顶手续费"
                            , "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
                }
                excelList.add(title);
                for (int i = 0; i < list.size(); i++) {
                    Map map = list.get(i);
                    if (userBean.getUnitLvl() != 0) {
                        String []rowContents ={
                                map.get("MID")==null?"":map.get("MID").toString(),
                                map.get("TID")==null?"":map.get("TID").toString(),
                                map.get("RNAME")==null?"":map.get("RNAME").toString(),
                                map.get("UNNO")==null?"":map.get("UNNO").toString(),
                                map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
                                map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
                                map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
                                map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
                                map.get("BNO")==null?"":map.get("BNO").toString(),
                                map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
                                map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
                                map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),
                                map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
                                map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
                                map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
                                map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
                                map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
                                map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
                                map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
                                map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
                                map.get("SN")==null?"":map.get("SN").toString(),
                                map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
                                map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
                                map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
                                map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
                                map.get("REMARKS")==null?"":map.get("REMARKS").toString(),
                        };
                        excelList.add(rowContents);
                    }else {
                        String []rowContents ={
                                map.get("MID")==null?"":map.get("MID").toString(),
                                map.get("TID")==null?"":map.get("TID").toString(),
                                map.get("RNAME")==null?"":map.get("RNAME").toString(),
                                map.get("UNNO")==null?"":map.get("UNNO").toString(),
                                map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
                                map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
                                map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
                                map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
                                map.get("BNO")==null?"":map.get("BNO").toString(),
                                map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
                                map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
                                map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),
                                map.get("LEGALNUM")==null?"":map.get("LEGALNUM").toString().length()==18?map.get("LEGALNUM").toString().substring(0,4)+"****"+map.get("LEGALNUM").toString().substring(14):map.get("LEGALNUM").toString().substring(0,4)+"****",
                                map.get("LEGALEXPDATE")==null?"":map.get("LEGALEXPDATE").toString(),
                                map.get("BANKBRANCH")==null?"":map.get("BANKBRANCH").toString(),
                                map.get("BANKACCNAME")==null?"":map.get("BANKACCNAME").toString(),
                                map.get("BANKACCNO")==null?"0":map.get("BANKACCNO").toString(),
                                map.get("ACCNUM")==null?"":map.get("ACCNUM").toString().length()==18?map.get("ACCNUM").toString().substring(0,4)+"****"+map.get("ACCNUM").toString().substring(14):map.get("ACCNUM").toString().substring(0,4)+"****",
                                map.get("ACCEXPDATE")==null?"":map.get("ACCEXPDATE").toString(),
                                map.get("PAYBANKID")==null?"":map.get("PAYBANKID").toString(),
                                map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
                                map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
                                map.get("BADDR")==null?"":map.get("BADDR").toString(),
                                map.get("CONTACTPERSON")==null?"":map.get("CONTACTPERSON").toString(),
                                map.get("CONTACTPHONE")==null?"0":getHiddenPhoneNumber(isHidden,map.get("CONTACTPHONE").toString()),
                                map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
                                map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
                                map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
                                map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
                                map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
                                map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
                                map.get("SN")==null?"":map.get("SN").toString(),
                                map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
                                map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
                                map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
                                map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
                                map.get("REMARKS")==null?"":map.get("REMARKS").toString(),
                        };
                        excelList.add(rowContents);
                    }
                }
                String excelName = "会员宝Pro商户入网资料";
                String sheetName = "会员宝Pro商户入网资料";
                JxlOutExcelUtil.writeExcel(excelList, cellFormatType.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
                json.setSuccess(true);
                json.setMsg("导出会员宝Pro入网资料Excel成功");
            } catch (Exception e) {
                log.error("导出会员宝Pro入网资料Excel异常：" + e);
                json.setMsg("导出会员宝Pro入网资料Excel失败");
            }
        }
    }
	
    /**
     * 会员宝Pro商户入网资料勾选导出
     * YQ
     **/
    public void exportMicroMerSelectedPro(){
        String bmids=getRequest().getParameter("bmids");
        HttpServletResponse response=getResponse();
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        //session失效
        if (userSession == null) {
            json.setSessionExpire(true);
        } else {
            try{
                boolean isShow = isHiddenPhoneNumber(merchantInfo);
                List<Map<String, Object>> list=merchantInfoService.exportMicroMerSelectedPro(bmids,(UserBean)userSession);
                List<String> excelHeader = new ArrayList<String>();
                List excelList = new ArrayList();
                UserBean userBean = (UserBean)userSession;
                String [] cellFormatType = {"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
                String title [] = {		 "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
                        , "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","法人证件号","法人身份证有效期","开户银行","账户名称",
                        "账号","支付系统行号","入账人身份证号","入账人身份证有效期","营业地址省","营业地址市", "营业地址详细", "联系人", "电话", "商户结算借记卡费率", "借记卡封顶手续费"
                        , "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
                if (userBean.getUnitLvl() != 0) {
                    cellFormatType = new String[]{"t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
                    title = new String[]{ "公司mid", "公司tid", "商户名称",  "机构编号", "机构名称", "一级机构"
                            , "归属机构","是否秒到", "营业执照号","营业执照号有效期","商户审批日期", "法人姓名","营业地址省","营业地址市", "商户结算借记卡费率", "借记卡封顶手续费"
                            , "贷记卡结算费率", "结算周期", "销售", "机型", "SN","设备绑定时间","设备审批时间",  "商户操作日期", "受理人员", "备注"};
                }
                excelList.add(title);
                for (int i = 0; i < list.size(); i++) {
                    Map map = list.get(i);
                    if (userBean.getUnitLvl() != 0) {
                        String []rowContents ={
                                map.get("MID")==null?"":map.get("MID").toString(),
                                map.get("TID")==null?"":map.get("TID").toString(),
                                map.get("RNAME")==null?"":map.get("RNAME").toString(),
                                map.get("UNNO")==null?"":map.get("UNNO").toString(),
                                map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
                                map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
                                map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
                                map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
                                map.get("BNO")==null?"":map.get("BNO").toString(),
                                map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
                                map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
                                map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),
                                map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
                                map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
                                map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
                                map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
                                map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
                                map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
                                map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
                                map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
                                map.get("SN")==null?"":map.get("SN").toString(),
                                map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
                                map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
                                map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
                                map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
                                map.get("REMARKS")==null?"":map.get("REMARKS").toString(),
                        };
                        excelList.add(rowContents);
                    }else {
                        String []rowContents ={
                                map.get("MID")==null?"":map.get("MID").toString(),
                                map.get("TID")==null?"":map.get("TID").toString(),
                                map.get("RNAME")==null?"":map.get("RNAME").toString(),
                                map.get("UNNO")==null?"":map.get("UNNO").toString(),
                                map.get("UNNONAME")==null?"":map.get("UNNONAME").toString(),
                                map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
                                map.get("PARENTUNITNAME")==null?"":map.get("PARENTUNITNAME").toString(),
                                map.get("SETTMETHOD")==null?"":map.get("SETTMETHOD").toString(),
                                map.get("BNO")==null?"":map.get("BNO").toString(),
                                map.get("BNOEXPDATE")==null?"0":map.get("BNOEXPDATE").toString(),
                                map.get("APPROVEDATE")==null?"0":map.get("APPROVEDATE").toString(),
                                map.get("LEGALPERSON")==null?"":map.get("LEGALPERSON").toString(),
                                map.get("LEGALNUM")==null?"":map.get("LEGALNUM").toString().length()==18?map.get("LEGALNUM").toString().substring(0,4)+"****"+map.get("LEGALNUM").toString().substring(14):map.get("LEGALNUM").toString().substring(0,4)+"****",
                                map.get("LEGALEXPDATE")==null?"":map.get("LEGALEXPDATE").toString(),
                                map.get("BANKBRANCH")==null?"":map.get("BANKBRANCH").toString(),
                                map.get("BANKACCNAME")==null?"":map.get("BANKACCNAME").toString(),
                                map.get("BANKACCNO")==null?"0":map.get("BANKACCNO").toString(),
                                map.get("ACCNUM")==null?"":map.get("ACCNUM").toString().length()==18?map.get("ACCNUM").toString().substring(0,4)+"****"+map.get("ACCNUM").toString().substring(14):map.get("ACCNUM").toString().substring(0,4)+"****",
                                map.get("ACCEXPDATE")==null?"":map.get("ACCEXPDATE").toString(),
                                map.get("PAYBANKID")==null?"":map.get("PAYBANKID").toString(),
                                map.get("PROVINCE")==null?"":map.get("PROVINCE").toString(),
                                map.get("AREA_NAME")==null?"":map.get("AREA_NAME").toString(),
                                map.get("BADDR")==null?"":map.get("BADDR").toString(),
                                map.get("CONTACTPERSON")==null?"":map.get("CONTACTPERSON").toString(),
                                map.get("CONTACTPHONE")==null?"0":getHiddenPhoneNumber(isShow,map.get("CONTACTPHONE").toString()),
                                map.get("BANKFEERATE")==null?"":map.get("BANKFEERATE").toString(),
                                map.get("FEEAMT")==null?"":map.get("FEEAMT").toString(),
                                map.get("CREDITBANKRATE")==null?"0":map.get("CREDITBANKRATE").toString(),
                                map.get("SETTLECYCLE")==null?"":map.get("SETTLECYCLE").toString(),
                                map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
                                map.get("MACHINEMODEL")==null?"":map.get("MACHINEMODEL").toString(),
                                map.get("SN")==null?"":map.get("SN").toString(),
                                map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
                                map.get("IAPPROVEDATE")==null?"":map.get("IAPPROVEDATE").toString(),
                                map.get("MAINTAINDATE")==null?"":map.get("MAINTAINDATE").toString(),
                                map.get("APPROVEUIDNAME")==null?"":map.get("APPROVEUIDNAME").toString(),
                                map.get("REMARKS")==null?"":map.get("REMARKS").toString(),
                        };
                        excelList.add(rowContents);
                    }
                }
                String excelName = "Pro商户入网资料";
                String sheetName = "Pro商户入网资料";
                JxlOutExcelUtil.writeExcel(excelList, cellFormatType.length, getResponse(), sheetName, excelName+".xls", cellFormatType);
                json.setSuccess(true);
                json.setMsg("导出Pro商户入网资料Excel成功");
            } catch (Exception e) {
                log.error("导出Pro商户入网资料Excel异常：" + e);
                json.setMsg("导出Pro商户入网资料Excel失败");
            }
        }
    }
}
