package com.github.vvorks.builder.server.expression;

import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.EnumValueDto;

public class EnumValueRef extends DtoRef<EnumValueDto> {

	public EnumValueRef(EnumValueDto enumValueDto) {
		super(enumValueDto);
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
