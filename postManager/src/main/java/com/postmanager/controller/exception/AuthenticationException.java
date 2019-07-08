package com.postmanager.controller.exception;

public class AuthenticationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6347322325807532698L;

	public AuthenticationException(String msg) {
		super(msg);
	}
}
