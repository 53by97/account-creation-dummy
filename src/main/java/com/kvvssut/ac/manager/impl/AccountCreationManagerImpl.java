/**
 * 
 */
package com.kvvssut.ac.manager.impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.kvvssut.ac.constants.ApplicationConstants;
import com.kvvssut.ac.dtos.AccountCreationResponseBean;
import com.kvvssut.ac.manager.AccountCreationManager;
import com.kvvssut.ac.service.AccountCreationService;
import com.kvvssut.ac.service.impl.AccountCreationServiceImpl;
import com.kvvssut.ac.threads.JobExecutor;
import com.kvvssut.ac.utils.AccountCreationUtils;
import com.kvvssut.ac.utils.DirectoryWatcher;
import com.kvvssut.ac.utils.FilesUtil;
import com.kvvssut.ac.utils.PropertiesReader;

/**
 * @author srimantas 18-Jan-2017
 */
public class AccountCreationManagerImpl implements AccountCreationManager {

	// private static final Logger LOGGER = LoggerFactory.getLogger(AccountCreationManagerImpl.class);

	private AccountCreationService accountCreationService;

	public AccountCreationManagerImpl() {
		accountCreationService = new AccountCreationServiceImpl();
	}

	/**
	 * @param tenantId
	 * 
	 */
	public void processAccountCreation(final String tenantId) {
		// LOGGER.info(LogKey.MESSAGE, "Started processing of account creations ...");
		final String directoryPath = PropertiesReader.getInstance(ApplicationConstants.PROPERTIES_FILE_NAME)
				.getStringPropertyValue(ApplicationConstants.SECURED_STORAGE_LOCATION, true);

		final Map<String, Integer> defaultIntegerProperties = AccountCreationUtils
				.getDefaultIntegerProperties(ApplicationConstants.PROPERTIES_FILE_NAME);
		final Map<String, Long> defaultLongProperties = AccountCreationUtils
				.getDefaultLongProperties(ApplicationConstants.PROPERTIES_FILE_NAME);
		final Map<String, String> defaultStringProperties = AccountCreationUtils
				.getDefaultStringProperties(ApplicationConstants.PROPERTIES_FILE_NAME);

		List<Path> files = FilesUtil.listCSVFilesForDirectory(directoryPath);

		if (!files.isEmpty()) {
			processFiles(files, tenantId, defaultIntegerProperties, defaultLongProperties, defaultStringProperties);
		}
		// LOGGER.info(LogKey.MESSAGE, "Processing of pre-added file's account creations successfully completed!");

		watchDirectoryAndProcessAccountCreation(directoryPath, tenantId, defaultIntegerProperties,
				defaultLongProperties, defaultStringProperties);
	}

	/**
	 * @param directoryPath
	 * @param tenantId
	 * @param csvHeaderArgs
	 * @param poolSizeArgs
	 * @param keepAliveAndPollTimeArgs
	 */
	public void watchDirectoryAndProcessAccountCreation(final String directoryPath, final String tenantId,
			final Map<String, Integer> defaultIntegerProperties, final Map<String, Long> defaultLongProperties,
			final Map<String, String> defaultStringProperties) {
		WatchService watchService = DirectoryWatcher.registerDirectory(Paths.get(directoryPath));
		WatchKey watchKey = null;
		while (true) {
			try {
				// LOGGER.info(LogKey.MESSAGE, "Wating for addition of new files to process ...");
				watchKey = watchService.poll(
						defaultLongProperties.get(ApplicationConstants.FILE_WATCHER_POLL_TIME_SECS), TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			final List<Path> files = new ArrayList<Path>();
			if (watchKey != null) {
				watchKey.pollEvents().stream().forEach(event -> {
					String fileName = event.context().toString();
					if (FilesUtil.isCSVFile(fileName)) {
						files.add(Paths.get(String.join(File.separator, directoryPath, fileName)));
					}
				});
				watchKey.reset();
			}
			if (!files.isEmpty()) {
				processFiles(files, tenantId, defaultIntegerProperties, defaultLongProperties, defaultStringProperties);
				// LOGGER.info(LogKey.MESSAGE, "Account creations successfully completed for added files!");
			}
		}
	}

	/**
	 * @param files
	 * @param tenantId
	 * @param map
	 * @param map2
	 * @param map3
	 */
	private void processFiles(List<Path> files, final String tenantId,
			final Map<String, Integer> defaultIntegerProperties, final Map<String, Long> defaultLongProperties,
			final Map<String, String> defaultStringProperties) {
		// LOGGER.info(LogKey.MESSAGE, "Started processing of added files ...");
		final Map<String, Map<String, AccountCreationResponseBean>> resultMap = new ConcurrentHashMap<String, Map<String, AccountCreationResponseBean>>();
		final JobExecutor filesExecutor = new JobExecutor(
				defaultIntegerProperties.get(ApplicationConstants.FILES_CORE_POOL_SIZE),
				defaultIntegerProperties.get(ApplicationConstants.FILES_MAX_POOL_SIZE),
				defaultLongProperties.get(ApplicationConstants.FILES_KEEP_ALIVE_TIME_SECS));

		files.forEach(filePath -> {
			filesExecutor.execute(new Runnable() {

				@Override
				public void run() {
					Map<String, AccountCreationResponseBean> userDetailsMap = processFileForUserAccountCreation(
							filePath, tenantId, defaultIntegerProperties, defaultLongProperties,
							defaultStringProperties);
					synchronized (resultMap) {
						resultMap.put(filePath.getFileName().toString(), userDetailsMap);
					}
				}
			});
		});

		try {
			filesExecutor.shutdown();
			while (!filesExecutor.awaitTerminationInSeconds(
					defaultLongProperties.get(ApplicationConstants.FILES_KEEP_ALIVE_TIME_SECS).intValue())) {
				// LOGGER.info(LogKey.MESSAGE, "Waiting for all files executor threads to terminate ...");
			}
		} catch (InterruptedException e) {
			// LOGGER.error(e, LogKey.EXCEPTION, String.join(" : ", "Error occurred during processing of account creation with message", e.getMessage()));
		}

		// temporary code
		for (Map.Entry<String, Map<String, AccountCreationResponseBean>> entry : resultMap.entrySet()) {
			// LOGGER.info(entry.getKey());
			entry.getValue().entrySet().forEach(userEntry -> {
				// LOGGER.info(userEntry.getKey());
				// LOGGER.info(String.join(" : ", userEntry.getValue().getIdentifier(), userEntry.getValue().getMessage()));
			});
		}

	}

	/**
	 * @param filePath
	 * @param tenantId
	 * @param defaultIntegerProperties
	 * @param defaultLongProperties
	 * @param defaultStringProperties
	 * @return
	 */
	public Map<String, AccountCreationResponseBean> processFileForUserAccountCreation(final Path filePath,
			final String tenantId, final Map<String, Integer> defaultIntegerProperties,
			final Map<String, Long> defaultLongProperties, final Map<String, String> defaultStringProperties) {
		// LOGGER.info(LogKey.MESSAGE, String.join(" : ", "Started processing of file", filePath.getFileName().toString()));
		final Map<String, AccountCreationResponseBean> userDetailsMap = new ConcurrentHashMap<String, AccountCreationResponseBean>();
		final JobExecutor recordsExecutor = new JobExecutor(
				defaultIntegerProperties.get(ApplicationConstants.RECORDS_CORE_POOL_SIZE),
				defaultIntegerProperties.get(ApplicationConstants.RECORDS_MAX_POOL_SIZE),
				defaultLongProperties.get(ApplicationConstants.RECORDS_KEEP_ALIVE_TIME_SECS));

		recordsExecutor.execute(new Runnable() {

			@Override
			public void run() {
				/*List<UserDetailsRequestBean> userDetailsRequestBeanList = AccountCreationUtils .extractUserDetailsListFromCSVFile(filePath, defaultStringProperties);
				userDetailsRequestBeanList.forEach(userDetailsRequestBean -> {
					AccountCreationResponseBean responseDTO = accountCreationService.createUser(tenantId,
							userDetailsRequestBean, defaultIntegerProperties, defaultStringProperties);
					synchronized (userDetailsMap) {
						userDetailsMap.put(userDetailsRequestBean.getEmail(), responseDTO);
					}
				});*/
			}
		});

		try {
			recordsExecutor.shutdown();
			while (!recordsExecutor.awaitTerminationInSeconds(
					defaultLongProperties.get(ApplicationConstants.RECORDS_KEEP_ALIVE_TIME_SECS).intValue())) {
				// LOGGER.info(LogKey.MESSAGE, String.join(" : ", "Waiting for all records executor threads to terminate for file", filePath.getFileName().toString()));
			}
		} catch (InterruptedException e) {
			// LOGGER.error(e, LogKey.EXCEPTION, String.join(" : ", "Error occurred during processing of", filePath.getFileName().toString(), "with message", e.getMessage()));
		}
		return userDetailsMap;
	}

}
