#coding=utf-8
from django.http import HttpResponse
from django.shortcuts import render_to_response
from qb.tools.helper import net_helper

def index(request):
    return render_to_response('index.html')

def spider_page(request):
    page = request.REQUEST.get('page', '')
    return HttpResponse(net_helper.get_str_from_url(page))

