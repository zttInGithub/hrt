package com.hrt.biz.bill.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
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
import com.hrt.biz.bill.entity.model.MerchantTaskDetail3Model;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.bill.service.IMerchantTaskDataService;
import com.hrt.biz.bill.service.IMerchantTaskDetail3Service;
import com.hrt.biz.bill.service.ITerminalInfoService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;

/**
 * 商户费率工单申请
 * @author xxx
 *
 */
public class MerchantTaskDetail3Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(MerchantTaskDetail3Action.class);
	//根据商户机构编号查询商户信息
	private String unno;		
	private IMerchantTaskDetail3Service merchantTaskDetail3Service;
	private MerchantTaskDetail3Model merchantTaskDetail3;
	
	private IMerchantTaskDataService merchantTaskDataService;

	private IMerchantInfoService merchantInfoService;
	
	private ITerminalInfoService terminalInfoService;
	
	
	private String mid;
	private List<MerchantInfoModel> list;
	private File feeUpLoad;
	private String feeUpLoadFileName;
	private Double maxAmt;
	
	
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	
	public ITerminalInfoService getTerminalInfoService() {
		return terminalInfoService;
	}
	public void setTerminalInfoService(ITerminalInfoService terminalInfoService) {
		this.terminalInfoService = terminalInfoService;
	}
	public IMerchantTaskDetail3Service getMerchantTaskDetail3Service() {
		return merchantTaskDetail3Service;
	}
	public void setMerchantTaskDetail3Service(IMerchantTaskDetail3Service merchantTaskDetail3Service) {
		this.merchantTaskDetail3Service = merchantTaskDetail3Service;
	}
	public MerchantTaskDetail3Model getMerchantTaskDetail3() {
		return merchantTaskDetail3;
	}
	public void setMerchantTaskDetail3(MerchantTaskDetail3Model merchantTaskDetail3) {
		this.merchantTaskDetail3 = merchantTaskDetail3;
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
	public File getFeeUpLoad() {
		return feeUpLoad;
	}
	public void setFeeUpLoad(File feeUpLoad) {
		this.feeUpLoad = feeUpLoad;
	}
	public String getFeeUpLoadFileName() {
		return feeUpLoadFileName;
	}
	public void setFeeUpLoadFileName(String feeUpLoadFileName) {
		this.feeUpLoadFileName = feeUpLoadFileName;
	}
	public Double getMaxAmt() {
		return maxAmt;
	}
	public void setMaxAmt(Double maxAmt) {
		this.maxAmt = maxAmt;
	}
	
	public IMerchantTaskDataService getMerchantTaskDataService() {
		return merchantTaskDataService;
	}

	public void setMerchantTaskDataService(
			IMerchantTaskDataService merchantTaskDataService) {
		this.merchantTaskDataService = merchantTaskDataService;
	} 
	
	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}
	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}
	/**
	 * 根据商户机构编号查询商户费率信息
	 */ 
	public void serchMerchantInfo(){
		//DataGridBean dgd = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		list=merchantTaskDetail3Service.queryMerchantInfo(mid,(UserBean)userSession);
		//list=merchantTaskDetail3Service.queryMerchantInfo(mid);
		super.writeJson(list);
	}
	
	public void judgeMid(){
		//收银台与plus费率调整限制修改，并且110000与b10000管理员可以进行修改20190929
		Object userSession = super.getRequest().getSession().getAttribute("user");
		String sendMid = super.getRequest().getParameter("sendMid");
		UserBean userUnno = (UserBean) userSession;
		String loginunno = userUnno.getUnNo();
		JsonBean json = new JsonBean();
		
		//判断所属特殊机构商户不润许修改
		boolean IsLimitUnno = merchantTaskDetail3Service.judgeLimitUnno(sendMid,Constant.LIMIT_UNNO);
		if(IsLimitUnno) {
			json.setSuccess(false);
			json.setMsg("暂不允许调整费率，敬请谅解！");
			super.writeJson(json);
			return;
		}
		
		//判断商户下设备含有活动90的设备，不允许调整
		//判断商户下设备含有特定活动的设备，不允许调整---20191210添加
		boolean IsMatching = merchantTaskDetail3Service.judgeActiveIsMatching(sendMid);
		if(IsMatching) {
			json.setSuccess(false);
			json.setMsg("暂不允许调整费率，敬请谅解！");
			super.writeJson(json);
			return;
		}
		
		//判断商户类型，相应不允许修改
		if(sendMid.contains("HRTSYT")||sendMid.contains("H864")) {
			if("110000".equals(loginunno)||"b10000".equalsIgnoreCase(loginunno)) {
				json.setSuccess(true);
				json.setMsg("运营机构可以修改");
				super.writeJson(json);
				return;
			}
			json.setSuccess(false);
			json.setMsg("暂不允许调整费率，敬请谅解！");
			super.writeJson(json);
			return ;
		}
		
		//特殊机构下(有特殊费率配置的中心机构)商户修改--添加判断是否是特殊活动ztt--20200311
		String SpecialUnno =  merchantTaskDetail3Service.judgeIsSpecialUnno(sendMid);
		if(SpecialUnno!=null) {
			json.setSuccess(true);
			json.setMsg(SpecialUnno);
			super.writeJson(json);
			return;
		}
		json.setSuccess(true);
		json.setMsg("其他商户可以修改");
		super.writeJson(json);
	}

	/**
	 * 保存商户提交的银行费率工单申请
	 */
	public void addMerchantTaskDetail3(){
		Map<String ,String> map=new HashMap<String ,String >();
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");

		// @author:lxg-20190903 商户工单费率申请，不允许temp1表中的商户做修改操作
		boolean isExists = merchantTaskDetail3Service.midExistsTemp(mid);
		if(isExists){
			json.setSuccess(false);
			json.setMsg("此商户不允许提交费率变更");
			super.writeJson(json);
			return ;
		}

		String uploadPath=merchantTaskDetail3Service.queryUpLoadPath();
		String creditBankRate1 = super.getRequest().getParameter("merchantTaskDetail3.creditBankRate1");
		String creditBankRate2 = super.getRequest().getParameter("merchantTaskDetail3.creditBankRate2");
		if(creditBankRate1!=null&&!"".equals(creditBankRate1)){
			merchantTaskDetail3.setCreditBankRate(Double.valueOf(creditBankRate1.split("-")[0])*100);
			merchantTaskDetail3.setBankFeeRate(Double.valueOf(creditBankRate1.split("-")[0])*100);
			merchantTaskDetail3.setSecondRate(Double.valueOf(creditBankRate1.split("-")[1]));
		}
		if(creditBankRate2!=null&&!"".equals(creditBankRate2)){
			merchantTaskDetail3.setScanRate(Double.valueOf(creditBankRate2.split("-")[0])*100);
			merchantTaskDetail3.setSecondRate(Double.valueOf(creditBankRate2.split("-")[1]));
		}
		//添加自定义费率ztt20200311
		String termIDStart = super.getRequest().getParameter("termIDStart");
		String termIDEnd = super.getRequest().getParameter("termIDEnd");
		if((creditBankRate1==null||"".equals(creditBankRate1))
				&&(creditBankRate2==null||"".equals(creditBankRate2))) {
			String isspecial = super.getRequest().getParameter("isspecial");
			if("1".equals(isspecial)) {
				if((termIDStart!=null&&!"".equals(termIDStart)
						&&termIDEnd!=null&&!"".equals(termIDEnd))) {
					merchantTaskDetail3.setCreditBankRate(Double.valueOf(termIDStart));
					merchantTaskDetail3.setBankFeeRate(Double.valueOf(termIDStart));
					merchantTaskDetail3.setSecondRate(Double.valueOf(termIDEnd));
				}else{
					json.setSuccess(false);  
					json.setMsg("特定活动费率与转账费请确认填写完整");
					super.writeJson(json); 
					return ;
				}
			}
		}
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				//判断是否存在待审批的工单
				boolean flag = merchantTaskDetail3Service.queryMerchantTaskDetailByMid(mid);
				if(flag){
					json.setSuccess(false);  
					json.setMsg("已存在待审批的工单！请等待其审批后再次提交");
					super.writeJson(json); 
					return ;
				}
                MerchantInfoModel merchantInfoModel= merchantInfoService.getMerInfoByMid(mid);
				boolean applyFlag=false;
				Integer merTaskDataId = null;
                // @author:lxg-20190605 只限制(新平台)秒到,贷记卡费率 大于之前直接拒绝,小于直接过
                // ism35=1 and setthod=100000
//				boolean isNewPlatform=false;
//				if(null!= merchantInfoModel && null!=merchantInfoModel.getUnno()){
//					isNewPlatform = ".a.b.c.d.e.f.g.h.i.j.k.".contains("."+merchantInfoModel.getUnno().substring(0,1).toLowerCase()+".");
//				}
//                if(isNewPlatform && null!=merchantInfoModel && merchantInfoModel.getIsM35() == 1 && "100000".equals(merchantInfoModel.getSettMethod())){
//                    if(null!=merchantTaskDetail3.getCreditBankRate() && null!= merchantInfoModel.getCreditBankRate()) {
//                        BigDecimal oldValue = new BigDecimal(merchantInfoModel.getCreditBankRate()+"");
//                        BigDecimal newValue = new BigDecimal(merchantTaskDetail3.getCreditBankRate()+"").divide(new BigDecimal("100"));
//
//                        if(newValue.compareTo(oldValue)>0){
//                            json.setSuccess(false);
//                            json.setMsg("工单申请失败,请联系销售");
//                            super.writeJson(json);
//                            return ;
//                        }
//                    }
//                }

				if(feeUpLoad==null){
					map.put("1", "");
					merTaskDataId = merchantTaskDetail3Service.saveMerchantTaskDetail3(merchantTaskDetail3, unno, mid,maxAmt,(UserBean)userSession);
					applyFlag = true;
					json.setSuccess(true); 
					json.setMsg("工单提交成功");    
				}else{
//					map.put("1", unno.trim()+"."+mid+"."+sf.format(new Date())+"."+(7)+feeUpLoadFileName.substring(feeUpLoadFileName.lastIndexOf(".")));
					map.put("1", mid+"."+sf.format(new Date())+"."+(7)+feeUpLoadFileName.substring(feeUpLoadFileName.lastIndexOf(".")));
					FileInputStream fis=new FileInputStream(feeUpLoad);
					//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%上传文件大小"+fis.available());
					if(fis.available()>1024*512){
						json.setMsg("工单提交失败，你的图片大于512Kb！");  
					}else{
						File file= new File(uploadPath+File.separator+sf.format(new Date()).trim()); 
						if(!file.exists()){
							file.mkdirs(); 
						}
						// @date:20190125 商户费率信息修改上传的图片添加水印
                        String filePath = uploadPath+File.separator+sf.format(new Date()).trim()+File.separator+
                                map.get("1").trim();
                        String fileName = map.get("1").trim();
						PictureWaterMarkUtil.addWatermark(fis,filePath
								, Constant.PICTURE_WATER_MARK,
                                fileName.substring(fileName.lastIndexOf(".")+1));
//						FileOutputStream fos=new FileOutputStream(file+File.separator+map.get("1"));
//						byte [] buffer=new byte[1024];
//						int len=0;
//						while((len=fis.read(buffer))>0){
//							fos.write(buffer,0,len);
//						}
//						fos.close();
						merchantTaskDetail3.setFeeUpLoad(map.get("1"));
						merTaskDataId = merchantTaskDetail3Service.saveMerchantTaskDetail3(merchantTaskDetail3, unno, mid,maxAmt,(UserBean)userSession);
						applyFlag = true;
						json.setSuccess(true); 
						json.setMsg("工单提交成功");  
					}
					fis.close(); 
				}
				
				//add 自动审批2017-10-9   限制（ism35=1 and setthod=100000）
				


				// @author:lxg-20190410  d62030\d62034 把这两个代理商修改费率落地(需要审批)
				// @author:lxg-20190513 新平台,费率变更都落地
				// @author:lxg-20190605 删除新平台费率变更落地，d62030\d62034与所有机构一致
//				if(!".a.b.c.d.e.f.g.h.i.j.k.".contains("."+merchantInfoModel.getUnno().substring(0,1).toLowerCase()+".")){
//				if(!".d62030.d62034.".contains("."+merchantInfoModel.getUnno()+".")) {
//				if (applyFlag == true && merchantInfoModel.getIsM35() != null && merchantInfoModel.getIsM35() == 1
//						&& merchantInfoModel.getSettMethod() != null && merchantInfoModel.getSettMethod().equals("100000")) {
					if (applyFlag == true && (merchantInfoModel.getIsM35() == 6 || (merchantInfoModel.getIsM35() == 1 && "100000".equals(merchantInfoModel.getSettMethod())))) {
						boolean f = merchantTaskDataService.updateAuditThrough(null, (UserBean) userSession, merTaskDataId + "");
						if (f) {
							json.setSuccess(true);
							json.setMsg("审批通过");
						} else {
							merchantTaskDataService.updateTaskDataStatusById(merTaskDataId, "W", (UserBean) userSession);
							log.error("商户费率变更工单失败：审批之中包含有已审批的工单，bmtkid:" + merTaskDataId);
							json.setSuccess(true);
							json.setMsg("自动审批失败，请人工审批！");
						}
					}
//				}
			} catch (Exception e) {
				log.error("工单提交异常：" + e);
				json.setMsg("工单提交失败");
			}
		}
		super.writeJson(json);
	}

}
