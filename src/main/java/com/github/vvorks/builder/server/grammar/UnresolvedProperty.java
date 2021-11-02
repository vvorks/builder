package com.github.vvorks.builder.server.grammar;

import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.expression.Expression;

public class UnresolvedProperty extends Expression {

	private final String name;

	private final boolean argument;

	public UnresolvedProperty(String name, boolean argument) {
		this.name = name;
		this.argument = argument;
	}

	public String getName() {
		return name;
	}

	@Override
	public DataType getType() {
		return null;
	}

	public boolean isArgument() {
		return argument;
	}

}
