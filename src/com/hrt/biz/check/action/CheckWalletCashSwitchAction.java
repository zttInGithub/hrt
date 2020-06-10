package com.hrt.biz.check.action;

import com.hrt.biz.check.entity.model.CheckWalletCashSwitchModel;
import com.hrt.biz.check.entity.pagebean.CheckWalletCashSwitchBean;
import com.hrt.biz.check.service.CheckWalletCashSwitchService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.ExcelServiceImpl;
import com.hrt.util.JxlOutExcelUtil;
import com.opensymphony.xwork2.ModelDriven;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * CheckWalletCashSwitchAction
 * <p>
 * 分润差额钱包
 * </p>
 *
 * @author xuegangliu 2019/10/22
 * @since 1.8
 **/
public class CheckWalletCashSwitchAction  extends BaseAction implements ModelDriven<CheckWalletCashSwitchBean> {
    private static final Log log = LogFactory.getLog(CheckWalletCashSwitchAction.class);

    private CheckWalletCashSwitchService checkWalletCashSwitchService;
    private CheckWalletCashSwitchBean checkWalletCashSwitchBean=new CheckWalletCashSwitchBean();
    
    private File upload=null;
    
    public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
    
    public CheckWalletCashSwitchBean getCheckWalletCashSwitchBean() {
		return checkWalletCashSwitchBean;
	}

	public void setCheckWalletCashSwitchBean(CheckWalletCashSwitchBean checkWalletCashSwitchBean) {
		this.checkWalletCashSwitchBean = checkWalletCashSwitchBean;
	}

	@Override
    public CheckWalletCashSwitchBean getModel() {
        return checkWalletCashSwitchBean;
    }

    public CheckWalletCashSwitchService getCheckWalletCashSwitchService() {
        return checkWalletCashSwitchService;
    }

    public void setCheckWalletCashSwitchService(CheckWalletCashSwitchService checkWalletCashSwitchService) {
        this.checkWalletCashSwitchService = checkWalletCashSwitchService;
    }

    public void listAvailableRebateType(){
        DataGridBean dgb = new DataGridBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if (userSession == null) {
            } else {
                UserBean user = (UserBean) userSession;
                dgb = checkWalletCashSwitchService.availableRebateTypeList(user);
            }
        } catch (Exception e) {
            log.error("查询钱包活动列表异常：" + e);
        }
        super.writeJson(dgb);
    }

    /**
     * 当前登录下级机构获取
     */
    public void listSubUnno(){
        DataGridBean dgb = new DataGridBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if (userSession == null) {
            } else {
                dgb = checkWalletCashSwitchService.subUnnoList(checkWalletCashSwitchBean,(UserBean)userSession);
            }
        } catch (Exception e) {
            log.error("查询下级机构异常：" + e);
        }
        super.writeJson(dgb);
    }

    /**
     * 实时钱包
     */
    public void listCheckWalletCashSwitchJS(){
    	JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        if(userSession!=null) {
            UserBean user = (UserBean) userSession;
            DataGridBean dataGridBean = checkWalletCashSwitchService.findAllWalletCashJS(checkWalletCashSwitchBean, user);
            super.writeJson(dataGridBean);
        }else{
            json.setSessionExpire(true);
        }
        super.writeJson(json);
        
    }
    
    public void listCheckWalletCashSwitch(){
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        if(userSession!=null){
            UserBean user = (UserBean) userSession;
            DataGridBean dataGridBean = checkWalletCashSwitchService.findAllWalletCash(checkWalletCashSwitchBean, user);
            super.writeJson(dataGridBean);
            return;
        }else{
            json.setSessionExpire(true);
        }
        super.writeJson(json);
    }

    /**
     * 新增实时钱包
     */
    public void saveCheckWalletCashSwitch(){
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        if(userSession!=null){
            UserBean user = (UserBean) userSession;
            try {
                if(user.getUnitLvl()==0) {
                    json.setSuccess(false);
                    json.setMsg("禁止提交中心机构分润差额钱包");
                }else {
                    String errMsg = checkWalletCashSwitchService.validateWalletCashStatusInfo(checkWalletCashSwitchBean,user);
                    String errMsg1 = checkWalletCashSwitchService.validateWalletCashStatus(user.getUnitLvl()+1,
                            checkWalletCashSwitchBean.getUnno(),null,checkWalletCashSwitchBean.getRebateType(),checkWalletCashSwitchBean.getWalletStatus(),false);
                    if("1".equals(checkWalletCashSwitchBean.getWalletStatus())&& (StringUtils.isNotEmpty(errMsg) || StringUtils.isNotEmpty(errMsg1))){
                        json.setSuccess(false);
                        json.setMsg(StringUtils.isNotEmpty(errMsg)?errMsg:errMsg1);
                    }else {
                        if(checkWalletCashSwitchService.hasWalletCashByUnnoAndRebateType(checkWalletCashSwitchBean)){
                            checkWalletCashSwitchBean.setRemark1(user.getUserID()+"");
                            checkWalletCashSwitchService.updateWalletCashW(checkWalletCashSwitchBean);
                            json.setSuccess(true);
                            json.setMsg("下月生效变更成功");
                        }else {
                            CheckWalletCashSwitchModel t = checkWalletCashSwitchService.saveWalletCash(checkWalletCashSwitchBean);
                            json.setSuccess(true);
                            json.setObj(t);
                            json.setMsg("添加成功");
                        }
                    }
                }
            } catch (InvocationTargetException e) {
                log.error("新增实时钱包异常：" + e);
            } catch (IllegalAccessException e) {
                log.error("新增实时钱包异常：" + e);
            }
        }else{
            json.setSessionExpire(true);
        }
        super.writeJson(json);
    }

    /**
     * 下月生效状态修改
     */
    public void updateCheckWalletCashSwitchStatus(){
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        if (userSession==null) {
            json.setSessionExpire(true);
        }else {
            try {
                UserBean user = (UserBean) userSession;
//                String errMsg = checkWalletCashSwitchService.validateWalletCashStatus(user.getUnitLvl(),
//                        user.getUnNo(),checkWalletCashSwitchBean.getRebateType(),checkWalletCashSwitchBean.getWalletStatus(),true);
                String errMsg = checkWalletCashSwitchService.validateWalletCashStatusInfo(checkWalletCashSwitchBean,user);
                String errMsg1 = checkWalletCashSwitchService.validateWalletCashStatus(user.getUnitLvl()+1,
                        checkWalletCashSwitchBean.getUnno(),null,checkWalletCashSwitchBean.getRebateType(),checkWalletCashSwitchBean.getWalletStatus(),false);
                if("1".equals(checkWalletCashSwitchBean.getWalletStatus())&&(StringUtils.isNotEmpty(errMsg) || StringUtils.isNotEmpty(errMsg1))){
                    json.setSuccess(false);
                    json.setMsg(StringUtils.isNotEmpty(errMsg)?errMsg:errMsg1);
                }else {
                    boolean res = checkWalletCashSwitchService.updateWalletCashWStatus(checkWalletCashSwitchBean, user);
                    if (res) {
                        json.setSuccess(true);
                        json.setMsg("下月生效状态修改成功");
                    } else {
                        json.setSuccess(false);
                        json.setMsg("下月生效状态修改失败");
                    }
                }
            } catch (Exception e) {
                log.error("修改失败异常：" + e);
                json.setMsg("修改失败");
            }
        }
        super.writeJson(json);
    }


    /**
     * 批量开通或者变更模板下载
     */
    public void dowloadWalletCashSwitchXls(){
        try {
            ExcelServiceImpl.download(getResponse(),"wallet_cash_switch_upload.xls","批量开通变更模板.xls");
        } catch (Exception e) {
            log.info("批量开通变更模板下载失败:"+e);
        }
    }

    /**
     * 批量开通或者变更模板导入
     */
    public void importWalletCashSwitchXls(){
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user=(UserBean)userSession;
        if(user == null){
            json.setSessionExpire(true);
            super.writeJson(json);
            return;
        }else{
            if(user.getUnitLvl()==0){
                json.setSuccess(false);
                json.setMsg("禁止提交中心机构分润差额钱包");
                super.writeJson(json);
                return;
            }
        }
        String UUID = System.currentTimeMillis()+"";
        String fileName = UUID+ ServletActionContext.getRequest().getParameter("fileContact");
        String basePath = ServletActionContext.getRequest().getRealPath("/upload");
        File dir = new File(basePath);
        if(!dir.exists())
            dir.mkdir();
        String path = basePath +"/"+fileName;
        File destFile = new File(path);
        upload.renameTo(destFile);
        String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
        String xlsfile = folderPath +"/"+fileName;
        log.info("批量开通变更模板导入Excel文件路径:"+xlsfile);
        try {
            if(xlsfile.length() > 0 && xlsfile != null){
                String message = checkWalletCashSwitchService.saveImportWalletCashSwitchXls(xlsfile,fileName,user);
                if(StringUtils.isEmpty(message)){
                    json.setSuccess(true);
                    json.setMsg("批量开通变更模板导入成功!");
                }else{
                    json.setSuccess(false);
                    json.setMsg(message);
                }
            }else{
                json.setSuccess(false);
                json.setMsg("导入文件异常!");
            }
        } catch (Exception e) {
            log.info("批量开通变更模板导入异常:"+e);
            json.setSuccess(false);
            json.setMsg("批量开通变更模板导入失败!");
        }
        ExcelServiceImpl.deleteUploadFile(folderPath,path);
        super.writeJson(json);
    }

    /**
     * 下月分润差额钱包
     */
    public void listCheckWalletCashSwitchW(){
        super.writeJson(checkWalletCashSwitchService.findAllWalletCashW(checkWalletCashSwitchBean));
    }

    /**
     * 历史分润差额钱包
     */
    public void listCheckWalletCashSwitchLog(){
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        if(userSession!=null) {
            UserBean user = (UserBean) userSession;
            DataGridBean dataGridBean = checkWalletCashSwitchService.findAllWalletCashLog(checkWalletCashSwitchBean, user);
            super.writeJson(dataGridBean);
        }else{
            json.setSessionExpire(true);
        }
        super.writeJson(json);
    }
    
    /**
	 * 批量开通钱包模板下载
	 */
	public void downloadBatchCashSwitchXls(){
		try {
			ExcelServiceImpl.download(getResponse(),"batchCashSwitchImport.xls","批量开通钱包模板.xls");
		} catch (Exception e) {
			log.info("批开通钱包模板下载失败:"+e);
		}
	}
	
	/**
	 * 批量开通钱包模板导入---结算批量修改钱包开通状态
	 */
	public void importBatchAddWalletCashSwitchJS(){
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
		log.info("批量开通钱包导入Excel文件路径:"+xlsfile);

		try {
			if(xlsfile.length() > 0 && xlsfile != null){
				String message = checkWalletCashSwitchService.saveImportBatchWalletCashSwitchJS(xlsfile,fileName,user);
				if(StringUtils.isEmpty(message)){
					json.setSuccess(true);
					json.setMsg("批量开通钱包导入成功!");
				}else{
					json.setSuccess(true);
					json.setMsg(message);
				}
			}else{
				json.setSuccess(false);
				json.setMsg("导入文件异常!");
			}
		} catch (Exception e) {
			log.info("批量开通钱包新增导入异常:"+e);
			json.setSuccess(false);
			json.setMsg("批量开通钱包新增导入失败!");
		}
		ExcelServiceImpl.deleteUploadFile(folderPath,path);
		super.writeJson(json);
	}
	
	//实时钱包单个修改钱包状态updateWalletCashOne
	public void updateWalletCashOne() {
		JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        if(userSession!=null) {
            UserBean user = (UserBean) userSession;
        }else{
            json.setSessionExpire(true);
        }
        super.writeJson(json);
	}
	
	/**
	 * 导出终端状态统计---活动类型
	 */
	public void exportCheckWalletCashSwitchLog() throws IOException, RowsExceededException, WriteException {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		HttpServletResponse response = super.getResponse();
		List<Map<String, Object>> list = checkWalletCashSwitchService.exportCheckWalletCashSwitchLog(checkWalletCashSwitchBean,(UserBean)userSession);
		String titles[] = {"代理机构号","代理机构名称","活动类型","开始时间","结束时间","钱包状态","代理分类"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String rowContents[] = {
					map.get("UNNO")==null?"":map.get("UNNO").toString(),
					map.get("UN_NAME")==null?"":map.get("UN_NAME").toString(),
					map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
					map.get("CREATEDATE")==null?"":map.get("CREATEDATE").toString(),
					map.get("ENDDATE")==null?"":map.get("ENDDATE").toString(),
					map.get("WALLETSTATUS")==null?"":map.get("WALLETSTATUS").toString(),
					map.get("UN_LVL")==null?"":map.get("UN_LVL").toString(),
			};
			excelList.add(rowContents);
		}
		String excelName = "代理分润差额钱包生效记录.csv";
		JxlOutExcelUtil.writeCSVFile(excelList, titles.length, response, excelName);
	}
}
