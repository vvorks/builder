package com.github.vvorks.builder.server.expression;

import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.EnumDto;

public class EnumRef extends DtoRef<EnumDto> {

	public EnumRef(EnumDto enumDto) {
		super(enumDto);
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
