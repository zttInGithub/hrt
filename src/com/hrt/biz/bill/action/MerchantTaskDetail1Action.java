package com.hrt.biz.bill.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.frame.constant.Constant;
import com.hrt.util.PictureWaterMarkUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail1Model;
import com.hrt.biz.bill.service.IMerchantTaskDetail1Service;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;

/**
 * 商户基本信息工单申请
 */
public class MerchantTaskDetail1Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(MerchantTaskDetail1Action.class);
	
	//根据商户机构编号查询商户信息
	private String unno;		 
	private IMerchantTaskDetail1Service merchantTaskDetail1Service;
	private MerchantTaskDetail1Model merchantTaskDetail1;
	private String mid;
	private List<MerchantInfoModel> list;
	//上传
	private File legalUpload;
	private File legalUpload2;
	private File bupload;
	private File rupload;
	private File registryUpLoad;
	private File materialUpLoad;
	private String legalUploadFileName;
	private String legalUpload2FileName;
	private String buploadFileName;
	private String ruploadFileName;
	private String registryUpLoadFileName;
	private String materialUpLoadFileName;
	
	
	public File getLegalUpload2() {
		return legalUpload2;
	}
	public void setLegalUpload2(File legalUpload2) {
		this.legalUpload2 = legalUpload2;
	}
	public String getLegalUpload2FileName() {
		return legalUpload2FileName;
	}
	public void setLegalUpload2FileName(String legalUpload2FileName) {
		this.legalUpload2FileName = legalUpload2FileName;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public IMerchantTaskDetail1Service getMerchantTaskDetail1Service() {
		return merchantTaskDetail1Service;
	}
	public void setMerchantTaskDetail1Service(IMerchantTaskDetail1Service merchantTaskDetail1Service) {
		this.merchantTaskDetail1Service = merchantTaskDetail1Service;
	}
	public MerchantTaskDetail1Model getMerchantTaskDetail1() {
		return merchantTaskDetail1;
	}
	public void setMerchantTaskDetail1(MerchantTaskDetail1Model merchantTaskDetail1) {
		this.merchantTaskDetail1 = merchantTaskDetail1;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public List<MerchantInfoModel> getList() {
		return list;
	}
	public void setList(List<MerchantInfoModel> list) {
		this.list = list;
	}
	public File getLegalUpload() {
		return legalUpload;
	}
	public void setLegalUpload(File legalUpload) {
		this.legalUpload = legalUpload;
	}

	public File getBupload() {
		return bupload;
	}
	public void setBupload(File bupload) {
		this.bupload = bupload;
	}
	public File getRupload() {
		return rupload;
	}
	public void setRupload(File rupload) {
		this.rupload = rupload;
	}
	public File getRegistryUpLoad() {
		return registryUpLoad;
	}
	public void setRegistryUpLoad(File registryUpLoad) {
		this.registryUpLoad = registryUpLoad;
	}
	public File getMaterialUpLoad() {
		return materialUpLoad;
	}
	public void setMaterialUpLoad(File materialUpLoad) {
		this.materialUpLoad = materialUpLoad;
	}
	public String getLegalUploadFileName() {
		return legalUploadFileName;
	}
	public void setLegalUploadFileName(String legalUploadFileName) {
		this.legalUploadFileName = legalUploadFileName;
	}
	
	public String getBuploadFileName() {
		return buploadFileName;
	}
	public void setBuploadFileName(String buploadFileName) {
		this.buploadFileName = buploadFileName;
	}
	public String getRuploadFileName() {
		return ruploadFileName;
	}
	public void setRuploadFileName(String ruploadFileName) {
		this.ruploadFileName = ruploadFileName;
	}
	
	public String getRegistryUpLoadFileName() {
		return registryUpLoadFileName;
	}
	public void setRegistryUpLoadFileName(String registryUpLoadFileName) {
		this.registryUpLoadFileName = registryUpLoadFileName;
	}
	public String getMaterialUpLoadFileName() {
		return materialUpLoadFileName;
	}
	public void setMaterialUpLoadFileName(String materialUpLoadFileName) {
		this.materialUpLoadFileName = materialUpLoadFileName;
	}
	
	
	/**
	 * 根据商户编号查询商户基本信息
	 */
	public void serchMerchantInfo(){
		//DataGridBean dgd = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		list=merchantTaskDetail1Service.queryMerchantInfo(mid,(UserBean)userSession);
		super.writeJson(list); 
	}
	
	/**
	 * 保存商户提交的商户基本信息工单申请
	 */
	public void addMerchantTaskDetail1(){
		Map<String ,String> map=new HashMap<String ,String >();
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd"); 
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		Integer foo=0;			//判断文件大小状态码
		String uploadPath=merchantTaskDetail1Service.queryUpLoadPath();
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				if(legalUpload==null){
					map.put("1", "");
				}else{
					FileInputStream fis=new FileInputStream(legalUpload);
					if(fis.available()>1024*512){
						foo+=1;
					}else{
//						map.put("1", unno.trim()+"."+mid+"."+sf.format(new Date())+"."+(1)+legalUploadFileName.substring(legalUploadFileName.lastIndexOf(".")));
						map.put("1", mid+"."+sf.format(new Date())+"."+(1)+legalUploadFileName.substring(legalUploadFileName.lastIndexOf(".")));
						File file= new File(uploadPath+File.separator+sf.format(new Date()).trim()); 
						if(!file.exists()){
							file.mkdirs();
						}
						// @date:20190125 商户基本信息修改上传的图片添加水印
                        String filePath = uploadPath+File.separator+sf.format(new Date()).trim()+File.separator+
                                map.get("1").trim();
                        String fileName = map.get("1").trim();
						PictureWaterMarkUtil.addWatermark(fis,filePath, Constant.PICTURE_WATER_MARK,
                                fileName.substring(fileName.lastIndexOf(".")+1));
//						FileOutputStream fos=new FileOutputStream(file+File.separator+map.get("1").trim());
//						byte [] buffer=new byte[1024];
//						int len=0;
//						while((len=fis.read(buffer))>0){
//							fos.write(buffer,0,len);
//						}
//						fos.close();
					}
					fis.close(); 
				}
				if(bupload==null){
					map.put("2", "");
				}else{
					FileInputStream fis=new FileInputStream(bupload);
					if(fis.available()>1024*512){
						foo+=1;
					}else{
//						map.put("2", unno.trim()+"."+mid+"."+sf.format(new Date())+"."+(2)+buploadFileName.substring(buploadFileName.lastIndexOf(".")));
						map.put("2", mid+"."+sf.format(new Date())+"."+(2)+buploadFileName.substring(buploadFileName.lastIndexOf(".")));
						File file= new File(uploadPath+File.separator+sf.format(new Date()).trim()); 
						if(!file.exists()){
							file.mkdirs();
						}
                        // @date:20190125 商户基本信息修改上传的图片添加水印
                        String filePath = uploadPath+File.separator+sf.format(new Date()).trim()+File.separator+
                                map.get("2").trim();
                        String fileName = map.get("2").trim();
                        PictureWaterMarkUtil.addWatermark(fis,filePath, Constant.PICTURE_WATER_MARK,
                                fileName.substring(fileName.lastIndexOf(".")+1));
//						FileOutputStream fos=new FileOutputStream(file+File.separator+map.get("2").trim());
//						byte [] buffer=new byte[1024];
//						int len=0;
//						while((len=fis.read(buffer))>0){
//							fos.write(buffer,0,len);
//						}
//						fos.close();
					}
					fis.close();
				}
				if(rupload==null){
					map.put("3", "");
				}else{
					FileInputStream fis=new FileInputStream(rupload);
					if(fis.available()>1024*512){
						foo+=1;
					}else{
//						map.put("3", unno.trim()+"."+mid+"."+sf.format(new Date())+"."+(3)+ruploadFileName.substring(ruploadFileName.lastIndexOf(".")));
						map.put("3", mid+"."+sf.format(new Date())+"."+(3)+ruploadFileName.substring(ruploadFileName.lastIndexOf(".")));
						File file= new File(uploadPath+File.separator+sf.format(new Date()).trim()); 
						if(!file.exists()){
							file.mkdirs();
						}
                        // @date:20190125 商户基本信息修改上传的图片添加水印
                        String filePath = uploadPath+File.separator+sf.format(new Date()).trim()+File.separator+
                                map.get("3").trim();
                        String fileName = map.get("3").trim();
                        PictureWaterMarkUtil.addWatermark(fis,filePath, Constant.PICTURE_WATER_MARK,
                                fileName.substring(fileName.lastIndexOf(".")+1));
//						FileOutputStream fos=new FileOutputStream(file+File.separator+map.get("3").trim());
//						byte [] buffer=new byte[1024];
//						int len=0;
//						while((len=fis.read(buffer))>0){
//							fos.write(buffer,0,len);
//						}
//						fos.close();
					}
					fis.close();
				}
				if(registryUpLoad==null){
					map.put("4", "");
				}else{
					FileInputStream fis=new FileInputStream(registryUpLoad);
					if(fis.available()>1024*512){
						foo+=1;
					}else{
//						map.put("4", unno.trim()+"."+mid+"."+sf.format(new Date())+"."+(4)+registryUpLoadFileName.substring(registryUpLoadFileName.lastIndexOf(".")));
						map.put("4", mid+"."+sf.format(new Date())+"."+(4)+registryUpLoadFileName.substring(registryUpLoadFileName.lastIndexOf(".")));
						File file= new File(uploadPath+File.separator+sf.format(new Date()).trim()); 
						if(!file.exists()){
							file.mkdirs(); 
						}
                        // @date:20190125 商户基本信息修改上传的图片添加水印
                        String filePath = uploadPath+File.separator+sf.format(new Date()).trim()+File.separator+
                                map.get("4").trim();
                        String fileName = map.get("4").trim();
                        PictureWaterMarkUtil.addWatermark(fis,filePath, Constant.PICTURE_WATER_MARK,
                                fileName.substring(fileName.lastIndexOf(".")+1));
//						FileOutputStream fos=new FileOutputStream(file+File.separator+map.get("4").trim());
//						byte [] buffer=new byte[1024];
//						int len=0;
//						while((len=fis.read(buffer))>0){
//							fos.write(buffer,0,len);
//						}
//						fos.close();
					}
					fis.close();
				}
				if(materialUpLoad==null){
					map.put("5", "");
				}else{
					FileInputStream fis=new FileInputStream(materialUpLoad);
					if(fis.available()>1024*512){
						foo+=1;
					}else{
//						map.put("5", unno.trim()+"."+mid+"."+sf.format(new Date())+"."+(5)+materialUpLoadFileName.substring(materialUpLoadFileName.lastIndexOf(".")));
						map.put("5", mid+"."+sf.format(new Date())+"."+(5)+materialUpLoadFileName.substring(materialUpLoadFileName.lastIndexOf(".")));
						File file= new File(uploadPath+File.separator+sf.format(new Date()).trim()); 
						if(!file.exists()){
							file.mkdirs();
						}
						// @date:20190125 商户基本信息修改上传的图片添加水印
                        String filePath = uploadPath+File.separator+sf.format(new Date()).trim()+File.separator+
                                map.get("5").trim();
                        String fileName = map.get("5").trim();
                        PictureWaterMarkUtil.addWatermark(fis,filePath, Constant.PICTURE_WATER_MARK,
                                fileName.substring(fileName.lastIndexOf(".")+1));
//						FileOutputStream fos=new FileOutputStream(file+File.separator+map.get("5").trim());
//						byte [] buffer=new byte[1024];
//						int len=0;
//						while((len=fis.read(buffer))>0){
//							fos.write(buffer,0,len);
//						}
//						fos.close();
					}
					fis.close(); 
				}
				if(legalUpload2==null){
					map.put("6", "");
				}else{
					FileInputStream fis=new FileInputStream(legalUpload2);
					if(fis.available()>1024*512){
						foo+=1;
					}else{
						map.put("6", mid+"."+sf.format(new Date())+"."+(6)+legalUpload2FileName.substring(legalUpload2FileName.lastIndexOf(".")));
						File file= new File(uploadPath+File.separator+sf.format(new Date()).trim()); 
						if(!file.exists()){
							file.mkdirs();
						}
						// @date:20190125 商户基本信息修改上传的图片添加水印
                        String filePath = uploadPath+File.separator+sf.format(new Date()).trim()+File.separator+
                                map.get("6").trim();
                        String fileName = map.get("6").trim();
                        PictureWaterMarkUtil.addWatermark(fis,filePath, Constant.PICTURE_WATER_MARK,
                                fileName.substring(fileName.lastIndexOf(".")+1));
//						FileOutputStream fos=new FileOutputStream(file+File.separator+map.get("6").trim());
//						byte [] buffer=new byte[1024];
//						int len=0;
//						while((len=fis.read(buffer))>0){
//							fos.write(buffer,0,len);
//						}
//						fos.close();
					}
					fis.close(); 
				}
				//判断是否存在待审批的工单
				boolean flag = merchantTaskDetail1Service.queryMerchantTaskDetailByMid(mid);
				if(flag){
					json.setSuccess(false);  
					json.setMsg("已存在待审批的工单！请等待其审批后再次提交");
					super.writeJson(json); 
					return ;
				}
				if(foo==0){ 
					merchantTaskDetail1.setLegalUploadFileName(map.get("1"));
					merchantTaskDetail1.setBupload(map.get("2"));
					merchantTaskDetail1.setRupload(map.get("3"));
					merchantTaskDetail1.setRegistryUpLoad(map.get("4")); 
					merchantTaskDetail1.setMaterialUpLoad(map.get("5"));
					merchantTaskDetail1.setLegalUpload2FileName(map.get("6"));//新加身份证反面
					merchantTaskDetail1Service.saveMerchantTaskDetail1(merchantTaskDetail1,unno,mid,(UserBean)userSession);
					json.setSuccess(true);  
					json.setMsg("工单提交成功");
				}else{
					json.setMsg("工单提交失败，您上传的文件大于512Kb！"); 
				}
	
			} catch (Exception e) {
				log.error("工单提交异常：" + e);
				json.setMsg("工单提交失败");
			}
		}
		super.writeJson(json); 
	}
}
