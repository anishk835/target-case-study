package com.casestudy.security.casestudy.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.casestudy.security.casestudy.config.security.userservice.UserBeanDetailService;

@Component
public class CaseStudyUserAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserBeanDetailService userBeanDetailService;

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UserDetails loadUserByUsername = userBeanDetailService.loadUserByUsername(authentication.getName());
		if (null != loadUserByUsername) {
			if (encoder().matches((CharSequence) authentication.getCredentials(), loadUserByUsername.getPassword()))
				return new UsernamePasswordAuthenticationToken(loadUserByUsername.getUsername(),
						loadUserByUsername.getPassword(), loadUserByUsername.getAuthorities());
		}
		throw new BadCredentialsException("Case study user bad credential");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

}
