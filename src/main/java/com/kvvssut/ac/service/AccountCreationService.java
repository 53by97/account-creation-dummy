/**
 * 
 */
package com.kvvssut.ac.service;

import java.util.Map;

import com.kvvssut.ac.dtos.AccountCreationResponseBean;

/**
 * @author srimantas 19-Jan-2017
 */
public interface AccountCreationService {

	public AccountCreationResponseBean createUser(String tenantId, Map<String, Integer> defaultIntegerProperties,
			Map<String, String> defaultStringProperties);

}
