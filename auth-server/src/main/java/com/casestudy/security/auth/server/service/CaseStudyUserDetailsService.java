package com.casestudy.security.auth.server.service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.casestudy.db.repository.UserRepository;

@Service
@Transactional
public class CaseStudyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.casestudy.db.entity.CaseStudyUser caseStudyUser = userRepository.findByEmail(email);
		if (caseStudyUser == null) {
			throw new UsernameNotFoundException("User name is not found");
		}
		String role = caseStudyUser.getRole();
		return new User(caseStudyUser.getEmail(), caseStudyUser.getPassword(), false, false, false, false,
				getAuthorities(role));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String... roles) {
		return Stream.of(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

}
