package com.hrt.biz.bill.action;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.hrt.biz.bill.entity.model.CheckUnitdealdetailCommModel;
import com.hrt.biz.bill.entity.pagebean.CheckUnitdealCommBean;
import com.hrt.biz.bill.service.TrafficBankInsertService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.UsePioOutExcel;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 交行 报表---数据导入
 *
 */
public class TrafficBankInsertAction extends BaseAction implements ModelDriven<CheckUnitdealCommBean> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;;

	private Log log = LogFactory.getLog(TrafficBankInsertAction.class);

	private CheckUnitdealCommBean checkUnitdealCommBean=new CheckUnitdealCommBean();
	
	private File upload=null;

	private TrafficBankInsertService trafficBankInsertService;
	
	@Override
	public CheckUnitdealCommBean getModel() {
		return checkUnitdealCommBean;
	} 
	
	public TrafficBankInsertService getTrafficBankInsertService() {
		return trafficBankInsertService;
	}
	public void setTrafficBankInsertService(
			TrafficBankInsertService trafficBankInsertService) {
		this.trafficBankInsertService = trafficBankInsertService;
	}
	
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	//导出数据
	public void TrafficBankListExport()throws IOException{
		Object userSession = super.getRequest().getSession().getAttribute("user");
		List<Map<String, String>> list =trafficBankInsertService.TrafficBankListExport(checkUnitdealCommBean,(UserBean)userSession);
		
		// 查询总记录数
		String[] titles ={"商户编号","商户名称","销售员","维护员","内卡交易总金额","内卡交易总笔数","外卡交易总金额","外卡交易总笔数","总交易量 ","交易日期"};
		List excelList = new ArrayList();
		excelList.add(titles);
		for (int rowId = 0; rowId < list.size(); rowId++) {
			
			Map order = list.get(rowId);
			
			String[] rowContents = { 
					 (String)order.get("MID"),
					 (String)order.get("MERCHNAME"),
					 (String)order.get("ASALENAME"),
					 (String)order.get("BSALENAME"),
					 order.get("TXNAMT").toString(),
					  order.get("COUNTS").toString(),
					  order.get("WLTXNAMT").toString(),
					  order.get("WLCOUNTS").toString(),
					  order.get("SUMTXNAMT").toString(),
					  (String)order.get("TRADEDATE") //.toString()
							};
			excelList.add(rowContents);
		}  
		String excelName="商户日报表交易信息报表";
		String sheetName="商户日报表交易信息报表";
		String[] cellFormatType = {"t","t","t","t","t","t","t","t","t","t"};
		UsePioOutExcel.writeExcel(excelList, 10, super.getResponse(), sheetName,excelName + ".xls", cellFormatType); // 调用导出方法
	}
	
	//查询
	public void TrafficBankList(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		try {
			if(checkUnitdealCommBean.getCreateDate()==null||checkUnitdealCommBean.getCreateDate().equals("")){
				//super.writeJson(dgb);
			}else{
			dgb = trafficBankInsertService.TrafficBankLists(checkUnitdealCommBean,(UserBean)userSession);
			super.writeJson(dgb);
			}
		} catch (Exception e) {
			log.error("信息异常：" + e);
		}
	}

	public void saveTrafficBankInsertExport(){
		//把文件保存到服务器的目录下
		JsonBean json = new JsonBean();
		String filename = ServletActionContext.getRequest().getParameter("fileContact1");	//获取文件名
		String wk = filename.substring(filename.lastIndexOf(".")-2,filename.lastIndexOf("."));
		String timed=filename.substring(0, 10);
		String dasePath = ServletActionContext.getServletContext().getRealPath("/upload");	
		File dir = new File(dasePath);
		dir.mkdirs();
		
		String path = dasePath + "/" + filename;
		File destFile = new File(path);
		upload.renameTo(destFile);
		String folderPath = ServletActionContext.getServletContext().getRealPath("/upload");// 存放上传文件的路径
		String xlsfile = folderPath + "/" + filename;
		// String AccountDay = filename.split("--")[1].replaceAll(".txt", "");
		try {
			List<HashMap> list=null;
			if (xlsfile.length() > 0 && xlsfile != null) {
				if("wk".equals(wk)){
					Integer count=trafficBankInsertService.inportrefW(timed);
					if(count>0){
						json.setSuccess(true);
						json.setMsg("已有该日期的数据！");
					}else{
					list= trafficBankInsertService.saveTrafficBankInsertWKExport(xlsfile,filename);
					}
				}else{
					Integer count=trafficBankInsertService.inportref(timed);
					if(count>0){
						json.setSuccess(true);
						json.setMsg("已有该日期的数据！");
					}else{
					list= trafficBankInsertService.saveTrafficBankInsertExport(xlsfile,filename); 
					}
				}
				if(list!=null){
				if (list.size()>0) {// 如果不为true,代表上传失败
					json.setSuccess(true);
					json.setMsg(list.size()+"记录更新失败!");
				}else {
					json.setSuccess(true);
					json.setMsg("文件更新成功!");
				}
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(true);
			json.setMsg("文件更新失败!");
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
				boolean flag = temp.delete();
				int j = i + 1;
				log.error("成功删除文件:第" + j + "个文件删除是否成功？" + flag);
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//				System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
			} else {
				log.error("文件不存在");
			}
//		}
		super.writeJson(json);
		  }
	}
}
