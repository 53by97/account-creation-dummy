/**
 * 
 */
package com.kvvssut.ac.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.client.utils.URIBuilder;

import com.kvvssut.ac.constants.ApplicationConstants;
import com.kvvssut.ac.dtos.AccountCreationResponseBean;
import com.kvvssut.ac.service.AccountCreationService;
import com.kvvssut.ac.utils.BaseRestClient;

/**
 * @author srimantas 19-Jan-2017
 */
public class AccountCreationServiceImpl implements AccountCreationService {

	// private static final Logger LOGGER = LoggerFactory.getLogger(AccountCreationServiceImpl.class);

	/**
	 * @param tenantId
	 * @param userDetailsRequestBean
	 * @param defaultIntegerProperties
	 * @param defaultStringProperties
	 * @return
	 * @throws URISyntaxException
	 */
	public AccountCreationResponseBean createUser(String tenantId, Map<String, Integer> defaultIntegerProperties, Map<String, String> defaultStringProperties) {
		// LOGGER.info(LogKey.MESSAGE, String.join(" : ", "Started creating user
		// for", userDetailsRequestBean.getEmail()));
		String userId = null, passwd = null;

		try {
			final String urlScheme = defaultStringProperties.get(ApplicationConstants.URL_SCHEME);
			final String webHost = defaultStringProperties.get(ApplicationConstants.USER_SERVICE_WEB_HOST);
			final int webPort = defaultIntegerProperties.get(ApplicationConstants.USER_SERVICE_WEB_PORT);

			Response response = loginAuthentication(urlScheme, webHost, webPort, defaultStringProperties);
			/* LoginResponseBean loginResponseBean */
			if (response.getStatus() == 200) {
				/* loginResponseBean = */ response.readEntity(/*LoginResponseBean.class*/String.class);
				// LOGGER.info(LogKey.MESSAGE, "Login authentication
				// successfully completed.", Key.USER_ID, userId);
			} else {
				throw new BadRequestException("Login authentication failed!");
			}

		} catch (Exception e) {
			/*LOGGER.error(e, LogKey.MESSAGE, String.join(" : ", "Started creating user for",
					userDetailsRequestBean.getEmail(), "with message", e.getMessage()));*/
			return new AccountCreationResponseBean(userId, passwd, "Failed");
		}

		/*LOGGER.info(LogKey.MESSAGE,
				String.join(" : ", "Creation of user completed for", userDetailsRequestBean.getEmail()), Key.USER_ID,
				userId);*/

		return new AccountCreationResponseBean(userId, passwd, "Success");
	}

	/**
	 * @param urlScheme
	 * @param webHost
	 * @param webPort
	 * @param defaultStringProperties
	 * @return
	 * @throws URISyntaxException
	 */
	private Response loginAuthentication(final String urlScheme, final String webHost, final int webPort,
			Map<String, String> defaultStringProperties) throws URISyntaxException {
		// loginRequest.setUserId(defaultStringProperties.get(ApplicationConstants.AUTH_USER_ID));
		// loginRequest.setPassword(defaultStringProperties.get(ApplicationConstants.AUTH_USER_PASSWD));

		URI httpURL = new URIBuilder().setScheme(urlScheme).setHost(webHost).setPort(webPort)
				.setPath(defaultStringProperties.get(ApplicationConstants.USER_SERVICE_WEB_URI_AUTH_LOGIN)).build();

		BaseRestClient restClient = new BaseRestClient();
		Builder builder = restClient.getBuilder(httpURL.toString());
		return builder.post(Entity.entity(/* loginRequest */null, MediaType.APPLICATION_JSON_TYPE), Response.class);
	}

}
