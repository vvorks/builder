package com.github.vvorks.builder.server.expression;

public abstract class Literal<T> extends Expression {

	protected final T value;

	protected Literal(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

}
