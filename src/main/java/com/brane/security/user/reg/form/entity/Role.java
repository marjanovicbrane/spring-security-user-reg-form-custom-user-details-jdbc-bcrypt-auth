package com.brane.security.user.reg.form.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//mapping this entity class to table employee from database
@Entity
@Table(name = "role")
public class Role {

	@Id//primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)//auto increment
	@Column(name = "id")//mapping column from db
	private Long id;

	@Column(name = "name")
	private String name;
	
	
	//default constructor
	public Role() {
	}

	
	//constructor with this 1 field
	public Role(String name) {
		this.name = name;
	}

	
	//getter and setter methods
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	//adding to string method for debugging
	@Override
	public String toString() {
		return "Role{" + "id=" + id + ", name='" + name + '\'' + '}';
	}
}
