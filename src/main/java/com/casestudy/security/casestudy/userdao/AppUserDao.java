package com.casestudy.security.casestudy.userdao;

import com.casestudy.security.casestudy.model.AppUser;

public interface AppUserDao {

	AppUser findUser(String userName);
}
