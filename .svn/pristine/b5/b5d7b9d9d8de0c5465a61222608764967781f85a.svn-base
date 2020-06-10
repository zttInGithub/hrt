package com.hrt.biz.bill.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hrt.util.ExcelServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.hrt.biz.bill.entity.pagebean.ProxyPurseBean;
import com.hrt.biz.bill.service.IProxyPurseService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.UsePioOutExcel;
import com.opensymphony.xwork2.ModelDriven;

/**
 *   分润调整
 * @author ztt
 * */
public class ProxyPurseAction extends BaseAction implements ModelDriven<ProxyPurseBean>{
	
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(ProxyPurseAction.class);
	
	private ProxyPurseBean proxyPurseBean = new ProxyPurseBean();
	
	private IProxyPurseService proxyPurseService;
	
	@Override
	public ProxyPurseBean getModel() {
		return proxyPurseBean;
	}
	
	public ProxyPurseBean getProxyPurseBean() {
		return proxyPurseBean;
	}
	public void setProxyPurseBean(ProxyPurseBean proxyPurseBean) {
		this.proxyPurseBean = proxyPurseBean;
	}
	public IProxyPurseService getProxyPurseService() {
		return proxyPurseService;
	}
	public void setProxyPurseService(IProxyPurseService proxyPurseService) {
		this.proxyPurseService = proxyPurseService;
	}
	
	File upload=null;

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	/**
	 * 钱包分润调整查询
	 * @author ztt
	 */
	public void queryUnnoAdjtxn(){
		DataGridBean dgd = new DataGridBean();
		try {
			dgd = proxyPurseService.queryUnnoAdj(proxyPurseBean);
		} catch (Exception e) {
			log.error("查询分润调整异常：" + e);
		}
		super.writeJson(dgd);
	}

	/**
	 * 钱包实时余额
	 */
	public void queryUnnoPurseBalanceGrid(){
		DataGridBean dgd = new DataGridBean();
		try {
			dgd = proxyPurseService.queryUnnoPurseBalanceGrid(proxyPurseBean);
		} catch (Exception e) {
			log.error("查询分润调整异常：" + e);
		}
		super.writeJson(dgd);
	}

	/**
	 * 钱包实时余额导出
	 */
	public void exportUnnoPurseBalanceLists() {
		try {
			String excelName = "钱包实时余额";
			List<Map<String, Object>> list = proxyPurseService.exportUnnoPurseBalanceGrid(proxyPurseBean);
			String[] titles = { "代理机构号","代理名称","代理级别","上级代理机构号","归属一代机构号","归属运营中心机构号","实时余额","钱包类型"};
			List excelList = new ArrayList();
			excelList.add(titles);
			try {
				for (int rowId = 0; rowId < list.size(); rowId++) {
					Map order = list.get(rowId);
					String[] rowContents = {
							String.valueOf(order.get("UNNO")==null?"":order.get("UNNO")),
							String.valueOf(order.get("UN_NAME")==null?"":order.get("UN_NAME")),
							String.valueOf(order.get("UN_LVL")==null?"":order.get("UN_LVL")),
							String.valueOf(order.get("UPPER_UNIT")==null?"":order.get("UPPER_UNIT")),
							String.valueOf(order.get("YIDAI")==null?"":order.get("YIDAI")),
							String.valueOf(order.get("YUNYING")==null?"":order.get("YUNYING")),
							String.valueOf(order.get("BALANCE")==null?"":order.get("BALANCE")),
							String.valueOf(order.get("WALLETTYPE")==null?"分润":order.get("WALLETTYPE").toString().equals("1")?"返现":"分润")
					};
					excelList.add(rowContents);
				}
			} catch (Exception e) {
				log.error("钱包实时余额导出异常:"+e.getMessage());
			}
			String[] cellFormatType = { "t","t","t","t","t","t","t","t"};
			UsePioOutExcel.writeExcel(excelList, titles.length, super.getResponse(), excelName,excelName+".xls", cellFormatType);
		} catch (Exception e) {
			log.error("钱包实时余额导出异常:"+e.getMessage());
		}
	}
	
	/**
	 * 钱包分润调整--添加
	 * @author ztt
	 */
	public void addUnnoAdj() {
		boolean flag=false;
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession == null) {
			json.setSessionExpire(true);
			super.writeJson(json);
		}
		try {
			//判断机构号是否存在
			List<Map<String, Object>> list = proxyPurseService.queryUnno(proxyPurseBean);
			if (list.size() < 1) {
				json.setSuccess(false);
				json.setMsg("机构不存在，请核查");
			} else {
				//判断机构是否存在于钱包内
				List<Map<String, Object>> purseList = proxyPurseService.queryPurse(proxyPurseBean.getUNNO(),proxyPurseBean.getWalletType());
				if (purseList.size() > 0) {
					Object object = purseList.get(0).get("PUPID");
					//添加
					flag = proxyPurseService.addUnnAdj(proxyPurseBean, ((UserBean) userSession).getLoginName(),
							object.toString());
					if (flag) {
						json.setSuccess(true);
						json.setMsg("添加成功");
					} else {
						json.setSuccess(false);
						json.setMsg("添加失败");
					}
				} else {
					json.setSuccess(false);
					String result = proxyPurseBean.getWalletType() == 1 ?"返现":"分润";
					json.setMsg("该机构暂无" + result + "钱包无法调整余额，请核对！");
				}
			} 
		} catch (Exception e) {
			log.error("添加异常：" + e);
			json.setMsg("添加失败");
		}
		super.writeJson(json);
	}

	public void importUnnoAdjAmt() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				synchronized(proxyPurseService){
					String filename = ServletActionContext.getRequest().getParameter("fileUnnoAdj");	//获取文件名
					String dasePath = ServletActionContext.getServletContext().getRealPath("/upload");
					File dir = new File(dasePath);
					dir.mkdirs();
					String path = dasePath + "/" + filename;
					File destFile = new File(path);
					upload.renameTo(destFile);
					String folderPath = ServletActionContext.getServletContext().getRealPath("/upload");// 存放上传文件的路径
					String xlsfile = folderPath + "/" + filename;
					try {
						if (xlsfile.length() > 0 && xlsfile != null) {
							List<Map<String,Object>> list = proxyPurseService.saveUnnoAdj(xlsfile,((UserBean)userSession).getLoginName(),filename); // 上传文件
							if (list.size()>0) {
								String[] titles = {"unno","失败原因"};
								List excelList = new ArrayList();
								excelList.add(titles);
								for (int rowId = 0; rowId < list.size(); rowId++) {
									Map order = (Map)list.get(rowId);
									String[] rowContents = {
											String.valueOf(order.get("unno")==null?"":order.get("unno")),
											String.valueOf(order.get("msg")==null?"":order.get("msg")),
									};
									excelList.add(rowContents);
								}
								String[] cellFormatType = {"t","t"};
								UsePioOutExcel.writeExcel(excelList, titles.length, super.getResponse(), "批量调整","批量调整失败" + ".xls", cellFormatType);
							}else{
								json.setSuccess(true);
								json.setMsg("记录更新成功！");
								super.writeJson(json);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						json.setMsg("文件更新失败!"+e);
						super.writeJson(json);
					}
					File file = new File(folderPath);
					String[] tempList = file.list();
					log.info("上传文件夹中文件的个数：" + tempList.length);
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
							log.info("成功删除文件:第" + j + "个文件删除是否成功？" + flag);
						} else {
							log.info("文件不存在");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				json.setSuccess(true);
				json.setMsg("文件更新失败!");
			}
		}
	}

	/**
	 * 钱包分润调整--批量导入
	 * @author ztt
	 */
	public void importUnnoAdj() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {	
				synchronized(proxyPurseService){
					String filename = ServletActionContext.getRequest().getParameter("fileUnnoAdj");	//获取文件名
					String dasePath = ServletActionContext.getServletContext().getRealPath("/upload");	
					File dir = new File(dasePath);
					dir.mkdirs();
					// 使用UUID做为文件名，以解决重名的问题
					String path = dasePath + "/" + filename;
					File destFile = new File(path);
					upload.renameTo(destFile);
					String folderPath = ServletActionContext.getServletContext().getRealPath("/upload");// 存放上传文件的路径
					String xlsfile = folderPath + "/" + filename;
					try {
						if (xlsfile.length() > 0 && xlsfile != null) {
							//判断该批次是否已经上传
							List<Map<String,String>> lis=proxyPurseService.queryPsmj(filename.substring(0, filename.indexOf(".")));
							Object obj=lis.get(0).get("NUMS");
							if(Integer.parseInt(obj.toString())>1){
								json.setSuccess(false);
								json.setMsg("该批次余额调整已经上传,请勿重新导入！");
								super.writeJson(json);
							}else{
								List<Map<String,Object>> list = proxyPurseService.saveUnnoAdj(xlsfile,((UserBean)userSession).getLoginName(),filename); // 上传文件
								if (list.size()>0) {// 如果不为true,代表上传失败	
									String[] titles = {"unno","调整金额","调整原因(备注)","钱包类型","失败原因"};
									List excelList = new ArrayList();
									excelList.add(titles);
									for (int rowId = 0; rowId < list.size(); rowId++) {
										Map order = (Map)list.get(rowId);
										String[] rowContents = { 
												String.valueOf(order.get("UNNO")==null?"":order.get("UNNO")),
												String.valueOf(order.get("FEEAMT")==null?"":order.get("FEEAMT")),
												String.valueOf(order.get("FEENOTE")==null?"":order.get("FEENOTE")),
												String.valueOf(order.get("WALLETTYPE")==null?"":order.get("WALLETTYPE")),
												String.valueOf(order.get("INFO")==null?"":order.get("INFO"))
										};
										excelList.add(rowContents);
									}
									list=null;
									String[] cellFormatType = {"t","t","t","t","t"};
									UsePioOutExcel.writeExcel(excelList, 5, super.getResponse(), "钱包分润调整记录","钱包分润调整记录" + ".xls", cellFormatType); // 调用导出方法								
								}else{
									json.setSuccess(true);
									json.setMsg("记录更新成功！");
									super.writeJson(json);
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						json.setMsg("文件更新失败!"+e);
						super.writeJson(json);
					}
					File file = new File(folderPath);
					String[] tempList = file.list();
					log.info("上传文件夹中文件的个数：" + tempList.length);
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
							log.info("成功删除文件:第" + j + "个文件删除是否成功？" + flag);
						} else {
							log.info("文件不存在");
						}
					}
			   }
			} catch (Exception e) {
				e.printStackTrace();
				json.setSuccess(true);
				json.setMsg("文件更新失败!");
			}		
		}
	}
	/**
	 * 钱包分润调整--导出
	 * @author ztt
	 */
	public void reportUnnoAdj() {
		try {
			String excelName;
			excelName = "钱包分润调整表";
			List<Map<String, Object>> list = proxyPurseService.reportUnnoAdj(proxyPurseBean);
			
			String[] titles = { "代理机构号","代理名称","代理级别","上级代理机构号","归属一代机构号","归属运营中心机构号","调整金额"
					,"调整日期","调整原因(备注)","类型"};
			List excelList = new ArrayList();
			excelList.add(titles);
			try {
				for (int rowId = 0; rowId < list.size(); rowId++) {
					Map order = list.get(rowId);
					String[] rowContents = { 
							String.valueOf(order.get("UNNO")==null?"":order.get("UNNO")),
							String.valueOf(order.get("UN_NAME")==null?"":order.get("UN_NAME")),
							String.valueOf(order.get("UNNOLVL")==null?"":order.get("UNNOLVL")),
							String.valueOf(order.get("UPPER_UNIT")==null?"":order.get("UPPER_UNIT")),
							String.valueOf(order.get("FIRSTUNNO")==null?"":order.get("FIRSTUNNO")),
							String.valueOf(order.get("YUNYING")==null?"":order.get("YUNYING")),
							String.valueOf(order.get("FEEAMT")==null?"":order.get("FEEAMT")),
							String.valueOf(order.get("SETTLEDAY")==null?"":order.get("SETTLEDAY")),
							String.valueOf(order.get("FEENOTE")==null?"":order.get("FEENOTE")),
							String.valueOf(order.get("WALLETTYPE")==null?"分润":order.get("WALLETTYPE").toString().equals("1")?"返现":"分润"),
					};
					excelList.add(rowContents);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			String[] cellFormatType = { "t","t","t","t","t","t","t","t","t","t"};
			UsePioOutExcel.writeExcel(excelList, titles.length, super.getResponse(), excelName,excelName+".xls", cellFormatType);
		} catch (Exception e) {
			log.error("钱包分润调整异常:"+e);
		}
	}

	/**
	 * 批量调整模板下载
	 */
	public void dowloadUnnoAdjtxnXls(){
		try {
			ExcelServiceImpl.download(getResponse(),"unno_Adjtxn.xls","批量调整模板.xls");
		} catch (Exception e) {
			log.info("批量调整模板下载失败:"+e);
		}
	}
	
	/**
	 * 钱包一代额度查询统计---查询
	 */
	public void queryGenerationQuotaStatistics(){
		DataGridBean dgd = new DataGridBean();
		try {
			dgd = proxyPurseService.queryGenerationQuotaStatistics(proxyPurseBean);
		} catch (Exception e) {
			log.error("查询分润调整异常：" + e);
		}
		super.writeJson(dgd);
	}
	/**
	 * 钱包一代额度查询统计---导出
	 */
	public void reportGenerationQuotaStatistics(){
		try {
			if(null!=proxyPurseBean.getPROVINCE() && !"".equals(proxyPurseBean.getPROVINCE())) {
				String string = new String(proxyPurseBean.getPROVINCE().getBytes("ISO-8859-1"),"utf-8");
				proxyPurseBean.setPROVINCE(string);//中文乱码处理
			}
			String excelName;
			excelName = "钱包一代额度统计表";
			List<Map<String, Object>> list = proxyPurseService.reportGenerationQuotaStatistics(proxyPurseBean);
			
			String[] titles = { "一代机构号","一代机构名称","总额度","已提现额度","剩余额度","归属中心机构号","归属中心机构名称"};
			List excelList = new ArrayList();
			excelList.add(titles);
			try {
				for (int rowId = 0; rowId < list.size(); rowId++) {
					Map order = list.get(rowId);
					String[] rowContents = { 
							String.valueOf(order.get("FIRSTUNNO")==null?"":order.get("FIRSTUNNO")),
							String.valueOf(order.get("PROVINCE")==null?"":order.get("PROVINCE")),
							String.valueOf(order.get("BALANCE")==null?"":order.get("BALANCE")),
							String.valueOf(order.get("FEEAMT")==null?"":order.get("FEEAMT")),
							String.valueOf(order.get("CURAMT")==null?"":order.get("CURAMT")),
							String.valueOf(order.get("CENTERUNNO")==null?"":order.get("CENTERUNNO")),
							String.valueOf(order.get("CENTERUNNONAME")==null?"":order.get("CENTERUNNONAME")),
					};
					excelList.add(rowContents);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			list=null;
			String[] cellFormatType = { "t","t","t","t","t","t","t"};
			UsePioOutExcel.writeExcel(excelList, 7, super.getResponse(), excelName,excelName+".xls", cellFormatType); // 调用导出方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 分活动钱包流水查询
	 */
	public void queryPurseTurnoverRebatety(){
		DataGridBean dgd = new DataGridBean();
		try {
			dgd = proxyPurseService.queryPurseTurnoverRebatety(proxyPurseBean);
		} catch (Exception e) {
			log.error("查询分活动钱包流水异常：" + e);
		}
		super.writeJson(dgd);
	}
	
	/**
	 * 分活动钱包流水--导出
	 */
	public void reportPurseTurnoverRebatety() {
		try {
			String excelName;
			excelName = "分活动钱包流水";
			List<Map<String, Object>> list = proxyPurseService.reportPurseTurnoverRebatety(proxyPurseBean);
			String[] titles = { "代理机构号","代理名称","代理级别","上级代理机构号","归属一代机构号","归属运营中心机构号","活动类型","分润金额","返现金额","扣款金额","交易/返现/扣款日期","钱包类型"};
			List excelList = new ArrayList();
			excelList.add(titles);
			try {
				for (int rowId = 0; rowId < list.size(); rowId++) {
					Map order = list.get(rowId);
					String[] rowContents = { 
							String.valueOf(order.get("UNNO")==null?"":order.get("UNNO")),
							String.valueOf(order.get("UNNAME")==null?"":order.get("UNNAME")),
							String.valueOf(order.get("UNLEVEL")==null?"":order.get("UNLEVEL")),
							String.valueOf(order.get("UPPERUNNO")==null?"":order.get("UPPERUNNO")),
							String.valueOf(order.get("YIDAIUNNO")==null?"":order.get("YIDAIUNNO")),
							String.valueOf(order.get("YUNYINGUNNO")==null?"":order.get("YUNYINGUNNO")),
							String.valueOf(order.get("MINFO1")==null?"":order.get("MINFO1")),
							String.valueOf(order.get("FENRUNMDA")==null?"":order.get("FENRUNMDA")),
							String.valueOf(order.get("FANXIANMDA")==null?"":order.get("FANXIANMDA")),
							String.valueOf(order.get("KOUKUANMDA")==null?"":order.get("KOUKUANMDA")),
							String.valueOf(order.get("TXNDAY")==null?"":order.get("TXNDAY")),
							String.valueOf(order.get("WALLETTYPE")==null?"分润":order.get("WALLETTYPE").toString().equals("1")?"返现":"分润")
					};
					excelList.add(rowContents);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			list=null;
			String[] cellFormatType = { "t","t","t","t","t","t","t","t","t","t","t","t"};
			UsePioOutExcel.writeExcel(excelList, 12, super.getResponse(), excelName,excelName+".xls", cellFormatType); // 调用导出方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 分日钱包流水查询
	 */
	public void queryPurseTurnoverDay(){
		DataGridBean dgd = new DataGridBean();
		try {
			dgd = proxyPurseService.queryPurseTurnoverDay(proxyPurseBean);
		} catch (Exception e) {
			log.error("查询分活动钱包流水异常：" + e);
		}
		super.writeJson(dgd);
	}
	
	/**
	 * 分日钱包流水--导出
	 */
	public void reportPurseTurnoverDay() {
		try {
			String excelName;
			excelName = "分日钱包流水";
			List<Map<String, Object>> list = proxyPurseService.reportPurseTurnoverDay(proxyPurseBean);
			
			String[] titles = { "代理机构号","代理名称","代理级别","上级代理机构号","归属一代机构号",
					"归属运营中心机构号","期初金额","分润金额","返现金额","扣款金额","调整金额","提现结算金额","扣税金额","提现转账费","期末金额","交易日期","钱包类型"};
			List excelList = new ArrayList();
			excelList.add(titles);
			try {
				for (int rowId = 0; rowId < list.size(); rowId++) {
					Map order = list.get(rowId);
					String[] rowContents = { 
							String.valueOf(order.get("UNNO")==null?"":order.get("UNNO")),
							String.valueOf(order.get("UNNAME")==null?"":order.get("UNNAME")),
							String.valueOf(order.get("UNLEVEL")==null?"":order.get("UNLEVEL")),
							String.valueOf(order.get("UPPERUNNO")==null?"":order.get("UPPERUNNO")),
							String.valueOf(order.get("YIDAIUNNO")==null?"":order.get("YIDAIUNNO")),
							String.valueOf(order.get("YUNYINGUNNO")==null?"":order.get("YUNYINGUNNO")),
							String.valueOf(order.get("BEGINBALANCE")==null?"":order.get("BEGINBALANCE")),
							String.valueOf(order.get("FENRUNMDA")==null?"":order.get("FENRUNMDA")),
							String.valueOf(order.get("FENRUNMDA")==null?"":order.get("FANXIANMDA")),
							String.valueOf(order.get("FENRUNMDA")==null?"":order.get("KOUKUANMDA")),
							String.valueOf(order.get("ADJTXNAMT")==null?"":order.get("ADJTXNAMT")),
							String.valueOf(order.get("CASHAMT")==null?"":order.get("CASHAMT")),
							String.valueOf(order.get("TAXAMT")==null?"":order.get("TAXAMT")),
							String.valueOf(order.get("CASHFEE")==null?"":order.get("CASHFEE")),
							String.valueOf(order.get("ENDBALANCE")==null?"":order.get("ENDBALANCE")),
							String.valueOf(order.get("TXNDAY")==null?"":order.get("TXNDAY")),
							String.valueOf(order.get("WALLETTYPE")==null?"分润":order.get("WALLETTYPE").toString().equals("1")?"返现":"分润")
					};
					excelList.add(rowContents);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			list=null;
			String[] cellFormatType = { "t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t","t"};
			UsePioOutExcel.writeExcel(excelList, 17, super.getResponse(), excelName,excelName+".xls", cellFormatType); // 调用导出方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
