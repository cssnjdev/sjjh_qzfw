package com.cwks.bizcore.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
@Controller
public class WebConfigurer {
	@Value("${developer.model}")
	private String developerModel;
	@RequestMapping(value = "/")
	public String homePage(Model model,HttpServletRequest request) {
		model.addAttribute("developerModel", developerModel);
		model.addAttribute("ctx", request.getContextPath());
		return "index";
	}
}
