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
	
	
	//If some user which is not authorized trys to access some additional page such is /systems or
	// /leaders, he will get this access denied page.
	@GetMapping("/access-denied")
	public String showAccessDenied() {
			
		return "access-denied";
			
	}
}









