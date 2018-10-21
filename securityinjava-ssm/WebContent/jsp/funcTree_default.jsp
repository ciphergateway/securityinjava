<%@ page contentType="text/html; charset=UTF-8" language="java" %>
    node_generated_module = insFld(foldersTree, gFld ("&nbsp;生成的模块", "", "ftv2folderopen.gif", "ftv2folderclosed.gif"));
    //start generated modules
    
    /* 消息 */
    rmCodeGenerationMessage_maintenance = insDoc(node_generated_module, gLnk("0","&nbsp;消息", "<%=request.getContextPath()%>/message", "ftv2link.gif"));

<%@ include file="/jsp/funcTree_systemModule.jsp" %>