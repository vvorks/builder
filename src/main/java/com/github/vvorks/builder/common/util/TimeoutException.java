package com.github.vvorks.builder.common.util;

public class TimeoutException extends Exception {

	private static final long serialVersionUID = 4529889139204402390L;

	public TimeoutException() {
		super();
	}

	public TimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public TimeoutException(String message) {
		super(message);
	}

	public TimeoutException(Throwable cause) {
		super(cause);
	}

}
