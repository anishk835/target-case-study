package com.casestudy.security.casestudy.model;

import org.springframework.security.core.userdetails.User;

public class UserDetail extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4323367219536904376L;

	public UserDetail(UserBean usr) {
		super(usr.getUserName(), usr.getPassword(), usr.getAuthorities());
	}

}
