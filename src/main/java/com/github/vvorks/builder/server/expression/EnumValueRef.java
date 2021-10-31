package com.github.vvorks.builder.server.expression;

import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.EnumValueContent;

public class EnumValueRef extends ContentRef<EnumValueContent> {

	public EnumValueRef(EnumValueContent content) {
		super(content);
	}

	@Override
	public DataType getType() {
		return DataType.ENUM_VALUE;
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
