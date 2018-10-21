<%@ page errorPage="/jsp/common/err.jsp" %><%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%><%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/tld/struts-layout.tld" prefix="layout" %><%@page import="org.quickbundle.config.RmConfig"%><%@page import="org.quickbundle.tools.helper.RmLogHelper"%><%@page import="org.quickbundle.tools.helper.RmStringHelper"%><%@page import="org.quickbundle.tools.helper.RmJspHelper"%><%@page import="org.quickbundle.project.IGlobalConstants"%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"></script>
<script type="text/javascript"> 
var dir_base = "<%=request.getContextPath()%>";
var windowOpenReturnValue = null;
var systemDebugMode = <%=RmConfig.getSingleton().isSystemDebugMode() ? "true" : "false"%>;
var resetListJspQueryInputValue = true;
var hiddenQueryDivValue = true;
doInitForm = function(e){
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

var isChrome = !!window.chrome && !!window.chrome.webstore;
ChromePopup = function (url, arg, feature) {
    var opFeature = feature.split(";");
    var featuresArray = new Array();
    for (var i = 0; i < opFeature.length - 1; i++) {
        var f = opFeature[i].split(":");
        if(f && f[1]) {
            featuresArray[f[0].toString().trim().toLowerCase()] = f[1].toString().trim();
        }
    }

    var h = "600px", w = "1000px", l = "100px", 
    t = "100px", r = "no", c = "yes", s = "no";
    if (featuresArray["dialogheight"]) h = featuresArray["dialogheight"];
    if (featuresArray["dialogwidth"]) w = featuresArray["dialogwidth"];
    if (featuresArray["dialogleft"]) l = featuresArray["dialogleft"];
    if (featuresArray["dialogtop"]) t = featuresArray["dialogtop"];
    if (featuresArray["resizable"]) r = featuresArray["resizable"];
    if (featuresArray["center"]) c = featuresArray["center"];
    if (featuresArray["status"]) s = featuresArray["status"];
    var modelFeature = "height = " + h + ",width = " + w + ",left=" + l + ",top=" + t + ",model=yes,alwaysRaised=yes,directories=no,titlebar=no,toolbar=no, location=no,status=no,menubar=no" + ",resizable= " + r + ",celter=" + c + ",status=" + s;
    var model = window.open(url, "", modelFeature, null);
    model.dialogArguments = arg;
    //check the new attribute to refresh parent window or not
    if (window.showModalDialog.refreshParent) {
        reloadPage(model);
    }
    return model;
}

function reloadPage(returnValue) {
    var timer = setInterval(function () {
        if (returnValue.closed) {
            clearInterval(timer);
            this.window.location.reload();
        }
    }, 1000);
}

function RefreshParent(isRefresh) {
    if (isChrome) {
        window.showModalDialog.refreshParent = isRefresh;
    }
}
$(document).ready(function () {
    if (isChrome) {
        window.showModalDialog = ChromePopup;
    }
});
<%
{ //system message
	HttpSession session2 = RmJspHelper.getSession(request, response, false);
	if(session2 != null) {
		Object systemMessage = session2.getAttribute(IGlobalConstants.SystemPara.system_message.name());
		if(systemMessage != null) {
			out.print("alert(\"" + RmStringHelper.replaceStringToScript(systemMessage) + "\");");
			session2.removeAttribute(IGlobalConstants.SystemPara.system_message.name());
		}
	}
}
%></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/js/jquery/jquery-ui-1.11.2.custom.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/rm-third.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/rm-tools.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/rm-insert.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/rm-behavior.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/rm-validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-ui-1.11.2.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/rm-project.js"></script>