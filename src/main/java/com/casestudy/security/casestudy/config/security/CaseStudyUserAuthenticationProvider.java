package com.casestudy.security.casestudy.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static Logger logger = LoggerFactory.getLogger(CaseStudyUserAuthenticationProvider.class);

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
			CharSequence credentials = (CharSequence) authentication.getCredentials();
			logger.debug("Found case study user auth token and the credential is {}", credentials);
			if (encoder().matches((CharSequence) credentials, loadUserByUsername.getPassword()))
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
