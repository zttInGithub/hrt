package com.hrt.frame.filter;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.servlet.FilterChain;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

import com.alibaba.fastjson.JSONObject;
import com.hrt.util.SimpleXmlUtil;  

//自定义扩展Struts2过滤器，用于解决struts会拦截cxf webservice请求的问题  
public class Struts2ExceuteFilter extends StrutsPrepareAndExecuteFilter {  

  private static Log log = LogFactory.getLog(Struts2ExceuteFilter.class);  
  private String c2bSignKey="dseesa325errtcyraswert749errtdyt";

  @Override  
  public void doFilter(ServletRequest req, ServletResponse res,  
          FilterChain chain) throws IOException, ServletException {
	  try {
			HttpServletRequest request = (HttpServletRequest) req;

			// 判断是否是向WebService发出的请求
			if (request.getRequestURI().contains("/service")) {
				// 如果是来自向CXFService发出的请求
				chain.doFilter(req, res);
			} else {
				String url = request.getRequestURI();
				if (isFilterUrl(url)) {
					HttpSession session = request.getSession(true);
					Object appId = session.getAttribute("APPID");//
					session.setMaxInactiveInterval(500);// 秒
					if (appId == null) {
						System.out.println("[银联绑卡]非法路径请求url-----》" + url);
						JSONObject json = new JSONObject();
						json.put("status", "E");
						json.put("sessionMsg", "登陆超时,请退出重新登录");
						@SuppressWarnings("unchecked")
						Map<String, String> itemMap = JSONObject.toJavaObject(json, Map.class);
						String sign = SimpleXmlUtil.getMd5Sign(itemMap, c2bSignKey);
						json.put("sign", sign);

						res.setCharacterEncoding("UTF-8");
						res.getWriter().write(json.toJSONString());
						return;
					} else {
						super.doFilter(req, res, chain);
						return;
					}
				}
				// 默认action请求
				super.doFilter(req, res, chain);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
  }  

  public Boolean isFilterUrl(String url) {
		if (url == null) {
			return false;
		}
		if ("".equals(url)) {
			return false;
		}
		if (url.endsWith("phoneCheckUnitDealData_encFindRiskReceiptsUploadData")) {
			return true;
		}
		if (url.endsWith("phoneMerchantWallet_decAddBankCard.action")) {
			return true;
		}
		if (url.endsWith("phoneMerchantWallet_decDeleteMerchantBankCard.action")) {
			return true;
		}
		if (url.endsWith("phoneMerchantWallet_encQueryMerchantBankCardList.action")) {
			return true;
		}
		if (url.endsWith("phoneMicroMerchantInfo_encQueryMicroMerchantInfo.action")) {
			return true;
		}
		if (url.endsWith("phoneMicroMerchantInfo_decMicroMerchantInfo.action")) {
			return false;
		}
		if (url.endsWith("phoneMicroMerchantInfo_decUpdateMicroMerchantInfo.action")) {
			return true;
		}
		if (url.endsWith("merchantAuthenticity_decMerchantAuthInfo.action")) {
			return true;
		}
		if (url.endsWith("phoneUser_decLogin.action")) {
			return true;
		}
		if (url.endsWith("decQueryBalance.action")) {
			return true;
		}
		if (url.endsWith("decQueryAsset.action")) {
			return true;
		}
		if (url.endsWith("decQueryCashWithAsset.action")) {
			return true;
		}
		if (url.endsWith("decQueryCashWithDrawal.action")) {
			return true;
		}
		if (url.endsWith("decQueryCashWithDrawalListData.action")) {
			return true;
		}
		return false;
	}

}
