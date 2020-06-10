package com.hrt.biz.check.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hrt.biz.check.entity.pagebean.CheckRealtxnBean;
import com.hrt.biz.check.service.CheckRealtxnService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.opensymphony.xwork2.ModelDriven;


/**
 * @实时交易
 *
 */
public class CheckRealtxnAction extends BaseAction implements ModelDriven<CheckRealtxnBean> {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(CheckRealtxnAction.class);
	
	private CheckRealtxnBean checkRealtxnBean =new CheckRealtxnBean();
	private CheckRealtxnService checkRealtxnService;
	
	@Override
	public CheckRealtxnBean getModel() {
		return checkRealtxnBean;
	}
	
	public CheckRealtxnService getCheckRealtxnService() {
		return checkRealtxnService;
	}

	public void setCheckRealtxnService(CheckRealtxnService checkRealtxnService) {
		this.checkRealtxnService = checkRealtxnService;
	}
	
	/**
	 * @查询实时交易
	 */
	public void listRealtxn(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		DataGridBean dgb = new DataGridBean();
		if (userSession == null) {
		} else {
			try {
				dgb = checkRealtxnService.listRealtxn(checkRealtxnBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("实时交易信息查询异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	/**
	 * @查询实时交易汇总
	 */
	public void listRealtxnTotal(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		List<Map<String, Object>> list = null;
		if (userSession == null) {
		} else {
			try {
				list = checkRealtxnService.listRealtxnTotal(checkRealtxnBean,(UserBean)userSession);
			} catch (Exception e) {
				log.error("实时交易汇总查询异常：" + e);
			}
		}
		super.writeJson(list);
	}
	
	/**
	 * @导出实时交易
	 */
	public void exportRealtxn() {
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			if (userSession == null) {
				json.setSessionExpire(true);
			} else {
				List<Map<String,Object>> list = checkRealtxnService.exportRealtxn(checkRealtxnBean,(UserBean)userSession);
				
				List<String[]> excelList = new ArrayList<String[]>();
				String title [] = {"商户编号","商户名称","机构号","机构名称","业务员","终端编号","门店名称","交易渠道","交易金额","交易方式","交易类型","交易日期","交易时间","交易卡号","卡类型","授权码","流水号/商户订单号","参考号","处理状态","SN号"};
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					if("0".equals(map.get("ISMPOS").toString())){
						map.put("ISMPOS","传统POS");
					}else if("1".equals(map.get("ISMPOS").toString())){
						map.put("ISMPOS","MPOS");
					}else if("2".equals(map.get("ISMPOS").toString())){
						map.put("ISMPOS","会员宝收银台");
					}else if("3".equals(map.get("ISMPOS").toString())){
						map.put("ISMPOS","会员宝PLUS");
					}else if("4".equals(map.get("ISMPOS").toString())){
						map.put("ISMPOS","会员宝Pro");
					}
					if(map.get("CARDTYPE")==null){
						map.put("CARDTYPE","");
					}else if("1".equals(map.get("CARDTYPE").toString())){
						map.put("CARDTYPE","借记卡");
					}else if("2".equals(map.get("CARDTYPE").toString())){
						map.put("CARDTYPE","贷记卡");
					}
					if("0".equals(map.get("TXNTYPE").toString())){
						map.put("TXNTYPE","银行卡");
					}else if("1".equals(map.get("TXNTYPE").toString())){
						map.put("TXNTYPE","微信");
					}else if("2".equals(map.get("TXNTYPE").toString())){
						map.put("TXNTYPE","支付宝");
					}else if("3".equals(map.get("TXNTYPE").toString())){
						map.put("TXNTYPE","银联二维码");
					}else if("8".equals(map.get("TXNTYPE").toString())){
						map.put("TXNTYPE","快捷支付");
					}else if("5".equals(map.get("TXNTYPE").toString())){
						map.put("TXNTYPE","手机Pay");
					}else if("10".equals(map.get("TXNTYPE").toString())){
						map.put("TXNTYPE","信用卡还款");
					}else if("11".equals(map.get("TXNTYPE").toString())){
						map.put("TXNTYPE","花呗");
					}else if("12".equals(map.get("TXNTYPE").toString())){
						map.put("TXNTYPE","花呗分期");
					}
					if("0".equals(map.get("MTI").toString())){
						map.put("MTI","消费");
					}else if("1".equals(map.get("MTI").toString())){
						map.put("MTI","预授权");
					}else if("2".equals(map.get("MTI").toString())){
						map.put("MTI","预授权撤销");
					}else if("3".equals(map.get("MTI").toString())){
						map.put("MTI","消费撤销");
					}else if("4".equals(map.get("MTI").toString())){
						map.put("MTI","冲正");
					}else if("5".equals(map.get("MTI").toString())){
						map.put("MTI","扫码退款");
					}else if("6".equals(map.get("MTI").toString())){
						map.put("MTI","银行卡退货");
					}
					if("0".equals(map.get("TXNSTATE").toString())){
						map.put("TXNSTATE","成功");
					}else if("1".equals(map.get("TXNSTATE").toString())){
						map.put("TXNSTATE","失败");
					}else if("2".equals(map.get("TXNSTATE").toString())){
						map.put("TXNSTATE","待付款");
					}
					String []rowContents ={
							map.get("MID")==null?"":map.get("MID").toString(),	
							map.get("RNAME")==null?"":map.get("RNAME").toString(),
							map.get("UNNO")==null?"":map.get("UNNO").toString(),
							map.get("UNNAME")==null?"":map.get("UNNAME").toString(),
							map.get("SALENAME")==null?"":map.get("SALENAME").toString(),
							map.get("TID")==null?"":map.get("TID").toString(),
							map.get("INSTALLEDNAME")==null?"":map.get("INSTALLEDNAME").toString(),
							map.get("ISMPOS")==null?"":map.get("ISMPOS").toString(),	
							map.get("TXNAMOUNT")==null?"":map.get("TXNAMOUNT").toString(),
							map.get("TXNTYPE")==null?"":map.get("TXNTYPE").toString(),
							map.get("MTI")==null?"":map.get("MTI").toString(),
							map.get("TXNDAY")==null?"":map.get("TXNDAY").toString(),
							map.get("TXNTIME")==null?"":map.get("TXNTIME").toString(),
							map.get("CARDPAN")==null?"":map.get("CARDPAN").toString(),
							map.get("CARDTYPE")==null?"":map.get("CARDTYPE").toString(),
							map.get("AUTHCODE")==null?"":map.get("AUTHCODE").toString(),
							map.get("ORDERID")==null?"":map.get("ORDERID").toString(),
							map.get("RRN")==null?"":map.get("RRN").toString(),
							map.get("TXNSTATE")==null?"":map.get("TXNSTATE").toString(),
							map.get("SN")==null?"":map.get("SN").toString()
						};
					excelList.add(rowContents);
					}
				
					String excelName = "实时交易.csv";
					
					JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
					json.setSuccess(true);
					json.setMsg("导出实时交易成功");
			}
		} catch (Exception e) {
			log.error("导出实时交易：" + e);
		}
	}
}