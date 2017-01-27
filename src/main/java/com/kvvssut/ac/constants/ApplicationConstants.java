/**
 * 
 */
package com.kvvssut.ac.constants;

import org.apache.commons.csv.CSVFormat;

/**
 * @author srimantas 18-Jan-2017
 */
public final class ApplicationConstants {

	private ApplicationConstants() {

	}

	/*
	 * Start-up related constants
	 */
	public static final String PARAM_TENANT_ID = "tenantId";
	public static final String PARAM_ACCOUNT_CREATION_FLAG = "accountCreationFlag";

	/*
	 * Files related constants
	 */
	public static final String PROPERTIES_FILE_NAME = "account-creation.properties";
	public static final String SECURED_STORAGE_LOCATION = "secured.storage.location";
	public static final String SECURED_PROCESSED_LOCATION = "secured.processed.location";
	public static final String SECURED_FAILED_LOCATION = "secured.failed.location";

	/*
	 * Properties related constants
	 */
	public static final String AUTH_USER_ID = "auth.user.id";
	public static final String AUTH_USER_PASSWD = "auth.user.passwd";
	public static final String HEADER_USER_NAME = "csv.header.user.name";
	public static final String HEADER_USER_EMAIL = "csv.header.user.email";
	public static final String HEADER_USER_CONTACT = "csv.header.user.contact";
	public static final String FILES_CORE_POOL_SIZE = "files.core.pool.size";
	public static final String FILES_MAX_POOL_SIZE = "files.max.pool.size";
	public static final String FILES_KEEP_ALIVE_TIME_SECS = "files.keep.alive.time.secs";
	public static final String RECORDS_CORE_POOL_SIZE = "records.core.pool.size";
	public static final String RECORDS_MAX_POOL_SIZE = "records.max.pool.size";
	public static final String RECORDS_KEEP_ALIVE_TIME_SECS = "records.keep.alive.time.secs";
	public static final String FILE_WATCHER_POLL_TIME_SECS = "file.watcher.poll.time.secs";

	/*
	 * User Service URI related properties
	 */
	public static final String URL_SCHEME = "url.scheme";
	public static final String USER_SERVICE_WEB_HOST = "user.service.web.host";
	public static final String USER_SERVICE_WEB_PORT = "user.service.web.port";
	public static final String USER_SERVICE_WEB_URI_AUTH_LOGIN = "user.service.web.uri.auth.login";
	public static final String USER_SERVICE_WEB_URI_GET_MULTITENANT = "user.service.web.uri.get.multitenant";
	public static final String USER_SERVICE_WEB_URI_CREATE_ADMIN = "user.service.web.uri.create.admin";
	public static final String USER_SERVICE_WEB_URI_RESET_PASSWD = "user.service.web.uri.reset.passwd";
	public static final String USER_SERVICE_WEB_URI_USER_MAP_ROLE_KEYCLOAK = "user.service.web.uri.map.role.keycloak";
	public static final String USER_SERVICE_WEB_URI_CREATE_ROLE = "user.service.web.uri.create.role";
	public static final String USER_SERVICE_WEB_URI_MAP_USER_ROLE = "user.service.web.uri.map.user.role";
	public static final String USER_SERVICE_WEB_URI_MAP_ROLE_PERMISSIONS = "user.service.web.uri.map.role.permissions";
	public static final String MAP_USER_ROLE = "map.user.role";
	public static final String MAP_ROLE_PERMISSIONS = "map.role.permissions";

	/*
	 * CSV constants
	 */
	public static final CSVFormat DEFAULT_CSV_FORMAT = CSVFormat.newFormat(',').withQuote('"').withHeader();

	/*
	 * Other constants
	 */
	public static final String FILE_EXTENSION_CSV = "csv";
	public static final String STATUS_1 = "1";
	public static final String N = "N";
	public static final String Y = "Y";
	public static final String ADMIN_EMAIL = "admin@kvvssut.com";
	public static final String TENANT_ID = "TenantId";
	public static final String REALM_NAME = "RealmName";
	public static final String USER_ID = "UserId";
	public static final String TOKEN_BEARER = "Bearer";
	public static final int PASSWD_LENGTH = 8;
	public static final int TOKEN_START_INDEX = 7;

}
