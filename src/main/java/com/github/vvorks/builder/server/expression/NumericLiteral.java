package com.github.vvorks.builder.server.expression;

import java.math.BigDecimal;

import com.github.vvorks.builder.server.domain.DataType;

public class NumericLiteral extends Literal<BigDecimal> {

	public NumericLiteral(BigDecimal value) {
		super(value);
	}

	@Override
	public DataType getType() {
		return DataType.NUMERIC;
	}

	@Override
	public int getWidth() {
		return value.precision();
	}

	@Override
	public int getScale() {
		return value.scale();
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
