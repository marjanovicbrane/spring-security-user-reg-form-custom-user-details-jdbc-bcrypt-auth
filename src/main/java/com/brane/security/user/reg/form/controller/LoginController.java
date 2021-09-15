package com.brane.security.user.reg.form.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	
	//our custom login page
	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage() {

		return "fancy-login";
	}
	
}









