package com.brane.security.user.reg.form.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.brane.security.user.reg.form.entity.User;
import com.brane.security.user.reg.form.service.UserService;

//we are going to add @Component annotation so we can automatically register bean
//customAuthenticationSuccessHandler, because we need to inject that bean in class DemoSecurityConfig.
@Component
//This is a strategy used to handle a successful user authentication.
//After a user has logged in by submitting a login form, 
//the application needs to decide where they should be redirected.
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	//We are going to do dependency injection here, so we can call findByUserName method 
	//from the UserServiceImpl class.
    @Autowired
    private UserService userService;
	
    
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		System.out.println("\n\nIn customAuthenticationSuccessHandler\n\n");
		
		//Authentication interface extends interface Principal and Principal interface have method getName.
		//Principal is actually username.
		String userName = authentication.getName();
		
		System.out.println("userName=" + userName);

		//We here delegate calls from the SERVICE layer to the DAO layer.
		//We have now single object user, because in DAO layer we are using method getSingleResult().
		//It is designed to retrieve single result when there is truly a single result.
		User theUser = userService.findByUserName(userName);
		
		//now place object theUser in the session
		HttpSession session = request.getSession();
		//user is string name, theUser is object value
		session.setAttribute("user", theUser);
		
		//forward user to home page (redirect user)
		response.sendRedirect(request.getContextPath() + "/");
	}

}
