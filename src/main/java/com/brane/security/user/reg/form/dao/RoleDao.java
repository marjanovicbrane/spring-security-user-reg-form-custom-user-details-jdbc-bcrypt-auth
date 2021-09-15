package com.brane.security.user.reg.form.dao;

import com.brane.security.user.reg.form.entity.Role;

//We created interface with 1 methods for role DAO (Data Access Object), to access data from the database.
//We need a method to find role for some user.
public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
