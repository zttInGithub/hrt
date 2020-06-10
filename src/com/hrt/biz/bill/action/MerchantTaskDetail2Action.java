package com.hrt.biz.bill.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.util.CommonTools;
import com.hrt.frame.constant.Constant;
import com.hrt.util.PictureWaterMarkUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail2Model;
import com.hrt.biz.bill.service.IMerchantTaskDetail2Service;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;

/**
 * 商户银行信息工单申请
 */
public class MerchantTaskDetail2Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(MerchantTaskDetail2Action.class);
	//根据商户机构编号查询商户信息
	private String unno;		
	private IMerchantTaskDetail2Service merchantTaskDetail2Service;
	private MerchantTaskDetail2Model merchantTaskDetail2;
	private String mid;
	private List<MerchantInfoModel> list;
	private File accUpLoad;
	private File authUpLoad;
	private File dUpLoad;
	private File openUpLoad;
	private String accUpLoadFileName;
	
	
	/**
	 * 根据商户机构编号查询商户银行信息
	 */
	public void serchMerchantInfo(){
		//DataGridBean dgd = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		list=merchantTaskDetail2Service.queryMerchantInfo(mid,(UserBean)userSession);
		//list=merchantTaskDetail2Service.queryMerchantInfo(mid); 
		super.writeJson(list);
	}

	public void addMerchantTaskDetail2DataV2(){
		// @author:lxg-20191114 敏感信息加密处理
		try {
			String data=getRequest().getParameter("data");
			String aesEn=getRequest().getParameter("aesEn");
			String sck= CommonTools.getSck(aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck,data);
			JSONObject t=JSON.parseObject(data0);
			if(t.containsKey("mid")){
				mid=t.getString("mid");
			}
			if(t.containsKey("accUpLoadFileName")){
				accUpLoadFileName=t.getString("accUpLoadFileName");
			}
			merchantTaskDetail2= JSONObject.parseObject(data0, MerchantTaskDetail2Model.class);
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
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		String uploadPath=merchantTaskDetail2Service.queryUpLoadPath();
		Integer imgIsRight=0;
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				if(accUpLoad!=null){

//					map.put("1", unno.trim()+"."+mid+"."+sf.format(new Date())+"."+(6)+accUpLoadFileName.substring(accUpLoadFileName.lastIndexOf(".")));
					map.put("1", mid+"."+sf.format(new Date())+"."+(6)+accUpLoadFileName.substring(accUpLoadFileName.lastIndexOf(".")));
					FileInputStream fis=new FileInputStream(accUpLoad);
					//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%上传文件大小"+fis.available());
					if(fis.available()>1024*512){
						++imgIsRight;
					}else{
						File file= new File(uploadPath+File.separator+sf.format(new Date()).trim()); 
						if(!file.exists()){
							file.mkdirs(); 
						}
						// @date:20190125 商户银行信息修改上传的图片添加水印
                        String filePath = uploadPath+File.separator+sf.format(new Date()).trim()+File.separator+
                                map.get("1").trim();
                        String fileName = map.get("1").trim();
						PictureWaterMarkUtil.addWatermark(fis,filePath
								, Constant.PICTURE_WATER_MARK,
                                fileName.trim().substring(fileName.lastIndexOf(".")+1));
//						FileOutputStream fos=new FileOutputStream(file+File.separator+map.get("1"));
//						byte [] buffer=new byte[1024];
//						int len=0;
//						while((len=fis.read(buffer))>0){
//							fos.write(buffer,0,len);
//						}
//						fos.close();
						merchantTaskDetail2.setAccUpLoad(map.get("1"));

					}
					fis.close(); 
				}
				if(authUpLoad!=null){
				
//					map.put("2", unno.trim()+"."+mid+"."+sf.format(new Date())+"."+(11)+".jpg");
					map.put("2", mid+"."+sf.format(new Date())+"."+(11)+".jpg");
					FileInputStream fis=new FileInputStream(authUpLoad);
					//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%上传文件大小"+fis.available());
					if(fis.available()>1024*512){
						++imgIsRight;
					}else{
						File file= new File(uploadPath+File.separator+sf.format(new Date()).trim()); 
						if(!file.exists()){
							file.mkdirs(); 
						}

                        // @date:20190125 商户银行信息修改上传的图片添加水印
                        String filePath = uploadPath+File.separator+sf.format(new Date()).trim()+File.separator+
                                map.get("2").trim();
                        String fileName = map.get("2").trim();
                        PictureWaterMarkUtil.addWatermark(fis,filePath
                                , Constant.PICTURE_WATER_MARK,
                                fileName.trim().substring(fileName.lastIndexOf(".")+1));
//						FileOutputStream fos=new FileOutputStream(file+File.separator+map.get("2"));
//						byte [] buffer=new byte[1024];
//						int len=0;
//						while((len=fis.read(buffer))>0){
//							fos.write(buffer,0,len);
//						}
//						fos.close();
						merchantTaskDetail2.setAuthUpLoad(map.get("2")); 
					}
					fis.close(); 
				} 
				if(dUpLoad!=null){
//					map.put("3", unno.trim()+"."+mid+"."+sf.format(new Date())+"."+(12)+".jpg");
					map.put("3", mid+"."+sf.format(new Date())+"."+(12)+".jpg");
					FileInputStream fis=new FileInputStream(dUpLoad);
					//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%上传文件大小"+fis.available());
					if(fis.available()>1024*512){
						++imgIsRight;
					}else{
						File file= new File(uploadPath+File.separator+sf.format(new Date()).trim()); 
						if(!file.exists()){
							file.mkdirs(); 
						}

                        // @date:20190125 商户银行信息修改上传的图片添加水印
                        String filePath = uploadPath+File.separator+sf.format(new Date()).trim()+File.separator+
                                map.get("3").trim();
                        String fileName = map.get("3").trim();
                        PictureWaterMarkUtil.addWatermark(fis,filePath
                                , Constant.PICTURE_WATER_MARK,
                                fileName.trim().substring(fileName.lastIndexOf(".")+1));
//						FileOutputStream fos=new FileOutputStream(file+File.separator+map.get("3"));
//						byte [] buffer=new byte[1024];
//						int len=0;
//						while((len=fis.read(buffer))>0){
//							fos.write(buffer,0,len);
//						}
//						fos.close();
						merchantTaskDetail2.setDUpLoad(map.get("3")); 
					}
					fis.close(); 
				} 
				if(openUpLoad!=null){
//					map.put("4", unno.trim()+"."+mid+"."+sf.format(new Date())+"."+(13)+".jpg");
					map.put("4", mid+"."+sf.format(new Date())+"."+(13)+".jpg");
					FileInputStream fis=new FileInputStream(openUpLoad);
					//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%上传文件大小"+fis.available());
					if(fis.available()>1024*512){
						++imgIsRight;
					}else{
						File file= new File(uploadPath+File.separator+sf.format(new Date()).trim()); 
						if(!file.exists()){
							file.mkdirs(); 
						}

                        // @date:20190125 商户银行信息修改上传的图片添加水印
                        String filePath = uploadPath+File.separator+sf.format(new Date()).trim()+File.separator+
                                map.get("4").trim();
                        String fileName = map.get("4").trim();
                        PictureWaterMarkUtil.addWatermark(fis,filePath
                                , Constant.PICTURE_WATER_MARK,
                                fileName.trim().substring(fileName.lastIndexOf(".")+1));
//						FileOutputStream fos=new FileOutputStream(file+File.separator+map.get("4"));
//						byte [] buffer=new byte[1024];
//						int len=0;
//						while((len=fis.read(buffer))>0){
//							fos.write(buffer,0,len);
//						}
//						fos.close();
						merchantTaskDetail2.setOpenUpLoad(map.get("4"));
					}
					fis.close(); 
				}
				//判断是否存在待审批的工单
				boolean flag = merchantTaskDetail2Service.queryMerchantTaskDetailByMid(mid);
				if(flag){
					json.setSuccess(false);  
					json.setMsg("已存在待审批的工单！请等待其审批后再次提交");
//					super.writeJson(json);
					// @author:lxg-20191114 敏感信息加密处理
					super.writeJson(CommonTools.jsonBeanToString(json,merchantTaskDetail2.isEnc(),merchantTaskDetail2.getSck()));
					return ;
				}
				if(imgIsRight>0){
					json.setMsg("工单提交失败，你的图片大于512Kb！");  
					json.setSuccess(false); 
				}else{
					merchantTaskDetail2Service.saveMerchantTaskDetail2(merchantTaskDetail2, unno, mid,(UserBean)userSession);
					json.setSuccess(true); 
					json.setMsg("工单提交成功");  
				}

			} catch (Exception e) {
				log.error("工单提交异常：" + e);
				json.setSuccess(false); 
				json.setMsg("工单提交失败");
			} 
		}
//		super.writeJson(json);
		// @author:lxg-20191114 敏感信息加密处理
		super.writeJson(CommonTools.jsonBeanToString(json,merchantTaskDetail2.isEnc(),merchantTaskDetail2.getSck()));
	}
	
	
	
	
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public IMerchantTaskDetail2Service getMerchantTaskDetail2Service() {
		return merchantTaskDetail2Service;
	}
	public void setMerchantTaskDetail2Service( IMerchantTaskDetail2Service merchantTaskDetail2Service) {
		this.merchantTaskDetail2Service = merchantTaskDetail2Service; 
	}
	public MerchantTaskDetail2Model getMerchantTaskDetail2() {
		return merchantTaskDetail2; 
	}
	public void setMerchantTaskDetail2(MerchantTaskDetail2Model merchantTaskDetail2) {
		this.merchantTaskDetail2 = merchantTaskDetail2;
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
	public File getAccUpLoad() {
		return accUpLoad;
	}
	public void setAccUpLoad(File accUpLoad) {
		this.accUpLoad = accUpLoad;
	}
	public String getAccUpLoadFileName() {
		return accUpLoadFileName;
	}
	public void setAccUpLoadFileName(String accUpLoadFileName) {
		this.accUpLoadFileName = accUpLoadFileName;
	}
	public File getAuthUpLoad() {
		return authUpLoad;
	}
	public void setAuthUpLoad(File authUpLoad) {
		this.authUpLoad = authUpLoad;
	}
	public File getOpenUpLoad() {
		return openUpLoad;
	}
	public File getdUpLoad() {
		return dUpLoad;
	}

	public void setdUpLoad(File dUpLoad) {
		this.dUpLoad = dUpLoad;
	}

	public void setOpenUpLoad(File openUpLoad) {
		this.openUpLoad = openUpLoad;
	}
}
