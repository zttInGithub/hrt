package com.hrt.biz.util.unionpay;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

import com.hrt.biz.util.gateway.Base64;

/**
 * sha 系列加密
 * @author lxj
 * 2018年9月27日
 */
public class SHAEncUtil {
	
	public static final String user_key = "4cMBwJNUvsk8PGbhbIf0wLWtQBfLWtQB";
	
	/***
	 * 利用Apache的工具类实现SHA-256加密,返回base64串
	 * 
	 * @param str
	 *            加密后的报文
	 * @return
	 */
	public static String getSHA256Str(String str) {
		MessageDigest messageDigest;
		String encdeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] hash = messageDigest.digest((str+user_key).getBytes("UTF-8"));
			encdeStr = Base64.encode(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encdeStr;
	}
	
	/***
	 * 利用Apache的工具类实现SHA-256加密,返回 Hex 串
	 * 
	 * @param str
	 *            加密后的报文
	 * @return
	 */
	public static String getSHA256Bytes(String str) {
		MessageDigest messageDigest;
		String ency = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
			ency = Hex.encodeHexString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ency;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// 8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		String encode = Base64.encode(messageDigest.digest("123456".getBytes("UTF-8")));
		System.out.println(encode);
		System.out.println("jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIMkjrcbJI=");
		System.out.println("-----------------------");
		String str = "cgSBxHL+XX04TQq/hph1eGFknUe+ujFIowu2wRTEDYpfT02FlJ5vfGsbkiVjx3TCF95KhjrqxADmM0Xqh84QmWllMRBjmczjpvOKBmg3E7jU4vJiElNaZNMvi+hTlcx1vwassfYdJG4PQ69lMPjt2kH0+/SXTtz0ZeL7QdLqspk6MNte9eiHou3O+Pl7aNDcoGGG1rqXjKb5j/+kDMIpwDAsZ9TWqWa7ON7Ibqwezw+MKmi8/wdJM2kqJlnX1h/XffcpUe4fG6elcoYORK08wAkAvd3bN+jakmBjTHYzVjLwxGMWu3u/6dlskbPrPUEJUomoPrNO77sPTHJ1SINf1gIY1rKBWU1r4izcuYXNYeawsuBDmfEVd6ZzSwlRyxoQ1V3YW4uYi+jTi00IiXeM6Gm32+BvMpewsopGznnNnhoFxTUxD0J7aAOzIZVAVpq0BV2vXKY9Y/jT6JY8d/+tZ6802mW3UGJegWbqYDVVyDPpwtX/4Jy+BdG5u5coSJ1M1WPo2iyi1UZl9U+vWBwKpXa47Aw714em9Sq3XntMAcq1rJrhZprI6OMmEle7rZLK8VTC22VQbkeOMgXHRTm/5w==155107710510RvlMjeFPs2aAhQdo/xt/Kg==c6zdyuyn0tb6nbni9coryrz45wh4s1q30";
		String sha256Str = getSHA256Str(str);
		System.out.println(sha256Str);
	}
}
