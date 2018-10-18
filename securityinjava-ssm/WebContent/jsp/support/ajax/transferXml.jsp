<%@page import="org.quickbundle.tools.helper.RmXmlConverter"%>
<%@page import="org.quickbundle.tools.helper.RmXmlHelper"%>
<%@page import="org.quickbundle.project.IGlobalConstants"%>
<%@ page contentType="text/xml;charset=UTF-8" language="java" %><%try {
	Object obj = request.getAttribute(IGlobalConstants.REQUEST_OUTPUT_OBJECT);
	
	String xmlStr = RmXmlConverter.getDocByObj(obj).asXML();
	//System.out.println(xmlStr);
	out.print(xmlStr);
} catch(Exception e) {
	e.printStackTrace();
}%>
