package com.ciphergateway.crypto;

import java.security.MessageDigest;

import org.quickbundle.config.RmBaseConfig;
public final class HashToken {
	private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	private static HashToken instance = new HashToken();

	public synchronized static HashToken getInstance() {
		return instance;
	}
	
	public String getShortToken(String arg0) {
		return encoder(arg0).substring(8,24);
	}
	public String getLongToken(String arg0) {
		return encoder(arg0).toString();
	}
	
	private StringBuffer encoder(String arg){
		if(arg==null){
			arg="";
		}
		MessageDigest md = null;
		try {
		    md=MessageDigest.getInstance("SHA-256");
		    md.update(arg.getBytes(RmBaseConfig.getSingleton().getDefaultEncode()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toHex(md.digest());
	}
	private StringBuffer toHex(byte[] bytes) {
		StringBuffer str = new StringBuffer(32);
		int length=bytes.length;
		for (int i = 0; i < length; i++) {
			str.append(hexDigits[(bytes[i] & 0xf0) >> 4]);
			str.append(hexDigits[bytes[i] & 0x0f]);
		}
		bytes=null;
		return str;
	}
	
	public static void main(String a[]){
		System.out.println();
	}
}
