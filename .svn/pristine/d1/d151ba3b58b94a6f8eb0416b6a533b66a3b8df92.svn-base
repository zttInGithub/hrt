package com.hrt.biz.util.mpos;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;

/**
 * AES CBC PKCS7Padding 128 加解密
 *
 * @author lxj
 * 2018年9月27日
 */
public class AESpkcs7paddingUtil {
    // 算法名
    final static String KEY_ALGORITHM = "AES";
    // 加解密算法/模式/填充方式
    final static String algorithmStr = "AES/CBC/PKCS7Padding";
    private static Key key;
    private static Cipher cipher;
    static byte[] nativeiv;

    public static void init(byte[] keyBytes) {
        try {
            nativeiv = "wQ2Yi4gzYfHjBZ8O".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        // 若密钥不足16位，补足
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        Security.addProvider(new BouncyCastleProvider());
        key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            cipher = Cipher.getInstance(algorithmStr, "BC");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密方法
     *
     * @param content  加密的字符串
     * @param keyBytes 加密的秘钥
     * @param iv       偏移量
     * @return
     */
    public static byte[] encrypt(byte[] content, byte[] keyBytes, Object iv) {
        byte[] encryptedText = null;
        init(keyBytes);
        try {
            if (iv == null) {
                cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(nativeiv));
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec((byte[]) iv));
            }
            encryptedText = cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }

    /**
     * 解密方法
     *
     * @param encryptedData 解密字符串
     * @param keyBytes      解密秘钥
     * @param iv            偏移量
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] encryptedData, byte[] keyBytes, Object iv) throws Exception {
        byte[] encryptedText = null;
        init(keyBytes);
        try {
            if (iv == null) {
                cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(nativeiv));
            } else {
                cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec((byte[]) iv));
            }
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return encryptedText;
    }

    public static void main(String[] args) throws Exception {
        byte[] bs = "4cMBwJNUvsk8PGbh".getBytes("UTF-8");
        byte[] iv = "wQ2Yi4gzYfHjBZ8O".getBytes("UTF-8");
        String content = "123456";
        System.out.println("加密前的：" + content);
        System.out.println("加密密钥：" + new String(bs));
        System.out.println("偏移量：" + new String(iv));
        // 加密方法
        byte[] enc = encrypt(content.getBytes(), bs, iv);
        System.out.println("加密后的内容BASE64：" + new String(Base64.encode(enc)));
        // 解密方法
        byte[] dec = decrypt(enc, bs, iv);
        System.out.println("解密后的内容：" + new String(dec));
        byte[] ss = Base64.decode("zfHwis8VX6Y+oNot2Fln+vUvjOPecJQ5bfvfrY/5yPY=");
        String st = new String(decrypt(ss, bs, iv), "UTF-8");
        System.out.println("解密：" + st);
    }
}
