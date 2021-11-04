package com.github.vvorks.builder.common.net;

import java.util.HashMap;
import java.util.Map;

public class JsonRpcs {

	public static final String KEY_JSONRPC	= "jsonrpc";
	public static final String KEY_ID		= "id";
	public static final String KEY_METHOD	= "method";
	public static final String KEY_PARAMS	= "params";
	public static final String KEY_RESULT	= "result";
	public static final String KEY_ERROR	= "error";
	public static final String KEY_CODE		= "code";
	public static final String KEY_MESSAGE	= "message";

	public static final int PARSE_ERROR			= -32700;
	public static final int INVALID_REQUEST		= -32600;
	public static final int METHOD_NOT_FOUND	= -32601;
	public static final int INVALID_PARAMS		= -32602;
	public static final int INTERNAL_ERROR		= -32603;
	public static final int APPLICATION_ERROR	= -32500;
	public static final int SYSTEM_ERROR		= -32400;
	public static final int TRANSPORT_ERROR		= -32300;

	private static final Map<Class<?>, Integer> ERROR_CODES = new HashMap<Class<?>, Integer>();
	static {
		ERROR_CODES.put(IllegalArgumentException.class, INVALID_PARAMS);
	}

	private JsonRpcs() {
	}

	public static int getErrorCode(Throwable err) {
		return getErrorCode(err.getClass());
	}

	public static int getErrorCode(Class<?> errCls) {
		Integer code = null;
		while (errCls != null && (code = ERROR_CODES.get(errCls)) == null) {
			errCls = errCls.getSuperclass();
		}
		return (code != null) ? code : INTERNAL_ERROR;
	}

}
