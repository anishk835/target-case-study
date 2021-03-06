package com.casestudy.security.casestudy.userdao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.casestudy.security.casestudy.model.UserRole;

@Repository
@Transactional
public class AppRoleDaoImpl implements AppRoleDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<String> getRoleNames(Long userId) {
		String sql = "Select ur.appRole.roleName from " + UserRole.class.getName() + " ur "
				+ " where ur.appUser.userId = :userId ";
		Query query = this.entityManager.createQuery(sql, String.class);
		query.setParameter("userId", userId);
		return query.getResultList();
	}

}
