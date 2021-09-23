package com.github.vvorks.builder.server.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

import com.github.vvorks.builder.common.lang.Asserts;

public class Invoker {

	private final Object target;

	private final Method method;

	public Invoker(Object target, Method method) {
		Asserts.requireNotNull(target);
		Asserts.requireNotNull(method);
		this.target = target;
		this.method = method;
	}

	public Object getTarget() {
		return target;
	}

	public Method getMethod() {
		return method;
	}

	public List<Parameter> getParameters() {
		return Arrays.asList(method.getParameters());
	}

	@SuppressWarnings("unchecked")
	public <T> T invoke(Object... params) {
		try {
			return (T) method.invoke(target, params);
		} catch (IllegalAccessException err) {
			throw new IllegalStateException(method.getName(), err);
		} catch (InvocationTargetException err) {
			Throwable cause = err.getCause();
			if (cause instanceof RuntimeException) {
				throw (RuntimeException) cause;
			}
			throw new IllegalStateException(method.getName(), cause);
		}
	}

}