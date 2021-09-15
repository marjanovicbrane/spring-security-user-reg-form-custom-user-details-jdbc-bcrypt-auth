package com.brane.security.user.reg.form.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.brane.security.user.reg.form.entity.User;

//THIS IS DAO LAYER
@Repository
public class UserDaoImpl implements UserDao {

	
	//private field sessionFactory, so we can do dependency injection on this field
	//to get data from database.This sessionFactory object we created in xml config file.
	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	//We are going to use this method to retreve single User object by username.
	@Override
	public User findByUserName(String theUserName) {
		
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		//now retrieve/read from database using username
		//WE ARE USING HERE HIBERNATE API with HQL
		//first we need to define parameter uName
		Query<User> theQuery = currentSession.createQuery("from User where userName=:uName", User.class);
		
		//and then we need to set the parameter to user name theUserName
		theQuery.setParameter("uName", theUserName);
		
		//at the beginning object User have null value
		User theUser = null;
		
		try {
			
			//We have now single object user, because we are using method getSingleResult().
			//It is designed to retrieve single result when there is truly a single result.
			theUser = theQuery.getSingleResult();
			
		} catch (Exception e) {
			
			theUser = null;
		}

		return theUser;
	}

	
	
	//We are going to use this method to save User object to the database.
	@Override
	public void save(User theUser) {
		
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		//create the user (save)
		currentSession.saveOrUpdate(theUser);
	}

}
