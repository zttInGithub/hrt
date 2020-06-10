package com.hrt.phone.action;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.hrt.biz.bill.entity.model.MerchantTerminalInfoModel;
import com.hrt.biz.bill.entity.pagebean.MachineInfoBean;
import com.hrt.biz.bill.entity.pagebean.MerchantTerminalInfoBean;
import com.hrt.biz.bill.service.IMachineInfoService;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.bill.service.IMerchantTerminalInfoService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.UsePioOutExcel;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 商户终端申请
 * @author tanwb
 *
 */
public class PhoneMerchantTerminalInfoAction extends BaseAction implements ModelDriven<MerchantTerminalInfoBean>{

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(PhoneMerchantTerminalInfoAction.class);
	
	private MerchantTerminalInfoBean merchantTerminalInfo = new MerchantTerminalInfoBean();
	
	private IMerchantTerminalInfoService merchantTerminalInfoService;
	
	private IMerchantInfoService merchantInfoService;
	
	private IMachineInfoService machineInfoService;
	
	private String id;
	
	private File upload;
	
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getId() {
		return id;
	}
	public MerchantTerminalInfoBean getMerchantTerminalInfo() {
		return merchantTerminalInfo;
	}

	public void setMerchantTerminalInfo(
			MerchantTerminalInfoBean merchantTerminalInfo) {
		this.merchantTerminalInfo = merchantTerminalInfo;
	}

	public IMachineInfoService getMachineInfoService() {
		return machineInfoService;
	}

	public void setMachineInfoService(IMachineInfoService machineInfoService) {
		this.machineInfoService = machineInfoService;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public MerchantTerminalInfoBean getModel() {
		return merchantTerminalInfo;
	}

	public IMerchantTerminalInfoService getMerchantTerminalInfoService() {
		return merchantTerminalInfoService;
	}

	public void setMerchantTerminalInfoService(
			IMerchantTerminalInfoService merchantTerminalInfoService) {
		this.merchantTerminalInfoService = merchantTerminalInfoService;
	}
	
	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	public void addMerchantTerminalInfoEdit(){
		JsonBean json = new JsonBean();
		//Object userSession = super.getRequest().getSession().getAttribute("user");
		String unNo = super.getRequest().getParameter("unNo")+"";
		String userID = super.getRequest().getParameter("userID")+"";
		UserBean userSession = new UserBean();
		//session失效
		if (unNo==null||"".equals(unNo)||userID==null||"".equals(userID)) {
			json.setSessionExpire(true);
		} else {
			try {
				String[] values={merchantTerminalInfo.getMid()+"#separate#"+merchantTerminalInfo.getTid()+"#separate#"+merchantTerminalInfo.getBmaid()+"#separate#"+merchantTerminalInfo.getInstalledAddress()+"#separate#"+merchantTerminalInfo.getInstalledName()+"#separate#"+merchantTerminalInfo.getContactPerson()+"#separate#"+merchantTerminalInfo.getContactPhone()+"#separate#"+merchantTerminalInfo.getContactTel()+"#separate#"+merchantTerminalInfo.getInstalledSIM()};
//				String[] values=getRequest().getParameterValues("MIDNUM");
				int result = 0;
				for (int i = 0; i < values.length; i++) {
					String[] pram=values[i].split("#separate#");
					result += merchantTerminalInfoService.TerminalInfoTerMID(pram[1]);
				}
				if(result==0){
					userSession.setUnNo(unNo);
					userSession.setUserID(Integer.valueOf(userID));
					merchantTerminalInfoService.saveInfo(merchantTerminalInfo, userSession,values);
					json.setSuccess(true);
					json.setMsg("提交商户增机申请成功");
				}else{
					json.setSuccess(false);
					json.setMsg("失败，所选终端有已被其它用户申请");
				}
				
			} catch (Exception e) {
				log.error("提交商户增机申请异常：" + e);
				json.setMsg("提交商户增机申请失败");
			}
		}
		
		super.writeJson(json);
	}
	/**
	 * 查询机具库存显示到相应的select(非M35)
	 */
	public void searchNormalMachineInfo(){
		JsonBean json = new JsonBean();
		List<MachineInfoBean> machineInfoList = null;
		try {
			String nameCode = super.getRequest().getParameter("nameCode");
			machineInfoList = machineInfoService.searchNormalMachineInfo(nameCode);
			json.setObj(machineInfoList);
		} catch (Exception e) {
			log.error("查询机具异常：" + e);
		}
		super.writeJson(json);
	}
	
	/**
	 * 报单自动挂终端
	 */
	public void addMerchantTerminalInfo(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				String[] values=getRequest().getParameterValues("IDNUM");
				String bmid=getRequest().getParameter("BMID");
				String mid=getRequest().getParameter("MID");
				
				int result = 0;
				if(values != null && values.length > 0){
					for (int i = 0; i < values.length; i++) {
						String[] pram=values[i].split("#separate#");
						boolean falg =merchantTerminalInfoService.queryMachineInfo(pram[1]);
						if(falg){
							result += merchantTerminalInfoService.TerminalInfoTerMID(pram[0]);
						}else{
							json.setSuccess(false);
							json.setMsg("设备型号不存在！");
							super.writeJson(json);
						}
					}
				}
				if(result==0){
					merchantTerminalInfoService.saveMerchantTerminalInfo(merchantTerminalInfo, ((UserBean)userSession), values, Integer.parseInt(bmid), mid);
					json.setSuccess(true);
					json.setMsg("终端申请成功");
				}else{
					json.setSuccess(false);
					json.setMsg("失败，所选终端有已被其它用户申请");
				}
			} catch (Exception e) {
				log.error("终端申请异常：" + e);
				json.setMsg("终端申请失败");
			}
		}
		
		super.writeJson(json);
	}
	public void listMerchantTerminalInfoZ(){
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			dgb = merchantTerminalInfoService.queryMerchantTerminalInfoZ(merchantTerminalInfo, ((UserBean)userSession));
		} catch (Exception e) {
			log.error("分页查询终端信息异常：" + e);
		}
		super.writeJson(dgb);
	}
	
}
