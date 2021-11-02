package com.github.vvorks.builder.server.expression;

import com.github.vvorks.builder.server.domain.DataType;

public class Argument extends Expression {

	private final String name;

	private DataType type;

	private int width;

	private int scale;

	private int erefId;

	public Argument(String name) {
		this.name = name;
		this.type = null;
	}

	public String getName() {
		return name;
	}

	@Override
	public DataType getType() {
		return type;
	}

	public void setType(DataType type) {
		this.type = type;
	}

	@Override
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getErefId() {
		return erefId;
	}

	public void setErefId(int refId) {
		this.erefId = refId;
	}

	@Override
	public <A,R> R accept(Visitor<A,R> visitor, A option) {
		return visitor.visit(this, option);
	}

}
