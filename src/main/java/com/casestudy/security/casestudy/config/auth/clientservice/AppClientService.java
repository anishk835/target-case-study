package com.casestudy.security.casestudy.config.auth.clientservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.casestudy.security.casestudy.model.AppUser;
import com.casestudy.security.casestudy.userdao.AppUserDao;

@Service
public class AppClientService implements ClientDetailsService {

	private static Logger logger = LoggerFactory.getLogger(AppClientService.class);

	@Autowired
	@Qualifier("appuserdaoimpl")
	private AppUserDao appUserDao;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		AppUser clientUser = appUserDao.findUser(clientId);
		return new AppClient(clientUser);
	}

}
