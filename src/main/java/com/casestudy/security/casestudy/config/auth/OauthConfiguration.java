package com.casestudy.security.casestudy.config.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.casestudy.security.casestudy.config.auth.clientservice.AppClientService;
import com.casestudy.security.casestudy.config.security.userservice.UserBeanDetailService;

@SuppressWarnings("deprecation")
@Configuration
public class OauthConfiguration extends AuthorizationServerConfigurerAdapter {

	private static Logger logger = LoggerFactory.getLogger(OauthConfiguration.class);

	private String clientId = "demo-security";
	private String clientSecret = "demo-secret-key";

	private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n"
			+ "MIIEpAIBAAKCAQEA0pBw1Vwz41LgV/RUCDuawxbB3LaW8+n2Qpv4HwDllrKGxbzH\n"
			+ "tpKvPr76ffDy96faFzKKVlBEy4LmnfkdxFrb4PwYciidGFRAGg5iy/RgWCyE0a2T\n"
			+ "2OWpo50MHzEgJLpZYrN06/U34K9L7Cd1fIxAOdM8hyVFU/G09stFToyLN8YvzJsD\n"
			+ "LDa24M+LEpt27FHS1nF6P+3lU2KHtiO0hN4HMdG5Qr1PFtJBXqtDGcxbybfbEkDe\n"
			+ "sjN7CMVxwmFHGkPE7XM39buHg9f0YJt0h2PQYoq8K+M3pMg7rQsbwMj4tXwEpFYz\n"
			+ "sr7v3uAeaqpo3lmmQ7vjH/nOPv8FBykpxXtFxwIDAQABAoIBACX3h3DhTDXcFrml\n"
			+ "Q5Wsca5lpxUOE0/tysqd5vJoLf2rOSPkczpXXqP4uaAF1zmSmX9/THRdytnLQ5wO\n"
			+ "dDCYlXcO3bWP3yuo3VoVZ/kQmmKrvFM2cic02g63XZ0uNXaJ514lr5pwP+ASm6T8\n"
			+ "RHHttBL5JLvVm5eUSKYLZvbP3rZygSdoT7hEsLVTZ6RYFILkcdH0XOpimGZZbGO/\n"
			+ "aE3O5xFp9meblnA9kWI1ClH/teuIxFlffsD6DNSgV59SSBDJ0Hkr8OR0oIQDnjkk\n"
			+ "+eaPc6E1+OoR99nMFOImJVDkNwSLhqrIcxMp6dB/6fN8vd4mGBq3i698U7mLPhUp\n"
			+ "XCZ9GZECgYEA6SuuXRZLYvT0dwAr+r4Vqo8IQlfCIDrg/INSJoXmnSvjgrVBgpc+\n"
			+ "4bikkxeS4F9sQ3Dm3YxhJOt4Q3ClRpXi2r0jUID4HsKA4c9JDQ/UqVtb5FZrJyQx\n"
			+ "2FNZU4Xxr2MdbUVrR9JnTAQlrqNO3DenGC1fR3xb7NfdbtGnzCbds3kCgYEA5y4j\n"
			+ "2FrB812nG53N2vW8rDPHnCZ8g+Og9jaoTkzMt7lZQeRgetn/9dOCvoA4UGLDWg8a\n"
			+ "cL+5jqnqtslG26Ta5b96g+cdYRKIZUjq31IYUqqDp18JPBK16msnn2SY2oRhE/lE\n"
			+ "30bEwrdbeY/iLoDJbSmg3JCH3ZfEVSTmvr3DMz8CgYEAqJ/JWlJ8NFVX0hOSuqPr\n"
			+ "AUytOWVD45EREixaJbfiPjn2L5mrhaihdhXVFvcOZis8HU2x9/jEbDHJ6GO3cuOE\n"
			+ "ipILzvVl+sDI+gSxHLwdHFkQjIIevdDEJ2CnkeqlDSSNrG9ulSNJRsUdBz3dEw9A\n"
			+ "TRDK/eSmihLWdYUzZ0WYQWkCgYAm2uttZruocrouexRpU9oVO1K2XQWaiTy5hTA+\n"
			+ "kMvrqTxmRcMtgsxxfc5AxH4yjJb24Qj5oE/IuahYdnXxXsDn0on9929JrHq+Q0Yu\n"
			+ "qLar1jrwe5mqvh5TOxVaxeam+47xc3ju6g8SBw1Z3iQO2th//3oo+CXNyb9W7C/Y\n"
			+ "KT7QswKBgQCM0G7zpKslLteoxOiiocpBzyxg3O5KBArrav0l4/G6P32ueWWrZSlp\n"
			+ "qPH4QlHH9yC7Q7h1axEPbkqNbK06I1twTDxLY8YB3PjMGxpqIjSzvSvv4aXefYbh\n"
			+ "73RMyBilR1rM1LXJKWLrCqJHkUEV+PazzcBzvxtuURS2/19pgKDwhQ==\n" + "-----END RSA PRIVATE KEY-----";

	private String publicKey = "-----BEGIN PUBLIC KEY-----\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0pBw1Vwz41LgV/RUCDua\n"
			+ "wxbB3LaW8+n2Qpv4HwDllrKGxbzHtpKvPr76ffDy96faFzKKVlBEy4LmnfkdxFrb\n"
			+ "4PwYciidGFRAGg5iy/RgWCyE0a2T2OWpo50MHzEgJLpZYrN06/U34K9L7Cd1fIxA\n"
			+ "OdM8hyVFU/G09stFToyLN8YvzJsDLDa24M+LEpt27FHS1nF6P+3lU2KHtiO0hN4H\n"
			+ "MdG5Qr1PFtJBXqtDGcxbybfbEkDesjN7CMVxwmFHGkPE7XM39buHg9f0YJt0h2PQ\n"
			+ "Yoq8K+M3pMg7rQsbwMj4tXwEpFYzsr7v3uAeaqpo3lmmQ7vjH/nOPv8FBykpxXtF\n" + "xwIDAQAB\n"
			+ "-----END PUBLIC KEY-----";

	@Autowired
	private UserBeanDetailService userBeanDetailService;
	
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AppClientService appClientService;

	@Bean
	public JwtAccessTokenConverter tokenEnhancer() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(privateKey);
		jwtAccessTokenConverter.setVerifierKey(publicKey);
		return jwtAccessTokenConverter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(tokenEnhancer());
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).tokenEnhancer(tokenEnhancer());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitALL()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret))
				.scopes("read", "write", "trust").authorizedGrantTypes("password", "refresh-token")
				.refreshTokenValiditySeconds(20000).accessTokenValiditySeconds(20000);
	}
}
