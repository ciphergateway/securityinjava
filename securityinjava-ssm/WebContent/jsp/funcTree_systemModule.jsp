﻿﻿<%@ page contentType="text/html; charset=UTF-8" language="java" %>

node_org = insFld(foldersTree, gFld ("&nbsp;通用组织结构", "", "ftv2folderopen.gif", "ftv2folderclosed.gif"));
    
    /* 用户生成在功能树中 */
    rmCodeGenerationRmUser_maintenance = insDoc(node_org, gLnk("0","&nbsp;用户", "<%=request.getContextPath()%>/RmUserAction.do?cmd=queryAll", "ftv2link.gif"));

    /* 用户在线记录生成在功能树中 */
    //rmCodeGenerationRmUserOnlineRecord_maintenance = insDoc(node_org, gLnk("0","&nbsp;用户在线记录", "<%=request.getContextPath()%>/RmUserOnlineRecordAction.do?cmd=queryAll", "ftv2link.gif"));
    node_user_online = insDoc(node_org, gLnk("0","&nbsp;在线用户", "<%=request.getContextPath()%>/RmUserAction.do?cmd=queryOnlineUser", "ftv2link.gif"));


//quartz begin
node_scheduler_folder = insFld(foldersTree, gFld ("&nbsp;调度管理", "", "ftv2folderopen.gif", "ftv2folderclosed.gif"));
	rmCodeGenerationJobDetail_maintenance = insDoc(node_scheduler_folder, gLnk("0","&nbsp;作业定义", "<%=request.getContextPath()%>/JobDetailAction.do?cmd=queryAll", "ftv2link.gif"));
	rmCodeGenerationJobTrigger_maintenance = insDoc(node_scheduler_folder, gLnk("0","&nbsp;作业部署", "<%=request.getContextPath()%>/JobTriggerAction.do?cmd=queryAll", "ftv2link.gif"));
	rmCodeGenerationJobExecuting_maintenance = insDoc(node_scheduler_folder, gLnk("0","&nbsp;执行中作业", "<%=request.getContextPath()%>/JobExecutingAction.do?cmd=queryAll", "ftv2link.gif"));
	rmCodeGenerationRmSchedulerEvent_maintenance = insDoc(node_scheduler_folder, gLnk("0","&nbsp;调度事件", "<%=request.getContextPath()%>/RmSchedulerEventAction.do?cmd=queryAll", "ftv2link.gif"));
//quartz end

node_monitor_log = insFld(foldersTree, gFld ("&nbsp;监控日志", "", "ftv2folderopen.gif", "ftv2folderclosed.gif"));
	node_buffer = insDoc(node_monitor_log, gLnk("0","&nbsp;缓冲器监控", "<%=request.getContextPath()%>/admin/buffer/list", "ftv2link.gif"));
	node_javamelody = insDoc(node_monitor_log, gLnk("0","&nbsp;系统监控", "<%=request.getContextPath()%>/admin/monitoring", "ftv2link.gif"));
	node_proxool = insDoc(node_monitor_log, gLnk("0","&nbsp;连接池监控", "<%=request.getContextPath()%>/admin/proxool", "ftv2link.gif"));
	//org_log begin
	rmCodeGenerationRmLogType_maintenance = insDoc(node_monitor_log, gLnk("0","&nbsp;日志类型", "<%=request.getContextPath()%>/RmLogTypeAction.do?cmd=queryAll", "ftv2link.gif"));
	rmCodeGenerationRmLog_maintenance = insDoc(node_monitor_log, gLnk("0","&nbsp;日志", "<%=request.getContextPath()%>/RmLogAction.do?cmd=queryAll&RM_ORDER_STR=ACTION_DATE DESC", "ftv2link.gif"));
	//org_log end

node_extendFunction = insFld(foldersTree, gFld ("&nbsp;扩展功能", "", "ftv2folderopen.gif", "ftv2folderclosed.gif"));
	rmCodeGenerationRmCodeType_maintenance = insDoc(node_extendFunction, gLnk("0","&nbsp;编码表", "<%=request.getContextPath()%>/RmCodeTypeAction.do?cmd=queryAll", "ftv2link.gif"));	
	rmCodeGenerationRmAffix_maintenance = insDoc(node_extendFunction, gLnk("0","&nbsp;附件", "<%=request.getContextPath()%>/RmAffixAction.do?cmd=queryAll", "ftv2link.gif"));
