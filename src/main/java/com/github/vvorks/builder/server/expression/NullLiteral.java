package com.github.vvorks.builder.server.expression;

import com.github.vvorks.builder.server.domain.DataType;

public class NullLiteral extends Literal<Void> {

	public static final NullLiteral INSTANCE = new NullLiteral();

	public NullLiteral() {
		super(null);
	}

	@Override
	public DataType getType() {
		return null;
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
