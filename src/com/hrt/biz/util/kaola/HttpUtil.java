package com.hrt.biz.util.kaola;

import java.util.Map;

import net.sf.json.JSONObject;

public class HttpUtil {
	
	public static String buildRequest(String url,Map<String, String> params) throws Exception {

		JSONObject json = JSONObject.fromObject(params);
		String rtnMsg = new String(HttpProtocolHandler.post(url, json),"UTF-8");
        return rtnMsg;
    }
}
