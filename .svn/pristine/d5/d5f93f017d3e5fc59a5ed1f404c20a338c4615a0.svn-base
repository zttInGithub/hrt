package com.hrt.biz.util.gateway;

import java.util.Random;

public class AESCryptPostUtil {

	public final static String DATA_ERROR = "Data error!";
	private static final int RANDOM_LENGTH = 16;

	
	/**
	 * 解密,根据秘钥解密
	 * @param password
	 * @param content
	 * @return
	 */
	public static String decryptByPassword(String password, String content) {

		String result = DATA_ERROR;
		if (null != password) {
			try {
				
				AESCrypt aesCrypt = new AESCrypt(password);
				// 解密
				content = aesCrypt.decrypt(content);
				String contentStr = content.substring(0, content.length() - 32);
				String md5Str = content.substring(content.length() - 32,
						content.length());
				String checkMd5Str = MD5Util.MD5(contentStr);
				if (md5Str.equals(checkMd5Str)) {
					result = contentStr.substring(16, contentStr.length());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	
	/**
	 * 加密, 直接传秘钥进行加密
	 * @param password
	 * @param content
	 * @return
	 */
	public static String encryptByPassword(String password, String content) {
		String result = content;
		
		if(password != null){
			try {
				AESCrypt aesCrypt = new AESCrypt(password);
				// 获取16位随机数
				String randomStr = getRandom(RANDOM_LENGTH);
				String contentStr = randomStr + content;
				// MD5
				String md5Str = MD5Util.MD5(new String(contentStr.getBytes()));
				// 加密
				result = aesCrypt.encrypt((contentStr + md5Str));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	

	

	/**
	 * 获取指定位数的随机数
	 * @param length
	 * @return
	 */
	public static String getRandom(int length) {
        String randomNum = "";
        try {
            randomNum = String.format("%0" + length + "d", Math.abs(new Random().nextLong()));
            randomNum = randomNum.substring(0, length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return randomNum;
	}
}
