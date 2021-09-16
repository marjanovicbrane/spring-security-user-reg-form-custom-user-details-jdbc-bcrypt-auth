package com.brane.security.user.reg.form.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.brane.security.user.reg.form.service.UserService;



//THIS IS JAVA CONFIG CLASS.WE ARE USING PURE JAVA CONFIG, NO XML
@Configuration
//@EnableWebSecurity annotation is used to enable SpringSecurity in our project.
@EnableWebSecurity
//We need to extend WebSecurityConfigurerAdapter class.
//It allows configuring things that impact all of web security.
//WebSecurityConfigurerAdapter allows customization to both WebSecurity and HttpSecurity.
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {


	//We have to fetch the UserDetails (username, email, password, roles, etc..) from a “securityDataSource” 
	//with the help of an UserService, and then authenticate the provided data against the actual data.
    @Autowired
    private UserService userService;
	
    //Strategy used to handle a successful user authentication.
    //after a user has logged in by submitting a login form, the application needs to decide 
    //where they should be redirected.
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    
    
    //The AuthenticationManager delegates the authentication to the appropriate AuthenticationProvider,
    //in this case DaoAuthenticationProvider.
    //The AuthenticationProvider calls the loadUserByUsername(username) method of the UserDetailsService 
    //and gets back the UserDetails object containing all the data of the user.
    //The most important data is the password becuase it will be used to check whether the provided 
    //password is correct.If no user is found with the given user name, a UsernameNotFoundException 
    //is thrown in the method loadUserByUsername(username).
    
    //The AuthenticationProvider after receiving the UserDetails checks the passwords and 
    //authenticates the user.
    //If the passwords do not match it throws a AuthenticationException.If the authentication is 
    //successful, a UsernamePasswordAuthenticationToken is created, and the fields principal, 
    //credentials, and authenticated are set to appropriate values.
    //Here principal refers to your username, credentials refers to password and the authenticated 
    //field is set to true.This token is returned back to the AuthenticationManager.
   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
	
   
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		
			//Everyone who have role EMPLOYEE can access HOME PAGE
			.antMatchers("/").hasRole("EMPLOYEE")
			.antMatchers("/leaders/**").hasRole("MANAGER")
			.antMatchers("/systems/**").hasRole("ADMIN")
			.and()
			
			//we are using our custom login form
			.formLogin()
			
				//show our custom login form with this request mapping:/showMyLoginPage.
				//We need to create a method i Controller class with this request mapping.
				.loginPage("/showMyLoginPage")
				
				//On this URL we will send data from our form, so Spring Security can do authentication.
				//Login form need to send POST method for processing data (username and password).
				.loginProcessingUrl("/authenticateTheUser")
				
				//Strategy used to handle a successful user authentication.
			    //after a user has logged in by submitting a login form, the application needs to decide 
			    //where they should be redirected.
				.successHandler(customAuthenticationSuccessHandler)
				
				//Allow everyone to see login form.
				.permitAll()
				
			//And we are adding LOGOUT SUPPORT for Spring Security
			.and()
			
			//We need to confirm that everyone have access to the login page, when we are logout from the system.
			//Because by default it return us to the home page(login page).
			.logout().permitAll()
			.and()
			
			//we are calling method for exception handling and on that method we are callingmethod for
			//access denied page with this request mapping:/access-denied.
			//We need to create a method i Controller class with this request mapping.
			.exceptionHandling().accessDeniedPage("/access-denied");
		
	}
	

	//bcrypt bean definition
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	//authenticationProvider bean definition
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		//HERE WE NEED TO LOAD USER DETAILS USERNAME,PASSWORD,ROLES,EMAIL FROM A securityDataSource(database),
		//WITH THE HELP OF AN userService AND THEN AUTHENTICATE THE PROVIDED DATA AGAINST THE ACTUAL DATA.

		//UserDetailsService
		//Interface UserService extends interface UserDetailsService and class UserServiceImpl
		//implements interface UserService.
		//UserDetailsService is a service which is responsible for fetching the details of the user 
		//from a “securityDataSource” (database) using the loadUserByUsername(String username) methods 
		//which takes in the username as a parameter.It then returns a UserDetails object populated 
		//with the user data fetched from the securityDataSource(database).
		//The three main fields of an UserDetails object are username, password and the roles/authorities.
		//This method loadUserByUsername(String username) is automatically called here:auth.setUserDetailsService(userService);
		//Here we can also do this:auth.setUserDetailsService(userDetailsService);
		auth.setUserDetailsService(userService);//set the custom user details service
		auth.setPasswordEncoder(passwordEncoder());//set the password encoder - bcrypt
		return auth;
	}
	  
}






