package com.brane.security.user.reg.form.dao;

import com.brane.security.user.reg.form.entity.User;

//We created interface with 2 methods for user DAO (Data Access Object), to access data from the database.
//We need method to find user by username and to save user to the database.
public interface UserDao {

    User findByUserName(String userName);
    
    void save(User user);
    
}
