<%@ page contentType="text/html; charset=UTF-8" session="false" language="java"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.quickbundle.project.IGlobalConstants"%>
<%@page import="org.quickbundle.project.login.IRmLoginConstants"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.config.RmConfig"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%
    HttpSession session2 = RmJspHelper.getSession(request, response, false);
	if(!"1".equals(request.getParameter("no_redirect"))) {
		HttpSession session = RmJspHelper.getSession(request, response, false);
		if(session != null && session.getAttribute(IGlobalConstants.RM_USER_VO) != null) {
			response.sendRedirect(request.getContextPath() + "/"); //index.jsp
		}
	}

%>