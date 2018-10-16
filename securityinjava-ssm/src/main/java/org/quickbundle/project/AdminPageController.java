package org.quickbundle.project;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * list					 /message
 * insert page		GET	 /message/insert
 * 
 * @author 白小勇
 */
@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/admin")
public class AdminPageController {

	/**
	 * Buffer列表页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "buffer/list")
	public String bufferList(HttpServletRequest request) {
		return "admin/buffer/listBuffer";
	}
	
	/**
	 * 刷新Buffer
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "buffer/flush")
	public String bufferFlush(HttpServletRequest request) {
	    return "admin/buffer/doFlushBuffer";
	}
	
}
