package org.quickbundle.tools.helper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RmUrlHelper {
    
    
    /**
     * 对url中的<%=...%>部分解析，如<%=org.quickbundle.project.RmProjectHelper.getRmLoginId(request)%>
     * @param str
     * @param request
     * @return
     */
    public static String replaceParameter(String str, HttpServletRequest request) {
    	return replaceParameter(str, request, null);
    }
    
    /**
     * 对url中的<%=...%>部分解析，如<%=org.quickbundle.project.RmProjectHelper.getRmLoginId(request)%>
     * @param str
     * @param request
     * @return
     */
    public static String replaceParameter(String str, HttpServletRequest request, Map<String, Object> mContext) {
        if(str.indexOf("<%=") > -1) {
            try {
                System.out.println("RmUrlHelper.replaceParameter(" + str + "): Thread.currentThread()=" + Thread.currentThread());
                HttpSession session = request.getSession(false);
                if(mContext == null) {
                	mContext = new HashMap<String, Object>();
                }
                mContext.put("request", request);
                mContext.put("session", session);
                while (str.indexOf("<%=") > -1) {
                    String tempStr = str.substring(str.indexOf("<%="), str.indexOf("%>") + "%>".length());
                    String tempKey = tempStr.substring("<%=".length(), tempStr.length() - "%>".length());
                    String tempValue = String.valueOf(RmVmHelper.runJavaCode(tempKey, mContext));
                    str = str.substring(0, str.indexOf("<%=")) + tempValue + str.substring(str.indexOf("%>") + "%>".length());
                }
            } catch (Exception e) {
            	System.err.println("RmUrlHelper.replaceParameter(" + str + "):" + e.toString());
            	throw new RuntimeException(e.getMessage(), e);
            }
        }
       
        return str;
    }
}
