package com.github.vvorks.builder.server.common.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.util.SimpleEntry;

public class JacksonJson extends Json {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private final JsonNode nativeValue;

	public JacksonJson(Object obj) {
		if (obj == null) {
			nativeValue = MAPPER.nullNode();
		} else if (obj instanceof JsonNode) {
			nativeValue = (JsonNode) obj;
		} else if (obj instanceof JacksonJson) {
			nativeValue = ((JacksonJson)obj).getNativeValue();
		} else if (obj instanceof Json) {
			nativeValue = parse(((Json)obj).toJsonString());
		} else if (obj instanceof String) {
			nativeValue = parse((String) obj);
		} else {
			nativeValue = fromObject(obj);
		}
	}

	private static JsonNode parse(String jsonString) {
		JsonNode value;
		if ("{}".equals(jsonString)) {
			value = MAPPER.createObjectNode();
		} else if ("[]".equals(jsonString)) {
			value = MAPPER.createArrayNode();
		} else {
			try {
				value = MAPPER.readTree(jsonString);
			} catch (JsonProcessingException err) {
				throw new IllegalArgumentException(err);
			}
		}
		return value;
	}

	public static Type getTypeOf(JsonNode value) {
		if (value == null) {
			return Type.UNDEFINED;
		} else if (value.isNull()) {
			return Type.NULL;
		} else if (value.isBoolean()) {
			return Type.BOOLEAN;
		} else if (value.isNumber()) {
			return Type.NUMBER;
		} else if (value.isTextual()) {
			return Type.STRING;
		} else if (value.isObject()) {
			return Type.OBJECT;
		} else if (value.isArray()) {
			return Type.ARRAY;
		} else {
			return Type.UNDEFINED;
		}
	}

	private static boolean asBoolean(JsonNode value, boolean defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return value.asBoolean();
		case NUMBER:
			return (value.asDouble() != 0.0);
		case STRING:
			return Boolean.valueOf(value.asText());
		default:
			return defaultValue;
		}
	}

	private static double asNumber(JsonNode value, double defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return value.asBoolean() ? 1 : 0;
		case NUMBER:
			return value.asDouble();
		case STRING:
			try {
				return Double.valueOf(value.asText());
			} catch (NumberFormatException err) {
				return defaultValue;
			}
		default:
			return defaultValue;
		}
	}

	private static String asString(JsonNode value, String defaultValue) {
		switch (getTypeOf(value)) {
		case BOOLEAN:
			return String.valueOf(value.asBoolean());
		case NUMBER:
			return String.valueOf(value.asDouble());
		case STRING:
			return value.asText();
		default:
			return defaultValue;
		}
	}

	private static ObjectNode asObject(JsonNode value) {
		if (getTypeOf(value) != Type.OBJECT) {
			throw new IllegalStateException();
		}
		return (ObjectNode) value;
	}

	private static ArrayNode asArray(JsonNode value) {
		if (getTypeOf(value) != Type.ARRAY) {
			throw new IllegalStateException();
		}
		return (ArrayNode) value;
	}

	private static Json wrap(JsonNode value) {
		return new JacksonJson(value);
	}

	private JsonNode toNativeValue(Json value) {
		if (value == null) {
			return null;
		} else if (value instanceof JacksonJson) {
			return ((JacksonJson)value).getNativeValue();
		} else {
			return parse(value.toJsonString());
		}
	}

	private JsonNode getNativeValue() {
		return nativeValue;
	}

	private static JsonNode fromObject(Object obj) {
		return MAPPER.convertValue(obj, JsonNode.class);
	}

	@Override
	public Object toObject(Class<?> cls) {
		try {
			return MAPPER.readValue(MAPPER.treeAsTokens(nativeValue), cls);
		} catch (IOException err) {
			throw new IllegalStateException(err);
		}
	}

	@Override
	public Type getType() {
		return getTypeOf(nativeValue);
	}

	@Override
	public boolean getBooleanValue(boolean defaultValue) {
		return asBoolean(nativeValue, defaultValue);
	}

	@Override
	public double getNumberValue(double defaultValue) {
		return asNumber(nativeValue, defaultValue);
	}

	@Override
	public String getStringValue(String defaultValue) {
		return asString(nativeValue, defaultValue);
	}

	@Override
	public Json get(String key) {
		return wrap(asObject(nativeValue).get(key));
	}

	@Override
	public Type getType(String key) {
		return getTypeOf(asObject(nativeValue).get(key));
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		return asBoolean(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public double getNumber(String key, double defaultValue) {
		return asNumber(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public String getString(String key, String defaultValue) {
		return asString(asObject(nativeValue).get(key), defaultValue);
	}

	@Override
	public int size() {
		return asArray(nativeValue).size();
	}

	@Override
	public Json get(int index) {
		return wrap(asArray(nativeValue).get(index));
	}

	@Override
	public Type getType(int index) {
		return getTypeOf(asArray(nativeValue).get(index));
	}

	@Override
	public boolean getBoolean(int index, boolean defaultValue) {
		return asBoolean(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public double getNumber(int index, double defaultValue) {
		return asNumber(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public String getString(int index, String defaultValue) {
		return asString(asArray(nativeValue).get(index), defaultValue);
	}

	@Override
	public String toJsonString() {
		return nativeValue.toString();
	}

	private <X> Iterable<X> asIterable(Iterator<X> itr) {
		return () -> itr;
	}

	@Override
	public Collection<Json> values() {
		Type type = getTypeOf(nativeValue);
		List<Json> result;
		if (type == Type.OBJECT) {
			result = new ArrayList<>();
			ObjectNode obj = asObject(nativeValue);
			for (JsonNode child : asIterable(obj.elements())) {
				result.add(wrap(child));
			}
		} else if (type == Type.ARRAY) {
			result = new ArrayList<>();
			ArrayNode array = asArray(nativeValue);
			for (int i = 0; i < array.size(); i++) {
				result.add(wrap(array.get(i)));
			}
		} else {
			result = Collections.emptyList();
		}
		return result;
	}

	@Override
	public Set<String> keySet() {
		Type type = getTypeOf(nativeValue);
		Set<String> result;
		if (type == Type.OBJECT) {
			ObjectNode obj = asObject(nativeValue);
			result = new LinkedHashSet<>();
			for (String key : asIterable(obj.fieldNames())) {
				result.add(key);
			}
		} else {
			result = Collections.emptySet();
		}
		return result;
	}

	@Override
	public Set<Entry<String, Json>> entrySet() {
		Type type = getTypeOf(nativeValue);
		Set<Map.Entry<String,Json>> result;
		if (type == Type.OBJECT) {
			result = new LinkedHashSet<>();
			ObjectNode obj = asObject(nativeValue);
			for (Map.Entry<String, JsonNode> e : asIterable(obj.fields())) {
				result.add(new SimpleEntry<>(e.getKey(), wrap(e.getValue())));
			}
		} else {
			result = Collections.emptySet();
		}
		return result;
	}

	@Override
	public void remove(String key) {
		asObject(nativeValue).remove(key);
	}

	@Override
	public void setNull(String key) {
		asObject(nativeValue).set(key, null);
	}

	@Override
	public void setBoolean(String key, boolean value) {
		asObject(nativeValue).put(key, value);
	}

	@Override
	public void setNumber(String key, double value) {
		asObject(nativeValue).put(key, value);
	}

	@Override
	public void setString(String key, String value) {
		asObject(nativeValue).put(key, value);
	}

	@Override
	public void set(String key, Json value) {
		asObject(nativeValue).set(key, toNativeValue(value));
	}

	@Override
	public Json setNewObject(String key) {
		JsonNode value = MAPPER.createObjectNode();
		asObject(nativeValue).set(key, value);
		return wrap(value);
	}

	@Override
	public Json setNewArray(String key) {
		JsonNode value = MAPPER.createArrayNode();
		asObject(nativeValue).set(key, value);
		return wrap(value);
	}

	@Override
	public void setNull(int index) {
		asArray(nativeValue).set(index, null);
	}

	@Override
	public void setBoolean(int index, boolean value) {
		asArray(nativeValue).set(index, value ? BooleanNode.TRUE : BooleanNode.FALSE);
	}

	@Override
	public void setNumber(int index, double value) {
		asArray(nativeValue).set(index, DoubleNode.valueOf(value));
	}

	@Override
	public void setString(int index, String value) {
		asArray(nativeValue).set(index, TextNode.valueOf(value));
	}

	@Override
	public void set(int index, Json value) {
		asArray(nativeValue).set(index, toNativeValue(value));
	}

	@Override
	public Json setNewObject(int index) {
		JsonNode value = MAPPER.createObjectNode();
		asArray(nativeValue).set(index, value);
		return wrap(value);
	}

	@Override
	public Json setNewArray(int index) {
		JsonNode value = MAPPER.createArrayNode();
		asArray(nativeValue).set(index, value);
		return wrap(value);
	}

}
