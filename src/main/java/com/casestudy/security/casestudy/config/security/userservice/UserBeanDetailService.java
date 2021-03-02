package com.casestudy.security.casestudy.config.security.userservice;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.casestudy.security.casestudy.model.AppUser;
import com.casestudy.security.casestudy.model.UserBean;
import com.casestudy.security.casestudy.model.UserDetail;
import com.casestudy.security.casestudy.userdao.AppRoleDao;
import com.casestudy.security.casestudy.userdao.AppUserDao;
import com.casestudy.security.casestudy.userdao.UserDao;

@Service
public class UserBeanDetailService implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(UserBeanDetailService.class);

	@Autowired
	@Qualifier("userdaoimpl")
	private UserDao userDao;

	@Autowired
	@Qualifier("appuserdaoimpl")
	private AppUserDao appUserDao;

	@Autowired
	private AppRoleDao appRoleDao;

	public UserDetails loadUser(String username) throws UsernameNotFoundException {
		UserDetail userDetail = null;
		try {
			UserBean userBean = userDao.getUserBeanDetails(username);
			userDetail = new UserDetail(userBean);
			logger.debug("User details from db is - {}", userDetail.toString());
		} catch (Exception e) {
			logger.error("Exception occured while getting user details - {}", e.getCause());
		}
		return userDetail;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		AppUser appUser = appUserDao.findUser(userName);
		if (appUser == null) {
			logger.info("User is not found! : {}", userName);
			throw new UsernameNotFoundException("User was not found in the database");
		}
		logger.info("User is found : {}", userName);
		List<String> roleNames = this.appRoleDao.getRoleNames(appUser.getUserId());
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if (roleNames != null) {
			for (String role : roleNames) {
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantList.add(authority);
			}
		}
		return new AppUserDetails(new User(appUser.getUserName(), appUser.getEncrytedPassword(), grantList));
	}

}
