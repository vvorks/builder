package com.github.vvorks.builder.server.grammar;

import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.expression.Expression;

public class UnresolvedProperty extends Expression {

	private final String name;

	public UnresolvedProperty(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public DataType getType() {
		return null;
	}

}
