package com.ciphergateway.crypto;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class RmCryptoHelper {
	
    private final static String AES = "AES"; 
	private final static String AES_MODE = "AES/GCM/NoPadding"; 
	
	/**
	 * AES加密
	 * @param src
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] aesEncrypt(byte[] src, byte[] key) throws Exception {
	    Key keyAES = new SecretKeySpec(key, AES);
	    
	    if (key.length != 32) {
	        throw new IllegalArgumentException("key.length should is 32, but is" + key.length);
	    }
	    
	    Cipher cipher = Cipher.getInstance(AES_MODE);
	    cipher.init(Cipher.ENCRYPT_MODE, keyAES);
	    byte[] iv = cipher.getIV();
	    assert iv.length == 12;
	    byte[] cipherText = cipher.doFinal(src);
	    assert cipherText.length == src.length + 16;
	    byte[] message = new byte[12 + src.length + 16];
	    System.arraycopy(iv, 0, message, 0, 12);
	    System.arraycopy(cipherText, 0, message, 12, cipherText.length);
	    return message;
	}
	
	/**
	 * AES解密
	 * @param src
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] aesDecrypt(byte[] src, byte[] key) throws Exception {
	    Key keyAES = new SecretKeySpec(key, AES);
	    
	    if (src.length < 12 + 16) {
	        throw new IllegalArgumentException("key.length should be greater than 12+16, but is" + src.length);
	    }
	    
	    Cipher cipher = Cipher.getInstance(AES_MODE);
	    GCMParameterSpec params = new GCMParameterSpec(128, src, 0, 12);
	    cipher.init(Cipher.DECRYPT_MODE, keyAES, params);
	    return cipher.doFinal(src, 12, src.length - 12);
	}

	/**
	 * 对密码单向哈希
	 * 
	 * @param password 密码
	 * @param seed 种子
	 * @return
	 */
	public static String digestPassword(String password, String seed) {
		if(seed == null) {
			seed = "";
		}
		return HashToken.getInstance().getLongToken(password + seed);
    }
	
	
	/**
	 * AES加密 + Base64编码(URL安全)
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	public static String encryptAesBase64(String value) throws Exception{
		byte[] src = RmCryptoHelper.aesEncrypt(value.getBytes(), tempUnsafeKey.getBytes());
		String base64 = Base64.getUrlEncoder().encodeToString(src);
		return base64;
	}
	
	/**
	 * Base64解码 + AES解密 
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	public static String decryptAesBase64(String value) throws Exception {
		byte[] s = Base64.getDecoder().decode(value);
		String result = new String(RmCryptoHelper.aesDecrypt(s, tempUnsafeKey.getBytes()));
		return result;
	}
	
	/**
	 * Base64编码(URL安全)
	 * @param value
	 * @return
	 */
	public static String encodeBase64(String value){
	    return Base64.getUrlEncoder().encodeToString(value.getBytes());
	}
	
	/**
	 * Base64解码
	 * @param value
	 * @return
	 */
	public static String decodeBase64(String value) {
		return new String(Base64.getDecoder().decode(value));
	}
	
   private final static String tempUnsafeKey = "abcdefghabcdefghabcdefghabcdefgh";
	
}
