/**
 * 
 */
package com.kvvssut.ac.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.kvvssut.ac.constants.ApplicationConstants;

/**
 * @author srimantas 23-Jan-2017
 */
public class BaseRestClient {

	private Invocation.Builder builder;
	private Map<String, String> headersMap = new HashMap<String, String>();
	private Client client = null;

	public BaseRestClient() {
		client = ClientBuilder.newBuilder().build();
		// populateHeaders();
	}

	/**
	 * @param uriString
	 * @return
	 */
	public Invocation.Builder getBuilder(String uriString) {
		WebTarget webTarget = client.target(uriString);
		webTarget.register(JacksonFeature.class);
		builder = webTarget.request();
		Iterator<Map.Entry<String, String>> itr = headersMap.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, String> entry = itr.next();
			builder = builder.header((String) entry.getKey(), entry.getValue());
		}
		return builder;
	}

	/**
	 * 
	 */
	public void close() {
		client.close();
	}
}
