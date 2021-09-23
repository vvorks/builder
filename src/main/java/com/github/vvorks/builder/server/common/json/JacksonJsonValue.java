package com.github.vvorks.builder.server.common.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.vvorks.builder.common.json.JsonType;
import com.github.vvorks.builder.common.json.JsonValue;

public class JacksonJsonValue implements JsonValue {

	private JsonNode node;

	protected JacksonJsonValue(JsonNode node) {
		this.node = node;
	}

	@Override
	public JsonType getType() {
		return JacksonJsonContext.getJsonTypeOf(node);
	}

	protected JsonNode getValue() {
		return node;
	}

	@Override
	public boolean isUndefined() {
		return getType() == JsonType.UNDEFINED;
	}

	@Override
	public boolean booleanValue() {
		if (getType() != JsonType.BOOLEAN) {
			throw new IllegalStateException();
		}
		return node.asBoolean();
	}

	@Override
	public int intValue() {
		if (getType() != JsonType.NUMBER) {
			throw new IllegalStateException();
		}
		return node.asInt();
	}

	@Override
	public double doubleValue() {
		if (getType() != JsonType.NUMBER) {
			throw new IllegalStateException();
		}
		return node.asDouble();
	}

	@Override
	public String stringValue() {
		if (getType() != JsonType.STRING) {
			throw new IllegalStateException();
		}
		return node.asText();
	}

	@Override
	public String toJsonString() {
		return node == null ? "" : node.toString();
	}

	@Override
	public String toString() {
		return toJsonString();
	}

}
