package com.hrt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StringUtil {

	/**
	 * 将字符串包括英文字母转为16进制
	 * 
	 * @author yaoy
	 * @param
	 * @return
	 */
	public static String toHexString(String s) {
		StringBuilder str=new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString((int) ch);
			str.append(s4);
		}
		return str.toString();
	}
	
	/**
	 * 格式化时间为字符串
	 * @param time
	 * @param rex
	 * @return
	 */
	public static String toDateString(Date time,String rex){
		SimpleDateFormat format=new SimpleDateFormat(rex);
		return format.format(time);
	}
	
	/**
	 * 格式化字符串为时间
	 * @param time
	 * @param rex
	 * @return
	 */
	public static Date toStrDate(String time,String rex){
		SimpleDateFormat format=new SimpleDateFormat(rex);
		Date date=new Date();
		try {
			date=format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}

	public static boolean isNotEmpty(String str) {
		return (!(isEmpty(str)));
	}

	public static boolean isBlank(String str) {
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0))
			return true;
		for (int i = 0; i < strLen; ++i) {
			if (!(Character.isWhitespace(str.charAt(i)))) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNotBlank(String str) {
		return (!(isBlank(str)));
	}
	
}
