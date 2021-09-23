package com.github.vvorks.builder.server.common.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.vvorks.builder.common.json.AbstractJsonContext;
import com.github.vvorks.builder.common.json.JsonType;
import com.github.vvorks.builder.common.json.JsonValue;
import com.github.vvorks.builder.common.lang.Asserts;

public class JacksonJsonContext extends AbstractJsonContext<JsonNode> {

	private ObjectMapper mapper = new ObjectMapper();

	protected JacksonJsonContext() {
		enter(mapper.createObjectNode());
	}

	public JacksonJsonContext(Object obj) {
		JsonNode jsonValue;
		if (obj instanceof String) {
			jsonValue = parse((String) obj);
		} else if (obj instanceof JsonValue) {
			jsonValue = fromValue((JsonValue) obj);
		} else {
			throw new IllegalArgumentException();
		}
		enter(jsonValue);
	}

	protected JacksonJsonContext(String jsonString) {
		enter(parse(jsonString));
	}

	protected JacksonJsonContext(JsonValue value) {
		enter(fromValue(value));
	}

	public static JsonType getJsonTypeOf(JsonNode value) {
		if (value == null) {
			return JsonType.UNDEFINED;
		} else if (value.isNull()) {
			return JsonType.NULL;
		} else if (value.isBoolean()) {
			return JsonType.BOOLEAN;
		} else if (value.isNumber()) {
			return JsonType.NUMBER;
		} else if (value.isTextual()) {
			return JsonType.STRING;
		} else if (value.isObject()) {
			return JsonType.OBJECT;
		} else if (value.isArray()) {
			return JsonType.ARRAY;
		} else {
			return JsonType.UNDEFINED;
		}
	}

	@Override
	public JsonType getCurrentType() {
		return getJsonTypeOf(peek());
	}

	@Override
	public void enterObject(String key) {
		ObjectNode parent = getParentObject(key);
		JsonNode child = parent.get(key);
		if (child == null || child.isObject()) {
			child = mapper.createObjectNode();
			parent.set(key, child);
		}
		enter(child);
	}

	@Override
	public void enterObject(int index) {
		ArrayNode parent = getParentArray(index, true);
		JsonNode child = parent.get(index);
		if (child == null || child.isObject()) {
			child = mapper.createObjectNode();
			parent.set(index, child);
		}
		enter(child);
	}

	@Override
	public void enterArray(String key) {
		ObjectNode parent = getParentObject(key);
		JsonNode child = parent.get(key);
		if (child == null || child.isArray()) {
			child = mapper.createArrayNode();
			parent.set(key, child);
		}
		enter(child);
	}

	@Override
	public void enterArray(int index) {
		ArrayNode parent = getParentArray(index, true);
		JsonNode child = parent.get(index);
		if (child == null || child.isArray()) {
			child = mapper.createArrayNode();
			parent.set(index, child);
		}
		enter(child);
	}

	@Override
	public int length() {
		JsonNode value = peek();
		if (getJsonTypeOf(value) != JsonType.ARRAY) {
			throw new IllegalStateException();
		}
		ArrayNode parent = (ArrayNode) value;
		return parent.size();
	}

	@Override
	protected JsonNode parse(String jsonString) {
		JsonNode value;
		if ("{}".equals(jsonString)) {
			value = mapper.createObjectNode();
		} else if ("[]".equals(jsonString)) {
			value = mapper.createArrayNode();
		} else {
			try {
				value = mapper.readTree(jsonString);
			} catch (JsonProcessingException err) {
				throw new RuntimeException(err);
			}
		}
		return value;
	}

	@Override
	protected JsonNode getNativeValue(String key) {
		ObjectNode parent = getParentObject(key);
		return parent.get(key);
	}

	@Override
	protected JsonNode getNativeValue(int index) {
		ArrayNode parent = getParentArray(index, false);
		return parent.get(index);
	}

	@Override
	protected void setNativeValue(String key, JsonNode value) {
		ObjectNode parent = getParentObject(key);
		parent.put(key, value);
	}

	@Override
	protected void setNativeValue(int index, JsonNode value) {
		ArrayNode parent = getParentArray(index, true);
		parent.set(index, value);
	}

	@Override
	protected boolean toBoolean(JsonNode value, boolean defaultValue) {
		boolean result;
		switch (getJsonTypeOf(value)) {
		case BOOLEAN:
			result = value.asBoolean();
			break;
		case NUMBER:
			result = (value.asDouble() != 0.0);
			break;
		case STRING:
			result = Boolean.valueOf(value.asText());
			break;
		case OBJECT:
		case ARRAY:
		case NULL:
		case UNDEFINED:
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

	@Override
	protected int toInt(JsonNode value, int defaultValue) {
		int result;
		switch (getJsonTypeOf(value)) {
		case BOOLEAN:
			result = value.asBoolean() ? 1 : 0;
			break;
		case NUMBER:
			result = (int) value.asDouble();
			break;
		case STRING:
			try {
				result = Integer.decode(value.asText());
			} catch (NumberFormatException err) {
				result = defaultValue;
			}
			break;
		case OBJECT:
		case ARRAY:
		case NULL:
		case UNDEFINED:
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

	@Override
	protected double toDouble(JsonNode value, double defaultValue) {
		double result;
		switch (getJsonTypeOf(value)) {
		case BOOLEAN:
			result = value.asBoolean() ? 1 : 0;
			break;
		case NUMBER:
			result = value.asDouble();
			break;
		case STRING:
			try {
				result = Double.valueOf(value.asText());
			} catch (NumberFormatException err) {
				result = defaultValue;
			}
			break;
		case OBJECT:
		case ARRAY:
		case NULL:
		case UNDEFINED:
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

	@Override
	protected String toString(JsonNode value, String defaultValue) {
		String result;
		switch (getJsonTypeOf(value)) {
		case BOOLEAN:
			result = String.valueOf(value.asBoolean());
			break;
		case NUMBER:
			result = String.valueOf(value.asDouble());
			break;
		case STRING:
			result = value.asText();
			break;
		case OBJECT:
		case ARRAY:
		case NULL:
		case UNDEFINED:
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

	@Override
	protected JsonValue toValue(JsonNode value, JsonValue defaultValue) {
		JsonValue result;
		switch (getJsonTypeOf(value)) {
		case BOOLEAN:
		case NUMBER:
		case STRING:
		case OBJECT:
		case ARRAY:
		case NULL:
			result = new JacksonJsonValue(value);
			break;
		case UNDEFINED:
		default:
			result = defaultValue;
			break;
		}
		return result;
	}

	@Override
	protected JsonNode fromNull() {
		return mapper.nullNode();
	}

	@Override
	protected JsonNode fromBoolean(boolean value) {
		return value ? BooleanNode.TRUE : BooleanNode.FALSE;
	}

	@Override
	protected JsonNode fromInt(int value) {
		return DoubleNode.valueOf(value);
	}

	@Override
	protected JsonNode fromDouble(double value) {
		return DoubleNode.valueOf(value);
	}

	@Override
	protected JsonNode fromString(String value) {
		return TextNode.valueOf(value);
	}

	@Override
	protected JsonNode fromValue(JsonValue value) {
		JsonNode jsonValue;
		if (value == null) {
			jsonValue = mapper.nullNode();
		} else if (value instanceof JacksonJsonValue) {
			jsonValue = ((JacksonJsonValue) value).getValue();
		} else {
			jsonValue = parse(value.toJsonString());
		}
		return jsonValue;
	}

	private ObjectNode getParentObject(String key) {
		JsonNode value = peek();
		Asserts.assume(getJsonTypeOf(value) == JsonType.OBJECT);
		ObjectNode parent = (ObjectNode) value;
		Asserts.requireNotEmpty(key);
		return parent;
	}

	private ArrayNode getParentArray(int index, boolean isSet) {
		JsonNode value = peek();
		Asserts.assume(getJsonTypeOf(value) == JsonType.ARRAY);
		ArrayNode parent = (ArrayNode) value;
		Asserts.require(0 <= index && index < parent.size() + (isSet ? 1 : 0));
		return parent;
	}

}
