#coding=utf-8
import os
import sys
import re
import datetime
from os import listdir
from os.path import isdir, normpath
import math

default_output_encode = 'utf-8'
global_sql_dic = {}
p_id = re.compile("'?(\d{19}|10\d+)'?")
p_ids = re.compile('(\$id,?){2,}')

def is_effective_log_file(lines, date_from, date_to):
    if len(lines) < 1:
        return False
    sql_log_from = lines[0].split('|', 4)
    if len(sql_log_from) > 4 and long(sql_log_from[0]) > date_to:
        return False
    sql_log_to = lines[len(lines)-1].split('|', 5)
    if len(sql_log_to) > 4 and long(sql_log_to[0]) < date_from:
        return False
    return True

def get_rsd(data_list):
    if len(data_list) < 2:
        return 0
    avg = sum(data_list)*1.0/len(data_list)
    temp_v = 0
    for data in data_list:
        temp_v += (data-avg)**2
    if avg == 0:
        return 0
    else:
        return math.sqrt(temp_v/(len(data_list)-1))/avg
    
def get_abstract_sql(special_sql, class_name):
    strsql = p_id.sub('$id', special_sql.upper())
    strsql = p_ids.sub('$ids', strsql)
    class_strsql = class_name + '->  ' + strsql
    if not global_sql_dic.__contains__(class_strsql):
        global_sql_dic[class_strsql] = special_sql
    return class_strsql

def sort_dict(sb, max_row, dic, global_sql_dic = None, print_affix='ms'):
    sb.append( '\n<h2>按值排序:</h2>')
    s_execute_time = sorted(dic.items(), cmp= lambda x,y:cmp(x,y), key=lambda dic: sum(dic[1])*1.0/len(dic[1]), reverse = True)
    index = 0
    for m in s_execute_time:
        if index >= max_row:
            break
        s = ''
        if index < 20:
            s += '<span style="color:red">'
        if global_sql_dic == None:
            s += 'avg:' + "%.1f" %(sum(m[1])*1.0/len(m[1])) + print_affix + '    count:' + str(len(m[1])) + '    rsd:' + "%.1f" % get_rsd(m[1]) + '    ' + m[0].encode(default_output_encode)
        else:
            s +=  'avg:' + "%.1f" %(sum(m[1])*1.0/len(m[1])) + print_affix + '    count:' + str(len(m[1])) + '    rsd:' + "%.1f" % get_rsd(m[1]) + '    ' + global_sql_dic[m[0]].encode(default_output_encode)
        if index < 20:
            s += '</span>'
        sb.append(s)
        index += 1
    s_count = sorted(dic.items(), cmp= lambda x,y:cmp(x,y), key=lambda dic: len(dic[1]), reverse = True)
    sb.append( '\n<h2>按出现频率排序:</h2>')
    index = 0
    for m in s_count:
        if index >= max_row:
            break
        s = ''
        if index < 20:
            s += '<span style="color:red">'
        if global_sql_dic == None:
            s += 'avg:' + "%.1f" %(sum(m[1])*1.0/len(m[1])) + print_affix + '    count:' + str(len(m[1])) + '    rsd:' + "%.1f" % get_rsd(m[1]) + '    ' + m[0].encode(default_output_encode)
        else:
            s += 'avg:' + "%.1f" %(sum(m[1])*1.0/len(m[1])) + print_affix + '    count:' + str(len(m[1])) + '    rsd:' + "%.1f" % get_rsd(m[1]) + '    ' + global_sql_dic[m[0]].encode(default_output_encode)
        if index < 20:
            s += '</span>'
        sb.append(s)
        index += 1
        
def report(sys_argv, max_row = 1000):
    sb = []
    begin_time = datetime.datetime.now()
    sb.append( begin_time.isoformat().replace('T', ' ') + ' begin')
    #init argv
    b_method = False
    b_sql = False
    b_request1 = False
    b_request2 = False
    for arg in sys_argv[1:]:
        if arg == '-mmethod':
            b_method = True
        elif arg == '-msql':
            b_sql = True
        elif arg == '-mrequest1':
            b_request1 = True
        elif arg == '-mrequest2':
            b_request2 = True
        elif arg.startswith('-e'):
            default_output_encode = arg[2:]
        else:
            continue
        sb.append( 'option -> ' + arg)
    #get filelist
    method_log_dic = {}
    sql_log_dic = {}
    request_log_dic_execute_time = {}
    request_log_dic_sql_count = {}
    for arg in sys_argv[1:]:
        if arg.startswith('-e') or arg.startswith('-m'):
            continue
        fs = [];
        if(arg.find('*') > -1):
            match_arg = re.match(r'^(?P<parent>.*[/\\])(?P<file>[^/\\]+?)$', arg)
            if not os.path.exists(match_arg.group('parent')):
                continue
            filelist = listdir(match_arg.group('parent'))
            p_file = match_arg.group('file').replace('*', '.*')
            for temp_file in filelist:
                if re.match(p_file, temp_file, re.IGNORECASE):
                    fs.append(match_arg.group('parent') + temp_file)
        else:
            fs.append(arg)
        sb.append( fs)
        for f in fs:
            analys_info =  datetime.datetime.now().isoformat().replace('T', ' ') + ' analysing ' + f + '......'
            print analys_info
            sb.append(analys_info)
            s = file(f).read().decode(default_output_encode)
            #analyse str
            if b_request1 or b_request2:
                ps = '''(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2},\d{3}) INFO \[rmrequest\] - \(executeTime:([\d]+?)ms, sqlCount:([\d]+?)\)([^ ]*) (.*)'''
                p_request_log = re.compile(ps)
                request_logs = p_request_log.findall(s)
                for request_log in request_logs:
                    if request_log_dic_execute_time.__contains__(request_log[3]):
                        t_sql = request_log_dic_execute_time[request_log[3]]
                        t_sql.append(int(request_log[1]))
                        request_log_dic_execute_time[request_log[3]] = t_sql
                    else:
                        request_log_dic_execute_time[request_log[3]] = [int(request_log[1])]
                    if request_log_dic_sql_count.__contains__(request_log[3]):
                        t_sql = request_log_dic_sql_count[request_log[3]]
                        t_sql.append(int(request_log[2]))
                        request_log_dic_sql_count[request_log[3]] = t_sql
                    else:
                        request_log_dic_sql_count[request_log[3]] = [int(request_log[2])]
            if b_method:
                ps = '''(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2},\d{3}) DEBUG \[rmmethod\] - (\d+?) ms, ([\w\d_]+?).([\w\d_]+?)->package ([\w\d_.]+?) afterMethod'''
                p_log = re.compile(ps)
                logs = p_log.findall(s)
                for log in logs:
                    if method_log_dic.__contains__(log[2] + '.' + log[3]):
                        t = method_log_dic[log[2] + '.' + log[3]]
                        t.append(int(log[1]))
                        method_log_dic[log[2] + '.' + log[3] ] = t
                    else:
                        method_log_dic[log[2] + '.' + log[3] ] = [int(log[1])]
            if b_sql:
                ps = '''(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2},\d{3}) INFO \[([\w\d_.]+?)\.([\w\d_]+?)\] - 
    Execute: time = (\d+?) ms,  (.*)'''
                p_sql_log = re.compile(ps)
                sql_logs = p_sql_log.findall(s)
                for sql_log in sql_logs:
                    class_strsql = get_abstract_sql(sql_log[4], sql_log[2])
                    if sql_log_dic.__contains__(class_strsql):
                        t_sql = sql_log_dic[class_strsql]
                        t_sql.append(int(sql_log[3]))
                        sql_log_dic[class_strsql] = t_sql
                    else:
                        sql_log_dic[class_strsql] = [int(sql_log[3])]
    #print sorted result
    if b_request1:
        sb.append( '\n' + datetime.datetime.now().isoformat().replace('T', ' ') + ' execute time per request statistic:')
        sb.append( '找到所有request日志，按平均执行时间和request出现频率统计：')
        sort_dict(sb, max_row, request_log_dic_execute_time)
    if b_request2:
        sb.append( '\n' + datetime.datetime.now().isoformat().replace('T', ' ') + ' sql count per request statistic:')
        sb.append( '找到所有request日志，按平均调用的sql语句数量和request频率统计：')
        sort_dict(sb, max_row, request_log_dic_sql_count, print_affix='')
    if b_method:
        sb.append( '\n' + datetime.datetime.now().isoformat().replace('T', ' ') + ' method statistic:')
        sb.append( '找到所有method日志，按平均执行时间和method出现频率统计：')
        sort_dict(sb, max_row, method_log_dic)
    if b_sql:
        sb.append( '\n' + datetime.datetime.now().isoformat().replace('T', ' ') + ' sql statistic:')
        sb.append( '找到所有sql日志，按平均执行时间和sql出现频率统计：')
        sort_dict(sb, max_row, sql_log_dic, global_sql_dic)
    end_time = datetime.datetime.now()
    sb.append( end_time.isoformat().replace('T', ' ') + ' end , cost:' + str(end_time - begin_time))
    return sb

#if len(sys.argv) < 2:
#    print '''\
#This program analyse log files to the standard output.
#Any number of files can be specified.
#Usage: analyse_log.py target encode file1 file2 ......
#String '*' match any char and it must be in the file name 
#target options include(default method+sql):
#  -mmethod: analyse method execute time and count
#  -msql: analyse sql execute and count
#  -mrequest: analyse execute time and sql cose per request
#encode format could be(default utf-8):
#  -eGBK
#  -eutf-8  
#examples:
#analyse_log.py -mmethod -eGBK /a.log* e:\logs\cms*log* >analyse_result.log
#analyse_log.py /logs/*
#analyse_log.py -mrequest -eGBK /logs/cms*log*
#analyse_log.py -msql /logs/cms*log*
#analyse_log.py -eutf-8 /logs/cms*log* >analyse_result.log
#'''