package com.brane.security.user.reg.form.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

//WE WILL CREATE OUR CUSTOM VALIDATION RULE FOR EMAIL.
//WE WILL CREATE CUSTOM JAVA ANNOTATION @ValidEmail.


//This is helper class EmailValidator, that contains business rules/validation logic.
@Constraint(validatedBy = EmailValidator.class)

//We can apply our annotation to:
//1.ElementType.TYPE->class,interface or enum, for example
//@YourAnnotation
//public class SomeClass {..}

//2.ElementType.FIELD->fields
//3.ElementType.ANNOTATION_TYPE->on other annotations, for example
//@YourAnnotation
//public @interface AnotherAnnotation {..}
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })

//Keep this annotation in the compiled java bytecode, so we can use it during RUNTIME.
@Retention(RetentionPolicy.RUNTIME)

//@Documented is a meta-annotation. You apply @Documented when defining an annotation, 
//to ensure that classes using your annotation show this in their generated JavaDoc.
@Documented
public @interface ValidEmail {
	
	//This annotation it's gonna have 1 parameter message
	String message() default "Invalid email";

	//we are not going to use groups
	Class<?>[] groups() default {};

	//we are not going to use payload
	Class<? extends Payload>[] payload() default {};
}
