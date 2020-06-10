package com.hrt.biz.util.gateway;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class AESCrypt {

	private final Cipher cipher;
	private final SecretKeySpec key;
	private AlgorithmParameterSpec spec;

	public AESCrypt(String keyString) throws NoSuchAlgorithmException,
			UnsupportedEncodingException, NoSuchPaddingException {
		super();
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		// hash password with SHA-256 and crop the output to 128-bit for key
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(keyString.getBytes("UTF-8"));
		byte[] keyBytes = new byte[32];
		System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
		cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		key = new SecretKeySpec(keyBytes, "AES");
		spec = getIV();
	}

	public String encrypt(String plainText) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, key, spec);
		byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
		String encryptedText = Base64.encode(encrypted);
		return encryptedText;
	}

	public String decrypt(String cryptedText) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, key, spec);
		byte[] bytes = Base64.decode(cryptedText);
		byte[] decrypted = cipher.doFinal(bytes);
		String decryptedText = new String(decrypted, "UTF-8");
		return decryptedText;
	}
	
	public AlgorithmParameterSpec getIV() {
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
		IvParameterSpec ivParameterSpec;
		ivParameterSpec = new IvParameterSpec(iv);
		return ivParameterSpec;
	}
	
	/**
	 * 加密数据
	 * 
	 * @param plainText
	 * @return 返回byte字节流
	 * @throws Exception
	 */
	public byte[] byteWithEncrypt(String plainText) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, key, spec);
		byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
		return encrypted;
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            被加密的数据，没有进行base64处理过
	 * @return
	 * @throws Exception
	 */
	public String stringWithDecrpyt(byte[] data) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, key, spec);
		byte[] decrypted = cipher.doFinal(data);
		String decryptedText = new String(decrypted, "UTF-8");
		return decryptedText;
	}

}
