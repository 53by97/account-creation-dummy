/**
 * 
 */
package com.kvvssut.ac.dtos;

/**
 * @author srimantas 20-Jan-2017
 */
public class AccountCreationResponseBean {

	private String identifier;
	private String passwd;
	private String message;
	private String displayId;

	/**
	 * @param identifier
	 * @param message
	 * @param displayId
	 */
	public AccountCreationResponseBean(String identifier, String passwd, String message) {
		super();
		this.identifier = identifier;
		this.passwd = passwd;
		this.message = message;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd
	 *            the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the displayId
	 */
	public String getDisplayId() {
		return displayId;
	}

	/**
	 * @param displayId
	 *            the displayId to set
	 */
	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}

}
