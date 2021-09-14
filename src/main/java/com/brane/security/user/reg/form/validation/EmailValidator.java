package com.brane.security.user.reg.form.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//CUSTOM EMAIL VALIDATOR

//This is helper class EmailValidator, that contains business rules/validation logic.
//This class implements ConstraintValidator interface.
//ValidEmail is our annotation and String is type of data to validate against.
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	
	//Compiles the given regex and returns the instance of the Pattern.
	//Creates a matcher that matches the given input with the pattern.
	private Pattern pattern;
	
	//Matcher class is used to search through a text for multiple occurrences of a regular expression.
	private Matcher matcher;
	
	//Regular expression-marjanovicbrane93@gmail.com
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	
	//Spring MVC will call this method at runtime to check is this data valid.
	//If is string valid it will return us true value, if is not it will return false value.
	//This method have 2 parameters, first is String, HTML form data entered by the user and
	//second is ConstraintValidatorContext, that is helper class for additional error messages.
	@Override
	public boolean isValid(final String email, final ConstraintValidatorContext context) {
		// compile method is used to create a pattern from the regular expression passed as parameter to method.
		pattern = Pattern.compile(EMAIL_PATTERN);
		
		if (email == null) {
			return false;
		}
		//The matcher() method is used to search for the pattern in a string. 
		//It returns a Matcher object which contains information about the search that was performed.
		//Matcher method checks if pattern email match email which we entered in HTML form.
		matcher = pattern.matcher(email);
		
		//If matcher matches return true, if is not return false.
		return matcher.matches();
	}

}