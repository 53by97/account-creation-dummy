/**
 * 
 */
package com.kvvssut.ac.servlets;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.kvvssut.ac.constants.ApplicationConstants;
import com.kvvssut.ac.manager.AccountCreationManager;
import com.kvvssut.ac.manager.impl.AccountCreationManagerImpl;

/**
 * @author srimantas 18-Jan-2017
 */
@SuppressWarnings("serial")
public class AccountCreationConfig extends HttpServlet {

	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(AccountCreationConfig.class);

	private AccountCreationManager accountCreationManager;

	public AccountCreationConfig() {
		accountCreationManager = new AccountCreationManagerImpl();
	}

	public void init() throws ServletException {
		// LOGGER.info(LogKey.MESSAGE, "Initiating account creation
		// configurations ...");
		final boolean processAccountCreation = Boolean
				.parseBoolean(getInitParameter(ApplicationConstants.PARAM_ACCOUNT_CREATION_FLAG));
		if (processAccountCreation) {
			final String tenantId = getInitParameter(ApplicationConstants.PARAM_TENANT_ID);

			Thread schedulerThread = new Thread(new Runnable() {

				@Override
				public void run() {
					accountCreationManager.processAccountCreation(tenantId);
				}
			});

			schedulerThread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

				@Override
				public void uncaughtException(Thread t, Throwable e) {
					// LOGGER.error(e, LogKey.EXCEPTION, String.join(" : ",
					// "Error occurred during processing of account creation
					// with message", e.getMessage()));
				}
			});

			schedulerThread.start();
		} else {
			// LOGGER.info(LogKey.MESSAGE, String.join(" : ", "Processing of
			// account creation is skipped because ",
			// ApplicationConstants.PARAM_ACCOUNT_CREATION_FLAG, "is set as
			// false."));
		}
		// LOGGER.info(LogKey.MESSAGE, "Account creation configurations
		// completed!");
	}

}
