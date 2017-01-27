/**
 * 
 */
package com.kvvssut.ac.exceptions;

/**
 * @author srimantas
 * 18-Jan-2017
 */
@SuppressWarnings("serial")
public class SystemRuntimeException extends RuntimeException {
	
	/**
	 * @param message
	 */
	public SystemRuntimeException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SystemRuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SystemRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
