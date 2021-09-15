package com.brane.security.user.reg.form.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.brane.security.user.reg.form.entity.User;

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

//We created interface with 2 methods for user DAO (Data Access Object), to access data from the database.
//We need method to find user by username and to save user to the database.
public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    //This is CrmUser object from the registration form with validation rules.
    void save(CrmUser crmUser);
}
