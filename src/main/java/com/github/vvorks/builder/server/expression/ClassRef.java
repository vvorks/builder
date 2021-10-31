package com.github.vvorks.builder.server.expression;

import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.DataType;

public class ClassRef extends ContentRef<ClassContent> {

	public ClassRef(ClassContent content) {
		super(content);
	}

	@Override
	public DataType getType() {
		return DataType.REF;
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
