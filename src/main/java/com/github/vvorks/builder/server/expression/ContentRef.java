package com.github.vvorks.builder.server.expression;

public abstract class ContentRef<T> extends Expression {

	protected final T content;

	protected ContentRef(T content) {
		this.content = content;
	}

	public T getContent() {
		return content;
	}

}
