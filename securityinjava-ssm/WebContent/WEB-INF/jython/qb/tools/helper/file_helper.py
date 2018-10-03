#coding=utf-8
import re
import os
import time

def list_file(files, date_from, date_to):
    fs = [];
    for file in files:
        file = file.strip()
        if len(file) == 0:
            continue
        if(file.find('*') > -1):
            match_arg = re.match(r'^(?P<parent>.*[/\\])(?P<file>[^/\\]+?)$', file)
            if not os.path.exists(match_arg.group('parent')):
                continue
            filelist = os.listdir(match_arg.group('parent'))
            p_file = match_arg.group('file').replace('*', '.*')
            for temp_file in filelist:
                if re.match(p_file, temp_file, re.IGNORECASE):
                    fs.append(match_arg.group('parent') + temp_file)
        else:
            fs.append(file)
    to_delete = []
    for f in fs:
        #print f,"exists:", os.path.exists(f), "date_from", date_from, "os.path.getmtime(f)", os.path.getmtime(f), 'valid', ((os.path.getmtime(f)) > date_from)
        if os.path.exists(f) and (date_from < 0 or (os.path.getmtime(f)) > date_from):
            continue
        info = 'mtime=' + time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(os.path.getmtime(f)))
        if not os.path.exists(f):
            info = 'not exists'
        print 'ignore', f, ',', info
        to_delete.append(f)
    for td in to_delete:
        fs.remove(td)
    return fs