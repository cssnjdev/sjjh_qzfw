package com.cwks.bizcore.comm.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;


/**
 * PortalPageController
 * <p>Title: PortalPageController.java</p>
 * <p>Description:通用嵌入PORTAL的URL控制</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: cssnj</p>
 * @author Cjl
 * @version 1.0
 */
@Controller
public class PortalPageController {

	@Value("${biz.cssnjworks.core.portal.login.url}")
	private String portal_login_url;

	@Value("${biz.cssnjworks.core.portal.index.url}")
	private String portal_index_url;

	@RequestMapping("/portalLogin")
	public String login(Model model){
		model.addAttribute("portal_login_url", portal_login_url);
		return "portal_login";
	}

	@RequestMapping("/portalIndex")
	public String index(Model model){
		model.addAttribute("portal_index_url", portal_index_url);
		return "portal_index";
	}
}
