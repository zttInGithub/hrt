package com.hrt.biz.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AES
{
  private String key = "HRThyb6235pay311";
  private BASE64Decoder decoder = new BASE64Decoder();
  private BASE64Encoder encoder = new BASE64Encoder();
  //生产加密值
  private static final byte[] enCodeFormat={-25, -60, -110, 47, -28, 46, -28, -55, -8, 2, -111, 4, -2, 57, 22, 79};
  //测试加密值
  //private static final byte[] enCodeFormat={-40, -34, 118, -31, -24, -35, 59, -98, 36, -58, -90, -86, -29, -12, -105, 37};
  public static byte[] encrypt(byte[] byteContent, byte[] password)
    throws Exception
  {
	//实例化一个用AES加密算法的密钥生  
    SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, key); 
    byte[] result = cipher.doFinal(byteContent);
    return result;
  }

  public static byte[] decrypt(byte[] content, byte[] password)
    throws Exception
  {
    SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(2, key);
    byte[] result = cipher.doFinal(content);
    return result;
  }

  private String byte2hex(byte[] b) {
    return this.encoder.encode(b);
  }

  private byte[] hex2byte(String hex) throws IOException
  {
    return this.decoder.decodeBuffer(hex);
  }

  public void setKey(String key)
  {
    this.key = key;
  }
  
  public final String encrypt(String data, String charsetName)
  {
    try
    {
      return byte2hex(encrypt(data.getBytes(), this.key.getBytes()));
    }
    catch (Exception e)
    {
      return null;
    }
  }
  
  /**
   * @deprecated
   */
  public final String decrypt(String data)
  {
    try
    {
      return new String(decrypt(hex2byte(data), this.key.getBytes()));
    }
    catch (Exception e)
    {
      return null;
    }
  }

  public final String decrypt(String data, String charsetName)
  {
    try
    {
      return new String(decrypt(hex2byte(data), this.key.getBytes()), charsetName);
    }
    catch (Exception e)
    {
      return null;
    }
  }
  
  
  public static byte[] parseHexStr2Byte(String hexStr) {   
      if (hexStr.length() < 1)   
          return null;   
      byte[] result = new byte[hexStr.length()/2];   
      for (int i = 0;i< hexStr.length()/2; i++) {   
          int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);   
          int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);   
          result[i] = (byte) (high * 16 + low);   
      }   
      return result;   
  }
}