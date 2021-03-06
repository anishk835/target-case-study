package com.casestudy.security.casestudy.userdao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.casestudy.security.casestudy.model.AppUser;

@Repository
@Transactional
@Qualifier("appuserdaoimpl")
public class AppUserDaoImpl implements AppUserDao {

	private static Logger logger = LoggerFactory.getLogger(AppUserDaoImpl.class);

	@Autowired
	private EntityManager entityManager;

	@Override
	public AppUser findUser(String userName) {
		AppUser appUser = null;
		try {
			String sql = "Select e from " + AppUser.class.getName() + " e " + " Where e.userName = :userName ";
			Query query = entityManager.createQuery(sql, AppUser.class);
			query.setParameter("userName", userName);
			appUser = (AppUser) query.getSingleResult();
		} catch (NoResultException e) {
			logger.error("Exception occurred while getting user from db - {}", e.getCause());
		}
		return appUser;
	}

	@Override
	public boolean isUserExists(String userName) {
		String sql = "Select e from " + AppUser.class.getName() + " e " + " Where e.userName = :userName ";
		logger.debug("Sql query to check if suer exists is {}", sql);
		Query query = entityManager.createQuery(sql, AppUser.class);
		query.setParameter("userName", userName);
		List<?> resultList = query.getResultList();
		return resultList != null ? !resultList.isEmpty() : false;
	}
}
