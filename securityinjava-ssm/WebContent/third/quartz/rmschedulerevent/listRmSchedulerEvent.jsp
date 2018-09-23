<%@page import="org.quickbundle.tools.helper.RmDateHelper"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<%@page import="org.quickbundle.base.RmPageVo"%>
<%@ page import="org.quickbundle.third.quartz.rmschedulerevent.vo.RmSchedulerEventVo" %>
<%@ page import="org.quickbundle.third.quartz.rmschedulerevent.util.IRmSchedulerEventConstants" %>
<%  //取出List
	List<RmSchedulerEventVo> lResult = null;  //定义结果列表的List变量
	if(request.getAttribute(IRmSchedulerEventConstants.REQUEST_BEANS) != null) {  //如果request中的beans不为空
		lResult = (List)request.getAttribute(IRmSchedulerEventConstants.REQUEST_BEANS);  //赋值给resultList
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "RmSchedulerEventAction";
	var rmJspPath = "";
	function findCheckbox_onClick() {  //从多选框到修改页面
		var ids = findSelections("checkbox_template","id");  //取得多选框的选择项
		if(ids == null) {  //如果ids为空
	  		alert("请选择一条记录!")
	  		return false;
		}
		if(ids.length > 1) {  //如果ids有2条以上的纪录
	  		alert("只能选择一条记录!")
	  		return false;
		}
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=find&id=" + ids;
		form.submit();
	}
	function deleteMulti_onClick(){  //从多选框物理删除多条记录
 		var ids = findSelections("checkbox_template","id");  //取得多选框的选择项
		if(ids == null)	{  //如果ids为空
			alert("请选择记录!")
			return false;
		}
		if(!confirm("是否彻底删除该数据？")) {  //如果用户在确认对话框按"确定"
			return false;
		}
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=deleteMulti&ids=" + ids;
    	form.submit();
	}
	function simpleQuery_onClick(){  //简单的模糊查询
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=simpleQuery";
    	form.submit();
  	}
  	
	function toAdd_onClick() {  //到增加记录页面
		window.location="<%=request.getContextPath()%>/third/quartz/rmschedulerevent" + rmJspPath + "/insertRmSchedulerEvent.jsp";
	}
	function refresh_onClick() {  //刷新本页
		form.submit();
	}
	function detail_onClick(thisId) {  //实现转到详细页面
		form.id.value = thisId;  //赋值thisId给隐藏值id
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=detail";
		form.submit();
	}
	function toDoDblClick(thisId) {
		detail_onClick(thisId);
	}
</script>
</head>
<body>
<form name="form" method="post">

<div id="div_simple">
	<table class="table_query">
		<tr>
			<td width="20%">&nbsp;</td>
			<td width="35%">&nbsp;</td>
			<td width="20%">&nbsp;</td>
			<td width="25%">&nbsp;</td>
		</tr>
		<tr>
			<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("job_name")%></td>
			<td>
				<input type="text" class="text_field" name="job_name" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("job_name")%>" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("job_group")%></td>
			<td>
				<input type="text" class="text_field" name="job_group" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("job_group")%>" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("trigger_name")%></td>
			<td>
				<input type="text" class="text_field" name="trigger_name" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("trigger_name")%>" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("trigger_group")%></td>
			<td>
				<input type="text" class="text_field" name="trigger_group" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("trigger_group")%>" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("fire_instance_id")%></td>
			<td>
				<input type="text" class="text_field" name="fire_instance_id" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("fire_instance_id")%>" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("event_type")%></td>
			<td>
				<input type="text" class="text_field" name="event_type" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("event_type")%>" maxLength="25"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("cost_millis")%></td>
			<td>
				<input type="text" class="text_field_half" name="cost_millis_from" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("cost_millis")%>" value="" columnSize="" decimalDigits="0" />&nbsp;到&nbsp;<input type="text" class="text_field_half" name="cost_millis_to" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("cost_millis")%>" value="" columnSize="" decimalDigits="0" />
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("result_status")%></td>
			<td>
				<input type="text" class="text_field" name="result_status" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("result_status")%>" maxLength="1"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("create_time")%></td>
			<td>
				<input type="text" class="text_date_half" name="create_time_from" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("create_time")%>"/>&nbsp;到&nbsp;<input type="text" class="text_date_half" name="create_time_to" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("create_time")%>"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("create_ip")%></td>
			<td>
				<input type="text" class="text_field" name="create_ip" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("create_ip")%>" maxLength="25"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("uuid")%></td>
			<td>
				<input type="text" class="text_field" name="uuid" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("uuid")%>" maxLength="25"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("is_archive")%></td>
			<td>
				<input type="text" class="text_field" name="is_archive" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("is_archive")%>" maxLength="1"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("result")%></td>
			<td>
				<input type="text" class="text_field" name="result" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("result")%>" maxLength="2000"/>
			<input type="button" class="button_ellipse" id="button_ok" onclickto="javascript:simpleQuery_onClick()" value="查询" />
				<input type="reset" class="button_ellipse" id="button_reset" value="清空" />
				<input type="button" class="button_ellipse" id="button_moreCondition" onclick="javascript:changeSearch_onClick(this);" value="更多条件" />
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		</table>
</div>
<div id="div_advanced" style="display:none;">
	<table class="table_query">
		<tr>
			<td width="20%"></td>
			<td width="35%"></td>
			<td width="20%"></td>
			<td width="25%"></td>
		</tr>
		<%-- 将需要隐藏的查询条件剪切到这里 --%>
	</table>
</div>

<table class="tableHeader">
  <tr>
    <td width="1%"><img src="<%=request.getContextPath()%>/images/bg_mcontentL.gif" /></td>
    <td class="tableHeaderMiddleTd"><b><%=IRmSchedulerEventConstants.TABLE_NAME_CHINESE %>列表</b></td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
    	<%--
		<input type="button" class="button_ellipse" id="button_toAdd" value="新增" onclick="javascript:toAdd_onClick();" title="跳转到新增页面"/>
		<input type="button" class="button_ellipse" id="button_deleteMulti" value="删除" onclickto="javascript:deleteMulti_onClick();" title="删除所选的记录"/>
		<input type="button" class="button_ellipse" id="button_findCheckbox" value="修改" onclick="javascript:findCheckbox_onClick();" title="跳转到修改所选的某条记录"/>
		--%>
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onclickto="javascript:refresh_onClick();" title="刷新当前页面"/>
    </td>
    <td width="1%" align="right"><img src="<%=request.getContextPath()%>/images/bg_mcontentR.gif" /></td>
  </tr>
</table>

<layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="1%" title="<input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<bean:define id="rmDisplayName" name="rmBean" property="job_name"/>
		<input type="checkbox" name="checkbox_template" value="<%=rmValue%>" displayName="<%=rmDisplayName%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="3%"  title="序" style="text-align:center;">
	<%
		Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
		RmPageVo pageVo = (RmPageVo)pageContext.getRequest().getAttribute(IRmSchedulerEventConstants.RM_PAGE_VO);
		out.print((pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + rmOrderNumber.intValue() + 1);
	%>
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("job_name")%>' property="job_name" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="job_name"/>
		<%="<a class='aul' onclick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue)%>
		<%="</a>"%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("job_group")%>' property="job_group" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("trigger_name")%>' property="trigger_name" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("trigger_group")%>' property="trigger_group" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("fire_instance_id")%>' property="fire_instance_id" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("event_type")%>' property="event_type" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("cost_millis")%>' property="cost_millis" sortable="true">
		<bean:define id="cost_millis" name="rmBean" property="cost_millis"/>
		<%long lCost_millis = Long.parseLong(String.valueOf(cost_millis));%>
		<span title="<%=lCost_millis%>毫秒"><%=RmDateHelper.parseToTimeDesciption(lCost_millis) %></span>
	</layout:collectionItem>	
	<layout:collectionItem width="8%" title='<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("result_status")%>' property="result_status" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("create_time")%>' property="create_time" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="create_time"/>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue, 19)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("create_ip")%>' property="create_ip" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("uuid")%>' property="uuid" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("result")%>' property="result" sortable="true"/>
</layout:collection>

<%-- 下边这句是翻页, 如果去掉就不带翻页了,同时注意Action中也要调整方法 --%>
<jsp:include page="/jsp/include/page.jsp" />

<input type="hidden" name="id" value="">
<input type="hidden" name="queryCondition" value="">
</form>

<%--begin 生成页面汇总，正式部署前删除以下代码 --%>
<div id="div_funcNode" style="padding:20px 10px 10px 0px; display:none" align="right">
	</div>
<%-- end --%>

</body>
</html>
<script type="text/javascript">
<%  //表单回写
	if(request.getAttribute(IRmSchedulerEventConstants.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的表单回写bean不为空
		out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(IRmSchedulerEventConstants.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
	}
%>
</script>