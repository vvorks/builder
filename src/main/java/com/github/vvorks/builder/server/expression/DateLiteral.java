package com.github.vvorks.builder.server.expression;

import java.util.Date;

import com.github.vvorks.builder.server.domain.DataType;

public class DateLiteral extends Literal<Date> {

	public DateLiteral(Date value) {
		super(value);
	}

	@Override
	public DataType getType() {
		return DataType.DATE;
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
