package com.github.vvorks.builder.server.expression;

import com.github.vvorks.builder.server.domain.DataType;
import com.github.vvorks.builder.server.domain.FieldContent;

public class FieldRef extends ContentRef<FieldContent> {

	public FieldRef(FieldContent content) {
		super(content);
	}

	@Override
	public DataType getType() {
		return content.getType();
	}

	@Override
	public int getWidth() {
		return content.getWidth();
	}

	@Override
	public int getScale() {
		return content.getScale();
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
