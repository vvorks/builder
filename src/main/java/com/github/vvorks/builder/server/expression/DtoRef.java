package com.github.vvorks.builder.server.expression;

public abstract class DtoRef<T> extends Expression {

	protected final T dto;

	protected DtoRef(T dto) {
		this.dto = dto;
	}

	public T getDto() {
		return dto;
	}

}
