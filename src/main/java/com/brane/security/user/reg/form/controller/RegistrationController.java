package com.brane.security.user.reg.form.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brane.security.user.reg.form.entity.User;
import com.brane.security.user.reg.form.service.UserService;
import com.brane.security.user.reg.form.user.CrmUser;

//THIS IS CONTROLLER LAYER
@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	//we need to inject UserService object, so we can do dependency injection,
	//because we want to delegate calls from controller layer to service layer.
    @Autowired
    private UserService userService;
	
    //logger just for debugging
    private Logger logger = Logger.getLogger(getClass().getName());
    
    
    
    //We have a problem here, for example, if we try to enter for the username Brane and
    //for the password all white spaces, this will work and we will process the form, which is wrong.
    //1.@InitBinder pre-process all web requests coming into our Controller.
  	//2.This method removes all whitespaces, from the left and from the right side.
  	//3.If string only have white space, trim it to null.
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		//This object removes whitespace from the left and from the right side.
		//true value means trim string to null if is all whitespace.
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		//And we need to register this as a custom editor.
		//For every string class, apply StringTrimmerEditor.
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	
	//method for showing the form, with parameter Model
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		
		//add CrmUser object (object for form registration) to the model
		theModel.addAttribute("crmUser", new CrmUser());
		
		return "registration-form";
	}
	
	
	
	//controller method for processing the form for registration-form.jsp
	//We are going to validate the CrmUser object here with annotation @Valid 
	//and we are using BindingResult object to store results of validation into this object.
	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
				@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, 
				BindingResult theBindingResult, 
				Model theModel) {
		
		//GET USERNAME WHICH WE ENTERED
		String userName = theCrmUser.getUserName();
		
		//Just for debugging, we are going to print out this information.
		logger.info("Processing registration form for: " + userName);
		
		
		//form validation
		//We are using now BindingResult object to see if we had errors, if we had 
		//return us to the registration-form again.
		//For validation rules we have only 1 rule and that is:username or password can't have null value.
		//If we had error (null value), we want to create a new CrmUser object 
		//and to add that object to the model attribute.
		//We also want to create one more model attribute registrationError for showing the error
		//message "User name/password can not be empty." if username or password have null value.
		 if (theBindingResult.hasErrors()){
			 
			 return "registration-form";
			 
	        }

		//check the database if user already exists with the same username
        User existing = userService.findByUserName(userName);
        
        //If user with this username exits in the database, return us to the registration-form again.
      	//And again create a new CrmUser object and add that object to the model attribute.
      	//We also want to create one more model attribute registrationError for showing the error
      	//message "User name already exists." if username alredy exists in the database.
        if (existing != null){
        	
        	//we want to have a new user object,if we delete this line we will have populated 
        	//old object(username,password,email,firstname,lastname...)
        	theModel.addAttribute("crmUser", new CrmUser());
        	
        	//Print out this error message from the model in our jsp page registration-form.
        	//we will call this model attribute registrationError in our registration-form.jsp to show an error message.
			theModel.addAttribute("registrationError", "User name already exists.");

			logger.warning("User name already exists.");
			
        	return "registration-form";
        }
        
        
        //If everything is ok, save theCrmUser to the database.       						
        userService.save(theCrmUser);
        
        logger.info("Successfully created user: " + userName);
        
        //now when user is successfully created, return us to the registration-confirmation page
        return "registration-confirmation";		
	}
}
