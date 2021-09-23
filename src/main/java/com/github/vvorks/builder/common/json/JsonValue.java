package com.github.vvorks.builder.common.json;

public interface JsonValue {

	public abstract boolean isUndefined();

	public abstract JsonType getType();

	public abstract boolean booleanValue();

	public abstract int intValue();

	public abstract double doubleValue();

	public abstract String stringValue();

	public abstract String toJsonString();

}
