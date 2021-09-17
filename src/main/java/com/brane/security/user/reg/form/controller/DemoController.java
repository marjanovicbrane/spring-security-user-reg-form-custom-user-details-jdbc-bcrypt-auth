package com.brane.security.user.reg.form.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

	
	//Request mapping for root of our application to show home page when we are logged in.
	@GetMapping("/")
	public String showHome() {
		
		return "home";
	}
	
}










