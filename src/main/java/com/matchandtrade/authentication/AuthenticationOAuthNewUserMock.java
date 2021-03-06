
package com.matchandtrade.authentication;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used for testing and development purposes. It will not send an
 * authentication request to the authentication authority. Instead it is going
 * to return to "redirectURI" allowing quick development and testing.
 * 
 * @author rafael.santos.bra@gmail.com
 */
public class AuthenticationOAuthNewUserMock implements AuthenticationOAuth {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationOAuthNewUserMock.class);

	private String buidRandomAccessToken() {
		Random random = new Random();
		return random.nextInt(9999) + "-" +random.nextInt(9999) + "-" +random.nextInt(9999);
	}
	
	@Override
	public void redirectToAuthorizationAuthority(HttpServletResponse response, String state, String clientId, String redirectURI) throws AuthenticationException {
		URI uri = null;
		try {
			uri = new URIBuilder(redirectURI)
			        .setParameter("client_id", clientId)
			        .setParameter("response_type", "code")
			        .setParameter("scope", "openid email profile" )
			        .setParameter("redirect_uri", redirectURI)
			        .setParameter("state", state)
			        .build();
		} catch (URISyntaxException e) {
			logger.error("Error building authentication URI.", e);
		}
		try {
			if (uri != null) {
				logger.info("Redirecting to {}", uri);
				response.sendRedirect(uri.toString());
			}
		} catch (IOException e) {
			logger.error("Error redirecting to Authorization Authority.", e);
			throw new AuthenticationException(e);
		}
	}

	@Override
	public AuthenticationResponsePojo obtainUserInformation(String accessToken) throws AuthenticationException {
		long now = System.currentTimeMillis();
		String email = now + "@test.com";
		String name = now + "AuthenticationOAuthNewUserMock";
		return new AuthenticationResponsePojo(
				null,
				true,
				email,
				name,
				buidRandomAccessToken());
	}

	@Override
	public String obtainAccessToken(String codeParameter, String clientId, String clientSecret, String redirectURI)
			throws AuthenticationException {
		return buidRandomAccessToken();
	}

}
