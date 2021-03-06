package com.casestudy.security.casestudy.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.casestudy.security.casestudy.userdao.AppUserDao;

@Component
public class CaseStudyUserValidationFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(CaseStudyUserValidationFilter.class);

	@Autowired
	@Qualifier("appuserdaoimpl")
	private AppUserDao appUserDao;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Map<String, String[]> parameterMap = request.getParameterMap();
		logger.debug("Case study user servelet request parameter is {}",
				java.util.Arrays.asList(parameterMap.entrySet().toArray()).toString());
		var httpRequest = (HttpServletRequest) request;
		var httpResponse = (HttpServletResponse) response;

		var userId = httpRequest.getParameter("username");

		boolean userExists = appUserDao.isUserExists(userId);
		if (!userExists) {
			httpResponse.setStatus(CaseStudyHttpServletResponse.USER_NOT_EXISTS);
			return;
		}
		/**
		 * String requestId = httpRequest.getHeader("Request-Id");
		 * if (requestId == null || requestId.isBlank()) {
		 *     httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		 *     return;
		 * }
		**/
		chain.doFilter(request, response);
	}

	/**
	 * Needs a couple of correction.  
	 */
	abstract class CaseStudyHttpServletResponse implements HttpServletResponse {

		public static final int USER_NOT_EXISTS = 400;
	}

}
