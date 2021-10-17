package com.github.vvorks.builder.server.expression;

import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.FieldDto;

public class FieldRef extends DtoRef<FieldDto> {

	public FieldRef(FieldDto fieldDto) {
		super(fieldDto);
	}

	@Override
	public DataType getType() {
		return dto.getType();
	}

	@Override
	public int getWidth() {
		return dto.getWidth();
	}

	@Override
	public int getScale() {
		return dto.getScale();
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
