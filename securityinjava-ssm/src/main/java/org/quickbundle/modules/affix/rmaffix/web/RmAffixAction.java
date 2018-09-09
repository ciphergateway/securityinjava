//代码生成时,文件路径: E:/platform/myProject/navinfo/code/nifl/src/main/java/org/quickbundle/modules/affix/rmaffix/web/RmAffixAction.java
//代码生成时,系统时间: 2010-07-26 01:03:42
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> nifl
 * 
 * 文件名称: org.quickbundle.modules.affix.rmaffix.web --> RmAffixAction.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-07-26 01:03:42 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.affix.rmaffix.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.web.page.RmPageVo;
import org.quickbundle.modules.affix.rmaffix.service.IRmAffixService;
import org.quickbundle.modules.affix.rmaffix.util.IRmAffixConstants;
import org.quickbundle.modules.affix.rmaffix.vo.RmAffixVo;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.project.cache.RmSqlCountCache;
import org.quickbundle.third.struts.actions.RmDispatchAction;
import org.quickbundle.tools.helper.RmJspHelper;
import org.quickbundle.tools.helper.RmPopulateHelper;
import org.quickbundle.tools.helper.RmSqlHelper;
import org.quickbundle.tools.helper.RmVoHelper;
import org.quickbundle.tools.support.statistic.RmStatisticHandler;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmAffixAction extends RmDispatchAction implements IRmAffixConstants {

    /**
     * 得到Service对象
     * 
     * @return Service对象
     */
    public IRmAffixService getService() {
        return (IRmAffixService) RmBeanFactory.getBean(SERVICE_KEY);  //得到Service对象,受事务控制
    }

    /**
     * 从页面表单获取信息注入vo，并插入单条记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RmAffixVo vo = new RmAffixVo();
        RmPopulateHelper.populate(vo, request);  //从request中注值进去vo
        RmVoHelper.markCreateStamp(request,vo);  //打创建时间,IP戳
        String id = getService().insert(vo);  //插入单条记录
        request.setAttribute(IGlobalConstants.INSERT_FORM_ID, id);  //新增记录的id放入request属性
        return mapping.findForward(FORWARD_TO_QUERY_ALL);
    }

    /**
     * 从页面的表单获取单条记录id，并删除单条记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int deleteCount = getService().delete(request.getParameter(REQUEST_ID));  //删除单条记录
        request.setAttribute(EXECUTE_ROW_COUNT, deleteCount);  //sql影响的行数放入request属性
        return mapping.findForward(FORWARD_TO_QUERY_ALL);
    }

    /**
     * 从页面的表单获取多条记录id，并删除多条记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward deleteMulti(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] id = RmJspHelper.getArrayFromRequest(request, REQUEST_IDS);  //从request获取多条记录id
        int deleteCount = 0;  //定义成功删除的记录数
        if (id != null && id.length != 0) {
            deleteCount = getService().delete(id);  //删除多条记录
        }
        request.setAttribute(EXECUTE_ROW_COUNT, deleteCount);  //sql影响的行数放入request属性
        return mapping.findForward(FORWARD_TO_QUERY_ALL);
    }

    /**
     * 从页面的表单获取单条记录id，查出这条记录的值，并跳转到修改页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        detail(mapping, form, request, response);
        return mapping.findForward(FORWARD_UPDATE_PAGE);
    }

    /**
     * 从页面表单获取信息注入vo，并修改单条记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RmAffixVo vo = new RmAffixVo();
        RmPopulateHelper.populate(vo, request);  //从request中注值进去vo
        RmVoHelper.markModifyStamp(request,vo);  //打修改时间,IP戳
        int count = getService().update(vo);  //更新单条记录
        request.setAttribute(EXECUTE_ROW_COUNT, count);  //sql影响的行数放入request属性
        return mapping.findForward(FORWARD_TO_QUERY_ALL);
    }
    
    /**
     * 批量保存，没有主键的insert，有主键的update
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward insertUpdateBatch(ActionMapping mapping, ActionForm form, final HttpServletRequest request, HttpServletResponse response) throws Exception {
    	List<RmAffixVo> lvo = RmPopulateHelper.populateAjax(RmAffixVo.class, request);
    	for(RmAffixVo vo : lvo) {
    		if(vo.getId() != null && vo.getId().trim().length() > 0) {
    			RmVoHelper.markModifyStamp(request, vo);
    		} else {
    			RmVoHelper.markCreateStamp(request, vo);
    		}
    	}
    	int[] sum_insert_update = getService().insertUpdateBatch(lvo.toArray(new RmAffixVo[0]));
    	request.setAttribute(REQUEST_OUTPUT_OBJECT, sum_insert_update);
        return mapping.findForward(FORWARD_TO_QUERY_ALL);
    }
    
    /**
     * 简单查询，分页显示，支持表单回写
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward simpleQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IRmAffixService service = getService();
        String queryCondition = getQueryCondition(request);  //从request中获得查询条件
        RmPageVo pageVo = RmJspHelper.transctPageVo(request, getCount(queryCondition));
        String orderStr = RmJspHelper.getOrderStr(request);  //得到排序信息
        List<RmAffixVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());  //按条件查询全部,带排序
        //如果采用下边这句，就是不带翻页的，同时需要删掉list页面的page.jsp相关语句
        //beans = service.queryByCondition(queryCondition, orderStr);  //查询全部,带排序
        RmJspHelper.saveOrderStr(orderStr, request);  //保存排序信息
        request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
        request.setAttribute(REQUEST_BEANS, beans);  //把结果集放入request
        request.setAttribute(REQUEST_WRITE_BACK_FORM_VALUES, RmVoHelper.getMapFromRequest((HttpServletRequest) request));  //回写表单
        return mapping.findForward(FORWARD_LIST_PAGE);
    }

    /**
     * 查询全部记录，分页显示，支持页面上触发的后台排序
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward queryAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(REQUEST_QUERY_CONDITION, "");
        simpleQuery(mapping, form, request, response);
        return mapping.findForward(FORWARD_LIST_PAGE);
    }

    /**
     * 从页面的表单获取单条记录id，并察看这条记录的详细信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RmAffixVo bean = getService().find(request.getParameter(REQUEST_ID));  //通过id获取vo
        request.setAttribute(REQUEST_BEAN, bean);  //把vo放入request
        if(RM_YES.equals(request.getParameter(REQUEST_IS_READ_ONLY))) {
            request.setAttribute(REQUEST_IS_READ_ONLY, request.getParameter(REQUEST_IS_READ_ONLY));
        }
        return mapping.findForward(FORWARD_DETAIL_PAGE);
    }
    
    /**
     * 参照信息查询，带简单查询，分页显示，支持表单回写
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward queryReference(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        simpleQuery(mapping, form, request, response);
        request.setAttribute(REQUEST_REFERENCE_INPUT_TYPE, request.getParameter(REQUEST_REFERENCE_INPUT_TYPE));  //传送输入方式,checkbox或radio
        return mapping.findForward(FORWARD_REFERENCE_PAGE);
    }
    
    /**
     * 功能: 统计
     *
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward statistic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String rowKeyField = "mime_type";  //定义行统计关键字
        String columnKeyField = "title";  //定义列统计关键字
        IRmAffixService service = getService();
        String queryCondition = getQueryCondition(request);  //从request中获得查询条件
        List<RmAffixVo> beans = service.queryByCondition(queryCondition, null);  //查询出全部结果
        RmStatisticHandler sh = new RmStatisticHandler(beans, rowKeyField, columnKeyField, "内容类型\\标题");
        request.setAttribute(REQUEST_STATISTIC_HANDLER, sh);  //把结果集放入request
        request.setAttribute(REQUEST_WRITE_BACK_FORM_VALUES, RmVoHelper.getMapFromRequest((HttpServletRequest) request));  //回写表单
        return mapping.findForward(FORWARD_STATISTIC_PAGE);
    }
    
    /**
     * 功能: 统计导出Excel
     *
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward statistic_exportExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        statistic(mapping, form, request, response);
    	return mapping.findForward(FORWARD_DOWNLOAD_STATISTIC_FILE_PAGE);
    }

    /**
     * 功能: 从request中获得查询条件
     *
     * @param request
     * @return
     */
    private String getQueryCondition(HttpServletRequest request) {
        String queryCondition = null;
        if(request.getAttribute(REQUEST_QUERY_CONDITION) != null) {  //如果request.getAttribute中有，就不取request.getParameter
            queryCondition = request.getAttribute(REQUEST_QUERY_CONDITION).toString();
        } else {
    		List<String> lQuery = new ArrayList<String>();
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".bs_keyword", request.getParameter("bs_keyword"), RmSqlHelper.TYPE_CHAR_LIKE));
    		
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".record_id", request.getParameter("record_id"), RmSqlHelper.TYPE_CHAR_LIKE));
    		
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".order_number", request.getParameter("order_number_from"), RmSqlHelper.TYPE_NUMBER_GREATER_EQUAL));
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".order_number", request.getParameter("order_number_to"), RmSqlHelper.TYPE_NUMBER_LESS_EQUAL));

    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".title", request.getParameter("title"), RmSqlHelper.TYPE_CHAR_LIKE));
    		
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".old_name", request.getParameter("old_name"), RmSqlHelper.TYPE_CHAR_LIKE));
    		
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".save_name", request.getParameter("save_name"), RmSqlHelper.TYPE_CHAR_LIKE));
    		
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".save_size", request.getParameter("save_size_from"), RmSqlHelper.TYPE_NUMBER_GREATER_EQUAL));
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".save_size", request.getParameter("save_size_to"), RmSqlHelper.TYPE_NUMBER_LESS_EQUAL));
    		
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".mime_type", request.getParameter("mime_type"), RmSqlHelper.TYPE_CHAR_EQUAL));
    		
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".encoding", request.getParameter("encoding"), RmSqlHelper.TYPE_CHAR_LIKE));
    		
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".description", request.getParameter("description"), RmSqlHelper.TYPE_CHAR_LIKE));
    		
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".author", request.getParameter("author"), RmSqlHelper.TYPE_CHAR_LIKE));
    		
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".modify_date", request.getParameter("modify_date_from"), RmSqlHelper.TYPE_DATE_GREATER_EQUAL));
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".modify_date", request.getParameter("modify_date_to"), RmSqlHelper.TYPE_DATE_LESS_EQUAL));
    		
    		queryCondition = RmSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
        }
        return queryCondition;
    }
    
    /**
     * 得到缓存中查询条件对应的count(*)记录数，如返回-1则执行查询
     * 
     * @param queryCondition
     * @return
     */
    private int getCount(String queryCondition) {
    	int count = RmSqlCountCache.getCount(TABLE_NAME, queryCondition);
    	if(count < 0) {
    		count = getService().getRecordCount(queryCondition);
    		RmSqlCountCache.putCount(TABLE_NAME, queryCondition, count);
    	}
    	return count;
    }

}