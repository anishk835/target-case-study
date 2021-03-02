package com.casestudy.security.casestudy.userdao;

import com.casestudy.security.casestudy.model.UserBean;

public interface UserDao {

	UserBean getUserBeanDetails(String username);

}