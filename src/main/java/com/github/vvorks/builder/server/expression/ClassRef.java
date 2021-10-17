package com.github.vvorks.builder.server.expression;

import com.github.vvorks.builder.server.domain.ClassDto;
import com.github.vvorks.builder.server.domain.DataType;

public class ClassRef extends DtoRef<ClassDto> {

	public ClassRef(ClassDto classDto) {
		super(classDto);
	}

	@Override
	public DataType getType() {
		return DataType.CLASS_REF;
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
