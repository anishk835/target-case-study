package com.casestudy.security.casestudy.controllerservice;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.casestudy.security.casestudy.Utils;

@Component
public class UserRestControllerService {

	public String getUserInfo(Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		return Utils.toString(loginedUser);
	}

}
