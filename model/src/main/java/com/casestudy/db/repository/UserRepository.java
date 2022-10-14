package com.casestudy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.db.entity.CaseStudyUser;

@Repository
public interface UserRepository extends JpaRepository<CaseStudyUser, Long> {

	CaseStudyUser findByEmail(String email);
}
