package com.casestudy.security.casestudy.userdao;

import java.util.List;

public interface AppRoleDao {

	List<String> getRoleNames(Long userId);

}