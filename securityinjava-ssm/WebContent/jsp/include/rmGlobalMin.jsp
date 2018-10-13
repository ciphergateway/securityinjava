<%@ page errorPage="/jsp/common/err.jsp" %><%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %><%@page import="org.quickbundle.config.RmConfig"%><script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/rm-tools.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/rm-behavior.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css">
<script type="text/javascript">
var dir_base = "<%=request.getContextPath()%>";
var systemDebugMode = <%=RmConfig.getSingleton().isSystemDebugMode() ? "true" : "false"%>;
function doInitDocument() {
	if(!systemDebugMode) {
		jQuery(document)[0].oncontextmenu = function() {return false;};
	}
}
if(window.addEventListener) {
	window.addEventListener("load",doInitDocument,false);
} else {
	window.attachEvent("onload", doInitDocument);
}
doInitForm = function(){
	try {
		if(!(/msie/i.test(navigator.userAgent)) && document.forms.length > 0) {
			form = document.forms[0];
		}
	}catch(e){}
};
if(window.addEventListener) {
	window.addEventListener("load",doInitForm,false);
} else {
	window.attachEvent("onload", doInitForm);
}
<%
{ //system message
	HttpSession sessionSystem = RmJspHelper.getSession(request, false);
	if(sessionSystem != null) {
		Object systemMessage = sessionSystem.getAttribute(IGlobalConstants.SystemPara.system_message.name());
		if(systemMessage != null) {
			out.print("alert(\"" + RmStringHelper.replaceStringToScript(systemMessage) + "\");");
			sessionSystem.removeAttribute(IGlobalConstants.SystemPara.system_message.name());
		}
	}
}
%>
</script>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.project.IGlobalConstants"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>