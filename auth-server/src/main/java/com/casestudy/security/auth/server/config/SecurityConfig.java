package com.casestudy.security.auth.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.casestudy.security.auth.server.service.CaseStudyAuthServerAuthenticationProvider;


@EnableWebSecurity(debug = true)
public class SecurityConfig {
	
	private static final String[] PERMIT_URLS = { "/h2-console" };

	@Autowired
	private CaseStudyAuthServerAuthenticationProvider caseStudyAuthServerAuthenticationProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeRequests(authorizeRequest -> authorizeRequest.antMatchers(PERMIT_URLS).permitAll()
						.anyRequest().authenticated())
				.formLogin(Customizer.withDefaults()).authenticationProvider(caseStudyAuthServerAuthenticationProvider)
				.build();
	}
}
