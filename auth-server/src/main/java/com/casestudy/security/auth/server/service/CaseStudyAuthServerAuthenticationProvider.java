package com.casestudy.security.auth.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CaseStudyAuthServerAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CaseStudyUserDetailsService caseStudyUserDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String rawPassword = authentication.getCredentials().toString();
		UserDetails userDetails = caseStudyUserDetailsService.loadUserByUsername(email);
		if (!passwordEncoder.matches(userDetails.getPassword(), rawPassword)) {
			throw new BadCredentialsException("Bad credential");
		}
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
