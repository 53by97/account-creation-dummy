/**
 * 
 */
package com.kvvssut.ac.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.kvvssut.ac.constants.ApplicationConstants;

/**
 * @author srimantas 20-Jan-2017
 */
public final class AccountCreationUtils {

	// private static final Logger LOGGER = LoggerFactory.getLogger(AccountCreationUtils.class);

	private AccountCreationUtils() {

	}

	/**
	 * @param propertiesFileName
	 * @return
	 */
	public static Map<String, Integer> getDefaultIntegerProperties(final String propertiesFileName) {
		final Map<String, Integer> defaultIntegerPropertiesMap = new HashMap<String, Integer>();
		final PropertiesReader propertiesReader = PropertiesReader.getInstance(propertiesFileName);

		// Multi-threading related int properties
		defaultIntegerPropertiesMap.put(ApplicationConstants.FILES_CORE_POOL_SIZE,
				propertiesReader.getIntegerPropertyValue(ApplicationConstants.FILES_CORE_POOL_SIZE, true));
		defaultIntegerPropertiesMap.put(ApplicationConstants.FILES_MAX_POOL_SIZE,
				propertiesReader.getIntegerPropertyValue(ApplicationConstants.FILES_MAX_POOL_SIZE, true));
		defaultIntegerPropertiesMap.put(ApplicationConstants.RECORDS_CORE_POOL_SIZE,
				propertiesReader.getIntegerPropertyValue(ApplicationConstants.RECORDS_CORE_POOL_SIZE, true));
		defaultIntegerPropertiesMap.put(ApplicationConstants.RECORDS_MAX_POOL_SIZE,
				propertiesReader.getIntegerPropertyValue(ApplicationConstants.RECORDS_MAX_POOL_SIZE, true));

		// User Service port related int properties
		defaultIntegerPropertiesMap.put(ApplicationConstants.USER_SERVICE_WEB_PORT,
				propertiesReader.getIntegerPropertyValue(ApplicationConstants.USER_SERVICE_WEB_PORT, true));

		return defaultIntegerPropertiesMap;
	}

	/**
	 * @param propertiesFileName
	 * @return
	 */
	public static Map<String, Long> getDefaultLongProperties(final String propertiesFileName) {
		final Map<String, Long> defaultLongPropertiesMap = new HashMap<String, Long>();
		final PropertiesReader propertiesReader = PropertiesReader.getInstance(propertiesFileName);

		// Multi-threading related long properties
		defaultLongPropertiesMap.put(ApplicationConstants.FILES_KEEP_ALIVE_TIME_SECS,
				propertiesReader.getLongPropertyValue(ApplicationConstants.FILES_KEEP_ALIVE_TIME_SECS, true));
		defaultLongPropertiesMap.put(ApplicationConstants.RECORDS_KEEP_ALIVE_TIME_SECS,
				propertiesReader.getLongPropertyValue(ApplicationConstants.RECORDS_KEEP_ALIVE_TIME_SECS, true));
		defaultLongPropertiesMap.put(ApplicationConstants.FILE_WATCHER_POLL_TIME_SECS,
				propertiesReader.getLongPropertyValue(ApplicationConstants.FILE_WATCHER_POLL_TIME_SECS, true));

		return defaultLongPropertiesMap;
	}

	/**
	 * @param propertiesFileName
	 * @return
	 */
	public static Map<String, String> getDefaultStringProperties(final String propertiesFileName) {
		final Map<String, String> defaultStringPropertiesMap = new HashMap<String, String>();
		final PropertiesReader propertiesReader = PropertiesReader.getInstance(propertiesFileName);

		// Authentication related properties
		defaultStringPropertiesMap.put(ApplicationConstants.AUTH_USER_ID,
				propertiesReader.getStringPropertyValue(ApplicationConstants.AUTH_USER_ID, true));
		defaultStringPropertiesMap.put(ApplicationConstants.AUTH_USER_PASSWD,
				propertiesReader.getStringPropertyValue(ApplicationConstants.AUTH_USER_PASSWD, true));

		// CSV header related properties
		defaultStringPropertiesMap.put(ApplicationConstants.HEADER_USER_NAME,
				propertiesReader.getStringPropertyValue(ApplicationConstants.HEADER_USER_NAME, true));
		defaultStringPropertiesMap.put(ApplicationConstants.HEADER_USER_EMAIL,
				propertiesReader.getStringPropertyValue(ApplicationConstants.HEADER_USER_EMAIL, true));
		defaultStringPropertiesMap.put(ApplicationConstants.HEADER_USER_CONTACT,
				propertiesReader.getStringPropertyValue(ApplicationConstants.HEADER_USER_CONTACT, true));

		// User Service URI related properties
		defaultStringPropertiesMap.put(ApplicationConstants.URL_SCHEME,
				propertiesReader.getStringPropertyValue(ApplicationConstants.URL_SCHEME, true));
		defaultStringPropertiesMap.put(ApplicationConstants.USER_SERVICE_WEB_HOST,
				propertiesReader.getStringPropertyValue(ApplicationConstants.USER_SERVICE_WEB_HOST, true));
		defaultStringPropertiesMap.put(ApplicationConstants.USER_SERVICE_WEB_URI_AUTH_LOGIN,
				propertiesReader.getStringPropertyValue(ApplicationConstants.USER_SERVICE_WEB_URI_AUTH_LOGIN, true));
		defaultStringPropertiesMap.put(ApplicationConstants.USER_SERVICE_WEB_URI_GET_MULTITENANT, propertiesReader
				.getStringPropertyValue(ApplicationConstants.USER_SERVICE_WEB_URI_GET_MULTITENANT, true));
		defaultStringPropertiesMap.put(ApplicationConstants.USER_SERVICE_WEB_URI_CREATE_ADMIN,
				propertiesReader.getStringPropertyValue(ApplicationConstants.USER_SERVICE_WEB_URI_CREATE_ADMIN, true));
		defaultStringPropertiesMap.put(ApplicationConstants.USER_SERVICE_WEB_URI_RESET_PASSWD,
				propertiesReader.getStringPropertyValue(ApplicationConstants.USER_SERVICE_WEB_URI_RESET_PASSWD, true));
		defaultStringPropertiesMap.put(ApplicationConstants.USER_SERVICE_WEB_URI_USER_MAP_ROLE_KEYCLOAK,
				propertiesReader.getStringPropertyValue(
						ApplicationConstants.USER_SERVICE_WEB_URI_USER_MAP_ROLE_KEYCLOAK, true));
		defaultStringPropertiesMap.put(ApplicationConstants.USER_SERVICE_WEB_URI_CREATE_ROLE,
				propertiesReader.getStringPropertyValue(ApplicationConstants.USER_SERVICE_WEB_URI_CREATE_ROLE, true));
		defaultStringPropertiesMap.put(ApplicationConstants.USER_SERVICE_WEB_URI_MAP_USER_ROLE,
				propertiesReader.getStringPropertyValue(ApplicationConstants.USER_SERVICE_WEB_URI_MAP_USER_ROLE, true));
		defaultStringPropertiesMap.put(ApplicationConstants.USER_SERVICE_WEB_URI_MAP_ROLE_PERMISSIONS, propertiesReader
				.getStringPropertyValue(ApplicationConstants.USER_SERVICE_WEB_URI_MAP_ROLE_PERMISSIONS, true));
		defaultStringPropertiesMap.put(ApplicationConstants.MAP_USER_ROLE,
				propertiesReader.getStringPropertyValue(ApplicationConstants.MAP_USER_ROLE, true));
		defaultStringPropertiesMap.put(ApplicationConstants.MAP_ROLE_PERMISSIONS,
				propertiesReader.getStringPropertyValue(ApplicationConstants.MAP_ROLE_PERMISSIONS, true));

		return defaultStringPropertiesMap;
	}

	/**
	 * @param filePath
	 * @param defaultStringProperties
	 * @return
	 */
	public static List<String> extractUserDetailsListFromCSVFile(Path filePath,
			Map<String, String> defaultStringProperties) {
		List<String> userDetailsRequestBeanList = new ArrayList<String>();

		try (CSVParser csvParser = new CSVParser(
				new InputStreamReader(Files.newInputStream(filePath), Charset.defaultCharset()),
				ApplicationConstants.DEFAULT_CSV_FORMAT);) {

			final Map<String, Integer> headers = csvParser.getHeaderMap();
			final boolean hasContactNumber = (headers != null)
					? headers.containsKey(defaultStringProperties.get(ApplicationConstants.HEADER_USER_CONTACT))
					: false;
			String userDetailsRequestBean = null;
			String name = null, email = null;

			for (CSVRecord record : csvParser) {
				name = record.get(defaultStringProperties.get(ApplicationConstants.HEADER_USER_NAME));
				if (name != null) {
					email = record.get(defaultStringProperties.get(ApplicationConstants.HEADER_USER_EMAIL));
					if (email != null) {
						userDetailsRequestBean = new String();
						/*userDetailsRequestBean.setFirstName(name);
						userDetailsRequestBean.setEmail(email);
						if (hasContactNumber) {
							userDetailsRequestBean.setContactNumber(
									record.get(defaultStringProperties.get(ApplicationConstants.HEADER_USER_CONTACT)));
						}
						userDetailsRequestBean.setStatus(ApplicationConstants.STATUS_1);*/
						userDetailsRequestBeanList.add(userDetailsRequestBean);
					} else {
						// LOGGER.error(LogKey.MESSAGE, String.join(" : ", "Record does not comply to expected format, kindly correct the name field", record.toString()));
					}
				} else {
					// LOGGER.error(LogKey.MESSAGE, String.join(" : ", "Record does not comply to expected format, kindly correct the email field", record.toString()));
				}
			}
			// LOGGER.info(LogKey.MESSAGE, String.join(" : ", "Extraction of user details completed for", filePath.getFileName().toString()));
		} catch (IOException e) {
			// LOGGER.error(e, LogKey.EXCEPTION, String.join(" : ", "IOException occurred while pasrsing", filePath.getFileName().toString()));
		}

		return userDetailsRequestBeanList;
	}

}
