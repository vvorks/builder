package com.github.vvorks.builder.server.expression;

import com.github.vvorks.builder.server.domain.DataType;

public class IntLiteral extends Literal<Integer> {

	private final int radix;

	public IntLiteral(int value, int radix) {
		super(value);
		this.radix = radix;
	}

	@Override
	public DataType getType() {
		return DataType.INTEGER;
	}

	@Override
	public int getWidth() {
		return 32;
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

	public int getRadix() {
		return radix;
	}

}
