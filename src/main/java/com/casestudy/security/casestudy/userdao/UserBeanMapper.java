package com.casestudy.security.casestudy.userdao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;

import com.casestudy.security.casestudy.model.UserBean;

public class UserBeanMapper implements RowMapper<UserBean> {

	@Override
	public UserBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new UserBean(rs.getString("USERNAME"), rs.getString("PASSWORD"), new ArrayList<GrantedAuthority>());
	}

}
