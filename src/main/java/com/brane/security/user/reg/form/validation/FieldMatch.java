package com.brane.security.user.reg.form.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


//THIS IS CUSTOM ANNOTATION TOMAKE SURE THAT THE PASSWORD AND MATCHING PASSWORD FIELDS MATCH UP. 

//This is helper class FieldMatchValidator, that contains business rules/validation logic.
@Constraint(validatedBy = FieldMatchValidator.class)

//We can apply our annotation to:
//1.ElementType.TYPE->class,interface or enum, for example
//@YourAnnotation
//public class SomeClass {..}

//2.ElementType.ANNOTATION_TYPE->on other annotations, for example
//@YourAnnotation
//public @interface AnotherAnnotation {..}
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })

//Keep this annotation in the compiled java bytecode, so we can use it during RUNTIME.
@Retention(RetentionPolicy.RUNTIME)

//@Documented is a meta-annotation. You apply @Documented when defining an annotation, 
//to ensure that classes using your annotation show this in their generated JavaDoc.
@Documented
//first annotation @FieldMatch.Inside this annotation,we have 1 more annotation @List
public @interface FieldMatch {
	
	//This annotation it's gonna have 3 parameter message, first and second
	
	//Third parameter for error message.
	String message() default "";
	
	//we are not going to use groups
	Class<?>[] groups() default {};
	
	//we are not going to use payload
	Class<? extends Payload>[] payload() default {};
	
	//first parameter for password
	String first();
	
	//second parameter for matching password
    String second();
    
    
    
    //Second annotation @List, to make array of FieldMatch annotations.
    //This annotation have 1 method called value()
    
    //We can also applay this annotation to class and to another annotations
    @Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    
    //Keep this annotation in the compiled java bytecode, so we can use it during RUNTIME.
    @Retention(RetentionPolicy.RUNTIME)
    
    //@Documented is a meta-annotation. You apply @Documented when defining an annotation, 
    //to ensure that classes using your annotation show this in their generated JavaDoc.
    @Documented
    @interface List
    {
    	//This annotation it's gonna have 1 parameter value
    	FieldMatch[] value();
    }
}