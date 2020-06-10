package com.hrt.biz.util;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class SignatureUtil
{
	
	/**
	* SHA-512ժҪ
	* @param secret �Ự��Կ
	*/
	public static String signature(Map<String, String> params, String secret)
	{
		String result = null;
		StringBuffer orgin = getSignParam(params);
		if (orgin == null)
			return result;
		orgin.append(secret);
		try
		{
			System.out.println("orgin=" + orgin.toString());
			 result = SHA512.getSHA512ofStr(orgin.toString());
		}
		catch (Exception e)
		{
			throw new java.lang.RuntimeException("sign error !");
		}
		return result;
	}
	
	/**
	* ��Ӳ���ķ�װ����
	*/
	private static StringBuffer getSignParam(Map<String, String> params)
	{
		StringBuffer sb = new StringBuffer();
		if (params == null)
			return null;
		Map<String, String> treeMap = new TreeMap<String, String>();
		treeMap.putAll(params);
		Iterator<String> iter = treeMap.keySet().iterator();
		while (iter.hasNext())
		{
			String name = (String) iter.next();
			sb.append(name).append(params.get(name));
		}
		return sb;
	}
}
