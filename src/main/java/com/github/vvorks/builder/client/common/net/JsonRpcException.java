package com.github.vvorks.builder.client.common.net;

public class JsonRpcException extends Exception {

	private static final long serialVersionUID = 548560360019201462L;

	public JsonRpcException() {
		super();
	}

	public JsonRpcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JsonRpcException(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonRpcException(String message) {
		super(message);
	}

	public JsonRpcException(Throwable cause) {
		super(cause);
	}

}
