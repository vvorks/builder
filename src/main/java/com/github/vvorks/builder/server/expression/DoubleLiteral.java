package com.github.vvorks.builder.server.expression;

import com.github.vvorks.builder.server.domain.DataType;

public class DoubleLiteral extends Literal<Double> {

	public DoubleLiteral(double value) {
		super(value);
	}

	@Override
	public DataType getType() {
		return DataType.REAL;
	}

	@Override
	public int getWidth() {
		return 64;
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
