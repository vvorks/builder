package com.github.vvorks.builder.server.expression;

import com.github.vvorks.builder.server.domain.DataType;

public class FloatLiteral extends Literal<Float> {

	public FloatLiteral(float value) {
		super(value);
	}

	@Override
	public DataType getType() {
		return DataType.REAL;
	}

	@Override
	public int getWidth() {
		return 32;
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
