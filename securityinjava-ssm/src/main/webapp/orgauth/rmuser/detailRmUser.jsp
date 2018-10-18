<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.tools.helper.RmStringHelper" %>
<%@ page import="org.quickbundle.orgauth.rmuser.RmUserVo" %>
<%@ page import="org.quickbundle.orgauth.rmuser.IRmUserConstants" %>
<%  //取出本条记录
	RmUserVo resultVo = null;  //定义一个临时的vo变量
	resultVo = (RmUserVo)request.getAttribute(IRmUserConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
	RmVoHelper.replaceToHtml(resultVo);  //把vo中的每个值过滤
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="org.quickbundle.project.login.IOrgauthConstants"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.project.common.RmCommonVo"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.TreeMap"%>
<%@page import="org.quickbundle.util.RmOrderCodes"%>
<%@page import="java.util.HashSet"%><html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	resetListJspQueryInputValue=false;
	var rmActionName = "RmUserAction";
	function find_onClick(){  //直接点到修改页面
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=find";
		form.submit();
	}
	function delete_onClick(){  //直接点删除单条记录
		if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
			return false;
		}
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=delete";
		form.submit();
	}  
	function goUrl(){
		form.action="<%=request.getContextPath()%>/RmUserAction.do?cmd=simpleQuery";
		form.submit();
	}
</script>
</head>
<body>
<form name="form" method="post">
<br/>
<table class="mainTable">
	<tr>
		<td align="right" width="20%">&nbsp;</td>
		<td width="35%">&nbsp;</td>
		<td align="right" width="20%">&nbsp;</td>
		<td width="25%">&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("name")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getName())%>&nbsp;</td>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("lock_status")%>：</td>
		<td><%=RmGlobalReference.get(IRmUserConstants.DICTIONARY_RM_LOCK_STATUS, resultVo.getLock_status())%></td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_id")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getLogin_id())%>&nbsp;</td>
		<td align="right"></td>
		<td>&nbsp;</td>
		<!--
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("organization_id")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getOrganization_id())%></td>
		-->
	</tr>
	<tr>
		<!--<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("employee_id")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getEmployee_id())%>&nbsp;</td>-->
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("email")%>：</td>
		<td colspan="3"><%=RmStringHelper.prt(resultVo.getEmail())%></td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("admin_type")%>：</td>
		<td><%=RmGlobalReference.get(IRmUserConstants.DICTIONARY_RM_ADMIN_TYPE, resultVo.getAdmin_type())%>&nbsp;</td>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("description")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getDescription())%>&nbsp;</td>
	</tr>
	</tr>
	<tr>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_status")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getLogin_status())%>&nbsp;</td>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("last_login_date")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getLast_login_date())%></td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("last_login_ip")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getLast_login_ip())%>&nbsp;</td>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_sum")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getLogin_sum())%></td>
	</tr>
</table>
<br>
<input type="hidden" name="id" value="<%=RmStringHelper.prt(resultVo.getId())%>">

<table align="center">
	<tr>
		<td><br>
			<input type="button" class="button_ellipse" id="button_update" value="修改" onClick="javascript:find_onClick();">
			<input type="button" class="button_ellipse" id="button_back" value="返回"  onClick="javascript:goUrl();" >
		</td>
	</tr>
</table>

<!-- 开始子表信息，带页签集成多个子表 -->
<script type="text/javascript">
var childTableTabs  =  new Array(
	new Array ('用户在线记录','<%=request.getContextPath()%>/RmUserOnlineRecordConditionAction.do?cmd=queryAll&user_id=<%=resultVo.getId()%>&RM_ORDER_STR=login_time DESC')
);
</script>
<br/><br/>
<table class="table_div_content">
	<tr>
		<td>
			<div id="childTableTabsDiv"></div>
		</td>
	</tr>
</table>
<!-- 结束子表信息 -->

</form>
</body>
</html>