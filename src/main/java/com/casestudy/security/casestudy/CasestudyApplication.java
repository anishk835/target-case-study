package com.casestudy.security.casestudy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
public class CasestudyApplication {

	private static Logger logger = LoggerFactory.getLogger(CasestudyApplication.class);

	static {
		String password = "123";
		String encrytedPassword = encrytePassword(password);
		logger.info("The encrypted password is : {}", encrytedPassword);
	}

	public static String encrytePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	public static void main(String[] args) {
		SpringApplication.run(CasestudyApplication.class, args);
	}

}
