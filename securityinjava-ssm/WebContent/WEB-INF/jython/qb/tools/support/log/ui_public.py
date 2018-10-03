#coding=utf-8

import os
import time
import traceback
import re

from qb.tools.helper import file_helper
from java.lang import System
from org.quickbundle.tools.helper import RmStringHelper

def get_line(line, log_file_encode):
    try:
        #print RmStringHelper.testAllEncode(line)
        line = RmStringHelper.encode2Encode(line.strip(), "iso8859-1", log_file_encode)
        #line = line.decode(log_file_encode)
    except:
        print traceback.format_exc()
    return line

def print_query_head(request, response):
    #print "request=", request.getParameterMap()
    out = response.getWriter()
    war_name = request.getContextPath()[1:]
    out.println('''<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="/''' + war_name + '''/js/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="profiler.js"></script>
<title>LOG Profiler - ''')
    out.println(request.getParameter("cmd"))
    out.println('''</title>
</head>
<style type="text/css">

ul li{margin:0;padding:0;}
.r{border-bottom:1px solid;}
.rtop{border-top:1px solid;}
.focus{color:red;}

</style>
<body>''')

def parse_log_para(request, response, log_para):
    #时间范围
    fsm = {}
    date_from = request.getParameter("date_from")
    if date_from != None and len(date_from) > 0:
        date_from = time.mktime(time.strptime(date_from, "%Y-%m-%d %H:%M:%S"))
    else:
        date_from = -1
    date_to = request.getParameter("date_to")
    if date_to != None and len(date_to) > 0:
        date_to = time.mktime(time.strptime(date_to, "%Y-%m-%d %H:%M:%S"))
    else:
        date_to = -1
    #处理log文件
    log_para = log_para.strip()
    log_groups = re.compile('^\[([\w_]+)\](((?!^\[).)*)',re.MULTILINE|re.DOTALL).findall(log_para)
    for log_group in log_groups:
        group_name = log_group[0]
        group_log = log_group[1]
        log = group_log.split(',')
        fs = file_helper.list_file(log, date_from, date_to)
        fsm[group_name] = fs
    return [fsm, date_from, date_to]

def print_query_form(request, response, log_para, buttons, ext_code=None):
    out = response.getWriter()
    #开始表单提交
    out.println('<form name="form" method="post">')
    out.println('<div>')
    out.println('''<div style="padding:5px 0px 5px;">
<span>请输入log文件所在路径（文件名支持*通配符,多个以逗号分隔）：</span><br/>
<textarea cols="60" rows="10" name="log">''')
    out.println(log_para)
    out.println('''</textarea>
</div>''')
    out.println('''<div style="padding:5px 0px 5px;">
<span>时间范围（可不输）：</span>
<input type="button" name="ms_range_1h" value="过去1小时内" onclick="javascript:setTimeRange(new Date().getTime() - 1000*60*60)">
<input type="button" name="ms_range_1d" value="过去1天内" onclick="javascript:setTimeRange(new Date().getTime() - 1000*60*60*24)">
<input type="button" name="ms_range_1w" value="过去1周内" onclick="javascript:setTimeRange(new Date().getTime() - 1000*60*60*24*7)">
<input type="button" name="ms_range_1m" value="过去1月内" onclick="javascript:setTimeRange(new Date().getTime() - 1000*60*60*24*30)">
<input type="button" name="ms_range_3m" value="过去3月内" onclick="javascript:setTimeRange(new Date().getTime() - 1000*60*60*24*90)">
<br/>''')
    default_date_from = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(time.time() - 60*60))
    default_date_to = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()) 
    out.println('''<div style="padding:3px;"><input type="text" name="date_from" value="''' + default_date_from + '''">''')
    out.println(''' 到 ''')
    out.println('''<input type="text" name="date_to" value="''' + default_date_to + '''"></div>''')
    out.println('''</div>''')
    if ext_code != None:
        out.println(ext_code)
    out.println('</div>')
    out.println('<div style="padding:15px 0px 5px;">')
    for button in buttons:
        out.println('<input type="button" value="' + button[1] + '" onclick="javascript:form.cmd.value=\'' + button[0] + '\';if(validateDateOk()){form.submit();}" />')
    out.println('</div>')
    out.println('<input type="hidden" name="cmd" value="" />')
    out.println('</form>')

def print_result_head(request, response, fs, date_from, date_to, valid_row_count, cost):
    out = response.getWriter()
    out.println('<textarea id="log" style="display:none">' + request.getParameter("log") + '</textarea>')
    out.println('<input type="hidden" id="date_from" value="' + request.getParameter("date_from") + '"/>')
    out.println('<input type="hidden" id="date_to" value="' + request.getParameter("date_to") + '"/>')
    if request.getParameter("match_user") != None:
        out.println('<input type="hidden" id="match_user" value="' + request.getParameter("match_user") + '"/>')
    if request.getParameter("match_url") != None:
        out.println('<input type="hidden" id="match_url" value="' + request.getParameter("match_url") + '"/>')
        
    out.println('<input type="button" name="button_back" value="返回" onclick="javascript:history.back();"/>')
    total_size = 0
    for f in fs:
        if os.path.exists(f):
            total_size += os.path.getsize(f)
    out.println('<div style="padding:5px;">')

    out.println(' 分析了文件：(总计：' + '%.3f'%(total_size/(1024*1024.0)) + 'M')
    out.println('&nbsp;&nbsp;&nbsp;&nbsp;命令：') 
    out.println(request.getParameter("cmd"))
    out.println(')</div>')
    out.println('<ul style="margin:-3 0 0 20;">')
    for f in fs:
        if os.path.exists(f):
            out.println('<li>' + str(f) + '</li>')
    out.println('</ul>')
    out.println('<div>时间范围：')
    if date_from > 0:
        out.println(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(date_from))) #s
    else:
        out.println('不限')
    out.println(' 到 ')
    if date_to > 0:
        out.println(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(date_to))) #s
    else:
        out.println('不限')
    out.println('</div>')
    out.println('<div>有效日志条数：' + str(valid_row_count))
    if date_from > 0 and date_to > 0 and date_to > date_from:
        out.println(' (日志产生频率：' + '%.1f'%(valid_row_count/((date_to-date_from)/60.0)) + '条/分钟)')
    out.println('</div>')
    cost_seconds = cost.seconds + cost.microseconds/1000000.0
    out.println('<div>本次分析耗时：' + '%.1f'%cost_seconds + '秒')
    if cost_seconds > 0:
        out.println(' (处理速度：' + '%.0f'%(valid_row_count/cost_seconds) + '条/秒)')
    out.println('</div>')
    out.println('</div>')
    
def print_result_content_html(request, response, sb):
    out = response.getWriter()
    #输出结果
    out.println('<div style="white-space: nowrap">')
    for s in sb:
        s = str(s)
        if len(s) > 10000:
            s = s[0:10000] + '...(' + str(len(s)) + ')'
        out.println('<div class="r">' + s + '</div>');
    out.println('</div>')
    out.println('</body></html>')