package com.hrt.biz.credit.action;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.credit.entity.pagebean.CreditInfoBean;
import com.hrt.biz.credit.entity.pagebean.LoanInfoBean;
import com.hrt.biz.credit.entity.pagebean.LoanRepayInfoBean;
import com.hrt.biz.credit.service.CreditAgentService;
import com.hrt.biz.credit.service.LoanRepayService;
import com.hrt.biz.credit.service.LoanService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

/**
 *	@author tenglong
 *	2017-01-5
 *	贷款授信代理基本业务
 */
public class LoanRepayAction extends BaseAction implements ModelDriven<LoanRepayInfoBean> {

	
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(LoanRepayAction.class);
	
	private LoanRepayInfoBean loanRepayInfoBean = new LoanRepayInfoBean();
	private LoanRepayService loanRepayService;
	
	@Override
	public LoanRepayInfoBean getModel() {
		return loanRepayInfoBean;
	}

	public LoanRepayInfoBean getLoanRepayInfoBean() {
		return loanRepayInfoBean;
	}


	public void setLoanRepayInfoBean(LoanRepayInfoBean loanRepayInfoBean) {
		this.loanRepayInfoBean = loanRepayInfoBean;
	}


	public LoanRepayService getLoanRepayService() {
		return loanRepayService;
	}


	public void setLoanRepayService(LoanRepayService loanRepayService) {
		this.loanRepayService = loanRepayService;
	}


	/**
	 * 还款申请
	 */
	public void LoanRepay(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		JsonBean json = new JsonBean();
		boolean flag =false;
		if(loanRepayInfoBean.getRemittanceFile() != null && loanRepayInfoBean.getRemittanceFile().length()>1024*512){
			json.setMsg("您上传的图片大于512KB，请重新添加！");
		}else{
			try {
				FileInputStream fis = new FileInputStream(loanRepayInfoBean.getRemittanceFile());    
				byte[] bytes = new byte[fis.available()];    
				fis.read(bytes);    
				fis.close();    
				// 生成字符串    
				String imgStr = byte2hex( bytes );    
				//System.out.println(imgStr); 
				loanRepayInfoBean.setRemittanceImg(imgStr);
				Map<String, Object> map=loanRepayService.LoanRepay(loanRepayInfoBean,user);
				if("0".equals(map.get("status"))){
					flag = true;
				}
				json.setMsg(map.get("msg")+"");
			} catch (Exception e) {
				log.info("还款申请异常"+e);
			}
		}
		json.setSuccess(flag);
		super.writeJson(json);
	}
	
	/** 
     * 二进制转字符串 
     * @param b byte数组 
     * @return 二进制字符串 
     */  
    public static String byte2hex(byte[] b){  
        StringBuffer sb = new StringBuffer();  
        String stmp = "";  
        for (int n = 0; n < b.length; n++) {  
            stmp = Integer.toHexString(b[n] & 0XFF);  
            if (stmp.length() == 1) {  
                sb.append("0" + stmp);  
            } else {  
                sb.append(stmp);  
            }  
        }  
        return sb.toString();  
    }
    
	/**
	 * 查询还款记录
	 */
	public void queryRepayInfo(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb=loanRepayService.queryRepayInfo(loanRepayInfoBean,user);
		} catch (Exception e) {
			log.info("查询还款记录异常"+e);
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 查询扣款记录
	 */
	public void queryDeAmtHist(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
			dgb=loanRepayService.queryDeAmtHist(loanRepayInfoBean,user);
		} catch (Exception e) {
			log.info("查询扣款记录异常"+e);
		}
		super.writeJson(dgb);
	}
	
}
