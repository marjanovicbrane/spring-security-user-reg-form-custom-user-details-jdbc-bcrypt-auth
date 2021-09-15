package com.brane.security.user.reg.form.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.brane.security.user.reg.form.entity.Role;

//THIS IS DAO LAYER
@Repository
public class RoleDaoImpl implements RoleDao {

	//private field sessionFactory, so we can do dependency injection on this field
	//to get data from database.This sessionFactory object we created in xml config file.
	@Autowired
	private SessionFactory sessionFactory;
		

	
	//We are going to use this method to find a role for some user.
	@Override
	public Role findRoleByName(String theRoleName) {

		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		//now retrieve/read from database using name
		//WE ARE USING HERE HIBERNATE API with HQL
		//first we need to define parameter roleName
		Query<Role> theQuery = currentSession.createQuery("from Role where name=:roleName", Role.class);
		
		//and then we need to set the parameter to role name, theRoleName
		theQuery.setParameter("roleName", theRoleName);
		
		//at the beginning object User have null value
		Role theRole = null;
		
		try {
			
			//We have now single object user, because we are using method getSingleResult().
			//It is designed to retrieve single result when there is truly a single result.
			theRole = theQuery.getSingleResult();
			
		} catch (Exception e) {
			
			theRole = null;
		}
		
		return theRole;
	}
}
