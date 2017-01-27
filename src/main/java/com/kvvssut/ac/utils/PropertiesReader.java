/**
 * 
 */
package com.kvvssut.ac.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.kvvssut.ac.exceptions.SystemRuntimeException;

/**
 * @author srimantas 18-Jan-2017
 */
public final class PropertiesReader {

	//private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesReader.class);
	private static final Map<String, PropertiesReader> PROPERTIES_MAP = new HashMap<String, PropertiesReader>();

	private Properties properties;

	private PropertiesReader() {

	}

	/**
	 * @param fileName
	 * @return
	 */
	public static PropertiesReader getInstance(final String fileName) {
		PropertiesReader propertiesReader = PROPERTIES_MAP.get(fileName);
		if (propertiesReader == null) {
			try {
				propertiesReader = loadAndReturnProperties(fileName);
				PROPERTIES_MAP.put(fileName, propertiesReader);
				//LOGGER.info(LogKey.MESSAGE, String.join(" : ", "Successfully loaded the properties file", fileName));
			} catch (IOException e) {
				//LOGGER.error(e, Key.EXCEPTION, String.join(" : ", "Error while loading the properties file", fileName));
			}
		}
		return propertiesReader;
	}

	/**
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private static PropertiesReader loadAndReturnProperties(final String fileName) throws IOException {
		PropertiesReader propertiesReader = null;
		InputStream inputStream = null;
		try {
			inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(fileName);
			if (inputStream != null) {
				propertiesReader = new PropertiesReader();
				propertiesReader.properties = new Properties();
				try {
					propertiesReader.properties.load(inputStream);
				} catch (IOException e) {
					//LOGGER.error(Key.MESSAGE, "Error while loading the input stream");
					throw e;
				}
			}
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					//LOGGER.error(Key.MESSAGE, "Error while closing the input stream");
					throw e;
				}
			}
		}
		return propertiesReader;
	}

	/**
	 * @param propertyName
	 * @param mustExist
	 * @return
	 */
	public String getStringPropertyValue(final String propertyName, final boolean mustExist) {
		String value = getPropertyValue(propertyName);
		if (mustExist && value == null) {
			throw new SystemRuntimeException(String.join(" : ", "Property", propertyName, "value not configured in properties file!"));
		}
		return value;
	}

	/**
	 * @param propertyName
	 * @param mustExist
	 * @return
	 */
	public Integer getIntegerPropertyValue(final String propertyName, final boolean mustExist) {
		String value = getPropertyValue(propertyName);
		if (mustExist && value == null) {
			throw new SystemRuntimeException(
					String.join(" : ", "Property", propertyName, "value not configured in properties file!"));
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new SystemRuntimeException(
					String.join(" : ", "Property", propertyName, "value is not integer type!"));
		}
	}

	/**
	 * @param propertyName
	 * @param mustExist
	 * @return
	 */
	public Long getLongPropertyValue(final String propertyName, final boolean mustExist) {
		String value = getPropertyValue(propertyName);
		if (mustExist && value == null) {
			throw new SystemRuntimeException(
					String.join(" : ", "Property", propertyName, "value not configured in properties file!"));
		}
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			throw new SystemRuntimeException(String.join(" : ", "Property", propertyName, "value is not long type!"));
		}
	}

	/**
	 * @param propertyName
	 * @param mustExist
	 * @return
	 */
	public Boolean getBooleanPropertyValue(final String propertyName, final boolean mustExist) {
		String value = getPropertyValue(propertyName);
		if (mustExist && value == null) {
			throw new SystemRuntimeException(
					String.join(" : ", "Property", propertyName, "value not configured in properties file!"));
		}
		try {
			return Boolean.parseBoolean(value);
		} catch (NumberFormatException e) {
			throw new SystemRuntimeException(
					String.join(" : ", "Property", propertyName, "value is not boolean type!"));
		}
	}

	/**
	 * @param propertyName
	 * @return
	 */
	private String getPropertyValue(final String propertyName) {
		String value = null;
		if (properties != null) {
			value = properties.getProperty(propertyName);
		}
		return value;
	}

}
