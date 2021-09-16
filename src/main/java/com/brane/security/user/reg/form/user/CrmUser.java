package com.brane.security.user.reg.form.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.brane.security.user.reg.form.validation.FieldMatch;
import com.brane.security.user.reg.form.validation.ValidEmail;

//ADDING VALIDATION RULES

//This is our custom annotation to make sure that the password and matching password fields match up.
//We will now make an array of @FieldMatch annotations to check if fields matching.
@FieldMatch.List({
    @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
    //@FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})

public class CrmUser {

	//Can't have null value and must have min 1 char
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String userName;

	//Can't have null value and must have min 1 char
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String password;
	
	//Can't have null value and must have min 1 char
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String matchingPassword;

	//Can't have null value and must have min 1 char
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String firstName;

	//Can't have null value and must have min 1 char
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String lastName;

	//This is our custom annotation for email validation using regulat expressions
	@ValidEmail
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String email;

	
	
	//default constructor
	public CrmUser() {

	}

	
	////getters and setters
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
