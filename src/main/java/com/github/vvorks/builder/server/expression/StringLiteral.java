package com.github.vvorks.builder.server.expression;

import com.github.vvorks.builder.server.domain.DataType;

public class StringLiteral extends Literal<String> {

	public StringLiteral(String value) {
		super(value);
	}

	@Override
	public DataType getType() {
		return DataType.STRING;
	}

	@Override
	public int getWidth() {
		return value.length();
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
