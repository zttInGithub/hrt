package com.hrt.biz.util.unionpay;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AESUtil {

	private static final Log log = LogFactory.getLog(AESUtil.class);

	/**
	 * 注意key和加密用到的字符串是不一样的 加密还要指定填充的加密模式和填充模式 AES密钥可以是128或者256，加密模式包括ECB, CBC等
	 * ECB模式是分组的模式，CBC是分块加密后，每块与前一块的加密结果异或后再加密 第一块加密的明文是与IV变量进行异或
	 */
	public static final String KEY_ALGORITHM = "AES";
	public static final String ECB_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	public static final String PLAIN_KEY = "HRThyb6235pay311";
	/**
	 * IV(Initialization Value)是一个初始值，对于CBC模式来说，它必须是随机选取并且需要保密的
	 * 而且它的长度和密码分组相同(比如：对于AES 128为128位，即长度为16的byte类型数组)
	 * @throws UnsupportedEncodingException 
	 * 
	 */

	
	/**
	 * 使用ECB模式进行加密。 加密过程三步走： 1. 传入算法，实例化一个加解密器 2. 传入加密模式和密钥，初始化一个加密器 3.
	 * 调用doFinal方法加密
	 * 
	 * @param plainText
	 * @return
	 */
	public static byte[] AesEcbEncode(byte[] plainText, SecretKey key) {

		try {

			Cipher cipher = Cipher.getInstance(ECB_CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用ECB解密，三步走，不说了
	 * 
	 * @param decodedText
	 * @param key
	 * @return
	 */
	public static String AesEcbDecode(byte[] decodedText, SecretKey key) {
		try {
			Cipher cipher = Cipher.getInstance(ECB_CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			return new String(cipher.doFinal(decodedText));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 1.创建一个KeyGenerator 2.调用KeyGenerator.generateKey方法
	 * 由于某些原因，这里只能是128，如果设置为256会报异常，原因在下面文字说明
	 * 
	 * @return
	 */
	public static byte[] generateAESSecretKey() {
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
			// keyGenerator.init(256);
			return keyGenerator.generateKey().getEncoded();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 还原密钥
	 * 
	 * @param secretBytes
	 * @return
	 */
	public static SecretKey restoreSecretKey(byte[] secretBytes) {
		SecretKey secretKey = new SecretKeySpec(PLAIN_KEY.getBytes(), KEY_ALGORITHM);
		return secretKey;

		// return "HRThyb6235pay311".getBytes();
	}
	
	public static SecretKey restoreSecretKey(String aesKey) {
		byte[] keyBytes = new byte[32];
		System.arraycopy(aesKey, 0, keyBytes, 0, keyBytes.length);
		SecretKey secretKey = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
		return secretKey;
	}
	
	public static SecretKey restoreSecretKey() {
		SecretKey secretKey = new SecretKeySpec(PLAIN_KEY.getBytes(), KEY_ALGORITHM);
		return secretKey;
	}
	/**
	 * base64+aes加密
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String base64AndAesEncode(String data) throws UnsupportedEncodingException {
		SecretKey key = restoreSecretKey();
		byte s [] = AesEcbEncode(data.getBytes(), key);
		byte b[] = Base64.encodeBase64(s);
		return new String(b,"utf-8");
	}
	/**
	 * base64+ase解密
	 * @param data
	 * @return
	 */
	public static String base64AndAesDecode(String data) {
		SecretKey key = restoreSecretKey();
		String s = AesEcbDecode(Base64.decodeBase64(data), key);
		return s;
	}
	public static void main(String[] arg) throws UnsupportedEncodingException {
//		byte b[] = "123456".getBytes();
//		byte [] b1 = Base64.encodeBase64(b);
//		
//		String s1 = new String(b1, "utf-8");
//		
//		byte [] b2 = Base64.decodeBase64(b1);
//		String s2 = new String(b2,"utf-8");
//		System.out.println(s1+"   "+s2);
//		
		
//		String s1 = "1231231";
//		SecretKey key = restoreSecretKey();
//		byte s [] = AesEcbEncode(s1.getBytes(), key);
//		String s3  = AesEcbDecode(s, key);
//		System.out.println(s3);
//		
//		byte s2 [] = AesEcbEncode(s, key);
//		String s4 = new String(s2);
//		System.out.println(s2);
//		String s2 = AESUtil.AesEcbDecode(s, key);
//		System.out.println(s2);
//		Timer timer = new Timer();
//		TimerTask task = new TimerTask() {
//			
//			@Override
//			public void run() {
//				System.out.println("定时任务");
//				
//			}
//		};
//		System.out.println("kaishi");
//		timer.schedule(task, 2000);
		
		SecretKey key = restoreSecretKey();
		String string = AESUtil.base64AndAesDecode("HtjCZkTy7FygCLMjG1b1yMQAa+q1yvJa+gFXCgY6tTQ=");
		try {
//			string  = com.hrt.biz.util.unionpay.AESUtil.encryptWithBase64("6235240000020739120");
//			System.out.println(string);
		} catch (Exception e) {
			log.error("main 异常:"+e);
		}
		
		
	}
}