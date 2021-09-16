package com.brane.security.user.reg.form.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brane.security.user.reg.form.dao.RoleDao;
import com.brane.security.user.reg.form.dao.UserDao;
import com.brane.security.user.reg.form.entity.Role;
import com.brane.security.user.reg.form.entity.User;

//THIS IS SERVICE LAYER
@Service
public class UserServiceImpl implements UserService {

	
	//need to inject userDao, so we can do dependency injection on this field
	//to delegate calls from SERVICE LAYER TO THE DAO LAYER
	@Autowired
	private UserDao userDao;

	//need to inject role dao
	@Autowired
	private RoleDao roleDao;
	
	//need to inject passwordEncoder from DemoSecurityConfig class
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	
	//We are going to use this method to retreve single User object by username.
	@Override
	//annotation for "begin transaction" and "commit transaction"
	@Transactional
	public User findByUserName(String userName) {
		
		//Check the database if the user already exists
		//DELEGATE CALLS FROM SERVICE LAYER TO DAO LAYER
		return userDao.findByUserName(userName);
	}

	
	
	//We are going to use this method to save CrmUser object to the database.
	//This is CrmUser object from the registration form with validation rules.
	//User object is entity object for mapping database table user.
	@Override
	//annotation for "begin transaction" and "commit transaction"
	@Transactional
	public void save(CrmUser crmUser) {
		
		//We creating a new object User,because we are going to put in this object all information
		//which we entered in our registration form(CrmUser object),we will do this with setter methods.
		User user = new User();
		
		//assign user details to the User object
		user.setUserName(crmUser.getUserName());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		user.setFirstName(crmUser.getFirstName());
		user.setLastName(crmUser.getLastName());
		user.setEmail(crmUser.getEmail());

		//give user default role of "employee"
		//This setter method setRoles for parameter takes Collection<Role> roles.
		//The collection he uses the most is ArrayList and LinkedHashMap.
		//we can do this also:
		//1.List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE");
		//2.List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList();
        //authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
		user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));

		 //save user in the database
		userDao.save(user);
	}

	
	
	
	//This method is from interface UserDetailsService and gets back the UserDetails object 
	//containing all the data of the user.
	//If no user is found with the given user name, a UsernameNotFoundException 
	//is thrown in the method loadUserByUsername.
	@Override
	//annotation for "begin transaction" and "commit transaction"
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		//delegate calls from service layer todao layer to find user by username
		User user = userDao.findByUserName(userName);
		
		//if user with that username don't exist, UsernameNotFoundException is thrown.
		if (user == null) {
			
			//UsernameNotFoundException  is thrown.
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		//If is not null and user with that username exist in the database,
		//use getter methods to take username, password and authorities for that user object.
		//Class User implements UserDetails interface, so we can do this.
		//Every user which we register only have one role by default ant it is EMPLOYEE role.
		//Here we want to add all roles for user, for example roles:EMPLOYEE and ADMIN.
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				   mapRolesToAuthorities(user.getRoles()));
	}

	
	
	//This methods returns Collection of roles and takes for parameter Collection<Role> roles.
	//1.List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE");
	//2.List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList();
    //authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
	}
}
