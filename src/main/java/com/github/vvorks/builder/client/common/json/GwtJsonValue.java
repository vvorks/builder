package com.github.vvorks.builder.client.common.json;

import com.github.vvorks.builder.common.json.JsonType;
import com.github.vvorks.builder.common.json.JsonValue;
import com.google.gwt.json.client.JSONValue;

public class GwtJsonValue implements JsonValue {

	private final JSONValue value;

	protected GwtJsonValue(JSONValue value) {
		this.value = value;
	}

	public boolean isUndefined() {
		return getType() == JsonType.UNDEFINED;
	}

	protected JSONValue getValue() {
		return value;
	}

	@Override
	public JsonType getType() {
		return GwtJsonContext.getJsonTypeOf(value);
	}

	@Override
	public boolean booleanValue() {
		if (getType() != JsonType.BOOLEAN) {
			throw new IllegalStateException();
		}
		return value.isBoolean().booleanValue();
	}

	@Override
	public int intValue() {
		if (getType() != JsonType.NUMBER) {
			throw new IllegalStateException();
		}
		return (int) value.isNumber().doubleValue();
	}

	@Override
	public double doubleValue() {
		if (getType() != JsonType.NUMBER) {
			throw new IllegalStateException();
		}
		return value.isNumber().doubleValue();
	}

	@Override
	public String stringValue() {
		if (getType() != JsonType.STRING) {
			throw new IllegalStateException();
		}
		return value.isString().stringValue();
	}

	@Override
	public String toJsonString() {
		return value == null ? "" : value.toString();
	}

	@Override
	public String toString() {
		return toJsonString();
	}

}
