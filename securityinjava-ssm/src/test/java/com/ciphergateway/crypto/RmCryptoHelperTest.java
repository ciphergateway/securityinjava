package com.ciphergateway.crypto;

import static org.junit.Assert.*;

import org.junit.Test;
import org.quickbundle.project.DeepTreeVo;
import org.quickbundle.project.DeepTreeXmlHandler;

public class RmCryptoHelperTest {

	@Test
    public void hashTokenTest() {
	    String hashValue = HashToken.getInstance().getLongToken("123456");
	    assertTrue("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92".equals(hashValue));
    }
	
	   @Test
	    public void aesEncrypt() throws Exception {
	        String src = "123456";
	        String key = "abcdefghabcdefghabcdefghabcdefgh";
	        byte[] cipher = RmCryptoHelper.aesEncrypt(src.getBytes(), key.getBytes());
	        byte[] plain= RmCryptoHelper.aesDecrypt(cipher, key.getBytes());
	        System.out.println("src=" + src + ", after encrypt and edcrypt, plain=" + new String(plain));
	        assertArrayEquals(src.getBytes(), plain);
	    }
}
