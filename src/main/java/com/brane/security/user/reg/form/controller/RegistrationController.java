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
}
