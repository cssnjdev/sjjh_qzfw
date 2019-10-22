package com.cwks.bizcore.comm.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * TyGoPageController
 * <p>Title: TyGoPageController.java</p>
 * <p>Description:通用跳转的URL控制</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: cssnj</p>
 * @author Cjl
 * @version 1.0
 */
@Controller
@RequestMapping("/tyGoPage")
public class TyGoPageController {
	/**
	 * <p>url: /html.action</p>
	 * <p>Description:系统统一加载html页面</p>
	 * @version 1.0
	 */
	@RequestMapping("/html")
	public String example(Model model,HttpServletRequest request){
		String path="biz/";
		String m=request.getParameter("model");
		if(request.getParameter("p") != null && !"".equals(request.getParameter("p"))){
			path= path+m+"/"+request.getParameter("p");
		}
		Enumeration<String> enumeration=request.getParameterNames();
		String pkey=null;
		while(enumeration.hasMoreElements()) {
			pkey=enumeration.nextElement();
			model.addAttribute(pkey, request.getParameter(pkey));
		}
		model.addAttribute("ctx", request.getContextPath());
		return path;
	}
}
