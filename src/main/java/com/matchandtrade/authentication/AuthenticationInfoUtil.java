package com.matchandtrade.authentication;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matchandtrade.config.AuthenticationProperties;
import com.matchandtrade.util.JsonUtil;

public class AuthenticationInfoUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInfoUtil.class);

	// Utility classes should not have public constructors
	private AuthenticationInfoUtil() {}
	
	public static void writeResponseBody(HttpServletRequest request, HttpServletResponse response) {
		Object authorizationHeader = request.getSession().getAttribute(AuthenticationProperties.OAuth.AUTHORIZATION_HEADER.toString());
		if (authorizationHeader == null) {
			response.setStatus(401);
			return;
		}
		
		Object callbackUrl = request.getSession().getAttribute(AuthenticationParameter.CALLBACK_URL.toString());
		AuthenticationInfoJson authenticationInfoJson = new AuthenticationInfoJson();
		authenticationInfoJson.setAuthenticationHeader(authorizationHeader);
		authenticationInfoJson.setCallbackUrl(callbackUrl);

		try {
			String authenticationInfoJsonAsString = JsonUtil.toJson(authenticationInfoJson);
			response.setContentType(MediaType.APPLICATION_JSON);
			response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
			response.getWriter().println(authenticationInfoJsonAsString);
		} catch (IOException e) {
			LOGGER.error("Not able to write AuthenticationInfoJson in the response body.", e);
			throw new AuthenticationException(e);
		}
	}
	
}
