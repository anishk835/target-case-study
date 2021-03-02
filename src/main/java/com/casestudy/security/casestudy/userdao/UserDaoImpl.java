package com.casestudy.security.casestudy.userdao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import com.casestudy.security.casestudy.model.UserBean;

@Repository
@Qualifier("userdaoimpl")
public class UserDaoImpl implements UserDao {

	private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserBean getUserBeanDetails(String username) {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		String userQuery = "SELECT * FROM USERS WHERE USERNAME=?";
		List<UserBean> userList = jdbcTemplate.query(userQuery, new UserBeanMapper(), username);
		if (!userList.isEmpty()) {
			logger.debug("List of users are - {}", userList.toString());
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_SYSTEMADMIN");
			authorities.add(grantedAuthority);
			userList.get(0).setAuthorities(authorities);
			return userList.get(0);
		}
		return null;
	}
}
