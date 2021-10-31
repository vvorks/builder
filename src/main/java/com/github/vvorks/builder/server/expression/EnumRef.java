package com.github.vvorks.builder.server.expression;

import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.EnumContent;

public class EnumRef extends ContentRef<EnumContent> {

	public EnumRef(EnumContent conent) {
		super(conent);
	}

	@Override
	public DataType getType() {
		return DataType.ENUM;
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
