package com.brane.security.user.reg.form.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;


//CUSTOM VALIDATOR FOR PASSWORD,TO MAKE SURE THAT THE PASSWORD AND MATCHING PASSWORD FIELDS MATCH UP.

//This is helper class FieldMatchValidator, that contains business rules/validation logic.
//This class implements ConstraintValidator interface.
//FieldMatch is our annotation and Object is type of data to validate against.
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	
	//fields
	private String firstFieldName;
    private String secondFieldName;
    private String message;

    
    //Now we have all 3 parameteres from the FieldMatch annotation
    //password,matching password and error message
    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
    	
	    	firstFieldName = constraintAnnotation.first();
	    	secondFieldName = constraintAnnotation.second();
	    	message = constraintAnnotation.message();
    }

    
    
    //Spring MVC will call this method at runtime to check is this data valid.
  	//If is string valid it will return us true value, if is not it will return false value.
  	//This method have 2 parameters, first is Object value, 
    //because we will apply this annotation @FieldMatch on the class CrmUser and
  	//second is ConstraintValidatorContext, that is helper class for additional error messages.
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
    	
        boolean valid = true;
        
        try
        {
        	//BeanWrapper wraps a bean to perform actions on that bean, like retrieving properties.
            final Object firstObj = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);//password
            final Object secondObj = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);//matching password

            //both equals null, or first not equal null and first equals second
            valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        
        catch (final Exception ignore)
        {
            //we can ignore
        }

        //if is not true
        if (!valid){
        	//ConstraintValidatorContext, that is helper class for additional error messages.
            context.buildConstraintViolationWithTemplate(message)//error message
                    .addPropertyNode(firstFieldName)//password
                    .addConstraintViolation()//adds the new ConstraintViolation to be generated if the constraint validator marks the value as invalid.
                    .disableDefaultConstraintViolation();//disables the default ConstraintViolation
        }
        
        return valid;
    }
	
}