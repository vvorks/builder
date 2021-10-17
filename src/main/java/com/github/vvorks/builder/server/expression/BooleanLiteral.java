package com.github.vvorks.builder.server.expression;

import com.github.vvorks.builder.server.domain.DataType;

public class BooleanLiteral extends Literal<Boolean> {

	public static final BooleanLiteral TRUE = new BooleanLiteral(true);

	public static final BooleanLiteral FALSE = new BooleanLiteral(false);

	private BooleanLiteral(boolean value) {
		super(value);
	}

	@Override
	public DataType getType() {
		return DataType.BOOLEAN;
	}

	@Override
	public int getWidth() {
		return 1;
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
