package com.github.vvorks.builder.common.lang;

public class Result<T> {

	private final T result;

	private final Exception error;

	public Result(T result) {
		this.result = result;
		this.error = null;
	}

	public Result(Exception error) {
		Asserts.requireNotNull(error);
		this.result = null;
		this.error = error;
	}

	public T get() throws Exception {
		if (error != null) {
			throw error;
		}
		return result;
	}

}
