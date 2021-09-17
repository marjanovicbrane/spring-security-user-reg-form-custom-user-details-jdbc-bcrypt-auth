package com.brane.security.user.reg.form.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

	
	//Request mapping for root of our application to show home page when we are logged in.
	//THIS CAN SEE ONLY USERS WITH EMPLOYEE ROLE
	@GetMapping("/")
	public String showHome() {
		
		return "home";
	}
	
	
	//Request mapping /leaders for MENAGER ROLE
	@GetMapping("/leaders")
	public String showLeaders() {
			
		return "leaders";
	}
	
	
	//request mapping /systems for ADMIN ROLE
	@GetMapping("/systems")
	public String showSystems() {
			
		return "systems";
	}
	
}










