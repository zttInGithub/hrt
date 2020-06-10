package com.hrt.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Hex;


/**
 * 处理类似于微信报文的简单单层xml
 * @author aibing
 * 2016年11月9日
 */
public class SimpleXmlUtil {
	
	

	
	/**
	 * 获取签名字符串(键按ascii排序,排除exc键,排除空值,使用url键值对格式)
	 * @param map
	 * @param exc
	 * @return
	 */
	public static String getSignBlock(Map<String,String> map,String exc) {
		Set<String> set = map.keySet();
		String[] keys = set.toArray(new String[0]);
		Arrays.sort(keys);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.length; i++) {
			if (!keys[i].equals(exc) && null!=map.get(keys[i]) && !"".equals(map.get(keys[i]))) {
				sb.append(String.format("%s=%s&",keys[i],map.get(keys[i])));
			}
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	public static String getSignBlock(Map<String,String> map) {
		return getSignBlock(map,null);
	}
	
	public static String getMd5Sign(Map<String,String> map,String key) {
		Set<String> set = map.keySet();
		String[] keys = set.toArray(new String[0]);
		Arrays.sort(keys);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.length; i++) {
			if (!keys[i].equals("sign") && null!=map.get(keys[i]) && !"".equals(map.get(keys[i]))) {
				sb.append(String.format("%s=%s&",keys[i],map.get(keys[i])));
			}
		}
		sb.append("key=").append(key);
		String result = "";
		try {
			result = digestUpperHex(sb.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	public static String digestUpperHex(byte[] bytes){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}
		return new String(Hex.encodeHex(md.digest(bytes),false));
	}
}
