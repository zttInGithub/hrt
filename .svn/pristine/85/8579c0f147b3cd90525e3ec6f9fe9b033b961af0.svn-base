package com.hrt.biz.util.gateway;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;


public class HttpUtils {
	
	/*
	 * params 填写的URL的参数 encode 字节编码
	 */
	public static String sendPostMessage(String PATH,String params,
			String encode,String requestMethod,Map<String, String> headParams) throws Exception {
		// 表示服务器端的url
		//String PATH = "http://120.24.239.82:8001/ExternalHandler.api?action=GetAllIntroducerCardByIdNo";
		System.out.println("请求地址："+PATH);
		URL url = new URL(PATH);		
		
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setConnectTimeout(3000);
		httpURLConnection.setDoInput(true);// 从服务器获取数据
		httpURLConnection.setDoOutput(true);// 向服务器写入数据

		httpURLConnection.setRequestMethod(requestMethod.toUpperCase());
		if (params != null && !params.isEmpty()) {
			// 获得上传信息的字节大小及长度
			byte[] mydata = params.getBytes();
			// 设置请求体的类型
			//httpURLConnection.setRequestProperty("Content-Type",
			//		"application/x-www-form-urlencoded;charset=utf-8");
			httpURLConnection.setRequestProperty("Content-Lenth", String
					.valueOf(mydata.length));
			
			if(headParams!=null){
				Set<Map.Entry<String,String>> entrySet=headParams.entrySet();
	            for(Map.Entry<String,String> entry: entrySet){
	            	httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
	            }  
	        } 
	
			// 获得输出流，向服务器输出数据
			OutputStream outputStream = httpURLConnection
					.getOutputStream();
			outputStream.write(mydata);
		}
		// 获得服务器响应的结果和状态码
		int responseCode = httpURLConnection.getResponseCode();	
		System.out.println("返回响应码："+responseCode);
		if (responseCode == 200) {
			// 获得输入流，从服务器端获得数据
			InputStream inputStream = httpURLConnection
					.getInputStream();
			return (changeInputStream(inputStream, encode));
		}
		return "";
	}

	/*
	 * 把从输入流InputStream按指定编码格式encode变成字符串String
	 */
	public static String changeInputStream(InputStream inputStream,
			String encode) throws Exception {		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if (inputStream != null) {			
			while ((len = inputStream.read(data)) != -1) {
				byteArrayOutputStream.write(data, 0, len);
			}
			result = new String(byteArrayOutputStream.toByteArray(), encode);
		}
		return result;
	}
}
