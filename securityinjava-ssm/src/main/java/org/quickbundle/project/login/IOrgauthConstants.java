package org.quickbundle.project.login;

import org.quickbundle.config.RmConfig;
import org.quickbundle.project.IGlobalConstants;

public interface IOrgauthConstants extends IGlobalConstants {
    
    /**
     * 定义当前系统的根节点编码
     */
    public final static String FUNCTION_NODE_ROOT_TOTAL_CODE = RmConfig.getSingleton().getDefaultTreeCodeFirst();
    
    /**
     * 用户的类型，分辨用户级别
     */
    public enum UserAdminType {
    	ADMIN("3");
    	private String value;
    	UserAdminType(String value_) {
    		value = value_;
    	}
    	
		public String value() {
			return value;
		}
    }
}