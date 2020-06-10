package com.hrt.biz.credit.action;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.biz.bill.entity.pagebean.AgentUnitBean;
import com.hrt.biz.bill.service.IAgentUnitService;
import com.hrt.biz.bill.service.impl.AgentUnitServiceImpl;
import com.hrt.biz.credit.entity.pagebean.CreditInfoBean;
import com.hrt.biz.credit.service.CreditAgentService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.HttpXmlClient;
import com.hrt.util.word;
import com.opensymphony.xwork2.ModelDriven;

/**
 *	@author tenglong
 *	2017-01-5
 *	贷款授信代理基本业务
 */
public class CreditAgentAction extends BaseAction implements ModelDriven<CreditInfoBean> {

	
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(CreditAgentAction.class);
	
	private CreditInfoBean creditInfoBean = new CreditInfoBean();
	private CreditAgentService creditAgentService;
	private IAgentUnitService agentUnitService;
	private char[] cnNumbers={'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'};
	private char[] series={'元','拾','百','仟','万','拾','百','仟','亿'};
	// 整数部分
	private String integerPart;
	// 小数部分
	private String floatPart;
	
	@Override
	public CreditInfoBean getModel() {
		return creditInfoBean;
	}
	
	public CreditInfoBean getCreditInfoBean() {
		return creditInfoBean;
	}

	public IAgentUnitService getAgentUnitService() {
		return agentUnitService;
	}

	public void setAgentUnitService(IAgentUnitService agentUnitService) {
		this.agentUnitService = agentUnitService;
	}

	public void setCreditInfoBean(CreditInfoBean creditInfoBean) {
		this.creditInfoBean = creditInfoBean;
	}


	public CreditAgentService getCreditAgentService() {
		return creditAgentService;
	}

	public void setCreditAgentService(CreditAgentService creditAgentService) {
		this.creditAgentService = creditAgentService;
	}

	/**
	 * 查询机构额度
	 */
	public void queryAvailableLimit(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if(userSession==null){
			json.setSuccess(false);
		}else{
			UserBean user=(UserBean)userSession;
			try {
				Map<String,String> map=creditAgentService.queryAvailableLimit(creditInfoBean,user);
				if(map!=null&&map.size()>0&&"0".equals(map.get("status"))){
					json.setSuccess(true);
					json.setObj(map);
				}else{
					json.setSuccess(false);
				}
			} catch (Exception e) {
				log.info("查询机构额度异常"+e);
			}
		}
		super.writeJson(json);
	}
	
	/*
	 * 下载贷款协议
	 */
	@SuppressWarnings("unchecked")
	public void listCreditAgentExcel(){
		DataGridBean dataGridBean = new DataGridBean();
		DataGridBean agBean = new DataGridBean();
		Map dataMap=new HashMap();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		String pROTOCOLNO=ServletActionContext.getRequest().getParameter("PROTOCOLNO");
		if(userSession!=null&&pROTOCOLNO!=null&&!"".equals(pROTOCOLNO)){
			UserBean user=(UserBean)userSession;
			creditInfoBean.setPROTOCOLNO(pROTOCOLNO);
			creditInfoBean.setPage(1);
			creditInfoBean.setRows(10);
			AgentUnitBean ag = new AgentUnitBean();
			ag.setUnno(user.getUnNo());
			try {
				agBean = agentUnitService.queryAgentUnitData(ag, user);
				dataGridBean = creditAgentService.queryCreditInfoData(creditInfoBean,user);
			} catch (Exception e) {
				log.info("下载贷款协议-查询贷款记录异常"+e);
			}
			try {
				if(dataGridBean!=null&&dataGridBean.getTotal()>0){
					Map<String, String> map = (Map<String, String>) dataGridBean.getRows().get(0);
					String money = map.get("CREAMT");
					//转换大写金额
					if(money.contains(".")){
				      // 如果包含小数点
				      int dotIndex=money.indexOf(".");
				      integerPart=money.substring(0,dotIndex);
				      floatPart=money.substring(dotIndex+1);
				    }
				    else{
				      // 不包含小数点
				      integerPart=money;
				    }
					String mStr = getCnString(money);
					ag =  (AgentUnitBean) agBean.getRows().get(0);
					dataMap.put("mStr", mStr);
					dataMap.put("downloadDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
					String [] d1 = map.get("APPROVEDTIME").split("-");
					dataMap.put("CREDATE0",d1[0] );//贷款日期
					dataMap.put("CREDATE1",d1[1] );//贷款日期
					dataMap.put("CREDATE2",d1[2].substring(0, 2) );//贷款日期
					dataMap.put("CRETIMELIMIT", map.get("CRETIMELIMIT"));  //贷款期限
					String [] r1 = map.get("REPAYTIME").split("-");
					dataMap.put("weiyue", "零");//违约
					dataMap.put("REPAYTIME0", r1[0]);//到期时间
					dataMap.put("REPAYTIME1", r1[1]);//到期时间
					dataMap.put("REPAYTIME2", r1[2]);//到期时间
					dataMap.put("legalPerson", ag.getLegalPerson());//法人
					dataMap.put("bankBranch", ag.getBankBranch());//开户银行
					dataMap.put("bankAccNo", ag.getBankAccNo());//开户账号
					dataMap.put("bankAccName", ag.getBankAccName());//开户名
					dataMap.put("baddr", ag.getBaddr());//经营地址
					dataMap.put("PROTOCOLNO", map.get("PROTOCOLNO"));
					dataMap.put("AGENTNAME", map.get("AGENTNAME"));
					dataMap.put("CREAMT", map.get("CREAMT"));
					int CRERATE = Integer.valueOf(map.get("CRERATE").substring(2));
					String CRERATE_STR = "";
					if(CRERATE==1){
						CRERATE_STR = "一";
					}else if (CRERATE==2){
						CRERATE_STR = "二";
					}else if (CRERATE==3){
						CRERATE_STR = "三";
					}else if (CRERATE==4){
						CRERATE_STR = "四";
					}else if (CRERATE==5){
						CRERATE_STR = "五";
					}else if (CRERATE==6){
						CRERATE_STR = "六";
					}else if (CRERATE==7){
						CRERATE_STR = "七";
					}else if (CRERATE==8){
						CRERATE_STR = "八";
					}else if (CRERATE==9){
						CRERATE_STR = "九";
					}
					dataMap.put("CRERATE", CRERATE_STR);
				}else{
					return ;
				}
				//dataMap.put("creditInfoBean", creditInfoBean);
				word w=new word();
				String filename="贷款协议("+creditInfoBean.getPROTOCOLNO()+").doc";
				String path="credit.xml";
				w.createDoc(dataMap, path, filename);
			} catch (Exception e) {
				log.info("下载贷款协议-解析异常"+e);
			}
		}
	}
	
	/**
	 * 微信交易明细导出所有
	 */
	public void wechantTxnExport(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=creditAgentService.wechantTxnExport(creditInfoBean,((UserBean)userSession));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("微信交易明细导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("微信交易明细导出Excel成功");
			} catch (Exception e) {
				log.error("微信交易明细导出Excel异常：" + e);
				json.setMsg("微信交易明细导出Excel失败");
			}
		}
	}
	
	/**
	 * 查询贷款记录
	 */
	public void queryCreditInfoData(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb=creditAgentService.queryCreditInfoData(creditInfoBean,user);
		} catch (Exception e) {
			log.info("查询贷款记录异常"+e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 微信交易商户汇总 导出所有
	 */
	public void checkWechatTxnDetailExcelAll(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=creditAgentService.checkWechatTxnDetailExcelAll(creditInfoBean,((UserBean)userSession));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("微信交易商户汇总".getBytes("GBK"), "ISO-8859-1") + ".xls");  
		        OutputStream ouputStream= response.getOutputStream();
		        wb.write(ouputStream);  
		        ouputStream.flush();  
		        ouputStream.close();
				json.setSuccess(true);
				json.setMsg("微信交易商户汇总Excel成功");
			} catch (Exception e) {
				log.error("微信交易商户汇总Excel异常：" + e);
				json.setMsg("查询微信交易商户汇总Excel失败");
			}
		}
	}
	
	/**
	 * 微信交易机构汇总 导出所有
	 */
	public void checkWechatTxnUnitDetailExcelAll(){
		HttpServletResponse response=getResponse();
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try{
				HSSFWorkbook wb=creditAgentService.checkWechatTxnUnitDetailExcelAll(creditInfoBean,((UserBean)userSession));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String("微信交易机构汇总导出".getBytes("GBK"), "ISO-8859-1") + ".xls");  
				OutputStream ouputStream= response.getOutputStream();
				wb.write(ouputStream);  
				ouputStream.flush();  
				ouputStream.close();
				json.setSuccess(true);
				json.setMsg("微信交易机构汇 导出Excel成功");
			} catch (Exception e) {
				log.error("微信交易机构汇 导出Excel异常：" + e);
				json.setMsg("微信交易机构汇 导出Excel失败");
			}
		}
	}
	
	 /**
	   * 取得大写形式的字符串
	   * @return
	   */
	  public String getCnString(String integerPart){
	    // 因为是累加所以用StringBuffer
	    StringBuffer sb=new StringBuffer();
	    
	    // 整数部分处理
	    for(int i=0;i<integerPart.length();i++){
	      int number=getNumber(integerPart.charAt(i));
	      
	      sb.append(cnNumbers[number]);
	      sb.append(series[integerPart.length()-1-i]);
	    }
	    
	    // 小数部分处理
	    if(floatPart!=null&&floatPart.length()>0){
	      sb.append("点");
	      for(int i=0;i<floatPart.length();i++){
	        int number=getNumber(floatPart.charAt(i));
	        
	        sb.append(cnNumbers[number]);
	      }
	    }
	    
	    // 返回拼接好的字符串
	    return sb.toString();
	  }
	  
	  /**
	   * 将字符形式的数字转化为整形数字
	   * 因为所有实例都要用到所以用静态修饰
	   * @param c
	   * @return
	   */
	  private static int getNumber(char c){
	    String str=String.valueOf(c);   
	    return Integer.parseInt(str);
	  }
	
	/**
	 * 微信交易退款
	 */
//	public void wechantTxnRefund(){
//		Object userSession = super.getRequest().getSession().getAttribute("user");
//		UserBean user=(UserBean)userSession;
//		JsonBean json =new JsonBean();
//		boolean flag2 =false;
//		try {
//			//查看退款金额是否合规
//			boolean flag1 = checkWechantTxnDetailService.queryIfWechantTxnRefund(checkWechatTxnDetailBean);
//				if(flag1){
//				//实现退款
//				flag2 = checkWechantTxnDetailService.saveWechantTxnRefund(checkWechatTxnDetailBean,user);
//				if(flag2){
//					json.setSuccess(true);
//					json.setMsg("退款成功");
//				}else{
//					json.setSuccess(false);
//					json.setMsg("退款失败");
//				}
//			}else{
//				json.setSuccess(false);
//				json.setMsg("退款金额不足");
//			}
//		} catch (Exception e) {
//			log.info("微信交易退款异常"+e);
//			json.setSuccess(false);
//			json.setMsg("退款失败");
//		}
//		super.writeJson(json);
//	}
}
